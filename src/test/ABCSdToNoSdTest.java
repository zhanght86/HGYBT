package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.abc.AbcConf;
import com.sinosoft.midplat.abc.format.ContConfirm;
import com.sinosoft.midplat.abc.format.NewCont;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class ABCSdToNoSdTest {
	
	public static void main(String[] args) throws Exception {

		AbcConf abcConf = AbcConf.newInstance();
		Element eleConf = abcConf.getConf().getRootElement();
		
		//借贷险
//		String mInStr = "D:/test/ZHH/农行/借贷险/25299_123_0_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/农行/借贷险/25299_123_0_outSvc.xmlOut.xml";
		
		String mInStr = "D:/test/ZHH/农行/借贷险/2625_58_1_outSvc.xml";
		String mOutFilePath = "D:/test/ZHH/农行/借贷险/2625_58_1_outSvc.xmlOut.xml";
		
		//普通险种
//		String mInStr = "D:/test/ZHH/农行/29469_555_1_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/农行/29469_555_1_outSvc.xmlOut.xml";
		
//		String mInStr = "D:/test/ZHH/农行/25971_7_1012_in.xml";
//		String mOutFilePath = "D:/test/ZHH/农行/25971_7_1012_in.xmlInSvc.xml";
		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		ContConfirm sNewContAll = new ContConfirm(eleConf);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
		Document OutDoc = sNewContAll.std2NoStd(XmlDoc);

//		RePrint rp = new RePrint(eleConf);
//		Document OutDoc = rp.noStd2Std(XmlDoc);
		
//		NewCont newCont = new NewCont(eleConf);
//		Document OutDoc = newCont.noStd2Std(XmlDoc);
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		
		
//		JdomUtil.print(OutDoc);

	
	}
}
