package com.sinosoft.midplat.newccb.format;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class UpdateServiceStatus extends XmlSimpFormat {

	private String mSYS_RECV_TIME = null;//服务接受时间
	private String mSYS_RESP_TIME = null;//服务响应时间
	private String tranNo = null;//交易码
	private String tranDate = null;//交易日期
	private String sysTxCode = null;//服务名
	private Element oldTxHeader = null;//旧报文头
	private Element oldComEntity = null;//旧公共域
	
	public UpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into UpdateServiceStatus.noStd2Std()...");
		
		return pNoStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into UpdateServiceStatus.std2NoStd()...");
		
		return pStdXml;
	}
	
}
