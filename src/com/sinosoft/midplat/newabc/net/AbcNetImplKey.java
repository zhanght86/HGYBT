package com.sinosoft.midplat.newabc.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sinosoft.midplat.newabc.key.AbcAES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;
import com.sinosoft.midplat.newabc.util.AbcXmlTag;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;

public class AbcNetImplKey implements AbcXmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected final Socket cSocket;
	protected final String cClientIp;
	
	protected final Element cThisConfRoot;
	
	protected final Element cTranComEle;
	protected String cFuncFlag;
	protected String cTransNo;
	private String cOutFuncFlag; //外部交易代码
//	protected byte[] cFuncBytes;
//	protected byte[] cInsuBytes;
	
	public AbcNetImplKey(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		cSocket = pSocket;
		cClientIp = cSocket.getInetAddress().getHostAddress();
		cLogger.info("客户端IP：" + cClientIp + "；服务端口：" + cSocket.getLocalPort());
		cThisConfRoot = pThisConfRoot;
		cTranComEle = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		String mOkIp = cThisConfRoot.getChildText(ip);
		if (null!=mOkIp && !mOkIp.contains(cClientIp)) {
			throw new NetException("非法ip："+cClientIp);
		}

	}
	
	public Document receive() throws Exception {
		cLogger.info("Into SocketNetImp.receive()...");
		
		
		
		/**********解密请求****************/
		byte[] mSecretHeadBytes = new byte[58];
		IOTrans.readFull(mSecretHeadBytes, cSocket.getInputStream());
		String secret_package_head = new String(mSecretHeadBytes);
		cLogger.info("secret_package_head:" + secret_package_head);
		//0-8位是加密报文长度
		int sBodyLen = Integer.parseInt(secret_package_head.substring(0, 8).trim()); 
		cLogger.info("sBodyLen:" + sBodyLen);
		//报文加密识别码，报文加密识别码的长度为32字节(长度为16的字符串转为16进制字符串长度为32)
		String secretCode = secret_package_head.substring(26, 58);
		cLogger.info("16进制32位长度的报文加密识别码是:" + secretCode);
		String decryptCode = AbcAES.Decrypt(secretCode);
		cLogger.info("解密后的报文加密识别码是:" + decryptCode);
		if (decryptCode.equals("ABCHINA..ANIHCBA")) {
			cLogger.info("解密成功");
		} else {
			cLogger.info("解密失败");
		}
		
		//开始获取业务报文体
		byte[] eBodyBytes = new byte[sBodyLen];
		cLogger.info("解密后的报文1:");
		IOTrans.readFull(eBodyBytes, cSocket.getInputStream());
		cLogger.info("解密后的报文2:");
		String dBodyStr = AbcAES.Decrypt(new String(eBodyBytes,"UTF-8"));
		cLogger.info("解密后的报文:" + dBodyStr);
		
		/**********解密请求****************/
		
		
		//包头73位
//		byte[] mHeadBytes = new byte[73];
//		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		String package_head = dBodyStr.substring(0, 73);
		//4-12 位是报文体长度
		cLogger.info("package_head:"+package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //包体长度
		cLogger.info("mBodyLen:"+mBodyLen);
//		byte[] mBodyBytes = new byte[mBodyLen]; //所有的body字节不带xml声明
//		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
//		cSocket.shutdownInput();
		
//		String axx = new String (mBodyBytes,"UTF-8");
		String axx = dBodyStr.substring(73);
		//System.out.println("银行传过来的字符串："+axx);
		StringBuffer abc_xml = new StringBuffer();
//		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //#
		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		abc_xml.append("\n");
		abc_xml.append(axx);
		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
		//System.out.println("xml字符："+new String(all_xml,"UTF-8"));
		//20131120 change tttttt  start
		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
		//System.out.println("解析mXmlDoc_bank：");
		//JdomUtil.print(mXmlDoc_bank);
		
		String coding = "GBK";//"UTF-8"  "GBK"
		Format mFormat = Format.getRawFormat().setIndent("   ").setEncoding(coding);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(mXmlDoc_bank,mBaos);
		Document mXmlDoc = JdomUtil.build(mBaos.toByteArray(),"GBK");
		//20131120 change tttttt  end
		cLogger.info("银行发送过来的报文:");
		JdomUtil.print(mXmlDoc);
		Element mHeadEle = mXmlDoc.getRootElement().getChild(Header);
		cFuncFlag = mHeadEle.getChildText(TransCode); //只有内部代码
		
		cTransNo = mHeadEle.getChildText(SerialNo); //流水号
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append("_").append(cTransNo)
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		cLogger.info("Out SocketNetImp.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into AbcNetImpl.send()...");
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(cTransNo)
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		cLogger.info("发送给银行的报文:");
		JdomUtil.print(pOutNoStd);
		
		//start
		//20131122 start
		String xmlStr = JdomUtil.toString(pOutNoStd);
//		System.out.println("IDHSFH++++==="+xmlStr.substring(xmlStr.indexOf("<ABCB2I>")).trim().getBytes().length );
        xmlStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlStr.substring(xmlStr.indexOf("<ABCB2I>")).trim();
		//xmlStr=xmlStr.substring(xmlStr.indexOf("<ABCB2I>")).trim();
        System.out.println("xxxxxx=="+xmlStr.getBytes().length);
        System.out.println("dddddd=="+xmlStr.getBytes("UTF-8").length);
//		Pattern p = Pattern.compile("|/n");
//		Matcher m = p.matcher(xmlStr);
//		xmlStr = m.replaceAll("");
		cLogger.info("给银行的xml字符串："+xmlStr);
		//20131122 end
		
		byte[] outBytes=xmlStr.getBytes("UTF-8");
		System.out.println("xml字符串的报文长度："+outBytes.length);
		//cLogger.info("发送至银行的业务报文体："+ new String(outBytes, "UTF-8"));

		byte array[] = new byte[8];
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
//		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(mBaos.size()), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+"                                                             ";
		array = sHeadBytes.getBytes("UTF-8");
		System.out.println("原始的报文长度："+sHeadBytes);
		//end

		
		/***************加密报文*****************/
		//原始报文，未加密之前的
		String origStr = sHeadBytes+xmlStr;
		//String origStr = new String(array, "UTF-8") + new String(outBytes, "UTF-8");//zsh
		cLogger.info("未加密ddddd的业务报文：" + origStr.getBytes("utf-8").length);
		
		//加密前要求报文长度为16的倍数
		//origStr = AbcAES.fillWithB16(origStr, ' ');
		cLogger.info("转化为长度为16的倍数未加密的业务报文：" + origStr);
		StringBuffer outSb = new StringBuffer();
		String encryptStr = AbcAES.Encrypt(origStr);
		//开始拼加密报文头,0-8报文长度
		outSb.append(AbcMidplatUtil.lpad(String.valueOf(encryptStr.getBytes().length), 8, '0'));
		//8-16加密算法
		outSb.append(AbcMidplatUtil.rpad("AES128", 8, ' '));
		//16-22公司代码
		outSb.append(AbcMidplatUtil.rpad("1108", 6, ' '));
		//22-26交易代码
		outSb.append(cFuncFlag);
		//26-58,32位报文加密识别码
		String enCode = AbcAES.Encrypt("ABCHINA..ANIHCBA");
		outSb.append(enCode);
		//加密后的业务报文
		outSb.append(encryptStr);
		cLogger.info("加密后的报文" + outSb.toString());
		outBytes = outSb.toString().getBytes("UTF-8");
		
		
		
		
		
		
		/***************加密报文*****************/
		
		OutputStream ous = null;
		try{
			ous = cSocket.getOutputStream();
			try {
				cSocket.sendUrgentData(0xFF);
			} catch(Exception ex) {				
				cLogger.info("chuxiancuowu " + ex);
				cLogger.info("客户端已经断开连接" + ex);
				return;
			}
//			ous.write(array);
			ous.flush();
			ous.write(outBytes);
			
		}catch(Exception e){
			e.printStackTrace();
			cLogger.error("发送返回报文异常");
		}finally{	
			try{
				ous.flush();
				if(ous != null){					
					ous.close();
				}
			}catch(IOException ioe){
				cLogger.error("关闭输出流异常");
			}
		}
		cLogger.info("Out AbcNetImpl.send()!");
	}
	
	public final void close() {
		try {
			cSocket.close();
		} catch (IOException ex) {
			cLogger.debug("Socket可能已关闭！", ex);
		}
	}
	public static void main(String[] args) throws Exception, IOException{
		Socket socket = new Socket("10.9.2.180",4000);
		Element a = new Element("abc");
		a.addContent(new Element(TranCom).setText("sdfsa"));
		a.addContent(new Element(ip).setText("10.9.2.180"));
		AbcNetImplKey net = new AbcNetImplKey(socket,a);
		
		Element mPrnts = new Element(Prnts);
		String str523="173     113     000004    04    01    AAbqcx              0 RP00T2201|01ABC|04|11|110102|04|20140116710100008885|20140116|092551|510||00000||210917010069|WT|03|2014/01/16|F|";
	
//			PgStatuscx xb = new PgStatuscx(mPrnts);
//			Document xml = xb.std2NoStd(str523);
//			JdomUtil.output(xml, System.out);
	
		
		//net.send(xml);
	}
}

