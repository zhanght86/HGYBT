package com.sinosoft.midplat.newabc.format;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jdom.JDOMException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RenewalPaymentQueryOutXsl extends XslCache {

	private static RenewalPaymentQueryOutXsl cThisIns=new RenewalPaymentQueryOutXsl();
	private String mPath="com/sinosoft/midplat/newabc/format/RenewalPaymentQueryOut.xsl";
	private RenewalPaymentQueryOutXsl() {
		load();
		FileCacheManage.newInstance().register(mPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into RenewalPaymentQueryOutXsl.load()...");
		String mFilePath=SysInfo.cBasePath+mPath;
		cLogger.info("Start load "+mFilePath+"...");
		cXslFile=new File(mFilePath);
		recordStatus();
		cXslTrsf=loadXsl(cXslFile);
		cLogger.info("End load "+mFilePath+"!");
		if(MidplatConf.newInstance().outConf())
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(mFilePath)),""));
			} catch (FileNotFoundException e) {
				cLogger.info(" ‰≥ˆXsl“Ï≥£",e);
			}
		cLogger.info("Out RenewalPaymentQueryOutXsl.load()!");
	}

	public static RenewalPaymentQueryOutXsl newInstance(){
		return cThisIns;
	}
	
}
