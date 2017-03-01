package com.sinosoft.midplat.newccb.format;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;

public class DelayedNewCont extends XmlSimpFormat
{
  private Document pNoStdXmlCopy = null;
  private String receiveTime = null;

  public DelayedNewCont(Element pThisConf) { super(pThisConf);
  }

  public Document noStd2Std(Document pNoStdXml) throws Exception {
    this.cLogger.info("Into DelayedNewCont.noStd2Std()...");
    this.pNoStdXmlCopy = pNoStdXml;

    this.receiveTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    String insuID = this.cThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu");
    this.cLogger.info("ÒøÐÐ±àÂë:" + insuID);
    pNoStdXml.getRootElement().getChild("Head").addContent(new Element("InsuID").setText(insuID));
    Document mStdXml = DelayedNewContInXsl.newInstance().getCache().transform(pNoStdXml);
    this.cLogger.info("Out DelayedNewCont.noStd2Std()!");
    return mStdXml;
  }

  public Document std2NoStd(Document pStdXml) throws Exception {
    this.cLogger.info("Into DelayedNewCont.std2NoStd()...");
    Document mNoStdXml = DelayedNewContOutXsl.newInstance().getCache().transform(pStdXml);

    String responseTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

    stdXmlFill(mNoStdXml, this.pNoStdXmlCopy, this.receiveTime, responseTime);
    this.cLogger.info("Out DelayedNewCont.std2NoStd()!");
    return mNoStdXml;
  }

  public void stdXmlFill(Document stdXml, Document noStdXml, String receiveTime, String responseTime)
    throws Exception
  {
    Element newTX_HEADER = stdXml.getRootElement().getChild("TX_HEADER");
    Element oldTX_HEADER = noStdXml.getRootElement().getChild("TX_HEADER");

    String mSYS_EVT_TRACE_ID = oldTX_HEADER.getChildText("SYS_EVT_TRACE_ID");
    newTX_HEADER.getChild("SYS_EVT_TRACE_ID").setText(mSYS_EVT_TRACE_ID);

    String mSYS_REQ_SEC_ID = oldTX_HEADER.getChildText("SYS_REQ_SEC_ID");
    newTX_HEADER.getChild("SYS_REQ_SEC_ID").setText(mSYS_REQ_SEC_ID);

    String mSYS_SND_SERIAL_NO = oldTX_HEADER.getChildText("SYS_SND_SERIAL_NO");
    newTX_HEADER.getChild("SYS_SND_SERIAL_NO").setText(mSYS_SND_SERIAL_NO);

    newTX_HEADER.getChild("SYS_RECV_TIME").setText(receiveTime);

    newTX_HEADER.getChild("SYS_RESP_TIME").setText(responseTime);

    int SYS_RESP_DESC_LENLength = newTX_HEADER.getChildText("SYS_RESP_DESC").getBytes("UTF-8").length;
    newTX_HEADER.getChild("SYS_RESP_DESC_LEN").setText(String.valueOf(SYS_RESP_DESC_LENLength));

    Element newENTITY = stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
    Element oldCOM_ENTITY = noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");

    Element mSYS_TX_CODE = new Element("SYS_TX_CODE");
    mSYS_TX_CODE.setText(oldTX_HEADER.getChildText("SYS_TX_CODE"));
    oldCOM_ENTITY.addContent(mSYS_TX_CODE);

    Element mIns_Co_Acg_DtEle = new Element("Ins_Co_Acg_Dt");
    mIns_Co_Acg_DtEle.setText(oldTX_HEADER.getChildText("SYS_REQ_TIME").substring(0, 8));
    oldCOM_ENTITY.addContent(mIns_Co_Acg_DtEle);

    Element mIns_Co_Jrnl_No = new Element("Ins_Co_Jrnl_No");
    mIns_Co_Jrnl_No.setText(oldCOM_ENTITY.getChildText("SvPt_Jrnl_No"));
    oldCOM_ENTITY.addContent(mIns_Co_Jrnl_No);

    Element mTelPhoneNo = new Element("Ins_Co_Cst_Svc_Tel");
    mTelPhoneNo.setText("4009-800-800");
    oldCOM_ENTITY.addContent(mTelPhoneNo);

    newENTITY.removeChild("COM_ENTITY");
    newENTITY.addContent((Element)oldCOM_ENTITY.clone());

    Element newAPP_ENTITY = stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
    Element oldAPP_ENTITY = noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");

    if ("00".equals(newTX_HEADER.getChildText("SYS_TX_STATUS"))) {
      Element APP_ENTITYEle = new Element("APP_ENTITY");
      Element AgIns_Pkg_IDEle = new Element("AgIns_Pkg_ID");

      Element Cvr_NumEle = new Element("Cvr_Num");
      Cvr_NumEle.setText(oldAPP_ENTITY.getChildText("Cvr_Num"));

      Element Bu_ListEle = new Element("Bu_List");
      APP_ENTITYEle.addContent(AgIns_Pkg_IDEle);
      APP_ENTITYEle.addContent(Cvr_NumEle);
      APP_ENTITYEle.addContent(Bu_ListEle);

      @SuppressWarnings("unchecked")
	List<Element> insuranceCodeList = oldAPP_ENTITY.getChild("Bu_List").getChildren("Bu_Detail");
      for (Element element : insuranceCodeList) {
        Element Bu_DetailEle = new Element("Bu_Detail");
        Element Cvr_IDEle = new Element("Cvr_ID");
        Cvr_IDEle.setText(element.getChildText("Cvr_ID"));
        Bu_DetailEle.addContent(Cvr_IDEle);
        Bu_ListEle.addContent(Bu_DetailEle);
      }

      newAPP_ENTITY.addContent(APP_ENTITYEle);

      Element Ins_BillNoEle = new Element("Ins_BillNo");
      Ins_BillNoEle.setText(oldAPP_ENTITY.getChildText("Ins_Bl_Prt_No"));
      Element Ins_Co_Acrdt_Stff_NmEle = new Element("Ins_Co_Acrdt_Stff_Nm");
      Element InsCoAcrStCrQuaCtf_IDEle = new Element("InsCoAcrStCrQuaCtf_ID");
      APP_ENTITYEle.addContent(Ins_BillNoEle);
      APP_ENTITYEle.addContent(Ins_Co_Acrdt_Stff_NmEle);
      APP_ENTITYEle.addContent(InsCoAcrStCrQuaCtf_IDEle);
    }

    int TX_BODYLength = JdomUtil.toBytes(stdXml.getRootElement().getChild("TX_BODY")).length;
    newTX_HEADER.getChild("SYS_MSG_LEN").setText(String.valueOf(TX_BODYLength));
  }
}




