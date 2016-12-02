//建行查询保单历史变动信息
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class QueryContChange extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	public QueryContChange(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into QueryContChange.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
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
		
//		JdomUtil.print(cTransaction_Header);
		
		Document mStdXml = 
			QueryContChangeInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out QueryContChange.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into QueryContChange.std2NoStd()...");	
		
		Document mNoStdXml = 
			QueryContChangeOutXsl.newInstance().getCache().transform(pStdXml);

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
		
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail1").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Rnew_PyF_Dnum").setText("0");
			}	
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail2").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Dvdn_Cnt").setText("0");
			}	
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail3").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("PsvBsn_Dnum").setText("0");
			}	
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail4").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPolcyEff_Rinst_Cnt").setText("0");
			}	
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail5").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPolcy_Cvr_Chg_Cnt").setText("0");
			}	
			if(pStdXml.getRootElement().getChild("Body").getChild("Detail6").getChildren().size()==0){
				mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("SetlOfClms_Rcrd_Num").setText("0");
			}
			
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-组织返回报文头*/

		cLogger.info("Out QueryContChange.std2NoStd()!");
		return mNoStdXml;
	}

//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
////		String mInFilePath = "H:/李路/模拟建行报文/ccb_CCB000000000111111_P53819177_134728.xml";
////		String mOutFilePath = "H:/李路/模拟建行报文/P53819177inSvc.xml";
//		String mInFilePath = "G:/2341439_8_19_outSvc.xml";
//		String mOutFilePath = "G:/P53819177inSvcStd.xml";
////		String mInFilePath = "H:/李路/任务/P53819177InNoStd.xml";
////		String mOutFilePath = "H:/李路/任务/P53819177inSvcStd.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new QueryContChange(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new QueryContChange(null).noStd2Std(mInXmlDoc);
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
