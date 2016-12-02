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

		// 保存银行方流水号，返回报文需要 lilu 20141127
		tranno = pNoStdXml.getRootElement().getChild("TXLifeRequest")
				.getChildText("TransRefGUID");
		// 保存银行销售渠道，根据渠道类别组织返回报文和保单样式 lilu 20141127
		// saleChannel=pNoStdXml.getRootElement().getChild("TXLifeRequest").getChild("OLifEExtension").getChildText("SourceType");
		saleChannel = mStdXml.getRootElement().getChild("Body")
				.getChildText("SaleChannel");

		// 20150123lilu
		// 增加规则校验：工行网银和终端只支持保额算保费，因此智赢C是保费算保额，所以按份数卖，一份保费10000元，因此他只会发份数过来
		// 20150203lilu 工行网银和终端的智赢C 不发职业代码
		// 20150203lilu 柜台渠道的智赢C 也按份数卖，一份保费10000，我们只取份数
		// 20150226lilu 邵宇洪要求智赢C 柜台支持份数和保费两选一，因为工行柜台录不了份数
		String riskcode = mStdXml.getRootElement().getChild("Body")
				.getChild("Risk").getChildText("RiskCode");
		if (!"0".equals(saleChannel) && "231204".equals(riskcode)
				|| "241201".equals(riskcode)) {
			// if("231204".equals(riskcode)){
			String mult = mStdXml.getRootElement().getChild("Body")
					.getChild("Risk").getChildText("Mult");
			if ("".equals(mult) || mult == null) {// 20150226
													// 处理智赢C未发份数的错误提示，在service类里添加
				mult = "0";
				mStdXml.getRootElement().getChild("Body").getChild("Risk")
						.getChild("Mult").setText("0");// 未传份数，设置为0
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
										// 跟核心沈世杰商定，保驾护航是保额算保费，银行不填保费，但会有默认值0，所以给核心一个空字符串
			mStdXml.getRootElement().getChild("Body").getChild("Risk")
					.getChild("Prem").setText("");
		}
		// 20150203lilu 柜台出单，职业代码写死
		if ("0".equals(saleChannel)) {
			if ("231204".equals(riskcode)) {
				// String
				// mult=mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
				// if("".equals(mult)||mult==null){//20150226 处理智赢C
				// 柜台录入不了份数，则份数置1，取保费
				// mult="1";
				mStdXml.getRootElement().getChild("Body").getChild("Risk")
						.getChild("Mult").setText("1");// 20150227又能录份数了，那么设置为1，以保费为准
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

			// 根据险种代码选择年金产品保单打印格式模板 20130408
			Element tRiskCode = (Element) XPath.selectSingleNode(
					pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
			cLogger.info("tRiskCode:" + tRiskCode.getValue());

			// saleChannel = "0";// 本地测试的时候加上
			if (saleChannel.equals("0")) {// 柜面出单的保单格式

				if ("231301".equals(tRiskCode.getValue())
						|| "231302".equals(tRiskCode.getValue())) {// 永裕年年和永利年年分红险
					tXslName = "NewContPensionRiskOut";
					Element tGetYearEle = (Element) XPath.selectSingleNode(
							pStdXml.getRootElement(),
							"/TranData/Body/Risk/GetYear");
					tGetYearEle.setText(tGetYearEle.getText() + "岁");

					pStdXml = dealPensionCashValues(pStdXml);
					pStdXml = dealDeValuesCopyToCashCalues(pStdXml);
				} else if ("211901".equals(tRiskCode.getValue())
						|| "211902".equals(tRiskCode.getValue())) {// 安赢借贷险和安赢A借贷险
					tXslName = "NewContOutLendRisk";
					pStdXml = dealCashValues(pStdXml);
				} else if ("221301".equals(tRiskCode.getValue())) {// 悦未来年金险
					Element tGetYearEle = (Element) XPath.selectSingleNode(
							pStdXml.getRootElement(),
							"/TranData/Body/Risk/GetYear");
					tGetYearEle.setText(tGetYearEle.getText() + "岁");
					// 处理现价的方法体目前相同，这样处理的方式是为了防止出现修改调整一个模板对另一个模板造成影响的问题。
					pStdXml = dealCashValues2(pStdXml);
				} else if ("221203".equals(tRiskCode.getValue())) {// 悦无忧附加险
																	// 20141120
					tXslName = "NewContOutEtraRisk";
					pStdXml = dealCashValues(pStdXml);
				} else if ("241201".equals(tRiskCode.getValue())) {// 中韩创赢财富两全保险（万能型）A款
					tXslName = "NewContOut_AlmightyRisk";
				} else if ("221206".equals(tRiskCode.getValue())) {// 中韩优越财富两全保险
					tXslName = "NewContOut_DominanceRisk";
					pStdXml = dealCashValues(pStdXml);
				}

				else {
					pStdXml = dealCashValues(pStdXml);
				}
			} else {// 1 网银和 8 自助终端出单
				tXslName = "NewContOutEB";
			}

			System.out.println("样式表:" + tXslName);
		}

		Document mNoStdXml = NewContOutXsl.newInstance(tXslName).getCache()
				.transform(pStdXml);

		JdomUtil.print(mNoStdXml);
		// 添加银行流水号 lilu 20141127
		mNoStdXml.getRootElement().getChild("TXLifeResponse")
				.getChild("TransRefGUID").setText(tranno);

		if (ttFlag.getValue().equals("0")) {

			if (saleChannel.equals("0")) {// 柜面出单
											// add by liuq 增加工行动态打印的行号等参数
											// 20101029
				List<Element> tSubVoucherList = XPath.selectNodes(
						mNoStdXml.getRootElement(),
						"/TXLife/TXLifeResponse/Print/SubVoucher");
				Element tPrintEle = (Element) XPath.selectSingleNode(
						mNoStdXml.getRootElement(),
						"/TXLife/TXLifeResponse/Print");
				// 增加页数
				Element tVoucherNum = new Element("VoucherNum");
				tVoucherNum.setText(String.valueOf(tSubVoucherList.size()));
				System.out.println("SubVoucher个数：" + tSubVoucherList.size());
				tPrintEle.addContent(tVoucherNum);
				// 增加总行数及行号
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

					System.out.println("TextRowContent个数"
							+ tTextRowContentList.size());
					for (int i = 0; i < tTextRowContentList.size(); i++) {
						Element tTextRowContentEle = tTextRowContentList.get(i);
						Element tTextContent = new Element("TextContent");
						tTextEle.addContent(tTextContent);
						Element mNewTextRowContentEle = (Element) tTextRowContentEle
								.clone();
						// 增加行号
						Element mRowNumEle = new Element("RowNum");
						mRowNumEle.setText(String.valueOf(i + 1));
						tTextContent.addContent(mRowNumEle);
						tTextContent.addContent(mNewTextRowContentEle);

					}
					// JdomUtil.print(mNoStdXml);
					// 增加总行数
					Element mRowTotalEle = new Element("RowTotal");
					mRowTotalEle.setText(tTextRowContentList.size() + "");
					tTextEle.addContent(mRowTotalEle);
					// 删除不正确的结构
					tTextEle.removeChildren("TextRowContent");
				}
			}
		}
		// System.out.println("转换前=========================");
		// JdomUtil.print(mNoStdXml);
		// String fOutNoStdxml=toStringFmtNull(mNoStdXml,"GBK");
		// mNoStdXml=JdomUtil.build(fOutNoStdxml.getBytes());
		// System.out.println("转换后=========================");
		// JdomUtil.print(mNoStdXml);

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * 处理受益人打印处的第一行显示"受益人"三个字，多个受益人时后面的受益人信息前不显示这三个字
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
			SendRiskIDMsgEle.setText(bnfEle.getChildText(Name) + "（证件号码："
					+ bnfEle.getChildText(IDNo) + "）");
			bnfEle.addContent(SendRiskIDMsgEle);
		}

		// Risk 节点下新增节点LendRiskDay存放借贷险保险期间（转化为天）
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
	 * 根据传入的报文检查现金价值的个数是否少于显示的行数（10），没有10行，则补充至10行，这是为了控制打印时的显示的完整性
	 * 
	 * @param InStdDoc
	 * @return
	 */
	private Document dealCashValues(Document InStdDoc) {
		// Element CashValuesEle =
		// InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		// List cashValuesList = CashValuesEle.getChildren(CashValue);
		// int cashValuesListSize = cashValuesList.size();
		// cLogger.info("dealPensionCashValues.cashValuesListSize："+cashValuesListSize);
		//
		// Element cashValueELe = new Element(CashValue);
		// Element EndYearEle = new Element("EndYear");
		// EndYearEle.setText(String.valueOf(cashValuesListSize+1));
		// Element CashEle = new Element("Cash");
		// CashEle.setText("（以下空白）");
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
		// CashEle2.setText("（以下空白）");
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
		cLogger.info("cashValuesListSize：" + cashValuesListSize);
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
		cLogger.info("cashValuesListSize：" + cashValuesListSize);
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
		cLogger.info("cashValuesListSize：" + cashValuesListSize);
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
		/* 减额交清表 */
		Element DeductionValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild("DeductionValues");
		List deductionValueList = DeductionValuesEle
				.getChildren("DeductionValue");
		int deductionValueListSize = deductionValueList.size();

		/* 现金价值 */
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body)
				.getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("dealPensionCashValues.cashValuesListSize："
				+ cashValuesListSize);
		int i = 0;
		int j = 0;
		// 将减额交清的金额放到现金价值标签里
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
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Users\\wangheng\\Desktop\\222.xml";
		String mOutFilePath = "C:\\Users\\wangheng\\Desktop\\1119012_1271_25_out.xml";
		// String mInFilePath =
		// "H:/李路/杭州中韩人寿/工行接口/UAT业务测试报文/1047435_23_1_outSvc.xml";
		// String mOutFilePath = "H:/李路/杭州中韩人寿/工行接口/UAT业务测试报文/悦无忧承保2.xml";

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
		System.out.println("成功结束！");
	}

}