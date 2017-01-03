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
	//中间平台配置文件对象
	protected File cConfFile;
	
	//最后一次被修改时间[毫秒数]
	private long cLastModified;
	//文件长度
	private long cLength; 
	
	/**
	 * 加载指定路径的xml，使用GBK编码解析为Document，并返回。
	 * @param pFile 中间平台配置文件
	 * @return 文档对象
	 */
	protected Document loadXml(File pFile) {
		//输入字节流
		InputStream mXmlIs = null;
		try {
			//XML文件构建文件输入字节流
			mXmlIs = new FileInputStream(pFile);
		} catch (FileNotFoundException ex) {
			cLogger.error("路径有误，未找到指定文件！", ex);
		}
		//返回Java文档对象模型工具采用GBK编码将中间平台配置文件构建为文档对象
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
	
	/**
	 * 记录状态
	 */
	protected final void recordStatus() {
		//中间平台配置文件最后一次被修改时间[1482463604567:2016-12-23 11:26:44]
		cLastModified = cConfFile.lastModified();
		//中间平台配置文件长度[25215]
		cLength = cConfFile.length();
		//conf file modified at (2016-12-02 20:37:53,105) and length=23443 bytes!
		//conf file modified at (2016-12-02 20:37:53,286) and length=686 bytes!
		//conf file modified at (2016-12-13 09:34:31,688) and length=24594 bytes!
		//conf file modified at (2016-12-14 09:32:38,839) and length=14170 bytes!
		//配置文件修改在（2016-12-14 09:32:38839），长度为25215字节！
		cLogger.info("conf file modified at (" + DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS") + ") and length=" + cLength + " bytes!");
	}
}