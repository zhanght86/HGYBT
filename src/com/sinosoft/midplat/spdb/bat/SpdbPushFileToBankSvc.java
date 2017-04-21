package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MyFileFilter;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.spdb.service.BatchServiceImpl;
import com.tongtech.wtp.WtpUploadFile;

/**
 * @ClassName: SpdbPushFileToBankSvc
 * @Description: �����ļ������з�����
 * @author sinosoft
 * @date 2017-4-21 ����5:43:01
 */
public abstract class SpdbPushFileToBankSvc extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	private final XmlConf cThisConf;

	private final int cFuncFlag; // ���״���

	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ� ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;

	protected String cResultMsg;

	protected Element cThisConfRoot = null;

	protected Element cThisBusiConf = null;

	protected TranLogDB cTranLogDB;

	public SpdbPushFileToBankSvc(XmlConf pThisConf, String pFuncFlag)
	{
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}

	public SpdbPushFileToBankSvc(XmlConf pThisConf, int pFuncFlag)
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

	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into SpdbPushFileToBankSvc.run()...");
		// �����һ�ν����Ϣ
		cResultMsg = null;
		try
		{
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

			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			Element tBody = getBody();
			tTranData.addContent(tBody);
			// ������־����
			cTranLogDB = new BatchServiceImpl().insertTranLog(tInStdXml);

			String tLocalDir = cThisBusiConf.getChildTextTrim(localDir);
			File tFile = new File(tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			cLogger.info("LocalDir��" + tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			// �ж��ļ��Ƿ���ڣ������򲻵��ú��ģ�������ú��ķ��񣬸��ݲ�ͬ��ҵ�����ͽ��и��Ի�����
			try
			{
				deal(tLocalDir);
			}
			catch (Exception ex)
			{
				cLogger.info("���Ի�ҵ�������....");
				ex.printStackTrace();
			}

			File[] tfiles = tFile.listFiles(new MyFileFilter(cFuncFlag));
			for (int i = 0; i < tfiles.length; i++)
			{
				if (i != 0)
				{
					cTranLogDB.setLogNo(NoFactory.nextTranLogNo());
					cTranLogDB.insert();
				}
				// ���ļ��ϴ����ַ�������
				boolean tFlag = false;
				try
				{
					if (tfiles[i].getName().endsWith("txt") || tfiles[i].getName().equalsIgnoreCase("FINISH"))
					{
						cLogger.info(tfiles[i].getName() + "�ļ������Ϲ淶����ʼ�ϴ���");
						tFlag = upLoadFile(tfiles[i].getName(), tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
					}
					else
					{
						cLogger.info(tfiles[i].getName() + "�ļ��������Ϲ淶�����ϴ���");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					tFlag = false;
				}

				if (tFlag)
				{
					cTranLogDB.setOutDoc(tfiles[i].getName());
					cTranLogDB.setRText(tfiles[i].getName() + "�ϴ��ɹ���");
					cTranLogDB.setRCode(CodeDef.RCode_OK);
					if (!cTranLogDB.update())
					{
						cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
					}
				}
				else
				{
					cTranLogDB.setOutDoc(tfiles[i].getName());
					cTranLogDB.setRText(tfiles[i].getName() + "�ϴ�ʧ�ܣ�");
					cTranLogDB.setRCode(CodeDef.RCode_ERROR);
					if (!cTranLogDB.update())
					{
						cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// ÿ�������������
		this.cTranDate = null;

		cLogger.info("Out SpdbPushFileToBankSvc.run()...");
	}

	private boolean upLoadFile(String cFileName, String cLocalDir) throws Exception
	{
		WtpUploadFile upload = new WtpUploadFile();
		boolean okFlag = false;
		int count = 1;
		if (!cLocalDir.endsWith("/"))
		{
			cLocalDir += "/";
		}
		// cLocalDir = cLocalDir.replace("/", "\\");// ���ز���ʹ��
		upload.setLocalFileName(cLocalDir + cFileName);
		upload.setRemoteDir("SCW/WTP/RECV"); // ����������ļ�������Ŀ¼,���������ļ���Ϊ׼
		upload.setRemoteFileName(cFileName);
		/* ������ ,û������Ĭ��Ϊ������,������ó�����(true),��������ļ�����ID�ŵ����÷���setTransId */
		upload.setRetransFlag(false);
		while (true)
		{
			if (upload.upLoadFile() != 0)
			{
				cLogger.info("WTP�ϴ�" + cFileName + "�ļ���" + count + "��ʧ��!");
				upload.printErrorMsg();
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
				cLogger.info("WTP�ϴ�" + cFileName + "�ļ��ɹ���");
				okFlag = true;
				break;
			}
		}
		cLogger.info("Java:TransId=[" + upload.getTransId() + "]"); // ����ֵ
		cLogger.info("Java:RemoteFileName=[" + upload.getRemoteFileName() + "]"); // ����ֵ
		return okFlag;
	}

	protected Element getBody()
	{
		cLogger.info("Into SpdbPushFileToBankSvc.getBody()...");
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mBody = new Element(Body);
		cLogger.info("Out SpdbPushFileToBankSvc.getBody()!");
		mBody.addContent(mTranDate);
		return mBody;
	}

	protected abstract void deal(String tLocalDir);

	protected Element getHead()
	{
		cLogger.info("Into SpdbPushFileToBankSvc.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(new Date(), "yyyyMMdd"));
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(new Date(), "HHmmss"));
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0117");
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBankCode);

		cLogger.info("Out SpdbPushFileToBankSvc.getHead()!");
		return mHead;
	}
}