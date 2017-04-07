package com.sinosoft.midplat.gzbank.net;

import java.net.Socket;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.net.SocketNetImpl;

public class GZBankNetImpl extends SocketNetImpl {
	/**
	 * 必须返回的基本信息
	 */
	private Element mTransNo = null;		//签到银行代码
	private Element mQdBankCode = null;		//签到银行代码
	private Element mBankCode = null;		//区域银行代码
	private Element mBranch = null;			//银行网点代码
	private Element mInsuOrgNo = null;		//保险公司机构代码
	private Element mTransExeDate = null;	//银行交易日期
	private Element mTransRefGUID = null;	//银行流水号
	private Element mTeller = null;			//银行操作员、柜员
	private Element mTellerName = null;		//银行操作员
	
	private String cInsuID="";      //保险公司代码
	
	private String cFuncFlag = null;
	private String cOutFuncFlag = null;
	
	public GZBankNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	public Document receive() throws Exception {
		cLogger.info(Thread.currentThread().getName() +"Into GZBankNetImpl.receive()...");
		
		/** 第一步 处理报文头 */
		byte[] mHeadBytes = new byte[19];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		cLogger.info("Head：" + new String(mHeadBytes));
		
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		cLogger.info("请求报文长度：" + mBodyLength);
		cOutFuncFlag = new String(mHeadBytes, 6, 7).trim();
		cLogger.info("交易代码：" + cOutFuncFlag);
		cInsuID = new String(mHeadBytes, 13, 6).trim();
		cLogger.info("保险公司代码：" + cInsuID);
		
		// 处理报文体
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		/**
		 * 赋值部分
		 */
		mTransNo = (Element) mRootEle.getChild("TransNo").clone();
		mQdBankCode = (Element) mRootEle.getChild("QdBankCode").clone();
		mInsuOrgNo = (Element) mRootEle.getChild("InsuOrgNo").clone();
		mBankCode = (Element) mRootEle.getChild("BankCode").clone();
		mBranch = (Element) mRootEle.getChild("Branch").clone();
		mTransExeDate = (Element) mRootEle.getChild("TransExeDate").clone();
		mTransRefGUID = (Element) mRootEle.getChild("TransRefGUID").clone();
		mTeller = (Element) mRootEle.getChild("Teller").clone();
		mTellerName = (Element) mRootEle.getChild("TellerName").clone();
		
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		
		XPath mXPath = XPath.newInstance("msg/@path");
		String mMsgPath = mXPath.valueOf(cThisConfRoot);
		if (null==mMsgPath || "".equals(mMsgPath)) {
			mMsgPath = mTranCom;
		} 
		XPath mXPath2 = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
	    cLogger.info("保存非标准报文完毕！"+mSaveName);
	    
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
		
		mXmlDoc = JdomUtil.build(mBodyBytes);
		
		Element tRootEle = mXmlDoc.getRootElement();
		
		tRootEle.addContent(mHeadEle);	
		JdomUtil.print(mXmlDoc);
		cLogger.info("Out GZBankNetImpl.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into GZBankNetImpl.send()...");	
		//赋值报文头信息
		Element mRootEle = pOutNoStd.getRootElement();
		mRootEle.addContent(mTransNo).addContent(mQdBankCode).addContent(mBankCode).addContent(mBranch)
		.addContent(mInsuOrgNo).addContent(mTransExeDate).addContent(mTransRefGUID).addContent(mTeller)
		.addContent(mTellerName);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cOutNoStdDoc = mSaveName.toString();
		cLogger.info("保存报文完毕！"+mSaveName);
		
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		cLogger.info("返回报文：" + new String(mBodyBytes));
		
		byte[] mHeadBytes = new byte[19];
		// 初始化前19位报文头为0
		for (int i = 0; i < 19; i++) {
			mHeadBytes[i] = ' ';
		}
		// 报文体长度
		String mLengthString = String.valueOf(mBodyBytes.length);
		cLogger.info("返回报文长度：" + mLengthString);
		byte[] mLengthBytes = mLengthString.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		// 交易代码
		byte[] mFuncFlagBytes = cOutFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mHeadBytes, 6,
				mFuncFlagBytes.length);
		
		//目标保险公司代码
		System.arraycopy(cInsuID.getBytes(), 0, mHeadBytes, 13,
				cInsuID.getBytes().length);
		
		//发送报文体
		cSocket.getOutputStream().write(mHeadBytes);
		cSocket.getOutputStream().write(mBodyBytes);
		cSocket.shutdownOutput();
		cLogger.info("Out GZBankNetImpl.send()!");
	}	
}