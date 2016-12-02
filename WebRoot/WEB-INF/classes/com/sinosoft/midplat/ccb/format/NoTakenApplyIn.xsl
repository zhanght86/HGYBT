<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="Transaction/Transaction_Header" />
	<Body>
		<xsl:apply-templates select="Transaction/Transaction_Body" />
	</Body>
</TranData>
</xsl:template>
    
<xsl:template name="Head" match="Transaction_Header">
	<Head>
		<!--�������� -->
		<TranDate><xsl:value-of select="BkPlatDate"/></TranDate>
		<!--����ʱ�� -->
		<TranTime><xsl:value-of select="BkPlatTime"/></TranTime>
		<!--��Ա�� -->
		<TellerNo><xsl:value-of select="BkTellerNo"/></TellerNo>
		<!-- ������ˮ�� -->
		<TranNo><xsl:value-of select="BkPlatSeqNo"/></TranNo>
		<!-- �������� -->
		<NodeNo><xsl:value-of select="BkBrchNo"/></NodeNo>
		<xsl:copy-of select="../Head/*"/>
		<!-- ���б��� -->
		<BankCode>0104</BankCode>
	</Head>
</xsl:template>

<xsl:template name="Body" match="Transaction_Body">
	<!--��������-->
	<ContNo><xsl:value-of select="PbInsuSlipNo"/></ContNo>
	<!--��������-->
	<Password><xsl:value-of select="LiInsuSlipPWD"/></Password> 
	<!--���չ�˾����-->
	<CarrierCode></CarrierCode > 
	<!--���ղ�Ʒ����-->
	<RiskCode>
		<xsl:call-template name="tran_MainRiskCode">
			<xsl:with-param name="MainRiskCode">
				<xsl:value-of select="PbInsuType"/>
			</xsl:with-param>
		</xsl:call-template>
	</RiskCode> 
    <!--���н�������-->
	<Channel></Channel>
	<!--Ͷ����֤������-->
	<IDType></IDType >
	<!-- Ͷ����֤������ -->
    <IDNo><xsl:value-of select="PbHoldId"/></IDNo> 
    <!--Ͷ��������-->
    <Name></Name>
    <!--������֤������-->
	<IDType></IDType >
	<!--������֤������-->
    <IDNo></IDNo>
    <!--����������-->
    <Name></Name>

</xsl:template>

	<!-- �����ִ��� -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231201</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
		<xsl:if test="$MainRiskCode = 0002">231202</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
		<xsl:if test="$MainRiskCode = 0003">231203</xsl:if><!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
		<xsl:if test="$MainRiskCode = 0004">221201</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$MainRiskCode = 0005">231204</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$MainRiskCode = 0006">221301</xsl:if><!-- �к���δ�������-->
	</xsl:template>

</xsl:stylesheet>
