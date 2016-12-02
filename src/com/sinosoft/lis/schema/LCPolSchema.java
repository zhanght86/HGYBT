/*
 * <p>ClassName: LCPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LCPolDB;

public class LCPolSchema implements Schema
{
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 投保单号 */
	private String ProposalContNo;
	/** 投保单险种号码 */
	private String ProposalNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 总单类型 */
	private String ContType;
	/** 保单类型标记 */
	private String PolTypeFlag;
	/** 主险保单号码 */
	private String MainPolNo;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种别名 */
	private String RiskAlias;
	/** 险种版本 */
	private String RiskVersion;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 销售渠道 */
	private String SaleChnl;
	/** 经办人 */
	private String Handler;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人生日 */
	private Date InsuredBirthday;
	/** 被保人投保年龄 */
	private int InsuredAppAge;
	/** 被保人数目 */
	private int InsuredPeoples;
	/** 被保人证件类型 */
	private String InsuredIDType;
	/** 被保人证件号码 */
	private String InsuredID;
	/** 被保人职业类别/工种编码 */
	private String OccupationType;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 险种生效日期 */
	private Date CValiDate;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PaytoDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 保险责任终止日期 */
	private Date EndDate;
	/** 意外责任终止日期 */
	private Date AcciEndDate;
	/** 主被保人保单号码 */
	private String MasterPolNo;
	/** 领取年龄年期标志 */
	private String GetYearFlag;
	/** 领取年龄年期 */
	private int GetYear;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 终交年龄年期 */
	private int PayEndYear;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private int InsuYear;
	/** 意外年龄年期标志 */
	private String AcciYearFlag;
	/** 意外年龄年期 */
	private int AcciYear;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 是否指定生效日期 */
	private String SpecifyValiDate;
	/** 交费方式 */
	private String PayMode;
	/** 交费位置 */
	private String PayLocation;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费年期 */
	private int PayYears;
	/** 保险年期 */
	private int Years;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 浮动费率 */
	private double FloatRate;
	/** 保费算保额标志 */
	private String PremToAmnt;
	/** 总份数 */
	private double Mult;
	/** 总标准保费 */
	private double StandPrem;
	/** 首期追加趸交保费 */
	private double FirstAddPrem;
	/** 总保费 */
	private double Prem;
	/** 总累计保费 */
	private double SumPrem;
	/** 录入的保费 */
	private double InPrem;
	/** 录入的保额 */
	private double InAmnt;
	/** 险种额外保费 */
	private double AddPrem;
	/** 险种额外保额 */
	private double AddAmnt;
	/** 总基本保额 */
	private double Amnt;
	/** 总风险保额 */
	private double RiskAmnt;
	/** 余额 */
	private double LeavingMoney;
	/** 批改次数 */
	private int EndorseTimes;
	/** 理赔次数 */
	private int ClaimTimes;
	/** 生存领取次数 */
	private int LiveTimes;
	/** 续保次数 */
	private int RenewCount;
	/** 最后一次给付日期 */
	private Date LastGetDate;
	/** 最后一次借款日期 */
	private Date LastLoanDate;
	/** 最后一次催收日期 */
	private Date LastRegetDate;
	/** 最后一次保全日期 */
	private Date LastEdorDate;
	/** 最近复效日期 */
	private Date LastRevDate;
	/** 续保标志 */
	private int RnewFlag;
	/** 停交标志 */
	private String StopFlag;
	/** 满期标志 */
	private String ExpiryFlag;
	/** 自动垫交标志 */
	private String AutoPayFlag;
	/** 利差返还方式 */
	private String InterestDifFlag;
	/** 减额交清标志 */
	private String SubFlag;
	/** 受益人标记 */
	private String BnfFlag;
	/** 黑名单标志 */
	private String HighRiskFlag;
	/** 是否体检件标志 */
	private String HealthCheckFlag;
	/** 投资日期标志 */
	private String InvestDateFlag;
	/** 告知标志 */
	private String ImpartFlag;
	/** 商业分保标记 */
	private String ReinsureFlag;
	/** 代收标志 */
	private String AgentPayFlag;
	/** 代付标志 */
	private String AgentGetFlag;
	/** 生存金领取方式 */
	private String LiveGetMode;
	/** 身故金领取方式 */
	private String DeadGetMode;
	/** 红利金领取方式 */
	private String BonusGetMode;
	/** 红利金领取人 */
	private String BonusMan;
	/** 被保人、被保人死亡标志 */
	private String DeadFlag;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 备注 */
	private String Remark;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 其它保单状态 */
	private String PolState;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 等待期 */
	private int WaitPeriod;
	/** 领取形式 */
	private String GetForm;
	/** 领取银行编码 */
	private String GetBankCode;
	/** 领取银行账户 */
	private String GetBankAccNo;
	/** 领取银行户名 */
	private String GetAccName;
	/** 不丧失价值选择 */
	private String KeepValueOpt;
	/** 领取起始年龄 */
	private int PayoutStart;
	/** 累计保费 */
	private double RiskPrem;
	/** 领取终止年龄 */
	private int PayoutEnd;

	public static final int FIELDNUM = 132;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPolSchema()
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

	/** 集体合同号码<P> */
	public String getGrpContNo()
	{
		if (GrpContNo != null && !GrpContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			GrpContNo = StrTool.unicodeToGBK(GrpContNo);
		}
		return GrpContNo;
	}
	/** 集体合同号码 */
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	/** 集体保单险种号码<P> */
	public String getGrpPolNo()
	{
		if (GrpPolNo != null && !GrpPolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			GrpPolNo = StrTool.unicodeToGBK(GrpPolNo);
		}
		return GrpPolNo;
	}
	/** 集体保单险种号码 */
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	/** 合同号码<P>合同号码 */
	public String getContNo()
	{
		if (ContNo != null && !ContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContNo = StrTool.unicodeToGBK(ContNo);
		}
		return ContNo;
	}
	/** 合同号码 */
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/** 保单险种号码<P>保单险种号码 */
	public String getPolNo()
	{
		if (PolNo != null && !PolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolNo = StrTool.unicodeToGBK(PolNo);
		}
		return PolNo;
	}
	/** 保单险种号码 */
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/** 投保单号<P>投保单号 */
	public String getProposalContNo()
	{
		if (ProposalContNo != null && !ProposalContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalContNo = StrTool.unicodeToGBK(ProposalContNo);
		}
		return ProposalContNo;
	}
	/** 投保单号 */
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/** 投保单险种号码<P>投保单险种号码 */
	public String getProposalNo()
	{
		if (ProposalNo != null && !ProposalNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalNo = StrTool.unicodeToGBK(ProposalNo);
		}
		return ProposalNo;
	}
	/** 投保单险种号码 */
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	/** 印刷号码<P>印刷号码 */
	public String getPrtNo()
	{
		if (PrtNo != null && !PrtNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PrtNo = StrTool.unicodeToGBK(PrtNo);
		}
		return PrtNo;
	}
	/** 印刷号码 */
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	/** 总单类型<P>1-个人总投保单 */
	public String getContType()
	{
		if (ContType != null && !ContType.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContType = StrTool.unicodeToGBK(ContType);
		}
		return ContType;
	}
	/** 总单类型 */
	public void setContType(String aContType)
	{
		ContType = aContType;
	}
	/** 保单类型标记<P>0 --个人单<br><br> */
	public String getPolTypeFlag()
	{
		if (PolTypeFlag != null && !PolTypeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolTypeFlag = StrTool.unicodeToGBK(PolTypeFlag);
		}
		return PolTypeFlag;
	}
	/** 保单类型标记 */
	public void setPolTypeFlag(String aPolTypeFlag)
	{
		PolTypeFlag = aPolTypeFlag;
	}
	/** 主险保单号码<P>主险保单号码 */
	public String getMainPolNo()
	{
		if (MainPolNo != null && !MainPolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainPolNo = StrTool.unicodeToGBK(MainPolNo);
		}
		return MainPolNo;
	}
	/** 主险保单号码 */
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	/** 险类编码<P>险类编码 */
	public String getKindCode()
	{
		if (KindCode != null && !KindCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			KindCode = StrTool.unicodeToGBK(KindCode);
		}
		return KindCode;
	}
	/** 险类编码 */
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	/** 险种编码<P>险种编码 */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 险种编码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 险种别名<P>险种别名 */
	public String getRiskAlias()
	{
		if (RiskAlias != null && !RiskAlias.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskAlias = StrTool.unicodeToGBK(RiskAlias);
		}
		return RiskAlias;
	}
	/** 险种别名 */
	public void setRiskAlias(String aRiskAlias)
	{
		RiskAlias = aRiskAlias;
	}
	/** 险种版本<P>险种版本 */
	public String getRiskVersion()
	{
		if (RiskVersion != null && !RiskVersion.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskVersion = StrTool.unicodeToGBK(RiskVersion);
		}
		return RiskVersion;
	}
	/** 险种版本 */
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	/** 管理机构<P>管理机构 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 管理机构 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** 代理机构<P>代理机构 */
	public String getAgentCom()
	{
		if (AgentCom != null && !AgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCom = StrTool.unicodeToGBK(AgentCom);
		}
		return AgentCom;
	}
	/** 代理机构 */
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/** 代理机构内部分类<P>代理机构内部分类 */
	public String getAgentType()
	{
		if (AgentType != null && !AgentType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentType = StrTool.unicodeToGBK(AgentType);
		}
		return AgentType;
	}
	/** 代理机构内部分类 */
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	/** 代理人编码<P>代理人编码 */
	public String getAgentCode()
	{
		if (AgentCode != null && !AgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode = StrTool.unicodeToGBK(AgentCode);
		}
		return AgentCode;
	}
	/** 代理人编码 */
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/** 代理人组别<P>代理人组别 */
	public String getAgentGroup()
	{
		if (AgentGroup != null && !AgentGroup.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentGroup = StrTool.unicodeToGBK(AgentGroup);
		}
		return AgentGroup;
	}
	/** 代理人组别 */
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	/** 联合代理人代码<P>联合代理人代码 */
	public String getAgentCode1()
	{
		if (AgentCode1 != null && !AgentCode1.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode1 = StrTool.unicodeToGBK(AgentCode1);
		}
		return AgentCode1;
	}
	/** 联合代理人代码 */
	public void setAgentCode1(String aAgentCode1)
	{
		AgentCode1 = aAgentCode1;
	}
	/** 销售渠道<P>3-银行代理 */
	public String getSaleChnl()
	{
		if (SaleChnl != null && !SaleChnl.equals("") && SysConst.CHANGECHARSET == true)
		{
			SaleChnl = StrTool.unicodeToGBK(SaleChnl);
		}
		return SaleChnl;
	}
	/** 销售渠道 */
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	/** 经办人<P>经办人 */
	public String getHandler()
	{
		if (Handler != null && !Handler.equals("") && SysConst.CHANGECHARSET == true)
		{
			Handler = StrTool.unicodeToGBK(Handler);
		}
		return Handler;
	}
	/** 经办人 */
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	/** 被保人客户号码<P>被保人客户号码 */
	public String getInsuredNo()
	{
		if (InsuredNo != null && !InsuredNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredNo = StrTool.unicodeToGBK(InsuredNo);
		}
		return InsuredNo;
	}
	/** 被保人客户号码 */
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/** 被保人名称<P>被保人名称 */
	public String getInsuredName()
	{
		if (InsuredName != null && !InsuredName.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredName = StrTool.unicodeToGBK(InsuredName);
		}
		return InsuredName;
	}
	/** 被保人名称 */
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	/** 被保人性别<P>被保人性别 */
	public String getInsuredSex()
	{
		if (InsuredSex != null && !InsuredSex.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredSex = StrTool.unicodeToGBK(InsuredSex);
		}
		return InsuredSex;
	}
	/** 被保人性别 */
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
	/** 被保人生日<P>被保人生日 */
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	/** 被保人生日 */
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	/** 被保人生日<P>被保人生日 */
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	/** 被保人投保年龄<P>被保人投保年龄 */
	public int getInsuredAppAge()
	{
		return InsuredAppAge;
	}
	/** 被保人投保年龄 */
	public void setInsuredAppAge(int aInsuredAppAge)
	{
		InsuredAppAge = aInsuredAppAge;
	}
	/** 被保人投保年龄<P>被保人投保年龄 */
	public void setInsuredAppAge(String aInsuredAppAge)
	{
		if (aInsuredAppAge != null && !aInsuredAppAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAppAge);
			int i = tInteger.intValue();
			InsuredAppAge = i;
		}
	}

	/** 被保人数目<P>被保人数目 */
	public int getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	/** 被保人数目 */
	public void setInsuredPeoples(int aInsuredPeoples)
	{
		InsuredPeoples = aInsuredPeoples;
	}
	/** 被保人数目<P>被保人数目 */
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		if (aInsuredPeoples != null && !aInsuredPeoples.equals(""))
		{
			Integer tInteger = new Integer(aInsuredPeoples);
			int i = tInteger.intValue();
			InsuredPeoples = i;
		}
	}

	/** 被保人证件类型<P>被保人证件类型 */
	public String getInsuredIDType()
	{
		if (InsuredIDType != null && !InsuredIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDType = StrTool.unicodeToGBK(InsuredIDType);
		}
		return InsuredIDType;
	}
	/** 被保人证件类型 */
	public void setInsuredIDType(String aInsuredIDType)
	{
		InsuredIDType = aInsuredIDType;
	}
	/** 被保人证件号码<P>被保人证件号码 */
	public String getInsuredID()
	{
		if (InsuredID != null && !InsuredID.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredID = StrTool.unicodeToGBK(InsuredID);
		}
		return InsuredID;
	}
	/** 被保人证件号码 */
	public void setInsuredID(String aInsuredID)
	{
		InsuredID = aInsuredID;
	}
	/** 被保人职业类别/工种编码<P>被保人职业类别/工种编码 */
	public String getOccupationType()
	{
		if (OccupationType != null && !OccupationType.equals("") && SysConst.CHANGECHARSET == true)
		{
			OccupationType = StrTool.unicodeToGBK(OccupationType);
		}
		return OccupationType;
	}
	/** 被保人职业类别/工种编码 */
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	/** 投保人客户号码<P>投保人客户号码 */
	public String getAppntNo()
	{
		if (AppntNo != null && !AppntNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntNo = StrTool.unicodeToGBK(AppntNo);
		}
		return AppntNo;
	}
	/** 投保人客户号码 */
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	/** 投保人名称<P>投保人名称 */
	public String getAppntName()
	{
		if (AppntName != null && !AppntName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntName = StrTool.unicodeToGBK(AppntName);
		}
		return AppntName;
	}
	/** 投保人名称 */
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	/** 险种生效日期<P>险种生效日期 */
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	/** 险种生效日期 */
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	/** 险种生效日期<P>险种生效日期 */
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	/** 签单机构<P>签单机构 */
	public String getSignCom()
	{
		if (SignCom != null && !SignCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			SignCom = StrTool.unicodeToGBK(SignCom);
		}
		return SignCom;
	}
	/** 签单机构 */
	public void setSignCom(String aSignCom)
	{
		SignCom = aSignCom;
	}
	/** 签单日期<P>签单日期 */
	public String getSignDate()
	{
		if( SignDate != null )
			return fDate.getString(SignDate);
		else
			return null;
	}
	/** 签单日期 */
	public void setSignDate(Date aSignDate)
	{
		SignDate = aSignDate;
	}
	/** 签单日期<P>签单日期 */
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals("") )
		{
			SignDate = fDate.getDate( aSignDate );
		}
		else
			SignDate = null;
	}

	/** 签单时间<P>签单时间 */
	public String getSignTime()
	{
		if (SignTime != null && !SignTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			SignTime = StrTool.unicodeToGBK(SignTime);
		}
		return SignTime;
	}
	/** 签单时间 */
	public void setSignTime(String aSignTime)
	{
		SignTime = aSignTime;
	}
	/** 首期交费日期<P>首期交费日期 */
	public String getFirstPayDate()
	{
		if( FirstPayDate != null )
			return fDate.getString(FirstPayDate);
		else
			return null;
	}
	/** 首期交费日期 */
	public void setFirstPayDate(Date aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	/** 首期交费日期<P>首期交费日期 */
	public void setFirstPayDate(String aFirstPayDate)
	{
		if (aFirstPayDate != null && !aFirstPayDate.equals("") )
		{
			FirstPayDate = fDate.getDate( aFirstPayDate );
		}
		else
			FirstPayDate = null;
	}

	/** 终交日期<P>终交日期 */
	public String getPayEndDate()
	{
		if( PayEndDate != null )
			return fDate.getString(PayEndDate);
		else
			return null;
	}
	/** 终交日期 */
	public void setPayEndDate(Date aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	/** 终交日期<P>终交日期 */
	public void setPayEndDate(String aPayEndDate)
	{
		if (aPayEndDate != null && !aPayEndDate.equals("") )
		{
			PayEndDate = fDate.getDate( aPayEndDate );
		}
		else
			PayEndDate = null;
	}

	/** 交至日期<P>交至日期 */
	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	/** 交至日期 */
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	/** 交至日期<P>交至日期 */
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	/** 起领日期<P>起领日期 */
	public String getGetStartDate()
	{
		if( GetStartDate != null )
			return fDate.getString(GetStartDate);
		else
			return null;
	}
	/** 起领日期 */
	public void setGetStartDate(Date aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	/** 起领日期<P>起领日期 */
	public void setGetStartDate(String aGetStartDate)
	{
		if (aGetStartDate != null && !aGetStartDate.equals("") )
		{
			GetStartDate = fDate.getDate( aGetStartDate );
		}
		else
			GetStartDate = null;
	}

	/** 保险责任终止日期<P>保险责任终止日期 */
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	/** 保险责任终止日期 */
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	/** 保险责任终止日期<P>保险责任终止日期 */
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/** 意外责任终止日期<P>意外责任终止日期 */
	public String getAcciEndDate()
	{
		if( AcciEndDate != null )
			return fDate.getString(AcciEndDate);
		else
			return null;
	}
	/** 意外责任终止日期 */
	public void setAcciEndDate(Date aAcciEndDate)
	{
		AcciEndDate = aAcciEndDate;
	}
	/** 意外责任终止日期<P>意外责任终止日期 */
	public void setAcciEndDate(String aAcciEndDate)
	{
		if (aAcciEndDate != null && !aAcciEndDate.equals("") )
		{
			AcciEndDate = fDate.getDate( aAcciEndDate );
		}
		else
			AcciEndDate = null;
	}

	/** 主被保人保单号码<P>主被保人保单号码 */
	public String getMasterPolNo()
	{
		if (MasterPolNo != null && !MasterPolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			MasterPolNo = StrTool.unicodeToGBK(MasterPolNo);
		}
		return MasterPolNo;
	}
	/** 主被保人保单号码 */
	public void setMasterPolNo(String aMasterPolNo)
	{
		MasterPolNo = aMasterPolNo;
	}
	/** 领取年龄年期标志<P>领取年龄年期标志 */
	public String getGetYearFlag()
	{
		if (GetYearFlag != null && !GetYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetYearFlag = StrTool.unicodeToGBK(GetYearFlag);
		}
		return GetYearFlag;
	}
	/** 领取年龄年期标志 */
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	/** 领取年龄年期<P>领取年龄年期 */
	public int getGetYear()
	{
		return GetYear;
	}
	/** 领取年龄年期 */
	public void setGetYear(int aGetYear)
	{
		GetYear = aGetYear;
	}
	/** 领取年龄年期<P>领取年龄年期 */
	public void setGetYear(String aGetYear)
	{
		if (aGetYear != null && !aGetYear.equals(""))
		{
			Integer tInteger = new Integer(aGetYear);
			int i = tInteger.intValue();
			GetYear = i;
		}
	}

	/** 终交年龄年期标志<P>终交年龄年期标志 */
	public String getPayEndYearFlag()
	{
		if (PayEndYearFlag != null && !PayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYearFlag = StrTool.unicodeToGBK(PayEndYearFlag);
		}
		return PayEndYearFlag;
	}
	/** 终交年龄年期标志 */
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	/** 终交年龄年期<P>终交年龄年期 */
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	/** 终交年龄年期 */
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** 终交年龄年期<P>终交年龄年期 */
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/** 保险年龄年期标志<P>保险年龄年期标志 */
	public String getInsuYearFlag()
	{
		if (InsuYearFlag != null && !InsuYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuYearFlag = StrTool.unicodeToGBK(InsuYearFlag);
		}
		return InsuYearFlag;
	}
	/** 保险年龄年期标志 */
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	/** 保险年龄年期<P>保险年龄年期 */
	public int getInsuYear()
	{
		return InsuYear;
	}
	/** 保险年龄年期 */
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	/** 保险年龄年期<P>保险年龄年期 */
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	/** 意外年龄年期标志<P>意外年龄年期标志 */
	public String getAcciYearFlag()
	{
		if (AcciYearFlag != null && !AcciYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AcciYearFlag = StrTool.unicodeToGBK(AcciYearFlag);
		}
		return AcciYearFlag;
	}
	/** 意外年龄年期标志 */
	public void setAcciYearFlag(String aAcciYearFlag)
	{
		AcciYearFlag = aAcciYearFlag;
	}
	/** 意外年龄年期<P>意外年龄年期 */
	public int getAcciYear()
	{
		return AcciYear;
	}
	/** 意外年龄年期 */
	public void setAcciYear(int aAcciYear)
	{
		AcciYear = aAcciYear;
	}
	/** 意外年龄年期<P>意外年龄年期 */
	public void setAcciYear(String aAcciYear)
	{
		if (aAcciYear != null && !aAcciYear.equals(""))
		{
			Integer tInteger = new Integer(aAcciYear);
			int i = tInteger.intValue();
			AcciYear = i;
		}
	}

	/** 起领日期计算类型<P>起领日期计算类型 */
	public String getGetStartType()
	{
		if (GetStartType != null && !GetStartType.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetStartType = StrTool.unicodeToGBK(GetStartType);
		}
		return GetStartType;
	}
	/** 起领日期计算类型 */
	public void setGetStartType(String aGetStartType)
	{
		GetStartType = aGetStartType;
	}
	/** 是否指定生效日期<P>是否指定生效日期 */
	public String getSpecifyValiDate()
	{
		if (SpecifyValiDate != null && !SpecifyValiDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			SpecifyValiDate = StrTool.unicodeToGBK(SpecifyValiDate);
		}
		return SpecifyValiDate;
	}
	/** 是否指定生效日期 */
	public void setSpecifyValiDate(String aSpecifyValiDate)
	{
		SpecifyValiDate = aSpecifyValiDate;
	}
	/** 交费方式<P>交费方式 */
	public String getPayMode()
	{
		if (PayMode != null && !PayMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayMode = StrTool.unicodeToGBK(PayMode);
		}
		return PayMode;
	}
	/** 交费方式 */
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	/** 交费位置<P>交费位置 */
	public String getPayLocation()
	{
		if (PayLocation != null && !PayLocation.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayLocation = StrTool.unicodeToGBK(PayLocation);
		}
		return PayLocation;
	}
	/** 交费位置 */
	public void setPayLocation(String aPayLocation)
	{
		PayLocation = aPayLocation;
	}
	/** 交费间隔<P>交费位置 */
	public int getPayIntv()
	{
		return PayIntv;
	}
	/** 交费间隔 */
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/** 交费间隔<P>交费位置 */
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/** 交费年期<P>交费年期 */
	public int getPayYears()
	{
		return PayYears;
	}
	/** 交费年期 */
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	/** 交费年期<P>交费年期 */
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	/** 保险年期<P>保险年期 */
	public int getYears()
	{
		return Years;
	}
	/** 保险年期 */
	public void setYears(int aYears)
	{
		Years = aYears;
	}
	/** 保险年期<P>保险年期 */
	public void setYears(String aYears)
	{
		if (aYears != null && !aYears.equals(""))
		{
			Integer tInteger = new Integer(aYears);
			int i = tInteger.intValue();
			Years = i;
		}
	}

	/** 管理费比例<P>管理费比例 */
	public double getManageFeeRate()
	{
		return ManageFeeRate;
	}
	/** 管理费比例 */
	public void setManageFeeRate(double aManageFeeRate)
	{
		ManageFeeRate = aManageFeeRate;
	}
	/** 管理费比例<P>管理费比例 */
	public void setManageFeeRate(String aManageFeeRate)
	{
		if (aManageFeeRate != null && !aManageFeeRate.equals(""))
		{
			Double tDouble = new Double(aManageFeeRate);
			double d = tDouble.doubleValue();
			ManageFeeRate = d;
		}
	}

	/** 浮动费率<P>浮动费率 */
	public double getFloatRate()
	{
		return FloatRate;
	}
	/** 浮动费率 */
	public void setFloatRate(double aFloatRate)
	{
		FloatRate = aFloatRate;
	}
	/** 浮动费率<P>浮动费率 */
	public void setFloatRate(String aFloatRate)
	{
		if (aFloatRate != null && !aFloatRate.equals(""))
		{
			Double tDouble = new Double(aFloatRate);
			double d = tDouble.doubleValue();
			FloatRate = d;
		}
	}

	/** 保费算保额标志<P>保费算保额标志 */
	public String getPremToAmnt()
	{
		if (PremToAmnt != null && !PremToAmnt.equals("") && SysConst.CHANGECHARSET == true)
		{
			PremToAmnt = StrTool.unicodeToGBK(PremToAmnt);
		}
		return PremToAmnt;
	}
	/** 保费算保额标志 */
	public void setPremToAmnt(String aPremToAmnt)
	{
		PremToAmnt = aPremToAmnt;
	}
	/** 总份数<P>总份数 */
	public double getMult()
	{
		return Mult;
	}
	/** 总份数 */
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	/** 总份数<P>总份数 */
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	/** 总标准保费<P>总标准保费 */
	public double getStandPrem()
	{
		return StandPrem;
	}
	/** 总标准保费 */
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	/** 总标准保费<P>总标准保费 */
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	/** 首期追加趸交保费<P>首期追加趸交保费 */
	public double getFirstAddPrem()
	{
		return FirstAddPrem;
	}
	/** 首期追加趸交保费 */
	public void setFirstAddPrem(double aFirstAddPrem)
	{
		FirstAddPrem = aFirstAddPrem;
	}
	/** 首期追加趸交保费<P>首期追加趸交保费 */
	public void setFirstAddPrem(String aFirstAddPrem)
	{
		if (aFirstAddPrem != null && !aFirstAddPrem.equals(""))
		{
			Double tDouble = new Double(aFirstAddPrem);
			double d = tDouble.doubleValue();
			FirstAddPrem = d;
		}
	}

	/** 总保费<P>总保费 */
	public double getPrem()
	{
		return Prem;
	}
	/** 总保费 */
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	/** 总保费<P>总保费 */
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	/** 总累计保费<P>总累计保费 */
	public double getSumPrem()
	{
		return SumPrem;
	}
	/** 总累计保费 */
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	/** 总累计保费<P>总累计保费 */
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	/** 录入的保费<P>录入的保费 */
	public double getInPrem()
	{
		return InPrem;
	}
	/** 录入的保费 */
	public void setInPrem(double aInPrem)
	{
		InPrem = aInPrem;
	}
	/** 录入的保费<P>录入的保费 */
	public void setInPrem(String aInPrem)
	{
		if (aInPrem != null && !aInPrem.equals(""))
		{
			Double tDouble = new Double(aInPrem);
			double d = tDouble.doubleValue();
			InPrem = d;
		}
	}

	/** 录入的保额<P>录入的保额 */
	public double getInAmnt()
	{
		return InAmnt;
	}
	/** 录入的保额 */
	public void setInAmnt(double aInAmnt)
	{
		InAmnt = aInAmnt;
	}
	/** 录入的保额<P>录入的保额 */
	public void setInAmnt(String aInAmnt)
	{
		if (aInAmnt != null && !aInAmnt.equals(""))
		{
			Double tDouble = new Double(aInAmnt);
			double d = tDouble.doubleValue();
			InAmnt = d;
		}
	}

	/** 险种额外保费<P> */
	public double getAddPrem()
	{
		return AddPrem;
	}
	/** 险种额外保费 */
	public void setAddPrem(double aAddPrem)
	{
		AddPrem = aAddPrem;
	}
	/** 险种额外保费<P> */
	public void setAddPrem(String aAddPrem)
	{
		if (aAddPrem != null && !aAddPrem.equals(""))
		{
			Double tDouble = new Double(aAddPrem);
			double d = tDouble.doubleValue();
			AddPrem = d;
		}
	}

	/** 险种额外保额<P> */
	public double getAddAmnt()
	{
		return AddAmnt;
	}
	/** 险种额外保额 */
	public void setAddAmnt(double aAddAmnt)
	{
		AddAmnt = aAddAmnt;
	}
	/** 险种额外保额<P> */
	public void setAddAmnt(String aAddAmnt)
	{
		if (aAddAmnt != null && !aAddAmnt.equals(""))
		{
			Double tDouble = new Double(aAddAmnt);
			double d = tDouble.doubleValue();
			AddAmnt = d;
		}
	}

	/** 总基本保额<P>总基本保额 */
	public double getAmnt()
	{
		return Amnt;
	}
	/** 总基本保额 */
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	/** 总基本保额<P>总基本保额 */
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	/** 总风险保额<P>总风险保额 */
	public double getRiskAmnt()
	{
		return RiskAmnt;
	}
	/** 总风险保额 */
	public void setRiskAmnt(double aRiskAmnt)
	{
		RiskAmnt = aRiskAmnt;
	}
	/** 总风险保额<P>总风险保额 */
	public void setRiskAmnt(String aRiskAmnt)
	{
		if (aRiskAmnt != null && !aRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aRiskAmnt);
			double d = tDouble.doubleValue();
			RiskAmnt = d;
		}
	}

	/** 余额<P>余额 */
	public double getLeavingMoney()
	{
		return LeavingMoney;
	}
	/** 余额 */
	public void setLeavingMoney(double aLeavingMoney)
	{
		LeavingMoney = aLeavingMoney;
	}
	/** 余额<P>余额 */
	public void setLeavingMoney(String aLeavingMoney)
	{
		if (aLeavingMoney != null && !aLeavingMoney.equals(""))
		{
			Double tDouble = new Double(aLeavingMoney);
			double d = tDouble.doubleValue();
			LeavingMoney = d;
		}
	}

	/** 批改次数<P>批改次数 */
	public int getEndorseTimes()
	{
		return EndorseTimes;
	}
	/** 批改次数 */
	public void setEndorseTimes(int aEndorseTimes)
	{
		EndorseTimes = aEndorseTimes;
	}
	/** 批改次数<P>批改次数 */
	public void setEndorseTimes(String aEndorseTimes)
	{
		if (aEndorseTimes != null && !aEndorseTimes.equals(""))
		{
			Integer tInteger = new Integer(aEndorseTimes);
			int i = tInteger.intValue();
			EndorseTimes = i;
		}
	}

	/** 理赔次数<P>理赔次数 */
	public int getClaimTimes()
	{
		return ClaimTimes;
	}
	/** 理赔次数 */
	public void setClaimTimes(int aClaimTimes)
	{
		ClaimTimes = aClaimTimes;
	}
	/** 理赔次数<P>理赔次数 */
	public void setClaimTimes(String aClaimTimes)
	{
		if (aClaimTimes != null && !aClaimTimes.equals(""))
		{
			Integer tInteger = new Integer(aClaimTimes);
			int i = tInteger.intValue();
			ClaimTimes = i;
		}
	}

	/** 生存领取次数<P>生存领取次数 */
	public int getLiveTimes()
	{
		return LiveTimes;
	}
	/** 生存领取次数 */
	public void setLiveTimes(int aLiveTimes)
	{
		LiveTimes = aLiveTimes;
	}
	/** 生存领取次数<P>生存领取次数 */
	public void setLiveTimes(String aLiveTimes)
	{
		if (aLiveTimes != null && !aLiveTimes.equals(""))
		{
			Integer tInteger = new Integer(aLiveTimes);
			int i = tInteger.intValue();
			LiveTimes = i;
		}
	}

	/** 续保次数<P>续保次数 */
	public int getRenewCount()
	{
		return RenewCount;
	}
	/** 续保次数 */
	public void setRenewCount(int aRenewCount)
	{
		RenewCount = aRenewCount;
	}
	/** 续保次数<P>续保次数 */
	public void setRenewCount(String aRenewCount)
	{
		if (aRenewCount != null && !aRenewCount.equals(""))
		{
			Integer tInteger = new Integer(aRenewCount);
			int i = tInteger.intValue();
			RenewCount = i;
		}
	}

	/** 最后一次给付日期<P>最后一次给付日期 */
	public String getLastGetDate()
	{
		if( LastGetDate != null )
			return fDate.getString(LastGetDate);
		else
			return null;
	}
	/** 最后一次给付日期 */
	public void setLastGetDate(Date aLastGetDate)
	{
		LastGetDate = aLastGetDate;
	}
	/** 最后一次给付日期<P>最后一次给付日期 */
	public void setLastGetDate(String aLastGetDate)
	{
		if (aLastGetDate != null && !aLastGetDate.equals("") )
		{
			LastGetDate = fDate.getDate( aLastGetDate );
		}
		else
			LastGetDate = null;
	}

	/** 最后一次借款日期<P>最后一次借款日期 */
	public String getLastLoanDate()
	{
		if( LastLoanDate != null )
			return fDate.getString(LastLoanDate);
		else
			return null;
	}
	/** 最后一次借款日期 */
	public void setLastLoanDate(Date aLastLoanDate)
	{
		LastLoanDate = aLastLoanDate;
	}
	/** 最后一次借款日期<P>最后一次借款日期 */
	public void setLastLoanDate(String aLastLoanDate)
	{
		if (aLastLoanDate != null && !aLastLoanDate.equals("") )
		{
			LastLoanDate = fDate.getDate( aLastLoanDate );
		}
		else
			LastLoanDate = null;
	}

	/** 最后一次催收日期<P>最后一次催收日期 */
	public String getLastRegetDate()
	{
		if( LastRegetDate != null )
			return fDate.getString(LastRegetDate);
		else
			return null;
	}
	/** 最后一次催收日期 */
	public void setLastRegetDate(Date aLastRegetDate)
	{
		LastRegetDate = aLastRegetDate;
	}
	/** 最后一次催收日期<P>最后一次催收日期 */
	public void setLastRegetDate(String aLastRegetDate)
	{
		if (aLastRegetDate != null && !aLastRegetDate.equals("") )
		{
			LastRegetDate = fDate.getDate( aLastRegetDate );
		}
		else
			LastRegetDate = null;
	}

	/** 最后一次保全日期<P>最后一次保全日期 */
	public String getLastEdorDate()
	{
		if( LastEdorDate != null )
			return fDate.getString(LastEdorDate);
		else
			return null;
	}
	/** 最后一次保全日期 */
	public void setLastEdorDate(Date aLastEdorDate)
	{
		LastEdorDate = aLastEdorDate;
	}
	/** 最后一次保全日期<P>最后一次保全日期 */
	public void setLastEdorDate(String aLastEdorDate)
	{
		if (aLastEdorDate != null && !aLastEdorDate.equals("") )
		{
			LastEdorDate = fDate.getDate( aLastEdorDate );
		}
		else
			LastEdorDate = null;
	}

	/** 最近复效日期<P>最近复效日期 */
	public String getLastRevDate()
	{
		if( LastRevDate != null )
			return fDate.getString(LastRevDate);
		else
			return null;
	}
	/** 最近复效日期 */
	public void setLastRevDate(Date aLastRevDate)
	{
		LastRevDate = aLastRevDate;
	}
	/** 最近复效日期<P>最近复效日期 */
	public void setLastRevDate(String aLastRevDate)
	{
		if (aLastRevDate != null && !aLastRevDate.equals("") )
		{
			LastRevDate = fDate.getDate( aLastRevDate );
		}
		else
			LastRevDate = null;
	}

	/** 续保标志<P>续保标志 */
	public int getRnewFlag()
	{
		return RnewFlag;
	}
	/** 续保标志 */
	public void setRnewFlag(int aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	/** 续保标志<P>续保标志 */
	public void setRnewFlag(String aRnewFlag)
	{
		if (aRnewFlag != null && !aRnewFlag.equals(""))
		{
			Integer tInteger = new Integer(aRnewFlag);
			int i = tInteger.intValue();
			RnewFlag = i;
		}
	}

	/** 停交标志<P>停交标志 */
	public String getStopFlag()
	{
		if (StopFlag != null && !StopFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			StopFlag = StrTool.unicodeToGBK(StopFlag);
		}
		return StopFlag;
	}
	/** 停交标志 */
	public void setStopFlag(String aStopFlag)
	{
		StopFlag = aStopFlag;
	}
	/** 满期标志<P>满期标志 */
	public String getExpiryFlag()
	{
		if (ExpiryFlag != null && !ExpiryFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ExpiryFlag = StrTool.unicodeToGBK(ExpiryFlag);
		}
		return ExpiryFlag;
	}
	/** 满期标志 */
	public void setExpiryFlag(String aExpiryFlag)
	{
		ExpiryFlag = aExpiryFlag;
	}
	/** 自动垫交标志<P>自动垫交标志 */
	public String getAutoPayFlag()
	{
		if (AutoPayFlag != null && !AutoPayFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AutoPayFlag = StrTool.unicodeToGBK(AutoPayFlag);
		}
		return AutoPayFlag;
	}
	/** 自动垫交标志 */
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		AutoPayFlag = aAutoPayFlag;
	}
	/** 利差返还方式<P>利差返还方式 */
	public String getInterestDifFlag()
	{
		if (InterestDifFlag != null && !InterestDifFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			InterestDifFlag = StrTool.unicodeToGBK(InterestDifFlag);
		}
		return InterestDifFlag;
	}
	/** 利差返还方式 */
	public void setInterestDifFlag(String aInterestDifFlag)
	{
		InterestDifFlag = aInterestDifFlag;
	}
	/** 减额交清标志<P>减额交清标志 */
	public String getSubFlag()
	{
		if (SubFlag != null && !SubFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubFlag = StrTool.unicodeToGBK(SubFlag);
		}
		return SubFlag;
	}
	/** 减额交清标志 */
	public void setSubFlag(String aSubFlag)
	{
		SubFlag = aSubFlag;
	}
	/** 受益人标记<P>受益人标记 */
	public String getBnfFlag()
	{
		if (BnfFlag != null && !BnfFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			BnfFlag = StrTool.unicodeToGBK(BnfFlag);
		}
		return BnfFlag;
	}
	/** 受益人标记 */
	public void setBnfFlag(String aBnfFlag)
	{
		BnfFlag = aBnfFlag;
	}
	/** 黑名单标志<P>黑名单标志 */
	public String getHighRiskFlag()
	{
		if (HighRiskFlag != null && !HighRiskFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			HighRiskFlag = StrTool.unicodeToGBK(HighRiskFlag);
		}
		return HighRiskFlag;
	}
	/** 黑名单标志 */
	public void setHighRiskFlag(String aHighRiskFlag)
	{
		HighRiskFlag = aHighRiskFlag;
	}
	/** 是否体检件标志<P>是否体检件标志 */
	public String getHealthCheckFlag()
	{
		if (HealthCheckFlag != null && !HealthCheckFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			HealthCheckFlag = StrTool.unicodeToGBK(HealthCheckFlag);
		}
		return HealthCheckFlag;
	}
	/** 是否体检件标志 */
	public void setHealthCheckFlag(String aHealthCheckFlag)
	{
		HealthCheckFlag = aHealthCheckFlag;
	}
	/** 投资日期标志<P>投资日期标志 */
	public String getInvestDateFlag()
	{
		if (InvestDateFlag != null && !InvestDateFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			InvestDateFlag = StrTool.unicodeToGBK(InvestDateFlag);
		}
		return InvestDateFlag;
	}
	/** 投资日期标志 */
	public void setInvestDateFlag(String aInvestDateFlag)
	{
		InvestDateFlag = aInvestDateFlag;
	}
	/** 告知标志<P>告知标志 */
	public String getImpartFlag()
	{
		if (ImpartFlag != null && !ImpartFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ImpartFlag = StrTool.unicodeToGBK(ImpartFlag);
		}
		return ImpartFlag;
	}
	/** 告知标志 */
	public void setImpartFlag(String aImpartFlag)
	{
		ImpartFlag = aImpartFlag;
	}
	/** 商业分保标记<P>商业分保标记 */
	public String getReinsureFlag()
	{
		if (ReinsureFlag != null && !ReinsureFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ReinsureFlag = StrTool.unicodeToGBK(ReinsureFlag);
		}
		return ReinsureFlag;
	}
	/** 商业分保标记 */
	public void setReinsureFlag(String aReinsureFlag)
	{
		ReinsureFlag = aReinsureFlag;
	}
	/** 代收标志<P>代收标志 */
	public String getAgentPayFlag()
	{
		if (AgentPayFlag != null && !AgentPayFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentPayFlag = StrTool.unicodeToGBK(AgentPayFlag);
		}
		return AgentPayFlag;
	}
	/** 代收标志 */
	public void setAgentPayFlag(String aAgentPayFlag)
	{
		AgentPayFlag = aAgentPayFlag;
	}
	/** 代付标志<P>代付标志 */
	public String getAgentGetFlag()
	{
		if (AgentGetFlag != null && !AgentGetFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentGetFlag = StrTool.unicodeToGBK(AgentGetFlag);
		}
		return AgentGetFlag;
	}
	/** 代付标志 */
	public void setAgentGetFlag(String aAgentGetFlag)
	{
		AgentGetFlag = aAgentGetFlag;
	}
	/** 生存金领取方式<P>生存金领取方式 */
	public String getLiveGetMode()
	{
		if (LiveGetMode != null && !LiveGetMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			LiveGetMode = StrTool.unicodeToGBK(LiveGetMode);
		}
		return LiveGetMode;
	}
	/** 生存金领取方式 */
	public void setLiveGetMode(String aLiveGetMode)
	{
		LiveGetMode = aLiveGetMode;
	}
	/** 身故金领取方式<P> */
	public String getDeadGetMode()
	{
		if (DeadGetMode != null && !DeadGetMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			DeadGetMode = StrTool.unicodeToGBK(DeadGetMode);
		}
		return DeadGetMode;
	}
	/** 身故金领取方式 */
	public void setDeadGetMode(String aDeadGetMode)
	{
		DeadGetMode = aDeadGetMode;
	}
	/** 红利金领取方式<P>红利金领取方式 */
	public String getBonusGetMode()
	{
		if (BonusGetMode != null && !BonusGetMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BonusGetMode = StrTool.unicodeToGBK(BonusGetMode);
		}
		return BonusGetMode;
	}
	/** 红利金领取方式 */
	public void setBonusGetMode(String aBonusGetMode)
	{
		BonusGetMode = aBonusGetMode;
	}
	/** 红利金领取人<P>红利金领取人 */
	public String getBonusMan()
	{
		if (BonusMan != null && !BonusMan.equals("") && SysConst.CHANGECHARSET == true)
		{
			BonusMan = StrTool.unicodeToGBK(BonusMan);
		}
		return BonusMan;
	}
	/** 红利金领取人 */
	public void setBonusMan(String aBonusMan)
	{
		BonusMan = aBonusMan;
	}
	/** 被保人、被保人死亡标志<P>被保人、被保人死亡标志 */
	public String getDeadFlag()
	{
		if (DeadFlag != null && !DeadFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			DeadFlag = StrTool.unicodeToGBK(DeadFlag);
		}
		return DeadFlag;
	}
	/** 被保人、被保人死亡标志 */
	public void setDeadFlag(String aDeadFlag)
	{
		DeadFlag = aDeadFlag;
	}
	/** 是否吸烟标志<P>是否吸烟标志 */
	public String getSmokeFlag()
	{
		if (SmokeFlag != null && !SmokeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SmokeFlag = StrTool.unicodeToGBK(SmokeFlag);
		}
		return SmokeFlag;
	}
	/** 是否吸烟标志 */
	public void setSmokeFlag(String aSmokeFlag)
	{
		SmokeFlag = aSmokeFlag;
	}
	/** 备注<P>备注 */
	public String getRemark()
	{
		if (Remark != null && !Remark.equals("") && SysConst.CHANGECHARSET == true)
		{
			Remark = StrTool.unicodeToGBK(Remark);
		}
		return Remark;
	}
	/** 备注 */
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/** 复核状态<P>复核状态 */
	public String getApproveFlag()
	{
		if (ApproveFlag != null && !ApproveFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApproveFlag = StrTool.unicodeToGBK(ApproveFlag);
		}
		return ApproveFlag;
	}
	/** 复核状态 */
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	/** 复核人编码<P>复核人编码 */
	public String getApproveCode()
	{
		if (ApproveCode != null && !ApproveCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApproveCode = StrTool.unicodeToGBK(ApproveCode);
		}
		return ApproveCode;
	}
	/** 复核人编码 */
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
	}
	/** 复核日期<P>复核日期 */
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	/** 复核日期 */
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	/** 复核日期<P>复核日期 */
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	/** 复核时间<P>复核时间 */
	public String getApproveTime()
	{
		if (ApproveTime != null && !ApproveTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApproveTime = StrTool.unicodeToGBK(ApproveTime);
		}
		return ApproveTime;
	}
	/** 复核时间 */
	public void setApproveTime(String aApproveTime)
	{
		ApproveTime = aApproveTime;
	}
	/** 核保状态<P>核保状态 */
	public String getUWFlag()
	{
		if (UWFlag != null && !UWFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWFlag = StrTool.unicodeToGBK(UWFlag);
		}
		return UWFlag;
	}
	/** 核保状态 */
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	/** 最终核保人编码<P>最终核保人编码 */
	public String getUWCode()
	{
		if (UWCode != null && !UWCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWCode = StrTool.unicodeToGBK(UWCode);
		}
		return UWCode;
	}
	/** 最终核保人编码 */
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
	}
	/** 核保完成日期<P>核保完成日期 */
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	/** 核保完成日期 */
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	/** 核保完成日期<P>核保完成日期 */
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	/** 核保完成时间<P>核保完成时间 */
	public String getUWTime()
	{
		if (UWTime != null && !UWTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWTime = StrTool.unicodeToGBK(UWTime);
		}
		return UWTime;
	}
	/** 核保完成时间 */
	public void setUWTime(String aUWTime)
	{
		UWTime = aUWTime;
	}
	/** 投保单申请日期<P>投保单申请日期 */
	public String getPolApplyDate()
	{
		if( PolApplyDate != null )
			return fDate.getString(PolApplyDate);
		else
			return null;
	}
	/** 投保单申请日期 */
	public void setPolApplyDate(Date aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
	}
	/** 投保单申请日期<P>投保单申请日期 */
	public void setPolApplyDate(String aPolApplyDate)
	{
		if (aPolApplyDate != null && !aPolApplyDate.equals("") )
		{
			PolApplyDate = fDate.getDate( aPolApplyDate );
		}
		else
			PolApplyDate = null;
	}

	/** 投保单/保单标志<P>投保单/保单标志 */
	public String getAppFlag()
	{
		if (AppFlag != null && !AppFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppFlag = StrTool.unicodeToGBK(AppFlag);
		}
		return AppFlag;
	}
	/** 投保单/保单标志 */
	public void setAppFlag(String aAppFlag)
	{
		AppFlag = aAppFlag;
	}
	/** 其它保单状态<P>其它保单状态 */
	public String getPolState()
	{
		if (PolState != null && !PolState.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolState = StrTool.unicodeToGBK(PolState);
		}
		return PolState;
	}
	/** 其它保单状态 */
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	/** 备用属性字段1<P>备用属性字段1 */
	public String getStandbyFlag1()
	{
		if (StandbyFlag1 != null && !StandbyFlag1.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag1 = StrTool.unicodeToGBK(StandbyFlag1);
		}
		return StandbyFlag1;
	}
	/** 备用属性字段1 */
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/** 备用属性字段2<P>备用属性字段2 */
	public String getStandbyFlag2()
	{
		if (StandbyFlag2 != null && !StandbyFlag2.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag2 = StrTool.unicodeToGBK(StandbyFlag2);
		}
		return StandbyFlag2;
	}
	/** 备用属性字段2 */
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	/** 备用属性字段3<P>备用属性字段3 */
	public String getStandbyFlag3()
	{
		if (StandbyFlag3 != null && !StandbyFlag3.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag3 = StrTool.unicodeToGBK(StandbyFlag3);
		}
		return StandbyFlag3;
	}
	/** 备用属性字段3 */
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	/** 操作员<P>操作员 */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 操作员 */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/** 入机日期<P>入机日期 */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 入机日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入机日期<P>入机日期 */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 入机时间<P>入机时间 */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 入机时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 最后一次修改日期<P>最后一次修改日期 */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 最后一次修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后一次修改日期<P>最后一次修改日期 */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后一次修改时间<P>最后一次修改时间 */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 最后一次修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 等待期<P>等待期 */
	public int getWaitPeriod()
	{
		return WaitPeriod;
	}
	/** 等待期 */
	public void setWaitPeriod(int aWaitPeriod)
	{
		WaitPeriod = aWaitPeriod;
	}
	/** 等待期<P>等待期 */
	public void setWaitPeriod(String aWaitPeriod)
	{
		if (aWaitPeriod != null && !aWaitPeriod.equals(""))
		{
			Integer tInteger = new Integer(aWaitPeriod);
			int i = tInteger.intValue();
			WaitPeriod = i;
		}
	}

	/** 领取形式<P>领取形式 */
	public String getGetForm()
	{
		if (GetForm != null && !GetForm.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetForm = StrTool.unicodeToGBK(GetForm);
		}
		return GetForm;
	}
	/** 领取形式 */
	public void setGetForm(String aGetForm)
	{
		GetForm = aGetForm;
	}
	/** 领取银行编码<P>领取银行编码 */
	public String getGetBankCode()
	{
		if (GetBankCode != null && !GetBankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetBankCode = StrTool.unicodeToGBK(GetBankCode);
		}
		return GetBankCode;
	}
	/** 领取银行编码 */
	public void setGetBankCode(String aGetBankCode)
	{
		GetBankCode = aGetBankCode;
	}
	/** 领取银行账户<P>领取银行账户 */
	public String getGetBankAccNo()
	{
		if (GetBankAccNo != null && !GetBankAccNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetBankAccNo = StrTool.unicodeToGBK(GetBankAccNo);
		}
		return GetBankAccNo;
	}
	/** 领取银行账户 */
	public void setGetBankAccNo(String aGetBankAccNo)
	{
		GetBankAccNo = aGetBankAccNo;
	}
	/** 领取银行户名<P>领取银行户名 */
	public String getGetAccName()
	{
		if (GetAccName != null && !GetAccName.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetAccName = StrTool.unicodeToGBK(GetAccName);
		}
		return GetAccName;
	}
	/** 领取银行户名 */
	public void setGetAccName(String aGetAccName)
	{
		GetAccName = aGetAccName;
	}
	/** 不丧失价值选择<P>不丧失价值选择 */
	public String getKeepValueOpt()
	{
		if (KeepValueOpt != null && !KeepValueOpt.equals("") && SysConst.CHANGECHARSET == true)
		{
			KeepValueOpt = StrTool.unicodeToGBK(KeepValueOpt);
		}
		return KeepValueOpt;
	}
	/** 不丧失价值选择 */
	public void setKeepValueOpt(String aKeepValueOpt)
	{
		KeepValueOpt = aKeepValueOpt;
	}
	/** 领取起始年龄<P>领取起始年龄 */
	public int getPayoutStart()
	{
		return PayoutStart;
	}
	/** 领取起始年龄 */
	public void setPayoutStart(int aPayoutStart)
	{
		PayoutStart = aPayoutStart;
	}
	/** 领取起始年龄<P>领取起始年龄 */
	public void setPayoutStart(String aPayoutStart)
	{
		if (aPayoutStart != null && !aPayoutStart.equals(""))
		{
			Integer tInteger = new Integer(aPayoutStart);
			int i = tInteger.intValue();
			PayoutStart = i;
		}
	}

	/** 累计保费<P>累计保费 */
	public double getRiskPrem()
	{
		return RiskPrem;
	}
	/** 累计保费 */
	public void setRiskPrem(double aRiskPrem)
	{
		RiskPrem = aRiskPrem;
	}
	/** 累计保费<P>累计保费 */
	public void setRiskPrem(String aRiskPrem)
	{
		if (aRiskPrem != null && !aRiskPrem.equals(""))
		{
			Double tDouble = new Double(aRiskPrem);
			double d = tDouble.doubleValue();
			RiskPrem = d;
		}
	}

	/** 领取终止年龄<P>领取终止年龄 */
	public int getPayoutEnd()
	{
		return PayoutEnd;
	}
	/** 领取终止年龄 */
	public void setPayoutEnd(int aPayoutEnd)
	{
		PayoutEnd = aPayoutEnd;
	}
	/** 领取终止年龄<P>领取终止年龄 */
	public void setPayoutEnd(String aPayoutEnd)
	{
		if (aPayoutEnd != null && !aPayoutEnd.equals(""))
		{
			Integer tInteger = new Integer(aPayoutEnd);
			int i = tInteger.intValue();
			PayoutEnd = i;
		}
	}


	/**
	* 使用另外一个 LCPolSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LCPolSchema aLCPolSchema)
	{
		this.GrpContNo = aLCPolSchema.getGrpContNo();
		this.GrpPolNo = aLCPolSchema.getGrpPolNo();
		this.ContNo = aLCPolSchema.getContNo();
		this.PolNo = aLCPolSchema.getPolNo();
		this.ProposalContNo = aLCPolSchema.getProposalContNo();
		this.ProposalNo = aLCPolSchema.getProposalNo();
		this.PrtNo = aLCPolSchema.getPrtNo();
		this.ContType = aLCPolSchema.getContType();
		this.PolTypeFlag = aLCPolSchema.getPolTypeFlag();
		this.MainPolNo = aLCPolSchema.getMainPolNo();
		this.KindCode = aLCPolSchema.getKindCode();
		this.RiskCode = aLCPolSchema.getRiskCode();
		this.RiskAlias = aLCPolSchema.getRiskAlias();
		this.RiskVersion = aLCPolSchema.getRiskVersion();
		this.ManageCom = aLCPolSchema.getManageCom();
		this.AgentCom = aLCPolSchema.getAgentCom();
		this.AgentType = aLCPolSchema.getAgentType();
		this.AgentCode = aLCPolSchema.getAgentCode();
		this.AgentGroup = aLCPolSchema.getAgentGroup();
		this.AgentCode1 = aLCPolSchema.getAgentCode1();
		this.SaleChnl = aLCPolSchema.getSaleChnl();
		this.Handler = aLCPolSchema.getHandler();
		this.InsuredNo = aLCPolSchema.getInsuredNo();
		this.InsuredName = aLCPolSchema.getInsuredName();
		this.InsuredSex = aLCPolSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLCPolSchema.getInsuredBirthday());
		this.InsuredAppAge = aLCPolSchema.getInsuredAppAge();
		this.InsuredPeoples = aLCPolSchema.getInsuredPeoples();
		this.InsuredIDType = aLCPolSchema.getInsuredIDType();
		this.InsuredID = aLCPolSchema.getInsuredID();
		this.OccupationType = aLCPolSchema.getOccupationType();
		this.AppntNo = aLCPolSchema.getAppntNo();
		this.AppntName = aLCPolSchema.getAppntName();
		this.CValiDate = fDate.getDate( aLCPolSchema.getCValiDate());
		this.SignCom = aLCPolSchema.getSignCom();
		this.SignDate = fDate.getDate( aLCPolSchema.getSignDate());
		this.SignTime = aLCPolSchema.getSignTime();
		this.FirstPayDate = fDate.getDate( aLCPolSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLCPolSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLCPolSchema.getPaytoDate());
		this.GetStartDate = fDate.getDate( aLCPolSchema.getGetStartDate());
		this.EndDate = fDate.getDate( aLCPolSchema.getEndDate());
		this.AcciEndDate = fDate.getDate( aLCPolSchema.getAcciEndDate());
		this.MasterPolNo = aLCPolSchema.getMasterPolNo();
		this.GetYearFlag = aLCPolSchema.getGetYearFlag();
		this.GetYear = aLCPolSchema.getGetYear();
		this.PayEndYearFlag = aLCPolSchema.getPayEndYearFlag();
		this.PayEndYear = aLCPolSchema.getPayEndYear();
		this.InsuYearFlag = aLCPolSchema.getInsuYearFlag();
		this.InsuYear = aLCPolSchema.getInsuYear();
		this.AcciYearFlag = aLCPolSchema.getAcciYearFlag();
		this.AcciYear = aLCPolSchema.getAcciYear();
		this.GetStartType = aLCPolSchema.getGetStartType();
		this.SpecifyValiDate = aLCPolSchema.getSpecifyValiDate();
		this.PayMode = aLCPolSchema.getPayMode();
		this.PayLocation = aLCPolSchema.getPayLocation();
		this.PayIntv = aLCPolSchema.getPayIntv();
		this.PayYears = aLCPolSchema.getPayYears();
		this.Years = aLCPolSchema.getYears();
		this.ManageFeeRate = aLCPolSchema.getManageFeeRate();
		this.FloatRate = aLCPolSchema.getFloatRate();
		this.PremToAmnt = aLCPolSchema.getPremToAmnt();
		this.Mult = aLCPolSchema.getMult();
		this.StandPrem = aLCPolSchema.getStandPrem();
		this.FirstAddPrem = aLCPolSchema.getFirstAddPrem();
		this.Prem = aLCPolSchema.getPrem();
		this.SumPrem = aLCPolSchema.getSumPrem();
		this.InPrem = aLCPolSchema.getInPrem();
		this.InAmnt = aLCPolSchema.getInAmnt();
		this.AddPrem = aLCPolSchema.getAddPrem();
		this.AddAmnt = aLCPolSchema.getAddAmnt();
		this.Amnt = aLCPolSchema.getAmnt();
		this.RiskAmnt = aLCPolSchema.getRiskAmnt();
		this.LeavingMoney = aLCPolSchema.getLeavingMoney();
		this.EndorseTimes = aLCPolSchema.getEndorseTimes();
		this.ClaimTimes = aLCPolSchema.getClaimTimes();
		this.LiveTimes = aLCPolSchema.getLiveTimes();
		this.RenewCount = aLCPolSchema.getRenewCount();
		this.LastGetDate = fDate.getDate( aLCPolSchema.getLastGetDate());
		this.LastLoanDate = fDate.getDate( aLCPolSchema.getLastLoanDate());
		this.LastRegetDate = fDate.getDate( aLCPolSchema.getLastRegetDate());
		this.LastEdorDate = fDate.getDate( aLCPolSchema.getLastEdorDate());
		this.LastRevDate = fDate.getDate( aLCPolSchema.getLastRevDate());
		this.RnewFlag = aLCPolSchema.getRnewFlag();
		this.StopFlag = aLCPolSchema.getStopFlag();
		this.ExpiryFlag = aLCPolSchema.getExpiryFlag();
		this.AutoPayFlag = aLCPolSchema.getAutoPayFlag();
		this.InterestDifFlag = aLCPolSchema.getInterestDifFlag();
		this.SubFlag = aLCPolSchema.getSubFlag();
		this.BnfFlag = aLCPolSchema.getBnfFlag();
		this.HighRiskFlag = aLCPolSchema.getHighRiskFlag();
		this.HealthCheckFlag = aLCPolSchema.getHealthCheckFlag();
		this.InvestDateFlag = aLCPolSchema.getInvestDateFlag();
		this.ImpartFlag = aLCPolSchema.getImpartFlag();
		this.ReinsureFlag = aLCPolSchema.getReinsureFlag();
		this.AgentPayFlag = aLCPolSchema.getAgentPayFlag();
		this.AgentGetFlag = aLCPolSchema.getAgentGetFlag();
		this.LiveGetMode = aLCPolSchema.getLiveGetMode();
		this.DeadGetMode = aLCPolSchema.getDeadGetMode();
		this.BonusGetMode = aLCPolSchema.getBonusGetMode();
		this.BonusMan = aLCPolSchema.getBonusMan();
		this.DeadFlag = aLCPolSchema.getDeadFlag();
		this.SmokeFlag = aLCPolSchema.getSmokeFlag();
		this.Remark = aLCPolSchema.getRemark();
		this.ApproveFlag = aLCPolSchema.getApproveFlag();
		this.ApproveCode = aLCPolSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLCPolSchema.getApproveDate());
		this.ApproveTime = aLCPolSchema.getApproveTime();
		this.UWFlag = aLCPolSchema.getUWFlag();
		this.UWCode = aLCPolSchema.getUWCode();
		this.UWDate = fDate.getDate( aLCPolSchema.getUWDate());
		this.UWTime = aLCPolSchema.getUWTime();
		this.PolApplyDate = fDate.getDate( aLCPolSchema.getPolApplyDate());
		this.AppFlag = aLCPolSchema.getAppFlag();
		this.PolState = aLCPolSchema.getPolState();
		this.StandbyFlag1 = aLCPolSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCPolSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCPolSchema.getStandbyFlag3();
		this.Operator = aLCPolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCPolSchema.getMakeDate());
		this.MakeTime = aLCPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPolSchema.getModifyDate());
		this.ModifyTime = aLCPolSchema.getModifyTime();
		this.WaitPeriod = aLCPolSchema.getWaitPeriod();
		this.GetForm = aLCPolSchema.getGetForm();
		this.GetBankCode = aLCPolSchema.getGetBankCode();
		this.GetBankAccNo = aLCPolSchema.getGetBankAccNo();
		this.GetAccName = aLCPolSchema.getGetAccName();
		this.KeepValueOpt = aLCPolSchema.getKeepValueOpt();
		this.PayoutStart = aLCPolSchema.getPayoutStart();
		this.RiskPrem = aLCPolSchema.getRiskPrem();
		this.PayoutEnd = aLCPolSchema.getPayoutEnd();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("PolTypeFlag") == null )
				this.PolTypeFlag = null;
			else
				this.PolTypeFlag = rs.getString("PolTypeFlag").trim();

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskAlias") == null )
				this.RiskAlias = null;
			else
				this.RiskAlias = rs.getString("RiskAlias").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			this.InsuredAppAge = rs.getInt("InsuredAppAge");
			this.InsuredPeoples = rs.getInt("InsuredPeoples");
			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredID") == null )
				this.InsuredID = null;
			else
				this.InsuredID = rs.getString("InsuredID").trim();

			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			this.EndDate = rs.getDate("EndDate");
			this.AcciEndDate = rs.getDate("AcciEndDate");
			if( rs.getString("MasterPolNo") == null )
				this.MasterPolNo = null;
			else
				this.MasterPolNo = rs.getString("MasterPolNo").trim();

			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("AcciYearFlag") == null )
				this.AcciYearFlag = null;
			else
				this.AcciYearFlag = rs.getString("AcciYearFlag").trim();

			this.AcciYear = rs.getInt("AcciYear");
			if( rs.getString("GetStartType") == null )
				this.GetStartType = null;
			else
				this.GetStartType = rs.getString("GetStartType").trim();

			if( rs.getString("SpecifyValiDate") == null )
				this.SpecifyValiDate = null;
			else
				this.SpecifyValiDate = rs.getString("SpecifyValiDate").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayLocation") == null )
				this.PayLocation = null;
			else
				this.PayLocation = rs.getString("PayLocation").trim();

			this.PayIntv = rs.getInt("PayIntv");
			this.PayYears = rs.getInt("PayYears");
			this.Years = rs.getInt("Years");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			this.FloatRate = rs.getDouble("FloatRate");
			if( rs.getString("PremToAmnt") == null )
				this.PremToAmnt = null;
			else
				this.PremToAmnt = rs.getString("PremToAmnt").trim();

			this.Mult = rs.getDouble("Mult");
			this.StandPrem = rs.getDouble("StandPrem");
			this.FirstAddPrem = rs.getDouble("FirstAddPrem");
			this.Prem = rs.getDouble("Prem");
			this.SumPrem = rs.getDouble("SumPrem");
			this.InPrem = rs.getDouble("InPrem");
			this.InAmnt = rs.getDouble("InAmnt");
			this.AddPrem = rs.getDouble("AddPrem");
			this.AddAmnt = rs.getDouble("AddAmnt");
			this.Amnt = rs.getDouble("Amnt");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.LeavingMoney = rs.getDouble("LeavingMoney");
			this.EndorseTimes = rs.getInt("EndorseTimes");
			this.ClaimTimes = rs.getInt("ClaimTimes");
			this.LiveTimes = rs.getInt("LiveTimes");
			this.RenewCount = rs.getInt("RenewCount");
			this.LastGetDate = rs.getDate("LastGetDate");
			this.LastLoanDate = rs.getDate("LastLoanDate");
			this.LastRegetDate = rs.getDate("LastRegetDate");
			this.LastEdorDate = rs.getDate("LastEdorDate");
			this.LastRevDate = rs.getDate("LastRevDate");
			this.RnewFlag = rs.getInt("RnewFlag");
			if( rs.getString("StopFlag") == null )
				this.StopFlag = null;
			else
				this.StopFlag = rs.getString("StopFlag").trim();

			if( rs.getString("ExpiryFlag") == null )
				this.ExpiryFlag = null;
			else
				this.ExpiryFlag = rs.getString("ExpiryFlag").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			if( rs.getString("InterestDifFlag") == null )
				this.InterestDifFlag = null;
			else
				this.InterestDifFlag = rs.getString("InterestDifFlag").trim();

			if( rs.getString("SubFlag") == null )
				this.SubFlag = null;
			else
				this.SubFlag = rs.getString("SubFlag").trim();

			if( rs.getString("BnfFlag") == null )
				this.BnfFlag = null;
			else
				this.BnfFlag = rs.getString("BnfFlag").trim();

			if( rs.getString("HighRiskFlag") == null )
				this.HighRiskFlag = null;
			else
				this.HighRiskFlag = rs.getString("HighRiskFlag").trim();

			if( rs.getString("HealthCheckFlag") == null )
				this.HealthCheckFlag = null;
			else
				this.HealthCheckFlag = rs.getString("HealthCheckFlag").trim();

			if( rs.getString("InvestDateFlag") == null )
				this.InvestDateFlag = null;
			else
				this.InvestDateFlag = rs.getString("InvestDateFlag").trim();

			if( rs.getString("ImpartFlag") == null )
				this.ImpartFlag = null;
			else
				this.ImpartFlag = rs.getString("ImpartFlag").trim();

			if( rs.getString("ReinsureFlag") == null )
				this.ReinsureFlag = null;
			else
				this.ReinsureFlag = rs.getString("ReinsureFlag").trim();

			if( rs.getString("AgentPayFlag") == null )
				this.AgentPayFlag = null;
			else
				this.AgentPayFlag = rs.getString("AgentPayFlag").trim();

			if( rs.getString("AgentGetFlag") == null )
				this.AgentGetFlag = null;
			else
				this.AgentGetFlag = rs.getString("AgentGetFlag").trim();

			if( rs.getString("LiveGetMode") == null )
				this.LiveGetMode = null;
			else
				this.LiveGetMode = rs.getString("LiveGetMode").trim();

			if( rs.getString("DeadGetMode") == null )
				this.DeadGetMode = null;
			else
				this.DeadGetMode = rs.getString("DeadGetMode").trim();

			if( rs.getString("BonusGetMode") == null )
				this.BonusGetMode = null;
			else
				this.BonusGetMode = rs.getString("BonusGetMode").trim();

			if( rs.getString("BonusMan") == null )
				this.BonusMan = null;
			else
				this.BonusMan = rs.getString("BonusMan").trim();

			if( rs.getString("DeadFlag") == null )
				this.DeadFlag = null;
			else
				this.DeadFlag = rs.getString("DeadFlag").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			this.PolApplyDate = rs.getDate("PolApplyDate");
			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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

			this.WaitPeriod = rs.getInt("WaitPeriod");
			if( rs.getString("GetForm") == null )
				this.GetForm = null;
			else
				this.GetForm = rs.getString("GetForm").trim();

			if( rs.getString("GetBankCode") == null )
				this.GetBankCode = null;
			else
				this.GetBankCode = rs.getString("GetBankCode").trim();

			if( rs.getString("GetBankAccNo") == null )
				this.GetBankAccNo = null;
			else
				this.GetBankAccNo = rs.getString("GetBankAccNo").trim();

			if( rs.getString("GetAccName") == null )
				this.GetAccName = null;
			else
				this.GetAccName = rs.getString("GetAccName").trim();

			if( rs.getString("KeepValueOpt") == null )
				this.KeepValueOpt = null;
			else
				this.KeepValueOpt = rs.getString("KeepValueOpt").trim();

			this.PayoutStart = rs.getInt("PayoutStart");
			this.RiskPrem = rs.getDouble("RiskPrem");
			this.PayoutEnd = rs.getInt("PayoutEnd");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LCPolSchema getSchema()
	{
		LCPolSchema aLCPolSchema = new LCPolSchema();
		aLCPolSchema.setSchema(this);
		return aLCPolSchema;
	}

	public LCPolDB getDB()
	{
		LCPolDB aDBOper = new LCPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPol描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(GrpContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpPolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolTypeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainPolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(KindCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskAlias) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskVersion) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGroup) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SaleChnl) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Handler) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredSex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( InsuredBirthday )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredAppAge) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredPeoples) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OccupationType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( CValiDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SignCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( SignDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SignTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( FirstPayDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PayEndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PaytoDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( GetStartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( EndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AcciEndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MasterPolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(GetYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AcciYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AcciYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetStartType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SpecifyValiDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayLocation) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayIntv) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayYears) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Years) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ManageFeeRate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FloatRate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PremToAmnt) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Mult) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(StandPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FirstAddPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Prem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(SumPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InAmnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AddPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AddAmnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Amnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RiskAmnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LeavingMoney) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(EndorseTimes) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ClaimTimes) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LiveTimes) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RenewCount) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( LastGetDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( LastLoanDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( LastRegetDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( LastEdorDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( LastRevDate )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RnewFlag) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StopFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ExpiryFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AutoPayFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InterestDifFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BnfFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HighRiskFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HealthCheckFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InvestDateFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ImpartFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ReinsureFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentPayFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGetFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LiveGetMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DeadGetMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BonusGetMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BonusMan) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DeadFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SmokeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ApproveDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( UWDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PolApplyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolState) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WaitPeriod) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetForm) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetBankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetBankAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetAccName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(KeepValueOpt) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayoutStart) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RiskPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayoutEnd);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			InsuredAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			InsuredPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InsuredID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			AcciEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MasterPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).intValue();
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			AcciYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			SpecifyValiDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).intValue();
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).intValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			PremToAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
			FirstAddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).doubleValue();
			InPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).doubleValue();
			InAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).doubleValue();
			AddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).doubleValue();
			AddAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).doubleValue();
			LeavingMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,74,SysConst.PACKAGESPILTER))).doubleValue();
			EndorseTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).intValue();
			ClaimTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).intValue();
			LiveTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).intValue();
			RenewCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,78,SysConst.PACKAGESPILTER))).intValue();
			LastGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79,SysConst.PACKAGESPILTER));
			LastLoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80,SysConst.PACKAGESPILTER));
			LastRegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82,SysConst.PACKAGESPILTER));
			LastRevDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83,SysConst.PACKAGESPILTER));
			RnewFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,84,SysConst.PACKAGESPILTER))).intValue();
			StopFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			ExpiryFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			InterestDifFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			SubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			BnfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			HighRiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			HealthCheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			InvestDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			ImpartFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			ReinsureFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			AgentPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			AgentGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			LiveGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			DeadGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			BonusMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			DeadFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113,SysConst.PACKAGESPILTER));
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 116, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 118, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 122,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 123, SysConst.PACKAGESPILTER );
			WaitPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,124,SysConst.PACKAGESPILTER))).intValue();
			GetForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 125, SysConst.PACKAGESPILTER );
			GetBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 126, SysConst.PACKAGESPILTER );
			GetBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 127, SysConst.PACKAGESPILTER );
			GetAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 128, SysConst.PACKAGESPILTER );
			KeepValueOpt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 129, SysConst.PACKAGESPILTER );
			PayoutStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,130,SysConst.PACKAGESPILTER))).intValue();
			RiskPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,131,SysConst.PACKAGESPILTER))).doubleValue();
			PayoutEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,132,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolSchema";
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
		if (FCode.equals("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equals("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equals("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equals("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equals("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equals("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equals("PolTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolTypeFlag));
		}
		if (FCode.equals("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equals("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("RiskAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAlias));
		}
		if (FCode.equals("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equals("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
		}
		if (FCode.equals("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equals("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equals("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equals("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equals("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equals("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equals("InsuredAppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAppAge));
		}
		if (FCode.equals("InsuredPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredPeoples));
		}
		if (FCode.equals("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equals("InsuredID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredID));
		}
		if (FCode.equals("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equals("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equals("SignCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignCom));
		}
		if (FCode.equals("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equals("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equals("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equals("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equals("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equals("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equals("AcciEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
		}
		if (FCode.equals("MasterPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasterPolNo));
		}
		if (FCode.equals("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equals("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equals("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equals("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equals("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equals("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equals("AcciYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYearFlag));
		}
		if (FCode.equals("AcciYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYear));
		}
		if (FCode.equals("GetStartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartType));
		}
		if (FCode.equals("SpecifyValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecifyValiDate));
		}
		if (FCode.equals("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equals("PayLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLocation));
		}
		if (FCode.equals("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equals("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equals("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equals("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
		}
		if (FCode.equals("FloatRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FloatRate));
		}
		if (FCode.equals("PremToAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremToAmnt));
		}
		if (FCode.equals("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equals("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equals("FirstAddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAddPrem));
		}
		if (FCode.equals("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equals("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equals("InPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InPrem));
		}
		if (FCode.equals("InAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAmnt));
		}
		if (FCode.equals("AddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPrem));
		}
		if (FCode.equals("AddAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmnt));
		}
		if (FCode.equals("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equals("RiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmnt));
		}
		if (FCode.equals("LeavingMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeavingMoney));
		}
		if (FCode.equals("EndorseTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndorseTimes));
		}
		if (FCode.equals("ClaimTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimTimes));
		}
		if (FCode.equals("LiveTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveTimes));
		}
		if (FCode.equals("RenewCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewCount));
		}
		if (FCode.equals("LastGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
		}
		if (FCode.equals("LastLoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
		}
		if (FCode.equals("LastRegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastRegetDate()));
		}
		if (FCode.equals("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
		}
		if (FCode.equals("LastRevDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
		}
		if (FCode.equals("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equals("StopFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopFlag));
		}
		if (FCode.equals("ExpiryFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryFlag));
		}
		if (FCode.equals("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equals("InterestDifFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestDifFlag));
		}
		if (FCode.equals("SubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFlag));
		}
		if (FCode.equals("BnfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfFlag));
		}
		if (FCode.equals("HighRiskFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HighRiskFlag));
		}
		if (FCode.equals("HealthCheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthCheckFlag));
		}
		if (FCode.equals("InvestDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestDateFlag));
		}
		if (FCode.equals("ImpartFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartFlag));
		}
		if (FCode.equals("ReinsureFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureFlag));
		}
		if (FCode.equals("AgentPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPayFlag));
		}
		if (FCode.equals("AgentGetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetFlag));
		}
		if (FCode.equals("LiveGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetMode));
		}
		if (FCode.equals("DeadGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadGetMode));
		}
		if (FCode.equals("BonusGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGetMode));
		}
		if (FCode.equals("BonusMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMan));
		}
		if (FCode.equals("DeadFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadFlag));
		}
		if (FCode.equals("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equals("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equals("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equals("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equals("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equals("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equals("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equals("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equals("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equals("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
		}
		if (FCode.equals("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equals("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equals("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equals("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equals("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
		if (FCode.equals("WaitPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WaitPeriod));
		}
		if (FCode.equals("GetForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetForm));
		}
		if (FCode.equals("GetBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetBankCode));
		}
		if (FCode.equals("GetBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetBankAccNo));
		}
		if (FCode.equals("GetAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetAccName));
		}
		if (FCode.equals("KeepValueOpt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeepValueOpt));
		}
		if (FCode.equals("PayoutStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayoutStart));
		}
		if (FCode.equals("RiskPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPrem));
		}
		if (FCode.equals("PayoutEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayoutEnd));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolTypeFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskAlias);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 26:
				strFieldValue = String.valueOf(InsuredAppAge);
				break;
			case 27:
				strFieldValue = String.valueOf(InsuredPeoples);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsuredID);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MasterPolNo);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 45:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 47:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 49:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 51:
				strFieldValue = String.valueOf(AcciYear);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(SpecifyValiDate);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 56:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 57:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 58:
				strFieldValue = String.valueOf(Years);
				break;
			case 59:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 60:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(PremToAmnt);
				break;
			case 62:
				strFieldValue = String.valueOf(Mult);
				break;
			case 63:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 64:
				strFieldValue = String.valueOf(FirstAddPrem);
				break;
			case 65:
				strFieldValue = String.valueOf(Prem);
				break;
			case 66:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 67:
				strFieldValue = String.valueOf(InPrem);
				break;
			case 68:
				strFieldValue = String.valueOf(InAmnt);
				break;
			case 69:
				strFieldValue = String.valueOf(AddPrem);
				break;
			case 70:
				strFieldValue = String.valueOf(AddAmnt);
				break;
			case 71:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 72:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 73:
				strFieldValue = String.valueOf(LeavingMoney);
				break;
			case 74:
				strFieldValue = String.valueOf(EndorseTimes);
				break;
			case 75:
				strFieldValue = String.valueOf(ClaimTimes);
				break;
			case 76:
				strFieldValue = String.valueOf(LiveTimes);
				break;
			case 77:
				strFieldValue = String.valueOf(RenewCount);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastRegetDate()));
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
				break;
			case 83:
				strFieldValue = String.valueOf(RnewFlag);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(StopFlag);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(ExpiryFlag);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(InterestDifFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(SubFlag);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(BnfFlag);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(HighRiskFlag);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(HealthCheckFlag);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(InvestDateFlag);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(ImpartFlag);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(ReinsureFlag);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(AgentPayFlag);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(AgentGetFlag);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(LiveGetMode);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(DeadGetMode);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(BonusMan);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(DeadFlag);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 115:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 117:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 121:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 122:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 123:
				strFieldValue = String.valueOf(WaitPeriod);
				break;
			case 124:
				strFieldValue = StrTool.GBKToUnicode(GetForm);
				break;
			case 125:
				strFieldValue = StrTool.GBKToUnicode(GetBankCode);
				break;
			case 126:
				strFieldValue = StrTool.GBKToUnicode(GetBankAccNo);
				break;
			case 127:
				strFieldValue = StrTool.GBKToUnicode(GetAccName);
				break;
			case 128:
				strFieldValue = StrTool.GBKToUnicode(KeepValueOpt);
				break;
			case 129:
				strFieldValue = String.valueOf(PayoutStart);
				break;
			case 130:
				strFieldValue = String.valueOf(RiskPrem);
				break;
			case 131:
				strFieldValue = String.valueOf(PayoutEnd);
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

		if (FCode.equals("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equals("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equals("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equals("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equals("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equals("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equals("PolTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolTypeFlag = FValue.trim();
			}
			else
				PolTypeFlag = null;
		}
		if (FCode.equals("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
		}
		if (FCode.equals("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equals("RiskAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAlias = FValue.trim();
			}
			else
				RiskAlias = null;
		}
		if (FCode.equals("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
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
		if (FCode.equals("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
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
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equals("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equals("AgentCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode1 = FValue.trim();
			}
			else
				AgentCode1 = null;
		}
		if (FCode.equals("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equals("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equals("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equals("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equals("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
		}
		if (FCode.equals("InsuredAppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredAppAge = i;
			}
		}
		if (FCode.equals("InsuredPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredPeoples = i;
			}
		}
		if (FCode.equals("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
		}
		if (FCode.equals("InsuredID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredID = FValue.trim();
			}
			else
				InsuredID = null;
		}
		if (FCode.equals("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equals("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equals("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equals("SignCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignCom = FValue.trim();
			}
			else
				SignCom = null;
		}
		if (FCode.equals("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equals("SignTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignTime = FValue.trim();
			}
			else
				SignTime = null;
		}
		if (FCode.equals("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equals("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
		}
		if (FCode.equals("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
		}
		if (FCode.equals("GetStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetStartDate = fDate.getDate( FValue );
			}
			else
				GetStartDate = null;
		}
		if (FCode.equals("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equals("AcciEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcciEndDate = fDate.getDate( FValue );
			}
			else
				AcciEndDate = null;
		}
		if (FCode.equals("MasterPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MasterPolNo = FValue.trim();
			}
			else
				MasterPolNo = null;
		}
		if (FCode.equals("GetYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYearFlag = FValue.trim();
			}
			else
				GetYearFlag = null;
		}
		if (FCode.equals("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYear = i;
			}
		}
		if (FCode.equals("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equals("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
		}
		if (FCode.equals("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equals("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
		}
		if (FCode.equals("AcciYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcciYearFlag = FValue.trim();
			}
			else
				AcciYearFlag = null;
		}
		if (FCode.equals("AcciYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AcciYear = i;
			}
		}
		if (FCode.equals("GetStartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartType = FValue.trim();
			}
			else
				GetStartType = null;
		}
		if (FCode.equals("SpecifyValiDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecifyValiDate = FValue.trim();
			}
			else
				SpecifyValiDate = null;
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
		if (FCode.equals("PayLocation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayLocation = FValue.trim();
			}
			else
				PayLocation = null;
		}
		if (FCode.equals("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equals("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
		}
		if (FCode.equals("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equals("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ManageFeeRate = d;
			}
		}
		if (FCode.equals("FloatRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FloatRate = d;
			}
		}
		if (FCode.equals("PremToAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremToAmnt = FValue.trim();
			}
			else
				PremToAmnt = null;
		}
		if (FCode.equals("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equals("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equals("FirstAddPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FirstAddPrem = d;
			}
		}
		if (FCode.equals("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equals("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equals("InPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InPrem = d;
			}
		}
		if (FCode.equals("InAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InAmnt = d;
			}
		}
		if (FCode.equals("AddPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPrem = d;
			}
		}
		if (FCode.equals("AddAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddAmnt = d;
			}
		}
		if (FCode.equals("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equals("RiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskAmnt = d;
			}
		}
		if (FCode.equals("LeavingMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeavingMoney = d;
			}
		}
		if (FCode.equals("EndorseTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndorseTimes = i;
			}
		}
		if (FCode.equals("ClaimTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClaimTimes = i;
			}
		}
		if (FCode.equals("LiveTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LiveTimes = i;
			}
		}
		if (FCode.equals("RenewCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RenewCount = i;
			}
		}
		if (FCode.equals("LastGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastGetDate = fDate.getDate( FValue );
			}
			else
				LastGetDate = null;
		}
		if (FCode.equals("LastLoanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastLoanDate = fDate.getDate( FValue );
			}
			else
				LastLoanDate = null;
		}
		if (FCode.equals("LastRegetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastRegetDate = fDate.getDate( FValue );
			}
			else
				LastRegetDate = null;
		}
		if (FCode.equals("LastEdorDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastEdorDate = fDate.getDate( FValue );
			}
			else
				LastEdorDate = null;
		}
		if (FCode.equals("LastRevDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastRevDate = fDate.getDate( FValue );
			}
			else
				LastRevDate = null;
		}
		if (FCode.equals("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RnewFlag = i;
			}
		}
		if (FCode.equals("StopFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopFlag = FValue.trim();
			}
			else
				StopFlag = null;
		}
		if (FCode.equals("ExpiryFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryFlag = FValue.trim();
			}
			else
				ExpiryFlag = null;
		}
		if (FCode.equals("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
		}
		if (FCode.equals("InterestDifFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestDifFlag = FValue.trim();
			}
			else
				InterestDifFlag = null;
		}
		if (FCode.equals("SubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFlag = FValue.trim();
			}
			else
				SubFlag = null;
		}
		if (FCode.equals("BnfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfFlag = FValue.trim();
			}
			else
				BnfFlag = null;
		}
		if (FCode.equals("HighRiskFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HighRiskFlag = FValue.trim();
			}
			else
				HighRiskFlag = null;
		}
		if (FCode.equals("HealthCheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthCheckFlag = FValue.trim();
			}
			else
				HealthCheckFlag = null;
		}
		if (FCode.equals("InvestDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestDateFlag = FValue.trim();
			}
			else
				InvestDateFlag = null;
		}
		if (FCode.equals("ImpartFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartFlag = FValue.trim();
			}
			else
				ImpartFlag = null;
		}
		if (FCode.equals("ReinsureFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureFlag = FValue.trim();
			}
			else
				ReinsureFlag = null;
		}
		if (FCode.equals("AgentPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPayFlag = FValue.trim();
			}
			else
				AgentPayFlag = null;
		}
		if (FCode.equals("AgentGetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetFlag = FValue.trim();
			}
			else
				AgentGetFlag = null;
		}
		if (FCode.equals("LiveGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetMode = FValue.trim();
			}
			else
				LiveGetMode = null;
		}
		if (FCode.equals("DeadGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadGetMode = FValue.trim();
			}
			else
				DeadGetMode = null;
		}
		if (FCode.equals("BonusGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGetMode = FValue.trim();
			}
			else
				BonusGetMode = null;
		}
		if (FCode.equals("BonusMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusMan = FValue.trim();
			}
			else
				BonusMan = null;
		}
		if (FCode.equals("DeadFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadFlag = FValue.trim();
			}
			else
				DeadFlag = null;
		}
		if (FCode.equals("SmokeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokeFlag = FValue.trim();
			}
			else
				SmokeFlag = null;
		}
		if (FCode.equals("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equals("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equals("ApproveCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveCode = FValue.trim();
			}
			else
				ApproveCode = null;
		}
		if (FCode.equals("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equals("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
		}
		if (FCode.equals("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equals("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
		}
		if (FCode.equals("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equals("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
		}
		if (FCode.equals("PolApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PolApplyDate = fDate.getDate( FValue );
			}
			else
				PolApplyDate = null;
		}
		if (FCode.equals("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
		}
		if (FCode.equals("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
		if (FCode.equals("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equals("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equals("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
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
		if (FCode.equals("WaitPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WaitPeriod = i;
			}
		}
		if (FCode.equals("GetForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetForm = FValue.trim();
			}
			else
				GetForm = null;
		}
		if (FCode.equals("GetBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetBankCode = FValue.trim();
			}
			else
				GetBankCode = null;
		}
		if (FCode.equals("GetBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetBankAccNo = FValue.trim();
			}
			else
				GetBankAccNo = null;
		}
		if (FCode.equals("GetAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetAccName = FValue.trim();
			}
			else
				GetAccName = null;
		}
		if (FCode.equals("KeepValueOpt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeepValueOpt = FValue.trim();
			}
			else
				KeepValueOpt = null;
		}
		if (FCode.equals("PayoutStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayoutStart = i;
			}
		}
		if (FCode.equals("RiskPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskPrem = d;
			}
		}
		if (FCode.equals("PayoutEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayoutEnd = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCPolSchema other = (LCPolSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ContType.equals(other.getContType())
			&& PolTypeFlag.equals(other.getPolTypeFlag())
			&& MainPolNo.equals(other.getMainPolNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskAlias.equals(other.getRiskAlias())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Handler.equals(other.getHandler())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredAppAge == other.getInsuredAppAge()
			&& InsuredPeoples == other.getInsuredPeoples()
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredID.equals(other.getInsuredID())
			&& OccupationType.equals(other.getOccupationType())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(AcciEndDate).equals(other.getAcciEndDate())
			&& MasterPolNo.equals(other.getMasterPolNo())
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& GetYear == other.getGetYear()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& AcciYear == other.getAcciYear()
			&& GetStartType.equals(other.getGetStartType())
			&& SpecifyValiDate.equals(other.getSpecifyValiDate())
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& PayIntv == other.getPayIntv()
			&& PayYears == other.getPayYears()
			&& Years == other.getYears()
			&& ManageFeeRate == other.getManageFeeRate()
			&& FloatRate == other.getFloatRate()
			&& PremToAmnt.equals(other.getPremToAmnt())
			&& Mult == other.getMult()
			&& StandPrem == other.getStandPrem()
			&& FirstAddPrem == other.getFirstAddPrem()
			&& Prem == other.getPrem()
			&& SumPrem == other.getSumPrem()
			&& InPrem == other.getInPrem()
			&& InAmnt == other.getInAmnt()
			&& AddPrem == other.getAddPrem()
			&& AddAmnt == other.getAddAmnt()
			&& Amnt == other.getAmnt()
			&& RiskAmnt == other.getRiskAmnt()
			&& LeavingMoney == other.getLeavingMoney()
			&& EndorseTimes == other.getEndorseTimes()
			&& ClaimTimes == other.getClaimTimes()
			&& LiveTimes == other.getLiveTimes()
			&& RenewCount == other.getRenewCount()
			&& fDate.getString(LastGetDate).equals(other.getLastGetDate())
			&& fDate.getString(LastLoanDate).equals(other.getLastLoanDate())
			&& fDate.getString(LastRegetDate).equals(other.getLastRegetDate())
			&& fDate.getString(LastEdorDate).equals(other.getLastEdorDate())
			&& fDate.getString(LastRevDate).equals(other.getLastRevDate())
			&& RnewFlag == other.getRnewFlag()
			&& StopFlag.equals(other.getStopFlag())
			&& ExpiryFlag.equals(other.getExpiryFlag())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& InterestDifFlag.equals(other.getInterestDifFlag())
			&& SubFlag.equals(other.getSubFlag())
			&& BnfFlag.equals(other.getBnfFlag())
			&& HighRiskFlag.equals(other.getHighRiskFlag())
			&& HealthCheckFlag.equals(other.getHealthCheckFlag())
			&& InvestDateFlag.equals(other.getInvestDateFlag())
			&& ImpartFlag.equals(other.getImpartFlag())
			&& ReinsureFlag.equals(other.getReinsureFlag())
			&& AgentPayFlag.equals(other.getAgentPayFlag())
			&& AgentGetFlag.equals(other.getAgentGetFlag())
			&& LiveGetMode.equals(other.getLiveGetMode())
			&& DeadGetMode.equals(other.getDeadGetMode())
			&& BonusGetMode.equals(other.getBonusGetMode())
			&& BonusMan.equals(other.getBonusMan())
			&& DeadFlag.equals(other.getDeadFlag())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& Remark.equals(other.getRemark())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& AppFlag.equals(other.getAppFlag())
			&& PolState.equals(other.getPolState())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& WaitPeriod == other.getWaitPeriod()
			&& GetForm.equals(other.getGetForm())
			&& GetBankCode.equals(other.getGetBankCode())
			&& GetBankAccNo.equals(other.getGetBankAccNo())
			&& GetAccName.equals(other.getGetAccName())
			&& KeepValueOpt.equals(other.getKeepValueOpt())
			&& PayoutStart == other.getPayoutStart()
			&& RiskPrem == other.getRiskPrem()
			&& PayoutEnd == other.getPayoutEnd();
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 5;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContType") ) {
			return 7;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return 8;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("KindCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("RiskAlias") ) {
			return 12;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 15;
		}
		if( strFieldName.equals("AgentType") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 18;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 19;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 20;
		}
		if( strFieldName.equals("Handler") ) {
			return 21;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 22;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 23;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return 26;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 27;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 28;
		}
		if( strFieldName.equals("InsuredID") ) {
			return 29;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 30;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 31;
		}
		if( strFieldName.equals("AppntName") ) {
			return 32;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 33;
		}
		if( strFieldName.equals("SignCom") ) {
			return 34;
		}
		if( strFieldName.equals("SignDate") ) {
			return 35;
		}
		if( strFieldName.equals("SignTime") ) {
			return 36;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 37;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 38;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 39;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 40;
		}
		if( strFieldName.equals("EndDate") ) {
			return 41;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return 42;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return 43;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 44;
		}
		if( strFieldName.equals("GetYear") ) {
			return 45;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 46;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 47;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 48;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 49;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 50;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 51;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 52;
		}
		if( strFieldName.equals("SpecifyValiDate") ) {
			return 53;
		}
		if( strFieldName.equals("PayMode") ) {
			return 54;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 55;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 56;
		}
		if( strFieldName.equals("PayYears") ) {
			return 57;
		}
		if( strFieldName.equals("Years") ) {
			return 58;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 59;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 60;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return 61;
		}
		if( strFieldName.equals("Mult") ) {
			return 62;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 63;
		}
		if( strFieldName.equals("FirstAddPrem") ) {
			return 64;
		}
		if( strFieldName.equals("Prem") ) {
			return 65;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 66;
		}
		if( strFieldName.equals("InPrem") ) {
			return 67;
		}
		if( strFieldName.equals("InAmnt") ) {
			return 68;
		}
		if( strFieldName.equals("AddPrem") ) {
			return 69;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return 70;
		}
		if( strFieldName.equals("Amnt") ) {
			return 71;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 72;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return 73;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return 74;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return 75;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return 76;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 77;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 78;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return 79;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return 80;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 81;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return 82;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 83;
		}
		if( strFieldName.equals("StopFlag") ) {
			return 84;
		}
		if( strFieldName.equals("ExpiryFlag") ) {
			return 85;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 86;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return 87;
		}
		if( strFieldName.equals("SubFlag") ) {
			return 88;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return 89;
		}
		if( strFieldName.equals("HighRiskFlag") ) {
			return 90;
		}
		if( strFieldName.equals("HealthCheckFlag") ) {
			return 91;
		}
		if( strFieldName.equals("InvestDateFlag") ) {
			return 92;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return 93;
		}
		if( strFieldName.equals("ReinsureFlag") ) {
			return 94;
		}
		if( strFieldName.equals("AgentPayFlag") ) {
			return 95;
		}
		if( strFieldName.equals("AgentGetFlag") ) {
			return 96;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return 97;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return 98;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return 99;
		}
		if( strFieldName.equals("BonusMan") ) {
			return 100;
		}
		if( strFieldName.equals("DeadFlag") ) {
			return 101;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 102;
		}
		if( strFieldName.equals("Remark") ) {
			return 103;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 104;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 105;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 106;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 107;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 108;
		}
		if( strFieldName.equals("UWCode") ) {
			return 109;
		}
		if( strFieldName.equals("UWDate") ) {
			return 110;
		}
		if( strFieldName.equals("UWTime") ) {
			return 111;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 112;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 113;
		}
		if( strFieldName.equals("PolState") ) {
			return 114;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 115;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 116;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 117;
		}
		if( strFieldName.equals("Operator") ) {
			return 118;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 119;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 120;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 121;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 122;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return 123;
		}
		if( strFieldName.equals("GetForm") ) {
			return 124;
		}
		if( strFieldName.equals("GetBankCode") ) {
			return 125;
		}
		if( strFieldName.equals("GetBankAccNo") ) {
			return 126;
		}
		if( strFieldName.equals("GetAccName") ) {
			return 127;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return 128;
		}
		if( strFieldName.equals("PayoutStart") ) {
			return 129;
		}
		if( strFieldName.equals("RiskPrem") ) {
			return 130;
		}
		if( strFieldName.equals("PayoutEnd") ) {
			return 131;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "ProposalNo";
				break;
			case 6:
				strFieldName = "PrtNo";
				break;
			case 7:
				strFieldName = "ContType";
				break;
			case 8:
				strFieldName = "PolTypeFlag";
				break;
			case 9:
				strFieldName = "MainPolNo";
				break;
			case 10:
				strFieldName = "KindCode";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "RiskAlias";
				break;
			case 13:
				strFieldName = "RiskVersion";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "AgentCom";
				break;
			case 16:
				strFieldName = "AgentType";
				break;
			case 17:
				strFieldName = "AgentCode";
				break;
			case 18:
				strFieldName = "AgentGroup";
				break;
			case 19:
				strFieldName = "AgentCode1";
				break;
			case 20:
				strFieldName = "SaleChnl";
				break;
			case 21:
				strFieldName = "Handler";
				break;
			case 22:
				strFieldName = "InsuredNo";
				break;
			case 23:
				strFieldName = "InsuredName";
				break;
			case 24:
				strFieldName = "InsuredSex";
				break;
			case 25:
				strFieldName = "InsuredBirthday";
				break;
			case 26:
				strFieldName = "InsuredAppAge";
				break;
			case 27:
				strFieldName = "InsuredPeoples";
				break;
			case 28:
				strFieldName = "InsuredIDType";
				break;
			case 29:
				strFieldName = "InsuredID";
				break;
			case 30:
				strFieldName = "OccupationType";
				break;
			case 31:
				strFieldName = "AppntNo";
				break;
			case 32:
				strFieldName = "AppntName";
				break;
			case 33:
				strFieldName = "CValiDate";
				break;
			case 34:
				strFieldName = "SignCom";
				break;
			case 35:
				strFieldName = "SignDate";
				break;
			case 36:
				strFieldName = "SignTime";
				break;
			case 37:
				strFieldName = "FirstPayDate";
				break;
			case 38:
				strFieldName = "PayEndDate";
				break;
			case 39:
				strFieldName = "PaytoDate";
				break;
			case 40:
				strFieldName = "GetStartDate";
				break;
			case 41:
				strFieldName = "EndDate";
				break;
			case 42:
				strFieldName = "AcciEndDate";
				break;
			case 43:
				strFieldName = "MasterPolNo";
				break;
			case 44:
				strFieldName = "GetYearFlag";
				break;
			case 45:
				strFieldName = "GetYear";
				break;
			case 46:
				strFieldName = "PayEndYearFlag";
				break;
			case 47:
				strFieldName = "PayEndYear";
				break;
			case 48:
				strFieldName = "InsuYearFlag";
				break;
			case 49:
				strFieldName = "InsuYear";
				break;
			case 50:
				strFieldName = "AcciYearFlag";
				break;
			case 51:
				strFieldName = "AcciYear";
				break;
			case 52:
				strFieldName = "GetStartType";
				break;
			case 53:
				strFieldName = "SpecifyValiDate";
				break;
			case 54:
				strFieldName = "PayMode";
				break;
			case 55:
				strFieldName = "PayLocation";
				break;
			case 56:
				strFieldName = "PayIntv";
				break;
			case 57:
				strFieldName = "PayYears";
				break;
			case 58:
				strFieldName = "Years";
				break;
			case 59:
				strFieldName = "ManageFeeRate";
				break;
			case 60:
				strFieldName = "FloatRate";
				break;
			case 61:
				strFieldName = "PremToAmnt";
				break;
			case 62:
				strFieldName = "Mult";
				break;
			case 63:
				strFieldName = "StandPrem";
				break;
			case 64:
				strFieldName = "FirstAddPrem";
				break;
			case 65:
				strFieldName = "Prem";
				break;
			case 66:
				strFieldName = "SumPrem";
				break;
			case 67:
				strFieldName = "InPrem";
				break;
			case 68:
				strFieldName = "InAmnt";
				break;
			case 69:
				strFieldName = "AddPrem";
				break;
			case 70:
				strFieldName = "AddAmnt";
				break;
			case 71:
				strFieldName = "Amnt";
				break;
			case 72:
				strFieldName = "RiskAmnt";
				break;
			case 73:
				strFieldName = "LeavingMoney";
				break;
			case 74:
				strFieldName = "EndorseTimes";
				break;
			case 75:
				strFieldName = "ClaimTimes";
				break;
			case 76:
				strFieldName = "LiveTimes";
				break;
			case 77:
				strFieldName = "RenewCount";
				break;
			case 78:
				strFieldName = "LastGetDate";
				break;
			case 79:
				strFieldName = "LastLoanDate";
				break;
			case 80:
				strFieldName = "LastRegetDate";
				break;
			case 81:
				strFieldName = "LastEdorDate";
				break;
			case 82:
				strFieldName = "LastRevDate";
				break;
			case 83:
				strFieldName = "RnewFlag";
				break;
			case 84:
				strFieldName = "StopFlag";
				break;
			case 85:
				strFieldName = "ExpiryFlag";
				break;
			case 86:
				strFieldName = "AutoPayFlag";
				break;
			case 87:
				strFieldName = "InterestDifFlag";
				break;
			case 88:
				strFieldName = "SubFlag";
				break;
			case 89:
				strFieldName = "BnfFlag";
				break;
			case 90:
				strFieldName = "HighRiskFlag";
				break;
			case 91:
				strFieldName = "HealthCheckFlag";
				break;
			case 92:
				strFieldName = "InvestDateFlag";
				break;
			case 93:
				strFieldName = "ImpartFlag";
				break;
			case 94:
				strFieldName = "ReinsureFlag";
				break;
			case 95:
				strFieldName = "AgentPayFlag";
				break;
			case 96:
				strFieldName = "AgentGetFlag";
				break;
			case 97:
				strFieldName = "LiveGetMode";
				break;
			case 98:
				strFieldName = "DeadGetMode";
				break;
			case 99:
				strFieldName = "BonusGetMode";
				break;
			case 100:
				strFieldName = "BonusMan";
				break;
			case 101:
				strFieldName = "DeadFlag";
				break;
			case 102:
				strFieldName = "SmokeFlag";
				break;
			case 103:
				strFieldName = "Remark";
				break;
			case 104:
				strFieldName = "ApproveFlag";
				break;
			case 105:
				strFieldName = "ApproveCode";
				break;
			case 106:
				strFieldName = "ApproveDate";
				break;
			case 107:
				strFieldName = "ApproveTime";
				break;
			case 108:
				strFieldName = "UWFlag";
				break;
			case 109:
				strFieldName = "UWCode";
				break;
			case 110:
				strFieldName = "UWDate";
				break;
			case 111:
				strFieldName = "UWTime";
				break;
			case 112:
				strFieldName = "PolApplyDate";
				break;
			case 113:
				strFieldName = "AppFlag";
				break;
			case 114:
				strFieldName = "PolState";
				break;
			case 115:
				strFieldName = "StandbyFlag1";
				break;
			case 116:
				strFieldName = "StandbyFlag2";
				break;
			case 117:
				strFieldName = "StandbyFlag3";
				break;
			case 118:
				strFieldName = "Operator";
				break;
			case 119:
				strFieldName = "MakeDate";
				break;
			case 120:
				strFieldName = "MakeTime";
				break;
			case 121:
				strFieldName = "ModifyDate";
				break;
			case 122:
				strFieldName = "ModifyTime";
				break;
			case 123:
				strFieldName = "WaitPeriod";
				break;
			case 124:
				strFieldName = "GetForm";
				break;
			case 125:
				strFieldName = "GetBankCode";
				break;
			case 126:
				strFieldName = "GetBankAccNo";
				break;
			case 127:
				strFieldName = "GetAccName";
				break;
			case 128:
				strFieldName = "KeepValueOpt";
				break;
			case 129:
				strFieldName = "PayoutStart";
				break;
			case 130:
				strFieldName = "RiskPrem";
				break;
			case 131:
				strFieldName = "PayoutEnd";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAlias") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetStartType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecifyValiDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FirstAddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RenewCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StopFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HighRiskFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthCheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("WaitPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayoutStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayoutEnd") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 41:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_INT;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_INT;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_INT;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 57:
				nFieldType = Schema.TYPE_INT;
				break;
			case 58:
				nFieldType = Schema.TYPE_INT;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 60:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 63:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 64:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 65:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 66:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 67:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 68:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 69:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 70:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 73:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 74:
				nFieldType = Schema.TYPE_INT;
				break;
			case 75:
				nFieldType = Schema.TYPE_INT;
				break;
			case 76:
				nFieldType = Schema.TYPE_INT;
				break;
			case 77:
				nFieldType = Schema.TYPE_INT;
				break;
			case 78:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 79:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 80:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 81:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 82:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 83:
				nFieldType = Schema.TYPE_INT;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 99:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 106:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 107:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 108:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 110:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 111:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 112:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 113:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 114:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 115:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 116:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 117:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 118:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 119:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 120:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 121:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 122:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 123:
				nFieldType = Schema.TYPE_INT;
				break;
			case 124:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 125:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 126:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 127:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 128:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 129:
				nFieldType = Schema.TYPE_INT;
				break;
			case 130:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 131:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
