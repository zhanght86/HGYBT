package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class RePrint extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String sContno = null;
	private String sOldContPrtNo = null;
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		
//		cTransaction_Header =
//			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		sContno=mStdXml.getRootElement().getChild("Body").getChildText("ContNo");
		if(sContno!=null&&!sContno.equals("")){
			//从tranlog表中查找对应的旧单证号
			String getOldContPrtNoSQL = new StringBuilder("select otherno from tranlog where contno = '").append(sContno).append("'").
			append(" and funcflag= '").append("1014'").toString();
			sOldContPrtNo = new ExeSQL().getOneValue(getOldContPrtNoSQL);
			cLogger.info("旧的单证号："+sOldContPrtNo);
			mStdXml.getRootElement().getChild("Body").getChild("OldContPrtNo").setText(sOldContPrtNo);
		}
		
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");

		//重打和新单返回报文基本完全一样，所以直接调用
		Element rr = new Element("cThisConf");
		ContConfirm mContConfirm = new ContConfirm(rr);
		mContConfirm.setHeader(cTransaction_Header);
		Document mNoStdXml = mContConfirm.std2NoStd(pStdXml);
//		Document mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml);

		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
	System.out.println("程序开始…");

	String mInFilePath = "F:\\959391_21_1011_in.xml";
	String mOutFilePath = "F:\\33333.xml";

//	String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//	String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

//	Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("成功结束！");
}
}
