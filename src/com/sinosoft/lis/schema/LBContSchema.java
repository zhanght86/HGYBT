/*
 * <p>ClassName: LBContSchema </p>
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
import com.sinosoft.lis.db.LBContDB;

public class LBContSchema implements Schema
{
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 保单合同号�� */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 总单类型 */
	private String ContType;
	/** 保单类型标记 */
	private String PolType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 金盛代理人 */
	private String AXAAgentCode;
	/** 金盛代理机构 */
	private String AXAAgentCom;
	/** 金盛网点 */
	private String AXANodeMap;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构名 */
	private String AgentComName;
	/** 代理人名称 */
	private String AgentName;
	/** 代理人编码 */
	private String AgentCode;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private Date AppntBirthday;
	/** 投保人证件类型 */
	private String AppntIDType;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出生日期 */
	private Date InsuredBirthday;
	/** 证件类型 */
	private String InsuredIDType;
	/** 证件号码 */
	private String InsuredIDNo;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 主险缴费期间类型 */
	private String PayEndYearFlag;
	/** 主险缴费期间 */
	private int PayEndYear;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 保单送达方式 */
	private String GetPolMode;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 保单打印次数 */
	private int PrintCount;
	/** 语种标记 */
	private String Lang;
	/** 份数 */
	private double Mult;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 首期保费 */
	private double FirstPrem;
	/** 主险保费 */
	private double MainPolPrem;
	/** 首期额外追加保费 */
	private double FirstAddPrem;
	/** 期交追加保费 */
	private double AddPrem;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 银代银行代码 */
	private String AgentBankCode;
	/** 银代柜员 */
	private String BankAgent;
	/** 强制人工核保标志 */
	private String ForceUWFlag;
	/** 录单人 */
	private String InputOperator;
	/** 录单完成日期 */
	private Date InputDate;
	/** 录单完成时间 */
	private String InputTime;
	/** 家庭单类型 */
	private String FamilyType;
	/** 家庭保障号 */
	private String FamilyID;
	/** 卡单标志 */
	private String CardFlag;
	/** 处理机构 */
	private String ExecuteCom;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 经办人 */
	private String Handler;
	/** 保单口令 */
	private String Password;
	/** 交费位置 */
	private String PayLocation;
	/** 合同争议处理方式 */
	private String DisputedFlag;
	/** 签单时间 */
	private String SignTime;
	/** 银行委托书号码 */
	private String ConsignNo;
	/** 遗失补发次数 */
	private int LostTimes;
	/** 币别 */
	private String Currency;
	/** 备注 */
	private String Remark;
	/** 人数 */
	private int Peoples;
	/** 累计保费 */
	private double SumPrem;
	/** 余额 */
	private double Dif;
	/** 交至日期 */
	private Date PaytoDate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核状态编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保状态 */
	private String UWFlag;
	/** 核保人 */
	private String UWOperator;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 保单送达时间 */
	private String GetPolTime;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 对账状态 */
	private String BalanceState;
	/** 状态 */
	private String State;
	/** 初审人 */
	private String FirstTrialOperator;
	/** 初审日期 */
	private Date FirstTrialDate;
	/** 初审时间 */
	private String FirstTrialTime;
	/** 收单人 */
	private String ReceiveOperator;
	/** 收单日期 */
	private Date ReceiveDate;
	/** 收单时间 */
	private String ReceiveTime;
	/** 暂收据号 */
	private String TempFeeNo;
	/** Selltype */
	private String SellType;
	/** 强制人工核保原因 */
	private String ForceUWReason;
	/** 首期银行编码 */
	private String NewBankCode;
	/** 首期银行帐号 */
	private String NewBankAccNo;
	/** 首期银行帐户名 */
	private String NewAccName;
	/** 首期交费方式 */
	private String NewPayMode;
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

	public static final int FIELDNUM = 108;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ContNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 集体合同号码<P>集体合同号码-YBT默认为20个0 */
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
	/** 保单合同号��<P>保单号-取自网点所属机构下的保单号-LKCONTNO */
	public String getContNo()
	{
		if (ContNo != null && !ContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContNo = StrTool.unicodeToGBK(ContNo);
		}
		return ContNo;
	}
	/** 保单合同号�� */
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/** 总单投保单号码<P>总单投保单号码-金盛20位 */
	public String getProposalContNo()
	{
		if (ProposalContNo != null && !ProposalContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalContNo = StrTool.unicodeToGBK(ProposalContNo);
		}
		return ProposalContNo;
	}
	/** 总单投保单号码 */
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/** 印刷号码<P>保单印刷号-20位 */
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
	/** 总单类型<P>ContType总单类型  默认为1<br>1-个人总投保单2-集体总单 */
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
	/** 保单类型标记<P>保单类型标记 默认为0<br>0 个人单1-无名单2 -团单公共帐户 */
	public String getPolType()
	{
		if (PolType != null && !PolType.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolType = StrTool.unicodeToGBK(PolType);
		}
		return PolType;
	}
	/** 保单类型标记 */
	public void setPolType(String aPolType)
	{
		PolType = aPolType;
	}
	/** 销售渠道<P>销售渠道 默认为3<br>1-个人营销,2-团险直销,3-银行代理<br> */
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
	/** 金盛代理人<P>金盛代理人  */
	public String getAXAAgentCode()
	{
		if (AXAAgentCode != null && !AXAAgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXAAgentCode = StrTool.unicodeToGBK(AXAAgentCode);
		}
		return AXAAgentCode;
	}
	/** 金盛代理人 */
	public void setAXAAgentCode(String aAXAAgentCode)
	{
		AXAAgentCode = aAXAAgentCode;
	}
	/** 金盛代理机构<P>金盛代理机构 */
	public String getAXAAgentCom()
	{
		if (AXAAgentCom != null && !AXAAgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXAAgentCom = StrTool.unicodeToGBK(AXAAgentCom);
		}
		return AXAAgentCom;
	}
	/** 金盛代理机构 */
	public void setAXAAgentCom(String aAXAAgentCom)
	{
		AXAAgentCom = aAXAAgentCom;
	}
	/** 金盛网点<P>金盛网点 */
	public String getAXANodeMap()
	{
		if (AXANodeMap != null && !AXANodeMap.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXANodeMap = StrTool.unicodeToGBK(AXANodeMap);
		}
		return AXANodeMap;
	}
	/** 金盛网点 */
	public void setAXANodeMap(String aAXANodeMap)
	{
		AXANodeMap = aAXANodeMap;
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
	/** 代理机构名<P>代理机构名 */
	public String getAgentComName()
	{
		if (AgentComName != null && !AgentComName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentComName = StrTool.unicodeToGBK(AgentComName);
		}
		return AgentComName;
	}
	/** 代理机构名 */
	public void setAgentComName(String aAgentComName)
	{
		AgentComName = aAgentComName;
	}
	/** 代理人名称<P>代理人名称 */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** 代理人名称 */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
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
	/** 投保人性别<P>投保人性别 */
	public String getAppntSex()
	{
		if (AppntSex != null && !AppntSex.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntSex = StrTool.unicodeToGBK(AppntSex);
		}
		return AppntSex;
	}
	/** 投保人性别 */
	public void setAppntSex(String aAppntSex)
	{
		AppntSex = aAppntSex;
	}
	/** 投保人出生日期<P>投保人出生日期 */
	public String getAppntBirthday()
	{
		if( AppntBirthday != null )
			return fDate.getString(AppntBirthday);
		else
			return null;
	}
	/** 投保人出生日期 */
	public void setAppntBirthday(Date aAppntBirthday)
	{
		AppntBirthday = aAppntBirthday;
	}
	/** 投保人出生日期<P>投保人出生日期 */
	public void setAppntBirthday(String aAppntBirthday)
	{
		if (aAppntBirthday != null && !aAppntBirthday.equals("") )
		{
			AppntBirthday = fDate.getDate( aAppntBirthday );
		}
		else
			AppntBirthday = null;
	}

	/** 投保人证件类型<P>证件类型<br>0-身份证 1-护照 2-军官证 3-士兵证 4-回乡证 5-临时身份证6-户口本7-其他9-警官证 */
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
	/** 被保人客户号<P>被保人客户号 */
	public String getInsuredNo()
	{
		if (InsuredNo != null && !InsuredNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredNo = StrTool.unicodeToGBK(InsuredNo);
		}
		return InsuredNo;
	}
	/** 被保人客户号 */
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
	/** 被保人出生日期<P>被保人出生日期 */
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	/** 被保人出生日期 */
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	/** 被保人出生日期<P>被保人出生日期 */
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	/** 证件类型<P>证件类型<br>0-身份证 1-护照 2-军官证 3-士兵证 4-回乡证 5-临时身份证6-户口本7-其他9-警官证 */
	public String getInsuredIDType()
	{
		if (InsuredIDType != null && !InsuredIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDType = StrTool.unicodeToGBK(InsuredIDType);
		}
		return InsuredIDType;
	}
	/** 证件类型 */
	public void setInsuredIDType(String aInsuredIDType)
	{
		InsuredIDType = aInsuredIDType;
	}
	/** 证件号码<P>证件号码 */
	public String getInsuredIDNo()
	{
		if (InsuredIDNo != null && !InsuredIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDNo = StrTool.unicodeToGBK(InsuredIDNo);
		}
		return InsuredIDNo;
	}
	/** 证件号码 */
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
	}
	/** 交费间隔<P>交费间隔<br> 1-年 2-月 3-半年 4-季 5-趸 */
	public int getPayIntv()
	{
		return PayIntv;
	}
	/** 交费间隔 */
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/** 交费间隔<P>交费间隔<br> 1-年 2-月 3-半年 4-季 5-趸 */
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/** 交费方式<P>默认为1<br>1--银行转帐 */
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
	/** 主险缴费期间类型<P>0-季缴1-缴至某确定年龄2-年3-月4-日5-趸缴6-终缴费7-不定期缴8-半年缴9-其他 */
	public String getPayEndYearFlag()
	{
		if (PayEndYearFlag != null && !PayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYearFlag = StrTool.unicodeToGBK(PayEndYearFlag);
		}
		return PayEndYearFlag;
	}
	/** 主险缴费期间类型 */
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	/** 主险缴费期间<P> */
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	/** 主险缴费期间 */
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** 主险缴费期间<P> */
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/** 溢交处理方式<P>暂不启用：红利领取方式 1累计生息 2领取现金 3低交保费 4增额缴清 5无红利 */
	public String getOutPayFlag()
	{
		if (OutPayFlag != null && !OutPayFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutPayFlag = StrTool.unicodeToGBK(OutPayFlag);
		}
		return OutPayFlag;
	}
	/** 溢交处理方式 */
	public void setOutPayFlag(String aOutPayFlag)
	{
		OutPayFlag = aOutPayFlag;
	}
	/** 保单送达方式<P>保单送达方式 4银行领取 */
	public String getGetPolMode()
	{
		if (GetPolMode != null && !GetPolMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetPolMode = StrTool.unicodeToGBK(GetPolMode);
		}
		return GetPolMode;
	}
	/** 保单送达方式 */
	public void setGetPolMode(String aGetPolMode)
	{
		GetPolMode = aGetPolMode;
	}
	/** 银行编码<P>银行编码 */
	public String getBankCode()
	{
		if (BankCode != null && !BankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankCode = StrTool.unicodeToGBK(BankCode);
		}
		return BankCode;
	}
	/** 银行编码 */
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/** 银行帐号<P>银行帐号 */
	public String getBankAccNo()
	{
		if (BankAccNo != null && !BankAccNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankAccNo = StrTool.unicodeToGBK(BankAccNo);
		}
		return BankAccNo;
	}
	/** 银行帐号 */
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	/** 银行帐户名<P>银行帐户名 */
	public String getAccName()
	{
		if (AccName != null && !AccName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccName = StrTool.unicodeToGBK(AccName);
		}
		return AccName;
	}
	/** 银行帐户名 */
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	/** 保单打印次数<P>保单打印次数 */
	public int getPrintCount()
	{
		return PrintCount;
	}
	/** 保单打印次数 */
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	/** 保单打印次数<P>保单打印次数 */
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	/** 语种标记<P>语种标记 */
	public String getLang()
	{
		if (Lang != null && !Lang.equals("") && SysConst.CHANGECHARSET == true)
		{
			Lang = StrTool.unicodeToGBK(Lang);
		}
		return Lang;
	}
	/** 语种标记 */
	public void setLang(String aLang)
	{
		Lang = aLang;
	}
	/** 份数<P>份数 */
	public double getMult()
	{
		return Mult;
	}
	/** 份数 */
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	/** 份数<P>份数 */
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	/** 保费<P>保费 */
	public double getPrem()
	{
		return Prem;
	}
	/** 保费 */
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	/** 保费<P>保费 */
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	/** 保额<P>保额 */
	public double getAmnt()
	{
		return Amnt;
	}
	/** 保额 */
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	/** 保额<P>保额 */
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	/** 首期保费<P>首期保费-银行柜面总保费 */
	public double getFirstPrem()
	{
		return FirstPrem;
	}
	/** 首期保费 */
	public void setFirstPrem(double aFirstPrem)
	{
		FirstPrem = aFirstPrem;
	}
	/** 首期保费<P>首期保费-银行柜面总保费 */
	public void setFirstPrem(String aFirstPrem)
	{
		if (aFirstPrem != null && !aFirstPrem.equals(""))
		{
			Double tDouble = new Double(aFirstPrem);
			double d = tDouble.doubleValue();
			FirstPrem = d;
		}
	}

	/** 主险保费<P>主险保费 */
	public double getMainPolPrem()
	{
		return MainPolPrem;
	}
	/** 主险保费 */
	public void setMainPolPrem(double aMainPolPrem)
	{
		MainPolPrem = aMainPolPrem;
	}
	/** 主险保费<P>主险保费 */
	public void setMainPolPrem(String aMainPolPrem)
	{
		if (aMainPolPrem != null && !aMainPolPrem.equals(""))
		{
			Double tDouble = new Double(aMainPolPrem);
			double d = tDouble.doubleValue();
			MainPolPrem = d;
		}
	}

	/** 首期额外追加保费<P>首期额外追加保费 */
	public double getFirstAddPrem()
	{
		return FirstAddPrem;
	}
	/** 首期额外追加保费 */
	public void setFirstAddPrem(double aFirstAddPrem)
	{
		FirstAddPrem = aFirstAddPrem;
	}
	/** 首期额外追加保费<P>首期额外追加保费 */
	public void setFirstAddPrem(String aFirstAddPrem)
	{
		if (aFirstAddPrem != null && !aFirstAddPrem.equals(""))
		{
			Double tDouble = new Double(aFirstAddPrem);
			double d = tDouble.doubleValue();
			FirstAddPrem = d;
		}
	}

	/** 期交追加保费<P>期交追加保费 */
	public double getAddPrem()
	{
		return AddPrem;
	}
	/** 期交追加保费 */
	public void setAddPrem(double aAddPrem)
	{
		AddPrem = aAddPrem;
	}
	/** 期交追加保费<P>期交追加保费 */
	public void setAddPrem(String aAddPrem)
	{
		if (aAddPrem != null && !aAddPrem.equals(""))
		{
			Double tDouble = new Double(aAddPrem);
			double d = tDouble.doubleValue();
			AddPrem = d;
		}
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

	/** 保单生效日期<P>保单生效日期 */
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	/** 保单生效日期 */
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	/** 保单生效日期<P>保单生效日期 */
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

	/** 投保单/保单标志<P>保单标志 0-投保 B-未对账核保通过 1-对账核保通过 */
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
	/** 银代银行代码<P>银代银行代码 */
	public String getAgentBankCode()
	{
		if (AgentBankCode != null && !AgentBankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentBankCode = StrTool.unicodeToGBK(AgentBankCode);
		}
		return AgentBankCode;
	}
	/** 银代银行代码 */
	public void setAgentBankCode(String aAgentBankCode)
	{
		AgentBankCode = aAgentBankCode;
	}
	/** 银代柜员<P> */
	public String getBankAgent()
	{
		if (BankAgent != null && !BankAgent.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankAgent = StrTool.unicodeToGBK(BankAgent);
		}
		return BankAgent;
	}
	/** 银代柜员 */
	public void setBankAgent(String aBankAgent)
	{
		BankAgent = aBankAgent;
	}
	/** 强制人工核保标志<P>强制人工核保标志 默认为0<br>1-为强制<br> */
	public String getForceUWFlag()
	{
		if (ForceUWFlag != null && !ForceUWFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ForceUWFlag = StrTool.unicodeToGBK(ForceUWFlag);
		}
		return ForceUWFlag;
	}
	/** 强制人工核保标志 */
	public void setForceUWFlag(String aForceUWFlag)
	{
		ForceUWFlag = aForceUWFlag;
	}
	/** 录单人<P>录单人-银保通默认为银代柜员 */
	public String getInputOperator()
	{
		if (InputOperator != null && !InputOperator.equals("") && SysConst.CHANGECHARSET == true)
		{
			InputOperator = StrTool.unicodeToGBK(InputOperator);
		}
		return InputOperator;
	}
	/** 录单人 */
	public void setInputOperator(String aInputOperator)
	{
		InputOperator = aInputOperator;
	}
	/** 录单完成日期<P>录单完成日期 */
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	/** 录单完成日期 */
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	/** 录单完成日期<P>录单完成日期 */
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	/** 录单完成时间<P>录单完成时间 */
	public String getInputTime()
	{
		if (InputTime != null && !InputTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			InputTime = StrTool.unicodeToGBK(InputTime);
		}
		return InputTime;
	}
	/** 录单完成时间 */
	public void setInputTime(String aInputTime)
	{
		InputTime = aInputTime;
	}
	/** 家庭单类型<P>家庭单类型0-个人，1-家庭单 */
	public String getFamilyType()
	{
		if (FamilyType != null && !FamilyType.equals("") && SysConst.CHANGECHARSET == true)
		{
			FamilyType = StrTool.unicodeToGBK(FamilyType);
		}
		return FamilyType;
	}
	/** 家庭单类型 */
	public void setFamilyType(String aFamilyType)
	{
		FamilyType = aFamilyType;
	}
	/** 家庭保障号<P>家庭保障号-暂不启用 */
	public String getFamilyID()
	{
		if (FamilyID != null && !FamilyID.equals("") && SysConst.CHANGECHARSET == true)
		{
			FamilyID = StrTool.unicodeToGBK(FamilyID);
		}
		return FamilyID;
	}
	/** 家庭保障号 */
	public void setFamilyID(String aFamilyID)
	{
		FamilyID = aFamilyID;
	}
	/** 卡单标志<P>卡单标志0正常1定额单3卡单-YBT不处理  默认为0 */
	public String getCardFlag()
	{
		if (CardFlag != null && !CardFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CardFlag = StrTool.unicodeToGBK(CardFlag);
		}
		return CardFlag;
	}
	/** 卡单标志 */
	public void setCardFlag(String aCardFlag)
	{
		CardFlag = aCardFlag;
	}
	/** 处理机构<P>处理机构-默认是出单网点所属机构 */
	public String getExecuteCom()
	{
		if (ExecuteCom != null && !ExecuteCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ExecuteCom = StrTool.unicodeToGBK(ExecuteCom);
		}
		return ExecuteCom;
	}
	/** 处理机构 */
	public void setExecuteCom(String aExecuteCom)
	{
		ExecuteCom = aExecuteCom;
	}
	/** 代理人组别<P>代理人组别-暂不启用 */
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
	/** 联合代理人代码<P>联合代理人代码-暂不启用 */
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
	/** 代理机构内部分类<P>代理机构内部分类<br>-暂不启用 */
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
	/** 经办人<P>经办人-暂不启用 */
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
	/** 保单口令<P>保单口令-暂不启用 */
	public String getPassword()
	{
		if (Password != null && !Password.equals("") && SysConst.CHANGECHARSET == true)
		{
			Password = StrTool.unicodeToGBK(Password);
		}
		return Password;
	}
	/** 保单口令 */
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	/** 交费位置<P>该字段表示续期交费形式PayLocation-暂不启用 */
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
	/** 合同争议处理方式<P>-暂不启用 */
	public String getDisputedFlag()
	{
		if (DisputedFlag != null && !DisputedFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			DisputedFlag = StrTool.unicodeToGBK(DisputedFlag);
		}
		return DisputedFlag;
	}
	/** 合同争议处理方式 */
	public void setDisputedFlag(String aDisputedFlag)
	{
		DisputedFlag = aDisputedFlag;
	}
	/** 签单时间<P>签单时间-暂不启用 */
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
	/** 银行委托书号码<P>银行委托书号码-暂不启用 */
	public String getConsignNo()
	{
		if (ConsignNo != null && !ConsignNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ConsignNo = StrTool.unicodeToGBK(ConsignNo);
		}
		return ConsignNo;
	}
	/** 银行委托书号码 */
	public void setConsignNo(String aConsignNo)
	{
		ConsignNo = aConsignNo;
	}
	/** 遗失补发次数<P> */
	public int getLostTimes()
	{
		return LostTimes;
	}
	/** 遗失补发次数 */
	public void setLostTimes(int aLostTimes)
	{
		LostTimes = aLostTimes;
	}
	/** 遗失补发次数<P> */
	public void setLostTimes(String aLostTimes)
	{
		if (aLostTimes != null && !aLostTimes.equals(""))
		{
			Integer tInteger = new Integer(aLostTimes);
			int i = tInteger.intValue();
			LostTimes = i;
		}
	}

	/** 币别<P>币别-暂不启用 */
	public String getCurrency()
	{
		if (Currency != null && !Currency.equals("") && SysConst.CHANGECHARSET == true)
		{
			Currency = StrTool.unicodeToGBK(Currency);
		}
		return Currency;
	}
	/** 币别 */
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/** 备注<P>备注-暂不启用 */
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
	/** 人数<P>人数-默认为1-暂不启用 */
	public int getPeoples()
	{
		return Peoples;
	}
	/** 人数 */
	public void setPeoples(int aPeoples)
	{
		Peoples = aPeoples;
	}
	/** 人数<P>人数-默认为1-暂不启用 */
	public void setPeoples(String aPeoples)
	{
		if (aPeoples != null && !aPeoples.equals(""))
		{
			Integer tInteger = new Integer(aPeoples);
			int i = tInteger.intValue();
			Peoples = i;
		}
	}

	/** 累计保费<P>累计保费 */
	public double getSumPrem()
	{
		return SumPrem;
	}
	/** 累计保费 */
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	/** 累计保费<P>累计保费 */
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	/** 余额<P>余额-默认为0-暂不启用 */
	public double getDif()
	{
		return Dif;
	}
	/** 余额 */
	public void setDif(double aDif)
	{
		Dif = aDif;
	}
	/** 余额<P>余额-默认为0-暂不启用 */
	public void setDif(String aDif)
	{
		if (aDif != null && !aDif.equals(""))
		{
			Double tDouble = new Double(aDif);
			double d = tDouble.doubleValue();
			Dif = d;
		}
	}

	/** 交至日期<P>交至日期-暂不启用 */
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
	/** 交至日期<P>交至日期-暂不启用 */
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	/** 首期交费日期<P>首期交费日期-暂不启用 */
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
	/** 首期交费日期<P>首期交费日期-暂不启用 */
	public void setFirstPayDate(String aFirstPayDate)
	{
		if (aFirstPayDate != null && !aFirstPayDate.equals("") )
		{
			FirstPayDate = fDate.getDate( aFirstPayDate );
		}
		else
			FirstPayDate = null;
	}

	/** 复核状态<P>复核状态<br>-默认为0-暂不启用 */
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
	/** 复核状态编码<P>复核人编码-默认为YBT */
	public String getApproveCode()
	{
		if (ApproveCode != null && !ApproveCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApproveCode = StrTool.unicodeToGBK(ApproveCode);
		}
		return ApproveCode;
	}
	/** 复核状态编码 */
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
	}
	/** 复核日期<P>复核日期-暂不启用 */
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
	/** 复核日期<P>复核日期-暂不启用 */
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	/** 复核时间<P>复核时间-暂不启用 */
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
	/** 核保状态<P>核保状态1 拒保 2 延期 3 条件承保 4 通融 5 自动 6 待上级 7 问题件 8 核保通知书 9 正常 <br>a 撤单<br>b 保险计划变更<br>z 核保订正<br><br> */
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
	/** 核保人<P>核保人-暂不启用 */
	public String getUWOperator()
	{
		if (UWOperator != null && !UWOperator.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWOperator = StrTool.unicodeToGBK(UWOperator);
		}
		return UWOperator;
	}
	/** 核保人 */
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
	}
	/** 核保完成日期<P>核保完成日期-暂不启用 */
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
	/** 核保完成日期<P>核保完成日期-暂不启用 */
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
	/** 保单送达日期<P>保单送达日期-暂不启用 */
	public String getGetPolDate()
	{
		if( GetPolDate != null )
			return fDate.getString(GetPolDate);
		else
			return null;
	}
	/** 保单送达日期 */
	public void setGetPolDate(Date aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	/** 保单送达日期<P>保单送达日期-暂不启用 */
	public void setGetPolDate(String aGetPolDate)
	{
		if (aGetPolDate != null && !aGetPolDate.equals("") )
		{
			GetPolDate = fDate.getDate( aGetPolDate );
		}
		else
			GetPolDate = null;
	}

	/** 保单送达时间<P>保单送达时间-暂不启用 */
	public String getGetPolTime()
	{
		if (GetPolTime != null && !GetPolTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetPolTime = StrTool.unicodeToGBK(GetPolTime);
		}
		return GetPolTime;
	}
	/** 保单送达时间 */
	public void setGetPolTime(String aGetPolTime)
	{
		GetPolTime = aGetPolTime;
	}
	/** 保单回执客户签收日期<P>保单回执客户签收日期-暂不启用 */
	public String getCustomGetPolDate()
	{
		if( CustomGetPolDate != null )
			return fDate.getString(CustomGetPolDate);
		else
			return null;
	}
	/** 保单回执客户签收日期 */
	public void setCustomGetPolDate(Date aCustomGetPolDate)
	{
		CustomGetPolDate = aCustomGetPolDate;
	}
	/** 保单回执客户签收日期<P>保单回执客户签收日期-暂不启用 */
	public void setCustomGetPolDate(String aCustomGetPolDate)
	{
		if (aCustomGetPolDate != null && !aCustomGetPolDate.equals("") )
		{
			CustomGetPolDate = fDate.getDate( aCustomGetPolDate );
		}
		else
			CustomGetPolDate = null;
	}

	/** 对账状态<P> */
	public String getBalanceState()
	{
		if (BalanceState != null && !BalanceState.equals("") && SysConst.CHANGECHARSET == true)
		{
			BalanceState = StrTool.unicodeToGBK(BalanceState);
		}
		return BalanceState;
	}
	/** 对账状态 */
	public void setBalanceState(String aBalanceState)
	{
		BalanceState = aBalanceState;
	}
	/** 状态<P>状态<br>C-当日撤单 */
	public String getState()
	{
		if (State != null && !State.equals("") && SysConst.CHANGECHARSET == true)
		{
			State = StrTool.unicodeToGBK(State);
		}
		return State;
	}
	/** 状态 */
	public void setState(String aState)
	{
		State = aState;
	}
	/** 初审人<P>初审人 */
	public String getFirstTrialOperator()
	{
		if (FirstTrialOperator != null && !FirstTrialOperator.equals("") && SysConst.CHANGECHARSET == true)
		{
			FirstTrialOperator = StrTool.unicodeToGBK(FirstTrialOperator);
		}
		return FirstTrialOperator;
	}
	/** 初审人 */
	public void setFirstTrialOperator(String aFirstTrialOperator)
	{
		FirstTrialOperator = aFirstTrialOperator;
	}
	/** 初审日期<P>初审日期 */
	public String getFirstTrialDate()
	{
		if( FirstTrialDate != null )
			return fDate.getString(FirstTrialDate);
		else
			return null;
	}
	/** 初审日期 */
	public void setFirstTrialDate(Date aFirstTrialDate)
	{
		FirstTrialDate = aFirstTrialDate;
	}
	/** 初审日期<P>初审日期 */
	public void setFirstTrialDate(String aFirstTrialDate)
	{
		if (aFirstTrialDate != null && !aFirstTrialDate.equals("") )
		{
			FirstTrialDate = fDate.getDate( aFirstTrialDate );
		}
		else
			FirstTrialDate = null;
	}

	/** 初审时间<P>初审时间 */
	public String getFirstTrialTime()
	{
		if (FirstTrialTime != null && !FirstTrialTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			FirstTrialTime = StrTool.unicodeToGBK(FirstTrialTime);
		}
		return FirstTrialTime;
	}
	/** 初审时间 */
	public void setFirstTrialTime(String aFirstTrialTime)
	{
		FirstTrialTime = aFirstTrialTime;
	}
	/** 收单人<P>收单人 */
	public String getReceiveOperator()
	{
		if (ReceiveOperator != null && !ReceiveOperator.equals("") && SysConst.CHANGECHARSET == true)
		{
			ReceiveOperator = StrTool.unicodeToGBK(ReceiveOperator);
		}
		return ReceiveOperator;
	}
	/** 收单人 */
	public void setReceiveOperator(String aReceiveOperator)
	{
		ReceiveOperator = aReceiveOperator;
	}
	/** 收单日期<P>收单日期 */
	public String getReceiveDate()
	{
		if( ReceiveDate != null )
			return fDate.getString(ReceiveDate);
		else
			return null;
	}
	/** 收单日期 */
	public void setReceiveDate(Date aReceiveDate)
	{
		ReceiveDate = aReceiveDate;
	}
	/** 收单日期<P>收单日期 */
	public void setReceiveDate(String aReceiveDate)
	{
		if (aReceiveDate != null && !aReceiveDate.equals("") )
		{
			ReceiveDate = fDate.getDate( aReceiveDate );
		}
		else
			ReceiveDate = null;
	}

	/** 收单时间<P>收单时间 */
	public String getReceiveTime()
	{
		if (ReceiveTime != null && !ReceiveTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ReceiveTime = StrTool.unicodeToGBK(ReceiveTime);
		}
		return ReceiveTime;
	}
	/** 收单时间 */
	public void setReceiveTime(String aReceiveTime)
	{
		ReceiveTime = aReceiveTime;
	}
	/** 暂收据号<P>暂收据号 */
	public String getTempFeeNo()
	{
		if (TempFeeNo != null && !TempFeeNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TempFeeNo = StrTool.unicodeToGBK(TempFeeNo);
		}
		return TempFeeNo;
	}
	/** 暂收据号 */
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	/** Selltype<P>销售方式 */
	public String getSellType()
	{
		if (SellType != null && !SellType.equals("") && SysConst.CHANGECHARSET == true)
		{
			SellType = StrTool.unicodeToGBK(SellType);
		}
		return SellType;
	}
	/** Selltype */
	public void setSellType(String aSellType)
	{
		SellType = aSellType;
	}
	/** 强制人工核保原因<P>强制人工核保原因 */
	public String getForceUWReason()
	{
		if (ForceUWReason != null && !ForceUWReason.equals("") && SysConst.CHANGECHARSET == true)
		{
			ForceUWReason = StrTool.unicodeToGBK(ForceUWReason);
		}
		return ForceUWReason;
	}
	/** 强制人工核保原因 */
	public void setForceUWReason(String aForceUWReason)
	{
		ForceUWReason = aForceUWReason;
	}
	/** 首期银行编码<P>首期银行编码 */
	public String getNewBankCode()
	{
		if (NewBankCode != null && !NewBankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			NewBankCode = StrTool.unicodeToGBK(NewBankCode);
		}
		return NewBankCode;
	}
	/** 首期银行编码 */
	public void setNewBankCode(String aNewBankCode)
	{
		NewBankCode = aNewBankCode;
	}
	/** 首期银行帐号<P>首期银行帐号 */
	public String getNewBankAccNo()
	{
		if (NewBankAccNo != null && !NewBankAccNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			NewBankAccNo = StrTool.unicodeToGBK(NewBankAccNo);
		}
		return NewBankAccNo;
	}
	/** 首期银行帐号 */
	public void setNewBankAccNo(String aNewBankAccNo)
	{
		NewBankAccNo = aNewBankAccNo;
	}
	/** 首期银行帐户名<P>首期银行帐户名 */
	public String getNewAccName()
	{
		if (NewAccName != null && !NewAccName.equals("") && SysConst.CHANGECHARSET == true)
		{
			NewAccName = StrTool.unicodeToGBK(NewAccName);
		}
		return NewAccName;
	}
	/** 首期银行帐户名 */
	public void setNewAccName(String aNewAccName)
	{
		NewAccName = aNewAccName;
	}
	/** 首期交费方式<P>首期交费方式 */
	public String getNewPayMode()
	{
		if (NewPayMode != null && !NewPayMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			NewPayMode = StrTool.unicodeToGBK(NewPayMode);
		}
		return NewPayMode;
	}
	/** 首期交费方式 */
	public void setNewPayMode(String aNewPayMode)
	{
		NewPayMode = aNewPayMode;
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

	/** 入机时间<P> */
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
	/** 最后一次修改日期<P> */
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
	/** 最后一次修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后一次修改时间<P> */
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

	/**
	* 使用另外一个 LBContSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LBContSchema aLBContSchema)
	{
		this.GrpContNo = aLBContSchema.getGrpContNo();
		this.ContNo = aLBContSchema.getContNo();
		this.ProposalContNo = aLBContSchema.getProposalContNo();
		this.PrtNo = aLBContSchema.getPrtNo();
		this.ContType = aLBContSchema.getContType();
		this.PolType = aLBContSchema.getPolType();
		this.SaleChnl = aLBContSchema.getSaleChnl();
		this.AXAAgentCode = aLBContSchema.getAXAAgentCode();
		this.AXAAgentCom = aLBContSchema.getAXAAgentCom();
		this.AXANodeMap = aLBContSchema.getAXANodeMap();
		this.ManageCom = aLBContSchema.getManageCom();
		this.AgentCom = aLBContSchema.getAgentCom();
		this.AgentComName = aLBContSchema.getAgentComName();
		this.AgentName = aLBContSchema.getAgentName();
		this.AgentCode = aLBContSchema.getAgentCode();
		this.AppntNo = aLBContSchema.getAppntNo();
		this.AppntName = aLBContSchema.getAppntName();
		this.AppntSex = aLBContSchema.getAppntSex();
		this.AppntBirthday = fDate.getDate( aLBContSchema.getAppntBirthday());
		this.AppntIDType = aLBContSchema.getAppntIDType();
		this.AppntIDNo = aLBContSchema.getAppntIDNo();
		this.InsuredNo = aLBContSchema.getInsuredNo();
		this.InsuredName = aLBContSchema.getInsuredName();
		this.InsuredSex = aLBContSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLBContSchema.getInsuredBirthday());
		this.InsuredIDType = aLBContSchema.getInsuredIDType();
		this.InsuredIDNo = aLBContSchema.getInsuredIDNo();
		this.PayIntv = aLBContSchema.getPayIntv();
		this.PayMode = aLBContSchema.getPayMode();
		this.PayEndYearFlag = aLBContSchema.getPayEndYearFlag();
		this.PayEndYear = aLBContSchema.getPayEndYear();
		this.OutPayFlag = aLBContSchema.getOutPayFlag();
		this.GetPolMode = aLBContSchema.getGetPolMode();
		this.BankCode = aLBContSchema.getBankCode();
		this.BankAccNo = aLBContSchema.getBankAccNo();
		this.AccName = aLBContSchema.getAccName();
		this.PrintCount = aLBContSchema.getPrintCount();
		this.Lang = aLBContSchema.getLang();
		this.Mult = aLBContSchema.getMult();
		this.Prem = aLBContSchema.getPrem();
		this.Amnt = aLBContSchema.getAmnt();
		this.FirstPrem = aLBContSchema.getFirstPrem();
		this.MainPolPrem = aLBContSchema.getMainPolPrem();
		this.FirstAddPrem = aLBContSchema.getFirstAddPrem();
		this.AddPrem = aLBContSchema.getAddPrem();
		this.PolApplyDate = fDate.getDate( aLBContSchema.getPolApplyDate());
		this.CValiDate = fDate.getDate( aLBContSchema.getCValiDate());
		this.SignCom = aLBContSchema.getSignCom();
		this.SignDate = fDate.getDate( aLBContSchema.getSignDate());
		this.AppFlag = aLBContSchema.getAppFlag();
		this.AgentBankCode = aLBContSchema.getAgentBankCode();
		this.BankAgent = aLBContSchema.getBankAgent();
		this.ForceUWFlag = aLBContSchema.getForceUWFlag();
		this.InputOperator = aLBContSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLBContSchema.getInputDate());
		this.InputTime = aLBContSchema.getInputTime();
		this.FamilyType = aLBContSchema.getFamilyType();
		this.FamilyID = aLBContSchema.getFamilyID();
		this.CardFlag = aLBContSchema.getCardFlag();
		this.ExecuteCom = aLBContSchema.getExecuteCom();
		this.AgentGroup = aLBContSchema.getAgentGroup();
		this.AgentCode1 = aLBContSchema.getAgentCode1();
		this.AgentType = aLBContSchema.getAgentType();
		this.Handler = aLBContSchema.getHandler();
		this.Password = aLBContSchema.getPassword();
		this.PayLocation = aLBContSchema.getPayLocation();
		this.DisputedFlag = aLBContSchema.getDisputedFlag();
		this.SignTime = aLBContSchema.getSignTime();
		this.ConsignNo = aLBContSchema.getConsignNo();
		this.LostTimes = aLBContSchema.getLostTimes();
		this.Currency = aLBContSchema.getCurrency();
		this.Remark = aLBContSchema.getRemark();
		this.Peoples = aLBContSchema.getPeoples();
		this.SumPrem = aLBContSchema.getSumPrem();
		this.Dif = aLBContSchema.getDif();
		this.PaytoDate = fDate.getDate( aLBContSchema.getPaytoDate());
		this.FirstPayDate = fDate.getDate( aLBContSchema.getFirstPayDate());
		this.ApproveFlag = aLBContSchema.getApproveFlag();
		this.ApproveCode = aLBContSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLBContSchema.getApproveDate());
		this.ApproveTime = aLBContSchema.getApproveTime();
		this.UWFlag = aLBContSchema.getUWFlag();
		this.UWOperator = aLBContSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLBContSchema.getUWDate());
		this.UWTime = aLBContSchema.getUWTime();
		this.GetPolDate = fDate.getDate( aLBContSchema.getGetPolDate());
		this.GetPolTime = aLBContSchema.getGetPolTime();
		this.CustomGetPolDate = fDate.getDate( aLBContSchema.getCustomGetPolDate());
		this.BalanceState = aLBContSchema.getBalanceState();
		this.State = aLBContSchema.getState();
		this.FirstTrialOperator = aLBContSchema.getFirstTrialOperator();
		this.FirstTrialDate = fDate.getDate( aLBContSchema.getFirstTrialDate());
		this.FirstTrialTime = aLBContSchema.getFirstTrialTime();
		this.ReceiveOperator = aLBContSchema.getReceiveOperator();
		this.ReceiveDate = fDate.getDate( aLBContSchema.getReceiveDate());
		this.ReceiveTime = aLBContSchema.getReceiveTime();
		this.TempFeeNo = aLBContSchema.getTempFeeNo();
		this.SellType = aLBContSchema.getSellType();
		this.ForceUWReason = aLBContSchema.getForceUWReason();
		this.NewBankCode = aLBContSchema.getNewBankCode();
		this.NewBankAccNo = aLBContSchema.getNewBankAccNo();
		this.NewAccName = aLBContSchema.getNewAccName();
		this.NewPayMode = aLBContSchema.getNewPayMode();
		this.Operator = aLBContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBContSchema.getMakeDate());
		this.MakeTime = aLBContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBContSchema.getModifyDate());
		this.ModifyTime = aLBContSchema.getModifyTime();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("AXAAgentCode") == null )
				this.AXAAgentCode = null;
			else
				this.AXAAgentCode = rs.getString("AXAAgentCode").trim();

			if( rs.getString("AXAAgentCom") == null )
				this.AXAAgentCom = null;
			else
				this.AXAAgentCom = rs.getString("AXAAgentCom").trim();

			if( rs.getString("AXANodeMap") == null )
				this.AXANodeMap = null;
			else
				this.AXANodeMap = rs.getString("AXANodeMap").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getDate("AppntBirthday");
			if( rs.getString("AppntIDType") == null )
				this.AppntIDType = null;
			else
				this.AppntIDType = rs.getString("AppntIDType").trim();

			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

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
			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("OutPayFlag") == null )
				this.OutPayFlag = null;
			else
				this.OutPayFlag = rs.getString("OutPayFlag").trim();

			if( rs.getString("GetPolMode") == null )
				this.GetPolMode = null;
			else
				this.GetPolMode = rs.getString("GetPolMode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.PrintCount = rs.getInt("PrintCount");
			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.FirstPrem = rs.getDouble("FirstPrem");
			this.MainPolPrem = rs.getDouble("MainPolPrem");
			this.FirstAddPrem = rs.getDouble("FirstAddPrem");
			this.AddPrem = rs.getDouble("AddPrem");
			this.PolApplyDate = rs.getDate("PolApplyDate");
			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("AgentBankCode") == null )
				this.AgentBankCode = null;
			else
				this.AgentBankCode = rs.getString("AgentBankCode").trim();

			if( rs.getString("BankAgent") == null )
				this.BankAgent = null;
			else
				this.BankAgent = rs.getString("BankAgent").trim();

			if( rs.getString("ForceUWFlag") == null )
				this.ForceUWFlag = null;
			else
				this.ForceUWFlag = rs.getString("ForceUWFlag").trim();

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("FamilyType") == null )
				this.FamilyType = null;
			else
				this.FamilyType = rs.getString("FamilyType").trim();

			if( rs.getString("FamilyID") == null )
				this.FamilyID = null;
			else
				this.FamilyID = rs.getString("FamilyID").trim();

			if( rs.getString("CardFlag") == null )
				this.CardFlag = null;
			else
				this.CardFlag = rs.getString("CardFlag").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("PayLocation") == null )
				this.PayLocation = null;
			else
				this.PayLocation = rs.getString("PayLocation").trim();

			if( rs.getString("DisputedFlag") == null )
				this.DisputedFlag = null;
			else
				this.DisputedFlag = rs.getString("DisputedFlag").trim();

			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			if( rs.getString("ConsignNo") == null )
				this.ConsignNo = null;
			else
				this.ConsignNo = rs.getString("ConsignNo").trim();

			this.LostTimes = rs.getInt("LostTimes");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.Peoples = rs.getInt("Peoples");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Dif = rs.getDouble("Dif");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
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

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			if( rs.getString("BalanceState") == null )
				this.BalanceState = null;
			else
				this.BalanceState = rs.getString("BalanceState").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("FirstTrialOperator") == null )
				this.FirstTrialOperator = null;
			else
				this.FirstTrialOperator = rs.getString("FirstTrialOperator").trim();

			this.FirstTrialDate = rs.getDate("FirstTrialDate");
			if( rs.getString("FirstTrialTime") == null )
				this.FirstTrialTime = null;
			else
				this.FirstTrialTime = rs.getString("FirstTrialTime").trim();

			if( rs.getString("ReceiveOperator") == null )
				this.ReceiveOperator = null;
			else
				this.ReceiveOperator = rs.getString("ReceiveOperator").trim();

			this.ReceiveDate = rs.getDate("ReceiveDate");
			if( rs.getString("ReceiveTime") == null )
				this.ReceiveTime = null;
			else
				this.ReceiveTime = rs.getString("ReceiveTime").trim();

			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("SellType") == null )
				this.SellType = null;
			else
				this.SellType = rs.getString("SellType").trim();

			if( rs.getString("ForceUWReason") == null )
				this.ForceUWReason = null;
			else
				this.ForceUWReason = rs.getString("ForceUWReason").trim();

			if( rs.getString("NewBankCode") == null )
				this.NewBankCode = null;
			else
				this.NewBankCode = rs.getString("NewBankCode").trim();

			if( rs.getString("NewBankAccNo") == null )
				this.NewBankAccNo = null;
			else
				this.NewBankAccNo = rs.getString("NewBankAccNo").trim();

			if( rs.getString("NewAccName") == null )
				this.NewAccName = null;
			else
				this.NewAccName = rs.getString("NewAccName").trim();

			if( rs.getString("NewPayMode") == null )
				this.NewPayMode = null;
			else
				this.NewPayMode = rs.getString("NewPayMode").trim();

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

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LBContSchema getSchema()
	{
		LBContSchema aLBContSchema = new LBContSchema();
		aLBContSchema.setSchema(this);
		return aLBContSchema;
	}

	public LBContDB getDB()
	{
		LBContDB aDBOper = new LBContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCont描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(GrpContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SaleChnl) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXAAgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXAAgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXANodeMap) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntSex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AppntBirthday )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredSex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( InsuredBirthday )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayIntv) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutPayFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetPolMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PrintCount) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Lang) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Mult) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Prem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Amnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FirstPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MainPolPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FirstAddPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AddPrem) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PolApplyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( CValiDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SignCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( SignDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentBankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankAgent) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ForceUWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InputOperator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( InputDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InputTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FamilyType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FamilyID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CardFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ExecuteCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGroup) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Handler) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Password) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayLocation) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DisputedFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SignTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ConsignNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LostTimes) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Currency) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Peoples) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(SumPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Dif) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PaytoDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( FirstPayDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ApproveDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApproveTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWOperator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( UWDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( GetPolDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetPolTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( CustomGetPolDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BalanceState) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(State) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FirstTrialOperator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( FirstTrialDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FirstTrialTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ReceiveOperator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ReceiveDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ReceiveTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TempFeeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SellType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ForceUWReason) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NewBankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NewBankAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NewAccName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NewPayMode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AXAAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AXAAgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AXANodeMap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AppntBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			AppntIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).intValue();
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			FirstPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			MainPolPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			FirstAddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			AddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47,SysConst.PACKAGESPILTER));
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			AgentBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			BankAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ForceUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			FamilyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			CardFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			ConsignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).intValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).intValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,74,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76,SysConst.PACKAGESPILTER));
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77,SysConst.PACKAGESPILTER));
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88,SysConst.PACKAGESPILTER));
			BalanceState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			FirstTrialDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92,SysConst.PACKAGESPILTER));
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95,SysConst.PACKAGESPILTER));
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			SellType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			ForceUWReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			NewBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			NewBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			NewAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			NewPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBContSchema";
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
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equals("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equals("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equals("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equals("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equals("AXAAgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXAAgentCode));
		}
		if (FCode.equals("AXAAgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXAAgentCom));
		}
		if (FCode.equals("AXANodeMap"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXANodeMap));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equals("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equals("AppntSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntSex));
		}
		if (FCode.equals("AppntBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
		}
		if (FCode.equals("AppntIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDType));
		}
		if (FCode.equals("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
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
		if (FCode.equals("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equals("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equals("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equals("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equals("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equals("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equals("OutPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayFlag));
		}
		if (FCode.equals("GetPolMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolMode));
		}
		if (FCode.equals("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equals("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equals("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equals("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equals("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equals("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equals("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equals("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equals("FirstPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPrem));
		}
		if (FCode.equals("MainPolPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolPrem));
		}
		if (FCode.equals("FirstAddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAddPrem));
		}
		if (FCode.equals("AddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPrem));
		}
		if (FCode.equals("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
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
		if (FCode.equals("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equals("AgentBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentBankCode));
		}
		if (FCode.equals("BankAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAgent));
		}
		if (FCode.equals("ForceUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWFlag));
		}
		if (FCode.equals("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equals("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equals("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
		}
		if (FCode.equals("FamilyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyType));
		}
		if (FCode.equals("FamilyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyID));
		}
		if (FCode.equals("CardFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardFlag));
		}
		if (FCode.equals("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equals("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equals("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
		}
		if (FCode.equals("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equals("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equals("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equals("PayLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLocation));
		}
		if (FCode.equals("DisputedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisputedFlag));
		}
		if (FCode.equals("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equals("ConsignNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsignNo));
		}
		if (FCode.equals("LostTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTimes));
		}
		if (FCode.equals("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equals("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equals("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equals("Dif"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dif));
		}
		if (FCode.equals("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equals("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
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
		if (FCode.equals("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equals("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equals("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equals("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equals("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equals("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equals("BalanceState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceState));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("FirstTrialOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialOperator));
		}
		if (FCode.equals("FirstTrialDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
		}
		if (FCode.equals("FirstTrialTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialTime));
		}
		if (FCode.equals("ReceiveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveOperator));
		}
		if (FCode.equals("ReceiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
		}
		if (FCode.equals("ReceiveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveTime));
		}
		if (FCode.equals("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equals("SellType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellType));
		}
		if (FCode.equals("ForceUWReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWReason));
		}
		if (FCode.equals("NewBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankCode));
		}
		if (FCode.equals("NewBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankAccNo));
		}
		if (FCode.equals("NewAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAccName));
		}
		if (FCode.equals("NewPayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewPayMode));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AXAAgentCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AXAAgentCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AXANodeMap);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AppntIDType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 27:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 30:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 36:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 38:
				strFieldValue = String.valueOf(Mult);
				break;
			case 39:
				strFieldValue = String.valueOf(Prem);
				break;
			case 40:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 41:
				strFieldValue = String.valueOf(FirstPrem);
				break;
			case 42:
				strFieldValue = String.valueOf(MainPolPrem);
				break;
			case 43:
				strFieldValue = String.valueOf(FirstAddPrem);
				break;
			case 44:
				strFieldValue = String.valueOf(AddPrem);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(AgentBankCode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(BankAgent);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(ForceUWFlag);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(FamilyType);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(CardFlag);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(ConsignNo);
				break;
			case 69:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 72:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 73:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 74:
				strFieldValue = String.valueOf(Dif);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(BalanceState);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(SellType);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(ForceUWReason);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(NewBankCode);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(NewBankAccNo);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(NewAccName);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(NewPayMode);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 107:
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

		if (FCode.equals("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equals("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equals("PolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolType = FValue.trim();
			}
			else
				PolType = null;
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
		if (FCode.equals("AXAAgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXAAgentCode = FValue.trim();
			}
			else
				AXAAgentCode = null;
		}
		if (FCode.equals("AXAAgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXAAgentCom = FValue.trim();
			}
			else
				AXAAgentCom = null;
		}
		if (FCode.equals("AXANodeMap"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXANodeMap = FValue.trim();
			}
			else
				AXANodeMap = null;
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
		if (FCode.equals("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
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
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		if (FCode.equals("AppntSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntSex = FValue.trim();
			}
			else
				AppntSex = null;
		}
		if (FCode.equals("AppntBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppntBirthday = fDate.getDate( FValue );
			}
			else
				AppntBirthday = null;
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
		if (FCode.equals("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
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
		if (FCode.equals("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
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
		if (FCode.equals("OutPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutPayFlag = FValue.trim();
			}
			else
				OutPayFlag = null;
		}
		if (FCode.equals("GetPolMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolMode = FValue.trim();
			}
			else
				GetPolMode = null;
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
		if (FCode.equals("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equals("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equals("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equals("Lang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Lang = FValue.trim();
			}
			else
				Lang = null;
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
		if (FCode.equals("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
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
		if (FCode.equals("FirstPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FirstPrem = d;
			}
		}
		if (FCode.equals("MainPolPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MainPolPrem = d;
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
		if (FCode.equals("AddPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPrem = d;
			}
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
		if (FCode.equals("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
		}
		if (FCode.equals("AgentBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentBankCode = FValue.trim();
			}
			else
				AgentBankCode = null;
		}
		if (FCode.equals("BankAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAgent = FValue.trim();
			}
			else
				BankAgent = null;
		}
		if (FCode.equals("ForceUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWFlag = FValue.trim();
			}
			else
				ForceUWFlag = null;
		}
		if (FCode.equals("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equals("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equals("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
		}
		if (FCode.equals("FamilyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyType = FValue.trim();
			}
			else
				FamilyType = null;
		}
		if (FCode.equals("FamilyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyID = FValue.trim();
			}
			else
				FamilyID = null;
		}
		if (FCode.equals("CardFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardFlag = FValue.trim();
			}
			else
				CardFlag = null;
		}
		if (FCode.equals("ExecuteCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExecuteCom = FValue.trim();
			}
			else
				ExecuteCom = null;
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
		if (FCode.equals("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
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
		if (FCode.equals("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equals("DisputedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisputedFlag = FValue.trim();
			}
			else
				DisputedFlag = null;
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
		if (FCode.equals("ConsignNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsignNo = FValue.trim();
			}
			else
				ConsignNo = null;
		}
		if (FCode.equals("LostTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LostTimes = i;
			}
		}
		if (FCode.equals("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		if (FCode.equals("Peoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples = i;
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
		if (FCode.equals("Dif"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Dif = d;
			}
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
		if (FCode.equals("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
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
		if (FCode.equals("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
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
		if (FCode.equals("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equals("GetPolTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolTime = FValue.trim();
			}
			else
				GetPolTime = null;
		}
		if (FCode.equals("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
		}
		if (FCode.equals("BalanceState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceState = FValue.trim();
			}
			else
				BalanceState = null;
		}
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equals("FirstTrialOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialOperator = FValue.trim();
			}
			else
				FirstTrialOperator = null;
		}
		if (FCode.equals("FirstTrialDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstTrialDate = fDate.getDate( FValue );
			}
			else
				FirstTrialDate = null;
		}
		if (FCode.equals("FirstTrialTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialTime = FValue.trim();
			}
			else
				FirstTrialTime = null;
		}
		if (FCode.equals("ReceiveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveOperator = FValue.trim();
			}
			else
				ReceiveOperator = null;
		}
		if (FCode.equals("ReceiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReceiveDate = fDate.getDate( FValue );
			}
			else
				ReceiveDate = null;
		}
		if (FCode.equals("ReceiveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveTime = FValue.trim();
			}
			else
				ReceiveTime = null;
		}
		if (FCode.equals("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
		}
		if (FCode.equals("SellType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellType = FValue.trim();
			}
			else
				SellType = null;
		}
		if (FCode.equals("ForceUWReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWReason = FValue.trim();
			}
			else
				ForceUWReason = null;
		}
		if (FCode.equals("NewBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankCode = FValue.trim();
			}
			else
				NewBankCode = null;
		}
		if (FCode.equals("NewBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankAccNo = FValue.trim();
			}
			else
				NewBankAccNo = null;
		}
		if (FCode.equals("NewAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAccName = FValue.trim();
			}
			else
				NewAccName = null;
		}
		if (FCode.equals("NewPayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewPayMode = FValue.trim();
			}
			else
				NewPayMode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBContSchema other = (LBContSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ContType.equals(other.getContType())
			&& PolType.equals(other.getPolType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AXAAgentCode.equals(other.getAXAAgentCode())
			&& AXAAgentCom.equals(other.getAXAAgentCom())
			&& AXANodeMap.equals(other.getAXANodeMap())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentComName.equals(other.getAgentComName())
			&& AgentName.equals(other.getAgentName())
			&& AgentCode.equals(other.getAgentCode())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& fDate.getString(AppntBirthday).equals(other.getAppntBirthday())
			&& AppntIDType.equals(other.getAppntIDType())
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& GetPolMode.equals(other.getGetPolMode())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PrintCount == other.getPrintCount()
			&& Lang.equals(other.getLang())
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& FirstPrem == other.getFirstPrem()
			&& MainPolPrem == other.getMainPolPrem()
			&& FirstAddPrem == other.getFirstAddPrem()
			&& AddPrem == other.getAddPrem()
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& AppFlag.equals(other.getAppFlag())
			&& AgentBankCode.equals(other.getAgentBankCode())
			&& BankAgent.equals(other.getBankAgent())
			&& ForceUWFlag.equals(other.getForceUWFlag())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& FamilyType.equals(other.getFamilyType())
			&& FamilyID.equals(other.getFamilyID())
			&& CardFlag.equals(other.getCardFlag())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& AgentType.equals(other.getAgentType())
			&& Handler.equals(other.getHandler())
			&& Password.equals(other.getPassword())
			&& PayLocation.equals(other.getPayLocation())
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& SignTime.equals(other.getSignTime())
			&& ConsignNo.equals(other.getConsignNo())
			&& LostTimes == other.getLostTimes()
			&& Currency.equals(other.getCurrency())
			&& Remark.equals(other.getRemark())
			&& Peoples == other.getPeoples()
			&& SumPrem == other.getSumPrem()
			&& Dif == other.getDif()
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& BalanceState.equals(other.getBalanceState())
			&& State.equals(other.getState())
			&& FirstTrialOperator.equals(other.getFirstTrialOperator())
			&& fDate.getString(FirstTrialDate).equals(other.getFirstTrialDate())
			&& FirstTrialTime.equals(other.getFirstTrialTime())
			&& ReceiveOperator.equals(other.getReceiveOperator())
			&& fDate.getString(ReceiveDate).equals(other.getReceiveDate())
			&& ReceiveTime.equals(other.getReceiveTime())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& SellType.equals(other.getSellType())
			&& ForceUWReason.equals(other.getForceUWReason())
			&& NewBankCode.equals(other.getNewBankCode())
			&& NewBankAccNo.equals(other.getNewBankAccNo())
			&& NewAccName.equals(other.getNewAccName())
			&& NewPayMode.equals(other.getNewPayMode())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContType") ) {
			return 4;
		}
		if( strFieldName.equals("PolType") ) {
			return 5;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 6;
		}
		if( strFieldName.equals("AXAAgentCode") ) {
			return 7;
		}
		if( strFieldName.equals("AXAAgentCom") ) {
			return 8;
		}
		if( strFieldName.equals("AXANodeMap") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 11;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 12;
		}
		if( strFieldName.equals("AgentName") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 14;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 15;
		}
		if( strFieldName.equals("AppntName") ) {
			return 16;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 17;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 18;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return 19;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 20;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 21;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 22;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 23;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 26;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 27;
		}
		if( strFieldName.equals("PayMode") ) {
			return 28;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 29;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 30;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 31;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 32;
		}
		if( strFieldName.equals("BankCode") ) {
			return 33;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 34;
		}
		if( strFieldName.equals("AccName") ) {
			return 35;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 36;
		}
		if( strFieldName.equals("Lang") ) {
			return 37;
		}
		if( strFieldName.equals("Mult") ) {
			return 38;
		}
		if( strFieldName.equals("Prem") ) {
			return 39;
		}
		if( strFieldName.equals("Amnt") ) {
			return 40;
		}
		if( strFieldName.equals("FirstPrem") ) {
			return 41;
		}
		if( strFieldName.equals("MainPolPrem") ) {
			return 42;
		}
		if( strFieldName.equals("FirstAddPrem") ) {
			return 43;
		}
		if( strFieldName.equals("AddPrem") ) {
			return 44;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 45;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 46;
		}
		if( strFieldName.equals("SignCom") ) {
			return 47;
		}
		if( strFieldName.equals("SignDate") ) {
			return 48;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 49;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return 50;
		}
		if( strFieldName.equals("BankAgent") ) {
			return 51;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return 52;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 53;
		}
		if( strFieldName.equals("InputDate") ) {
			return 54;
		}
		if( strFieldName.equals("InputTime") ) {
			return 55;
		}
		if( strFieldName.equals("FamilyType") ) {
			return 56;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 57;
		}
		if( strFieldName.equals("CardFlag") ) {
			return 58;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 59;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 60;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 61;
		}
		if( strFieldName.equals("AgentType") ) {
			return 62;
		}
		if( strFieldName.equals("Handler") ) {
			return 63;
		}
		if( strFieldName.equals("Password") ) {
			return 64;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 65;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 66;
		}
		if( strFieldName.equals("SignTime") ) {
			return 67;
		}
		if( strFieldName.equals("ConsignNo") ) {
			return 68;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 69;
		}
		if( strFieldName.equals("Currency") ) {
			return 70;
		}
		if( strFieldName.equals("Remark") ) {
			return 71;
		}
		if( strFieldName.equals("Peoples") ) {
			return 72;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 73;
		}
		if( strFieldName.equals("Dif") ) {
			return 74;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 75;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 76;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 77;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 78;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 79;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 80;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 81;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 82;
		}
		if( strFieldName.equals("UWDate") ) {
			return 83;
		}
		if( strFieldName.equals("UWTime") ) {
			return 84;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 85;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 86;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 87;
		}
		if( strFieldName.equals("BalanceState") ) {
			return 88;
		}
		if( strFieldName.equals("State") ) {
			return 89;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 90;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 91;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 92;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 93;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 94;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 95;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 96;
		}
		if( strFieldName.equals("SellType") ) {
			return 97;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return 98;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return 99;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return 100;
		}
		if( strFieldName.equals("NewAccName") ) {
			return 101;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return 102;
		}
		if( strFieldName.equals("Operator") ) {
			return 103;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 104;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 105;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 106;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 107;
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
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "ProposalContNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "ContType";
				break;
			case 5:
				strFieldName = "PolType";
				break;
			case 6:
				strFieldName = "SaleChnl";
				break;
			case 7:
				strFieldName = "AXAAgentCode";
				break;
			case 8:
				strFieldName = "AXAAgentCom";
				break;
			case 9:
				strFieldName = "AXANodeMap";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "AgentCom";
				break;
			case 12:
				strFieldName = "AgentComName";
				break;
			case 13:
				strFieldName = "AgentName";
				break;
			case 14:
				strFieldName = "AgentCode";
				break;
			case 15:
				strFieldName = "AppntNo";
				break;
			case 16:
				strFieldName = "AppntName";
				break;
			case 17:
				strFieldName = "AppntSex";
				break;
			case 18:
				strFieldName = "AppntBirthday";
				break;
			case 19:
				strFieldName = "AppntIDType";
				break;
			case 20:
				strFieldName = "AppntIDNo";
				break;
			case 21:
				strFieldName = "InsuredNo";
				break;
			case 22:
				strFieldName = "InsuredName";
				break;
			case 23:
				strFieldName = "InsuredSex";
				break;
			case 24:
				strFieldName = "InsuredBirthday";
				break;
			case 25:
				strFieldName = "InsuredIDType";
				break;
			case 26:
				strFieldName = "InsuredIDNo";
				break;
			case 27:
				strFieldName = "PayIntv";
				break;
			case 28:
				strFieldName = "PayMode";
				break;
			case 29:
				strFieldName = "PayEndYearFlag";
				break;
			case 30:
				strFieldName = "PayEndYear";
				break;
			case 31:
				strFieldName = "OutPayFlag";
				break;
			case 32:
				strFieldName = "GetPolMode";
				break;
			case 33:
				strFieldName = "BankCode";
				break;
			case 34:
				strFieldName = "BankAccNo";
				break;
			case 35:
				strFieldName = "AccName";
				break;
			case 36:
				strFieldName = "PrintCount";
				break;
			case 37:
				strFieldName = "Lang";
				break;
			case 38:
				strFieldName = "Mult";
				break;
			case 39:
				strFieldName = "Prem";
				break;
			case 40:
				strFieldName = "Amnt";
				break;
			case 41:
				strFieldName = "FirstPrem";
				break;
			case 42:
				strFieldName = "MainPolPrem";
				break;
			case 43:
				strFieldName = "FirstAddPrem";
				break;
			case 44:
				strFieldName = "AddPrem";
				break;
			case 45:
				strFieldName = "PolApplyDate";
				break;
			case 46:
				strFieldName = "CValiDate";
				break;
			case 47:
				strFieldName = "SignCom";
				break;
			case 48:
				strFieldName = "SignDate";
				break;
			case 49:
				strFieldName = "AppFlag";
				break;
			case 50:
				strFieldName = "AgentBankCode";
				break;
			case 51:
				strFieldName = "BankAgent";
				break;
			case 52:
				strFieldName = "ForceUWFlag";
				break;
			case 53:
				strFieldName = "InputOperator";
				break;
			case 54:
				strFieldName = "InputDate";
				break;
			case 55:
				strFieldName = "InputTime";
				break;
			case 56:
				strFieldName = "FamilyType";
				break;
			case 57:
				strFieldName = "FamilyID";
				break;
			case 58:
				strFieldName = "CardFlag";
				break;
			case 59:
				strFieldName = "ExecuteCom";
				break;
			case 60:
				strFieldName = "AgentGroup";
				break;
			case 61:
				strFieldName = "AgentCode1";
				break;
			case 62:
				strFieldName = "AgentType";
				break;
			case 63:
				strFieldName = "Handler";
				break;
			case 64:
				strFieldName = "Password";
				break;
			case 65:
				strFieldName = "PayLocation";
				break;
			case 66:
				strFieldName = "DisputedFlag";
				break;
			case 67:
				strFieldName = "SignTime";
				break;
			case 68:
				strFieldName = "ConsignNo";
				break;
			case 69:
				strFieldName = "LostTimes";
				break;
			case 70:
				strFieldName = "Currency";
				break;
			case 71:
				strFieldName = "Remark";
				break;
			case 72:
				strFieldName = "Peoples";
				break;
			case 73:
				strFieldName = "SumPrem";
				break;
			case 74:
				strFieldName = "Dif";
				break;
			case 75:
				strFieldName = "PaytoDate";
				break;
			case 76:
				strFieldName = "FirstPayDate";
				break;
			case 77:
				strFieldName = "ApproveFlag";
				break;
			case 78:
				strFieldName = "ApproveCode";
				break;
			case 79:
				strFieldName = "ApproveDate";
				break;
			case 80:
				strFieldName = "ApproveTime";
				break;
			case 81:
				strFieldName = "UWFlag";
				break;
			case 82:
				strFieldName = "UWOperator";
				break;
			case 83:
				strFieldName = "UWDate";
				break;
			case 84:
				strFieldName = "UWTime";
				break;
			case 85:
				strFieldName = "GetPolDate";
				break;
			case 86:
				strFieldName = "GetPolTime";
				break;
			case 87:
				strFieldName = "CustomGetPolDate";
				break;
			case 88:
				strFieldName = "BalanceState";
				break;
			case 89:
				strFieldName = "State";
				break;
			case 90:
				strFieldName = "FirstTrialOperator";
				break;
			case 91:
				strFieldName = "FirstTrialDate";
				break;
			case 92:
				strFieldName = "FirstTrialTime";
				break;
			case 93:
				strFieldName = "ReceiveOperator";
				break;
			case 94:
				strFieldName = "ReceiveDate";
				break;
			case 95:
				strFieldName = "ReceiveTime";
				break;
			case 96:
				strFieldName = "TempFeeNo";
				break;
			case 97:
				strFieldName = "SellType";
				break;
			case 98:
				strFieldName = "ForceUWReason";
				break;
			case 99:
				strFieldName = "NewBankCode";
				break;
			case 100:
				strFieldName = "NewBankAccNo";
				break;
			case 101:
				strFieldName = "NewAccName";
				break;
			case 102:
				strFieldName = "NewPayMode";
				break;
			case 103:
				strFieldName = "Operator";
				break;
			case 104:
				strFieldName = "MakeDate";
				break;
			case 105:
				strFieldName = "MakeTime";
				break;
			case 106:
				strFieldName = "ModifyDate";
				break;
			case 107:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXAAgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXAAgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXANodeMap") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDNo") ) {
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
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FirstPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainPolPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FirstAddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConsignNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalanceState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SellType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewPayMode") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 46:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_INT;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_INT;
				break;
			case 73:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 76:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
