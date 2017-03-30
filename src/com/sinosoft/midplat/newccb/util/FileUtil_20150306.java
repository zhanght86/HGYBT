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

public class FileUtil_20150306{
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String typeCode = null;
	private String secNodeId = null;
	private String rmtSecNodeId = null;
	private String fileName = null;
	private String filePath = null;
	private Element cBusiConfRoot = null;
//	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	private Document returnNoStd = null;//���ܺ�ķǱ�׼����
	private Document cInXmlDoc = null;//ת����ı�׼����
	private java.util.List<Element> Detail_List = new ArrayList<Element>(); 
	private Document cNoStdXml ;
	private Element cTransaction_Header = null;
	
	
	public FileUtil_20150306(Document pInXmlDoc) throws JDOMException{
		cLogger.info("into init FileUtil()..");
		System.out.println("�������е㱻��");
		JdomUtil.print(pInXmlDoc);
		if(!pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_TX_CODE").equals("P53816107")){
			cTransaction_Header=(Element) pInXmlDoc.getRootElement().getChild("Head").clone();
		}
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
		secNodeId = TX_HEADER.getChildTextTrim("LocalID");
		rmtSecNodeId = TX_HEADER.getChildTextTrim("remoteID");
		
		if(typeCode.equals("P53816107")){
			cThisBusiConf = (Element) XPath.selectSingleNode(
					cBusiConfRoot, "business[funcFlag='1043']");
			Date tCurDate = new Date();
			fileName = "P53816107"+"_"+DateUtil.get8Date(tCurDate)+"_"+"010072"+"_"+"00001"+".xml";
			//���ܱ���·��
//			filePath = "/home/ap/fserver2/snd/";
//			JdomUtil.print(cThisBusiConf);
			System.out.println("================");
			filePath = cThisBusiConf.getChildText("ccbLocalDir");
			if (!filePath.endsWith("/")) {
				filePath += '/';
			}
		} else{
			Element mTranNo = new Element("TranNo");
			mTranNo.setText(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
			cTransaction_Header.addContent(mTranNo);
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_NAME");
			//���ܱ���·��
			filePath = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_PATH");
//			if (!filePath.endsWith("/")) {
//				filePath += '/';
//			}
		}
		
		cLogger.info("out init FileUtil()..");
	}
	
	//�������з��͹����ļ����ļ������Ҷ�ȡ�ļ������ݲ�ͬ����ת���ɲ�ͬ��׼���ġ�
	public Document fileSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileSecurity()...");
		try{
//			if(typeCode.equals("P53818103")){//����ȡ��
//				try {
//					cThisBusiConf = (Element) XPath.selectSingleNode(
//							cBusiConfRoot, "business[funcFlag='887']");
//				} catch (JDOMException e) {
//					e.printStackTrace();
//				}
//				try{
//					cLogger.info("FileName = "+fileName);
//					cLogger.info("FilePath=="+filePath);
//					//���ܺ�ı���·��
//					String ttLocalDir = cThisBusiConf.getChildTextTrim("localDir");
//					//����
//					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, filePath, ttLocalDir);
//					//���ݽ��ܺ���·�������ƻ�ȡ������
//					//InputStream ttBatIns = getLocalFile(ttLocalDir, fileName);
//					//��ȡ�Ǳ�׼����
//					//Document returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
//				}catch(Exception ex){
//					cLogger.error("��ñ�׼���˱��ĳ���", ex);
//					throw new MidplatException("��ȡ���ܱ��ĳ���");
//				}
//				
//			}else if(typeCode.equals("P53818105")){//��������
//				try {
//					cThisBusiConf = (Element) XPath.selectSingleNode(
//							cBusiConfRoot, "business[funcFlag='888']");
//				} catch (JDOMException e) {
//					e.printStackTrace();
//				}
//				try{
//					cLogger.info("FileName = "+fileName);
//					cLogger.info("FilePath=="+filePath);
//					//���ܺ�ı���·��
//					String ttLocalDir = cThisBusiConf.getChildTextTrim("localDir");
//					//����
//					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath, ttLocalDir);
//					//���ݽ��ܺ���·�������ƻ�ȡ������
//					//InputStream ttBatIns = getLocalFile(ttLocalDir, fileName);
//					//��ȡ�Ǳ�׼����
//					//Document returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
//				}catch(Exception ex){
//					cLogger.error("��ñ�׼���˱��ĳ���", ex);
//					throw new MidplatException("��ȡ���ܱ��ĳ���");
//				}
//			}else
			if(typeCode.equals("P53817103")){//���ն���
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cBusiConfRoot, "business[funcFlag='3005']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
//					String mFilePath = new StringBuilder(SysInfo.cHome)
//					.append("msg/")
//					.append("002").append('/')
//					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/")).toString();
//					String mFilePath = "F:/ybt/";
					//���ܣ����ؽڵ㣬�Զ˽ڵ㣬�����ļ����·�������ܺ������ļ�����·��
					cLogger.info("����ǰ�Ķ����ļ����·����"+mFilePath+fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����"+filePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, mFilePath+fileName, filePath+fileName);
					//���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					if(ttBatIns == null){
						cLogger.info("===============null");
					}else{
						cLogger.info(ttBatIns);
					}
					//��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try{
//					System.out.println("---------------------------------");
					//��װ���ı�׼����
					cInXmlDoc = balanceTo(returnNoStd);
					cLogger.info("�������ĵı�׼���˱���:");
					JdomUtil.print(cInXmlDoc);
				}catch(Exception ex){
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}else	 if(typeCode.equals("P53817104")){//��ȫ����
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cBusiConfRoot, "business[funcFlag='1048']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
//					String mFilePath = new StringBuilder(SysInfo.cHome)
//					.append("msg/")
//					.append("002").append('/')
//					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/")).toString();
//					String mFilePath = "F:/ybt/";
					//���ܣ����ؽڵ㣬�Զ˽ڵ㣬�����ļ����·�������ܺ������ļ�����·��
					cLogger.info("����ǰ�Ķ����ļ����·����"+filePath+fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����"+mFilePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath+fileName, mFilePath+fileName);
					//���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
//					if(ttBatIns == null){
//						cLogger.info("===============null");
//					}else{
//						cLogger.info(ttBatIns);
//					}
					//��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try{
//					System.out.println("---------------------------------");
					//��װ���ı�׼����
					cInXmlDoc = balanceTo(returnNoStd);
//					JdomUtil.print(cInXmlDoc);
				}catch(Exception ex){
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}else if(typeCode.equals("P53817105")){//�������ж˵�֤��Ϣ
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cBusiConfRoot, "business[funcFlag='3006']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
//					String mFilePath = new StringBuilder(SysInfo.cHome)
//					.append("msg/")
//					.append("002").append('/')
//					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/")).toString();
//					String mFilePath = "F:/ybt/";
					//����
					cLogger.info("����ǰ�Ķ����ļ����·����"+filePath+fileName);
					cLogger.info("���ܺ�Ķ����ļ����·����"+mFilePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath+fileName, mFilePath+fileName);
					//���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					//��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
//					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try{
					//��װ���ı�׼����
					cInXmlDoc = docuInfoTo(returnNoStd);
				}catch(Exception ex){
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
		}catch(Exception ex){
			throw new MidplatException("�ļ����ܹ��̱���");
		}
		
		cLogger.info("Out FileUtil.fileSecurity()...");
		return cInXmlDoc;
	}
	
	//�Է��������ļ����м��ܣ�ʹ�û�ȡ��������ȡ��
//	public Document fileEncrpSecurity() throws MidplatException{
	public Void fileEncrpSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileEncrpSecurity()...");
		try{
				
			System.out.println("ʲô���:"+typeCode);
			 if(typeCode.equals("P53816107")){//��ȡ��������ȡ��
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cBusiConfRoot, "business[funcFlag='1043']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//���ܺ�ı���·��
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					String mFilePathccb = cThisBusiConf.getChildTextTrim("ccbLocalDir");
//					String mFilePath = new StringBuilder(SysInfo.cHome)
//					.append("msg/")
//					.append("002").append('/')
//					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/")).toString();
//					String mFilePath = "F:/ybt/";
					//����
					cLogger.info("����ǰ���ļ����·����"+mFilePath+fileName);
					cLogger.info("���ܺ���ļ����·����"+filePath+fileName);
					cLogger.info("secNodeId��"+secNodeId+"       rmtSecNodeId:"+rmtSecNodeId);
					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, mFilePath+fileName, filePath+fileName);
					//���ݽ��ܺ���·�������ƻ�ȡ������
//					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					//��ȡ�Ǳ�׼����
//					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
//					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("���ܱ��������ļ�����", ex);
					throw new MidplatException("���ܱ��������ļ�����");
				}
//				try{
//					//��װ���ı�׼����
//					cInXmlDoc = docuInfoTo(returnNoStd);
//				}catch(Exception ex){
//					throw new MidplatException("��ȡ��׼���ĳ���");
//				}
			}
		}catch(Exception ex){
			throw new MidplatException("�ļ����ܹ��̱���");
		}
		
		cLogger.info("Out FileUtil.fileEncrpSecurity()...");
//		return cInXmlDoc;
		return null;
	}
	
	//��֤����
	private Document docuInfoTo(Document returnNoStd){
		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element COM_ENTITY = cNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		Detail_List = returnNoStd.getRootElement().getChild("Detail_List").getChildren("Detail");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
//		Element APP_ENTITY = returnNoStd.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		int size = Detail_List.size();
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body",TranData);
		
		//���ɱ�׼����ͷ
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate",String.valueOf(DateUtil.get8Date(tCurDate)),cTransaction_Header);
		Element mTranTime = new ElementLis("TranTime",String.valueOf(DateUtil.get6Time(tCurDate)),cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);
		
		ElementLis Count = new ElementLis("Count",Body);
		Count.setText(String.valueOf(size));
		
			for(int i=0;i<size;i++){
				Element tempEle = Detail_List.get(i);
				ElementLis listDetail = new ElementLis("Detail",Body);
					//�ؿ�����
					ElementLis CardType = new ElementLis("CardType",tempEle.getChildTextTrim("Ins_IBVoch_TpCd"),listDetail);
					//�ؿ�ӡˢ��
					ElementLis CardNo = new ElementLis("CardNo",tempEle.getChildTextTrim("Ins_IBVoch_ID"),listDetail);
					//�ؿ�״̬
					ElementLis CardState = new ElementLis("CardState",checkBkFlag2(tempEle.getChildTextTrim("IpOpR_Crcl_StCd")),listDetail);
					if(CardState.getText().equals("03")){//��ʹ��
						CardState.setText("4");
					}
					if(CardState.getText().equals("04")){//������
						CardState.setText("6");
					}
					//��֤������
					ElementLis OtherNo = new ElementLis("OtherNo",listDetail);
					//���ʻ�����
					ElementLis AgentCom = new ElementLis("AgentCom",listDetail);
					
					ElementLis TellerNo = new ElementLis("TellerNo",listDetail);
					//���з�������ˮ��
					ElementLis TranNo = new ElementLis("TranNo",tempEle.getChildTextTrim("RqPtTcNum"),listDetail);
			}
		Document tInXmlDoc = new Document(TranData);
		return tInXmlDoc;	
	}
	
	private String checkBkFlag2(String flag){
		if(flag == null || flag.length() == 0 ){
			return "";
		}
		int flagInt = Integer.valueOf(flag);
		switch(flagInt){
		case 1 : return "2";
		case 2 : return "11";
		case 3 : return "11";
		case 4 : return "3";
		default : return "";
		}
	}
	
	//���ն��˺ͱ�ȫ����
	private Document balanceTo(Document returnNoStd){
//		JdomUtil.print(returnNoStd);
		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element COM_ENTITY = cNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		Detail_List = returnNoStd.getRootElement().getChild("Detail_List").getChildren("Detail");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
		int size = Detail_List.size();
		String typecode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
		
		ElementLis TranData = new ElementLis("TranData");
			ElementLis Body = new ElementLis("Body",TranData);
			
			//���ɱ�׼����ͷ
			Date tCurDate = new Date();
			Element mTranDate = new ElementLis("TranDate",String.valueOf(DateUtil.get8Date(tCurDate)),cTransaction_Header);
			Element mTranTime = new ElementLis("TranTime",String.valueOf(DateUtil.get6Time(tCurDate)),cTransaction_Header);
			Element mNodeNo = new Element("NodeNo");
			mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));
			Element mTellerNo = new Element("TellerNo");
			mTellerNo.setText(CodeDef.SYS);
			cTransaction_Header.addContent(mNodeNo);
			cTransaction_Header.addContent(mTellerNo);
			TranData.addContent(cTransaction_Header);
//				ElementLis insu_id = new ElementLis("insu-id",COM_ENTITY.getChildTextTrim("Ins_Co_ID"),listDetail);
//				ElementLis trans_date = new ElementLis("trans-date",NewCcbFormatUtil.getTimeAndDate(TX_HEADER.getChildTextTrim("SYS_REQ_TIME"),0,8),listDetail); 
//				ElementLis trans_time = new ElementLis("trans-time",NewCcbFormatUtil.getTimeAndDate(TX_HEADER.getChildTextTrim("SYS_REQ_TIME"),8,14),listDetail);
//				ElementLis branch = new ElementLis("branch",COM_ENTITY.getChildTextTrim("CCBIns_ID"),listDetail);
//				ElementLis agency_hand = new ElementLis("agency-hand",COM_ENTITY.getChildTextTrim("CCB_EmpID"),listDetail);
//			ElementLis request_checkbill = new ElementLis("request-checkbill",listDetail);
//				ElementLis check_date = new ElementLis("check-date",NewCcbFormatUtil.getTimeAndDate(TX_HEADER.getChildTextTrim("SYS_REQ_TIME"),0,8),request_checkbill);

//				ElementLis check_trans_list = new ElementLis("check-trans-list",request_checkbill);
				if(size>0){
					for(int i=0;i<size;i++){
						ElementLis listDetail = new ElementLis("Detail",Body);
						Element tempEle = Detail_List.get(i);
//						ElementLis check_trans = new ElementLis("check-trans",check_trans_list);
						//���ն���
						if(typecode.equals("P53817103")){
							ElementLis Count = new ElementLis("Count",Body);
							ElementLis sumPrem = new ElementLis("Prem",Body);
							ElementLis ContNo = new ElementLis("ContNo",tempEle.getChildTextTrim("InsPolcy_No"),listDetail);
							ElementLis Prem = new ElementLis("Prem",tempEle.getChildTextTrim("TxnAmt"),listDetail);
							ElementLis AgentCom = new ElementLis("AgentCom",tempEle.getChildTextTrim("CCBIns_ID"),listDetail);
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo","",listDetail);
							ElementLis AppntName = new ElementLis("AppntName","",listDetail);
							ElementLis InsuredName = new ElementLis("InsuredName","",listDetail);
//							mClientIpEle.setText("3005");
							}
						//��ȫ����
						if(typecode.equals("P53817104")){
//							mClientIpEle.setText("3006");
							
							ElementLis BusiType = new ElementLis("BusiType" ,listDetail);
							if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819192")){//���ڸ���
								BusiType.setText("09");
							}
							if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819144")){//�˱�
								BusiType.setText("10");
							}
							if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819156")){//���ڽɷ�
								BusiType.setText("11");
							}
							ElementLis TranNo = new ElementLis("TranNo",tempEle.getChildTextTrim("InsPolcy_No"),listDetail);
							ElementLis NodeNo = new ElementLis("NodeNo",tempEle.getChildTextTrim("CCBIns_ID"),listDetail);
							ElementLis ContNo = new ElementLis("ContNo",tempEle.getChildTextTrim("Ins_Co_Jrnl_No"),listDetail);
							ElementLis EdorNo = new ElementLis("EdorNo",tempEle.getChildTextTrim("InsPolcy_Vchr_No"),listDetail);
							ElementLis AccNo = new ElementLis("AccNo",listDetail);
							ElementLis AccName = new ElementLis("AccName","",listDetail);
							ElementLis TranDate = new ElementLis("TranDate",tempEle.getChildTextTrim("Txn_Dt"),listDetail);
						}
							
//							if(mTypeCode.equals("P53819152")){
//								ElementLis trans_type1 = new ElementLis("trans-type","01",check_trans);
//								ElementLis back_flag = new ElementLis("back-flag","102",check_trans);
//							}else if(mTypeCode.equals("P53819156")){
//								ElementLis trans_type1 = new ElementLis("trans-type","02",check_trans);
//								ElementLis back_flag = new ElementLis("back-flag","240",check_trans);
//							}else if(mTypeCode.equals("P53819144")){
//								ElementLis trans_type1 = new ElementLis("trans-type","03",check_trans);
//								ElementLis back_flag = new ElementLis("back-flag","246",check_trans);
//							}else if(mTypeCode.equals("P53819161")){
//								ElementLis trans_type1 = new ElementLis("trans-type","03",check_trans);
//								ElementLis back_flag = new ElementLis("back-flag","245",check_trans);
//							}
					}
				}
		Document tInXmlDoc = new Document(TranData);	
		if(typecode.equals("P53817103")){
			System.out.println("-----------========++++++++++====");
			JdomUtil.print(tInXmlDoc);
			Element tBody = tInXmlDoc.getRootElement().getChild("Body");
			java.util.List<Element> tDetail = tBody.getChildren("Detail");
			long mSumPrem = 0;
			for(int i = 0;i<tDetail.size();i++){
				//�����ã�����û����������ֶΣ������Լ�����ֵ
				if(tDetail.get(i).getChildText("Prem").equals("")||tDetail.get(i).getChildText("Prem")==null){
					tDetail.get(i).getChild("Prem").setText("25");
				}
				
				long tPremFen = NumberUtil.yuanToFen(((Element)tDetail.get(i)).getChildText("Prem"));
				System.out.println("���˱��ѣ�"+tPremFen);
				mSumPrem += tPremFen; 			
			}
			cLogger.info("====�����ܱ���==="+mSumPrem);
			try {
				tBody.getChild("Prem").setText(String.valueOf(NumberUtil.fenToYuan(mSumPrem)));
				tBody.getChild("Count").setText(String.valueOf(tDetail.size()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cLogger.info("��ҿ���,��׼����զ����");
		JdomUtil.print(tInXmlDoc);
		return tInXmlDoc;
	}
	
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into Balance.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	
}
