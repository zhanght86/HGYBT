package com.sinosoft.midplat.newccb.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ElementLis;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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
	private Document cNoStdXml;
	private Element cTransaction_Header = null;

	public FileUtil(Document pInXmlDoc) throws JDOMException
	{
		cLogger.info("into init FileUtil()..");
		System.out.println("����FileUtil�ı��ģ�");
		JdomUtil.print(pInXmlDoc);
		cNoStdXml = pInXmlDoc;

		if (!pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_TX_CODE").equals("P53816107"))
		{
			cTransaction_Header = (Element) pInXmlDoc.getRootElement().getChild("Head").clone();
		}
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
		secNodeId = TX_HEADER.getChildTextTrim("LocalID");
		rmtSecNodeId = TX_HEADER.getChildTextTrim("remoteID");

		//��ȡ��������ȡ��(����)
		if (typeCode.equals("P53816107"))
		{
			cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
			Date tCurDate = new Date();
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("AgIns_BtchBag_Nm") + ".xml";

			filePath = cThisBusiConf.getChildText("ccbLocalDir");
			if (!filePath.endsWith("/"))
			{
				filePath += '/';
			}
		}
		else
		{
			Element mTranNo = new Element("TranNo");
			mTranNo.setText(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
			cTransaction_Header.addContent(mTranNo);
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_NAME");
			// ���ܱ���·��
			filePath = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_PATH");
			if (!filePath.endsWith("/"))
			{
				filePath += '/';
			}
		}

		cLogger.info("out init FileUtil()..");
	}

	// �������з��͹����ļ����ļ������Ҷ�ȡ�ļ������ݲ�ͬ����ת���ɲ�ͬ��׼���ġ�
	public Document fileSecurity() throws MidplatException
	{
		//Into FileUtil.fileSecurity()...
		cLogger.info("Into FileUtil.fileSecurity()...");
		try
		{
			
			if (typeCode.equals("P53817103"))
			{// ���ն���
				try
				{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3005']");
				}
				catch (JDOMException e)
				{
					e.printStackTrace();
				}
				try
				{
					//
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");

					// ���ܣ����ؽڵ㣬�Զ˽ڵ㣬�����ļ����·�������ܺ������ļ�����·��
					//����ǰ�Ķ����ļ����·����/home/ap/fserver2/rcv/RcnclFile_20161231_010079_510000000_001.xml
					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
					//���ܺ�Ķ����ļ����·����/ybttest/newccb/zw/RcnclFile_20161231_010079_510000000_001.xml
					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
					
					//�ļ�����
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					if (ttBatIns == null)
					{
						cLogger.info("===============null");
					}
					else
					{
						cLogger.info(ttBatIns);
					}
					// ��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try
				{
					// ��װ���ı�׼����
					cLogger.info("��ʼ���������ļ���ı��ģ�����װ�ɱ�׼���ģ�");
					cInXmlDoc = balanceTo(returnNoStd);
					cLogger.info("�������ĵı�׼���˱���:");
					JdomUtil.print(cInXmlDoc);
				}
				catch (Exception ex)
				{
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
			else if (typeCode.equals("P53817104"))
			{// ��ȫ����
				try
				{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1048']");
				}
				catch (JDOMException e)
				{
					e.printStackTrace();
				}
				try
				{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");

					// ���ܣ����ؽڵ㣬�Զ˽ڵ㣬�����ļ����·�������ܺ������ļ�����·��
					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);

					// ��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try
				{
					// System.out.println("---------------------------------");
					// ��װ���ı�׼����
					cInXmlDoc = balanceTo(returnNoStd);
					// JdomUtil.print(cInXmlDoc);
				}
				catch (Exception ex)
				{
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
			else if (typeCode.equals("P53817105"))
			{// �������ж˵�֤��Ϣ
				try
				{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3006']");
				}
				catch (JDOMException e)
				{
					e.printStackTrace();
				}
				try
				{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// String mFilePath = new StringBuilder(SysInfo.cHome)
					// ����
					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					// ��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
					// JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try
				{
					// ��װ���ı�׼����
					cInXmlDoc = docuInfoTo(returnNoStd);
				}
				catch (Exception ex)
				{
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
		}
		catch (Exception ex)
		{
			throw new MidplatException("�ļ����ܹ��̱���");
		}

		cLogger.info("Out FileUtil.fileSecurity()...");
		return cInXmlDoc;
	}

	// �Է��������ļ����м��ܣ�ʹ�û�ȡ��������ȡ��
	// public Document fileEncrpSecurity() throws MidplatException{
	public Void fileEncrpSecurity() throws MidplatException
	{
		cLogger.info("Into FileUtil.fileEncrpSecurity()...");
		try
		{

			System.out.println("ʲô���:" + typeCode);
			if (typeCode.equals("P53816107"))
			{// ��ȡ��������ȡ��
				try
				{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
				}
				catch (JDOMException e)
				{
					e.printStackTrace();
				}
				try
				{
					cLogger.info("FileName = " + fileName);
					cLogger.info("���ܺ���·��:FilePath==" + filePath);
					// ���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					
					// String mFilePath = new StringBuilder(SysInfo.cHome)
					// ����
					cLogger.info("����ǰ���ļ����·����" + mFilePath + fileName);
					cLogger.info("���ܺ���ļ����·����" + filePath + fileName);
					cLogger.info("secNodeId��" + secNodeId + "       rmtSecNodeId:" + rmtSecNodeId);
					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, mFilePath + fileName, filePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					// InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					// ��ȡ�Ǳ�׼����
					// returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
					// JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					cLogger.error("���ܱ��������ļ�����", ex);
					throw new MidplatException("���ܱ��������ļ�����");
				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new MidplatException("�ļ����ܹ��̱���");
		}

		cLogger.info("Out FileUtil.fileEncrpSecurity()...");
		// return cInXmlDoc;
		return null;
	}

	// ��֤����
	private Document docuInfoTo(Document returnNoStd)
	{
		Document tInXmlDoc = null;

		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element COM_ENTITY = cNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		// ���ɱ�׼����ͷ
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", String.valueOf(DateUtil.get8Date(tCurDate)), cTransaction_Header);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml����
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);

		if (Detail_List != null)
		{// �������ļ�Ϊ�յ�ʱ����û��Detail_List�ڵ��

			try
			{
				Detail = Detail_List.getChildren("Detail");
				// Element APP_ENTITY =
				// returnNoStd.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
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
					ElementLis CardType = new ElementLis("CardType", tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0, 7), listDetail);
					// �ؿ�ӡˢ��
					ElementLis CardNo = new ElementLis("CardNo", tempEle.getChildTextTrim("Ins_IBVoch_ID"), listDetail);
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
					ElementLis OtherNo = new ElementLis("OtherNo", listDetail);
					// ���ʻ�����
					ElementLis AgentCom = new ElementLis("AgentCom", listDetail);

					ElementLis TellerNo = new ElementLis("TellerNo", listDetail);
					// ���з�������ˮ��
					ElementLis TranNo = new ElementLis("TranNo", tempEle.getChildTextTrim("RqPtTcNum"), listDetail);
				}
				tInXmlDoc = new Document(TranData);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		else
		{// �����ļ�����Ϊ��
			tInXmlDoc = new Document(TranData);
		}

		return tInXmlDoc;
	}

	// ���ն��˺ͱ�ȫ����
	private Document balanceTo(Document returnNoStd)
	{
		// JdomUtil.print(returnNoStd);
		cLogger.info("�����ģ�");
		JdomUtil.print(cNoStdXml);
		Document tInXmlDoc = null;

		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");

		// ��װ��׼����

		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", String.valueOf(DateUtil.get8Date(tCurDate)), cTransaction_Header);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml����
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);

		if (Detail_List != null)
		{// �������ļ�Ϊ�յ�ʱ����û��Detail_List�ڵ��

			try
			{
				Detail = Detail_List.getChildren("Detail");
				Element mHead = returnNoStd.getRootElement().getChild("Head");
				int size = Detail.size();
				int cnt = 0;
				cLogger.info("����" + size + "�ʶ��ˣ�");
				String typecode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");

				if (size > 0)
				{
					for (int i = 0; i < size; i++)
					{
						ElementLis listDetail = new ElementLis("Detail", Body);
						Element tempEle = Detail.get(i);
						// ElementLis check_trans = new
						// ElementLis("check-trans",check_trans_list);
						// ���ն���
						if (typecode.equals("P53817103"))
						{
							// ElementLis Count = new ElementLis("Count",Body);
							// ElementLis sumPrem = new ElementLis("Prem",Body);
							cLogger.info("========����������˱���ƴװ=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819152"))
							{// �µ��ɷ�
								BusiType.setText("01");
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819156"))
							{// ���ڽɷ�
								BusiType.setText("11");
							}
							cnt++;
							ElementLis ContNo = new ElementLis("ContNo", tempEle.getChildTextTrim("InsPolcy_No"), listDetail);
							ElementLis Prem = new ElementLis("Prem", tempEle.getChildTextTrim("TxnAmt"), listDetail);
							ElementLis AgentCom = new ElementLis("AgentCom", tempEle.getChildTextTrim("CCBIns_ID"), listDetail);
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", "", listDetail);
							ElementLis AppntName = new ElementLis("AppntName", "", listDetail);
							ElementLis InsuredName = new ElementLis("InsuredName", "", listDetail);
							ElementLis TranDate = new ElementLis("TranDate", tempEle.getChildTextTrim("Txn_Dt"), listDetail);
							// mClientIpEle.setText("3005");
						}
						// ��ȫ����
						if (typecode.equals("P53817104"))
						{
							cLogger.info("========���뱣ȫ���˱���ƴװ=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819192"))
							{// ���ڸ���
								BusiType.setText("09");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819144"))
							{// �˱�
								BusiType.setText("10");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819161"))
							{// �޸ı���������Ϣ,���Ǻ��Ĳ������˴�����
								BusiType.setText("07");
								cnt++;
							}
							ElementLis TranNo = new ElementLis("TranNo", tempEle.getChildTextTrim("Ins_Co_Jrnl_No"), listDetail);
							ElementLis NodeNo = new ElementLis("NodeNo", tempEle.getChildTextTrim("CCBIns_ID"), listDetail);
							ElementLis ContNo = new ElementLis("ContNo", tempEle.getChildTextTrim("InsPolcy_No"), listDetail);
							ElementLis EdorNo = new ElementLis("EdorNo", tempEle.getChildTextTrim("InsPolcy_Vchr_No"), listDetail);
							ElementLis AccNo = new ElementLis("AccNo", listDetail);
							ElementLis AccName = new ElementLis("AccName", "", listDetail);
							// ��ʵ��ȫ����Ҫ�����ֻ����saveDetail��ʱ��ȡֵ�����Ը����ڵ㣬��Ȼ��ָ���쳣
							ElementLis Prem = new ElementLis("Prem", "0", listDetail);
							// ��ʵ��ȫ����Ҫ�����ֻ����saveDetail��ʱ��ȡֵ�����Ը����ڵ㣬��Ȼ��ָ���쳣
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", "", listDetail);
							ElementLis TranDate = new ElementLis("TranDate", tempEle.getChildTextTrim("Txn_Dt"), listDetail);

						}

					}
				}
				tInXmlDoc = new Document(TranData);
				if (typecode.equals("P53817103"))
				{
					cLogger.info("========�����������Count��Prem����ƴװ=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					long mSumPrem = 0;
					for (int i = 0; i < tDetail.size(); i++)
					{
						long tPremFen = NumberUtil.yuanToFen(((Element) tDetail.get(i)).getChildText("Prem"));
						System.out.println("���˱��ѣ�" + tPremFen);
						tDetail.get(i).getChild("Prem").setText(String.valueOf(tPremFen));
						mSumPrem += tPremFen;
					}
					cLogger.info("====�����ܱ���===" + mSumPrem);
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
						ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
						tBody.getChild("Prem").setText(String.valueOf(mSumPrem));
						tBody.getChild("Count").setText(String.valueOf(cnt));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (typecode.equals("P53817104"))
				{
					cLogger.info("========���뱣ȫ����Count��Prem����ƴװ=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
						ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
																			// ����contblcdtl�Ǻ�ȡprem�����Բ���û��
						tBody.getChild("Prem").setText("0");
						tBody.getChild("Count").setText(String.valueOf(cnt));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

		}
		else
		{// �����ļ�����Ϊ��
			tInXmlDoc = new Document(TranData);
			ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
			ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
			tInXmlDoc.getRootElement().getChild("Body").getChild("Prem").setText("0");
			tInXmlDoc.getRootElement().getChild("Body").getChild("Count").setText("0");
		}

		cLogger.info("��ҿ���,��׼����զ����");
		JdomUtil.print(tInXmlDoc);
		return tInXmlDoc;
	}

	private InputStream getLocalFile(String pDir, String pName) throws MidplatException
	{
		cLogger.info("Into Balance.getLocalFile()...");

		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/"))
		{
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);

		InputStream mIns = null;
		try
		{
			mIns = new FileInputStream(mPathName);
		}
		catch (IOException ex)
		{
			// δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}

		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}

}
