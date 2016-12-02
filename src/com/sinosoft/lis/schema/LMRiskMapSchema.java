/*
 * <p>ClassName: LMRiskMapSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-05
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMRiskMapDB;

public class LMRiskMapSchema implements Schema
{
	// @Field
	/** 关系类型 */
	private String CodeType;
	/** 机构代码 */
	private String ComCode;
	/** Ybt代码 */
	private String InsuCode;
	/** 活动代码 */
	private String ActivityCode;
	/** 银行代码 */
	private String BankCode;
	/** 起售日期 */
	private String StartDate;
	/** 终止日期 */
	private String EndDate;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "CodeType";
		pk[1] = "ComCode";
		pk[2] = "InsuCode";
		pk[3] = "BankCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 关系类型<P> */
	public String getCodeType()
	{
		if (CodeType != null && !CodeType.equals("") && SysConst.CHANGECHARSET == true)
		{
			CodeType = StrTool.unicodeToGBK(CodeType);
		}
		return CodeType;
	}
	/** 关系类型 */
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	/** 机构代码<P> */
	public String getComCode()
	{
		if (ComCode != null && !ComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComCode = StrTool.unicodeToGBK(ComCode);
		}
		return ComCode;
	}
	/** 机构代码 */
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	/** Ybt代码<P> */
	public String getInsuCode()
	{
		if (InsuCode != null && !InsuCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuCode = StrTool.unicodeToGBK(InsuCode);
		}
		return InsuCode;
	}
	/** Ybt代码 */
	public void setInsuCode(String aInsuCode)
	{
		InsuCode = aInsuCode;
	}
	/** 活动代码<P> */
	public String getActivityCode()
	{
		if (ActivityCode != null && !ActivityCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ActivityCode = StrTool.unicodeToGBK(ActivityCode);
		}
		return ActivityCode;
	}
	/** 活动代码 */
	public void setActivityCode(String aActivityCode)
	{
		ActivityCode = aActivityCode;
	}
	/** 银行代码<P> */
	public String getBankCode()
	{
		if (BankCode != null && !BankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankCode = StrTool.unicodeToGBK(BankCode);
		}
		return BankCode;
	}
	/** 银行代码 */
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/** 起售日期<P> */
	public String getStartDate()
	{
		if (StartDate != null && !StartDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			StartDate = StrTool.unicodeToGBK(StartDate);
		}
		return StartDate;
	}
	/** 起售日期 */
	public void setStartDate(String aStartDate)
	{
		StartDate = aStartDate;
	}
	/** 终止日期<P> */
	public String getEndDate()
	{
		if (EndDate != null && !EndDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			EndDate = StrTool.unicodeToGBK(EndDate);
		}
		return EndDate;
	}
	/** 终止日期 */
	public void setEndDate(String aEndDate)
	{
		EndDate = aEndDate;
	}

	/**
	* 使用另外一个 LMRiskMapSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMRiskMapSchema aLMRiskMapSchema)
	{
		this.CodeType = aLMRiskMapSchema.getCodeType();
		this.ComCode = aLMRiskMapSchema.getComCode();
		this.InsuCode = aLMRiskMapSchema.getInsuCode();
		this.ActivityCode = aLMRiskMapSchema.getActivityCode();
		this.BankCode = aLMRiskMapSchema.getBankCode();
		this.StartDate = aLMRiskMapSchema.getStartDate();
		this.EndDate = aLMRiskMapSchema.getEndDate();
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
			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("InsuCode") == null )
				this.InsuCode = null;
			else
				this.InsuCode = rs.getString("InsuCode").trim();

			if( rs.getString("ActivityCode") == null )
				this.ActivityCode = null;
			else
				this.ActivityCode = rs.getString("ActivityCode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("StartDate") == null )
				this.StartDate = null;
			else
				this.StartDate = rs.getString("StartDate").trim();

			if( rs.getString("EndDate") == null )
				this.EndDate = null;
			else
				this.EndDate = rs.getString("EndDate").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMRiskMapSchema getSchema()
	{
		LMRiskMapSchema aLMRiskMapSchema = new LMRiskMapSchema();
		aLMRiskMapSchema.setSchema(this);
		return aLMRiskMapSchema;
	}

	public LMRiskMapDB getDB()
	{
		LMRiskMapDB aDBOper = new LMRiskMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskMap描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CodeType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ActivityCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StartDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(EndDate) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ActivityCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			EndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskMapSchema";
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
		if (FCode.equals("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equals("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equals("InsuCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuCode));
		}
		if (FCode.equals("ActivityCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityCode));
		}
		if (FCode.equals("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equals("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDate));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDate));
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
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ActivityCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StartDate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EndDate);
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

		if (FCode.equals("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equals("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equals("InsuCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuCode = FValue.trim();
			}
			else
				InsuCode = null;
		}
		if (FCode.equals("ActivityCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityCode = FValue.trim();
			}
			else
				ActivityCode = null;
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
		if (FCode.equals("StartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDate = FValue.trim();
			}
			else
				StartDate = null;
		}
		if (FCode.equals("EndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDate = FValue.trim();
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
		LMRiskMapSchema other = (LMRiskMapSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& ComCode.equals(other.getComCode())
			&& InsuCode.equals(other.getInsuCode())
			&& ActivityCode.equals(other.getActivityCode())
			&& BankCode.equals(other.getBankCode())
			&& StartDate.equals(other.getStartDate())
			&& EndDate.equals(other.getEndDate());
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
		if( strFieldName.equals("CodeType") ) {
			return 0;
		}
		if( strFieldName.equals("ComCode") ) {
			return 1;
		}
		if( strFieldName.equals("InsuCode") ) {
			return 2;
		}
		if( strFieldName.equals("ActivityCode") ) {
			return 3;
		}
		if( strFieldName.equals("BankCode") ) {
			return 4;
		}
		if( strFieldName.equals("StartDate") ) {
			return 5;
		}
		if( strFieldName.equals("EndDate") ) {
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
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "ComCode";
				break;
			case 2:
				strFieldName = "InsuCode";
				break;
			case 3:
				strFieldName = "ActivityCode";
				break;
			case 4:
				strFieldName = "BankCode";
				break;
			case 5:
				strFieldName = "StartDate";
				break;
			case 6:
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
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
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
