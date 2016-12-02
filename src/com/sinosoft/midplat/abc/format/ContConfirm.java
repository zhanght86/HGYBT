package com.sinosoft.midplat.abc.format;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;


public class ContConfirm extends XmlSimpFormat {
	
	String tXslName;

	public ContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		
		Document mStdXml = 
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
	

		
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.Std2StdnoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		if (ttFlag.getValue().equals("0")){
			pStdXml =  dealBnf(pStdXml);	
			pStdXml =  dealCashValues(pStdXml);	
			
			//根据险种代码选择保单打印格式模板 20130228
			Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
			cLogger.info("tRiskCode:"+tRiskCode.getValue());
			if("211901".equals(tRiskCode.getValue()) || "211902".equals(tRiskCode.getValue())){
				tXslName="ContConfirmOutLendRisk";
			}
		}
		
		

		Document mNoStdXml = 
		  ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		  Element mPrnts= mNoStdXml.getRootElement().getChild("Prnts");
		  if(mPrnts!=null){
			  List mPrntList=mPrnts.getChildren("Prnt");
			  Element mCount=mPrnts.getChild("Count");
			  mCount.setText(String.valueOf(mPrntList.size()));
		      
		  }
		  Element mMessages = mNoStdXml.getRootElement().getChild("Messages");
		  if(mMessages!=null){
			 
			  List mMessageList = mMessages.getChildren("Message");
			  Element mCountMess = mMessages.getChild("Count");
			  mCountMess.setText(String.valueOf(mMessageList.size()));
		  }
		  
		
		
		cLogger.info("Out ContConfirm.Std2StdnoStd()!");
		
		return mNoStdXml;
	}
	private Document dealBnf(Document InStdDoc){
		List bnfList = InStdDoc.getRootElement().getChild(Body).getChildren(Bnf);
		for(int i = 0;i<bnfList.size();i++){
			Element bnfEle = (Element) bnfList.get(i);
			Element SeqNoELe = new Element("SeqNo");
			SeqNoELe.setText(String.valueOf(i+1));
			bnfEle.addContent(SeqNoELe);
			
			Element SendRiskIDMsgEle = new Element("LendRiskIDMsg");
			SendRiskIDMsgEle.setText(bnfEle.getChildText(Name)+"（证件号码："+bnfEle.getChildText(IDNo)+"）");
			bnfEle.addContent(SendRiskIDMsgEle);
			
		}
		//Risk 节点下新增节点LendRiskDay存放借贷险保险期间（转化为天）
		Element RiskEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk);
		String startDay = RiskEle.getChildText(CValiDate);
		String endDay = RiskEle.getChildText(InsuEndDate);
		
		Element LendRiskDayEle = new Element("LendRiskDay");
		LendRiskDayEle.setText(String.valueOf(DateUtil.compareDay(startDay, endDay)+1));
		RiskEle.addContent(LendRiskDayEle);
		
		return InStdDoc;
	}
	private Document dealCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<10){
			for(int i=0 ;i<10-cashValuesListSize;i++){
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize+i));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");
				
				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);
				
				CashValuesEle.addContent(cashValueELe);
			}
		}
		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

}
