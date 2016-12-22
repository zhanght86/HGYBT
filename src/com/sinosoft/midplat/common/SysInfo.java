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
	
	public final static String cHome;
	static {
		String tHome = System.getenv("MIDPLAT_HOME");
		//MIDPLAT_HOME = null
		cLogger.debug("MIDPLAT_HOME = " + tHome);
		if (null==tHome || "".equals(tHome)) {
			tHome = getRealPath("..");
		}
		tHome = tHome.replace('\\', '/');
		if (!tHome.endsWith("/")) {
			tHome += '/';
		}
		File tFile = new File(tHome);
		if (tFile.exists() && tFile.isDirectory()) {
			cHome = tHome;
		} else {
			cLogger.error("MIDPLAT_HOME设置有误！" + tHome);
			cHome = null;
		}
		//Home = /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/
		cLogger.debug("Home = " + cHome);
	}
	

	/**
	 * 得到绝对路径
	 * @param pPath
	 * @return
	 */
	public static String getRealPath(String pPath) {
		///F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/
		pPath = new File(cBasePath).toURI().resolve(pPath.replace('\\', '/')).getPath();
		try {
			pPath = URLDecoder.decode(pPath, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			cLogger.error("不支持utf-8！", ex);
		}
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
