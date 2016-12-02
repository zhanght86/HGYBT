/**
 * 农行新契约对账
 */

package com.sinosoft.midplat.abc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.abc.AbcConf;
import com.sinosoft.utility.ExeSQL;

public class AbcBusiBlc extends Balance {
	public AbcBusiBlc() {
		super(AbcConf.newInstance(), "2005");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "B"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".TXT";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into AbcBusiBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[4].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[5].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			if (!"01".equals(tSubMsgs[10])) {
				cLogger.warn("非承保保单，直接跳过，继续下一条！");
				continue;
			}
			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
				cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
				continue;
			}
			
			/*
			 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
			 * 联调的时候和农行的人员确认的，20130403
			 */
			String nodeNo=null;
			if(tSubMsgs[3]!=null&&tSubMsgs[4]!=null&&tSubMsgs[5]!=null){
				nodeNo=tSubMsgs[3].trim()+tSubMsgs[4].trim()+tSubMsgs[5].trim();
			}
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[2]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[7]);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[8]);
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[9]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
//			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
//			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			//tDetailEle.addContent(tContTypeEle);
			
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//关闭流
		
		cLogger.info("Out AbcBusiBlc.parse()!");
		return mBodyEle;
	}
	
	protected Element getHead() {
		cLogger.info("Into Balance.getHead()...");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..."+mCurrDate);
		
		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		if (null!=tTempStr && !"".equals(tTempStr)) {
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));
		
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText("node"));
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		Element mFuncFlag = new Element(FuncFlag);

		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		mFuncFlag.setText(tTempStr);
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		//报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		
		//报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);
		
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);

		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.AbcBusiBlc.main");
		mLogger.info("程序开始...");
		
		AbcBusiBlc mBatch = new AbcBusiBlc();
		
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
