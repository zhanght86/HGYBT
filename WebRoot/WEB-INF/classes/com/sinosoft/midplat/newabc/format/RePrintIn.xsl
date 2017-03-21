<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- ���ı����ش������� -->
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
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	<Body>
		<ContNo></ContNo>
		<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)"/></ProposalPrtNo>
		<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/NewVchNo)"/></ContPrtNo>
		<OldContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/OldVchNo)"/></OldContPrtNo>
	</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>