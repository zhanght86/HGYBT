package com.sinosoft.midplat.ccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class GreenTestOutXsl extends XslCache {
	private static GreenTestOutXsl cThisIns = new GreenTestOutXsl();
	
	private String cPath = "com/sinosoft/midplat/ccb/format/GreenTestOut.xsl";
	
	private GreenTestOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		//Into GreenTestOutXsl.load()...
		cLogger.info("Into GreenTestOutXsl.load()...");
		
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/GreenTestOut.xsl...
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
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/newccb/format/GreenTestOut.xsl!
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
						JdomUtil.toString(//Java�ĵ�����ģ�͹��ߺ��������еı���
								JdomUtil.build(new FileInputStream(cXslFile)), ""));//Java�ĵ�����ģ�͹��߲���GBK���빹��һ���ĵ����󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
			} catch (IOException ex) {
				//��������ļ��쳣��
				cLogger.error("��������ļ��쳣��", ex);
			}
		}
		//Out GreenTestOutXsl.load()!
		cLogger.info("Out GreenTestOutXsl.load()!");
	}
	
	public static GreenTestOutXsl newInstance() {
		return cThisIns;
	}
}
