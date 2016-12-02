<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--客户解约-->
		<TranData>
		<xsl:for-each select="/Transaction/Transaction_Header">
			<Head>
			<TranDate>
					<xsl:value-of select="BkPlatDate" />
				</TranDate>
				<!--银行交易日期-->
				<TranTime>
				   <xsl:value-of select="BkPlatTime" />
				</TranTime>
				<!--交易时间-->
				　　<TranCom>03</TranCom> <!-- 交易单位(银行/农信社/经代公司) -->
					<BankCode>0104</BankCode><!--银行代码(cd05)-->
				<ZoneNo>
						<xsl:value-of select="substring(BkBrchNo, 1, 3)"/>
					</ZoneNo>
					<!--地区代码 -->
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--网点代码-->
					<TellerNo>	<!--建行银行柜员代码为12位，我方数据库(LKTRANSSTATUS)中对应字段(BANKOPERATOR)为10位，自动截取后10位。-->
						<xsl:value-of select="BkTellerNo"/>
					</TellerNo>
				<!--柜员代码-->
				<TranNo>
					<xsl:value-of select="BkPlatSeqNo"/>
				</TranNo>
				<!--交易流水号-->
				<SaleChannel>
					<xsl:value-of select="BkChnlNo"/>
				</SaleChannel>
				<!--交易渠道代号-->
				<InsuID>
					<xsl:value-of select="PbInsuId"/>
				</InsuID>
				<!--保险公司代码(cd03) -->
				<xsl:copy-of select="../Head/*"/>
			</Head>
			</xsl:for-each>
			<Body>
				<ContNo>
					<xsl:value-of select="/Transaction/Transaction_Body/PbInsuSlipNo"/>
				</ContNo>
				<AppntName>
					<xsl:value-of select="/Transaction/Transaction_Body/PbHoldName"/>
				</AppntName>
				<BatType>
					<xsl:value-of select="/Transaction/Transaction_Body/BkTxType"/>
				</BatType>
				<GrpNo>
					<xsl:value-of select="/Transaction/Transaction_Body/LiRatifyPrtId"/>
				</GrpNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
