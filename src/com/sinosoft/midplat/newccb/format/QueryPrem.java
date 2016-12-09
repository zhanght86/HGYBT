//���в�ѯ�ɷ���Ϣ
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

public class QueryPrem extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sContno = null;
	private String sProposalPrtNo = null;
	private String sLv1BrNo = null;
	private String sAgInsPkgID = null;
	private String sysTxCode = null;
	private String sPayedTimes  = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private Document cInNoStdXml = null;
	
	
	public QueryPrem(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPrem.noStd2Std()...");
		cInNoStdXml=pNoStdXml;
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
			QueryPremInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		sProposalPrtNo=mStdXml.getRootElement().getChild("Body").getChildText("ProposalPrtNo");
		sContno=mStdXml.getRootElement().getChild("Body").getChildText("ContNo");
		if(sContno==null||sContno.equals("")){
			//��cont���в��Ҷ�Ӧ�ı�����
			String getContNoSQL = new StringBuilder("select contno from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sContno = new ExeSQL().getOneValue(getContNoSQL);
			if(sContno==null&&sContno.equals("")){
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sProposalPrtNo);
			}else {
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContno);
			}
		}
		
		cLogger.info("Out QueryPrem.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryPrem.std2NoStd()...");
		
//		String mInFilePath2 = "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/1053981_1_1033_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2);
//		cInNoStdXml=mInXmlDoc;
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();
		
		
			
		Document mNoStdXml = 
			QueryPremOutXsl.newInstance().getCache().transform(pStdXml);

		//������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
		

		//COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			
			sPayedTimes=pStdXml.getRootElement().getChild("Body").getChildText("PayedTimes");
			//�ѽ���������>=1����ʾ�����ڽɷѲ�ѯ������ΪʵʱͶ���ɷѲ�ѯ
			if(Integer.parseInt(sPayedTimes)>=1){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("14");
			}else{
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("11");
			}
		
		
		sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		sProposalPrtNo=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_BillNo");
		
		if(sContno!=null&&!sContno.equals("")){
			//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ���һ�����кţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�10 bak10
			String getLv1BrNoSQL = new StringBuilder("select bak10 from cont where contno = '").append(sContno).append("'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL);
			//APP_ENTITY�ڵ���뽨��һ�����к�
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
			
			//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ��д������ײͱ�ţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�9 bak9
			String getAgInsPkgIDSQL = new StringBuilder("select bak9 from cont where contno = '").append(sContno).append("'").toString();
			sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL);
			//APP_ENTITY�ڵ���뽨�д������ײͱ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
		} else if(sProposalPrtNo!=null&&!sProposalPrtNo.equals("")){
			//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ���һ�����кţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�10 bak10
			String getLv1BrNoSQL2 = new StringBuilder("select bak10 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL2);
			//APP_ENTITY�ڵ���뽨��һ�����к�
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
			
			//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ��д������ײͱ�ţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�9 bak9
			String getAgInsPkgIDSQL2 = new StringBuilder("select bak9 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL2);
			//APP_ENTITY�ڵ���뽨�д������ײͱ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
		}
		
		//20150311 ����Ҫ���뱣���ž�ֻ���ر����ţ�����Ͷ�����žͷ���Ͷ�����ţ���ѡһ������ͬʱ����
		String contno=cInNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		String proposalprtno=cInNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_BillNo");
		if("".equals(contno)||contno==null){
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPolcy_No").setText("");
		}
		if("".equals(proposalprtno)||proposalprtno==null){
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_BillNo").setText("");
		}
		
			
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out QueryPrem.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("����ʼ��");
//
//		String mInFilePath = "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/1053981_3_28_outSvc.xml";
//		String mOutFilePath = "H:/��·/�����к�����/���нӿ�/UATҵ����Ա���/��ѯ���ѽ�����Ϣout.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
//		
//		Document mOutXmlDoc = new QueryPrem(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new QueryPrem(null).noStd2Std(mInXmlDoc);
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