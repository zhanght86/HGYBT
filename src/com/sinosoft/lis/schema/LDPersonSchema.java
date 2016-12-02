/*
 * <p>ClassName: LDPersonSchema </p>
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
import com.sinosoft.lis.db.LDPersonDB;

public class LDPersonSchema implements Schema
{
	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 客户姓名 */
	private String Name;
	/** 客户性别 */
	private String Sex;
	/** 客户出生日期 */
	private Date Birthday;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 密码 */
	private String Password;
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
	/** 其它证件类型 */
	private String OthIDType;
	/** 其它证件号码 */
	private String OthIDNo;
	/** Ic卡号 */
	private String ICNo;
	/** 单位编码 */
	private String GrpNo;
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
	/** 死亡日期 */
	private Date DeathDate;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 黑名单标记 */
	private String BlacklistFlag;
	/** 属性 */
	private String Proterty;
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String State;
	/** Vip值 */
	private String VIPValue;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 单位名称 */
	private String GrpName;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;

	public static final int FIELDNUM = 44;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPersonSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CustomerNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 客户号码<P>客户号码 */
	public String getCustomerNo()
	{
		if (CustomerNo != null && !CustomerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			CustomerNo = StrTool.unicodeToGBK(CustomerNo);
		}
		return CustomerNo;
	}
	/** 客户号码 */
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/** 客户姓名<P>客户姓名 */
	public String getName()
	{
		if (Name != null && !Name.equals("") && SysConst.CHANGECHARSET == true)
		{
			Name = StrTool.unicodeToGBK(Name);
		}
		return Name;
	}
	/** 客户姓名 */
	public void setName(String aName)
	{
		Name = aName;
	}
	/** 客户性别<P>客户性别 */
	public String getSex()
	{
		if (Sex != null && !Sex.equals("") && SysConst.CHANGECHARSET == true)
		{
			Sex = StrTool.unicodeToGBK(Sex);
		}
		return Sex;
	}
	/** 客户性别 */
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	/** 客户出生日期<P>客户出生日期 */
	public String getBirthday()
	{
		if( Birthday != null )
			return fDate.getString(Birthday);
		else
			return null;
	}
	/** 客户出生日期 */
	public void setBirthday(Date aBirthday)
	{
		Birthday = aBirthday;
	}
	/** 客户出生日期<P>客户出生日期 */
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
	}

	/** 证件类型<P>证件类型<br>0 -- 身份证         <br>1 -- 护照         <br>2 -- 军官证         <br>3 -- 驾照         <br>4 -- 出生证明         <br>5 -- 户口簿         <br>8 -- 其他         <br>9 -- 数据转换证件 */
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
	/** 密码<P>密码 */
	public String getPassword()
	{
		if (Password != null && !Password.equals("") && SysConst.CHANGECHARSET == true)
		{
			Password = StrTool.unicodeToGBK(Password);
		}
		return Password;
	}
	/** 密码 */
	public void setPassword(String aPassword)
	{
		Password = aPassword;
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
	/** 其它证件类型<P>其它证件类型 */
	public String getOthIDType()
	{
		if (OthIDType != null && !OthIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			OthIDType = StrTool.unicodeToGBK(OthIDType);
		}
		return OthIDType;
	}
	/** 其它证件类型 */
	public void setOthIDType(String aOthIDType)
	{
		OthIDType = aOthIDType;
	}
	/** 其它证件号码<P>其它证件号码 */
	public String getOthIDNo()
	{
		if (OthIDNo != null && !OthIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			OthIDNo = StrTool.unicodeToGBK(OthIDNo);
		}
		return OthIDNo;
	}
	/** 其它证件号码 */
	public void setOthIDNo(String aOthIDNo)
	{
		OthIDNo = aOthIDNo;
	}
	/** Ic卡号<P>ic卡号 */
	public String getICNo()
	{
		if (ICNo != null && !ICNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ICNo = StrTool.unicodeToGBK(ICNo);
		}
		return ICNo;
	}
	/** Ic卡号 */
	public void setICNo(String aICNo)
	{
		ICNo = aICNo;
	}
	/** 单位编码<P>单位编码 */
	public String getGrpNo()
	{
		if (GrpNo != null && !GrpNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			GrpNo = StrTool.unicodeToGBK(GrpNo);
		}
		return GrpNo;
	}
	/** 单位编码 */
	public void setGrpNo(String aGrpNo)
	{
		GrpNo = aGrpNo;
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
	/** 兼职（工种）<P>兼职（工种） */
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
	/** 死亡日期<P>死亡日期 */
	public String getDeathDate()
	{
		if( DeathDate != null )
			return fDate.getString(DeathDate);
		else
			return null;
	}
	/** 死亡日期 */
	public void setDeathDate(Date aDeathDate)
	{
		DeathDate = aDeathDate;
	}
	/** 死亡日期<P>死亡日期 */
	public void setDeathDate(String aDeathDate)
	{
		if (aDeathDate != null && !aDeathDate.equals("") )
		{
			DeathDate = fDate.getDate( aDeathDate );
		}
		else
			DeathDate = null;
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
	/** 黑名单标记<P>黑名单标记<br>0-正常,1-黑名单 */
	public String getBlacklistFlag()
	{
		if (BlacklistFlag != null && !BlacklistFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			BlacklistFlag = StrTool.unicodeToGBK(BlacklistFlag);
		}
		return BlacklistFlag;
	}
	/** 黑名单标记 */
	public void setBlacklistFlag(String aBlacklistFlag)
	{
		BlacklistFlag = aBlacklistFlag;
	}
	/** 属性<P>属性 */
	public String getProterty()
	{
		if (Proterty != null && !Proterty.equals("") && SysConst.CHANGECHARSET == true)
		{
			Proterty = StrTool.unicodeToGBK(Proterty);
		}
		return Proterty;
	}
	/** 属性 */
	public void setProterty(String aProterty)
	{
		Proterty = aProterty;
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
	/** 状态<P>状态 */
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
	/** Vip值<P>目前只通过<br><br>0标识非VIP客户<br>1标识VIP客户<br><br>预设计支持VIP值区别VIP等级<br> */
	public String getVIPValue()
	{
		if (VIPValue != null && !VIPValue.equals("") && SysConst.CHANGECHARSET == true)
		{
			VIPValue = StrTool.unicodeToGBK(VIPValue);
		}
		return VIPValue;
	}
	/** Vip值 */
	public void setVIPValue(String aVIPValue)
	{
		VIPValue = aVIPValue;
	}
	/** 操作员代码<P> */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 操作员代码 */
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
	/** 单位名称<P> */
	public String getGrpName()
	{
		if (GrpName != null && !GrpName.equals("") && SysConst.CHANGECHARSET == true)
		{
			GrpName = StrTool.unicodeToGBK(GrpName);
		}
		return GrpName;
	}
	/** 单位名称 */
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
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
	* 使用另外一个 LDPersonSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDPersonSchema aLDPersonSchema)
	{
		this.CustomerNo = aLDPersonSchema.getCustomerNo();
		this.Name = aLDPersonSchema.getName();
		this.Sex = aLDPersonSchema.getSex();
		this.Birthday = fDate.getDate( aLDPersonSchema.getBirthday());
		this.IDType = aLDPersonSchema.getIDType();
		this.IDNo = aLDPersonSchema.getIDNo();
		this.Password = aLDPersonSchema.getPassword();
		this.NativePlace = aLDPersonSchema.getNativePlace();
		this.Nationality = aLDPersonSchema.getNationality();
		this.RgtAddress = aLDPersonSchema.getRgtAddress();
		this.Marriage = aLDPersonSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLDPersonSchema.getMarriageDate());
		this.Health = aLDPersonSchema.getHealth();
		this.Stature = aLDPersonSchema.getStature();
		this.Avoirdupois = aLDPersonSchema.getAvoirdupois();
		this.Degree = aLDPersonSchema.getDegree();
		this.CreditGrade = aLDPersonSchema.getCreditGrade();
		this.OthIDType = aLDPersonSchema.getOthIDType();
		this.OthIDNo = aLDPersonSchema.getOthIDNo();
		this.ICNo = aLDPersonSchema.getICNo();
		this.GrpNo = aLDPersonSchema.getGrpNo();
		this.JoinCompanyDate = fDate.getDate( aLDPersonSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLDPersonSchema.getStartWorkDate());
		this.Position = aLDPersonSchema.getPosition();
		this.Salary = aLDPersonSchema.getSalary();
		this.OccupationType = aLDPersonSchema.getOccupationType();
		this.OccupationCode = aLDPersonSchema.getOccupationCode();
		this.WorkType = aLDPersonSchema.getWorkType();
		this.PluralityType = aLDPersonSchema.getPluralityType();
		this.DeathDate = fDate.getDate( aLDPersonSchema.getDeathDate());
		this.SmokeFlag = aLDPersonSchema.getSmokeFlag();
		this.BlacklistFlag = aLDPersonSchema.getBlacklistFlag();
		this.Proterty = aLDPersonSchema.getProterty();
		this.Remark = aLDPersonSchema.getRemark();
		this.State = aLDPersonSchema.getState();
		this.VIPValue = aLDPersonSchema.getVIPValue();
		this.Operator = aLDPersonSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDPersonSchema.getMakeDate());
		this.MakeTime = aLDPersonSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDPersonSchema.getModifyDate());
		this.ModifyTime = aLDPersonSchema.getModifyTime();
		this.GrpName = aLDPersonSchema.getGrpName();
		this.License = aLDPersonSchema.getLicense();
		this.LicenseType = aLDPersonSchema.getLicenseType();
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
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

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

			if( rs.getString("OthIDType") == null )
				this.OthIDType = null;
			else
				this.OthIDType = rs.getString("OthIDType").trim();

			if( rs.getString("OthIDNo") == null )
				this.OthIDNo = null;
			else
				this.OthIDNo = rs.getString("OthIDNo").trim();

			if( rs.getString("ICNo") == null )
				this.ICNo = null;
			else
				this.ICNo = rs.getString("ICNo").trim();

			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

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

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("Proterty") == null )
				this.Proterty = null;
			else
				this.Proterty = rs.getString("Proterty").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("VIPValue") == null )
				this.VIPValue = null;
			else
				this.VIPValue = rs.getString("VIPValue").trim();

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

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

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
			tError.moduleName = "LDPersonSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDPersonSchema getSchema()
	{
		LDPersonSchema aLDPersonSchema = new LDPersonSchema();
		aLDPersonSchema.setSchema(this);
		return aLDPersonSchema;
	}

	public LDPersonDB getDB()
	{
		LDPersonDB aDBOper = new LDPersonDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPerson描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CustomerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Name) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Sex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( Birthday )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Password) ) + SysConst.PACKAGESPILTER
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
					+ StrTool.cTrim( StrTool.unicodeToGBK(OthIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OthIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ICNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( JoinCompanyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartWorkDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Position) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Salary) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OccupationType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OccupationCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WorkType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PluralityType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( DeathDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SmokeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BlacklistFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Proterty) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(State) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(VIPValue) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(License) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LicenseType) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPerson>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			OthIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			OthIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ICNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Proterty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonSchema";
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
		if (FCode.equals("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equals("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equals("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equals("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
		}
		if (FCode.equals("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equals("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equals("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
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
		if (FCode.equals("OthIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthIDType));
		}
		if (FCode.equals("OthIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthIDNo));
		}
		if (FCode.equals("ICNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICNo));
		}
		if (FCode.equals("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
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
		if (FCode.equals("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equals("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equals("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
		}
		if (FCode.equals("Proterty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Proterty));
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("VIPValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPValue));
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
		if (FCode.equals("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 13:
				strFieldValue = String.valueOf(Stature);
				break;
			case 14:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(OthIDType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(OthIDNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ICNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 24:
				strFieldValue = String.valueOf(Salary);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Proterty);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 43:
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

		if (FCode.equals("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equals("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equals("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equals("Birthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Birthday = fDate.getDate( FValue );
			}
			else
				Birthday = null;
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
		if (FCode.equals("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equals("OthIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthIDType = FValue.trim();
			}
			else
				OthIDType = null;
		}
		if (FCode.equals("OthIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthIDNo = FValue.trim();
			}
			else
				OthIDNo = null;
		}
		if (FCode.equals("ICNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICNo = FValue.trim();
			}
			else
				ICNo = null;
		}
		if (FCode.equals("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
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
		if (FCode.equals("DeathDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeathDate = fDate.getDate( FValue );
			}
			else
				DeathDate = null;
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
		if (FCode.equals("BlacklistFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlacklistFlag = FValue.trim();
			}
			else
				BlacklistFlag = null;
		}
		if (FCode.equals("Proterty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Proterty = FValue.trim();
			}
			else
				Proterty = null;
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
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equals("VIPValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPValue = FValue.trim();
			}
			else
				VIPValue = null;
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
		if (FCode.equals("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
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
		LDPersonSchema other = (LDPersonSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& Password.equals(other.getPassword())
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
			&& OthIDType.equals(other.getOthIDType())
			&& OthIDNo.equals(other.getOthIDNo())
			&& ICNo.equals(other.getICNo())
			&& GrpNo.equals(other.getGrpNo())
			&& fDate.getString(JoinCompanyDate).equals(other.getJoinCompanyDate())
			&& fDate.getString(StartWorkDate).equals(other.getStartWorkDate())
			&& Position.equals(other.getPosition())
			&& Salary == other.getSalary()
			&& OccupationType.equals(other.getOccupationType())
			&& OccupationCode.equals(other.getOccupationCode())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& Proterty.equals(other.getProterty())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState())
			&& VIPValue.equals(other.getVIPValue())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& GrpName.equals(other.getGrpName())
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("Name") ) {
			return 1;
		}
		if( strFieldName.equals("Sex") ) {
			return 2;
		}
		if( strFieldName.equals("Birthday") ) {
			return 3;
		}
		if( strFieldName.equals("IDType") ) {
			return 4;
		}
		if( strFieldName.equals("IDNo") ) {
			return 5;
		}
		if( strFieldName.equals("Password") ) {
			return 6;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 7;
		}
		if( strFieldName.equals("Nationality") ) {
			return 8;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 9;
		}
		if( strFieldName.equals("Marriage") ) {
			return 10;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 11;
		}
		if( strFieldName.equals("Health") ) {
			return 12;
		}
		if( strFieldName.equals("Stature") ) {
			return 13;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 14;
		}
		if( strFieldName.equals("Degree") ) {
			return 15;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 16;
		}
		if( strFieldName.equals("OthIDType") ) {
			return 17;
		}
		if( strFieldName.equals("OthIDNo") ) {
			return 18;
		}
		if( strFieldName.equals("ICNo") ) {
			return 19;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 20;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 21;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 22;
		}
		if( strFieldName.equals("Position") ) {
			return 23;
		}
		if( strFieldName.equals("Salary") ) {
			return 24;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 25;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 26;
		}
		if( strFieldName.equals("WorkType") ) {
			return 27;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 28;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 29;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 30;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 31;
		}
		if( strFieldName.equals("Proterty") ) {
			return 32;
		}
		if( strFieldName.equals("Remark") ) {
			return 33;
		}
		if( strFieldName.equals("State") ) {
			return 34;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 35;
		}
		if( strFieldName.equals("Operator") ) {
			return 36;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 37;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 40;
		}
		if( strFieldName.equals("GrpName") ) {
			return 41;
		}
		if( strFieldName.equals("License") ) {
			return 42;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 43;
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "Name";
				break;
			case 2:
				strFieldName = "Sex";
				break;
			case 3:
				strFieldName = "Birthday";
				break;
			case 4:
				strFieldName = "IDType";
				break;
			case 5:
				strFieldName = "IDNo";
				break;
			case 6:
				strFieldName = "Password";
				break;
			case 7:
				strFieldName = "NativePlace";
				break;
			case 8:
				strFieldName = "Nationality";
				break;
			case 9:
				strFieldName = "RgtAddress";
				break;
			case 10:
				strFieldName = "Marriage";
				break;
			case 11:
				strFieldName = "MarriageDate";
				break;
			case 12:
				strFieldName = "Health";
				break;
			case 13:
				strFieldName = "Stature";
				break;
			case 14:
				strFieldName = "Avoirdupois";
				break;
			case 15:
				strFieldName = "Degree";
				break;
			case 16:
				strFieldName = "CreditGrade";
				break;
			case 17:
				strFieldName = "OthIDType";
				break;
			case 18:
				strFieldName = "OthIDNo";
				break;
			case 19:
				strFieldName = "ICNo";
				break;
			case 20:
				strFieldName = "GrpNo";
				break;
			case 21:
				strFieldName = "JoinCompanyDate";
				break;
			case 22:
				strFieldName = "StartWorkDate";
				break;
			case 23:
				strFieldName = "Position";
				break;
			case 24:
				strFieldName = "Salary";
				break;
			case 25:
				strFieldName = "OccupationType";
				break;
			case 26:
				strFieldName = "OccupationCode";
				break;
			case 27:
				strFieldName = "WorkType";
				break;
			case 28:
				strFieldName = "PluralityType";
				break;
			case 29:
				strFieldName = "DeathDate";
				break;
			case 30:
				strFieldName = "SmokeFlag";
				break;
			case 31:
				strFieldName = "BlacklistFlag";
				break;
			case 32:
				strFieldName = "Proterty";
				break;
			case 33:
				strFieldName = "Remark";
				break;
			case 34:
				strFieldName = "State";
				break;
			case 35:
				strFieldName = "VIPValue";
				break;
			case 36:
				strFieldName = "Operator";
				break;
			case 37:
				strFieldName = "MakeDate";
				break;
			case 38:
				strFieldName = "MakeTime";
				break;
			case 39:
				strFieldName = "ModifyDate";
				break;
			case 40:
				strFieldName = "ModifyTime";
				break;
			case 41:
				strFieldName = "GrpName";
				break;
			case 42:
				strFieldName = "License";
				break;
			case 43:
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
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
		if( strFieldName.equals("OthIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNo") ) {
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
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Proterty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPValue") ) {
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
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
