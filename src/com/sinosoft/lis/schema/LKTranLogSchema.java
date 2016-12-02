/*
 * <p>ClassName: LKTranLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LKTranLogDB;

public class LKTranLogSchema implements Schema
{
	// @Field
	/** 日志号 */
	private int LogNo;
	/** 交易单位 */
	private int TranCom;
	/** 地区代码 */
	private String ZoneNo;
	/** 交易网点 */
	private String NodeNo;
	/** 交易流水号 */
	private String TranNo;
	/** 操作员 */
	private String Operator;
	/** 交易类型 */
	private int FuncFlag;
	/** 交易日期 */
	private Date TranDate;
	/** 交易时间 */
	private String TranTime;
	/** 投保单(印刷)号 */
	private String ProposalPrtNo;
	/** 保单号 */
	private String ContNo;
	/** 其他关联号 */
	private String OtherNo;
	/** 代理机构 */
	private String AgentCom;
	/** 代理人 */
	private String AgentCode;
	/** 管理机构 */
	private String ManageCom;
	/** 交易结果 */
	private int RCode;
	/** 结果描述 */
	private String RText;
	/** 服务耗时 */
	private int UsedTime;
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
	private Date MakeDate;
	/** 入库时间 */
	private String MakeTime;
	/** Modifydate */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKTranLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 日志号<P>日志号 */
	public int getLogNo()
	{
		return LogNo;
	}
	/** 日志号 */
	public void setLogNo(int aLogNo)
	{
		LogNo = aLogNo;
	}
	/** 日志号<P>日志号 */
	public void setLogNo(String aLogNo)
	{
		if (aLogNo != null && !aLogNo.equals(""))
		{
			Integer tInteger = new Integer(aLogNo);
			int i = tInteger.intValue();
			LogNo = i;
		}
	}

	/** 交易单位<P>交易单位 */
	public int getTranCom()
	{
		return TranCom;
	}
	/** 交易单位 */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易单位<P>交易单位 */
	public void setTranCom(String aTranCom)
	{
		if (aTranCom != null && !aTranCom.equals(""))
		{
			Integer tInteger = new Integer(aTranCom);
			int i = tInteger.intValue();
			TranCom = i;
		}
	}

	/** 地区代码<P>地区代码 */
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
	/** 交易网点<P>交易网点 */
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
	/** 交易流水号<P>交易流水号 */
	public String getTranNo()
	{
		if (TranNo != null && !TranNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranNo = StrTool.unicodeToGBK(TranNo);
		}
		return TranNo;
	}
	/** 交易流水号 */
	public void setTranNo(String aTranNo)
	{
		TranNo = aTranNo;
	}
	/** 操作员<P>操作员 */
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
	/** 交易类型<P>交易类型 */
	public int getFuncFlag()
	{
		return FuncFlag;
	}
	/** 交易类型 */
	public void setFuncFlag(int aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	/** 交易类型<P>交易类型 */
	public void setFuncFlag(String aFuncFlag)
	{
		if (aFuncFlag != null && !aFuncFlag.equals(""))
		{
			Integer tInteger = new Integer(aFuncFlag);
			int i = tInteger.intValue();
			FuncFlag = i;
		}
	}

	/** 交易日期<P>交易日期 */
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
	/** 交易日期<P>交易日期 */
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals("") )
		{
			TranDate = fDate.getDate( aTranDate );
		}
		else
			TranDate = null;
	}

	/** 交易时间<P>交易时间 */
	public String getTranTime()
	{
		if (TranTime != null && !TranTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranTime = StrTool.unicodeToGBK(TranTime);
		}
		return TranTime;
	}
	/** 交易时间 */
	public void setTranTime(String aTranTime)
	{
		TranTime = aTranTime;
	}
	/** 投保单(印刷)号<P>投保单(印刷)号 */
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
	/** 保单号<P>保单号 */
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
	/** 其他关联号<P>其他相关联的号，如保单合同书印刷号、批单号等 */
	public String getOtherNo()
	{
		if (OtherNo != null && !OtherNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			OtherNo = StrTool.unicodeToGBK(OtherNo);
		}
		return OtherNo;
	}
	/** 其他关联号 */
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/** 代理机构<P>代理机构 */
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
	/** 代理人<P>代理人 */
	public String getAgentCode()
	{
		if (AgentCode != null && !AgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode = StrTool.unicodeToGBK(AgentCode);
		}
		return AgentCode;
	}
	/** 代理人 */
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/** 管理机构<P>管理机构 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 管理机构 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** 交易结果<P>0-成功；1-失败 */
	public int getRCode()
	{
		return RCode;
	}
	/** 交易结果 */
	public void setRCode(int aRCode)
	{
		RCode = aRCode;
	}
	/** 交易结果<P>0-成功；1-失败 */
	public void setRCode(String aRCode)
	{
		if (aRCode != null && !aRCode.equals(""))
		{
			Integer tInteger = new Integer(aRCode);
			int i = tInteger.intValue();
			RCode = i;
		}
	}

	/** 结果描述<P>结果描述 */
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
	/** 服务耗时<P>服务耗时，单位秒(s) */
	public int getUsedTime()
	{
		return UsedTime;
	}
	/** 服务耗时 */
	public void setUsedTime(int aUsedTime)
	{
		UsedTime = aUsedTime;
	}
	/** 服务耗时<P>服务耗时，单位秒(s) */
	public void setUsedTime(String aUsedTime)
	{
		if (aUsedTime != null && !aUsedTime.equals(""))
		{
			Integer tInteger = new Integer(aUsedTime);
			int i = tInteger.intValue();
			UsedTime = i;
		}
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
	/** Modifydate<P> */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** Modifydate */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** Modifydate<P> */
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

	/**
	* 使用另外一个 LKTranLogSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKTranLogSchema aLKTranLogSchema)
	{
		this.LogNo = aLKTranLogSchema.getLogNo();
		this.TranCom = aLKTranLogSchema.getTranCom();
		this.ZoneNo = aLKTranLogSchema.getZoneNo();
		this.NodeNo = aLKTranLogSchema.getNodeNo();
		this.TranNo = aLKTranLogSchema.getTranNo();
		this.Operator = aLKTranLogSchema.getOperator();
		this.FuncFlag = aLKTranLogSchema.getFuncFlag();
		this.TranDate = fDate.getDate( aLKTranLogSchema.getTranDate());
		this.TranTime = aLKTranLogSchema.getTranTime();
		this.ProposalPrtNo = aLKTranLogSchema.getProposalPrtNo();
		this.ContNo = aLKTranLogSchema.getContNo();
		this.OtherNo = aLKTranLogSchema.getOtherNo();
		this.AgentCom = aLKTranLogSchema.getAgentCom();
		this.AgentCode = aLKTranLogSchema.getAgentCode();
		this.ManageCom = aLKTranLogSchema.getManageCom();
		this.RCode = aLKTranLogSchema.getRCode();
		this.RText = aLKTranLogSchema.getRText();
		this.UsedTime = aLKTranLogSchema.getUsedTime();
		this.Bak1 = aLKTranLogSchema.getBak1();
		this.Bak2 = aLKTranLogSchema.getBak2();
		this.Bak3 = aLKTranLogSchema.getBak3();
		this.Bak4 = aLKTranLogSchema.getBak4();
		this.Bak5 = aLKTranLogSchema.getBak5();
		this.MakeDate = fDate.getDate( aLKTranLogSchema.getMakeDate());
		this.MakeTime = aLKTranLogSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLKTranLogSchema.getModifyDate());
		this.ModifyTime = aLKTranLogSchema.getModifyTime();
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
			this.LogNo = rs.getInt("LogNo");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("TranNo") == null )
				this.TranNo = null;
			else
				this.TranNo = rs.getString("TranNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.FuncFlag = rs.getInt("FuncFlag");
			this.TranDate = rs.getDate("TranDate");
			if( rs.getString("TranTime") == null )
				this.TranTime = null;
			else
				this.TranTime = rs.getString("TranTime").trim();

			if( rs.getString("ProposalPrtNo") == null )
				this.ProposalPrtNo = null;
			else
				this.ProposalPrtNo = rs.getString("ProposalPrtNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.RCode = rs.getInt("RCode");
			if( rs.getString("RText") == null )
				this.RText = null;
			else
				this.RText = rs.getString("RText").trim();

			this.UsedTime = rs.getInt("UsedTime");
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
			tError.moduleName = "LKTranLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKTranLogSchema getSchema()
	{
		LKTranLogSchema aLKTranLogSchema = new LKTranLogSchema();
		aLKTranLogSchema.setSchema(this);
		return aLKTranLogSchema;
	}

	public LKTranLogDB getDB()
	{
		LKTranLogDB aDBOper = new LKTranLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTranLog描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(LogNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FuncFlag) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( TranDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalPrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OtherNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RCode) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UsedTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTranLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TranNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FuncFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			TranDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			TranTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ProposalPrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UsedTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKTranLogSchema";
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
		if (FCode.equals("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equals("TranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranNo));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
		}
		if (FCode.equals("TranTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranTime));
		}
		if (FCode.equals("ProposalPrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalPrtNo));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equals("RText"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RText));
		}
		if (FCode.equals("UsedTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UsedTime));
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
				strFieldValue = String.valueOf(LogNo);
				break;
			case 1:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TranNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = String.valueOf(FuncFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TranTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ProposalPrtNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = String.valueOf(RCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 17:
				strFieldValue = String.valueOf(UsedTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
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

		if (FCode.equals("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LogNo = i;
			}
		}
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranCom = i;
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
		if (FCode.equals("TranNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranNo = FValue.trim();
			}
			else
				TranNo = null;
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
		if (FCode.equals("FuncFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FuncFlag = i;
			}
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
		if (FCode.equals("TranTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranTime = FValue.trim();
			}
			else
				TranTime = null;
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
		if (FCode.equals("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equals("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
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
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equals("UsedTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UsedTime = i;
			}
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
		LKTranLogSchema other = (LKTranLogSchema)otherObject;
		return
			LogNo == other.getLogNo()
			&& TranCom == other.getTranCom()
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& TranNo.equals(other.getTranNo())
			&& Operator.equals(other.getOperator())
			&& FuncFlag == other.getFuncFlag()
			&& fDate.getString(TranDate).equals(other.getTranDate())
			&& TranTime.equals(other.getTranTime())
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& ContNo.equals(other.getContNo())
			&& OtherNo.equals(other.getOtherNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& ManageCom.equals(other.getManageCom())
			&& RCode == other.getRCode()
			&& RText.equals(other.getRText())
			&& UsedTime == other.getUsedTime()
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
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
		if( strFieldName.equals("LogNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 3;
		}
		if( strFieldName.equals("TranNo") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 6;
		}
		if( strFieldName.equals("TranDate") ) {
			return 7;
		}
		if( strFieldName.equals("TranTime") ) {
			return 8;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return 9;
		}
		if( strFieldName.equals("ContNo") ) {
			return 10;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 12;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("RCode") ) {
			return 15;
		}
		if( strFieldName.equals("RText") ) {
			return 16;
		}
		if( strFieldName.equals("UsedTime") ) {
			return 17;
		}
		if( strFieldName.equals("Bak1") ) {
			return 18;
		}
		if( strFieldName.equals("Bak2") ) {
			return 19;
		}
		if( strFieldName.equals("Bak3") ) {
			return 20;
		}
		if( strFieldName.equals("Bak4") ) {
			return 21;
		}
		if( strFieldName.equals("Bak5") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
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
				strFieldName = "LogNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "NodeNo";
				break;
			case 4:
				strFieldName = "TranNo";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "FuncFlag";
				break;
			case 7:
				strFieldName = "TranDate";
				break;
			case 8:
				strFieldName = "TranTime";
				break;
			case 9:
				strFieldName = "ProposalPrtNo";
				break;
			case 10:
				strFieldName = "ContNo";
				break;
			case 11:
				strFieldName = "OtherNo";
				break;
			case 12:
				strFieldName = "AgentCom";
				break;
			case 13:
				strFieldName = "AgentCode";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "RCode";
				break;
			case 16:
				strFieldName = "RText";
				break;
			case 17:
				strFieldName = "UsedTime";
				break;
			case 18:
				strFieldName = "Bak1";
				break;
			case 19:
				strFieldName = "Bak2";
				break;
			case 20:
				strFieldName = "Bak3";
				break;
			case 21:
				strFieldName = "Bak4";
				break;
			case 22:
				strFieldName = "Bak5";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
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
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TranTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RText") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UsedTime") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
