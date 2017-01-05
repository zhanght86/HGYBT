package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

/**
 * @ClassName: UpdateServiceStatusInXsl
 * @Description: 
 * @author yuantongxin
 * @date 2017-1-4 上午10:57:02
 */
public class UpdateServiceStatusInXsl extends XslCache {

		//实时投保输入报文样式实例
		private static UpdateServiceStatusInXsl cThisIns = new UpdateServiceStatusInXsl();
		
		private String cPath = "com/sinosoft/midplat/newccb/format/UpdateServiceStatusIn.xsl";
		
		/**
		 * 实时投保输入报文样式构造器
		 */
		private UpdateServiceStatusInXsl() {
			//加载XSL文件
			load();
			//配置文件缓存管理系统实例注册XSL路径
			FileCacheManage.newInstance().register(cPath, this);
		}
		
		/**
		 * 加载XSL文件
		 */
		public void load() {
			//Into UpdateServiceStatusInXsl.load()...
			cLogger.info("Into UpdateServiceStatusInXsl.load()...");
			
			//XSL文件绝对路径
			String mFilePath = SysInfo.cBasePath + cPath;
			//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/UpdateServiceStatusIn.xsl...
			cLogger.info("Start load " + mFilePath + "...");
			//XSL文件路径构建文件对象
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
			recordStatus();//记录XSL状态
			
			// 加载指定路径的Xsl，使用GBK编码解析为XSL转换器，并返回。
			cXslTrsf = loadXsl(cXslFile);
			//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl!
			cLogger.info("End load " + mFilePath + "!");
			
			//是否输出xsl文件
			boolean mOut = true;
			//获取配置加载元素
			Element mConfLoad =
				MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
			//睡眠秒数非空、禁用
			if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
				mOut = false;//不输出xsl文件
			}
			//输出xsl文件
			if (mOut) {
				try {
					cLogger.info(
							JdomUtil.toString(//输出原格式XSL文件字符串
									JdomUtil.build(new FileInputStream(cXslFile)), ""));//GBK编码构建一个文档对象[忽略标签之间的空字符]<?xml version="1.0"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" version="1.0" exclude-result-prefixes="java">
				} catch (IOException ex) {
					cLogger.error("输出配置文件异常！", ex);
				}
			}
			//Out UpdateServiceStatusIn.load()!
			cLogger.info("Out UpdateServiceStatusInXsl.load()!");
		}
		
		/**
		 * 获取实时投保输入报文样式实例
		 * @return
		 */
		public static UpdateServiceStatusInXsl newInstance() {
			return cThisIns;
		}

}
