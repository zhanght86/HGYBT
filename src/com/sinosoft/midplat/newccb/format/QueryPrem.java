//建行查询缴费信息
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

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryPrem extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sContno = null;
	private String sProposalPrtNo = null;
	private String sLv1BrNo = null;
	private String sAgInsPkgID = null;
	private String sysTxCode = null;
	private String sPayedTimes  = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private Document cInNoStdXml = null;
	
	
	public QueryPrem(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPrem.noStd2Std()...");
		cInNoStdXml=pNoStdXml;
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
		//服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		//临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//临时保存银行发起交易日期作为保险公司账务日期 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		
		Document mStdXml = 
			QueryPremInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		sProposalPrtNo=mStdXml.getRootElement().getChild("Body").getChildText("ProposalPrtNo");
		sContno=mStdXml.getRootElement().getChild("Body").getChildText("ContNo");
		if(sContno==null||sContno.equals("")){
			//从cont表中查找对应的保单号
			String getContNoSQL = new StringBuilder("select contno from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sContno = new ExeSQL().getOneValue(getContNoSQL);
			if(sContno==null&&sContno.equals("")){
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sProposalPrtNo);
			}else {
				mStdXml.getRootElement().getChild("Body").getChild("ContNo").setText(sContno);
			}
		}
		
		cLogger.info("Out QueryPrem.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryPrem.std2NoStd()...");
		
//		String mInFilePath2 = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/1053981_1_1033_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2);
//		cInNoStdXml=mInXmlDoc;
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();
		
		
			
		Document mNoStdXml = 
			QueryPremOutXsl.newInstance().getCache().transform(pStdXml);

		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
		

		//COM_ENTITY节点加入服务方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		//COM_ENTITY节点加入保险公司方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		/*Start-组织返回报文头*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			
			sPayedTimes=pStdXml.getRootElement().getChild("Body").getChildText("PayedTimes");
			//已交保费期数>=1，表示是续期缴费查询，否则为实时投保缴费查询
			if(Integer.parseInt(sPayedTimes)>=1){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("14");
			}else{
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("11");
			}
		
		
		sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		sProposalPrtNo=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_BillNo");
		
		if(sContno!=null&&!sContno.equals("")){
			//从cont表中查找对应的投保单号的建行一级分行号，实时投保时已存入备用字段10 bak10
			String getLv1BrNoSQL = new StringBuilder("select bak10 from cont where contno = '").append(sContno).append("'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL);
			//APP_ENTITY节点加入建行一级分行号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
			
			//从cont表中查找对应的投保单号的建行代理保险套餐编号，实时投保时已存入备用字段9 bak9
			String getAgInsPkgIDSQL = new StringBuilder("select bak9 from cont where contno = '").append(sContno).append("'").toString();
			sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL);
			//APP_ENTITY节点加入建行代理保险套餐编号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
		} else if(sProposalPrtNo!=null&&!sProposalPrtNo.equals("")){
			//从cont表中查找对应的投保单号的建行一级分行号，实时投保时已存入备用字段10 bak10
			String getLv1BrNoSQL2 = new StringBuilder("select bak10 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL2);
			//APP_ENTITY节点加入建行一级分行号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
			
			//从cont表中查找对应的投保单号的建行代理保险套餐编号，实时投保时已存入备用字段9 bak9
			String getAgInsPkgIDSQL2 = new StringBuilder("select bak9 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL2);
			//APP_ENTITY节点加入建行代理保险套餐编号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
		}
		
		//20150311 建行要输入保单号就只返回保单号，输入投保单号就返回投保单号，二选一，不能同时返回
		String contno=cInNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		String proposalprtno=cInNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Ins_BillNo");
		if("".equals(contno)||contno==null){
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPolcy_No").setText("");
		}
		if("".equals(proposalprtno)||proposalprtno==null){
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_BillNo").setText("");
		}
		
			
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-组织返回报文头*/

		cLogger.info("Out QueryPrem.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
//		String mInFilePath = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/1053981_3_28_outSvc.xml";
//		String mOutFilePath = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/查询保费缴纳信息out.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
//		
//		Document mOutXmlDoc = new QueryPrem(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new QueryPrem(null).noStd2Std(mInXmlDoc);
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