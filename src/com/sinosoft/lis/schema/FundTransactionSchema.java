/*
 * <p>ClassName: FundTransactionSchema </p>
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
import com.sinosoft.lis.db.FundTransactionDB;

public class FundTransactionSchema implements Schema
{
	// @Field
	/** ��ˮ�� */
	private String SerialNo;
	/** �������� */
	private int ExtractedDate;
	/** ������ */
	private String PolicyNo;
	/** Ͷ���˻����� */
	private String FundCode;
	/** Ͷ���˻�Ӣ���� */
	private String FundEnglishName;
	/** Ͷ���˻������� */
	private String FundChineseName;
	/** �������� */
	private int TransactionDate;
	/** �������� */
	private String TransactionType;
	/** ���״��� */
	private String TransactionCode;
	/** ���׷��� */
	private double TransactionNo;
	/** Ͷ���ܶ� */
	private double InvestmentAmount;
	/** ��λ�۸� */
	private double CurrentUnitPrice;
	/** ����۸� */
	private double OfferPrice;
	/** �Ƽ����� */
	private int ValuationDate;
	/** �ɽ����� */
	private int DealingDate;
	/** ����ģʽ */
	private String ProcessingMode;
	/** �������� */
	private int MakeDate;
	/** ����ʱ�� */
	private String MakeTime;
	/** �������� */
	private int ModifyDate;
	/** ����ʱ�� */
	private String ModifyTime;
	/** Column_21 */
	private int DealDate;
	/** Column_22 */
	private String DealTime;

	public static final int FIELDNUM = 22;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public FundTransactionSchema()
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

	/** ��ˮ��<P> */
	public String getSerialNo()
	{
		if (SerialNo != null && !SerialNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SerialNo = StrTool.unicodeToGBK(SerialNo);
		}
		return SerialNo;
	}
	/** ��ˮ�� */
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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

	/** ������<P>��Ʒ���� */
	public String getPolicyNo()
	{
		if (PolicyNo != null && !PolicyNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyNo = StrTool.unicodeToGBK(PolicyNo);
		}
		return PolicyNo;
	}
	/** ������ */
	public void setPolicyNo(String aPolicyNo)
	{
		PolicyNo = aPolicyNo;
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
	/** Ͷ���˻�Ӣ����<P> */
	public String getFundEnglishName()
	{
		if (FundEnglishName != null && !FundEnglishName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundEnglishName = StrTool.unicodeToGBK(FundEnglishName);
		}
		return FundEnglishName;
	}
	/** Ͷ���˻�Ӣ���� */
	public void setFundEnglishName(String aFundEnglishName)
	{
		FundEnglishName = aFundEnglishName;
	}
	/** Ͷ���˻�������<P> */
	public String getFundChineseName()
	{
		if (FundChineseName != null && !FundChineseName.equals("") && SysConst.CHANGECHARSET == true)
		{
			FundChineseName = StrTool.unicodeToGBK(FundChineseName);
		}
		return FundChineseName;
	}
	/** Ͷ���˻������� */
	public void setFundChineseName(String aFundChineseName)
	{
		FundChineseName = aFundChineseName;
	}
	/** ��������<P> */
	public int getTransactionDate()
	{
		return TransactionDate;
	}
	/** �������� */
	public void setTransactionDate(int aTransactionDate)
	{
		TransactionDate = aTransactionDate;
	}
	/** ��������<P> */
	public void setTransactionDate(String aTransactionDate)
	{
		if (aTransactionDate != null && !aTransactionDate.equals(""))
		{
			Integer tInteger = new Integer(aTransactionDate);
			int i = tInteger.intValue();
			TransactionDate = i;
		}
	}

	/** ��������<P> */
	public String getTransactionType()
	{
		if (TransactionType != null && !TransactionType.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionType = StrTool.unicodeToGBK(TransactionType);
		}
		return TransactionType;
	}
	/** �������� */
	public void setTransactionType(String aTransactionType)
	{
		TransactionType = aTransactionType;
	}
	/** ���״���<P> */
	public String getTransactionCode()
	{
		if (TransactionCode != null && !TransactionCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionCode = StrTool.unicodeToGBK(TransactionCode);
		}
		return TransactionCode;
	}
	/** ���״��� */
	public void setTransactionCode(String aTransactionCode)
	{
		TransactionCode = aTransactionCode;
	}
	/** ���׷���<P> */
	public double getTransactionNo()
	{
		return TransactionNo;
	}
	/** ���׷��� */
	public void setTransactionNo(double aTransactionNo)
	{
		TransactionNo = aTransactionNo;
	}
	/** ���׷���<P> */
	public void setTransactionNo(String aTransactionNo)
	{
		if (aTransactionNo != null && !aTransactionNo.equals(""))
		{
			Double tDouble = new Double(aTransactionNo);
			double d = tDouble.doubleValue();
			TransactionNo = d;
		}
	}

	/** Ͷ���ܶ�<P> */
	public double getInvestmentAmount()
	{
		return InvestmentAmount;
	}
	/** Ͷ���ܶ� */
	public void setInvestmentAmount(double aInvestmentAmount)
	{
		InvestmentAmount = aInvestmentAmount;
	}
	/** Ͷ���ܶ�<P> */
	public void setInvestmentAmount(String aInvestmentAmount)
	{
		if (aInvestmentAmount != null && !aInvestmentAmount.equals(""))
		{
			Double tDouble = new Double(aInvestmentAmount);
			double d = tDouble.doubleValue();
			InvestmentAmount = d;
		}
	}

	/** ��λ�۸�<P> */
	public double getCurrentUnitPrice()
	{
		return CurrentUnitPrice;
	}
	/** ��λ�۸� */
	public void setCurrentUnitPrice(double aCurrentUnitPrice)
	{
		CurrentUnitPrice = aCurrentUnitPrice;
	}
	/** ��λ�۸�<P> */
	public void setCurrentUnitPrice(String aCurrentUnitPrice)
	{
		if (aCurrentUnitPrice != null && !aCurrentUnitPrice.equals(""))
		{
			Double tDouble = new Double(aCurrentUnitPrice);
			double d = tDouble.doubleValue();
			CurrentUnitPrice = d;
		}
	}

	/** ����۸�<P> */
	public double getOfferPrice()
	{
		return OfferPrice;
	}
	/** ����۸� */
	public void setOfferPrice(double aOfferPrice)
	{
		OfferPrice = aOfferPrice;
	}
	/** ����۸�<P> */
	public void setOfferPrice(String aOfferPrice)
	{
		if (aOfferPrice != null && !aOfferPrice.equals(""))
		{
			Double tDouble = new Double(aOfferPrice);
			double d = tDouble.doubleValue();
			OfferPrice = d;
		}
	}

	/** �Ƽ�����<P> */
	public int getValuationDate()
	{
		return ValuationDate;
	}
	/** �Ƽ����� */
	public void setValuationDate(int aValuationDate)
	{
		ValuationDate = aValuationDate;
	}
	/** �Ƽ�����<P> */
	public void setValuationDate(String aValuationDate)
	{
		if (aValuationDate != null && !aValuationDate.equals(""))
		{
			Integer tInteger = new Integer(aValuationDate);
			int i = tInteger.intValue();
			ValuationDate = i;
		}
	}

	/** �ɽ�����<P> */
	public int getDealingDate()
	{
		return DealingDate;
	}
	/** �ɽ����� */
	public void setDealingDate(int aDealingDate)
	{
		DealingDate = aDealingDate;
	}
	/** �ɽ�����<P> */
	public void setDealingDate(String aDealingDate)
	{
		if (aDealingDate != null && !aDealingDate.equals(""))
		{
			Integer tInteger = new Integer(aDealingDate);
			int i = tInteger.intValue();
			DealingDate = i;
		}
	}

	/** ����ģʽ<P> */
	public String getProcessingMode()
	{
		if (ProcessingMode != null && !ProcessingMode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProcessingMode = StrTool.unicodeToGBK(ProcessingMode);
		}
		return ProcessingMode;
	}
	/** ����ģʽ */
	public void setProcessingMode(String aProcessingMode)
	{
		ProcessingMode = aProcessingMode;
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

	/** ����ʱ��<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** ����ʱ�� */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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

	/** ����ʱ��<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** ����ʱ�� */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** Column_21<P> */
	public int getDealDate()
	{
		return DealDate;
	}
	/** Column_21 */
	public void setDealDate(int aDealDate)
	{
		DealDate = aDealDate;
	}
	/** Column_21<P> */
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals(""))
		{
			Integer tInteger = new Integer(aDealDate);
			int i = tInteger.intValue();
			DealDate = i;
		}
	}

	/** Column_22<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** Column_22 */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* ʹ������һ�� FundTransactionSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(FundTransactionSchema aFundTransactionSchema)
	{
		this.SerialNo = aFundTransactionSchema.getSerialNo();
		this.ExtractedDate = aFundTransactionSchema.getExtractedDate();
		this.PolicyNo = aFundTransactionSchema.getPolicyNo();
		this.FundCode = aFundTransactionSchema.getFundCode();
		this.FundEnglishName = aFundTransactionSchema.getFundEnglishName();
		this.FundChineseName = aFundTransactionSchema.getFundChineseName();
		this.TransactionDate = aFundTransactionSchema.getTransactionDate();
		this.TransactionType = aFundTransactionSchema.getTransactionType();
		this.TransactionCode = aFundTransactionSchema.getTransactionCode();
		this.TransactionNo = aFundTransactionSchema.getTransactionNo();
		this.InvestmentAmount = aFundTransactionSchema.getInvestmentAmount();
		this.CurrentUnitPrice = aFundTransactionSchema.getCurrentUnitPrice();
		this.OfferPrice = aFundTransactionSchema.getOfferPrice();
		this.ValuationDate = aFundTransactionSchema.getValuationDate();
		this.DealingDate = aFundTransactionSchema.getDealingDate();
		this.ProcessingMode = aFundTransactionSchema.getProcessingMode();
		this.MakeDate = aFundTransactionSchema.getMakeDate();
		this.MakeTime = aFundTransactionSchema.getMakeTime();
		this.ModifyDate = aFundTransactionSchema.getModifyDate();
		this.ModifyTime = aFundTransactionSchema.getModifyTime();
		this.DealDate = aFundTransactionSchema.getDealDate();
		this.DealTime = aFundTransactionSchema.getDealTime();
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

			this.TransactionDate = rs.getInt("TransactionDate");
			if( rs.getString("TransactionType") == null )
				this.TransactionType = null;
			else
				this.TransactionType = rs.getString("TransactionType").trim();

			if( rs.getString("TransactionCode") == null )
				this.TransactionCode = null;
			else
				this.TransactionCode = rs.getString("TransactionCode").trim();

			this.TransactionNo = rs.getDouble("TransactionNo");
			this.InvestmentAmount = rs.getDouble("InvestmentAmount");
			this.CurrentUnitPrice = rs.getDouble("CurrentUnitPrice");
			this.OfferPrice = rs.getDouble("OfferPrice");
			this.ValuationDate = rs.getInt("ValuationDate");
			this.DealingDate = rs.getInt("DealingDate");
			if( rs.getString("ProcessingMode") == null )
				this.ProcessingMode = null;
			else
				this.ProcessingMode = rs.getString("ProcessingMode").trim();

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
			// @@������
			CError tError = new CError();
			tError.moduleName = "FundTransactionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public FundTransactionSchema getSchema()
	{
		FundTransactionSchema aFundTransactionSchema = new FundTransactionSchema();
		aFundTransactionSchema.setSchema(this);
		return aFundTransactionSchema;
	}

	public FundTransactionDB getDB()
	{
		FundTransactionDB aDBOper = new FundTransactionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundTransaction����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
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
					+  ChgData.chgData(TransactionDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionCode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InvestmentAmount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CurrentUnitPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(OfferPrice) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ValuationDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(DealingDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProcessingMode) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(DealDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealTime) );
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundTransaction>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
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
			TransactionDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			TransactionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TransactionCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TransactionNo = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			InvestmentAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			CurrentUnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			OfferPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			ValuationDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			DealingDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			ProcessingMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			DealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "FundTransactionSchema";
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
		if (FCode.equals("DealingDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealingDate));
		}
		if (FCode.equals("ProcessingMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessingMode));
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
				strFieldValue = String.valueOf(TransactionDate);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TransactionType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TransactionCode);
				break;
			case 9:
				strFieldValue = String.valueOf(TransactionNo);
				break;
			case 10:
				strFieldValue = String.valueOf(InvestmentAmount);
				break;
			case 11:
				strFieldValue = String.valueOf(CurrentUnitPrice);
				break;
			case 12:
				strFieldValue = String.valueOf(OfferPrice);
				break;
			case 13:
				strFieldValue = String.valueOf(ValuationDate);
				break;
			case 14:
				strFieldValue = String.valueOf(DealingDate);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ProcessingMode);
				break;
			case 16:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = String.valueOf(DealDate);
				break;
			case 21:
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
		if (FCode.equals("DealingDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DealingDate = i;
			}
		}
		if (FCode.equals("ProcessingMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessingMode = FValue.trim();
			}
			else
				ProcessingMode = null;
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
		FundTransactionSchema other = (FundTransactionSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& FundCode.equals(other.getFundCode())
			&& FundEnglishName.equals(other.getFundEnglishName())
			&& FundChineseName.equals(other.getFundChineseName())
			&& TransactionDate == other.getTransactionDate()
			&& TransactionType.equals(other.getTransactionType())
			&& TransactionCode.equals(other.getTransactionCode())
			&& TransactionNo == other.getTransactionNo()
			&& InvestmentAmount == other.getInvestmentAmount()
			&& CurrentUnitPrice == other.getCurrentUnitPrice()
			&& OfferPrice == other.getOfferPrice()
			&& ValuationDate == other.getValuationDate()
			&& DealingDate == other.getDealingDate()
			&& ProcessingMode.equals(other.getProcessingMode())
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime.equals(other.getModifyTime())
			&& DealDate == other.getDealDate()
			&& DealTime.equals(other.getDealTime());
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
		if( strFieldName.equals("TransactionNo") ) {
			return 9;
		}
		if( strFieldName.equals("InvestmentAmount") ) {
			return 10;
		}
		if( strFieldName.equals("CurrentUnitPrice") ) {
			return 11;
		}
		if( strFieldName.equals("OfferPrice") ) {
			return 12;
		}
		if( strFieldName.equals("ValuationDate") ) {
			return 13;
		}
		if( strFieldName.equals("DealingDate") ) {
			return 14;
		}
		if( strFieldName.equals("ProcessingMode") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("DealDate") ) {
			return 20;
		}
		if( strFieldName.equals("DealTime") ) {
			return 21;
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
				strFieldName = "TransactionNo";
				break;
			case 10:
				strFieldName = "InvestmentAmount";
				break;
			case 11:
				strFieldName = "CurrentUnitPrice";
				break;
			case 12:
				strFieldName = "OfferPrice";
				break;
			case 13:
				strFieldName = "ValuationDate";
				break;
			case 14:
				strFieldName = "DealingDate";
				break;
			case 15:
				strFieldName = "ProcessingMode";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "DealDate";
				break;
			case 21:
				strFieldName = "DealTime";
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
		if( strFieldName.equals("TransactionDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TransactionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionCode") ) {
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
		if( strFieldName.equals("DealingDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ProcessingMode") ) {
			return Schema.TYPE_STRING;
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
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
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
