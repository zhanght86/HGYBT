package test;

import java.io.EOFException;
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
	//标识当前类的日志对象,来记录当前类可能发生的异常[初始化日志]
	private Logger cLogger = Logger.getLogger(getClass());
	
	//网络之间互连的协议[定义]
	private String cIP = null;
	//端口[定义]
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
//		String mIP = "10.2.0.31";
		String mIP = "127.0.0.1";//[127.0.0.1]
//		String mIP = "10.0.4.14";
		//定义局部端口并赋值
		int mPort = 39871;//[39871]
		
		
		//绿灯测试
//		String funcflag = "P53818152";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/9704_15_111_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/9704_15_111_out.xml";
		//新单试算
		String funcflag = "P53819113";//交易码[P53819113]
		//[D:/task/20161223/newccb/core_local/10452_551_1012_in.xml]
		String mInFilePath = "D:/task/20161223/newccb/core_test/11092_1314_1012_in.xml";//输入文件路径
		//[D:/task/20161223/newccb/local/1012/P53819113in_noStd.xml]
//		mInFilePath="D:/task/20161223/newccb/ybt_local/P53819113in_noStd.xml";
		//[D:/task/20161223/newccb/core_local/10452_551_1012_out.xml]
		String mOutFilePath = "D:/task/20161223/newccb/core_test/11092_1314_1012_out.xml";//输出文件路径
		//D:/task/20161223/newccb/local/1012/P53819113out_noStd.xml
//		mOutFilePath="D:/task/20161223/newccb/ybt_local/P53819113out_noStd.xml";
//		
		//新单确认
//		String funcflag = "P53819152";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/11094_1318_1014_in.xml";
//		String mOutFilePath = "D:/task/20161215/newccb/local/11094_1318_1014_out.xml";
		//打印保单
//		String funcflag = "P53819182";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/9982_317_1032_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/9982_317_1032_out.xml";
		
		/*//自动冲正*/
//		String funcflag = "P53818154";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/11435_57_0004_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/11435_57_0004_out.xml";
		
		//重打上笔
//		String funcflag = "P53819184";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/1287_9_1011_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/1287_9_1011_in.xml";
		
		//重控核对
//		String funcflag = "P538191A2";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/1307_44_108_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/1307_44_108_out.xml";
//		
		//确认撤销当日保单
//		String funcflag = "P53819142";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/6568_66_1015_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/6568_66_1015_out.xml";
		
		//绿灯测试
//		String funcflag = "P53818152";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/9704_15_111_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/9704_15_111_in.xml";
		
		//打印投保单
//		String funcflag = "P53819188";
//		String mInFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/打印投保单/打印投保单.xml";
//		String mOutFilePath = "C:/Users/Administrator/Desktop/liuzk/中韩新建行测试/打印投保单/打印投保单_out.xml";
		
		//查询缴纳保费信息
//		String funcflag = "P53819151";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/11395_477_1033_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/11395_477_1033_out.xml";
		
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
//		String mInFilePath = "D:/task/20161223/newccb/core_test/3497_559_1040_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/3497_559_1040_out.xml";
		
		//查询保单历史变动信息
//		String funcflag = "P53819177";
//		String mInFilePath = "D:/task/20161223/newccb/core_test/6853_144_1042_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/6853_144_1042_out.xml";
		
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
//		String mInFilePath = "D:/task/20161223/newccb/core_test/6853_144_1042_in.xml";
//		String mOutFilePath = "D:/task/20161223/newccb/core_test/6853_144_1042_out.xml";
		
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
			//[File:/task/20161223/newccb/local/1012/P53819113in_noStd.xml]
			InputStream mIs = new FileInputStream(mInFilePath);
		//	byte[] mOutBytes = mTestUI.sendRequest(funcflag,mIs);
			//采用UTF-8字符集编码构建一个文档对象，忽略标签之间的空字符(空格、换行、制表符等)。
			Document document = JdomUtil.build(mIs,"UTF-8");//[Element: <TX/>]忽略空白UTF-8报文
			//获取新建行测试用户界面实例发送请求(包含交易码和XML文档对象)返回的字节数组
			//发送请求[P53819113,文档对象]
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
		cIP = pIP;//形式网络之间互连的协议赋值给成员网络之间互连的协议[初始化成员]
		cPort = pPort;///形式端口号赋值给成员端口号[初始化成员]
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
	@SuppressWarnings("unused")
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
	
	/**
	 * 发送请求
	 * @param pFuncFlag 交易码
	 * @param document 文档对象
	 * @return 字节数组[二进制]
	 * @throws Exception
	 */
	public byte[] sendRequest(String pFuncFlag, Document document) throws Exception {
		//Socket连接127.0.0.1:39871
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		//初始化套接字[IP,端口号]
		Socket mSocket = new Socket(cIP, cPort);
		//早期毫秒数[系统当前时间][1481862732977:2016-12-16 12:32:12]
		long mOldTimeMillis = System.currentTimeMillis();
		//当前毫秒数[默认早期毫秒数]	[1481862784332:2016-12-16 12:33:04]
		long mCurTimeMillis = mOldTimeMillis;
		//文档对象转换为GBK编码的字节数组
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document,"GBK");//GBK二进制序列[UTF-8报文转GBK二进制序列]
		//初始化一个GBK字节数组大六个元素的数组
		//[53, 55, 53, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,...]
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 6];//输入总字节数组[报文GBK二进制序列+6个字节]
		
		// 报文体长度
		//新字节数组长度字符串[报文GBK二进制序列+6个字节后的总字节数]
		//6537[GBK二进制序列字节数]
		String mInBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		//请求报文长度：1522
		//请求报文长度：5759
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		//字节数组长度字符串得到字节数组[53, 55, 53, 57]
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();//GBK二进制序列字节数二进制序列
		//从指定源数组中复制一个数组，复制从起始位置开始，到目标数组的最大索引位置结束
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);//报文GBK总字节数二进制序列加入报文长度字节数组
		//从指定源数组中复制一个数组，复制从索引6位置开始，到目标数组的最大索引位置结束。
		//[53, 55, 53, 57, 0, 0, 60(6), 63, 120, 109, 108, 32, 118, 101, 114, 115, 105, 111, 110, 61, 34, 49, 46, 48, 34, 32, 101, 110, 99, 111, 100, 105, 110, 103, 61, 34, 71, 66, 75, 34, 63, 62, 13, 10, 60, 84, 88, 62, 60, 84, 88, 95, 72, 69, 65, 68, 69, 82, 62, 60, 83, 89, 83, 95, 72, 68, 82, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 72, 68, 82, 95, 76, 69, 78, 62, 60, 83, 89, 83, 95, 80, 75, 71, 95, 86, 82, 83, 78, 62, 48, 49, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 86, 82, 83, 78, 62, 60, 83, 89, 83, 95, 84, 84, 76, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 84, 84, 76, 95, 76, 69, 78, 62, 60, 83, 89, 83, 95, 82, 69, 81, 95, 83, 69, 67, 95, 73, 68, 62, 49, 48, 50, 48, 48, 49, 60, 47, 83, 89, 83, 95, 82, 69, 81, 95, 83, 69, 67, 95, 73, 68, 62, 60, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 67, 95, 73, 68, 62, 49, 48, 56, 48, 49, 49, 60, 47, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 67, 95, 73, 68, 62, 60, 83, 89, 83, 95, 84, 88, 95, 67, 79, 68, 69, 62, 80, 53, 51, 56, 49, 57, 49, 49, 51, 60, 47, 83, 89, 83, 95, 84, 88, 95, 67, 79, 68, 69, 62, 60, 83, 89, 83, 95, 84, 88, 95, 86, 82, 83, 78, 62, 48, 49, 60, 47, 83, 89, 83, 95, 84, 88, 95, 86, 82, 83, 78, 62, 60, 83, 89, 83, 95, 84, 88, 95, 84, 89, 80, 69, 62, 48, 50, 48, 48, 48, 48, 60, 47, 83, 89, 83, 95, 84, 88, 95, 84, 89, 80, 69, 62, 60, 83, 89, 83, 95, 82, 69, 83, 69, 82, 86, 69, 68, 62, 48, 60, 47, 83, 89, 83, 95, 82, 69, 83, 69, 82, 86, 69, 68, 62, 60, 83, 89, 83, 95, 69, 86, 84, 95, 84, 82, 65, 67, 69, 95, 73, 68, 62, 49, 48, 50, 48, 48, 49, 56, 48, 50, 49, 52, 56, 51, 49, 52, 55, 53, 51, 52, 48, 48, 49, 52, 56, 56, 60, 47, 83, 89, 83, 95, 69, 86, 84, 95, 84, 82, 65, 67, 69, 95, 73, 68, 62, 60, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 82, 73, 65, 76, 95, 78, 79, 62, 49, 48, 48, 48, 48, 48, 48, 48, 48, 48, 60, 47, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 82, 73, 65, 76, 95, 78, 79, 62, 60, 83, 89, 83, 95, 80, 75, 71, 95, 84, 89, 80, 69, 62, 49, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 84, 89, 80, 69, 62, 60, 83, 89, 83, 95, 77, 83, 71, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 77, 83, 71, 95, 76, 69, 78, 62, 60, 83, 89, 83, 95, 73, 83, 95, 69, 78, 67, 82, 89, 80, 84, 69, 68, 62, 48, 60, 47, 83, 89, 83, 95, 73, 83, 95, 69, 78, 67, 82, 89, 80, 84, 69, 68, 62, 60, 83, 89, 83, 95, 69, 78, 67, 82, 89, 80, 84, 95, 84, 89, 80, 69, 62, 51, 60, 47, 83, 89, 83, 95, 69, 78, 67, 82, 89, 80, 84, 95, 84, 89, 80, 69, 62, 60, 83, 89, 83, 95, 67, 79, 77, 80, 82, 69, 83, 83, 95, 84, 89, 80, 69, 62, 48, 60, 47, 83, 89, 83, 95, 67, 79, 77, 80, 82, 69, 83, 83, 95, 84, 89, 80, 69, 62, 60, 83, 89, 83, 95, 69, 77, 66, 95, 77, 83, 71, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 69, 77, 66, 95, 77, 83, 71, 95, 76, 69, 78, 62, 60, 83, 89, 83, 95, 82, 69, 81, 95, 84, 73, 77, 69, 62, 50, 48, 49, 54, 49, 50, 51, 49, 48, 57, 50, 53, 51, 52, 49, 50, 53, 60, 47, 83, 89, 83, 95, 82, 69, 81, 95, 84, 73, 77, 69, 62, 60, 83, 89, 83, 95, 84, 73, 77, 69, 95, 76, 69, 70, 84, 62, 48, 48, 48, 49, 49, 57, 55, 48, 51, 60, 47, 83, 89, 83, 95, 84, 73, 77, 69, 95, 76, 69, 70, 84, 62, 60, 83, 89, 83, 95, 80, 75, 71, 95, 83, 84, 83, 95, 84, 89, 80, 69, 62, 48, 48, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 83, 84, 83, 95, 84, 89, 80, 69, 62, 60, 76, 111, 99, 97, 108, 73, 68, 62, 53, 49, 48, 48, 57, 54, 60, 47, 76, 111, 99, 97, 108, 73, 68, 62, 60, 114, 101, 109, 111, 116, 101, 73, 68, 62, 49, 48, 53, 48, 48, 53, 60, 47, 114, 101, 109, 111, 116, 101, 73, 68, 62, 60, 47, 84, 88, 95, 72, 69, 65, 68, 69, 82, 62, 60, 84, 88, 95, 66, 79, 68, 89, 62, 60, 67, 79, 77, 77, 79, 78, 62, 60, 70, 73, 76, 69, 95, 76, 73, 83, 84, 95, 80, 65, 67, 75, 62, 60, 70, 73, 76, 69, 95, 78, 85, 77, 62, 48, 60, 47, 70, 73, 76, 69, 95, 78, 85, 77, 62, 60, 70, 73, 76, 69, 95, 77, 79, 68, 69, 62, 48, 60, 47, 70, 73, 76, 69, 95, 77, 79, 68, 69, 62, 60, 70, 73, 76, 69, 95, 78, 79, 68, 69, 32, 47, 62, 60, 70, 73, 76, 69, 95, 78, 65, 77, 69, 95, 80, 65, 67, 75, 32, 47, 62, 60, 70, 73, 76, 69, 95, 80, 65, 84, 72, 95, 80, 65, 67, 75, 32, 47, 62, 60, 47, 70, 73, 76, 69, 95, 76, 73, 83, 84, 95, 80, 65, 67, 75, 62, 60, 47, 67, 79, 77, 77, 79, 78, 62, 60, 69, 78, 84, 73, 84, 89, 62, 60, 67, 79, 77, 95, 69, 78, 84, 73, 84, 89, 62, 60, 73, 110, 115, 116, 95, 69, 110, 103, 95, 83, 104, 114, 116, 78, 109, 62, 67, 67, 66, 60, 47, 73, 110, 115, 116, 95, 69, 110, 103, 95, 83, 104, 114, 116, 78, 109, 62, 60, 73, 110, 115, 95, 67, 111, 95, 73, 68, 62, 48, 49, 48, 48, 55, 57, 60, 47, 73, 110, 115, 95, 67, 111, 95, 73, 68, 62, 60, 83, 118, 80, 116, 95, 74, 114, 110, 108, 95, 78, 111, 62, 49, 48, 56, 48, 49, 49, 114, 118, 49, 49, 52, 56, 49, 55, 54, 53, 49, 49, 49, 48, 49, 53, 49, 50, 48, 60, 47, 83, 118, 80, 116, 95, 74, 114, 110, 108, 95, 78, 111, 62, 60, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 73, 68, 62, 48, 48, 50, 57, 49, 49, 48, 51, 55, 56, 51, 48, 48, 48, 48, 32, 32, 32, 32, 32, 32, 32, 32, 60, 47, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 73, 68, 62, 60, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 67, 71, 89, 95, 67, 79, 68, 69, 62, 50, 48, 49, 55, 48, 48, 50, 57, 60, 47, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 67, 71, 89, 95, 67, 79, 68, 69, 62, 60, 67, 67, 66, 73, 110, 115, 95, 73, 68, 62, 49, 49, 48, 51, 55, 56, 51, 48, 48, 60, 47, 67, 67, 66, 73, 110, 115, 95, 73, 68, 62, 60, 67, 67, 66, 95, 69, 109, 112, 73, 68, 62, 57, 51, 57, 49, 48, 48, 57, 50, 60, 47, 67, 67, 66, 95, 69, 109, 112, 73, 68, 62, 60, 79, 112, 114, 103, 68, 97, 121, 95, 80, 114, 100, 62, 50, 48, 49, 54, 49, 50, 51, 49, 60, 47, 79, 112, 114, 103, 68, 97, 121, 95, 80, 114, 100, 62, 60, 76, 78, 71, 95, 73, 68, 62, 122, 104, 45, 99, 110, 60, 47, 76, 78, 71, 95, 73, 68, 62, 60, 47, 67, 79, 77, 95, 69, 78, 84, 73, 84, 89, 62, 60, 65, 80, 80, 95, 69, 78, 84, 73, 84, 89, 62, 60, 80, 108, 99, 104, 100, 95, 78, 109, 62, -43, -44, -63, -7, 60, 47, 80, 108, 99, 104, 100, 95, 78, 109, 62, 60, 80, 108, 99, 104, 100, 95, 71, 110, 100, 95, 67, 100, 62, 48, 49, 60, 47, 80, 108, 99, 104, 100, 95, 71, 110, 100, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 66, 114, 116, 104, 95, 68, 116, 62, 49, 57, 54, 51, 48, 54, 50, 54, 60, 47, 80, 108, 99, 104, 100, 95, 66, 114, 116, 104, 95, 68, 116, 62, 60, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 84, 112, 67, 100, 62, 49, 48, 49, 48, 60, 47, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 84, 112, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 78, 111, 62, 51, 50, 48, 49, 55, 55, 49, 57, 54, 51, 48, 54, 50, 54, 49, 48, 49, 54, 60, 47, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 78, 111, 62, 60, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 69, 102, 68, 116, 62, 50, 48, 48, 55, 48, 57, 50, 52, 60, 47, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 69, 102, 68, 116, 62, 60, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 69, 120, 112, 68, 116, 62, 50, 48, 49, 56, 49, 50, 49, 50, 60, 47, 80, 108, 99, 104, 100, 95, 67, 114, 100, 116, 95, 69, 120, 112, 68, 116, 62, 60, 80, 108, 99, 104, 100, 95, 78, 97, 116, 95, 67, 100, 62, 49, 53, 54, 60, 47, 80, 108, 99, 104, 100, 95, 78, 97, 116, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 67, 116, 99, 65, 100, 114, 67, 116, 121, 82, 103, 111, 110, 95, 67, 100, 62, 49, 53, 54, 60, 47, 80, 108, 99, 104, 100, 67, 116, 99, 65, 100, 114, 67, 116, 121, 82, 103, 111, 110, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 80, 114, 111, 118, 95, 67, 100, 62, 49, 49, 48, 48, 48, 48, 60, 47, 80, 108, 99, 104, 100, 95, 80, 114, 111, 118, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 67, 105, 116, 121, 95, 67, 100, 62, 49, 49, 48, 48, 48, 48, 60, 47, 80, 108, 99, 104, 100, 95, 67, 105, 116, 121, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 67, 110, 116, 121, 65, 110, 100, 68, 115, 116, 99, 95, 67, 100, 62, 49, 49, 48, 49, 48, 49, 60, 47, 80, 108, 99, 104, 100, 95, 67, 110, 116, 121, 65, 110, 100, 68, 115, 116, 99, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 68, 116, 108, 95, 65, 100, 114, 95, 67, 110, 116, 110, 116, 62, -74, -85, -75, -60, -75, -60, -75, -60, -75, -60, -75, -60, -54, -57, -54, -57, -54, -57, 60, 47, 80, 108, 99, 104, 100, 95, 68, 116, 108, 95, 65, 100, 114, 95, 67, 110, 116, 110, 116, 62, 60, 80, 108, 99, 104, 100, 95, 67, 111, 109, 109, 95, 65, 100, 114, 32, 47, 62, 60, 80, 108, 99, 104, 100, 95, 90, 105, 112, 69, 67, 68, 62, 49, 48, 48, 48, 48, 48, 60, 47, 80, 108, 99, 104, 100, 95, 90, 105, 112, 69, 67, 68, 62, 60, 80, 108, 99, 104, 100, 70, 105, 120, 84, 101, 108, 73, 116, 110, 108, 68, 115, 116, 99, 78, 111, 62, 56, 54, 60, 47, 80, 108, 99, 104, 100, 70, 105, 120, 84, 101, 108, 73, 116, 110, 108, 68, 115, 116, 99, 78, 111, 62, 60, 80, 108, 99, 104, 100, 70, 105, 120, 84, 101, 108, 68, 109, 115, 116, 68, 115, 116, 99, 78, 111, 62, 48, 49, 48, 60, 47, 80, 108, 99, 104, 100, 70, 105, 120, 84, 101, 108, 68, 109, 115, 116, 68, 115, 116, 99, 78, 111, 62, 60, 80, 108, 99, 104, 100, 95, 70, 105, 120, 95, 84, 101, 108, 78, 111, 62, 51, 56, 54, 52, 51, 53, 54, 52, 60, 47, 80, 108, 99, 104, 100, 95, 70, 105, 120, 95, 84, 101, 108, 78, 111, 62, 60, 80, 108, 99, 104, 100, 77, 111, 118, 101, 84, 101, 108, 73, 116, 108, 68, 115, 116, 99, 78, 111, 62, 56, 54, 60, 47, 80, 108, 99, 104, 100, 77, 111, 118, 101, 84, 101, 108, 73, 116, 108, 68, 115, 116, 99, 78, 111, 62, 60, 80, 108, 99, 104, 100, 95, 77, 111, 118, 101, 95, 84, 101, 108, 78, 111, 62, 49, 51, 51, 53, 53, 54, 53, 57, 56, 55, 55, 60, 47, 80, 108, 99, 104, 100, 95, 77, 111, 118, 101, 95, 84, 101, 108, 78, 111, 62, 60, 80, 108, 99, 104, 100, 95, 69, 109, 97, 105, 108, 95, 65, 100, 114, 32, 47, 62, 60, 80, 108, 99, 104, 100, 95, 79, 99, 112, 95, 67, 100, 62, 65, 48, 48, 48, 48, 60, 47, 80, 108, 99, 104, 100, 95, 79, 99, 112, 95, 67, 100, 62, 60, 80, 108, 99, 104, 100, 95, 89, 114, 95, 73, 110, 99, 109, 65, 109, 62, 51, 48, 48, 48, 48, 48, 48, 46, 48, 48, 60, 47, 80, 108, 99, 104, 100, 95, 89, 114, 95, 73, 110, 99, 109, 65, 109, 62, 60, 70, 97, 109, 95, 89, 114, 95, 73, 110, 99, 109, 65, 109, 62, 51, 48, 48, 48, 48, 48, 48, 46, 48, 48, 60, 47, 70, 97, 109, 95, 89, 114, 95, 7...
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 6,
				mInClearBodyBytes.length);//加入报文GBK二进制序列
		
		//返回此套接字的输出流将 数组长度个字节从输入总字节数组写入套接字输出流。
		mSocket.getOutputStream().write(mInTotalBytes);//写入总字节数二进制序列到套接字输出流
		//Socket[addr=/127.0.0.1,port=39871,localport=53343]返回此套接字的输出流刷新此输出流并强制写出所有缓冲的输出字节
		mSocket.getOutputStream().flush();//强制写出缓冲的输出字节
		
		/**以下处理返回报文************************/
		//Socket[addr=/127.0.0.1,port=39871,localport=53343]返回此套接字的输入流
		InputStream mSocketIs = mSocket.getInputStream();//获取输入流
		//收到返回报文！
		cLogger.info("收到返回报文！");

		//获取返回报文
		//初始化输出报文头字节数组[50k]
		byte[] mOutHeadBytes = new byte[1024*50];//输出报文头
		//遍历输出报文头字节数组
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			//将输入流中最多 元素个数 个数据字节读入字节数组
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			//读入缓冲区的总字节数，如果由于已到达流末尾而不再有数据，则返回 -1
			if (-1 == tRead) {
				//获取报文头出错！实际读入：输出报文头字节数组
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			//未到达流末尾，还有数据
			}else if(-1 != tRead){
				break;
			}
			//读取大小加上读入缓冲区的总字节数
			tReadSize += tRead;
		}
		//报文体数据字符串[报文体字节数组转换为字符串返回副本，忽略前导空白和尾部空白]
		String mBodyDataStr = new String(mOutHeadBytes).trim();
		//返回报文长度：1926[报文体数据字符串的长度]
		cLogger.info("返回报文长度：" + mBodyDataStr.length());
		//Socket[addr=/127.0.0.1,port=39871,localport=54303]关闭套接字
		mSocket.close();
		//当前系统时间毫秒数[1481871563250:2016-12-16 02:59:23]
		mCurTimeMillis = System.currentTimeMillis();
		//客户端接收返回报文完毕！耗时：191.118s
		cLogger.info("客户端接收返回报文完毕！耗时："+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //解密
		/**
		 * <?xml version="1.0" encoding="UTF-8"?>
		    <TX><TX_HEADER><SYS_HDR_LEN>823</SYS_HDR_LEN><SYS_PKG_VRSN>01</SYS_PKG_VRSN><SYS_TTL_LEN>1926</SYS_TTL_LEN><SYS_SND_SEC_ID>510050</SYS_SND_SEC_ID><SYS_REQ_SEC_ID>102001</SYS_REQ_SEC_ID><SYS_TX_TYPE>020000</SYS_TX_TYPE><SYS_EVT_TRACE_ID>1020018021483147534001488</SYS_EVT_TRACE_ID><SYS_SND_SERIAL_NO>1000000000</SYS_SND_SERIAL_NO><SYS_PKG_TYPE>1</SYS_PKG_TYPE><SYS_MSG_LEN>728</SYS_MSG_LEN><SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED><SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE><SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE><SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN><SYS_RECV_TIME>20161223145615581</SYS_RECV_TIME><SYS_RESP_TIME>20161223145615696</SYS_RESP_TIME><SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE><SYS_TX_STATUS>01</SYS_TX_STATUS><SYS_RESP_CODE>ZZZ072000001</SYS_RESP_CODE><SYS_RESP_DESC_LEN>7</SYS_RESP_DESC_LEN><SYS_RESP_DESC>鎻掑叆鏃ュ織澶辫触锛�/SYS_RESP_DESC></TX_HEADER><TX_BODY><COMMON><FILE_LIST_PACK><FILE_NUM>0</FILE_NUM><FILE_MODE>0</FILE_MODE><FILE_NODE /><FILE_NAME_PACK /><FILE_PATH_PACK /><FILE_INFO><FILE_NAME /><FILE_PATH /></FILE_INFO></FILE_LIST_PACK></COMMON><ENTITY><APP_ENTITY><AgIns_Pkg_ID /><Cvr_Num>0</Cvr_Num><Bu_List /><Ins_BillNo /><Tot_InsPrem_Amt /><Init_PyF_Amt /><Anulz_InsPrem_Amt /><Ins_Co_Acrdt_Stff_Nm /><InsCoAcrStCrQuaCtf_ID /><InsPrem_PyF_MtdCd /><AgInsRgAutoDdcn_AccNo /><EcIst_PyF_Amt_Inf /><InsPrem_PyF_Prd_Num /><InsPrem_PyF_Cyc_Cd /></APP_ENTITY><COM_ENTITY><Inst_Eng_ShrtNm>CCB</Inst_Eng_ShrtNm><Ins_Co_ID>010079</Ins_Co_ID><SvPt_Jrnl_No>108011rv11481765111015120</SvPt_Jrnl_No><TXN_ITT_CHNL_ID>002911037830000        </TXN_ITT_CHNL_ID><TXN_ITT_CHNL_CGY_CODE>20170029</TXN_ITT_CHNL_CGY_CODE><CCBIns_ID>110378300</CCBIns_ID><CCB_EmpID>93910092</CCB_EmpID><OprgDay_Prd>20161231</OprgDay_Prd><LNG_ID>zh-cn</LNG_ID><SYS_TX_CODE>P53819113</SYS_TX_CODE><Ins_Co_Acg_Dt /><Ins_Co_Jrnl_No /><Ins_Co_Cst_Svc_Tel>4009-800-800</Ins_Co_Cst_Svc_Tel></COM_ENTITY></ENTITY></TX_BODY></TX>
		 */
		//返回使用UTF-8字符集将此 报文体数据字符串 解码为字节序列，并将结果存储到一个新的字节数组中。
		return mBodyDataStr.getBytes("UTF-8");
	}
}
