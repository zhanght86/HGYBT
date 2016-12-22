<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="/TranData"><!-- ����Ӧ����ת��Ϊ��������Ӧ���� -->
<TXLife>
	<!-- ������-->
	<TransNo></TransNo>
	<!-- ǩ�����д���-->
	<QdBankCode></QdBankCode>
	<!-- �������д���-->
	<BankCode></BankCode>
	<!--����������� -->
	<Branch></Branch>
	<!--���ջ�������-->
	<InsuOrgNo></InsuOrgNo>
	<!-- ���н�������-->
	<TransExeDate></TransExeDate>
	<!-- ���н���ʱ��-->
	<TransExeTime></TransExeTime>
	<!-- ������ˮ��-->
	<TransRefGUID></TransRefGUID>
	<!-- ���в���Ա-->
	<Teller></Teller>
	<!-- ���в���Ա����-->
	<TellerName></TellerName>
	<CpicWater></CpicWater>
	<ResultCode>0<xsl:value-of select ="Head/Flag"/></ResultCode>
	<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
</TXLife>
</xsl:template>
</xsl:stylesheet>