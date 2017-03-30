package com.sinosoft.midplat.newccb.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeader;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeaderUtils;

public class NewCcbNetImpllong extends SocketNetImpl {

	String localSecNodeId;
	String remoteSecNodeId;
	SecurityMessageHeader header;
	String mSYS_TX_CODE = "";
	private String mIns_Co_ID = "";

	private byte crlf13 = (byte) 13;
	private byte crlf10 = (byte) 10;
	private StringBuffer request = new StringBuffer(); // 用于保存所有内容

	public NewCcbNetImpllong(Socket pSocket, Element pThisConfRoot)
			throws MidplatException {
		super(pSocket, pThisConfRoot);

	}

	protected byte[] receive(InputStream pIs) throws Exception {
		cLogger.info("Into CcbNetImpl.receive()...");

		String mDataStr = "";

		byte[] crlf = new byte[1];
		int crlfNum = 0; // 已经连接的回车换行数 crlfNum=4为头部结束
		while (pIs.read(crlf) != -1) // 读取头部
		{
			if (crlf[0] == crlf13 || crlf[0] == crlf10) {
				crlfNum++;
			} else {
				crlfNum = 0;
			} // 不是则清
			request = request.append(new String(crlf, 0, 1)); // byte数组相加
			if (crlfNum == 4)
				break;
		}
		String mData = request.toString();
		cLogger.info("报文头信息：" + request.toString());
		int mContent_Length = mData.indexOf("Content-Length:") + 15;
		mContent_Length = Integer.valueOf(mData.substring(mContent_Length,
				mData.indexOf("\r\n", mContent_Length)).trim());

		cLogger.debug("报文体长度：" + mContent_Length);
		readLenData(mContent_Length, pIs);

		mDataStr = request.toString();
		cLogger.debug("收到的数据源：" + mDataStr);
		// 解析数据源
		int index_Connection = mDataStr.indexOf("Connection:");
		int index_SEQ_SEP = mDataStr.indexOf("\r\n", index_Connection);

		mDataStr = mDataStr.substring(index_SEQ_SEP, mDataStr.length()).trim();
		cLogger.debug("安全报文头 + 报文体：" + mDataStr);

		// 解析安全报文头及报文体
		Object[] recv = SecurityMessageHeaderUtils.ummarshal(
				mDataStr.getBytes(), true, new SecurityMessageHeader());
		byte[] responseData = mDataStr.getBytes("UTF-8");
		cLogger.debug("获得明文报文体：" + new String(responseData, "UTF-8"));
		header = (SecurityMessageHeader) recv[0];

		localSecNodeId = header.getRmtSecNodeId();
		remoteSecNodeId = header.getSecNodeId();

		// 保存报文
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread()
				.getName()).append('_').append(NoFactory.nextAppNo())
				.append('_').append(cFuncFlag).append("_in.xml");
		SaveMessage.save(responseData, cTranComEle.getText(),
				mSaveName.toString());
		cLogger.info("保存原始请求报文完毕！文件名：" + mSaveName);

		// 获得报文体数据
		Document mInNoStd = JdomUtil.build(responseData, "UTF-8");
		mSYS_TX_CODE = mInNoStd.getRootElement().getChild("TX_HEADER")
				.getChildTextTrim("SYS_TX_CODE");
		Element mCOM_ENTITY = mInNoStd.getRootElement().getChild("TX_BODY")
				.getChild("ENTITY").getChild("COM_ENTITY");
		if (mCOM_ENTITY != null) {
			mIns_Co_ID = mCOM_ENTITY.getChildTextTrim("Ins_Co_ID");
		}
		mInNoStd.getRootElement().getChild("TX_HEADER")
				.addContent(new Element("LocalID").setText(remoteSecNodeId));
		mInNoStd.getRootElement().getChild("TX_HEADER")
				.addContent(new Element("remoteID").setText(localSecNodeId));
		System.out.println("mSYS_TX_CODE: " + mSYS_TX_CODE);
		responseData = JdomUtil.toBytes(mInNoStd, "UTF-8");
		cLogger.info("Out CcbNetImpl.receive()");
		return responseData;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into CCBControl.getOutBytes()...");
		//
		byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
		mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");

		Format mFormat = Format.getRawFormat().setEncoding("UTF-8")
				.setIndent("   ").setLineSeparator("\n");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(pOutNoStd, mBaos);
		mXmlBytes = mBaos.toByteArray();
		byte[] outSec = mXmlBytes;

		StringBuffer sb_http = new StringBuffer();
		sb_http.append("HTTP/1.1 200 OK\r\n");
		// sb.append("POST / HTTP/1.1\r\n");
		sb_http.append("Host: 127.0.0.1:8080\r\n");
		sb_http.append("Server: BIP 1.0\r\n");
		sb_http.append("Date: Fri Mar  7 10:26:40 2014 GMT\r\n");
		sb_http.append("Content-Type: application/x-www-form-urlencoded\r\n");
		sb_http.append("Content-Length:");

		sb_http.append(outSec.length + "\r\n");
		sb_http.append("Connection: keep-alive\r\n");
		sb_http.append("\r\n");

		byte[] sb_httpBytes = sb_http.toString().getBytes();
		byte[] outData = new byte[sb_httpBytes.length + outSec.length];
		System.arraycopy(sb_httpBytes, 0, outData, 0, sb_httpBytes.length);
		System.arraycopy(outSec, 0, outData, sb_httpBytes.length, outSec.length);

		cLogger.info("Out CCBControl.getOutBytes()!");
		cLogger.debug("拼装后报文为：" + sb_http.toString());

		cSocket.getOutputStream().write(outData); // 发送
		cSocket.shutdownOutput();
	}

	private void readLenData(int size, InputStream input) // 读取定长数据
	{
		int readed = 0; // 已经读取数
		try {
			int available = 0;// input.available(); //可读数
			if (available > (size - readed))
				available = size - readed;
			while (readed < size) {
				while (available == 0) { // 等到有数据可读
					available = input.available(); // 可读数
				}
				if (available > (size - readed))
					available = size - readed; // size-readed--剩余数
				if (available > 2048)
					available = 2048; // size-readed--剩余数
				byte[] buffer = new byte[available];
				int reading = input.read(buffer);
				request = request
						.append(new String(buffer, 0, reading, "UTF-8")); // byte数组相加
				readed += reading; // 已读字符
			}
		} catch (IOException e) {
			System.out.println("Read readLenData Error!");
		}
	}
}
