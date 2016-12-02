package com.sinosoft.lis.citireport;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PremPoundageDetail extends CreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sStartDay = "";

	private String sEndDay = "";
	
	private String filePath = "";

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public PremPoundageDetail() {

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

		this.sStartDay = (String) mTransferData.getValueByName("StartDay");
		this.sEndDay = (String) mTransferData.getValueByName("EndDay");
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
	 * 
	 * @author JiaoYF
	 * @return boolean
	 */
	private boolean getPrintData() {

		// 设置Excel文件输出的路径
		setFilePath(filePath);
		// 设置Excel的Sheet
		String[] Sheet = { "保费佣金明细表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15",
				"15", "15"};

		// 设置标题
		String[] title = { "保费佣金明细表" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);

System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 12, 0);
		setBorderLineStyle(1, 0, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 12, 1);
		//setContent(1, 0, "购买日期:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 12);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// 设置列头
		String[] head = { "保单号", "险种代码","保费类型", "交易日期", "银行帐号", "交易代码", "交易金额",
				"佣金率", "佣金", "导出日期", "佣金说明", "保单年度"};
		for (int i = 0; i < head.length; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}
		sSql += "SELECT PolicyNo 保单号,"
				+ "   PlanCode 险种代码,"
				+ "   premiumType 保费类型,"
				+ "   date8to10(TransactionDate) 交易日期,"
				+ "   accountNo 银行帐号,"
				+ "   transactionCode 交易代码,"
				+ "   CASE WHEN af.premiumtype='T' Then TransactionAmount*(-1) else TransactionAmount end 交易金额,"
				+ "   CASE WHEN CommissionLevel = 0 THEN  null ELSE  CommissionLevel END 佣金率,"
				+ "   CASE WHEN Commission = 0  THEN  null ELSE  Commission END 佣金,"
				+ "   date8to10(extracted) 导出日期,"
				+ "   TransactionDetial 佣金说明,"				
				+ "   policyYear 保单年度"
				
				+ "	 from AXAPolicyTransaction af "
				
				+ "	 where af.extracted between "+DateUtil.date10to8(this.sStartDay)
				+ "	 and  "+ DateUtil.date10to8(this.sEndDay);
		
		tSSRS = exeSql.execSQL(sSql);
		
		
		setTitle(title);
		String[][] print = tSSRS.getAllData();		
//		String[][] print = new String[14][14];
		
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
			}
		}
		
			

		int mergeColumns = 1;
		setColorFlag("LO");
		setData(0, print, 2, 0);
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
				mResult.add(gettFile());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
}