package com.sinosoft.midplat.gzbank.net;

import java.net.Socket;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.net.SocketNetImpl;

public class GZBankNetImpl extends SocketNetImpl {
	/**
	 * ���뷵�صĻ�����Ϣ
	 */
	private Element mTransNo = null;		//ǩ�����д���
	private Element mQdBankCode = null;		//ǩ�����д���
	private Element mBankCode = null;		//�������д���
	private Element mBranch = null;			//�����������
	private Element mInsuOrgNo = null;		//���չ�˾��������
	private Element mTransExeDate = null;	//���н�������
	private Element mTransRefGUID = null;	//������ˮ��
	private Element mTeller = null;			//���в���Ա����Ա
	private Element mTellerName = null;		//���в���Ա
	
	private String cInsuID="";      //���չ�˾����
	
	private String cFuncFlag = null;
	private String cOutFuncFlag = null;
	
	public GZBankNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	public Document receive() throws Exception {
		cLogger.info(Thread.currentThread().getName() +"Into GZBankNetImpl.receive()...");
		
		/** ��һ�� ������ͷ */
		byte[] mHeadBytes = new byte[19];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		cLogger.info("Head��" + new String(mHeadBytes));
		
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		cLogger.info("�����ĳ��ȣ�" + mBodyLength);
		cOutFuncFlag = new String(mHeadBytes, 6, 7).trim();
		cLogger.info("���״��룺" + cOutFuncFlag);
		cInsuID = new String(mHeadBytes, 13, 6).trim();
		cLogger.info("���չ�˾���룺" + cInsuID);
		
		// ��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		/**
		 * ��ֵ����
		 */
		mTransNo = (Element) mRootEle.getChild("TransNo").clone();
		mQdBankCode = (Element) mRootEle.getChild("QdBankCode").clone();
		mInsuOrgNo = (Element) mRootEle.getChild("InsuOrgNo").clone();
		mBankCode = (Element) mRootEle.getChild("BankCode").clone();
		mBranch = (Element) mRootEle.getChild("Branch").clone();
		mTransExeDate = (Element) mRootEle.getChild("TransExeDate").clone();
		mTransRefGUID = (Element) mRootEle.getChild("TransRefGUID").clone();
		mTeller = (Element) mRootEle.getChild("Teller").clone();
		mTellerName = (Element) mRootEle.getChild("TellerName").clone();
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		
		XPath mXPath = XPath.newInstance("msg/@path");
		String mMsgPath = mXPath.valueOf(cThisConfRoot);
		if (null==mMsgPath || "".equals(mMsgPath)) {
			mMsgPath = mTranCom;
		} 
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
	    cLogger.info("����Ǳ�׼������ϣ�"+mSaveName);
	    
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
		cLogger.info("Out GZBankNetImpl.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into GZBankNetImpl.send()...");	
		//��ֵ����ͷ��Ϣ
		Element mRootEle = pOutNoStd.getRootElement();
		mRootEle.addContent(mTransNo).addContent(mQdBankCode).addContent(mBankCode).addContent(mBranch)
		.addContent(mInsuOrgNo).addContent(mTransExeDate).addContent(mTransRefGUID).addContent(mTeller)
		.addContent(mTellerName);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cOutNoStdDoc = mSaveName.toString();
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		cLogger.info("���ر��ģ�" + new String(mBodyBytes));
		
		byte[] mHeadBytes = new byte[19];
		// ��ʼ��ǰ19λ����ͷΪ0
		for (int i = 0; i < 19; i++) {
			mHeadBytes[i] = ' ';
		}
		// �����峤��
		String mLengthString = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthString);
		byte[] mLengthBytes = mLengthString.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		// ���״���
		byte[] mFuncFlagBytes = cOutFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mHeadBytes, 6,
				mFuncFlagBytes.length);
		
		//Ŀ�걣�չ�˾����
		System.arraycopy(cInsuID.getBytes(), 0, mHeadBytes, 13,
				cInsuID.getBytes().length);
		
		//���ͱ�����
		cSocket.getOutputStream().write(mHeadBytes);
		cSocket.getOutputStream().write(mBodyBytes);
		cSocket.shutdownOutput();
		cLogger.info("Out GZBankNetImpl.send()!");
	}	
}