package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RenewalPaymentQueryInXsl extends XslCache {
	
	//单例
	private static RenewalPaymentQueryInXsl cThisIns=new RenewalPaymentQueryInXsl();
	//Xsl路径
	private String cPath="com/sinosoft/midplat/newabc/format/RenewalPaymentQueryIn.xsl";
	
	//无参构造器
	private RenewalPaymentQueryInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	@Override
	public void load() {
		cLogger.info("Into RenewalPaymentQueryInXsl.load()...");
		//文件(绝对)路径
		String mFilePath=SysInfo.cBasePath+cPath;
		cLogger.info("Start load "+mFilePath+"...");
		//xsl文件对象
		cXslFile=new File(mFilePath);
		//记录XSL状态
		recordStatus();
		//加载指定路径的Xsl，使用GBK编码解析为XSLTransformer，并返回
		cXslTrsf=loadXsl(cXslFile);
		cLogger.info("End FilePath "+mFilePath+"!");
		//是否(日志)输出xsl文件
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)),""));
			} catch (Exception e) {
				cLogger.info("输出Xsl异常",e);
			}
		}
		cLogger.info("Out RenewalPaymentQueryInXsl.load()!");
	}
	
	public static RenewalPaymentQueryInXsl newInstance(){
		return cThisIns;
	}

}
