/*
 * <p>ClassName: YBTLinePoliciesSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-12
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.YBTLinePoliciesDB;

public class YBTLinePoliciesSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 保单编号 */
	private String PYNO;
	/** 保单险种 */
	private String LYPLAN;
	/** 保单状态 */
	private String PYSTU;
	/** 被保人姓名 */
	private String PYNAME;
	/** 被保险人身份证 */
	private String PYID;
	/** 被保险人生日 */
	private Date PYDOB;
	/** 保额 */
	private String LYSA;
	/** 提取日期 */
	private Date MakeDate;
	/** 提取时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public YBTLinePoliciesSchema()
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
	/** 保单编号<P> */
	public String getPYNO()
	{
		if (PYNO != null && !PYNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			PYNO = StrTool.unicodeToGBK(PYNO);
		}
		return PYNO;
	}
	/** 保单编号 */
	public void setPYNO(String aPYNO)
	{
		PYNO = aPYNO;
	}
	/** 保单险种<P> */
	public String getLYPLAN()
	{
		if (LYPLAN != null && !LYPLAN.equals("") && SysConst.CHANGECHARSET == true)
		{
			LYPLAN = StrTool.unicodeToGBK(LYPLAN);
		}
		return LYPLAN;
	}
	/** 保单险种 */
	public void setLYPLAN(String aLYPLAN)
	{
		LYPLAN = aLYPLAN;
	}
	/** 保单状态<P> */
	public String getPYSTU()
	{
		if (PYSTU != null && !PYSTU.equals("") && SysConst.CHANGECHARSET == true)
		{
			PYSTU = StrTool.unicodeToGBK(PYSTU);
		}
		return PYSTU;
	}
	/** 保单状态 */
	public void setPYSTU(String aPYSTU)
	{
		PYSTU = aPYSTU;
	}
	/** 被保人姓名<P> */
	public String getPYNAME()
	{
		if (PYNAME != null && !PYNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			PYNAME = StrTool.unicodeToGBK(PYNAME);
		}
		return PYNAME;
	}
	/** 被保人姓名 */
	public void setPYNAME(String aPYNAME)
	{
		PYNAME = aPYNAME;
	}
	/** 被保险人身份证<P> */
	public String getPYID()
	{
		if (PYID != null && !PYID.equals("") && SysConst.CHANGECHARSET == true)
		{
			PYID = StrTool.unicodeToGBK(PYID);
		}
		return PYID;
	}
	/** 被保险人身份证 */
	public void setPYID(String aPYID)
	{
		PYID = aPYID;
	}
	/** 被保险人生日<P> */
	public String getPYDOB()
	{
		if( PYDOB != null )
			return fDate.getString(PYDOB);
		else
			return null;
	}
	/** 被保险人生日 */
	public void setPYDOB(Date aPYDOB)
	{
		PYDOB = aPYDOB;
	}
	/** 被保险人生日<P> */
	public void setPYDOB(String aPYDOB)
	{
		if (aPYDOB != null && !aPYDOB.equals("") )
		{
			PYDOB = fDate.getDate( aPYDOB );
		}
		else
			PYDOB = null;
	}

	/** 保额<P> */
	public String getLYSA()
	{
		if (LYSA != null && !LYSA.equals("") && SysConst.CHANGECHARSET == true)
		{
			LYSA = StrTool.unicodeToGBK(LYSA);
		}
		return LYSA;
	}
	/** 保额 */
	public void setLYSA(String aLYSA)
	{
		LYSA = aLYSA;
	}
	/** 提取日期<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 提取日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 提取日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 提取时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 提取时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 最后修改日期<P> */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 最后修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 最后修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 YBTLinePoliciesSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(YBTLinePoliciesSchema aYBTLinePoliciesSchema)
	{
		this.SerialNo = aYBTLinePoliciesSchema.getSerialNo();
		this.PYNO = aYBTLinePoliciesSchema.getPYNO();
		this.LYPLAN = aYBTLinePoliciesSchema.getLYPLAN();
		this.PYSTU = aYBTLinePoliciesSchema.getPYSTU();
		this.PYNAME = aYBTLinePoliciesSchema.getPYNAME();
		this.PYID = aYBTLinePoliciesSchema.getPYID();
		this.PYDOB = fDate.getDate( aYBTLinePoliciesSchema.getPYDOB());
		this.LYSA = aYBTLinePoliciesSchema.getLYSA();
		this.MakeDate = fDate.getDate( aYBTLinePoliciesSchema.getMakeDate());
		this.MakeTime = aYBTLinePoliciesSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aYBTLinePoliciesSchema.getModifyDate());
		this.ModifyTime = aYBTLinePoliciesSchema.getModifyTime();
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

			if( rs.getString("PYNO") == null )
				this.PYNO = null;
			else
				this.PYNO = rs.getString("PYNO").trim();

			if( rs.getString("LYPLAN") == null )
				this.LYPLAN = null;
			else
				this.LYPLAN = rs.getString("LYPLAN").trim();

			if( rs.getString("PYSTU") == null )
				this.PYSTU = null;
			else
				this.PYSTU = rs.getString("PYSTU").trim();

			if( rs.getString("PYNAME") == null )
				this.PYNAME = null;
			else
				this.PYNAME = rs.getString("PYNAME").trim();

			if( rs.getString("PYID") == null )
				this.PYID = null;
			else
				this.PYID = rs.getString("PYID").trim();

			this.PYDOB = rs.getDate("PYDOB");
			if( rs.getString("LYSA") == null )
				this.LYSA = null;
			else
				this.LYSA = rs.getString("LYSA").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTLinePoliciesSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public YBTLinePoliciesSchema getSchema()
	{
		YBTLinePoliciesSchema aYBTLinePoliciesSchema = new YBTLinePoliciesSchema();
		aYBTLinePoliciesSchema.setSchema(this);
		return aYBTLinePoliciesSchema;
	}

	public YBTLinePoliciesDB getDB()
	{
		YBTLinePoliciesDB aDBOper = new YBTLinePoliciesDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTLinePolicies描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PYNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LYPLAN) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PYSTU) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PYNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PYID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( PYDOB )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LYSA) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTLinePolicies>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PYNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LYPLAN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PYSTU = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PYNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PYID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PYDOB = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			LYSA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTLinePoliciesSchema";
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
		if (FCode.equals("PYNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PYNO));
		}
		if (FCode.equals("LYPLAN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LYPLAN));
		}
		if (FCode.equals("PYSTU"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PYSTU));
		}
		if (FCode.equals("PYNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PYNAME));
		}
		if (FCode.equals("PYID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PYID));
		}
		if (FCode.equals("PYDOB"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPYDOB()));
		}
		if (FCode.equals("LYSA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LYSA));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(PYNO);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LYPLAN);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PYSTU);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PYNAME);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PYID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPYDOB()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(LYSA);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equals("PYNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PYNO = FValue.trim();
			}
			else
				PYNO = null;
		}
		if (FCode.equals("LYPLAN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LYPLAN = FValue.trim();
			}
			else
				LYPLAN = null;
		}
		if (FCode.equals("PYSTU"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PYSTU = FValue.trim();
			}
			else
				PYSTU = null;
		}
		if (FCode.equals("PYNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PYNAME = FValue.trim();
			}
			else
				PYNAME = null;
		}
		if (FCode.equals("PYID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PYID = FValue.trim();
			}
			else
				PYID = null;
		}
		if (FCode.equals("PYDOB"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PYDOB = fDate.getDate( FValue );
			}
			else
				PYDOB = null;
		}
		if (FCode.equals("LYSA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LYSA = FValue.trim();
			}
			else
				LYSA = null;
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
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equals("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		YBTLinePoliciesSchema other = (YBTLinePoliciesSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& PYNO.equals(other.getPYNO())
			&& LYPLAN.equals(other.getLYPLAN())
			&& PYSTU.equals(other.getPYSTU())
			&& PYNAME.equals(other.getPYNAME())
			&& PYID.equals(other.getPYID())
			&& fDate.getString(PYDOB).equals(other.getPYDOB())
			&& LYSA.equals(other.getLYSA())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("PYNO") ) {
			return 1;
		}
		if( strFieldName.equals("LYPLAN") ) {
			return 2;
		}
		if( strFieldName.equals("PYSTU") ) {
			return 3;
		}
		if( strFieldName.equals("PYNAME") ) {
			return 4;
		}
		if( strFieldName.equals("PYID") ) {
			return 5;
		}
		if( strFieldName.equals("PYDOB") ) {
			return 6;
		}
		if( strFieldName.equals("LYSA") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
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
				strFieldName = "PYNO";
				break;
			case 2:
				strFieldName = "LYPLAN";
				break;
			case 3:
				strFieldName = "PYSTU";
				break;
			case 4:
				strFieldName = "PYNAME";
				break;
			case 5:
				strFieldName = "PYID";
				break;
			case 6:
				strFieldName = "PYDOB";
				break;
			case 7:
				strFieldName = "LYSA";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("PYNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LYPLAN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PYSTU") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PYNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PYID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PYDOB") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LYSA") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
