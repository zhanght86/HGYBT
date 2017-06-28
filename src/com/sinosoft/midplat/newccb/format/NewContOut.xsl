<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<TX>
			<!-- ����ͷ -->
			<TX_HEADER>
			     <!-- ����ͷ���� -->
				<SYS_HDR_LEN></SYS_HDR_LEN>
			     <!-- Э��汾�� -->
				<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
			     <!-- �����ܳ��� -->
				<SYS_TTL_LEN></SYS_TTL_LEN>
			     <!-- ���ͷ���ȫ�ڵ��� -->
				<SYS_SND_SEC_ID>510050</SYS_SND_SEC_ID>
			     <!-- ���𷢰�ȫ�ڵ���  ת���������-->
				<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
			     <!--��������-->
				<SYS_TX_TYPE>020000</SYS_TX_TYPE>
			     <!-- ȫ���¼����ٺ�  ת���������-->
				<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
			     <!-- �ӽ������  ת���������-->
				<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
			     <!-- Ӧ�ñ��ĸ�ʽ���� -->
				<SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
			     <!-- Ӧ�ñ��ĳ���  ת���������-->
				<SYS_MSG_LEN></SYS_MSG_LEN>
			     <!-- Ӧ�ñ����Ƿ���� -->
				<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
			     <!-- Ӧ�ñ��ļ��ܷ�ʽ -->
				<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
			     <!-- Ӧ�ñ���ѹ����ʽ -->
				<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
			     <!-- �Ӵ����ĳ��� -->
				<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
			     <!-- �������ʱ�� ת���������-->
				<SYS_RECV_TIME></SYS_RECV_TIME>    
			     <!-- ������Ӧʱ��  ת���������-->
				<SYS_RESP_TIME></SYS_RESP_TIME>
			     <!-- ����״̬����  -->
				<SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
				<xsl:if test = "/TranData/Head/Flag='0'">
			     <!-- ����״̬ -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- ������Ӧ�� -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/Head/Flag !='0'">
			     <!-- ����״̬ -->
				<SYS_TX_STATUS>01</SYS_TX_STATUS>    
			     <!-- ������Ӧ�� -->
				<SYS_RESP_CODE></SYS_RESP_CODE>    
				</xsl:if>
			     <!-- ������Ӧ��������  ת���������-->
				<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
			     <!-- ������Ӧ���� -->
				<SYS_RESP_DESC></SYS_RESP_DESC>    
				<xsl:copy-of select="Head/*"/>
			</TX_HEADER>
	
		<!-- ������ -->
			<TX_BODY>
	      			<COMMON>
	         			<FILE_LIST_PACK>
	         				<!-- �ļ����� -->
				            <FILE_NUM>0</FILE_NUM>
				            <!-- �ļ�����ʽ -->
				            <FILE_MODE>0</FILE_MODE>
				        	<!-- �ļ��ڵ� -->
				            <FILE_NODE></FILE_NODE>
				            <!-- �������ļ��� -->
				            <FILE_NAME_PACK></FILE_NAME_PACK>
				            <!-- ����ļ���ȡ·�� -->
				            <FILE_PATH_PACK></FILE_PATH_PACK>
				            <!-- �ļ���Ϣ -->
				            <FILE_INFO>
				            <!-- �ļ���Ϣ -->
				               <FILE_NAME></FILE_NAME>
				            <!-- �ļ�·�� -->
				               <FILE_PATH></FILE_PATH>
				            </FILE_INFO>
	         			</FILE_LIST_PACK>
	      			</COMMON>
	      			<ENTITY>
			        	<APP_ENTITY>
			        		<!-- �������ײͱ�� -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
			        		<!-- ���ָ��� -->
			        		<Cvr_Num><xsl:value-of select="count(/TranData/Body/Risk/RiskCode)"/></Cvr_Num>
			        		<Bu_List>
				        		<xsl:for-each select="/TranData/Body/Risk">
				        			<Bu_Detail>
					        			<!-- ���ֱ�� -->
					        			<Cvr_ID><xsl:value-of select="RiskCode" /></Cvr_ID>
					        			<!-- �����ձ�־ -->
					        			<xsl:if test="RiskCode != MainRiskCode">
					        				<MainAndAdlIns_Ind>0</MainAndAdlIns_Ind>
					        			</xsl:if>
					        			<xsl:if test="RiskCode!= MainRiskCode">
					        				<MainAndAdlIns_Ind>1</MainAndAdlIns_Ind>
					        			</xsl:if>
					        			<!-- ���ѽ�� -->
					        			<InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></InsPrem_Amt>
					        			<!-- Ͷ������ -->
				        				<Ins_Cps><xsl:value-of select="Mult"/></Ins_Cps>
				        				<!-- Ͷ������ -->
				        				<Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></Ins_Cvr>
				        			</Bu_Detail>
				        		</xsl:for-each>
				        	</Bu_List>
			        		<!-- Ͷ������ -->
			        		<Ins_BillNo><xsl:value-of select="substring(/TranData/Body/ProposalPrtNo,1,13)"/></Ins_BillNo>
			        		<!-- �ܱ��ѽ�� -->
			        		<Tot_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/PayPrem)"/></Tot_InsPrem_Amt>
			        		<!-- ���ڽɷѽ�� -->
			        		<Init_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/></Init_PyF_Amt>
			        		<!-- �껯���ѽ�� -->
			        		<Anulz_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)"/></Anulz_InsPrem_Amt>
			        		<!-- ���չ�˾��פ��Ա���� -->
			        		<Ins_Co_Acrdt_Stff_Nm><xsl:value-of select="/TranData/Body/SaleName"/></Ins_Co_Acrdt_Stff_Nm>
			        		<!-- ���չ�˾��פ��Ա��ҵ�ʸ�֤���� -->
			        		<InsCoAcrStCrQuaCtf_ID><xsl:value-of select="/TranData/Body/SaleCertNo"/></InsCoAcrStCrQuaCtf_ID>
			        		<!-- ���ѽɷѷ�ʽ���� format�������-->
			        		<InsPrem_PyF_MtdCd></InsPrem_PyF_MtdCd>
			        		<!-- �������ڽɴ����˺� ����û�У����зǱ����ֶ�-->
			        		<AgInsRgAutoDdcn_AccNo></AgInsRgAutoDdcn_AccNo>
			        		<!-- ÿ�ڽɷѽ����Ϣ -->
			        		<EcIst_PyF_Amt_Inf><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)"/></EcIst_PyF_Amt_Inf>
			        		<!-- ���ѽɷ����� -->
			        		<InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/Body/Risk/PayEndYear"/></InsPrem_PyF_Prd_Num>
			        		<!-- ���ѽɷ����ڴ���  format�������-->
			        		<InsPrem_PyF_Cyc_Cd></InsPrem_PyF_Cyc_Cd>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	<!-- ��������/�����־ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">2</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = ''">0</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">1</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">2</xsl:when>
			<xsl:when test="$PbInsuYearFlag = ''">3</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'M'">4</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'D'">5</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'A'">6</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	

<!-- �����ձ�־ no -->
<xsl:template name="tran_MainAndAdlIns_Ind">
	<xsl:param name="mainAndAdlIns_ind" />
	<xsl:choose>
    	<xsl:when test="$mainAndAdlIns_ind=1">1</xsl:when>	<!-- ����  -->
		<xsl:when test="$mainAndAdlIns_ind=2">0</xsl:when>	<!-- ������ -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>	