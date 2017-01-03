package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.MidplatConf;

/**
 * 保存报文
 * @author yuantongxin
 */
public class SaveMessage {
	private final static Logger cLogger = Logger.getLogger(SaveMessage.class);
	public final static String cSavePath;//保存路径
	static {
		//获取中间平台配置文件保存路径[盘符]
		String tSavePath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
		//SavePath_HOME = f:\
		cLogger.debug("SavePath_HOME = " + tSavePath);
		//保存路径为空
		if (null==tSavePath || "".equals(tSavePath)) {
			//使用
			tSavePath = SysInfo.getRealPath("..");
		}
		tSavePath = tSavePath.replace('\\', '/');
		if (!tSavePath.endsWith("/")) {
			tSavePath += '/';
		}
		File tFile = new File(tSavePath);
		if (tFile.exists() && tFile.isDirectory()) {
			cSavePath = tSavePath;
		} else {
			cLogger.error("SavePath_HOME设置有误！" + tSavePath);
			cSavePath = null;
		}
		
		cLogger.debug("SavePath_HOME = " + cSavePath);
	}
	
	/**
	 * 保存[非标准输出]报文，到03[交易机构代码]目录下
	 * @param pXmlDoc 非标准输出报文
	 * @param pTranCom 交易机构代码
	 * @param pName 报文文件名
	 */
	public static void save(Document pXmlDoc, String pTranCom, String pName) {
		//1481623141858[2016-12-13 05:59:01]
		long mStartMillis = System.currentTimeMillis();
		//f:/msg/13/2016/201612/20161213/
		//保存文件路径[保存路径(盘符)/msg/交易机构代码/当前日期(年/年月/年月日)]
		StringBuilder mFilePath = new StringBuilder(cSavePath)//f:/
					.append("msg/")//msg/
					.append(pTranCom).append('/')//13/
					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));//2016/201612/20161213/
		//f:/msg/13/2016/201612/20161213/
		//保存文件路径文件夹
		File mFileDir = new File(mFilePath.toString());
		//此抽象路径名表示的文件或目录不存在
		if (!mFileDir.exists()) {
			//创建此抽象路径名指定的目录，包括创建必需但不存在的父目录
			mFileDir.mkdirs();
			//此抽象路径名表示的文件或目录还是不存在[提示错误消息]
			if (!mFileDir.exists()) {
				//目录不存在，且尝试创建失败！此抽象路径名转换为一个路径名字符串
				cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
				//强制退出方法
				return;
			}
		}
		
		/**
		 * 创建文件输出流，将XML文档输出到文件输出流，关闭输出流 
		 */
		try {
			//创建一个向具有指定名称的文件中写入数据的输出文件流
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			//将XML文档输出到文件输出流
			JdomUtil.output(pXmlDoc, tFos);
			//关闭输出流 
			tFos.close();
		} catch (IOException ex) {
			//保存文件失败！[异常对象]
			cLogger.error("保存文件失败！", ex);
		}
		//1388_6_111_out.xml IO输出耗时:0.0s
		//1406_6_111_out.xml IO输出耗时:0.015s
		//1550_6_0_inSvc.xml IO输出耗时:0.021s
		//1561_11_108_in.xml IO输出耗时:0.0020s
		//1561_12_30_inSvc.xml IO输出耗时:0.0010s
		cLogger.info(pName+" IO输出耗时:"+(System.currentTimeMillis()-mStartMillis)/1000.0 + "s");
	}
	
	public static void save(byte[] pBytes, String pTranCom, String pName) {
		StringBuilder mFilePath = new StringBuilder(cSavePath)
			.append("msg/")
			.append(pTranCom).append('/')
			.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		File mFileDir = new File(mFilePath.toString());
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("保存文件失败！", ex);
		}
	}
}
