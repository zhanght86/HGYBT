/*
 * <p>ClassName: LCInsureAccSchema </p>
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
import com.sinosoft.lis.db.LCInsureAccDB;

public class LCInsureAccSchema implements Schema
{
	// @Field
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 合同号码 */
	private String ContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 保险账户代码 */
	private String AccCode;
	/** 保险账户姓名 */
	private String AccName;
	/** 保险账户比率 */
	private double AccRate;
	/** 投资日期 */
	private String AccTimeFlag;
	/** 险种编码 */
	private String RiskCode;
	/** 保单险种号码 */
	private String PolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 账户类型 */
	private String AccType;
	/** 投资类型 */
	private String InvestType;
	/** 基金公司代码 */
	private String FundCompanyCode;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 账户所有者 */
	private String Owner;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 账户成立日期 */
	private Date AccFoundDate;
	/** 账户成立时间 */
	private String AccFoundTime;
	/** 结算日期 */
	private Date BalaDate;
	/** 结算时间 */
	private String BalaTime;
	/** 累计交费 */
	private double SumPay;
	/** 累计领取 */
	private double SumPaym;
	/** 期初账户现金余额 */
	private double LastAccBala;
	/** 期初账户单位数 */
	private double LastUnitCount;
	/** 期初账户单位价格 */
	private double LastUnitPrice;
	/** 帐户现金余额 */
	private double InsuAccBala;
	/** 帐户单位数 */
	private double UnitCount;
	/** 帐户单位价格 */
	private double UnitPrice;
	/** 账户可领金额 */
	private double InsuAccGetMoney;
	/** 冻结金额 */
	private double FrozenMoney;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
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

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCInsureAccSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "InsuAccNo";
		pk[1] = "ContNo";
		pk[2] = "AccCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 保险帐户号码<P>保险帐户号码 */
	public String getInsuAccNo()
	{
		if (InsuAccNo != null && !InsuAccNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuAccNo = StrTool.unicodeToGBK(InsuAccNo);
		}
		return InsuAccNo;
	}
	/** 保险帐户号码 */
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
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
	/** 保险账户代码<P>保险账户代码 */
	public String getAccCode()
	{
		if (AccCode != null && !AccCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccCode = StrTool.unicodeToGBK(AccCode);
		}
		return AccCode;
	}
	/** 保险账户代码 */
	public void setAccCode(String aAccCode)
	{
		AccCode = aAccCode;
	}
	/** 保险账户姓名<P>保险账户姓名 */
	public String getAccName()
	{
		if (AccName != null && !AccName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccName = StrTool.unicodeToGBK(AccName);
		}
		return AccName;
	}
	/** 保险账户姓名 */
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	/** 保险账户比率<P>保险账户比率 */
	public double getAccRate()
	{
		return AccRate;
	}
	/** 保险账户比率 */
	public void setAccRate(double aAccRate)
	{
		AccRate = aAccRate;
	}
	/** 保险账户比率<P>保险账户比率 */
	public void setAccRate(String aAccRate)
	{
		if (aAccRate != null && !aAccRate.equals(""))
		{
			Double tDouble = new Double(aAccRate);
			double d = tDouble.doubleValue();
			AccRate = d;
		}
	}

	/** 投资日期<P>投资日期 */
	public String getAccTimeFlag()
	{
		if (AccTimeFlag != null && !AccTimeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccTimeFlag = StrTool.unicodeToGBK(AccTimeFlag);
		}
		return AccTimeFlag;
	}
	/** 投资日期 */
	public void setAccTimeFlag(String aAccTimeFlag)
	{
		AccTimeFlag = aAccTimeFlag;
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
	/** 集体合同号码<P>集体合同号码--暂不启用 */
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
	/** 集体保单险种号码<P>--暂不启用 */
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
	/** 账户类型<P>--暂不启用<br> */
	public String getAccType()
	{
		if (AccType != null && !AccType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccType = StrTool.unicodeToGBK(AccType);
		}
		return AccType;
	}
	/** 账户类型 */
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	/** 投资类型<P>--暂不启用<br> */
	public String getInvestType()
	{
		if (InvestType != null && !InvestType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InvestType = StrTool.unicodeToGBK(InvestType);
		}
		return InvestType;
	}
	/** 投资类型 */
	public void setInvestType(String aInvestType)
	{
		InvestType = aInvestType;
	}
	/** 基金公司代码<P>--暂不启用 */
	public String getFundCompanyCode()
	{
		if (FundCompanyCode != null && !FundCompanyCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundCompanyCode = StrTool.unicodeToGBK(FundCompanyCode);
		}
		return FundCompanyCode;
	}
	/** 基金公司代码 */
	public void setFundCompanyCode(String aFundCompanyCode)
	{
		FundCompanyCode = aFundCompanyCode;
	}
	/** 被保人客户号码<P>--暂不启用 */
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
	/** 投保人客户号码<P>--暂不启用 */
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
	/** 账户所有者<P>--暂不启用 */
	public String getOwner()
	{
		if (Owner != null && !Owner.equals("") && SysConst.CHANGECHARSET == true)
		{
			Owner = StrTool.unicodeToGBK(Owner);
		}
		return Owner;
	}
	/** 账户所有者 */
	public void setOwner(String aOwner)
	{
		Owner = aOwner;
	}
	/** 账户结算方式<P>--暂不启用<br><br> */
	public String getAccComputeFlag()
	{
		if (AccComputeFlag != null && !AccComputeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccComputeFlag = StrTool.unicodeToGBK(AccComputeFlag);
		}
		return AccComputeFlag;
	}
	/** 账户结算方式 */
	public void setAccComputeFlag(String aAccComputeFlag)
	{
		AccComputeFlag = aAccComputeFlag;
	}
	/** 账户成立日期<P>--暂不启用 */
	public String getAccFoundDate()
	{
		if( AccFoundDate != null )
			return fDate.getString(AccFoundDate);
		else
			return null;
	}
	/** 账户成立日期 */
	public void setAccFoundDate(Date aAccFoundDate)
	{
		AccFoundDate = aAccFoundDate;
	}
	/** 账户成立日期<P>--暂不启用 */
	public void setAccFoundDate(String aAccFoundDate)
	{
		if (aAccFoundDate != null && !aAccFoundDate.equals("") )
		{
			AccFoundDate = fDate.getDate( aAccFoundDate );
		}
		else
			AccFoundDate = null;
	}

	/** 账户成立时间<P>-暂不启用 */
	public String getAccFoundTime()
	{
		if (AccFoundTime != null && !AccFoundTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccFoundTime = StrTool.unicodeToGBK(AccFoundTime);
		}
		return AccFoundTime;
	}
	/** 账户成立时间 */
	public void setAccFoundTime(String aAccFoundTime)
	{
		AccFoundTime = aAccFoundTime;
	}
	/** 结算日期<P>暂不启用 */
	public String getBalaDate()
	{
		if( BalaDate != null )
			return fDate.getString(BalaDate);
		else
			return null;
	}
	/** 结算日期 */
	public void setBalaDate(Date aBalaDate)
	{
		BalaDate = aBalaDate;
	}
	/** 结算日期<P>暂不启用 */
	public void setBalaDate(String aBalaDate)
	{
		if (aBalaDate != null && !aBalaDate.equals("") )
		{
			BalaDate = fDate.getDate( aBalaDate );
		}
		else
			BalaDate = null;
	}

	/** 结算时间<P>账户成立时间 */
	public String getBalaTime()
	{
		if (BalaTime != null && !BalaTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			BalaTime = StrTool.unicodeToGBK(BalaTime);
		}
		return BalaTime;
	}
	/** 结算时间 */
	public void setBalaTime(String aBalaTime)
	{
		BalaTime = aBalaTime;
	}
	/** 累计交费<P>账户成立时间 */
	public double getSumPay()
	{
		return SumPay;
	}
	/** 累计交费 */
	public void setSumPay(double aSumPay)
	{
		SumPay = aSumPay;
	}
	/** 累计交费<P>账户成立时间 */
	public void setSumPay(String aSumPay)
	{
		if (aSumPay != null && !aSumPay.equals(""))
		{
			Double tDouble = new Double(aSumPay);
			double d = tDouble.doubleValue();
			SumPay = d;
		}
	}

	/** 累计领取<P>账户成立时间 */
	public double getSumPaym()
	{
		return SumPaym;
	}
	/** 累计领取 */
	public void setSumPaym(double aSumPaym)
	{
		SumPaym = aSumPaym;
	}
	/** 累计领取<P>账户成立时间 */
	public void setSumPaym(String aSumPaym)
	{
		if (aSumPaym != null && !aSumPaym.equals(""))
		{
			Double tDouble = new Double(aSumPaym);
			double d = tDouble.doubleValue();
			SumPaym = d;
		}
	}

	/** 期初账户现金余额<P>账户成立时间 */
	public double getLastAccBala()
	{
		return LastAccBala;
	}
	/** 期初账户现金余额 */
	public void setLastAccBala(double aLastAccBala)
	{
		LastAccBala = aLastAccBala;
	}
	/** 期初账户现金余额<P>账户成立时间 */
	public void setLastAccBala(String aLastAccBala)
	{
		if (aLastAccBala != null && !aLastAccBala.equals(""))
		{
			Double tDouble = new Double(aLastAccBala);
			double d = tDouble.doubleValue();
			LastAccBala = d;
		}
	}

	/** 期初账户单位数<P>账户成立时间 */
	public double getLastUnitCount()
	{
		return LastUnitCount;
	}
	/** 期初账户单位数 */
	public void setLastUnitCount(double aLastUnitCount)
	{
		LastUnitCount = aLastUnitCount;
	}
	/** 期初账户单位数<P>账户成立时间 */
	public void setLastUnitCount(String aLastUnitCount)
	{
		if (aLastUnitCount != null && !aLastUnitCount.equals(""))
		{
			Double tDouble = new Double(aLastUnitCount);
			double d = tDouble.doubleValue();
			LastUnitCount = d;
		}
	}

	/** 期初账户单位价格<P>账户成立时间 */
	public double getLastUnitPrice()
	{
		return LastUnitPrice;
	}
	/** 期初账户单位价格 */
	public void setLastUnitPrice(double aLastUnitPrice)
	{
		LastUnitPrice = aLastUnitPrice;
	}
	/** 期初账户单位价格<P>账户成立时间 */
	public void setLastUnitPrice(String aLastUnitPrice)
	{
		if (aLastUnitPrice != null && !aLastUnitPrice.equals(""))
		{
			Double tDouble = new Double(aLastUnitPrice);
			double d = tDouble.doubleValue();
			LastUnitPrice = d;
		}
	}

	/** 帐户现金余额<P>账户成立时间 */
	public double getInsuAccBala()
	{
		return InsuAccBala;
	}
	/** 帐户现金余额 */
	public void setInsuAccBala(double aInsuAccBala)
	{
		InsuAccBala = aInsuAccBala;
	}
	/** 帐户现金余额<P>账户成立时间 */
	public void setInsuAccBala(String aInsuAccBala)
	{
		if (aInsuAccBala != null && !aInsuAccBala.equals(""))
		{
			Double tDouble = new Double(aInsuAccBala);
			double d = tDouble.doubleValue();
			InsuAccBala = d;
		}
	}

	/** 帐户单位数<P>账户成立时间 */
	public double getUnitCount()
	{
		return UnitCount;
	}
	/** 帐户单位数 */
	public void setUnitCount(double aUnitCount)
	{
		UnitCount = aUnitCount;
	}
	/** 帐户单位数<P>账户成立时间 */
	public void setUnitCount(String aUnitCount)
	{
		if (aUnitCount != null && !aUnitCount.equals(""))
		{
			Double tDouble = new Double(aUnitCount);
			double d = tDouble.doubleValue();
			UnitCount = d;
		}
	}

	/** 帐户单位价格<P>账户成立时间 */
	public double getUnitPrice()
	{
		return UnitPrice;
	}
	/** 帐户单位价格 */
	public void setUnitPrice(double aUnitPrice)
	{
		UnitPrice = aUnitPrice;
	}
	/** 帐户单位价格<P>账户成立时间 */
	public void setUnitPrice(String aUnitPrice)
	{
		if (aUnitPrice != null && !aUnitPrice.equals(""))
		{
			Double tDouble = new Double(aUnitPrice);
			double d = tDouble.doubleValue();
			UnitPrice = d;
		}
	}

	/** 账户可领金额<P>账户成立时间 */
	public double getInsuAccGetMoney()
	{
		return InsuAccGetMoney;
	}
	/** 账户可领金额 */
	public void setInsuAccGetMoney(double aInsuAccGetMoney)
	{
		InsuAccGetMoney = aInsuAccGetMoney;
	}
	/** 账户可领金额<P>账户成立时间 */
	public void setInsuAccGetMoney(String aInsuAccGetMoney)
	{
		if (aInsuAccGetMoney != null && !aInsuAccGetMoney.equals(""))
		{
			Double tDouble = new Double(aInsuAccGetMoney);
			double d = tDouble.doubleValue();
			InsuAccGetMoney = d;
		}
	}

	/** 冻结金额<P>账户成立时间 */
	public double getFrozenMoney()
	{
		return FrozenMoney;
	}
	/** 冻结金额 */
	public void setFrozenMoney(double aFrozenMoney)
	{
		FrozenMoney = aFrozenMoney;
	}
	/** 冻结金额<P>账户成立时间 */
	public void setFrozenMoney(String aFrozenMoney)
	{
		if (aFrozenMoney != null && !aFrozenMoney.equals(""))
		{
			Double tDouble = new Double(aFrozenMoney);
			double d = tDouble.doubleValue();
			FrozenMoney = d;
		}
	}

	/** 状态<P>账户成立时间 */
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
	/** 管理机构<P>账户成立时间 */
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
	/** 操作员<P> */
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
	/** 入机日期<P> */
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
	/** 入机日期<P> */
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
	* 使用另外一个 LCInsureAccSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LCInsureAccSchema aLCInsureAccSchema)
	{
		this.InsuAccNo = aLCInsureAccSchema.getInsuAccNo();
		this.ContNo = aLCInsureAccSchema.getContNo();
		this.PrtNo = aLCInsureAccSchema.getPrtNo();
		this.AccCode = aLCInsureAccSchema.getAccCode();
		this.AccName = aLCInsureAccSchema.getAccName();
		this.AccRate = aLCInsureAccSchema.getAccRate();
		this.AccTimeFlag = aLCInsureAccSchema.getAccTimeFlag();
		this.RiskCode = aLCInsureAccSchema.getRiskCode();
		this.PolNo = aLCInsureAccSchema.getPolNo();
		this.GrpContNo = aLCInsureAccSchema.getGrpContNo();
		this.GrpPolNo = aLCInsureAccSchema.getGrpPolNo();
		this.AccType = aLCInsureAccSchema.getAccType();
		this.InvestType = aLCInsureAccSchema.getInvestType();
		this.FundCompanyCode = aLCInsureAccSchema.getFundCompanyCode();
		this.InsuredNo = aLCInsureAccSchema.getInsuredNo();
		this.AppntNo = aLCInsureAccSchema.getAppntNo();
		this.Owner = aLCInsureAccSchema.getOwner();
		this.AccComputeFlag = aLCInsureAccSchema.getAccComputeFlag();
		this.AccFoundDate = fDate.getDate( aLCInsureAccSchema.getAccFoundDate());
		this.AccFoundTime = aLCInsureAccSchema.getAccFoundTime();
		this.BalaDate = fDate.getDate( aLCInsureAccSchema.getBalaDate());
		this.BalaTime = aLCInsureAccSchema.getBalaTime();
		this.SumPay = aLCInsureAccSchema.getSumPay();
		this.SumPaym = aLCInsureAccSchema.getSumPaym();
		this.LastAccBala = aLCInsureAccSchema.getLastAccBala();
		this.LastUnitCount = aLCInsureAccSchema.getLastUnitCount();
		this.LastUnitPrice = aLCInsureAccSchema.getLastUnitPrice();
		this.InsuAccBala = aLCInsureAccSchema.getInsuAccBala();
		this.UnitCount = aLCInsureAccSchema.getUnitCount();
		this.UnitPrice = aLCInsureAccSchema.getUnitPrice();
		this.InsuAccGetMoney = aLCInsureAccSchema.getInsuAccGetMoney();
		this.FrozenMoney = aLCInsureAccSchema.getFrozenMoney();
		this.State = aLCInsureAccSchema.getState();
		this.ManageCom = aLCInsureAccSchema.getManageCom();
		this.Operator = aLCInsureAccSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCInsureAccSchema.getMakeDate());
		this.MakeTime = aLCInsureAccSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCInsureAccSchema.getModifyDate());
		this.ModifyTime = aLCInsureAccSchema.getModifyTime();
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
			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("AccCode") == null )
				this.AccCode = null;
			else
				this.AccCode = rs.getString("AccCode").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.AccRate = rs.getDouble("AccRate");
			if( rs.getString("AccTimeFlag") == null )
				this.AccTimeFlag = null;
			else
				this.AccTimeFlag = rs.getString("AccTimeFlag").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("FundCompanyCode") == null )
				this.FundCompanyCode = null;
			else
				this.FundCompanyCode = rs.getString("FundCompanyCode").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("Owner") == null )
				this.Owner = null;
			else
				this.Owner = rs.getString("Owner").trim();

			if( rs.getString("AccComputeFlag") == null )
				this.AccComputeFlag = null;
			else
				this.AccComputeFlag = rs.getString("AccComputeFlag").trim();

			this.AccFoundDate = rs.getDate("AccFoundDate");
			if( rs.getString("AccFoundTime") == null )
				this.AccFoundTime = null;
			else
				this.AccFoundTime = rs.getString("AccFoundTime").trim();

			this.BalaDate = rs.getDate("BalaDate");
			if( rs.getString("BalaTime") == null )
				this.BalaTime = null;
			else
				this.BalaTime = rs.getString("BalaTime").trim();

			this.SumPay = rs.getDouble("SumPay");
			this.SumPaym = rs.getDouble("SumPaym");
			this.LastAccBala = rs.getDouble("LastAccBala");
			this.LastUnitCount = rs.getDouble("LastUnitCount");
			this.LastUnitPrice = rs.getDouble("LastUnitPrice");
			this.InsuAccBala = rs.getDouble("InsuAccBala");
			this.UnitCount = rs.getDouble("UnitCount");
			this.UnitPrice = rs.getDouble("UnitPrice");
			this.InsuAccGetMoney = rs.getDouble("InsuAccGetMoney");
			this.FrozenMoney = rs.getDouble("FrozenMoney");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			tError.moduleName = "LCInsureAccSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LCInsureAccSchema getSchema()
	{
		LCInsureAccSchema aLCInsureAccSchema = new LCInsureAccSchema();
		aLCInsureAccSchema.setSchema(this);
		return aLCInsureAccSchema;
	}

	public LCInsureAccDB getDB()
	{
		LCInsureAccDB aDBOper = new LCInsureAccDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAcc描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(InsuAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AccRate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccTimeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpPolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InvestType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundCompanyCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Owner) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccComputeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AccFoundDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccFoundTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( BalaDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BalaTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(SumPay) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(SumPaym) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LastAccBala) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LastUnitCount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LastUnitPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuAccBala) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UnitCount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UnitPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuAccGetMoney) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FrozenMoney) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(State) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAcc>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AccTimeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			FundCompanyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Owner = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AccFoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			AccFoundTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BalaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			BalaTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			SumPaym = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			LastAccBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			LastUnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			LastUnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			InsuAccBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			UnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			UnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			InsuAccGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			FrozenMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsureAccSchema";
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
		if (FCode.equals("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equals("AccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCode));
		}
		if (FCode.equals("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equals("AccRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccRate));
		}
		if (FCode.equals("AccTimeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccTimeFlag));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equals("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equals("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equals("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equals("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equals("FundCompanyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCompanyCode));
		}
		if (FCode.equals("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("Owner"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Owner));
		}
		if (FCode.equals("AccComputeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccComputeFlag));
		}
		if (FCode.equals("AccFoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccFoundDate()));
		}
		if (FCode.equals("AccFoundTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccFoundTime));
		}
		if (FCode.equals("BalaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
		}
		if (FCode.equals("BalaTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalaTime));
		}
		if (FCode.equals("SumPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPay));
		}
		if (FCode.equals("SumPaym"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPaym));
		}
		if (FCode.equals("LastAccBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastAccBala));
		}
		if (FCode.equals("LastUnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastUnitCount));
		}
		if (FCode.equals("LastUnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastUnitPrice));
		}
		if (FCode.equals("InsuAccBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccBala));
		}
		if (FCode.equals("UnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitCount));
		}
		if (FCode.equals("UnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitPrice));
		}
		if (FCode.equals("InsuAccGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccGetMoney));
		}
		if (FCode.equals("FrozenMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FrozenMoney));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 5:
				strFieldValue = String.valueOf(AccRate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccTimeFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(FundCompanyCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Owner);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccFoundDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AccFoundTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BalaTime);
				break;
			case 22:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 23:
				strFieldValue = String.valueOf(SumPaym);
				break;
			case 24:
				strFieldValue = String.valueOf(LastAccBala);
				break;
			case 25:
				strFieldValue = String.valueOf(LastUnitCount);
				break;
			case 26:
				strFieldValue = String.valueOf(LastUnitPrice);
				break;
			case 27:
				strFieldValue = String.valueOf(InsuAccBala);
				break;
			case 28:
				strFieldValue = String.valueOf(UnitCount);
				break;
			case 29:
				strFieldValue = String.valueOf(UnitPrice);
				break;
			case 30:
				strFieldValue = String.valueOf(InsuAccGetMoney);
				break;
			case 31:
				strFieldValue = String.valueOf(FrozenMoney);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 38:
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

		if (FCode.equals("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equals("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equals("AccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCode = FValue.trim();
			}
			else
				AccCode = null;
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
		if (FCode.equals("AccRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccRate = d;
			}
		}
		if (FCode.equals("AccTimeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccTimeFlag = FValue.trim();
			}
			else
				AccTimeFlag = null;
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
		if (FCode.equals("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
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
		if (FCode.equals("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equals("InvestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestType = FValue.trim();
			}
			else
				InvestType = null;
		}
		if (FCode.equals("FundCompanyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundCompanyCode = FValue.trim();
			}
			else
				FundCompanyCode = null;
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
		if (FCode.equals("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equals("Owner"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Owner = FValue.trim();
			}
			else
				Owner = null;
		}
		if (FCode.equals("AccComputeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccComputeFlag = FValue.trim();
			}
			else
				AccComputeFlag = null;
		}
		if (FCode.equals("AccFoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccFoundDate = fDate.getDate( FValue );
			}
			else
				AccFoundDate = null;
		}
		if (FCode.equals("AccFoundTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccFoundTime = FValue.trim();
			}
			else
				AccFoundTime = null;
		}
		if (FCode.equals("BalaDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalaDate = fDate.getDate( FValue );
			}
			else
				BalaDate = null;
		}
		if (FCode.equals("BalaTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalaTime = FValue.trim();
			}
			else
				BalaTime = null;
		}
		if (FCode.equals("SumPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPay = d;
			}
		}
		if (FCode.equals("SumPaym"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPaym = d;
			}
		}
		if (FCode.equals("LastAccBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastAccBala = d;
			}
		}
		if (FCode.equals("LastUnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastUnitCount = d;
			}
		}
		if (FCode.equals("LastUnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastUnitPrice = d;
			}
		}
		if (FCode.equals("InsuAccBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuAccBala = d;
			}
		}
		if (FCode.equals("UnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitCount = d;
			}
		}
		if (FCode.equals("UnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitPrice = d;
			}
		}
		if (FCode.equals("InsuAccGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuAccGetMoney = d;
			}
		}
		if (FCode.equals("FrozenMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FrozenMoney = d;
			}
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
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		LCInsureAccSchema other = (LCInsureAccSchema)otherObject;
		return
			InsuAccNo.equals(other.getInsuAccNo())
			&& ContNo.equals(other.getContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& AccCode.equals(other.getAccCode())
			&& AccName.equals(other.getAccName())
			&& AccRate == other.getAccRate()
			&& AccTimeFlag.equals(other.getAccTimeFlag())
			&& RiskCode.equals(other.getRiskCode())
			&& PolNo.equals(other.getPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& AccType.equals(other.getAccType())
			&& InvestType.equals(other.getInvestType())
			&& FundCompanyCode.equals(other.getFundCompanyCode())
			&& InsuredNo.equals(other.getInsuredNo())
			&& AppntNo.equals(other.getAppntNo())
			&& Owner.equals(other.getOwner())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& fDate.getString(AccFoundDate).equals(other.getAccFoundDate())
			&& AccFoundTime.equals(other.getAccFoundTime())
			&& fDate.getString(BalaDate).equals(other.getBalaDate())
			&& BalaTime.equals(other.getBalaTime())
			&& SumPay == other.getSumPay()
			&& SumPaym == other.getSumPaym()
			&& LastAccBala == other.getLastAccBala()
			&& LastUnitCount == other.getLastUnitCount()
			&& LastUnitPrice == other.getLastUnitPrice()
			&& InsuAccBala == other.getInsuAccBala()
			&& UnitCount == other.getUnitCount()
			&& UnitPrice == other.getUnitPrice()
			&& InsuAccGetMoney == other.getInsuAccGetMoney()
			&& FrozenMoney == other.getFrozenMoney()
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 2;
		}
		if( strFieldName.equals("AccCode") ) {
			return 3;
		}
		if( strFieldName.equals("AccName") ) {
			return 4;
		}
		if( strFieldName.equals("AccRate") ) {
			return 5;
		}
		if( strFieldName.equals("AccTimeFlag") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("PolNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 10;
		}
		if( strFieldName.equals("AccType") ) {
			return 11;
		}
		if( strFieldName.equals("InvestType") ) {
			return 12;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return 13;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 14;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 15;
		}
		if( strFieldName.equals("Owner") ) {
			return 16;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 17;
		}
		if( strFieldName.equals("AccFoundDate") ) {
			return 18;
		}
		if( strFieldName.equals("AccFoundTime") ) {
			return 19;
		}
		if( strFieldName.equals("BalaDate") ) {
			return 20;
		}
		if( strFieldName.equals("BalaTime") ) {
			return 21;
		}
		if( strFieldName.equals("SumPay") ) {
			return 22;
		}
		if( strFieldName.equals("SumPaym") ) {
			return 23;
		}
		if( strFieldName.equals("LastAccBala") ) {
			return 24;
		}
		if( strFieldName.equals("LastUnitCount") ) {
			return 25;
		}
		if( strFieldName.equals("LastUnitPrice") ) {
			return 26;
		}
		if( strFieldName.equals("InsuAccBala") ) {
			return 27;
		}
		if( strFieldName.equals("UnitCount") ) {
			return 28;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return 29;
		}
		if( strFieldName.equals("InsuAccGetMoney") ) {
			return 30;
		}
		if( strFieldName.equals("FrozenMoney") ) {
			return 31;
		}
		if( strFieldName.equals("State") ) {
			return 32;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 35;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 38;
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
				strFieldName = "InsuAccNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "PrtNo";
				break;
			case 3:
				strFieldName = "AccCode";
				break;
			case 4:
				strFieldName = "AccName";
				break;
			case 5:
				strFieldName = "AccRate";
				break;
			case 6:
				strFieldName = "AccTimeFlag";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "PolNo";
				break;
			case 9:
				strFieldName = "GrpContNo";
				break;
			case 10:
				strFieldName = "GrpPolNo";
				break;
			case 11:
				strFieldName = "AccType";
				break;
			case 12:
				strFieldName = "InvestType";
				break;
			case 13:
				strFieldName = "FundCompanyCode";
				break;
			case 14:
				strFieldName = "InsuredNo";
				break;
			case 15:
				strFieldName = "AppntNo";
				break;
			case 16:
				strFieldName = "Owner";
				break;
			case 17:
				strFieldName = "AccComputeFlag";
				break;
			case 18:
				strFieldName = "AccFoundDate";
				break;
			case 19:
				strFieldName = "AccFoundTime";
				break;
			case 20:
				strFieldName = "BalaDate";
				break;
			case 21:
				strFieldName = "BalaTime";
				break;
			case 22:
				strFieldName = "SumPay";
				break;
			case 23:
				strFieldName = "SumPaym";
				break;
			case 24:
				strFieldName = "LastAccBala";
				break;
			case 25:
				strFieldName = "LastUnitCount";
				break;
			case 26:
				strFieldName = "LastUnitPrice";
				break;
			case 27:
				strFieldName = "InsuAccBala";
				break;
			case 28:
				strFieldName = "UnitCount";
				break;
			case 29:
				strFieldName = "UnitPrice";
				break;
			case 30:
				strFieldName = "InsuAccGetMoney";
				break;
			case 31:
				strFieldName = "FrozenMoney";
				break;
			case 32:
				strFieldName = "State";
				break;
			case 33:
				strFieldName = "ManageCom";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "MakeDate";
				break;
			case 36:
				strFieldName = "MakeTime";
				break;
			case 37:
				strFieldName = "ModifyDate";
				break;
			case 38:
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccTimeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Owner") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccFoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccFoundTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalaTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPaym") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastAccBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastUnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastUnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuAccBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuAccGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FrozenMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
