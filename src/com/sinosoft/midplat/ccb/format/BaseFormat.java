/**
 * 报文转换基础类。
 * 本类提供了getStdXml(Document)和getNoStdXml(Document)的简单实现，进来什么，返回什么。
 * 用于支持非标准的org.jdom.Document和标准的org.jdom.Document之间的转换。
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