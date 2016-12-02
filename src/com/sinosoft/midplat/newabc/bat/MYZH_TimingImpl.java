package com.sinosoft.midplat.newabc.bat;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.Getkey_file;
import com.sinosoft.midplat.newabc.util.RSAUtil;
import com.sinosoft.utility.ExeSQL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import java.security.Provider;
public class MYZH_TimingImpl extends TimerTask implements XmlTag {
	private Logger mLogger = Logger.getLogger(MYZH_TimingImpl.class);
	private final String cFuncFlag;
	private final XmlConf cThisConf;
	protected String cTranDate;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	protected final Logger cLogger = Logger.getLogger(super.getClass());
	public static String cBasePath;
	private Socket cSocket;
	public static String cHome;
	private String cInsu;
	public MYZH_TimingImpl(XmlConf pThisConf, String pFuncFlag) {
		mLogger.info("into MYZH_TimingImpl 构造方法!");
		cThisConf = pThisConf;// newabc.xml
		cFuncFlag = pFuncFlag;// 1001
		System.out.println("MYZH_TimingImpl:" + cFuncFlag);
	}

	public void run() {
		Thread.currentThread().setName(
				new SimpleDateFormat("MMddhhmmssSSS").format(new Date()));
		this.mLogger.info("Into MYZH_TimingImpl.run ...");
		this.mLogger.info("====newmygx====");
		this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
		cInsu=cThisConfRoot.getChild("bank").getAttributeValue("insu");
		JdomUtil.print(this.cThisConfRoot);
		try {
			
			String tSql = "select codename from ldcode where codetype ='MYGX' and code='AGC'";
			String flag = new ExeSQL().getOneValue(tSql);
			System.out.println("是否进行密钥更新的flag:"+ flag);
			
			if("Y".equals(flag)){
			System.out.println("cFuncFlag--" + this.cFuncFlag);
			System.out.println("______business[funcFlag='" + this.cFuncFlag
					+ "']");
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(
					this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag
							+ "']"));
			
			JdomUtil.print(this.cThisBusiConf);
			Element xml_ftp = this.cThisBusiConf.getChild("socket");
			String abc_ip = xml_ftp.getAttributeValue("ip");
			System.out.println("配置文件IP:" + abc_ip);
			int abc_socket = Integer
					.parseInt(xml_ftp.getAttributeValue("port"));
			System.out.println("配置文件port:" + abc_socket);
			this.cSocket = new Socket(abc_ip, abc_socket);
			this.cSocket.setSoTimeout(60000);
			
			System.out.println("开始发送下载报文,获取证书.......");
			cInsu=cThisConfRoot.getChild("bank").getAttributeValue("insu");
			//发送下载报文,获取证书 
			File_download file_download=new File_download(cThisBusiConf,"MYGX",DateUtil.getCurDate("yyyyMMdd"),cInsu);
			System.out.println("cInsu是::"+cInsu);
			file_download.bank_dz_file();  
			//证书保存完成!
			//许亚飞要求
			String newKey = Getkey_file.getKey();
			
			String oldKey = Getkey_file.getKeyFile("newkey");
			System.out.println("_____" + Getkey_file.getKeyFile("oldkey"));
			System.out.println("_____" + Getkey_file.getKeyFile("newkey"));
			String jmoldKey = RSAUtil.getAESKey(oldKey);
			String jmnewKey = RSAUtil.getAESKey(newKey);
			
//			String newKey = "9ea1e99532e42f80";
//			String oldKey = "3yrQpIxKCsC49jGi";
//			
//			String jmoldKey = "5D6B304990BA8FBC97D39DC87AF6065C68471475AD409B6822E2D32BEA8E3A3A82E3F343AECED431FB95DE760BCE3F1D1364480866253BB32B248534F865645CC7CFB40A2C61051B92055DE3E401544BB8E99BF1621E2F11134597024E05DDA55C6FBC927F6C755E655732A36999A7E3930D64E931FA2D2CB17A4F0997AA8818";
//			String jmnewKey = "75F760C98C11BA7F31621B899B66D0A0CF23030F582613F75166EEBA7BB228A5B51E9F2D72897AA77D5635B94DECEBAF066D6C0F833EAD765172BEBC21D35FDED538804D6AF8E4B30CF621CE27CAF17B6E6F2B089B76469D9B6104E441AE869B55F57F6D13D11937D240AFBDE61A19FDB861CFF1AC3DA81605A0640A76A71B99";
			
			System.out.println("jmoldKey==" + jmoldKey);
			System.out.println("jmnewKey==" + jmnewKey);
			Document send_xml = getxml(jmnewKey, jmoldKey);
			
			this.mLogger.info("发送置换密钥报文：");
			JdomUtil.print(send_xml);
			send(send_xml);
			Document receive_xml = receive();
			this.mLogger.info("接收银行发过来密钥置换报文");
			JdomUtil.print(receive_xml);

			String errorcode = receive_xml.getRootElement().getChild("Header")
					.getChildText("RetCode");
			if (errorcode.equals("000000")) {
				this.mLogger.info("密钥置换交易成功！");
				String mSQL = "update ldcode set codename ='N' where codetype ='MYGX' and code='AGC'";
				boolean b = new ExeSQL().execUpdateSQL(mSQL);
				if(b){
					this.mLogger.info("密钥重置交易更新flag成功");
				}else{
					this.mLogger.info("密钥重置交易更新flag失败");
				}
				String useKey = Getkey_file.getKeyFile("newkey");
				this.mLogger.info("置换成的密钥是：" + useKey);
				Getkey_file.saveKey(newKey);
				AES.setSKey(useKey);
				
				
			}
			this.mLogger.info("密钥置换交易成功！");
			}else{
				this.mLogger.info("无需进行密钥更新");
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		 this.mLogger.info("out  PgwtTimingImpl.run ...");
	}

	public Document getxml(String newMY, String oldMY) {
		Element root = new Element("ABCB2I");
		Element head = new Element("Header");
		root.addContent(head);

		Element mSerialNo = new Element("SerialNo");
		Element mInsuSerial = new Element("InsuSerial");
		Element mTransDate = new Element("TransDate");
		Element mTransTime = new Element("TransTime");
		Element mBankCode = new Element("BankCode");
		Element mCorpNo = new Element("CorpNo");
		Element mTransCode = new Element("TransCode");
		Element mTransSide = new Element("TransSide");
		Element mEntrustWay = new Element("EntrustWay");
		Element mProvCode = new Element("ProvCode");
		Element mBranchNo = new Element("BranchNo");

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
//		mCorpNo.setText("3103");
		mCorpNo.setText(cInsu);
		mTransCode.setText("1001");
		mTransSide.setText("0");

		Element mApp = new Element("App");
		root.addContent(mApp);
		Element body = new Element("Req");
		mApp.addContent(body);

		Element mEncType = new Element("EncType");
		Element mPriKey = new Element("PriKey");
		Element mOrgKey = new Element("OrgKey");

		body.addContent(mEncType);
		body.addContent(mPriKey);
		body.addContent(mOrgKey);

		mEncType.setText("01");
		mPriKey.setText(newMY);
		mOrgKey.setText(oldMY);
		Document doc = new Document(root);
		return doc;
	}

	public Document receive() throws Exception {
		this.cLogger.info("Into File_download.receive()...");

		byte[] mHeadBytes = new byte[73];
		System.out.println("还连接着呢吗？" + this.cSocket.getRemoteSocketAddress());
		IOTrans.readFull(mHeadBytes, this.cSocket.getInputStream());
		String package_head = new String(mHeadBytes);

		this.cLogger.info("package_head:" + package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim());
		System.out.println("mBodyLen:" + mBodyLen);
		this.cLogger.info("mBodyLen:" + mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen];
		System.out.println("还连接着呢吗2？" + this.cSocket.isClosed());
		IOTrans.readFull(mBodyBytes, this.cSocket.getInputStream());
		this.cSocket.shutdownInput();

		String axx = new String(mBodyBytes, "UTF-8");
		StringBuffer abc_xml = new StringBuffer();
		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		abc_xml.append("\n");
		abc_xml.append(axx);
		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");
		Document mXmlDoc_bank = JdomUtil.build(all_xml, "UTF-8");

		String coding = "GBK";
		Format mFormat = Format.getRawFormat().setIndent("   ")
				.setEncoding(coding);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(mXmlDoc_bank, mBaos);
		Document mXmlDoc = JdomUtil.build(mBaos.toByteArray(), "GBK");
		this.cLogger.info("密钥置换返回报文:");
		JdomUtil.print(mXmlDoc);

		this.cSocket.close();
		this.cLogger.info("Out File_download.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		this.cLogger.info("Into File_download.send()...");
		this.cLogger.info("发送给银行的报文:");
		JdomUtil.print(pOutNoStd);

		String xmlStr = JdomUtil.toString(pOutNoStd);
		System.out.println("xxxxxssss:" + xmlStr);
		xmlStr = xmlStr.substring(xmlStr.indexOf("<ABCB2I>"));
		this.cLogger.info("给银行的xml字符串：" + xmlStr);

		byte[] outBytes = xmlStr.getBytes("UTF-8");
		System.out.println("xml字符串的报文长度：" + outBytes.length);
		String sHeadBytes = RSAUtil.lpad(String.valueOf(outBytes.length), 8,
				'0');
		sHeadBytes = "X1.0"
				+ sHeadBytes
				+ "3103    00000                                       000000000";
		byte[] array = sHeadBytes.getBytes();
		System.out.println("发送报文头为：" + sHeadBytes);

		OutputStream ous = null;
		try {
			ous = this.cSocket.getOutputStream();
			ous.write(array);
			ous.flush();
			ous.write(outBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.cLogger.info("chuxiancuowu " + ex);
			this.cLogger.info("客户端已经断开连接" + ex);
			return;
		} finally {
			try {
				ous.flush();
			} catch (IOException ioe) {
				this.cLogger.error("关闭输出流异常");
			}
		}

		this.cLogger.info("Out File_download.send()!");
	}
}