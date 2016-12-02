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

		// �������з���ˮ�ţ����ر�����Ҫ lilu 20141127
		tranno = pNoStdXml.getRootElement().getChild("TXLifeRequest").getChildText("TransRefGUID");
		// �������������������������������֯���ر��ĺͱ�����ʽ lilu 20141127
		saleChannel = mStdXml.getRootElement().getChild("Body").getChildText("SaleChannel");

		// 20150123lilu
		// ���ӹ���У�飺�����������ն�ֻ֧�ֱ����㱣�ѣ������ӮC�Ǳ����㱣����԰���������һ�ݱ���10000Ԫ�������ֻ�ᷢ��������
		// 20150203lilu �����������ն˵���ӮC ����ְҵ����
		// 20150203lilu ��̨��������ӮC Ҳ����������һ�ݱ���10000������ֻȡ����
		// 20150226lilu �����Ҫ����ӮC ��̨֧�ַ����ͱ�����ѡһ����Ϊ���й�̨¼���˷���
		String riskcode = mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("RiskCode");
		if (!"0".equals(saleChannel) && "231204".equals(riskcode))
		{
			// if("231204".equals(riskcode)){
			String mult = mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
			if ("".equals(mult) || mult == null)
			{// 20150226 ������ӮCδ�������Ĵ�����ʾ����service�������
				mult = "0";
				mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("0");// δ������������Ϊ0
			}
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText(String.valueOf(Integer.parseInt(mult) * 1000000));
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("1");
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("JobCode").setText("3010101");
			mStdXml.getRootElement().getChild("Body").getChild("Insured").getChild("JobCode").setText("3010101");
		}
		if ("221201".equals(riskcode))
		{// 20150204lilu �������������̶������ݻ����Ǳ����㱣�ѣ����в���ѣ�������Ĭ��ֵ0�����Ը�����һ�����ַ���
			mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText("");
		}
		// 20150203lilu ��̨������ְҵ����д��
		if ("0".equals(saleChannel))
		{
			if ("231204".equals(riskcode))
			{
				// String
				// mult=mStdXml.getRootElement().getChild("Body").getChild("Risk").getChildText("Mult");
				// if("".equals(mult)||mult==null){//20150226 ������ӮC
				// ��̨¼�벻�˷������������1��ȡ����
				// mult="1";
				mStdXml.getRootElement().getChild("Body").getChild("Risk").getChild("Mult").setText("1");// 20150227����¼�����ˣ���ô����Ϊ1���Ա���Ϊ׼
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
		cLogger.info("Into ICBCNewContInput.std2NoStd()...==�������㷵��");

		// �������ִ���ѡ������Ʒ������ӡ��ʽģ�� 20150101
		Element tRiskCode = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");

		// saleChannel="1";//���ز��Լ���
		if (!saleChannel.equals("0"))
		{// �ǹ�̨����
			tXslName = "NewContOutEB";
		}
		if (tRiskCode != null)
		{
			if ("221203".equals(tRiskCode.getValue()))
			{// �����Ǹ����� 20141120
				tXslName = "NewContOutEtraRisk";
				pStdXml = dealCashValues(pStdXml);
			}
			if ("121504".equals(tRiskCode.getValue()))
			{// �к����������ش󼲲����� 
				tXslName = "NewContOutIllRisk";
				pStdXml = dealCashValues(pStdXml);
			}
		}
		System.out.println("��ʽ��:" + tXslName);

		Document mNoStdXml = ICBCNewContOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
		//�ǹ�̨���㣬���ý�����
		if(!saleChannel.equals("0"))
		{
			mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransType").setText("1012");
			mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransType").setAttribute("tc", "1012");
		}
		

		// ���������ˮ�� lilu 20141127
		mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("TransRefGUID").setText(tranno);

		JdomUtil.print(mNoStdXml);

		cLogger.info("Out ICBCNewContInput.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * ���ݴ���ı��ļ���ֽ��ֵ�ĸ����Ƿ�������ʾ��������10����û��10�У��򲹳���10�У�����Ϊ�˿��ƴ�ӡʱ����ʾ��������
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
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize��" + cashValuesListSize);
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
		System.out.println("����ʼ��");

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

		System.out.println("�ɹ�������");

	}

}