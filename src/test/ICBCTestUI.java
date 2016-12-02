/**
 * 工行总行测试程序主方类
 * 
 * ChenGB(陈贵菠) 2008.12.10
 */
 
 
package test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;


import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

import test.security.DESCipher;
   
public class ICBCTestUI {
	public  ICBCTestUI(){};
	private Logger cLogger = Logger.getLogger(getClass());
	 private InputStream mInputStream = null; 
 
	    private Document resultDocument = null;
	    private String functionFlag = "";
	    private String tOutMsgFilePath = null;
	private String cIP = null; 
	private int cPort = 0; 
	  
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		//本地 
		String mIP = "127.0.0.1"; 
		//UAT
//		String mIP = "10.0.4.14"; 
		int mPort = 35000;
  
   
		//生产环境--慎用哦
		 
		//DAT环境 
		
		String mFuncFlag = null;	
		 
		//1010-查询，1011-重打，1013-新单承保，1015-撤单,0001-密钥，,213-犹豫期退保查询
		 //105-犹豫期退保查询106犹豫期退保107犹豫期退保冲正108续期缴费查询
		//109续期缴费201续期缴费冲正202退保查询203退保204退保冲正
		//205满期给付查询206满期给付207满期给付冲正
		
		//重打
//		mFuncFlag = "1011";  
//		String mInFilePath = "D:/test/ZHH/工行/162771_217_1011_in.xml";
//		String mOutFilePath = "D:/test/ZHH/工行/162771_217_1011_in.xmlOut.xml";
		
		//新单
		mFuncFlag = "1013";  
//		String mInFilePath = "H:/李路/杭州中韩人寿/核心返回报文/工行投保与撤保/生产工行承保_1206_2117_1013_in.xml";
//		String mOutFilePath = "H:/李路/杭州中韩人寿/核心返回报文/工行投保与撤保/工行承保请求.xml";
		String mInFilePath = "H:/1047162_63_1013_in.xml";
//		String mInFilePath = "H:/1047162_63_1013_in.xml";
//		String mOutFilePath = "H:/请求.xml";
		String mOutFilePath = "H:/承保.xml";
	
		ICBCTestUI mTestUI = new ICBCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		System.out.println(mFuncFlag);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
//		mFos.write(mOutBytes);
		
		JdomUtil.print(mOutXmlDoc);
		
		System.out.println("成功结束！");
	}
	
	public ICBCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public byte[] sendRequestUI(String pFuncFlag, Document document) throws Exception {
		cLogger.info("sendRequestUI的Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
		//加密
		DESCipher mDESCipher = new DESCipher(cPort);
		byte[] mInCipherBodyBytes = mDESCipher.encode(mInClearBodyBytes);
		cLogger.info("请求报文加密完成！");
		
		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + 16];
		
		//报文体长度
		String mInCipherBodyLengthStr = String.valueOf(mInCipherBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//交易代码
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//公司代码
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 16, mInCipherBodyBytes.length);
		
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		//处理报文头
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		//处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		//密钥更换交易特殊处理
		if (pFuncFlag.equals("0001")) {
			try {
				keyChange(mInClearBodyBytes, mOutCipherBodyBytes);	//更新本地密钥文件
				mDESCipher = new DESCipher(cPort);
			} catch (Exception ex) {
				cLogger.debug("密钥更新失败！", ex);
			}
		}		
		
		return mDESCipher.decode(mOutCipherBodyBytes);
	}
	 
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("sendRequest的Socket连接" + cIP + ":" + cPort);

		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
		//加密
		DESCipher mDESCipher = new DESCipher(cPort);
		byte[] mInCipherBodyBytes = mDESCipher.encode(mInClearBodyBytes);
		cLogger.info("请求报文加密完成！");
		
		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + 16];
		
		//报文体长度
		String mInCipherBodyLengthStr = String.valueOf(mInCipherBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//交易代码
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//公司代码
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 16, mInCipherBodyBytes.length);
		
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		//处理报文头
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		//处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		//密钥更换交易特殊处理
		if (pFuncFlag.equals("0001")) {
			try {
				keyChange(mInClearBodyBytes, mOutCipherBodyBytes);	//更新本地密钥文件
				mDESCipher = new DESCipher(cPort);
			} catch (Exception ex) {
				cLogger.debug("密钥更新失败！", ex);
			}
		}		
		
		return mDESCipher.decode(mOutCipherBodyBytes);
	}
	
	/**
	 * 判断密钥更换交易是否成功，若成功则更换本地密钥文件。
	 */
	private void keyChange(byte[] pInClearBytes, byte[] pOutCipherBytes) throws Exception {
		cLogger.debug("Into ICBCControl.keyChange()...");
		
		//从密钥更换交易的请求报文中获取密钥
		ByteArrayInputStream mBais = new ByteArrayInputStream(pInClearBytes);
		InputStreamReader mIsr = new InputStreamReader(mBais, "GBK");
		SAXBuilder mSAXBuilder = new SAXBuilder();
		mSAXBuilder.setIgnoringBoundaryWhitespace(true);
		Document mInXmlDoc = mSAXBuilder.build(mIsr);
		String mDesKey = mInXmlDoc.getRootElement().getChildTextTrim("DesKey");
		if(16 != mDesKey.length()) {
			throw new Exception("密钥长度不是16！" + mDesKey);
		}
		cLogger.debug("密钥：" + mDesKey);
		
		//解密返回报文
		byte[] mKeyBytes = new byte[8];
		for (int i = 0; i < mKeyBytes.length; i++) {
			mKeyBytes[i] = (byte) Integer.parseInt(mDesKey.substring(i*2, i*2+2), 16);
		}		
		SecretKeySpec mKey = new SecretKeySpec(mKeyBytes, "DES");
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, mKey);
		byte[] mOutClearBytes = mCipher.doFinal(pOutCipherBytes);
		
		//更新密钥
		mBais = new ByteArrayInputStream(mOutClearBytes);
		mIsr = new InputStreamReader(mBais, "GBK");
		Document mOutXmlDoc = mSAXBuilder.build(mIsr);
		String mResultCode = mOutXmlDoc.getRootElement()
		.getChild("TransResult").getChildText("ResultCode");
		if (mResultCode.equals("0000")) {
			String mKeyPath = getClass().getResource("security/").getPath();
			FileOutputStream mKeyFos = new FileOutputStream(mKeyPath + cPort + ".dat");
			mKeyFos.write(mDesKey.getBytes());
			mKeyFos.close();
			cLogger.info("密钥更新成功！");
		}
		
		cLogger.debug("Out ICBCControl.keyChange()!");
	}
	
	
	
	 public Document sendRequestUI(String tFunctionFlag) {
		
	    	functionFlag = tFunctionFlag;

	        try {
	        	
	        	System.out.println("1.组织测试报文信息");

	            Socket socket = new Socket(cIP, cPort);
	            
	            OutputStream outputstream = socket.getOutputStream();
	            InputStream inputstream = socket.getInputStream();
	            InputStream fis = mInputStream;

	            byte array[] = new byte[24]; 
	            int m = 1;
//	          把文件流变为字节数组
	            byte[] bytes = new byte[1];
		      	  ByteArrayOutputStream vInNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
		      	  while (fis.read(bytes) != -1)
		      	  {
		      			vInNoSTDByteArrayOutputStream.write(bytes);
		      	  }
		      	  vInNoSTDByteArrayOutputStream.flush();
		      	  vInNoSTDByteArrayOutputStream.close();
		      	  byte[] tInNoSTDbytes = vInNoSTDByteArrayOutputStream.toByteArray();
		      	  
	      	  
	            //包头
	            int fileLength = 0;
	            InputStream d = new ByteArrayInputStream(tInNoSTDbytes);
	            for(fileLength = 0; d.read() != -1; fileLength++);
	            
	            System.out.println("  报文体总长度：" + fileLength);
	            int tPackHeadLength = fileLength;
	            String tPackHeadLengthStr = (new StringBuffer(String.valueOf(tPackHeadLength))).toString();
	            
//	          交行加解密标志位
	            String Flag = "0";
	            byte FlagLength[] = Flag.getBytes();
	            array[0] = FlagLength[0];
	            
	            String BankInfo = "BANK&&COMM";
	            byte BankInfoLength[] = BankInfo.getBytes();
	            for(int i = 0; i < 10; i++)
	            {
	          	 if(i < BankInfoLength.length)
	          	 {
	                 array[m] = BankInfoLength[i];
	          	 }       
	          	 m++;               
	            }              
	            
//	          交易码信息
	            byte arrayPackFunctionFlag[] = functionFlag.getBytes();
	            for(int i = 0; i < arrayPackFunctionFlag.length; i++)
	            {
	               array[m] = arrayPackFunctionFlag[i];
	               m++;
	            }   
//	          报文体长度信息
	            byte arrayPackHeadLength[] = tPackHeadLengthStr.getBytes();
	            for(int i = 0; i < 8-arrayPackHeadLength.length; i++)
	            {
	            	array[m] = "0".getBytes()[0];     
	            	m++;
	            }
	            for(int i = 0; i < arrayPackHeadLength.length; i++)
	            {
	              array[m] = arrayPackHeadLength[i];       
	          	  m++;               
	            }
	            
	            
	            String test = new String (array);
	            System.out.println("  请求的报文头信息:"+test);
	            String fileLengthString = fileLength + "";           
	            outputstream.write(array);
	            
	            
	            //包体
	            System.out.println("2.开始发起交易");
	            
	            InputStream InNoSTD = new ByteArrayInputStream(tInNoSTDbytes);
	            InputStream InNoSTD2 = new ByteArrayInputStream(tInNoSTDbytes);
	            for(; InNoSTD.read(bytes) != -1; outputstream.write(bytes)); 
	            System.out.println("jiaoyihou");

	    
	            
	       
	           
	            
	            outputstream.flush();
	            socket.shutdownOutput();

	           
	           
	            /** 输出返回的结果 */
	            System.out.println("客户端得到返回结果：" + inputstream);
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            // 读取报文前24个字节，获得报文长度
	            String tPackHead = "";
	            String lengStr = "";
	            int x = 0;
	            for (int i = 0; i < 24; i++) {
	                x = inputstream.read();
	                if (x == -1) {
	                    throw new RuntimeException("输入流字节数数小于16");
	                } 
	                tPackHead += (char) x;
	            } 
	            
	            System.out.println("  返回的包头:"+tPackHead);
	        	int length = 0;
	            // 读取报文主体部分

	        	x = inputstream.read();
	            for(length = 0; x!= -1; length++){
	            	baos.write(x);
	                x = inputstream.read();
	            }
	            System.out.println("  返回报文长度为：" + length);
	            baos.flush();
	            baos.close();
	            InputStream bais = new ByteArrayInputStream(baos
	                    .toByteArray());
	            
	            /** 输出返回的结果 */
	            System.out.println("3.交易结束 !");
	            //输出结果
	            try { 
	            	//将前置机返回信息保存在文件中
	            //	YBTFun.savaStream(bais,"4",tOutMsgFilePath+".xml");
	            	
	            	
//	      		  FileOutputStream fos = new FileOutputStream(new File(tOutMsgFilePath));
//	      	      byte[] rbytes = new byte[1];
//	      	      while (bais.read(rbytes) != -1)
//	      	      {	  
//	      		    fos.write(rbytes); 
//	      		  }
//	      	      fos.flush();
//	      	      fos.close();
	      		  System.out.println("4.交易结果是："); 
	      	   } catch (Exception e) {
	      		  e.printStackTrace(); 
	      	   } 
	            
	            

	            
	            socket.close();

	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }

	        return this.resultDocument;
 
	    }
	 public Document getXmlFromLis(){ 
//		add by zhj 用于测试
//			String mInStr = "D:\\表单\\查询_out.xml";
			String mInStr = "D:\\表单\\签单_outSvc.xml";
//			String mInStr = "D:\\表单\\重打_out.xml";
//			String mInStr = "D:\\表单\\悔单_out.xml";
//		 String mInStr = "D:\\表单\\对账_outSvc.xml";
//			String mInStr = "D:\\表单\\犹豫期退保查询_outSvc.xml";
//		 String mInStr = "D:\\表单\\犹豫期退保_outSvc.xml";
//			String mInStr = "D:\\表单\\犹豫期退保冲正_outSvc.xml";
//			String mInStr = "D:\\表单\\续期缴费查询_out.xml";
//			String mInStr = "D:\\表单\\续期缴费_outSvc.xml";
//			String mInStr = "D:\\表单\\续期缴费冲正_outSvc.xml";
//			String mInStr = "D:\\表单\\满期给付查询_outSvc.xml";
//			String mInStr = "D:\\表单\\满期给付_outSvc.xml";
//			String mInStr = "D:\\表单\\满期给付冲正_out.xml";
//			String mInStr = "D:\\表单\\退保查询_outSvc.xml";
//			String mInStr = "D:\\表单\\退保_outSvc.xml";
//			String mInStr = "D:\\表单\\退保冲正_outSvc.xml";
			InputStream mIs = null;  
			try { 
				mIs = new FileInputStream(mInStr); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
			Document mOutXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
			System.out.println( "此时testUI.getXmlFromLis()中加载"+mInStr);
			//JdomUtil.print(mOutXmlDoc);
	return mOutXmlDoc;
	 }
}
