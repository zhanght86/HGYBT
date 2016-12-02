/*
 * <p>ClassName: FundPriceUpTempSchema </p>
 * <p>Description: DB�� Schema ���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
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
	/** ���� */
	private int Oid;
	/** Ͷ���˻����� */
	private String FundCode;
	/** ��Ч���� */
	private int PriceEffectiveDate;
	/** ����(�̶�Ϊrmb) */
	private String Currency;
	/** ��λ�۸� */
	private double UtilPrice;
	/** ����� */
	private double OfferPrice;
	/** Ͷ��۸� */
	private double BidPrice;
	/** �����־ */
	private int HadProcessed;
	/** �������� */
	private int MakeDate;
	/** �������� */
	private int ModifyDate;
	/** �������� */
	private int ExtractedDate;

	public static final int FIELDNUM = 11;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

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

	/** ����<P> */
	public int getOid()
	{
		return Oid;
	}
	/** ���� */
	public void setOid(int aOid)
	{
		Oid = aOid;
	}
	/** ����<P> */
	public void setOid(String aOid)
	{
		if (aOid != null && !aOid.equals(""))
		{
			Integer tInteger = new Integer(aOid);
			int i = tInteger.intValue();
			Oid = i;
		}
	}

	/** Ͷ���˻�����<P> */
	public String getFundCode()
	{
		if (FundCode != null && !FundCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundCode = StrTool.unicodeToGBK(FundCode);
		}
		return FundCode;
	}
	/** Ͷ���˻����� */
	public void setFundCode(String aFundCode)
	{
		FundCode = aFundCode;
	}
	/** ��Ч����<P> */
	public int getPriceEffectiveDate()
	{
		return PriceEffectiveDate;
	}
	/** ��Ч���� */
	public void setPriceEffectiveDate(int aPriceEffectiveDate)
	{
		PriceEffectiveDate = aPriceEffectiveDate;
	}
	/** ��Ч����<P> */
	public void setPriceEffectiveDate(String aPriceEffectiveDate)
	{
		if (aPriceEffectiveDate != null && !aPriceEffectiveDate.equals(""))
		{
			Integer tInteger = new Integer(aPriceEffectiveDate);
			int i = tInteger.intValue();
			PriceEffectiveDate = i;
		}
	}

	/** ����(�̶�Ϊrmb)<P> */
	public String getCurrency()
	{
		if (Currency != null && !Currency.equals("") && SysConst.CHANGECHARSET == true)
		{
			Currency = StrTool.unicodeToGBK(Currency);
		}
		return Currency;
	}
	/** ����(�̶�Ϊrmb) */
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/** ��λ�۸�<P> */
	public double getUtilPrice()
	{
		return UtilPrice;
	}
	/** ��λ�۸� */
	public void setUtilPrice(double aUtilPrice)
	{
		UtilPrice = aUtilPrice;
	}
	/** ��λ�۸�<P> */
	public void setUtilPrice(String aUtilPrice)
	{
		if (aUtilPrice != null && !aUtilPrice.equals(""))
		{
			Double tDouble = new Double(aUtilPrice);
			double d = tDouble.doubleValue();
			UtilPrice = d;
		}
	}

	/** �����<P> */
	public double getOfferPrice()
	{
		return OfferPrice;
	}
	/** ����� */
	public void setOfferPrice(double aOfferPrice)
	{
		OfferPrice = aOfferPrice;
	}
	/** �����<P> */
	public void setOfferPrice(String aOfferPrice)
	{
		if (aOfferPrice != null && !aOfferPrice.equals(""))
		{
			Double tDouble = new Double(aOfferPrice);
			double d = tDouble.doubleValue();
			OfferPrice = d;
		}
	}

	/** Ͷ��۸�<P> */
	public double getBidPrice()
	{
		return BidPrice;
	}
	/** Ͷ��۸� */
	public void setBidPrice(double aBidPrice)
	{
		BidPrice = aBidPrice;
	}
	/** Ͷ��۸�<P> */
	public void setBidPrice(String aBidPrice)
	{
		if (aBidPrice != null && !aBidPrice.equals(""))
		{
			Double tDouble = new Double(aBidPrice);
			double d = tDouble.doubleValue();
			BidPrice = d;
		}
	}

	/** �����־<P> */
	public int getHadProcessed()
	{
		return HadProcessed;
	}
	/** �����־ */
	public void setHadProcessed(int aHadProcessed)
	{
		HadProcessed = aHadProcessed;
	}
	/** �����־<P> */
	public void setHadProcessed(String aHadProcessed)
	{
		if (aHadProcessed != null && !aHadProcessed.equals(""))
		{
			Integer tInteger = new Integer(aHadProcessed);
			int i = tInteger.intValue();
			HadProcessed = i;
		}
	}

	/** ��������<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** �������� */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** ��������<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** ��������<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** �������� */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** ��������<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** ��������<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** �������� */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** ��������<P> */
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
	* ʹ������һ�� FundPriceUpTempSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
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
	* ʹ�� ResultSet �еĵ� i �и� Schema ��ֵ
	* @param: ResultSet ����; i ����
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// �ǹ����α�
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
			// @@������
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
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUpTemp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
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
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUpTemp>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
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
			// @@������
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
	* ȡ�ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode ϣ��ȡ�õ��ֶ���
	* @return: FValue String��ʽ���ֶ�ֵ
	*			FValue = ""		û��ƥ����ֶ�
	*			FValue = "null"	�ֶ�ֵΪnull
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
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�ֵ
	* @param: nFieldIndex ָ�����ֶ�����ֵ
	* @return: �ֶ�ֵ��
	*          ���û�ж�Ӧ���ֶΣ�����""
	*          ����ֶ�ֵΪ�գ�����"null"
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
	* ���ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode ϣ��ȡ�õ��ֶ���
	* @return: FValue String��ʽ���ֶ�ֵ
	*			FValue = ""		û��ƥ����ֶ�
	*			FValue = "null"	�ֶ�ֵΪnull
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
	* ȡ��Schemaӵ���ֶε�����
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* ȡ��Schema��ָ���ֶ�������Ӧ������ֵ
	* ���û�ж�Ӧ���ֶΣ�����-1
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
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ���
	* ���û�ж�Ӧ���ֶΣ�����""
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
	* ȡ��Schema��ָ���ֶ�������Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
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
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
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
