package com.sinosoft.lis.f1print;


import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;

/**
 * <p>
 * ClassName: LO_PrintBL
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

public class LO_PrintBL extends CreatExcel {

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

	private TransferData mTransferData = new TransferData();

	private String sTranCom = "";
	
	private String sManageCom = "";

	private String sAgentCom = "";

	private String sAgentCode = "";

	private String sRiskCode = "";

//	private String sStartDay = "";
//
//	private String sEndDay = "";
	
	private String sDay = "";

	private String filePath = "";

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public LO_PrintBL() {

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

		this.sManageCom = (String) mTransferData.getValueByName("ManageCodeNo");
		this.sTranCom = (String) mTransferData.getValueByName("TranCom");
		this.sAgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.sAgentCode = (String) mTransferData.getValueByName("AgentCode");
		this.sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.sDay = (String) mTransferData.getValueByName("Day");
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
		String[] Sheet = { "����ͨÿ��ʵʱ���ݵ�����" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "18", "18","18", "18", "18", "18", "18", "18", "18","18",
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "20", "18", "18", "18", "18", "18", "18", "20", 
				"18", "18", "18", "18", "18", "18", "20", "18", "18", "18",
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18", 
				"18", "18", "18", "18", "18", "18", "18", "18", "18", "18" 
				  };

		// ���ñ���
		String[] title = { "����ͨÿ��ʵʱ���ݵ�����" };

		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.LEFT);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
		System.out.println("aaaa" + getFontSize(0, 0, 0));
		
		setMergeCells(0, 0, 69, 0);
		setBorderLineStyle(1, 0, BorderLineStyle.THIN);
		setMergeCells(0, 1, 69, 1);
		setContent(1, 0, "��������:" + this.sDay);
		setCellFont(1, 0, ExcelFont.ARIAL);
		setFontSize(1, 0, 12);
		setFontBold(1, 0, ExcelFont.No_Bold);
		setFontAlign(1, 0, ExcelAlignment.LEFT);

		// ������ͷ
		String[] head = { "�������", "����״̬","����״̬", "�������", "������", "��������", "��������",
				"��Ʒ����", "�����˱��", "��������", "�������Ա�", "�����˳�������", "���������֤��",
				"����������֤����", "�������ʱ�", "�����˵�ַ", "�����˵绰", "�������ֻ�", "������ְҵ����","Ͷ�����뱻���˹�ϵ",
				"Ͷ����", "Ͷ�����Ա�", "Ͷ���˳�������", "Ͷ�������֤��", "Ͷ��������֤��", "Ͷ�����ʱ�",
				"Ͷ���˵�ַ", "Ͷ���˵绰", "Ͷ�����ֻ�",
				"������1����", "������1�Ա�", "������1��������","������1֤������", "������1����˳��", "������1�������","������1�뱻���˹�ϵ",
				"������2����", "������2�Ա�", "������2��������","������2֤������", "������2����˳��", "������2�������","������2�뱻���˹�ϵ",
				"������3����", "������3�Ա�", "������3��������","������3֤������", "������3����˳��", "������3�������","������3�뱻���˹�ϵ",
				"��Ʒ����", "����", "����", "��������", "�ɷѷ�ʽ",
				"��������", "���ջ������շ�", "�ڽ�׷�ӱ��շ�", "����׷�ӱ��շ�",
				"�����ղ�Ʒ����", "�����շ���", "�����ձ���", "�����ձ��շ�",
				"���շѺϼ�",
				"Ͷ���˻�", "Ͷ���˻�����", "Ͷ������", "ת���ʺ�", "�˻�������", "����ʱ��" };
		for (int i = 0; i < 70; i++) {
			setContent(2, i, head[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		sSql += "SELECT TRIM(C.CONTNO) �������,"
				+ "   CASE WHEN c.appflag = 'B' or c.appflag = '1' THEN  '������Ч' WHEN c.appflag = '0' AND (TRIM(c.state) = 'C' or TRIM(c.state) = 'B') THEN  'Э����ֹ'  ELSE  '--' END ����״̬,"
				+ "   CASE WHEN c.balancestate = 'Y' THEN  '�Ѷ���' WHEN c.balancestate = 'N' THEN  'δ����'  ELSE  '--' END ����״̬,"
				+ "   TRIM(C.AXANODEMAP) �������,"
				+ "   TRIM(C.AXAAGENTCOM) ������,"
				+ "   C.AGENTCOMNAME ��������,"
				+ "   to_char(c.makedate, 'YYYY-mm-dd') || ' ' || c.maketime ��������,"
				+ "   p.riskalias ��Ʒ����,"
				+ "   TRIM(C.AXAAGENTCODE) �����˱��,"
				+ ""
				+ "   i.name ��������,"
				+ "   CASE  WHEN i.sex = 'M' THEN   '��'  WHEN i.sex = 'F' THEN  'Ů'  ELSE  '����'  END �������Ա�,"
				+ "   to_char(i.birthday, 'yyyy-mm-dd') �����˳�������,"
				+ "   CASE  WHEN i.idtype = '0' THEN i.idno  ELSE  '' END ���������֤��,"
				+ "   CASE  WHEN i.idtype != '0' THEN  i.idno  ELSE  '' END ����������֤����,"
				+ "   d.zipcode �������ʱ�,"
				+ "   d.postaladdress �����˵�ַ,"
				+ "   d.homephone �����˵绰,"
				+ "   d.mobile �������ֻ�,"       
				+ "   CASE  WHEN  i.occupationcode ='' THEN  i.occupationcode ELSE '&' END ������ְҵ����,"
				+ "   CASE  WHEN  AP.PLURALITYTYPE ='1' THEN  '��ż' WHEN  AP.PLURALITYTYPE ='2' THEN  '��ĸ'  WHEN  AP.PLURALITYTYPE ='3' THEN  '��Ů'  WHEN  AP.PLURALITYTYPE ='4' THEN  '�游��ĸ'  WHEN  AP.PLURALITYTYPE ='5' THEN  '����Ů' WHEN  AP.PLURALITYTYPE ='6' THEN  '�ֵܽ���'  WHEN  AP.PLURALITYTYPE ='7' THEN  '��������'  WHEN  AP.PLURALITYTYPE ='8' THEN  '����' WHEN  AP.PLURALITYTYPE ='9' THEN  '����'   WHEN  AP.PLURALITYTYPE ='99' THEN  '����'  ELSE '&' END Ͷ�����뱻���չ�ϵ,"
				+ ""
				+ "   ap.appntname ��������,"
				+ "   CASE  WHEN ap.appntsex = 'M' THEN   '��'  WHEN ap.appntsex = 'F' THEN  'Ů'  ELSE  '����'  END �������Ա�,"
				+ "   to_char(ap.appntbirthday, 'yyyy-mm-dd') �����˳�������,"
				+ "   CASE  WHEN ap.idtype = '0' THEN ap.idno  ELSE  '' END ���������֤��,"
				+ "   CASE  WHEN ap.idtype != '0' THEN  ap.idno  ELSE  '' END ����������֤����,"
				+ "   d2.zipcode �������ʱ�,"
				+ "   d2.postaladdress �����˵�ַ,"
				+ "   d2.homephone �����˵绰,"
				+ "   d2.mobile �������ֻ�,"
				+ ""
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1����,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '��'  WHEN B1.SEX = 'F' THEN  'Ů'  ELSE  '����'  END �������Ա�  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1�Ա�,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1��������,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1֤������,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1����˳��,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='1') ������1�������,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '��ż' WHEN  B1.RELATIONTOINSURED ='2' THEN  '��ĸ'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '��Ů'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '�游��ĸ'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '����Ů' WHEN  B1.RELATIONTOINSURED ='6' THEN  '�ֵܽ���'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '��������'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '����' WHEN  B1.RELATIONTOINSURED ='9' THEN  '����'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '����'  ELSE '&' END Ͷ�����뱻���չ�ϵ  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=1) ������1�뱻���˹�ϵ,"
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1����,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '��'  WHEN B1.SEX = 'F' THEN  'Ů'  ELSE  '����'  END �������Ա�  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1�Ա�,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1��������,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1֤������,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1����˳��,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='2') ������1�������,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '��ż' WHEN  B1.RELATIONTOINSURED ='2' THEN  '��ĸ'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '��Ů'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '�游��ĸ'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '����Ů' WHEN  B1.RELATIONTOINSURED ='6' THEN  '�ֵܽ���'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '��������'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '����' WHEN  B1.RELATIONTOINSURED ='9' THEN  '����'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '����'  ELSE '&' END Ͷ�����뱻���չ�ϵ  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=2) ������1�뱻���˹�ϵ,"
				
				
				+ "  (SELECT B1.NAME FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1����,"
				+ "  (SELECT CASE  WHEN B1.SEX = 'M' THEN   '��'  WHEN B1.SEX = 'F' THEN  'Ů'  ELSE  '����'  END �������Ա�  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1�Ա�,"
				+ "  (SELECT B1.BIRTHDAY FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1��������,"
				+ "  (SELECT B1.IDNO FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1֤������,"
				+ "  (SELECT TO_CHAR(B1.BNFGRADE) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1����˳��,"
				+ "  (SELECT TO_CHAR(B1.BNFLOT*100) FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO='3') ������1�������,"
				+ "  (SELECT CASE  WHEN  B1.RELATIONTOINSURED ='1' THEN  '��ż' WHEN  B1.RELATIONTOINSURED ='2' THEN  '��ĸ'  WHEN  B1.RELATIONTOINSURED ='3' THEN  '��Ů'  WHEN  B1.RELATIONTOINSURED ='4' THEN  '�游��ĸ'  WHEN  B1.RELATIONTOINSURED ='5' THEN  '����Ů' WHEN  B1.RELATIONTOINSURED ='6' THEN  '�ֵܽ���'  WHEN  B1.RELATIONTOINSURED ='7' THEN  '��������'  WHEN  B1.RELATIONTOINSURED ='8' THEN  '����' WHEN  B1.RELATIONTOINSURED ='9' THEN  '����'   WHEN  B1.RELATIONTOINSURED ='99' THEN  '����'  ELSE '&' END Ͷ�����뱻���չ�ϵ  FROM LCBNF B1 WHERE C.CONTNO = B1.CONTNO AND B1.BNFNO=3) ������1�뱻���˹�ϵ,"
				
				
				
				+ "  (select r.riskname FROM lmriskapp r where p.riskcode = r.riskcode) ��Ʒ����,"
				+ "  CASE WHEN p.mult = '0.00000' THEN  '' WHEN p.mult != '0.00000' THEN  to_char(p.mult)  ELSE  '--' END ����, "
				+ "  CASE WHEN p.amnt = '0.00' THEN  '' WHEN p.amnt != '0.00' THEN  to_char(p.amnt) END ����,"
				+ "   case when p.insuyearflag ='5' then '����' ELSE  TO_CHAR(p.insuyear) END ��������,"
				+ "   CASE  WHEN  p.payintv = '1' THEN   '���'  WHEN  p.payintv = '2' THEN   '�½�'  WHEN  p.payintv = '3' THEN   '�����'  WHEN  p.payintv = '4' THEN   '����'  WHEN  p.payintv = '5' THEN   '����'   WHEN  p.payintv = '6' THEN   '�����ڽ�'    ELSE  '����'  END  �ɷѷ�ʽ,"
				+ "   CASE WHEN p.PAYENDYEARFLAG ='6' THEN '����' WHEN p.PAYENDYEARFLAG = '5' THEN '����' ELSE to_char(p.payendyear) END �ɷ�����,"
				+ "   to_char(p.prem) ���ջ������շ�,"
				+ "   to_char(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0))  �ڽ�׷�ӱ��շ�,"
				+ "   to_char((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual)) ����׷�ӱ��շ�,"
				
				+ "  (select r.riskname FROM lmriskapp r, lcpol sp1 where sp1.riskcode = r.riskcode and sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����ղ�Ʒ����,"
				+ "  (select CASE WHEN sp1.mult = '0.00000' THEN  '' WHEN sp1.mult != '0.00000' THEN  to_char(sp1.mult)  ELSE  '--' END from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����շ���,"
				+ "  (select CASE WHEN sp1.amnt = '0.00' THEN  '' WHEN sp1.amnt != '0.00' THEN  to_char(sp1.amnt)  ELSE  '--' END from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����ձ���,"
				+ "  (select to_char(sp1.prem) from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')) �����ձ��շ�,"
			
				+ "  to_char(c.prem) �ϼƱ��շ�,"
				+ "	(select wmsys.wm_concat(ac.acccode) from lcinsureacc ac where ac.contno = c.contno)  Ͷ���˻�,"
				+ "  (select wmsys.wm_concat(acc.accrate) from lcinsureacc acc where acc.contno = c.contno )  Ͷ�ʱ���,"
				+ "  (select  CASE  WHEN accc.acctimeflag = '1' THEN  'Ͷ������'  WHEN accc.acctimeflag = '2' THEN  '��ԥ�ں�'  ELSE  '' END Ͷ������ from lcinsureacc accc where accc.contno = c.contno and accc.insuaccno='1') Ͷ������,"
				+ "   c.bankaccno ת���˺�,"
				+ "   c.accname �˻�������,"
				+ "   to_char(c.downdate, 'yyyy-mm-dd')||' '||c.downtime ����ʱ��"
				+ "	 from lccont c, lcpol p, lcinsured i, lcaddress d,lcaddress d2, lcappnt ap"
				+ "	 where c.contno = p.contno"
				+ "		and p.riskcode = p.kindcode"
				+ "		and c.contno = i.contno"
				+ "		and i.insuredno = d.customerno"
				+ "		and i.addressno = d.addressno"
				+ "		and c.contno = ap.contno"
				+ "  	and ap.appntno = d2.customerno"
				+ "		and ap.addressno = d2.addressno"
				+ "		and c.norealflag = 'N'"
				+ "		and ((c.appflag = 'B' AND C.UWFLAG = '9') OR (C.APPFLAG = '0' AND C.STATE='C') OR (C.APPFLAG = '0' AND C.STATE='B') OR ( C.APPFLAG = '1'))";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += " and c.managecom like '" + sManageCom + "%' ";
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
		if (sDay != null && !"".equals(sDay)) {
			sSql += "  AND p.MAKEDATE = DATE'" + sDay + "' ";
		}


		sSql +=   " union"
			+ " select '�ϼ�:��'||count(C.CONTNO)||'��','','','','','','','','','','',"
			+ " '','','','','','','','','','','','','','','','','','',"
			+ " null,null,null,null,null,null,null,  null,null,null,null,null,null,null,  null,null,null,null,null,null,null, '',null,"
			+ " to_char(sum(p.amnt)),"
			+ " null,"
			+ " '',null,"
			+ " to_char(sum(p.prem)),"
			+ " to_char(sum(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0))),"
			+ " to_char(sum((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END ����Ͷ�� FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual))), "
			+ " null,null,"
			+ " to_char(sum((select sp1.amnt from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')))),"
			+ " to_char(sum((select sp1.prem from lcpol sp1 where sp1.polno=c.contno||'-1' and sp1.kindcode not in('NHY','HYG3')))),"  
			+ " to_char(sum(c.prem)),"
			+ " '','','',"
			+ " '','',''"
			+ "	 from lccont c, lcpol p, lcinsured i, lcaddress d,lcaddress d2, lcappnt ap"
			+ "	 where c.contno = p.contno"
			+ "		and p.riskcode = p.kindcode"
			+ "		and c.contno = i.contno"
			+ "		and i.insuredno = d.customerno"
			+ "		and i.addressno = d.addressno"
			+ "		and c.contno = ap.contno"
			+ "  	and ap.appntno = d2.customerno"
			+ "		and ap.addressno = d2.addressno"
			+ "		and c.norealflag = 'N'"
				+ "		and ((c.appflag = 'B' AND C.UWFLAG = '9') OR (C.APPFLAG = '0' AND C.STATE='C') OR (C.APPFLAG = '0' AND C.STATE='B') OR ( C.APPFLAG = '1'))";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += " and c.managecom like '" + sManageCom + "%' ";
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
		if (sDay != null && !"".equals(sDay)) {
			sSql += "  AND p.MAKEDATE = DATE'" + sDay + "' ";
		}


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
		// ׼������������Ϣ
		TransferData tTransferData = new TransferData();
		String filePath = "E:/bb.xls";

		String sArea = "";
		String sCity = "";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sDay = "2012-03-01";

		System.out
				.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-����,2-����,3-��������,4-����,5-����רԱ,6-���ִ���,7-��ʼ����,8-��������");
		System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-" + sArea + ",2-" + sCity
				+ ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode
				+ ",6-" + sRiskCode + ",7-" + sDay + "");
		System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

		tTransferData.setNameAndValue("Area", sArea);
		tTransferData.setNameAndValue("City", sCity);
		tTransferData.setNameAndValue("TranCom", sTranCom);
		tTransferData.setNameAndValue("AgentCom", sAgentCom);
		tTransferData.setNameAndValue("AgentCode", sAgentCode);
		tTransferData.setNameAndValue("RiskCode", sRiskCode);
		tTransferData.setNameAndValue("Day", sDay);
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		String Content = "";
		String FlagStr = "";
		LO_PrintBL tLO_PrintBL = new LO_PrintBL();
		if (!tLO_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tLO_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tLO_PrintBL.getResult().get(0);
			System.out.println(Content);
		}
	}
}