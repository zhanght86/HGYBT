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
	
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData cOutVData = new VData();
	
	/**
	 * 传输数据的公共方法
	 * <p><b>Example: </b><p>
	 * <p>CodeQueryUI tCodeQueryUI=new CodeQueryUI();<p>
	 * <p>VData tData=new VData();<p>
	 * <p>LDCodeSchema tLDCodeSchema =new LDCodeSchema();<p>
	 * <p>tLDCodeSchema.setCodeType("sex");<p>
	 * <p>tData.add(tLDCodeSchema);<p>
	 * <p>tCodeQueryUI.submitData(tData, "QUERY||MAIN");<p>
	 * @param cInputData 作为数据容器的VData对象
	 * @param cOperate 操作符标志
	 * @return 如果后台数据处理操作成功，将结果存在内部VData对象中，返回true；处理失败则返回false
	 */
	public boolean submitData(VData pInVData, String pOperate) {
		cLogger.info("Into CodeQueryUI.submitData()...");
		
		CodeQueryBL mCodeQueryBL = new CodeQueryBL();
		if (mCodeQueryBL.submitData(pInVData, pOperate)) {
			cOutVData = mCodeQueryBL.getResult();
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(mCodeQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CodeQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			cOutVData.clear();
			return false;
		}
		
		cLogger.info("Out CodeQueryUI.submitData()!");
		return true;
	}


	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult()
	{
		return cOutVData;
	}
}
