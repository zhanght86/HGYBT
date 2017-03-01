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
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- ����ʱ��-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���ױ���-->
		<TransCode>1019</TransCode>
	</Header>
	<App>
		<Ret>
			<!-- �˱��ɷ������ -->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Amt>
			<!-- �˱������� -->
			<FeeAmt></FeeAmt>
			<!-- �ֽ��ֵ��ӡ���� -->
			<PrntCount></PrntCount>
			<!-- �ֽ��ֵ���д�ӡ -->
			<Prnt></Prnt>
			<!-- ���������� -->
			<ExpireDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuEndDate"/></ExpireDate>
		</Ret>
	</App>
</ABCB2I>
		
</xsl:template>
	
</xsl:stylesheet>
