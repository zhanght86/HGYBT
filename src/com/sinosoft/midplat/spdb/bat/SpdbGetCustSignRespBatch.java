package com.sinosoft.midplat.spdb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

/**
 * @ClassName: SpdbGetCustSignRespBatch
 * @Description: 批量签约回盘文件
 * @author sinosoft
 * @date 2017-4-21 下午5:21:49
 */
public class SpdbGetCustSignRespBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetCustSignRespBatch()
	{
		super(SpdbConf.newInstance(), "3011");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetCustSignRespBatch.deal()...个性化业务处理模块...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 根据返回的文件内容组织请求信息...
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();// 为了略过文件头
			String info;
			Element tBody = new Element("Body");
			cInXmlDoc.getRootElement().addContent(tBody);
			while ((info = br.readLine()) != null)
			{
				cLogger.info(info);
				String[] str = info.split("※", info.length());
				Element tDtls = new Element("Detail");
				new ElementLis("SignNo", str[0], tDtls);
				new ElementLis("AccType", str[1], tDtls);
				new ElementLis("AccNo", str[2], tDtls);
				new ElementLis("AppntName", str[3], tDtls);
				new ElementLis("AppntIDType", str[4], tDtls);
				new ElementLis("AppntIDNo", str[5], tDtls);
				new ElementLis("ProposalPrtNo", str[6], tDtls);
				new ElementLis("Flag", str[7], tDtls);
				new ElementLis("IsBankCust", str[8], tDtls);
				new ElementLis("ErrFlag", str[9], tDtls);
				new ElementLis("ErrDesc", str[10], tDtls);
				new ElementLis("Bak1", str[11], tDtls);
				new ElementLis("Bak2", str[12], tDtls);
				tBody.addContent(tDtls);
			}
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbCustSignResp).call(cInXmlDoc);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			// 因为父类中的TranLogDB可能包含多笔交易的情况，所以需要重新查询
			String updateSql = "update tranlog set rcode='" + tHeadEle.getChildText(Flag) + "',bak2 = '" + "调用核心接口："
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

	private Document sendRequest()
	{
		cLogger.info("Into SpdbGetCustSignRespBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetCustSignRespBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws MidplatException
	{
		SpdbGetCustSignRespBatch tBlc = new SpdbGetCustSignRespBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetCustSignRespBatch.class);

		mLogger.info("浦发批量签约下载批处理程序开始...");

		// args = new String[1];
		// args[0] = "20161212";

		// 用于补批量签约下载批处理，设置补批量签约下载批处理日期
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
		mLogger.info("浦发批量签约下载批处理成功结束！");
	}
}
