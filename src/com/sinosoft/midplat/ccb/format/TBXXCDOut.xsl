<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<Transaction_Body>
				<BkRecNum><xsl:value-of select="TranData/Body/BkRecNum" /></BkRecNum><!-- ��¼�� -->
				<xsl:for-each select = "TranData/Body/Detail_List/Detail">
					<Detail_List>
						<Detail>
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="PbInsuType" />
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType><!-- ���ִ��� -->
							<PbInsuSlipNo><xsl:value-of select="PbInsuSlipNo" /></PbInsuSlipNo><!--��������-->
							<BkActBrch><xsl:value-of select="BkActBrch" /></BkActBrch>
							<BkActTeller><xsl:value-of select="BkActTeller" /></BkActTeller><!--������Ա��-->
							<PbHoldName><xsl:value-of select="PbHoldName" /></PbHoldName><!--Ͷ��������-->
							<LiRcgnName><xsl:value-of select="LiRcgnName" /></LiRcgnName><!-- ���������� -->
							<BkTotAmt><xsl:value-of select="BkTotAmt"/></BkTotAmt><!-- �ܱ��� -->
							<BkFlag1><xsl:value-of select="BkFlag1" /></BkFlag1><!-- �˱���־ -->
							<PbSlipNumb><xsl:value-of select="PbSlipNumb" /></PbSlipNumb><!-- �����˱����� -->
							<PiAmount><xsl:value-of select="PiAmount" /></PiAmount><!-- �����˱���� -->
							<BkOthTxCode><xsl:value-of select="BkOthTxCode" /></BkOthTxCode><!-- �˱��������� -->
							<PiQdDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(PiQdDate)" /></PiQdDate><!-- ǩ������ -->
							<Bk8Date1><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Bk8Date1)" /></Bk8Date1><!-- �˱����� -->
							<BkNum1><xsl:value-of select="BkNum1" /></BkNum1><!-- �����ո��� -->
							<!-- <xsl:for-each select = "Appd_List/Appd_Detail"> -->
								<Appd_List>
									<Appd_Detail>
										<LiAppdInsuType><xsl:value-of select="LiAppdInsuType" /></LiAppdInsuType><!-- �������˱����� -->
										<LiAppdInsuNumb><xsl:value-of select="LiAppdInsuNumb" /></LiAppdInsuNumb><!-- �������˱����� -->
										<LiAppdInsuExp><xsl:value-of select="LiAppdInsuExp" /></LiAppdInsuExp><!-- �������˱���� -->
									</Appd_Detail>
								</Appd_List>
							<!-- </xsl:for-each> -->
						</Detail>
					</Detail_List>
				</xsl:for-each>
			</Transaction_Body>
		</Transaction>
	</xsl:template>
	
	<!-- ���ִ��� -->
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode"></xsl:param>
		<xsl:if test="$RiskCode = 313030">0001</xsl:if>
		<xsl:if test="$RiskCode = 321010">0002</xsl:if>
		<xsl:if test="$RiskCode = 313050">0003</xsl:if>
		<xsl:if test="$RiskCode = 321110">0004</xsl:if>
		<xsl:if test="$RiskCode = 321040">0005</xsl:if>
		<xsl:if test="$RiskCode = 321120">0006</xsl:if>
		<xsl:if test="$RiskCode = 321170">0007</xsl:if>
		<xsl:if test="$RiskCode = 321180">0008</xsl:if>
		<xsl:if test="$RiskCode = 321030">0009</xsl:if>
		<xsl:if test="$RiskCode = 321220">0010</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
