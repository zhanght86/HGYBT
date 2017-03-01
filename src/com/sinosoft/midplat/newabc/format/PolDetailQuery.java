package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class PolDetailQuery extends XmlSimpFormat {
    	private Element header=null;
    	private String bankcode=null;
    	private String sdate=null;
    	private String stime=null;
    	private String stranno=null;
    	private String sinsuserial=null;

		public PolDetailQuery(Element pThisBusiConf) {
			super(pThisBusiConf);
		}
	
		public Document noStd2Std(Document pNoStdXml) throws Exception {
			
			header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
			bankcode=header.getChildText("BankCode");
			sdate=header.getChildText("TransDate");
			stime=header.getChildText("TransTime");
			stranno=header.getChildText("SerialNo");
			sinsuserial=header.getChildText("InsuSerial");
			cLogger.info("Into PolDetailQuery.noStd2Std()...");
			Document mStdXml = 
					PolDetailQueryInXsl.newInstance().getCache().transform(pNoStdXml);
			//����
			JdomUtil.print(mStdXml);
			Element mBody=mStdXml.getRootElement().getChild("Body");
			String contno=mBody.getChildText("ContNo");
			String sql="select proposalprtno from cont where contno= '"+contno+"' ";
			SSRS ssrs0=new ExeSQL().execSQL(sql);
			Element sProposalPrtNo = new Element("ProposalPrtNo");
			mBody.addContent(sProposalPrtNo);
			Element ProposalPrtNo = mBody.getChild("ProposalPrtNo");
			if(ssrs0.MaxNumber!=0){
				ProposalPrtNo.setText(ssrs0.GetText(1, 1));
			}else{
				ProposalPrtNo.setText(null);
			}
			
			cLogger.info("Out PolDetailQuery.noStd2Std()!");
			return mStdXml;
		
		}
	
		
		public Document std2NoStd(Document pStdXml) throws Exception {
			cLogger.info("Into PolDetailQuery.std2NoStd()...");
			
			Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
			Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
			Document mNoStdXml = 
					PolDetailQueryOutXsl.newInstance().getCache().transform(pStdXml);
			Element  mHeader=new Element("Header");
			mNoStdXml.getRootElement().addContent(mHeader);
			Element  mRetCode=new Element("RetCode");
			Element  mRetMsg=new Element("RetMsg");
			Element  mBankCode=new Element("BankCode");
			Element  mSerialNo=new Element("SerialNo");
			Element  mInsuSerial=new Element("InsuSerial");
			Element  mTransDate=new Element("TransDate");
			Element  mTransTime=new Element("TransTime");
			mHeader.addContent(mSerialNo);
			mHeader.addContent(mInsuSerial);
			mHeader.addContent(mTransDate);
			mHeader.addContent(mTransTime);
			
			mHeader.addContent(mRetCode);
			mHeader.addContent(mRetMsg);
			mHeader.addContent(mBankCode);
			
			mSerialNo.setText(stranno);
			mInsuSerial.setText(sinsuserial);
			mTransDate.setText(sdate);
			mTransTime.setText(stime);
			mBankCode.setText(bankcode);
			//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
			System.out.println("=============="+ttDesc.getText());
			mRetMsg.setText(ttDesc.getText());
			if (ttFlag.getValue().equals("0")){
			   cLogger.info("���׳ɹ�=========");
			   mRetCode.setText("000000");
			  mRetMsg.setText("���׳ɹ�");
			}
			if (ttFlag.getValue().equals("1")){
				cLogger.info("����ʧ��=========ʧ����Ϣ:"+mRetMsg.getText());
				mRetCode.setText("009999");
				mRetMsg.setText("����ʧ��");
			}
			
			cLogger.info(mRetCode.getText());
			cLogger.info(mRetMsg.getText());
			
			cLogger.info("Out PolDetailQuery.std2NoStd()!");
			return mNoStdXml;
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)  throws Exception  {
		System.out.println("����ʼ��");
//		String mInFilePath = "E:/Java/����/��ũ�б�����ѯ����.xml";
//		String mOutFilePath = "E:/Java/����/��ũ�б�����ѯ����.xml";

		String mInFilePath = "D:/File/task/20170215/newabc/ybt_test/1021in_noStd.xml";
		String mOutFilePath = "D:/File/task/20170215/newabc/ybt_test/1021in_Std.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

	//	Document mOutXmlDoc = new PolDetailQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new PolDetailQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		System.out.println("�ɹ�������");
	}

}
