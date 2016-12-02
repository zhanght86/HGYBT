package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.icbc.format.ICBCNewContInXsl;
import com.sinosoft.midplat.icbc.format.ICBCNewContOutXsl;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ICBCNewCont extends XmlSimpFormat
{

	String tXslName;
	private String tranno = null;
	private String saleChannel = null;

	public ICBCNewCont(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into ICBCNewCont.noStd2Std()...");

		Document mStdXml = ICBCNewContInXsl.newInstance().getCache().transform(pNoStdXml);

		// 保存银行方流水号，返回报文需要 lilu 20141127
		tranno = pNoStdXml.getRootElement().getChild("TXLifeRequest").getChildText("TransRefGUID");
		// 保存银行销售渠道，根据渠道类别组织返回报文和保单样式 lilu 20141127
		saleChannel = mStdXml.getRootElement().getChild("Body").getChildText("SaleChannel");

		// 20150123lilu
		// 增加规则校验：工行网银和终端只支持保额算保费，因此智赢C是保费算保额，所以按份数卖，一份保费10000元，因此他只会发份数过来
		// 20150203lilu 工行网银和终端的智赢C 不发职业代码
		// 20150203lilu 柜台渠道的智赢C 也按份数卖，一份保费10000，我们只取份数
		// 20150226lilu 邵宇洪要求智赢C 柜台支持份数和保费两选一，因为工行柜台录不了份数
		String riskcode = mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("RiskCode");
		if (!"0".equals(saleChannel) && "231204".equals(riskcode))
		{
			// if("231204".equals(riskcode)){
			String mult = mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
			if ("".equals(mult) || mult == null)
			{// 20150226 处理智赢C未发份数的错误提示，在service类里添加
				mult = "0";
				mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("0");// 未传份数，设置为0
			}
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText(String.valueOf(Integer.parseInt(mult) * 1000000));
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("1");
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("JobCode").setText("3010101");
			mStdXml.getRootElement().getChild("Body").getChild("Insured").getChild("JobCode").setText("3010101");
		}
		if ("221201".equals(riskcode))
		{// 20150204lilu 跟核心沈世杰商定，保驾护航是保额算保费，银行不填保费，但会有默认值0，所以给核心一个空字符串
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText("");
		}
		// 20150203lilu 柜台出单，职业代码写死
		if ("0".equals(saleChannel))
		{
			if ("231204".equals(riskcode))
			{
				// String
				// mult=mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
				// if("".equals(mult)||mult==null){//20150226 处理智赢C
				// 柜台录入不了份数，则份数置1，取保费
				// mult="1";
				mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("1");// 20150227又能录份数了，那么设置为1，以保费为准
				// }
			}
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("JobCode").setText("3010101");
			mStdXml.getRootElement().getChild("Body").getChild("Insured").getChild("JobCode").setText("3010101");
		}

		JdomUtil.print(mStdXml);
		cLogger.info("Out ICBCNewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into ICBCNewContInput.std2NoStd()...==工行试算返回");

		// 根据险种代码选择年金产品保单打印格式模板 20150101
		Element tRiskCode = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");

		// saleChannel="1";//本地测试加上
		if (!saleChannel.equals("0"))
		{// 非柜台试算
			tXslName = "NewContOutEB";
		}
		if (tRiskCode != null)
		{
			if ("221203".equals(tRiskCode.getValue()))
			{// 悦无忧附加险 20141120
				tXslName = "NewContOutEtraRisk";
				pStdXml = dealCashValues(pStdXml);
			}
			if ("121504".equals(tRiskCode.getValue()))
			{// 中韩臻佑终身重大疾病保险 
				tXslName = "NewContOutIllRisk";
				pStdXml = dealCashValues(pStdXml);
			}
		}
		System.out.println("样式表:" + tXslName);

		Document mNoStdXml = ICBCNewContOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
		//非柜台试算，设置交易码
		if(!saleChannel.equals("0"))
		{
			mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransType").setText("1012");
			mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransType").setAttribute("tc", "1012");
		}
		

		// 添加银行流水号 lilu 20141127
		mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransRefGUID").setText(tranno);

		JdomUtil.print(mNoStdXml);

		cLogger.info("Out ICBCNewContInput.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * 根据传入的报文检查现金价值的个数是否少于显示的行数（10），没有10行，则补充至10行，这是为了控制打印时的显示的完整性
	 * 
	 * @param InStdDoc
	 * @return
	 */
	private Document dealCashValues(Document InStdDoc)
	{
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
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize：" + cashValuesListSize);
		if (cashValuesListSize < 25)
		{
			for (int i = 0; i < 25 - cashValuesListSize; i++)
			{
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
		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Users\\wangheng\\Desktop\\222.xml";
		String mOutFilePath = "C:\\Users\\wangheng\\Desktop\\1119012_1271_25_out.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
//		 
//		Document mNoStdXml = ICBCNewContOutXsl.newInstance(null).getCache().transform(mInXmlDoc);
////		ICBCNewCont newCont = new ICBCNewCont(null);
////		newCont.saleChannel = "8";
////
////		Document mOutXmlDoc = newCont.std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new ICBCNewCont(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");

	}

}