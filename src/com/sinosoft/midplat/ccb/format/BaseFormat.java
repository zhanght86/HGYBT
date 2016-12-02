/**
 * ����ת�������ࡣ
 * �����ṩ��getStdXml(Document)��getNoStdXml(Document)�ļ�ʵ�֣�����ʲô������ʲô��
 * ����֧�ַǱ�׼��org.jdom.Document�ͱ�׼��org.jdom.Document֮���ת����
 */

package com.sinosoft.midplat.ccb.format;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.XmlTag;

public class BaseFormat implements XmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	public Document getStdXml(Document pNoStdXml) throws Exception {
		cLogger.info("Into BaseFormat.getStdXml()...");
		cLogger.info("Out BaseFormat.getStdXml()!");
		return pNoStdXml;
	}
	
	public Document getNoStdXml(Document pStdXml) throws Exception {
		cLogger.info("Into BaseFormat.getNoStdXml()...");
		cLogger.info("Out BaseFormat.getNoStdXml()!");
		return pStdXml;
	}
}