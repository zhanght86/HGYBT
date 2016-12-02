/*
 * <p>ClassName: YBTNoRealPoliciesSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-20
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.YBTNoRealPoliciesDB;

public class YBTNoRealPoliciesSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
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
	private String YNAME;
	/** 被保险人身份证id */
	private String YID;
	/** 投保人姓名 */
	private String YOWNER;
	/** 险种代码 */
	private String YPLAN;
	/** 保额 */
	private String YSA;
	/** 缴费方式 */
	private String YMODE;
	/** 缴费期限 */
	private int YLPRE;
	/** 缴费类型
缴费类型 */
	private String YLPREA;
	/** 维护日期 */
	private Date YMDAT;
	/** 险种类型 */
	private String YFLAG;
	/** Processflag */
	private int PROCESSFLAG;
	/** Makedate */
	private Date MAKEDATE;
	/** Maketime */
	private String MAKETIME;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public YBTNoRealPoliciesSchema()
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
	public String getYNAME()
	{
		if (YNAME != null && !YNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			YNAME = StrTool.unicodeToGBK(YNAME);
		}
		return YNAME;
	}
	/** 被保险人姓名 */
	public void setYNAME(String aYNAME)
	{
		YNAME = aYNAME;
	}
	/** 被保险人身份证id<P> */
	public String getYID()
	{
		if (YID != null && !YID.equals("") && SysConst.CHANGECHARSET == true)
		{
			YID = StrTool.unicodeToGBK(YID);
		}
		return YID;
	}
	/** 被保险人身份证id */
	public void setYID(String aYID)
	{
		YID = aYID;
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
	public String getYSA()
	{
		if (YSA != null && !YSA.equals("") && SysConst.CHANGECHARSET == true)
		{
			YSA = StrTool.unicodeToGBK(YSA);
		}
		return YSA;
	}
	/** 保额 */
	public void setYSA(String aYSA)
	{
		YSA = aYSA;
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
缴费类型<P>缴费类型<br>0-期间<br>1-缴费到几岁  */
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

	/** 险种类型<P> */
	public String getYFLAG()
	{
		if (YFLAG != null && !YFLAG.equals("") && SysConst.CHANGECHARSET == true)
		{
			YFLAG = StrTool.unicodeToGBK(YFLAG);
		}
		return YFLAG;
	}
	/** 险种类型 */
	public void setYFLAG(String aYFLAG)
	{
		YFLAG = aYFLAG;
	}
	/** Processflag<P> */
	public int getPROCESSFLAG()
	{
		return PROCESSFLAG;
	}
	/** Processflag */
	public void setPROCESSFLAG(int aPROCESSFLAG)
	{
		PROCESSFLAG = aPROCESSFLAG;
	}
	/** Processflag<P> */
	public void setPROCESSFLAG(String aPROCESSFLAG)
	{
		if (aPROCESSFLAG != null && !aPROCESSFLAG.equals(""))
		{
			Integer tInteger = new Integer(aPROCESSFLAG);
			int i = tInteger.intValue();
			PROCESSFLAG = i;
		}
	}

	/** Makedate<P> */
	public String getMAKEDATE()
	{
		if( MAKEDATE != null )
			return fDate.getString(MAKEDATE);
		else
			return null;
	}
	/** Makedate */
	public void setMAKEDATE(Date aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	/** Makedate<P> */
	public void setMAKEDATE(String aMAKEDATE)
	{
		if (aMAKEDATE != null && !aMAKEDATE.equals("") )
		{
			MAKEDATE = fDate.getDate( aMAKEDATE );
		}
		else
			MAKEDATE = null;
	}

	/** Maketime<P> */
	public String getMAKETIME()
	{
		if (MAKETIME != null && !MAKETIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			MAKETIME = StrTool.unicodeToGBK(MAKETIME);
		}
		return MAKETIME;
	}
	/** Maketime */
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
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
	* 使用另外一个 YBTNoRealPoliciesSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(YBTNoRealPoliciesSchema aYBTNoRealPoliciesSchema)
	{
		this.SerialNo = aYBTNoRealPoliciesSchema.getSerialNo();
		this.YNO = aYBTNoRealPoliciesSchema.getYNO();
		this.YSTUA = aYBTNoRealPoliciesSchema.getYSTUA();
		this.YMPREM = aYBTNoRealPoliciesSchema.getYMPREM();
		this.YMPREM1 = aYBTNoRealPoliciesSchema.getYMPREM1();
		this.YMPREM2 = aYBTNoRealPoliciesSchema.getYMPREM2();
		this.YMPREM3 = aYBTNoRealPoliciesSchema.getYMPREM3();
		this.YNAME = aYBTNoRealPoliciesSchema.getYNAME();
		this.YID = aYBTNoRealPoliciesSchema.getYID();
		this.YOWNER = aYBTNoRealPoliciesSchema.getYOWNER();
		this.YPLAN = aYBTNoRealPoliciesSchema.getYPLAN();
		this.YSA = aYBTNoRealPoliciesSchema.getYSA();
		this.YMODE = aYBTNoRealPoliciesSchema.getYMODE();
		this.YLPRE = aYBTNoRealPoliciesSchema.getYLPRE();
		this.YLPREA = aYBTNoRealPoliciesSchema.getYLPREA();
		this.YMDAT = fDate.getDate( aYBTNoRealPoliciesSchema.getYMDAT());
		this.YFLAG = aYBTNoRealPoliciesSchema.getYFLAG();
		this.PROCESSFLAG = aYBTNoRealPoliciesSchema.getPROCESSFLAG();
		this.MAKEDATE = fDate.getDate( aYBTNoRealPoliciesSchema.getMAKEDATE());
		this.MAKETIME = aYBTNoRealPoliciesSchema.getMAKETIME();
		this.ModifyDate = fDate.getDate( aYBTNoRealPoliciesSchema.getModifyDate());
		this.ModifyTime = aYBTNoRealPoliciesSchema.getModifyTime();
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
			if( rs.getString("YNAME") == null )
				this.YNAME = null;
			else
				this.YNAME = rs.getString("YNAME").trim();

			if( rs.getString("YID") == null )
				this.YID = null;
			else
				this.YID = rs.getString("YID").trim();

			if( rs.getString("YOWNER") == null )
				this.YOWNER = null;
			else
				this.YOWNER = rs.getString("YOWNER").trim();

			if( rs.getString("YPLAN") == null )
				this.YPLAN = null;
			else
				this.YPLAN = rs.getString("YPLAN").trim();

			if( rs.getString("YSA") == null )
				this.YSA = null;
			else
				this.YSA = rs.getString("YSA").trim();

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
			if( rs.getString("YFLAG") == null )
				this.YFLAG = null;
			else
				this.YFLAG = rs.getString("YFLAG").trim();

			this.PROCESSFLAG = rs.getInt("PROCESSFLAG");
			this.MAKEDATE = rs.getDate("MAKEDATE");
			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

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
			tError.moduleName = "YBTNoRealPoliciesSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public YBTNoRealPoliciesSchema getSchema()
	{
		YBTNoRealPoliciesSchema aYBTNoRealPoliciesSchema = new YBTNoRealPoliciesSchema();
		aYBTNoRealPoliciesSchema.setSchema(this);
		return aYBTNoRealPoliciesSchema;
	}

	public YBTNoRealPoliciesDB getDB()
	{
		YBTNoRealPoliciesDB aDBOper = new YBTNoRealPoliciesDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTNoRealPolicies描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YSTUA) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM1) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM2) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YMPREM3) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YOWNER) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YPLAN) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YSA) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YMODE) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(YLPRE) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YLPREA) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( YMDAT )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(YFLAG) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PROCESSFLAG) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MAKEDATE )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MAKETIME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTNoRealPolicies>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			YNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			YSTUA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			YMPREM = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			YMPREM3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			YNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			YID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			YOWNER = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			YPLAN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			YSA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			YMODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			YLPRE= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			YLPREA = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			YMDAT = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			YFLAG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PROCESSFLAG= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTNoRealPoliciesSchema";
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
		if (FCode.equals("YNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YNAME));
		}
		if (FCode.equals("YID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YID));
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
		if (FCode.equals("YFLAG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YFLAG));
		}
		if (FCode.equals("PROCESSFLAG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PROCESSFLAG));
		}
		if (FCode.equals("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
		}
		if (FCode.equals("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
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
				strFieldValue = StrTool.GBKToUnicode(YNO);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(YSTUA);
				break;
			case 3:
				strFieldValue = String.valueOf(YMPREM);
				break;
			case 4:
				strFieldValue = String.valueOf(YMPREM1);
				break;
			case 5:
				strFieldValue = String.valueOf(YMPREM2);
				break;
			case 6:
				strFieldValue = String.valueOf(YMPREM3);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(YNAME);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(YID);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(YOWNER);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(YPLAN);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(YSA);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(YMODE);
				break;
			case 13:
				strFieldValue = String.valueOf(YLPRE);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(YLPREA);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getYMDAT()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(YFLAG);
				break;
			case 17:
				strFieldValue = String.valueOf(PROCESSFLAG);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
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
		if (FCode.equals("YNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YNAME = FValue.trim();
			}
			else
				YNAME = null;
		}
		if (FCode.equals("YID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YID = FValue.trim();
			}
			else
				YID = null;
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
				YSA = FValue.trim();
			}
			else
				YSA = null;
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
		if (FCode.equals("YFLAG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YFLAG = FValue.trim();
			}
			else
				YFLAG = null;
		}
		if (FCode.equals("PROCESSFLAG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PROCESSFLAG = i;
			}
		}
		if (FCode.equals("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MAKEDATE = fDate.getDate( FValue );
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equals("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
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
		YBTNoRealPoliciesSchema other = (YBTNoRealPoliciesSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& YNO.equals(other.getYNO())
			&& YSTUA.equals(other.getYSTUA())
			&& YMPREM == other.getYMPREM()
			&& YMPREM1 == other.getYMPREM1()
			&& YMPREM2 == other.getYMPREM2()
			&& YMPREM3 == other.getYMPREM3()
			&& YNAME.equals(other.getYNAME())
			&& YID.equals(other.getYID())
			&& YOWNER.equals(other.getYOWNER())
			&& YPLAN.equals(other.getYPLAN())
			&& YSA.equals(other.getYSA())
			&& YMODE.equals(other.getYMODE())
			&& YLPRE == other.getYLPRE()
			&& YLPREA.equals(other.getYLPREA())
			&& fDate.getString(YMDAT).equals(other.getYMDAT())
			&& YFLAG.equals(other.getYFLAG())
			&& PROCESSFLAG == other.getPROCESSFLAG()
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
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
		if( strFieldName.equals("YNO") ) {
			return 1;
		}
		if( strFieldName.equals("YSTUA") ) {
			return 2;
		}
		if( strFieldName.equals("YMPREM") ) {
			return 3;
		}
		if( strFieldName.equals("YMPREM1") ) {
			return 4;
		}
		if( strFieldName.equals("YMPREM2") ) {
			return 5;
		}
		if( strFieldName.equals("YMPREM3") ) {
			return 6;
		}
		if( strFieldName.equals("YNAME") ) {
			return 7;
		}
		if( strFieldName.equals("YID") ) {
			return 8;
		}
		if( strFieldName.equals("YOWNER") ) {
			return 9;
		}
		if( strFieldName.equals("YPLAN") ) {
			return 10;
		}
		if( strFieldName.equals("YSA") ) {
			return 11;
		}
		if( strFieldName.equals("YMODE") ) {
			return 12;
		}
		if( strFieldName.equals("YLPRE") ) {
			return 13;
		}
		if( strFieldName.equals("YLPREA") ) {
			return 14;
		}
		if( strFieldName.equals("YMDAT") ) {
			return 15;
		}
		if( strFieldName.equals("YFLAG") ) {
			return 16;
		}
		if( strFieldName.equals("PROCESSFLAG") ) {
			return 17;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 18;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
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
				strFieldName = "YNO";
				break;
			case 2:
				strFieldName = "YSTUA";
				break;
			case 3:
				strFieldName = "YMPREM";
				break;
			case 4:
				strFieldName = "YMPREM1";
				break;
			case 5:
				strFieldName = "YMPREM2";
				break;
			case 6:
				strFieldName = "YMPREM3";
				break;
			case 7:
				strFieldName = "YNAME";
				break;
			case 8:
				strFieldName = "YID";
				break;
			case 9:
				strFieldName = "YOWNER";
				break;
			case 10:
				strFieldName = "YPLAN";
				break;
			case 11:
				strFieldName = "YSA";
				break;
			case 12:
				strFieldName = "YMODE";
				break;
			case 13:
				strFieldName = "YLPRE";
				break;
			case 14:
				strFieldName = "YLPREA";
				break;
			case 15:
				strFieldName = "YMDAT";
				break;
			case 16:
				strFieldName = "YFLAG";
				break;
			case 17:
				strFieldName = "PROCESSFLAG";
				break;
			case 18:
				strFieldName = "MAKEDATE";
				break;
			case 19:
				strFieldName = "MAKETIME";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
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
		if( strFieldName.equals("YNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YOWNER") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YPLAN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YSA") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("YFLAG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PROCESSFLAG") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAKETIME") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
