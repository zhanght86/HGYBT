package com.sinosoft.midplat.newccb.service;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

public class DelayedNewContInput extends ServiceImpl
{
  public DelayedNewContInput(Element pThisBusiConf)
  {
    super(pThisBusiConf);
  }

  public Document service(Document pInXmlDoc)
  {
    this.cLogger.info("Into DelayedNewContInput.service()!");
    this.cLogger.info("转换的标准报文:" + JdomUtil.toStringFmt(pInXmlDoc));

    String mProposalContNo = pInXmlDoc.getRootElement().getChild("LCCont").getChildText("ProposalContNo");
    long mStartMillis = System.currentTimeMillis();
    try {
      this.cTranLogDB = insertTranLog(pInXmlDoc);

      int tLockTime = 300;
      try
      {
        tLockTime = Integer.parseInt(this.cThisBusiConf.getChildText("locktime"));
      }
      catch (Exception ex)
      {
        this.cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)：" + tLockTime, ex);
      }
      Calendar tCurCalendar = Calendar.getInstance();
      tCurCalendar.add(13, -tLockTime);
      String tSqlStr = "select count(1) from TranLog where RCode=" + 
        -1 + " and ProposalPrtNo='" + 
        mProposalContNo + '\'' + " and MakeDate>=" + 
        DateUtil.get8Date(tCurCalendar) + " and MakeTime>=" + 
        DateUtil.get6Time(tCurCalendar);
      this.cLogger.info(tSqlStr);
      if (!("1".equals(new ExeSQL().getOneValue(tSqlStr))))
      {
        throw new MidplatException("此保单数据正在处理中，请稍候！");
      }

      if ("99".equals(pInXmlDoc.getRootElement().getChild("LCCont").getChild("LCAppnt").getChildText("AppntIDType"))) {
        throw new MidplatException("被保人证件类型不符合保险公司规定");
      }

      this.cOutXmlDoc = CallWebsvcAtomSvc(pInXmlDoc);
    } catch (Exception e) {
      this.cLogger.info(this.cThisBusiConf.getChildText("name") + "交易失败！" + e);
      this.cOutXmlDoc = getSimpleErrorXml("0", e.getMessage());
    }
    if (this.cTranLogDB != null)
    {
      Element RetDataEle = this.cOutXmlDoc.getRootElement().getChild("RetData");
      this.cTranLogDB.setRCode((RetDataEle.getChildText("Flag").equals("1")) ? "0" : "1");
      this.cTranLogDB.setRText(RetDataEle.getChildText("Desc"));
      long tCurMillis = System.currentTimeMillis();
      this.cTranLogDB.setUsedTime((int)(tCurMillis - mStartMillis) / 1000);
      this.cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
      this.cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
      if (!(this.cTranLogDB.update()))
      {
        this.cLogger.error("更新日志信息失败！" + this.cTranLogDB.mErrors.getFirstError());
      }
    }
    this.cLogger.info("核心返回报文:" + JdomUtil.toStringFmt(this.cOutXmlDoc));
    this.cLogger.info("Out DelayedNewContInput.service()!");
    return this.cOutXmlDoc; }

  public Document getSimpleErrorXml(String mFlag, String mDesc) {
    Element TranDataEle = new Element("TranData");
    Element RetDataEle = new Element("RetData");
    Element FlagEle = new Element("Flag");
    FlagEle.setText(mFlag);
    Element DescEle = new Element("Desc");
    DescEle.setText(mDesc);
    RetDataEle.addContent(FlagEle);
    RetDataEle.addContent(DescEle);
    TranDataEle.addContent(RetDataEle);
    return new Document(TranDataEle); }

  public Document CallWebsvcAtomSvc(Document inSvcDocXml) throws Exception {
    this.cLogger.info("Into CallWebsvcAtomSvc.service()...");
    String cServiceId = inSvcDocXml.getRootElement().getChild("Head").getChildText("ServiceId");
    String mTranCom = inSvcDocXml.getRootElement().getChild("Head").getChildText("TranCom");

    String mProposalContNo = inSvcDocXml.getRootElement().getChild("LCCont").getChildText("ProposalContNo");

    XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
    Element serviceEle = (Element)mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
    String mServAddress = serviceEle.getAttributeValue("address");
    String mServMethod = serviceEle.getAttributeValue("method");

    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_inSvc.xml");
    SaveMessage.save(inSvcDocXml, mTranCom, mSaveName.toString());
    this.cLogger.info("保存核心报文完毕！" + mSaveName);
    System.out.println("start call " + serviceEle.getAttributeValue("name") + "(" + mServAddress + "." + mServMethod + ")...");
    this.cLogger.info("start call " + serviceEle.getAttributeValue("name") + "(" + mServAddress + "." + mServMethod + ")...");
    byte[] mInXmlByte = JdomUtil.toBytes(inSvcDocXml, "GBK");
    long mStartMillis = System.currentTimeMillis();

    RPCServiceClient serviceClient = new RPCServiceClient();
    Options options = serviceClient.getOptions();

    options.setTimeOutInMilliSeconds(60000L);

    String servicePath = mServAddress + "?wsdl";
    EndpointReference targetEPR = new EndpointReference(servicePath);
    options.setTo(targetEPR);

    Object[] opAddEntryArgs = { mInXmlByte };

    Class[] classes = new Class[] { byte[].class };

    QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com", mServMethod);

    byte[] mOutStr = (byte[])serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
    this.cLogger.info("投保单号" + mProposalContNo + serviceEle.getAttributeValue("name") + "(" + mServMethod + ")耗时：" + ((System.currentTimeMillis() - mStartMillis) / 1000.0D) + "s");
    Document mOutXmlDoc = JdomUtil.build(mOutStr);
    if (mOutXmlDoc == null)
    {
      throw new MidplatException(serviceEle.getAttributeValue("name") + "服务返回结果异常！");
    }

    mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
    SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
    this.cLogger.info("保存核心返回报文完毕！" + mSaveName);

    this.cLogger.info("Out CallWebsvcAtomSvc.service()!");
    return mOutXmlDoc;
  }

  protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
  {
    this.cLogger.debug("Into DelayedNewContInput.insertTranLog()...");

    Element mTranDataEle = pXmlDoc.getRootElement();
    Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
    Element mLCContEle = mTranDataEle.getChild("LCCont");
    Element mHeadEle = mTranDataEle.getChild("Head");

    TranLogDB mTranLogDB = new TranLogDB();
    mTranLogDB.setLogNo(Thread.currentThread().getName());
    System.out.println("进程名：" + Thread.currentThread().getName());
    mTranLogDB.setTranCom(mHeadEle.getChildText("TranCom"));
    mTranLogDB.setZoneNo(mBaseInfoEle.getChildText("ZoneNo"));
    mTranLogDB.setNodeNo(mBaseInfoEle.getChildText("BankCode"));
    mTranLogDB.setTranNo(mBaseInfoEle.getChildText("TransrNo"));
    mTranLogDB.setOperator(mBaseInfoEle.getChildText("TellerNo"));
    mTranLogDB.setFuncFlag(mBaseInfoEle.getChildText("FunctionFlag"));
    mTranLogDB.setTranDate(mBaseInfoEle.getChildText("BankDate"));
    mTranLogDB.setTranTime(mBaseInfoEle.getChildText("BankTime"));
    mTranLogDB.setInNoDoc(mBaseInfoEle.getChildText("InNoDoc"));
    System.out.println("trancom:" + mTranLogDB.getTranCom());
    System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
    System.out.println("mHeadEle.getChildText" + mBaseInfoEle.getChildText("InNoDoc"));
    mTranLogDB.setProposalPrtNo(mLCContEle.getChildText("ProposalContNo"));
    mTranLogDB.setContNo("");
    mTranLogDB.setOtherNo("");
    mTranLogDB.setBak2("");
    mTranLogDB.setAppntName(mLCContEle.getChild("LCAppnt").getChildText("AppntName"));
    mTranLogDB.setAppntIDNo(mLCContEle.getChild("LCAppnt").getChildText("AppntIDNo"));
    mTranLogDB.setRCode(-1);
    mTranLogDB.setUsedTime(-1);
    mTranLogDB.setRText("处理中");
    mTranLogDB.setBak1("");
    Date mCurDate = new Date();
    mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
    mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
    mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
    mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
    if (!(mTranLogDB.insert()))
    {
      this.cLogger.error(mTranLogDB.mErrors.getFirstError());
      throw new MidplatException("插入日志失败！");
    }

    this.cLogger.debug("Out DelayedNewContInput.insertTranLog()!");
    return mTranLogDB;
  }
}




