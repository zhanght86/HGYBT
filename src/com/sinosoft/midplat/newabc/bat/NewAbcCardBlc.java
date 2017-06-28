package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NewAbcCardBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;
	
	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NewAbcCardBlc.run()...");
		try {
			cResultMsg = null;
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2000"); // 得到bat.xml文件中的对应节点.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date()).replace("-","");
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			// 初始化文件名称		POLICY  +公司编号 	+.	 	+ 当前时间
			String mFIleName = "POLICY"+mCorNo+"." + cCurDate; 
			if(!new BatUtils().downLoadFile(mFIleName, "02","2000",cCurDate)){
				throw new MidplatException("新农行单证对账文件下载异常");
			}
			// 处理对账
			cLogger.info("处理新农行单证对账开始...");
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\POLICY1147.20170607";
			cLogger.info(myFilePath);
			// 得到请求标准报文
			Document cInXmlDoc = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);
			String reCode =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
		    cTranLogDB.setRCode(reCode);
        	cTranLogDB.setRText(reMsg);
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
         	
	        // 每月1日备份上月的对账文件
			if ("01".equals(DateUtil.getDateStr(new Date(), "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
         	
         	cResultMsg = reMsg;
		} catch (Exception e) {
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name")+ "  处理异常..."+e.getMessage());
			e.printStackTrace();
		    cTranLogDB.setRCode("1");
        	cTranLogDB.setRText(e.getMessage());
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
		}finally {
			cCurDate = "";
		}
		cLogger.info("处理新农行单证对账结束!");
		cLogger.info("Out NewAbcCardBlc.run()!");
	}
	protected TranLogDB insertTranLog() throws MidplatException {
	    this.cLogger.debug("Into NewAbcCardBlc.insertTranLog()...");
	    TranLogDB mTranLogDB = new TranLogDB();
	    mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
	    mTranLogDB.setTranCom("05");
	    mTranLogDB.setNodeNo("0000000");
	    mTranLogDB.setFuncFlag("2000");
	    mTranLogDB.setOperator("YBT");
	    mTranLogDB.setTranNo(NoFactory.nextTranLogNo()+"");
	    mTranLogDB.setTranDate(DateUtil.getCur8Date());
	    mTranLogDB.setTranTime(DateUtil.getCur6Time());
	    Date mCurDate = new Date();
	    mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setUsedTime(0);
	    mTranLogDB.setBak1("");
	    mTranLogDB.setRCode("-1");
	    mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
	    mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
	    mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
	    if (!(mTranLogDB.insert())) {
	      this.cLogger.error(mTranLogDB.mErrors.getFirstError());
	      throw new MidplatException("插入日志失败！");
	    }
	    this.cLogger.debug("Out NewAbcCardBlc.insertTranLog()!");
	    return mTranLogDB;
	  }
	
	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NewAbcCardBlc.parse()...");
		String mCharset = "";
		// 组织根节点以及Head节点内容
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(cCurDate);
		String cTranDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..." + cCurDate);
		cLogger.info(" 当前日期为..." + cTranDate);

		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0
		if (!cCurDate.equals(cTranDate)){
			tBalanceFlag = "1";
		}
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(new Date(), "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("com"));
		
		Element mZoneNo = new Element(ZoneNo);
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText(NodeNo));

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));

		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);

		// 报文头结点增加核心的银行编码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(NewAbcConf.newInstance().getConf().getRootElement().getChildText("BankCode"));

		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mBankCode).addContent(mTranCom)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mBalanceFlag);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
			/*
			 * 处理逻辑：柜面渠道，出单成功的单证对账；当日撤单数据单证对账；冲正交易不处理单证对账
			 * （汇总信息）： 保险公司代码|总记录数|总金额|成功总记录数|成功总金额 
			 * （明细记录）： 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|交易状态
			 */
			@SuppressWarnings("unused")
			String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
			// 把成功的记录独取出来发给核心
			Element mCountEle = new Element(Count);
			Element mBodyEle = new Element(Body);
			int count=0;
			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());){
				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg)){
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}
				String[] tSubMsgs = tLineMsg.split("\\|", -1);
				if (!"01".equals(tSubMsgs[6])){
					cLogger.warn("非承保保单，直接跳过，继续下一条！");
					continue;
				}
				String tSqlStr = new StringBuffer("select bak5 from cont where contNo='")
					.append(tSubMsgs[4]).append('\'').append(" and tranCom=").append(05)
					.append(" and state in(").append(AblifeCodeDef.ContState_Cancel).append(",")
					.append(AblifeCodeDef.ContState_Sign).append(")").toString();
				cLogger.info(tSqlStr);
				SSRS ssrs = new ExeSQL().execSQL(tSqlStr);
				//判断是否是当日撤单数据，是：单证对账；否：不对账
				if(ssrs.getMaxRow()>0){
					//非柜面渠道跳过
					if(!ssrs.GetText(1, 1).equals("0")){
						cLogger.warn("电子渠道数据，直接跳过，继续下一条！");
						continue;
					}else{
						cLogger.info("柜面渠道数据...");
					}
				}else{
					cLogger.warn("非当日撤单or非承保数据，直接跳过，继续下一条！");
					continue;
				}
				//保单号
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[4]);
				// 从日志表中查询保单印刷号
				String tContPrtNoSql = new StringBuffer("select otherno from tranlog where funcflag=")
					.append(1004).append(" and contno='").append(tContNoEle.getText()).append('\'')
					.append(" and trancom=").append(05).append(" and rcode=").append(0).toString();
				this.cLogger.info("保单印刷号sql:" + tContPrtNoSql);
				String tContPrtNo = new ExeSQL().getOneValue(tContPrtNoSql);
				//如果查询单证印刷号为空，则抛出异常
				if(StringUtils.isEmpty(tContPrtNo)){
					throw new MidplatException("单证对账失败：保单" + tContNoEle.getText() + "的单证号查询失败");
				}
				//单证类型
				Element tCardTypeEle = new Element("CardType");
				if (!"".equals(tContPrtNo) && tContPrtNo != null){
					tCardTypeEle.setText(tContPrtNo.substring(0, 5));
				}
				//保单印刷号
				Element tCardNoEle = new Element(CardNo);
				tCardNoEle.setText(tContPrtNo);
				//核心单证状态：4：核销；6：使用作废；9：停止作废
				Element tCardStateEle = new Element("CardState");
				tCardStateEle.setText("4");
				Element tAgentCom = new Element(AgentCom);
				Element mTellerNoEle = new Element(TellerNo);
				Element tTranNoEle = new Element(TranNo);
				count++;
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
			mCountEle.setText(count+"");
			mBodyEle.addContent(mCountEle);
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // 关闭流
		}catch(Exception e){
			e.printStackTrace();
			throw new MidplatException("解析对账文件失败！"+e.getMessage());
		}
		cLogger.info("Out NewAbcCardBlc.parse()!");
		return new Document(mTranData); 
	}

	// 备份月文件，比如yyyyMM01当日，系统会在目录localDir 建立/yyyy/yyyyMM，
	// 然后把所有文件移动到该目录，但是yyyyMM01的文件除外
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NewAbcCardBlc.bakFiles...");

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

		cLogger.info("Out NewAbcCardBlc.bakFiles!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NewAbcCardBlc.main");
		mLogger.info("新农行单证对账程序启动...");

		NewAbcCardBlc abcAES = new NewAbcCardBlc();

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
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("新农行单证对账程序完成!");
	}
	
}
