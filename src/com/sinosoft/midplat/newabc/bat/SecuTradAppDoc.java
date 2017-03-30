
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
 *保全交易申请文件
 * @author liuzk
 *
 */
public class SecuTradAppDoc extends Balance{
	/**
	 * <p>Title: 保全交易申请文件批量无参构造</p>
	 * <p>Description: </p>
	 */
	public SecuTradAppDoc() {
		super(NewAbcConf.newInstance(), "2003");
	}
	protected String getFileName()throws Exception {
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf,"BQSQ",DateUtil.getDateStr(cTranDate,"yyyyMMdd"),mBankEle.getAttributeValue("insu"));
		String fileName="BQAPPLY"+mBankEle.getAttributeValue("insu")+"."+DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		try {
			f.bank_dz_file();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MidplatException(ex.getMessage());
		} 
		return fileName;
	}
	
	 public void run()
	   {
	        Thread.currentThread().setName(
	          String.valueOf(NoFactory.nextTranLogNo()));
	        this.cLogger.info("Into SecuTradAppDoc.run()...");
	 
	        this.cResultMsg = null;
	     try
	     {
	         this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
	         this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
	         this.cThisBusiConf = ((Element)XPath.selectSingleNode(
	           this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
	 
	         String nextDate = this.cThisBusiConf.getChildText("nextDate");
	 
	         if (this.cTranDate == null)
	           if ((nextDate != null) && ("Y".equals(nextDate))) {
	             this.cTranDate = new Date();
	             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
	      } else {
	             this.cTranDate = new Date();
	      }
	         Element tTranData = new Element("TranData");
	         Document tInStdXml = new Document(tTranData);
	 
	         Element tHeadEle = getHead();
	         tTranData.addContent(tHeadEle);
	    try
	    {
	           String ttFileName = getFileName();
	           this.cLogger.info("FileName = " + ttFileName);
	           String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
	           this.cLogger.info("localDir = "+ttLocalDir);
	           InputStream ttBatIns = null;

	           ttBatIns = new FileInputStream(ttLocalDir+ttFileName);
	           
	           Element ttBodyEle = parse(ttBatIns);
	           tTranData.addContent(ttBodyEle);
	    } catch (Exception ex) {
	           this.cLogger.error("生成标准对账报文出错!", ex);
	 
	           Element ttError = new Element("Error");
	           String ttErrorStr = ex.getMessage();
	           if ("".equals(ttErrorStr)) {
	             ttErrorStr = ex.toString();
	      }
	           ttError.setText(ttErrorStr);
	           tTranData.addContent(ttError);
	    }
	 
	         String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
	 
	         String tServiceValue = this.cMidplatRoot.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	 
	         tServiceValue = this.cThisConfRoot.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	         tServiceValue = this.cThisBusiConf.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	         this.cLogger.info("业务处理模块" + tServiceClassName);
	         Constructor tServiceConstructor = Class.forName(
	           tServiceClassName).getConstructor(new Class[] { Element.class });
	         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
	         Document tOutStdXml = tService.service(tInStdXml);
	 
	         this.cResultMsg = tOutStdXml.getRootElement().getChild(
	           "Head").getChildText("Desc");
	 
	  }
	  catch (Throwable ex) {
	         this.cLogger.error("交易出错", ex);
	         this.cResultMsg = ex.toString();
	  }
	 
	       this.cTranDate = null;
	 
	       this.cLogger.info("Out SecuTradAppDoc.run()!");
	   }
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into SecuTradAppDoc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		// 格式：保险公司代码|银行代码|总记录数|总金额|犹撤总记录数|犹撤总金额|满期总记录数|满期总金额|退保总记录数|退保总金额|
		//文件其他内容：（明细记录）
		// 公司编码|业务类别|交易日期|保单号|证件类型|证件号码|申请人名称|保费|申请状态|省市代码|网点号|投保单号|领取金额|
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
			
//			if (!"01".equals(tSubMsgs[10])) {
//				cLogger.warn("非承保保单，直接跳过，继续下一条！");
//				continue;
//			}
//			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
//				cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
//				continue;
//			}
			
			/*
			 * 农行的实时的地区码是4位的（银行省市代码+2位地区码），对账的地区码是2位的，所以对账的地区码还要拼接省市银行代码部分
			 *    
			 * 联调的时候和农行的人员确认的，20130403
			 * 
			 * 交易日期|银行交易流水号|银行省市代码|网点代码|保单号|交易金额|交易类型|保单状态
			 */
			
			//业务类别
			Element tBusiType = new Element("BusiType");
			if("01".equals(tSubMsgs[1])){//犹撤
				tBusiType.setText("07");
			}
			if("02".equals(tSubMsgs[1])){//满期
				tBusiType.setText("09");
			}
			if("03".equals(tSubMsgs[1])){//退保
				tBusiType.setText("11");
			}
			
			//交易日期
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[2]);
			
			//保单号
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[3]);
			
			//申请人姓名
			Element tAppntNameEle=new Element("AppntName");
			
			//投保人证件类型
			Element tIDTypeEle=new Element(IDType);
			
			//证件号码
			Element tIDNoEle=new Element(IDNo);
			
			//投保单号
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[11]);
			
			//金额
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[12]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			//省代码
			Element tZoneNo=new Element("ZoneNo");
			tZoneNo.setText(tSubMsgs[9].trim());
			
			//网点代码
			String nodeNo=null;
			if(tSubMsgs[9]!=null&&tSubMsgs[10]!=null){
				nodeNo=tSubMsgs[9].trim()+tSubMsgs[10].trim();
			}
			Element tNodeNo=new Element("NodeNo");
			tNodeNo.setText(nodeNo);
			
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tBusiType);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tAppntNameEle);
			tDetailEle.addContent(tIDTypeEle);
			tDetailEle.addContent(tIDNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tZoneNo);
			tDetailEle.addContent(tNodeNo);
			
			tDetailEle.addContent(tAgentCom);
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//关闭流
		
		cLogger.info("Out SecuTradAppDoc.parse()!");
		return mBodyEle;
	}
	
	protected Element getHead() {
		cLogger.info("Into SecuTradAppDoc.getHead()...");
		String tBalanceFlag = "0";
		
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" 对账日期为..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" 当前日期为..."+mCurrDate);
		
		// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		//交易日期
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//交易时间
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		//交易机构代码(银行/农信社/经代公司)
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		if (null!=tTempStr && !"".equals(tTempStr)) {
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		//银行代码
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		//地区代码
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));
		
		//银行网点
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText("node"));
		
		//柜员代码
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		//交易流水号
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		//交易类型
		Element mFuncFlag = new Element(FuncFlag);
		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		mFuncFlag.setText(tTempStr);
		
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mBankCode);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		
		mHead.addContent(mBalanceFlag);

		cLogger.info("Out SecuTradAppDoc.getHead()!");
		return mHead;
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.SecuTradAppDoc.main");
		mLogger.info("程序开始...");
		
		SecuTradAppDoc mBatch = new SecuTradAppDoc();
		//用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。
			 * 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}\\d{2}\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		
		mBatch.run();
		
		mLogger.info("成功结束！");
	}
}
