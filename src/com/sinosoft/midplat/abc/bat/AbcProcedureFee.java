package com.sinosoft.midplat.abc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.abc.AbcConf;

public class AbcProcedureFee extends Balance {
	public AbcProcedureFee() {
		super(AbcConf.newInstance(), "9");
	}

	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		GregorianCalendar mInsuEndCalendar = new GregorianCalendar();
		mInsuEndCalendar.setTime(cTranDate);
		mInsuEndCalendar.add(GregorianCalendar.DAY_OF_MONTH, 0);
		return "YBTCX" + mBankEle.getAttributeValue("insu")
				//+ DateUtil.getDateStr(mInsuEndCalendar, "yyyyMMdd") + "_RTN";
		+ DateUtil.getDateStr(mInsuEndCalendar, "yyyyMMdd");
	}

	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into AbcProcedureFee.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mPremEle.setText("0");
		Element mFileName = new Element("FileName");
		mFileName.setText(getFileName());
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
		mBodyEle.addContent(mFileName);

		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info(tLineMsg);

			// 空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}

			mCount++;
		}
		mCountEle.setText(String.valueOf(mCount));

		mBufReader.close(); // 关闭流

		cLogger.info("Out AbcProcedureFee.parse()!");
		return mBodyEle;
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.abc.bat.AbcProcedureFee.main");
		mLogger.info("程序开始...");

		AbcProcedureFee mBatch = new AbcProcedureFee();

		// 用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}\\d{2}\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
