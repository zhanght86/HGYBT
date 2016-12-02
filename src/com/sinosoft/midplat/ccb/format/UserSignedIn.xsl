<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--�ͻ�ǩԼ-->
		<TranData>
		<xsl:for-each select="/Transaction/Transaction_Header">
			<Head>
				<TranDate>
					<xsl:value-of select="BkPlatDate"/>
				</TranDate>
				<!--���н�������-->
				<TranTime>
				   <xsl:value-of select="BkPlatTime" />
				</TranTime>
				<!--����ʱ��-->
			����<TranCom>03</TranCom> <!-- ���׵�λ(����/ũ����/������˾) -->
				<BankCode>0104</BankCode> <!--���з��д��� -->
				<!--���д���(cd05)-->
				<ZoneNo>
						<xsl:value-of select="substring(BkBrchNo, 1, 3)"/>
					</ZoneNo>
					<!--�������� -->
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--�������-->
					<TellerNo>	<!--�������й�Ա����Ϊ12λ���ҷ����ݿ�(LKTRANSSTATUS)�ж�Ӧ�ֶ�(BANKOPERATOR)Ϊ10λ���Զ���ȡ��10λ��-->
						<xsl:value-of select="BkTellerNo"/>
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
				<!--�����־(cd02) <xsl:value-of select="BkTxCode"/>-->
				<InsuID>
					<xsl:value-of select="PbInsuId"/>
				</InsuID>
				<xsl:copy-of select="../Head/*"/>
				<!--���չ�˾����(cd03) -->
			</Head>
			</xsl:for-each>
			<Body>
				<ContNo>
					<xsl:value-of select="/Transaction/Transaction_Body/PbInsuSlipNo"/>
				</ContNo>
				<AppntName>
					<xsl:value-of select="/Transaction/Transaction_Body/PbHoldName"/>
				</AppntName>
				<BankAccNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkAcctNo1"/>
				</BankAccNo>
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
