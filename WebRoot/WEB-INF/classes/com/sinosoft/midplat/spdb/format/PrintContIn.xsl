<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/REQUEST">
	 <TranData>
		<!--������Ϣ-->
		<Head>
	  		<!-- �������� -->
	  		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="string(java:com.sinosoft.midplat.common.DateUtil.getCur6Time())"/></TranTime>
			<!-- ���д��� -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
			<!-- �������� -->
			<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
			<!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			<xsl:copy-of select="Head/*"/>
		</Head>
		<Body>
			<!-- ���ִ��� -->
			<RiskCode/>
			<!-- ���յ��� -->
			<ContNo/>
			 <!-- ������ͬӡˢ�� -->
			<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/BILL_USED)"/></ContPrtNo>
			<!-- Ͷ����(ӡˢ)�� -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/APPNO)"/></ProposalPrtNo> 
		</Body>
	</TranData>
</xsl:template>
</xsl:stylesheet>