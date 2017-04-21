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
 * @Description: FINISH�����ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:18:20
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
			// list�����������ļ�������ΪFinish�ļ��������͸��ַ�
			long mStartMillis = System.currentTimeMillis();
			cLogger.info("�����ļ�·����" + ttLocalDir);
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
				cLogger.error("�Ҳ����ļ�");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				cLogger.error("I/O�쳣");
			}
			cTranLogDB.setRCode(AblifeCodeDef.RCode_OK);
			cTranLogDB.setBak2("����FINISH�ļ��ɹ�...");
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
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

		mLogger.info("�ַ�FINISH�ϴ����������ʼ...");
		// args = new String[1];
		// args[0]="20160121";
		// ���ڲ�FINISH�ϴ����������ò�FINISH�ϴ�����������
		if (0 != args.length)
		{
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 *
			 * ������У���������ʽ��\\d{4}\\d{2}\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))"))
			{
				tBlc.setDate(args[0]);
				tBlc.run();
			}
			else
			{
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		mLogger.info("�ַ�FINISH�ϴ�������ɹ�������");
	}
}
