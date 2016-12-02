package com.sinosoft.midplat.newccb.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.format.PrintCont;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 日终与保险公司对账
 * 在该服务类中根据银行请求报文中的交易码来判断是保全对账还是账务对账
 * 当为保全对账的时候调用保全对账服务类
 * 在该服务类中调用文件解密类，获取标准对账报文
 * 对账流程：先对账交易成功的交易，然后通过本地数据库查询出银行方没有，但是核心存在的保单，
 * 组织取消保单的对账报文，并且调用核心相应接口
 * @author 
 *
 */
public class NewContBlc_20150309 extends ServiceImpl {
	private Element cSubInXmlEle1 = new Element("Body");
	private Element cSubInXmlEle2 = new Element("Body");
	private Element cSubInXmlEle3 = new Element("Body");
	private Element cSubInXmlEle4 = new Element("Body");
	protected Document cSubInXmlDoc1=null;
	protected Document cSubInXmlDoc2=null;
	protected Document cSubInXmlDoc3=null;
	protected Document cSubInXmlDoc4=null;
	protected Document cSubOutXmlDoc1;
	protected Document cSubOutXmlDoc2;
	protected Document cSubOutXmlDoc3;
	protected Document cSubOutXmlDoc4;
	private String typeCode = null;
	private String mTranCom = null;
	private Map<Integer, Element> rescueMap = new HashMap<Integer, Element>();
	private Map<Integer, String> contNoMap = new HashMap<Integer, String>();
	private Map<Integer, String> tranNoMap = new HashMap<Integer, String>();
	public NewContBlc_20150309(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 

	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws Exception {
		long mStartMillis = System.currentTimeMillis();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
//		if((typeCode.equals("P53817104"))&&(typeCode!=null)){//保全对账
//			Element cBusiConfRoot = null;
//			cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
//			Element cThisBusiConf = null;
//			try {
//				cThisBusiConf = (Element) XPath.selectSingleNode(
//						cBusiConfRoot, "business[funcFlag='1048']");
//			} catch (JDOMException e) {
//				e.printStackTrace();
//			}
//			
//			ServiceImpl mCcbContBlcSec =  new com.sinosoft.midplat.newccb.service.NewContBlcSec(cThisBusiConf);
//			try {
//				cOutXmlDoc =	mCcbContBlcSec.service(pInXmlDoc);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return cOutXmlDoc;
//		}
		cLogger.info("Into NewContBlc.service()...");
		
		try {
			int fileNum = Integer.valueOf(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChildTextTrim("FILE_NUM"));
			if(fileNum < 1){
				throw new MidplatException("没有对账文件！");
			}
			try{
				//解密报文，并且获得标准报文
				FileUtil fu = new FileUtil(pInXmlDoc);
				cInXmlDoc = fu.fileSecurity();
				cLogger.info("成功做完标准对账报文.................");
			}catch(Exception ex){
				throw new MidplatException("获取银行账务标准对账报文出错！");
			}
			Element mRootEle = cInXmlDoc.getRootElement();		
			Element mHeadEle = mRootEle.getChild("Head");
			String FuncFlag = mHeadEle.getChildText("FuncFlag");
			mTranCom = mHeadEle.getChildText("TranCom");
//			StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
//			.append('_').append(NoFactory.nextAppNo())
//			.append('_').append(FuncFlag)
//			.append("_inStd.xml");	
//			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName.toString());
//			cLogger.info("保存标准报文完毕！"+mSaveName);
			JdomUtil.print(cInXmlDoc);
			
			cTranLogDB = insertTranLog(cInXmlDoc);
//			//add by zhj 网点与权限 添加代理   
//			cInXmlDoc = authority(cInXmlDoc);
//			//add by zhj 网点与权限 添加代理end 	
//			//每次对账时初始化对账状态
//			String tAgentcodeStr = cInXmlDoc.getRootElement().getChild("Header").getChildTextTrim("AgentCode");
			//管理机构
//			String tManagecomstr = cInXmlDoc.getRootElement().getChild("Header").getChildTextTrim("ManageCom");
//			cTranLogDB.setManageCom(tManagecomstr);
//			cTranLogDB.setAgentCode(tAgentcodeStr);
			
//			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
//				.append(" and TranDate=").append(cTranLogDB.getTranDate())
//				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
//				.append(" and TranCom=").append(cTranLogDB.getTranCom())
//				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
//				.toString();
//			ExeSQL tExeSQL = new ExeSQL();
//			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
//				throw new MidplatException("已成功做过新单对账，不能重复操作！");
//			} else if (tExeSQL.mErrors.needDealError()) {
//				throw new MidplatException("查询历史对账信息异常！");
//			} 
			
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}			
			
			//账务对账
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817103")){
				
				List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
				//剔除对账内容，再按类型组装报文
				for (int i=0;i<tDetail.size();i++) {
					cLogger.info("交易类型："+tDetail.get(i).getChildText("BusiType"));
					if(tDetail.get(i).getChildTextTrim("BusiType").equals("09")){//满期
						cSubInXmlEle1.addContent((Element)tDetail.get(i).clone());
					}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("10")){//退保
						cSubInXmlEle2.addContent((Element)tDetail.get(i).clone());
					}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("11")){//续期
						cSubInXmlEle3.addContent((Element)tDetail.get(i).clone());
					}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("12")){//新单
						cSubInXmlEle4.addContent((Element)tDetail.get(i).clone());
					}
				}
				
				try {
					//重新按类型将Detail填充到子报文中去
					System.out.println("=============重新组装报文=============");
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle1);
					cSubInXmlDoc1 = (Document)cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle2);
					cSubInXmlDoc2 =  (Document)cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle3);
					cSubInXmlDoc3 =  (Document)cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle4);
					cSubInXmlDoc4 =  (Document)cInXmlDoc.clone();
				} catch (Exception e) {
					e.printStackTrace();
				}
				JdomUtil.print(cSubInXmlDoc1);
				JdomUtil.print(cSubInXmlDoc2);
				JdomUtil.print(cSubInXmlDoc3);
				JdomUtil.print(cSubInXmlDoc4);
				
				
//				cSubOutXmlDoc1 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc1);
				
				if(cSubInXmlDoc2.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc2 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc2);
				}
				if(cSubInXmlDoc3.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc3 = new CallWebsvcAtomSvc("18").call(cSubInXmlDoc3);
				}
				if(cSubInXmlDoc4.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc4 = new CallWebsvcAtomSvc("6").call(cSubInXmlDoc4);
				}
				
				
//				满期对账核心没有，假交易，返回成功
				Element tTranData=new Element(TranData);
				
				Element tHead=new Element(Head);
				Element tFlag=new Element(Flag);
				tFlag.setText("0");
				Element tDesc=new Element(Desc);
				tDesc.setText("交易成功");
				Element tBody=new Element(Body);
				
				tTranData.addContent(tHead);
				tTranData.addContent(tBody);
				
				tHead.addContent(tFlag);
				tHead.addContent(tDesc);
				
				cSubOutXmlDoc1=new Document(tTranData);
				
				Element tOutHeadEle = cSubOutXmlDoc1.getRootElement().getChild(Head);
				Element tOutHeadEle2 = cSubOutXmlDoc2.getRootElement().getChild(Head);
				Element tOutHeadEle3 = cSubOutXmlDoc3.getRootElement().getChild(Head);
				Element tOutHeadEle4 = cSubOutXmlDoc4.getRootElement().getChild(Head);
				if(cSubInXmlDoc1.getRootElement().getChild("Body").getContentSize()!=0){
					if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
						cOutXmlDoc=cSubOutXmlDoc1;
//						throw new MidplatException(tOutHeadEle.getChildText(Desc));
					}
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle2.getChildText(Flag))) {	//交易失败
					cOutXmlDoc=cSubOutXmlDoc2;
//					throw new MidplatException(tOutHeadEle2.getChildText(Desc));
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle3.getChildText(Flag))) {	//交易失败
					cOutXmlDoc=cSubOutXmlDoc3;
//					throw new MidplatException(tOutHeadEle3.getChildText(Desc));
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle4.getChildText(Flag))) {	//交易失败
					cOutXmlDoc=cSubOutXmlDoc4;
//					throw new MidplatException(tOutHeadEle3.getChildText(Desc));
				}
				
				if(CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag))&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle2.getChildText(Flag))
						&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle3.getChildText(Flag))&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle4.getChildText(Flag))){
					cOutXmlDoc=cSubOutXmlDoc1;
				}
				
			}
			
			//保全对账
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817104")){

				cOutXmlDoc = new CallWebsvcAtomSvc("16").call(cInXmlDoc);
//				测试，假交易，返回成功
//				Element tTranData=new Element(TranData);
//				
//				Element tHead=new Element(Head);
//				Element tFlag=new Element(Flag);
//				tFlag.setText("0");
//				Element tDesc=new Element(Desc);
//				tDesc.setText("交易成功");
//				Element tBody=new Element(Body);
//				
//				tTranData.addContent(tHead);
//				tTranData.addContent(tBody);
//				
//				tHead.addContent(tFlag);
//				tHead.addContent(tDesc);
//				
//				cSubOutXmlDoc1=new Document(tTranData);
				
			}
			
			//单证对账
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817105")){
				

				
//				测试，假交易，返回成功
//				Element tTranData=new Element(TranData);
//				
//				Element tHead=new Element(Head);
//				Element tFlag=new Element(Flag);
//				tFlag.setText("0");
//				Element tDesc=new Element(Desc);
//				tDesc.setText("交易成功");
//				Element tBody=new Element(Body);
//				
//				tTranData.addContent(tHead);
//				tTranData.addContent(tBody);
//				
//				tHead.addContent(tFlag);
//				tHead.addContent(tDesc);
//				
//				cSubOutXmlDoc1=new Document(tTranData);
				
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);		
			}
			//保存对账明细
//			saveDetails(cInXmlDoc);
			
 			 		
//			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(cInXmlDoc);

			//测试时做假交易，返回成功
			Element tTranData=new Element(TranData);
			
			Element tHead=new Element(Head);
			Element tFlag=new Element(Flag);
			tFlag.setText("0");
			Element tDesc=new Element(Desc);
			tDesc.setText("交易成功");
			Element tBody=new Element(Body);
			tTranData.addContent(tHead);
			tTranData.addContent(tBody);
			tHead.addContent(tFlag);
			tHead.addContent(tDesc);
			cOutXmlDoc=new Document(tTranData);
			
			
			
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("Head");
			
//			if (!tOutHeadEle.getChildText("Flag").equals("0")) {
//				List<Element> mDetail = tOutRootEle.getChild("Body").getChildren("Detail");
//				for(int i=0;i<mDetail.size();i++){
//					Element detail = (Element)mDetail.get(i);
//						String tsql = "update rescue set recode = '2' where contno = '"+detail.getChildText("ContNo")+"' and " +
//								"and trancom = '"+mTranCom+"'";
//						ExeSQL ttExeSQL = new ExeSQL();
//			    		if (!ttExeSQL.execUpdateSQL(tsql)) {
//				    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
//			    		}
//				}
//				throw new MidplatException("对账交易成功的保单失败！");
//			}
			//准备需要撤单的对账数据  银行无 系统有的
			// 需要把Cont的表状态改为3(撤单)
//			cInXmlDoc = getWriteOffDoc(cInXmlDoc);
//			StringBuffer mSaveName2 = new StringBuffer(Thread.currentThread().getName())
//			.append('_').append(NoFactory.nextAppNo())
//			.append('_').append(FuncFlag)
//			.append("_inStd.xml");	
//			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName2.toString());
//			cLogger.info("保存标准报文完毕2！"+mSaveName);
//			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(cInXmlDoc);
//			if (!tOutHeadEle.getChildText("trans-result").equals("0")) {
//				List<Element> checktranslist = tOutRootEle.getChild("response-checkbill").getChild("cancel-trans-list").getChildren("check-trans");
//				for(int i=0;i<checktranslist.size();i++){
//					Element tchecktrans = (Element)checktranslist.get(i);
//					String transType = tchecktrans.getChildTextTrim("trans-type");
//						if(transType.equals("01")){
//						String tsql = "update CONTBLCDTL set rcode = '1',rtext = '"+tchecktrans.getChild("errors").getChild("error").getChildText("error-desc")+"'"
//						+" where contno = '"+tchecktrans.getChildText("policy-code")+"'";		
//						ExeSQL ttExeSQL = new ExeSQL();
//			    		if (!ttExeSQL.execUpdateSQL(tsql)) {
//				    		cLogger.error("更新新单保单对账状态失败！" + ttExeSQL.mErrors.getFirstError());
//			    		}
//					}else{
//						String tsql = "update rescue set recode = '2' where contno = '"+tchecktrans.getChildText("policy-code")+"' and " +
//						" tranno = '"+tchecktrans.getChildText("sys-trans-id")+"' and trancom = '"+mTranCom+"'";
//						ExeSQL ttExeSQL = new ExeSQL();
//			    		if (!ttExeSQL.execUpdateSQL(tsql)) {
//				    		cLogger.error("更新RESCUE表状态失败！" + ttExeSQL.mErrors.getFirstError());
//			    		}
//					}
//				}
//				throw new MidplatException("对账需要取消的保单失败！");
//			} else {
//				// 改状态
//				MMap mSubmitMMap = new MMap();
//				for (int i = 0; i < CDBdh.size(); i++) {
//					String tSql = "update cont set state = '3' where contno = '"
//							+ CDBdh.get(i) + "'";
//					System.out.println(tSql);
//					mSubmitMMap.put(tSql, "UPDATE");
//				}
//				
//				VData mSubmitVData = new VData();
//				mSubmitVData.add(mSubmitMMap);
//				PubSubmit mPubSubmit = new PubSubmit();
//				if (!mPubSubmit.submitData(mSubmitVData, "")) {
//					cLogger.error("保存撤单对账明细失败！" + mPubSubmit.mErrors.getFirstError());
//					throw new MidplatException("保存撤单对账明细失败！");
//				}
//				
//				MMap mSubmitMMap2 = new MMap();
//				for (int i = 1; i <= contNoMap.size(); i++) {
//					String tSql = "update rescue set state = '3' where contno = '"
//							+ contNoMap.get(i) + "' and tranno = '"+tranNoMap.get(i)+"' and trancom = '"+mTranCom+"'";
//					System.out.println(tSql);
//					mSubmitMMap2.put(tSql, "UPDATE");
//				}
//				
//				VData mSubmitVData2 = new VData();
//				mSubmitVData2.add(mSubmitMMap2);
//				PubSubmit mPubSubmit2 = new PubSubmit();
//				if (!mPubSubmit2.submitData(mSubmitVData2, "")) {
//					cLogger.error("保存RESCUE表撤单对账明细失败！" + mPubSubmit2.mErrors.getFirstError());
//					throw new MidplatException("保存RESCUE表撤单对账明细失败！");
//				}
//			}
//			if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null			
//				cTranLogDB.setRCode("0");
//				cTranLogDB.setRText("对账成功");
//				cTranLogDB.setBak1("1");//账务
//				long tCurMillis = System.currentTimeMillis();
////				cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
//				cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
//				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
//				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
//				if (!cTranLogDB.update()) {
//					cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
//				}
//			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			//插入日志失败时cTranLogDB=null			
			cTranLogDB.setRCode("1");
//			cTranLogDB.setBak1("1");//账务
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		
		} 
//		catch (Exception ex) {
//			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
//			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
//			//插入日志失败时cTranLogDB=null			
//			cTranLogDB.setRCode("1");
////			cTranLogDB.setBak1("1");//账务
//			cTranLogDB.setRText(ex.getMessage());
//			long tCurMillis = System.currentTimeMillis();
////			cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
//			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
//			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
//			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
//			if (!cTranLogDB.update()) {
//				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
//			}
//		}
		
		cLogger.info("Out NewContBlc.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NewContBlc.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount) {
			throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList) {
		//	mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
			
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//有些银行传
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
//			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//有些银行传
//			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //有些银行传
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem) {
			throw new MidplatException("汇总金额与明细总金额不符！"+ mSumPrem + "!=" + mSumDtlPrem);
		}
		
		/** 
		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
		 */
		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("保存对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存对账明细失败！");
		}
		
		cLogger.debug("Out NewContBlc.saveDetails()!");
		return mContBlcDtlSet;
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
//	@SuppressWarnings("unchecked")
//	private Document getWriteOffDoc(Document mInXmlDoc) throws MidplatException{
//		Element mRootEle = mInXmlDoc.getRootElement();
//		Element mHeadEle = (Element) mRootEle.getChild("request-head");
//		mRootEle.getChild("request-checkbill").removeContent();
//		Element tcancellist = new Element("cancel-trans-list");
//		mRootEle.getChild("request-checkbill").addContent(tcancellist);
////		String sNodeNo = (String)mHeadEle.getChildTextTrim("area")+(String)mHeadEle.getChildTextTrim("branch");
//		String sTranCom =  (String)mHeadEle.getChildTextTrim("bank");
//		String tsql = "select a.recordno,a.contno,a.trandate,a.prem,b.trancom,b.nodeno,b.proposalprtno,b.tranno,b.otherno from cont a,tranlog b where a.managecom like'"+tManagecom+"%' and a.trancom = '"+sTranCom+"'"+
//		" and a.trandate='"+balanceDate+"' and a.state = '2' and (a.bak10 is null or a.bak10 != '1') and a.contno = b.contno and b.funcflag='102'";		
//		SSRS tSsrs = new ExeSQL().execSQL(tsql);
//        ContBlcDtlSet tContBlcDtlSet = new ContBlcDtlSet();
//        for(int j=1;j<=tSsrs.MaxRow;j++){
//        	ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
//			tContBlcDtlSchema.setBlcTranNo(tSsrs.GetText(j, 1));
//			tContBlcDtlSchema.setContNo(tSsrs.GetText(j, 2));
//			CDBdh.add(tSsrs.GetText(j, 2));
//			tContBlcDtlSchema.setProposalPrtNo("");	//有些银行传
//			tContBlcDtlSchema.setTranDate(tSsrs.GetText(j, 3));
//			//
//			String tPrem = tSsrs.GetText(j, 4);
//			if (tPrem == null || tPrem.equals(""))
//				tPrem = "0.0";
//			double nPrem = java.lang.Double.parseDouble(tPrem)*100;
//			DecimalFormat df1 = new DecimalFormat("####");
//			df1.setGroupingUsed(false);
//			String prem = df1.format(nPrem);
//			// 
//			tContBlcDtlSchema.setPrem(prem);
//			tContBlcDtlSchema.setTranCom(tSsrs.GetText(j, 5));
//			tContBlcDtlSchema.setNodeNo(tSsrs.GetText(j, 6));
//			tContBlcDtlSchema.setAppntName("");	//有些银行传
//			tContBlcDtlSchema.setInsuredName(""); //有些银行传
//			tContBlcDtlSchema.setMakeDate(DateUtil.getCur8Date());
//			tContBlcDtlSchema.setMakeTime(DateUtil.getCur6Time());
//			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
//			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
//			tContBlcDtlSchema.setOperator(CodeDef.SYS);
//			tContBlcDtlSchema.setBak1("3"); // 3是撤单
//			tContBlcDtlSet.add(tContBlcDtlSchema);
//			Element tchecktrans = new Element("check-trans");
//			Element sequence = new Element("sequence");
//			sequence.setText("");
//			tchecktrans.addContent(sequence);
//			Element transdate = new Element("trans-date");
//			transdate.setText(tSsrs.GetText(j, 3));
//			tchecktrans.addContent(transdate);
//			Element transtime = new Element("trans-time");
//			transtime.setText(String.valueOf(DateUtil.getCur6Time()));
//			tchecktrans.addContent(transtime);
//			Element transid = new Element("trans-id");
//			transid.setText(tSsrs.GetText(j, 8));
//			tchecktrans.addContent(transid);
//			Element transarea = new Element("trans-area");
//			transarea.setText(sTranCom);
//			tchecktrans.addContent(transarea);
//			Element transbank = new Element("trans-bank");
//			transbank.setText("002");
//			tchecktrans.addContent(transbank);
//			Element transzone = new Element("trans-zone");
//			tchecktrans.addContent(transzone);
//			Element transbr = new Element("trans-br");
//			transbr.setText(tSsrs.GetText(j, 6));
//			tchecktrans.addContent(transbr);
//			Element ph_name = new Element("ph-name");
//			ph_name.setText("");
//			tchecktrans.addContent(ph_name);
//			Element applycode = new Element("apply-code");		
//			applycode.setText(tSsrs.GetText(j, 7));
//			tchecktrans.addContent(applycode);
//			Element policycode = new Element("policy-code");
//			policycode.setText(tSsrs.GetText(j, 2));
//			tchecktrans.addContent(policycode);
//			Element print_code = new Element("print-code");
//			print_code.setText(tSsrs.GetText(j, 9));
//			tchecktrans.addContent(print_code);
//			Element premium = new Element("premium");
//			premium.setText(tSsrs.GetText(j, 4));
//			tchecktrans.addContent(premium);
//			Element agency_hand = new Element("agency-hand");
//			agency_hand.setText("");
//			tchecktrans.addContent(agency_hand);
//			Element transtype = new Element("trans-type");
//			transtype.setText("01");
//			tchecktrans.addContent(transtype);
//			Element backflag = new Element("back-flag");
//			backflag.setText("102");
//			tchecktrans.addContent(backflag);
//			Element successflag = new Element("success-flag");
//			successflag.setText("0000");
//			tchecktrans.addContent(successflag);	
//			Element sys_trans_id = new Element("sys-trans-id");
//			sys_trans_id.setText(tSsrs.GetText(j, 8));
//			tchecktrans.addContent(sys_trans_id);
//			tcancellist.addContent(tchecktrans);
//        }
//        
//        String ttsql2 = "select rescueno, contno , trandate, trancom, nodeno, tranno, applycode, funcflag from rescue where trancom = '"+sTranCom+"' and trandate = '"+balanceDate+"' and (result is null or result != '1')" +
//        		"  and (funcflag = '240' or funcflag = '246')";//240  续期确认        246 退保
//        SSRS tSsrs2 = new ExeSQL().execSQL(ttsql2);
//        for(int j=1;j<=tSsrs2.MaxRow;j++){
//        	contNoMap.put(j, tSsrs2.GetText(j, 2));
//        	tranNoMap.put(j, tSsrs2.GetText(j, 6));
//        	String funcflag = tSsrs2.GetText(j, 8);
//			Element tchecktrans = new Element("check-trans");
//			Element sequence = new Element("sequence");
//			sequence.setText("");
//			tchecktrans.addContent(sequence);
//			Element transdate = new Element("trans-date");
//			transdate.setText(tSsrs2.GetText(j, 3));
//			tchecktrans.addContent(transdate);
//			Element transtime = new Element("trans-time");
//			transtime.setText(String.valueOf(DateUtil.getCur6Time()));
//			tchecktrans.addContent(transtime);
//			Element transid = new Element("trans-id");
//			transid.setText(tSsrs2.GetText(j, 6));
//			tchecktrans.addContent(transid);
//			Element transarea = new Element("trans-area");
//			transarea.setText(sTranCom);
//			tchecktrans.addContent(transarea);
//			Element transbank = new Element("trans-bank");
//			transbank.setText("002");
//			tchecktrans.addContent(transbank);
//			Element transzone = new Element("trans-zone");
//			tchecktrans.addContent(transzone);
//			Element transbr = new Element("trans-br");
//			transbr.setText(tSsrs2.GetText(j, 5));
//			tchecktrans.addContent(transbr);
//			Element ph_name = new Element("ph-name");
//			ph_name.setText("");
//			tchecktrans.addContent(ph_name);
//			Element applycode = new Element("apply-code");		
//			applycode.setText("");
//			tchecktrans.addContent(applycode);
//			Element policycode = new Element("policy-code");
//			policycode.setText(tSsrs2.GetText(j, 2));
//			tchecktrans.addContent(policycode);
//			Element print_code = new Element("print-code");
//			print_code.setText(tSsrs2.GetText(j, 7));
//			tchecktrans.addContent(print_code);
//			Element premium = new Element("premium");
//			premium.setText("0");
//			tchecktrans.addContent(premium);
//			Element agency_hand = new Element("agency-hand");
//			agency_hand.setText("");
//			tchecktrans.addContent(agency_hand);
//			Element transtype = new Element("trans-type");
//			if(funcflag.equals("240")){
//				transtype.setText("02");
//			}else{
//				transtype.setText("03");
//			}	
//			tchecktrans.addContent(transtype);
//			Element backflag = new Element("back-flag");
//			backflag.setText(tSsrs2.GetText(j, 8));
//			tchecktrans.addContent(backflag);
//			Element successflag = new Element("success-flag");
//			successflag.setText("0000");
//			tchecktrans.addContent(successflag);	
//			Element sys_trans_id = new Element("sys-trans-id");
//			sys_trans_id.setText(tSsrs2.GetText(j, 6));
//			tchecktrans.addContent(sys_trans_id);
//			tcancellist.addContent(tchecktrans);
//        }
//        
//        MMap mSubmitMMap = new MMap();
//		mSubmitMMap.put(tContBlcDtlSet, "INSERT");
//		VData mSubmitVData = new VData();
//		mSubmitVData.add(mSubmitMMap);
//		PubSubmit mPubSubmit = new PubSubmit();
//		if (!mPubSubmit.submitData(mSubmitVData, "")) {
//			cLogger.error("保存撤单对账明细失败！" + mPubSubmit.mErrors.getFirstError());
//			throw new MidplatException("保存撤单对账明细失败！");
//		}
//		
//		return mInXmlDoc;		
//	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("此网点不存在，请确认！");
		}  
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
		
	}
	
}
