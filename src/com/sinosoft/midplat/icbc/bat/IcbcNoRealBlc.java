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

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.KeyUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;

public class IcbcNoRealBlc extends Balance {
	public IcbcNoRealBlc() {
		super(IcbcConf.newInstance(), "1201");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
//		return "ENY"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt";
		
		//总对总：ENY(3位)+保险公司代码(3位)+银行代码（2位）+日期（8位）+03.txt 加密的对账文件的文件名
		return "ENY"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt.des";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcNoRealBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		/**
		 * 对账文件加密，调用相应方法解密对账文件内容
		 */
		pBatIs = KeyUtil.decode(pBatIs, IcbcKeyCache.newInstance().getKey());
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
				
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		mBodyEle.addContent(mCountEle);

		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);
			
			Element tZoneNoEle = new Element("ZoneNo");
			tZoneNoEle.setText(tSubMsgs[2]);
			
			//中韩核心只使用NodeNo，NodeNo的值为地区码+网点码的值。
			String nodeNo = tSubMsgs[2].trim()+tSubMsgs[3].trim();
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(nodeNo);
			cLogger.info("NodeNo:"+nodeNo);
			
			Element tTellerNoEle = new Element(TellerNo);
			tTellerNoEle.setText(tSubMsgs[4]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[6]);
			cLogger.info("ProposalPrtNo:"+tSubMsgs[6]);
			
			Element tSaleChannelEle = new Element("SaleChannel");
			tSaleChannelEle.setText(tSubMsgs[7]);
			
			Element tAppFlagEle = new Element("AppFlag");
			tAppFlagEle.setText(tSubMsgs[8]);
			
			Element tAppntNameEle = new Element("AppntName");
			tAppntNameEle.setText(tSubMsgs[9]);
			
			Element tAppntIDTypeEle = new Element("AppntIDType");
			tAppntIDTypeEle.setText(tSubMsgs[10]);
			
			Element tAppntIDNoEle = new Element("AppntIDNo");
			tAppntIDNoEle.setText(tSubMsgs[11]);
			
			Element tAccNoEle = new Element("AccNo");
			tAccNoEle.setText(tSubMsgs[12]);
			
			
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tZoneNoEle);
			tDetailEle.addContent(tNodeNoEle);
			tDetailEle.addContent(tTellerNoEle);
			
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tSaleChannelEle);
			tDetailEle.addContent(tAppFlagEle);
			tDetailEle.addContent(tAppntNameEle);
			
			tDetailEle.addContent(tAppntIDTypeEle);
			tDetailEle.addContent(tAppntIDNoEle);
			tDetailEle.addContent(tAccNoEle);
			
			mBodyEle.addContent(tDetailEle);
			 
			mCount++; 
		} 
		mCountEle.setText(String.valueOf(mCount));

		mBufReader.close();	//关闭流 
		
		cLogger.info("Out IcbcNoRealBlc.parse()!");
		return mBodyEle;
	}
	protected Element getHead() {
		cLogger.info("Into Balance.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		//报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0101");
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());
		
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		//报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcNoRealBlc.main");
		mLogger.info("程序开始...");
		
		IcbcNoRealBlc mBatch = new IcbcNoRealBlc();
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
