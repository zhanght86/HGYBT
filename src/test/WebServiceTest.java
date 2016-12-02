package test;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;

public class WebServiceTest implements XmlTag {
	public static void main(String[] args) {

		try {
			String tPath = "F:/MyEclipse/workspace/HGLIFE/src/test/doc/16_inSvc.xml";
			InputStream mIs = new FileInputStream(tPath);
			Document mInXmlDoc = JdomUtil.build(mIs);
			JdomUtil.print(mInXmlDoc);
			Document d = new CallWebsvcAtomSvc("16").call(mInXmlDoc);
		    JdomUtil.print(d);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		System.out.println("Out Get_PayRetThread.run()!");
	}
	}

}
