package com.sinosoft.midplat.newabc.bat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class KeyReset extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	//配置信息节点
	protected String cResultMsg;
	protected Element cConfigEle;
	private static String cCurDate = "";
	private String publicKey;
	private String newAESRSAKey;
	private String oldAESRSAKey;
	private AES2RSAEnCipher aesTorsa;

	public KeyReset() {
	}

	public void run() {
		cLogger.info("Into KeyReset.run()...");
		cResultMsg = null;
		
		try{
			cConfigEle = BatUtils.getConfigEle("1001");
			System.out.println("quzhi:"+cConfigEle.getChildText("startTime"));		
		}catch(Exception e){
			cLogger.info("读取配置文件异常，请检查路径是否正确及文件是否存在......");
			e.getStackTrace();
		}
		
		if ("".equals(cCurDate)) {
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		
		String mLocalPort = cConfigEle.getChildTextTrim("LocalPort");
		cLogger.info("得到类绝对路径："+cConfigEle.getChildText("FilePath")+"/");
				
		String mKeyPath =SysInfo.cHome+"key/";
		//新安全证书
		String publicNewKeyPath = mKeyPath  +"cacert.crt";
		long mOldTimeMillis = System.currentTimeMillis();
		
		String mTransNo = "1001" + cCurDate + new SimpleDateFormat("HHmmss").format(new Date());
		try {
			
			//老安全证书
			String publicKeyPath = mKeyPath + mLocalPort+"_pub.cer";
			cLogger.info(publicKeyPath);
			publicKey = new String(IOTrans.toBytes( new FileInputStream(publicKeyPath)));
			
			aesTorsa = new AES2RSAEnCipher();
			try {
				aesTorsa.createAESCode(mKeyPath);
				Thread.sleep(2000);
				
				String newAESKeyPath = mKeyPath +"newABCKey.dat";
				String newAESKey = new String(IOTrans.toBytes( new FileInputStream(newAESKeyPath)));
				
				cLogger.info("RSA加密前的新工作密钥："+ newAESKey);
				cLogger.info("RSA加密前的公钥："+ publicKey);
								
				newAESRSAKey = RSAUtils.encryptAESKey(publicNewKeyPath, newAESKey);  			
				
				cLogger.info("RSA加密后的新工作密钥："+ newAESRSAKey);
				
				String oldAESKeyPath = mKeyPath +"oldABCKey.dat";     //老的密钥
				String oldAESKey = new String(IOTrans.toBytes( new FileInputStream(oldAESKeyPath)));
				cLogger.info("RSA加密前的老工作密钥："+ oldAESKey);
								
				oldAESRSAKey = RSAUtils.encryptAESKey(publicKeyPath, oldAESKey);  //  用老证书加密老密钥				
				 
				cLogger.info("RSA加密后的老工作密钥："+ oldAESRSAKey);      
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			cLogger.info("异常："+ e1.getMessage());   
		}

		//获取银行IP地址
		String mIp = cConfigEle.getChildText("ip").trim();
		//获取银行端口
		int mPort = Integer.valueOf(cConfigEle.getChildTextTrim("ABCport")).intValue();
		
		try {
			//拼接文件上传下载请求报文 
			//组织根节点以及Header节点内容 
			Element mABCB2I = new Element("ABCB2I");
			
			//业务包头
			Element mHeader = new Element("Header");
			
			//保险公司流水号
			Element mInsuSerial = new Element("InsuSerial");
			mInsuSerial.setText(mTransNo);
			
			//当前日期对象
			Date date = new Date();
			//交易日期
			Element mTransDate = new Element("TransDate");
			mTransDate.setText(cCurDate);
			
			//交易时间
			Element mTransTime = new Element("TransTime");
			mTransTime.setText(new SimpleDateFormat("HHmmss").format(date));
			
			//银行代码
			Element mBankCode = new Element("BankCode");
			mBankCode.setText("03");
			
			//保险公司代码
			Element mCorpNo = new Element("CorpNo");
			mCorpNo.setText(cConfigEle.getChildTextTrim("ComCode"));
			
			//交易编码
			Element mTransCode = new Element("TransCode");
			mTransCode.setText("1001");
			
			//交易发起方
			Element mTransSide = new Element("TransSide");
			mTransSide.setText("0");
			
			//委托方式
			Element mEntrustWay = new Element("EntrustWay");
			
			// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
			mHeader.addContent(mInsuSerial).addContent(mTransDate)
			.addContent(mTransTime).addContent(mBankCode)
			.addContent(mCorpNo).addContent(mTransCode)
			.addContent(mTransSide).addContent(mEntrustWay);
			// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

			//组织App节点内容 
			//业务包体
			Element mApp = new Element("App");
			
			//请求报文
			Element mReq = new Element("Req");
			
			//加密方式[01: 默认方式RSA+AES方式]
			Element mEncType = new Element("EncType");
			mEncType.setText("01");
			
			//新密钥[16进制字符串]
			Element mPriKey = new Element("PriKey");
			mPriKey.setText(newAESRSAKey);
			
			//原密钥[16进制字符串]
			Element mOrgKey = new Element("OrgKey");
			mOrgKey.setText(oldAESRSAKey);
			
			mReq.addContent(mEncType).addContent(mPriKey)
			.addContent(mOrgKey);
			mApp.addContent(mReq);

			mABCB2I.addContent(mHeader);
			mABCB2I.addContent(mApp);

			//将根节点转换为GBK编码的字节数组，保持原格式
			byte[] mOutBodyBytes = JdomUtil.toBytes(mABCB2I);
			cLogger.info("IP、端口：" + mIp + mPort);
			Socket mSocket = new Socket(mIp, mPort);
			cLogger.info("连接成功");
			//获取报文追加报文头字节数组后的总请求字节数组
			byte[] mInTotalBytes = new KeyReset().appendHeadBytes(mOutBodyBytes);
			System.out.println("发送的请求报文：" + new String(mInTotalBytes));
			cLogger.info("发送请求报文！");
			
			//发送数据给银行返回总请求字节数组
			OutputStream os = mSocket.getOutputStream();
			os.write(mInTotalBytes);
			os.flush();
			
			/** 以下处理返回报文 ************************/
			//接收数据给银保通返回应答报文输入字节流
			InputStream mSocketIs = mSocket.getInputStream();
			//关闭输出流
			mSocket.shutdownOutput();
			//当前时间毫秒数
			long mCurTimeMillis = System.currentTimeMillis();
			//客户端请求发送完毕！耗时：(当前时间毫秒数-开始时间毫秒数)/1000.0s
			cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");
			cLogger.info("收到返回报文！");

			// 处理报文头
			byte[] mOutHeadBytes = new byte[73];
			//从输入字节流中读取73个字节的数据，填满传入的字节数组，不关闭流
			IOTrans.readFull(mOutHeadBytes, mSocketIs);
			// byte[] mOutHeadBytes = IOTrans.toBytes(mSocketIs);
			//收到返回报文头：报文头字符串
			cLogger.info("收到返回报文头：" + new String(mOutHeadBytes));
			//获取报文体[包体]长度
			int mInBodyLength = Integer.parseInt(new String(mOutHeadBytes, 4, 8).trim());
			//收到返回报文长度为：报文体[包体]长度
			cLogger.info("收到返回报文长度为：" + mInBodyLength);

			//返回报文体字节数组
			byte[] nnOutBodyBytes = new byte[mInBodyLength];
			//从输入字节流中读取报文体长度个字节的数据，填满传入的字节数组，不关闭流
			IOTrans.readFull(nnOutBodyBytes, mSocketIs);
			//获取UTF-8编码的返回报文体字符串
			String mOutBodyStr = new String(nnOutBodyBytes, "UTF-8");
			//银行返回非标准报文！返回报文体字符串
			cLogger.info("银行返回非标准报文！" + mOutBodyStr);

			//采用GBK编码构建一个非标准输出报文，忽略标签之间的空字符(空格、换行、制表符等)
			Document mOutNoStd = JdomUtil.build(mOutBodyStr.getBytes());
			
			// 农行密钥已经下载并保存到本地
			if ("000000".equals(mOutNoStd.getRootElement().getChild("Header").getChildText("RetCode"))) {// 交易成功
				//农行处理密钥更新成功，进行本地密钥更换
				cLogger.info("农行处理密钥更新成功，进行本地密钥更换");
				//在银行同步密钥交易成功之后，将新生成的密钥替换老的密钥，并将老密钥备份
				aesTorsa.synchAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				//在银行同步密钥交易成功之后，将新下载的安全证书替换老的安全证书，并将老安全证书备份
				aesTorsa.synchRSACode(mKeyPath, Integer.parseInt(mLocalPort));
				//本地密钥更换完成...
				cLogger.info("本地密钥更换完成!");
			} else {
				//农行处理密钥更新交易失败，不进行更新本地密钥处理...
				cLogger.error("农行处理密钥更新交易失败，不进行更新本地密钥处理!");
			}
			if (os != null)
				os.close();
			if (mSocketIs != null)
				mSocketIs.close();
			if (mSocket != null)
				mSocket.close();
			cResultMsg = mOutNoStd.getRootElement().getChild("Header").getChildText("RetMsg");
		} catch (UnknownHostException e) {
			cResultMsg = e.toString();
			e.printStackTrace();
		} catch (IOException e) {
			cResultMsg = e.toString();
			e.printStackTrace();
		}

		cLogger.info("Out KeyReset.run()!");
	}

	/**
	 * @Title: appendHeadBytes
	 * @Description: 报文追加报文头字节数组
	 * @param mBodyBytes 报文体字节数组
	 * @return 报文头+报文体			字节数组
	 * @return 报文头+报文体			byte[]
	 * @throws
	 */
	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		// 剪切xml头部之后报文体
		String mBodyStr = new String(mBodyBytes);
		String tPackHeadLengthStr = String.valueOf(mBodyStr.length());

		/*
		 * 数据包长度[N8:0x(8-包体长度)+包体长度]，包体的长度不能超过50K字节。
		 * 如果加密，指加密后的长度；如果压缩，指定压缩后的长度。
		 */
		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			//前补(8-包体长度)个0
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;

		//摘要[C40:40x' ']
		String des = "";
		for (int i = 0; i < 40; i++) {
			//后置40个空格
			des += " ";
		}
		/*	[C1:固定"X",C3:1.0,N8:数据包长度,C8:银行分配保险公司代码,C1:0-不加密1-关键数据加密2-报文整体加密,C1:加密算法
		 *		报文类型+版本号+数据包长度[包体长度]+公司代码+加密标示+加密算法
		 */
		String pack = "X1.0" + tPackHeadLengthStr + "1147    " + "0" + "0"
		/*
		 * C1:0-不压缩1-压缩,C1:0,C1:0,C40:40x' ',C8:固定值00000000]
		 * 数据压缩标志+数据压缩算法+摘要算法+摘要+预留字段[固定值C8:00000000]
		 */
								+ "0" + "0" + "0" + des + "00000000";
		
		/*
		 * 			包头+XML结构业务包体	
		 * 返回	报文头+报文体			字节数组
		 */
		return (pack + mBodyStr).getBytes();
	}

	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger=Logger.getLogger("com.sinosoft.midplat.newabc.bat.KeyReset.main");
		mLogger.info("新农行密钥重置程序启动...");
		
		KeyReset abcAES = new KeyReset();
		
		// 用于补对账，设置补对账日期 
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
		
			/**
			 * 严格日期校验的正则表达式：\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		abcAES.run();
		mLogger.info("新农行密钥重置程序完成!");
	}

}
