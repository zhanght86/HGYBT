<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="TXLife/TXLifeRequest" />
	
	<Body>
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy" />
	</Body>
</TranData>
</xsl:template>

<xsl:template name="Head" match="TXLifeRequest">
<Head> 
	<!-- 交易日期 -->
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<!-- 交易时间 -->
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<!-- 交易单位(银行/农信社/经代公司) -->
	<BankCode>01</BankCode>
	<TranCom>1</TranCom>
	<!-- 银行网点 -->
	<NodeNo>
		<xsl:value-of select="OLifEExtension/RegionCode"/>
		<xsl:value-of select="OLifEExtension/Branch"/>
	</NodeNo>
	<!-- 柜员代码 -->
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<!-- 交易流水号 -->
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
	<!-- 子交易码 401 -->
	<FuncFlag>41</FuncFlag>
	<FuncFlagDetail>411</FuncFlagDetail>
	<!-- 主交易编码  40 -->
	<AgentCom></AgentCom>
	<AgentCode></AgentCode>
</Head>
</xsl:template>

    <xsl:template name="Body" match="Policy">
    <!-- 保单号 -->
    <PolNumber><xsl:value-of select="PolNumber"/></PolNumber>
     <ProviderFormNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/FormInstance/ProviderFormNumber"/></ProviderFormNumber>
    <xsl:variable name="AppntNode"
				select="/TXLife/TXLifeRequest/OLifE/Party[@id='Party_1']" />
	
    <!-- 投保人证件类型 -->
    <AppntIDType><xsl:value-of select="$AppntNode/GovtIDTC"/></AppntIDType> 
    <!-- 投保人证件号码 -->
    <AppntID><xsl:value-of select="$AppntNode/GovtID"/></AppntID> 
    <!-- 投保人姓名 -->
    <AppntName><xsl:value-of select="$AppntNode/FullName"/></AppntName>
    <xsl:variable name="InsNode"
				select="/TXLife/TXLifeRequest/OLifE/Party[@id='Party_2']" />
    <!-- 被保人证件类型 -->
    <InsuredIDType><xsl:value-of select="$InsNode/GovtIDTC"/></InsuredIDType> 
    <!-- 被保人证件号码 -->
    <InsuredID><xsl:value-of select="$InsNode/GovtID"/></InsuredID>
    <!-- 被保人姓名 -->
    <InsuredName><xsl:value-of select="$InsNode/FullName"/></InsuredName>
    <ReasonCode>
         <xsl:call-template name="tran_WithDrawReason">
					<xsl:with-param name="WithDrawReason">
								<xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason" />
				</xsl:with-param>
			</xsl:call-template>
    </ReasonCode>
    <xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 0">
    	 <ReasonName>移居国外</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 1 or /TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 3">
    	 <ReasonName>移居外地</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 2">
    	 <ReasonName>经济原因</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 4 or /TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 6">
    	 <ReasonName>险种不理想</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 5">
    	 <ReasonName>转公司投保</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 7">
    	 <ReasonName>营销员离职</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 8">
    	 <ReasonName>服务不满意</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 9">
    	 <ReasonName>其他原因</ReasonName>
    	</xsl:if>   
</xsl:template>
<xsl:template name="tran_WithDrawReason">
		<xsl:param name="WithDrawReason">99</xsl:param>
		<xsl:if test="$WithDrawReason =0">10</xsl:if><!-- 出国移民 -->
		<xsl:if test="$WithDrawReason =1">7</xsl:if><!-- 移居外地-->
		<xsl:if test="$WithDrawReason =2">9</xsl:if><!-- 经济原因 -->
		<xsl:if test="$WithDrawReason =3">7</xsl:if><!-- 移居外地 -->
		<xsl:if test="$WithDrawReason =4">4</xsl:if><!-- 险种不理想 -->
		<xsl:if test="$WithDrawReason =5">24</xsl:if><!-- 转公司投保 -->
		<xsl:if test="$WithDrawReason =6">4</xsl:if><!-- 险种不理想 -->
		<xsl:if test="$WithDrawReason =7">25</xsl:if><!-- 营销员离职 -->
		<xsl:if test="$WithDrawReason =8">5</xsl:if><!-- 服务不满意 -->
		<xsl:if test="$WithDrawReason =9">99</xsl:if><!-- 其他原因 -->
	</xsl:template>

</xsl:stylesheet>
