/*
 * <p>ClassName: EndorseDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LKMatureBalance
 * @CreateDate：2015-01-06
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.EndorseDetailDB;

public class EndorseDetailSchema implements Schema
{
	// @Field
	/** 交易日期 */
	private Date TransDate;
	/** 银行代码 */
	private String BankCode;
	/** 地区码 */
	private String ZoneNo;
	/** 银行网点 */
	private String BankNode;
	/** 银行柜员号 */
	private String TellerNo;
	/** 交易流水号 */
	private String TransNo;
	/** 保全类型 */
	private String Type;
	/** 保全申请状态 */
	private String state;
	/** 交易码 */
	private String FuncFlag;
	/** 保单号 */
	private String ContNo;
	/** 交易金额 */
	private double TransAmnt;
	/** 交易渠道 */
	private String SourceType;
	/** 对账流水号 */
	private String BalanceNo;
	/** 成功标志 */
	private String ConfigFlag;
	/** 备注1 */
	private String Temp1;
	/** 备注2 */
	private String Temp2;
	/** 备注3 */
	private String Temp3;
	/** 备注4 */
	private String Temp4;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public EndorseDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TransNo";
		pk[1] = "ContNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 交易日期<P> */
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	/** 交易日期 */
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	/** 交易日期<P> */
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	/** 银行代码<P> */
	public String getBankCode()
	{
		if (BankCode != null && !BankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankCode = StrTool.unicodeToGBK(BankCode);
		}
		return BankCode;
	}
	/** 银行代码 */
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/** 地区码<P> */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 地区码 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 银行网点<P> */
	public String getBankNode()
	{
		if (BankNode != null && !BankNode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankNode = StrTool.unicodeToGBK(BankNode);
		}
		return BankNode;
	}
	/** 银行网点 */
	public void setBankNode(String aBankNode)
	{
		BankNode = aBankNode;
	}
	/** 银行柜员号<P> */
	public String getTellerNo()
	{
		if (TellerNo != null && !TellerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerNo = StrTool.unicodeToGBK(TellerNo);
		}
		return TellerNo;
	}
	/** 银行柜员号 */
	public void setTellerNo(String aTellerNo)
	{
		TellerNo = aTellerNo;
	}
	/** 交易流水号<P> */
	public String getTransNo()
	{
		if (TransNo != null && !TransNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TransNo = StrTool.unicodeToGBK(TransNo);
		}
		return TransNo;
	}
	/** 交易流水号 */
	public void setTransNo(String aTransNo)
	{
		TransNo = aTransNo;
	}
	/** 保全类型<P> */
	public String getType()
	{
		if (Type != null && !Type.equals("") && SysConst.CHANGECHARSET == true)
		{
			Type = StrTool.unicodeToGBK(Type);
		}
		return Type;
	}
	/** 保全类型 */
	public void setType(String aType)
	{
		Type = aType;
	}
	/** 保全申请状态<P> */
	public String getstate()
	{
		if (state != null && !state.equals("") && SysConst.CHANGECHARSET == true)
		{
			state = StrTool.unicodeToGBK(state);
		}
		return state;
	}
	/** 保全申请状态 */
	public void setstate(String astate)
	{
		state = astate;
	}
	/** 交易码<P> */
	public String getFuncFlag()
	{
		if (FuncFlag != null && !FuncFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			FuncFlag = StrTool.unicodeToGBK(FuncFlag);
		}
		return FuncFlag;
	}
	/** 交易码 */
	public void setFuncFlag(String aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	/** 保单号<P> */
	public String getContNo()
	{
		if (ContNo != null && !ContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContNo = StrTool.unicodeToGBK(ContNo);
		}
		return ContNo;
	}
	/** 保单号 */
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/** 交易金额<P> */
	public double getTransAmnt()
	{
		return TransAmnt;
	}
	/** 交易金额 */
	public void setTransAmnt(double aTransAmnt)
	{
		TransAmnt = aTransAmnt;
	}
	/** 交易金额<P> */
	public void setTransAmnt(String aTransAmnt)
	{
		if (aTransAmnt != null && !aTransAmnt.equals(""))
		{
			Double tDouble = new Double(aTransAmnt);
			double d = tDouble.doubleValue();
			TransAmnt = d;
		}
	}

	/** 交易渠道<P> */
	public String getSourceType()
	{
		if (SourceType != null && !SourceType.equals("") && SysConst.CHANGECHARSET == true)
		{
			SourceType = StrTool.unicodeToGBK(SourceType);
		}
		return SourceType;
	}
	/** 交易渠道 */
	public void setSourceType(String aSourceType)
	{
		SourceType = aSourceType;
	}
	/** 对账流水号<P> */
	public String getBalanceNo()
	{
		if (BalanceNo != null && !BalanceNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			BalanceNo = StrTool.unicodeToGBK(BalanceNo);
		}
		return BalanceNo;
	}
	/** 对账流水号 */
	public void setBalanceNo(String aBalanceNo)
	{
		BalanceNo = aBalanceNo;
	}
	/** 成功标志<P> */
	public String getConfigFlag()
	{
		if (ConfigFlag != null && !ConfigFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ConfigFlag = StrTool.unicodeToGBK(ConfigFlag);
		}
		return ConfigFlag;
	}
	/** 成功标志 */
	public void setConfigFlag(String aConfigFlag)
	{
		ConfigFlag = aConfigFlag;
	}
	/** 备注1<P> */
	public String getTemp1()
	{
		if (Temp1 != null && !Temp1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp1 = StrTool.unicodeToGBK(Temp1);
		}
		return Temp1;
	}
	/** 备注1 */
	public void setTemp1(String aTemp1)
	{
		Temp1 = aTemp1;
	}
	/** 备注2<P> */
	public String getTemp2()
	{
		if (Temp2 != null && !Temp2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp2 = StrTool.unicodeToGBK(Temp2);
		}
		return Temp2;
	}
	/** 备注2 */
	public void setTemp2(String aTemp2)
	{
		Temp2 = aTemp2;
	}
	/** 备注3<P> */
	public String getTemp3()
	{
		if (Temp3 != null && !Temp3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp3 = StrTool.unicodeToGBK(Temp3);
		}
		return Temp3;
	}
	/** 备注3 */
	public void setTemp3(String aTemp3)
	{
		Temp3 = aTemp3;
	}
	/** 备注4<P> */
	public String getTemp4()
	{
		if (Temp4 != null && !Temp4.equals("") && SysConst.CHANGECHARSET == true)
		{
			Temp4 = StrTool.unicodeToGBK(Temp4);
		}
		return Temp4;
	}
	/** 备注4 */
	public void setTemp4(String aTemp4)
	{
		Temp4 = aTemp4;
	}
	/** 创建日期<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 创建日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 创建日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
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
	/** 修改日期<P> */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 EndorseDetailSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(EndorseDetailSchema aEndorseDetailSchema)
	{
		this.TransDate = fDate.getDate( aEndorseDetailSchema.getTransDate());
		this.BankCode = aEndorseDetailSchema.getBankCode();
		this.ZoneNo = aEndorseDetailSchema.getZoneNo();
		this.BankNode = aEndorseDetailSchema.getBankNode();
		this.TellerNo = aEndorseDetailSchema.getTellerNo();
		this.TransNo = aEndorseDetailSchema.getTransNo();
		this.Type = aEndorseDetailSchema.getType();
		this.state = aEndorseDetailSchema.getstate();
		this.FuncFlag = aEndorseDetailSchema.getFuncFlag();
		this.ContNo = aEndorseDetailSchema.getContNo();
		this.TransAmnt = aEndorseDetailSchema.getTransAmnt();
		this.SourceType = aEndorseDetailSchema.getSourceType();
		this.BalanceNo = aEndorseDetailSchema.getBalanceNo();
		this.ConfigFlag = aEndorseDetailSchema.getConfigFlag();
		this.Temp1 = aEndorseDetailSchema.getTemp1();
		this.Temp2 = aEndorseDetailSchema.getTemp2();
		this.Temp3 = aEndorseDetailSchema.getTemp3();
		this.Temp4 = aEndorseDetailSchema.getTemp4();
		this.MakeDate = fDate.getDate( aEndorseDetailSchema.getMakeDate());
		this.MakeTime = aEndorseDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aEndorseDetailSchema.getModifyDate());
		this.ModifyTime = aEndorseDetailSchema.getModifyTime();
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
			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("BankNode") == null )
				this.BankNode = null;
			else
				this.BankNode = rs.getString("BankNode").trim();

			if( rs.getString("TellerNo") == null )
				this.TellerNo = null;
			else
				this.TellerNo = rs.getString("TellerNo").trim();

			if( rs.getString("TransNo") == null )
				this.TransNo = null;
			else
				this.TransNo = rs.getString("TransNo").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("state") == null )
				this.state = null;
			else
				this.state = rs.getString("state").trim();

			if( rs.getString("FuncFlag") == null )
				this.FuncFlag = null;
			else
				this.FuncFlag = rs.getString("FuncFlag").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.TransAmnt = rs.getDouble("TransAmnt");
			if( rs.getString("SourceType") == null )
				this.SourceType = null;
			else
				this.SourceType = rs.getString("SourceType").trim();

			if( rs.getString("BalanceNo") == null )
				this.BalanceNo = null;
			else
				this.BalanceNo = rs.getString("BalanceNo").trim();

			if( rs.getString("ConfigFlag") == null )
				this.ConfigFlag = null;
			else
				this.ConfigFlag = rs.getString("ConfigFlag").trim();

			if( rs.getString("Temp1") == null )
				this.Temp1 = null;
			else
				this.Temp1 = rs.getString("Temp1").trim();

			if( rs.getString("Temp2") == null )
				this.Temp2 = null;
			else
				this.Temp2 = rs.getString("Temp2").trim();

			if( rs.getString("Temp3") == null )
				this.Temp3 = null;
			else
				this.Temp3 = rs.getString("Temp3").trim();

			if( rs.getString("Temp4") == null )
				this.Temp4 = null;
			else
				this.Temp4 = rs.getString("Temp4").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EndorseDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public EndorseDetailSchema getSchema()
	{
		EndorseDetailSchema aEndorseDetailSchema = new EndorseDetailSchema();
		aEndorseDetailSchema.setSchema(this);
		return aEndorseDetailSchema;
	}

	public EndorseDetailDB getDB()
	{
		EndorseDetailDB aDBOper = new EndorseDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEndorseDetail描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( TransDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankNode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TransNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Type) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(state) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FuncFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TransAmnt) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SourceType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BalanceNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ConfigFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Temp4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEndorseDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1,SysConst.PACKAGESPILTER));
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BankNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TransNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			state = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FuncFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TransAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			SourceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BalanceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ConfigFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Temp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Temp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Temp3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Temp4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EndorseDetailSchema";
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
		if (FCode.equals("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equals("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("BankNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankNode));
		}
		if (FCode.equals("TellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerNo));
		}
		if (FCode.equals("TransNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransNo));
		}
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("state"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(state));
		}
		if (FCode.equals("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("TransAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransAmnt));
		}
		if (FCode.equals("SourceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceType));
		}
		if (FCode.equals("BalanceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceNo));
		}
		if (FCode.equals("ConfigFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfigFlag));
		}
		if (FCode.equals("Temp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp1));
		}
		if (FCode.equals("Temp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp2));
		}
		if (FCode.equals("Temp3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp3));
		}
		if (FCode.equals("Temp4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp4));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BankNode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TellerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TransNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(state);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FuncFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 10:
				strFieldValue = String.valueOf(TransAmnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SourceType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BalanceNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ConfigFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Temp1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Temp2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Temp3);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Temp4);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
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

		if (FCode.equals("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equals("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equals("ZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneNo = FValue.trim();
			}
			else
				ZoneNo = null;
		}
		if (FCode.equals("BankNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankNode = FValue.trim();
			}
			else
				BankNode = null;
		}
		if (FCode.equals("TellerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerNo = FValue.trim();
			}
			else
				TellerNo = null;
		}
		if (FCode.equals("TransNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransNo = FValue.trim();
			}
			else
				TransNo = null;
		}
		if (FCode.equals("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equals("state"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				state = FValue.trim();
			}
			else
				state = null;
		}
		if (FCode.equals("FuncFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FuncFlag = FValue.trim();
			}
			else
				FuncFlag = null;
		}
		if (FCode.equals("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equals("TransAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransAmnt = d;
			}
		}
		if (FCode.equals("SourceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceType = FValue.trim();
			}
			else
				SourceType = null;
		}
		if (FCode.equals("BalanceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceNo = FValue.trim();
			}
			else
				BalanceNo = null;
		}
		if (FCode.equals("ConfigFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfigFlag = FValue.trim();
			}
			else
				ConfigFlag = null;
		}
		if (FCode.equals("Temp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp1 = FValue.trim();
			}
			else
				Temp1 = null;
		}
		if (FCode.equals("Temp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp2 = FValue.trim();
			}
			else
				Temp2 = null;
		}
		if (FCode.equals("Temp3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp3 = FValue.trim();
			}
			else
				Temp3 = null;
		}
		if (FCode.equals("Temp4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp4 = FValue.trim();
			}
			else
				Temp4 = null;
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
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
		EndorseDetailSchema other = (EndorseDetailSchema)otherObject;
		return
			fDate.getString(TransDate).equals(other.getTransDate())
			&& BankCode.equals(other.getBankCode())
			&& ZoneNo.equals(other.getZoneNo())
			&& BankNode.equals(other.getBankNode())
			&& TellerNo.equals(other.getTellerNo())
			&& TransNo.equals(other.getTransNo())
			&& Type.equals(other.getType())
			&& state.equals(other.getstate())
			&& FuncFlag.equals(other.getFuncFlag())
			&& ContNo.equals(other.getContNo())
			&& TransAmnt == other.getTransAmnt()
			&& SourceType.equals(other.getSourceType())
			&& BalanceNo.equals(other.getBalanceNo())
			&& ConfigFlag.equals(other.getConfigFlag())
			&& Temp1.equals(other.getTemp1())
			&& Temp2.equals(other.getTemp2())
			&& Temp3.equals(other.getTemp3())
			&& Temp4.equals(other.getTemp4())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
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
		if( strFieldName.equals("TransDate") ) {
			return 0;
		}
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("BankNode") ) {
			return 3;
		}
		if( strFieldName.equals("TellerNo") ) {
			return 4;
		}
		if( strFieldName.equals("TransNo") ) {
			return 5;
		}
		if( strFieldName.equals("Type") ) {
			return 6;
		}
		if( strFieldName.equals("state") ) {
			return 7;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 8;
		}
		if( strFieldName.equals("ContNo") ) {
			return 9;
		}
		if( strFieldName.equals("TransAmnt") ) {
			return 10;
		}
		if( strFieldName.equals("SourceType") ) {
			return 11;
		}
		if( strFieldName.equals("BalanceNo") ) {
			return 12;
		}
		if( strFieldName.equals("ConfigFlag") ) {
			return 13;
		}
		if( strFieldName.equals("Temp1") ) {
			return 14;
		}
		if( strFieldName.equals("Temp2") ) {
			return 15;
		}
		if( strFieldName.equals("Temp3") ) {
			return 16;
		}
		if( strFieldName.equals("Temp4") ) {
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
				strFieldName = "TransDate";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "BankNode";
				break;
			case 4:
				strFieldName = "TellerNo";
				break;
			case 5:
				strFieldName = "TransNo";
				break;
			case 6:
				strFieldName = "Type";
				break;
			case 7:
				strFieldName = "state";
				break;
			case 8:
				strFieldName = "FuncFlag";
				break;
			case 9:
				strFieldName = "ContNo";
				break;
			case 10:
				strFieldName = "TransAmnt";
				break;
			case 11:
				strFieldName = "SourceType";
				break;
			case 12:
				strFieldName = "BalanceNo";
				break;
			case 13:
				strFieldName = "ConfigFlag";
				break;
			case 14:
				strFieldName = "Temp1";
				break;
			case 15:
				strFieldName = "Temp2";
				break;
			case 16:
				strFieldName = "Temp3";
				break;
			case 17:
				strFieldName = "Temp4";
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
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("state") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SourceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfigFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
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
