<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="REQUEST">
<TranData>
	<Head>
		<!-- ���н������� -->
		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
		<!-- ����ʱ�� �ַ���������ʱ�� ȡϵͳ��ǰʱ�� -->
		<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
		<!-- �������� -->
		<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
		<!-- ������� -->
		<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
		<!-- ��Ա���� -->
		<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
		<!-- ���н�����ˮ�� -->
		<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
		<!-- YBT��֯�Ľڵ���Ϣ -->
		<xsl:copy-of select="Head/*"/>
	</Head>
</TranData>
</xsl:template>
</xsl:stylesheet>