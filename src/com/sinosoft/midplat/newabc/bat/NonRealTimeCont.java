/**
 * 新农行非实时出单结果文件
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import sun.security.util.BigInt;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.bat.GetSFTPConnection;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.format.NonRealTimeContOutXsl;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import java.lang.reflect.Constructor;

public class NonRealTimeCont extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	// 子系统配置文件缓存代理。列:icbc.xml
	private final XmlConf cThisConf;
	private final int cFuncFlag; // 交易代码
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

	private String stranNo = null;
	private String curDate = null;
	private String fileDate = null;

	public NonRealTimeCont()
	{
		this(NewAbcConf.newInstance(), 2008);
	}

	public NonRealTimeCont(XmlConf pThisConf, int pFuncFlag)
	{
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}

	protected String getFileName()
	{
		String tTranNo = sendRequest().getRootElement().getChild("Head").getChildText("TranNo");
		return tTranNo;
	}

	protected String getCoreNum()
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu");
	}

	protected Element getHead()
	{
		cLogger.info("Into NonRealTimeCont.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild("NodeNo").clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());

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

		cLogger.info("Out NonRealTimeCont.getHead()!");
		return mHead;
	}

	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into NonRealTimeCont.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 处理核心返回报文
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRst).call(cInXmlDoc);

			cLogger.info("核心返回的非实时对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc = NonRealTimeContOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("转换后的对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			receive(cOutXmlDoc, ttLocalDir);

			getFileName2();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out NonRealTimeCont.deal()...");
	}

	/**
	 * 设置对账文件名，处理每个月最后一天生成的文件名为下个月第一天
	 */
	protected String dealFileName() throws Exception
	{

		// 设置生成文件日期，一定注意每个月最后一天的日期，与农行高绍洁约定，
		// 当日生成的退保犹撤数据文件和非实时出单结果文件的文件名为次日，农行会在次日处理
		// curDate=当前日期
		// fileDate=生成文件日期
		// 20141217
		String gfileDate = "";
		Calendar c = Calendar.getInstance();
		curDate = String.valueOf(DateUtil.get8Date(c.getTime()));
		System.out.println("今天是：" + curDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(new Date());
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		System.out.println("最后一天：" + DateUtil.get8Date(lastDate));

		if (String.valueOf(DateUtil.get8Date(lastDate)) == curDate || String.valueOf(DateUtil.get8Date(lastDate)).equals(curDate))
		{
			System.out.println("今天是本月最后一天");
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			gfileDate = String.valueOf(DateUtil.get8Date(c.getTime()));
			System.out.println("下个月的第一天: " + gfileDate);
			System.out.println("今天的文件日期：" + gfileDate);
		}
		else
		{
			System.out.println("今天不是本月最后一天");

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			gfileDate = String.valueOf(DateUtil.date10to8((sdf.format(date))));
			System.out.println("今天生成的文件日期：" + gfileDate);
		}
		return gfileDate;
	}

	/**
	 * 获取对账文件名
	 */
	protected String getFileName2() throws Exception
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf, "FSSCDJGWJ", fileDate, mBankEle.getAttributeValue("insu"));
		String fileName = "FRESULT" + mBankEle.getAttributeValue("insu") + "." + fileDate;
		try
		{
			f.bank_dz_file();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
		return fileName;
	}

	/**
	 * 
	 * sendRequest 组织核心请求报文
	 *
	 * @return
	 */
	private Document sendRequest()
	{
		cLogger.info("Into NonRealTimeCont.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Head = new ElementLis("Head", TranData);
		int date = DateUtil.get8Date(System.currentTimeMillis());
		int time = DateUtil.get6Time(System.currentTimeMillis());
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		ElementLis TranDate = new ElementLis("TranDate", trantime.substring(0, 8), Head);
		ElementLis TranTime = new ElementLis("TranTime", trantime.substring(8, 14), Head);
		ElementLis TranCom = new ElementLis("TranCom", "05", Head);
		ElementLis ZoneNo = new ElementLis("ZoneNo", "11", Head);
		ElementLis NodeNo = new ElementLis("NodeNo", cThisBusiConf.getChildText("NodeNo"), Head);
		ElementLis BankCode = new ElementLis("BankCode", "0102", Head);
		ElementLis TellerNo = new ElementLis("TellerNo", "11010102110", Head);
		PubFun1 p = new PubFun1();
		stranNo = p.CreateMaxNo("TransNo", 16);
		ElementLis TranNo = new ElementLis("TranNo", stranNo, Head);
		ElementLis FuncFlag = new ElementLis("FuncFlag", cThisBusiConf.getChildText("funcFlag"), Head);// 交易代码
		ElementLis Body = new ElementLis("Body", TranData);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out NonRealTimeCont.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	// 处理核心返回报文并存储在固定路径 20140911
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception
	{
		cLogger.info("Into NonRealTimeCont.receive()..." + cTranDate);
		JdomUtil.print(cOutXmlDoc);

		fileDate = String.valueOf(DateUtil.get8Date(cTranDate));

		String outHead = null;// 头记录
		String serialNo = "";// 序号
		String fileName = "";// 文件名称
		String out = "";
		String outBody = "";
		int maxNo = 100;// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可
		// 头记录处理
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot + "           ===========      " + tHead);
		String tFlag = tHead.getChildText("Flag");// 交易成功标志
		String tComCode = cThisConfRoot.getChild("bank").getAttributeValue("insu");// 保险公司代码
		String tFuncFlag = cThisBusiConf.getChildText("funcFlag");// 交易代码
		String tCount = null;// 明细记录总笔数
		String tSumMoney = null;// 总金额
		Element tBody = tRoot.getChild("Body");
		// try {

		List<Element> list = tBody.getChildren("Detail");

		tCount = Integer.toString(list.size());
		tSumMoney = "0000";
		long sum = 0;

		cLogger.info("生成返回文件头记录" + outHead);
		cLogger.info("生成返回文件总记录" + list.size());

		/*
		 * 文件第一行：（汇总信息） 格式：保险公司代码|银行代码|总记录数|总金额| 文件其他内容：（明细记录）
		 * 交易日期|试算申请顺序号|投保人姓名
		 * |投保人证件类型|投保人证件号码|险种编码|产品编码|保单号|受理日期|投保人与被保人关系|被保人姓名|
		 * 被保人证件类型|被保人证件号码|保费|保额|缴费账户|缴费方式|缴费期限|保单到期日|投保分数|个性化费率|保单印刷号|附加险个数|
		 */
		if (list.size() != 0)
		{
			/*
			 * 通过总数和最大数取得所能生成文件的数量
			 */
			for (int i = 0; i <= list.size() / maxNo; i++)
			{
				if (i < 10)
				{
					serialNo = "0" + Integer.toString(i);

					fileName = "FRESULT" + tComCode + "." + fileDate;
				}
				else
				{
					serialNo = Integer.toString(i);
					// fileName =
					// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "FRESULT" + tComCode + "." + fileDate;
				}
				File file = new File(ttLocalDir + "/" + fileName);
				// 如果文件不存在穿件文件
				if (!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				/*
				 * 此循环是向第i个文件中流出(i+1)*maxNo - i*maxNo个明细记录
				 * 第i个文件里取的明细记录是从第i*maxNo个至第(i+1)*maxNo个, 当取的值和总数相同时，则跳出此循环
				 */
				for (int j = i * maxNo; j < (i + 1) * maxNo; j++)
				{
					if (j == list.size())
					{
						break;
					}
					Element tDetail = list.get(j);
					String tTranDate = tDetail.getChildText("TranDate");
					tTranDate = DateUtil.date10to8(tTranDate);
					String tApplyNo = tDetail.getChildText("ApplyNo");
					String tAppName = tDetail.getChild("Appnt").getChildText("AppntName");
					String tAppIDtype = tDetail.getChild("Appnt").getChildText("IDType");
					String tAppIDno = tDetail.getChild("Appnt").getChildText("IDNo");
					String tRiskCode = tDetail.getChildText("RiskCode");
					String tProdCode = tDetail.getChildText("ProdCode");
					String tContNo = tDetail.getChildText("ContNo");
					String tApplyDate = tDetail.getChildText("ApplyDate");
					tApplyDate = DateUtil.date10to8(tApplyDate);
					String tRelationToInusre = tDetail.getChildText("RelationToInusre");
					String tInsuName = tDetail.getChild("Insured").getChildText("Name");
					String tInsuDtype = tDetail.getChild("Insured").getChildText("IDType");
					String tInsuDno = tDetail.getChild("Insured").getChildText("IDNo");
					String tPrem = tDetail.getChildText("Prem");
					// 处理金额
					long prem = Long.parseLong(tPrem);
					sum = sum + prem;
					tPrem = NumberUtil.fenToYuan(tPrem);
					String tAmnt = tDetail.getChildText("Amnt");
					tAmnt = NumberUtil.fenToYuan(tAmnt);
					String tAccNo = tDetail.getChildText("AccNo");
					String tPayIntv = tDetail.getChildText("PayIntv");
					String tPayEndDate = tDetail.getChildText("PayEndDate");
					tPayEndDate = DateUtil.date10to8(tPayEndDate);
					String tContEndDate = tDetail.getChildText("ContEndDate");
					tContEndDate = DateUtil.date10to8(tContEndDate);
					String tMult = tDetail.getChildText("Mult");
					double dmult = Double.parseDouble(tMult);
					int imult = (int) dmult;
					tMult = String.valueOf(imult);
					String tContPrtNo = tDetail.getChildText("ContPrtNo");
					String tSpecialRate = tDetail.getChildText("SpecialRate");
					String tEtraRiskCnt = tDetail.getChildText("EtraRiskCnt");

					outBody += tTranDate + "|" + tApplyNo + "|" + tAppName + "|" + tAppIDtype + "|" + tAppIDno + "|" + tRiskCode + "|" + tProdCode + "|" + tContNo + "|" + tApplyDate + "|" + tRelationToInusre + "|" + tInsuName + "|" + tInsuDtype + "|" + tInsuDno + "|" + tPrem + "|" + tAmnt + "|" + tAccNo + "|" + tPayIntv + "|" + tPayEndDate + "|" + tContEndDate + "|" + tMult + "|" + tSpecialRate + "|" + tContPrtNo + "|" + tEtraRiskCnt + "|" + "\n";
				}
				// 输出记录至txt文件
				tSumMoney = String.valueOf(sum);
				tSumMoney = NumberUtil.fenToYuan(tSumMoney);
				System.out.println("总金额：" + tSumMoney);
				if (tSumMoney.equals("") || tSumMoney == null)
				{
					tSumMoney = "0.00";
				}
				outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
				out = outHead + outBody;
				cLogger.info("第" + i + "个文件生成返回文件总记录" + out);
				byte[] m = out.getBytes("GBK");
				try
				{
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				}
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					cLogger.error("找不到文件");
				}
				catch (IOException e)
				{
					e.printStackTrace();
					cLogger.error("I/O异常");
				}
				// 删除文件，避免生成多余文件
				if (i * maxNo == list.size())
				{
					file.delete();
				}
				// 清空明细记录和总记录
				outBody = "";
				out = "";
			}
		}
		else
		{
			// fileName =
			// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
			fileName = "FRESULT" + tComCode + "." + fileDate;
			File file = new File(ttLocalDir + "/" + fileName);
			// 如果文件不存在创建文件
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			// 输出记录至txt文件
			tCount = "0";
			tSumMoney = "0.00";
			outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
			out = outHead;
			cLogger.info("只返回头文件，核心未返回退保值" + out);
			byte[] m = out.getBytes("GBK");
			try
			{
				FileOutputStream fos = new FileOutputStream(file);
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
		}

		System.out.println("文件名：" + fileName);
		cLogger.info("Out NonRealTimeCont.receive()...");
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeCont.run()...");

		// 清空上一次结果信息
		cResultMsg = null;

		try
		{

			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			System.out.println(cThisConf.getConf().getRootElement().getChildText("TranCom"));
			cThisConfRoot = cThisConf.getConf().getRootElement();
			System.out.println(cThisConfRoot);
			System.out.println(cFuncFlag);
			cThisBusiConf = (Element) XPath.selectSingleNode(cThisConfRoot, "business[funcFlag='" + cFuncFlag + "']");
			JdomUtil.print(cThisBusiConf);
			System.out.println(cThisBusiConf.getContent());
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
			System.out.println("====此处打印：发给核心的报文====");
			JdomUtil.print(tInStdXml);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);

			try
			{
				String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);

				deal(ttLocalDir);

			}
			catch (Exception ex)
			{
				cLogger.error("生成标准对账报文出错！", ex);

				// 获取标准对账报文
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			// 调用业务处理，获取标准返回报文
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			// 若midplat.xml中有非空默认配置，采用该配置
			String tServiceValue = cMidplatRoot.getChildText(service);
			if (null != tServiceValue && !"".equals(tServiceValue))
			{
				tServiceClassName = tServiceValue;
			}
			// 若子系统的个性化配置文件中有非空默认配置，采用该配置
			tServiceValue = cThisConfRoot.getChildText(service);
			if (null != tServiceValue && !"".equals(tServiceValue))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText(service);
			if (null != tServiceValue && !"".equals(tServiceValue))
			{
				tServiceClassName = tServiceValue;
			}
			cLogger.info("业务处理模块：" + tServiceClassName);
			Constructor tServiceConstructor = (Constructor<Service>) Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { cThisBusiConf });
			System.out.println("看看是啥：");
			JdomUtil.print(tInStdXml);
			Document tOutStdXml = tService.service(tInStdXml);

			cResultMsg = tOutStdXml.getRootElement().getChild(Head).getChildText(Desc);

			// 每月1日备份上月的对账文件
			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd")))
			{
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
		}
		catch (Throwable ex)
		{
			cLogger.error("交易出错！", ex);
			cResultMsg = ex.toString();
		}

		cTranDate = null; // 每次跑完，清空日期

		cLogger.info("Out NonRealTimeCont.run()!");
	}

	// 备份月文件，比如20141201当日，系统会在目录localDir 建立/2014/201411，
	// 然后把所有文件移动到该目录，但是20141201的文件除外
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into Balance.bakFiles()...");

		if (null == pFileDir || "".equals(pFileDir))
		{
			cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory())
		{
			cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
			return;
		}

		File[] mOldFiles = mDirFile.listFiles(new FileFilter()
		{
			public boolean accept(File pFile)
			{
				if (!pFile.isFile())
				{
					return false;
				}

				Calendar tCurCalendar = Calendar.getInstance();
				tCurCalendar.setTime(cTranDate);

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
			try
			{
				String fileName_date = tFile.getName().substring(tFile.getName().length() - 8);
				Date date = new Date();
				if (!fileName_date.equals(String.valueOf(DateUtil.get8Date(date))))
				{
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			}
			catch (IOException ex)
			{
				cLogger.error(tFile.getAbsoluteFile() + "备份失败！", ex);
			}
		}

		cLogger.info("Out Balance.bakFiles()!");
	}
}
