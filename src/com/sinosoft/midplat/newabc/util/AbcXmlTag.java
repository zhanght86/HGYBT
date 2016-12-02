package com.sinosoft.midplat.newabc.util;


public interface AbcXmlTag {
	String PACKAGESPLITER ="|";
	String ABCB2I="ABCB2I";
	String Req = "Req"; //��������
	String Ret = "Ret"; //���չ�˾����
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
	//��ͷ
	String Header = "Header";  //��ͷ���ڵ�
	String SerialNo= "SerialNo";  //���н�����ˮ��
	String InsuSerial= "InsuSerial";  //���չ�˾��ˮ��
	String TransDate= "TransDate";  //��������  ������Ч����
	String TransTime= "TransTime";  //����ʱ��
	String BankCode= "BankCode";  //���д���
	String CorpNo= "CorpNo";  //���չ�˾����
	String TransCode= "TransCode";  //���ױ���
	String TransSide= "TransSide";  //���׷���
	String EntrustWay= "EntrustWay";  //ί�з�ʽ
	String ProvCode= "ProvCode";  //ʡ�д���
	String Tlid = "Tlid"; //��Ա
	String BranchNo= "BranchNo";  //�����
	String Saler="Saler"; //���в���Ա����
	//����
	String App= "App";//������ڵ�
	String Appl = "Appl"; //Ͷ������Ϣ�ڵ�
	String Insu = "Insu"; //��������Ϣ�ڵ�
	String Bnfs = "Bnfs"; //�����˽ڵ�
	String Risks = "Risks"; //������Ϣ�ڵ�
	String Addt = "Addt"; //��������Ϣ�ڵ�
	
	//������Ϣ
	String RiskCode = "RiskCode"; //���չ�˾�����д���
	
	//Ͷ�����ˣ���������Ϣ
	String Name = "Name"; //����
	String Sex = "Sex"; //�Ա�
	String Birthday = "Birthday"; //����
	String IDKind = "IDKind"; //֤������
	String IDCode = "IDCode"; //֤������
	String Address = "Address"; //ͨѶ��ַ
	String ZipCode = "ZipCode"; //�ʱ�
	String Phone = "Phone"; //�̻�
	String Mobile = "Mobile"; //�ƶ��绰
	String JobCode = "JobCode"; //ְҵ����
	String Email = "Email"; //��������
	String RelaToInsured = "RelaToInsured"; //Ͷ�����˹�ϵ
	String Count = "Count"; //�����˸���
	String Sequence = "Sequence"; //����˳��
	String RelationToInsured = "RelationToInsured"; //�������뱻���˹�ϵ
	String Prop = "Prop"; //�������
	String Base = "Base"; //������Ϣ
	String HealthNotice = "HealthNotice"; //�����˽�����־
	String ApplyDate = "ApplyDate"; //Ͷ������
	//����
	String InvestType = "InvestType"; //Ͷ�ʷ�ʽ
	String Share = "Share"; //����
	String PayType = "PayType"; //�ɷѷ�ʽ
	String InsuDueDate = "InsuDueDate"; //����
	String InsuDueType = "InsuDueType";//�����ڼ�����
	String SpecArranged = "SpecArranged"; //�ر�Լ��
	String PayDueDate = "PayDueDate"; //�ɷ��ڼ�
	String BonusGetMode = "BonusGetMode"; //������ȡ��ʽ
	String ConAccName = "ConAccName"; //���ڽɷ��˻�����
	String ConAccNo = "ConAccNo"; //���ڽɷ��˺�
	String Inv = "Inv"; //Ͷ����Ͷ���˻�����
	String Type = "Type"; //Ͷ���˻�����
	String Pro = "Pro"; //Ͷ���˻�����
	String FullBonusGetMode = "FullBonusGetMode"; //���ڱ��ս���ȡ��־
	String Amnt = "Amnt"; //����
	String Prem = "Prem"; //����
	String PayAccount = "PayAccount"; //�ɷ��˺�
	String PayAmt ="PayAmt"; //�ɷѽ��
	String Risk = "Risk"; //����
	String Mult = "Mult"; //����
	String PayEndYear = "PayEndYear"; //�ɷ��ڼ�
	String PayIntv = "PayIntv"; //�ɷѷ�ʽ
	String InsuCode = "InsuCode"; //���ִ���
	String PolicyApplyNo = "PolicyApplyNo"; //Ͷ��������
	String NewVchNo = "NewVchNo";//����ӡˢ����
	String ProdCode = "ProdCode";//��Ʒ����
	//����
	String ApplySerial = "ApplySerial"; //����������ˮ����
	String OrgSerialNo = "OrgSerialNo"; //ԭ��ˮ����
	String OrgTransDate = "OrgTransDate";//ԭ��������
	//����������Ϣ
	String PolicyNo = "PolicyNo"; //��������
	String ClientName = "ClientName";//������
	String IdKind = "IdKind";//֤������
	String IdCode = "IdCode"; //֤������
	String VchNo = "VchNo"; //����ӡˢ����
	String AcceptDate = "AcceptDate"; //ǩԼ����
	String ValidDate ="ValidDate";//������Ч����
	String PolicyDuedate = "PolicyDuedate"; //������ֹ����
	String PayDuedate = "PayDuedate"; //�ɷ��ڼ�
	String UserId = "UserId"; //ҵ��Ա����
	String ProdName = "ProdName"; //��������
	String Prnts = "Prnts"; //������ӡ
	String Prnt = "Prnt"; //��������
	String Value = "Value"; //����������
	String Messages = "Messages"; //�ֽ��ֵ��ӡ
	String Message = "Message"; //�ֽ��ֵ���� 
	String TbdPrnts = "TbdPrnts"; //Ͷ������ӡ
	String TbdPrnt = "TbdPrnt"; //Ͷ��������
	String DuePeriod = "DuePeriod"; //����Ӧ������
	String DueDate = "DueDate"; //����Ӧ������
	String DueAmt = "DueAmt"; //����Ӧ�ɽ��
	String PayAcc = "PayAcc"; //�ɷѽ��
	
	String PolicyApplySerial = "PolicyApplySerial"; //Ͷ��������
	String Flag = "Flag";
	String Mesg = "Mesg";
	String RetData = "RetData";
	String ProposalContNo = "ProposalContNo";
	String ReqsrNo = "ReqsrNo";
	
}
