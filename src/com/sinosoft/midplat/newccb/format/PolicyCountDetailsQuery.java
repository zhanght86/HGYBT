//��ȡ���������ѯ
package com.sinosoft.midplat.newccb.format;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
//import com.sinosoft.midplat.ccbNew.CcbGetFile;
import com.sinosoft.midplat.newccb.util.CcbGetFile;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.midplat.newccb.util.PutContFile;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class PolicyCountDetailsQuery extends XmlSimpFormat {
	
	private Document bankXml = null;
	//���ܷ���ʱ��
	private String startTime = null;
	
	String tPackNum = null;
	
	public PolicyCountDetailsQuery(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into PolicyCountDetailsQuery.noStd2Std()...");
		/*
		 * bankXml ȡ�����з����Ǳ�׼����
		 * @param tranDate����ʱ��
		 * @param tranTime ��������
		 */
		startTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		bankXml = pNoStdXml;
		String reqTime = pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"); 
		String tranDate = reqTime.substring(0,8);
		String tranTime = reqTime.substring(8,14);
		
		Element tRoot = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		Document mStdXml = 
			PolicyCountDetailsQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		//��������
		mStdXml.getRootElement().getChild("Head").getChild("TranDate").setText(tranDate);
		//����ʱ��
		mStdXml.getRootElement().getChild("Head").getChild("TranTime").setText(tranTime);
		tPackNum = tRoot.getChildText("BtchBag_SN");
		cLogger.info("Out PolicyCountDetailsQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into PolicyCountDetailsQuery.std2NoStd()...");
		String endTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Document mNoStdXml = 
			PolicyCountDetailsQueryOutXsl.newInstance().getCache().transform(pStdXml);
		/*Start-��֯���ر���*/
		NewCcbFormatUtil.setDom(bankXml, mNoStdXml,startTime,endTime);
		//����״̬
		Element mSYS_TX_STATUS =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");
		//������Ӧ����
		Element mSYS_RESP_DESC =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("1")) {	//���׳ɹ�
			mSYS_TX_STATUS.setText("00");
			mSYS_RESP_DESC.setText(mRetData.getChildText(Desc));
		} else if(mRetData.getChildText(Flag).equals("0")){	//����ʧ��
			mSYS_TX_STATUS.setText("01");
			mSYS_RESP_DESC.setText("����ʧ��");
		} else {
			mSYS_TX_STATUS.setText("02");
			mSYS_RESP_DESC.setText("��ȷ��");
		}
		/*End-��֯���ر���ͷ*/
		Element tAPP_ENTITY = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		Element pThisConfRoot = CcbGetFile.newInstance().getConf().getRootElement();
		String id = pThisConfRoot.getChild("file").getChildText("PackNum");
		String date  = pThisConfRoot.getChild("file").getChildText("Date");
		String localPath = cThisBusiConf.getChildText("localDir");
		String tTranCode = cThisBusiConf.getAttributeValue(outcode);
		String filePath = localPath + "/"+tTranCode+"_"+date+"_73_"+"tPackNum.xml";
		if(id.equals(tPackNum)){
			List<Element> list = pThisConfRoot.getChild("file").getChild("listCont").getChildren("contNo");
			boolean isTrue = new PutContFile().isSuccessed(list, filePath, "", null, null);
			if(isTrue=true){
				Element tCur_Btch_Dtl_TDnum = tAPP_ENTITY.getChild("Cur_Btch_Dtl_TDnum").setText(Integer.toString(list.size()));
			}else{
				mSYS_TX_STATUS.setText("01");
				mSYS_RESP_DESC.setText("�ļ�������");
			}
		}
		cLogger.info("Out PolicyCountDetailsQuery.std2NoStd()!");
		return mNoStdXml;
	}
}