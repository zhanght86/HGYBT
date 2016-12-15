package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NewContInXsl extends XslCache {
	private static NewContInXsl cThisIns = new NewContInXsl();
	
	private String cPath = "com/sinosoft/midplat/newccb/format/NewContIn.xsl";
	
	private NewContInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		//Into NewContInXsl.load()...
		cLogger.info("Into NewContInXsl.load()...");
		
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl...
		cLogger.info("Start load " + mFilePath + "...");
		
		cXslFile = new File(mFilePath);
		
		/**
		 * 一定要在加载之前记录文件属性。
		 * 文件的加载到文件属性设置之间存在细微的时间差，
		 * 如果恰巧在此时间差内外部修改了文件，
		 * 那么记录的数据就是新修改后的，导致这次修改不会自动被加载；
		 * 将文件属性设置放在加载之前，就算在时间差内文件发生改变，
		 * 由于记录的是旧的属性，系统会在下一个时间单元重新加载，
		 * 这样顶多会导致同一文件多加载一次，但不会出现修改而不被加载的bug。
		 */
		recordStatus();
		
		cXslTrsf = loadXsl(cXslFile);
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl!
		cLogger.info("End load " + mFilePath + "!");
		
		//是否输出xsl文件
		boolean mOut = true;
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			mOut = false;
		}
		if (mOut) {
			try {
				cLogger.info(
						JdomUtil.toString(
								JdomUtil.build(new FileInputStream(cXslFile)), ""));//<?xml version="1.0"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" version="1.0" exclude-result-prefixes="java">
			} catch (IOException ex) {
				cLogger.error("输出配置文件异常！", ex);
			}
		}
		//Out NewContInXsl.load()!
		cLogger.info("Out NewContInXsl.load()!");
	}
	
	public static NewContInXsl newInstance() {
		return cThisIns;
	}
}
