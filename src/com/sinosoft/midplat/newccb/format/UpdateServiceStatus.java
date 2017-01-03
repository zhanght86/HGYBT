package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.midplat.newccb.dao.LKPolicyXMLDao;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class UpdateServiceStatus extends XmlSimpFormat {

	/*非标准报文头*/
	private Element cTransaction_Header = null;
	//服务接受时间
	private String mSYS_RECV_TIME = null;
	//服务响应时间
	private String mSYS_RESP_TIME = null;
	//服务方流水号[TX_BODY/COM_ENTITY/SvPt_Jrnl_No]
	private String tranNo = null;
	//交易日期[TX_HEADER/SYS_REQ_TIME]
	private String tranDate = null;
	//服务名[TX_HEADER/SYS_TX_CODE]
	private String sysTxCode = null;
	//代理保险套餐编号[TX_BODY/APP_ENTITY/AgIns_Pkg_ID]
	private String sAginsPkgId = null;
	//缴费频次[TX_BODY/APP_ENTITY/../InsPrem_PyF_MtdCd]
	private String sPayIntv = null;
	//投保份数[TX_BODY/APP_ENTITY]
	private String sMult = null;
	//受益比例(整数，百分比) [TX_BODY/APP_ENTITY/Bnft_Pct]
	private String sLot = null;
	//
	private String sPayCycCod = null;
	//关系类型
	private String sRelationShip = null;
	//报文头[TX_HEADER]
	private Element oldTxHeader = null;
	//旧公共域[TX_BODY/COM_ENTITY]
	private Element oldComEntity = null;
	//旧应用域[TX_BODY/APP_ENTITY]
	private Element oldAppEntity = null;
	
	/**请求的非标准报文*/
	private Document noStdDoc = null;//非标准报文

	public UpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		// Into UpdateServiceStatus.noStd2Std()...
		cLogger.info("Into UpdateServiceStatus.noStd2Std()...");
		
		//备份非标准报文应用域[TX_BODY/APP_ENTITY]
		oldAppEntity =
			(Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").clone();
		//获取非标准报文代理保险套餐编号[TX_BODY/APP_ENTITY/../AgIns_Pkg_ID]
//		sAginsPkgId	=oldAppEntity.getChild("Busi_List").getChild("Busi_Detail").getChildText("AgIns_Pkg_ID");
		System.out.println(sAginsPkgId);
		
		//备份非标准报文报文头[TX_HEADER]
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		//备份非标准报文公共域[TX_BODY/COM_ENTITY]
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		//获取非标准报文服务名[TX_HEADER/SYS_TX_CODE]
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		//获取非标准报文服务方流水号[TX_BODY/COM_ENTITY/SvPt_Jrnl_No]
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		//保费缴纳周期代码[TX_BODY/APP_ENTITY/../InsPrem_PyF_Cyc_Cd]
//		sPayCycCod =  pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Busi_List").getChild("Busi_Detail").getChildText("InsPrem_PyF_Cyc_Cd");
		//复制非标准报文
		noStdDoc = (Document)pNoStdXml.clone();//获取非标准报文
		// 实时投保输入报文样式实例获取端口-处理类 配置将非标准报文转换为标准报文
		Document mStdXml = UpdateServiceStatusInXsl.newInstance().getCache()
				.transform(pNoStdXml);// [TranData]
		JdomUtil.print(mStdXml);
		cLogger.info(JdomUtil.toStringFmt(mStdXml));
		// Out UpdateServiceStatus.noStd2Std()!
		cLogger.info("Out UpdateServiceStatus.noStd2Std()!");
		// 返回输入标准报文
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into UpdateServiceStatus.std2NoStd()...");
		// 如果试算成功,保存原始保文
		String funcFlag = pStdXml.getRootElement().getChild("RetData")
				.getChildText("Flag");
		if (StringUtils.isNotBlank(funcFlag) && "0".equals(funcFlag)) {
			// 保存非标准报文
			saveNoStdDoc(pStdXml);
		}

//		JdomUtil.print(pStdXml);
		Document mNoStdXml = UpdateServiceStatusOutXsl.newInstance().getCache()
				.transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		
		// 服务响应时间[20161212103228778]
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 设置TX_HEADER的一些节点信息[返回给银行的非标准报文,银行发过来的非标准报文的头节点,服务接受时间(20161212095023139),服务响应时间(20161212103228778)]
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader,
				mSYS_RECV_TIME, mSYS_RESP_TIME);

		// 当核保成功的时候，返回TX_BODY时，增加COM_ENTITY节点
		// String resultCode =
		// pStdXml.getRootElement().getChild("Head").getChildText("Flag");
		// if(resultCode.equals("0")){
		// 返回加入了COM_ENTITY节点的非标准报文[转换后的非标准报文,核心返回的标准报文,body节点下的COM_ENTITY节点,服务名(交易码:P53819113)]
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml,
				oldComEntity, sysTxCode);
		// }
		mNoStdXml=setAppEntity(mNoStdXml);
		
		/* Start-组织返回报文头 */
		// 核心返回的标准报文头
		Element mRetData = pStdXml.getRootElement().getChild("RetData");
		if (mRetData.getChildText(Flag).equals("1")) { // 交易成功[设置转换后的非标准报文头]
			mNoStdXml.getRootElement().getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC")
					.setText(mRetData.getChildText(Desc));
			mNoStdXml
					.getRootElement()
					.getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC_LEN")
					.setText(
							Integer.toString(mRetData.getChildText(Desc)
									.length()));
			// COM_ENTITY节点加入服务方流水号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("SvPt_Jrnl_No")
					.setText(tranNo);
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt")
					.setText(tranDate);

			// COM_ENTITY节点加入保险公司方流水号
			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
					.getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No")
					.setText(tranNo);
			// 加入保险代理套餐编号
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("AgIns_Pkg_ID")
//					.setText(sAginsPkgId);
			// 加入保费缴费方式代码
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("InsPrem_PyF_MtdCd")
//					.setText(sPayIntv);
			// 加入保费缴费周期代码
//			mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY")
//					.getChild("APP_ENTITY").getChild("InsPrem_PyF_Cyc_Cd")
//					.setText(sPayCycCod);
			// 加入投保份数
			// mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Ins_Cps").setText(sMult);

		} else { // 交易失败[设置转换后的非标准报文头错误信息]
//			mNoStdXml.getRootElement().getChild("TX_HEADER")
//					.getChild("SYS_RESP_CODE").setText("ZZZ072000001");// 返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC")
					.setText(mRetData.getChildText("Desc"));// 服务响应描述
			mNoStdXml
					.getRootElement()
					.getChild("TX_HEADER")
					.getChild("SYS_RESP_DESC_LEN")
					.setText(
							Integer.toString(mRetData.getChildText(Desc)
									.length()));// 服务响应描述长度
		}

		/* End-组织返回报文头 */

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}

	/**
	 * 
	 * saveNoStdDoc 标准报文
	 * 
	 * @param stdDoc
	 */
	private void saveNoStdDoc(Document stdDoc) {

		LKPolicyXML policyXML = new LKPolicyXML();

		// 标准报文根节点
		Element stdDocRoot = stdDoc.getRootElement();

		// 设置保单号
		policyXML.setContNo(stdDocRoot.getChild("LCConts").getChildText(ContNo));
		// 投置投保单号
		policyXML.setProposalPrtNo(stdDocRoot.getChild("LCConts").getChildText("ProposalContNo"));

		// 设置银行机构码
		policyXML.setTranCom("03");

		// 设置交易日期
		policyXML.setTranDate(DateUtil.parseDate(tranDate, "yyyyMMdd"));

		// 设置报文信息
		policyXML.setXmlContent(JdomUtil.toBytes(noStdDoc));

		// 设置入机及及修改日期时间
		policyXML.setMakeDate(new Date());
		policyXML.setMakeTime(DateUtil.getCur8Time());
		policyXML.setModifyDate(new Date());
		policyXML.setModifyTime(DateUtil.getCur8Time());

		LKPolicyXMLDao dao = new LKPolicyXMLDao();
		try {
			// 插入保单报文信息
			boolean flag = dao.insert(policyXML);
			if (!flag) {
				cLogger.error("保存非标准报文到数据库失败!");
			} else {
				cLogger.info("保存非标准报文到数据库成功!");
			}
		} catch (Exception e) {
			cLogger.error("保存非标准报文到数据库失败:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Document setAppEntity(Document mNoStdXml){
		Element mRootEle=mNoStdXml.getRootElement();
		Element mAppEntity=mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		List<Element> insuList=mAppEntity.getChild("Insu_List").getChildren("Insu_Detail");
		for (Element e : insuList) {
			if(!e.getChildText("AcIsAR_StCd").equals("076019")){
				e.removeChild("Tot_InsPrem_Amt");
				e.removeChild("Ins_PyF_Amt");
				e.removeChild("PyF_CODt");
			}
		}
		return mNoStdXml;
	}
	
}
