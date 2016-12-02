<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
<xsl:template match="InsuReq">
<TranData><!-- 核心收费签单请求报文 -->
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Main/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <ClientIp>127.0.0.1</ClientIp>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
      <ProposalPrtNo><xsl:value-of select="Main/ApplyNo"/></ProposalPrtNo>
      <Prem><xsl:value-of select="Main/Premium"/></Prem>
      <OldTranNo><xsl:value-of select="Main/OriginTransNo"/></OldTranNo>
      <ContNo />
      <ContPrtNo><xsl:value-of select="Main/PrintNo"/></ContPrtNo>
      <PayMode />
      <AcctNo><xsl:value-of select="Main/PayAcc"/></AcctNo>
   </Body>
</TranData>
</xsl:template>
</xsl:stylesheet>
