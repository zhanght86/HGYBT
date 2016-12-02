package com.sinosoft.midplat.abc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class Cancel extends XmlSimpFormat {
	public Cancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Cancel.noStd2Std()...");
		 
		Document mStdXml = 
			CancelInXsl.newInstance().getCache().transform(pNoStdXml);
//		Element BodyEle = mStdXml.getRootElement().getChild(Body);
//		Element mContPrtNoEle = BodyEle.getChild(ContPrtNo);
//        String mProposalPrtNo = BodyEle.getChildText(ProposalPrtNo);
//		
//		String sql = "select otherno from tranlog where proposalprtno = '" + mProposalPrtNo +"' and rcode='0' and funcflag='2'";
//		
//		 String mContPrtNo = null;
//		 SSRS ssrs = new SSRS();
//	        ssrs = new ExeSQL().execSQL(sql);
//	        if (ssrs.MaxRow > 0) {
//	        	mContPrtNo = ssrs.GetText(1, 1);
//	        }
//	        if(null == mContPrtNo || "".equals(mContPrtNo)){
//	        	throw new MidplatException("未找到有效的保单，不能做撤单操作！");
//	        }
//	        mContPrtNoEle.setText(mContPrtNo);
	        
	        cLogger.info("Out Cancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Cancel.std2NoStd()...");
		
		Document mNoStdXml = 
			CancelOutXsl.newInstance().getCache().transform(pStdXml);
		
		cLogger.info("Out Cancel.std2NoStd()!");
		return mNoStdXml;
	}
}