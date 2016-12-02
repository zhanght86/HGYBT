package com.sinosoft.midplat.jhyh.net;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.adtec.security.BaseException;
import com.adtec.security.Nobis;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.jhyh.JhyhConf;
import com.sinosoft.midplat.net.SocketNetImpl;

public class JhyhNetImpl extends SocketNetImpl {
	private Nobis cNobis;
	
	public JhyhNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
//		try {
//			cNobis = Nobis.nobisFactory();
//			cNobis.bisReadKey(SysInfo.cHome + "key/jhyhKey.dat");
//		} catch (BaseException ex) {
//			cLogger.error("解析密钥文件失败！", ex);
//		}
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into jhyhNetImpl.receive()...");

		//处理报文头
		byte[] mHeadBytes = new byte[6];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		System.out.println("报文头为："+new String(mHeadBytes));
		cLogger.info("请求报文长度：" + mBodyLength);
		
		//处理报文体
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		System.out.println("报文体为："+new String(mBodyBytes));
		cSocket.shutdownInput();
		System.out.println("总报文体："+new String(mHeadBytes)+new String(mBodyBytes));
		Element mJhyhEle = JhyhConf.newInstance().getConf().getRootElement();		
		
		//金华银行同老建行都是恒生电子做的接口，金华银行报文是不加密的
//		cLogger.info("开始解密...");
//		mBodyBytes = cNobis.bisPkgDecompressDec(mBodyBytes);
//		cLogger.info("解密成功！");
		
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		Element mTransaction_Header = mRootEle.getChild("Transaction_Header");
		
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(mJhyhEle.getChildText("bank"));
		
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
		String mTempStr = mXPath.valueOf(mJhyhEle);
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
		
		cLogger.info("Out jhyhNetImp.receive()!");
		return mXmlDoc;
	}

	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into jhyhNetImpl.send()...");

		JdomUtil.print(pOutNoStd);
		
		String xmlStr = JdomUtil.toString(pOutNoStd);
		System.out.println("转换前的String报文："+xmlStr);
		xmlStr=xmlStr.replace('\r', ' ');//去掉回车
		System.out.println("去掉回车后的String报文："+xmlStr);
		xmlStr=xmlStr.replace('\n', ' ');//去掉换行
		System.out.println("去掉换行后的String报文："+xmlStr);
		Document mOutXmlDoc = JdomUtil.build(xmlStr);
		//但似乎不能消除Linux下显示出^M（换行）标识
		cLogger.info("以下打印报文：");
		JdomUtil.print(mOutXmlDoc);
		
		
		//需要我方排版保单打印格式，所以转换为字节时需保留原格式，不做任何格式化处理
		Format mFormat = Format.getRawFormat().setEncoding("GBK");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(mOutXmlDoc, mBaos);
		byte[] mBodyBytes = mBaos.toByteArray();
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(cFuncFlag)
		.append("_out.xml");
	     SaveMessage.save(mOutXmlDoc, cTranComEle.getText(), mSaveName.toString());
	     cLogger.info("保存报文完毕！"+mSaveName);
	     cOutNoStdDoc = mSaveName.toString();
	
		//金华银行同老建行都是恒生电子做的接口，金华银行报文是不加密的
//		cLogger.info("开始加密...");
//		mBodyBytes = cNobis.bisPkgCompressEnc(new String(mBodyBytes));
//		cLogger.info("加密成功！");
		
		byte[] mHeadBytes = new byte[6];
		//报文体长度
		String mLengthStr = String.valueOf(mBodyBytes.length);
		cLogger.info("返回报文长度：" + mLengthStr);
		int g = mLengthStr.length();
		if(g<=6){
			for (int i = 0; i < 6-g; i++) {
				mLengthStr =  "0" + mLengthStr;
			}
		}
		cLogger.info("补齐6位返回报文长度：" + mLengthStr);
		
//		byte[] mLengthBytes = mLengthStr.getBytes();
//		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
		
		cSocket.getOutputStream().write(mLengthStr.getBytes());	//发送报文体长度
//		cSocket.getOutputStream().write(mHeadBytes);	//发送报文头
		cSocket.getOutputStream().write(mBodyBytes);	//发送报文体
		cSocket.shutdownOutput();
		
		cLogger.info("Out jhyhNetImpl.send()!");
	}

}