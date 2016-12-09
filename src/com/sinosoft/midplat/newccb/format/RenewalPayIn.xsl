<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- �������� -->
				<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0104</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
			
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- ������ -->
		<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
		    <!-- ���ս��ɽ�� -->
			<Prem><xsl:value-of select="Ins_PyF_Amt" /></Prem>
			<!-- �����ʺ� -->
			<PayAcc><xsl:value-of select="CCB_AccNo" /></PayAcc>
			<!-- ����֧����ʽ���� -->
			<PayMode>B</PayMode>
			<!-- Ͷ������ -->
			<ProposalPrtNo></ProposalPrtNo>
			<!--��������-->
			<ContNo></ContNo>
			<!-- ���չ�˾������ˮ�� -->
			<TranNo><xsl:value-of select="Ins_Co_Jrnl_No" /></TranNo>
		</Body>
	</xsl:template>
	
</xsl:stylesheet>

