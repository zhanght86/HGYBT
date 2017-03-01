package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class PolValueQueryOutXsl extends XslCache {

	private static PolValueQueryOutXsl cThisIns=new PolValueQueryOutXsl();
	
	private String cPath="com/sinosoft/midplat/newabc/format/PolValueQueryOut.xsl";
	
	private PolValueQueryOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into PolValueQueryOutXsl.load()..."); 
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
		cLogger.info("Out PolValueQueryOutXsl.load()!"); 
	}
	
	public static PolValueQueryOutXsl newInstance(){
		return cThisIns;
	}

}
