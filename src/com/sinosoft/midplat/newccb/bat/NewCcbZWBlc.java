package com.sinosoft.midplat.newccb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.vdb.TranLogDBSet;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.Service;

import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NewCcbZWBlc extends TimerTask implements XmlTag
{

	protected final Logger cLogger = Logger.getLogger(getClass());
	private XmlConf cThisConf;
	private int cFuncFlag;
	protected Date cTranDate;
	protected String cResultMsg;
	protected String sTranDate;
	protected Element cMidplatRoot = null;
	protected Element cBatchConfRoot = null;
	protected Element cNewCcbConfRoot = null;
	protected Element cThisBusiConf = null;
	protected Document cOutStdXml01 = null;
	protected Document cOutStdXml11 = null;
	TranLogDB cTranLogDB01 = new TranLogDB();
	TranLogDB cTranLogDB11 = new TranLogDB();

	public NewCcbZWBlc()
	{
		this(NewCcbConf.newInstance(), 3005);
	}

	public NewCcbZWBlc(XmlConf pThisConf, int pFuncFlag)
	{
		this.cThisConf = pThisConf;
		this.cFuncFlag = pFuncFlag;
	}

	public void run()
	{
		long mStartMillis = System.currentTimeMillis();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NewCcbZWBlc.run()...");

		this.cResultMsg = null;
		try
		{
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			this.cBatchConfRoot = BatConf.newInstance().getConf().getRootElement();
			this.cNewCcbConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cBatchConfRoot, "batch[funcFlag='" + this.cFuncFlag + "']"));

			JdomUtil.print(cThisBusiConf);

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
			//新单
			Element tTranData01 = new Element("TranData");
			Document tInStdXml01 = new Document(tTranData01);
			Element tHeadEle01 = getHead();
			Element tBodyEle01 = new ElementLis("Body", tTranData01);
			tTranData01.addContent(tHeadEle01);

			//续期
			Element tTranData11 = new Element("TranData");
			Document tInStdXml11 = new Document(tTranData11);
			Element tHeadEle11 = getHead();
			Element tBodyEle11 = new ElementLis("Body", tTranData11);
			tTranData11.addContent(tHeadEle11);

			try
			{
				sTranDate = String.valueOf(DateUtil.get8Date(this.cTranDate));
				
				// 新单
				String mSqlStr01 = "select * from ContBlcDtl where trancom='03' and trandate="+sTranDate+"  and type='01'";
				SSRS ssrs01 = new ExeSQL().execSQL(mSqlStr01);
				ElementLis TranType01 = new ElementLis("TranType", "01", tBodyEle01);
				int tSumPrem01 = 0;
				for (int i = 0; i < ssrs01.getMaxRow(); i++)
				{
					ElementLis Detail = new ElementLis("Detail", tBodyEle01);
					ElementLis BusiType = new ElementLis("BusiType", "01", Detail);
					ElementLis ContNo = new ElementLis("ContNo", ssrs01.GetText(i + 1, 3), Detail);
					ElementLis Prem = new ElementLis("Prem", ssrs01.GetText(i + 1, 6), Detail);
					
					ElementLis AgentCom = new ElementLis("AgentCom", ssrs01.GetText(i + 1, 9), Detail);
					
					ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", ssrs01.GetText(i + 1, 4), Detail);
					ElementLis AppntName = new ElementLis("AppntName", ssrs01.GetText(i + 1, 12), Detail);
					ElementLis InsuredName = new ElementLis("InsuredName", ssrs01.GetText(i + 1, 13), Detail);
					
					//在detail节点下增加NodeNo节点
					Element nodeNo = new Element(NodeNo);
					//设置机构代码为网点代码
					nodeNo.setText(ssrs01.GetText(i + 1, 9));
					Detail.addContent(nodeNo);

					tSumPrem01 = tSumPrem01 + Integer.parseInt(ssrs01.GetText(i + 1, 6));
				}

				ElementLis sumCount01 = new ElementLis("Count", String.valueOf(ssrs01.getMaxRow()), tBodyEle01);// 总比数
				ElementLis sumPrem01 = new ElementLis("Prem", String.valueOf(tSumPrem01), tBodyEle01);// 总保费

				// 续期
				String mSqlStr11 = "select * from ContBlcDtl where trancom='03' " + " and trandate='" + sTranDate + "'" + " and type='11'";
				SSRS ssrs11 = new ExeSQL().execSQL(mSqlStr11);
				ElementLis TranType11 = new ElementLis("TranType", "11", tBodyEle11);
				int tSumPrem11 = 0;
				for (int i = 0; i < ssrs11.getMaxRow(); i++)
				{
					ElementLis Detail = new ElementLis("Detail", tBodyEle11);
					ElementLis BusiType = new ElementLis("BusiType", "11", Detail);
					ElementLis ContNo = new ElementLis("ContNo", ssrs11.GetText(i + 1, 3), Detail);
					ElementLis Prem = new ElementLis("Prem", ssrs11.GetText(i + 1, 6), Detail);
					ElementLis AgentCom = new ElementLis("AgentCom", ssrs11.GetText(i + 1, 9), Detail);
					ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", ssrs11.GetText(i + 1, 4), Detail);
					ElementLis AppntName = new ElementLis("AppntName", ssrs11.GetText(i + 1, 12), Detail);
					ElementLis InsuredName = new ElementLis("InsuredName", ssrs11.GetText(i + 1, 13), Detail);
					
					//在detail节点下增加NodeNo节点
					Element nodeNo = new Element(NodeNo);
					//设置机构代码为网点代码
					nodeNo.setText(ssrs01.GetText(i + 1, 9));
					Detail.addContent(nodeNo);
					
					tSumPrem11 = tSumPrem11 + Integer.parseInt(ssrs11.GetText(i + 1, 6));
				}

				ElementLis sumCount11 = new ElementLis("Count", String.valueOf(ssrs11.getMaxRow()), tBodyEle11);// 总比数
				ElementLis sumPrem11 = new ElementLis("Prem", String.valueOf(tSumPrem11), tBodyEle11);// 总保费

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
				tTranData01.addContent(ttError);
				tTranData11.addContent(ttError);
			}

			String rMesg01 = "";
			String rMesg11 = "";
			try
			{

				// 新单对账
				try
				{
					cTranLogDB01 = insertTranLog(tInStdXml01);

					cOutStdXml01 = new CallWebsvcAtomSvc("6").call(tInStdXml01);
					rMesg01 = cOutStdXml01.getRootElement().getChild("Head").getChildText("Desc");

					JdomUtil.print(cOutStdXml01);

					if (cTranLogDB01 != null)
					{ // 插入日志失败时cTranLogDB=null
						Element tHeadEle = cOutStdXml01.getRootElement().getChild(Head);
						cTranLogDB01.setRCode(tHeadEle.getChildText(Flag));
						cTranLogDB01.setRText(tHeadEle.getChildText(Desc));
						cTranLogDB01.setRCode("0");
						cTranLogDB01.setRText("新单对账成功！");
						long tCurMillis = System.currentTimeMillis();
						cTranLogDB01.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
						cTranLogDB01.setModifyDate(DateUtil.get8Date(tCurMillis));
						cTranLogDB01.setModifyTime(DateUtil.get6Time(tCurMillis));
						if (!cTranLogDB01.update())
						{
							cLogger.error(cTranLogDB01.mErrors.getFirstError());
							throw new MidplatException("插入日志失败！");
						}
					}
				}
				catch (Exception e)
				{
					cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", e);
					cOutStdXml01 = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, e.getMessage());
					// 插入日志失败时cTranLogDB=null
					cTranLogDB01.setRCode("1");
					cTranLogDB01.setRText(e.getMessage());
					long tCurMillis01 = System.currentTimeMillis();
					cTranLogDB01.setUsedTime((int) (tCurMillis01 - mStartMillis) / 1000);
					cTranLogDB01.setModifyDate(DateUtil.get8Date(tCurMillis01));
					cTranLogDB01.setModifyTime(DateUtil.get6Time(tCurMillis01));
					if (!cTranLogDB01.update())
					{
						cLogger.error("更新日志信息失败！" + cTranLogDB01.mErrors.getFirstError());
					}
					e.printStackTrace();
				}

				// 续期对账
				try
				{
					cTranLogDB11 = insertTranLog(tInStdXml11);

					cOutStdXml11 = new CallWebsvcAtomSvc("18").call(tInStdXml11);
					rMesg11 = cOutStdXml11.getRootElement().getChild("Head").getChildText("Desc");

					JdomUtil.print(cOutStdXml11);
					if (cTranLogDB11 != null)
					{ // 插入日志失败时cTranLogDB=null
						Element tHeadEle = cOutStdXml11.getRootElement().getChild(Head);
						cTranLogDB11.setRCode(tHeadEle.getChildText(Flag));
						cTranLogDB11.setRText(tHeadEle.getChildText(Desc));
						cTranLogDB11.setRCode("0");
						cTranLogDB11.setRText("续期对账成功！");
						long tCurMillis = System.currentTimeMillis();
						cTranLogDB11.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
						cTranLogDB11.setModifyDate(DateUtil.get8Date(tCurMillis));
						cTranLogDB11.setModifyTime(DateUtil.get6Time(tCurMillis));
						if (!cTranLogDB11.update())
						{
							cLogger.error(cTranLogDB11.mErrors.getFirstError());
							throw new MidplatException("插入日志失败！");
						}
					}
				}
				catch (Exception e)
				{
					cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", e);
					cOutStdXml11 = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, e.getMessage());
					// 插入日志失败时cTranLogDB=null
					cTranLogDB11.setRCode("1");
					cTranLogDB11.setRText(e.getMessage());
					long tCurMillis11 = System.currentTimeMillis();
					cTranLogDB11.setUsedTime((int) (tCurMillis11 - mStartMillis) / 1000);
					cTranLogDB11.setModifyDate(DateUtil.get8Date(tCurMillis11));
					cTranLogDB11.setModifyTime(DateUtil.get6Time(tCurMillis11));
					if (!cTranLogDB11.update())
					{
						cLogger.error("更新日志信息失败！" + cTranLogDB11.mErrors.getFirstError());
					}
					e.printStackTrace();
				}

			}
			catch (Exception e)
			{
				throw new MidplatException(rMesg01 + ";" + rMesg11);
			}

			this.cResultMsg = rMesg01 + ";" + rMesg11;

		}
		catch (Throwable ex)
		{
			this.cLogger.error("对账交易出错", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out NewCcbZWBlc.run()!");
	}

	/*
	 * 设置对账日期
	 */
	public final void setDate(String p8DateStr)
	{
		this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}

	public String getResultMsg()
	{
		return this.cResultMsg;
	}

	protected Element getHead()
	{
		cLogger.info("Into NewCcbZWBlc.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cNewCcbConfRoot.getChild(TranCom).clone();

		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());

		// 报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cThisBusiConf.getChildText("BankCode"));

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		// 报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);

		cLogger.info("Out NewCcbZWBlc.getHead()!");
		return mHead;
	}

	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into NewCcbBWBlc.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		if ("11".equals(mBodyEle.getChildText("TranType")))
		{// 此处是因为只有一个进程，tranlog的主键是logno
			mTranLogDB.setLogNo(String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			System.out.println("进程名：" + String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			mTranLogDB.setBak1("11");
		}
		else
		{
			mTranLogDB.setLogNo(Thread.currentThread().getName());
			System.out.println("进程名：" + Thread.currentThread().getName());
			mTranLogDB.setBak1("01");
		}
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));

		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}

		cLogger.debug("Out NewCcbBWBlc.insertTranLog()!");
		return mTranLogDB;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.jhyh.bat.NewCcbZWBlc.main");
		mLogger.info("程序开始...");

		NewCcbZWBlc mBatch = new NewCcbZWBlc();
		mBatch.run();
		// File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
		// InputStream in = new FileInputStream(file);
		// Element mBodyEle = mBatch.parse(in);
		// JdomUtil.print(mBodyEle);
		mLogger.info("成功结束！");
	}

}
