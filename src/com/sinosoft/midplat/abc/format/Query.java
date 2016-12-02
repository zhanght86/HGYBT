package com.sinosoft.midplat.abc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class Query extends XmlSimpFormat {
	private final NewCont cNewCont = new NewCont(cThisBusiConf);
	
	private boolean cType = false;

	public Query(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Query.noStd2Std()...");

		Document mStdXml = 
			QueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//农行传ContNo，方从Cont中查出ProposalPrtNo
		Element mBodyEle = mStdXml.getRootElement().getChild(Body);
		String mSqlStr = "select ProposalPrtNo from Cont where Type= " + AblifeCodeDef.ContType_Bank
				+ " and ContNo='" + mBodyEle.getChildText(ContNo) + "'";
		SSRS mSSRS = new ExeSQL().execSQL(mSqlStr);
		if (1 != mSSRS.MaxRow) {
			throw new MidplatException("查询保单信息失败！");
		}
		
		mBodyEle.getChild(ProposalPrtNo).setText(mSSRS.GetText(1, 1));
		
		cLogger.info("Out Query.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Query.std2NoStd()...");

		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);

		cLogger.info("Out Query.std2NoStd()!");
		return mNoStdXml;
	}
}
