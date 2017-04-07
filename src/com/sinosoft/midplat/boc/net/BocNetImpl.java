package com.sinosoft.midplat.boc.net;

import java.net.Socket;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;

public class BocNetImpl extends SocketNetImpl {

	private String cFuncFlag = null;
	private String cOutFuncFlag = null;

	public BocNetImpl(Socket pSocket, Element pThisConfRoot)
			throws MidplatException {
		super(pSocket, pThisConfRoot);
	}

	@Override
	public Document receive() throws Exception {
		cLogger.info(Thread.currentThread().getName() +"Into BocNetImpl.receive()...");

		/** 第一步 处理报文头 */
		byte[] mHeadBytes = new byte[8];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		cLogger.info("Head：" + new String(mHeadBytes));

		int mBodyLen;
		mBodyLen = Integer.parseInt(new String(mHeadBytes, 0, 8).trim());
		cLogger.info("请求报文长度：" + mBodyLen);
		
		// 处理报文体
		byte[] mBodyBytes = new byte[mBodyLen];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		cOutFuncFlag = mXmlDoc.getRootElement().getChild("Main").getChildText("TranCode");
		cLogger.info("交易代码：" + cOutFuncFlag);
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		XPath mXPath = XPath.newInstance("msg/@path");
		String mMsgPath = mXPath.valueOf(cThisConfRoot);
		if (null == mMsgPath || "".equals(mMsgPath)) {
			mMsgPath = mTranCom;
		}
		XPath mXPath2 = XPath.newInstance("business/funcFlag[@outcode='"
				+ cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread()
				.getName()).append('_').append(NoFactory.nextAppNo())
				.append('_').append(cFuncFlag).append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！" + mSaveName);

		Element mTranComEle = new Element(TranCom);
		mTranComEle.setText(mTranCom);
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mInNoDoc=new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cThisConfRoot.getChildText("BankCode"));
		Element mHeadEle = new Element(Head);

		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
		mHeadEle.addContent(mBankCode);
		
		mXmlDoc = JdomUtil.build(mBodyBytes);

		Element tRootEle = mXmlDoc.getRootElement();
		
		tRootEle.addContent(mHeadEle);	
		JdomUtil.print(mXmlDoc);
		cLogger.info("Out BocNetImpl.receive()!");
		return mXmlDoc;
	}
	@Override
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into BocNetImpl.send()...");
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);		
		
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		cLogger.info("返回报文：" + new String(mBodyBytes));

		byte[] mHeadBytes = new byte[8];
		// 初始化前8位报文头为0
		for (int i = 0; i < 8; i++) {
			mHeadBytes[i] = '0';
		}
		// 报文体长度
		String mLengthString = String.valueOf(mBodyBytes.length);
		cLogger.info("返回报文长度：" + mLengthString);
		byte[] mLengthBytes = mLengthString.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 8-mLengthBytes.length, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mHeadBytes); // 发送报文头
		cSocket.getOutputStream().write(mBodyBytes); // 发送报文体
		cSocket.shutdownOutput();

		cLogger.info("Out BocNetImpl.send()!");
	}
}
