/*
 * <p>ClassName: FundPriceUpTempSchema </p>
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
import com.sinosoft.lis.db.FundPriceUpTempDB;

public class FundPriceUpTempSchema implements Schema
{
	// @Field
	/** 主键 */
	private int Oid;
	/** 投资账户代码 */
	private String FundCode;
	/** 生效日期 */
	private int PriceEffectiveDate;
	/** 币种(固定为rmb) */
	private String Currency;
	/** 单位价格 */
	private double UtilPrice;
	/** 买入价 */
	private double OfferPrice;
	/** 投标价格 */
	private double BidPrice;
	/** 处理标志 */
	private int HadProcessed;
	/** 创建日期 */
	private int MakeDate;
	/** 更新日期 */
	private int ModifyDate;
	/** 导出日期 */
	private int ExtractedDate;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FundPriceUpTempSchema()
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

	/** 主键<P> */
	public int getOid()
	{
		return Oid;
	}
	/** 主键 */
	public void setOid(int aOid)
	{
		Oid = aOid;
	}
	/** 主键<P> */
	public void setOid(String aOid)
	{
		if (aOid != null && !aOid.equals(""))
		{
			Integer tInteger = new Integer(aOid);
			int i = tInteger.intValue();
			Oid = i;
		}
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
	/** 生效日期<P> */
	public int getPriceEffectiveDate()
	{
		return PriceEffectiveDate;
	}
	/** 生效日期 */
	public void setPriceEffectiveDate(int aPriceEffectiveDate)
	{
		PriceEffectiveDate = aPriceEffectiveDate;
	}
	/** 生效日期<P> */
	public void setPriceEffectiveDate(String aPriceEffectiveDate)
	{
		if (aPriceEffectiveDate != null && !aPriceEffectiveDate.equals(""))
		{
			Integer tInteger = new Integer(aPriceEffectiveDate);
			int i = tInteger.intValue();
			PriceEffectiveDate = i;
		}
	}

	/** 币种(固定为rmb)<P> */
	public String getCurrency()
	{
		if (Currency != null && !Currency.equals("") && SysConst.CHANGECHARSET == true)
		{
			Currency = StrTool.unicodeToGBK(Currency);
		}
		return Currency;
	}
	/** 币种(固定为rmb) */
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/** 单位价格<P> */
	public double getUtilPrice()
	{
		return UtilPrice;
	}
	/** 单位价格 */
	public void setUtilPrice(double aUtilPrice)
	{
		UtilPrice = aUtilPrice;
	}
	/** 单位价格<P> */
	public void setUtilPrice(String aUtilPrice)
	{
		if (aUtilPrice != null && !aUtilPrice.equals(""))
		{
			Double tDouble = new Double(aUtilPrice);
			double d = tDouble.doubleValue();
			UtilPrice = d;
		}
	}

	/** 买入价<P> */
	public double getOfferPrice()
	{
		return OfferPrice;
	}
	/** 买入价 */
	public void setOfferPrice(double aOfferPrice)
	{
		OfferPrice = aOfferPrice;
	}
	/** 买入价<P> */
	public void setOfferPrice(String aOfferPrice)
	{
		if (aOfferPrice != null && !aOfferPrice.equals(""))
		{
			Double tDouble = new Double(aOfferPrice);
			double d = tDouble.doubleValue();
			OfferPrice = d;
		}
	}

	/** 投标价格<P> */
	public double getBidPrice()
	{
		return BidPrice;
	}
	/** 投标价格 */
	public void setBidPrice(double aBidPrice)
	{
		BidPrice = aBidPrice;
	}
	/** 投标价格<P> */
	public void setBidPrice(String aBidPrice)
	{
		if (aBidPrice != null && !aBidPrice.equals(""))
		{
			Double tDouble = new Double(aBidPrice);
			double d = tDouble.doubleValue();
			BidPrice = d;
		}
	}

	/** 处理标志<P> */
	public int getHadProcessed()
	{
		return HadProcessed;
	}
	/** 处理标志 */
	public void setHadProcessed(int aHadProcessed)
	{
		HadProcessed = aHadProcessed;
	}
	/** 处理标志<P> */
	public void setHadProcessed(String aHadProcessed)
	{
		if (aHadProcessed != null && !aHadProcessed.equals(""))
		{
			Integer tInteger = new Integer(aHadProcessed);
			int i = tInteger.intValue();
			HadProcessed = i;
		}
	}

	/** 创建日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 创建日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 创建日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
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

	/** 导出日期<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** 导出日期 */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** 导出日期<P> */
	public void setExtractedDate(String aExtractedDate)
	{
		if (aExtractedDate != null && !aExtractedDate.equals(""))
		{
			Integer tInteger = new Integer(aExtractedDate);
			int i = tInteger.intValue();
			ExtractedDate = i;
		}
	}


	/**
	* 使用另外一个 FundPriceUpTempSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(FundPriceUpTempSchema aFundPriceUpTempSchema)
	{
		this.Oid = aFundPriceUpTempSchema.getOid();
		this.FundCode = aFundPriceUpTempSchema.getFundCode();
		this.PriceEffectiveDate = aFundPriceUpTempSchema.getPriceEffectiveDate();
		this.Currency = aFundPriceUpTempSchema.getCurrency();
		this.UtilPrice = aFundPriceUpTempSchema.getUtilPrice();
		this.OfferPrice = aFundPriceUpTempSchema.getOfferPrice();
		this.BidPrice = aFundPriceUpTempSchema.getBidPrice();
		this.HadProcessed = aFundPriceUpTempSchema.getHadProcessed();
		this.MakeDate = aFundPriceUpTempSchema.getMakeDate();
		this.ModifyDate = aFundPriceUpTempSchema.getModifyDate();
		this.ExtractedDate = aFundPriceUpTempSchema.getExtractedDate();
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
			this.Oid = rs.getInt("Oid");
			if( rs.getString("FundCode") == null )
				this.FundCode = null;
			else
				this.FundCode = rs.getString("FundCode").trim();

			this.PriceEffectiveDate = rs.getInt("PriceEffectiveDate");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.UtilPrice = rs.getDouble("UtilPrice");
			this.OfferPrice = rs.getDouble("OfferPrice");
			this.BidPrice = rs.getDouble("BidPrice");
			this.HadProcessed = rs.getInt("HadProcessed");
			this.MakeDate = rs.getInt("MakeDate");
			this.ModifyDate = rs.getInt("ModifyDate");
			this.ExtractedDate = rs.getInt("ExtractedDate");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FundPriceUpTempSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public FundPriceUpTempSchema getSchema()
	{
		FundPriceUpTempSchema aFundPriceUpTempSchema = new FundPriceUpTempSchema();
		aFundPriceUpTempSchema.setSchema(this);
		return aFundPriceUpTempSchema;
	}

	public FundPriceUpTempDB getDB()
	{
		FundPriceUpTempDB aDBOper = new FundPriceUpTempDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUpTemp描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(Oid) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FundCode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PriceEffectiveDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Currency) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UtilPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(OfferPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BidPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(HadProcessed) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUpTemp>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Oid= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			FundCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PriceEffectiveDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UtilPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			OfferPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BidPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			HadProcessed= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FundPriceUpTempSchema";
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
		if (FCode.equals("FundCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCode));
		}
		if (FCode.equals("PriceEffectiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriceEffectiveDate));
		}
		if (FCode.equals("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equals("UtilPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UtilPrice));
		}
		if (FCode.equals("OfferPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferPrice));
		}
		if (FCode.equals("BidPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BidPrice));
		}
		if (FCode.equals("HadProcessed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HadProcessed));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equals("ExtractedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtractedDate));
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
				strFieldValue = String.valueOf(Oid);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FundCode);
				break;
			case 2:
				strFieldValue = String.valueOf(PriceEffectiveDate);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 4:
				strFieldValue = String.valueOf(UtilPrice);
				break;
			case 5:
				strFieldValue = String.valueOf(OfferPrice);
				break;
			case 6:
				strFieldValue = String.valueOf(BidPrice);
				break;
			case 7:
				strFieldValue = String.valueOf(HadProcessed);
				break;
			case 8:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 9:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 10:
				strFieldValue = String.valueOf(ExtractedDate);
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Oid = i;
			}
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
		if (FCode.equals("PriceEffectiveDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PriceEffectiveDate = i;
			}
		}
		if (FCode.equals("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equals("UtilPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UtilPrice = d;
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
		if (FCode.equals("BidPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BidPrice = d;
			}
		}
		if (FCode.equals("HadProcessed"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				HadProcessed = i;
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
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyDate = i;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FundPriceUpTempSchema other = (FundPriceUpTempSchema)otherObject;
		return
			Oid == other.getOid()
			&& FundCode.equals(other.getFundCode())
			&& PriceEffectiveDate == other.getPriceEffectiveDate()
			&& Currency.equals(other.getCurrency())
			&& UtilPrice == other.getUtilPrice()
			&& OfferPrice == other.getOfferPrice()
			&& BidPrice == other.getBidPrice()
			&& HadProcessed == other.getHadProcessed()
			&& MakeDate == other.getMakeDate()
			&& ModifyDate == other.getModifyDate()
			&& ExtractedDate == other.getExtractedDate();
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
		if( strFieldName.equals("FundCode") ) {
			return 1;
		}
		if( strFieldName.equals("PriceEffectiveDate") ) {
			return 2;
		}
		if( strFieldName.equals("Currency") ) {
			return 3;
		}
		if( strFieldName.equals("UtilPrice") ) {
			return 4;
		}
		if( strFieldName.equals("OfferPrice") ) {
			return 5;
		}
		if( strFieldName.equals("BidPrice") ) {
			return 6;
		}
		if( strFieldName.equals("HadProcessed") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ExtractedDate") ) {
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
				strFieldName = "Oid";
				break;
			case 1:
				strFieldName = "FundCode";
				break;
			case 2:
				strFieldName = "PriceEffectiveDate";
				break;
			case 3:
				strFieldName = "Currency";
				break;
			case 4:
				strFieldName = "UtilPrice";
				break;
			case 5:
				strFieldName = "OfferPrice";
				break;
			case 6:
				strFieldName = "BidPrice";
				break;
			case 7:
				strFieldName = "HadProcessed";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ExtractedDate";
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FundCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriceEffectiveDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UtilPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OfferPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BidPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HadProcessed") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ExtractedDate") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
