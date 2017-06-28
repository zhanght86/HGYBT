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
		<SerialNo></SerialNo>
		<!--���չ�˾��ˮ�� -->
		<InsuSerial></InsuSerial>
		<!-- ����ʱ��-->
		<TransTime></TransTime>
		<!-- ��������-->
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
				<Name></Name>
				<!-- ֤������ -->
				<IDKind></IDKind>
				<!-- ֤������ -->
				<IDCode></IDCode>
			</Appl>
			<!-- Ӧ������ -->
			<DuePeriod></DuePeriod>
			<!-- Ӧ������ -->
			<DueDate></DueDate>
			<!-- Ӧ�ɽ�� -->
			<DueAmt></DueAmt>
			<!-- ���չ�˾�����ִ���  format������� -->
			<RiskCode></RiskCode>
			<!-- ������-->
			<PolicyNo></PolicyNo>
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
