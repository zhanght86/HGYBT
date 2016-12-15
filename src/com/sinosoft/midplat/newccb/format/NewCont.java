//建行试算交易
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;

public class NewCont extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private String sAginsPkgId = null;
	private String sPayIntv = null;
	private String sMult = null;
	private String sLot = null;
	private String sPayCycCod = null;
	private String sRelationShip = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private Element oldAppEntity = null;
	
	/**请求的非标准报文*/
	private Document noStdDoc = null;
	
	
	public NewCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		//Into NewCont.noStd2Std()...
		cLogger.info("Into NewCont.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		//此处备份一下请求报文体APP_EITITY相关信息，组织返回报文时会用到
		oldAppEntity =
			(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();;
		
		sAginsPkgId	=oldAppEntity.getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
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
		//保费缴费方式代码
		sPayIntv =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_MtdCd");
		//投保份数
	//	sMult =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("Ins_Cps");
		//受益比例 lilu20150305 业务测试银行收益比例100%发1.0000，所以要转成double*100后取整发给核心，核心Lot为100以内整数
		
		if(!"0".equals(pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Benf_Num"))){
			List<Element> tBnf=pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Benf_List").getChildren("Benf_Detail");
			for (int i=0 ; i<tBnf.size() ; i++) {
				sLot =  tBnf.get(i).getChildText("Bnft_Pct");
				double dLot=Double.parseDouble(sLot);
				int iLot=(int) (dLot*100);
				sLot = String.valueOf(iLot);
				tBnf.get(i).getChild("Bnft_Pct").setText(sLot);
			}
		}
		//保费缴纳周期代码
		sPayCycCod =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_Cyc_Cd");
		
		//复制非标准报文
		noStdDoc = (Document)pNoStdXml.clone();
		
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		//lilu20150305处理关系代码，建行发被保人是投保人的关系，核心要投保人是被保人的关系，目前核心只有00本人，01父母，02配偶，03子女，04祖孙，05监护，06其他，07保单服务人员
		sRelationShip = mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChildText("RelaToInsured");
		//================0133043
		System.out.println("================"+pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Plchd_And_Rcgn_ReTpCd"));
		//================00
		System.out.println("================"+sRelationShip);
		//关系类型为父母
		if("01".equals(sRelationShip)){
			//投保人被保人关系为子女
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("03");
		}
		//关系类型为子女
		if("03".equals(sRelationShip)){
			//投保人被保人关系为父母
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("01");
		}
		//++++++++++++++++00
		System.out.println("++++++++++++++++"+sRelationShip);//本人
		
		//中韩附加定盈宝两全保险（万能型）保障类型随主险
		//主险
		//险种[可多个] 
		List mRiskList = mStdXml.getRootElement().getChild("Body").getChildren("Risk");
		//定义保险年期年龄标志
		String mInsuYearFlag = "";
		//定义保险年期年龄
		String mInsuYear = "";
		//遍历险种
		for(int i=0;i<mRiskList.size();i++){
			//获取当前险种元素
			Element mRiskEle = (Element) mRiskList.get(i);
			//
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			//险种代码为131204、131204、131301、131302、221206、001301、231302
			if("131204".equals(mRiskCode) || "131205".equals(mRiskCode) || "131301".equals(mRiskCode) || "131302".equals(mRiskCode) ||
					"221206".equals(mRiskCode) || "001301".equals(mRiskCode) || "231302".equals(mRiskCode)){
				//获取保险年期年龄标志
				mInsuYearFlag = mRiskEle.getChildText("InsuYearFlag");
				//获取保险年期年龄
				mInsuYear = mRiskEle.getChildText("InsuYear");
			}
		}
		//遍历险种[二次]
		for(int i=0;i<mRiskList.size();i++){
			//获取当前险种元素
			Element mRiskEle = (Element) mRiskList.get(i);
			//获取险种代码
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			//获取险种代码为145201
			if("145201".equals(mRiskCode)){
				//设置保险年期年龄标志为""
				mRiskEle.getChild("InsuYearFlag").setText(mInsuYearFlag);
				//设置保险年期年龄为""
				mRiskEle.getChild("InsuYear").setText(mInsuYear);
			}
		}
		//Out NewCont.noStd2Std()!
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");

		//如果试算成功,保存原始保文
		String funcFlag = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
		if(StringUtils.isNotBlank(funcFlag) && "0".equals(funcFlag))
		{
			//保存非标准报文
			saveNoStdDoc(pStdXml);
		}
		
		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		
		//服务响应时间[20161212103228778]
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//设置TX_HEADER的一些节点信息[返回给银行的非标准报文,银行发过来的非标准报文的头节点,服务接受时间(20161212095023139),服务响应时间(20161212103228778)]
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//当核保成功的时候，返回TX_BODY时，增加COM_ENTITY节点
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
		//返回加入了COM_ENTITY节点的非标准报文[转换后的非标准报文,核心返回的标准报文,body节点下的COM_ENTITY节点,服务名(交易码:P53819113)]
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		
		/*Start-组织返回报文头*/
		//核心返回的标准报文头
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功[设置转换后的非标准报文头]
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText(Desc));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
			//COM_ENTITY节点加入服务方流水号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
			
			//COM_ENTITY节点加入保险公司方流水号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
			//加入保险代理套餐编号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("AgIns_Pkg_ID").setText(sAginsPkgId);
			//加入保费缴费方式代码
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPrem_PyF_MtdCd").setText(sPayIntv);
			//加入保费缴费周期代码
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("InsPrem_PyF_Cyc_Cd").setText(sPayCycCod);
			//加入投保份数
		//	mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_Cps").setText(sMult);

			
			
		} else {	//交易失败[设置转换后的非标准报文头错误信息]
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));//服务响应描述
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));//服务响应描述长度
		}
		
		/*End-组织返回报文头*/

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	/**
	 * 
	 * saveNoStdDoc 标准报文
	 *
	 * @param stdDoc
	 */
	private void saveNoStdDoc(Document stdDoc)
	{

		LKPolicyXML policyXML = new LKPolicyXML();
		
		//标准报文根节点
		Element stdDocRoot = stdDoc.getRootElement();
		
		
		//设置保单号
		policyXML.setContNo(stdDocRoot.getChild(Body).getChildText(ContNo));
		//投置投保单号
		policyXML.setProposalPrtNo(stdDocRoot.getChild(Body).getChildText(ProposalPrtNo));
		
		//设置银行机构码
		policyXML.setTranCom("03");
		
		//设置交易日期
		policyXML.setTranDate(DateUtil.parseDate(tranDate, "yyyyMMdd"));
		
		//设置报文信息
		policyXML.setXmlContent(JdomUtil.toBytes(noStdDoc));
		
		//设置入机及及修改日期时间
		policyXML.setMakeDate(new Date());
		policyXML.setMakeTime(DateUtil.getCur8Time());
		policyXML.setModifyDate(new Date());
		policyXML.setModifyTime(DateUtil.getCur8Time());

		LKPolicyXMLDao dao = new LKPolicyXMLDao();
		try
		{
			//插入保单报文信息
			boolean flag = dao.insert(policyXML);
			if(!flag)
			{
				cLogger.error("保存非标准报文到数据库失败!");
			}
			else
			{
				cLogger.info("保存非标准报文到数据库成功!");
			}
		}
		catch (Exception e)
		{
			cLogger.error("保存非标准报文到数据库失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");


		String mInFilePath = "C:/Users/liuzk/Desktop/1380526_39_0_outSvc.xml";
		String mOutFilePath = "C:/Users/liuzk/Desktop/22.xml";



		Document mInXmlDoc = JdomUtil.build(mInFilePath);
		
//		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
//
		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");

	}
}