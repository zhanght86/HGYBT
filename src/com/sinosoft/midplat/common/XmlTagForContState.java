package com.sinosoft.midplat.common;

public abstract interface XmlTagForContState {

	/**
	 * 报文根节点
	 */
	public static final String Package = "Package";
	
	/**
	 * 请求信息节点
	 */
	public static final String Request = "Request";
	
	/**
	 * 请求类别
	 */
	public static final String RequestType = "RequestType";
	
	/**
	 * 请求日期
	 */
	public static final String RequestDate = "RequestDate";
	
	/**
	 * 应答信息节点
	 */
	public static final String Response = "Response";
	
	/**
	 * 返回结果(成功或失败)
	 */
	public static final String ResultCode = "ResultCode";
	
	/**
	 * 返回结果描述
	 */
	public static final String ResultMessage = "ResultMessage";
	
	/**
	 * 返回结果节点
	 */
	public static final String Result = "Result";
	
	/**
	 * 返回结果记录数
	 */
	public static final String ResultCount = "ResultCount";
	
	/**
	 * 保单状态循环节点
	 */
	public static final String ResultInfo = "ResultInfo";
	
	/**
	 * 银行代码
	 */
	public static final String BankCode = "BankCode";
	
	/**
	 * 银行地区代码
	 */
	public static final String Zone = "Zone";
	
	/**
	 * 银行网点代码
	 */
	public static final String BranchNo = "BranchNo";
	
	/**
	 * 交易日期
	 */
	public static final String TranDate = "TranDate";
	
	/**
	 * 保单号
	 */
	public static final String ContNo = "ContNo";
	
	/**
	 * 保单印刷号
	 */
	public static final String PrtNo = "PrtNo";
	
	/**
	 * 投保单号
	 */
	public static final String ProposalNo = "ProposalNo";
	
	/**
	 * 投保人姓名
	 */
	public static final String AppntName = "AppntName";
	
	/**
	 * 投保人证件类型
	 */
	public static final String AppntIDType = "AppntIDType";
	
	/**
	 * 投保人证件号码
	 */
	public static final String AppntIDNo = "AppntIDNo";
	
	/**
	 * 投保人与被保人关系
	 */
	public static final String RelaToInsu = "RelaToInsu";
	
	/**
	 * 被保人姓名
	 */
	public static final String InsuName = "InsuName";
	
	/**
	 * 被保人证件类型
	 */
	public static final String InsuIDType = "InsuIDType";
	
	/**
	 * 被保人证件号码
	 */
	public static final String InsuIDNo = "InsuIDNo";
	
	/**
	 * 险种编码
	 */
	public static final String RiskCode = "RiskCode";
	
	/**
	 * 险种名称
	 */
	public static final String RiskName = "RiskName";
	
	/**
	 * 保费
	 */
	public static final String Prem = "Prem";
	
	/**
	 * 保额
	 */
	public static final String Amnt = "Amnt";
	
	/**
	 * 签单日期
	 */
	public static final String SignDate = "SignDate";
	
	/**
	 * 保单生效日
	 */
	public static final String ValiDate = "ValiDate";
	
	/**
	 * 保单到期日
	 */
	public static final String InsuToDate = "InsuToDate";
	
	/**
	 * 缴费账户
	 */
	public static final String BankAccNo = "BankAccNo";
	
	/**
	 * 缴费方式
	 */
	public static final String PayIntv = "PayIntv";
	
	/**
	 * 缴费年期类型
	 */
	public static final String PayEndYearFlag = "PayEndYearFlag";
	
	/**
	 * 缴费年期
	 */
	public static final String PayEndYear = "PayEndYear";
	
	/**
	 * 保险年期类型
	 */
	public static final String InsuYearFlag = "InsuYearFlag";
	
	/**
	 * 保险年期
	 */
	public static final String InsuYear = "InsuYear";
	
	/**
	 * 交易金额
	 */
	public static final String TransAmt = "TransAmt";
	
	/**
	 * 申请人姓名
	 */
	public static final String ApplyName = "ApplyName";
	
	/**
	 * 保单状态
	 */
	public static final String ContState = "ContState";

	/**
	 * 退保时间
	 */
	public static final String SurrDate = "SurrDate";
	
	/**
	 * 处理日期
	 */
	public static final String DealDate = "DealDate";
	
	/**
	 * 退保金额
	 */
	public static final String GetMoney = "GetMoney";
	
	
	/**
	 * 投保人性别
	 */
	public static final String AppntSex = "AppntSex";
	
	/**
	 * 被保人性别
	 */
	public static final String InsuSex = "InsuSex";
}
