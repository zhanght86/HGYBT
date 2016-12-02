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
	 * ���ش�Ͳ�ѯʹ�á�	
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
			if("221301".equals(tRiskCode.getValue())){//�к���δ�������
			   pStdXml =  dealCashValues2(pStdXml);
			}if("231302".equals(tRiskCode.getValue())){//��������ֺ��� 
				tXslName="ContConfirmOut_PensionCash";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"��");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else if("211902".equals(tRiskCode.getValue())){//��Ӯ�����A��       
				tXslName="ContConfirmOut_LendRisk";
				pStdXml =  dealCashValues(pStdXml);
			}else if("221203".equals(tRiskCode.getValue())){//��δ��������
				tXslName="ContConfirmOut_EtraRisk";
				pStdXml =  dealCashValues(pStdXml);
			}else if("241201".equals(tRiskCode.getValue())){//�к���Ӯ�Ƹ���ȫ���գ������ͣ�A��
				tXslName="ContConfirmOut_AlmightyRisk";
			}
		
			else if("221206".equals(tRiskCode.getValue()))
			{//�к���Խ�Ƹ���ȫ����
				tXslName="ContConfirmOut_DominanceRisk";
				pStdXml =  dealCashValues(pStdXml);
			}
			
			else{
				pStdXml =  dealCashValues(pStdXml);
			}
		}
		System.out.println("ʹ�õ���ʽ��"+tXslName);
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
			/*Start-��֯���ر���ͷ*/
			}
			Element mBkOthDate = new Element("BkOthDate");
			mBkOthDate.setText(
					String.valueOf(DateUtil.getCur8Date()));

			Element mBkOthSeq = new Element("BkOthSeq");
			mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

			Element mBkOthRetCode = new Element("BkOthRetCode");
			Element mBkOthRetMsg = new Element("BkOthRetMsg");
			
			Element mRetData = pStdXml.getRootElement().getChild("Head");
			if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
				mBkOthRetCode.setText("00000");
				mBkOthRetMsg.setText("���׳ɹ���");
			} else {	//����ʧ��
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
			/*End-��֯���ر���ͷ*/
		
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
			SendRiskIDMsgEle.setText(bnfEle.getChildText(Name)+"��֤�����룺"+bnfEle.getChildText(IDNo)+"��");
			bnfEle.addContent(SendRiskIDMsgEle);
		}
		
		//Risk �ڵ��������ڵ�LendRiskDay��Ž���ձ����ڼ䣨ת��Ϊ�죩
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
		cLogger.info("cashValuesListSize��"+cashValuesListSize);
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
		cLogger.info("cashValuesListSize��"+cashValuesListSize);
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
		cLogger.info("cashValuesListSize��"+cashValuesListSize);
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
		/*������*/
		Element DeductionValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild("DeductionValues");
		List deductionValueList = DeductionValuesEle.getChildren("DeductionValue");
		int deductionValueListSize = deductionValueList.size();
		
		/*�ֽ��ֵ*/
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("dealPensionCashValues.cashValuesListSize��"+cashValuesListSize);
		int i=0;
		int j=0;
		//�������Ľ��ŵ��ֽ��ֵ��ǩ��
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
	
	
	
	//���ڼ�ȥһBody/Risk/InsuEndDate
	public static String dateChange(String pDate){
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
	Date date;
	String datenow0=null;
	String datenow=null;
	try {
		date = format.parse(pDate);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);  //��1��
		datenow0=format.format(cal.getTime());
		char[] mChars = datenow0.toCharArray();
		StringBuilder  sd=new StringBuilder();
		datenow=sd.append(mChars, 0, 4).append('��')
		.append(mChars, 4, 2).append('��')
		.append(mChars, 6, 2).append('��').toString();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
	}
		return datenow;
}

	
//	public static void main(String[] args) throws Exception {
//		System.out.println("����ʼ��");
//
////		String mInFilePath = "G:/3115938_16_1_outSvc.xml";
////		String mOutFilePath = "G:/out.xml";
////		String mInFilePath = "E:/1017954_1_1014_in.xml";
////		String mOutFilePath = "E:/jh.xml";
////		String mInFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/��ӮC_1017957_3_1_outSvc.xml";
////		String mOutFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/��ӮC.xml";
////		String mInFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/��δ��_1017963_7_1_outSvc.xml";
////		String mInFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/��Ӯ_1018125_95_1_outSvc.xml";
////		String mInFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/����_1018461_63_1_outSvc.xml";
////		String mInFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/���ݻ���A_1018653_79_1_outSvc.xml";
//		String mInFilePath = "H:/1021461_19_1_outSvc.xml";
////		String mInFilePath = "F:/1018167_39_1_outSvc.xml";
////		String mOutFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/��Ӯ.xml";
////		String mOutFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/����.xml";
////		String mOutFilePath = "H:/��·/�����к�����/������/UATҵ����Ա���/���ݻ���A.xml";
//		String mOutFilePath = "H:/��Ӯ����.xml";
////		String mOutFilePath = "F:/����.xml";
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
//		System.out.println("�ɹ�������");
//	}
}
