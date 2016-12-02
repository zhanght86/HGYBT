package com.sinosoft.midplat.common;

public interface JSLifeCodeDef extends CodeDef {
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
	/** �µ�ȡ�� */
	int ContState_RollBack = -1;
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
	
	/** -������- */
	int TranCom_NULL = 1;
	/** �й��������� */
	int TranCom_ICBC = 1;
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
