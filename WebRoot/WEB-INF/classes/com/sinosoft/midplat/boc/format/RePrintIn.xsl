<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--�ش�����-->
		<TranData><!-- ���ı����ش������� -->
									
			<Head>
				<TranDate>
					<xsl:value-of select="/InsuReq/Main/TranDate"/>
				</TranDate>
				<!--���н�������-->
				<TranTime>
					<xsl:value-of select="/InsuReq/Main/TranTime" />
				</TranTime>
				<!--���д���(cd05)-->
				<TellerNo>
					<xsl:value-of
						select="/InsuReq/Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="/InsuReq/Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="/InsuReq/Main/BkBrchNo" />
				</NodeNo>
				<!--������ˮ��-->
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>

			<Body>
				<ContNo>
					<xsl:value-of select="/InsuReq/Main/PolicyNo"/>
				</ContNo>
				<!--������ -->
				<ContPrtNo>
					<xsl:value-of select="/InsuReq/InsuReq_Body/PrintNo"/>
				</ContPrtNo>
				<!--����ӡˢ��-->
				<ProposalPrtNo></ProposalPrtNo>
					<!--Ͷ������-->
					<OldContPrtNo></OldContPrtNo>
					<!-- �ɱ���ӡˢ�� -->
					<OldTranNo><xsl:value-of select="/InsuReq/InsuReq_Body/BkOthOldSeq"/></OldTranNo>
					<!-- ԭ������ˮ�� -->
				<Password></Password>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
