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
 * @Description: 推送文件到银行服务类
 * @author sinosoft
 * @date 2017-4-21 下午5:43:01
 */
public abstract class SpdbPushFileToBankSvc extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	//当前配置文件
	private final XmlConf cThisConf;

	//交易代码
	private final int cFuncFlag;

	/**
	 * 交易日期:提供一个全局访问点，只在每次对账开始时初始化， 确保在该次对账处理的整个过程中日期一致性， 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	protected Date cTranDate;

	protected String cResultMsg;

	protected Element cThisConfRoot = null;

	protected Element cThisBusiConf = null;

	protected TranLogDB cTranLogDB;

	/**
	 * <p>Title: 推送文件到银行服务类构造器(Xml,String)</p>
	 * <p>Description: 初始化当前配置文件、交易代码</p>
	 * @param pThisConf
	 * @param pFuncFlag
	 */
	public SpdbPushFileToBankSvc(XmlConf pThisConf, String pFuncFlag)
	{
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}

	/**
	 * <p>Title: 推送文件到银行服务类构造器(Xml,Int)</p>
	 * <p>Description: 初始化当前配置文件、交易代码</p>
	 * @param pThisConf
	 * @param pFuncFlag
	 */
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
		// 清空上一次结果信息
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
			// 进行日志处理
			cTranLogDB = new BatchServiceImpl().insertTranLog(tInStdXml);

			String tLocalDir = cThisBusiConf.getChildTextTrim(localDir);
			File tFile = new File(tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			cLogger.info("LocalDir：" + tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			// 判断文件是否存在，存在则不调用核心，否则调用核心服务，根据不同的业务类型进行个性化定制
			try
			{
				deal(tLocalDir);
			}
			catch (Exception ex)
			{
				cLogger.info("个性化业务处理出错....");
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
				// 将文件上传到浦发服务器
				boolean tFlag = false;
				try
				{
					if (tfiles[i].getName().endsWith("txt") || tfiles[i].getName().equalsIgnoreCase("FINISH"))
					{
						cLogger.info(tfiles[i].getName() + "文件名符合规范，开始上传！");
						tFlag = upLoadFile(tfiles[i].getName(), tLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
					}
					else
					{
						cLogger.info(tfiles[i].getName() + "文件名不符合规范，不上传！");
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
					cTranLogDB.setRText(tfiles[i].getName() + "上传成功！");
					cTranLogDB.setRCode(CodeDef.RCode_OK);
					if (!cTranLogDB.update())
					{
						cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
					}
				}
				else
				{
					cTranLogDB.setOutDoc(tfiles[i].getName());
					cTranLogDB.setRText(tfiles[i].getName() + "上传失败！");
					cTranLogDB.setRCode(CodeDef.RCode_ERROR);
					if (!cTranLogDB.update())
					{
						cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// 每次跑完清空日期
		this.cTranDate = null;

		cLogger.info("Out SpdbPushFileToBankSvc.run()...");
	}

	/**
	 * @Title: upLoadFile
	 * @Description: 上传文件
	 * @param cFileName 本地文件名
	 * @param cLocalDir 本地保存文件目录
	 * @return 文件上传结果
	 * @throws Exception
	 * @return boolean
	 * @throws
	 */
	private boolean upLoadFile(String cFileName, String cLocalDir) throws Exception
	{
		//构建WTP上传文件实例
		WtpUploadFile upload = new WtpUploadFile();
		//成功标志[失败]
		boolean okFlag = false;
		//计数器[次数]
		int count = 1;
		//本地保存文件目录非/结尾
		if (!cLocalDir.endsWith("/"))
		{
			//拼接上/
			cLocalDir += "/";
		}
		// cLocalDir = cLocalDir.replace("/", "\\");// 本地测试使用
		//WTP设置上传文件本地文件名[本地保存文件目录+本地文件名]
		upload.setLocalFileName(cLocalDir + cFileName);
		//WTP设置远程文件存放目录[如果不设置文件服务器目录,则以配置文件中为准]
		upload.setRemoteDir("SCW/WTP/RECV"); 
		//WTP设置远程文件名[本地文件名]
		upload.setRemoteFileName(cFileName);
		/* 
		 * WTP设置是否续传[非续传]
		 * 非续传 ,没有这行默认为非续传,如果设置成续传(true),则必须有文件传输ID号的设置方法setTransId
		 */
		upload.setRetransFlag(false);
		//死循环
		while (true)
		{
			//上传文件失败
			if (upload.upLoadFile() != 0)
			{
				//WTP上传
				cLogger.info("WTP上传" + cFileName + "文件第" + count + "次失败!");
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
				cLogger.info("WTP上传" + cFileName + "文件成功！");
				okFlag = true;
				break;
			}
		}
		cLogger.info("Java:TransId=[" + upload.getTransId() + "]"); // 返回值
		cLogger.info("Java:RemoteFileName=[" + upload.getRemoteFileName() + "]"); // 返回值
		return okFlag;
	}

	/**
	 * @Title: getBody
	 * @Description: 获取报文体
	 * @return 报文体
	 * @return Element 报文体节点
	 * @throws
	 */
	protected Element getBody()
	{
		cLogger.info("Into SpdbPushFileToBankSvc.getBody()...");
		//交易日期
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//报文体
		Element mBody = new Element(Body);
		mBody.addContent(mTranDate);
		cLogger.info("Out SpdbPushFileToBankSvc.getBody()!");
		return mBody;
	}

	/**
	 * @Title: deal
	 * @Description: 处理
	 * @param tLocalDir 本地保存文件目录
	 * @return void
	 * @throws
	 */
	protected abstract void deal(String tLocalDir);

	/**
	 * @Title: getHead
	 * @Description: 获取报文头
	 * @return 报文头
	 * @return Element 报文头节点
	 * @throws
	 */
	protected Element getHead()
	{
		cLogger.info("Into SpdbPushFileToBankSvc.getHead()...");

		//交易日期
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(new Date(), "yyyyMMdd"));
		
		//交易时间
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(new Date(), "HHmmss"));
		
		//交易机构代码(
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		//银行代码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0117");
		
		//省市代码
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		
		//银行网点
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		//柜员代码
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		//交易流水号
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		//交易类型
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mTranCom).addContent(mBankCode)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		cLogger.info("Out SpdbPushFileToBankSvc.getHead()!");
		return mHead;
	}
}