//�����Զ�����
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class AutoWriteOff extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String stranNo = null;
	private String tranDate = null;
	private String sProposalPrtNo = null;
	private String sContPrtNo = null;
	private String sContNo = null;
	private String sfuncflag = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;

	public AutoWriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into AutoWriteOff.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();

		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Txn_Cd");	
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");	
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//ȡ��Ҫ�����ı�����Ӧ�ı��չ�˾��������ˮ��
		stranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Orig_TxnSrlNo");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		Document mStdXml = 
			AutoWriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//�ý�����ˮ�Ŵ�Tranlog���в��Ҷ�Ӧ��Ͷ������
		String getProposalPrtNoSQL = new StringBuilder("select ProposalPrtNo from tranlog where tranno = '").append(stranNo).append("'").toString();
		sProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
		mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalPrtNo);
		
//		if(sysTxCode.equals("")||sysTxCode==null){
			String getfuncflagSQL = new StringBuilder("select funcflag from tranlog where tranno = '").append(stranNo).append("'").toString();
			sfuncflag = new ExeSQL().getOneValue(getfuncflagSQL);
			mStdXml.getRootElement().getChild("Body").getChild("Type").setText(sfuncflag);
//		}
		
		if(sfuncflag.equals("1034")){//���ڳ���
			mStdXml.getRootElement().getChild("Body").getChild("Flag").setText("1");
			String getContNoSQL = new StringBuilder("select contno from tranlog where tranno = '").append(stranNo).append("'").toString();
			sContNo = new ExeSQL().getOneValue(getContNoSQL);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContNo);
		}
		if(sfuncflag.equals("1017")){//�˱�����
			SSRS tSSRS= new SSRS();
			ExeSQL  tExeSQL = new ExeSQL();
			String getContNoSQL = new StringBuilder("select contno from tranlog where tranno = '").append(stranNo).append("'").toString();
			sContNo = new ExeSQL().getOneValue(getContNoSQL);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContNo);
			String getContPrtNoSQL = new StringBuilder("select proposalprtno,otherno from tranlog where contno = '").append(sContNo).append("'").toString();
			tSSRS=tExeSQL.execSQL(getContPrtNoSQL);
			for (int j = 1;j<=tSSRS.getMaxRow();j++) {
				mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(tSSRS.GetText(1,1));
				mStdXml.getRootElement().getChild("Body").getChild("ContPrtNo").setText(tSSRS.GetText(1,2));
			}
		}
		cLogger.info("Out AutoWriteOff.noStd2Std()!");
		JdomUtil.print(mStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into AutoWriteOff.std2NoStd()...");
		System.out.println("������Ϣ���£�");
		JdomUtil.print(pStdXml);
		
//		String mInFilePath2 = "E:/994374_3_112_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();
		
		Document mNoStdXml = 
			AutoWriteOffOutXsl.newInstance().getCache().transform(pStdXml);

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
		
		/*Start-��֯���ر���ͷ*/
		JdomUtil.print(mNoStdXml);
		
		Element mSYS_TX_STATUS =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");
		//������Ӧ��
		Element mSYS_RESP_CODE =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE");
		//������Ӧ��������
		Element mSYS_RESP_DESC_LEN =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN");
		//������Ӧ����
		Element mSYS_RESP_DESC =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");
		
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if(mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mSYS_TX_STATUS.setText("00");
			mSYS_RESP_CODE.setText("000000000000");
			mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText(Desc).length()));
			mSYS_RESP_DESC.setText(mRetData.getChildText(Desc));
		} else{	//����ʧ��
			mSYS_TX_STATUS.setText("01");
			mSYS_RESP_CODE.setText("ZZZ072000001");
			mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText(Desc).length()));
			mSYS_RESP_DESC.setText(mRetData.getChildText(Desc));
		}
		
		/*End-��֯���ر���ͷ*/
		
		cLogger.info("Out AutoWriteOff.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("����ʼ��");
//
////		String mInFilePath = "H:/��·/����/P53819113OutSvrStd.xml";
////		String mOutFilePath = "H:/��·/����/P53819113OutNoStd2.xml";
//		String mInFilePath = "E:/994374_5_2_outSvc.xml";
//		String mOutFilePath = "E:/11111.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs,"GBK");
//		mIs.close();
//		
//		Document mOutXmlDoc = new AutoWriteOff(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new AutoWriteOff(null).noStd2Std(mInXmlDoc);
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
