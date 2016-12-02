/*
 * <p>ClassName: LDMaxNoSchema </p>
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
import com.sinosoft.lis.db.LDMaxNoDB;

public class LDMaxNoSchema implements Schema
{
	// @Field
	/** 号码类型 */
	private String NoType;
	/** 号码限制条件 */
	private String NoLimit;
	/** 当前最大值 */
	private int MaxNo;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMaxNoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "NoType";
		pk[1] = "NoLimit";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 号码类型<P>号码类型 */
	public String getNoType()
	{
		if (NoType != null && !NoType.equals("") && SysConst.CHANGECHARSET == true)
		{
			NoType = StrTool.unicodeToGBK(NoType);
		}
		return NoType;
	}
	/** 号码类型 */
	public void setNoType(String aNoType)
	{
		NoType = aNoType;
	}
	/** 号码限制条件<P>号码限制条件 */
	public String getNoLimit()
	{
		if (NoLimit != null && !NoLimit.equals("") && SysConst.CHANGECHARSET == true)
		{
			NoLimit = StrTool.unicodeToGBK(NoLimit);
		}
		return NoLimit;
	}
	/** 号码限制条件 */
	public void setNoLimit(String aNoLimit)
	{
		NoLimit = aNoLimit;
	}
	/** 当前最大值<P>当前最大值 */
	public int getMaxNo()
	{
		return MaxNo;
	}
	/** 当前最大值 */
	public void setMaxNo(int aMaxNo)
	{
		MaxNo = aMaxNo;
	}
	/** 当前最大值<P>当前最大值 */
	public void setMaxNo(String aMaxNo)
	{
		if (aMaxNo != null && !aMaxNo.equals(""))
		{
			Integer tInteger = new Integer(aMaxNo);
			int i = tInteger.intValue();
			MaxNo = i;
		}
	}


	/**
	* 使用另外一个 LDMaxNoSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDMaxNoSchema aLDMaxNoSchema)
	{
		this.NoType = aLDMaxNoSchema.getNoType();
		this.NoLimit = aLDMaxNoSchema.getNoLimit();
		this.MaxNo = aLDMaxNoSchema.getMaxNo();
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
			if( rs.getString("NoType") == null )
				this.NoType = null;
			else
				this.NoType = rs.getString("NoType").trim();

			if( rs.getString("NoLimit") == null )
				this.NoLimit = null;
			else
				this.NoLimit = rs.getString("NoLimit").trim();

			this.MaxNo = rs.getInt("MaxNo");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDMaxNoSchema getSchema()
	{
		LDMaxNoSchema aLDMaxNoSchema = new LDMaxNoSchema();
		aLDMaxNoSchema.setSchema(this);
		return aLDMaxNoSchema;
	}

	public LDMaxNoDB getDB()
	{
		LDMaxNoDB aDBOper = new LDMaxNoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNo描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(NoType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NoLimit) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MaxNo);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNo>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NoLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MaxNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoSchema";
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
		if (FCode.equals("NoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoType));
		}
		if (FCode.equals("NoLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoLimit));
		}
		if (FCode.equals("MaxNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxNo));
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
				strFieldValue = StrTool.GBKToUnicode(NoType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NoLimit);
				break;
			case 2:
				strFieldValue = String.valueOf(MaxNo);
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

		if (FCode.equals("NoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoType = FValue.trim();
			}
			else
				NoType = null;
		}
		if (FCode.equals("NoLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoLimit = FValue.trim();
			}
			else
				NoLimit = null;
		}
		if (FCode.equals("MaxNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMaxNoSchema other = (LDMaxNoSchema)otherObject;
		return
			NoType.equals(other.getNoType())
			&& NoLimit.equals(other.getNoLimit())
			&& MaxNo == other.getMaxNo();
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
		if( strFieldName.equals("NoType") ) {
			return 0;
		}
		if( strFieldName.equals("NoLimit") ) {
			return 1;
		}
		if( strFieldName.equals("MaxNo") ) {
			return 2;
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
				strFieldName = "NoType";
				break;
			case 1:
				strFieldName = "NoLimit";
				break;
			case 2:
				strFieldName = "MaxNo";
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
		if( strFieldName.equals("NoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxNo") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
