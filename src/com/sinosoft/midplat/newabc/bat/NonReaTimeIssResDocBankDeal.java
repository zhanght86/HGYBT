package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;

public class NonReaTimeIssResDocBankDeal extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	@SuppressWarnings("unused")
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;

	public void run() {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.run()...");
		try {
			cConfigEle = BatUtils.getConfigEle("2007"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "FRESULT.BANK" + mComCode + "." + cCurDate; // 初始化文件名称POLICY3002
			// //3002公司编号 + 当前时间
			if (!new BatUtils()
					.downLoadFile(mFIleName, "02", "2007", cCurDate)) {
				cTranLogDB.setRCode("1");
				cTranLogDB.setRText("非实时出单结果文件-银行处理结果回盘文件下载失败");
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
				throw new MidplatException("非实时出单结果文件-银行处理结果回盘文件下载失败");
			}
			// 处理对账
			cLogger.info("处理非实时出单结果文件-银行处理结果回盘开始...");
			// 得到请求标准报文
			cTranLogDB = insertTranLog();
			/*String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "C:\\Users\\chenjinwei\\Desktop\\BQAPPLY1132.20161130";
			Document mInStd = parse(myFilePath);
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc("").call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("BaseInfo")
					.getChildText("ResultCode");
			String reMsg = cOutXmlDoc.getRootElement().getChild("BaseInfo")
					.getChildText("ResultMsg");
			cLogger.info("保全对账结果代码："+reCode+"      结果说明："+reMsg);*/
			cTranLogDB.setRCode("0");
			cTranLogDB.setRText("交易成功");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  处理异常...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("交易失败");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("处理非实时出单结果文件-银行处理结果回盘结束...");
		cLogger.info("Out onReaTimeIssResDocBankDeal.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NonReaTimeIssResDocBankDeal.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2007");
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
		this.cLogger.debug("Out NonReaTimeIssResDocBankDeal.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.parse()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..." + mCurrDate);

		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			tBalanceFlag = "1";
		}

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("NodeNo"));

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));

		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);

		// 报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(NewAbcConf.newInstance().getConf().getRootElement().getChildText("BankCode"));

		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mBankCode).addContent(mTranCom)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mBalanceFlag);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		
		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		
		// 格式：保险公司代码|银行代码|总记录数|总金额|
		// 文件其他内容：（明细记录）
		// 保险公司代码|申请日期|试算申请顺序号|投保人姓名|投保人证件类型|投保人证件号码|险种编码|产品编码|保单号|承保日期|
		// 投被保人关系|被保人姓名|被保人证件类型|被保人证件号码|保费|保额|缴费账户|缴费方式|缴费期限|保单到期日|投保分数|
		// 个性化费率|保单印刷号|错误码|错误信息|备注1|备注2|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// 把成功的记录独取出来发给核心
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{
			cLogger.info(tLineMsg);

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!"3103".equals(tSubMsgs[0]))
			{// 如果回盘正确，首行为记录；错误的时候第二行为错误信息

				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}
				
				Element tDetailEle = new Element(Detail);

				//设置批处理执行日期为交易日期
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				tDetailEle.addContent(tTranDateEle);

				// 保单号
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[8]);
				tDetailEle.addContent(tContNoEle);
				
				//试算申请顺序号
				Element tApplyNo = new Element("ApplyNo");
				tApplyNo.setText(tSubMsgs[2]);
				tDetailEle.addContent(tApplyNo);

				//保费
				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[14]);
				tPremEle.setText(String.valueOf(tPremFen));
				tDetailEle.addContent(tPremEle);
				
				//投保人姓名 
				Element appntName = new Element("AppntName");
				appntName.setText(tSubMsgs[3]);
				tDetailEle.addContent(appntName);
				
				//被保人姓名 
				Element insuredName = new Element("InsuredName");
				insuredName.setText(tSubMsgs[11]);
				tDetailEle.addContent(insuredName);
				
				//返回码
				Element dealCode = new Element("State");
				dealCode.setText(tSubMsgs[23]);
				tDetailEle.addContent(dealCode);
				
				//返回信息
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[24]);
				tDetailEle.addContent(msg);
				
				mBodyEle.addContent(tDetailEle);
			}
			else
			{
				mCountEle.setText("0");
				mSumPremEle.setText("0");
			}

		}
		mTranData.addContent(mBodyEle);
		mBufReader.close(); // 关闭流

		cLogger.info("Out NonReaTimeIssResDocBankDeal.parse()!");
		return new Document(mTranData);
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonReaTimeIssResDocBankDeal.main");
		mLogger.info("新农行非实时出单结果文件-银行处理结果回盘对账程序启动...");

		NonReaTimeIssResDocBankDeal abcAES = new NonReaTimeIssResDocBankDeal();

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
		mLogger.info("新农行非实时出单结果文件-银行处理结果回盘对账程序完成!");
	}

}
