package com.sinosoft.midplat.newccb.net;

import java.io.ByteArrayOutputStream;
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

	private byte crlf13 = (byte) 13;
	private byte crlf10 = (byte) 10;
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

	public Document receive() throws Exception
	{
		// 服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		cLogger.info("Into NewCcbNetImpl.receive()...");
		// 获得输入流
		InputStream mIs = cSocket.getInputStream();
		// 读取HTTP报文头
		String mDataStr = "";
		byte[] crlf = new byte[1];
		int crlfNum = 0; // 已经连接的回车换行数 crlfNum=4为头部结束
		while (mIs.read(crlf) != -1) // 读取头部
		{
			if (crlf[0] == crlf13 || crlf[0] == crlf10)
			{
				crlfNum++;
			}
			else
			{
				crlfNum = 0;
			}
			request = request.append(new String(crlf, 0, 1, "UTF-8")); // byte数组相加
			if (crlfNum == 4)
				break;
		}
		// 获得安全报文头和报文的长度
		String mData = request.toString();
		cLogger.info("报文头信息：" + request.toString());
		int mContent_Length = mData.indexOf("Content-Length:") + 15;
		mContent_Length = Integer.parseInt(mData.substring(mContent_Length, mData.indexOf("\r\n", mContent_Length)).trim());
		cLogger.info("报文体长度：" + mContent_Length);
		// 读取安全报文头和加密的报文
		readLenData(mContent_Length, mIs);
		mDataStr = request.toString();
		cLogger.debug("收到的数据源：" + mDataStr);
		int index_Connection = mDataStr.indexOf("SEC_ERROR_CODE:");
		mDataStr = mDataStr.substring(index_Connection, mDataStr.length()).trim();
		cLogger.debug("安全报文头 + 报文体：" + mDataStr);
		// 解析安全报文头及报文体
		Object[] recv = SecurityMessageHeaderUtils.ummarshal(mDataStr.getBytes(), true, new SecurityMessageHeader());
		byte[] responseData = (byte[]) recv[1];
		cLogger.debug("获得明文报文体：" + new String(responseData, "UTF-8"));
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
		String mAgentCom=this.cThisConfRoot.getChildText("AgentCom");
		this.cLogger.info("AgentCom=============="+mAgentCom);
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
		Element mAgentComEle = new Element(AgentCom);
		mAgentComEle.setText(mAgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentComEle);
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
