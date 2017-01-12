//建行新契约交易
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
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class ContConfirm extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	String tXslName=null;
	 
	public ContConfirm(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		
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
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);

		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	
	/**
	 * 供重打和查询使用。	
	 */
	void setHeader(Element pTransaction_Header) {
		cTransaction_Header = pTransaction_Header;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
		if (ttFlag.getValue().equals("0")){
			   //处理标准输入报文受益人
			   pStdXml =  dealBnf(pStdXml);	
			//
			if("221301".equals(tRiskCode.getValue())){
				//处理标准输入报文现金价值表
			   pStdXml =  dealCashValues2(pStdXml);
			}else if("231302".equals(tRiskCode.getValue())){//20140918 新增永利年年分红险 
				tXslName="ContConfirmOut_PensionCash";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else if("241201".equals(tRiskCode.getValue())){ //中韩创赢财富两全保险（万能型）A款
				tXslName="ContConfirmOut_AlmightyRisk";
			}else{
				pStdXml =  dealCashValues(pStdXml);
			}
		}	
		
		System.out.println("使用的样式表："+tXslName);
		Document mNoStdXml =  
			ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
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
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-组织返回报文头*/
		
		
		if(pStdXml.getRootElement().getChild("Head").getChildText(Flag).equals("0")){
			List tDetail_List = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildren("Detail_List");
			for (int i=0;i<tDetail_List.size();i++){
				Element ttDetail_List = (Element)tDetail_List.get(i);
				List tDetail = ttDetail_List.getChildren("Detail");
				for (int j=0;j<tDetail.size();j++){
					Element ttDetail = (Element)tDetail.get(j);
					List Ret_Inf = ttDetail.getChildren("Ret_Inf");
					ttDetail_List.getChild("Rvl_Rcrd_Num").setText(String.valueOf(Ret_Inf.size()));
				}
			}
		}

			cLogger.info("Out ContConfirm.std2NoStd()!");
			
			return mNoStdXml;
		}
	public Document std2NoStd(Document pStdXml,String funcflag) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tRiskCode  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/RiskCode");
		if (ttFlag.getValue().equals("0")){
			//标准输入报文加入序列号节点
			pStdXml =  dealBnf(pStdXml);	
			//险种代码为221301
			if("221301".equals(tRiskCode.getValue())){
				//
				pStdXml =  dealCashValues2(pStdXml);
			}if("231302".equals(tRiskCode.getValue())){//20140918 新增永利年年分红险 
				tXslName="ContConfirmOut_PensionCash";
				Element tGetYearEle  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/Risk/GetYear");
				tGetYearEle.setText(tGetYearEle.getText()+"岁");
				
				pStdXml =  dealPensionCashValues(pStdXml);	
				pStdXml =  dealDeValuesCopyToCashCalues(pStdXml);
			}else{
				pStdXml =  dealCashValues(pStdXml);
			}
		}
			
		
		System.out.println("使用的样式表："+tXslName);
		Document mNoStdXml =  
			ContConfirmOutXsl.newInstance(tXslName).getCache().transform(pStdXml);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out ContConfirm.std2NoStd()!");
		
		return mNoStdXml;
	}
		
		/**
		 * @Title: dealBnf
		 * @Description: 处理标准输入报文受益人
		 * @param InStdDoc
		 * @return 返回加入序列号节点后的标准输入报文
		 * @return Document 返回加入序列号节点后的标准输入报文
		 * @throws 异常
		 */
		private Document dealBnf(Document InStdDoc){
			//标准输入报文受益人列表
			List bnfList = InStdDoc.getRootElement().getChild(Body).getChildren(Bnf);
			//遍历受益人列表
			for(int i = 0;i<bnfList.size();i++){
				//获取当前受益人
				Element bnfEle = (Element) bnfList.get(i);
				//序列号节点
				Element SeqNoELe = new Element("SeqNo");
				//设置序列号节点文本内容为[下标+1:受益人顺序]
				SeqNoELe.setText(String.valueOf(i+1));
				//受益人节点加入序列号子节点
				bnfEle.addContent(SeqNoELe);
			}
			//返回加入序列号节点后的标准输入报文
			return InStdDoc;
		}
		

		private Document dealCashValues (Document InStdDoc){
			Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
			List cashValuesList = CashValuesEle.getChildren(CashValue);
			int cashValuesListSize = cashValuesList.size();
			cLogger.info("cashValuesListSize："+cashValuesListSize);
			if(cashValuesListSize<25){
				for(int i=0 ;i<25-cashValuesListSize;i++){
					Element cashValueELe = new Element(CashValue);
					Element EndYearEle = new Element("EndYear");
					EndYearEle.setText(String.valueOf(cashValuesListSize+i+1));
					Element CashEle = new Element("Cash");
					CashEle.setText("-");
					
					cashValueELe.addContent(EndYearEle);
					cashValueELe.addContent(CashEle);
					
					CashValuesEle.addContent(cashValueELe);
				}
			}

			JdomUtil.print(InStdDoc);
			return InStdDoc;
	}
		
		/**
		 * @Title: dealCashValues2
		 * @Description: 处理现金价值表2
		 * @param InStdDoc 标准输入报文
		 * @return 返回现金价值表加入结束年节点、现金节点后的标准输入报文
		 * @return Document 返回现金价值表加入结束年节点、现金节点后的标准输入报文
		 * @throws 异常
		 */
		private Document dealCashValues2 (Document InStdDoc){
			//现金价值表节点
			Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
			//现金价值表列表
			List cashValuesList = CashValuesEle.getChildren(CashValue);
			//现金价值表列表元素个数
			int cashValuesListSize = cashValuesList.size();
			//cashValuesListSize：元素个数
			cLogger.info("cashValuesListSize："+cashValuesListSize);
			//元素个数少于33
			if(cashValuesListSize<33){
				//遍历现金价值表列表
				for(int i=0 ;i<33-cashValuesListSize;i++){
					//现金价值表节点
					Element cashValueELe = new Element(CashValue);
					//结束年节点
					Element EndYearEle = new Element("EndYear");
					//设置结束年节点文本内容为元素个数与下标之和
					EndYearEle.setText(String.valueOf(cashValuesListSize+i));
					//现金节点
					Element CashEle = new Element("Cash");
					//文本内容为-
					CashEle.setText("-");
					
					//<CashValue><EndYear>1</EndYear></CashValue>
					cashValueELe.addContent(EndYearEle);
					//<CashValue><EndYear>1</EndYear><Cash>-</Cash></CashValue>
					cashValueELe.addContent(CashEle);
					//<CashValues><CashValue><EndYear>1</EndYear><Cash>-</Cash></CashValue></CashValues>
					CashValuesEle.addContent(cashValueELe);
				}
			}
			//将标准输入报文打印到控制台[GBK编码，缩进3空格]
			JdomUtil.print(InStdDoc);
			//返回现金价值表加入结束年节点、现金节点后的标准输入报文
			return InStdDoc;
		}
		
		private Document dealPensionCashValues (Document InStdDoc){
			Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
			List cashValuesList = CashValuesEle.getChildren(CashValue);
			int cashValuesListSize = cashValuesList.size();
			cLogger.info("cashValuesListSize："+cashValuesListSize);
			if(cashValuesListSize<35){
				for(int i=0 ;i<35-cashValuesListSize;i++){
					Element cashValueELe = new Element(CashValue);
					Element EndYearEle = new Element("EndYear");
					EndYearEle.setText(String.valueOf(cashValuesListSize+i));
					Element CashEle = new Element("Cash");
					CashEle.setText("-");
					
					cashValueELe.addContent(EndYearEle);
					cashValueELe.addContent(CashEle);
					
					CashValuesEle.addContent(cashValueELe);
				}
			}

//			JdomUtil.print(InStdDoc);
			return InStdDoc;
		}

		private Document dealDeValuesCopyToCashCalues (Document InStdDoc){
			/*减额交清表*/
			Element DeductionValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild("DeductionValues");
			List deductionValueList = DeductionValuesEle.getChildren("DeductionValue");
			int deductionValueListSize = deductionValueList.size();
			
			/*现金价值*/
			Element CashValuesEle = InStdDoc.getRootElement().getChild(Body).getChild(Risk).getChild(CashValues);
			List cashValuesList = CashValuesEle.getChildren(CashValue);
			int cashValuesListSize = cashValuesList.size();
			cLogger.info("dealPensionCashValues.cashValuesListSize："+cashValuesListSize);
			int i=0;
			int j=0;
			//将减额交清的金额放到现金价值标签里
			for(i=0;i<cashValuesListSize;i++){
				Element CashValue=(Element)cashValuesList.get(i);
				for(j=0;j<deductionValueListSize;j++){
					Element valuesEle=(Element)deductionValueList.get(j);
					if(CashValue.getChildTextTrim("EndYear").equals(valuesEle.getChildTextTrim("EndYear"))){
						Element copyEndYearAmnt=new Element("EndYearAmnt");
						copyEndYearAmnt.setText(valuesEle.getChildText("EndYearAmnt"));
						CashValue.addContent(copyEndYearAmnt);
					}
				}
			}
			
			JdomUtil.print(InStdDoc);
			return InStdDoc;
		}
		public static void main(String[] args) throws Exception {
			System.out.println("程序开始…");

			String mInFilePath = "C:/Users/liuzk/Desktop/11.xml";
			String mOutFilePath = "C:/Users/liuzk/Desktop/13.xml";

			InputStream mIs = new FileInputStream(mInFilePath);
			Document mInXmlDoc = JdomUtil.build(mIs);
			mIs.close();

			Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);

			JdomUtil.print(mOutXmlDoc);

			OutputStream mOs = new FileOutputStream(mOutFilePath);
			JdomUtil.output(mOutXmlDoc, mOs);
			mOs.flush();
			mOs.close();

			System.out.println("成功结束！");
		}
}
