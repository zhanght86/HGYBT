package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.ccb.format.TBJYXX;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class CCBTBJYXStdToNoStd {
	public static void main(String[] args) throws Exception {

		CcbConf icbcConf = CcbConf.newInstance();
		Element eleConf = icbcConf.getConf().getRootElement();
		
		
		String mInStr = "D:/test/ZHH/退保信息传递OutSvc.xml";
		String mOutFilePath = "D:/test/ZHH/退保信息传递OutSvcOut.xml";
		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		TBJYXX sNewContAll = new TBJYXX(eleConf);
		Document OutDoc = sNewContAll.std2NoStd(XmlDoc);
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		
		
		JdomUtil.print(OutDoc);

	
	
}
}
