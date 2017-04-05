package com.sinosoft.midplat.newabc.bat;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BatUtils
{
	protected Element cThisBusiConf = null;//��ǰ�������ýڵ�
	protected Element cThisConfRoot = null;//��ǰ���н��������ļ����ڵ�

	/** �������־����*/
	protected static final Logger cLogger = Logger.getLogger(BatUtils.class);
	
	/** ���������ýڵ�*/
	protected static Element cConfigEle;
	
	/** ��ǰʱ���ַ���[yyyyMMdd]*/
	private static String cCurDate = "";
	
	/** ũ�н���ip */
	private static String ABCSocketIp = "";

	/** ũ�н��׶˿� */
	private static int ABCSocketPort = 0;
	/** ���ͷ�ʽ		0: �ϴ�*/
	private static final String UP = "0";
	/** ���ͷ�ʽ		1: ����*/
	private static final String DOWN = "1";
	
	/** �ļ�����		01: ֤���ļ�*/
	private static final String CRTFILE = "01";	
	
	/**
	 * ��ȡ���������ýڵ���Ϣ
	 * @param funcFlag ��������
	 * @return ����������������Ϣ�ڵ�
	 */
	public static Element getConfigEle (String funcFlag){
		//��ǰʱ���ַ���Ϊ��
		if("".equals(cCurDate)){
			//��ʽ�����ڶ���ΪyyyyMMdd�ַ���
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		try{
			//bat.xml�ĵ�
			Document mInXmlDoc =  BatConf.newInstance().getConf();
			//���������ýڵ�XPath·��
			String elementXpathUrl = "/batchs/batch[funcFlag = '"+ funcFlag +"']";
			//bat.xml�ĵ���ѡ��һ�������������ýڵ�XPath·��ƥ��Ľڵ�
			cConfigEle = (Element)XPath.selectSingleNode(mInXmlDoc, elementXpathUrl);
			//ȡֵ:startTime�ӽڵ�����
			System.out.println("ȡֵ:"+cConfigEle.getChildText("startTime"));
		}catch(Exception e){ 
			//��ȡ�����ļ��쳣������·���Ƿ���ȷ���ļ��Ƿ����......
			cLogger.info("��ȡ�����ļ��쳣������·���Ƿ���ȷ���ļ��Ƿ����......");
			e.getStackTrace();
		}
		
		//����������������Ϣ�ڵ�
		return cConfigEle;
	}
	
	/**
	 * ��ȡ��ͷ(����ͷ)
	 * @param packBodyLen ���峤��(���ݰ�����)
	 * @return ���ذ�ͷ�ַ����ֽ�����
	 */
	private static byte[] getHeader(int packBodyLen) {
		//��ͷ���ݰ������ַ���
		String tPackHeadDataPackLengthStr = String.valueOf(packBodyLen);
		
		//����Ϊ8λǰ����10�������ݰ������ַ���
		for (int i = tPackHeadDataPackLengthStr.length(); i < 8; i++)
			tPackHeadDataPackLengthStr = "0" + tPackHeadDataPackLengthStr;
		
		//ժҪ�ַ���
		String tabloid = "";
		//����Ϊ40λ�󵼿ո�ժҪ�ַ���
		for(int i = 0; i < 40; i++){
			tabloid += " ";
		}
		
		//��ͷ�ַ���[��������:X(��׼�ӿ�),�汾��:1.0,���ݰ�����,��˾����:C8(1132    ),���ܱ�ʾ:(0-�����ܣ�1-�ؼ����ݼ��ܣ�2-�����������),�����㷨:0,����ѹ����־:(0-��ѹ����1-ѹ��),����ѹ���㷨:0,ժҪ�㷨:0, ժҪ,Ԥ���ֶ�:00000000]
		String packHeadStr = "X1.0" +tPackHeadDataPackLengthStr+"1132    "+"0"+"0"+"0"+"0"+"0"+tabloid+"00000000";
		//���ذ�ͷ�ַ����ֽ�����
		return  packHeadStr.getBytes();
	}
	
	
	/**
	 * ��ȡ�ļ��ϴ����ر���(����/������)[UTF-8]�ֽ�����
	 * @param transFlag  ���ͷ�ʽ		0: �ϴ�		1: ����
	 * @param fileName  �ļ�����
	 * @param fileType �ļ�����		01: ֤���ļ�		02: �����ļ�
	 * @param FileLen �ļ����� 		�ļ��ϴ�:�ϴ��ļ�UTF-8��ʽ�ļ����� 	�ļ�����:00000000
	 * @param mTranDate ��ǰʱ��
	 * fileData �ļ�����		�ļ��ϴ�������	����		�ļ�����Ӧ���� ֮����
	 * ���շ����� 
	 * ���Ƚ��ձ���ͷ���õ�XML���ĳ��� 
	 * �ñ��ĳ��ȵõ������ı���
	 * �ڱ������еõ��ļ����ȣ������ļ����Ƚ����ļ�
	 * @return
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen,String mTranDate) {
		
		//�½����ڶ���
		Date date = new Date();
		//��ǰ�����ַ���[yyyyMMdd]
		String cCurDate = new SimpleDateFormat("yyyyMMdd").format(date);
		//��ǰʱ���ַ���[HHmmss]
		String cCurTime = new SimpleDateFormat("HHmmss").format(date);
		//������ǰ�����ַ���(�ļ��޸�ʱ���)[yyyy-MM-dd HH:mm:ss.SSS]
		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
		
		try {
			//���ױ���(��ȡ������ˮ��)[04+yyyyMMdd+HHmmss]
			String tTransCode = "04" + cCurDate + cCurTime;
			//���ͷ�ʽΪ����
			if(transFlag.equals(DOWN)){
				//�����ļ�����:00000000
				FileLen = "00000000";
			}else{//���ͷ�ʽΪ�ϴ�
				//�ϴ��ļ�����:����ǰ�����8λ10�����ַ���
				for (int i = FileLen.length(); i < 8; i++)
					//����Ϊǰ����8λ10�����ַ���
					FileLen = "0" + FileLen;
			}
			
			//�ļ�����Ϊ֤���ļ�
			if(fileType.equals(CRTFILE)){
				//�ļ�����Ϊ֤���ļ�ʱ�����ֶο���Ϊ��
				fileName = "";
			}
			//�����ַ���
			String xmlStr = "<ABCB2I><Header><SerialNo></SerialNo><InsuSerial>"+tTransCode+"</InsuSerial><TransDate>"+ mTranDate +"</TransDate>" +
					"<TransTime>"+ cCurTime +"</TransTime><BankCode></BankCode><CorpNo>1132</CorpNo><TransCode>1017</TransCode>" +
							"<TransSide>0</TransSide><EntrustWay></EntrustWay><ProvCode></ProvCode><BranchNo></BranchNo></Header>" +
							"<App><Req><TransFlag>"+transFlag+"</TransFlag><FileType>"+fileType+"</FileType><FileName>"+fileName+"</FileName>" +
									"<FileLen>"+FileLen+"</FileLen><FileTimeStamp>"+ cAllCurDate +"</FileTimeStamp></Req></App></ABCB2I>";
			//�����з����ļ��ϴ����ؽ��ױ��ģ������ַ���
			cLogger.info("�����з����ļ��ϴ����ؽ��ױ��ģ�"+xmlStr);
			//�����ַ�������UTF-8 ������ֽ�����������
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			//�����ֽ����������
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//�ֽ�
			int a = 0;
			//��ȡ�����ֽڷ�-1(δβ)
			while ((a = input.read()) != -1) {
				//���������ֽ�д�������
				baos.write(a);
			}
			//�������������д�����ݵ��ֽ�����
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @Title: downLoadFile
	 * @Description: �ļ�����
	 * @param fileName �ļ�����
	 * @param fileType �ļ�����
	 * @param funcFlag ���ͷ�ʽ
	 * @param cCurDate ��ǰʱ��
	 * @return ���سɹ�/ʧ��
	 * @return boolean
	 * @throws
	 */
	public boolean downLoadFile(String fileName,String fileType,String funcFlag,String cCurDate){
		//��ȡ�ļ��ϴ����ر���(����/������)[UTF-8]�ֽ�����[���ͷ�ʽ: ����,�ļ�����,�ļ�����,�ļ�����:0(����),��ǰʱ��]
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0",cCurDate);  //�õ����ܺ�ı�������Ϣ
		//��ȡ��ͷ(����ͷ)�ֽ�����
		byte[] headerStr = getHeader(bodyStr.length);
		//
		return sendData(headerStr,bodyStr,DOWN,null,fileName,funcFlag);
	} 
	
	
	/**
	 * �ļ��ϴ�
	 * @param fileName
	 * @param fileType
	 */
	public boolean upLoadFile(String fileName,String fileType,String funcFlag,String mTranDate,String sendName){
		boolean result = false;
		try {
			InputStream is = new FileInputStream(fileName);
			ByteArrayOutputStream tTemp = new ByteArrayOutputStream();
			byte[] bytes = new byte[1];
			while (is.read(bytes) != -1) {
				tTemp.write(bytes);
			}
			tTemp.flush();
			tTemp.close();
			byte[] tBytes = tTemp.toByteArray();
			byte[] bodyStr = getBodyStr(UP,sendName,fileType,tBytes.length+"",mTranDate);
			byte[] headerStr = getHeader(bodyStr.length);
			result = sendData(headerStr,bodyStr,UP,tBytes,fileName,funcFlag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 *  ���ܻ�ȡ���ж˷��ر�����Ϣ�����ұ����ļ���Ϣ
	 * @param is  ���ж˷��ص�InputStream
	 * @param transFlag  ���ͷ�ʽ  �ϴ�������
	 * @return
	 */
	private static boolean readFileData(InputStream inputstream,String transFlag) {
		try {
			String tPackHead = "";
			int x = 0;
			for (int i = 0; i < 73; i++) {
					x = inputstream.read();
					System.out.print(x);
				if (x == -1) {
					throw new RuntimeException("�������ֽ�����С��73");
				}
				tPackHead += (char) x;
			}
			cLogger.info("���ж˽��ױ���ͷ�� "+tPackHead);
			String tPackHeadLength = tPackHead.substring(4, 12).trim();
			int inputLen = Integer.parseInt(tPackHeadLength.trim());
			// ��ȡ�������岿��
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			for (int i = 0; i < inputLen; i++) {
				x = inputstream.read();
				if (x == -1) {
					System.out.println("�������������󣬳���ӦΪ" + inputLen + "����ʵ�ʳ���Ϊ"
							+ i);
					throw new MidplatException("�������������󣬳���ӦΪ" + inputLen
							+ "����ʵ�ʳ���Ϊ" + i);
				}
				bao.write((char) x);
			}
			
			byte[] Pack = bao.toByteArray();
			InputStream XMLInputstream = new ByteArrayInputStream(Pack);
			
			// �Ա��Ľ���
//			ABCSecurity mABCSecurity = new ABCSecurity();
//			if (!mABCSecurity.decryption(XMLInputstream, null)) {
//				mLogger.info("���Ľ���ʧ�ܣ�");
//			}
//			XMLInputstream = mABCSecurity.getClearStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
			while ((a = XMLInputstream.read()) != -1) {
				baos.write(a);
			}
			byte[] bPack = baos.toByteArray();
			
			String pack = new String(bPack,"UTF-8");
			System.out.println(pack);
			pack  = "<?xml version='1.0' encoding='GBK'?>"+pack;
			cLogger.info(" ���صı�����:" + pack);
			
			Document mInNoStd = JdomUtil.build(pack.getBytes());
//			Document tDoc = XmlOperator .produceXmlDoc(new ByteArrayInputStream(pack.getBytes()));
			Element root = mInNoStd.getRootElement();
			
			String resultCode = root.getChild("Header").getChildTextTrim("RetCode");
			
			if(!resultCode.equals("000000")){
				System.out.println("���д����ļ����ؽ���ʧ��");
				throw new MidplatException("���д����ļ����ؽ���ʧ��");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidplatException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * �����з�������(����)
	 * @param headerStr  ����ͷ[�ֽ�����]
	 * @param bodyStr  ������(����)[�ֽ�����]
	 * @param transFlag ���ͷ�ʽ
	 * @param fileData  �ļ�����(���͵�����)[�ֽ�����]
	 * @param fileName �ļ�����
	 * @param funcFlag ��������
	 */
	private boolean sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName,String funcFlag) {
		//����������:\n����ͷ+������+�ļ�����
		cLogger.info("����������:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		//��ȡ�뽻������ƥ������������ýڵ���Ϣũ�н���ip
		ABCSocketIp = BatUtils.getConfigEle(funcFlag).getChildText("ip");
		//��ȡ�뽻������ƥ������������ýڵ���Ϣũ�н��׶˿�
		ABCSocketPort = Integer.parseInt(BatUtils.getConfigEle(funcFlag).getChildText("ABCport"));
		//Ŀ��ip��˿�:ũ�н���ip::ũ�н��׶˿�
		cLogger.info("Ŀ��ip��˿�:" + ABCSocketIp + "::" + ABCSocketPort);
		//
		boolean result = false;
		//�׽���
		Socket socket = null;
		//�����
		OutputStream os = null;
		//������
		InputStream in = null;
		try {
			//���������(�����׽�������ͨ������)
			socket = new Socket(ABCSocketIp, ABCSocketPort);
			//socket������
			cLogger.info("socket������");		
			//�������ݳ�ʱʱ��(����/����60000����(60��/1����)��ʱֵ�� SO_TIMEOUT)
			socket.setSoTimeout(60000);
			//��������
			os = socket.getOutputStream();
			//��������
			in = socket.getInputStream();
			//���ͱ���ͷ(����)
			os.write(headerStr);
			//���ͱ�����(����)
			os.write(bodyStr);
//			os.flush();
			//socket�����ӣ������ѷ���
			cLogger.info("socket�����ӣ������ѷ���");	
			//
			String confirm = "0000";
			int fileLen = 0;
			if(transFlag.equals(UP)){ //�ϴ�
				if(fileData == null || "".equals(fileData)){
					return false;
				}				
				String confir = "";
				int x = 0;
				for (int i = 0; i < 4; i++) {
					x = in.read();
					cLogger.info(x);
					if (x == -1) {
						throw new RuntimeException("�������ֽ�����С��4");
					}
					confir += (char) x;
				}
				cLogger.info("���з��ؽ��"+confir);
				if(!"0000".equals(confir)){
					return false;
				}
				String upFileLen = String.valueOf(fileData.length);
				fileLen = Integer.parseInt(upFileLen);
				int mm = fileLen/4096;
				int mmm = fileLen%4096;
				cLogger.info("�̣� "+mm);
				cLogger.info("������ "+mmm);
				if(mmm!=0)
				{
					mm = mm+1;
				}
				cLogger.info("���ʹ����� "+mm);
				for(int m  =0;m<mm;m++){
					byte[] RealfileData = null;
					if(mm-m!=1){
						RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
					
					}else{
						RealfileData = new String(fileData).substring(m*4096).getBytes();
					}
					cLogger.info("��"+m+"�η��͵�����"+new String(RealfileData));
//					cLogger.info("��"+m+"�η��͵����ݳ���"+(new String(RealfileData)).length());
					cLogger.info("��"+m+"�η��͵����ݳ���"+RealfileData.length);
//					String tSendLen = (new String(RealfileData)).length()+"";
					String tSendLen = RealfileData.length+"";
					for(int i = ((new String(RealfileData)).length()+"").length();i<12;i++){
						tSendLen = "0"+tSendLen;
					}
					cLogger.info("��"+m+"�η��͵����ݳ���"+tSendLen);
					os.write(tSendLen.getBytes());
					os.write(RealfileData);
					String secondCon = "";
					for (int i = 0; i < 4; i++) {
						x = in.read();
						System.out.print(x);
						if (x == -1) {
							throw new RuntimeException("�������ֽ�����С��4");
						}
						secondCon += (char) x;
					}
					cLogger.info("��"+m+"�η������з������ݽ��"+secondCon);
					if(!"0000".equals(secondCon)){
						return false;
					}
				}
				
			}else if(transFlag.equals(DOWN)){    
				String tFileLen = "";
				int x = 0;
				for (int i = 0; i < 12; i++) {
						x = in.read();
					if (x == -1) {
						throw new RuntimeException("�������ֽ�����С��12");
					}
					tFileLen += (char) x;
				}
				fileLen = Integer.parseInt(tFileLen);
				cLogger.info("---���з��͵��ļ����ȣ� "+tFileLen);
				fileLen = Integer.parseInt(tFileLen); 
				int mm = fileLen/4096;
				int mmm = fileLen%4096;
				cLogger.info("�̣� "+mm);
				cLogger.info("������ "+mmm);
				if(mmm!=0)
				{
					mm = mm+1;
				}
				cLogger.info("��������� "+mm);
				//�����з���ȷ��
				os.write(confirm.getBytes());
				//��ʼ�����ļ�
				cLogger.info("------�����д��͵��ļ�---");
				ByteArrayInputStream is = null;
				if(fileLen>0){   //�����д��ݵ��ļ����ݱ�����mDownLoadPath·����
					// ���ز���
					ByteArrayOutputStream files = new ByteArrayOutputStream();
					for(int m  =0;m<mm;m++)
					{
					int f = 0;
					// ��ȡ�������岿��
					String tSenFileLen = "";
					for (int i = 0; i < 12; i++) {
							x = in.read();
						if (x == -1) {
							throw new RuntimeException("�������ֽ�����С��12");
						}
						tSenFileLen += (char) x;
					}
					cLogger.info("2345679:::::::"+tSenFileLen);
					fileLen = Integer.parseInt(tSenFileLen);
					for (int i = 0; i < fileLen; i++) {
						f = in.read();
						if (f == -1) {
							System.out.println("�������������󣬳���ӦΪ" + fileLen + "����ʵ�ʳ���Ϊ" + i);
							throw new MidplatException("�������������󣬳���ӦΪ" + fileLen + "����ʵ�ʳ���Ϊ" + i);
						}
						files.write((char) f);
					}
					is = new ByteArrayInputStream(files.toByteArray()); 
					//�����з���ȷ��
					os.write(confirm.getBytes());
					}
					OutputStream fileOutput = new FileOutputStream(BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
					System.out.println("����·��:" + BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
					int len = 0;
					while ((len = is.read()) != -1) {
						fileOutput.write(len);
					}
					fileOutput.flush();
					fileOutput.close();
					is.close();
				}else{
					System.out.println("�������������󣬴����ļ�����Ϊ��");
					throw new MidplatException("�������������󣬴����ļ�����Ϊ��");
				}
				cLogger.info("�ļ�������ϣ������з���ȷ����Ϣ");
				//�ٴη���ȷ��
				os.write(confirm.getBytes());
			
			}
			
			os.flush();
			socket.shutdownOutput();
			cLogger.info("�����з���ȷ����Ϣ���");
			if(transFlag.equals(UP)){ 
			InputStream isBack = null;
			if (socket.isConnected()) {
				socket.setSoTimeout(60000);
				isBack = socket.getInputStream();
				cLogger.info("-------�������ж˷�����Ϣ----");
				result = readFileData(isBack,transFlag);
			}
			
			if (isBack != null){
				isBack.close();
			}
			}else{				
				result = true;
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidplatException e) {
			e.printStackTrace();
		}finally{			
				try {
					if (os != null){
						os.close();
						cLogger.info("�ر�OutputStream�ɹ�");
					}
					if (in != null){
						in.close();
						cLogger.info("�ر�InputStream�ɹ�");
					}
					if (socket != null){
					socket.close();
					cLogger.info("�ر�socket�ɹ�");
					}					
				} catch (IOException e) {
					cLogger.info("�ر�socket�쳣"+e.getMessage());
				}
		}
		return result;
	}
	
	

}