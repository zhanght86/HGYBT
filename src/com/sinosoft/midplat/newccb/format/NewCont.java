//建行试算交易
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class NewCont extends XmlSimpFormat {
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private String sAginsPkgId = null;
	private String sPayIntv = null;
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
		cLogger.info("Into NewCont.noStd2Std()...");
		
		//此处备份一下请求报文体APP_EITITY相关信息，组织返回报文时会用到
		oldAppEntity =
			(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();;
		
		sAginsPkgId	=oldAppEntity.getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
		
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
		
		System.out.println("================"+pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("Plchd_And_Rcgn_ReTpCd"));
		System.out.println("================"+sRelationShip);
		if("01".equals(sRelationShip)){
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("03");
		}
		if("03".equals(sRelationShip)){
			mStdXml.getRootElement().getChild("Body").getChild("Appnt").getChild("RelaToInsured").setText("01");
		}
		System.out.println("++++++++++++++++"+sRelationShip);
		
		//中韩附加定盈宝两全保险（万能型）保障类型随主险
		//主险
		List mRiskList = mStdXml.getRootElement().getChild("Body").getChildren("Risk");
		String mInsuYearFlag = "";
		String mInsuYear = "";
		for(int i=0;i<mRiskList.size();i++){
			Element mRiskEle = (Element) mRiskList.get(i);
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			if("131204".equals(mRiskCode) || "131205".equals(mRiskCode) || "131301".equals(mRiskCode) || "131302".equals(mRiskCode) ||
					"221206".equals(mRiskCode) || "001301".equals(mRiskCode) || "231302".equals(mRiskCode)){
				mInsuYearFlag = mRiskEle.getChildText("InsuYearFlag");
				mInsuYear = mRiskEle.getChildText("InsuYear");
			}
		}
		
		for(int i=0;i<mRiskList.size();i++){
			Element mRiskEle = (Element) mRiskList.get(i);
			String mRiskCode = mRiskEle.getChildText("RiskCode");
			if("145201".equals(mRiskCode)){
				mRiskEle.getChild("InsuYearFlag").setText(mInsuYearFlag);
				mRiskEle.getChild("InsuYear").setText(mInsuYear);
			}
		}
			
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
		//服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		//当核保成功的时候，返回TX_BODY时，增加COM_ENTITY节点
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		/*Start-组织返回报文头*/
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
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

			
			
		} else {	//交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
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
	
	/**
	 *  网销渠道生成投保单号
	 * @param tSaleChnl
	 * @return
	 * @throws Exception
	 */
	
	public static String dealPrtNo() throws Exception {
	    int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        //格式化一个整数,位数不够向前补0(整数只能长度为10位)
        String tRomNo = String.format("%010d", hashCodeV);
        String tPrtNo="10199"+tRomNo.substring(2);
        return tPrtNo;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\ccb\\新单投保.xml";
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		JdomUtil.print(mInXmlDoc);
		Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
//		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		System.out.println("成功结束！");

	}
}