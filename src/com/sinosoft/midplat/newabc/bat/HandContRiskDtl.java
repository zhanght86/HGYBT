package com.sinosoft.midplat.newabc.bat;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.ExeSQL;

public class HandContRiskDtl extends ToAbcBalance{
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	
	public HandContRiskDtl() {
		super(NewAbcConf.newInstance(), "2011");
	}
	
	@Override
	public void checkTranLog() throws MidplatException {
		String sql = "select 1 from tranlog where funcflag='2011' and rcode='0' and trandate ='"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+ "'";
		String flag = new ExeSQL().getOneValue(sql);
		if ("1".equals(flag)) {
			throw new MidplatException("手工单数据明细文件已成功回传，请不要重复操作！");

		}

	}
	
}
