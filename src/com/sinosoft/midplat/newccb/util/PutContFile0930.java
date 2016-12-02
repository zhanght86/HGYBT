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
		
		ElementLis Ins_Co_Nm = new ElementLis("Ins_Co_Nm","中韩人寿保险有限公司",Insu_Detail);//保险公司名称
		ElementLis Pkg_ID = new ElementLis("Pkg_ID",Insu_Detail);//代理保险套餐编号
		ElementLis Agnc_Ins_Aply_ID = new ElementLis("Agnc_Ins_Aply_ID",Insu_Detail);//申请编号
		//投保人
		String tSql1 = "select rp.client_no from  toi.relation_pp  rp where policy_no ='"+tContNo+"' and relation ='O'";
        String client_no_TBR = new ExeSQL().getOneValue(tSql1);
        String tSqlTBR = "select pi.name,pi.sex,pi.birthday,pi.ssn_type,pi.ssn_code,pi.ssn_valid,pi.tel_office,pi.tel_home,pi.mobile_no,pi.email from toi.person_info pi where pi.client_no='"+client_no_TBR+"'";
        SSRS ssrs1 = new ExeSQL().execSQL(tSqlTBR);
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",ssrs1.GetText(1, 1),Insu_Detail);//投保人名称
		ElementLis Plchd_CPA_FullNm = new ElementLis("Plchd_CPA_FullNm",Insu_Detail);//投保人拼音全称
		ElementLis Plchd_Gnd_Cd = new ElementLis("Plchd_Gnd_Cd",ssrs1.GetText(1, 2),Insu_Detail);//投保人性别代码
		ElementLis Plchd_Brth_Dt = new ElementLis("Plchd_Brth_Dt",ssrs1.GetText(1, 3),Insu_Detail);//投保人出生日期
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",ssrs1.GetText(1, 4),Insu_Detail);//投保人证件类型代码
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",ssrs1.GetText(1, 5),Insu_Detail);//投保人证件号码
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",Insu_Detail);//投保人证件生效日期
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",ssrs1.GetText(1, 6),Insu_Detail);//投保人证件失效日期
		ElementLis Plchd_Unit_Comm_Adr = new ElementLis("Plchd_Unit_Comm_Adr",Insu_Detail);//投保人单位通讯地址
		ElementLis Pichd_Unit_ZipECD = new ElementLis("Pichd_Unit_ZipECD",Insu_Detail);//投保人单位邮政编码
		ElementLis Plchd_Unit_Ctc_Tel = new ElementLis("Plchd_Unit_Ctc_Tel",ssrs1.GetText(1, 7),Insu_Detail);//投保人单位联系电话
		ElementLis Plchd_Fam_Adr = new ElementLis("Plchd_Fam_Adr",Insu_Detail);//投保人家庭地址 
		ElementLis Plchd_Fam_ZipECD = new ElementLis("Plchd_Fam_ZipECD",Insu_Detail);//投保人家庭邮政编码
		ElementLis Plchd_Fam_Ctc_Tel = new ElementLis("Plchd_Fam_Ctc_Tel",ssrs1.GetText(1, 8),Insu_Detail);//投保人家庭联系电话
		ElementLis Plchd_Move_TelNo = new ElementLis("Plchd_Move_TelNo",ssrs1.GetText(1, 9),Insu_Detail);//投保人移动电话号码
		ElementLis Plchd_Email_Adr = new ElementLis("Plchd_Email_Adr",ssrs1.GetText(1, 10),Insu_Detail);//投保人电子邮件地址
		ElementLis Plchd_Ocp_Cd = new ElementLis("Plchd_Ocp_Cd",Insu_Detail);//投保人职业代码
		ElementLis Plchd_And_Rcgn_ReTpCd = new ElementLis("Plchd_And_Rcgn_ReTpCd",Insu_Detail);//投保人和被保人关系类型代码 
		//被保人
		String tSql2 = "select rp.client_no from  toi.relation_pp  rp where policy_no ='"+tContNo+"' and relation ='I'";
	    String client_no_BBR = new ExeSQL().getOneValue(tSql2);
	    String tSqlBBR = "select pi.name,pi.sex,pi.birthday,pi.ssn_type,pi.ssn_code,pi.ssn_valid,pi.tel_home,pi.mobile_no,pi.email from toi.person_info pi where pi.client_no='"+client_no_BBR+"'";
	    SSRS ssrs2 = new ExeSQL().execSQL(tSqlBBR);
		ElementLis Rcgn_Nm = new ElementLis("Rcgn_Nm",ssrs2.GetText(1, 1),Insu_Detail);//被保人名称 
        ElementLis Rcgn_Gnd_Cd = new ElementLis("Rcgn_Gnd_Cd",ssrs2.GetText(1, 2),Insu_Detail);//被保人性别代码
        ElementLis Rcgn_Brth_Dt = new ElementLis("Rcgn_Brth_Dt",ssrs2.GetText(1, 3),Insu_Detail);//被保人出生日期
        ElementLis Rcgn_Crdt_TpCd = new ElementLis("Rcgn_Crdt_TpCd",ssrs2.GetText(1, 4),Insu_Detail);//被保人证件类型代码
        ElementLis Rcgn_Crdt_No = new ElementLis("Rcgn_Crdt_No",ssrs2.GetText(1, 5),Insu_Detail);//被保人证件号码
        ElementLis Rcgn_Crdt_EfDt = new ElementLis("Rcgn_Crdt_EfDt",Insu_Detail);//被保人证件生效日期
        ElementLis Rcgn_Crdt_ExpDt =new ElementLis("Rcgn_Crdt_ExpDt",ssrs2.GetText(1, 6),Insu_Detail);//被保人证件失效日期
        ElementLis Rcgn_Nat_Cd = new ElementLis("Rcgn_Nat_Cd",Insu_Detail);//被保人国籍代码
        ElementLis Rcgn_Comm_Adr = new ElementLis("Rcgn_Comm_Adr",Insu_Detail);//被保人通讯地址
        ElementLis Rcgn_ZipECD = new ElementLis("Rcgn_ZipECD",Insu_Detail);//被保人邮政编码
        ElementLis Rcgn_Fix_TelNo = new ElementLis("Rcgn_Fix_TelNo",ssrs2.GetText(1, 7),Insu_Detail);//被保人固定电话号码
        ElementLis Rcgn_Move_TelNo = new ElementLis("Rcgn_Move_TelNo",ssrs2.GetText(1, 8),Insu_Detail);//被保人移动电话号码
        ElementLis Rcgn_Email_Adr = new ElementLis("Rcgn_Email_Adr",Insu_Detail);//被保人电子邮件地址
        ElementLis Rcgn_Ocp_Cd = new ElementLis("Rcgn_Ocp_Cd",Insu_Detail);//被保人职业代码
        ElementLis Rcgn_LvFr_Pps_Lnd_Num = new ElementLis("Rcgn_LvFr_Pps_Lnd_Num",Insu_Detail);//被保人前往目的地个数 
        //目的地循环 -- 保险公司没有，放空
        ElementLis Pps_List = new ElementLis("Pps_List",Insu_Detail);//目的地信息循环Pps_List，每个目的地用Pps_Detail
        ElementLis Pps_Detail = new ElementLis("Pps_Detail",Pps_List);//Pps_Detail
        ElementLis Rcgn_LvFr_Pps_Lnd = new ElementLis("Rcgn_LvFr_Pps_Lnd",Pps_Detail);//被保人前往目的地 
        //受益人
        Element Benf_Num = new ElementLis("Benf_Num",Insu_Detail);//受益人个数 
        Element Benf_List = new ElementLis("Benf_List",Insu_Detail);//受益人信息循环Benf_List，每个受益人的标签用Benf_Detail
        ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
        ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",Benf_Detail);//代理保险受益人类型代码
        ElementLis SN_ID = new ElementLis("SN_ID",Benf_Detail);//#序号编号
        ElementLis AgIns_Bnft_Ord_Val = new ElementLis("AgIns_Bnft_Ord_Val",Benf_Detail);//代理保险受益次序值
        ElementLis Benf_Nm = new ElementLis("Benf_Nm",Benf_Detail);//受益人姓名
        ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",Benf_Detail);//受益人性别代码 
        ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",Benf_Detail);//受益人出生日期 
        ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",Benf_Detail);//受益人证件类型代码
        ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",Benf_Detail);//受益人证件号码
        ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",Benf_Detail);//受益人证件生效日期
        ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",Benf_Detail);//受益人证件失效日期
        ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd",Benf_Detail);//受益人国籍代码
        ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",Benf_Detail);//受益人和被保人关系类型代码
        ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",Benf_Detail);//受益比例
        ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",Benf_Detail);//受益人通讯地址
        //险种
        String tSqlCode = "select c.plan,value_desc,c.mode_premium,c.face_amount,c.pay_year,toi.policy.divident_option,toi.policy.nf_option,c.issue_date,c.mat_exp_date from toi.value_table where value in '(select c.plan_code from toi.coverage c where c.policy_no='"+tContNo+"')'";
        SSRS ssrs3 = new ExeSQL().execSQL(tSqlCode);
        Element Rvl_Rcrd_Num =  new ElementLis("Rvl_Rcrd_Num",Integer.toString(ssrs3.MaxCol),Insu_Detail);//#险种循环记录条数 
        Element Busi_List =  new ElementLis("Busi_List",Insu_Detail);//险种信息循环Busi_List，每个险种的标签用Busi_Detail
        for (int i = 0; i < ssrs3.MaxCol; i++) {
	        ElementLis Busi_Detail = new ElementLis("Busi_Detail",Busi_List);
	        ElementLis Cvr_ID = new ElementLis("Cvr_ID",ssrs3.GetText(i, 1),Busi_Detail);//险种编号
	        ElementLis Cvr_Nm = new ElementLis("Cvr_Nm",ssrs3.GetText(i, 2),Busi_Detail);//险种名称
	        ElementLis MainAndAdlIns_Ind = new ElementLis("MainAndAdlIns_Ind",Busi_Detail);//主附险标志
	        ElementLis Ins_Cps = new ElementLis("Ins_Cps",Busi_Detail);//投保份数 
	        ElementLis InsPrem_Amt = new ElementLis("InsPrem_Amt",ssrs3.GetText(i, 3),Busi_Detail);//保费金额 
	        ElementLis Ins_Cvr = new ElementLis("Ins_Cvr",ssrs3.GetText(i, 4),Busi_Detail);//投保保额
	        ElementLis Ins_Scm_Inf = new ElementLis("Ins_Scm_Inf",Busi_Detail);//投保方案信息
	        ElementLis Opt_Part_DieIns_Amt = new ElementLis("Opt_Part_DieIns_Amt",Busi_Detail);//可选部分身故保险金额
	        ElementLis FTm_Extr_Adl_InsPrem = new ElementLis("FTm_Extr_Adl_InsPrem",Busi_Detail);//首次额外追加保费
	        ElementLis Ivs_MtdCd = new ElementLis("Ivs_MtdCd",Busi_Detail);//投资方式代码
	        ElementLis InsPolcy_Rcv_MtdCd = new ElementLis("InsPolcy_Rcv_MtdCd","3",Busi_Detail);//保单领取方式代码
	        ElementLis Ins_PyF_AR_ID = new ElementLis("Ins_PyF_AR_ID",Busi_Detail);//
	        ElementLis CCB_AccNo = new ElementLis("CCB_AccNo",Busi_Detail);//建行账号
	        ElementLis Ins_Yr_Prd_Cgy_Cd = new ElementLis("Ins_Yr_Prd_Cgy_Cd","2",Busi_Detail);//保险年期类别代码
	        ElementLis Ins_Ddln = new ElementLis("Ins_Ddln",Busi_Detail);//保险期限
	        ElementLis Ins_Cyc_Cd = new ElementLis("Ins_Cyc_Cd",Busi_Detail);//保险周期代码
	        ElementLis InsPrem_PyF_Cyc_Cd = new ElementLis("InsPrem_PyF_Cyc_Cd",Busi_Detail);//保费缴费周期代码
	        ElementLis Ins_PyF_MtdCd = new ElementLis("Ins_PyF_MtdCd","12",Busi_Detail);//保险缴费方式代码
	        ElementLis InsPrem_PyF_Prd_Num = new ElementLis("InsPrem_PyF_Prd_Num",ssrs3.GetText(i, 5),Busi_Detail);//保费缴费期数
	        ElementLis spcl_Apnt_Inf = new ElementLis("spcl_Apnt_Inf",Busi_Detail);//特别约定信息
	        ElementLis Anuty_Rcv_Cyc_CgyCd = new ElementLis("Anuty_Rcv_Cyc_CgyCd",Busi_Detail);//
	        ElementLis ExpPrmmRcvModCgyCd = new ElementLis("ExpPrmmRcvModCgyCd","0",Busi_Detail);//满期保险金领取方式类别代码 
	        ElementLis SvBnf_Rcv_Cyc_CgyCd = new ElementLis("SvBnf_Rcv_Cyc_CgyCd","0",Busi_Detail);//生存金领取周期代码
	        ElementLis XtraDvdn_Pcsg_MtdCd = new ElementLis("XtraDvdn_Pcsg_MtdCd",ssrs3.GetText(i, 6),Busi_Detail);//红利处理方式代码 
	        ElementLis ApntInsPremPyAdvnInd = new ElementLis("ApntInsPremPyAdvnInd",ssrs3.GetText(i, 7),Busi_Detail);//约定保费垫交标志 
	        ElementLis Auto_RnwCv_Ind = new ElementLis("Auto_RnwCv_Ind",Busi_Detail);//自动续保标志 
	        ElementLis Dspt_Arbtr_MtdCd = new ElementLis("Dspt_Arbtr_MtdCd",Busi_Detail);//争议处理方式代码
	        ElementLis Dspt_Arbtr_Inst_Nm = new ElementLis("Dspt_Arbtr_Inst_Nm",Busi_Detail);//争议仲裁机构名称
	        ElementLis Minr_Acm_Cvr = new ElementLis("Minr_Acm_Cvr",Busi_Detail);//未成年人累计保额
	        ElementLis Hlt_Ntf_Ind = new ElementLis("Hlt_Ntf_Ind",Busi_Detail);
	        ElementLis XtraDvdn_Alct_Ind = new ElementLis("XtraDvdn_Alct_Ind",Busi_Detail);//红利分配标志
	        ElementLis RdAmtPyCls_Ind = new ElementLis("RdAmtPyCls_Ind",Busi_Detail);//减额交清标志 
	        ElementLis InsPlocy_EfDt = new ElementLis("InsPlocy_EfDt",ssrs3.GetText(i, 8),Busi_Detail);//保单生效日期
	        ElementLis InsPlocy_ExpDt = new ElementLis("InsPlocy_ExpDt",ssrs3.GetText(i, 9),Busi_Detail);//保单失效日期 
	        ElementLis AcIsAR_StCd = new ElementLis("AcIsAR_StCd",Busi_Detail);//代理保险合约状态代码 
	        ElementLis Emgr_CtcPsn = new ElementLis("Emgr_CtcPsn",Busi_Detail);//紧急联系人
	        ElementLis Emgr_Ctc_Tel = new ElementLis("Emgr_Ctc_Tel",Busi_Detail);//紧急联系电话
	        ElementLis IvLkIns_Acc_Num = new ElementLis("IvLkIns_Acc_Num",Busi_Detail);//投连险账户个数
	        //投连险 -- 保险公司没有，放空
	        ElementLis PayAcctCode_List = new ElementLis("PayAcctCode_List",Busi_Detail);//投连险账号循环PayAcctCode_List，每个投连险账号用PayAcctCode_Detail
	        ElementLis PayAcctCode_Detail = new ElementLis("PayAcctCode_Detail",PayAcctCode_List);
	        ElementLis ILIVA_ID = new ElementLis("ILIVA_ID",PayAcctCode_Detail);//投连险虚账户编号
	        ElementLis Ivs_Tp_Alct_Pctg = new ElementLis("Ivs_Tp_Alct_Pctg",PayAcctCode_Detail);//投资类型分配比例 
	        ElementLis Adl_Ins_Fee_Alct_Pctg = new ElementLis("Adl_Ins_Fee_Alct_Pctg",PayAcctCode_Detail);//追加保险费分配比例
        }
        ElementLis InsPolcy_Vchr_No = new ElementLis("InsPolcy_Vchr_No",Insu_Detail);//保单凭证号码
        ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",Insu_Detail);//投保单号
        ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",tContNo,Insu_Detail);//保单号码
        ElementLis CstMgr_ID = new ElementLis("CstMgr_ID",Insu_Detail);//客户经理编号 
        String tsqlP = "select BANK_ACC_NO from Relation_Person_info where RELATION='P' and policy_no='" + tContNo + "'";
        String tPlchd_PyF_AccNo = new ExeSQL().getOneValue(tsqlP);
        ElementLis Plchd_PyF_AccNo = new ElementLis("Plchd_PyF_AccNo");//投保人缴费账号 
        Plchd_PyF_AccNo.setText(tPlchd_PyF_AccNo);
        String tsqlA = "select BANK_ACC_NO from Relation_Person_info where RELATION='A' and policy_no='" + tContNo + "'";
        String tPlchd_Drw_AccNo = new ExeSQL().getOneValue(tsqlA);
        ElementLis Plchd_Drw_AccNo = new ElementLis("Plchd_Drw_AccNo",Insu_Detail);//投保人领取账号
        Plchd_Drw_AccNo.setText(tPlchd_Drw_AccNo);
        ElementLis Rcgn_AccNo = new ElementLis("Rcgn_AccNo",Insu_Detail);//被保人账号  
        ElementLis Benf_AccNo = new ElementLis("Benf_AccNo",Insu_Detail);//受益人账号
        ElementLis Rnew_PyF_PyMd_Cd = new ElementLis("Rnew_PyF_PyMd_Cd",Insu_Detail);//续期缴费支付方式代码
        ElementLis Rsrv_Fld_1 = new ElementLis("Rsrv_Fld_1",Insu_Detail);//#保留字段一 
        ElementLis Rsrv_Fld_2 = new ElementLis("Rsrv_Fld_2",Insu_Detail);//#保留字段二 
        ElementLis Rsrv_Fld_3 = new ElementLis("Rsrv_Fld_3",Insu_Detail);//#保留字段三 
        ElementLis Rsrv_Fld_4 = new ElementLis("Rsrv_Fld_4",Insu_Detail);//#保留字段四
        ElementLis Rsrv_Fld_5 = new ElementLis("Rsrv_Fld_5",Insu_Detail);//#保留字段五
        ElementLis Rsrv_Fld_6 = new ElementLis("Rsrv_Fld_6",Insu_Detail);//#保留字段六
		return Insu_Detail;
	}
}
