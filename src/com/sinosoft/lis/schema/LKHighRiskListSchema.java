/*
 * <p>ClassName: LKHighRiskListSchema </p>
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
import com.sinosoft.lis.db.LKHighRiskListDB;

public class LKHighRiskListSchema implements Schema
{
	// @Field
	/** 被保人姓名 */
	private String AName;
	/** 被保人出生日期 */
	private Date ADob;
	/** 被保人性别 */
	private String ASex;
	/** 被保人身份证id */
	private String AID;
	/** 总保额 */
	private int ASA;
	/** 保额选项 */
	private String Aflag;
	/** 维护日期 */
	private Date AMDat;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKHighRiskListSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "AName";
		pk[1] = "ADob";
		pk[2] = "ASex";
		pk[3] = "AID";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 被保人姓名<P>中文姓名 */
	public String getAName()
	{
		if (AName != null && !AName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AName = StrTool.unicodeToGBK(AName);
		}
		return AName;
	}
	/** 被保人姓名 */
	public void setAName(String aAName)
	{
		AName = aAName;
	}
	/** 被保人出生日期<P>出生日期 */
	public String getADob()
	{
		if( ADob != null )
			return fDate.getString(ADob);
		else
			return null;
	}
	/** 被保人出生日期 */
	public void setADob(Date aADob)
	{
		ADob = aADob;
	}
	/** 被保人出生日期<P>出生日期 */
	public void setADob(String aADob)
	{
		if (aADob != null && !aADob.equals("") )
		{
			ADob = fDate.getDate( aADob );
		}
		else
			ADob = null;
	}

	/** 被保人性别<P>保单合同号 */
	public String getASex()
	{
		if (ASex != null && !ASex.equals("") && SysConst.CHANGECHARSET == true)
		{
			ASex = StrTool.unicodeToGBK(ASex);
		}
		return ASex;
	}
	/** 被保人性别 */
	public void setASex(String aASex)
	{
		ASex = aASex;
	}
	/** 被保人身份证id<P>证件号码 */
	public String getAID()
	{
		if (AID != null && !AID.equals("") && SysConst.CHANGECHARSET == true)
		{
			AID = StrTool.unicodeToGBK(AID);
		}
		return AID;
	}
	/** 被保人身份证id */
	public void setAID(String aAID)
	{
		AID = aAID;
	}
	/** 总保额<P>产品代码 */
	public int getASA()
	{
		return ASA;
	}
	/** 总保额 */
	public void setASA(int aASA)
	{
		ASA = aASA;
	}
	/** 总保额<P>产品代码 */
	public void setASA(String aASA)
	{
		if (aASA != null && !aASA.equals(""))
		{
			Integer tInteger = new Integer(aASA);
			int i = tInteger.intValue();
			ASA = i;
		}
	}

	/** 保额选项<P> */
	public String getAflag()
	{
		if (Aflag != null && !Aflag.equals("") && SysConst.CHANGECHARSET == true)
		{
			Aflag = StrTool.unicodeToGBK(Aflag);
		}
		return Aflag;
	}
	/** 保额选项 */
	public void setAflag(String aAflag)
	{
		Aflag = aAflag;
	}
	/** 维护日期<P>疾病代码 */
	public String getAMDat()
	{
		if( AMDat != null )
			return fDate.getString(AMDat);
		else
			return null;
	}
	/** 维护日期 */
	public void setAMDat(Date aAMDat)
	{
		AMDat = aAMDat;
	}
	/** 维护日期<P>疾病代码 */
	public void setAMDat(String aAMDat)
	{
		if (aAMDat != null && !aAMDat.equals("") )
		{
			AMDat = fDate.getDate( aAMDat );
		}
		else
			AMDat = null;
	}


	/**
	* 使用另外一个 LKHighRiskListSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKHighRiskListSchema aLKHighRiskListSchema)
	{
		this.AName = aLKHighRiskListSchema.getAName();
		this.ADob = fDate.getDate( aLKHighRiskListSchema.getADob());
		this.ASex = aLKHighRiskListSchema.getASex();
		this.AID = aLKHighRiskListSchema.getAID();
		this.ASA = aLKHighRiskListSchema.getASA();
		this.Aflag = aLKHighRiskListSchema.getAflag();
		this.AMDat = fDate.getDate( aLKHighRiskListSchema.getAMDat());
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
			if( rs.getString("ASex") == null )
				this.ASex = null;
			else
				this.ASex = rs.getString("ASex").trim();

			if( rs.getString("AID") == null )
				this.AID = null;
			else
				this.AID = rs.getString("AID").trim();

			this.ASA = rs.getInt("ASA");
			if( rs.getString("Aflag") == null )
				this.Aflag = null;
			else
				this.Aflag = rs.getString("Aflag").trim();

			this.AMDat = rs.getDate("AMDat");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKHighRiskListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKHighRiskListSchema getSchema()
	{
		LKHighRiskListSchema aLKHighRiskListSchema = new LKHighRiskListSchema();
		aLKHighRiskListSchema.setSchema(this);
		return aLKHighRiskListSchema;
	}

	public LKHighRiskListDB getDB()
	{
		LKHighRiskListDB aDBOper = new LKHighRiskListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKHighRiskList描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(AName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ADob )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ASex) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AID) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ASA) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Aflag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( AMDat )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKHighRiskList>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ADob = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2,SysConst.PACKAGESPILTER));
			ASex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ASA= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			Aflag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AMDat = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKHighRiskListSchema";
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
		if (FCode.equals("ASex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ASex));
		}
		if (FCode.equals("AID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AID));
		}
		if (FCode.equals("ASA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ASA));
		}
		if (FCode.equals("Aflag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Aflag));
		}
		if (FCode.equals("AMDat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAMDat()));
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
				strFieldValue = StrTool.GBKToUnicode(ASex);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AID);
				break;
			case 4:
				strFieldValue = String.valueOf(ASA);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Aflag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAMDat()));
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
		if (FCode.equals("ASex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ASex = FValue.trim();
			}
			else
				ASex = null;
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
		if (FCode.equals("ASA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ASA = i;
			}
		}
		if (FCode.equals("Aflag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Aflag = FValue.trim();
			}
			else
				Aflag = null;
		}
		if (FCode.equals("AMDat"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AMDat = fDate.getDate( FValue );
			}
			else
				AMDat = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKHighRiskListSchema other = (LKHighRiskListSchema)otherObject;
		return
			AName.equals(other.getAName())
			&& fDate.getString(ADob).equals(other.getADob())
			&& ASex.equals(other.getASex())
			&& AID.equals(other.getAID())
			&& ASA == other.getASA()
			&& Aflag.equals(other.getAflag())
			&& fDate.getString(AMDat).equals(other.getAMDat());
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
		if( strFieldName.equals("ASex") ) {
			return 2;
		}
		if( strFieldName.equals("AID") ) {
			return 3;
		}
		if( strFieldName.equals("ASA") ) {
			return 4;
		}
		if( strFieldName.equals("Aflag") ) {
			return 5;
		}
		if( strFieldName.equals("AMDat") ) {
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
				strFieldName = "ASex";
				break;
			case 3:
				strFieldName = "AID";
				break;
			case 4:
				strFieldName = "ASA";
				break;
			case 5:
				strFieldName = "Aflag";
				break;
			case 6:
				strFieldName = "AMDat";
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
		if( strFieldName.equals("ASex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ASA") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Aflag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AMDat") ) {
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
				nFieldType = Schema.TYPE_INT;
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
