<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I><!-- ũҵ�������ڽɷ�Ӧ���� -->
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
		<TransTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/TranDate)"/></TransTime>
		<!-- ����ʱ-->
		<TransDate><xsl:value-of select="Body/TranTime"/></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���д���-->
		<TransCode>1013</TransCode>
	</Header>
	<App>
		<Ret>
			<RiskCode></RiskCode>
			<ProdCode></ProdCode>
			<!--������ -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<Appl>
				<IDKind></IDKind>
				<IDCode></IDCode>
				<Name></Name>
			</Appl>
			<PayAcc></PayAcc>
			<PayAmt></PayAmt>
		</Ret>
	</App>
	</ABCB2I>
		
	</xsl:template>
</xsl:stylesheet>
