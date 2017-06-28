<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<TranData>
	<Head>
		<!-- YBT��֯�Ľڵ���Ϣ -->
		 <xsl:copy-of select="Head/*"/> <!-- -->
	</Head>
	<Body>
		<xsl:for-each select="Body/Detail">
		 	<!--ѭ���ڵ� -->
			<Detail>
				<!-- ���ִ��� -->
				<RiskCode><xsl:value-of select="RiskCode"/></RiskCode>
				<!-- ������ -->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!-- �ɷѷ�ʽ -->
				<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
				<!-- �ɷ��ڼ����� -->
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>
				<!-- �ɷ��ڼ� -->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				<xsl:choose>
					<!-- ���ı������Ʒ�������ڼ���105 -->
					<xsl:when test="InsuYear='105' and InsuYearFlag='A'"> 
						<!-- �����ڼ����� ������-->
						<InsuYearFlag>6</InsuYearFlag>
						<!-- �����ڼ� -->
						<InsuYear>199</InsuYear>
					</xsl:when>
					<xsl:otherwise>
						<!-- �����ڼ����� -->
						<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
						<!-- �����ڼ� -->
						<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
					</xsl:otherwise>
				</xsl:choose>
				<!-- ���� -->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!-- ���� -->
				<Amnt><xsl:value-of select="Amnt"/></Amnt>
			</Detail>
		</xsl:for-each>
		</Body>
	</TranData>
		
	</xsl:template>
	
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">2</xsl:when>	<!-- �½� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
		<xsl:when test=".=6">4</xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=12">5</xsl:when>	<!-- �꽻 -->
		<xsl:when test=".=-1">0</xsl:when>	<!-- ������ -->
	</xsl:choose>
</xsl:template>
	
<!-- �ɷ��ڼ�����-->
<xsl:template name="tran_payendyearflag" match="PayEndYearFlag">
	<xsl:choose>
		<xsl:when test=".='A'">0</xsl:when><!-- ����ĳȷ�����䡢���� -->
		<xsl:when test=".='M'">2</xsl:when><!-- �� -->
		<xsl:when test=".='D'">3</xsl:when><!-- �� -->
		<xsl:when test=".='Y'">4</xsl:when><!-- �� -->
	</xsl:choose>
</xsl:template>	

<!-- �����ڼ�����-->
<xsl:template name="tran_insuyearflag" match="InsuYearFlag">
	<xsl:choose>
		<xsl:when test=".='A'">5</xsl:when><!-- ���� -->
		<xsl:when test=".='M'">2</xsl:when><!-- �� -->
		<xsl:when test=".='D'">1</xsl:when><!-- �� -->
		<xsl:when test=".='Y'">4</xsl:when><!-- �� -->
	</xsl:choose>
</xsl:template>
	
</xsl:stylesheet>
