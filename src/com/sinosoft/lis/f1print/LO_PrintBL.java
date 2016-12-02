package com.sinosoft.lis.f1print;


import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;

/**
 * <p>
 * ClassName: LO_PrintBL
 * </p>
 * <p>
 * Description: 银保通每日实时数据导出表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: AXA_DB
 * @CreateDate：2011-07-26
 * @ReWriteDate:
 */

public class LO_PrintBL extends CreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sTranCom = "";
	
	private String sManageCom = "";

	private String sAgentCom = "";

	private String sAgentCode = "";

	private String sRiskCode = "";

//	private String sStartDay = "";
//
//	private String sEndDay = "";
	
	private String sDay = "";

	private String filePath = "";

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public LO_PrintBL() {

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

		this.sManageCom = (String) mTransferData.getValueByName("ManageCodeNo");
		this.sTranCom = (String) mTransferData.getValueByName("TranCom");
		this.sAgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.sAgentCode = (String) mTransferData.getValueByName("AgentCode");
		this.sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.sDay = (String) mTransferData.getValueByName("Day");
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
		String[] Sheet = { "银保通每日实时数据导出表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "18", "18","18", "18", "18", "18", "18", "18", "18","18",
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "20", "18", "18", "18", "18", "18", "18", "20", 
				"18", "18", "18", "18", "18", "18", "20", "18", "18", "18",
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18" 
				  };

		// 设置标题
		String[] title = { "银保通每日实时数据导出表" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.LEFT);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 69, 0);
		setBorderLineStyle(1, 0, BorderLineStyle.THIN);
		setMergeCells(0, 1, 69, 1);
		setContent(1, 0, "购买日期:" + this.sDay);
		setCellFont(1, 0, ExcelFont.ARIAL);
		setFontSize(1, 0, 12);
		setFontBold(1, 0, ExcelFont.No_Bold);
		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// 设置列头
		String[] head = { "保单编号", "保单状态","对账状态", "网点代码", "网点编号", "网点名称", "购买日期",
				"产品代码", "代理人编号", "被保险人", "被保人性别", "被保人出生日期", "被保人身份证号",
				"被保人其他证件号", "被保人邮编", "被保人地址", "被保人电话", "被保人手机", "被保人职业内容","投保人与被保人关系",
				"投保人", "投保人性别", "投保人出生日期", "投保人身份证号", "投保人其他证号", "投保人邮编",
				"投保人地址", "投保人电话", "投保人手机",
				"受益人1姓名", "受益人1性别", "受益人1出生日期","受益人1证件号码", "受益人1受益顺序", "受益人1受益比例","受益人1与被保人关系",
				"受益人2姓名", "受益人2性别", "受益人2出生日期","受益人2证件号码", "受益人2受益顺序", "受益人2受益比例","受益人2与被保人关系",
				"受益人3姓名", "受益人3性别", "受益人3出生日期","受益人3证件号码", "受益人3受益顺序", "受益人3受益比例","受益人3与被保人关系",
				"产品名称", "份数", "保额", "保险年期", "缴费方式",
				"交费年限", "主险基本保险费", "期交追加保险费", "趸交追加保险费",
				"附加险产品名称", "附加险份数", "附加险保额", "附加险保险费",
				"保险费合计",
				"投资账户", "投资账户比例", "投资日期", "转账帐号", "账户所有人", "下载时间" };
		for (int i = 0; i < 70; i++) {
			setContent(2, i, head[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		sSql += "SELECT TRIM(C.CONTNO) 保单编号,"
				+ "   CASE WHEN c.appflag = 'B' or c.appflag = '1' THEN  '保单生效' WHEN c.appflag = '0' AND (TRIM(c.state) = 'C' or TRIM(c.state) = 'B') THEN  '协议终止'  ELSE  '--' END 保单状态,"
				+ "   CASE WHEN c.balancestate = 'Y' THEN  '已对账' WHEN c.balancestate = 'N' THEN  '未对账'  ELSE  '--' END 对账状态,"
				+ "   TRIM(C.AXANODEMAP) 网点代码,"
				+ "   TRIM(C.AXAAGENTCOM) 网点编号,"
				+ "   C.AGENTCOMNAME 网点名称,"
				+ "   to_char(c.makedate, 'YYYY-mm-dd') || ' ' || c.maketime 购买日期,"
				+ "   p.riskalias 产品代码,"
				+ "   TRIM(C.AXAAGENTCODE) 代理人编号,"
				+ ""
				+ "   i.name 被保险人,"
				+ "   CASE  WHEN i.sex = 'M' THEN   '男'  WHEN i.sex = 'F' THEN  '女'  ELSE  '其他'  END 被保人性别,"
				+ "   to_char(i.birthday, 'yyyy-mm-dd') 被保人出生日期,"
				+ "   CASE  WHEN i.idtype = '0' THEN i.idno  ELSE  '' END 被保人身份证号,"
				+ "   CASE  WHEN i.idtype != '0' THEN  i.idno  ELSE  '' END 被保人其他证件号,"
				+ "   d.zipcode 被保人邮编,"
				+ "   d.postaladdress 被保人地址,"
				+ "   d.homephone 被保人电话,"
				+ "   d.mobile 被保人手机,"       
				+ "   CASE  WHEN  i.occupationcode ='' THEN  i.occupationcode ELSE '&' END 被保人职业内容,"
				+ "   CASE  WHEN  AP.PLURALITYTYPE ='1' THEN  '配偶' WHEN  AP.PLURALITYTYPE ='2' THEN  '父母'  WHEN  AP.PLURALITYTYPE ='3' THEN  '子女'  WHEN  AP.PLURALITYTYPE ='4' THEN  '祖父祖母'  WHEN  AP.PLURALITYTYPE ='5' THEN  '孙子女' WHEN  AP.PLURALITYTYPE ='6' THEN  '兄弟姐妹'  WHEN  AP.PLURALITYTYPE ='7' THEN  '其他亲属'  WHEN  AP.PLURALITYTYPE ='8' THEN  '本人' WHEN  AP.PLURALITYTYPE ='9' THEN  '朋友'   WHEN  AP.PLURALITYTYPE ='99' THEN  '其他'  ELSE '&' END 投保人与被保险关系,"
				+ ""
				+ "   ap.appntname 被保险人,"
				+ "   CASE  WHEN ap.appntsex = 'M' THEN   '男'  WHEN ap.appntsex = 'F' THEN  '女'  ELSE  '其他'  END 被保人性别,"
				+ "   to_char(ap.appntbirthday, 'yyyy-mm-dd') 被保人出生日期,"
				+ "   CASE  WHEN ap.idtype = '0' THEN ap.idno  ELSE  '' END 被保人身份证号,"
				+ "   CASE  WHEN ap.idtype != '0' THEN  ap.idno  ELSE  '' END 被保人其他证件号,"
				+ "   d2.zipcode 被保人邮编,"
				+ "   d2.postaladdress 被保人地址,"
				+ "   d2.homephone 被保人电话,"
				+ "   d2.mobile 被保人手机,"
				+ ""
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1姓名,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '男'  WHEN B1.SEX = 'F' THEN  '女'  ELSE  '其他'  END 被保人性别  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1性别,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1出生日期,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1证件号码,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1受益顺序,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') 受益人1收益比例,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '配偶' WHEN  B1.RELATIONTOINSURED ='2' THEN  '父母'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '子女'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '祖父祖母'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '孙子女' WHEN  B1.RELATIONTOINSURED ='6' THEN  '兄弟姐妹'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '其他亲属'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '本人' WHEN  B1.RELATIONTOINSURED ='9' THEN  '朋友'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '其他'  ELSE '&' END 投保人与被保险关系  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=1) 受益人1与被保人关系,"
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1姓名,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '男'  WHEN B1.SEX = 'F' THEN  '女'  ELSE  '其他'  END 被保人性别  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1性别,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1出生日期,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1证件号码,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1受益顺序,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') 受益人1收益比例,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '配偶' WHEN  B1.RELATIONTOINSURED ='2' THEN  '父母'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '子女'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '祖父祖母'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '孙子女' WHEN  B1.RELATIONTOINSURED ='6' THEN  '兄弟姐妹'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '其他亲属'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '本人' WHEN  B1.RELATIONTOINSURED ='9' THEN  '朋友'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '其他'  ELSE '&' END 投保人与被保险关系  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=2) 受益人1与被保人关系,"
				
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1姓名,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '男'  WHEN B1.SEX = 'F' THEN  '女'  ELSE  '其他'  END 被保人性别  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1性别,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1出生日期,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1证件号码,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1受益顺序,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') 受益人1收益比例,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '配偶' WHEN  B1.RELATIONTOINSURED ='2' THEN  '父母'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '子女'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '祖父祖母'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '孙子女' WHEN  B1.RELATIONTOINSURED ='6' THEN  '兄弟姐妹'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '其他亲属'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '本人' WHEN  B1.RELATIONTOINSURED ='9' THEN  '朋友'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '其他'  ELSE '&' END 投保人与被保险关系  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=3) 受益人1与被保人关系,"
				
				
				
				+ "  (select r.riskname FROM lmriskapp r where p.riskcode = r.riskcode) 产品名称,"
				+ "  CASE WHEN p.mult = '0.00000' THEN  '' WHEN p.mult != '0.00000' THEN  to_char(p.mult)  ELSE  '--' END 份数, "
				+ "  CASE WHEN p.amnt = '0.00' THEN  '' WHEN p.amnt != '0.00' THEN  to_char(p.amnt) END 保额,"
				+ "   case when p.insuyearflag ='5' then '终身' ELSE  TO_CHAR(p.insuyear) END 保险年期,"
				+ "   CASE  WHEN  p.payintv = '1' THEN   '年缴'  WHEN  p.payintv = '2' THEN   '月缴'  WHEN  p.payintv = '3' THEN   '半年缴'  WHEN  p.payintv = '4' THEN   '季缴'  WHEN  p.payintv = '5' THEN   '趸缴'   WHEN  p.payintv = '6' THEN   '不定期缴'    ELSE  '其他'  END  缴费方式,"
				+ "   CASE WHEN p.PAYENDYEARFLAG ='6' THEN '终身' WHEN p.PAYENDYEARFLAG = '5' THEN '趸交' ELSE to_char(p.payendyear) END 缴费年期,"
				+ "   to_char(p.prem) 主险基本保险费,"
				+ "   to_char(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END 定期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0))  期交追加保险费,"
				+ "   to_char((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END 首期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual)) 趸交追加保险费,"
				
				+ "  (select r.riskname FROM lmriskapp r, lcpol sp1 where sp1.riskcode = r.riskcode and sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) 附加险产品名称,"
				+ "  (select CASE WHEN sp1.mult = '0.00000' THEN  '' WHEN sp1.mult != '0.00000' THEN  to_char(sp1.mult)  ELSE  '--' END from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) 附加险份数,"
				+ "  (select CASE WHEN sp1.amnt = '0.00' THEN  '' WHEN sp1.amnt != '0.00' THEN  to_char(sp1.amnt)  ELSE  '--' END from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) 附加险保额,"
				+ "  (select to_char(sp1.prem) from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) 附加险保险费,"
			
				+ "  to_char(c.prem) 合计保险费,"
				+ "	(select wmsys.wm_concat(ac.acccode) from lcinsureacc ac where ac.contno = c.contno)  投资账户,"
				+ "  (select wmsys.wm_concat(acc.accrate) from lcinsureacc acc where acc.contno = c.contno )  投资比例,"
				+ "  (select  CASE  WHEN accc.acctimeflag = '1' THEN  '投保次日'  WHEN accc.acctimeflag = '2' THEN  '犹豫期后'  ELSE  '' END 投资日期 from lcinsureacc accc where accc.contno = c.contno and accc.insuaccno='1') 投资日期,"
				+ "   c.bankaccno 转账账号,"
				+ "   c.accname 账户所有人,"
				+ "   to_char(c.downdate, 'yyyy-mm-dd')||' '||c.downtime 下载时间"
				+ "	 from lccont c, lcpol p, lcinsured i, lcaddress d,lcaddress d2, lcappnt ap"
				+ "	 where c.contno = p.contno"
				+ "		and p.riskcode = p.kindcode"
				+ "		and c.contno = i.contno"
				+ "		and i.insuredno = d.customerno"
				+ "		and i.addressno = d.addressno"
				+ "		and c.contno = ap.contno"
				+ "  	and ap.appntno = d2.customerno"
				+ "		and ap.addressno = d2.addressno"
				+ "		and c.norealflag = 'N'"
				+ "		and ((c.appflag = 'B' AND C.UWFLAG = '9') OR (C.APPFLAG = '0' AND C.STATE='C') OR (C.APPFLAG = '0' AND C.STATE='B') OR ( C.APPFLAG = '1'))";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += " and c.managecom like '" + sManageCom + "%' ";
		}

		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sDay != null && !"".equals(sDay)) {
			sSql += "  AND p.MAKEDATE = DATE'" + sDay + "' ";
		}


		sSql +=   " union"
			+ " select '合计:共'||count(C.CONTNO)||'条','','','','','','','','','','',"
			+ " '','','','','','','','','','','','','','','','','','',"
			+ " null,null,null,null,null,null,null,  null,null,null,null,null,null,null,  null,null,null,null,null,null,null, '',null,"
			+ " to_char(sum(p.amnt)),"
			+ " null,"
			+ " '',null,"
			+ " to_char(sum(p.prem)),"
			+ " to_char(sum(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END 定期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0))),"
			+ " to_char(sum((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END 首期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual))), "
			+ " null,null,"
			+ " to_char(sum((select sp1.amnt from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')))),"
			+ " to_char(sum((select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')))),"  
			+ " to_char(sum(c.prem)),"
			+ " '','','',"
			+ " '','',''"
			+ "	 from lccont c, lcpol p, lcinsured i, lcaddress d,lcaddress d2, lcappnt ap"
			+ "	 where c.contno = p.contno"
			+ "		and p.riskcode = p.kindcode"
			+ "		and c.contno = i.contno"
			+ "		and i.insuredno = d.customerno"
			+ "		and i.addressno = d.addressno"
			+ "		and c.contno = ap.contno"
			+ "  	and ap.appntno = d2.customerno"
			+ "		and ap.addressno = d2.addressno"
			+ "		and c.norealflag = 'N'"
				+ "		and ((c.appflag = 'B' AND C.UWFLAG = '9') OR (C.APPFLAG = '0' AND C.STATE='C') OR (C.APPFLAG = '0' AND C.STATE='B') OR ( C.APPFLAG = '1'))";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += " and c.managecom like '" + sManageCom + "%' ";
		}
		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sDay != null && !"".equals(sDay)) {
			sSql += "  AND p.MAKEDATE = DATE'" + sDay + "' ";
		}


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
		// 准备数据容器信息
		TransferData tTransferData = new TransferData();
		String filePath = "E:/bb.xls";

		String sArea = "";
		String sCity = "";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sDay = "2012-03-01";

		System.out
				.println("LO:客户正在提取每日实时数据，查询条件为：1-地区,2-城市,3-银行渠道,4-网点,5-网点专员,6-险种代码,7-开始日期,8-结束日期");
		System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-" + sArea + ",2-" + sCity
				+ ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode
				+ ",6-" + sRiskCode + ",7-" + sDay + "");
		System.out.println("LO:客户正在提取每日实时数据，文件路径为：" + filePath);

		tTransferData.setNameAndValue("Area", sArea);
		tTransferData.setNameAndValue("City", sCity);
		tTransferData.setNameAndValue("TranCom", sTranCom);
		tTransferData.setNameAndValue("AgentCom", sAgentCom);
		tTransferData.setNameAndValue("AgentCode", sAgentCode);
		tTransferData.setNameAndValue("RiskCode", sRiskCode);
		tTransferData.setNameAndValue("Day", sDay);
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		String Content = "";
		String FlagStr = "";
		LO_PrintBL tLO_PrintBL = new LO_PrintBL();
		if (!tLO_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tLO_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tLO_PrintBL.getResult().get(0);
			System.out.println(Content);
		}
	}
}