package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class AccountChangeInXsl extends XslCache {

	private static AccountChangeInXsl cThisIns=new AccountChangeInXsl();
	private String cPath="com/sinosoft/midplat/newabc/format/AccountChangeIn.xsl";
	
	private AccountChangeInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into AccountChangeInXsl.load()...");
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
				cLogger.error("输出Xsl文件异常!",e);
			}
		}
		cLogger.info("Out AccountChangeInXsl.load()!");
	}

	public static AccountChangeInXsl newInstance(){
		return cThisIns;
	}
	
}
