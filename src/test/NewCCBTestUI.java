package test;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class NewCCBTestUI {
	private Logger cLogger = Logger.getLogger(getClass());

	private String cIP = null;
	private int cPort = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		String mIP = "127.0.0.1";
//		String mIP = "10.0.4.14";
		int mPort = 39871;
		
		
		//绿灯测试
//		String funcflag = "P53818152";
//		String mInFilePath = "D:/TestXml/zhrs/newccb/testlvdeng.xml";
//		String mOutFilePath = "D:/TestXml/zhrs/newccb/testlvdeng_out.xml";
		//新单试算
		String funcflag = "P53819113";
		String mInFilePath = "D:/task/20161129/newccb/P53819113in_noStd.xml";
		String mOutFilePath = "D:/task/20161129/newccb/P53819113out_noStd.xml";
//		
		//新单确认
//		String funcflag = "P53819152";
//		String mInFilePath = "F:\\xml\\CCB/P53819152_新单确认.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819152_out.xml";

		//打印保单
//		String funcflag = "P53819182";
//		String mInFilePath = "F:\\xml\\CCB\\P53819182_保单打印.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819182__out.xml";
		
		/*//自动冲正*/
//		String funcflag = "P53818154";
//		String mInFilePath = "F:\\xml\\CCB/P53818154_冲正.xml";
//		String mOutFilePath = "F:\\xml\\CCB/P53818154_out.xml";
		
		//重打上笔
//		String funcflag = "P53819184";
//		String mInFilePath = "F:\\xml\\CCB\\P53819184_重打.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819184_out.xml";
		
		//重控核对
//		String funcflag = "P538191A2";
//		String mInFilePath = "F:\\xml\\CCB\\P538191A2_核对.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P538191A2_核对_out.xml";
		
		//确认撤销当日保单
//		String funcflag = "P53819142";
//		String mInFilePath = "F:\\xml\\CCB\\P53819142_撤单.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819142_out.xml";
		
		//绿灯测试
//		String funcflag = "P53818152";
//		String mInFilePath = "D:/task/20161128/newccb/P53818152in_noStd.xml";
//		String mOutFilePath = "D:/task/20161128/newccb/P53818152out_noStd.xml";
		
		//打印投保单
//		String funcflag = "P53819188";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/打印投保单/打印投保单.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/打印投保单/打印投保单_out.xml";
		
		//查询缴纳保费信息
//		String funcflag = "P53819151";
//		String mInFilePath = "D:/TestXml/zhrs/newccb/查询缴纳保费信息.xml";
//		String mOutFilePath = "D:/TestXml/zhrs/newccb/查询缴纳保费信息_out.xml";
		
		//确认续期缴费
//		String funcflag = "P53819156";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认续期缴费/确认续期缴费.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认续期缴费/确认续期缴费_out.xml";
		
		//确认取消续期缴费
//		String funcflag = "P53819154";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认取消续期缴费/确认取消续期缴费.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认取消续期缴费/确认取消续期缴费_out.xml";
		
		//查询满期给付
//		String funcflag = "P53819191";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/查询满期给付/查询满期给付.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/查询满期给付/查询满期给付_out.xml";
		
		//修改保单基本信息
//		String funcflag = "P53819161";
//		String mInFilePath = "D:/TestXml/zhrs/newccb/修改保单基本信息.xml";
//		String mOutFilePath = "D:/TestXml/zhrs/newccb/修改保单基本信息_out.xml";
		
		//查询客户保单
//		String funcflag = "P53819176";
//		String mInFilePath = "F:\\xml\\CCB\\P53819176_客户保单查询.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819176_查询客户保单_out.xml";
		
		
		//查询保单详情
//		String funcflag = "P53819171";
//		String mInFilePath = "F:\\xml\\CCB\\P53819171_查询保单详情.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53819171_查询保单详情_out.xml";
		
		//查询保单历史变动信息
//		String funcflag = "P53819177";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/查询保单历史变动信息/查询保单历史变动信息.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/查询保单历史变动信息/查询保单历史变动信息_out.xml";
		
		//申请退保
//		String funcflag = "P53819143";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/申请退保/申请退保.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/申请退保/申请退保_out.xml";		
		
		//确认退保
//		String funcflag = "P53819144";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认退保/确认退保.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/确认退保/确认退保_out.xml";
		
		//获取保单详情查询
//		String funcflag = "P53817107";
//		String mInFilePath = "F:\\xml\\CCB\\P53817107_获取保单详情.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53817107_获取保单详情_out.xml";
		
		//获取保单详情取数(寿险)
//		String funcflag = "P53816107";
//		String mInFilePath = "F:\\xml\\CCB\\P53816107_获取保单详情取数.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P53816107_获取保单详情取数_out.xml";
		
		//获取保险公司巡点员信息 P538191F1
//		String funcflag = "P538191F1";
//		String mInFilePath = "F:\\xml\\CCB\\P538191F1_驻点.xml";
//		String mOutFilePath = "F:\\xml\\CCB\\P538191F1_驻点_out.xml";
		
			NewCCBTestUI mTestUI = new NewCCBTestUI(mIP,mPort);
			InputStream mIs = new FileInputStream(mInFilePath);
		//	byte[] mOutBytes = mTestUI.sendRequest(funcflag,mIs);
			Document document = JdomUtil.build(mIs,"UTF-8");
			byte[] mOutBytes = mTestUI.sendRequest(funcflag,document);
			Document mOutXmlDoc = JdomUtil.build(mOutBytes , "UTF-8");
			System.out.println("实际返回报文为：");
			JdomUtil.print(mOutXmlDoc);
			OutputStream mFos = new FileOutputStream(mOutFilePath);
			JdomUtil.output(mOutXmlDoc, mFos);
			mFos.flush();
			mFos.close();
			System.out.println("执行结果是---"+mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_RESP_DESC"));
			System.out.println("成功结束！"); 
	}

	public NewCCBTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String funcflag,InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		mSocket.getOutputStream().write(mInClearBodyBytes);
		mSocket.getOutputStream().flush();
		mSocket.shutdownOutput();
		return mInClearBodyBytes;
//		Document document = JdomUtil.build(pInputStream);
//		return sendRequest(funcflag,document);
//		Socket mSocket = new Socket(cIP, cPort);
//
//		long mOldTimeMillis = System.currentTimeMillis();
//		long mCurTimeMillis = mOldTimeMillis;
//
//		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
//		Document mDoc = JdomUtil.build(mInClearBodyBytes ,"UTF-8");
//		Element eSYS_TX_CODE = mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE");
//		String mSYS_TX_CODE = eSYS_TX_CODE.getText();
//		
//		mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE").setText("P5"+mSYS_TX_CODE.substring(2, mSYS_TX_CODE.length()));
////		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText("CCB000000000111111");
////		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("CCB_EmpID").setText("建设银行");
//		mInClearBodyBytes = JdomUtil.toBytes(mDoc ,"UTF-8");
//		//		mInClearBodyBytes = JdomUtil.toBytes(JdomUtil.build(mInClearBodyBytes , "UTF-8"),"UTF-8");
//		
//		//加密 
//		
//		//添加报文头
////		String resHeadPath = "F:/innostd_head.xml"; 
////		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
////		System.out.println("安全报文头："+ new String(mInResHeadBytes) +"over!");
//		
//		String mHeadStr = "POST / HTTP/1.1"+"\r\n"
//				+"Host: 128.192.154.4:13010"+"\r\n"
//				+"Server: BIP 1.0"+"\r\n"
//				+"Date: Wed Mar 26 11:12:49 2014 GMT"+"\r\n"
//				+"Content-Type: application/octet-stream; charset=UTF-8"+"\r\n"
//				+"Content-Length:"+mInClearBodyBytes.length+"\r\n"
//				+"Connection: keep-alive"+"\r\n\r\n" +
//						"SEC_ERROR_CODE:00000000000" +"\r\n"+
//						"SEC_IS_MAC:1"+"\r\n" +
//						"SEC_IS_CONTEXT:1"+"\r\n" +
//						"SEC_IS_ENC:1"+"\r\n" +
//						"SEC_MAC:345789gh98rh3r9f8u3r"+"\r\n" +
//						"SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj"+"\r\n" +
//						"SEC_ID1:408002"+"\r\n" +
//						"SEC_ID2:408003"+"\r\n" +
//						"SEC_TRACE_ID:1143445435"+"\r\n" +
//						"SEC_TX_CODE:A2343423"+"\r\n" +
//						"SEC_TX_TYPE:00000"+"\r\n" +
//						"SEC_RESP_CODE:"+"\r\n" +
//						"SEC_LEN:001498"+"\r\n\r\n";
//		byte[] mInResHeadBytes = mHeadStr.getBytes();
//		
//		byte[] mInCipherBodyBytes = mInClearBodyBytes;
////		byte[] mInCipherBodyBytes = SecAPI.pkgEncrypt( "613001" ,"613001",mInClearBodyBytes);
//		cLogger.info( new String(mInCipherBodyBytes));
//
//		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + mInResHeadBytes.length];
//
//		System.arraycopy(mInResHeadBytes, 0, mInTotalBytes, 0, mInResHeadBytes.length);
////		System.arraycopy("\r\n".getBytes(), 0, mInTotalBytes, 0, "\r\n".getBytes().length);
//		
//		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, mInResHeadBytes.length, mInClearBodyBytes.length);
//		
//		
//		String mInBodyLengthStr = String.valueOf(mInResHeadBytes.length);
//		cLogger.info("请求报文长度：" + mInBodyLengthStr);
//		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
////		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
//
////		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 0, mInCipherBodyBytes.length);
//		System.out.println("整体报文："+ new String(mInTotalBytes));
//		cLogger.info("发送请求报文长度：" + new String(mInTotalBytes).length());
//		cLogger.info("发送请求报文！");
//		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.getOutputStream().flush();
////		mSocket.shutdownOutput();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
//
//		/**以下处理返回报文************************/
//		InputStream mSocketIs = mSocket.getInputStream();
//		cLogger.info("收到返回报文！");
//
//		//获取返回报文
//		byte[] mOutHeadBytes = new byte[1024*50];
//		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
//			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
//			if (-1 == tRead) {
//				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
//			}else if(-1 != tRead){
//				break;
//			}
//			tReadSize += tRead;
//		}
////		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes).trim());
//		cLogger.info("返回报文长度：" + mOutHeadBytes.length);
//
//		
//		String mDataStr = new String(mOutHeadBytes).trim();
//		int index_SEC_LEN = mDataStr.indexOf("SEC_LEN:");
//		int index_Body_SEP = mDataStr.indexOf("\r\n", index_SEC_LEN);
//		
//		String mHeadDataStr = mDataStr.substring(0,index_Body_SEP);
//		
//		System.out.println("安全报文头：\r\n"+mHeadDataStr);
//		
//		
//		String mBodyDataStr = mDataStr.substring(index_Body_SEP,mDataStr.length()).trim();
//		System.out.println(mBodyDataStr);
//
//		mSocket.close();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("客户端接收返回报文完毕！耗时："+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
//		
////		return mCCBCipher.decode(mOutCipherBodyBytes);  //解密
//		return mBodyDataStr.getBytes();
	}
	
	public byte[] sendRequest(String pFuncFlag, Element document) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
		Document mDoc = JdomUtil.build(mInClearBodyBytes ,"UTF-8");
		Element eSYS_TX_CODE = mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE");
		String mSYS_TX_CODE = eSYS_TX_CODE.getText();
		
		mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE").setText("P5"+mSYS_TX_CODE.substring(2, mSYS_TX_CODE.length()));
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText("CCB000000000111111");
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("CCB_EmpID").setText("建设银行");
		mInClearBodyBytes = JdomUtil.toBytes(mDoc ,"UTF-8");
		//		mInClearBodyBytes = JdomUtil.toBytes(JdomUtil.build(mInClearBodyBytes , "UTF-8"),"UTF-8");
		
		//加密 
		
		//添加报文头
//		String resHeadPath = "F:/innostd_head.xml"; 
//		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
//		System.out.println("安全报文头："+ new String(mInResHeadBytes) +"over!");
		
		String mHeadStr = "POST / HTTP/1.1"+"\r\n"
				+"Host: 128.192.154.4:13010"+"\r\n"
				+"Server: BIP 1.0"+"\r\n"
				+"Date: Wed Mar 26 11:12:49 2014 GMT"+"\r\n"
				+"Content-Type: application/octet-stream; charset=UTF-8"+"\r\n"
				+"Content-Length:"+mInClearBodyBytes.length+"\r\n"
				+"Connection: keep-alive"+"\r\n\r\n"+
				"SEC_ERROR_CODE:00000000000" +"\r\n"+
				"SEC_IS_MAC:1"+"\r\n" +
				"SEC_IS_CONTEXT:1"+"\r\n" +
				"SEC_IS_ENC:1"+"\r\n" +
				"SEC_MAC:345789gh98rh3r9f8u3r"+"\r\n" +
				"SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj"+"\r\n" +
				"SEC_ID1:408002"+"\r\n" +
				"SEC_ID2:408003"+"\r\n" +
				"SEC_TRACE_ID:1143445435"+"\r\n" +
				"SEC_TX_CODE:A2343423"+"\r\n" +
				"SEC_TX_TYPE:00000"+"\r\n" +
				"SEC_RESP_CODE:"+"\r\n" +
				"SEC_LEN:001498"+"\r\n\r\n";;
				byte[] mInResHeadBytes = mHeadStr.getBytes();
		
		byte[] mInCipherBodyBytes = mInClearBodyBytes;
//		byte[] mInCipherBodyBytes = SecAPI.pkgEncrypt( "613001" ,"613001",mInClearBodyBytes);
		cLogger.info( new String(mInCipherBodyBytes));

		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + mInResHeadBytes.length];

		System.arraycopy(mInResHeadBytes, 0, mInTotalBytes, 0, mInResHeadBytes.length);
//		System.arraycopy("\r\n".getBytes(), 0, mInTotalBytes, 0, "\r\n".getBytes().length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, mInResHeadBytes.length, mInClearBodyBytes.length);
		
		
		String mInBodyLengthStr = String.valueOf(mInResHeadBytes.length);
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
//		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);

//		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 0, mInCipherBodyBytes.length);
		System.out.println("整体报文："+ new String(mInTotalBytes));
		cLogger.info("发送请求报文长度：" + new String(mInTotalBytes).length());
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.getOutputStream().flush();
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");

		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		//获取返回报文
		byte[] mOutHeadBytes = new byte[1024*50];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}else if(-1 != tRead){
				break;
			}
			tReadSize += tRead;
		}
//		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes).trim());
		cLogger.info("返回报文长度：" + mOutHeadBytes.length);

		
		String mDataStr = new String(mOutHeadBytes).trim();
		int index_SEC_LEN = mDataStr.indexOf("SEC_LEN:");
		int index_Body_SEP = mDataStr.indexOf("\r\n", index_SEC_LEN);
		
		String mHeadDataStr = mDataStr.substring(0,index_Body_SEP);
		
		System.out.println("安全报文头：\r\n"+mHeadDataStr);
		
		
		String mBodyDataStr = mDataStr.substring(index_Body_SEP,mDataStr.length()).trim();
		System.out.println(mBodyDataStr);

		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时："+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //解密
		return mBodyDataStr.getBytes();
	}
	
	public byte[] sendRequest(String pFuncFlag, Document document) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document,"GBK");
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 6];

		// 报文体长度
		String mInBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 6,
				mInClearBodyBytes.length);
		
		
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.getOutputStream().flush();
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		//获取返回报文
		byte[] mOutHeadBytes = new byte[1024*50];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}else if(-1 != tRead){
				break;
			}
			tReadSize += tRead;
		}
		String mBodyDataStr = new String(mOutHeadBytes).trim();
		cLogger.info("返回报文长度：" + mBodyDataStr.length());

		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时："+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //解密
		return mBodyDataStr.getBytes("UTF-8");
	}
}
