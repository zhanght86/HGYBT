/*
 * <p>ClassName: YBTHighRiskListSchema </p>
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
import com.sinosoft.lis.db.YBTHighRiskListDB;

public class YBTHighRiskListSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 被保险人姓名 */
	private String AName;
	/** 被保险人生日 */
	private Date ADob;
	/** 被保险人身份证id */
	private String AID;
	/** 被保险人性别 */
	private String ASex;
	/** 总保额 */
	private String ASA;
	/** 检查保额标志 */
	private String AFlag;
	/** 维护日期 */
	private Date AMDAT;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public YBTHighRiskListSchema()
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
	public String getASA()
	{
		if (ASA != null && !ASA.equals("") && SysConst.CHANGECHARSET == true)
		{
			ASA = StrTool.unicodeToGBK(ASA);
		}
		return ASA;
	}
	/** 总保额 */
	public void setASA(String aASA)
	{
		ASA = aASA;
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

	/** 创建日期<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 创建日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 创建日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 创建时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 创建时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 YBTHighRiskListSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(YBTHighRiskListSchema aYBTHighRiskListSchema)
	{
		this.SerialNo = aYBTHighRiskListSchema.getSerialNo();
		this.AName = aYBTHighRiskListSchema.getAName();
		this.ADob = fDate.getDate( aYBTHighRiskListSchema.getADob());
		this.AID = aYBTHighRiskListSchema.getAID();
		this.ASex = aYBTHighRiskListSchema.getASex();
		this.ASA = aYBTHighRiskListSchema.getASA();
		this.AFlag = aYBTHighRiskListSchema.getAFlag();
		this.AMDAT = fDate.getDate( aYBTHighRiskListSchema.getAMDAT());
		this.MakeDate = fDate.getDate( aYBTHighRiskListSchema.getMakeDate());
		this.MakeTime = aYBTHighRiskListSchema.getMakeTime();
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

			if( rs.getString("ASA") == null )
				this.ASA = null;
			else
				this.ASA = rs.getString("ASA").trim();

			if( rs.getString("AFlag") == null )
				this.AFlag = null;
			else
				this.AFlag = rs.getString("AFlag").trim();

			this.AMDAT = rs.getDate("AMDAT");
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
			tError.moduleName = "YBTHighRiskListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public YBTHighRiskListSchema getSchema()
	{
		YBTHighRiskListSchema aYBTHighRiskListSchema = new YBTHighRiskListSchema();
		aYBTHighRiskListSchema.setSchema(this);
		return aYBTHighRiskListSchema;
	}

	public YBTHighRiskListDB getDB()
	{
		YBTHighRiskListDB aDBOper = new YBTHighRiskListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTHighRiskList描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ADob )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ASex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ASA) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AMDAT )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTHighRiskList>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ADob = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			AID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ASex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ASA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AMDAT = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTHighRiskListSchema";
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getADob()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ASex);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ASA);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAMDAT()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
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

		if (FCode.equals("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
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
				ASA = FValue.trim();
			}
			else
				ASA = null;
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
		YBTHighRiskListSchema other = (YBTHighRiskListSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& AName.equals(other.getAName())
			&& fDate.getString(ADob).equals(other.getADob())
			&& AID.equals(other.getAID())
			&& ASex.equals(other.getASex())
			&& ASA.equals(other.getASA())
			&& AFlag.equals(other.getAFlag())
			&& fDate.getString(AMDAT).equals(other.getAMDAT())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("AName") ) {
			return 1;
		}
		if( strFieldName.equals("ADob") ) {
			return 2;
		}
		if( strFieldName.equals("AID") ) {
			return 3;
		}
		if( strFieldName.equals("ASex") ) {
			return 4;
		}
		if( strFieldName.equals("ASA") ) {
			return 5;
		}
		if( strFieldName.equals("AFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AMDAT") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
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
				strFieldName = "AName";
				break;
			case 2:
				strFieldName = "ADob";
				break;
			case 3:
				strFieldName = "AID";
				break;
			case 4:
				strFieldName = "ASex";
				break;
			case 5:
				strFieldName = "ASA";
				break;
			case 6:
				strFieldName = "AFlag";
				break;
			case 7:
				strFieldName = "AMDAT";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AMDAT") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
