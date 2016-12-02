<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="Req">
<TranData>
	  	<Head>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Date()"/></TranDate>
	  		<!-- ����ʱ�� ũ�в�������ʱ�� ȡϵͳ��ǰʱ�� -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- ���е����� -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- ���������� -->
			<NodeNo><xsl:value-of select="ZoneNo"/><xsl:value-of select="BrNo"/></NodeNo>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- ���б��� -->
			<BankCode>0102</BankCode>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
		<Body>
			<ContNo><xsl:value-of select="ConfirmInfo/ContNo"/></ContNo>
			<OldTranNo><xsl:value-of select="ConfirmInfo/OldTranNo"/></OldTranNo>
			<ProposalPrtNo></ProposalPrtNo>
		</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>