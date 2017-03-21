package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;

import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class Cancel extends XmlSimpFormat {
	private Element header=null;
	private String OrgSerialNo=null;
	public Cancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Cancel.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Element eOrgSerialNo=(Element) XPath.selectSingleNode(pNoStdXml.getRootElement(), "/ABCB2I/App/Req/OrgSerialNo");
		Element eContNo=(Element) XPath.selectSingleNode(pNoStdXml.getRootElement(), "/ABCB2I/App/Req/PolicyNo");
		String cTransDate = pNoStdXml.getRootElement().getChild("Header").getChildText("TransDate");
		String mTranCom = pNoStdXml.getRootElement().getChild("Head").getChildText(TranCom);
		OrgSerialNo=eOrgSerialNo.getText();
		String sqlStr = "select proposalprtno from tranlog where trancom='"+mTranCom+"' and rcode='0' and funcflag='1014' and contno='"+eContNo.getText()+"' and MakeDate='"+cTransDate+"' order by logno desc";
		SSRS ssrs0=new ExeSQL().execSQL(sqlStr);
		Document mStdXml = 
			CancelInXsl.newInstance().getCache().transform(pNoStdXml);
		mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(ssrs0.GetText(1, 1));
		//测试
		System.out.println(JdomUtil.toStringFmt(mStdXml));
	    cLogger.info("Out Cancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Cancel.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml = 
			CancelOutXsl.newInstance().getCache().transform(pStdXml);
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
		mNoStdXml.getRootElement().addContent(header);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out Cancel.std2NoStd()!");
		return mNoStdXml;
	}
}