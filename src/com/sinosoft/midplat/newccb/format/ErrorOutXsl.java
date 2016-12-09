/**
 * $RCSfile: ErrorOutXsl.java
 * $Revision: 1.0
 * $Date: 2015-4-2
 *
 * Copyright (C) 2010 SinoSoft, Inc. All rights reserved.
 *
 * This software is the proprietary information of SinoSoft, Inc.
 * Use is subject to license terms.
 */
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
 * <p>Title: zhybt</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: SinoSoft</p>
 *
 * @author apple
 * @version 1.0
 */
public class ErrorOutXsl extends XslCache
{
	/**ʵ��������*/
	private static ErrorOutXsl mErrorOutXsl = new ErrorOutXsl();
	
	/**��ʽ��·��*/
	private String mXslPath = "com/sinosoft/midplat/newccb/format/ErrorMsgOut.xsl";
	
	/**
	 * 
	 * ErrorOutXsl���췽��
	 *
	 */
	private ErrorOutXsl()
	{
		load();
		FileCacheManage.newInstance().register(mXslPath, this);
	}

	/**
	 * load �Ĺ�������
	 * 
	 * @see com.sinosoft.midplat.common.cache.Load#load()
	 */
	public void load()
	{
		cLogger.info("Into ReturnContOutXsl.load()...");

		String mFilePath = SysInfo.cBasePath + mXslPath;
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
		Element mConfLoad = MidplatConf.newInstance().getConf().getRootElement().getChild("confLoad");
		if (null != mConfLoad && "false".equals(mConfLoad.getAttributeValue("out")))
		{
			mOut = false;
		}
		if (mOut)
		{
			try
			{
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)), ""));
			}
			catch (IOException ex)
			{
				cLogger.error("��������ļ��쳣��", ex);
			}
		}

		cLogger.info("Out ReturnContOutXsl.load()!");

	}

	/**
	 * 
	 * newInstance ����ʵ������ErrorOutXsl��̬����
	 *
	 * @return
	 */
	public static ErrorOutXsl newInstance()
	{
		return mErrorOutXsl;
	}
}
