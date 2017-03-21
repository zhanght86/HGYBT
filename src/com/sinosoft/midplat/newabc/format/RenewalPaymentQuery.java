package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class RenewalPaymentQuery extends XmlSimpFormat {

	private Element header=null;
	private String riskCode=null;
	private String policyNo=null;
	private String sProposalPrtNo=null;
	private String sContno=null;
	
	public RenewalPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdxml) throws Exception{
		cLogger.info("Into RenewalPaymentQuery.noStd2Std()...");
		header=pNoStdxml.getRootElement().getChild("Header");
		riskCode=pNoStdxml.getRootElement().getChild("App").getChild("Req").getChildText("RiskCode");
		policyNo=pNoStdxml.getRootElement().getChild("App").getChild("Req").getChildText("PolicyNo");
		Document mStdXml=RenewalPaymentQueryInXsl.newInstance().getCache().transform(pNoStdxml);
		sProposalPrtNo=mStdXml.getRootElement().getChild("Body").getChildText("ProposalPrtNo");
		sContno=mStdXml.getRootElement().getChild("Body").getChildText("ContNo");
		if(sContno==null||sContno.equals("")){
			//从cont表中查找对应的保单号
			String getContNoSQL = new StringBuilder("select contno from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sContno = new ExeSQL().getOneValue(getContNoSQL);
			if(sContno==null&&sContno.equals("")){
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sProposalPrtNo);
			}else {
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContno);
			}
		}
		cLogger.info("Out RenewalPaymentQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into RenewalPaymentQuery.std2NoStd()...");
		Element tFlag=(Element)XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tDesc=(Element)XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml=RenewalPaymentQueryOutXsl.newInstance().getCache().transform(pStdXml);
		Element mRetCode=mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element mRetMsg=mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(header.getChildText("SerialNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(header.getChildText("InsuSerial"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(header.getChildText("TransDate"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(header.getChildText("TransTime"));
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		mNoStdXml.getRootElement().getChild("Header").getChild("CorpNo").setText(header.getChildText("CorpNo"));
//		mNoStdXml.getRootElement().getChild("Header").getChild("TransCode").setText(header.getChildText("TransCode"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("RiskCode").setText(riskCode);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PolicyNo").setText(policyNo);
		if(tFlag.getText().equals("0")){
			cLogger.info("交易成功====");
			mRetCode.setText("000000");
		}else if(tFlag.getText().equals("1")){
			cLogger.info("交易成功====");
			mRetCode.setText("009999");
		}
		mRetMsg.setText(tDesc.getText());
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RenewalPaymentQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
}
