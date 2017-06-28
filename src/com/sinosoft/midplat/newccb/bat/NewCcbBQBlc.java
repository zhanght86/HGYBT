package com.sinosoft.midplat.newccb.bat;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NewCcbBQBlc extends TimerTask implements XmlTag
{

	protected final Logger cLogger = Logger.getLogger(getClass());
	@SuppressWarnings("unused")
	private XmlConf cThisConf;
	private int cFuncFlag;
	protected Date cTranDate;
	protected String cResultMsg;
	protected String sTranDate;
	protected Element cMidplatRoot = null;
	protected Element cBatchConfRoot = null;
	protected Element cNewCcbConfRoot = null;
	protected Element cThisBusiConf = null;
	protected Document cOutStdXml10 = null;

	public NewCcbBQBlc()
	{
		this(NewCcbConf.newInstance(), 1048);
	}

	public NewCcbBQBlc(XmlConf pThisConf, int pFuncFlag)
	{
		this.cThisConf = pThisConf;
		this.cFuncFlag = pFuncFlag;
	}

	public void run()
	{
		long mStartMillis = System.currentTimeMillis();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NewCcbBQBlc.run()...");
		TranLogDB cTranLogDB = new TranLogDB();
		cTranLogDB.setLogNo(Thread.currentThread().getName());
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
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					this.cTranDate = new Date();
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					this.cTranDate = new Date();
				}
			Element tTranData10 = new Element("TranData");
			Document tInStdXml10 = new Document(tTranData10);
			Element tHeadEle10 = getHead();
			Element tBodyEle10 = new ElementLis("Body", tTranData10);
			tTranData10.addContent(tHeadEle10);

			try
			{
				sTranDate = String.valueOf(DateUtil.get8Date(this.cTranDate));// 设置对账日期为前一天
				// sTranDate = String.valueOf(DateUtil.get8Date(new
				// Date().getTime() - 86400000L));//设置对账日期为前一天
				// 退保(目前的保全，只有退保，没有满期业务，后续有满期业务时为09)
				ElementLis TranType10 = new ElementLis("TranType", "10", tBodyEle10);
				String mSqlStr10 = "select * from ContBlcDtl where trancom='03' " + " and trandate='" + sTranDate + "'" + " and type='10'";
				SSRS ssrs10 = new ExeSQL().execSQL(mSqlStr10);
				for (int i = 0; i < ssrs10.getMaxRow(); i++)
				{
					ElementLis Detail = new ElementLis("Detail", tBodyEle10);
					ElementLis BusiType = new ElementLis("BusiType", "10", Detail);
					ElementLis ContNo = new ElementLis("ContNo", ssrs10.GetText(i + 1, 3), Detail);
					ElementLis TranNo = new ElementLis("TranNo", ssrs10.GetText(i + 1, 17), Detail);
					ElementLis NodeNo = new ElementLis("NodeNo", ssrs10.GetText(i + 1, 16), Detail);
					ElementLis EdorNo = new ElementLis("EdorNo", ssrs10.GetText(i + 1, 18), Detail);
					ElementLis TranDate = new ElementLis("TranDate", ssrs10.GetText(i + 1, 19), Detail);
					ElementLis AccNo = new ElementLis("AccNo", "", Detail);// 银行对账文件暂时没有
					ElementLis AccName = new ElementLis("AccName", "", Detail);// 银行对账文件暂时没有
				}

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
				tTranData10.addContent(ttError);
			}

			try
			{
				if ("10".equals(tInStdXml10.getRootElement().getChild("Body").getChildText("TranType")))
				{
					// 退保对账
					try
					{
						cTranLogDB = insertTranLog(tInStdXml10);

						/*
						 * //本地测试用 Element tTranDataa=new Element(TranData);
						 * Element tHeada=new Element(Head); Element tFlaga=new
						 * Element(Flag); tFlaga.setText("0"); Element
						 * tDesca=new Element(Desc); tDesca.setText("新单对账成功！");
						 * Element tBodya=new Element(Body);
						 * 
						 * tTranDataa.addContent(tHeada);
						 * tTranDataa.addContent(tBodya);
						 * 
						 * tHeada.addContent(tFlaga); tHeada.addContent(tDesca);
						 * cOutStdXml10=new Document(tTranDataa);
						 */

						cOutStdXml10 = new CallWebsvcAtomSvc("16").call(tInStdXml10);
						JdomUtil.print(cOutStdXml10);

					}
					catch (Exception e)
					{
						cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", e);
						cOutStdXml10 = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, e.getMessage());
						// 插入日志失败时cTranLogDB=null
						cTranLogDB.setRCode("1");
						cTranLogDB.setRText("退保对账"+e.getMessage());
						long tCurMillis = System.currentTimeMillis();
						cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
						cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
						cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
						if (!cTranLogDB.update())
						{
							cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
						}
						e.printStackTrace();
					}
					if (null != cTranLogDB)
					{ // 插入日志失败时cTranLogDB=null
						Element tHeadEle = cOutStdXml10.getRootElement().getChild(Head);
						cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
						cTranLogDB.setRText("退保对账"+tHeadEle.getChildText(Desc));
						long tCurMillis = System.currentTimeMillis();
						cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
						cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
						cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
						if (!cTranLogDB.update())
						{
							cLogger.error(cTranLogDB.mErrors.getFirstError());
							throw new MidplatException("插入日志失败！");
						}
					}
				}

			}
			catch (Exception e)
			{
				throw new MidplatException(cOutStdXml10.getRootElement().getChild("Head").getChildText("Desc"));
			}

			this.cResultMsg = cOutStdXml10.getRootElement().getChild("Head").getChildText("Desc");

		}
		catch (Throwable ex)
		{
			this.cLogger.error("对账交易出错", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out NewCcbBQBlc.run()!");
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
		cLogger.info("Into NewCcbBQBlc.getHead()...");

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

		cLogger.info("Out NewCcbBQBlc.getHead()!");
		return mHead;
	}

	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into NewCcbBWBlc.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		if ("10".equals(mBodyEle.getChildText("TranType")))
		{// 此处是因为只有一个进程，tranlog的主键是logno
			mTranLogDB.setBak1("11");
		}
		System.out.println("进程名：" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo)+(new Random().nextInt(9999-1000)+1000));
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
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newccb.bat.NewCcbBQBlc.main");
		mLogger.info("程序开始...");
		NewCcbBQBlc mBatch = new NewCcbBQBlc();
		mBatch.run();
		// File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
		// InputStream in = new FileInputStream(file);
		// Element mBodyEle = mBatch.parse(in);
		// JdomUtil.print(mBodyEle);
		mLogger.info("成功结束！");
	}

}
