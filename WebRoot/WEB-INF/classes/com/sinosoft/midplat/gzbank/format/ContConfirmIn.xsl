<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TXLife">
		<TranData>
		   <Head>
		   	   <!-- 交易日期 -->
		       <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
		       <!-- 交易时间 -->
		       <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
		       <!-- 银行代码 -->
		       <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>	
		       <!-- 地区代码 -->
		       <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>
		       <!-- 银行网点 -->
		       <NodeNo><xsl:value-of select="Branch"/></NodeNo>
		       <!-- 柜员代码 -->
		       <TellerNo><xsl:value-of select="Teller"/></TellerNo>
		       <!-- 交易流水号 -->
		       <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
		       <!-- YBT组织的节点信息 -->
			   <xsl:copy-of select="Head/*"/> 
		   </Head>
		   <Body>
		   	   <!-- 销售渠道 -->
		   	   <SaleChannel>0</SaleChannel>
		       <!-- 保险单号 -->
		       <ContNo />
		   	   <!-- 投保单(印刷)号 -->
		       <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>
		       <!-- 保单合同印刷号 -->
		       <ContPrtNo>1<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(ProviderPolicyNumber)"/></ContPrtNo>
		       <OldTranNo></OldTranNo>
			   <BkAcctNo><xsl:value-of select="PayInAcctNo" /></BkAcctNo>
		       <Prem></Prem>
		       <PayMode />
		       <AcctNo />
		       <BkPayMode>
		      		<xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="PayMode" />
						</xsl:with-param>
					</xsl:call-template>
			   </BkPayMode>
		   </Body>
		</TranData>
	</xsl:template>
	
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode"></xsl:param>
		<xsl:if test="$PayMode = 'CS'">0</xsl:if><!-- 现金 -->
		<xsl:if test="$PayMode = 'TR'">0</xsl:if><!-- 折代扣 -->
	</xsl:template>	

</xsl:stylesheet>