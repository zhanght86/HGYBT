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

public class ContConfirm extends XmlSimpFormat{
	public ContConfirm(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}
	@Override
	public Document noStd2Std(Document pInNoStd) throws Exception {
		cLogger.info("Into PolicyContTrial.noStd2Std()...");
		Document mStdXml=ContConfirmInXsl.newInstance().getCache().transform(pInNoStd);
		JdomUtil.print(mStdXml);
		cLogger.info("Out PolicyContTrial.noStd2Std()!");
		return mStdXml;
	}

	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
        Document mNoStdXml =ContConfirmOutXsl.newInstance().getCache().transform(pOutStd);
        JdomUtil.print(mNoStdXml);
        String tFlag=pOutStd.getRootElement().getChild(Head).getChildText(Flag);
        if("0".equals(tFlag)){
        	@SuppressWarnings("unchecked")
			List<Element> mPrintLists=mNoStdXml.getRootElement().getChildren("PrintList");
        	for (Element mPrintList : mPrintLists) {
        		dealPrint(mPrintList);
			}
        }
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
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

		String mInFilePath = "D:/File/task/20170620/gz/transfer_test/9000103out_Std.xml";
		String mOutFilePath = "D:/File/task/20170620/gz/transfer_test/9000103out_noStd.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);
		Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
	
}
