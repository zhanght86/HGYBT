/*
 * <p>ClassName: LDMenuGrpToMenuSchema </p>
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
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;

public class LDMenuGrpToMenuSchema implements Schema
{
	// @Field
	/** 菜单组编码 */
	private String MenuGrpCode;
	/** 菜单编码 */
	private String NodeCode;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMenuGrpToMenuSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "MenuGrpCode";
		pk[1] = "NodeCode";

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
	/** 菜单编码<P>菜单编码 */
	public String getNodeCode()
	{
		if (NodeCode != null && !NodeCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeCode = StrTool.unicodeToGBK(NodeCode);
		}
		return NodeCode;
	}
	/** 菜单编码 */
	public void setNodeCode(String aNodeCode)
	{
		NodeCode = aNodeCode;
	}

	/**
	* 使用另外一个 LDMenuGrpToMenuSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDMenuGrpToMenuSchema aLDMenuGrpToMenuSchema)
	{
		this.MenuGrpCode = aLDMenuGrpToMenuSchema.getMenuGrpCode();
		this.NodeCode = aLDMenuGrpToMenuSchema.getNodeCode();
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

			if( rs.getString("NodeCode") == null )
				this.NodeCode = null;
			else
				this.NodeCode = rs.getString("NodeCode").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuGrpToMenuSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDMenuGrpToMenuSchema getSchema()
	{
		LDMenuGrpToMenuSchema aLDMenuGrpToMenuSchema = new LDMenuGrpToMenuSchema();
		aLDMenuGrpToMenuSchema.setSchema(this);
		return aLDMenuGrpToMenuSchema;
	}

	public LDMenuGrpToMenuDB getDB()
	{
		LDMenuGrpToMenuDB aDBOper = new LDMenuGrpToMenuDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrpToMenu描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(MenuGrpCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeCode) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrpToMenu>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MenuGrpCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuGrpToMenuSchema";
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
		if (FCode.equals("NodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeCode));
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
				strFieldValue = StrTool.GBKToUnicode(NodeCode);
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
		if (FCode.equals("NodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeCode = FValue.trim();
			}
			else
				NodeCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMenuGrpToMenuSchema other = (LDMenuGrpToMenuSchema)otherObject;
		return
			MenuGrpCode.equals(other.getMenuGrpCode())
			&& NodeCode.equals(other.getNodeCode());
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
		if( strFieldName.equals("NodeCode") ) {
			return 1;
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
				strFieldName = "NodeCode";
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
		if( strFieldName.equals("NodeCode") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
