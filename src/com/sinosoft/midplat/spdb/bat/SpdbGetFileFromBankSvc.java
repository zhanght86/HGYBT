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
 * @Description: 获取回盘文件
 * @author sinosoft
 * @date 2017-4-21 下午5:22:09
 */
public abstract class SpdbGetFileFromBankSvc extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	// 子系统配置文件缓存代理。列:spdb.xml
	private final XmlConf cThisConf;

	protected final int cFuncFlag; // 交易代码

	private String ErrMsg = "";

	/**
	 * 提供一个全局访问点，只在每次对账开始时初始化， 确保在该次对账处理的整个过程中日期一致性， 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	protected Date cTranDate;

	protected String cResultMsg;

	/**
	 * 提供一个全局访问点，只在每次对账开始时重新初始化， 确保在该次对账处理的整个过程中配置一致性，
	 * 不受配置文件自动加载的影响。也就是说，本次定时任务一旦启动， 其后配置文件的修改将会在下一次批跑时生效，不影响本次。
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
		// 清空上一次结果信息
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

			// 进行日志处理
			cTranLogDB = new BatchServiceImpl().insertTranLog(tInStdXml);
			File endFile = new File(tLocalDir + "/END");
			if (!endFile.exists())
			{
				// 先去浦发下载END文件和FAIL文件，FAIL文件作为运维参考，然后根据END文件内容下载其他批量文件
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
				// 过滤文件只有符合当前FuncFlag的文件，并且本地文件不存在时才去下载
				if (matchFile(fileList.get(i), cFuncFlag))
				{
					if (!new File(tLocalDir + "/" + fileList.get(i)).exists())
					{
						cLogger.info(cThisBusiConf.getChildText(name) + "文件：" + fileList.get(i) + "匹配成功，且本地无文件，开始下载文件!");
						getFileFromWtp(tLocalDir, fileList.get(i));
					}
					else
					{
						cLogger.info("要匹配的文件：" + tLocalDir + "/" + fileList.get(i) + "已存在，直接使用！");
					}
				}
			}
			File[] tfiles = tFile.listFiles(new MyFileFilter(cFuncFlag));
			for (int i = 0; i < tfiles.length; i++)
			{
				cLogger.info("本地路径文件个数：" + tfiles.length + "文件名:" + tfiles[0].getName());
				deal(tfiles[i]); // 个性化业务处理
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// 每次跑完清空日期
		this.cTranDate = null;
		this.cLogger.info("Out SpdbGetFileFromBankSvc.run()!");
	}

	private boolean getFileFromWtp(String cLocalDir, String fileName) throws Exception
	{
		boolean tOkFlag = false;
		int count = 1;
		// 检查本地保存路径是否存在，不存在则创建目录
		File file = new File(cLocalDir);
		if (!file.isDirectory())
		{
			file.mkdir();
		}

		WtpDownloadFile download = new WtpDownloadFile();
		download.setOverWrite(true); // 默认覆盖
		// cLocalDir = cLocalDir.replace("/", "\\");本地测试使用
		download.setLocalFileDir(cLocalDir); // 设置

		while (true)
		{
			if (download.downLoadFile("14db3cf7f1", fileName) != 0)
			{
				cLogger.info("WTP下载" + fileName + "文件第" + count + "次失败!");
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
				cLogger.info("WTP下载" + fileName + "文件成功！");
				tOkFlag = true;
				break;
			}
		}

		cLogger.info("Java:TransId=[" + download.getTransId() + "]");
		// 返回值
		cLogger.info("Java:LocalFileName=[" + download.getLocalFileName() + "]"); // 返回值
		// 记录Wtp下载结果
		if (fileName.equals("END") || fileName.equals("FAIL"))
		{
			// END FAIL文件只会下载一次，第一次下载的时候新增记录。
			saveTranlog(cTranLogDB, fileName, tOkFlag);
		}
		else
		{
			cTranLogDB.setInDoc(fileName);
			if (!tOkFlag)
			{
				cTranLogDB.setRText("WTP下载" + fileName + "文件失败！");
				cTranLogDB.setRCode(CodeDef.RCode_ERROR);
			}
			else
			{
				cTranLogDB.setRText("WTP下载" + fileName + "文件成功！");
				cTranLogDB.setRCode(CodeDef.RCode_OK);
			}
			if (!cTranLogDB.update())
			{
				cLogger.info("日志更新失败!");
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
			nTranLogDB.setRText("WTP下载" + fileName + "文件失败！");
			nTranLogDB.setRCode(CodeDef.RCode_ERROR);
		}
		else
		{
			nTranLogDB.setRText("WTP下载" + fileName + "文件成功！");
			nTranLogDB.setRCode(CodeDef.RCode_OK);
		}
		if (!nTranLogDB.insert())
		{
			cLogger.info(fileName + "存表失败！");
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
	 * 方法功能:记录日志 创建日期:2012-02-23
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
			mTranLogDB.setRText("文件:" + tFileName + "下载成功");
		else
			mTranLogDB.setRText(ErrMsg);
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
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