package com.sinosoft.midplat.gzbank.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.gzbank.GZBankConf;
import com.sinosoft.midplat.service.Service;


public class GZContBlc extends Balance {

	public GZContBlc() {
		super(GZBankConf.newInstance(), "9005");
	}

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu") + ""
				+ DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".txt";
	}

	public void run() {
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		this.cLogger.info("Into GZContBlc.run()...");

		this.cResultMsg = null;
		try {
			this.cMidplatRoot = MidplatConf.newInstance().getConf()
					.getRootElement();
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(
					this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag
							+ "']"));

			String nextDate = this.cThisBusiConf.getChildText("nextDate");

			if (this.cTranDate == null)
				if ((nextDate != null) && ("Y".equals(nextDate))) {
					this.cTranDate = new Date();
					this.cTranDate = new Date(
							this.cTranDate.getTime() - 86400000L);
				} else {
					this.cTranDate = new Date();
				}
			Element tTranData = new Element("TranData");
			Document tInStdXml = new Document(tTranData);

			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			try {
				String ttFileName = getFileName();
				this.cLogger.info("FileName = " + ttFileName);
				String ttLocalDir = this.cThisBusiConf
						.getChildTextTrim("localDir");
				this.cLogger.info("localDir = " + ttLocalDir);
				InputStream ttBatIns = null;

				ttBatIns = new FileInputStream(ttLocalDir + ttFileName);

				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			} catch (Exception ex) {
				this.cLogger.error("生成标准对账报文出错!", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr)) {
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("业务处理模块" + tServiceClassName);
			Constructor tServiceConstructor = Class.forName(tServiceClassName)
					.getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor
					.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head")
					.getChildText("Desc");

		} catch (Throwable ex) {
			this.cLogger.error("交易出错", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out GZContBlc.run()!");
	}

	protected Element parse(InputStream pBatIs) throws Exception {

		cLogger.info("Into GZContBlc.parse()...");

		int sumCount = 0;
		long sumPrem = 0;

		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}

		// 文件每行字段值以半角竖线“|”分隔，每行末尾也以“|”为结束标识
		// 首记录：日期（yyyymmdd）|总笔数|总金额（以元为单位，2位小数）|
		// 明细记录：日期（yyyymmdd）|签单交易流水号|保单号|金额（以元为单位，2位小数）|

		System.out.println(pBatIs);
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		// 把成功的记录独取出来发给核心
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			// cLogger.info("这是个么东西："+tLineMsg);

			// 空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!tSubMsgs[0].equals("01") || !tSubMsgs[4].equals("9000103")) {
				continue;
			}

			// 上海农商银行对账文件里是没有网点代码的，核心的要求是每单必须有出单网点
			String nodeNo = tSubMsgs[3];

			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);

			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);

			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[6]);

			Element tAgentCom = new Element(AgentCom);
			tAgentCom.setText(nodeNo);

			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[7]);
			tPremEle.setText(String.valueOf(tPremFen));

			sumPrem += tPremFen;
			sumCount ++;

			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);

			mBodyEle.addContent(tDetailEle);
		}
		mCountEle.setText(sumCount+"");
		mSumPremEle.setText(sumPrem+"");
		mBufReader.close(); // 关闭流n(mSumPrem)+"、对账总数：" +
							// String.valueOf(mCount));

		cLogger.info("Out GZContBlc.parse()!");
		return mBodyEle;
	}

	protected Element getHead() {
		cLogger.info("Into GZContBlc.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();

		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());

		// 报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("9");

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		// 报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);

		cLogger.info("Out GZContBlc.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.Srcb.bat.GZContBlc.main");
		mLogger.info("程序开始...");

		GZContBlc mBatch = new GZContBlc();
		mBatch.run();
		// File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
		// InputStream in = new FileInputStream(file);
		// Element mBodyEle = mBatch.parse(in);
		// JdomUtil.print(mBodyEle);
		mLogger.info("成功结束！");
	}

}
