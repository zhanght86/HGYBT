<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--������-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<BkRecNum>
					<xsl:value-of select="count(/TranData/CancelDetails/CancelDetail)" />
				</BkRecNum>
				<!--��¼��-->
				<Detail_List>
					<xsl:for-each select="/TranData/CancelDetails/CancelDetail">
						<Detail>
							<!-- �˱�������ϸ��Ϣ -->
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="RiskCode"/>
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType>
							<!--���ִ���-->
							<PbInsuSlipNo>
								<xsl:value-of select="ContNo"/>
							</PbInsuSlipNo>
							<!--��������-->
							<PbHoldName>
								<xsl:value-of select="AppntName"/>
							</PbHoldName>
							<!--������������-->
							<BkActBrch>
								<xsl:value-of select="BrNo"/>
							</BkActBrch>
							<!--������Ա��-->
							<BkActTeller>
								<xsl:value-of select="TellerNo"/>
							</BkActTeller>
							<!--Ͷ��������-->
							<LiRcgnName>
								<xsl:value-of select="InsuredName"/>
							</LiRcgnName>
							<!--����������-->
							<BkTotAmt>
								<xsl:value-of select="Prem"/>
							</BkTotAmt>
							<!--�ܱ���-->
							<BkFlag1>
								<xsl:value-of select="CancelFlag"/>
							</BkFlag1>
							<!--�˱���־-->
							<PbSlipNumb>
								<xsl:value-of select="CancelMult"/>
							</PbSlipNumb>
							<!--�˱�����-->
							<PiAmount>
								<xsl:value-of select="CancelPrem"/>
							</PiAmount>
							<!--�˱����-->
							<BkOthTxCode>
								<xsl:value-of select="CancelType"/>
							</BkOthTxCode>
							<!--�˱���������-->
							<PiQdDate>
								<xsl:value-of select="java:com.sinosoft.midplat.channel.util.DateUtil.formatTrans(SignDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</PiQdDate>
							<!--ǩ������-->
							<Bk8Date1>
								<xsl:value-of select="java:com.sinosoft.midplat.channel.util.DateUtil.formatTrans(CancelDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</Bk8Date1>
							<!--�˱�����-->
						</Detail>
					</xsl:for-each>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	<!-- 	<xsl:template name="tran_cancelflag">-->
		<!--�˱���־ת��--> 
	<!-- 	<xsl:param name="cancelflag">0</xsl:param> 
		<xsl:choose>Ŀǰ�����������־��Ǳ���ȫ����ԥ���˱�,д��ΪG
			<xsl:when test="$cancelflag = 'PA'">20</xsl:when>
			<xsl:when test="$cancelflag = 12">12</xsl:when>
			<xsl:when test="$cancelflag = 1">1</xsl:when>
			<xsl:when test="$cancelflag = 6">6</xsl:when>
			<xsl:when test="$cancelflag = 3">3</xsl:when>
			<xsl:when test="$cancelflag = -1">-1</xsl:when>
		</xsl:choose>
	</xsl:template>   -->
	
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 331201">0002</xsl:if>
		<xsl:if test="$RiskCode = 230701">0001</xsl:if>
		<xsl:if test="$RiskCode = 331301">0003</xsl:if>
		<xsl:if test="$RiskCode = 331601">0004</xsl:if>
		<xsl:if test="$RiskCode = 240501">0005</xsl:if>
		<xsl:if test="$RiskCode = 331701">0006</xsl:if>
	</xsl:template>
</xsl:stylesheet>
