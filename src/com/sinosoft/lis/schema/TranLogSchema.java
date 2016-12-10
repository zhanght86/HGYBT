/*
 * <p>ClassName: TranLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.TranLogDB;
/**
 * DB层 交易日志数据库对象的集合(用户) 类
 * @author yuantongxin
 */
public class TranLogSchema implements Schema
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
	private int TranDate;
	/** 交易时间 */
	private int TranTime;
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
	/** 代理机构名称 */
	private String AgentComName;
	/** 代理人名称 */
	private String AgentName;
	/** 组别 */
	private String UnitCode;
	/** 代理级别 */
	private String AgentCodeGrade;
	/** 代理机构级别 */
	private String AgentGrade;
	/** 管理机构 */
	private String ManageCom;
	/** 交易结果 */
	private int RCode;
	/** 结果描述 */
	private String RText;
	/** 服务耗时 */
	private int UsedTime;
	/** 投保人姓名 */
	private String AppntName;
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
	/** 进入报文 */
	private String InNoDoc;
	/** 入库日期 */
	private int MakeDate;
	/** 进入标准报文 */
	private String InDoc;
	/** 返回标准报文 */
	private String OutDoc;
	/** 入库时间 */
	private int MakeTime;
	/** 最后修改日期 */
	private int ModifyDate;
	/** 最后修改时间 */
	private int ModifyTime;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人姓名 */
	private String InsuredName;
	/** 被保人证件号码 */
	private String InsuredIDNo;
	/** 产品代码 */
	private String ProductId;
	/** 返回报文 */
	private String OutNoDoc;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public TranLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];//字符串数组
		pk[0] = "TranNo";//为第一个元素赋值

		PK = pk;//将元素值赋给成员变量
	}

	/**
	 * 得到主键
	 */
	// @Method
	public String[] getPK()
	{
		return PK;//返回主键字符串数组
	}

	/**getter&&setter*/
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
	public int getTranDate()
	{
		return TranDate;
	}
	/** 交易日期 */
	public void setTranDate(int aTranDate)
	{
		TranDate = aTranDate;
	}
	/** 交易日期<P>交易日期 */
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals(""))
		{
			Integer tInteger = new Integer(aTranDate);
			int i = tInteger.intValue();
			TranDate = i;
		}
	}

	/** 交易时间<P>交易时间 */
	public int getTranTime()
	{
		return TranTime;
	}
	/** 交易时间 */
	public void setTranTime(int aTranTime)
	{
		TranTime = aTranTime;
	}
	/** 交易时间<P>交易时间 */
	public void setTranTime(String aTranTime)
	{
		if (aTranTime != null && !aTranTime.equals(""))
		{
			Integer tInteger = new Integer(aTranTime);
			int i = tInteger.intValue();
			TranTime = i;
		}
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
	/** 代理机构名称<P> */
	public String getAgentComName()
	{
		if (AgentComName != null && !AgentComName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentComName = StrTool.unicodeToGBK(AgentComName);
		}
		return AgentComName;
	}
	/** 代理机构名称 */
	public void setAgentComName(String aAgentComName)
	{
		AgentComName = aAgentComName;
	}
	/** 代理人名称<P> */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** 代理人名称 */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	/** 组别<P> */
	public String getUnitCode()
	{
		if (UnitCode != null && !UnitCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UnitCode = StrTool.unicodeToGBK(UnitCode);
		}
		return UnitCode;
	}
	/** 组别 */
	public void setUnitCode(String aUnitCode)
	{
		UnitCode = aUnitCode;
	}
	/** 代理级别<P> */
	public String getAgentCodeGrade()
	{
		if (AgentCodeGrade != null && !AgentCodeGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCodeGrade = StrTool.unicodeToGBK(AgentCodeGrade);
		}
		return AgentCodeGrade;
	}
	/** 代理级别 */
	public void setAgentCodeGrade(String aAgentCodeGrade)
	{
		AgentCodeGrade = aAgentCodeGrade;
	}
	/** 代理机构级别<P> */
	public String getAgentGrade()
	{
		if (AgentGrade != null && !AgentGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentGrade = StrTool.unicodeToGBK(AgentGrade);
		}
		return AgentGrade;
	}
	/** 代理机构级别 */
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
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

	/** 投保人姓名<P> */
	public String getAppntName()
	{
		if (AppntName != null && !AppntName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntName = StrTool.unicodeToGBK(AppntName);
		}
		return AppntName;
	}
	/** 投保人姓名 */
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
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
	/** 进入报文<P> */
	public String getInNoDoc()
	{
		if (InNoDoc != null && !InNoDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			InNoDoc = StrTool.unicodeToGBK(InNoDoc);
		}
		return InNoDoc;
	}
	/** 进入报文 */
	public void setInNoDoc(String aInNoDoc)
	{
		InNoDoc = aInNoDoc;
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

	/** 进入标准报文<P> */
	public String getInDoc()
	{
		if (InDoc != null && !InDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			InDoc = StrTool.unicodeToGBK(InDoc);
		}
		return InDoc;
	}
	/** 进入标准报文 */
	public void setInDoc(String aInDoc)
	{
		InDoc = aInDoc;
	}
	/** 返回标准报文<P> */
	public String getOutDoc()
	{
		if (OutDoc != null && !OutDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutDoc = StrTool.unicodeToGBK(OutDoc);
		}
		return OutDoc;
	}
	/** 返回标准报文 */
	public void setOutDoc(String aOutDoc)
	{
		OutDoc = aOutDoc;
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

	/** 投保人证件号码<P> */
	public String getAppntIDNo()
	{
		if (AppntIDNo != null && !AppntIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntIDNo = StrTool.unicodeToGBK(AppntIDNo);
		}
		return AppntIDNo;
	}
	/** 投保人证件号码 */
	public void setAppntIDNo(String aAppntIDNo)
	{
		AppntIDNo = aAppntIDNo;
	}
	/** 被保人姓名<P> */
	public String getInsuredName()
	{
		if (InsuredName != null && !InsuredName.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredName = StrTool.unicodeToGBK(InsuredName);
		}
		return InsuredName;
	}
	/** 被保人姓名 */
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	/** 被保人证件号码<P> */
	public String getInsuredIDNo()
	{
		if (InsuredIDNo != null && !InsuredIDNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDNo = StrTool.unicodeToGBK(InsuredIDNo);
		}
		return InsuredIDNo;
	}
	/** 被保人证件号码 */
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
	}
	/** 产品代码<P> */
	public String getProductId()
	{
		if (ProductId != null && !ProductId.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProductId = StrTool.unicodeToGBK(ProductId);
		}
		return ProductId;
	}
	/** 产品代码 */
	public void setProductId(String aProductId)
	{
		ProductId = aProductId;
	}
	/** 返回报文<P> */
	public String getOutNoDoc()
	{
		if (OutNoDoc != null && !OutNoDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutNoDoc = StrTool.unicodeToGBK(OutNoDoc);
		}
		return OutNoDoc;
	}
	/** 返回报文 */
	public void setOutNoDoc(String aOutNoDoc)
	{
		OutNoDoc = aOutNoDoc;
	}

	/**
	* 使用另外一个 TranLogSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(TranLogSchema aTranLogSchema)
	{
		this.LogNo = aTranLogSchema.getLogNo();
		this.TranCom = aTranLogSchema.getTranCom();
		this.ZoneNo = aTranLogSchema.getZoneNo();
		this.NodeNo = aTranLogSchema.getNodeNo();
		this.TranNo = aTranLogSchema.getTranNo();
		this.Operator = aTranLogSchema.getOperator();
		this.FuncFlag = aTranLogSchema.getFuncFlag();
		this.TranDate = aTranLogSchema.getTranDate();
		this.TranTime = aTranLogSchema.getTranTime();
		this.ProposalPrtNo = aTranLogSchema.getProposalPrtNo();
		this.ContNo = aTranLogSchema.getContNo();
		this.OtherNo = aTranLogSchema.getOtherNo();
		this.AgentCom = aTranLogSchema.getAgentCom();
		this.AgentCode = aTranLogSchema.getAgentCode();
		this.AgentComName = aTranLogSchema.getAgentComName();
		this.AgentName = aTranLogSchema.getAgentName();
		this.UnitCode = aTranLogSchema.getUnitCode();
		this.AgentCodeGrade = aTranLogSchema.getAgentCodeGrade();
		this.AgentGrade = aTranLogSchema.getAgentGrade();
		this.ManageCom = aTranLogSchema.getManageCom();
		this.RCode = aTranLogSchema.getRCode();
		this.RText = aTranLogSchema.getRText();
		this.UsedTime = aTranLogSchema.getUsedTime();
		this.AppntName = aTranLogSchema.getAppntName();
		this.Bak1 = aTranLogSchema.getBak1();
		this.Bak2 = aTranLogSchema.getBak2();
		this.Bak3 = aTranLogSchema.getBak3();
		this.Bak4 = aTranLogSchema.getBak4();
		this.Bak5 = aTranLogSchema.getBak5();
		this.InNoDoc = aTranLogSchema.getInNoDoc();
		this.MakeDate = aTranLogSchema.getMakeDate();
		this.InDoc = aTranLogSchema.getInDoc();
		this.OutDoc = aTranLogSchema.getOutDoc();
		this.MakeTime = aTranLogSchema.getMakeTime();
		this.ModifyDate = aTranLogSchema.getModifyDate();
		this.ModifyTime = aTranLogSchema.getModifyTime();
		this.AppntIDNo = aTranLogSchema.getAppntIDNo();
		this.InsuredName = aTranLogSchema.getInsuredName();
		this.InsuredIDNo = aTranLogSchema.getInsuredIDNo();
		this.ProductId = aTranLogSchema.getProductId();
		this.OutNoDoc = aTranLogSchema.getOutNoDoc();
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
			this.TranDate = rs.getInt("TranDate");
			this.TranTime = rs.getInt("TranTime");
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

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("UnitCode") == null )
				this.UnitCode = null;
			else
				this.UnitCode = rs.getString("UnitCode").trim();

			if( rs.getString("AgentCodeGrade") == null )
				this.AgentCodeGrade = null;
			else
				this.AgentCodeGrade = rs.getString("AgentCodeGrade").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

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
			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

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

			if( rs.getString("InNoDoc") == null )
				this.InNoDoc = null;
			else
				this.InNoDoc = rs.getString("InNoDoc").trim();

			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("InDoc") == null )
				this.InDoc = null;
			else
				this.InDoc = rs.getString("InDoc").trim();

			if( rs.getString("OutDoc") == null )
				this.OutDoc = null;
			else
				this.OutDoc = rs.getString("OutDoc").trim();

			this.MakeTime = rs.getInt("MakeTime");
			this.ModifyDate = rs.getInt("ModifyDate");
			this.ModifyTime = rs.getInt("ModifyTime");
			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			if( rs.getString("ProductId") == null )
				this.ProductId = null;
			else
				this.ProductId = rs.getString("ProductId").trim();

			if( rs.getString("OutNoDoc") == null )
				this.OutNoDoc = null;
			else
				this.OutNoDoc = rs.getString("OutNoDoc").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

    /**
     * 获得数据库对象的集合(用户)
     * @return 交易日志数据库对象的集合(用户)
     */
	public TranLogSchema getSchema()
	{
		TranLogSchema aTranLogSchema = new TranLogSchema();
		aTranLogSchema.setSchema(this);
		return aTranLogSchema;
	}

	public TranLogDB getDB()
	{
		TranLogDB aDBOper = new TranLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTranLog描述/A>表字段
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
					+  ChgData.chgData(TranDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalPrtNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OtherNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UnitCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCodeGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(RCode) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UsedTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InNoDoc) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InDoc) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutDoc) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProductId) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutNoDoc) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTranLog>历史记账凭证主表信息</A>表字段
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
			TranDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			TranTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			ProposalPrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			UnitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCodeGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UsedTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InNoDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			InDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			OutDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ProductId = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			OutNoDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogSchema";
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
		if (FCode.equals("LogNo"))//传入字段值是日志号
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));//字符串处理工具类将字符串转换为Unicode字符串
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
			System.out.println(String.valueOf(TranNo));
			System.out.println(StrTool.GBKToUnicode(String.valueOf(TranNo)));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
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
		if (FCode.equals("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equals("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equals("UnitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitCode));
		}
		if (FCode.equals("AgentCodeGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeGrade));
		}
		if (FCode.equals("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
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
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
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
		if (FCode.equals("InNoDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InNoDoc));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("InDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InDoc));
		}
		if (FCode.equals("OutDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDoc));
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
		if (FCode.equals("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
		}
		if (FCode.equals("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equals("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equals("ProductId"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProductId));
		}
		if (FCode.equals("OutNoDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutNoDoc));
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
				strFieldValue = String.valueOf(TranDate);
				break;
			case 8:
				strFieldValue = String.valueOf(TranTime);
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
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(UnitCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeGrade);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = String.valueOf(RCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 22:
				strFieldValue = String.valueOf(UsedTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InNoDoc);
				break;
			case 30:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(InDoc);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(OutDoc);
				break;
			case 33:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 34:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 35:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ProductId);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(OutNoDoc);
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
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranDate = i;
			}
		}
		if (FCode.equals("TranTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranTime = i;
			}
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
		if (FCode.equals("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
		}
		if (FCode.equals("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
		}
		if (FCode.equals("UnitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitCode = FValue.trim();
			}
			else
				UnitCode = null;
		}
		if (FCode.equals("AgentCodeGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeGrade = FValue.trim();
			}
			else
				AgentCodeGrade = null;
		}
		if (FCode.equals("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
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
		if (FCode.equals("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
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
		if (FCode.equals("InNoDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InNoDoc = FValue.trim();
			}
			else
				InNoDoc = null;
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
		if (FCode.equals("InDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InDoc = FValue.trim();
			}
			else
				InDoc = null;
		}
		if (FCode.equals("OutDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutDoc = FValue.trim();
			}
			else
				OutDoc = null;
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
		if (FCode.equals("AppntIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDNo = FValue.trim();
			}
			else
				AppntIDNo = null;
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
		if (FCode.equals("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
		}
		if (FCode.equals("ProductId"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProductId = FValue.trim();
			}
			else
				ProductId = null;
		}
		if (FCode.equals("OutNoDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutNoDoc = FValue.trim();
			}
			else
				OutNoDoc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		TranLogSchema other = (TranLogSchema)otherObject;
		return
			LogNo == other.getLogNo()
			&& TranCom == other.getTranCom()
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& TranNo.equals(other.getTranNo())
			&& Operator.equals(other.getOperator())
			&& FuncFlag == other.getFuncFlag()
			&& TranDate == other.getTranDate()
			&& TranTime == other.getTranTime()
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& ContNo.equals(other.getContNo())
			&& OtherNo.equals(other.getOtherNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentComName.equals(other.getAgentComName())
			&& AgentName.equals(other.getAgentName())
			&& UnitCode.equals(other.getUnitCode())
			&& AgentCodeGrade.equals(other.getAgentCodeGrade())
			&& AgentGrade.equals(other.getAgentGrade())
			&& ManageCom.equals(other.getManageCom())
			&& RCode == other.getRCode()
			&& RText.equals(other.getRText())
			&& UsedTime == other.getUsedTime()
			&& AppntName.equals(other.getAppntName())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& InNoDoc.equals(other.getInNoDoc())
			&& MakeDate == other.getMakeDate()
			&& InDoc.equals(other.getInDoc())
			&& OutDoc.equals(other.getOutDoc())
			&& MakeTime == other.getMakeTime()
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime == other.getModifyTime()
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& ProductId.equals(other.getProductId())
			&& OutNoDoc.equals(other.getOutNoDoc());
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
		if( strFieldName.equals("AgentComName") ) {
			return 14;
		}
		if( strFieldName.equals("AgentName") ) {
			return 15;
		}
		if( strFieldName.equals("UnitCode") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCodeGrade") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("RCode") ) {
			return 20;
		}
		if( strFieldName.equals("RText") ) {
			return 21;
		}
		if( strFieldName.equals("UsedTime") ) {
			return 22;
		}
		if( strFieldName.equals("AppntName") ) {
			return 23;
		}
		if( strFieldName.equals("Bak1") ) {
			return 24;
		}
		if( strFieldName.equals("Bak2") ) {
			return 25;
		}
		if( strFieldName.equals("Bak3") ) {
			return 26;
		}
		if( strFieldName.equals("Bak4") ) {
			return 27;
		}
		if( strFieldName.equals("Bak5") ) {
			return 28;
		}
		if( strFieldName.equals("InNoDoc") ) {
			return 29;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 30;
		}
		if( strFieldName.equals("InDoc") ) {
			return 31;
		}
		if( strFieldName.equals("OutDoc") ) {
			return 32;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 34;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 35;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 36;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 37;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 38;
		}
		if( strFieldName.equals("ProductId") ) {
			return 39;
		}
		if( strFieldName.equals("OutNoDoc") ) {
			return 40;
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
				strFieldName = "AgentComName";
				break;
			case 15:
				strFieldName = "AgentName";
				break;
			case 16:
				strFieldName = "UnitCode";
				break;
			case 17:
				strFieldName = "AgentCodeGrade";
				break;
			case 18:
				strFieldName = "AgentGrade";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "RCode";
				break;
			case 21:
				strFieldName = "RText";
				break;
			case 22:
				strFieldName = "UsedTime";
				break;
			case 23:
				strFieldName = "AppntName";
				break;
			case 24:
				strFieldName = "Bak1";
				break;
			case 25:
				strFieldName = "Bak2";
				break;
			case 26:
				strFieldName = "Bak3";
				break;
			case 27:
				strFieldName = "Bak4";
				break;
			case 28:
				strFieldName = "Bak5";
				break;
			case 29:
				strFieldName = "InNoDoc";
				break;
			case 30:
				strFieldName = "MakeDate";
				break;
			case 31:
				strFieldName = "InDoc";
				break;
			case 32:
				strFieldName = "OutDoc";
				break;
			case 33:
				strFieldName = "MakeTime";
				break;
			case 34:
				strFieldName = "ModifyDate";
				break;
			case 35:
				strFieldName = "ModifyTime";
				break;
			case 36:
				strFieldName = "AppntIDNo";
				break;
			case 37:
				strFieldName = "InsuredName";
				break;
			case 38:
				strFieldName = "InsuredIDNo";
				break;
			case 39:
				strFieldName = "ProductId";
				break;
			case 40:
				strFieldName = "OutNoDoc";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	 * 获得字段类型(数值)
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND(数据库对象的集合类型未找到)
	**/
	public int getFieldType(String strFieldName)//字符串的字段
	{
		if( strFieldName.equals("LogNo") ) {//字符串字段是日志号
			return Schema.TYPE_INT;//返回int类型(3)
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranTime") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
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
		if( strFieldName.equals("AppntName") ) {
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
		if( strFieldName.equals("InNoDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDoc") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("AppntIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProductId") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutNoDoc") ) {
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
		//默认:类型不存在(-1)
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 34:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 35:
				//整数类型(3)
				nFieldType = Schema.TYPE_INT;
				break;
			case 36:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
			//字符串类型(0)
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
	public static void main(String[] args) {
//		System.out.println(new TranLogSchema().getV("LogNo"));
		String[] pk=new TranLogSchema().getPK();
		for (int i = 0; i < pk.length; i++) {
			System.out.println(pk[i]);
		}
	}
}
