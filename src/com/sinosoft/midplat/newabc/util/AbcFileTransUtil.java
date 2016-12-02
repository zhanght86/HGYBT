package com.sinosoft.midplat.newabc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.ElementLis;

/**
 * 新农行文件上传下载类
 * @author Administrator
 *
 */
public class AbcFileTransUtil {
	
	/** 农行交易ip */
	private String cABCSocketIp = "";
	/** 农行交易端口 */
	private int cABCSocketPort = 0;
	/**下载银行的文件会保存到该路径下*/
	private String cDownLoadPath = "";
	/** 上传给银行的文件 保存到该路径下*/
	private String cUpLoadPath = "";
	/** 标识是传送方式为 上传*/
	private static final String UP = "0";
	/** 标识是传送方式为 下载*/
	private static final String DOWN = "1";
	/** 标识是文件类型为 证书文件*/
	private static final String CRTFILE = "01";	
	/** 保险公司编码 */
	private String cInsu;
	/** 交易流水 */
	private String cTransNo = "";
	/** 优化日志输出 */
	private Logger cLogger = Logger.getLogger(AbcFileTransUtil.class);
	/** 当前系统日期YYYYMMDD */
	private String c8Date = String.valueOf(DateUtil.getCur8Date());
	/** 当前系统时间HHMMSS */
	private String c6Time = String.valueOf(DateUtil.getCur6Time());
	
	/**
	 * 从指定路径上传或下载文件时，必须带路径参数
	 * @param cDownLoadPath  下载文件时 必要参数
	 * @param cUpLoadPath   从指定路径上传文件时，必要参数
	 * @throws MidplatException 
	 */
	public AbcFileTransUtil(String cDownLoadPath,String cUpLoadPath) throws MidplatException{
		this.cDownLoadPath = cDownLoadPath;
		this.cUpLoadPath = cUpLoadPath;
		getServerInfo();
	}
	
	/**
	 * 从农行配置文件获取农行ip、端口信息
	 * @throws MidplatException 
	 */
	private void getServerInfo() throws MidplatException {
		try {
			Element tAbcConfEle = NewAbcConf.newInstance().getConf().getRootElement();
			cABCSocketIp = tAbcConfEle.getChild("socket").getAttributeValue("ip");
			cABCSocketPort = Integer.valueOf(tAbcConfEle.getChild("socket").getAttributeValue("port"));
			cInsu=tAbcConfEle.getChild("bank").getAttributeValue("insu");
			File tFile = new File(cUpLoadPath);
			if (!tFile.exists())
				tFile.mkdirs();

			tFile = new File(cDownLoadPath);
			if (!tFile.exists())
				tFile.mkdirs();
			
			cLogger.info("农行服务信息：\n 通知信息: ip = " + cABCSocketIp + "] port = " + cABCSocketPort );
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("获取银行服务器信息失败!");
		}
		
	}
	
	/**
	 * 下载文件
	 * @param fileName 需下载的文件名
	 * @param fileType 需下载的文件类型   01  证书文件  02 对账文件
	 * @throws Exception 
	 */
	public void downLoadFile(String fileName,String fileType) throws Exception{
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0");  //得到加密后的报文体信息
		byte[] headerStr = getHeader(bodyStr.length); //得到报文请求头
		sendData(headerStr,bodyStr,DOWN,null,fileName);
	}
	
	/**
	 * 文件上传
	 * @param pFileName
	 * @param pFileType
	 * @throws Exception
	 */
	public void upLoadFile(String pFileName,String pFileType) throws Exception{
		try {
			InputStream is = new FileInputStream(cUpLoadPath + pFileName);
			ByteArrayOutputStream tTemp = new ByteArrayOutputStream();
			byte[] bytes = new byte[1];
			while (is.read(bytes) != -1) {
				tTemp.write(bytes);
			}
			tTemp.flush();
			tTemp.close();
			byte[] tBytes = tTemp.toByteArray();
			byte[] bodyStr = getBodyStr(UP,pFileName,pFileType,tBytes.length+"");
			byte[] headerStr = getHeader(bodyStr.length);
			sendData(headerStr,bodyStr,UP,tBytes,pFileName);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("文件上传失败:"+e.getMessage());
		}
	}
	
	public void upLoadFile(String fileName,String fileType,byte[] data) throws Exception{
		byte[] bodyStr = getBodyStr(UP,fileName,fileType,data.length+"");
		byte[] headerStr = getHeader(bodyStr.length);
		sendData(headerStr,bodyStr,UP,data,fileName);
	}
	
	/**
	 * 向银行发送交易
	 * @param headerStr  报文头
	 * @param bodyStr  密文
	 * @param fileData  传送的数据
	 */
	private void sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName) throws Exception {
		cLogger.info("发送内容是:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		cLogger.info("目标ip与端口:" + cABCSocketIp + ":" + cABCSocketPort);
		InputStream in = null;
		OutputStream os = null;
		Socket socket = null;
		try {

			socket =  new Socket(cABCSocketIp, cABCSocketPort);
			socket.setSoTimeout(60000);
			os = socket.getOutputStream();
			in = socket.getInputStream();
			
			cLogger.info("向银行发送报文头!");
			os.write(headerStr);
			cLogger.info("向银行发送报文体!");
			os.write(bodyStr);
			InputStream is = null;
			String confirm = "0000";
			int fileLen = 0;
			
			//文件上传处理
			if(transFlag.equals(UP)){ //上传
				if(fileData == null || "".equals(fileData)){
					throw new MidplatException("文件上传失败,上传文件内容为空!");
				}
				
				cLogger.info("处理银行返回结果!");
				String confir = "";
				int x = 0;
				for (int i = 0; i < 4; i++) {
					x = in.read();
					System.out.print(x);
					if (x == -1) {
						throw new RuntimeException("输入流字节数数小于12");
					}
					confir += (char) x;
				}
				cLogger.info("银行返回结果:"+confir);
				if(!"0000".equals(confir)){
					cLogger.info("非0000返回!");
					readFileData(in,transFlag,69);
				}
				String upFileLen = String.valueOf(fileData.length);
				fileLen = Integer.parseInt(upFileLen);
				cLogger.info("数据总长度"+fileLen);
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
					cLogger.info("m:"+m);
					cLogger.info("fileData:"+fileData.length);
					if(mm-m!=1){
						//RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
						RealfileData = subBytes(fileData,m*4096,4096);
					}else{
						RealfileData = subBytes(fileData,m*4096,mmm);
						
					}
					//cLogger.info("第"+m+"次发送的数据"+new String(RealfileData));
					cLogger.info("第"+m+"次发送的数据长度"+RealfileData.length);
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
					cLogger.info("第"+m+"次发送的数据长度"+secondCon);
					if(!"0000".equals(secondCon)){
						throw new MidplatException("文件上传失败!");
					}
				}
			}else if(transFlag.equals(DOWN)){    
				
				String tFileLen = "";
				int x = 0;
				for (int i = 0; i < 1; i++) {
					x = in.read();
				if (x == -1) {
					throw new RuntimeException("输入流字节数数小于12");
				}
				tFileLen += (char) x;
				}
				
				if(tFileLen.startsWith("X")){
					readFileData(in,transFlag,72);
				}
				
				for (int i = 0; i < 11; i++) {
					x = in.read();
				if (x == -1) {
					throw new RuntimeException("输入流字节数数小于12");
				}
				tFileLen += (char) x;
				}
				
				fileLen = Integer.parseInt(tFileLen);
				cLogger.info("---银行发送的文件长度： "+tFileLen);
				
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
				if(fileLen>0){   //把银行传递的文件数据保存在cDownLoadPath路径下
					// 返回参数
					ByteArrayOutputStream files = new ByteArrayOutputStream();
					
					for(int m  =0;m<mm;m++){
						
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
					if(fileName == null || fileName.equals("")){
						fileName = "cacert.crt";
					}
					OutputStream fileOutput = new FileOutputStream(cDownLoadPath + fileName);
					
					System.out.println("下载路径:" + cDownLoadPath + fileName);
					
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
				//再次发送确认
				//os.write(confirm.getBytes());
			}
			
			os.flush();
			socket.shutdownOutput();

			if (socket.isConnected()) {
				socket.setSoTimeout(60000);
				is = socket.getInputStream();
				cLogger.info("-------接收银行端返回信息----");
				readFileData(is,transFlag,73);
			}
			if (os != null)
				os.close();
			if (is != null)
				is.close();
			if (socket != null)
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		} finally{	
			try{
				os.flush();
				in.close();
				socket.close();
			}catch(IOException ioe){
				cLogger.error("关闭输出流异常");
				
			}
			
		}

	}

	/**
	 *  解密获取银行端返回报文信息，并且报文文件信息
	 * @param is  银行端返回的InputStream
	 * @param transFlag  传送方式  上传或下载
	 * @return
	 */
	private void readFileData(InputStream inputstream,String transFlag,int num) throws MidplatException {
		try {
			String tPackHead = "";
			int x = 0;
			for (int i = 0; i < num; i++) {
					x = inputstream.read();
					//System.out.print(x);
				if (x == -1) {
					throw new RuntimeException("输入流字节数数小于73");
				}
				tPackHead += (char) x;
			}
			cLogger.info("银行端交易报文头： "+tPackHead);
			String tPackHeadLength = tPackHead.substring(4, 12).trim();
			if(num == 72){
				tPackHeadLength = tPackHead.substring(3, 11).trim();
			}
			if(num == 69){
				tPackHeadLength = tPackHead.substring(0, 8).trim();
			}
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
//				cLogger.info("报文解密失败！");
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
			Document tDoc = JdomUtil.build(pack);
			Element root = tDoc.getRootElement();
			JdomUtil.print(tDoc);
			String resultCode = root.getChild("Header").getChildTextTrim("RetCode");
			String resultText = root.getChild("Header").getChildTextTrim("RetMsg");
			System.out.println("银行返回代码:" + resultCode);
			System.out.println("银行返回结果:" + resultText);
			if(!resultCode.equals("000000")){
				throw new MidplatException(""+resultText);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
	}

	/**
	 * 获取报文体
	 * @param transFlag  交易类型  0-下载 1-上传
	 * @param fileName  文件名称
	 * @param fileData 文件数据
	 * @param fileType 文件类型 01  证书文件  02 对账文件
	 * @return
	 * @throws Exception 
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen) throws Exception {
		try {
			if(transFlag.equals(DOWN)){
				FileLen = "00000000";
			}else{
				for (int i = FileLen.length(); i < 8; i++)
					FileLen = "0" + FileLen;
			}
			
			if(fileType.equals(CRTFILE)){
				fileName = "";
			}
			String xmlStr = getToABCXml(fileName,FileLen, transFlag);
				
			cLogger.info("向银行发送文件上传下载交易报文体：");
			JdomUtil.print(JdomUtil.build(xmlStr));
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
				while ((a = input.read()) != -1) {
					baos.write(a);
				}
				
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new MidplatException("生成报文体失败:"+e.getMessage());
		}
	}
	
	/**
	 * 传入文件名、文件长度、文件类型，获取发送银行的报文信息
	 */
	public String getToABCXml(String FileName, String FileLen,String transFlag)throws Exception{
		ElementLis ABCB2I = new ElementLis("ABCB2I");
			ElementLis Header = new ElementLis("Header",ABCB2I);
				ElementLis SerialNo = new ElementLis("SerialNo",String.valueOf(DateUtil.getCur8Date())+DateUtil.getCur6Time(),Header); //银行交易流水号	
				ElementLis InsuSerial = new ElementLis("InsuSerial","",Header);  //保险公司流水号	
				ElementLis TransDate = new ElementLis("TransDate",String.valueOf(DateUtil.getCur8Date()),Header); //交易日期
				ElementLis TransTime = new ElementLis("TransTime",DateUtil.getCur6Time()+"",Header); //交易时间	
				ElementLis BankCode = new ElementLis("BankCode","03",Header); //银行代码	
				ElementLis CorpNo = new ElementLis("CorpNo",cInsu,Header); //保险公司代码	
				ElementLis TransCode  = new ElementLis("TransCode","1017",Header);  //交易编码 1017-文件上传下载交易
				ElementLis TransSide = new ElementLis("TransSide","0",Header); //交易发起方	0-保险公司 1-银行
				ElementLis EntrustWay = new ElementLis("EntrustWay","",Header);  //委托方式	
				ElementLis ProvCode = new ElementLis("ProvCode","",Header); //省市代码	
				ElementLis BranchNo = new ElementLis("BranchNo","",Header); //网点号
		
			ElementLis App = new ElementLis("App",ABCB2I);
				ElementLis Req = new ElementLis("Req",App);
					ElementLis mTransFlag = new ElementLis("TransFlag",transFlag,Req); 
					ElementLis mFileType = new ElementLis("FileType","02",Req);
					ElementLis mFIleName = new ElementLis("FileName",FileName,Req);
					ElementLis mFileLen = new ElementLis("FileLen",FileLen,Req);
					ElementLis mFileTimeStamp = new ElementLis("FileTimeStamp",c8Date+c6Time,Req);

		Document doc = new Document(ABCB2I);
		
		return JdomUtil.toString(doc);
	}

	/**
	 * 获取交易报文头
	 * @param packLen 报文体的长度
	 * @return
	 */
	private byte[] getHeader(int packLen) {
		String tPackHeadLengthStr = String.valueOf(packLen);

		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;
		
		String des = "";
		for(int i = 0; i < 40; i++){
			des += " ";
		}
		
		String pack = "X1.0" +tPackHeadLengthStr+"1116    "+"0"+"0"+"0"+"0"+"0"+des+"00000000";
		return  pack.getBytes();
	}
	
	 public static byte[] subBytes(byte[] src, int begin, int count) {
			// System.out.println("begin:"+begin +"  " )
		        byte[] bs = new byte[count];
		        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
		        return bs;
		}
	
	public static void main(String[] args) throws Exception {
		String downPath = "/web/ybt/WEB-INF/key/";
		String upPath = "D:/0-利安人寿/I-农业银行/农行新接口/";
		AbcFileTransUtil util = new AbcFileTransUtil(downPath,upPath);
   		util.downLoadFile("cacert.crt", CRTFILE);
	    //System.out.println(InetAddress.getLocalHost());
		//util.downLoadFile("POLICY1122.20150130", "02");
	}
}
