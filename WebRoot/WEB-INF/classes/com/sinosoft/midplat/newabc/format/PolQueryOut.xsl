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
			<!-- 附加内容1 -->
			<prt1><xsl:value-of select="Body/prt1"/></prt1>
			<!-- 附加内容2 -->
			<prt2><xsl:value-of select="Body/prt2"/></prt2>
			<!-- 附加内容3 -->
			<prt3><xsl:value-of select="Body/prt3"/></prt3>
			<!-- 附加内容4 -->
			<prt4><xsl:value-of select="Body/prt4"/></prt4>
			<!-- 附加内容5 -->
			<prt5><xsl:value-of select="Body/prt5"/></prt5>
			<!-- 附加内容6 -->
			<prt6><xsl:value-of select="Body/prt6"/></prt6>
		</Ret>
	</App>
	</ABCB2I>
	</xsl:template>
	
	<!--  保单状态 -->
	<xsl:template name="policyStatus" >
		<xsl:param name="PolicyStatus"></xsl:param>
			<xsl:if test="$PolicyStatus = ''">05</xsl:if><!-- 	状态不明确 -->	
			<xsl:if test="$PolicyStatus = 01">04</xsl:if><!-- 	满期终止 -->
			<xsl:if test="$PolicyStatus = 02">01</xsl:if><!-- 	退保终止 -->
			<xsl:if test="$PolicyStatus = 03">08</xsl:if><!-- 	处理中 -->
			<xsl:if test="$PolicyStatus = 04">07</xsl:if><!-- 	理赔终止 -->
			<xsl:if test="$PolicyStatus = 05">01</xsl:if><!-- 	协退终止 -->
			<xsl:if test="$PolicyStatus = 06">03</xsl:if><!-- 	犹退终止 -->
			<xsl:if test="$PolicyStatus = 07">05</xsl:if><!-- 	失效终止 -->
			<xsl:if test="$PolicyStatus = 08">00</xsl:if><!-- 	有效 -->
			<xsl:if test="$PolicyStatus = 09">05</xsl:if><!-- 	失效 -->
			<xsl:if test="$PolicyStatus = 10"></xsl:if><!-- 	协议终止 -->
			<xsl:if test="$PolicyStatus = 11"></xsl:if><!-- 	理赔终止 -->		
			<xsl:if test="$PolicyStatus = 12"></xsl:if><!-- 	自垫终止 -->
			<xsl:if test="$PolicyStatus = 13"></xsl:if><!-- 	贷款终止 -->
	</xsl:template>
</xsl:stylesheet>
