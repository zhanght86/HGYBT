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
	
	String ProposalPrtNo = "ProposalPrtNo";
	String ContPrtNo = "ContPrtNo";
	String ContNo = "ContNo";
	String AccName = "AccName";
	String AccNo = "AccNo";
	String CreditContNo = "CreditContNo";
	String CreditStartDate = "CreditStartDate";
	String CreditEndDate = "CreditEndDate";
	String GetPolMode = "GetPolMode";
	String ProductId = "ProductId";
	String Amnt = "Amnt";
	String AmntText = "AmntText";
	String Prem = "Prem";
	String PremText = "PremText";
	String ManageCom = "ManageCom";
	String AgentCode = "AgentCode";
	String AgentName = "AgentName";
	String AgentGrpCode = "AgentGrpCode";
	String AgentGrpName = "AgentGrpName";
	String AgentCom = "AgentCom";
	String AgentComName = "AgentComName";
	String ComCode = "ComCode";
	String ComName = "ComName";
	String ComLocation = "ComLocation";
	String ComZipCode = "ComZipCode";
	String ComPhone = "ComPhone";
	String SpecContent = "SpecContent";
	
	String CustomerNo = "CustomerNo";
	String Name = "Name";
	String Sex = "Sex";
	String Birthday = "Birthday";
	String IDType = "IDType";
	String IDNo = "IDNo";
	String JobType = "JobType";
	String JobCode = "JobCode";
	String JobName = "JobName";
	String Nationality = "Nationality";
	String Stature = "Stature";
	String Weight = "Weight";
	String MaritalStatus = "MaritalStatus";
	String Address = "Address";
	String ZipCode = "ZipCode";
	String Phone = "Phone";
	String Mobile = "Mobile";
	String Email = "Email";
	String RelaToInsured = "RelaToInsured";
	
	String Type = "Type";
	String Grade = "Grade";
	String Lot = "Lot";
	
	String RiskCode = "RiskCode";
	String RiskName = "RiskName";//险种名称
	String MainRiskCode = "MainRiskCode";
	String Mult = "Mult";//投保份数投保份数
	String PayIntv = "PayIntv";//缴费频次
	String PayMode = "PayMode";//缴费形式
	String PolApplyDate = "PolApplyDate";
	String SignDate = "SignDate";
	String CValiDate = "CValiDate";
	String InsuEndDate = "InsuEndDate";
	String InsuYearFlag = "InsuYearFlag";
	String InsuYear = "InsuYear";
	String PayEndYearFlag = "PayEndYearFlag";//缴费年期类型
	String PayEndYear = "PayEndYear";//缴费年期
	String PayEndDate = "PayEndDate";
	String BonusGetMode = "BonusGetMode";
	String FullBonusGetMode = "FullBonusGetMode";
	String GetYearFlag = "GetYearFlag";
	String GetYear = "GetYear";//领取年龄
	String GetIntv = "GetIntv";
	String CostIntv = "CostIntv";
	String CostDate = "CostDate";
	String AutoPayFlag = "AutoPayFlag";
	String SubFlag = "SubFlag";
	String Account = "Account";
	String AccMoney = "AccMoney";
	String AccRate = "AccRate";
	
	String CashValues = "CashValues";
	String CashValue = "CashValue";
	String BonusValues = "BonusValues";
	String BonusValue = "BonusValue";
	String EndYear = "EndYear";
	String Cash = "Cash";
	
	String EdorPrtNo = "EdorPrtNo";
	
	String Detail = "Detail";
	
	String Count = "Count";
	
	String OperType = "OperType";
	String CardType = "CardType";
	String CardNo = "CardNo";
	String State = "State";
	String OtherNo = "OtherNo";
	String Channel = "Channel";
	String Result = "Result";
	String ResultText = "ResultText";
	
	String Error = "Error";
	String TempTag = "TempTag";
	String StartNo = "StartNo";
	String EndNo = "EndNo";
	String StartDate = "StartDate";
	String EndDate = "EndDate";
	
	/*soap******************/
	String SOAP_URI = "http://schemas.xmlsoap.org/soap/envelope/";
	String Fault = "Fault";
	String faultcode = "faultcode";
	String faultstring = "faultstring";
}