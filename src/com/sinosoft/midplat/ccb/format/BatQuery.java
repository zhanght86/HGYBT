package com.sinosoft.midplat.ccb.format;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatQuery extends XmlSimpFormat {
    public BatQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
		// TODO Auto-generated constructor stub
	}

	private Element cTransaction_Header = null;

    public Document noStd2Std(Document pNoStdXml) throws Exception {
        cLogger.info("Into BatQuery.noStd2Std()...");

        cTransaction_Header = (Element) pNoStdXml.getRootElement().getChild(
                "Transaction_Header").clone();

        InputStream mSheetIs = getClass().getResourceAsStream("BatQueryIn.xsl");
        InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
        Document mStdXml = new XSLTransformer(mSheetIsr).transform(pNoStdXml);
        mSheetIsr.close();

        cLogger.info("Out BatQuery.noStd2Std()!");
        return mStdXml;
    }

    public Document std2NoStd(Document pStdXml) throws Exception {
        cLogger.info("Into BatQuery.std2NoStd()...");

        InputStream mSheetIs = getClass()
                .getResourceAsStream("BatQueryOut.xsl");
        InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
        Document pNoStdXml = new XSLTransformer(mSheetIsr).transform(pStdXml);
        mSheetIsr.close();

        /* Start-组织返回报文头 */
        Element mBkOthDate = new Element("BkOthDate");
        mBkOthDate.setText(DateUtil.getCurDate("yyyyMMdd"));

        Element mBkOthSeq = new Element("BkOthSeq");
        mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

        Element mBkOthRetCode = new Element("BkOthRetCode");
        Element mBkOthRetMsg = new Element("BkOthRetMsg");
        Element mRetData = pStdXml.getRootElement().getChild("Head");
        if (mRetData.getChildText(Flag).equals("0")) { // 交易成功
            mBkOthRetCode.setText("00000");
            mBkOthRetMsg.setText("交易成功！");
        } else { // 交易失败
            mBkOthRetCode.setText("11111");
            mBkOthRetMsg.setText(mRetData.getChildText(Desc));
        }

        Element mTran_Response = new Element("Tran_Response");
        mTran_Response.addContent(mBkOthDate);
        mTran_Response.addContent(mBkOthSeq);
        mTran_Response.addContent(mBkOthRetCode);
        mTran_Response.addContent(mBkOthRetMsg);

        cTransaction_Header.addContent(mTran_Response);
        
        pNoStdXml.getRootElement().addContent(0, cTransaction_Header);
        /* End-组织返回报文头 */

        cLogger.info("Out BatQuery.std2NoStd()!");
        return pNoStdXml;
    }
}
