package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: SpdbPLDFReqBatch
 * @Description: 批量代发文件
 * @author sinosoft
 * @date 2017-4-21 下午5:37:03
 */
public class SpdbPLDFReqBatch extends SpdbPushFileToBankSvc
{

	public SpdbPLDFReqBatch()
	{
		super(SpdbConf.newInstance(), "3015");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDFReqBatch.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		JdomUtil.print(cInXmlDoc);
		try
		{
			// 处理核心返回报文
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_spdbpldfReq).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;

			receive(cOutXmlDoc, ttLocalDir);// 生成文件

			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setBak2("UBP调用核心接口：" + tHeadEle.getChildText(Desc));
			cTranLogDB.setUsedTime(usedtime);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out SpdbPLDFReqBatch.deal()...");
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbPLDFReqBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		ElementLis Body = new ElementLis("Body", TranData);
		TranData.addContent(Head);
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbPLDFReqBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	/***
	 * 每1000条记录生成一个文件
	 * 
	 * @param cOutXmlDoc
	 * @param ttLocalDir
	 */
	@SuppressWarnings("unchecked")
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDFReqBatch.receive()...");
		String tComCode = AblifeCodeDef.spdb_ComCode;// 保险公司代码
		String outHead = "";// 头记录
		String serialNo = "";// 序号
		String fileName = "";// 文件名称
		String out = "";
		String outBody = "";
		String tAccNo;
		String tPrem;
		String tAppntName;
		String tProposalPrtNo;
		String tSumID;
		String tMarkRemark1;
		String tMarkRemark2;
		String tMark;
		Double tSingleFileSumPrem = 0.00;
		Double tSumPrem = 0.00;
		BigDecimal tSingle;
		BigDecimal sSumPrem;
		File file;
		FileOutputStream fos;
		int maxNo = 1000;// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可
		int totalRound = 0;
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tBody = tRoot.getChild("Body");
		List<Element> list = tBody.getChildren("Detail");
		int tCount = list.size();
		cLogger.info("生成返回文件头记录" + outHead);

		if (list.size() != 0)
		{
			if (tCount % maxNo == 0)
				totalRound = tCount / maxNo;
			else
				totalRound = tCount / maxNo + 1;

			for (int i = 0; i < totalRound; i++)
			{
				if (i + 1 < 10)
				{
					serialNo = "0" + Integer.toString(i);
					fileName = "UBD_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					serialNo = Integer.toString(i);
					fileName = "UBD_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				file = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "/" + fileName);
				if (!file.exists())
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				/*
				 * 此循环是向第i个文件中流出(i+1)*maxNo - i*maxNo个明细记录
				 * 第i个文件里取的明细记录是从第i*maxNo个至第(i+1)*maxNo个, 当取的值和总数相同时，则跳出此循环
				 */
				tSingle = new BigDecimal(tSingleFileSumPrem);
				for (int j = i * maxNo; j < (i + 1) * maxNo; j++)
				{
					if (j == list.size())
					{
						break;
					}
					Element tDetail = list.get(j);
					tAccNo = tDetail.getChildText("AccNo");
					tPrem = tDetail.getChildText("Prem");
					tSingle.add(new BigDecimal(Double.parseDouble(tPrem)));
					tSumPrem += Double.parseDouble(tPrem);
					tAppntName = tDetail.getChildText("AppntName");
					tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
					tSumID = tDetail.getChildText("SumID");
					tMarkRemark1 = tDetail.getChildText("Bak1");
					tMarkRemark2 = tDetail.getChildText("Bak2");
					tMark = "";
					outBody += tAccNo + "※" + tPrem + "※" + tAppntName + "※" + tProposalPrtNo + "※" + tSumID + "※" + tMarkRemark1 + "※"
							+ tMarkRemark2 + "※" + tMark + "\n";
				}
				sSumPrem = new BigDecimal(tSumPrem);
				// 输出记录至txt文件
				if ((i + 1) * maxNo < tCount)
					outHead = "F※" + tComCode + "※" + "1008※" + sSumPrem.doubleValue() + "※1000※※1000※※※N" + "\n";
				else
					outHead = "F※" + tComCode + "※" + "1008※" + sSumPrem.doubleValue() + "※" + (tCount - (i * maxNo)) + "※※"
							+ (tCount - (i * maxNo)) + "※※※N" + "\n";

				out = outHead + outBody;
				cLogger.info("第" + i + "个文件生成返回文件总记录" + out);
				byte[] m = out.getBytes();
				try
				{
					fos = new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				if (i * maxNo == list.size())
				{
					file.delete();
				}
			}
		}
		cLogger.info("Out SpdbPLDFReqBatch.receive()..." + cTranDate);
	}

	public static void main(String args[]) throws MidplatException
	{
		SpdbPLDFReqBatch tBlc = new SpdbPLDFReqBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbPLDFReqBatch.class);

		mLogger.info("浦发批量代付上传批处理开始...");

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