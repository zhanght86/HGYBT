/**
 * 日结对帐文件格式（非实时核保）
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class ToIcbcNoRealBlc extends Balance {
	public ToIcbcNoRealBlc() {
		super(IcbcConf.newInstance(), "107");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		
		return "ENYIAAS"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into ToIcbcNoRealBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		
		
		String sFtpIP = cThisBusiConf.getChildText("FtpIP");
		String sFtpPort = cThisBusiConf.getChildText("FtpPort");
		String sFtpUser = cThisBusiConf.getChildText("FtpUser");
		String sFtpPass = cThisBusiConf.getChildText("FtpPass");
		String sFtpFilePath = cThisBusiConf.getChildText("FtpFilePath");
		String sFilePath = cThisBusiConf.getChildText("FilePath");
		String sFileName = getFileName();
		
		Element tFilePath = new Element("FilePath");
		Element tFileName = new Element("FileName");
		Element tFtpIP = new Element("FtpIP");
		Element tFtpPort = new Element("FtpPort");
		Element tFtpUser = new Element("FtpUser");
		Element tFtpPass = new Element("FtpPass");
		Element tFtpFilePath = new Element("FtpFilePath");
		
		tFtpIP.setText(sFtpIP);
		tFtpPort.setText(sFtpPort);
		tFtpUser.setText(sFtpUser);
		tFtpPass.setText(sFtpPass);
		tFtpFilePath.setText(sFtpFilePath);
		tFileName.setText(sFileName);
		tFilePath.setText(sFilePath);
		
		mBodyEle.addContent(tFtpIP);
		mBodyEle.addContent(tFtpPort);
		mBodyEle.addContent(tFtpUser);
		mBodyEle.addContent(tFtpPass);
		mBodyEle.addContent(tFtpFilePath);
		mBodyEle.addContent(tFileName);
		mBodyEle.addContent(tFilePath);
		mBufReader.close();	//关闭流 
		
		cLogger.info("Out ToIcbcNoRealBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.ToIcbcNoRealBlc.main");
		mLogger.info("程序开始...");
		
		ToIcbcNoRealBlc mBatch = new ToIcbcNoRealBlc();
		mBatch.setDate(new Date());
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
		
		mLogger.info("成功结束！");
	}
}
