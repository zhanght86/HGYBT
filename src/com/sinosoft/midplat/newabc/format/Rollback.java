package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Rollback extends XmlSimpFormat {
	private Element header=null;
	private String OrgSerialNo=null;
    private String OrgTransDate=null;
    private String TransCode=null;
	public Rollback(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Element eOrgSerialNo=(Element) XPath.selectSingleNode(pNoStdXml.getRootElement(), "/ABCB2I/App/Req/OrgSerialNo");
		Element eOrgTransDate=(Element) XPath.selectSingleNode(pNoStdXml.getRootElement(), "/ABCB2I/App/Req/OrgTransDate");
		Element eTransCode=(Element) XPath.selectSingleNode(pNoStdXml.getRootElement(), "/ABCB2I/App/Req/TransCode");
		OrgSerialNo=eOrgSerialNo.getText();
		OrgTransDate=eOrgTransDate.getText();
		TransCode=eTransCode.getText();
		cLogger.info("Into Rollback.noStd2Std()...");
		Document mStdXml = 
			RollbackInXsl.newInstance().getCache().transform(pNoStdXml);
        
		cLogger.info("Out Rollback.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Rollback.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml = 
			RollbackOutXsl.newInstance().getCache().transform(pStdXml);
		Element  RetCode=new Element("RetCode");
		Element  RetMsg = new Element("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("交易成功=========");
		   RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		header.addContent(RetCode);
		header.addContent(RetMsg);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("OrgSerialNo").setText(OrgSerialNo);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("OrgTransDate").setText(OrgTransDate);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("TransCode").setText(TransCode);
		mNoStdXml.getRootElement().addContent(header);
		cLogger.info("Out Rollback.std2NoStd()!");
		return mNoStdXml;
	}
}
