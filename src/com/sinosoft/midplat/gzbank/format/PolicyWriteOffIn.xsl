<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TXLife">
<TranData><!-- 核心当日撤单请求报文 -->
   <Head>
      <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
      <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
      <NodeNo><xsl:value-of select="Branch"/></NodeNo>
      <BankCode>0105</BankCode>
      <TellerNo><xsl:value-of select="Teller"/></TellerNo>
      <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
      <ResendFlag></ResendFlag>
      <ContNo><xsl:value-of select="PolNumber"/></ContNo> <!--保险单号-->
      <ContPrtNo></ContPrtNo> <!-- 新保单合同印刷号 -->
	  <TransNo></TransNo>
	  <Password></Password>
   </Body>
</TranData>
</xsl:template>
</xsl:stylesheet>