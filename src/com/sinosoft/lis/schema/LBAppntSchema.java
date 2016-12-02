/*
 * <p>ClassName: LBAppntSchema </p>
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
import com.sinosoft.lis.db.LBAppntDB;

public class LBAppntSchema implements Schema
{
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人级别 */
	private String AppntGrade;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private Date AppntBirthday;
	/** 投保人类型 */
	private String AppntType;
	/** 客户地址号码 */
	private String AddressNo;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 国籍 */
	private String NativePlace;
	/** 民族 */
	private String Nationality;
	/** 户口所在地 */
	private String RgtAddress;
	/** 婚姻状况 */
	private String Marriage;
	/** 结婚日期 */
	private Date MarriageDate;
	/** 健康状况 */
	private String Health;
	/** 身高 */
	private double Stature;
	/** 体重 */
	private double Avoirdupois;
	/** 学历 */
	private String Degree;
	/** 信用等级 */
	private String CreditGrade;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 入司日期 */
	private Date JoinCompanyDate;
	/** 参加工作日期 */
	private Date StartWorkDate;
	/** 职位 */
	private String Position;
	/** 工资 */
	private double Salary;
	/** 职业类别 */
	private String OccupationType;
	/** 职业代码 */
	private String OccupationCode;
	/** 职业（工种） */
	private String WorkType;
	/** 兼职（工种） */
	private String PluralityType;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 身体指标 */
	private double BMI;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;

	public static final int FIELDNUM = 43;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBAppntSchema()
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

	/** 集体合同号码<P>集体合同号码 */
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
	/** 投保人级别<P>投保人级别 */
	public String getAppntGrade()
	{
		if (AppntGrade != null && !AppntGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntGrade = StrTool.unicodeToGBK(AppntGrade);
		}
		return AppntGrade;
	}
	/** 投保人级别 */
	public void setAppntGrade(String aAppntGrade)
	{
		AppntGrade = aAppntGrade;
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

	/** 投保人类型<P>投保人类型 */
	public String getAppntType()
	{
		if (AppntType != null && !AppntType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntType = StrTool.unicodeToGBK(AppntType);
		}
		return AppntType;
	}
	/** 投保人类型 */
	public void setAppntType(String aAppntType)
	{
		AppntType = aAppntType;
	}
	/** 客户地址号码<P>客户地址号码 */
	public String getAddressNo()
	{
		if (AddressNo != null && !AddressNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddressNo = StrTool.unicodeToGBK(AddressNo);
		}
		return AddressNo;
	}
	/** 客户地址号码 */
	public void setAddressNo(String aAddressNo)
	{
		AddressNo = aAddressNo;
	}
	/** 证件类型<P>证件类型 0-身份证 1-护照 2-军官证 3-士兵证 4-回乡证 5-临时身份证 */
	public String getIDType()
	{
		if (IDType != null && !IDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			IDType = StrTool.unicodeToGBK(IDType);
		}
		return IDType;
	}
	/** 证件类型 */
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	/** 证件号码<P>证件号码 */
	public String getIDNo()
	{
		if (IDNo != null && !IDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			IDNo = StrTool.unicodeToGBK(IDNo);
		}
		return IDNo;
	}
	/** 证件号码 */
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	/** 国籍<P>国籍 */
	public String getNativePlace()
	{
		if (NativePlace != null && !NativePlace.equals("") && SysConst.CHANGECHARSET == true)
		{
			NativePlace = StrTool.unicodeToGBK(NativePlace);
		}
		return NativePlace;
	}
	/** 国籍 */
	public void setNativePlace(String aNativePlace)
	{
		NativePlace = aNativePlace;
	}
	/** 民族<P>民族 */
	public String getNationality()
	{
		if (Nationality != null && !Nationality.equals("") && SysConst.CHANGECHARSET == true)
		{
			Nationality = StrTool.unicodeToGBK(Nationality);
		}
		return Nationality;
	}
	/** 民族 */
	public void setNationality(String aNationality)
	{
		Nationality = aNationality;
	}
	/** 户口所在地<P>户口所在地 */
	public String getRgtAddress()
	{
		if (RgtAddress != null && !RgtAddress.equals("") && SysConst.CHANGECHARSET == true)
		{
			RgtAddress = StrTool.unicodeToGBK(RgtAddress);
		}
		return RgtAddress;
	}
	/** 户口所在地 */
	public void setRgtAddress(String aRgtAddress)
	{
		RgtAddress = aRgtAddress;
	}
	/** 婚姻状况<P>婚姻状况 */
	public String getMarriage()
	{
		if (Marriage != null && !Marriage.equals("") && SysConst.CHANGECHARSET == true)
		{
			Marriage = StrTool.unicodeToGBK(Marriage);
		}
		return Marriage;
	}
	/** 婚姻状况 */
	public void setMarriage(String aMarriage)
	{
		Marriage = aMarriage;
	}
	/** 结婚日期<P>结婚日期 */
	public String getMarriageDate()
	{
		if( MarriageDate != null )
			return fDate.getString(MarriageDate);
		else
			return null;
	}
	/** 结婚日期 */
	public void setMarriageDate(Date aMarriageDate)
	{
		MarriageDate = aMarriageDate;
	}
	/** 结婚日期<P>结婚日期 */
	public void setMarriageDate(String aMarriageDate)
	{
		if (aMarriageDate != null && !aMarriageDate.equals("") )
		{
			MarriageDate = fDate.getDate( aMarriageDate );
		}
		else
			MarriageDate = null;
	}

	/** 健康状况<P>健康状况 */
	public String getHealth()
	{
		if (Health != null && !Health.equals("") && SysConst.CHANGECHARSET == true)
		{
			Health = StrTool.unicodeToGBK(Health);
		}
		return Health;
	}
	/** 健康状况 */
	public void setHealth(String aHealth)
	{
		Health = aHealth;
	}
	/** 身高<P>身高 */
	public double getStature()
	{
		return Stature;
	}
	/** 身高 */
	public void setStature(double aStature)
	{
		Stature = aStature;
	}
	/** 身高<P>身高 */
	public void setStature(String aStature)
	{
		if (aStature != null && !aStature.equals(""))
		{
			Double tDouble = new Double(aStature);
			double d = tDouble.doubleValue();
			Stature = d;
		}
	}

	/** 体重<P>体重 */
	public double getAvoirdupois()
	{
		return Avoirdupois;
	}
	/** 体重 */
	public void setAvoirdupois(double aAvoirdupois)
	{
		Avoirdupois = aAvoirdupois;
	}
	/** 体重<P>体重 */
	public void setAvoirdupois(String aAvoirdupois)
	{
		if (aAvoirdupois != null && !aAvoirdupois.equals(""))
		{
			Double tDouble = new Double(aAvoirdupois);
			double d = tDouble.doubleValue();
			Avoirdupois = d;
		}
	}

	/** 学历<P>学历 */
	public String getDegree()
	{
		if (Degree != null && !Degree.equals("") && SysConst.CHANGECHARSET == true)
		{
			Degree = StrTool.unicodeToGBK(Degree);
		}
		return Degree;
	}
	/** 学历 */
	public void setDegree(String aDegree)
	{
		Degree = aDegree;
	}
	/** 信用等级<P>信用等级 */
	public String getCreditGrade()
	{
		if (CreditGrade != null && !CreditGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			CreditGrade = StrTool.unicodeToGBK(CreditGrade);
		}
		return CreditGrade;
	}
	/** 信用等级 */
	public void setCreditGrade(String aCreditGrade)
	{
		CreditGrade = aCreditGrade;
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
	/** 入司日期<P>入司日期 */
	public String getJoinCompanyDate()
	{
		if( JoinCompanyDate != null )
			return fDate.getString(JoinCompanyDate);
		else
			return null;
	}
	/** 入司日期 */
	public void setJoinCompanyDate(Date aJoinCompanyDate)
	{
		JoinCompanyDate = aJoinCompanyDate;
	}
	/** 入司日期<P>入司日期 */
	public void setJoinCompanyDate(String aJoinCompanyDate)
	{
		if (aJoinCompanyDate != null && !aJoinCompanyDate.equals("") )
		{
			JoinCompanyDate = fDate.getDate( aJoinCompanyDate );
		}
		else
			JoinCompanyDate = null;
	}

	/** 参加工作日期<P>参加工作日期 */
	public String getStartWorkDate()
	{
		if( StartWorkDate != null )
			return fDate.getString(StartWorkDate);
		else
			return null;
	}
	/** 参加工作日期 */
	public void setStartWorkDate(Date aStartWorkDate)
	{
		StartWorkDate = aStartWorkDate;
	}
	/** 参加工作日期<P>参加工作日期 */
	public void setStartWorkDate(String aStartWorkDate)
	{
		if (aStartWorkDate != null && !aStartWorkDate.equals("") )
		{
			StartWorkDate = fDate.getDate( aStartWorkDate );
		}
		else
			StartWorkDate = null;
	}

	/** 职位<P>职位 */
	public String getPosition()
	{
		if (Position != null && !Position.equals("") && SysConst.CHANGECHARSET == true)
		{
			Position = StrTool.unicodeToGBK(Position);
		}
		return Position;
	}
	/** 职位 */
	public void setPosition(String aPosition)
	{
		Position = aPosition;
	}
	/** 工资<P>工资 */
	public double getSalary()
	{
		return Salary;
	}
	/** 工资 */
	public void setSalary(double aSalary)
	{
		Salary = aSalary;
	}
	/** 工资<P>工资 */
	public void setSalary(String aSalary)
	{
		if (aSalary != null && !aSalary.equals(""))
		{
			Double tDouble = new Double(aSalary);
			double d = tDouble.doubleValue();
			Salary = d;
		}
	}

	/** 职业类别<P>职业类别 */
	public String getOccupationType()
	{
		if (OccupationType != null && !OccupationType.equals("") && SysConst.CHANGECHARSET == true)
		{
			OccupationType = StrTool.unicodeToGBK(OccupationType);
		}
		return OccupationType;
	}
	/** 职业类别 */
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	/** 职业代码<P>职业代码 */
	public String getOccupationCode()
	{
		if (OccupationCode != null && !OccupationCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			OccupationCode = StrTool.unicodeToGBK(OccupationCode);
		}
		return OccupationCode;
	}
	/** 职业代码 */
	public void setOccupationCode(String aOccupationCode)
	{
		OccupationCode = aOccupationCode;
	}
	/** 职业（工种）<P>职业（工种） */
	public String getWorkType()
	{
		if (WorkType != null && !WorkType.equals("") && SysConst.CHANGECHARSET == true)
		{
			WorkType = StrTool.unicodeToGBK(WorkType);
		}
		return WorkType;
	}
	/** 职业（工种） */
	public void setWorkType(String aWorkType)
	{
		WorkType = aWorkType;
	}
	/** 兼职（工种）<P>赞设为与被保人的关系 */
	public String getPluralityType()
	{
		if (PluralityType != null && !PluralityType.equals("") && SysConst.CHANGECHARSET == true)
		{
			PluralityType = StrTool.unicodeToGBK(PluralityType);
		}
		return PluralityType;
	}
	/** 兼职（工种） */
	public void setPluralityType(String aPluralityType)
	{
		PluralityType = aPluralityType;
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
	/** 管理机构<P> */
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
	/** 身体指标<P> */
	public double getBMI()
	{
		return BMI;
	}
	/** 身体指标 */
	public void setBMI(double aBMI)
	{
		BMI = aBMI;
	}
	/** 身体指标<P> */
	public void setBMI(String aBMI)
	{
		if (aBMI != null && !aBMI.equals(""))
		{
			Double tDouble = new Double(aBMI);
			double d = tDouble.doubleValue();
			BMI = d;
		}
	}

	/** 驾照<P> */
	public String getLicense()
	{
		if (License != null && !License.equals("") && SysConst.CHANGECHARSET == true)
		{
			License = StrTool.unicodeToGBK(License);
		}
		return License;
	}
	/** 驾照 */
	public void setLicense(String aLicense)
	{
		License = aLicense;
	}
	/** 驾照类型<P> */
	public String getLicenseType()
	{
		if (LicenseType != null && !LicenseType.equals("") && SysConst.CHANGECHARSET == true)
		{
			LicenseType = StrTool.unicodeToGBK(LicenseType);
		}
		return LicenseType;
	}
	/** 驾照类型 */
	public void setLicenseType(String aLicenseType)
	{
		LicenseType = aLicenseType;
	}

	/**
	* 使用另外一个 LBAppntSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LBAppntSchema aLBAppntSchema)
	{
		this.GrpContNo = aLBAppntSchema.getGrpContNo();
		this.ContNo = aLBAppntSchema.getContNo();
		this.PrtNo = aLBAppntSchema.getPrtNo();
		this.AppntNo = aLBAppntSchema.getAppntNo();
		this.AppntGrade = aLBAppntSchema.getAppntGrade();
		this.AppntName = aLBAppntSchema.getAppntName();
		this.AppntSex = aLBAppntSchema.getAppntSex();
		this.AppntBirthday = fDate.getDate( aLBAppntSchema.getAppntBirthday());
		this.AppntType = aLBAppntSchema.getAppntType();
		this.AddressNo = aLBAppntSchema.getAddressNo();
		this.IDType = aLBAppntSchema.getIDType();
		this.IDNo = aLBAppntSchema.getIDNo();
		this.NativePlace = aLBAppntSchema.getNativePlace();
		this.Nationality = aLBAppntSchema.getNationality();
		this.RgtAddress = aLBAppntSchema.getRgtAddress();
		this.Marriage = aLBAppntSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLBAppntSchema.getMarriageDate());
		this.Health = aLBAppntSchema.getHealth();
		this.Stature = aLBAppntSchema.getStature();
		this.Avoirdupois = aLBAppntSchema.getAvoirdupois();
		this.Degree = aLBAppntSchema.getDegree();
		this.CreditGrade = aLBAppntSchema.getCreditGrade();
		this.BankCode = aLBAppntSchema.getBankCode();
		this.BankAccNo = aLBAppntSchema.getBankAccNo();
		this.AccName = aLBAppntSchema.getAccName();
		this.JoinCompanyDate = fDate.getDate( aLBAppntSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLBAppntSchema.getStartWorkDate());
		this.Position = aLBAppntSchema.getPosition();
		this.Salary = aLBAppntSchema.getSalary();
		this.OccupationType = aLBAppntSchema.getOccupationType();
		this.OccupationCode = aLBAppntSchema.getOccupationCode();
		this.WorkType = aLBAppntSchema.getWorkType();
		this.PluralityType = aLBAppntSchema.getPluralityType();
		this.SmokeFlag = aLBAppntSchema.getSmokeFlag();
		this.Operator = aLBAppntSchema.getOperator();
		this.ManageCom = aLBAppntSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLBAppntSchema.getMakeDate());
		this.MakeTime = aLBAppntSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBAppntSchema.getModifyDate());
		this.ModifyTime = aLBAppntSchema.getModifyTime();
		this.BMI = aLBAppntSchema.getBMI();
		this.License = aLBAppntSchema.getLicense();
		this.LicenseType = aLBAppntSchema.getLicenseType();
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

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntGrade") == null )
				this.AppntGrade = null;
			else
				this.AppntGrade = rs.getString("AppntGrade").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getDate("AppntBirthday");
			if( rs.getString("AppntType") == null )
				this.AppntType = null;
			else
				this.AppntType = rs.getString("AppntType").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("NativePlace") == null )
				this.NativePlace = null;
			else
				this.NativePlace = rs.getString("NativePlace").trim();

			if( rs.getString("Nationality") == null )
				this.Nationality = null;
			else
				this.Nationality = rs.getString("Nationality").trim();

			if( rs.getString("RgtAddress") == null )
				this.RgtAddress = null;
			else
				this.RgtAddress = rs.getString("RgtAddress").trim();

			if( rs.getString("Marriage") == null )
				this.Marriage = null;
			else
				this.Marriage = rs.getString("Marriage").trim();

			this.MarriageDate = rs.getDate("MarriageDate");
			if( rs.getString("Health") == null )
				this.Health = null;
			else
				this.Health = rs.getString("Health").trim();

			this.Stature = rs.getDouble("Stature");
			this.Avoirdupois = rs.getDouble("Avoirdupois");
			if( rs.getString("Degree") == null )
				this.Degree = null;
			else
				this.Degree = rs.getString("Degree").trim();

			if( rs.getString("CreditGrade") == null )
				this.CreditGrade = null;
			else
				this.CreditGrade = rs.getString("CreditGrade").trim();

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

			this.JoinCompanyDate = rs.getDate("JoinCompanyDate");
			this.StartWorkDate = rs.getDate("StartWorkDate");
			if( rs.getString("Position") == null )
				this.Position = null;
			else
				this.Position = rs.getString("Position").trim();

			this.Salary = rs.getDouble("Salary");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("OccupationCode") == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString("OccupationCode").trim();

			if( rs.getString("WorkType") == null )
				this.WorkType = null;
			else
				this.WorkType = rs.getString("WorkType").trim();

			if( rs.getString("PluralityType") == null )
				this.PluralityType = null;
			else
				this.PluralityType = rs.getString("PluralityType").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			this.BMI = rs.getDouble("BMI");
			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBAppntSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LBAppntSchema getSchema()
	{
		LBAppntSchema aLBAppntSchema = new LBAppntSchema();
		aLBAppntSchema.setSchema(this);
		return aLBAppntSchema;
	}

	public LBAppntDB getDB()
	{
		LBAppntDB aDBOper = new LBAppntDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBAppnt描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(GrpContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntSex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AppntBirthday )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddressNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NativePlace) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Nationality) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RgtAddress) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Marriage) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MarriageDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Health) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Stature) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Avoirdupois) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Degree) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CreditGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( JoinCompanyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartWorkDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Position) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Salary) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OccupationType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OccupationCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WorkType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PluralityType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SmokeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BMI) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(License) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LicenseType) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBAppnt>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			AppntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBAppntSchema";
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
		if (FCode.equals("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("AppntGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntGrade));
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
		if (FCode.equals("AppntType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntType));
		}
		if (FCode.equals("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equals("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equals("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equals("NativePlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NativePlace));
		}
		if (FCode.equals("Nationality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Nationality));
		}
		if (FCode.equals("RgtAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtAddress));
		}
		if (FCode.equals("Marriage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Marriage));
		}
		if (FCode.equals("MarriageDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
		}
		if (FCode.equals("Health"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Health));
		}
		if (FCode.equals("Stature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Stature));
		}
		if (FCode.equals("Avoirdupois"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Avoirdupois));
		}
		if (FCode.equals("Degree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Degree));
		}
		if (FCode.equals("CreditGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreditGrade));
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
		if (FCode.equals("JoinCompanyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
		}
		if (FCode.equals("StartWorkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
		}
		if (FCode.equals("Position"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Position));
		}
		if (FCode.equals("Salary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salary));
		}
		if (FCode.equals("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equals("OccupationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationCode));
		}
		if (FCode.equals("WorkType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkType));
		}
		if (FCode.equals("PluralityType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PluralityType));
		}
		if (FCode.equals("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equals("BMI"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BMI));
		}
		if (FCode.equals("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equals("LicenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseType));
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
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AppntType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 18:
				strFieldValue = String.valueOf(Stature);
				break;
			case 19:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 28:
				strFieldValue = String.valueOf(Salary);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = String.valueOf(BMI);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
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
		if (FCode.equals("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equals("AppntGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntGrade = FValue.trim();
			}
			else
				AppntGrade = null;
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
		if (FCode.equals("AppntType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntType = FValue.trim();
			}
			else
				AppntType = null;
		}
		if (FCode.equals("AddressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddressNo = FValue.trim();
			}
			else
				AddressNo = null;
		}
		if (FCode.equals("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equals("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equals("NativePlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NativePlace = FValue.trim();
			}
			else
				NativePlace = null;
		}
		if (FCode.equals("Nationality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Nationality = FValue.trim();
			}
			else
				Nationality = null;
		}
		if (FCode.equals("RgtAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtAddress = FValue.trim();
			}
			else
				RgtAddress = null;
		}
		if (FCode.equals("Marriage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Marriage = FValue.trim();
			}
			else
				Marriage = null;
		}
		if (FCode.equals("MarriageDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MarriageDate = fDate.getDate( FValue );
			}
			else
				MarriageDate = null;
		}
		if (FCode.equals("Health"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Health = FValue.trim();
			}
			else
				Health = null;
		}
		if (FCode.equals("Stature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Stature = d;
			}
		}
		if (FCode.equals("Avoirdupois"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Avoirdupois = d;
			}
		}
		if (FCode.equals("Degree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Degree = FValue.trim();
			}
			else
				Degree = null;
		}
		if (FCode.equals("CreditGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreditGrade = FValue.trim();
			}
			else
				CreditGrade = null;
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
		if (FCode.equals("JoinCompanyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JoinCompanyDate = fDate.getDate( FValue );
			}
			else
				JoinCompanyDate = null;
		}
		if (FCode.equals("StartWorkDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartWorkDate = fDate.getDate( FValue );
			}
			else
				StartWorkDate = null;
		}
		if (FCode.equals("Position"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Position = FValue.trim();
			}
			else
				Position = null;
		}
		if (FCode.equals("Salary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Salary = d;
			}
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
		if (FCode.equals("OccupationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationCode = FValue.trim();
			}
			else
				OccupationCode = null;
		}
		if (FCode.equals("WorkType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkType = FValue.trim();
			}
			else
				WorkType = null;
		}
		if (FCode.equals("PluralityType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PluralityType = FValue.trim();
			}
			else
				PluralityType = null;
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
		if (FCode.equals("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equals("BMI"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BMI = d;
			}
		}
		if (FCode.equals("License"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				License = FValue.trim();
			}
			else
				License = null;
		}
		if (FCode.equals("LicenseType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LicenseType = FValue.trim();
			}
			else
				LicenseType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBAppntSchema other = (LBAppntSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntGrade.equals(other.getAppntGrade())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& fDate.getString(AppntBirthday).equals(other.getAppntBirthday())
			&& AppntType.equals(other.getAppntType())
			&& AddressNo.equals(other.getAddressNo())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& NativePlace.equals(other.getNativePlace())
			&& Nationality.equals(other.getNationality())
			&& RgtAddress.equals(other.getRgtAddress())
			&& Marriage.equals(other.getMarriage())
			&& fDate.getString(MarriageDate).equals(other.getMarriageDate())
			&& Health.equals(other.getHealth())
			&& Stature == other.getStature()
			&& Avoirdupois == other.getAvoirdupois()
			&& Degree.equals(other.getDegree())
			&& CreditGrade.equals(other.getCreditGrade())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(JoinCompanyDate).equals(other.getJoinCompanyDate())
			&& fDate.getString(StartWorkDate).equals(other.getStartWorkDate())
			&& Position.equals(other.getPosition())
			&& Salary == other.getSalary()
			&& OccupationType.equals(other.getOccupationType())
			&& OccupationCode.equals(other.getOccupationCode())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BMI == other.getBMI()
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType());
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
		if( strFieldName.equals("PrtNo") ) {
			return 2;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return 4;
		}
		if( strFieldName.equals("AppntName") ) {
			return 5;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 6;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 7;
		}
		if( strFieldName.equals("AppntType") ) {
			return 8;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 9;
		}
		if( strFieldName.equals("IDType") ) {
			return 10;
		}
		if( strFieldName.equals("IDNo") ) {
			return 11;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 12;
		}
		if( strFieldName.equals("Nationality") ) {
			return 13;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 14;
		}
		if( strFieldName.equals("Marriage") ) {
			return 15;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 16;
		}
		if( strFieldName.equals("Health") ) {
			return 17;
		}
		if( strFieldName.equals("Stature") ) {
			return 18;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 19;
		}
		if( strFieldName.equals("Degree") ) {
			return 20;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 21;
		}
		if( strFieldName.equals("BankCode") ) {
			return 22;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 23;
		}
		if( strFieldName.equals("AccName") ) {
			return 24;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 25;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 26;
		}
		if( strFieldName.equals("Position") ) {
			return 27;
		}
		if( strFieldName.equals("Salary") ) {
			return 28;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 29;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 30;
		}
		if( strFieldName.equals("WorkType") ) {
			return 31;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 32;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("BMI") ) {
			return 40;
		}
		if( strFieldName.equals("License") ) {
			return 41;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 42;
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
				strFieldName = "PrtNo";
				break;
			case 3:
				strFieldName = "AppntNo";
				break;
			case 4:
				strFieldName = "AppntGrade";
				break;
			case 5:
				strFieldName = "AppntName";
				break;
			case 6:
				strFieldName = "AppntSex";
				break;
			case 7:
				strFieldName = "AppntBirthday";
				break;
			case 8:
				strFieldName = "AppntType";
				break;
			case 9:
				strFieldName = "AddressNo";
				break;
			case 10:
				strFieldName = "IDType";
				break;
			case 11:
				strFieldName = "IDNo";
				break;
			case 12:
				strFieldName = "NativePlace";
				break;
			case 13:
				strFieldName = "Nationality";
				break;
			case 14:
				strFieldName = "RgtAddress";
				break;
			case 15:
				strFieldName = "Marriage";
				break;
			case 16:
				strFieldName = "MarriageDate";
				break;
			case 17:
				strFieldName = "Health";
				break;
			case 18:
				strFieldName = "Stature";
				break;
			case 19:
				strFieldName = "Avoirdupois";
				break;
			case 20:
				strFieldName = "Degree";
				break;
			case 21:
				strFieldName = "CreditGrade";
				break;
			case 22:
				strFieldName = "BankCode";
				break;
			case 23:
				strFieldName = "BankAccNo";
				break;
			case 24:
				strFieldName = "AccName";
				break;
			case 25:
				strFieldName = "JoinCompanyDate";
				break;
			case 26:
				strFieldName = "StartWorkDate";
				break;
			case 27:
				strFieldName = "Position";
				break;
			case 28:
				strFieldName = "Salary";
				break;
			case 29:
				strFieldName = "OccupationType";
				break;
			case 30:
				strFieldName = "OccupationCode";
				break;
			case 31:
				strFieldName = "WorkType";
				break;
			case 32:
				strFieldName = "PluralityType";
				break;
			case 33:
				strFieldName = "SmokeFlag";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "ManageCom";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "BMI";
				break;
			case 41:
				strFieldName = "License";
				break;
			case 42:
				strFieldName = "LicenseType";
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
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntGrade") ) {
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
		if( strFieldName.equals("AppntType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NativePlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Nationality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Marriage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Health") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Stature") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Degree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreditGrade") ) {
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
		if( strFieldName.equals("JoinCompanyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Position") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PluralityType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
