package com.sinosoft.midplat.newabc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;

public class ABCDocUtil {
	private static String cDocHomePath = SysInfo.cBasePath+"test/doc/abc2/";
	protected static final Logger cLogger = Logger.getLogger(ABCDocUtil.class);
	
	public static Document getInNoStd(String pFuncflag){
		String tPath = cDocHomePath + pFuncflag + "_InNoStd.xml";
		return getDoc(tPath);
	}
	
	public static InputStream getInNoStdIs(String pFuncflag){
		String tPath = cDocHomePath + pFuncflag + "_InNoStd.xml";
		return getIs(tPath);
	}
	
	public static String getOutNoStdPath(String pFuncflag){
		String tPath = cDocHomePath + pFuncflag + "_OutNoStd.xml";
		return tPath;
	}
	
	public static Document getOutStd(String pFuncflag){
		String tPath = cDocHomePath + pFuncflag + "_OutStd.xml";
		return getDoc(tPath);
	}
	
	public static Document getDoc(String tPath){
		Document tInNoStdDoc = null;
		try {
			InputStream tIsDoc = new FileInputStream(tPath);
			tInNoStdDoc = JdomUtil.build(tIsDoc,"UTF-8");
		} catch (FileNotFoundException e) {
			cLogger.info("未找到文件："+tPath);
			e.printStackTrace();
		}
		return tInNoStdDoc;
	}
	
	public static InputStream getIs(String tPath){
		InputStream tIsDoc = null;
		try {
			tIsDoc = new FileInputStream(tPath);
		} catch (FileNotFoundException e) {
			cLogger.info("未找到文件："+tPath);
			e.printStackTrace();
		}
		return tIsDoc;
	}
	
}
