<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<Transaction>
	<xsl:if test="/TranData/Head/Flag='0'">
	<Transaction_Body>
	<!-- ������ˮ�� -->
	<BkPlatSeqNo><xsl:value-of select="TranData/Body/TranNo"/></BkPlatSeqNo>
	<!-- ������/�����־ -->
	<BkTxCode>OPR012</BkTxCode>
		
 	 <BkPlatDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></BkPlatDate> 
 	 <BkPlatTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></BkPlatTime> 
		<xsl:apply-templates select="TranData/Body"/>
	</Transaction_Body>    
	</xsl:if>
</Transaction> 
</xsl:template>   

<xsl:template match="Body">
			<!-- �����ļ������� --> 
			<BkFileNum>1</BkFileNum>
			<Detail_List>
					<!-- ��ӡ��ʾ��Ϣ -->
					<BkFileDesc>��ԥ���˱�����</BkFileDesc>				
					<!-- �ؿ����� -->
					<BkType1>330072000001</BkType1>				
					<!-- ��֤��/�ؿ�ӡˢ�� -->
					<BkVchNo><xsl:value-of select="CardNo"/></BkVchNo>				
					<!-- ���ı���ӡ���� -->
					<BkRecNum>20</BkRecNum>				
			</Detail_List>
</xsl:template>
</xsl:stylesheet>
