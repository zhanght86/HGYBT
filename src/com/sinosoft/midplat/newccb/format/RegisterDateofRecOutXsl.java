package com.sinosoft.midplat.newccb.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RegisterDateofRecOutXsl extends XslCache {
	private static RegisterDateofRecOutXsl cThisIns = new RegisterDateofRecOutXsl();
	
	private String cPath = "com/sinosoft/midplat/newccb/format/RegisterDateofRecOut.xsl";
	
	private RegisterDateofRecOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into RegisterDateofRecOutXsl.load()...");
		
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
//		boolean mOut = true;
//		Element mConfLoad =
//			MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
//		if (null!=mConfLoad && "false".equals(mConfLoad.getAttributeValue("out"))) {
//			mOut = false;
//		}
//		if (mOut) {
//			try {
//				cLogger.info(
//						JdomUtil.toString(
//								JdomUtil.build(new FileInputStream(cXslFile)), ""));
//			} catch (IOException ex) {
//				cLogger.error("��������ļ��쳣��", ex);
//			}
//		}
		
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
		cLogger.info("Out RegisterDateofRecOutXsl.load()!");
	}
	
	public static RegisterDateofRecOutXsl newInstance() {
		return cThisIns;
	}
}