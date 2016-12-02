package com.sinosoft.midplat.srcb.net;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.srcb.SrcbConf;

public class SrcbNetImpl extends SocketNetImpl {
	
	public SrcbNetImpl(Socket pSocket,Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into SrcbNetImpl.receive()...");
		
		//������ͷ
		byte[] mHeadBytes = new byte[8];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 8).trim());
		System.out.println("�ļ�ͷΪ��"+new String(mHeadBytes));
		cLogger.debug("�����ĳ��ȣ�" + mBodyLength);
		
		//��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
//		System.out.println("�ļ���Ϊ��"+new String(mBodyBytes));
		Element mSrcbEle = SrcbConf.newInstance().getConf().getRootElement();		
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes,"GBK");
		Element mRootEle = mXmlDoc.getRootElement();
		Element mHeader = mRootEle.getChild("Header");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(mSrcbEle.getChildText("bank"));
		
		Element mZoneNo = new Element("ZoneNo");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mHeader.getChildText("SerialNo"));
		
		Element mFuncFlag = new Element(FuncFlag);
		String mOutFuncFlagStr = mHeader.getChildText("TransCode");

		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		String mTempStr = mXPath.valueOf(mSrcbEle);
		cLogger.info("���ױ�ʾ"+mTempStr);
		if (null==mTempStr || "".equals(mTempStr)) {	//δ�����ⲿ�������ڲ�����ӳ�䣬ֱ��ȡ�ⲿ����
			mFuncFlag.setText(mOutFuncFlagStr);
		} else {	//�ж����ⲿ����ӳ�䣬ȡ���Ӧ�ڲ�����
			mFuncFlag.setText(mTempStr);
		}
		
		/** ���ɱ�׼ͷ����  */
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		System.out.println("InNoDoc"+mSaveName.toString());
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mInNoDoc);
		mRootEle.addContent(mHeadEle);
		JdomUtil.print(mXmlDoc);
		cLogger.info("Out SrcbNetImpl.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into SrcbNetImpl.send()...");
		JdomUtil.print(pOutNoStd);

		Format mFormat = Format.getRawFormat().setEncoding("UTF-8");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(pOutNoStd, mBaos);
		byte[] mBodyBytes = mBaos.toByteArray();
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
	     SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
	     cLogger.info("���汨����ϣ�"+mSaveName);
	     cOutNoStdDoc = mSaveName.toString();

		
		byte[] mHeadBytes = new byte[8];
		//�����峤��
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthStr);
		byte[] mLengthBytes = mLengthStr.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mHeadBytes);	//���ͱ���ͷ
		cSocket.getOutputStream().write(mBodyBytes);	//���ͱ�����
		cSocket.shutdownOutput();
		
		cLogger.info("Out SrcbNetImpl.send()!");
	}
}
