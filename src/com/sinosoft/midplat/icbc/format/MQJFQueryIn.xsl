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
	<!-- �ӽ����� 401 -->
	<FuncFlag>42</FuncFlag>
	<FuncFlagDetail>421</FuncFlagDetail>
	<!-- �����ױ���  40 -->
	<AgentCom></AgentCom>
	<AgentCode></AgentCode>
</Head>
</xsl:template>

<xsl:template name="Body" match="Policy">
<PolNumber><xsl:value-of select="PolNumber"/></PolNumber>
</xsl:template>

</xsl:stylesheet>
