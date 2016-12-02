/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 用户业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author DingZhong
 * @version 1.0
 */
public class LDUserAgentBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	/** 菜单组、菜单组到菜单的相关信息 */
	LDUserSchema mLDUserSchema = new LDUserSchema();

	LDUserSet mLDUserSet = new LDUserSet();

	String mResultStr = "";

	int mResultNum = 0;

	public LDUserAgentBL() {
		// just for debug
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 判断操作是不是查询
		if (!cOperate.equals("query")) {
			return false;
		}

		// 将操作数据拷贝到本类中
		// this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		// System.out.println("start get inputdata...");
		if (!getInputData(cInputData)) {
			return false;
		}
		// System.out.println("---getInputData---");

		// 进行业务处理
		if (!queryUser()) {
			return false;
		}
		// System.out.println("---queryUser---");
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public int getResultNum() {
		return mResultNum;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 0);

		if (mLDUserSchema == null) {
			return false;
		}

		return true;
	}

	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean queryUser() {
		String encryptPwd = mLDUserSchema.getPassword();
		//
		// String decryptPwd = tIdea.decryptString(encryptPwd);
		// decryptPwd = decryptPwd.trim();
		// System.out.println("now encryptPwd");
		// System.out.println("decrypt pwd:" + decryptPwd);

		// String sqlStr = "select * from lduser where usercode = '" +
		// mLDUserSchema.getUserCode() + "' and password = '" + encryptPwd +
		// "'";
		StringBuffer tSBql = new StringBuffer(256);
		tSBql.append("select * from lduser where usercode = '");
		tSBql.append(mLDUserSchema.getUserCode());
		tSBql.append("' and password = '");
		tSBql.append(encryptPwd);
		tSBql.append("' and agentcom is not null");

		// System.out.println(sqlStr);
		LDUserDB tLDUserDB = mLDUserSchema.getDB();

		mLDUserSet = tLDUserDB.executeQuery(tSBql.toString());
		// System.out.println("here here here");
		if (tLDUserDB.mErrors.needDealError()) {
			// System.out.println("chucuo " + tLDUserDB.mErrors.toString() +
			// "***");
			// @@错误处理
			this.mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUserAgentBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "用户查询失败!";
			this.mErrors.addOneError(tError);
			mLDUserSet.clear();
			return false;
		}

		if (mLDUserSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserAgentBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);

			mLDUserSet.clear();
			return false;
		}

		// 判断用户的有效时间，包括开始时间和结束时间
		LDUserSchema tLDUserSchema = mLDUserSet.get(1);

		String strCurrentDate = com.sinosoft.lis.pubfun.PubFun.getCurrentDate();
		String strValidStartDate = tLDUserSchema.getValidStartDate();
		String strValidEndDate = tLDUserSchema.getValidEndDate();

		// 如果生效开始时间为空，则不做校验
		if (strValidStartDate != null && !strValidStartDate.equals("")) {

			if (strValidStartDate.compareTo(strCurrentDate) > 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserAgentBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "当前帐户还没有生效（系统时间小于生效时间）!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}

		// 如果生效结束时间为空，则不做校验
		if (strValidEndDate != null && !strValidEndDate.equals("")) {

			if (strValidEndDate.compareTo(strCurrentDate) < 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserAgentBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "当前帐户已经失效（系统时间大于失效时间）!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}

		// System.out.println("here gose:");

		// 兼业登陆不需要录入机构 周磊 2006-10-25
		// String ComCode = mLDUserSchema.getComCode();
		// //judge if comcode is valid station
		//
		// // String strSql =
		// // "select * from ldcode where codetype = 'station' and code = '" +
		// // ComCode + "'";
		// tSBql = new StringBuffer(256);
		// tSBql.append("select * from ldcode where codetype = 'station' and
		// code = '");
		// tSBql.append(ComCode);
		// tSBql.append("'");
		//
		// // System.out.println(strSql);
		// LDCodeSet tCodeSet = new LDCodeSet();
		// LDCodeDB tCodeDB = new LDCodeDB();
		// tCodeSet = tCodeDB.executeQuery(tSBql.toString());
		// if (tCodeSet == null || tCodeSet.size() == 0)
		// {
		// return false;
		// }
		//
		// // System.out.println("ComCode:" + ComCode);
		// int matchCount = 0;
		// for (int i = 1; i <= mLDUserSet.size(); i++)
		// {
		// LDUserSchema tUserSchema = mLDUserSet.get(i);
		// String getComCode = tUserSchema.getComCode();
		// // System.out.println("getComCode:" + getComCode);
		// // System.out.println("*************************");
		// if (getComCode.length() > ComCode.length())
		// {
		// continue;
		// }
		// int j = 0;
		// for (; j < getComCode.length(); j++)
		// {
		// if (ComCode.charAt(j) != getComCode.charAt(j))
		// {
		// break;
		// }
		// }
		//
		// if (j == getComCode.length())
		// {
		// matchCount++;
		// }
		// }
		// // System.out.println("matchCount:" + matchCount);
		// if (matchCount == 0)
		// {
		// return false;
		// }

		mResult.add(mLDUserSet);
		mResultNum = mLDUserSet.size();

		return true;
	}
}
