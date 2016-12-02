package com.sinosoft.lis.f1print;

import jxl.format.Colour;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.print.MatrixPrint;
import com.sinosoft.lis.excel.BCCreatExcel;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelBorderLineStyle;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.midplat.common.DateUtil;

/**
 * <p>
 * ClassName: BC_PrintBL
 * </p>
 * <p>
 * Description: ����ͨÿ��ʵʱ���ݵ�����
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

public class BC_PrintBL extends BCCreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";

	private String sTranCom = "";

	private String sAgentCom = "";

	private String sAgentCode = "";

	private String sRiskCode = "";

	private String sStartDay = "";

	private String sEndDay = "";

	private String filePath = "";
	
	private String sManageCodeNo="";

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public BC_PrintBL() {

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
		this.sManageCodeNo = (String) mTransferData.getValueByName("ManageCodeNo");
		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.sTranCom = (String) mTransferData.getValueByName("TranCom");
		this.sAgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.sAgentCode = (String) mTransferData.getValueByName("AgentCode");
		this.sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.sStartDay = (String) mTransferData.getValueByName("StartDay");
		this.sEndDay = (String) mTransferData.getValueByName("EndDay");
		this.filePath = (String) mTransferData.getValueByName("filePath");

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
	 * @author JiaoYF
	 * @return boolean
	 */
	private boolean getPrintData() {

		// ����Excel�ļ������·��
		setFilePath(filePath);
		// ����Excel��Sheet
		String[] Sheet = { "����ͨҵ����ϸ����" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "10", "10", "10", "9", "9", "10", "9", "10",
				"10", "10", "10", "10", "10", "10", "10", "10", "10"};

		// ���ñ���
		String[] title = { "����ͨҵ����ϸ����" };
		setMergeCells(0, 0, 16, 0);
		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 16);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
	
		
		
		setMergeCells(0, 1, 3, 1);
		setContent(1, 0, "�������������ԣ�");
		setCellFont(1, 0, ExcelFont.ARIAL);
		setFontSize(1, 0, 12);
		setFontBold(1, 0, ExcelFont.No_Bold);
		setFontAlign(1, 0, ExcelAlignment.LEFT);
		
		setMergeCells(4, 1, 7, 1);
		setContent(1, 4, this.sStartDay);
		setCellFont(1, 4, ExcelFont.ARIAL);
		setFontSize(1, 4, 12);
		setFontBold(1, 4, ExcelFont.No_Bold);
		setFontAlign(1, 4, ExcelAlignment.LEFT);

		setMergeCells(8, 1, 11, 1);
		setContent(1, 8, "������������ֹ��");
		setCellFont(1, 8, ExcelFont.ARIAL);
		setFontSize(1, 8, 12);
		setFontBold(1, 8, ExcelFont.No_Bold);
		setFontAlign(1, 8, ExcelAlignment.LEFT);
		

		setMergeCells(12, 1, 16, 1);
		setContent(1, 12, this.sEndDay);
		setCellFont(1, 12, ExcelFont.ARIAL);
		setFontSize(1, 12, 12);
		setFontBold(1, 12, ExcelFont.No_Bold);
		setFontAlign(1, 12, ExcelAlignment.LEFT);

		for(int i=0;i<=16;i++){
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}

		
		// ������ͷ
		String[] head = { "�������", "����", "�������", "������","��������", "FA����",
				"FA����", "�������", "����", "��������", "�˱�����", "���ջ������շ�",
				"�ڽ�׷�ӱ��շ�", "����׷�ӱ��շ�", "�����ձ��շ�", "�ܱ���"};
		for (int i = 0; i < 16; i++) {
			setContent(2, i, head[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		 sSql += "	SELECT  "
		       + "  D.NAME �������,"
		       + "  CASE WHEN c.bankcode = '011' THEN 'ICBC' WHEN c.bankcode = '012' THEN 'CGB' ELSE '--' END ����,"
		       + "  TRIM(C.AXANODEMAP)  ������,"
			   + "  TRIM(C.AXAAGENTCOM) ���,"
			   + "  C.AGENTCOMNAME ��������,"
			   + "  TRIM(C.AXAAGENTCODE) �����˱��,"
		       + "  C.AGENTNAME FA����,"
		       + "  TRIM(C.CONTNO) �������,"
		       + "  P.RISKALIAS ����,"
		       + "  TO_CHAR(C.MODIFYDATE,'YYYY-MM-DD')||' '||C.MODIFYTIME ��������,"
		       + "  TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME �˱�����,"
		       + "  C.MAINPOLPREM ���ջ������շ�,"
		       + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0) �ڽ�׷�ӱ��շ�,"
		       + "  (select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual) ����׷�ӱ��շ�,"
		       + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)','NHYrider(lpsm)','HYG3rider(lpsm)') THEN 0 ELSE sp.prem END FROM lcpol sp WHERE (sp.polno=c.contno||'-1')),0)  ���ӱ��շ�,"
		       + "  C.PREM �ܱ���" 
		  	   + " FROM LCCONT C, LCPOL P ,LDCOM D"
		 	   + " WHERE "
		       + " TRIM(C.MANAGECOM) = D.COMCODE"
		       + " AND C.CONTNO||'-0' = P.POLNO"
		       + " AND C.APPFLAG IN ('1','B')"
		       + " AND C.UWFLAG = '9'";
		       

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " and c.managecom like '" + sManageCodeNo + "%' ";
		}
		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sStartDay != null && !"".equals(sStartDay)) {
			sSql += "  AND p.MAKEDATE >= DATE'" + sStartDay + "' ";
		}
		if (sEndDay != null && !"".equals(sEndDay)) {
			sSql += " AND p.MAKEDATE <= DATE'" + sEndDay + "' ";
		}
	

		sSql += " UNION "

		     + " SELECT "
		     + " '�ܼ�:��'||count(D.COMCODE)||'��',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " SUM(C.MAINPOLPREM),"
		     + " SUM(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0)),"
		     + " SUM((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual)),"
		     + " SUM(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)','NHYrider(lpsm)','HYG3rider(lpsm)') THEN 0 ELSE sp.prem END FROM lcpol sp WHERE (sp.polno=c.contno||'-1')),0)),"
		     + " SUM(C.PREM)"
		       + " FROM LCCONT C, LCPOL P ,LDCOM D"
		 	   + " WHERE "
		       + " TRIM(C.MANAGECOM) = D.COMCODE"
		       + " AND C.CONTNO||'-0' = P.POLNO"
		       + " AND C.APPFLAG IN ('1','B')"
		       + " AND C.UWFLAG = '9'";
		    

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " and c.managecom like '" + sManageCodeNo + "%' ";
		}
		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sStartDay != null && !"".equals(sStartDay)) {
			sSql += "  AND p.MAKEDATE >= DATE'" + sStartDay + "' ";
		}
		if (sEndDay != null && !"".equals(sEndDay)) {
			sSql += " AND p.MAKEDATE <= DATE'" + sEndDay + "' ";
		}
		sSql+= " order by  ��������";
		
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();


		
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
			//	System.out.println(print[i][j]);
				if (print[i][j] == null || "null".equals(print[i][j])) {
				//	System.out.println("aaa");
					print[i][j] = "";

				}

			}

		}

		int mergeColumns = 1;
		// setDateByFixArrays(0, print, 4, 0, mergeColumns,"�ϼ�;ȫ��");
		//setColorFlag("LO");
		setData(0, print, 3, 0);
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
				mResult.add(gettFile());
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

		String sArea = "";
		String sCity = "";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sStartDay = "2011-08-01";
		String sEndDay = "2012-09-10";

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
		BC_PrintBL tBC_PrintBL = new BC_PrintBL();
		if (!tBC_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tBC_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tBC_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}