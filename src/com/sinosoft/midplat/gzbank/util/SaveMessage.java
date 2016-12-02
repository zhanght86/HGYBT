package com.sinosoft.midplat.gzbank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;

public class SaveMessage {
	private SaveMessage() {}
	private final static Logger cLogger = Logger.getLogger(SaveMessage.class);
	
	public static void save(Document pXmlDoc, String pTranCom, String pName) {
		StringBuilder mFilePath = new StringBuilder(SysInfo.cHome)
					.append("msg/")
					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append(pTranCom+"/").append(pName+"/");
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
	}
	
	public static void save(byte[] pBytes, String pTranCom, String pName,String tFileName) {
		StringBuilder mFilePath = new StringBuilder(SysInfo.cHome)
			.append("msg/")
			.append('/')
			.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append(pTranCom+"/").append(pName+"/")
					;
		File mFileDir = new File(mFilePath.toString());
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + tFileName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("保存文件失败！", ex);
		}
	}
	public static void main(String[] args) {
		System.out.println(SaveMessage.class.getResource("/").getPath());
	}
}
