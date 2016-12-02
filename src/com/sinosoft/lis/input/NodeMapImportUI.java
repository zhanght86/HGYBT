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
public class NodeMapImportUI {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	public int iSuccNo = 0;
	
	public int iInsertNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iRepeatNo = 0;
	
	public int iEndNo = 0;

	public NodeMapImportUI() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		this.mOperate = cOperate;
		NodeMapImport tNodeMapImport = new NodeMapImport();

		if (tNodeMapImport.submitData(cInputData, cOperate) == false) {
			this.mErrors.copyAllErrors(tNodeMapImport.mErrors);
			System.out.println(tNodeMapImport.mErrors.getError(0).errorMessage);
			return false;
		} else {
			if (tNodeMapImport.mErrors.needDealError()) {
				System.out.println();
				this.mErrors.copyAllErrors(tNodeMapImport.mErrors);
				System.out.println("Error");
			}
			mResult = tNodeMapImport.getResult();
			iSuccNo = tNodeMapImport.iSuccNo;
			iInsertNo = tNodeMapImport.iInsertNo;
			iUpdateNo = tNodeMapImport.iUpdateNo;
			iRepeatNo = tNodeMapImport.iRepeatNo;
			iEndNo = tNodeMapImport.iEndNo;
			System.out.println(tNodeMapImport.XmlFileName);
			//System.out.println("成功提交" + tNodeMapImport.iSuccNo + "条数据!");
			System.out.println("最新插入" + tNodeMapImport.iInsertNo + "条数据!");
			System.out.println("最新更新" + tNodeMapImport.iUpdateNo + "条数据!");
			if(tNodeMapImport.iRepeatNo != 0){
				System.out.println(tNodeMapImport.iRepeatNo +"条数据已存在!");
			}
			
			File tFile = new File(tNodeMapImport.XmlFileName);
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
		tGlobalInput.ManageCom = "860300300";
		tTransferData.setNameAndValue("FileName", "NodeMap_Report.xls");
		tTransferData.setNameAndValue("FilePath", "C:/Users/asus/Desktop/");
		tTransferData.setNameAndValue("GlobalInput", tGlobalInput);
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		NodeMapImportUI tAgentImportUI = new NodeMapImportUI();
		tAgentImportUI.submitData(tVData, tGlobalInput.Operator);
		
		
		
		String Content = "";
		if(tAgentImportUI.iInsertNo != 0){
			Content = Content + "成功提交"
			+ tAgentImportUI.iInsertNo + "个新网点";
		}
		if(tAgentImportUI.iUpdateNo != 0){
			Content = Content + "成功更新"
			+ tAgentImportUI.iUpdateNo + "个网点";
		}
		
		if (tAgentImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tAgentImportUI.mErrors.getLastError();
		}else if(!tAgentImportUI.mErrors.needDealError()){
			Content = Content + ""
			+ "第" + tAgentImportUI.iEndNo + "行为空行,上载结束!";
		}
		System.out.println(Content);
	}
}
