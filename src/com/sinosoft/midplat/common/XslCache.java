package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.cache.Load;

/**
 * XSL������
 * @author yuantongxin
 */
public abstract class XslCache implements Load {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected XSLTransformer cXslTrsf;	//�˿�-������ ����
	
	protected File cXslFile;//����չ��ʽ���ļ�
	
	private long cLastModified;//����޸�ʱ��
	private long cLength;
	
	/**
	 * ����ָ��·����Xsl��ʹ��GBK�������ΪXSLTransformer�������ء�
	 */
	protected XSLTransformer loadXsl(File pFile) {
		XSLTransformer mXSLTransformer = null;
		try {
			InputStream tXslIs = new FileInputStream(pFile);
			InputStreamReader tXslIsr = new InputStreamReader(tXslIs, "GBK");
			mXSLTransformer = new XSLTransformer(tXslIsr);
		} catch (Exception ex) {
			cLogger.error("·������δ�ҵ�ָ���ļ���", ex);
		}
		return mXSLTransformer;
	}
	
	/**
	 * ��ȡ�˿�-������ ����
	 * @return
	 */
	public XSLTransformer getCache() {
		return cXslTrsf;
	}
	
	public boolean isChanged() {
		if (cXslFile.lastModified()!=cLastModified
			|| cXslFile.length()!=cLength) {
			return true;
		} else {
			return false;
		}
	}
	
	protected final void recordStatus() {
		cLastModified = cXslFile.lastModified();
		cLength = cXslFile.length();
		//conf file modified at (2016-12-02 20:37:30,488) and length=3306 bytes!
		//conf file modified at (2016-12-02 20:37:01,965) and length=941 bytes!
		//conf file modified at (2016-12-31 18:08:20,041) and length=25875 bytes!
		cLogger.info("conf file modified at (" + DateUtil.getDateStr(cLastModified, "yyyy-MM-dd HH:mm:ss,SSS") + ") and length=" + cLength + " bytes!");
	}
}
