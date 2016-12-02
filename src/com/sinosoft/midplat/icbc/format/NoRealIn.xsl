<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<Count><xsl:value-of select="Body/Count" /></Count>
				<xsl:for-each select="Body/Detail">
					<Detail>
						<TranDate><xsl:value-of select="TranDate" /></TranDate>
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<TellerNo><xsl:value-of select="TellerNo" /></TellerNo>
						<TranNo><xsl:value-of select="TranNo" /></TranNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<!-- ��������,����������������������Ϊ03�����Ĵ��ֶε�ֵ��д���ģ�����Ϊ�˱�֤����Դ��ԭʼ�ԣ����Ǵ�ԭζ�����ݸ����İ�-->
						<SaleChannel><xsl:apply-templates select="SaleChannel" /></SaleChannel>
						<AppFlag><xsl:value-of select="AppFlag" /></AppFlag>
						<AppntName><xsl:value-of select="AppntName" /></AppntName>
						<AppntIDType><xsl:apply-templates select="AppntIDType" /></AppntIDType><!-- ֤��������Ҫӳ�� -->
						<AppntIDNo><xsl:value-of select="AppntIDNo" /></AppntIDNo>
						<AccNo><xsl:value-of select="AccNo" /></AccNo>
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>
	
<!-- ֤������ -->
<xsl:template name="tran_idtype" match="AppntIDType">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">A</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">B</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">C</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">4</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- ����  -->
	<xsl:when test=".=9">D</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ��������-->
<xsl:template name="tran_SaleChannel" match="SaleChannel">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ����-->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ���� -->
	<xsl:when test=".=3">3</xsl:when>	<!-- ����Ӫ��  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- ȫ��  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- �ֻ�����  -->
	<xsl:otherwise>03</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
