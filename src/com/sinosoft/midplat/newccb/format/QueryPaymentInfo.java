package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ExeSQL;

public class QueryPaymentInfo extends XmlSimpFormat {
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sProposalPrtNo = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	public QueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryPaymentInfo.noStd2Std()...");
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
				//根据投保单号查询非实时投保时的：保险年期类别代码、保险期限、保险周期代码、保费缴费方式代码、保费缴费期数、保费缴费周期代码 
				Element mAppEntityEle = mNoStdXml.getRootElement().getChild("TX_BODY")
				.getChild("ENTITY").getChild("APP_ENTITY");
				Element mLcCont=pStdXml.getRootElement().getChild("LCCont");
				sProposalPrtNo= mLcCont.getChildText("PrtNo");
				String sql="select BAK1 from TranLog where ProposalPrtNo='"+sProposalPrtNo+"' " +
						"and funcflag='1060' and rcode='0'";
				String mBak1 = new ExeSQL().getOneValue(sql);
				if(mBak1 == null || mBak1.equals("")){
					throw new MidplatException("根据投保单号未查询到非实时投保相应信息，请确认！");
				}else{
					String strs[] = mBak1.split("\\|");
					 //保险年期类别代码
					mAppEntityEle.getChild("Ins_Yr_Prd_CgyCd").setText(strs[0]);
				    //保险期限
					mAppEntityEle.getChild("Ins_Ddln").setText(strs[1]);
				    //保险周期代码
					mAppEntityEle.getChild("Ins_Cyc_Cd").setText(strs[2]);
				    //保费缴费方式代码
					mAppEntityEle.getChild("InsPrem_PyF_MtdCd").setText(strs[3]);
				    //保费缴费期数
					mAppEntityEle.getChild("InsPrem_PyF_Prd_Num").setText(strs[4]);
				    //保费缴费周期代码 
					mAppEntityEle.getChild("InsPrem_PyF_Cyc_Cd").setText(strs[5]);
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
