package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

/**
 * ʵʱͶ�����뱨����ʽ
 * @author yuantongxin
 */
public class NewContInXsl extends XslCache {
	//ʵʱͶ�����뱨����ʽʵ��
	private static NewContInXsl cThisIns = new NewContInXsl();
	
	private String cPath = "com/sinosoft/midplat/newccb/format/NewContIn.xsl";
	
	/**
	 * ʵʱͶ�����뱨����ʽ������
	 */
	private NewContInXsl() {
		//����XSL�ļ�
		load();
		//�����ļ��������ϵͳʵ��ע��XSL·��
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	/**
	 * ����XSL�ļ�
	 */
	public void load() {
		//Into NewContInXsl.load()...
		cLogger.info("Into NewContInXsl.load()...");
		
		//XSL�ļ�����·��
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl...
		cLogger.info("Start load " + mFilePath + "...");
		//XSL�ļ�·�������ļ�����
		cXslFile = new File(mFilePath);
		
		/**
		 * һ��Ҫ�ڼ���֮ǰ��¼�ļ����ԡ�
		 * �ļ��ļ��ص��ļ���������֮�����ϸ΢��ʱ��
		 * ���ǡ���ڴ�ʱ������ⲿ�޸����ļ���
		 * ��ô��¼�����ݾ������޸ĺ�ģ���������޸Ĳ����Զ������أ�
		 * ���ļ��������÷��ڼ���֮ǰ��������ʱ������ļ������ı䣬
		 * ���ڼ�¼���Ǿɵ����ԣ�ϵͳ������һ��ʱ�䵥Ԫ���¼��أ�
		 * ��������ᵼ��ͬһ�ļ������һ�Σ�����������޸Ķ��������ص�bug��
		 */
		recordStatus();//��¼XSL״̬
		
		// ����ָ��·����Xsl��ʹ��GBK�������ΪXSLת�����������ء�
		cXslTrsf = loadXsl(cXslFile);
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl!
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ����xsl�ļ�
		boolean mOut = true;
		//��ȡ���ü���Ԫ��
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		//˯�������ǿա�����
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			mOut = false;//�����xsl�ļ�
		}
		//���xsl�ļ�
		if (mOut) {
			try {
				cLogger.info(
						JdomUtil.toString(//���ԭ��ʽXSL�ļ��ַ���
								JdomUtil.build(new FileInputStream(cXslFile)), ""));//GBK���빹��һ���ĵ�����[���Ա�ǩ֮��Ŀ��ַ�]<?xml version="1.0"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" version="1.0" exclude-result-prefixes="java">
			} catch (IOException ex) {
				cLogger.error("��������ļ��쳣��", ex);
			}
		}
		//Out NewContInXsl.load()!
		cLogger.info("Out NewContInXsl.load()!");
	}
	
	/**
	 * ��ȡʵʱͶ�����뱨����ʽʵ��
	 * @return
	 */
	public static NewContInXsl newInstance() {
		return cThisIns;
	}
}
