package com.sinosoft.midplat.newabc.format;

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
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


public class ContConfirm extends XmlSimpFormat {
	
	String tXslName;
	private Element header=null;
	private String tranlog=null;
	private String riskcode=null;
	private String mTranNo=null;


	public ContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");

		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Element mRootEle = pNoStdXml.getRootElement();
		String uwTransId = mRootEle.getChild("App").getChild("Req").getChildText("ApplySerial");
//		String proposalprtno = mRootEle.getChild("App").getChild("Req").getChildText("PolicyNo");
//		String sql="select bak8 from cont where proposalprtno='"+proposalprtno+"' and state ='1' ";
//		String applyno=new ExeSQL().getOneValue(sql);
//		if(uwTransId!=applyno){
//			throw new MidplatException("������ʱ����������˳��Ų�����");
//		}
//		String sql2="select proposalprtno from cont where bak8='"+uwTransId+"' and state ='1' ";
//		String proposalprtno2=new ExeSQL().getOneValue(sql2);
//		if(proposalprtno!=proposalprtno2){
//			throw new MidplatException("������ʱ��Ͷ�����Ų�����");
//		}
		
		
		String cTransDate = mRootEle.getChild("Header").getChildText("TransDate");
		String cTranCom = mRootEle.getChild("Head").getChildText(TranCom);
		cLogger.info("��ѯ===================================!");
		String sqlStr = "select proposalprtno,otherno,logno,Operator from tranlog where trancom='"+cTranCom+"' and rcode='0' and funcflag='1012' and tranno='"+uwTransId+"' and MakeDate='"+cTransDate+"' order by logno desc";
		SSRS ssrs0=new ExeSQL().execSQL(sqlStr);
		cLogger.info("��ѯ�����"+ssrs0.MaxNumber);
		Document mStdXml = 
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		
		
		
//		//���ýɷѵ�Ͷ������
//		 cTransDate = mStdXml.getRootElement().getChild("Head").getChildText("TranDate");
//		 mTranNo = pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("ApplySerial");
//		 sqlStr = "select proposalprtno,otherno from tranlog where trancom='005' and rcode='0' and funcflag=01 and tranno='"+mTranNo+"' and MakeDate='"+cTransDate+"' order by logno desc";
//		 SSRS ssrs1=new ExeSQL().execSQL(sqlStr);
//		 //����Ͷ������	
//		 Element applyCode = mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo");
//		 applyCode.setText(ssrs1.GetText(1, 1));
//		 
		 
		cLogger.info("��ֵ===================================!");
		Element proposalPrtNo = mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo");
		Element contPrtNo = mStdXml.getRootElement().getChild("Body").getChild("ContPrtNo");
		Element tellerNo = mStdXml.getRootElement().getChild("Head").getChild("TellerNo");
		if(ssrs0.MaxNumber!=0){
			proposalPrtNo.setText(ssrs0.GetText(1, 1));
			contPrtNo.setText(ssrs0.GetText(1, 2));
			tellerNo.setText(ssrs0.GetText(1, 4));
			tranlog=ssrs0.GetText(1, 3);
		}else{
			proposalPrtNo.setText(null);
			contPrtNo.setText(null);
			tellerNo.setText(null);
			tranlog=null;
		}
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.Std2StdnoStd()...");
		
//		String mInFilePath2 = "H:/1016043_33_1014_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		header=(Element)mInXmlDoc.getRootElement().getChild("Header").clone();
//		mIs2.close();	
		
		
		
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		
		if (ttFlag.getValue().equals("0")){
			pStdXml =  dealBnf(pStdXml);	
//			//�������ִ���ѡ�񱣵���ӡ��ʽģ�� 20130228
			Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
			riskcode=tRiskCode.getValue();
			cLogger.info("tRiskCode:"+tRiskCode.getValue());
			if("211901".equals(tRiskCode.getValue()) || "211902".equals(tRiskCode.getValue())){//��Ӯ�Ͱ�ӮA�����
				tXslName="ContConfirmOutLendRisk";
				pStdXml =  dealCashValues(pStdXml);	
			}else if("231301".equals(tRiskCode.getValue())||"231302".equals(tRiskCode.getValue())){//��ԣ�������������ֺ���
				tXslName="ContConfirmOutPensionRisk";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"��");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else if("241201".equals(tRiskCode.getValue())){//�к���Ӯ�Ƹ���ȫ���գ������ͣ�A��
				tXslName="ContConfirmOut_AlmightyRisk";
			}
			else if("221206".equals(tRiskCode.getValue()))
			{//�к���Խ�Ƹ���ȫ����
				tXslName="DominanceRisk";
			}
			
			else{//���ݻ���A��׿Խ�Ƹ�
				pStdXml =  dealCashValues(pStdXml);
			}
			System.out.println("��ʽ��:"+tXslName);
		}
		
		
		Document mNoStdXml = 
		  ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
		Element  RetCode=new Element("RetCode");
		Element  RetMsg = new Element("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("���׳ɹ�=========");
		   RetCode.setText("000000");
		   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Prnts"));
		   if(!riskcode.equals("211902")&&!riskcode.equals("241201")){
			   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Messages"));
		   }
		   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("TbdPrnts"));
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("����ʧ��=========ʧ����Ϣ:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		Element insuSerial=new Element("InsuSerial");
		insuSerial.setText(tranlog);
		header.addContent(insuSerial);
		header.addContent(RetCode);
		header.addContent(RetMsg);
		mNoStdXml.getRootElement().addContent(header);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out ContConfirm.Std2StdnoStd()!");
		
		return mNoStdXml;
	}
	
	private Document dealPensionCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize��"+cashValuesListSize);
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
		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	
	private Document dealBnf(Document InStdDoc){
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
	private void dealPrint(Element mPrnts){
	     List<Element> mPrntList=mPrnts.getChildren("Prnt");
		 Element mCount=mPrnts.getChild("Count");
	     mCount.setText(String.valueOf(mPrntList.size()));
	     System.out.println("Prnt������"+String.valueOf(mPrntList.size()));
	     int i=1;
		 for(Element e:mPrntList){
			 e.setName("Prnt"+i);
			 i++;
		  }
	}

	public void setHeader(Element pHeader){
		header=pHeader;
	}
	
	public static void main(String[] args) throws Exception {
	System.out.println("����ʼ��");

	String mInFilePath = "C:\\Users\\wangheng\\Desktop\\111.xml";
	String mOutFilePath = "C:\\Users\\wangheng\\Desktop\\333.xml";
	
	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();
	
	ContConfirm ccf=new ContConfirm(null);
//	Document mOutXmlDoc = ccf.std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("�ɹ�������");
}
	
}
