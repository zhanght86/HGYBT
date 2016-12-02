<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TXLife">
<TranData><!-- 核心收费签单请求报文 -->
   <Head>
      <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
      <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
      <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>	<!-- 地区代码 -->
      <NodeNo><xsl:value-of select="Branch"/></NodeNo>		<!-- 网点代码 -->
      <BankCode>0105</BankCode>		
      <TellerNo><xsl:value-of select="Teller"/></TellerNo>
      <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
      <ServiceId>1</ServiceId>
   </Head>
   <Body>
   	  <SaleChannel>0</SaleChannel>
      <ProposalPrtNo><xsl:value-of select="HOAppFormNumber"/></ProposalPrtNo>
      <Prem></Prem>
      <OldTranNo></OldTranNo>
      <ContNo />
      <ContPrtNo><xsl:value-of select="ProviderPolicyNumber"/></ContPrtNo>
      <PayMode />
      <AcctNo />
	  <BkAcctNo><xsl:value-of select="PayInAcctNo" /></BkAcctNo>
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