package com.sinosoft.midplat.common;

/**
 * ���ı��ı�ǩ
 * @author yuantongxin
 */
public interface XmlTag {
	
	/*
	 * add by fzg 20120705 IC����ؽڵ� Start
	 */
	String BankAccountNum = "BankAccountNum";//��Ӧ�����к�
	String TerminalCode = "TerminalCode";//�����ն˺�
	String KeyCode = "KeyCode";//��Կ����
	String RandomNo = "RandomNo";//�ն����ɵ������
	String CommData = "CommData";//ָ����
	String FileID = "FileID";//�����ļ�ID
	String ConsumerId = "ConsumerId";//�м�ƽ̨�ͻ�ID��
	String Status = "Status";//���п���״̬
	String AskNo = "AskNo";//�������Ĵ���
	
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
	String Risk = "Risk";//����
	
	String ProposalPrtNo = "ProposalPrtNo";//Ͷ����(ӡˢ)��
	String ContPrtNo = "ContPrtNo";//������ͬӡˢ�� (��֤) 
	String ContNo = "ContNo";//���յ���
	String AccName = "AccName";//�˻�����
	String AccNo = "AccNo";//�����˻�
	String CreditContNo = "CreditContNo";
	String CreditStartDate = "CreditStartDate";
	String CreditEndDate = "CreditEndDate";
	String GetPolMode = "GetPolMode";//�������ͷ�ʽ
	String ProductId = "ProductId";
	String Amnt = "Amnt";//����(��) 
	String AmntText = "AmntText";
	String Prem = "Prem";//���շ�(��)
	String PremText = "PremText";
	String ManageCom = "ManageCom";
	String AgentCode = "AgentCode";//�����˱���
	String AgentName = "AgentName";//����������
	String AgentGrpCode = "AgentGrpCode";//������������
	String AgentGrpName = "AgentGrpName";//���������
	String AgentCom = "AgentCom";//�����������
	String AgentComName = "AgentComName";//�����������
	String ComCode = "ComCode";//�б���˾����
	String ComName = "ComName";//�б���˾����
	String ComLocation = "ComLocation";//�б���˾��ַ
	String ComZipCode = "ComZipCode";//�б���˾�ʱ�
	String ComPhone = "ComPhone";//�б���˾�绰
	String SpecContent = "SpecContent";//�ر�Լ��
	
	String CustomerNo = "CustomerNo";//�ͻ���
	String Name = "Name";//����
	String Sex = "Sex";//�Ա�
	String Birthday = "Birthday";//��������
	String IDType = "IDType";//֤������
	String IDNo = "IDNo";//֤������
	String JobType = "JobType";//ְҵ���
	String JobCode = "JobCode";//ְҵ����
	String JobName = "JobName";//ְҵ����
	String Nationality = "Nationality";//����
	String Stature = "Stature";//���(cm)
	String Weight = "Weight";//����(g) 
	String MaritalStatus = "MaritalStatus";//���(N/Y)
	String Address = "Address";//��ַ
	String ZipCode = "ZipCode";//�ʱ�
	String Phone = "Phone";//�̶��绰
	String Mobile = "Mobile";//�ƶ��绰
	String Email = "Email";//�����ʼ�
	String RelaToInsured = "RelaToInsured";//�뱻���˹�ϵ
	
	String Type = "Type";//���������
	String Grade = "Grade";//����˳��
	String Lot = "Lot";//�������(�������ٷֱ�) 
	
	String RiskCode = "RiskCode";//���ִ���
	String RiskName = "RiskName";//��������
	String MainRiskCode = "MainRiskCode";//���մ���
	String Mult = "Mult";//Ͷ������
	String PayIntv = "PayIntv";//�ɷ�Ƶ��
	String PayMode = "PayMode";//�ɷ���ʽ
	String PolApplyDate = "PolApplyDate";//Ͷ������
	String SignDate = "SignDate";//�б�����
	String CValiDate = "CValiDate";//������
	String InsuEndDate = "InsuEndDate";//����������ֹ����
	String InsuYearFlag = "InsuYearFlag";//�����������ڱ�־
	String InsuYear = "InsuYear";//������������
	String PayEndYearFlag = "PayEndYearFlag";//�ɷ���������
	String PayEndYear = "PayEndYear";//�ɷ�����
	String PayEndDate = "PayEndDate";//�ս�����
	String BonusGetMode = "BonusGetMode";//������ȡ��ʽ
	String FullBonusGetMode = "FullBonusGetMode";//������ȡ����ȡ��ʽ
	String GetYearFlag = "GetYearFlag";//��ȡ�������ڱ�־
	String GetYear = "GetYear";//��ȡ����
	String GetIntv = "GetIntv";//��ȡ��ʽ
	String CostIntv = "CostIntv";//�ۿ���
	String CostDate = "CostDate";//�ۿ�ʱ��
	String AutoPayFlag = "AutoPayFlag";//�Զ��潻��־
	String SubFlag = "SubFlag";//������־
	String Account = "Account";//�����˻�
	String AccMoney = "AccMoney";//�˻����
	String AccRate = "AccRate";//�˻�����
	
	String CashValues = "CashValues";//�ֽ��ֵ��
	String CashValue = "CashValue";//�ֽ��ֵ��
	String BonusValues = "BonusValues";//������������ĩ�����ֵ��
	String BonusValue = "BonusValue";//������������ĩ�����ֵ��
	String EndYear = "EndYear";//���
	String Cash = "Cash";//�ֽ��ֵ
	
	String EdorPrtNo = "EdorPrtNo";
	
	String Detail = "Detail";//��ϸ
	
	String Count = "Count";//�ܱ���
	
	String OperType = "OperType";
	String CardType = "CardType";//��֤����
	String CardNo = "CardNo";//��֤��[����ӡˢ��]
	String State = "State";//״̬
	String OtherNo = "OtherNo";//��֤������(�����š���ȫ�ŵ�)
	String Channel = "Channel";//���н�������
	String Result = "Result";//�������
	String ResultText = "ResultText";//�������
	
	String Error = "Error";
	String TempTag = "TempTag";
	String StartNo = "StartNo";//�����ؿ���ʼ����
	String EndNo = "EndNo";//�����ؿս�������
	String StartDate = "StartDate";
	String EndDate = "EndDate";
	
	/*soap******************/
	String SOAP_URI = "http://schemas.xmlsoap.org/soap/envelope/";
	String Fault = "Fault";
	String faultcode = "faultcode";
	String faultstring = "faultstring";
}