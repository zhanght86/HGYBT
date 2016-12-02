/*
 * <p>ClassName: LFPYCHGSSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-19
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LFPYCHGSDB;

public class LFPYCHGSSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 保单号 */
	private String RNO;
	/** 保单状态 */
	private String RSTU;
	/** 变更状态生效日 */
	private Date REFF;
	/** 维护日期 */
	private Date RMDAT;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFPYCHGSSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 流水号<P> */
	public String getSerialNo()
	{
		if (SerialNo != null && !SerialNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SerialNo = StrTool.unicodeToGBK(SerialNo);
		}
		return SerialNo;
	}
	/** 流水号 */
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/** 保单号<P> */
	public String getRNO()
	{
		if (RNO != null && !RNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			RNO = StrTool.unicodeToGBK(RNO);
		}
		return RNO;
	}
	/** 保单号 */
	public void setRNO(String aRNO)
	{
		RNO = aRNO;
	}
	/** 保单状态<P> */
	public String getRSTU()
	{
		if (RSTU != null && !RSTU.equals("") && SysConst.CHANGECHARSET == true)
		{
			RSTU = StrTool.unicodeToGBK(RSTU);
		}
		return RSTU;
	}
	/** 保单状态 */
	public void setRSTU(String aRSTU)
	{
		RSTU = aRSTU;
	}
	/** 变更状态生效日<P> */
	public String getREFF()
	{
		if( REFF != null )
			return fDate.getString(REFF);
		else
			return null;
	}
	/** 变更状态生效日 */
	public void setREFF(Date aREFF)
	{
		REFF = aREFF;
	}
	/** 变更状态生效日<P> */
	public void setREFF(String aREFF)
	{
		if (aREFF != null && !aREFF.equals("") )
		{
			REFF = fDate.getDate( aREFF );
		}
		else
			REFF = null;
	}

	/** 维护日期<P> */
	public String getRMDAT()
	{
		if( RMDAT != null )
			return fDate.getString(RMDAT);
		else
			return null;
	}
	/** 维护日期 */
	public void setRMDAT(Date aRMDAT)
	{
		RMDAT = aRMDAT;
	}
	/** 维护日期<P> */
	public void setRMDAT(String aRMDAT)
	{
		if (aRMDAT != null && !aRMDAT.equals("") )
		{
			RMDAT = fDate.getDate( aRMDAT );
		}
		else
			RMDAT = null;
	}


	/**
	* 使用另外一个 LFPYCHGSSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LFPYCHGSSchema aLFPYCHGSSchema)
	{
		this.SerialNo = aLFPYCHGSSchema.getSerialNo();
		this.RNO = aLFPYCHGSSchema.getRNO();
		this.RSTU = aLFPYCHGSSchema.getRSTU();
		this.REFF = fDate.getDate( aLFPYCHGSSchema.getREFF());
		this.RMDAT = fDate.getDate( aLFPYCHGSSchema.getRMDAT());
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RNO") == null )
				this.RNO = null;
			else
				this.RNO = rs.getString("RNO").trim();

			if( rs.getString("RSTU") == null )
				this.RSTU = null;
			else
				this.RSTU = rs.getString("RSTU").trim();

			this.REFF = rs.getDate("REFF");
			this.RMDAT = rs.getDate("RMDAT");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFPYCHGSSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LFPYCHGSSchema getSchema()
	{
		LFPYCHGSSchema aLFPYCHGSSchema = new LFPYCHGSSchema();
		aLFPYCHGSSchema.setSchema(this);
		return aLFPYCHGSSchema;
	}

	public LFPYCHGSDB getDB()
	{
		LFPYCHGSDB aDBOper = new LFPYCHGSDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFPYCHGS描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RSTU) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( REFF )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( RMDAT )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFPYCHGS>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RSTU = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			REFF = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			RMDAT = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFPYCHGSSchema";
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
		if (FCode.equals("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equals("RNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RNO));
		}
		if (FCode.equals("RSTU"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RSTU));
		}
		if (FCode.equals("REFF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getREFF()));
		}
		if (FCode.equals("RMDAT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRMDAT()));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RNO);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RSTU);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getREFF()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRMDAT()));
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

		if (FCode.equals("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equals("RNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RNO = FValue.trim();
			}
			else
				RNO = null;
		}
		if (FCode.equals("RSTU"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RSTU = FValue.trim();
			}
			else
				RSTU = null;
		}
		if (FCode.equals("REFF"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				REFF = fDate.getDate( FValue );
			}
			else
				REFF = null;
		}
		if (FCode.equals("RMDAT"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RMDAT = fDate.getDate( FValue );
			}
			else
				RMDAT = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFPYCHGSSchema other = (LFPYCHGSSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& RNO.equals(other.getRNO())
			&& RSTU.equals(other.getRSTU())
			&& fDate.getString(REFF).equals(other.getREFF())
			&& fDate.getString(RMDAT).equals(other.getRMDAT());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("RNO") ) {
			return 1;
		}
		if( strFieldName.equals("RSTU") ) {
			return 2;
		}
		if( strFieldName.equals("REFF") ) {
			return 3;
		}
		if( strFieldName.equals("RMDAT") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "RNO";
				break;
			case 2:
				strFieldName = "RSTU";
				break;
			case 3:
				strFieldName = "REFF";
				break;
			case 4:
				strFieldName = "RMDAT";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RSTU") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("REFF") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RMDAT") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
