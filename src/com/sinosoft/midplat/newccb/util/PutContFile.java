package com.sinosoft.midplat.newccb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.format.GetContList2OutXsl;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class PutContFile
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	public PutContFile()
	{

	}

	
	/**
	 * 
	 * isSuccessed 生成保单详情批量包文件
	 *
	 * @param list 保单详情Detial
	 * @param filePath 生成文件路径
	 * @param bagName 批量包名称
	 * @param mNoStdXml 应答Nostd
	 * @param cNoStdXml 请求Nostd
	 * @return
	 * @throws MidplatException
	 * @throws IOException
	 */
	public boolean isSuccessed(List<Element> list, String filePath, String bagName, Document mNoStdXml, Document cNoStdXml) throws MidplatException, IOException
	{
		cLogger.info("进入保单详情批量包生成");
		cLogger.info("请求非标准报文:");
		JdomUtil.print(cNoStdXml);
		Element tInsu_List = new Element("Insu_List");

		for (int i = 0; i < list.size(); i++)
		{

			try
			{
				//批量文件明细节点生成
				Element tInsu_Detail = getInsuDetail(list.get(i));
				tInsu_List.addContent(tInsu_Detail);
			}
			catch(Exception e)
			{
				cLogger.error("生成银行保单详情文件出错:" + e.getMessage());
				throw new MidplatException("生成银行保单详情文件出错！");
			}
		}
		Element tHead = new Element("Head");
		//批量包名称
		Element AgIns_BtchBag_Nm = new Element("AgIns_BtchBag_Nm");
		AgIns_BtchBag_Nm.setText(bagName);
		
		//明细总数
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
			if(!file.delete())
			{
				cLogger.error("删除原始文件失败！");
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
			// 对标准报文加密
			FileUtil fu = new FileUtil(cNoStdXml);
			fu.fileEncrpSecurity();
			cLogger.info("成功对保单详情文件加密.................");
		}
		catch (Exception ex)
		{
			throw new MidplatException("生成银行保单详情文件出错！");
		}

		boolean isTrue = file.exists();
		cLogger.info("完成保单详情批量包生成");
		return isTrue;
	}
	
	/**
	 * 
	 * getInsuDetail 保单详情节点生成
	 *
	 * @param tDetail 保单详情原始节点
	 * @return
	 * @throws SQLException 
	 * @throws MidplatException 
	 * @throws JDOMException 
	 * @throws FileNotFoundException 
	 */
	private Element getInsuDetail(Element tDetail) throws SQLException, MidplatException, JDOMException, FileNotFoundException
	{
		
		//获得非标准报文
		Document noStdDoc = getNoStdDoc(tDetail);
		
		//保单详情节点复制
		Element Insu_Detail = (Element)tDetail.clone();
		
		//投保人节点处理
		dealAppnt(Insu_Detail, noStdDoc);
		
		//被保人节点处理
		dealInsured(Insu_Detail, noStdDoc);
		
		//受益人节点处理
		dealBnf(Insu_Detail, noStdDoc);
		
		//险种节点处理
		dealRisk(Insu_Detail, noStdDoc);
		
		//其他部分处理
		dealOthers(Insu_Detail, noStdDoc);
		
		return Insu_Detail;
	}
	
	/**
	 * 
	 * dealAppnt 投保人节点处理
	 *
	 * @param insuDetail 保单详情
	 * @param noStdDoc 原始请求非标准报文
	 * @throws JDOMException 
	 */
	private void dealAppnt(Element insuDetail, Document noStdDoc) throws JDOMException
	{
		//定义 数字格式
		DecimalFormat df = new DecimalFormat("#######0.00 ");
				
		//投保人证件生效日期
		Element Plchd_Crdt_EfDt = insuDetail.getChild("Plchd_Crdt_EfDt");
		if(StringUtils.isEmpty(Plchd_Crdt_EfDt.getTextTrim()))
		{
			Element tPlchd_Crdt_EfDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Crdt_EfDt");
			if(tPlchd_Crdt_EfDt != null)
			{
				Plchd_Crdt_EfDt.setText(tPlchd_Crdt_EfDt.getTextTrim());
			}
			
		}
		
		//投保人证件失效日期
		Element Plchd_Crdt_ExpDt = insuDetail.getChild("Plchd_Crdt_ExpDt");
		if(StringUtils.isEmpty(Plchd_Crdt_ExpDt.getTextTrim()))
		{
			Element tPlchd_Crdt_ExpDt = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Crdt_ExpDt");
			if(tPlchd_Crdt_ExpDt != null)
			{
				Plchd_Crdt_ExpDt.setText(tPlchd_Crdt_ExpDt.getTextTrim());
			}
			
		}
		
		//投保人职业代码
		Element Plchd_Ocp_Cd = insuDetail.getChild("Plchd_Ocp_Cd");
		if(StringUtils.isEmpty(Plchd_Ocp_Cd.getTextTrim()))
		{
			Element tPlchd_Ocp_Cd = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_Ocp_Cd");
			if(tPlchd_Ocp_Cd != null)
			{
				Plchd_Ocp_Cd.setText(tPlchd_Ocp_Cd.getTextTrim());
			}
			
		}
		
		//投保人年收入
		Element Plchd_Yr_IncmAm = insuDetail.getChild("Plchd_Yr_IncmAm");
		if(StringUtils.isEmpty(Plchd_Yr_IncmAm.getTextTrim()))
		{
			Plchd_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
		}
		else
		{
			Plchd_Yr_IncmAm.setText(df.format(Double.valueOf(Plchd_Yr_IncmAm.getTextTrim())).trim());
		}
		
		//投保人家庭年收入
		Element Fam_Yr_IncmAm = insuDetail.getChild("Fam_Yr_IncmAm");
		if(StringUtils.isEmpty(Fam_Yr_IncmAm.getTextTrim()))
		{
			Fam_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
		}
		else
		{
			Fam_Yr_IncmAm.setText(df.format(Double.valueOf(Fam_Yr_IncmAm.getTextTrim())).trim());
		}
		
		//投保人与被保人关系
		Element Plchd_And_Rcgn_ReTpCd = insuDetail.getChild("Plchd_And_Rcgn_ReTpCd");
		//取原始报文的关系代码返回
		Element tPlchd_And_Rcgn_ReTpCd = (Element)XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Plchd_And_Rcgn_ReTpCd");
		if(tPlchd_And_Rcgn_ReTpCd != null)
		{
			Plchd_And_Rcgn_ReTpCd.setText(tPlchd_And_Rcgn_ReTpCd.getTextTrim());	
		}
		
	}
	
	/**
	 * 
	 * dealInsured 处理被保人关系
	 *
	 * @param insuDetail 保单详情
	 * @param noStdDoc 原始请求非标准报文
	 * @throws JDOMException 
	 */
	private void dealInsured(Element insuDetail, Document noStdDoc) throws JDOMException
	{
		//定义 数字格式
		DecimalFormat df = new DecimalFormat("#######0.00 ");
		
		//得到投保人与被保人关系代码
		String sPlchd_And_Rcgn_ReTpCd = insuDetail.getChildText("Plchd_And_Rcgn_ReTpCd");
		//判断是否为本人
		boolean flag = false;
		if(StringUtils.isNotEmpty(sPlchd_And_Rcgn_ReTpCd) && "0133043".equals(sPlchd_And_Rcgn_ReTpCd))
		{
			flag = true;
		}
		
		// 被保人证件生效日期
		Element Rcgn_Crdt_EfDt = insuDetail.getChild("Rcgn_Crdt_EfDt");
		if (StringUtils.isEmpty(Rcgn_Crdt_EfDt.getTextTrim()))
		{
			Element tRcgn_Crdt_EfDt = (Element) XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Crdt_EfDt");
			if(tRcgn_Crdt_EfDt != null)
			{
				Rcgn_Crdt_EfDt.setText(tRcgn_Crdt_EfDt.getTextTrim());
			}
			
		}

		// 被保人证件失效日期
		Element Rcgn_Crdt_ExpDt = insuDetail.getChild("Rcgn_Crdt_ExpDt");
		if (StringUtils.isEmpty(Rcgn_Crdt_ExpDt.getTextTrim()))
		{
			Element tRcgn_Crdt_ExpDt = (Element) XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Crdt_ExpDt");
			if(tRcgn_Crdt_ExpDt != null)
			{
				Rcgn_Crdt_ExpDt.setText(tRcgn_Crdt_ExpDt.getTextTrim());
			}
			
		}

		// 被保人职业代码
		Element Rcgn_Ocp_Cd = insuDetail.getChild("Rcgn_Ocp_Cd");
		if (StringUtils.isEmpty(Rcgn_Ocp_Cd.getTextTrim()))
		{
			Element tRcgn_Ocp_Cd = (Element) XPath.selectSingleNode(noStdDoc, "//APP_ENTITY/Rcgn_Ocp_Cd");
			if(tRcgn_Ocp_Cd != null)
			{
				Rcgn_Ocp_Cd.setText(tRcgn_Ocp_Cd.getTextTrim());
			}
			
		}

		// 被保人年收入
		Element Rcgn_Yr_IncmAm = insuDetail.getChild("Rcgn_Yr_IncmAm");
		if (StringUtils.isEmpty(Rcgn_Yr_IncmAm.getTextTrim()))
		{
			Rcgn_Yr_IncmAm.setText(df.format(Double.valueOf("0")).trim());
		}
		else
		{
			Rcgn_Yr_IncmAm.setText(df.format(Double.valueOf(Rcgn_Yr_IncmAm.getTextTrim())).trim());
		}
	}
	
	/**
	 * 
	 * dealBnf 受益人处理
	 *
	 * @param insuDetail 保单详情节点
	 * @param noStdDoc 原始非标准报文
	 * @throws JDOMException
	 */
	private void dealBnf(Element insuDetail, Document noStdDoc) throws JDOMException
	{
		//得到收益人节点
		Element Benf_List = insuDetail.getChild("Benf_List");
		List<Element> benf_Detail = Benf_List.getChildren();
		
		//循环处理收益人节点
		for(int i = 0 ; i < benf_Detail.size(); ++ i)
		{
			Element Benf_Detail = benf_Detail.get(i);
			
			//获得受益人姓名
			String sBenf_Nm = Benf_Detail.getChildText("Benf_Nm");
			
			//受益人证件类型
			String sBenf_Crdt_TpCd = Benf_Detail.getChildText("Benf_Crdt_TpCd");
			
			//受益人证件号码
			String sBenf_Crdt_No = Benf_Detail.getChildText("Benf_Crdt_No");
			
			//根据受益人姓名、证件类型、证件号码获取原始报文受益人信息
			String selectXpath = "//APP_ENTITY/Benf_List/Benf_Detail[Benf_Nm='" + sBenf_Nm + "' and Benf_Crdt_TpCd='" + sBenf_Crdt_TpCd + "' and Benf_Crdt_No='" + sBenf_Crdt_No + "']";
			Element o_Benf_Detail = (Element)XPath.selectSingleNode(noStdDoc, selectXpath);
			
			//如果查询到结点，处理相应的值
			if(o_Benf_Detail != null)
			{
				//受益人证件生效日期
				Element Benf_Crdt_EfDt = Benf_Detail.getChild("Benf_Crdt_EfDt");
				if(StringUtils.isEmpty(Benf_Crdt_EfDt.getTextTrim()))
				{
					Benf_Crdt_EfDt.setText(o_Benf_Detail.getChildTextTrim("Benf_Crdt_EfDt"));
				}
				
				//受益人证件失效日期
				Element Benf_Crdt_ExpDt = Benf_Detail.getChild("Benf_Crdt_ExpDt");
				if(StringUtils.isEmpty(Benf_Crdt_ExpDt.getTextTrim()))
				{
					Benf_Crdt_ExpDt.setText(o_Benf_Detail.getChildTextTrim("Benf_Crdt_ExpDt"));
				}
				
				//受益人与被保人关系代码
				Element Benf_And_Rcgn_ReTpCd = Benf_Detail.getChild("Benf_And_Rcgn_ReTpCd");
				Benf_And_Rcgn_ReTpCd.setText(o_Benf_Detail.getChildTextTrim("Benf_And_Rcgn_ReTpCd"));
			}
		}
	}
	
	/**
	 * 
	 * dealRisk 险种处理
	 *
	 * @param insuDetail
	 * @param noStdDoc
	 * @throws JDOMException
	 */
	private void dealRisk(Element insuDetail, Document noStdDoc) throws JDOMException
	{
		//得到险种循环节点
		Element Busi_List = insuDetail.getChild("Busi_List");
		
		//险种明细节点
		List<Element> busi_Detail = Busi_List.getChildren();
		
		//循环处理险种节点
		for(int i = 0; i < busi_Detail.size(); ++ i)
		{
			//险种节点
			Element Busi_Detail = busi_Detail.get(i);
			
			//险种代码
			String riskCode = Busi_Detail.getChildText("Cvr_ID");
			
			//获得原始报文相应的险种节点
			String selectXpath = "//APP_ENTITY/Busi_List/Busi_Detail[Cvr_ID='" + riskCode + "']";
			Element o_Busi_Detail = (Element)XPath.selectSingleNode(noStdDoc, selectXpath);
			//如果结果不为空,进行相应的处理
			if(null != o_Busi_Detail)
			{
				//投资方式代码
				Element Ivs_MtdCd = Busi_Detail.getChild("Ivs_MtdCd");
				if(StringUtils.isEmpty(Ivs_MtdCd.getTextTrim()))
				{
					Ivs_MtdCd.setText(o_Busi_Detail.getChildText("Ivs_MtdCd"));
				}
				
				//保险周期代码
				Element Ins_Cyc_Cd = Busi_Detail.getChild("Ins_Cyc_Cd");
				if(StringUtils.isEmpty(Ins_Cyc_Cd.getTextTrim()))
				{
					Ins_Cyc_Cd.setText(o_Busi_Detail.getChildText("Ins_Cyc_Cd"));
				}
				
				//生存金领取周期代码
				Element SvBnf_Drw_Cyc_Cd = Busi_Detail.getChild("SvBnf_Drw_Cyc_Cd");
				if(StringUtils.isEmpty(SvBnf_Drw_Cyc_Cd.getTextTrim()))
				{
					SvBnf_Drw_Cyc_Cd.setText(o_Busi_Detail.getChildText("SvBnf_Drw_Cyc_Cd"));
				}
				
				//保费缴费周期代码
				Element InsPrem_PyF_Cyc_Cd = Busi_Detail.getChild("InsPrem_PyF_Cyc_Cd");
				if(StringUtils.isEmpty(InsPrem_PyF_Cyc_Cd.getTextTrim()))
				{
					InsPrem_PyF_Cyc_Cd.setText(o_Busi_Detail.getChildText("InsPrem_PyF_Cyc_Cd"));
				}
				
				//自动续保标志
				Element Auto_RnwCv_Ind = Busi_Detail.getChild("Auto_RnwCv_Ind");
				if(StringUtils.isEmpty(Auto_RnwCv_Ind.getTextTrim()))
				{
					Auto_RnwCv_Ind.setText(o_Busi_Detail.getChildText("Auto_RnwCv_Ind"));
				}
				
				//减额缴清标志
				Element RdAmtPyCls_Ind = Busi_Detail.getChild("RdAmtPyCls_Ind");
				if(StringUtils.isEmpty(RdAmtPyCls_Ind.getTextTrim()))
				{
					RdAmtPyCls_Ind.setText(o_Busi_Detail.getChildText("RdAmtPyCls_Ind"));
				}
				
				//保险金额递减方式代码 
				Element Ins_Amt_Dgrs_MtdCd = Busi_Detail.getChild("Ins_Amt_Dgrs_MtdCd");
				if(StringUtils.isEmpty(Ins_Amt_Dgrs_MtdCd.getTextTrim()))
				{
					Ins_Amt_Dgrs_MtdCd.setText(o_Busi_Detail.getChildText("Ins_Amt_Dgrs_MtdCd"));
				}
				
				//紧急联系人
				Element Emgr_CtcPsn = Busi_Detail.getChild("Emgr_CtcPsn");
				if(StringUtils.isEmpty(Emgr_CtcPsn.getTextTrim()))
				{
					Emgr_CtcPsn.setText(o_Busi_Detail.getChildText("Emgr_CtcPsn"));
				}
				
				//紧急联系人与被保人关系类型代码
				Element EmgrCtcPsnAndRcReTpCd = Busi_Detail.getChild("EmgrCtcPsnAndRcReTpCd");
				if(StringUtils.isEmpty(EmgrCtcPsnAndRcReTpCd.getTextTrim()))
				{
					EmgrCtcPsnAndRcReTpCd.setText(o_Busi_Detail.getChildText("EmgrCtcPsnAndRcReTpCd"));
				}
				
				//紧急联系人电话
				Element Emgr_Ctc_Tel = Busi_Detail.getChild("Emgr_Ctc_Tel");
				if(StringUtils.isEmpty(Emgr_Ctc_Tel.getTextTrim()))
				{
					Emgr_Ctc_Tel.setText(o_Busi_Detail.getChildText("Emgr_Ctc_Tel"));
				}
				
				//银行贷款合同编号 
				Element Bnk_Loan_Ctr_ID = Busi_Detail.getChild("Bnk_Loan_Ctr_ID");
				if(StringUtils.isEmpty(Bnk_Loan_Ctr_ID.getTextTrim()))
				{
					Bnk_Loan_Ctr_ID.setText(o_Busi_Detail.getChildText("Bnk_Loan_Ctr_ID"));
				}
				
				//贷款合同编号失效日期
				Element Ln_Ctr_ExpDt = Busi_Detail.getChild("Ln_Ctr_ExpDt");
				if(StringUtils.isEmpty(Ln_Ctr_ExpDt.getTextTrim()))
				{
					Ln_Ctr_ExpDt.setText(o_Busi_Detail.getChildText("Ln_Ctr_ExpDt"));
				}
				
				//主单保单凭证号码
				Element PrimBlInsPolcyVchr_No = Busi_Detail.getChild("PrimBlInsPolcyVchr_No");
				if(StringUtils.isEmpty(PrimBlInsPolcyVchr_No.getTextTrim()))
				{
					PrimBlInsPolcyVchr_No.setText(o_Busi_Detail.getChildText("PrimBlInsPolcyVchr_No"));
				}
				
				//保留字段1
				Element Rsrv_Fld_1 = Busi_Detail.getChild("Rsrv_Fld_1");
				if(StringUtils.isEmpty(Rsrv_Fld_1.getTextTrim()))
				{
					Rsrv_Fld_1.setText(o_Busi_Detail.getChildText("Rsrv_Fld_1"));
				}
				
				//保留字段2
				Element Rsrv_Fld_2 = Busi_Detail.getChild("Rsrv_Fld_2");
				if(StringUtils.isEmpty(Rsrv_Fld_2.getTextTrim()))
				{
					Rsrv_Fld_2.setText(o_Busi_Detail.getChildText("Rsrv_Fld_2"));
				}
				
				//保留字段3
				Element Rsrv_Fld_3 = Busi_Detail.getChild("Rsrv_Fld_3");
				if(StringUtils.isEmpty(Rsrv_Fld_3.getTextTrim()))
				{
					Rsrv_Fld_3.setText(o_Busi_Detail.getChildText("Rsrv_Fld_3"));
				}
				//保留字段4
				Element Rsrv_Fld_4 = Busi_Detail.getChild("Rsrv_Fld_4");
				if(StringUtils.isEmpty(Rsrv_Fld_4.getTextTrim()))
				{
					Rsrv_Fld_4.setText(o_Busi_Detail.getChildText("Rsrv_Fld_4"));
				}
				//保留字段5
				Element Rsrv_Fld_5 = Busi_Detail.getChild("Rsrv_Fld_5");
				if(StringUtils.isEmpty(Rsrv_Fld_5.getTextTrim()))
				{
					Rsrv_Fld_5.setText(o_Busi_Detail.getChildText("Rsrv_Fld_5"));
				}
				//保留字段6
				Element Rsrv_Fld_6 = Busi_Detail.getChild("Rsrv_Fld_6");
				if(StringUtils.isEmpty(Rsrv_Fld_6.getTextTrim()))
				{
					Rsrv_Fld_6.setText(o_Busi_Detail.getChildText("Rsrv_Fld_6"));
				}
				//保留字段7
				Element Rsrv_Fld_7 = Busi_Detail.getChild("Rsrv_Fld_7");
				if(StringUtils.isEmpty(Rsrv_Fld_7.getTextTrim()))
				{
					Rsrv_Fld_7.setText(o_Busi_Detail.getChildText("Rsrv_Fld_7"));
				}
				//保留字段8
				Element Rsrv_Fld_8 = Busi_Detail.getChild("Rsrv_Fld_8");
				if(StringUtils.isEmpty(Rsrv_Fld_8.getTextTrim()))
				{
					Rsrv_Fld_8.setText(o_Busi_Detail.getChildText("Rsrv_Fld_8"));
				}
				//保留字段9
				Element Rsrv_Fld_9 = Busi_Detail.getChild("Rsrv_Fld_9");
				if(StringUtils.isEmpty(Rsrv_Fld_9.getTextTrim()))
				{
					Rsrv_Fld_9.setText(o_Busi_Detail.getChildText("Rsrv_Fld_9"));
				}
				//保留字段10
				Element Rsrv_Fld_10 = Busi_Detail.getChild("Rsrv_Fld_10");
				if(StringUtils.isEmpty(Rsrv_Fld_10.getTextTrim()))
				{
					Rsrv_Fld_10.setText(o_Busi_Detail.getChildText("Rsrv_Fld_10"));
				}
			}
		}
	}
	
	/**
	 * 
	 * dealOthers 处理其它部分节点值
	 *
	 * @param insuDetail 保单节点
	 * @param noStdDoc 原始报文节点
	 * @throws JDOMException
	 */
	private void dealOthers(Element insuDetail, Document noStdDoc) throws JDOMException
	{
		//得到原始报文APP_ENTITY节点
		Element APP_ENTITY = (Element) XPath.selectSingleNode(noStdDoc, "//APP_ENTITY");
		
		//投保人缴费账号 
		Element Plchd_PyF_AccNo = insuDetail.getChild("Plchd_PyF_AccNo");
		if(StringUtils.isEmpty(Plchd_PyF_AccNo.getTextTrim()))
		{
			Element tPlchd_PyF_AccNo = APP_ENTITY.getChild("Plchd_PyF_AccNo");
			if(null != tPlchd_PyF_AccNo)
			{
				Plchd_PyF_AccNo.setText(tPlchd_PyF_AccNo.getTextTrim());
			}
		}
		
		//投保人领取账号
		Element Plchd_Drw_AccNo = insuDetail.getChild("Plchd_Drw_AccNo");
		if(StringUtils.isEmpty(Plchd_Drw_AccNo.getTextTrim()))
		{
			Element tPlchd_Drw_AccNo = APP_ENTITY.getChild("Plchd_Drw_AccNo");
			if(null != tPlchd_Drw_AccNo)
			{
				//如果投保人领取账号为空,则取缴费账号
				if(StringUtils.isEmpty(tPlchd_Drw_AccNo.getTextTrim()))
				{
					Plchd_Drw_AccNo.setText(Plchd_PyF_AccNo.getTextTrim());
				}
				else
				{
					Plchd_Drw_AccNo.setText(tPlchd_Drw_AccNo.getTextTrim());
				}
				
			}
		}
		
		//被保人帐号		
		Element Rcgn_AccNo = insuDetail.getChild("Rcgn_AccNo");
		if(StringUtils.isEmpty(Rcgn_AccNo.getTextTrim()))
		{
			Element tRcgn_AccNo = APP_ENTITY.getChild("Rcgn_AccNo");
			if(null != tRcgn_AccNo)
			{
				//如果被保人帐号为空,则取缴费账号
				if(StringUtils.isEmpty(tRcgn_AccNo.getTextTrim()))
				{
					Rcgn_AccNo.setText(Plchd_PyF_AccNo.getTextTrim());
				}
				else
				{
					Rcgn_AccNo.setText(tRcgn_AccNo.getTextTrim());
				}
				
			}
		}
		//受益人账号
		Element Benf_AccNo = insuDetail.getChild("Benf_AccNo");
		if(StringUtils.isEmpty(Benf_AccNo.getTextTrim()))
		{
			Element tBenf_AccNo = APP_ENTITY.getChild("Benf_AccNo");
			if(null != tBenf_AccNo)
			{
				Benf_AccNo.setText(tBenf_AccNo.getTextTrim());				
			}
		}
		
		//续期缴费支付方式代码
		Element Rnew_PyF_PyMd_Cd = insuDetail.getChild("Rnew_PyF_PyMd_Cd");
		if(StringUtils.isEmpty(Rnew_PyF_PyMd_Cd.getTextTrim()))
		{
			Element tRnew_PyF_PyMd_Cd = APP_ENTITY.getChild("Rnew_PyF_PyMd_Cd");
			if(null != tRnew_PyF_PyMd_Cd)
			{
				Rnew_PyF_PyMd_Cd.setText(tRnew_PyF_PyMd_Cd.getTextTrim());				
			}
		}
		
		//保单介质类型代码
		Element InsPolcy_Medm_TpCd = insuDetail.getChild("InsPolcy_Medm_TpCd");
		if(StringUtils.isEmpty(InsPolcy_Medm_TpCd.getTextTrim()))
		{
			Element tInsPolcy_Medm_TpCd = APP_ENTITY.getChild("InsPolcy_Medm_TpCd");
			if(null != tInsPolcy_Medm_TpCd)
			{
				InsPolcy_Medm_TpCd.setText(tInsPolcy_Medm_TpCd.getTextTrim());				
			}
		}
		
		//建行机构码
		Element CCBIns_ID = insuDetail.getChild("CCBIns_ID");
		if(StringUtils.isEmpty(CCBIns_ID.getTextTrim()))
		{
			Element tCCBIns_ID = APP_ENTITY.getChild("CCBIns_ID");
			if(null != tCCBIns_ID)
			{
				CCBIns_ID.setText(tCCBIns_ID.getTextTrim());				
			}
		}
		
		//一级分行号
		Element Lv1_Br_No = insuDetail.getChild("Lv1_Br_No");
		if(StringUtils.isEmpty(Lv1_Br_No.getTextTrim()))
		{
			Element tLv1_Br_No = APP_ENTITY.getChild("Lv1_Br_No");
			if(null != tLv1_Br_No)
			{
				Lv1_Br_No.setText(tLv1_Br_No.getTextTrim());				
			}
		}
		
		//网点保险兼职代理业务许可证编码
		Element BOInsPrAgnBsnLcns_ECD = insuDetail.getChild("BOInsPrAgnBsnLcns_ECD");
		if(StringUtils.isEmpty(BOInsPrAgnBsnLcns_ECD.getTextTrim()))
		{
			Element tBOInsPrAgnBsnLcns_ECD = APP_ENTITY.getChild("BOInsPrAgnBsnLcns_ECD");
			if(null != tBOInsPrAgnBsnLcns_ECD)
			{
				BOInsPrAgnBsnLcns_ECD.setText(tBOInsPrAgnBsnLcns_ECD.getTextTrim());				
			}
		}
		//网点分管代理保险业务负责人编号
		Element BOIChOfAgInsBsnPnp_ID = insuDetail.getChild("BOIChOfAgInsBsnPnp_ID");
		if(StringUtils.isEmpty(BOIChOfAgInsBsnPnp_ID.getTextTrim()))
		{
			Element tBOIChOfAgInsBsnPnp_ID = APP_ENTITY.getChild("BOIChOfAgInsBsnPnp_ID");
			if(null != tBOIChOfAgInsBsnPnp_ID)
			{
				BOIChOfAgInsBsnPnp_ID.setText(tBOIChOfAgInsBsnPnp_ID.getTextTrim());				
			}
		}
		
		//网点分管代理保险业务负责人姓名
		Element BOIChOfAgInsBsnPnp_Nm = insuDetail.getChild("BOIChOfAgInsBsnPnp_Nm");
		if(StringUtils.isEmpty(BOIChOfAgInsBsnPnp_Nm.getTextTrim()))
		{
			Element tBOIChOfAgInsBsnPnp_Nm = APP_ENTITY.getChild("BOIChOfAgInsBsnPnp_Nm");
			if(null != tBOIChOfAgInsBsnPnp_Nm)
			{
				BOIChOfAgInsBsnPnp_Nm.setText(tBOIChOfAgInsBsnPnp_Nm.getTextTrim());				
			}
		}
		
		//投保人操作编码
		Element Ins_Mnplt_Psn_ID = insuDetail.getChild("Ins_Mnplt_Psn_ID");
		if(StringUtils.isEmpty(Ins_Mnplt_Psn_ID.getTextTrim()))
		{
			Element tIns_Mnplt_Psn_ID = APP_ENTITY.getChild("Ins_Mnplt_Psn_ID");
			if(null != tIns_Mnplt_Psn_ID)
			{
				Ins_Mnplt_Psn_ID.setText(tIns_Mnplt_Psn_ID.getTextTrim());				
			}
		}
		
		//推荐客户经理编号
		Element RcmCst_Mgr_ID = insuDetail.getChild("RcmCst_Mgr_ID");
		if(StringUtils.isEmpty(RcmCst_Mgr_ID.getTextTrim()))
		{
			Element tRcmCst_Mgr_ID = APP_ENTITY.getChild("RcmCst_Mgr_ID");
			if(null != tRcmCst_Mgr_ID)
			{
				RcmCst_Mgr_ID.setText(tRcmCst_Mgr_ID.getTextTrim());				
			}
		}
		
		//推荐客户经理姓名
		Element RcmCst_Mgr_Nm = insuDetail.getChild("RcmCst_Mgr_Nm");
		if(StringUtils.isEmpty(RcmCst_Mgr_Nm.getTextTrim()))
		{
			Element tRcmCst_Mgr_Nm = APP_ENTITY.getChild("RcmCst_Mgr_Nm");
			if(null != tRcmCst_Mgr_Nm)
			{
				RcmCst_Mgr_Nm.setText(tRcmCst_Mgr_Nm.getTextTrim());				
			}
		}
		
		// 保险专案类别代码
		Element Ins_Prj_CgyCd = insuDetail.getChild("Ins_Prj_CgyCd");
		if (StringUtils.isEmpty(Ins_Prj_CgyCd.getTextTrim()))
		{
			Element tIns_Prj_CgyCd = APP_ENTITY.getChild("Ins_Prj_CgyCd");
			if (null != tIns_Prj_CgyCd)
			{
				Ins_Prj_CgyCd.setText(tIns_Prj_CgyCd.getTextTrim());
			}
		}
		
		// 见费出单类型代码 
		Element PydFeeOutBill_CgyCd = insuDetail.getChild("PydFeeOutBill_CgyCd");
		if (StringUtils.isEmpty(PydFeeOutBill_CgyCd.getTextTrim()))
		{
			Element tPydFeeOutBill_CgyCd= APP_ENTITY.getChild("PydFeeOutBill_CgyCd");
			if (null != tPydFeeOutBill_CgyCd)
			{
				PydFeeOutBill_CgyCd.setText(tPydFeeOutBill_CgyCd.getTextTrim());
			}
		}
		
		// 保单实际销售地区编码 
		Element InsPolcyActSaleRgonID = insuDetail.getChild("InsPolcyActSaleRgonID");
		if (StringUtils.isEmpty(InsPolcyActSaleRgonID.getTextTrim()))
		{
			Element tInsPolcyActSaleRgonID = APP_ENTITY.getChild("InsPolcyActSaleRgonID");
			if (null != tInsPolcyActSaleRgonID)
			{
				InsPolcyActSaleRgonID.setText(tInsPolcyActSaleRgonID.getTextTrim());
			}
		}
		
		//保险客户名单提供地区编码 
		Element Ins_CsLs_Prvd_Rgon_ID = insuDetail.getChild("Ins_CsLs_Prvd_Rgon_ID");
		if (StringUtils.isEmpty(Ins_CsLs_Prvd_Rgon_ID.getTextTrim()))
		{
			Element tIns_CsLs_Prvd_Rgon_ID = APP_ENTITY.getChild("Ins_CsLs_Prvd_Rgon_ID");
			if (null != tIns_CsLs_Prvd_Rgon_ID)
			{
				Ins_CsLs_Prvd_Rgon_ID.setText(tIns_CsLs_Prvd_Rgon_ID.getTextTrim());
			}
		}
		//保单领取方式代码 
		Element InsPolcy_Rcv_MtdCd = insuDetail.getChild("InsPolcy_Rcv_MtdCd");
		if (StringUtils.isEmpty(InsPolcy_Rcv_MtdCd.getTextTrim()))
		{
			Element tInsPolcy_Rcv_MtdCd = APP_ENTITY.getChild("InsPolcy_Rcv_MtdCd");
			if (null != tInsPolcy_Rcv_MtdCd)
			{
				InsPolcy_Rcv_MtdCd.setText(tInsPolcy_Rcv_MtdCd.getTextTrim());
			}
		}
		
		//争议处理方式代码
		Element Dspt_Pcsg_MtdCd = insuDetail.getChild("Dspt_Pcsg_MtdCd");
		if (StringUtils.isEmpty(Dspt_Pcsg_MtdCd.getTextTrim()))
		{
			Element tDspt_Pcsg_MtdCd = APP_ENTITY.getChild("Dspt_Pcsg_MtdCd");
			if (null != tDspt_Pcsg_MtdCd)
			{
				Dspt_Pcsg_MtdCd.setText(tDspt_Pcsg_MtdCd.getTextTrim());
			}
		}
		
		//争议仲裁机构名称 
		Element Dspt_Arbtr_Inst_Nm = insuDetail.getChild("Dspt_Arbtr_Inst_Nm");
		if (StringUtils.isEmpty(Dspt_Arbtr_Inst_Nm.getTextTrim()))
		{
			Element tDspt_Arbtr_Inst_Nm = APP_ENTITY.getChild("Dspt_Arbtr_Inst_Nm");
			if (null != tDspt_Arbtr_Inst_Nm)
			{
				Dspt_Arbtr_Inst_Nm.setText(tDspt_Arbtr_Inst_Nm.getTextTrim());
			}
		}
		
		//告知事项标志
		Element Ntf_Itm_Ind = insuDetail.getChild("Ntf_Itm_Ind");
		if (StringUtils.isEmpty(Ntf_Itm_Ind.getTextTrim()))
		{
			Element tNtf_Itm_Ind = APP_ENTITY.getChild("Ntf_Itm_Ind");
			if (null != tNtf_Itm_Ind)
			{
				Ntf_Itm_Ind.setText(tNtf_Itm_Ind.getTextTrim());
			}
		}
		
		//保留字段11
		Element Rsrv_Fld_11 = insuDetail.getChild("Rsrv_Fld_11");
		if(StringUtils.isEmpty(Rsrv_Fld_11.getTextTrim()))
		{
			Rsrv_Fld_11.setText(APP_ENTITY.getChildText("Rsrv_Fld_11"));
		}
		
		//保留字段12
		Element Rsrv_Fld_12 = insuDetail.getChild("Rsrv_Fld_12");
		if(StringUtils.isEmpty(Rsrv_Fld_12.getTextTrim()))
		{
			Rsrv_Fld_12.setText(APP_ENTITY.getChildText("Rsrv_Fld_12"));
		}
		
		//保留字段13
		Element Rsrv_Fld_13 = insuDetail.getChild("Rsrv_Fld_13");
		if(StringUtils.isEmpty(Rsrv_Fld_13.getTextTrim()))
		{
			Rsrv_Fld_13.setText(APP_ENTITY.getChildText("Rsrv_Fld_13"));
		}
		
		//保留字段14
		Element Rsrv_Fld_14 = insuDetail.getChild("Rsrv_Fld_14");
		if(StringUtils.isEmpty(Rsrv_Fld_14.getTextTrim()))
		{
			Rsrv_Fld_14.setText(APP_ENTITY.getChildText("Rsrv_Fld_14"));
		}
		
		//保留字段15
		Element Rsrv_Fld_15 = insuDetail.getChild("Rsrv_Fld_15");
		if(StringUtils.isEmpty(Rsrv_Fld_15.getTextTrim()))
		{
			Rsrv_Fld_15.setText(APP_ENTITY.getChildText("Rsrv_Fld_15"));
		}
		
		//保留字段16
		Element Rsrv_Fld_16 = insuDetail.getChild("Rsrv_Fld_16");
		if(StringUtils.isEmpty(Rsrv_Fld_16.getTextTrim()))
		{
			Rsrv_Fld_16.setText(APP_ENTITY.getChildText("Rsrv_Fld_16"));
		}
		
		//保留字段17
		Element Rsrv_Fld_17 = insuDetail.getChild("Rsrv_Fld_17");
		if(StringUtils.isEmpty(Rsrv_Fld_17.getTextTrim()))
		{
			Rsrv_Fld_17.setText(APP_ENTITY.getChildText("Rsrv_Fld_17"));
		}
		
		//保留字段18
		Element Rsrv_Fld_18 = insuDetail.getChild("Rsrv_Fld_18");
		if(StringUtils.isEmpty(Rsrv_Fld_18.getTextTrim()))
		{
			Rsrv_Fld_18.setText(APP_ENTITY.getChildText("Rsrv_Fld_18"));
		}
		
		//保留字段19
		Element Rsrv_Fld_19 = insuDetail.getChild("Rsrv_Fld_19");
		if(StringUtils.isEmpty(Rsrv_Fld_19.getTextTrim()))
		{
			Rsrv_Fld_19.setText(APP_ENTITY.getChildText("Rsrv_Fld_19"));
		}
		
		//保留字段20
		Element Rsrv_Fld_20 = insuDetail.getChild("Rsrv_Fld_20");
		if(StringUtils.isEmpty(Rsrv_Fld_20.getTextTrim()))
		{
			Rsrv_Fld_20.setText(APP_ENTITY.getChildText("Rsrv_Fld_20"));
		}
	}
	
	/**
	 * 
	 * getNoStdDoc 获得非标准报文
	 *
	 * @param detail
	 * @return
	 * @throws SQLException
	 * @throws MidplatException
	 * @throws FileNotFoundException
	 */
	private Document getNoStdDoc(Element detail) throws SQLException, MidplatException, FileNotFoundException
	{
		// 获得保单号
		String contNo = detail.getChildText("InsPolcy_No");
		
		// 获得投保单号
		String proposalPrtNo =NumberUtil.no13To15(detail.getChildText("Ins_BillNo"));
        cLogger.info("投保单号由13位转为15为:"+proposalPrtNo);
		// 非标准报文
		Document noStdDoc = null;

		// 查询原始请求报文
		LKPolicyXMLDao dao = new LKPolicyXMLDao();
		//byte[] content = dao.queryXmlContent(contNo, "03");
		byte[] content = dao.queryXmlContent(proposalPrtNo, "03");
		// 如果原始请求报文为空，抛出异常信息
		if (content == null)
		{
			// 查询本地文件名
			String querySql = "select innodoc from tranlog where funcflag='1012' and trancom='03' and proposalPrtNo='" + proposalPrtNo + "' and rcode='0'";
			cLogger.info("查询非标准报文文件名:" + querySql);
			String inNoDocName = new ExeSQL().getOneValue(querySql);
			if (StringUtils.isEmpty(inNoDocName))
			{
				throw new MidplatException("原始报文查询失败");
			}

			// 查询交易日期
			querySql = "select trandate from tranlog where funcflag='1012' and trancom='03' and proposalPrtNo='" + proposalPrtNo + "' and rcode='0'";
			cLogger.info("查询交易日期:" + querySql);
			String tranDate = new ExeSQL().getOneValue(querySql);
			if (StringUtils.isEmpty(tranDate))
			{
				throw new MidplatException("原始报文查询失败");
			}

			// 拼接原始报文路径
			String tSavePath = MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
			tSavePath += "msg/03";
			tSavePath += "/" + String.valueOf(tranDate).substring(0, 4);
			tSavePath += "/" + String.valueOf(tranDate).substring(0, 6);
			tSavePath += "/" + String.valueOf(tranDate).substring(0, 8);
			tSavePath += "/" + inNoDocName;
			cLogger.info("非标准报文路径:" + tSavePath);

			File fileIn = new File(tSavePath);
			if (!fileIn.exists())
			{
				throw new MidplatException("原始报文查询失败");
			}

			FileInputStream fileIS = new FileInputStream(fileIn);

			// 构建非标准报文
			noStdDoc = JdomUtil.build(fileIS);
		}
		else
		{
			// 构建非标准报文
			noStdDoc = JdomUtil.build(content);
		}

		return noStdDoc;
	}
	
	public static void main(String args[]) throws IOException, JDOMException, MidplatException
	{
		
		
		File file=new File("c://users/anico/desktop/mmmmm.xml");
		System.out.println("生成文件："+file.createNewFile());
//		String mInFilePath = "D:\\work\\中韩\\报文\\1079763_17_38_outSvc.xml";
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
//		//非标准报文
//		Document mNoStdXml = GetContList2OutXsl.newInstance().getCache().transform(mInXmlDoc);
//		
//		System.out.println("--------------------");
//		JdomUtil.print(mNoStdXml);
//		
//		List<Element> tDetail = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Insu_List").getChildren("Insu_Detail");
//		
//		new PutContFile().isSuccessed(tDetail, "D:\\work\\中韩\\报文\\t.xml", "t.xml", mNoStdXml, null);
		
	}
}
