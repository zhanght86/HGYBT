/*
 * <p>ClassName: ContBlcDtlSchema </p>
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
import com.sinosoft.lis.db.ContBlcDtlDB;

public class ContBlcDtlSchema implements Schema
{
	// @Field
	/** 对账批次号 */
	private int BlcTranNo;
	/** 保单类型 */
	private int Type;
	/** 保单号 */
	private String ContNo;
	/** 投保单(印刷)号 */
	private String ProposalPrtNo;
	/** 交易日期 */
	private int TranDate;
	/** 交易金额 */
	private int Prem;
	/** 交易单位 */
	private int TranCom;
	/** 交易网点 */
	private String NodeNo;
	/** 代理机构 */
	private String AgentCom;
	/** 代理人 */
	private String AgentCode;
	/** 管理机构 */
	private String ManageCom;
	/** 投保人 */
	private String AppntName;
	/** 被保人 */
	private String InsuredName;
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
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ContBlcDtlSchema()
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

	/** 保单类型<P> */
	public int getType()
	{
		return Type;
	}
	/** 保单类型 */
	public void setType(int aType)
	{
		Type = aType;
	}
	/** 保单类型<P> */
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

	/** 交易金额<P>单位“分”，整数 */
	public int getPrem()
	{
		return Prem;
	}
	/** 交易金额 */
	public void setPrem(int aPrem)
	{
		Prem = aPrem;
	}
	/** 交易金额<P>单位“分”，整数 */
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Integer tInteger = new Integer(aPrem);
			int i = tInteger.intValue();
			Prem = i;
		}
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
	/** 代理人<P> */
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
	/** 投保人<P> */
	public String getAppntName()
	{
		if (AppntName != null && !AppntName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntName = StrTool.unicodeToGBK(AppntName);
		}
		return AppntName;
	}
	/** 投保人 */
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	/** 被保人<P> */
	public String getInsuredName()
	{
		if (InsuredName != null && !InsuredName.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredName = StrTool.unicodeToGBK(InsuredName);
		}
		return InsuredName;
	}
	/** 被保人 */
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
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
	* 使用另外一个 ContBlcDtlSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(ContBlcDtlSchema aContBlcDtlSchema)
	{
		this.BlcTranNo = aContBlcDtlSchema.getBlcTranNo();
		this.Type = aContBlcDtlSchema.getType();
		this.ContNo = aContBlcDtlSchema.getContNo();
		this.ProposalPrtNo = aContBlcDtlSchema.getProposalPrtNo();
		this.TranDate = aContBlcDtlSchema.getTranDate();
		this.Prem = aContBlcDtlSchema.getPrem();
		this.TranCom = aContBlcDtlSchema.getTranCom();
		this.NodeNo = aContBlcDtlSchema.getNodeNo();
		this.AgentCom = aContBlcDtlSchema.getAgentCom();
		this.AgentCode = aContBlcDtlSchema.getAgentCode();
		this.ManageCom = aContBlcDtlSchema.getManageCom();
		this.AppntName = aContBlcDtlSchema.getAppntName();
		this.InsuredName = aContBlcDtlSchema.getInsuredName();
		this.RCode = aContBlcDtlSchema.getRCode();
		this.RText = aContBlcDtlSchema.getRText();
		this.Bak1 = aContBlcDtlSchema.getBak1();
		this.Bak2 = aContBlcDtlSchema.getBak2();
		this.Bak3 = aContBlcDtlSchema.getBak3();
		this.Bak4 = aContBlcDtlSchema.getBak4();
		this.Bak5 = aContBlcDtlSchema.getBak5();
		this.MakeDate = aContBlcDtlSchema.getMakeDate();
		this.MakeTime = aContBlcDtlSchema.getMakeTime();
		this.ModifyDate = aContBlcDtlSchema.getModifyDate();
		this.ModifyTime = aContBlcDtlSchema.getModifyTime();
		this.Operator = aContBlcDtlSchema.getOperator();
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

			this.TranDate = rs.getInt("TranDate");
			this.Prem = rs.getInt("Prem");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

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

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

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
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContBlcDtlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public ContBlcDtlSchema getSchema()
	{
		ContBlcDtlSchema aContBlcDtlSchema = new ContBlcDtlSchema();
		aContBlcDtlSchema.setSchema(this);
		return aContBlcDtlSchema;
	}

	public ContBlcDtlDB getDB()
	{
		ContBlcDtlDB aDBOper = new ContBlcDtlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpContBlcDtl描述/A>表字段
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
					+  ChgData.chgData(TranDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Prem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RCode) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpContBlcDtl>历史记账凭证主表信息</A>表字段
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
			TranDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			Prem= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContBlcDtlSchema";
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
		}
		if (FCode.equals("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
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
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equals("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
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
				strFieldValue = String.valueOf(TranDate);
				break;
			case 5:
				strFieldValue = String.valueOf(Prem);
				break;
			case 6:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 13:
				strFieldValue = String.valueOf(RCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 20:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 21:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 22:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 23:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 24:
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
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranDate = i;
			}
		}
		if (FCode.equals("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Prem = i;
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
		if (FCode.equals("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
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
		if (FCode.equals("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equals("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
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
		ContBlcDtlSchema other = (ContBlcDtlSchema)otherObject;
		return
			BlcTranNo == other.getBlcTranNo()
			&& Type == other.getType()
			&& ContNo.equals(other.getContNo())
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& TranDate == other.getTranDate()
			&& Prem == other.getPrem()
			&& TranCom == other.getTranCom()
			&& NodeNo.equals(other.getNodeNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& ManageCom.equals(other.getManageCom())
			&& AppntName.equals(other.getAppntName())
			&& InsuredName.equals(other.getInsuredName())
			&& RCode == other.getRCode()
			&& RText.equals(other.getRText())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& MakeDate == other.getMakeDate()
			&& MakeTime == other.getMakeTime()
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime == other.getModifyTime()
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
		if( strFieldName.equals("Prem") ) {
			return 5;
		}
		if( strFieldName.equals("TranCom") ) {
			return 6;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("AppntName") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 12;
		}
		if( strFieldName.equals("RCode") ) {
			return 13;
		}
		if( strFieldName.equals("RText") ) {
			return 14;
		}
		if( strFieldName.equals("Bak1") ) {
			return 15;
		}
		if( strFieldName.equals("Bak2") ) {
			return 16;
		}
		if( strFieldName.equals("Bak3") ) {
			return 17;
		}
		if( strFieldName.equals("Bak4") ) {
			return 18;
		}
		if( strFieldName.equals("Bak5") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "Prem";
				break;
			case 6:
				strFieldName = "TranCom";
				break;
			case 7:
				strFieldName = "NodeNo";
				break;
			case 8:
				strFieldName = "AgentCom";
				break;
			case 9:
				strFieldName = "AgentCode";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "AppntName";
				break;
			case 12:
				strFieldName = "InsuredName";
				break;
			case 13:
				strFieldName = "RCode";
				break;
			case 14:
				strFieldName = "RText";
				break;
			case 15:
				strFieldName = "Bak1";
				break;
			case 16:
				strFieldName = "Bak2";
				break;
			case 17:
				strFieldName = "Bak3";
				break;
			case 18:
				strFieldName = "Bak4";
				break;
			case 19:
				strFieldName = "Bak5";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NodeNo") ) {
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
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
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
