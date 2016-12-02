package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.LMSpecContentDB;
import com.sinosoft.midplat.icbc.format.NewContInXsl;
import com.sinosoft.midplat.icbc.format.NewContOutXsl;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.icbc.IcbcConf;

public class NewCont2 extends XmlSimpFormat {
	
	String tXslName;
	
	public NewCont2(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");


		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		if (ttFlag.getValue().equals("0")){
			pStdXml =  dealBnf(pStdXml);	
			
			
			//根据险种代码选择年金产品保单打印格式模板 20130408
			Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
			cLogger.info("tRiskCode:"+tRiskCode.getValue());
			if("231301".equals(tRiskCode.getValue())||"231302".equals(tRiskCode.getValue())){
				tXslName="NewContPensionRiskOut";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else if("211901".equals(tRiskCode.getValue()) || "211902".equals(tRiskCode.getValue())){
				tXslName="NewContOutLendRisk";
				pStdXml =  dealCashValues(pStdXml);
			}else if("221301".equals(tRiskCode.getValue())){
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				//处理现价的方法体目前相同，这样处理的方式是为了防止出现修改调整一个模板对另一个模板造成影响的问题。
				pStdXml =  dealCashValues2(pStdXml);
			}else{
				pStdXml =  dealCashValues(pStdXml);
			}
			
		}
		
		
		
		
		Document mNoStdXml =  
			NewContOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
		if (ttFlag.getValue().equals("0")){
			
			//add by liuq 增加工行动态打印的行号等参数 20101029
			List<Element> tSubVoucherList = XPath.selectNodes(mNoStdXml.getRootElement(), "/TXLife/TXLifeResponse/Print/SubVoucher");
			Element tPrintEle  = (Element) XPath.selectSingleNode(mNoStdXml.getRootElement(), "/TXLife/TXLifeResponse/Print");
//			增加页数 
			Element tVoucherNum = new Element("VoucherNum");
			tVoucherNum.setText(String.valueOf(tSubVoucherList.size()));
			tPrintEle.addContent(tVoucherNum);
//			增加总行数及行号		
			for(Element tSubVoucherEle : tSubVoucherList){ 
//				List<Element> tTextRowContentList  =  XPath.selectNodes(tSubVoucherEle, "/Text/TextRowContent");
				List<Element> tTextRowContentList  =  tSubVoucherEle.getChild("Text").getChildren("TextRowContent");
//				Element tTextEle  = (Element) XPath.selectSingleNode(tSubVoucherEle, "/Text");
				Element tTextEle  =  tSubVoucherEle.getChild("Text");
		//		 JdomUtil.print(tTextEle);
				  
				for(int i = 0; i < tTextRowContentList.size(); i++){
					Element tTextRowContentEle = tTextRowContentList.get(i);
				    Element tTextContent = new Element("TextContent");
				    tTextEle.addContent(tTextContent);
				    Element mNewTextRowContentEle =(Element) tTextRowContentEle.clone();
				    //增加行号   
				    Element mRowNumEle = new Element("RowNum");
				    mRowNumEle.setText(String.valueOf(i+1));
				    tTextContent.addContent(mRowNumEle);
				    tTextContent.addContent(mNewTextRowContentEle);  
	 
				} 
//				 JdomUtil.print(mNoStdXml);
			//增加总行数
			  Element mRowTotalEle = new Element("RowTotal");
			  mRowTotalEle.setText(tTextRowContentList.size()+""); 
			  tTextEle.addContent(mRowTotalEle);
				//删除不正确的结构
				 tTextEle.removeChildren("TextRowContent");
			}
			}
		
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	/**
	 * 处理受益人打印处的第一行显示"受益人"三个字，多个受益人时后面的受益人信息前不显示这三个字
	 * @param InStdDoc
	 * @return
	 */
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
	
	/**
	 * 根据传入的报文检查现金价值的个数是否少于显示的行数（10），没有10行，则补充至10行，这是为了控制打印时的显示的完整性
	 * @param InStdDoc
	 * @return
	 */
	private Document dealCashValues (Document InStdDoc){
//		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
//		List cashValuesList = CashValuesEle.getChildren(CashValue);
//		int cashValuesListSize = cashValuesList.size();
//		cLogger.info("dealPensionCashValues.cashValuesListSize："+cashValuesListSize);
//		
//		Element cashValueELe = new Element(CashValue);
//		Element EndYearEle = new Element("EndYear");
//		EndYearEle.setText(String.valueOf(cashValuesListSize+1));
//		Element CashEle = new Element("Cash");
//		CashEle.setText("（以下空白）");
//		
//		cashValueELe.addContent(EndYearEle);
//		cashValueELe.addContent(CashEle);
//		CashValuesEle.addContent(cashValueELe);
//		
//		cashValuesListSize = cashValuesListSize+1;
//		
//		if(cashValuesListSize<35){
//			for(int i=0 ;i<35-cashValuesListSize;i++){
//				Element cashValueELe2 = new Element(CashValue);
//				Element EndYearEle2 = new Element("EndYear");
//				EndYearEle2.setText(String.valueOf(cashValuesListSize+i));
//				Element CashEle2 = new Element("Cash");
//				if(i==35-cashValuesListSize){
//					CashEle2.setText("（以下空白）");
//					}else{
//						CashEle2.setText("-");
//					}
//				
//				cashValueELe2.addContent(EndYearEle2);
//				cashValueELe2.addContent(CashEle2);
//				
//				CashValuesEle.addContent(cashValueELe2);
//			}
//		}
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<25){
			for(int i=0 ;i<25-cashValuesListSize;i++){
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
	
	
	private Document dealCashValues2 (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<33){
			for(int i=0 ;i<33-cashValuesListSize;i++){
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
	private Document dealPensionCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<25){
			for(int i=0 ;i<25-cashValuesListSize;i++){
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

//		JdomUtil.print(InStdDoc);
//		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	private Document dealDeValuesCopyToCashCalues (Document InStdDoc){
		/*减额交清表*/
		Element DeductionValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild("DeductionValues");
		List deductionValueList = DeductionValuesEle.getChildren("DeductionValue");
		int deductionValueListSize = deductionValueList.size();
		
		/*现金价值*/
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("dealPensionCashValues.cashValuesListSize："+cashValuesListSize);
		int i=0;
		int j=0;
		//将减额交清的金额放到现金价值标签里
		for(i=0;i<cashValuesListSize;i++){
			Element CashValue=(Element)cashValuesList.get(i);
			for(j=0;j<deductionValueListSize;j++){
				Element valuesEle=(Element)deductionValueList.get(j);
				if(CashValue.getChildTextTrim("EndYear").equals(valuesEle.getChildTextTrim("EndYear"))){
					Element copyEndYearAmnt=new Element("EndYearAmnt");
					copyEndYearAmnt.setText(valuesEle.getChildText("EndYearAmnt"));
					CashValue.addContent(copyEndYearAmnt);
				}
			}
		}
		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	

}