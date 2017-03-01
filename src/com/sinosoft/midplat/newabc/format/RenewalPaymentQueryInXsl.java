package com.sinosoft.midplat.newabc.format;

import java.io.File;
import java.io.FileInputStream;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class RenewalPaymentQueryInXsl extends XslCache {
	
	//����
	private static RenewalPaymentQueryInXsl cThisIns=new RenewalPaymentQueryInXsl();
	//Xsl·��
	private String cPath="com/sinosoft/midplat/newabc/format/RenewalPaymentQueryIn.xsl";
	
	//�޲ι�����
	private RenewalPaymentQueryInXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	@Override
	public void load() {
		cLogger.info("Into RenewalPaymentQueryInXsl.load()...");
		//�ļ�(����)·��
		String mFilePath=SysInfo.cBasePath+cPath;
		cLogger.info("Start load "+mFilePath+"...");
		//xsl�ļ�����
		cXslFile=new File(mFilePath);
		//��¼XSL״̬
		recordStatus();
		//����ָ��·����Xsl��ʹ��GBK�������ΪXSLTransformer��������
		cXslTrsf=loadXsl(cXslFile);
		cLogger.info("End FilePath "+mFilePath+"!");
		//�Ƿ�(��־)���xsl�ļ�
		if(MidplatConf.newInstance().outConf()){
			try {
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)),""));
			} catch (Exception e) {
				cLogger.info("���Xsl�쳣",e);
			}
		}
		cLogger.info("Out RenewalPaymentQueryInXsl.load()!");
	}
	
	public static RenewalPaymentQueryInXsl newInstance(){
		return cThisIns;
	}

}
