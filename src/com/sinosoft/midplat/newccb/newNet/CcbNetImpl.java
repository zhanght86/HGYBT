package com.sinosoft.midplat.newccb.newNet;

import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.NodeMapSet;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.format.ErrorOutXsl;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;
import org.jdom.xpath.XPath;

public class CcbNetImpl extends SocketNetImpl
{
  private String cFuncFlag = null;
  private Document mInNoStd = null;

  private String startTime = null;

  public CcbNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
    super(pSocket, pThisConfRoot);
  }
  
  /**
   * ����
   */
  public Document receive() throws Exception {
	  //Into CcbNetImpl.receive()...
    this.cLogger.info("Into CcbNetImpl.receive()...");
    //20161213101951123
    this.startTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    //��ʼ������ͷ�ֽ�����
    byte[] mHeadBytes = new byte[6];
    //�������ת�����׽��������ֽ����ж�ȡ���ݣ���������ı���ͷ�ֽ����飬���ر���
    IOTrans.readFull(mHeadBytes, this.cSocket.getInputStream());
    //5877
    int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
    //�����ĳ��ȣ�5877
    this.cLogger.debug("�����ĳ��ȣ�" + mBodyLength);
    //��ʼ�����ĳ��ȸ�Ԫ�صı������ֽ�����
    byte[] mBodyBytes = new byte[mBodyLength];
    //�������ת���������ֽ����ж�ȡ���ݣ�����������ֽ����飬���ر���
    IOTrans.readFull(mBodyBytes, this.cSocket.getInputStream());
    //���׽��ֵ����������ڡ�����ĩβ��
    this.cSocket.shutdownInput();
    //����һ���µ��ַ���(ʹ��UTF-8�ַ������뱨��ͷ�ֽ�����)
    String docIn=new String(mBodyBytes,"UTF-8");
    //-------------����Ǳ�׼�����ַ���
    cLogger.info("-------------"+docIn);
    //Java�ĵ�����ģ�͹��߲���GBK���빹��һ���ĵ����󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
    this.mInNoStd = JdomUtil.build(docIn.getBytes());
    //����Ǳ�׼���ĸ�Ԫ��
    Element mRootEle = this.mInNoStd.getRootElement();
    //����ͷԪ��
    Element cHeader = mRootEle.getChild("TX_HEADER");
    //������[P53819113]
    String mSYS_TX_CODE = cHeader.getChildText("SYS_TX_CODE");
    //�½��н�����Ϊ==P53819113
    this.cLogger.info("�½��н�����Ϊ==" + mSYS_TX_CODE);
    //���׻��������ӽڵ�����[13]
    String mTranCom = this.cThisConfRoot.getChildText("TranCom");
    //mTranCom==============13
    this.cLogger.info("mTranCom==============" + mTranCom);
    //·�����ʽ:business/funcFlag[@outcode='P53819113']
    //child::business/child::funcFlag[(attribute::outcode = "P53819113")]
    XPath mXPath2 = XPath.newInstance(
      "business/funcFlag[@outcode='" + mSYS_TX_CODE + "']");
    //1012
    this.cFuncFlag = mXPath2.valueOf(this.cThisConfRoot);
    //7946_3_1012_in.xml
    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
      .append('_').append(NoFactory.nextAppNo()).append('_').append(this.cFuncFlag).append("_in.xml");
    SaveMessage.save(this.mInNoStd, this.cTranComEle.getText(), mSaveName.toString());
    //���汨����ϣ�7946_3_1012_in.xml
    this.cLogger.info("���汨����ϣ�" + mSaveName);
    //Java�ĵ�����ģ�͹��߽�����Ǳ�׼���Ĵ�ӡ������̨(GBK���룬����3�ո�)
    JdomUtil.print(this.mInNoStd);
    	
	// ���׻�������Ԫ��
	Element mTranComEle = new Element(TranCom);
	//���ý��׻�������
	mTranComEle.setText(mTranCom);
	//���뱨��Ԫ��
	Element mInNoDoc = new Element("InNoDoc");
	//���ý��뱨��
	mInNoDoc.setText(mSaveName.toString());
	//���ж�IPԪ��
	Element mClientIpEle = new Element(ClientIp);
	//�������ж�IP
	mClientIpEle.setText(cClientIp);
	//������Ԫ��
	Element mFuncFlagEle = new Element(FuncFlag);
	//���ý�����
	mFuncFlagEle.setText(cFuncFlag);
	//�����������Ԫ��
	Element mAgentCom = new Element(AgentCom);
	//�����˱���Ԫ��
	Element mAgentCode = new Element(AgentCode);
	//����ͷԪ��
	Element mHeadEle = new Element(Head);
	//����ͷԪ�ؼ������ж�IPԪ��
	mHeadEle.addContent(mClientIpEle);
	//����ͷԪ�ؼ��뽻�׻�������Ԫ��
	mHeadEle.addContent(mTranComEle);
	//����ͷԪ�ؼ��뽻����Ԫ��
	mHeadEle.addContent(mFuncFlagEle);
	//����ͷԪ�ؼ�������������Ԫ��
	mHeadEle.addContent(mAgentCom);
	//����ͷԪ�ؼ�������˱���Ԫ��
	mHeadEle.addContent(mAgentCode);
	//����ͷԪ�ؼ�����뱨��Ԫ��
	mHeadEle.addContent(mInNoDoc);
	
	//��Ԫ�ؼ��뱨��ͷԪ��
	mRootEle.addContent(mHeadEle);
	//���ӱ�׼����ͷ�ڵ���ģ�
	cLogger.info("���ӱ�׼����ͷ�ڵ���ģ�");
	//Java�ĵ�����ģ�͹��߽�����Ǳ�׼���Ĵ�ӡ������̨(GBK���룬����3�ո�)
	JdomUtil.print(mInNoStd);

	//Out CcbNetImpl.receive()!
    this.cLogger.info("Out CcbNetImpl.receive()!");
    //��������Ǳ�׼����
    return this.mInNoStd;
  }

  public void send(Document pOutNoStd) throws Exception {
	//Into CcbNetImpl.send()...
    this.cLogger.info("Into CcbNetImpl.send()...");
    
    //Java�ĵ�����ģ�͹��߽��ĵ�(������Э�鱨��ͷ+������+ͨ����+ʵ����+������+Ӧ����)��ӡ������̨�� GBK���룬����3�ո�
    //������Ǳ�׼���Ĵ�ӡ������̨
    JdomUtil.print(pOutNoStd);
    if (pOutNoStd.getRootElement().getChild("TX_HEADER") == null) {
      System.out.println("==================send====");
      Element tRootEle = this.mInNoStd.getRootElement();

      Element mNodeNo = new Element("NodeNo");
      String tNodeNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCBIns_ID");
      mNodeNo.setText(tNodeNo);

      Element mTranNo = new Element("TranNo");
      String tTranNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
      mTranNo.setText(tTranNo);

      Element mTellerNo = new Element("TellerNo");
      String tTellerNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCB_EmpID");
      mTellerNo.setText(tTellerNo);

      Element mTranDate = new Element("TranDate");
      String tTranDate = tRootEle.getChild("TX_HEADER").getChildText("SYS_REQ_TIME").substring(0, 8);
      mTranDate.setText(tTranDate);

      Element mHeadEle = pOutNoStd.getRootElement().getChild("Head");
      mHeadEle.addContent(mNodeNo);
      mHeadEle.addContent(mTranNo);
      mHeadEle.addContent(mTellerNo);
      mHeadEle.addContent(mTranDate);

      JdomUtil.print(pOutNoStd);
      String endTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
      Document mNoStdXml = 
        ErrorOutXsl.newInstance().getCache().transform(pOutNoStd);
      NewCcbFormatUtil.setDom(this.mInNoStd, mNoStdXml, this.startTime, endTime);
      JdomUtil.print(mNoStdXml);

      Element mSYS_TX_STATUS = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");

      Element mSYS_RESP_CODE = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE");

      Element mSYS_RESP_DESC_LEN = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN");

      Element mSYS_RESP_DESC = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");
      Element mRetData = pOutNoStd.getRootElement().getChild("Head");
      if (mRetData.getChildText("Flag").equals("0")) {
        mSYS_TX_STATUS.setText("00");
        mSYS_RESP_CODE.setText("000000000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText("Desc").length()));
        mSYS_RESP_DESC.setText(mRetData.getChildText("Desc"));
      } else if (mRetData.getChildText("Flag").equals("1")) {
        mSYS_TX_STATUS.setText("01");
        mSYS_RESP_CODE.setText("ZZZ073000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText("Desc").length()));
        mSYS_RESP_DESC.setText(mRetData.getChildText("Desc"));
      } else {
        mSYS_TX_STATUS.setText("02");
        mSYS_RESP_CODE.setText("ZZZ073000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString("��ȷ��".length()));
        mSYS_RESP_DESC.setText("��ȷ��");
      }
      pOutNoStd = mNoStdXml;
    }

    //[Element: <TX_HEADER/>]
    //����Ǳ�׼����ͷԪ��
    Element mHeadEle = pOutNoStd.getRootElement().getChild("TX_HEADER");
    //��ȡJava�ĵ�����ģ�͹��߽�����ͷԪ��ת��ΪUTF-8������ֽ�����(����ԭ��ʽ)��Ԫ�ظ���[800]
    int mHeadEleLength = JdomUtil.toBytes(mHeadEle, "UTF-8").length;//827
    //827+(3-1)=829
    //800+(3-1)=802
    mHeadEleLength += String.valueOf(mHeadEleLength).length() - 1;//829
    //<SYS_HDR_LEN>829</SYS_HDR_LEN>
    //��������Ǳ�׼����ͷ����[802]
    mHeadEle.getChild("SYS_HDR_LEN").setText(String.valueOf(mHeadEleLength));
    // Java�ĵ�����ģ�͹��߽��ĵ�ת��ΪUTF-8������ֽ�����(����ԭ��ʽ)[Element: <TX/>]
    //����Ǳ�׼�����ֽ�����
    byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
    //XML�ĵ��ֽ�����Ԫ�ظ���[1753]
    //����Ǳ�׼�����ֽ����鳤��[1902]
    int mXmlDocLength = mXmlBytes.length;
    //1753+(4-1)=1756
    //1902+(4-1)=1905
    mXmlDocLength += String.valueOf(mXmlDocLength).length() - 1;
    //<SYS_TTL_LEN>1756</SYS_TTL_LEN>
    //��������Ǳ�׼����ͷ�����ܳ���
    mHeadEle.getChild("SYS_TTL_LEN").setText(String.valueOf(mXmlDocLength));
    //
    //1406_6_111_out.xml
    //5357_9_1012_out.xml
    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())//1406[5357]
      .append('_').append(NoFactory.nextAppNo())//_6[_9]
      .append('_').append(this.cFuncFlag)//_111[_1012]
      .append("_out.xml");//_out.xml
    //[Element: <TX/>]��[Element: <TranCom/>]��1406_6_111_out.xml
    SaveMessage.save(pOutNoStd, this.cTranComEle.getText(), mSaveName.toString());
    //���汨����ϣ�1406_6_111_out.xml
    this.cLogger.info("���汨����ϣ�" + mSaveName);
    //1406_6_111_out.xml
    //5357_9_1012_out.xml
    this.cOutNoStdDoc = mSaveName.toString();
    //���ĵ�ת��ΪUTF-8������ֽ����飬����ԭ��ʽ��
    //����Ǳ�׼�����ֽ�����
    byte[] mBodyBytes0 = JdomUtil.toBytes(pOutNoStd,"UTF-8");
    //���ظ����еı��ģ������̵Ʋ�����Ӧ����
    //������Ǳ�׼�����ֽ������ַ�����ӡ������̨
    this.cLogger.info("���ظ����еı��ģ�" + new String(mBodyBytes0));	
    //Socket[addr=/127.0.0.1,port=50412,localport=39871]�õ���������ӱ������ֽ�����д�뱨�����ֽ�����Ԫ�ظ��ֽڵ����ļ��������
    //Socket[addr=/127.0.0.1,port=50200,localport=39871]д������Ǳ�׼�����ֽ����鵽�����
    this.cSocket.getOutputStream().write(mBodyBytes0);
    //Socket[addr=/127.0.0.1,port=50412,localport=39871]�ر����
    //Socket[addr=/127.0.0.1,port=50200,localport=39871]�ر������
    this.cSocket.shutdownOutput();
    //Out CcbNetImpl.send()!
    this.cLogger.info("Out CcbNetImpl.send()!");
  }
}