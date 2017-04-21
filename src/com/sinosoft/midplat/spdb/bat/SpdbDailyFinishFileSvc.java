package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.spdb.SpdbConf;

/**
 * @ClassName: SpdbDailyFinishFileSvc
 * @Description: FINISH结束文件
 * @author sinosoft
 * @date 2017-4-21 下午5:18:20
 */
public class SpdbDailyFinishFileSvc extends SpdbPushFileToBankSvc
{

	public SpdbDailyFinishFileSvc()
	{
		super(SpdbConf.newInstance(), "3017");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbDailyFinishFileSvc.deal()...");
		try
		{
			// list本地生产的文件，汇总为Finish文件，并发送给浦发
			long mStartMillis = System.currentTimeMillis();
			cLogger.info("本地文件路径：" + ttLocalDir);
			File tFileDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			File tFinishFile = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "/" + "FINISH");
			String fileName = "";
			if (tFinishFile.exists())
			{
				tFinishFile.delete();
			}
			File[] fileList = tFileDir.listFiles();
			String tFileNameList = "";
			for (int i = 0; i < fileList.length; i++)
			{
				fileName = fileList[i].getName();
				if (fileName.endsWith("txt"))
				{
					tFileNameList += fileName + "\n";
				}
			}
			tFileNameList += "finish" + "\n";
			byte[] m = tFileNameList.getBytes();
			try
			{
				FileOutputStream fos = new FileOutputStream(tFinishFile);
				fos.write(m);
				fos.flush();
				fos.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
				cLogger.error("找不到文件");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				cLogger.error("I/O异常");
			}
			cTranLogDB.setRCode(AblifeCodeDef.RCode_OK);
			cTranLogDB.setBak2("生成FINISH文件成功...");
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out SpdbDailyFinishFileSvc.deal()...");
	}

	public static void main(String args[]) throws MidplatException
	{
		SpdbDailyFinishFileSvc tBlc = new SpdbDailyFinishFileSvc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbDailyFinishFileSvc.class);

		mLogger.info("浦发FINISH上传批处理程序开始...");
		// args = new String[1];
		// args[0]="20160121";
		// 用于补FINISH上传批处理，设置补FINISH上传批处理日期
		if (0 != args.length)
		{
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 *
			 * 简单日期校验的正则表达式：\\d{4}\\d{2}\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))"))
			{
				tBlc.setDate(args[0]);
				tBlc.run();
			}
			else
			{
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		mLogger.info("浦发FINISH上传批处理成功结束！");
	}
}
