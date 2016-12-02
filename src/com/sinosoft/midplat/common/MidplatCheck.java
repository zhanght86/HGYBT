package com.sinosoft.midplat.common;

/**
 * <p>Title:        网点信息校验</p>
 * <p>Description:  检验网点是状态是否有效 </p>
 * <p>Copyright:    Copyright (c) 2006.08.26</p>
 * <p>Company:      公司名称picc</p>
 * @author
 * @version         1.0
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.AxaAgentDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LKTransAuthorizationDB;
import com.sinosoft.lis.db.LMRiskMapDB;
import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.schema.LKTransAuthorizationSchema;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.LKTransAuthorizationSet;
//import com.sinosoft.lis.ybt.common.ControlUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * @author Administrator
 */
public class MidplatCheck implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(MidplatCheck.class);

	public MidplatCheck() {

	}

	/**
	 * @DESC 根据LMRiskMap表里的网点的产品起售和停售时间对网点销售权限校验
	 * @return boolean create by fzg 2012-2-14
	 */
	public static void NodeTimeCheck(Element mRootEle) throws MidplatException {
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		// Element mRiskEle = (Element) mBodyEle.getChild(Risk);//险种不唯一，有主险和附加险

		String mTranCom = mHeadEle.getChildText(TranCom);
		String mZoneNo = mHeadEle.getChildText("ZoneNo");
		String mNodeNo = mHeadEle.getChildText(NodeNo);
		String mTranDate = mHeadEle.getChildText(TranDate);
		/**
		 * 由于数据类型的原因，导致银行代码(例如011,012)之前的0丢失，
		 * 这样在查询表LMRiskMap时出现用'12'去匹配，而不是正确的'012'， 所以需要在此处给mTranCom赋新的值。
		 */

		String mSqlStr = new StringBuilder(
				"select ManageCom from NodeMap where Type=").append("0")
				.append(" and TranCom='").append(mTranCom).append('\'')
				.append(" and ZoneNo='").append(mZoneNo).append('\'')
				.append(" and NodeNo='").append(mNodeNo).append('\'')
				.toString();
		String mManageCom = new ExeSQL().getOneValue(mSqlStr);
		if (mManageCom == "") {
			throw new MidplatException("该网点目前没有产品的销售权限！");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date tranDate = null;
		try {
			tranDate = dateFormat.parse(mTranDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 分险种判断是否有销售权限
		List<Element> mRisks = new ArrayList<Element>();
		mRisks = mBodyEle.getChildren(Risk);

		for (int i = 0; i < mRisks.size(); i++) {
			Element Risk = (Element) mRisks.get(i);
			String mRiskCode = Risk.getChildText(RiskCode);
			String mMainRiskCode = Risk.getChildText(MainRiskCode);
			String mTranRiskCode = Risk.getChildText("TranRiskCode");
			if(!mRiskCode.equals(mMainRiskCode)){
				return;
			}
			LMRiskMapDB lmRiskMapDB = new LMRiskMapDB();
			lmRiskMapDB.setCodeType("risk_com");
			lmRiskMapDB.setComCode(mManageCom);
			lmRiskMapDB.setBankCode(mTranCom);
			lmRiskMapDB.setInsuCode(mTranRiskCode);

			// 产品销售权限没设置给通过
			if (lmRiskMapDB.getInfo()) {
				String startD = lmRiskMapDB.getStartDate();
				String endD = lmRiskMapDB.getEndDate();
				Date startDate = null;
				Date endDate = null;
				try {
					startDate = dateFormat.parse(startD);// String
															// --->java.util.Date
					endDate = dateFormat.parse(endD);
				} catch (Exception e) {
					e.printStackTrace();
					throw new MidplatException(e.getMessage());
				}
				if (tranDate.before(startDate)) {
					throw new MidplatException("该网点的产品" + mRiskCode
							+ "还没到出售日期！");
				}
				if (tranDate.after(endDate)) {
					throw new MidplatException("该网点的产品" + mRiskCode
							+ "销售日期已经结束！");
				}
			}

		}
	}

	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 *             create by zhj 2010 11 05 网点 权限 添加校验方法
	 */
	public static Document NodeMapCheck(Document cInXmlDoc)
			throws MidplatException {
		Document mInXmlDoc = cInXmlDoc;
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		Element mAgentComName = mHeadEle.getChild("AgentComName");
		Element mAgentName = mHeadEle.getChild("AgentName");
		Element mManageCom = mHeadEle.getChild("ManageCom");
		Element mUnitCode = mHeadEle.getChild("UnitCode");
		Element mAgentGrade = mHeadEle.getChild("AgentGrade");
		Element mAgentCodeGrade = mHeadEle.getChild("AgentCodeGrade");
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sFuncFlag = (String) mHeadEle.getChildTextTrim("FuncFlag");
		String mRiskEle = "";
		List mRiskList = mBodyEle.getChildren(Risk);

		String mSqlStr = new StringBuilder(
				"select MapNo from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(sTranCom)
				.append(" and ZoneNo='").append(sZoneNo).append('\'')
				.append(" and NodeNo='").append(sNodeNo).append('\'')
				.toString();
		String sMapNo = new ExeSQL().getOneValue(mSqlStr);
		if(sMapNo==null || "".equals(sMapNo) || "0".equals(sMapNo)){
			throw new MidplatException("该网点不存在，请确认！");
		}
		NodeMapDB aNodeMapDB = new NodeMapDB();
		aNodeMapDB.setMapNo(sMapNo);
		aNodeMapDB.setTranCom(sTranCom);
		aNodeMapDB.setZoneNo(sZoneNo);
		aNodeMapDB.setNodeNo(sNodeNo);
		long mStartMillis = System.currentTimeMillis();
		if (!aNodeMapDB.getInfo()) {
			throw new MidplatException("该网点不存在，请确认！");
		}

		mAgentCom.setText(aNodeMapDB.getAgentCom());
		mAgentCode.setText(aNodeMapDB.getAgentCode());
		mUnitCode.setText(aNodeMapDB.getUnitCode());
		mAgentGrade.setText(aNodeMapDB.getAgentGrade());
		mManageCom.setText(aNodeMapDB.getManageCom());
		mAgentComName.setText(aNodeMapDB.getAgentComName());		
		
		AxaAgentDB aAxaAgentDB = new AxaAgentDB();
		aAxaAgentDB.setAgentCode(aNodeMapDB.getAgentCode());
		aAxaAgentDB.setManageCom(aNodeMapDB.getManageCom());
		mStartMillis = System.currentTimeMillis();
		if (!aAxaAgentDB.getInfo()) {
			throw new MidplatException("此网点所属网点专员不存在，请确认！");
		}
		
		String sAgentCodeGrade = aNodeMapDB.getAgentCodeGrade();
		if (sAgentCodeGrade.length() == 1) {
			sAgentCodeGrade = "0" + sAgentCodeGrade;
		}
		mAgentName.setText(aAxaAgentDB.getAgentName());
		mAgentCodeGrade.setText(sAgentCodeGrade);
		return mInXmlDoc;
	}

	public static void NodeMapSpecCheck(Document cInXmlDoc)
			throws MidplatException {
		Document mInXmlDoc = cInXmlDoc;
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sManageCom= (String) mHeadEle.getChildTextTrim("ManageCom");

		List mRiskList = mBodyEle.getChildren(Risk);

		String mSqlStr = new StringBuilder(
				"select MapNo from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(sTranCom)
				.append(" and ZoneNo='").append(sZoneNo).append('\'')
				.append(" and NodeNo='").append(sNodeNo).append('\'')
				.toString();
		String sMapNo = new ExeSQL().getOneValue(mSqlStr);
		if(sMapNo==null || "".equals(sMapNo) || "0".equals(sMapNo)){
			throw new MidplatException("该网点不存在，请确认！");
		}
		NodeMapDB aNodeMapDB = new NodeMapDB();
		aNodeMapDB.setMapNo(sMapNo);
		aNodeMapDB.setTranCom(sTranCom);
		aNodeMapDB.setZoneNo(sZoneNo);
		aNodeMapDB.setNodeNo(sNodeNo);
		long mStartMillis = System.currentTimeMillis();
		if (!aNodeMapDB.getInfo()) {
			throw new MidplatException("该网点不存在，请确认！");
		}
		
		ControlUtil.sqlExecTime("NodeMapDB.getInfo()", mStartMillis);
		String state = aNodeMapDB.getState();
		if (state == null) {
			state = "";
		}
		String saleFlag = aNodeMapDB.getSaleFlag();
		if (saleFlag == null) {
			saleFlag = "";
		}
		boolean ifAccFlag = false;
		if (MidplatUtil.ifAccRiskCheck(mRiskList, sTranCom)) {
			ifAccFlag = MidplatUtil.ifAccRisk(mRiskList);
		}
		System.out.println("投连险标志:" + ifAccFlag);
		
		LDComDB cLDComDB = new LDComDB();
		cLDComDB.setComCode(sManageCom);
		if(!cLDComDB.getInfo()){
			throw new MidplatException("未找到相关管理机构,请确认");
		} 
		if(cLDComDB.getTellerFlag()!= null && !"".equals(cLDComDB.getTellerFlag())){
			cLogger.debug("资质校验开始");
			
			if ("T".equals(state)) {// 系统状态为T
				throw new MidplatException("该网点销售资格已经终止，银行网点销售资格校验失败，不允许实时出单!");
			}
			
			CheckNodeQu(aNodeMapDB);
			
			if ("".equals(saleFlag)) {
					throw new MidplatException("该网点无销售此产品的资质，银行网点销售资格校验失败，不允许实时出单!");
			} else if ("L".equals(saleFlag) && ifAccFlag) {
					throw new MidplatException("该网点无销售此产品的资质，银行网点销售资格校验失败，不允许实时出单!");
				}
			
			CheckTeller(mHeadEle);
			
			cLogger.debug("资质校验结束");
		}else if ("T".equals(state)) {// 系统状态为T
			throw new MidplatException("该网点处于关闭状态，系统限制投保!");
		} else if ("".equals(saleFlag)) {
				throw new MidplatException("该网点未获得销售许可，限制投保!");
		} else if ("L".equals(saleFlag) && ifAccFlag) {
				throw new MidplatException("该网点未获得投连产品销售许可，限制投保");
			}
		
		
		String sAgentCodeGrade = aNodeMapDB.getAgentCodeGrade();
		if (sAgentCodeGrade.length() == 1) {
			sAgentCodeGrade = "0" + sAgentCodeGrade;
		}
		// add by fzg 2012-2-14 时间校验
		NodeTimeCheck(mRootEle);
		
	}
	/**
	 * 同一受益顺序校验
	 * 
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws Exception
	 */
	public static boolean SameGradeBnfCheck(Document InXmlDoc) throws Exception {
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");

		String tBnfGrade = "";
		List bnfs = new ArrayList(); // 用来装受益人
		List bnfLots = new ArrayList(); // 用来装受益人
		// List bnfID = new ArrayList(); // 用来装受益人证件号
		bnfs = mBodyEle.getChildren("Bnf");
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			if (beneficType.equals("N")) {
				String[] tBnfLot1 = new String[2];
				tBnfLot1[0] = bnf.getChildText("Grade");
				tBnfLot1[1] = bnf.getChildText("Lot");
				bnfLots.add(tBnfLot1);
			}
		}

		// 判断受益人中是否有 两个节点录入同一个受益人
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			if (beneficType.equals("N")) {
				String[] tBnfID = new String[2];
				tBnfID[0] = bnf.getChildText("IDType");
				tBnfID[1] = bnf.getChildText("IDNo");
				// 循环比较当前节点与此节点之后的每个节点的证件类型及号码是否相同
				for (int j = i + 1; j < bnfs.size(); j++) {
					Element bnf2 = (Element) bnfs.get(j);
					String[] tBnfID2 = new String[2];
					tBnfID2[0] = bnf2.getChildText("IDType");
					tBnfID2[1] = bnf2.getChildText("IDNo");
					if (tBnfID[0].equals(tBnfID2[0])
							&& tBnfID2[1].equals(tBnfID[1])) {
						throw new Exception("受益人证件信息有误！");
					}
				}
			}

		}

		// 判断同一顺序的受益人份额是否为100%
		for (int i = 0; i < bnfLots.size(); i++) {
			String[] tArrBnfLoti = (String[]) bnfLots.get(i);
			String tBnfGradei = tArrBnfLoti[0];
			double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);

			// 如果仅有一个受益人，则直接进行判断
			if (bnfLots.size() == 1) {
				if (tBnfLoti < 100) {
					return false;
				}
			}

			if ("".equals(tBnfGrade) || tBnfGrade.indexOf(tBnfGradei) < 0) {
				for (int j = i + 1; j < bnfLots.size(); j++) {
					String[] tArrBnfLotj = (String[]) bnfLots.get(j);
					String tBnfGradej = tArrBnfLotj[0];
					double tBnfLotj = Double.parseDouble(tArrBnfLotj[1]);
					if (tBnfGradej.equals(tBnfGradei)) {
						// 设置已经判断过的受益人顺序
						tBnfGrade += tBnfGradej + ",";
						// 设置同一受益顺序的受益人总份额
						tBnfLoti += tBnfLotj;
						System.out.println("tBnfLoti" + tBnfLoti);
					}
					if (j == bnfLots.size() - 1) {
						if (tBnfLoti > 100) {
							return false;
						}
						if (tBnfLoti < 100) {
							return false;
						}
					}
				}
				if (i == bnfLots.size() - 1) {
					if (tBnfLoti < 100) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 跳号校验
	 * 
	 * @param InXmlDoc
	 * @return
	 */
	public static boolean DigitBnfCheck(Document InXmlDoc) {
		boolean digitFlag = true;
		// 受益人列表
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		List bnfs = new ArrayList(); // 用来装受益人
		List<Integer> bnfGrades = new ArrayList<Integer>(); // 用来装受益人
		bnfs = mBodyEle.getChildren("Bnf");
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			String Grade = bnf.getChildTextTrim("Grade");
			int iGrade = Integer.valueOf(Grade);
			if (beneficType.equals("N")) {
				bnfGrades.add(iGrade);
			}
		}

		// 对数组排序
		Collections.sort(bnfGrades);

		// 检查跳号
		digitFlag = check(bnfGrades);

		return digitFlag;
	}

	public static boolean check(List<Integer> bnfGrades) {
		for (int i = 0; i < bnfGrades.size() - 1; i++) {
			if ((bnfGrades.get(i + 1) - bnfGrades.get(i)) > 1) {
				return false;
			}
		}
		return true;
	}

	public static void CheckPrt20No(String sPrtNo) throws MidplatException {
		// if (sPrtNo == null || "".equals(sPrtNo)) {
		// throw new MidplatException("保单首页号不能为空！");
		// } else if (sPrtNo.length() != 20) {
		// throw new MidplatException("保单首页号长度必须为20位！");
		// }
	}
	
	public static void CheckTeller (Element mHeadEle) throws MidplatException{
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sTellerNo= (String) mHeadEle.getChildTextTrim("TellerNo");
		String sTranDate= (String) mHeadEle.getChildTextTrim("TranDate");
		String sql="Select TellerQuNo,to_char(EndDate,'yyyyMMdd') from LdTeller where TranCom='"+sTranCom+"' and ZoneNo='"+sZoneNo+"' and TellerNo='"+sTellerNo+"'";
		SSRS tSSRS = new SSRS();
		ExeSQL texeSql = new ExeSQL();
		tSSRS = texeSql.execSQL(sql);
		if(tSSRS.MaxRow==0){
			throw new MidplatException("未找到相关银行柜员,请确认");
		}else if(tSSRS.GetText(1, 1)==null || "".equals(tSSRS.GetText(1, 1))){
			throw new MidplatException("未找到该柜员的兼业代理资格证信息，销售资质校验失败，不允许实时出单");
		}else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date tranDate=null;
			Date endDate= null;
			
			try {
				tranDate = dateFormat.parse(sTranDate);
				if(tSSRS.GetText(1, 2)==null || "".equals(tSSRS.GetText(1, 2))){
					throw new MidplatException("未找到该柜员的兼业代理资格证到期日信息，销售资质校验失败，不允许实时出单");
				}
				endDate = dateFormat.parse(tSSRS.GetText(1, 2));
//				if(tSSRS.GetText(1, 2)!=null && !"".equals(tSSRS.GetText(1, 2))){
//					StartDate = dateFormat.parse(tSSRS.GetText(1, 2));	
//				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if(tranDate.before(StartDate)){
//				throw new MidplatException("该银行柜员销售资格证未生效,限制投保");
//			}
			if(endDate.before(tranDate)){
				throw new MidplatException("该柜员的兼业代理资格证已到期，销售资质校验失败，不允许实时出单");
			}
			
		} 
	}
	
	
	public static void CheckNodeQu (NodeMapDB aNodeMapDB) throws MidplatException{
		String Conagentno = aNodeMapDB.getConAgentNo();
		String Conenddate = aNodeMapDB.getConEndDate();
		
		if(Conagentno==null || "".equals(Conagentno)){
			throw new MidplatException("未找到该网点销售资格证号，银行网点销售资格校验失败，不允许实时出单!");
		}else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date tranDate=null;
			Date endDate= null;
			
			try {
				tranDate = dateFormat.parse(DateUtil.getCur10Date());
				if(Conenddate==null || "".equals(Conenddate)){
					throw new MidplatException("未找到该网点销售资格证到期日信息，银行网点销售资格校验失败，不允许实时出单!");
				}
				endDate = dateFormat.parse(Conenddate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(endDate.before(tranDate)){
				throw new MidplatException("该网点销售资格证已到期，银行网点销售资格校验失败，不允许实时出单");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
////			String s = "0123456789012345678";
//			MidplatCheck.CheckTeller(s);
//		} catch (Exception e) {
//			System.out.print(e.getMessage());
//		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date endDate= null;
		try {
			endDate = dateFormat.parse("20120405");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date StartDate=new Date(0);
		try {
			StartDate=dateFormat.parse("20120405");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(StartDate.equals(endDate));
	}
}
