<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="TXLife/TXLifeRequest" />
	
	<Body>
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy" />
	</Body>
</TranData>
</xsl:template>

<xsl:template name="Head" match="TXLifeRequest">
<Head> 
	<!-- �������� -->
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<!-- ����ʱ�� -->
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<!-- ���׵�λ(����/ũ����/������˾) -->
	<BankCode>01</BankCode>
	<TranCom>1</TranCom>
	<!-- �������� -->
	<NodeNo>
		<xsl:value-of select="OLifEExtension/RegionCode"/>
		<xsl:value-of select="OLifEExtension/Branch"/>
	</NodeNo>
	<!-- ��Ա���� -->
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<!-- ������ˮ�� -->
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
	<AgentCom />
    <AgentCode />
    <FuncFlag>40</FuncFlag>
	<FuncFlagDetail>402</FuncFlagDetail>
	<SourceType><xsl:apply-templates select="OLifEExtension/SourceType" /></SourceType>
</Head>
</xsl:template>

<xsl:template name="Body" match="Policy">
<PolNumber><xsl:value-of select="PolNumber"/></PolNumber>
<ProviderFormNumber></ProviderFormNumber>
<AccountNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Banking/AccountNumber" /></AccountNumber>
<AcctHolderName><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Banking/AcctHolderName" /></AcctHolderName>
</xsl:template>

<!-- ���� -->
<xsl:template name="tran_SourceType" match="SourceType">
<xsl:choose>
	<xsl:when test=".=0">ybt</xsl:when>	<!-- ����ͨ -->
	<xsl:when test=".=1">wy</xsl:when>	<!-- ���� -->
	<xsl:when test=".=8">atm</xsl:when>	<!-- �����ն� -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
