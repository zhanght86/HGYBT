package com.sinosoft.midplat.gzbank.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

/**
 * 新单试算贵州银行非标准输入报文转核心标准输入报文可扩展样式表
 * @author yuantongxin
 */
public class PolicyContTrialInXsl extends XslCache {
	//构建当前类的实例
	private static PolicyContTrialInXsl cThisIns = new PolicyContTrialInXsl();
	
	//新单试算贵州银行非标准输入报文转核心标准输入报文XSL文件路径
	private String cPath = "com/sinosoft/midplat/gzbank/format/PolicyContTrialIn.xsl";
	
	//新单试算核心标准输入请求报文扩展样式表构造器
	private PolicyContTrialInXsl() {
		//加载XSL文件
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	/**
	 * 加载XSL文件
	 */
	public void load() {
		//Into PolicyContTrialInXsl.load()...
		cLogger.info("Into PolicyContTrialInXsl.load()...");
		//XSL文件绝对路径[F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/XSL文件路径]
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load XSL文件绝对路径...[开始加载XSL文件绝对路径...]
		cLogger.info("Start load " + mFilePath + "...");
		//XSL文件绝对路径构建文件对象
		cXslFile = new File(mFilePath);
		/**
		 * 一定要在加载之前记录文件属性。
		 * 文件的加载到文件属性设置之间存在细微的时间差，
		 * 如果恰巧在此时间差内外部修改了文件，
		 * 那么记录的数据就是新修改后的，导致这次修改不会自动被加载；
		 * 将文件属性设置放在加载之前，就算在时间差内文件发生改变，
		 * 由于记录的是旧的属性，系统会在下一个时间单元重新加载，
		 * 这样顶多会导致同一文件多加载一次，但不会出现修改而不被加载的bug。
		 */
		recordStatus();
		
		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//是否输出xsl文件
		if (MidplatConf.newInstance().outConf()) {
			try {
				cLogger.info(
						JdomUtil.toString(
								JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (IOException ex) {
				cLogger.error("输出xsl异常！", ex);
			}
		}
		
		cLogger.info("Out PolicyContTrialInXsl.load()!");
	}
	
	public static PolicyContTrialInXsl newInstance() {
		return cThisIns;
	}
}
