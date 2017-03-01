package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class RePrint extends XmlSimpFormat {
	private Element header=null;
	private String  riskCode=null;
	private String  sProposalprtno=null;
	private String  sContno=null;
	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		riskCode=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("InsuCode");
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//测试
		JdomUtil.print(mStdXml);
		//农行传ProposalContNo，方从Cont中查出ProposalPrtNo,ContNo
		sProposalprtno=mStdXml.getRootElement().getChild("Body").getChildText("ProposalPrtNo");
		String getContNoSQL = new StringBuilder("select contno from cont where proposalprtno = '").append(sProposalprtno).append("'").toString();
		sContno = new ExeSQL().getOneValue(getContNoSQL);
		mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContno);
		JdomUtil.print(mStdXml);

		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		Element rr = new Element("cThisConf");
		ContConfirm contConfirm=new ContConfirm(rr);
		contConfirm.setHeader(header);
		Document mNoStdXml = contConfirm.std2NoStd(pStdXml);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
}
