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
	private String cDate;//��������
	private final Element cThisConf;//��ǰ���н��������ļ�
	private final String cFuncFlag; // ���״���
	private Socket cSocket;//�׽���
	private String insu;// ���չ�˾����
	private String filename;//�ļ���
	protected Element cThisBusiConf = null;//��ǰ�������ýڵ�
	protected Element cThisConfRoot = null;//��ǰ���н��������ļ����ڵ�

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
		//ũ�ж˿�
		int abc_socket = 0;
		//ũ��IP
		String abc_ip = "";
		try
		{
			//=====��������=====
			System.out.println("=====��������=====");
			//����ǰ���н��������ļ���ӡ������̨�� GBK���룬����3�ո�
			JdomUtil.print(cThisConf);
			//��ȡ��ǰ���н��������ļ�socket�ӽڵ�
			Element xml_ftp = cThisConf.getChild("socket");
			//��ȡũ��IP[socket�ڵ���ip����ֵ]
			abc_ip = xml_ftp.getAttributeValue("ip");
			//�����ļ�IP:ũ��IP
			System.out.println("�����ļ�IP:" + abc_ip);
			//ũ�ж˿�[socket�ڵ���port����ֵ]
			abc_socket = Integer.parseInt(xml_ftp.getAttributeValue("port"));
			//�����ļ�Socket:ũ�ж˿�
			System.out.println("�����ļ�Socket:" + abc_socket);
			//�½��׽���[ũ��IP,ũ�ж˿�]
			cSocket = new Socket(abc_ip, abc_socket);// 10.136.80.52
			//���ô���60000���볬ʱֵ�� SO_TIMEOUT
			cSocket.setSoTimeout(60000);// ���ó�ʱʱ��
		}
		catch (UnknownHostException e)//ָʾ���� IP ��ַ�޷�ȷ�����׳����쳣
		{
			//��ӡ��ջ���� 
			e.printStackTrace();
			//�������ж��쳣!ip��ַ:ũ��IP�˿ں�:ũ�ж˿�
			cLogger.info("�������ж��쳣!ip��ַ:" + abc_ip + "�˿ں�:" + abc_socket);
			//�������ж��쳣!
			throw new MidplatException("�������ж��쳣!");
		}
		catch (IOException e)
		{
			//�������ж˳�ʱ!
			cLogger.info("�������ж˳�ʱ!" + e);
			//�������ж˳�ʱ!
			throw new MidplatException("�������ж˳�ʱ!");
		}
	}

	/**
	 * ��ȡ�ļ��ϴ����طǱ�׼���뱨��
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
		//�½��Ǳ�׼���뱨�ĸ��ڵ�
		Element root = new Element("ABCB2I");
		//�½��Ǳ�׼���뱨��ͷ
		Element head = new Element("Header");
		//�Ǳ�׼���뱨�ĸ��ڵ���뱨��ͷ�ڵ�
		root.addContent(head);
		//�½����н�����ˮ�Žڵ�
		Element mSerialNo = new Element("SerialNo"); // ���н�����ˮ��
		//�½����չ�˾��ˮ�Žڵ�
		Element mInsuSerial = new Element("InsuSerial"); // ���չ�˾��ˮ��
		//�½��������ڽڵ�
		Element mTransDate = new Element("TransDate"); // ��������
		//�½�����ʱ��ڵ�
		Element mTransTime = new Element("TransTime"); // ����ʱ��
		//�½����д���ڵ�
		Element mBankCode = new Element("BankCode"); // ���д���
		//�½����չ�˾����ڵ�
		Element mCorpNo = new Element("CorpNo"); // ���չ�˾����
		//�½����ױ���ڵ�
		Element mTransCode = new Element("TransCode"); // ���ױ���
		//�½����׷��𷽽ڵ�
		Element mTransSide = new Element("TransSide"); // ���׷���
		//�½�ί�з�ʽ�ڵ�
		Element mEntrustWay = new Element("EntrustWay"); // ί�з�ʽ
		//�½�ʡ�д���ڵ�
		Element mProvCode = new Element("ProvCode"); // ʡ�д���
		//�½�����Žڵ�
		Element mBranchNo = new Element("BranchNo"); // �����
		//�½���Ա�ڵ�
		Element mTlid = new Element("Tlid"); // ��Ա
		/**
		 * �Ǳ�׼���뱨��ͷ�������н�����ˮ�š����չ�˾��ˮ�š��������ڡ�����ʱ�䡢���д��롢
		 * ���չ�˾���롢���ױ��롢���׷��𷽡�ί�з�ʽ��ʡ�д��롢����š���Ա�ڵ�
		 * */
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
		//����һ��α�����������ȡ�Դ���������������еġ��� 0����������9999����������֮����ȷֲ��� int 
		int t = new Random().nextInt(9999);
		//������ǣ�α�����
		System.out.println("������ǣ�" + t);
		//���� Date ���󲢳�ʼ���˶����Ա�ʾ��������ʱ�䣨��ȷ�����룩
		Date d = new Date();
		//�ø�����ģʽ��Ĭ�����Ի��������ڸ�ʽ���Ź��� SimpleDateFormat
		//8λ�����ַ���
		String date = new SimpleDateFormat("yyyyMMdd").format(d);
		//6λʱ���ַ���
		String time = new SimpleDateFormat("HHmmss").format(d);
		//���׺�[8λ�����ַ���+6λʱ���ַ���+α�����]
		String trans_no = date + time + String.valueOf(t);
		//���չ�˾��ˮ�Žڵ������ı�Ϊ���׺�
		mInsuSerial.setText(trans_no);
		//�������ڽڵ������ı�Ϊ8λ�����ַ���
		mTransDate.setText(date);
		//����ʱ��ڵ������ı�Ϊ6λʱ���ַ���
		mTransTime.setText(time);
		//���д���ڵ������ı�Ϊ03
		mBankCode.setText("03");
		//���չ�˾����ڵ������ı�Ϊ���չ�˾����
		mCorpNo.setText(insu);
		//���ױ���ڵ������ı�Ϊ1017[�ļ��ϴ�����]
		mTransCode.setText("1017");
		//���׷��𷽽ڵ������ı�Ϊ0[���չ�˾]
		mTransSide.setText("0");
		//�½��Ǳ�׼���뱨����
		Element mApp = new Element("App");
		//�Ǳ�׼���뱨�ĸ��ڵ���뱨����ڵ�
		root.addContent(mApp);
		//�½��Ǳ�׼���������Ľڵ�
		Element body = new Element("Req");
		//�Ǳ�׼���뱨����������������Ľڵ�
		mApp.addContent(body);
		//�½����ͷ�ʽ�ڵ�
		Element mTransFlag = new Element("TransFlag"); // ���ͷ�ʽ
		//�½��ļ����ͽڵ�
		Element mFileType = new Element("FileType"); // �ļ�����
		//�½��ļ����ƽڵ�
		Element mFIleName = new Element("FileName");// �ļ�����
		//�½��ļ����Ƚڵ�
		Element mFileLen = new Element("FileLen"); // �ļ�����
		//�½��ļ��޸�ʱ����ڵ�
		Element FileTimeStamp = new Element("FileTimeStamp");// �ļ��޸�ʱ���
		/**�Ǳ�׼���������Ľڵ���봫�ͷ�ʽ[���䷽ʽ�ַ���]���ļ�����[�ļ������ַ���]���ļ�����[�ļ������ַ���]���ļ�����[�ļ������ַ���]���ļ��޸�ʱ���[�����ڸ�ʽ����ǰ���ڶ���]**/
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
		//�½��Ǳ�׼���뱨��
		Document doc = new Document(root);
		//���طǱ�׼���뱨��
		return doc;
	}

	/**
	 * @Title: receive
	 * @Description: �����ļ�������·��
	 * @param mFilePath ����ı���·��
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public String receive(String mFilePath) throws Exception
	{
		//Into File_download.receive()...
		cLogger.info("Into File_download.receive()...");
		//����ֽ���
		OutputStream ous = null;
		//���ձ�־
		String js_flag = "0";// �����Ƿ�ɹ���־ 0 -�ɹ� 1 -���
		//�½��ļ�����[����ı���·��]
		File f = new File(mFilePath);
		//�����ļ�����
		if (f.exists())
		{
			//�ļ��Ѿ�����,��ɾ����!
			cLogger.info("�ļ��Ѿ�����,��ɾ����!");
			// ɾ�����汾��·����ʾ���ļ�
			f.delete();
		}
		
		//����ѭ��
		while (true)
		{
			//�½���־�ֽ�����
			byte[] flag_byte = new byte[1];
			
			//��ȡȫ��[��־�ֽ�����,�׽���������]���
			if (readFull(flag_byte, cSocket.getInputStream()) == 0)
//			if (readFull(flag_byte, cSocket.getInputStream()) == 1)
			{
				// IOTrans.readFull(flag_byte, cSocket.getInputStream());
				//�½���־�ַ���
				String flag = new String(flag_byte);
				//��־�ַ���[��������]ΪX
				if (flag.equals("X"))
				{
					//�������з��ر��ģ�last_baowen
					cLogger.info("�������з��ر��ģ�last_baowen");
					//�������еķ��ر��ĳɹ�
					if (last_baowen().equals("0"))
					{ // �ɹ�ֱ���˳�
						//�����ļ�����1
						cLogger.info("�����ļ�����1");
						break;
					}
					else
					{ // ʧ���ˣ�ҲҪ�˳�����
						//���ձ�־��Ϊ1[���ʧ��]
						js_flag = "1";
						//�ر��׽���
						cSocket.close();// ʧ���˹ر�socket
						break;
					}
				}
				else
				{
					// �״ζ�ȡ12λ�ļ��ܳ���
					//�½�12λ��־�ֽ�����
					byte[] flag_byte11 = new byte[11];

					//��ȡȫ��[12λ��־�ֽ�����,�׽���������]ʧ��
					if (readFull(flag_byte11, cSocket.getInputStream()) != 0)
					{
						//����1[ʧ��]
						return "1";
					}

					//�½��ļ������ַ���
					String file_length = new String(flag_byte11);
					//�ó��ļ��ܳ��ȣ��ļ������ַ���
					cLogger.info("�ó��ļ��ܳ��ȣ�" + file_length);
					
					//��ȡ�׽��������
					ous = cSocket.getOutputStream();
					// �� b.length ���ֽڴ��ֽ�����[0000]д��������
					ous.write("0000".getBytes());
					//ˢ���׽����������ǿ��д�����л��������ֽ�
					ous.flush();
					//���ճ���[�ļ�����]
					int receive_length = Integer.parseInt(file_length);// �����Ƿ�ɹ���־
																		// 0 -�ɹ�
																		// 1 -���
					//���ձ�־Ϊ�ɹ�
					while (js_flag.equals("0"))
					{
						//�����ļ�[���ձ�־,����ı���·��,���ճ���]�ɹ�
						if (receive_file(js_flag, mFilePath, receive_length).equals("0"))
						{
							//�����ļ���������....continue��
							cLogger.info("�����ļ���������....continue��");
							//���ճ�����Ϊ1
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

	/**
	 * @Title: receive_file
	 * @Description: �����ļ�
	 * @param flag ���ձ�־
	 * @param mFilePath ����ı���·��
	 * @param receivelength ���ճ���
	 * @return
	 * @throws MidplatException
	 * @throws IOException
	 * @return String
	 * @throws
	 */
	public String receive_file(String flag, String mFilePath, int receivelength) throws MidplatException, IOException
	{
		//���ڽ����ļ��С���
		cLogger.info("���ڽ����ļ��С���");
		//flag�������ձ�־   mFilePath....����ı���·��
		cLogger.info("flag����" + flag + "   mFilePath...." + mFilePath);
		//����ֽ���
		OutputStream ous = null;
		//�ļ�����ֽ���
		FileOutputStream tFos = null;
		//����
		int length = 0;
		//���հ�����
		int i = 1;// ѭ�����հ�����
		//���ձ�־[Ĭ��0:�ɹ�]
		String js_flag = "0";// �����Ƿ�ɹ���־ 0 -�ɹ� 1 -���

		try
		{
			//��ʼ���ܳ��ȣ����ճ���
			cLogger.info("��ʼ���ܳ��ȣ�" + receivelength);
			//���ճ��ȷ�0
			while (receivelength > 0)
			{
				//1λ����ֽ�����
				byte[] flag_byte = new byte[1];
				
				//��ȡȫ��[1λ����ֽ�����,�׽���������]�ɹ�
				if (readFull(flag_byte, cSocket.getInputStream()) == 0)
				{
					// IOTrans.readFull(flag_byte, cSocket.getInputStream());
					//�½��׸���־�ַ���[1λ����ֽ�����:��������]
					String firstflag = new String(flag_byte);
					//�׸���־�ַ���ΪX
					if (firstflag.equals("X"))
					{
						//�������з��ر��ģ�last_baowen
						cLogger.info("�������з��ر��ģ�last_baowen");
						//�������еķ��ر��ĳɹ�
						if (last_baowen().equals("0"))
						{ // �ɹ�ֱ���˳�
							//�����ļ�����2
							cLogger.info("�����ļ�����2");
							break;
						}
						else
						{ // ʧ���ˣ�ҲҪ�˳�����
							//���ձ�־��Ϊ���
							js_flag = "1";
							//�ر��׽���
							cSocket.close();// ʧ���˹ر�socket
							break;
						}
					}
					else
					{
						//12λ�����ֽ�����
						byte[] length_byte = new byte[11];
						//��ȡ�׽��������ֽ���
						InputStream ins = cSocket.getInputStream();
						// IOTrans.readFull(length_byte, ins);
						//��ȡȫ��[12λ�����ֽ�����,�׽��������ֽ���]ʧ��
						if (readFull(length_byte, ins) != 0)
						{
							//����1[ʧ��]
							return "1";
						}
						//�½������ַ���[12λ�����ֽ�����]
						String length_str = flag + new String(length_byte);
						//length_str������������ַ���
						cLogger.info("length_str�������" + length_str);
						//����[�����ַ�����ֵ]
						length = Integer.parseInt(length_str);
						//��i�ΰ������ļ�����:����
						cLogger.info("��" + i + "�������ļ�����:" + length);
						// ���ճɹ�֮�󷵻ظ����н��ճɹ���ʾ0000
						//��ȡ�׽��������
						ous = cSocket.getOutputStream();
						//д��b.length���ֽڵ��׽��������
						ous.write("0000".getBytes());
						//ˢ���׽����������ǿ��д�����л��������ֽ�
						ous.flush();
						//ѭ�����հ������ۼ�
						i++;
						// ������ʼ�����ļ�
						//�ļ�·��:����ı���·��
						cLogger.info("�ļ�·��:" + mFilePath);

						try
						{
							//���Ȳ�����4 MB
							if (length <= 4096)
							{
								//�����ֽ���
								InputStream inst = null;
								//��ȡ�׽��������ֽ���
								inst = cSocket.getInputStream();
								//�ļ��ֽ�����[�ļ�����]
								byte[] file_byte = new byte[length];
								// IOTrans.readFull(file_byte,inst);
								//��ȡȫ��[�ļ��ֽ�����,�׽��������ֽ���]ʧ��
								if (readFull(file_byte, inst) != 0)
								{
									//����1[ʧ��]
									return "1";
								}
								//
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
		//�½���ͷ�ֽ�����
		byte[] mHeadBytes = new byte[72];
		//���ӵ�ַ���׽������ӵĶ˵�ĵ�ַ[δ���ӷ��� null]
		System.out.println("���ӵ�ַ��" + cSocket.getRemoteSocketAddress());
		try
		{
			// IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
			//��ȡȫ��[��ͷ�ֽ�����,�׽���������]���
			if (readFull(mHeadBytes, cSocket.getInputStream()) != 0)
			{
				//����1
				return "1";
			}
		}
		catch (IOException e)
		{
			//��ȡ����72λ����ͷ����!
			throw new MidplatException("��ȡ����72λ����ͷ����!");
		}
		//�½���ͷ�ַ���[��ͷ�ֽ�����]
		String package_head = new String(mHeadBytes);
		// 2-12 λ�Ǳ����峤��
		//package_head:��ͷ�ַ���
		cLogger.info("package_head:" + package_head);
		//���峤��[��ͷ�ַ�����2����ʼ��ֱ������ 11 �����ַ�����ǰ���հ׺�β���հ�]
		int mBodyLen = Integer.parseInt(package_head.substring(2, 11).trim()); // ���峤��
		//mBodyLen:���峤��
		System.out.println("mBodyLen:" + mBodyLen);
		//mBodyLen:���峤��
		cLogger.info("mBodyLen:" + mBodyLen);
		//�½������ֽ�����
		byte[] mBodyBytes = new byte[mBodyLen]; // ���е�body�ֽڲ���xml����
		// byte[] mBodyBytes = new byte[100]; //���е�body�ֽڲ���xml����
		//�鿴�Ƿ� �׽��ֵĹر�״̬
		cLogger.info("�鿴�Ƿ�" + cSocket.isClosed());
		try
		{
			// IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
			//��ȡȫ��[�����ֽ�����,�׽���������]���
			if (readFull(mBodyBytes, cSocket.getInputStream()) != 0)
			{
				//����1
				return "1";
			}
			// cSocket.shutdownInput();
			// cLogger.info("���ܿ�ʼ:============================");
			// String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
			//�½�����UTF-8�����ַ���
			String axx = new String(mBodyBytes, "UTF-8");
			// cLogger.info("���ܽ���:============================");
			// String axx = new String (mBodyBytes,"UTF-8");
			//�½�ũ�б����ַ�������
			StringBuffer abc_xml = new StringBuffer();
			//׷��<?xml version="1.0" encoding="UTF-8"?>
			abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			//׷�ӻس����з�
			abc_xml.append("\n");
			//׷�Ӱ����ַ���
			abc_xml.append(axx);
			//��ȡũ�б��Ļ����ַ���UTF-8�����ֽ�����
			byte[] all_xml = abc_xml.toString().getBytes("UTF-8");// #
			//����UTF-8���빹��һ�����б��ģ����Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)
			Document mXmlDoc_bank = JdomUtil.build(all_xml, "UTF-8"); // #
			//�ļ��ϴ��������з��͹����ı���:
			cLogger.info("�ļ��ϴ��������з��͹����ı���:");
			//�����б��Ĵ�ӡ������̨�� GBK���룬����3�ո�
			JdomUtil.print(mXmlDoc_bank);

			//��ȡ���б���Ӧ���Ľڵ�
			Element body = mXmlDoc_bank.getRootElement().getChild("App").getChild("Ret");
			//��ȡӦ�����ļ������ӽڵ��ı���ֵ
			int filelength = Integer.parseInt(body.getChildText("FileLen"));
			//��ȡӦ�����ļ������ӽڵ��ı�
			String downfilename = body.getChildText("FileName");// �ļ�����
			//�ļ��ϴ����ص��ļ����֣������ļ�����
			cLogger.info("�ļ��ϴ����ص��ļ����֣�" + downfilename);
			 String downfilename1 =downfilename.substring(0, 3);
			// �ļ����� �ļ�����
			// POLICY$.YYYYMMDD �±��б����������ļ�
			// VCH*$&.YYYYMMDD ƾ֤�����ļ�
			// FAPPLY$.YYYYMMDD ��ʵʱ������ˮ��ϸ
			// BQAPPLY$.YYYYMMDD ��ȫ���������ļ�
			// INVALID$.YYYYMMDD �˱��̳������ļ�
			// FRESULT$.YYYYMMDD ��ʵʱ��������ļ�
			 String filedetails="";
			// ��β��Ե�ʱ���ٿ� ---- ˵�ļ���������̱���. ��������Ƿ��ر���,�ļ����ں��档
			 if(cFuncFlag.equals("RZDZ")){//�±��б����������ļ������ն��ˣ�
			 byte[] mfileBodyBytes = new byte[filelength]; //���е�body�ֽڲ���xml����
			 if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			 return "1";
			 }
			 filedetails= new String(mfileBodyBytes);
			 cSocket.shutdownInput();
			 String local_host=cThisConf.getChildText("localPath").trim();
			 makeLocalBalanceFile(local_host,downfilename,filedetails);
			 }else if(cFuncFlag.equals("YCXXCD")){//�˱��̳������ļ�
			 String RetCode =
			 mXmlDoc_bank.getRootElement().getChild("Header").getChildText("RetCode");
			 cLogger.info("�˱��̳������ļ��ϴ�������(000000Ϊ�ɹ�)��"+RetCode);
			 filedetails =RetCode; //�̳���Ϣ���ݷ��ص������и��Ĵ������
			 }else if(cFuncFlag.equals("MYGX")){
			 byte[] mfileBodyBytes = new byte[filelength]; //���е�body�ֽڲ���xml����
			 if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			 return "1";
			 }
			 filedetails= new String(mfileBodyBytes);
			 cSocket.shutdownInput();
			 String local_host=SysInfo.cHome+"key/"; //֤���ļ�·��
			 makeLocalBalanceFile(local_host,downfilename,filedetails);
			 }
			//�ر��׽���
			cSocket.close();// �ӵ�����֮��socketͨѶ�ر�

		}
		catch (IOException e)//�ļ������쳣
		{
			e.printStackTrace();
			//����1[ʧ��]
			return "1";
		}
		catch (Exception e)//�����쳣
		{
			e.printStackTrace();
			//����1[ʧ��]
			return "1";
		}
		//����0[�ɹ�]
		return "0";
	}

	/**
	 * �����ļ��ϴ����طǱ�׼������ĺ��ļ�����
	 * @param pOutNoStd
	 *            ���͸����еı���
	 * @param file
	 *            �����ļ�������
	 * @throws Exception
	 *             �׳��쳣
	 */
	public void send(Document pOutNoStd, String file) throws Exception
	{
		//����File_download�����ļ��ϴ����طǱ�׼������ĺ��ļ����ݷ���...
		cLogger.info("Into File_download.send()...");

		// cLogger.info("���͸����еĶ��˱���file:"+file);
		//���ļ��ϴ����طǱ�׼������Ĵ�ӡ������̨�� GBK���룬����3�ո�
		JdomUtil.print(pOutNoStd);

		// String xmlStr = JdomUtil.toString(pOutNoStd);
		//�ļ��ϴ����طǱ�׼������ı���ԭ��ʽ�����������еı������ַ���
		String xmlStr = JdomUtil.toString(pOutNoStd);
		// System.out.println("xxxxxssss:"+xmlStr);
		// xmlStr=xmlStr.trim();
		// System.out.println("xxxxxssss:"+xmlStr);
		// xmlStr=xmlStr.replace('\r', ' ');
		//xxxxxssss:�ļ��ϴ����طǱ�׼��������ַ���
		System.out.println("xxxxxssss:" + xmlStr);
		//ͨ���ÿո� �滻�ļ��ϴ����طǱ�׼��������ַ����г��ֵ����� �س����� �õ��±����ַ���
		xmlStr = xmlStr.replace('\n', ' ');
		//xxxxxssss:���ļ��ϴ����طǱ�׼��������ַ���
		System.out.println("xxxxxssss:" + xmlStr);
		//�������ļ��ϴ����طǱ�׼��������ַ��������ַ���
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		// cLogger.info("�����е�xml�ַ�����"+xmlStr);
		//��ʼ���ܱ���
		cLogger.info("��ʼ���ܱ���");
		// xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		// String endxmlStr=AES.Encrypt(xmlStr);
		//�����ļ��ϴ����طǱ�׼��������ַ���
		String endxmlStr = xmlStr; // ������;
		//ʹ��ƽ̨��Ĭ���ַ������ļ��ϴ����طǱ�׼��������ַ��� ����Ϊ ������ ���У���������洢��һ���µ� �ֽ� ������
		byte[] outBytes = endxmlStr.getBytes();
		//���ܱ��Ľ���!
		cLogger.info("���ܱ��Ľ���!");
		
		//����73λ����ͷ:
		cLogger.info("����73λ����ͷ:");
		//��ͷ�ַ���[�ַ���8λ����]
		String sHeadBytes = AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		//��ͷ�ַ���[73�ֽ�:��������+�汾��+���ݰ�����+��˾����+(���ܱ�ʾ+�����㷨+����ѹ����־+����ѹ���㷨+ժҪ�㷨+ժҪ+Ԥ���ֶ�)]
		sHeadBytes = "X1.0" + sHeadBytes + insu + "    00000                                       000000000";
		//ʹ��ƽ̨��Ĭ���ַ�������ͷ�ַ��� ����Ϊ������ ���У���������洢��һ���µ� �ֽ�������
		byte array[] = sHeadBytes.getBytes();
		//����73λ����ͷ����
		cLogger.info("����73λ����ͷ����");
		//���͸����еı���ͷΪ����ͷ�ַ���
		cLogger.info("���͸����еı���ͷΪ��" + sHeadBytes);

		//ʹ��ƽ̨��Ĭ���ַ����������ļ����� ����Ϊ ������ ���У���������洢��һ���µ� �ֽ�������
		byte[] file_byte = file.getBytes();
		//���ͱ���ͷ���ȣ���ͷ�ֽ����鳤��
		System.out.println("���ͱ���ͷ���ȣ�" + array.length);
		//���ͱ����峤�ȣ��ļ��ϴ����طǱ�׼��������ֽ����鳤��
		System.out.println("���ͱ����峤�ȣ�" + outBytes.length);
		//�����ļ����ȣ������ļ������ֽ����鳤��
		System.out.println("�����ļ����ȣ�" + file_byte.length);
		//����ֽ���
		OutputStream ous = null;
		//�������ļ��ϴ����طǱ�׼��������ַ��������ַ���
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		try
		{
			//�½���ͷGBK�����ַ���
			String str1 = new String(array, "GBK");
			//����ͷstr1==:��ͷ�ַ���
			System.out.println("����ͷstr1==:" + str1);
			//�½��ļ��ϴ����طǱ�׼�������GBK�����ַ���
			String str2 = new String(outBytes, "GBK");
			//������str2==:�ļ��ϴ����طǱ�׼��������ַ���
			System.out.println("������str2==:" + str2);
			
			//��ȡ���׽��ֵ������
			ous = cSocket.getOutputStream();
			//�� b.length ���ֽڴӰ�ͷ�ֽ����� д��������
			ous.write(array);
			//�� b.length ���ֽڴ��ļ��ϴ����طǱ�׼��������ֽ�����д��������
			ous.write(xmlStr.getBytes());
			//�����е�xml�ַ������ļ��ϴ����طǱ�׼��������ַ���
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
			//�����ļ����ݵĳ��ȷ�0[�����ļ����ݷǿ�]
			if (file.length() > 0)
			{
				// ous.flush();
				//���ʹ��ж����ļ����ϴ����ױ��ģ�
				cLogger.info("���ʹ��ж����ļ����ϴ����ױ��ģ�");
				//�½������ֽ�����
				byte[] returnbyte = new byte[4];
				//��ȡȫ��[�����ֽ�����,�׽���������]ʧ��
				if (readFull(returnbyte, cSocket.getInputStream()) == 0)
				{
					//�½���ͷ��־[�����ֽ�����]
					String headflag = new String(returnbyte);
					//headflag:��ͷ��־
					cLogger.info("headflag:" + headflag);
					//��ͷ��־Ϊ0000
					if (headflag.equals("0000"))
					{// ���з�0000��ʾ��ʼ����
					// ous = cSocket.getOutputStream();
						//�½������ļ������ֽ�����GBK�����ַ���
						String str3 = new String(file_byte, "GBK");
						//�½��ַ������� 
						StringBuffer s = new StringBuffer();
						//�����ļ������ֽ����鳤���ַ����ĳ���
						int k = String.valueOf(file_byte.length).length();
						//socket �ر������׽��ֵĹر�״̬
						System.out.println("socket �ر�����" + cSocket.isClosed());
						//��0׷�ӵ��ַ������� 
						for (int i = 0; i < 12 - k; i++)
						{
							// ous.write("0".getBytes());
							s.append("0");
							// cLogger.info("��"+i+"�η���0");
						}
						//�������ļ������ֽ����鳤��׷�ӵ��ַ�������
						s.append(file_byte.length);
						//12λ���ȣ������ַ���=12λ�ֽڣ�ʹ��ƽ̨��Ĭ���ַ����������ַ�������Ϊ ����������
						cLogger.info("12λ���ȣ�" + String.valueOf(s) + "=12λ�ֽڣ�" + String.valueOf(s).getBytes());
						//�� b.length ���ֽڴӻ����ַ����ֽ�����д�뵽�����
						ous.write(String.valueOf(s).getBytes());
						// ous.flush();
						//�� b.length ���ֽڴӶ����ļ������ֽ�����д�뵽�����
						ous.write(file_byte);
						//�½������ֽ����� 
						byte[] send_byte = new byte[4];
						//��ȡȫ��[�����ֽ�����,�׽���������]
						readFull(send_byte, cSocket.getInputStream());
						//�½����ͱ�־�ַ���[�����ֽ�����]
						String sendflag = new String(send_byte);
						//sendflag:���ͱ�־�ַ���
						cLogger.info("sendflag:" + sendflag);
						//���ͱ�־�ַ���Ϊ0000
						if (sendflag.equals("0000"))
						{
							//������0000��ʾ�����ļ���������
							cLogger.info("������0000��ʾ�����ļ���������");
						}
						//�����ļ�����str3==:�����ļ������ַ���
						System.out.println("�����ļ�����str3==:" + str3);
					}

					// socketδ�رգ���ȡ�����ϴ����׷��ر�����λ
					//�½���־�ֽ�����
					byte[] flag_byte = new byte[1];
					//��ȡȫ��[��־�ֽ�����,�׽���������]
					if (readFull(flag_byte, cSocket.getInputStream()) == 0)
					{
						//�½��׸���־�ַ���
						String firstflag = new String(flag_byte);
						//firstflag:�׸���־�ַ���
						cLogger.info("firstflag:" + firstflag);
						//�׸���־�ַ���ΪX
						if (firstflag.equals("X"))
						{
							//�ϴ��ļ������󣬽������з��ر��ģ�
							cLogger.info("�ϴ��ļ������󣬽������з��ر��ģ�");
							//�������еķ��ر��ĳɹ�
							if (last_baowen().equals("0"))
							{ // �ɹ��������з��ر���ֱ���˳�
								//�����ļ�������
								cLogger.info("�����ļ�������");
							}
						}
						else
						{
							//����δ�����ϴ����ױ��ģ�
							cLogger.info("����δ�����ϴ����ױ��ģ�");
						}
					}
					else
					{
						//����δ����
						cLogger.info("����δ����");
					}
				}
				else
				{
					//����û�з���0000��ȷ�Ͻ��ն��˰����Ⱥ�����
					cLogger.info("����û�з���0000��ȷ�Ͻ��ն��˰����Ⱥ�����");
				}
			}
			else
			{
				//���Ͳ��������ļ����ϴ����ױ��ģ�
				cLogger.info("���Ͳ��������ļ����ϴ����ױ��ģ�");
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//File_download.send 
			cLogger.info("File_download.send " + ex);
			//�׳��м�ƽ̨�쳣[���ж�Socket�ر���!]
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
		//��File_download�����ļ��ϴ����طǱ�׼������ĺ��ļ����ݷ�������!
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
	 * @Description: �ϴ����������ļ�
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
		//���״���ΪRZDZ
		if (cFuncFlag.equals("RZDZ"))
		{ // ���ն����ļ�����
			// cs_way 0: �ϴ� 1: ����
			// filetype 01: ֤���ļ� 02: �����ļ�
			filename = "POLICY" + insu + "." + cDate; // ���ʱ��ô���,������Ҫ��
			//��ȡ�ļ��ϴ����طǱ�׼���뱨��[���ͷ�ʽ:����,�ļ�����:�����ļ�,�ļ�����,�ļ�����:�ļ�����00000000]
			dox = getxml("1", "02", filename, "00000000");
			//���״���=��֯�õı��ģ�
			cLogger.info(cFuncFlag + "=��֯�õı��ģ�");
			//����ı���·��[��ǰ���н��������ļ���localDir�ӽڵ��ı�+�ļ���]
			mFilePath = cThisConf.getChildText("localDir") + filename;
			//���ļ��ϴ����طǱ�׼���뱨�Ĵ�ӡ������̨�� GBK���룬����3�ո�
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
			//�½����汾��·���ļ�����
			File f = new File(mFilePath);
			//�ļ�����·����:����ı���·��
			System.out.println("�ļ�����·����:" + mFilePath);
			//�ļ��ϴ����طǱ�׼���뱨�ĸ��ڵ��±������ӽڵ����������ӽڵ��´��ͷ�ʽ�ڵ��ı�Ϊ����
			if (dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag").equals("1"))
			{
				//���汾��·���ļ�������
				if (!f.exists())
				{
					//�����ļ��ϴ����طǱ�׼���뱨�ĺ��ļ�����
					send(dox, file);
					//���͸��������
					System.out.println("���͸��������");
					//socket �ر������׽��ֵĹر�״̬
					System.out.println("socket �ر�����" + cSocket.isClosed());
					// �ϴ����ײ�receiveũ�еı���
					//��ȡ�����ļ��ϴ����طǱ�׼���뱨�������Ĵ��ͷ�ʽ�ӽڵ��ı�
					String flag = dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag");
					//���ͷ�ʽΪ����
					if (flag.equals("1"))
					{
						//�����ļ�������·��
						receive(mFilePath);
					}
					//�ļ�����:���з��͵��ļ�����
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

	/**
	 * @Title: readFull
	 * @Description: ��ȡȫ�� 
	 * @param pByte �����ֽ�����
	 * @param pIs �׽���������
	 * @return
	 * @throws IOException
	 * @return int
	 * @throws
	 */
	public int readFull(byte[] pByte, InputStream pIs) throws IOException
	{
		//Into readFull() =====�����ֽ����鳤��
		cLogger.info("Into readFull() =====" + pByte.length);
		//��ȡ��С<4
		for (int tReadSize = 0; tReadSize < pByte.length;)
		{
			//���뻺���������ֽ���[������������� 1 �������ֽڶ��� �����ֽ�����]
			int tRead = pIs.read(pByte, tReadSize, 1);
			// cLogger.info("tReadSize=="+tReadSize+"       tRead=="+tRead);
			//��ȡ�����ļ�ĩβû�п����ֽ�
			if (-1 == tRead)
			{
				//���ض�ȡ���
				return 1;
			}
			//��ȡ��С�ۼ��϶��뻺���������ֽ���
			tReadSize += tRead;
		}
		//��ȡ�ɹ�
		return 0;
	}

}