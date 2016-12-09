//�������ն��˱�ȫ��
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
import com.sinosoft.utility.ExeSQL;

public class DailyBQBlc_20150305 extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sFuncflag = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	
	
	public DailyBQBlc_20150305(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into DailyBQBlc.noStd2Std()...");
		
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
		
		Document mStdXml = 
			DailyBQBlcInXsl.newInstance().getCache().transform(pNoStdXml);

		//ȡ��׼��������Detail���BusiType
		List<Element> tDetail = XPath.selectNodes(mStdXml.getRootElement(), "/TranData/Body/Detail");

		//ȡ����������Detail���TranNo
		List<Element> tDetail2 = XPath.selectNodes(pNoStdXml.getRootElement(), "//TX/TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail");
		for(int i=0;i<tDetail2.size();i++){ 
			String  tranno  =  tDetail2.get(i).getChildText("Ins_Co_Jrnl_No");
			//�ý�����ˮ�Ŵ�Tranlog���в��Ҷ�Ӧ��Ͷ������
			String getFuncflagSQL = new StringBuilder("select funcflag from tranlog where tranno = '").append(tranno).append("'")
			.append(" and rcode='0'").toString();
			sFuncflag = new ExeSQL().getOneValue(getFuncflagSQL);
			//���ڸ��� "1037"
			if (sFuncflag.equals("1011")) {
				tDetail.get(i).getChild("BusiType").setText("09");
			}else
			//�˱� "1017"
			if (sFuncflag.equals("1017")) {
				tDetail.get(i).getChild("BusiType").setText("10");
			}else
			//���� "1034"
			if (sFuncflag.equals("1034")) {
				tDetail.get(i).getChild("BusiType").setText("11");
			}else {
				tDetail.get(i).getChild("BusiType").setText("12");
			}
		}
		cLogger.info("Out DailyBQBlc.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into DailyBQBlc.std2NoStd()...");
		
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			DailyBQBlcOutXsl.newInstance().getCache().transform(pStdXml);

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
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText("���׳ɹ���");
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
		}
		
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out DailyBQBlc.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
	System.out.println("����ʼ��");

	String mInFilePath = "H:/��·/����/2340715_71_1048_in.xml";
	String mOutFilePath = "H:/��·/2340715_71_1048_InSvc.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

//	Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new DailyBQBlc_20150305(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("�ɹ�������");
}
	
}