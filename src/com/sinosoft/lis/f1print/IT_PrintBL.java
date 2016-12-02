package com.sinosoft.lis.f1print;

import jxl.format.BorderLineStyle;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.ITCreatExcel;
import com.sinosoft.midplat.common.DateUtil;

/**
 * <p>
 * ClassName: IT_PrintBL
 * </p>
 * <p>
 * Description: ����ͨIT����ʹ�����ͳ�Ʊ���
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

public class IT_PrintBL extends ITCreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";

	private String sSysFlag = "";

	private String filePath = "";

	private String sManageCom = "";
	SSRS tSSRS = new SSRS();

	SSRS tSSRS1 = new SSRS();

	public IT_PrintBL() {

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
	 * ��ȡ��UI�˴��������Ϣ ���� 
	 * 
	 * @param cInputData
	 *            VData
	 *            �����롢�����롢ϵͳ��־���ļ�·��
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.sManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.filePath = (String) mTransferData.getValueByName("filePath");
		this.sSysFlag = (String) mTransferData.getValueByName("SysFlag");
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
	 * ͨ��SQL��ȡ������Ϣ�����ҽ���ȡ����Ϣ����EXCEL
	 * @author ZHANGHJ
	 * @return boolean
	 */
	private boolean getPrintData() {

		// ����Excel�ļ������·��
		setFilePath(filePath);
		
		// ����Excel��Sheet
		String[] Sheet = { "����ʹ�����ͳ�Ʊ�" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		

		//���ñ���
		String[] title = { "����ʹ�����ͳ�Ʊ�" };
		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THICK);
		setMergeCells(1, 0, 5, 0);
		setTitle(0, title, 0, 1);
		
		//������ͷ
		String[] colSize = { "13",  "13", "13", "13", "13", "13" };
		String[] head = { "", "����",  "����ϵͳ", "����������", "��ʹ�ñ���������",
				"��ʹ�ñ���������" };
		for (int i = 1; i < 6; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.No_Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THICK);
		}
		
		
		
		//����SQL
		sSql += "	SELECT "
				+ "  N.CITYCODE ����,"
				+ "  N.SYSFLAG ����ϵͳ,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG) ����������,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG AND N1.STATUS = '1') ��ʹ�ñ���������,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG AND N1.STATUS = '0') ��ʹ�ñ���������"
				+ "  FROM LKCONTNO N WHERE 1=1";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += "AND N.CITYCODE IN (SELECT DD.AXASHORTNAME FROM LDCOM DD WHERE DD.AXASHORTNAME IS NOT NULL AND DD.comcode like '" + sManageCom + "%')";
		}
//		if (sCity != null && !"".equals(sCity)) {
//			
//			String sLDComSql = "SELECT SHORTNAME FROM LDCOM D WHERE D.CITYCODE = '" + sCity + "'";
//			String sShortName = new ExeSQL().getOneValue(sLDComSql);
//			
//			sSql += " AND TRIM(N.CITYCODE) = '" + sShortName + "'";
//		}
		if (sSysFlag != null && !"".equals(sSysFlag)) {
			sSql += " AND TRIM(N.SYSFLAG) = '" + sSysFlag + "'";
		}
		sSql += "  GROUP BY N.CITYCODE, N.SYSFLAG" + "  ORDER BY CITYCODE";

		tSSRS = exeSql.execSQL(sSql);
		
		String[][] print = tSSRS.getAllData();
		
		//�ϲ�������һ�еĵ�Ԫ��
		int z = print.length;
		setMergeCells(1, z + 2, 6, z + 2);
		setContent(z + 2, 1,
				DateUtil.getCur10Date() + "  " + DateUtil.getCur8Time());
		setCellFont(z + 2, 1, ExcelFont.ARIAL);
		setFontSize(z + 2, 1, 10);
		setFontBold(z + 2, 1, ExcelFont.No_Bold);
		setFontAlign(z + 2, 1, ExcelAlignment.LEFT);
		setData(0, print, 2, 1);
		setColSize(0, colSize, 0);
		
		try {
			if (!createExcel()) {
				// @@������
				CError tError = new CError();
				tError.moduleName = "ITPrintBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = "����������ʹ���������ʱ�����쳣!";
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
		String sStartDay = "";
		String sEndDay = "";
		String sSysFlag = "";
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
		tTransferData.setNameAndValue("SysFlag", sSysFlag);
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		// tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		IT_PrintBL tIT_PrintBL = new IT_PrintBL();
		if (!tIT_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tIT_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tIT_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}