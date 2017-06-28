<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
<xsl:template match="InsuReq">
<TranData>
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
   		<!-- 保险单号 -->
        <ContNo />
        <!-- 投保单(印刷)号 -->
      	<ProposalPrtNo>
        	<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Main/ApplyNo)"/>
      	</ProposalPrtNo>
      	<!-- 保单合同印刷号 -->
      	<ContPrtNo>
	        <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Main/PrintNo)"/>
      	</ContPrtNo>
      	<Prem>
        	<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Main/Premium)"/>
      	</Prem>
      	<OldTranNo><xsl:value-of select="Main/OriginTransNo"/></OldTranNo>
      	<PayMode />
      	<BkAcctNo><xsl:value-of select="Main/PayAcc"/></BkAcctNo>
      	<!-- 缴费方式 -->
      	<BkPayMode></BkPayMode>
   </Body>
</TranData>
</xsl:template>
</xsl:stylesheet>
