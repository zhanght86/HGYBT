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
	  	
		<!-- 如果交易成功，才返回下面的结点 -->
		<xsl:if test="Head/Flag='0'">
			<!--投保信息-->
			<Base>
				<CompName>中韩人寿保险有限公司</CompName>
				<!-- 投保单（印刷 ）号-->
				<ProposalContNo><xsl:value-of select ="Body/ProposalPrtNo"/></ProposalContNo>
				<!-- 试算请求的交易流水号,不是必填字段 -->
				<ReqsrNo></ReqsrNo>
		    	<Prem><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Prem>
		   		<Amnt><xsl:value-of select ="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></Amnt>
		    </Base>
		</xsl:if> 
	  </Ret>
</xsl:template>
	
</xsl:stylesheet>