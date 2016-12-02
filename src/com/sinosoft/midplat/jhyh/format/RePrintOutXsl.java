package com.sinosoft.midplat.jhyh.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;
import com.sinosoft.midplat.icbc.format.NewContOutXsl;

public class RePrintOutXsl extends XslCache {
//	private static RePrintOutXsl cThisIns = new RePrintOutXsl();
//	
//	private String cPath = "com/sinosoft/midplat/jhyh/format/RePrintOut.xsl";
	
	String mXslName;
	
	private String cPath;
	
	private RePrintOutXsl(String tXslName) {
		mXslName=tXslName;
		if(mXslName==null){
			mXslName  ="RePrintOut";
		}
		cPath = "com/sinosoft/midplat/jhyh/format/"+mXslName+".xsl";
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	private RePrintOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into RePrintOutXsl()...");
		
		String mFilePath = SysInfo.cBasePath + cPath;
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
								JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (IOException ex) {
				cLogger.error("��������ļ��쳣��", ex);
			}
		}
		
		cLogger.info("Out RePrintOutXsl.load()!");
	}
	
	public static RePrintOutXsl newInstance(String tXslName) {
		RePrintOutXsl cThisIns = new RePrintOutXsl(tXslName);
		return cThisIns;
	}
}
