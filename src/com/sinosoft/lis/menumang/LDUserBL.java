/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

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
public class LDUserBL {
	private static final Logger cLogger = Logger.getLogger(LDUserBL.class);
	
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

	private String mManageCom = "";
	
	int mResultNum = 0;

	public LDUserBL() {
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
		this.mManageCom = mLDUserSchema.getComCode();
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
		StringBuffer tSBql = new StringBuffer(256);
		tSBql.append("select * from lduser where usercode = '");
		tSBql.append(mLDUserSchema.getUserCode());
		tSBql.append("' and password = '");
		tSBql.append(encryptPwd);
		tSBql.append("' "); 

		LDUserDB tLDUserDB = mLDUserSchema.getDB();

		cLogger.debug("SQL: " + tSBql);
		mLDUserSet = tLDUserDB.executeQuery(tSBql.toString());
		if (tLDUserDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUserBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "用户查询失败!";
			this.mErrors.addOneError(tError);
			mLDUserSet.clear();
			return false;
		}

		if (mLDUserSet == null || mLDUserSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);

			mLDUserSet.clear();
			return false;
		}

		// 判断用户的有效时间，包括开始时间和结束时间
		LDUserSchema tLDUserSchema = mLDUserSet.get(1);

		String strCurrentDate = PubFun.getCurrentDate();
		String strValidStartDate = tLDUserSchema.getValidStartDate();
		String strValidEndDate = tLDUserSchema.getValidEndDate();

		// 如果生效开始时间为空，则不做校验
		if (strValidStartDate != null && !strValidStartDate.equals("")) {

			if (strValidStartDate.compareTo(strCurrentDate) > 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserBL";
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
				tError.moduleName = "LDUserBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "当前帐户已经失效（系统时间大于失效时间）!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}

		

		String ComCode = mLDUserSchema.getComCode();
		System.out.println("mManageCom:"+ mManageCom + "ComCode:"+ tLDUserSchema.getComCode());
		if(mManageCom == null || "".equals(mManageCom) || !mManageCom.equals(tLDUserSchema.getComCode())){
			CError tError = new CError();
			tError.moduleName = "LDUserBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "登录机构选择有误,请选择该用户对应的登录机构!";
			this.mErrors.addOneError(tError);
 
			mLDUserSet.clear();
			return false;
		}
		
		String sUserState = tLDUserSchema.getUserState();
		// 如果生效结束时间为空，则不做校验
		if (sUserState != null && !sUserState.equals("")) {

			if ("1".equals(sUserState)) {
				CError tError = new CError();
				tError.moduleName = "LDUserBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "当前用户状态为失效状态,请确认!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}
		
		// System.out.println("ComCode:" + ComCode);
		int matchCount = 0;
		for (int i = 1; i <= mLDUserSet.size(); i++) {
			LDUserSchema tUserSchema = mLDUserSet.get(i);
			String getComCode = tUserSchema.getComCode();;
			if (getComCode.length() > ComCode.length()) {
				continue;
			}
			int j = 0;
			for (; j < getComCode.length(); j++) {
				if (ComCode.charAt(j) != getComCode.charAt(j)) {
					break;
				}
			}

			if (j == getComCode.length()) {
				matchCount++;
			}
		}
		// System.out.println("matchCount:" + matchCount);
		if (matchCount == 0) {
			return false;
		}
		mResult.add(mLDUserSet);
		mResultNum = mLDUserSet.size();
		// System.out.println(mResult);
		return true;
	}
}
