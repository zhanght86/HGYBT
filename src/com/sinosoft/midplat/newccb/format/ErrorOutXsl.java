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
	/**实例化对象*/
	private static ErrorOutXsl mErrorOutXsl = new ErrorOutXsl();
	
	/**样式表路径*/
	private String mXslPath = "com/sinosoft/midplat/newccb/format/ErrorMsgOut.xsl";
	
	/**
	 * 
	 * ErrorOutXsl构造方法
	 *
	 */
	private ErrorOutXsl()
	{
		load();
		FileCacheManage.newInstance().register(mXslPath, this);
	}

	/**
	 * load 的功能描述
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
		 * 一定要在加载之前记录文件属性。 文件的加载到文件属性设置之间存在细微的时间差， 如果恰巧在此时间差内外部修改了文件，
		 * 那么记录的数据就是新修改后的，导致这次修改不会自动被加载； 将文件属性设置放在加载之前，就算在时间差内文件发生改变，
		 * 由于记录的是旧的属性，系统会在下一个时间单元重新加载， 这样顶多会导致同一文件多加载一次，但不会出现修改而不被加载的bug。
		 */
		recordStatus();

		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");

		// 是否输出xsl文件
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
				cLogger.error("输出配置文件异常！", ex);
			}
		}

		cLogger.info("Out ReturnContOutXsl.load()!");

	}

	/**
	 * 
	 * newInstance 返回实例化的ErrorOutXsl静态对象
	 *
	 * @return
	 */
	public static ErrorOutXsl newInstance()
	{
		return mErrorOutXsl;
	}
}
