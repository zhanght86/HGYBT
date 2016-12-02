/*
 * <p>ClassName: AxaPolicyTransactionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛花旗
 * @CreateDate：2012-03-31
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.AxaPolicyTransactionDB;

public class AxaPolicyTransactionSchema implements Schema
{
	// @Field
	/** 主键 */
	private int Oid;
	/** 导出日期 */
	private int extracted;
	/** 交???序列号 */
	private String TransactionSeqNo;
	/** 交易子序列号 */
	private String TransactionSubSeqNo;
	/** 保单号 */
	private String PolicyNo;
	/** 险种代码 */
	private String PlanCode;
	/** 缴费类型 */
	private String premiumType;
	/** 缴费到期日 */
	private int lastPremiumDueDate;
	/** Column_32 */
	private String accountNo;
	/** 交易金额 */
	private double TransactionAmount;
	/** 交易日期 */
	private int TransactionDate;
	/** 手续费率 */
	private double CommissionLevel;
	/** 手续费 */
	private double Commission;
	/** 交易代码 */
	private String transactionCode;
	/** 交易描述 */
	private String TransactionDetial;
	/** 期缴定??投资保费 */
	private double ModalRegularPremium;
	/** 定期额外投资保费 */
	private double RegularTopUpPremium;
	/** 保费总计 */
	private double LumpSumPremium;
	/** 首期保费总计 */
	private double initLumpSumPremium;
	/** Column_12 */
	private String MonthsCovered;
	/** 保单年度 */
	private String policyYear;
	/** 创建日期 */
	private int MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 处理日期 */
	private int DealDate;
	/** 处理时间 */
	private String DealTime;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AxaPolicyTransactionSchema()
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

	/** 导出日期<P> */
	public int getextracted()
	{
		return extracted;
	}
	/** 导出日期 */
	public void setextracted(int aextracted)
	{
		extracted = aextracted;
	}
	/** 导出日期<P> */
	public void setextracted(String aextracted)
	{
		if (aextracted != null && !aextracted.equals(""))
		{
			Integer tInteger = new Integer(aextracted);
			int i = tInteger.intValue();
			extracted = i;
		}
	}

	/** 交???序列号<P> */
	public String getTransactionSeqNo()
	{
		if (TransactionSeqNo != null && !TransactionSeqNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionSeqNo = StrTool.unicodeToGBK(TransactionSeqNo);
		}
		return TransactionSeqNo;
	}
	/** 交???序列号 */
	public void setTransactionSeqNo(String aTransactionSeqNo)
	{
		TransactionSeqNo = aTransactionSeqNo;
	}
	/** 交易子序列号<P> */
	public String getTransactionSubSeqNo()
	{
		if (TransactionSubSeqNo != null && !TransactionSubSeqNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionSubSeqNo = StrTool.unicodeToGBK(TransactionSubSeqNo);
		}
		return TransactionSubSeqNo;
	}
	/** 交易子序列号 */
	public void setTransactionSubSeqNo(String aTransactionSubSeqNo)
	{
		TransactionSubSeqNo = aTransactionSubSeqNo;
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
	/** 缴费类型<P> */
	public String getpremiumType()
	{
		if (premiumType != null && !premiumType.equals("") && SysConst.CHANGECHARSET == true)
		{
			premiumType = StrTool.unicodeToGBK(premiumType);
		}
		return premiumType;
	}
	/** 缴费类型 */
	public void setpremiumType(String apremiumType)
	{
		premiumType = apremiumType;
	}
	/** 缴费到期日<P> */
	public int getlastPremiumDueDate()
	{
		return lastPremiumDueDate;
	}
	/** 缴费到期日 */
	public void setlastPremiumDueDate(int alastPremiumDueDate)
	{
		lastPremiumDueDate = alastPremiumDueDate;
	}
	/** 缴费到期日<P> */
	public void setlastPremiumDueDate(String alastPremiumDueDate)
	{
		if (alastPremiumDueDate != null && !alastPremiumDueDate.equals(""))
		{
			Integer tInteger = new Integer(alastPremiumDueDate);
			int i = tInteger.intValue();
			lastPremiumDueDate = i;
		}
	}

	/** Column_32<P> */
	public String getaccountNo()
	{
		if (accountNo != null && !accountNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			accountNo = StrTool.unicodeToGBK(accountNo);
		}
		return accountNo;
	}
	/** Column_32 */
	public void setaccountNo(String aaccountNo)
	{
		accountNo = aaccountNo;
	}
	/** 交易金额<P> */
	public double getTransactionAmount()
	{
		return TransactionAmount;
	}
	/** 交易金额 */
	public void setTransactionAmount(double aTransactionAmount)
	{
		TransactionAmount = aTransactionAmount;
	}
	/** 交易金额<P> */
	public void setTransactionAmount(String aTransactionAmount)
	{
		if (aTransactionAmount != null && !aTransactionAmount.equals(""))
		{
			Double tDouble = new Double(aTransactionAmount);
			double d = tDouble.doubleValue();
			TransactionAmount = d;
		}
	}

	/** 交易日期<P> */
	public int getTransactionDate()
	{
		return TransactionDate;
	}
	/** 交易日期 */
	public void setTransactionDate(int aTransactionDate)
	{
		TransactionDate = aTransactionDate;
	}
	/** 交易日期<P> */
	public void setTransactionDate(String aTransactionDate)
	{
		if (aTransactionDate != null && !aTransactionDate.equals(""))
		{
			Integer tInteger = new Integer(aTransactionDate);
			int i = tInteger.intValue();
			TransactionDate = i;
		}
	}

	/** 手续费率<P> */
	public double getCommissionLevel()
	{
		return CommissionLevel;
	}
	/** 手续费率 */
	public void setCommissionLevel(double aCommissionLevel)
	{
		CommissionLevel = aCommissionLevel;
	}
	/** 手续费率<P> */
	public void setCommissionLevel(String aCommissionLevel)
	{
		if (aCommissionLevel != null && !aCommissionLevel.equals(""))
		{
			Double tDouble = new Double(aCommissionLevel);
			double d = tDouble.doubleValue();
			CommissionLevel = d;
		}
	}

	/** 手续费<P> */
	public double getCommission()
	{
		return Commission;
	}
	/** 手续费 */
	public void setCommission(double aCommission)
	{
		Commission = aCommission;
	}
	/** 手续费<P> */
	public void setCommission(String aCommission)
	{
		if (aCommission != null && !aCommission.equals(""))
		{
			Double tDouble = new Double(aCommission);
			double d = tDouble.doubleValue();
			Commission = d;
		}
	}

	/** 交易代码<P> */
	public String gettransactionCode()
	{
		if (transactionCode != null && !transactionCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			transactionCode = StrTool.unicodeToGBK(transactionCode);
		}
		return transactionCode;
	}
	/** 交易代码 */
	public void settransactionCode(String atransactionCode)
	{
		transactionCode = atransactionCode;
	}
	/** 交易描述<P> */
	public String getTransactionDetial()
	{
		if (TransactionDetial != null && !TransactionDetial.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransactionDetial = StrTool.unicodeToGBK(TransactionDetial);
		}
		return TransactionDetial;
	}
	/** 交易描述 */
	public void setTransactionDetial(String aTransactionDetial)
	{
		TransactionDetial = aTransactionDetial;
	}
	/** 期缴定??投资保费<P> */
	public double getModalRegularPremium()
	{
		return ModalRegularPremium;
	}
	/** 期缴定??投资保费 */
	public void setModalRegularPremium(double aModalRegularPremium)
	{
		ModalRegularPremium = aModalRegularPremium;
	}
	/** 期缴定??投资保费<P> */
	public void setModalRegularPremium(String aModalRegularPremium)
	{
		if (aModalRegularPremium != null && !aModalRegularPremium.equals(""))
		{
			Double tDouble = new Double(aModalRegularPremium);
			double d = tDouble.doubleValue();
			ModalRegularPremium = d;
		}
	}

	/** 定期额外投资保费<P> */
	public double getRegularTopUpPremium()
	{
		return RegularTopUpPremium;
	}
	/** 定期额外投资保费 */
	public void setRegularTopUpPremium(double aRegularTopUpPremium)
	{
		RegularTopUpPremium = aRegularTopUpPremium;
	}
	/** 定期额外投资保费<P> */
	public void setRegularTopUpPremium(String aRegularTopUpPremium)
	{
		if (aRegularTopUpPremium != null && !aRegularTopUpPremium.equals(""))
		{
			Double tDouble = new Double(aRegularTopUpPremium);
			double d = tDouble.doubleValue();
			RegularTopUpPremium = d;
		}
	}

	/** 保费总计<P> */
	public double getLumpSumPremium()
	{
		return LumpSumPremium;
	}
	/** 保费总计 */
	public void setLumpSumPremium(double aLumpSumPremium)
	{
		LumpSumPremium = aLumpSumPremium;
	}
	/** 保费总计<P> */
	public void setLumpSumPremium(String aLumpSumPremium)
	{
		if (aLumpSumPremium != null && !aLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(aLumpSumPremium);
			double d = tDouble.doubleValue();
			LumpSumPremium = d;
		}
	}

	/** 首期保费总计<P> */
	public double getinitLumpSumPremium()
	{
		return initLumpSumPremium;
	}
	/** 首期保费总计 */
	public void setinitLumpSumPremium(double ainitLumpSumPremium)
	{
		initLumpSumPremium = ainitLumpSumPremium;
	}
	/** 首期保费总计<P> */
	public void setinitLumpSumPremium(String ainitLumpSumPremium)
	{
		if (ainitLumpSumPremium != null && !ainitLumpSumPremium.equals(""))
		{
			Double tDouble = new Double(ainitLumpSumPremium);
			double d = tDouble.doubleValue();
			initLumpSumPremium = d;
		}
	}

	/** Column_12<P> */
	public String getMonthsCovered()
	{
		if (MonthsCovered != null && !MonthsCovered.equals("") && SysConst.CHANGECHARSET == true)
		{
			MonthsCovered = StrTool.unicodeToGBK(MonthsCovered);
		}
		return MonthsCovered;
	}
	/** Column_12 */
	public void setMonthsCovered(String aMonthsCovered)
	{
		MonthsCovered = aMonthsCovered;
	}
	/** 保单年度<P> */
	public String getpolicyYear()
	{
		if (policyYear != null && !policyYear.equals("") && SysConst.CHANGECHARSET == true)
		{
			policyYear = StrTool.unicodeToGBK(policyYear);
		}
		return policyYear;
	}
	/** 保单年度 */
	public void setpolicyYear(String apolicyYear)
	{
		policyYear = apolicyYear;
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

	/** 创建时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 创建时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 处理日期<P> */
	public int getDealDate()
	{
		return DealDate;
	}
	/** 处理日期 */
	public void setDealDate(int aDealDate)
	{
		DealDate = aDealDate;
	}
	/** 处理日期<P> */
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals(""))
		{
			Integer tInteger = new Integer(aDealDate);
			int i = tInteger.intValue();
			DealDate = i;
		}
	}

	/** 处理时间<P> */
	public String getDealTime()
	{
		if (DealTime != null && !DealTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			DealTime = StrTool.unicodeToGBK(DealTime);
		}
		return DealTime;
	}
	/** 处理时间 */
	public void setDealTime(String aDealTime)
	{
		DealTime = aDealTime;
	}

	/**
	* 使用另外一个 AxaPolicyTransactionSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AxaPolicyTransactionSchema aAxaPolicyTransactionSchema)
	{
		this.Oid = aAxaPolicyTransactionSchema.getOid();
		this.extracted = aAxaPolicyTransactionSchema.getextracted();
		this.TransactionSeqNo = aAxaPolicyTransactionSchema.getTransactionSeqNo();
		this.TransactionSubSeqNo = aAxaPolicyTransactionSchema.getTransactionSubSeqNo();
		this.PolicyNo = aAxaPolicyTransactionSchema.getPolicyNo();
		this.PlanCode = aAxaPolicyTransactionSchema.getPlanCode();
		this.premiumType = aAxaPolicyTransactionSchema.getpremiumType();
		this.lastPremiumDueDate = aAxaPolicyTransactionSchema.getlastPremiumDueDate();
		this.accountNo = aAxaPolicyTransactionSchema.getaccountNo();
		this.TransactionAmount = aAxaPolicyTransactionSchema.getTransactionAmount();
		this.TransactionDate = aAxaPolicyTransactionSchema.getTransactionDate();
		this.CommissionLevel = aAxaPolicyTransactionSchema.getCommissionLevel();
		this.Commission = aAxaPolicyTransactionSchema.getCommission();
		this.transactionCode = aAxaPolicyTransactionSchema.gettransactionCode();
		this.TransactionDetial = aAxaPolicyTransactionSchema.getTransactionDetial();
		this.ModalRegularPremium = aAxaPolicyTransactionSchema.getModalRegularPremium();
		this.RegularTopUpPremium = aAxaPolicyTransactionSchema.getRegularTopUpPremium();
		this.LumpSumPremium = aAxaPolicyTransactionSchema.getLumpSumPremium();
		this.initLumpSumPremium = aAxaPolicyTransactionSchema.getinitLumpSumPremium();
		this.MonthsCovered = aAxaPolicyTransactionSchema.getMonthsCovered();
		this.policyYear = aAxaPolicyTransactionSchema.getpolicyYear();
		this.MakeDate = aAxaPolicyTransactionSchema.getMakeDate();
		this.MakeTime = aAxaPolicyTransactionSchema.getMakeTime();
		this.DealDate = aAxaPolicyTransactionSchema.getDealDate();
		this.DealTime = aAxaPolicyTransactionSchema.getDealTime();
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
			this.extracted = rs.getInt("extracted");
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

			if( rs.getString("premiumType") == null )
				this.premiumType = null;
			else
				this.premiumType = rs.getString("premiumType").trim();

			this.lastPremiumDueDate = rs.getInt("lastPremiumDueDate");
			if( rs.getString("accountNo") == null )
				this.accountNo = null;
			else
				this.accountNo = rs.getString("accountNo").trim();

			this.TransactionAmount = rs.getDouble("TransactionAmount");
			this.TransactionDate = rs.getInt("TransactionDate");
			this.CommissionLevel = rs.getDouble("CommissionLevel");
			this.Commission = rs.getDouble("Commission");
			if( rs.getString("transactionCode") == null )
				this.transactionCode = null;
			else
				this.transactionCode = rs.getString("transactionCode").trim();

			if( rs.getString("TransactionDetial") == null )
				this.TransactionDetial = null;
			else
				this.TransactionDetial = rs.getString("TransactionDetial").trim();

			this.ModalRegularPremium = rs.getDouble("ModalRegularPremium");
			this.RegularTopUpPremium = rs.getDouble("RegularTopUpPremium");
			this.LumpSumPremium = rs.getDouble("LumpSumPremium");
			this.initLumpSumPremium = rs.getDouble("initLumpSumPremium");
			if( rs.getString("MonthsCovered") == null )
				this.MonthsCovered = null;
			else
				this.MonthsCovered = rs.getString("MonthsCovered").trim();

			if( rs.getString("policyYear") == null )
				this.policyYear = null;
			else
				this.policyYear = rs.getString("policyYear").trim();

			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

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
			tError.moduleName = "AxaPolicyTransactionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AxaPolicyTransactionSchema getSchema()
	{
		AxaPolicyTransactionSchema aAxaPolicyTransactionSchema = new AxaPolicyTransactionSchema();
		aAxaPolicyTransactionSchema.setSchema(this);
		return aAxaPolicyTransactionSchema;
	}

	public AxaPolicyTransactionDB getDB()
	{
		AxaPolicyTransactionDB aDBOper = new AxaPolicyTransactionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAxaPolicyTransaction描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(Oid) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(extracted) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionSeqNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionSubSeqNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PlanCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(premiumType) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(lastPremiumDueDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(accountNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionAmount) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransactionDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(CommissionLevel) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Commission) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(transactionCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransactionDetial) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModalRegularPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RegularTopUpPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(LumpSumPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(initLumpSumPremium) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MonthsCovered) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(policyYear) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(DealDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(DealTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAxaPolicyTransaction>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Oid= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			extracted= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			TransactionSeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TransactionSubSeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			premiumType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			lastPremiumDueDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			accountNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TransactionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			TransactionDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			CommissionLevel = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Commission = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			transactionCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			TransactionDetial = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModalRegularPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			RegularTopUpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			LumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			initLumpSumPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			MonthsCovered = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			policyYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			DealDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			DealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AxaPolicyTransactionSchema";
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
		if (FCode.equals("extracted"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(extracted));
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
		if (FCode.equals("premiumType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(premiumType));
		}
		if (FCode.equals("lastPremiumDueDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(lastPremiumDueDate));
		}
		if (FCode.equals("accountNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(accountNo));
		}
		if (FCode.equals("TransactionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionAmount));
		}
		if (FCode.equals("TransactionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDate));
		}
		if (FCode.equals("CommissionLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommissionLevel));
		}
		if (FCode.equals("Commission"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Commission));
		}
		if (FCode.equals("transactionCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(transactionCode));
		}
		if (FCode.equals("TransactionDetial"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransactionDetial));
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
		if (FCode.equals("initLumpSumPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(initLumpSumPremium));
		}
		if (FCode.equals("MonthsCovered"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonthsCovered));
		}
		if (FCode.equals("policyYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(policyYear));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = String.valueOf(Oid);
				break;
			case 1:
				strFieldValue = String.valueOf(extracted);
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
				strFieldValue = StrTool.GBKToUnicode(premiumType);
				break;
			case 7:
				strFieldValue = String.valueOf(lastPremiumDueDate);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(accountNo);
				break;
			case 9:
				strFieldValue = String.valueOf(TransactionAmount);
				break;
			case 10:
				strFieldValue = String.valueOf(TransactionDate);
				break;
			case 11:
				strFieldValue = String.valueOf(CommissionLevel);
				break;
			case 12:
				strFieldValue = String.valueOf(Commission);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(transactionCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(TransactionDetial);
				break;
			case 15:
				strFieldValue = String.valueOf(ModalRegularPremium);
				break;
			case 16:
				strFieldValue = String.valueOf(RegularTopUpPremium);
				break;
			case 17:
				strFieldValue = String.valueOf(LumpSumPremium);
				break;
			case 18:
				strFieldValue = String.valueOf(initLumpSumPremium);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MonthsCovered);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(policyYear);
				break;
			case 21:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = String.valueOf(DealDate);
				break;
			case 24:
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

		if (FCode.equals("Oid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Oid = i;
			}
		}
		if (FCode.equals("extracted"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				extracted = i;
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
		if (FCode.equals("premiumType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				premiumType = FValue.trim();
			}
			else
				premiumType = null;
		}
		if (FCode.equals("lastPremiumDueDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				lastPremiumDueDate = i;
			}
		}
		if (FCode.equals("accountNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				accountNo = FValue.trim();
			}
			else
				accountNo = null;
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
		if (FCode.equals("TransactionDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TransactionDate = i;
			}
		}
		if (FCode.equals("CommissionLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommissionLevel = d;
			}
		}
		if (FCode.equals("Commission"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Commission = d;
			}
		}
		if (FCode.equals("transactionCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				transactionCode = FValue.trim();
			}
			else
				transactionCode = null;
		}
		if (FCode.equals("TransactionDetial"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransactionDetial = FValue.trim();
			}
			else
				TransactionDetial = null;
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
		if (FCode.equals("initLumpSumPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				initLumpSumPremium = d;
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
		if (FCode.equals("policyYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				policyYear = FValue.trim();
			}
			else
				policyYear = null;
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
		AxaPolicyTransactionSchema other = (AxaPolicyTransactionSchema)otherObject;
		return
			Oid == other.getOid()
			&& extracted == other.getextracted()
			&& TransactionSeqNo.equals(other.getTransactionSeqNo())
			&& TransactionSubSeqNo.equals(other.getTransactionSubSeqNo())
			&& PolicyNo.equals(other.getPolicyNo())
			&& PlanCode.equals(other.getPlanCode())
			&& premiumType.equals(other.getpremiumType())
			&& lastPremiumDueDate == other.getlastPremiumDueDate()
			&& accountNo.equals(other.getaccountNo())
			&& TransactionAmount == other.getTransactionAmount()
			&& TransactionDate == other.getTransactionDate()
			&& CommissionLevel == other.getCommissionLevel()
			&& Commission == other.getCommission()
			&& transactionCode.equals(other.gettransactionCode())
			&& TransactionDetial.equals(other.getTransactionDetial())
			&& ModalRegularPremium == other.getModalRegularPremium()
			&& RegularTopUpPremium == other.getRegularTopUpPremium()
			&& LumpSumPremium == other.getLumpSumPremium()
			&& initLumpSumPremium == other.getinitLumpSumPremium()
			&& MonthsCovered.equals(other.getMonthsCovered())
			&& policyYear.equals(other.getpolicyYear())
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("Oid") ) {
			return 0;
		}
		if( strFieldName.equals("extracted") ) {
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
		if( strFieldName.equals("premiumType") ) {
			return 6;
		}
		if( strFieldName.equals("lastPremiumDueDate") ) {
			return 7;
		}
		if( strFieldName.equals("accountNo") ) {
			return 8;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return 9;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return 10;
		}
		if( strFieldName.equals("CommissionLevel") ) {
			return 11;
		}
		if( strFieldName.equals("Commission") ) {
			return 12;
		}
		if( strFieldName.equals("transactionCode") ) {
			return 13;
		}
		if( strFieldName.equals("TransactionDetial") ) {
			return 14;
		}
		if( strFieldName.equals("ModalRegularPremium") ) {
			return 15;
		}
		if( strFieldName.equals("RegularTopUpPremium") ) {
			return 16;
		}
		if( strFieldName.equals("LumpSumPremium") ) {
			return 17;
		}
		if( strFieldName.equals("initLumpSumPremium") ) {
			return 18;
		}
		if( strFieldName.equals("MonthsCovered") ) {
			return 19;
		}
		if( strFieldName.equals("policyYear") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("DealDate") ) {
			return 23;
		}
		if( strFieldName.equals("DealTime") ) {
			return 24;
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
				strFieldName = "extracted";
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
				strFieldName = "premiumType";
				break;
			case 7:
				strFieldName = "lastPremiumDueDate";
				break;
			case 8:
				strFieldName = "accountNo";
				break;
			case 9:
				strFieldName = "TransactionAmount";
				break;
			case 10:
				strFieldName = "TransactionDate";
				break;
			case 11:
				strFieldName = "CommissionLevel";
				break;
			case 12:
				strFieldName = "Commission";
				break;
			case 13:
				strFieldName = "transactionCode";
				break;
			case 14:
				strFieldName = "TransactionDetial";
				break;
			case 15:
				strFieldName = "ModalRegularPremium";
				break;
			case 16:
				strFieldName = "RegularTopUpPremium";
				break;
			case 17:
				strFieldName = "LumpSumPremium";
				break;
			case 18:
				strFieldName = "initLumpSumPremium";
				break;
			case 19:
				strFieldName = "MonthsCovered";
				break;
			case 20:
				strFieldName = "policyYear";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "DealDate";
				break;
			case 24:
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
		if( strFieldName.equals("Oid") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("extracted") ) {
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
		if( strFieldName.equals("premiumType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("lastPremiumDueDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("accountNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TransactionDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CommissionLevel") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Commission") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("transactionCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransactionDetial") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("initLumpSumPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MonthsCovered") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("policyYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
