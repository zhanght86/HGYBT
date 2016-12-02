package com.sinosoft.midplat.boc.net;

import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;

public class BocNetImpl extends SocketNetImpl {
	public BocNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	//������ˮ��
	private  String TransNo;
	private  String TranCom;
	private  String mOutFuncFlagStr; 
	public Document receive() throws Exception {
		cLogger.info("Into BocNetImpl.receive()...");
		// ������ͷ
		byte[] mHeadBytes = new byte[8];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 8).trim());
		cLogger.info("�����ĳ��ȣ�" + mBodyLength);
		
		
		//��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Element mBocEle = BocConf.newInstance().getConf().getRootElement();		
		
		Document mXmlDoc =JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mMain = mRootEle.getChild("Main");
		Element mFuncFlag = new Element(FuncFlag);
	    mOutFuncFlagStr = mMain.getChildText("TranCode");
		
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_in.xml");
	    TransNo=mMain.getChildText("TransNo");
	    TranCom=mBocEle.getChildText("TranCom");
//		StringBuffer mSaveName = new StringBuffer(TransNo)
//		.append('_').append(mOutFuncFlagStr)
//		.append('_').append(new SimpleDateFormat("HHmmss").format(new Date()))
//		.append(".xml");
//	    SaveMessage.save(mBodyBytes,TranCom,"InNoStd",mSaveName.toString());
	    SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
	    this.cOutNoStdDoc=mSaveName.toString();
	    cLogger.info("����Ǳ�׼������ϣ�"+mSaveName);
	    mXPath = XPath.newInstance("business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		String mTempStr = mXPath.valueOf(mBocEle);
		cLogger.info("���ױ�ʾ"+mTempStr);
		if (null==mTempStr || "".equals(mTempStr)) {	
			//δ�����ⲿ�������ڲ�����ӳ�䣬ֱ��ȡ�ⲿ����
			mFuncFlag.setText(mOutFuncFlagStr);
		} else {	
			//�ж����ⲿ����ӳ�䣬ȡ���Ӧ�ڲ�����
			mFuncFlag.setText(mTempStr);
		}
		Element mHeadEle = new Element("Head");
		Element mTranCom=new Element("TranCom");
		Element mInNoDoc=new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		mTranCom.setText(TranCom);
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		mHeadEle.addContent(mFuncFlag);
		mHeadEle.addContent(mTranCom);
		mHeadEle.addContent(mInNoDoc);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mClientIpEle);
		mRootEle.addContent(mHeadEle);
		cLogger.info("Out BocNetImpl.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into BocNetImpl.send()...");					
		//�޸�֮ǰ���ظ����������Ĵ洢����
//		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
//		.append('_').append(NoFactory.nextAppNo())
//		.append('_').append(cFuncFlag)
//		.append("_out.xml");
//		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		StringBuffer mSaveName = new StringBuffer(TransNo)
		.append('_').append(mOutFuncFlagStr)
		.append('_').append(new SimpleDateFormat("HHmmss").format(new Date()))
		.append(".xml");
//		SaveMessage.save(mBodyBytes,TranCom,"OutNoStd", mSaveName.toString());
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("�������б��ı�����ϣ�"+mSaveName);
		
		byte[] mTotalBytes = new byte[mBodyBytes.length + 8];
		//�����峤��
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthStr);
		byte[] mLengthBytes = mLengthStr.getBytes();
		System.arraycopy("00000000".getBytes(), 0, mTotalBytes, 0, 8-mLengthBytes.length);
		System.arraycopy(mLengthBytes, 0, mTotalBytes, 8-mLengthBytes.length, mLengthBytes.length);
		System.arraycopy(mBodyBytes, 0, mTotalBytes, 8, mBodyBytes.length);
		//���ͱ�����
		cSocket.getOutputStream().write(mTotalBytes);
		cSocket.shutdownOutput();
		
		cLogger.info("Out BocNetImpl.send()!");
	}	
}