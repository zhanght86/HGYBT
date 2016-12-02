<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<!--�ش�����-->
		<TranData>
			
			<Head>
				<TranDate>
					<xsl:value-of select="/InsuReq/Main/TranDate"/>
				</TranDate>
				<!--���н�������-->
				<TranTime>
					<xsl:value-of select="/InsuReq/Main/TranTime" />
				</TranTime>
				<!-- ���н���ʱ�� -->
				<!--���д���(cd05)-->
				<TellerNo>
					<xsl:value-of
						select="/InsuReq/Main/TellerNo" />
				</TellerNo>
				<!-- ��Ա���� -->
				<TranNo>
					<xsl:value-of
						select="/InsuReq/Main/TransNo" />
				</TranNo>
				<!-- ������ˮ�� -->
				<NodeNo>
					<xsl:value-of select="/InsuReq/Main/BrNo" />
				</NodeNo>
				<!--�������-->
				<BankCode>0104</BankCode>
				<!-- ���д��� -->
				<xsl:copy-of select="/InsuReq/Head/*"/>
			</Head>

			<Body>
				<ContNo>
					<xsl:value-of select="/InsuReq/Main/PolicyNo"/>
				</ContNo>
				<!--������ -->
				<ContPrtNo>
					<xsl:value-of select="/InsuReq/Main/PrintNo"/>
				</ContPrtNo>
				<!--����ӡˢ��-->
				<ProposalPrtNo></ProposalPrtNo>
					<!--Ͷ������-->
					<OldContPrtNo></OldContPrtNo>
					<!-- �ɱ���ӡˢ�� -->
					<OldTranNo><xsl:value-of select="/InsuReq/Main/BkOthOldSeq"/></OldTranNo>
					<!-- ԭ������ˮ�� -->
				<Password></Password>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>
