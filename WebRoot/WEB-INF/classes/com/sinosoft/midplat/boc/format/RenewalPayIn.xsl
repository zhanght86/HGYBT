<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--���ڽɷ�-->
	<xsl:template match="/">
		<TranData><!-- �������ڽɷ������� -->
			<Head>
				<TranDate>
					<xsl:value-of
						select="InsuReq/Main/TranDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="InsuReq/Main/TranTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="InsuReq/Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="InsuReq/Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="InsuReq/Main/ZoneNo" /><xsl:value-of select="InsuReq/Main/BrNo" />
				</NodeNo>
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>
			<Body>
				<PayMode>B</PayMode><!-- ����֧����ʽ���� -->
				<ProposalPrtNo /><!-- Ͷ����(ӡˢ)�� -->
				<TranNo /><!-- ���չ�˾��ˮ�� -->
				<ContNo><!-- �������� -->
					<xsl:value-of select="InsuReq/Main/PolicyNo"/>
				</ContNo>
				<Prem><xsl:value-of select="InsuReq/Main/Premium"/></Prem><!-- Ӧ�ս�� -->
				<PayAcc><xsl:value-of select="InsuReq/Main/PayAcc"/></PayAcc><!-- �ɷ��ʺ� -->
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
