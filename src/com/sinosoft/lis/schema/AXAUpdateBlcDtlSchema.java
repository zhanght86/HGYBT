/*
 * <p>ClassName: AXAUpdateBlcDtlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-28
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.AXAUpdateBlcDtlDB;

public class AXAUpdateBlcDtlSchema implements Schema
{
	// @Field
	/** 对账批次号 */
	private int BlcTranNo;
	/** Blctype */
	private int BlcType;
	/** 保单号 */
	private String ContNo;
	/** Orderno */
	private int OrderNo;
	/** 投保单号 */
	private String ProposalContNo;
	/** 更新批次号 */
	private String SerialNo;
	/** 交易日期 */
	private Date TranDate;
	/** 交易流水 */
	private String TranNo;
	/** 状态 */
	private int State;
	/** 对账结果 */
	private int RCode;
	/** 结果描述 */
	private String RText;
	/** 备用1 */
	private String Bak1;
	/** 备用2 */
	private String Bak2;
	/** 备用3 */
	private String Bak3;
	/** 入库日期 */
	private Date MakeDate;
	/** 入库时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AXAUpdateBlcDtlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BlcTranNo";
		pk[1] = "BlcType";
		pk[2] = "ContNo";
		pk[3] = "OrderNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 对账批次号<P> */
	public int getBlcTranNo()
	{
		return BlcTranNo;
	}
	/** 对账批次号 */
	public void setBlcTranNo(int aBlcTranNo)
	{
		BlcTranNo = aBlcTranNo;
	}
	/** 对账批次号<P> */
	public void setBlcTranNo(String aBlcTranNo)
	{
		if (aBlcTranNo != null && !aBlcTranNo.equals(""))
		{
			Integer tInteger = new Integer(aBlcTranNo);
			int i = tInteger.intValue();
			BlcTranNo = i;
		}
	}

	/** Blctype<P> */
	public int getBlcType()
	{
		return BlcType;
	}
	/** Blctype */
	public void setBlcType(int aBlcType)
	{
		BlcType = aBlcType;
	}
	/** Blctype<P> */
	public void setBlcType(String aBlcType)
	{
		if (aBlcType != null && !aBlcType.equals(""))
		{
			Integer tInteger = new Integer(aBlcType);
			int i = tInteger.intValue();
			BlcType = i;
		}
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
	/** Orderno<P> */
	public int getOrderNo()
	{
		return OrderNo;
	}
	/** Orderno */
	public void setOrderNo(int aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	/** Orderno<P> */
	public void setOrderNo(String aOrderNo)
	{
		if (aOrderNo != null && !aOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aOrderNo);
			int i = tInteger.intValue();
			OrderNo = i;
		}
	}

	/** 投保单号<P> */
	public String getProposalContNo()
	{
		if (ProposalContNo != null && !ProposalContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalContNo = StrTool.unicodeToGBK(ProposalContNo);
		}
		return ProposalContNo;
	}
	/** 投保单号 */
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/** 更新批次号<P> */
	public String getSerialNo()
	{
		if (SerialNo != null && !SerialNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SerialNo = StrTool.unicodeToGBK(SerialNo);
		}
		return SerialNo;
	}
	/** 更新批次号 */
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/** 交易日期<P> */
	public String getTranDate()
	{
		if( TranDate != null )
			return fDate.getString(TranDate);
		else
			return null;
	}
	/** 交易日期 */
	public void setTranDate(Date aTranDate)
	{
		TranDate = aTranDate;
	}
	/** 交易日期<P> */
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals("") )
		{
			TranDate = fDate.getDate( aTranDate );
		}
		else
			TranDate = null;
	}

	/** 交易流水<P> */
	public String getTranNo()
	{
		if (TranNo != null && !TranNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranNo = StrTool.unicodeToGBK(TranNo);
		}
		return TranNo;
	}
	/** 交易流水 */
	public void setTranNo(String aTranNo)
	{
		TranNo = aTranNo;
	}
	/** 状态<P> */
	public int getState()
	{
		return State;
	}
	/** 状态 */
	public void setState(int aState)
	{
		State = aState;
	}
	/** 状态<P> */
	public void setState(String aState)
	{
		if (aState != null && !aState.equals(""))
		{
			Integer tInteger = new Integer(aState);
			int i = tInteger.intValue();
			State = i;
		}
	}

	/** 对账结果<P>0-成功；1-失败 */
	public int getRCode()
	{
		return RCode;
	}
	/** 对账结果 */
	public void setRCode(int aRCode)
	{
		RCode = aRCode;
	}
	/** 对账结果<P>0-成功；1-失败 */
	public void setRCode(String aRCode)
	{
		if (aRCode != null && !aRCode.equals(""))
		{
			Integer tInteger = new Integer(aRCode);
			int i = tInteger.intValue();
			RCode = i;
		}
	}

	/** 结果描述<P> */
	public String getRText()
	{
		if (RText != null && !RText.equals("") && SysConst.CHANGECHARSET == true)
		{
			RText = StrTool.unicodeToGBK(RText);
		}
		return RText;
	}
	/** 结果描述 */
	public void setRText(String aRText)
	{
		RText = aRText;
	}
	/** 备用1<P> */
	public String getBak1()
	{
		if (Bak1 != null && !Bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak1 = StrTool.unicodeToGBK(Bak1);
		}
		return Bak1;
	}
	/** 备用1 */
	public void setBak1(String aBak1)
	{
		Bak1 = aBak1;
	}
	/** 备用2<P> */
	public String getBak2()
	{
		if (Bak2 != null && !Bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak2 = StrTool.unicodeToGBK(Bak2);
		}
		return Bak2;
	}
	/** 备用2 */
	public void setBak2(String aBak2)
	{
		Bak2 = aBak2;
	}
	/** 备用3<P> */
	public String getBak3()
	{
		if (Bak3 != null && !Bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak3 = StrTool.unicodeToGBK(Bak3);
		}
		return Bak3;
	}
	/** 备用3 */
	public void setBak3(String aBak3)
	{
		Bak3 = aBak3;
	}
	/** 入库日期<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 入库日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入库日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 入库时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 入库时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 最后修改日期<P> */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 最后修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 最后修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 操作员<P> */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 操作员 */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}

	/**
	* 使用另外一个 AXAUpdateBlcDtlSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AXAUpdateBlcDtlSchema aAXAUpdateBlcDtlSchema)
	{
		this.BlcTranNo = aAXAUpdateBlcDtlSchema.getBlcTranNo();
		this.BlcType = aAXAUpdateBlcDtlSchema.getBlcType();
		this.ContNo = aAXAUpdateBlcDtlSchema.getContNo();
		this.OrderNo = aAXAUpdateBlcDtlSchema.getOrderNo();
		this.ProposalContNo = aAXAUpdateBlcDtlSchema.getProposalContNo();
		this.SerialNo = aAXAUpdateBlcDtlSchema.getSerialNo();
		this.TranDate = fDate.getDate( aAXAUpdateBlcDtlSchema.getTranDate());
		this.TranNo = aAXAUpdateBlcDtlSchema.getTranNo();
		this.State = aAXAUpdateBlcDtlSchema.getState();
		this.RCode = aAXAUpdateBlcDtlSchema.getRCode();
		this.RText = aAXAUpdateBlcDtlSchema.getRText();
		this.Bak1 = aAXAUpdateBlcDtlSchema.getBak1();
		this.Bak2 = aAXAUpdateBlcDtlSchema.getBak2();
		this.Bak3 = aAXAUpdateBlcDtlSchema.getBak3();
		this.MakeDate = fDate.getDate( aAXAUpdateBlcDtlSchema.getMakeDate());
		this.MakeTime = aAXAUpdateBlcDtlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aAXAUpdateBlcDtlSchema.getModifyDate());
		this.ModifyTime = aAXAUpdateBlcDtlSchema.getModifyTime();
		this.Operator = aAXAUpdateBlcDtlSchema.getOperator();
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
			this.BlcTranNo = rs.getInt("BlcTranNo");
			this.BlcType = rs.getInt("BlcType");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.OrderNo = rs.getInt("OrderNo");
			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.TranDate = rs.getDate("TranDate");
			if( rs.getString("TranNo") == null )
				this.TranNo = null;
			else
				this.TranNo = rs.getString("TranNo").trim();

			this.State = rs.getInt("State");
			this.RCode = rs.getInt("RCode");
			if( rs.getString("RText") == null )
				this.RText = null;
			else
				this.RText = rs.getString("RText").trim();

			if( rs.getString("Bak1") == null )
				this.Bak1 = null;
			else
				this.Bak1 = rs.getString("Bak1").trim();

			if( rs.getString("Bak2") == null )
				this.Bak2 = null;
			else
				this.Bak2 = rs.getString("Bak2").trim();

			if( rs.getString("Bak3") == null )
				this.Bak3 = null;
			else
				this.Bak3 = rs.getString("Bak3").trim();

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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAUpdateBlcDtlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AXAUpdateBlcDtlSchema getSchema()
	{
		AXAUpdateBlcDtlSchema aAXAUpdateBlcDtlSchema = new AXAUpdateBlcDtlSchema();
		aAXAUpdateBlcDtlSchema.setSchema(this);
		return aAXAUpdateBlcDtlSchema;
	}

	public AXAUpdateBlcDtlDB getDB()
	{
		AXAUpdateBlcDtlDB aDBOper = new AXAUpdateBlcDtlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAUpdateBlcDtl描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(BlcTranNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(BlcType) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(OrderNo) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SerialNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( TranDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(State) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RCode) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXAUpdateBlcDtl>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BlcTranNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			BlcType= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TranDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			TranNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			RCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AXAUpdateBlcDtlSchema";
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
		if (FCode.equals("BlcTranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlcTranNo));
		}
		if (FCode.equals("BlcType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlcType));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equals("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equals("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
		}
		if (FCode.equals("TranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranNo));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equals("RText"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RText));
		}
		if (FCode.equals("Bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak1));
		}
		if (FCode.equals("Bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak2));
		}
		if (FCode.equals("Bak3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak3));
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
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = String.valueOf(BlcTranNo);
				break;
			case 1:
				strFieldValue = String.valueOf(BlcType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = String.valueOf(OrderNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TranNo);
				break;
			case 8:
				strFieldValue = String.valueOf(State);
				break;
			case 9:
				strFieldValue = String.valueOf(RCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equals("BlcTranNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BlcTranNo = i;
			}
		}
		if (FCode.equals("BlcType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BlcType = i;
			}
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
		if (FCode.equals("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrderNo = i;
			}
		}
		if (FCode.equals("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
		}
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
			if( FValue != null && !FValue.equals("") )
			{
				TranDate = fDate.getDate( FValue );
			}
			else
				TranDate = null;
		}
		if (FCode.equals("TranNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranNo = FValue.trim();
			}
			else
				TranNo = null;
		}
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				State = i;
			}
		}
		if (FCode.equals("RCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RCode = i;
			}
		}
		if (FCode.equals("RText"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RText = FValue.trim();
			}
			else
				RText = null;
		}
		if (FCode.equals("Bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak1 = FValue.trim();
			}
			else
				Bak1 = null;
		}
		if (FCode.equals("Bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak2 = FValue.trim();
			}
			else
				Bak2 = null;
		}
		if (FCode.equals("Bak3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak3 = FValue.trim();
			}
			else
				Bak3 = null;
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
		if (FCode.equals("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AXAUpdateBlcDtlSchema other = (AXAUpdateBlcDtlSchema)otherObject;
		return
			BlcTranNo == other.getBlcTranNo()
			&& BlcType == other.getBlcType()
			&& ContNo.equals(other.getContNo())
			&& OrderNo == other.getOrderNo()
			&& ProposalContNo.equals(other.getProposalContNo())
			&& SerialNo.equals(other.getSerialNo())
			&& fDate.getString(TranDate).equals(other.getTranDate())
			&& TranNo.equals(other.getTranNo())
			&& State == other.getState()
			&& RCode == other.getRCode()
			&& RText.equals(other.getRText())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("BlcTranNo") ) {
			return 0;
		}
		if( strFieldName.equals("BlcType") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 5;
		}
		if( strFieldName.equals("TranDate") ) {
			return 6;
		}
		if( strFieldName.equals("TranNo") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("RCode") ) {
			return 9;
		}
		if( strFieldName.equals("RText") ) {
			return 10;
		}
		if( strFieldName.equals("Bak1") ) {
			return 11;
		}
		if( strFieldName.equals("Bak2") ) {
			return 12;
		}
		if( strFieldName.equals("Bak3") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
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
				strFieldName = "BlcTranNo";
				break;
			case 1:
				strFieldName = "BlcType";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "OrderNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "SerialNo";
				break;
			case 6:
				strFieldName = "TranDate";
				break;
			case 7:
				strFieldName = "TranNo";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "RCode";
				break;
			case 10:
				strFieldName = "RText";
				break;
			case 11:
				strFieldName = "Bak1";
				break;
			case 12:
				strFieldName = "Bak2";
				break;
			case 13:
				strFieldName = "Bak3";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "Operator";
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
		if( strFieldName.equals("BlcTranNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BlcType") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TranNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RText") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak3") ) {
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
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
