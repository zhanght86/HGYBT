package com.sinosoft.lis.f1print;

import jxl.format.BorderLineStyle;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.AgentCreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.NodeMapCreatExcel;

/**
 * <p>
 * ClassName: Agent_PrintBL
 * </p>
 * <p>
 * Description: �����˵���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: AXA_DB
 * @CreateDate��2011-07-26
 * @ReWriteDate:
 */ 
 
public class NodeMap_PrintBL extends NodeMapCreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";
	
	private String sBankCode= "";
	
	private String sManageCodeNo="";
	
	private String filePath = "";

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public NodeMap_PrintBL() {

	}

	/**
	 * ���ش���
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * �������ݵĹ�������
	 * 
	 * @param: cInputData ��������� cOperate ���ݲ���
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// �õ��ⲿ���������,�����ݱ��ݵ�������
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// ��ӡ
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * ȡ���������Ϣ
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.sBankCode = (String) mTransferData.getValueByName("BankCode");
		this.filePath = (String) mTransferData.getValueByName("filePath");
		this.sManageCodeNo = (String) mTransferData.getValueByName("ManageCodeNo");
		
		return true;
	}

	/**
	 * ���ؽ����
	 * 
	 * @return: VData ��������
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * ��ӡ����
	 * 
	 * @author zhanghj
	 * @return boolean
	 */
	private boolean getPrintData() {

		// ����Excel�ļ������·��
		setFilePath(filePath);
		// ����Excel��Sheet
		String[] Sheet = { "���㵼����" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = {  "25", "20", "20", "20", "15","15" , "15" , "15", "15", "15", "20"};

		// ������ͷ
		String[] head = {  "��������", "�����������", "�������","רԱ����","����רԱ����","��������","���б���","��ҵ�����ʸ�֤","�ʸ�֤��ʼ��","�ʸ�֤������","����ϵͳ״̬","���������ʸ�" };
		for (int i = 0; i < 12; i++) {
			setContent(0, i, head[i]);
			setCellFont(0, i, ExcelFont.ARIAL);
			setFontSize(0, i, 10);
			setFontBold(0, i, ExcelFont.Bold);
			setFontAlign(0, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(0, i, BorderLineStyle.THIN);
		}
		sSql += "	SELECT " 
				+ " N.AGENTCOMNAME ��������,"
				+ " '0'||N.TRANCOM||'-'||N.ZONENO||'-'||N.NODENO �����������,"
				+ " N.UNITCODE||'-'||N.AGENTGRADE||'-'||N.AGENTCOM �������,"
				+" N.AGENTNAME רԱ����,"
				+ " N.UNITCODE||'-'||N.AGENTCODEGRADE||'-'||N.AGENTCODE ����רԱ����,"
				+" (select ld.name from ldcom ld where ld.comcode=N.managecom) ��������,"
				+ " SUBSTR(N.MANAGECOM,7,3) ���б���,"
				+ " N.CONAGENTNO ��ҵ�����ʸ���,"
				+ " N.CONSTARTDATE �ʸ�����ʼ��,"
				+ " N.CONENDDATE �ʸ���������,"
				+ " N.STATE ����ϵͳ״̬,"
				+ " N.SALEFLAG ���������ʸ�"
				+ "  FROM NODEMAP N WHERE 1=1";

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " AND N.MANAGECOM like'" + sManageCodeNo + "%'";
		}
		if (sBankCode != null && !"".equals(sBankCode)) {
			sSql += " AND '0'||N.TRANCOM ='" + sBankCode + "'";
		}
		sSql += "AND N.TYPE !='9' ORDER BY N.MANAGECOM,N.UNITCODE,N.AGENTCOM";

		tSSRS = exeSql.execSQL(sSql);

		String[][] print = tSSRS.getAllData();

		setData(0, print, 1, 0);
		setColSize(0, colSize, 0);

		try {
			if (!createExcel()) {
				// @@������
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = "@GMRSelGatherBL020@";
				this.mErrors.addOneError(tError);
				mResult.clear();
				return false;
			} else {
				mResult.clear();
				mResult.add(filePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		// ׼������������Ϣ
		TransferData tTransferData = new TransferData();
		String filePath = "C:/Users/asus/Desktop/aa.xls";

		String sArea = "03";
		String sCity = "300";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sStartDay = "";
		String sEndDay = "";

		System.out
				.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-����,2-����,3-��������,4-����,5-����רԱ,6-���ִ���,7-��ʼ����,8-��������");
		System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-" + sArea + ",2-" + sCity
				+ ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode
				+ ",6-" + sRiskCode + ",7-" + sStartDay + ",8-" + sEndDay + "");
		System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

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
		// tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		NodeMap_PrintBL tAgent_PrintBL = new NodeMap_PrintBL();
		if (!tAgent_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tAgent_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tAgent_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}