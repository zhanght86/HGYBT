package com.sinosoft.midplat.newccb.format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class DelayedNewCont extends XmlSimpFormat {
	private Document pNoStdXmlCopy=null;
	private String receiveTime=null;
	public DelayedNewCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into DelayedNewCont.noStd2Std()...");
		pNoStdXmlCopy=pNoStdXml;//�������д����Ǳ�׼����
		//�������ʱ��
		receiveTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String insuID=this.cThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu");
		cLogger.info("���б���:"+insuID);
		pNoStdXml.getRootElement().getChild("Head").addContent(new Element("InsuID").setText(insuID));
		Document mStdXml = DelayedNewContInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out DelayedNewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into DelayedNewCont.std2NoStd()...");
		Document mNoStdXml = DelayedNewContOutXsl.newInstance().getCache().transform(pStdXml);
		//������Ӧʱ��
		String responseTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//���طǱ�׼���Ľڵ�ֵ����
		stdXmlFill(mNoStdXml,pNoStdXmlCopy,receiveTime,responseTime);
		cLogger.info("Out DelayedNewCont.std2NoStd()!");
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
		
		/**����ʵ������Ӧ������Ϣ*/
		Element newAPP_ENTITY=stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
		Element oldAPP_ENTITY=noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		//������׳ɹ�����ʵ�����е�Ӧ����
	    if("00".equals(newTX_HEADER.getChildText("SYS_TX_STATUS"))){
	    	Element APP_ENTITYEle=new Element("APP_ENTITY");
	    	Element AgIns_Pkg_IDEle=new Element("AgIns_Pkg_ID");
	    	//���ָ���
	    	Element Cvr_NumEle=new Element("Cvr_Num");
	    	Cvr_NumEle.setText(oldAPP_ENTITY.getChildText("Cvr_Num"));
	    	//���ֽڵ�
	    	Element Bu_ListEle=new Element("Bu_List");
	    	APP_ENTITYEle.addContent(AgIns_Pkg_IDEle);
	    	APP_ENTITYEle.addContent(Cvr_NumEle);
	    	APP_ENTITYEle.addContent(Bu_ListEle);
	    	//��ȡ���д������������ֽڵ㼯��
	    	List<Element> insuranceCodeList=oldAPP_ENTITY.getChild("Bu_List").getChildren("Bu_Detail");
	    	for (Element element : insuranceCodeList) {
	    		Element Bu_DetailEle=new Element("Bu_Detail");
	    		Element Cvr_IDEle=new Element("Cvr_ID");
	    		Cvr_IDEle.setText(element.getChildText("Cvr_ID"));
	    		Bu_DetailEle.addContent(Cvr_IDEle);
	    		Bu_ListEle.addContent((Element)Bu_DetailEle);
			}
	    	//���ʵ������Ӧ����ڵ�
	    	newAPP_ENTITY.addContent(APP_ENTITYEle);
	    	//Ͷ�����Žڵ�
	    	Element Ins_BillNoEle=new Element("Ins_BillNo");
	    	Ins_BillNoEle.setText(oldAPP_ENTITY.getChildText("Ins_Bl_Prt_No"));
	    	Element Ins_Co_Acrdt_Stff_NmEle=new Element("Ins_Co_Acrdt_Stff_Nm");
	    	Element InsCoAcrStCrQuaCtf_IDEle=new Element("InsCoAcrStCrQuaCtf_ID");
	    	APP_ENTITYEle.addContent(Ins_BillNoEle);
	    	APP_ENTITYEle.addContent(Ins_Co_Acrdt_Stff_NmEle);
	    	APP_ENTITYEle.addContent(InsCoAcrStCrQuaCtf_IDEle);
	    }
	    //����TX_HEADERЭ�鱨��ͷ��Ӧ�ñ��ĳ���
	    int TX_BODYLength=JdomUtil.toBytes(stdXml.getRootElement().getChild("TX_BODY")).length;
	    newTX_HEADER.getChild("SYS_MSG_LEN").setText(TX_BODYLength+"");
	}
}
