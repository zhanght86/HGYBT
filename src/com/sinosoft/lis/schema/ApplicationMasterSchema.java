/*
 * <p>ClassName: ApplicationMasterSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛花旗
 * @CreateDate：2012-03-24
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.ApplicationMasterDB;

public class ApplicationMasterSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 提取日期 */
	private int ExtractedDate;
	/** 保单号 */
	private String PolicyNo;
	/** 保险公司代码 */
	private String SourceCode;
	/** 险种代码 */
	private String BasicPlanCode;
	/** 保单状态 */
	private String PolicyStatus;
	/** 保费累计 */
	private String SumInsured;
	/** 投保日期 */
	private int ApplicationDate;
	/** 签单日期 */
	private int PolicyContractDate;
	/** 支付方式 */
	private String PaymentMethod;
	/** Monthsdebited */
	private String Monthsdebited;
	/** 保单币种 */
	private String PolicyCurrency;
	/** 期缴保费 */
	private double ModalPremium;
	/** 保费总计 */
	private double LumpSumPremium;
	/** 首付总保费 */
	private double InitLumpSumPremium;
	/** 定期额外投资保费 */
	private double RegularTopUpPremium;
	/** 缴费方式 */
	private String PayMode;
	/** 保单状态变更日期 */
	private int StatusChangeDate;
	/** 被保人身份证件号码 */
	private String InsuredIDNo;
	/** 被保人姓名 */
	private String InsuredName;
	/** 被保人生日 */
	private int InsuredBirthday;
	/** 被保人性别 */
	private String InsuredGender;
	/** 投保年龄 */
	private String AgeatIssue;
	/** 代理1 */
	private String ProduceAgent1;
	/** 代理2 */
	private String ProduceAgent2;
	/** Unitcode */
	private String UnitCode;
	/** 代理公司代码 */
	private String AgentSourceCode;
	/** 代理号码 */
	private String AgentNo;
	/** 代理类型 */
	private String AgentType;
	/** 代理人姓名 */
	private String AgentName;
	/** 代理id */
	private String AgentID;
	/** 生成日期 */
	private int MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 更新日期 */
	private int ModifyDate;
	/** 更新时间 */
	private String ModifyTime;
	/** 处理日期 */
	private int dealDate;
	/** 处理时间 */
	private String DealTime;

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ApplicationMasterSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 流水号<P> */
	public String getSerialNo()
	{
		if (SerialNo != null && !SerialNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SerialNo = StrTool.unicodeToGBK(SerialNo);
		}
		return SerialNo;
	}
	/** 流水号 */
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/** 提取日期<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** 提取日期 */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** 提取日期<P> */
	public void setExtractedDate(String aExtractedDate)
	{
		if (aExtractedDate != null && !aExtractedDate.equals(""))
		{
			Integer tInteger = new Integer(aExtractedDate);
			int i = tInteger.intValue();
			ExtractedDate = i;
		}
	}

	/** 保单号<P>产品代码 */
	public String getPolicyNo()
	{
		if (PolicyNo != null && !PolicyNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyNo = StrTool.unicodeToGBK(PolicyNo);
		}
		return PolicyNo;
	}
	/** 保单号 */
	public void setPolicyNo(String aPolicyNo)
	{
		PolicyNo = aPolicyNo;
	}
	/** 保险公司代码<P>被保人年龄 */
	public String getSourceCode()
	{
		if (SourceCode != null && !SourceCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			SourceCode = StrTool.unicodeToGBK(SourceCode);
		}
		return SourceCode;
	}
	/** 保险公司代码 */
	public void setSourceCode(String aSourceCode)
	{
		SourceCode = aSourceCode;
	}
	/** 险种代码<P>M-男 F-女 */
	public String getBasicPlanCode()
	{
		if (BasicPlanCode != null && !BasicPlanCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BasicPlanCode = StrTool.unicodeToGBK(BasicPlanCode);
		}
		return BasicPlanCode;
	}
	/** 险种代码 */
	public void setBasicPlanCode(String aBasicPlanCode)
	{
		BasicPlanCode = aBasicPlanCode;
	}
	/** 保单状态<P>1-年 2-月 3-半年 4-季 5-趸 6-不定期 9-其他 */
	public String getPolicyStatus()
	{
		if (PolicyStatus != null && !PolicyStatus.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyStatus = StrTool.unicodeToGBK(PolicyStatus);
		}
		return PolicyStatus;
	}
	/** 保单状态 */
	public void setPolicyStatus(String aPolicyStatus)
	{
		PolicyStatus = aPolicyStatus;
	}
	/** 保费累计<P> */
	public String getSumInsured()
	{
		if (SumInsured != null && !SumInsured.equals("") && SysConst.CHANGECHARSET == true)
		{
			SumInsured = StrTool.unicodeToGBK(SumInsured);
		}
		return SumInsured;
	}
	/** 保费累计 */
	public void setSumInsured(String aSumInsured)
	{
		SumInsured = aSumInsured;
	}
	/** 投保日期<P> */
	public int getApplicationDate()
	{
		return ApplicationDate;
	}
	/** 投保日期 */
	public void setApplicationDate(int aApplicationDate)
	{
		ApplicationDate = aApplicationDate;
	}
	/** 投保日期<P> */
	public void setApplicationDate(String aApplicationDate)
	{
		if (aApplicationDate != null && !aApplicationDate.equals(""))
		{
			Integer tInteger = new Integer(aApplicationDate);
			int i = tInteger.intValue();
			ApplicationDate = i;
		}
	}

	/** 签单日期<P> */
	public int getPolicyContractDate()
	{
		return PolicyContractDate;
	}
	/** 签单日期 */
	public void setPolicyContractDate(int aPolicyContractDate)
	{
		PolicyContractDate = aPolicyContractDate;
	}
	/** 签单日期<P> */
	public void setPolicyContractDate(String aPolicyContractDate)
	{
		if (aPolicyContractDate != null && !aPolicyContractDate.equals(""))
		{
			Integer tInteger = new Integer(aPolicyContractDate);
			int i = tInteger.intValue();
			PolicyContractDate = i;
		}
	}

	/** 支付方式<P> */
	public String getPaymentMethod()
	{
		if (PaymentMethod != null && !PaymentMethod.equals("") && SysConst.CHANGECHARSET == true)
		{
			PaymentMethod = StrTool.unicodeToGBK(PaymentMethod);
		}
		return PaymentMethod;
	}
	/** 支付方式 */
	public void setPaymentMethod(String aPaymentMethod)
	{
		PaymentMethod = aPaymentMethod;
	}
	/** Monthsdebited<P> */
	public String getMonthsdebited()
	{
		if (Monthsdebited != null && !Monthsdebited.equals("") && SysConst.CHANGECHARSET == true)
		{
			Monthsdebited = StrTool.unicodeToGBK(Monthsdebited);
		}
		return Monthsdebited;
	}
	/** Monthsdebited */
	public void setMonthsdebited(String aMonthsdebited)
	{
		Monthsdebited = aMonthsdebited;
	}
	/** 保单币种<P> */
	public String getPolicyCurrency()
	{
		if (PolicyCurrency != null && !PolicyCurrency.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyCurrency = StrTool.unicodeToGBK(PolicyCurrency);
		}
		return PolicyCurrency;
	}
	/** 保单币种 */
	public void setPolicyCurrency(String aPolicyCurrency)
	{
		PolicyCurrency = aPolicyCurrency;
	}
	/** 期缴保费<P> */
	public double getModalPremium()
	{
		return ModalPremium;
	}
	/** 期缴保费 */
	public void setModalPremium(double aModalPremium)
	{
		ModalPremium = aModalPremium;
	}
	/** 期缴保费<P> */
	public void setModalPremium(String aModalPremium)
	{
		if (aModalPremium != null && !aModalPremium.equals(""))
		{
			Double tDouble = new Double(aModalPremium);
			double d = tDouble.doubleValue();
			ModalPremium = d;
		}
	}

	/** 保费总计<P> */
	public double getLumpSumPremium()
	{
		return LumpSumPremium;
	}
	/** 保费总计 */
	public void setLumpSumPremium(double aLumpSumPremium)
	{
		LumpSumPremium = aLumpSumPremium;
	}
	/** 保费总计<P> */
	public void setLumpSumPremium(String aLumpSumPremium)
	{
		if (aLumpSumPremium != null && !aLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(aLumpSumPremium);
			double d = tDouble.doubleValue();
			LumpSumPremium = d;
		}
	}

	/** 首付总保费<P> */
	public double getInitLumpSumPremium()
	{
		return InitLumpSumPremium;
	}
	/** 首付总保费 */
	public void setInitLumpSumPremium(double aInitLumpSumPremium)
	{
		InitLumpSumPremium = aInitLumpSumPremium;
	}
	/** 首付总保费<P> */
	public void setInitLumpSumPremium(String aInitLumpSumPremium)
	{
		if (aInitLumpSumPremium != null && !aInitLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(aInitLumpSumPremium);
			double d = tDouble.doubleValue();
			InitLumpSumPremium = d;
		}
	}

	/** 定期额外投资保费<P> */
	public double getRegularTopUpPremium()
	{
		return RegularTopUpPremium;
	}
	/** 定期额外投资保费 */
	public void setRegularTopUpPremium(double aRegularTopUpPremium)
	{
		RegularTopUpPremium = aRegularTopUpPremium;
	}
	/** 定期额外投资保费<P> */
	public void setRegularTopUpPremium(String aRegularTopUpPremium)
	{
		if (aRegularTopUpPremium != null && !aRegularTopUpPremium.equals(""))
		{
			Double tDouble = new Double(aRegularTopUpPremium);
			double d = tDouble.doubleValue();
			RegularTopUpPremium = d;
		}
	}

	/** 缴费方式<P> */
	public String getPayMode()
	{
		if (PayMode != null && !PayMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayMode = StrTool.unicodeToGBK(PayMode);
		}
		return PayMode;
	}
	/** 缴费方式 */
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	/** 保单状态变更日期<P> */
	public int getStatusChangeDate()
	{
		return StatusChangeDate;
	}
	/** 保单状态变更日期 */
	public void setStatusChangeDate(int aStatusChangeDate)
	{
		StatusChangeDate = aStatusChangeDate;
	}
	/** 保单状态变更日期<P> */
	public void setStatusChangeDate(String aStatusChangeDate)
	{
		if (aStatusChangeDate != null && !aStatusChangeDate.equals(""))
		{
			Integer tInteger = new Integer(aStatusChangeDate);
			int i = tInteger.intValue();
			StatusChangeDate = i;
		}
	}

	/** 被保人身份证件号码<P> */
	public String getInsuredIDNo()
	{
		if (InsuredIDNo != null && !InsuredIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDNo = StrTool.unicodeToGBK(InsuredIDNo);
		}
		return InsuredIDNo;
	}
	/** 被保人身份证件号码 */
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
	}
	/** 被保人姓名<P> */
	public String getInsuredName()
	{
		if (InsuredName != null && !InsuredName.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredName = StrTool.unicodeToGBK(InsuredName);
		}
		return InsuredName;
	}
	/** 被保人姓名 */
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	/** 被保人生日<P> */
	public int getInsuredBirthday()
	{
		return InsuredBirthday;
	}
	/** 被保人生日 */
	public void setInsuredBirthday(int aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	/** 被保人生日<P> */
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals(""))
		{
			Integer tInteger = new Integer(aInsuredBirthday);
			int i = tInteger.intValue();
			InsuredBirthday = i;
		}
	}

	/** 被保人性别<P> */
	public String getInsuredGender()
	{
		if (InsuredGender != null && !InsuredGender.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredGender = StrTool.unicodeToGBK(InsuredGender);
		}
		return InsuredGender;
	}
	/** 被保人性别 */
	public void setInsuredGender(String aInsuredGender)
	{
		InsuredGender = aInsuredGender;
	}
	/** 投保年龄<P> */
	public String getAgeatIssue()
	{
		if (AgeatIssue != null && !AgeatIssue.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgeatIssue = StrTool.unicodeToGBK(AgeatIssue);
		}
		return AgeatIssue;
	}
	/** 投保年龄 */
	public void setAgeatIssue(String aAgeatIssue)
	{
		AgeatIssue = aAgeatIssue;
	}
	/** 代理1<P> */
	public String getProduceAgent1()
	{
		if (ProduceAgent1 != null && !ProduceAgent1.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProduceAgent1 = StrTool.unicodeToGBK(ProduceAgent1);
		}
		return ProduceAgent1;
	}
	/** 代理1 */
	public void setProduceAgent1(String aProduceAgent1)
	{
		ProduceAgent1 = aProduceAgent1;
	}
	/** 代理2<P> */
	public String getProduceAgent2()
	{
		if (ProduceAgent2 != null && !ProduceAgent2.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProduceAgent2 = StrTool.unicodeToGBK(ProduceAgent2);
		}
		return ProduceAgent2;
	}
	/** 代理2 */
	public void setProduceAgent2(String aProduceAgent2)
	{
		ProduceAgent2 = aProduceAgent2;
	}
	/** Unitcode<P> */
	public String getUnitCode()
	{
		if (UnitCode != null && !UnitCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UnitCode = StrTool.unicodeToGBK(UnitCode);
		}
		return UnitCode;
	}
	/** Unitcode */
	public void setUnitCode(String aUnitCode)
	{
		UnitCode = aUnitCode;
	}
	/** 代理公司代码<P> */
	public String getAgentSourceCode()
	{
		if (AgentSourceCode != null && !AgentSourceCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentSourceCode = StrTool.unicodeToGBK(AgentSourceCode);
		}
		return AgentSourceCode;
	}
	/** 代理公司代码 */
	public void setAgentSourceCode(String aAgentSourceCode)
	{
		AgentSourceCode = aAgentSourceCode;
	}
	/** 代理号码<P> */
	public String getAgentNo()
	{
		if (AgentNo != null && !AgentNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentNo = StrTool.unicodeToGBK(AgentNo);
		}
		return AgentNo;
	}
	/** 代理号码 */
	public void setAgentNo(String aAgentNo)
	{
		AgentNo = aAgentNo;
	}
	/** 代理类型<P> */
	public String getAgentType()
	{
		if (AgentType != null && !AgentType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentType = StrTool.unicodeToGBK(AgentType);
		}
		return AgentType;
	}
	/** 代理类型 */
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	/** 代理人姓名<P> */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** 代理人姓名 */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	/** 代理id<P> */
	public String getAgentID()
	{
		if (AgentID != null && !AgentID.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentID = StrTool.unicodeToGBK(AgentID);
		}
		return AgentID;
	}
	/** 代理id */
	public void setAgentID(String aAgentID)
	{
		AgentID = aAgentID;
	}
	/** 生成日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 生成日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 生成日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** 生成时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 生成时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 更新日期<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** 更新日期 */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 更新日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** 更新时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 更新时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 处理日期<P> */
	public int getdealDate()
	{
		return dealDate;
	}
	/** 处理日期 */
	public void setdealDate(int adealDate)
	{
		dealDate = adealDate;
	}
	/** 处理日期<P> */
	public void setdealDate(String adealDate)
	{
		if (adealDate != null && !adealDate.equals(""))
		{
			Integer tInteger = new Integer(adealDate);
			int i = tInteger.intValue();
			dealDate = i;
		}
	}

	/** 处理时间<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** 处理时间 */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* 使用另外一个 ApplicationMasterSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(ApplicationMasterSchema aApplicationMasterSchema)
	{
		this.SerialNo = aApplicationMasterSchema.getSerialNo();
		this.ExtractedDate = aApplicationMasterSchema.getExtractedDate();
		this.PolicyNo = aApplicationMasterSchema.getPolicyNo();
		this.SourceCode = aApplicationMasterSchema.getSourceCode();
		this.BasicPlanCode = aApplicationMasterSchema.getBasicPlanCode();
		this.PolicyStatus = aApplicationMasterSchema.getPolicyStatus();
		this.SumInsured = aApplicationMasterSchema.getSumInsured();
		this.ApplicationDate = aApplicationMasterSchema.getApplicationDate();
		this.PolicyContractDate = aApplicationMasterSchema.getPolicyContractDate();
		this.PaymentMethod = aApplicationMasterSchema.getPaymentMethod();
		this.Monthsdebited = aApplicationMasterSchema.getMonthsdebited();
		this.PolicyCurrency = aApplicationMasterSchema.getPolicyCurrency();
		this.ModalPremium = aApplicationMasterSchema.getModalPremium();
		this.LumpSumPremium = aApplicationMasterSchema.getLumpSumPremium();
		this.InitLumpSumPremium = aApplicationMasterSchema.getInitLumpSumPremium();
		this.RegularTopUpPremium = aApplicationMasterSchema.getRegularTopUpPremium();
		this.PayMode = aApplicationMasterSchema.getPayMode();
		this.StatusChangeDate = aApplicationMasterSchema.getStatusChangeDate();
		this.InsuredIDNo = aApplicationMasterSchema.getInsuredIDNo();
		this.InsuredName = aApplicationMasterSchema.getInsuredName();
		this.InsuredBirthday = aApplicationMasterSchema.getInsuredBirthday();
		this.InsuredGender = aApplicationMasterSchema.getInsuredGender();
		this.AgeatIssue = aApplicationMasterSchema.getAgeatIssue();
		this.ProduceAgent1 = aApplicationMasterSchema.getProduceAgent1();
		this.ProduceAgent2 = aApplicationMasterSchema.getProduceAgent2();
		this.UnitCode = aApplicationMasterSchema.getUnitCode();
		this.AgentSourceCode = aApplicationMasterSchema.getAgentSourceCode();
		this.AgentNo = aApplicationMasterSchema.getAgentNo();
		this.AgentType = aApplicationMasterSchema.getAgentType();
		this.AgentName = aApplicationMasterSchema.getAgentName();
		this.AgentID = aApplicationMasterSchema.getAgentID();
		this.MakeDate = aApplicationMasterSchema.getMakeDate();
		this.MakeTime = aApplicationMasterSchema.getMakeTime();
		this.ModifyDate = aApplicationMasterSchema.getModifyDate();
		this.ModifyTime = aApplicationMasterSchema.getModifyTime();
		this.dealDate = aApplicationMasterSchema.getdealDate();
		this.DealTime = aApplicationMasterSchema.getDealTime();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: ResultSet 对象; i 行数
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.ExtractedDate = rs.getInt("ExtractedDate");
			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("SourceCode") == null )
				this.SourceCode = null;
			else
				this.SourceCode = rs.getString("SourceCode").trim();

			if( rs.getString("BasicPlanCode") == null )
				this.BasicPlanCode = null;
			else
				this.BasicPlanCode = rs.getString("BasicPlanCode").trim();

			if( rs.getString("PolicyStatus") == null )
				this.PolicyStatus = null;
			else
				this.PolicyStatus = rs.getString("PolicyStatus").trim();

			if( rs.getString("SumInsured") == null )
				this.SumInsured = null;
			else
				this.SumInsured = rs.getString("SumInsured").trim();

			this.ApplicationDate = rs.getInt("ApplicationDate");
			this.PolicyContractDate = rs.getInt("PolicyContractDate");
			if( rs.getString("PaymentMethod") == null )
				this.PaymentMethod = null;
			else
				this.PaymentMethod = rs.getString("PaymentMethod").trim();

			if( rs.getString("Monthsdebited") == null )
				this.Monthsdebited = null;
			else
				this.Monthsdebited = rs.getString("Monthsdebited").trim();

			if( rs.getString("PolicyCurrency") == null )
				this.PolicyCurrency = null;
			else
				this.PolicyCurrency = rs.getString("PolicyCurrency").trim();

			this.ModalPremium = rs.getDouble("ModalPremium");
			this.LumpSumPremium = rs.getDouble("LumpSumPremium");
			this.InitLumpSumPremium = rs.getDouble("InitLumpSumPremium");
			this.RegularTopUpPremium = rs.getDouble("RegularTopUpPremium");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.StatusChangeDate = rs.getInt("StatusChangeDate");
			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			this.InsuredBirthday = rs.getInt("InsuredBirthday");
			if( rs.getString("InsuredGender") == null )
				this.InsuredGender = null;
			else
				this.InsuredGender = rs.getString("InsuredGender").trim();

			if( rs.getString("AgeatIssue") == null )
				this.AgeatIssue = null;
			else
				this.AgeatIssue = rs.getString("AgeatIssue").trim();

			if( rs.getString("ProduceAgent1") == null )
				this.ProduceAgent1 = null;
			else
				this.ProduceAgent1 = rs.getString("ProduceAgent1").trim();

			if( rs.getString("ProduceAgent2") == null )
				this.ProduceAgent2 = null;
			else
				this.ProduceAgent2 = rs.getString("ProduceAgent2").trim();

			if( rs.getString("UnitCode") == null )
				this.UnitCode = null;
			else
				this.UnitCode = rs.getString("UnitCode").trim();

			if( rs.getString("AgentSourceCode") == null )
				this.AgentSourceCode = null;
			else
				this.AgentSourceCode = rs.getString("AgentSourceCode").trim();

			if( rs.getString("AgentNo") == null )
				this.AgentNo = null;
			else
				this.AgentNo = rs.getString("AgentNo").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentID") == null )
				this.AgentID = null;
			else
				this.AgentID = rs.getString("AgentID").trim();

			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getInt("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.dealDate = rs.getInt("dealDate");
			if( rs.getString("DealTime") == null )
				this.DealTime = null;
			else
				this.DealTime = rs.getString("DealTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ApplicationMasterSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public ApplicationMasterSchema getSchema()
	{
		ApplicationMasterSchema aApplicationMasterSchema = new ApplicationMasterSchema();
		aApplicationMasterSchema.setSchema(this);
		return aApplicationMasterSchema;
	}

	public ApplicationMasterDB getDB()
	{
		ApplicationMasterDB aDBOper = new ApplicationMasterDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpApplicationMaster描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SourceCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BasicPlanCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyStatus) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SumInsured) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ApplicationDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PolicyContractDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PaymentMethod) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Monthsdebited) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyCurrency) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModalPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LumpSumPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InitLumpSumPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RegularTopUpPremium) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayMode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(StatusChangeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredBirthday) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredGender) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgeatIssue) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProduceAgent1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProduceAgent2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UnitCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentSourceCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentID) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(dealDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpApplicationMaster>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SourceCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BasicPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolicyStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SumInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ApplicationDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			PolicyContractDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			PaymentMethod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Monthsdebited = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PolicyCurrency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModalPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			LumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			InitLumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			RegularTopUpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StatusChangeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			InsuredBirthday= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			InsuredGender = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AgeatIssue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ProduceAgent1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ProduceAgent2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			UnitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AgentSourceCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			AgentNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			AgentID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			dealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ApplicationMasterSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode 希望取得的字段名
	* @return: FValue String形式的字段值
	*			FValue = ""		没有匹配的字段
	*			FValue = "null"	字段值为null
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equals("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equals("ExtractedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtractedDate));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("SourceCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceCode));
		}
		if (FCode.equals("BasicPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BasicPlanCode));
		}
		if (FCode.equals("PolicyStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyStatus));
		}
		if (FCode.equals("SumInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumInsured));
		}
		if (FCode.equals("ApplicationDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicationDate));
		}
		if (FCode.equals("PolicyContractDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyContractDate));
		}
		if (FCode.equals("PaymentMethod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaymentMethod));
		}
		if (FCode.equals("Monthsdebited"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Monthsdebited));
		}
		if (FCode.equals("PolicyCurrency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCurrency));
		}
		if (FCode.equals("ModalPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModalPremium));
		}
		if (FCode.equals("LumpSumPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LumpSumPremium));
		}
		if (FCode.equals("InitLumpSumPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitLumpSumPremium));
		}
		if (FCode.equals("RegularTopUpPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegularTopUpPremium));
		}
		if (FCode.equals("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equals("StatusChangeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatusChangeDate));
		}
		if (FCode.equals("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equals("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equals("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredBirthday));
		}
		if (FCode.equals("InsuredGender"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredGender));
		}
		if (FCode.equals("AgeatIssue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgeatIssue));
		}
		if (FCode.equals("ProduceAgent1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProduceAgent1));
		}
		if (FCode.equals("ProduceAgent2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProduceAgent2));
		}
		if (FCode.equals("UnitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitCode));
		}
		if (FCode.equals("AgentSourceCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentSourceCode));
		}
		if (FCode.equals("AgentNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentNo));
		}
		if (FCode.equals("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equals("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equals("AgentID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentID));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equals("dealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(dealDate));
		}
		if (FCode.equals("DealTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealTime));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex 指定的字段索引值
	* @return: 字段值。
	*          如果没有对应的字段，返回""
	*          如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = String.valueOf(ExtractedDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SourceCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BasicPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolicyStatus);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SumInsured);
				break;
			case 7:
				strFieldValue = String.valueOf(ApplicationDate);
				break;
			case 8:
				strFieldValue = String.valueOf(PolicyContractDate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PaymentMethod);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Monthsdebited);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PolicyCurrency);
				break;
			case 12:
				strFieldValue = String.valueOf(ModalPremium);
				break;
			case 13:
				strFieldValue = String.valueOf(LumpSumPremium);
				break;
			case 14:
				strFieldValue = String.valueOf(InitLumpSumPremium);
				break;
			case 15:
				strFieldValue = String.valueOf(RegularTopUpPremium);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 17:
				strFieldValue = String.valueOf(StatusChangeDate);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 20:
				strFieldValue = String.valueOf(InsuredBirthday);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InsuredGender);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AgeatIssue);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ProduceAgent1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ProduceAgent2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(UnitCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AgentSourceCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AgentNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AgentID);
				break;
			case 31:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 33:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 35:
				strFieldValue = String.valueOf(dealDate);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(DealTime);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode 希望取得的字段名
	* @return: FValue String形式的字段值
	*			FValue = ""		没有匹配的字段
	*			FValue = "null"	字段值为null
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equals("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equals("ExtractedDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExtractedDate = i;
			}
		}
		if (FCode.equals("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equals("SourceCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceCode = FValue.trim();
			}
			else
				SourceCode = null;
		}
		if (FCode.equals("BasicPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BasicPlanCode = FValue.trim();
			}
			else
				BasicPlanCode = null;
		}
		if (FCode.equals("PolicyStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyStatus = FValue.trim();
			}
			else
				PolicyStatus = null;
		}
		if (FCode.equals("SumInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SumInsured = FValue.trim();
			}
			else
				SumInsured = null;
		}
		if (FCode.equals("ApplicationDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ApplicationDate = i;
			}
		}
		if (FCode.equals("PolicyContractDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolicyContractDate = i;
			}
		}
		if (FCode.equals("PaymentMethod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaymentMethod = FValue.trim();
			}
			else
				PaymentMethod = null;
		}
		if (FCode.equals("Monthsdebited"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Monthsdebited = FValue.trim();
			}
			else
				Monthsdebited = null;
		}
		if (FCode.equals("PolicyCurrency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCurrency = FValue.trim();
			}
			else
				PolicyCurrency = null;
		}
		if (FCode.equals("ModalPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ModalPremium = d;
			}
		}
		if (FCode.equals("LumpSumPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LumpSumPremium = d;
			}
		}
		if (FCode.equals("InitLumpSumPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitLumpSumPremium = d;
			}
		}
		if (FCode.equals("RegularTopUpPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RegularTopUpPremium = d;
			}
		}
		if (FCode.equals("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equals("StatusChangeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StatusChangeDate = i;
			}
		}
		if (FCode.equals("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
		}
		if (FCode.equals("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		if (FCode.equals("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredBirthday = i;
			}
		}
		if (FCode.equals("InsuredGender"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredGender = FValue.trim();
			}
			else
				InsuredGender = null;
		}
		if (FCode.equals("AgeatIssue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgeatIssue = FValue.trim();
			}
			else
				AgeatIssue = null;
		}
		if (FCode.equals("ProduceAgent1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProduceAgent1 = FValue.trim();
			}
			else
				ProduceAgent1 = null;
		}
		if (FCode.equals("ProduceAgent2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProduceAgent2 = FValue.trim();
			}
			else
				ProduceAgent2 = null;
		}
		if (FCode.equals("UnitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitCode = FValue.trim();
			}
			else
				UnitCode = null;
		}
		if (FCode.equals("AgentSourceCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentSourceCode = FValue.trim();
			}
			else
				AgentSourceCode = null;
		}
		if (FCode.equals("AgentNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentNo = FValue.trim();
			}
			else
				AgentNo = null;
		}
		if (FCode.equals("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
		}
		if (FCode.equals("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
		}
		if (FCode.equals("AgentID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentID = FValue.trim();
			}
			else
				AgentID = null;
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MakeDate = i;
			}
		}
		if (FCode.equals("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyDate = i;
			}
		}
		if (FCode.equals("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equals("dealDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				dealDate = i;
			}
		}
		if (FCode.equals("DealTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealTime = FValue.trim();
			}
			else
				DealTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ApplicationMasterSchema other = (ApplicationMasterSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& SourceCode.equals(other.getSourceCode())
			&& BasicPlanCode.equals(other.getBasicPlanCode())
			&& PolicyStatus.equals(other.getPolicyStatus())
			&& SumInsured.equals(other.getSumInsured())
			&& ApplicationDate == other.getApplicationDate()
			&& PolicyContractDate == other.getPolicyContractDate()
			&& PaymentMethod.equals(other.getPaymentMethod())
			&& Monthsdebited.equals(other.getMonthsdebited())
			&& PolicyCurrency.equals(other.getPolicyCurrency())
			&& ModalPremium == other.getModalPremium()
			&& LumpSumPremium == other.getLumpSumPremium()
			&& InitLumpSumPremium == other.getInitLumpSumPremium()
			&& RegularTopUpPremium == other.getRegularTopUpPremium()
			&& PayMode.equals(other.getPayMode())
			&& StatusChangeDate == other.getStatusChangeDate()
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredBirthday == other.getInsuredBirthday()
			&& InsuredGender.equals(other.getInsuredGender())
			&& AgeatIssue.equals(other.getAgeatIssue())
			&& ProduceAgent1.equals(other.getProduceAgent1())
			&& ProduceAgent2.equals(other.getProduceAgent2())
			&& UnitCode.equals(other.getUnitCode())
			&& AgentSourceCode.equals(other.getAgentSourceCode())
			&& AgentNo.equals(other.getAgentNo())
			&& AgentType.equals(other.getAgentType())
			&& AgentName.equals(other.getAgentName())
			&& AgentID.equals(other.getAgentID())
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime.equals(other.getModifyTime())
			&& dealDate == other.getdealDate()
			&& DealTime.equals(other.getDealTime());
	}

	/**
	* 取得Schema拥有字段的数量
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("ExtractedDate") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("SourceCode") ) {
			return 3;
		}
		if( strFieldName.equals("BasicPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("PolicyStatus") ) {
			return 5;
		}
		if( strFieldName.equals("SumInsured") ) {
			return 6;
		}
		if( strFieldName.equals("ApplicationDate") ) {
			return 7;
		}
		if( strFieldName.equals("PolicyContractDate") ) {
			return 8;
		}
		if( strFieldName.equals("PaymentMethod") ) {
			return 9;
		}
		if( strFieldName.equals("Monthsdebited") ) {
			return 10;
		}
		if( strFieldName.equals("PolicyCurrency") ) {
			return 11;
		}
		if( strFieldName.equals("ModalPremium") ) {
			return 12;
		}
		if( strFieldName.equals("LumpSumPremium") ) {
			return 13;
		}
		if( strFieldName.equals("InitLumpSumPremium") ) {
			return 14;
		}
		if( strFieldName.equals("RegularTopUpPremium") ) {
			return 15;
		}
		if( strFieldName.equals("PayMode") ) {
			return 16;
		}
		if( strFieldName.equals("StatusChangeDate") ) {
			return 17;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 18;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 19;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 20;
		}
		if( strFieldName.equals("InsuredGender") ) {
			return 21;
		}
		if( strFieldName.equals("AgeatIssue") ) {
			return 22;
		}
		if( strFieldName.equals("ProduceAgent1") ) {
			return 23;
		}
		if( strFieldName.equals("ProduceAgent2") ) {
			return 24;
		}
		if( strFieldName.equals("UnitCode") ) {
			return 25;
		}
		if( strFieldName.equals("AgentSourceCode") ) {
			return 26;
		}
		if( strFieldName.equals("AgentNo") ) {
			return 27;
		}
		if( strFieldName.equals("AgentType") ) {
			return 28;
		}
		if( strFieldName.equals("AgentName") ) {
			return 29;
		}
		if( strFieldName.equals("AgentID") ) {
			return 30;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 31;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 34;
		}
		if( strFieldName.equals("dealDate") ) {
			return 35;
		}
		if( strFieldName.equals("DealTime") ) {
			return 36;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "ExtractedDate";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "SourceCode";
				break;
			case 4:
				strFieldName = "BasicPlanCode";
				break;
			case 5:
				strFieldName = "PolicyStatus";
				break;
			case 6:
				strFieldName = "SumInsured";
				break;
			case 7:
				strFieldName = "ApplicationDate";
				break;
			case 8:
				strFieldName = "PolicyContractDate";
				break;
			case 9:
				strFieldName = "PaymentMethod";
				break;
			case 10:
				strFieldName = "Monthsdebited";
				break;
			case 11:
				strFieldName = "PolicyCurrency";
				break;
			case 12:
				strFieldName = "ModalPremium";
				break;
			case 13:
				strFieldName = "LumpSumPremium";
				break;
			case 14:
				strFieldName = "InitLumpSumPremium";
				break;
			case 15:
				strFieldName = "RegularTopUpPremium";
				break;
			case 16:
				strFieldName = "PayMode";
				break;
			case 17:
				strFieldName = "StatusChangeDate";
				break;
			case 18:
				strFieldName = "InsuredIDNo";
				break;
			case 19:
				strFieldName = "InsuredName";
				break;
			case 20:
				strFieldName = "InsuredBirthday";
				break;
			case 21:
				strFieldName = "InsuredGender";
				break;
			case 22:
				strFieldName = "AgeatIssue";
				break;
			case 23:
				strFieldName = "ProduceAgent1";
				break;
			case 24:
				strFieldName = "ProduceAgent2";
				break;
			case 25:
				strFieldName = "UnitCode";
				break;
			case 26:
				strFieldName = "AgentSourceCode";
				break;
			case 27:
				strFieldName = "AgentNo";
				break;
			case 28:
				strFieldName = "AgentType";
				break;
			case 29:
				strFieldName = "AgentName";
				break;
			case 30:
				strFieldName = "AgentID";
				break;
			case 31:
				strFieldName = "MakeDate";
				break;
			case 32:
				strFieldName = "MakeTime";
				break;
			case 33:
				strFieldName = "ModifyDate";
				break;
			case 34:
				strFieldName = "ModifyTime";
				break;
			case 35:
				strFieldName = "dealDate";
				break;
			case 36:
				strFieldName = "DealTime";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExtractedDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourceCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BasicPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicationDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolicyContractDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PaymentMethod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Monthsdebited") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCurrency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModalPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LumpSumPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitLumpSumPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RegularTopUpPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StatusChangeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredGender") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgeatIssue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProduceAgent1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProduceAgent2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentSourceCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("dealDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DealTime") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_INT;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_INT;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
