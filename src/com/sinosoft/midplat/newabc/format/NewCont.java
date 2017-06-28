package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NewCont extends XmlSimpFormat {
	private Element header=null;
	@SuppressWarnings("unused")
	private String riskcode=null;
	
	public NewCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into newABCNewCont.noStd2Std()...");
		
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		riskcode=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChild("Risks").getChildText("RiskCode");
		
		//����������
		List<Element> bnfs=dealBnf(pNoStdXml);
		JdomUtil.print(pNoStdXml);
		Document mStdXml = NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		
		mStdXml.getRootElement().getChild("Body").addContent(bnfs);
		cLogger.info("Out newABCNewCont.noStd2Std()!");
		
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into newABCNewCont.std2NoStd()...");
		
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);
		//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
		Element  RetCode=new Element("RetCode");
		Element  RetMsg = new Element("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("���׳ɹ�=========");
		   RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("����ʧ��=========ʧ����Ϣ:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		if (ttFlag.getValue().equals("2")){
			cLogger.info("ת��ʵʱ����=========:"+RetMsg.getText());
			RetCode.setText("009990");
		}
		Element insuSerial=new Element("InsuSerial");
		header.addContent(insuSerial);
		header.addContent(RetCode);
		header.addContent(RetMsg);
		mNoStdXml.getRootElement().addContent(header);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out newABCNewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	/**
	 *  
	 *  ����������
	 *   <BeneficType>N</BeneficType>
         <Type>1</Type>
         <Grade>1</Grade>
         <Name>����</Name>
         <Sex>0</Sex>
         <Birthday>19850917</Birthday>
         <IDType>0</IDType>
         <IDNo>220523850917341</IDNo>
         <IdExpDate>20200916</IdExpDate>
         <RelaToInsured>02</RelaToInsured>
         <Lot>100</Lot>
	 *  @param doc �Ǳ�׼���뱨��
	 * @return
	 */
	private List<Element> dealBnf(Document doc){
		//�������б�
		List<Element> bnfs=new ArrayList<Element>();
		//�Ǳ�׼���뱨���������б�ڵ�
		Element noStdBnf=doc.getRootElement().getChild("App").getChild("Req").getChild("Bnfs");
		//�����˸���
		String count=noStdBnf.getChild("Count").getText();
		int countInt=Integer.parseInt(count);
		cLogger.info("�����˸���:"+countInt);
		for(int i=1;i<=countInt;i++){
			//������
			Element bnf=new Element("Bnf");
			//����������
			Element BeneficType=new Element("BeneficType");
			BeneficType.setText("N");
			//���������
			Element Type=new Element("Type");
			Type.setText(noStdBnf.getChild("Type"+i).getText());
			//����˳��
			Element Grade=new Element("Grade");
			Grade.setText(noStdBnf.getChild("Sequence"+i).getText());
			//����������
			Element Name=new Element("Name");
			Name.setText(noStdBnf.getChild("Name"+i).getText());
			//�������Ա�
			Element Sex=new Element("Sex");
			Sex.setText(noStdBnf.getChild("Sex"+i).getText());
			//�����˳�������
			Element Birthday=new Element("Birthday");
			Birthday.setText(noStdBnf.getChild("Birthday"+i).getText());
			//������֤������
			Element IDType=new Element("IDType");
			IDType.setText(returnIdType(noStdBnf.getChild("IDKind"+i).getText()));
			//������֤������
			Element IDNo=new Element("IDNo");
			IDNo.setText(noStdBnf.getChild("IDCode"+i).getText());
			//���֤֤����Ч��
			Element IdExpDate=new Element("IdExpDate");
			String  noStdIdExpDate=noStdBnf.getChild("InvalidDate"+i).getText();
			if(noStdIdExpDate.equals("20991231")){
				IdExpDate.setText("99990101");
			}else{
				IdExpDate.setText(noStdIdExpDate);
			}
			//�������뱻���˹�ϵ
			Element RelaToInsured=new Element("RelaToInsured");
			RelaToInsured.setText(retion(noStdBnf.getChild("RelationToInsured"+i).getText()));;
			//�������
			Element Lot=new Element("Lot");
			Lot.setText(noStdBnf.getChild("Prop"+i).getText());
			//�����˽ڵ����:���������͡��������������˳���������������Ա� ���������ڡ�֤�����͡�֤�����롢���֤֤����Ч�ڡ��뱻���˹�ϵ��������� �ӽڵ�
			bnf.addContent(BeneficType);
			bnf.addContent(Type);
			bnf.addContent(Grade);
			bnf.addContent(Name);
			bnf.addContent(Sex);
			bnf.addContent(Birthday);
			bnf.addContent(IDType);
			bnf.addContent(IDNo);
			bnf.addContent(IdExpDate);
			bnf.addContent(RelaToInsured);
			bnf.addContent(Lot);
			//�������б�ڵ�����������ӽڵ�
			bnfs.add(bnf);
		}
		//�����������б�ڵ�
		return bnfs;
	}
	
	/**
	 * @Title: returnIdType
	 * @Description: ����ӳ����֤������
	 * @param noStdType �Ǳ�׼����֤������
	 * @return ����֤������
	 * @return String 
	 * @throws
	 */
	private String returnIdType(String noStdType){
		if("110001".equals(noStdType))return "0";//�������֤
		if("110002".equals(noStdType))return "0";//�غž������֤
		if("110003".equals(noStdType))return "0";//��ʱ�������֤ 
		if("110004".equals(noStdType))return "0";//�غ���ʱ�������֤
		if("110005".equals(noStdType))return "4";//���ڲ�
		if("110006".equals(noStdType))return "4";//�غŻ��ڲ�
		if("110007".equals(noStdType))return "2";
		if("110008".equals(noStdType))return "2";
		if("110009".equals(noStdType))return "D";
		if("110010".equals(noStdType))return "D";
		if("110011".equals(noStdType))return "99";//���ݸɲ�����֤
		if("110012".equals(noStdType))return "99";//�غ����ݸɲ�����֤
		if("110013".equals(noStdType))return "99";//��������֤
		if("110014".equals(noStdType))return "99";//�غž�������֤
		if("110015".equals(noStdType))return "99";//��ְ�ɲ�����֤
		if("110016".equals(noStdType))return "99";//�غ���ְ�ɲ�����֤
		if("110017".equals(noStdType))return "99";//����ԺУѧԱ֤
		if("110018".equals(noStdType))return "99";//�غž���ԺУѧԱ֤
		if("110019".equals(noStdType))return "F";//�۰ľ��������ڵ�ͨ��֤
		if("110020".equals(noStdType))return "F";//�غŸ۰ľ��������ڵ�ͨ��֤
		if("110021".equals(noStdType))return "F";//̨�����������½ͨ��֤
		if("110022".equals(noStdType))return "F";//�غ�̨�����������½ͨ��֤
		if("110023".equals(noStdType))return "1";//�л����񹲺͹�����
		if("110024".equals(noStdType))return "1";//�غ��л����񹲺͹�����
		if("110025".equals(noStdType))return "1";//�������
		if("110026".equals(noStdType))return "1";//�غ��������
		if("110027".equals(noStdType))return "2";//����֤
		if("110028".equals(noStdType))return "2";//�غž���֤
		if("110029".equals(noStdType))return "99";//��ְ�ɲ�֤
		if("110030".equals(noStdType))return "99";//�غ���ְ�ɲ�֤
		if("110031".equals(noStdType))return "D";//����֤
		if("110032".equals(noStdType))return "D";//�غž���֤
		if("110033".equals(noStdType))return "2";//����ʿ��֤
		if("110034".equals(noStdType))return "2";//�غž���ʿ��֤
		if("110035".equals(noStdType))return "D";//�侯ʿ��֤
		if("110036".equals(noStdType))return "D";//�غ��侯ʿ��֤
		if("119998".equals(noStdType))return "99";//ϵͳʹ�õĸ���֤��ʶ���ʶ
		if("119999".equals(noStdType))return "99";//��������֤��ʶ���ʶ
		return "--";
	}
	private String retion(String noStdType){
		if("01".equals(noStdType))return  "00";//����		
		if("02".equals(noStdType))return  "02";//�ɷ�
		if("03".equals(noStdType))return  "02";//����
		if("04".equals(noStdType))return  "01";//����
		if("05".equals(noStdType))return  "01";//ĸ��
		if("06".equals(noStdType))return  "03";//����
		if("07".equals(noStdType))return  "03";//Ů��
		if("08".equals(noStdType))return  "04";//�游
		if("09".equals(noStdType))return  "04";//��ĸ
		if("10".equals(noStdType))return "04";//����
		if("11".equals(noStdType))return "04";//��Ů
		if("12".equals(noStdType))return "04";//���游
		if("13".equals(noStdType))return "04";//����ĸ
		if("14".equals(noStdType))return "04";//����
		if("15".equals(noStdType))return "04";//����Ů
		if("16".equals(noStdType))return "06";//���
		if("17".equals(noStdType))return "06";//���
		if("18".equals(noStdType))return "06";//�ܵ�
		if("19".equals(noStdType))return "06";//����
		if("20".equals(noStdType))return "06";//����
		if("21".equals(noStdType))return "06";//����
		if("22".equals(noStdType))return "06";//��ϱ
		if("23".equals(noStdType))return "06";//����
		if("24".equals(noStdType))return "06";//��ĸ
		if("25".equals(noStdType))return "06";//Ů��
		if("26".equals(noStdType))return "06";//��������
		if("27".equals(noStdType))return "06";//ͬ��
		if("28".equals(noStdType))return "06";//����
		if("29".equals(noStdType))return "09";//����
		if("30".equals(noStdType))return "06";//����
		return "--";
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "C:\\Users\\wangheng\\Desktop\\BYC.xml";
		mInFilePath="D:/task/20161118/midplat/ABC/����.xml";
		String mOutFilePath = "C:\\Users\\wangheng\\Desktop\\1119012_1271_25_out.xml";
		mOutFilePath="D:/task/20161118/midplat/ABC/";
//		String mInFilePath = "H:/956304_53_25_outSvc.xml";
//		String mOutFilePath = "E:/��ӮA.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

	//	Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}