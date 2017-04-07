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

		/** ��һ�� ������ͷ */
		byte[] mHeadBytes = new byte[8];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		cLogger.info("Head��" + new String(mHeadBytes));

		int mBodyLen;
		mBodyLen = Integer.parseInt(new String(mHeadBytes, 0, 8).trim());
		cLogger.info("�����ĳ��ȣ�" + mBodyLen);
		
		// ��������
		byte[] mBodyBytes = new byte[mBodyLen];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		cOutFuncFlag = mXmlDoc.getRootElement().getChild("Main").getChildText("TranCode");
		cLogger.info("���״��룺" + cOutFuncFlag);
		
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
		cLogger.info("���汨����ϣ�" + mSaveName);

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
		cLogger.info("���汨����ϣ�"+mSaveName);		
		
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		cLogger.info("���ر��ģ�" + new String(mBodyBytes));

		byte[] mHeadBytes = new byte[8];
		// ��ʼ��ǰ8λ����ͷΪ0
		for (int i = 0; i < 8; i++) {
			mHeadBytes[i] = '0';
		}
		// �����峤��
		String mLengthString = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthString);
		byte[] mLengthBytes = mLengthString.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 8-mLengthBytes.length, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mHeadBytes); // ���ͱ���ͷ
		cSocket.getOutputStream().write(mBodyBytes); // ���ͱ�����
		cSocket.shutdownOutput();

		cLogger.info("Out BocNetImpl.send()!");
	}
}
