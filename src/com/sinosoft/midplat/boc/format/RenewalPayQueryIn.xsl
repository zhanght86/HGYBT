<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="InsuReq">
		<TranData><!-- 核心续期缴费查询请求报文 -->
			<Head>
				<TranDate>
					<xsl:value-of
						select="Main/TranDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="Main/TranTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="Main/ZoneNo" /><xsl:value-of select="Main/BrNo" />
				</NodeNo>
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>
			<Body>
				<!-- 投保单(印刷)号 -->
				<ProposalPrtNo />
				<!-- 保单号码 -->
				<ContNo>
					<xsl:value-of select="Main/PolicyNo"/>
				</ContNo>
				<!-- 保单密码 -->
				<Password><xsl:value-of select="Main/Password"/></Password>
				<!-- 保险公司代码 -->
				<CarrierCode><xsl:value-of select="Main/InsuId"/></CarrierCode>
				<!-- 保险产品代码 -->
				<RiskCode />
				<!-- 投保人证件类型 -->
				<IDType />
				<!-- 投保人证件号码 -->
				<IDNo />
				<!-- 投保人姓名 -->
				<Name />
				<!-- 银行交易渠道 -->
				<Channel><xsl:apply-templates select="Main/Channel"/></Channel>
			</Body>
		</TranData>
	</xsl:template>
	
	<xsl:template name="tran_channel"  match="Channel">
		<xsl:choose>
			<xsl:when test=".=0"></xsl:when><!-- 其他 -->
			<xsl:when test=".=1">0</xsl:when><!-- 柜面 -->
			<xsl:when test=".=2">1</xsl:when><!-- 网银 -->
			<xsl:when test=".=3"></xsl:when><!-- 手机银行 -->
			<xsl:when test=".=4">8</xsl:when><!-- 自助终端 -->
			<xsl:when test=".=5"></xsl:when><!-- 智能柜台 -->
			<xsl:when test=".=6"></xsl:when><!-- 呼叫中心 -->
			<xsl:when test=".=7"></xsl:when><!-- 中银开放平台 -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
