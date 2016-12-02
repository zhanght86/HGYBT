/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class CodeQueryUI {
	private final static Logger cLogger = Logger.getLogger(CodeQueryUI.class);
	
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** �����洫�����ݵ����� */
	private VData cOutVData = new VData();
	
	/**
	 * �������ݵĹ�������
	 * <p><b>Example: </b><p>
	 * <p>CodeQueryUI tCodeQueryUI=new CodeQueryUI();<p>
	 * <p>VData tData=new VData();<p>
	 * <p>LDCodeSchema tLDCodeSchema =new LDCodeSchema();<p>
	 * <p>tLDCodeSchema.setCodeType("sex");<p>
	 * <p>tData.add(tLDCodeSchema);<p>
	 * <p>tCodeQueryUI.submitData(tData, "QUERY||MAIN");<p>
	 * @param cInputData ��Ϊ����������VData����
	 * @param cOperate ��������־
	 * @return �����̨���ݴ�������ɹ�������������ڲ�VData�����У�����true������ʧ���򷵻�false
	 */
	public boolean submitData(VData pInVData, String pOperate) {
		cLogger.info("Into CodeQueryUI.submitData()...");
		
		CodeQueryBL mCodeQueryBL = new CodeQueryBL();
		if (mCodeQueryBL.submitData(pInVData, pOperate)) {
			cOutVData = mCodeQueryBL.getResult();
		} else {
			// @@������
			this.mErrors.copyAllErrors(mCodeQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CodeQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "���ݲ�ѯʧ��!";
			this.mErrors.addOneError(tError);
			cOutVData.clear();
			return false;
		}
		
		cLogger.info("Out CodeQueryUI.submitData()!");
		return true;
	}


	/**
	 * �������������������ȡ���ݴ�����
	 * @return ���������ݲ�ѯ����ַ�����VData����
	 */
	public VData getResult()
	{
		return cOutVData;
	}
}
