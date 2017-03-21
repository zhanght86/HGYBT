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
		<TransTime></TransTime>
		<!-- ����ʱ-->
		<TransDate></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���ױ���-->
		<TransCode>1007</TransCode>
	</Header>
	<App>
		<Ret>
			<Appl>
				<!-- Ͷ�������� -->
				<Name><xsl:value-of select="Body/Name"/></Name>
				<!-- ֤������ -->
				<IDKind><xsl:value-of select="Body/IDType"/></IDKind>
				<!-- ֤������ -->
				<IDCode><xsl:value-of select="Body/IDNo"/></IDCode>
			</Appl>
			<!-- Ӧ������ -->
			<DuePeriod><xsl:value-of select="Body/RecvNum"/></DuePeriod>
			<!-- Ӧ������ -->
			<DueDate><xsl:value-of select="RecvDate"/></DueDate>
			<!-- Ӧ�ɽ�� -->
			<DueAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></DueAmt>
			<!-- ���չ�˾�����ִ���  format������� -->
			<RiskCode></RiskCode>
			<!-- ������-->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
		</Ret>	
	</App>
</ABCB2I>
</xsl:template>
	
	<!--  ����״̬ -->
	<xsl:template name="policyStatus" >
		<xsl:param name="PolicyStatus"></xsl:param>
			<xsl:if test="$PolicyStatus = 08">00</xsl:if><!-- �ɹ� -->
			<xsl:if test="$PolicyStatus = 02">01</xsl:if><!-- �˱� -->		
			<xsl:if test="$PolicyStatus = 06">03</xsl:if><!-- �̳� -->
			<xsl:if test="$PolicyStatus = 04">07</xsl:if><!-- ������ֹ -->	
	</xsl:template>
	
	<!--  ��ȫ����״̬ -->
	<xsl:template name="bQStatus" >
		<xsl:param name="BQStatus"></xsl:param>
		<xsl:if test="$BQStatus= 0">S</xsl:if><!-- �ɹ� -->
		<xsl:if test="$BQStatus = 3">D</xsl:if><!-- ������ -->
	</xsl:template>
	
</xsl:stylesheet>
