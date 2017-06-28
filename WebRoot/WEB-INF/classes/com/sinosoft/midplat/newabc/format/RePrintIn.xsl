<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- ���ı����ش������� -->
	<!--������Ϣ-->
	  	<Head>
	  		<!-- �������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ���д��� -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- �������� -->
			<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
	        <!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> 
	  	</Head>
	<Body>
		<!-- �������� -->
		<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
		<!-- ���յ��� -->
		<ContNo></ContNo>
		<!-- Ͷ����(ӡˢ)�� -->
		<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)"/></ProposalPrtNo>
		<!-- �±�����ͬӡˢ�� -->
		<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/NewVchNo)"/></ContPrtNo>
		<!-- �ɵı�����ͬӡˢ�� -->
		<OldContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/OldVchNo)"/></OldContPrtNo>
	</Body>
</TranData>
</xsl:template>

<!-- ί�з�ʽ
01-������������
02-������������
04-���������ն�����
11-���й�̨����
20-���չ�˾����
 -->
<xsl:template name="tran_salechannel" match="EntrustWay">
	<xsl:choose>
		<xsl:when test=".=01">1</xsl:when><!-- ������������ -->
		<xsl:when test=".=02">2</xsl:when><!-- ������������ -->
		<xsl:when test=".=04">8</xsl:when><!-- ���������ն����� -->
		<xsl:when test=".=11">0</xsl:when><!-- ���й�̨���� -->
		<xsl:when test=".=20"></xsl:when><!-- ���չ�˾���� -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>