/*
 * <p>ClassName: LDMenuGrpSchema </p>
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
import com.sinosoft.lis.db.LDMenuGrpDB;

public class LDMenuGrpSchema implements Schema
{
	// @Field
	/** 菜单组编码 */
	private String MenuGrpCode;
	/** 菜单组名称 */
	private String MenuGrpName;
	/** 菜单组描述 */
	private String MenuGrpDescription;
	/** 菜单标志 */
	private String MenuSign;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMenuGrpSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "MenuGrpCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 菜单组编码<P>菜单组编码 */
	public String getMenuGrpCode()
	{
		if (MenuGrpCode != null && !MenuGrpCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			MenuGrpCode = StrTool.unicodeToGBK(MenuGrpCode);
		}
		return MenuGrpCode;
	}
	/** 菜单组编码 */
	public void setMenuGrpCode(String aMenuGrpCode)
	{
		MenuGrpCode = aMenuGrpCode;
	}
	/** 菜单组名称<P>菜单组名称 */
	public String getMenuGrpName()
	{
		if (MenuGrpName != null && !MenuGrpName.equals("") && SysConst.CHANGECHARSET == true)
		{
			MenuGrpName = StrTool.unicodeToGBK(MenuGrpName);
		}
		return MenuGrpName;
	}
	/** 菜单组名称 */
	public void setMenuGrpName(String aMenuGrpName)
	{
		MenuGrpName = aMenuGrpName;
	}
	/** 菜单组描述<P>菜单组描述 */
	public String getMenuGrpDescription()
	{
		if (MenuGrpDescription != null && !MenuGrpDescription.equals("") && SysConst.CHANGECHARSET == true)
		{
			MenuGrpDescription = StrTool.unicodeToGBK(MenuGrpDescription);
		}
		return MenuGrpDescription;
	}
	/** 菜单组描述 */
	public void setMenuGrpDescription(String aMenuGrpDescription)
	{
		MenuGrpDescription = aMenuGrpDescription;
	}
	/** 菜单标志<P>菜单标志 */
	public String getMenuSign()
	{
		if (MenuSign != null && !MenuSign.equals("") && SysConst.CHANGECHARSET == true)
		{
			MenuSign = StrTool.unicodeToGBK(MenuSign);
		}
		return MenuSign;
	}
	/** 菜单标志 */
	public void setMenuSign(String aMenuSign)
	{
		MenuSign = aMenuSign;
	}
	/** 操作员<P>操作员 */
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

	/**
	* 使用另外一个 LDMenuGrpSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDMenuGrpSchema aLDMenuGrpSchema)
	{
		this.MenuGrpCode = aLDMenuGrpSchema.getMenuGrpCode();
		this.MenuGrpName = aLDMenuGrpSchema.getMenuGrpName();
		this.MenuGrpDescription = aLDMenuGrpSchema.getMenuGrpDescription();
		this.MenuSign = aLDMenuGrpSchema.getMenuSign();
		this.Operator = aLDMenuGrpSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDMenuGrpSchema.getMakeDate());
		this.MakeTime = aLDMenuGrpSchema.getMakeTime();
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
			if( rs.getString("MenuGrpCode") == null )
				this.MenuGrpCode = null;
			else
				this.MenuGrpCode = rs.getString("MenuGrpCode").trim();

			if( rs.getString("MenuGrpName") == null )
				this.MenuGrpName = null;
			else
				this.MenuGrpName = rs.getString("MenuGrpName").trim();

			if( rs.getString("MenuGrpDescription") == null )
				this.MenuGrpDescription = null;
			else
				this.MenuGrpDescription = rs.getString("MenuGrpDescription").trim();

			if( rs.getString("MenuSign") == null )
				this.MenuSign = null;
			else
				this.MenuSign = rs.getString("MenuSign").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuGrpSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDMenuGrpSchema getSchema()
	{
		LDMenuGrpSchema aLDMenuGrpSchema = new LDMenuGrpSchema();
		aLDMenuGrpSchema.setSchema(this);
		return aLDMenuGrpSchema;
	}

	public LDMenuGrpDB getDB()
	{
		LDMenuGrpDB aDBOper = new LDMenuGrpDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrp描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(MenuGrpCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MenuGrpName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MenuGrpDescription) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MenuSign) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrp>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MenuGrpCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MenuGrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MenuGrpDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MenuSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuGrpSchema";
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
		if (FCode.equals("MenuGrpCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MenuGrpCode));
		}
		if (FCode.equals("MenuGrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MenuGrpName));
		}
		if (FCode.equals("MenuGrpDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MenuGrpDescription));
		}
		if (FCode.equals("MenuSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MenuSign));
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
				strFieldValue = StrTool.GBKToUnicode(MenuGrpCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MenuGrpName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MenuGrpDescription);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MenuSign);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equals("MenuGrpCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MenuGrpCode = FValue.trim();
			}
			else
				MenuGrpCode = null;
		}
		if (FCode.equals("MenuGrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MenuGrpName = FValue.trim();
			}
			else
				MenuGrpName = null;
		}
		if (FCode.equals("MenuGrpDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MenuGrpDescription = FValue.trim();
			}
			else
				MenuGrpDescription = null;
		}
		if (FCode.equals("MenuSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MenuSign = FValue.trim();
			}
			else
				MenuSign = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMenuGrpSchema other = (LDMenuGrpSchema)otherObject;
		return
			MenuGrpCode.equals(other.getMenuGrpCode())
			&& MenuGrpName.equals(other.getMenuGrpName())
			&& MenuGrpDescription.equals(other.getMenuGrpDescription())
			&& MenuSign.equals(other.getMenuSign())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("MenuGrpCode") ) {
			return 0;
		}
		if( strFieldName.equals("MenuGrpName") ) {
			return 1;
		}
		if( strFieldName.equals("MenuGrpDescription") ) {
			return 2;
		}
		if( strFieldName.equals("MenuSign") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "MenuGrpCode";
				break;
			case 1:
				strFieldName = "MenuGrpName";
				break;
			case 2:
				strFieldName = "MenuGrpDescription";
				break;
			case 3:
				strFieldName = "MenuSign";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("MenuGrpCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MenuGrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MenuGrpDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MenuSign") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
