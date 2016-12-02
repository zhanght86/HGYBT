package com.sinosoft.midplat.abc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Rollback extends XmlSimpFormat {

	public Rollback(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Rollback.noStd2Std()...");
//		JdomUtil.print(pNoStdXml);
		Document mStdXml = 
			RollbackInXsl.newInstance().getCache().transform(pNoStdXml);
//		JdomUtil.print(mStdXml);
//		Element mBodyEle = mStdXml.getRootElement().getChild(Body);
//		String mTranNo = pNoStdXml.getRootElement().getChild("ConfirmInfo").getChildText("OldTranNo");
//		Element mmContNo= mBodyEle.getChild(ContNo);
//		Element mmOtherNO = mBodyEle.getChild(ContPrtNo);
//		Element mmPro = mBodyEle.getChild(ProposalPrtNo);
//		String mSqlStr = "select otherno, ProposalPrtNo  from tranlog where tranno='"+mTranNo+"'";
//		String mOtherNo = null;
//		String mProposalPrtNo=null;
//		 SSRS ssrs = new SSRS();
//	        ssrs = new ExeSQL().execSQL(mSqlStr);
//	        if (ssrs.MaxRow > 0) {
//	        	mOtherNo = ssrs.GetText(1, 1);
//	        	mProposalPrtNo=ssrs.GetText(1, 2);
//	        }  
//		
//		if (null == mOtherNo || "".equals(mOtherNo)) {
//			throw new MidplatException("找不到相应的保单信息！");
//		}
//		mmOtherNO.setText(mOtherNo);
//		if (null == mProposalPrtNo|| "".equals(mProposalPrtNo)) {
//			throw new MidplatException("找不到相应的保单信息！");
//		}
//		mmPro.setText(mProposalPrtNo);

		cLogger.info("Out Rollback.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Rollback.std2NoStd()...");

		Document mNoStdXml = 
			RollbackOutXsl.newInstance().getCache().transform(pStdXml);

		cLogger.info("Out Rollback.std2NoStd()!");
		return mNoStdXml;
	}
}
