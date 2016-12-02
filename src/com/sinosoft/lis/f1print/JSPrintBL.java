package com.sinosoft.lis.f1print;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.print.MatrixPrint;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelBorderLineStyle;
import com.sinosoft.lis.excel.ExcelFont;

/**
 * <p>
 * ClassName: FeePrintBL
 * </p>
 * <p>
 * Description: 代理业务达成率在线打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: BASS
 * @CreateDate：2009-09
 * @ReWriteDate:
 */

public class JSPrintBL extends CreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();



	private String ManageCom = ""; 

	private String BankCode = ""; 

	private String AgentCom = ""; 

	private String RiskCode = ""; 

	private String StartDate = ""; 
	
	private String EndDate = ""; 
	
	private String filePath = ""; 

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public JSPrintBL() {

	}

	/**
	 * 返回错误
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 打印
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		
		this.ManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.BankCode = (String) mTransferData.getValueByName("BankCode");
		this.AgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.RiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.StartDate = (String) mTransferData.getValueByName("StartDate");
		this.EndDate = (String) mTransferData.getValueByName("EndDate");
		this.filePath = (String) mTransferData.getValueByName("filePath");

		return true;
	}

	/**
	 * 返回结果集
	 * 
	 * @return: VData 程序处理结果
	 */
	public VData getResult() {
		return mResult;
	}
	

	
/**
 * 打印数据
 * @author JiaoYF
 * @return boolean
 */
private boolean getPrintData() {
	setFilePath(filePath);
	
	String[] Sheet = { "金胜报表" };
	setSheet(Sheet);
	ExeSQL exeSql = new ExeSQL();
	String feesql = "";
	
			
			String[] colSize = {  "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18","18", "18", "18","18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18" };
			//设置标题
			
			setMergeCells(0, 0, 42, 0);
			setTitleBold(0, ExcelFont.Bold);
			setTitleFont(0, ExcelFont.ARIAL);
			setTitleFontSize(0, 12);
			setTitleAlign(0, ExcelAlignment.LEFT);
			setTitleBorder(0, ExcelBorder.ALL);
			
			setMergeCells(0,1,42,1);
			setContent(1,0,"购买日期:"+PubFun.getCurrentDate());
			setCellFont(1,0,ExcelFont.ARIAL);
			setFontSize(1,0,12);
			setFontBold(1,0,ExcelFont.Bold);
			setFontAlign(1,0,ExcelAlignment.LEFT);
			
			//设置列头
			String[] head = { "保单编号","保单状态","网点代码", "网点编号","网点名称","购买日期", "产品代码",
					"代理人编号", "被保险人", "被保人性别","被保人出生日期", "被保人身份证号","被保人其他证件号","被保人邮编", "被保人地址", "被保人电话", "被保人手机", "被保人职业内容", "投保人", "投保人性别", "投保人出生日期", "投保人身份证号", "投保人其他证号", "投保人邮编", "投保人地址", "投保人电话", "投保人手机", "产品名称", "保额", "保险年期", "缴费方式", "交费年限", "主险基本保险费", "期交追加保险费", "趸交追加保险费", "附加险保险费", "保险费合计", "投资账户", "投资账户比例", "投资日期", "转账帐号", "账户所有人", "下载时间"};
			for(int i = 0; i < 43; i++){
				setContent(2, i, head[i]);
				setCellFont(2, i, ExcelFont.ARIAL);
				setFontSize(2, i, 10);
				setFontBold(2, i, ExcelFont.Bold);
				setFontAlign(2, i, ExcelAlignment.CENTRE);
			}
			feesql += "select trim(c.contno) 保单编号, "
       +" CASE"
       +" WHEN c.appflag = 'B' or c.appflag = '1' THEN"
       +"   '保单生效'"
       +"  WHEN c.appflag = '0' AND (c.state = 'C' or c.state = 'B') THEN "
       +"   '协议终止' "
       +" ELSE "
       +"  '--' "
       +" END 保单状态, "
       +" (select '0' || n.trancom || '-' || n.zoneno || '-' || n.nodeno "
       +"    from nodemap n "
       +"   where trim(c.agentcom) = n.agentcom "
       +"     and trim(c.bankcode) = '0' || n.trancom) 网点代码, "
       +" trim(c.agentcom) 网点编号, "
       +" (select a.agentcomname "
       +"   from agent a "
       +"   where a.agentcom = trim(c.agentcom)) 网点名称, "
       +" to_char(c.makedate, 'YYYY-mm-dd') || ' ' || c.maketime 购买日期, "
       +" p.riskcode 产品代码, "
       +" trim(c.agentcode) 代理人编号, "
       +" i.name 被保险人, "
       +"  CASE "
       +"   WHEN i.sex = '0' THEN "
       +"    '男' "
       +"   WHEN i.sex = '1' THEN "
       +"   '女' "
       +"  ELSE "
       +"    '其他' "
       +" END 被保人性别, "
       +" to_char(i.birthday, 'yyyy-mm-dd') 被保人出生日期, "
       +" CASE "
       +"   WHEN i.idtype = '0' THEN "
       +"    i.idno "
       +"   ELSE "
       +"   '' "
       +" END 被保人身份证号, "
       +"  CASE "
       +"   WHEN i.idtype != '0' THEN "
       +"   i.idno "
       +"  ELSE "
       +"    '' "
       +" END 被保人其他证件号, "
       +" d.zipcode 被保人邮编, "
       +" d.postaladdress 被保人地址, "
       +" d.homephone 被保人电话, "
       +" d.phone 被保人手机, "
       +" i.occupationcode 被保人职业内容, "
       +" (select r.riskname FROM lmriskapp r where p.riskcode = r.riskcode) 产品名称, "
       +"  p.amnt 保额, "
       +" p.insuyear 保险年期, "
       +" p.payendyear 缴费年期, "
       +" p.prem 主险基本保险费, "
       +" p.riskprem 期交追加保险费, "
       +" p.firstaddprem 趸交追加保险费, "
       +" p.standprem 附加保险费, "
       +" p.sumprem 合计保险费, "
       +" c.bankaccno 转账账号, "
       +" c.bankaccno 账户所有人, "
       +" to_char(sysdate, 'yyyy-mm-dd hh:mm:ss') 下载时间 "
       +" from lccont c, lcpol p, lcinsured i, lcaddress d "
       +" where c.contno = p.contno "
       +" and p.riskcode = p.kindcode "
       +" and c.contno = i.contno "
       +" and i.insuredno = d.customerno "
       +" and i.addressno = d.addressno "
       +" and c.managecom like '8603%' "
       +" and c.bankcode = '012' "
       +" and c.agentcom = '2949' "
       +" and p.riskcode = 'PAC' "
       +" AND P.MAKEDATE > DATE '2011-01-01' "
       +" AND P.MAKEDATE < DATE '2011-11-01' ";

			String[] title = { "银保通每日实时数据导出表" };
			setTitle(title);
			tSSRS = exeSql.execSQL(feesql);
			
			String[][] print = tSSRS.getAllData();
			int mergeColumns=1;
//			setDateByFixArrays(0, print, 4, 0, mergeColumns,"合计;全国");
			setData(0, print, 3, 0);
			setColSize(0, colSize, 0);
	
	try {
		if (!createExcel()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = "@GMRSelGatherBL020@";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult.clear();
			mResult.add(filePath);
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return true;
	}
}