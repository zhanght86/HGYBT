/**
 * ��������
 */

package com.sinosoft.midplat.manage;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ComManageUI {
	private final static Logger cLogger = Logger.getLogger(ComManageUI.class);
	VData inputData = new VData();
	TransferData tTransferData = new TransferData();

	private String ComGrade = "";
	private String OutComCode = "";
	private String ComCode = "";
	private String Name = "";
	private String ShortName = "";
	private String UpComCode = "";
	private String mOperator = "";

	private int iUpComGrade = 0;
	private int iComGrade = 0;

	private final GlobalInput cGlobalInput;

	public ComManageUI(VData tVData) {
		inputData = tVData;
		tTransferData = (TransferData) inputData.get(0);
		cGlobalInput = (GlobalInput) inputData.get(1);

		this.ComGrade = (String) tTransferData.getValueByName("ComGrade");
		this.OutComCode = (String) tTransferData.getValueByName("OutComCode");
		this.ComCode = (String) tTransferData.getValueByName("ComCode");
		this.Name = (String) tTransferData.getValueByName("Name");
		this.ShortName = (String) tTransferData.getValueByName("ShortName");
		this.UpComCode = (String) tTransferData.getValueByName("UpComCode");

		cLogger.info("ͨ�������롢�������õĹ������Ϊ" + this.ComCode);
		this.mOperator = cGlobalInput.Operator;

	}

	/**
	 * @throws MidplatException
	 */
	public void insert() throws MidplatException {
		cLogger.info("�û�:" + this.mOperator + " Into ComManageUI.insert()...");

		if ("01".equals(this.ComGrade)) {
			throw new MidplatException("�Ѵ���һ���ܹ�˾,��������!");
		}

		checkLDCom();

		// ��ʼ��װLDCOM
		LDComDB tLDComDB = new LDComDB();
//		String cComCode = MidplatUtil.CreateManageCom(this.ComGrade,
//				this.OutComCode, this.UpComCode);
		tLDComDB.setComCode(this.ComCode);
		if (tLDComDB.getInfo()) {
			throw new MidplatException("�û����Ѵ���,��ȷ��!");
		}

		if (iComGrade == 4) {
			tLDComDB.setOutComCode(this.OutComCode);
//			tLDComDB.setCityCode(this.OutComCode);
//			
//			tLDComDB.setCityName(this.Name);
		}
		if (!"01".equals(this.ComGrade)) {
			tLDComDB.setUpComCode(this.UpComCode);
//			tLDComDB.setAreaID(this.AreaID);
//			tLDComDB.setAreaName(this.AreaName);
		}
//		tLDComDB.setOPERATOR(this.mOperator);
//		tLDComDB.setMAKEDATE(DateUtil.getCur10Date());
//		tLDComDB.setMAKETIME(DateUtil.getCur8Time());
//		tLDComDB.setMODIFYDATE(DateUtil.getCur10Date());
//		tLDComDB.setMODIFYTIME(DateUtil.getCur8Time());
		tLDComDB.setOutComCode(this.OutComCode);
		tLDComDB.setName(this.Name);
		tLDComDB.setShortName(this.ShortName);
		tLDComDB.setComGrade(this.ComGrade);

		if (!tLDComDB.insert()) {
			cLogger.error(tLDComDB.mErrors.getFirstError());
			throw new MidplatException("������������Ϣʧ�ܣ�");
		}

		cLogger.info("�û�:" + this.mOperator + "Out ComManageUI.insert()...");
	}

	public void update() throws MidplatException {
		cLogger.info("�û�:" + this.mOperator + " Into ComManageUI.update()...");

		if ("01".equals(this.ComGrade)) {
			throw new MidplatException("��������ܹ�˾��Ϣ!");
		}

		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(this.ComCode);
		if (!tLDComDB.getInfo()) {
			throw new MidplatException("�û���������,��ȷ��!");
		}

		checkLDCom();

		String cComCode = MidplatUtil.CreateManageCom(this.ComGrade,
				this.OutComCode, this.UpComCode);

		// �����������û��
		if (cComCode.equals(this.ComCode)) {
			LDComDB updateComDB = new LDComDB();
			updateComDB.setComCode(this.ComCode);
			if (!updateComDB.getInfo()) {
				throw new MidplatException("�û���������,��ȷ��!");
			}

			if (iComGrade == 4) {
				tLDComDB.setComCode(this.ComCode);
				tLDComDB.setOutComCode(this.OutComCode);
//				updateComDB.setCityCode(this.OutComCode);
//				updateComDB.setCityName(this.Name);
			}
			if (!"01".equals(this.ComGrade)) {
				updateComDB.setUpComCode(this.UpComCode);
//				updateComDB.setAreaID(this.AreaID);
//				updateComDB.setAreaName(this.AreaName);
			}
			updateComDB.setOutComCode(this.OutComCode);
			updateComDB.setName(this.Name);
			updateComDB.setShortName(this.ShortName);
			updateComDB.setComGrade(this.ComGrade);
			if (!updateComDB.update()) {
				throw new MidplatException("���»�����Ϣʧ�ܣ�");
			}
		} else {// ��������������
			String mSqlStr = new StringBuilder(
					"SELECT COUNT(1) FROM nodemap WHERE 1 = 1")
					.append("AND TRIM(MANAGECOM)='").append(this.ComCode)
					.append('\'').toString();
			if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
				cLogger.error(tLDComDB.mErrors.getFirstError());
				throw new MidplatException("�ù������������������,���ܸ��»������롢��������������ϼ�������");
			}

			String mSqlStr2 = new StringBuilder(
					"SELECT COUNT(1) FROM LDCOM WHERE 1 = 1")
					.append("AND TRIM(UPCOMCODE)='").append(this.ComCode)
					.append('\'').toString();
			if (!"0".equals(new ExeSQL().getOneValue(mSqlStr2))) {
				cLogger.error(tLDComDB.mErrors.getFirstError());
				throw new MidplatException(
						"�ù�������´��ڷ�֧����,���ܸ��»������롢��������������ϼ���������");
			}
			delete();
			insert();
		}

		cLogger.info("�û�:" + this.mOperator + " Out ComManageUI.update...");
	}

	public void delete() throws MidplatException {
		cLogger.info("�û�:" + this.mOperator + " Into ComManageUI.delete()...");
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(this.ComCode);
		if (!tLDComDB.getInfo()) {
			throw new MidplatException("�ù������������,��ȷ�ϣ�");
		}

		String mSqlStr = new StringBuilder(
				"SELECT COUNT(1) FROM nodemap WHERE 1 = 1")
				.append("AND TRIM(MANAGECOM)='").append(this.ComCode)
				.append('\'').toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			cLogger.error(tLDComDB.mErrors.getFirstError());
			throw new MidplatException("�ù������������������,����ɾ����");
		}

		String mSqlStr2 = new StringBuilder(
				"SELECT COUNT(1) FROM LDCOM WHERE 1 = 1")
				.append("AND TRIM(UPCOMCODE)='").append(this.ComCode)
				.append('\'').toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr2))) {
			cLogger.error(tLDComDB.mErrors.getFirstError());
			throw new MidplatException("�ù�������´��ڷ�֧����,����ɾ����");
		}

		if (!tLDComDB.delete()) {
			cLogger.error(tLDComDB.mErrors.getFirstError());
			throw new MidplatException("ɾ�����������Ϣʧ�ܣ�");
		}

		cLogger.info("�û�:" + this.mOperator + " Out  ComManageUI.delete...");
	}

	public void checkLDCom() throws MidplatException {
		if (!"01".equals(this.ComGrade)) {
			LDComDB tUpComDB = new LDComDB();
			tUpComDB.setComCode(this.UpComCode);
			if (!tUpComDB.getInfo()) {
				throw new MidplatException("���ϼ�����������,��ȷ��!");
			}
			String sUpComGrade = tUpComDB.getComGrade();
			iUpComGrade = Integer.valueOf(sUpComGrade);
			iComGrade = Integer.valueOf(this.ComGrade);
			if (iComGrade <= iUpComGrade) {
				throw new MidplatException("�û��������ܴ��������ϼ������ļ���!");
			}
//			if (!this.AreaID.equals(tUpComDB.getAreaID()) && iUpComGrade != 1) {
//				throw new MidplatException("�û��������������ϼ���������������ͬ,��ȷ��!");
//			}
		}
	}

	public static void main(String[] args) {
		GlobalInput pGlobalInput = new GlobalInput();
		pGlobalInput.ManageCom = "86";
		pGlobalInput.Operator = "002";

		VData vdate = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ComGrade", "04");
		tTransferData.setNameAndValue("AreaID", "01");
		tTransferData.setNameAndValue("AreaName", "����");
		tTransferData.setNameAndValue("OutComCode", "399");
		tTransferData.setNameAndValue("ComCode", "860100399");
		tTransferData.setNameAndValue("Name", "�麣");
		tTransferData.setNameAndValue("ShortName", "AXAZH");
		tTransferData.setNameAndValue("UpComCode", "8601");

		vdate.add(tTransferData);
		vdate.add(pGlobalInput);

		ComManageUI agentManageUI = new ComManageUI(vdate);
		try {
			// agentManageUI.insert();
			agentManageUI.update();
			// agentManageUI.delete();
		} catch (MidplatException e) {
			e.printStackTrace();
			e.getMessage();

		}
	}
}
