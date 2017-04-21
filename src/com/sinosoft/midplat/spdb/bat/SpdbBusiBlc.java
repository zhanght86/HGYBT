package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ExeSQL;

public class SpdbBusiBlc extends ToBankBalance
{
	public SpdbBusiBlc()
	{
		super(SpdbConf.newInstance(), "3001");
	}

	@Override
	protected String getFileName(Date cDate)
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "DAYCHECK" + "SPDB" + mBankEle.getAttributeValue("insu") + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".xml";
	}

	public Document parse(InputStream pBatIs)
	{
		cLogger.info("Into SpdbBusiBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		
		//根节点
		Element mTranData = new Element("TranData");
		//交易日期
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//交易时间
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		//交易机构代码
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		//银行代码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(SpdbConf.newInstance().getConf().getRootElement().getChildText("BankCode"));
				
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
		
		//交易日志号[后加]
		Element mTranLogNo = new Element("TranLogNo");
		
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mTranCom).addContent(mBankCode)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mTranLogNo);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}
		
		Document mStdXml = JdomUtil.build(pBatIs);
		cLogger.info("银行的非标准报文!");
		JdomUtil.print(mStdXml);
		
		//CHECK节点
		Element tRoot = mStdXml.getRootElement().getChild("CHECK");
		//报文体
		Element mBodyEle = new Element(Body);
		//总笔数
		Element mCountEle = new Element(Count);
		//总金额(分)
		Element mPremEle = new Element(Prem);
		//报文体加入总笔数节点
		mBodyEle.addContent(mCountEle);
		//报文体加入总金额(分)节点
		mBodyEle.addContent(mPremEle);
		
		//CHECK节点非空
		if (tRoot != null)
		{
			// PROJECT=2时为新契约对账
			if (tRoot.getChildText("PROJECT").equals("2"))
			{
				//总保费
				long mSumPrem = 0;
				//总笔数
				int mCount = 0;
				
				//获取CHECK节点下交易明细子节点下交易节点列表
				@SuppressWarnings("rawtypes")
				List list = (List) tRoot.getChild("DTLS").getChildren("DTL");
				//遍历交易节点列表
				for (int i = 0; i < list.size(); i++)
				{
					//交易节点
					Element tDTL = (Element) list.get(i);
					//明细节点
					Element tDetailEle = new Element(Detail);
					
					// 在数据库中根据投保单号查出保单号
					String tsql = "select c.contno from cont c where c.proposalprtno='" + tDTL.getChildText("APPNO") + "' and state='2'";
					String mContNo = new ExeSQL().getOneValue(tsql);
					Element tContNo = new Element(ContNo);
					tContNo.setText(mContNo);
					
					//保费(分)
					String tPremFen=tDTL.getChildText("INSU_IN");
					Element tPremEle = new Element(Prem);
					tPremEle.setText(tPremFen);
					
					//代理机构
					Element tAgentCom = new Element(AgentCom);
					tAgentCom.setText(tDTL.getChildText("DEPT"));
					
					//投保单(印刷)号[非必须] 
					Element tProposalPrtNo = new Element(ProposalPrtNo);
					tProposalPrtNo.setText(tDTL.getChildText("APPNO"));
					
					//投保人姓名[非必须，有些银行传] 
					Element tAppntName = new Element("AppntName");
					tAppntName.setText(new ExeSQL().getOneValue("select appntname from cont where contno='" + mContNo + "'"));
					
					//被保人姓名[非必须]
					Element tInsuredName = new Element("InsuredName");
					tInsuredName.setText(new ExeSQL().getOneValue("select insuredname from cont where contno='" + mContNo + "'"));
					
					// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
					tDetailEle.addContent(tContNo).addContent(tPremEle)
					.addContent(tAgentCom).addContent(tProposalPrtNo)
					.addContent(tAppntName).addContent(tInsuredName);
					mBodyEle.addContent(tDetailEle);
					// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
					
					mCount++;
					mSumPrem += Long.parseLong(tPremFen);
				}
				mCountEle.setText(String.valueOf(mCount));
				mPremEle.setText(String.valueOf(mSumPrem));
				cLogger.info("对账日期：" + DateUtil.getCur8Date() + "、对账总金额：" + NumberUtil.fenToYuan(mSumPrem) + "、对账总数：" + String.valueOf(mCount));
			}
		}
		else
		{
			mCountEle.setText("0");
			mPremEle.setText("0");
		}
		mTranData.addContent(mBodyEle);
		
		cLogger.info("Out SpdbBusiBlc.parse()!");
		return new Document(mTranData);
	}

	@Override
	public Document getBalanceFile(Date cTranDate) throws Exception
	{
		String localDir = cThisBusiConf.getChildText("localDir") + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		WtpDownloadFile download = new WtpDownloadFile();
		String downFileName = getFileName(cTranDate);
		int count = 1;
		download.setOverWrite(true); // 默认覆盖,
		download.setLocalFileDir(localDir);
		while (true)
		{
			if (download.downLoadFile("14db3d025f", downFileName) != 0)
			{
				cLogger.info("WTP下载" + downFileName + "文件第" + count + "次失败!");
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
				cLogger.info("WTP下载" + downFileName + "文件成功！");
				break;
			}
		}

		cLogger.info("Java:TransId=[" + download.getTransId() + "]"); // 返回值
		cLogger.info("Java:LocalFileName=[" + download.getLocalFileName() + "]"); // 返回值

		InputStream ins = new FileInputStream(new File(localDir + "/" + getFileName(cTranDate)));
		return parse(ins);
	}

	@Override
	public void dealPrivateBusi(String cTranCom, Date cTransDate)
	{
	}

	public static void main(String[] args) throws Exception
	{
		SpdbBusiBlc tBlc = new SpdbBusiBlc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));

		Logger mLogger = Logger.getLogger(SpdbBusiBlc.class);
		mLogger.info("浦发对账程序开始...");

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
				tBlc.setDate(args[0]);
				tBlc.run();
			}
			else
			{
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		mLogger.info("浦发对账成功结束！");
	}
}