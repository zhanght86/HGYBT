package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class DelayedNewContOutXsl extends XslCache {

	private static DelayedNewContOutXsl cThisIns=new DelayedNewContOutXsl();
	private String cPath="com/sinosoft/midplat/newccb/format/DelayedNewContOut.xsl";
	private DelayedNewContOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	@Override
	public void load() {
		cLogger.info("Into DelayedNewContOutXsl.load()...");
		String cFilePath=SysInfo.cBasePath+cPath;
		cXslFile=new File(cFilePath);
		cLogger.info("Start load "+cFilePath+"...");
		cXslTrsf=loadXsl(cXslFile);
		recordStatus();
		cLogger.info("End load "+cFilePath+"!");
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)),""));
			} catch (FileNotFoundException e) {
				cLogger.error("输出Xsl文件异常",e);
			}
		}
		cLogger.info("Out DelayedNewContOutXsl.load()!");
	}

	public static DelayedNewContOutXsl newInstance() {
		return cThisIns;
	}

}
