<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--��ѯ-->
		<TranData>
			<Head>
			<xsl:for-each select="/Transaction/Transaction_Header">
				<TranDate>
					<xsl:value-of select="BkPlatDate"/>
				</TranDate>
				<!--���н�������-->
				<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
				<!--���н���ʱ��-->
				����<BankCode>0104</BankCode> <!--���з��д��� -->
				<!--���д���-->
				<ZoneNo>
						<xsl:value-of select="substring(BkBrchNo, 1, 3)"/>
					</ZoneNo>
					<!--�������� -->
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--�������-->
					<TellerNo>	<!--�������й�Ա����Ϊ12λ���ҷ����ݿ�(LKTRANSSTATUS)�ж�Ӧ�ֶ�(BANKOPERATOR)Ϊ10λ���Զ���ȡ��10λ��-->
						<xsl:value-of select="substring(BkTellerNo, 3)"/>
					</TellerNo>
				<!--��Ա����-->
				<TranNo>
					<xsl:value-of select="BkPlatSeqNo"/>
				</TranNo>
				<!--������ˮ��-->
				<SaleChannel>
					<xsl:value-of select="BkChnlNo"/>
				</SaleChannel>
				<!--������������-->
				<InsuID>
					<xsl:value-of select="PbInsuId"/>
				</InsuID>
				<!--���չ�˾����(cd03) -->
				<xsl:copy-of select="../Head/*"/>
			</xsl:for-each>
			</Head>
			<Body></Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
