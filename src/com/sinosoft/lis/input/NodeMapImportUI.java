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

	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** �����洫�����ݵ����� */
	private VData mResult = new VData();
	/** ���ݲ����ַ��� */
	private String mOperate;
	
	public int iSuccNo = 0;
	
	public int iInsertNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iRepeatNo = 0;
	
	public int iEndNo = 0;

	public NodeMapImportUI() {

	}

	/**
	 * �������ݵĹ�������
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
			//System.out.println("�ɹ��ύ" + tNodeMapImport.iSuccNo + "������!");
			System.out.println("���²���" + tNodeMapImport.iInsertNo + "������!");
			System.out.println("���¸���" + tNodeMapImport.iUpdateNo + "������!");
			if(tNodeMapImport.iRepeatNo != 0){
				System.out.println(tNodeMapImport.iRepeatNo +"�������Ѵ���!");
			}
			
			File tFile = new File(tNodeMapImport.XmlFileName);
			if (!tFile.delete()) {
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "checkData";
				tError.errorMessage = "Xml�ļ�ɾ��ʧ��!";
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
			Content = Content + "�ɹ��ύ"
			+ tAgentImportUI.iInsertNo + "��������";
		}
		if(tAgentImportUI.iUpdateNo != 0){
			Content = Content + "�ɹ�����"
			+ tAgentImportUI.iUpdateNo + "������";
		}
		
		if (tAgentImportUI.mErrors.needDealError()) {
			Content = Content + ""
					+ tAgentImportUI.mErrors.getLastError();
		}else if(!tAgentImportUI.mErrors.needDealError()){
			Content = Content + ""
			+ "��" + tAgentImportUI.iEndNo + "��Ϊ����,���ؽ���!";
		}
		System.out.println(Content);
	}
}
