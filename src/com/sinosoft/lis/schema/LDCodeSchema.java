/*
 * <p>ClassName: LDCodeSchema </p>
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
import com.sinosoft.lis.db.LDCodeDB;

public class LDCodeSchema implements Schema
{
	// @Field
	/** 代码类型 */
	private String CodeType;
	/** 代码 */
	private String Code;
	/** 代码名称 */
	private String CodeName;
	/** 代码别名 */
	private String CodeAlias;
	/** 机构代码 */
	private String ComCode;
	/** 其它标志 */
	private String OtherSign;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CodeType";
		pk[1] = "Code";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 代码类型<P>代码类型 */
	public String getCodeType()
	{
		if (CodeType != null && !CodeType.equals("") && SysConst.CHANGECHARSET == true)
		{
			CodeType = StrTool.unicodeToGBK(CodeType);
		}
		return CodeType;
	}
	/** 代码类型 */
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	/** 代码<P>代码 */
	public String getCode()
	{
		if (Code != null && !Code.equals("") && SysConst.CHANGECHARSET == true)
		{
			Code = StrTool.unicodeToGBK(Code);
		}
		return Code;
	}
	/** 代码 */
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	/** 代码名称<P>代码名称 */
	public String getCodeName()
	{
		if (CodeName != null && !CodeName.equals("") && SysConst.CHANGECHARSET == true)
		{
			CodeName = StrTool.unicodeToGBK(CodeName);
		}
		return CodeName;
	}
	/** 代码名称 */
	public void setCodeName(String aCodeName)
	{
		CodeName = aCodeName;
	}
	/** 代码别名<P>代码别名 */
	public String getCodeAlias()
	{
		if (CodeAlias != null && !CodeAlias.equals("") && SysConst.CHANGECHARSET == true)
		{
			CodeAlias = StrTool.unicodeToGBK(CodeAlias);
		}
		return CodeAlias;
	}
	/** 代码别名 */
	public void setCodeAlias(String aCodeAlias)
	{
		CodeAlias = aCodeAlias;
	}
	/** 机构代码<P>机构代码 */
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
	/** 其它标志<P>其它标志 */
	public String getOtherSign()
	{
		if (OtherSign != null && !OtherSign.equals("") && SysConst.CHANGECHARSET == true)
		{
			OtherSign = StrTool.unicodeToGBK(OtherSign);
		}
		return OtherSign;
	}
	/** 其它标志 */
	public void setOtherSign(String aOtherSign)
	{
		OtherSign = aOtherSign;
	}

	/**
	* 使用另外一个 LDCodeSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDCodeSchema aLDCodeSchema)
	{
		this.CodeType = aLDCodeSchema.getCodeType();
		this.Code = aLDCodeSchema.getCode();
		this.CodeName = aLDCodeSchema.getCodeName();
		this.CodeAlias = aLDCodeSchema.getCodeAlias();
		this.ComCode = aLDCodeSchema.getComCode();
		this.OtherSign = aLDCodeSchema.getOtherSign();
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

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("CodeName") == null )
				this.CodeName = null;
			else
				this.CodeName = rs.getString("CodeName").trim();

			if( rs.getString("CodeAlias") == null )
				this.CodeAlias = null;
			else
				this.CodeAlias = rs.getString("CodeAlias").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OtherSign") == null )
				this.OtherSign = null;
			else
				this.OtherSign = rs.getString("OtherSign").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDCodeSchema getSchema()
	{
		LDCodeSchema aLDCodeSchema = new LDCodeSchema();
		aLDCodeSchema.setSchema(this);
		return aLDCodeSchema;
	}

	public LDCodeDB getDB()
	{
		LDCodeDB aDBOper = new LDCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CodeType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Code) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CodeName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CodeAlias) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OtherSign) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CodeAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeSchema";
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
		if (FCode.equals("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equals("CodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
		}
		if (FCode.equals("CodeAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeAlias));
		}
		if (FCode.equals("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equals("OtherSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherSign));
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
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CodeAlias);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherSign);
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
		if (FCode.equals("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equals("CodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName = FValue.trim();
			}
			else
				CodeName = null;
		}
		if (FCode.equals("CodeAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeAlias = FValue.trim();
			}
			else
				CodeAlias = null;
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
		if (FCode.equals("OtherSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherSign = FValue.trim();
			}
			else
				OtherSign = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCodeSchema other = (LDCodeSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& Code.equals(other.getCode())
			&& CodeName.equals(other.getCodeName())
			&& CodeAlias.equals(other.getCodeAlias())
			&& ComCode.equals(other.getComCode())
			&& OtherSign.equals(other.getOtherSign());
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
		if( strFieldName.equals("Code") ) {
			return 1;
		}
		if( strFieldName.equals("CodeName") ) {
			return 2;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return 3;
		}
		if( strFieldName.equals("ComCode") ) {
			return 4;
		}
		if( strFieldName.equals("OtherSign") ) {
			return 5;
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
				strFieldName = "Code";
				break;
			case 2:
				strFieldName = "CodeName";
				break;
			case 3:
				strFieldName = "CodeAlias";
				break;
			case 4:
				strFieldName = "ComCode";
				break;
			case 5:
				strFieldName = "OtherSign";
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
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherSign") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
