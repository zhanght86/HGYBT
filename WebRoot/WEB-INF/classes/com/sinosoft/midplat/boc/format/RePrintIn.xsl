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
					<xsl:value-of select="/InsuReq/Main/ZoneNo" /><xsl:value-of select="/InsuReq/Main/BrNo" />
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
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(/InsuReq/Main/PrintNo)"/>
				</ContPrtNo>
				<!--����ӡˢ��-->
				<!-- <ProposalPrtNo></ProposalPrtNo> -->
				<!--Ͷ������-->
				<!-- <OldContPrtNo></OldContPrtNo> -->
				<!-- �ɱ���ӡˢ�� -->
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
