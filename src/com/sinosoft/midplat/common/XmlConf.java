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
	//�м�ƽ̨�����ļ�����
	protected File cConfFile;
	
	//���һ�α��޸�ʱ��[������]
	private long cLastModified;
	//�ļ�����
	private long cLength; 
	
	/**
	 * ����ָ��·����xml��ʹ��GBK�������ΪDocument�������ء�
	 * @param pFile �м�ƽ̨�����ļ�
	 * @return �ĵ�����
	 */
	protected Document loadXml(File pFile) {
		//�����ֽ���
		InputStream mXmlIs = null;
		try {
			//XML�ļ������ļ������ֽ���
			mXmlIs = new FileInputStream(pFile);
		} catch (FileNotFoundException ex) {
			cLogger.error("·������δ�ҵ�ָ���ļ���", ex);
		}
		//����Java�ĵ�����ģ�͹��߲���GBK���뽫�м�ƽ̨�����ļ�����Ϊ�ĵ�����
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
	
	/**
	 * ��¼״̬
	 */
	protected final void recordStatus() {
		//�м�ƽ̨�����ļ����һ�α��޸�ʱ��[1482463604567:2016-12-23 11:26:44]
		cLastModified = cConfFile.lastModified();
		//�м�ƽ̨�����ļ�����[25215]
		cLength = cConfFile.length();
		//conf file modified at (2016-12-02 20:37:53,105) and length=23443 bytes!
		//conf file modified at (2016-12-02 20:37:53,286) and length=686 bytes!
		//conf file modified at (2016-12-13 09:34:31,688) and length=24594 bytes!
		//conf file modified at (2016-12-14 09:32:38,839) and length=14170 bytes!
		//�����ļ��޸��ڣ�2016-12-14 09:32:38839��������Ϊ25215�ֽڣ�
		cLogger.info("conf file modified at (" + DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS") + ") and length=" + cLength + " bytes!");
	}
}