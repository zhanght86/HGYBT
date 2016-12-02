/*
 * <p>ClassName: NoRealBlcDtlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中韩
 * @CreateDate：2013-04-03
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.NoRealBlcDtlDB;

public class NoRealBlcDtlSchema implements Schema
{
	// @Field
	/** 对账批次号 */
	private String BlcNo;
	/** 银行编码 */
	private String TranCom;
	/** 交易日期 */
	private int TranDate;
	/** 地区代码 */
	private String ZoneNo;
	/** 交易网点 */
	private String NodeNo;
	/** 柜员号 */
	private String TellerNo;
	/** 银行交易流水号 */
	private String BankTranNo;
	/** 投保单(印刷)号 */
	private String ProposalPrtNo;
	/** 出单渠道 */
	private String SaleChannel;
	/** 核保标志 */
	private String AppFlag;
	/** 投保人姓名 */
	private String AplName;
	/** 投保人证件类型 */
	private String AplIDType;
	/** 投保人证件号 */
	private String AplIDNo;
	/** 投保人账户 */
	private String AplAccNo;
	/** 备用1 */
	private String Bak1;
	/** 备用2 */
	private String Bak2;
	/** 备用3 */
	private String Bak3;
	/** 备用4 */
	private String Bak4;
	/** 备用5 */
	private String Bak5;
	/** 入库日期 */
	private int MakeDate;
	/** 入库时间 */
	private int MakeTime;
	/** 最后修改日期 */
	private int ModifyDate;
	/** 最后修改时间 */
	private int ModifyTime;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public NoRealBlcDtlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BlcNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 对账批次号<P> */
	public String getBlcNo()
	{
		if (BlcNo != null && !BlcNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			BlcNo = StrTool.unicodeToGBK(BlcNo);
		}
		return BlcNo;
	}
	/** 对账批次号 */
	public void setBlcNo(String aBlcNo)
	{
		BlcNo = aBlcNo;
	}
	/** 银行编码<P> */
	public String getTranCom()
	{
		if (TranCom != null && !TranCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranCom = StrTool.unicodeToGBK(TranCom);
		}
		return TranCom;
	}
	/** 银行编码 */
	public void setTranCom(String aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易日期<P> */
	public int getTranDate()
	{
		return TranDate;
	}
	/** 交易日期 */
	public void setTranDate(int aTranDate)
	{
		TranDate = aTranDate;
	}
	/** 交易日期<P> */
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals(""))
		{
			Integer tInteger = new Integer(aTranDate);
			int i = tInteger.intValue();
			TranDate = i;
		}
	}

	/** 地区代码<P> */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 地区代码 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 交易网点<P> */
	public String getNodeNo()
	{
		if (NodeNo != null && !NodeNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeNo = StrTool.unicodeToGBK(NodeNo);
		}
		return NodeNo;
	}
	/** 交易网点 */
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
	}
	/** 柜员号<P> */
	public String getTellerNo()
	{
		if (TellerNo != null && !TellerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerNo = StrTool.unicodeToGBK(TellerNo);
		}
		return TellerNo;
	}
	/** 柜员号 */
	public void setTellerNo(String aTellerNo)
	{
		TellerNo = aTellerNo;
	}
	/** 银行交易流水号<P> */
	public String getBankTranNo()
	{
		if (BankTranNo != null && !BankTranNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankTranNo = StrTool.unicodeToGBK(BankTranNo);
		}
		return BankTranNo;
	}
	/** 银行交易流水号 */
	public void setBankTranNo(String aBankTranNo)
	{
		BankTranNo = aBankTranNo;
	}
	/** 投保单(印刷)号<P> */
	public String getProposalPrtNo()
	{
		if (ProposalPrtNo != null && !ProposalPrtNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalPrtNo = StrTool.unicodeToGBK(ProposalPrtNo);
		}
		return ProposalPrtNo;
	}
	/** 投保单(印刷)号 */
	public void setProposalPrtNo(String aProposalPrtNo)
	{
		ProposalPrtNo = aProposalPrtNo;
	}
	/** 出单渠道<P> */
	public String getSaleChannel()
	{
		if (SaleChannel != null && !SaleChannel.equals("") && SysConst.CHANGECHARSET == true)
		{
			SaleChannel = StrTool.unicodeToGBK(SaleChannel);
		}
		return SaleChannel;
	}
	/** 出单渠道 */
	public void setSaleChannel(String aSaleChannel)
	{
		SaleChannel = aSaleChannel;
	}
	/** 核保标志<P> */
	public String getAppFlag()
	{
		if (AppFlag != null && !AppFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppFlag = StrTool.unicodeToGBK(AppFlag);
		}
		return AppFlag;
	}
	/** 核保标志 */
	public void setAppFlag(String aAppFlag)
	{
		AppFlag = aAppFlag;
	}
	/** 投保人姓名<P> */
	public String getAplName()
	{
		if (AplName != null && !AplName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AplName = StrTool.unicodeToGBK(AplName);
		}
		return AplName;
	}
	/** 投保人姓名 */
	public void setAplName(String aAplName)
	{
		AplName = aAplName;
	}
	/** 投保人证件类型<P> */
	public String getAplIDType()
	{
		if (AplIDType != null && !AplIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AplIDType = StrTool.unicodeToGBK(AplIDType);
		}
		return AplIDType;
	}
	/** 投保人证件类型 */
	public void setAplIDType(String aAplIDType)
	{
		AplIDType = aAplIDType;
	}
	/** 投保人证件号<P> */
	public String getAplIDNo()
	{
		if (AplIDNo != null && !AplIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AplIDNo = StrTool.unicodeToGBK(AplIDNo);
		}
		return AplIDNo;
	}
	/** 投保人证件号 */
	public void setAplIDNo(String aAplIDNo)
	{
		AplIDNo = aAplIDNo;
	}
	/** 投保人账户<P> */
	public String getAplAccNo()
	{
		if (AplAccNo != null && !AplAccNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AplAccNo = StrTool.unicodeToGBK(AplAccNo);
		}
		return AplAccNo;
	}
	/** 投保人账户 */
	public void setAplAccNo(String aAplAccNo)
	{
		AplAccNo = aAplAccNo;
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
	/** 备用4<P> */
	public String getBak4()
	{
		if (Bak4 != null && !Bak4.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak4 = StrTool.unicodeToGBK(Bak4);
		}
		return Bak4;
	}
	/** 备用4 */
	public void setBak4(String aBak4)
	{
		Bak4 = aBak4;
	}
	/** 备用5<P> */
	public String getBak5()
	{
		if (Bak5 != null && !Bak5.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak5 = StrTool.unicodeToGBK(Bak5);
		}
		return Bak5;
	}
	/** 备用5 */
	public void setBak5(String aBak5)
	{
		Bak5 = aBak5;
	}
	/** 入库日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 入库日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入库日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** 入库时间<P> */
	public int getMakeTime()
	{
		return MakeTime;
	}
	/** 入库时间 */
	public void setMakeTime(int aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 入库时间<P> */
	public void setMakeTime(String aMakeTime)
	{
		if (aMakeTime != null && !aMakeTime.equals(""))
		{
			Integer tInteger = new Integer(aMakeTime);
			int i = tInteger.intValue();
			MakeTime = i;
		}
	}

	/** 最后修改日期<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** 最后修改日期 */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** 最后修改时间<P> */
	public int getModifyTime()
	{
		return ModifyTime;
	}
	/** 最后修改时间 */
	public void setModifyTime(int aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 最后修改时间<P> */
	public void setModifyTime(String aModifyTime)
	{
		if (aModifyTime != null && !aModifyTime.equals(""))
		{
			Integer tInteger = new Integer(aModifyTime);
			int i = tInteger.intValue();
			ModifyTime = i;
		}
	}


	/**
	* 使用另外一个 NoRealBlcDtlSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(NoRealBlcDtlSchema aNoRealBlcDtlSchema)
	{
		this.BlcNo = aNoRealBlcDtlSchema.getBlcNo();
		this.TranCom = aNoRealBlcDtlSchema.getTranCom();
		this.TranDate = aNoRealBlcDtlSchema.getTranDate();
		this.ZoneNo = aNoRealBlcDtlSchema.getZoneNo();
		this.NodeNo = aNoRealBlcDtlSchema.getNodeNo();
		this.TellerNo = aNoRealBlcDtlSchema.getTellerNo();
		this.BankTranNo = aNoRealBlcDtlSchema.getBankTranNo();
		this.ProposalPrtNo = aNoRealBlcDtlSchema.getProposalPrtNo();
		this.SaleChannel = aNoRealBlcDtlSchema.getSaleChannel();
		this.AppFlag = aNoRealBlcDtlSchema.getAppFlag();
		this.AplName = aNoRealBlcDtlSchema.getAplName();
		this.AplIDType = aNoRealBlcDtlSchema.getAplIDType();
		this.AplIDNo = aNoRealBlcDtlSchema.getAplIDNo();
		this.AplAccNo = aNoRealBlcDtlSchema.getAplAccNo();
		this.Bak1 = aNoRealBlcDtlSchema.getBak1();
		this.Bak2 = aNoRealBlcDtlSchema.getBak2();
		this.Bak3 = aNoRealBlcDtlSchema.getBak3();
		this.Bak4 = aNoRealBlcDtlSchema.getBak4();
		this.Bak5 = aNoRealBlcDtlSchema.getBak5();
		this.MakeDate = aNoRealBlcDtlSchema.getMakeDate();
		this.MakeTime = aNoRealBlcDtlSchema.getMakeTime();
		this.ModifyDate = aNoRealBlcDtlSchema.getModifyDate();
		this.ModifyTime = aNoRealBlcDtlSchema.getModifyTime();
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
			if( rs.getString("BlcNo") == null )
				this.BlcNo = null;
			else
				this.BlcNo = rs.getString("BlcNo").trim();

			if( rs.getString("TranCom") == null )
				this.TranCom = null;
			else
				this.TranCom = rs.getString("TranCom").trim();

			this.TranDate = rs.getInt("TranDate");
			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("TellerNo") == null )
				this.TellerNo = null;
			else
				this.TellerNo = rs.getString("TellerNo").trim();

			if( rs.getString("BankTranNo") == null )
				this.BankTranNo = null;
			else
				this.BankTranNo = rs.getString("BankTranNo").trim();

			if( rs.getString("ProposalPrtNo") == null )
				this.ProposalPrtNo = null;
			else
				this.ProposalPrtNo = rs.getString("ProposalPrtNo").trim();

			if( rs.getString("SaleChannel") == null )
				this.SaleChannel = null;
			else
				this.SaleChannel = rs.getString("SaleChannel").trim();

			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("AplName") == null )
				this.AplName = null;
			else
				this.AplName = rs.getString("AplName").trim();

			if( rs.getString("AplIDType") == null )
				this.AplIDType = null;
			else
				this.AplIDType = rs.getString("AplIDType").trim();

			if( rs.getString("AplIDNo") == null )
				this.AplIDNo = null;
			else
				this.AplIDNo = rs.getString("AplIDNo").trim();

			if( rs.getString("AplAccNo") == null )
				this.AplAccNo = null;
			else
				this.AplAccNo = rs.getString("AplAccNo").trim();

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

			if( rs.getString("Bak4") == null )
				this.Bak4 = null;
			else
				this.Bak4 = rs.getString("Bak4").trim();

			if( rs.getString("Bak5") == null )
				this.Bak5 = null;
			else
				this.Bak5 = rs.getString("Bak5").trim();

			this.MakeDate = rs.getInt("MakeDate");
			this.MakeTime = rs.getInt("MakeTime");
			this.ModifyDate = rs.getInt("ModifyDate");
			this.ModifyTime = rs.getInt("ModifyTime");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NoRealBlcDtlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public NoRealBlcDtlSchema getSchema()
	{
		NoRealBlcDtlSchema aNoRealBlcDtlSchema = new NoRealBlcDtlSchema();
		aNoRealBlcDtlSchema.setSchema(this);
		return aNoRealBlcDtlSchema;
	}

	public NoRealBlcDtlDB getDB()
	{
		NoRealBlcDtlDB aDBOper = new NoRealBlcDtlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNoRealBlcDtl描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(BlcNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranCom) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankTranNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalPrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SaleChannel) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AplName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AplIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AplIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AplAccNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNoRealBlcDtl>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BlcNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TranCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TranDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BankTranNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ProposalPrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SaleChannel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AplName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AplIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AplIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AplAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NoRealBlcDtlSchema";
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
		if (FCode.equals("BlcNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlcNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equals("TellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerNo));
		}
		if (FCode.equals("BankTranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankTranNo));
		}
		if (FCode.equals("ProposalPrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalPrtNo));
		}
		if (FCode.equals("SaleChannel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChannel));
		}
		if (FCode.equals("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equals("AplName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AplName));
		}
		if (FCode.equals("AplIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AplIDType));
		}
		if (FCode.equals("AplIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AplIDNo));
		}
		if (FCode.equals("AplAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AplAccNo));
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
		if (FCode.equals("Bak4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak4));
		}
		if (FCode.equals("Bak5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak5));
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
				strFieldValue = StrTool.GBKToUnicode(BlcNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TranCom);
				break;
			case 2:
				strFieldValue = String.valueOf(TranDate);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TellerNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BankTranNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ProposalPrtNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SaleChannel);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AplName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AplIDType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AplIDNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AplAccNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 19:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 20:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 21:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 22:
				strFieldValue = String.valueOf(ModifyTime);
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

		if (FCode.equals("BlcNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlcNo = FValue.trim();
			}
			else
				BlcNo = null;
		}
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranCom = FValue.trim();
			}
			else
				TranCom = null;
		}
		if (FCode.equals("TranDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranDate = i;
			}
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
		if (FCode.equals("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
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
		if (FCode.equals("BankTranNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankTranNo = FValue.trim();
			}
			else
				BankTranNo = null;
		}
		if (FCode.equals("ProposalPrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalPrtNo = FValue.trim();
			}
			else
				ProposalPrtNo = null;
		}
		if (FCode.equals("SaleChannel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChannel = FValue.trim();
			}
			else
				SaleChannel = null;
		}
		if (FCode.equals("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
		}
		if (FCode.equals("AplName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AplName = FValue.trim();
			}
			else
				AplName = null;
		}
		if (FCode.equals("AplIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AplIDType = FValue.trim();
			}
			else
				AplIDType = null;
		}
		if (FCode.equals("AplIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AplIDNo = FValue.trim();
			}
			else
				AplIDNo = null;
		}
		if (FCode.equals("AplAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AplAccNo = FValue.trim();
			}
			else
				AplAccNo = null;
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
		if (FCode.equals("Bak4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak4 = FValue.trim();
			}
			else
				Bak4 = null;
		}
		if (FCode.equals("Bak5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak5 = FValue.trim();
			}
			else
				Bak5 = null;
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MakeTime = i;
			}
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyTime = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		NoRealBlcDtlSchema other = (NoRealBlcDtlSchema)otherObject;
		return
			BlcNo.equals(other.getBlcNo())
			&& TranCom.equals(other.getTranCom())
			&& TranDate == other.getTranDate()
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& TellerNo.equals(other.getTellerNo())
			&& BankTranNo.equals(other.getBankTranNo())
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& SaleChannel.equals(other.getSaleChannel())
			&& AppFlag.equals(other.getAppFlag())
			&& AplName.equals(other.getAplName())
			&& AplIDType.equals(other.getAplIDType())
			&& AplIDNo.equals(other.getAplIDNo())
			&& AplAccNo.equals(other.getAplAccNo())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& MakeDate == other.getMakeDate()
			&& MakeTime == other.getMakeTime()
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime == other.getModifyTime();
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
		if( strFieldName.equals("BlcNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("TranDate") ) {
			return 2;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 3;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 4;
		}
		if( strFieldName.equals("TellerNo") ) {
			return 5;
		}
		if( strFieldName.equals("BankTranNo") ) {
			return 6;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return 7;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return 8;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 9;
		}
		if( strFieldName.equals("AplName") ) {
			return 10;
		}
		if( strFieldName.equals("AplIDType") ) {
			return 11;
		}
		if( strFieldName.equals("AplIDNo") ) {
			return 12;
		}
		if( strFieldName.equals("AplAccNo") ) {
			return 13;
		}
		if( strFieldName.equals("Bak1") ) {
			return 14;
		}
		if( strFieldName.equals("Bak2") ) {
			return 15;
		}
		if( strFieldName.equals("Bak3") ) {
			return 16;
		}
		if( strFieldName.equals("Bak4") ) {
			return 17;
		}
		if( strFieldName.equals("Bak5") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "BlcNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "TranDate";
				break;
			case 3:
				strFieldName = "ZoneNo";
				break;
			case 4:
				strFieldName = "NodeNo";
				break;
			case 5:
				strFieldName = "TellerNo";
				break;
			case 6:
				strFieldName = "BankTranNo";
				break;
			case 7:
				strFieldName = "ProposalPrtNo";
				break;
			case 8:
				strFieldName = "SaleChannel";
				break;
			case 9:
				strFieldName = "AppFlag";
				break;
			case 10:
				strFieldName = "AplName";
				break;
			case 11:
				strFieldName = "AplIDType";
				break;
			case 12:
				strFieldName = "AplIDNo";
				break;
			case 13:
				strFieldName = "AplAccNo";
				break;
			case 14:
				strFieldName = "Bak1";
				break;
			case 15:
				strFieldName = "Bak2";
				break;
			case 16:
				strFieldName = "Bak3";
				break;
			case 17:
				strFieldName = "Bak4";
				break;
			case 18:
				strFieldName = "Bak5";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("BlcNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankTranNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AplName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AplIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AplIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AplAccNo") ) {
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
		if( strFieldName.equals("Bak4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
