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
 * �µ�����������зǱ�׼���뱨��ת���ı�׼���뱨�Ŀ���չ��ʽ��
 * @author yuantongxin
 */
public class PolicyContTrialInXsl extends XslCache {
	//������ǰ���ʵ��
	private static PolicyContTrialInXsl cThisIns = new PolicyContTrialInXsl();
	
	//�µ�����������зǱ�׼���뱨��ת���ı�׼���뱨��XSL�ļ�·��
	private String cPath = "com/sinosoft/midplat/gzbank/format/PolicyContTrialIn.xsl";
	
	//�µ�������ı�׼������������չ��ʽ������
	private PolicyContTrialInXsl() {
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
	
	public static PolicyContTrialInXsl newInstance() {
		return cThisIns;
	}
}
