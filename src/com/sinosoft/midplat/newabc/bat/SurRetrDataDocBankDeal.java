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
 * 退保犹撤数据文件-银行处理结果回盘
 * 
 * @author lilu
 * 
 */
public class SurRetrDataDocBankDeal extends Balance
{
	public SurRetrDataDocBankDeal()
	{
		super(NewAbcConf.newInstance(), "2006");
	}

	protected String getFileName() throws Exception
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf, "TBYCHP", DateUtil.getDateStr(cTranDate, "yyyyMMdd"), mBankEle.getAttributeValue("insu"));
		String fileName = "INVALID.BANK" + mBankEle.getAttributeValue("insu") + "." + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
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
		this.cLogger.info("Into SurRetrDataDocBankDeal.run()...");

		this.cResultMsg = null;
		try
		{
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));

			String nextDate = this.cThisBusiConf.getChildText("nextDate");

			if (this.cTranDate == null)
			{
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					this.cTranDate = new Date();
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					this.cTranDate = new Date();
				}
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

		this.cLogger.info("Out SurRetrDataDocBankDeal.run()!");
	}

	protected Element parse(InputStream pBatIs) throws Exception
	{
		cLogger.info("Into SurRetrDataDocBankDeal.parse()...");

		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}
		// 格式：保险公司代码|银行代码|总记录数|总金额|犹撤总记录数|犹撤总金额|满期总记录数|满期总金额|退保总记录数|退保总金额
		// 文件其他内容：（明细记录）
		// 公司编码|业务发生日期|保单号|投保人名称|投保人证件类型|投保人证件号码|保费|接受文件日期|业务类别|受理渠道|
		// 银行处理流水号|保险公司受理状态|银行端保单状态|错误码|错误信息|备注1|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String mHeadMsgs = mBufReader.readLine();
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		if ("3103".equals(mHeadMsgs.substring(0, 4)))
		{// 如果回盘正确，首行为记录；错误的时候为错误信息
			cLogger.info(mHeadMsgs.substring(0, 4));
			String[] tHeadMsgs = mHeadMsgs.split("\\|", -1);
			cLogger.info(tHeadMsgs);
			mCountEle.setText(tHeadMsgs[2].trim());
			mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(tHeadMsgs[3].trim())));

			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
				cLogger.info(tLineMsg);

				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}

				String[] tSubMsgs = tLineMsg.split("\\|", -1);

				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[0]);
				//
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[10]);

				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[2]);

				Element tAppntName = new Element("AppntName");
				tAppntName.setText(tSubMsgs[3]);

				//处理结果
				Element tHandleState = new Element("State");		
				if ("240000".equals(tSubMsgs[13]))
				{// 银行受理成功
					tHandleState.setText("0");
				}
				else
				{// 银行受理失败
					tHandleState.setText("1");
				}
			
				//处理结果描述
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[14]);

				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[6]);
				tPremEle.setText(String.valueOf(tPremFen));

				Element tBusiType = new Element("BusiType");
				if ("01".equals(tSubMsgs[8]))
				{// 犹撤
					tBusiType.setText("07");
				}
				else if ("02".equals(tSubMsgs[8]))
				{// 满期
					tBusiType.setText("09");
				}
				else if ("03".equals(tSubMsgs[8]))
				{// 退保
					tBusiType.setText("11");
				}

				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tBusiType);
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tAppntName);
				tDetailEle.addContent(tTranNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tHandleState);
				tDetailEle.addContent(tPremEle);

				mBodyEle.addContent(tDetailEle);
			}
			mBufReader.close(); // 关闭流
		}
		else
		{
			mCountEle.setText("0");
			mSumPremEle.setText("0");
		}

		cLogger.info("Out SurRetrDataDocBankDeal.parse()!");
		return mBodyEle;
	}

	protected Element getHead()
	{
		cLogger.info("Into SurRetrDataDocBankDeal.getHead()...");
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

		cLogger.info("Out SurRetrDataDocBankDeal.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.SurRetrDataDocBankDeal.main");
		mLogger.info("程序开始...");

		SurRetrDataDocBankDeal mBatch = new SurRetrDataDocBankDeal();
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
