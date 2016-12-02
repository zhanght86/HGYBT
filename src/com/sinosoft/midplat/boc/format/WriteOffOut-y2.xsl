<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
		<InsuRet>
			<Main>
				<!-- �������� -->
				<TranDate>
					<xsl:value-of select="Body/TranDate"/>
				</TranDate>
				<!-- ����ʱ�� -->
				<TranTime>
					<xsl:value-of select="Body/TranTime"/>
				</TranTime>
				<!-- ���չ�˾���� -->
				<InsuId>
					<xsl:value-of select="Body/InsuId"/>
				</InsuId>
				<!-- �������� -->
				<ZoneNo>
					<xsl:value-of select="Body/ZoneNo"/>
				</ZoneNo>
				<!-- ������� -->
				<BrNo>
					<xsl:value-of select="Body/BrNo"/>
				</BrNo>
				<!-- ��Ա���� -->
				<TellerNo>
					<xsl:value-of select="Body/TellerNo"/>
				</TellerNo>
				<!-- ������ˮ�� -->
				<TransNo>
					<xsl:value-of select="Body/TransNo"/>
				</TransNo>
				<!-- ������ -->
				<TranCode>
					<xsl:value-of select="Body/TranCode"/>
				</TranCode>
				<!-- ��Ӧ���� -->
				<ResultCode>
					<xsl:value-of select="Body/ResultCode"/>
				</ResultCode>
				<!-- ��Ӧ��Ϣ -->
				<ResultInfo>
					<xsl:value-of select="Body/ResultInfo"/>
				</ResultInfo>
				<!-- ������ -->
				<PolicyNo>
					<xsl:value-of select="Body/ContNo"/>
				</PolicyNo>
			</Main>
		</InsuRet>
	</xsl:template>
</xsl:stylesheet>
