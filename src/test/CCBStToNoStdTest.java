package test;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.ccb.format.ContConfirm;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class CCBStToNoStdTest extends XmlSimpFormat{
	public CCBStToNoStdTest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public static void main(String[] args) throws Exception {
		CcbConf icbcConf = CcbConf.newInstance();
		Element eleConf = icbcConf.getConf().getRootElement();
		
		 
//		String mInFilePath = "E:\\951063_103_1_outSvc.xml";
//		String mOutFilePath = "E:\\951063_104_1014_返回.xml";
		String mInFilePath = "F:/1058019_29_1_outSvc.xml";  
		String mOutFilePath = "F:/非标准.xml";

		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInFilePath); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		ContConfirm newCont = new ContConfirm(eleConf);
		Document OutDoc = newCont.std2NoStd(XmlDoc);
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		
		JdomUtil.print(OutDoc);
	}
	
	private static Document dealCashValues (Document InStdDoc){
		Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
		List cashValuesList = CashValuesEle.getChildren(CashValue);
		int cashValuesListSize = cashValuesList.size();
		if(cashValuesListSize<10){
			for(int i=0 ;i<10-cashValuesListSize;i++){
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

}
