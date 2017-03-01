package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jdom.JDOMException;

import com.f1j.calc.pro.re;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RenewalPaymentInXsl extends XslCache {

	private static RenewalPaymentInXsl cThisIns=new RenewalPaymentInXsl();
	private String mPath="com/sinosoft/midplat/newabc/format/RenewalPaymentIn.xsl";
	
	private RenewalPaymentInXsl() {
		load();
		FileCacheManage.newInstance().register(mPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into RenewalPaymentInXsl.load()... ");
		String cFilePath=SysInfo.cBasePath+mPath;
		cLogger.info("Start load "+cFilePath+"...");
		cXslFile=new File(cFilePath);
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
		cLogger.info("Out RenewalPaymentInXsl.load()! ");
	}
	
	public static RenewalPaymentInXsl newInstance(){
		return cThisIns;
	}

}
