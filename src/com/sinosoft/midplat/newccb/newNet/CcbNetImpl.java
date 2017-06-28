package com.sinosoft.midplat.newccb.newNet;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.format.ErrorOutXsl;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

public class CcbNetImpl extends SocketNetImpl
{
  private String cFuncFlag = null;
  private Document mInNoStd = null;

  private String startTime = null;

  public CcbNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
    super(pSocket, pThisConfRoot);
  }

  public Document receive() throws Exception {
    this.cLogger.info("Into CcbNetImpl.receive()...");
    this.startTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

    byte[] mHeadBytes = new byte[6];
    IOTrans.readFull(mHeadBytes, this.cSocket.getInputStream());
    int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
    this.cLogger.debug("请求报文长度：" + mBodyLength);

    byte[] mBodyBytes = new byte[mBodyLength];
    IOTrans.readFull(mBodyBytes, this.cSocket.getInputStream());
    this.cSocket.shutdownInput();
    String docIn=new String(mBodyBytes,"GBK");//更改编码
    this.mInNoStd = JdomUtil.build(docIn.getBytes());

    Element mRootEle = this.mInNoStd.getRootElement();
    Element cHeader = mRootEle.getChild("TX_HEADER");
    String mSYS_TX_CODE = cHeader.getChildText("SYS_TX_CODE");
    this.cLogger.info("新建行交易码为==" + mSYS_TX_CODE);

    String mTranCom = this.cThisConfRoot.getChildText("TranCom");
    String mAgentCom=this.cThisConfRoot.getChildText("AgentCom");
    this.cLogger.info("mTranCom==============" + mTranCom);
    this.cLogger.info("AgentCom=============="+mAgentCom);
    XPath mXPath2 = XPath.newInstance(
      "business/funcFlag[@outcode='" + mSYS_TX_CODE + "']");

    this.cFuncFlag = mXPath2.valueOf(this.cThisConfRoot);

    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
      .append('_').append(NoFactory.nextAppNo()).append('_').append(this.cFuncFlag).append("_in.xml");
    SaveMessage.save(this.mInNoStd, this.cTranComEle.getText(), mSaveName.toString());
    this.cLogger.info("保存报文完毕！" + mSaveName);
    JdomUtil.print(this.mInNoStd);

	// 生成标准报文头
	Element mTranComEle = new Element(TranCom);
	mTranComEle.setText(mTranCom);
	Element mInNoDoc = new Element("InNoDoc");
	mInNoDoc.setText(mSaveName.toString());
	Element mClientIpEle = new Element(ClientIp);
	mClientIpEle.setText(cClientIp);
	Element mFuncFlagEle = new Element(FuncFlag);
	mFuncFlagEle.setText(cFuncFlag);
	Element mAgentComEle = new Element(AgentCom);
	mAgentComEle.setText(mAgentCom);
	Element mAgentCode = new Element(AgentCode);
	Element mHeadEle = new Element(Head);
	Element mTranNo = new Element(TranNo);
	mTranNo.setText(mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
	mHeadEle.addContent(mClientIpEle);
	mHeadEle.addContent(mTranComEle);
	mHeadEle.addContent(mFuncFlagEle);
	mHeadEle.addContent(mAgentComEle);
	mHeadEle.addContent(mAgentCode);
	mHeadEle.addContent(mTranNo);
	mHeadEle.addContent(mInNoDoc);

	mRootEle.addContent(mHeadEle);
	cLogger.info("增加标准报文头节点后报文：");
	JdomUtil.print(mInNoStd);


    this.cLogger.info("Out CcbNetImpl.receive()!");
    return this.mInNoStd;
  }

  public void send(Document pOutNoStd) throws Exception {
    this.cLogger.info("Into CcbNetImpl.send()...");

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
        mSYS_RESP_DESC_LEN.setText(Integer.toString("不确定".length()));
        mSYS_RESP_DESC.setText("不确定");
      }
      pOutNoStd = mNoStdXml;
    }

    Element mHeadEle = pOutNoStd.getRootElement().getChild("TX_HEADER");
    int mHeadEleLength = JdomUtil.toBytes(mHeadEle, "UTF-8").length;
    mHeadEleLength += String.valueOf(mHeadEleLength).length() - 1;
    mHeadEle.getChild("SYS_HDR_LEN").setText(String.valueOf(mHeadEleLength));

    byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
    int mXmlDocLength = mXmlBytes.length;
    mXmlDocLength += String.valueOf(mXmlDocLength).length() - 1;
    mHeadEle.getChild("SYS_TTL_LEN").setText(String.valueOf(mXmlDocLength));

    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
      .append('_').append(NoFactory.nextAppNo())
      .append('_').append(this.cFuncFlag)
      .append("_out.xml");
    SaveMessage.save(pOutNoStd, this.cTranComEle.getText(), mSaveName.toString());
    this.cLogger.info("保存报文完毕！" + mSaveName);
    this.cOutNoStdDoc = mSaveName.toString();
    byte[] mBodyBytes0 = JdomUtil.toBytes(pOutNoStd,"UTF-8");
    this.cLogger.info("返回给银行的报文：" +JdomUtil.toStringFmt(JdomUtil.build(mBodyBytes0,"UTF-8"),"UTF-8"));

    this.cSocket.getOutputStream().write(mBodyBytes0);
    this.cSocket.shutdownOutput();

    this.cLogger.info("Out CcbNetImpl.send()!");
  }
}