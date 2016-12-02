package com.sinosoft.lis.citireport;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PolicyFundSummary extends CreatExcel {

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

	public PolicyFundSummary() {

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
	 * @throws MidplatException 
	 */
	public boolean submitData(VData cInputData, String cOperate) throws MidplatException {
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
	 * @throws MidplatException 
	 */
	private boolean getPrintData() throws MidplatException {

		// ����Excel�ļ������·��
		setFilePath(filePath);
		// ����Excel��Sheet
		String[] Sheet = { "���챣��ÿ�ջ�����ܱ�" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "15", "15","15", "15", "15", "15", "15", "15", "15","15"};

		// ���ñ���
		String[] title = { "����ÿ�ջ�����ܱ�" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 10, 0);
		setBorderLineStyle(1, 10, BorderLineStyle.THIN);
//		setMergeCells(0, 1, 10, 1);
		//setContent(1, 0, "��������:" + this.sDay);
//		setCellFont(1, 0, ExcelFont.ARIAL);
//		setFontSize(1, 0, 10);
//		setFontBold(1, 0, ExcelFont.No_Bold);
//		setFontAlign(1, 0, ExcelAlignment.CENTRE);
		
		

		// ������ͷ
		String[] head = { "������","�������","��������", "����λ", "��λ��ֵ", "�����", "������",
				"������", "�Ƽ�����", "���ݵ�������"};
		for (int i = 0; i < head.length; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}
		sSql += "SELECT fs.PolicyNo ������,"
				+ "   fs.FundCode �������,"
				+ "   fs.FundChineseName ��������,"
				+ "   fs.FundUnitNo ����λ,"
				+ "   fs.CurUnitPrice ��λ��ֵ,"
				
				+ "   (select fp.bidprice from FundPriceUp fp where fp.PRICEEFFECTIVEDATE = (select max(PRICEEFFECTIVEDATE) from FundPriceUp) and fp.fundcode = fs.fundcode) �����,"
				
				+ "   fs.CurOfferPrice ������,"
				+ "   fs.TotalValue ������,"
				+ "   date8to10(fs.LastValuationDate) �Ƽ�����,"
				+ "   date8to10(fs.ExtractedDate) ���ݵ������� "
				
				+ "	 from FundSummary fs "
				
				+ "	 where fs.fundcode not in('U4YTZ','U5HL')"
				+ "  and fs.ExtractedDate between "+DateUtil.date10to8(this.sStartDay) 
				+ "  and  "+DateUtil.date10to8(this.sEndDay);
		sSql+=" Union "
			+ " select to_char(sysdate,'yyyy-mm-dd hh:mm:ss'),'','',to_number(''),to_number(''),to_number(''),to_number(''),to_number(''),'','' from FundSummary fs where fs.ExtractedDate between "+DateUtil.date10to8(this.sStartDay) 
				+ "  and  "+DateUtil.date10to8(this.sEndDay)+"order by �Ƽ�����";
		
		
		tSSRS = exeSql.execSQL(sSql);
		int num = tSSRS.getMaxRow();
		if(num>500){
			throw new MidplatException("��ѡ���ڷ�Χ�ڵ�����̫�࣬����С��ѯ��Χ!");
		}
		setTitle(title);
		String[][] print = tSSRS.getAllData();
		
//		String[][] print = new String[10][10] ;
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
			}
		}
		
		int mergeColumns = 1;
		setColorFlag("LO");
		setData(0, print, 2, 0);
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