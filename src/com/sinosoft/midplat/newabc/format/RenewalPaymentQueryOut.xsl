<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<ABCB2I>
		<Header>
		<!--返回码 -->
		<RetCode></RetCode>
		<!--返回信息 -->
		<RetMsg></RetMsg>
		<!--银行交易流水号 -->
		<SerialNo></SerialNo>
		<!--保险公司流水号 -->
		<InsuSerial></InsuSerial>
		<!-- 交易时间-->
		<TransTime></TransTime>
		<!-- 交易日期-->
		<TransDate></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 交易编码-->
		<TransCode>1007</TransCode>
	</Header>
	<App>
		<Ret>
			<Appl>
				<!-- 投保人姓名 -->
				<Name></Name>
				<!-- 证件类型 -->
				<IDKind></IDKind>
				<!-- 证件号码 -->
				<IDCode></IDCode>
			</Appl>
			<!-- 应缴期数 -->
			<DuePeriod></DuePeriod>
			<!-- 应缴日期 -->
			<DueDate></DueDate>
			<!-- 应缴金额 -->
			<DueAmt></DueAmt>
			<!-- 保险公司方险种代码  format类中添加 -->
			<RiskCode></RiskCode>
			<!-- 保单号-->
			<PolicyNo></PolicyNo>
		</Ret>	
	</App>
</ABCB2I>
</xsl:template>
	<!--  保单状态 -->
	<xsl:template name="policyStatus" >
		<xsl:param name="PolicyStatus"></xsl:param>
			<xsl:if test="$PolicyStatus = 08">00</xsl:if><!-- 成功 -->
			<xsl:if test="$PolicyStatus = 02">01</xsl:if><!-- 退保 -->		
			<xsl:if test="$PolicyStatus = 06">03</xsl:if><!-- 犹撤 -->
			<xsl:if test="$PolicyStatus = 04">07</xsl:if><!-- 理赔终止 -->	
	</xsl:template>
	
	<!--  保全申请状态 -->
	<xsl:template name="bQStatus" >
		<xsl:param name="BQStatus"></xsl:param>
		<xsl:if test="$BQStatus= 0">S</xsl:if><!-- 成功 -->
		<xsl:if test="$BQStatus = 3">D</xsl:if><!-- 处理中 -->
	</xsl:template>
	
</xsl:stylesheet>
