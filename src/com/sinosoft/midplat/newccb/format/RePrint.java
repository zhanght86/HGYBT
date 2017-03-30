//���б����ش�
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class RePrint extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String stranNo = null;
	private String sContNo = null;
	private String sContPrtNo = null;
	private String sProposalPrtNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;	
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
//		JdomUtil.print(cTransaction_Header);
		
		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//�����µ�ȷ��ʱ�Ľ�����ˮ�ţ����������µ�ȷ�Ͻ��׵�Ͷ�����ţ�����ӡˢ��
		stranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_Co_Jrnl_No");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		sContNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//�ý�����ˮ�Ŵ�Cont���в��Ҷ�Ӧ��Ͷ������
		String getProposalPrtNoSQL = new StringBuilder("select ProposalPrtNo from cont where contno = '").append(sContNo).append("'")
		.toString();
		sProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
		//�ý�����ˮ�Ŵ�Tranlog���в���Ͷ��ʱ��Ӧ�ľɵı���ӡˢ��
		String getContPrtNoSQL = new StringBuilder("select otherno from tranlog where contno = '").append(sContNo).append("'")
		.append(" and funcflag='").append("1032'").append(" and rcode='0'").toString();
		sContPrtNo = new ExeSQL().getOneValue(getContPrtNoSQL);
//		//�ý�����ˮ�Ŵ�Tranlog���в��Ҷ�Ӧ��Ͷ������
//		String getProposalPrtNoSQL = new StringBuilder("select ProposalPrtNo from tranlog where contno = '").append(sContNo).append("'")
//		.append(" and tranno='").append(stranNo).append("'").append(" and rcode='0'").toString();
//		sProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
//		//�ý�����ˮ�Ŵ�Tranlog���в��Ҷ�Ӧ�ľɵı���ӡˢ��
//		String getContPrtNoSQL = new StringBuilder("select otherno from tranlog where ProposalPrtNo = '").append(sProposalPrtNo).append("'")
//		.append(" and funcflag='").append("1012'").append(" and rcode='0'").toString();
//		sContPrtNo = new ExeSQL().getOneValue(getContPrtNoSQL);
		
		mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalPrtNo);
		mStdXml.getRootElement().getChild("Body").getChild("OldContPrtNo").setText(sContPrtNo);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public  Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
		if (ttFlag.getValue().equals("0")){
			
			//�к���Խ�Ƹ���ȫ����
			if("221206".equals(tRiskCode.getValue())){
				pStdXml =  dealBnf(pStdXml);
				pStdXml =  dealCashValues(pStdXml);
				
			}
		}
		
		Document mNoStdXml = null;
//		if("221206".equals(tRiskCode.getValue())){
//			 mNoStdXml = 
//					RePrintOutXsl.newInstance().getCache().transform(pStdXml);
//		}else{
//			 mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml,"1011");
//		}
//		mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml,"1011");
		pStdXml =  dealCashValues(pStdXml);
		mNoStdXml=PrintContOutXsl.newInstance(null).getCache().transform(pStdXml);
		//������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//���˱��ɹ���ʱ�򣬷���TX_BODYʱ������COM_ENTITY�ڵ�
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		//COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		
		//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-��֯���ر���ͷ*/
		
		
		if(pStdXml.getRootElement().getChild("Head").getChildText(Flag).equals("0")){
			List tDetail_List = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildren("Detail_List");
			for (int i=0;i<tDetail_List.size();i++){
				Element ttDetail_List = (Element)tDetail_List.get(i);
				List tDetail = ttDetail_List.getChildren("Detail");
				for (int j=0;j<tDetail.size();j++){
					Element ttDetail = (Element)tDetail.get(j);
					List Ret_Inf = ttDetail.getChildren("Ret_Inf");
					ttDetail_List.getChild("Rvl_Rcrd_Num").setText(String.valueOf(Ret_Inf.size()));
				}
			}
		}
		
		
		cLogger.info("Out RePrint.std2NoStd()!");
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

		JdomUtil.print(InStdDoc);
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
		JdomUtil.print(InStdDoc);
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
		
		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
        Element TranData=new Element("TranData");
        Element Head=new Element("Head");
        Element Flag=new Element("Flag");
        Flag.setText("1");
        Element Desc=new Element("Desc");
        Desc.setText("�ش���ʧ��");
        Head.addContent(Flag);
        Head.addContent(Desc);
        TranData.addContent(Head);
        Document TranDataDoc=new Document(TranData);
        Element newccb=NewCcbConf.newInstance().getConf().getRootElement();
        Element xpath= (Element) XPath.selectSingleNode(newccb,"business[funcFlag='1011']");
        System.out.println("-------"+JdomUtil.toStringFmt(xpath));
        RePrint  rrrr=new RePrint(xpath);
        Document TranDataDocOut= rrrr.std2NoStd(TranDataDoc);
        System.out.println("======"+JdomUtil.toStringFmt(TranDataDocOut));
//		String mInFilePath = "C:/Users/liuzk/Desktop/11.xml";
//		String mOutFilePath = "C:/Users/liuzk/Desktop/13.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();

		System.out.println("�ɹ�������");
	}
}
