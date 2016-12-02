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
	String mTransNo = "";		//交易码
	String mQdBankCode = "";	//签到银行代码
	String mBankCode = "";		//区域银行代码
	String mBranch = "";		//银行网点代码
	String mInsuOrgNo = "";		//保险公司统一编码
	String mTransExeDate = "";	//银行交易日期
	String mTransExeTime = "";	//银行交易时间
	String mTransRefGUID = "";	//银行流水号
	String mTeller = "";		//银行操作员、柜员
	String mTellerName = "";	//银行操作员
	String mCpicWater = "";		//保险公司流水号
	String m = "";				//备用
	
	
	String cOutFuncFlag = "";
	String cInsuID="";
	public GZBankNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	public Document receive() throws Exception {
		cLogger.info("Into GZBankNetImpl.receive()...");
		byte[] mHeadBytes = new byte[19];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		cLogger.info("请求报文长度：" + mBodyLength);
		cOutFuncFlag = new String(mHeadBytes, 6, 7).trim();
		cLogger.info("交易代码：" + cOutFuncFlag);
		cInsuID = new String(mHeadBytes, 13, 6).trim();
		cLogger.info("保险公司代码：" + cInsuID);
		
		
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mTXLife = mXmlDoc.getRootElement();
		
		/**
		 * 赋值部分
		 */
		this.mTransNo = mTXLife.getChildText("TransNo");
		this.mQdBankCode = mTXLife.getChildText("QdBankCode");
		this.mBankCode = mTXLife.getChildText("BankCode");
		this.mBranch = mTXLife.getChildText("Branch");
		this.mTransExeDate = mTXLife.getChildText("TransExeDate");
		this.mTransExeTime = mTXLife.getChildText("TransExeTime");
		this.mTransRefGUID = mTXLife.getChildText("TransRefGUID");
		this.mTeller = mTXLife.getChildText("Teller");
		this.mTellerName = mTXLife.getChildText("TellerName");
		
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
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
      
		mRootEle.addContent(mHeadEle);
	    
	    JdomUtil.print(mXmlDoc);
		cLogger.info("Out GZBankNetImpl.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into GZBankNetImpl.send()...");	
		Element mRootEle = pOutNoStd.getRootElement();
		mRootEle.getChild("TransNo").setText(this.mTransNo);
		mRootEle.getChild("QdBankCode").setText(this.mQdBankCode);
		mRootEle.getChild("BankCode").setText(this.mBankCode);
		mRootEle.getChild("Branch").setText(this.mBranch);
		mRootEle.getChild("TransExeDate").setText(this.mTransExeDate);
		mRootEle.getChild("TransExeTime").setText(this.mTransExeTime);
		mRootEle.getChild("TransRefGUID").setText(this.mTransRefGUID);
		mRootEle.getChild("Teller").setText(this.mTeller);
		mRootEle.getChild("TellerName").setText(this.mTellerName);
		mRootEle.getChild("CpicWater").setText(this.mTransRefGUID);
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cOutNoStdDoc = mSaveName.toString();
		cLogger.info("保存报文完毕！"+mSaveName);
	    cLogger.info("返回非标准报文:"+JdomUtil.toStringFmt(pOutNoStd));
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		String head="";
		String byteLength=mBodyBytes.length+"";
		int count=6-byteLength.length();
		if(byteLength.length()<6){
		    for (int i = 0; i <count; i++) {
		    	byteLength+=" ";
			}
		}
		head=byteLength+cOutFuncFlag+cInsuID+"   ";
		byte[] mHeadBytes = head.getBytes();
		cLogger.info("send消息头长度为:"+head.getBytes().length);

		//发送报文体
		cSocket.getOutputStream().write(mHeadBytes);
		cSocket.getOutputStream().write(mBodyBytes);
		cSocket.shutdownOutput();
		cLogger.info("Out GZBankNetImpl.send()!");
	}	
}