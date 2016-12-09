//建行发送银行端单证信息
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
import com.sinosoft.utility.SSRS;

public class SendDocument extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	
	
	public SendDocument(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into SendDocument.noStd2Std()...");
		
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
			SendDocumentInXsl.newInstance().getCache().transform(pNoStdXml);
		
		//取标准所有Detail里的BusiType
		List<Element> tDetail = XPath.selectNodes(mStdXml.getRootElement(), "/TranData/Body/Detail");
		
		//取请求所有Detail里的交易流水号RqPtTcNum
		List<Element> tDetail2 = XPath.selectNodes(pNoStdXml.getRootElement(), "//TX/TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail");
		//用交易流水号从Tranlog表中查找对应的单证号
		SSRS tSSRS= new SSRS();
		ExeSQL  tExeSQL = new ExeSQL();
		for(int i=0;i<tDetail2.size();i++){ 
			String  tranno  =  tDetail2.get(i).getChildText("RqPtTcNum");
			//用交易流水号从Tranlog表中查找对应的投保单号
			String getProposalprtNoSQL = new StringBuilder("select proposalprtno from tranlog where tranno = '").append(tranno).append("'")
			.append(" and rcode='0'").toString();
			String proposalprtno = new ExeSQL().getOneValue(getProposalprtNoSQL);
			String getContNoSQL = new StringBuilder("select contno,state from cont where proposalprtno = '").append(proposalprtno).append("'").toString();
			tSSRS=tExeSQL.execSQL(getContNoSQL);
			for (int j = 1;j<=tSSRS.getMaxRow();j++) {
				tDetail.get(i).getChild("OtherNo").setText(tSSRS.GetText(1,1));
				tDetail.get(i).getChild("CardState").setText(tSSRS.GetText(1,2));
			}
//			tDetail.get(i).getChild("OtherNo").setText(new ExeSQL().execSQL(getContNoSQL).GetText(1,1));
//			tDetail.get(i).getChild("CardState").setText(new ExeSQL().execSQL(getContNoSQL).GetText(1,2));
		}
		
		cLogger.info("Out SendDocument.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into SendDocument.std2NoStd()...");

		Document mNoStdXml = 
			SendDocumentOutXsl.newInstance().getCache().transform(pStdXml);

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
		
		/*Start-组织返回报文头*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
		
		/*End-组织返回报文头*/

		cLogger.info("Out SendDocument.std2NoStd()!");
		return mNoStdXml;
	}

}