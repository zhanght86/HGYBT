/*
 * <p>ClassName: ContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: midplat
 * @CreateDate：2010-11-25
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.ContDB;

public class ContSchema implements Schema
{
	// @Field
	/** 记录号 */
	private int RecordNo;
	/** 保单类型 */
	private int Type;
	/** 保单号 */
	private String ContNo;
	/** 投保单(印刷)号 */
	private String ProposalPrtNo;
	/** 产品号 */
	private int ProductId;
	/** 交易机构 */
	private int TranCom;
	/** 交易网点 */
	private String NodeNo;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构名称 */
	private String AgentComName;
	/** 代理人 */
	private String AgentCode;
	/** 代理人姓名 */
	private String AgentName;
	/** 管理机构 */
	private String ManageCom;
	/** 投保人客户号 */
	private String AppntNo;
	/** 投保人姓名 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private int AppntBirthday;
	/** 投保人证件类型 */
	private String AppntIDType;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人姓名 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出生日期 */
	private int InsuredBirthday;
	/** 被保人证件类型 */
	private String InsuredIDType;
	/** 被保人证件号码 */
	private String InsuredIDNo;
	/** 交易日期 */
	private int TranDate;
	/** 投保日期 */
	private int PolApplyDate;
	/** 签单日期 */
	private int SignDate;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 保单状态 */
	private int State;
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
	/** 备用6 */
	private String Bak6;
	/** 备用7 */
	private String Bak7;
	/** 备用8 */
	private String Bak8;
	/** 备用9 */
	private String Bak9;
	/** 备用10 */
	private String Bak10;
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

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RecordNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 记录号<P> */
	public int getRecordNo()
	{
		return RecordNo;
	}
	/** 记录号 */
	public void setRecordNo(int aRecordNo)
	{
		RecordNo = aRecordNo;
	}
	/** 记录号<P> */
	public void setRecordNo(String aRecordNo)
	{
		if (aRecordNo != null && !aRecordNo.equals(""))
		{
			Integer tInteger = new Integer(aRecordNo);
			int i = tInteger.intValue();
			RecordNo = i;
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

	/** 保单号<P>保单合同号 */
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
	/** 产品号<P> */
	public int getProductId()
	{
		return ProductId;
	}
	/** 产品号 */
	public void setProductId(int aProductId)
	{
		ProductId = aProductId;
	}
	/** 产品号<P> */
	public void setProductId(String aProductId)
	{
		if (aProductId != null && !aProductId.equals(""))
		{
			Integer tInteger = new Integer(aProductId);
			int i = tInteger.intValue();
			ProductId = i;
		}
	}

	/** 交易机构<P> */
	public int getTranCom()
	{
		return TranCom;
	}
	/** 交易机构 */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易机构<P> */
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
	/** 代理人姓名<P> */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** 代理人姓名 */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
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
	/** 投保人客户号<P> */
	public String getAppntNo()
	{
		if (AppntNo != null && !AppntNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntNo = StrTool.unicodeToGBK(AppntNo);
		}
		return AppntNo;
	}
	/** 投保人客户号 */
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
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
	/** 投保人性别<P> */
	public String getAppntSex()
	{
		if (AppntSex != null && !AppntSex.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntSex = StrTool.unicodeToGBK(AppntSex);
		}
		return AppntSex;
	}
	/** 投保人性别 */
	public void setAppntSex(String aAppntSex)
	{
		AppntSex = aAppntSex;
	}
	/** 投保人出生日期<P> */
	public int getAppntBirthday()
	{
		return AppntBirthday;
	}
	/** 投保人出生日期 */
	public void setAppntBirthday(int aAppntBirthday)
	{
		AppntBirthday = aAppntBirthday;
	}
	/** 投保人出生日期<P> */
	public void setAppntBirthday(String aAppntBirthday)
	{
		if (aAppntBirthday != null && !aAppntBirthday.equals(""))
		{
			Integer tInteger = new Integer(aAppntBirthday);
			int i = tInteger.intValue();
			AppntBirthday = i;
		}
	}

	/** 投保人证件类型<P> */
	public String getAppntIDType()
	{
		if (AppntIDType != null && !AppntIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			AppntIDType = StrTool.unicodeToGBK(AppntIDType);
		}
		return AppntIDType;
	}
	/** 投保人证件类型 */
	public void setAppntIDType(String aAppntIDType)
	{
		AppntIDType = aAppntIDType;
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
	/** 被保人客户号<P> */
	public String getInsuredNo()
	{
		if (InsuredNo != null && !InsuredNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredNo = StrTool.unicodeToGBK(InsuredNo);
		}
		return InsuredNo;
	}
	/** 被保人客户号 */
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
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
	/** 被保人性别<P> */
	public String getInsuredSex()
	{
		if (InsuredSex != null && !InsuredSex.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredSex = StrTool.unicodeToGBK(InsuredSex);
		}
		return InsuredSex;
	}
	/** 被保人性别 */
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
	/** 被保人出生日期<P> */
	public int getInsuredBirthday()
	{
		return InsuredBirthday;
	}
	/** 被保人出生日期 */
	public void setInsuredBirthday(int aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	/** 被保人出生日期<P> */
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals(""))
		{
			Integer tInteger = new Integer(aInsuredBirthday);
			int i = tInteger.intValue();
			InsuredBirthday = i;
		}
	}

	/** 被保人证件类型<P> */
	public String getInsuredIDType()
	{
		if (InsuredIDType != null && !InsuredIDType.equals("") && SysConst.CHANGECHARSET == true)
		{
			InsuredIDType = StrTool.unicodeToGBK(InsuredIDType);
		}
		return InsuredIDType;
	}
	/** 被保人证件类型 */
	public void setInsuredIDType(String aInsuredIDType)
	{
		InsuredIDType = aInsuredIDType;
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

	/** 投保日期<P> */
	public int getPolApplyDate()
	{
		return PolApplyDate;
	}
	/** 投保日期 */
	public void setPolApplyDate(int aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
	}
	/** 投保日期<P> */
	public void setPolApplyDate(String aPolApplyDate)
	{
		if (aPolApplyDate != null && !aPolApplyDate.equals(""))
		{
			Integer tInteger = new Integer(aPolApplyDate);
			int i = tInteger.intValue();
			PolApplyDate = i;
		}
	}

	/** 签单日期<P> */
	public int getSignDate()
	{
		return SignDate;
	}
	/** 签单日期 */
	public void setSignDate(int aSignDate)
	{
		SignDate = aSignDate;
	}
	/** 签单日期<P> */
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals(""))
		{
			Integer tInteger = new Integer(aSignDate);
			int i = tInteger.intValue();
			SignDate = i;
		}
	}

	/** 保费<P>单位“分”，整数 */
	public double getPrem()
	{
		return Prem;
	}
	/** 保费 */
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	/** 保费<P>单位“分”，整数 */
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	/** 保额<P> */
	public double getAmnt()
	{
		return Amnt;
	}
	/** 保额 */
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	/** 保额<P> */
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	/** 保单状态<P>0-录单；1-签单；2-撤单 */
	public int getState()
	{
		return State;
	}
	/** 保单状态 */
	public void setState(int aState)
	{
		State = aState;
	}
	/** 保单状态<P>0-录单；1-签单；2-撤单 */
	public void setState(String aState)
	{
		if (aState != null && !aState.equals(""))
		{
			Integer tInteger = new Integer(aState);
			int i = tInteger.intValue();
			State = i;
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
	/** 备用6<P> */
	public String getBak6()
	{
		if (Bak6 != null && !Bak6.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak6 = StrTool.unicodeToGBK(Bak6);
		}
		return Bak6;
	}
	/** 备用6 */
	public void setBak6(String aBak6)
	{
		Bak6 = aBak6;
	}
	/** 备用7<P> */
	public String getBak7()
	{
		if (Bak7 != null && !Bak7.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak7 = StrTool.unicodeToGBK(Bak7);
		}
		return Bak7;
	}
	/** 备用7 */
	public void setBak7(String aBak7)
	{
		Bak7 = aBak7;
	}
	/** 备用8<P> */
	public String getBak8()
	{
		if (Bak8 != null && !Bak8.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak8 = StrTool.unicodeToGBK(Bak8);
		}
		return Bak8;
	}
	/** 备用8 */
	public void setBak8(String aBak8)
	{
		Bak8 = aBak8;
	}
	/** 备用9<P> */
	public String getBak9()
	{
		if (Bak9 != null && !Bak9.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak9 = StrTool.unicodeToGBK(Bak9);
		}
		return Bak9;
	}
	/** 备用9 */
	public void setBak9(String aBak9)
	{
		Bak9 = aBak9;
	}
	/** 备用10<P> */
	public String getBak10()
	{
		if (Bak10 != null && !Bak10.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak10 = StrTool.unicodeToGBK(Bak10);
		}
		return Bak10;
	}
	/** 备用10 */
	public void setBak10(String aBak10)
	{
		Bak10 = aBak10;
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
	* 使用另外一个 ContSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(ContSchema aContSchema)
	{
		this.RecordNo = aContSchema.getRecordNo();
		this.Type = aContSchema.getType();
		this.ContNo = aContSchema.getContNo();
		this.ProposalPrtNo = aContSchema.getProposalPrtNo();
		this.ProductId = aContSchema.getProductId();
		this.TranCom = aContSchema.getTranCom();
		this.NodeNo = aContSchema.getNodeNo();
		this.AgentCom = aContSchema.getAgentCom();
		this.AgentComName = aContSchema.getAgentComName();
		this.AgentCode = aContSchema.getAgentCode();
		this.AgentName = aContSchema.getAgentName();
		this.ManageCom = aContSchema.getManageCom();
		this.AppntNo = aContSchema.getAppntNo();
		this.AppntName = aContSchema.getAppntName();
		this.AppntSex = aContSchema.getAppntSex();
		this.AppntBirthday = aContSchema.getAppntBirthday();
		this.AppntIDType = aContSchema.getAppntIDType();
		this.AppntIDNo = aContSchema.getAppntIDNo();
		this.InsuredNo = aContSchema.getInsuredNo();
		this.InsuredName = aContSchema.getInsuredName();
		this.InsuredSex = aContSchema.getInsuredSex();
		this.InsuredBirthday = aContSchema.getInsuredBirthday();
		this.InsuredIDType = aContSchema.getInsuredIDType();
		this.InsuredIDNo = aContSchema.getInsuredIDNo();
		this.TranDate = aContSchema.getTranDate();
		this.PolApplyDate = aContSchema.getPolApplyDate();
		this.SignDate = aContSchema.getSignDate();
		this.Prem = aContSchema.getPrem();
		this.Amnt = aContSchema.getAmnt();
		this.State = aContSchema.getState();
		this.Bak1 = aContSchema.getBak1();
		this.Bak2 = aContSchema.getBak2();
		this.Bak3 = aContSchema.getBak3();
		this.Bak4 = aContSchema.getBak4();
		this.Bak5 = aContSchema.getBak5();
		this.Bak6 = aContSchema.getBak6();
		this.Bak7 = aContSchema.getBak7();
		this.Bak8 = aContSchema.getBak8();
		this.Bak9 = aContSchema.getBak9();
		this.Bak10 = aContSchema.getBak10();
		this.MakeDate = aContSchema.getMakeDate();
		this.MakeTime = aContSchema.getMakeTime();
		this.ModifyDate = aContSchema.getModifyDate();
		this.ModifyTime = aContSchema.getModifyTime();
		this.Operator = aContSchema.getOperator();
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
			this.RecordNo = rs.getInt("RecordNo");
			this.Type = rs.getInt("Type");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalPrtNo") == null )
				this.ProposalPrtNo = null;
			else
				this.ProposalPrtNo = rs.getString("ProposalPrtNo").trim();

			this.ProductId = rs.getInt("ProductId");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getInt("AppntBirthday");
			if( rs.getString("AppntIDType") == null )
				this.AppntIDType = null;
			else
				this.AppntIDType = rs.getString("AppntIDType").trim();

			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getInt("InsuredBirthday");
			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			this.TranDate = rs.getInt("TranDate");
			this.PolApplyDate = rs.getInt("PolApplyDate");
			this.SignDate = rs.getInt("SignDate");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.State = rs.getInt("State");
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

			if( rs.getString("Bak6") == null )
				this.Bak6 = null;
			else
				this.Bak6 = rs.getString("Bak6").trim();

			if( rs.getString("Bak7") == null )
				this.Bak7 = null;
			else
				this.Bak7 = rs.getString("Bak7").trim();

			if( rs.getString("Bak8") == null )
				this.Bak8 = null;
			else
				this.Bak8 = rs.getString("Bak8").trim();

			if( rs.getString("Bak9") == null )
				this.Bak9 = null;
			else
				this.Bak9 = rs.getString("Bak9").trim();

			if( rs.getString("Bak10") == null )
				this.Bak10 = null;
			else
				this.Bak10 = rs.getString("Bak10").trim();

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
			tError.moduleName = "ContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public ContSchema getSchema()
	{
		ContSchema aContSchema = new ContSchema();
		aContSchema.setSchema(this);
		return aContSchema;
	}

	public ContDB getDB()
	{
		ContDB aDBOper = new ContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpCont描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(RecordNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Type) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalPrtNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ProductId) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntSex) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AppntBirthday) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AppntIDNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredSex) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(InsuredBirthday) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InsuredIDNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PolApplyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(SignDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Prem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Amnt) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(State) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak6) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak7) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak8) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak9) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak10) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RecordNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			Type= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalPrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProductId= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppntBirthday= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			AppntIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InsuredBirthday= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			TranDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			PolApplyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			SignDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			State= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).intValue();
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Bak6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Bak7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Bak8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			Bak9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Bak10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContSchema";
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
		if (FCode.equals("RecordNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecordNo));
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
		if (FCode.equals("ProductId"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProductId));
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
		if (FCode.equals("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equals("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equals("AppntSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntSex));
		}
		if (FCode.equals("AppntBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntBirthday));
		}
		if (FCode.equals("AppntIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDType));
		}
		if (FCode.equals("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
		}
		if (FCode.equals("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equals("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equals("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equals("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredBirthday));
		}
		if (FCode.equals("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equals("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
		}
		if (FCode.equals("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolApplyDate));
		}
		if (FCode.equals("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDate));
		}
		if (FCode.equals("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equals("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equals("Bak6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak6));
		}
		if (FCode.equals("Bak7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak7));
		}
		if (FCode.equals("Bak8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak8));
		}
		if (FCode.equals("Bak9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak9));
		}
		if (FCode.equals("Bak10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak10));
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
				strFieldValue = String.valueOf(RecordNo);
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
				strFieldValue = String.valueOf(ProductId);
				break;
			case 5:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 15:
				strFieldValue = String.valueOf(AppntBirthday);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AppntIDType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 21:
				strFieldValue = String.valueOf(InsuredBirthday);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 24:
				strFieldValue = String.valueOf(TranDate);
				break;
			case 25:
				strFieldValue = String.valueOf(PolApplyDate);
				break;
			case 26:
				strFieldValue = String.valueOf(SignDate);
				break;
			case 27:
				strFieldValue = String.valueOf(Prem);
				break;
			case 28:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 29:
				strFieldValue = String.valueOf(State);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Bak6);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Bak7);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Bak8);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(Bak9);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Bak10);
				break;
			case 40:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 41:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 42:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 43:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 44:
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

		if (FCode.equals("RecordNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RecordNo = i;
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
		if (FCode.equals("ProductId"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ProductId = i;
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
		if (FCode.equals("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
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
		if (FCode.equals("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
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
		if (FCode.equals("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equals("AppntSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntSex = FValue.trim();
			}
			else
				AppntSex = null;
		}
		if (FCode.equals("AppntBirthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppntBirthday = i;
			}
		}
		if (FCode.equals("AppntIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDType = FValue.trim();
			}
			else
				AppntIDType = null;
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
		if (FCode.equals("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equals("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equals("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredBirthday = i;
			}
		}
		if (FCode.equals("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
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
		if (FCode.equals("TranDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranDate = i;
			}
		}
		if (FCode.equals("PolApplyDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolApplyDate = i;
			}
		}
		if (FCode.equals("SignDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SignDate = i;
			}
		}
		if (FCode.equals("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equals("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
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
		if (FCode.equals("Bak6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak6 = FValue.trim();
			}
			else
				Bak6 = null;
		}
		if (FCode.equals("Bak7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak7 = FValue.trim();
			}
			else
				Bak7 = null;
		}
		if (FCode.equals("Bak8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak8 = FValue.trim();
			}
			else
				Bak8 = null;
		}
		if (FCode.equals("Bak9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak9 = FValue.trim();
			}
			else
				Bak9 = null;
		}
		if (FCode.equals("Bak10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak10 = FValue.trim();
			}
			else
				Bak10 = null;
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
		ContSchema other = (ContSchema)otherObject;
		return
			RecordNo == other.getRecordNo()
			&& Type == other.getType()
			&& ContNo.equals(other.getContNo())
			&& ProposalPrtNo.equals(other.getProposalPrtNo())
			&& ProductId == other.getProductId()
			&& TranCom == other.getTranCom()
			&& NodeNo.equals(other.getNodeNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentComName.equals(other.getAgentComName())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentName.equals(other.getAgentName())
			&& ManageCom.equals(other.getManageCom())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& AppntBirthday == other.getAppntBirthday()
			&& AppntIDType.equals(other.getAppntIDType())
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& InsuredBirthday == other.getInsuredBirthday()
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& TranDate == other.getTranDate()
			&& PolApplyDate == other.getPolApplyDate()
			&& SignDate == other.getSignDate()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& State == other.getState()
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& Bak6.equals(other.getBak6())
			&& Bak7.equals(other.getBak7())
			&& Bak8.equals(other.getBak8())
			&& Bak9.equals(other.getBak9())
			&& Bak10.equals(other.getBak10())
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
		if( strFieldName.equals("RecordNo") ) {
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
		if( strFieldName.equals("ProductId") ) {
			return 4;
		}
		if( strFieldName.equals("TranCom") ) {
			return 5;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 6;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 7;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 9;
		}
		if( strFieldName.equals("AgentName") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 12;
		}
		if( strFieldName.equals("AppntName") ) {
			return 13;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 14;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 15;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return 16;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 17;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 18;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 19;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 20;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 21;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 22;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 23;
		}
		if( strFieldName.equals("TranDate") ) {
			return 24;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 25;
		}
		if( strFieldName.equals("SignDate") ) {
			return 26;
		}
		if( strFieldName.equals("Prem") ) {
			return 27;
		}
		if( strFieldName.equals("Amnt") ) {
			return 28;
		}
		if( strFieldName.equals("State") ) {
			return 29;
		}
		if( strFieldName.equals("Bak1") ) {
			return 30;
		}
		if( strFieldName.equals("Bak2") ) {
			return 31;
		}
		if( strFieldName.equals("Bak3") ) {
			return 32;
		}
		if( strFieldName.equals("Bak4") ) {
			return 33;
		}
		if( strFieldName.equals("Bak5") ) {
			return 34;
		}
		if( strFieldName.equals("Bak6") ) {
			return 35;
		}
		if( strFieldName.equals("Bak7") ) {
			return 36;
		}
		if( strFieldName.equals("Bak8") ) {
			return 37;
		}
		if( strFieldName.equals("Bak9") ) {
			return 38;
		}
		if( strFieldName.equals("Bak10") ) {
			return 39;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 40;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 41;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 42;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 43;
		}
		if( strFieldName.equals("Operator") ) {
			return 44;
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
				strFieldName = "RecordNo";
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
				strFieldName = "ProductId";
				break;
			case 5:
				strFieldName = "TranCom";
				break;
			case 6:
				strFieldName = "NodeNo";
				break;
			case 7:
				strFieldName = "AgentCom";
				break;
			case 8:
				strFieldName = "AgentComName";
				break;
			case 9:
				strFieldName = "AgentCode";
				break;
			case 10:
				strFieldName = "AgentName";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "AppntNo";
				break;
			case 13:
				strFieldName = "AppntName";
				break;
			case 14:
				strFieldName = "AppntSex";
				break;
			case 15:
				strFieldName = "AppntBirthday";
				break;
			case 16:
				strFieldName = "AppntIDType";
				break;
			case 17:
				strFieldName = "AppntIDNo";
				break;
			case 18:
				strFieldName = "InsuredNo";
				break;
			case 19:
				strFieldName = "InsuredName";
				break;
			case 20:
				strFieldName = "InsuredSex";
				break;
			case 21:
				strFieldName = "InsuredBirthday";
				break;
			case 22:
				strFieldName = "InsuredIDType";
				break;
			case 23:
				strFieldName = "InsuredIDNo";
				break;
			case 24:
				strFieldName = "TranDate";
				break;
			case 25:
				strFieldName = "PolApplyDate";
				break;
			case 26:
				strFieldName = "SignDate";
				break;
			case 27:
				strFieldName = "Prem";
				break;
			case 28:
				strFieldName = "Amnt";
				break;
			case 29:
				strFieldName = "State";
				break;
			case 30:
				strFieldName = "Bak1";
				break;
			case 31:
				strFieldName = "Bak2";
				break;
			case 32:
				strFieldName = "Bak3";
				break;
			case 33:
				strFieldName = "Bak4";
				break;
			case 34:
				strFieldName = "Bak5";
				break;
			case 35:
				strFieldName = "Bak6";
				break;
			case 36:
				strFieldName = "Bak7";
				break;
			case 37:
				strFieldName = "Bak8";
				break;
			case 38:
				strFieldName = "Bak9";
				break;
			case 39:
				strFieldName = "Bak10";
				break;
			case 40:
				strFieldName = "MakeDate";
				break;
			case 41:
				strFieldName = "MakeTime";
				break;
			case 42:
				strFieldName = "ModifyDate";
				break;
			case 43:
				strFieldName = "ModifyTime";
				break;
			case 44:
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
		if( strFieldName.equals("RecordNo") ) {
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
		if( strFieldName.equals("ProductId") ) {
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
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("Bak6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak10") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_INT;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_INT;
				break;
			case 41:
				nFieldType = Schema.TYPE_INT;
				break;
			case 42:
				nFieldType = Schema.TYPE_INT;
				break;
			case 43:
				nFieldType = Schema.TYPE_INT;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
