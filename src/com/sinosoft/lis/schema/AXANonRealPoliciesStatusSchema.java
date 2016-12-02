/*
 * <p>ClassName: AXANonRealPoliciesStatusSchema </p>
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
import com.sinosoft.lis.db.AXANonRealPoliciesStatusDB;

public class AXANonRealPoliciesStatusSchema implements Schema
{
	// @Field
	/** 保单编号 */
	private String YNO;
	/** 核保状态
核保状态 */
	private String YSTUA;
	/** 总保费 */
	private double YMPREM;
	/** 基本保费 */
	private double YMPREM1;
	/** 定投保费 */
	private double YMPREM2;
	/** 追加投资保费 */
	private double YMPREM3;
	/** 被保险人姓名 */
	private String YRNAME;
	/** 被保险人身份证id */
	private String YRID;
	/** 投保人姓名 */
	private String YOWNER;
	/** 险种代码 */
	private String YPLAN;
	/** 保额 */
	private double YSA;
	/** 缴费方式 */
	private String YMODE;
	/** 缴费期限 */
	private int YLPRE;
	/** 缴费类型
缴费类型 */
	private String YLPREA;
	/** 维护日期 */
	private Date YMDAT;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AXANonRealPoliciesStatusSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "YNO";
		pk[1] = "YMDAT";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 保单编号<P> */
	public String getYNO()
	{
		if (YNO != null && !YNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			YNO = StrTool.unicodeToGBK(YNO);
		}
		return YNO;
	}
	/** 保单编号 */
	public void setYNO(String aYNO)
	{
		YNO = aYNO;
	}
	/** 核保状态
核保状态<P> */
	public String getYSTUA()
	{
		if (YSTUA != null && !YSTUA.equals("") && SysConst.CHANGECHARSET == true)
		{
			YSTUA = StrTool.unicodeToGBK(YSTUA);
		}
		return YSTUA;
	}
	/** 核保状态
核保状态 */
	public void setYSTUA(String aYSTUA)
	{
		YSTUA = aYSTUA;
	}
	/** 总保费<P> */
	public double getYMPREM()
	{
		return YMPREM;
	}
	/** 总保费 */
	public void setYMPREM(double aYMPREM)
	{
		YMPREM = aYMPREM;
	}
	/** 总保费<P> */
	public void setYMPREM(String aYMPREM)
	{
		if (aYMPREM != null && !aYMPREM.equals(""))
		{
			Double tDouble = new Double(aYMPREM);
			double d = tDouble.doubleValue();
			YMPREM = d;
		}
	}

	/** 基本保费<P> */
	public double getYMPREM1()
	{
		return YMPREM1;
	}
	/** 基本保费 */
	public void setYMPREM1(double aYMPREM1)
	{
		YMPREM1 = aYMPREM1;
	}
	/** 基本保费<P> */
	public void setYMPREM1(String aYMPREM1)
	{
		if (aYMPREM1 != null && !aYMPREM1.equals(""))
		{
			Double tDouble = new Double(aYMPREM1);
			double d = tDouble.doubleValue();
			YMPREM1 = d;
		}
	}

	/** 定投保费<P> */
	public double getYMPREM2()
	{
		return YMPREM2;
	}
	/** 定投保费 */
	public void setYMPREM2(double aYMPREM2)
	{
		YMPREM2 = aYMPREM2;
	}
	/** 定投保费<P> */
	public void setYMPREM2(String aYMPREM2)
	{
		if (aYMPREM2 != null && !aYMPREM2.equals(""))
		{
			Double tDouble = new Double(aYMPREM2);
			double d = tDouble.doubleValue();
			YMPREM2 = d;
		}
	}

	/** 追加投资保费<P> */
	public double getYMPREM3()
	{
		return YMPREM3;
	}
	/** 追加投资保费 */
	public void setYMPREM3(double aYMPREM3)
	{
		YMPREM3 = aYMPREM3;
	}
	/** 追加投资保费<P> */
	public void setYMPREM3(String aYMPREM3)
	{
		if (aYMPREM3 != null && !aYMPREM3.equals(""))
		{
			Double tDouble = new Double(aYMPREM3);
			double d = tDouble.doubleValue();
			YMPREM3 = d;
		}
	}

	/** 被保险人姓名<P> */
	public String getYRNAME()
	{
		if (YRNAME != null && !YRNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			YRNAME = StrTool.unicodeToGBK(YRNAME);
		}
		return YRNAME;
	}
	/** 被保险人姓名 */
	public void setYRNAME(String aYRNAME)
	{
		YRNAME = aYRNAME;
	}
	/** 被保险人身份证id<P> */
	public String getYRID()
	{
		if (YRID != null && !YRID.equals("") && SysConst.CHANGECHARSET == true)
		{
			YRID = StrTool.unicodeToGBK(YRID);
		}
		return YRID;
	}
	/** 被保险人身份证id */
	public void setYRID(String aYRID)
	{
		YRID = aYRID;
	}
	/** 投保人姓名<P> */
	public String getYOWNER()
	{
		if (YOWNER != null && !YOWNER.equals("") && SysConst.CHANGECHARSET == true)
		{
			YOWNER = StrTool.unicodeToGBK(YOWNER);
		}
		return YOWNER;
	}
	/** 投保人姓名 */
	public void setYOWNER(String aYOWNER)
	{
		YOWNER = aYOWNER;
	}
	/** 险种代码<P> */
	public String getYPLAN()
	{
		if (YPLAN != null && !YPLAN.equals("") && SysConst.CHANGECHARSET == true)
		{
			YPLAN = StrTool.unicodeToGBK(YPLAN);
		}
		return YPLAN;
	}
	/** 险种代码 */
	public void setYPLAN(String aYPLAN)
	{
		YPLAN = aYPLAN;
	}
	/** 保额<P> */
	public double getYSA()
	{
		return YSA;
	}
	/** 保额 */
	public void setYSA(double aYSA)
	{
		YSA = aYSA;
	}
	/** 保额<P> */
	public void setYSA(String aYSA)
	{
		if (aYSA != null && !aYSA.equals(""))
		{
			Double tDouble = new Double(aYSA);
			double d = tDouble.doubleValue();
			YSA = d;
		}
	}

	/** 缴费方式<P> */
	public String getYMODE()
	{
		if (YMODE != null && !YMODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			YMODE = StrTool.unicodeToGBK(YMODE);
		}
		return YMODE;
	}
	/** 缴费方式 */
	public void setYMODE(String aYMODE)
	{
		YMODE = aYMODE;
	}
	/** 缴费期限<P> */
	public int getYLPRE()
	{
		return YLPRE;
	}
	/** 缴费期限 */
	public void setYLPRE(int aYLPRE)
	{
		YLPRE = aYLPRE;
	}
	/** 缴费期限<P> */
	public void setYLPRE(String aYLPRE)
	{
		if (aYLPRE != null && !aYLPRE.equals(""))
		{
			Integer tInteger = new Integer(aYLPRE);
			int i = tInteger.intValue();
			YLPRE = i;
		}
	}

	/** 缴费类型
缴费类型<P> */
	public String getYLPREA()
	{
		if (YLPREA != null && !YLPREA.equals("") && SysConst.CHANGECHARSET == true)
		{
			YLPREA = StrTool.unicodeToGBK(YLPREA);
		}
		return YLPREA;
	}
	/** 缴费类型
缴费类型 */
	public void setYLPREA(String aYLPREA)
	{
		YLPREA = aYLPREA;
	}
	/** 维护日期<P> */
	public String getYMDAT()
	{
		if( YMDAT != null )
			return fDate.getString(YMDAT);
		else
			return null;
	}
	/** 维护日期 */
	public void setYMDAT(Date aYMDAT)
	{
		YMDAT = aYMDAT;
	}
	/** 维护日期<P> */
	public void setYMDAT(String aYMDAT)
	{
		if (aYMDAT != null && !aYMDAT.equals("") )
		{
			YMDAT = fDate.getDate( aYMDAT );
		}
		else
			YMDAT = null;
	}


	/**
	* 使用另外一个 AXANonRealPoliciesStatusSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AXANonRealPoliciesStatusSchema aAXANonRealPoliciesStatusSchema)
	{
		this.YNO = aAXANonRealPoliciesStatusSchema.getYNO();
		this.YSTUA = aAXANonRealPoliciesStatusSchema.getYSTUA();
		this.YMPREM = aAXANonRealPoliciesStatusSchema.getYMPREM();
		this.YMPREM1 = aAXANonRealPoliciesStatusSchema.getYMPREM1();
		this.YMPREM2 = aAXANonRealPoliciesStatusSchema.getYMPREM2();
		this.YMPREM3 = aAXANonRealPoliciesStatusSchema.getYMPREM3();
		this.YRNAME = aAXANonRealPoliciesStatusSchema.getYRNAME();
		this.YRID = aAXANonRealPoliciesStatusSchema.getYRID();
		this.YOWNER = aAXANonRealPoliciesStatusSchema.getYOWNER();
		this.YPLAN = aAXANonRealPoliciesStatusSchema.getYPLAN();
		this.YSA = aAXANonRealPoliciesStatusSchema.getYSA();
		this.YMODE = aAXANonRealPoliciesStatusSchema.getYMODE();
		this.YLPRE = aAXANonRealPoliciesStatusSchema.getYLPRE();
		this.YLPREA = aAXANonRealPoliciesStatusSchema.getYLPREA();
		this.YMDAT = fDate.getDate( aAXANonRealPoliciesStatusSchema.getYMDAT());
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
			if( rs.getString("YNO") == null )
				this.YNO = null;
			else
				this.YNO = rs.getString("YNO").trim();

			if( rs.getString("YSTUA") == null )
				this.YSTUA = null;
			else
				this.YSTUA = rs.getString("YSTUA").trim();

			this.YMPREM = rs.getDouble("YMPREM");
			this.YMPREM1 = rs.getDouble("YMPREM1");
			this.YMPREM2 = rs.getDouble("YMPREM2");
			this.YMPREM3 = rs.getDouble("YMPREM3");
			if( rs.getString("YRNAME") == null )
				this.YRNAME = null;
			else
				this.YRNAME = rs.getString("YRNAME").trim();

			if( rs.getString("YRID") == null )
				this.YRID = null;
			else
				this.YRID = rs.getString("YRID").trim();

			if( rs.getString("YOWNER") == null )
				this.YOWNER = null;
			else
				this.YOWNER = rs.getString("YOWNER").trim();

			if( rs.getString("YPLAN") == null )
				this.YPLAN = null;
			else
				this.YPLAN = rs.getString("YPLAN").trim();

			this.YSA = rs.getDouble("YSA");
			if( rs.getString("YMODE") == null )
				this.YMODE = null;
			else
				this.YMODE = rs.getString("YMODE").trim();

			this.YLPRE = rs.getInt("YLPRE");
			if( rs.getString("YLPREA") == null )
				this.YLPREA = null;
			else
				this.YLPREA = rs.getString("YLPREA").trim();

			this.YMDAT = rs.getDate("YMDAT");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXANonRealPoliciesStatusSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AXANonRealPoliciesStatusSchema getSchema()
	{
		AXANonRealPoliciesStatusSchema aAXANonRealPoliciesStatusSchema = new AXANonRealPoliciesStatusSchema();
		aAXANonRealPoliciesStatusSchema.setSchema(this);
		return aAXANonRealPoliciesStatusSchema;
	}

	public AXANonRealPoliciesStatusDB getDB()
	{
		AXANonRealPoliciesStatusDB aDBOper = new AXANonRealPoliciesStatusDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXANonRealPoliciesStatus描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(YNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YSTUA) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM1) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM2) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM3) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YRNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YRID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YOWNER) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YPLAN) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YSA) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YMODE) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YLPRE) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YLPREA) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( YMDAT )) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXANonRealPoliciesStatus>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			YNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			YSTUA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			YMPREM = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			YRNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			YRID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			YOWNER = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			YPLAN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			YSA = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			YMODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			YLPRE= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			YLPREA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			YMDAT = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXANonRealPoliciesStatusSchema";
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
		if (FCode.equals("YNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YNO));
		}
		if (FCode.equals("YSTUA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YSTUA));
		}
		if (FCode.equals("YMPREM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YMPREM));
		}
		if (FCode.equals("YMPREM1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YMPREM1));
		}
		if (FCode.equals("YMPREM2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YMPREM2));
		}
		if (FCode.equals("YMPREM3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YMPREM3));
		}
		if (FCode.equals("YRNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YRNAME));
		}
		if (FCode.equals("YRID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YRID));
		}
		if (FCode.equals("YOWNER"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YOWNER));
		}
		if (FCode.equals("YPLAN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YPLAN));
		}
		if (FCode.equals("YSA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YSA));
		}
		if (FCode.equals("YMODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YMODE));
		}
		if (FCode.equals("YLPRE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YLPRE));
		}
		if (FCode.equals("YLPREA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YLPREA));
		}
		if (FCode.equals("YMDAT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getYMDAT()));
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
				strFieldValue = StrTool.GBKToUnicode(YNO);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(YSTUA);
				break;
			case 2:
				strFieldValue = String.valueOf(YMPREM);
				break;
			case 3:
				strFieldValue = String.valueOf(YMPREM1);
				break;
			case 4:
				strFieldValue = String.valueOf(YMPREM2);
				break;
			case 5:
				strFieldValue = String.valueOf(YMPREM3);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(YRNAME);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(YRID);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(YOWNER);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(YPLAN);
				break;
			case 10:
				strFieldValue = String.valueOf(YSA);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(YMODE);
				break;
			case 12:
				strFieldValue = String.valueOf(YLPRE);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(YLPREA);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getYMDAT()));
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

		if (FCode.equals("YNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YNO = FValue.trim();
			}
			else
				YNO = null;
		}
		if (FCode.equals("YSTUA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YSTUA = FValue.trim();
			}
			else
				YSTUA = null;
		}
		if (FCode.equals("YMPREM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YMPREM = d;
			}
		}
		if (FCode.equals("YMPREM1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YMPREM1 = d;
			}
		}
		if (FCode.equals("YMPREM2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YMPREM2 = d;
			}
		}
		if (FCode.equals("YMPREM3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YMPREM3 = d;
			}
		}
		if (FCode.equals("YRNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YRNAME = FValue.trim();
			}
			else
				YRNAME = null;
		}
		if (FCode.equals("YRID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YRID = FValue.trim();
			}
			else
				YRID = null;
		}
		if (FCode.equals("YOWNER"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YOWNER = FValue.trim();
			}
			else
				YOWNER = null;
		}
		if (FCode.equals("YPLAN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YPLAN = FValue.trim();
			}
			else
				YPLAN = null;
		}
		if (FCode.equals("YSA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YSA = d;
			}
		}
		if (FCode.equals("YMODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YMODE = FValue.trim();
			}
			else
				YMODE = null;
		}
		if (FCode.equals("YLPRE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				YLPRE = i;
			}
		}
		if (FCode.equals("YLPREA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YLPREA = FValue.trim();
			}
			else
				YLPREA = null;
		}
		if (FCode.equals("YMDAT"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				YMDAT = fDate.getDate( FValue );
			}
			else
				YMDAT = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AXANonRealPoliciesStatusSchema other = (AXANonRealPoliciesStatusSchema)otherObject;
		return
			YNO.equals(other.getYNO())
			&& YSTUA.equals(other.getYSTUA())
			&& YMPREM == other.getYMPREM()
			&& YMPREM1 == other.getYMPREM1()
			&& YMPREM2 == other.getYMPREM2()
			&& YMPREM3 == other.getYMPREM3()
			&& YRNAME.equals(other.getYRNAME())
			&& YRID.equals(other.getYRID())
			&& YOWNER.equals(other.getYOWNER())
			&& YPLAN.equals(other.getYPLAN())
			&& YSA == other.getYSA()
			&& YMODE.equals(other.getYMODE())
			&& YLPRE == other.getYLPRE()
			&& YLPREA.equals(other.getYLPREA())
			&& fDate.getString(YMDAT).equals(other.getYMDAT());
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
		if( strFieldName.equals("YNO") ) {
			return 0;
		}
		if( strFieldName.equals("YSTUA") ) {
			return 1;
		}
		if( strFieldName.equals("YMPREM") ) {
			return 2;
		}
		if( strFieldName.equals("YMPREM1") ) {
			return 3;
		}
		if( strFieldName.equals("YMPREM2") ) {
			return 4;
		}
		if( strFieldName.equals("YMPREM3") ) {
			return 5;
		}
		if( strFieldName.equals("YRNAME") ) {
			return 6;
		}
		if( strFieldName.equals("YRID") ) {
			return 7;
		}
		if( strFieldName.equals("YOWNER") ) {
			return 8;
		}
		if( strFieldName.equals("YPLAN") ) {
			return 9;
		}
		if( strFieldName.equals("YSA") ) {
			return 10;
		}
		if( strFieldName.equals("YMODE") ) {
			return 11;
		}
		if( strFieldName.equals("YLPRE") ) {
			return 12;
		}
		if( strFieldName.equals("YLPREA") ) {
			return 13;
		}
		if( strFieldName.equals("YMDAT") ) {
			return 14;
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
				strFieldName = "YNO";
				break;
			case 1:
				strFieldName = "YSTUA";
				break;
			case 2:
				strFieldName = "YMPREM";
				break;
			case 3:
				strFieldName = "YMPREM1";
				break;
			case 4:
				strFieldName = "YMPREM2";
				break;
			case 5:
				strFieldName = "YMPREM3";
				break;
			case 6:
				strFieldName = "YRNAME";
				break;
			case 7:
				strFieldName = "YRID";
				break;
			case 8:
				strFieldName = "YOWNER";
				break;
			case 9:
				strFieldName = "YPLAN";
				break;
			case 10:
				strFieldName = "YSA";
				break;
			case 11:
				strFieldName = "YMODE";
				break;
			case 12:
				strFieldName = "YLPRE";
				break;
			case 13:
				strFieldName = "YLPREA";
				break;
			case 14:
				strFieldName = "YMDAT";
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
		if( strFieldName.equals("YNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YSTUA") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YMPREM") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YMPREM1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YMPREM2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YMPREM3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YRNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YRID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YOWNER") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YPLAN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YSA") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YMODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YLPRE") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("YLPREA") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YMDAT") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
