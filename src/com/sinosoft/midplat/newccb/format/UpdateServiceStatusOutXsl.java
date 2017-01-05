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
 * @ClassName: UpdateServiceStatusOutXsl
 * @Description: 
 * @author yuantongxin
 * @date 2017-1-4 ����10:57:19
 */
public class UpdateServiceStatusOutXsl extends XslCache {

	private static UpdateServiceStatusOutXsl cThisIns = new UpdateServiceStatusOutXsl();

	private String cPath = "com/sinosoft/midplat/newccb/format/UpdateServiceStatusOut.xsl";

	private UpdateServiceStatusOutXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}

	public void load() {
		cLogger.info("Into UpdateServiceStatusOutXsl.load()...");

		String mFilePath = SysInfo.cBasePath + cPath;
		cLogger.info("Start load " + mFilePath + "...");

		cXslFile = new File(mFilePath);

		/**
		 * һ��Ҫ�ڼ���֮ǰ��¼�ļ����ԡ� �ļ��ļ��ص��ļ���������֮�����ϸ΢��ʱ�� ���ǡ���ڴ�ʱ������ⲿ�޸����ļ���
		 * ��ô��¼�����ݾ������޸ĺ�ģ���������޸Ĳ����Զ������أ� ���ļ��������÷��ڼ���֮ǰ��������ʱ������ļ������ı䣬
		 * ���ڼ�¼���Ǿɵ����ԣ�ϵͳ������һ��ʱ�䵥Ԫ���¼��أ� ��������ᵼ��ͬһ�ļ������һ�Σ�����������޸Ķ��������ص�bug��
		 */
		recordStatus();

		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");

		// �Ƿ����xsl�ļ�
		boolean mOut = true;
		Element mConfLoad = MidplatConf.newInstance().getConf()
				.getRootElement().getChild("confLoad");
		if (null != mConfLoad
				&& "false".equals(mConfLoad.getAttributeValue("out"))) {
			mOut = false;
		}
		if (mOut) {
			try {
				cLogger.info(JdomUtil.toString(
						JdomUtil.build(new FileInputStream(cXslFile)), ""));
			} catch (IOException ex) {
				cLogger.error("��������ļ��쳣��", ex);
			}
		}

		cLogger.info("Out UpdateServiceStatusOutXsl.load()!");
	}

	public static UpdateServiceStatusOutXsl newInstance() {
		return cThisIns;
	}

}
