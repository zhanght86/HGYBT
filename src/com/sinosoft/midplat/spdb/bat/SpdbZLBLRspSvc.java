package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * @ClassName: SpdbZLBLRspSvc
 * @Description: 直联补录回传文件
 * @author sinosoft
 * @date 2017-4-21 下午5:34:26
 */
public class SpdbZLBLRspSvc extends SpdbPushFileToBankSvc
{

	// 回传结果给银行
	public SpdbZLBLRspSvc()
	{
		super(SpdbConf.newInstance(), "3009");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbZLBLRspSvc.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 处理核心返回报文
			long mStartMillis = System.currentTimeMillis();

			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbzlblResp).call(cInXmlDoc);
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
		cLogger.info("Out SpdbZLBLRspSvc.deal()...");
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbZLBLRspSvc.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		TranData.addContent(Head);
		ElementLis Body = new ElementLis("Body", TranData);
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbZLBLRspSvc.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	@SuppressWarnings(
	{ "unchecked" })
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbZLBLRspSvc.receive()..." + cTranDate);

		String outHead = "";// 头记录
		String serialNo = "";// 序号
		String fileName = "";// 文件名称
		String out = "";
		String outBody = "";
		int maxNo = 1000;// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可

		Element tRoot = cOutXmlDoc.getRootElement();
		String tComCode = AblifeCodeDef.spdb_ComCode;// 保险公司代码

		List<Element> list = tRoot.getChild("Body").getChildren("Detail");
		int tCount = list.size();
		cLogger.info("生成返回文件头记录" + outHead);

		Map<String, String> riskMap = new HashMap<String, String>();
		SSRS res = new ExeSQL().execSQL("select INSU_CODE,BANK_CODE from codemapping where COMCODE='SPDB' and CODETYPE='riskcode'");

		for (int i = 1; i <= res.MaxRow; i++)
		{
			riskMap.put(res.GetText(i, 1), res.GetText(i, 2));
		}

		if (list.size() != 0)
		{
			int totalRound = 0;
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
					fileName = "DZLBL_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					serialNo = Integer.toString(i);
					fileName = "DZLBL_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				File tDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				// 如果文件不存在穿件文件
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
					String tZoneNo = tDetail.getChildText("ZoneNo");
					String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
					String tErrFlag = tDetail.getChildText("ErrFlag");
					// 如果失败
					if (tErrFlag.equals("1"))
					{
						String tErrDesc = tDetail.getChildText("ErrDesc");
						outBody += tZoneNo + "※" + tComCode + "※" + tProposalPrtNo + "※" + tErrFlag + "※" + tErrDesc + "※";
					}
					else
					{
						String tErrDesc = tDetail.getChildText("ErrDesc");
						String sContNo = tDetail.getChildText("ContNo");
						String tFlag1 = tDetail.getChildText("Flag1");
						String tInsuredName = tDetail.getChildText("InsuredName");
						String tInsuredIDType = exChangeNum(tDetail.getChildText("InsuredIDType"), "IDTYPE");
						String tInsuredIDNo = tDetail.getChildText("InsuredIDNo");
						String tPayMode = exChangeNum(tDetail.getChildText("PayMode"), "PayMode");
						String tPayDate = tDetail.getChildText("TrsDate").replace("-", "");
						outBody += tZoneNo + "※"// 地区号
								+ tComCode + "※" // 保险公司代码
								+ tProposalPrtNo + "※" // 投保单号
								+ tErrFlag + "※" // 错误代码0：成功1：失败
								+ tErrDesc + "※" // 错误信息 成功时：成功 失败时：给出失败原因
								+ sContNo + "※"// 保单号
								+ tFlag1 + "※" // 核保结论1 2- 核保成功 3- 核保失败 a-非常规退保
												// b-犹豫期退保 c-减保
								+ tInsuredName + "※" // 被保人姓名
								+ tInsuredIDType + "※" // 被保人证件类型
								+ tInsuredIDNo + "※" // 被保人证件号码
								+ tPayMode + "※" // 交费方式0：到保险公司交费 1：银行代收费
								+ tPayDate + "※";// 扣费日期
						List<Element> riskList = tDetail.getChild("Risks").getChildren();
						if (riskList.size() != 0)
						{
							outBody += riskList.size() + "※";// 保单包含产品个数
							for (int k = 0; k < riskList.size(); k++)
							{
								Element tRiskDetail = riskList.get(k);
								String tRiskCode = riskMap.get(tRiskDetail.getChildText("RiskCode"));
								String tPayEndYearFlag = tRiskDetail.getChildText("PayEndYearFlag");
								String tPayEndYear = tRiskDetail.getChildText("PayEndYear");
								String tInsuYearFlag = tRiskDetail.getChildText("InsuYearFlag");
								String tInsuYear = tRiskDetail.getChildText("InsuYear");
								// 核心趸缴0转成4给银行
								if (tPayEndYearFlag.equals("0"))
								{
									tPayEndYearFlag = "4";
								}
								else if (tPayEndYearFlag.equals("12"))
								{
									tPayEndYearFlag = "3";
								}
								// 核心L/1000保终身转成1/-1给银行
								if (tInsuYearFlag.equals("L"))
								{
									tInsuYearFlag = "1";
									if (tInsuYear.equals("1000"))
									{
										tInsuYear = "-1";
									}
								}
								else if (tInsuYearFlag.equals("Y"))
								{
									tInsuYearFlag = "2";
								}
								else if (tInsuYearFlag.equals("A"))
								{
									tInsuYearFlag = "3";
								}

								String tAmnt = format2Npoint(Double.valueOf(tRiskDetail.getChildText("Amnt")), 2);
								String tPrem = format2Npoint(Double.valueOf(tRiskDetail.getChildText("Prem")), 2);
								String tMult = "";// 与浦发确认后该字段可以为空，故置为空
								// 产品代码 投保份数 保费 保额 缴费年期类型 3-年缴 4-趸交 6缴至某确定年龄
								// 7终生缴费 缴费年期 保障年期类型 保障年期
								outBody += tRiskCode + "※" + tMult + "※" + tPrem + "※" + tAmnt + "※" + tPayEndYearFlag + "※" + tPayEndYear + "※"
										+ tInsuYearFlag + "※" + tInsuYear + "※";
							}
						}
					}

					String tMark = "";
					outBody += tMark + "\n";
				}
				// 输出记录至txt文件
				if ((i + 1) * maxNo < tCount)
				{
					outHead = "F※" + tComCode + "※" + "1007※1000※" + "※" + "※" + "\n";
				}
				else
				{
					outHead = "F※" + tComCode + "※" + "1007※" + (tCount - (i * maxNo)) + "※" + "※" + "※" + "\n";
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
		cLogger.info("Out SpdbZLBLRspSvc.receive()...");
	}

	@SuppressWarnings("unused")
	private String fenToYuan(String pFen)
	{
		if (null == pFen || "".equals(pFen))
		{
			return pFen;
		}

		return new DecimalFormat("0.00").format(Long.parseLong(pFen) / 100.0D);
	}

	private static String format2Npoint(double tNumber, int num)
	{

		String tNo1 = null;

		tNo1 = String.format("%." + num + "f", tNumber);

		return tNo1;

	}

	private String exChangeNum(String num1, String type)
	{
		String tResult = null;
		if (type.trim().equals("IDTYPE"))
		{
			if (num1.trim().equals("0"))
			{// 身份证
				tResult = "1";
			}
			else if (num1.trim().equals("1"))
			{// 护照
				tResult = "2";
			}
			else if (num1.trim().equals("2"))
			{// 军人证（军官证）
				tResult = "3";
			}
			else if (num1.trim().equals("3"))
			{// 驾照
				tResult = "7";
			}
			else if (num1.trim().equals("4"))
			{// 户口本
				tResult = "6";
			}
			else if (num1.trim().equals("5"))
			{// 学生证
				tResult = "7";
			}
			else if (num1.trim().equals("6"))
			{// 工作证
				tResult = "7";
			}
			else if (num1.trim().equals("7"))
			{// 出生证
				tResult = "7";
			}
			else if (num1.trim().equals("8"))
			{// 其它
				tResult = "7";
			}
			else if (num1.trim().equals("9"))
			{// 无证件
				tResult = "7";
			}
			else if (num1.trim().equals("A"))
			{// 士兵证
				tResult = "A";
			}
			else if (num1.trim().equals("B"))
			{// 回乡证
				tResult = "7";
			}
			else if (num1.trim().equals("C"))
			{// 临时身份证
				tResult = "1";
			}
			else if (num1.trim().equals("D"))
			{// 警官证
				tResult = "4";
			}
			else if (num1.trim().equals("E"))
			{// 台胞证
				tResult = "B";
			}
			else if (num1.trim().equals("F"))
			{// 港、澳、台通行证
				tResult = "5";
			}
			else
			{
				tResult = "7";
			}
		}
		else if (type.trim().equals("PayMode"))
		{
			if (num1.trim().equals("0"))
			{// 银行转帐
				tResult = "1";
			}
			else if (num1.trim().equals("2"))
			{// 现金，到保险公司缴费
				tResult = "0";
			}
			else if (num1.trim().equals("B"))
			{// 其他
				tResult = "1";
			}
			else
			{
				tResult = "1";
			}
		}
		return tResult;
	}

	public static void main(String args[]) throws Exception
	{
		SpdbZLBLRspSvc tBlc = new SpdbZLBLRspSvc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));

		Logger mLogger = Logger.getLogger(SpdbZLBLRspSvc.class);
		mLogger.info("浦发直连补录上传DZLBL批处理开始...");
//		args = new String[1];
//		args[0] = "20170407";
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
		mLogger.info("浦发直连补录上传DZLBL批处理成功结束！");
	}
}