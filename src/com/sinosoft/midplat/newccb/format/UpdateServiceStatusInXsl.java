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
 * @ClassName: UpdateServiceStatusInXsl
 * @Description: 
 * @author yuantongxin
 * @date 2017-1-4 ����10:57:02
 */
public class UpdateServiceStatusInXsl extends XslCache {

		//ʵʱͶ�����뱨����ʽʵ��
		private static UpdateServiceStatusInXsl cThisIns = new UpdateServiceStatusInXsl();
		
		private String cPath = "com/sinosoft/midplat/newccb/format/UpdateServiceStatusIn.xsl";
		
		/**
		 * ʵʱͶ�����뱨����ʽ������
		 */
		private UpdateServiceStatusInXsl() {
			//����XSL�ļ�
			load();
			//�����ļ��������ϵͳʵ��ע��XSL·��
			FileCacheManage.newInstance().register(cPath, this);
		}
		
		/**
		 * ����XSL�ļ�
		 */
		public void load() {
			//Into UpdateServiceStatusInXsl.load()...
			cLogger.info("Into UpdateServiceStatusInXsl.load()...");
			
			//XSL�ļ�����·��
			String mFilePath = SysInfo.cBasePath + cPath;
			//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/UpdateServiceStatusIn.xsl...
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
			//Out UpdateServiceStatusIn.load()!
			cLogger.info("Out UpdateServiceStatusInXsl.load()!");
		}
		
		/**
		 * ��ȡʵʱͶ�����뱨����ʽʵ��
		 * @return
		 */
		public static UpdateServiceStatusInXsl newInstance() {
			return cThisIns;
		}

}
