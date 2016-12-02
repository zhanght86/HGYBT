//建行续期缴费确认
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class RenewalPay extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String stranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private String sProposalPrtNo = null;
	private String sContNo = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	 
	public RenewalPay(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewalPay.noStd2Std()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
		//服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//取续期缴费对应保单的保险公司方交易流水号
		stranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_Co_Jrnl_No");
		//临时保存银行发起交易日期作为保险公司账务日期 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		Document mStdXml = 
			RenewalPayInXsl.newInstance().getCache().transform(pNoStdXml);

		//用交易流水号从Tranlog表中查找对应的保单号,续期确认的时候发的流水号是续期查询的流水号
		String getContNoSQL = new StringBuilder("select contno from tranlog where tranno = '").append(stranNo).append("'")
		.append(" and rcode='0' and funcflag='1033'").toString();
		String getProposalPrtNoSQL = new StringBuilder("select proposalprtno from tranlog where tranno = '").append(stranNo).append("'")
		.append(" and rcode='0' and funcflag='1033'").toString();
		sContNo = new ExeSQL().getOneValue(getContNoSQL);
		sProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
		if(!"".equals(sContNo)&&sContNo!=null){
			String getProposalPrtNoSQL2 = new StringBuilder("select proposalprtno from cont where contno = '").append(sContNo).append("' and state='2'").toString();
			String sProposalPrtNo2 = new ExeSQL().getOneValue(getProposalPrtNoSQL2);
			mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalPrtNo2);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContNo);
		}
		if(!"".equals(sProposalPrtNo)&&sProposalPrtNo!=null){
			String getContNoSQL2 = new StringBuilder("select contno from cont where proposalprtno = '").append(sProposalPrtNo).append("' and state='2'").toString();
			String sConttNo2 = new ExeSQL().getOneValue(getContNoSQL2);
			mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(sProposalPrtNo);
			mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sConttNo2);
		}
	
		cLogger.info("Out RenewalPay.noStd2Std()!");
		return mStdXml;
	}
	
	/**
	 * 供重打和查询使用。	
	 */
	void setHeader(Element pTransaction_Header) {
		cTransaction_Header = pTransaction_Header;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewalPay.std2NoStd()...");

//		String mInFilePath2 = "H:/李路/任务/P53819156InNoStd.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"GBk");
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_Co_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();		
		
		Document mNoStdXml =  
			RenewalPayOutXsl.newInstance().getCache().transform(pStdXml);
		
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

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-组织返回报文头*/

			cLogger.info("Out RenewalPay.std2NoStd()!");
			
			return mNoStdXml;
		}
		

//		public static void main(String[] args) throws Exception {
//			System.out.println("程序开始…");
//
////			String mInFilePath = "H:/李路/模拟建行报文/ccb_CCB000000000111111_P53819156_134728.xml";
////			String mOutFilePath = "H:/李路/模拟建行报文/P53819156inSvc.xml";
//			String mInFilePath = "H:/李路/任务/P53819156InNoStd.xml";
//			String mOutFilePath = "H:/李路/任务/P53819156inSvcStd.xml";
//
//			InputStream mIs = new FileInputStream(mInFilePath);
//			Document mInXmlDoc = JdomUtil.build(mIs,"UTF-8");
//			mIs.close();
//
////			Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
//			Document mOutXmlDoc = new RenewalPay(null).noStd2Std(mInXmlDoc);
//
//			JdomUtil.print(mOutXmlDoc);
//
//			OutputStream mOs = new FileOutputStream(mOutFilePath);
//			JdomUtil.output(mOutXmlDoc, mOs);
//			mOs.flush();
//			mOs.close();
//
//			System.out.println("成功结束！");
//
//		}
		
}
