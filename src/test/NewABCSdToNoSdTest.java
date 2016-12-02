package test;

import java.io.FileInputStream;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.format.NewCont;
import com.sinosoft.midplat.newabc.format.ContConfirm;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class NewABCSdToNoSdTest {
	
	public static void main(String[] args) throws Exception {

		NewAbcConf abcConf = NewAbcConf.newInstance();
		Element eleConf = abcConf.getConf().getRootElement();
		
//		String mInStr = "E:/������Ŀ/�к���Ŀ/�к�/ũ���½ӿ�/ABC/ABC/abc_prmcalck.xml";
//		String mOutFilePath = "E:/������Ŀ/�к���Ŀ/�к�/ũ���½ӿ�/ABC/ABC/abc_prmcalck_���㷵��.xml";
		String mInStr = "C:/Users/Administrator/Desktop/1611409_80_1_outSvc.xml";
		String mOutFilePath = "C:/Users/Administrator/Desktop/abc.xml";
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
//		NewCont sNewContAll = new NewCont(eleConf);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
		ContConfirm c=new ContConfirm(eleConf);
		Document OutDoc = c.std2NoStd(XmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);

	
	}
}
