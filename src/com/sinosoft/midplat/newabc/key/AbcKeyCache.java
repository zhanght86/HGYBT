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
		 * һ��Ҫ�ڼ���֮ǰ��¼�ļ����ԡ� �ļ��ļ��ص��ļ���������֮�����ϸ΢��ʱ�� ���ǡ���ڴ�ʱ������ⲿ�޸����ļ���
		 * ��ô��¼�����ݾ������޸ĺ�ģ���������޸Ĳ����Զ������أ� ���ļ��������÷��ڼ���֮ǰ��������ʱ������ļ������ı䣬
		 * ���ڼ�¼���Ǿɵ����ԣ�ϵͳ������һ��ʱ�䵥Ԫ���¼��أ� ��������ᵼ��ͬһ�ļ������һ�Σ�����������޸Ķ��������ص�bug��
		 */
		recordStatus();

		try {
			FileInputStream mKeyIs = new FileInputStream(cKeyFile);
			byte[] mKeyHexBytes = new byte[16];
			IOTrans.readFull(mKeyHexBytes, mKeyIs);
			mKeyIs.close();
			cLogger.debug("��Կ��" + new String(mKeyHexBytes));			
			cKey = new SecretKeySpec(mKeyHexBytes, "AES");
		} catch (Exception ex) {
			cLogger.error("��Կ�ļ�����", ex);
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
