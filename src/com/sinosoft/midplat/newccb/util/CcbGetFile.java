package com.sinosoft.midplat.newccb.util;

import java.io.File;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class CcbGetFile extends XmlConf {
	private static CcbGetFile cThisIns = new CcbGetFile();
	
	private String cPath = "com/sinosoft/midplat/newccb/format/xslTemplate/getfile.xml";
	
	private CcbGetFile() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into CcbConf.load()...");
		
		String mFilePath = SysInfo.cHome + cPath;
		cLogger.info("Start load " + mFilePath + "...");
		
		cConfFile = new File(mFilePath);
		
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
		
		cConfDoc = loadXml(cConfFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//是否输出配置文件
		boolean mOut = true;
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			mOut = false;
		}
		if (mOut) {
			cLogger.info(JdomUtil.toString(cConfDoc, ""));
		}
		
		cLogger.info("Out CcbConf.load()!");
	}
	
	public static CcbGetFile newInstance() {
		return cThisIns;
	}
}
