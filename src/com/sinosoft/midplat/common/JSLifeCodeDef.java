package com.sinosoft.midplat.common;

public interface JSLifeCodeDef extends CodeDef {
	/** 银保普通出单网点 */
	int NodeType_Bank_Sale = 0;
	/** 银保新单对账网点 */
	int NodeType_Bank_Bat = 1;
	/** 银保退保信息传递网点 */
	int NodeType_Bank_EdorCTInfo = 2;
	
	/** 银保保单 */
	int ContType_Bank = 0;
	 
	/** 录单 */
	int ContState_Input = 1;
	/** 新单取消 */
	int ContState_RollBack = -1;
	/** 签单 */
	int ContState_Sign = 2;
	/** 当日撤单 */
	int ContState_Cancel = 3;

	/** 对账一致 */
	int ContState_BlcOK = 10;
	/** 对账问题单 */
	int ContState_BlcERROR = 11;
	
	/** 银保录单核保 */
	String SID_Bank_ContInput = "0";
	/** 银保收费签单 */
	String SID_Bank_ContConfirm = "1";
	/** 银保新单回滚 */
	String SID_Bank_ContRollback = "2";
	/** 银保保单重打 */
	String SID_Bank_ContRePrint = "3";
	/** 银保保单查询 */
	String SID_Bank_ContQuery = "4";
	/** 银保当日撤单 */
	String SID_Bank_ContCancel = "5";
	/** 银保新单日结 */
	String SID_Bank_NewContBlc = "6";
	/** 单证(保险单)对账 */
	String SID_Bank_ContCardBlc = "7";
	/** 银保退保信息传递 */
	String SID_Bank_EdorCTInfo = "8";
	
	/** -保留域- */
	int TranCom_NULL = 1;
	/** 中国工商银行 */
	int TranCom_ICBC = 1;
	/** 中国银行 */
	int TranCom_BOC = 2;
	/** 中国建设银行 */
	int TranCom_CCB = 3;
	/** 中国农业银行 */
	int TranCom_ABC = 4;
	/** 中国交通银行 */
	int TranCom_BCM = 5;
	/** 中国邮政储蓄银行 */
	int TranCom_PSBC = 6;
}
