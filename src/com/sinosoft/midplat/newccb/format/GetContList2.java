//���л�ȡ������ϸȡ��(����)
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
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.CcbGetFile;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.midplat.newccb.util.PutContFile;

public class GetContList2 extends XmlSimpFormat
{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private String tPackNum = null;
	private String tBagName = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private Element cBusiConfRoot = null;
	private Element cThisBusiConf = null;
	/**����ķǱ�׼����*/
	private Document cNoStdXml = null;

	public GetContList2(Element pThisConf)
	{
		super(pThisConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into GetContList2.noStd2Std()...");
		cNoStdXml = pNoStdXml;
		// �˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();

		// JdomUtil.print(cTransaction_Header);

		// �������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		oldTxHeader = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode = oldTxHeader.getChildText("SYS_TX_CODE");

		// ��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		// ��ʱ�������з�����������Ϊ���չ�˾��������
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		tBagName = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("AgIns_BtchBag_Nm");
		Document mStdXml = GetContList2InXsl.newInstance().getCache().transform(pNoStdXml);

		// ������ǵ��췢��ѯ���׵Ļ�������ȡ��������TranDate�����ԣ����Ƕ�����������substringһ�°Ѳ�ѯʱ���͸�����
		mStdXml.getRootElement().getChild("Body").getChild("QryStrDate").setText(tBagName.substring(10, 18));

		cLogger.info("Out GetContList.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into GetContList2.std2NoStd()...");
		JdomUtil.print(pStdXml);

		//�Ǳ�׼����
		Document mNoStdXml = GetContList2OutXsl.newInstance().getCache().transform(pStdXml);

		// ������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		// ����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

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

		// ����״̬
		Element mSYS_TX_STATUS = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");
		// ������Ӧ����
		Element mSYS_RESP_DESC = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");

		/* End-��֯���ر���ͷ */
		Element tAPP_ENTITY = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		//��ȡ���ýڵ���Ϣ
		cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");		
		cLogger.info("���ýڵ�element:");
		JdomUtil.print(cThisBusiConf);

		//���ؼ���ǰ·�� 
		String localPath = cThisBusiConf.getChildText("LocalDir");
		//����ȡ�ļ�·��
		String ccblocalPath = cThisBusiConf.getChildText("ccbLocalDir");
		String tTranCode = cThisBusiConf.getChild("funcFlag").getAttributeValue("outcode");
		System.out.println("����ǰ�ļ�·����" + localPath);
		System.out.println("�����룺" + tTranCode);
	
		String fileName = tBagName + ".xml";
		String filePath = localPath + fileName;
		System.out.println("�ļ�����·����" + filePath);
		
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChild("FILE_NAME").setText(fileName);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChild("FILE_PATH").setText(ccblocalPath);
		//ÿ������ȡ���ļ�ֻ��һ�������Ը�FILE_NUM����Ϊ1
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_NUM").setText("1");
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_MODE").setText("0");


		JdomUtil.print(mNoStdXml);

		//��ȡ��������ѭ���ڵ�����
		List<Element> tDetail = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Insu_List").getChildren("Insu_Detail");
		//��֯�������鷵���ļ�
		if (tDetail.size() != 0)
		{
			JdomUtil.print(cNoStdXml);
			boolean isTrue = new PutContFile().isSuccessed(tDetail, filePath, tBagName, mNoStdXml, cNoStdXml);
			if (isTrue = true)
			{
				//��ǰ����ϸ�ܱ���
				Element tCur_Btch_Dtl_TDnum = tAPP_ENTITY.getChild("Cur_Btch_Dtl_TDnum").setText(Integer.toString(tDetail.size()));
				//����������
				Element AgIns_BtchBag_Nm = tAPP_ENTITY.getChild("AgIns_BtchBag_Nm").setText(tBagName);
			}
			else
			{
				mSYS_TX_STATUS.setText("01");
				mSYS_RESP_DESC.setText("�ļ�������");
			}
		}

		JdomUtil.print(mNoStdXml);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").removeChild("Insu_List");
		JdomUtil.print(mNoStdXml);

		cLogger.info("Out GetContList2.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("����ʼ��");

		// String mInFilePath =
		// "H:/��·/ģ�⽨�б���/ccb_CCB000000000111111_P53819184_134728.xml";
		// String mOutFilePath = "H:/��·/ģ�⽨�б���/P53819184inSvc.xml";
		// String mInFilePath = "H:/��·/����/P53819184InNoStd.xml";
		// String mOutFilePath = "H:/��·/����/P53819184inSvcStd.xml";
		String mInFilePath = "E:/1067817_13_38_outSvc.xml";
		String mOutFilePath = "E:/99999.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new GetContList2(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new GetContList2(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}

}