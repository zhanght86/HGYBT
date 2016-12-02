<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet>
			<Main>
				<!--��������-->
				<TranDate><xsl:value-of select="Head/TranDate" /></TranDate>
				<!--����ʱ��-->
				<TranTime><xsl:value-of select="Head/TranTime"/></TranTime>
				<!--���д���-->
				<BankCode>BOC</BankCode>
				<!--���չ�˾����-->
				<InsuId/>
				<!--��������-->
				<ZoneNo/>
				<!--�������-->
				<BrNo><xsl:value-of select="Head/NodeNo" /></BrNo>
				<!--��Ա����-->
				<TellerNo><xsl:value-of select="Head/TellerNo" /></TellerNo>
				<!--������ˮ��-->
				<TransNo><xsl:value-of select="Head/TranNo" /></TransNo>
				<!--������-->
				<TranCode/>
				<!--������ʶ-->
				<Channel/>
				<!--ԭ����������ˮ��-->
				<OriginTransNo/>
				<!--������-->
				<PolicyNo><xsl:value-of select="Main/ContNo" /></PolicyNo>
				<!--���շ�-->
				<Premium/>
			</Main>
		</InsuRet>
	</xsl:template>
</xsl:stylesheet>
