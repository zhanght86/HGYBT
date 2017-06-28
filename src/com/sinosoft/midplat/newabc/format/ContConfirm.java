package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


public class ContConfirm extends XmlSimpFormat {
	
	String tXslName;
	private Element header=null;
	private String entrustWay=null;
	private String tranlog=null;

	public ContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");

		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Element mRootEle = pNoStdXml.getRootElement();
		String uwTransId = mRootEle.getChild("App").getChild("Req").getChildText("ApplySerial");
		String cTransDate = mRootEle.getChild("Header").getChildText("TransDate");
		String cTranCom = mRootEle.getChild("Head").getChildText(TranCom);
		cLogger.info("查询===================================!");
		String sqlStr = "select proposalprtno,otherno,logno,Operator from tranlog where trancom='"+cTranCom+"' and rcode='0' and funcflag='1002' and tranno='"+uwTransId+"' and MakeDate='"+cTransDate+"' order by logno desc";
		SSRS ssrs0=new ExeSQL().execSQL(sqlStr);
		cLogger.info("查询结果："+ssrs0.MaxNumber);
		Document mStdXml = ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("赋值===================================!");
		Element proposalPrtNo = mStdXml.getRootElement().getChild("Body").getChild("ProposalPrtNo");
		Element contPrtNo = mStdXml.getRootElement().getChild("Body").getChild("ContPrtNo");
		Element tellerNo = mStdXml.getRootElement().getChild("Head").getChild("TellerNo");
		if(ssrs0.MaxNumber!=0){
			proposalPrtNo.setText(ssrs0.GetText(1, 1));
			contPrtNo.setText(ssrs0.GetText(1, 2));
			tellerNo.setText(ssrs0.GetText(1, 4));
			tranlog=ssrs0.GetText(1, 3);
		}else{
			proposalPrtNo.setText(null);
			contPrtNo.setText(null);
			tellerNo.setText(null);
			tranlog=null;
		}
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.Std2StdnoStd()...");
		
		entrustWay=header.getChildText("EntrustWay");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		
		if(ttFlag.getValue().equals("0"))
			pStdXml =  dealBnf(pStdXml);	
		
		Document mNoStdXml = ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		//为请求业务报文头信息加入返回码和返回信息.把请求的业务报文头加入到返回报文中返回给银行。
		Element  RetCode=new Element("RetCode");
		Element  RetMsg = new Element("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")&&entrustWay.equals("11")){
		   cLogger.info("交易成功=========");
		   RetCode.setText("000000");
		   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Prnts"));
		   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Messages"));
		   dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("TbdPrnts"));
		}
		if(ttFlag.getValue().equals("0") && !entrustWay.equals("11")){
			cLogger.info("交易成功=========");
			RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		Element insuSerial=new Element("InsuSerial");
		insuSerial.setText(tranlog);
		header.addContent(insuSerial);
		header.addContent(RetCode);
		header.addContent(RetMsg);
		mNoStdXml.getRootElement().addContent(header);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out ContConfirm.Std2StdnoStd()!");
		
		return mNoStdXml;
	}
	
	@SuppressWarnings("rawtypes")
	private Document dealBnf(Document InStdDoc){
		List bnfList = InStdDoc.getRootElement().getChild(Body).getChildren(Bnf);
		for(int i = 0;i<bnfList.size();i++){
			Element bnfEle = (Element) bnfList.get(i);
			Element SeqNoELe = new Element("SeqNo");
			SeqNoELe.setText(String.valueOf(i+1));
			bnfEle.addContent(SeqNoELe);
			
			Element SendRiskIDMsgEle = new Element("LendRiskIDMsg");
			SendRiskIDMsgEle.setText(bnfEle.getChildText(Name)+"（证件号码："+bnfEle.getChildText(IDNo)+"）");
			bnfEle.addContent(SendRiskIDMsgEle);
			
		}
		//Risk 节点下新增节点LendRiskDay存放借贷险保险期间（转化为天）
		Element RiskEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk);
		String startDay = RiskEle.getChildText(CValiDate);
		String endDay = RiskEle.getChildText(InsuEndDate);
		
		Element LendRiskDayEle = new Element("LendRiskDay");
		LendRiskDayEle.setText(String.valueOf(DateUtil.compareDay(startDay, endDay)+1));
		RiskEle.addContent(LendRiskDayEle);
		
		return InStdDoc;
	}
	
	@SuppressWarnings("unchecked")
	private void dealPrint(Element mPrnts){
	     List<Element> mPrntList=mPrnts.getChildren("Prnt");
		 Element mCount=mPrnts.getChild("Count");
	     mCount.setText(String.valueOf(mPrntList.size()));
	     System.out.println("Prnt个数："+String.valueOf(mPrntList.size()));
	     int i=1;
		 for(Element e:mPrntList){
			 e.setName("Prnt"+i);
			 i++;
		  }
	}

	public void setHeader(Element pHeader){
		header=pHeader;
	}
	
	public static void main(String[] args) throws Exception {
	System.out.println("程序开始…");

	String mInFilePath = "C:\\Users\\PengYF\\Desktop\\核心承保返回.xml";
	String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
	
	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();
	
//	Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);
	Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("成功结束！");
}
	
}
