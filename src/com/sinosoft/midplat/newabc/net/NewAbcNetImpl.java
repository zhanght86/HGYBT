package com.sinosoft.midplat.newabc.net;

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
import com.sinosoft.midplat.newabc.util.AES;
import com.sinosoft.midplat.newabc.util.AbcMidplatUtil;

/**
 * @ClassName: NewAbcNetImpl
 * @Description: 新农行套接字网络通讯实现类
 * @author sinosoft
 * @date 2017-4-13 下午3:48:41
 */
public class NewAbcNetImpl extends SocketNetImpl {
	//外部交易码
	private String cOutFuncFlag = null;
	//保险公司代码
	private String cInsuID = null;
	//交易类型
	private String cFuncFlag = null;
	
	/**
	 * <p>Title: NewAbcNetImpl</p>
	 * <p>Description: 新农行套接字网络通讯实现类构造器</p>
	 * @param pSocket 套接字
	 * @param pThisConfRoot 当前配置文件根节点
	 * @throws MidplatException
	 */
	public NewAbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		//调用父类套接字网络通讯实现类构造器
		super(pSocket, pThisConfRoot);
	}
	
	/**
	 * 接收[新农行非标准输入]报文，增加标准报文头
	 */
	public Document receive() throws Exception {
		//进入 NewAbcNetImp.receive方法...
		cLogger.info("Into NewAbcNetImp.receive()...");

		//包头73字节
		byte[] mHeadBytes = new byte[73];
		//
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		String package_head = new String(mHeadBytes);
		//4-11 位是报文体长度
		cLogger.info("package_head:"+package_head);
		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //包体长度
		
		//12-20 位是保险公司代码
		cInsuID=String.valueOf(Integer.parseInt(package_head.substring(12, 20).trim()));
		cLogger.info("cInsuID:"+cInsuID);
		
		cLogger.info("mBodyLen:"+mBodyLen);
		byte[] mBodyBytes = new byte[mBodyLen]; //所有的body字节不带xml声明
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		cLogger.info("解密前的报文:"+new String(mBodyBytes,"UTF-8"));
		/**********解密请求****************/
		cLogger.info("解密开始");
		String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
//		String axx = new String(mBodyBytes,"UTF-8");
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
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_in.xml");
	    SaveMessage.save(mXmlDoc_bank, cTranComEle.getText(), mSaveName.toString());
	    cLogger.info("保存报文完毕！"+mSaveName);

		//生成标准报文头
		Element mTranComEle = new Element(TranCom);
		mTranComEle.setText(mTranCom);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());		
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);	
		mFuncFlagEle.setText(cFuncFlag);
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cThisConfRoot.getChildText("BankCode"));
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
		mHeadEle.addContent(mBankCode);
      
		mRootEle.addContent(mHeadEle);
		
		cLogger.info("Out NewAbcNetImp.receive()!");
		return mXmlDoc_bank;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into NewAbcNetImp.send()...");
		
		//返回给农行的提示信息不超过50个字节（25个汉子），否则截取第一个(前面的内容   银行要求，客户和核心沟通确认结果
		Element mesgEle = pOutNoStd.getRootElement().getChild("Header").getChild("RetMsg");
		String mesg = mesgEle.getText().trim();
		if(mesg.length()>25 && mesg.indexOf("(")!=-1){
			mesg = mesg.substring(0, mesg.indexOf("("));
			mesgEle .setText(mesg);
		}
	    
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		
		
		cOutNoStdDoc = mSaveName.toString();
		String xmlStr = JdomUtil.toString(pOutNoStd); 
		xmlStr=xmlStr.replace("<?xml version=\"1.0\"?>","");
		cLogger.info("xmlStr 去掉 xml投后发送回去的报文:"+xmlStr);
		xmlStr=AES.rpadEncrypt(xmlStr, ' ');
		String endxmlStr=AES.Encrypt(xmlStr);
//		String endxmlStr=xmlStr;
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
		
		cLogger.info("Out NewAbcNetImp.send()!");
	}
	
}