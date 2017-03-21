<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<ABCB2I>
	<App>
		<Ret>
			<!-- �˱��ɷ������ -->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amt)"/></Amt>
			<!-- �˱������� -->
			<FeeAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/FeeAmt)"/></FeeAmt>
			<!-- �ֽ��ֵ��ӡ���� -->
			<PrntCount><xsl:value-of select="count(Body/CashValues/CashValue)"/></PrntCount>
			<!-- �ֽ��ֵ���д�ӡ -->
			<Prnt>�������ĩ 			�ֽ��ֵ��</Prnt>
			<xsl:apply-templates select="/TranData/Body/CashValues"/>
			<!-- ���������� -->
			<ExpireDate><xsl:value-of select="Body/ExpireDate"/></ExpireDate>
		</Ret>
	</App>
</ABCB2I>
		
</xsl:template>
	
<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Prnt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,15)"/>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan0(Cash)"/>
				</Prnt>
		</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
