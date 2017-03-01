package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class AccountChangeOutXsl extends XslCache {

	private static AccountChangeOutXsl cThisIns=new AccountChangeOutXsl();
	private String cPath="com/sinosoft/midplat/newabc/format/AccountChangeOut.xsl";
	
	private AccountChangeOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into AccountChangeOutXsl.load()...");
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
		cLogger.info("Out AccountChangeOutXsl.load()!");
	}

	public static AccountChangeOutXsl newInstance(){
		return cThisIns;
	}

}
