package com.sinosoft.lis.citi;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class MissInfoConfirm {
	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private MMap cSubmitMMap = new MMap();
	
	public int iSuccNo = 0;
	

	SSRS tSSRSTEMP = new SSRS(); // ���������в�ѯ����ʹ��

	public MissInfoConfirm() {

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
	@SuppressWarnings("unchecked")
	public boolean submitData() throws MidplatException {
		VData mSubmitVData = new VData();
		mSubmitVData.add(getInputData());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}

		cSubmitMMap = null;
		return true;
		
	}

	private MMap getInputData() {
		cSubmitMMap.put("delete from AxaMissingInfoForPolicyTemp", "UPDATE");
		ExeSQL exeSql = new ExeSQL();
		String sSql = "SELECT EXTRACTEDDATE,POLICYNO,APPLICATIONNO,INSUREDIDTYPE,APPLICANTIDTYPE,APPLICANTIDNO," +
				"APPLICANTACCTNO,CUSTOMERNO,POLICYCANCELCODE,CANCELREASON,HADPROCESSED,BECOVERED FROM AxaMissingInfoForPolicyTemp";				
		tSSRSTEMP = exeSql.execSQL(sSql);
		iSuccNo = tSSRSTEMP.getMaxRow();
		for (int i = 1; i <= tSSRSTEMP.getMaxRow(); i++) {
			String oid = PubFun1.CreateMaxNo("MissInfo",1);
			String date = tSSRSTEMP.GetText(i, 1);
			String policyNo = tSSRSTEMP.GetText(i, 2);
			String applicationNo = tSSRSTEMP.GetText(i, 3);
			String insuredIdType = tSSRSTEMP.GetText(i, 4);
			String applicantIdType = tSSRSTEMP.GetText(i, 5);
			String applicantIdNo = tSSRSTEMP.GetText(i, 6);
			String applicantAcctNo = tSSRSTEMP.GetText(i, 7);
			String customerNo = tSSRSTEMP.GetText(i, 8);
			String cancelCode = tSSRSTEMP.GetText(i, 9);
			String cancelReason = tSSRSTEMP.GetText(i, 10);
			String hadProcessed = tSSRSTEMP.GetText(i, 11);
			String beCovered = tSSRSTEMP.GetText(i, 12);
			Integer makeDate = DateUtil.getCur8Date();
			String makeTime = DateUtil.getCur8Time();
			
			String sqlMissTemp = "select 1 from AxaMissingInfo a where a.policyNo='"+policyNo+"'";//�����޸�Ϊ1�ˣ�֮ǰ��*�ŵ�Ч��̫�ͣ����һ���û�õĶ�����
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql.execSQL(sqlMissTemp);
			if(tSSRS.getMaxRow()==1){
				String sql = "UPDATE AxaMissingInfo set EXTRACTEDDATE="+date+",APPLICATIONNO='"+applicationNo+"',INSUREDIDTYPE='"+insuredIdType
					+"',APPLICANTIDTYPE='"+applicantIdType+"',APPLICANTIDNO='"+applicantIdNo+"',APPLICANTACCTNO='"+applicantAcctNo+"',CUSTOMERNO='"
					+customerNo+"',POLICYCANCELCODE='"+cancelCode+"',CANCELREASON='"+cancelReason+"',hadProcessed="+hadProcessed+",BECOVERED="
					+beCovered+",ModifyDate="+makeDate+",ModifyTime='"+makeTime+"' where PolicyNo='"+policyNo+"'";
				
				cSubmitMMap.put(sql, "UPDATE");
			}else{
			String sql = "insert into AxaMissingInfo" + "(Oid,EXTRACTEDDATE,POLICYNO,APPLICATIONNO,INSUREDIDTYPE,APPLICANTIDTYPE,APPLICANTIDNO," +
					"APPLICANTACCTNO,CUSTOMERNO,POLICYCANCELCODE,CANCELREASON,HADPROCESSED,BECOVERED,MAKEDATE,MAKETIME) "
					+ "Values  " 
					+ "("+ oid +","+date+",'"+policyNo+"','"+ applicationNo + "','" + insuredIdType + "','" + 
					applicantIdType + "','" + applicantIdNo + "','" + applicantAcctNo + "','" + customerNo + "','" + 
					cancelCode + "','" + cancelReason + "'," + hadProcessed+","+beCovered+","+makeDate+",'"+makeTime
					+"')";
			cSubmitMMap.put(sql, "INSERT");
			}
		}
		cSubmitMMap.put("delete from AxaMissingInfoForPolicyTemp", "UPDATE");
		return cSubmitMMap;
	}
	
	public static void main(String[] args) {
		
	}
}
