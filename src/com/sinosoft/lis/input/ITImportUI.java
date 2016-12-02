package com.sinosoft.lis.input;

import java.io.File;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class ITImportUI {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	public int iSuccNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iRepeatNo = 0;
	
	public int iEndNo = 0;

	public ITImportUI() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		this.mOperate = cOperate;
		ITImport tITImport = new ITImport();

		if (tITImport.submitData(cInputData, cOperate) == false) {
			this.mErrors.copyAllErrors(tITImport.mErrors);
			System.out.println(tITImport.mErrors.getError(0).errorMessage);
			return false;
		} else {
			if (tITImport.mErrors.needDealError()) {
				System.out.println();
				this.mErrors.copyAllErrors(tITImport.mErrors);
				System.out.println("Error");
			}
			mResult = tITImport.getResult();
			iSuccNo = tITImport.iSuccNo;
			iRepeatNo = tITImport.iRepeatNo;
			iUpdateNo = tITImport.iUpdateNo;
			iEndNo = tITImport.iEndNo;
			System.out.println(tITImport.XmlFileName);
			System.out.println("成功提交" + tITImport.iSuccNo + "条数据!");
			if(tITImport.iRepeatNo != 0){
				System.out.println(tITImport.iRepeatNo +"条数据已存在!");
			}
			if(tITImport.iUpdateNo != 0){
				System.out.println(tITImport.iUpdateNo +"条数据已更新!");
			}
			
			File tFile = new File(tITImport.XmlFileName);
			if (!tFile.delete()) {
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "checkData";
				tError.errorMessage = "Xml文件删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tTransferData.setNameAndValue("FileName", "Policy.xls");
		tTransferData.setNameAndValue("FilePath", "C:/Users/asus/Desktop/");
		tTransferData.setNameAndValue("GlobalInput", tGlobalInput);
		tTransferData.setNameAndValue("AreaNo", "03");
		tTransferData.setNameAndValue("CityNo", "567");
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		ITImportUI tITImportUI = new ITImportUI();
		tITImportUI.submitData(tVData, tGlobalInput.Operator);
	}
}
