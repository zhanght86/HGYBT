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
 * Title: Webҵ��ϵͳ
 * </p>
 * <p>
 * Description: �û�ҵ���߼�������
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
	
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();
	
	/** �����洫�����ݵ����� */
	private VData mResult = new VData();

	/** ���ݲ����ַ��� */
	// private String mOperate;
	/** ҵ������ر��� */
	/** �˵��顢�˵��鵽�˵��������Ϣ */
	LDUserSchema mLDUserSchema = new LDUserSchema();

	LDUserSet mLDUserSet = new LDUserSet();

	String mResultStr = "";

	private String mManageCom = "";
	
	int mResultNum = 0;

	public LDUserBL() {
		// just for debug
	}

	/**
	 * �������ݵĹ�������
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// �жϲ����ǲ��ǲ�ѯ
		if (!cOperate.equals("query")) {
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		// System.out.println("---getInputData---");

		// ����ҵ����
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
	 * �����������еõ����ж��� ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// �����ѯ����
		mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 0);
		this.mManageCom = mLDUserSchema.getComCode();
		if (mLDUserSchema == null) {
			return false;
		}

		return true;
	}

	/**
	 * ��ѯ������������Ϣ ��������׼������ʱ���������򷵻�false,���򷵻�true
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
			// @@������
			this.mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUserBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "�û���ѯʧ��!";
			this.mErrors.addOneError(tError);
			mLDUserSet.clear();
			return false;
		}

		if (mLDUserSet == null || mLDUserSet.size() == 0) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "LDUserBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "δ�ҵ��������!";
			this.mErrors.addOneError(tError);

			mLDUserSet.clear();
			return false;
		}

		// �ж��û�����Чʱ�䣬������ʼʱ��ͽ���ʱ��
		LDUserSchema tLDUserSchema = mLDUserSet.get(1);

		String strCurrentDate = PubFun.getCurrentDate();
		String strValidStartDate = tLDUserSchema.getValidStartDate();
		String strValidEndDate = tLDUserSchema.getValidEndDate();

		// �����Ч��ʼʱ��Ϊ�գ�����У��
		if (strValidStartDate != null && !strValidStartDate.equals("")) {

			if (strValidStartDate.compareTo(strCurrentDate) > 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "��ǰ�ʻ���û����Ч��ϵͳʱ��С����Чʱ�䣩!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}

		// �����Ч����ʱ��Ϊ�գ�����У��
		if (strValidEndDate != null && !strValidEndDate.equals("")) {

			if (strValidEndDate.compareTo(strCurrentDate) < 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "��ǰ�ʻ��Ѿ�ʧЧ��ϵͳʱ�����ʧЧʱ�䣩!";
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
			tError.errorMessage = "��¼����ѡ������,��ѡ����û���Ӧ�ĵ�¼����!";
			this.mErrors.addOneError(tError);
 
			mLDUserSet.clear();
			return false;
		}
		
		String sUserState = tLDUserSchema.getUserState();
		// �����Ч����ʱ��Ϊ�գ�����У��
		if (sUserState != null && !sUserState.equals("")) {

			if ("1".equals(sUserState)) {
				CError tError = new CError();
				tError.moduleName = "LDUserBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "��ǰ�û�״̬ΪʧЧ״̬,��ȷ��!";
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
