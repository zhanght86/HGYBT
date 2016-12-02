package com.sinosoft.lis.citireport;

import jxl.format.BorderLineStyle;

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

public class ChannelAchieve extends ChannelCreateExcel {

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

	public ChannelAchieve() {

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
System.out.println(mGlobalInput);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
System.out.println("mTransferData:"+mTransferData);
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
		String[] Sheet = { "ҵ����Ӷ�𱨱�" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15",
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15", "15", "15", "15", "15", "15", "15", "15", "15", "15", 
				"15"};

		// ���ñ���
		String[] title = { "����ҵ������" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 42, 0);
		setBorderLineStyle(1, 0, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 42, 1);
		//setContent(1, 0, "��������:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 12);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// ������ͷ
		String[] head = { "����", "��������","��������", "��������", "������", "FA����", "FA���",
				"��������", "Ͷ������", "�˱�ͨ����", "��Ч����", "�ͻ��ʺ�", "Ͷ������",
				"Ͷ����", "Ͷ����֤����", "��������", "������֤����", "��Ʒ����", "��Ʒ����","�ɷѷ�ʽ",
				"�ɷ���", "����", "�������", "��������", "���ⶨͶ����", "�״�׷�ӱ���",
				"׷�ӱ��ѣ������ڣ�", "�ܱ���", "NBI",
				"����", "Ӷ��", "����״̬","��ִ��", "�˻�1����", "�˻����","�˻�2����",
				"�˻����", "�˻�3����", "�˻����","Total Fund Value", "���ݵ�������"};
		for (int i = 0; i < head.length; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
		 	setFontAlign(1, i, ExcelAlignment.CENTRE);
		    setBorderLineStyle(1, i, BorderLineStyle.THIN);
		    
		}
		sSql += "SELECT '' ����,"
				+ "   (select INSU_CODE from bankandinsumap where codetype='rlsCode' and bak4 = substr(PolicyNo,1,3) ) ��������,"//���ݱ����ŵ�ǰ��λ����Mapping
				+ "   '��������' ��������,"//û������Դ������������
				+ "   (select distinct(p.agentname) from policymaster p where p.policyno=pm.policyno and p.extracteddate=pm.extracted) ��������,"
				+ "   (select distinct(p.agentno) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) ������,"
				+ "   '' FA����,"//û������Դ������������
				+ "   '' FA���,"//û������Դ������������
				+ "   pm.PolicyNo ��������,"
				+ "   date8to10((select distinct(p.ApplicationDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) Ͷ������,"
				+ "   date8to10((select distinct(p.PolicyApprovedDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) �˱�ͨ����,"
				+ "   date8to10((select distinct(p.PolicyContractDate) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)) ��Ч����,"
				+ "   (select asm.APPLICANTACCTNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) �ͻ��ʺ�,"
				+ "   (select asm.APPLICATIONNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) Ͷ������,"
				+ "   ''  Ͷ����,"//û������Դ���ÿգ�
				+ "   (select asm.APPLICANTIDNO from axamissinginfo asm where asm.PolicyNo=pm.policyNo) Ͷ����֤����,"
				+ "   (select distinct(p.InsuredName) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) ��������,"
				+ "   (select distinct(p.InsuredIDNo) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) ������֤����,"
				+ "   pm.PlanCode ��Ʒ����,"       
				+ "   (select codename from ldcode ld where ld.codetype='citi_procode' and ld.code=pm.plancode) ��Ʒ����,"//��Ʒ����Ͳ�Ʒ���Ƶ�ӳ����Ҫ��LDCDOOE��������
				+ "   (select distinct(p.PayMode) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted)  �ɷѷ�ʽ,"
				+ "   (select distinct(p.premiumterm) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) �ɷ���,"
				+ "   pm.transactiondetial ����,"
				+ "   pm.policyyear  �������,"
				+ "   pm.modalregularpremium ��������,"
				+ "   pm.RegularTopUpPremium ���ⶨͶ����,"
				+ "   pm.InitLumpSumPremium �״�׷�ӱ���,"
				+ "   pm.LumpSumPremium ׷�ӱ��ѷ�����,"
				+ "   (pm.modalregularpremium+pm.RegularTopUpPremium+pm.InitLumpSumPremium+pm.LumpSumPremium) �ܱ���,"//�����ĸ��͡�
				+ "   (pm.modalregularpremium+(pm.RegularTopUpPremium+pm.InitLumpSumPremium+pm.LumpSumPremium)*0.1)  NBI,"//��������+�������Ѻ�������ͳ���0.1�Ļ���
				+ "   ( select distinct(p.SumInsured) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) ����,"
				+ "  (select sum(ap.Commission) from AxaPolicyTransaction ap where ap.transactionseqno = pm.transactionseqno) Ӷ��,"
				+ "  (select distinct(p.PolicyStatus) from policymaster p where p.policyno = pm.policyno and p.extracteddate=pm.extracted) ����״̬,"
				+ "  Case when ((select max(p.ReplyTargetDate) from policymaster p where p.policyno = pm.policyno)='0') then '' Else (date8to10((select max(p.ReplyTargetDate) from policymaster p where p.policyno = pm.policyno))) end ��ִ��,"
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U1ZY')  �˻�1����,"//���ﴦ��Ƚϸ��ӣ��Ժ���������
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U1ZY') �˻�1���,"//������--�ҵ�Ͷ���˻�����FundSummary�������Ĳ�ͬ�˻���Ӧ��TotalValue
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U2WJ')  �˻�2����,"
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U2WJ') �˻�2���,"
				+ "  (select tt.fundchinesename from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U3AX') �˻�3����,"
				+ "  (select tt.totalvalue from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno and tt.fundCode='U3AX') �˻�3���,"
				+ "  (select sum(tt.totalvalue) from (select s.* from Fundsummary s where s.extracteddate = (select max(a.extracteddate) from fundsummary a where a.policyno=s.policyno)) tt where tt.policyno=pm.policyno) TotalFundValue,"			
				+ "  date8to10(pm.Extracted) ���ݵ�������"
				
//				+ "	 from Axapolicytransaction pm"//policytransaction����飬Ldcodeӳ�������
//				+ "	 where pm.premiumtype='T'" 
//				+ "  and pm.policyno in (select p.policyno from policymaster p where p.policyapproveddate between "+DateUtil.date10to8(this.sStartDay)
//				+ "  and  "+DateUtil.date10to8(this.sEndDay)+")";	
				
				+ "	 from AXAPolicyTransaction pm,"
				+ "  (select distinct transactionseqno seqno, transactionsubseqno subseqno"
				+ "  from AXAPolicyTransaction"
				+ "  where commission != 0 and extracted between "+DateUtil.date10to8(this.sStartDay)
				+ " and "+DateUtil.date10to8(this.sEndDay)+") b"
				
				+ " where pm.transactionseqno = b.seqno "
                + " and pm.transactionsubseqno = b.subseqno " 
                + " and pm.premiumtype='T'";
		
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();

		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
			}	
		}

		int mergeColumns = 1;
		setData(0, print, 2, 0);
		setColSize(0, colSize, 0);
		

		
//		jxl.write.NumberFormat number  = new jxl.write.NumberFormat("#.##"); 
		
		
		//���õ�Ԫ����ֵ��
//		setDataColNumer(4,"0");//����
//		setDataColNumer(20,"defaut");
		setDataColNumer(22,"0");//�������
		
//		setDataColNumer(23,"defaut");
//		setDataColNumer(24,"defaut");
//		setDataColNumer(25,"defaut");
//		setDataColNumer(26,"defaut");
//		setDataColNumer(27,"defaut");
//		setDataColNumer(28,"defaut");
//		setDataColNumer(29,"defaut");
//		setDataColNumer(30,"defaut");
//		setDataColNumer(34,"defaut");
//		setDataColNumer(36,"defaut");
//		setDataColNumer(38,"defaut");
//		setDataColNumer(39,"defaut");

		//���õ�Ԫ�����ڸ�ʽ
//		setDataColNumer(8,"yyyy/mm/dd");
			
		
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
		System.out.println("���ð�����ʪ���ܱ��롤��");
	}
}