package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.cache.Load;

/**
 * @author changqing
 * ��һ�������࣬��װ��xml�����ļ��������һЩ���õĲ�����Ϊ��д������xml��������ṩ��ݡ�
 */
public abstract class XmlConf implements Load {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	//�洢xml�����ļ��Ļ������
	protected Document cConfDoc;	//�˿�-������ ����
	
	//ָ��xml�����ļ�
	protected File cConfFile;
	
	private long cLastModified;
	private long cLength; 
	
	/**
	 * ����ָ��·����xml��ʹ��GBK�������ΪDocument�������ء�
	 */
	protected Document loadXml(File pFile) {
		InputStream mXmlIs = null;
		try {
			mXmlIs = new FileInputStream(pFile);
		} catch (FileNotFoundException ex) {
			cLogger.error("·������δ�ҵ�ָ���ļ���", ex);
		}
		return JdomUtil.build(mXmlIs, "GBK");
	}
	
	/**
	 * ��ȡxml�����ļ��Ļ������
	 * @return  xml�����ļ��Ļ������
	 */
	public Document getConf() {
		return cConfDoc;
	}
	
	public boolean isChanged() {
		if (cConfFile.lastModified()!=cLastModified
			|| cConfFile.length()!=cLength) {
			return true;
		} else {
			return false;
		}
	}
	
	protected final void recordStatus() {
		cLastModified = cConfFile.lastModified();
		cLength = cConfFile.length();
		//conf file modified at (2016-12-02 20:37:53,105) and length=23443 bytes!
		//conf file modified at (2016-12-02 20:37:53,286) and length=686 bytes!
		cLogger.info("conf file modified at (" + DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS") + ") and length=" + cLength + " bytes!");
	}
}