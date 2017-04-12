package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;

/**
 * @ClassName: NewAbcBusiBlc
 * @Description: 新农行新单对账
 * @author sinosoft
 * @date 2017-4-7 下午12:07:19
 */
public class NewAbcBusiBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private static String cCurTime="";
	@SuppressWarnings("unused")
	private String mFileData = "";
	private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;

	public void run() {
		cLogger.info("Into NewAbcBusiBlc.run()...");
		try {
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2001"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				cCurTime=new SimpleDateFormat("HHmmss").format(new Date());
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			// 初始化文件名称		POLICY 	+公司编号	+.		+当前时间
			String mFIleName = "POLICY" + mCorNo + "." + cCurDate; 
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2001", cCurDate)) {
				throw new MidplatException("农行新保承保保单对账文件下载异常");
			}
			// 处理对账
			cLogger.info("处理农行新单对账开始...");
			// 得到请求标准报文
			String myFilePath =cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/POLICY010079.20170405";
			System.out.println(myFilePath);
			Document mInStd = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			cLogger.info("处理农行新单对账结束!");

		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  处理异常..."
					+ e.getMessage());
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("Out NewAbcBusiBlc.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NewAbcBusiBlc.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2001");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
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
		this.cLogger.debug("Out NewAbcBusiBlc.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NewAbcBusiBlc.parse()...");
		String mCharset = "";
		// 组织根节点以及Head节点内容
		Element mTranData = new Element("TranData");
		//对账标志
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
		//新建交易日期节点
		Element mTranDate = new Element(TranDate);
		//交易日期节点设置文本为8位日期字符串
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		//获取当前日期字符串
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		// 对账日期为...8位交易日期字符串
		cLogger.info(" 对账日期为..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		// 当前日期为...当前日期字符串
		cLogger.info(" 当前日期为..." + mCurrDate);

		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		//8位交易日期字符串非当前日期字符串
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			//对账标志置为1[手工对账]
			tBalanceFlag = "1";
		}

		//新建交易时间节点
		Element mTranTime = new Element(TranTime);
		//交易时间节点设置文本为6位交易时间字符串
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		//新建交易机构代码节点
		Element mTranCom = new Element(TranCom);
		//交易机构代码节点设置文本为当前银行交易配置文件根节点下交易机构代码子节点文本
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		//新建省市代码节点
		Element mZoneNo = new Element("ZoneNo");
		//设置文本为当前交易配置节点下省市代码子节点文本
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		//新建银行网点节点
		Element mNodeNo = new Element(NodeNo);
		//设置文本为当前交易配置节点下银行网点子节点文本
		mNodeNo.setText(cConfigEle.getChildText("node"));

		//新建柜员代码节点
		Element mTellerNo = new Element(TellerNo);
		//设置文本为sys
		mTellerNo.setText("sys");
		
		//新建交易流水号节点
		Element mTranNo = new Element(TranNo);
		//设置文本为当前线程名
		mTranNo.setText(Thread.currentThread().getName());
		
		//新建交易类型节点
		Element mFuncFlag = new Element(FuncFlag);
		//设置文本为临时变量
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));

		//新建对账标志节点
		Element mBalanceFlag = new Element("BalanceFlag");
		//设置文本为对账标志
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
		
		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		//创建GBK字符集的 InputStreamReader,使用默认大小输入缓冲区的缓冲字符输入流
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
			//缓冲字符输入流读取一行根据匹配给定的正则表达式来拆分此字符串。
			String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
			// 把成功的记录独取出来发给核心
			Element mCountEle = new Element(Count);
			mCountEle.setText(mSubMsgs[3].trim());
			Element mSumPremEle = new Element(Prem);
			mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[4].trim())));
	
			Element mBodyEle = new Element(Body);
			mBodyEle.addContent(mCountEle);
			mBodyEle.addContent(mSumPremEle);
	
			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
				// cLogger.info("这是个么东西："+tLineMsg);
	
				// 空行，直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("空行，直接跳过，继续下一条！");
					continue;
				}
	
				String[] tSubMsgs = tLineMsg.split("\\|", -1);
	
				if (!"01".equals(tSubMsgs[6]))
				{
					cLogger.warn("非承保保单，直接跳过，继续下一条！");
					continue;
				}
				if (!("01".equals(tSubMsgs[7])))
				{
					cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
					continue;
				}
	
				/*
				 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
				 * 
				 * 联调的时候和农行的人员确认的，20130403
				 * 
				 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|保单状态
				 */
				String nodeNo = null;
				if (tSubMsgs[2] != null && tSubMsgs[3] != null)
				{
					nodeNo = tSubMsgs[2].trim() + tSubMsgs[3].trim();
				}
	
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[0]);
	
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[1]);
	
				//保险单号
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[4]);
				
				//保费(分)
				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[5]);
				tPremEle.setText(String.valueOf(tPremFen));
	
				//代理机构
				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(nodeNo);
	
				//投保单(印刷)号[非必须]
				Element tProposalPrtNoEle = new Element(ProposalPrtNo);
				
				//投保人姓名[非必须，有些银行传]
				Element tAppntNameEle=new Element("AppntName");
				
				//被保人姓名[非必须]
				Element tInsuredNameEle=new Element("InsuredName");
				
				/*
				 * Element tContTypeEle = new Element("ContType"); if
				 * (!(tSubMsgs[8].trim()).endsWith("88")) {
				 * tContTypeEle.setText(String
				 * .valueOf(HxlifeCodeDef.ContType_Group)); } else {
				 * tContTypeEle.setText
				 * (String.valueOf(HxlifeCodeDef.ContType_Bank)); }
				 */
	
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tPremEle);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tProposalPrtNoEle);
				tDetailEle.addContent(tAppntNameEle);
				tDetailEle.addContent(tInsuredNameEle);
	
				mBodyEle.addContent(tDetailEle);
			}
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // 关闭流		
			
			cLogger.info("Out NewAbcBusiBlc.parse()!");
			return new Document(mTranData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("解析对账文件失败！" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NewAbcBusiBlc.main");
		mLogger.info("新农行新单对账程序启动...");

		NewAbcBusiBlc abcAES = new NewAbcBusiBlc();

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
		System.out.println("新农行新单对账程序完成!");
	}

}
