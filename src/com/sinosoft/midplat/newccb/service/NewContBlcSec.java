package com.sinosoft.midplat.newccb.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 日终与保险公司对账（保全类）
 * @author 
 *
 */
public class NewContBlcSec extends ServiceImpl {
	public NewContBlcSec(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	/** 银行不承认 需要做撤单 */
	private String mTranCom = null;
	private Map<Integer, Element> rescueMap = new HashMap<Integer, Element>();
	private Map<Integer, String> contNoMap = new HashMap<Integer, String>();
	private Map<Integer, String> tranNoMap = new HashMap<Integer, String>();
	private String tManagecom = "";
	/** 对账日期 */
	private String balanceDate = "";
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContBlcSec.service()...");
		try {
			int fileNum = Integer.valueOf(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChildTextTrim("FILE_NUM"));
			if(fileNum < 1){
				throw new MidplatException("没有对账文件！");
			}
			try{
				FileUtil fu = new FileUtil(pInXmlDoc);
				cInXmlDoc = fu.fileSecurity();
			}catch(Exception ex){
				throw new MidplatException("获取保全对账标准报文出错！");
			}
			Element mRootEle = cInXmlDoc.getRootElement();		
			Element mHeadEle = mRootEle.getChild("request-head");
			String mBankName = mHeadEle.getChildText("bankname");	
			String mtranstype = mHeadEle.getChildText("trans-type");
			mTranCom = mHeadEle.getChildText("bank");
			balanceDate = mHeadEle.getChildTextTrim("trans-date");
			StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(mtranstype)
			.append("_instd.xml");	
			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName.toString());
			cLogger.info("1保存标准报文完毕！"+mSaveName);
			JdomUtil.print(cInXmlDoc);
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			//add by zhj 网点与权限 添加代理   
			cInXmlDoc = authority(cInXmlDoc);
			//add by zhj 网点与权限 添加代理end 	
			//每次对账时初始化对账状态
			String tAgentcodeStr = cInXmlDoc.getRootElement().getChild("request-head").getChildTextTrim("branch");
			//管理机构
			String tManagecomstr = cInXmlDoc.getRootElement().getChild("request-head").getChildTextTrim("ManageCom");
			cTranLogDB.setManageCom(tManagecomstr);
			cTranLogDB.setAgentCode(tAgentcodeStr);
			
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=").append(cTranLogDB.getTranDate())
				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and bak1='2'")
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				throw new MidplatException("已成功做过建行保全对账，不能重复操作！");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("查询历史对账信息异常！");
			} 
			
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}			
			//补全apply-code
//			List transList = cInXmlDoc.getRootElement().getChild("request-checkbill").getChild("check-trans-list").getChildren("check-trans");
//			for(int i=0;i<transList.size();i++){
//				Element thisEle = (Element)transList.get(i);
//				String transNo = thisEle.getChildTextTrim("sys-trans-id");
//				String sqlStr = "select * from rescue where tranno = '"+transNo+"' and trancom = '"+mTranCom+"' and makedate = '"+cTranLogDB.getMakeDate()+"' order by rescueno desc";
//				RESCUESet sRESCUEDB = new RESCUEDB().executeQuery(sqlStr);
//				if(1 == sRESCUEDB.size() ){
//					RESCUESchema sRESCUESchema = sRESCUEDB.get(1);
//					thisEle.getChild("back-flag").setText(sRESCUESchema.getAPPLYCODE());
//				}
//			}
//			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName.toString());
//			cLogger.info("2保存标准报文完毕！"+mSaveName);
//			cLogger.info("补全保全回执编号后的报文："+JdomUtil.toString(cInXmlDoc));
		
			//修改保全表result状态
			saveDetails(cInXmlDoc);
			 			 		
			cOutXmlDoc = new CallWebsvcAtomSvc("").call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("response-head");
			
			if (!tOutHeadEle.getChildText("trans-result").equals("0")) {
				List checktranslist = tOutRootEle.getChild("response-checkbill").getChild("check-trans-list").getChildren("check-trans");
				for(int i=0;i<checktranslist.size();i++){
					Element tchecktrans = (Element)checktranslist.get(i);
					String tsql = "update rescue set recode = '2' where contno = '"+tchecktrans.getChildText("policy-code")+"' and " +
					" tranno = '"+tchecktrans.getChildText("sys-trans-id")+"' and trancom = '"+mTranCom+"'";
					ExeSQL ttExeSQL = new ExeSQL();
		    		if (!ttExeSQL.execUpdateSQL(tsql)) {
			    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
		    		}
				}
				throw new MidplatException("保全有效交易对账失败！");
			}
//			else{
//				List checktranslist = tOutRootEle.getChild("response-checkbill").getChild("check-trans-list").getChildren("check-trans");
//				for(int i=0;i<checktranslist.size();i++){
//					Element tchecktrans = (Element)checktranslist.get(i);
//					String tsql = "update rescue set rcode = '1' where contno = '"+tchecktrans.getChildText("policy-code")+"' and " +
//					" tranno = '"+tchecktrans.getChildText("sys-trans-id")+"' and trancom = '"+mTranCom+"'";
//					ExeSQL ttExeSQL = new ExeSQL();
//		    		if (!ttExeSQL.execUpdateSQL(tsql)) {
//			    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
//			    		throw new MidplatException("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
//		    		}
//				}
//			}
			//准备需要撤单的对账数据  银行无 系统有的
			// 需要把Cont的表状态改为3(撤单)
			cInXmlDoc = getWriteOffDoc(cInXmlDoc);
			StringBuffer mSaveName2 = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(mtranstype)
			.append("_instd.xml");	
			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName2.toString());
			cLogger.info("3保存撤单标准报文完毕！"+mSaveName2);
			
			cOutXmlDoc = new CallWebsvcAtomSvc("").call(cInXmlDoc);
			tOutRootEle = cOutXmlDoc.getRootElement();
			tOutHeadEle = tOutRootEle.getChild("response-head");
			if (!tOutHeadEle.getChildText("trans-result").equals("0")) {
				List checktranslist = tOutRootEle.getChild("response-checkbill").getChild("cancel-trans-list").getChildren("check-trans");
				for(int i=0;i<checktranslist.size();i++){
					Element tchecktrans = (Element)checktranslist.get(i);
					String tsql = "update rescue set recode = '2' where contno = '"+tchecktrans.getChildText("policy-code")+"' and " +
					" tranno = '"+tchecktrans.getChildText("sys-trans-id")+"' and trancom = '"+mTranCom+"'";
					ExeSQL ttExeSQL = new ExeSQL();
		    		if (!ttExeSQL.execUpdateSQL(tsql)) {
			    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
		    		}
				}
				throw new MidplatException("保全无效交易对账失败！");
			} else {
				// 改状态
				MMap mSubmitMMap2 = new MMap();
				for (int i = 1; i <= contNoMap.size(); i++) {
					String tSql = "update rescue set state = '3' where contno = '"
							+ contNoMap.get(i) + "' and tranno = '"+tranNoMap.get(i)+"' and trancom = '"+mTranCom+"'";
					System.out.println(tSql);
					mSubmitMMap2.put(tSql, "UPDATE");
				}
				
				VData mSubmitVData2 = new VData();
				mSubmitVData2.add(mSubmitMMap2);
				PubSubmit mPubSubmit2 = new PubSubmit();
				if (!mPubSubmit2.submitData(mSubmitVData2, "")) {
					cLogger.error("保存RESCUE表撤单对账明细失败！" + mPubSubmit2.mErrors.getFirstError());
					throw new MidplatException("保存RESCUE表撤单对账明细失败！");
				}
			}
			if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null			
				cTranLogDB.setRCode("0");
				cTranLogDB.setRText("对账成功");
				cTranLogDB.setBak1("2");//保全
				long tCurMillis = System.currentTimeMillis();
//				cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
				cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
				if (!cTranLogDB.update()) {
					cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
				}
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			//插入日志失败时cTranLogDB=null			
			cTranLogDB.setRCode("1");
//			cTranLogDB.setBak1("2");//保全
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			//插入日志失败时cTranLogDB=null			
			cTranLogDB.setRCode("1");
//			cTranLogDB.setBak1("2");//保全
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
//			cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
	
		
		cLogger.info("Out NewContBlcSec.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected void saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NewContBlc.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element requestcheckbill = mTranDataEle.getChild("request-checkbill");
		
		int mCount = Integer.parseInt(requestcheckbill.getChildText("client-success-total-count"));

		List<Element> mDetailList = requestcheckbill.getChild("check-trans-list").getChildren("check-trans");
		if (mDetailList.size() != mCount) {
			throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" + mDetailList.size());
		}
		int index = 0;
		for (Element tDetailEle : mDetailList) {
			rescueMap.put(index, tDetailEle);
			index++;
			
		}

		for(int j=0;j<rescueMap.size();j++){
			Element thisEle = rescueMap.get(j);
			String ttsql = "update rescue set result = '1'"
	     		+" where contno = '"+thisEle.getChildTextTrim("policy-code")+"' and tranno = '"+thisEle.getChildTextTrim("sys-trans-id")+"'" +
	     				" and trancom = '"+mTranCom+"'";	
    		ExeSQL ttExeSQL = new ExeSQL();
    		if (!ttExeSQL.execUpdateSQL(ttsql)) { 
	    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
    		}
		}
		
		cLogger.debug("Out NewContBlc.saveDetails()!");
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document getWriteOffDoc(Document mInXmlDoc) throws MidplatException{
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild("request-head");
		mRootEle.getChild("request-checkbill").removeContent();
		Element tcancellist = new Element("cancel-trans-list");
		mRootEle.getChild("request-checkbill").addContent(tcancellist);
		String sTranCom =  (String)mHeadEle.getChildTextTrim("bank");
		String tsql = "select rescueno, contno , trandate, trancom, nodeno, tranno, applycode, funcflag from rescue where trancom = '"+sTranCom+"' and trandate = '"+balanceDate+"' and (result is null or result != '1')" +
 		"  and funcflag = '245'";
		SSRS tSsrs = new ExeSQL().execSQL(tsql);
        for(int j=1;j<=tSsrs.MaxRow;j++){
        	contNoMap.put(j, tSsrs.GetText(j, 2));
        	tranNoMap.put(j, tSsrs.GetText(j, 6));
			Element tchecktrans = new Element("check-trans");
			Element sequence = new Element("sequence");
			sequence.setText(""+j);
			tchecktrans.addContent(sequence);
			Element transdate = new Element("trans-date");
			transdate.setText(tSsrs.GetText(j, 3));
			tchecktrans.addContent(transdate);
			Element transtime = new Element("trans-time");
			transtime.setText(String.valueOf(DateUtil.getCur6Time()));
			tchecktrans.addContent(transtime);
			Element transid = new Element("trans-id");
			transid.setText(tSsrs.GetText(j, 6));
			tchecktrans.addContent(transid);
			Element transarea = new Element("trans-area");
			transarea.setText(sTranCom);
			tchecktrans.addContent(transarea);
			Element transzone = new Element("trans-zone");
			tchecktrans.addContent(transzone);
			Element transbank = new Element("trans-bank");
			transbank.setText("002");
			tchecktrans.addContent(transbank);
			Element transbr = new Element("trans-br");
			transbr.setText(tSsrs.GetText(j, 5));
			tchecktrans.addContent(transbr);
			Element ph_name = new Element("ph-name");
			ph_name.setText("");
			tchecktrans.addContent(ph_name);
			Element applycode = new Element("apply-code");		
			applycode.setText("");
			tchecktrans.addContent(applycode);
			Element policycode = new Element("policy-code");
			policycode.setText(tSsrs.GetText(j, 2));
			tchecktrans.addContent(policycode);
			Element print_code = new Element("print-code");
			print_code.setText(tSsrs.GetText(j, 7));
			tchecktrans.addContent(print_code);
			Element premium = new Element("premium");
			premium.setText("0");
			tchecktrans.addContent(premium);
			Element agency_hand = new Element("agency-hand");
			agency_hand.setText("");
			tchecktrans.addContent(agency_hand);
			Element transtype = new Element("trans-type");
			transtype.setText("02");	
			tchecktrans.addContent(transtype);
			Element backflag = new Element("back-flag");
			backflag.setText(tSsrs.GetText(j, 8));
			tchecktrans.addContent(backflag);
			Element successflag = new Element("success-flag");
			successflag.setText("0000");
			tchecktrans.addContent(successflag);	
			Element sys_trans_id = new Element("sys-trans-id");
			sys_trans_id.setText(tSsrs.GetText(j, 6));
			tchecktrans.addContent(sys_trans_id);
			tcancellist.addContent(tchecktrans);
        }
		cLogger.info("保全撤销对账交易报文：=="+JdomUtil.toString(mInXmlDoc));
		return mInXmlDoc;		
	}
	
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
		
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild("request-head");
		Element mpost = (Element) mHeadEle.getChild("post");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("area")+(String)mHeadEle.getChildTextTrim("branch");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("bank");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = "select agentcode,agentcomname,managecom  from NodeMap where TranCom='"+sTranCom+"' and bak5 = '1' and nodeno = '"+
		sNodeNo+"'";
		SSRS tssrs = new ExeSQL().execSQL(tSqlStr2);
		if (tssrs.MaxRow==0) {
			throw new MidplatException("此对账网点不存在，请确认！");
		}   
		tManagecom = tssrs.GetText(1, 3);
		
		// add start
		// 建行是分对分的模式，因此需要在branch(ebao编码)来区分是哪个地区的
		// 注意，在网点配置的时候对账网点的ManageCom与普通网点的ManageCom 是对应关系，就是说这个普通网点到底属于哪个对账网点的 用的是ManageCom%
//		if (sTranCom.equals("002")) {
//			String tSqlStr3 = "select bak1 from NodeMap where TranCom='"+sTranCom+"' and bak5 = '1' and nodeno = '"+
//			sNodeNo+"'";
//			mHeadEle.getChild("branch").setText(new ExeSQL().getOneValue(tSqlStr3).trim());
//		}
		// add end
		
		mpost.setText(tssrs.GetText(1, 1));
		Element tAgentcomName = new Element("AgentcomName");
		tAgentcomName.setText(tssrs.GetText(1, 2));
		mHeadEle.addContent(tAgentcomName);
		Element tManageCom = new Element("ManageCom");
		tManageCom.setText(tssrs.GetText(1, 3));
		mHeadEle.addContent(tManageCom);
		return mInXmlDoc;
		
	}
	
}
