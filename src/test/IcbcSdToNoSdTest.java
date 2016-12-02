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
import com.sinosoft.midplat.icbc.format.NewCont;
import com.sinosoft.midplat.icbc.format.Taken;

public class IcbcSdToNoSdTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		IcbcConf icbcConf = IcbcConf.newInstance();
		Element eleConf = icbcConf.getConf().getRootElement();
		//中韩未来悦年金险 221301
		//永裕
		String mInStr = "H:/李路/核心返回报文/1497099_153_0_outSvc.xml";  
		String mOutFilePath = "C:/Users/Administrator/Desktop/测试转换后的报文.xml";
		
		
		
//		String mInStr = "E:/1058001_5_1013_in.xml";  
//		String mOutFilePath = "E:/标准.xml";
		
		//中韩智赢财富两全保险（分红型）A款.xml
//		String mInStr = "E:/中韩智赢财富两全保险（分红型）A款.xml";  
//		String mOutFilePath = "E:/中韩智赢财富两全保险（分红型）A款返回非标准.xml";
		
		
		//中韩智赢财富两全保险（分红型）A款.xml
//		String mInStr = "E:/中韩安赢A.xml";  
//		String mOutFilePath = "E:/中韩安赢A返回非标准.xml";
		
		
//		String mInStr = "D:/test/ZHH/工行/老产品格式调整/30780_13_1_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/工行/老产品格式调整/30780_13_1_outSvc.xmlOut.xml";
				
//		String mInStr = "D:/test/ZHH/工行/借意险/2625_58_1_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/工行/借意险/2625_58_1_outSvc.xmlOut.xml";
		
		
//		String mInStr = "D:/test/ZHH/工行/借意险/31200_381_1013_in.xml";
//		String mOutFilePath = "D:/test/ZHH/工行/借意险/31200_381_1013_in.xmSVc.xml";
		
//		String mInStr = "D:/test/ZHH/工行/保全/31158_323_14_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/工行/保全/31158_323_14_outSvc.xmlOut.xml";
		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		
		//保单格式
		NewCont sNewContAll = new NewCont(eleConf);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
		Document OutDoc = sNewContAll.std2NoStd(XmlDoc);
		
		//犹豫期批单格式
//		NoTaken noTaken = new NoTaken(eleConf);
//		Document OutDoc = noTaken.std2NoStd(XmlDoc);
		
		//退保批单格式
//		Taken taken = new Taken(eleConf);
//		Document OutDoc = taken.std2NoStd(XmlDoc);

//		RePrint rp = new RePrint(eleConf);
//		Document OutDoc = rp.noStd2Std(XmlDoc);
		
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		
		
//		JdomUtil.print(OutDoc);

	}

}
