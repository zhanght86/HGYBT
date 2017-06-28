/**
 * 新农行非实时出单结果文件
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

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
import com.sinosoft.midplat.newabc.format.NonRealTimeContRstOutXsl;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: NonRealTimeContRst
 * @Description: 非实时出单结果文件
 * @author sinosoft
 * @date 2017-2-27 下午8:12:08
 */
public class NonRealTimeContRst extends TimerTask implements XmlTag
{
	//生成一个本类的日志对象
	protected final static Logger cLogger = Logger.getLogger(NonRealTimeContRst.class);
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时初始化， 确保在该次对账处理的整个过程中日期一致性， 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	//返回信息
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	//交易流水号
	private String stranNo = null;
	//当前日期
	private String curDate = null;
	
	protected String getCoreNum()
	{
		return cConfigEle.getChildText("ComCode");
	}

	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into NonRealTimeContRst.deal()...");
		// 发送请求报文至核心
		Document cInXmlDoc = sendRequest();
		//测试
		JdomUtil.print(cInXmlDoc);
		try
		{
			cTranLogDB = insertTranLog();
			// 处理核心返回报文
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRst).call(cInXmlDoc);

			cLogger.info("核心返回的非实时对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc = NonRealTimeContRstOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("转换后的对账结果报文：");
			JdomUtil.print(cOutXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // 交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			receive(cOutXmlDoc, ttLocalDir);
			
			Element mComCodeEle = cConfigEle.getChild("ComCode");
			String mFileName = "FRESULT" + mComCodeEle.getText() + "." + cCurDate;
			cLogger.info("今天生成的文件名为：" + mFileName);
			if(!new BatUtils().upLoadFile(ttLocalDir+"/"+mFileName, "02","2008",cCurDate,mFileName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"上传失败！");
			}
			
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("非实时出单结果文件对账结果代码：" + reCode + "      结果说明：" + reMsg);
			
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
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		}
		
		cLogger.info(cConfigEle.getChildText(name)+"文件上传成功！");
		cLogger.info("Out NonRealTimeContRst.deal()...");
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

	/**
	 * 
	 * sendRequest 组织核心请求报文
	 * 发送请求报文
	 * @return 标准请求报文
	 */
	@SuppressWarnings("static-access")
	private Document sendRequest()
	{
		//进入NonRealTimeContRst发送请求报文方法...
		cLogger.info("Into NonRealTimeContRst.sendRequest()...");
		//新建根节点
		ElementLis TranData = new ElementLis("TranData");
		//新建报文头节点加入到根节点
		ElementLis Head = new ElementLis("Head", TranData);
		//新建交易日期节点加入到报文头节点
		new ElementLis("TranDate", cCurDate, Head);
		//新建交易时间节点加入到报文头节点
		new ElementLis("TranTime", DateUtil.getDateStr(new Date(), "HHmmss"), Head);
		//新建交易机构代码节点加入到报文头节点
		new ElementLis("TranCom", "05", Head);
		//新建省市代码节点加入到报文头节点
		new ElementLis("ZoneNo", "11", Head);
		//新建银行网点节点加入到报文头节点
		new ElementLis("NodeNo", cConfigEle.getChildText("NodeNo"), Head);
		//新建银行代码节点加入到报文头节点
		new ElementLis("BankCode", "0102", Head);
		//新建柜员代码节点加入到报文头节点
		new ElementLis("TellerNo", "11010102110", Head);
		//公共函数
		PubFun1 p = new PubFun1();
		//获取创建16位最大号为交易流水号
		stranNo = p.CreateMaxNo("TransNo", 16);
		//新建交易流水号节点加入到报文头节点
		new ElementLis("TranNo", stranNo, Head);
		//新建交易码节点加入到报文头节点
		new ElementLis("FuncFlag", cConfigEle.getChildText("funcFlag"), Head);// 交易代码
		//新建报文体节点加入到根节点
		new ElementLis("Body", TranData);
		//新建报文
		Document pXmlDoc = new Document(TranData);
		//从NonRealTimeContRst发送请求报文方法出来+交易日期
		cLogger.info("Out NonRealTimeContRst.sendRequest()!" + cCurDate);
		return pXmlDoc;
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		cLogger.debug("Into NonRealTimeContRst.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2008");
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
		cLogger.debug("Out NonRealTimeContRst.insertTranLog()!");
		return mTranLogDB;
	}
	
	// 处理核心返回报文并存储在固定路径
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception
	{
		cLogger.info("Into NonRealTimeContRst.receive()..." + cCurDate);
		JdomUtil.print(cOutXmlDoc);

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
		String tCount = null;// 明细记录总笔数
		String tSumMoney = null;// 总金额
		Element tBody = tRoot.getChild("Body");
		// try {

		@SuppressWarnings("unchecked")
		List<Element> list = tBody.getChildren("Detail");

		tCount = Integer.toString(list.size());
		tSumMoney = "0000";
		long sum = 0;

		cLogger.info("生成返回文件头记录" + outHead);
		cLogger.info("生成返回文件总记录" + list.size());

		/*
		 * 文件第一行：（汇总信息） 格式：保险公司代码|银行代码|总记录数|总金额| 文件其他内容：（明细记录）
		 * 交易日期|试算申请顺序号|投保人姓名
		 * |投保人证件类型|投保人证件号码|险种编码|产品编码|保单号|受理日期|投保人与被保人关系|被保人姓名|
		 * 被保人证件类型|被保人证件号码|保费|保额|缴费账户|缴费方式|缴费期限|保单到期日|投保分数|个性化费率|保单印刷号|附加险个数|
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

					fileName = "FRESULT" + tComCode + "." + cCurDate;
				}
				else
				{
					serialNo = Integer.toString(i);
					// fileName =
					// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "FRESULT" + tComCode + "." + cCurDate;
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
					String tTranDate = tDetail.getChildText("TranDate");
					tTranDate = DateUtil.date10to8(tTranDate);
					String tApplyNo = tDetail.getChildText("ApplyNo");
					String tAppName = tDetail.getChild("Appnt").getChildText("AppntName");
					String tAppIDtype = tDetail.getChild("Appnt").getChildText("IDType");
					String tAppIDno = tDetail.getChild("Appnt").getChildText("IDNo");
					String tRiskCode = tDetail.getChildText("RiskCode");
					String tProdCode = tDetail.getChildText("ProdCode");
					String tContNo = tDetail.getChildText("ContNo");
					String tApplyDate = tDetail.getChildText("ApplyDate");
					tApplyDate = DateUtil.date10to8(tApplyDate);
					String tRelationToInusre = tDetail.getChildText("RelationToInusre");
					String tInsuName = tDetail.getChild("Insured").getChildText("Name");
					String tInsuDtype = tDetail.getChild("Insured").getChildText("IDType");
					String tInsuDno = tDetail.getChild("Insured").getChildText("IDNo");
					String tPrem = tDetail.getChildText("Prem");
					// 处理金额
					long prem = Long.parseLong(tPrem);
					sum = sum + prem;
					tPrem = NumberUtil.fenToYuan(tPrem);
					String tAmnt = tDetail.getChildText("Amnt");
					tAmnt = NumberUtil.fenToYuan(tAmnt);
					String tAccNo = tDetail.getChildText("AccNo");
					String tPayIntv = tDetail.getChildText("PayIntv");
					String tPayEndDate = tDetail.getChildText("PayEndDate");
					tPayEndDate = DateUtil.date10to8(tPayEndDate);
					String tContEndDate = tDetail.getChildText("ContEndDate");
					tContEndDate = DateUtil.date10to8(tContEndDate);
					String tMult = tDetail.getChildText("Mult");
					double dmult = Double.parseDouble(tMult);
					int imult = (int) dmult;
					tMult = String.valueOf(imult);
					String tContPrtNo = tDetail.getChildText("ContPrtNo");
					if(tContPrtNo.length()>13){
						tContPrtNo = tContPrtNo.substring(0, 13);
					}else{
						tContPrtNo="";
					}
					String tState = tDetail.getChildText("State");
					String tRtext = tDetail.getChildText("Rtext");
					String tSpecialRate = tDetail.getChildText("SpecialRate");
					String tEtraRiskCnt = tDetail.getChildText("EtraRiskCnt");

					outBody += tTranDate + "|" + tApplyNo + "|" + tAppName + "|" + tAppIDtype + "|" + tAppIDno + "|" + tRiskCode + "|" + tProdCode + "|" + tContNo + "|" + tApplyDate + "|" + tRelationToInusre + "|" + tInsuName + "|" + tInsuDtype + "|" + tInsuDno + "|" + tPrem + "|" + tAmnt + "|" + tAccNo + "|" + tPayIntv + "|" + tPayEndDate + "|" + tContEndDate + "|" + tMult + "|" + tSpecialRate + "|" + tContPrtNo + "|"  + tState + "|"  + tRtext + "|" + tEtraRiskCnt + "|" + "\n";
				}
				// 输出记录至txt文件
				tSumMoney = String.valueOf(sum);
				tSumMoney = NumberUtil.fenToYuan(tSumMoney);
				System.out.println("总金额：" + tSumMoney);
				if (tSumMoney.equals("") || tSumMoney == null)
				{
					tSumMoney = "0.00";
				}
				outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
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
			// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
			fileName = "FRESULT" + tComCode + "." + cCurDate;
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
			tCount = "0";
			tSumMoney = "0.00";
			outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
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
		cLogger.info("Out NonRealTimeContRst.receive()!");
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeContRst.run()...");

		// 清空上一次结果信息
		cResultMsg = null;

		try
		{
			cConfigEle = BatUtils.getConfigEle("2008"); // 得到bat.xml文件中的对应节点.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			cLogger.info(cConfigEle.getContent());
			
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

		cLogger.info("Out NonRealTimeContRst.run()!");
	}

	// 备份月文件，比如yyyyMM01当日，系统会在目录localDir 建立/yyyy/yyyyMM，
	// 然后把所有文件移动到该目录，但是yyyyMM01的文件除外
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NonRealTimeContRst.bakFiles()...");

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
				Date date=new Date();
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

		cLogger.info("Out NonRealTimeContRst.bakFiles()!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonRealTimeContRst.main");
		cLogger.info("开始非实时出单结果文件对账程序...");
		
		NonRealTimeContRst abcAES = new NonRealTimeContRst();

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
		
		cLogger.info("结束非实时出单结果文件对账程序!");
	}
	
}
