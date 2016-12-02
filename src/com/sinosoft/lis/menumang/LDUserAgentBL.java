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
public class LDUserAgentBL {
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

	int mResultNum = 0;

	public LDUserAgentBL() {
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

		// ���������ݿ�����������
		// this.mOperate = cOperate;
		// �õ��ⲿ���������,�����ݱ��ݵ�������
		// System.out.println("start get inputdata...");
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
			// @@������
			this.mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUserAgentBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "�û���ѯʧ��!";
			this.mErrors.addOneError(tError);
			mLDUserSet.clear();
			return false;
		}

		if (mLDUserSet.size() == 0) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "LDUserAgentBL";
			tError.functionName = "queryUser";
			tError.errorMessage = "δ�ҵ��������!";
			this.mErrors.addOneError(tError);

			mLDUserSet.clear();
			return false;
		}

		// �ж��û�����Чʱ�䣬������ʼʱ��ͽ���ʱ��
		LDUserSchema tLDUserSchema = mLDUserSet.get(1);

		String strCurrentDate = com.sinosoft.lis.pubfun.PubFun.getCurrentDate();
		String strValidStartDate = tLDUserSchema.getValidStartDate();
		String strValidEndDate = tLDUserSchema.getValidEndDate();

		// �����Ч��ʼʱ��Ϊ�գ�����У��
		if (strValidStartDate != null && !strValidStartDate.equals("")) {

			if (strValidStartDate.compareTo(strCurrentDate) > 0) {
				CError tError = new CError();
				tError.moduleName = "LDUserAgentBL";
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
				tError.moduleName = "LDUserAgentBL";
				tError.functionName = "queryUser";
				tError.errorMessage = "��ǰ�ʻ��Ѿ�ʧЧ��ϵͳʱ�����ʧЧʱ�䣩!";
				this.mErrors.addOneError(tError);

				mLDUserSet.clear();
				return false;
			}
		}

		// System.out.println("here gose:");

		// ��ҵ��½����Ҫ¼����� ���� 2006-10-25
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
