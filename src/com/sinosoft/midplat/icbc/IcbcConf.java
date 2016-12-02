package com.sinosoft.midplat.icbc;

import java.io.File;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class IcbcConf extends XmlConf {
	private static final IcbcConf cThisIns = new IcbcConf();
	
	private static final String cPath = "conf/icbc.xml";
	
	private IcbcConf() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into IcbcConf.load()...");
		 
		String mFilePath = SysInfo.cHome + cPath;
		cLogger.info("Start load " + mFilePath + "...");
		 
		cConfFile = new File(mFilePath);
		
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
		
		cConfDoc = loadXml(cConfFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ���������ļ�
		if (MidplatConf.newInstance().outConf()) {
			cLogger.info(JdomUtil.toString(cConfDoc));
		}
		cLogger.info("Out IcbcConf.load()!");
	}
	
	public static IcbcConf newInstance() {
		return cThisIns;
	}
}
