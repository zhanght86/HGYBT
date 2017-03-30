package com.sinosoft.midplat.newccb.format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class GetDelayedUnderwritingStatus extends XmlSimpFormat {
	private Document pNoStdXmlCopy=null;
	private String receiveTime=null;
	public GetDelayedUnderwritingStatus(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into GetDelayedUnderwritingStatus.noStd2Std()...");
		pNoStdXmlCopy=pNoStdXml;//�������д����Ǳ�׼����
		//�������ʱ��
		receiveTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String insuID=this.cThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu");
		cLogger.info("���б���:"+insuID);
		pNoStdXml.getRootElement().getChild("Head").addContent(new Element("InsuID").setText(insuID));
		Document mStdXml = GetDelayedUnderwritingStatusInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out GetDelayedUnderwritingStatus.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into GetDelayedUnderwritingStatus.std2NoStd()...");
		Document mNoStdXml = GetDelayedUnderwritingStatusOutXsl.newInstance().getCache().transform(pStdXml);
		//������Ӧʱ��
		String responseTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//���طǱ�׼���Ľڵ�ֵ����
		stdXmlFill(mNoStdXml,pNoStdXmlCopy,receiveTime,responseTime);
		cLogger.info("Out GetDelayedUnderwritingStatus.std2NoStd()!");
		return mNoStdXml;
	}
	/**
	 * ���طǱ�׼���Ľڵ�ֵ����
	 * @param stdXml ���ظ����еķǱ�׼����
	 * @param noStdXml ���д���ķǱ�׼����
	 * @param requestTime ���Ľ���ʱ��
	 * @param responseTime ������Ӧʱ��
	 */
	public void stdXmlFill(Document stdXml,Document noStdXml,String receiveTime,String responseTime) throws Exception{
		/**Э�鱨��ͷ��Ϣ����*/
		Element newTX_HEADER=stdXml.getRootElement().getChild("TX_HEADER");
		Element oldTX_HEADER=noStdXml.getRootElement().getChild("TX_HEADER");
		//ȫ���¼����ٺ�
		String mSYS_EVT_TRACE_ID = oldTX_HEADER.getChildText("SYS_EVT_TRACE_ID");
		newTX_HEADER.getChild("SYS_EVT_TRACE_ID").setText(mSYS_EVT_TRACE_ID);
		//���𷽰�ȫ�ڵ���
		String mSYS_REQ_SEC_ID = oldTX_HEADER.getChildText("SYS_REQ_SEC_ID"); 
		newTX_HEADER.getChild("SYS_REQ_SEC_ID").setText(mSYS_REQ_SEC_ID);
		//�ӽ������
		String mSYS_SND_SERIAL_NO = oldTX_HEADER.getChildText("SYS_SND_SERIAL_NO");
		newTX_HEADER.getChild("SYS_SND_SERIAL_NO").setText(mSYS_SND_SERIAL_NO);
		//�������ʱ��
		newTX_HEADER.getChild("SYS_RECV_TIME").setText(receiveTime);
		//������Ӧʱ��
		newTX_HEADER.getChild("SYS_RESP_TIME").setText(responseTime);
		//������Ӧ��������
		int SYS_RESP_DESC_LENLength=newTX_HEADER.getChildText("SYS_RESP_DESC").getBytes("UTF-8").length;
		newTX_HEADER.getChild("SYS_RESP_DESC_LEN").setText(SYS_RESP_DESC_LENLength+"");
		/**Ӧ�ñ����й�������Ϣ����*/
		Element newENTITY=stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
		Element oldCOM_ENTITY=noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		//������
		Element mSYS_TX_CODE = new Element("SYS_TX_CODE");
		mSYS_TX_CODE.setText(oldTX_HEADER.getChildText("SYS_TX_CODE"));
		oldCOM_ENTITY.addContent(mSYS_TX_CODE);
		//���չ�˾��������  
		Element mIns_Co_Acg_DtEle = new Element("Ins_Co_Acg_Dt");
		mIns_Co_Acg_DtEle.setText(oldTX_HEADER.getChildText("SYS_REQ_TIME").substring(0,8));
		oldCOM_ENTITY.addContent(mIns_Co_Acg_DtEle);
		//���չ�˾��ˮ��
		Element mIns_Co_Jrnl_No = new Element("Ins_Co_Jrnl_No");
		mIns_Co_Jrnl_No.setText(oldCOM_ENTITY.getChildText("SvPt_Jrnl_No"));
		oldCOM_ENTITY.addContent(mIns_Co_Jrnl_No);
		//���չ�˾�ͷ��绰
		Element mTelPhoneNo = new Element("Ins_Co_Cst_Svc_Tel");
		mTelPhoneNo.setText("4009-800-800");
		oldCOM_ENTITY.addContent(mTelPhoneNo);
		//�滻���ر����й�����ڵ�
		newENTITY.removeChild("COM_ENTITY");
		newENTITY.addContent((Element)oldCOM_ENTITY.clone());
	    //����TX_HEADERЭ�鱨��ͷ��Ӧ�ñ��ĳ���
	    int TX_BODYLength=JdomUtil.toBytes(stdXml.getRootElement().getChild("TX_BODY")).length;
	    newTX_HEADER.getChild("SYS_MSG_LEN").setText(TX_BODYLength+"");
	}
}
