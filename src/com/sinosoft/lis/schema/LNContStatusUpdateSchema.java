/*
 * <p>ClassName: LNContStatusUpdateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LNContStatusUpdate
 * @CreateDate：2014-08-20
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LNContStatusUpdateDB;

public class LNContStatusUpdateSchema implements Schema
{
	// @Field
	/** 业务种类 */
	private String Type;
	/** 银行代码 */
	private String BankCode;
	/** 银行地区号 */
	private String ZoneNo;
	/** 保险公司代码 */
	private String InsuComCode;
	/** 分公司代码 */
	private String ManageCom;
	/** 三级机构代码 */
	private String ComCode;
	/** 代理人代码 */
	private String AgentCode;
	/** 出单柜员号 */
	private String Operator;
	/** 投保单收到的日期 */
	private String PolicyAcceDate;
	/** 交易日期 */
	private String TransDate;
	/** 业务变更日期 */
	private String StateChangeDate;
	/** 险种代码 */
	private String RiskCode;
	/** 投保单号 */
	private String ProposalNo;
	/** 保单号 */
	private String PolNo;
	/** 投保人姓名 */
	private String AppntName;
	/** 投保人证件类型 */
	private String AppntIDType;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人姓名 */
	private String InsuName;
	/** 被保人证件类型 */
	private String InsuIDType;
	/** 被保人证件号码 */
	private String InsuIDNo;
	/** 总保费 */
	private String allPrem;
	/** 交易金额 */
	private String TransAmt;
	/** 是否犹豫期 */
	private String IsHesi;
	/** 保单到期日期 */
	private String InsuEndDate;
	/** 保单最新状态 */
	private String NewState;
	/** 申请人姓名 */
	private String ApplyName;
	/** 缴费期数 */
	private String PayTimes;
	/** 下期应缴费日期 */
	private String NextPayDate;
	/** 下期应缴费金额 */
	private String NextPayAmt;
	/** 本期应缴费日期 */
	private String PayDate;
	/** 增量现金价值 */
	private String IncreaCashValue;
	/** 最终现金价值 */
	private String FinalCashValue;
	/** 退保标志 */
	private String SurrMarks;
	/** 退保交易类型 */
	private String SurrTransType;
	/** 签单日期 */
	private String SignDate;
	/** 退保日期 */
	private String SurrDate;
	/** 处理日期 */
	private String DealDate;
	/** 犹豫期退保总金额 */
	private String SurrAllAmt;
	/** 主险退保份数 */
	private String MainRiskSurrMult;
	/** 主险退保金额 */
	private String MainRiskSurrAmt;
	/** 附加险个数 */
	private String AddRiskNum;
	/** 附加险退保险种1 */
	private String AddRiskCode1;
	/** 附加险退保份数1 */
	private String AddRiskSurrMult1;
	/** 附加险退保金额1 */
	private String AddRiskSurrAmt1;
	/** 附加险退保险种2 */
	private String AddRiskCode2;
	/** 附加险退保份数2 */
	private String AddRiskSurrMult2;
	/** 附加险退保金额2 */
	private String AddRiskSurrAmt2;
	/** 附加险退保险种3 */
	private String AddRiskCode3;
	/** 附加险退保份数3 */
	private String AddRiskSurrMult3;
	/** 附加险退保金额3 */
	private String AddRiskSurrAmt3;
	/** 附加险退保险种4 */
	private String AddRiskCode4;
	/** 附加险退保份数4 */
	private String AddRiskSurrMult4;
	/** 附加险退保金额4 */
	private String AddRiskSurrAmt4;
	/** 备用1 */
	private String BackUp1;
	/** 备用2 */
	private String BackUp2;
	/** 备用3 */
	private String BackUp3;
	/** 处理标记 */
	private String Flag;
	/** 处理信息 */
	private String DealMsg;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 62;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LNContStatusUpdateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PolNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 业务种类<P>业务种类 */
	public String getType()
	{
		if (Type != null && !Type.equals("") && SysConst.CHANGECHARSET == true)
		{
			Type = StrTool.unicodeToGBK(Type);
		}
		return Type;
	}
	/** 业务种类 */
	public void setType(String aType)
	{
		Type = aType;
	}
	/** 银行代码<P>银行代码 */
	public String getBankCode()
	{
		if (BankCode != null && !BankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankCode = StrTool.unicodeToGBK(BankCode);
		}
		return BankCode;
	}
	/** 银行代码 */
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/** 银行地区号<P>银行地区号 */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 银行地区号 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 保险公司代码<P>保险公司代码 */
	public String getInsuComCode()
	{
		if (InsuComCode != null && !InsuComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuComCode = StrTool.unicodeToGBK(InsuComCode);
		}
		return InsuComCode;
	}
	/** 保险公司代码 */
	public void setInsuComCode(String aInsuComCode)
	{
		InsuComCode = aInsuComCode;
	}
	/** 分公司代码<P>分公司代码 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 分公司代码 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** 三级机构代码<P>三级机构代码 */
	public String getComCode()
	{
		if (ComCode != null && !ComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComCode = StrTool.unicodeToGBK(ComCode);
		}
		return ComCode;
	}
	/** 三级机构代码 */
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	/** 代理人代码<P>代理人代码 */
	public String getAgentCode()
	{
		if (AgentCode != null && !AgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode = StrTool.unicodeToGBK(AgentCode);
		}
		return AgentCode;
	}
	/** 代理人代码 */
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/** 出单柜员号<P>出单柜员号 */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 出单柜员号 */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/** 投保单收到的日期<P>投保单收到的日期 */
	public String getPolicyAcceDate()
	{
		if (PolicyAcceDate != null && !PolicyAcceDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyAcceDate = StrTool.unicodeToGBK(PolicyAcceDate);
		}
		return PolicyAcceDate;
	}
	/** 投保单收到的日期 */
	public void setPolicyAcceDate(String aPolicyAcceDate)
	{
		PolicyAcceDate = aPolicyAcceDate;
	}
	/** 交易日期<P>交易日期 */
	public String getTransDate()
	{
		if (TransDate != null && !TransDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransDate = StrTool.unicodeToGBK(TransDate);
		}
		return TransDate;
	}
	/** 交易日期 */
	public void setTransDate(String aTransDate)
	{
		TransDate = aTransDate;
	}
	/** 业务变更日期<P>业务变更日期 */
	public String getStateChangeDate()
	{
		if (StateChangeDate != null && !StateChangeDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			StateChangeDate = StrTool.unicodeToGBK(StateChangeDate);
		}
		return StateChangeDate;
	}
	/** 业务变更日期 */
	public void setStateChangeDate(String aStateChangeDate)
	{
		StateChangeDate = aStateChangeDate;
	}
	/** 险种代码<P>险种代码 */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 险种代码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 投保单号<P>投保单号 */
	public String getProposalNo()
	{
		if (ProposalNo != null && !ProposalNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalNo = StrTool.unicodeToGBK(ProposalNo);
		}
		return ProposalNo;
	}
	/** 投保单号 */
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	/** 保单号<P>保单号 */
	public String getPolNo()
	{
		if (PolNo != null && !PolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolNo = StrTool.unicodeToGBK(PolNo);
		}
		return PolNo;
	}
	/** 保单号 */
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/** 投保人姓名<P>投保人姓名 */
	public String getAppntName()
	{
		if (AppntName != null && !AppntName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntName = StrTool.unicodeToGBK(AppntName);
		}
		return AppntName;
	}
	/** 投保人姓名 */
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	/** 投保人证件类型<P>投保人证件类型 */
	public String getAppntIDType()
	{
		if (AppntIDType != null && !AppntIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntIDType = StrTool.unicodeToGBK(AppntIDType);
		}
		return AppntIDType;
	}
	/** 投保人证件类型 */
	public void setAppntIDType(String aAppntIDType)
	{
		AppntIDType = aAppntIDType;
	}
	/** 投保人证件号码<P>投保人证件号码 */
	public String getAppntIDNo()
	{
		if (AppntIDNo != null && !AppntIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntIDNo = StrTool.unicodeToGBK(AppntIDNo);
		}
		return AppntIDNo;
	}
	/** 投保人证件号码 */
	public void setAppntIDNo(String aAppntIDNo)
	{
		AppntIDNo = aAppntIDNo;
	}
	/** 被保人姓名<P>被保人姓名 */
	public String getInsuName()
	{
		if (InsuName != null && !InsuName.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuName = StrTool.unicodeToGBK(InsuName);
		}
		return InsuName;
	}
	/** 被保人姓名 */
	public void setInsuName(String aInsuName)
	{
		InsuName = aInsuName;
	}
	/** 被保人证件类型<P>被保人证件类型 */
	public String getInsuIDType()
	{
		if (InsuIDType != null && !InsuIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuIDType = StrTool.unicodeToGBK(InsuIDType);
		}
		return InsuIDType;
	}
	/** 被保人证件类型 */
	public void setInsuIDType(String aInsuIDType)
	{
		InsuIDType = aInsuIDType;
	}
	/** 被保人证件号码<P>被保人证件号码 */
	public String getInsuIDNo()
	{
		if (InsuIDNo != null && !InsuIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuIDNo = StrTool.unicodeToGBK(InsuIDNo);
		}
		return InsuIDNo;
	}
	/** 被保人证件号码 */
	public void setInsuIDNo(String aInsuIDNo)
	{
		InsuIDNo = aInsuIDNo;
	}
	/** 总保费<P>总保费 */
	public String getallPrem()
	{
		if (allPrem != null && !allPrem.equals("") && SysConst.CHANGECHARSET == true)
		{
			allPrem = StrTool.unicodeToGBK(allPrem);
		}
		return allPrem;
	}
	/** 总保费 */
	public void setallPrem(String aallPrem)
	{
		allPrem = aallPrem;
	}
	/** 交易金额<P>交易金额 */
	public String getTransAmt()
	{
		if (TransAmt != null && !TransAmt.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransAmt = StrTool.unicodeToGBK(TransAmt);
		}
		return TransAmt;
	}
	/** 交易金额 */
	public void setTransAmt(String aTransAmt)
	{
		TransAmt = aTransAmt;
	}
	/** 是否犹豫期<P>是否犹豫期 */
	public String getIsHesi()
	{
		if (IsHesi != null && !IsHesi.equals("") && SysConst.CHANGECHARSET == true)
		{
			IsHesi = StrTool.unicodeToGBK(IsHesi);
		}
		return IsHesi;
	}
	/** 是否犹豫期 */
	public void setIsHesi(String aIsHesi)
	{
		IsHesi = aIsHesi;
	}
	/** 保单到期日期<P>保单到期日期 */
	public String getInsuEndDate()
	{
		if (InsuEndDate != null && !InsuEndDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuEndDate = StrTool.unicodeToGBK(InsuEndDate);
		}
		return InsuEndDate;
	}
	/** 保单到期日期 */
	public void setInsuEndDate(String aInsuEndDate)
	{
		InsuEndDate = aInsuEndDate;
	}
	/** 保单最新状态<P>保单最新状态 */
	public String getNewState()
	{
		if (NewState != null && !NewState.equals("") && SysConst.CHANGECHARSET == true)
		{
			NewState = StrTool.unicodeToGBK(NewState);
		}
		return NewState;
	}
	/** 保单最新状态 */
	public void setNewState(String aNewState)
	{
		NewState = aNewState;
	}
	/** 申请人姓名<P>申请人姓名 */
	public String getApplyName()
	{
		if (ApplyName != null && !ApplyName.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplyName = StrTool.unicodeToGBK(ApplyName);
		}
		return ApplyName;
	}
	/** 申请人姓名 */
	public void setApplyName(String aApplyName)
	{
		ApplyName = aApplyName;
	}
	/** 缴费期数<P>缴费期数 */
	public String getPayTimes()
	{
		if (PayTimes != null && !PayTimes.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayTimes = StrTool.unicodeToGBK(PayTimes);
		}
		return PayTimes;
	}
	/** 缴费期数 */
	public void setPayTimes(String aPayTimes)
	{
		PayTimes = aPayTimes;
	}
	/** 下期应缴费日期<P>下期应缴费日期 */
	public String getNextPayDate()
	{
		if (NextPayDate != null && !NextPayDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			NextPayDate = StrTool.unicodeToGBK(NextPayDate);
		}
		return NextPayDate;
	}
	/** 下期应缴费日期 */
	public void setNextPayDate(String aNextPayDate)
	{
		NextPayDate = aNextPayDate;
	}
	/** 下期应缴费金额<P>下期应缴费金额 */
	public String getNextPayAmt()
	{
		if (NextPayAmt != null && !NextPayAmt.equals("") && SysConst.CHANGECHARSET == true)
		{
			NextPayAmt = StrTool.unicodeToGBK(NextPayAmt);
		}
		return NextPayAmt;
	}
	/** 下期应缴费金额 */
	public void setNextPayAmt(String aNextPayAmt)
	{
		NextPayAmt = aNextPayAmt;
	}
	/** 本期应缴费日期<P>本期应缴费日期 */
	public String getPayDate()
	{
		if (PayDate != null && !PayDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayDate = StrTool.unicodeToGBK(PayDate);
		}
		return PayDate;
	}
	/** 本期应缴费日期 */
	public void setPayDate(String aPayDate)
	{
		PayDate = aPayDate;
	}
	/** 增量现金价值<P>增量现金价值 */
	public String getIncreaCashValue()
	{
		if (IncreaCashValue != null && !IncreaCashValue.equals("") && SysConst.CHANGECHARSET == true)
		{
			IncreaCashValue = StrTool.unicodeToGBK(IncreaCashValue);
		}
		return IncreaCashValue;
	}
	/** 增量现金价值 */
	public void setIncreaCashValue(String aIncreaCashValue)
	{
		IncreaCashValue = aIncreaCashValue;
	}
	/** 最终现金价值<P>最终现金价值 */
	public String getFinalCashValue()
	{
		if (FinalCashValue != null && !FinalCashValue.equals("") && SysConst.CHANGECHARSET == true)
		{
			FinalCashValue = StrTool.unicodeToGBK(FinalCashValue);
		}
		return FinalCashValue;
	}
	/** 最终现金价值 */
	public void setFinalCashValue(String aFinalCashValue)
	{
		FinalCashValue = aFinalCashValue;
	}
	/** 退保标志<P>退保标志 */
	public String getSurrMarks()
	{
		if (SurrMarks != null && !SurrMarks.equals("") && SysConst.CHANGECHARSET == true)
		{
			SurrMarks = StrTool.unicodeToGBK(SurrMarks);
		}
		return SurrMarks;
	}
	/** 退保标志 */
	public void setSurrMarks(String aSurrMarks)
	{
		SurrMarks = aSurrMarks;
	}
	/** 退保交易类型<P>退保交易类型 */
	public String getSurrTransType()
	{
		if (SurrTransType != null && !SurrTransType.equals("") && SysConst.CHANGECHARSET == true)
		{
			SurrTransType = StrTool.unicodeToGBK(SurrTransType);
		}
		return SurrTransType;
	}
	/** 退保交易类型 */
	public void setSurrTransType(String aSurrTransType)
	{
		SurrTransType = aSurrTransType;
	}
	/** 签单日期<P>签单日期 */
	public String getSignDate()
	{
		if (SignDate != null && !SignDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			SignDate = StrTool.unicodeToGBK(SignDate);
		}
		return SignDate;
	}
	/** 签单日期 */
	public void setSignDate(String aSignDate)
	{
		SignDate = aSignDate;
	}
	/** 退保日期<P>退保日期 */
	public String getSurrDate()
	{
		if (SurrDate != null && !SurrDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			SurrDate = StrTool.unicodeToGBK(SurrDate);
		}
		return SurrDate;
	}
	/** 退保日期 */
	public void setSurrDate(String aSurrDate)
	{
		SurrDate = aSurrDate;
	}
	/** 处理日期<P>处理日期 */
	public String getDealDate()
	{
		if (DealDate != null && !DealDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealDate = StrTool.unicodeToGBK(DealDate);
		}
		return DealDate;
	}
	/** 处理日期 */
	public void setDealDate(String aDealDate)
	{
		DealDate = aDealDate;
	}
	/** 犹豫期退保总金额<P>犹豫期退保总金额 */
	public String getSurrAllAmt()
	{
		if (SurrAllAmt != null && !SurrAllAmt.equals("") && SysConst.CHANGECHARSET == true)
		{
			SurrAllAmt = StrTool.unicodeToGBK(SurrAllAmt);
		}
		return SurrAllAmt;
	}
	/** 犹豫期退保总金额 */
	public void setSurrAllAmt(String aSurrAllAmt)
	{
		SurrAllAmt = aSurrAllAmt;
	}
	/** 主险退保份数<P>主险退保份数 */
	public String getMainRiskSurrMult()
	{
		if (MainRiskSurrMult != null && !MainRiskSurrMult.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainRiskSurrMult = StrTool.unicodeToGBK(MainRiskSurrMult);
		}
		return MainRiskSurrMult;
	}
	/** 主险退保份数 */
	public void setMainRiskSurrMult(String aMainRiskSurrMult)
	{
		MainRiskSurrMult = aMainRiskSurrMult;
	}
	/** 主险退保金额<P>主险退保金额 */
	public String getMainRiskSurrAmt()
	{
		if (MainRiskSurrAmt != null && !MainRiskSurrAmt.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainRiskSurrAmt = StrTool.unicodeToGBK(MainRiskSurrAmt);
		}
		return MainRiskSurrAmt;
	}
	/** 主险退保金额 */
	public void setMainRiskSurrAmt(String aMainRiskSurrAmt)
	{
		MainRiskSurrAmt = aMainRiskSurrAmt;
	}
	/** 附加险个数<P>附加险个数 */
	public String getAddRiskNum()
	{
		if (AddRiskNum != null && !AddRiskNum.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskNum = StrTool.unicodeToGBK(AddRiskNum);
		}
		return AddRiskNum;
	}
	/** 附加险个数 */
	public void setAddRiskNum(String aAddRiskNum)
	{
		AddRiskNum = aAddRiskNum;
	}
	/** 附加险退保险种1<P>附加险退保险种1 */
	public String getAddRiskCode1()
	{
		if (AddRiskCode1 != null && !AddRiskCode1.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskCode1 = StrTool.unicodeToGBK(AddRiskCode1);
		}
		return AddRiskCode1;
	}
	/** 附加险退保险种1 */
	public void setAddRiskCode1(String aAddRiskCode1)
	{
		AddRiskCode1 = aAddRiskCode1;
	}
	/** 附加险退保份数1<P>附加险退保份数1 */
	public String getAddRiskSurrMult1()
	{
		if (AddRiskSurrMult1 != null && !AddRiskSurrMult1.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrMult1 = StrTool.unicodeToGBK(AddRiskSurrMult1);
		}
		return AddRiskSurrMult1;
	}
	/** 附加险退保份数1 */
	public void setAddRiskSurrMult1(String aAddRiskSurrMult1)
	{
		AddRiskSurrMult1 = aAddRiskSurrMult1;
	}
	/** 附加险退保金额1<P>附加险退保金额1 */
	public String getAddRiskSurrAmt1()
	{
		if (AddRiskSurrAmt1 != null && !AddRiskSurrAmt1.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrAmt1 = StrTool.unicodeToGBK(AddRiskSurrAmt1);
		}
		return AddRiskSurrAmt1;
	}
	/** 附加险退保金额1 */
	public void setAddRiskSurrAmt1(String aAddRiskSurrAmt1)
	{
		AddRiskSurrAmt1 = aAddRiskSurrAmt1;
	}
	/** 附加险退保险种2<P>附加险退保险种2 */
	public String getAddRiskCode2()
	{
		if (AddRiskCode2 != null && !AddRiskCode2.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskCode2 = StrTool.unicodeToGBK(AddRiskCode2);
		}
		return AddRiskCode2;
	}
	/** 附加险退保险种2 */
	public void setAddRiskCode2(String aAddRiskCode2)
	{
		AddRiskCode2 = aAddRiskCode2;
	}
	/** 附加险退保份数2<P>附加险退保份数2 */
	public String getAddRiskSurrMult2()
	{
		if (AddRiskSurrMult2 != null && !AddRiskSurrMult2.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrMult2 = StrTool.unicodeToGBK(AddRiskSurrMult2);
		}
		return AddRiskSurrMult2;
	}
	/** 附加险退保份数2 */
	public void setAddRiskSurrMult2(String aAddRiskSurrMult2)
	{
		AddRiskSurrMult2 = aAddRiskSurrMult2;
	}
	/** 附加险退保金额2<P>附加险退保金额2 */
	public String getAddRiskSurrAmt2()
	{
		if (AddRiskSurrAmt2 != null && !AddRiskSurrAmt2.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrAmt2 = StrTool.unicodeToGBK(AddRiskSurrAmt2);
		}
		return AddRiskSurrAmt2;
	}
	/** 附加险退保金额2 */
	public void setAddRiskSurrAmt2(String aAddRiskSurrAmt2)
	{
		AddRiskSurrAmt2 = aAddRiskSurrAmt2;
	}
	/** 附加险退保险种3<P>附加险退保险种3 */
	public String getAddRiskCode3()
	{
		if (AddRiskCode3 != null && !AddRiskCode3.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskCode3 = StrTool.unicodeToGBK(AddRiskCode3);
		}
		return AddRiskCode3;
	}
	/** 附加险退保险种3 */
	public void setAddRiskCode3(String aAddRiskCode3)
	{
		AddRiskCode3 = aAddRiskCode3;
	}
	/** 附加险退保份数3<P>附加险退保份数3 */
	public String getAddRiskSurrMult3()
	{
		if (AddRiskSurrMult3 != null && !AddRiskSurrMult3.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrMult3 = StrTool.unicodeToGBK(AddRiskSurrMult3);
		}
		return AddRiskSurrMult3;
	}
	/** 附加险退保份数3 */
	public void setAddRiskSurrMult3(String aAddRiskSurrMult3)
	{
		AddRiskSurrMult3 = aAddRiskSurrMult3;
	}
	/** 附加险退保金额3<P>附加险退保金额3 */
	public String getAddRiskSurrAmt3()
	{
		if (AddRiskSurrAmt3 != null && !AddRiskSurrAmt3.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrAmt3 = StrTool.unicodeToGBK(AddRiskSurrAmt3);
		}
		return AddRiskSurrAmt3;
	}
	/** 附加险退保金额3 */
	public void setAddRiskSurrAmt3(String aAddRiskSurrAmt3)
	{
		AddRiskSurrAmt3 = aAddRiskSurrAmt3;
	}
	/** 附加险退保险种4<P>附加险退保险种4 */
	public String getAddRiskCode4()
	{
		if (AddRiskCode4 != null && !AddRiskCode4.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskCode4 = StrTool.unicodeToGBK(AddRiskCode4);
		}
		return AddRiskCode4;
	}
	/** 附加险退保险种4 */
	public void setAddRiskCode4(String aAddRiskCode4)
	{
		AddRiskCode4 = aAddRiskCode4;
	}
	/** 附加险退保份数4<P>附加险退保份数4 */
	public String getAddRiskSurrMult4()
	{
		if (AddRiskSurrMult4 != null && !AddRiskSurrMult4.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrMult4 = StrTool.unicodeToGBK(AddRiskSurrMult4);
		}
		return AddRiskSurrMult4;
	}
	/** 附加险退保份数4 */
	public void setAddRiskSurrMult4(String aAddRiskSurrMult4)
	{
		AddRiskSurrMult4 = aAddRiskSurrMult4;
	}
	/** 附加险退保金额4<P>附加险退保金额4 */
	public String getAddRiskSurrAmt4()
	{
		if (AddRiskSurrAmt4 != null && !AddRiskSurrAmt4.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddRiskSurrAmt4 = StrTool.unicodeToGBK(AddRiskSurrAmt4);
		}
		return AddRiskSurrAmt4;
	}
	/** 附加险退保金额4 */
	public void setAddRiskSurrAmt4(String aAddRiskSurrAmt4)
	{
		AddRiskSurrAmt4 = aAddRiskSurrAmt4;
	}
	/** 备用1<P>备用1 */
	public String getBackUp1()
	{
		if (BackUp1 != null && !BackUp1.equals("") && SysConst.CHANGECHARSET == true)
		{
			BackUp1 = StrTool.unicodeToGBK(BackUp1);
		}
		return BackUp1;
	}
	/** 备用1 */
	public void setBackUp1(String aBackUp1)
	{
		BackUp1 = aBackUp1;
	}
	/** 备用2<P>备用2 */
	public String getBackUp2()
	{
		if (BackUp2 != null && !BackUp2.equals("") && SysConst.CHANGECHARSET == true)
		{
			BackUp2 = StrTool.unicodeToGBK(BackUp2);
		}
		return BackUp2;
	}
	/** 备用2 */
	public void setBackUp2(String aBackUp2)
	{
		BackUp2 = aBackUp2;
	}
	/** 备用3<P>备用3 */
	public String getBackUp3()
	{
		if (BackUp3 != null && !BackUp3.equals("") && SysConst.CHANGECHARSET == true)
		{
			BackUp3 = StrTool.unicodeToGBK(BackUp3);
		}
		return BackUp3;
	}
	/** 备用3 */
	public void setBackUp3(String aBackUp3)
	{
		BackUp3 = aBackUp3;
	}
	/** 处理标记<P>处理标记 */
	public String getFlag()
	{
		if (Flag != null && !Flag.equals("") && SysConst.CHANGECHARSET == true)
		{
			Flag = StrTool.unicodeToGBK(Flag);
		}
		return Flag;
	}
	/** 处理标记 */
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
	}
	/** 处理信息<P>处理信息 */
	public String getDealMsg()
	{
		if (DealMsg != null && !DealMsg.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealMsg = StrTool.unicodeToGBK(DealMsg);
		}
		return DealMsg;
	}
	/** 处理信息 */
	public void setDealMsg(String aDealMsg)
	{
		DealMsg = aDealMsg;
	}
	/** 创建日期<P>创建日期 */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 创建日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 创建日期<P>创建日期 */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 创建时间<P>创建时间 */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 创建时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 修改日期<P>修改日期 */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 修改日期<P>修改日期 */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 修改时间<P>修改时间 */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LNContStatusUpdateSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LNContStatusUpdateSchema aLNContStatusUpdateSchema)
	{
		this.Type = aLNContStatusUpdateSchema.getType();
		this.BankCode = aLNContStatusUpdateSchema.getBankCode();
		this.ZoneNo = aLNContStatusUpdateSchema.getZoneNo();
		this.InsuComCode = aLNContStatusUpdateSchema.getInsuComCode();
		this.ManageCom = aLNContStatusUpdateSchema.getManageCom();
		this.ComCode = aLNContStatusUpdateSchema.getComCode();
		this.AgentCode = aLNContStatusUpdateSchema.getAgentCode();
		this.Operator = aLNContStatusUpdateSchema.getOperator();
		this.PolicyAcceDate = aLNContStatusUpdateSchema.getPolicyAcceDate();
		this.TransDate = aLNContStatusUpdateSchema.getTransDate();
		this.StateChangeDate = aLNContStatusUpdateSchema.getStateChangeDate();
		this.RiskCode = aLNContStatusUpdateSchema.getRiskCode();
		this.ProposalNo = aLNContStatusUpdateSchema.getProposalNo();
		this.PolNo = aLNContStatusUpdateSchema.getPolNo();
		this.AppntName = aLNContStatusUpdateSchema.getAppntName();
		this.AppntIDType = aLNContStatusUpdateSchema.getAppntIDType();
		this.AppntIDNo = aLNContStatusUpdateSchema.getAppntIDNo();
		this.InsuName = aLNContStatusUpdateSchema.getInsuName();
		this.InsuIDType = aLNContStatusUpdateSchema.getInsuIDType();
		this.InsuIDNo = aLNContStatusUpdateSchema.getInsuIDNo();
		this.allPrem = aLNContStatusUpdateSchema.getallPrem();
		this.TransAmt = aLNContStatusUpdateSchema.getTransAmt();
		this.IsHesi = aLNContStatusUpdateSchema.getIsHesi();
		this.InsuEndDate = aLNContStatusUpdateSchema.getInsuEndDate();
		this.NewState = aLNContStatusUpdateSchema.getNewState();
		this.ApplyName = aLNContStatusUpdateSchema.getApplyName();
		this.PayTimes = aLNContStatusUpdateSchema.getPayTimes();
		this.NextPayDate = aLNContStatusUpdateSchema.getNextPayDate();
		this.NextPayAmt = aLNContStatusUpdateSchema.getNextPayAmt();
		this.PayDate = aLNContStatusUpdateSchema.getPayDate();
		this.IncreaCashValue = aLNContStatusUpdateSchema.getIncreaCashValue();
		this.FinalCashValue = aLNContStatusUpdateSchema.getFinalCashValue();
		this.SurrMarks = aLNContStatusUpdateSchema.getSurrMarks();
		this.SurrTransType = aLNContStatusUpdateSchema.getSurrTransType();
		this.SignDate = aLNContStatusUpdateSchema.getSignDate();
		this.SurrDate = aLNContStatusUpdateSchema.getSurrDate();
		this.DealDate = aLNContStatusUpdateSchema.getDealDate();
		this.SurrAllAmt = aLNContStatusUpdateSchema.getSurrAllAmt();
		this.MainRiskSurrMult = aLNContStatusUpdateSchema.getMainRiskSurrMult();
		this.MainRiskSurrAmt = aLNContStatusUpdateSchema.getMainRiskSurrAmt();
		this.AddRiskNum = aLNContStatusUpdateSchema.getAddRiskNum();
		this.AddRiskCode1 = aLNContStatusUpdateSchema.getAddRiskCode1();
		this.AddRiskSurrMult1 = aLNContStatusUpdateSchema.getAddRiskSurrMult1();
		this.AddRiskSurrAmt1 = aLNContStatusUpdateSchema.getAddRiskSurrAmt1();
		this.AddRiskCode2 = aLNContStatusUpdateSchema.getAddRiskCode2();
		this.AddRiskSurrMult2 = aLNContStatusUpdateSchema.getAddRiskSurrMult2();
		this.AddRiskSurrAmt2 = aLNContStatusUpdateSchema.getAddRiskSurrAmt2();
		this.AddRiskCode3 = aLNContStatusUpdateSchema.getAddRiskCode3();
		this.AddRiskSurrMult3 = aLNContStatusUpdateSchema.getAddRiskSurrMult3();
		this.AddRiskSurrAmt3 = aLNContStatusUpdateSchema.getAddRiskSurrAmt3();
		this.AddRiskCode4 = aLNContStatusUpdateSchema.getAddRiskCode4();
		this.AddRiskSurrMult4 = aLNContStatusUpdateSchema.getAddRiskSurrMult4();
		this.AddRiskSurrAmt4 = aLNContStatusUpdateSchema.getAddRiskSurrAmt4();
		this.BackUp1 = aLNContStatusUpdateSchema.getBackUp1();
		this.BackUp2 = aLNContStatusUpdateSchema.getBackUp2();
		this.BackUp3 = aLNContStatusUpdateSchema.getBackUp3();
		this.Flag = aLNContStatusUpdateSchema.getFlag();
		this.DealMsg = aLNContStatusUpdateSchema.getDealMsg();
		this.MakeDate = fDate.getDate( aLNContStatusUpdateSchema.getMakeDate());
		this.MakeTime = aLNContStatusUpdateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLNContStatusUpdateSchema.getModifyDate());
		this.ModifyTime = aLNContStatusUpdateSchema.getModifyTime();
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
			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("InsuComCode") == null )
				this.InsuComCode = null;
			else
				this.InsuComCode = rs.getString("InsuComCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("PolicyAcceDate") == null )
				this.PolicyAcceDate = null;
			else
				this.PolicyAcceDate = rs.getString("PolicyAcceDate").trim();

			if( rs.getString("TransDate") == null )
				this.TransDate = null;
			else
				this.TransDate = rs.getString("TransDate").trim();

			if( rs.getString("StateChangeDate") == null )
				this.StateChangeDate = null;
			else
				this.StateChangeDate = rs.getString("StateChangeDate").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntIDType") == null )
				this.AppntIDType = null;
			else
				this.AppntIDType = rs.getString("AppntIDType").trim();

			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

			if( rs.getString("InsuName") == null )
				this.InsuName = null;
			else
				this.InsuName = rs.getString("InsuName").trim();

			if( rs.getString("InsuIDType") == null )
				this.InsuIDType = null;
			else
				this.InsuIDType = rs.getString("InsuIDType").trim();

			if( rs.getString("InsuIDNo") == null )
				this.InsuIDNo = null;
			else
				this.InsuIDNo = rs.getString("InsuIDNo").trim();

			if( rs.getString("allPrem") == null )
				this.allPrem = null;
			else
				this.allPrem = rs.getString("allPrem").trim();

			if( rs.getString("TransAmt") == null )
				this.TransAmt = null;
			else
				this.TransAmt = rs.getString("TransAmt").trim();

			if( rs.getString("IsHesi") == null )
				this.IsHesi = null;
			else
				this.IsHesi = rs.getString("IsHesi").trim();

			if( rs.getString("InsuEndDate") == null )
				this.InsuEndDate = null;
			else
				this.InsuEndDate = rs.getString("InsuEndDate").trim();

			if( rs.getString("NewState") == null )
				this.NewState = null;
			else
				this.NewState = rs.getString("NewState").trim();

			if( rs.getString("ApplyName") == null )
				this.ApplyName = null;
			else
				this.ApplyName = rs.getString("ApplyName").trim();

			if( rs.getString("PayTimes") == null )
				this.PayTimes = null;
			else
				this.PayTimes = rs.getString("PayTimes").trim();

			if( rs.getString("NextPayDate") == null )
				this.NextPayDate = null;
			else
				this.NextPayDate = rs.getString("NextPayDate").trim();

			if( rs.getString("NextPayAmt") == null )
				this.NextPayAmt = null;
			else
				this.NextPayAmt = rs.getString("NextPayAmt").trim();

			if( rs.getString("PayDate") == null )
				this.PayDate = null;
			else
				this.PayDate = rs.getString("PayDate").trim();

			if( rs.getString("IncreaCashValue") == null )
				this.IncreaCashValue = null;
			else
				this.IncreaCashValue = rs.getString("IncreaCashValue").trim();

			if( rs.getString("FinalCashValue") == null )
				this.FinalCashValue = null;
			else
				this.FinalCashValue = rs.getString("FinalCashValue").trim();

			if( rs.getString("SurrMarks") == null )
				this.SurrMarks = null;
			else
				this.SurrMarks = rs.getString("SurrMarks").trim();

			if( rs.getString("SurrTransType") == null )
				this.SurrTransType = null;
			else
				this.SurrTransType = rs.getString("SurrTransType").trim();

			if( rs.getString("SignDate") == null )
				this.SignDate = null;
			else
				this.SignDate = rs.getString("SignDate").trim();

			if( rs.getString("SurrDate") == null )
				this.SurrDate = null;
			else
				this.SurrDate = rs.getString("SurrDate").trim();

			if( rs.getString("DealDate") == null )
				this.DealDate = null;
			else
				this.DealDate = rs.getString("DealDate").trim();

			if( rs.getString("SurrAllAmt") == null )
				this.SurrAllAmt = null;
			else
				this.SurrAllAmt = rs.getString("SurrAllAmt").trim();

			if( rs.getString("MainRiskSurrMult") == null )
				this.MainRiskSurrMult = null;
			else
				this.MainRiskSurrMult = rs.getString("MainRiskSurrMult").trim();

			if( rs.getString("MainRiskSurrAmt") == null )
				this.MainRiskSurrAmt = null;
			else
				this.MainRiskSurrAmt = rs.getString("MainRiskSurrAmt").trim();

			if( rs.getString("AddRiskNum") == null )
				this.AddRiskNum = null;
			else
				this.AddRiskNum = rs.getString("AddRiskNum").trim();

			if( rs.getString("AddRiskCode1") == null )
				this.AddRiskCode1 = null;
			else
				this.AddRiskCode1 = rs.getString("AddRiskCode1").trim();

			if( rs.getString("AddRiskSurrMult1") == null )
				this.AddRiskSurrMult1 = null;
			else
				this.AddRiskSurrMult1 = rs.getString("AddRiskSurrMult1").trim();

			if( rs.getString("AddRiskSurrAmt1") == null )
				this.AddRiskSurrAmt1 = null;
			else
				this.AddRiskSurrAmt1 = rs.getString("AddRiskSurrAmt1").trim();

			if( rs.getString("AddRiskCode2") == null )
				this.AddRiskCode2 = null;
			else
				this.AddRiskCode2 = rs.getString("AddRiskCode2").trim();

			if( rs.getString("AddRiskSurrMult2") == null )
				this.AddRiskSurrMult2 = null;
			else
				this.AddRiskSurrMult2 = rs.getString("AddRiskSurrMult2").trim();

			if( rs.getString("AddRiskSurrAmt2") == null )
				this.AddRiskSurrAmt2 = null;
			else
				this.AddRiskSurrAmt2 = rs.getString("AddRiskSurrAmt2").trim();

			if( rs.getString("AddRiskCode3") == null )
				this.AddRiskCode3 = null;
			else
				this.AddRiskCode3 = rs.getString("AddRiskCode3").trim();

			if( rs.getString("AddRiskSurrMult3") == null )
				this.AddRiskSurrMult3 = null;
			else
				this.AddRiskSurrMult3 = rs.getString("AddRiskSurrMult3").trim();

			if( rs.getString("AddRiskSurrAmt3") == null )
				this.AddRiskSurrAmt3 = null;
			else
				this.AddRiskSurrAmt3 = rs.getString("AddRiskSurrAmt3").trim();

			if( rs.getString("AddRiskCode4") == null )
				this.AddRiskCode4 = null;
			else
				this.AddRiskCode4 = rs.getString("AddRiskCode4").trim();

			if( rs.getString("AddRiskSurrMult4") == null )
				this.AddRiskSurrMult4 = null;
			else
				this.AddRiskSurrMult4 = rs.getString("AddRiskSurrMult4").trim();

			if( rs.getString("AddRiskSurrAmt4") == null )
				this.AddRiskSurrAmt4 = null;
			else
				this.AddRiskSurrAmt4 = rs.getString("AddRiskSurrAmt4").trim();

			if( rs.getString("BackUp1") == null )
				this.BackUp1 = null;
			else
				this.BackUp1 = rs.getString("BackUp1").trim();

			if( rs.getString("BackUp2") == null )
				this.BackUp2 = null;
			else
				this.BackUp2 = rs.getString("BackUp2").trim();

			if( rs.getString("BackUp3") == null )
				this.BackUp3 = null;
			else
				this.BackUp3 = rs.getString("BackUp3").trim();

			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			if( rs.getString("DealMsg") == null )
				this.DealMsg = null;
			else
				this.DealMsg = rs.getString("DealMsg").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LNContStatusUpdateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LNContStatusUpdateSchema getSchema()
	{
		LNContStatusUpdateSchema aLNContStatusUpdateSchema = new LNContStatusUpdateSchema();
		aLNContStatusUpdateSchema.setSchema(this);
		return aLNContStatusUpdateSchema;
	}

	public LNContStatusUpdateDB getDB()
	{
		LNContStatusUpdateDB aDBOper = new LNContStatusUpdateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLNContStatusUpdate描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(Type) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyAcceDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StateChangeDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(allPrem) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransAmt) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IsHesi) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuEndDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NewState) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplyName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayTimes) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NextPayDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NextPayAmt) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IncreaCashValue) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FinalCashValue) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SurrMarks) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SurrTransType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SignDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SurrDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SurrAllAmt) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainRiskSurrMult) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainRiskSurrAmt) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskNum) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskCode1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrMult1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrAmt1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskCode2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrMult2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrAmt2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskCode3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrMult3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrAmt3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskCode4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrMult4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddRiskSurrAmt4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BackUp1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BackUp2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BackUp3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Flag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealMsg) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLNContStatusUpdate>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolicyAcceDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TransDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StateChangeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppntIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			InsuName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InsuIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			InsuIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			allPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			TransAmt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			IsHesi = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			NewState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ApplyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PayTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			NextPayDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			NextPayAmt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PayDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			IncreaCashValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			FinalCashValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			SurrMarks = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SurrTransType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			SignDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			SurrDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			DealDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SurrAllAmt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MainRiskSurrMult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MainRiskSurrAmt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			AddRiskNum = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			AddRiskCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			AddRiskSurrMult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AddRiskSurrAmt1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			AddRiskCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			AddRiskSurrMult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			AddRiskSurrAmt2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			AddRiskCode3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			AddRiskSurrMult3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			AddRiskSurrAmt3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			AddRiskCode4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			AddRiskSurrMult4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			AddRiskSurrAmt4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			BackUp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			BackUp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			BackUp3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			DealMsg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LNContStatusUpdateSchema";
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
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("InsuComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuComCode));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("PolicyAcceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyAcceDate));
		}
		if (FCode.equals("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransDate));
		}
		if (FCode.equals("StateChangeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateChangeDate));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equals("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equals("AppntIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDType));
		}
		if (FCode.equals("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
		}
		if (FCode.equals("InsuName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuName));
		}
		if (FCode.equals("InsuIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuIDType));
		}
		if (FCode.equals("InsuIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuIDNo));
		}
		if (FCode.equals("allPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(allPrem));
		}
		if (FCode.equals("TransAmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransAmt));
		}
		if (FCode.equals("IsHesi"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsHesi));
		}
		if (FCode.equals("InsuEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuEndDate));
		}
		if (FCode.equals("NewState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewState));
		}
		if (FCode.equals("ApplyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyName));
		}
		if (FCode.equals("PayTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayTimes));
		}
		if (FCode.equals("NextPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NextPayDate));
		}
		if (FCode.equals("NextPayAmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NextPayAmt));
		}
		if (FCode.equals("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayDate));
		}
		if (FCode.equals("IncreaCashValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncreaCashValue));
		}
		if (FCode.equals("FinalCashValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinalCashValue));
		}
		if (FCode.equals("SurrMarks"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrMarks));
		}
		if (FCode.equals("SurrTransType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrTransType));
		}
		if (FCode.equals("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDate));
		}
		if (FCode.equals("SurrDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrDate));
		}
		if (FCode.equals("DealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealDate));
		}
		if (FCode.equals("SurrAllAmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrAllAmt));
		}
		if (FCode.equals("MainRiskSurrMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskSurrMult));
		}
		if (FCode.equals("MainRiskSurrAmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskSurrAmt));
		}
		if (FCode.equals("AddRiskNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskNum));
		}
		if (FCode.equals("AddRiskCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskCode1));
		}
		if (FCode.equals("AddRiskSurrMult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrMult1));
		}
		if (FCode.equals("AddRiskSurrAmt1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrAmt1));
		}
		if (FCode.equals("AddRiskCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskCode2));
		}
		if (FCode.equals("AddRiskSurrMult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrMult2));
		}
		if (FCode.equals("AddRiskSurrAmt2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrAmt2));
		}
		if (FCode.equals("AddRiskCode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskCode3));
		}
		if (FCode.equals("AddRiskSurrMult3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrMult3));
		}
		if (FCode.equals("AddRiskSurrAmt3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrAmt3));
		}
		if (FCode.equals("AddRiskCode4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskCode4));
		}
		if (FCode.equals("AddRiskSurrMult4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrMult4));
		}
		if (FCode.equals("AddRiskSurrAmt4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRiskSurrAmt4));
		}
		if (FCode.equals("BackUp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUp1));
		}
		if (FCode.equals("BackUp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUp2));
		}
		if (FCode.equals("BackUp3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUp3));
		}
		if (FCode.equals("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equals("DealMsg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealMsg));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuComCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolicyAcceDate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(TransDate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StateChangeDate);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AppntIDType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(InsuName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InsuIDType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(InsuIDNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(allPrem);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(TransAmt);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(IsHesi);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuEndDate);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(NewState);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ApplyName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(PayTimes);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(NextPayDate);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(NextPayAmt);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(PayDate);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(IncreaCashValue);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(FinalCashValue);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(SurrMarks);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SurrTransType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(SignDate);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(SurrDate);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(DealDate);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SurrAllAmt);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MainRiskSurrMult);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MainRiskSurrAmt);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(AddRiskNum);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(AddRiskCode1);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrMult1);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrAmt1);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(AddRiskCode2);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrMult2);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrAmt2);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(AddRiskCode3);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrMult3);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrAmt3);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(AddRiskCode4);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrMult4);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(AddRiskSurrAmt4);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(BackUp1);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(BackUp2);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(BackUp3);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(DealMsg);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equals("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equals("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equals("ZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneNo = FValue.trim();
			}
			else
				ZoneNo = null;
		}
		if (FCode.equals("InsuComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuComCode = FValue.trim();
			}
			else
				InsuComCode = null;
		}
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equals("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equals("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equals("PolicyAcceDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyAcceDate = FValue.trim();
			}
			else
				PolicyAcceDate = null;
		}
		if (FCode.equals("TransDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransDate = FValue.trim();
			}
			else
				TransDate = null;
		}
		if (FCode.equals("StateChangeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateChangeDate = FValue.trim();
			}
			else
				StateChangeDate = null;
		}
		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equals("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
		}
		if (FCode.equals("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equals("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equals("AppntIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDType = FValue.trim();
			}
			else
				AppntIDType = null;
		}
		if (FCode.equals("AppntIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDNo = FValue.trim();
			}
			else
				AppntIDNo = null;
		}
		if (FCode.equals("InsuName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuName = FValue.trim();
			}
			else
				InsuName = null;
		}
		if (FCode.equals("InsuIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuIDType = FValue.trim();
			}
			else
				InsuIDType = null;
		}
		if (FCode.equals("InsuIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuIDNo = FValue.trim();
			}
			else
				InsuIDNo = null;
		}
		if (FCode.equals("allPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				allPrem = FValue.trim();
			}
			else
				allPrem = null;
		}
		if (FCode.equals("TransAmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransAmt = FValue.trim();
			}
			else
				TransAmt = null;
		}
		if (FCode.equals("IsHesi"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsHesi = FValue.trim();
			}
			else
				IsHesi = null;
		}
		if (FCode.equals("InsuEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuEndDate = FValue.trim();
			}
			else
				InsuEndDate = null;
		}
		if (FCode.equals("NewState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewState = FValue.trim();
			}
			else
				NewState = null;
		}
		if (FCode.equals("ApplyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyName = FValue.trim();
			}
			else
				ApplyName = null;
		}
		if (FCode.equals("PayTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayTimes = FValue.trim();
			}
			else
				PayTimes = null;
		}
		if (FCode.equals("NextPayDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NextPayDate = FValue.trim();
			}
			else
				NextPayDate = null;
		}
		if (FCode.equals("NextPayAmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NextPayAmt = FValue.trim();
			}
			else
				NextPayAmt = null;
		}
		if (FCode.equals("PayDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayDate = FValue.trim();
			}
			else
				PayDate = null;
		}
		if (FCode.equals("IncreaCashValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncreaCashValue = FValue.trim();
			}
			else
				IncreaCashValue = null;
		}
		if (FCode.equals("FinalCashValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinalCashValue = FValue.trim();
			}
			else
				FinalCashValue = null;
		}
		if (FCode.equals("SurrMarks"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrMarks = FValue.trim();
			}
			else
				SurrMarks = null;
		}
		if (FCode.equals("SurrTransType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrTransType = FValue.trim();
			}
			else
				SurrTransType = null;
		}
		if (FCode.equals("SignDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignDate = FValue.trim();
			}
			else
				SignDate = null;
		}
		if (FCode.equals("SurrDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrDate = FValue.trim();
			}
			else
				SurrDate = null;
		}
		if (FCode.equals("DealDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealDate = FValue.trim();
			}
			else
				DealDate = null;
		}
		if (FCode.equals("SurrAllAmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrAllAmt = FValue.trim();
			}
			else
				SurrAllAmt = null;
		}
		if (FCode.equals("MainRiskSurrMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskSurrMult = FValue.trim();
			}
			else
				MainRiskSurrMult = null;
		}
		if (FCode.equals("MainRiskSurrAmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskSurrAmt = FValue.trim();
			}
			else
				MainRiskSurrAmt = null;
		}
		if (FCode.equals("AddRiskNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskNum = FValue.trim();
			}
			else
				AddRiskNum = null;
		}
		if (FCode.equals("AddRiskCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskCode1 = FValue.trim();
			}
			else
				AddRiskCode1 = null;
		}
		if (FCode.equals("AddRiskSurrMult1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrMult1 = FValue.trim();
			}
			else
				AddRiskSurrMult1 = null;
		}
		if (FCode.equals("AddRiskSurrAmt1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrAmt1 = FValue.trim();
			}
			else
				AddRiskSurrAmt1 = null;
		}
		if (FCode.equals("AddRiskCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskCode2 = FValue.trim();
			}
			else
				AddRiskCode2 = null;
		}
		if (FCode.equals("AddRiskSurrMult2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrMult2 = FValue.trim();
			}
			else
				AddRiskSurrMult2 = null;
		}
		if (FCode.equals("AddRiskSurrAmt2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrAmt2 = FValue.trim();
			}
			else
				AddRiskSurrAmt2 = null;
		}
		if (FCode.equals("AddRiskCode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskCode3 = FValue.trim();
			}
			else
				AddRiskCode3 = null;
		}
		if (FCode.equals("AddRiskSurrMult3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrMult3 = FValue.trim();
			}
			else
				AddRiskSurrMult3 = null;
		}
		if (FCode.equals("AddRiskSurrAmt3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrAmt3 = FValue.trim();
			}
			else
				AddRiskSurrAmt3 = null;
		}
		if (FCode.equals("AddRiskCode4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskCode4 = FValue.trim();
			}
			else
				AddRiskCode4 = null;
		}
		if (FCode.equals("AddRiskSurrMult4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrMult4 = FValue.trim();
			}
			else
				AddRiskSurrMult4 = null;
		}
		if (FCode.equals("AddRiskSurrAmt4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddRiskSurrAmt4 = FValue.trim();
			}
			else
				AddRiskSurrAmt4 = null;
		}
		if (FCode.equals("BackUp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUp1 = FValue.trim();
			}
			else
				BackUp1 = null;
		}
		if (FCode.equals("BackUp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUp2 = FValue.trim();
			}
			else
				BackUp2 = null;
		}
		if (FCode.equals("BackUp3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUp3 = FValue.trim();
			}
			else
				BackUp3 = null;
		}
		if (FCode.equals("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
		}
		if (FCode.equals("DealMsg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealMsg = FValue.trim();
			}
			else
				DealMsg = null;
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LNContStatusUpdateSchema other = (LNContStatusUpdateSchema)otherObject;
		return
			Type.equals(other.getType())
			&& BankCode.equals(other.getBankCode())
			&& ZoneNo.equals(other.getZoneNo())
			&& InsuComCode.equals(other.getInsuComCode())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& AgentCode.equals(other.getAgentCode())
			&& Operator.equals(other.getOperator())
			&& PolicyAcceDate.equals(other.getPolicyAcceDate())
			&& TransDate.equals(other.getTransDate())
			&& StateChangeDate.equals(other.getStateChangeDate())
			&& RiskCode.equals(other.getRiskCode())
			&& ProposalNo.equals(other.getProposalNo())
			&& PolNo.equals(other.getPolNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntIDType.equals(other.getAppntIDType())
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuName.equals(other.getInsuName())
			&& InsuIDType.equals(other.getInsuIDType())
			&& InsuIDNo.equals(other.getInsuIDNo())
			&& allPrem.equals(other.getallPrem())
			&& TransAmt.equals(other.getTransAmt())
			&& IsHesi.equals(other.getIsHesi())
			&& InsuEndDate.equals(other.getInsuEndDate())
			&& NewState.equals(other.getNewState())
			&& ApplyName.equals(other.getApplyName())
			&& PayTimes.equals(other.getPayTimes())
			&& NextPayDate.equals(other.getNextPayDate())
			&& NextPayAmt.equals(other.getNextPayAmt())
			&& PayDate.equals(other.getPayDate())
			&& IncreaCashValue.equals(other.getIncreaCashValue())
			&& FinalCashValue.equals(other.getFinalCashValue())
			&& SurrMarks.equals(other.getSurrMarks())
			&& SurrTransType.equals(other.getSurrTransType())
			&& SignDate.equals(other.getSignDate())
			&& SurrDate.equals(other.getSurrDate())
			&& DealDate.equals(other.getDealDate())
			&& SurrAllAmt.equals(other.getSurrAllAmt())
			&& MainRiskSurrMult.equals(other.getMainRiskSurrMult())
			&& MainRiskSurrAmt.equals(other.getMainRiskSurrAmt())
			&& AddRiskNum.equals(other.getAddRiskNum())
			&& AddRiskCode1.equals(other.getAddRiskCode1())
			&& AddRiskSurrMult1.equals(other.getAddRiskSurrMult1())
			&& AddRiskSurrAmt1.equals(other.getAddRiskSurrAmt1())
			&& AddRiskCode2.equals(other.getAddRiskCode2())
			&& AddRiskSurrMult2.equals(other.getAddRiskSurrMult2())
			&& AddRiskSurrAmt2.equals(other.getAddRiskSurrAmt2())
			&& AddRiskCode3.equals(other.getAddRiskCode3())
			&& AddRiskSurrMult3.equals(other.getAddRiskSurrMult3())
			&& AddRiskSurrAmt3.equals(other.getAddRiskSurrAmt3())
			&& AddRiskCode4.equals(other.getAddRiskCode4())
			&& AddRiskSurrMult4.equals(other.getAddRiskSurrMult4())
			&& AddRiskSurrAmt4.equals(other.getAddRiskSurrAmt4())
			&& BackUp1.equals(other.getBackUp1())
			&& BackUp2.equals(other.getBackUp2())
			&& BackUp3.equals(other.getBackUp3())
			&& Flag.equals(other.getFlag())
			&& DealMsg.equals(other.getDealMsg())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("Type") ) {
			return 0;
		}
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuComCode") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("ComCode") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("PolicyAcceDate") ) {
			return 8;
		}
		if( strFieldName.equals("TransDate") ) {
			return 9;
		}
		if( strFieldName.equals("StateChangeDate") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 12;
		}
		if( strFieldName.equals("PolNo") ) {
			return 13;
		}
		if( strFieldName.equals("AppntName") ) {
			return 14;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return 15;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 16;
		}
		if( strFieldName.equals("InsuName") ) {
			return 17;
		}
		if( strFieldName.equals("InsuIDType") ) {
			return 18;
		}
		if( strFieldName.equals("InsuIDNo") ) {
			return 19;
		}
		if( strFieldName.equals("allPrem") ) {
			return 20;
		}
		if( strFieldName.equals("TransAmt") ) {
			return 21;
		}
		if( strFieldName.equals("IsHesi") ) {
			return 22;
		}
		if( strFieldName.equals("InsuEndDate") ) {
			return 23;
		}
		if( strFieldName.equals("NewState") ) {
			return 24;
		}
		if( strFieldName.equals("ApplyName") ) {
			return 25;
		}
		if( strFieldName.equals("PayTimes") ) {
			return 26;
		}
		if( strFieldName.equals("NextPayDate") ) {
			return 27;
		}
		if( strFieldName.equals("NextPayAmt") ) {
			return 28;
		}
		if( strFieldName.equals("PayDate") ) {
			return 29;
		}
		if( strFieldName.equals("IncreaCashValue") ) {
			return 30;
		}
		if( strFieldName.equals("FinalCashValue") ) {
			return 31;
		}
		if( strFieldName.equals("SurrMarks") ) {
			return 32;
		}
		if( strFieldName.equals("SurrTransType") ) {
			return 33;
		}
		if( strFieldName.equals("SignDate") ) {
			return 34;
		}
		if( strFieldName.equals("SurrDate") ) {
			return 35;
		}
		if( strFieldName.equals("DealDate") ) {
			return 36;
		}
		if( strFieldName.equals("SurrAllAmt") ) {
			return 37;
		}
		if( strFieldName.equals("MainRiskSurrMult") ) {
			return 38;
		}
		if( strFieldName.equals("MainRiskSurrAmt") ) {
			return 39;
		}
		if( strFieldName.equals("AddRiskNum") ) {
			return 40;
		}
		if( strFieldName.equals("AddRiskCode1") ) {
			return 41;
		}
		if( strFieldName.equals("AddRiskSurrMult1") ) {
			return 42;
		}
		if( strFieldName.equals("AddRiskSurrAmt1") ) {
			return 43;
		}
		if( strFieldName.equals("AddRiskCode2") ) {
			return 44;
		}
		if( strFieldName.equals("AddRiskSurrMult2") ) {
			return 45;
		}
		if( strFieldName.equals("AddRiskSurrAmt2") ) {
			return 46;
		}
		if( strFieldName.equals("AddRiskCode3") ) {
			return 47;
		}
		if( strFieldName.equals("AddRiskSurrMult3") ) {
			return 48;
		}
		if( strFieldName.equals("AddRiskSurrAmt3") ) {
			return 49;
		}
		if( strFieldName.equals("AddRiskCode4") ) {
			return 50;
		}
		if( strFieldName.equals("AddRiskSurrMult4") ) {
			return 51;
		}
		if( strFieldName.equals("AddRiskSurrAmt4") ) {
			return 52;
		}
		if( strFieldName.equals("BackUp1") ) {
			return 53;
		}
		if( strFieldName.equals("BackUp2") ) {
			return 54;
		}
		if( strFieldName.equals("BackUp3") ) {
			return 55;
		}
		if( strFieldName.equals("Flag") ) {
			return 56;
		}
		if( strFieldName.equals("DealMsg") ) {
			return 57;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 58;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 59;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 60;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 61;
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
				strFieldName = "Type";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "InsuComCode";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "ComCode";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "PolicyAcceDate";
				break;
			case 9:
				strFieldName = "TransDate";
				break;
			case 10:
				strFieldName = "StateChangeDate";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "ProposalNo";
				break;
			case 13:
				strFieldName = "PolNo";
				break;
			case 14:
				strFieldName = "AppntName";
				break;
			case 15:
				strFieldName = "AppntIDType";
				break;
			case 16:
				strFieldName = "AppntIDNo";
				break;
			case 17:
				strFieldName = "InsuName";
				break;
			case 18:
				strFieldName = "InsuIDType";
				break;
			case 19:
				strFieldName = "InsuIDNo";
				break;
			case 20:
				strFieldName = "allPrem";
				break;
			case 21:
				strFieldName = "TransAmt";
				break;
			case 22:
				strFieldName = "IsHesi";
				break;
			case 23:
				strFieldName = "InsuEndDate";
				break;
			case 24:
				strFieldName = "NewState";
				break;
			case 25:
				strFieldName = "ApplyName";
				break;
			case 26:
				strFieldName = "PayTimes";
				break;
			case 27:
				strFieldName = "NextPayDate";
				break;
			case 28:
				strFieldName = "NextPayAmt";
				break;
			case 29:
				strFieldName = "PayDate";
				break;
			case 30:
				strFieldName = "IncreaCashValue";
				break;
			case 31:
				strFieldName = "FinalCashValue";
				break;
			case 32:
				strFieldName = "SurrMarks";
				break;
			case 33:
				strFieldName = "SurrTransType";
				break;
			case 34:
				strFieldName = "SignDate";
				break;
			case 35:
				strFieldName = "SurrDate";
				break;
			case 36:
				strFieldName = "DealDate";
				break;
			case 37:
				strFieldName = "SurrAllAmt";
				break;
			case 38:
				strFieldName = "MainRiskSurrMult";
				break;
			case 39:
				strFieldName = "MainRiskSurrAmt";
				break;
			case 40:
				strFieldName = "AddRiskNum";
				break;
			case 41:
				strFieldName = "AddRiskCode1";
				break;
			case 42:
				strFieldName = "AddRiskSurrMult1";
				break;
			case 43:
				strFieldName = "AddRiskSurrAmt1";
				break;
			case 44:
				strFieldName = "AddRiskCode2";
				break;
			case 45:
				strFieldName = "AddRiskSurrMult2";
				break;
			case 46:
				strFieldName = "AddRiskSurrAmt2";
				break;
			case 47:
				strFieldName = "AddRiskCode3";
				break;
			case 48:
				strFieldName = "AddRiskSurrMult3";
				break;
			case 49:
				strFieldName = "AddRiskSurrAmt3";
				break;
			case 50:
				strFieldName = "AddRiskCode4";
				break;
			case 51:
				strFieldName = "AddRiskSurrMult4";
				break;
			case 52:
				strFieldName = "AddRiskSurrAmt4";
				break;
			case 53:
				strFieldName = "BackUp1";
				break;
			case 54:
				strFieldName = "BackUp2";
				break;
			case 55:
				strFieldName = "BackUp3";
				break;
			case 56:
				strFieldName = "Flag";
				break;
			case 57:
				strFieldName = "DealMsg";
				break;
			case 58:
				strFieldName = "MakeDate";
				break;
			case 59:
				strFieldName = "MakeTime";
				break;
			case 60:
				strFieldName = "ModifyDate";
				break;
			case 61:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyAcceDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateChangeDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("allPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransAmt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsHesi") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuEndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayTimes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NextPayDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NextPayAmt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncreaCashValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinalCashValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrMarks") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrTransType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrAllAmt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskSurrMult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskSurrAmt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskNum") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrMult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrAmt1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrMult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrAmt2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskCode3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrMult3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrAmt3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskCode4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrMult4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRiskSurrAmt4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackUp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackUp2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackUp3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealMsg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
