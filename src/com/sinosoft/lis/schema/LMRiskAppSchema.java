/*
 * <p>ClassName: LMRiskAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 网点柜员数据
 * @CreateDate：2012-06-14
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMRiskAppDB;

public class LMRiskAppSchema implements Schema
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 主险编码 */
	private String MainRiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 主附险标记 */
	private String SubRiskFlag;
	/** 险种分类 */
	private String RiskType;
	/** 追加保费标记 */
	private String FirstAddPremFlag;
	/** 期交追加保费标记 */
	private String AddPremFlag;
	/** 投资账户标记 */
	private String AccountFlag;
	/** 产品分组 */
	private String GroupID;
	/** 重疾标志 */
	private String CCIFlag;
	/** 意外标志 */
	private String ADDFlag;
	/** 必带附加险标志 */
	private String SubRiskNecessary;
	/** 单产品累积标志 */
	private String PremADDFlag;
	/** 现价价值表 */
	private String CVTable;
	/** 产品费率表 */
	private String PATable;
	/** 计算标志 */
	private String CalFlag;
	/** 开办日期 */
	private Date StartDate;
	/** 停办日期 */
	private Date EndDate;
	/** 页面份数 */
	private double WebMult;
	/** 页面保费 */
	private double WebPrem;
	/** 页面保额 */
	private double WebAmnt;
	/** 页面保险年期类型 */
	private String WebInsuYearFlag;
	/** 页面保险年期 */
	private int WebInsuYear;
	/** 页面缴费年期类型 */
	private String WebPayEndYearFlag;
	/** 页面缴费年期 */
	private int WebPayEndYear;
	/** 页面缴费方式 */
	private String WebPayIntv;
	/** 页面投资账户比率 */
	private String WebAccRate;
	/** 页面投资账户编码 */
	private String WebAccCode;
	/** 页面投资账户投资日期 */
	private String WebAccTimeFlag;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 险种编码<P>险种编码 */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 险种编码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 主险编码<P> */
	public String getMainRiskCode()
	{
		if (MainRiskCode != null && !MainRiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainRiskCode = StrTool.unicodeToGBK(MainRiskCode);
		}
		return MainRiskCode;
	}
	/** 主险编码 */
	public void setMainRiskCode(String aMainRiskCode)
	{
		MainRiskCode = aMainRiskCode;
	}
	/** 险种版本<P>险种版本 */
	public String getRiskVer()
	{
		if (RiskVer != null && !RiskVer.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskVer = StrTool.unicodeToGBK(RiskVer);
		}
		return RiskVer;
	}
	/** 险种版本 */
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/** 险种名称<P>险种名称 */
	public String getRiskName()
	{
		if (RiskName != null && !RiskName.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskName = StrTool.unicodeToGBK(RiskName);
		}
		return RiskName;
	}
	/** 险种名称 */
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/** 主附险标记<P>主附险标记 M--主险(Main) S--附险(Sub) A--两者都可以。 */
	public String getSubRiskFlag()
	{
		if (SubRiskFlag != null && !SubRiskFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubRiskFlag = StrTool.unicodeToGBK(SubRiskFlag);
		}
		return SubRiskFlag;
	}
	/** 主附险标记 */
	public void setSubRiskFlag(String aSubRiskFlag)
	{
		SubRiskFlag = aSubRiskFlag;
	}
	/** 险种分类<P>1 -传统险2 -分红3- 投连4-万能 */
	public String getRiskType()
	{
		if (RiskType != null && !RiskType.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskType = StrTool.unicodeToGBK(RiskType);
		}
		return RiskType;
	}
	/** 险种分类 */
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	/** 追加保费标记<P>Y-可以有???期追加保费 N-无首期追加保费-暂不启用 */
	public String getFirstAddPremFlag()
	{
		if (FirstAddPremFlag != null && !FirstAddPremFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			FirstAddPremFlag = StrTool.unicodeToGBK(FirstAddPremFlag);
		}
		return FirstAddPremFlag;
	}
	/** 追加保费标记 */
	public void setFirstAddPremFlag(String aFirstAddPremFlag)
	{
		FirstAddPremFlag = aFirstAddPremFlag;
	}
	/** 期交追加保费标记<P> */
	public String getAddPremFlag()
	{
		if (AddPremFlag != null && !AddPremFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddPremFlag = StrTool.unicodeToGBK(AddPremFlag);
		}
		return AddPremFlag;
	}
	/** 期交追加保费标记 */
	public void setAddPremFlag(String aAddPremFlag)
	{
		AddPremFlag = aAddPremFlag;
	}
	/** 投资账户标记<P> */
	public String getAccountFlag()
	{
		if (AccountFlag != null && !AccountFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccountFlag = StrTool.unicodeToGBK(AccountFlag);
		}
		return AccountFlag;
	}
	/** 投资账户标记 */
	public void setAccountFlag(String aAccountFlag)
	{
		AccountFlag = aAccountFlag;
	}
	/** 产品分组<P> */
	public String getGroupID()
	{
		if (GroupID != null && !GroupID.equals("") && SysConst.CHANGECHARSET == true)
		{
			GroupID = StrTool.unicodeToGBK(GroupID);
		}
		return GroupID;
	}
	/** 产品分组 */
	public void setGroupID(String aGroupID)
	{
		GroupID = aGroupID;
	}
	/** 重疾标志<P> */
	public String getCCIFlag()
	{
		if (CCIFlag != null && !CCIFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CCIFlag = StrTool.unicodeToGBK(CCIFlag);
		}
		return CCIFlag;
	}
	/** 重疾标志 */
	public void setCCIFlag(String aCCIFlag)
	{
		CCIFlag = aCCIFlag;
	}
	/** 意外标志<P> */
	public String getADDFlag()
	{
		if (ADDFlag != null && !ADDFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ADDFlag = StrTool.unicodeToGBK(ADDFlag);
		}
		return ADDFlag;
	}
	/** 意外标志 */
	public void setADDFlag(String aADDFlag)
	{
		ADDFlag = aADDFlag;
	}
	/** 必带附加险标志<P> */
	public String getSubRiskNecessary()
	{
		if (SubRiskNecessary != null && !SubRiskNecessary.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubRiskNecessary = StrTool.unicodeToGBK(SubRiskNecessary);
		}
		return SubRiskNecessary;
	}
	/** 必带附加险标志 */
	public void setSubRiskNecessary(String aSubRiskNecessary)
	{
		SubRiskNecessary = aSubRiskNecessary;
	}
	/** 单产品累积标志<P> */
	public String getPremADDFlag()
	{
		if (PremADDFlag != null && !PremADDFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PremADDFlag = StrTool.unicodeToGBK(PremADDFlag);
		}
		return PremADDFlag;
	}
	/** 单产品累积标志 */
	public void setPremADDFlag(String aPremADDFlag)
	{
		PremADDFlag = aPremADDFlag;
	}
	/** 现价价值表<P> */
	public String getCVTable()
	{
		if (CVTable != null && !CVTable.equals("") && SysConst.CHANGECHARSET == true)
		{
			CVTable = StrTool.unicodeToGBK(CVTable);
		}
		return CVTable;
	}
	/** 现价价值表 */
	public void setCVTable(String aCVTable)
	{
		CVTable = aCVTable;
	}
	/** 产品费率表<P> */
	public String getPATable()
	{
		if (PATable != null && !PATable.equals("") && SysConst.CHANGECHARSET == true)
		{
			PATable = StrTool.unicodeToGBK(PATable);
		}
		return PATable;
	}
	/** 产品费率表 */
	public void setPATable(String aPATable)
	{
		PATable = aPATable;
	}
	/** 计算标志<P> */
	public String getCalFlag()
	{
		if (CalFlag != null && !CalFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalFlag = StrTool.unicodeToGBK(CalFlag);
		}
		return CalFlag;
	}
	/** 计算标志 */
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/** 开办日期<P>开办日期 */
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	/** 开办日期 */
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	/** 开办日期<P>开办日期 */
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	/** 停办日期<P> */
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	/** 停办日期 */
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	/** 停办日期<P> */
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/** 页面份数<P> */
	public double getWebMult()
	{
		return WebMult;
	}
	/** 页面份数 */
	public void setWebMult(double aWebMult)
	{
		WebMult = aWebMult;
	}
	/** 页面份数<P> */
	public void setWebMult(String aWebMult)
	{
		if (aWebMult != null && !aWebMult.equals(""))
		{
			Double tDouble = new Double(aWebMult);
			double d = tDouble.doubleValue();
			WebMult = d;
		}
	}

	/** 页面保费<P> */
	public double getWebPrem()
	{
		return WebPrem;
	}
	/** 页面保费 */
	public void setWebPrem(double aWebPrem)
	{
		WebPrem = aWebPrem;
	}
	/** 页面保费<P> */
	public void setWebPrem(String aWebPrem)
	{
		if (aWebPrem != null && !aWebPrem.equals(""))
		{
			Double tDouble = new Double(aWebPrem);
			double d = tDouble.doubleValue();
			WebPrem = d;
		}
	}

	/** 页面保额<P> */
	public double getWebAmnt()
	{
		return WebAmnt;
	}
	/** 页面保额 */
	public void setWebAmnt(double aWebAmnt)
	{
		WebAmnt = aWebAmnt;
	}
	/** 页面保额<P> */
	public void setWebAmnt(String aWebAmnt)
	{
		if (aWebAmnt != null && !aWebAmnt.equals(""))
		{
			Double tDouble = new Double(aWebAmnt);
			double d = tDouble.doubleValue();
			WebAmnt = d;
		}
	}

	/** 页面保险年期类型<P> */
	public String getWebInsuYearFlag()
	{
		if (WebInsuYearFlag != null && !WebInsuYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebInsuYearFlag = StrTool.unicodeToGBK(WebInsuYearFlag);
		}
		return WebInsuYearFlag;
	}
	/** 页面保险年期类型 */
	public void setWebInsuYearFlag(String aWebInsuYearFlag)
	{
		WebInsuYearFlag = aWebInsuYearFlag;
	}
	/** 页面保险年期<P> */
	public int getWebInsuYear()
	{
		return WebInsuYear;
	}
	/** 页面保险年期 */
	public void setWebInsuYear(int aWebInsuYear)
	{
		WebInsuYear = aWebInsuYear;
	}
	/** 页面保险年期<P> */
	public void setWebInsuYear(String aWebInsuYear)
	{
		if (aWebInsuYear != null && !aWebInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aWebInsuYear);
			int i = tInteger.intValue();
			WebInsuYear = i;
		}
	}

	/** 页面缴费年期类型<P> */
	public String getWebPayEndYearFlag()
	{
		if (WebPayEndYearFlag != null && !WebPayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebPayEndYearFlag = StrTool.unicodeToGBK(WebPayEndYearFlag);
		}
		return WebPayEndYearFlag;
	}
	/** 页面缴费年期类型 */
	public void setWebPayEndYearFlag(String aWebPayEndYearFlag)
	{
		WebPayEndYearFlag = aWebPayEndYearFlag;
	}
	/** 页面缴费年期<P> */
	public int getWebPayEndYear()
	{
		return WebPayEndYear;
	}
	/** 页面缴费年期 */
	public void setWebPayEndYear(int aWebPayEndYear)
	{
		WebPayEndYear = aWebPayEndYear;
	}
	/** 页面缴费年期<P> */
	public void setWebPayEndYear(String aWebPayEndYear)
	{
		if (aWebPayEndYear != null && !aWebPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aWebPayEndYear);
			int i = tInteger.intValue();
			WebPayEndYear = i;
		}
	}

	/** 页面缴费方式<P> */
	public String getWebPayIntv()
	{
		if (WebPayIntv != null && !WebPayIntv.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebPayIntv = StrTool.unicodeToGBK(WebPayIntv);
		}
		return WebPayIntv;
	}
	/** 页面缴费方式 */
	public void setWebPayIntv(String aWebPayIntv)
	{
		WebPayIntv = aWebPayIntv;
	}
	/** 页面投资账户比率<P> */
	public String getWebAccRate()
	{
		if (WebAccRate != null && !WebAccRate.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccRate = StrTool.unicodeToGBK(WebAccRate);
		}
		return WebAccRate;
	}
	/** 页面投资账户比率 */
	public void setWebAccRate(String aWebAccRate)
	{
		WebAccRate = aWebAccRate;
	}
	/** 页面投资账户编码<P> */
	public String getWebAccCode()
	{
		if (WebAccCode != null && !WebAccCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccCode = StrTool.unicodeToGBK(WebAccCode);
		}
		return WebAccCode;
	}
	/** 页面投资账户编码 */
	public void setWebAccCode(String aWebAccCode)
	{
		WebAccCode = aWebAccCode;
	}
	/** 页面投资账户投资日期<P> */
	public String getWebAccTimeFlag()
	{
		if (WebAccTimeFlag != null && !WebAccTimeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccTimeFlag = StrTool.unicodeToGBK(WebAccTimeFlag);
		}
		return WebAccTimeFlag;
	}
	/** 页面投资账户投资日期 */
	public void setWebAccTimeFlag(String aWebAccTimeFlag)
	{
		WebAccTimeFlag = aWebAccTimeFlag;
	}

	/**
	* 使用另外一个 LMRiskAppSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMRiskAppSchema aLMRiskAppSchema)
	{
		this.RiskCode = aLMRiskAppSchema.getRiskCode();
		this.MainRiskCode = aLMRiskAppSchema.getMainRiskCode();
		this.RiskVer = aLMRiskAppSchema.getRiskVer();
		this.RiskName = aLMRiskAppSchema.getRiskName();
		this.SubRiskFlag = aLMRiskAppSchema.getSubRiskFlag();
		this.RiskType = aLMRiskAppSchema.getRiskType();
		this.FirstAddPremFlag = aLMRiskAppSchema.getFirstAddPremFlag();
		this.AddPremFlag = aLMRiskAppSchema.getAddPremFlag();
		this.AccountFlag = aLMRiskAppSchema.getAccountFlag();
		this.GroupID = aLMRiskAppSchema.getGroupID();
		this.CCIFlag = aLMRiskAppSchema.getCCIFlag();
		this.ADDFlag = aLMRiskAppSchema.getADDFlag();
		this.SubRiskNecessary = aLMRiskAppSchema.getSubRiskNecessary();
		this.PremADDFlag = aLMRiskAppSchema.getPremADDFlag();
		this.CVTable = aLMRiskAppSchema.getCVTable();
		this.PATable = aLMRiskAppSchema.getPATable();
		this.CalFlag = aLMRiskAppSchema.getCalFlag();
		this.StartDate = fDate.getDate( aLMRiskAppSchema.getStartDate());
		this.EndDate = fDate.getDate( aLMRiskAppSchema.getEndDate());
		this.WebMult = aLMRiskAppSchema.getWebMult();
		this.WebPrem = aLMRiskAppSchema.getWebPrem();
		this.WebAmnt = aLMRiskAppSchema.getWebAmnt();
		this.WebInsuYearFlag = aLMRiskAppSchema.getWebInsuYearFlag();
		this.WebInsuYear = aLMRiskAppSchema.getWebInsuYear();
		this.WebPayEndYearFlag = aLMRiskAppSchema.getWebPayEndYearFlag();
		this.WebPayEndYear = aLMRiskAppSchema.getWebPayEndYear();
		this.WebPayIntv = aLMRiskAppSchema.getWebPayIntv();
		this.WebAccRate = aLMRiskAppSchema.getWebAccRate();
		this.WebAccCode = aLMRiskAppSchema.getWebAccCode();
		this.WebAccTimeFlag = aLMRiskAppSchema.getWebAccTimeFlag();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("MainRiskCode") == null )
				this.MainRiskCode = null;
			else
				this.MainRiskCode = rs.getString("MainRiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("SubRiskFlag") == null )
				this.SubRiskFlag = null;
			else
				this.SubRiskFlag = rs.getString("SubRiskFlag").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("FirstAddPremFlag") == null )
				this.FirstAddPremFlag = null;
			else
				this.FirstAddPremFlag = rs.getString("FirstAddPremFlag").trim();

			if( rs.getString("AddPremFlag") == null )
				this.AddPremFlag = null;
			else
				this.AddPremFlag = rs.getString("AddPremFlag").trim();

			if( rs.getString("AccountFlag") == null )
				this.AccountFlag = null;
			else
				this.AccountFlag = rs.getString("AccountFlag").trim();

			if( rs.getString("GroupID") == null )
				this.GroupID = null;
			else
				this.GroupID = rs.getString("GroupID").trim();

			if( rs.getString("CCIFlag") == null )
				this.CCIFlag = null;
			else
				this.CCIFlag = rs.getString("CCIFlag").trim();

			if( rs.getString("ADDFlag") == null )
				this.ADDFlag = null;
			else
				this.ADDFlag = rs.getString("ADDFlag").trim();

			if( rs.getString("SubRiskNecessary") == null )
				this.SubRiskNecessary = null;
			else
				this.SubRiskNecessary = rs.getString("SubRiskNecessary").trim();

			if( rs.getString("PremADDFlag") == null )
				this.PremADDFlag = null;
			else
				this.PremADDFlag = rs.getString("PremADDFlag").trim();

			if( rs.getString("CVTable") == null )
				this.CVTable = null;
			else
				this.CVTable = rs.getString("CVTable").trim();

			if( rs.getString("PATable") == null )
				this.PATable = null;
			else
				this.PATable = rs.getString("PATable").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.WebMult = rs.getDouble("WebMult");
			this.WebPrem = rs.getDouble("WebPrem");
			this.WebAmnt = rs.getDouble("WebAmnt");
			if( rs.getString("WebInsuYearFlag") == null )
				this.WebInsuYearFlag = null;
			else
				this.WebInsuYearFlag = rs.getString("WebInsuYearFlag").trim();

			this.WebInsuYear = rs.getInt("WebInsuYear");
			if( rs.getString("WebPayEndYearFlag") == null )
				this.WebPayEndYearFlag = null;
			else
				this.WebPayEndYearFlag = rs.getString("WebPayEndYearFlag").trim();

			this.WebPayEndYear = rs.getInt("WebPayEndYear");
			if( rs.getString("WebPayIntv") == null )
				this.WebPayIntv = null;
			else
				this.WebPayIntv = rs.getString("WebPayIntv").trim();

			if( rs.getString("WebAccRate") == null )
				this.WebAccRate = null;
			else
				this.WebAccRate = rs.getString("WebAccRate").trim();

			if( rs.getString("WebAccCode") == null )
				this.WebAccCode = null;
			else
				this.WebAccCode = rs.getString("WebAccCode").trim();

			if( rs.getString("WebAccTimeFlag") == null )
				this.WebAccTimeFlag = null;
			else
				this.WebAccTimeFlag = rs.getString("WebAccTimeFlag").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMRiskAppSchema getSchema()
	{
		LMRiskAppSchema aLMRiskAppSchema = new LMRiskAppSchema();
		aLMRiskAppSchema.setSchema(this);
		return aLMRiskAppSchema;
	}

	public LMRiskAppDB getDB()
	{
		LMRiskAppDB aDBOper = new LMRiskAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskApp描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainRiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskVer) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubRiskFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FirstAddPremFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddPremFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccountFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GroupID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CCIFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ADDFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubRiskNecessary) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PremADDFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CVTable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PATable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( EndDate )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebMult) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebAmnt) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebInsuYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebInsuYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebPayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebPayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebPayIntv) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccRate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccTimeFlag) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SubRiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FirstAddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccountFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GroupID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CCIFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ADDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SubRiskNecessary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PremADDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CVTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PATable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			WebMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			WebPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			WebAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			WebInsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			WebInsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			WebPayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			WebPayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			WebPayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			WebAccRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			WebAccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			WebAccTimeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppSchema";
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
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("MainRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskCode));
		}
		if (FCode.equals("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equals("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equals("SubRiskFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskFlag));
		}
		if (FCode.equals("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equals("FirstAddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAddPremFlag));
		}
		if (FCode.equals("AddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremFlag));
		}
		if (FCode.equals("AccountFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountFlag));
		}
		if (FCode.equals("GroupID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupID));
		}
		if (FCode.equals("CCIFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CCIFlag));
		}
		if (FCode.equals("ADDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ADDFlag));
		}
		if (FCode.equals("SubRiskNecessary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskNecessary));
		}
		if (FCode.equals("PremADDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremADDFlag));
		}
		if (FCode.equals("CVTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CVTable));
		}
		if (FCode.equals("PATable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PATable));
		}
		if (FCode.equals("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equals("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equals("WebMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebMult));
		}
		if (FCode.equals("WebPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPrem));
		}
		if (FCode.equals("WebAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAmnt));
		}
		if (FCode.equals("WebInsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebInsuYearFlag));
		}
		if (FCode.equals("WebInsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebInsuYear));
		}
		if (FCode.equals("WebPayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayEndYearFlag));
		}
		if (FCode.equals("WebPayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayEndYear));
		}
		if (FCode.equals("WebPayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayIntv));
		}
		if (FCode.equals("WebAccRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccRate));
		}
		if (FCode.equals("WebAccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccCode));
		}
		if (FCode.equals("WebAccTimeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccTimeFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SubRiskFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FirstAddPremFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AddPremFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccountFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GroupID);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CCIFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ADDFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SubRiskNecessary);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PremADDFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CVTable);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PATable);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 19:
				strFieldValue = String.valueOf(WebMult);
				break;
			case 20:
				strFieldValue = String.valueOf(WebPrem);
				break;
			case 21:
				strFieldValue = String.valueOf(WebAmnt);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(WebInsuYearFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(WebInsuYear);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(WebPayEndYearFlag);
				break;
			case 25:
				strFieldValue = String.valueOf(WebPayEndYear);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(WebPayIntv);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(WebAccRate);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(WebAccCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(WebAccTimeFlag);
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

		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equals("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equals("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equals("SubRiskFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskFlag = FValue.trim();
			}
			else
				SubRiskFlag = null;
		}
		if (FCode.equals("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equals("FirstAddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstAddPremFlag = FValue.trim();
			}
			else
				FirstAddPremFlag = null;
		}
		if (FCode.equals("AddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremFlag = FValue.trim();
			}
			else
				AddPremFlag = null;
		}
		if (FCode.equals("AccountFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountFlag = FValue.trim();
			}
			else
				AccountFlag = null;
		}
		if (FCode.equals("GroupID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupID = FValue.trim();
			}
			else
				GroupID = null;
		}
		if (FCode.equals("CCIFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CCIFlag = FValue.trim();
			}
			else
				CCIFlag = null;
		}
		if (FCode.equals("ADDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ADDFlag = FValue.trim();
			}
			else
				ADDFlag = null;
		}
		if (FCode.equals("SubRiskNecessary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskNecessary = FValue.trim();
			}
			else
				SubRiskNecessary = null;
		}
		if (FCode.equals("PremADDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremADDFlag = FValue.trim();
			}
			else
				PremADDFlag = null;
		}
		if (FCode.equals("CVTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CVTable = FValue.trim();
			}
			else
				CVTable = null;
		}
		if (FCode.equals("PATable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PATable = FValue.trim();
			}
			else
				PATable = null;
		}
		if (FCode.equals("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equals("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equals("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equals("WebMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebMult = d;
			}
		}
		if (FCode.equals("WebPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebPrem = d;
			}
		}
		if (FCode.equals("WebAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebAmnt = d;
			}
		}
		if (FCode.equals("WebInsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebInsuYearFlag = FValue.trim();
			}
			else
				WebInsuYearFlag = null;
		}
		if (FCode.equals("WebInsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WebInsuYear = i;
			}
		}
		if (FCode.equals("WebPayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebPayEndYearFlag = FValue.trim();
			}
			else
				WebPayEndYearFlag = null;
		}
		if (FCode.equals("WebPayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WebPayEndYear = i;
			}
		}
		if (FCode.equals("WebPayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebPayIntv = FValue.trim();
			}
			else
				WebPayIntv = null;
		}
		if (FCode.equals("WebAccRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccRate = FValue.trim();
			}
			else
				WebAccRate = null;
		}
		if (FCode.equals("WebAccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccCode = FValue.trim();
			}
			else
				WebAccCode = null;
		}
		if (FCode.equals("WebAccTimeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccTimeFlag = FValue.trim();
			}
			else
				WebAccTimeFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskAppSchema other = (LMRiskAppSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& MainRiskCode.equals(other.getMainRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& SubRiskFlag.equals(other.getSubRiskFlag())
			&& RiskType.equals(other.getRiskType())
			&& FirstAddPremFlag.equals(other.getFirstAddPremFlag())
			&& AddPremFlag.equals(other.getAddPremFlag())
			&& AccountFlag.equals(other.getAccountFlag())
			&& GroupID.equals(other.getGroupID())
			&& CCIFlag.equals(other.getCCIFlag())
			&& ADDFlag.equals(other.getADDFlag())
			&& SubRiskNecessary.equals(other.getSubRiskNecessary())
			&& PremADDFlag.equals(other.getPremADDFlag())
			&& CVTable.equals(other.getCVTable())
			&& PATable.equals(other.getPATable())
			&& CalFlag.equals(other.getCalFlag())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& WebMult == other.getWebMult()
			&& WebPrem == other.getWebPrem()
			&& WebAmnt == other.getWebAmnt()
			&& WebInsuYearFlag.equals(other.getWebInsuYearFlag())
			&& WebInsuYear == other.getWebInsuYear()
			&& WebPayEndYearFlag.equals(other.getWebPayEndYearFlag())
			&& WebPayEndYear == other.getWebPayEndYear()
			&& WebPayIntv.equals(other.getWebPayIntv())
			&& WebAccRate.equals(other.getWebAccRate())
			&& WebAccCode.equals(other.getWebAccCode())
			&& WebAccTimeFlag.equals(other.getWebAccTimeFlag());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 2;
		}
		if( strFieldName.equals("RiskName") ) {
			return 3;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return 4;
		}
		if( strFieldName.equals("RiskType") ) {
			return 5;
		}
		if( strFieldName.equals("FirstAddPremFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return 7;
		}
		if( strFieldName.equals("AccountFlag") ) {
			return 8;
		}
		if( strFieldName.equals("GroupID") ) {
			return 9;
		}
		if( strFieldName.equals("CCIFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ADDFlag") ) {
			return 11;
		}
		if( strFieldName.equals("SubRiskNecessary") ) {
			return 12;
		}
		if( strFieldName.equals("PremADDFlag") ) {
			return 13;
		}
		if( strFieldName.equals("CVTable") ) {
			return 14;
		}
		if( strFieldName.equals("PATable") ) {
			return 15;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 16;
		}
		if( strFieldName.equals("StartDate") ) {
			return 17;
		}
		if( strFieldName.equals("EndDate") ) {
			return 18;
		}
		if( strFieldName.equals("WebMult") ) {
			return 19;
		}
		if( strFieldName.equals("WebPrem") ) {
			return 20;
		}
		if( strFieldName.equals("WebAmnt") ) {
			return 21;
		}
		if( strFieldName.equals("WebInsuYearFlag") ) {
			return 22;
		}
		if( strFieldName.equals("WebInsuYear") ) {
			return 23;
		}
		if( strFieldName.equals("WebPayEndYearFlag") ) {
			return 24;
		}
		if( strFieldName.equals("WebPayEndYear") ) {
			return 25;
		}
		if( strFieldName.equals("WebPayIntv") ) {
			return 26;
		}
		if( strFieldName.equals("WebAccRate") ) {
			return 27;
		}
		if( strFieldName.equals("WebAccCode") ) {
			return 28;
		}
		if( strFieldName.equals("WebAccTimeFlag") ) {
			return 29;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "MainRiskCode";
				break;
			case 2:
				strFieldName = "RiskVer";
				break;
			case 3:
				strFieldName = "RiskName";
				break;
			case 4:
				strFieldName = "SubRiskFlag";
				break;
			case 5:
				strFieldName = "RiskType";
				break;
			case 6:
				strFieldName = "FirstAddPremFlag";
				break;
			case 7:
				strFieldName = "AddPremFlag";
				break;
			case 8:
				strFieldName = "AccountFlag";
				break;
			case 9:
				strFieldName = "GroupID";
				break;
			case 10:
				strFieldName = "CCIFlag";
				break;
			case 11:
				strFieldName = "ADDFlag";
				break;
			case 12:
				strFieldName = "SubRiskNecessary";
				break;
			case 13:
				strFieldName = "PremADDFlag";
				break;
			case 14:
				strFieldName = "CVTable";
				break;
			case 15:
				strFieldName = "PATable";
				break;
			case 16:
				strFieldName = "CalFlag";
				break;
			case 17:
				strFieldName = "StartDate";
				break;
			case 18:
				strFieldName = "EndDate";
				break;
			case 19:
				strFieldName = "WebMult";
				break;
			case 20:
				strFieldName = "WebPrem";
				break;
			case 21:
				strFieldName = "WebAmnt";
				break;
			case 22:
				strFieldName = "WebInsuYearFlag";
				break;
			case 23:
				strFieldName = "WebInsuYear";
				break;
			case 24:
				strFieldName = "WebPayEndYearFlag";
				break;
			case 25:
				strFieldName = "WebPayEndYear";
				break;
			case 26:
				strFieldName = "WebPayIntv";
				break;
			case 27:
				strFieldName = "WebAccRate";
				break;
			case 28:
				strFieldName = "WebAccCode";
				break;
			case 29:
				strFieldName = "WebAccTimeFlag";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstAddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GroupID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CCIFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ADDFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskNecessary") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremADDFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CVTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PATable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("WebMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebInsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebInsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WebPayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebPayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WebPayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccTimeFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
