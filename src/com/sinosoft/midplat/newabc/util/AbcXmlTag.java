package com.sinosoft.midplat.newabc.util;


public interface AbcXmlTag {
	String PACKAGESPLITER ="|";
	String ABCB2I="ABCB2I";
	String Req = "Req"; //银行请求
	String Ret = "Ret"; //保险公司返回
	//AbcNetImpl
	String TranCom = "TranCom";
	String ip = "ip";
	String FuncFlag = "FuncFlag";
	String ClientIp = "ClientIp";
	String Content = "Content";
	
	//Abc4YBT
	String preNet = "preNet";
	String format= "format";
	String service = "service";
	String jyqrFlag = "jyqrFlag";
	
	int mesHeadLen = 8;
	String tuxSrv = "tuxSrv";
	
	/*ABC_TK****************/
	//包头
	String Header = "Header";  //包头根节点
	String SerialNo= "SerialNo";  //银行交易流水号
	String InsuSerial= "InsuSerial";  //保险公司流水号
	String TransDate= "TransDate";  //交易日期  保单生效日期
	String TransTime= "TransTime";  //交易时间
	String BankCode= "BankCode";  //银行代码
	String CorpNo= "CorpNo";  //保险公司代码
	String TransCode= "TransCode";  //交易编码
	String TransSide= "TransSide";  //交易发起方
	String EntrustWay= "EntrustWay";  //委托方式
	String ProvCode= "ProvCode";  //省市代码
	String Tlid = "Tlid"; //柜员
	String BranchNo= "BranchNo";  //网点号
	String Saler="Saler"; //银行操作员代码
	//包体
	String App= "App";//包体根节点
	String Appl = "Appl"; //投保人信息节点
	String Insu = "Insu"; //被保人信息节点
	String Bnfs = "Bnfs"; //受益人节点
	String Risks = "Risks"; //主险信息节点
	String Addt = "Addt"; //附加险信息节点
	
	//险种信息
	String RiskCode = "RiskCode"; //保险公司方银行代码
	
	//投被保人，受益人信息
	String Name = "Name"; //姓名
	String Sex = "Sex"; //性别
	String Birthday = "Birthday"; //生日
	String IDKind = "IDKind"; //证件类型
	String IDCode = "IDCode"; //证件号码
	String Address = "Address"; //通讯地址
	String ZipCode = "ZipCode"; //邮编
	String Phone = "Phone"; //固话
	String Mobile = "Mobile"; //移动电话
	String JobCode = "JobCode"; //职业类型
	String Email = "Email"; //电子邮箱
	String RelaToInsured = "RelaToInsured"; //投被保人关系
	String Count = "Count"; //受益人个数
	String Sequence = "Sequence"; //受益顺序
	String RelationToInsured = "RelationToInsured"; //受益人与被保人关系
	String Prop = "Prop"; //受益比例
	String Base = "Base"; //基本信息
	String HealthNotice = "HealthNotice"; //被保人健康标志
	String ApplyDate = "ApplyDate"; //投保日期
	//险种
	String InvestType = "InvestType"; //投资方式
	String Share = "Share"; //份数
	String PayType = "PayType"; //缴费方式
	String InsuDueDate = "InsuDueDate"; //保险
	String InsuDueType = "InsuDueType";//保险期间类型
	String SpecArranged = "SpecArranged"; //特别约定
	String PayDueDate = "PayDueDate"; //缴费期间
	String BonusGetMode = "BonusGetMode"; //红利领取方式
	String ConAccName = "ConAccName"; //续期缴费账户名称
	String ConAccNo = "ConAccNo"; //续期缴费账号
	String Inv = "Inv"; //投连险投资账户个数
	String Type = "Type"; //投资账户代码
	String Pro = "Pro"; //投资账户比例
	String FullBonusGetMode = "FullBonusGetMode"; //满期保险金领取标志
	String Amnt = "Amnt"; //保额
	String Prem = "Prem"; //保费
	String PayAccount = "PayAccount"; //缴费账号
	String PayAmt ="PayAmt"; //缴费金额
	String Risk = "Risk"; //险种
	String Mult = "Mult"; //份数
	String PayEndYear = "PayEndYear"; //缴费期间
	String PayIntv = "PayIntv"; //缴费方式
	String InsuCode = "InsuCode"; //险种代码
	String PolicyApplyNo = "PolicyApplyNo"; //投保单号码
	String NewVchNo = "NewVchNo";//保单印刷号码
	String ProdCode = "ProdCode";//产品代码
	//交易
	String ApplySerial = "ApplySerial"; //保费试算流水号码
	String OrgSerialNo = "OrgSerialNo"; //原流水号码
	String OrgTransDate = "OrgTransDate";//原交易日期
	//保单返回信息
	String PolicyNo = "PolicyNo"; //保单号码
	String ClientName = "ClientName";//申请人
	String IdKind = "IdKind";//证件类型
	String IdCode = "IdCode"; //证件号码
	String VchNo = "VchNo"; //保单印刷号码
	String AcceptDate = "AcceptDate"; //签约日期
	String ValidDate ="ValidDate";//保险生效日期
	String PolicyDuedate = "PolicyDuedate"; //保单终止日期
	String PayDuedate = "PayDuedate"; //缴费期间
	String UserId = "UserId"; //业务员代码
	String ProdName = "ProdName"; //险种名称
	String Prnts = "Prnts"; //保单打印
	String Prnt = "Prnt"; //保单行数
	String Value = "Value"; //行数的内容
	String Messages = "Messages"; //现金价值打印
	String Message = "Message"; //现金价值行数 
	String TbdPrnts = "TbdPrnts"; //投保单打印
	String TbdPrnt = "TbdPrnt"; //投保单行数
	String DuePeriod = "DuePeriod"; //续期应缴期数
	String DueDate = "DueDate"; //续期应缴日期
	String DueAmt = "DueAmt"; //续期应缴金额
	String PayAcc = "PayAcc"; //缴费金额
	
	String PolicyApplySerial = "PolicyApplySerial"; //投保单号码
	String Flag = "Flag";
	String Mesg = "Mesg";
	String RetData = "RetData";
	String ProposalContNo = "ProposalContNo";
	String ReqsrNo = "ReqsrNo";
	
}
