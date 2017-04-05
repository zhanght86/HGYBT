package com.sinosoft.midplat.newabc.bat;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BatUtils
{
	protected Element cThisBusiConf = null;//当前交易配置节点
	protected Element cThisConfRoot = null;//当前银行交易配置文件根节点

	/** 本类的日志对象*/
	protected static final Logger cLogger = Logger.getLogger(BatUtils.class);
	
	/** 批处理配置节点*/
	protected static Element cConfigEle;
	
	/** 当前时间字符串[yyyyMMdd]*/
	private static String cCurDate = "";
	
	/** 农行交易ip */
	private static String ABCSocketIp = "";

	/** 农行交易端口 */
	private static int ABCSocketPort = 0;
	/** 传送方式		0: 上传*/
	private static final String UP = "0";
	/** 传送方式		1: 下载*/
	private static final String DOWN = "1";
	
	/** 文件类型		01: 证书文件*/
	private static final String CRTFILE = "01";	
	
	/**
	 * 获取批处理配置节点信息
	 * @param funcFlag 交易类型
	 * @return 返回批处理配置信息节点
	 */
	public static Element getConfigEle (String funcFlag){
		//当前时间字符串为空
		if("".equals(cCurDate)){
			//格式化日期对象为yyyyMMdd字符串
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		try{
			//bat.xml文档
			Document mInXmlDoc =  BatConf.newInstance().getConf();
			//批处理配置节点XPath路径
			String elementXpathUrl = "/batchs/batch[funcFlag = '"+ funcFlag +"']";
			//bat.xml文档中选择一个与批处理配置节点XPath路径匹配的节点
			cConfigEle = (Element)XPath.selectSingleNode(mInXmlDoc, elementXpathUrl);
			//取值:startTime子节点内容
			System.out.println("取值:"+cConfigEle.getChildText("startTime"));
		}catch(Exception e){ 
			//读取配置文件异常，请检查路径是否正确及文件是否存在......
			cLogger.info("读取配置文件异常，请检查路径是否正确及文件是否存在......");
			e.getStackTrace();
		}
		
		//返回批处理配置信息节点
		return cConfigEle;
	}
	
	/**
	 * 获取包头(报文头)
	 * @param packBodyLen 包体长度(数据包长度)
	 * @return 返回包头字符串字节数组
	 */
	private static byte[] getHeader(int packBodyLen) {
		//包头数据包长度字符串
		String tPackHeadDataPackLengthStr = String.valueOf(packBodyLen);
		
		//补足为8位前导零10进制数据包长度字符串
		for (int i = tPackHeadDataPackLengthStr.length(); i < 8; i++)
			tPackHeadDataPackLengthStr = "0" + tPackHeadDataPackLengthStr;
		
		//摘要字符串
		String tabloid = "";
		//补足为40位后导空格摘要字符串
		for(int i = 0; i < 40; i++){
			tabloid += " ";
		}
		
		//包头字符串[报文类型:X(标准接口),版本号:1.0,数据包长度,公司代码:C8(1132    ),加密标示:(0-不加密；1-关键数据加密；2-报文整体加密),加密算法:0,数据压缩标志:(0-不压缩；1-压缩),数据压缩算法:0,摘要算法:0, 摘要,预留字段:00000000]
		String packHeadStr = "X1.0" +tPackHeadDataPackLengthStr+"1132    "+"0"+"0"+"0"+"0"+"0"+tabloid+"00000000";
		//返回包头字符串字节数组
		return  packHeadStr.getBytes();
	}
	
	
	/**
	 * 获取文件上传下载报文(包体/报文体)[UTF-8]字节数组
	 * @param transFlag  传送方式		0: 上传		1: 下载
	 * @param fileName  文件名称
	 * @param fileType 文件类型		01: 证书文件		02: 对账文件
	 * @param FileLen 文件长度 		文件上传:上传文件UTF-8格式文件长度 	文件下载:00000000
	 * @param mTranDate 当前时间
	 * fileData 文件数据		文件上传请求报文	或者		文件下载应答报文 之后发送
	 * 接收方法： 
	 * 首先接收报文头，得到XML报文长度 
	 * 用报文长度得到完整的报文
	 * 在报文体中得到文件长度，按照文件长度接收文件
	 * @return
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen,String mTranDate) {
		
		//新建日期对象
		Date date = new Date();
		//当前日期字符串[yyyyMMdd]
		String cCurDate = new SimpleDateFormat("yyyyMMdd").format(date);
		//当前时间字符串[HHmmss]
		String cCurTime = new SimpleDateFormat("HHmmss").format(date);
		//完整当前日期字符串(文件修改时间戳)[yyyy-MM-dd HH:mm:ss.SSS]
		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
		
		try {
			//交易编码(获取交易流水号)[04+yyyyMMdd+HHmmss]
			String tTransCode = "04" + cCurDate + cCurTime;
			//传送方式为下载
			if(transFlag.equals(DOWN)){
				//下载文件长度:00000000
				FileLen = "00000000";
			}else{//传送方式为上传
				//上传文件长度:具有前导零的8位10进制字符串
				for (int i = FileLen.length(); i < 8; i++)
					//补足为前导零8位10进制字符串
					FileLen = "0" + FileLen;
			}
			
			//文件类型为证书文件
			if(fileType.equals(CRTFILE)){
				//文件类型为证书文件时，该字段可以为空
				fileName = "";
			}
			//包体字符串
			String xmlStr = "<ABCB2I><Header><SerialNo></SerialNo><InsuSerial>"+tTransCode+"</InsuSerial><TransDate>"+ mTranDate +"</TransDate>" +
					"<TransTime>"+ cCurTime +"</TransTime><BankCode></BankCode><CorpNo>1132</CorpNo><TransCode>1017</TransCode>" +
							"<TransSide>0</TransSide><EntrustWay></EntrustWay><ProvCode></ProvCode><BranchNo></BranchNo></Header>" +
							"<App><Req><TransFlag>"+transFlag+"</TransFlag><FileType>"+fileType+"</FileType><FileName>"+fileName+"</FileName>" +
									"<FileLen>"+FileLen+"</FileLen><FileTimeStamp>"+ cAllCurDate +"</FileTimeStamp></Req></App></ABCB2I>";
			//向银行发送文件上传下载交易报文：包体字符串
			cLogger.info("向银行发送文件上传下载交易报文："+xmlStr);
			//包体字符串构建UTF-8 编码的字节数组输入流
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			//构建字节数组输出流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//字节
			int a = 0;
			//读取到的字节非-1(未尾)
			while ((a = input.read()) != -1) {
				//将读到的字节写入输出流
				baos.write(a);
			}
			//返回输出流中已写入数据的字节数组
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @Title: downLoadFile
	 * @Description: 文件下载
	 * @param fileName 文件名称
	 * @param fileType 文件类型
	 * @param funcFlag 传送方式
	 * @param cCurDate 当前时间
	 * @return 下载成功/失败
	 * @return boolean
	 * @throws
	 */
	public boolean downLoadFile(String fileName,String fileType,String funcFlag,String cCurDate){
		//获取文件上传下载报文(包体/报文体)[UTF-8]字节数组[传送方式: 下载,文件名称,文件类型,文件长度:0(下载),当前时间]
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0",cCurDate);  //得到加密后的报文体信息
		//获取包头(报文头)字节数组
		byte[] headerStr = getHeader(bodyStr.length);
		//
		return sendData(headerStr,bodyStr,DOWN,null,fileName,funcFlag);
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
	 * 向银行发送数据(交易)
	 * @param headerStr  报文头[字节数组]
	 * @param bodyStr  报文体(密文)[字节数组]
	 * @param transFlag 传送方式
	 * @param fileData  文件数据(传送的数据)[字节数组]
	 * @param fileName 文件名称
	 * @param funcFlag 交易类型
	 */
	private boolean sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName,String funcFlag) {
		//发送内容是:\n报文头+报文体+文件数据
		cLogger.info("发送内容是:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		//获取与交易类型匹配的批处理配置节点信息农行交易ip
		ABCSocketIp = BatUtils.getConfigEle(funcFlag).getChildText("ip");
		//获取与交易类型匹配的批处理配置节点信息农行交易端口
		ABCSocketPort = Integer.parseInt(BatUtils.getConfigEle(funcFlag).getChildText("ABCport"));
		//目标ip与端口:农行交易ip::农行交易端口
		cLogger.info("目标ip与端口:" + ABCSocketIp + "::" + ABCSocketPort);
		//
		boolean result = false;
		//套接字
		Socket socket = null;
		//输出流
		OutputStream os = null;
		//输入流
		InputStream in = null;
		try {
			//创建服务端(建立套接字网络通信连接)
			socket = new Socket(ABCSocketIp, ABCSocketPort);
			//socket已连接
			cLogger.info("socket已连接");		
			//接收数据超时时间(启用/禁用60000毫秒(60秒/1分钟)超时值的 SO_TIMEOUT)
			socket.setSoTimeout(60000);
			//发送数据
			os = socket.getOutputStream();
			//接收数据
			in = socket.getInputStream();
			//发送报文头(数据)
			os.write(headerStr);
			//发送报文体(数据)
			os.write(bodyStr);
//			os.flush();
			//socket已连接，数据已发送
			cLogger.info("socket已连接，数据已发送");	
			//
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
						RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
					
					}else{
						RealfileData = new String(fileData).substring(m*4096).getBytes();
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
				fileLen = Integer.parseInt(tFileLen);
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
					
					System.out.println("下载路径:" + BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
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
	
	

}