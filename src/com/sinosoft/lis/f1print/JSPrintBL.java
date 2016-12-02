package com.sinosoft.lis.f1print;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.print.MatrixPrint;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelBorderLineStyle;
import com.sinosoft.lis.excel.ExcelFont;

/**
 * <p>
 * ClassName: FeePrintBL
 * </p>
 * <p>
 * Description: ����ҵ���������ߴ�ӡ��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: BASS
 * @CreateDate��2009-09
 * @ReWriteDate:
 */

public class JSPrintBL extends CreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();



	private String ManageCom = ""; 

	private String BankCode = ""; 

	private String AgentCom = ""; 

	private String RiskCode = ""; 

	private String StartDate = ""; 
	
	private String EndDate = ""; 
	
	private String filePath = ""; 

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public JSPrintBL() {

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
		
		this.ManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.BankCode = (String) mTransferData.getValueByName("BankCode");
		this.AgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.RiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.StartDate = (String) mTransferData.getValueByName("StartDate");
		this.EndDate = (String) mTransferData.getValueByName("EndDate");
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
 * @author JiaoYF
 * @return boolean
 */
private boolean getPrintData() {
	setFilePath(filePath);
	
	String[] Sheet = { "��ʤ����" };
	setSheet(Sheet);
	ExeSQL exeSql = new ExeSQL();
	String feesql = "";
	
			
			String[] colSize = {  "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18","18", "18", "18","18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18", "18" };
			//���ñ���
			
			setMergeCells(0, 0, 42, 0);
			setTitleBold(0, ExcelFont.Bold);
			setTitleFont(0, ExcelFont.ARIAL);
			setTitleFontSize(0, 12);
			setTitleAlign(0, ExcelAlignment.LEFT);
			setTitleBorder(0, ExcelBorder.ALL);
			
			setMergeCells(0,1,42,1);
			setContent(1,0,"��������:"+PubFun.getCurrentDate());
			setCellFont(1,0,ExcelFont.ARIAL);
			setFontSize(1,0,12);
			setFontBold(1,0,ExcelFont.Bold);
			setFontAlign(1,0,ExcelAlignment.LEFT);
			
			//������ͷ
			String[] head = { "�������","����״̬","�������", "������","��������","��������", "��Ʒ����",
					"�����˱��", "��������", "�������Ա�","�����˳�������", "���������֤��","����������֤����","�������ʱ�", "�����˵�ַ", "�����˵绰", "�������ֻ�", "������ְҵ����", "Ͷ����", "Ͷ�����Ա�", "Ͷ���˳�������", "Ͷ�������֤��", "Ͷ��������֤��", "Ͷ�����ʱ�", "Ͷ���˵�ַ", "Ͷ���˵绰", "Ͷ�����ֻ�", "��Ʒ����", "����", "��������", "�ɷѷ�ʽ", "��������", "���ջ������շ�", "�ڽ�׷�ӱ��շ�", "����׷�ӱ��շ�", "�����ձ��շ�", "���շѺϼ�", "Ͷ���˻�", "Ͷ���˻�����", "Ͷ������", "ת���ʺ�", "�˻�������", "����ʱ��"};
			for(int i = 0; i < 43; i++){
				setContent(2, i, head[i]);
				setCellFont(2, i, ExcelFont.ARIAL);
				setFontSize(2, i, 10);
				setFontBold(2, i, ExcelFont.Bold);
				setFontAlign(2, i, ExcelAlignment.CENTRE);
			}
			feesql += "select trim(c.contno) �������, "
       +" CASE"
       +" WHEN c.appflag = 'B' or c.appflag = '1' THEN"
       +"   '������Ч'"
       +"  WHEN c.appflag = '0' AND (c.state = 'C' or c.state = 'B') THEN "
       +"   'Э����ֹ' "
       +" ELSE "
       +"  '--' "
       +" END ����״̬, "
       +" (select '0' || n.trancom || '-' || n.zoneno || '-' || n.nodeno "
       +"    from nodemap n "
       +"   where trim(c.agentcom) = n.agentcom "
       +"     and trim(c.bankcode) = '0' || n.trancom) �������, "
       +" trim(c.agentcom) ������, "
       +" (select a.agentcomname "
       +"   from agent a "
       +"   where a.agentcom = trim(c.agentcom)) ��������, "
       +" to_char(c.makedate, 'YYYY-mm-dd') || ' ' || c.maketime ��������, "
       +" p.riskcode ��Ʒ����, "
       +" trim(c.agentcode) �����˱��, "
       +" i.name ��������, "
       +"  CASE "
       +"   WHEN i.sex = '0' THEN "
       +"    '��' "
       +"   WHEN i.sex = '1' THEN "
       +"   'Ů' "
       +"  ELSE "
       +"    '����' "
       +" END �������Ա�, "
       +" to_char(i.birthday, 'yyyy-mm-dd') �����˳�������, "
       +" CASE "
       +"   WHEN i.idtype = '0' THEN "
       +"    i.idno "
       +"   ELSE "
       +"   '' "
       +" END ���������֤��, "
       +"  CASE "
       +"   WHEN i.idtype != '0' THEN "
       +"   i.idno "
       +"  ELSE "
       +"    '' "
       +" END ����������֤����, "
       +" d.zipcode �������ʱ�, "
       +" d.postaladdress �����˵�ַ, "
       +" d.homephone �����˵绰, "
       +" d.phone �������ֻ�, "
       +" i.occupationcode ������ְҵ����, "
       +" (select r.riskname FROM lmriskapp r where p.riskcode = r.riskcode) ��Ʒ����, "
       +"  p.amnt ����, "
       +" p.insuyear ��������, "
       +" p.payendyear �ɷ�����, "
       +" p.prem ���ջ������շ�, "
       +" p.riskprem �ڽ�׷�ӱ��շ�, "
       +" p.firstaddprem ����׷�ӱ��շ�, "
       +" p.standprem ���ӱ��շ�, "
       +" p.sumprem �ϼƱ��շ�, "
       +" c.bankaccno ת���˺�, "
       +" c.bankaccno �˻�������, "
       +" to_char(sysdate, 'yyyy-mm-dd hh:mm:ss') ����ʱ�� "
       +" from lccont c, lcpol p, lcinsured i, lcaddress d "
       +" where c.contno = p.contno "
       +" and p.riskcode = p.kindcode "
       +" and c.contno = i.contno "
       +" and i.insuredno = d.customerno "
       +" and i.addressno = d.addressno "
       +" and c.managecom like '8603%' "
       +" and c.bankcode = '012' "
       +" and c.agentcom = '2949' "
       +" and p.riskcode = 'PAC' "
       +" AND P.MAKEDATE > DATE '2011-01-01' "
       +" AND P.MAKEDATE < DATE '2011-11-01' ";

			String[] title = { "����ͨÿ��ʵʱ���ݵ�����" };
			setTitle(title);
			tSSRS = exeSql.execSQL(feesql);
			
			String[][] print = tSSRS.getAllData();
			int mergeColumns=1;
//			setDateByFixArrays(0, print, 4, 0, mergeColumns,"�ϼ�;ȫ��");
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
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return true;
	}
}