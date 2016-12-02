package com.sinosoft.midplat.icbc.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XslCache;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class NewContOutXsl extends XslCache
{
	// private static NewContOutXsl cThisIns = new NewContOutXsl();

	// private String cPath = "com/sinosoft/midplat/icbc/format/NewContOut.xsl";

	String mXslName;
	private String cPath;

	private NewContOutXsl(String tXslName)
	{
		mXslName = tXslName;
		if (mXslName == null)
		{
			mXslName = "NewContOut";
		}
		cPath = "com/sinosoft/midplat/icbc/format/" + mXslName + ".xsl";
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}

	public void load()
	{
		cLogger.info("Into NewContOutXsl.load()...");

		String mFilePath = SysInfo.cBasePath + cPath;
		cLogger.info("Start load " + mFilePath + "...");

		cXslFile = new File(mFilePath);

		recordStatus();

		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");

		// 是否输出xsl文件
		if (MidplatConf.newInstance().outConf())
		{
			try
			{
				cLogger.info(JdomUtil.toString(JdomUtil.build(new FileInputStream(cXslFile)), ""));
			}
			catch (IOException ex)
			{
				cLogger.error("输出xsl异常！", ex);
			}
		}

		cLogger.info("Out NewContOutXsl.load()!");
	}

	public static NewContOutXsl newInstance(String tXslName)
	{
		NewContOutXsl cThisIns = new NewContOutXsl(tXslName);
		return cThisIns;
	}
}