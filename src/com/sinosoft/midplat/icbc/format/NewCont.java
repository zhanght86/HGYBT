package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NewCont extends XmlSimpFormat {

	String tXslName;
	private String tranno = null;
	private String saleChannel = null;

	public NewCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");

		Document mStdXml = NewContInXsl.newInstance().getCache()
				.transform(pNoStdXml);

		// �������з���ˮ�ţ����ر�����Ҫ lilu 20141127
		tranno = pNoStdXml.getRootElement().getChild("TXLifeRequest")
				.getChildText("TransRefGUID");
		// �������������������������������֯���ر��ĺͱ�����ʽ lilu 20141127
		// saleChannel=pNoStdXml.getRootElement().getChild("TXLifeRequest").getChild("OLifEExtension").getChildText("SourceType");
		saleChannel = mStdXml.getRootElement().getChild("Body")
				.getChildText("SaleChannel");

		// 20150123lilu
		// ���ӹ���У�飺�����������ն�ֻ֧�ֱ����㱣�ѣ������ӮC�Ǳ����㱣����԰���������һ�ݱ���10000Ԫ�������ֻ�ᷢ��������
		// 20150203lilu �����������ն˵���ӮC ����ְҵ����
		// 20150203lilu ��̨��������ӮC Ҳ����������һ�ݱ���10000������ֻȡ����
		// 20150226lilu �����Ҫ����ӮC ��̨֧�ַ����ͱ�����ѡһ����Ϊ���й�̨¼���˷���
		String riskcode = mStdXml.getRootElement().getChild("Body")
				.getChild("Risk").getChildText("RiskCode");
		if (!"0".equals(saleChannel) && "231204".equals(riskcode)
				|| "241201".equals(riskcode)) {
			// if("231204".equals(riskcode)){
			String mult = mStdXml.getRootElement().getChild("Body")
					.getChild("Risk").getChildText("Mult");
			if ("".equals(mult) || mult == null) {// 20150226
													// ������ӮCδ�������Ĵ�����ʾ����service�������
				mult = "0";
				mStdXml.getRootElement().getChild("Body").getChild("Risk")
						.getChild("Mult").setText("0");// δ������������Ϊ0
			}
			mStdXml.getRootElement().getChild("Body").getChild("Risk")
					.getChild("Prem")
					.setText(String.valueOf(Integer.parseInt(mult) * 1000000));
			mStdXml.getRootElement().getChild("Body").getChild("Risk")
					.getChild("Mult").setText("1");
			mStdXml.getRootElement().getChild("Body").getChild("Appnt")
					.getChild("JobCode").setText("3010101");
			mStdXml.getRootElement().getChild("Body").getChild("Insured")
					.getChild("JobCode").setText("3010101");
		}
		if ("221201".equals(riskcode)) {// 20150204lilu
										// �������������̶������ݻ����Ǳ����㱣�ѣ����в���ѣ�������Ĭ��ֵ0�����Ը�����һ�����ַ���
			mStdXml.getRootElement().getChild("Body").getChild("Risk")
					.getChild("Prem").setText("");
		}
		// 20150203lilu ��̨������ְҵ����д��
		if ("0".equals(saleChannel)) {
			if ("231204".equals(riskcode)) {
				// String
				// mult=mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
				// if("".equals(mult)||mult==null){//20150226 ������ӮC
				// ��̨¼�벻�˷������������1��ȡ����
				// mult="1";
				mStdXml.getRootElement().getChild("Body").getChild("Risk")
						.getChild("Mult").setText("1");// 20150227����¼�����ˣ���ô����Ϊ1���Ա���Ϊ׼
				// }
			}
			mStdXml.getRootElement().getChild("Body").getChild("Appnt")
					.getChild("JobCode").setText("3010101");
			mStdXml.getRootElement().getChild("Body").getChild("Insured")
					.getChild("JobCode").setText("3010101");
		}

		JdomUtil.print(mStdXml);
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		
		cLogger.info("Into NewCont.std2NoStd()...");
		Element ttFlag = (Element) XPath.selectSingleNode(
				pStdXml.getRootElement(), "/TranData/Head/Flag");
		if (ttFlag.getValue().equals("0")) {
			pStdXml = dealBnf(pStdXml);

			// �������ִ���ѡ������Ʒ������ӡ��ʽģ�� 20130408
			Element tRiskCode = (Element) XPath.selectSingleNode(
					pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
			cLogger.info("tRiskCode:" + tRiskCode.getValue());

			// saleChannel = "0";// ���ز��Ե�ʱ�����
			if (saleChannel.equals("0")) {// ��������ı�����ʽ

				if ("231301".equals(tRiskCode.getValue())
						|| "231302".equals(tRiskCode.getValue())) {// ��ԣ�������������ֺ���
					tXslName = "NewContPensionRiskOut";
					Element tGetYearEle = (Element) XPath.selectSingleNode(
							pStdXml.getRootElement(),
							"/TranData/Body/Risk/GetYear");
					tGetYearEle.setText(tGetYearEle.getText() + "��");

					pStdXml = dealPensionCashValues(pStdXml);
					pStdXml = dealDeValuesCopyToCashCalues(pStdXml);
				} else if ("211901".equals(tRiskCode.getValue())
						|| "211902".equals(tRiskCode.getValue())) {// ��Ӯ����պͰ�ӮA�����
					tXslName = "NewContOutLendRisk";
					pStdXml = dealCashValues(pStdXml);
				} else if ("221301".equals(tRiskCode.getValue())) {// ��δ�������
					Element tGetYearEle = (Element) XPath.selectSingleNode(
							pStdXml.getRootElement(),
							"/TranData/Body/Risk/GetYear");
					tGetYearEle.setText(tGetYearEle.getText() + "��");
					// �����ּ۵ķ�����Ŀǰ��ͬ����������ķ�ʽ��Ϊ�˷�ֹ�����޸ĵ���һ��ģ�����һ��ģ�����Ӱ������⡣
					pStdXml = dealCashValues2(pStdXml);
				} else if ("221203".equals(tRiskCode.getValue())) {// �����Ǹ�����
																	// 20141120
					tXslName = "NewContOutEtraRisk";
					pStdXml = dealCashValues(pStdXml);
				} else if ("241201".equals(tRiskCode.getValue())) {// �к���Ӯ�Ƹ���ȫ���գ������ͣ�A��
					tXslName = "NewContOut_AlmightyRisk";
				} else if ("221206".equals(tRiskCode.getValue())) {// �к���Խ�Ƹ���ȫ����
					tXslName = "NewContOut_DominanceRisk";
					pStdXml = dealCashValues(pStdXml);
				}

				else {
					pStdXml = dealCashValues(pStdXml);
				}
			} else {// 1 ������ 8 �����ն˳���
				tXslName = "NewContOutEB";
			}

			System.out.println("��ʽ��:" + tXslName);
		}

		Document mNoStdXml = NewContOutXsl.newInstance(tXslName).getCache()
				.transform(pStdXml);

		JdomUtil.print(mNoStdXml);
		// ���������ˮ�� lilu 20141127
		mNoStdXml.getRootElement().getChild("TXLifeResponse")
				.getChild("TransRefGUID").setText(tranno);

		if (ttFlag.getValue().equals("0")) {

			if (saleChannel.equals("0")) {// �������
											// add by liuq ���ӹ��ж�̬��ӡ���кŵȲ���
											// 20101029
				List<Element> tSubVoucherList = XPath.selectNodes(
						mNoStdXml.getRootElement(),
						"/TXLife/TXLifeResponse/Print/SubVoucher");
				Element tPrintEle = (Element) XPath.selectSingleNode(
						mNoStdXml.getRootElement(),
						"/TXLife/TXLifeResponse/Print");
				// ����ҳ��
				Element tVoucherNum = new Element("VoucherNum");
				tVoucherNum.setText(String.valueOf(tSubVoucherList.size()));
				System.out.println("SubVoucher������" + tSubVoucherList.size());
				tPrintEle.addContent(tVoucherNum);
				// �������������к�
				for (Element tSubVoucherEle : tSubVoucherList) {
					// List<Element> tTextRowContentList =
					// XPath.selectNodes(tSubVoucherEle,
					// "/Text/TextRowContent");
					List<Element> tTextRowContentList = tSubVoucherEle
							.getChild("Text").getChildren("TextRowContent");
					// Element tTextEle = (Element)
					// XPath.selectSingleNode(tSubVoucherEle, "/Text");
					Element tTextEle = tSubVoucherEle.getChild("Text");
					// JdomUtil.print(tTextEle);

					System.out.println("TextRowContent����"
							+ tTextRowContentList.size());
					for (int i = 0; i < tTextRowContentList.size(); i++) {
						Element tTextRowContentEle = tTextRowContentList.get(i);
						Element tTextContent = new Element("TextContent");
						tTextEle.addContent(tTextContent);
						Element mNewTextRowContentEle = (Element) tTextRowContentEle
								.clone();
						// �����к�
						Element mRowNumEle = new Element("RowNum");
						mRowNumEle.setText(String.valueOf(i + 1));
						tTextContent.addContent(mRowNumEle);
						tTextContent.addContent(mNewTextRowContentEle);

					}
					// JdomUtil.print(mNoStdXml);
					// ����������
					Element mRowTotalEle = new Element("RowTotal");
					mRowTotalEle.setText(tTextRowContentList.size() + "");
					tTextEle.addContent(mRowTotalEle);
					// ɾ������ȷ�Ľṹ
					tTextEle.removeChildren("TextRowContent");
				}
			}
		}
		// System.out.println("ת��ǰ=========================");
		// JdomUtil.print(mNoStdXml);
		// String fOutNoStdxml=toStringFmtNull(mNoStdXml,"GBK");
		// mNoStdXml=JdomUtil.build(fOutNoStdxml.getBytes());
		// System.out.println("ת����=========================");
		// JdomUtil.print(mNoStdXml);

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * ���������˴�ӡ���ĵ�һ����ʾ"������"�����֣����������ʱ�������������Ϣǰ����ʾ��������
	 * 
	 * @param InStdDoc
	 * @return
	 */
	private Document dealBnf(Document InStdDoc) {
		List bnfList = InStdDoc.getRootElement().getChild(Body)
				.getChildren(Bnf);
		for (int i = 0; i < bnfList.size(); i++) {
			Element bnfEle = (Element) bnfList.get(i);
			Element SeqNoELe = new Element("SeqNo");
			SeqNoELe.setText(String.valueOf(i + 1));
			bnfEle.addContent(SeqNoELe);

			Element SendRiskIDMsgEle = new Element("LendRiskIDMsg");
			SendRiskIDMsgEle.setText(bnfEle.getChildText(Name) + "��֤�����룺"
					+ bnfEle.getChildText(IDNo) + "��");
			bnfEle.addContent(SendRiskIDMsgEle);
		}

		// Risk �ڵ��������ڵ�LendRiskDay��Ž���ձ����ڼ䣨ת��Ϊ�죩
		Element RiskEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk);
		String startDay = RiskEle.getChildText(CValiDate);
		String endDay = RiskEle.getChildText(InsuEndDate);

		Element LendRiskDayEle = new Element("LendRiskDay");
		LendRiskDayEle.setText(String.valueOf(DateUtil.compareDay(startDay,
				endDay) + 1));
		RiskEle.addContent(LendRiskDayEle);

		return InStdDoc;
	}

	/**
	 * ���ݴ���ı��ļ���ֽ��ֵ�ĸ����Ƿ�������ʾ��������10����û��10�У��򲹳���10�У�����Ϊ�˿��ƴ�ӡʱ����ʾ��������
	 * 
	 * @param InStdDoc
	 * @return
	 */
	private Document dealCashValues(Document InStdDoc) {
		// Element CashValuesEle =
		// InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		// List cashValuesList = CashValuesEle.getChildren(CashValue);
		// int cashValuesListSize = cashValuesList.size();
		// cLogger.info("dealPensionCashValues.cashValuesListSize��"+cashValuesListSize);
		//
		// Element cashValueELe = new Element(CashValue);
		// Element EndYearEle = new Element("EndYear");
		// EndYearEle.setText(String.valueOf(cashValuesListSize+1));
		// Element CashEle = new Element("Cash");
		// CashEle.setText("�����¿հף�");
		//
		// cashValueELe.addContent(EndYearEle);
		// cashValueELe.addContent(CashEle);
		// CashValuesEle.addContent(cashValueELe);
		//
		// cashValuesListSize = cashValuesListSize+1;
		//
		// if(cashValuesListSize<35){
		// for(int i=0 ;i<35-cashValuesListSize;i++){
		// Element cashValueELe2 = new Element(CashValue);
		// Element EndYearEle2 = new Element("EndYear");
		// EndYearEle2.setText(String.valueOf(cashValuesListSize+i));
		// Element CashEle2 = new Element("Cash");
		// if(i==35-cashValuesListSize){
		// CashEle2.setText("�����¿հף�");
		// }else{
		// CashEle2.setText("-");
		// }
		//
		// cashValueELe2.addContent(EndYearEle2);
		// cashValueELe2.addContent(CashEle2);
		//
		// CashValuesEle.addContent(cashValueELe2);
		// }
		// }
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize��" + cashValuesListSize);
		if (cashValuesListSize < 25) {
			for (int i = 0; i < 25 - cashValuesListSize; i++) {
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize + i));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");

				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);

				CashValuesEle.addContent(cashValueELe);
			}
		}
		// JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

	private Document dealCashValues2(Document InStdDoc) {
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize��" + cashValuesListSize);
		if (cashValuesListSize < 33) {
			for (int i = 0; i < 33 - cashValuesListSize; i++) {
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize + i));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");

				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);

				CashValuesEle.addContent(cashValueELe);
			}
		}
		// JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

	private Document dealPensionCashValues(Document InStdDoc) {
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize��" + cashValuesListSize);
		if (cashValuesListSize < 25) {
			for (int i = 0; i < 25 - cashValuesListSize; i++) {
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize + i));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");

				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);

				CashValuesEle.addContent(cashValueELe);
			}
		}

		// JdomUtil.print(InStdDoc);
		// JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

	private Document dealDeValuesCopyToCashCalues(Document InStdDoc) {
		/* ������ */
		Element DeductionValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild("DeductionValues");
		List deductionValueList = DeductionValuesEle
				.getChildren("DeductionValue");
		int deductionValueListSize = deductionValueList.size();

		/* �ֽ��ֵ */
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("dealPensionCashValues.cashValuesListSize��"
				+ cashValuesListSize);
		int i = 0;
		int j = 0;
		// �������Ľ��ŵ��ֽ��ֵ��ǩ��
		for (i = 0; i < cashValuesListSize; i++) {
			Element CashValue = (Element) cashValuesList.get(i);
			for (j = 0; j < deductionValueListSize; j++) {
				Element valuesEle = (Element) deductionValueList.get(j);
				if (CashValue.getChildTextTrim("EndYear").equals(
						valuesEle.getChildTextTrim("EndYear"))) {
					Element copyEndYearAmnt = new Element("EndYearAmnt");
					copyEndYearAmnt.setText(valuesEle
							.getChildText("EndYearAmnt"));
					CashValue.addContent(copyEndYearAmnt);
				}
			}
		}
		// JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

	public static String toStringFmtNull(Document pXmlDoc, String pEncodingDecl) {
		Format mFormat = Format.getRawFormat().setIndent("");
		mFormat.setLineSeparator("");
		if (null == pEncodingDecl) {
			mFormat.setOmitDeclaration(true);
		} else if ("".equals(pEncodingDecl)) {
			mFormat.setOmitEncoding(true);
		} else {
			mFormat.setEncoding(pEncodingDecl);
		}
		return new XMLOutputter(mFormat).outputString(pXmlDoc);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "C:\\Users\\wangheng\\Desktop\\222.xml";
		String mOutFilePath = "C:\\Users\\wangheng\\Desktop\\1119012_1271_25_out.xml";
		// String mInFilePath =
		// "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/1047435_23_1_outSvc.xml";
		// String mOutFilePath = "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/�����ǳб�2.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		// JdomUtil.print(mInXmlDoc);

		NewCont newCont = new NewCont(null);
		
		Document mOutXmlDoc = newCont.noStd2Std(mInXmlDoc);
		// Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);

		// JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		JdomUtil.print(mOutXmlDoc);
		System.out.println("�ɹ�������");
	}

}