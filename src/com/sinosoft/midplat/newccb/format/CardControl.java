//建行重控核对
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class CardControl extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sCardNo = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	
	public CardControl(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into CardControl.getStdXml()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
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
			CardControlInXsl.newInstance().getCache().transform(pNoStdXml);
		
		sCardNo=mStdXml.getRootElement().getChild("Body").getChildText("StartNo");
		sCardNo=sCardNo.substring(0,7);
		if(sCardNo.equals("2104141")){//核对投保单号
			mStdXml.getRootElement().getChild("Body").getChild("CardType").setText("2104141");
		}
		if(sCardNo.equals("0101141")){//核对保单印刷号
			mStdXml.getRootElement().getChild("Body").getChild("CardType").setText("0101141");
		}
		cLogger.info("Out CardControl.getStdXml()!");
		JdomUtil.print(mStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into CardControl.getNoStdXml()...");
		
//		InputStream mSheetIs = getClass().getResourceAsStream("CardControlOut.xsl");
//		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
//		Document pNoStdXml = new XSLTransformer(mSheetIsr).transform(pStdXml);
//		mSheetIsr.close();

//		String mInFilePath2 = "H:/李路/任务/P538191A2InNoStd.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2,"UTF-8");
//		
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();
		
		Document mNoStdXml = 
			CardControlOutXsl.newInstance().getCache().transform(pStdXml);
		
		
		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		JdomUtil.print(pStdXml);
		//当核保成功的时候，返回TX_BODY时，增加COM_ENTITY节点
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		//COM_ENTITY节点加入服务方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		
		//COM_ENTITY节点加入保险公司方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		
		/*Start-组织返回报文头*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-组织返回报文头*/
		
		
		cLogger.info("Out CardControl.std2NoStd()!");
		JdomUtil.print(mNoStdXml);
		return mNoStdXml;
		
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//		
////		String mInFilePath = "H:/李路/模拟建行报文/ccb_CCB000000000111111_P538191A2_134728.xml";
////		String mOutFilePath = "H:/李路/模拟建行报文/P538191A2inSvcStd.xml";
////		String mInFilePath = "H:/李路/任务/P538191A2InNoStd.xml";
////		String mOutFilePath = "H:/李路/任务/P538191A2inSvcStd.xml";
//		String mInFilePath = "E:/986109_16_108_in.xml";
//		String mOutFilePath = "E:/33333.xml";
////		String mInFilePath = "H:/李路/任务/P538191A2inSvcStd返回报文.xml";
////		String mOutFilePath = "H:/李路/任务/P538191A2OutNoStd.xml";
//
//		
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
////		mSheetIsr.close();		
////		Document mOutXmlDoc = new CardControl(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new CardControl(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//		
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//		
//		System.out.println("成功结束！");
//	}
}
