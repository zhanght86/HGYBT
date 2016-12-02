<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<Transaction>
	<xsl:if test="/TranData/Head/Flag='0'">
	<Transaction_Body>
	<!-- ���ִ��� -->
	<PbInsuType>
			<xsl:call-template name="tran_MainRiskCode">
					<xsl:with-param name="MainRiskCode">
						<xsl:value-of select="TranData/Body/RiskCode"/>
					</xsl:with-param>
			</xsl:call-template>
	</PbInsuType>
	<!-- ������ -->
	<PbInsuSlipNo><xsl:value-of select="TranData/Body/ContNo"/></PbInsuSlipNo>
	<!-- Ͷ�������� -->
	<PbHoldName><xsl:value-of select="TranData/Body/AppntName"/></PbHoldName>
	<!-- ���������� -->
	<LiRcgnName><xsl:value-of select="TranData/Body/InSuName"/></LiRcgnName>
	<!-- �˱���� -->
	<LiLoanValue><xsl:value-of select="TranData/Body/EdorCTPrem"/></LiLoanValue>
	<!-- ������ -->
	<LiRatifyId><xsl:value-of select="TranData/Body/RiskCode"/></LiRatifyId>
	<!-- Ͷ����֤���� -->
	<PbHoldId><xsl:value-of select="TranData/Body/AppntIdNo"/></PbHoldId>
	<!-- �˱��ʺ� -->
	<BkAcctNo1><xsl:value-of select="TranData/Body/BankAccNo"/></BkAcctNo1>
	</Transaction_Body>    
	</xsl:if>
</Transaction> 
</xsl:template>   

	<!-- ���ִ���  -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 231201">0001</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
		<xsl:if test="$MainRiskCode = 231202">0002</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
		<xsl:if test="$MainRiskCode = 231203">0003</xsl:if><!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
		<xsl:if test="$MainRiskCode = 221201">0004</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$MainRiskCode = 231204">0005</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$MainRiskCode = 221301">0006</xsl:if><!-- �к���δ�������-->
	</xsl:template>

</xsl:stylesheet>
