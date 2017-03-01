<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="TranData">

		<ABCB2I><!-- 农业银行保全查询应答报文 -->
			<Header>
				<!--返回码 -->
				<RetCode></RetCode>
				<!--返回信息 -->
				<RetMsg></RetMsg>
				<!--银行交易流水号 -->
				<SerialNo></SerialNo>
				<!--保险公司流水号 -->
				<InsuSerial></InsuSerial>
				<!-- 交易日期 -->
				<TransTime>
					<xsl:value-of
						select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/TranDate)" />
				</TransTime>
				<!-- 交易时间 -->
				<TransDate>
					<xsl:value-of select="Body/TranTime" />
				</TransDate>
				<!-- 银行代码 -->
				<BankCode></BankCode>
				<!--保险公司代码 -->
				<CorpNo></CorpNo>
				<!-- 银行代码 -->
				<TransCode>1006</TransCode>
			</Header>
			<App>
				<Ret>
					<Appl>
						<!--投保人名称 -->
						<Name></Name>
						<!--投保人证件类型 -->
						<IDKind></IDKind>
						<!--投保人证件号码 -->
						<IDCode></IDCode>
					</Appl>
					<!--投保单号 -->
					<PolicyApplyNo>
						<xsl:value-of select="LCCont/ProposalContNo" />
					</PolicyApplyNo>
					<!--保险公司方险种代码 -->
					<RiskCode>
						<xsl:value-of select="LCCont/Risks/Risk/RiskCode" />
					</RiskCode>
					<!--产品代码 -->
					<ProdCode>
						<xsl:value-of select="LCCont/Risks/Risk/RiskCode" />
					</ProdCode>
					<!--保费 -->
					<Prem></Prem>
				</Ret>
			</App>
		</ABCB2I>
	</xsl:template>
</xsl:stylesheet>
