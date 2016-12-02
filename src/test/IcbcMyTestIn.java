package test;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.format.ICBCNewCont;

public class IcbcMyTestIn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			
			IcbcConf icbcConf = IcbcConf.newInstance();
			Element eleConf = icbcConf.getConf().getRootElement();
			
			String mInFilePathName = "C:\\Users\\Administrator\\Desktop\\67311_3026_1012_in.xml";
			String mOutFilePathName = "C:\\Users\\Administrator\\Desktop\\67311_3026_1012_out.xml";
			
			InputStream mIs = new FileInputStream(mInFilePathName);
			Document document = JdomUtil.build(mIs);
			ICBCNewCont newcont = new ICBCNewCont(eleConf);
			Document mStdNoXml = new Document();
			mStdNoXml = newcont.noStd2Std(document);
			
			OutputStream mFos = new FileOutputStream(mOutFilePathName);
			JdomUtil.output(mStdNoXml, mFos);
			mFos.flush();
			mFos.close();
			JdomUtil.print(mStdNoXml);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
