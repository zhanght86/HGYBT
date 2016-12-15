package com.sinosoft.midplat.newccb;

import java.io.File;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NewCcbConf extends XmlConf {
	private static NewCcbConf cThisIns = new NewCcbConf();
	
	private String cPath = "conf/newccb.xml";
	
	private NewCcbConf() {
		//�����½��������ļ�
		load();
		//�ļ��������
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		//Into NewCcbConf.load()...
		cLogger.info("Into NewCcbConf.load()...");
		// /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml
		String mFilePath = SysInfo.cHome + cPath;
		//Start load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml...
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
		//End load /F:/MyEclipse/workspace/.metadata/.me_tcat/webapps/HGLIFE/WEB-INF/conf/newccb.xml!
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ���������ļ�[Ĭ�����]
		boolean mOut = true;
		//���ü���[˯������:60]
		//[Element: <confLoad/>]
		Element mConfLoad =
			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		//���ü��� != null��out����ֵ==false
		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
			//����������ļ�
			mOut = false;
		}
		//��������ļ�
		if (mOut) {
			//Java�ĵ�����ģ�͹��߱���ԭ��ʽ�½��������ļ�:[Element: <newccb/>]
			cLogger.info(JdomUtil.toString(cConfDoc, ""));
		}
		//Out NewCcbConf.load()!
		cLogger.info("Out NewCcbConf.load()!");
	}
	
	public static NewCcbConf newInstance() {
		return cThisIns;
	}
}
