package com.sinosoft.midplat.jhyh.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.jhyh.bat.Balance;
import com.sinosoft.midplat.bat.GetSFTPConnection;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.jhyh.JhyhConf;
import com.sinosoft.midplat.service.Service;

import com.sinosoft.utility.ExeSQL;

public class JhyhBusiBlc extends Balance{

	public JhyhBusiBlc() {
		super(JhyhConf.newInstance(), "5005");
	}

	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
//		Element mBankEle = cThisConfRoot.getChild("bank");
		return  "ZHRS" +DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}

	   public void run()
	   {
	        Thread.currentThread().setName(
	          String.valueOf(NoFactory.nextTranLogNo()));
	        this.cLogger.info("Into JhyhBusiBlc.run()...");
	 
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
//			   Element mBankEle = cThisConfRoot.getChild("bank");
//	           String ttFileName = "POLICY"+mBankEle.getAttributeValue("insu")+"."+DateUtil.getDateStr(cTranDate, "yyyyMMdd");
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
	 
	       this.cLogger.info("Out JhyhBusiBlc.run()!");
	   }
	
	protected Element parse(InputStream pBatIs) throws Exception {
		
		cLogger.info("Into jhyhBusiBlc.parse()...");
		
		int sumCount=0;
		long sumPrem=0;
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		// 格式：保险公司代码|总记录数|总金额|成功总记录数|成功总金额
		//文件其他内容：（明细记录）
		// 交易日期|地区代码|网点代码|银行流水号|保单号|交易金额(带小数点)|销售渠道|
		
		System.out.println(pBatIs); 
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
//		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		//把成功的记录独取出来发给核心
		Element mCountEle = new Element(Count);
//		mCountEle.setText(mSubMsgs[5].trim());
		Element mSumPremEle = new Element(Prem);
//		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[4].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
//			cLogger.info("这是个么东西："+tLineMsg);
			//总比数加一
			sumCount++;
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
//			if (!"01".equals(tSubMsgs[6])) {
//				cLogger.warn("非承保保单，直接跳过，继续下一条！");
//				continue;
//			}
//			if (!("01".equals(tSubMsgs[7]))) {
//				cLogger.warn("承保保单（冲正或撤单的单子），直接跳过，继续下一条！");
//				continue;
//			}
			
//			String nodeNo=null;
//			if(tSubMsgs[1]!=null&&tSubMsgs[2]!=null){
//				nodeNo=tSubMsgs[1].trim()+tSubMsgs[2].trim();
//			}
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[3]);
			
//			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
//			tProposalPrtNoEle.setText(tSubMsgs[7]);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[4]);
			
			Element tAgentCom=new Element(AgentCom);
//			tAgentCom.setText(nodeNo);
			tAgentCom.setText(tSubMsgs[2]);
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[5]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			sumPrem=sumPrem+tPremFen;
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
//			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
//			tDetailEle.addContent(tTranNoEle);
//			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			
			mBodyEle.addContent(tDetailEle);
		}
		
		mCountEle.setText(String.valueOf(sumCount));
		mSumPremEle.setText(String.valueOf(sumPrem));
		mBufReader.close();	//关闭流n(mSumPrem)+"、对账总数：" + String.valueOf(mCount));
	
	cLogger.info("Out jhyhBusiBlc.parse()!");
	return mBodyEle;
}


protected Element getHead() {
	cLogger.info("Into jhyhBusiBlc.getHead()...");
	
	Element mTranDate = new Element(TranDate);
	mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
	
	Element mTranTime = new Element(TranTime);
	mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
	
	Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
	
	Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
	
	Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
	
	Element mTellerNo = new Element(TellerNo);
	mTellerNo.setText(CodeDef.SYS);
	
	Element mTranNo = new Element(TranNo);
	mTranNo.setText(getFileName());
	
	Element mFuncFlag = new Element(FuncFlag);
	mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
	
	Element mTranLogNo = new Element("TranLogNo");
	mTranLogNo.setText(Thread.currentThread().getName());
	
	//报文头结点增加核心的银行编码
	Element mBankCode = new Element("BankCode");
	mBankCode.setText("0122");
	
	Element mHead = new Element(Head);
	mHead.addContent(mTranDate);
	mHead.addContent(mTranTime);
	mHead.addContent(mTranCom);
	mHead.addContent(mZoneNo);
	mHead.addContent(mNodeNo);
	mHead.addContent(mTellerNo);
	mHead.addContent(mTranNo);
	mHead.addContent(mFuncFlag);
	mHead.addContent(mTranLogNo);
	//报文头结点增加核心的银行编码
	mHead.addContent(mBankCode);
	
	cLogger.info("Out jhyhBusiBlc.getHead()!");
	return mHead;
}
public static void main(String[] args) throws Exception {
	Logger mLogger = Logger.getLogger("com.sinosoft.midplat.jhyh.bat.jhyhBusiBlc.main");
	mLogger.info("程序开始...");
	
	JhyhBusiBlc mBatch = new JhyhBusiBlc();
	mBatch.run();
//	File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
//	InputStream in  = new FileInputStream(file);
//	Element mBodyEle = mBatch.parse(in);
//	JdomUtil.print(mBodyEle);
	mLogger.info("成功结束！");
}

}
