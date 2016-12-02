/*
 * <p>ClassName: FundSummarySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛花旗
 * @CreateDate：2012-03-24
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.FundSummaryDB;

public class FundSummarySchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 交易日期 */
	private int ExtractedDate;
	/** 保单号 */
	private String PolicyNo;
	/** 投资账户代码 */
	private String FundCode;
	/** 投资账户英文名 */
	private String FundEnglishName;
	/** 投资账户中文名 */
	private String FundChineseName;
	/** 投资单位总数 */
	private double FundUnitNo;
	/** 当前单位价格 */
	private double CurUnitPrice;
	/** 当前买入价格 */
	private double CurOfferPrice;
	/** 折合金额 */
	private double TotalValue;
	/** 计价日期 */
	private int LastValuationDate;
	/** 生成日期 */
	private int MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 更新日期 */
	private int ModifyDate;
	/** 更新时间 */
	private String ModifyTime;
	/** Column_16 */
	private int DealDate;
	/** Column_17 */
	private String DealTime;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FundSummarySchema()
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
	/** 交易日期<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** 交易日期 */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** 交易日期<P> */
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
	/** 投资账户代码<P>被保人年龄 */
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
	/** 投资单位总数<P> */
	public double getFundUnitNo()
	{
		return FundUnitNo;
	}
	/** 投资单位总数 */
	public void setFundUnitNo(double aFundUnitNo)
	{
		FundUnitNo = aFundUnitNo;
	}
	/** 投资单位总数<P> */
	public void setFundUnitNo(String aFundUnitNo)
	{
		if (aFundUnitNo != null && !aFundUnitNo.equals(""))
		{
			Double tDouble = new Double(aFundUnitNo);
			double d = tDouble.doubleValue();
			FundUnitNo = d;
		}
	}

	/** 当前单位价格<P> */
	public double getCurUnitPrice()
	{
		return CurUnitPrice;
	}
	/** 当前单位价格 */
	public void setCurUnitPrice(double aCurUnitPrice)
	{
		CurUnitPrice = aCurUnitPrice;
	}
	/** 当前单位价格<P> */
	public void setCurUnitPrice(String aCurUnitPrice)
	{
		if (aCurUnitPrice != null && !aCurUnitPrice.equals(""))
		{
			Double tDouble = new Double(aCurUnitPrice);
			double d = tDouble.doubleValue();
			CurUnitPrice = d;
		}
	}

	/** 当前买入价格<P> */
	public double getCurOfferPrice()
	{
		return CurOfferPrice;
	}
	/** 当前买入价格 */
	public void setCurOfferPrice(double aCurOfferPrice)
	{
		CurOfferPrice = aCurOfferPrice;
	}
	/** 当前买入价格<P> */
	public void setCurOfferPrice(String aCurOfferPrice)
	{
		if (aCurOfferPrice != null && !aCurOfferPrice.equals(""))
		{
			Double tDouble = new Double(aCurOfferPrice);
			double d = tDouble.doubleValue();
			CurOfferPrice = d;
		}
	}

	/** 折合金额<P> */
	public double getTotalValue()
	{
		return TotalValue;
	}
	/** 折合金额 */
	public void setTotalValue(double aTotalValue)
	{
		TotalValue = aTotalValue;
	}
	/** 折合金额<P> */
	public void setTotalValue(String aTotalValue)
	{
		if (aTotalValue != null && !aTotalValue.equals(""))
		{
			Double tDouble = new Double(aTotalValue);
			double d = tDouble.doubleValue();
			TotalValue = d;
		}
	}

	/** 计价日期<P> */
	public int getLastValuationDate()
	{
		return LastValuationDate;
	}
	/** 计价日期 */
	public void setLastValuationDate(int aLastValuationDate)
	{
		LastValuationDate = aLastValuationDate;
	}
	/** 计价日期<P> */
	public void setLastValuationDate(String aLastValuationDate)
	{
		if (aLastValuationDate != null && !aLastValuationDate.equals(""))
		{
			Integer tInteger = new Integer(aLastValuationDate);
			int i = tInteger.intValue();
			LastValuationDate = i;
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
	/** 更新日期<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** 更新日期 */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 更新日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** 更新时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 更新时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** Column_16<P> */
	public int getDealDate()
	{
		return DealDate;
	}
	/** Column_16 */
	public void setDealDate(int aDealDate)
	{
		DealDate = aDealDate;
	}
	/** Column_16<P> */
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals(""))
		{
			Integer tInteger = new Integer(aDealDate);
			int i = tInteger.intValue();
			DealDate = i;
		}
	}

	/** Column_17<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** Column_17 */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* 使用另外一个 FundSummarySchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(FundSummarySchema aFundSummarySchema)
	{
		this.SerialNo = aFundSummarySchema.getSerialNo();
		this.ExtractedDate = aFundSummarySchema.getExtractedDate();
		this.PolicyNo = aFundSummarySchema.getPolicyNo();
		this.FundCode = aFundSummarySchema.getFundCode();
		this.FundEnglishName = aFundSummarySchema.getFundEnglishName();
		this.FundChineseName = aFundSummarySchema.getFundChineseName();
		this.FundUnitNo = aFundSummarySchema.getFundUnitNo();
		this.CurUnitPrice = aFundSummarySchema.getCurUnitPrice();
		this.CurOfferPrice = aFundSummarySchema.getCurOfferPrice();
		this.TotalValue = aFundSummarySchema.getTotalValue();
		this.LastValuationDate = aFundSummarySchema.getLastValuationDate();
		this.MakeDate = aFundSummarySchema.getMakeDate();
		this.MakeTime = aFundSummarySchema.getMakeTime();
		this.ModifyDate = aFundSummarySchema.getModifyDate();
		this.ModifyTime = aFundSummarySchema.getModifyTime();
		this.DealDate = aFundSummarySchema.getDealDate();
		this.DealTime = aFundSummarySchema.getDealTime();
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

			this.FundUnitNo = rs.getDouble("FundUnitNo");
			this.CurUnitPrice = rs.getDouble("CurUnitPrice");
			this.CurOfferPrice = rs.getDouble("CurOfferPrice");
			this.TotalValue = rs.getDouble("TotalValue");
			this.LastValuationDate = rs.getInt("LastValuationDate");
			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getInt("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

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
			tError.moduleName = "FundSummarySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public FundSummarySchema getSchema()
	{
		FundSummarySchema aFundSummarySchema = new FundSummarySchema();
		aFundSummarySchema.setSchema(this);
		return aFundSummarySchema;
	}

	public FundSummaryDB getDB()
	{
		FundSummaryDB aDBOper = new FundSummaryDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFundSummary描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundEnglishName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundChineseName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FundUnitNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CurUnitPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CurOfferPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TotalValue) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LastValuationDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(DealDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFundSummary>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FundCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FundEnglishName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FundChineseName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FundUnitNo = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			CurUnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CurOfferPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			TotalValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			LastValuationDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FundSummarySchema";
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
		if (FCode.equals("FundUnitNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundUnitNo));
		}
		if (FCode.equals("CurUnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurUnitPrice));
		}
		if (FCode.equals("CurOfferPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurOfferPrice));
		}
		if (FCode.equals("TotalValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalValue));
		}
		if (FCode.equals("LastValuationDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastValuationDate));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
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
				strFieldValue = String.valueOf(FundUnitNo);
				break;
			case 7:
				strFieldValue = String.valueOf(CurUnitPrice);
				break;
			case 8:
				strFieldValue = String.valueOf(CurOfferPrice);
				break;
			case 9:
				strFieldValue = String.valueOf(TotalValue);
				break;
			case 10:
				strFieldValue = String.valueOf(LastValuationDate);
				break;
			case 11:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = String.valueOf(DealDate);
				break;
			case 16:
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
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equals("FundUnitNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FundUnitNo = d;
			}
		}
		if (FCode.equals("CurUnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurUnitPrice = d;
			}
		}
		if (FCode.equals("CurOfferPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurOfferPrice = d;
			}
		}
		if (FCode.equals("TotalValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalValue = d;
			}
		}
		if (FCode.equals("LastValuationDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LastValuationDate = i;
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
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyDate = i;
			}
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
		FundSummarySchema other = (FundSummarySchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& FundCode.equals(other.getFundCode())
			&& FundEnglishName.equals(other.getFundEnglishName())
			&& FundChineseName.equals(other.getFundChineseName())
			&& FundUnitNo == other.getFundUnitNo()
			&& CurUnitPrice == other.getCurUnitPrice()
			&& CurOfferPrice == other.getCurOfferPrice()
			&& TotalValue == other.getTotalValue()
			&& LastValuationDate == other.getLastValuationDate()
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime.equals(other.getModifyTime())
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
		if( strFieldName.equals("FundUnitNo") ) {
			return 6;
		}
		if( strFieldName.equals("CurUnitPrice") ) {
			return 7;
		}
		if( strFieldName.equals("CurOfferPrice") ) {
			return 8;
		}
		if( strFieldName.equals("TotalValue") ) {
			return 9;
		}
		if( strFieldName.equals("LastValuationDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("DealDate") ) {
			return 15;
		}
		if( strFieldName.equals("DealTime") ) {
			return 16;
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
				strFieldName = "FundUnitNo";
				break;
			case 7:
				strFieldName = "CurUnitPrice";
				break;
			case 8:
				strFieldName = "CurOfferPrice";
				break;
			case 9:
				strFieldName = "TotalValue";
				break;
			case 10:
				strFieldName = "LastValuationDate";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "DealDate";
				break;
			case 16:
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
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("FundUnitNo") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurUnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurOfferPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastValuationDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
