//���в�ѯ��������
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryContDetail extends XmlSimpFormat
{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sIns_Co_ID = null;
	private String sysTxCode = null;

	private String sContno = null;
	private String sLv1BrNo = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;

	public QueryContDetail(Element pThisConf)
	{
		super(pThisConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into QueryContDetail.noStd2Std()...");

		// �˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();

		// �������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		oldTxHeader = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode = oldTxHeader.getChildText("SYS_TX_CODE");

		// ��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		// ��ʱ�������з�����������Ϊ���չ�˾��������
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);

		sIns_Co_ID = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("Ins_Co_ID");

		// JdomUtil.print(cTransaction_Header);

		Document mStdXml = QueryContDetailInXsl.newInstance().getCache().transform(pNoStdXml);

		cLogger.info("Out QueryContDetail.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into QueryContDetail.std2NoStd()...");

		

		Document mNoStdXml = QueryContDetailOutXsl.newInstance().getCache().transform(pStdXml);

		// ������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		// ����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

		//����ComEntity�ڵ�ֵ
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);

		// COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);

		// COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);

		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_ID").setText(sIns_Co_ID);

		/* Start-��֯���ر���ͷ */

		

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0"))
		{ // ���׳ɹ�
			sContno = pStdXml.getRootElement().getChild("Body").getChildText("Contno");

			// System.out.println("================="+sContno);

			if (sContno != null && !sContno.equals(""))
			{
				// ��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ���һ�����кţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�10 bak10
				String getLv1BrNoSQL = new StringBuilder("select bak10 from cont where contno = '").append(sContno).append("' and state='2'").toString();
				sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL);
				// APP_ENTITY�ڵ���뽨��һ�����к�
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
			}
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

		cLogger.info("Out QueryContDetail.std2NoStd()!");
		return mNoStdXml;
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("����ʼ��");
	//
	// String mInFilePath =
	// "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/1053951_15_36_outSvc.xml";
	// String mOutFilePath = "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/��ѯ��������out.xml";
	//
	// InputStream mIs = new FileInputStream(mInFilePath);
	// Document mInXmlDoc = JdomUtil.build(mIs);
	// mIs.close();
	//
	// Document mOutXmlDoc = new QueryContDetail(null).std2NoStd(mInXmlDoc);
	// // Document mOutXmlDoc = new QueryContDetail(null).noStd2Std(mInXmlDoc);
	//
	// JdomUtil.print(mOutXmlDoc);
	//
	// OutputStream mOs = new FileOutputStream(mOutFilePath);
	// JdomUtil.output(mOutXmlDoc, mOs);
	// mOs.flush();
	// mOs.close();
	//
	// System.out.println("�ɹ�������");
	// }

}
