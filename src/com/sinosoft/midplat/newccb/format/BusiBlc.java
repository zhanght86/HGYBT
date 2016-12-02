package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

/**
 * ���˷����ࣺ��ȫ���ˡ�ҵ����ˡ���֤���� ���ڸý��׶���ͨ���ļ������ж��˵ģ���������Ĳ���ת��
 * 
 * @author
 * 
 */
public class BusiBlc extends XmlSimpFormat
{
	/**�������ʱ��*/
	private String mSYS_RECV_TIME = null;
	/**������Ӧʱ��*/
	private String mSYS_RESP_TIME = null;
	private Document noStdDoc = new Document();
	private String typeCode = null;
	
	/**������ˮ��*/
	private String tranNo = null;
	/**��������*/
	private String tranDate = null;
	private String sFuncflag = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;

	public BusiBlc(Element pThisConf)
	{
		super(pThisConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into BusiBlc.noStd2Std()...");
		noStdDoc = pNoStdXml;

		oldTxHeader = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode = oldTxHeader.getChildText("SYS_TX_CODE");

		// ��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");

		// ��ʱ�������з�����������Ϊ���չ�˾��������
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);

		// �������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		typeCode = pNoStdXml.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE");
		cLogger.info("Out BusiBlc.noStd2Std()!");
		return pNoStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into BusiBlc.std2NoStd()...");
		Document mNoStdXml = null;
		//�����뱣�չ�˾���ˣ������ࣩ
		if (typeCode.equals("P53817103"))
		{
			mNoStdXml = DailyZWBlcOutXsl.newInstance().getCache().transform(pStdXml);
		}
		//�����뱣�չ�˾���ˣ���ȫ�ࣩ
		if (typeCode.equals("P53817104"))
		{
			mNoStdXml = DailyBQBlcOutXsl.newInstance().getCache().transform(pStdXml);
		}
		//�������ж˵�֤��Ϣ
		if (typeCode.equals("P53817105"))
		{
			mNoStdXml = SendDocumentOutXsl.newInstance().getCache().transform(pStdXml);
		}

		// ������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		// ����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

		//����COM_ENTITY�ڵ�
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);


		// COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);

		// COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);

		/* Start-��֯���ر���ͷ */

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0"))
		{ // ���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		else
		{ // ����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");// ����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}

		/* End-��֯���ر���ͷ */
		cLogger.info("Out BusiBlc.std2NoStd()!");
		return mNoStdXml;
	}

	 public static void main(String[] args) throws Exception {
	 System.out.println("����ʼ��");
	
	 String mInFile = "D:\\work\\�к�\\����\\60333_537_1003_in.xml";
	 String mOutFile = "D:\\work\\�к�\\����\\11111.xml";
	
	 Document mInXmlDoc = JdomUtil.build(new FileInputStream(mInFile));

	 JdomUtil.print(mInXmlDoc);
	
	 System.out.println("-----------------------------------------------------------");
	 Element w = new Element("e");
//	 Document mOutXmlDoc = new BusiBlc(w).noStd2Std(mInXmlDoc);
	  Document mOutXmlDoc = new BusiBlc(w).std2NoStd(mInXmlDoc);
	
	 JdomUtil.print(mOutXmlDoc);
	 JdomUtil.output(mOutXmlDoc, new FileOutputStream(mOutFile));
	
	 System.out.println("�ɹ�������");
	 }
}