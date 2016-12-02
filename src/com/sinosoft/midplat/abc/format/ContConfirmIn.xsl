<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">


<xsl:template match="Req">
<TranData><!-- �����շ�ǩ�������� -->
	<!--������Ϣ-->
	  	<Head>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="BankDate"/></TranDate>
	  		<!-- ����ʱ�� ũ�в�������ʱ�� ȡϵͳ��ǰʱ�� -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- ���н���ʱ�� -->
			<!-- <TranTime><xsl:value-of select="BankTime"/></TranTime> -->
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- ������� -->
			<NodeNo><xsl:value-of select="ZoneNo"/><xsl:value-of select="BrNo"/></NodeNo>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
	
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="Base/ProposalContNo"/></ProposalPrtNo>
			<!-- ����ӡˢ�� -->
			<ContPrtNo><xsl:value-of select="Base/PrtNo"/></ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="Base/PolApplyDate"/></PolApplyDate>
			<Prem><xsl:value-of select="Base/Prem"/></Prem>	
		</Body>
	</TranData>
</xsl:template>	

</xsl:stylesheet>
