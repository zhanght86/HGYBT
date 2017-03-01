package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RenewalPaymentOutXsl extends XslCache {

	private static RenewalPaymentOutXsl cThisIns=new RenewalPaymentOutXsl();
	private String mPath="com/sinosoft/midplat/newabc/format/RenewalPaymentOut.xsl";
	
	private RenewalPaymentOutXsl() {
		load();
		FileCacheManage.newInstance().register(mPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into RenewalPaymentOutXsl.load()... ");
		String cFilePath=SysInfo.cBasePath+mPath;
		cXslFile=new File(cFilePath);
		cLogger.info("Start load "+cFilePath+"...");
		recordStatus();
		cXslTrsf=loadXsl(cXslFile);
		cLogger.info("End load "+cFilePath+"!");
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)),""));
			} catch (FileNotFoundException e) {
				cLogger.info(" ‰≥ˆXsl“Ï≥£",e);
			}
		}
		cLogger.info("Out RenewalPaymentOutXsl.load()! ");
	}
	
	public static RenewalPaymentOutXsl newInstance(){
		return cThisIns;
	}

}
