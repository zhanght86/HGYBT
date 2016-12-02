package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NonRealTimeContInXsl extends XslCache {
	private static NonRealTimeContInXsl cThisIns = new NonRealTimeContInXsl();
	
	private String cPath = "com/sinosoft/midplat/newabc/format/NonRealTimeContIn.xsl";
	
	private NonRealTimeContInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into NonRealTimeContInXsl.load()...");
		
		String mFilePath = SysInfo.cBasePath + cPath;
		cLogger.info("Start load " + mFilePath + "...");
		
		cXslFile = new File(mFilePath);
		
		recordStatus();
		
		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ����xsl�ļ�
		if (MidplatConf.newInstance().outConf()) {
			try {
				cLogger.info(
						JdomUtil.toString(
								JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (IOException ex) {
				cLogger.error("���xsl�쳣��", ex);
			}
		}
		
		cLogger.info("Out NonRealTimeContInXsl.load()!");
	}
	
	public static NonRealTimeContInXsl newInstance() {
		return cThisIns;
	}
}