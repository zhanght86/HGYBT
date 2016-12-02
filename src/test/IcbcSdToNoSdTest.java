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
		//�к�δ��������� 221301
		//��ԣ
		String mInStr = "H:/��·/���ķ��ر���/1497099_153_0_outSvc.xml";  
		String mOutFilePath = "C:/Users/Administrator/Desktop/����ת����ı���.xml";
		
		
		
//		String mInStr = "E:/1058001_5_1013_in.xml";  
//		String mOutFilePath = "E:/��׼.xml";
		
		//�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��.xml
//		String mInStr = "E:/�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��.xml";  
//		String mOutFilePath = "E:/�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��طǱ�׼.xml";
		
		
		//�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��.xml
//		String mInStr = "E:/�к���ӮA.xml";  
//		String mOutFilePath = "E:/�к���ӮA���طǱ�׼.xml";
		
		
//		String mInStr = "D:/test/ZHH/����/�ϲ�Ʒ��ʽ����/30780_13_1_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/����/�ϲ�Ʒ��ʽ����/30780_13_1_outSvc.xmlOut.xml";
				
//		String mInStr = "D:/test/ZHH/����/������/2625_58_1_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/����/������/2625_58_1_outSvc.xmlOut.xml";
		
		
//		String mInStr = "D:/test/ZHH/����/������/31200_381_1013_in.xml";
//		String mOutFilePath = "D:/test/ZHH/����/������/31200_381_1013_in.xmSVc.xml";
		
//		String mInStr = "D:/test/ZHH/����/��ȫ/31158_323_14_outSvc.xml";
//		String mOutFilePath = "D:/test/ZHH/����/��ȫ/31158_323_14_outSvc.xmlOut.xml";
		
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(mInStr); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		
		//������ʽ
		NewCont sNewContAll = new NewCont(eleConf);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
//		Document OutDoc = sNewContAll.noStd2Std(XmlDoc);
		Document OutDoc = sNewContAll.std2NoStd(XmlDoc);
		
		//��ԥ��������ʽ
//		NoTaken noTaken = new NoTaken(eleConf);
//		Document OutDoc = noTaken.std2NoStd(XmlDoc);
		
		//�˱�������ʽ
//		Taken taken = new Taken(eleConf);
//		Document OutDoc = taken.std2NoStd(XmlDoc);

//		RePrint rp = new RePrint(eleConf);
//		Document OutDoc = rp.noStd2Std(XmlDoc);
		
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(OutDoc, mFos);
		
		
//		JdomUtil.print(OutDoc);

	}

}
