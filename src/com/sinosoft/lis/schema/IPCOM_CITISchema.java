/*
 * <p>ClassName: IPCOM_CITISchema </p>
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
import com.sinosoft.lis.db.IPCOM_CITIDB;

public class IPCOM_CITISchema implements Schema
{
	// @Field
	/** 产品代码 */
	private String RiskCode;
	/** 代理机构 */
	private String AgentCom;
	/** 缴费年期 */
	private String PayEndYear;
	/** 保单年度 */
	private int PolicyYear;
	/** 保障年期 */
	private String InsuredYear;
	/** 费率 */
	private double PremRate;
	/** 计算单位 */
	private int PremScale;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

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

	/** 产品代码<P>产???代码 */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 产品代码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 代理机构<P> */
	public String getAgentCom()
	{
		if (AgentCom != null && !AgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCom = StrTool.unicodeToGBK(AgentCom);
		}
		return AgentCom;
	}
	/** 代理机构 */
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/** 缴费年期<P> */
	public String getPayEndYear()
	{
		if (PayEndYear != null && !PayEndYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			PayEndYear = StrTool.unicodeToGBK(PayEndYear);
		}
		return PayEndYear;
	}
	/** 缴费年期 */
	public void setPayEndYear(String aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	/** 保单年度<P> */
	public int getPolicyYear()
	{
		return PolicyYear;
	}
	/** 保单年度 */
	public void setPolicyYear(int aPolicyYear)
	{
		PolicyYear = aPolicyYear;
	}
	/** 保单年度<P> */
	public void setPolicyYear(String aPolicyYear)
	{
		if (aPolicyYear != null && !aPolicyYear.equals(""))
		{
			Integer tInteger = new Integer(aPolicyYear);
			int i = tInteger.intValue();
			PolicyYear = i;
		}
	}

	/** 保障年期<P> */
	public String getInsuredYear()
	{
		if (InsuredYear != null && !InsuredYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredYear = StrTool.unicodeToGBK(InsuredYear);
		}
		return InsuredYear;
	}
	/** 保障年期 */
	public void setInsuredYear(String aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	/** 费率<P> */
	public double getPremRate()
	{
		return PremRate;
	}
	/** 费率 */
	public void setPremRate(double aPremRate)
	{
		PremRate = aPremRate;
	}
	/** 费率<P> */
	public void setPremRate(String aPremRate)
	{
		if (aPremRate != null && !aPremRate.equals(""))
		{
			Double tDouble = new Double(aPremRate);
			double d = tDouble.doubleValue();
			PremRate = d;
		}
	}

	/** 计算单位<P> */
	public int getPremScale()
	{
		return PremScale;
	}
	/** 计算单位 */
	public void setPremScale(int aPremScale)
	{
		PremScale = aPremScale;
	}
	/** 计算单位<P> */
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
	* 使用另外一个 IPCOM_CITISchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
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
			// @@错误处理
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
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpIPCOM_CITI描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpIPCOM_CITI>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
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
			// @@错误处理
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
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
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
