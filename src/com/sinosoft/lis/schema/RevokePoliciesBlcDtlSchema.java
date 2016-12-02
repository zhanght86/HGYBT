/*
 * <p>ClassName: RevokePoliciesBlcDtlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-02
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RevokePoliciesBlcDtlDB;

public class RevokePoliciesBlcDtlSchema implements Schema
{
	// @Field
	/** 对账批次号 */
	private int BlcTranNo;
	/** Type */
	private int Type;
	/** 保单号 */
	private String ContNo;
	/** 投保单(印刷)号 */
	private String ProposalPrtNo;
	/** 交易日期 */
	private Date TranDate;
	/** 交易单位 */
	private int TranCom;
	/** 交易流水 */
	private String TranNo;
	/** 地区代码 */
	private String ZoneNo;
	/** 交易网点 */
	private String NodeNo;
	/** 管理机构 */
	private String ManageCom;
	/** 保单状态 */
	private String ContState;
	/** 变更状态生效日 */
	private Date StateChangeDate;
	/** 维护日期 */
	private Date RepairDate;
	/** 对账结果 */
	private int RCode;
	/** 结果描述 */
	private String RText;
	/** 处理状态 */
	private String State;
	/** 处理结果 */
	private String Result;
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
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RevokePoliciesBlcDtlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BlcTranNo";
		pk[1] = "Type";
		pk[2] = "ContNo";

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

	/** Type<P> */
	public int getType()
	{
		return Type;
	}
	/** Type */
	public void setType(int aType)
	{
		Type = aType;
	}
	/** Type<P> */
	public void setType(String aType)
	{
		if (aType != null && !aType.equals(""))
		{
			Integer tInteger = new Integer(aType);
			int i = tInteger.intValue();
			Type = i;
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

	/** 交易单位<P> */
	public int getTranCom()
	{
		return TranCom;
	}
	/** 交易单位 */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易单位<P> */
	public void setTranCom(String aTranCom)
	{
		if (aTranCom != null && !aTranCom.equals(""))
		{
			Integer tInteger = new Integer(aTranCom);
			int i = tInteger.intValue();
			TranCom = i;
		}
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
	/** 管理机构<P> */
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
	/** 保单状态<P> */
	public String getContState()
	{
		if (ContState != null && !ContState.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContState = StrTool.unicodeToGBK(ContState);
		}
		return ContState;
	}
	/** 保单状态 */
	public void setContState(String aContState)
	{
		ContState = aContState;
	}
	/** 变更状态生效日<P> */
	public String getStateChangeDate()
	{
		if( StateChangeDate != null )
			return fDate.getString(StateChangeDate);
		else
			return null;
	}
	/** 变更状态生效日 */
	public void setStateChangeDate(Date aStateChangeDate)
	{
		StateChangeDate = aStateChangeDate;
	}
	/** 变更状态生效日<P> */
	public void setStateChangeDate(String aStateChangeDate)
	{
		if (aStateChangeDate != null && !aStateChangeDate.equals("") )
		{
			StateChangeDate = fDate.getDate( aStateChangeDate );
		}
		else
			StateChangeDate = null;
	}

	/** 维护日期<P> */
	public String getRepairDate()
	{
		if( RepairDate != null )
			return fDate.getString(RepairDate);
		else
			return null;
	}
	/** 维护日期 */
	public void setRepairDate(Date aRepairDate)
	{
		RepairDate = aRepairDate;
	}
	/** 维护日期<P> */
	public void setRepairDate(String aRepairDate)
	{
		if (aRepairDate != null && !aRepairDate.equals("") )
		{
			RepairDate = fDate.getDate( aRepairDate );
		}
		else
			RepairDate = null;
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
	/** 处理状态<P> */
	public String getState()
	{
		if (State != null && !State.equals("") && SysConst.CHANGECHARSET == true)
		{
			State = StrTool.unicodeToGBK(State);
		}
		return State;
	}
	/** 处理状态 */
	public void setState(String aState)
	{
		State = aState;
	}
	/** 处理结果<P> */
	public String getResult()
	{
		if (Result != null && !Result.equals("") && SysConst.CHANGECHARSET == true)
		{
			Result = StrTool.unicodeToGBK(Result);
		}
		return Result;
	}
	/** 处理结果 */
	public void setResult(String aResult)
	{
		Result = aResult;
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
	* 使用另外一个 RevokePoliciesBlcDtlSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(RevokePoliciesBlcDtlSchema aRevokePoliciesBlcDtlSchema)
	{
		this.BlcTranNo = aRevokePoliciesBlcDtlSchema.getBlcTranNo();
		this.Type = aRevokePoliciesBlcDtlSchema.getType();
		this.ContNo = aRevokePoliciesBlcDtlSchema.getContNo();
		this.ProposalPrtNo = aRevokePoliciesBlcDtlSchema.getProposalPrtNo();
		this.TranDate = fDate.getDate( aRevokePoliciesBlcDtlSchema.getTranDate());
		this.TranCom = aRevokePoliciesBlcDtlSchema.getTranCom();
		this.TranNo = aRevokePoliciesBlcDtlSchema.getTranNo();
		this.ZoneNo = aRevokePoliciesBlcDtlSchema.getZoneNo();
		this.NodeNo = aRevokePoliciesBlcDtlSchema.getNodeNo();
		this.ManageCom = aRevokePoliciesBlcDtlSchema.getManageCom();
		this.ContState = aRevokePoliciesBlcDtlSchema.getContState();
		this.StateChangeDate = fDate.getDate( aRevokePoliciesBlcDtlSchema.getStateChangeDate());
		this.RepairDate = fDate.getDate( aRevokePoliciesBlcDtlSchema.getRepairDate());
		this.RCode = aRevokePoliciesBlcDtlSchema.getRCode();
		this.RText = aRevokePoliciesBlcDtlSchema.getRText();
		this.State = aRevokePoliciesBlcDtlSchema.getState();
		this.Result = aRevokePoliciesBlcDtlSchema.getResult();
		this.Bak1 = aRevokePoliciesBlcDtlSchema.getBak1();
		this.Bak2 = aRevokePoliciesBlcDtlSchema.getBak2();
		this.Bak3 = aRevokePoliciesBlcDtlSchema.getBak3();
		this.Bak4 = aRevokePoliciesBlcDtlSchema.getBak4();
		this.Bak5 = aRevokePoliciesBlcDtlSchema.getBak5();
		this.MakeDate = fDate.getDate( aRevokePoliciesBlcDtlSchema.getMakeDate());
		this.MakeTime = aRevokePoliciesBlcDtlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aRevokePoliciesBlcDtlSchema.getModifyDate());
		this.ModifyTime = aRevokePoliciesBlcDtlSchema.getModifyTime();
		this.Operator = aRevokePoliciesBlcDtlSchema.getOperator();
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
			this.Type = rs.getInt("Type");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalPrtNo") == null )
				this.ProposalPrtNo = null;
			else
				this.ProposalPrtNo = rs.getString("ProposalPrtNo").trim();

			this.TranDate = rs.getDate("TranDate");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("TranNo") == null )
				this.TranNo = null;
			else
				this.TranNo = rs.getString("TranNo").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ContState") == null )
				this.ContState = null;
			else
				this.ContState = rs.getString("ContState").trim();

			this.StateChangeDate = rs.getDate("StateChangeDate");
			this.RepairDate = rs.getDate("RepairDate");
			this.RCode = rs.getInt("RCode");
			if( rs.getString("RText") == null )
				this.RText = null;
			else
				this.RText = rs.getString("RText").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Result") == null )
				this.Result = null;
			else
				this.Result = rs.getString("Result").trim();

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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RevokePoliciesBlcDtlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public RevokePoliciesBlcDtlSchema getSchema()
	{
		RevokePoliciesBlcDtlSchema aRevokePoliciesBlcDtlSchema = new RevokePoliciesBlcDtlSchema();
		aRevokePoliciesBlcDtlSchema.setSchema(this);
		return aRevokePoliciesBlcDtlSchema;
	}

	public RevokePoliciesBlcDtlDB getDB()
	{
		RevokePoliciesBlcDtlDB aDBOper = new RevokePoliciesBlcDtlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRevokePoliciesBlcDtl描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(BlcTranNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Type) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalPrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( TranDate )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContState) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StateChangeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( RepairDate )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RCode) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(State) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Result) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRevokePoliciesBlcDtl>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BlcTranNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			Type= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalPrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TranDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			TranNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ContState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StateChangeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			RepairDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			RCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Result = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RevokePoliciesBlcDtlSchema";
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
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("ProposalPrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalPrtNo));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("TranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranNo));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("ContState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContState));
		}
		if (FCode.equals("StateChangeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStateChangeDate()));
		}
		if (FCode.equals("RepairDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRepairDate()));
		}
		if (FCode.equals("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equals("RText"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RText));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("Result"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Result));
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
				strFieldValue = String.valueOf(Type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalPrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
				break;
			case 5:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TranNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ContState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStateChangeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRepairDate()));
				break;
			case 13:
				strFieldValue = String.valueOf(RCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Result);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 26:
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
		if (FCode.equals("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Type = i;
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
		if (FCode.equals("ProposalPrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalPrtNo = FValue.trim();
			}
			else
				ProposalPrtNo = null;
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
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranCom = i;
			}
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
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equals("ContState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContState = FValue.trim();
			}
			else
				ContState = null;
		}
		if (FCode.equals("StateChangeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StateChangeDate = fDate.getDate( FValue );
			}
			else
				StateChangeDate = null;
		}
		if (FCode.equals("RepairDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RepairDate = fDate.getDate( FValue );
			}
			else
				RepairDate = null;
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
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equals("Result"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Result = FValue.trim();
			}
			else
				Result = null;
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
		RevokePoliciesBlcDtlSchema other = (RevokePoliciesBlcDtlSchema)otherObject;
		return
			BlcTranNo == other.getBlcTranNo()
			&& Type == other.getType()
			&& ContNo.equals(other.getContNo())
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& fDate.getString(TranDate).equals(other.getTranDate())
			&& TranCom == other.getTranCom()
			&& TranNo.equals(other.getTranNo())
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& ManageCom.equals(other.getManageCom())
			&& ContState.equals(other.getContState())
			&& fDate.getString(StateChangeDate).equals(other.getStateChangeDate())
			&& fDate.getString(RepairDate).equals(other.getRepairDate())
			&& RCode == other.getRCode()
			&& RText.equals(other.getRText())
			&& State.equals(other.getState())
			&& Result.equals(other.getResult())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
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
		if( strFieldName.equals("Type") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("TranDate") ) {
			return 4;
		}
		if( strFieldName.equals("TranCom") ) {
			return 5;
		}
		if( strFieldName.equals("TranNo") ) {
			return 6;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 7;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
		}
		if( strFieldName.equals("ContState") ) {
			return 10;
		}
		if( strFieldName.equals("StateChangeDate") ) {
			return 11;
		}
		if( strFieldName.equals("RepairDate") ) {
			return 12;
		}
		if( strFieldName.equals("RCode") ) {
			return 13;
		}
		if( strFieldName.equals("RText") ) {
			return 14;
		}
		if( strFieldName.equals("State") ) {
			return 15;
		}
		if( strFieldName.equals("Result") ) {
			return 16;
		}
		if( strFieldName.equals("Bak1") ) {
			return 17;
		}
		if( strFieldName.equals("Bak2") ) {
			return 18;
		}
		if( strFieldName.equals("Bak3") ) {
			return 19;
		}
		if( strFieldName.equals("Bak4") ) {
			return 20;
		}
		if( strFieldName.equals("Bak5") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 25;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "BlcTranNo";
				break;
			case 1:
				strFieldName = "Type";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "ProposalPrtNo";
				break;
			case 4:
				strFieldName = "TranDate";
				break;
			case 5:
				strFieldName = "TranCom";
				break;
			case 6:
				strFieldName = "TranNo";
				break;
			case 7:
				strFieldName = "ZoneNo";
				break;
			case 8:
				strFieldName = "NodeNo";
				break;
			case 9:
				strFieldName = "ManageCom";
				break;
			case 10:
				strFieldName = "ContState";
				break;
			case 11:
				strFieldName = "StateChangeDate";
				break;
			case 12:
				strFieldName = "RepairDate";
				break;
			case 13:
				strFieldName = "RCode";
				break;
			case 14:
				strFieldName = "RText";
				break;
			case 15:
				strFieldName = "State";
				break;
			case 16:
				strFieldName = "Result";
				break;
			case 17:
				strFieldName = "Bak1";
				break;
			case 18:
				strFieldName = "Bak2";
				break;
			case 19:
				strFieldName = "Bak3";
				break;
			case 20:
				strFieldName = "Bak4";
				break;
			case 21:
				strFieldName = "Bak5";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
				strFieldName = "ModifyTime";
				break;
			case 26:
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
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalPrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateChangeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RepairDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RText") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Result") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
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
