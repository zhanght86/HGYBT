<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--����-->
	<xsl:template match="InsuReq">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="Main/TranDate" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="Main/TranTime" /></TranTime>
				<!-- �������� -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Main/BrNo" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0107</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="Main/TellerNo" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Main/TransNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Main>
				<ContNo>	<xsl:value-of select="/InsuReq/Main/PolicyNo"/>	</ContNo><!-- ���յ��� -->
				<ContPrtNo><xsl:value-of select="/InsuReq/Main/InsurancePrintNo"/></ContPrtNo><!-- �±�����ͬӡˢ�� -->
				<ProposalPrtNo><xsl:value-of select="/InsuReq/Main/InsuranceSlipNo"/></ProposalPrtNo><!-- Ͷ����(ӡˢ)�� -->
			</Main>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
