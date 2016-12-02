/**
 * ¼���˱�+�շ�ǩ������Թ�������Լ
 */

package com.sinosoft.midplat.service;


import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class Trail extends ServiceImpl {
	public Trail(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {

		//mStartMillis ����Trail��ʼʱ��
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into Trail.service()...");
		cInXmlDoc = pInXmlDoc;  
		Element mHeadEle = (Element) pInXmlDoc.getRootElement().getChild(Head);
		String mTranCom = mHeadEle.getChildText(TranCom);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(0)
		.append("_inSvc.xml");			
		SaveMessage.save(pInXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);		   
		try {					
		cLogger.info("Into Trail.service()...-->authority(cInXmlDoc)������Ȩ�� ��Ӵ���");	
		//add by zhj ������Ȩ�� ��Ӵ���
		cInXmlDoc = authority(cInXmlDoc); 	
		//add by zhj ������Ȩ�� ��Ӵ���end 			
		 	
		cLogger.info("Into Trail.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");	
		AuthorityCheck authorityCheck = new AuthorityCheck();
		if(!authorityCheck.submitData(mHeadEle)){
			throw new MidplatException("��������Ȩ�ޣ�");
		} 
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			int tLockTime = 300;	
			try { 
				cLogger.info("-----���õ�����ʱ��Ϊ:"+Integer.parseInt(cThisBusiConf.getChildText(locktime)));
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			} 			

			cLogger.info("Into Trail.service()...-->RuleParser().check(cInXmlDoc)У��");
			long mStartRuleParser = System.currentTimeMillis();
			new RuleParser().check(cInXmlDoc);
			long mUsedRuleParser = (System.currentTimeMillis() - mStartRuleParser);
			cLogger.info("----------Timekeeping---------->RuleParser().check(cInXmlDoc)У��ʱ��Ϊ:"+String.valueOf(mUsedRuleParser));			
			//add by zhanghj   
			long mStartYBTDataVerification = System.currentTimeMillis();
			YBTDataVerification verification = new YBTDataVerification();
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
			if(GradeFlag==false){
				throw new MidplatException("ͬһ����˳������ݶ�֮�Ͳ�����1����ȷ��");
			}
			boolean digitFlag = verification.digitBnfVerification(cInXmlDoc);
			if(digitFlag==false){ 
				throw new MidplatException("����˳�������ţ���ȷ��");
			}  
			long mUsedYBTDataVerification = (System.currentTimeMillis() - mStartYBTDataVerification);
			cLogger.info("----------Timekeeping---------->YBTDataVerificationУ��ʱ��Ϊ:"+String.valueOf(mUsedYBTDataVerification));			
			//add end 			   						 
			String tRiskCode = cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChildText("MainRiskCode");
			if("313040".equals(tRiskCode)){
			calculatePrem(cInXmlDoc); 
			}else{
				throw new MidplatException("�˲�Ʒ��������");
			}
			Element mFlag = new Element(Flag);
			mFlag.addContent("0");
			
			Element mDesc = new Element(Desc);
			mDesc.addContent("���׳ɹ�");
			
			Element mHead = new Element(Head);
			mHead.addContent(mFlag);
			mHead.addContent(mDesc);
			cOutXmlDoc = new Document();
			Element tTranData = new Element("TranData");
			tTranData.addContent(mHead);
			tTranData.addContent((Element)cInXmlDoc.getRootElement().getChild(Body).clone());
			cOutXmlDoc.addContent(tTranData);
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		}				
		
		cLogger.info("Out Trail.service()!");
		return cOutXmlDoc;
	}
	
	private boolean calculatePrem(Document cInXmlDoc) throws MidplatException {
		cLogger.debug("Into Trail.calculatePrem()...");
		double tAmnt = Double.parseDouble(NumberUtil.fenToYuan(cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChildText("Amnt")));
		
	    String tBirthDay = cInXmlDoc.getRootElement().getChild("Body").getChild("Insured").getChildText("Birthday");
	    int tAppAge = DateUtil.getAge(tBirthDay);
		String tsql = "select "+tAmnt+"/1000*prem From rt_313040 where "+tAppAge+" Between age1 and age2";
//		String tPrem = String.valueOf((Double.parseDouble(new ExeSQL().getOneValue(tsql)))*100.00);
		String tPrem = new ExeSQL().getOneValue(tsql);
		cLogger.debug("���"+tAmnt);
		cLogger.debug("���������գ�"+tBirthDay);
		cLogger.debug("���������䣺"+tAppAge);
		cLogger.debug("������ı��ѣ�"+tPrem);
		if("".equals(tPrem)||tPrem == null){
			throw new MidplatException("���㱣��ʧ�ܣ����ܱ�����Ͷ����������");
		}
		cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText(tPrem);
		cLogger.debug("Out Trail.getContDB()!");
		return true;
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
	public static void main(String[] args) throws Exception {
		String mInFilePath = "D:/instd.xml";
		mInFilePath="F:/MyEclipse/workspace/HGLIFE/WebRoot/WEB-INF/conf/rule.xml";
		String mOutFilePath = "D:/trailOut.xml";
		mOutFilePath="D:/task/20161121/P53819113Out.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		new RuleParser().check(mInXmlDoc);
		System.out.println("out");
	}
	
}
