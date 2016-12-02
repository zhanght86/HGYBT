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
 * �����뱣�չ�˾���ˣ���ȫ�ࣩ
 * @author 
 *
 */
public class NewContBlcSec extends ServiceImpl {
	public NewContBlcSec(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	/** ���в����� ��Ҫ������ */
	private String mTranCom = null;
	private Map<Integer, Element> rescueMap = new HashMap<Integer, Element>();
	private Map<Integer, String> contNoMap = new HashMap<Integer, String>();
	private Map<Integer, String> tranNoMap = new HashMap<Integer, String>();
	private String tManagecom = "";
	/** �������� */
	private String balanceDate = "";
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContBlcSec.service()...");
		try {
			int fileNum = Integer.valueOf(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChildTextTrim("FILE_NUM"));
			if(fileNum < 1){
				throw new MidplatException("û�ж����ļ���");
			}
			try{
				FileUtil fu = new FileUtil(pInXmlDoc);
				cInXmlDoc = fu.fileSecurity();
			}catch(Exception ex){
				throw new MidplatException("��ȡ��ȫ���˱�׼���ĳ���");
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
			cLogger.info("1�����׼������ϣ�"+mSaveName);
			JdomUtil.print(cInXmlDoc);
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			//add by zhj ������Ȩ�� ��Ӵ���   
			cInXmlDoc = authority(cInXmlDoc);
			//add by zhj ������Ȩ�� ��Ӵ���end 	
			//ÿ�ζ���ʱ��ʼ������״̬
			String tAgentcodeStr = cInXmlDoc.getRootElement().getChild("request-head").getChildTextTrim("branch");
			//�������
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
				throw new MidplatException("�ѳɹ��������б�ȫ���ˣ������ظ�������");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}			
			//��ȫapply-code
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
//			cLogger.info("2�����׼������ϣ�"+mSaveName);
//			cLogger.info("��ȫ��ȫ��ִ��ź�ı��ģ�"+JdomUtil.toString(cInXmlDoc));
		
			//�޸ı�ȫ��result״̬
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
			    		cLogger.error("����RESCUE��״̬ʧ�ܣ�" + ttExeSQL.mErrors.getFirstError());
		    		}
				}
				throw new MidplatException("��ȫ��Ч���׶���ʧ�ܣ�");
			}
//			else{
//				List checktranslist = tOutRootEle.getChild("response-checkbill").getChild("check-trans-list").getChildren("check-trans");
//				for(int i=0;i<checktranslist.size();i++){
//					Element tchecktrans = (Element)checktranslist.get(i);
//					String tsql = "update rescue set rcode = '1' where contno = '"+tchecktrans.getChildText("policy-code")+"' and " +
//					" tranno = '"+tchecktrans.getChildText("sys-trans-id")+"' and trancom = '"+mTranCom+"'";
//					ExeSQL ttExeSQL = new ExeSQL();
//		    		if (!ttExeSQL.execUpdateSQL(tsql)) {
//			    		cLogger.error("����RESCUE��״̬ʧ�ܣ�" + ttExeSQL.mErrors.getFirstError());
//			    		throw new MidplatException("����RESCUE��״̬ʧ�ܣ�" + ttExeSQL.mErrors.getFirstError());
//		    		}
//				}
//			}
			//׼����Ҫ�����Ķ�������  ������ ϵͳ�е�
			// ��Ҫ��Cont�ı�״̬��Ϊ3(����)
			cInXmlDoc = getWriteOffDoc(cInXmlDoc);
			StringBuffer mSaveName2 = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(mtranstype)
			.append("_instd.xml");	
			SaveMessage.save(cInXmlDoc, mTranCom, mSaveName2.toString());
			cLogger.info("3���泷����׼������ϣ�"+mSaveName2);
			
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
			    		cLogger.error("����RESCUE��״̬ʧ�ܣ�" + ttExeSQL.mErrors.getFirstError());
		    		}
				}
				throw new MidplatException("��ȫ��Ч���׶���ʧ�ܣ�");
			} else {
				// ��״̬
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
					cLogger.error("����RESCUE����������ϸʧ�ܣ�" + mPubSubmit2.mErrors.getFirstError());
					throw new MidplatException("����RESCUE����������ϸʧ�ܣ�");
				}
			}
			if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null			
				cTranLogDB.setRCode("0");
				cTranLogDB.setRText("���˳ɹ�");
				cTranLogDB.setBak1("2");//��ȫ
				long tCurMillis = System.currentTimeMillis();
//				cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
				cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
				if (!cTranLogDB.update()) {
					cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
				}
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			//������־ʧ��ʱcTranLogDB=null			
			cTranLogDB.setRCode("1");
//			cTranLogDB.setBak1("2");//��ȫ
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			//������־ʧ��ʱcTranLogDB=null			
			cTranLogDB.setRCode("1");
//			cTranLogDB.setBak1("2");//��ȫ
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
//			cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
	
		
		cLogger.info("Out NewContBlcSec.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected void saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NewContBlc.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element requestcheckbill = mTranDataEle.getChild("request-checkbill");
		
		int mCount = Integer.parseInt(requestcheckbill.getChildText("client-success-total-count"));

		List<Element> mDetailList = requestcheckbill.getChild("check-trans-list").getChildren("check-trans");
		if (mDetailList.size() != mCount) {
			throw new MidplatException("���ܱ�������ϸ����������"+ mCount + "!=" + mDetailList.size());
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
	    		cLogger.error("����RESCUE��״̬ʧ�ܣ�" + ttExeSQL.mErrors.getFirstError());
    		}
		}
		
		cLogger.debug("Out NewContBlc.saveDetails()!");
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * ���� Ȩ�� ���У�鷽��
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
		cLogger.info("��ȫ�������˽��ױ��ģ�=="+JdomUtil.toString(mInXmlDoc));
		return mInXmlDoc;		
	}
	
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
		
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild("request-head");
		Element mpost = (Element) mHeadEle.getChild("post");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("area")+(String)mHeadEle.getChildTextTrim("branch");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("bank");
		 
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = "select agentcode,agentcomname,managecom  from NodeMap where TranCom='"+sTranCom+"' and bak5 = '1' and nodeno = '"+
		sNodeNo+"'";
		SSRS tssrs = new ExeSQL().execSQL(tSqlStr2);
		if (tssrs.MaxRow==0) {
			throw new MidplatException("�˶������㲻���ڣ���ȷ�ϣ�");
		}   
		tManagecom = tssrs.GetText(1, 3);
		
		// add start
		// �����ǷֶԷֵ�ģʽ�������Ҫ��branch(ebao����)���������ĸ�������
		// ע�⣬���������õ�ʱ����������ManageCom����ͨ�����ManageCom �Ƕ�Ӧ��ϵ������˵�����ͨ���㵽�������ĸ���������� �õ���ManageCom%
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
