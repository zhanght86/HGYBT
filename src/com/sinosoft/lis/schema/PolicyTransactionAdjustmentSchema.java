/*
 * <p>ClassName: PolicyTransactionAdjustmentSchema </p>
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
import com.sinosoft.lis.db.PolicyTransactionAdjustmentDB;

public class PolicyTransactionAdjustmentSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 交易日期 */
	private String TranDate;
	/** 保单号 */
	private String PolicyNo;
	/** 险种代码 */
	private String PlanCode;
	/** 险种名称 */
	private String PlanName;
	/** 保费类型 */
	private String PremType;
	/** 费用金额 */
	private double CostAmount;
	/** 保单年度 */
	private String PolicyYear;
	/** 生成日期 */
	private int MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 更新日期 */
	private int ModifyDate;
	/** 更新时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PolicyTransactionAdjustmentSchema()
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
	public String getTranDate()
	{
		if (TranDate != null && !TranDate.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranDate = StrTool.unicodeToGBK(TranDate);
		}
		return TranDate;
	}
	/** 交易日期 */
	public void setTranDate(String aTranDate)
	{
		TranDate = aTranDate;
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
	/** 险种代码<P> */
	public String getPlanCode()
	{
		if (PlanCode != null && !PlanCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			PlanCode = StrTool.unicodeToGBK(PlanCode);
		}
		return PlanCode;
	}
	/** 险种代码 */
	public void setPlanCode(String aPlanCode)
	{
		PlanCode = aPlanCode;
	}
	/** 险种名称<P> */
	public String getPlanName()
	{
		if (PlanName != null && !PlanName.equals("") && SysConst.CHANGECHARSET == true)
		{
			PlanName = StrTool.unicodeToGBK(PlanName);
		}
		return PlanName;
	}
	/** 险种名称 */
	public void setPlanName(String aPlanName)
	{
		PlanName = aPlanName;
	}
	/** 保费类型<P> */
	public String getPremType()
	{
		if (PremType != null && !PremType.equals("") && SysConst.CHANGECHARSET == true)
		{
			PremType = StrTool.unicodeToGBK(PremType);
		}
		return PremType;
	}
	/** 保费类型 */
	public void setPremType(String aPremType)
	{
		PremType = aPremType;
	}
	/** 费用金额<P> */
	public double getCostAmount()
	{
		return CostAmount;
	}
	/** 费用金额 */
	public void setCostAmount(double aCostAmount)
	{
		CostAmount = aCostAmount;
	}
	/** 费用金额<P> */
	public void setCostAmount(String aCostAmount)
	{
		if (aCostAmount != null && !aCostAmount.equals(""))
		{
			Double tDouble = new Double(aCostAmount);
			double d = tDouble.doubleValue();
			CostAmount = d;
		}
	}

	/** 保单年度<P> */
	public String getPolicyYear()
	{
		if (PolicyYear != null && !PolicyYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyYear = StrTool.unicodeToGBK(PolicyYear);
		}
		return PolicyYear;
	}
	/** 保单年度 */
	public void setPolicyYear(String aPolicyYear)
	{
		PolicyYear = aPolicyYear;
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

	/**
	* 使用另外一个 PolicyTransactionAdjustmentSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(PolicyTransactionAdjustmentSchema aPolicyTransactionAdjustmentSchema)
	{
		this.SerialNo = aPolicyTransactionAdjustmentSchema.getSerialNo();
		this.TranDate = aPolicyTransactionAdjustmentSchema.getTranDate();
		this.PolicyNo = aPolicyTransactionAdjustmentSchema.getPolicyNo();
		this.PlanCode = aPolicyTransactionAdjustmentSchema.getPlanCode();
		this.PlanName = aPolicyTransactionAdjustmentSchema.getPlanName();
		this.PremType = aPolicyTransactionAdjustmentSchema.getPremType();
		this.CostAmount = aPolicyTransactionAdjustmentSchema.getCostAmount();
		this.PolicyYear = aPolicyTransactionAdjustmentSchema.getPolicyYear();
		this.MakeDate = aPolicyTransactionAdjustmentSchema.getMakeDate();
		this.MakeTime = aPolicyTransactionAdjustmentSchema.getMakeTime();
		this.ModifyDate = aPolicyTransactionAdjustmentSchema.getModifyDate();
		this.ModifyTime = aPolicyTransactionAdjustmentSchema.getModifyTime();
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

			if( rs.getString("TranDate") == null )
				this.TranDate = null;
			else
				this.TranDate = rs.getString("TranDate").trim();

			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("PlanName") == null )
				this.PlanName = null;
			else
				this.PlanName = rs.getString("PlanName").trim();

			if( rs.getString("PremType") == null )
				this.PremType = null;
			else
				this.PremType = rs.getString("PremType").trim();

			this.CostAmount = rs.getDouble("CostAmount");
			if( rs.getString("PolicyYear") == null )
				this.PolicyYear = null;
			else
				this.PolicyYear = rs.getString("PolicyYear").trim();

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

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PolicyTransactionAdjustmentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public PolicyTransactionAdjustmentSchema getSchema()
	{
		PolicyTransactionAdjustmentSchema aPolicyTransactionAdjustmentSchema = new PolicyTransactionAdjustmentSchema();
		aPolicyTransactionAdjustmentSchema.setSchema(this);
		return aPolicyTransactionAdjustmentSchema;
	}

	public PolicyTransactionAdjustmentDB getDB()
	{
		PolicyTransactionAdjustmentDB aDBOper = new PolicyTransactionAdjustmentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyTransactionAdjustment描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranDate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PlanCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PlanName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PremType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CostAmount) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyYear) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyTransactionAdjustment>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TranDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PremType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CostAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			PolicyYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PolicyTransactionAdjustmentSchema";
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
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equals("PlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanName));
		}
		if (FCode.equals("PremType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremType));
		}
		if (FCode.equals("CostAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostAmount));
		}
		if (FCode.equals("PolicyYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyYear));
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
				strFieldValue = StrTool.GBKToUnicode(TranDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PlanName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PremType);
				break;
			case 6:
				strFieldValue = String.valueOf(CostAmount);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolicyYear);
				break;
			case 8:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equals("TranDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranDate = FValue.trim();
			}
			else
				TranDate = null;
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
		if (FCode.equals("PlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanName = FValue.trim();
			}
			else
				PlanName = null;
		}
		if (FCode.equals("PremType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremType = FValue.trim();
			}
			else
				PremType = null;
		}
		if (FCode.equals("CostAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CostAmount = d;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PolicyTransactionAdjustmentSchema other = (PolicyTransactionAdjustmentSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& TranDate.equals(other.getTranDate())
			&& PolicyNo.equals(other.getPolicyNo())
			&& PlanCode.equals(other.getPlanCode())
			&& PlanName.equals(other.getPlanName())
			&& PremType.equals(other.getPremType())
			&& CostAmount == other.getCostAmount()
			&& PolicyYear.equals(other.getPolicyYear())
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("TranDate") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 3;
		}
		if( strFieldName.equals("PlanName") ) {
			return 4;
		}
		if( strFieldName.equals("PremType") ) {
			return 5;
		}
		if( strFieldName.equals("CostAmount") ) {
			return 6;
		}
		if( strFieldName.equals("PolicyYear") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
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
				strFieldName = "TranDate";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "PlanCode";
				break;
			case 4:
				strFieldName = "PlanName";
				break;
			case 5:
				strFieldName = "PremType";
				break;
			case 6:
				strFieldName = "CostAmount";
				break;
			case 7:
				strFieldName = "PolicyYear";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CostAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolicyYear") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
