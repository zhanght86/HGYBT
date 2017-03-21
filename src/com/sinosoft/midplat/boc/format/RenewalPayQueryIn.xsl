<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="InsuReq">
		<TranData><!-- �������ڽɷѲ�ѯ������ -->
			<Head>
				<TranDate>
					<xsl:value-of
						select="Main/TranDate" />
				</TranDate>
				<TranTime>
					<xsl:value-of
						select="Main/TranTime" />
				</TranTime>
				<TellerNo>
					<xsl:value-of
						select="Main/TellerNo" />
				</TellerNo>
				<TranNo>
					<xsl:value-of
						select="Main/TransNo" />
				</TranNo>
				<NodeNo>
					<xsl:value-of select="Main/ZoneNo" /><xsl:value-of select="Main/BrNo" />
				</NodeNo>
				<BankCode>0122</BankCode>
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>
			<Body>
				<!-- Ͷ����(ӡˢ)�� -->
				<ProposalPrtNo />
				<!-- �������� -->
				<ContNo>
					<xsl:value-of select="Main/PolicyNo"/>
				</ContNo>
				<!-- �������� -->
				<Password><xsl:value-of select="Main/Password"/></Password>
				<!-- ���չ�˾���� -->
				<CarrierCode><xsl:value-of select="Main/InsuId"/></CarrierCode>
				<!-- ���ղ�Ʒ���� -->
				<RiskCode />
				<!-- Ͷ����֤������ -->
				<IDType />
				<!-- Ͷ����֤������ -->
				<IDNo />
				<!-- Ͷ�������� -->
				<Name />
				<!-- ���н������� -->
				<Channel><xsl:apply-templates select="Main/Channel"/></Channel>
			</Body>
		</TranData>
	</xsl:template>
	
	<xsl:template name="tran_channel"  match="Channel">
		<xsl:choose>
			<xsl:when test=".=0"></xsl:when><!-- ���� -->
			<xsl:when test=".=1">0</xsl:when><!-- ���� -->
			<xsl:when test=".=2">1</xsl:when><!-- ���� -->
			<xsl:when test=".=3"></xsl:when><!-- �ֻ����� -->
			<xsl:when test=".=4">8</xsl:when><!-- �����ն� -->
			<xsl:when test=".=5"></xsl:when><!-- ���ܹ�̨ -->
			<xsl:when test=".=6"></xsl:when><!-- �������� -->
			<xsl:when test=".=7"></xsl:when><!-- ��������ƽ̨ -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
