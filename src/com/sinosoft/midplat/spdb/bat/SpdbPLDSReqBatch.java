package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: SpdbPLDSReqBatch
 * @Description: 批量代扣上传文件
 * @author sinosoft
 * @date 2017-4-21 下午5:38:51
 */
public class SpdbPLDSReqBatch extends SpdbPushFileToBankSvc
{

	public SpdbPLDSReqBatch()
	{
		super(SpdbConf.newInstance(), "3013");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDSReqBatch.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 处理核心返回报文
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_spdbpldsReq).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setBak2("调用核心接口：" + tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
			receive(cOutXmlDoc, ttLocalDir);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out SpdbPLDSReqBatch.deal()...");
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbPLDSReqBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		ElementLis Body = new ElementLis("Body", TranData);
		new ElementLis(TranDate, DateUtil.getDateStr(cTranDate, "yyyyMMdd"), Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbPLDSReqBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	@SuppressWarnings("unchecked")
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDSReqBatch.receive()..." + cTranDate);
		String outHead = "";// 头记录
		String serialNo = "";// 序号
		String fileName = "";// 文件名称
		String out = "";
		String outBody = "";
		int maxNo = 1000;// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可
		int totalRound = 0;

		Element tRoot = cOutXmlDoc.getRootElement();
		String tComCode = AblifeCodeDef.spdb_ComCode;// 保险公司代码

		Element tBody = tRoot.getChild("Body");

		List<Element> list = tBody.getChildren("Detail");
		int tCount = list.size();
		cLogger.info("生成返回文件头记录" + outHead);
		if (list.size() > 0)
		{
			String sSumPrem = tBody.getChildText("SAmount");
			if (tCount % maxNo == 0)
			{
				totalRound = tCount / maxNo;
			}
			else
			{
				totalRound = tCount / maxNo + 1;
			}

			for (int i = 0; i < totalRound; i++)
			{
				if (i + 1 < 10)
				{
					serialNo = "0" + Integer.toString(i);
					fileName = "UBP_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					serialNo = Integer.toString(i);
					fileName = "UBP_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				File tDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				// 如果文件目录不存在创建目录
				if (!tDir.exists())
				{
					try
					{
						tDir.mkdir();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				File file = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "/" + fileName);
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
					String tAccNo = tDetail.getChildText("AccNo");
					String tPrem = tDetail.getChildText("Prem");
					String tAppntName = tDetail.getChildText("AppntName");
					String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
					String tPayPeriod = tDetail.getChildText("PayPeriod");
					String tSumID = tDetail.getChildText("SumID");
					String tMarkRemark1 = tDetail.getChildText("Bak1");
					String tMarkRemark2 = tDetail.getChildText("Bak2");
					String tMark = "";
					outBody += tAccNo + "※" + tPrem + "※" + tAppntName + "※" + tProposalPrtNo + "※" + tPayPeriod + "※" + tSumID + "※"
							+ tMarkRemark1 + "※" + tMarkRemark2 + "※" + tMark + "\n";
				}
				// 输出记录至txt文件
				if ((i + 1) * maxNo < tCount)
				{
					outHead = "F※" + tComCode + "※" + "1006※" + sSumPrem + "※1000※※1000※※※N" + "\n";
				}
				else
				{
					outHead = "F※" + tComCode + "※" + "1006※" + sSumPrem + "※" + (tCount - (i * maxNo)) + "※※" + (tCount - (i * maxNo)) + "※※※N"
							+ "\n";
				}
				out = outHead + outBody;
				cLogger.info("第" + (i + 1) + "个文件生成返回文件总记录" + out);
				byte[] m = out.getBytes();
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
				if (i * maxNo == list.size())
				{
					file.delete();
				}
				outBody = "";
				out = "";
			}
		}
		cLogger.info("Out SpdbPLDSReqBatch.receive()...");
	}

	public static void main(String args[]) throws Exception
	{
		SpdbPLDSReqBatch tBlc = new SpdbPLDSReqBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbPLDSReqBatch.class);

		mLogger.info("浦发批量代收上传批处理开始...");
//		args = new String[1];
//		args[0] = "20160202";
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
		mLogger.info("浦发批量代收上传批处理结束！");
	}
}