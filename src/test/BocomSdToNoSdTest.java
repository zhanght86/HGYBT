package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.format.ContConfirm;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.icbc.IcbcConf;

public class BocomSdToNoSdTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		IcbcConf bocCom = IcbcConf.newInstance();
		Element eleConf = bocCom.getConf().getRootElement();
		
		
		String mInStr = "G:/YBT_SAVE_XML/JX/1401774_29_102_outstd.xml";
		String mOutFilePath = "G:/YBT_SAVE_XML/JX/Output.xml";
		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes,"GBK");
		
		ContConfirm sNewContAll = new ContConfirm(eleConf);
		Document OutDoc = sNewContAll.std2NoStd(XmlDoc);
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		JdomUtil.print(OutDoc);

	}

}
