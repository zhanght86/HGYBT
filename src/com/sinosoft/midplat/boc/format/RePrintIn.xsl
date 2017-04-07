<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="InsuReq">
		<!--�ش�����-->
		<TranData>
		  <Head>
		    <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
		    <TranTime><xsl:value-of select="Main/TranTime" /></TranTime>
		    <TellerNo><xsl:value-of select="Main/TellerNo" /></TellerNo>
			<TranNo><xsl:value-of select="Main/TransNo" /></TranNo>
		    <NodeNo><xsl:value-of select="Main/BrNo" /> </NodeNo>
		    <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
		    <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
		    <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
	        <AgentCom />
	        <AgentCode />
	        <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
	        <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
	        <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
		  </Head>
		  <Body>
		    <ContNo><xsl:value-of select="Main/PolicyNo"/></ContNo>
		    <!--������ -->
		    <ContPrtNo>
			  <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Main/PrintNo)"/>
		    </ContPrtNo>
		    <!--����ӡˢ��-->
		    <ProposalPrtNo></ProposalPrtNo>
		    <!--Ͷ������-->
		    <OldContPrtNo></OldContPrtNo>
			<!-- �ɱ���ӡˢ�� -->
		  </Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
