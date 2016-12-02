package com.sinosoft.midplat.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.MidplatConf;

public final class SysInfo {
	private final static Class<SysInfo> cThisClass = SysInfo.class;
	
	private final static Logger cLogger = Logger.getLogger(cThisClass);
	
	public final static String cBasePath;
	static {
		String tPath = cThisClass.getResource("/basepath.r").getPath();
		try {
			tPath = URLDecoder.decode(tPath, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			cLogger.error("不支持utf-8！", ex);
		}
		cLogger.debug("basepath.r = " + tPath);
		cBasePath = tPath.substring(0, tPath.lastIndexOf("basepath.r"));//F:/MyEclipse/workspace/HGLIFE/src/
		cLogger.debug("BasePath = " + cBasePath);
	}
	
	public final static String cHome;
	static {
		String tHome = System.getenv("MIDPLAT_HOME");
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
		
		cLogger.debug("Home = " + cHome);
	}
	

	
	public static String getRealPath(String pPath) {
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
