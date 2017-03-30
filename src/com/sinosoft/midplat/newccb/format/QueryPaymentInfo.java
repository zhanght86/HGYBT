package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryPaymentInfo extends XmlSimpFormat {
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
	
	public QueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.noStd2Std()...");
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
				QueryPaymentInfoInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info(JdomUtil.toStringFmt(mStdXml));
		sProposalPrtNo=mStdXml.getRootElement().getChild("LCCont").getChildText("PrtNo");
//		sContno=mStdXml.getRootElement().getChild("LCCont").getChildText("ContNo");
		if(sContno==null||sContno.equals("")){
			//从cont表中查找对应的保单号
			String getContNoSQL = new StringBuilder("select contno from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
			sContno = new ExeSQL().getOneValue(getContNoSQL);
			if(sContno==null&&sContno.equals("")){
				mStdXml.getRootElement().getChild("LCCont").getChild("ContNo").setText(sProposalPrtNo);
			}else {
//				mStdXml.getRootElement().getChild("LCCont").getChild("ContNo").setText(sContno);
			}
		}
		
		cLogger.info("Out QueryPaymentInfo.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.std2NoStd()...");
		Document mNoStdXml = 
				QueryPaymentInfoOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
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

			Element mRetData = pStdXml.getRootElement().getChild("RetData");
			if (mRetData.getChildText(Flag).equals("1")) {	//交易成功
				
//				sPayedTimes=pStdXml.getRootElement().getChild("Body").getChildText("PayedTimes");
				//已交保费期数>=1，表示是续期缴费查询，否则为实时投保缴费查询
//				if(Integer.parseInt(sPayedTimes)>=1){
//					mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("14");
//				}else{
//					mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgInsPyFBsnSbdvsn_Cd").setText("11");
//				}
			
			
//			sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
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
//				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
				
				//从cont表中查找对应的投保单号的建行代理保险套餐编号，实时投保时已存入备用字段9 bak9
				String getAgInsPkgIDSQL2 = new StringBuilder("select bak9 from cont where ProposalPrtNo = '").append(sProposalPrtNo).append("'").toString();
				sAgInsPkgID = new ExeSQL().getOneValue(getAgInsPkgIDSQL2);
				//APP_ENTITY节点加入建行代理保险套餐编号
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAgInsPkgID);
			}
			
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			} else {	//交易失败
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
				mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			}
			
			/*End-组织返回报文头*/

			cLogger.info("Out QueryPaymentInfo.std2NoStd()!");
			return mNoStdXml;
	}

}
