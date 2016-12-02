package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class WriteOff extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private Element cTransaction_Body = null;
	
	public WriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into WriteOff.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		cTransaction_Body =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Body").clone();
		
		Document mStdXml = 
			WriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out WriteOff.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into WriteOff.std2NoStd()...");
		
		Document mNoStdXml = 
			WriteOffOutXsl.newInstance().getCache().transform(pStdXml);
		
		/*Start-组织返回报文头*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));
		
		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));
		
		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("交易成功！");
		} else {	//交易失败
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}
		
		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);
		
		cTransaction_Header.addContent(mTran_Response);
		
		mNoStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-组织返回报文头*/
		
		/*Start-组织返回报文体*/
		Element mRootEle = mNoStdXml.getRootElement();
		Element Transaction_Body = new Element("Transaction_Body");
		mRootEle.addContent(Transaction_Body);
		Element tTransaction_Body = mRootEle.getChild("Transaction_Body");
		String tcontno = cTransaction_Body.getChildText("PbInsuSlipNo");
		String sql1 = "select BAK1 from cont where contno = '"+tcontno+"'";
		String sql2 = "select APPNTNAME from cont where contno = '"+tcontno+"'";
		String sql3 = "select INSUREDNAME from cont where contno = '"+tcontno+"'";
		String sql4 = "select PREM from cont where contno = '"+tcontno+"'";
		ExeSQL dExeSQL = new ExeSQL();
		String BAK1 = dExeSQL.getOneValue(sql1);
		String APPNTNAME = dExeSQL.getOneValue(sql2);
		String INSUREDNAME = dExeSQL.getOneValue(sql3);
		String PREM = dExeSQL.getOneValue(sql4);
		Element PbInsuType = new Element("PbInsuType");
		Element PbHoldName = new Element("PbHoldName");
		Element LiRcgnName = new Element("LiRcgnName");
		Element LiLoanValue = new Element("LiLoanValue");
		Element PbInsuSlipNo = new Element("PbInsuSlipNo");
		tTransaction_Body.addContent(PbInsuType);
		tTransaction_Body.addContent(PbHoldName);
		tTransaction_Body.addContent(LiRcgnName);
		tTransaction_Body.addContent(LiLoanValue);
		tTransaction_Body.addContent(PbInsuSlipNo);
		tTransaction_Body.getChild("PbInsuType").setText(BAK1);
		tTransaction_Body.getChild("PbHoldName").setText(APPNTNAME);
		tTransaction_Body.getChild("LiRcgnName").setText(INSUREDNAME);
		tTransaction_Body.getChild("LiLoanValue").setText(com.sinosoft.midplat.common.NumberUtil.fenToYuan(PREM));
		tTransaction_Body.getChild("PbInsuSlipNo").setText(tcontno);
		
		/*End-组织返回报文体*/
		
		cLogger.info("Out WriteOff.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//		
//		String mInFilePath = "F:\\中融建行test\\ccb_当日撤单.xml";
//		String mOutFilePath = "F:\\中融建行test\\ccb.xml";
//		
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
////		Document mOutXmlDoc = new WriteOff(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new WriteOff(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//		
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//		
//		System.out.println("成功结束！");
//	}
}