package com.sinosoft.midplat.newccb;

import java.io.File;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NewCcbConf extends XmlConf {
	private static NewCcbConf cThisIns = new NewCcbConf();
	
	private String cPath = "conf/newccb.xml";
	
	private NewCcbConf() {
		//加载新建行配置文件
		load();
		//文件缓存管理
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		//Into NewCcbConf.load()...
		cLogger.info("Into NewCcbConf.load()...");
		// /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml
		String mFilePath = SysInfo.cHome + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml...
		cLogger.info("Start load " + mFilePath + "...");
		
		cConfFile = new File(mFilePath);
		
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
		
		cConfDoc = loadXml(cConfFile);
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml!
		cLogger.info("End load " + mFilePath + "!");
		
		//是否输出配置文件[默认输出]
		boolean mOut = true;
		//配置加载[睡眠秒数:60]
		//[Element: <confLoad/>]
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		//配置加载 != null，out属性值==false
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			//不输出配置文件
			mOut = false;
		}
		//输出配置文件
		if (mOut) {
			//Java文档对象模型工具保持原格式新建行配置文件:[Element: <newccb/>]
			cLogger.info(JdomUtil.toString(cConfDoc, ""));
		}
		//Out NewCcbConf.load()!
		cLogger.info("Out NewCcbConf.load()!");
	}
	
	public static NewCcbConf newInstance() {
		return cThisIns;
	}
}
