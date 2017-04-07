package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class SecuTradAppDoc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;
	String cAppType = null;// 48-自助渠道

	public void run() {
		cLogger.info("Into SecuTradAppDoc.run()...");
		try {

			cConfigEle = BatUtils.getConfigEle("2003"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "BQAPPLY" + mCorNo + "." + cCurDate; // 初始化文件名称POLICY3002
			// //3002公司编号 + 当前时间
			// // 组织Document
			 if(!new BatUtils().downLoadFile(mFIleName,"02","2003",cCurDate)){
				 throw new MidplatException("保全交易申请对账文件下载失败!");
			 }
			// 处理对账
			cLogger.info("处理保全交易申请对账文件开始...");
			// 得到请求标准报文
			//
			cTranLogDB = insertTranLog();
			 String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/BQAPPLY010079.20170405";
			Document mInStd = parse(myFilePath, "YBT");

			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("保全对账结果代码：" + reCode + "      结果说明：" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			// =========================保全网银渠道对账=========================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseWY(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("保全对账结果代码：" + reCode + "      结果说明：" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================保全网银渠道对账结束=========================
			// =========================保全自助终端渠道对账=========================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseZD(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("保全对账结果代码：" + reCode + "      结果说明：" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================保全自助终端渠道对账结束=========================
			// =========================保全手机银行渠道对账============================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseSJ(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("保全对账结果代码：" + reCode + "      结果说明：" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================保全手机银行渠道对账结束===========================
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  对账处理异常...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();

		} finally {
			cCurDate = "";
		}
		cLogger.info("处理保全交易申请对账文件结束!");
		cLogger.info("Out SecuTradAppDoc.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into SecuTradAppDoc.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2003");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
		mTranLogDB.setTranDate(DateUtil.getCur8Date());
		mTranLogDB.setTranTime(DateUtil.getCur6Time());
		Date mCurDate = new Date();
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(0);
		mTranLogDB.setUsedTime(0);
		mTranLogDB.setBak1("");
		mTranLogDB.setRCode("-1");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!(mTranLogDB.insert())) {
			this.cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		this.cLogger.debug("Out SecuTradAppDoc.insertTranLog()!");
		return mTranLogDB;
	}

	// 柜面渠道保全对账
	protected Document parse(String nFileURL, String channel) throws Exception {
		cLogger.info("Into SecuTradAppDoc.parse()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		Date cTranDate = new Date();
		cLogger.info(" 对账日期为..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..."+mCurrDate);
		
		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		//交易日期
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//交易时间
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		//交易机构代码(银行/农信社/经代公司)
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		//银行代码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		//地区代码
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		//银行网点
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("node"));
		
		//柜员代码
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		//交易流水号
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		//交易类型
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mTranCom).addContent(mBankCode)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mBalanceFlag);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		// 格式：保险公司代码|银行代码|总记录数|总金额|犹撤总记录数|犹撤总金额|满期总记录数|满期总金额|退保总记录数|退保总金额|
		//文件其他内容：（明细记录）
		// 公司编码|业务类别|交易日期|保单号|证件类型|证件号码|申请人名称|保费|申请状态|省市代码|网点号|投保单号|领取金额|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
//			if (!"01".equals(tSubMsgs[10])) {
//				cLogger.warn("非承保保单，直接跳过，继续下一条！");
//				continue;
//			}
//			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
//				cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
//				continue;
//			}
			
			/*
			 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
			 *    
			 * 联调的时候和农行的人员确认的，20130403
			 * 
			 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|保单状态
			 */
			
			//业务类别
			Element tBusiType = new Element("BusiType");
			if("01".equals(tSubMsgs[1])){//犹撤
				tBusiType.setText("07");
			}
			if("02".equals(tSubMsgs[1])){//满期
				tBusiType.setText("09");
			}
			if("03".equals(tSubMsgs[1])){//退保
				tBusiType.setText("11");
			}
			
			//交易日期
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[2]);
			
			//保单号
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[3]);
			
			//申请人姓名
			Element tAppntNameEle=new Element("AppntName");
			
			//投保人证件类型
			Element tIDTypeEle=new Element(IDType);
			
			//证件号码
			Element tIDNoEle=new Element(IDNo);
			
			//投保单号
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[11]);
			
			//金额
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[12]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			//省代码
			Element tZoneNo=new Element("ZoneNo");
			tZoneNo.setText(tSubMsgs[9].trim());
			
			//网点代码
			String nodeNo=null;
			if(tSubMsgs[9]!=null&&tSubMsgs[10]!=null){
				nodeNo=tSubMsgs[9].trim()+tSubMsgs[10].trim();
			}
			Element tNodeNo=new Element("NodeNo");
			tNodeNo.setText(nodeNo);
			
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tBusiType);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tAppntNameEle);
			tDetailEle.addContent(tIDTypeEle);
			tDetailEle.addContent(tIDNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tZoneNo);
			tDetailEle.addContent(tNodeNo);
			
			tDetailEle.addContent(tAgentCom);
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//关闭流

		mTranData.addContent(mBodyEle);
		// TransData.addContent(getHead());
		cLogger.info("Out SecuTradAppDoc.parse()!");
		return new Document(mTranData);
	}

	// 网银渠道保全对账
	protected Document parseWY(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		// Operator.setText("YBT");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // 从第一行读取
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("41");// 网银渠道
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);
		// 从第二行读取
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("正在读取文件###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// 根据保单号查询保全申请方式
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("48")
					|| cAppType.equals("65")) {// 21-柜面;41-网银;48-自助终端;65-手机银行;
				cLogger.warn("网银渠道保全对账，柜面、自助终端、手机银行渠道数据直接跳过，继续下一条！");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
				break;
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // 根据保单号查询保全流水号
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // 保费
			cLogger.info("金额" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // 保全状态
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // 银行代码
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// 北京
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// 河南
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 格式化设置
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	// 自助终端渠道保全对账
	protected Document parseZD(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // 从第一行读取
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("48");// 自助终端渠道
		channel = "ABCWEB";
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);

		// 从第二行读取
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("正在读取文件###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// 根据保单号查询保全申请方式
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("41")
					|| cAppType.equals("65")) {// 21-柜面;41-网银;48-自助终端;65-手机银行
				cLogger.warn("自助终端渠道保全对账，柜面、网银渠道、手机银行数据直接跳过，继续下一条！");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
				break;
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // 根据保单号查询保全流水号
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // 保费
			cLogger.info("金额" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // 保全状态
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // 银行代码
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// 北京
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// 河南
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 格式化设置
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	// 手机银行渠道保全对账
	protected Document parseSJ(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // 从第一行读取
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("65");// 手机银行渠道
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);

		// 从第二行读取
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("正在读取文件###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// 根据保单号查询保全申请方式
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("41")
					|| cAppType.equals("48")) {// 21-柜面;41-网银;48-自助终端;65-手机银行
				cLogger.warn("手机银行渠道保全对账，柜面、网银、自助终端渠道数据直接跳过，继续下一条！");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
				break;
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // 根据保单号查询保全流水号
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-犹退,020011-退保
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // 犹退
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // 满期给付
			} else if (ywlb.equals("03")) { // 退保
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // 保费
			cLogger.info("金额" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // 保全状态
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // 银行代码
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("查询SQL语句：" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// 北京
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// 河南
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 格式化设置
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.SecuTradAppDoc.main");
		mLogger.info("新农行保全交易申请文件对账程序启动...");

		SecuTradAppDoc abcAES = new SecuTradAppDoc();

		// 用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("新农行保全交易申请文件对账程序完成!");
	}

}
