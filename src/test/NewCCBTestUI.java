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

/**
 * 新建行测试用户界面
 * @author yuantongxin
 */
public class NewCCBTestUI {
	//标识当前类的日志对象,来记录当前类可能发生的异常
	private Logger cLogger = Logger.getLogger(getClass());
	
	//网络之间互连的协议
	private String cIP = null;
	//端口
	private int cPort = 0;
	/**
	 * java程序的入口地址
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//程序开始...
		System.out.println("程序开始...");
		//定义局部网络之间互连的协议并赋值
		String mIP = "127.0.0.1";
//		String mIP = "10.0.4.14";
		//定义局部端口并赋值
		int mPort = 39871;
		
		
		//绿灯测试
//		String funcflag = "P53818152";
//		String mInFilePath = "D:/task/20161206/newccb/P53818152in_noStd.xml";
//		String mOutFilePath = "D:/task/20161206/newccb/P53818152out_noStd.xml";
		//新单试算
		String funcflag = "P53819113";//交易码
		String mInFilePath = "D:/task/20161209/newccb/P53819113in_noStd.xml";//输入文件路径
		String mOutFilePath = "D:/task/20161209/newccb/P53819113out_noStd.xml";//输出文件路径
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
//		String mInFilePath = "D:/task/20161209/newccb/P538191A2in_noStd.xml";
//		String mOutFilePath = "D:/task/20161209/newccb/P538191A2out_noStd.xml";
//		
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
//		String mInFilePath = "D:/task/20161206/newccb/P53819143in_noStd.xml";
//		String mOutFilePath = "D:/task/20161206/newccb/P53819143out_noStd.xml";		
		
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
			//通过局部网络之间互连的协议和端口号创建新建行测试用户界面实例
			NewCCBTestUI mTestUI = new NewCCBTestUI(mIP,mPort);
			//通过输入文件路径创建文件输入流
			InputStream mIs = new FileInputStream(mInFilePath);
		//	byte[] mOutBytes = mTestUI.sendRequest(funcflag,mIs);
			//采用UTF-8字符集编码构建一个文档对象，忽略标签之间的空字符(空格、换行、制表符等)。
			Document document = JdomUtil.build(mIs,"UTF-8");
			//获取新建行测试用户界面实例发送请求(包含交易码和XML文档对象)返回的字节数组
			byte[] mOutBytes = mTestUI.sendRequest(funcflag,document);
			//字节数组采用UTF-8字符集编码构建一个XML文档对象，忽略标签之间的空字符(空格、换行、制表符等)。 构建失败，返回null。
			Document mOutXmlDoc = JdomUtil.build(mOutBytes , "UTF-8");
			//实际返回报文为：
			System.out.println("实际返回报文为：");
			//将XML文档打印到控制台， GBK编码(默认)，缩进3空格。
			JdomUtil.print(mOutXmlDoc);
			//通过输出文件路径创建文件输出流
			OutputStream mFos = new FileOutputStream(mOutFilePath);
			//将输出XML文档输出到文件输出流，GBK编码(默认)，缩进3空格。
			JdomUtil.output(mOutXmlDoc, mFos);
			//刷新文件输出流缓冲区
			mFos.flush();
			//关闭文件输出流
			mFos.close();
			//执行结果是---服务响应描述
			System.out.println("执行结果是---"+mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_RESP_DESC"));
			//成功结束！
			System.out.println("成功结束！"); 
	}

	/**
	 *  新建行测试用户界面有参构造器
	 * @param pIP 网络之间互连的协议
	 * @param pPort 端口号
	 */
	public NewCCBTestUI(String pIP, int pPort) {
		cIP = pIP;//形式网络之间互连的协议赋值给成员网络之间互连的协议
		cPort = pPort;///形式端口号赋值给成员端口号
	}

	/**
	 * 通过交易码和输入流发送请求
	 * @param funcflag 交易码
	 * @param pInputStream 输入字节流
	 * @return 字节数组
	 * @throws Exception
	 */
	public byte[] sendRequest(String funcflag,InputStream pInputStream) throws Exception {
		//Socket连接[网络之间互连的协议地址]:[端口号]
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		//通过网络之间互连的协议地址和端口号创建套接字
		Socket mSocket = new Socket(cIP, cPort);
		//输入输出转换类将流转换为字节数组，关闭流
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		/**
		 * 对套接字的三个操作
		 * 1.写入输出流
		 * 2.刷新缓冲区
		 * 3.
		 */
		//套接字获取输出流写入字节数组
		mSocket.getOutputStream().write(mInClearBodyBytes);
		//套接字获取输出流刷新缓冲区
		mSocket.getOutputStream().flush();
		//
		mSocket.shutdownOutput();
		//返回字节数组
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
	
	/**
	 * 通过交易码和XML文档发送请求
	 * @param pFuncFlag 交易码
	 * @param document XML文档
	 * @return 字节数组
	 * @throws Exception
	 */
	public byte[] sendRequest(String pFuncFlag, Element document) throws Exception {
		//Socket连接[网络之间互连的协议]:[端口号]
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		//通过网络之间互连的协议地址和端口号创建套接字
		Socket mSocket = new Socket(cIP, cPort);
		//系统当前时间毫秒数作为旧时间毫秒数
		long mOldTimeMillis = System.currentTimeMillis();
		//旧时间毫秒数赋值给当前时间毫秒数
		long mCurTimeMillis = mOldTimeMillis;
		//Java文档对象模型工具类将XML文档转换为GBK编码的字节数组，保持原格式。
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		//Java文档对象模型工具类字节数组采用UTF-8编码构建一个文档对象，忽略标签之间的空字符(空格、换行、制表符等)。 构建失败，返回null。
		Document mDoc = JdomUtil.build(mInClearBodyBytes ,"UTF-8");
		//XML文档得到根元素下的报文头子元素得到服务名子元素
		Element eSYS_TX_CODE = mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE");
		//得到服务名元素内容
		String mSYS_TX_CODE = eSYS_TX_CODE.getText();
		//XML文档得到根元素下的报文头子元素设置元素内容为P5[截取服务名下标为2的字符开始到最后一位的字符串]，例:string--(st,ring)-->ring
		mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE").setText("P5"+mSYS_TX_CODE.substring(2, mSYS_TX_CODE.length()));
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText("CCB000000000111111");
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("CCB_EmpID").setText("建设银行");
		//Java文档对象模型工具将XML文档转换为UTF-8编码的字节数组，保持原格式。
		mInClearBodyBytes = JdomUtil.toBytes(mDoc ,"UTF-8");
		//		mInClearBodyBytes = JdomUtil.toBytes(JdomUtil.build(mInClearBodyBytes , "UTF-8"),"UTF-8");
		
		//加密 
		
		//添加报文头
//		String resHeadPath = "F:/innostd_head.xml"; 
//		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
//		System.out.println("安全报文头："+ new String(mInResHeadBytes) +"over!");
		
		//拼接请求头
		String mHeadStr = "POST / HTTP/1.1"+"\r\n"//POST / HTTP/1.1
				+"Host: 128.192.154.4:13010"+"\r\n"//Host: 128.192.154.4:13010
				+"Server: BIP 1.0"+"\r\n"//Server: BIP 1.0
				+"Date: Wed Mar 26 11:12:49 2014 GMT"+"\r\n"//Date: Wed Mar 26 11:12:49 2014 GMT
				+"Content-Type: application/octet-stream; charset=UTF-8"+"\r\n"//Content-Type: application/octet-stream; charset=UTF-8
				+"Content-Length:"+mInClearBodyBytes.length+"\r\n"//Content-Length:[字节数组长度]
				+"Connection: keep-alive"+"\r\n\r\n"+//Connection: keep-alive
				"SEC_ERROR_CODE:00000000000" +"\r\n"+//SEC_ERROR_CODE:00000000000
				"SEC_IS_MAC:1"+"\r\n" +//SEC_IS_MAC:1
				"SEC_IS_CONTEXT:1"+"\r\n" +//SEC_IS_CONTEXT:1
				"SEC_IS_ENC:1"+"\r\n" +//SEC_IS_ENC:1
				"SEC_MAC:345789gh98rh3r9f8u3r"+"\r\n" +//SEC_MAC:345789gh98rh3r9f8u3r
				"SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj"+"\r\n" +//SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj
				"SEC_ID1:408002"+"\r\n" +//SEC_ID1:408002
				"SEC_ID2:408003"+"\r\n" +//SEC_ID2:408003
				"SEC_TRACE_ID:1143445435"+"\r\n" +//SEC_TRACE_ID:1143445435
				"SEC_TX_CODE:A2343423"+"\r\n" +//SEC_TX_CODE:A2343423
				"SEC_TX_TYPE:00000"+"\r\n" +//SEC_TX_TYPE:00000
				"SEC_RESP_CODE:"+"\r\n" +//SEC_RESP_CODE:
				"SEC_LEN:001498"+"\r\n\r\n";;//SEC_LEN:001498
				//请求头得到字节数组
				byte[] mInResHeadBytes = mHeadStr.getBytes();
				
		//XML文档字符数组赋值给最小密码体字节数组
		byte[] mInCipherBodyBytes = mInClearBodyBytes;
//		byte[] mInCipherBodyBytes = SecAPI.pkgEncrypt( "613001" ,"613001",mInClearBodyBytes);
		//最小密码体字节转为字符串
		cLogger.info( new String(mInCipherBodyBytes));
		//初始化元素个数为最小密码体字节数组元素个数与请求头字节数组元素个数和的最小总字节数组
		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + mInResHeadBytes.length];
		//系统级数组复制(请求头字节数组[源数组]，首位[源数组要复制的起始位置]，最小总字节数组[目的数组]，[目的数组放置的起始位置]，请求头字节数组元素个数[复制长度])
		System.arraycopy(mInResHeadBytes, 0, mInTotalBytes, 0, mInResHeadBytes.length);//实现数组之间的复制
//		System.arraycopy("\r\n".getBytes(), 0, mInTotalBytes, 0, "\r\n".getBytes().length);
		//系统级数组复制(XML文档字符数组[源数组]，首位[源数组要复制的起始位置]，最小总字节数组[目的数组]，请求头字节数组下一位[目的数组放置的起始位置]，XML文档字符数组元素个数[复制长度])
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, mInResHeadBytes.length, mInClearBodyBytes.length);
		
		//通过请求头字节数组得到最小体长字符串
		String mInBodyLengthStr = String.valueOf(mInResHeadBytes.length);
		//请求报文长度：最小体长字符串
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		//最小体长字符串得到字节数组
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
//		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);

//		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 0, mInCipherBodyBytes.length);
		//
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
		//Socket连接127.0.0.1:39871
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document,"GBK");
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 6];

		// 报文体长度
		String mInBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		//请求报文长度：1522
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
		//收到返回报文！
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
