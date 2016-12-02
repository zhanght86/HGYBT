package com.sinosoft.midplat.gzbank;

import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.Ybt4Socket;
import com.sinosoft.midplat.common.DateUtil;

public class GZBankYbt extends Ybt4Socket {
	public GZBankYbt(Socket pSocket) throws Exception {
		super(pSocket, GZBankConf.newInstance());
	}
	
	protected Document getErrorXml(String pErrorMsg) {
		cLogger.info("Into GZBankYbt.getErrorXml()...");
		
		Element mTranDate = new Element("TranDate");
		mTranDate.setText(
				String.valueOf(DateUtil.getCur8Date()));
		
		Element mTransNo = new Element("TransNo");
		
		Element mResultCode = new Element("ResultCode");
		mResultCode.setText("0001");
		
		Element mResultInfo = new Element("ResultInfo");
		mResultInfo.setText(pErrorMsg);
		
		Element mMain = new Element("Main");
		mMain.addContent(mTranDate);
		mMain.addContent(mTransNo);
		mMain.addContent(mResultCode);
		mMain.addContent(mResultInfo);

		Element mInsuRet = new Element("InsuRet");
		mInsuRet.addContent(mMain);
		
		cLogger.info("Out GZBankYbt.getErrorXml()!");
		return new Document(mInsuRet);
	}
}
