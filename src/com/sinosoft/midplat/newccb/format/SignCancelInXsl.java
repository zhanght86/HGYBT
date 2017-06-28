package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class SignCancelInXsl extends XslCache {

	private static SignCancelInXsl cThisIns=new SignCancelInXsl();
	private String cPath="com/sinosoft/midplat/newccb/format/SignCancelIn.xsl";
	
	private SignCancelInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	@Override
	public void load() {
		cLogger.info("Into SignCancelInXsl.load()...");
		
		String mFilePath=SysInfo.cBasePath+cPath;
		cLogger.info("Start load "+mFilePath+"...");
		
		cXslFile=new File(mFilePath);
		recordStatus();
		cXslTrsf=loadXsl(cXslFile);
		
		cLogger.info("End load "+mFilePath+"!");
		
		boolean mOut=true;
		Element mConfLoad=MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		if(null!=mConfLoad&&"false".equals(mConfLoad.getAttributeValue("out"))){
			mOut=false;
		}
		if(mOut){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)),""));
			} catch (Exception e) {
				cLogger.error("输出配置文件异常!",e);
			}
		}
		
		cLogger.info("End SignCancelInXsl.load()!");
	}

	public static SignCancelInXsl newInstance() {
		return cThisIns;
	}

}
