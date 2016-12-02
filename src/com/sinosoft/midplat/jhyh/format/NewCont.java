package com.sinosoft.midplat.jhyh.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.jhyh.format.NewContInXsl;
import com.sinosoft.midplat.jhyh.format.NewContOutXsl;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.jhyh.format.NewCont;

public class NewCont extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private Element cTransaction_Body = null;
	private String cGrade = null;
	private String cContPrtNo = null;
	
	public NewCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	@SuppressWarnings("unchecked")
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		
		//20150112 lilu ��������������ʱ�����з�����15λ��֤ʱ���Զ��ڵ�֤��ǰ���һ��0,����16λ��
		//��ˣ�������Ҫȥ����֤����λ��0��Ҫ���ɽ���������·���������������ȷ�ϣ��漰����֤��ȫ����Ҫ��������
		cContPrtNo=pNoStdXml.getRootElement().getChild("Transaction_Body").getChildText("BkVchNo");
		cContPrtNo=cContPrtNo.substring(1, cContPrtNo.length());
		pNoStdXml.getRootElement().getChild("Transaction_Body").getChild("BkVchNo").setText(cContPrtNo);
		
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		cLogger.info("======1");
		JdomUtil.print(pNoStdXml);
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("======2");
		JdomUtil.print(mStdXml);
		cTransaction_Body =
			(Element) mStdXml.getRootElement().getChild("Body").clone();
		
		//20141225 lilu��Ӯ�����A�������Ҫ��Դ������ı����е���������˵�˳λȫ��Ĭ��������һλ���ʼ�ȷ��
		if("211902".equals(cTransaction_Body.getChild("Risk").getChildText("RiskCode"))&&
				(cTransaction_Body.getChild("Bnf")!=null)){
		
			List<Element> bnfs= mStdXml.getRootElement().getChild("Body").getChildren("Bnf");
			
				for (int i=0;i<bnfs.size() ;i++) {
					if(bnfs.get(i).getChildText("Type").equals("1")){
						cGrade= bnfs.get(i).getChildText("Grade");
						cGrade=String.valueOf(Integer.parseInt(cGrade)+1);
						bnfs.get(i).getChild("Grade").setText(cGrade);
					}
				}
		}
		
		cLogger.info("Out NewCont.noStd2Std()!");
		cLogger.info("��ӡ��׼����:");
		JdomUtil.print(mStdXml);
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");

//		String mInFilePath2 = "H:/41682_675_1012_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		cTransaction_Header =
//			(Element) mInXmlDoc.getRootElement().getChild("Transaction_Header").clone();
//		mIs2.close();	
		
		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);
		
		/*Start-��֯���ر���ͷ*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("���׳ɹ���");
		} else {	//����ʧ��
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}

		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);

//		cTransaction_Header.addContent(mTran_Response);
		mNoStdXml.getRootElement().addContent(mTran_Response);

		mNoStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-��֯���ر���ͷ*/
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
	
//		String mInFilePath = "D:/msg/07/2014/201411/20141124/1687_1_1012_in.xml";
//		String mOutFilePath = "D:/msg/07/2014/201411/20141124/��·.xml";
//		String mInFilePath = "H:/1685_1_1012_in.xml";
//		String mOutFilePath = "H:/aaaa.xml";
//		String mInFilePath = "H:/1017756_179_1012_in.xml";
//		String mOutFilePath = "H:/1017756_179_1012inSvc.xml";
//		String mInFilePath = "F:/1018224_29_1012_in.xml";
//		String mOutFilePath = "H:/2222.xml";
//		String mInFilePath = "H';:/1021443_145_1_outSvc.xml";
//		String mOutFilePath = "H:/��Ӯ����.xml";
//		String mInFilePath = "H:/1021440_139_1012_in.xml";
//		String mOutFilePath = "H:/anying2.xml";
	
//		String mInFilePath = "D:/work/�к�/����/111.xml";
		String mInFilePath = "E:/112.txt";
		String mOutFilePath = "E:/out.xml";
//		String mOutFilePath = "D:/work/�к�/����/1098768_99_1012_out.xml";		
		InputStream mIs = null;
		OutputStream mOs = null;
		try{
			mIs = new FileInputStream(mInFilePath);
			Document mInXmlDoc = JdomUtil.build(mIs);
			mIs.close();
		
	//		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
			Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
			JdomUtil.print(mOutXmlDoc);
		
			mOs = new FileOutputStream(mOutFilePath);
			JdomUtil.output(mOutXmlDoc, mOs);
			mOs.flush();
			mOs.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(mIs!=null)
					mIs.close();
				if(mOs!=null)
					mOs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

//		String mInFilePath2 = "H:/2222.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		mIs2.close();	
//		new RuleParser().check(mInXmlDoc);
	
		System.out.println("�ɹ�������");
	}
}