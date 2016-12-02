package com.sinosoft.midplat.newabc.key;

import java.io.File;
import java.io.FileInputStream;

import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.cache.FileCacheManage;
import com.sinosoft.midplat.common.cache.Load;

public class AbcKeyCache implements Load {
	private final static Logger cLogger = Logger.getLogger(AbcKeyCache.class);

	private static AbcKeyCache cThisIns = new AbcKeyCache();

	private File cKeyFile = null;

	private long cLastModified;
	private long cLength;

	private SecretKeySpec cKey = null;

	private String cPath = "key/newABCKey.dat";

	private AbcKeyCache() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}

	public void load() {
		cLogger.info("Into AbcKeyCache.load()...");

		String keyPath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("KeyPath");     
		String mFilePath = keyPath + cPath;
//		String mFilePath = SysInfo.cHome + cPath;
		
		cLogger.info("Start load " + mFilePath + "...");

		cKeyFile = new File(mFilePath);

		/**
		 * 一定要在加载之前记录文件属性。 文件的加载到文件属性设置之间存在细微的时间差， 如果恰巧在此时间差内外部修改了文件，
		 * 那么记录的数据就是新修改后的，导致这次修改不会自动被加载； 将文件属性设置放在加载之前，就算在时间差内文件发生改变，
		 * 由于记录的是旧的属性，系统会在下一个时间单元重新加载， 这样顶多会导致同一文件多加载一次，但不会出现修改而不被加载的bug。
		 */
		recordStatus();

		try {
			FileInputStream mKeyIs = new FileInputStream(cKeyFile);
			byte[] mKeyHexBytes = new byte[16];
			IOTrans.readFull(mKeyHexBytes, mKeyIs);
			mKeyIs.close();
			cLogger.debug("密钥：" + new String(mKeyHexBytes));			
			cKey = new SecretKeySpec(mKeyHexBytes, "AES");
		} catch (Exception ex) {
			cLogger.error("密钥文件有误！", ex);
		}

		cLogger.info("Out AbcKeyCache.load()!");
	}

	public SecretKeySpec getKey() {
		return cKey;
	}

	public static AbcKeyCache newInstance() {
		return cThisIns;
	}

	public boolean isChanged() {
		if (cKeyFile.lastModified() != cLastModified
				|| cKeyFile.length() != cLength) {
			return true;
		} else {
			return false;
		}
	}

	protected final void recordStatus() {
		cLastModified = cKeyFile.lastModified();
		cLength = cKeyFile.length();
		cLogger.info("conf file modified at ("
				+ DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS")
				+ ") and length=" + cLength + " bytes!");
	}
	
	public static void main(String [] args){
		System.out.println(AbcKeyCache.newInstance().getKey().getAlgorithm());
	}
}
