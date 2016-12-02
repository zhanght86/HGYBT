<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData>
	<!--������Ϣ-->
	<Head>
	        <!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- ������� -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
			<!--������ -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
			 <!-- ���ִ��� -->
			<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
			<!--ҵ������  -->
			<BusiType>
					<xsl:call-template name="busitype">
						<xsl:with-param name="BusiType">
							<xsl:value-of select="App/Req/BusinType" />
						</xsl:with-param>
					</xsl:call-template>
			</BusiType>
	</Body>
</TranData>
</xsl:template>

	<!--  ҵ������ -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- �̳� -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- ���� -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- �˱� -->
	</xsl:template>

<!-- ���ִ��� -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
</xsl:choose>
</xsl:template>

</xsl:stylesheet>