//建行修改保单基本信息
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ModifyCont extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sContno = null;
	private String sLv1BrNo = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	
	
	public ModifyCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ModifyCont.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
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
			ModifyContInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out ModifyCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ModifyCont.std2NoStd()...");

//		String mInFilePath2 = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/1053936_245_1040_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2);
//		
//		oldTxHeader = (Element)mInXmlDoc.getRootElement().getChild("TX_HEADER").clone();
//		oldComEntity = (Element)mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
//		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
//		tranNo=mInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
//		tranDate = NewCcbFormatUtil.getTimeAndDate(mInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
//		mIs2.close();		
		
		Document mNoStdXml = 
			ModifyContOutXsl.newInstance().getCache().transform(pStdXml);

		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		JdomUtil.print(mNoStdXml);
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
		
		sContno=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("InsPolcy_No");
		
		if(sContno!=null&&!sContno.equals("")){
			//从cont表中查找对应的投保单号的建行一级分行号，实时投保时已存入备用字段10 bak10
			String getLv1BrNoSQL = new StringBuilder("select bak10 from cont where contno = '").append(sContno).append("' and state='2'").toString();
			sLv1BrNo = new ExeSQL().getOneValue(getLv1BrNoSQL);
			//APP_ENTITY节点加入建行一级分行号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Lv1_Br_No").setText(sLv1BrNo);
		} 
		
		/*Start-组织返回报文头*/

//		Element oldInfo=pStdXml.getRootElement().getChild("Body").getChild("OldInfo");
//		String bankAccNo=oldInfo.getChildText("OldBankAccNo");
//		String appAddress=oldInfo.getChildText("OldAppntAddress");
//		String appZipCode=oldInfo.getChildText("OldAppntZipCode");
//		String appPhone=oldInfo.getChildText("OldAppntPhone");
//		String appEmail=oldInfo.getChildText("OldAppntEmail");
//		String appMobile=oldInfo.getChildText("OldAppntMobile");
//		String modifyContent=bankAccNo+";"+appAddress+";"+appZipCode+";"
//										+appPhone+";"+appEmail+";"+appMobile;
//		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Mod_Cntnt").setText(modifyContent);
		
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

		Element cRoot=pStdXml.getRootElement();
		Element cHead=cRoot.getChild("Head");
		String flag=cHead.getChildText("Flag");
		List<Element> list=null;
		JdomUtil.print(pStdXml);
		if("0".equals(flag)){
		    list=pStdXml.getRootElement().getChild("Body").getChildren("Attachment");
		    JdomUtil.print(mNoStdXml);
		    Element mDetail=mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Detail_List").getChild("Detail");

			List<Element> noList=new ArrayList<Element>();
			int i=1;
			for(Element e:list){
				Element eDate= new Element("Ret_Inf");
//				String text=eDate.getText();
				String text=e.getChild("AttachmentData").getText();
				System.out.println("====="+text);
				eDate.setText("                                                "+text);
				noList.add(eDate);
				i++;
			}
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Detail_List").getChild("Rvl_Rcrd_Num").setText(String.valueOf(noList.size()));
			mDetail.addContent(noList);
		}
		
		
		
		cLogger.info("Out ModifyCont.std2NoStd()!");
		return mNoStdXml;
	}
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
//		String mInFilePath = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/1053936_247_36_outSvc.xml";
//		String mOutFilePath = "H:/李路/杭州中韩人寿/建行接口/UAT业务测试报文/修改保单基本信息out.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new ModifyCont(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new ModifyCont(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("成功结束！");
//
//	}
}