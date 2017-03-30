package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryPaymentInfo extends XmlSimpFormat {
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
	
	public QueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.noStd2Std()...");
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
				QueryPaymentInfoInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info(JdomUtil.toStringFmt(mStdXml));
		sProposalPrtNo=mStdXml.getRootElement().getChild("LCCont").getChildText("PrtNo");
//		sContno=mStdXml.getRootElement().getChild("LCCont").getChildText("ContNo");
		if(sContno==null||sContno.equals("")){
			//��cont���в��Ҷ�Ӧ�ı�����
			String getContNoSQL = new StringBuilder("select contno from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sContno = new ExeSQL().getOneValue(getContNoSQL);
			if(sContno==null&&sContno.equals("")){
				mStdXml.getRootElement().getChild("LCCont").getChild("ContNo").setText(sProposalPrtNo);
			}else {
//				mStdXml.getRootElement().getChild("LCCont").getChild("ContNo").setText(sContno);
			}
		}
		
		cLogger.info("Out QueryPaymentInfo.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.std2NoStd()...");
		Document mNoStdXml = 
				QueryPaymentInfoOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
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

			Element mRetData = pStdXml.getRootElement().getChild("RetData");
			if (mRetData.getChildText(Flag).equals("1")) {	//���׳ɹ�
				
//				sPayedTimes=pStdXml.getRootElement().getChild("Body").getChildText("PayedTimes");
				//�ѽ���������>=1����ʾ�����ڽɷѲ�ѯ������ΪʵʱͶ���ɷѲ�ѯ
//				if(Integer.parseInt(sPayedTimes)>=1){
//					mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("14");
//				}else{
//					mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("11");
//				}
			
			
//			sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
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
//				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
				
				//��cont���в��Ҷ�Ӧ��Ͷ�����ŵĽ��д������ײͱ�ţ�ʵʱͶ��ʱ�Ѵ��뱸���ֶ�9 bak9
				String getAgInsPkgIDSQL2 = new StringBuilder("select bak9 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
				sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL2);
				//APP_ENTITY�ڵ���뽨�д������ײͱ��
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
			}
			
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			} else {	//����ʧ��
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			}
			
			/*End-��֯���ر���ͷ*/

			cLogger.info("Out QueryPaymentInfo.std2NoStd()!");
			return mNoStdXml;
	}

}
