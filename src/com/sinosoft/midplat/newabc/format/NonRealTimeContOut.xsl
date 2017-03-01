<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="TranData">

		<ABCB2I><!-- ũҵ���б�ȫ��ѯӦ���� -->
			<Header>
				<!--������ -->
				<RetCode></RetCode>
				<!--������Ϣ -->
				<RetMsg></RetMsg>
				<!--���н�����ˮ�� -->
				<SerialNo></SerialNo>
				<!--���չ�˾��ˮ�� -->
				<InsuSerial></InsuSerial>
				<!-- �������� -->
				<TransTime>
					<xsl:value-of
						select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/TranDate)" />
				</TransTime>
				<!-- ����ʱ�� -->
				<TransDate>
					<xsl:value-of select="Body/TranTime" />
				</TransDate>
				<!-- ���д��� -->
				<BankCode></BankCode>
				<!--���չ�˾���� -->
				<CorpNo></CorpNo>
				<!-- ���д��� -->
				<TransCode>1006</TransCode>
			</Header>
			<App>
				<Ret>
					<Appl>
						<!--Ͷ�������� -->
						<Name></Name>
						<!--Ͷ����֤������ -->
						<IDKind></IDKind>
						<!--Ͷ����֤������ -->
						<IDCode></IDCode>
					</Appl>
					<!--Ͷ������ -->
					<PolicyApplyNo>
						<xsl:value-of select="LCCont/ProposalContNo" />
					</PolicyApplyNo>
					<!--���չ�˾�����ִ��� -->
					<RiskCode>
						<xsl:value-of select="LCCont/Risks/Risk/RiskCode" />
					</RiskCode>
					<!--��Ʒ���� -->
					<ProdCode>
						<xsl:value-of select="LCCont/Risks/Risk/RiskCode" />
					</ProdCode>
					<!--���� -->
					<Prem></Prem>
				</Ret>
			</App>
		</ABCB2I>
	</xsl:template>
</xsl:stylesheet>
