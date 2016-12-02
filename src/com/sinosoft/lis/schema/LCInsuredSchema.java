/*
 * <p>ClassName: LCInsuredSchema </p>
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
import com.sinosoft.lis.db.LCInsuredDB;

public class LCInsuredSchema implements Schema
{
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 管理机构 */
	private String ManageCom;
	/** 处理机构 */
	private String ExecuteCom;
	/** 家庭保障号 */
	private String FamilyID;
	/** 与主被保人关系 */
	private String RelationToMainInsured;
	/** 与投保人关系 */
	private String RelationToAppnt;
	/** 客户地址号码 */
	private String AddressNo;
	/** 客户内部号码 */
	private String SequenceNo;
	/** 被保人名称 */
	private String Name;
	/** 被保人性别 */
	private String Sex;
	/** 被保人出生日期 */
	private Date Birthday;
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
	/** 兼职  暂存投被保人关系 */
	private String PluralityType;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 操作员 */
	private String Operator;
	/** 被保人状态 */
	private String InsuredStat;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 身体指标 */
	private double BMI;
	/** 被保人数目 */
	private int InsuredPeoples;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;
	/** 团体客户序号 */
	private int CustomerSeqNo;

	public static final int FIELDNUM = 55;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCInsuredSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "InsuredNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 集体合同号码<P>冗余，标准在个人保单表，方便查询统计 */
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
	/** 合同号码<P> */
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
	/** 被保人客户号<P> */
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
	/** 印刷号码<P>同投保单号<br> */
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
	/** 投保人客户号码<P> */
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
	/** 处理机构<P>关联统括保单处理 */
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
	/** 家庭保障号<P> */
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
	/** 与主被保人关系<P> */
	public String getRelationToMainInsured()
	{
		if (RelationToMainInsured != null && !RelationToMainInsured.equals("") && SysConst.CHANGECHARSET == true)
		{
			RelationToMainInsured = StrTool.unicodeToGBK(RelationToMainInsured);
		}
		return RelationToMainInsured;
	}
	/** 与主被保人关系 */
	public void setRelationToMainInsured(String aRelationToMainInsured)
	{
		RelationToMainInsured = aRelationToMainInsured;
	}
	/** 与投保人关系<P> */
	public String getRelationToAppnt()
	{
		if (RelationToAppnt != null && !RelationToAppnt.equals("") && SysConst.CHANGECHARSET == true)
		{
			RelationToAppnt = StrTool.unicodeToGBK(RelationToAppnt);
		}
		return RelationToAppnt;
	}
	/** 与投保人关系 */
	public void setRelationToAppnt(String aRelationToAppnt)
	{
		RelationToAppnt = aRelationToAppnt;
	}
	/** 客户地址号码<P> */
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
	/** 客户内部号码<P>客户分类的号码<br> */
	public String getSequenceNo()
	{
		if (SequenceNo != null && !SequenceNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SequenceNo = StrTool.unicodeToGBK(SequenceNo);
		}
		return SequenceNo;
	}
	/** 客户内部号码 */
	public void setSequenceNo(String aSequenceNo)
	{
		SequenceNo = aSequenceNo;
	}
	/** 被保人名称<P>冗余，作为保单层面的标准 */
	public String getName()
	{
		if (Name != null && !Name.equals("") && SysConst.CHANGECHARSET == true)
		{
			Name = StrTool.unicodeToGBK(Name);
		}
		return Name;
	}
	/** 被保人名称 */
	public void setName(String aName)
	{
		Name = aName;
	}
	/** 被保人性别<P>冗余，作为保单层面的标准 */
	public String getSex()
	{
		if (Sex != null && !Sex.equals("") && SysConst.CHANGECHARSET == true)
		{
			Sex = StrTool.unicodeToGBK(Sex);
		}
		return Sex;
	}
	/** 被保人性别 */
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	/** 被保人出生日期<P>冗余，作为保单层面的标准 */
	public String getBirthday()
	{
		if( Birthday != null )
			return fDate.getString(Birthday);
		else
			return null;
	}
	/** 被保人出生日期 */
	public void setBirthday(Date aBirthday)
	{
		Birthday = aBirthday;
	}
	/** 被保人出生日期<P>冗余，作为保单层面的标准 */
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
	}

	/** 证件类型<P>0 -- 身份证         <br>1 -- 护照         <br>2 -- 军官证         <br>3 -- 驾照         <br>4 -- 出生证明         <br>5 -- 户口簿         <br>8 -- 其他         <br>9 -- 数据转换证件 */
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
	/** 证件号码<P> */
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
	/** 国籍<P> */
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
	/** 民族<P> */
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
	/** 户口所在地<P> */
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
	/** 婚姻状况<P> */
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
	/** 结婚日期<P> */
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
	/** 结婚日期<P> */
	public void setMarriageDate(String aMarriageDate)
	{
		if (aMarriageDate != null && !aMarriageDate.equals("") )
		{
			MarriageDate = fDate.getDate( aMarriageDate );
		}
		else
			MarriageDate = null;
	}

	/** 健康状况<P> */
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
	/** 身高<P> */
	public double getStature()
	{
		return Stature;
	}
	/** 身高 */
	public void setStature(double aStature)
	{
		Stature = aStature;
	}
	/** 身高<P> */
	public void setStature(String aStature)
	{
		if (aStature != null && !aStature.equals(""))
		{
			Double tDouble = new Double(aStature);
			double d = tDouble.doubleValue();
			Stature = d;
		}
	}

	/** 体重<P> */
	public double getAvoirdupois()
	{
		return Avoirdupois;
	}
	/** 体重 */
	public void setAvoirdupois(double aAvoirdupois)
	{
		Avoirdupois = aAvoirdupois;
	}
	/** 体重<P> */
	public void setAvoirdupois(String aAvoirdupois)
	{
		if (aAvoirdupois != null && !aAvoirdupois.equals(""))
		{
			Double tDouble = new Double(aAvoirdupois);
			double d = tDouble.doubleValue();
			Avoirdupois = d;
		}
	}

	/** 学历<P> */
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
	/** 信用等级<P> */
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
	/** 银行编码<P> */
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
	/** 银行帐号<P> */
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
	/** 银行帐户名<P> */
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
	/** 入司日期<P> */
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
	/** 入司日期<P> */
	public void setJoinCompanyDate(String aJoinCompanyDate)
	{
		if (aJoinCompanyDate != null && !aJoinCompanyDate.equals("") )
		{
			JoinCompanyDate = fDate.getDate( aJoinCompanyDate );
		}
		else
			JoinCompanyDate = null;
	}

	/** 参加工作日期<P> */
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
	/** 参加工作日期<P> */
	public void setStartWorkDate(String aStartWorkDate)
	{
		if (aStartWorkDate != null && !aStartWorkDate.equals("") )
		{
			StartWorkDate = fDate.getDate( aStartWorkDate );
		}
		else
			StartWorkDate = null;
	}

	/** 职位<P> */
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
	/** 工资<P> */
	public double getSalary()
	{
		return Salary;
	}
	/** 工资 */
	public void setSalary(double aSalary)
	{
		Salary = aSalary;
	}
	/** 工资<P> */
	public void setSalary(String aSalary)
	{
		if (aSalary != null && !aSalary.equals(""))
		{
			Double tDouble = new Double(aSalary);
			double d = tDouble.doubleValue();
			Salary = d;
		}
	}

	/** 职业类别<P> */
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
	/** 职业代码<P> */
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
	/** 职业（工种）<P> */
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
	/** 兼职  暂存投被保人关系<P>兼职  暂存投被保人关系 */
	public String getPluralityType()
	{
		if (PluralityType != null && !PluralityType.equals("") && SysConst.CHANGECHARSET == true)
		{
			PluralityType = StrTool.unicodeToGBK(PluralityType);
		}
		return PluralityType;
	}
	/** 兼职  暂存投被保人关系 */
	public void setPluralityType(String aPluralityType)
	{
		PluralityType = aPluralityType;
	}
	/** 是否吸烟标志<P> */
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
	/** 保险计划编码<P> */
	public String getContPlanCode()
	{
		if (ContPlanCode != null && !ContPlanCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContPlanCode = StrTool.unicodeToGBK(ContPlanCode);
		}
		return ContPlanCode;
	}
	/** 保险计划编码 */
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
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
	/** 被保人状态<P>1-暂停状态<br>0-未暂停状态 */
	public String getInsuredStat()
	{
		if (InsuredStat != null && !InsuredStat.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredStat = StrTool.unicodeToGBK(InsuredStat);
		}
		return InsuredStat;
	}
	/** 被保人状态 */
	public void setInsuredStat(String aInsuredStat)
	{
		InsuredStat = aInsuredStat;
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
	/** 核保状态<P>1 拒保 <br>2 延期 <br>3 条件承保 <br>4 通融 <br>5 自动 <br>6 待上级 <br>7 问题件 <br>8 核保通知书 <br>9 正常 <br>a 撤单 <br>b 保险计划变更<br>z 核保订正<br><br> */
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
	/** 最终核保人编码<P> */
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
	/** 核保完成日期<P> */
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
	/** 核保完成日期<P> */
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	/** 核保完成时间<P> */
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

	/** 被保人数目<P> */
	public int getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	/** 被保人数目 */
	public void setInsuredPeoples(int aInsuredPeoples)
	{
		InsuredPeoples = aInsuredPeoples;
	}
	/** 被保人数目<P> */
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		if (aInsuredPeoples != null && !aInsuredPeoples.equals(""))
		{
			Integer tInteger = new Integer(aInsuredPeoples);
			int i = tInteger.intValue();
			InsuredPeoples = i;
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
	/** 团体客户序号<P> */
	public int getCustomerSeqNo()
	{
		return CustomerSeqNo;
	}
	/** 团体客户序号 */
	public void setCustomerSeqNo(int aCustomerSeqNo)
	{
		CustomerSeqNo = aCustomerSeqNo;
	}
	/** 团体客户序号<P> */
	public void setCustomerSeqNo(String aCustomerSeqNo)
	{
		if (aCustomerSeqNo != null && !aCustomerSeqNo.equals(""))
		{
			Integer tInteger = new Integer(aCustomerSeqNo);
			int i = tInteger.intValue();
			CustomerSeqNo = i;
		}
	}


	/**
	* 使用另外一个 LCInsuredSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LCInsuredSchema aLCInsuredSchema)
	{
		this.GrpContNo = aLCInsuredSchema.getGrpContNo();
		this.ContNo = aLCInsuredSchema.getContNo();
		this.InsuredNo = aLCInsuredSchema.getInsuredNo();
		this.PrtNo = aLCInsuredSchema.getPrtNo();
		this.AppntNo = aLCInsuredSchema.getAppntNo();
		this.ManageCom = aLCInsuredSchema.getManageCom();
		this.ExecuteCom = aLCInsuredSchema.getExecuteCom();
		this.FamilyID = aLCInsuredSchema.getFamilyID();
		this.RelationToMainInsured = aLCInsuredSchema.getRelationToMainInsured();
		this.RelationToAppnt = aLCInsuredSchema.getRelationToAppnt();
		this.AddressNo = aLCInsuredSchema.getAddressNo();
		this.SequenceNo = aLCInsuredSchema.getSequenceNo();
		this.Name = aLCInsuredSchema.getName();
		this.Sex = aLCInsuredSchema.getSex();
		this.Birthday = fDate.getDate( aLCInsuredSchema.getBirthday());
		this.IDType = aLCInsuredSchema.getIDType();
		this.IDNo = aLCInsuredSchema.getIDNo();
		this.NativePlace = aLCInsuredSchema.getNativePlace();
		this.Nationality = aLCInsuredSchema.getNationality();
		this.RgtAddress = aLCInsuredSchema.getRgtAddress();
		this.Marriage = aLCInsuredSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLCInsuredSchema.getMarriageDate());
		this.Health = aLCInsuredSchema.getHealth();
		this.Stature = aLCInsuredSchema.getStature();
		this.Avoirdupois = aLCInsuredSchema.getAvoirdupois();
		this.Degree = aLCInsuredSchema.getDegree();
		this.CreditGrade = aLCInsuredSchema.getCreditGrade();
		this.BankCode = aLCInsuredSchema.getBankCode();
		this.BankAccNo = aLCInsuredSchema.getBankAccNo();
		this.AccName = aLCInsuredSchema.getAccName();
		this.JoinCompanyDate = fDate.getDate( aLCInsuredSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLCInsuredSchema.getStartWorkDate());
		this.Position = aLCInsuredSchema.getPosition();
		this.Salary = aLCInsuredSchema.getSalary();
		this.OccupationType = aLCInsuredSchema.getOccupationType();
		this.OccupationCode = aLCInsuredSchema.getOccupationCode();
		this.WorkType = aLCInsuredSchema.getWorkType();
		this.PluralityType = aLCInsuredSchema.getPluralityType();
		this.SmokeFlag = aLCInsuredSchema.getSmokeFlag();
		this.ContPlanCode = aLCInsuredSchema.getContPlanCode();
		this.Operator = aLCInsuredSchema.getOperator();
		this.InsuredStat = aLCInsuredSchema.getInsuredStat();
		this.MakeDate = fDate.getDate( aLCInsuredSchema.getMakeDate());
		this.MakeTime = aLCInsuredSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCInsuredSchema.getModifyDate());
		this.ModifyTime = aLCInsuredSchema.getModifyTime();
		this.UWFlag = aLCInsuredSchema.getUWFlag();
		this.UWCode = aLCInsuredSchema.getUWCode();
		this.UWDate = fDate.getDate( aLCInsuredSchema.getUWDate());
		this.UWTime = aLCInsuredSchema.getUWTime();
		this.BMI = aLCInsuredSchema.getBMI();
		this.InsuredPeoples = aLCInsuredSchema.getInsuredPeoples();
		this.License = aLCInsuredSchema.getLicense();
		this.LicenseType = aLCInsuredSchema.getLicenseType();
		this.CustomerSeqNo = aLCInsuredSchema.getCustomerSeqNo();
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

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("FamilyID") == null )
				this.FamilyID = null;
			else
				this.FamilyID = rs.getString("FamilyID").trim();

			if( rs.getString("RelationToMainInsured") == null )
				this.RelationToMainInsured = null;
			else
				this.RelationToMainInsured = rs.getString("RelationToMainInsured").trim();

			if( rs.getString("RelationToAppnt") == null )
				this.RelationToAppnt = null;
			else
				this.RelationToAppnt = rs.getString("RelationToAppnt").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("SequenceNo") == null )
				this.SequenceNo = null;
			else
				this.SequenceNo = rs.getString("SequenceNo").trim();

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

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("InsuredStat") == null )
				this.InsuredStat = null;
			else
				this.InsuredStat = rs.getString("InsuredStat").trim();

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

			this.BMI = rs.getDouble("BMI");
			this.InsuredPeoples = rs.getInt("InsuredPeoples");
			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

			this.CustomerSeqNo = rs.getInt("CustomerSeqNo");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LCInsuredSchema getSchema()
	{
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		aLCInsuredSchema.setSchema(this);
		return aLCInsuredSchema;
	}

	public LCInsuredDB getDB()
	{
		LCInsuredDB aDBOper = new LCInsuredDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsured描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(GrpContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ExecuteCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FamilyID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RelationToMainInsured) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RelationToAppnt) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddressNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SequenceNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Name) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Sex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( Birthday )) ) + SysConst.PACKAGESPILTER
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
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContPlanCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredStat) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( UWDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BMI) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredPeoples) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(License) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LicenseType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CustomerSeqNo);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsured>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RelationToMainInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RelationToAppnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			InsuredStat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			CustomerSeqNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredSchema";
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
		if (FCode.equals("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equals("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equals("FamilyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyID));
		}
		if (FCode.equals("RelationToMainInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToMainInsured));
		}
		if (FCode.equals("RelationToAppnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToAppnt));
		}
		if (FCode.equals("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equals("SequenceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SequenceNo));
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
		if (FCode.equals("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("InsuredStat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredStat));
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
		if (FCode.equals("BMI"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BMI));
		}
		if (FCode.equals("InsuredPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredPeoples));
		}
		if (FCode.equals("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equals("LicenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseType));
		}
		if (FCode.equals("CustomerSeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerSeqNo));
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
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RelationToMainInsured);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RelationToAppnt);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 23:
				strFieldValue = String.valueOf(Stature);
				break;
			case 24:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 33:
				strFieldValue = String.valueOf(Salary);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(InsuredStat);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 50:
				strFieldValue = String.valueOf(BMI);
				break;
			case 51:
				strFieldValue = String.valueOf(InsuredPeoples);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 54:
				strFieldValue = String.valueOf(CustomerSeqNo);
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
		if (FCode.equals("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equals("FamilyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyID = FValue.trim();
			}
			else
				FamilyID = null;
		}
		if (FCode.equals("RelationToMainInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToMainInsured = FValue.trim();
			}
			else
				RelationToMainInsured = null;
		}
		if (FCode.equals("RelationToAppnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToAppnt = FValue.trim();
			}
			else
				RelationToAppnt = null;
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
		if (FCode.equals("SequenceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SequenceNo = FValue.trim();
			}
			else
				SequenceNo = null;
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
		if (FCode.equals("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
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
		if (FCode.equals("InsuredStat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredStat = FValue.trim();
			}
			else
				InsuredStat = null;
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
		if (FCode.equals("BMI"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BMI = d;
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
		if (FCode.equals("CustomerSeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CustomerSeqNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCInsuredSchema other = (LCInsuredSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& PrtNo.equals(other.getPrtNo())
			&& AppntNo.equals(other.getAppntNo())
			&& ManageCom.equals(other.getManageCom())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& FamilyID.equals(other.getFamilyID())
			&& RelationToMainInsured.equals(other.getRelationToMainInsured())
			&& RelationToAppnt.equals(other.getRelationToAppnt())
			&& AddressNo.equals(other.getAddressNo())
			&& SequenceNo.equals(other.getSequenceNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
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
			&& ContPlanCode.equals(other.getContPlanCode())
			&& Operator.equals(other.getOperator())
			&& InsuredStat.equals(other.getInsuredStat())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& BMI == other.getBMI()
			&& InsuredPeoples == other.getInsuredPeoples()
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType())
			&& CustomerSeqNo == other.getCustomerSeqNo();
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
		if( strFieldName.equals("InsuredNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 6;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 7;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return 8;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return 9;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 10;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 11;
		}
		if( strFieldName.equals("Name") ) {
			return 12;
		}
		if( strFieldName.equals("Sex") ) {
			return 13;
		}
		if( strFieldName.equals("Birthday") ) {
			return 14;
		}
		if( strFieldName.equals("IDType") ) {
			return 15;
		}
		if( strFieldName.equals("IDNo") ) {
			return 16;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 17;
		}
		if( strFieldName.equals("Nationality") ) {
			return 18;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 19;
		}
		if( strFieldName.equals("Marriage") ) {
			return 20;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 21;
		}
		if( strFieldName.equals("Health") ) {
			return 22;
		}
		if( strFieldName.equals("Stature") ) {
			return 23;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 24;
		}
		if( strFieldName.equals("Degree") ) {
			return 25;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 26;
		}
		if( strFieldName.equals("BankCode") ) {
			return 27;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 28;
		}
		if( strFieldName.equals("AccName") ) {
			return 29;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 30;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 31;
		}
		if( strFieldName.equals("Position") ) {
			return 32;
		}
		if( strFieldName.equals("Salary") ) {
			return 33;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 34;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 35;
		}
		if( strFieldName.equals("WorkType") ) {
			return 36;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 37;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 38;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 39;
		}
		if( strFieldName.equals("Operator") ) {
			return 40;
		}
		if( strFieldName.equals("InsuredStat") ) {
			return 41;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 42;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 45;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 46;
		}
		if( strFieldName.equals("UWCode") ) {
			return 47;
		}
		if( strFieldName.equals("UWDate") ) {
			return 48;
		}
		if( strFieldName.equals("UWTime") ) {
			return 49;
		}
		if( strFieldName.equals("BMI") ) {
			return 50;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 51;
		}
		if( strFieldName.equals("License") ) {
			return 52;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 53;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return 54;
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
				strFieldName = "InsuredNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "AppntNo";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "ExecuteCom";
				break;
			case 7:
				strFieldName = "FamilyID";
				break;
			case 8:
				strFieldName = "RelationToMainInsured";
				break;
			case 9:
				strFieldName = "RelationToAppnt";
				break;
			case 10:
				strFieldName = "AddressNo";
				break;
			case 11:
				strFieldName = "SequenceNo";
				break;
			case 12:
				strFieldName = "Name";
				break;
			case 13:
				strFieldName = "Sex";
				break;
			case 14:
				strFieldName = "Birthday";
				break;
			case 15:
				strFieldName = "IDType";
				break;
			case 16:
				strFieldName = "IDNo";
				break;
			case 17:
				strFieldName = "NativePlace";
				break;
			case 18:
				strFieldName = "Nationality";
				break;
			case 19:
				strFieldName = "RgtAddress";
				break;
			case 20:
				strFieldName = "Marriage";
				break;
			case 21:
				strFieldName = "MarriageDate";
				break;
			case 22:
				strFieldName = "Health";
				break;
			case 23:
				strFieldName = "Stature";
				break;
			case 24:
				strFieldName = "Avoirdupois";
				break;
			case 25:
				strFieldName = "Degree";
				break;
			case 26:
				strFieldName = "CreditGrade";
				break;
			case 27:
				strFieldName = "BankCode";
				break;
			case 28:
				strFieldName = "BankAccNo";
				break;
			case 29:
				strFieldName = "AccName";
				break;
			case 30:
				strFieldName = "JoinCompanyDate";
				break;
			case 31:
				strFieldName = "StartWorkDate";
				break;
			case 32:
				strFieldName = "Position";
				break;
			case 33:
				strFieldName = "Salary";
				break;
			case 34:
				strFieldName = "OccupationType";
				break;
			case 35:
				strFieldName = "OccupationCode";
				break;
			case 36:
				strFieldName = "WorkType";
				break;
			case 37:
				strFieldName = "PluralityType";
				break;
			case 38:
				strFieldName = "SmokeFlag";
				break;
			case 39:
				strFieldName = "ContPlanCode";
				break;
			case 40:
				strFieldName = "Operator";
				break;
			case 41:
				strFieldName = "InsuredStat";
				break;
			case 42:
				strFieldName = "MakeDate";
				break;
			case 43:
				strFieldName = "MakeTime";
				break;
			case 44:
				strFieldName = "ModifyDate";
				break;
			case 45:
				strFieldName = "ModifyTime";
				break;
			case 46:
				strFieldName = "UWFlag";
				break;
			case 47:
				strFieldName = "UWCode";
				break;
			case 48:
				strFieldName = "UWDate";
				break;
			case 49:
				strFieldName = "UWTime";
				break;
			case 50:
				strFieldName = "BMI";
				break;
			case 51:
				strFieldName = "InsuredPeoples";
				break;
			case 52:
				strFieldName = "License";
				break;
			case 53:
				strFieldName = "LicenseType";
				break;
			case 54:
				strFieldName = "CustomerSeqNo";
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
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SequenceNo") ) {
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
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredStat") ) {
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
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
