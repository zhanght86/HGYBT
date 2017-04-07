package com.sinosoft.midplat.gzbank.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.gzbank.GZBankConf;


public class GZContBlc extends Balance {

	public GZContBlc() {
		super(GZBankConf.newInstance(), "9005");
	}

	@Override
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu") + ""
				+ DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into GZContBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		/**
		 * 交易状态(2位)+交易日期(8位)+签到银行代码(10位)+储蓄所代码(10)+交易码(7位)
		 * +银行流水号(30位)+保单号(20位)+金额(12位，带小数点)+ 销售渠道（2位）为01
		 */
		int sumCount = 0;
		long sumPrem = 0;
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
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
			//明细节点
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
		mBufReader.close(); 
		cLogger.info("Out GZContBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.gzbank.bat.GZContBlc.main");
		mLogger.info("贵州银行新单对账程序开始...");
		
		GZContBlc mBatch = new GZContBlc();
		
		//用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。
			 * 4位年：4位[0-9]的数字。
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
		
		mLogger.info("贵州银行新单对账成功结束！");
	}
}
