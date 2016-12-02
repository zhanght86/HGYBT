package com.sinosoft.midplat.newabc.net;

import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;
//import common.utils.StringUtility;

public class AbcNetImpl extends SocketNetImpl {
	private String cOutFuncFlag = null;
	private String cFuncFlagStr = null;
	private String cInsuID = null;
	private String cFuncFlag = null;
	public AbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into AbcNetImp.receive()...");

		//包头73位
		byte[] mHeadBytes = new byte[73];
		
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		String package_head = new String(mHeadBytes);
		//4-12 位是报文体长度
		cLogger.info("package_head:"+package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //包体长度
		
		//保险公司代码
		cInsuID=String.valueOf(Integer.parseInt(package_head.substring(12, 19).trim()));
		cLogger.info("cInsuID:"+cInsuID);
		
		cLogger.info("mBodyLen:"+mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; //所有的body字节不带xml声明
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		/**********解密请求****************/
		cLogger.info("解密开始");
		String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
		cLogger.info("解密完成");
		/**********解密完成****************/
		
		System.out.println("解密后的报文:============"+axx);
		StringBuffer abc_xml = new StringBuffer();
		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		abc_xml.append("\n");
		abc_xml.append(axx);
		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
		cLogger.info("UTF-8 农行的报文: ");
		JdomUtil.print(mXmlDoc_bank);
		//取出交易码
		Element mRootEle=mXmlDoc_bank.getRootElement();
		Element cHeader = mRootEle.getChild("Header");
		cOutFuncFlag=cHeader.getChildText("TransCode");
		cLogger.info("农行交易码为=======:"+cOutFuncFlag);
		
		Element mAbcEle = NewAbcConf.newInstance().getConf().getRootElement();
		String mTranCom = mAbcEle.getChildText("TranCom");
		
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(NewAbcConf.newInstance());
		String AppNo = cHeader.getChildText("SerialNo");
		StringBuffer mSaveName = new StringBuffer(AppNo)
		.append('_').append(cOutFuncFlag)
		.append("_in.xml");
	    SaveMessage.save(mXmlDoc_bank,mSaveName.toString(), "original");
	    cLogger.info("保存报文完毕！"+mSaveName);
	    
	    Element mAbcRootEle = mXmlDoc_bank.getRootElement();
	    Element mBaseInfo = new Element("BaseInfo");
	    
	    Element mBankCode = new Element("BankCode");
		mBankCode.setText(mAbcEle.getChildText("bank"));
	    
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(AppNo);
		
		Element mFuncFlag = new Element(FuncFlag);
		XPath mXPath = XPath.newInstance("business/funcFlag[@outcode='"
				+ cOutFuncFlag + "']");
		String mTempStr = mXPath.valueOf(mAbcEle);
		
		if (null == mTempStr || "".equals(mTempStr)) { // 未定义外部编码与内部编码映射，直接取外部编码
			mFuncFlag.setText(cOutFuncFlag);
		} else { // 有定义外部编码映射，取其对应内部编码
			mFuncFlag.setText(mTempStr);
		}
		
	    mBaseInfo.addContent(mBankCode);
		mBaseInfo.addContent(mTranNo);
		mBaseInfo.addContent(mFuncFlag);
		mAbcRootEle.addContent(mBaseInfo);
	    
		
		cLogger.info("Out AbcNetImp.receive()!");
		return mXmlDoc_bank;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into AbcNetImp.send()...");
		JdomUtil.print(pOutNoStd);
		//返回给农行的提示信息不超过50个字节（25个汉子），否则截取第一个(前面的内容   银行要求，客户和核心沟通确认结果
		Element mesgEle = pOutNoStd.getRootElement().getChild("Header").getChild("RetMsg");
		String mesg = mesgEle.getText().trim();
		if(mesg.length()>25 && mesg.indexOf("(")!=-1){
			mesg = mesg.substring(0, mesg.indexOf("("));
			mesgEle .setText(mesg);
		}
		Element mAbcEle = NewAbcConf.newInstance().getConf().getRootElement();
		String mTranCom = mAbcEle.getChildText("TranCom");
		
		String xmlStr = JdomUtil.toString(pOutNoStd); 
		//和王超超约定 一个$SPACE$ 等于5个全角空格
		//$Space$ 等于2个全角空格
		//$space$ 等于1个全角空格
//		xmlStr = StringUtility.replaceAll(xmlStr,"$SPACE$","　　　　　");
//		xmlStr = StringUtility.replaceAll(xmlStr,"$Space$","　　");
//		xmlStr = StringUtility.replaceAll(xmlStr,"$space$","　");
		
		xmlStr=xmlStr.replace("<?xml version=\"1.0\"?>","");
		
		cLogger.info("xmlStr 去掉 xml投后发送回去的报文:"+xmlStr);
		xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		String endxmlStr=AES.Encrypt(xmlStr);
		System.out.println("报文长度"+xmlStr);
		
		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml字符串的报文长度："+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();//new byte[sHeadBytes.getBytes().length];
		
		
		cSocket.getOutputStream().write(array);
		cSocket.getOutputStream().write(endxmlStr.getBytes("UTF-8"));
		cSocket.shutdownOutput();
		
		cLogger.info("Out AbcNetImp.send()!");
	}
	
}