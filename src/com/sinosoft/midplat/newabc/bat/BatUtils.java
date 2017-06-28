package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BatUtils {
	protected static final Logger cLogger = Logger.getLogger(BatUtils.class);
	protected static Element cConfigEle;
	private static String cCurDate = "";
	
	/** ABCSocketIp ũ�н���ip */
	private static String ABCSocketIp = "";

	/** ABCSocketPort ũ�н��׶˿� */
	private static int ABCSocketPort = 0;
	/** ��ʶ�Ǵ��ͷ�ʽΪ �ϴ�*/
	private static final String UP = "0";
	/** ��ʶ�Ǵ��ͷ�ʽΪ ����*/
	private static final String DOWN = "1";
	
	/** ��ʶ���ļ�����Ϊ ֤���ļ�*/
	private static final String CRTFILE = "01";	
	/**
	 * ��ȡ������Ϣ
	 * wz 2014-7-26 16:39:28
	 * funcFlag ��������
	 * @return ����������Ϣ��Element
	 */
	public static Element getConfigEle (String funcFlag){
		if("".equals(cCurDate)){
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		try{
//			InputStream mIs = new FileInputStream(mConfPath);
			Document mInXmlDoc =  BatConf.newInstance().getConf();
			String elementUrl = "/batchs/batch[funcFlag = '"+ funcFlag +"']";
			cConfigEle = (Element)org.jdom.xpath.XPath.selectSingleNode(mInXmlDoc, elementUrl);
			System.out.println("quzhi:"+cConfigEle.getChildText("startTime"));
//			mIs.close();
		}catch(Exception e){ 
			cLogger.info("��ȡ�����ļ��쳣������·���Ƿ���ȷ���ļ��Ƿ����......");
			e.getStackTrace();
		}
		
		return cConfigEle;
	}
	
	public boolean downLoadFile(String fileName,String fileType,String funcFlag,String cCurDate){
		byte[] bodyStr = getBodyStr(DOWN,fileName,fileType,"0",cCurDate);  //�õ����ܺ�ı�������Ϣ
		byte[] headerStr = getHeader(bodyStr.length); //�õ���������ͷ
		return sendData(headerStr,bodyStr,DOWN,null,fileName,funcFlag);
	} 
	
	/**
	 * ��ȡ������
	 * @param transFlag  ��������  
	 * @param fileName  �ļ�����
	 * @param fileData �ļ�����
	 * @param fileType �ļ����� 01  ֤���ļ�  02 �����ļ�
	 * @return
	 */
	private byte[] getBodyStr(String transFlag,String fileName,String fileType,String FileLen,String mTranDate) {
		
		Date date = new Date();
		String cCurDate = new SimpleDateFormat("yyyyMMdd").format(date);
		String cCurTime = new SimpleDateFormat("HHmmss").format(date);
		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
//		String cAllCurDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date().getTime() - 86400000L);
		try {
			String tTransCode = "04" + cCurDate + cCurTime;//��ȡ������ˮ��
			if(transFlag.equals(DOWN)){
				FileLen = "00000000";
			}else{
				for (int i = FileLen.length(); i < 8; i++)
					FileLen = "0" + FileLen;
			}
			
			if(fileType.equals(CRTFILE)){
				fileName = "";
			}
			String xmlStr = "<ABCB2I><Header><SerialNo></SerialNo><InsuSerial>"+tTransCode+"</InsuSerial><TransDate>"+ mTranDate +"</TransDate>" +
					"<TransTime>"+ cCurTime +"</TransTime><BankCode>03</BankCode><CorpNo>1147</CorpNo><TransCode>1017</TransCode>" +
							"<TransSide>0</TransSide><EntrustWay></EntrustWay><ProvCode></ProvCode><BranchNo></BranchNo></Header>" +
							"<App><Req><TransFlag>"+transFlag+"</TransFlag><FileType>"+fileType+"</FileType><FileName>"+fileName+"</FileName>" +
									"<FileLen>"+FileLen+"</FileLen><FileTimeStamp>"+ cAllCurDate +"</FileTimeStamp></Req></App></ABCB2I>";
			cLogger.info("�����з����ļ��ϴ����ؽ��ױ����壺"+xmlStr);
			InputStream input = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int a = 0;
			
				while ((a = input.read()) != -1) {
					baos.write(a);
				}
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	protected static Document parse(String nFileURL) throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
	    String mCharset = "";
        if (null == mCharset || "".equals(mCharset)) {
            mCharset = "GBK";
        }
        InputStream pBatIs = new FileInputStream(nFileURL);
        
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		
		Element mChkDetails = new Element("ChkDetails");
		String tLineMsg = mBufReader.readLine();
		for (; null != (tLineMsg= mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
                cLogger.warn("���У�ֱ��������������һ����");
                continue;
            }
			String[] tSubMsgs = tLineMsg.split("\\|");
			
			if (!"01".equals(tSubMsgs[6])) {
                cLogger.warn("���µ��б�������ֱ��������������һ����");
                continue;
            }
			if (!"01".equals(tSubMsgs[7])) {
                cLogger.warn("�ǳб�״̬������ֱ��������������һ����");
                continue;
            }
			Element tTranDate = new Element("TranDate");
			tTranDate.setText(tSubMsgs[0]);
			
			Element tTransrNo = new Element("TransrNo");
			tTransrNo.setText(tSubMsgs[1]);
			
			Element tBankCode = new Element("BankCode");
			tBankCode.setText(cConfigEle.getChildTextTrim("bank"));
			
			String mBankZoneCodeStr = tSubMsgs[2];	//ũ�ж����ļ��ѵ�������ֳ�������
			Element tBankZoneCode = new Element("BankZoneCode");
			tBankZoneCode.setText(mBankZoneCodeStr);
			
			Element tBrNo = new Element("BrNo");
			tBrNo.setText(tSubMsgs[3]);
			
			Element tCardNo = new Element("CardNo");
			tCardNo.setText(tSubMsgs[4]);
			
			Element tTranAmnt = new Element("TranAmnt");
			tTranAmnt.setText(tSubMsgs[5]);
			
			Element tTellerNo = new Element("TellerNo");

			Element tFuncFlag = new Element("FuncFlag");
			tFuncFlag.setText(tSubMsgs[6]);
			
			Element tConfirmFlag = new Element("ConfirmFlag");
			tConfirmFlag.setText("1");

			Element tAppntName = new Element("AppntName");
			
			Element tChkDetail = new Element("ChkDetail");
			tChkDetail.addContent(tBankCode);
			tChkDetail.addContent(tTranDate);
			tChkDetail.addContent(tBankZoneCode);
			tChkDetail.addContent(tBrNo);
			tChkDetail.addContent(tTellerNo);
			tChkDetail.addContent(tFuncFlag);
			tChkDetail.addContent(tTransrNo);
			tChkDetail.addContent(tCardNo);
			tChkDetail.addContent(tAppntName);
			tChkDetail.addContent(tTranAmnt);
			tChkDetail.addContent(tConfirmFlag);
			
			mChkDetails.addContent(tChkDetail);
			
		}
		mBufReader.close();
		
		Element mTranData = new Element("TranData");
		mTranData.addContent(getHead());
		mTranData.addContent(mChkDetails);
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(mTranData);
	} 
	
	protected static Element getHead() {
		cLogger.info("Into OldBalance.getHead()...");
		
		Element mBankDate = new Element("BankDate");
		mBankDate.setText(String.valueOf(cCurDate));
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cConfigEle.getChildTextTrim("bank"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		Element mBrNo = new Element("BrNo");
		mBrNo.setText(cConfigEle.getChildText("node"));
		
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText("midplat");
		
		Element mTransrNo = new Element("TransrNo");
		
		Element mFunctionFlag = new Element("FunctionFlag");
		mFunctionFlag.setText("17");
		
		Element mBaseInfo = new Element("BaseInfo");
		mBaseInfo.addContent(mBankDate);
		mBaseInfo.addContent(mBankCode);
		mBaseInfo.addContent(mZoneNo);
		mBaseInfo.addContent(mBrNo);
		mBaseInfo.addContent(mTellerNo);
		mBaseInfo.addContent(mTransrNo);
		mBaseInfo.addContent(mFunctionFlag);

		cLogger.info("Out OldBalance.getHead()!");
		return mBaseInfo;
	}
	
	
	
	protected static Document callPostServlet(Document pInStdXmlDoc) throws Exception {
		cLogger.info("Into Balance.callPostServlet()...");
		
		Document mInXmlDoc = MidplatConf.newInstance().getConf();
		
		String mPostServletURL = mInXmlDoc.getRootElement().getChild("suf").getAttributeValue("url");
		
		
		cLogger.info("PostServletURL = " + mPostServletURL);
		URL mURL = new URL(mPostServletURL);
		URLConnection mURLConnection = mURL.openConnection();
		mURLConnection.setDoOutput(true);
		mURLConnection.setDoInput(true);
      XMLOutputter mXMLOutputter = new XMLOutputter(Format.getCompactFormat().setEncoding("GBK"));
      cLogger.info("ǰ�û���ʼ����û����ͱ���...");
		OutputStream mURLOs = mURLConnection.getOutputStream();
		mXMLOutputter.output(pInStdXmlDoc, mURLOs);
		mURLOs.close();
      
      long mStartMillis = System.currentTimeMillis();
		InputStream mURLIs = mURLConnection.getInputStream();
		Document mOutStdXml = JdomUtil.build(mURLIs);	//ͳһʹ��GBK����
		mURLIs.close();
		cLogger.debug("���û������ʱ��" + (System.currentTimeMillis()-mStartMillis)/1000.0 + "s");
		cLogger.info("���յ����û����ر��ģ�������ΪDocument��");

		cLogger.info("Out Balance.callPostServlet()!");
		cLogger.info("���˽��"+JdomUtil.toString(mOutStdXml));
		return mOutStdXml;
	}
	/**
	 * ��ȡ���ױ���ͷ
	 * @param packLen ������ĳ���
	 * @return
	 */
	private static byte[] getHeader(int packLen) {
		String tPackHeadLengthStr = String.valueOf(packLen);

		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;
		
		String des = "";
		for(int i = 0; i < 40; i++){
			des += " ";
		}
		
		String pack = "X1.0" +tPackHeadLengthStr+"1147    "+"0"+"0"+"0"+"0"+"0"+des+"00000000";
		return  pack.getBytes();
	}
	/**
	 * �����з��ͽ���
	 * @param headerStr  ����ͷ
	 * @param bodyStr  ����
	 * @param fileData  ���͵�����
	 */
	private boolean sendData(byte[] headerStr, byte[] bodyStr, String transFlag,byte[] fileData,String fileName,String funcFlag) {
		cLogger.info("����������:\n" + new String(headerStr)+new String(bodyStr)+fileData);
		ABCSocketIp = BatUtils.getConfigEle(funcFlag).getChildText("ip");
		ABCSocketPort = Integer.parseInt(BatUtils.getConfigEle(funcFlag).getChildText("ABCport"));
		cLogger.info("Ŀ��ip��˿�:" + ABCSocketIp + "::" + ABCSocketPort);
		boolean result = false;
		Socket socket = null;
		OutputStream os = null;
		InputStream in = null;
		try {
			socket = new Socket(ABCSocketIp, ABCSocketPort);
			cLogger.info("socket������");		
			socket.setSoTimeout(60000);
			os = socket.getOutputStream();
			in = socket.getInputStream();
			os.write(headerStr);
			os.write(bodyStr);
//			os.flush();
			cLogger.info("socket�����ӣ������ѷ���");		
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
//						RealfileData = new String(fileData).substring(m*4096, (m+1)*4096).getBytes();
						RealfileData = new String(fileData,m*4096,4096).getBytes();
					}else{
//						RealfileData = new String(fileData).substring(m*4096).getBytes();
						RealfileData = new String(fileData,m*4096,fileLen-m*4096).getBytes();
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
					
					cLogger.info("����·��:" + BatUtils.getConfigEle(funcFlag).getChildText("FilePath") + fileName);
					
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
}
