package com.sinosoft.midplat.newccb.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatchSendDisk extends XmlSimpFormat {
	//非标准输入报文头
	private Element oldHeader=null;
	//非标准输入报文公共域
	private Element oldComEntity=null;
	//非标准输入报文头应用域
	private Element oldAppEntity=null;
	private String mSYS_RECV_TIME=null;
	private String mSYS_RESP_TIME=null;
	private String tranNo=null;
	private String tranDate=null;
	private String mSEC_TX_CODE=null;
	
	public BatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into BatchSendDisk.noStd2Std()...");
		Document pStdXml=BatResponseInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out BatchSendDisk.noStd2Std()...");
		return pStdXml;
	}
	
	public Document std2NoStd(Document pStdXml){
		return pStdXml;
	}
	
}
