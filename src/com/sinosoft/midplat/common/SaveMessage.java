package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.MidplatConf;

public class SaveMessage {
	private final static Logger cLogger = Logger.getLogger(SaveMessage.class);
	public final static String cSavePath;
	static {
		String tSavePath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
		cLogger.debug("SavePath_HOME = " + tSavePath);
		if (null==tSavePath || "".equals(tSavePath)) {
			tSavePath = SysInfo.getRealPath("..");
		}
		tSavePath = tSavePath.replace('\\', '/');
		if (!tSavePath.endsWith("/")) {
			tSavePath += '/';
		}
		File tFile = new File(tSavePath);
		if (tFile.exists() && tFile.isDirectory()) {
			cSavePath = tSavePath;
		} else {
			cLogger.error("SavePath_HOME设置有误！" + tSavePath);
			cSavePath = null;
		}
		
		cLogger.debug("SavePath_HOME = " + cSavePath);
	}
	
	
	public static void save(Document pXmlDoc, String pTranCom, String pName) {
		long mStartMillis = System.currentTimeMillis();
		StringBuilder mFilePath = new StringBuilder(cSavePath)
					.append("msg/")
					.append(pTranCom).append('/')
					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		File mFileDir = new File(mFilePath.toString());
		if (!mFileDir.exists()) {
			mFileDir.mkdirs();
			if (!mFileDir.exists()) {
				cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
				return;
			}
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			JdomUtil.output(pXmlDoc, tFos);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("保存文件失败！", ex);
		}
		cLogger.info(pName+" IO输出耗时:"+(System.currentTimeMillis()-mStartMillis)/1000.0 + "s");
	}
	
	public static void save(byte[] pBytes, String pTranCom, String pName) {
		StringBuilder mFilePath = new StringBuilder(cSavePath)
			.append("msg/")
			.append(pTranCom).append('/')
			.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		File mFileDir = new File(mFilePath.toString());
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("保存文件失败！", ex);
		}
	}
}
