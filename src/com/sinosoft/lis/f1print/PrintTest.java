package com.sinosoft.lis.f1print;

import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PrintTest {
	public static void main(String[] args) {
		// 准备数据容器信息
		TransferData tTransferData = new TransferData();
		String filePath = "C:/Users/asus/Desktop/20110726axa/";

		String sArea = "";
		String sCity = "";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sStartDay = "";
		String sEndDay = "";

		System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-地区,2-城市,3-银行渠道,4-网点,5-网点专员,6-险种代码,7-开始日期,8-结束日期");
		System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-" + sArea + ",2-" + sCity
				+ ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode
				+ ",6-" + sRiskCode + ",7-" + sStartDay + ",8-" + sEndDay + "");
		System.out.println("LO:客户正在提取每日实时数据，文件路径为：" + filePath);

		tTransferData.setNameAndValue("Area", sArea);
		tTransferData.setNameAndValue("City", sCity);
		tTransferData.setNameAndValue("TranCom", sTranCom);
		tTransferData.setNameAndValue("AgentCom", sAgentCom);
		tTransferData.setNameAndValue("AgentCode", sAgentCode);
		tTransferData.setNameAndValue("RiskCode", sRiskCode);
		tTransferData.setNameAndValue("StartDay", sStartDay);
		tTransferData.setNameAndValue("EndDay", sEndDay);
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		//tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		LO_PrintBL tLO_PrintBL = new LO_PrintBL();
		if (!tLO_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tLO_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tLO_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}
