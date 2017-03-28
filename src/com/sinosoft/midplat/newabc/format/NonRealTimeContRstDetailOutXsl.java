package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NonRealTimeContRstDetailOutXsl extends XslCache {

	private static NonRealTimeContRstDetailOutXsl cThisIns=new NonRealTimeContRstDetailOutXsl();
	private String cPath="com/sinosoft/midplat/newabc/format/NonRealTimeContRstDetailOut.xsl";
	
	public NonRealTimeContRstDetailOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into NonRealTimeContRstDetailOutXsl.load()...");
		String mFilePath=SysInfo.cBasePath+cPath;
		cLogger.info("Start load "+mFilePath+"...");
		cXslFile=new File(mFilePath);
		recordStatus();
		cXslTrsf=loadXsl(cXslFile);
		cLogger.info("End load "+mFilePath+"!");
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile))));
			} catch (IOException e) {
				cLogger.error(" ‰≥ˆxsl“Ï≥£",e);
			}
		}
		cLogger.info("Out NonRealTimeContRstDetailOutXsl.load()!");
	}

	public static NonRealTimeContRstDetailOutXsl newInstance() {
		return cThisIns;
	}
	
}
