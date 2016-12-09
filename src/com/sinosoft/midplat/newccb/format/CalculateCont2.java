//�������㱣�ղ�Ʒ(����)
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class CalculateCont2 extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private String   Riskname="";
	
	
	public CalculateCont2(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into CalculateCont2.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
	
		
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		
		cLogger.info("sssssssssssssssssssssssssssssssssssssssss"+tranDate);
		
		Document mStdXml = 
			CalculateContInXsl2.newInstance().getCache().transform(pNoStdXml);
		
		
		
		
		Element mbody=mStdXml.getRootElement().getChild("Body");
	
		Riskname=mbody.getChild(Risk).getChildText("RiskName");
		
		cLogger.info("sssssssssssssssssssssssssssssssssssssssss");
		JdomUtil.print(oldTxHeader);
		
		
		cLogger.info("Out CalculateCont2.noStd2Std()!");
		return mStdXml;
	}

	
	
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into CalculateCont2.std2NoStd()...");
		
		 //������ָ���
		Element  mRiskCount=new Element("RiskCount");
	      Element mbody=pStdXml.getRootElement().getChild("Body");
	    List<Element> countRisk=mbody.getChildren("Risk");
	    mRiskCount.setText(String.valueOf(countRisk.size()));
	    mbody.addContent(mRiskCount);
	 
	    Element mRisk=mbody.getChild("Risk");
	    Element mRiskname=new Element("RiskNm");
	    mRiskname.setText(Riskname);
	    mRisk.addContent(mRiskname);
	    cLogger.info("ssssssssssssssssssssssssssssssssssss"+Riskname);
	    
	    //���ܱ��ѽ��
	   Element pTotalPrem=new Element("TotalPrem");
	   int countprem=0;
	    for (int i = 1; i <= countRisk.size(); i++) {
	    	 int mPayEndYear=Integer.valueOf(mbody.getChild("Risk").getChildText("PayEndYear")) ;
	    	countprem=countprem+Integer.valueOf(mbody.getChild("Risk").getChildText("Prem"))*mPayEndYear;
		}
	     //���������
	    pTotalPrem.setText(String.valueOf(countprem));
	  mbody.addContent(pTotalPrem);
		Document mNoStdXml = 
			CalculateContOutXsl2.newInstance().getCache().transform(pStdXml);
      
		JdomUtil.print(mNoStdXml);
		
		
		//������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		
		
		//����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//���˱��ɹ���ʱ�򣬷���TX_BODYʱ������COM_ENTITY�ڵ�
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		//COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		
		//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out CalculateCont2.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

//		String mInFilePath = "H:/��·/ģ�⽨�б���/ccb_CCB000000000111111_P53819151_134728.xml";
//		String mOutFilePath = "H:/��·/ģ�⽨�б���/P53819151inSvc.xml";
	//	String mInFilePath = "C:\\Users\\Administrator\\Desktop\\356307_181_1010_in.xml";
		String mInFilePath = "C:\\Users\\Administrator\\Desktop\\ũ��.xml";
		String mOutFilePath = "C:\\Users\\Administrator\\Desktop\\���㱣�ղ�Ʒout.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new CalculateCont2(null).std2NoStd(mInXmlDoc);
	//	Document mOutXmlDoc = new CalculateCont2(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");

	}
}