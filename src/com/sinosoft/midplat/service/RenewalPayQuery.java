package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class RenewalPayQuery extends ServiceImpl {
	public RenewalPayQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into RenewalPayQuery.service()...");
		cInXmlDoc = pInXmlDoc;	
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			Element mRootEle = cInXmlDoc.getRootElement();
			Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
			Element mBodyEle = mRootEle.getChild(Body);
			String wContNo  = mBodyEle.getChildText("ContNo");
			
		//中行续期缴费查询交易 直接组织报文返回给银行   

			Document mOutStdXml = null;
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
			.append(" and TranDate=").append(cTranLogDB.getTranDate())
			.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
			.append(" and ContNo=").append(cTranLogDB.getContNo())
			.append(" and TranCom='").append(cTranLogDB.getTranCom()).append('\'')
			.toString();
			ExeSQL ExeSQL = new ExeSQL();
			Element tInsut = new Element("Insut");
			Element tMaina = new Element("Maina");
			Element tResultCode = new Element("ResultCode");
			Element tResultInfo = new Element("ResultInfo");
			Element tPolicyNo = new Element("PolicyNo");
			Element tRecvDate = new Element("RecvDate");
			Element tRecvAmount = new Element("RecvAmount");
			Element tRecvNum = new Element("RecvNum");
			Element tPayStartDate = new Element("PayStartDate");
			Element tPayEndDate = new Element("PayEndDate");
			Element tAppntName = new Element("AppntName");
			Element tRiskName = new Element("RiskName");
			if ("1".equals(ExeSQL.getOneValue(tSqlStr)))
			{
				tMaina.addContent(tResultCode.setText("0001"));
				tMaina.addContent(tResultInfo.setText("已经成功做过续期缴费查询交易，不能重复操作"));
				tMaina.addContent(tPolicyNo.setText(wContNo));
				tMaina.addContent(tRecvDate.setText(""));
				tMaina.addContent(tRecvAmount.setText(""));
				tMaina.addContent(tRecvNum.setText(""));//应收期数
				tMaina.addContent(tPayStartDate.setText(""));
				tMaina.addContent(tPayEndDate.setText(""));
				tMaina.addContent(tAppntName.setText(""));
				tMaina.addContent(tRiskName.setText(""));
			}
			else{
			String sql = "select count(*) from cont where trancom = '3' and contno = '"+wContNo+"'";
			String sql1 = "select signdate from cont where trancom = '3' and contno = '"+wContNo+"'";
			String sql2 = "select prem from cont where trancom = '3' and contno = '"+wContNo+"'";
			String sql3 = "select appntname from cont where trancom = '3' and contno = '"+wContNo+"'";
			String sql4 = "select bak1 from cont where trancom = '3' and contno = '"+wContNo+"'";
//			ExeSQL ExeSQL = new ExeSQL();
			String count = ExeSQL.getOneValue(sql);
			String msigndate = ExeSQL.getOneValue(sql1);
			String mprem = ExeSQL.getOneValue(sql2);
			String mappntname = ExeSQL.getOneValue(sql3);
			String mbak1 = ExeSQL.getOneValue(sql4);
			String qriskname = "";
			if(mbak1.equals("321010"))
			{
				qriskname="中融融盛连年万能B款";
			}
			if(mbak1.equals("313030"))
			{
				qriskname="中融融化富贵分红A";
			}
			if(mbak1.equals("313050"))
			{
				qriskname="中融荣华富贵分红C";
			}
			if(count.equals("0"))
			{
				cLogger.info("保单数量为："+count);
				tMaina.addContent(tResultCode.setText("0001"));
				tMaina.addContent(tResultInfo.setText("保险公司没有此保单"));
			}
			else{
				tMaina.addContent(tResultCode.setText("0000"));
				tMaina.addContent(tResultInfo.setText("交易成功"));	
			}
			
			tMaina.addContent(tPolicyNo.setText(wContNo));
			tMaina.addContent(tRecvDate.setText(msigndate));
			tMaina.addContent(tRecvAmount.setText(mprem));
			tMaina.addContent(tRecvNum.setText("5"));//应收期数
			tMaina.addContent(tPayStartDate.setText(msigndate));
			tMaina.addContent(tPayEndDate.setText(msigndate));
			tMaina.addContent(tAppntName.setText(mappntname));
			tMaina.addContent(tRiskName.setText(qriskname));
			}
			tInsut.addContent(tMaina);
			mOutStdXml = new Document(tInsut);	
			cOutXmlDoc = mOutStdXml;
			
			
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {
			Element tMainaEle = cOutXmlDoc.getRootElement().getChild("Maina");
			cTranLogDB.setRCode(tMainaEle.getChildText("ResultCode"));
			cTranLogDB.setRText(tMainaEle.getChildText("ResultInfo"));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out RenewalPayQuery.service()!");
		return cOutXmlDoc;
	}
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		if (null != mBodyEle) {
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
		}
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert()) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
}
