package com.sinosoft.midplat.spdb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MyFileFilter;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.spdb.service.BatchServiceImpl;
import com.tongtech.wtp.WtpDownloadFile;

/**
 * @ClassName: SpdbGetFileFromBankSvc
 * @Description: ��ȡ�����ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:22:09
 */
public abstract class SpdbGetFileFromBankSvc extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	// ��ϵͳ�����ļ����������:spdb.xml
	private final XmlConf cThisConf;

	protected final int cFuncFlag; // ���״���

	private String ErrMsg = "";

	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ� ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;

	protected String cResultMsg;

	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ�������� ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
	 */
	protected Element cMidplatRoot = null;

	protected Element cThisConfRoot = null;

	protected Element cThisBusiConf = null;

	protected TranLogDB cTranLogDB;

	public SpdbGetFileFromBankSvc(XmlConf pThisConf, String pFuncFlag)
	{
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}

	public SpdbGetFileFromBankSvc(XmlConf pThisConf, int pFuncFlag)
	{
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}

	public final void setDate(Date pDate)
	{
		cTranDate = pDate;
	}

	public final void setDate(String p8DateStr)
	{
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}

	@SuppressWarnings("resource")
	public void run()
	{
		cLogger.info("Into SpdbGetFileFromBankSvc.run()...");
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		// �����һ�ν����Ϣ
		cResultMsg = null;
		ErrMsg = null;
		try
		{
			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cThisConfRoot = cThisConf.getConf().getRootElement();
			cThisBusiConf = (Element) XPath.selectSingleNode(cThisConfRoot, "business[funcFlag='" + cFuncFlag + "']");
			String nextDate = cThisBusiConf.getChildText("nextDate");

			if (null == cTranDate)
			{
				if (null != nextDate && "Y".equals(nextDate))
				{
					cTranDate = new Date();
					cTranDate = new Date(cTranDate.getTime() - 1000 * 3600 * 24);
				}
				else
					cTranDate = new Date();
			}

			String tLocalDir = cThisBusiConf.getChildText("localDir") + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd");

			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);

			// ������־����
			cTranLogDB = new BatchServiceImpl().insertTranLog(tInStdXml);
			File endFile = new File(tLocalDir + "/END");
			if (!endFile.exists())
			{
				// ��ȥ�ַ�����END�ļ���FAIL�ļ���FAIL�ļ���Ϊ��ά�ο���Ȼ�����END�ļ������������������ļ�
				getFileFromWtp(tLocalDir, "END");
				getFileFromWtp(tLocalDir, "FAIL");
			}
			BufferedReader endReader = new BufferedReader(new FileReader(endFile));
			String readInfo = null;
			List<String> fileList = new ArrayList<String>();
			while ((readInfo = endReader.readLine()) != null)
			{
				if (!readInfo.equals("") && !readInfo.equalsIgnoreCase("end"))
				{
					fileList.add(readInfo);
				}
			}
			File tFile = new File(tLocalDir);
			for (int i = 0; i < fileList.size(); i++)
			{
				// �����ļ�ֻ�з��ϵ�ǰFuncFlag���ļ������ұ����ļ�������ʱ��ȥ����
				if (matchFile(fileList.get(i), cFuncFlag))
				{
					if (!new File(tLocalDir + "/" + fileList.get(i)).exists())
					{
						cLogger.info(cThisBusiConf.getChildText(name) + "�ļ���" + fileList.get(i) + "ƥ��ɹ����ұ������ļ�����ʼ�����ļ�!");
						getFileFromWtp(tLocalDir, fileList.get(i));
					}
					else
					{
						cLogger.info("Ҫƥ����ļ���" + tLocalDir + "/" + fileList.get(i) + "�Ѵ��ڣ�ֱ��ʹ�ã�");
					}
				}
			}
			File[] tfiles = tFile.listFiles(new MyFileFilter(cFuncFlag));
			for (int i = 0; i < tfiles.length; i++)
			{
				cLogger.info("����·���ļ�������" + tfiles.length + "�ļ���:" + tfiles[0].getName());
				deal(tfiles[i]); // ���Ի�ҵ����
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// ÿ�������������
		this.cTranDate = null;
		this.cLogger.info("Out SpdbGetFileFromBankSvc.run()!");
	}

	private boolean getFileFromWtp(String cLocalDir, String fileName) throws Exception
	{
		boolean tOkFlag = false;
		int count = 1;
		// ��鱾�ر���·���Ƿ���ڣ��������򴴽�Ŀ¼
		File file = new File(cLocalDir);
		if (!file.isDirectory())
		{
			file.mkdir();
		}

		WtpDownloadFile download = new WtpDownloadFile();
		download.setOverWrite(true); // Ĭ�ϸ���
		// cLocalDir = cLocalDir.replace("/", "\\");���ز���ʹ��
		download.setLocalFileDir(cLocalDir); // ����

		while (true)
		{
			if (download.downLoadFile("14db3cf7f1", fileName) != 0)
			{
				cLogger.info("WTP����" + fileName + "�ļ���" + count + "��ʧ��!");
				download.printErrorMsg();
				count++;
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (count > 10)
				{
					break;
				}
			}
			else
			{
				cLogger.info("WTP����" + fileName + "�ļ��ɹ���");
				tOkFlag = true;
				break;
			}
		}

		cLogger.info("Java:TransId=[" + download.getTransId() + "]");
		// ����ֵ
		cLogger.info("Java:LocalFileName=[" + download.getLocalFileName() + "]"); // ����ֵ
		// ��¼Wtp���ؽ��
		if (fileName.equals("END") || fileName.equals("FAIL"))
		{
			// END FAIL�ļ�ֻ������һ�Σ���һ�����ص�ʱ��������¼��
			saveTranlog(cTranLogDB, fileName, tOkFlag);
		}
		else
		{
			cTranLogDB.setInDoc(fileName);
			if (!tOkFlag)
			{
				cTranLogDB.setRText("WTP����" + fileName + "�ļ�ʧ�ܣ�");
				cTranLogDB.setRCode(CodeDef.RCode_ERROR);
			}
			else
			{
				cTranLogDB.setRText("WTP����" + fileName + "�ļ��ɹ���");
				cTranLogDB.setRCode(CodeDef.RCode_OK);
			}
			if (!cTranLogDB.update())
			{
				cLogger.info("��־����ʧ��!");
			}
		}
		return tOkFlag;
	}

	private void saveTranlog(TranLogDB cTranLogDB2, String fileName, boolean tOkFlag)
	{
		TranLogDB nTranLogDB = new TranLogDB();
		nTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		nTranLogDB.setTranCom(cTranLogDB.getTranCom());
		nTranLogDB.setNodeNo(cTranLogDB.getNodeNo());
		nTranLogDB.setTranNo(cTranLogDB.getTranNo());
		nTranLogDB.setOperator("sys");
		nTranLogDB.setTranDate(cTranLogDB.getTranDate());
		nTranLogDB.setTranTime(cTranLogDB.getTranTime());
		nTranLogDB.setTranCom(cTranLogDB.getTranCom());
		nTranLogDB.setUsedTime(0);
		nTranLogDB.setMakeDate(DateUtil.getCur8Date());
		nTranLogDB.setMakeTime(DateUtil.getCur6Time());
		nTranLogDB.setModifyDate(DateUtil.getCur8Date());
		nTranLogDB.setModifyTime(DateUtil.getCur6Time());
		nTranLogDB.setInDoc(fileName);
		nTranLogDB.setFuncFlag(3000);
		if (!tOkFlag)
		{
			nTranLogDB.setRText("WTP����" + fileName + "�ļ�ʧ�ܣ�");
			nTranLogDB.setRCode(CodeDef.RCode_ERROR);
		}
		else
		{
			nTranLogDB.setRText("WTP����" + fileName + "�ļ��ɹ���");
			nTranLogDB.setRCode(CodeDef.RCode_OK);
		}
		if (!nTranLogDB.insert())
		{
			cLogger.info(fileName + "���ʧ�ܣ�");
		}
	}

	protected abstract void deal(File cLocalFile);

	protected Element getHead()
	{
		cLogger.info("Into SpdbGetFileFromBankSvc.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0117");

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBankCode);
		cLogger.info("Out SpdbGetFileFromBankSvc.getHead()!");
		return mHead;
	}

	/**
	 * ��������:��¼��־ ��������:2012-02-23
	 * 
	 * @return TranLogDB
	 * @throws MidplatException
	 */
	public void insertBatTranLog(String tFileName, int cFuncflag, int cRcode) throws MidplatException
	{
		cLogger.info("Into SpdbGetFileFromBankSvc.insertBatTranLog()...");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(String.valueOf(NoFactory.nextTranLogNo()));
		mTranLogDB.setTranCom(cThisConfRoot.getChildText(TranCom));
		mTranLogDB.setNodeNo("98006000");
		mTranLogDB.setTranNo(Thread.currentThread().getName());
		mTranLogDB.setFuncFlag(cFuncflag);
		Date mCurDate = new Date();
		mTranLogDB.setTranDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(cRcode);
		mTranLogDB.setUsedTime(0);
		mTranLogDB.setOperator("sys");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setModifyTime(DateUtil.get6Time(mCurDate));
		if (null == ErrMsg || ErrMsg.equals(""))
			mTranLogDB.setRText("�ļ�:" + tFileName + "���سɹ�");
		else
			mTranLogDB.setRText(ErrMsg);
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}

		cLogger.info("Out SpdbGetFileFromBankSvc.insertBatTranLog()!");
	}

	private boolean matchFile(String fileName, int mFuncFlag)
	{
		if (mFuncFlag == 3007)
		{
			if (fileName.startsWith("UBS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3008)
		{
			if (fileName.startsWith("UBCS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3012)
		{
			if (fileName.startsWith("UZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3013)
		{
			if (fileName.startsWith("UBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3015)
		{
			if (fileName.startsWith("UBD"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3011)
		{
			if (fileName.startsWith("DBCS"))
			{
				return true;
			}
			return false;
		}
		if (mFuncFlag == 3010)
		{
			if (fileName.startsWith("DBS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3009)
		{
			if (fileName.startsWith("DZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3014)
		{
			if (fileName.startsWith("DBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3016)
		{
			if (fileName.startsWith("DBD"))
			{
				return true;
			}
			return false;
		}
		else
			return false;
	}
}