/*
 * <p>ClassName: ApplicationRiderSchema </p>
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
import com.sinosoft.lis.db.ApplicationRiderDB;

public class ApplicationRiderSchema implements Schema
{
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 交易日期 */
	private int ExtractedDate;
	/** 保单号码 */
	private String PolicyNo;
	/** 附加险产品代码 */
	private String RiderPlan;
	/** 附加于(主险代码) */
	private String AttachTo;
	/** Packagedto */
	private String PackagedTo;
	/** 保费金额 */
	private String SumInsured;
	/** 期缴保费 */
	private double ModalPremium;
	/** 生效日期 */
	private int EffectiveDate;
	/** 生成日期 */
	private int MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 更新日期 */
	private int ModifyDate;
	/** 更新时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ApplicationRiderSchema()
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
	/** 交易日期<P>产品代码 */
	public int getExtractedDate()
	{
		return ExtractedDate;
	}
	/** 交易日期 */
	public void setExtractedDate(int aExtractedDate)
	{
		ExtractedDate = aExtractedDate;
	}
	/** 交易日期<P>产品代码 */
	public void setExtractedDate(String aExtractedDate)
	{
		if (aExtractedDate != null && !aExtractedDate.equals(""))
		{
			Integer tInteger = new Integer(aExtractedDate);
			int i = tInteger.intValue();
			ExtractedDate = i;
		}
	}

	/** 保单号码<P> */
	public String getPolicyNo()
	{
		if (PolicyNo != null && !PolicyNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PolicyNo = StrTool.unicodeToGBK(PolicyNo);
		}
		return PolicyNo;
	}
	/** 保单号码 */
	public void setPolicyNo(String aPolicyNo)
	{
		PolicyNo = aPolicyNo;
	}
	/** 附加险产品代码<P> */
	public String getRiderPlan()
	{
		if (RiderPlan != null && !RiderPlan.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiderPlan = StrTool.unicodeToGBK(RiderPlan);
		}
		return RiderPlan;
	}
	/** 附加险产品代码 */
	public void setRiderPlan(String aRiderPlan)
	{
		RiderPlan = aRiderPlan;
	}
	/** 附加于(主险代码)<P> */
	public String getAttachTo()
	{
		if (AttachTo != null && !AttachTo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AttachTo = StrTool.unicodeToGBK(AttachTo);
		}
		return AttachTo;
	}
	/** 附加于(主险代码) */
	public void setAttachTo(String aAttachTo)
	{
		AttachTo = aAttachTo;
	}
	/** Packagedto<P> */
	public String getPackagedTo()
	{
		if (PackagedTo != null && !PackagedTo.equals("") && SysConst.CHANGECHARSET == true)
		{
			PackagedTo = StrTool.unicodeToGBK(PackagedTo);
		}
		return PackagedTo;
	}
	/** Packagedto */
	public void setPackagedTo(String aPackagedTo)
	{
		PackagedTo = aPackagedTo;
	}
	/** 保费金额<P> */
	public String getSumInsured()
	{
		if (SumInsured != null && !SumInsured.equals("") && SysConst.CHANGECHARSET == true)
		{
			SumInsured = StrTool.unicodeToGBK(SumInsured);
		}
		return SumInsured;
	}
	/** 保费金额 */
	public void setSumInsured(String aSumInsured)
	{
		SumInsured = aSumInsured;
	}
	/** 期缴保费<P> */
	public double getModalPremium()
	{
		return ModalPremium;
	}
	/** 期缴保费 */
	public void setModalPremium(double aModalPremium)
	{
		ModalPremium = aModalPremium;
	}
	/** 期缴保费<P> */
	public void setModalPremium(String aModalPremium)
	{
		if (aModalPremium != null && !aModalPremium.equals(""))
		{
			Double tDouble = new Double(aModalPremium);
			double d = tDouble.doubleValue();
			ModalPremium = d;
		}
	}

	/** 生效日期<P> */
	public int getEffectiveDate()
	{
		return EffectiveDate;
	}
	/** 生效日期 */
	public void setEffectiveDate(int aEffectiveDate)
	{
		EffectiveDate = aEffectiveDate;
	}
	/** 生效日期<P> */
	public void setEffectiveDate(String aEffectiveDate)
	{
		if (aEffectiveDate != null && !aEffectiveDate.equals(""))
		{
			Integer tInteger = new Integer(aEffectiveDate);
			int i = tInteger.intValue();
			EffectiveDate = i;
		}
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
	* 使用另外一个 ApplicationRiderSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(ApplicationRiderSchema aApplicationRiderSchema)
	{
		this.SerialNo = aApplicationRiderSchema.getSerialNo();
		this.ExtractedDate = aApplicationRiderSchema.getExtractedDate();
		this.PolicyNo = aApplicationRiderSchema.getPolicyNo();
		this.RiderPlan = aApplicationRiderSchema.getRiderPlan();
		this.AttachTo = aApplicationRiderSchema.getAttachTo();
		this.PackagedTo = aApplicationRiderSchema.getPackagedTo();
		this.SumInsured = aApplicationRiderSchema.getSumInsured();
		this.ModalPremium = aApplicationRiderSchema.getModalPremium();
		this.EffectiveDate = aApplicationRiderSchema.getEffectiveDate();
		this.MakeDate = aApplicationRiderSchema.getMakeDate();
		this.MakeTime = aApplicationRiderSchema.getMakeTime();
		this.ModifyDate = aApplicationRiderSchema.getModifyDate();
		this.ModifyTime = aApplicationRiderSchema.getModifyTime();
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

			this.ExtractedDate = rs.getInt("ExtractedDate");
			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("RiderPlan") == null )
				this.RiderPlan = null;
			else
				this.RiderPlan = rs.getString("RiderPlan").trim();

			if( rs.getString("AttachTo") == null )
				this.AttachTo = null;
			else
				this.AttachTo = rs.getString("AttachTo").trim();

			if( rs.getString("PackagedTo") == null )
				this.PackagedTo = null;
			else
				this.PackagedTo = rs.getString("PackagedTo").trim();

			if( rs.getString("SumInsured") == null )
				this.SumInsured = null;
			else
				this.SumInsured = rs.getString("SumInsured").trim();

			this.ModalPremium = rs.getDouble("ModalPremium");
			this.EffectiveDate = rs.getInt("EffectiveDate");
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
			tError.moduleName = "ApplicationRiderSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public ApplicationRiderSchema getSchema()
	{
		ApplicationRiderSchema aApplicationRiderSchema = new ApplicationRiderSchema();
		aApplicationRiderSchema.setSchema(this);
		return aApplicationRiderSchema;
	}

	public ApplicationRiderDB getDB()
	{
		ApplicationRiderDB aDBOper = new ApplicationRiderDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpApplicationRider描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ExtractedDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PolicyNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiderPlan) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AttachTo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PackagedTo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SumInsured) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModalPremium) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(EffectiveDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpApplicationRider>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ExtractedDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiderPlan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AttachTo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PackagedTo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SumInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ModalPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			EffectiveDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ApplicationRiderSchema";
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
		if (FCode.equals("ExtractedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtractedDate));
		}
		if (FCode.equals("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equals("RiderPlan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiderPlan));
		}
		if (FCode.equals("AttachTo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachTo));
		}
		if (FCode.equals("PackagedTo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PackagedTo));
		}
		if (FCode.equals("SumInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumInsured));
		}
		if (FCode.equals("ModalPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModalPremium));
		}
		if (FCode.equals("EffectiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EffectiveDate));
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
				strFieldValue = String.valueOf(ExtractedDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiderPlan);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AttachTo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PackagedTo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SumInsured);
				break;
			case 7:
				strFieldValue = String.valueOf(ModalPremium);
				break;
			case 8:
				strFieldValue = String.valueOf(EffectiveDate);
				break;
			case 9:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 12:
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
		if (FCode.equals("RiderPlan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiderPlan = FValue.trim();
			}
			else
				RiderPlan = null;
		}
		if (FCode.equals("AttachTo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachTo = FValue.trim();
			}
			else
				AttachTo = null;
		}
		if (FCode.equals("PackagedTo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PackagedTo = FValue.trim();
			}
			else
				PackagedTo = null;
		}
		if (FCode.equals("SumInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SumInsured = FValue.trim();
			}
			else
				SumInsured = null;
		}
		if (FCode.equals("ModalPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ModalPremium = d;
			}
		}
		if (FCode.equals("EffectiveDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EffectiveDate = i;
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
		ApplicationRiderSchema other = (ApplicationRiderSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ExtractedDate == other.getExtractedDate()
			&& PolicyNo.equals(other.getPolicyNo())
			&& RiderPlan.equals(other.getRiderPlan())
			&& AttachTo.equals(other.getAttachTo())
			&& PackagedTo.equals(other.getPackagedTo())
			&& SumInsured.equals(other.getSumInsured())
			&& ModalPremium == other.getModalPremium()
			&& EffectiveDate == other.getEffectiveDate()
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
		if( strFieldName.equals("ExtractedDate") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiderPlan") ) {
			return 3;
		}
		if( strFieldName.equals("AttachTo") ) {
			return 4;
		}
		if( strFieldName.equals("PackagedTo") ) {
			return 5;
		}
		if( strFieldName.equals("SumInsured") ) {
			return 6;
		}
		if( strFieldName.equals("ModalPremium") ) {
			return 7;
		}
		if( strFieldName.equals("EffectiveDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
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
				strFieldName = "ExtractedDate";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "RiderPlan";
				break;
			case 4:
				strFieldName = "AttachTo";
				break;
			case 5:
				strFieldName = "PackagedTo";
				break;
			case 6:
				strFieldName = "SumInsured";
				break;
			case 7:
				strFieldName = "ModalPremium";
				break;
			case 8:
				strFieldName = "EffectiveDate";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
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
		if( strFieldName.equals("ExtractedDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiderPlan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachTo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PackagedTo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModalPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EffectiveDate") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
