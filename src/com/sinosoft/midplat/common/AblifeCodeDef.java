package com.sinosoft.midplat.common;

public interface AblifeCodeDef extends CodeDef {
	/** ������ͨ�������� */
	int NodeType_Bank_Sale = 0;
	/** �����µ��������� */
	int NodeType_Bank_Bat = 1;
	/** �����˱���Ϣ�������� */
	int NodeType_Bank_EdorCTInfo = 2;
	
	/** �������� */
	int ContType_Bank = 0;
	
	/** ¼�� */
	int ContState_Input = 1;
	/** ǩ�� */
	int ContState_Sign = 2;
	/** ���ճ��� */
	int ContState_Cancel = 3;
	/** ����һ�� */
	int ContState_BlcOK = 10;
	/** �������ⵥ */
	int ContState_BlcERROR = 11;
	
	/** ����¼���˱� */
	String SID_Bank_ContInput = "0";
	/** �����շ�ǩ�� */
	String SID_Bank_ContConfirm = "1";
	/** �����µ��ع� */
	String SID_Bank_ContRollback = "2";
	/** ���������ش� */
	String SID_Bank_ContRePrint = "3";
	/** ����������ѯ */
	String SID_Bank_ContQuery = "4";
	/** �������ճ��� */
	String SID_Bank_ContCancel = "5";
	/** �����µ��ս� */
	String SID_Bank_NewContBlc = "6";
	/** ��֤(���յ�)���� */
	String SID_Bank_ContCardBlc = "7";
	/** �����˱���Ϣ���� */
	String SID_Bank_EdorCTInfo = "8";
	/** �������ڽɷѲ�ѯ */
	String SID_Bank_RenewPaymentQuery = "9";
	/** ������ԥ���˱���ѯ */
	String SID_Bank_NoTakenQuery = "10";
	/** ������ԥ���˱� */
	String SID_Bank_NoTaken = "11";
	/** ������ԥ���˱�����*/
	String SID_Bank_NoTakenCancel = "12";
	/** �����˱���ѯ */
	String SID_Bank_TakenQuery = "13";
	/** �����˱� */
	String SID_Bank_Taken = "14";
	/** �����˱�����*/
	String SID_Bank_TakenCancel = "15";
	/** ���������ڸ�����ѯ */
	String SID_Bank_ManPaymentQuery = "16";
	/** ���������ڸ��� */
	String SID_Bank_ManPayment = "17";
	/** ���������ڸ������� */
	String SID_Bank_ManPaymentCancel = "18";
	/** �������ڽɷ� */
	String SID_Bank_RenewPayment = "19";
	/** �������ڽɷѳ��� */
	String SID_Bank_RenewPaymentCancel = "20";
	/**�ͻ���Լ**/
	String SID_Bank_UserDismiss="21";
	/**�ͻ�ǩԼ**/
	String SID_Bank_UserSigned="22";
	
	//add by fzg 20121122
	/**����������ѯ*/
	String SID_Bank_ContBatQuery = "23";
	
	/**���в�ѯ������ʷ�䶯*/
	String SID_Bank_QueryContChange = "19";
	/**����Ͷ������ӡ*/
	String SID_Bank_PrintAppCont = "27";
	/**���к��������д�ӡ����*/
	String SID_Bank_PrintCont = "29";
	/**��ѯ(����)����*/
	String SID_Bank_QueryPrem = "28";
	/**�����ؿպ˶�*/
	String SID_Bank_CardControl = "30";
	/**���ڽɷ�*/
	String SID_Bank_RenewalPay = "32";
	/**ȡ�����ڽɷ�*/
	String SID_Bank_CancleRenewalPay = "33";
	/**�����޸ı���������Ϣ*/
	String SID_Bank_QueryContModify = "34";
	/**���в�ѯ�ͻ�����*/
	String SID_Bank_QueryCont = "35";
	/**���в�ѯ�������飨���գ�*/
	String SID_Bank_QueryContDetail = "36";
	/**���л�ȡ���������ѯ*/
	String SID_Bank_GetContList = "37";
	/**���л�ȡ��������ȡ��*/
	String SID_Bank_GetContList2 = "38";
	/**���в�ѯ���չ�˾Ѳ��Ա��Ϣ*/
	String SID_Bank_QueryInsStaff = "39";
	/**����ʧ�㱣�ղ�Ʒ*/
	String SID_Bank_CalculateCont = "40";
	/**����ʧ�㱣�ղ�Ʒ�����գ�*/
	String SID_Bank_CalculateCont2 = "63";
	/** ���������������˱�����*/
	String SID_Bank_CheckSur = "41";
	/** ��ũ����������ѯ*/
	String SID_NewContQuery= "31";
	

	
	
	/** ��ũ�б�����ѯ*/
	String SID_NewAbcContQuery= "45";
	/** ��ũ�б�ȫ����״̬��ѯ*/
	String SID_SecureStatusQuery= "46";
	/** ��ũ���˱��̳����˽���ļ�*/
	String SID_NoTakenBalanceRst= "48";
	/** ��ũ�з�ʵʱ�������˽���ļ�*/
	String SID_NonRealTimeContRst= "49";
	/** ��ũ�з�ʵʱ��������*/
	String SID_NonRealTimeContBlc= "20";
	/** ��ũ���ֹ�������������ϸ*/
	String SID_HandContRst= "50";
	/** ��ũ�з�ʵʱ/�ֹ�������������ϸ*/
	String SID_NonRealTimeContRiskDtl= "51";
	/** ��ũ���˱��̳����˽���ļ�--���л���*/
	String SID_NoTakenBlcBankRst= "54";
	/** ��ũ�з�ʵʱ�������˽���ļ�--���л���*/
	String SID_NonRealTimeContBankRst= "55";
	/**��ũ�б�ȫ��ѯ*/
	String SID_SecureApply="";
	/**��ũ�б�ȫ����*/
	String  SID_SecureQuery="";
	/**��ũ�б��������ѯ*/
	String SID_PolDetailQuery="62";
	
	
	
	/** �½����˱�����*/
	String SID_CCBApplyReturnCont= "52";
	/** �½����˱�ȷ��*/
	String SID_CCBReturnCont= "53";
	
	/** �½��д������ۺ����Ѳ�ѯ*/
	String SID_ActInsuSaleRemindQuery= "58";
	/** �½��д������ۺ�����ȡ��*/
	String SID_ActInsuSaleRemindAccess= "59";
	/** �½�������Ǽ�̨��*/
	String SID_SignPolicyFormat= "60";
	/** �½���ȷ�ϵǼ�̨��*/
	String SID_SignConfirm= "61";
	
	/*�½��з�ʵʱ*/
	/**���з�ʵʱͶ������**/
	/**���и��·�ʵʱҵ��״̬**/
	String SID_UpdateServiceStatus="64";
	/**���в�ѯ��ʵʱ�ɷ���Ϣ**/
	String SID_QueryPaymentInfo="65";
	/**���л�ȡ��ʵʱ�˱�״̬**/
	
	/** ��ũ�б�ȫ����*/
	String SID_BQContBlc= "16";
	
	/** -������- */
	int TranCom_NULL = 1;
	/** �й��������� */
	String TranCom_ICBC = "01";
	/** �й����� */
	int TranCom_BOC = 2;
	/** �й��������� */
	int TranCom_CCB = 3;
	/** �й�ũҵ���� */
	int TranCom_ABC = 4;
	/** �й���ͨ���� */
	int TranCom_BCM = 5;
	/** �й������������� */
	int TranCom_PSBC = 6;
	
}
