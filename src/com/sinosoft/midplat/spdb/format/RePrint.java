package com.sinosoft.midplat.spdb.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {

	String tranNo;
	
	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		Document mStdXml = RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		/* ��¼��������ˮ�ţ����ر���ʱ�á� */
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		Document mNoStdXml =PrintContOutXsl.newInstance().getCache().transform(pStdXml);
		// ���ظ��ַ��ı���������ˮ��
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
	
}
