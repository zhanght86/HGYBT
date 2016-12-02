<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<TranData>
			<xsl:for-each select="/Transaction/Transaction_Header">
				<Head>
					<TranDate>
						<xsl:value-of select="BkPlatDate"/>
					</TranDate>
					<!--���н�������-->
					<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
					<NodeNo>
						<xsl:value-of select="BkBrchNo"/>
					</NodeNo>
					<!--���д���-->
					<BankCode>0122</BankCode>
					<!--�������-->
					<TellerNo>
						<xsl:value-of select="BkTellerNo"/>
					</TellerNo>
					<!--��Ա����-->
					<TranNo>
						<xsl:value-of select="BkPlatSeqNo"/>
					</TranNo>
					<!--������ˮ��-->
					<TranCom>07</TranCom>
				    <xsl:copy-of select="../Head/*"/>
				</Head>
			</xsl:for-each>
			<Body>
				<OldTranNo>
					<xsl:value-of select="/Transaction/Transaction_Body/BkOthOldSeq"/>
				</OldTranNo>
				<!--ԭ���Ľ�����ˮ��   20150109 ������˵�ú��ĵĴ���-->
				<RiskCode><xsl:value-of select="/Transaction/Transaction_Body/PbInsuType" /></RiskCode>
				<ContNo></ContNo> <!-- ���յ��� -->
                <ProposalPrtNo></ProposalPrtNo> <!-- Ͷ����(ӡˢ)�� -->
                <ContPrtNo></ContPrtNo> <!-- ������ͬӡˢ�� -->
                <!-- ���ڽɷѷ�ʽ -->
                <BkPayMode>
                <xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="/Transaction/Transaction_Body/BkPayMode" />
						</xsl:with-param>
					</xsl:call-template>
				</BkPayMode>
                 <!-- ���ڽɷ��ʺ� -->
                <BkAcctNo><xsl:value-of select="/Transaction/Transaction_Body/BkAcctNo" /></BkAcctNo>
			</Body>
		</TranData>
	</xsl:template>
		<!-- �����ִ��� -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231201</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
		<xsl:if test="$MainRiskCode = 0002">231202</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
		<xsl:if test="$MainRiskCode = 0003">231203</xsl:if><!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
		<xsl:if test="$MainRiskCode = 0004">221201</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$MainRiskCode = 0005">231204</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$MainRiskCode = 0006">221301</xsl:if><!-- �к���δ������� -->
	</xsl:template>
	   <!-- ���ڽɷѷ�ʽ -->
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode">0</xsl:param>
		<xsl:if test="$PayMode = 1">0</xsl:if><!-- �ֽ� -->
		<xsl:if test="$PayMode = 2">0</xsl:if><!-- �۴��� -->
		<xsl:if test="$PayMode = 3">0</xsl:if><!-- ������ -->
		<xsl:if test="$PayMode = 9">0</xsl:if><!-- �Թ����� -->
	</xsl:template>	
</xsl:stylesheet>
