package com.sinosoft.midplat.ccb.net;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.adtec.security.BaseException;
import com.adtec.security.Nobis;
import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;

public class CcbNetImpl extends SocketNetImpl {
	private Nobis cNobis;
	
	public CcbNetImpl(Socket pSocket,Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
		try {
			cNobis = Nobis.nobisFactory();
			cNobis.bisReadKey(SysInfo.cHome + "key/ccbKey.dat");
		} catch (BaseException ex) {
			cLogger.error("解析密钥文件失败！", ex);
		}
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into CcbNetImpl.receive()...");
		
		//处理报文头
		byte[] mHeadBytes = new byte[6];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		System.out.println("文件头为："+new String(mHeadBytes));
		cLogger.debug("请求报文长度：" + mBodyLength);
		
		//处理报文体
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
//		System.out.println("文件体为："+new String(mBodyBytes));
		Element mCcbEle = CcbConf.newInstance().getConf().getRootElement();		
		
		cLogger.info("开始解密...");
		mBodyBytes = cNobis.bisPkgDecompressDec(mBodyBytes);
		cLogger.info("解密成功！");
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mTransaction_Header = mRootEle.getChild("Transaction_Header");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(mCcbEle.getChildText("bank"));
		
		Element mZoneNo = new Element("ZoneNo");
		Element mBrNo = new Element("BrNo");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mTransaction_Header.getChildText("BkPlatSeqNo"));
		
		Element mFuncFlag = new Element(FuncFlag);
		String mOutFuncFlagStr = mTransaction_Header.getChildText("BkTxCode");

		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mOutFuncFlagStr + "']");
		String mTempStr = mXPath.valueOf(mCcbEle);
		cLogger.info("交易标示"+mTempStr);
		if (null==mTempStr || "".equals(mTempStr)) {	//未定义外部编码与内部编码映射，直接取外部编码
			mFuncFlag.setText(mOutFuncFlagStr);
		} else {	//有定义外部编码映射，取其对应内部编码
			mFuncFlag.setText(mTempStr);
		}
		
		/** 生成标准头报文  */
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		System.out.println("InNoDoc"+mSaveName.toString());
//		Element mManageCom = new Element(ManageCom);
//		Element mAgentCom = new Element(AgentCom);
//		Element mAgentComName = new Element(AgentComName);
//		Element mAgentName = new Element(AgentName);
//		Element mAgentCode = new Element(AgentCode);
//		Element mUntiCode = new Element("UnitCode");
//		Element mAgentGrade = new Element("AgentGrade");
//		Element mAgentCodeGrade = new Element("AgentCodeGrade");
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
//		mHeadEle.addContent(mManageCom);
//		mHeadEle.addContent(mAgentCom);
//		mHeadEle.addContent(mAgentComName);
//		mHeadEle.addContent(mAgentName);
//		mHeadEle.addContent(mAgentCode);
//		mHeadEle.addContent(mUntiCode);
//		mHeadEle.addContent(mAgentGrade);
//		mHeadEle.addContent(mAgentCodeGrade);
		mHeadEle.addContent(mInNoDoc);
		mRootEle.addContent(mHeadEle);
		JdomUtil.print(mXmlDoc);
		cLogger.info("Out CcbNetImpl.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into CcbNetImpl.send()...");
		JdomUtil.print(pOutNoStd);
		//建行需要我方排版保单打印格式，所以转换为字节时需保留原格式，不做任何格式化处理
		Format mFormat = Format.getRawFormat().setEncoding("GBK");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(pOutNoStd, mBaos);
		byte[] mBodyBytes = mBaos.toByteArray();
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
	     SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
	     cLogger.info("保存报文完毕！"+mSaveName);
	     cOutNoStdDoc = mSaveName.toString();
		cLogger.info("开始加密...");
		mBodyBytes = cNobis.bisPkgCompressEnc(new String(mBodyBytes));
		cLogger.info("加密成功！");
		
		byte[] mHeadBytes = new byte[6];
		//报文体长度
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("返回报文长度：" + mLengthStr);
		byte[] mLengthBytes = mLengthStr.getBytes();
		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mHeadBytes);	//发送报文头
		cSocket.getOutputStream().write(mBodyBytes);	//发送报文体
		cSocket.shutdownOutput();
		
		cLogger.info("Out CcbNetImpl.send()!");
	}
}
