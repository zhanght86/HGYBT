/*
 * <p>ClassName: AxaMissingInfoForPolicyTempSchema </p>
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
import com.sinosoft.lis.db.AxaMissingInfoForPolicyTempDB;

public class AxaMissingInfoForPolicyTempSchema implements Schema
{
	// @Field
	/** ���� */
	private int Oid;
	/** �������� */
	private int ExtractedDate;
	/** ������ */
	private String PolicyNo;
	/** Ͷ������ */
	private String ApplicationNo;
	/** ������֤������ */
	private String InsuredIdType;
	/** Ͷ����֤������ */
	private String ApplicantIdType;
	/** Ͷ����֤���� */
	private String ApplicantIdNo;
	/** Ͷ���������ʺ� */
	private String ApplicantAcctNo;
	/** �ͻ���� */
	private String CustomerNo;
	/** �������� */
	private String PolicyCancelCode;
	/** �������� */
	private String CancelReason;
	/** Hadprocessed */
	private int HadProcessed;
	/** Becovered */
	private int BeCovered;
	/** �������� */
	private int MakeDate;
	/** ����ʱ�� */
	private String MakeTime;

	public static final int FIELDNUM = 15;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public AxaMissingInfoForPolicyTempSchema()
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

	/** ����<P>��Ʒ���� */
	public int getOid()
	{
		return Oid;
	}
	/** ���� */
	public void setOid(int aOid)
	{
		Oid = aOid;
	}
	/** ����<P>��Ʒ���� */
	public void setOid(String aOid)
	{
		if (aOid != null && !aOid.equals(""))
		{
			Integer tInteger = new Integer(aOid);
			int i = tInteger.intValue();
			Oid = i;
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

	/** ������<P> */
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
	/** Ͷ������<P> */
	public String getApplicationNo()
	{
		if (ApplicationNo != null && !ApplicationNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplicationNo = StrTool.unicodeToGBK(ApplicationNo);
		}
		return ApplicationNo;
	}
	/** Ͷ������ */
	public void setApplicationNo(String aApplicationNo)
	{
		ApplicationNo = aApplicationNo;
	}
	/** ������֤������<P>���������� */
	public String getInsuredIdType()
	{
		if (InsuredIdType != null && !InsuredIdType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIdType = StrTool.unicodeToGBK(InsuredIdType);
		}
		return InsuredIdType;
	}
	/** ������֤������ */
	public void setInsuredIdType(String aInsuredIdType)
	{
		InsuredIdType = aInsuredIdType;
	}
	/** Ͷ����֤������<P> */
	public String getApplicantIdType()
	{
		if (ApplicantIdType != null && !ApplicantIdType.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplicantIdType = StrTool.unicodeToGBK(ApplicantIdType);
		}
		return ApplicantIdType;
	}
	/** Ͷ����֤������ */
	public void setApplicantIdType(String aApplicantIdType)
	{
		ApplicantIdType = aApplicantIdType;
	}
	/** Ͷ����֤����<P> */
	public String getApplicantIdNo()
	{
		if (ApplicantIdNo != null && !ApplicantIdNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplicantIdNo = StrTool.unicodeToGBK(ApplicantIdNo);
		}
		return ApplicantIdNo;
	}
	/** Ͷ����֤���� */
	public void setApplicantIdNo(String aApplicantIdNo)
	{
		ApplicantIdNo = aApplicantIdNo;
	}
	/** Ͷ���������ʺ�<P> */
	public String getApplicantAcctNo()
	{
		if (ApplicantAcctNo != null && !ApplicantAcctNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ApplicantAcctNo = StrTool.unicodeToGBK(ApplicantAcctNo);
		}
		return ApplicantAcctNo;
	}
	/** Ͷ���������ʺ� */
	public void setApplicantAcctNo(String aApplicantAcctNo)
	{
		ApplicantAcctNo = aApplicantAcctNo;
	}
	/** �ͻ����<P> */
	public String getCustomerNo()
	{
		if (CustomerNo != null && !CustomerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			CustomerNo = StrTool.unicodeToGBK(CustomerNo);
		}
		return CustomerNo;
	}
	/** �ͻ���� */
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/** ��������<P> */
	public String getPolicyCancelCode()
	{
		if (PolicyCancelCode != null && !PolicyCancelCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyCancelCode = StrTool.unicodeToGBK(PolicyCancelCode);
		}
		return PolicyCancelCode;
	}
	/** �������� */
	public void setPolicyCancelCode(String aPolicyCancelCode)
	{
		PolicyCancelCode = aPolicyCancelCode;
	}
	/** ��������<P> */
	public String getCancelReason()
	{
		if (CancelReason != null && !CancelReason.equals("") && SysConst.CHANGECHARSET == true)
		{
			CancelReason = StrTool.unicodeToGBK(CancelReason);
		}
		return CancelReason;
	}
	/** �������� */
	public void setCancelReason(String aCancelReason)
	{
		CancelReason = aCancelReason;
	}
	/** Hadprocessed<P> */
	public int getHadProcessed()
	{
		return HadProcessed;
	}
	/** Hadprocessed */
	public void setHadProcessed(int aHadProcessed)
	{
		HadProcessed = aHadProcessed;
	}
	/** Hadprocessed<P> */
	public void setHadProcessed(String aHadProcessed)
	{
		if (aHadProcessed != null && !aHadProcessed.equals(""))
		{
			Integer tInteger = new Integer(aHadProcessed);
			int i = tInteger.intValue();
			HadProcessed = i;
		}
	}

	/** Becovered<P> */
	public int getBeCovered()
	{
		return BeCovered;
	}
	/** Becovered */
	public void setBeCovered(int aBeCovered)
	{
		BeCovered = aBeCovered;
	}
	/** Becovered<P> */
	public void setBeCovered(String aBeCovered)
	{
		if (aBeCovered != null && !aBeCovered.equals(""))
		{
			Integer tInteger = new Integer(aBeCovered);
			int i = tInteger.intValue();
			BeCovered = i;
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

	/**
	* ʹ������һ�� AxaMissingInfoForPolicyTempSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(AxaMissingInfoForPolicyTempSchema aAxaMissingInfoForPolicyTempSchema)
	{
		this.Oid = aAxaMissingInfoForPolicyTempSchema.getOid();
		this.ExtractedDate = aAxaMissingInfoForPolicyTempSchema.getExtractedDate();
		this.PolicyNo = aAxaMissingInfoForPolicyTempSchema.getPolicyNo();
		this.ApplicationNo = aAxaMissingInfoForPolicyTempSchema.getApplicationNo();
		this.InsuredIdType = aAxaMissingInfoForPolicyTempSchema.getInsuredIdType();
		this.ApplicantIdType = aAxaMissingInfoForPolicyTempSchema.getApplicantIdType();
		this.ApplicantIdNo = aAxaMissingInfoForPolicyTempSchema.getApplicantIdNo();
		this.ApplicantAcctNo = aAxaMissingInfoForPolicyTempSchema.getApplicantAcctNo();
		this.CustomerNo = aAxaMissingInfoForPolicyTempSchema.getCustomerNo();
		this.PolicyCancelCode = aAxaMissingInfoForPolicyTempSchema.getPolicyCancelCode();
		this.CancelReason = aAxaMissingInfoForPolicyTempSchema.getCancelReason();
		this.HadProcessed = aAxaMissingInfoForPolicyTempSchema.getHadProcessed();
		this.BeCovered = aAxaMissingInfoForPolicyTempSchema.getBeCovered();
		this.MakeDate = aAxaMissingInfoForPolicyTempSchema.getMakeDate();
		this.MakeTime = aAxaMissingInfoForPolicyTempSchema.getMakeTime();
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
			this.ExtractedDate = rs.getInt("ExtractedDate");
			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("ApplicationNo") == null )
				this.ApplicationNo = null;
			else
				this.ApplicationNo = rs.getString("ApplicationNo").trim();

			if( rs.getString("InsuredIdType") == null )
				this.InsuredIdType = null;
			else
				this.InsuredIdType = rs.getString("InsuredIdType").trim();

			if( rs.getString("ApplicantIdType") == null )
				this.ApplicantIdType = null;
			else
				this.ApplicantIdType = rs.getString("ApplicantIdType").trim();

			if( rs.getString("ApplicantIdNo") == null )
				this.ApplicantIdNo = null;
			else
				this.ApplicantIdNo = rs.getString("ApplicantIdNo").trim();

			if( rs.getString("ApplicantAcctNo") == null )
				this.ApplicantAcctNo = null;
			else
				this.ApplicantAcctNo = rs.getString("ApplicantAcctNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("PolicyCancelCode") == null )
				this.PolicyCancelCode = null;
			else
				this.PolicyCancelCode = rs.getString("PolicyCancelCode").trim();

			if( rs.getString("CancelReason") == null )
				this.CancelReason = null;
			else
				this.CancelReason = rs.getString("CancelReason").trim();

			this.HadProcessed = rs.getInt("HadProcessed");
			this.BeCovered = rs.getInt("BeCovered");
			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "AxaMissingInfoForPolicyTempSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AxaMissingInfoForPolicyTempSchema getSchema()
	{
		AxaMissingInfoForPolicyTempSchema aAxaMissingInfoForPolicyTempSchema = new AxaMissingInfoForPolicyTempSchema();
		aAxaMissingInfoForPolicyTempSchema.setSchema(this);
		return aAxaMissingInfoForPolicyTempSchema;
	}

	public AxaMissingInfoForPolicyTempDB getDB()
	{
		AxaMissingInfoForPolicyTempDB aDBOper = new AxaMissingInfoForPolicyTempDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpAxaMissingInfoForPolicyTemp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(Oid) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplicationNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIdType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplicantIdType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplicantIdNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ApplicantAcctNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CustomerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyCancelCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CancelReason) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(HadProcessed) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BeCovered) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) );
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpAxaMissingInfoForPolicyTemp>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Oid= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ApplicationNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InsuredIdType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ApplicantIdType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ApplicantIdNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ApplicantAcctNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PolicyCancelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CancelReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HadProcessed= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			BeCovered= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "AxaMissingInfoForPolicyTempSchema";
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
		if (FCode.equals("ExtractedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtractedDate));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("ApplicationNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicationNo));
		}
		if (FCode.equals("InsuredIdType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIdType));
		}
		if (FCode.equals("ApplicantIdType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicantIdType));
		}
		if (FCode.equals("ApplicantIdNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicantIdNo));
		}
		if (FCode.equals("ApplicantAcctNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplicantAcctNo));
		}
		if (FCode.equals("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equals("PolicyCancelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCancelCode));
		}
		if (FCode.equals("CancelReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancelReason));
		}
		if (FCode.equals("HadProcessed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HadProcessed));
		}
		if (FCode.equals("BeCovered"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeCovered));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = String.valueOf(ExtractedDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ApplicationNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InsuredIdType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ApplicantIdType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ApplicantIdNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ApplicantAcctNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PolicyCancelCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CancelReason);
				break;
			case 11:
				strFieldValue = String.valueOf(HadProcessed);
				break;
			case 12:
				strFieldValue = String.valueOf(BeCovered);
				break;
			case 13:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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
		if (FCode.equals("ApplicationNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplicationNo = FValue.trim();
			}
			else
				ApplicationNo = null;
		}
		if (FCode.equals("InsuredIdType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIdType = FValue.trim();
			}
			else
				InsuredIdType = null;
		}
		if (FCode.equals("ApplicantIdType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplicantIdType = FValue.trim();
			}
			else
				ApplicantIdType = null;
		}
		if (FCode.equals("ApplicantIdNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplicantIdNo = FValue.trim();
			}
			else
				ApplicantIdNo = null;
		}
		if (FCode.equals("ApplicantAcctNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplicantAcctNo = FValue.trim();
			}
			else
				ApplicantAcctNo = null;
		}
		if (FCode.equals("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equals("PolicyCancelCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCancelCode = FValue.trim();
			}
			else
				PolicyCancelCode = null;
		}
		if (FCode.equals("CancelReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancelReason = FValue.trim();
			}
			else
				CancelReason = null;
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
		if (FCode.equals("BeCovered"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BeCovered = i;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AxaMissingInfoForPolicyTempSchema other = (AxaMissingInfoForPolicyTempSchema)otherObject;
		return
			Oid == other.getOid()
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& ApplicationNo.equals(other.getApplicationNo())
			&& InsuredIdType.equals(other.getInsuredIdType())
			&& ApplicantIdType.equals(other.getApplicantIdType())
			&& ApplicantIdNo.equals(other.getApplicantIdNo())
			&& ApplicantAcctNo.equals(other.getApplicantAcctNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& PolicyCancelCode.equals(other.getPolicyCancelCode())
			&& CancelReason.equals(other.getCancelReason())
			&& HadProcessed == other.getHadProcessed()
			&& BeCovered == other.getBeCovered()
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("ExtractedDate") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("ApplicationNo") ) {
			return 3;
		}
		if( strFieldName.equals("InsuredIdType") ) {
			return 4;
		}
		if( strFieldName.equals("ApplicantIdType") ) {
			return 5;
		}
		if( strFieldName.equals("ApplicantIdNo") ) {
			return 6;
		}
		if( strFieldName.equals("ApplicantAcctNo") ) {
			return 7;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 8;
		}
		if( strFieldName.equals("PolicyCancelCode") ) {
			return 9;
		}
		if( strFieldName.equals("CancelReason") ) {
			return 10;
		}
		if( strFieldName.equals("HadProcessed") ) {
			return 11;
		}
		if( strFieldName.equals("BeCovered") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
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
				strFieldName = "ExtractedDate";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "ApplicationNo";
				break;
			case 4:
				strFieldName = "InsuredIdType";
				break;
			case 5:
				strFieldName = "ApplicantIdType";
				break;
			case 6:
				strFieldName = "ApplicantIdNo";
				break;
			case 7:
				strFieldName = "ApplicantAcctNo";
				break;
			case 8:
				strFieldName = "CustomerNo";
				break;
			case 9:
				strFieldName = "PolicyCancelCode";
				break;
			case 10:
				strFieldName = "CancelReason";
				break;
			case 11:
				strFieldName = "HadProcessed";
				break;
			case 12:
				strFieldName = "BeCovered";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("ExtractedDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicationNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIdType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicantIdType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicantIdNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplicantAcctNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCancelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CancelReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HadProcessed") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BeCovered") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
