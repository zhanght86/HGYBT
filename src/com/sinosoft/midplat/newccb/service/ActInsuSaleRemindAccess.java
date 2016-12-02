package com.sinosoft.midplat.newccb.service;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
	/**
	 * �������ۺ����Ѳ�ѯ
	 * @author lizk
	 *
	 */

public class ActInsuSaleRemindAccess extends ServiceImpl {
	
	private String checkDate = "";
	
	public ActInsuSaleRemindAccess(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ActInsuSaleRemindAccess.service()...");
		cInXmlDoc = pInXmlDoc;
		
		try {
			
			
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			String mBagName = cInXmlDoc.getRootElement().getChild("Body").getChildText("BagName");
			
			checkDate = mBagName.substring(10, 18);
			
			
			//���ؼ���ǰ·�� 
			String localPath = cThisBusiConf.getChildText("LocalDir").replace('\\', '/');
			if (!localPath.endsWith("/")) {
				localPath += '/';}
			
			//����ȡ�ļ�·��
			String ccblocalPath = cThisBusiConf.getChildText("ccbLocalDir").replace('\\', '/');
			if (!ccblocalPath.endsWith("/")) {
				ccblocalPath += '/';}
			
			String tTranCode = cThisBusiConf.getChild("funcFlag").getAttributeValue("outcode");
			System.out.println("����ǰ�ļ�·����" + localPath);
			System.out.println("�����룺" + tTranCode);
		
			//���չ�˾�ڵ�
			String secNodeId = cInXmlDoc.getRootElement().getChild("Body").getChildTextTrim("secNodeId");
			//���нڵ�
			String rmtSecNodeId = cInXmlDoc.getRootElement().getChild("Body").getChildTextTrim("rmtSecNodeId");
			
			Element mFileName = new Element("fileName");
			Element mCcblocalPath = new Element("ccblocalPath");
			Element mCount = new Element("Count");
			
			mCcblocalPath.setText(ccblocalPath);
			
			if(mBagName.substring(mBagName.length()-5, mBagName.length()).equals("00001")){
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_ActInsuSaleRemindAccess).call(cInXmlDoc);

				cLogger.info("���ͺ��Ļ�ȡ�������ۺ�����ȡ��������� ");
				JdomUtil.print(cOutXmlDoc);
				Element tOutRootEle = cOutXmlDoc.getRootElement();
				Element tOutHeadEle = tOutRootEle.getChild(Head);
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
					throw new MidplatException(tOutHeadEle.getChildText(Desc));
				}else{
					String fileName = mBagName + ".xml";
					String filePath = localPath + fileName;
					System.out.println("�ļ�����·����" + filePath);
					mFileName.setText(fileName);
					
					List<Element> tDetail = cOutXmlDoc.getRootElement().getChild("Body").getChildren("Detail");
					try{
						//��֯�����ۺ�����ȡ���ļ�
						isSuccessed(tDetail,localPath);
					}catch(Exception ex){
						throw new MidplatException("��֯���������ļ���"+fileName+"����"+ex.getMessage());
					}
					
					
					
					Document mInXmlDoc = JdomUtil.build(new FileInputStream(filePath),"UTF-8");
					mCount.setText(mInXmlDoc.getRootElement().getChild("Head").getChildText("Rvl_Rcrd_Num")); 
					
					try{
						//���ܣ����ؽڵ㣬�Զ˽ڵ㣬����֮ǰ�ļ����·���� ����֮���ļ�����·��
//						System.out.println("secNodeId ====== "+secNodeId);
//						System.out.println("rmtSecNodeId ====== "+rmtSecNodeId);
//						System.out.println("localPath+fileName ====== "+localPath+fileName);
//						System.out.println("ccblocalPath+fileName ====== "+ccblocalPath+fileName);
//						
						SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, localPath+fileName, ccblocalPath+fileName);
//						
					}catch(Exception ex){
						throw new MidplatException("���ܴ������ۺ�����ȡ���ļ���"+fileName+"����");
					}
				}
				
				
			}else{
				String fileName = mBagName + ".xml";
				String filePath = localPath + fileName;
				System.out.println("�ļ�����·����" + filePath);
				mFileName.setText(fileName);
				File mFile = new File(filePath);
				if(!mFile.exists()){
					throw new MidplatException("δ�ҵ��������ۺ�����ȡ���ļ���"+fileName);
				}
				
				Document mInXmlDoc = JdomUtil.build(new FileInputStream(filePath),"UTF-8");
				mCount.setText(mInXmlDoc.getRootElement().getChild("Head").getChildText("Rvl_Rcrd_Num")); 
				
				try{
					//���ܣ����ؽڵ㣬�Զ˽ڵ㣬����֮ǰ�ļ����·���� ����֮���ļ�����·��
//					System.out.println("secNodeId ====== "+secNodeId);
//					System.out.println("rmtSecNodeId ====== "+rmtSecNodeId);
//					System.out.println("localPath+fileName ====== "+localPath+fileName);
//					System.out.println("ccblocalPath+fileName ====== "+ccblocalPath+fileName);
//					
//					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, localPath+fileName, ccblocalPath+fileName);
				
				}catch(Exception ex){
					throw new MidplatException("���ܴ������ۺ�����ȡ���ļ���"+fileName+"����");
				}
				cOutXmlDoc = cInXmlDoc;
				Element mFlag = new Element(Flag);
				Element mDesc = new Element(Desc);
				mFlag.setText("0");
				mDesc.setText("���׳ɹ���");
				cOutXmlDoc.getRootElement().getChild(Head).addContent(mFlag);
				cOutXmlDoc.getRootElement().getChild(Head).addContent(mDesc);
			}
			
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mFileName);
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mCcblocalPath);
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mCount);
			
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		cLogger.info("������־��"+cTranLogDB);
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			cLogger.info("������־�ɹ���"+cTranLogDB);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out ActInsuSaleRemindAccess.service()!");
		return cOutXmlDoc;
	}
	
	
	private void rollback() {
		cLogger.debug("Into ActInsuSaleRemindAccess.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}
		
		cLogger.debug("Out ActInsuSaleRemindAccess.rollback()!");
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
	
private void isSuccessed(List<Element> list,String mLocalPath)  throws MidplatException, IOException
	
	{
		
		String cardType = "";
		String mSsnType = "";
		String mTranType = "";
		String mRiskCode = "";
		String mBonusType = "";
		Element mDetail;
		
		int rowNum = list.size();  //�ļ���ϸ��
		
		int lastNum = rowNum%5000;
		int fileNum = rowNum/5000; //�ļ���
		
		if(lastNum != 0 ){
			fileNum = fileNum + 1;
		}
		//�����ļ������Ұ���˳����ÿ���ļ���д��5000��������ϸ
		for(int i =0;i< fileNum;i++ ){
			int endNum = 0;
			if((i+1)*5000<rowNum){
				endNum = (i+1)*5000;
			}else{
				endNum = rowNum;
			}
			System.out.println("endNum ========="+endNum);
			ElementLis Root = new ElementLis("Root");
			ElementLis Head = new ElementLis("Head",Root);
			new ElementLis("Rvl_Rcrd_Num",String.valueOf(endNum-i*5000),Head);//ѭ����¼����
			ElementLis Insu_List = new ElementLis("Insu_List",Root);
			
			for(int j=i*5000+1;j<= endNum;j++){
				
				System.out.println("��"+j+"�β�ѯ��");
				
				ElementLis Insu_Detail = new ElementLis("Insu_Detail",Insu_List);
				mDetail = list.get(j-1);
				
				new ElementLis("Ins_Co_ID","010073",Insu_Detail);//���չ�˾���
				mRiskCode = mDetail.getChildText("RiskCode");
				

				new ElementLis("Cvr_ID",mRiskCode,Insu_Detail);//���ֱ��
				new ElementLis("InsPolcy_No",mDetail.getChildText("ContNo"),Insu_Detail);//��������
			
				
				new ElementLis("AgIns_Rmndr_TpCd",mDetail.getChildText("AlertType"),Insu_Detail);//�������������ʹ���
				
				
			    new ElementLis("Plchd_Nm",mDetail.getChildText("AppntName"),Insu_Detail);//Ͷ��������
			    
			    mSsnType = mDetail.getChildText("IDType");
			    	if(mSsnType.equals("0")){
						cardType = "1010";//���֤
					}
					if(mSsnType.equals("2")){
						cardType = "1022";//����֤
					}
					if(mSsnType.equals("D")){
						cardType = "1032";//����֤ 
					}
					if(mSsnType.equals("A")){
						cardType = "1021";//��ž�ʿ��֤
					}
					if(mSsnType.equals("4")){
						cardType = "1040";//���ڲ�
					}
					if(mSsnType.equals("B")){
						cardType = "1080";//(�۰�)����֤��ͨ��֤
					}
					if(mSsnType.equals("1")){
						cardType = "1050";//����
					}
					if(mSsnType.equals("5")){
						cardType = "1060";//ѧ��֤
					}
					if(mSsnType.equals("3")){
						cardType = "1100";//����
					}
					if(mSsnType.equals("6")){
						cardType = "1999";//��������֤��
					}
					if(mSsnType.equals("C")){
						cardType = "1011";//��ʱ�������֤
					}
					if(mSsnType.equals("E")){
						cardType = "1160";//̨��������֤ ̨��֤
					}
					
				new ElementLis("Plchd_Crdt_TpCd",cardType,Insu_Detail);//Ͷ����֤�����ʹ���
				new ElementLis("Plchd_Crdt_No",mDetail.getChildText("IDNo"),Insu_Detail);//Ͷ����֤������
			    
			
			 
				new ElementLis("TXN_DT",DateUtil.date10to8(mDetail.getChildText("TranDate")),Insu_Detail);//��������
				
				
				new ElementLis("InsPrem_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("Prem")),Insu_Detail);//���ѽ��
				new ElementLis("CnclIns_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("EdorCTPrem ")),Insu_Detail);//�˱���� 

				new ElementLis("Rnew_PyF_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("RecvAmount")),Insu_Detail);//���ڽɷѽ��
				new ElementLis("Rnew_Pbl_Dt",DateUtil.date10to8(mDetail.getChildText("RecvDate")),Insu_Detail);//����Ӧ������

				new ElementLis("InsPolcy_ExDat",DateUtil.date10to8(mDetail.getChildText("InsuEndDate")),Insu_Detail);//������������
		
				new ElementLis("CrnPrd_XtDvdAmt",NumberUtil.yuanToDouble(mDetail.getChildText("CurrentAmt")),Insu_Detail);//���ں������
				new ElementLis("Acm_XtDvdAmt",NumberUtil.yuanToDouble(mDetail.getChildText("BonusAmt")),Insu_Detail);//�ۻ��������
				
				/**
				 * 0	ֱ�Ӹ���
				 * 1	�ֽ�����
				 * 2	�ۼ���Ϣ
				 * 3	�����
				 * 4	ת�������˻�
				 */
				
				if("1".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "2";//�ۼ���Ϣ
				}
				if("2".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "0";//ֱ�Ӹ���
				}
				if("3".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "1";//�ֽ�����
				}
			
				new ElementLis("XtraDvdn_Pcsg_MtdCd",mBonusType,Insu_Detail);//��������ʽ����
				
				new ElementLis("Plchd_AccNo",mDetail.getChildText("AccNo "),Insu_Detail);//Ͷ�����˺�
				new ElementLis("Ins_PD_TpCd",mTranType,Insu_Detail);//���ղ�Ʒ���ʹ���
				
			}
			
			Document returnStd = new Document(Root);
			cLogger.info("��֯�ı���=============");
			JdomUtil.print(returnStd);
			//�ڽ�����д�뵽�ļ���ʱ��Ҫ����ԭ�����ݣ�����ʹ��UFT-8����
			FileOutputStream fOut = new FileOutputStream(mLocalPath+ getFileName(i),false);
			JdomUtil.output(returnStd,fOut,"UTF-8");
			fOut.flush();
			fOut.close();
		
		}		
	}

	private String getFileName(int index){
		index = index + 1;
		String indexStr = NumberUtil.fillWith0(index, 5);
	//	String checkdate = DateUtil.date10to8(checkDate);
		String fileName = "P5381B223_"+checkDate+"_010072_"+indexStr+".xml";
		return fileName;
	}
}
