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
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcOverTimeBlc extends Balance{

	public IcbcOverTimeBlc() {
		super(IcbcConf.newInstance(), "1202");
	}

	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"CHAOSHI.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcBusiBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}	
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
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
			
			//中韩核心的NodeNo是把地区码和网点码拼接在一起的。
			String nodeNo = tSubMsgs[0]+tSubMsgs[1];
			cLogger.info("ZoneNo前："+tSubMsgs[0]+",NodeNo前:" + tSubMsgs[1]+",NodeNo:"+nodeNo);
			
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(nodeNo);
			cLogger.info("NodeNo:" + nodeNo);
			
			//工行定义的中韩的保险公司代码为050
			Element InsuIdEle = new Element("InsuId");
			InsuIdEle.setText("050");
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[3]);
			cLogger.info("TranNo:" + tSubMsgs[3]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[4]);
			cLogger.info("ProposalPrtNo:" + tSubMsgs[4]);
			
			Element tAppntNameEle = new Element("AppntName");
			tAppntNameEle.setText(tSubMsgs[5]);
			cLogger.info("AppntName:" + tSubMsgs[5]);
			
			Element tAppFlagEle = new Element("AppFlag");
			tAppFlagEle.setText(tSubMsgs[6]);
			cLogger.info("AppFlag:" + tSubMsgs[6]);
			
			Element tTranDateEle = new Element("TranDate");
			tTranDateEle.setText(tSubMsgs[7]);
			cLogger.info("TranDate:" + tSubMsgs[7]);
			
			Element tMakeDateEle = new Element("MakeDate");
			tMakeDateEle.setText(tSubMsgs[8]);
			cLogger.info("MakeDate:" + tSubMsgs[8]);
			
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tNodeNoEle);
			tDetailEle.addContent(InsuIdEle);
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tAppntNameEle);
			tDetailEle.addContent(tAppFlagEle);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tMakeDateEle);
			
			mBodyEle.addContent(tDetailEle);
			 
			mCount++; 
		} 
		mCountEle.setText(String.valueOf(mCount));
		cLogger.info("超时文件处理日期：" + DateUtil.getCur10Date()+"、处理总数：" + String.valueOf(mCount));
		mBufReader.close();	//关闭流 
		
		cLogger.info("Out IcbcBusiBlc.parse()!");
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
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcOverTimeBlc.main");
		mLogger.info("程序开始...");
		
		IcbcOverTimeBlc mBatch = new IcbcOverTimeBlc();
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
