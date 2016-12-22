package com.sinosoft.midplat.newccb.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class QueryPaymentInfo extends XmlSimpFormat {
	private String mSYS_RECV_TIME = null;//�������ʱ��
	private String mSYS_RESP_TIME = null;//������Ӧʱ��
	private String tranNo = null;//������
	private String tranDate = null;//��������
	private String sysTxCode = null;//������
	private Element oldTxHeader = null;//�ɱ���ͷ
	private Element oldComEntity = null;//�ɹ�����
	
	public QueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.noStd2Std()...");
		
		return pNoStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.std2NoStd()...");
		
		return pStdXml;
	}

}
