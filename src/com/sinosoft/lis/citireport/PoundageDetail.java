package com.sinosoft.lis.citireport;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PoundageDetail extends CreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();

	private String sStartDay = "";

	private String sEndDay = "";
	
	private String filePath = "";

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public PoundageDetail() {

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

		this.sStartDay = (String) mTransferData.getValueByName("StartDay");
		this.sEndDay = (String) mTransferData.getValueByName("EndDay");
		this.filePath = (String) mTransferData.getValueByName("filePath");
//System.out.println("this.sStartDay:"+this.sStartDay);
//System.out.println("this.sEndDay:"+this.sEndDay);
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
		String[] Sheet = { "��������ϸ��" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15",
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15"};

		// ���ñ���
		String[] title = { "��������ϸ��" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 24, 0);
		setBorderLineStyle(1, 24, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 24, 1);
		//setContent(1, 0, "��������:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 12);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// ������ͷ
		String[] head1 = { "#", "DOA","Client #", " Total Premium", "Commission", "Commission", "Cap.Date",
				"Product", "Owner", "Outlet", "Sales1", "Sales2", "City",
				"Insurer", "Payment Mode", "Payment Term", "Basic Premium", "RTU", "TP-LS(1st time)","TP-LS(non-1st time)",
				"Eff.Date", "Remark", "Policy #", "Insured"};
		for (int i = 0; i < head1.length; i++) {
			setContent(1, i, head1[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}
		
		String[] head2 = { "", "�ռ�����","�ͻ����", "�ܱ���", "Ӷ�����գ�", "Ӷ��Ͷ����", "Ͷ������",
				"��Ʒ����", "Ͷ����", "֧������", "������Ա1", "������Ա2", "��������",
				"���չ�˾����", "�ɷѷ�ʽ", "�ɷ���", "��������", "���ⶨͶ����", "�״�׷�ӱ���","׷�ӱ��ѣ������ڣ�",
				"��Ч����", "����", "��������", "��������"};
		for (int i = 0; i < head2.length; i++) {
			setContent(2, i, head2[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		
//System.out.println("Head2"+head2.length);	
		
		sSql += "SELECT ''  ���,"//������ҲҪ�ÿգ������ѯ����
				+ "   ''  �ռ�����,"//���ֵҪ�ÿգ���Tonnyȷ�Ϲ���
				+ "   (select am.CustomerNo from axaMissingInfo am where am.policyno = a.PolicyNo) �ͻ����,"
				+ "   a.TransactionAmount �ܱ���,"
				+ "   case when a.premiumtype in ('I', 'R', 'E') then a.commission else 0 end  Ӷ������,"
				+ "   case when a.premiumtype='V' then a.commission else 0 end Ӷ��Ͷ��,"
				+ "   date8to10((select distinct(p.ApplicationDate) from policymaster p where p.policyno=a.policyno)) Ͷ������,"
				+ "   (select codename from ldcode ld where ld.codetype='citi_procode' and ld.code=a.plancode) ��Ʒ����,"//��Ҫӳ�䣬Ŀǰû��������LDCODE�ﴦ��
				+ "   '' Ͷ����,"//�������Ҫ�ÿգ�û������Դ�������ѯ����
				+ "   (select distinct(p.AgentName) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) ֧������,"
				+ "   '' ������Ա1,"
				+ "   '' ������Ա2,"
				+ "   '' ��������,"//���������������ֵĿǰ��ûȡ�����Ժ���
				+ "   'AXA' ���չ�˾����,"
				+ "   (select distinct(p.paymentmethod) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) �ɷѷ�ʽ,"
				+ "   (select distinct(p.premiumterm) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) �ɷ���,"
				+ "   (select distinct(p.ModalPremium) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) ��������,"
				+ "   a.RegularTopUpPremium  ���ⶨͶ����,"       
				+ "   a.InitLumpSumPremium �״�׷�ӱ���,"
				+ "   a.LumpSumPremium ׷�ӱ��ѷ�����,"
				+ "   date8to10((select distinct(p.PolicyContractDate) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted)) ��Ч����,"
				+ "   a.transactiondetial ����,"//��������Ҫӳ�䣬Ŀǰ��û�д���
				+ "   a.PolicyNo ��������,"
				+ "   (select distinct(p.InsuredName) from policymaster p where p.policyno=a.policyno and p.extracteddate=a.extracted) ��������"

				+ "	 from AxaPolicyTransaction a"		
				+ "	 where a.commission!=0.0 and a.extracted between "+DateUtil.date10to8(this.sStartDay)
				+ "	 and  "+DateUtil.date10to8(this.sEndDay);
//System.out.println("sSql:"+sSql);
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();

//		String[][] print = new String[25][25];
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
				print[i][0]=new String("")+(i+1);//��һ�м�����1,2,3������
		
			}
		}

		int mergeColumns = 1;
		setColorFlag("LO");
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
		
	}
}