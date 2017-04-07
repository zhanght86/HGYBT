<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">
<xsl:template match="TranData">
<InsuReq>
  <Main>
    <TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Date()"/></TranDate>
    <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
    <InsuId></InsuId>
    <ZoneNo></ZoneNo>
    <BrNo></BrNo>
    <TellerNo></TellerNo>
    <TransNo></TransNo>
    <TranCode></TranCode>
    <xsl:if test="Head/Flag='0'">
    <ResultCode>0000</ResultCode>
    </xsl:if>
    <xsl:if test="Head/Flag='1'">
    <ResultCode>0001</ResultCode>
    </xsl:if>
    <ResultInfo><xsl:value-of select="Head/Desc"/></ResultInfo>
    <ApplyNo>
      <xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/>
    </ApplyNo>
    <Premium><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Premium>
  </Main>
</InsuReq>
</xsl:template>
</xsl:stylesheet>
