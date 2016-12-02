/*
 * <p>ClassName: IPCOM_CITISchema </p>
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
import com.sinosoft.lis.db.IPCOM_CITIDB;

public class IPCOM_CITISchema implements Schema
{
	// @Field
	/** ��Ʒ���� */
	private String RiskCode;
	/** ������� */
	private String AgentCom;
	/** �ɷ����� */
	private String PayEndYear;
	/** ������� */
	private int PolicyYear;
	/** �������� */
	private String InsuredYear;
	/** ���� */
	private double PremRate;
	/** ���㵥λ */
	private int PremScale;

	public static final int FIELDNUM = 7;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public IPCOM_CITISchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "PayEndYear";
		pk[2] = "PolicyYear";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** ��Ʒ����<P>��???���� */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** ��Ʒ���� */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** �������<P> */
	public String getAgentCom()
	{
		if (AgentCom != null && !AgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCom = StrTool.unicodeToGBK(AgentCom);
		}
		return AgentCom;
	}
	/** ������� */
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/** �ɷ�����<P> */
	public String getPayEndYear()
	{
		if (PayEndYear != null && !PayEndYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYear = StrTool.unicodeToGBK(PayEndYear);
		}
		return PayEndYear;
	}
	/** �ɷ����� */
	public void setPayEndYear(String aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** �������<P> */
	public int getPolicyYear()
	{
		return PolicyYear;
	}
	/** ������� */
	public void setPolicyYear(int aPolicyYear)
	{
		PolicyYear = aPolicyYear;
	}
	/** �������<P> */
	public void setPolicyYear(String aPolicyYear)
	{
		if (aPolicyYear != null && !aPolicyYear.equals(""))
		{
			Integer tInteger = new Integer(aPolicyYear);
			int i = tInteger.intValue();
			PolicyYear = i;
		}
	}

	/** ��������<P> */
	public String getInsuredYear()
	{
		if (InsuredYear != null && !InsuredYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredYear = StrTool.unicodeToGBK(InsuredYear);
		}
		return InsuredYear;
	}
	/** �������� */
	public void setInsuredYear(String aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	/** ����<P> */
	public double getPremRate()
	{
		return PremRate;
	}
	/** ���� */
	public void setPremRate(double aPremRate)
	{
		PremRate = aPremRate;
	}
	/** ����<P> */
	public void setPremRate(String aPremRate)
	{
		if (aPremRate != null && !aPremRate.equals(""))
		{
			Double tDouble = new Double(aPremRate);
			double d = tDouble.doubleValue();
			PremRate = d;
		}
	}

	/** ���㵥λ<P> */
	public int getPremScale()
	{
		return PremScale;
	}
	/** ���㵥λ */
	public void setPremScale(int aPremScale)
	{
		PremScale = aPremScale;
	}
	/** ���㵥λ<P> */
	public void setPremScale(String aPremScale)
	{
		if (aPremScale != null && !aPremScale.equals(""))
		{
			Integer tInteger = new Integer(aPremScale);
			int i = tInteger.intValue();
			PremScale = i;
		}
	}


	/**
	* ʹ������һ�� IPCOM_CITISchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(IPCOM_CITISchema aIPCOM_CITISchema)
	{
		this.RiskCode = aIPCOM_CITISchema.getRiskCode();
		this.AgentCom = aIPCOM_CITISchema.getAgentCom();
		this.PayEndYear = aIPCOM_CITISchema.getPayEndYear();
		this.PolicyYear = aIPCOM_CITISchema.getPolicyYear();
		this.InsuredYear = aIPCOM_CITISchema.getInsuredYear();
		this.PremRate = aIPCOM_CITISchema.getPremRate();
		this.PremScale = aIPCOM_CITISchema.getPremScale();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("PayEndYear") == null )
				this.PayEndYear = null;
			else
				this.PayEndYear = rs.getString("PayEndYear").trim();

			this.PolicyYear = rs.getInt("PolicyYear");
			if( rs.getString("InsuredYear") == null )
				this.InsuredYear = null;
			else
				this.InsuredYear = rs.getString("InsuredYear").trim();

			this.PremRate = rs.getDouble("PremRate");
			this.PremScale = rs.getInt("PremScale");
		}
		catch(SQLException sqle)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "IPCOM_CITISchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public IPCOM_CITISchema getSchema()
	{
		IPCOM_CITISchema aIPCOM_CITISchema = new IPCOM_CITISchema();
		aIPCOM_CITISchema.setSchema(this);
		return aIPCOM_CITISchema;
	}

	public IPCOM_CITIDB getDB()
	{
		IPCOM_CITIDB aDBOper = new IPCOM_CITIDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpIPCOM_CITI����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PayEndYear) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PolicyYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredYear) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PremRate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PremScale);
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpIPCOM_CITI>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayEndYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolicyYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			InsuredYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			PremScale= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "IPCOM_CITISchema";
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
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equals("PolicyYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyYear));
		}
		if (FCode.equals("InsuredYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredYear));
		}
		if (FCode.equals("PremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremRate));
		}
		if (FCode.equals("PremScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremScale));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayEndYear);
				break;
			case 3:
				strFieldValue = String.valueOf(PolicyYear);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InsuredYear);
				break;
			case 5:
				strFieldValue = String.valueOf(PremRate);
				break;
			case 6:
				strFieldValue = String.valueOf(PremScale);
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

		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equals("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equals("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYear = FValue.trim();
			}
			else
				PayEndYear = null;
		}
		if (FCode.equals("PolicyYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolicyYear = i;
			}
		}
		if (FCode.equals("InsuredYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredYear = FValue.trim();
			}
			else
				InsuredYear = null;
		}
		if (FCode.equals("PremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremRate = d;
			}
		}
		if (FCode.equals("PremScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PremScale = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		IPCOM_CITISchema other = (IPCOM_CITISchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& AgentCom.equals(other.getAgentCom())
			&& PayEndYear.equals(other.getPayEndYear())
			&& PolicyYear == other.getPolicyYear()
			&& InsuredYear.equals(other.getInsuredYear())
			&& PremRate == other.getPremRate()
			&& PremScale == other.getPremScale();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 1;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 2;
		}
		if( strFieldName.equals("PolicyYear") ) {
			return 3;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return 4;
		}
		if( strFieldName.equals("PremRate") ) {
			return 5;
		}
		if( strFieldName.equals("PremScale") ) {
			return 6;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "AgentCom";
				break;
			case 2:
				strFieldName = "PayEndYear";
				break;
			case 3:
				strFieldName = "PolicyYear";
				break;
			case 4:
				strFieldName = "InsuredYear";
				break;
			case 5:
				strFieldName = "PremRate";
				break;
			case 6:
				strFieldName = "PremScale";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremScale") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
