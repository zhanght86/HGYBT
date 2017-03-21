package com.sinosoft.midplat.boc.format;


import java.io.FileInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 缴费出单转换
 * @author anico
 *
 */
public class ContConfirm extends XmlSimpFormat {
	String InsuId;//保险公司代码
	String ZoneNo;//地区代码
	String BrNo;//网点代码
	String TellerNo;//柜员代码
	String TransNo;//交易流水号
	String TranCode;//交易码
	String PrintNo;//保单印刷号
	public ContConfirm(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		cLogger.info("第三方请求报文:"+JdomUtil.toStringFmt(pNoStdXml));
		Element mainEle=pNoStdXml.getRootElement().getChild("Main");
		InsuId=mainEle.getChildText("InsuId");
		ZoneNo=mainEle.getChildText("ZoneNo");
		BrNo=mainEle.getChildText("BrNo");
		TellerNo=mainEle.getChildText("TellerNo");
		TransNo=mainEle.getChildText("TransNo");
		TranCode=mainEle.getChildText("TranCode");
		PrintNo=mainEle.getChildText("PrintNo");
		Document mStdXml = ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		Element policyEle=mNoStdXml.getRootElement().getChild("Policy");
		mainEle.getChild("InsuId").setText(InsuId);
		mainEle.getChild("ZoneNo").setText(ZoneNo);
		mainEle.getChild("BrNo").setText(BrNo);
		mainEle.getChild("TellerNo").setText(TellerNo);
		mainEle.getChild("TransNo").setText(TransNo);
		mainEle.getChild("TranCode").setText(TranCode);
		if("0000".equals(mainEle.getChildText("ResultCode"))){
			policyEle.getChild("PrintNo").setText(PrintNo);
			dealRowCount(mNoStdXml);
		}
		
		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out ContConfirm.std2NoStd()!");
		return mNoStdXml;
	}
	
	private void dealRowCount(Document mNoStdXml){
		@SuppressWarnings("unchecked")
		List<Element> pageContentList=mNoStdXml.getRootElement().getChild("Print").getChild("Paper").getChildren("PageContent");
		for (Element e : pageContentList) {
			int size=e.getChild("Details").getChildren("Row").size();
			System.out.println(size);
			e.getChild("RowCount").setText(String.valueOf(size));
		}
	}
	
	public static void main(String[] args) throws Exception {
		String pathXml="E:\\middleware\\user_projects\\domains\\base_domain\\autodeploy\\HGLIFE\\WEB-INF\\msg\\11\\OutStd\\2016\\201611\\20161108\\2016101410002904_1014_113327.xml";
		Document xmlDoc=JdomUtil.build(new FileInputStream(pathXml));
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(xmlDoc);
		System.out.println(JdomUtil.toStringFmt(mNoStdXml));
	}
}
