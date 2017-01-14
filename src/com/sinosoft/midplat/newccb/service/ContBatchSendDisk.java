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
		//解密文件标记，如解密正常，启动脚本与核心交互
		boolean boolFlag = false;
		String[] mEnFileList = null;
		if(!"".equals(pFileNum) && pFileNum != null){ 
			try{
				if(Integer.valueOf(pFileNum) > 0){//传入的文件大于0个
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
							cLogger.info("接收文件为："+pEnvFilePath +"    "+ pEnvFileName);
						}else{
							continue;
						}
						pEnvFilePath = pEnvFilePath+"/"+pEnvFileName;
						tBusiDeFilePath = tBusiDeFilePath+"/"+pEnvFileName;
						cLogger.info("密文路径："+pEnvFilePath +"    明文路径："+ tBusiDeFilePath);
						//文件解密
						cLogger.info("文件解密开始...");
						bakFile(tBusiDeFilePath);
						SecAPI.fileUnEnvelop(pLocalID, pRemoteID, pEnvFilePath, tBusiDeFilePath);
						mEnFileList[i] = tBusiDeFilePath;
						
						cLogger.info("文件解密完成...");
						boolFlag = true;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				cLogger.error("文件解密失败........");
			}
		}
		
//		//测试代码
//		boolFlag = true;
//		String[] mEnFileList = new String[1];
//		mEnFileList[0] = "E:/HongK/弘康建行新一代/innostd/AL01100742014121701_RESULT.xml";
		
		if(boolFlag){
			//另起线程与核心交互
			new PayRetServ(mEnFileList , pInXmlDoc).start();
			
			pInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "交易成功");
		}else {
			pInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, "文件解密处理失败");
		}
		
		cLogger.info("Out Get_PayRetServ.service()!");
		return pInXmlDoc;
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = mRootEle.getChild(Head);
		
		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
		String sNodeNo = mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		cLogger.info("authority-->"+sAgentCom);
		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("此网点不存在，请确认！");
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
				System.out.println("文件存在，进行备份："+ mDeFilePath);
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
			throw new MidplatException("插入日志失败！");
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
				//如果发送文件个数不为0
					cLogger.info("开始拼接发送核心报文....");
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
								cLogger.info("存在两个或以上文件的情况，走这里处理...");
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
						cLogger.error("处理回盘文件失败...");
						try {
							throw new MidplatException("处理回盘文件失败");
						} catch (MidplatException e1) {
							e1.printStackTrace();
						}
					}
					
					myTransSeq2 = pInXmlDoc.getRootElement().getChild("InputData").getChild("AgIns_BtchBag_Nm");
					pInXmlDoc.getRootElement().removeChild("InputData");//清除原有InputData
					if(mStdXml != null){
//						JdomUtil.print(pInXmlDoc);
//						JdomUtil.print(mStdXml.getRootElement());
						pInXmlDoc.getRootElement().addContent((Element) mStdXml.getRootElement().clone());
					}else{
						cLogger.debug("处理文件内容有误....");
					}
					
				cLogger.info("完成拼接发送核心报文....");
				cInXmlDoc =  pInXmlDoc;
		     	JdomUtil.print(cInXmlDoc);
			
				cTranLogDB = insertTranLog(cInXmlDoc);  
				//add by zhj 网点与权限 添加代理   
				cInXmlDoc = authority(cInXmlDoc);   
				//add by zhj 网点与权限 添加代理end 
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
					myDealType.setText(ssr.GetText(1, 2).trim().toString().equals("0")? "S":"F");   //获取处理类型结果  0  代扣  1  代付
					myTransCode.setText(ssr.GetText(1, 2).trim().toString().equals("0")? "030002":"030001");
					TransSeq.setText(ssr.GetText(1, 3).trim().toString());   //获取批次号
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
				cLogger.info("发送核心报文："+JdomUtil.toString(cInXmlDoc));
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BatchSendDisk).call(cInXmlDoc);
				
				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("BaseInfo");
				if (CodeDef.RCode_ERROR == Integer.parseInt(tBaseInfoEle.getChildText("ResultCode"))) {	//交易失败
					throw new MidplatException(tBaseInfoEle.getChildText("ResultMsg"));
				}
			} 
			catch (MidplatException ex) {
				cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			} 
			catch (Exception ex) {
				cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			}
			
			if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("BaseInfo");
				cTranLogDB.setRCode(tBaseInfoEle.getChildText("ResultCode"));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(tBaseInfoEle.getChildText("ResultMsg"));
				long tCurMillis = System.currentTimeMillis();
				cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
				if (!cTranLogDB.update()) {
					cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		Get_PayRetServ gp = new Get_PayRetServ(null);
//		String url = "E:/HongK/弘康建行新一代/innostd/回盘本地测试报文.xml";
//		InputStream is = new FileInputStream(url);
//		Document pXml = JdomUtil.build(is);
//		
//		pXml = gp.service(pXml);
//		System.out.println("获得返回报文......");
//		JdomUtil.print(pXml);
		
		String mInputURL = "E:/HongK/弘康建行新一代/innostd/010036_返回报文.xml";
		InputStream is = new FileInputStream(mInputURL);
		Document pXml = JdomUtil.build(is);
		Document mStdXmls = BatchSendDiskToHXInXsl.newInstance().getCache().transform(pXml);
		JdomUtil.print(mStdXmls);
	}
	
}
