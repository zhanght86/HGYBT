<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:output indent='yes'/>
<xsl:template match="/TranData">
	  <Ret>
	  	<RetData>
	  		<Flag><xsl:value-of select ="Head/Flag"/></Flag>
	  		<Mesg><xsl:value-of select ="Head/Desc"/></Mesg>
	  	</RetData>
	  	
		<!-- ������׳ɹ����ŷ�������Ľ�� -->
		<xsl:if test="Head/Flag='0'">
			<!--Ͷ����Ϣ-->
			<Base>
				<CompName>�к����ٱ������޹�˾</CompName>
				<!-- Ͷ������ӡˢ ����-->
				<ProposalContNo><xsl:value-of select ="Body/ProposalPrtNo"/></ProposalContNo>
				<!-- ��������Ľ�����ˮ��,���Ǳ����ֶ� -->
				<ReqsrNo></ReqsrNo>
		    	<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		   		<Amnt><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></Amnt>
		    </Base>
		</xsl:if> 
	  </Ret>
</xsl:template>
	
</xsl:stylesheet>