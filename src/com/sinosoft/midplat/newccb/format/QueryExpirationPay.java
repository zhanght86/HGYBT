//�������ڸ�����ѯ
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

public class QueryExpirationPay extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sContno = null;
	private String sLv1BrNo = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	
	
	public QueryExpirationPay(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryExpirationPay.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		Document mStdXml = 
			QueryExpirationPayInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out QueryExpirationPay.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryExpirationPay.std2NoStd()...");

//		String mInFilePath2 = "H:/��·/����/P53819113InNoStd.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBk");
//		
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();		
		
		Document mNoStdXml = 
			QueryExpirationPayOutXsl.newInstance().getCache().transform(pStdXml);

		//������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//���˱��ɹ���ʱ�򣬷���TX_BODYʱ������COM_ENTITY�ڵ�
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		//COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);

		//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);		
		
		sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		if(sContno!=null&&!sContno.equals("")){
			//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ���һ�����кţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�10 bak10
			String getLv1BrNoSQL = new StringBuilder("select bak10 from cont where contno = '").append(sContno).append("'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL);
			//APP_ENTITY�ڵ���뽨��һ�����к�
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
		}
			
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText("���׳ɹ���");
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
		}
		
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out QueryExpirationPay.std2NoStd()!");
		return mNoStdXml;
	}

//	public static void main(String[] args) throws Exception {
//		System.out.println("����ʼ��");
//
////		String mInFilePath = "H:/��·/ģ�⽨�б���/ccb_CCB000000000111111_P53819191_134728.xml";
////		String mOutFilePath = "H:/��·/ģ�⽨�б���/P53819191inSvc.xml";
//		String mInFilePath = "H:/��·/����/P53819191InNoStd.xml";
//		String mOutFilePath = "H:/��·/����/P53819191inSvcStd.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs,"UTF-8");
//		mIs.close();
//
////		Document mOutXmlDoc = new QueryExpirationPay(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new QueryExpirationPay(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("�ɹ�������");
//
//	}
		
}