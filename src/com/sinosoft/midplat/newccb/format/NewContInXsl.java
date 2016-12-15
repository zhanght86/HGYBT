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

public class NewContInXsl extends XslCache {
	private static NewContInXsl cThisIns = new NewContInXsl();
	
	private String cPath = "com/sinosoft/midplat/newccb/format/NewContIn.xsl";
	
	private NewContInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		//Into NewContInXsl.load()...
		cLogger.info("Into NewContInXsl.load()...");
		
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl...
		cLogger.info("Start load " + mFilePath + "...");
		
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
		recordStatus();
		
		cXslTrsf = loadXsl(cXslFile);
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/NewContIn.xsl!
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ����xsl�ļ�
		boolean mOut = true;
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			mOut = false;
		}
		if (mOut) {
			try {
				cLogger.info(
						JdomUtil.toString(
								JdomUtil.build(new FileInputStream(cXslFile)), ""));//<?xml version="1.0"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" version="1.0" exclude-result-prefixes="java">
			} catch (IOException ex) {
				cLogger.error("��������ļ��쳣��", ex);
			}
		}
		//Out NewContInXsl.load()!
		cLogger.info("Out NewContInXsl.load()!");
	}
	
	public static NewContInXsl newInstance() {
		return cThisIns;
	}
}
