<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
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
    <!--保单试算状态-->
    <TranType>
   		<xsl:call-template name="cont_state">
	        <xsl:with-param name="State">
			  <xsl:value-of select="Body/EdorType"/>
		    </xsl:with-param>
	    </xsl:call-template>
    </TranType>
    <!--退保\满期给付金额 -->
    <Backmium><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/EdorCTPrem)"/></Backmium>
    <Comments></Comments>
  </Main>
</InsuReq>
</xsl:template>
<!--保单状态-->
<xsl:template name="cont_state">
  <xsl:param name="State"></xsl:param>
  <xsl:choose>
  <xsl:when test="$State = '1'">Y</xsl:when><!--犹豫期内-->
  <xsl:when test="$State = '2'">X</xsl:when><!--犹豫期外 -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
