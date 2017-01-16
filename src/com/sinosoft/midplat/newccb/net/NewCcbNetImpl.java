package com.sinosoft.midplat.newccb.net;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.format.ErrorOutXsl;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeader;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeaderUtils;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class NewCcbNetImpl extends SocketNetImpl
{

	//13个回车换行
	private byte crlf13 = (byte) 13;
	//10个回车换行
	private byte crlf10 = (byte) 10;
	//请求字符串缓冲
	private StringBuffer request = new StringBuffer();
	private String mSYS_TX_CODE = "";
	private SecurityMessageHeader header;
	private String localSecNodeId;
	private String remoteSecNodeId;
	
	/**服务接收时间*/
	public String mSYS_RECV_TIME = null;
	
	/**服务响应时间*/
	public String mSYS_RESP_TIME = null;
	
	/**接收到的请求报文*/
	public Document mInDoc = null;

	// private String mIns_Co_ID = "";

	public NewCcbNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException
	{
		super(pSocket, pThisConfRoot);
	}

	/**
	 * 接收[加密报文]
	 */
	public Document receive() throws Exception
	{
		// 服务接受时间[简单日期格式化当前日期]
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//Into NewCcbNetImpl.receive()...
		cLogger.info("Into NewCcbNetImpl.receive()...");
		// 获得输入流
		//获取套接字字节输入流
		InputStream mIs = cSocket.getInputStream();
		// 读取HTTP报文头
		//数据字符串
		String mDataStr = "";
		//定义了一个长度为1的字节数组
		byte[] crlf = new byte[1];
		//回车换行数
		int crlfNum = 0; // 已经连接的回车换行数 crlfNum=4为头部结束
		//从输入流中读取一个字节并将其存储在缓冲区数组 crlf 中
		while (mIs.read(crlf) != -1) // 读取头部
		{
			//首个字节为13个回车换行、10个回车换行
			if (crlf[0] == crlf13 || crlf[0] == crlf10)
			{
				//回车换行数加一
				crlfNum++;
			}
			else//其他
			{
				//回车换行数置为0
				crlfNum = 0;
			}
			//请求字符串缓冲追加使用UTF-8字符集解码的字节数组字符串
			request = request.append(new String(crlf, 0, 1, "UTF-8")); // byte数组相加
			//回车换行数为4[头部结束]
			if (crlfNum == 4)
				//退出循环
				break;
		}
		// 获得安全报文头和报文的长度
		//请求字符串缓冲数据
		String mData = request.toString();
		/*
		 * POST / HTTP/1.1<!--HTTP 协议报文头-->
			Host: 128.32.96.74:39871
			Server: BIP 1.0
			Date: Sun Jan 15 20:02:00 2017 GMT
			Content-Type: application/octet-stream; charset=UTF-8
			Content-Length: 2660
			Connection: keep-alive
		  */
		//报文头信息：请求字符串缓冲数据
		cLogger.info("报文头信息：" + request.toString());
		//==================20140901==================
		cLogger.info("==================20140901==================");
		//请求字符串缓冲数据内容长度
		//138+15=153
		int mContent_Length = mData.indexOf("Content-Length:") + 15;
		//截取行首:到行尾换行之间的字符串解析为整型
		mContent_Length = Integer.parseInt(mData.substring(mContent_Length, mData.indexOf("\r\n", mContent_Length)).trim());
		//报文体长度：内容长度
		cLogger.info("报文体长度：" + mContent_Length);
		// 读取安全报文头和加密的报文
		//从字节输入流读取内容长度个数据
		readLenData(mContent_Length, mIs);
		//数据字符串赋值为请求字符串缓冲
		mDataStr = request.toString();
		/*
		 * POST / HTTP/1.1<!--HTTP 协议报文头-->
			Host: 128.32.96.74:39871
			Server: BIP 1.0
			Date: Sun Jan 15 20:02:00 2017 GMT
			Content-Type: application/octet-stream; charset=UTF-8
			Content-Length: 2660
			Connection: keep-alive
		 */
		//收到的数据源：数据字符串
		cLogger.debug("收到的数据源：" + mDataStr);
		//
		int index_Connection = mDataStr.indexOf("SEC_ERROR_CODE:");
		mDataStr = mDataStr.substring(index_Connection, mDataStr.length()).trim();
		/*
		 *	SEC_ERROR_CODE:000000000000<!--安全协议报文头-->
			SEC_IS_MAC:1
			SEC_IS_CONTEXT:0
			SEC_IS_ENC:1
			SEC_MAC:DQOUVXrIYtfuviLMJDpuyAiPIGJ3ng==
			SEC_ID1:105005
			SEC_ID2:510096
			SEC_TRACE_ID:108011rv11484481690013205
			SEC_TX_CODE:P5381B123
			SEC_TX_TYPE:00
			SEC_LEN:2416 
			<!--加密报文体-->
			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
		 */
		cLogger.debug("安全报文头 + 报文体：" + mDataStr);
		// 解析安全报文头及报文体
		//解密安全报文头+报文体
		Object[] recv = SecurityMessageHeaderUtils.ummarshal(mDataStr.getBytes(), true, new SecurityMessageHeader());
		//响应数据字节数组赋值为第二个元素[报文体]
		byte[] responseData = (byte[]) recv[1];
		//获得明文报文体：使用UTF-8字符集解码报文体字节数组为字符串
		cLogger.debug("获得明文报文体：" + new String(responseData, "UTF-8"));
		//安全报文头赋值为首个元素[安全报文头]
		header = (SecurityMessageHeader) recv[0];
		// 发送方安全节点编号
		localSecNodeId = header.getRmtSecNodeId();
		// 目标安全节点号
		remoteSecNodeId = header.getSecNodeId();

		// 获得报文体数据
		Document mInNoStd = JdomUtil.build(responseData, "UTF-8");
		
		//复制接收到的报文储入类变量中
		mInDoc = (Document) mInNoStd.clone();
		
		mSYS_TX_CODE = mInNoStd.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE");
		Element mCOM_ENTITY = mInNoStd.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");

		mInNoStd.getRootElement().getChild("TX_HEADER").addContent(new Element("LocalID").setText(remoteSecNodeId));
		mInNoStd.getRootElement().getChild("TX_HEADER").addContent(new Element("remoteID").setText(localSecNodeId));

		// 取出交易码
		Element mRootEle = mInNoStd.getRootElement();
		Element cHeader = mRootEle.getChild("TX_HEADER");
		mSYS_TX_CODE = cHeader.getChildText("SYS_TX_CODE");
		cLogger.info("新建行交易码为==" + mSYS_TX_CODE);
		// JdomUtil.print(cThisConfRoot);
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		cLogger.info("mTranCom==============" + mTranCom);

		XPath mXPath2 = XPath.newInstance("business/funcFlag[@outcode='" + mSYS_TX_CODE + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);

		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cFuncFlag).append("_in.xml");
		SaveMessage.save(mInNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！" + mSaveName);

		// 生成标准报文头
		Element mTranComEle = new Element(TranCom);
		mTranComEle.setText(mTranCom);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
		mHeadEle.addContent(mTranNo);
		mRootEle.addContent(mHeadEle);
		cLogger.info("增加标准报文头节点后报文：");
		JdomUtil.print(mInNoStd);

		cLogger.info("Out NewCcbNetImpl.receive()...");
		return mInNoStd;
	}

	public void send(Document pOutNoStd) throws Exception
	{
		// 服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		cLogger.info("Into NewCcbNetImpl.send()...");
		// 报文头长度
		cLogger.info("发送给银行的报文：");
		JdomUtil.print(pOutNoStd);
		
		//处理返回报文的异常报文,根据根节点是否为[TX]进行判断,因为异常标准报文根节点为[TranData]
		if(!"TX".equals(pOutNoStd.getRootElement().getName()) && "TranData".equals(pOutNoStd.getRootElement().getName()))
		{
			pOutNoStd = dealErrorMsg((Document)pOutNoStd.clone());
			cLogger.info("异常报文转换后的报文：");
		}
		
		Element mHeadEle = pOutNoStd.getRootElement().getChild("TX_HEADER");
		int mHeadEleLength = JdomUtil.toBytes(mHeadEle, "UTF-8").length;
		mHeadEleLength += String.valueOf(mHeadEleLength).length() - 1;
		mHeadEle.getChild("SYS_HDR_LEN").setText(String.valueOf(mHeadEleLength));
		// 报文总长度
		byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
		int mXmlDocLength = mXmlBytes.length;
		mXmlDocLength += String.valueOf(mXmlDocLength).length() - 1;
		mHeadEle.getChild("SYS_TTL_LEN").setText(String.valueOf(mXmlDocLength));

		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cFuncFlag).append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！" + mSaveName);
		this.cOutNoStdDoc = mSaveName.toString();
		Format mFormat = Format.getRawFormat().setEncoding("UTF-8").setIndent("   ").setLineSeparator("\n");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(pOutNoStd, mBaos);
		mXmlBytes = mBaos.toByteArray();

		Document cXmlDoc = JdomUtil.build(mXmlBytes, "UTF-8");
		// cLogger.info("保险公司编码："+cXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("Ins_Co_ID"));
		cLogger.info("====" + JdomUtil.toString(cXmlDoc));
		// 拼装安全报文头及报文体
		byte[] outSec = SecurityMessageHeaderUtils.marshal(header, JdomUtil.toBytes(cXmlDoc,"UTF-8"), true);

		cLogger.debug("加密后内容：\n" + new String(outSec));

		// 拼装HTTP协议头
		StringBuffer sb_http = new StringBuffer();
		sb_http.append("HTTP/1.1 200 OK\r\n");
		sb_http.append("Host: 15.128.4.2: 9000\r\n");
		sb_http.append("Server: BIP 1.0\r\n");
		sb_http.append("Date: (2014-6-3 10:44:05)\r\n");
		sb_http.append("Content-Type: application/x-www-form-urlencoded\r\n");
		sb_http.append("Content-Length:");

		sb_http.append(outSec.length + "\r\n");
		sb_http.append("Connection: keep-alive\r\n");
		sb_http.append("\r\n");

		byte[] sb_httpBytes = sb_http.toString().getBytes();
		byte[] outData = new byte[sb_httpBytes.length + outSec.length];
		System.arraycopy(sb_httpBytes, 0, outData, 0, sb_httpBytes.length);
		System.arraycopy(outSec, 0, outData, sb_httpBytes.length, outSec.length);
		cLogger.debug("拼装后报文为：" + sb_http.toString());

		cSocket.getOutputStream().write(outData);
		cSocket.getOutputStream().flush();

		cLogger.info("Into NewCcbNetImpl.send()...");
	}

	/**
	 * @Title: readLenData 
	 * @Description: 读取指定长度数据
	 * @param size 长度
	 * @param input 字节输入流
	 * @return void 
	 * @throws 异常
	 */
	private void readLenData(int size, InputStream input) // 读取定长数据
	{
		int readed = 0; // 已经读取数
		try
		{
			int available = 0;// input.available(); //可读数
			if (available > (size - readed))
				available = size - readed;
			while (readed < size)
			{
				while (available == 0)
				{ // 等到有数据可读
					available = input.available(); // 可读数
				}
				if (available > (size - readed))
					available = size - readed; // size-readed--剩余数
				if (available > 2048)
					available = 2048; // size-readed--剩余数
				byte[] buffer = new byte[available];
				int reading = input.read(buffer);
				request = request.append(new String(buffer, 0, reading)); // byte数组相加
				readed += reading; // 已读字符
			}
		}
		catch (java.io.IOException e)
		{
			cLogger.info("Read readLenData Error!");
		}
	}

	/**
	 * 
	 * dealErrorMsg 处理异常报文信息
	 *
	 * @param outDoc 异常报文
	 * @return 返回非标准报文
	 */
	private  Document dealErrorMsg(Document outStdDoc)
	{
		Document outNoStdDoc = null;
		
		if(outStdDoc != null)
		{
			try
			{
				outNoStdDoc = ErrorOutXsl.newInstance().getCache().transform(outStdDoc);
				
				//获得请求报文报文头
				if(mInDoc != null)
				{
					Element header = (Element) mInDoc.getRootElement().getChild("TX_HEADER").clone();
					
					//设置返回报文报文头信息
					outNoStdDoc = NewCcbFormatUtil.setNoStdTxHeader(outNoStdDoc, header, mSYS_RECV_TIME, mSYS_RESP_TIME);
					
					Element mRetData = outStdDoc.getRootElement().getChild("Head");
					if (mRetData.getChildText(Flag).equals("0"))
					{ // 交易成功
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText("交易成功！");
					}
					else
					{ // 交易失败
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");// 返回通用错误代码
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
					}
				}
				
			}
			catch (XSLTransformException e)
			{
				cLogger.error("返回报文转换异常：" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return outNoStdDoc;
	}	
}
