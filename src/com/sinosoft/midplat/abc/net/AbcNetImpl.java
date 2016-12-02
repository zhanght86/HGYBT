package com.sinosoft.midplat.abc.net;

import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;

public class AbcNetImpl extends SocketNetImpl {
	private String cOutFuncFlag = null;
	private String cInsuID = null;
	private String cFuncFlag = null;
	public AbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		System.out.println("ʲôʱ��ִ��receive��������");
		cLogger.info("Into AbcNetImp.receive()...");

		// ������ͷ
		byte[] mHeadBytes = new byte[16];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		System.out.println("�������ı��ĳ��ȣ�"+mHeadBytes.length);
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		cLogger.debug("�����ĳ��ȣ�" + mBodyLength);
		cOutFuncFlag = new String(mHeadBytes, 6, 4).trim();
		cLogger.info("���״��룺" + cOutFuncFlag);
		cInsuID = new String(mHeadBytes, 10, 6).trim();
		cLogger.info("���չ�˾���룺" + cInsuID);
		
		
		
		//��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		
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
	cLogger.info("���汨����ϣ�"+mSaveName);

		//���ɱ�׼����ͷ
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
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
      
		mRootEle.addContent(mHeadEle);
		
		cLogger.info("Out AbcNetImp.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into AbcNetImp.send()...");
		
		Element mRootEle = pOutNoStd.getRootElement();
		Element tHeadEle = mRootEle.getChild(Head);
		if (tHeadEle != null) {
			tHeadEle.getChild(Flag).setText("0");
			if (cOutFuncFlag.equals("01") || cOutFuncFlag.equals("02")) {
				tHeadEle.getChild(Desc).setName("Mesg");
			}
			tHeadEle.setName("RetData");
			mRootEle.setName("Ret");
		} else {
			Element mHeadEle = (Element) mRootEle.getChild("RetData");
			Element mFlagEle = mHeadEle.getChild(Flag);
			if (CodeDef.RCode_OK == Integer.parseInt(mFlagEle.getText())) {
				mFlagEle.setText("1");
			} else {
				mFlagEle.setText("0");
			}
			mHeadEle.setName("RetData");
		}
		
		//���ظ�ũ�е���ʾ��Ϣ������50���ֽڣ�25�����ӣ��������ȡ��һ��(ǰ�������   ����Ҫ�󣬿ͻ��ͺ��Ĺ�ͨȷ�Ͻ��
		Element mesgEle = pOutNoStd.getRootElement().getChild("RetData").getChild("Mesg");
		String mesg = mesgEle.getText().trim();
		if(mesg.length()>25 && mesg.indexOf("(")!=-1){
			mesg = mesg.substring(0, mesg.indexOf("("));
			
			mesgEle .setText(mesg);
		}
	
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);
		cOutNoStdDoc = mSaveName.toString();
	
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		byte[] mHeadBytes = new byte[6];
		//��ʼ��ǰ6λ����ͷΪ�ո�
		for (int i = 0; i <6; i++) {
			mHeadBytes[i] = ' ';
		}
		//�����峤��
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthStr);
		byte[] mLengthBytes = mLengthStr.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);

		//���ͱ�����
		cSocket.getOutputStream().write(mHeadBytes);
		cSocket.getOutputStream().write(mBodyBytes);
		cSocket.shutdownOutput();
		
		cLogger.info("Out AbcNetImp.send()!");
	}
	
}