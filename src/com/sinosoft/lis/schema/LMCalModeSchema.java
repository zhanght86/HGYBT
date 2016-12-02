/*
 * <p>ClassName: LMCalModeSchema </p>
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
import com.sinosoft.lis.db.LMCalModeDB;

public class LMCalModeSchema implements Schema
{
	// @Field
	/** 算法编码 */
	private String CalCode;
	/** 险种编码 */
	private String RiskCode;
	/** 算法类型 */
	private String Type;
	/** 算法内容 */
	private String CalSQL;
	/** 算法描述 */
	private String Remark;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCalModeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CalCode";

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
	/** 险种编码<P>险种编码 */
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
	/** 算法类型<P>V--承保时CheckField中的描述P--计算缴费A--计算责任给付(Amount)、(Premium)、U--个险核保规则W--佣金计算的编码现金价值计算<br><br><br><br> */
	public String getType()
	{
		if (Type != null && !Type.equals("") && SysConst.CHANGECHARSET == true)
		{
			Type = StrTool.unicodeToGBK(Type);
		}
		return Type;
	}
	/** 算法类型 */
	public void setType(String aType)
	{
		Type = aType;
	}
	/** 算法内容<P>算法内容 */
	public String getCalSQL()
	{
		if (CalSQL != null && !CalSQL.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalSQL = StrTool.unicodeToGBK(CalSQL);
		}
		return CalSQL;
	}
	/** 算法内容 */
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}
	/** 算法描述<P>算法描述 */
	public String getRemark()
	{
		if (Remark != null && !Remark.equals("") && SysConst.CHANGECHARSET == true)
		{
			Remark = StrTool.unicodeToGBK(Remark);
		}
		return Remark;
	}
	/** 算法描述 */
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LMCalModeSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMCalModeSchema aLMCalModeSchema)
	{
		this.CalCode = aLMCalModeSchema.getCalCode();
		this.RiskCode = aLMCalModeSchema.getRiskCode();
		this.Type = aLMCalModeSchema.getType();
		this.CalSQL = aLMCalModeSchema.getCalSQL();
		this.Remark = aLMCalModeSchema.getRemark();
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

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMCalModeSchema getSchema()
	{
		LMCalModeSchema aLMCalModeSchema = new LMCalModeSchema();
		aLMCalModeSchema.setSchema(this);
		return aLMCalModeSchema;
	}

	public LMCalModeDB getDB()
	{
		LMCalModeDB aDBOper = new LMCalModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalMode描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Type) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalSQL) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalMode>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalModeSchema";
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
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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
		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equals("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equals("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		if (FCode.equals("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCalModeSchema other = (LMCalModeSchema)otherObject;
		return
			CalCode.equals(other.getCalCode())
			&& RiskCode.equals(other.getRiskCode())
			&& Type.equals(other.getType())
			&& CalSQL.equals(other.getCalSQL())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("Type") ) {
			return 2;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 3;
		}
		if( strFieldName.equals("Remark") ) {
			return 4;
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
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "Type";
				break;
			case 3:
				strFieldName = "CalSQL";
				break;
			case 4:
				strFieldName = "Remark";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
