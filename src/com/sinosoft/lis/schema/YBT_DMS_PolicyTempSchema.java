/*
 * <p>ClassName: YBT_DMS_PolicyTempSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-01
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.YBT_DMS_PolicyTempDB;

public class YBT_DMS_PolicyTempSchema implements Schema
{
	// @Field
	/** 主键 */
	private String Oid;
	/** 保单首页号 */
	private String PolicySerialNumber;
	/** 投保单号 */
	private String ApplicationNumber;
	/** 保单号 */
	private String PolicyNumber;
	/** 网点编码 */
	private String ChannelOrganizationNumber;
	/** Fa编码 */
	private String CarrierOfficer;
	/** 数据日期 */
	private Date SignedDate;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public YBT_DMS_PolicyTempSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Oid";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 主键<P>主键 */
	public String getOid()
	{
		if (Oid != null && !Oid.equals("") && SysConst.CHANGECHARSET == true)
		{
			Oid = StrTool.unicodeToGBK(Oid);
		}
		return Oid;
	}
	/** 主键 */
	public void setOid(String aOid)
	{
		Oid = aOid;
	}
	/** 保单首页号<P> */
	public String getPolicySerialNumber()
	{
		if (PolicySerialNumber != null && !PolicySerialNumber.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicySerialNumber = StrTool.unicodeToGBK(PolicySerialNumber);
		}
		return PolicySerialNumber;
	}
	/** 保单首页号 */
	public void setPolicySerialNumber(String aPolicySerialNumber)
	{
		PolicySerialNumber = aPolicySerialNumber;
	}
	/** 投保单号<P> */
	public String getApplicationNumber()
	{
		if (ApplicationNumber != null && !ApplicationNumber.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplicationNumber = StrTool.unicodeToGBK(ApplicationNumber);
		}
		return ApplicationNumber;
	}
	/** 投保单号 */
	public void setApplicationNumber(String aApplicationNumber)
	{
		ApplicationNumber = aApplicationNumber;
	}
	/** 保单号<P> */
	public String getPolicyNumber()
	{
		if (PolicyNumber != null && !PolicyNumber.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyNumber = StrTool.unicodeToGBK(PolicyNumber);
		}
		return PolicyNumber;
	}
	/** 保单号 */
	public void setPolicyNumber(String aPolicyNumber)
	{
		PolicyNumber = aPolicyNumber;
	}
	/** 网点编码<P> */
	public String getChannelOrganizationNumber()
	{
		if (ChannelOrganizationNumber != null && !ChannelOrganizationNumber.equals("") && SysConst.CHANGECHARSET == true)
		{
			ChannelOrganizationNumber = StrTool.unicodeToGBK(ChannelOrganizationNumber);
		}
		return ChannelOrganizationNumber;
	}
	/** 网点编码 */
	public void setChannelOrganizationNumber(String aChannelOrganizationNumber)
	{
		ChannelOrganizationNumber = aChannelOrganizationNumber;
	}
	/** Fa编码<P> */
	public String getCarrierOfficer()
	{
		if (CarrierOfficer != null && !CarrierOfficer.equals("") && SysConst.CHANGECHARSET == true)
		{
			CarrierOfficer = StrTool.unicodeToGBK(CarrierOfficer);
		}
		return CarrierOfficer;
	}
	/** Fa编码 */
	public void setCarrierOfficer(String aCarrierOfficer)
	{
		CarrierOfficer = aCarrierOfficer;
	}
	/** 数据日期<P> */
	public String getSignedDate()
	{
		if( SignedDate != null )
			return fDate.getString(SignedDate);
		else
			return null;
	}
	/** 数据日期 */
	public void setSignedDate(Date aSignedDate)
	{
		SignedDate = aSignedDate;
	}
	/** 数据日期<P> */
	public void setSignedDate(String aSignedDate)
	{
		if (aSignedDate != null && !aSignedDate.equals("") )
		{
			SignedDate = fDate.getDate( aSignedDate );
		}
		else
			SignedDate = null;
	}


	/**
	* 使用另外一个 YBT_DMS_PolicyTempSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(YBT_DMS_PolicyTempSchema aYBT_DMS_PolicyTempSchema)
	{
		this.Oid = aYBT_DMS_PolicyTempSchema.getOid();
		this.PolicySerialNumber = aYBT_DMS_PolicyTempSchema.getPolicySerialNumber();
		this.ApplicationNumber = aYBT_DMS_PolicyTempSchema.getApplicationNumber();
		this.PolicyNumber = aYBT_DMS_PolicyTempSchema.getPolicyNumber();
		this.ChannelOrganizationNumber = aYBT_DMS_PolicyTempSchema.getChannelOrganizationNumber();
		this.CarrierOfficer = aYBT_DMS_PolicyTempSchema.getCarrierOfficer();
		this.SignedDate = fDate.getDate( aYBT_DMS_PolicyTempSchema.getSignedDate());
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
			if( rs.getString("Oid") == null )
				this.Oid = null;
			else
				this.Oid = rs.getString("Oid").trim();

			if( rs.getString("PolicySerialNumber") == null )
				this.PolicySerialNumber = null;
			else
				this.PolicySerialNumber = rs.getString("PolicySerialNumber").trim();

			if( rs.getString("ApplicationNumber") == null )
				this.ApplicationNumber = null;
			else
				this.ApplicationNumber = rs.getString("ApplicationNumber").trim();

			if( rs.getString("PolicyNumber") == null )
				this.PolicyNumber = null;
			else
				this.PolicyNumber = rs.getString("PolicyNumber").trim();

			if( rs.getString("ChannelOrganizationNumber") == null )
				this.ChannelOrganizationNumber = null;
			else
				this.ChannelOrganizationNumber = rs.getString("ChannelOrganizationNumber").trim();

			if( rs.getString("CarrierOfficer") == null )
				this.CarrierOfficer = null;
			else
				this.CarrierOfficer = rs.getString("CarrierOfficer").trim();

			this.SignedDate = rs.getDate("SignedDate");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBT_DMS_PolicyTempSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public YBT_DMS_PolicyTempSchema getSchema()
	{
		YBT_DMS_PolicyTempSchema aYBT_DMS_PolicyTempSchema = new YBT_DMS_PolicyTempSchema();
		aYBT_DMS_PolicyTempSchema.setSchema(this);
		return aYBT_DMS_PolicyTempSchema;
	}

	public YBT_DMS_PolicyTempDB getDB()
	{
		YBT_DMS_PolicyTempDB aDBOper = new YBT_DMS_PolicyTempDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBT_DMS_PolicyTemp描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(Oid) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicySerialNumber) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplicationNumber) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNumber) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ChannelOrganizationNumber) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CarrierOfficer) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( SignedDate )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBT_DMS_PolicyTemp>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Oid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolicySerialNumber = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ApplicationNumber = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolicyNumber = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChannelOrganizationNumber = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CarrierOfficer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SignedDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBT_DMS_PolicyTempSchema";
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
		if (FCode.equals("Oid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Oid));
		}
		if (FCode.equals("PolicySerialNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicySerialNumber));
		}
		if (FCode.equals("ApplicationNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicationNumber));
		}
		if (FCode.equals("PolicyNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNumber));
		}
		if (FCode.equals("ChannelOrganizationNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChannelOrganizationNumber));
		}
		if (FCode.equals("CarrierOfficer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CarrierOfficer));
		}
		if (FCode.equals("SignedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignedDate()));
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
				strFieldValue = StrTool.GBKToUnicode(Oid);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolicySerialNumber);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ApplicationNumber);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolicyNumber);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChannelOrganizationNumber);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CarrierOfficer);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignedDate()));
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

		if (FCode.equals("Oid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Oid = FValue.trim();
			}
			else
				Oid = null;
		}
		if (FCode.equals("PolicySerialNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicySerialNumber = FValue.trim();
			}
			else
				PolicySerialNumber = null;
		}
		if (FCode.equals("ApplicationNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplicationNumber = FValue.trim();
			}
			else
				ApplicationNumber = null;
		}
		if (FCode.equals("PolicyNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNumber = FValue.trim();
			}
			else
				PolicyNumber = null;
		}
		if (FCode.equals("ChannelOrganizationNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChannelOrganizationNumber = FValue.trim();
			}
			else
				ChannelOrganizationNumber = null;
		}
		if (FCode.equals("CarrierOfficer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CarrierOfficer = FValue.trim();
			}
			else
				CarrierOfficer = null;
		}
		if (FCode.equals("SignedDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignedDate = fDate.getDate( FValue );
			}
			else
				SignedDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		YBT_DMS_PolicyTempSchema other = (YBT_DMS_PolicyTempSchema)otherObject;
		return
			Oid.equals(other.getOid())
			&& PolicySerialNumber.equals(other.getPolicySerialNumber())
			&& ApplicationNumber.equals(other.getApplicationNumber())
			&& PolicyNumber.equals(other.getPolicyNumber())
			&& ChannelOrganizationNumber.equals(other.getChannelOrganizationNumber())
			&& CarrierOfficer.equals(other.getCarrierOfficer())
			&& fDate.getString(SignedDate).equals(other.getSignedDate());
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
		if( strFieldName.equals("Oid") ) {
			return 0;
		}
		if( strFieldName.equals("PolicySerialNumber") ) {
			return 1;
		}
		if( strFieldName.equals("ApplicationNumber") ) {
			return 2;
		}
		if( strFieldName.equals("PolicyNumber") ) {
			return 3;
		}
		if( strFieldName.equals("ChannelOrganizationNumber") ) {
			return 4;
		}
		if( strFieldName.equals("CarrierOfficer") ) {
			return 5;
		}
		if( strFieldName.equals("SignedDate") ) {
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
				strFieldName = "Oid";
				break;
			case 1:
				strFieldName = "PolicySerialNumber";
				break;
			case 2:
				strFieldName = "ApplicationNumber";
				break;
			case 3:
				strFieldName = "PolicyNumber";
				break;
			case 4:
				strFieldName = "ChannelOrganizationNumber";
				break;
			case 5:
				strFieldName = "CarrierOfficer";
				break;
			case 6:
				strFieldName = "SignedDate";
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
		if( strFieldName.equals("Oid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicySerialNumber") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicationNumber") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyNumber") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChannelOrganizationNumber") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CarrierOfficer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignedDate") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
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
