<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="SRCB">
		<!--�ش�����-->
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="Header/TransDate" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="Header/TransTime" /></TranTime>
				<!-- �������� -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Header/BranchNo" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0107</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="Header/TellerNo" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Header/SerialNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>

			<Body>
				<!--������ -->
				<ContNo>
					<xsl:value-of select="Body/PolicyNo"/>
				</ContNo>
				<!--����ӡˢ��-->
				<ContPrtNo>
					<xsl:value-of select="Body/InsurancePrintNo"/>
				</ContPrtNo>
				<!--Ͷ������-->
				<ProposalPrtNo><xsl:value-of select="Body/InsuranceSlipNo"/></ProposalPrtNo>
				<!-- �ɱ���ӡˢ�� -->
				<OldContPrtNo></OldContPrtNo>
				<!-- ԭ������ˮ�� -->
				<OldTranNo></OldTranNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
