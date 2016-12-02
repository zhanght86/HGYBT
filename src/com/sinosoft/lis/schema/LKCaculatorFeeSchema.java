/*
 * <p>ClassName: LKCaculatorFeeSchema </p>
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
import com.sinosoft.lis.db.LKCaculatorFeeDB;

public class LKCaculatorFeeSchema implements Schema
{
	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 计算标志 */
	private String CalFlag;
	/** 费率计算表 */
	private String CalTable;
	/** 现价计算表 */
	private String CashTable;
	/** 备用字段1 */
	private String Temp1;
	/** 备用字段2 */
	private String Temp2;
	/** 备用字段3 */
	private String Temp3;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKCaculatorFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 险种代码<P> */
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
	/** 计算标志<P> */
	public String getCalFlag()
	{
		if (CalFlag != null && !CalFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalFlag = StrTool.unicodeToGBK(CalFlag);
		}
		return CalFlag;
	}
	/** 计算标志 */
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/** 费率计算表<P> */
	public String getCalTable()
	{
		if (CalTable != null && !CalTable.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalTable = StrTool.unicodeToGBK(CalTable);
		}
		return CalTable;
	}
	/** 费率计算表 */
	public void setCalTable(String aCalTable)
	{
		CalTable = aCalTable;
	}
	/** 现价计算表<P> */
	public String getCashTable()
	{
		if (CashTable != null && !CashTable.equals("") && SysConst.CHANGECHARSET == true)
		{
			CashTable = StrTool.unicodeToGBK(CashTable);
		}
		return CashTable;
	}
	/** 现价计算表 */
	public void setCashTable(String aCashTable)
	{
		CashTable = aCashTable;
	}
	/** 备用字段1<P> */
	public String getTemp1()
	{
		if (Temp1 != null && !Temp1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp1 = StrTool.unicodeToGBK(Temp1);
		}
		return Temp1;
	}
	/** 备用字段1 */
	public void setTemp1(String aTemp1)
	{
		Temp1 = aTemp1;
	}
	/** 备用字段2<P> */
	public String getTemp2()
	{
		if (Temp2 != null && !Temp2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp2 = StrTool.unicodeToGBK(Temp2);
		}
		return Temp2;
	}
	/** 备用字段2 */
	public void setTemp2(String aTemp2)
	{
		Temp2 = aTemp2;
	}
	/** 备用字段3<P> */
	public String getTemp3()
	{
		if (Temp3 != null && !Temp3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp3 = StrTool.unicodeToGBK(Temp3);
		}
		return Temp3;
	}
	/** 备用字段3 */
	public void setTemp3(String aTemp3)
	{
		Temp3 = aTemp3;
	}

	/**
	* 使用另外一个 LKCaculatorFeeSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKCaculatorFeeSchema aLKCaculatorFeeSchema)
	{
		this.RiskCode = aLKCaculatorFeeSchema.getRiskCode();
		this.CalFlag = aLKCaculatorFeeSchema.getCalFlag();
		this.CalTable = aLKCaculatorFeeSchema.getCalTable();
		this.CashTable = aLKCaculatorFeeSchema.getCashTable();
		this.Temp1 = aLKCaculatorFeeSchema.getTemp1();
		this.Temp2 = aLKCaculatorFeeSchema.getTemp2();
		this.Temp3 = aLKCaculatorFeeSchema.getTemp3();
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

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("CalTable") == null )
				this.CalTable = null;
			else
				this.CalTable = rs.getString("CalTable").trim();

			if( rs.getString("CashTable") == null )
				this.CashTable = null;
			else
				this.CashTable = rs.getString("CashTable").trim();

			if( rs.getString("Temp1") == null )
				this.Temp1 = null;
			else
				this.Temp1 = rs.getString("Temp1").trim();

			if( rs.getString("Temp2") == null )
				this.Temp2 = null;
			else
				this.Temp2 = rs.getString("Temp2").trim();

			if( rs.getString("Temp3") == null )
				this.Temp3 = null;
			else
				this.Temp3 = rs.getString("Temp3").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKCaculatorFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKCaculatorFeeSchema getSchema()
	{
		LKCaculatorFeeSchema aLKCaculatorFeeSchema = new LKCaculatorFeeSchema();
		aLKCaculatorFeeSchema.setSchema(this);
		return aLKCaculatorFeeSchema;
	}

	public LKCaculatorFeeDB getDB()
	{
		LKCaculatorFeeDB aDBOper = new LKCaculatorFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKCaculatorFee描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalTable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CashTable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp3) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKCaculatorFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CashTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Temp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Temp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Temp3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKCaculatorFeeSchema";
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
		if (FCode.equals("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equals("CalTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalTable));
		}
		if (FCode.equals("CashTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashTable));
		}
		if (FCode.equals("Temp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp1));
		}
		if (FCode.equals("Temp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp2));
		}
		if (FCode.equals("Temp3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp3));
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
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CalTable);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CashTable);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Temp1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Temp2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Temp3);
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
		if (FCode.equals("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equals("CalTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalTable = FValue.trim();
			}
			else
				CalTable = null;
		}
		if (FCode.equals("CashTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CashTable = FValue.trim();
			}
			else
				CashTable = null;
		}
		if (FCode.equals("Temp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp1 = FValue.trim();
			}
			else
				Temp1 = null;
		}
		if (FCode.equals("Temp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp2 = FValue.trim();
			}
			else
				Temp2 = null;
		}
		if (FCode.equals("Temp3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp3 = FValue.trim();
			}
			else
				Temp3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKCaculatorFeeSchema other = (LKCaculatorFeeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& CalFlag.equals(other.getCalFlag())
			&& CalTable.equals(other.getCalTable())
			&& CashTable.equals(other.getCashTable())
			&& Temp1.equals(other.getTemp1())
			&& Temp2.equals(other.getTemp2())
			&& Temp3.equals(other.getTemp3());
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
		if( strFieldName.equals("CalFlag") ) {
			return 1;
		}
		if( strFieldName.equals("CalTable") ) {
			return 2;
		}
		if( strFieldName.equals("CashTable") ) {
			return 3;
		}
		if( strFieldName.equals("Temp1") ) {
			return 4;
		}
		if( strFieldName.equals("Temp2") ) {
			return 5;
		}
		if( strFieldName.equals("Temp3") ) {
			return 6;
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
				strFieldName = "CalFlag";
				break;
			case 2:
				strFieldName = "CalTable";
				break;
			case 3:
				strFieldName = "CashTable";
				break;
			case 4:
				strFieldName = "Temp1";
				break;
			case 5:
				strFieldName = "Temp2";
				break;
			case 6:
				strFieldName = "Temp3";
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
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CashTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp3") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
