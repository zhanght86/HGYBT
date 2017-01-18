package com.sinosoft.midplat.newccb.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatchSendDisk extends XmlSimpFormat {
	//�Ǳ�׼���뱨��ͷ
	private Element oldHeader=null;
	//�Ǳ�׼���뱨�Ĺ�����
	private Element oldComEntity=null;
	//�Ǳ�׼���뱨��ͷӦ����
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
