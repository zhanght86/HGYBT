//�������㽻��
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
/**
 * �������㽻��
 * @author yuantongxin
 */
public class NewCont extends XmlSimpFormat {
	private Element cTransaction_Header = null;//���ױ���ͷ
	private String mSYS_RECV_TIME = null;//�������ʱ��
	private String mSYS_RESP_TIME = null;//������Ӧʱ��
	private String tranNo = null;//������ˮ��
	private String tranDate = null;//���չ�˾��������
	private String sysTxCode = null;//���״���
	private String sAginsPkgId = null;//�������ײͱ��
	private String sPayIntv = null;//���ѽ��ɷ�ʽ����
	private String sMult = null;//����
	private String sLot = null;//�������(�������ٷֱ�)
	private String sPayCycCod = null;
	private String sRelationShip = null;//��ϵ
	private Element oldTxHeader = null;//�ɽ��ױ���ͷ
	private Element oldComEntity = null;//�ɹ�����
	private Element oldAppEntity = null;//��Ӧ����
	
	/**����ķǱ�׼����*/
	private Document noStdDoc = null;
	
	/**
	 * 
	 * @param pThisConf
	 */
	public NewCont(Element pThisConf) {
		super(pThisConf);
	}
	
	/**
	 * �Ǳ�׼����ת��׼����
	 * @param pNoStdXml �Ǳ�׼����
	 */
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();//����TX_HEADER
		//�˴�����һ����������APP_EITITY�����Ϣ����֯���ر���ʱ���õ�
		//����TX_BODY/ENTITY/APP_ENTITY
		oldAppEntity =
			(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();;
		//����APP_ENTITY/Busi_List/
		sAginsPkgId	=oldAppEntity.getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
		System.out.println(sAginsPkgId);
		//		JdomUtil.print(cTransaction_Header);
		
		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		//���ѽɷѷ�ʽ����
		sPayIntv =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_MtdCd");
		//Ͷ������
	//	sMult =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("Ins_Cps");
		//������� lilu20150305 ҵ����������������100%��1.0000������Ҫת��double*100��ȡ���������ģ�����LotΪ100��������
		
		if(!"0".equals(pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Benf_Num"))){
			List<Element> tBnf=pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Benf_List").getChildren("Benf_Detail");
			for (int i=0 ; i<tBnf.size() ; i++) {
				sLot =  tBnf.get(i).getChildText("Bnft_Pct");
				double dLot=Double.parseDouble(sLot);
				int iLot=(int) (dLot*100);
				sLot = String.valueOf(iLot);
				tBnf.get(i).getChild("Bnft_Pct").setText(sLot);
			}
		}
		//���ѽ������ڴ���
		sPayCycCod =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_Cyc_Cd");
		
		//���ƷǱ�׼����
		noStdDoc = (Document)pNoStdXml.clone();
		
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		//lilu20150305�����ϵ���룬���з���������Ͷ���˵Ĺ�ϵ������ҪͶ�����Ǳ����˵Ĺ�ϵ��Ŀǰ����ֻ��00���ˣ�01��ĸ��02��ż��03��Ů��04���05�໤��06������07����������Ա
		sRelationShip = mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChildText("RelaToInsured");
		
		System.out.println("================"+pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Plchd_And_Rcgn_ReTpCd"));
		System.out.println("================"+sRelationShip);
		if("01".equals(sRelationShip)){
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("03");
		}
		if("03".equals(sRelationShip)){
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("01");
		}
		System.out.println("++++++++++++++++"+sRelationShip);
		
		//�к����Ӷ�ӯ����ȫ���գ������ͣ���������������
		//����
		List mRiskList = mStdXml.getRootElement().getChild("Body").getChildren("Risk");
		String mInsuYearFlag = "";
		String mInsuYear = "";
		for(int i=0;i<mRiskList.size();i++){
			Element mRiskEle = (Element) mRiskList.get(i);
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			if("131204".equals(mRiskCode) || "131205".equals(mRiskCode) || "131301".equals(mRiskCode) || "131302".equals(mRiskCode) ||
					"221206".equals(mRiskCode) || "001301".equals(mRiskCode) || "231302".equals(mRiskCode)){
				mInsuYearFlag = mRiskEle.getChildText("InsuYearFlag");
				mInsuYear = mRiskEle.getChildText("InsuYear");
			}
		}
		
		for(int i=0;i<mRiskList.size();i++){
			Element mRiskEle = (Element) mRiskList.get(i);
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			if("145201".equals(mRiskCode)){
				mRiskEle.getChild("InsuYearFlag").setText(mInsuYearFlag);
				mRiskEle.getChild("InsuYear").setText(mInsuYear);
			}
		}
			
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");

		//�������ɹ�,����ԭʼ����
		String funcFlag = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
		if(StringUtils.isNotBlank(funcFlag) && "0".equals(funcFlag))
		{
			//����Ǳ�׼����
			saveNoStdDoc(pStdXml);
		}
		
		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);
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
		
		
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText(Desc));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			//COM_ENTITY�ڵ���������ˮ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
			
			//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
			//���뱣�մ����ײͱ��
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAginsPkgId);
			//���뱣�ѽɷѷ�ʽ����
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPrem_PyF_MtdCd").setText(sPayIntv);
			//���뱣�ѽɷ����ڴ���
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPrem_PyF_Cyc_Cd").setText(sPayCycCod);
			//����Ͷ������
		//	mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_Cps").setText(sMult);

			
			
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	/**
	 * 
	 * saveNoStdDoc ��׼����
	 *
	 * @param stdDoc
	 */
	private void saveNoStdDoc(Document stdDoc)
	{

		LKPolicyXML policyXML = new LKPolicyXML();
		
		//��׼���ĸ��ڵ�
		Element stdDocRoot = stdDoc.getRootElement();
		
		
		//���ñ�����
		policyXML.setContNo(stdDocRoot.getChild(Body).getChildText(ContNo));
		//Ͷ��Ͷ������
		policyXML.setProposalPrtNo(stdDocRoot.getChild(Body).getChildText(ProposalPrtNo));
		
		//�������л�����
		policyXML.setTranCom("03");
		
		//���ý�������
		policyXML.setTranDate(DateUtil.parseDate(tranDate, "yyyyMMdd"));
		
		//���ñ�����Ϣ
		policyXML.setXmlContent(JdomUtil.toBytes(noStdDoc));
		
		//������������޸�����ʱ��
		policyXML.setMakeDate(new Date());
		policyXML.setMakeTime(DateUtil.getCur8Time());
		policyXML.setModifyDate(new Date());
		policyXML.setModifyTime(DateUtil.getCur8Time());

		LKPolicyXMLDao dao = new LKPolicyXMLDao();
		try
		{
			//���뱣��������Ϣ
			boolean flag = dao.insert(policyXML);
			if(!flag)
			{
				cLogger.error("����Ǳ�׼���ĵ����ݿ�ʧ��!");
			}
			else
			{
				cLogger.info("����Ǳ�׼���ĵ����ݿ�ɹ�!");
			}
		}
		catch (Exception e)
		{
			cLogger.error("����Ǳ�׼���ĵ����ݿ�ʧ��:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");


		String mInFilePath = "D:/task/20161201/newccb/P53819113in_noStd.xml";
		String mOutFilePath = "D:/task/20161201/newccb/P53819113in_Std.xml";



		Document mInXmlDoc = JdomUtil.build(mInFilePath);
		
//		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
//
		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");

	}
}