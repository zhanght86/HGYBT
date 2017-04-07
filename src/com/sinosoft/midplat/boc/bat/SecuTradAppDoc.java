
package com.sinosoft.midplat.boc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.bat.Balance;
/**
 *	保全对账
 * 	@author PengYF
 *
 */
public class SecuTradAppDoc extends Balance{
	public SecuTradAppDoc() {
		super(BocConf.newInstance(), "1106");
	}
	protected String getFileName(){
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "SJTB"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into SecuTradAppDoc.parse()...");
		
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
		long prem=0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			tLineMsg = tLineMsg.trim();
			if(tLineMsg.length()==20){
				mCountEle.setText(Integer.parseInt(tLineMsg.substring(12,20))+"");
				cLogger.info("成功总数:"+tLineMsg.substring(12,20).trim());
			}
			if(tLineMsg.length()>20){
				Element tZoneNo=new Element("ZoneNo");
				Element tNodeNo=new Element("NodeNo");
				tNodeNo.setText(tLineMsg.substring(16,21).trim());
				Element tBusiType = new Element("BusiType");
				if('Y'==tLineMsg.charAt(tLineMsg.length()-1)){//Y-犹豫期内退保
					tBusiType.setText("07");
				}
				if('X'==tLineMsg.charAt(tLineMsg.length()-1)){//X-犹豫期外退保
					tBusiType.setText("09");
				}
				if('R'==tLineMsg.charAt(tLineMsg.length()-1)){//R-满期给付成功
					tBusiType.setText("11");
				}
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tLineMsg.substring(21,29).trim());
				Element tProposalPrtNoEle = new Element(ProposalPrtNo);
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tLineMsg.substring(29,59).trim());
				Element tAgentCom=new Element(AgentCom);
				tAgentCom.setText(tLineMsg.substring(16,21).trim());
				Element tPremEle = new Element(Prem);
				tPremEle.setText(NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim())+"");
				prem+=NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim());
				Element tDetailEle = new Element(Detail);
				cLogger.info("交易日期："+ tLineMsg.substring(21,29).trim());
				cLogger.info("保单号："+tLineMsg.substring(29,59).trim());
				cLogger.info("金额："+NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim()));
				cLogger.info("网点："+tLineMsg.substring(16,21).trim());
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tBusiType);
				tDetailEle.addContent(tAgentCom);
				tDetailEle.addContent(tProposalPrtNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tPremEle);
				tDetailEle.addContent(tNodeNo);
				tDetailEle.addContent(tZoneNo);
				
				mBodyEle.addContent(tDetailEle);
			}
		}
		cLogger.info("总金额:"+prem);
		mBodyEle.getChild("Prem").setText(String.valueOf(prem));
		mBufReader.close();	//关闭流
		cLogger.info("Out SecuTradAppDoc.parse()!");
		return mBodyEle;
	}
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.SecuTradAppDoc.main");
		mLogger.info("中国银行保全对账程序开始...");
		
		SecuTradAppDoc mBatch = new SecuTradAppDoc();
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
		mLogger.info("中国银行保全对账成功结束！");
	}
}
