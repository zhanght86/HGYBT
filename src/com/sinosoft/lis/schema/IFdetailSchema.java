/*
 * <p>ClassName: IFdetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-19
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.IFdetailDB;

public class IFdetailSchema implements Schema
{
	// @Field
	/** 流水号 */
	private int LogNo;
	/** 接口日志号 */
	private int IFLogNo;
	/** 接口类型 */
	private String IFtype;
	/** 操作员 */
	private String Operator;
	/** 结果 */
	private String RCode;
	/** 文件名 */
	private String FileName;
	/** 备用1 */
	private String Bak1;
	/** 备用2 */
	private String Bak2;
	/** 备用3 */
	private String Bak3;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public IFdetailSchema()
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

	/** 流水号<P>日志号 */
	public int getLogNo()
	{
		return LogNo;
	}
	/** 流水号 */
	public void setLogNo(int aLogNo)
	{
		LogNo = aLogNo;
	}
	/** 流水号<P>日志号 */
	public void setLogNo(String aLogNo)
	{
		if (aLogNo != null && !aLogNo.equals(""))
		{
			Integer tInteger = new Integer(aLogNo);
			int i = tInteger.intValue();
			LogNo = i;
		}
	}

	/** 接口日志号<P> */
	public int getIFLogNo()
	{
		return IFLogNo;
	}
	/** 接口日志号 */
	public void setIFLogNo(int aIFLogNo)
	{
		IFLogNo = aIFLogNo;
	}
	/** 接口日志号<P> */
	public void setIFLogNo(String aIFLogNo)
	{
		if (aIFLogNo != null && !aIFLogNo.equals(""))
		{
			Integer tInteger = new Integer(aIFLogNo);
			int i = tInteger.intValue();
			IFLogNo = i;
		}
	}

	/** 接口类型<P> */
	public String getIFtype()
	{
		if (IFtype != null && !IFtype.equals("") && SysConst.CHANGECHARSET == true)
		{
			IFtype = StrTool.unicodeToGBK(IFtype);
		}
		return IFtype;
	}
	/** 接口类型 */
	public void setIFtype(String aIFtype)
	{
		IFtype = aIFtype;
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
	/** 结果<P>???果描述 */
	public String getRCode()
	{
		if (RCode != null && !RCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RCode = StrTool.unicodeToGBK(RCode);
		}
		return RCode;
	}
	/** 结果 */
	public void setRCode(String aRCode)
	{
		RCode = aRCode;
	}
	/** 文件名<P> */
	public String getFileName()
	{
		if (FileName != null && !FileName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FileName = StrTool.unicodeToGBK(FileName);
		}
		return FileName;
	}
	/** 文件名 */
	public void setFileName(String aFileName)
	{
		FileName = aFileName;
	}
	/** 备用1<P> */
	public String getBak1()
	{
		if (Bak1 != null && !Bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak1 = StrTool.unicodeToGBK(Bak1);
		}
		return Bak1;
	}
	/** 备用1 */
	public void setBak1(String aBak1)
	{
		Bak1 = aBak1;
	}
	/** 备用2<P> */
	public String getBak2()
	{
		if (Bak2 != null && !Bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak2 = StrTool.unicodeToGBK(Bak2);
		}
		return Bak2;
	}
	/** 备用2 */
	public void setBak2(String aBak2)
	{
		Bak2 = aBak2;
	}
	/** 备用3<P> */
	public String getBak3()
	{
		if (Bak3 != null && !Bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak3 = StrTool.unicodeToGBK(Bak3);
		}
		return Bak3;
	}
	/** 备用3 */
	public void setBak3(String aBak3)
	{
		Bak3 = aBak3;
	}
	/** Makedate<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** Makedate */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** Makedate<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** Maketime<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** Maketime */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 IFdetailSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(IFdetailSchema aIFdetailSchema)
	{
		this.LogNo = aIFdetailSchema.getLogNo();
		this.IFLogNo = aIFdetailSchema.getIFLogNo();
		this.IFtype = aIFdetailSchema.getIFtype();
		this.Operator = aIFdetailSchema.getOperator();
		this.RCode = aIFdetailSchema.getRCode();
		this.FileName = aIFdetailSchema.getFileName();
		this.Bak1 = aIFdetailSchema.getBak1();
		this.Bak2 = aIFdetailSchema.getBak2();
		this.Bak3 = aIFdetailSchema.getBak3();
		this.MakeDate = fDate.getDate( aIFdetailSchema.getMakeDate());
		this.MakeTime = aIFdetailSchema.getMakeTime();
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
			this.LogNo = rs.getInt("LogNo");
			this.IFLogNo = rs.getInt("IFLogNo");
			if( rs.getString("IFtype") == null )
				this.IFtype = null;
			else
				this.IFtype = rs.getString("IFtype").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("RCode") == null )
				this.RCode = null;
			else
				this.RCode = rs.getString("RCode").trim();

			if( rs.getString("FileName") == null )
				this.FileName = null;
			else
				this.FileName = rs.getString("FileName").trim();

			if( rs.getString("Bak1") == null )
				this.Bak1 = null;
			else
				this.Bak1 = rs.getString("Bak1").trim();

			if( rs.getString("Bak2") == null )
				this.Bak2 = null;
			else
				this.Bak2 = rs.getString("Bak2").trim();

			if( rs.getString("Bak3") == null )
				this.Bak3 = null;
			else
				this.Bak3 = rs.getString("Bak3").trim();

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
			tError.moduleName = "IFdetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public IFdetailSchema getSchema()
	{
		IFdetailSchema aIFdetailSchema = new IFdetailSchema();
		aIFdetailSchema.setSchema(this);
		return aIFdetailSchema;
	}

	public IFdetailDB getDB()
	{
		IFdetailDB aDBOper = new IFdetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpIFdetail描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(LogNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(IFLogNo) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(IFtype) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FileName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpIFdetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			IFLogNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			IFtype = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFdetailSchema";
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
		if (FCode.equals("IFLogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IFLogNo));
		}
		if (FCode.equals("IFtype"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IFtype));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equals("FileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileName));
		}
		if (FCode.equals("Bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak1));
		}
		if (FCode.equals("Bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak2));
		}
		if (FCode.equals("Bak3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak3));
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
				strFieldValue = String.valueOf(LogNo);
				break;
			case 1:
				strFieldValue = String.valueOf(IFLogNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(IFtype);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FileName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LogNo = i;
			}
		}
		if (FCode.equals("IFLogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				IFLogNo = i;
			}
		}
		if (FCode.equals("IFtype"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IFtype = FValue.trim();
			}
			else
				IFtype = null;
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
		if (FCode.equals("RCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCode = FValue.trim();
			}
			else
				RCode = null;
		}
		if (FCode.equals("FileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileName = FValue.trim();
			}
			else
				FileName = null;
		}
		if (FCode.equals("Bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak1 = FValue.trim();
			}
			else
				Bak1 = null;
		}
		if (FCode.equals("Bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak2 = FValue.trim();
			}
			else
				Bak2 = null;
		}
		if (FCode.equals("Bak3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak3 = FValue.trim();
			}
			else
				Bak3 = null;
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
		IFdetailSchema other = (IFdetailSchema)otherObject;
		return
			LogNo == other.getLogNo()
			&& IFLogNo == other.getIFLogNo()
			&& IFtype.equals(other.getIFtype())
			&& Operator.equals(other.getOperator())
			&& RCode.equals(other.getRCode())
			&& FileName.equals(other.getFileName())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
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
		if( strFieldName.equals("IFLogNo") ) {
			return 1;
		}
		if( strFieldName.equals("IFtype") ) {
			return 2;
		}
		if( strFieldName.equals("Operator") ) {
			return 3;
		}
		if( strFieldName.equals("RCode") ) {
			return 4;
		}
		if( strFieldName.equals("FileName") ) {
			return 5;
		}
		if( strFieldName.equals("Bak1") ) {
			return 6;
		}
		if( strFieldName.equals("Bak2") ) {
			return 7;
		}
		if( strFieldName.equals("Bak3") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
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
				strFieldName = "IFLogNo";
				break;
			case 2:
				strFieldName = "IFtype";
				break;
			case 3:
				strFieldName = "Operator";
				break;
			case 4:
				strFieldName = "RCode";
				break;
			case 5:
				strFieldName = "FileName";
				break;
			case 6:
				strFieldName = "Bak1";
				break;
			case 7:
				strFieldName = "Bak2";
				break;
			case 8:
				strFieldName = "Bak3";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IFLogNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IFtype") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak3") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
