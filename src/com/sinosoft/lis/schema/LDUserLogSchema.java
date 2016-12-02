/*
 * <p>ClassName: LDUserLogSchema </p>
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
import com.sinosoft.lis.db.LDUserLogDB;

public class LDUserLogSchema implements Schema
{
	// @Field
	/** 日志号 */
	private String LogNo;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;
	/** 日志类型 */
	private String LogType;
	/** 日志内容 */
	private String LogContent;
	/** 客户端ip */
	private String CientIP;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUserLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 日志号<P>日志号 */
	public String getLogNo()
	{
		if (LogNo != null && !LogNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			LogNo = StrTool.unicodeToGBK(LogNo);
		}
		return LogNo;
	}
	/** 日志号 */
	public void setLogNo(String aLogNo)
	{
		LogNo = aLogNo;
	}
	/** 管理机构<P>管理机构 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 管理机构 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	/** 日志类型<P>日志类型 */
	public String getLogType()
	{
		if (LogType != null && !LogType.equals("") && SysConst.CHANGECHARSET == true)
		{
			LogType = StrTool.unicodeToGBK(LogType);
		}
		return LogType;
	}
	/** 日志类型 */
	public void setLogType(String aLogType)
	{
		LogType = aLogType;
	}
	/** 日志内容<P>日志内容 */
	public String getLogContent()
	{
		if (LogContent != null && !LogContent.equals("") && SysConst.CHANGECHARSET == true)
		{
			LogContent = StrTool.unicodeToGBK(LogContent);
		}
		return LogContent;
	}
	/** 日志内容 */
	public void setLogContent(String aLogContent)
	{
		LogContent = aLogContent;
	}
	/** 客户端ip<P>客户端ip */
	public String getCientIP()
	{
		if (CientIP != null && !CientIP.equals("") && SysConst.CHANGECHARSET == true)
		{
			CientIP = StrTool.unicodeToGBK(CientIP);
		}
		return CientIP;
	}
	/** 客户端ip */
	public void setCientIP(String aCientIP)
	{
		CientIP = aCientIP;
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
	* 使用另外一个 LDUserLogSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDUserLogSchema aLDUserLogSchema)
	{
		this.LogNo = aLDUserLogSchema.getLogNo();
		this.ManageCom = aLDUserLogSchema.getManageCom();
		this.Operator = aLDUserLogSchema.getOperator();
		this.LogType = aLDUserLogSchema.getLogType();
		this.LogContent = aLDUserLogSchema.getLogContent();
		this.CientIP = aLDUserLogSchema.getCientIP();
		this.MakeDate = fDate.getDate( aLDUserLogSchema.getMakeDate());
		this.MakeTime = aLDUserLogSchema.getMakeTime();
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
			if( rs.getString("LogNo") == null )
				this.LogNo = null;
			else
				this.LogNo = rs.getString("LogNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("LogType") == null )
				this.LogType = null;
			else
				this.LogType = rs.getString("LogType").trim();

			if( rs.getString("LogContent") == null )
				this.LogContent = null;
			else
				this.LogContent = rs.getString("LogContent").trim();

			if( rs.getString("CientIP") == null )
				this.CientIP = null;
			else
				this.CientIP = rs.getString("CientIP").trim();

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
			tError.moduleName = "LDUserLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDUserLogSchema getSchema()
	{
		LDUserLogSchema aLDUserLogSchema = new LDUserLogSchema();
		aLDUserLogSchema.setSchema(this);
		return aLDUserLogSchema;
	}

	public LDUserLogDB getDB()
	{
		LDUserLogDB aDBOper = new LDUserLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUserLog描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(LogNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LogType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LogContent) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CientIP) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUserLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LogType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LogContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CientIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserLogSchema";
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
		if (FCode.equals("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("LogType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogType));
		}
		if (FCode.equals("LogContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogContent));
		}
		if (FCode.equals("CientIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CientIP));
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
				strFieldValue = StrTool.GBKToUnicode(LogNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LogType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LogContent);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CientIP);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
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

		if (FCode.equals("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogNo = FValue.trim();
			}
			else
				LogNo = null;
		}
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equals("LogType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogType = FValue.trim();
			}
			else
				LogType = null;
		}
		if (FCode.equals("LogContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogContent = FValue.trim();
			}
			else
				LogContent = null;
		}
		if (FCode.equals("CientIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CientIP = FValue.trim();
			}
			else
				CientIP = null;
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
		LDUserLogSchema other = (LDUserLogSchema)otherObject;
		return
			LogNo.equals(other.getLogNo())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& LogType.equals(other.getLogType())
			&& LogContent.equals(other.getLogContent())
			&& CientIP.equals(other.getCientIP())
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
		if( strFieldName.equals("LogNo") ) {
			return 0;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("Operator") ) {
			return 2;
		}
		if( strFieldName.equals("LogType") ) {
			return 3;
		}
		if( strFieldName.equals("LogContent") ) {
			return 4;
		}
		if( strFieldName.equals("CientIP") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
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
				strFieldName = "LogNo";
				break;
			case 1:
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "Operator";
				break;
			case 3:
				strFieldName = "LogType";
				break;
			case 4:
				strFieldName = "LogContent";
				break;
			case 5:
				strFieldName = "CientIP";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
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
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CientIP") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
