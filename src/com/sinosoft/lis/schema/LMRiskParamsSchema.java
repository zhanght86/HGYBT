/*
 * <p>ClassName: LMRiskParamsSchema </p>
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
import com.sinosoft.lis.db.LMRiskParamsDB;

public class LMRiskParamsSchema implements Schema
{
	// @Field
	/** 险种别名 */
	private String RiskAlias;
	/** 险种编码 */
	private String RiskCode;
	/** 缴费方式 */
	private int PayIntv;
	/** 缴费期间 */
	private int PayEndYear;
	/** 缴费期间单位 */
	private String PayEndYearFlag;
	/** 保险期间 */
	private int InsuYear;
	/** 保险期间单位 */
	private String InsuYearFlag;
	/** 领取方式 */
	private String GetIntv;
	/** 领取时间 */
	private Date GetYear;
	/** 领取时间单位 */
	private String GetYeatFlag;
	/** 是否通过被保人年龄判断 */
	private String InsuredAgeFlag;
	/** 被保人年龄限制 */
	private int InsuredAge;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;
	/** 备用字段3 */
	private String StandbyFlag3;
	/** 生效日期 */
	private Date StartDate;
	/** 停效日期 */
	private Date EndDate;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskParamsSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskAlias";
		pk[1] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 险种别名<P>险种编码-金盛险种编码(副) */
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
	/** 险种编码<P>险种编码(总) */
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
	/** 缴费方式<P>缴费方式 */
	public int getPayIntv()
	{
		return PayIntv;
	}
	/** 缴费方式 */
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/** 缴费方式<P>缴费方式 */
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/** 缴费期间<P>缴费期间 */
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	/** 缴费期间 */
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** 缴费期间<P>缴费期间 */
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/** 缴费期间单位<P>缴费期间单位 */
	public String getPayEndYearFlag()
	{
		if (PayEndYearFlag != null && !PayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYearFlag = StrTool.unicodeToGBK(PayEndYearFlag);
		}
		return PayEndYearFlag;
	}
	/** 缴费期间单位 */
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	/** 保险期间<P>保险期间 */
	public int getInsuYear()
	{
		return InsuYear;
	}
	/** 保险期间 */
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	/** 保险期间<P>保险期间 */
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	/** 保险期间单位<P>保险期间单位 */
	public String getInsuYearFlag()
	{
		if (InsuYearFlag != null && !InsuYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuYearFlag = StrTool.unicodeToGBK(InsuYearFlag);
		}
		return InsuYearFlag;
	}
	/** 保险期间单位 */
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	/** 领取方式<P> */
	public String getGetIntv()
	{
		if (GetIntv != null && !GetIntv.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetIntv = StrTool.unicodeToGBK(GetIntv);
		}
		return GetIntv;
	}
	/** 领取方式 */
	public void setGetIntv(String aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	/** 领取时间<P>领取时间 */
	public String getGetYear()
	{
		if( GetYear != null )
			return fDate.getString(GetYear);
		else
			return null;
	}
	/** 领取时间 */
	public void setGetYear(Date aGetYear)
	{
		GetYear = aGetYear;
	}
	/** 领取时间<P>领取时间 */
	public void setGetYear(String aGetYear)
	{
		if (aGetYear != null && !aGetYear.equals("") )
		{
			GetYear = fDate.getDate( aGetYear );
		}
		else
			GetYear = null;
	}

	/** 领取时间单位<P>领取时间单位 */
	public String getGetYeatFlag()
	{
		if (GetYeatFlag != null && !GetYeatFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			GetYeatFlag = StrTool.unicodeToGBK(GetYeatFlag);
		}
		return GetYeatFlag;
	}
	/** 领取时间单位 */
	public void setGetYeatFlag(String aGetYeatFlag)
	{
		GetYeatFlag = aGetYeatFlag;
	}
	/** 是否通过被保人年龄判断<P>是否通过被保人年龄判断 */
	public String getInsuredAgeFlag()
	{
		if (InsuredAgeFlag != null && !InsuredAgeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredAgeFlag = StrTool.unicodeToGBK(InsuredAgeFlag);
		}
		return InsuredAgeFlag;
	}
	/** 是否通过被保人年龄判断 */
	public void setInsuredAgeFlag(String aInsuredAgeFlag)
	{
		InsuredAgeFlag = aInsuredAgeFlag;
	}
	/** 被保人年龄限制<P>被保人年龄限制 */
	public int getInsuredAge()
	{
		return InsuredAge;
	}
	/** 被保人年龄限制 */
	public void setInsuredAge(int aInsuredAge)
	{
		InsuredAge = aInsuredAge;
	}
	/** 被保人年龄限制<P>被保人年龄限制 */
	public void setInsuredAge(String aInsuredAge)
	{
		if (aInsuredAge != null && !aInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAge);
			int i = tInteger.intValue();
			InsuredAge = i;
		}
	}

	/** 备用字段1<P> */
	public String getStandbyFlag1()
	{
		if (StandbyFlag1 != null && !StandbyFlag1.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag1 = StrTool.unicodeToGBK(StandbyFlag1);
		}
		return StandbyFlag1;
	}
	/** 备用字段1 */
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/** 备用字段2<P> */
	public String getStandbyFlag2()
	{
		if (StandbyFlag2 != null && !StandbyFlag2.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag2 = StrTool.unicodeToGBK(StandbyFlag2);
		}
		return StandbyFlag2;
	}
	/** 备用字段2 */
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	/** 备用字段3<P> */
	public String getStandbyFlag3()
	{
		if (StandbyFlag3 != null && !StandbyFlag3.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandbyFlag3 = StrTool.unicodeToGBK(StandbyFlag3);
		}
		return StandbyFlag3;
	}
	/** 备用字段3 */
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	/** 生效日期<P>序号 */
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	/** 生效日期 */
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	/** 生效日期<P>序号 */
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	/** 停效日期<P>保额算保费 */
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	/** 停效日期 */
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	/** 停效日期<P>保额算保费 */
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}


	/**
	* 使用另外一个 LMRiskParamsSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMRiskParamsSchema aLMRiskParamsSchema)
	{
		this.RiskAlias = aLMRiskParamsSchema.getRiskAlias();
		this.RiskCode = aLMRiskParamsSchema.getRiskCode();
		this.PayIntv = aLMRiskParamsSchema.getPayIntv();
		this.PayEndYear = aLMRiskParamsSchema.getPayEndYear();
		this.PayEndYearFlag = aLMRiskParamsSchema.getPayEndYearFlag();
		this.InsuYear = aLMRiskParamsSchema.getInsuYear();
		this.InsuYearFlag = aLMRiskParamsSchema.getInsuYearFlag();
		this.GetIntv = aLMRiskParamsSchema.getGetIntv();
		this.GetYear = fDate.getDate( aLMRiskParamsSchema.getGetYear());
		this.GetYeatFlag = aLMRiskParamsSchema.getGetYeatFlag();
		this.InsuredAgeFlag = aLMRiskParamsSchema.getInsuredAgeFlag();
		this.InsuredAge = aLMRiskParamsSchema.getInsuredAge();
		this.StandbyFlag1 = aLMRiskParamsSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLMRiskParamsSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLMRiskParamsSchema.getStandbyFlag3();
		this.StartDate = fDate.getDate( aLMRiskParamsSchema.getStartDate());
		this.EndDate = fDate.getDate( aLMRiskParamsSchema.getEndDate());
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
			if( rs.getString("RiskAlias") == null )
				this.RiskAlias = null;
			else
				this.RiskAlias = rs.getString("RiskAlias").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.PayIntv = rs.getInt("PayIntv");
			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			if( rs.getString("GetIntv") == null )
				this.GetIntv = null;
			else
				this.GetIntv = rs.getString("GetIntv").trim();

			this.GetYear = rs.getDate("GetYear");
			if( rs.getString("GetYeatFlag") == null )
				this.GetYeatFlag = null;
			else
				this.GetYeatFlag = rs.getString("GetYeatFlag").trim();

			if( rs.getString("InsuredAgeFlag") == null )
				this.InsuredAgeFlag = null;
			else
				this.InsuredAgeFlag = rs.getString("InsuredAgeFlag").trim();

			this.InsuredAge = rs.getInt("InsuredAge");
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

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskParamsSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMRiskParamsSchema getSchema()
	{
		LMRiskParamsSchema aLMRiskParamsSchema = new LMRiskParamsSchema();
		aLMRiskParamsSchema.setSchema(this);
		return aLMRiskParamsSchema;
	}

	public LMRiskParamsDB getDB()
	{
		LMRiskParamsDB aDBOper = new LMRiskParamsDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskParams描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskAlias) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayIntv) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuYearFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetIntv) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( GetYear )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GetYeatFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredAgeFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredAge) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandbyFlag3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( EndDate )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskParams>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GetIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetYear = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			GetYeatFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InsuredAgeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskParamsSchema";
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
		if (FCode.equals("RiskAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAlias));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equals("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equals("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equals("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equals("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equals("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equals("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetYear()));
		}
		if (FCode.equals("GetYeatFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYeatFlag));
		}
		if (FCode.equals("InsuredAgeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAgeFlag));
		}
		if (FCode.equals("InsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAge));
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
		if (FCode.equals("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(RiskAlias);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 3:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 5:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GetIntv);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetYear()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetYeatFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsuredAgeFlag);
				break;
			case 11:
				strFieldValue = String.valueOf(InsuredAge);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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

		if (FCode.equals("RiskAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAlias = FValue.trim();
			}
			else
				RiskAlias = null;
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
		if (FCode.equals("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
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
		if (FCode.equals("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
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
		if (FCode.equals("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equals("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetIntv = FValue.trim();
			}
			else
				GetIntv = null;
		}
		if (FCode.equals("GetYear"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetYear = fDate.getDate( FValue );
			}
			else
				GetYear = null;
		}
		if (FCode.equals("GetYeatFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYeatFlag = FValue.trim();
			}
			else
				GetYeatFlag = null;
		}
		if (FCode.equals("InsuredAgeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredAgeFlag = FValue.trim();
			}
			else
				InsuredAgeFlag = null;
		}
		if (FCode.equals("InsuredAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredAge = i;
			}
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
		if (FCode.equals("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskParamsSchema other = (LMRiskParamsSchema)otherObject;
		return
			RiskAlias.equals(other.getRiskAlias())
			&& RiskCode.equals(other.getRiskCode())
			&& PayIntv == other.getPayIntv()
			&& PayEndYear == other.getPayEndYear()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& InsuYear == other.getInsuYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& GetIntv.equals(other.getGetIntv())
			&& fDate.getString(GetYear).equals(other.getGetYear())
			&& GetYeatFlag.equals(other.getGetYeatFlag())
			&& InsuredAgeFlag.equals(other.getInsuredAgeFlag())
			&& InsuredAge == other.getInsuredAge()
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate());
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
		if( strFieldName.equals("RiskAlias") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 2;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 3;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 4;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 5;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 6;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 7;
		}
		if( strFieldName.equals("GetYear") ) {
			return 8;
		}
		if( strFieldName.equals("GetYeatFlag") ) {
			return 9;
		}
		if( strFieldName.equals("InsuredAgeFlag") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return 11;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 13;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 14;
		}
		if( strFieldName.equals("StartDate") ) {
			return 15;
		}
		if( strFieldName.equals("EndDate") ) {
			return 16;
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
				strFieldName = "RiskAlias";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "PayIntv";
				break;
			case 3:
				strFieldName = "PayEndYear";
				break;
			case 4:
				strFieldName = "PayEndYearFlag";
				break;
			case 5:
				strFieldName = "InsuYear";
				break;
			case 6:
				strFieldName = "InsuYearFlag";
				break;
			case 7:
				strFieldName = "GetIntv";
				break;
			case 8:
				strFieldName = "GetYear";
				break;
			case 9:
				strFieldName = "GetYeatFlag";
				break;
			case 10:
				strFieldName = "InsuredAgeFlag";
				break;
			case 11:
				strFieldName = "InsuredAge";
				break;
			case 12:
				strFieldName = "StandbyFlag1";
				break;
			case 13:
				strFieldName = "StandbyFlag2";
				break;
			case 14:
				strFieldName = "StandbyFlag3";
				break;
			case 15:
				strFieldName = "StartDate";
				break;
			case 16:
				strFieldName = "EndDate";
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
		if( strFieldName.equals("RiskAlias") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetYeatFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredAgeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
