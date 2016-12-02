package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.utility.*;

public class CodeQueryBL {
	private final static Logger cLogger = Logger.getLogger(CodeQueryBL.class);

	public CErrors mErrors = new CErrors();

	private VData cOutVData = new VData();

	private GlobalInput cGlobalInput;

	/** �洢��ѯ���� */
	private String cCodeCondition;

	/** ҵ������ر��� */
	private LDCodeSchema cLDCodeSchema;

	public boolean submitData(VData pInVData, String pOperate) {
		cLogger.info("Into CodeQueryBL.submitData()...");

		if (!getInputData(pInVData)) {
			return false;
		}

		// ����ҵ����
		if (!queryData()) {
			return false;
		}

		cLogger.info("Out CodeQueryBL.submitData()!");
		return true;
	}

	public VData getResult() {
		return cOutVData;
	}

	private boolean getInputData(VData pInVData) {
		cLogger.debug("Into CodeQueryBL.getInputData()...");
		
		cGlobalInput = (GlobalInput) pInVData.getObjectByObjectName(
				"GlobalInput", 0);
		cLDCodeSchema = (LDCodeSchema) pInVData.getObjectByObjectName(
				"LDCodeSchema", 0);
		TransferData mTransData = (TransferData) pInVData
				.getObjectByObjectName("TransferData", 0);

		if (null == cGlobalInput) { // ���ڵ�½֮ǰ
			cGlobalInput = new GlobalInput();
			cGlobalInput.ManageCom = "86";
			cGlobalInput.Operator = "sys";
		}

		cCodeCondition = (String) mTransData.getValueByName("codeCondition");
		cLogger.debug("CodeCondition = " + cCodeCondition);
		if (null == cCodeCondition
				|| "".equals(cCodeCondition = cCodeCondition.trim())
				|| "null".equalsIgnoreCase(cCodeCondition)) {
			cCodeCondition = "1=1";
		}
		cCodeCondition = cCodeCondition.replace('#', '\'');

		cLogger.debug("Out debug.getInputData()!");
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean queryData() {
		cLogger.debug("Into CodeQueryBL.queryData()...");

		String mCodeType = cLDCodeSchema.getCodeType();
		cLogger.info("CodeType = " + mCodeType);

		// �������
		StringBuffer mSqlStrBuf = new StringBuffer();
		if ("ComCode".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select ComCode, Name from ldcom where ")
					.append(cCodeCondition).append(" and ComCode like '")
					.append(cGlobalInput.ManageCom).append("%'")
					.append(" order by ComCode");
		}
		
		// Ͷ���˻�
		else if ("account_code".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select code, codename from ldcode where 1=1  and ")
					.append(cCodeCondition).append(" and codetype ='account_code'");
		}

		
		
		// �����������
		else if ("Region_Code".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select distinct ZoneNo, (select (d.name) from ldcom d where d.comcode = n.managecom)  from nodemap n where ")
					.append(cCodeCondition)
					.append(" order by n.ZoneNo");
		}
		// ���е�������
		else if ("Branch_Code".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select NodeNo, AgentComName from NodeMap where ")
					.append(cCodeCondition)
					.append(" and type = '0' ")
					.append(" order by NodeNo");
		}
		// �������
		else if ("AgentCom".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCom, AgentComName from Agent where ")
					.append(cCodeCondition).append(" and ManageCom like '")
					.append(cGlobalInput.ManageCom).append("%'")
					.append(" order by AgentCom");
		} else if ("AgentCom-".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCom, AgentComName from Agent where ")
					.append(cCodeCondition).append(" order by AgentCom");
		}

		// ������
		else if ("Agent".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select AgentCode, AgentName from Agent where ")
					.append(cCodeCondition).append(" order by AgentCode");
		}

		/** ----------------������ Start-------------- */
		// ����
		else if ("Area_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select Code, CodeName from LDCODE where ")
					.append(cCodeCondition)
					.append(" And CodeType='area_type' order by Code");
		}
		// ���� �û�ҳ��
		else if ("Area_User".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select Code, CodeName from LDCODE where ")
					.append(cCodeCondition)
					.append(" And CodeType='area_type' order by Code");
		}
		//����
		else if ("Area_UserH".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select comcode, Name from LDCom where ")
					.append(cCodeCondition)
					.append("  order by comcode");
		}
		// ����
		else if ("City_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select citycode, cityname from ldcom where ")
					.append(cCodeCondition).append(" and citycode is not null order by areaid,citycode");
		}
		// ��������
		else if ("Trancom_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf.append("select Code, CodeName from ldcode where ")
					.append(cCodeCondition)
					.append(" And CodeType='trancom_bank' order by Code");
		}
		// ����
		else if ("AgentCom_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCom, AgentComName from NODEMAP where ")
					.append(cCodeCondition)
					.append("  and type = 0  and ManageCom like '86%' order by AgentCom");
		}
		// ����רԱ
		else if ("Agent_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCode, trim(AgentName) from NODEMAP where ")
					.append(cCodeCondition)
					.append(" group by agentcode,AgentName order by AgentCode");
		}
		// ���ִ���
		else if ("RiskCode_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select RISKCODE, RISKNAME from LMRISKAPP where ")
					.append(cCodeCondition)
					.append(" AND SUBRISKFLAG = 'M' order by RISKCODE");
		}
		// ������ϵͳ��־
		else if ("SysFlag_IT".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select Code, CodeName from ldcode where ")
					.append(cCodeCondition).append(" and CodeType='").append(mCodeType).append("'");
		}
		//����״̬
		else if ("Cont_Statue".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select Code, CodeName from ldcode where ")
					.append(cCodeCondition).append(" and CodeType='").append(mCodeType).append("' ORDER BY CODE");
		}
		/** ----------------������ End----------------- */

		//���� ����
		else if ("Area_CityNo".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("Select CityCode,CityName From LDCom where ")
					.append(cCodeCondition).append(" and 1=1 and citycode is not null").append(" order by CityCode");
		}
		// ����
		else if ("AgentCom_NodeMap".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCom, AgentComName,AgentCode,AgentName,UNITCODE,AGENTGRADE from NODEMAP where ")
					.append(cCodeCondition)
					.append("   and type = 0 order by AgentCom");
		} 
		// ����
		else if ("AgentCom_NodeMap_Input".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCom, AgentComName from NODEMAP where ")
					.append(cCodeCondition)
					.append( "  and type = 0  order by AgentCom");
		} 
		else if ("com_upcom".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select comcode,name from ldcom WHERE  ")
					.append(cCodeCondition)
					.append( "  AND COMGRADE!='04' order by COMCODE");
		}
		// ����
		else if ("AgentCode_NodeMap".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCode,AgentName from NODEMAP where ")
					.append(cCodeCondition)
					.append(" and type = 0 GROUP BY AGENTCODE,AGENTNAME"); 
		}
		//����רԱά��_�����ѯ_AXAAGENT
		else if ("AgentCode_AXAAGENT".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select AgentCode,TRIM(AgentName),UNITCODE from AXAAgent where ")
					.append(cCodeCondition)
					.append(" ORDER BY AGENTCODE");
		}
		//��Ʒ���۹���_��Ʒ��ѯ_LMRiskApp_RISKCODE
		else if ("LMRiskApp_RISKCODE".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select RiskCode,RiskName from LMRiskApp");
					
		}
		//��Ʒ���۹���_��Ʒ����_LDBankMap_BankCODE
		else if ("LDBankMap_BankCODE".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select riskcode,riskname from lmriskapp r,Ldbankmap b where ")
					.append(cCodeCondition)
					.append(" and b.codetype = 'risk_code' and b.insucode = r.riskcode");
		}
		else if ("MainRiskCode_Print".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select a.insu_code,b.riskname from bankandinsumap a,lmriskapp b where ")
					
					.append(cCodeCondition)
					.append(" and subriskflag != 'S' and a.INSU_CODE=b.RISKCODE   order by b.startdate");
		}
		//��ѯICBC����
		else if ("ICBC_MainRiskCode".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select ")
					.append("p.tran_code, ")
					.append("'('||m.riskcode||')'||m.riskname ,")
					.append("m.WebMult||'',")
					.append("m.WebPrem||'',")
					.append("m.WebAmnt||'',")
					.append("m.WebInsuYearFlag||'',")
					.append("m.WebInsuYear||'',")
					.append("m.WebPayEndYearFlag||'',")
					.append("m.WebPayEndYear||'',")
					.append("m.WebPayIntv||'',")
					.append("m.RiskCode||'', ")
					.append("m.WebAccCode||'', ")
					.append("m.WebAccRate||'', ")
					.append("(select codename from ldcode where codetype='account_code' and code=m.webacccode)||'' ,")
					.append("m.WebAccTimeFlag||'' ")
					.append(" from lmriskapp m,bankandinsumap p where ")
					.append(cCodeCondition)
					.append(" and subriskflag = 'M' and P.INSU_CODE=M.RISKCODE and p.trancom='011'  order by startdate");
		}
		//��ѯICBC������
		else if ("ICBC_SubRiskCode".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select ")
					.append("p.tran_code, ")
					.append("'('||m.riskcode||')'||m.riskname ,")
					.append("m.WebMult||'',")
					.append("m.WebPrem||'',")
					.append("m.WebAmnt||'',")
					.append("m.WebInsuYearFlag||'',")
					.append("m.WebInsuYear||'',")
					.append("m.WebPayEndYearFlag||'',")
					.append("m.WebPayEndYear||'',")
					.append("m.RiskCode||'' ")
					.append(" from lmriskapp m,bankandinsumap p where ")
					.append(cCodeCondition)
					.append(" and subriskflag = 'S' and P.INSU_CODE=M.RISKCODE and p.trancom='011' order by startdate");
		}
		
		//�ֹ�����ҳ��  �������Ͳ�ѯ
		else if ("balance_flag".equalsIgnoreCase(mCodeType)) {
			mSqlStrBuf
					.append("select insu_code,code_desc from bankandinsumap where ")
					.append("  codetype='TranCode' and bak4='manual' and ")
					.append(cCodeCondition)
					.append(" ORDER BY insu_code");
		}
		else if ("trancom_bankadd".equalsIgnoreCase(mCodeType)){
			mSqlStrBuf.append("select Code, CodeName,Code||'-',Code||'-'  from ldcode where ")
					.append(cCodeCondition).append(" and CodeType='trancom_bank")
					.append('\'').append(" order by Code");
		}
		else if ("Zone_No".equalsIgnoreCase(mCodeType)){
			mSqlStrBuf.append("select ZoneNo, ZoneName from lkbankcom where ")
					.append(cCodeCondition)
					.append(" order by ZoneNo");
		}
		
		// ��ѯldcode
		else {
			mSqlStrBuf.append("select Code, CodeName from ldcode where ")
					.append(cCodeCondition).append(" and CodeType='")
					.append(mCodeType).append('\'').append(" order by Code");
		}

		String mSqlStr = mSqlStrBuf.toString();
		cLogger.info("CodeQueryBL.SQL: " + mSqlStr);
		ExeSQL mExeSQL = new ExeSQL();
		String mResultStr = mExeSQL.getEncodedResult(mSqlStr, 1);
		
		/** -----------------������ Start---------------- */
		if ("Area_User".equalsIgnoreCase(mCodeType)) {
		String tSql = setTopCode(mResultStr, "86", "�ܹ�˾");
		StringBuffer sbSqlStr = new StringBuffer(tSql);
		mResultStr = sbSqlStr.toString();
		}
//		if ("Area_Print1".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***��������***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}

//		if ("City_Print".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***���г���***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("Trancom_Print".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***��������***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("AgentCom_Print".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***��������***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("Agent_Print".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***��������רԱ***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("RiskCode_Print".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***�������ִ���***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("SysFlag_IT".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***����ϵͳ***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
//		if ("Cont_Statue".equalsIgnoreCase(mCodeType)) {
//			String tSql = setTopCode(mResultStr, "", "***����״̬***");
//			StringBuffer sbSqlStr = new StringBuffer(tSql);
//			mResultStr = sbSqlStr.toString();
//		}
		
		
		/** ----------------������ End------------------- */

		System.out.println("�����"+mResultStr);
		if (mExeSQL.mErrors.needDealError()) {
			mErrors.copyAllErrors(mExeSQL.mErrors);
			cLogger.error(mExeSQL.mErrors.getFirstError());
			cLogger.error("Error sql: " + mSqlStr);
			return false;
		}
		cOutVData.add(mResultStr);

		cLogger.debug("Out debug.queryData()()!");
		return true;
	}

	public String setTopCode(String sql, String Code, String CodeName) {
		String rSql = "";
		StringBuffer sbSql = new StringBuffer("0|");
		if (sql != null && !"".equals(sql)) {
			int i1 = sql.indexOf('|', 1);
			int i2 = sql.indexOf('^', 1);
			String s1 = sql.substring(i1 + 1, i2);
			String s2 = sql.substring(i2);
			int i3 = Integer.valueOf(s1);
			int i4 = i3 + 1;

			sbSql.append(String.valueOf(i4));
			sbSql.append("^");
			sbSql.append(Code);
			sbSql.append("|");
			sbSql.append(CodeName);
			sbSql.append(s2);
			System.out.println(i1);
			System.out.println(i2);
			System.out.println(i3);
			System.out.println(i4);
			System.out.println(s2);
			System.out.println(sbSql);
			rSql = sbSql.toString();
		}
		return rSql;

	}

	public static void main(String[] args) {
		CodeQueryBL codeQueryBL = new CodeQueryBL();
		codeQueryBL.setTopCode("0|125^121201|�����^121209|", "0", "***��������***");
	}
}