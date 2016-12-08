package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.cache.Load;

/**
 * @author changqing
 * 是一个抽象类，封装了xml配置文件缓存代理一些公用的操作，为编写真正的xml缓存代理提供便捷。
 */
public abstract class XmlConf implements Load {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	//存储xml配置文件的缓存对象。
	protected Document cConfDoc;	//端口-处理类 配置
	
	//指向xml配置文件
	protected File cConfFile;
	
	private long cLastModified;
	private long cLength; 
	
	/**
	 * 加载指定路径的xml，使用GBK编码解析为Document，并返回。
	 */
	protected Document loadXml(File pFile) {
		InputStream mXmlIs = null;
		try {
			mXmlIs = new FileInputStream(pFile);
		} catch (FileNotFoundException ex) {
			cLogger.error("路径有误，未找到指定文件！", ex);
		}
		return JdomUtil.build(mXmlIs, "GBK");
	}
	
	/**
	 * 获取xml配置文件的缓存对象
	 * @return  xml配置文件的缓存对象
	 */
	public Document getConf() {
		return cConfDoc;
	}
	
	public boolean isChanged() {
		if (cConfFile.lastModified()!=cLastModified
			|| cConfFile.length()!=cLength) {
			return true;
		} else {
			return false;
		}
	}
	
	protected final void recordStatus() {
		cLastModified = cConfFile.lastModified();
		cLength = cConfFile.length();
		//conf file modified at (2016-12-02 20:37:53,105) and length=23443 bytes!
		//conf file modified at (2016-12-02 20:37:53,286) and length=686 bytes!
		cLogger.info("conf file modified at (" + DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS") + ") and length=" + cLength + " bytes!");
	}
}