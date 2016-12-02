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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 菜单功能逻辑处理类
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
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	// private VData mResult = new VData();
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate = null;

	private LDMenuSchema mLDMenuSchema = new LDMenuSchema();

	private TransferData tTransferData = new TransferData();

	private String NodeCode = "";

	private String ischild = "";

	// 业务处理相关变量

	public LDMenuFunBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// System.out.println("After getinputdata");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// System.out.println("After dealData！");
		// 准备往后台的数据
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

		// 如果有需要处理的错误，则返回
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
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		} else if (this.mOperate.equalsIgnoreCase("insert")) {
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			ischild = (String) tTransferData.getValueByName("ischild");
			NodeCode = (String) tTransferData.getValueByName("NodeCode");
			
			if (ischild.equals("") || NodeCode.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDMenuFunBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有得到足够的数据，请您确认!";
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private static boolean dealData() {
		return true;
	}
}
