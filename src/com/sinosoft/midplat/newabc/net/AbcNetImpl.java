package com.sinosoft.midplat.newabc.net;

import java.io.File;
import java.io.FileOutputStream;
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
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;
//import common.utils.StringUtility;

public class AbcNetImpl extends SocketNetImpl {
	private String cOutFuncFlag = null;
	private String cFuncFlagStr = null;
	private String cInsuID = null;
	private String cFuncFlag = null;
	public AbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into AbcNetImp.receive()...");

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
		//ȡ��������
		Element mRootEle=mXmlDoc_bank.getRootElement();
		Element cHeader = mRootEle.getChild("Header");
		cOutFuncFlag=cHeader.getChildText("TransCode");
		cLogger.info("ũ�н�����Ϊ=======:"+cOutFuncFlag);
		
		Element mAbcEle = NewAbcConf.newInstance().getConf().getRootElement();
		String mTranCom = mAbcEle.getChildText("TranCom");
		
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(NewAbcConf.newInstance());
		String AppNo = cHeader.getChildText("SerialNo");
		StringBuffer mSaveName = new StringBuffer(AppNo)
		.append('_').append(cOutFuncFlag)
		.append("_in.xml");
	    SaveMessage.save(mXmlDoc_bank,mSaveName.toString(), "original");
	    cLogger.info("���汨����ϣ�"+mSaveName);
	    
	    Element mAbcRootEle = mXmlDoc_bank.getRootElement();
	    Element mBaseInfo = new Element("BaseInfo");
	    
	    Element mBankCode = new Element("BankCode");
		mBankCode.setText(mAbcEle.getChildText("bank"));
	    
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(AppNo);
		
		Element mFuncFlag = new Element(FuncFlag);
		XPath mXPath = XPath.newInstance("business/funcFlag[@outcode='"
				+ cOutFuncFlag + "']");
		String mTempStr = mXPath.valueOf(mAbcEle);
		
		if (null == mTempStr || "".equals(mTempStr)) { // δ�����ⲿ�������ڲ�����ӳ�䣬ֱ��ȡ�ⲿ����
			mFuncFlag.setText(cOutFuncFlag);
		} else { // �ж����ⲿ����ӳ�䣬ȡ���Ӧ�ڲ�����
			mFuncFlag.setText(mTempStr);
		}
		
	    mBaseInfo.addContent(mBankCode);
		mBaseInfo.addContent(mTranNo);
		mBaseInfo.addContent(mFuncFlag);
		mAbcRootEle.addContent(mBaseInfo);
	    
		
		cLogger.info("Out AbcNetImp.receive()!");
		return mXmlDoc_bank;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into AbcNetImp.send()...");
		JdomUtil.print(pOutNoStd);
		//���ظ�ũ�е���ʾ��Ϣ������50���ֽڣ�25�����ӣ��������ȡ��һ��(ǰ�������   ����Ҫ�󣬿ͻ��ͺ��Ĺ�ͨȷ�Ͻ��
		Element mesgEle = pOutNoStd.getRootElement().getChild("Header").getChild("RetMsg");
		String mesg = mesgEle.getText().trim();
		if(mesg.length()>25 && mesg.indexOf("(")!=-1){
			mesg = mesg.substring(0, mesg.indexOf("("));
			mesgEle .setText(mesg);
		}
		Element mAbcEle = NewAbcConf.newInstance().getConf().getRootElement();
		String mTranCom = mAbcEle.getChildText("TranCom");
		
		String xmlStr = JdomUtil.toString(pOutNoStd); 
		//��������Լ�� һ��$SPACE$ ����5��ȫ�ǿո�
		//$Space$ ����2��ȫ�ǿո�
		//$space$ ����1��ȫ�ǿո�
//		xmlStr = StringUtility.replaceAll(xmlStr,"$SPACE$","����������");
//		xmlStr = StringUtility.replaceAll(xmlStr,"$Space$","����");
//		xmlStr = StringUtility.replaceAll(xmlStr,"$space$","��");
		
		xmlStr=xmlStr.replace("<?xml version=\"1.0\"?>","");
		
		cLogger.info("xmlStr ȥ�� xmlͶ���ͻ�ȥ�ı���:"+xmlStr);
		xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		String endxmlStr=AES.Encrypt(xmlStr);
		System.out.println("���ĳ���"+xmlStr);
		
		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml�ַ����ı��ĳ��ȣ�"+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();//new byte[sHeadBytes.getBytes().length];
		
		
		cSocket.getOutputStream().write(array);
		cSocket.getOutputStream().write(endxmlStr.getBytes("UTF-8"));
		cSocket.shutdownOutput();
		
		cLogger.info("Out AbcNetImp.send()!");
	}
	
}