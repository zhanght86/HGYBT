package test;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.JDOMParseException;

import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.cache.Load;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.format.NewCont;

public class IcbcMyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			
			IcbcConf icbcConf = IcbcConf.newInstance();
			Element eleConf = icbcConf.getConf().getRootElement();
			
			String mInFilePathName = "D:\\YBT_WORKSPACE\\中融文档\\"+"outStd.xml";
			String mOutFilePathName = "D:\\YBT_WORKSPACE\\中融文档\\"+"outNoStd.xml";
			
			InputStream mIs = new FileInputStream(mInFilePathName);
			Document document = JdomUtil.build(mIs);
			NewCont newcont = new NewCont(eleConf);
			Document mStdNoXml = new Document();
			mStdNoXml = newcont.std2NoStd(document);
			
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
