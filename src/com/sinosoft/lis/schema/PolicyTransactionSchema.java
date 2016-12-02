/*
 * <p>ClassName: PolicyTransactionSchema </p>
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
import com.sinosoft.lis.db.PolicyTransactionDB;

public class PolicyTransactionSchema implements Schema
{
	// @Field
	/** ��ˮ�� */
	private String SerialNo;
	/** ��ȡ���� */
	private int ExtractedDate;
	/** �������к� */
	private String TransactionSeqNo;
	/** ���������к� */
	private String TransactionSubSeqNo;
	/** ������ */
	private String PolicyNo;
	/** ���ִ��� */
	private String PlanCode;
	/** �������� */
	private String PlanType;
	/** �������� */
	private int TransactionDate;
	/** �������� */
	private String TransactionType;
	/** �ӽ������� */
	private String SubTransactionType;
	/** ���׽�� */
	private double TransactionAmount;
	/** �ڽɶ�??Ͷ�ʱ��� */
	private double ModalRegularPremium;
	/** ���ڶ���Ͷ�ʱ��� */
	private double RegularTopUpPremium;
	/** �����ܼ� */
	private double LumpSumPremium;
	/** ���ڱ����ܼ� */
	private double InitLumpSumPremium;
	/** Monthscovered */
	private String MonthsCovered;
	/** ������� */
	private String PolicyYear;
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
	/** �������� */
	private int DealDate;
	/** ����ʱ�� */
	private String DealTime;

	public static final int FIELDNUM = 24;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public PolicyTransactionSchema()
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
	/** ��ȡ����<P> */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** ��ȡ���� */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** ��ȡ����<P> */
	public void setExtractedDate(String aExtractedDate)
	{
		if (aExtractedDate != null && !aExtractedDate.equals(""))
		{
			Integer tInteger = new Integer(aExtractedDate);
			int i = tInteger.intValue();
			ExtractedDate = i;
		}
	}

	/** �������к�<P> */
	public String getTransactionSeqNo()
	{
		if (TransactionSeqNo != null && !TransactionSeqNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionSeqNo = StrTool.unicodeToGBK(TransactionSeqNo);
		}
		return TransactionSeqNo;
	}
	/** �������к� */
	public void setTransactionSeqNo(String aTransactionSeqNo)
	{
		TransactionSeqNo = aTransactionSeqNo;
	}
	/** ���������к�<P> */
	public String getTransactionSubSeqNo()
	{
		if (TransactionSubSeqNo != null && !TransactionSubSeqNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionSubSeqNo = StrTool.unicodeToGBK(TransactionSubSeqNo);
		}
		return TransactionSubSeqNo;
	}
	/** ���������к� */
	public void setTransactionSubSeqNo(String aTransactionSubSeqNo)
	{
		TransactionSubSeqNo = aTransactionSubSeqNo;
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
	/** ���ִ���<P> */
	public String getPlanCode()
	{
		if (PlanCode != null && !PlanCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			PlanCode = StrTool.unicodeToGBK(PlanCode);
		}
		return PlanCode;
	}
	/** ���ִ��� */
	public void setPlanCode(String aPlanCode)
	{
		PlanCode = aPlanCode;
	}
	/** ��������<P> */
	public String getPlanType()
	{
		if (PlanType != null && !PlanType.equals("") && SysConst.CHANGECHARSET == true)
		{
			PlanType = StrTool.unicodeToGBK(PlanType);
		}
		return PlanType;
	}
	/** �������� */
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
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
	/** �ӽ�������<P> */
	public String getSubTransactionType()
	{
		if (SubTransactionType != null && !SubTransactionType.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubTransactionType = StrTool.unicodeToGBK(SubTransactionType);
		}
		return SubTransactionType;
	}
	/** �ӽ������� */
	public void setSubTransactionType(String aSubTransactionType)
	{
		SubTransactionType = aSubTransactionType;
	}
	/** ���׽��<P> */
	public double getTransactionAmount()
	{
		return TransactionAmount;
	}
	/** ���׽�� */
	public void setTransactionAmount(double aTransactionAmount)
	{
		TransactionAmount = aTransactionAmount;
	}
	/** ���׽��<P> */
	public void setTransactionAmount(String aTransactionAmount)
	{
		if (aTransactionAmount != null && !aTransactionAmount.equals(""))
		{
			Double tDouble = new Double(aTransactionAmount);
			double d = tDouble.doubleValue();
			TransactionAmount = d;
		}
	}

	/** �ڽɶ�??Ͷ�ʱ���<P> */
	public double getModalRegularPremium()
	{
		return ModalRegularPremium;
	}
	/** �ڽɶ�??Ͷ�ʱ��� */
	public void setModalRegularPremium(double aModalRegularPremium)
	{
		ModalRegularPremium = aModalRegularPremium;
	}
	/** �ڽɶ�??Ͷ�ʱ���<P> */
	public void setModalRegularPremium(String aModalRegularPremium)
	{
		if (aModalRegularPremium != null && !aModalRegularPremium.equals(""))
		{
			Double tDouble = new Double(aModalRegularPremium);
			double d = tDouble.doubleValue();
			ModalRegularPremium = d;
		}
	}

	/** ���ڶ���Ͷ�ʱ���<P> */
	public double getRegularTopUpPremium()
	{
		return RegularTopUpPremium;
	}
	/** ���ڶ���Ͷ�ʱ��� */
	public void setRegularTopUpPremium(double aRegularTopUpPremium)
	{
		RegularTopUpPremium = aRegularTopUpPremium;
	}
	/** ���ڶ���Ͷ�ʱ���<P> */
	public void setRegularTopUpPremium(String aRegularTopUpPremium)
	{
		if (aRegularTopUpPremium != null && !aRegularTopUpPremium.equals(""))
		{
			Double tDouble = new Double(aRegularTopUpPremium);
			double d = tDouble.doubleValue();
			RegularTopUpPremium = d;
		}
	}

	/** �����ܼ�<P> */
	public double getLumpSumPremium()
	{
		return LumpSumPremium;
	}
	/** �����ܼ� */
	public void setLumpSumPremium(double aLumpSumPremium)
	{
		LumpSumPremium = aLumpSumPremium;
	}
	/** �����ܼ�<P> */
	public void setLumpSumPremium(String aLumpSumPremium)
	{
		if (aLumpSumPremium != null && !aLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(aLumpSumPremium);
			double d = tDouble.doubleValue();
			LumpSumPremium = d;
		}
	}

	/** ���ڱ����ܼ�<P> */
	public double getInitLumpSumPremium()
	{
		return InitLumpSumPremium;
	}
	/** ���ڱ����ܼ� */
	public void setInitLumpSumPremium(double aInitLumpSumPremium)
	{
		InitLumpSumPremium = aInitLumpSumPremium;
	}
	/** ���ڱ����ܼ�<P> */
	public void setInitLumpSumPremium(String aInitLumpSumPremium)
	{
		if (aInitLumpSumPremium != null && !aInitLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(aInitLumpSumPremium);
			double d = tDouble.doubleValue();
			InitLumpSumPremium = d;
		}
	}

	/** Monthscovered<P> */
	public String getMonthsCovered()
	{
		if (MonthsCovered != null && !MonthsCovered.equals("") && SysConst.CHANGECHARSET == true)
		{
			MonthsCovered = StrTool.unicodeToGBK(MonthsCovered);
		}
		return MonthsCovered;
	}
	/** Monthscovered */
	public void setMonthsCovered(String aMonthsCovered)
	{
		MonthsCovered = aMonthsCovered;
	}
	/** �������<P> */
	public String getPolicyYear()
	{
		if (PolicyYear != null && !PolicyYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyYear = StrTool.unicodeToGBK(PolicyYear);
		}
		return PolicyYear;
	}
	/** ������� */
	public void setPolicyYear(String aPolicyYear)
	{
		PolicyYear = aPolicyYear;
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
	/** ��������<P> */
	public int getDealDate()
	{
		return DealDate;
	}
	/** �������� */
	public void setDealDate(int aDealDate)
	{
		DealDate = aDealDate;
	}
	/** ��������<P> */
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals(""))
		{
			Integer tInteger = new Integer(aDealDate);
			int i = tInteger.intValue();
			DealDate = i;
		}
	}

	/** ����ʱ��<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** ����ʱ�� */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* ʹ������һ�� PolicyTransactionSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(PolicyTransactionSchema aPolicyTransactionSchema)
	{
		this.SerialNo = aPolicyTransactionSchema.getSerialNo();
		this.ExtractedDate = aPolicyTransactionSchema.getExtractedDate();
		this.TransactionSeqNo = aPolicyTransactionSchema.getTransactionSeqNo();
		this.TransactionSubSeqNo = aPolicyTransactionSchema.getTransactionSubSeqNo();
		this.PolicyNo = aPolicyTransactionSchema.getPolicyNo();
		this.PlanCode = aPolicyTransactionSchema.getPlanCode();
		this.PlanType = aPolicyTransactionSchema.getPlanType();
		this.TransactionDate = aPolicyTransactionSchema.getTransactionDate();
		this.TransactionType = aPolicyTransactionSchema.getTransactionType();
		this.SubTransactionType = aPolicyTransactionSchema.getSubTransactionType();
		this.TransactionAmount = aPolicyTransactionSchema.getTransactionAmount();
		this.ModalRegularPremium = aPolicyTransactionSchema.getModalRegularPremium();
		this.RegularTopUpPremium = aPolicyTransactionSchema.getRegularTopUpPremium();
		this.LumpSumPremium = aPolicyTransactionSchema.getLumpSumPremium();
		this.InitLumpSumPremium = aPolicyTransactionSchema.getInitLumpSumPremium();
		this.MonthsCovered = aPolicyTransactionSchema.getMonthsCovered();
		this.PolicyYear = aPolicyTransactionSchema.getPolicyYear();
		this.ProcessingMode = aPolicyTransactionSchema.getProcessingMode();
		this.MakeDate = aPolicyTransactionSchema.getMakeDate();
		this.MakeTime = aPolicyTransactionSchema.getMakeTime();
		this.ModifyDate = aPolicyTransactionSchema.getModifyDate();
		this.ModifyTime = aPolicyTransactionSchema.getModifyTime();
		this.DealDate = aPolicyTransactionSchema.getDealDate();
		this.DealTime = aPolicyTransactionSchema.getDealTime();
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
			if( rs.getString("TransactionSeqNo") == null )
				this.TransactionSeqNo = null;
			else
				this.TransactionSeqNo = rs.getString("TransactionSeqNo").trim();

			if( rs.getString("TransactionSubSeqNo") == null )
				this.TransactionSubSeqNo = null;
			else
				this.TransactionSubSeqNo = rs.getString("TransactionSubSeqNo").trim();

			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			this.TransactionDate = rs.getInt("TransactionDate");
			if( rs.getString("TransactionType") == null )
				this.TransactionType = null;
			else
				this.TransactionType = rs.getString("TransactionType").trim();

			if( rs.getString("SubTransactionType") == null )
				this.SubTransactionType = null;
			else
				this.SubTransactionType = rs.getString("SubTransactionType").trim();

			this.TransactionAmount = rs.getDouble("TransactionAmount");
			this.ModalRegularPremium = rs.getDouble("ModalRegularPremium");
			this.RegularTopUpPremium = rs.getDouble("RegularTopUpPremium");
			this.LumpSumPremium = rs.getDouble("LumpSumPremium");
			this.InitLumpSumPremium = rs.getDouble("InitLumpSumPremium");
			if( rs.getString("MonthsCovered") == null )
				this.MonthsCovered = null;
			else
				this.MonthsCovered = rs.getString("MonthsCovered").trim();

			if( rs.getString("PolicyYear") == null )
				this.PolicyYear = null;
			else
				this.PolicyYear = rs.getString("PolicyYear").trim();

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
			tError.moduleName = "PolicyTransactionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public PolicyTransactionSchema getSchema()
	{
		PolicyTransactionSchema aPolicyTransactionSchema = new PolicyTransactionSchema();
		aPolicyTransactionSchema.setSchema(this);
		return aPolicyTransactionSchema;
	}

	public PolicyTransactionDB getDB()
	{
		PolicyTransactionDB aDBOper = new PolicyTransactionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyTransaction����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionSeqNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionSubSeqNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PlanCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PlanType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubTransactionType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionAmount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModalRegularPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RegularTopUpPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LumpSumPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InitLumpSumPremium) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MonthsCovered) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyYear) ) + SysConst.PACKAGESPILTER
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
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyTransaction>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			TransactionSeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TransactionSubSeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			TransactionDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			TransactionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SubTransactionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TransactionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			ModalRegularPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			RegularTopUpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			LumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			InitLumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			MonthsCovered = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PolicyYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ProcessingMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "PolicyTransactionSchema";
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
		if (FCode.equals("TransactionSeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionSeqNo));
		}
		if (FCode.equals("TransactionSubSeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionSubSeqNo));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equals("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equals("TransactionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDate));
		}
		if (FCode.equals("TransactionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionType));
		}
		if (FCode.equals("SubTransactionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubTransactionType));
		}
		if (FCode.equals("TransactionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionAmount));
		}
		if (FCode.equals("ModalRegularPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModalRegularPremium));
		}
		if (FCode.equals("RegularTopUpPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegularTopUpPremium));
		}
		if (FCode.equals("LumpSumPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LumpSumPremium));
		}
		if (FCode.equals("InitLumpSumPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitLumpSumPremium));
		}
		if (FCode.equals("MonthsCovered"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonthsCovered));
		}
		if (FCode.equals("PolicyYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyYear));
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
				strFieldValue = StrTool.GBKToUnicode(TransactionSeqNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TransactionSubSeqNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 7:
				strFieldValue = String.valueOf(TransactionDate);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TransactionType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SubTransactionType);
				break;
			case 10:
				strFieldValue = String.valueOf(TransactionAmount);
				break;
			case 11:
				strFieldValue = String.valueOf(ModalRegularPremium);
				break;
			case 12:
				strFieldValue = String.valueOf(RegularTopUpPremium);
				break;
			case 13:
				strFieldValue = String.valueOf(LumpSumPremium);
				break;
			case 14:
				strFieldValue = String.valueOf(InitLumpSumPremium);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MonthsCovered);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PolicyYear);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ProcessingMode);
				break;
			case 18:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equals("TransactionSeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionSeqNo = FValue.trim();
			}
			else
				TransactionSeqNo = null;
		}
		if (FCode.equals("TransactionSubSeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionSubSeqNo = FValue.trim();
			}
			else
				TransactionSubSeqNo = null;
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
		if (FCode.equals("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
		}
		if (FCode.equals("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
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
		if (FCode.equals("SubTransactionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubTransactionType = FValue.trim();
			}
			else
				SubTransactionType = null;
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
		if (FCode.equals("ModalRegularPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ModalRegularPremium = d;
			}
		}
		if (FCode.equals("RegularTopUpPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RegularTopUpPremium = d;
			}
		}
		if (FCode.equals("LumpSumPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LumpSumPremium = d;
			}
		}
		if (FCode.equals("InitLumpSumPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitLumpSumPremium = d;
			}
		}
		if (FCode.equals("MonthsCovered"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonthsCovered = FValue.trim();
			}
			else
				MonthsCovered = null;
		}
		if (FCode.equals("PolicyYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyYear = FValue.trim();
			}
			else
				PolicyYear = null;
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
		PolicyTransactionSchema other = (PolicyTransactionSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ExtractedDate == other.getExtractedDate()
			&& TransactionSeqNo.equals(other.getTransactionSeqNo())
			&& TransactionSubSeqNo.equals(other.getTransactionSubSeqNo())
			&& PolicyNo.equals(other.getPolicyNo())
			&& PlanCode.equals(other.getPlanCode())
			&& PlanType.equals(other.getPlanType())
			&& TransactionDate == other.getTransactionDate()
			&& TransactionType.equals(other.getTransactionType())
			&& SubTransactionType.equals(other.getSubTransactionType())
			&& TransactionAmount == other.getTransactionAmount()
			&& ModalRegularPremium == other.getModalRegularPremium()
			&& RegularTopUpPremium == other.getRegularTopUpPremium()
			&& LumpSumPremium == other.getLumpSumPremium()
			&& InitLumpSumPremium == other.getInitLumpSumPremium()
			&& MonthsCovered.equals(other.getMonthsCovered())
			&& PolicyYear.equals(other.getPolicyYear())
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
		if( strFieldName.equals("TransactionSeqNo") ) {
			return 2;
		}
		if( strFieldName.equals("TransactionSubSeqNo") ) {
			return 3;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 4;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("PlanType") ) {
			return 6;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return 7;
		}
		if( strFieldName.equals("TransactionType") ) {
			return 8;
		}
		if( strFieldName.equals("SubTransactionType") ) {
			return 9;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return 10;
		}
		if( strFieldName.equals("ModalRegularPremium") ) {
			return 11;
		}
		if( strFieldName.equals("RegularTopUpPremium") ) {
			return 12;
		}
		if( strFieldName.equals("LumpSumPremium") ) {
			return 13;
		}
		if( strFieldName.equals("InitLumpSumPremium") ) {
			return 14;
		}
		if( strFieldName.equals("MonthsCovered") ) {
			return 15;
		}
		if( strFieldName.equals("PolicyYear") ) {
			return 16;
		}
		if( strFieldName.equals("ProcessingMode") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "TransactionSeqNo";
				break;
			case 3:
				strFieldName = "TransactionSubSeqNo";
				break;
			case 4:
				strFieldName = "PolicyNo";
				break;
			case 5:
				strFieldName = "PlanCode";
				break;
			case 6:
				strFieldName = "PlanType";
				break;
			case 7:
				strFieldName = "TransactionDate";
				break;
			case 8:
				strFieldName = "TransactionType";
				break;
			case 9:
				strFieldName = "SubTransactionType";
				break;
			case 10:
				strFieldName = "TransactionAmount";
				break;
			case 11:
				strFieldName = "ModalRegularPremium";
				break;
			case 12:
				strFieldName = "RegularTopUpPremium";
				break;
			case 13:
				strFieldName = "LumpSumPremium";
				break;
			case 14:
				strFieldName = "InitLumpSumPremium";
				break;
			case 15:
				strFieldName = "MonthsCovered";
				break;
			case 16:
				strFieldName = "PolicyYear";
				break;
			case 17:
				strFieldName = "ProcessingMode";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("TransactionSeqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionSubSeqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TransactionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubTransactionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ModalRegularPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RegularTopUpPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LumpSumPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitLumpSumPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MonthsCovered") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyYear") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
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
