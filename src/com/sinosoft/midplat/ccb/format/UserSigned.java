package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class UserSigned extends XmlSimpFormat {
    public UserSigned(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	private Element cTransaction_Header = null;

    public Document noStd2Std(Document pNoStdXml) throws Exception {
        cLogger.info("Into UserSigned.noStd2Std()...");

        cTransaction_Header = (Element) pNoStdXml.getRootElement().getChild(
                "Transaction_Header").clone();

        InputStream mSheetIs = getClass().getResourceAsStream(
                "UserSignedIn.xsl");
        InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
        Document mStdXml = new XSLTransformer(mSheetIsr).transform(pNoStdXml);
        mSheetIsr.close();

        cLogger.info("Out UserSigned.noStd2Std()!");
        return mStdXml;
    }

    public Document std2NoStd(Document pStdXml) throws Exception {
        cLogger.info("Into UserSigned.std2NoStd()...");

        InputStream mSheetIs = getClass().getResourceAsStream(
                "UserSignedOut.xsl");
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
            List tDetail_List = pNoStdXml.getRootElement().getChild("Transaction_Body").getChildren("Detail_List");
    		for (int i=0;i<tDetail_List.size();i++){
    			Element ttDetail_List = (Element)tDetail_List.get(i);
    			List tDetail = ttDetail_List.getChildren("Detail");
    			for (int j=0;j<tDetail.size();j++){
    				Element ttDetail = (Element)tDetail.get(j);
    				List tBkDetail1 = ttDetail.getChildren("BkDetail1");
    				ttDetail_List.getChild("BkRecNum").setText(String.valueOf(tBkDetail1.size()));
    			}
    		}
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
//        JdomUtil.print(pNoStdXml);
        pNoStdXml.getRootElement().addContent(cTransaction_Header);
        /* End-组织返回报文头 */

        cLogger.info("Out UserSigned.std2NoStd()!");
        return pNoStdXml;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("程序开始…");

        String mInFilePath = "E:/ccb_SPE010.xml";
        String mOutFilePath = "E:/ccb_28.xml";

        InputStream mIs = new FileInputStream(mInFilePath);
        Document mInXmlDoc = JdomUtil.build(mIs);
        mIs.close();

        Document mOutXmlDoc = new UserSigned(null).noStd2Std(mInXmlDoc);

        JdomUtil.print(mOutXmlDoc);

        OutputStream mOs = new FileOutputStream(mOutFilePath);
        JdomUtil.output(mOutXmlDoc, mOs);
        mOs.flush();
        mOs.close();

        System.out.println("成功结束！");
    }
}
