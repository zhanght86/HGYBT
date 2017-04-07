package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class NewAbcCardBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger
			.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	 private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;
	public void run() {
		cLogger.info("Into NewAbcCardBlc.run()...");
		try {
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2000"); // 得到bat.xml文件中的对应节点.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date()).replace("-","");
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			
			String mFIleName = "POLICY"+mCorNo+"." + cCurDate; // 初始化文件名称POLICY3002
															// //3002公司编号 + 当前时间
			// 组织Document
			if(!new BatUtils().downLoadFile(mFIleName, "02","2000",cCurDate)){
				throw new MidplatException("新农行单证对账文件下载异常");
			}
			// 处理对账
			cLogger.info("处理新农行单证对账开始...");
			// 得到请求标准报文
			//
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "C:\\Users\\chenjinwei\\Desktop\\POLICY1132.20161130";
			System.out.println(myFilePath);
			Document mInStd = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(mInStd);
			String reCode =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
		    cTranLogDB.setRCode(reCode);
        	cTranLogDB.setRText(reMsg);
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
			cLogger.info("处理新农行单证对账结束!");
		} catch (Exception e) {
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
		// 组织根节点以及BaseInfo节点内容
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..." + mCurrDate);

		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			tBalanceFlag = "1";
		}

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
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
		mBankCode.setText("0102");

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
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
			/*
			 * 文件第一行：（汇总信息） 格式：保险公司代码|总记录数|总金额|成功总记录数|成功总金额 文件其他内容：（明细记录）
			 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|交易状态
			 */
			String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
			// 把成功的记录独取出来发给核心
			Element mCountEle = new Element(Count);
			mCountEle.setText(mSubMsgs[3].trim());
			Element mBodyEle = new Element(Body);
			mBodyEle.addContent(mCountEle);
	
			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
	
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
				 */
				
				//单证关联号(保单号、保全号等)
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[4]);
				
				// 获取保单印刷号
				String tContPrtNoSql = "select otherno from tranlog where funcflag='1004' and contno='" + tContNoEle.getText() + "' and trancom='05' and rcode='0' ";
				this.cLogger.info("保单印刷号sql:" + tContPrtNoSql);
				String tContPrtNo = new ExeSQL().getOneValue(tContPrtNoSql);
				//如果查询单证印刷号为空，则抛出异常
				if(StringUtils.isEmpty(tContPrtNo))
				{
					throw new MidplatException("单证对账失败：保单" + tContNoEle.getText() + "的单证号查询失败");
				}
				
				//单证类型
				Element tCardTypeEle = new Element("CardType");
				if (!"".equals(tContPrtNo) && tContPrtNo != null)
				{
					tCardTypeEle.setText(tContPrtNo.substring(0, 5));
				}
				
				//单证号 保单印刷号
				Element tCardNoEle = new Element(CardNo);
				tCardNoEle.setText(tContPrtNo);
							
				//单证状态
				Element tCardStateEle = new Element("CardState");
				tCardStateEle.setText("6");
				
				//机构[非必须，有些银行传] 
				String nodeNo = null;
				if (tSubMsgs[2] != null && tSubMsgs[3] != null)
				{
					nodeNo = tSubMsgs[2].trim() + tSubMsgs[3].trim();
				}
				Element tAgentCom = new Element(AgentCom);
				tAgentCom.setText(nodeNo);
				
				//柜员代码[非必须，有些银行传]
				Element mTellerNoEle = new Element(TellerNo);
				mTellerNoEle.setText(tSubMsgs[3]);
				
				//交易流水号[非必须，有些银行传]
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[1]);
				
	
				/*// 获取交易日期
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[0]);*/
	
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
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // 关闭流
		}catch(Exception e){
			e.printStackTrace();
			throw new MidplatException("解析对账文件失败！"+e.getMessage());
		}
		cLogger.info("Out NewAbcCardBlc.parse()!");
		return new Document(mTranData); 
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
