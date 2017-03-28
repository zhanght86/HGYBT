package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.service.Service;

/**
 * 非实时出单结果文件-银行处理结果回盘
 * 
 * @author liuzk
 * 
 */
public class NonReaTimeIssResDocBankDeal extends Balance
{
	public NonReaTimeIssResDocBankDeal()
	{
		super(NewAbcConf.newInstance(), "2007");
	}

	protected String getFileName() throws Exception
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf, "FSSCDHP", DateUtil.getDateStr(cTranDate, "yyyyMMdd"), mBankEle.getAttributeValue("insu"));
		String fileName = "FRESULT.BANK" + mBankEle.getAttributeValue("insu") + "." + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		try
		{
			f.bank_dz_file();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new MidplatException(ex.getMessage());
		}
		return fileName;
	}

	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		this.cLogger.info("Into NonReaTimeIssResDocBankDeal.run()...");

		this.cResultMsg = null;
		try
		{
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));

			String nextDate = this.cThisBusiConf.getChildText("nextDate");

			if (this.cTranDate == null)
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					this.cTranDate = new Date();
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					this.cTranDate = new Date();
				}
			Element tTranData = new Element("TranData");
			Document tInStdXml = new Document(tTranData);

			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			try
			{
				String ttFileName = getFileName();
				this.cLogger.info("FileName = " + ttFileName);
				String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
				this.cLogger.info("localDir = " + ttLocalDir);
				InputStream ttBatIns = null;

				ttBatIns = new FileInputStream(ttLocalDir + ttFileName);
				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			}
			catch (Exception ex)
			{
				this.cLogger.error("生成标准对账报文出错!", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("业务处理模块" + tServiceClassName);
			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head").getChildText("Desc");

		}
		catch (Throwable ex)
		{
			this.cLogger.error("交易出错", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out NonReaTimeIssResDocBankDeal.run()!");
	}

	protected Element parse(InputStream pBatIs) throws Exception
	{
		cLogger.info("Into NonReaTimeIssResDocBankDeal.parse()...");

		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}
		// 格式：保险公司代码|银行代码|总记录数|总金额|
		// 文件其他内容：（明细记录）
		// 保险公司代码|申请日期|试算申请顺序号|投保人姓名|投保人证件类型|投保人证件号码|险种编码|产品编码|保单号|承保日期|
		// 投被保人关系|被保人姓名|被保人证件类型|被保人证件号码|保费|保额|缴费账户|缴费方式|缴费期限|保单到期日|投保分数|
		// 个性化费率|保单印刷号|错误码|错误信息|备注1|备注2|

		System.out.println(pBatIs);
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// 把成功的记录独取出来发给核心
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{
			cLogger.info(tLineMsg);

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!"3103".equals(tSubMsgs[0]))
			{// 如果回盘正确，首行为记录；错误的时候第二行为错误信息

				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}
				
				Element tDetailEle = new Element(Detail);

				//设置批处理执行日期为交易日期
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				tDetailEle.addContent(tTranDateEle);

				// 保单号
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[8]);
				tDetailEle.addContent(tContNoEle);
				
				//试算申请顺序号
				Element tApplyNo = new Element("ApplyNo");
				tApplyNo.setText(tSubMsgs[2]);
				tDetailEle.addContent(tApplyNo);

				//保费
				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[14]);
				tPremEle.setText(String.valueOf(tPremFen));
				tDetailEle.addContent(tPremEle);
				
				//投保人姓名 
				Element appntName = new Element("AppntName");
				appntName.setText(tSubMsgs[3]);
				tDetailEle.addContent(appntName);
				
				//被保人姓名 
				Element insuredName = new Element("InsuredName");
				insuredName.setText(tSubMsgs[11]);
				tDetailEle.addContent(insuredName);
				
				//返回码
				Element dealCode = new Element("State");
				dealCode.setText(tSubMsgs[23]);
				tDetailEle.addContent(dealCode);
				
				//返回信息
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[24]);
				tDetailEle.addContent(msg);
				
				mBodyEle.addContent(tDetailEle);
			}
			else
			{
				mCountEle.setText("0");
				mSumPremEle.setText("0");
			}

		}
		mBufReader.close(); // 关闭流

		cLogger.info("Out NonReaTimeIssResDocBankDeal.parse()!");
		return mBodyEle;
	}

	protected Element getHead()
	{
		cLogger.info("Into NonReaTimeIssResDocBankDeal.getHead()...");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..." + mCurrDate);

		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			tBalanceFlag = "1";
		}

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		if (null != tTempStr && !"".equals(tTempStr))
		{
			mTranCom.setAttribute(outcode, tTempStr);
		}

		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);

		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		mFuncFlag.setText(tTempStr);

		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);

		// 报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);

		// 报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);

		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);

		cLogger.info("Out NonReaTimeIssResDocBankDeal.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.NonReaTimeIssResDocBankDeal.main");
		mLogger.info("程序开始...");

		NonReaTimeIssResDocBankDeal mBatch = new NonReaTimeIssResDocBankDeal();
		// 用于补对账，设置补对账日期
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
				mBatch.setDate(args[0]);
			}
			else
			{
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
