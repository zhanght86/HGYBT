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
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--保险公司流水号 -->
		<InsuSerial></InsuSerial>
		<!-- 交易日期-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- 交易时-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 银行代码-->
		<TransCode>1016</TransCode>
	</Header>
	<App>
		<Ret>
			<!-- 保单号 -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!--保险公司险种代码  -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!--险种名称  -->
			<RiskName><xsl:value-of select="Body/RiskName"/></RiskName>
			<!--  保单状态-->
			<PolicyStatus>
					<xsl:call-template name="policyStatus">
						<xsl:with-param name="PolicyStatus">
							<xsl:value-of select="Body/PolicyStatus"/>
						</xsl:with-param>
					</xsl:call-template>
			</PolicyStatus>
			<!-- 保单质押状态 -->
			<PolicyPledge>0</PolicyPledge>
			<!--  受理日期-->
			<AcceptDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/ApplyDate)"/></AcceptDate>
			<!--  保单生效日-->
			<PolicyBgnDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></PolicyBgnDate>
			<!--  保单到期日-->
			<PolicyEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/InsuEndDate)"/></PolicyEndDate>
			<!-- 投保份数 -->
			<PolicyAmmount><xsl:value-of select="Body/Mult"/></PolicyAmmount>
			<!--  保费-->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Amt>
			<!--  保额-->
			<Beamt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></Beamt>
			<!-- 保单价值 -->
			<PolicyValue><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/ContValue)"/></PolicyValue>
			<!--  自动转账授权账号-->
			<AutoTransferAccNo><xsl:value-of select="Body/AccNo"/></AutoTransferAccNo>
			<!--  附加内容-->
			<part1></part1>
			<part2></part2>
			<part3></part3>
			<part4></part4>
			<part5></part5>
			<part6></part6>
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
</xsl:stylesheet>
