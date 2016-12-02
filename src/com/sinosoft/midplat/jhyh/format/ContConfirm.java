package com.sinosoft.midplat.jhyh.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.jhyh.format.ContConfirmInXsl;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.jhyh.format.ContConfirmOutXsl;
import com.sinosoft.utility.ExeSQL;


public class ContConfirm extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	String tXslName=null;

	public ContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);

		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	} 
	
	/**
	 * 供重打和查询使用。	
	 */
	void setHeader(Element pTransaction_Header) {
		cTransaction_Header = pTransaction_Header;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");

		
		
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
		if (ttFlag.getValue().equals("0")){
			   pStdXml =  dealBnf(pStdXml);	
			if("221301".equals(tRiskCode.getValue())){//中韩悦未来年金险
			   pStdXml =  dealCashValues2(pStdXml);
			}if("231302".equals(tRiskCode.getValue())){//永利年年分红险 
				tXslName="ContConfirmOut_PensionCash";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else if("211902".equals(tRiskCode.getValue())){//安赢借贷险A款       
				tXslName="ContConfirmOut_LendRisk";
				pStdXml =  dealCashValues(pStdXml);
			}else if("221203".equals(tRiskCode.getValue())){//悦未来附加险
				tXslName="ContConfirmOut_EtraRisk";
				pStdXml =  dealCashValues(pStdXml);
			}else if("241201".equals(tRiskCode.getValue())){//中韩创赢财富两全保险（万能型）A款
				tXslName="ContConfirmOut_AlmightyRisk";
			}
		
			else if("221206".equals(tRiskCode.getValue()))
			{//中韩优越财富两全保险
				tXslName="ContConfirmOut_DominanceRisk";
				pStdXml =  dealCashValues(pStdXml);
			}
			
			else{
				pStdXml =  dealCashValues(pStdXml);
			}
		}
		System.out.println("使用的样式表："+tXslName);
		Document mNoStdXml =  
			ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
		if(pStdXml.getRootElement().getChild("Head").getChildText(Flag).equals("0")){
			List tDetail_List = mNoStdXml.getRootElement().getChild("Transaction_Body").getChildren("Detail_List");
			for (int i=0;i<tDetail_List.size();i++){
				Element ttDetail_List = (Element)tDetail_List.get(i);
				List tDetail = ttDetail_List.getChildren("Detail");
				for (int j=0;j<tDetail.size();j++){
					Element ttDetail = (Element)tDetail.get(j);
					List tBkDetail1 = ttDetail.getChildren("BkDetail1");
					ttDetail_List.getChild("BkRecNum").setText(String.valueOf(tBkDetail1.size()));
				}
			}
			/*Start-组织返回报文头*/
			}
			Element mBkOthDate = new Element("BkOthDate");
			mBkOthDate.setText(
					String.valueOf(DateUtil.getCur8Date()));

			Element mBkOthSeq = new Element("BkOthSeq");
			mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

			Element mBkOthRetCode = new Element("BkOthRetCode");
			Element mBkOthRetMsg = new Element("BkOthRetMsg");
			
			Element mRetData = pStdXml.getRootElement().getChild("Head");
			if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
				mBkOthRetCode.setText("00000");
				mBkOthRetMsg.setText("交易成功！");
			} else {	//交易失败
				mBkOthRetCode.setText("11111");
				mBkOthRetMsg.setText(
						mRetData.getChildText(Desc));
			}
			
			JdomUtil.print(mNoStdXml);
			Element mTran_Response = new Element("Tran_Response");
			mTran_Response.addContent(mBkOthDate);
			mTran_Response.addContent(mBkOthSeq);
			mTran_Response.addContent(mBkOthRetCode);
			mTran_Response.addContent(mBkOthRetMsg);

//			cTransaction_Header.addContent(mTran_Response);
			mNoStdXml.getRootElement().addContent(mTran_Response);

			mNoStdXml.getRootElement().addContent(cTransaction_Header);
			/*End-组织返回报文头*/
		
		cLogger.info("Out ContConfirm.std2NoStd()!");
		return mNoStdXml;
	}
	
	private Document dealBnf(Document InStdDoc){
		System.out.println("=====================dealbnf====================");
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
		
		JdomUtil.print(InStdDoc);
		
		return InStdDoc;
	}
	

	private Document dealCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<25){
			for(int i=0 ;i<25-cashValuesListSize;i++){
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize+i+1));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");
				
				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);
				
				CashValuesEle.addContent(cashValueELe);
			}
		}

//		JdomUtil.print(InStdDoc);
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
//		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	
	private Document dealPensionCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<35){
			for(int i=0 ;i<35-cashValuesListSize;i++){
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
//		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	
	
	
	//日期减去一Body/Risk/InsuEndDate
	public static String dateChange(String pDate){
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
	Date date;
	String datenow0=null;
	String datenow=null;
	try {
		date = format.parse(pDate);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);  //减1天
		datenow0=format.format(cal.getTime());
		char[] mChars = datenow0.toCharArray();
		StringBuilder  sd=new StringBuilder();
		datenow=sd.append(mChars, 0, 4).append('年')
		.append(mChars, 4, 2).append('月')
		.append(mChars, 6, 2).append('日').toString();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
	}
		return datenow;
}

	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
////		String mInFilePath = "G:/3115938_16_1_outSvc.xml";
////		String mOutFilePath = "G:/out.xml";
////		String mInFilePath = "E:/1017954_1_1014_in.xml";
////		String mOutFilePath = "E:/jh.xml";
////		String mInFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/智赢C_1017957_3_1_outSvc.xml";
////		String mOutFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/智赢C.xml";
////		String mInFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/悦未来_1017963_7_1_outSvc.xml";
////		String mInFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/安赢_1018125_95_1_outSvc.xml";
////		String mInFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/永利_1018461_63_1_outSvc.xml";
////		String mInFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/保驾护航A_1018653_79_1_outSvc.xml";
//		String mInFilePath = "H:/1021461_19_1_outSvc.xml";
////		String mInFilePath = "F:/1018167_39_1_outSvc.xml";
////		String mOutFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/安赢.xml";
////		String mOutFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/永利.xml";
////		String mOutFilePath = "H:/李路/杭州中韩人寿/金华银行/UAT业务测试报文/保驾护航A.xml";
//		String mOutFilePath = "H:/安赢保单.xml";
////		String mOutFilePath = "F:/永利.xml";
//
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);
//
//		
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("成功结束！");
//	}
}
