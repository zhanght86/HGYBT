package com.sinosoft.midplat.ccb;

import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.Ybt4Socket;
import com.sinosoft.midplat.common.DateUtil;

public class CcbYbt extends Ybt4Socket {
	public CcbYbt(Socket pSocket) throws Exception {
		super(pSocket, CcbConf.newInstance());
	}
	
	protected Document getErrorXml(String pErrorMsg) {
		cLogger.info("Into CcbYbt.getErrorXml()...");
		
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));
		
		Element mBkPlatSeqNo = new Element("BkPlatSeqNo");
		
		Element mBkOthRetCode = new Element("BkOthRetCode");
		mBkOthRetCode.setText("10001");
		
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		mBkOthRetMsg.setText(pErrorMsg);
		
		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkPlatSeqNo);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);
		
		Element mTransaction_Header = new Element("Transaction_Header");
		mTransaction_Header.addContent(mTran_Response);

		Element mTransaction = new Element("Transaction");
		mTransaction.addContent(mTransaction_Header);
		
		cLogger.info("Out CcbYbt.getErrorXml()!");
		return new Document(mTransaction);
	}
}
