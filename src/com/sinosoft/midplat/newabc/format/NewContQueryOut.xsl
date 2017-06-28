<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I>
		<Header>
		<!--������ -->
		<RetCode></RetCode>
		<!--������Ϣ -->
		<RetMsg></RetMsg>
		<!--���н�����ˮ�� -->
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--���չ�˾��ˮ�� -->
		<InsuSerial></InsuSerial>
		<!-- ��������-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- ����ʱ-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���д���-->
		<TransCode>1005</TransCode>
	</Header>
	<App>
		<Ret>
			<!--�˱����-->
			<AppResult></AppResult>
			<!--������ -->
			<PolicyNo><xsl:value-of select ="Body/ContNo"/></PolicyNo>
			<!-- ���չ�˾�����ִ��� -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!-- ���� -->
			<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		</Ret>	
	</App>
</ABCB2I>
</xsl:template>
	
</xsl:stylesheet>
