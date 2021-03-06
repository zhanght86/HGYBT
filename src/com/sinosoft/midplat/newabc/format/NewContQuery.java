package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 新单试算结果查询 报文转换
 * @author Liuzk
 *
 */
public class NewContQuery extends XmlSimpFormat {
	private Element header=null;
	

	public NewContQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewContQuery.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = NewContQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewContQuery.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Element ttContNo  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/ContNo");
		Document mNoStdXml = 
			NewContQueryOutXsl.newInstance().getCache().transform(pStdXml);
		//为请求业务报文头信息加入返回码和返回信息.把请求的业务报文头加入到返回报文中返回给银行。
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		//核保结果
		Element  AppResult = mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("AppResult");
		
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("交易成功=========");
		   RetCode.setText("000000");
		   RetMsg.setText("交易成功");
		   if(ttContNo.getText()==null)
			   AppResult.setText("A");
		   else
			   AppResult.setText("0");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
			RetCode.setText("009999");
			RetMsg.setText("交易失败");
			AppResult.setText("1");
		}
		
		cLogger.info(RetCode.getText());
		cLogger.info(RetMsg.getText());
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out NewContQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		String mInFilePath = "C:/Users/Administrator/Desktop/报文/新农行新单试算结果查询请求.xml";
		String mOutFilePath = "C:/Users/Administrator/Desktop/报文/新农行新单试算结果查询返回.xml";

//		String mInFilePath = "C:/Users/Administrator/Desktop/报文/新农行新单试算结果查询返回.xml";
//		String mOutFilePath = "E:\\ccb_20140723Nostd.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

	//	Document mOutXmlDoc = new NewContQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new NewContQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		System.out.println("成功结束！");
	}
}
