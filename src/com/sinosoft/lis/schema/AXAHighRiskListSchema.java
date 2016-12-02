/*
 * <p>ClassName: AXAHighRiskListSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-01-05
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.AXAHighRiskListDB;

public class AXAHighRiskListSchema implements Schema
{
	// @Field
	/** 被保险人姓名 */
	private String AName;
	/** 被保险人生日 */
	private Date ADob;
	/** 被保险人身份证id */
	private String AID;
	/** 被保险人性别 */
	private String ASex;
	/** 总保额 */
	private double ASA;
	/** 检查保额标志 */
	private String AFlag;
	/** 维护日期 */
	private Date AMDAT;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AXAHighRiskListSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AName";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 被保险人姓名<P> */
	public String getAName()
	{
		if (AName != null && !AName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AName = StrTool.unicodeToGBK(AName);
		}
		return AName;
	}
	/** 被保险人姓名 */
	public void setAName(String aAName)
	{
		AName = aAName;
	}
	/** 被保险人生日<P> */
	public String getADob()
	{
		if( ADob != null )
			return fDate.getString(ADob);
		else
			return null;
	}
	/** 被保险人生日 */
	public void setADob(Date aADob)
	{
		ADob = aADob;
	}
	/** 被保险人生日<P> */
	public void setADob(String aADob)
	{
		if (aADob != null && !aADob.equals("") )
		{
			ADob = fDate.getDate( aADob );
		}
		else
			ADob = null;
	}

	/** 被保险人身份证id<P> */
	public String getAID()
	{
		if (AID != null && !AID.equals("") && SysConst.CHANGECHARSET == true)
		{
			AID = StrTool.unicodeToGBK(AID);
		}
		return AID;
	}
	/** 被保险人身份证id */
	public void setAID(String aAID)
	{
		AID = aAID;
	}
	/** 被保险人性别<P> */
	public String getASex()
	{
		if (ASex != null && !ASex.equals("") && SysConst.CHANGECHARSET == true)
		{
			ASex = StrTool.unicodeToGBK(ASex);
		}
		return ASex;
	}
	/** 被保险人性别 */
	public void setASex(String aASex)
	{
		ASex = aASex;
	}
	/** 总保额<P> */
	public double getASA()
	{
		return ASA;
	}
	/** 总保额 */
	public void setASA(double aASA)
	{
		ASA = aASA;
	}
	/** 总保额<P> */
	public void setASA(String aASA)
	{
		if (aASA != null && !aASA.equals(""))
		{
			Double tDouble = new Double(aASA);
			double d = tDouble.doubleValue();
			ASA = d;
		}
	}

	/** 检查保额标志<P>保额选项<br>‘Y’- 需要检查总保额<br>‘ ’ – 不需要检查总保额<br> */
	public String getAFlag()
	{
		if (AFlag != null && !AFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AFlag = StrTool.unicodeToGBK(AFlag);
		}
		return AFlag;
	}
	/** 检查保额标志 */
	public void setAFlag(String aAFlag)
	{
		AFlag = aAFlag;
	}
	/** 维护日期<P> */
	public String getAMDAT()
	{
		if( AMDAT != null )
			return fDate.getString(AMDAT);
		else
			return null;
	}
	/** 维护日期 */
	public void setAMDAT(Date aAMDAT)
	{
		AMDAT = aAMDAT;
	}
	/** 维护日期<P> */
	public void setAMDAT(String aAMDAT)
	{
		if (aAMDAT != null && !aAMDAT.equals("") )
		{
			AMDAT = fDate.getDate( aAMDAT );
		}
		else
			AMDAT = null;
	}


	/**
	* 使用另外一个 AXAHighRiskListSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AXAHighRiskListSchema aAXAHighRiskListSchema)
	{
		this.AName = aAXAHighRiskListSchema.getAName();
		this.ADob = fDate.getDate( aAXAHighRiskListSchema.getADob());
		this.AID = aAXAHighRiskListSchema.getAID();
		this.ASex = aAXAHighRiskListSchema.getASex();
		this.ASA = aAXAHighRiskListSchema.getASA();
		this.AFlag = aAXAHighRiskListSchema.getAFlag();
		this.AMDAT = fDate.getDate( aAXAHighRiskListSchema.getAMDAT());
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
			if( rs.getString("AName") == null )
				this.AName = null;
			else
				this.AName = rs.getString("AName").trim();

			this.ADob = rs.getDate("ADob");
			if( rs.getString("AID") == null )
				this.AID = null;
			else
				this.AID = rs.getString("AID").trim();

			if( rs.getString("ASex") == null )
				this.ASex = null;
			else
				this.ASex = rs.getString("ASex").trim();

			this.ASA = rs.getDouble("ASA");
			if( rs.getString("AFlag") == null )
				this.AFlag = null;
			else
				this.AFlag = rs.getString("AFlag").trim();

			this.AMDAT = rs.getDate("AMDAT");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAHighRiskListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AXAHighRiskListSchema getSchema()
	{
		AXAHighRiskListSchema aAXAHighRiskListSchema = new AXAHighRiskListSchema();
		aAXAHighRiskListSchema.setSchema(this);
		return aAXAHighRiskListSchema;
	}

	public AXAHighRiskListDB getDB()
	{
		AXAHighRiskListDB aDBOper = new AXAHighRiskListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAHighRiskList描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(AName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ADob )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ASex) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ASA) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AMDAT )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAHighRiskList>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ADob = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2,SysConst.PACKAGESPILTER));
			AID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ASex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ASA = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			AFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AMDAT = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAHighRiskListSchema";
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
		if (FCode.equals("AName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AName));
		}
		if (FCode.equals("ADob"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getADob()));
		}
		if (FCode.equals("AID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AID));
		}
		if (FCode.equals("ASex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ASex));
		}
		if (FCode.equals("ASA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ASA));
		}
		if (FCode.equals("AFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AFlag));
		}
		if (FCode.equals("AMDAT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAMDAT()));
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
				strFieldValue = StrTool.GBKToUnicode(AName);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getADob()));
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ASex);
				break;
			case 4:
				strFieldValue = String.valueOf(ASA);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAMDAT()));
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

		if (FCode.equals("AName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AName = FValue.trim();
			}
			else
				AName = null;
		}
		if (FCode.equals("ADob"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ADob = fDate.getDate( FValue );
			}
			else
				ADob = null;
		}
		if (FCode.equals("AID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AID = FValue.trim();
			}
			else
				AID = null;
		}
		if (FCode.equals("ASex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ASex = FValue.trim();
			}
			else
				ASex = null;
		}
		if (FCode.equals("ASA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ASA = d;
			}
		}
		if (FCode.equals("AFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AFlag = FValue.trim();
			}
			else
				AFlag = null;
		}
		if (FCode.equals("AMDAT"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AMDAT = fDate.getDate( FValue );
			}
			else
				AMDAT = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AXAHighRiskListSchema other = (AXAHighRiskListSchema)otherObject;
		return
			AName.equals(other.getAName())
			&& fDate.getString(ADob).equals(other.getADob())
			&& AID.equals(other.getAID())
			&& ASex.equals(other.getASex())
			&& ASA == other.getASA()
			&& AFlag.equals(other.getAFlag())
			&& fDate.getString(AMDAT).equals(other.getAMDAT());
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
		if( strFieldName.equals("AName") ) {
			return 0;
		}
		if( strFieldName.equals("ADob") ) {
			return 1;
		}
		if( strFieldName.equals("AID") ) {
			return 2;
		}
		if( strFieldName.equals("ASex") ) {
			return 3;
		}
		if( strFieldName.equals("ASA") ) {
			return 4;
		}
		if( strFieldName.equals("AFlag") ) {
			return 5;
		}
		if( strFieldName.equals("AMDAT") ) {
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
				strFieldName = "AName";
				break;
			case 1:
				strFieldName = "ADob";
				break;
			case 2:
				strFieldName = "AID";
				break;
			case 3:
				strFieldName = "ASex";
				break;
			case 4:
				strFieldName = "ASA";
				break;
			case 5:
				strFieldName = "AFlag";
				break;
			case 6:
				strFieldName = "AMDAT";
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
		if( strFieldName.equals("AName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ADob") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ASex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ASA") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AMDAT") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
