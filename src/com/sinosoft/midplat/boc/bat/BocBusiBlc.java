/**
 * 中行日终对账
 */

package com.sinosoft.midplat.boc.bat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BocBusiBlc extends Balance {
	public BocBusiBlc() {
		super(BocConf.newInstance(), "1105");
	}
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "RZDZ"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into BocBusiBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
        int count=0;
        long  prem=0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			tLineMsg = tLineMsg.trim();
			if(tLineMsg.length()<60)
			{
				cLogger.warn("汇总行，直接跳过，继续下一条！");
				continue;
			}
			if(tLineMsg.length()>60){
				if("".equals(tLineMsg) || tLineMsg == null || tLineMsg.endsWith("W")){
					continue;
				}
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tLineMsg.substring(0, 16));
				Element tNodeNoEle = new Element("AgentCom");
				tNodeNoEle.setText(tLineMsg.substring(20, 25));
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tLineMsg.substring(25, 33));
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tLineMsg.substring(tLineMsg.length()-46, tLineMsg.length()-16).trim());
				Element tPremEle = new Element(Prem);
				tPremEle.setText(NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-16, tLineMsg.length()-3).trim())+"");
				cLogger.info("交易流水号："+tLineMsg.substring(0,16));
				cLogger.info("网点："+tLineMsg.substring(20,25));
				cLogger.info("交易日期："+tLineMsg.substring(25,33));
				cLogger.info("保单号："+tLineMsg.substring(tLineMsg.length()-46, tLineMsg.length()-16).trim());
				cLogger.info("保费："+tLineMsg.substring(tLineMsg.length()-16, tLineMsg.length()-3).trim());
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tNodeNoEle);
				tDetailEle.addContent(tTranNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tPremEle);
				mBodyEle.addContent(tDetailEle);
				count++;
				prem+=NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-15, tLineMsg.length()-3).trim());
			}
		}
		cLogger.info("总笔数:"+count);
		cLogger.info("总金额:"+prem);
		mCountEle.setText(String.valueOf(count));
		mPremEle.setText(String.valueOf(prem));
		mBufReader.close();	//关闭流 
		cLogger.info("Out BocBusiBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.BocBusiBlc.main");
		mLogger.info("中国银行新单对账程序开始...");
		
		BocBusiBlc mBatch = new BocBusiBlc();
		
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
		
		mLogger.info("中国银行新单对账成功结束！");
	}
}
