/*
 * <p>ClassName: LMSpecContentSchema </p>
 * <p>Description: DB�� Schema ���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �˱����
 * @CreateDate��2012-06-06
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMSpecContentDB;

public class LMSpecContentSchema implements Schema
{
	// @Field
	/** ���ֱ��� */
	private String RiskCode;
	/** �ر�Լ�� */
	private String line1;
	/** �ڶ��� */
	private String line2;
	/** ������ */
	private String line3;
	/** ������ */
	private String line4;
	/** ������ */
	private String line5;
	/** ������ */
	private String line6;
	/** ԭԼ���ֶ� */
	private String SpecContent;

	public static final int FIELDNUM = 8;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public LMSpecContentSchema()
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

	/** ���ֱ���<P> */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** ���ֱ��� */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** �ر�Լ��<P> */
	public String getline1()
	{
		if (line1 != null && !line1.equals("") && SysConst.CHANGECHARSET == true)
		{
			line1 = StrTool.unicodeToGBK(line1);
		}
		return line1;
	}
	/** �ر�Լ�� */
	public void setline1(String aline1)
	{
		line1 = aline1;
	}
	/** �ڶ���<P> */
	public String getline2()
	{
		if (line2 != null && !line2.equals("") && SysConst.CHANGECHARSET == true)
		{
			line2 = StrTool.unicodeToGBK(line2);
		}
		return line2;
	}
	/** �ڶ��� */
	public void setline2(String aline2)
	{
		line2 = aline2;
	}
	/** ������<P> */
	public String getline3()
	{
		if (line3 != null && !line3.equals("") && SysConst.CHANGECHARSET == true)
		{
			line3 = StrTool.unicodeToGBK(line3);
		}
		return line3;
	}
	/** ������ */
	public void setline3(String aline3)
	{
		line3 = aline3;
	}
	/** ������<P> */
	public String getline4()
	{
		if (line4 != null && !line4.equals("") && SysConst.CHANGECHARSET == true)
		{
			line4 = StrTool.unicodeToGBK(line4);
		}
		return line4;
	}
	/** ������ */
	public void setline4(String aline4)
	{
		line4 = aline4;
	}
	/** ������<P> */
	public String getline5()
	{
		if (line5 != null && !line5.equals("") && SysConst.CHANGECHARSET == true)
		{
			line5 = StrTool.unicodeToGBK(line5);
		}
		return line5;
	}
	/** ������ */
	public void setline5(String aline5)
	{
		line5 = aline5;
	}
	/** ������<P> */
	public String getline6()
	{
		if (line6 != null && !line6.equals("") && SysConst.CHANGECHARSET == true)
		{
			line6 = StrTool.unicodeToGBK(line6);
		}
		return line6;
	}
	/** ������ */
	public void setline6(String aline6)
	{
		line6 = aline6;
	}
	/** ԭԼ���ֶ�<P> */
	public String getSpecContent()
	{
		if (SpecContent != null && !SpecContent.equals("") && SysConst.CHANGECHARSET == true)
		{
			SpecContent = StrTool.unicodeToGBK(SpecContent);
		}
		return SpecContent;
	}
	/** ԭԼ���ֶ� */
	public void setSpecContent(String aSpecContent)
	{
		SpecContent = aSpecContent;
	}

	/**
	* ʹ������һ�� LMSpecContentSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(LMSpecContentSchema aLMSpecContentSchema)
	{
		this.RiskCode = aLMSpecContentSchema.getRiskCode();
		this.line1 = aLMSpecContentSchema.getline1();
		this.line2 = aLMSpecContentSchema.getline2();
		this.line3 = aLMSpecContentSchema.getline3();
		this.line4 = aLMSpecContentSchema.getline4();
		this.line5 = aLMSpecContentSchema.getline5();
		this.line6 = aLMSpecContentSchema.getline6();
		this.SpecContent = aLMSpecContentSchema.getSpecContent();
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

			if( rs.getString("line1") == null )
				this.line1 = null;
			else
				this.line1 = rs.getString("line1").trim();

			if( rs.getString("line2") == null )
				this.line2 = null;
			else
				this.line2 = rs.getString("line2").trim();

			if( rs.getString("line3") == null )
				this.line3 = null;
			else
				this.line3 = rs.getString("line3").trim();

			if( rs.getString("line4") == null )
				this.line4 = null;
			else
				this.line4 = rs.getString("line4").trim();

			if( rs.getString("line5") == null )
				this.line5 = null;
			else
				this.line5 = rs.getString("line5").trim();

			if( rs.getString("line6") == null )
				this.line6 = null;
			else
				this.line6 = rs.getString("line6").trim();

			if( rs.getString("SpecContent") == null )
				this.SpecContent = null;
			else
				this.SpecContent = rs.getString("SpecContent").trim();

		}
		catch(SQLException sqle)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "LMSpecContentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMSpecContentSchema getSchema()
	{
		LMSpecContentSchema aLMSpecContentSchema = new LMSpecContentSchema();
		aLMSpecContentSchema.setSchema(this);
		return aLMSpecContentSchema;
	}

	public LMSpecContentDB getDB()
	{
		LMSpecContentDB aDBOper = new LMSpecContentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMSpecContent����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(line6) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SpecContent) );
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMSpecContent>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			line1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			line2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			line3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			line4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			line5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			line6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SpecContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "LMSpecContentSchema";
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
		if (FCode.equals("line1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line1));
		}
		if (FCode.equals("line2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line2));
		}
		if (FCode.equals("line3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line3));
		}
		if (FCode.equals("line4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line4));
		}
		if (FCode.equals("line5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line5));
		}
		if (FCode.equals("line6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(line6));
		}
		if (FCode.equals("SpecContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecContent));
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
				strFieldValue = StrTool.GBKToUnicode(line1);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(line2);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(line3);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(line4);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(line5);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(line6);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SpecContent);
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
		if (FCode.equals("line1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line1 = FValue.trim();
			}
			else
				line1 = null;
		}
		if (FCode.equals("line2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line2 = FValue.trim();
			}
			else
				line2 = null;
		}
		if (FCode.equals("line3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line3 = FValue.trim();
			}
			else
				line3 = null;
		}
		if (FCode.equals("line4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line4 = FValue.trim();
			}
			else
				line4 = null;
		}
		if (FCode.equals("line5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line5 = FValue.trim();
			}
			else
				line5 = null;
		}
		if (FCode.equals("line6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				line6 = FValue.trim();
			}
			else
				line6 = null;
		}
		if (FCode.equals("SpecContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecContent = FValue.trim();
			}
			else
				SpecContent = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMSpecContentSchema other = (LMSpecContentSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& line1.equals(other.getline1())
			&& line2.equals(other.getline2())
			&& line3.equals(other.getline3())
			&& line4.equals(other.getline4())
			&& line5.equals(other.getline5())
			&& line6.equals(other.getline6())
			&& SpecContent.equals(other.getSpecContent());
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
		if( strFieldName.equals("line1") ) {
			return 1;
		}
		if( strFieldName.equals("line2") ) {
			return 2;
		}
		if( strFieldName.equals("line3") ) {
			return 3;
		}
		if( strFieldName.equals("line4") ) {
			return 4;
		}
		if( strFieldName.equals("line5") ) {
			return 5;
		}
		if( strFieldName.equals("line6") ) {
			return 6;
		}
		if( strFieldName.equals("SpecContent") ) {
			return 7;
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
				strFieldName = "line1";
				break;
			case 2:
				strFieldName = "line2";
				break;
			case 3:
				strFieldName = "line3";
				break;
			case 4:
				strFieldName = "line4";
				break;
			case 5:
				strFieldName = "line5";
				break;
			case 6:
				strFieldName = "line6";
				break;
			case 7:
				strFieldName = "SpecContent";
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
		if( strFieldName.equals("line1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("line2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("line3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("line4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("line5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("line6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecContent") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
