package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.utility.ExeSQL;

public class IcbcBusiBlc extends Balance{

	public IcbcBusiBlc() {
		super(IcbcConf.newInstance(), "1005");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"01.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcBusiBlc.parse()..."+cTranDate);
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}	
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
		long mSumPrem = 0;
		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			/**
			 * 
			 * 　　银行编号（10位）＋交易日期（10位）＋区域代码（10位）
			 *    ＋网点代码（10位）＋对帐交易码（4位）
			 *    ＋应对帐银行交易流水号（30位）＋保单号（20位）＋金额（12位，带小数点）＋销售渠道（2位）
			 *    01|20121004|00200|02108|0001|9049395|101-1261250|80071.99|01|
			 *    
			 *    
			 */
			
			
			
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			//ContNo|Prem|AgentCom|ProposalPrtNo|AppntName|InsuredName
			String nodeNo=null;
			if(tSubMsgs[2]!=null&&tSubMsgs[3]!=null){
				nodeNo=tSubMsgs[2].trim()+tSubMsgs[3].trim();
			}
			//String sql="select agentcom from nodemap where nodeno='"+nodeNo+"' and trancom=1";
			
//			ExeSQL exeSQL=new ExeSQL();
//			
//			String agentcom=exeSQL.getOneValue(sql);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[6]);
			cLogger.info("ContNo:|" + tSubMsgs[6]+"|");
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[7]);
			tPremEle.setText(String.valueOf(tPremFen));
			cLogger.info("Prem:" + tSubMsgs[7]);
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			cLogger.info("tAgentCom:" + nodeNo);
			
			Element tProposalPrtNo = new Element(ProposalPrtNo);
			cLogger.info("tProposalPrtNo:");
			
			Element tAppntName=new Element("AppntName");
			
			Element tInsuredName=new Element("InsuredName");
//			Element tNodeNoEle = new Element(NodeNo);
			
//			Element tTranDateEle = new Element(TranDate);
//			tTranDateEle.setText(tSubMsgs[0]);
//			cLogger.info("TranDate:" + tSubMsgs[0]);
			
//			Element tTranNo=new Element(TranNo);
			
			//Element tZoneNoEle = new Element(ZoneNo);
//			tTranNo.setText(tSubMsgs[1]);
//			cLogger.info("TranNo:" + tSubMsgs[1]);
			
			

			
			
			
			
			
			Element tDetailEle = new Element(Detail);
//			tDetailEle.addContent(tTranDateEle);
			//tDetailEle.addContent(tZoneNoEle);
//			tDetailEle.addContent(tTranNo);
			
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tAgentCom);
			
			tDetailEle.addContent(tProposalPrtNo);
			tDetailEle.addContent(tAppntName);
			tDetailEle.addContent(tInsuredName);
			
			mBodyEle.addContent(tDetailEle);
			 
			mCount++; 
			mSumPrem += tPremFen;
		} 
		mCountEle.setText(String.valueOf(mCount));
		mPremEle.setText(String.valueOf(mSumPrem));
		cLogger.info("对账日期：" + DateUtil.getCur10Date()+"、对账总金额："+NumberUtil.fenToYuan(mSumPrem)+"、对账总数：" + String.valueOf(mCount));
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
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());
		
		//报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0101");
		
		
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
		//报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}

}
