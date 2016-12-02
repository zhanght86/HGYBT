package com.sinosoft.lis.citireport;

import jxl.format.BorderLineStyle;

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

public class ChannelAchieve extends ChannelCreateExcel {

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

	public ChannelAchieve() {

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
System.out.println(mGlobalInput);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
System.out.println("mTransferData:"+mTransferData);
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
		String[] Sheet = { "业绩和佣金报表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15",
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15"};

		// 设置标题
		String[] title = { "渠道业绩报表" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 42, 0);
		setBorderLineStyle(1, 0, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 42, 1);
		//setContent(1, 0, "购买日期:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 12);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// 设置列头
		String[] head = { "区域", "所属城市","银行名称", "网点名称", "网点编号", "FA姓名", "FA编号",
				"保单号码", "投保日期", "核保通过日", "生效日期", "客户帐号", "投保单号",
				"投保人", "投保人证件号", "被保险人", "被保人证件号", "产品代码", "产品名称","缴费方式",
				"缴费期", "性质", "保单年度", "基本保费", "额外定投保费", "首次追加保费",
				"追加保费（非首期）", "总保费", "NBI",
				"保额", "佣金", "保单状态","回执日", "账户1名称", "账户额度","账户2名称",
				"账户额度", "账户3名称", "账户额度","Total Fund Value", "数据导出日期"};
		for (int i = 0; i < head.length; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
		 	setFontAlign(1, i, ExcelAlignment.CENTRE);
		    setBorderLineStyle(1, i, BorderLineStyle.THIN);
		    
		}
		sSql += "SELECT '' 区域,"
				+ "   (select INSU_CODE from bankandinsumap where codetype='rlsCode' and bak4 = substr(PolicyNo,1,3) ) 所属城市,"//根据保单号的前三位做的Mapping
				+ "   '花旗银行' 银行名称,"//没有数据源，待处理···
				+ "   (select distinct(p.agentname) from policymaster p where p.policyno=pm.policyno and p.extracteddate=pm.extracted) 网点名称,"
				+ "   (select distinct(p.agentno) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 网点编号,"
				+ "   '' FA姓名,"//没有数据源，待处理···
				+ "   '' FA编号,"//没有数据源，待处理···
				+ "   pm.PolicyNo 保单号码,"
				+ "   date8to10((select distinct(p.ApplicationDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) 投保日期,"
				+ "   date8to10((select distinct(p.PolicyApprovedDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) 核保通过日,"
				+ "   date8to10((select distinct(p.PolicyContractDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) 生效日期,"
				+ "   (select asm.APPLICANTACCTNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) 客户帐号,"
				+ "   (select asm.APPLICATIONNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) 投保单号,"
				+ "   ''  投保人,"//没有数据源，置空，
				+ "   (select asm.APPLICANTIDNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) 投保人证件号,"
				+ "   (select distinct(p.InsuredName) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 被保险人,"
				+ "   (select distinct(p.InsuredIDNo) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 被保人证件号,"
				+ "   pm.PlanCode 产品代码,"       
				+ "   (select codename from ldcode ld where ld.codetype='citi_procode' and ld.code=pm.plancode) 产品名称,"//产品代码和产品名称的映射需要在LDCDOOE里做处理，
				+ "   (select distinct(p.PayMode) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)  缴费方式,"
				+ "   (select distinct(p.premiumterm) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 缴费期,"
				+ "   pm.transactiondetial 性质,"
				+ "   pm.policyyear  保单年度,"
				+ "   pm.modalregularpremium 基本保费,"
				+ "   pm.RegularTopUpPremium 额外定投保费,"
				+ "   pm.InitLumpSumPremium 首次追加保费,"
				+ "   pm.LumpSumPremium 追加保费非首期,"
				+ "   (pm.modalregularpremium+pm.RegularTopUpPremium+pm.InitLumpSumPremium+pm.LumpSumPremium) 总保费,"//上面四个和。
				+ "   (pm.modalregularpremium+(pm.RegularTopUpPremium+pm.InitLumpSumPremium+pm.LumpSumPremium)*0.1)  NBI,"//基本保费+基本保费后面三项和乘以0.1的积，
				+ "   ( select distinct(p.SumInsured) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 保额,"
				+ "  (select sum(ap.Commission) from AxaPolicyTransaction ap where ap.transactionseqno = pm.transactionseqno) 佣金,"
				+ "  (select distinct(p.PolicyStatus) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) 保单状态,"
				+ "  Case when ((select max(p.ReplyTargetDate) from policymaster p where p.policyno = pm.policyno)='0') then '' Else (date8to10((select max(p.ReplyTargetDate) from policymaster p where p.policyno = pm.policyno))) end 回执日,"
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U1ZY')  账户1名称,"//这里处理比较复杂，稍后处理。。。。
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U1ZY') 账户1额度,"//保单号--找到投资账户，到FundSummary日期最大的不同账户对应的TotalValue
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U2WJ')  账户2名称,"
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U2WJ') 账户2额度,"
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U3AX') 账户3名称,"
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U3AX') 账户3额度,"
				+ "  (select sum(tt.totalvalue) from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno) TotalFundValue,"			
				+ "  date8to10(pm.Extracted) 数据导出日期"
				
//				+ "	 from Axapolicytransaction pm"//policytransaction主表查，Ldcode映射查性质
//				+ "	 where pm.premiumtype='T'" 
//				+ "  and pm.policyno in (select p.policyno from policymaster p where p.policyapproveddate between "+DateUtil.date10to8(this.sStartDay)
//				+ "  and  "+DateUtil.date10to8(this.sEndDay)+")";	
				
				+ "	 from AXAPolicyTransaction pm,"
				+ "  (select distinct transactionseqno seqno, transactionsubseqno subseqno"
				+ "  from AXAPolicyTransaction"
				+ "  where commission != 0 and extracted between "+DateUtil.date10to8(this.sStartDay)
				+ " and "+DateUtil.date10to8(this.sEndDay)+") b"
				
				+ " where pm.transactionseqno = b.seqno "
                + " and pm.transactionsubseqno = b.subseqno " 
                + " and pm.premiumtype='T'";
		
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();

		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
			}	
		}

		int mergeColumns = 1;
		setData(0, print, 2, 0);
		setColSize(0, colSize, 0);
		

		
//		jxl.write.NumberFormat number  = new jxl.write.NumberFormat("#.##"); 
		
		
		//设置单元格数值型
//		setDataColNumer(4,"0");//网点
//		setDataColNumer(20,"defaut");
		setDataColNumer(22,"0");//保单年度
		
//		setDataColNumer(23,"defaut");
//		setDataColNumer(24,"defaut");
//		setDataColNumer(25,"defaut");
//		setDataColNumer(26,"defaut");
//		setDataColNumer(27,"defaut");
//		setDataColNumer(28,"defaut");
//		setDataColNumer(29,"defaut");
//		setDataColNumer(30,"defaut");
//		setDataColNumer(34,"defaut");
//		setDataColNumer(36,"defaut");
//		setDataColNumer(38,"defaut");
//		setDataColNumer(39,"defaut");

		//设置单元格日期格式
//		setDataColNumer(8,"yyyy/mm/dd");
			
		
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
		System.out.println("你妹啊，老湿不能编译··");
	}
}