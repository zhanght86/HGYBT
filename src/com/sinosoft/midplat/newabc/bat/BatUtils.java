package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BatUtils {
	protected static final Logger cLogger = Logger.getLogger(BatUtils.class);
	protected static Element cConfigEle;
	private static String cCurDate = "";
	
	/** ABCSocketIp 农行交易ip */
	private static String ABCSocketIp = "";

	/** ABCSocketPort 农行交易端口 */
	private static int ABCSocketPort = 0;
	/** 标识是传送方式为 上传*/
	private static final String UP = "0";
	/** 标识是传送方式为 下载*/
	private static final String DOWN = "1";
	
	/** 标识是文件类型为 证书文件*/
	private static final String CRTFILE = "01";	
	/**
	 * 获取配置信息
	 * wz 2014-7-26 16:39:28
	 * funcFlag 交易类型
	 * @return 返回配置信息的Element
	 */
	public static Element getConfigEle (String funcFlag){
		if("".equals(cCurDate)){
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		try{
//			InputStream mIs = new FileInputStream(mConfPath);
			Document mInXmlDoc =  BatConf.newInstance().getConf();
			String elementUrl = "/batchs/batch[funcFlag = '"+ funcFlag +"']";
			cConfigEle = (Element)org.jdom.xpath.XPath.selectSingleNode(mInXmlDoc, elementUrl);
			System.out.println("quzhi:"+cConfigEle.getChildText("startTime"));
//			mIs.close();
		}catch(Exception e){ 
			cLogger.info("读取配置文件异常，请检查路径是否正确及文件是否存在......");
			e.getStackTrace();
		}
		
		return cConfigEle;
	}
	
	public boolean downLoadFile(String fileName,String fileType,String funcFlag,String cCurDate){
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0",cCurDate);  //得到加密后的报文体信息
		byte[] headerStr = getHeader(bodyStr.length); //得到报文请求头
		return sendData(headerStr,bodyStr,DOWN,null,fileName,funcFlag);
	} 
	
	/**
	 * 获取报文体
	 * @param transFlag  交易类型  
	 * @param fileName  文件名称
	 * @param fileData 文件数据
	 * @param fileType 文件类型 01  证书文件  02 对账文件
	 * @return
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen,String mTranDate) {
		
		Date date = new Date();
		String cCurDate = new SimpleDateFormat("yyyyMMdd").format(date);
		String cCurTime = new SimpleDateFormat("HHmmss").format(date);
		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
//		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date().getTime() - 86400000L);
		try {
			String tTransCode = "04" + cCurDate + cCurTime;//获取交易流水号
			if(transFlag.equals(DOWN)){
				FileLen = "00000000";
			}else{
				for (int i = FileLen.length(); i < 8; i++)
					FileLen = "0" + FileLen;
			}
			
			if(fileType.equals(CRTFILE)){
				fileName = "";
			}
			String xmlStr = "<ABCB2I><Header><SerialNo></SerialNo><InsuSerial>"+tTransCode+"</InsuSerial><TransDate>"+ mTranDate +"</TransDate>" +
					"<TransTime>"+ cCurTime +"</TransTime><BankCode>03</BankCode><CorpNo>1147</CorpNo><TransCode>1017</TransCode>" +
							"<TransSide>0</TransSide><EntrustWay></EntrustWay><ProvCode></ProvCode><BranchNo></BranchNo></Header>" +
							"<App><Req><TransFlag>"+transFlag+"</TransFlag><FileType>"+fileType+"</FileType><FileName>"+fileName+"</FileName>" +
									"<FileLen>"+FileLen+"</FileLen><FileTimeStamp>"+ cAllCurDate +"</FileTimeStamp></Req></App></ABCB2I>";
			cLogger.info("向银行发送文件上传下载交易报文体："+xmlStr);
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
			
				while ((a = input.read()) != -1) {
					baos.write(a);
				}
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	protected static Document parse(String nFileURL) throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
	    String mCharset = "";
        if (null == mCharset || "".equals(mCharset)) {
            mCharset = "GBK";
        }
        InputStream pBatIs = new FileInputStream(nFileURL);
        
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		
		Element mChkDetails = new Element("ChkDetails");
		String tLineMsg = mBufReader.readLine();
		for (; null != (tLineMsg= mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
                cLogger.warn("空行，直接跳过，继续下一条！");
                continue;
            }
			String[] tSubMsgs = tLineMsg.split("\\|");
			
			if (!"01".equals(tSubMsgs[6])) {
                cLogger.warn("非新单承保保单，直接跳过，继续下一条！");
                continue;
            }
			if (!"01".equals(tSubMsgs[7])) {
                cLogger.warn("非承保状态保单，直接跳过，继续下一条！");
                continue;
            }
			Element tTranDate = new Element("TranDate");
			tTranDate.setText(tSubMsgs[0]);
			
			Element tTransrNo = new Element("TransrNo");
			tTransrNo.setText(tSubMsgs[1]);
			
			Element tBankCode = new Element("BankCode");
			tBankCode.setText(cConfigEle.getChildTextTrim("bank"));
			
			String mBankZoneCodeStr = tSubMsgs[2];	//农行对账文件把地区代码分成了两个
			Element tBankZoneCode = new Element("BankZoneCode");
			tBankZoneCode.setText(mBankZoneCodeStr);
			
			Element tBrNo = new Element("BrNo");
			tBrNo.setText(tSubMsgs[3]);
			
			Element tCardNo = new Element("CardNo");
			tCardNo.setText(tSubMsgs[4]);
			
			Element tTranAmnt = new Element("TranAmnt");
			tTranAmnt.setText(tSubMsgs[5]);
			
			Element tTellerNo = new Element("TellerNo");

			Element tFuncFlag = new Element("FuncFlag");
			tFuncFlag.setText(tSubMsgs[6]);
			
			Element tConfirmFlag = new Element("ConfirmFlag");
			tConfirmFlag.setText("1");

			Element tAppntName = new Element("AppntName");
			
			Element tChkDetail = new Element("ChkDetail");
			tChkDetail.addContent(tBankCode);
			tChkDetail.addContent(tTranDate);
			tChkDetail.addContent(tBankZoneCode);
			tChkDetail.addContent(tBrNo);
			tChkDetail.addContent(tTellerNo);
			tChkDetail.addContent(tFuncFlag);
			tChkDetail.addContent(tTransrNo);
			tChkDetail.addContent(tCardNo);
			tChkDetail.addContent(tAppntName);
			tChkDetail.addContent(tTranAmnt);
			tChkDetail.addContent(tConfirmFlag);
			
			mChkDetails.addContent(tChkDetail);
			
		}
		mBufReader.close();
		
		Element mTranData = new Element("TranData");
		mTranData.addContent(getHead());
		mTranData.addContent(mChkDetails);
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(mTranData);
	} 
	
	protected static Element getHead() {
		cLogger.info("Into OldBalance.getHead()...");
		
		Element mBankDate = new Element("BankDate");
		mBankDate.setText(String.valueOf(cCurDate));
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cConfigEle.getChildTextTrim("bank"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		Element mBrNo = new Element("BrNo");
		mBrNo.setText(cConfigEle.getChildText("node"));
		
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText("midplat");
		
		Element mTransrNo = new Element("TransrNo");
		
		Element mFunctionFlag = new Element("FunctionFlag");
		mFunctionFlag.setText("17");
		
		Element mBaseInfo = new Element("BaseInfo");
		mBaseInfo.addContent(mBankDate);
		mBaseInfo.addContent(mBankCode);
		mBaseInfo.addContent(mZoneNo);
		mBaseInfo.addContent(mBrNo);
		mBaseInfo.addContent(mTellerNo);
		mBaseInfo.addContent(mTransrNo);
		mBaseInfo.addContent(mFunctionFlag);

		cLogger.info("Out OldBalance.getHead()!");
		return mBaseInfo;
	}
	
	
	
	protected static Document callPostServlet(Document pInStdXmlDoc) throws Exception {
		cLogger.info("Into Balance.callPostServlet()...");
		
		Document mInXmlDoc = MidplatConf.newInstance().getConf();
		
		String mPostServletURL = mInXmlDoc.getRootElement().getChild("suf").getAttributeValue("url");
		
		
		cLogger.info("PostServletURL = " + mPostServletURL);
		URL mURL = new URL(mPostServletURL);
		URLConnection mURLConnection = mURL.openConnection();
		mURLConnection.setDoOutput(true);
		mURLConnection.setDoInput(true);
      XMLOutputter mXMLOutputter = new XMLOutputter(Format.getCompactFormat().setEncoding("GBK"));
      cLogger.info("前置机开始向后置机发送报文...");
		OutputStream mURLOs = mURLConnection.getOutputStream();
		mXMLOutputter.output(pInStdXmlDoc, mURLOs);
		mURLOs.close();
      
      long mStartMillis = System.currentTimeMillis();
		InputStream mURLIs = mURLConnection.getInputStream();
		Document mOutStdXml = JdomUtil.build(mURLIs);	//统一使用GBK编码
		mURLIs.close();
		cLogger.debug("后置机处理耗时：" + (System.currentTimeMillis()-mStartMillis)/1000.0 + "s");
		cLogger.info("接收到后置机返回报文，并解析为Document！");

		cLogger.info("Out Balance.callPostServlet()!");
		cLogger.info("对账结果"+JdomUtil.toString(mOutStdXml));
		return mOutStdXml;
	}
	/**
	 * 获取交易报文头
	 * @param packLen 报文体的长度
	 * @return
	 */
	private static byte[] getHeader(int packLen) {
		String tPackHeadLengthStr = String.valueOf(packLen);

		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;
		
		String des = "";
		for(int i = 0; i < 40; i++){
			des += " ";
		}
		
		String pack = "X1.0" +tPackHeadLengthStr+"1147    "+"0"+"0"+"0"+"0"+"0"+des+"00000000";
		return  pack.getBytes();
	}
	/**
	 * 向银行发送交易
	 * @param headerStr  报文头
	 * @param bodyStr  密文
	 * @param fileData  传送的数据
	 */
	private boolean sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName,String funcFlag) {
		cLogger.info("发送内容是:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		ABCSocketIp = BatUtils.getConfigEle(funcFlag).getChildText("ip");
		ABCSocketPort = Integer.parseInt(BatUtils.getConfigEle(funcFlag).getChildText("ABCport"));
		cLogger.info("目标ip与端口:" + ABCSocketIp + "::" + ABCSocketPort);
		boolean result = false;
		Socket socket = null;
		OutputStream os = null;
		InputStream in = null;
		try {
			socket = new Socket(ABCSocketIp, ABCSocketPort);
			cLogger.info("socket已连接");		
			socket.setSoTimeout(60000);
			os = socket.getOutputStream();
			in = socket.getInputStream();
			os.write(headerStr);
			os.write(bodyStr);
//			os.flush();
			cLogger.info("socket已连接，数据已发送");		
			String confirm = "0000";
			int fileLen = 0;
			if(transFlag.equals(UP)){ //上传
				if(fileData == null || "".equals(fileData)){
					return false;
				}				
				String confir = "";
				int x = 0;
				for (int i = 0; i < 4; i++) {
					x = in.read();
					cLogger.info(x);
					if (x == -1) {
						throw new RuntimeException("输入流字节数数小于4");
					}
					confir += (char) x;
				}
				cLogger.info("银行返回结果"+confir);
				if(!"0000".equals(confir)){
					return false;
				}
				String upFileLen = String.valueOf(fileData.length);
				fileLen = Integer.parseInt(upFileLen);
				int mm = fileLen/4096;
				int mmm = fileLen%4096;
				cLogger.info("商： "+mm);
				cLogger.info("余数： "+mmm);
				if(mmm!=0)
				{
					mm = mm+1;
				}
				cLogger.info("发送次数： "+mm);
				for(int m  =0;m<mm;m++){
					byte[] RealfileData = null;
					if(mm-m!=1){
//						RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
						RealfileData = new String(fileData,m*4096,4096).getBytes();
					}else{
//						RealfileData = new String(fileData).substring(m*4096).getBytes();
						RealfileData = new String(fileData,m*4096,fileLen-m*4096).getBytes();
					}
					cLogger.info("第"+m+"次发送的数据"+new String(RealfileData));
//					cLogger.info("第"+m+"次发送的数据长度"+(new String(RealfileData)).length());
					cLogger.info("第"+m+"次发送的数据长度"+RealfileData.length);
//					String tSendLen = (new String(RealfileData)).length()+"";
					String tSendLen = RealfileData.length+"";
					for(int i = ((new String(RealfileData)).length()+"").length();i<12;i++){
						tSendLen = "0"+tSendLen;
					}
					cLogger.info("第"+m+"次发送的数据长度"+tSendLen);
					os.write(tSendLen.getBytes());
					os.write(RealfileData);
					String secondCon = "";
					for (int i = 0; i < 4; i++) {
						x = in.read();
						System.out.print(x);
						if (x == -1) {
							throw new RuntimeException("输入流字节数数小于4");
						}
						secondCon += (char) x;
					}
					cLogger.info("第"+m+"次发送银行返回数据结果"+secondCon);
					if(!"0000".equals(secondCon)){
						return false;
					}
				}
				
			}else if(transFlag.equals(DOWN)){    
				String tFileLen = "";
				int x = 0;
				for (int i = 0; i < 12; i++) {
						x = in.read();
					if (x == -1) {
						throw new RuntimeException("输入流字节数数小于12");
					}
					tFileLen += (char) x;
				}
				cLogger.info("---银行发送的文件长度： "+tFileLen);
				fileLen = Integer.parseInt(tFileLen); 
				int mm = fileLen/4096;
				int mmm = fileLen%4096;
				cLogger.info("商： "+mm);
				cLogger.info("余数： "+mmm);
				if(mmm!=0)
				{
					mm = mm+1;
				}
				cLogger.info("请求次数： "+mm);
				//给银行返回确认
				os.write(confirm.getBytes());
				//开始接受文件
				cLogger.info("------读银行传送的文件---");
				ByteArrayInputStream is = null;
				if(fileLen>0){   //把银行传递的文件数据保存在mDownLoadPath路径下
					// 返回参数
					ByteArrayOutputStream files = new ByteArrayOutputStream();
					for(int m  =0;m<mm;m++)
					{
					int f = 0;
					// 读取报文主体部分
					String tSenFileLen = "";
					for (int i = 0; i < 12; i++) {
							x = in.read();
						if (x == -1) {
							throw new RuntimeException("输入流字节数数小于12");
						}
						tSenFileLen += (char) x;
					}
					cLogger.info("2345679:::::::"+tSenFileLen);
					fileLen = Integer.parseInt(tSenFileLen);
					for (int i = 0; i < fileLen; i++) {
						f = in.read();
						if (f == -1) {
							System.out.println("读入数据流错误，长度应为" + fileLen + "，而实际长度为" + i);
							throw new MidplatException("读入数据流错误，长度应为" + fileLen + "，而实际长度为" + i);
						}
						files.write((char) f);
					}
					is = new ByteArrayInputStream(files.toByteArray()); 
					//给银行返回确认
					os.write(confirm.getBytes());
					}
					OutputStream fileOutput = new FileOutputStream(BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
					cLogger.info("下载路径:" + BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
					int len = 0;
					while ((len = is.read()) != -1) {
						fileOutput.write(len);
					}
					fileOutput.flush();
					fileOutput.close();
					is.close();
				}else{
					System.out.println("读入数据流错误，传输文件数据为空");
					throw new MidplatException("读入数据流错误，传输文件数据为空");
				}
				cLogger.info("文件下载完毕，向银行发送确认消息");
				//再次发送确认
				os.write(confirm.getBytes());
			
			}
			
			os.flush();
			socket.shutdownOutput();
			cLogger.info("向银行发送确认消息完毕");
			if(transFlag.equals(UP)){ 
			InputStream isBack = null;
			if (socket.isConnected()) {
				socket.setSoTimeout(60000);
				isBack = socket.getInputStream();
				cLogger.info("-------接收银行端返回信息----");
				result = readFileData(isBack,transFlag);
			}
			
			if (isBack != null){
				isBack.close();
			}
			}else{				
				result = true;
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidplatException e) {
			e.printStackTrace();
		}finally{			
				try {
					if (os != null){
						os.close();
						cLogger.info("关闭OutputStream成功");
					}
					if (in != null){
						in.close();
						cLogger.info("关闭InputStream成功");
					}
					if (socket != null){
					socket.close();
					cLogger.info("关闭socket成功");
					}					
				} catch (IOException e) {
					cLogger.info("关闭socket异常"+e.getMessage());
				}
		}
		return result;
	}
	/**
	 *  解密获取银行端返回报文信息，并且报文文件信息
	 * @param is  银行端返回的InputStream
	 * @param transFlag  传送方式  上传或下载
	 * @return
	 */
	private static boolean readFileData(InputStream inputstream,String transFlag) {
		try {
			String tPackHead = "";
			int x = 0;
			for (int i = 0; i < 73; i++) {
					x = inputstream.read();
					System.out.print(x);
				if (x == -1) {
					throw new RuntimeException("输入流字节数数小于73");
				}
				tPackHead += (char) x;
			}
			cLogger.info("银行端交易报文头： "+tPackHead);
			String tPackHeadLength = tPackHead.substring(4, 12).trim();
			int inputLen = Integer.parseInt(tPackHeadLength.trim());
			// 读取报文主体部分
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			for (int i = 0; i < inputLen; i++) {
				x = inputstream.read();
				if (x == -1) {
					System.out.println("读入数据流错误，长度应为" + inputLen + "，而实际长度为"
							+ i);
					throw new MidplatException("读入数据流错误，长度应为" + inputLen
							+ "，而实际长度为" + i);
				}
				bao.write((char) x);
			}
			
			byte[] Pack = bao.toByteArray();
			InputStream XMLInputstream = new ByteArrayInputStream(Pack);
			
			// 对报文解密
//			ABCSecurity mABCSecurity = new ABCSecurity();
//			if (!mABCSecurity.decryption(XMLInputstream, null)) {
//				mLogger.info("报文解密失败！");
//			}
//			XMLInputstream = mABCSecurity.getClearStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
			while ((a = XMLInputstream.read()) != -1) {
				baos.write(a);
			}
			byte[] bPack = baos.toByteArray();
			
			String pack = new String(bPack,"UTF-8");
			System.out.println(pack);
			pack  = "<?xml version='1.0' encoding='GBK'?>"+pack;
			cLogger.info(" 返回的报文体:" + pack);
			
			Document mInNoStd = JdomUtil.build(pack.getBytes());
//			Document tDoc = XmlOperator .produceXmlDoc(new ByteArrayInputStream(pack.getBytes()));
			Element root = mInNoStd.getRootElement();
			
			String resultCode = root.getChild("Header").getChildTextTrim("RetCode");
			
			if(!resultCode.equals("000000")){
				System.out.println("银行处理文件下载交易失败");
				throw new MidplatException("银行处理文件下载交易失败");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidplatException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	/**
	 * 文件上传
	 * @param fileName
	 * @param fileType
	 */
	public boolean upLoadFile(String fileName,String fileType,String funcFlag,String mTranDate,String sendName){
		boolean result = false;
		try {
			InputStream is = new FileInputStream(fileName);
			ByteArrayOutputStream tTemp = new ByteArrayOutputStream();
			byte[] bytes = new byte[1];
			while (is.read(bytes) != -1) {
				tTemp.write(bytes);
			}
			tTemp.flush();
			tTemp.close();
			byte[] tBytes = tTemp.toByteArray();
			byte[] bodyStr = getBodyStr(UP,sendName,fileType,tBytes.length+"",mTranDate);
			byte[] headerStr = getHeader(bodyStr.length);
			result = sendData(headerStr,bodyStr,UP,tBytes,fileName,funcFlag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
