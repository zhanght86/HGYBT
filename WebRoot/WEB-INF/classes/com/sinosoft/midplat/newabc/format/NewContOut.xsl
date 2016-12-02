<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:output indent='yes'/>
<xsl:template match="/TranData">
	  <ABCB2I><!-- ũҵ�����±�����Ӧ���� -->
		<!-- ������׳ɹ����ŷ�������Ľ�� -->
		<xsl:if test="Head/Flag='0'">
			<!--Ͷ����Ϣ-->
			<App>
				<Ret>
					<!-- Ͷ������ӡˢ ����-->
					<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
					<CorpCheckNo></CorpCheckNo>
	                <ValidDate></ValidDate>
	                <PolicyDueDate></PolicyDueDate>
			    	<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
			   		<Amnt><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></Amnt>
		   		</Ret>
		    </App>
		</xsl:if> 
	  </ABCB2I>
</xsl:template>
	
</xsl:stylesheet>