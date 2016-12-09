//建行打印投保单
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
import com.sinosoft.utility.ExeSQL;



public class PrintAppCont extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sAginsPkgId = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	public PrintAppCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into PrintAppCont.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
		//服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");	
		sAginsPkgId=pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
		
		//临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");		
		//临时保存银行发起交易日期作为保险公司账务日期 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		JdomUtil.print(cTransaction_Header);
		
		Document mStdXml = 
			PrintAppContInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//处理地址 
//		//投保人
//		String PlchdAddress ="";
//		Element APP_ENTITY  = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
//		String Plchd_Prov_Cd = APP_ENTITY.getChildText("Plchd_Prov_Cd");
//		String Plchd_City_Cd = APP_ENTITY.getChildText("Plchd_City_Cd");
//		String Plchd_CntyAndDstc_Cd = APP_ENTITY.getChildText("Plchd_CntyAndDstc_Cd");
//		String Plchd_Dtl_Adr_Cntnt = APP_ENTITY.getChildText("Plchd_Dtl_Adr_Cntnt");
//		
//		String StrSql1 = "select localText from localcode where localid = '"+Plchd_Prov_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql1).equals(""))&& (new ExeSQL().getOneValue(StrSql1)!=null)){
//			PlchdAddress += new ExeSQL().getOneValue(StrSql1);
//		}
//		
//		String StrSql2 = "select localText from localcode where localid = '"+Plchd_City_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql2).equals(""))&& (new ExeSQL().getOneValue(StrSql2)!=null)){
//			PlchdAddress += new ExeSQL().getOneValue(StrSql2);
//		}
//		
//		String StrSql3 = "select localText from localcode where localid = '"+Plchd_CntyAndDstc_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql3).equals(""))&& (new ExeSQL().getOneValue(StrSql3)!=null)){
//			PlchdAddress += new ExeSQL().getOneValue(StrSql3);
//		}
//		PlchdAddress +=  Plchd_Dtl_Adr_Cntnt;
//		
//		System.out.println("PlchdAddress ========== "+PlchdAddress);
//		
//		//被保人
//		String RcgnAdress = "";
//		String Rcgn_Prov_Cd = APP_ENTITY.getChildText("Rcgn_Prov_Cd");
//		String Rcgn_City_Cd = APP_ENTITY.getChildText("Rcgn_City_Cd");
//		String Rcgn_CntyAndDstc_Cd = APP_ENTITY.getChildText("Rcgn_CntyAndDstc_Cd");
//		String Rcgn_Dtl_Adr_Cntnt = APP_ENTITY.getChildText("Rcgn_Dtl_Adr_Cntnt");
//		
//		String StrSql4 = "select localText from localcode where localid = '"+Rcgn_Prov_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql4).equals(""))&& (new ExeSQL().getOneValue(StrSql4)!=null)){
//			RcgnAdress += new ExeSQL().getOneValue(StrSql4);
//		}
//		
//		String StrSql5 = "select localText from localcode where localid = '"+Rcgn_City_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql5).equals(""))&& (new ExeSQL().getOneValue(StrSql5)!=null)){
//			RcgnAdress += new ExeSQL().getOneValue(StrSql5);
//		}
//		
//		String StrSql6 = "select localText from localcode where localid = '"+Rcgn_CntyAndDstc_Cd+"'";
//		
//		if(!(new ExeSQL().getOneValue(StrSql6).equals(""))&& (new ExeSQL().getOneValue(StrSql6)!=null)){
//			RcgnAdress += new ExeSQL().getOneValue(StrSql6);
//		}
//		RcgnAdress +=  Rcgn_Dtl_Adr_Cntnt;
//		System.out.println("RcgnAdress ========== "+RcgnAdress);
//		
//		
//		mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("Address").setText(PlchdAddress);
//		mStdXml.getRootElement().getChild("Body").getChild("Insured").getChild("Address").setText(RcgnAdress);
//		

		cLogger.info("Out PrintAppCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into PrintAppCont.std2NoStd()...");

//		String mInFilePath2 = "H:/李路/任务/P53819188InNoStd.xml";
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
			PrintAppContOutXsl.newInstance().getCache().transform(pStdXml);

		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
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
	
		//加入保险代理套餐编号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAginsPkgId);
		
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

		cLogger.info("Out PrintAppCont.std2NoStd()!");
		return mNoStdXml;
	}

//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
////		String mInFilePath = "H:/李路/模拟建行报文/ccb_CCB000000000111111_P53819188_134728.xml";
////		String mOutFilePath = "H:/李路/模拟建行报文/P53819188inSvc.xml";
////		String mInFilePath = "H:/李路/任务/P53819188InNoStd.xml";
////		String mOutFilePath = "H:/李路/任务/P53819188inSvcStd.xml";
//		String mInFilePath = "H:/李路/任务/P53819188inSvcStd返回报文.xml";
//		String mOutFilePath = "H:/李路/任务/P53819188OutNoStd.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new PrintAppCont(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new PrintAppCont(null).noStd2Std(mInXmlDoc);
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
