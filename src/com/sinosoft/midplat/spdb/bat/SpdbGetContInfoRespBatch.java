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
 * @ClassName: SpdbGetContInfoRespBatch
 * @Description: 批量保单信息同步回盘文件
 * @author sinosoft
 * @date 2017-4-21 下午5:19:33
 */
public class SpdbGetContInfoRespBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetContInfoRespBatch()
	{
		super(SpdbConf.newInstance(), "3010");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetContInfoRespBatch.deal()...个性化业务处理模块...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 根据返回的文件内容组织请求信息...
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();// 为了略过文件头
			String info;
			Element tBody = cInXmlDoc.getRootElement().addContent(new Element("Body"));
			while ((info = br.readLine()) != null)
			{
				String[] str = info.split("※", info.length());
				Element tDtls = new Element("Detail");
				new ElementLis("AppntName", str[0], tDtls);
				new ElementLis("ProposalPrtNo", str[1], tDtls);
				new ElementLis("Flag", str[2], tDtls);
				new ElementLis("ErrFlag", str[3], tDtls);
				new ElementLis("ErrDesc", str[4], tDtls);
				new ElementLis("Bak1", str[5], tDtls);
				new ElementLis("Bak2", str[6], tDtls);
				tBody.addContent(tDtls);
			}
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbContInfoResp).call(cInXmlDoc);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			// 因为父类中的TranLogDB可能包含多比交易的情况，所以需要重新查询
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
		cLogger.info("Into SpdbGetContInfoRespBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetContInfoRespBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws Exception
	{
		SpdbGetContInfoRespBatch tBlc = new SpdbGetContInfoRespBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetContInfoRespBatch.class);
		
		mLogger.info("浦发保单信息同步下载批处理程序开始...");

		// 用于补保单信息同步下载批处理，设置补保单信息同步下载批处理日期
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
		mLogger.info("浦发保单信息同步下载批处理成功结束！");
	}
}