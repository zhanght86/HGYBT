package com.sinosoft.midplat.newccb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class PutContFile0930 {
	protected final Logger cLogger = Logger.getLogger(getClass());
	public PutContFile0930(){
		
	}
	public boolean isSuccessed(List<String> list,String filePath){
		Element tInsu_List = new Element("Insu_List"); 
		for (int i = 0; i < list.size(); i++) {
			String contNo = list.get(i);
			Element tInsu_Detail = returnElement(contNo);
			tInsu_List.addContent(tInsu_Detail);
		}
		Element tHead = new Element("Head");
		Element tBtchBag_SN = new Element("BtchBag_SN");
		Element tCur_Btch_Dtl_TDnum = new Element("Cur_Btch_Dtl_TDnum"); 
		tHead.addContent(tBtchBag_SN);
		tHead.addContent(tCur_Btch_Dtl_TDnum);
		Element tRoot = new Element("Root");
		tRoot.addContent(tHead);
		tRoot.addContent(tInsu_List);
		Document pOutNoStd = new Document(tRoot);
		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mBodyBytes);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isTrue = file.exists();
		return isTrue;
	}
	protected Element returnElement(String tContNo){
		Element Insu_Detail = new Element("Insu_Detail");
		
		ElementLis Ins_Co_Nm = new ElementLis("Ins_Co_Nm","�к����ٱ������޹�˾",Insu_Detail);//���չ�˾����
		ElementLis Pkg_ID = new ElementLis("Pkg_ID",Insu_Detail);//�������ײͱ��
		ElementLis Agnc_Ins_Aply_ID = new ElementLis("Agnc_Ins_Aply_ID",Insu_Detail);//������
		//Ͷ����
		String tSql1 = "select rp.client_no from  toi.relation_pp  rp where policy_no ='"+tContNo+"' and relation ='O'";
        String client_no_TBR = new ExeSQL().getOneValue(tSql1);
        String tSqlTBR = "select pi.name,pi.sex,pi.birthday,pi.ssn_type,pi.ssn_code,pi.ssn_valid,pi.tel_office,pi.tel_home,pi.mobile_no,pi.email from toi.person_info pi where pi.client_no='"+client_no_TBR+"'";
        SSRS ssrs1 = new ExeSQL().execSQL(tSqlTBR);
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",ssrs1.GetText(1, 1),Insu_Detail);//Ͷ��������
		ElementLis Plchd_CPA_FullNm = new ElementLis("Plchd_CPA_FullNm",Insu_Detail);//Ͷ����ƴ��ȫ��
		ElementLis Plchd_Gnd_Cd = new ElementLis("Plchd_Gnd_Cd",ssrs1.GetText(1, 2),Insu_Detail);//Ͷ�����Ա����
		ElementLis Plchd_Brth_Dt = new ElementLis("Plchd_Brth_Dt",ssrs1.GetText(1, 3),Insu_Detail);//Ͷ���˳�������
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",ssrs1.GetText(1, 4),Insu_Detail);//Ͷ����֤�����ʹ���
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",ssrs1.GetText(1, 5),Insu_Detail);//Ͷ����֤������
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",Insu_Detail);//Ͷ����֤����Ч����
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",ssrs1.GetText(1, 6),Insu_Detail);//Ͷ����֤��ʧЧ����
		ElementLis Plchd_Unit_Comm_Adr = new ElementLis("Plchd_Unit_Comm_Adr",Insu_Detail);//Ͷ���˵�λͨѶ��ַ
		ElementLis Pichd_Unit_ZipECD = new ElementLis("Pichd_Unit_ZipECD",Insu_Detail);//Ͷ���˵�λ��������
		ElementLis Plchd_Unit_Ctc_Tel = new ElementLis("Plchd_Unit_Ctc_Tel",ssrs1.GetText(1, 7),Insu_Detail);//Ͷ���˵�λ��ϵ�绰
		ElementLis Plchd_Fam_Adr = new ElementLis("Plchd_Fam_Adr",Insu_Detail);//Ͷ���˼�ͥ��ַ 
		ElementLis Plchd_Fam_ZipECD = new ElementLis("Plchd_Fam_ZipECD",Insu_Detail);//Ͷ���˼�ͥ��������
		ElementLis Plchd_Fam_Ctc_Tel = new ElementLis("Plchd_Fam_Ctc_Tel",ssrs1.GetText(1, 8),Insu_Detail);//Ͷ���˼�ͥ��ϵ�绰
		ElementLis Plchd_Move_TelNo = new ElementLis("Plchd_Move_TelNo",ssrs1.GetText(1, 9),Insu_Detail);//Ͷ�����ƶ��绰����
		ElementLis Plchd_Email_Adr = new ElementLis("Plchd_Email_Adr",ssrs1.GetText(1, 10),Insu_Detail);//Ͷ���˵����ʼ���ַ
		ElementLis Plchd_Ocp_Cd = new ElementLis("Plchd_Ocp_Cd",Insu_Detail);//Ͷ����ְҵ����
		ElementLis Plchd_And_Rcgn_ReTpCd = new ElementLis("Plchd_And_Rcgn_ReTpCd",Insu_Detail);//Ͷ���˺ͱ����˹�ϵ���ʹ��� 
		//������
		String tSql2 = "select rp.client_no from  toi.relation_pp  rp where policy_no ='"+tContNo+"' and relation ='I'";
	    String client_no_BBR = new ExeSQL().getOneValue(tSql2);
	    String tSqlBBR = "select pi.name,pi.sex,pi.birthday,pi.ssn_type,pi.ssn_code,pi.ssn_valid,pi.tel_home,pi.mobile_no,pi.email from toi.person_info pi where pi.client_no='"+client_no_BBR+"'";
	    SSRS ssrs2 = new ExeSQL().execSQL(tSqlBBR);
		ElementLis Rcgn_Nm = new ElementLis("Rcgn_Nm",ssrs2.GetText(1, 1),Insu_Detail);//���������� 
        ElementLis Rcgn_Gnd_Cd = new ElementLis("Rcgn_Gnd_Cd",ssrs2.GetText(1, 2),Insu_Detail);//�������Ա����
        ElementLis Rcgn_Brth_Dt = new ElementLis("Rcgn_Brth_Dt",ssrs2.GetText(1, 3),Insu_Detail);//�����˳�������
        ElementLis Rcgn_Crdt_TpCd = new ElementLis("Rcgn_Crdt_TpCd",ssrs2.GetText(1, 4),Insu_Detail);//������֤�����ʹ���
        ElementLis Rcgn_Crdt_No = new ElementLis("Rcgn_Crdt_No",ssrs2.GetText(1, 5),Insu_Detail);//������֤������
        ElementLis Rcgn_Crdt_EfDt = new ElementLis("Rcgn_Crdt_EfDt",Insu_Detail);//������֤����Ч����
        ElementLis Rcgn_Crdt_ExpDt =new ElementLis("Rcgn_Crdt_ExpDt",ssrs2.GetText(1, 6),Insu_Detail);//������֤��ʧЧ����
        ElementLis Rcgn_Nat_Cd = new ElementLis("Rcgn_Nat_Cd",Insu_Detail);//�����˹�������
        ElementLis Rcgn_Comm_Adr = new ElementLis("Rcgn_Comm_Adr",Insu_Detail);//������ͨѶ��ַ
        ElementLis Rcgn_ZipECD = new ElementLis("Rcgn_ZipECD",Insu_Detail);//��������������
        ElementLis Rcgn_Fix_TelNo = new ElementLis("Rcgn_Fix_TelNo",ssrs2.GetText(1, 7),Insu_Detail);//�����˹̶��绰����
        ElementLis Rcgn_Move_TelNo = new ElementLis("Rcgn_Move_TelNo",ssrs2.GetText(1, 8),Insu_Detail);//�������ƶ��绰����
        ElementLis Rcgn_Email_Adr = new ElementLis("Rcgn_Email_Adr",Insu_Detail);//�����˵����ʼ���ַ
        ElementLis Rcgn_Ocp_Cd = new ElementLis("Rcgn_Ocp_Cd",Insu_Detail);//������ְҵ����
        ElementLis Rcgn_LvFr_Pps_Lnd_Num = new ElementLis("Rcgn_LvFr_Pps_Lnd_Num",Insu_Detail);//������ǰ��Ŀ�ĵظ��� 
        //Ŀ�ĵ�ѭ�� -- ���չ�˾û�У��ſ�
        ElementLis Pps_List = new ElementLis("Pps_List",Insu_Detail);//Ŀ�ĵ���Ϣѭ��Pps_List��ÿ��Ŀ�ĵ���Pps_Detail
        ElementLis Pps_Detail = new ElementLis("Pps_Detail",Pps_List);//Pps_Detail
        ElementLis Rcgn_LvFr_Pps_Lnd = new ElementLis("Rcgn_LvFr_Pps_Lnd",Pps_Detail);//������ǰ��Ŀ�ĵ� 
        //������
        Element Benf_Num = new ElementLis("Benf_Num",Insu_Detail);//�����˸��� 
        Element Benf_List = new ElementLis("Benf_List",Insu_Detail);//��������Ϣѭ��Benf_List��ÿ�������˵ı�ǩ��Benf_Detail
        ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
        ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",Benf_Detail);//���������������ʹ���
        ElementLis SN_ID = new ElementLis("SN_ID",Benf_Detail);//#��ű��
        ElementLis AgIns_Bnft_Ord_Val = new ElementLis("AgIns_Bnft_Ord_Val",Benf_Detail);//�������������ֵ
        ElementLis Benf_Nm = new ElementLis("Benf_Nm",Benf_Detail);//����������
        ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",Benf_Detail);//�������Ա���� 
        ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",Benf_Detail);//�����˳������� 
        ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",Benf_Detail);//������֤�����ʹ���
        ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",Benf_Detail);//������֤������
        ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",Benf_Detail);//������֤����Ч����
        ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",Benf_Detail);//������֤��ʧЧ����
        ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd",Benf_Detail);//�����˹�������
        ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",Benf_Detail);//�����˺ͱ����˹�ϵ���ʹ���
        ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",Benf_Detail);//�������
        ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",Benf_Detail);//������ͨѶ��ַ
        //����
        String tSqlCode = "select c.plan,value_desc,c.mode_premium,c.face_amount,c.pay_year,toi.policy.divident_option,toi.policy.nf_option,c.issue_date,c.mat_exp_date from toi.value_table where value in '(select c.plan_code from toi.coverage c where c.policy_no='"+tContNo+"')'";
        SSRS ssrs3 = new ExeSQL().execSQL(tSqlCode);
        Element Rvl_Rcrd_Num =  new ElementLis("Rvl_Rcrd_Num",Integer.toString(ssrs3.MaxCol),Insu_Detail);//#����ѭ����¼���� 
        Element Busi_List =  new ElementLis("Busi_List",Insu_Detail);//������Ϣѭ��Busi_List��ÿ�����ֵı�ǩ��Busi_Detail
        for (int i = 0; i < ssrs3.MaxCol; i++) {
	        ElementLis Busi_Detail = new ElementLis("Busi_Detail",Busi_List);
	        ElementLis Cvr_ID = new ElementLis("Cvr_ID",ssrs3.GetText(i, 1),Busi_Detail);//���ֱ��
	        ElementLis Cvr_Nm = new ElementLis("Cvr_Nm",ssrs3.GetText(i, 2),Busi_Detail);//��������
	        ElementLis MainAndAdlIns_Ind = new ElementLis("MainAndAdlIns_Ind",Busi_Detail);//�����ձ�־
	        ElementLis Ins_Cps = new ElementLis("Ins_Cps",Busi_Detail);//Ͷ������ 
	        ElementLis InsPrem_Amt = new ElementLis("InsPrem_Amt",ssrs3.GetText(i, 3),Busi_Detail);//���ѽ�� 
	        ElementLis Ins_Cvr = new ElementLis("Ins_Cvr",ssrs3.GetText(i, 4),Busi_Detail);//Ͷ������
	        ElementLis Ins_Scm_Inf = new ElementLis("Ins_Scm_Inf",Busi_Detail);//Ͷ��������Ϣ
	        ElementLis Opt_Part_DieIns_Amt = new ElementLis("Opt_Part_DieIns_Amt",Busi_Detail);//��ѡ������ʱ��ս��
	        ElementLis FTm_Extr_Adl_InsPrem = new ElementLis("FTm_Extr_Adl_InsPrem",Busi_Detail);//�״ζ���׷�ӱ���
	        ElementLis Ivs_MtdCd = new ElementLis("Ivs_MtdCd",Busi_Detail);//Ͷ�ʷ�ʽ����
	        ElementLis InsPolcy_Rcv_MtdCd = new ElementLis("InsPolcy_Rcv_MtdCd","3",Busi_Detail);//������ȡ��ʽ����
	        ElementLis Ins_PyF_AR_ID = new ElementLis("Ins_PyF_AR_ID",Busi_Detail);//
	        ElementLis CCB_AccNo = new ElementLis("CCB_AccNo",Busi_Detail);//�����˺�
	        ElementLis Ins_Yr_Prd_Cgy_Cd = new ElementLis("Ins_Yr_Prd_Cgy_Cd","2",Busi_Detail);//��������������
	        ElementLis Ins_Ddln = new ElementLis("Ins_Ddln",Busi_Detail);//��������
	        ElementLis Ins_Cyc_Cd = new ElementLis("Ins_Cyc_Cd",Busi_Detail);//�������ڴ���
	        ElementLis InsPrem_PyF_Cyc_Cd = new ElementLis("InsPrem_PyF_Cyc_Cd",Busi_Detail);//���ѽɷ����ڴ���
	        ElementLis Ins_PyF_MtdCd = new ElementLis("Ins_PyF_MtdCd","12",Busi_Detail);//���սɷѷ�ʽ����
	        ElementLis InsPrem_PyF_Prd_Num = new ElementLis("InsPrem_PyF_Prd_Num",ssrs3.GetText(i, 5),Busi_Detail);//���ѽɷ�����
	        ElementLis spcl_Apnt_Inf = new ElementLis("spcl_Apnt_Inf",Busi_Detail);//�ر�Լ����Ϣ
	        ElementLis Anuty_Rcv_Cyc_CgyCd = new ElementLis("Anuty_Rcv_Cyc_CgyCd",Busi_Detail);//
	        ElementLis ExpPrmmRcvModCgyCd = new ElementLis("ExpPrmmRcvModCgyCd","0",Busi_Detail);//���ڱ��ս���ȡ��ʽ������ 
	        ElementLis SvBnf_Rcv_Cyc_CgyCd = new ElementLis("SvBnf_Rcv_Cyc_CgyCd","0",Busi_Detail);//�������ȡ���ڴ���
	        ElementLis XtraDvdn_Pcsg_MtdCd = new ElementLis("XtraDvdn_Pcsg_MtdCd",ssrs3.GetText(i, 6),Busi_Detail);//��������ʽ���� 
	        ElementLis ApntInsPremPyAdvnInd = new ElementLis("ApntInsPremPyAdvnInd",ssrs3.GetText(i, 7),Busi_Detail);//Լ�����ѵ潻��־ 
	        ElementLis Auto_RnwCv_Ind = new ElementLis("Auto_RnwCv_Ind",Busi_Detail);//�Զ�������־ 
	        ElementLis Dspt_Arbtr_MtdCd = new ElementLis("Dspt_Arbtr_MtdCd",Busi_Detail);//���鴦��ʽ����
	        ElementLis Dspt_Arbtr_Inst_Nm = new ElementLis("Dspt_Arbtr_Inst_Nm",Busi_Detail);//�����ٲû�������
	        ElementLis Minr_Acm_Cvr = new ElementLis("Minr_Acm_Cvr",Busi_Detail);//δ�������ۼƱ���
	        ElementLis Hlt_Ntf_Ind = new ElementLis("Hlt_Ntf_Ind",Busi_Detail);
	        ElementLis XtraDvdn_Alct_Ind = new ElementLis("XtraDvdn_Alct_Ind",Busi_Detail);//���������־
	        ElementLis RdAmtPyCls_Ind = new ElementLis("RdAmtPyCls_Ind",Busi_Detail);//������־ 
	        ElementLis InsPlocy_EfDt = new ElementLis("InsPlocy_EfDt",ssrs3.GetText(i, 8),Busi_Detail);//������Ч����
	        ElementLis InsPlocy_ExpDt = new ElementLis("InsPlocy_ExpDt",ssrs3.GetText(i, 9),Busi_Detail);//����ʧЧ���� 
	        ElementLis AcIsAR_StCd = new ElementLis("AcIsAR_StCd",Busi_Detail);//�����պ�Լ״̬���� 
	        ElementLis Emgr_CtcPsn = new ElementLis("Emgr_CtcPsn",Busi_Detail);//������ϵ��
	        ElementLis Emgr_Ctc_Tel = new ElementLis("Emgr_Ctc_Tel",Busi_Detail);//������ϵ�绰
	        ElementLis IvLkIns_Acc_Num = new ElementLis("IvLkIns_Acc_Num",Busi_Detail);//Ͷ�����˻�����
	        //Ͷ���� -- ���չ�˾û�У��ſ�
	        ElementLis PayAcctCode_List = new ElementLis("PayAcctCode_List",Busi_Detail);//Ͷ�����˺�ѭ��PayAcctCode_List��ÿ��Ͷ�����˺���PayAcctCode_Detail
	        ElementLis PayAcctCode_Detail = new ElementLis("PayAcctCode_Detail",PayAcctCode_List);
	        ElementLis ILIVA_ID = new ElementLis("ILIVA_ID",PayAcctCode_Detail);//Ͷ�������˻����
	        ElementLis Ivs_Tp_Alct_Pctg = new ElementLis("Ivs_Tp_Alct_Pctg",PayAcctCode_Detail);//Ͷ�����ͷ������ 
	        ElementLis Adl_Ins_Fee_Alct_Pctg = new ElementLis("Adl_Ins_Fee_Alct_Pctg",PayAcctCode_Detail);//׷�ӱ��շѷ������
        }
        ElementLis InsPolcy_Vchr_No = new ElementLis("InsPolcy_Vchr_No",Insu_Detail);//����ƾ֤����
        ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",Insu_Detail);//Ͷ������
        ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",tContNo,Insu_Detail);//��������
        ElementLis CstMgr_ID = new ElementLis("CstMgr_ID",Insu_Detail);//�ͻ������� 
        String tsqlP = "select BANK_ACC_NO from Relation_Person_info where RELATION='P' and policy_no='" + tContNo + "'";
        String tPlchd_PyF_AccNo = new ExeSQL().getOneValue(tsqlP);
        ElementLis Plchd_PyF_AccNo = new ElementLis("Plchd_PyF_AccNo");//Ͷ���˽ɷ��˺� 
        Plchd_PyF_AccNo.setText(tPlchd_PyF_AccNo);
        String tsqlA = "select BANK_ACC_NO from Relation_Person_info where RELATION='A' and policy_no='" + tContNo + "'";
        String tPlchd_Drw_AccNo = new ExeSQL().getOneValue(tsqlA);
        ElementLis Plchd_Drw_AccNo = new ElementLis("Plchd_Drw_AccNo",Insu_Detail);//Ͷ������ȡ�˺�
        Plchd_Drw_AccNo.setText(tPlchd_Drw_AccNo);
        ElementLis Rcgn_AccNo = new ElementLis("Rcgn_AccNo",Insu_Detail);//�������˺�  
        ElementLis Benf_AccNo = new ElementLis("Benf_AccNo",Insu_Detail);//�������˺�
        ElementLis Rnew_PyF_PyMd_Cd = new ElementLis("Rnew_PyF_PyMd_Cd",Insu_Detail);//���ڽɷ�֧����ʽ����
        ElementLis Rsrv_Fld_1 = new ElementLis("Rsrv_Fld_1",Insu_Detail);//#�����ֶ�һ 
        ElementLis Rsrv_Fld_2 = new ElementLis("Rsrv_Fld_2",Insu_Detail);//#�����ֶζ� 
        ElementLis Rsrv_Fld_3 = new ElementLis("Rsrv_Fld_3",Insu_Detail);//#�����ֶ��� 
        ElementLis Rsrv_Fld_4 = new ElementLis("Rsrv_Fld_4",Insu_Detail);//#�����ֶ���
        ElementLis Rsrv_Fld_5 = new ElementLis("Rsrv_Fld_5",Insu_Detail);//#�����ֶ���
        ElementLis Rsrv_Fld_6 = new ElementLis("Rsrv_Fld_6",Insu_Detail);//#�����ֶ���
		return Insu_Detail;
	}
}
