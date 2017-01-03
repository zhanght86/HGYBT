package com.sinosoft.midplat.common;

public interface AblifeCodeDef extends CodeDef {
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
	/** 银保续期缴费查询 */
	String SID_Bank_RenewPaymentQuery = "9";
	/** 银保犹豫期退保查询 */
	String SID_Bank_NoTakenQuery = "10";
	/** 银保犹豫期退保 */
	String SID_Bank_NoTaken = "11";
	/** 银保犹豫期退保冲正*/
	String SID_Bank_NoTakenCancel = "12";
	/** 银保退保查询 */
	String SID_Bank_TakenQuery = "13";
	/** 银保退保 */
	String SID_Bank_Taken = "14";
	/** 银保退保冲正*/
	String SID_Bank_TakenCancel = "15";
	/** 银保慢满期给付查询 */
	String SID_Bank_ManPaymentQuery = "16";
	/** 银保慢满期给付 */
	String SID_Bank_ManPayment = "17";
	/** 银保慢满期给付冲正 */
	String SID_Bank_ManPaymentCancel = "18";
	/** 银保续期缴费 */
	String SID_Bank_RenewPayment = "19";
	/** 银保续期缴费冲正 */
	String SID_Bank_RenewPaymentCancel = "20";
	/**客户解约**/
	String SID_Bank_UserDismiss="21";
	/**客户签约**/
	String SID_Bank_UserSigned="22";
	
	//add by fzg 20121122
	/**建行批量查询*/
	String SID_Bank_ContBatQuery = "23";
	
	/**建行查询保单历史变动*/
	String SID_Bank_QueryContChange = "19";
	/**建行投保单打印*/
	String SID_Bank_PrintAppCont = "27";
	/**建行和宁波银行打印保单*/
	String SID_Bank_PrintCont = "29";
	/**查询(续期)保费*/
	String SID_Bank_QueryPrem = "28";
	/**建行重空核对*/
	String SID_Bank_CardControl = "30";
	/**续期缴费*/
	String SID_Bank_RenewalPay = "32";
	/**取消续期缴费*/
	String SID_Bank_CancleRenewalPay = "33";
	/**建行修改保单基本信息*/
	String SID_Bank_QueryContModify = "34";
	/**建行查询客户保单*/
	String SID_Bank_QueryCont = "35";
	/**建行查询保单详情（寿险）*/
	String SID_Bank_QueryContDetail = "36";
	/**建行获取保单详情查询*/
	String SID_Bank_GetContList = "37";
	/**建行获取保单详情取数*/
	String SID_Bank_GetContList2 = "38";
	/**建行查询保险公司巡逻员信息*/
	String SID_Bank_QueryInsStaff = "39";
	/**建行失算保险产品*/
	String SID_Bank_CalculateCont = "40";
	/**建行失算保险产品（寿险）*/
	String SID_Bank_CalculateCont2 = "63";
	/** 宁波银行银保核退保交易*/
	String SID_Bank_CheckSur = "41";
	/** 新农行试算结果查询*/
	String SID_NewContQuery= "31";
	

	
	
	/** 新农行保单查询*/
	String SID_NewAbcContQuery= "45";
	/** 新农行保全申请状态查询*/
	String SID_SecureStatusQuery= "46";
	/** 新农行退保犹撤对账结果文件*/
	String SID_NoTakenBalanceRst= "48";
	/** 新农行非实时出单对账结果文件*/
	String SID_NonRealTimeContRst= "49";
	/** 新农行非实时出单对账*/
	String SID_NonRealTimeContBlc= "20";
	/** 新农行手工单出单险种明细*/
	String SID_HandContRst= "50";
	/** 新农行非实时/手工单出单险种明细*/
	String SID_NonRealTimeContRiskDtl= "51";
	/** 新农行退保犹撤对账结果文件--银行回盘*/
	String SID_NoTakenBlcBankRst= "54";
	/** 新农行非实时出单对账结果文件--银行回盘*/
	String SID_NonRealTimeContBankRst= "55";
	/**新农行保全查询*/
	String SID_SecureApply="";
	/**新农行保全申请*/
	String  SID_SecureQuery="";
	/**新农行保单详情查询*/
	String SID_PolDetailQuery="62";
	
	
	
	/** 新建行退保申请*/
	String SID_CCBApplyReturnCont= "52";
	/** 新建行退保确认*/
	String SID_CCBReturnCont= "53";
	
	/** 新建行代理保险售后提醒查询*/
	String SID_ActInsuSaleRemindQuery= "58";
	/** 新建行代理保险售后提醒取数*/
	String SID_ActInsuSaleRemindAccess= "59";
	/** 新建行申请登记台账*/
	String SID_SignPolicyFormat= "60";
	/** 新建行确认登记台账*/
	String SID_SignConfirm= "61";
	
	/*新建行非实时*/
	/**建行非实时投保申请**/
	/**建行更新非实时业务状态**/
	String SID_UpdateServiceStatus="64";
	/**建行查询非实时缴费信息**/
	String SID_QueryPaymentInfo="65";
	/**建行获取非实时核保状态**/
	
	/** 新农行保全对账*/
	String SID_BQContBlc= "16";
	
	/** -保留域- */
	int TranCom_NULL = 1;
	/** 中国工商银行 */
	String TranCom_ICBC = "01";
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
