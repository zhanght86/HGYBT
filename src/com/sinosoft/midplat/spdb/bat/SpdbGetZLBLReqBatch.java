package com.sinosoft.midplat.spdb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
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
 * @ClassName: SpdbGetZLBLReqBatch
 * @Description: 直联补录上传文件
 * @author sinosoft
 * @date 2017-4-21 下午5:32:52
 */
public class SpdbGetZLBLReqBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetZLBLReqBatch()
	{
		super(SpdbConf.newInstance(), "3012");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetZLBLReqBatch.deal()...个性化业务处理模块...");
		try
		{
			// 定义需要的变量
			String tRiskCode;
			String tPayEndYearFlag;
			String tPayEndYear;
			String tInsuYearFlag;
			String tInsuYear;
			String tMRiskCode;
			Element tDtls;
			Element tRisks;
			String[] str;
			Element tBody = new Element("Body");
			String info;
			Document cInXmlDoc = sendRequest();
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			Map<String, String> riskMap = new HashMap<String, String>();
			SSRS res = new ExeSQL().execSQL("select BANK_CODE,MID_CODE from codemapping where COMCODE='SPDB' and CODETYPE='riskcode'");
			for (int i = 1; i <= res.MaxRow; i++)
			{
				riskMap.put(res.GetText(i, 1), res.GetText(i, 2));
			}
			cInXmlDoc.getRootElement().addContent(tBody);
			br.readLine();// 略过文件头
			while ((info = br.readLine()) != null)
			{
				cLogger.info("开始解析文件，组织报文......");
				str = info.split("※", info.length());
				tDtls = new Element("Detail");
				tRisks = new Element("Risks");
				new ElementLis("BankCode", str[0], tDtls);
				new ElementLis("ZoneNo", str[1], tDtls);
				new ElementLis("NodeNo", str[2], tDtls);
				new ElementLis("TellerNo", str[3], tDtls);
				new ElementLis("PolApplyDate", str[4], tDtls);
				new ElementLis("ProposalPrtNo", str[5], tDtls);
				new ElementLis("AppntName", str[6], tDtls);
				new ElementLis("AppntIDType", tranIdType(str[7]), tDtls);
				new ElementLis("AppntIDNo", str[8], tDtls);
				new ElementLis("AccNo", str[9], tDtls);
				tDtls.addContent(tRisks);
				int productCount = Integer.parseInt(str[10]);
				for (int i = 0; i < productCount; i++)
				{
					Element tRisk = new Element("Risk");
					tRisks.addContent(tRisk);
					tRiskCode = str[10 + i * 6 + 1];
					tPayEndYearFlag = str[10 + i * 6 + 2];
					tPayEndYear = str[10 + i * 6 + 3];
					tInsuYearFlag = str[10 + i * 6 + 4];
					tInsuYear = str[10 + i * 6 + 5];
					tMRiskCode = str[11];
					tRiskCode = riskMap.get(tRiskCode);
					tMRiskCode = riskMap.get(tMRiskCode);
					// 缴费年期类型银行 趸缴4/1 转成0/1给核心
					if (tPayEndYearFlag.equals("3"))
					{
						tPayEndYearFlag = "12";
					}
					else if (tPayEndYearFlag.equals("4"))
					{
						tPayEndYearFlag = "0";
					}
					// 保险年期类型转换 保终身1/-1 转成L/1000
					if (tInsuYearFlag.equals("1"))
					{
						tInsuYearFlag = "L";
						if (tInsuYear.equals("-1"))
						{
							tInsuYear = "1000";
						}
					}
					else if (tInsuYearFlag.equals("2"))
					{
						tInsuYearFlag = "Y";
					}
					else if (tInsuYearFlag.equals("3"))
					{
						tInsuYearFlag = "A";
					}
					if (tRiskCode == null || "".equals(tRiskCode))
						tRiskCode = str[10 + i * 6 + 1];
					if (tRiskCode == null || "".equals(tRiskCode))
						tMRiskCode = str[11];
					new ElementLis("RiskCode", tRiskCode, tRisk);
					new ElementLis("MainRiskCode", tMRiskCode, tRisk);
					new ElementLis("PayEndYearFlag", tPayEndYearFlag, tRisk);
					new ElementLis("PayEndYear", tPayEndYear, tRisk);
					new ElementLis("InsuYearFlag", tInsuYearFlag, tRisk);
					new ElementLis("InsuYear", tInsuYear, tRisk);
					new ElementLis("Prem", str[10 + i * 6 + 6], tRisk);
				}
				tBody.addContent(tDtls);
			}
			
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_Spdbzlbl).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;

			String updateSql = "update tranlog set rcode='" + tHeadEle.getChildText(Flag) + "',bak2 = '" + "UZLBL调用核心接口："
					+ tHeadEle.getChildText(Desc) + "', usedtime='" + usedtime + "',modifydate='" + DateUtil.get8Date(tCurMillis)
					+ "',modifytime='" + DateUtil.get6Time(tCurMillis) + "' where indoc='" + cLocalFile.getName() + "' and funcflag='"
					+ cFuncFlag + "'";
			boolean tExeFlag = new ExeSQL().execUpdateSQL(updateSql);
			if (!tExeFlag)
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String tranIdType(String idType)
	{
		if (idType.equals("1"))
			idType = "0";
		else if (idType.equals("2"))
			idType = "1";
		else if (idType.equals("3"))
			idType = "2";
		else if (idType.equals("4") || idType.equals("8"))
			idType = "D";
		else if (idType.equals("5"))
			idType = "F";
		else if (idType.equals("6"))
			idType = "4";
		else if (idType.equals("7") || idType.equals("9") || idType.equals("D"))
			idType = "8";
		else if (idType.equals("A"))
			idType = "A";
		else if (idType.equals("B"))
			idType = "E";
		else if (idType.equals("C"))
			idType = "C";
		return idType;
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbGetZLBLReqBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetZLBLReqBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws MidplatException
	{
		SpdbGetZLBLReqBatch tBlc = new SpdbGetZLBLReqBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetZLBLReqBatch.class);
		mLogger.info("浦发直连补录下载UZLBL批处理程序开始...");
		// 用于补直连补录上传批处理，设置补直连补录上传批处理日期
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
		mLogger.info("浦发直连补录下载UZLBL批处理成功结束！");
	}
}