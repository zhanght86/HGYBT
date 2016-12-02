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

public class PoundageDetail extends CreatExcel {

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

	public PoundageDetail() {

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
//System.out.println("this.sStartDay:"+this.sStartDay);
//System.out.println("this.sEndDay:"+this.sEndDay);
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
		String[] Sheet = { "手续费明细表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15",
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15"};

		// 设置标题
		String[] title = { "手续费明细表" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 24, 0);
		setBorderLineStyle(1, 24, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 24, 1);
		//setContent(1, 0, "购买日期:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 12);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// 设置列头
		String[] head1 = { "#", "DOA","Client #", " Total Premium", "Commission", "Commission", "Cap.Date",
				"Product", "Owner", "Outlet", "Sales1", "Sales2", "City",
				"Insurer", "Payment Mode", "Payment Term", "Basic Premium", "RTU", "TP-LS(1st time)","TP-LS(non-1st time)",
				"Eff.Date", "Remark", "Policy #", "Insured"};
		for (int i = 0; i < head1.length; i++) {
			setContent(1, i, head1[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}
		
		String[] head2 = { "", "收件日期","客户编号", "总保费", "佣金（主险）", "佣金（投连）", "投保日期",
				"产品名称", "投保人", "支行名称", "销售人员1", "销售人员2", "所属城市",
				"保险公司名称", "缴费方式", "缴费期", "基本保费", "额外定投保费", "首次追加保费","追加保费（非首期）",
				"生效日期", "性质", "保单号码", "被保险人"};
		for (int i = 0; i < head2.length; i++) {
			setContent(2, i, head2[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		
//System.out.println("Head2"+head2.length);	
		
		sSql += "SELECT ''  序号,"//这玩意也要置空，下面查询后处理
				+ "   ''  收件日期,"//这个值要置空，和Tonny确认过的
				+ "   (select am.CustomerNo from axaMissingInfo am where am.policyno = a.PolicyNo) 客户编号,"
				+ "   a.TransactionAmount 总保费,"
				+ "   case when a.premiumtype in ('I', 'R', 'E') then a.commission else 0 end  佣金主险,"
				+ "   case when a.premiumtype='V' then a.commission else 0 end 佣金投连,"
				+ "   date8to10((select distinct(p.ApplicationDate) from policymaster p where p.policyno=a.policyno)) 投保日期,"
				+ "   (select codename from ldcode ld where ld.codetype='citi_procode' and ld.code=a.plancode) 产品名称,"//需要映射，目前没有做，在LDCODE里处理
				+ "   '' 投保人,"//这个玩意要置空，没有数据源，下面查询后处理
				+ "   (select distinct(p.AgentName) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) 支行名称,"
				+ "   '' 销售人员1,"
				+ "   '' 销售人员2,"
				+ "   '' 所属城市,"//此上三个数据项的值目前还没取到，以后处理。
				+ "   'AXA' 保险公司名称,"
				+ "   (select distinct(p.paymentmethod) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) 缴费方式,"
				+ "   (select distinct(p.premiumterm) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) 缴费期,"
				+ "   (select distinct(p.ModalPremium) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) 基本保费,"
				+ "   a.RegularTopUpPremium  额外定投保费,"       
				+ "   a.InitLumpSumPremium 首次追加保费,"
				+ "   a.LumpSumPremium 追加保费非首期,"
				+ "   date8to10((select distinct(p.PolicyContractDate) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted)) 生效日期,"
				+ "   a.transactiondetial 性质,"//这玩意需要映射，目前还没有处理
				+ "   a.PolicyNo 保单号码,"
				+ "   (select distinct(p.InsuredName) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) 被保险人"

				+ "	 from AxaPolicyTransaction a"		
				+ "	 where a.commission!=0.0 and a.extracted between "+DateUtil.date10to8(this.sStartDay)
				+ "	 and  "+DateUtil.date10to8(this.sEndDay);
//System.out.println("sSql:"+sSql);
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();

//		String[][] print = new String[25][25];
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
				print[i][0]=new String("")+(i+1);//第一行计数用1,2,3···
		
			}
		}

		int mergeColumns = 1;
		setColorFlag("LO");
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
				mResult.add(gettFile());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}