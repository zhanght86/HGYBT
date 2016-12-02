/**
 * 工行豫期退保/退保业务对账
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcBusiBlcYT extends Balance {
	public IcbcBusiBlcYT() {
		super(IcbcConf.newInstance(), "45");
	}
	protected String getFileName() {
		Date now = new Date();
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+"01"+new SimpleDateFormat("yyyyMMdd").format(now)+"BAOQUAN.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcBusiBlcYT.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));

		Element mBodyEle = new Element(Body);
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			String type = tSubMsgs[0];
			if(type.equals("07"))//犹退
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("404");
				
				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);
				
				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			}
			if(type.equals("10"))//退保
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("414");

				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);

				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			}
			if(!type.equals("07")&&!type.equals("10"))
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("424");

				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);
				
				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			} 
		} 

		mBufReader.close();	//关闭流 
		
		cLogger.info("Out IcbcBusiBlcYT.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcBusiBlcYT.main");
		mLogger.info("程序开始...");
		
		IcbcBusiBlc mBatch = new IcbcBusiBlc();
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
