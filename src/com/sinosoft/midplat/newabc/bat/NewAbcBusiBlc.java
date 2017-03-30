/**
 * 农行新契约对账
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.service.Service;

/**
 * @ClassName: NewAbcBusiBlc
 * @Description: 新农行新单对账
 * @author sinosoft
 * @date 2017-2-27 上午10:39:03
 */
public class NewAbcBusiBlc extends Balance
{

	//生成一个本类的日志对象
	protected final Logger cLogger = Logger.getLogger(getClass());

	/**
	 * <p>Title: 新农行新单对账批量无参构造</p>
	 * <p>Description: </p>
	 */
	public NewAbcBusiBlc()
	{
		super(NewAbcConf.newInstance(), "2001");
	}

	/**
	 * 获取上传下载文件名
	 * @return 
	 */
	protected String getFileName() throws Exception
	{
		//获取当前银行交易配置文件根节点下银行子节点
		Element mBankEle = cThisConfRoot.getChild("bank");
		//新建文件下载批量实例[当前交易配置节点,交易码,8位交易日期字符串,银行节点insu属性值]
		File_download f = new File_download(cThisBusiConf, "RZDZ", DateUtil.getDateStr(cTranDate, "yyyyMMdd"), mBankEle.getAttributeValue("insu"));
		//获取文件名[POLICY保险公司代码.8位交易日期字符串]
		String fileName = "POLICY" + mBankEle.getAttributeValue("insu") + "." + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		try
		{
			//上传下载银行文件
			f.bank_dz_file();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new MidplatException(ex.getMessage());
		}
		//返回文件名[POLICY保险公司代码.8位交易日期字符串]
		return fileName;
	}

	/**
	 * 运行
	 */
	public void run()
	{
		//设置当前线程名称为下一个交易日志号 
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		//Into NewAbcBusiBlc.run()...
		this.cLogger.info("Into NewAbcBusiBlc.run()...");
		
		//返回信息置为空
		this.cResultMsg = null;
		try
		{
			//获取中间平台配置文件根节点
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			//获取当前银行交易配置文件根节点
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			//获取当前交易配置节点[选择当前银行交易配置文件根节点下business子节点funcFlag属性为 交易码的单个节点]
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
			//获取下一日期[当前交易配置节点下nextDate子节点文本]
			String nextDate = this.cThisBusiConf.getChildText("nextDate");
			
			//交易日期为空
			if (this.cTranDate == null)
				//下一日期非空且为Y
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					//交易日期赋值为当前日期对象
					this.cTranDate = new Date();
					//交易日期赋值为昨天
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					//交易日期赋值为当前日期对象
					this.cTranDate = new Date();
				}
			//新建根节点
			Element tTranData = new Element("TranData");
			//新建标准输入报文
			Document tInStdXml = new Document(tTranData);
			
			//获取标准输入报文头
			Element tHeadEle = getHead();
			//根节点加入标准输入报文头
			tTranData.addContent(tHeadEle);
			try
			{
				//获取上传下载文件名
				String ttFileName = getFileName();
				//FileName = 上传下载文件名
				this.cLogger.info("FileName = " + ttFileName);
				//本地目录字符串
				String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
				//localDir = 本地目录字符串
				this.cLogger.info("localDir = " + ttLocalDir);
				//输入字节流
				InputStream ttBatIns = null;
				//本地目录上传下载文件输入流
				ttBatIns = new FileInputStream(ttLocalDir + ttFileName);
				
				//
				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			}
			catch (Exception ex)
			{
				this.cLogger.error("生成标准对账报文出错!", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("业务处理模块" + tServiceClassName);
			@SuppressWarnings("rawtypes")
			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head").getChildText("Desc");

		}
		catch (Throwable ex)
		{
			this.cLogger.error("交易出错", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out NewAbcBusiBlc.run()!");
	}

	/**
	 * 转换文件输入流为报文体
	 * @return pBatIs 本地目录上传下载文件输入流
	 * @return 标准输入报文体
	 */
	protected Element parse(InputStream pBatIs) throws Exception
	{
		//进入NewAbcBusiBlc转换文件输入流为报文体方法...
		cLogger.info("Into NewAbcBusiBlc.parse()...");
		
		//字符集[当前交易配置节点下字符集子节点文本]
		String mCharset = cThisBusiConf.getChildText(charset);
		//字符集非空、空字符串
		if (null == mCharset || "".equals(mCharset))
		{
			//置为GBK
			mCharset = "GBK";
		}
		// 格式：保险公司代码|总记录数|总金额|成功总记录数|成功总金额
		// 文件其他内容：（明细记录）
		// 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|保单状态
		//输出本地目录上传下载文件输入流
		System.out.println(pBatIs);
		//创建GBK字符集的 InputStreamReader,使用默认大小输入缓冲区的缓冲字符输入流
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
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
		mBufReader.close(); // 关闭流

		cLogger.info("Out NewAbcBusiBlc.parse()!");
		return mBodyEle;
	}

	/**
	 * 获取标准输入报文头
	 */
	protected Element getHead()
	{
		//进入NewAbcBusiBlc获取报文头方法...
		cLogger.info("Into NewAbcBusiBlc.getHead()...");
		//对账标志
		String tBalanceFlag = "0";
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
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		//临时字符串为交易机构代码子节点outcode属性值
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		//临时字符串非空、空字符串
		if (null != tTempStr && !"".equals(tTempStr))
		{
			//交易机构代码节点设置属性outcode为其赋值为临时字符串
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		//新建省市代码节点
		Element mZoneNo = new Element("ZoneNo");
		//设置文本为当前交易配置节点下省市代码子节点文本
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));

		//新建银行网点节点
		Element mNodeNo = new Element(NodeNo);
		//设置文本为当前交易配置节点下银行网点子节点文本
		mNodeNo.setText(cThisBusiConf.getChildText("node"));

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
		//临时变量为当前交易配置节点下交易类型子节点outcode属性值
		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		//设置文本为临时变量
		mFuncFlag.setText(tTempStr);

		//新建对账标志节点
		Element mBalanceFlag = new Element("BalanceFlag");
		//设置文本为对账标志
		mBalanceFlag.setText(tBalanceFlag);
		
		// 报文头结点增加核心的银行编码
		//新建银行代码节点
		Element mBankCode = new Element("BankCode");
		//设置文本为0102
		mBankCode.setText("0102");
		
		//新建报文头节点
		Element mHead = new Element(Head);
		/**
		 * 加入交易日期、交易时间、银行代码、交易机构代码、省市代码、
		 * 银行网点、柜员代码、交易流水号、交易类型、对账标志节点
		 **/
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);

		// 报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);

		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);
		
		//从NewAbcBusiBlc获取报文头方法出来!
		cLogger.info("Out NewAbcBusiBlc.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.NewAbcBusiBlc.main");
		mLogger.info("程序开始...");

		NewAbcBusiBlc mBatch = new NewAbcBusiBlc();
		// 用于补对账，设置补对账日期
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
				mBatch.setDate(args[0]);
			}
			else
			{
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
