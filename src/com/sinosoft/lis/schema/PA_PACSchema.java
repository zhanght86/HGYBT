/*
 * <p>ClassName: PA_PACSchema </p>
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
import com.sinosoft.lis.db.PA_PACDB;

public class PA_PACSchema implements Schema
{
	// @Field
	/** 产品代码 */
	private String RiskCode;
	/** 被保人年龄 */
	private int InsuredAge;
	/** 被保人性别 */
	private String InsuredSex;
	/** 缴费方式 */
	private String PayIntv;
	/** 缴费年期标志 */
	private String PayEndYearFlag;
	/** 缴费年期 */
	private int PayEndYear;
	/** 吸烟标志 */
	private String SmokeFlag;
	/** 精算险种代码 */
	private String AXARiskCode;
	/** 费率 */
	private double PremRate;
	/** 计算单位 */
	private int PremScale;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PA_PACSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "RiskCode";
		pk[1] = "InsuredAge";
		pk[2] = "InsuredSex";
		pk[3] = "PayIntv";
		pk[4] = "PayEndYearFlag";
		pk[5] = "PayEndYear";
		pk[6] = "SmokeFlag";
		pk[7] = "AXARiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 产品代码<P>产品代码 */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 产品代码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 被保人年龄<P>被保人年龄 */
	public int getInsuredAge()
	{
		return InsuredAge;
	}
	/** 被保人年龄 */
	public void setInsuredAge(int aInsuredAge)
	{
		InsuredAge = aInsuredAge;
	}
	/** 被保人年龄<P>被保人年龄 */
	public void setInsuredAge(String aInsuredAge)
	{
		if (aInsuredAge != null && !aInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAge);
			int i = tInteger.intValue();
			InsuredAge = i;
		}
	}

	/** 被保人性别<P>M-男 F-女 */
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
	/** 缴费方式<P>1-年 2-月 3-半年 4-季 5-趸 6-不定期 9-其他 */
	public String getPayIntv()
	{
		if (PayIntv != null && !PayIntv.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayIntv = StrTool.unicodeToGBK(PayIntv);
		}
		return PayIntv;
	}
	/** 缴费方式 */
	public void setPayIntv(String aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/** 缴费年期标志<P> */
	public String getPayEndYearFlag()
	{
		if (PayEndYearFlag != null && !PayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYearFlag = StrTool.unicodeToGBK(PayEndYearFlag);
		}
		return PayEndYearFlag;
	}
	/** 缴费年期标志 */
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	/** 缴费年期<P> */
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	/** 缴费年期 */
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** 缴费年期<P> */
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/** 吸烟标志<P>Y-是 N-否 */
	public String getSmokeFlag()
	{
		if (SmokeFlag != null && !SmokeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SmokeFlag = StrTool.unicodeToGBK(SmokeFlag);
		}
		return SmokeFlag;
	}
	/** 吸烟标志 */
	public void setSmokeFlag(String aSmokeFlag)
	{
		SmokeFlag = aSmokeFlag;
	}
	/** 精算险种代码<P> */
	public String getAXARiskCode()
	{
		if (AXARiskCode != null && !AXARiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXARiskCode = StrTool.unicodeToGBK(AXARiskCode);
		}
		return AXARiskCode;
	}
	/** 精算险种代码 */
	public void setAXARiskCode(String aAXARiskCode)
	{
		AXARiskCode = aAXARiskCode;
	}
	/** 费率<P> */
	public double getPremRate()
	{
		return PremRate;
	}
	/** 费率 */
	public void setPremRate(double aPremRate)
	{
		PremRate = aPremRate;
	}
	/** 费率<P> */
	public void setPremRate(String aPremRate)
	{
		if (aPremRate != null && !aPremRate.equals(""))
		{
			Double tDouble = new Double(aPremRate);
			double d = tDouble.doubleValue();
			PremRate = d;
		}
	}

	/** 计算单位<P> */
	public int getPremScale()
	{
		return PremScale;
	}
	/** 计算单位 */
	public void setPremScale(int aPremScale)
	{
		PremScale = aPremScale;
	}
	/** 计算单位<P> */
	public void setPremScale(String aPremScale)
	{
		if (aPremScale != null && !aPremScale.equals(""))
		{
			Integer tInteger = new Integer(aPremScale);
			int i = tInteger.intValue();
			PremScale = i;
		}
	}


	/**
	* 使用另外一个 PA_PACSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(PA_PACSchema aPA_PACSchema)
	{
		this.RiskCode = aPA_PACSchema.getRiskCode();
		this.InsuredAge = aPA_PACSchema.getInsuredAge();
		this.InsuredSex = aPA_PACSchema.getInsuredSex();
		this.PayIntv = aPA_PACSchema.getPayIntv();
		this.PayEndYearFlag = aPA_PACSchema.getPayEndYearFlag();
		this.PayEndYear = aPA_PACSchema.getPayEndYear();
		this.SmokeFlag = aPA_PACSchema.getSmokeFlag();
		this.AXARiskCode = aPA_PACSchema.getAXARiskCode();
		this.PremRate = aPA_PACSchema.getPremRate();
		this.PremScale = aPA_PACSchema.getPremScale();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.InsuredAge = rs.getInt("InsuredAge");
			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			if( rs.getString("PayIntv") == null )
				this.PayIntv = null;
			else
				this.PayIntv = rs.getString("PayIntv").trim();

			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("AXARiskCode") == null )
				this.AXARiskCode = null;
			else
				this.AXARiskCode = rs.getString("AXARiskCode").trim();

			this.PremRate = rs.getDouble("PremRate");
			this.PremScale = rs.getInt("PremScale");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PA_PACSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public PA_PACSchema getSchema()
	{
		PA_PACSchema aPA_PACSchema = new PA_PACSchema();
		aPA_PACSchema.setSchema(this);
		return aPA_PACSchema;
	}

	public PA_PACDB getDB()
	{
		PA_PACDB aDBOper = new PA_PACDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPA_PAC描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredAge) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredSex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayIntv) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SmokeFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXARiskCode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PremRate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PremScale);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPA_PAC>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AXARiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			PremScale= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PA_PACSchema";
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
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("InsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAge));
		}
		if (FCode.equals("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equals("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equals("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equals("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equals("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equals("AXARiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXARiskCode));
		}
		if (FCode.equals("PremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremRate));
		}
		if (FCode.equals("PremScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremScale));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = String.valueOf(InsuredAge);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayIntv);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 5:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AXARiskCode);
				break;
			case 8:
				strFieldValue = String.valueOf(PremRate);
				break;
			case 9:
				strFieldValue = String.valueOf(PremScale);
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

		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equals("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equals("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayIntv = FValue.trim();
			}
			else
				PayIntv = null;
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
		if (FCode.equals("SmokeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokeFlag = FValue.trim();
			}
			else
				SmokeFlag = null;
		}
		if (FCode.equals("AXARiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXARiskCode = FValue.trim();
			}
			else
				AXARiskCode = null;
		}
		if (FCode.equals("PremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremRate = d;
			}
		}
		if (FCode.equals("PremScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PremScale = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PA_PACSchema other = (PA_PACSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& InsuredAge == other.getInsuredAge()
			&& InsuredSex.equals(other.getInsuredSex())
			&& PayIntv.equals(other.getPayIntv())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& AXARiskCode.equals(other.getAXARiskCode())
			&& PremRate == other.getPremRate()
			&& PremScale == other.getPremScale();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return 1;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 2;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 3;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 5;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AXARiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("PremRate") ) {
			return 8;
		}
		if( strFieldName.equals("PremScale") ) {
			return 9;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "InsuredAge";
				break;
			case 2:
				strFieldName = "InsuredSex";
				break;
			case 3:
				strFieldName = "PayIntv";
				break;
			case 4:
				strFieldName = "PayEndYearFlag";
				break;
			case 5:
				strFieldName = "PayEndYear";
				break;
			case 6:
				strFieldName = "SmokeFlag";
				break;
			case 7:
				strFieldName = "AXARiskCode";
				break;
			case 8:
				strFieldName = "PremRate";
				break;
			case 9:
				strFieldName = "PremScale";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXARiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremScale") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
