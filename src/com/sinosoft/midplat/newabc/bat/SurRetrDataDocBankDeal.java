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

public class SurRetrDataDocBankDeal extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());
	private TranLogDB cTranLogDB;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	@SuppressWarnings("unused")
	private Document cOutXmlDoc;

	public void run() {
		cLogger.info("Into SurRetrDataDocBankDeal.run()...");
		try {
			cConfigEle = BatUtils.getConfigEle("2006"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "INVALID.BANK" + mComCode + "." + cCurDate; // 初始化文件名称POLICY3002
			// //3002公司编号 + 当前时间
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2006", cCurDate)) {
				throw new MidplatException("退保犹撤数据文件-银行处理结果回盘文件下载失败");
			}
			// 处理对账
			cLogger.info("退保犹撤数据文件-银行处理结果回盘开始...");
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
			cTranLogDB.setRText("成功");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  回传交易处理异常...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("处理退保犹撤数据文件-银行处理结果回盘结束!");
		cLogger.info("Out SurRetrDataDocBankDeal.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into SurRetrDataDocBankDeal.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2006");
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
		this.cLogger.debug("Out SurRetrDataDocBankDeal.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into SurRetrDataDocBankDeal.parse()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element mTranData = new Element("TranData");
		Date cTranDate = new Date();
		String tBalanceFlag = "0";
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
		
		// 格式：保险公司代码|银行代码|总记录数|总金额|犹撤总记录数|犹撤总金额|满期总记录数|满期总金额|退保总记录数|退保总金额
		// 文件其他内容：（明细记录）
		// 公司编码|业务发生日期|保单号|投保人名称|投保人证件类型|投保人证件号码|保费|接受文件日期|业务类别|受理渠道|
		// 银行处理流水号|保险公司受理状态|银行端保单状态|错误码|错误信息|备注1|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String mHeadMsgs = mBufReader.readLine();
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		if (!"3103".equals(mHeadMsgs.substring(0, 4)))
		{// 如果回盘正确，首行为记录；错误的时候为错误信息
			cLogger.info(mHeadMsgs.substring(0, 4));
			String[] tHeadMsgs = mHeadMsgs.split("\\|", -1);
			cLogger.info(tHeadMsgs);
			mCountEle.setText(tHeadMsgs[2].trim());
			mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(tHeadMsgs[3].trim())));

			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
				cLogger.info(tLineMsg);

				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}

				String[] tSubMsgs = tLineMsg.split("\\|", -1);

				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[1]);
				//
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[10]);

				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[2]);

				Element tAppntName = new Element("AppntName");
				tAppntName.setText(tSubMsgs[3]);

				//处理结果
				Element tHandleState = new Element("State");		
				if ("240000".equals(tSubMsgs[13]))
				{// 银行受理成功
					tHandleState.setText("0");
				}
				else
				{// 银行受理失败
					tHandleState.setText("1");
				}
			
				//处理结果描述
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[14]);

				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[6]);
				tPremEle.setText(String.valueOf(tPremFen));

				Element tBusiType = new Element("BusiType");
				if ("01".equals(tSubMsgs[8]))
				{// 犹撤
					tBusiType.setText("07");
				}
				else if ("02".equals(tSubMsgs[8]))
				{// 满期
					tBusiType.setText("09");
				}
				else if ("03".equals(tSubMsgs[8]))
				{// 退保
					tBusiType.setText("11");
				}

				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tBusiType);
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tAppntName);
				tDetailEle.addContent(tTranNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tHandleState);
				tDetailEle.addContent(tPremEle);

				mBodyEle.addContent(tDetailEle);
			}
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // 关闭流
		}
		else
		{
			mCountEle.setText("0");
			mSumPremEle.setText("0");
		}
		cLogger.info("Out SurRetrDataDocBankDeal.parse()!");
		return new Document(mTranData);
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.SurRetrDataDocBankDeal.main");
		mLogger.info("新农行退保犹撤数据文件-银行处理结果回盘对账程序启动...");

		SurRetrDataDocBankDeal abcAES = new SurRetrDataDocBankDeal();

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
				throw new MidplatException("日期格式有误，应为yyyy-MM-dd！" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("新农行退保犹撤数据文件-银行处理结果回盘对账程序完成!");
	}

}
