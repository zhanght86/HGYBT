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
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.VData;

/**
 * @ClassName: NonReaTimeIssWatDetail
 * @Description: 非实时出单流水明细
 * @author sinosoft
 * @date 2017-4-7 下午2:09:59
 */
public class NonReaTimeIssWatDetail extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;

	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonReaTimeIssWatDetail.run()...");
		try {
			cResultMsg = null;
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2002"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode").trim();

			String mFIleName = "FAPPLY" + mComCode + "." + cCurDate; // 初始化文件名称
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2002", cCurDate)) {
				cTranLogDB.setRCode("1");
				cTranLogDB.setRText("非实时出单流水明细文件下载异常");
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
				throw new MidplatException("非实时出单流水明细文件下载异常");
			}

			//处理对账
			cLogger.info("处理非实时出单流水明细文件开始...");
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/BQAPPLY010079.20170405";
			cLogger.info(myFilePath);
			
			// 得到请求标准报文
			Document cInXmlDoc = parse(myFilePath);
			JdomUtil.print(cInXmlDoc);
			//保存对账明细
			saveDetails(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContBlc).call(cInXmlDoc);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("非实时出单流水明细对账结果代码：" + reCode + "      结果说明：" + reMsg);
			
			if (cTranLogDB != null) {
				cTranLogDB.setRCode(reCode);
				cTranLogDB.setRText(reMsg);
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
			}
			
			 // 每月1日备份上月的对账文件
			if ("01".equals(DateUtil.getDateStr(new Date(), "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
			
			cResultMsg = reMsg;
		} catch (Exception e) {
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  对账处理异常!");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("非实时出单流水明细文件处理失败");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		
		cLogger.info("处理非实时出单流水明细文件结束!");
		cLogger.info("Out NonReaTimeIssWatDetail.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NonReaTimeIssWatDetail.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2002");
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
			this.cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		this.cLogger.debug("Out NonReaTimeIssWatDetail.insertTranLog()!");
		return mTranLogDB;
	}

	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		byte[] mInTotalBytes = new byte[mBodyBytes.length + 73];

		// 剪切xml头部之后报文体
		String mBodyStr = new String(mBodyBytes);
		byte[] xBodyBytes = mBodyStr.substring(mBodyStr.indexOf("<ABCB2I>"),
				mBodyStr.length()).getBytes();
		String xBodyBytesLength = String.valueOf(xBodyBytes.length);
		System.arraycopy("X".getBytes(), 0, mInTotalBytes, 0,
				"X".getBytes().length);// 报文类型
		System.arraycopy("1.0".getBytes(), 0, mInTotalBytes, 1,
				"1.0".getBytes().length);// 版本号
		System.arraycopy(xBodyBytesLength.getBytes(), 0, mInTotalBytes, 4,
				xBodyBytesLength.length());// 报文体长度
		byte[] mComCode = cConfigEle.getChildText("ComCode").trim().getBytes();
		System.arraycopy(mComCode, 0, mInTotalBytes, 12, mComCode.length);// 公司代码
		System.arraycopy("1".getBytes(), 0, mInTotalBytes, 20,
				"1".getBytes().length);// 加密标示 0-不加密；1-关键数据加密；2-报文整体加密。
		System.arraycopy("RSA".getBytes(), 0, mInTotalBytes, 21,
				"RSA".getBytes().length);// 加密算法
		System.arraycopy("0".getBytes(), 0, mInTotalBytes, 22,
				"0".getBytes().length);// 数据压缩标志 0-不压缩；1-压缩
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 23,
				" ".getBytes().length);// 数据压缩算法
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 24,
				" ".getBytes().length);// 摘要算法
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 25,
				" ".getBytes().length);// 摘要
		System.arraycopy("00000000".getBytes(), 0, mInTotalBytes, 65,
				"00000000".getBytes().length);// 预留字段

		System.arraycopy(xBodyBytes, 0, mInTotalBytes, 73, xBodyBytes.length);// 预留字段
		return mInTotalBytes;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NonReaTimeIssWatDetail.parse()...");
		String mCharset = "";
		// 组织根节点以及BaseInfo节点内容
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(cCurDate);
		String cTranDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..."+cCurDate);
		cLogger.info(" 当前日期为..."+cTranDate);
		
		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0
		if(!cCurDate.equals(cTranDate)){
			tBalanceFlag = "1";
		}
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(new Date(), "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("com"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("node"));
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		//报文头结点增加核心的银行编码
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
		//格式：保险公司代码|银行代码|总记录数|总金额|
		//文件其他内容：（明细记录）
		//交易日期|试算申请顺序号|投保人姓名|投保人证件类型|投保人证件号码|险种编码|产品编码|投保单号|保费|个性化费率|账号|电话号码|手机号码|地址|邮政编码|附言|省市代码|网点号|
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			/*
			 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
			 *    
			 * 联调的时候和农行的人员确认的
			 * 
			 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|保单状态
			 */
			
			//交易日期
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			//试算申请序号
			Element tApplyNoEle = new Element("ApplyNo");
			tApplyNoEle.setText(tSubMsgs[1]);
			
			//投保人姓名
			Element tAppNameEle = new Element("AppntName");
			tAppNameEle.setText(tSubMsgs[2]);
			
			//投保人证件类型
			Element tIDTypeEle=new Element(IDType);
			tIDTypeEle.setText(tSubMsgs[3]);
			
			//证件号码
			Element tIDNoEle=new Element(IDNo);
			tIDNoEle.setText(tSubMsgs[4]);
			
			//险种代码-
			Element tRiskCodeEle=new Element(RiskCode);
			tRiskCodeEle.setText(tSubMsgs[5]);
			
			//产品代码
			Element tProdCodeEle =new Element("ProdCode");
			tProdCodeEle.setText(tSubMsgs[6]);
			
			//投保单号
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(NumberUtil.no13To15(tSubMsgs[7]));
			
			//保费
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[8]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			//费率
			Element tSpecialRateEle= new Element("SpecialRate");
			tSpecialRateEle.setText(tSubMsgs[9]);
			
			//账号
			Element tAccNoEle=new Element("AccNo");
			tAccNoEle.setText(tSubMsgs[10]);
			
			//电话号码
			Element tPhoneEle=new Element("Phone");
			tPhoneEle.setText(tSubMsgs[11]);
			
			//手机号码
			Element tMobileEle=new Element("Mobile");
			tMobileEle.setText(tSubMsgs[12]);
			
			//银行网点
			String nodeNo=null;
			if(tSubMsgs[16]!=null&&tSubMsgs[17]!=null){
				nodeNo=tSubMsgs[16].trim()+tSubMsgs[17].trim();
			}
			Element tNodeNo = new Element("NodeNo");
			tNodeNo.setText(nodeNo);
			
			//非实时出单银保通不知道保单号，因此在插入对账明细表的时候用投保单号插入保单号
 			Element tContNo=new Element(ContNo);
 			tContNo.setText(tSubMsgs[7]);
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tApplyNoEle);
			tDetailEle.addContent(tAppNameEle);
			tDetailEle.addContent(tIDTypeEle);
			tDetailEle.addContent(tIDNoEle);
			tDetailEle.addContent(tRiskCodeEle);
			tDetailEle.addContent(tProdCodeEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tSpecialRateEle);
			tDetailEle.addContent(tAccNoEle);
			tDetailEle.addContent(tPhoneEle);
			tDetailEle.addContent(tMobileEle);
			
			tDetailEle.addContent(tNodeNo);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tContNo);
			mBodyEle.addContent(tDetailEle);
		}
		mTranData.addContent(mBodyEle);
		mBufReader.close();	//关闭流

		cLogger.info("Out NonReaTimeIssWatDetail.parse()!");
		return new Document(mTranData);
	}

	/** 
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NonReaTimeIssWatDetail.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount) {
			throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList) {
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
			
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//有些银行传
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setPrem(tDetailEle.getChildText(Prem));
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//有些银行传
			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //有些银行传
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setBak1(tDetailEle.getChildText("ApplyNo"));
			
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem) {
			throw new MidplatException("汇总金额与明细总金额不符！"+ mSumPrem + "!=" + mSumDtlPrem);
		}
		
		/** 
		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
		 */
		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("保存对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存对账明细失败！");
		}
		
		cLogger.debug("Out NonReaTimeIssWatDetail.saveDetails()!");
		return mContBlcDtlSet;
	}
	
	// 备份月文件，比如yyyyMM01当日，系统会在目录localDir 建立/yyyy/yyyyMM，
	// 然后把所有文件移动到该目录，但是yyyyMM01的文件除外
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NonReaTimeIssWatDetail.bakFiles...");

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

		cLogger.info("Out NonReaTimeIssWatDetail.bakFiles!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonReaTimeIssWatDetail.main");
		mLogger.info("新农行非实时出单流水明细对账程序启动...");

		NonReaTimeIssWatDetail abcAES = new NonReaTimeIssWatDetail();

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
		System.out.println("新农行非实时出单流水明细对账程序完成!");
	}

}
