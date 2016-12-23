package com.sinosoft.midplat.gzbank.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

/**
 * �µ����������������Ӧ������չ��ʽ��
 * @author yuantongxin
 */
public class PolicyContTrialOutXsl extends XslCache {
	//������ǰ���ʵ��
	private static PolicyContTrialOutXsl cThisIns = new PolicyContTrialOutXsl();
	
	//�µ�������ı�׼�������ת��Ϊ�������зǱ�׼�������XSL�ļ�·��
	private String cPath = "com/sinosoft/midplat/gzbank/format/PolicyContTrialOut.xsl";
	
	//�µ����������������Ӧ������չ��ʽ������
	private PolicyContTrialOutXsl() {
		//����XSL�ļ�
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	/**
	 * ����XSL�ļ�
	 */
	public void load() {
		//Into PolicyContTrialInXsl.load()...
		cLogger.info("Into PolicyContTrialInXsl.load()...");
		//XSL�ļ�����·��[F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/classes/XSL�ļ�·��]
		String mFilePath = SysInfo.cBasePath + cPath;
		//Start load XSL�ļ�����·��...[��ʼ����XSL�ļ�����·��...]
		cLogger.info("Start load " + mFilePath + "...");
		//XSL�ļ�����·�������ļ�����
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
		if (MidplatConf.newInstance().outConf()) {
			try {
				cLogger.info(
						JdomUtil.toString(
								JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (IOException ex) {
				cLogger.error("���xsl�쳣��", ex);
			}
		}
		
		cLogger.info("Out PolicyContTrialInXsl.load()!");
	}
	
	/**
	 * ��ȡ�µ����������������Ӧ������չ��ʽ��һʵ��
	 * @return ������ǰ���ʵ��
	 */
	public static PolicyContTrialOutXsl newInstance() {
		return cThisIns;
	}
}
