/*
 * <p>ClassName: LKSubRiskCheckSchema </p>
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
import com.sinosoft.lis.db.LKSubRiskCheckDB;

public class LKSubRiskCheckSchema implements Schema
{
	// @Field
	/** 附加险代码 */
	private String SubRiskCode;
	/** 主险代码 */
	private String MainRiskCode;
	/** 是否指定保额标志 */
	private String LimitedAmntFlag;
	/** 指定保额 */
	private double LimitedAmnt;
	/** 是否指定保费标志 */
	private String LimitedPremFlag;
	/** 指定保费 */
	private double LimitedPrem;
	/** 限定主附险保额比例标志 */
	private String AmntScaleFlag;
	/** 主附险保额比例（附/主） */
	private double AmntScale;
	/** 限定主附险保费比例 */
	private String PremScaleFlag;
	/** 主附险保费比例（附/主） */
	private double PremScale;
	/** 备用字段1 */
	private String Temp1;
	/** 备用字段2 */
	private String Temp2;
	/** Temp3 */
	private int Temp3;
	/** 备用字段4 */
	private double Temp4;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKSubRiskCheckSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "SubRiskCode";
		pk[1] = "MainRiskCode";
		pk[2] = "LimitedAmntFlag";
		pk[3] = "LimitedPremFlag";
		pk[4] = "AmntScale";
		pk[5] = "PremScale";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 附加险代码<P>附加险代码 */
	public String getSubRiskCode()
	{
		if (SubRiskCode != null && !SubRiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubRiskCode = StrTool.unicodeToGBK(SubRiskCode);
		}
		return SubRiskCode;
	}
	/** 附加险代码 */
	public void setSubRiskCode(String aSubRiskCode)
	{
		SubRiskCode = aSubRiskCode;
	}
	/** 主险代码<P>主险代码 */
	public String getMainRiskCode()
	{
		if (MainRiskCode != null && !MainRiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainRiskCode = StrTool.unicodeToGBK(MainRiskCode);
		}
		return MainRiskCode;
	}
	/** 主险代码 */
	public void setMainRiskCode(String aMainRiskCode)
	{
		MainRiskCode = aMainRiskCode;
	}
	/** 是否指定保额标志<P>是否指定保额标志1--指定保额 0--不指定 */
	public String getLimitedAmntFlag()
	{
		if (LimitedAmntFlag != null && !LimitedAmntFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			LimitedAmntFlag = StrTool.unicodeToGBK(LimitedAmntFlag);
		}
		return LimitedAmntFlag;
	}
	/** 是否指定保额标志 */
	public void setLimitedAmntFlag(String aLimitedAmntFlag)
	{
		LimitedAmntFlag = aLimitedAmntFlag;
	}
	/** 指定保额<P>指定保额 */
	public double getLimitedAmnt()
	{
		return LimitedAmnt;
	}
	/** 指定保额 */
	public void setLimitedAmnt(double aLimitedAmnt)
	{
		LimitedAmnt = aLimitedAmnt;
	}
	/** 指定保额<P>指定保额 */
	public void setLimitedAmnt(String aLimitedAmnt)
	{
		if (aLimitedAmnt != null && !aLimitedAmnt.equals(""))
		{
			Double tDouble = new Double(aLimitedAmnt);
			double d = tDouble.doubleValue();
			LimitedAmnt = d;
		}
	}

	/** 是否指定保费标志<P>是否指定保费标志0--不指定1--指定<br> */
	public String getLimitedPremFlag()
	{
		if (LimitedPremFlag != null && !LimitedPremFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			LimitedPremFlag = StrTool.unicodeToGBK(LimitedPremFlag);
		}
		return LimitedPremFlag;
	}
	/** 是否指定保费标志 */
	public void setLimitedPremFlag(String aLimitedPremFlag)
	{
		LimitedPremFlag = aLimitedPremFlag;
	}
	/** 指定保费<P>指定保费 */
	public double getLimitedPrem()
	{
		return LimitedPrem;
	}
	/** 指定保费 */
	public void setLimitedPrem(double aLimitedPrem)
	{
		LimitedPrem = aLimitedPrem;
	}
	/** 指定保费<P>指定保费 */
	public void setLimitedPrem(String aLimitedPrem)
	{
		if (aLimitedPrem != null && !aLimitedPrem.equals(""))
		{
			Double tDouble = new Double(aLimitedPrem);
			double d = tDouble.doubleValue();
			LimitedPrem = d;
		}
	}

	/** 限定主附险保额比例标志<P>限定主附险保额比例标志0--不限定 1--限定 */
	public String getAmntScaleFlag()
	{
		if (AmntScaleFlag != null && !AmntScaleFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AmntScaleFlag = StrTool.unicodeToGBK(AmntScaleFlag);
		}
		return AmntScaleFlag;
	}
	/** 限定主附险保额比例标志 */
	public void setAmntScaleFlag(String aAmntScaleFlag)
	{
		AmntScaleFlag = aAmntScaleFlag;
	}
	/** 主附险保额比例（附/主）<P>主附险保额比例（附/主） */
	public double getAmntScale()
	{
		return AmntScale;
	}
	/** 主附险保额比例（附/主） */
	public void setAmntScale(double aAmntScale)
	{
		AmntScale = aAmntScale;
	}
	/** 主附险保额比例（附/主）<P>主附险保额比例（附/主） */
	public void setAmntScale(String aAmntScale)
	{
		if (aAmntScale != null && !aAmntScale.equals(""))
		{
			Double tDouble = new Double(aAmntScale);
			double d = tDouble.doubleValue();
			AmntScale = d;
		}
	}

	/** 限定主附险保费比例<P>限定主附险保费比例 0--不限定 1--限定 */
	public String getPremScaleFlag()
	{
		if (PremScaleFlag != null && !PremScaleFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PremScaleFlag = StrTool.unicodeToGBK(PremScaleFlag);
		}
		return PremScaleFlag;
	}
	/** 限定主附险保费比例 */
	public void setPremScaleFlag(String aPremScaleFlag)
	{
		PremScaleFlag = aPremScaleFlag;
	}
	/** 主附险保费比例（附/主）<P>主附险保费比例（附/主） */
	public double getPremScale()
	{
		return PremScale;
	}
	/** 主附险保费比例（附/主） */
	public void setPremScale(double aPremScale)
	{
		PremScale = aPremScale;
	}
	/** 主附险保费比例（附/主）<P>主附险保费比例（附/主） */
	public void setPremScale(String aPremScale)
	{
		if (aPremScale != null && !aPremScale.equals(""))
		{
			Double tDouble = new Double(aPremScale);
			double d = tDouble.doubleValue();
			PremScale = d;
		}
	}

	/** 备用字段1<P> */
	public String getTemp1()
	{
		if (Temp1 != null && !Temp1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp1 = StrTool.unicodeToGBK(Temp1);
		}
		return Temp1;
	}
	/** 备用字段1 */
	public void setTemp1(String aTemp1)
	{
		Temp1 = aTemp1;
	}
	/** 备用字段2<P> */
	public String getTemp2()
	{
		if (Temp2 != null && !Temp2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp2 = StrTool.unicodeToGBK(Temp2);
		}
		return Temp2;
	}
	/** 备用字段2 */
	public void setTemp2(String aTemp2)
	{
		Temp2 = aTemp2;
	}
	/** Temp3<P> */
	public int getTemp3()
	{
		return Temp3;
	}
	/** Temp3 */
	public void setTemp3(int aTemp3)
	{
		Temp3 = aTemp3;
	}
	/** Temp3<P> */
	public void setTemp3(String aTemp3)
	{
		if (aTemp3 != null && !aTemp3.equals(""))
		{
			Integer tInteger = new Integer(aTemp3);
			int i = tInteger.intValue();
			Temp3 = i;
		}
	}

	/** 备用字段4<P> */
	public double getTemp4()
	{
		return Temp4;
	}
	/** 备用字段4 */
	public void setTemp4(double aTemp4)
	{
		Temp4 = aTemp4;
	}
	/** 备用字段4<P> */
	public void setTemp4(String aTemp4)
	{
		if (aTemp4 != null && !aTemp4.equals(""))
		{
			Double tDouble = new Double(aTemp4);
			double d = tDouble.doubleValue();
			Temp4 = d;
		}
	}


	/**
	* 使用另外一个 LKSubRiskCheckSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKSubRiskCheckSchema aLKSubRiskCheckSchema)
	{
		this.SubRiskCode = aLKSubRiskCheckSchema.getSubRiskCode();
		this.MainRiskCode = aLKSubRiskCheckSchema.getMainRiskCode();
		this.LimitedAmntFlag = aLKSubRiskCheckSchema.getLimitedAmntFlag();
		this.LimitedAmnt = aLKSubRiskCheckSchema.getLimitedAmnt();
		this.LimitedPremFlag = aLKSubRiskCheckSchema.getLimitedPremFlag();
		this.LimitedPrem = aLKSubRiskCheckSchema.getLimitedPrem();
		this.AmntScaleFlag = aLKSubRiskCheckSchema.getAmntScaleFlag();
		this.AmntScale = aLKSubRiskCheckSchema.getAmntScale();
		this.PremScaleFlag = aLKSubRiskCheckSchema.getPremScaleFlag();
		this.PremScale = aLKSubRiskCheckSchema.getPremScale();
		this.Temp1 = aLKSubRiskCheckSchema.getTemp1();
		this.Temp2 = aLKSubRiskCheckSchema.getTemp2();
		this.Temp3 = aLKSubRiskCheckSchema.getTemp3();
		this.Temp4 = aLKSubRiskCheckSchema.getTemp4();
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
			if( rs.getString("SubRiskCode") == null )
				this.SubRiskCode = null;
			else
				this.SubRiskCode = rs.getString("SubRiskCode").trim();

			if( rs.getString("MainRiskCode") == null )
				this.MainRiskCode = null;
			else
				this.MainRiskCode = rs.getString("MainRiskCode").trim();

			if( rs.getString("LimitedAmntFlag") == null )
				this.LimitedAmntFlag = null;
			else
				this.LimitedAmntFlag = rs.getString("LimitedAmntFlag").trim();

			this.LimitedAmnt = rs.getDouble("LimitedAmnt");
			if( rs.getString("LimitedPremFlag") == null )
				this.LimitedPremFlag = null;
			else
				this.LimitedPremFlag = rs.getString("LimitedPremFlag").trim();

			this.LimitedPrem = rs.getDouble("LimitedPrem");
			if( rs.getString("AmntScaleFlag") == null )
				this.AmntScaleFlag = null;
			else
				this.AmntScaleFlag = rs.getString("AmntScaleFlag").trim();

			this.AmntScale = rs.getDouble("AmntScale");
			if( rs.getString("PremScaleFlag") == null )
				this.PremScaleFlag = null;
			else
				this.PremScaleFlag = rs.getString("PremScaleFlag").trim();

			this.PremScale = rs.getDouble("PremScale");
			if( rs.getString("Temp1") == null )
				this.Temp1 = null;
			else
				this.Temp1 = rs.getString("Temp1").trim();

			if( rs.getString("Temp2") == null )
				this.Temp2 = null;
			else
				this.Temp2 = rs.getString("Temp2").trim();

			this.Temp3 = rs.getInt("Temp3");
			this.Temp4 = rs.getDouble("Temp4");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKSubRiskCheckSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKSubRiskCheckSchema getSchema()
	{
		LKSubRiskCheckSchema aLKSubRiskCheckSchema = new LKSubRiskCheckSchema();
		aLKSubRiskCheckSchema.setSchema(this);
		return aLKSubRiskCheckSchema;
	}

	public LKSubRiskCheckDB getDB()
	{
		LKSubRiskCheckDB aDBOper = new LKSubRiskCheckDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKSubRiskCheck描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SubRiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainRiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LimitedAmntFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LimitedAmnt) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(LimitedPremFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LimitedPrem) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AmntScaleFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AmntScale) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PremScaleFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PremScale) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp2) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Temp3) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Temp4);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKSubRiskCheck>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LimitedAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LimitedAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			LimitedPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			LimitedPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AmntScaleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AmntScale = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			PremScaleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PremScale = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Temp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Temp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Temp3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			Temp4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKSubRiskCheckSchema";
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
		if (FCode.equals("SubRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskCode));
		}
		if (FCode.equals("MainRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskCode));
		}
		if (FCode.equals("LimitedAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitedAmntFlag));
		}
		if (FCode.equals("LimitedAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitedAmnt));
		}
		if (FCode.equals("LimitedPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitedPremFlag));
		}
		if (FCode.equals("LimitedPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitedPrem));
		}
		if (FCode.equals("AmntScaleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntScaleFlag));
		}
		if (FCode.equals("AmntScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntScale));
		}
		if (FCode.equals("PremScaleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremScaleFlag));
		}
		if (FCode.equals("PremScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremScale));
		}
		if (FCode.equals("Temp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp1));
		}
		if (FCode.equals("Temp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp2));
		}
		if (FCode.equals("Temp3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp3));
		}
		if (FCode.equals("Temp4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp4));
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
				strFieldValue = StrTool.GBKToUnicode(SubRiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LimitedAmntFlag);
				break;
			case 3:
				strFieldValue = String.valueOf(LimitedAmnt);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LimitedPremFlag);
				break;
			case 5:
				strFieldValue = String.valueOf(LimitedPrem);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AmntScaleFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(AmntScale);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PremScaleFlag);
				break;
			case 9:
				strFieldValue = String.valueOf(PremScale);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Temp1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Temp2);
				break;
			case 12:
				strFieldValue = String.valueOf(Temp3);
				break;
			case 13:
				strFieldValue = String.valueOf(Temp4);
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

		if (FCode.equals("SubRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskCode = FValue.trim();
			}
			else
				SubRiskCode = null;
		}
		if (FCode.equals("MainRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskCode = FValue.trim();
			}
			else
				MainRiskCode = null;
		}
		if (FCode.equals("LimitedAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LimitedAmntFlag = FValue.trim();
			}
			else
				LimitedAmntFlag = null;
		}
		if (FCode.equals("LimitedAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LimitedAmnt = d;
			}
		}
		if (FCode.equals("LimitedPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LimitedPremFlag = FValue.trim();
			}
			else
				LimitedPremFlag = null;
		}
		if (FCode.equals("LimitedPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LimitedPrem = d;
			}
		}
		if (FCode.equals("AmntScaleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmntScaleFlag = FValue.trim();
			}
			else
				AmntScaleFlag = null;
		}
		if (FCode.equals("AmntScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AmntScale = d;
			}
		}
		if (FCode.equals("PremScaleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremScaleFlag = FValue.trim();
			}
			else
				PremScaleFlag = null;
		}
		if (FCode.equals("PremScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremScale = d;
			}
		}
		if (FCode.equals("Temp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp1 = FValue.trim();
			}
			else
				Temp1 = null;
		}
		if (FCode.equals("Temp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp2 = FValue.trim();
			}
			else
				Temp2 = null;
		}
		if (FCode.equals("Temp3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Temp3 = i;
			}
		}
		if (FCode.equals("Temp4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Temp4 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKSubRiskCheckSchema other = (LKSubRiskCheckSchema)otherObject;
		return
			SubRiskCode.equals(other.getSubRiskCode())
			&& MainRiskCode.equals(other.getMainRiskCode())
			&& LimitedAmntFlag.equals(other.getLimitedAmntFlag())
			&& LimitedAmnt == other.getLimitedAmnt()
			&& LimitedPremFlag.equals(other.getLimitedPremFlag())
			&& LimitedPrem == other.getLimitedPrem()
			&& AmntScaleFlag.equals(other.getAmntScaleFlag())
			&& AmntScale == other.getAmntScale()
			&& PremScaleFlag.equals(other.getPremScaleFlag())
			&& PremScale == other.getPremScale()
			&& Temp1.equals(other.getTemp1())
			&& Temp2.equals(other.getTemp2())
			&& Temp3 == other.getTemp3()
			&& Temp4 == other.getTemp4();
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
		if( strFieldName.equals("SubRiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("LimitedAmntFlag") ) {
			return 2;
		}
		if( strFieldName.equals("LimitedAmnt") ) {
			return 3;
		}
		if( strFieldName.equals("LimitedPremFlag") ) {
			return 4;
		}
		if( strFieldName.equals("LimitedPrem") ) {
			return 5;
		}
		if( strFieldName.equals("AmntScaleFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AmntScale") ) {
			return 7;
		}
		if( strFieldName.equals("PremScaleFlag") ) {
			return 8;
		}
		if( strFieldName.equals("PremScale") ) {
			return 9;
		}
		if( strFieldName.equals("Temp1") ) {
			return 10;
		}
		if( strFieldName.equals("Temp2") ) {
			return 11;
		}
		if( strFieldName.equals("Temp3") ) {
			return 12;
		}
		if( strFieldName.equals("Temp4") ) {
			return 13;
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
				strFieldName = "SubRiskCode";
				break;
			case 1:
				strFieldName = "MainRiskCode";
				break;
			case 2:
				strFieldName = "LimitedAmntFlag";
				break;
			case 3:
				strFieldName = "LimitedAmnt";
				break;
			case 4:
				strFieldName = "LimitedPremFlag";
				break;
			case 5:
				strFieldName = "LimitedPrem";
				break;
			case 6:
				strFieldName = "AmntScaleFlag";
				break;
			case 7:
				strFieldName = "AmntScale";
				break;
			case 8:
				strFieldName = "PremScaleFlag";
				break;
			case 9:
				strFieldName = "PremScale";
				break;
			case 10:
				strFieldName = "Temp1";
				break;
			case 11:
				strFieldName = "Temp2";
				break;
			case 12:
				strFieldName = "Temp3";
				break;
			case 13:
				strFieldName = "Temp4";
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
		if( strFieldName.equals("SubRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LimitedAmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LimitedAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LimitedPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LimitedPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AmntScaleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmntScale") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremScaleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremScale") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Temp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Temp4") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
