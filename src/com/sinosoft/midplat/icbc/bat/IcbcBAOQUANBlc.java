package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcBAOQUANBlc extends Balance{

	public IcbcBAOQUANBlc() {
		super(IcbcConf.newInstance(), "1104");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"BAOQUAN.txt";
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
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tBusiTypeEle = new Element("BusiType");
			tBusiTypeEle.setText(tSubMsgs[0]);
			cLogger.info("BusiType:" + tSubMsgs[0]);
			
			Element tTranNo = new Element("TranNo");
			tTranNo.setText(tSubMsgs[1]);
			cLogger.info("TranNo:" + tSubMsgs[1]);
			
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(tSubMsgs[3]+tSubMsgs[4]);
			cLogger.info("NodeNo:" + tSubMsgs[3]+tSubMsgs[4]);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[7]);
			cLogger.info("ContNo:" + tSubMsgs[7]);
			
			Element tEdorNoEle = new Element("EdorNo");
			tEdorNoEle.setText(tSubMsgs[8]);
			cLogger.info("EdorNo:" + tSubMsgs[8]);
			
			Element tAccNoEle=new Element(AccNo);
			tAccNoEle.setText(tSubMsgs[9]);
			cLogger.info("AccNo:" + tSubMsgs[9]);
			
			Element tAccNameEle = new Element(AccName);
			tAccNameEle.setText(tSubMsgs[10]);
			cLogger.info("AccName:" + tSubMsgs[10]);
			
			Element tTranDate=new Element("TranDate");
			tTranDate.setText(tSubMsgs[11]);
			cLogger.info("TranDate:" + tSubMsgs[11]);
			
			
			Element tDetailEle = new Element(Detail);
			
			tDetailEle.addContent(tBusiTypeEle);
			tDetailEle.addContent(tTranNo);
			tDetailEle.addContent(tNodeNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tEdorNoEle);
			tDetailEle.addContent(tAccNoEle);
			tDetailEle.addContent(tAccNameEle);
			tDetailEle.addContent(tTranDate);
			
			mBodyEle.addContent(tDetailEle);
			 
		} 
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
