<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<!-- ���ճ��� Ӧ����ʽ�� -->
<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>����</STSDESC>
	<BUSI>
		<SUBJECT>1</SUBJECT>
		<TRANS><xsl:value-of select="TranData/Body/TranNo"/></TRANS>
		<CONTENT>
			<!-- ����ɹ�����������ڵ� -->
			<xsl:if test="TranData/Head/Flag='0'">
				<INSU_TRANS></INSU_TRANS>
				<APPNO><xsl:value-of select="TranData/Body/ContNo"/></APPNO>			
				<REJECT_CODE>0000</REJECT_CODE>
				<REJECT_DESC>�����׳ɹ�!</REJECT_DESC>
				<MAIN></MAIN>				
			</xsl:if>
			<!-- ���ʧ�ܣ���������ڵ� -->
			<xsl:if test="/TranData/Head/Flag!='0'">
				<REJECT_CODE>1234</REJECT_CODE>
				<REJECT_DESC><xsl:value-of select="TranData/Head/Desc" /></REJECT_DESC>
			</xsl:if>
		</CONTENT>
	</BUSI>
</RETURN>
</xsl:template>
</xsl:stylesheet>