<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I><!-- ũҵ���б�ȫ��ѯӦ���� -->
		<Header>
		<!--������ -->
		<RetCode></RetCode>
		<!--������Ϣ -->
		<RetMsg></RetMsg>
		<!--���н�����ˮ�� -->
		<SerialNo></SerialNo>
		<!--���չ�˾��ˮ�� -->
		<InsuSerial></InsuSerial>
		<!-- ��������-->
		<TransTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/TranDate)"/></TransTime>
		<!-- ����ʱ��-->
		<TransDate><xsl:value-of select="Body/TranTime"/></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���д���-->
		<TransCode>1012</TransCode>
	</Header>
	<App>
		<Ret>
			<!--��������-->
			<InsuName><xsl:value-of select="Body/RiskName"/></InsuName>
			<!--������ -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!-- ����ȡ��� -->
			<OccurBala><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/EdorCTPrem)"/></OccurBala>
			<!-- ������Ч�� -->
			<ValidDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></ValidDate>
			<!--����������  -->
			<ExpireDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/InsuEndDate)"/></ExpireDate>
			<!--���д�ӡ���� -->
			<PrntCount>5</PrntCount>
			<!-- ���д�ӡ����1..n -->
			<Prnt1></Prnt1>
			<Prnt2></Prnt2>
			<Prnt3></Prnt3> 
			<Prnt4></Prnt4>
			<Prnt5></Prnt5>
		</Ret>	
	</App>
</ABCB2I>
	</xsl:template>
</xsl:stylesheet>
