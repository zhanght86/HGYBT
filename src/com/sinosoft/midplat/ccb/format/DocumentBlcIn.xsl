<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="Transaction">
		<TranData>
			<Head>
				<TranDate>
					<xsl:value-of select="Transaction_Header/BkPlatDate" />
				</TranDate>
				<!--���н�������-->
				<TranTime>
				   <xsl:value-of select="Transaction_Header/BkPlatTime" />
				</TranTime>
				<!--����ʱ��-->
				<BankCode>0104</BankCode>
				<!--���д���-->
				<NodeNo><xsl:value-of
						select="Transaction_Header/BkBrchNo" /></NodeNo>
				<!--�������-->
				<TellerNo>
					<xsl:value-of select="Transaction_Header/BkTellerNo" />
				</TellerNo>
				<!--��Ա����-->
				<TranNo>
					<xsl:value-of select="Transaction_Header/BkPlatSeqNo" />
				</TranNo>
				<!--������ˮ��17-->
					<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<Count>
					<xsl:value-of select="Transaction_Body/BkTotNum" />
				</Count>
				<xsl:for-each select="Transaction_Body/Detail_List/Detail">
					<Detail>
					    <CardType>0105121</CardType><!-- �ؿ����� -���ı������� -->
						<CardNo><xsl:value-of select="BkVchNo" /></CardNo><!-- �ؿ�ӡˢ�� -->
						<CardState><xsl:call-template name="tran_CardState">
						<xsl:with-param name="CardState">
							<xsl:value-of select="BkFlag2" />
						</xsl:with-param>
					</xsl:call-template></CardState><!-- �ؿ�״̬ -->
						<AgentCom><xsl:value-of select="BkActBrch" /></AgentCom><!-- ���ʻ����� -->
						<TranNo><xsl:value-of select="BkChnlSeq" /></TranNo><!-- ���з�������ˮ�� -->
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>
	
	<!-- �����ִ��� -->
	<xsl:template name="tran_CardState">
		<xsl:param name="CardState">-1</xsl:param>
		<xsl:if test="$CardState = '2'">4</xsl:if>
		<xsl:if test="$CardState = '3'">6</xsl:if>
	</xsl:template>
</xsl:stylesheet>
