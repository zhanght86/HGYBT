package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.format.BatchSendDiskToHXInXsl;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class ContBatchSendDisk extends ServiceImpl {
	
	private Element myTransSeq2;
	private Element mBusiConf = null;
	public ContBatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
		mBusiConf = pThisBusiConf;
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public Document service(Document pInXmlDoc) {
		
		cLogger.info("Into Get_PayRetServ.service()...");
		Element pInputData = pInXmlDoc.getRootElement().getChild("InputData");
		String pFileNum = pInputData.getChildText("FileNum").trim();
		cLogger.info(pFileNum);
		JdomUtil.print(pInXmlDoc);
		//�����ļ���ǣ�����������������ű�����Ľ���
		boolean boolFlag = false;
		String[] mEnFileList = null;
		if(!"".equals(pFileNum) && pFileNum != null){ 
			try{
				if(Integer.valueOf(pFileNum) > 0){//������ļ�����0��
					mEnFileList = new String[Integer.valueOf(pFileNum)];
					String pLocalID = pInXmlDoc.getRootElement().getChild("InputData").getChildText("LocalID");
					String pRemoteID = pInXmlDoc.getRootElement().getChild("InputData").getChildText("RemoteID");
					
					String tBusiDeFilePath = mBusiConf.getChildText("savePath");
					List<Element> pFileList = pInputData.getChild("Files").getChildren("File");
					for (int i = 0; i < pFileList.size(); i++) {
						Element pFile = pFileList.get(i);
						String pEnvFilePath = pFile.getChildText("FilePath").trim();
						String pEnvFileName = pFile.getChildText("FileName").trim();
						if(!"".equals(pEnvFilePath) && !"".equals(pEnvFileName)){
							cLogger.info("�����ļ�Ϊ��"+pEnvFilePath +"    "+ pEnvFileName);
						}else{
							continue;
						}
						pEnvFilePath = pEnvFilePath+"/"+pEnvFileName;
						tBusiDeFilePath = tBusiDeFilePath+"/"+pEnvFileName;
						cLogger.info("����·����"+pEnvFilePath +"    ����·����"+ tBusiDeFilePath);
						//�ļ�����
						cLogger.info("�ļ����ܿ�ʼ...");
						bakFile(tBusiDeFilePath);
						SecAPI.fileUnEnvelop(pLocalID, pRemoteID, pEnvFilePath, tBusiDeFilePath);
						mEnFileList[i] = tBusiDeFilePath;
						
						cLogger.info("�ļ��������...");
						boolFlag = true;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				cLogger.error("�ļ�����ʧ��........");
			}
		}
		
//		//���Դ���
//		boolFlag = true;
//		String[] mEnFileList = new String[1];
//		mEnFileList[0] = "E:/HongK/�뿵������һ��/innostd/AL01100742014121701_RESULT.xml";
		
		if(boolFlag){
			//�����߳�����Ľ���
			new PayRetServ(mEnFileList , pInXmlDoc).start();
			
			pInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "���׳ɹ�");
		}else {
			pInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, "�ļ����ܴ���ʧ��");
		}
		
		cLogger.info("Out Get_PayRetServ.service()!");
		return pInXmlDoc;
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
		Element mHeadEle = mRootEle.getChild(Head);
		
		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
		String sNodeNo = mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		cLogger.info("authority-->"+sAgentCom);
		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}   
		mAgentCom.setText(sAgentCom);
		return mInXmlDoc;
		
	}
	
	public void bakFile(String mDeFilePath){
		try{
			File file = new File(mDeFilePath);
			InputStream mIs = null;
			OutputStream mOs = null;
			if(file.exists()){
				System.out.println("�ļ����ڣ����б��ݣ�"+ mDeFilePath);
				mDeFilePath = mDeFilePath + ".bak_" + new SimpleDateFormat("HHmmss").format(new Date());
				File newFile = new File(mDeFilePath);
				newFile.createNewFile();
				
				mIs = new FileInputStream(file);
				byte[] mInBytes = IOTrans.toBytes(mIs);
				mOs = new FileOutputStream(newFile);
				mOs.write(mInBytes);
				file.delete();
			}
			mOs.close();
			mIs.close();
		}catch(Exception e){
			e.getStackTrace();
		}
		
	}
	
	@Override
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
		Element mInputDataEle = mTranDataEle.getChild("InputData");
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(myTransSeq2.getText().trim().toString());
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(DateUtil.date10to8(mBaseInfoEle.getChildText("TransDate")));
		mTranLogDB.setTranTime(DateUtil.time8to6(mBaseInfoEle.getChildText("TransTime")));
	
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert()) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
		
	}
	
	class PayRetServ extends Thread{
		
		private String[] mEnFileList = null;
		private Document pInXmlDoc = null;
		public PayRetServ(String[] xEnFileList , Document xInXmlDoc){
			mEnFileList = xEnFileList;
			pInXmlDoc = xInXmlDoc;
		}
		
		@Override
		public void run(){
			long mStartMillis = System.currentTimeMillis();
			try {
				//��������ļ�������Ϊ0
					cLogger.info("��ʼƴ�ӷ��ͺ��ı���....");
					Document mStdXml =  null;
					try{
						for (int i = 0; i < mEnFileList.length; i++) {
							cLogger.info( "88888888888"+mEnFileList.length);
							InputStream fileIs = new FileInputStream(mEnFileList[i]);
							Document xxInputFileDoc = JdomUtil.build(fileIs , "UTF-8");
							if(i == 0){
								mStdXml = BatchSendDiskToHXInXsl.newInstance().getCache().transform(xxInputFileDoc);
								JdomUtil.print(mStdXml);
							}else if(i > 0){
								cLogger.info("���������������ļ�������������ﴦ��...");
								Document mStdXmls = BatchSendDiskToHXInXsl.newInstance().getCache().transform(xxInputFileDoc);
								List<Element> rowList = mStdXmls.getRootElement().getChild("BANKDATA").getChildren("Row");
								Element xxBANKDATA = mStdXml.getRootElement().getChild("BANKDATA");
								for (int j = 0; j < rowList.size(); j++) {
									xxBANKDATA.addContent(rowList.get(j));
								}
							}
							fileIs.close();
						}
					}catch(Exception e) {
						e.getStackTrace();
						cLogger.error("��������ļ�ʧ��...");
						try {
							throw new MidplatException("��������ļ�ʧ��");
						} catch (MidplatException e1) {
							e1.printStackTrace();
						}
					}
					
					myTransSeq2 = pInXmlDoc.getRootElement().getChild("InputData").getChild("AgIns_BtchBag_Nm");
					pInXmlDoc.getRootElement().removeChild("InputData");//���ԭ��InputData
					if(mStdXml != null){
//						JdomUtil.print(pInXmlDoc);
//						JdomUtil.print(mStdXml.getRootElement());
						pInXmlDoc.getRootElement().addContent((Element) mStdXml.getRootElement().clone());
					}else{
						cLogger.debug("�����ļ���������....");
					}
					
				cLogger.info("���ƴ�ӷ��ͺ��ı���....");
				cInXmlDoc =  pInXmlDoc;
		     	JdomUtil.print(cInXmlDoc);
			
				cTranLogDB = insertTranLog(cInXmlDoc);  
				//add by zhj ������Ȩ�� ��Ӵ���   
				cInXmlDoc = authority(cInXmlDoc);   
				//add by zhj ������Ȩ�� ��Ӵ���end 
		     	Element cBaseInfo = cInXmlDoc.getRootElement().getChild("BaseInfo");
		     	Element cInputData = cInXmlDoc.getRootElement().getChild("InputData");
		     	Element TransSeq = cBaseInfo.getChild("TransSeq");
		     	Element SumMoney = cInputData.getChild("BANKDATA").getChild("content").getChild("SumMoney");
		     	Element CountNum =  cInputData.getChild("BANKDATA").getChild("content").getChild("CountNum");
		     	Element myTransCode = cBaseInfo.getChild("TransCode");
		     	Element myDealType =   cInputData.getChild("BANKDATA").getChild("content").getChild("DealType");
		     	List<Element> Rows = cInputData.getChild("BANKDATA").getChildren("Row");
		     	Element mySerialNo = new Element("mySerialNo");
		     	String sql = "select BankCode,DealType,SerialNo from Serial where FileName='"+myTransSeq2.getText()+"'";
				 cLogger.info("#########################"+sql);
				String myPolNo="";
				SSRS ssr = new ExeSQL().execSQL(sql);  
				if(ssr.MaxRow > 0){
					for(Element myRow :Rows){
						myRow.getChild("BankCode").setText(ssr.GetText(1, 1).trim().toString());
					}
					myDealType.setText(ssr.GetText(1, 2).trim().toString().equals("0")? "S":"F");   //��ȡ�������ͽ��  0  ����  1  ����
					myTransCode.setText(ssr.GetText(1, 2).trim().toString().equals("0")? "030002":"030001");
					TransSeq.setText(ssr.GetText(1, 3).trim().toString());   //��ȡ���κ�
				}
				
				Element myPayCode;
				long sum=0;
				int count=1;
				
				for(Element myRows :Rows ){
					sum += Double.parseDouble(myRows.getChildText("PayMoney"));
					 myPolNo= myRows.getChildText("PolNo");
					Element mySeqNo= myRows.getChild("SeqNo");
					int tempSeqNo = Integer.parseInt(mySeqNo.getText());
					if(tempSeqNo>=1 && tempSeqNo<=9){
						mySeqNo.setText("000"+tempSeqNo);
					}
					else if(tempSeqNo>=10 && tempSeqNo<=99){
						mySeqNo.setText("00"+tempSeqNo);
					}
					else if(tempSeqNo>=100 && tempSeqNo<=999){
						mySeqNo.setText("0"+tempSeqNo);
					}
					else if(tempSeqNo>=1000 && tempSeqNo<=9999){
						mySeqNo.setText(""+tempSeqNo);
					}
					 myPayCode = myRows.getChild("PayCode");
					 
					 String sql3 = "select BankCode , PayCode  from Tran_Dtl  where SN="+"'"+mySeqNo.getText()+"' and PolNo = '"+myPolNo+"'";
					 ExeSQL exe = new  ExeSQL();
					 SSRS ss = exe.execSQL(sql3);
					 
					 if(ss.MaxRow>=1){
						 myRows.getChild("BankCode").setText(ss.GetText(1, 1).trim().toString());
						 myPayCode.setText(ss.GetText(1, 2).toString());
					 }
					 count++;
				}
				TransSeq.setText(ssr.GetText(1, 3).trim().toString()); 
				CountNum.setText(Rows.size()+"");
				SumMoney.setText(sum+"");
				
				
				cInXmlDoc.getRootElement().removeChild("Head");
				cLogger.info("���ͺ��ı��ģ�"+JdomUtil.toString(cInXmlDoc));
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BatchSendDisk).call(cInXmlDoc);
				
				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("BaseInfo");
				if (CodeDef.RCode_ERROR == Integer.parseInt(tBaseInfoEle.getChildText("ResultCode"))) {	//����ʧ��
					throw new MidplatException(tBaseInfoEle.getChildText("ResultMsg"));
				}
			} 
			catch (MidplatException ex) {
				cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			} 
			catch (Exception ex) {
				cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			}
			
			if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("BaseInfo");
				cTranLogDB.setRCode(tBaseInfoEle.getChildText("ResultCode"));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				cTranLogDB.setRText(tBaseInfoEle.getChildText("ResultMsg"));
				long tCurMillis = System.currentTimeMillis();
				cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
				if (!cTranLogDB.update()) {
					cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		Get_PayRetServ gp = new Get_PayRetServ(null);
//		String url = "E:/HongK/�뿵������һ��/innostd/���̱��ز��Ա���.xml";
//		InputStream is = new FileInputStream(url);
//		Document pXml = JdomUtil.build(is);
//		
//		pXml = gp.service(pXml);
//		System.out.println("��÷��ر���......");
//		JdomUtil.print(pXml);
		
		String mInputURL = "E:/HongK/�뿵������һ��/innostd/010036_���ر���.xml";
		InputStream is = new FileInputStream(mInputURL);
		Document pXml = JdomUtil.build(is);
		Document mStdXmls = BatchSendDiskToHXInXsl.newInstance().getCache().transform(pXml);
		JdomUtil.print(mStdXmls);
	}
	
}
