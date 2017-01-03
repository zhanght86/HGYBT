package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class UpdateServiceStatus extends XmlSimpFormat {

	/*�Ǳ�׼����ͷ*/
	private Element cTransaction_Header = null;
	//�������ʱ��
	private String mSYS_RECV_TIME = null;
	//������Ӧʱ��
	private String mSYS_RESP_TIME = null;
	//������ˮ��[TX_BODY/COM_ENTITY/SvPt_Jrnl_No]
	private String tranNo = null;
	//��������[TX_HEADER/SYS_REQ_TIME]
	private String tranDate = null;
	//������[TX_HEADER/SYS_TX_CODE]
	private String sysTxCode = null;
	//�������ײͱ��[TX_BODY/APP_ENTITY/AgIns_Pkg_ID]
	private String sAginsPkgId = null;
	//�ɷ�Ƶ��[TX_BODY/APP_ENTITY/../InsPrem_PyF_MtdCd]
	private String sPayIntv = null;
	//Ͷ������[TX_BODY/APP_ENTITY]
	private String sMult = null;
	//�������(�������ٷֱ�) [TX_BODY/APP_ENTITY/Bnft_Pct]
	private String sLot = null;
	//
	private String sPayCycCod = null;
	//��ϵ����
	private String sRelationShip = null;
	//����ͷ[TX_HEADER]
	private Element oldTxHeader = null;
	//�ɹ�����[TX_BODY/COM_ENTITY]
	private Element oldComEntity = null;
	//��Ӧ����[TX_BODY/APP_ENTITY]
	private Element oldAppEntity = null;
	
	/**����ķǱ�׼����*/
	private Document noStdDoc = null;//�Ǳ�׼����

	public UpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		// Into UpdateServiceStatus.noStd2Std()...
		cLogger.info("Into UpdateServiceStatus.noStd2Std()...");
		
		//���ݷǱ�׼����Ӧ����[TX_BODY/APP_ENTITY]
		oldAppEntity =
			(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();
		//��ȡ�Ǳ�׼���Ĵ������ײͱ��[TX_BODY/APP_ENTITY/../AgIns_Pkg_ID]
//		sAginsPkgId	=oldAppEntity.getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
		System.out.println(sAginsPkgId);
		
		//���ݷǱ�׼���ı���ͷ[TX_HEADER]
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		//���ݷǱ�׼���Ĺ�����[TX_BODY/COM_ENTITY]
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		//��ȡ�Ǳ�׼���ķ�����[TX_HEADER/SYS_TX_CODE]
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		//��ȡ�Ǳ�׼���ķ�����ˮ��[TX_BODY/COM_ENTITY/SvPt_Jrnl_No]
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		//���ѽ������ڴ���[TX_BODY/APP_ENTITY/../InsPrem_PyF_Cyc_Cd]
//		sPayCycCod =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_Cyc_Cd");
		//���ƷǱ�׼����
		noStdDoc = (Document)pNoStdXml.clone();//��ȡ�Ǳ�׼����
		// ʵʱͶ�����뱨����ʽʵ����ȡ�˿�-������ ���ý��Ǳ�׼����ת��Ϊ��׼����
		Document mStdXml = UpdateServiceStatusInXsl.newInstance().getCache()
				.transform(pNoStdXml);// [TranData]
		JdomUtil.print(mStdXml);
		cLogger.info(JdomUtil.toStringFmt(mStdXml));
		// Out UpdateServiceStatus.noStd2Std()!
		cLogger.info("Out UpdateServiceStatus.noStd2Std()!");
		// ���������׼����
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into UpdateServiceStatus.std2NoStd()...");
		// �������ɹ�,����ԭʼ����
		String funcFlag = pStdXml.getRootElement().getChild("RetData")
				.getChildText("Flag");
		if (StringUtils.isNotBlank(funcFlag) && "0".equals(funcFlag)) {
			// ����Ǳ�׼����
			saveNoStdDoc(pStdXml);
		}

//		JdomUtil.print(pStdXml);
		Document mNoStdXml = UpdateServiceStatusOutXsl.newInstance().getCache()
				.transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		
		// ������Ӧʱ��[20161212103228778]
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// ����TX_HEADER��һЩ�ڵ���Ϣ[���ظ����еķǱ�׼����,���з������ķǱ�׼���ĵ�ͷ�ڵ�,�������ʱ��(20161212095023139),������Ӧʱ��(20161212103228778)]
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader,
				mSYS_RECV_TIME, mSYS_RESP_TIME);

		// ���˱��ɹ���ʱ�򣬷���TX_BODYʱ������COM_ENTITY�ڵ�
		// String resultCode =
		// pStdXml.getRootElement().getChild("Head").getChildText("Flag");
		// if(resultCode.equals("0")){
		// ���ؼ�����COM_ENTITY�ڵ�ķǱ�׼����[ת����ķǱ�׼����,���ķ��صı�׼����,body�ڵ��µ�COM_ENTITY�ڵ�,������(������:P53819113)]
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml,
				oldComEntity, sysTxCode);
		// }
		mNoStdXml=setAppEntity(mNoStdXml);
		
		/* Start-��֯���ر���ͷ */
		// ���ķ��صı�׼����ͷ
		Element mRetData = pStdXml.getRootElement().getChild("RetData");
		if (mRetData.getChildText(Flag).equals("1")) { // ���׳ɹ�[����ת����ķǱ�׼����ͷ]
			mNoStdXml.getRootElement().getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC")
					.setText(mRetData.getChildText(Desc));
			mNoStdXml
					.getRootElement()
					.getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC_LEN")
					.setText(
							Integer.toString(mRetData.getChildText(Desc)
									.length()));
			// COM_ENTITY�ڵ���������ˮ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("SvPt_Jrnl_No")
					.setText(tranNo);
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt")
					.setText(tranDate);

			// COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No")
					.setText(tranNo);
			// ���뱣�մ����ײͱ��
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("AgIns_Pkg_ID")
//					.setText(sAginsPkgId);
			// ���뱣�ѽɷѷ�ʽ����
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("InsPrem_PyF_MtdCd")
//					.setText(sPayIntv);
			// ���뱣�ѽɷ����ڴ���
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("InsPrem_PyF_Cyc_Cd")
//					.setText(sPayCycCod);
			// ����Ͷ������
			// mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_Cps").setText(sMult);

		} else { // ����ʧ��[����ת����ķǱ�׼����ͷ������Ϣ]
//			mNoStdXml.getRootElement().getChild("TX_HEADER")
//					.getChild("SYS_RESP_CODE").setText("ZZZ072000001");// ����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC")
					.setText(mRetData.getChildText("Desc"));// ������Ӧ����
			mNoStdXml
					.getRootElement()
					.getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC_LEN")
					.setText(
							Integer.toString(mRetData.getChildText(Desc)
									.length()));// ������Ӧ��������
		}

		/* End-��֯���ر���ͷ */

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * 
	 * saveNoStdDoc ��׼����
	 * 
	 * @param stdDoc
	 */
	private void saveNoStdDoc(Document stdDoc) {

		LKPolicyXML policyXML = new LKPolicyXML();

		// ��׼���ĸ��ڵ�
		Element stdDocRoot = stdDoc.getRootElement();

		// ���ñ�����
		policyXML.setContNo(stdDocRoot.getChild("LCConts").getChildText(ContNo));
		// Ͷ��Ͷ������
		policyXML.setProposalPrtNo(stdDocRoot.getChild("LCConts").getChildText("ProposalContNo"));

		// �������л�����
		policyXML.setTranCom("03");

		// ���ý�������
		policyXML.setTranDate(DateUtil.parseDate(tranDate, "yyyyMMdd"));

		// ���ñ�����Ϣ
		policyXML.setXmlContent(JdomUtil.toBytes(noStdDoc));

		// ������������޸�����ʱ��
		policyXML.setMakeDate(new Date());
		policyXML.setMakeTime(DateUtil.getCur8Time());
		policyXML.setModifyDate(new Date());
		policyXML.setModifyTime(DateUtil.getCur8Time());

		LKPolicyXMLDao dao = new LKPolicyXMLDao();
		try {
			// ���뱣��������Ϣ
			boolean flag = dao.insert(policyXML);
			if (!flag) {
				cLogger.error("����Ǳ�׼���ĵ����ݿ�ʧ��!");
			} else {
				cLogger.info("����Ǳ�׼���ĵ����ݿ�ɹ�!");
			}
		} catch (Exception e) {
			cLogger.error("����Ǳ�׼���ĵ����ݿ�ʧ��:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Document setAppEntity(Document mNoStdXml){
		Element mRootEle=mNoStdXml.getRootElement();
		Element mAppEntity=mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		List<Element> insuList=mAppEntity.getChild("Insu_List").getChildren("Insu_Detail");
		for (Element e : insuList) {
			if(!e.getChildText("AcIsAR_StCd").equals("076019")){
				e.removeChild("Tot_InsPrem_Amt");
				e.removeChild("Ins_PyF_Amt");
				e.removeChild("PyF_CODt");
			}
		}
		return mNoStdXml;
	}
	
}
