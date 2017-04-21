<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
<!--保费试算 返回样式表 -->
<RETURN>
	<!-- 应答状态 -->
	<ACKSTS>0</ACKSTS>
	<!-- 状态描述 -->
	<STSDESC>正常</STSDESC>
	<!-- 如果成功，返回下面节点 -->
	<xsl:if test="Head/Flag='0'"> 
		<!-- 交易应答科目 -->
		<BUSI>
			<!-- 交易科目 -->
			<SUBJECT>1</SUBJECT>
			<!-- 交易流水号 -->
			<TRANS></TRANS>
			<!-- 交易内容信息区 -->
			<CONTENT>
				<!-- 投保单基本信息 -->
				<MAIN>
					<!-- 首期保费（大写） -->
					<PREMC><xsl:value-of select="substring(Body/PremText,4)" /></PREMC>
					<!-- 首期保费 -->
					<PREM><xsl:value-of select="Body/Prem" /></PREM>
				</MAIN>
				<!-- 投保单号 -->
				<APPNO><xsl:value-of select="substring(Body/ProposalPrtNo,1,13)" /></APPNO>
				<!-- 保单号 -->
				<POL><xsl:value-of select="Body/ContNo" /></POL>
				<!-- 拒保原因代码 -->
				<REJECT_CODE>0000</REJECT_CODE>
				<!-- 拒保原因说明 -->
				<REJECT_DESC>交易成功</REJECT_DESC>
				<!-- 险种列表 -->
				<xsl:apply-templates select="Body" />
				<!-- 现金价值 -->
				<xsl:choose>
					<xsl:when test="boolean(//CashValues)">
						<xsl:apply-templates select="Body/Risk[RiskCode = MainRiskCode]/CashValues" />
					</xsl:when>
					<xsl:otherwise>
						<VT>
							<VTI></VTI>
						</VT>
					</xsl:otherwise>
				</xsl:choose>
			</CONTENT>
		</BUSI>
	</xsl:if>
	<!-- 如果失败，返回下面节点 -->
	<xsl:if test="Head/Flag!='0'">
		<!-- 交易应答科目 -->
		<BUSI>
			<!-- 交易应答科目 -->
			<SUBJECT>1</SUBJECT>
			<!-- 交易流水号 -->
			<TRANS></TRANS>
			<!-- 交易内容信息区 -->
			<CONTENT>
				<!-- 拒保原因代码 -->
				<REJECT_CODE>1234</REJECT_CODE>
				<!-- 拒保原因说明 -->
				<REJECT_DESC><xsl:value-of select="Head/Desc" /></REJECT_DESC>
			</CONTENT>
		</BUSI>
	</xsl:if>
</RETURN>
</xsl:template>

<!-- 险种模板 -->
<xsl:template name="Risks" match="Body">
	<!-- 险种列表 -->
	<PTS>
		<xsl:for-each select="Risk">
			<xsl:if test="RiskCode = MainRiskCode">
				<!-- 险种 -->
				<PT>
					<!-- 险种代码 -->
					<ID><xsl:value-of select="RiskCode"/></ID>
					<!-- 投保份数 -->
					<UNIT><xsl:value-of select="Mult"/></UNIT>
					<!--  缴费年期类型-->
					<CRG>
						<xsl:choose>
							<xsl:when test="PayIntv = '0'">1</xsl:when>
							<xsl:when test="PayEndYearFlag = 'A'">6</xsl:when><!-- 缴至某确定年龄  -->
							<xsl:when test="PayEndYearFlag = 'M'">5</xsl:when><!-- 月缴 -->
							<xsl:when test="PayEndYearFlag = 'Y'">2</xsl:when><!-- 年缴 -->
						</xsl:choose>			
					</CRG>	
					<!-- 险种名称 -->
					<NAME><xsl:value-of select="RiskName" /></NAME>
					<!-- 保障年期 -->
					<PERIOD><xsl:value-of select="InsuYear" /></PERIOD>
					<!-- 年金起领年龄 -->
					<DRAW_T><xsl:value-of select="GetYear" /></DRAW_T>
					<!-- 保险金额 -->
					<AMT><xsl:value-of select="Amnt" /></AMT>
					<!-- 投保金额 -->
					<PREM><xsl:value-of select="Prem" /></PREM>
				</PT>
			</xsl:if>
		</xsl:for-each>
	</PTS>
</xsl:template>

<!-- 现价模板 -->
<xsl:template name="CashValues" match="Body/Risk[RiskCode = MainRiskCode]/CashValues">
	<!-- 现金价值表 -->
	<VT>
		<!-- 循环现价 -->
		<xsl:for-each select="CashValue">
			<!-- 现金价值表项 -->
			<VTI>
				<!-- 生存年金 -->
				<LIVE/>
				<!-- 疾病身故保险金 -->
				<ILL/>
				<!-- 年份 -->
				<YEAR><xsl:value-of select="EndYear" /></YEAR>
				<!-- 年末 -->
				<END/>
				<!-- 年末现金价值 -->
				<CASH><xsl:value-of select="Cash" /></CASH>
				<!-- 意外身故保险金 -->
				<ACI/>
			</VTI>
		</xsl:for-each>
	</VT>
</xsl:template>
</xsl:stylesheet>