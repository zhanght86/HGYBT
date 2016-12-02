package test;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.format.NoRealBlc;

public class IcbcNoRealTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			
			IcbcConf icbcConf = IcbcConf.newInstance();
			Element eleConf = icbcConf.getConf().getRootElement();
			
			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/非实时/2004_2_23_outSvc.xml";
			String mOutFilePathName = "D:/YBT_SAVE_XML/ZHH/非实时/2004_2_23_outSvc.xmlOutToBank.xml";
			
			InputStream mIs = new FileInputStream(mInFilePathName);

			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
			
			NoRealBlc newcont = new NoRealBlc(eleConf);
			Document  mStdNoXml = newcont.std2NoStd(XmlDoc);
			
			OutputStream mFos = new FileOutputStream(mOutFilePathName);
			JdomUtil.output(mStdNoXml, mFos);
			mFos.flush();
			mFos.close();
//			JdomUtil.print(mStdNoXml);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
