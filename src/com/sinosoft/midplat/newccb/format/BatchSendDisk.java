package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class BatchSendDisk extends XmlSimpFormat {
	
	//�Ǳ�׼���뱨��ͷ
	private Element oldTxHeader=null;
	//�Ǳ�׼���뱨�Ĺ�����
	private Element oldComEntity=null;
	//�Ǳ�׼���뱨��ͷӦ����
	private Element oldAppEntity=null;
	private String mSYS_RECV_TIME=null;
	private String mSYS_RESP_TIME=null;
	private String tranNo=null;
	private String tranDate=null;
	private String sysTxCode=null;
	
	public BatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into BatchSendDisk.noStd2Std()...");
		oldTxHeader=(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity=(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		oldAppEntity=(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();
		mSYS_RECV_TIME=new SimpleDateFormat("yyyyMMdd").format(new Date());
		tranNo=oldComEntity.getChildText("SvPt_Jrnl_No");
		tranDate=NewCcbFormatUtil.getTimeAndDate(oldTxHeader.getChildText("SYS_REQ_TIME"), 0, 8);
		sysTxCode=oldTxHeader.getChildText("SYS_TX_CODE");
		Document pStdXml=BatResponseInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out BatchSendDisk.noStd2Std()!");
		return pStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into BatchSendDisk.std2NoStd()...");
		mSYS_RESP_TIME=new SimpleDateFormat("yyyyMMdd").format(new Date());
		Document mNoStdXml=BatResponseOutXsl.newInstance().getCache().transform(pStdXml);
		mNoStdXml=NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		mNoStdXml=NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
		//������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		//���չ�˾��������
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		//���չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		//���ر���ͷ:������Ӧ�롢������Ӧ������������Ӧ��������
		Element mHead=pStdXml.getRootElement().getChild("Head");
		if(mHead.getChildText(Flag).equals(0)){
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mHead.getChildText(Desc).length()));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mHead.getChildText(Desc));
		}else{
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mHead.getChildText(Desc).length()));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mHead.getChildText(Desc));
		}
		cLogger.info("Out BatchSendDisk.std2NoStd()!");
		return pStdXml;
	}
	
}
