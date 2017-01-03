package com.sinosoft.midplat.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.MidplatConf;

/**
 * 系统信息
 * @author yuantongxin
 */
public final class SysInfo {
	private final static Class<SysInfo> cThisClass = SysInfo.class;
	//记录当前类的日志
	private final static Logger cLogger = Logger.getLogger(cThisClass);
	
	//基本路径
	public final static String cBasePath;
	static {
		String tPath = cThisClass.getResource("/basepath.r").getPath();
		try {
			tPath = URLDecoder.decode(tPath, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			cLogger.error("不支持utf-8！", ex);
		}
		//basepath.r = /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/basepath.r
		cLogger.debug("basepath.r = " + tPath);
		cBasePath = tPath.substring(0, tPath.lastIndexOf("basepath.r"));//F:/MyEclipse/workspace/HGLIFE/src/
		//BasePath = /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/
		cLogger.debug("BasePath = " + cBasePath);
	}
	
	//环境变量
	public final static String cHome;
	static {
		//获取中间平台本地环境变量值
		String tHome = System.getenv("MIDPLAT_HOME");
		//MIDPLAT_HOME = null
		cLogger.debug("MIDPLAT_HOME = " + tHome);
		//环境变量值为空、空字符
		if (null==tHome || "".equals(tHome)) {
			//设置环境变量值为得到的绝对路径
			tHome = getRealPath("..");
		}
		//将环境变量中的\\替换为/
		tHome = tHome.replace('\\', '/');
		//环境变量非/后缀结尾
		if (!tHome.endsWith("/")) {
			//环境变量拼接上/
			tHome += '/';
		}
		//构建环境变量文件对象
		File tFile = new File(tHome);
		//环境变量表示的目录存在
		if (tFile.exists() && tFile.isDirectory()) {
			//为成员环境变量赋值
			cHome = tHome;
		} else {
			//MIDPLAT_HOME设置有误！环境变量
			cLogger.error("MIDPLAT_HOME设置有误！" + tHome);
			//环境变量设为空
			cHome = null;
		}
		//Home = /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/
		cLogger.debug("Home = " + cHome);
	}
	

	/**
	 * 得到绝对路径
	 * @param pPath 路径[父级目录:..]
	 * @return pPath 绝对路径
	 */
	public static String getRealPath(String pPath) {
		///F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/
		//解析一个表示基本路径的文件统一资源标识符的将路径中的\\转换为/的字符串,构造一个新统一资源标识符[路径URI]
		///F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/
		pPath = new File(cBasePath).toURI().resolve(pPath.replace('\\', '/')).getPath();
		try {
			//将路径URI编码为UTF-8字符串
			pPath = URLDecoder.decode(pPath, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			cLogger.error("不支持utf-8！", ex);
		}
		//返回UTF-8格式的路径
		return pPath;
	}
	
	public static String getCurPath(Class<?> pClass) {
		String mPath = pClass.getResource(".").getPath();
		try {
			mPath = URLDecoder.decode(mPath, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			cLogger.error("不支持utf-8！", ex);
		}
		return mPath;
	}
	
	public static void main(String args []){
		System.out.println(SysInfo.cBasePath);
		System.out.println(SysInfo.cHome);
		System.out.println(SysInfo.getRealPath("aaa"));
		System.out.println(SysInfo.getCurPath(SysInfo.class));
	}
}
