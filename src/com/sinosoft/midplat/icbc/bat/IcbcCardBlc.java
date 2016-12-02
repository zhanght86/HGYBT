/**
 * 工行日终业务对账
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcCardBlc extends Balance {
	public IcbcCardBlc() {
		super(IcbcConf.newInstance(), "1006");
	}
	  
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"02.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcCardBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		int count = 0;
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		mCountEle.setText(String.valueOf(count));
		mBodyEle.addContent(mCountEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tCardTypeEle = new Element(CardType);
			cLogger.info("CardType:" + tSubMsgs[4]);
			if("001".equals(tSubMsgs[4])){
				tSubMsgs[4]=tSubMsgs[5].substring(0,7);
			}
			tCardTypeEle.setText(tSubMsgs[4]);
			cLogger.info("CardType转化后:" + tSubMsgs[4]);
			
			Element tCardNoEle = new Element(CardNo);
			tCardNoEle.setText(tSubMsgs[5]);
			cLogger.info("CardNo:" + tSubMsgs[5]);
			
			Element tCardStateEle = new Element("CardState");
			cLogger.info("CardState:" + tSubMsgs[6]);
			if("2".equals(tSubMsgs[6])){
				tSubMsgs[6] = "4";
			}else if("3".equals(tSubMsgs[6])){
				tSubMsgs[6] = "6";
			}else if("4".equals(tSubMsgs[6])){
				tSubMsgs[6] = "9";
			}else{
				tSubMsgs[6] = "-1";
			}
			tCardStateEle.setText(tSubMsgs[6]);
			cLogger.info("CardState转化后:" + tSubMsgs[6]);
			
			Element tOtherNoEle = new Element(OtherNo);
			tOtherNoEle.setText(tSubMsgs[8]);
			cLogger.info("CardState:" + tSubMsgs[8]);
			
			Element tAgentComEle = new Element(AgentCom);
			tAgentComEle.setText(tSubMsgs[1]+tSubMsgs[2]);
			cLogger.info("AgentCom:" + tSubMsgs[1]+tSubMsgs[2]);
			
			Element tTellerNoEle = new Element(TellerNo);
			tTellerNoEle.setText(tSubMsgs[3]);
			cLogger.info("TellerNo:" + tSubMsgs[3]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[7]);
			cLogger.info("TranNo:" + tSubMsgs[7]);
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tCardTypeEle);
			tDetailEle.addContent(tCardNoEle);
			tDetailEle.addContent(tCardStateEle);
			tDetailEle.addContent(tOtherNoEle);
			tDetailEle.addContent(tAgentComEle);
			tDetailEle.addContent(tTellerNoEle);
			tDetailEle.addContent(tTranNoEle);
			
			mBodyEle.addContent(tDetailEle);
			
			count++;
			
		}
		cLogger.info("对账日期：" + DateUtil.getCur10Date()+"、对账总数：" + String.valueOf(count));
		
		mCountEle.setText(String.valueOf(count));
		
		mBufReader.close();	//关闭流
		
		cLogger.info("Out IcbcCardBlc.parse()!");
		return mBodyEle;
	}
	
	
	protected Element getHead() {
		cLogger.info("Into IcbcCardBlc.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
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
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		
		cLogger.info("Out IcbcCardBlc.getHead()!");
		return mHead;
	}
}
