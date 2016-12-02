//承保交易
package com.sinosoft.midplat.srcb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.icbc.format.NewContOutXsl;

public class ContConfirm extends XmlSimpFormat {
	private Element cHeader = null;
	String tXslName=null;
	String cAccNo=null;
	String cAccName=null;
	
	public ContConfirm(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		
		cHeader =
			(Element) pNoStdXml.getRootElement().getChild("Header").clone();
		
		Document mStdXml = 
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		
		cAccNo=pNoStdXml.getRootElement().getChild("Body").getChildText("AccNo");
		cAccName=pNoStdXml.getRootElement().getChild("Body").getChildText("AccName");
		
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	
	/**
	 * 供重打和查询使用。	
	 */
	void setHeader(Element pTransaction_Header) {
		cHeader = pTransaction_Header;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
//		String mInFilePath2 = "D:/msg/03/2014/201409/20140918/1446_27_1014_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		cTransaction_Header=(Element) mInXmlDoc.getRootElement().getChild("Transaction_Header").clone();
//		mIs2.close();	
		
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
		if (ttFlag.getValue().equals("0")){
			   pStdXml =  dealBnf(pStdXml);	
			if("221301".equals(tRiskCode.getValue())){
			   pStdXml =  dealCashValues2(pStdXml);
			}if("231302".equals(tRiskCode.getValue())){ 
				tXslName="ContConfirmOut_PensionCash";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else{
				pStdXml =  dealCashValues(pStdXml);
			}
		}
		System.out.println("使用的样式表："+tXslName);
		Document mNoStdXml =  
			ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
//		if(pStdXml.getRootElement().getChild("Head").getChildText(Flag).equals("0")){
//			List tDetail_List = mNoStdXml.getRootElement().getChild("Transaction_Body").getChildren("Detail_List");
//			for (int i=0;i<tDetail_List.size();i++){
//				Element ttDetail_List = (Element)tDetail_List.get(i);
//				List tDetail = ttDetail_List.getChildren("Detail");
//				for (int j=0;j<tDetail.size();j++){
//					Element ttDetail = (Element)tDetail.get(j);
//					List tBkDetail1 = ttDetail.getChildren("BkDetail1");
//					ttDetail_List.getChild("BkRecNum").setText(String.valueOf(tBkDetail1.size()));
//				}
//			}
//			}
		
		/*Start-组织返回报文头*/

		Element mRetCode = new Element("Flag");
		Element mRetMsg = new Element("Desc");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mRetCode.setText("0");
			mRetMsg.setText("交易成功！");
		} else {	//交易失败
			mRetCode.setText("1");
			mRetMsg.setText(mRetData.getChildText(Desc));
		}

		Element mHeader = new Element("Header");
		mHeader.addContent(mRetCode);
		mHeader.addContent(mRetMsg);
		mNoStdXml.getRootElement().addContent(mHeader);
		
		/*End-组织返回报文头*/

			mNoStdXml.getRootElement().getChild("Body").getChild("AccNo").setText(cAccNo);
			mNoStdXml.getRootElement().getChild("Body").getChild("AccName").setText(cAccName);
			
			JdomUtil.print(mNoStdXml);
			
			cLogger.info("Out ContConfirm.std2NoStd()!");
			
			return mNoStdXml;
		}
		
		
		private Document dealBnf(Document InStdDoc){
			List bnfList = InStdDoc.getRootElement().getChild(Body).getChildren(Bnf);
			for(int i = 0;i<bnfList.size();i++){
				Element bnfEle = (Element) bnfList.get(i);
				Element SeqNoELe = new Element("SeqNo");
				SeqNoELe.setText(String.valueOf(i+1));
				bnfEle.addContent(SeqNoELe);
			}
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

//			JdomUtil.print(InStdDoc);
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
		
		public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");


		String mInFilePath = "D:/msg/04/2015/201501/20150128/146_6_1014_in.xml";
		String mOutFilePath = "H:/2222.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}
