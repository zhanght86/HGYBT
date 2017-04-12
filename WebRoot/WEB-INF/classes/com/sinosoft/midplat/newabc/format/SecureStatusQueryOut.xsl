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
		<TransTime></TransTime>
		<!-- 交易时-->
		<TransDate></TransDate>
		<!-- 银行代码-->
		<BankCode></BankCode>
		<!--保险公司代码 -->
		<CorpNo></CorpNo>
		<!-- 交易编码-->
		<TransCode>1014</TransCode>
	</Header>
	<App>
		<Ret>
			<!--险种代码 format类中添加-->
			<RiskCode></RiskCode>
			<!-- 保单号-->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!--保单状态 -->
			<PolicyStatus>
					<xsl:call-template name="policyStatus">
						<xsl:with-param name="PolicyStatus">
							<xsl:value-of select="Body/State"/>
						</xsl:with-param>
					</xsl:call-template>
			</PolicyStatus>
			<!--申请日期 -->
			<ApplyDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/ApplyDate)"/></ApplyDate>
			<!--生效日期 -->
			<ValidDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></ValidDate>
			<!--业务类别  format类中添加-->
			<BusinType></BusinType>
			<!--保全申请状态 -->
			<BQStatus>
					<xsl:call-template name="bQStatus">
						<xsl:with-param name="BQStatus">
							<xsl:value-of select="Body/BQState"/>
						</xsl:with-param>
					</xsl:call-template>
			</BQStatus>
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
	
	<!--  保全申请状态 -->
	<xsl:template name="bQStatus" >
		<xsl:param name="BQStatus"></xsl:param>
		<xsl:if test="$BQStatus= 0">S</xsl:if><!-- 	确认生效	 -->
		<xsl:if test="$BQStatus = 1">D</xsl:if><!-- 录入完成	 -->
		<xsl:if test="$BQStatus = 2">D</xsl:if><!-- 申请确认	 -->
		<xsl:if test="$BQStatus = 3">D</xsl:if><!-- 等待录入	 -->
		<xsl:if test="$BQStatus = 4">F</xsl:if><!-- 逾期终止	 -->
		<xsl:if test="$BQStatus = 5">D</xsl:if><!-- 审批修改	 -->
		<xsl:if test="$BQStatus = 6">D</xsl:if><!-- 确认未生效 -->
		<xsl:if test="$BQStatus = 7">F</xsl:if><!-- 保全撤销	 -->
		<xsl:if test="$BQStatus = 8">F</xsl:if><!-- 核保终止	 -->
		<xsl:if test="$BQStatus = 9">F</xsl:if><!-- 审批终止	 -->
		<xsl:if test="$BQStatus = a">D</xsl:if><!-- 审批通过	 -->
		<xsl:if test="$BQStatus = b">U</xsl:if><!-- 保全回退	 -->
		<xsl:if test="$BQStatus = c">F</xsl:if><!-- 保全终止	    在申请确认之前触发的状态   -->
		<xsl:if test="$BQStatus = d">F</xsl:if><!-- 强制终止		在保全确认界面点击触发的状态 -->
	</xsl:template>
	
</xsl:stylesheet>
