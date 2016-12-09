//建行自动冲正
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class AutoWriteOff extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String stranNo = null;
	private String tranDate = null;
	private String sProposalPrtNo = null;
	private String sContPrtNo = null;
	private String sContNo = null;
	private String sfuncflag = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;

	public AutoWriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into AutoWriteOff.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();

		//服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Txn_Cd");	
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");	
		
		//临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//取需要冲正的保单对应的保险公司方交易流水号
		stranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Orig_TxnSrlNo");
		//临时保存银行发起交易日期作为保险公司账务日期 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		Document mStdXml = 
			AutoWriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//用交易流水号从Tranlog表中查找对应的投保单号
		String getProposalPrtNoSQL = new StringBuilder("select ProposalPrtNo from tranlog where tranno = '").append(stranNo).append("'").toString();
		sProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
		mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalPrtNo);
		
//		if(sysTxCode.equals("")||sysTxCode==null){
			String getfuncflagSQL = new StringBuilder("select funcflag from tranlog where tranno = '").append(stranNo).append("'").toString();
			sfuncflag = new ExeSQL().getOneValue(getfuncflagSQL);
			mStdXml.getRootElement().getChild("Body").getChild("Type").setText(sfuncflag);
//		}
		
		if(sfuncflag.equals("1034")){//续期冲正
			mStdXml.getRootElement().getChild("Body").getChild("Flag").setText("1");
			String getContNoSQL = new StringBuilder("select contno from tranlog where tranno = '").append(stranNo).append("'").toString();
			sContNo = new ExeSQL().getOneValue(getContNoSQL);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContNo);
		}
		if(sfuncflag.equals("1017")){//退保冲正
			SSRS tSSRS= new SSRS();
			ExeSQL  tExeSQL = new ExeSQL();
			String getContNoSQL = new StringBuilder("select contno from tranlog where tranno = '").append(stranNo).append("'").toString();
			sContNo = new ExeSQL().getOneValue(getContNoSQL);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContNo);
			String getContPrtNoSQL = new StringBuilder("select proposalprtno,otherno from tranlog where contno = '").append(sContNo).append("'").toString();
			tSSRS=tExeSQL.execSQL(getContPrtNoSQL);
			for (int j = 1;j<=tSSRS.getMaxRow();j++) {
				mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(tSSRS.GetText(1,1));
				mStdXml.getRootElement().getChild("Body").getChild("ContPrtNo").setText(tSSRS.GetText(1,2));
			}
		}
		cLogger.info("Out AutoWriteOff.noStd2Std()!");
		JdomUtil.print(mStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into AutoWriteOff.std2NoStd()...");
		System.out.println("报文信息如下：");
		JdomUtil.print(pStdXml);
		
//		String mInFilePath2 = "E:/994374_3_112_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBK");
//		
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();
		
		Document mNoStdXml = 
			AutoWriteOffOutXsl.newInstance().getCache().transform(pStdXml);

		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//当核保成功的时候，返回TX_BODY时，增加COM_ENTITY节点
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		//COM_ENTITY节点加入服务方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		
		//COM_ENTITY节点加入保险公司方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		
		/*Start-组织返回报文头*/
		JdomUtil.print(mNoStdXml);
		
		Element mSYS_TX_STATUS =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");
		//服务响应码
		Element mSYS_RESP_CODE =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE");
		//服务响应描述长度
		Element mSYS_RESP_DESC_LEN =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN");
		//服务响应描述
		Element mSYS_RESP_DESC =  mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");
		
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if(mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mSYS_TX_STATUS.setText("00");
			mSYS_RESP_CODE.setText("000000000000");
			mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText(Desc).length()));
			mSYS_RESP_DESC.setText(mRetData.getChildText(Desc));
		} else{	//交易失败
			mSYS_TX_STATUS.setText("01");
			mSYS_RESP_CODE.setText("ZZZ072000001");
			mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText(Desc).length()));
			mSYS_RESP_DESC.setText(mRetData.getChildText(Desc));
		}
		
		/*End-组织返回报文头*/
		
		cLogger.info("Out AutoWriteOff.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
////		String mInFilePath = "H:/李路/任务/P53819113OutSvrStd.xml";
////		String mOutFilePath = "H:/李路/任务/P53819113OutNoStd2.xml";
//		String mInFilePath = "E:/994374_5_2_outSvc.xml";
//		String mOutFilePath = "E:/11111.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs,"GBK");
//		mIs.close();
//		
//		Document mOutXmlDoc = new AutoWriteOff(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new AutoWriteOff(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("成功结束！");
//
//	}
}
