package com.sinosoft.midplat.abc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class RePrint extends XmlSimpFormat {

	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");

		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//农行传ProposalContNo，方从Cont中查出ProposalPrtNo,ContNo
		Element mBodyEle = mStdXml.getRootElement().getChild(Body);
		String mSqlStr = "select ContNo from Cont where Type=0 and ProposalPrtNo ='" + mBodyEle.getChildText("ProposalPrtNo") + "' order by makedate,maketime desc ";
		SSRS mSSRS = new ExeSQL().execSQL(mSqlStr);
		if (1 != mSSRS.MaxRow) {
			throw new MidplatException("查询保单信息失败！");
		}
		
		mBodyEle.getChild(ContNo).setText(mSSRS.GetText(1, 1));

		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");

		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);

		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
}
