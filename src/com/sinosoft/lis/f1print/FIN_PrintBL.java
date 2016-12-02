package com.sinosoft.lis.f1print;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.FINCreatExcel;

/**
 * <p>
 * ClassName: FIN_PrintBL
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

public class FIN_PrintBL extends FINCreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();

	private String sTranCom = "";

	private String sAgentCom = "";

	private String sAgentCode = "";

	private String sRiskCode = "";
	
	private String sStartDay = "";
	private String sEndDay = "";
	private String sStartHour = "";
	private String sEndHour = "";

	private String filePath = "";
	
	private String sContStatue = "";
	
	private String sManageCom = "";
	
	public String sContStatueSql = " ";
	
	public static DecimalFormat df=new DecimalFormat("#################0.00");

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public FIN_PrintBL() {

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


		this.sTranCom = (String) mTransferData.getValueByName("TranCom");
		this.sAgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.sAgentCode = (String) mTransferData.getValueByName("AgentCode");
		this.sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.sStartDay = ((String)this.mTransferData.getValueByName("StartDay"));
		this.sEndDay = ((String)this.mTransferData.getValueByName("EndDay"));
		this.sStartHour = ((String)this.mTransferData.getValueByName("StartHour"));
		this.sEndHour = ((String)this.mTransferData.getValueByName("EndHour"));
		this.filePath = (String) mTransferData.getValueByName("filePath");
		this.sContStatue =(String) mTransferData.getValueByName("ContStatue"); 
		this.sManageCom =(String) mTransferData.getValueByName("ManageCodeNo"); 
//		this.sDay = (String) mTransferData.getValueByName("Day"); 
		this.sStartDay = this.sStartDay + " " + this.sStartHour;
	    this.sEndDay = this.sEndDay + " " + this.sEndHour;
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
		
		double acodePrem1 = 0;
		double acodePrem2 = 0;
		double acodePrem3 = 0;
		double acodePrem4 = 0;
		double acodePrem5 = 0;

		// ����Excel�ļ������·��
		setFilePath(filePath);
		// ����Excel��Sheet
		String[] Sheet = { "����ͨÿ��������ϸ����" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "10", "10", "10", "10", "9", "9", "10", "10",
				"10", "10", "10", "10", "11", "12", "12"};

		// ���ñ���
		String[] title = { "����ͨÿ��������ϸ����" };
		for(int i=0;i<=13;i++){
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}
		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		
		//System.out.println("aaaa" + getFontSize(0, 0, 0));
		setMergeCells(0, 0, 14, 0);
		setBorderLineStyle(0, 0, BorderLineStyle.THIN);
		
		
		setMergeCells(0, 1, 1, 1);
	    setContent(1, 0, "��ʼ���ڣ�");
	    setCellFont(1, 0, ExcelFont.ARIAL);
	    setFontSize(1, 0, 12);
	    setFontBold(1, 0, "No_Bold");
	    setFontAlign(1, 0, ExcelAlignment.LEFT);
	    setBorderLineStyle(1, 0, BorderLineStyle.THIN);

	    setMergeCells(2, 1, 3, 1);
	    setContent(1, 2, this.sStartDay);
	    setCellFont(1, 2, ExcelFont.ARIAL);
	    setFontSize(1, 2, 12);
	    setFontBold(1, 2, "No_Bold");
	    setFontAlign(1, 2, ExcelAlignment.LEFT);

	    setMergeCells(4, 1, 5, 1);
	    setContent(1, 4, "�������ڣ�");
	    setCellFont(1, 4, ExcelFont.ARIAL);
	    setFontSize(1, 4, 12);
	    setFontBold(1, 4, "No_Bold");
	    setFontAlign(1, 4, ExcelAlignment.LEFT);
	    setBorderLineStyle(1, 4, BorderLineStyle.THIN);

	    setMergeCells(6, 1, 7, 1);
	    setContent(1, 6, this.sEndDay);
	    setCellFont(1, 6, ExcelFont.ARIAL);
	    setFontSize(1, 6, 12);
	    setFontBold(1, 6, "No_Bold");
	    setFontAlign(1, 6, ExcelAlignment.LEFT);
	    String title2 = "";
		

		
		if(sContStatue==null || "".equals(sContStatue)){
			title2 = "***����״̬***";
			sContStatueSql = "  (C.NOREALFLAG = 'N' AND C.APPFLAG in ('B','1','0') AND C.UWFLAG IN ('9','a'))";
		}else if(sContStatue!=null && "9".equals(sContStatue)){
			title2 = "***������Ч***";
			sContStatueSql = "  (C.NOREALFLAG = 'N' AND (C.APPFLAG in ('B','1') AND C.UWFLAG = '9'))";
		}
		else if(sContStatue!=null && "a".equals(sContStatue)){
			title2 = "***Э����ֹ***";
			sContStatueSql = "  (C.NOREALFLAG = 'N' AND  (C.APPFLAG = '0' AND C.UWFLAG = 'a' AND  C.STATE in ('B','C'))) ";
		}
		
		StringBuffer sSBSql = new StringBuffer();
		
		sSBSql.append(sContStatueSql);
		
		if(this.sTranCom != null && !"".equals(this.sTranCom)){
			sSBSql.append(" AND TRIM(C.BANKCODE)="+this.sTranCom );
		}
		 sSBSql.append(" AND TRIM(C.MANAGECOM) LIKE '"+this.sManageCom +"%' " );
		 sSBSql.append(" AND (TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME) >= '" + this.sStartDay + "' AND  (TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME) <= '" + this.sEndDay + "' ");

		StringBuffer sSBSql2 = new StringBuffer();
		sSBSql2.append(sSBSql.toString());
		sSBSql.append(" GROUP BY C.MANAGECOM,C.AGENTCODE ");
		setMergeCells(8, 1, 11, 1);
		setContent(1, 8, "����״̬��");
		setCellFont(1, 8, ExcelFont.ARIAL);
		setFontSize(1, 8, 12);
		setFontBold(1, 8, ExcelFont.No_Bold);
		setFontAlign(1, 8, ExcelAlignment.LEFT);
		

		setMergeCells(12, 1, 14, 1);
		setContent(1, 12, title2);
		setCellFont(1, 12, ExcelFont.ARIAL);
		setFontSize(1, 12, 12);
		setFontBold(1, 12, ExcelFont.No_Bold);
		setFontAlign(1, 12, ExcelAlignment.LEFT);

		for(int i=0;i<=13;i++){
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}

		setMergeCells(0, 2, 10, 2);
		setContent(2, 0, "������ÿ��������ϸ");
		setCellFont(2, 0, ExcelFont.ARIAL);
		setFontSize(2, 0, 10);
		setFontBold(2, 0, ExcelFont.No_Bold);
		setFontAlign(2, 0, ExcelAlignment.LEFT);
		
		
		setMergeCells(11, 2, 14, 2);
		setContent(2, 11, "");
		setCellFont(2, 4, ExcelFont.ARIAL);
		setFontSize(2, 4, 12);
		setFontBold(2, 4, ExcelFont.No_Bold);
		setFontAlign(2, 4, ExcelAlignment.LEFT);
		setBorderLineStyle(2, 0, BorderLineStyle.THIN);
		setBorderLineStyle(2, 1, BorderLineStyle.THIN);
		
		
		

		// ������ͷ
		String[] head = { "������ˮ��", "�������", "���ִ���", "���ѽ��", "������", "�����˱��",
				"��������", "����", "����", "����", "����״̬", "���ձ���",
				"�����ձ���", "�ڽ�׷�ӱ��շ�", "����׷�ӱ��շ�"};
		for (int i = 0; i < 15; i++) {
			setContent(3, i, head[i]);
			setCellFont(3, i, ExcelFont.ARIAL);
			setFontSize(3, i, 10);
			setFontBold(3, i, ExcelFont.Bold);
			setFontAlign(3, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(3, i, BorderLineStyle.THIN);
		}
		
		
		
		String nAgentCodeSql = "";
		 nAgentCodeSql += "SELECT C.AGENTCODE,C.MANAGECOM" 
			+ "  FROM LCCONT C"
			 + "  WHERE "
			 + sSBSql;
		// System.out.println(nAgentCodeSql);
		 List array = new ArrayList();
		SSRS tSSRS2 = exeSql.execSQL(nAgentCodeSql);
			String[][] AgentCodeS = tSSRS2.getAllData();
			
			for (int i =0; i<AgentCodeS.length; i++){
			//System.out.println(AgentCodeS[i][0]);
			
			sSql = getSql(AgentCodeS[i][0],AgentCodeS[i][1],sSBSql2.toString());
			SSRS tSSRST = exeSql.execSQL(sSql);
			String[][] sss = tSSRST.getAllData();
			for (int a = 0; a < sss.length; a++) {
				for (int j = 0; j < sss[a].length; j++) {
					//System.out.println(sss[a][j]);
					if (sss[a][j] == null || "null".equals(sss[a][j])) {
						if(j== 12 ||j== 13 || j ==14 || j==15){
						sss[a][j] = "0";
						}
						else{
							sss[a][j] = "";
						}
					}
					
				}
			}
			try {
				
			
			for (int a = 0; a < sss.length; a++) {
				for (int j = 0; j < sss[a].length; j++) {
					if(sss[a][j].toString().equals("�����˱��:")){
						sss[a][j+1] = sss[a-1][5];
						acodePrem1 += Double.valueOf(sss[a][j+3]);
						acodePrem2 += Double.valueOf(sss[a][j+11]);
						acodePrem3 += Double.valueOf(sss[a][j+12]);
						acodePrem4 += Double.valueOf(sss[a][j+13]);
						acodePrem5 += Double.valueOf(sss[a][j+14]);
					}
				}
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
			array.add(sss);
			}
			//System.out.println("array.size():"+array.size());
			
			int z = 0;
			SSRS tSSRS4 = new SSRS();
			
			for(int i =0;i<array.size();i++){
				String[][] tSSRS5 = (String[][]) array.get(i);
				
				//System.out.println("tSSRS5:"+tSSRS5.length);
				for(int j=0;j<tSSRS5.length;j++){
					z++;
				}
			}
			//System.out.println("�������:"+z);
			String[][] ssrs = new String[z+1][15];
			
			int zz =0;
		for(int i =0;i<array.size();i++){
			String[][] tSSRS5 = (String[][]) array.get(i);
			
			//System.out.println("tSSRS5:"+tSSRS5.length);
			for(int j=0;j<tSSRS5.length;j++){
				//System.out.println("j:"+j);
				//System.out.println("ZZ:"+zz);
				ssrs[zz] = tSSRS5[j];
				zz++;
			}
		}

		String [] aSum =new String [15];
		aSum[0] = "�ܼƣ�";aSum[1] = "";aSum[2] = "";aSum[3] = df.format(acodePrem1);aSum[4] = "";
		aSum[5] = "";aSum[6] = "";aSum[7] = "";aSum[8] = "";aSum[9] = "";
		aSum[10] = "";aSum[11] = df.format(acodePrem2);aSum[12] = df.format(acodePrem3);aSum[13] = df.format(acodePrem4);aSum[14] = df.format(acodePrem5);
		
		ssrs[zz] = aSum;
		
		ArrayList arrayList2 = new ArrayList();
		String [] anull =new String [15];
		for(int i = 0;i<anull.length;i++){
			anull[i] = "";
		}
		arrayList2.add(anull);
		
		
		for (int a = 0; a < ssrs.length; a++) {
			arrayList2.add(ssrs[a]);
			for (int j = 0; j < ssrs[a].length; j++) {
				if(ssrs[a][j].toString().equals("�����˱��:")){
					arrayList2.add(anull);
				}
			}
		}
		

		
		

		String[][] ssrs22 = new String[arrayList2.size()+1][15];
		for (int a = 0; a < ssrs22.length-2; a++) {
			ssrs22[a] = (String[]) arrayList2.get(a);
			}
		ssrs22[arrayList2.size()-2] = aSum;
		
		String [] anull2 =new String [15];
		anull2 [0] = "test1";
		for(int i = 2;i<anull2.length;i++){
			anull2[i] = "";
		}
		String [] anull3 =new String [15];
		anull3 [0] = "test2";
		for(int i = 2;i<anull3.length;i++){
			anull3[i] = "";
		}
		ssrs22[arrayList2.size()-1] = anull2;
		ssrs22[arrayList2.size()] = anull3;
		
		
		 String[][] scom = getString1(this.sTranCom, this.sManageCom,  this.sStartDay, this.sEndDay, this.sContStatueSql, this.sStartHour, this.sEndHour);
	
		String [][] sssprint = new String [scom.length + ssrs22.length][];
		
		
		ArrayList arrayList3 = new ArrayList();
		for (int a = 0; a < ssrs22.length; a++) {
			arrayList3.add(ssrs22[a]);
		}
		for (int a = 0; a < scom.length; a++) {
			arrayList3.add(scom[a]);
		}
		
		for (int a = 0; a < arrayList3.size(); a++) {
			sssprint[a] = (String[]) arrayList3.get(a);
			}
		
		String[][] print = sssprint;
		
		setTitle(title);
		//System.out.println(print.length);
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
				if (print[i][j] == null || "null".equals(print[i][j])) {
					print[i][j] = "";
				}
			}
		}
		
		String [][] sprint = null;
		for (int i = 0; i < print.length; i++) {
			
		}
		
			

		int mergeColumns = 1;
		setData(0, print, 4, 0);
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
	
	
	
	public static String getSql (String agentcode,String managecom,String sContStatueSql){
		String sSql = "";
		sSql += " SELECT "
		       
			 + "  C.PROPOSALCONTNO ������ˮ��,"
			 + "  C.CONTNO �������,"
			 + "  P.RISKALIAS ���ִ���,"
			 + "  C.PREM ���ѽ��,"
			 + "  TRIM(C.AXAAGENTCOM)  ������,"
			 + "  TRIM(C.AXAAGENTCODE) �����˱��,"
			 + "  C.AGENTCOMNAME ��������,"
			 + "  (SELECT DD.NAME FROM LDCOM DD WHERE DD.COMCODE = TRIM(C.MANAGECOM)) ����,"
			 + "  (SELECT OTHERSIGN FROM LDCODE LD WHERE LD.CODETYPE='trancom_bank' AND LD.CODE = TRIM(C.BANKCODE)) ����,"
			 + "  (SELECT DD.NAME FROM LDCOM DD WHERE DD.COMCODE = substr(TRIM(C.MANAGECOM),0,4)) ����,"
			 + "  CASE WHEN C.APPFLAG in ('B','1') AND C.UWFLAG='9' THEN  '������Ч' WHEN C.APPFLAG = '0' AND (C.STATE IN ('C','B')) THEN  'Э����ֹ'  ELSE  '--' END ����״̬,"
			 + "  C.MAINPOLPREM ���ջ������շ�,"
			 + "  (select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����ձ��շ�,"
			 + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1 AND sp.riskcode in ('NHYrider(RTU)', 'HYG3rider(RTU)')),0) �ڽ�׷�ӱ��շ�,"
			 +  " (select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1 and sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)')),0) from dual) ����׷�ӱ��շ�"
			 + "  FROM LCCONT C, LCPOL P "
			 + "  WHERE "
			 + "   C.CONTNO||'-0' = P.polno"
			 + "  AND " + sContStatueSql
			 + "  AND C.MANAGECOM = '" + managecom + "' "
			 + " AND C.AGENTCODE = '" + agentcode + "' ";
			 
			
		
		sSql +=" UNION "

			+ "  SELECT "

			+ "  '�����˱��:',"
			+ "  '',"
			+ "  '����С��:',"
			+ "  SUM(C.PREM),"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  SUM(C.MAINPOLPREM),"
			+ "  sum((select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3'))),"
			+ "  sum(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1 AND sp.riskcode in ('NHYrider(RTU)', 'HYG3rider(RTU)')),0)),"
			 + " sum((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1 AND sp.payendyear=1 and sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)')),0) from dual)) "
			 + "  FROM LCCONT C, LCPOL P "
			 + "  WHERE "
			 + "   C.CONTNO||'-0' = P.polno"
			 + "  AND " + sContStatueSql
			 + "  AND C.MANAGECOM = '" + managecom + "' "
			 + " AND C.AGENTCODE = '" + agentcode + "' ";


		System.out.println(sSql);
		return sSql;
		
	}
	
	
	public static String getSql2 (String agentCom,String managecom,String sContStatueSql){
		String sSql = "";
		sSql += " SELECT "
		       
			 + "  C.PROPOSALCONTNO ������ˮ��,"
			 + "  C.CONTNO �������,"
			 + "  P.RISKALIAS ���ִ���,"
			 + "  C.PREM ���ѽ��,"
			 +  "  TRIM(C.AXAAGENTCOM)  ������,"
			 + "  TRIM(C.AXAAGENTCODE) �����˱��,"
			 + "  C.AGENTCOMNAME ��������,"
			 + "  (SELECT DD.NAME FROM LDCOM DD WHERE DD.COMCODE = TRIM(C.MANAGECOM)) ����,"
			 + "  (SELECT OTHERSIGN FROM LDCODE LD WHERE LD.CODETYPE='trancom_bank' AND LD.CODE = C.BANKCODE) ����,"
			 + "  (SELECT DD.NAME FROM LDCOM DD WHERE DD.COMCODE = substr(TRIM(C.MANAGECOM),0,4)) ����,"
			 + "  CASE WHEN C.APPFLAG in ('B','1') AND C.UWFLAG='9' THEN  '������Ч' WHEN C.APPFLAG = '0' AND (C.STATE IN ('C','B')) THEN  'Э����ֹ'  ELSE  '--' END ����״̬,"
			 + "  C.MAINPOLPREM ���ջ������շ�,"
			 + "  (select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����ձ��շ�,"
			 + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0) �ڽ�׷�ӱ��շ�,"
			 +  " (select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual) ����׷�ӱ��շ�"
			 + "  FROM LCCONT C, LCPOL P "
			 + "  WHERE "
			 + "   C.CONTNO||'-0' = P.polno"
			 + "  AND " + sContStatueSql
			 + "  AND C.MANAGECOM = '" + managecom + "' "
		     + " AND C.AGENTCOM = '" + agentCom + "' ";

		
		sSql +=" UNION "

			+ "  SELECT "

			+ "  '��������:',"
			+ "  '',"
			+ "  '����С��:',"
			+ "  SUM(C.PREM),"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  '',"
			+ "  SUM(C.MAINPOLPREM),"
			+ "  sum((select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3'))),"
			+ "  sum(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0)),"
			 + " sum((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual)) "
			 + "  FROM LCCONT C, LCPOL P "
			 + "  WHERE "
			 + "   C.CONTNO||'-0' = P.polno"
			 + "  AND " + sContStatueSql
			 + "  AND C.MANAGECOM = '" + managecom + "' "
		     + " AND C.AGENTCOM = '" + agentCom + "' ";


		return sSql;
		
	}	

	  public static String[][] getString1(String sTranCom, String tManageCom , String sStartDay, String sEndDay, String sContStatueSql, String sStartHour, String sEndHour)
	  {

		StringBuffer sSBSql = new StringBuffer();
		
		sSBSql.append(sContStatueSql);
		
		if(sTranCom != null && !"".equals(sTranCom)){
			sSBSql.append(" AND TRIM(C.BANKCODE)="+sTranCom );
		}
		 sSBSql.append(" AND TRIM(C.MANAGECOM) LIKE '"+tManageCom +"%' " );

	    sSBSql.append(" AND (TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME) >= '" + sStartDay + "' AND  (TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME) <= '" + sEndDay + "' ");

		StringBuffer sSBSql2 = new StringBuffer();
		sSBSql2.append(sSBSql.toString());
		sSBSql.append(" GROUP BY C.MANAGECOM,C.AGENTCOM");
		
		double acodePrem1 = 0;
		double acodePrem2 = 0;
		double acodePrem3 = 0;
		double acodePrem4 = 0;
		double acodePrem5 = 0;
		ExeSQL exeSql = new ExeSQL();
		
		String nAgentCodeSql = "";
		 nAgentCodeSql += "SELECT C.AGENTCOM, C.MANAGECOM" 
			+ "  FROM LCCONT C"
			 + "  WHERE "
			 + sSBSql;
		 List array = new ArrayList();
		SSRS tSSRS2 = exeSql.execSQL(nAgentCodeSql);
			String[][] AgentCodeS = tSSRS2.getAllData();
			
			for (int i =0; i<AgentCodeS.length; i++){
			//System.out.println(AgentCodeS[i][0]);
			
			String sSql = "";
			sSql = getSql2(AgentCodeS[i][0],AgentCodeS[i][1],sSBSql2.toString());
			SSRS tSSRST = exeSql.execSQL(sSql);
			String[][] sss = tSSRST.getAllData();
			for (int a = 0; a < sss.length; a++) {
				for (int j = 0; j < sss[a].length; j++) {
					//System.out.println(sss[a][j]);
					if (sss[a][j] == null || "null".equals(sss[a][j])) {
						if(j== 12 ||j== 13 || j ==14 || j==15){
						sss[a][j] = "0";
						}
						else{
							sss[a][j] = "";
						}
					}
					
				}
			}
			try {
				
			
			for (int a = 0; a < sss.length; a++) {
				for (int j = 0; j < sss[a].length; j++) {
					if(sss[a][j].toString().equals("��������:")){
						sss[a][j+1] = sss[a-1][6];
						acodePrem1 += Double.valueOf(sss[a][j+3]);
						acodePrem2 += Double.valueOf(sss[a][j+11]);
						acodePrem3 += Double.valueOf(sss[a][j+12]);
						acodePrem4 += Double.valueOf(sss[a][j+13]);
						acodePrem5 += Double.valueOf(sss[a][j+14]);
					}
				}
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			array.add(sss);
			}
			//System.out.println("array.size():"+array.size());
			
			int z = 0;
			SSRS tSSRS4 = new SSRS();
			
			for(int i =0;i<array.size();i++){
				String[][] tSSRS5 = (String[][]) array.get(i);
				
				//System.out.println("tSSRS5:"+tSSRS5.length);
				for(int j=0;j<tSSRS5.length;j++){
					z++;
				}
			}
		//	System.out.println("�������:"+z);
			String[][] ssrs = new String[z+1][15];
			
			int zz =0;
		for(int i =0;i<array.size();i++){
			String[][] tSSRS5 = (String[][]) array.get(i);
			
			//System.out.println("tSSRS5:"+tSSRS5.length);
			for(int j=0;j<tSSRS5.length;j++){
				//System.out.println("j:"+j);
				//System.out.println("ZZ:"+zz);
				ssrs[zz] = tSSRS5[j];
				zz++;
			}
		}

		String [] aSum =new String [15];
		aSum[0] = "�ܼƣ�";aSum[1] = "";aSum[2] = "";aSum[3] = df.format(acodePrem1);aSum[4] = "";
		aSum[5] = "";aSum[6] = "";aSum[7] = "";aSum[8] = "";aSum[9] = "";
		aSum[10] = "";aSum[11] = df.format(acodePrem2);aSum[12] = df.format(acodePrem3);aSum[13] = df.format(acodePrem4);aSum[14] = df.format(acodePrem5);
		
		ssrs[zz] = aSum;
		
		ArrayList arrayList2 = new ArrayList();
		String [] anull =new String [15];
		for(int i = 0;i<anull.length;i++){
			anull[i] = "";
		}
		arrayList2.add(anull);
		for (int a = 0; a < ssrs.length; a++) {
			arrayList2.add(ssrs[a]);
			for (int j = 0; j < ssrs[a].length; j++) {
				if(ssrs[a][j].toString().equals("��������:")){
					arrayList2.add(anull);
				}
			}
		}
	
		String[][] ssrs22 = new String[arrayList2.size()-1][15];
		for (int a = 0; a < ssrs22.length; a++) {
			ssrs22[a] = (String[]) arrayList2.get(a);
			}
		ssrs22[arrayList2.size()-2] = aSum;
		
		return ssrs22;
		
	}
	
	  public static void main(String[] args)
	  {
	    TransferData tTransferData = new TransferData();
	    String filePath = "E:/aa.xls";

	    String sArea = "";
	    String sCity = "";
	    String sTranCom = "";
	    String sAgentCom = "";
	    String sAgentCode = "";
	    String sRiskCode = "";
	    String sStartDay = "2011-03-01";
	    String sEndDay = "2011-03-14";
	    String sStartHour = "00:00:00";
	    String sEndHour = "22:00:00";
	    String sContStatue = "";

	    System.out.println
	      ("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-����,2-����,3-��������,4-����,5-����רԱ,6-���ִ���,7-��ʼ����,8-��������");
	    System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-" + sArea + ",2-" + sCity + 
	      ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode + 
	      ",6-" + sRiskCode + ",7-" + sStartDay + ",8-" + sEndDay);
	    System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

	    tTransferData.setNameAndValue("TranCom", sTranCom);
	    tTransferData.setNameAndValue("AgentCom", sAgentCom);
	    tTransferData.setNameAndValue("AgentCode", sAgentCode);
	    tTransferData.setNameAndValue("RiskCode", sRiskCode);
	    tTransferData.setNameAndValue("StartDay", sStartDay);
	    tTransferData.setNameAndValue("EndDay", sEndDay);
	    tTransferData.setNameAndValue("filePath", filePath);
	    tTransferData.setNameAndValue("ContStatue", sContStatue);
	    tTransferData.setNameAndValue("StartHour", sStartHour);
	    tTransferData.setNameAndValue("EndHour", sEndHour);
	    VData tVData = new VData();
	    tVData.addElement(tTransferData);

	    String Content = "";
	    String FlagStr = "";
	    FIN_PrintBL tFIN_PrintBL = new FIN_PrintBL();
	    if (!(tFIN_PrintBL.submitData(tVData, "download"))) {
	      FlagStr = "Fail";
	      Content = tFIN_PrintBL.mErrors.getFirstError().toString();
	    }
	    else {
	      FlagStr = "Succ";
	      Content = (String)tFIN_PrintBL.getResult().get(0);

	      System.out.println(Content);
	    }
	  }
}