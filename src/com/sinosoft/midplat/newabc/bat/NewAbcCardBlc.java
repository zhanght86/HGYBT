/**
 * 新农行单证对账
 * @author sinosoft
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ExeSQL;

public class NewAbcCardBlc extends Balance
{

	protected final Logger cLogger = Logger.getLogger(getClass());

	public NewAbcCardBlc()
	{
		super(NewAbcConf.newInstance(), "2000");
	}

	/**
	 * 获取新单对账文件名
	 */
	protected String getFileName() throws Exception
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		String fileName = "POLICY" + mBankEle.getAttributeValue("insu") + "." + DateUtil.getDateStr(cTranDate, "yyyyMMdd");

		return fileName;
	}

	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		this.cLogger.info("Into NewAbcBusiBlc.run()...");
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

		this.cLogger.info("Out NewAbcCardBlc.run()!");
	}

	protected Element parse(InputStream pBatIs) throws Exception
	{
		cLogger.info("Into NewAbcBusiBlc.parse()...");

		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}

		System.out.println(pBatIs);
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		/*
		 * 文件第一行：（汇总信息） 格式：保险公司代码|总记录数|总金额|成功总记录数|成功总金额 文件其他内容：（明细记录）
		 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|交易状态
		 */
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// 把成功的记录独取出来发给核心
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[3].trim());
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{

			// 空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg))
			{
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!"01".equals(tSubMsgs[6]))
			{
				cLogger.warn("非承保保单，直接跳过，继续下一条！");
				continue;
			}
			if (!("01".equals(tSubMsgs[7])))
			{
				cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
				continue;
			}

			/*
			 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
			 * 
			 * 联调的时候和农行的人员确认的，20130403
			 */
			
			//单证关联号(保单号、保全号等)
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[4]);
			
			// 获取保单印刷号
			String tContPrtNoSql = "select otherno from tranlog where funcflag='1004' and contno='" + tContNoEle.getText() + "' and trancom='05' and rcode='0' ";
			this.cLogger.info("保单印刷号sql:" + tContPrtNoSql);
			String tContPrtNo = new ExeSQL().getOneValue(tContPrtNoSql);
			//如果查询单证印刷号为空，则抛出异常
			if(StringUtils.isEmpty(tContPrtNo))
			{
				throw new MidplatException("单证对账失败：保单" + tContNoEle.getText() + "的单证号查询失败");
			}
			
			//单证类型
			Element tCardTypeEle = new Element("CardType");
			if (!"".equals(tContPrtNo) && tContPrtNo != null)
			{
				tCardTypeEle.setText(tContPrtNo.substring(0, 5));
			}
			
			//单证号 保单印刷号
			Element tCardNoEle = new Element(CardNo);
			tCardNoEle.setText(tContPrtNo);
						
			//单证状态
			Element tCardStateEle = new Element("CardState");
			tCardStateEle.setText("6");
			
			//机构[非必须，有些银行传] 
			String nodeNo = null;
			if (tSubMsgs[2] != null && tSubMsgs[3] != null)
			{
				nodeNo = tSubMsgs[2].trim() + tSubMsgs[3].trim();
			}
			Element tAgentCom = new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			//柜员代码[非必须，有些银行传]
			Element mTellerNoEle = new Element(TellerNo);
			mTellerNoEle.setText(tSubMsgs[3]);
			
			//交易流水号[非必须，有些银行传]
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[1]);
			

			/*// 获取交易日期
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);*/

			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tCardTypeEle);// 单证类型
			tDetailEle.addContent(tCardNoEle);// 保单印刷号
			tDetailEle.addContent(tCardStateEle);// 单证状态
			tDetailEle.addContent(tContNoEle);// 保单号
			tDetailEle.addContent(tAgentCom);// 机构
			tDetailEle.addContent(mTellerNoEle);// 柜员代码
			tDetailEle.addContent(tTranNoEle);// 交易流水号
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close(); // 关闭流

		cLogger.info("Out NewAbcBusiBlc.parse()!");
		return mBodyEle;
	}

	protected Element getHead()
	{
		cLogger.info("Into NewAbcCardBlc.getHead()...");
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

		Element mZoneNo = new Element(ZoneNo);
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText(NodeNo));

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

		cLogger.info("Out NewAbcCardBlc.getHead()!");
		return mHead;
	}

	
	  public static void main(String[] args) throws Exception { 
		  Logger mLogger= Logger.getLogger("com.sinosoft.midplat.Abc.bat.NewAbcBusiBlc.main");
		  mLogger.info("程序开始...");
	  
		  NewAbcCardBlc mBatch = new NewAbcCardBlc(); //用于补对账，设置补对账日期 
		  if (0 != args.length) { 
			  mLogger.info("args[0] = " + args[0]);
			 /**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。 4位年-2位月-2位日。
			 * 4位年：4位[0-9]的数字。 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}\\d{2}\\d{2}。
			 */
			 if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				 mBatch.setDate(args[0]); 
			 } else { throw new
				 MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]); 
			 } 
		  }
		  
		 mBatch.run();
		  
		 mLogger.info("成功结束！"); 
	  }	

}
