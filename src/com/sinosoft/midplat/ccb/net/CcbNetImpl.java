package com.sinosoft.midplat.ccb.net;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.adtec.security.BaseException;
import com.adtec.security.Nobis;
import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;

public class CcbNetImpl extends SocketNetImpl {
	private Nobis cNobis;
	
	public CcbNetImpl(Socket pSocket,Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
		try {
			cNobis = Nobis.nobisFactory();
			cNobis.bisReadKey(SysInfo.cHome + "key/ccbKey.dat");
		} catch (BaseException ex) {
			cLogger.error("������Կ�ļ�ʧ�ܣ�", ex);
		}
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into CcbNetImpl.receive()...");
		
		//������ͷ
		byte[] mHeadBytes = new byte[6];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		System.out.println("�ļ�ͷΪ��"+new String(mHeadBytes));
		cLogger.debug("�����ĳ��ȣ�" + mBodyLength);
		
		//��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
//		System.out.println("�ļ���Ϊ��"+new String(mBodyBytes));
		Element mCcbEle = CcbConf.newInstance().getConf().getRootElement();		
		
		cLogger.info("��ʼ����...");
		mBodyBytes = cNobis.bisPkgDecompressDec(mBodyBytes);
		cLogger.info("���ܳɹ���");
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mTransaction_Header = mRootEle.getChild("Transaction_Header");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(mCcbEle.getChildText("bank"));
		
		Element mZoneNo = new Element("ZoneNo");
		Element mBrNo = new Element("BrNo");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mTransaction_Header.getChildText("BkPlatSeqNo"));
		
		Element mFuncFlag = new Element(FuncFlag);
		String mOutFuncFlagStr = mTransaction_Header.getChildText("BkTxCode");

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
		String mTempStr = mXPath.valueOf(mCcbEle);
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
//		Element mManageCom = new Element(ManageCom);
//		Element mAgentCom = new Element(AgentCom);
//		Element mAgentComName = new Element(AgentComName);
//		Element mAgentName = new Element(AgentName);
//		Element mAgentCode = new Element(AgentCode);
//		Element mUntiCode = new Element("UnitCode");
//		Element mAgentGrade = new Element("AgentGrade");
//		Element mAgentCodeGrade = new Element("AgentCodeGrade");
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
//		mHeadEle.addContent(mManageCom);
//		mHeadEle.addContent(mAgentCom);
//		mHeadEle.addContent(mAgentComName);
//		mHeadEle.addContent(mAgentName);
//		mHeadEle.addContent(mAgentCode);
//		mHeadEle.addContent(mUntiCode);
//		mHeadEle.addContent(mAgentGrade);
//		mHeadEle.addContent(mAgentCodeGrade);
		mHeadEle.addContent(mInNoDoc);
		mRootEle.addContent(mHeadEle);
		JdomUtil.print(mXmlDoc);
		cLogger.info("Out CcbNetImpl.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into CcbNetImpl.send()...");
		JdomUtil.print(pOutNoStd);
		//������Ҫ�ҷ��Ű汣����ӡ��ʽ������ת��Ϊ�ֽ�ʱ�豣��ԭ��ʽ�������κθ�ʽ������
		Format mFormat = Format.getRawFormat().setEncoding("GBK");
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
		cLogger.info("��ʼ����...");
		mBodyBytes = cNobis.bisPkgCompressEnc(new String(mBodyBytes));
		cLogger.info("���ܳɹ���");
		
		byte[] mHeadBytes = new byte[6];
		//�����峤��
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthStr);
		byte[] mLengthBytes = mLengthStr.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mHeadBytes);	//���ͱ���ͷ
		cSocket.getOutputStream().write(mBodyBytes);	//���ͱ�����
		cSocket.shutdownOutput();
		
		cLogger.info("Out CcbNetImpl.send()!");
	}
}
