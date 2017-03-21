/*
 * 中行犹豫期退保对账
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
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.boc.bat.BocTBBusiBlc;

public class BocTBBusiBlc extends Balance {
	public BocTBBusiBlc() {
		super(BocConf.newInstance(), "801");
	}
	
	/**
	 * 获取核心上传的犹豫期退保对账文件名
	 */
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		//读取文件
		System.out.println("读取核心上传的犹豫期退保对账文件");
		return "YYQTB"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	
	/**
	 * 
	 */
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into BocTBBusiBlc.parse()...");
		
		//获取当前交易配置文件根节点下charset子节点文本
		String mCharset = cThisBusiConf.getChildText(charset);
		//charset子节点文本为空、空字符串
		if (null==mCharset || "".equals(mCharset)) {
			//charset子节点文本置为GBK
			mCharset = "GBK";
		}
		
		//新建使用GBK字符集的字节流通向字符流的桥梁
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		//新建Body节点
		Element mBodyEle = new Element(Body);
		
		//中行FTPIP
		String sFtpIP = cThisBusiConf.getChildText("FtpIP");
		//中行FTP端口
		String sFtpPort = cThisBusiConf.getChildText("FtpPort");
		//中行FTP用户名
		String sFtpUser = cThisBusiConf.getChildText("FtpUser");
		//中行FTP密码
		String sFtpPass = cThisBusiConf.getChildText("FtpPass");
		//中行FTP路径
		String sFtpFilePath = cThisBusiConf.getChildText("FtpFilePath");
		//放置在中行文件地址
		String sFilePath = cThisBusiConf.getChildText("FilePath");
		//核心上传文件地址[文件名]
		String sFileName = getFileName();
		
		//放置在中行文件地址节点
		Element tFilePath = new Element("FilePath");
		//核心上传文件地址[文件名]节点
		Element tFileName = new Element("FileName");
		//中行FTPIP节点
		Element tFtpIP = new Element("FtpIP");
		//中行FTP端口节点
		Element tFtpPort = new Element("FtpPort");
		//中行FTP用户名节点
		Element tFtpUser = new Element("FtpUser");
		//中行FTP密码节点
		Element tFtpPass = new Element("FtpPass");
		//中行FTP路径节点
		Element tFtpFilePath = new Element("FtpFilePath");
		
		//设置文本内容为中行FTPIP
		tFtpIP.setText(sFtpIP);
		//设置文本内容为中行FTP端口
		tFtpPort.setText(sFtpPort);
		//设置文本内容为中行FTP用户名
		tFtpUser.setText(sFtpUser);
		//设置文本内容为中行FTP密码
		tFtpPass.setText(sFtpPass);
		//设置文本内容为中行FTP路径
		tFtpFilePath.setText(sFtpFilePath);
		//设置文本内容为核心上传文件地址[文件名]
		tFileName.setText(sFileName);
		//设置文本内容为放置在中行文件地址
		tFilePath.setText(sFilePath);
		
		/**
		 * Body节点加入中行FTPIP节点、中行FTP端口节点、中行FTP用户名节点、中行FTP密码节点、
		 * 中行FTP路径节点、核心上传文件地址[文件名]节点、放置在中行文件地址节点
		 **/
		mBodyEle.addContent(tFtpIP);
		mBodyEle.addContent(tFtpPort);
		mBodyEle.addContent(tFtpUser);
		mBodyEle.addContent(tFtpPass);
		mBodyEle.addContent(tFtpFilePath);
		mBodyEle.addContent(tFileName);
		mBodyEle.addContent(tFilePath);
		mBufReader.close();	//关闭流 
		
		cLogger.info("Out BocTBBusiBlc.parse()!");
		//返回Body节点
		return mBodyEle;
	}
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.BocTBBusiBlc.main");
		mLogger.info("程序开始...");
		
		BocTBBusiBlc mBatch = new BocTBBusiBlc();
		
		//用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * 严格日期校验的正则表达式：\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。
			 * 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("日期格式有误，应为yyyy-MM-dd！" + args[0]);
			}
		}
		
		mBatch.run();
		
		mLogger.info("成功结束！");
	}
}
