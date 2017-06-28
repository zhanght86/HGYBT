package com.sinosoft.midplat.newccb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.utility.ElementLis;

public class FileUtil
{
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String typeCode = null;
	private String secNodeId = null;
	private String rmtSecNodeId = null;
	/**�ļ���*/
	private String fileName = null;
	/**�ļ�·��*/
	private String filePath = null;
	private Element cBusiConfRoot = null;
	protected Element cThisBusiConf = null;
	/**���ܺ�ķǱ�׼����*/
	private Document returnNoStd = null;
	/**ת����ı�׼����*/
	private Document cInXmlDoc = null;
	private java.util.List<Element> Detail = new ArrayList<Element>();
	private Element cTransaction_Header = null;

	public FileUtil(Document pInXmlDoc) throws JDOMException{
		cLogger.info("into init FileUtil()..");
		System.out.println("����FileUtil�ı��ģ�");
		JdomUtil.print(pInXmlDoc);
		if (!pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_TX_CODE").equals("P53816107")){   
			//����Head�ڵ�
			cTransaction_Header = (Element) pInXmlDoc.getRootElement().getChild("Head").clone();
		}
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
		secNodeId = TX_HEADER.getChildTextTrim("LocalID");
		rmtSecNodeId = TX_HEADER.getChildTextTrim("remoteID");
		//��ȡ��������ȡ��(����)
		if (typeCode.equals("P53816107")){
			cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("AgIns_BtchBag_Nm") + ".xml";

			filePath = cThisBusiConf.getChildText("ccbLocalDir");
			if (!filePath.endsWith("/")){
				filePath += '/';
			}
		}else{
			Element mTranNo = new Element("TranNo");
			mTranNo.setText(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
			cTransaction_Header.addContent(mTranNo);
			//���ܱ��ĵ��ļ���
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_NAME");
			// ���ܱ���·��
			filePath = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_PATH");
			if (!filePath.endsWith("/")){
				filePath += '/';
			}
		}
		cLogger.info("out init FileUtil()..");
	}
	// �������з��͹����ļ����ļ������Ҷ�ȡ�ļ������ݲ�ͬ����ת���ɲ�ͬ��׼���ġ�
	public Document fileSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileSecurity()...");
		try{
			if (typeCode.equals("P53817103") || typeCode.equals("P53817104")){
				try{
					// ���ն���
					if(typeCode.equals("P53817103")){
						cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3005']");
					}
					// ��ȫ����
					if(typeCode.equals("P53817104")){
						cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1048']");
					}
				}catch (JDOMException e){
					e.printStackTrace();
				}
				boolean flag = true;
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath = " + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// ���ܣ����ؽڵ㣬�Զ˽ڵ㣬�����ļ����·�������ܺ������ļ�����·��
					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
					//�ļ�����
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
				}catch (Exception ex){
					ex.printStackTrace();
					cLogger.error("�����ļ�ʧ�ܣ�", ex);
					flag = false;
				}
				if(flag){
					cInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "�����ļ��ɹ�");
				}else{
					cInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, "�����ļ�ʧ��");
				}
			}else if (typeCode.equals("P53817105")){
				// �������ж˵�֤��Ϣ
				try{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3006']");
				}catch (JDOMException e){
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// ����
					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					// ��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
				}catch (Exception ex){
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try{
					// ��װ���ı�׼����
					cInXmlDoc = docuInfoTo(returnNoStd);
				}catch (Exception ex){
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
		}catch (Exception ex){
			throw new MidplatException("�ļ����ܹ��̱���");
		}
		cLogger.info("Out FileUtil.fileSecurity()...");
		return cInXmlDoc;
	}
	// �Է��������ļ����м��ܣ�ʹ�û�ȡ��������ȡ��
	public Void fileEncrpSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileEncrpSecurity()...");
		try{
			if (typeCode.equals("P53816107")){// ��ȡ��������ȡ��
				try{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
				}catch (JDOMException e){
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("���ܺ���·��:FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// ����
					cLogger.info("����ǰ���ļ����·����" + mFilePath + fileName);
					cLogger.info("���ܺ���ļ����·����" + filePath + fileName);
					cLogger.info("secNodeId��" + secNodeId + "       rmtSecNodeId:" + rmtSecNodeId);
					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, mFilePath + fileName, filePath + fileName);
				}catch (Exception ex){
					ex.printStackTrace();
					cLogger.error("���ܱ��������ļ�����", ex);
					throw new MidplatException("���ܱ��������ļ�����");
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
			throw new MidplatException("�ļ����ܹ��̱���");
		}
		cLogger.info("Out FileUtil.fileEncrpSecurity()...");
		return null;
	}
	// ��֤����
	@SuppressWarnings("unchecked")
	private Document docuInfoTo(Document returnNoStd){
		Document tInXmlDoc = null;
		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		// ���ɱ�׼����ͷ
		Date tCurDate = new Date();
		new ElementLis("TranDate", String.valueOf(DateUtil.get8Date(tCurDate)), cTransaction_Header);
		new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml����
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);
		if (Detail_List != null){// �������ļ�Ϊ�յ�ʱ����û��Detail_List�ڵ��
			try{
				Detail = Detail_List.getChildren("Detail");
				int size = Detail.size();
				ElementLis Count = new ElementLis("Count", Body);
				Count.setText(String.valueOf(size));
				/*
				 * ��ʽ <Body> <Count>1</Count> <Detail>
				 * <CardType>0101141</CardType> <CardNo>010114100001474</CardNo>
				 * <CardState>4</CardState> <OtherNo>2014112102000001</OtherNo>
				 * <AgentCom>0390101205</AgentCom> <TellerNo>02670</TellerNo>
				 * <TranNo>20495730</TranNo> </Detail> </Body>
				 */
				for (int i = 0; i < size; i++)
				{
					Element tempEle = Detail.get(i);
					ElementLis listDetail = new ElementLis("Detail", Body);
					// �ؿ�����
					new ElementLis("CardType", tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0,5), listDetail);
					// �ؿ�ӡˢ��
					new ElementLis("CardNo",NumberUtil.no13To15(tempEle.getChildTextTrim("Ins_IBVoch_ID")), listDetail);
					// �ؿ�״̬
					ElementLis CardState = new ElementLis("CardState", tempEle.getChildTextTrim("IpOpR_Crcl_StCd"), listDetail);
					if (CardState.getText().equals("01"))
					{// δʹ��
						CardState.setText("9");
					}
					if (CardState.getText().equals("03"))
					{// ��ʹ��---���N
						CardState.setText("4");
					}
					if (CardState.getText().equals("04"))
					{// ������---���U
						CardState.setText("6");
					}
					// ��֤������
					new ElementLis("OtherNo", listDetail);
					// ���ʻ�����
					new ElementLis("AgentCom", listDetail);

					new ElementLis("TellerNo", listDetail);
					// ���з�������ˮ��
					new ElementLis("TranNo", tempEle.getChildTextTrim("RqPtTcNum"), listDetail);
				}
				tInXmlDoc = new Document(TranData);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			// �����ļ�����Ϊ��
			tInXmlDoc = new Document(TranData);
		}
		return tInXmlDoc;
	}
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException{
		cLogger.info("Into Balance.getLocalFile()...");
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")){
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);

		InputStream mIns = null;
		try{
			mIns = new FileInputStream(mPathName);
		}catch (IOException ex){
			// δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}

}
