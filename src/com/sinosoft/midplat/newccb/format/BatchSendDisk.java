package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class BatchSendDisk extends XmlSimpFormat {
	//�Ǳ�׼���뱨��ͷ
	private Element oldHeader=null;
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
		oldHeader=(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity=(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		oldAppEntity=(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();
		mSYS_RECV_TIME=new SimpleDateFormat("yyyyMMdd").format(new Date());
		tranNo=oldComEntity.getChildText("SvPt_Jrnl_No");
		tranDate=NewCcbFormatUtil.getTimeAndDate(oldHeader.getChildText("SYS_REQ_TIME"), 0, 8);
		sysTxCode=oldHeader.getChildText("SYS_TX_CODE");
		Document pStdXml=BatResponseInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out BatchSendDisk.noStd2Std()...");
		return pStdXml;
	}
	
	public Document std2NoStd(Document pStdXml){
		mSYS_RESP_TIME=new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		return pStdXml;
	}
	
}
