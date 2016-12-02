/**
 * 上传银行文件
 */
package com.sinosoft.midplat.newabc.bat;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.Service;
public abstract class ToAbcBalance extends TimerTask implements XmlTag{
	// 成员变量
	private final XmlConf cThisConf;
	private final String cFuncFlag;
	protected Date cTranDate;
	protected String cCurDate = null;
	 protected String cResultMsg;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	 protected Element cMidplatRoot = null;
	protected final Logger cLogger = Logger.getLogger(getClass());	
	public ToAbcBalance(XmlConf pThisConf, String pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	public void run(){
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));				
		cLogger.info("Into ToAbcBalance.run ...");
		 this.cResultMsg = null;
		 
		  try{
			 this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			 this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
		     this.cThisBusiConf = ((Element)XPath.selectSingleNode(
		     this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
		   String nextDate = this.cThisBusiConf.getChildText("nextDate");
         if (this.cCurDate == null) {
             this.cCurDate = DateUtil.getCur10Date();
           }
         if (this.cTranDate == null)
           if ((nextDate != null) && ("Y".equals(nextDate))) {
             this.cTranDate = new Date();
             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
      } else {
    	  cTranDate = new Date();
      }
		cThisConfRoot = cThisConf.getConf().getRootElement();
		cLogger.info("cThisConfRoot=="+cThisConfRoot.getName());
		//校验是否有重复操作
		checkTranLog();
		
		
		
		
		 Element tTranData = new Element(TranData);
         Document tInStdXml = new Document(tTranData);
         //报文头  
         Element tBaseInfo = getHead();
         tTranData.addContent(tBaseInfo);
		 //报文体
         Element ttBodyEle = new Element(Body);
         Element tTranDateEle = new Element(TranDate);
         tTranDateEle.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd")); 
         ttBodyEle.addContent(tTranDateEle);
         tTranData.addContent(ttBodyEle);
		
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
         Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
         Document tOutStdXml = tService.service(tInStdXml);
         this.cResultMsg = tOutStdXml.getRootElement().getChild( Head).getChildText(Desc);
         ending(tOutStdXml);    
		  } catch (MidplatException e) {
	             this.cLogger.error("交易出错", e);
	             this.cResultMsg = e.getMessage();
	        }catch (Throwable ex) {
             this.cLogger.error("交易出错", ex);
             this.cResultMsg = ex.toString();
             }
      this.cTranDate = null;
      cLogger.info("out  ToAbcBalance.run ...");
	}
     
	


	 public abstract void checkTranLog() throws MidplatException;

	protected Element getHead() {
			cLogger.info("Into ToBankBalance.getHead()...");
			String tBalanceFlag = "0";
			Element mTranDate = new Element(TranDate);
			mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
			cLogger.info(" 对账日期为..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
			cLogger.info(" 当前日期为..."+mCurrDate);
			
			// 若手工对账，则tBalanceFlag标志置为1 ，日终对账置为0 modify by liuq 2010-11-11
			if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
				tBalanceFlag = "1";
			}
			Element mTranTime = new Element(TranTime);
			mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
			
			Element mTranCom = new Element(TranCom);
			mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
			String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
			if (null!=tTempStr && !"".equals(tTempStr)) {
				mTranCom.setAttribute(outcode, tTempStr);
			}
			
			Element mZoneNo = new Element("ZoneNo");
			mZoneNo.setText(cThisBusiConf.getChildText("ZoneNo"));
			
			Element mNodeNo = new Element(NodeNo);
			mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));
			
			Element mTellerNo = new Element(TellerNo);
			mTellerNo.setText("sys");
			
			Element mTranNo = new Element(TranNo);
			mTranNo.setText(Thread.currentThread().getName());
			
			Element mFuncFlag = new Element(FuncFlag);

			tTempStr = cThisBusiConf.getChildText(funcFlag);
			mFuncFlag.setText(tTempStr);
			
			Element mBalanceFlag = new Element("BalanceFlag");
			mBalanceFlag.setText(tBalanceFlag);
			
			Element mAgentCom = new Element(AgentCom);
			Element mAgentComName = new Element(AgentComName);
			Element mAgentName = new Element(AgentName);
			Element mAgentCode = new Element(AgentCode);
			Element mHead = new Element(Head);
			mHead.addContent(mTranDate);
			mHead.addContent(mTranTime);
			mHead.addContent(mTranCom);
			mHead.addContent(mZoneNo);
			mHead.addContent(mNodeNo);
			mHead.addContent(mAgentCom);
			mHead.addContent(mAgentComName);
			mHead.addContent(mAgentName);
			mHead.addContent(mAgentCode);
			mHead.addContent(mTellerNo);
			mHead.addContent(mTranNo);
			mHead.addContent(mFuncFlag);
			mHead.addContent(mBalanceFlag);
			cLogger.info("Out ToBankBalance.getHead()!");
			return mHead;
		}
   
	public void setDate(Date pDate) {
		this.cTranDate = pDate;
	}
	
	public void setDate(String p8DateStr) {
	       this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	     }

	protected void ending(Document pOutStdXml) throws Exception {
		this.cLogger.info("Into Balance.ending()...");

		this.cLogger.info("do nothing, just out!");

		this.cLogger.info("Out Balance.ending()!");
	}

	public String getResultMsg() {
		return this.cResultMsg;
	}
		 
}
