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
		//�ͻ���IP��127.0.0.1������˿ڣ�39871
		cLogger.info("�ͻ���IP��" + cClientIp + "������˿ڣ�" + cSocket.getLocalPort());
		cThisConfRoot = pThisConfRoot;
		cTranComEle = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		String mOkIp = cThisConfRoot.getChildText(ip);
		if (null!=mOkIp && !mOkIp.contains(cClientIp)) {
			throw new NetException("�Ƿ�ip��"+cClientIp);
		}
	}
	
	/**
	 * ����
	 * @return
	 * @throws Exception
	 */
	public Document receive() throws Exception {
		//Into SocketNetImp.receive()...
		cLogger.info("Into SocketNetImp.receive()...");
		//�������ת��Ϊ�ֽ����鲻�ر���
		byte[] mBodyBytes = 
			IOTrans.toBytes5Close(cSocket.getInputStream());
		cSocket.shutdownInput();
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		
		Element mHeadEle = mXmlDoc.getRootElement().getChild(Head);
		Element mFuncFlagEle = mHeadEle.getChild(FuncFlag);
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mFuncFlagEle.getText() + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		if (null == cFuncFlag) {	//δ�����ڲ������룬ֱ��ȡ������������
			cFuncFlag = mFuncFlagEle.getText();
		}
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		//���Head
		mFuncFlagEle.setText(cFuncFlag);
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		
		cLogger.info("Out SocketNetImp.receive()!");
		return mXmlDoc;
	}
	
	/**
	 * ����
	 * @param pOutNoStd ����Ǳ�׼����
	 * @throws Exception
	 */
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into SocketNetImp.send()...");
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		cSocket.getOutputStream().write(
				JdomUtil.toBytes(pOutNoStd));
		cSocket.shutdownOutput();
		
		cLogger.info("Out SocketNetImp.send()!");
	}
	
	/**
	 * �ر�
	 */
	public final void close() {
		try {
			cSocket.close();
		} catch (IOException ex) {
			cLogger.debug("Socket�����ѹرգ�", ex);
		}
	}
}

