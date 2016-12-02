package com.sinosoft.midplat.jhyh.net;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.adtec.security.BaseException;
import com.adtec.security.Nobis;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.jhyh.JhyhConf;
import com.sinosoft.midplat.net.SocketNetImpl;

public class JhyhNetImpl extends SocketNetImpl {
	private Nobis cNobis;
	
	public JhyhNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
//		try {
//			cNobis = Nobis.nobisFactory();
//			cNobis.bisReadKey(SysInfo.cHome + "key/jhyhKey.dat");
//		} catch (BaseException ex) {
//			cLogger.error("������Կ�ļ�ʧ�ܣ�", ex);
//		}
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into jhyhNetImpl.receive()...");

		//������ͷ
		byte[] mHeadBytes = new byte[6];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		System.out.println("����ͷΪ��"+new String(mHeadBytes));
		cLogger.info("�����ĳ��ȣ�" + mBodyLength);
		
		//��������
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		System.out.println("������Ϊ��"+new String(mBodyBytes));
		cSocket.shutdownInput();
		System.out.println("�ܱ����壺"+new String(mHeadBytes)+new String(mBodyBytes));
		Element mJhyhEle = JhyhConf.newInstance().getConf().getRootElement();		
		
		//������ͬ�Ͻ��ж��Ǻ����������Ľӿڣ������б����ǲ����ܵ�
//		cLogger.info("��ʼ����...");
//		mBodyBytes = cNobis.bisPkgDecompressDec(mBodyBytes);
//		cLogger.info("���ܳɹ���");
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mTransaction_Header = mRootEle.getChild("Transaction_Header");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(mJhyhEle.getChildText("bank"));
		
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
		String mTempStr = mXPath.valueOf(mJhyhEle);
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
		
		cLogger.info("Out jhyhNetImp.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into jhyhNetImpl.send()...");

		JdomUtil.print(pOutNoStd);
		
		String xmlStr = JdomUtil.toString(pOutNoStd);
		System.out.println("ת��ǰ��String���ģ�"+xmlStr);
		xmlStr=xmlStr.replace('\r', ' ');//ȥ���س�
		System.out.println("ȥ���س����String���ģ�"+xmlStr);
		xmlStr=xmlStr.replace('\n', ' ');//ȥ������
		System.out.println("ȥ�����к��String���ģ�"+xmlStr);
		Document mOutXmlDoc = JdomUtil.build(xmlStr);
		//���ƺ���������Linux����ʾ��^M�����У���ʶ
		cLogger.info("���´�ӡ���ģ�");
		JdomUtil.print(mOutXmlDoc);
		
		
		//��Ҫ�ҷ��Ű汣����ӡ��ʽ������ת��Ϊ�ֽ�ʱ�豣��ԭ��ʽ�������κθ�ʽ������
		Format mFormat = Format.getRawFormat().setEncoding("GBK");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(mOutXmlDoc, mBaos);
		byte[] mBodyBytes = mBaos.toByteArray();
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
	     SaveMessage.save(mOutXmlDoc, cTranComEle.getText(), mSaveName.toString());
	     cLogger.info("���汨����ϣ�"+mSaveName);
	     cOutNoStdDoc = mSaveName.toString();
	
		//������ͬ�Ͻ��ж��Ǻ����������Ľӿڣ������б����ǲ����ܵ�
//		cLogger.info("��ʼ����...");
//		mBodyBytes = cNobis.bisPkgCompressEnc(new String(mBodyBytes));
//		cLogger.info("���ܳɹ���");
		
		byte[] mHeadBytes = new byte[6];
		//�����峤��
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("���ر��ĳ��ȣ�" + mLengthStr);
		int g = mLengthStr.length();
		if(g<=6){
			for (int i = 0; i < 6-g; i++) {
				mLengthStr =  "0" + mLengthStr;
			}
		}
		cLogger.info("����6λ���ر��ĳ��ȣ�" + mLengthStr);
		
//		byte[] mLengthBytes = mLengthStr.getBytes();
//		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mLengthStr.getBytes());	//���ͱ����峤��
//		cSocket.getOutputStream().write(mHeadBytes);	//���ͱ���ͷ
		cSocket.getOutputStream().write(mBodyBytes);	//���ͱ�����
		cSocket.shutdownOutput();
		
		cLogger.info("Out jhyhNetImpl.send()!");
	}

}