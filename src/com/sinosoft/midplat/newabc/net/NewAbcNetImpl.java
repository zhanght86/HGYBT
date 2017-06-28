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

/**
 * @ClassName: NewAbcNetImpl
 * @Description: ��ũ���׽�������ͨѶʵ����
 * @author sinosoft
 * @date 2017-4-13 ����3:48:41
 */
public class NewAbcNetImpl extends SocketNetImpl {
	//�ⲿ������
	private String cOutFuncFlag = null;
	//���չ�˾����
	private String cInsuID = null;
	//��������
	private String cFuncFlag = null;
	
	/**
	 * <p>Title: NewAbcNetImpl</p>
	 * <p>Description: ��ũ���׽�������ͨѶʵ���๹����</p>
	 * @param pSocket �׽���
	 * @param pThisConfRoot ��ǰ�����ļ����ڵ�
	 * @throws MidplatException
	 */
	public NewAbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		//���ø����׽�������ͨѶʵ���๹����
		super(pSocket, pThisConfRoot);
	}
	
	/**
	 * ����[��ũ�зǱ�׼����]���ģ����ӱ�׼����ͷ
	 */
	public Document receive() throws Exception {
		//���� NewAbcNetImp.receive����...
		cLogger.info("Into NewAbcNetImp.receive()...");

		//��ͷ73�ֽ�
		byte[] mHeadBytes = new byte[73];
		//
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		String package_head = new String(mHeadBytes);
		//4-11 λ�Ǳ����峤��
		cLogger.info("package_head:"+package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //���峤��
		
		//12-20 λ�Ǳ��չ�˾����
		cInsuID=String.valueOf(Integer.parseInt(package_head.substring(12, 20).trim()));
		cLogger.info("cInsuID:"+cInsuID);
		
		cLogger.info("mBodyLen:"+mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; //���е�body�ֽڲ���xml����
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		cLogger.info("����ǰ�ı���:"+new String(mBodyBytes,"UTF-8"));
		/**********��������****************/
		cLogger.info("���ܿ�ʼ");
		String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
//		String axx = new String(mBodyBytes,"UTF-8");
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
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
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
//		String endxmlStr=xmlStr;
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
		
		cLogger.info("Out NewAbcNetImp.send()!");
	}
	
}