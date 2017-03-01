package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class PolValueQueryInXsl extends XslCache {

	private static PolValueQueryInXsl cThisIns=new PolValueQueryInXsl();
	private String cPath="com/sinosoft/midplat/newabc/format/PolValueQueryIn.xsl";
	
	private PolValueQueryInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into PolValueQueryInXsl.load()...");
		String cFilePath=SysInfo.cBasePath+cPath;
		cXslFile=new File(cFilePath);
		cLogger.info("Start load "+cFilePath+"...");
		cXslTrsf=loadXsl(cXslFile);
		recordStatus();
		cLogger.info("End load "+cFilePath+"!");
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (FileNotFoundException e) {
				cLogger.error(" ‰≥ˆXsl“Ï≥£!",e);
			}
		}
		cLogger.info("Out PolValueQueryInXsl.load()!");
	}

	public static PolValueQueryInXsl newInstance(){
		return cThisIns;
	}
}
