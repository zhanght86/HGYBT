package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.format.BatchSendDiskInXsl;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatchSendDisk extends ServiceImpl {
	
	private Element myTransSeq2;
	private Element mBusiConf = null;
	public ContBatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
		mBusiConf = pThisBusiConf;
	} 
	
	/**
	 * 业务处理
	 * @param pInXmlDoc 标准输入报文 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Document service(Document pInXmlDoc) {
		
		cLogger.info("Into Get_PayRetServ.service()...");
		Element pBody = pInXmlDoc.getRootElement().getChild("TX_BODY");
		String pFileNum = pBody.getChild("COMMON").getChild("FILE_LIST_PACK").getChildText("FILE_NUM").trim();
		cLogger.info(pFileNum);
		JdomUtil.print(pInXmlDoc);
		//解密文件标记，如解密正常，启动脚本与核心交互
		boolean boolFlag = false;
		String[] mEnFileList = null;
		if(!"".equals(pFileNum) && pFileNum != null){ 
			try{
				if(Integer.valueOf(pFileNum) > 0){//传入的文件大于0个
					mEnFileList = new String[Integer.valueOf(pFileNum)];
					String pLocalID = pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("LocalID");
					String pRemoteID = pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("remoteID");
					
					String tBusiDeFilePath = mBusiConf.getChildText("savePath");
					List<Element> pFileList = pBody.getChild("COMMON").getChild("FILE_LIST_PACK").getChildren("FILE_INFO");
					for (int i = 0; i < pFileList.size(); i++) {
						Element pFileInfo = pFileList.get(i);
						String pEnvFilePath = pFileInfo.getChildText("FILE_PATH").trim();
						String pEnvFileName = pFileInfo.getChildText("FILE_NAME").trim();
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
//						bakFile(tBusiDeFilePath);
//						SecAPI.fileUnEnvelop(pLocalID, pRemoteID, pEnvFilePath, tBusiDeFilePath);
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
		 
		/*cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		cLogger.info("authority-->"+sAgentCom);
		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("此网点不存在，请确认！");
		}   
		mAgentCom.setText(sAgentCom);*/
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
		Element mHeadEle = mTranDataEle.getChild("Head");
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mTranDataEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCBIns_ID"));
		mTranLogDB.setTranNo(mTranDataEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
		mTranLogDB.setOperator(mTranDataEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCB_EmpID"));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
//		mTranLogDB.setTranDate(DateUtil.date10to8(mBaseInfoEle.getChildText("TransDate")));
//		mTranLogDB.setTranTime(DateUtil.time8to6(mBaseInfoEle.getChildText("TransTime")));
	
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
			String xslSource =ContBatchSendDisk.class.getResource("/").getPath()+"com/sinosoft/midplat/newccb/format/BatchSendDiskIn.xsl";
			String outXml=null;
			try {
				//如果发送文件个数不为0
					cLogger.info("开始拼接发送核心报文....");
					Document mStdXml =  null;
					try{
						for (int i = 0; i < mEnFileList.length; i++) {
							cLogger.info( "88888888888"+mEnFileList.length);
							InputStream fileIs= new FileInputStream(mEnFileList[i]);
							Document xxInputFileDoc = JdomUtil.build(fileIs , "UTF-8");
							String filename=mEnFileList[i].substring(mEnFileList[i].lastIndexOf("/")+1, mEnFileList[i].length());
							String sign=filename.substring(2, 3);
							if(sign.equals("0"))
								sign="S";
							else if(sign.equals("1"))
								sign="F";
							String submitDate=filename.substring(9, 17);
							String batchNo=filename.substring(17, 19);
							if(batchNo.length()<5){
								byte[] bs=new byte[5-(batchNo.length())];
								for (int j = 0; j < bs.length; j++) {
									batchNo=bs[i]+batchNo;
								}
							}
							outXml="D:/home/ap/fserver2/snd/"+"0000000001_"+sign+"02"+submitDate+"_"+batchNo+".txt";
//							if(i == 0){
//								transferXml(mEnFileList[i],"F:/MyEclipse/workspace/HGLIFE/src/com/sinosoft/midplat/newccb/format/BatchSendDiskIn.xsl" , "D:/home/ap/fserver2/snd/0000000001_F020010020090729_00001.txt");
//								mStdXml = BatchSendDiskInXsl.newInstance().getCache().transform(xxInputFileDoc);
//								JdomUtil.print(mStdXml);
//								bakFile(mDeFilePath);
//								save(mStdXml, "0000000001_F020010020090729_00001.txt");
								transferXml(mEnFileList[i], xslSource, outXml);
//							}else if(i > 0){
////								cLogger.info("存在两个或以上文件的情况，走这里处理...");
////								Document mStdXmls = BatchSendDiskInXsl.newInstance().getCache().transform(xxInputFileDoc);
////								List<Element> rowList = mStdXmls.getRootElement().getChild("BANKDATA").getChildren("Row");
////								Element xxBANKDATA = mStdXml.getRootElement().getChild("BANKDATA");
////								for (int j = 0; j < rowList.size(); j++) {
////									xxBANKDATA.addContent(rowList.get(j));
////								}
//							}
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
					
					/*myTransSeq2 = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_BtchBag_Nm");
					if(mStdXml != null){
						pInXmlDoc.getRootElement().addContent((Element) mStdXml.getRootElement().clone());
					}else{
						cLogger.debug("处理文件内容有误....");
					}*/
					
				cLogger.info("完成拼接发送核心报文....");
				cInXmlDoc =  pInXmlDoc;
		     	JdomUtil.print(cInXmlDoc);
			
				cTranLogDB = insertTranLog(cInXmlDoc);  
				//add by zhj 网点与权限 添加代理   
				cInXmlDoc = authority(cInXmlDoc);   
				//add by zhj 网点与权限 添加代理end 
		     	/*Element cBaseInfo = cInXmlDoc.getRootElement().getChild("BaseInfo");
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
				}*/
				
				Element myPayCode;
				long sum=0;
				int count=1;
				
				/*for(Element myRows :Rows ){
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
				}*/
				/*TransSeq.setText(ssr.GetText(1, 3).trim().toString()); 
				CountNum.setText(Rows.size()+"");
				SumMoney.setText(sum+"");*/
				
				
//				cInXmlDoc.getRootElement().removeChild("Head");
//				cLogger.info("发送核心报文："+JdomUtil.toString(cInXmlDoc));
//				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BatchSendDisk).call(cInXmlDoc);
//				cOutXmlDoc=cInXmlDoc;
//				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("Head");
//				if (CodeDef.RCode_ERROR == Integer.parseInt(tBaseInfoEle.getChildText("Flag"))) {	//交易失败
//					throw new MidplatException(tBaseInfoEle.getChildText("Desc"));
//				}
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
//				Element tBaseInfoEle = cOutXmlDoc.getRootElement().getChild("Head");
//				cTranLogDB.setRCode(tBaseInfoEle.getChildText("Flag"));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
//				cTranLogDB.setRText(tBaseInfoEle.getChildText("Desc"));
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
	
	/**
	 * 创建文件输出流，将XML文档输出到文件输出流，关闭输出流 
	 */
	public static void save(Document pXmlDoc,String pName){
		String cSavePath;//保存路径
		//获取中间平台配置文件保存路径[盘符]
//		String tSavePath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
		String tSavePath="D:/home/ap/fserver2/snd";
		//保存路径为空
		if (null==tSavePath || "".equals(tSavePath)) {
			//使用
			tSavePath = SysInfo.getRealPath("..");
		}
		tSavePath = tSavePath.replace('\\', '/');
		if (!tSavePath.endsWith("/")) {
			tSavePath += '/';
		}
		File tFile = new File(tSavePath);
		if (tFile.exists() && tFile.isDirectory()) {
			cSavePath = tSavePath;
		} else {
			cSavePath = null;
		}
		StringBuilder mFilePath = new StringBuilder(cSavePath);
		File mFileDir = new File(mFilePath.toString());
		//此抽象路径名表示的文件或目录不存在
		if (!mFileDir.exists()) {
			//创建此抽象路径名指定的目录，包括创建必需但不存在的父目录
			mFileDir.mkdirs();
			//此抽象路径名表示的文件或目录还是不存在[提示错误消息]
			if (!mFileDir.exists()) {
				//目录不存在，且尝试创建失败！此抽象路径名转换为一个路径名字符串
				//强制退出方法
				return;
			}
		}
		try {
			//创建一个向具有指定名称的文件中写入数据的输出文件流
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			//将XML文档输出到文件输出流
			JdomUtil.output(pXmlDoc, tFos);
			//关闭输出流 
			tFos.close();
		} catch (IOException ex) {
			//保存文件失败！[异常对象]
//			cLogger.error("保存文件失败！", ex);
		}
	}
	
	public static void transferXml(String inXmlSource, String xslSource,
			String outXml) throws TransformerException {

		TransformerFactory tFactory = TransformerFactory.newInstance();

		StreamSource source = new StreamSource(new File(xslSource));

		Transformer tx = tFactory.newTransformer(source);

		Properties properties = tx.getOutputProperties();
		properties.setProperty(OutputKeys.ENCODING, "GBK");
		properties.setProperty(OutputKeys.METHOD, "xml");
		tx.setOutputProperties(properties);

		StreamSource xmlSource = new StreamSource(new File(inXmlSource));
		File targetFile = new File(outXml);
		StreamResult result = new StreamResult(targetFile);

		tx.transform(xmlSource, result);

	}
	
	public static void main(String[] args) throws Exception {
		
		ContBatchSendDisk gp = new ContBatchSendDisk(null);
		String url = "D:/task/20170116/newccb/1059/03_108011rv11484294694478099_P53818105_20170113_160512_.xml";
		InputStream is = new FileInputStream(url);
		Document pXml = JdomUtil.build(is);
//		
		pXml = gp.service(pXml);
		System.out.println("获得返回报文......");
		JdomUtil.print(pXml);
		
		/*String mInputURL = "D:/task/20170114/newccb/transfer_test/AL03100192017011101_RESULT.XML";
		InputStream is = new FileInputStream(mInputURL);
		Document pXml = JdomUtil.build(is);
		Document mStdXmls = BatchSendDiskInXsl.newInstance().getCache().transform(pXml);
		JdomUtil.print(mStdXmls);*/
	}
	
}
