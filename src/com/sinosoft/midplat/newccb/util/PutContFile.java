package com.sinosoft.midplat.newccb.util;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.utility.ExeSQL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class PutContFile
{
  protected final Logger cLogger = Logger.getLogger(getClass());
  
  public boolean isSuccessed(List<Element> list, String filePath, String bagName, Document mNoStdXml, Document cNoStdXml)
    throws MidplatException, IOException
  {
    this.cLogger.info("进入保单详情批量包生成");
    this.cLogger.info("请求非标准报文:");
    JdomUtil.print(cNoStdXml);
    Element tInsu_List = new Element("Insu_List");
    for (int i = 0; i < list.size(); i++) {
      try
      {
        Element tInsu_Detail = getInsuDetail((Element)list.get(i));
        tInsu_List.addContent(tInsu_Detail);
      }
      catch (Exception e)
      {
        this.cLogger.error("生成银行保单详情文件出错:" + e.getMessage());
        throw new MidplatException("生成银行保单详情文件出错！");
      }
    }
    Element tHead = new Element("Head");
    
    Element AgIns_BtchBag_Nm = new Element("AgIns_BtchBag_Nm");
    AgIns_BtchBag_Nm.setText(bagName);
    
    Element tCur_Btch_Dtl_TDnum = new Element("Cur_Btch_Dtl_TDnum");
    tCur_Btch_Dtl_TDnum.setText(String.valueOf(list.size()));
    
    tHead.addContent(AgIns_BtchBag_Nm);
    tHead.addContent(tCur_Btch_Dtl_TDnum);
    
    Element tRoot = new Element("Root");
    tRoot.addContent(tHead);
    tRoot.addContent(tInsu_List);
    Document pOutNoStd = new Document(tRoot);
    
    JdomUtil.print(pOutNoStd);
    
    byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
    File file = new File(filePath);
    
    System.out.println("这个文件名：" + filePath);
    if (file.exists())
    {
      if (!file.delete()) {
        this.cLogger.error("删除原始文件失败！");
      }
      file.createNewFile();
    }
    else
    {
      file.createNewFile();
    }
    try
    {
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(mBodyBytes);
      fos.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    try
    {
      FileUtil fu = new FileUtil(cNoStdXml);
      fu.fileEncrpSecurity();
      this.cLogger.info("成功对保单详情文件加密.................");
    }
    catch (Exception ex)
    {
      throw new MidplatException("生成银行保单详情文件出错！");
    }
    boolean isTrue = file.exists();
    this.cLogger.info("完成保单详情批量包生成");
    return isTrue;
  }
  
  private Element getInsuDetail(Element tDetail)
    throws SQLException, MidplatException, JDOMException, FileNotFoundException
  {
    Document noStdDoc = getNoStdDoc(tDetail);
    
    Element Insu_Detail = (Element)tDetail.clone();
    
    dealAppnt(Insu_Detail, noStdDoc);
    
    dealInsured(Insu_Detail, noStdDoc);
    
    dealBnf(Insu_Detail, noStdDoc);
    
    dealRisk(Insu_Detail, noStdDoc);
    
    dealOthers(Insu_Detail, noStdDoc);
    
    return Insu_Detail;
  }
  
  private void dealAppnt(Element insuDetail, Document noStdDoc)
    throws JDOMException
  {
    DecimalFormat df = new DecimalFormat("#######0.00 ");
    
    Element Plchd_Crdt_EfDt = insuDetail.getChild("Plchd_Crdt_EfDt");
    if (StringUtils.isEmpty(Plchd_Crdt_EfDt.getTextTrim()))
    {
      Element tPlchd_Crdt_EfDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Crdt_EfDt");
      if (tPlchd_Crdt_EfDt != null) {
        Plchd_Crdt_EfDt.setText(tPlchd_Crdt_EfDt.getTextTrim());
      }
    }
    Element Plchd_Crdt_ExpDt = insuDetail.getChild("Plchd_Crdt_ExpDt");
    if (StringUtils.isEmpty(Plchd_Crdt_ExpDt.getTextTrim()))
    {
      Element tPlchd_Crdt_ExpDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Crdt_ExpDt");
      if (tPlchd_Crdt_ExpDt != null) {
        Plchd_Crdt_ExpDt.setText(tPlchd_Crdt_ExpDt.getTextTrim());
      }
    }
    Element Plchd_Ocp_Cd = insuDetail.getChild("Plchd_Ocp_Cd");
    if (StringUtils.isEmpty(Plchd_Ocp_Cd.getTextTrim()))
    {
      Element tPlchd_Ocp_Cd = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Ocp_Cd");
      if (tPlchd_Ocp_Cd != null) {
        Plchd_Ocp_Cd.setText(tPlchd_Ocp_Cd.getTextTrim());
      }
    }
    Element Plchd_Yr_IncmAm = insuDetail.getChild("Plchd_Yr_IncmAm");
    if (StringUtils.isEmpty(Plchd_Yr_IncmAm.getTextTrim())) {
      Plchd_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
    } else {
      Plchd_Yr_IncmAm.setText(df.format(Double.valueOf(Plchd_Yr_IncmAm.getTextTrim())).trim());
    }
    Element Fam_Yr_IncmAm = insuDetail.getChild("Fam_Yr_IncmAm");
    if (StringUtils.isEmpty(Fam_Yr_IncmAm.getTextTrim())) {
      Fam_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
    } else {
      Fam_Yr_IncmAm.setText(df.format(Double.valueOf(Fam_Yr_IncmAm.getTextTrim())).trim());
    }
    Element Plchd_And_Rcgn_ReTpCd = insuDetail.getChild("Plchd_And_Rcgn_ReTpCd");
    
    Element tPlchd_And_Rcgn_ReTpCd = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_And_Rcgn_ReTpCd");
    if (tPlchd_And_Rcgn_ReTpCd != null) {
      Plchd_And_Rcgn_ReTpCd.setText(tPlchd_And_Rcgn_ReTpCd.getTextTrim());
    }
  }
  
  private void dealInsured(Element insuDetail, Document noStdDoc)
    throws JDOMException
  {
    DecimalFormat df = new DecimalFormat("#######0.00 ");
    
    String sPlchd_And_Rcgn_ReTpCd = insuDetail.getChildText("Plchd_And_Rcgn_ReTpCd");
    
    boolean flag = false;
    if ((StringUtils.isNotEmpty(sPlchd_And_Rcgn_ReTpCd)) && ("0133043".equals(sPlchd_And_Rcgn_ReTpCd))) {
      flag = true;
    }
    Element Rcgn_Crdt_EfDt = insuDetail.getChild("Rcgn_Crdt_EfDt");
    if (StringUtils.isEmpty(Rcgn_Crdt_EfDt.getTextTrim()))
    {
      Element tRcgn_Crdt_EfDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Crdt_EfDt");
      if (tRcgn_Crdt_EfDt != null) {
        Rcgn_Crdt_EfDt.setText(tRcgn_Crdt_EfDt.getTextTrim());
      }
    }
    Element Rcgn_Crdt_ExpDt = insuDetail.getChild("Rcgn_Crdt_ExpDt");
    if (StringUtils.isEmpty(Rcgn_Crdt_ExpDt.getTextTrim()))
    {
      Element tRcgn_Crdt_ExpDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Crdt_ExpDt");
      if (tRcgn_Crdt_ExpDt != null) {
        Rcgn_Crdt_ExpDt.setText(tRcgn_Crdt_ExpDt.getTextTrim());
      }
    }
    Element Rcgn_Ocp_Cd = insuDetail.getChild("Rcgn_Ocp_Cd");
    if (StringUtils.isEmpty(Rcgn_Ocp_Cd.getTextTrim()))
    {
      Element tRcgn_Ocp_Cd = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Ocp_Cd");
      if (tRcgn_Ocp_Cd != null) {
        Rcgn_Ocp_Cd.setText(tRcgn_Ocp_Cd.getTextTrim());
      }
    }
    Element Rcgn_Yr_IncmAm = insuDetail.getChild("Rcgn_Yr_IncmAm");
    if (StringUtils.isEmpty(Rcgn_Yr_IncmAm.getTextTrim())) {
      Rcgn_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
    } else {
      Rcgn_Yr_IncmAm.setText(df.format(Double.valueOf(Rcgn_Yr_IncmAm.getTextTrim())).trim());
    }
  }
  
  private void dealBnf(Element insuDetail, Document noStdDoc)
    throws JDOMException
  {
    Element Benf_List = insuDetail.getChild("Benf_List");
    List<Element> benf_Detail = Benf_List.getChildren();
    for (int i = 0; i < benf_Detail.size(); i++)
    {
      Element Benf_Detail = (Element)benf_Detail.get(i);
      
      String sBenf_Nm = Benf_Detail.getChildText("Benf_Nm");
      
      String sBenf_Crdt_TpCd = Benf_Detail.getChildText("Benf_Crdt_TpCd");
      
      String sBenf_Crdt_No = Benf_Detail.getChildText("Benf_Crdt_No");
      
      String selectXpath = "//APP_ENTITY/Benf_List/Benf_Detail[Benf_Nm='" + sBenf_Nm + "' and Benf_Crdt_TpCd='" + sBenf_Crdt_TpCd + "' and Benf_Crdt_No='" + sBenf_Crdt_No + "']";
      Element o_Benf_Detail = (Element)XPath.selectSingleNode(noStdDoc, selectXpath);
      if (o_Benf_Detail != null)
      {
        Element Benf_Crdt_EfDt = Benf_Detail.getChild("Benf_Crdt_EfDt");
        if (StringUtils.isEmpty(Benf_Crdt_EfDt.getTextTrim())) {
          Benf_Crdt_EfDt.setText(o_Benf_Detail.getChildTextTrim("Benf_Crdt_EfDt"));
        }
        Element Benf_Crdt_ExpDt = Benf_Detail.getChild("Benf_Crdt_ExpDt");
        if (StringUtils.isEmpty(Benf_Crdt_ExpDt.getTextTrim())) {
          Benf_Crdt_ExpDt.setText(o_Benf_Detail.getChildTextTrim("Benf_Crdt_ExpDt"));
        }
        Element Benf_And_Rcgn_ReTpCd = Benf_Detail.getChild("Benf_And_Rcgn_ReTpCd");
        Benf_And_Rcgn_ReTpCd.setText(o_Benf_Detail.getChildTextTrim("Benf_And_Rcgn_ReTpCd"));
      }
    }
  }
  
  private void dealRisk(Element insuDetail, Document noStdDoc)
    throws JDOMException
  {
    Element Busi_List = insuDetail.getChild("Busi_List");
    
    List<Element> busi_Detail = Busi_List.getChildren();
    for (int i = 0; i < busi_Detail.size(); i++)
    {
      Element Busi_Detail = (Element)busi_Detail.get(i);
      
      String riskCode = Busi_Detail.getChildText("Cvr_ID");
      
      String selectXpath = "//APP_ENTITY/Busi_List/Busi_Detail[Cvr_ID='" + riskCode + "']";
      Element o_Busi_Detail = (Element)XPath.selectSingleNode(noStdDoc, selectXpath);
      if (o_Busi_Detail != null)
      {
        Element Ivs_MtdCd = Busi_Detail.getChild("Ivs_MtdCd");
        if (StringUtils.isEmpty(Ivs_MtdCd.getTextTrim())) {
          Ivs_MtdCd.setText(o_Busi_Detail.getChildText("Ivs_MtdCd"));
        }
        Element Ins_Cyc_Cd = Busi_Detail.getChild("Ins_Cyc_Cd");
        if (StringUtils.isEmpty(Ins_Cyc_Cd.getTextTrim())) {
          Ins_Cyc_Cd.setText(o_Busi_Detail.getChildText("Ins_Cyc_Cd"));
        }
        Element SvBnf_Drw_Cyc_Cd = Busi_Detail.getChild("SvBnf_Drw_Cyc_Cd");
        if (StringUtils.isEmpty(SvBnf_Drw_Cyc_Cd.getTextTrim())) {
          SvBnf_Drw_Cyc_Cd.setText(o_Busi_Detail.getChildText("SvBnf_Drw_Cyc_Cd"));
        }
        Element InsPrem_PyF_Cyc_Cd = Busi_Detail.getChild("InsPrem_PyF_Cyc_Cd");
        if (StringUtils.isEmpty(InsPrem_PyF_Cyc_Cd.getTextTrim())) {
          InsPrem_PyF_Cyc_Cd.setText(o_Busi_Detail.getChildText("InsPrem_PyF_Cyc_Cd"));
        }
        Element Auto_RnwCv_Ind = Busi_Detail.getChild("Auto_RnwCv_Ind");
        if (StringUtils.isEmpty(Auto_RnwCv_Ind.getTextTrim())) {
          Auto_RnwCv_Ind.setText(o_Busi_Detail.getChildText("Auto_RnwCv_Ind"));
        }
        Element RdAmtPyCls_Ind = Busi_Detail.getChild("RdAmtPyCls_Ind");
        if (StringUtils.isEmpty(RdAmtPyCls_Ind.getTextTrim())) {
          RdAmtPyCls_Ind.setText(o_Busi_Detail.getChildText("RdAmtPyCls_Ind"));
        }
        Element Ins_Amt_Dgrs_MtdCd = Busi_Detail.getChild("Ins_Amt_Dgrs_MtdCd");
        if (StringUtils.isEmpty(Ins_Amt_Dgrs_MtdCd.getTextTrim())) {
          Ins_Amt_Dgrs_MtdCd.setText(o_Busi_Detail.getChildText("Ins_Amt_Dgrs_MtdCd"));
        }
        Element Emgr_CtcPsn = Busi_Detail.getChild("Emgr_CtcPsn");
        if (StringUtils.isEmpty(Emgr_CtcPsn.getTextTrim())) {
          Emgr_CtcPsn.setText(o_Busi_Detail.getChildText("Emgr_CtcPsn"));
        }
        Element EmgrCtcPsnAndRcReTpCd = Busi_Detail.getChild("EmgrCtcPsnAndRcReTpCd");
        if (StringUtils.isEmpty(EmgrCtcPsnAndRcReTpCd.getTextTrim())) {
          EmgrCtcPsnAndRcReTpCd.setText(o_Busi_Detail.getChildText("EmgrCtcPsnAndRcReTpCd"));
        }
        Element Emgr_Ctc_Tel = Busi_Detail.getChild("Emgr_Ctc_Tel");
        if (StringUtils.isEmpty(Emgr_Ctc_Tel.getTextTrim())) {
          Emgr_Ctc_Tel.setText(o_Busi_Detail.getChildText("Emgr_Ctc_Tel"));
        }
        Element Bnk_Loan_Ctr_ID = Busi_Detail.getChild("Bnk_Loan_Ctr_ID");
        if (StringUtils.isEmpty(Bnk_Loan_Ctr_ID.getTextTrim())) {
          Bnk_Loan_Ctr_ID.setText(o_Busi_Detail.getChildText("Bnk_Loan_Ctr_ID"));
        }
        Element Ln_Ctr_ExpDt = Busi_Detail.getChild("Ln_Ctr_ExpDt");
        if (StringUtils.isEmpty(Ln_Ctr_ExpDt.getTextTrim())) {
          Ln_Ctr_ExpDt.setText(o_Busi_Detail.getChildText("Ln_Ctr_ExpDt"));
        }
        Element PrimBlInsPolcyVchr_No = Busi_Detail.getChild("PrimBlInsPolcyVchr_No");
        if (StringUtils.isEmpty(PrimBlInsPolcyVchr_No.getTextTrim())) {
          PrimBlInsPolcyVchr_No.setText(o_Busi_Detail.getChildText("PrimBlInsPolcyVchr_No"));
        }
        Element Rsrv_Fld_1 = Busi_Detail.getChild("Rsrv_Fld_1");
        if (StringUtils.isEmpty(Rsrv_Fld_1.getTextTrim())) {
          Rsrv_Fld_1.setText(o_Busi_Detail.getChildText("Rsrv_Fld_1"));
        }
        Element Rsrv_Fld_2 = Busi_Detail.getChild("Rsrv_Fld_2");
        if (StringUtils.isEmpty(Rsrv_Fld_2.getTextTrim())) {
          Rsrv_Fld_2.setText(o_Busi_Detail.getChildText("Rsrv_Fld_2"));
        }
        Element Rsrv_Fld_3 = Busi_Detail.getChild("Rsrv_Fld_3");
        if (StringUtils.isEmpty(Rsrv_Fld_3.getTextTrim())) {
          Rsrv_Fld_3.setText(o_Busi_Detail.getChildText("Rsrv_Fld_3"));
        }
        Element Rsrv_Fld_4 = Busi_Detail.getChild("Rsrv_Fld_4");
        if (StringUtils.isEmpty(Rsrv_Fld_4.getTextTrim())) {
          Rsrv_Fld_4.setText(o_Busi_Detail.getChildText("Rsrv_Fld_4"));
        }
        Element Rsrv_Fld_5 = Busi_Detail.getChild("Rsrv_Fld_5");
        if (StringUtils.isEmpty(Rsrv_Fld_5.getTextTrim())) {
          Rsrv_Fld_5.setText(o_Busi_Detail.getChildText("Rsrv_Fld_5"));
        }
        Element Rsrv_Fld_6 = Busi_Detail.getChild("Rsrv_Fld_6");
        if (StringUtils.isEmpty(Rsrv_Fld_6.getTextTrim())) {
          Rsrv_Fld_6.setText(o_Busi_Detail.getChildText("Rsrv_Fld_6"));
        }
        Element Rsrv_Fld_7 = Busi_Detail.getChild("Rsrv_Fld_7");
        if (StringUtils.isEmpty(Rsrv_Fld_7.getTextTrim())) {
          Rsrv_Fld_7.setText(o_Busi_Detail.getChildText("Rsrv_Fld_7"));
        }
        Element Rsrv_Fld_8 = Busi_Detail.getChild("Rsrv_Fld_8");
        if (StringUtils.isEmpty(Rsrv_Fld_8.getTextTrim())) {
          Rsrv_Fld_8.setText(o_Busi_Detail.getChildText("Rsrv_Fld_8"));
        }
        Element Rsrv_Fld_9 = Busi_Detail.getChild("Rsrv_Fld_9");
        if (StringUtils.isEmpty(Rsrv_Fld_9.getTextTrim())) {
          Rsrv_Fld_9.setText(o_Busi_Detail.getChildText("Rsrv_Fld_9"));
        }
        Element Rsrv_Fld_10 = Busi_Detail.getChild("Rsrv_Fld_10");
        if (StringUtils.isEmpty(Rsrv_Fld_10.getTextTrim())) {
          Rsrv_Fld_10.setText(o_Busi_Detail.getChildText("Rsrv_Fld_10"));
        }
      }
    }
  }
  
  private void dealOthers(Element insuDetail, Document noStdDoc)
    throws JDOMException
  {
    Element APP_ENTITY = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY");
    
    Element Plchd_PyF_AccNo = insuDetail.getChild("Plchd_PyF_AccNo");
    if (StringUtils.isEmpty(Plchd_PyF_AccNo.getTextTrim()))
    {
      Element tPlchd_PyF_AccNo = APP_ENTITY.getChild("Plchd_PyF_AccNo");
      if (tPlchd_PyF_AccNo != null) {
        Plchd_PyF_AccNo.setText(tPlchd_PyF_AccNo.getTextTrim());
      }
    }
    Element Plchd_Drw_AccNo = insuDetail.getChild("Plchd_Drw_AccNo");
    if (StringUtils.isEmpty(Plchd_Drw_AccNo.getTextTrim()))
    {
      Element tPlchd_Drw_AccNo = APP_ENTITY.getChild("Plchd_Drw_AccNo");
      if (tPlchd_Drw_AccNo != null) {
        if (StringUtils.isEmpty(tPlchd_Drw_AccNo.getTextTrim())) {
          Plchd_Drw_AccNo.setText(Plchd_PyF_AccNo.getTextTrim());
        } else {
          Plchd_Drw_AccNo.setText(tPlchd_Drw_AccNo.getTextTrim());
        }
      }
    }
    Element Rcgn_AccNo = insuDetail.getChild("Rcgn_AccNo");
    if (StringUtils.isEmpty(Rcgn_AccNo.getTextTrim()))
    {
      Element tRcgn_AccNo = APP_ENTITY.getChild("Rcgn_AccNo");
      if (tRcgn_AccNo != null) {
        if (StringUtils.isEmpty(tRcgn_AccNo.getTextTrim())) {
          Rcgn_AccNo.setText(Plchd_PyF_AccNo.getTextTrim());
        } else {
          Rcgn_AccNo.setText(tRcgn_AccNo.getTextTrim());
        }
      }
    }
    Element Benf_AccNo = insuDetail.getChild("Benf_AccNo");
    if (StringUtils.isEmpty(Benf_AccNo.getTextTrim()))
    {
      Element tBenf_AccNo = APP_ENTITY.getChild("Benf_AccNo");
      if (tBenf_AccNo != null) {
        Benf_AccNo.setText(tBenf_AccNo.getTextTrim());
      }
    }
    Element Rnew_PyF_PyMd_Cd = insuDetail.getChild("Rnew_PyF_PyMd_Cd");
    if (StringUtils.isEmpty(Rnew_PyF_PyMd_Cd.getTextTrim()))
    {
      Element tRnew_PyF_PyMd_Cd = APP_ENTITY.getChild("Rnew_PyF_PyMd_Cd");
      if (tRnew_PyF_PyMd_Cd != null) {
        Rnew_PyF_PyMd_Cd.setText(tRnew_PyF_PyMd_Cd.getTextTrim());
      }
    }
    Element InsPolcy_Medm_TpCd = insuDetail.getChild("InsPolcy_Medm_TpCd");
    if (StringUtils.isEmpty(InsPolcy_Medm_TpCd.getTextTrim()))
    {
      Element tInsPolcy_Medm_TpCd = APP_ENTITY.getChild("InsPolcy_Medm_TpCd");
      if (tInsPolcy_Medm_TpCd != null) {
        InsPolcy_Medm_TpCd.setText(tInsPolcy_Medm_TpCd.getTextTrim());
      }
    }
    Element CCBIns_ID = insuDetail.getChild("CCBIns_ID");
    if (StringUtils.isEmpty(CCBIns_ID.getTextTrim()))
    {
      Element tCCBIns_ID = APP_ENTITY.getChild("CCBIns_ID");
      if (tCCBIns_ID != null) {
        CCBIns_ID.setText(tCCBIns_ID.getTextTrim());
      }
    }
    Element Lv1_Br_No = insuDetail.getChild("Lv1_Br_No");
    if (StringUtils.isEmpty(Lv1_Br_No.getTextTrim()))
    {
      Element tLv1_Br_No = APP_ENTITY.getChild("Lv1_Br_No");
      if (tLv1_Br_No != null) {
        Lv1_Br_No.setText(tLv1_Br_No.getTextTrim());
      }
    }
    Element BOInsPrAgnBsnLcns_ECD = insuDetail.getChild("BOInsPrAgnBsnLcns_ECD");
    if (StringUtils.isEmpty(BOInsPrAgnBsnLcns_ECD.getTextTrim()))
    {
      Element tBOInsPrAgnBsnLcns_ECD = APP_ENTITY.getChild("BOInsPrAgnBsnLcns_ECD");
      if (tBOInsPrAgnBsnLcns_ECD != null) {
        BOInsPrAgnBsnLcns_ECD.setText(tBOInsPrAgnBsnLcns_ECD.getTextTrim());
      }
    }
    Element BOIChOfAgInsBsnPnp_ID = insuDetail.getChild("BOIChOfAgInsBsnPnp_ID");
    if (StringUtils.isEmpty(BOIChOfAgInsBsnPnp_ID.getTextTrim()))
    {
      Element tBOIChOfAgInsBsnPnp_ID = APP_ENTITY.getChild("BOIChOfAgInsBsnPnp_ID");
      if (tBOIChOfAgInsBsnPnp_ID != null) {
        BOIChOfAgInsBsnPnp_ID.setText(tBOIChOfAgInsBsnPnp_ID.getTextTrim());
      }
    }
    Element BOIChOfAgInsBsnPnp_Nm = insuDetail.getChild("BOIChOfAgInsBsnPnp_Nm");
    if (StringUtils.isEmpty(BOIChOfAgInsBsnPnp_Nm.getTextTrim()))
    {
      Element tBOIChOfAgInsBsnPnp_Nm = APP_ENTITY.getChild("BOIChOfAgInsBsnPnp_Nm");
      if (tBOIChOfAgInsBsnPnp_Nm != null) {
        BOIChOfAgInsBsnPnp_Nm.setText(tBOIChOfAgInsBsnPnp_Nm.getTextTrim());
      }
    }
    Element Ins_Mnplt_Psn_ID = insuDetail.getChild("Ins_Mnplt_Psn_ID");
    if (StringUtils.isEmpty(Ins_Mnplt_Psn_ID.getTextTrim()))
    {
      Element tIns_Mnplt_Psn_ID = APP_ENTITY.getChild("Ins_Mnplt_Psn_ID");
      if (tIns_Mnplt_Psn_ID != null) {
        Ins_Mnplt_Psn_ID.setText(tIns_Mnplt_Psn_ID.getTextTrim());
      }
    }
    Element RcmCst_Mgr_ID = insuDetail.getChild("RcmCst_Mgr_ID");
    if (StringUtils.isEmpty(RcmCst_Mgr_ID.getTextTrim()))
    {
      Element tRcmCst_Mgr_ID = APP_ENTITY.getChild("RcmCst_Mgr_ID");
      if (tRcmCst_Mgr_ID != null) {
        RcmCst_Mgr_ID.setText(tRcmCst_Mgr_ID.getTextTrim());
      }
    }
    Element RcmCst_Mgr_Nm = insuDetail.getChild("RcmCst_Mgr_Nm");
    if (StringUtils.isEmpty(RcmCst_Mgr_Nm.getTextTrim()))
    {
      Element tRcmCst_Mgr_Nm = APP_ENTITY.getChild("RcmCst_Mgr_Nm");
      if (tRcmCst_Mgr_Nm != null) {
        RcmCst_Mgr_Nm.setText(tRcmCst_Mgr_Nm.getTextTrim());
      }
    }
    Element Ins_Prj_CgyCd = insuDetail.getChild("Ins_Prj_CgyCd");
    if (StringUtils.isEmpty(Ins_Prj_CgyCd.getTextTrim()))
    {
      Element tIns_Prj_CgyCd = APP_ENTITY.getChild("Ins_Prj_CgyCd");
      if (tIns_Prj_CgyCd != null) {
        Ins_Prj_CgyCd.setText(tIns_Prj_CgyCd.getTextTrim());
      }
    }
    Element PydFeeOutBill_CgyCd = insuDetail.getChild("PydFeeOutBill_CgyCd");
    if (StringUtils.isEmpty(PydFeeOutBill_CgyCd.getTextTrim()))
    {
      Element tPydFeeOutBill_CgyCd = APP_ENTITY.getChild("PydFeeOutBill_CgyCd");
      if (tPydFeeOutBill_CgyCd != null) {
        PydFeeOutBill_CgyCd.setText(tPydFeeOutBill_CgyCd.getTextTrim());
      }
    }
    Element InsPolcyActSaleRgonID = insuDetail.getChild("InsPolcyActSaleRgonID");
    if (StringUtils.isEmpty(InsPolcyActSaleRgonID.getTextTrim()))
    {
      Element tInsPolcyActSaleRgonID = APP_ENTITY.getChild("InsPolcyActSaleRgonID");
      if (tInsPolcyActSaleRgonID != null) {
        InsPolcyActSaleRgonID.setText(tInsPolcyActSaleRgonID.getTextTrim());
      }
    }
    Element Ins_CsLs_Prvd_Rgon_ID = insuDetail.getChild("Ins_CsLs_Prvd_Rgon_ID");
    if (StringUtils.isEmpty(Ins_CsLs_Prvd_Rgon_ID.getTextTrim()))
    {
      Element tIns_CsLs_Prvd_Rgon_ID = APP_ENTITY.getChild("Ins_CsLs_Prvd_Rgon_ID");
      if (tIns_CsLs_Prvd_Rgon_ID != null) {
        Ins_CsLs_Prvd_Rgon_ID.setText(tIns_CsLs_Prvd_Rgon_ID.getTextTrim());
      }
    }
    Element InsPolcy_Rcv_MtdCd = insuDetail.getChild("InsPolcy_Rcv_MtdCd");
    if (StringUtils.isEmpty(InsPolcy_Rcv_MtdCd.getTextTrim()))
    {
      Element tInsPolcy_Rcv_MtdCd = APP_ENTITY.getChild("InsPolcy_Rcv_MtdCd");
      if (tInsPolcy_Rcv_MtdCd != null) {
        InsPolcy_Rcv_MtdCd.setText(tInsPolcy_Rcv_MtdCd.getTextTrim());
      }
    }
    Element Dspt_Pcsg_MtdCd = insuDetail.getChild("Dspt_Pcsg_MtdCd");
    if (StringUtils.isEmpty(Dspt_Pcsg_MtdCd.getTextTrim()))
    {
      Element tDspt_Pcsg_MtdCd = APP_ENTITY.getChild("Dspt_Pcsg_MtdCd");
      if (tDspt_Pcsg_MtdCd != null) {
        Dspt_Pcsg_MtdCd.setText(tDspt_Pcsg_MtdCd.getTextTrim());
      }
    }
    Element Dspt_Arbtr_Inst_Nm = insuDetail.getChild("Dspt_Arbtr_Inst_Nm");
    if (StringUtils.isEmpty(Dspt_Arbtr_Inst_Nm.getTextTrim()))
    {
      Element tDspt_Arbtr_Inst_Nm = APP_ENTITY.getChild("Dspt_Arbtr_Inst_Nm");
      if (tDspt_Arbtr_Inst_Nm != null) {
        Dspt_Arbtr_Inst_Nm.setText(tDspt_Arbtr_Inst_Nm.getTextTrim());
      }
    }
    Element Ntf_Itm_Ind = insuDetail.getChild("Ntf_Itm_Ind");
    if (StringUtils.isEmpty(Ntf_Itm_Ind.getTextTrim()))
    {
      Element tNtf_Itm_Ind = APP_ENTITY.getChild("Ntf_Itm_Ind");
      if (tNtf_Itm_Ind != null) {
        Ntf_Itm_Ind.setText(tNtf_Itm_Ind.getTextTrim());
      }
    }
    Element Rsrv_Fld_11 = insuDetail.getChild("Rsrv_Fld_11");
    if (StringUtils.isEmpty(Rsrv_Fld_11.getTextTrim())) {
      Rsrv_Fld_11.setText(APP_ENTITY.getChildText("Rsrv_Fld_11"));
    }
    Element Rsrv_Fld_12 = insuDetail.getChild("Rsrv_Fld_12");
    if (StringUtils.isEmpty(Rsrv_Fld_12.getTextTrim())) {
      Rsrv_Fld_12.setText(APP_ENTITY.getChildText("Rsrv_Fld_12"));
    }
    Element Rsrv_Fld_13 = insuDetail.getChild("Rsrv_Fld_13");
    if (StringUtils.isEmpty(Rsrv_Fld_13.getTextTrim())) {
      Rsrv_Fld_13.setText(APP_ENTITY.getChildText("Rsrv_Fld_13"));
    }
    Element Rsrv_Fld_14 = insuDetail.getChild("Rsrv_Fld_14");
    if (StringUtils.isEmpty(Rsrv_Fld_14.getTextTrim())) {
      Rsrv_Fld_14.setText(APP_ENTITY.getChildText("Rsrv_Fld_14"));
    }
    Element Rsrv_Fld_15 = insuDetail.getChild("Rsrv_Fld_15");
    if (StringUtils.isEmpty(Rsrv_Fld_15.getTextTrim())) {
      Rsrv_Fld_15.setText(APP_ENTITY.getChildText("Rsrv_Fld_15"));
    }
    Element Rsrv_Fld_16 = insuDetail.getChild("Rsrv_Fld_16");
    if (StringUtils.isEmpty(Rsrv_Fld_16.getTextTrim())) {
      Rsrv_Fld_16.setText(APP_ENTITY.getChildText("Rsrv_Fld_16"));
    }
    Element Rsrv_Fld_17 = insuDetail.getChild("Rsrv_Fld_17");
    if (StringUtils.isEmpty(Rsrv_Fld_17.getTextTrim())) {
      Rsrv_Fld_17.setText(APP_ENTITY.getChildText("Rsrv_Fld_17"));
    }
    Element Rsrv_Fld_18 = insuDetail.getChild("Rsrv_Fld_18");
    if (StringUtils.isEmpty(Rsrv_Fld_18.getTextTrim())) {
      Rsrv_Fld_18.setText(APP_ENTITY.getChildText("Rsrv_Fld_18"));
    }
    Element Rsrv_Fld_19 = insuDetail.getChild("Rsrv_Fld_19");
    if (StringUtils.isEmpty(Rsrv_Fld_19.getTextTrim())) {
      Rsrv_Fld_19.setText(APP_ENTITY.getChildText("Rsrv_Fld_19"));
    }
    Element Rsrv_Fld_20 = insuDetail.getChild("Rsrv_Fld_20");
    if (StringUtils.isEmpty(Rsrv_Fld_20.getTextTrim())) {
      Rsrv_Fld_20.setText(APP_ENTITY.getChildText("Rsrv_Fld_20"));
    }
  }
  
  private Document getNoStdDoc(Element detail)
    throws SQLException, MidplatException, FileNotFoundException
  {
    String contNo = detail.getChildText("InsPolcy_No");
    
    String proposalPrtNo = NumberUtil.no13To15(detail.getChildText("Ins_BillNo"));
    this.cLogger.info("投保单号由13位转为15为:" + proposalPrtNo);
    
    Document noStdDoc = null;
    
    LKPolicyXMLDao dao = new LKPolicyXMLDao();
    
    byte[] content = dao.queryXmlContent(proposalPrtNo, "03");
    if (content == null)
    {
      String querySql = "select innodoc from tranlog where funcflag='1012' and trancom='03' and proposalPrtNo='" + proposalPrtNo + "' and rcode='0'";
      this.cLogger.info("查询非标准报文文件名:" + querySql);
      String inNoDocName = new ExeSQL().getOneValue(querySql);
      if (StringUtils.isEmpty(inNoDocName)) {
        throw new MidplatException("原始报文查询失败");
      }
      querySql = "select trandate from tranlog where funcflag='1012' and trancom='03' and proposalPrtNo='" + proposalPrtNo + "' and rcode='0'";
      this.cLogger.info("查询交易日期:" + querySql);
      String tranDate = new ExeSQL().getOneValue(querySql);
      if (StringUtils.isEmpty(tranDate)) {
        throw new MidplatException("原始报文查询失败");
      }
      String tSavePath = MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
      tSavePath = tSavePath + "msg/03";
      tSavePath = tSavePath + "/" + String.valueOf(tranDate).substring(0, 4);
      tSavePath = tSavePath + "/" + String.valueOf(tranDate).substring(0, 6);
      tSavePath = tSavePath + "/" + String.valueOf(tranDate).substring(0, 8);
      tSavePath = tSavePath + "/" + inNoDocName;
      this.cLogger.info("非标准报文路径:" + tSavePath);
      
      File fileIn = new File(tSavePath);
      if (!fileIn.exists()) {
        throw new MidplatException("原始报文查询失败");
      }
      FileInputStream fileIS = new FileInputStream(fileIn);
      
      noStdDoc = JdomUtil.build(fileIS);
    }
    else
    {
      noStdDoc = JdomUtil.build(content);
    }
    return noStdDoc;
  }
  
  public static void main(String[] args)
    throws IOException, JDOMException, MidplatException
  {
    File file = new File("c://users/anico/desktop/mmmmm.xml");
    System.out.println("生成文件：" + file.createNewFile());
  }
}
