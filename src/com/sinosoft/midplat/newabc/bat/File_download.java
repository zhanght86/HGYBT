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
 * @Description: 文件下载批量
 * @author sinosoft
 * @date 2017-2-27 上午10:47:45
 */
public class File_download
{
	//生成一个本类的日志对象
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String cDate;//
	private final Element cThisConf;//当前交易配置节点
	private final String cFuncFlag; // 交易代码
	private Socket cSocket;//套接字
	private String insu;// 保险公司编码
	private String filename;//文件名
	protected Element cThisBusiConf = null;
	protected Element cThisConfRoot = null;

	/**
	 * 
	 * File_download构造方法
	 * 
	 * @param pThisConf 配置文件
	 * @param pFuncFlag 交易码
	 * @param pDate 交易日期
	 * @param pInsu 保险公司代码
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
	 * getsocket 打开socket连接
	 *
	 * @throws MidplatException
	 */
	public void getsocket() throws MidplatException
	{
		//农行socket
		int abc_socket = 0;
		//农行IP
		String abc_ip = "";
		try
		{
			System.out.println("=====哈哈哈哈=====");
			//
			JdomUtil.print(cThisConf);
			Element xml_ftp = cThisConf.getChild("socket");
			abc_ip = xml_ftp.getAttributeValue("ip");
			System.out.println("配置文件IP:" + abc_ip);
			abc_socket = Integer.parseInt(xml_ftp.getAttributeValue("port"));
			System.out.println("配置文件Socket:" + abc_socket);
			cSocket = new Socket(abc_ip, abc_socket);// 10.136.80.52
			cSocket.setSoTimeout(60000);// 设置超时时间
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			cLogger.info("连接银行端异常!ip地址:" + abc_ip + "端口号:" + abc_socket);
			throw new MidplatException("连接银行端异常!");
		}
		catch (IOException e)
		{
			cLogger.info("连接银行端超时!" + e);
			throw new MidplatException("连接银行端超时!");
		}
	}

	/**
	 * 
	 * @param cs_way
	 *            传输方式 0: 上传 1: 下载
	 * @param filetype
	 *            文件类型 01: 证书文件 02: 对账文件
	 * @param filename
	 *            文件名字
	 * @param filelength
	 *            文件长度
	 * @return 返回文件下载的报文
	 */
	public Document getxml(String cs_way, String filetype, String filename, String filelength)
	{
		Element root = new Element("ABCB2I");
		Element head = new Element("Header");
		root.addContent(head);
		Element mSerialNo = new Element("SerialNo"); // 银行交易流水号
		Element mInsuSerial = new Element("InsuSerial"); // 保险公司流水号
		Element mTransDate = new Element("TransDate"); // 交易日期
		Element mTransTime = new Element("TransTime"); // 交易时间
		Element mBankCode = new Element("BankCode"); // 银行代码
		Element mCorpNo = new Element("CorpNo"); // 保险公司代码
		Element mTransCode = new Element("TransCode"); // 交易编码
		Element mTransSide = new Element("TransSide"); // 交易发起方
		Element mEntrustWay = new Element("EntrustWay"); // 委托方式
		Element mProvCode = new Element("ProvCode"); // 省市代码
		Element mBranchNo = new Element("BranchNo"); // 网点号
		Element mTlid = new Element("Tlid"); // 网点号
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
		System.out.println("随机数是：" + t);
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
		Element mTransFlag = new Element("TransFlag"); // 传送方式
		Element mFileType = new Element("FileType"); // 文件类型
		Element mFIleName = new Element("FileName");// 文件名称
		Element mFileLen = new Element("FileLen"); // 文件长度
		Element FileTimeStamp = new Element("FileTimeStamp");// 文件修改时间戳
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
		String js_flag = "0";// 接收是否成功标志 0 -成功 1 -标记
		File f = new File(mFilePath);
		if (f.exists())
		{
			cLogger.info("文件已经存在,先删除掉!");
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
					cLogger.info("接收银行返回报文！last_baowen");
					if (last_baowen().equals("0"))
					{ // 成功直接退出
						cLogger.info("接收文件结束1");
						break;
					}
					else
					{ // 失败了，也要退出重来
						js_flag = "1";
						cSocket.close();// 失败了关闭socket
						break;
					}
				}
				else
				{
					// 首次读取12位文件总长度
					byte[] flag_byte11 = new byte[11];

					if (readFull(flag_byte11, cSocket.getInputStream()) != 0)
					{
						return "1";
					}

					String file_length = new String(flag_byte11);
					cLogger.info("得出文件总长度：" + file_length);

					ous = cSocket.getOutputStream();
					ous.write("0000".getBytes());
					ous.flush();
					int receive_length = Integer.parseInt(file_length);// 接收是否成功标志
																		// 0 -成功
																		// 1 -标记
					while (js_flag.equals("0"))
					{
						if (receive_file(js_flag, mFilePath, receive_length).equals("0"))
						{
							cLogger.info("对账文件接收完了....continue！");
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
				cLogger.info("接收长度有问题！");
				js_flag = "1";
				cSocket.close();// 失败了关闭socket
				break;
			}
		}

		cLogger.info("Out File_download.receive()!");
		return null;
	}

	public String receive_file(String flag, String mFilePath, int receivelength) throws MidplatException, IOException
	{
		cLogger.info("正在接收文件中……");
		cLogger.info("flag……" + flag + "   mFilePath...." + mFilePath);
		OutputStream ous = null;
		FileOutputStream tFos = null;
		int length = 0;
		int i = 1;// 循环接收包次数
		String js_flag = "0";// 接收是否成功标志 0 -成功 1 -标记

		try
		{
			cLogger.info("开始接受长度：" + receivelength);
			while (receivelength > 0)
			{
				byte[] flag_byte = new byte[1];

				if (readFull(flag_byte, cSocket.getInputStream()) == 0)
				{
					// IOTrans.readFull(flag_byte, cSocket.getInputStream());
					String firstflag = new String(flag_byte);
					if (firstflag.equals("X"))
					{
						cLogger.info("接收银行返回报文！last_baowen");
						if (last_baowen().equals("0"))
						{ // 成功直接退出
							cLogger.info("接收文件结束2");
							break;
						}
						else
						{ // 失败了，也要退出重来
							js_flag = "1";
							cSocket.close();// 失败了关闭socket
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
						cLogger.info("length_str是这个：" + length_str);
						length = Integer.parseInt(length_str);
						cLogger.info("第" + i + "包接收文件长度:" + length);
						// 接收成功之后返回给银行接收成功标示0000
						ous = cSocket.getOutputStream();
						ous.write("0000".getBytes());
						ous.flush();
						i++;
						// 真正开始接收文件
						cLogger.info("文件路径:" + mFilePath);

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
								tFos = new FileOutputStream(mFilePath, true); // 追加写入
								tFos.write(file_byte);
								receivelength = receivelength - length;
								ous = cSocket.getOutputStream();
								ous.write("0000".getBytes());
								ous.flush();
								cLogger.info("文件保存完成，路径:" + mFilePath);
							}
							else if (length - 4096 > 0)
							{
								tFos = new FileOutputStream(mFilePath, true); // 追加写入
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
							cLogger.info("没有找到文件：" + mFilePath);
							e.printStackTrace();
							return "1";
						}
						catch (IOException e)
						{
							cLogger.info("读取文件发生异常：" + mFilePath);
							e.printStackTrace();
							return "1";
						}

					}
				}
				else
				{
					cLogger.info("接收长度还是有问题！");
					js_flag = "1";
					cSocket.close();// 失败了关闭socket
					break;
				}
				cLogger.info("第" + (i - 1) + "次接收完还剩" + receivelength + "长度");
			}
			cLogger.info("退出while循环");
		}
		catch (Exception ex)
		{
			cLogger.info("chuxiancuowu " + ex);
			cLogger.info("客户端已经断开连接" + ex);
			return "1";
		}
		finally
		{
			try
			{
				cLogger.info("finally块总是要跑的");
				ous.flush();
				tFos.close();
			}
			catch (IOException ioe)
			{
				cLogger.error("关闭输出流异常");
				return "1";
			}

		}

		cLogger.info("就这样结束了本次服务");
		// 接收成功之后给银行返回成功标志
		try
		{
			cLogger.info("关注socket有没有关闭：" + cSocket.isConnected());
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

		cLogger.info("本次文件接收完成……");
		return "0";
	}

	/**
	 * 接收银行的返回报文
	 * 
	 * @return
	 */
	public String last_baowen() throws MidplatException
	{
		// 包头72位
		byte[] mHeadBytes = new byte[72];
		System.out.println("连接地址：" + cSocket.getRemoteSocketAddress());
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
			throw new MidplatException("读取银行72位报文头错误!");
		}
		String package_head = new String(mHeadBytes);
		// 2-12 位是报文体长度
		cLogger.info("package_head:" + package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(2, 11).trim()); // 包体长度
		System.out.println("mBodyLen:" + mBodyLen);
		cLogger.info("mBodyLen:" + mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; // 所有的body字节不带xml声明
		// byte[] mBodyBytes = new byte[100]; //所有的body字节不带xml声明
		cLogger.info("查看是否" + cSocket.isClosed());
		try
		{
			// IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
			if (readFull(mBodyBytes, cSocket.getInputStream()) != 0)
			{
				return "1";
			}
			// cSocket.shutdownInput();
			// cLogger.info("解密开始:============================");
			// String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
			String axx = new String(mBodyBytes, "UTF-8");
			// cLogger.info("解密结束:============================");
			// String axx = new String (mBodyBytes,"UTF-8");
			StringBuffer abc_xml = new StringBuffer();
			abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			abc_xml.append("\n");
			abc_xml.append(axx);
			byte[] all_xml = abc_xml.toString().getBytes("UTF-8");// #
			Document mXmlDoc_bank = JdomUtil.build(all_xml, "UTF-8"); // #

			cLogger.info("文件上传下载银行发送过来的报文:");
			JdomUtil.print(mXmlDoc_bank);

			Element body = mXmlDoc_bank.getRootElement().getChild("App").getChild("Ret");
			int filelength = Integer.parseInt(body.getChildText("FileLen"));
			String downfilename = body.getChildText("FileName");// 文件名字
			cLogger.info("文件上传下载的文件名字：" + downfilename);
			// String downfilename1 =downfilename.substring(0, 3);
			// 文件名称 文件内容
			// POLICY$.YYYYMMDD 新保承保保单对账文件
			// VCH*$&.YYYYMMDD 凭证对账文件
			// FAPPLY$.YYYYMMDD 非实时出单流水明细
			// BQAPPLY$.YYYYMMDD 保全交易申请文件
			// INVALID$.YYYYMMDD 退保犹撤数据文件
			// FRESULT$.YYYYMMDD 非实时出单结果文件
			// String filedetails="";
			// 这段测试的时候再看 ---- 说文件传输的流程变了. 这个流程是返回报文,文件跟在后面。
			// if(cFuncFlag.equals("RZDZ")){//新保承保保单对账文件（日终对账）
			// byte[] mfileBodyBytes = new byte[filelength]; //所有的body字节不带xml声明
			// if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			// return "1";
			// }
			// filedetails= new String(mfileBodyBytes);
			// cSocket.shutdownInput();
			// String local_host=cThisConf.getChildText("localPath").trim();
			// makeLocalBalanceFile(local_host,downfilename,filedetails);
			// }else if(cFuncFlag.equals("YCXXCD")){//退保犹撤数据文件
			// String RetCode =
			// mXmlDoc_bank.getRootElement().getChild("Header").getChildText("RetCode");
			// cLogger.info("退保犹撤数据文件上传返回码(000000为成功)："+RetCode);
			// filedetails =RetCode; //犹撤信息传递返回的是银行给的错误代码
			// }else if(cFuncFlag.equals("MYGX")){
			// byte[] mfileBodyBytes = new byte[filelength]; //所有的body字节不带xml声明
			// if(readFull(mfileBodyBytes, cSocket.getInputStream())!=0){
			// return "1";
			// }
			// filedetails= new String(mfileBodyBytes);
			// cSocket.shutdownInput();
			// String local_host=SysInfo.cHome+"key/"; //证书文件路径
			// makeLocalBalanceFile(local_host,downfilename,filedetails);
			// }
			cSocket.close();// 接到数据之后将socket通讯关闭

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
	 *            发送给银行的报文
	 * @param file
	 *            对账文件的内容
	 * @throws Exception
	 *             抛出异常
	 */
	public void send(Document pOutNoStd, String file) throws Exception
	{
		cLogger.info("Into File_download.send()...");

		// cLogger.info("发送给银行的对账报文file:"+file);
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
		// cLogger.info("给银行的xml字符串："+xmlStr);

		cLogger.info("开始加密报文");
		// xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		// String endxmlStr=AES.Encrypt(xmlStr);
		String endxmlStr = xmlStr; // 不加密;
		byte[] outBytes = endxmlStr.getBytes();
		cLogger.info("加密报文结束!");

		cLogger.info("生成73位报文头:");
		String sHeadBytes = AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0" + sHeadBytes + insu + "    00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();
		cLogger.info("生成73位报文头结束");
		cLogger.info("发送给银行的报文头为：" + sHeadBytes);

		byte[] file_byte = file.getBytes();
		System.out.println("发送报文头长度：" + array.length);
		System.out.println("发送报文体长度：" + outBytes.length);
		System.out.println("发送文件长度：" + file_byte.length);
		OutputStream ous = null;
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		try
		{
			String str1 = new String(array, "GBK");
			System.out.println("报文头str1==:" + str1);
			String str2 = new String(outBytes, "GBK");
			System.out.println("报文体str2==:" + str2);

			ous = cSocket.getOutputStream();
			ous.write(array);
			ous.write(xmlStr.getBytes());
			cLogger.info("给银行的xml字符串：" + xmlStr);

			// if(file.length()>0 ){
			// // ous.flush();
			// try {
			// ous.write(file_byte);
			// System.out.println("发送完了");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			if (file.length() > 0)
			{
				// ous.flush();
				cLogger.info("发送带有对账文件的上传交易报文！");
				byte[] returnbyte = new byte[4];
				if (readFull(returnbyte, cSocket.getInputStream()) == 0)
				{
					String headflag = new String(returnbyte);
					cLogger.info("headflag:" + headflag);
					if (headflag.equals("0000"))
					{// 银行发0000表示开始接收
					// ous = cSocket.getOutputStream();
						String str3 = new String(file_byte, "GBK");
						StringBuffer s = new StringBuffer();
						int k = String.valueOf(file_byte.length).length();
						System.out.println("socket 关闭了吗？" + cSocket.isClosed());
						for (int i = 0; i < 12 - k; i++)
						{
							// ous.write("0".getBytes());
							s.append("0");
							// cLogger.info("第"+i+"次发送0");
						}
						s.append(file_byte.length);
						cLogger.info("12为长度：" + String.valueOf(s) + "=12为字节：" + String.valueOf(s).getBytes());
						ous.write(String.valueOf(s).getBytes());
						// ous.flush();
						ous.write(file_byte);
						byte[] send_byte = new byte[4];
						readFull(send_byte, cSocket.getInputStream());
						String sendflag = new String(send_byte);
						cLogger.info("sendflag:" + sendflag);
						if (sendflag.equals("0000"))
						{
							cLogger.info("返回了0000表示对账文件接收完了");
						}
						System.out.println("对账文件内容str3==:" + str3);
					}

					// socket未关闭，读取银行上传交易返回报文首位
					byte[] flag_byte = new byte[1];
					if (readFull(flag_byte, cSocket.getInputStream()) == 0)
					{
						String firstflag = new String(flag_byte);
						cLogger.info("firstflag:" + firstflag);
						if (firstflag.equals("X"))
						{
							cLogger.info("上传文件结束后，接收银行返回报文！");
							if (last_baowen().equals("0"))
							{ // 成功接收银行返回报文直接退出
								cLogger.info("发送文件结束！");
							}
						}
						else
						{
							cLogger.info("银行未返回上传交易报文！");
						}
					}
					else
					{
						cLogger.info("银行未处理");
					}
				}
				else
				{
					cLogger.info("银行没有发送0000来确认接收对账包长度和数据");
				}
			}
			else
			{
				cLogger.info("发送不带对账文件的上传交易报文！");
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			cLogger.info("File_download.send " + ex);
			throw new MidplatException("银行端Socket关闭了!");
		}
		finally
		{
			// try{
			// ous.flush();
			// ous.close();
			// }catch(IOException ioe){
			// ioe.printStackTrace();
			// throw new MidplatException("关闭输出流异常!");
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
			cLogger.debug("Socket可能已关闭！", ex);
		}
	}

	/**
	 * 
	 * @param filename
	 *            文件名字
	 * @param peizhi
	 *            配置的xml节点
	 * @return 返回 要上传给银行的文件内容且编码方式是utf-8格式的
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
		System.out.println("传给银行文件内容1：" + lastfile);
		System.out.println("传给银行文件内容2：" + new String(lastfile.getBytes("UTF-8")));
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

		getsocket(); // 获取socket 对象
		Document dox = null; // 要发送银行的报文
		String file = ""; // 文件内容
		String filedetails = ""; // 银行发送的文件内容
		String mFilePath = ""; // 保存的本地路径
		if (cFuncFlag.equals("RZDZ"))
		{ // 日终对账文件下载
			// cs_way 0: 上传 1: 下载
			// filetype 01: 证书文件 02: 对账文件
			filename = "POLICY" + insu + "." + cDate; // 这个时间得传入,补对账要用
			dox = getxml("1", "02", filename, "00000000");
			cLogger.info(cFuncFlag + "=组织好的报文：");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDJGWJ"))
		{ // 非实时出单结果文件 FRESULT$.YYYYMMDD
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
			System.out.println("组织好的文件：" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("组织好的文件长度：" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("TBYCSJWJ"))
		{ // 退保犹撤数据文件 INVALID$.YYYYMMDD
			filename = "INVALID" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("组织好的文件：" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("组织好的文件长度：" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDXZMX"))
		{ // 非实时出单险种明细 FRESULTKZ$.YYYYMMDD
			filename = "FRESULTKZ" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("组织好的文件：" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("组织好的文件长度：" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));

			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDJGWJ"))
		{ // 手工单结果文件 SRESULT$.YYYYMMDD
			filename = "SRESULT" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("组织好的文件：" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("组织好的文件长度：" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDCDXZMX"))
		{ // 手工单出单险种明细 SRESULTKZ$.YYYYMMDD
			filename = "SRESULTKZ" + insu + "." + cDate;
			String ttLocalDir = cThisConf.getChildTextTrim("localDir");
			mFilePath = ttLocalDir + filename;
			// file=getfiledetails(filename,cThisConf);
			InputStream fis = new FileInputStream(mFilePath);
			byte[] bytes = IOTrans.toBytes(fis);
			int FileLen = bytes.length;
			System.out.println("组织好的文件：" + mFilePath);
			file = new String(bytes, "GBK");
			int length = file.getBytes("GBK").length;
			System.out.println("组织好的文件长度：" + FileLen + "     " + file);
			dox = getxml("0", "02", filename, String.valueOf(length));
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("SGDJGHP"))
		{ // 手工单结果文件-银行处理结果回盘 SRESULT.BANK$.YYYYMMDD
			filename = "SRESULT" + "." + "BANK" + insu + "." + cDate;
			System.out.println("组织好的文件：" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("组织好的文件长度：" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDLSMX"))
		{ // 非实时出单流水明细 FAPPLY$.YYYYMMDD
			filename = "FAPPLY" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("组织好的文件：" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("组织好的文件长度：" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("BQSQ"))
		{ // 保全交易申请文件 BQAPPLY$.YYYYMMDD
			filename = "BQAPPLY" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("组织好的文件：" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("组织好的文件长度：" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("TBYCHP"))
		{ // 退保犹撤数据文件-回盘 INVALID.BANK$.YYYYMMDD
			filename = "INVALID" + "." + "BANK" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("组织好的文件：" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("组织好的文件长度：" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("FSSCDHP"))
		{ // 非实时出单结果文件-回盘 FRESULT.BANK$.YYYYMMDD
			filename = "FRESULT" + "." + "BANK" + insu + "." + cDate;
			// file=getfiledetails(filename,cThisConf);
			System.out.println("组织好的文件：" + file);
			int length = file.getBytes("UTF-8").length;
			System.out.println("组织好的文件长度：" + length);
			dox = getxml("1", "02", filename, "00000000");
			mFilePath = cThisConf.getChildText("localDir") + filename;
			cLogger.info(cFuncFlag + "=组织好的报文：");
			JdomUtil.print(dox);
		}
		else if (cFuncFlag.equals("MYGX"))
		{ // 密钥更新交易
			filename = "cacert.crt"; // 证书文件名称
			dox = getxml("1", "01", filename, "00000000"); // 组织上传下载交易发给银行,下载证书文件
			String keyPath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("KeyPath");     
			mFilePath = keyPath + "key/" + filename;
//			mFilePath = SysInfo.cHome + "key/" + filename;
			cLogger.info(cFuncFlag + "==========组织好的报文:");
			JdomUtil.print(dox);
		}
		try
		{
			File f = new File(mFilePath);
			System.out.println("文件绝对路径是:" + mFilePath);
			if (dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag").equals("1"))
			{
				if (!f.exists())
				{
					send(dox, file);
					System.out.println("发送给银行完成");
					System.out.println("socket 关闭了吗？" + cSocket.isClosed());
					// 上传交易不receive农行的报文
					String flag = dox.getRootElement().getChild("App").getChild("Req").getChildText("TransFlag");
					if (flag.equals("1"))
					{
						receive(mFilePath);
					}

					System.out.println("文件内容:" + filedetails);
				}
				else
				{
					cLogger.info("文件已存在 无需再次下载");
				}
			}
			else
			{
				cLogger.info("发送文件内容：" + file);
				send(dox, file);
				System.out.println("发送给银行完成");
				System.out.println("socket 关闭了吗？" + cSocket.isClosed());

				System.out.println("文件内容:" + file);
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

		cLogger.info("本地对账文件路径：" + localPath);
		cLogger.info("本地对账文件名称：" + fileName);
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