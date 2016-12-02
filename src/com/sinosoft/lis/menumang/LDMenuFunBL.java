/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Webҵ��ϵͳ
 * </p>
 * <p>
 * Description: �˵������߼�������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fanym
 * @version 1.0
 */
public class LDMenuFunBL {
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** �����洫�����ݵ����� */
	// private VData mResult = new VData();
	private VData mInputData = new VData();

	/** ���ݲ����ַ��� */
	private String mOperate = null;

	private LDMenuSchema mLDMenuSchema = new LDMenuSchema();

	private TransferData tTransferData = new TransferData();

	private String NodeCode = "";

	private String ischild = "";

	// ҵ������ر���

	public LDMenuFunBL() {
	}

	// �������ݵĹ�������
	public boolean submitData(VData cInputData, String cOperate) {
		// ���������ݿ�����������
		this.mOperate = cOperate;

		// �õ��ⲿ���������,�����ݱ��ݵ�������
		if (!getInputData(cInputData)) {
			return false;
		}
		// System.out.println("After getinputdata");

		// ����ҵ����
		if (!dealData()) {
			return false;
		}
		// System.out.println("After dealData��");
		// ׼������̨������
		if (!prepareOutputData()) {
			return false;
		}
		// System.out.println("After prepareOutputData");
		// System.out.println("Start LDMenuGrp BL Submit...");

		LDMenuFunBLS tLDMenuFunBLS = new LDMenuFunBLS();
		boolean tag = tLDMenuFunBLS.submitData(cInputData, cOperate);
		// System.out.println("tag : " + tag);
		if (!tag) {
			return false;
		}
		// System.out.println("End LDMenuGrp BL Submit...");

		// �������Ҫ����Ĵ����򷵻�
		if (tLDMenuFunBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDMenuFunBLS.mErrors);
			return false;
		}

		mInputData = null;
		return true;
	}

	private boolean getInputData(VData mInputData) {
		// System.out.println("start BL get inputdata...");
		mLDMenuSchema = (LDMenuSchema) mInputData.getObjectByObjectName(
				"LDMenuSchema", 0);
		if ((mLDMenuSchema == null)) {
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "û�еõ��㹻�����ݣ�����ȷ��!";
			this.mErrors.addOneError(tError);
			return false;
		} else if (this.mOperate.equalsIgnoreCase("insert")) {
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			ischild = (String) tTransferData.getValueByName("ischild");
			NodeCode = (String) tTransferData.getValueByName("NodeCode");
			
			if (ischild.equals("") || NodeCode.equals("")) {
				// @@������
				CError tError = new CError();
				tError.moduleName = "LDMenuFunBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "û�еõ��㹻�����ݣ�����ȷ��!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			mInputData.add(mLDMenuSchema);
		} catch (Exception ex) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "��׼������㴦������Ҫ������ʱ����";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private static boolean dealData() {
		return true;
	}
}
