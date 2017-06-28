package com.sinosoft.midplat.gzbank.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {

	private String mOldDanNo = "";
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		mOldDanNo = pNoStdXml.getRootElement().getChildText("OldDanNo");
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	@SuppressWarnings("unchecked")
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		//重打和新单返回报文基本完全一样，所以直接调用
		Document mNoStdXml =ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		if(pStdXml.getRootElement().getChild("Head").getChildText("Flag").equals("0")){
			mNoStdXml.getRootElement().getChild("OldDanNo").setText(mOldDanNo);
			List<Element> mPrintLists=mNoStdXml.getRootElement().getChildren("PrintList");
        	for (Element mPrintList : mPrintLists) {
        		dealPrint(mPrintList);
			}
		}
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}

	@SuppressWarnings("unchecked")
	private void dealPrint(Element mPrintList){
	     List<Element> mDetailsList=mPrintList.getChild("PrintDetails").getChildren("Details");
		 Element mPrintRecNum=mPrintList.getChild("PrintRecNum");
		 mPrintRecNum.setText(String.valueOf(mDetailsList.size()));
	     System.out.println("Prnt个数："+String.valueOf(mDetailsList.size()));
	}
	
	public static void main(String[] args) throws Exception {
	System.out.println("程序开始…");

	String mInFilePath = "D:/File/task/20170623/gz/transfer_test/176955_33_3_outSvc.xml";
	String mOutFilePath = "D:/File/task/20170623/gz/transfer_test/176955_33_3_outStd.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

	Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
//	Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("成功结束！");
}
}
