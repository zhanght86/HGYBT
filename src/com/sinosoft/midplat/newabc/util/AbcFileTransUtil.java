package com.sinosoft.midplat.newabc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.ElementLis;

/**
 * ��ũ���ļ��ϴ�������
 * @author Administrator
 *
 */
public class AbcFileTransUtil {
	
	/** ũ�н���ip */
	private String cABCSocketIp = "";
	/** ũ�н��׶˿� */
	private int cABCSocketPort = 0;
	/**�������е��ļ��ᱣ�浽��·����*/
	private String cDownLoadPath = "";
	/** �ϴ������е��ļ� ���浽��·����*/
	private String cUpLoadPath = "";
	/** ��ʶ�Ǵ��ͷ�ʽΪ �ϴ�*/
	private static final String UP = "0";
	/** ��ʶ�Ǵ��ͷ�ʽΪ ����*/
	private static final String DOWN = "1";
	/** ��ʶ���ļ�����Ϊ ֤���ļ�*/
	private static final String CRTFILE = "01";	
	/** ���չ�˾���� */
	private String cInsu;
	/** ������ˮ */
	private String cTransNo = "";
	/** �Ż���־��� */
	private Logger cLogger = Logger.getLogger(AbcFileTransUtil.class);
	/** ��ǰϵͳ����YYYYMMDD */
	private String c8Date = String.valueOf(DateUtil.getCur8Date());
	/** ��ǰϵͳʱ��HHMMSS */
	private String c6Time = String.valueOf(DateUtil.getCur6Time());
	
	/**
	 * ��ָ��·���ϴ��������ļ�ʱ�������·������
	 * @param cDownLoadPath  �����ļ�ʱ ��Ҫ����
	 * @param cUpLoadPath   ��ָ��·���ϴ��ļ�ʱ����Ҫ����
	 * @throws MidplatException 
	 */
	public AbcFileTransUtil(String cDownLoadPath,String cUpLoadPath) throws MidplatException{
		this.cDownLoadPath = cDownLoadPath;
		this.cUpLoadPath = cUpLoadPath;
		getServerInfo();
	}
	
	/**
	 * ��ũ�������ļ���ȡũ��ip���˿���Ϣ
	 * @throws MidplatException 
	 */
	private void getServerInfo() throws MidplatException {
		try {
			Element tAbcConfEle = NewAbcConf.newInstance().getConf().getRootElement();
			cABCSocketIp = tAbcConfEle.getChild("socket").getAttributeValue("ip");
			cABCSocketPort = Integer.valueOf(tAbcConfEle.getChild("socket").getAttributeValue("port"));
			cInsu=tAbcConfEle.getChild("bank").getAttributeValue("insu");
			File tFile = new File(cUpLoadPath);
			if (!tFile.exists())
				tFile.mkdirs();

			tFile = new File(cDownLoadPath);
			if (!tFile.exists())
				tFile.mkdirs();
			
			cLogger.info("ũ�з�����Ϣ��\n ֪ͨ��Ϣ: ip = " + cABCSocketIp + "] port = " + cABCSocketPort );
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("��ȡ���з�������Ϣʧ��!");
		}
		
	}
	
	/**
	 * �����ļ�
	 * @param fileName �����ص��ļ���
	 * @param fileType �����ص��ļ�����   01  ֤���ļ�  02 �����ļ�
	 * @throws Exception 
	 */
	public void downLoadFile(String fileName,String fileType) throws Exception{
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0");  //�õ����ܺ�ı�������Ϣ
		byte[] headerStr = getHeader(bodyStr.length); //�õ���������ͷ
		sendData(headerStr,bodyStr,DOWN,null,fileName);
	}
	
	/**
	 * �ļ��ϴ�
	 * @param pFileName
	 * @param pFileType
	 * @throws Exception
	 */
	public void upLoadFile(String pFileName,String pFileType) throws Exception{
		try {
			InputStream is = new FileInputStream(cUpLoadPath + pFileName);
			ByteArrayOutputStream tTemp = new ByteArrayOutputStream();
			byte[] bytes = new byte[1];
			while (is.read(bytes) != -1) {
				tTemp.write(bytes);
			}
			tTemp.flush();
			tTemp.close();
			byte[] tBytes = tTemp.toByteArray();
			byte[] bodyStr = getBodyStr(UP,pFileName,pFileType,tBytes.length+"");
			byte[] headerStr = getHeader(bodyStr.length);
			sendData(headerStr,bodyStr,UP,tBytes,pFileName);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("�ļ��ϴ�ʧ��:"+e.getMessage());
		}
	}
	
	public void upLoadFile(String fileName,String fileType,byte[] data) throws Exception{
		byte[] bodyStr = getBodyStr(UP,fileName,fileType,data.length+"");
		byte[] headerStr = getHeader(bodyStr.length);
		sendData(headerStr,bodyStr,UP,data,fileName);
	}
	
	/**
	 * �����з��ͽ���
	 * @param headerStr  ����ͷ
	 * @param bodyStr  ����
	 * @param fileData  ���͵�����
	 */
	private void sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName) throws Exception {
		cLogger.info("����������:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		cLogger.info("Ŀ��ip��˿�:" + cABCSocketIp + ":" + cABCSocketPort);
		InputStream in = null;
		OutputStream os = null;
		Socket socket = null;
		try {

			socket =  new Socket(cABCSocketIp, cABCSocketPort);
			socket.setSoTimeout(60000);
			os = socket.getOutputStream();
			in = socket.getInputStream();
			
			cLogger.info("�����з��ͱ���ͷ!");
			os.write(headerStr);
			cLogger.info("�����з��ͱ�����!");
			os.write(bodyStr);
			InputStream is = null;
			String confirm = "0000";
			int fileLen = 0;
			
			//�ļ��ϴ�����
			if(transFlag.equals(UP)){ //�ϴ�
				if(fileData == null || "".equals(fileData)){
					throw new MidplatException("�ļ��ϴ�ʧ��,�ϴ��ļ�����Ϊ��!");
				}
				
				cLogger.info("�������з��ؽ��!");
				String confir = "";
				int x = 0;
				for (int i = 0; i < 4; i++) {
					x = in.read();
					System.out.print(x);
					if (x == -1) {
						throw new RuntimeException("�������ֽ�����С��12");
					}
					confir += (char) x;
				}
				cLogger.info("���з��ؽ��:"+confir);
				if(!"0000".equals(confir)){
					cLogger.info("��0000����!");
					readFileData(in,transFlag,69);
				}
				String upFileLen = String.valueOf(fileData.length);
				fileLen = Integer.parseInt(upFileLen);
				cLogger.info("�����ܳ���"+fileLen);
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
					cLogger.info("m:"+m);
					cLogger.info("fileData:"+fileData.length);
					if(mm-m!=1){
						//RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
						RealfileData = subBytes(fileData,m*4096,4096);
					}else{
						RealfileData = subBytes(fileData,m*4096,mmm);
						
					}
					//cLogger.info("��"+m+"�η��͵�����"+new String(RealfileData));
					cLogger.info("��"+m+"�η��͵����ݳ���"+RealfileData.length);
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
					cLogger.info("��"+m+"�η��͵����ݳ���"+secondCon);
					if(!"0000".equals(secondCon)){
						throw new MidplatException("�ļ��ϴ�ʧ��!");
					}
				}
			}else if(transFlag.equals(DOWN)){    
				
				String tFileLen = "";
				int x = 0;
				for (int i = 0; i < 1; i++) {
					x = in.read();
				if (x == -1) {
					throw new RuntimeException("�������ֽ�����С��12");
				}
				tFileLen += (char) x;
				}
				
				if(tFileLen.startsWith("X")){
					readFileData(in,transFlag,72);
				}
				
				for (int i = 0; i < 11; i++) {
					x = in.read();
				if (x == -1) {
					throw new RuntimeException("�������ֽ�����С��12");
				}
				tFileLen += (char) x;
				}
				
				fileLen = Integer.parseInt(tFileLen);
				cLogger.info("---���з��͵��ļ����ȣ� "+tFileLen);
				
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
				if(fileLen>0){   //�����д��ݵ��ļ����ݱ�����cDownLoadPath·����
					// ���ز���
					ByteArrayOutputStream files = new ByteArrayOutputStream();
					
					for(int m  =0;m<mm;m++){
						
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
					if(fileName == null || fileName.equals("")){
						fileName = "cacert.crt";
					}
					OutputStream fileOutput = new FileOutputStream(cDownLoadPath + fileName);
					
					System.out.println("����·��:" + cDownLoadPath + fileName);
					
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
				//�ٴη���ȷ��
				//os.write(confirm.getBytes());
			}
			
			os.flush();
			socket.shutdownOutput();

			if (socket.isConnected()) {
				socket.setSoTimeout(60000);
				is = socket.getInputStream();
				cLogger.info("-------�������ж˷�����Ϣ----");
				readFileData(is,transFlag,73);
			}
			if (os != null)
				os.close();
			if (is != null)
				is.close();
			if (socket != null)
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		} finally{	
			try{
				os.flush();
				in.close();
				socket.close();
			}catch(IOException ioe){
				cLogger.error("�ر�������쳣");
				
			}
			
		}

	}

	/**
	 *  ���ܻ�ȡ���ж˷��ر�����Ϣ�����ұ����ļ���Ϣ
	 * @param is  ���ж˷��ص�InputStream
	 * @param transFlag  ���ͷ�ʽ  �ϴ�������
	 * @return
	 */
	private void readFileData(InputStream inputstream,String transFlag,int num) throws MidplatException {
		try {
			String tPackHead = "";
			int x = 0;
			for (int i = 0; i < num; i++) {
					x = inputstream.read();
					//System.out.print(x);
				if (x == -1) {
					throw new RuntimeException("�������ֽ�����С��73");
				}
				tPackHead += (char) x;
			}
			cLogger.info("���ж˽��ױ���ͷ�� "+tPackHead);
			String tPackHeadLength = tPackHead.substring(4, 12).trim();
			if(num == 72){
				tPackHeadLength = tPackHead.substring(3, 11).trim();
			}
			if(num == 69){
				tPackHeadLength = tPackHead.substring(0, 8).trim();
			}
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
//				cLogger.info("���Ľ���ʧ�ܣ�");
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
			Document tDoc = JdomUtil.build(pack);
			Element root = tDoc.getRootElement();
			JdomUtil.print(tDoc);
			String resultCode = root.getChild("Header").getChildTextTrim("RetCode");
			String resultText = root.getChild("Header").getChildTextTrim("RetMsg");
			System.out.println("���з��ش���:" + resultCode);
			System.out.println("���з��ؽ��:" + resultText);
			if(!resultCode.equals("000000")){
				throw new MidplatException(""+resultText);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
	}

	/**
	 * ��ȡ������
	 * @param transFlag  ��������  0-���� 1-�ϴ�
	 * @param fileName  �ļ�����
	 * @param fileData �ļ�����
	 * @param fileType �ļ����� 01  ֤���ļ�  02 �����ļ�
	 * @return
	 * @throws Exception 
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen) throws Exception {
		try {
			if(transFlag.equals(DOWN)){
				FileLen = "00000000";
			}else{
				for (int i = FileLen.length(); i < 8; i++)
					FileLen = "0" + FileLen;
			}
			
			if(fileType.equals(CRTFILE)){
				fileName = "";
			}
			String xmlStr = getToABCXml(fileName,FileLen, transFlag);
				
			cLogger.info("�����з����ļ��ϴ����ؽ��ױ����壺");
			JdomUtil.print(JdomUtil.build(xmlStr));
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
				while ((a = input.read()) != -1) {
					baos.write(a);
				}
				
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new MidplatException("���ɱ�����ʧ��:"+e.getMessage());
		}
	}
	
	/**
	 * �����ļ������ļ����ȡ��ļ����ͣ���ȡ�������еı�����Ϣ
	 */
	public String getToABCXml(String FileName, String FileLen,String transFlag)throws Exception{
		ElementLis ABCB2I = new ElementLis("ABCB2I");
			ElementLis Header = new ElementLis("Header",ABCB2I);
				ElementLis SerialNo = new ElementLis("SerialNo",String.valueOf(DateUtil.getCur8Date())+DateUtil.getCur6Time(),Header); //���н�����ˮ��	
				ElementLis InsuSerial = new ElementLis("InsuSerial","",Header);  //���չ�˾��ˮ��	
				ElementLis TransDate = new ElementLis("TransDate",String.valueOf(DateUtil.getCur8Date()),Header); //��������
				ElementLis TransTime = new ElementLis("TransTime",DateUtil.getCur6Time()+"",Header); //����ʱ��	
				ElementLis BankCode = new ElementLis("BankCode","03",Header); //���д���	
				ElementLis CorpNo = new ElementLis("CorpNo",cInsu,Header); //���չ�˾����	
				ElementLis TransCode  = new ElementLis("TransCode","1017",Header);  //���ױ��� 1017-�ļ��ϴ����ؽ���
				ElementLis TransSide = new ElementLis("TransSide","0",Header); //���׷���	0-���չ�˾ 1-����
				ElementLis EntrustWay = new ElementLis("EntrustWay","",Header);  //ί�з�ʽ	
				ElementLis ProvCode = new ElementLis("ProvCode","",Header); //ʡ�д���	
				ElementLis BranchNo = new ElementLis("BranchNo","",Header); //�����
		
			ElementLis App = new ElementLis("App",ABCB2I);
				ElementLis Req = new ElementLis("Req",App);
					ElementLis mTransFlag = new ElementLis("TransFlag",transFlag,Req); 
					ElementLis mFileType = new ElementLis("FileType","02",Req);
					ElementLis mFIleName = new ElementLis("FileName",FileName,Req);
					ElementLis mFileLen = new ElementLis("FileLen",FileLen,Req);
					ElementLis mFileTimeStamp = new ElementLis("FileTimeStamp",c8Date+c6Time,Req);

		Document doc = new Document(ABCB2I);
		
		return JdomUtil.toString(doc);
	}

	/**
	 * ��ȡ���ױ���ͷ
	 * @param packLen ������ĳ���
	 * @return
	 */
	private byte[] getHeader(int packLen) {
		String tPackHeadLengthStr = String.valueOf(packLen);

		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;
		
		String des = "";
		for(int i = 0; i < 40; i++){
			des += " ";
		}
		
		String pack = "X1.0" +tPackHeadLengthStr+"1116    "+"0"+"0"+"0"+"0"+"0"+des+"00000000";
		return  pack.getBytes();
	}
	
	 public static byte[] subBytes(byte[] src, int begin, int count) {
			// System.out.println("begin:"+begin +"  " )
		        byte[] bs = new byte[count];
		        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
		        return bs;
		}
	
	public static void main(String[] args) throws Exception {
		String downPath = "/web/ybt/WEB-INF/key/";
		String upPath = "D:/0-��������/I-ũҵ����/ũ���½ӿ�/";
		AbcFileTransUtil util = new AbcFileTransUtil(downPath,upPath);
   		util.downLoadFile("cacert.crt", CRTFILE);
	    //System.out.println(InetAddress.getLocalHost());
		//util.downLoadFile("POLICY1122.20150130", "02");
	}
}
