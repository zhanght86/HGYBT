package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @ClassName: SpdbCustSignSvc
 * @Description: 批量签约文件
 * @author sinosoft
 * @date 2017-4-21 下午5:18:02
 */
public class SpdbCustSignSvc extends SpdbPushFileToBankSvc
{

	/**
	 * <p>Title: 批量签约文件构造器</p>
	 * <p>Description: 初始化当前配置文件、交易代码</p>
	 */
	public SpdbCustSignSvc()
	{
		super(SpdbConf.newInstance(), "3008");
	}

	/**
	 * 处理
	 * @param ttLocalDir 本地保存文件目录
	 */
	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbCustSignSvc.deal()...");

		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			// 处理核心返回报文
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbCustSign).call(cInXmlDoc);

			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
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
		cLogger.info("Out SpdbCustSignSvc.deal()...");
	}

	/**
	 * @Title: sendRequest
	 * @Description: 发送核心请求报文
	 * @return 标准输入报文
	 * @return Document 核心标准输入报文
	 * @throws
	 */
	private Document sendRequest()
	{
		cLogger.info("Into SpdbCustSignSvc.sendRequest()...");
		//根节点
		ElementLis TranData = new ElementLis("TranData");
		//报文头[交易基本信息]
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		TranData.addContent(Head);
		//报文体[交易数据]
		ElementLis Body = new ElementLis("Body", TranData);
		//交易日期
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		//构建标准输入报文
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbCustSignSvc.sendRequest()..." + cTranDate);
		//返回标准输入报文
		return pXmlDoc;
	}

	/**
	 * @Title: receive
	 * @Description: 接收
	 * @param cOutXmlDoc 标准输出报文
	 * @param ttLocalDir 本地保存文件目录
	 * @return void  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbCustSignSvc.receive()..." + cTranDate);
		//头记录
		String outHead = "";
		//序号
		String serialNo = "";
		//文件名称
		String fileName = "";
		String out = "";
		String outBody = "";
		// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可
		int maxNo = 500;

		//标准输出报文根节点
		Element tRoot = cOutXmlDoc.getRootElement();
		//保险公司代码
		String tComCode = AblifeCodeDef.spdb_ComCode;

		//批量签约文件类实例
		SpdbCustSignSvc tRun = new SpdbCustSignSvc();
		
		//报文体
		Element tBody = tRoot.getChild("Body");
		//明细
		List<Element> list = tBody.getChildren("Detail");
		//明细数
		int tCount = list.size();
		//生成返回文件头记录
		cLogger.info("生成返回文件头记录" + outHead);
		//明细数非0
		if (list.size() != 0)
		{
			//全轮
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
				//序号不足2两位[小于10]
				if (i + 1 < 10)
				{
					//序号前置0
					serialNo = "0" + Integer.toString(i);
					//文件名称[	 UBCS_保险公司编号(4位) _机构代码（4位）_日期（8位）_序号（2位）										 文件类型：TXT]
					fileName = "UBCS_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					//取两位序号
					serialNo = Integer.toString(i);
					//文件名称[	 UBCS_保险公司编号(4位) _机构代码（4位）_日期（8位）_序号（2位）										 文件类型：TXT]
					fileName = "UBCS_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				//本地保存文件目录/yyyyMMdd
				File tDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				// 如果文件夹不存在
				if (!tDir.exists())
				{
					try
					{
						//创建文件夹
						tDir.mkdir();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				//本地保存文件目录/yyyyMMdd/UBCS_保险公司编号(4位) _机构代码（4位）_日期（8位）_序号（2位）.TXT
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
					String tSignNo = tDetail.getChildText("SignNo");
					String tAccType = tDetail.getChildText("AccType");
					String sAccNo = tDetail.getChildText("AccNo");
					String tAppntName = tDetail.getChildText("AppntName");
					String tAppntIDType = tRun.exChangeNum(tDetail.getChildText("AppntIDType"), null, "IDTYPE", null);
					String tAppntIDNo = tDetail.getChildText("AppntIDNo");
					String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");

					String tFlag = tDetail.getChildText("Flag");

					String tIsBankCust = tDetail.getChildText("IsBankCust");

					String tMarkRemark1 = tDetail.getChildText("Bak1");
					String tMarkRemark2 = tDetail.getChildText("Bak1");

					String tMark = "";
					outBody += tSignNo + "※" + tAccType + "※" + sAccNo + "※" + tAppntName + "※" + tAppntIDType + "※" + tAppntIDNo + "※"
							+ tProposalPrtNo + "※" + tFlag + "※" + tIsBankCust + "※" + tMarkRemark1 + "※" + tMarkRemark2 + "※" + tMark + "\n";
				}
				// 输出记录至txt文件
				if ((i + 1) * maxNo < tCount)
				{
					outHead = "F※" + tComCode + "※" + "1002※500※" + "※" + "※N" + "\n";
				}
				else
				{
					outHead = "F※" + tComCode + "※" + "1002※" + (tCount - (i * maxNo)) + "※" + "※" + "※N" + "\n";
				}
				out = outHead + outBody;
				cLogger.info("第" + i + "个文件生成返回文件总记录" + out);
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
		cLogger.info("Out SpdbCustSignSvc.receive()...");
	}

	private String exChangeNum(String num1, String num2, String type, String type2)
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
		return tResult;
	}

	public static void main(String args[]) throws MidplatException
	{
		SpdbCustSignSvc tBlc = new SpdbCustSignSvc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbCustSignSvc.class);

		mLogger.info("浦发批量签约上传批处理程序开始...");
		args = new String[1];
		args[0] = "20170407";
		// 用于补批量签约上传批处理，设置补批量签约上传批处理日期
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
		mLogger.info("浦发批量签约上传批处理成功结束！");
	}
}