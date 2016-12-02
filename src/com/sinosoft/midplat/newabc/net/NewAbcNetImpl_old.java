package com.sinosoft.midplat.newabc.net;

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
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;

public class NewAbcNetImpl_old extends SocketNetImpl {
	private String cOutFuncFlag = null;
	private String cInsuID = null;
	private String cFuncFlag = null;
	public NewAbcNetImpl_old(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into NewAbcNetImp.receive()...");

		//��ͷ73λ
		byte[] mHeadBytes = new byte[73];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		String package_head = new String(mHeadBytes);
		//4-12 λ�Ǳ����峤��
		cLogger.info("package_head:"+package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //���峤��
		
		//���չ�˾����
		cInsuID=String.valueOf(Integer.parseInt(package_head.substring(12, 19).trim()));
		cLogger.info("cInsuID:"+cInsuID);
		
		cLogger.info("mBodyLen:"+mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; //���е�body�ֽڲ���xml����
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		/**********��������****************/
		cLogger.info("���ܿ�ʼ");
		String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
		cLogger.info("�������");
		/**********�������****************/
		
		System.out.println("���ܺ�ı���:============"+axx);
		StringBuffer abc_xml = new StringBuffer();
		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		abc_xml.append("\n");
		abc_xml.append(axx);
		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
		cLogger.info("UTF-8 ũ�еı���: ");
		JdomUtil.print(mXmlDoc_bank);
		
//		String coding = "GBK";//"UTF-8"  "GBK"
//		Format mFormat = Format.getRawFormat().setIndent("   ").setEncoding(coding);
//		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
//		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
//		mXMLOutputter.output(mXmlDoc_bank,mBaos);
//		String mGBKString=new String(abc_xml.toString().getBytes("UTF-8"),"GBK");
//		
//		
//		
//		Document mXmlDoc = JdomUtil.build(mGBKString.getBytes(),"GBK");
//		cLogger.info("GBK ���з��͹����ı���:");
//		JdomUtil.print(mXmlDoc);
		
		//ȡ��������
		Element mRootEle=mXmlDoc_bank.getRootElement();
		Element cHeader = mRootEle.getChild("Header");
		cOutFuncFlag=cHeader.getChildText("TransCode");
		cLogger.info("ũ�н�����Ϊ=======:"+cOutFuncFlag);
		
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		
//		XPath mXPath = XPath.newInstance("msg/@path");
//		String mMsgPath = mXPath.valueOf(cThisConfRoot);
//		if (null==mMsgPath || "".equals(mMsgPath)) {
//			mMsgPath = mTranCom;
//		} 
		
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_in.xml");
	    SaveMessage.save(mXmlDoc_bank, cTranComEle.getText(), mSaveName.toString());
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
		
		cLogger.info("Out NewAbcNetImp.receive()!");
		return mXmlDoc_bank;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into NewAbcNetImp.send()...");
		
		//���ظ�ũ�е���ʾ��Ϣ������50���ֽڣ�25�����ӣ��������ȡ��һ��(ǰ�������   ����Ҫ�󣬿ͻ��ͺ��Ĺ�ͨȷ�Ͻ��
		Element mesgEle = pOutNoStd.getRootElement().getChild("Header").getChild("RetMsg");
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
		String xmlStr = JdomUtil.toString(pOutNoStd); 
		xmlStr=xmlStr.replace("<?xml version=\"1.0\"?>","");
		cLogger.info("xmlStr ȥ�� xmlͶ���ͻ�ȥ�ı���:"+xmlStr);
		xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		String endxmlStr=AES.Encrypt(xmlStr);
		System.out.println("���ĳ���"+xmlStr);
		
//		byte[] mHeadBytes = new byte[73];
		
		//��������
//		byte[] C1Type="X".getBytes();
//		System.arraycopy(C1Type, 0, mHeadBytes, 0, C1Type.length);
//		//�汾
//		byte[] C3="1.0".getBytes();
//		System.arraycopy(C3, 0, mHeadBytes, 1, C3.length);
		//���ݰ�����
//		byte[] mInLengthBytes = endxmlStr.getBytes("UTF-8");
//		cLogger.info("���ܺ�ı��ĳ���+"+mInLengthBytes.length);
//		String mInCipherBodyLengthStr = String.valueOf(mInLengthBytes.length);
//		byte[] mInLengthBytess = mInCipherBodyLengthStr.getBytes();
//		byte[] dateLenth=new byte[8];
//		
//		System.arraycopy(mInLengthBytess, 0, dateLenth, 0, mInLengthBytess.length);                          
//		System.arraycopy(dateLenth, 0, mHeadBytes, 4, dateLenth.length);
//		
//		cLogger.info("���ͱ���ͷ23423423��"+new String(dateLenth,"UTF-8"));
//		//��˾����
//		byte[] C1TypeT="4518    ".getBytes();
//		System.arraycopy(C1TypeT, 0, mHeadBytes, 12, C1TypeT.length);
//		//���ܱ�ʾ
//		byte[] C1TypeK="1".getBytes();
//		System.arraycopy(C1TypeK, 0, mHeadBytes, 20, C1TypeK.length);
//		//�����㷨
//		byte[] C1TypeM=" ".getBytes();
//		System.arraycopy(C1TypeM, 0, mHeadBytes, 21, C1TypeM.length);
//		//����ѹ����־
//		byte[] C1TypeH=" ".getBytes();
//		System.arraycopy(C1TypeH, 0, mHeadBytes, 22, C1TypeH.length);
//		//����ѹ���㷨
//		byte[] C1TypeY=" ".getBytes();
//		System.arraycopy(C1TypeY, 0, mHeadBytes, 23, C1TypeY.length);
//		//ժҪ�㷨
//		byte[] C1TypeZ=" ".getBytes();
//		System.arraycopy(C1TypeZ, 0, mHeadBytes, 23, C1TypeZ.length);
//		//ժҪ
//		StringBuffer sp=new StringBuffer();;
//		for(int i=0;i<=39;i++){
//			sp.append(" ");
//		}
//		
//		byte[] C2TypeZ=sp.toString().getBytes();
//		System.out.println(C2TypeZ.length);
//		System.arraycopy(C2TypeZ, 0, mHeadBytes, 24, C2TypeZ.length);
//		
//		//Ԥ���ֶ�00000000
//		byte[] temp="00000000".getBytes();
//		System.arraycopy(temp, 0, mHeadBytes, 65, temp.length);
//		for(int i=0;i<mHeadBytes.length;i++){
//			System.out.print("="+mHeadBytes[i]+"=");
//		}
		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml�ַ����ı��ĳ��ȣ�"+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();//new byte[sHeadBytes.getBytes().length];
		
		
		cSocket.getOutputStream().write(array);
		cSocket.getOutputStream().write(endxmlStr.getBytes("UTF-8"));
		cSocket.shutdownOutput();
		
		cLogger.info("Out NewAbcNetImp.send()!");
	}
	
}