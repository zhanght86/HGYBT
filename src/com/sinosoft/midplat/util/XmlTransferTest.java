package com.sinosoft.midplat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;

import com.sinosoft.midplat.common.JdomUtil;

public class XmlTransferTest {

	public static void transferXml(String inXmlSource, String xslSource,
			String outXml) throws TransformerException {

		TransformerFactory tFactory = TransformerFactory.newInstance();

		StreamSource source = new StreamSource(new File(xslSource));

		Transformer tx = tFactory.newTransformer(source);

		Properties properties = tx.getOutputProperties();
		properties.setProperty(OutputKeys.ENCODING, "GBK");
		properties.setProperty(OutputKeys.METHOD, "xml");
		tx.setOutputProperties(properties);

		StreamSource xmlSource = new StreamSource(new File(inXmlSource));
		File targetFile = new File(outXml);
		StreamResult result = new StreamResult(targetFile);

		tx.transform(xmlSource, result);

	}

	public static void main(String[] args) throws TransformerException,
			FileNotFoundException {
		String inXmlSource = "F:/xml/ABC/";
		inXmlSource = "D:/File/task/20170628/newccb/transfer_test/";
//		inXmlSource="D:/File/task/20170628/newabc/ybt_test/";
//		inXmlSource="D:/File/task/20170628/newccb/test_case/model2/";
		String filename="";//XML�ļ�
//		filename="01in_noStd.xml";
//		filename="01out_Std.xml";
//		filename="23in_noStd.xml";
//		filename="23out_Std.xml";
		//�й�����
//		filename="1001in_noStd.xml";
//		filename="1001out_Std.xml";
//		filename="1002in_noStd.xml";
//		filename="1002out_Std.xml";
//		filename="1003out_Std.xml";
//		filename="1004in_noStd.xml";
//		filename="1004out_Std.xml";
//		filename="1005in_noStd.xml";
//		filename="1005out_Std.xml";
//		filename="1006in_noStd.xml";
//		filename="1006out_Std.xml";
//		filename="1007in_noStd.xml";
//		filename="1007out_Std.xml";
//		filename="1009in_noStd.xml";
		//��������
//		filename="9000102in_noStd.xml";//�������б�����������
//		filename="9000102out_Std.xml";//���ı�������Ӧ��
//		filename="9000103in_noStd.xml";//�������нɷѳ�������
//		filename="9000103out_Std.xml";//���Ľɷѳ���Ӧ��
//		filename="9000801in_noStd.xml";
//		filename="9000801out_Std.xml";
//		filename="9000901in_noStd.xml";
//		filename="9000901out_Std.xml";
		//��������
//		filename="P53819113in_noStd.xml";
//		filename="P53819113out_Std.xml";
//		filename="P53819152in_noStd.xml";
//		filename="P53819152out_Std.xml";
//		filename="P53819184in_noStd.xml";
//		filename="P53819184out_Std.xml";
//		filename="P53819142in_noStd.xml";
//		filename="P53819142out_Std.xml";
//		filename="P53819182in_noStd.xml";
//		filename="P53819182out_Std.xml";
//		filename="P538191E4in_noStd.xml";
//		filename="P538191E4out_Std.xml";
//		filename="P53818102in_noStd.xml";
//		filename="AL03100192017011101_RESULT.XML";
//		filename="AL13100192017011101_RESULT.XML";
//		filename="P53818105in_noStd.xml";
//		filename="P53818103in_noStd.xml";
//		filename="50022_2673_1012_in.xml";
		filename="178434_21_36_outSvc.xml";
		//ũҵ����
//		filename="1002in_noStd.xml";
//		filename="1002out_Std.xml";
//		filename="1004in_noStd.xml";
//		filename="1004out_Std_0.xml";
//		filename="1004out_Std_3_12.xml";
//		filename="166212_396_1_outSvc.xml";
//		filename="1010in_noStd.xml";
//		filename="1010out_Std.xml";
//		filename="1012in_noStd.xml";
//		filename="1012out_Std.xml";
//		filename="1018in_noStd.xml";
//		filename="1018out_Std.xml";
//		filename="1013in_noStd.xml";
//		filename="1013out_Std.xml";
//		filename="1009in_noStd.xml";
//		filename="1009out_Std.xml";
//		filename="1021in_noStd.xml";
//		filename="1021out_Std.xml";
		String XslPath= "";//XSL�ļ�
		//�й�����
//		XslPath = "NewContIn.xsl";
//		XslPath="NewContOut.xsl";
//		XslPath="ContConfirmIn.xsl";
//		XslPath="ContConfirmOut.xsl";
//		XslPath="RePrintIn.xsl";
//		XslPath="RePrintOut-y1.xsl";
//		XslPath="WriteOffIn.xsl";
//		XslPath="WriteOffOut.xsl";
//		XslPath="PolicyCancelConfirmIn.xsl";
//		XslPath="RenewalPayQueryIn.xsl";
//		XslPath="RenewalPayQueryOut.xsl";
//		XslPath="RenewalPayIn.xsl";
//		XslPath="RenewalPayOut.xsl";
//		XslPath="PolicyCancelTrialIn.xsl";
//		XslPath="PolicyCancelTrialOut.xsl";
//		XslPath="PolicyCancelConfirmIn.xsl";
//		XslPath="PolicyCancelConfirmOut.xsl";
		//��������
		XslPath="PolicyContTrialIn.xsl";//ת��Ϊ����������XSL
//		XslPath="PolicyContTrialOut.xsl";
//		XslPath="ContConfirmIn.xsl";
//		XslPath="ContConfirmOut.xsl";//ת��Ϊ��������Ӧ����XSL
//		XslPath="RePrintIn.xsl";
//		XslPath="PolicyWriteOffIn.xsl";
//		XslPath="PolicyWriteOffOut.xsl";
		//��������
//		XslPath="NewContIn.xsl";
//		XslPath="NewContOut.xsl";
//		XslPath="ContConfirmIn.xsl";
//		XslPath="ContConfirmOut.xsl";
//		XslPath="RePrintIn.xsl";
//		XslPath="RePrintOut.xsl";
//		XslPath="WriteOffIn.xsl";
//		XslPath="WriteOffOut.xsl";
//		XslPath="PrintContOut.xsl";
//		XslPath="UpdateServiceStatusIn.xsl";
//		XslPath="UpdateServiceStatusOut.xsl";
//		XslPath="BatQueryIn.xsl";
//		XslPath="BatchSendDiskIn.xsl";
//		XslPath="BatRequestIn.xsl";
//		XslPath="BatResponseIn.xsl";
		XslPath="QueryContDetailOut.xsl";
		//ũҵ����
//		XslPath="NewContIn.xsl";
//		XslPath="NewContOut.xsl";
//		XslPath="ContConfirmIn.xsl";
//		XslPath="ContConfirmOut.xsl";
//		XslPath="RePrintIn.xsl";
//		XslPath="CancelIn.xsl";
//		XslPath="CancelOut.xsl";
//		XslPath="SecureQueryIn.xsl";
//		XslPath="SecureQueryOut.xsl";
//		XslPath="SecureApplyIn.xsl";
//		XslPath="SecureApplyOut.xsl";
//		XslPath="RollbackIn.xsl";
//		XslPath="RollbackOut.xsl";
//		XslPath="PolDetailQueryIn.xsl";
		inXmlSource=inXmlSource+filename;
//		String filepath = "F:/MyEclipse/workspace/HGLIFE/src/com/sinosoft/midplat/newabc/format/";
		String filepath="D:/Software/MyEclipse/workspace/HGLIFE/src/com/sinosoft/midplat/newccb/format/";
//		String filepath = "D:/task/20170628/newccb/transfer_test/NewCBCSetBatXmltoTxt.xsl";
		String xslSource = filepath + XslPath;
//		String outXml = "D:/File/task/20170213/newabc/ybt_test/1021in_Std.xml";//32
//		String outXml = "D:/task/20170628/newccb/transfer_test/0000000001_F020010020090729_00001.txt";//37
//		String outXml = "D:/File/task/20170628/newabc/transfer_test/1004out_noStd_0.xml";
		String outXml = "D:/File/task/20170628/newccb/transfer_test/178434_24_1040_out.xml";
		transferXml(inXmlSource, xslSource, outXml);
		InputStream fis = new FileInputStream(new File(outXml));
		Document mLogDocument = JdomUtil.build(fis);
		JdomUtil.print(mLogDocument);
	}

}
