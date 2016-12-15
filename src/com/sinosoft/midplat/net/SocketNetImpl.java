package com.sinosoft.midplat.net;

import java.io.IOException;

import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;

public class SocketNetImpl implements XmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected final Socket cSocket;
	protected final String cClientIp;
	
	protected final Element cThisConfRoot;
	
	protected final Element cTranComEle;
	protected String cFuncFlag;
	public  String cOutNoStdDoc;
	
	public SocketNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		cSocket = pSocket;
		cClientIp = cSocket.getInetAddress().getHostAddress();
		//客户端IP：127.0.0.1；服务端口：39871
		cLogger.info("客户端IP：" + cClientIp + "；服务端口：" + cSocket.getLocalPort());
		cThisConfRoot = pThisConfRoot;
		cTranComEle = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		String mOkIp = cThisConfRoot.getChildText(ip);
		if (null!=mOkIp && !mOkIp.contains(cClientIp)) {
			throw new NetException("非法ip："+cClientIp);
		}
	}
	
	/**
	 * 接收
	 * @return
	 * @throws Exception
	 */
	public Document receive() throws Exception {
		//Into SocketNetImp.receive()...
		cLogger.info("Into SocketNetImp.receive()...");
		//输入输出转换为字节数组不关闭流
		byte[] mBodyBytes = 
			IOTrans.toBytes5Close(cSocket.getInputStream());
		cSocket.shutdownInput();
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		
		Element mHeadEle = mXmlDoc.getRootElement().getChild(Head);
		Element mFuncFlagEle = mHeadEle.getChild(FuncFlag);
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mFuncFlagEle.getText() + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		if (null == cFuncFlag) {	//未定义内部交易码，直接取传过来的数据
			cFuncFlag = mFuncFlagEle.getText();
		}
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		//填充Head
		mFuncFlagEle.setText(cFuncFlag);
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		
		cLogger.info("Out SocketNetImp.receive()!");
		return mXmlDoc;
	}
	
	/**
	 * 发送
	 * @param pOutNoStd 输出非标准报文
	 * @throws Exception
	 */
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into SocketNetImp.send()...");
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		cSocket.getOutputStream().write(
				JdomUtil.toBytes(pOutNoStd));
		cSocket.shutdownOutput();
		
		cLogger.info("Out SocketNetImp.send()!");
	}
	
	/**
	 * 关闭
	 */
	public final void close() {
		try {
			cSocket.close();
		} catch (IOException ex) {
			cLogger.debug("Socket可能已关闭！", ex);
		}
	}
}

