<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--�ش�����-->
		<TranData>
			
			<Head>
				<TranDate>
					<xsl:value-of select="/Transaction/Transaction_Header/BkPlatDate"/>
				</TranDate>
				<!--���н�������-->
				<TranTime>
					<xsl:value-of select="/Transaction/Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--���д���(cd05)-->
				<TellerNo>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkTellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="/Transaction/Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="/Transaction/Transaction_Header/BkBrchNo" />
				</NodeNo>
				<!--������ˮ��-->
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/Transaction/Head/*"/>
			</Head>

			<Body>
				<ContNo>
					<xsl:value-of select="/Transaction/Transaction_Body/PbInsuSlipNo"/>
				</ContNo>
				<!--������ -->
				<ContPrtNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkVchNo"/>
				</ContPrtNo>
				<!--����ӡˢ��-->
				<ProposalPrtNo></ProposalPrtNo>
					<!--Ͷ������-->
					<OldContPrtNo></OldContPrtNo>
					<!-- �ɱ���ӡˢ�� -->
					<OldTranNo><xsl:value-of select="/Transaction/Transaction_Body/BkOthOldSeq"/></OldTranNo>
					<!-- ԭ������ˮ�� -->
				<Password></Password>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
