<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="TranData">
	  <TranData>
	  <Head>
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  </Head>
  		<!--投保信息-->
		<Body>
		<xsl:for-each select="Body/Detail">
			<Detail>
				 <!-- 业务类别 -->
				<BusiType >
						<xsl:call-template name="busitype">
							<xsl:with-param name="BusiType">
								<xsl:value-of select="BusiType" />
							</xsl:with-param>
						</xsl:call-template>
				</BusiType >
				<!-- 交易日期  -->
				<TranDate><xsl:value-of select="TranDate"/></TranDate> 
				<!-- 保单号-->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!-- 申请人姓名-->
				<AppntName><xsl:value-of select="AppntName"/></AppntName>
				<!-- 申请状态-->
				<Status><xsl:value-of select="Status"/></Status>
				<!-- 领取金额-->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!-- 受理渠道 -->
				<Channel><xsl:value-of select="Channel"/></Channel>
			</Detail>

 			</xsl:for-each>
		</Body>
	</TranData>
</xsl:template>	


	<!--  业务类型 -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 07">01</xsl:if><!-- 犹撤  -->
		<xsl:if test="$BusiType = 09">02</xsl:if><!-- 满期 -->
		<xsl:if test="$BusiType = 10">03</xsl:if><!-- 退保 -->
	</xsl:template>


</xsl:stylesheet>