/*
 * <p>ClassName: AXAFundTransactionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛花旗
 * @CreateDate：2012-03-31
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.AXAFundTransactionDB;

public class AXAFundTransactionSchema implements Schema
{
	// @Field
	/** 流水号 */
	private int SerialNo;
	/** 提取日期 */
	private int ExtractedDate;
	/** 保单号 */
	private String PolicyNo;
	/** 投资账户代码 */
	private String FundCode;
	/** 投资账户英文名 */
	private String FundEnglishName;
	/** 投资账户中文名 */
	private String FundChineseName;
	/** 交易日期 */
	private int TransactionDate;
	/** 交易类型 */
	private String TransactionType;
	/** 交易代码 */
	private String TransactionCode;
	/** 映射类型 */
	private String TransactionType_oth;
	/** 映射代码 */
	private String TransactionCode_oth;
	/** 交易描述 */
	private String TransactionDesc;
	/** 交易描述b */
	private String TransactionDesc_oth;
	/** 交易份数 */
	private double TransactionNo;
	/** 投资总额 */
	private double InvestmentAmount;
	/** 单位价格 */
	private double CurrentUnitPrice;
	/** 卖出价格 */
	private double OfferPrice;
	/** 计价日期 */
	private int ValuationDate;
	/** Column_16 */
	private double TransactionCharge;
	/** Column_17 */
	private double TransactionAmount;
	/** 生成日期 */
	private int MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 处理日期 */
	private int DealDate;
	/** 处理时间 */
	private String DealTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AXAFundTransactionSchema()
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
	public int getSerialNo()
	{
		return SerialNo;
	}
	/** 流水号 */
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/** 流水号<P> */
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	/** 提取日期<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** 提取日期 */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** 提取日期<P> */
	public void setExtractedDate(String aExtractedDate)
	{
		if (aExtractedDate != null && !aExtractedDate.equals(""))
		{
			Integer tInteger = new Integer(aExtractedDate);
			int i = tInteger.intValue();
			ExtractedDate = i;
		}
	}

	/** 保单号<P>产品代码 */
	public String getPolicyNo()
	{
		if (PolicyNo != null && !PolicyNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyNo = StrTool.unicodeToGBK(PolicyNo);
		}
		return PolicyNo;
	}
	/** 保单号 */
	public void setPolicyNo(String aPolicyNo)
	{
		PolicyNo = aPolicyNo;
	}
	/** 投资账户代码<P> */
	public String getFundCode()
	{
		if (FundCode != null && !FundCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundCode = StrTool.unicodeToGBK(FundCode);
		}
		return FundCode;
	}
	/** 投资账户代码 */
	public void setFundCode(String aFundCode)
	{
		FundCode = aFundCode;
	}
	/** 投资账户英文名<P> */
	public String getFundEnglishName()
	{
		if (FundEnglishName != null && !FundEnglishName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundEnglishName = StrTool.unicodeToGBK(FundEnglishName);
		}
		return FundEnglishName;
	}
	/** 投资账户英文名 */
	public void setFundEnglishName(String aFundEnglishName)
	{
		FundEnglishName = aFundEnglishName;
	}
	/** 投资账户中文名<P> */
	public String getFundChineseName()
	{
		if (FundChineseName != null && !FundChineseName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundChineseName = StrTool.unicodeToGBK(FundChineseName);
		}
		return FundChineseName;
	}
	/** 投资账户中文名 */
	public void setFundChineseName(String aFundChineseName)
	{
		FundChineseName = aFundChineseName;
	}
	/** 交易日期<P> */
	public int getTransactionDate()
	{
		return TransactionDate;
	}
	/** 交易日期 */
	public void setTransactionDate(int aTransactionDate)
	{
		TransactionDate = aTransactionDate;
	}
	/** 交易日期<P> */
	public void setTransactionDate(String aTransactionDate)
	{
		if (aTransactionDate != null && !aTransactionDate.equals(""))
		{
			Integer tInteger = new Integer(aTransactionDate);
			int i = tInteger.intValue();
			TransactionDate = i;
		}
	}

	/** 交易类型<P> */
	public String getTransactionType()
	{
		if (TransactionType != null && !TransactionType.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionType = StrTool.unicodeToGBK(TransactionType);
		}
		return TransactionType;
	}
	/** 交易类型 */
	public void setTransactionType(String aTransactionType)
	{
		TransactionType = aTransactionType;
	}
	/** 交易代码<P> */
	public String getTransactionCode()
	{
		if (TransactionCode != null && !TransactionCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionCode = StrTool.unicodeToGBK(TransactionCode);
		}
		return TransactionCode;
	}
	/** 交易代码 */
	public void setTransactionCode(String aTransactionCode)
	{
		TransactionCode = aTransactionCode;
	}
	/** 映射类型<P> */
	public String getTransactionType_oth()
	{
		if (TransactionType_oth != null && !TransactionType_oth.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionType_oth = StrTool.unicodeToGBK(TransactionType_oth);
		}
		return TransactionType_oth;
	}
	/** 映射类型 */
	public void setTransactionType_oth(String aTransactionType_oth)
	{
		TransactionType_oth = aTransactionType_oth;
	}
	/** 映射代码<P> */
	public String getTransactionCode_oth()
	{
		if (TransactionCode_oth != null && !TransactionCode_oth.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionCode_oth = StrTool.unicodeToGBK(TransactionCode_oth);
		}
		return TransactionCode_oth;
	}
	/** 映射代码 */
	public void setTransactionCode_oth(String aTransactionCode_oth)
	{
		TransactionCode_oth = aTransactionCode_oth;
	}
	/** 交易描述<P> */
	public String getTransactionDesc()
	{
		if (TransactionDesc != null && !TransactionDesc.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionDesc = StrTool.unicodeToGBK(TransactionDesc);
		}
		return TransactionDesc;
	}
	/** 交易描述 */
	public void setTransactionDesc(String aTransactionDesc)
	{
		TransactionDesc = aTransactionDesc;
	}
	/** 交易描述b<P> */
	public String getTransactionDesc_oth()
	{
		if (TransactionDesc_oth != null && !TransactionDesc_oth.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionDesc_oth = StrTool.unicodeToGBK(TransactionDesc_oth);
		}
		return TransactionDesc_oth;
	}
	/** 交易描述b */
	public void setTransactionDesc_oth(String aTransactionDesc_oth)
	{
		TransactionDesc_oth = aTransactionDesc_oth;
	}
	/** 交易份数<P> */
	public double getTransactionNo()
	{
		return TransactionNo;
	}
	/** 交易份数 */
	public void setTransactionNo(double aTransactionNo)
	{
		TransactionNo = aTransactionNo;
	}
	/** 交易份数<P> */
	public void setTransactionNo(String aTransactionNo)
	{
		if (aTransactionNo != null && !aTransactionNo.equals(""))
		{
			Double tDouble = new Double(aTransactionNo);
			double d = tDouble.doubleValue();
			TransactionNo = d;
		}
	}

	/** 投资总额<P> */
	public double getInvestmentAmount()
	{
		return InvestmentAmount;
	}
	/** 投资总额 */
	public void setInvestmentAmount(double aInvestmentAmount)
	{
		InvestmentAmount = aInvestmentAmount;
	}
	/** 投资总额<P> */
	public void setInvestmentAmount(String aInvestmentAmount)
	{
		if (aInvestmentAmount != null && !aInvestmentAmount.equals(""))
		{
			Double tDouble = new Double(aInvestmentAmount);
			double d = tDouble.doubleValue();
			InvestmentAmount = d;
		}
	}

	/** 单位价格<P> */
	public double getCurrentUnitPrice()
	{
		return CurrentUnitPrice;
	}
	/** 单位价格 */
	public void setCurrentUnitPrice(double aCurrentUnitPrice)
	{
		CurrentUnitPrice = aCurrentUnitPrice;
	}
	/** 单位价格<P> */
	public void setCurrentUnitPrice(String aCurrentUnitPrice)
	{
		if (aCurrentUnitPrice != null && !aCurrentUnitPrice.equals(""))
		{
			Double tDouble = new Double(aCurrentUnitPrice);
			double d = tDouble.doubleValue();
			CurrentUnitPrice = d;
		}
	}

	/** 卖出价格<P> */
	public double getOfferPrice()
	{
		return OfferPrice;
	}
	/** 卖出价格 */
	public void setOfferPrice(double aOfferPrice)
	{
		OfferPrice = aOfferPrice;
	}
	/** 卖出价格<P> */
	public void setOfferPrice(String aOfferPrice)
	{
		if (aOfferPrice != null && !aOfferPrice.equals(""))
		{
			Double tDouble = new Double(aOfferPrice);
			double d = tDouble.doubleValue();
			OfferPrice = d;
		}
	}

	/** 计价日期<P> */
	public int getValuationDate()
	{
		return ValuationDate;
	}
	/** 计价日期 */
	public void setValuationDate(int aValuationDate)
	{
		ValuationDate = aValuationDate;
	}
	/** 计价日期<P> */
	public void setValuationDate(String aValuationDate)
	{
		if (aValuationDate != null && !aValuationDate.equals(""))
		{
			Integer tInteger = new Integer(aValuationDate);
			int i = tInteger.intValue();
			ValuationDate = i;
		}
	}

	/** Column_16<P> */
	public double getTransactionCharge()
	{
		return TransactionCharge;
	}
	/** Column_16 */
	public void setTransactionCharge(double aTransactionCharge)
	{
		TransactionCharge = aTransactionCharge;
	}
	/** Column_16<P> */
	public void setTransactionCharge(String aTransactionCharge)
	{
		if (aTransactionCharge != null && !aTransactionCharge.equals(""))
		{
			Double tDouble = new Double(aTransactionCharge);
			double d = tDouble.doubleValue();
			TransactionCharge = d;
		}
	}

	/** Column_17<P> */
	public double getTransactionAmount()
	{
		return TransactionAmount;
	}
	/** Column_17 */
	public void setTransactionAmount(double aTransactionAmount)
	{
		TransactionAmount = aTransactionAmount;
	}
	/** Column_17<P> */
	public void setTransactionAmount(String aTransactionAmount)
	{
		if (aTransactionAmount != null && !aTransactionAmount.equals(""))
		{
			Double tDouble = new Double(aTransactionAmount);
			double d = tDouble.doubleValue();
			TransactionAmount = d;
		}
	}

	/** 生成日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 生成日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 生成日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** 生成时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 生成时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 处理日期<P> */
	public int getDealDate()
	{
		return DealDate;
	}
	/** 处理日期 */
	public void setDealDate(int aDealDate)
	{
		DealDate = aDealDate;
	}
	/** 处理日期<P> */
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals(""))
		{
			Integer tInteger = new Integer(aDealDate);
			int i = tInteger.intValue();
			DealDate = i;
		}
	}

	/** 处理时间<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** 处理时间 */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* 使用另外一个 AXAFundTransactionSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AXAFundTransactionSchema aAXAFundTransactionSchema)
	{
		this.SerialNo = aAXAFundTransactionSchema.getSerialNo();
		this.ExtractedDate = aAXAFundTransactionSchema.getExtractedDate();
		this.PolicyNo = aAXAFundTransactionSchema.getPolicyNo();
		this.FundCode = aAXAFundTransactionSchema.getFundCode();
		this.FundEnglishName = aAXAFundTransactionSchema.getFundEnglishName();
		this.FundChineseName = aAXAFundTransactionSchema.getFundChineseName();
		this.TransactionDate = aAXAFundTransactionSchema.getTransactionDate();
		this.TransactionType = aAXAFundTransactionSchema.getTransactionType();
		this.TransactionCode = aAXAFundTransactionSchema.getTransactionCode();
		this.TransactionType_oth = aAXAFundTransactionSchema.getTransactionType_oth();
		this.TransactionCode_oth = aAXAFundTransactionSchema.getTransactionCode_oth();
		this.TransactionDesc = aAXAFundTransactionSchema.getTransactionDesc();
		this.TransactionDesc_oth = aAXAFundTransactionSchema.getTransactionDesc_oth();
		this.TransactionNo = aAXAFundTransactionSchema.getTransactionNo();
		this.InvestmentAmount = aAXAFundTransactionSchema.getInvestmentAmount();
		this.CurrentUnitPrice = aAXAFundTransactionSchema.getCurrentUnitPrice();
		this.OfferPrice = aAXAFundTransactionSchema.getOfferPrice();
		this.ValuationDate = aAXAFundTransactionSchema.getValuationDate();
		this.TransactionCharge = aAXAFundTransactionSchema.getTransactionCharge();
		this.TransactionAmount = aAXAFundTransactionSchema.getTransactionAmount();
		this.MakeDate = aAXAFundTransactionSchema.getMakeDate();
		this.MakeTime = aAXAFundTransactionSchema.getMakeTime();
		this.DealDate = aAXAFundTransactionSchema.getDealDate();
		this.DealTime = aAXAFundTransactionSchema.getDealTime();
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
			this.SerialNo = rs.getInt("SerialNo");
			this.ExtractedDate = rs.getInt("ExtractedDate");
			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("FundCode") == null )
				this.FundCode = null;
			else
				this.FundCode = rs.getString("FundCode").trim();

			if( rs.getString("FundEnglishName") == null )
				this.FundEnglishName = null;
			else
				this.FundEnglishName = rs.getString("FundEnglishName").trim();

			if( rs.getString("FundChineseName") == null )
				this.FundChineseName = null;
			else
				this.FundChineseName = rs.getString("FundChineseName").trim();

			this.TransactionDate = rs.getInt("TransactionDate");
			if( rs.getString("TransactionType") == null )
				this.TransactionType = null;
			else
				this.TransactionType = rs.getString("TransactionType").trim();

			if( rs.getString("TransactionCode") == null )
				this.TransactionCode = null;
			else
				this.TransactionCode = rs.getString("TransactionCode").trim();

			if( rs.getString("TransactionType_oth") == null )
				this.TransactionType_oth = null;
			else
				this.TransactionType_oth = rs.getString("TransactionType_oth").trim();

			if( rs.getString("TransactionCode_oth") == null )
				this.TransactionCode_oth = null;
			else
				this.TransactionCode_oth = rs.getString("TransactionCode_oth").trim();

			if( rs.getString("TransactionDesc") == null )
				this.TransactionDesc = null;
			else
				this.TransactionDesc = rs.getString("TransactionDesc").trim();

			if( rs.getString("TransactionDesc_oth") == null )
				this.TransactionDesc_oth = null;
			else
				this.TransactionDesc_oth = rs.getString("TransactionDesc_oth").trim();

			this.TransactionNo = rs.getDouble("TransactionNo");
			this.InvestmentAmount = rs.getDouble("InvestmentAmount");
			this.CurrentUnitPrice = rs.getDouble("CurrentUnitPrice");
			this.OfferPrice = rs.getDouble("OfferPrice");
			this.ValuationDate = rs.getInt("ValuationDate");
			this.TransactionCharge = rs.getDouble("TransactionCharge");
			this.TransactionAmount = rs.getDouble("TransactionAmount");
			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.DealDate = rs.getInt("DealDate");
			if( rs.getString("DealTime") == null )
				this.DealTime = null;
			else
				this.DealTime = rs.getString("DealTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAFundTransactionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AXAFundTransactionSchema getSchema()
	{
		AXAFundTransactionSchema aAXAFundTransactionSchema = new AXAFundTransactionSchema();
		aAXAFundTransactionSchema.setSchema(this);
		return aAXAFundTransactionSchema;
	}

	public AXAFundTransactionDB getDB()
	{
		AXAFundTransactionDB aDBOper = new AXAFundTransactionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAFundTransaction描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(SerialNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundEnglishName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundChineseName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionType_oth) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionCode_oth) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionDesc) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionDesc_oth) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InvestmentAmount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CurrentUnitPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(OfferPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ValuationDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionCharge) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionAmount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(DealDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAFundTransaction>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FundCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FundEnglishName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FundChineseName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TransactionDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			TransactionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TransactionCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TransactionType_oth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TransactionCode_oth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TransactionDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			TransactionDesc_oth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			TransactionNo = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			InvestmentAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			CurrentUnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			OfferPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			ValuationDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			TransactionCharge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			TransactionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAFundTransactionSchema";
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
		if (FCode.equals("ExtractedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtractedDate));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("FundCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCode));
		}
		if (FCode.equals("FundEnglishName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundEnglishName));
		}
		if (FCode.equals("FundChineseName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundChineseName));
		}
		if (FCode.equals("TransactionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDate));
		}
		if (FCode.equals("TransactionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionType));
		}
		if (FCode.equals("TransactionCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionCode));
		}
		if (FCode.equals("TransactionType_oth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionType_oth));
		}
		if (FCode.equals("TransactionCode_oth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionCode_oth));
		}
		if (FCode.equals("TransactionDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDesc));
		}
		if (FCode.equals("TransactionDesc_oth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDesc_oth));
		}
		if (FCode.equals("TransactionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionNo));
		}
		if (FCode.equals("InvestmentAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestmentAmount));
		}
		if (FCode.equals("CurrentUnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrentUnitPrice));
		}
		if (FCode.equals("OfferPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferPrice));
		}
		if (FCode.equals("ValuationDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValuationDate));
		}
		if (FCode.equals("TransactionCharge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionCharge));
		}
		if (FCode.equals("TransactionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionAmount));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("DealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealDate));
		}
		if (FCode.equals("DealTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealTime));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = String.valueOf(ExtractedDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FundCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FundEnglishName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FundChineseName);
				break;
			case 6:
				strFieldValue = String.valueOf(TransactionDate);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TransactionType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TransactionCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(TransactionType_oth);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(TransactionCode_oth);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(TransactionDesc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TransactionDesc_oth);
				break;
			case 13:
				strFieldValue = String.valueOf(TransactionNo);
				break;
			case 14:
				strFieldValue = String.valueOf(InvestmentAmount);
				break;
			case 15:
				strFieldValue = String.valueOf(CurrentUnitPrice);
				break;
			case 16:
				strFieldValue = String.valueOf(OfferPrice);
				break;
			case 17:
				strFieldValue = String.valueOf(ValuationDate);
				break;
			case 18:
				strFieldValue = String.valueOf(TransactionCharge);
				break;
			case 19:
				strFieldValue = String.valueOf(TransactionAmount);
				break;
			case 20:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = String.valueOf(DealDate);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(DealTime);
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equals("ExtractedDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExtractedDate = i;
			}
		}
		if (FCode.equals("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equals("FundCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundCode = FValue.trim();
			}
			else
				FundCode = null;
		}
		if (FCode.equals("FundEnglishName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundEnglishName = FValue.trim();
			}
			else
				FundEnglishName = null;
		}
		if (FCode.equals("FundChineseName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundChineseName = FValue.trim();
			}
			else
				FundChineseName = null;
		}
		if (FCode.equals("TransactionDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TransactionDate = i;
			}
		}
		if (FCode.equals("TransactionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionType = FValue.trim();
			}
			else
				TransactionType = null;
		}
		if (FCode.equals("TransactionCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionCode = FValue.trim();
			}
			else
				TransactionCode = null;
		}
		if (FCode.equals("TransactionType_oth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionType_oth = FValue.trim();
			}
			else
				TransactionType_oth = null;
		}
		if (FCode.equals("TransactionCode_oth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionCode_oth = FValue.trim();
			}
			else
				TransactionCode_oth = null;
		}
		if (FCode.equals("TransactionDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionDesc = FValue.trim();
			}
			else
				TransactionDesc = null;
		}
		if (FCode.equals("TransactionDesc_oth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionDesc_oth = FValue.trim();
			}
			else
				TransactionDesc_oth = null;
		}
		if (FCode.equals("TransactionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransactionNo = d;
			}
		}
		if (FCode.equals("InvestmentAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestmentAmount = d;
			}
		}
		if (FCode.equals("CurrentUnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurrentUnitPrice = d;
			}
		}
		if (FCode.equals("OfferPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OfferPrice = d;
			}
		}
		if (FCode.equals("ValuationDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ValuationDate = i;
			}
		}
		if (FCode.equals("TransactionCharge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransactionCharge = d;
			}
		}
		if (FCode.equals("TransactionAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransactionAmount = d;
			}
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MakeDate = i;
			}
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
		if (FCode.equals("DealDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DealDate = i;
			}
		}
		if (FCode.equals("DealTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealTime = FValue.trim();
			}
			else
				DealTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AXAFundTransactionSchema other = (AXAFundTransactionSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& FundCode.equals(other.getFundCode())
			&& FundEnglishName.equals(other.getFundEnglishName())
			&& FundChineseName.equals(other.getFundChineseName())
			&& TransactionDate == other.getTransactionDate()
			&& TransactionType.equals(other.getTransactionType())
			&& TransactionCode.equals(other.getTransactionCode())
			&& TransactionType_oth.equals(other.getTransactionType_oth())
			&& TransactionCode_oth.equals(other.getTransactionCode_oth())
			&& TransactionDesc.equals(other.getTransactionDesc())
			&& TransactionDesc_oth.equals(other.getTransactionDesc_oth())
			&& TransactionNo == other.getTransactionNo()
			&& InvestmentAmount == other.getInvestmentAmount()
			&& CurrentUnitPrice == other.getCurrentUnitPrice()
			&& OfferPrice == other.getOfferPrice()
			&& ValuationDate == other.getValuationDate()
			&& TransactionCharge == other.getTransactionCharge()
			&& TransactionAmount == other.getTransactionAmount()
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& DealDate == other.getDealDate()
			&& DealTime.equals(other.getDealTime());
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
		if( strFieldName.equals("ExtractedDate") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("FundCode") ) {
			return 3;
		}
		if( strFieldName.equals("FundEnglishName") ) {
			return 4;
		}
		if( strFieldName.equals("FundChineseName") ) {
			return 5;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return 6;
		}
		if( strFieldName.equals("TransactionType") ) {
			return 7;
		}
		if( strFieldName.equals("TransactionCode") ) {
			return 8;
		}
		if( strFieldName.equals("TransactionType_oth") ) {
			return 9;
		}
		if( strFieldName.equals("TransactionCode_oth") ) {
			return 10;
		}
		if( strFieldName.equals("TransactionDesc") ) {
			return 11;
		}
		if( strFieldName.equals("TransactionDesc_oth") ) {
			return 12;
		}
		if( strFieldName.equals("TransactionNo") ) {
			return 13;
		}
		if( strFieldName.equals("InvestmentAmount") ) {
			return 14;
		}
		if( strFieldName.equals("CurrentUnitPrice") ) {
			return 15;
		}
		if( strFieldName.equals("OfferPrice") ) {
			return 16;
		}
		if( strFieldName.equals("ValuationDate") ) {
			return 17;
		}
		if( strFieldName.equals("TransactionCharge") ) {
			return 18;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("DealDate") ) {
			return 22;
		}
		if( strFieldName.equals("DealTime") ) {
			return 23;
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
				strFieldName = "ExtractedDate";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "FundCode";
				break;
			case 4:
				strFieldName = "FundEnglishName";
				break;
			case 5:
				strFieldName = "FundChineseName";
				break;
			case 6:
				strFieldName = "TransactionDate";
				break;
			case 7:
				strFieldName = "TransactionType";
				break;
			case 8:
				strFieldName = "TransactionCode";
				break;
			case 9:
				strFieldName = "TransactionType_oth";
				break;
			case 10:
				strFieldName = "TransactionCode_oth";
				break;
			case 11:
				strFieldName = "TransactionDesc";
				break;
			case 12:
				strFieldName = "TransactionDesc_oth";
				break;
			case 13:
				strFieldName = "TransactionNo";
				break;
			case 14:
				strFieldName = "InvestmentAmount";
				break;
			case 15:
				strFieldName = "CurrentUnitPrice";
				break;
			case 16:
				strFieldName = "OfferPrice";
				break;
			case 17:
				strFieldName = "ValuationDate";
				break;
			case 18:
				strFieldName = "TransactionCharge";
				break;
			case 19:
				strFieldName = "TransactionAmount";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "DealDate";
				break;
			case 23:
				strFieldName = "DealTime";
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ExtractedDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundEnglishName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundChineseName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TransactionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionType_oth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionCode_oth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionDesc_oth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionNo") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestmentAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurrentUnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OfferPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ValuationDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TransactionCharge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DealTime") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
