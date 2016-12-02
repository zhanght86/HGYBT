/**
 * 工行核保超时处理类
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcRealtimeBlc extends Balance {
	public IcbcRealtimeBlc() {
		super(IcbcConf.newInstance(), "200");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"CHAOSHI.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcRealtimeBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mtotalNumEle = new Element("totalNum");
		mBodyEle.addContent(mtotalNumEle);
		int mtotalNum = 0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tZoneNoEle = new Element("ZoneNo");
			tZoneNoEle.setText(tSubMsgs[0]);
			
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(tSubMsgs[1]);
			
			Element tTransNoEle = new Element("TransNo");
			tTransNoEle.setText(tSubMsgs[3]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[4]);
			
			Element tAppntNameEle = new Element("AppntName");
			tAppntNameEle.setText(tSubMsgs[5]);			
			
			Element tContDataEle = new Element("ContData");
			tContDataEle.addContent(tZoneNoEle);
			tContDataEle.addContent(tNodeNoEle);
			tContDataEle.addContent(tTransNoEle);
			tContDataEle.addContent(tProposalPrtNoEle);
			tContDataEle.addContent(tAppntNameEle);
			
			mBodyEle.addContent(tContDataEle);
			 
			mtotalNum++; 
		} 
		mtotalNumEle.setText(String.valueOf(mtotalNum));

		mBufReader.close();	//关闭流 
		
		cLogger.info("Out IcbcRealtimeBlc.parse()!");
		return mBodyEle;
	}
	protected Element getHead() {
		cLogger.info("Into Balance.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText("1");
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mServiceId = new Element(ServiceId);
		mServiceId.setText("20");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("1");
		
		Element mType = new Element("Type");
		mType.setText("2");//文件类型
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mServiceId);
		mHead.addContent(mBankCode);
		mHead.addContent(mType);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcRealtimeBlc.main");
		mLogger.info("程序开始...");
		
		IcbcRealtimeBlc mBatch = new IcbcRealtimeBlc();
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
