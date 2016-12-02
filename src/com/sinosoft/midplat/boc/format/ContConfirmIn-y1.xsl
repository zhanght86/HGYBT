<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/InsuReq">
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
			<InsuReq>
				<ContNo><xsl:value-of select="Main/PolicyNo" /></ContNo> <!-- ���յ��� -->
                <ProposalPrtNo><xsl:value-of select="Main/ApplyNo" /></ProposalPrtNo> <!-- Ͷ����(ӡˢ)�� -->
                <ContPrtNo><xsl:value-of select="Main/PrintNo" /></ContPrtNo> <!-- ������ͬӡˢ�� -->
                 <!-- ���ڽɷ��ʺ� -->
                <BkAcctNo><xsl:value-of select="Main/PayAcc" /></BkAcctNo>
			</InsuReq>
		</TranData>
	</xsl:template>

</xsl:stylesheet>