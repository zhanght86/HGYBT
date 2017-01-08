package com.sinosoft.midplat.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.NodeMapSet;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;

/**
 * @ClassName: MidplatUtil
 * @Description: 中间平台工具类
 * @author yuantongxin
 * @date 2017-1-6 下午2:33:14
 */
public class MidplatUtil implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(MidplatUtil.class);
	
	/**
	 * DESC 校验保单号前三位与城市编码是否符合规则 如果不存在返回false
	 * 
	 * @param ContNo
	 *            CityNo
	 * @return boolean
	 */
	public static String getShortName4LDCom(String CityNo)
			throws MidplatException {

		String mSqlStr = new StringBuilder("SELECT axashortname FROM LDCOM WHERE ")
				.append(" SUBSTR(COMCODE,7,3)='").append(CityNo).append('\'').toString();
		String sShortComName = new ExeSQL().getOneValue(mSqlStr);

		if (sShortComName == null || "".equals(sShortComName)) {
			throw new MidplatException("该机构未配置机构短名称");
		}
		return sShortComName;
	}
	
	/**
	 * 检验是否存在投连险
	 * 
	 * @param mRiskList
	 * @return
	 */
	
	public static boolean ifAccRisk(List mRiskList) {
		long mStartMillis = System.currentTimeMillis();
		boolean rFlag = false;
		// 险种数量
		int mRiskSize = mRiskList.size();
		// 获取主险的Risk节点
		for (int i = 0; i < mRiskSize; i++) {
			Element eRisk = (Element) mRiskList.get(i);
			String sRiskCode = eRisk.getChildText(RiskCode);
			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
			mLMRiskAppDB.setRiskCode(sRiskCode);
			if (!mLMRiskAppDB.getInfo()) {
				rFlag = false;
			} else {
				if (mLMRiskAppDB.getRiskType().equals("3")) {
					rFlag = true;
				}
			}
		}
		ControlUtil.sqlExecTime("TranLogDB.insert()", mStartMillis);
		return rFlag;
	}
	
	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);

		Element mHead = new Element(Head);
		mHead.addContent(mFlag);
		mHead.addContent(mDesc);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHead);

		return new Document(mTranData);
	}

	/**
	 * DESC 不存在  返回true 存在 返回false
	 * @param tTranCom
	 * @param tZoneNo
	 * @param tNodeNo
	 * @return
	 */
	public static boolean checkBankNode4DB(int tTranCom, String tZoneNo,
			String tNodeNo) {
		boolean retFlag = true;
		String mSqlStr = new StringBuilder(
				"select count(1) from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(tTranCom)
				.append(" and ZoneNo='").append(tZoneNo).append('\'')
				.append(" and NodeNo='").append(tNodeNo).append('\'')
				.toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 校验保单号编写规范 602-0027501
	 * 
	 * @param ContNo
	 * @return boolean
	 */
	public static boolean checkContNo(String ContNo) throws MidplatException {
		boolean retFlag = true;
		String preContNo = ContNo.substring(0, 3);
		String midContNo = ContNo.substring(3, 4);
		String endContNo = ContNo.substring(4, 11);
		if (!preContNo.matches("\\d{3}") || !"-".equals(midContNo)
				|| !endContNo.matches("\\d{7}")) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 校验保单号前三位与城市编码是否符合规则 如果不存在返回false
	 * 
	 * @param ContNo
	 *            CityNo
	 * @return boolean
	 */
	public static boolean checkContNo(String ContNo, String preContNo)
			throws MidplatException {
		boolean retFlag = true;
		String sPreContNo = ContNo.substring(0, 3);

		if (!sPreContNo.equals(preContNo)) {
			retFlag = false;
		}

		return retFlag;
	}

	/**
	 * DESC 校验保单号前三位与城市编码是否符合规则 如果不存在返回false
	 * 
	 * @param ContNo
	 *            CityNo
	 * @return boolean
	 */
//	public static String getShortName4LDCom(String CityNo)
//			throws MidplatException {
//
//		String mSqlStr = new StringBuilder("SELECT axashortname FROM LDCOM WHERE ")
//				.append(" SUBSTR(COMCODE,7,3)='").append(CityNo).append('\'').toString();
//		String sShortComName = new ExeSQL().getOneValue(mSqlStr);
//
//		if (sShortComName == null || "".equals(sShortComName)) {
//			throw new MidplatException("该机构未配置机构短名称");
//		}
//		return sShortComName;
//	}

	/**
	 * DESC 如果该网点存在 返回false 不存在返回TRUE
	 * 
	 * @param tManageCom
	 * @param tAgentCom
	 * @return
	 */
	public static boolean checkAgentCom4DB(String tManageCom, String tAgentCom) {
		boolean retFlag = true;
		String mSqlStr = new StringBuilder(
				"select count(1) from NodeMap where Type=").append("0")
				.append(" and AgentCom='").append(tAgentCom).append('\'')
				.append(" and ManageCom='").append(tManageCom).append('\'')
				.toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 如果存在 返回FALSE 不存在返回TRUE
	 * 
	 * @param tManageCom
	 * @param tAgentCode
	 * @return
	 */
	public static boolean checkAgentCode4DB(String tManageCom, String tAgentCode) {
		boolean retFlag = true;
		String mSqlStr = new StringBuilder(
				"select count(1) from AXAAgent where 1=").append("1")
				.append(" and AgentCode='").append(tAgentCode).append('\'')
				.append(" and ManageCom like '").append(tManageCom).append("%").append('\'')
				.toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 如果存在 返回FALSE 不存在返回TRUE
	 * 
	 * @param tManageCom
	 * @param tAgentCode
	 * @return
	 */
	public static boolean checkCityCode4DB(String tCityCode) {
		boolean retFlag = true;
		
		String mSqlStr = new StringBuilder(
				"select count(1) from LDCOM where 1=").append("1")
				.append(" and ComCode LIKE '").append("86____"+tCityCode).append('\'')
				.toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}
	/**
	 * 下面方法是为了确定“城市编码”是否存在于指定管理机构下
	 * @param tManage
	 * @param tCityCode
	 * @return
	 */
	public static boolean checkCityFManageDB(String tManage, String tCityCode) {
		boolean retFlag = true;
		//String sComCode = MidplatUtil.getManagecom4ac(tAreaNo, tCityCode);
		String mSqlStr = new StringBuilder(
				"select count(1) from LDCOM where 1=").append("1")
				.append(" and ComCode like '").append(tManage).append("%").append('\'')
				.append(" and SUBSTR(COMCODE,7,3)='").append(tCityCode).append('\'').toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 如果存在 返回FALSE 不存在返回TRUE
	 * 
	 * @param tManageCom
	 * @param tAgentCode
	 * @return
	 */
	public static boolean checkCityNo4DB(String tCityNo) {
		boolean retFlag = true;

		String mSqlStr = new StringBuilder(
				"select count(1) from LDCOM where 1=").append("1")
				.append(" and SUBSTR(ComCode,7,3)='").append(tCityNo)
				.append('\'').toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			retFlag = false;
		}
		return retFlag;
	}

	/**
	 * DESC 如果存在 返回FALSE 不存在返回TRUE
	 * 
	 * @param tManageCom
	 * @param tAgentCode
	 * @return
	 */
	public static String checkAgentCodeName4DB(String tManageCom,
			String tAgentCode) {
		String sAgentName = "";
		String mSqlStr = new StringBuilder(
				"select AgentName from AXAAgent where 1=").append("1")
				.append(" and AgentCode='").append(tAgentCode).append('\'')
				.append(" and ManageCom='").append(tManageCom).append('\'')
				.toString();
		sAgentName = new ExeSQL().getOneValue(mSqlStr);
		if(sAgentName == null)
			sAgentName = "";
		return sAgentName;
	}

	/**
	 * 校验金盛代理机构
	 * 
	 * @param sAgentCode
	 * @return
	 */
	public static boolean checkAgentCode(String sAgentCode) {
		boolean flag = true;
		if (sAgentCode == null || "".equals(sAgentCode)) {
			flag = false;
		}
		if (!sAgentCode.matches("\\d{6}")) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验金盛城市机构
	 * 
	 * @param sAgentCode
	 * @return
	 */
	public static boolean checkCityCode(String sCityCode) {
		boolean flag = true;
		if (sCityCode == null || "".equals(sCityCode)) {
			flag = false;
		}
		if (!sCityCode.matches("\\d{3}")) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验金盛银行编码
	 * 
	 * @param sAgentCode
	 * @return
	 */
	public static boolean checkBankNode(String sBankNode) {

		if (sBankNode == null || "".equals(sBankNode)) {
			return false;
		}

		String sTranCom = sBankNode.substring(0, 3);

		if (sTranCom.equals("011")) {
			if (sBankNode.length() != 15) {
				return false;
			}

			String sb = sBankNode.substring(3, 4);
			String sb2 = sBankNode.substring(9, 10);

			String sZoneNo = sBankNode.substring(4, 9);
			String sNodeNo = sBankNode.substring(10, 15);
			String dTranCom = getLDCodeName(sTranCom, "trancom_bank");
			if (dTranCom == null || "".equals(dTranCom)) {
				return false;
			}
			if (!sZoneNo.matches("\\d{5}")) {
				return false;
			}
			if (!sNodeNo.matches("\\d{5}")) {
				return false;
			}
			if (!sb.equals("-") || !sb2.equals("-")) {
				return false;
			}

			System.out.println(sTranCom + sZoneNo + sNodeNo);
		}else if (sTranCom.equals("012")) {
			if (sBankNode.length() != 17) {
				return false;
			}

			String sb = sBankNode.substring(3, 4);
			String sb2 = sBankNode.substring(10, 11);

			String sZoneNo = sBankNode.substring(4, 10);
			String sNodeNo = sBankNode.substring(11, 17);
			String dTranCom = getLDCodeName(sTranCom, "trancom_bank");
			if (dTranCom == null || "".equals(dTranCom)) {
				return false;
			}
			if (!sZoneNo.matches("\\d{6}")) {
				return false;
			}
			if (!sNodeNo.matches("\\d{6}")) {
				return false;
			}
			if (!sb.equals("-") || !sb2.equals("-")) {
				return false;
			}

			System.out.println(sTranCom + sZoneNo + sNodeNo);
		}
		return true;
	}

	/**
	 * 校验金盛代理机构编码
	 * 
	 * @param sAgentCode
	 * @return
	 */
	public static boolean checkAxaAgentCom(String sAxaAgentCom) {

		if (sAxaAgentCom == null || "".equals(sAxaAgentCom)) {
			return false;
		}
		sAxaAgentCom = sAxaAgentCom.trim();
		if (sAxaAgentCom.length() != 16) {
			return false;
		}
		String sUnitCode = sAxaAgentCom.substring(0, 6);
		if (!sUnitCode.matches("\\d{6}")) {
			return false;
		}

		String sb = sAxaAgentCom.substring(6, 7);
		String sAgentGrade = sAxaAgentCom.substring(7, 9);
		String sb2 = sAxaAgentCom.substring(9, 10);
		String sAgentCom = sAxaAgentCom.substring(10, 16);
		if (!sAgentCom.matches("\\d{6}")) {
			return false;
		}

		String dAgentGrade = getLDCodeName(sAgentGrade, "agent_level");
		if (dAgentGrade == null || "".equals(dAgentGrade)) {
			return false;
		}

		if (!sb.equals("-") || !sb2.equals("-")) {
			return false;
		}
		System.out.println(sUnitCode + sb + sAgentGrade + sb2 + sAgentCom);
		return true;
	}

	/**
	 * 校验金盛销售组号
	 * 
	 * @param sAgentCode
	 * @return
	 */
	public static boolean checkUnitCode(String sAxaAgentCom,
			String sAxaAgentCode) {

		String sUnitCode = sAxaAgentCom.substring(0, 6);
		String sUnitCode2 = sAxaAgentCode.substring(0, 6);
		if (!sUnitCode.equals(sUnitCode2)) {
			return false;
		}
		return true;
	}

	
	/**
	 * DESC 花旗校验证件类型，只能为A出生证，I身份证，P护照，S军人证，X其他
	 * 
	 * @param ContNo
	 * @return boolean
	 */
	public static boolean checkIdType(String IdNo) throws MidplatException {
		if ("A".equals(IdNo)){
			return true;
		}
		if ("I".equals(IdNo)){
			return true;
		}
		if ("P".equals(IdNo)){
			return true;
		}
		if ("S".equals(IdNo)){
			return true;
		}
		if ("X".equals(IdNo)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 通过传入的　地区编码　城市编码　组合成管理机构
	 * 
	 * @param sAreaNo
	 * @param sCityNo
	 * @return ManageCom
	 */
	public static String getManagecom4ac(String sAreaNo, String sCityNo) {
		if (sAreaNo == null || "".equals(sAreaNo)) {
			sAreaNo = "00";
		}
		if (sCityNo == null || "".equals(sCityNo)) {
			sCityNo = "000";
		}
		String sManageCom = "86" + sAreaNo + "00" + sCityNo;
		if (sManageCom.length() != 9) {
			sManageCom = "86";
		}
		return sManageCom;
	}
	
	public static String getManagecom4ac(String sCityNo) {
		String sManageCom = "";
		if (sManageCom.length() != 9) {
			sManageCom = "86";
		}
		
		String sql="select comcode from ldcom where substr(comcode,7,3)='"+sCityNo+"'";
		sManageCom =new ExeSQL().getOneValue(sql);

		return sManageCom;
	}

	public static NodeMapSchema getNodeMap(int pType, int pTranCom,
			String pNodeNo) throws MidplatException {
		String mSqlStr = new StringBuilder("select * from NodeMap where Type=")
				.append(pType).append(" and TranCom=").append(pTranCom)
				.append(" and NodeNo='").append(pNodeNo).append('\'')
				.toString();
		cLogger.info(mSqlStr);
		NodeMapSet mNodeMapSet = new NodeMapDB().executeQuery(mSqlStr);
		if (mNodeMapSet.size() != 1) {
			throw new MidplatException("网点未配置，或配置有误！");
		}

		return mNodeMapSet.get(1);
	}

	// 用于NewContConfirmOut.xsl样式表方法
	public static String GetNextOne(String endYear) {
		int a;
		if (endYear == "" || endYear == null) {
			return "-";
		}
		a = Integer.valueOf(endYear) + 1;
		String retStr = String.valueOf(a);
		return a + "";

	}

	public static String GetNextTwo(String endYear) {
		int a;
		if (endYear == "" || endYear == null) {
			return "-";
		}
		a = Integer.valueOf(endYear) + 2;
		String retStr = String.valueOf(a);
		return a + "";

	}

	public static String GetNextThree(String endYear) {
		int a;
		if (endYear == "" || endYear == null) {
			return "-";
		}
		a = Integer.valueOf(endYear) + 3;
		String retStr = String.valueOf(a);
		return a + "";

	}

	public static String GetInsuredName(String name) {
		String a;
		a = "被保险人：" + name;
		return a;
	}

	public static String GetCountMoney(String a, String b) {
		String s;
		s = "本期保险费合计：" + a + "(RMB" + b + "元)";
		return s;
	}

	public static String GetFirstAddMoney(String a) {
		String s;
		s = "首期追加保险费：" + a + "元";
		return s;
	}

	/**
	 * 通过保险公司方代码查询银行方代码
	 * 
	 * @param tinsurer_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getLDCodeName(String code, String tcodetype) {
		String mbank_Code;
		String tSql1 = "select codename from ldcode " + "where code ='" + code
				+ "' and codetype ='" + tcodetype + "'";
		ExeSQL ttExeSQL = new ExeSQL();
		mbank_Code = ttExeSQL.getOneValue(tSql1);
		System.out.println(tSql1);
		return mbank_Code;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * 参照calInterval(String cstartDate, String cendDate, String
	 * unit)，前两个变量改为日期型即可
	 * <p>
	 * 
	 * @param startDate
	 *            起始日期，Date变量
	 * @param endDate
	 *            终止日期，Date变量
	 * @param unit
	 *            时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
//	public static String calInterval(String sstartDate, String sendDate,
//			String unit) {
//		int interval = 0;
//		if (sstartDate == null) {
//			return "";
//		}
//		Date startDate = DateUtil.parseDate(sstartDate, "yyyy-mm-dd");
//		Date endDate = DateUtil.parseDate(sendDate, "yyyy-mm-dd");
//		GregorianCalendar sCalendar = new GregorianCalendar();
//		sCalendar.setTime(startDate);
//		int sYears = sCalendar.get(Calendar.YEAR);
//		int sMonths = sCalendar.get(Calendar.MONTH);
//		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
//
//		GregorianCalendar eCalendar = new GregorianCalendar();
//		eCalendar.setTime(endDate);
//		int eYears = eCalendar.get(Calendar.YEAR);
//		int eMonths = eCalendar.get(Calendar.MONTH);
//		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
//
//		if (unit.equals("Y")) {
//			interval = eYears - sYears;
//			if (eMonths < sMonths) {
//				interval--;
//			} else {
//				if (eMonths == sMonths && eDays < sDays) {
//					interval--;
//					if (eMonths == 1) { // 如果同是2月，校验润年问题
//						if ((sYears % 4) == 0 && (eYears % 4) != 0) { // 如果起始年是润年，终止年不是润年
//							if (eDays == 28) { // 如果终止年不是润年，且2月的最后一天28日，那么补一
//								interval++;
//							}
//						}
//					}
//				}
//			}
//		}
//		if (unit.equals("M")) {
//			interval = eYears - sYears;
//			interval *= 12;
//
//			interval += eMonths - sMonths;
//			if (eDays < sDays) {
//				interval--;
//				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
//				if (eDays == maxDate) {
//					interval++;
//				}
//			}
//		}
//		if (unit.equals("D")) {
//
//			sCalendar.set(sYears, sMonths, sDays);
//			eCalendar.set(eYears, eMonths, eDays);
//			long lInterval = (eCalendar.getTime().getTime() - sCalendar
//					.getTime().getTime()) / 86400000;
//			interval = (int) lInterval;
//
//		}
//
//		String sinterval = String.valueOf(interval);
//		return sinterval;
//	}

	/**
	 * 检验是否存在投连险
	 * 
	 * @param mRiskList
	 * @return
	 */
	
//	public static boolean ifAccRisk(List mRiskList) {
//		long mStartMillis = System.currentTimeMillis();
//		boolean rFlag = false;
//		// 险种数量
//		int mRiskSize = mRiskList.size();
//		// 获取主险的Risk节点
//		for (int i = 0; i < mRiskSize; i++) {
//			Element eRisk = (Element) mRiskList.get(i);
//			String sRiskCode = eRisk.getChildText(RiskCode);
////			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
//			mLMRiskAppDB.setRiskCode(sRiskCode);
//			if (!mLMRiskAppDB.getInfo()) {
//				rFlag = false;
//			} else {
//				if (mLMRiskAppDB.getRiskType().equals("3")) {
//					rFlag = true;
//				}
//			}
//		}
//		ControlUtil.sqlExecTime("TranLogDB.insert()", mStartMillis);
//		return rFlag;
//	}
	/**
	 * 判断主险是否为在售状态 
	 * 操作表bankandinsumap
	 * @throws MidplatException 
	 */
	public static boolean ifAccRiskCheck(List mRiskList,String sTranCom) throws MidplatException {
		long mStartMillis = System.currentTimeMillis();
		boolean rFlag = false;
		// 险种数量
		int mRiskSize = mRiskList.size();
		// 获取主险的Risk节点
		for (int i = 0; i < mRiskSize; i++) {
			Element eRisk = (Element) mRiskList.get(i);
			String sRiskCode = eRisk.getChildText(RiskCode);
			String mMainRiskCode = eRisk.getChildText(MainRiskCode);
			if(!sRiskCode.equals(mMainRiskCode)){
				continue;
			}
			String mTranCode = eRisk.getChildText("TranRiskCode");
			String sqlstr_s="select bak1 from bankandinsumap b " +
					"where b.trancom='" +sTranCom+"' " +
					"and b.tran_code='" +
					mTranCode+"' " +
					"AND CODETYPE='" +
					"RiskCode'";
			
			String sqlstr_d="select bak2 from bankandinsumap b " +
			"where b.trancom='" +sTranCom+"' " +
			"and b.tran_code='" +
			mTranCode+"' " +
			"AND CODETYPE='" +
			"RiskCode'";
			
			ExeSQL tExeSQL = new ExeSQL();
			String tStart = tExeSQL.getOneValue(sqlstr_s);
			cLogger.info("起售日期"+tStart);
			String tEnd = tExeSQL.getOneValue(sqlstr_d);
			cLogger.info("停售日期"+tEnd);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date startDate = null;
			Date endDate = null;
			Date tranDate=null;
			try {
				 startDate = dateFormat.parse(tStart);													
				 endDate = dateFormat.parse(tEnd);
				 tranDate=dateFormat.parse(String.valueOf(DateUtil.getCur8Date()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new MidplatException(e.getMessage());
			}
			if (tranDate.before(startDate)) {
				throw new MidplatException("此产品在此银行尚未开通！");
			}else if (tranDate.after(endDate)||tranDate.equals(endDate)) {
				throw new MidplatException("此产品在此银行已停售！");
			}else {
				rFlag = true; 
			}
			
		}
		return rFlag;
	}
	/**
	 * 检验是否存在投连险
	 * 
	 * @param mRiskList
	 * @return
	 */
	public static int AxaCashFlag(String sCashValue) {

		int num = Integer.valueOf(sCashValue);
		if (num <= 28) {
			return 0;
		}

		return 1;

	}

	/**
	 * 检验是否存在投连险
	 * 
	 * @param mRiskList
	 * @return
	 */
	public static int getCashNo(String n, String Num) {

		int in = Integer.valueOf(n);
		int iNum = Integer.valueOf(Num);

		return iNum - in;
	}

	/**
	 * 检验是否存在投连险
	 * 
	 * @param mRiskList
	 * @return
	 */
	public static int getCashNo2(String Num) {

		int iNum = Integer.valueOf(Num);

		if (iNum <= 4) {
			return 1;
		} else if (4 < iNum && iNum <= 8) {
			return 2;
		} else if (8 < iNum && iNum <= 12) {
			return 3;
		} else if (12 < iNum && iNum <= 16) {
			return 4;
		} else if (16 < iNum && iNum <= 20) {
			return 5;
		} else if (20 < iNum && iNum <= 24) {
			return 6;
		} else
			return 7;
	}

	public static String CreateManageCom(String sComGrade, String sOutComCode,
			String sUpComCode) {

		String sComCode = "86";

		if (sComGrade != null && "01".equals(sComGrade)) {
			return sOutComCode;
		} else if (sComGrade != null && "02".equals(sComGrade)) {
			return sUpComCode + sOutComCode;
		} else if (sComGrade != null && "03".equals(sComGrade)) {
			if (sUpComCode.length() == 2) {
				return sUpComCode + "00" + sOutComCode;
			}
			return sUpComCode + sOutComCode;
		} else if (sComGrade != null && "04".equals(sComGrade)) {
			if (sUpComCode.length() == 2) {
				return sUpComCode + "0000" + sOutComCode;
			} else if (sUpComCode.length() == 4) {
				return sUpComCode + "00" + sOutComCode;
			}
			return sUpComCode + sOutComCode;
		}

		return sComCode;
	}
	
	

    /**
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，舍弃法 author: HST
     * 起始日期，(String,格式："YYYY-MM-DD")
     * @param cstartDate String
     * 终止日期，(String,格式："YYYY-MM-DD")
     * @param cendDate String
     * 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @param unit String
     * 时间间隔,整形变量int
     * @return int
     */
    public static int calInterval(String cstartDate, String cendDate,
                                  String unit,String Insu)
    {
        FDate fDate = new FDate();
        Date startDate = fDate.getDate(cstartDate);
        Date endDate = fDate.getDate(cendDate);
        if (fDate.mErrors.needDealError())
        {
            return 0;
        }

        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

       
        
        if (StrTool.cTrim(unit).equals("Y"))
        {
            interval = eYears - sYears;
            cLogger.info("1----：" + interval);
            if (eMonths < sMonths)
            {
                interval--;
                cLogger.info("2----：" + interval);
            }
          
            else
            {
            	
            	if(eDays > 28 ){
                 	eDays = 28;
                 }
            
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    cLogger.info("3----：" + interval);
                    /*if (eMonths == 2)
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
                                interval++;
                                cLogger.info("4----：" + interval);
                            }
                        }
                    }*/
                }
                
            }
            cLogger.info("5----：" + interval);
        }
        if (StrTool.cTrim(unit).equals("M"))
        {
            interval = eYears - sYears;
//            interval = interval * 12;
            interval *= 12;
//            interval = eMonths - sMonths + interval;
            interval += eMonths - sMonths;

            if (eDays < sDays)
            {
                interval--;
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (StrTool.cTrim(unit).equals("D"))
        {
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // 处理润年
//            int n = 0;
//            eYears--;
//            if (eYears > sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    sYears++;
//                    n++;
//                }
//                int j = (eYears) % 4;
//                if (j == 0)
//                {
//                    eYears--;
//                    n++;
//                }
//                n += (eYears - sYears) / 4;
//            }
//            if (eYears == sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    n++;
//                }
//            }
//            interval += n;
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========

        }
        return interval;
    }
    
    /**
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
     * <p><b>Example: </b><p>
     * <p>参照calInterval(String  cstartDate, String  cendDate, String unit)，前两个变量改为日期型即可<p>
     * @param startDate 起始日期，Date变量
     * @param endDate 终止日期，Date变量
     * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @return 时间间隔,整形变量int
     */
    public static int calInterval(Date startDate, Date endDate, String unit)
    {
        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

        if (unit.equals("Y"))
        {
            interval = eYears - sYears;
            if (eMonths < sMonths)
            {
                interval--;
            }
            else
            {
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    if (eMonths == 1)
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
                                interval++;
                            }
                        }
                    }
                }
            }
        }
        if (unit.equals("M"))
        {
            interval = eYears - sYears;
//            interval = interval * 12;
            interval *= 12;

//            interval = eMonths - sMonths + interval;
            interval += eMonths - sMonths;
            if (eDays < sDays)
            {
                interval--;
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (unit.equals("D"))
        {
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // 处理润年
//            int n = 0;
//            eYears--;
//            if (eYears > sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    sYears++;
//                    n++;
//                }
//                int j = (eYears) % 4;
//                if (j == 0)
//                {
//                    eYears--;
//                    n++;
//                }
//                n += (eYears - sYears) / 4;
//            }
//            if (eYears == sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    n++;
//                }
//            }
//            interval += n;
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
        }
        return interval;
    }

    /**
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，舍弃法 author: HST
     * 起始日期，(String,格式："YYYY-MM-DD")
     * @param cstartDate String
     * 终止日期，(String,格式："YYYY-MM-DD")
     * @param cendDate String
     * 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @param unit String
     * 时间间隔,整形变量int
     * @return int
     */
    public static String calInterval(String cstartDate, String cendDate,
                                  String unit)
    {
        FDate fDate = new FDate();
        Date startDate = fDate.getDate(cstartDate);
        Date endDate = fDate.getDate(cendDate);
        if (fDate.mErrors.needDealError())
        {
            return "0";
        }

        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

        if (StrTool.cTrim(unit).equals("Y"))
        {
            interval = eYears - sYears;

            if (eMonths < sMonths)
            {
                interval--;
            }
            else
            {
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    if (eMonths == 2)
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
                                interval++;
                            }
                        }
                    }
                }
            }
        }
        if (StrTool.cTrim(unit).equals("M"))
        {
            interval = eYears - sYears;
//            interval = interval * 12;
            interval *= 12;
//            interval = eMonths - sMonths + interval;
            interval += eMonths - sMonths;

            if (eDays < sDays)
            {
                interval--;
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (StrTool.cTrim(unit).equals("D"))
        {
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // 处理润年
//            int n = 0;
//            eYears--;
//            if (eYears > sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    sYears++;
//                    n++;
//                }
//                int j = (eYears) % 4;
//                if (j == 0)
//                {
//                    eYears--;
//                    n++;
//                }
//                n += (eYears - sYears) / 4;
//            }
//            if (eYears == sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    n++;
//                }
//            }
//            interval += n;
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========

        }
        return String.valueOf(interval);
    }
    
    


}