/*
 * <p>ClassName: LBBnfSchema </p>
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
import com.sinosoft.lis.db.LBBnfDB;

public class LBBnfSchema implements Schema
{
	// @Field
	/** 合同号码 */
	private String ContNo;
	/** 保单号码 */
	private String PolNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 受益人类别 */
	private String BnfType;
	/** 受益人序号 */
	private int BnfNo;
	/** 受益人级别 */
	private String BnfGrade;
	/** 与被保人关系 */
	private String RelationToInsured;
	/** 受益份额 */
	private double BnfLot;
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

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBBnfSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "PolNo";
		pk[1] = "InsuredNo";
		pk[2] = "BnfType";
		pk[3] = "BnfNo";
		pk[4] = "BnfGrade";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	/** 保单号码<P> */
	public String getPolNo()
	{
		if (PolNo != null && !PolNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolNo = StrTool.unicodeToGBK(PolNo);
		}
		return PolNo;
	}
	/** 保单号码 */
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/** 被保人客户号码<P> */
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
	/** 受益人类别<P>0 -- 生存受益人<br>1 -- 死亡受益人<br>2 -- 红利受益人<br> */
	public String getBnfType()
	{
		if (BnfType != null && !BnfType.equals("") && SysConst.CHANGECHARSET == true)
		{
			BnfType = StrTool.unicodeToGBK(BnfType);
		}
		return BnfType;
	}
	/** 受益人类别 */
	public void setBnfType(String aBnfType)
	{
		BnfType = aBnfType;
	}
	/** 受益人序号<P>对受益人进行唯一编码。 */
	public int getBnfNo()
	{
		return BnfNo;
	}
	/** 受益人序号 */
	public void setBnfNo(int aBnfNo)
	{
		BnfNo = aBnfNo;
	}
	/** 受益人序号<P>对受益人进行唯一编码。 */
	public void setBnfNo(String aBnfNo)
	{
		if (aBnfNo != null && !aBnfNo.equals(""))
		{
			Integer tInteger = new Integer(aBnfNo);
			int i = tInteger.intValue();
			BnfNo = i;
		}
	}

	/** 受益人级别<P>指对受益人进行分组。<br>如第一受益人，第二受益人。<br> */
	public String getBnfGrade()
	{
		if (BnfGrade != null && !BnfGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			BnfGrade = StrTool.unicodeToGBK(BnfGrade);
		}
		return BnfGrade;
	}
	/** 受益人级别 */
	public void setBnfGrade(String aBnfGrade)
	{
		BnfGrade = aBnfGrade;
	}
	/** 与被保人关系<P> */
	public String getRelationToInsured()
	{
		if (RelationToInsured != null && !RelationToInsured.equals("") && SysConst.CHANGECHARSET == true)
		{
			RelationToInsured = StrTool.unicodeToGBK(RelationToInsured);
		}
		return RelationToInsured;
	}
	/** 与被保人关系 */
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	/** 受益份额<P> */
	public double getBnfLot()
	{
		return BnfLot;
	}
	/** 受益份额 */
	public void setBnfLot(double aBnfLot)
	{
		BnfLot = aBnfLot;
	}
	/** 受益份额<P> */
	public void setBnfLot(String aBnfLot)
	{
		if (aBnfLot != null && !aBnfLot.equals(""))
		{
			Double tDouble = new Double(aBnfLot);
			double d = tDouble.doubleValue();
			BnfLot = d;
		}
	}

	/** 客户号码<P> */
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
	/** 客户姓名<P> */
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
	/** 客户性别<P> */
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
	/** 客户出生日期<P> */
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
	/** 客户出生日期<P> */
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
	}

	/** 证件类型<P>0 -- 身份证         <br>1 -- 护照         <br>2 -- 军官证         <br>3 -- 驾照         <br>4 -- 出生证明         <br>5 -- 户口簿         <br>8 -- 其他         <br>9 -- 数据转换证件<br> */
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
	* 使用另外一个 LBBnfSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LBBnfSchema aLBBnfSchema)
	{
		this.ContNo = aLBBnfSchema.getContNo();
		this.PolNo = aLBBnfSchema.getPolNo();
		this.InsuredNo = aLBBnfSchema.getInsuredNo();
		this.BnfType = aLBBnfSchema.getBnfType();
		this.BnfNo = aLBBnfSchema.getBnfNo();
		this.BnfGrade = aLBBnfSchema.getBnfGrade();
		this.RelationToInsured = aLBBnfSchema.getRelationToInsured();
		this.BnfLot = aLBBnfSchema.getBnfLot();
		this.CustomerNo = aLBBnfSchema.getCustomerNo();
		this.Name = aLBBnfSchema.getName();
		this.Sex = aLBBnfSchema.getSex();
		this.Birthday = fDate.getDate( aLBBnfSchema.getBirthday());
		this.IDType = aLBBnfSchema.getIDType();
		this.IDNo = aLBBnfSchema.getIDNo();
		this.Operator = aLBBnfSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBBnfSchema.getMakeDate());
		this.MakeTime = aLBBnfSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBBnfSchema.getModifyDate());
		this.ModifyTime = aLBBnfSchema.getModifyTime();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("BnfType") == null )
				this.BnfType = null;
			else
				this.BnfType = rs.getString("BnfType").trim();

			this.BnfNo = rs.getInt("BnfNo");
			if( rs.getString("BnfGrade") == null )
				this.BnfGrade = null;
			else
				this.BnfGrade = rs.getString("BnfGrade").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			this.BnfLot = rs.getDouble("BnfLot");
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
			tError.moduleName = "LBBnfSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LBBnfSchema getSchema()
	{
		LBBnfSchema aLBBnfSchema = new LBBnfSchema();
		aLBBnfSchema.setSchema(this);
		return aLBBnfSchema;
	}

	public LBBnfDB getDB()
	{
		LBBnfDB aDBOper = new LBBnfDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBBnf描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BnfType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BnfNo) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BnfGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RelationToInsured) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BnfLot) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CustomerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Name) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Sex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( Birthday )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBBnf>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BnfType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BnfNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			BnfGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BnfLot = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBBnfSchema";
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
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equals("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equals("BnfType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfType));
		}
		if (FCode.equals("BnfNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfNo));
		}
		if (FCode.equals("BnfGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfGrade));
		}
		if (FCode.equals("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equals("BnfLot"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfLot));
		}
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BnfType);
				break;
			case 4:
				strFieldValue = String.valueOf(BnfNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BnfGrade);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 7:
				strFieldValue = String.valueOf(BnfLot);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
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
		if (FCode.equals("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equals("BnfType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfType = FValue.trim();
			}
			else
				BnfType = null;
		}
		if (FCode.equals("BnfNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BnfNo = i;
			}
		}
		if (FCode.equals("BnfGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfGrade = FValue.trim();
			}
			else
				BnfGrade = null;
		}
		if (FCode.equals("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equals("BnfLot"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BnfLot = d;
			}
		}
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
		LBBnfSchema other = (LBBnfSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& BnfType.equals(other.getBnfType())
			&& BnfNo == other.getBnfNo()
			&& BnfGrade.equals(other.getBnfGrade())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& BnfLot == other.getBnfLot()
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 2;
		}
		if( strFieldName.equals("BnfType") ) {
			return 3;
		}
		if( strFieldName.equals("BnfNo") ) {
			return 4;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return 5;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 6;
		}
		if( strFieldName.equals("BnfLot") ) {
			return 7;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 8;
		}
		if( strFieldName.equals("Name") ) {
			return 9;
		}
		if( strFieldName.equals("Sex") ) {
			return 10;
		}
		if( strFieldName.equals("Birthday") ) {
			return 11;
		}
		if( strFieldName.equals("IDType") ) {
			return 12;
		}
		if( strFieldName.equals("IDNo") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "InsuredNo";
				break;
			case 3:
				strFieldName = "BnfType";
				break;
			case 4:
				strFieldName = "BnfNo";
				break;
			case 5:
				strFieldName = "BnfGrade";
				break;
			case 6:
				strFieldName = "RelationToInsured";
				break;
			case 7:
				strFieldName = "BnfLot";
				break;
			case 8:
				strFieldName = "CustomerNo";
				break;
			case 9:
				strFieldName = "Name";
				break;
			case 10:
				strFieldName = "Sex";
				break;
			case 11:
				strFieldName = "Birthday";
				break;
			case 12:
				strFieldName = "IDType";
				break;
			case 13:
				strFieldName = "IDNo";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfLot") ) {
			return Schema.TYPE_DOUBLE;
		}
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
