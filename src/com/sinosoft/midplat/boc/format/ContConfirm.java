package com.sinosoft.midplat.boc.format;


import java.io.FileInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformException;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 缴费出单转换
 * @author anico
 *
 */
public class ContConfirm extends XmlSimpFormat {
	private Element cMain = null;
	public ContConfirm(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		Document mStdXml = ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		if("0".equals(pStdXml.getRootElement().getChild("Head").getChildText("Flag"))){
			pStdXml =  dealCashValues(pStdXml);
		}
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		Element policyEle=mNoStdXml.getRootElement().getChild("Policy");
		mainEle.getChild("InsuId").setText(cMain.getChildText("InsuId"));
		mainEle.getChild("ZoneNo").setText(cMain.getChildText("ZoneNo"));
		mainEle.getChild("BrNo").setText(cMain.getChildText("BrNo"));
		mainEle.getChild("TellerNo").setText(cMain.getChildText("TellerNo"));
		mainEle.getChild("TransNo").setText(cMain.getChildText("TransNo"));
		mainEle.getChild("TranCode").setText(cMain.getChildText("TranCode"));
		if("0000".equals(mainEle.getChildText("ResultCode"))){
			policyEle.getChild("PrintNo").setText(cMain.getChildText("PrintNo"));
			
		}
		
		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out ContConfirm.std2NoStd()!");
		return mNoStdXml;
	}
	private Document dealCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
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
	public static void main(String[] args) throws Exception {
		String pathXml="E:\\middleware\\user_projects\\domains\\base_domain\\autodeploy\\HGLIFE\\WEB-INF\\msg\\11\\OutStd\\2016\\201611\\20161108\\2016101410002904_1014_113327.xml";
		Document xmlDoc=JdomUtil.build(new FileInputStream(pathXml));
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(xmlDoc);
		System.out.println(JdomUtil.toStringFmt(mNoStdXml));
	}
}
