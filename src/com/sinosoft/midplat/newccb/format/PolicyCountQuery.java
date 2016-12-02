package com.sinosoft.midplat.newccb.format;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class PolicyCountQuery extends XmlSimpFormat {
	private Document bankXml = null;
	//���ܷ���ʱ��
	private String startTime = null;
	public PolicyCountQuery(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into PolicyCountQuery.noStd2Std()...");
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
		Document mStdXml = 
			PolicyCountQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		//��������
		mStdXml.getRootElement().getChild("Head").getChild("TranDate").setText(tranDate);
		//����ʱ��
		mStdXml.getRootElement().getChild("Head").getChild("TranTime").setText(tranTime);
		cLogger.info("Out PolicyCountQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into PolicyCountQuery.std2NoStd()...");
		String endTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Document mNoStdXml = 
			PolicyCountQueryOutXsl.newInstance().getCache().transform(pStdXml);
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
		cLogger.info("Out PolicyCountQuery.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
		
		String mInFilePath = "F:\\ccbNew\\�̵Ʋ���������.xml";
//		String mOutFilePath = "F:\\���ڽ���test\\ccb.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new PolicyCountQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new PolicyCountQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
		
		System.out.println("�ɹ�������");
	}
}