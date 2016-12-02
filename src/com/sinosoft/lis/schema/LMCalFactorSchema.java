/*
 * <p>ClassName: LMCalFactorSchema </p>
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
import com.sinosoft.lis.db.LMCalFactorDB;

public class LMCalFactorSchema implements Schema
{
	// @Field
	/** 算法编码 */
	private String CalCode;
	/** 要素编码 */
	private String FactorCode;
	/** 要素名称 */
	private String FactorName;
	/** 要素类型 */
	private String FactorType;
	/** 要素优先级 */
	private String FactorGrade;
	/** 要素算法编码 */
	private String FactorCalCode;
	/** 默认值 */
	private String FactorDefault;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCalFactorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CalCode";
		pk[1] = "FactorCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 算法编码<P> */
	public String getCalCode()
	{
		if (CalCode != null && !CalCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalCode = StrTool.unicodeToGBK(CalCode);
		}
		return CalCode;
	}
	/** 算法编码 */
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/** 要素编码<P> */
	public String getFactorCode()
	{
		if (FactorCode != null && !FactorCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorCode = StrTool.unicodeToGBK(FactorCode);
		}
		return FactorCode;
	}
	/** 要素编码 */
	public void setFactorCode(String aFactorCode)
	{
		FactorCode = aFactorCode;
	}
	/** 要素名称<P> */
	public String getFactorName()
	{
		if (FactorName != null && !FactorName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorName = StrTool.unicodeToGBK(FactorName);
		}
		return FactorName;
	}
	/** 要素名称 */
	public void setFactorName(String aFactorName)
	{
		FactorName = aFactorName;
	}
	/** 要素类型<P>1--基本要素、2--扩展要素，或别的分类。 */
	public String getFactorType()
	{
		if (FactorType != null && !FactorType.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorType = StrTool.unicodeToGBK(FactorType);
		}
		return FactorType;
	}
	/** 要素类型 */
	public void setFactorType(String aFactorType)
	{
		FactorType = aFactorType;
	}
	/** 要素优先级<P>1--最高级 2--高级 ......类推 */
	public String getFactorGrade()
	{
		if (FactorGrade != null && !FactorGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorGrade = StrTool.unicodeToGBK(FactorGrade);
		}
		return FactorGrade;
	}
	/** 要素优先级 */
	public void setFactorGrade(String aFactorGrade)
	{
		FactorGrade = aFactorGrade;
	}
	/** 要素算法编码<P> */
	public String getFactorCalCode()
	{
		if (FactorCalCode != null && !FactorCalCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorCalCode = StrTool.unicodeToGBK(FactorCalCode);
		}
		return FactorCalCode;
	}
	/** 要素算法编码 */
	public void setFactorCalCode(String aFactorCalCode)
	{
		FactorCalCode = aFactorCalCode;
	}
	/** 默认值<P> */
	public String getFactorDefault()
	{
		if (FactorDefault != null && !FactorDefault.equals("") && SysConst.CHANGECHARSET == true)
		{
			FactorDefault = StrTool.unicodeToGBK(FactorDefault);
		}
		return FactorDefault;
	}
	/** 默认值 */
	public void setFactorDefault(String aFactorDefault)
	{
		FactorDefault = aFactorDefault;
	}

	/**
	* 使用另外一个 LMCalFactorSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMCalFactorSchema aLMCalFactorSchema)
	{
		this.CalCode = aLMCalFactorSchema.getCalCode();
		this.FactorCode = aLMCalFactorSchema.getFactorCode();
		this.FactorName = aLMCalFactorSchema.getFactorName();
		this.FactorType = aLMCalFactorSchema.getFactorType();
		this.FactorGrade = aLMCalFactorSchema.getFactorGrade();
		this.FactorCalCode = aLMCalFactorSchema.getFactorCalCode();
		this.FactorDefault = aLMCalFactorSchema.getFactorDefault();
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
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("FactorType") == null )
				this.FactorType = null;
			else
				this.FactorType = rs.getString("FactorType").trim();

			if( rs.getString("FactorGrade") == null )
				this.FactorGrade = null;
			else
				this.FactorGrade = rs.getString("FactorGrade").trim();

			if( rs.getString("FactorCalCode") == null )
				this.FactorCalCode = null;
			else
				this.FactorCalCode = rs.getString("FactorCalCode").trim();

			if( rs.getString("FactorDefault") == null )
				this.FactorDefault = null;
			else
				this.FactorDefault = rs.getString("FactorDefault").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMCalFactorSchema getSchema()
	{
		LMCalFactorSchema aLMCalFactorSchema = new LMCalFactorSchema();
		aLMCalFactorSchema.setSchema(this);
		return aLMCalFactorSchema;
	}

	public LMCalFactorDB getDB()
	{
		LMCalFactorDB aDBOper = new LMCalFactorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactor描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorCalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FactorDefault) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactor>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FactorCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FactorDefault = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSchema";
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
		if (FCode.equals("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equals("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equals("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equals("FactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorType));
		}
		if (FCode.equals("FactorGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorGrade));
		}
		if (FCode.equals("FactorCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCalCode));
		}
		if (FCode.equals("FactorDefault"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorDefault));
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
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FactorGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FactorDefault);
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

		if (FCode.equals("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equals("FactorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCode = FValue.trim();
			}
			else
				FactorCode = null;
		}
		if (FCode.equals("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equals("FactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorType = FValue.trim();
			}
			else
				FactorType = null;
		}
		if (FCode.equals("FactorGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorGrade = FValue.trim();
			}
			else
				FactorGrade = null;
		}
		if (FCode.equals("FactorCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCalCode = FValue.trim();
			}
			else
				FactorCalCode = null;
		}
		if (FCode.equals("FactorDefault"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorDefault = FValue.trim();
			}
			else
				FactorDefault = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCalFactorSchema other = (LMCalFactorSchema)otherObject;
		return
			CalCode.equals(other.getCalCode())
			&& FactorCode.equals(other.getFactorCode())
			&& FactorName.equals(other.getFactorName())
			&& FactorType.equals(other.getFactorType())
			&& FactorGrade.equals(other.getFactorGrade())
			&& FactorCalCode.equals(other.getFactorCalCode())
			&& FactorDefault.equals(other.getFactorDefault());
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
		if( strFieldName.equals("CalCode") ) {
			return 0;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 1;
		}
		if( strFieldName.equals("FactorName") ) {
			return 2;
		}
		if( strFieldName.equals("FactorType") ) {
			return 3;
		}
		if( strFieldName.equals("FactorGrade") ) {
			return 4;
		}
		if( strFieldName.equals("FactorCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("FactorDefault") ) {
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
				strFieldName = "CalCode";
				break;
			case 1:
				strFieldName = "FactorCode";
				break;
			case 2:
				strFieldName = "FactorName";
				break;
			case 3:
				strFieldName = "FactorType";
				break;
			case 4:
				strFieldName = "FactorGrade";
				break;
			case 5:
				strFieldName = "FactorCalCode";
				break;
			case 6:
				strFieldName = "FactorDefault";
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
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorDefault") ) {
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
