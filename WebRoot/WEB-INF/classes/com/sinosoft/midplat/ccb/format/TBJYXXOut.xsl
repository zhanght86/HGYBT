<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--������-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<BkRecNum>
					<xsl:value-of select="count(/TranData/Body/Detail)" />
				</BkRecNum>
				<!--��¼��-->
				<Detail_List>
					<xsl:for-each select="/TranData/Body/Detail">
					<!-- �˱�������ϸ��Ϣ -->
						<Detail>							
							<!--���ִ���-->
							<PbInsuType>
								<xsl:call-template name="tran_RiskCode">
									<xsl:with-param name="RiskCode">
										<xsl:value-of select="RiskCode"/>
									</xsl:with-param>
								</xsl:call-template>
							</PbInsuType>		
							<!--��������-->		
							<PbInsuSlipNo>
								<xsl:value-of select="ContNo"/>
							</PbInsuSlipNo>			
							<!--������������-->
							<BkActBrch>
								<xsl:value-of select="AgentCom"/>
								<!--������Ա��-->
							<BkActTeller>
								<xsl:value-of select="BankAgent"/>
							</BkActTeller>
							</BkActBrch>
							<!--Ͷ��������-->
							<PbHoldName>
								<xsl:value-of select="AppntName"/>
							</PbHoldName>
							<!--����������-->
							<LiRcgnName>
								<xsl:value-of select="InsuredName"/>
							</LiRcgnName>
							<!--�ܱ���-->
							<BkTotAmt>
								<xsl:value-of select="SumPrem"/>
							</BkTotAmt>
							<!--�˱���־-->
							<BkFlag1>G</BkFlag1>
							<!--�����˱�����-->
							<PbSlipNumb>
								<xsl:value-of select="Mult"/>
							</PbSlipNumb>
							<!--�����˱����-->
							<PiAmount>
								<xsl:value-of select="EdorCTPrem"/>
							</PiAmount>
							<!-- �����ո��� -->
							<BkNum1>
								<xsl:value-of select="AddRiskCount"/>
							</BkNum1>
							<!-- �����յ���Ϣ -->
							<xsl:apply-templates select="AddRiskDetail/AddRisk"/>
							
							<!--�˱���������-->
							<BkOthTxCode>
								<xsl:value-of select="EdorCTType"/>
							</BkOthTxCode>
							<!--ǩ������-->
							<PiQdDate>
								<xsl:value-of select="SignDate"/>
							</PiQdDate>
							<!--�˱�����-->
							<Bk8Date1>
								<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.formatTrans(CancelDate,'yyyy-MM-dd','yyyyMMdd')"/>
							</Bk8Date1>
						</Detail>
					</xsl:for-each>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>

	
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">231201</xsl:param>
		<xsl:if test="$RiskCode = 231201">0001</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
		<xsl:if test="$RiskCode = 231202">0002</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
		<xsl:if test="$RiskCode = 231203">0003</xsl:if><!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
		<xsl:if test="$RiskCode = 221201">0004</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$RiskCode = 231204">0005</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	</xsl:template>
	
	<xsl:template name="AddRisk">
		
		<xsl:if test="AddRiskCount!='' or AddRiskCount!='0'">
			<Appd_List>
				<xsl:for-each select="AddRisk">
				<Appd_Detail>
					<!-- �������˱����� -->
					<LiAppdInsuType><xsl:value-of select="RiskCode"/></LiAppdInsuType>
					<!-- �������˱����� -->
					<LiAppdInsuNumb><xsl:value-of select="EdorCTPrem"/></LiAppdInsuNumb>
					<!-- �������˱���� -->
					<LiAppdInsuExp><xsl:value-of select="Mult"/></LiAppdInsuExp>
				</Appd_Detail>
				</xsl:for-each>
			</Appd_List>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
