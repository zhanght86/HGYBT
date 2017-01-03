package com.sinosoft.midplat.common;

/**
 * 核心报文标签
 * @author yuantongxin
 */
public interface XmlTag {
	
	/*
	 * add by fzg 20120705 IC卡相关节点 Start
	 */
	String BankAccountNum = "BankAccountNum";//卡应用序列号
	String TerminalCode = "TerminalCode";//自助终端号
	String KeyCode = "KeyCode";//密钥代码
	String RandomNo = "RandomNo";//终端生成的随机数
	String CommData = "CommData";//指令流
	String FileID = "FileID";//数据文件ID
	String ConsumerId = "ConsumerId";//中间平台客户ID号
	String Status = "Status";//银行卡号状态
	String AskNo = "AskNo";//请求中心代码
	
	/*
	 * The  End
	 */
	
	/*midplat******************/
	String midplat = "midplat";
	String database = "database";
	String type = "type";
	String name = "name";
	String ip = "ip";
	String port = "port";
	String user = "user";
	String password = "password";
	String confLoad = "confLoad";
	String sleepSecond = "sleepSecond";
	String out = "out";
	String webservice = "webservice";
	String address = "address";
	String method = "method";
	String parameter = "parameter";
	String namespace = "namespace";
	String suf = "suf";
	String url = "url";
	String charset = "charset";
	String timeout = "timeout";
	String batchs = "batchs";
	String autoRestart = "autoRestart";
	String batch = "batch";
	String startTime = "startTime";
	String sockets = "sockets";
	String socket = "socket";
	String localDir = "localDir";
	String ftp = "ftp";
	String path = "path";
	String reconn = "reconn";
	String preNet = "preNet";
	String business = "business";
	String funcFlag = "funcFlag";
	String outcode = "outcode";
	String format = "format";
	String service = "service";
	String log = "log";
	String reset = "reset";
	String cluster = "cluster";
	String id = "id";
	String count = "count";
	String rules = "rules";
	String rule = "rule";
	String value = "value";
	String msg = "msg";
	String root = "root";
	String locktime = "locktime";
	/*sftp *******************/
	String sftp = "sftp";
	/*std xml******************/
	String TranData = "TranData";
	
	String Head = "Head";
	String Body = "Body";
	
	String TranDate = "TranDate";
	String TranTime = "TranTime";
	String TranCom = "TranCom";
	String ZoneNo = "ZoneNo";
	String NodeNo = "NodeNo";
	String FuncFlag = "FuncFlag";
	String ServiceId = "ServiceId";
	String TellerNo = "TellerNo";
	String TranNo = "TranNo";
	String OldTranNo = "OldTranNo";
	String LogNo = "LogNo";
	String ClientIp = "ClientIp";
	String Flag = "Flag";
	String Desc = "Desc";
	
	String Appnt = "Appnt";
	String Insured = "Insured";
	String Bnf = "Bnf";
	String Risk = "Risk";//险种
	
	String ProposalPrtNo = "ProposalPrtNo";//投保单(印刷)号
	String ContPrtNo = "ContPrtNo";//保单合同印刷号 (单证) 
	String ContNo = "ContNo";//保险单号
	String AccName = "AccName";//账户姓名
	String AccNo = "AccNo";//银行账户
	String CreditContNo = "CreditContNo";
	String CreditStartDate = "CreditStartDate";
	String CreditEndDate = "CreditEndDate";
	String GetPolMode = "GetPolMode";//保单递送方式
	String ProductId = "ProductId";
	String Amnt = "Amnt";//保额(分) 
	String AmntText = "AmntText";
	String Prem = "Prem";//保险费(分)
	String PremText = "PremText";
	String ManageCom = "ManageCom";
	String AgentCode = "AgentCode";//代理人编码
	String AgentName = "AgentName";//代理人姓名
	String AgentGrpCode = "AgentGrpCode";//代理人组别编码
	String AgentGrpName = "AgentGrpName";//代理人组别
	String AgentCom = "AgentCom";//代理机构编码
	String AgentComName = "AgentComName";//代理机构名称
	String ComCode = "ComCode";//承保公司编码
	String ComName = "ComName";//承保公司名称
	String ComLocation = "ComLocation";//承保公司地址
	String ComZipCode = "ComZipCode";//承保公司邮编
	String ComPhone = "ComPhone";//承保公司电话
	String SpecContent = "SpecContent";//特别约定
	
	String CustomerNo = "CustomerNo";//客户号
	String Name = "Name";//姓名
	String Sex = "Sex";//性别
	String Birthday = "Birthday";//出生日期
	String IDType = "IDType";//证件类型
	String IDNo = "IDNo";//证件号码
	String JobType = "JobType";//职业类别
	String JobCode = "JobCode";//职业代码
	String JobName = "JobName";//职业名称
	String Nationality = "Nationality";//国籍
	String Stature = "Stature";//身高(cm)
	String Weight = "Weight";//体重(g) 
	String MaritalStatus = "MaritalStatus";//婚否(N/Y)
	String Address = "Address";//地址
	String ZipCode = "ZipCode";//邮编
	String Phone = "Phone";//固定电话
	String Mobile = "Mobile";//移动电话
	String Email = "Email";//电子邮件
	String RelaToInsured = "RelaToInsured";//与被保人关系
	
	String Type = "Type";//受益人类别
	String Grade = "Grade";//受益顺序
	String Lot = "Lot";//受益比例(整数，百分比) 
	
	String RiskCode = "RiskCode";//险种代码
	String RiskName = "RiskName";//险种名称
	String MainRiskCode = "MainRiskCode";//主险代码
	String Mult = "Mult";//投保份数
	String PayIntv = "PayIntv";//缴费频次
	String PayMode = "PayMode";//缴费形式
	String PolApplyDate = "PolApplyDate";//投保日期
	String SignDate = "SignDate";//承保日期
	String CValiDate = "CValiDate";//起保日期
	String InsuEndDate = "InsuEndDate";//保险责任终止日期
	String InsuYearFlag = "InsuYearFlag";//保险年龄年期标志
	String InsuYear = "InsuYear";//保险年龄年期
	String PayEndYearFlag = "PayEndYearFlag";//缴费年期类型
	String PayEndYear = "PayEndYear";//缴费年期
	String PayEndDate = "PayEndDate";//终缴日期
	String BonusGetMode = "BonusGetMode";//红利领取方式
	String FullBonusGetMode = "FullBonusGetMode";//满期领取金领取方式
	String GetYearFlag = "GetYearFlag";//领取年龄年期标志
	String GetYear = "GetYear";//领取年龄
	String GetIntv = "GetIntv";//领取方式
	String CostIntv = "CostIntv";//扣款间隔
	String CostDate = "CostDate";//扣款时间
	String AutoPayFlag = "AutoPayFlag";//自动垫交标志
	String SubFlag = "SubFlag";//减额交清标志
	String Account = "Account";//交费账户
	String AccMoney = "AccMoney";//账户金额
	String AccRate = "AccRate";//账户比率
	
	String CashValues = "CashValues";//现金价值表
	String CashValue = "CashValue";//现金价值项
	String BonusValues = "BonusValues";//红利保额保单年度末利益价值表
	String BonusValue = "BonusValue";//红利保额保单年度末利益价值项
	String EndYear = "EndYear";//年度
	String Cash = "Cash";//现金价值
	
	String EdorPrtNo = "EdorPrtNo";
	
	String Detail = "Detail";//明细
	
	String Count = "Count";//总笔数
	
	String OperType = "OperType";
	String CardType = "CardType";//单证类型
	String CardNo = "CardNo";//单证号[保单印刷号]
	String State = "State";//状态
	String OtherNo = "OtherNo";//单证关联号(保单号、保全号等)
	String Channel = "Channel";//银行交易渠道
	String Result = "Result";//结果代码
	String ResultText = "ResultText";//结果描述
	
	String Error = "Error";
	String TempTag = "TempTag";
	String StartNo = "StartNo";//保险重空起始案号
	String EndNo = "EndNo";//保险重空结束案号
	String StartDate = "StartDate";
	String EndDate = "EndDate";
	
	/*soap******************/
	String SOAP_URI = "http://schemas.xmlsoap.org/soap/envelope/";
	String Fault = "Fault";
	String faultcode = "faultcode";
	String faultstring = "faultstring";
}