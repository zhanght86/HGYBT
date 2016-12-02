<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="Transaction">
		<TranData>
			<Head>
				<TranDate>
					<xsl:value-of
						select="Transaction_Header/BkPlatDate" />
				</TranDate>
				<!--���н�������-->
				<TranTime>
				   <xsl:value-of select="Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--����ʱ��-->
				<BankCode>0104</BankCode>
				<!--���д���-->
				<NodeNo><xsl:value-of
						select="Transaction_Header/BkBrchNo" /></NodeNo>
				<!--�������-->
				<TellerNo>
					<xsl:value-of
						select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<!--��Ա����-->
				<TranNo>
					<xsl:value-of
						select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
					<xsl:copy-of select="Head/*"/>
				<!--�����־-->
			</Head>
			<Body>
			<xsl:variable name="BkChnlNo" select="Transaction_Header/BkChnlNo" />
			<xsl:variable name="BkBrchNo" select="Transaction_Header/BkBrchNo" />
			<xsl:variable name="BkTellerNo" select="Transaction_Header/BkTellerNo" />
				<Count>
					<xsl:value-of select="Transaction_Body/BkTotNum" />
				</Count>

				<xsl:for-each
					select="Transaction_Body/Detail_List/Detail">
					<Detail>
						<TranDate>
							<xsl:value-of select="BkPlatDate10" />
						</TranDate>
						<AgentCom>
							<xsl:value-of
								select="BkPlatBrch" />
						</AgentCom>
						<TellerNo>
							<xsl:value-of
								select="$BkTellerNo" />
						</TellerNo>
						<TranNo>
							<xsl:value-of select="BkChnlSeq" />
						</TranNo>
						<ContNo>
							<xsl:value-of select="PbInsuSlipNo" />
						</ContNo>
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(BkTotAmt)" />
						</Prem>
						<AppntName></AppntName>
						<ProposalPrtNo></ProposalPrtNo>
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
