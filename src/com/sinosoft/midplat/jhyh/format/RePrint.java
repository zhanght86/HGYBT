package com.sinosoft.midplat.jhyh.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.jhyh.format.ContConfirm;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.jhyh.format.RePrintInXsl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class RePrint extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String sContno = null;
	private String cContPrtNo = null;
	private String sOldContPrtNo = null;
	
	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		
		//20150112 lilu 金华银行生产测试时，银行发核心15位单证时会自动在单证号前面加一个0,补齐16位，
		//因此，我们需要去掉单证号首位的0，要求由金华银行汪金路提出，核心沈世杰确认，涉及到单证的全部需要这样处理
		cContPrtNo=pNoStdXml.getRootElement().getChild("Transaction_Body").getChildText("BkVchNo");
		cContPrtNo=cContPrtNo.substring(1, cContPrtNo.length());
		pNoStdXml.getRootElement().getChild("Transaction_Body").getChild("BkVchNo").setText(cContPrtNo);
		
		
		cTransaction_Header =
		(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
	
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
		
		return mNoStdXml;
	}
	

	/**
	 * 根据传入的报文检查现金价值的个数是否少于显示的行数（10），没有10行，则补充至10行，这是为了控制打印时的显示的完整性
	 * @param InStdDoc
	 * @return
	 */
	private Document dealCashValues (Document InStdDoc){
		JdomUtil.print(InStdDoc);
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<25){
			for(int i=0 ;i<25-cashValuesListSize;i++){
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize+i));
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
	private Document dealCashValues2 (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		cLogger.info("cashValuesListSize："+cashValuesListSize);
		if(cashValuesListSize<33){
			for(int i=0 ;i<33-cashValuesListSize;i++){
				Element cashValueELe = new Element(CashValue);
				Element EndYearEle = new Element("EndYear");
				EndYearEle.setText(String.valueOf(cashValuesListSize+i));
				Element CashEle = new Element("Cash");
				CashEle.setText("-");
				
				cashValueELe.addContent(EndYearEle);
				cashValueELe.addContent(CashEle);
				
				CashValuesEle.addContent(cashValueELe);
			}
		}
//		JdomUtil.print(InStdDoc);
		return InStdDoc;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
//		String mInFilePath = "H:/980868_11_3_outSvc.xml";
////		String mInFilePath = "H:/980871_15_3_outSvc.xml";
//		String mOutFilePath = "H:/1111111111.xml";
//
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);
//		JdomUtil.print(mOutXmlDoc);
//		
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("成功结束！");
//	}
	
}
