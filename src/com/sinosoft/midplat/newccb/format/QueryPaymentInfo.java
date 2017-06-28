package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryPaymentInfo extends XmlSimpFormat {
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sProposalPrtNo = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	public QueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.noStd2Std()...");
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
				//����Ͷ�����Ų�ѯ��ʵʱͶ��ʱ�ģ��������������롢�������ޡ��������ڴ��롢���ѽɷѷ�ʽ���롢���ѽɷ����������ѽɷ����ڴ��� 
				Element mAppEntityEle = mNoStdXml.getRootElement().getChild("TX_BODY")
				.getChild("ENTITY").getChild("APP_ENTITY");
				Element mLcCont=pStdXml.getRootElement().getChild("LCCont");
				sProposalPrtNo= mLcCont.getChildText("PrtNo");
				String sql="select BAK1 from TranLog where ProposalPrtNo='"+sProposalPrtNo+"' " +
						"and funcflag='1060' and rcode='0'";
				String mBak1 = new ExeSQL().getOneValue(sql);
				if(mBak1 == null || mBak1.equals("")){
					throw new MidplatException("����Ͷ������δ��ѯ����ʵʱͶ����Ӧ��Ϣ����ȷ�ϣ�");
				}else{
					String strs[] = mBak1.split("\\|");
					 //��������������
					mAppEntityEle.getChild("Ins_Yr_Prd_CgyCd").setText(strs[0]);
				    //��������
					mAppEntityEle.getChild("Ins_Ddln").setText(strs[1]);
				    //�������ڴ���
					mAppEntityEle.getChild("Ins_Cyc_Cd").setText(strs[2]);
				    //���ѽɷѷ�ʽ����
					mAppEntityEle.getChild("InsPrem_PyF_MtdCd").setText(strs[3]);
				    //���ѽɷ�����
					mAppEntityEle.getChild("InsPrem_PyF_Prd_Num").setText(strs[4]);
				    //���ѽɷ����ڴ��� 
					mAppEntityEle.getChild("InsPrem_PyF_Cyc_Cd").setText(strs[5]);
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
