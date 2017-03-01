package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;

/**
 * @ClassName: File_download
 * @Description: �ļ���������
 * @author sinosoft
 * @date 2017-2-27 ����10:47:45
 */
public class File_download
{
	//����һ���������־����
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String cDate;//
	private final Element cThisConf;//��ǰ�������ýڵ�
	private final String cFuncFlag; // ���״���
	private Socket cSocket;//�׽���
	private String insu;// ���չ�˾����
	private String filename;//�ļ���
	protected Element cThisBusiConf = null;
	protected Element cThisConfRoot = null;

	/**
	 * 
	 * File_download���췽��
	 * 
	 * @param pThisConf �����ļ�
	 * @param pFuncFlag ������
	 * @param pDate ��������
	 * @param pInsu ���չ�˾����
	 */
	public File_download(Element pThisConf, String pFuncFlag, String pDate, String pInsu)
	{
		cDate = pDate;
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
		insu = pInsu;
	}

	/**
	 * 
	 * getsocket ��socket����
	 *
	 * @throws MidplatException
	 */
	public void getsocket() throws MidplatException
	{
		//ũ��socket
		int abc_socket = 0;
		//ũ��IP
		String abc_ip = "";
		try
		{
			System.out.println("=====��������=====");
			//
			JdomUtil.print(cThisConf);
			Element xml_ftp = cThisConf.getChild("socket");
			abc_ip = xml_ftp.getAttributeValue("ip");
			System.out.println("�����ļ�IP:" + abc_ip);
			abc_socket = Integer.parseInt(xml_ftp.getAttributeValue("port"));
			System.out.println("�����ļ�Socket:" + abc_socket);
			cSocket = new Socket(abc_ip, abc_socket);// 10.136.80.52
			cSocket.setSoTimeout(60000);// ���ó�ʱʱ��
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			cLogger.info("�������ж��쳣!ip��ַ:" + abc_ip + "�˿ں�:" + abc_socket);
			throw new MidplatException("�������ж��쳣!");
		}
		catch (IOException e)
		{
			cLogger.info("�������ж˳�ʱ!" + e);
			throw new MidplatException("�������ж˳�ʱ!");
		}
	}

	/**
	 * 
	 * @param cs_way
	 *            ���䷽ʽ 0: �ϴ� 1: ����
	 * @param filetype
	 *            �ļ����� 01: ֤���ļ� 02: �����ļ�
	 * @param filename
	 *            �ļ�����
	 * @param filelength
	 *            �ļ�����
	 * @return �����ļ����صı���
	 */
	public Document getxml(String cs_way, String filetype, String filename, String filelength)
	{
		Element root = new Element("ABCB2I");
		Element head = new Element("Header");
		root.addContent(head);
		Element mSerialNo = new Element("SerialNo"); // ���н�����ˮ��
		Element mInsuSerial = new Element("InsuSerial"); // ���չ�˾��ˮ��
		Element mTransDate = new Element("TransDate"); // ��������
		Element mTransTime = new Element("TransTime"); // ����ʱ��
		Element mBankCode = new Element("BankCode"); // ���д���
		Element mCorpNo = new Element("CorpNo"); // ���չ�˾����
		Element mTransCode = new Element("TransCode"); // ���ױ���
		Element mTransSide = new Element("TransSide"); // ���׷���
		Element mEntrustWay = new Element("EntrustWay"); // ί�з�ʽ
		Element mProvCode = new Element("ProvCode"); // ʡ�д���
		Element mBranchNo = new Element("BranchNo"); // �����
		Element mTlid = new Element("Tlid"); // �����
		head.addContent(mSerialNo);
		head.addContent(mInsuSerial);
		head.addContent(mTransDate);
		head.addContent(mTransTime);
		head.addContent(mBankCode);
		head.addContent(mCorpNo);
		head.addContent(mTransCode);
		head.addContent(mTransSide);
		head.addContent(mEntrustWay);
		head.addContent(mProvCode);
		head.addContent(mBranchNo);
		head.addContent(mTlid);
		int t = new Random().nextInt(9999);
		System.out.println("������ǣ�" + t);
		Date d = new Date();
		String date = new SimpleDateFormat("yyyyMMdd").format(d);
		String time = new SimpleDateFormat("HHmmss").format(d);
		String trans_no = date + time + String.valueOf(t);
		mInsuSerial.setText(trans_no);
		mTransDate.setText(date);
		mTransTime.setText(time);
		mBankCode.setText("03");
		mCorpNo.setText(insu);
		mTransCode.setText("1017");
		mTransSide.setText("0");
		Element mApp = new Element("App");
		root.addContent(mApp);
		Element body = new Element("Req");
		mApp.addContent(body);
		Element mTransFlag = new Element("TransFlag"); // ���ͷ�ʽ
		Element mFileType = new Element("FileType"); // �ļ�����
		Element mFIleName = new Element("FileName");// �ļ�����
		Element mFileLen = new Element("FileLen"); // �ļ�����
		Element FileTimeStamp = new Element("FileTimeStamp");// �ļ��޸�ʱ���
		body.addContent(mTransFlag);
		body.addContent(mFileType);
		body.addContent(mFIleName);
		body.addContent(mFileLen);
		body.addContent(FileTimeStamp);
		mTransFlag.setText(cs_way);
		mFileType.setText(filetype);
		mFIleName.setText(filename);
		// mFileLen.setText(String.valueOf(filelength));
		mFileLen.setText(filelength);
		FileTimeStamp.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Document doc = new Document(root);
		return doc;
	}

	public String receive(String mFilePath) throws Exception
	{
		cLogger.info("Into File_download.receive()...");
		OutputStream ous = null;
		String js_flag = "0";// �����Ƿ�ɹ���־ 0 -�ɹ� 1 -���
		File f = new File(mFilePath);
		if (f.exists())
		{
			cLogger.info("�ļ��Ѿ�����,��ɾ����!");
			f.delete();
		}

		while (true)
		{
			byte[] flag_byte = new byte[1];

			if (readFull(flag_byte, cSocket.getInputStream()) == 0)
			{
				// IOTrans.readFull(flag_byte, cSocket.getInputStream());
				String flag = new String(flag_byte);
				if (flag.equals("X"))
				{
					cLogger.info("�������з��ر��ģ�last_baowen");
					if (last_baowen().equals("0"))
					{ // �ɹ�ֱ���˳�
						cLogger.info("�����ļ�����1");
						break;
					}
					else
					{ // ʧ���ˣ�ҲҪ�˳�����
						js_flag = "1";
						cSocket.close();// ʧ���˹ر�socket
						break;
					}
				}
				else
				{
					// �״ζ�ȡ12λ�ļ��ܳ���
					byte[] flag_byte11 = new byte[11];

					if (readFull(flag_byte11, cSocket.getInputStream()) != 0)
					{
						return "1";
					}

					String file_length = new String(flag_byte11);
					cLogger.info("�ó��ļ��ܳ��ȣ�" + file_length);

					ous = cSocket.getOutputStream();
					ous.write("0000".getBytes());
					ous.flush();
					int receive_length = Integer.parseInt(file_length);// �����Ƿ�ɹ���־
																		// 0 -�ɹ�
																		// 1 -���
					while (js_flag.equals("0"))
					{
						if (receive_file(js_flag, mFilePath, receive_length).equals("0"))
						{
							cLogger.info("�����ļ���������....continue��");
							receive_length = 1;
							// continue;
							// break;
						}
						break;
					}
					// break;
				}
			}
			else
			{
				cLogger.info("���ճ��������⣡");
				js_flag = "1";
				cSocket.close();// ʧ���˹ر�socket
				break;
			}
		}

		cLogger.info("Out File_download.receive()!");
		return null;
	}

	public String receive_file(String flag, String mFilePath, int receivelength) throws MidplatException, IOException
	{
		cLogger.info("���ڽ����ļ��С���");
		cLogger.info("flag����" + flag + "   mFilePath...." + mFilePath);
		OutputStream ous = null;
		FileOutputStream tFos = null;
		int length = 0;
		int i = 1;// ѭ�����հ�����
		String js_flag = "0";// �����Ƿ�ɹ���־ 0 -�ɹ� 1 -���

		try
		{
			cLogger.info("��ʼ���ܳ��ȣ�" + receivelength);
			while (receivelength > 0)
			{
				byte[] flag_byte = new byte[1];

				if (readFull(flag_byte, cSocket.getInputStream()) == 0)
				{
					// IOTrans.readFull(flag_byte, cSocket.getInputStream());
					String firstflag = new String(flag_byte);
					if (firstflag.equals("X"))
					{
						cLogger.info("�������з��ر��ģ�last_baowen");
						if (last_baowen().equals("0"))
						{ // �ɹ�ֱ���˳�
							cLogger.info("�����ļ�����2");
							break;
						}
						else
						{ // ʧ���ˣ�ҲҪ�˳�����
							js_flag = "1";
							cSocket.close();// ʧ���˹ر�socket
							break;
						}
					}
					else
					{
						byte[] length_byte = new byte[11];
						InputStream ins = cSocket.getInputStream();
						// IOTrans.readFull(length_byte, ins);
						if (readFull(length_byte, ins) != 0)
						{
							return "1";
						}
						String length_str = flag + new String(length_byte);
						cLogger.info("length_str�������" + length_str);
						length = Integer.parseInt(length_str);
						cLogger.info("��" + i + "�������ļ�����:" + length);
						// ���ճɹ�֮�󷵻ظ����н��ճɹ���ʾ0000
						ous = cSocket.getOutputStream();
						ous.write("0000".getBytes());
						ous.flush();
						i++;
						// ������ʼ�����ļ�
						cLogger.info("�ļ�·��:" + mFilePath);

						try
						{
							if (length <= 4096)
							{
								InputStream inst = null;
								inst = cSocket.getInputStream();
								byte[] file_byte = new byte[length];
								// IOTrans.readFull(file_byte,inst);
								if (readFull(file_byte, inst) != 0)
								{
									return "1";
								}
								tFos = new FileOutputStream(mFilePath, true); // ׷��д��
								tFos.write(file_byte);
								receivelength = receivelength - length;
								ous = cSocket.getOutputStream();
								ous.write("0000".getBytes());
								ous.flush();
								cLogger.info("�ļ�������ɣ�·��:" + mFilePath);
							}
							else if (length - 4096 > 0)
							{
								tFos = new FileOutputStream(mFilePath, true); // ׷��д��
								byte[] file_byte = new byte[4096];
								InputStream inst = null;
								inst = cSocket.getInputStream();
								// IOTrans.readFull(file_byte,inst);
								if (readFull(file_byte, inst) != 0)
								{
									return "1";
								}
								tFos.write(file_byte);
								receivelength = receivelength - 4096;
								System.out.println("111111");
								ous = cSocket.getOutputStream();
								ous.write("0000".getBytes());
								ous.flush();
								System.out.println("2222222222");
							}
						}
						catch (FileNotFoundException e)
						{
							cLogger.info("û���ҵ��ļ���" + mFilePath);
							e.printStackTrace();
							return "1";
						}
						catch (IOException e)
						{
							cLogger.info("��ȡ�ļ������쳣��" + mFilePath);
							e.printStackTrace();
							return "1";
						}

					}
				}
				else
				{
					cLogger.info("���ճ��Ȼ��������⣡");
					js_flag = "1";
					cSocket.close();// ʧ���˹ر�socket
					break;
				}
				cLogger.info("��" + (i - 1) + "�ν����껹ʣ" + receivelength + "����");
			}
			cLogger.info("�˳�whileѭ��");
		}
		catch (Exception ex)
		{
			cLogger.info("chuxiancuowu " + ex);
			cLogger.info("�ͻ����Ѿ��Ͽ�����" + ex);
			return "1";
		}
		finally
		{
			try
			{
				cLogger.info("finally������Ҫ�ܵ�");
				ous.flush();
				tFos.close();
			}
			catch (IOException ioe)
			{
				cLogger.error("�ر�������쳣");
				return "1";
			}

		}

		cLogger.info("�����������˱��η���");
		// ���ճɹ�֮������з��سɹ���־
		try
		{
			cLogger.info("��עsocket��û�йرգ�" + cSocket.isConnected());
			if (cSocket.isConnected())
			{
				ous = cSocket.getOutputStream();
				ous.write("0000".getBytes());
				ous.flush();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "1";
		}

		cLogger.info("�����ļ�������ɡ���");
		return "0";
	}

	/**
	 * �������еķ��ر���
	 * 
	 * @return
	 */
	public String last_baowen() throws MidplatException
	{
		// ��ͷ72λ
		byte[] mHeadBytes = new byte[72];
		System.out.println("���ӵ�ַ��" + cSocket.getRemoteSocketAddress());
		try
		{
			// IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
			if (readFull(mHeadBytes, cSocket.getInputStream()) != 0)
			{
				return "1";
			}
		}
		catch (IOException e)
		{
			throw new MidplatException("��ȡ����72λ����ͷ����!");
		}
		String package_head = new String(mHeadBytes);
		// 2-12 λ�Ǳ����峤��
		cLogger.info("package_head:" + package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(2, 11).trim()); // ���峤��
		System.out.println("mBodyLen:" + mBodyLen);
		cLogger.info("mBodyLen:" + mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; // ���е�body�ֽڲ���xml����
		// byte[] mBodyBytes = new byte[100]; //���е�body�ֽڲ���xml����
		cLogger.info("�鿴�Ƿ�" + cSocket.isClosed());
		try
		{
			// IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
			if (readFull(mBodyBytes, cSocket.getInputStream()) != 0)
			{
				return "1";
			}
			// cSocket.shutdownInput();
			// cLogger.info("���ܿ�ʼ:============================");
			// String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
			String axx = new String(mBodyBytes, "UTF-8");
			// cLogger.info("���ܽ���:============================");
			// String axx = new String (mBodyBytes,"UTF-8");
			StringBuffer abc_xml = new StringBuffer();
			abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			abc_xml.append("\n");
			abc_xml.append(axx);
			byte[] all_xml = abc_xml.toString().getBytes("UTF-8");// #
			Document mXmlDoc_bank = JdomUtil.build(all_xml, "UTF-8"); // #

			cLogger.info("�ļ��ϴ��������з��͹����ı���:");
			JdomUtil.print(mXmlDoc_bank);

			Element body = mXmlDoc_bank.getRootElement().getChild("App").getChild("Ret");
			int filelength = Integer.parseInt(body.getChildText("FileLen"));
			String downfilename = body.getChildText("FileName");// �ļ�����
			cLogger.info("�ļ��ϴ����ص��ļ����֣�" + downfilename);
			// String downfilename1 =downfilename.substring(0, 3);
			// �ļ����� �ļ�����
			// POLICY$.YYYYMMDD �±��б����������ļ�
			// VCH*$&.YYYYMMDD ƾ֤�����ļ�
			// FAPPLY$.YYYYMMDD ��ʵʱ������ˮ��ϸ
			// BQAPPLY$.YYYYMMDD ��ȫ���������ļ�
			// INVALID$.YYYYMMDD �˱��̳������ļ�
			// FRESULT$.YYYYMMDD ��ʵʱ��������ļ�
			// String filedetails="";
			// ��β��Ե�ʱ���ٿ� ---- ˵�ļ���������̱���. ��������Ƿ��ر���,�ļ����ں��档
			// if(cFuncFlag.equals("RZDZ")){//�±��б����������ļ������ն��ˣ�
			// byte[] mfileBodyBytes = new byte[filelength]; //���е�body�ֽڲ���xml����
			// if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			// return "1";
			// }
			// filedetails= new String(mfileBodyBytes);
			// cSocket.shutdownInput();
			// String local_host=cThisConf.getChildText("localPath").trim();
			// makeLocalBalanceFile(local_host,downfilename,filedetails);
			// }else if(cFuncFlag.equals("YCXXCD")){//�˱��̳������ļ�
			// String RetCode =
			// mXmlDoc_bank.getRootElement().getChild("Header").getChildText("RetCode");
			// cLogger.info("�˱��̳������ļ��ϴ�������(000000Ϊ�ɹ�)��"+RetCode);
			// filedetails =RetCode; //�̳���Ϣ���ݷ��ص������и��Ĵ������
			// }else if(cFuncFlag.equals("MYGX")){
			// byte[] mfileBodyBytes = new byte[filelength]; //���е�body�ֽڲ���xml����
			// if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			// return "1";
			// }
			// filedetails= new String(mfileBodyBytes);
			// cSocket.shutdownInput();
			// String local_host=SysInfo.cHome+"key/"; //֤���ļ�·��
			// makeLocalBalanceFile(local_host,downfilename,filedetails);
			// }
			cSocket.close();// �ӵ�����֮��socketͨѶ�ر�

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "1";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "1";
		}
		return "0";
	}

	/**
	 * 
	 * @param pOutNoStd
	 *            ���͸����еı���
	 * @param file
	 *            �����ļ�������
	 * @throws Exception
	 *             �׳��쳣
	 */
	public void send(Document pOutNoStd, String file) throws Exception
	{
		cLogger.info("Into File_download.send()...");

		// cLogger.info("���͸����еĶ��˱���file:"+file);
		JdomUtil.print(pOutNoStd);

		// String xmlStr = JdomUtil.toString(pOutNoStd);
		String xmlStr = JdomUtil.toString(pOutNoStd);
		// System.out.println("xxxxxssss:"+xmlStr);
		// xmlStr=xmlStr.trim();
		// System.out.println("xxxxxssss:"+xmlStr);
		// xmlStr=xmlStr.replace('\r', ' ');
		System.out.println("xxxxxssss:" + xmlStr);
		xmlStr = xmlStr.replace('\n', ' ');
		System.out.println("xxxxxssss:" + xmlStr);
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		// cLogger.info("�����е�xml�ַ�����"+xmlStr);

		cLogger.info("��ʼ���ܱ���");
		// xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		// String endxmlStr=AES.Encrypt(xmlStr);
		String endxmlStr = xmlStr; // ������;
		byte[] outBytes = endxmlStr.getBytes();
		cLogger.info("���ܱ��Ľ���!");

		cLogger.info("����73λ����ͷ:");
		String sHeadBytes = AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0" + sHeadBytes + insu + "    00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();
		cLogger.info("����73λ����ͷ����");
		cLogger.info("���͸����еı���ͷΪ��" + sHeadBytes);

		byte[] file_byte = file.getBytes();
		System.out.println("���ͱ���ͷ���ȣ�" + array.length);
		System.out.println("���ͱ����峤�ȣ�" + outBytes.length);
		System.out.println("�����ļ����ȣ�" + file_byte.length);
		OutputStream ous = null;
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		try
		{
			String str1 = new String(array, "GBK");
			System.out.println("����ͷstr1==:" + str1);
			String str2 = new String(outBytes, "GBK");
			System.out.println("������str2==:" + str2);

			ous = cSocket.getOutputStream();
			ous.write(array);
			ous.write(xmlStr.getBytes());
			cLogger.info("�����е�xml�ַ�����" + xmlStr);

			// if(file.length()>0 ){
			// // ous.flush();
			// try {
			// ous.write(file_byte);
			// System.out.println("��������");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			if (file.length() > 0)
			{
				// ous.flush();
				cLogger.info("���ʹ��ж����ļ����ϴ����ױ��ģ�");
				byte[] returnbyte = new byte[4];
				if (readFull(returnbyte, cSocket.getInputStream()) == 0)
				{
					String headflag = new String(returnbyte);
					cLogger.info("headflag:" + headflag);
					if (headflag.equals("0000"))
					{// ���з�0000��ʾ��ʼ����
					// ous = cSocket.getOutputStream();
						String str3 = new String(file_byte, "GBK");
						StringBuffer s = new StringBuffer();
						int k = String.valueOf(file_byte.length).length();
						System.out.println("socket �ر�����" + cSocket.isClosed());
						for (int i = 0; i < 12 - k; i++)
						{
							// ous.write("0".getBytes());
							s.append("0");
							// cLogger.info("��"+i+"�η���0");
						}
						s.append(file_byte.length);
						cLogger.info("12Ϊ���ȣ�" + String.valueOf(s) + "=12Ϊ�ֽڣ�" + String.valueOf(s).getBytes());
						ous.write(String.valueOf(s).getBytes());
						// ous.flush();
						ous.write(file_byte);
						byte[] send_byte = new byte[4];
						readFull(send_byte, cSocket.getInputStream());
						String sendflag = new String(send_byte);
						cLogger.info("sendflag:" + sendflag);
						if (sendflag.equals("0000"))
						{
							cLogger.info("������0000��ʾ�����ļ���������");
						}
						System.out.println("�����ļ�����str3==:" + str3);
					}

					// socketδ�رգ���ȡ�����ϴ����׷��ر�����λ
					byte[] flag_byte = new byte[1];
					if (readFull(flag_byte, cSocket.getInputStream()) == 0)
					{
						String firstflag = new String(flag_byte);
						cLogger.info("firstflag:" + firstflag);
						if (firstflag.equals("X"))
						{
							cLogger.info("�ϴ��ļ������󣬽������з��ر��ģ�");
							if (last_baowen().equals("0"))
							{ // �ɹ��������з��ر���ֱ���˳�
								cLogger.info("�����ļ�������");
							}
						}
						else
						{
							cLogger.info("����δ�����ϴ����ױ��ģ�");
						}
					}
					else
					{
						cLogger.info("����δ����");
					}
				}
				else
				{
					cLogger.info("����û�з���0000��ȷ�Ͻ��ն��˰����Ⱥ�����");
				}
			}
			else
			{
				cLogger.info("���Ͳ��������ļ����ϴ����ױ��ģ�");
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			cLogger.info("File_download.send " + ex);
			throw new MidplatException("���ж�Socket�ر���!");
		}
		finally
		{
			// try{
			// ous.flush();
			// ous.close();
			// }catch(IOException ioe){
			// ioe.printStackTrace();
			// throw new MidplatException("�ر�������쳣!");
			// }
		}
		cLogger.info("Out File_download.send()!");
	}

	public final void close()
	{
		try
		{
			cSocket.close();
		}
		catch (IOException ex)
		{
			cLogger.debug("Socket�����ѹرգ�", ex);
		}
	}

	/**
	 * 
	 * @param filename
	 *            �ļ�����
	 * @param peizhi
	 *            ���õ�xml�ڵ�
	 * @return ���� Ҫ�ϴ������е��ļ������ұ��뷽ʽ��utf-8��ʽ��
	 * @throws IOException
	 */
	public String getfiledetails(String filename, Element peizhi) throws IOException
	{
		String filepath = cThisConf.getChildText("localPath").trim() + filename;
		// BufferedReader br = new BufferedReader(new InputStreamReader(new
		// FileInputStream("E:\\temp_workspace\\ABC_YBT_Project\\abc\\INVALID1108.20140118"),"GBK"));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "GBK"));
		StringBuffer strbuf = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null)
		{
			strbuf.append(line);
			strbuf.append("\n");

		}
		System.out.println("xggfgdg==" + strbuf.toString());
		String lastfile = strbuf.toString();// new
											// String(strbuf.toString().getBytes("UTF-8"));
		System.out.println("���������ļ�����1��" + lastfile);
		System.out.println("���������ļ�����2��" + new String(lastfile.getBytes("UTF-8")));
		return lastfile;
	}

	/**
	 * @Title: bank_dz_file
	 * @Description: 
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public void bank_dz_file() throws Exception
	{

		getsocket(); // ��ȡsocket ����
		Document dox = null; // Ҫ�������еı���
		String file = ""; // �ļ�����
		String filedetails = ""; // ���з��͵��ļ�����
		String mFilePath = ""; // ����ı���·��
		if (cFuncFlag.equals("RZDZ"))
		{ // ���ն����ļ�����
			// cs_way 0: �ϴ� 1: ����
			// filetype 01: ֤���ļ� 02: �����ļ�
			filename = "POLICY" + insu + "." + cDate; // ���ʱ��ô���,������Ҫ��
			dox = getxml("1", "02", filename, "00000000");
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDJGWJ"))
		{ // ��ʵʱ��������ļ� FRESULT$.YYYYMMDD
		// cThisConfRoot = NewAbcConf.newInstance().getConf().getRootElement();
		// cThisBusiConf = (Element) XPath.selectSingleNode(
		// cThisConfRoot, "business[cfuncFlag='"+cFuncFlag+"']");
			filename = "FRESULT" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("��֯�õ��ļ���" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("��֯�õ��ļ����ȣ�" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("TBYCSJWJ"))
		{ // �˱��̳������ļ� INVALID$.YYYYMMDD
			filename = "INVALID" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("��֯�õ��ļ���" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("��֯�õ��ļ����ȣ�" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDXZMX"))
		{ // ��ʵʱ����������ϸ FRESULTKZ$.YYYYMMDD
			filename = "FRESULTKZ" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("��֯�õ��ļ���" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("��֯�õ��ļ����ȣ�" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDJGWJ"))
		{ // �ֹ�������ļ� SRESULT$.YYYYMMDD
			filename = "SRESULT" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("��֯�õ��ļ���" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("��֯�õ��ļ����ȣ�" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDCDXZMX"))
		{ // �ֹ�������������ϸ SRESULTKZ$.YYYYMMDD
			filename = "SRESULTKZ" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("��֯�õ��ļ���" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("��֯�õ��ļ����ȣ�" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDJGHP"))
		{ // �ֹ�������ļ�-���д��������� SRESULT.BANK$.YYYYMMDD
			filename = "SRESULT" + "." + "BANK" + insu + "." + cDate;
			System.out.println("��֯�õ��ļ���" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("��֯�õ��ļ����ȣ�" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDLSMX"))
		{ // ��ʵʱ������ˮ��ϸ FAPPLY$.YYYYMMDD
			filename = "FAPPLY" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("��֯�õ��ļ���" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("��֯�õ��ļ����ȣ�" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("BQSQ"))
		{ // ��ȫ���������ļ� BQAPPLY$.YYYYMMDD
			filename = "BQAPPLY" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("��֯�õ��ļ���" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("��֯�õ��ļ����ȣ�" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("TBYCHP"))
		{ // �˱��̳������ļ�-���� INVALID.BANK$.YYYYMMDD
			filename = "INVALID" + "." + "BANK" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("��֯�õ��ļ���" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("��֯�õ��ļ����ȣ�" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDHP"))
		{ // ��ʵʱ��������ļ�-���� FRESULT.BANK$.YYYYMMDD
			filename = "FRESULT" + "." + "BANK" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("��֯�õ��ļ���" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("��֯�õ��ļ����ȣ�" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("MYGX"))
		{ // ��Կ���½���
			filename = "cacert.crt"; // ֤���ļ�����
			dox = getxml("1", "01", filename, "00000000"); // ��֯�ϴ����ؽ��׷�������,����֤���ļ�
			String keyPath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("KeyPath");     
			mFilePath = keyPath + "key/" + filename;
//			mFilePath = SysInfo.cHome + "key/" + filename;
			cLogger.info(cFuncFlag + "==========��֯�õı���:");
			JdomUtil.print(dox);
		}
		try
		{
			File f = new File(mFilePath);
			System.out.println("�ļ�����·����:" + mFilePath);
			if (dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag").equals("1"))
			{
				if (!f.exists())
				{
					send(dox, file);
					System.out.println("���͸��������");
					System.out.println("socket �ر�����" + cSocket.isClosed());
					// �ϴ����ײ�receiveũ�еı���
					String flag = dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag");
					if (flag.equals("1"))
					{
						receive(mFilePath);
					}

					System.out.println("�ļ�����:" + filedetails);
				}
				else
				{
					cLogger.info("�ļ��Ѵ��� �����ٴ�����");
				}
			}
			else
			{
				cLogger.info("�����ļ����ݣ�" + file);
				send(dox, file);
				System.out.println("���͸��������");
				System.out.println("socket �ر�����" + cSocket.isClosed());

				System.out.println("�ļ�����:" + file);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception
	{
	}

	public void makeLocalBalanceFile(String localPath, String fileName, String fileContent) throws Exception
	{
		File file = new File(localPath + fileName);
		if (!file.exists())
			file.createNewFile();

		cLogger.info("���ض����ļ�·����" + localPath);
		cLogger.info("���ض����ļ����ƣ�" + fileName);
		cLogger.info("fileContent == " + fileContent);

		FileWriter fw = null;
		fw = new FileWriter(localPath + fileName);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(fileContent);
		bw.flush();
		// bw.close();//test
		fw.close();
	}

	public int readFull(byte[] pByte, InputStream pIs) throws IOException
	{
		cLogger.info("Into readFull() =====" + pByte.length);
		for (int tReadSize = 0; tReadSize < pByte.length;)
		{
			int tRead = pIs.read(pByte, tReadSize, 1);
			// cLogger.info("tReadSize=="+tReadSize+"       tRead=="+tRead);
			if (-1 == tRead)
			{
				return 1;
			}
			tReadSize += tRead;
		}
		return 0;
	}

}