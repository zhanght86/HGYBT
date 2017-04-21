package com.sinosoft.midplat.spdb.bat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ExeSQL;

public abstract class ToBankBalance extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	// ��ϵͳ�����ļ����������:abc.xml
	private final XmlConf cThisConf;

	private final int cFuncFlag; // ���״���

	protected String cTranName;

	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ� ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;

	protected TranLogDB cTranLogDB = null;

	protected String cResultMsg;

	protected String cTranCom;

	protected Document outSvcDoc;

	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ�������� ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
	 */

	protected Element cThisConfRoot = null;

	protected Element cThisBusiConf = null;

	public ToBankBalance(XmlConf pThisConf, String pFuncFlag)
	{
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}

	public ToBankBalance(XmlConf pThisConf, int pFuncFlag)
	{
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	public abstract Document parse(InputStream is);

	public abstract void dealPrivateBusi(String cTranCom, Date cTransDate);

	public abstract Document getBalanceFile(Date cTranDate) throws Exception;

	@SuppressWarnings("rawtypes")
	public void run()
	{
		long mStartMillis = System.currentTimeMillis();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into ToBankBalance.run()...");
		String sFlag = "0";
		// �����һ�ν����Ϣ
		cResultMsg = null;
		try
		{
			// ��鵱���Ƿ������ý���
			checkTranLog();

			cThisConfRoot = cThisConf.getConf().getRootElement();
			cTranCom = cThisConfRoot.getChildText("TranCom");

			cThisBusiConf = (Element) XPath.selectSingleNode(cThisConfRoot, "business[funcFlag='" + cFuncFlag + "']");
			cTranName = cThisBusiConf.getChildText("name");

			String ttFileName = getFileName(cTranDate);
			String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);

			Document tInStdDoc = null;
			if (getLocalFile(ttLocalDir, ttFileName))
			{
				cLogger.info(cTranName + "�����ļ����ڣ�·���ǣ�" + ttLocalDir + ttFileName);
				tInStdDoc = creatInStdDoc(ttLocalDir, ttFileName);
			}
			else
			{// ����ļ����ھ�ȥ�����ļ�����·��ȡ�ļ�����������ھ�ȥ���з��������ض����ļ�
				cLogger.info(cTranName + "�����ļ������ڣ���ʼ�����ļ�!");
				tInStdDoc = getBalanceFile(cTranDate);
			}

			cLogger.info(cTranName + "ת��֮��ı�׼���˱���!");
			JdomUtil.print(tInStdDoc);

			String tServiceClassName = this.cThisBusiConf.getChildText(service);
			this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);

			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[]
			{ Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[]
			{ this.cThisBusiConf });
			outSvcDoc = tService.service(tInStdDoc);

			Element tOutHeadEle = outSvcDoc.getRootElement().getChild(Head);

			sFlag = tOutHeadEle.getChildText(Flag);

			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			else if (2 == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				cResultMsg = tOutHeadEle.getChildText(Desc);
			}
			else
			{
				// ���ɷ��ظ����ж����ļ�������Ӧ������ʵ�֡�
				if (createFile(outSvcDoc, DateUtil.get8Date(cTranDate)))
				{
					cLogger.info("����" + cTranName + "�ļ��ɹ���");
				}
				else
				{
					cLogger.info("����" + cTranName + "�ļ�ʧ�ܣ�");
				}
			}
			// ����ж��˽�������Ҫ�������ϴ����˽����������ʵ��
			dealPrivateBusi(cTranCom, cTranDate);

			// ÿ��1�ձ������µĶ����ļ�
			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd")))
			{
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
			cResultMsg = cTranName + "�ɹ���";
		}
		catch (Exception ex)
		{
			cLogger.error("���׳���", ex);
			sFlag = "1";
			cResultMsg = ex.getMessage();
		}

		if (null != cTranLogDB)
		{ // ������־ʧ��ʱtTranLogDB=null
			cTranLogDB.setRText(cResultMsg);
			cTranLogDB.setRCode(sFlag);
			if ("2".equals(sFlag))
			{
				cTranLogDB.setRCode("0");
			}
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis));
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cTranDate = null; // ÿ�����꣬�������

		cLogger.info("Out ToBankBalance.run()!");
	}

	private boolean getLocalFile(String pDir, String pName) throws MidplatException
	{
		cLogger.info("Into ToBankBalance.getLocalFile()...");

		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/"))
		{
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);

		File tFile = new File(mPathName);
		if (tFile.exists())
		{
			return true;
		}
		return false;
	}

	protected void checkTranLog() throws MidplatException
	{
		cLogger.info("Into ToBankBalance.checkTranLog()...");
		if (null == cTranDate)
		{
			cTranDate = new Date();
			cTranDate = new Date(cTranDate.getTime());

			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK).append(" and TranDate=")
					.append(DateUtil.get8Date(cTranDate)).append(" and FuncFlag=").append(cFuncFlag).append(" and TranCom=").append(cTranCom)
					.toString();
			ExeSQL tExeSQL = new ExeSQL();

			if (tExeSQL.execSQL(tSqlStr).MaxRow > 0)
			{
				throw new MidplatException("�ѳɹ�����" + cTranName + "�����������ظ�������");
			}
		}
		else
		{
			cLogger.info("�����˲�����У��...");
		}
		cLogger.info("Out ToBankBalance.checkTranLog()......");
	}

	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into ToBankBalance.bakFiles()...");

		if (null == pFileDir || "".equals(pFileDir))
		{
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory())
		{
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
			return;
		}

		File[] mOldFiles = mDirFile.listFiles(new FileFilter()
		{
			@SuppressWarnings("static-access")
			public boolean accept(File pFile)
			{
				if (!pFile.isFile())
				{
					return false;
				}

				Calendar tCurCalendar = Calendar.getInstance();
				tCurCalendar.setTime(cTranDate);

				/* ��ֹ������ʱ���ÿ��1�ŵ��ļ�Ҳ���� */
				tCurCalendar.set(tCurCalendar.HOUR, 0);
				tCurCalendar.set(tCurCalendar.MINUTE, 0);
				tCurCalendar.set(tCurCalendar.SECOND, 0);

				Calendar tFileCalendar = Calendar.getInstance();
				tFileCalendar.setTime(new Date(pFile.lastModified()));

				return tFileCalendar.before(tCurCalendar);
			}
		});

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles)
		{
			cLogger.info(tFile.getAbsoluteFile() + " start move...");
			try
			{
				IOTrans.fileMove(tFile, mNewDir);
				cLogger.info(tFile.getAbsoluteFile() + " end move!");
			}
			catch (IOException ex)
			{
				cLogger.error(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
			}
		}

		cLogger.info("Out ToBankBalance.bakFiles()!");
	}

	public final void setDate(Date pDate)
	{
		cTranDate = pDate;
	}

	public final void setDate(String p8DateStr)
	{
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}

	protected Document creatInStdDoc(String ttLocalDir, String ttFileName) throws Exception
	{
		cLogger.info("Into ToBankBalance.creatInStdDoc()...");
		
		InputStream tins = new FileInputStream(new File(ttLocalDir + ttFileName));

		cLogger.info("Out ToBankBalance.creatInStdDoc()......");
		return parse(tins);

	}

	/**
	 * ���ݶ����ļ������������ɵ�������ļ���
	 */
	protected abstract String getFileName(Date cTranDate);

	/**
	 * ���ݶ����ļ������������ɵ�������ļ���
	 * 
	 * @throws Exception
	 */
	protected String encode(String ttFileName) throws Exception
	{
		return ttFileName;
	}

	/**
	 * �÷������ڵ������̨����֮�󱻵��á� Ĭ���ǿ�ʵ�֣���һЩ���⽻���п���������һЩ���Ի��Ķ����������
	 * ��������������з�ʵʱ�˱����ˣ��ڵ������̨�󣬽����ļ��ϴ������������ڴ˷�����ʵ�֡�
	 * 
	 * @param Document
	 *            pOutStdXml: ��̨���صı�׼���ġ�
	 */
	protected void ending(Document pOutStdXml) throws Exception
	{
		cLogger.info("Into ToBankBalance.ending()...");

		cLogger.info("do nothing, just out!");

		cLogger.info("Out ToBankBalance.ending()!");
	}

	public String getResultMsg()
	{
		return cResultMsg;
	}

	/**
	 * ��������:��¼��־ ��������:2012-02-23
	 * 
	 * @return TranLogDB
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog() throws MidplatException
	{
		cLogger.info("Into ToBankBalance.insertTranLog()...");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		mTranLogDB.setTranCom(cTranCom);
		// ���б�ȫ���ݻش����� �ļ������ȴ��ڱ��ֶζ���ĳ��ȣ�30�����ʴ˴�����ȡ������������û��Ӱ�졣
		mTranLogDB.setTranNo(Thread.currentThread().getName());
		mTranLogDB.setNodeNo(cThisBusiConf.getChildText(NodeNo));
		mTranLogDB.setZoneNo("-");
		mTranLogDB.setFuncFlag(cFuncFlag);
		Date mCurDate = new Date();
		mTranLogDB.setTranDate(DateUtil.get8Date(cTranDate));
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setOperator("sys");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());

		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}

		cLogger.info("Out ToBankBalance.insertTranLog()!");
		return mTranLogDB;
	}

	/**
	 * InputStream --> byte[]
	 */
	public static byte[] IsToBytes(InputStream pIns)
	{
		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();

		try
		{
			byte[] tBytes = new byte[8 * 1024];
			for (int tReadSize; -1 != (tReadSize = pIns.read(tBytes));)
			{
				mByteArrayOutputStream.write(tBytes, 0, tReadSize);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				pIns.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		return mByteArrayOutputStream.toByteArray();
	}

	/**
	 * ���ɶ����ļ�
	 */
	public boolean createFile(Document cOutXmlDoc, int date)
	{
		return true;
	}
}
