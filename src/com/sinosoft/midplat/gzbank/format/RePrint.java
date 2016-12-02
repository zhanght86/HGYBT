package com.sinosoft.midplat.gzbank.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class RePrint extends XmlSimpFormat {

	private String mOldDanNo = "";
	
	private String mNewDanNo = "";
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		
		mOldDanNo = pNoStdXml.getRootElement().getChildText("OldDanNo");
		
		mNewDanNo = pNoStdXml.getRootElement().getChildText("NewDanNo");
		
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		String sContno=mStdXml.getRootElement().getChild("Body").getChildText("ContNo");
		if(sContno!=null&&!sContno.equals("")){
			//��tranlog���в��Ҷ�Ӧ�ľɵ�֤��
			String getProposalprtnoSql = new StringBuilder("select ProposalPrtNo from tranlog where contno = '").append(sContno).append("'").
			append(" and funcflag= '").append("1014'").toString();
			String sProposalprtnoSql = new ExeSQL().getOneValue(getProposalprtnoSql);
			cLogger.info("Ͷ�����ţ�"+sProposalprtnoSql);
			mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalprtnoSql);
		}
		
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");

		//�ش���µ����ر��Ļ�����ȫһ��������ֱ�ӵ���
		Element rr = new Element("cThisConf");
		ContConfirm mContConfirm = new ContConfirm(rr);
		Document mNoStdXml = mContConfirm.std2NoStd(pStdXml);
//		Document mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml);
		if("00".equals(mNoStdXml.getRootElement().getChildText("ResultCode"))){
			mNoStdXml.getRootElement().getChild("OldDanNo").setText(mOldDanNo);
		}
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
	System.out.println("����ʼ��");

	String mInFilePath = "F:\\959391_21_1011_in.xml";
	String mOutFilePath = "F:\\33333.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

//	Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("�ɹ�������");
}
}
