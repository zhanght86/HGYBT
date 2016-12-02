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
 * �����뱣�չ�˾����
 * �ڸ÷������и��������������еĽ��������ж��Ǳ�ȫ���˻����������
 * ��Ϊ��ȫ���˵�ʱ����ñ�ȫ���˷�����
 * �ڸ÷������е����ļ������࣬��ȡ��׼���˱���
 * �������̣��ȶ��˽��׳ɹ��Ľ��ף�Ȼ��ͨ���������ݿ��ѯ�����з�û�У����Ǻ��Ĵ��ڵı�����
 * ��֯ȡ�������Ķ��˱��ģ����ҵ��ú�����Ӧ�ӿ�
 * @author 
 *
 */
public class NewContBlc_0309fenduizong extends ServiceImpl {
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
	public NewContBlc_0309fenduizong(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 

	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws Exception {
		long mStartMillis = System.currentTimeMillis();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
//		if((typeCode.equals("P53817104"))&&(typeCode!=null)){//��ȫ����
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
				throw new MidplatException("û�ж����ļ���");
			}
			try{
				//���ܱ��ģ����һ�ñ�׼����
				FileUtil fu = new FileUtil(pInXmlDoc);
				cInXmlDoc = fu.fileSecurity();
				cLogger.info("�ɹ������׼���˱���.................");
			}catch(Exception ex){
				throw new MidplatException("��ȡ���������׼���˱��ĳ���");
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
//			cLogger.info("�����׼������ϣ�"+mSaveName);
			JdomUtil.print(cInXmlDoc);
			
			cTranLogDB = insertTranLog(cInXmlDoc);
//			//add by zhj ������Ȩ�� ��Ӵ���   
//			cInXmlDoc = authority(cInXmlDoc);
//			//add by zhj ������Ȩ�� ��Ӵ���end 	
//			//ÿ�ζ���ʱ��ʼ������״̬
//			String tAgentcodeStr = cInXmlDoc.getRootElement().getChild("Header").getChildTextTrim("AgentCode");
			//�������
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
//				throw new MidplatException("�ѳɹ������µ����ˣ������ظ�������");
//			} else if (tExeSQL.mErrors.needDealError()) {
//				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
//			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}			
			
			//�������
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817103")){

				List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
				//�޳��������ݣ��ٰ�������װ����
				for (int i=0;i<tDetail.size();i++) {
					cLogger.info("�������ͣ�"+tDetail.get(i).getChildText("BusiType"));
					if(tDetail.get(i).getChildTextTrim("BusiType").equals("11")){//����
						cSubInXmlEle3.addContent((Element)tDetail.get(i).clone());
					}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("01")){//�µ�
						cSubInXmlEle4.addContent((Element)tDetail.get(i).clone());
					}
				}
				
				try {
					//���°����ͽ�Detail��䵽�ӱ�����ȥ
					System.out.println("=============������װ����=============");
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle3);
					cSubInXmlDoc3 =  (Document)cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle4);
					cSubInXmlDoc4 =  (Document)cInXmlDoc.clone();
				} catch (Exception e) {
					e.printStackTrace();
				}
				JdomUtil.print(cSubInXmlDoc3);
				JdomUtil.print(cSubInXmlDoc4);
				
				String desc="";
//				cSubOutXmlDoc1 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc1);
//				
//				if(cSubInXmlDoc2.getRootElement().getChild("Body").getChildren("Detail")!=null){
//					cSubOutXmlDoc2 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc2);
//				}
				if(cSubInXmlDoc3.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc3 = new CallWebsvcAtomSvc("18").call(cSubInXmlDoc3);
				}
				if(cSubInXmlDoc4.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc4 = new CallWebsvcAtomSvc("6").call(cSubInXmlDoc4);
				}
//				
//				cSubOutXmlDoc1=new Document(tTranData);
				
//				Element tOutHeadEle = cSubOutXmlDoc1.getRootElement().getChild(Head);
//				Element tOutHeadEle2 = cSubOutXmlDoc2.getRootElement().getChild(Head);
				Element tOutHeadEle3 = cSubOutXmlDoc3.getRootElement().getChild(Head);
				Element tOutHeadEle4 = cSubOutXmlDoc4.getRootElement().getChild(Head);
//				if(cSubInXmlDoc1.getRootElement().getChild("Body").getContentSize()!=0){
//					if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
//						cOutXmlDoc=cSubOutXmlDoc1;
////						throw new MidplatException(tOutHeadEle.getChildText(Desc));
//					}
//				}
//				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle2.getChildText(Flag))) {	//����ʧ��
//					cOutXmlDoc=cSubOutXmlDoc2;
////					throw new MidplatException(tOutHeadEle2.getChildText(Desc));
//				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle3.getChildText(Flag))) {	//����ʧ��
					cOutXmlDoc=cSubOutXmlDoc3;
					desc=desc+"���ڶ��ˣ�"+cSubInXmlDoc3.getRootElement().getChild("Head").getChildText("Desc")+";";
//					throw new MidplatException(tOutHeadEle3.getChildText(Desc));
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle4.getChildText(Flag))) {	//����ʧ��
					cOutXmlDoc=cSubOutXmlDoc4;
					desc=desc+"�µ����ˣ�"+cSubInXmlDoc4.getRootElement().getChild("Head").getChildText("Desc")+";";
//					throw new MidplatException(tOutHeadEle3.getChildText(Desc));
				}
				
				if(CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle3.getChildText(Flag))&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle4.getChildText(Flag))){
					cOutXmlDoc=cSubOutXmlDoc3;
				}
				
//				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(cInXmlDoc);
				Element tOutRootEle = cOutXmlDoc.getRootElement();
				Element tOutHeadEle = tOutRootEle.getChild("Head");
				cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText(desc);
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
					throw new MidplatException(tOutHeadEle.getChildText(Desc));
				}
				
			}
			
			//��ȫ����
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817104")){

				List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
				//�޳��������ݣ��ٰ�������װ����
				for (int i=0;i<tDetail.size();i++) {
					cLogger.info("�������ͣ�"+tDetail.get(i).getChildText("BusiType"));
					if(tDetail.get(i).getChildTextTrim("BusiType").equals("09")){//����
						cSubInXmlEle1.addContent((Element)tDetail.get(i).clone());
					}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("10")){//�˱�
						cSubInXmlEle2.addContent((Element)tDetail.get(i).clone());
					}
				}
				
				try {
					//���°����ͽ�Detail��䵽�ӱ�����ȥ
					System.out.println("=============������װ����=============");
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle1);
					cSubInXmlDoc1 = (Document)cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle2);
					cSubInXmlDoc2 =  (Document)cInXmlDoc.clone();

				} catch (Exception e) {
					e.printStackTrace();
				}
				JdomUtil.print(cSubInXmlDoc1);
				JdomUtil.print(cSubInXmlDoc2);
				
				String desc="";
//				cSubOutXmlDoc1 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc1);
				
				if(cSubInXmlDoc2.getRootElement().getChild("Body").getChildren("Detail")!=null){
					cSubOutXmlDoc2 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc2);
				}
				
				
//				���ڶ��˺���û�У��ٽ��ף����سɹ�
				Element tTranData=new Element(TranData);
				
				Element tHead=new Element(Head);
				Element tFlag=new Element(Flag);
				tFlag.setText("0");
				Element tDesc=new Element(Desc);
				tDesc.setText("���ڶ��˳ɹ���");
				Element tBody=new Element(Body);
				
				tTranData.addContent(tHead);
				tTranData.addContent(tBody);
				
				tHead.addContent(tFlag);
				tHead.addContent(tDesc);
				
				cSubOutXmlDoc1=new Document(tTranData);
				
				Element tOutHeadEle = cSubOutXmlDoc1.getRootElement().getChild(Head);
				Element tOutHeadEle2 = cSubOutXmlDoc2.getRootElement().getChild(Head);
				
				if(cSubInXmlDoc1.getRootElement().getChild("Body").getContentSize()!=0){
					if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
						cOutXmlDoc=cSubOutXmlDoc1;
//						throw new MidplatException(tOutHeadEle.getChildText(Desc));
						desc=desc+cSubInXmlDoc1.getRootElement().getChild("Head").getChildText("Desc")+";";
					}
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle2.getChildText(Flag))) {	//����ʧ��
					cOutXmlDoc=cSubOutXmlDoc2;
//					throw new MidplatException(tOutHeadEle2.getChildText(Desc));
					desc=desc+cSubInXmlDoc1.getRootElement().getChild("Head").getChildText("Desc")+";";
				}
				
				if(CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag))&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle2.getChildText(Flag))){
					cOutXmlDoc=cSubOutXmlDoc2;
				}				
				
				cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText(desc);
				if (CodeDef.RCode_ERROR == Integer.parseInt(cOutXmlDoc.getRootElement().getChild("Head").getChildText(Flag))) {
					throw new MidplatException(cOutXmlDoc.getRootElement().getChild("Head").getChildText(Desc));
				}
			}
			
			//��֤����
			if(pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817105")){
				

				
//				���ԣ��ٽ��ף����سɹ�
//				Element tTranData=new Element(TranData);
//				
//				Element tHead=new Element(Head);
//				Element tFlag=new Element(Flag);
//				tFlag.setText("0");
//				Element tDesc=new Element(Desc);
//				tDesc.setText("���׳ɹ�");
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
			//���������ϸ
			saveDetails(cInXmlDoc);
			
 			 		
//			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(cInXmlDoc);

//			//����ʱ���ٽ��ף����سɹ�
//			Element tTranData=new Element(TranData);
//			
//			Element tHead=new Element(Head);
//			Element tFlag=new Element(Flag);
//			tFlag.setText("0");
//			Element tDesc=new Element(Desc);
//			tDesc.setText("���׳ɹ�");
//			Element tBody=new Element(Body);
//			tTranData.addContent(tHead);
//			tTranData.addContent(tBody);
//			tHead.addContent(tFlag);
//			tHead.addContent(tDesc);
//			cOutXmlDoc=new Document(tTranData);
			
			
			
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("Head");
			
			cLogger.info("���˺��ķ��صı��ģ�");
			JdomUtil.print(cOutXmlDoc);
			
			if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null			
				
				Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
				cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
				cTranLogDB.setRText(tHeadEle.getChildText(Desc));
				cTranLogDB.setRCode("0");
				cTranLogDB.setRText("���˳ɹ�");
				cTranLogDB.setBak1("1");//����
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
//			cTranLogDB.setBak1("1");//����
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		
		} 
//		catch (Exception ex) {
//			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
//			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
//			//������־ʧ��ʱcTranLogDB=null			
//			cTranLogDB.setRCode("1");
////			cTranLogDB.setBak1("1");//����
//			cTranLogDB.setRText(ex.getMessage());
//			long tCurMillis = System.currentTimeMillis();
////			cTranLogDB.setContNo(cOutXmlDoc.getRootElement().getChild("response-policy-issue").getChild("policy-print").getChild("policy-info").getChildText("policy-code"));
//			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
//			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
//			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
//			if (!cTranLogDB.update()) {
//				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
//			}
//		}
		
		cLogger.info("Out NewContBlc.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NewContBlc.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		Element mHeadEle = mTranDataEle.getChild(Head);
		String sfuncflag=mHeadEle.getChildText("FuncFlag");
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount) {
			throw new MidplatException("���ܱ�������ϸ����������"+ mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList) {
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
			
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//��Щ���д�
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
//			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
//			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//��Щ���д�
//			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //��Щ���д�
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setBak1(tDetailEle.getChildText("BusiType"));//�ڶ�����ϸ����������01�µ���11���ڣ�09���ڣ�10�˱�
			if("1048".equals(sfuncflag)){//��ȫ��һ�������
				tContBlcDtlSchema.setBak2(tDetailEle.getChildText("NodeNo"));
			}
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem) {
			throw new MidplatException("���ܽ������ϸ�ܽ�����"+ mSumPrem + "!=" + mSumDtlPrem);
		}
		
		/** 
		 * �����з������Ķ�����ϸ�洢��������ϸ��(ContBlcDtl)��
		 */
		cLogger.info("������ϸ����(DtlSet)Ϊ��" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("���������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���������ϸʧ�ܣ�");
		}
		
		cLogger.debug("Out NewContBlc.saveDetails()!");
		return mContBlcDtlSet;
	}
	
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * ���� Ȩ�� ���У�鷽��
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}  
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
		
	}
	
}
