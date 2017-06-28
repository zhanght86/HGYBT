/**
 * 新农行退保犹撤结果文件
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.NoTakenBalanceRstOutXsl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NoTakenBalanceRst extends TimerTask implements XmlTag
{
	protected final static Logger cLogger = Logger.getLogger(NoTakenBalanceRst.class);
	
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	private String sTranNo = null;
	private String curDate = null;
	
	protected String getCoreNum()
	{
		return cConfigEle.getChildText("ComCode");
	}
	
	protected Element getBody()
	{
		cLogger.info("Into NoTakenBalanceRst.getBody()...");

		Element mBody = new Element(Body);
		try {
			// contblcdtl里查找昨日银行回盘文件中失败的
			String rTranDate = String.valueOf(DateUtil.get8Date(new SimpleDateFormat("yyyyMMdd").parse(cCurDate).getTime() - 86400000L));
			String mSqlStr10 = "select * from ContBlcDtl where trancom='05' " + " and trandate='" + rTranDate + "'" + " and type='24'" + " and bak5='1'";
			SSRS ssrs = new ExeSQL().execSQL(mSqlStr10);
			for (int i = 0; i < ssrs.getMaxRow(); i++)
			{
				ElementLis Detail = new ElementLis("Detail", mBody);
				new ElementLis("BusiType", ssrs.GetText(i + 1, 18), Detail);
				new ElementLis("ContNo", ssrs.GetText(i + 1, 3), Detail);
				new ElementLis("AppntName", ssrs.GetText(i + 1, 12), Detail);
				new ElementLis("Status", ssrs.GetText(i + 1, 18), Detail);
				new ElementLis("TranDate", ssrs.GetText(i + 1, 5), Detail);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cLogger.info("Out NoTakenBalanceRst.getBody()!");
		return mBody;
	}

	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into NoTakenBalanceRst.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		try
		{
			cTranLogDB = insertTranLog();
			// 处理核心返回报文
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NoTakenBalanceRst).call(cInXmlDoc);

			// 使用样式表对关系代码，证件类型，险种代码，缴费方式转换成农行的，不改变核心标准报文结构
			cLogger.info("核心返回的非实时对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc = NoTakenBalanceRstOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("转换后的对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // 交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}

			receive(cOutXmlDoc, ttLocalDir);
			
			Element mComCodeEle = cConfigEle.getChild("ComCode");
			String mFileName = "INVALID" + mComCodeEle.getText() + "." + cCurDate;
			cLogger.info("今天生成的文件名为：" + mFileName);
			if(!new BatUtils().upLoadFile(ttLocalDir+"/"+mFileName, "02", "2004",cCurDate,mFileName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"上传失败！");
			}
			
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("退保犹撤数据文件对账结果代码：" + reCode + "      结果说明：" + reMsg);
			
			if (cTranLogDB != null) {
				cTranLogDB.setRCode(reCode);
				cTranLogDB.setRText(reMsg);
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
			}
			
			cResultMsg = reMsg;
		}catch (Exception e){
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  对账处理异常!");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("退保犹撤数据文件处理失败");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		}
		
		cLogger.info(cConfigEle.getChildText(name)+"文件上传成功！");
		cLogger.info("Out NoTakenBalanceRst.deal()!");
	}

	/**
	 * 设置对账文件名，处理每个月最后一天生成的文件名为下个月第一天
	 */
	@SuppressWarnings("deprecation")
	protected String dealFileName() throws Exception
	{

		// 设置生成文件日期，一定注意每个月最后一天的日期，与农行高绍洁约定，
		// 当日生成的退保犹撤数据文件和非实时出单结果文件的文件名为次日，农行会在次日处理
		// curDate=当前日期
		// fileDate=生成文件日期
		String gfileDate = "";
		Calendar c = Calendar.getInstance();
		curDate = String.valueOf(DateUtil.get8Date(c.getTime()));
		System.out.println("今天是：" + curDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(new Date());
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		System.out.println("最后一天：" + DateUtil.get8Date(lastDate));

		if (String.valueOf(DateUtil.get8Date(lastDate)) == curDate || String.valueOf(DateUtil.get8Date(lastDate)).equals(curDate))
		{
			System.out.println("今天是本月最后一天");
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			gfileDate = String.valueOf(DateUtil.get8Date(c.getTime()));
			System.out.println("下个月的第一天: " + gfileDate);
			System.out.println("今天的文件日期：" + gfileDate);
		}
		else
		{
			System.out.println("今天不是本月最后一天");

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			gfileDate = String.valueOf(DateUtil.date10to8((sdf.format(date))));
			System.out.println("今天生成的文件日期：" + gfileDate);
		}
		return gfileDate;
	}
	
	// 创建请求报文
	@SuppressWarnings("static-access")
	private Document sendRequest()
	{
		cLogger.info("Into NoTakenBalanceRst.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Head = new ElementLis("Head", TranData);
		new ElementLis("TranDate",cCurDate, Head);
		new ElementLis("TranTime", DateUtil.getDateStr(new Date(), "HHmmss"), Head);
		new ElementLis("TranCom", "05", Head);
		new ElementLis("ZoneNo", "11", Head);
		new ElementLis("NodeNo", cConfigEle.getChildText("NodeNo"), Head);
		new ElementLis("BankCode", "0102", Head);
		new ElementLis("TellerNo", "11010102110", Head);
		PubFun1 p = new PubFun1();
		sTranNo = p.CreateMaxNo("TransNo", 16);
		new ElementLis("TranNo", sTranNo, Head);
		new ElementLis("FuncFlag", cConfigEle.getChildText("funcFlag"), Head);// 交易代码
		Element Body = getBody();
		TranData.addContent(Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out NoTakenBalanceRst.sendRequest()!" + cCurDate);
		return pXmlDoc;
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		cLogger.debug("Into NoTakenBalanceRst.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2004");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
		mTranLogDB.setTranDate(DateUtil.getCur8Date());
		mTranLogDB.setTranTime(DateUtil.getCur6Time());
		Date mCurDate = new Date();
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(0);
		mTranLogDB.setUsedTime(0);
		mTranLogDB.setBak1("");
		mTranLogDB.setRCode("-1");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!(mTranLogDB.insert())) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		cLogger.debug("Out NoTakenBalanceRst.insertTranLog()!");
		return mTranLogDB;
	}
	
	// 处理核心返回报文并存储在固定路径
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception
	{
		cLogger.info("Into NoTakenBalanceRst.receive()..." + cCurDate);
		JdomUtil.print(cOutXmlDoc);

		// fileDate=dealFileName();
		String outHead = null;// 头记录
		@SuppressWarnings("unused")
		String serialNo = "";// 序号
		String fileName = "";// 文件名称
		String out = "";
		String outBody = "";
		int maxNo = 100;// 一个txt中允许最多的明细记录数 测试的话，只需要把这个值换掉即可
		// 头记录处理
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot + "           ===========      " + tHead);
		String tComCode = cConfigEle.getChildText("ComCode");// 保险公司代码
		String tSumCount = "0";// 明细记录总笔数
		String tSumMoney = "0";// 金额总笔数
		String tCount1 = "0";// 犹撤明细记录总笔数
		String tCount2 = "0";// 满期明细记录总笔数
		String tCount3 = "0";// 退保明细记录总笔数
		String tSumMoney1 = "0";// 犹撤总金额
		String tSumMoney2 = "0";// 满期总金额
		String tSumMoney3 = "0";// 退保总金额
		Element tBody = tRoot.getChild("Body");
		@SuppressWarnings("unchecked")
		List<Element> list = tBody.getChildren("Detail");
		tSumCount = Integer.toString(list.size());
		long sum1 = 0;
		long sum2 = 0;
		long sum3 = 0;
		long count1 = 0;
		long count2 = 0;
		long count3 = 0;

		cLogger.info("生成返回文件头记录" + outHead);
		cLogger.info("生成返回文件总记录" + list.size());

		/*
		 * 文件第一行：（汇总信息）
		 * 格式：保险公司代码|银行代码|总记录数|总金额|犹撤总记录数|犹撤总金额|满期总记录数|满期总金额|退保总记录数|退保总金额|
		 * 文件其他内容：（明细记录）
		 * 业务类别|交易日期|保单号|申请人姓名|受理渠道|投保人姓名|投保人证件类型|投保人证件号码|申请状态|领取金额|
		 */

		if (list.size() != 0)
		{
			/*
			 * 通过总数和最大数取得所能生成文件的数量
			 */
			for (int i = 0; i <= list.size() / maxNo; i++)
			{
				if (i < 10)
				{
					serialNo = "0" + Integer.toString(i);
					// fileName =
					// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "INVALID" + tComCode + "." + cCurDate;
				}
				else
				{
					serialNo = Integer.toString(i);
					// fileName =
					// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "INVALID" + tComCode + "." + cCurDate;
				}
				File file = new File(ttLocalDir + "/" + fileName);
				// 如果文件不存在创建文件
				if (!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
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
					String tBusiType = tDetail.getChildText("BusiType");
					String tTranDate = tDetail.getChildText("TranDate");
					tTranDate = DateUtil.date10to8(tTranDate);
					String tContNo = tDetail.getChildText("ContNo");
					String tAppntName = tDetail.getChildText("AppntName");
					String tApplyState = tDetail.getChildText("Status");
					// 受理渠道0保险公司，1银行
					String tChannel = tDetail.getChildText("Channel");
					//如果渠道为空，则默认渠道编码为0-保险公司
					if(StringUtils.isEmpty(tChannel))
					{
						tChannel = "0";
					}
					String tPrem = tDetail.getChildText("Prem");
					// 核心暂时没有满期业务 核心WT犹撤，CT退保
					if (tBusiType.equals("01"))
					{
						// tBusiType="01";
						// 处理金额
						long prem1 = Long.parseLong(tPrem);
						sum1 = sum1 + prem1;
						count1++;
						tSumMoney1 = String.valueOf(sum1);
					}
					else if (tBusiType.equals("03"))
					{
						// tBusiType="03";
						// 处理金额
						long prem3 = Long.parseLong(tPrem);
						sum3 = sum3 + prem3;
						count3++;
						tSumMoney3 = String.valueOf(sum3);
					}
					tPrem = NumberUtil.fenToYuan(tPrem);
					if (tChannel == null || tChannel.equals(""))
					{
						tChannel = "0";
					}
					outBody += tBusiType + "|" + tTranDate + "|" + tContNo + "|" + tAppntName + "|" + tChannel + "|" + "|" + "|" + "|" + tApplyState + "|" + tPrem + "|" + "\n";
				}
				// 输出记录至txt文件
				tCount1 = String.valueOf(count1);
				tCount2 = String.valueOf(count2);
				tCount3 = String.valueOf(count3);
				tSumCount = String.valueOf(count1 + count2 + count3);
				System.out.println("a:" + tSumMoney1 + "b:" + tSumMoney2 + "b:" + tSumMoney3);
				tSumMoney1 = NumberUtil.fenToYuan(tSumMoney1);
				tSumMoney2 = NumberUtil.fenToYuan(tSumMoney2);
				tSumMoney3 = NumberUtil.fenToYuan(tSumMoney3);
				tSumMoney = NumberUtil.fenToYuan(String.valueOf(sum1 + sum2 + sum3));
				System.out.println("总金额：" + tSumMoney);
				if (tSumMoney.equals("") || tSumMoney == null)
				{
					tSumMoney = "0.00";
				}
				if (tSumMoney1.equals("") || tSumMoney1 == null)
				{
					tSumMoney1 = "0.00";
				}
				if (tSumMoney2.equals("") || tSumMoney2 == null)
				{
					tSumMoney2 = "0.00";
				}
				if (tSumMoney3.equals("") || tSumMoney3 == null)
				{
					tSumMoney3 = "0.00";
				}
				if (tCount1.equals("") || tCount1 == null)
				{
					tCount1 = "0";
				}
				if (tCount2.equals("") || tCount2 == null)
				{
					tCount2 = "0";
				}
				if (tCount3.equals("") || tCount3 == null)
				{
					tCount3 = "0";
				}
				outHead = tComCode + "|" + "03" + "|" + tSumCount + "|" + tSumMoney + "|" + tCount1 + "|" + tSumMoney1 + "|" + tCount2 + "|" + tSumMoney2 + "|" + tCount3 + "|" + tSumMoney3 + "|" + "\n";
				out = outHead + outBody;
				cLogger.info("第" + (i+1) + "个文件生成返回文件总记录" + out);
				byte[] m = out.getBytes("GBK");
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
				// 删除文件，避免生成多余文件
				if (i * maxNo == list.size())
				{
					file.delete();
				}
				// 清空明细记录和总记录
				outBody = "";
				out = "";
			}
		}
		else
		{
			// fileName =
			// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
			fileName = "INVALID" + tComCode + "." + cCurDate;
			File file = new File(ttLocalDir + "/" + fileName);
			// 如果文件不存在创建文件
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			// 输出记录至txt文件
			tSumCount = "0";
			tSumMoney = "0.00";
			tCount1 = "0";
			tSumMoney1 = "0.00";
			tCount2 = "0";
			tSumMoney2 = "0.00";
			tCount3 = "0";
			tSumMoney3 = "0.00";
			System.out.println("总金额：" + tSumMoney);
			outHead = tComCode + "|" + "03" + "|" + tSumCount + "|" + tSumMoney + "|" + tCount1 + "|" + tSumMoney1 + "|" + tCount2 + "|" + tSumMoney2 + "|" + tCount3 + "|" + tSumMoney3 + "|" + "\n";
			out = outHead;
			cLogger.info("只返回头文件，核心未返回退保值" + out);
			byte[] m = out.getBytes("GBK");
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
		}
		System.out.println("文件名：" + fileName);
		cLogger.info("Out NoTakenBalanceRst.receive()!");
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NoTakenBalanceRst.run()...");

		// 清空上一次结果信息
		cResultMsg = null;

		try
		{
			cConfigEle = BatUtils.getConfigEle("2004"); // 得到bat.xml文件中的对应节点.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			System.out.println(cConfigEle.getContent());
			
			try
			{
				String ttLocalDir = cConfigEle.getChildTextTrim("FilePath");
				
				deal(ttLocalDir);
			}
			catch (Exception ex)
			{
				cLogger.error("生成标准对账报文出错！", ex);

				// 获取标准对账报文
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				Element tTranData = new Element(TranData);
				Element tHeadEle = ((Document) sendRequest().clone()).getRootElement().getChild("Head");
				tTranData.addContent(tHeadEle);
				tTranData.addContent(ttError);
			}

			// 每月1日备份上月的对账文件
			if ("01".equals(DateUtil.getDateStr(new Date(), "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
		}
		catch (Throwable ex)
		{
			cResultMsg = ex.toString();
			cLogger.error("交易出错！", ex);
		}

		cCurDate = null; // 每次跑完，清空日期

		cLogger.info("Out NoTakenBalanceRst.run()!");
	}

	// 备份月文件，比如yyyyMM01当日，系统会在目录localDir 建立/yyyy/yyyyMM，
	// 然后把所有文件移动到该目录，但是yyyyMM01的文件除外
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NoTakenBalanceRst.bakFiles()...");

		if (null == pFileDir || "".equals(pFileDir))
		{
			cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory())
		{
			cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
			return;
		}

		File[] mOldFiles = mDirFile.listFiles(new FileFilter()
		{
			public boolean accept(File pFile)
			{
				if (!pFile.isFile())
				{
					return false;
				}

				Calendar tCurCalendar = Calendar.getInstance();
				tCurCalendar.setTime(new Date());

				Calendar tFileCalendar = Calendar.getInstance();
				tFileCalendar.setTime(new Date(pFile.lastModified()));

				return tFileCalendar.before(tCurCalendar);
			}
		});

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles)
		{
			try
			{
				String fileName_date = tFile.getName().substring(tFile.getName().length() - 8);
				Date date = new Date();
				if (!fileName_date.equals(String.valueOf(DateUtil.get8Date(date))))
				{
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			}
			catch (IOException ex)
			{
				cLogger.error(tFile.getAbsoluteFile() + "备份失败！", ex);
			}
		}

		cLogger.info("Out NoTakenBalanceRst.bakFiles()!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NoTakenBalanceRst.main");
		cLogger.info("开始退保犹撤数据文件对账程序...");
		
		NoTakenBalanceRst abcAES = new NoTakenBalanceRst();
		// 用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("日期格式有误，应为yyyy-MM-dd！" + args[0]);
			}
		}
		abcAES.run();
		
		cLogger.info("结束退保犹撤数据文件对账程序!");
	}
}
