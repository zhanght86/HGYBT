<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<!--������-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
			    <!-- ���շ��� -->
				<PbSlipNumb />
				<!-- ������ -->
				<PiStartDate>
					<xsl:value-of
						select="TranData/Body/Risk/CValiDate" />
				</PiStartDate>
				<!-- ��ֹ���� -->
				<PiEndDate>
					<xsl:value-of
						select="TranData/Body/Risk/InsuEndDate" />
				</PiEndDate>
				<BkTotAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
					<!-- �ܱ��� -->
				</BkTotAmt>
				<BkTxAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
					<!-- ���ڱ��� -->
				</BkTxAmt>
				<PbMainExp>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)" />
					<!-- �������ձ���-->
				</PbMainExp>
				<BkNum1>0</BkNum1>
				<!-- �����Ҫ�� -->
				<!-- ÿ��ת�˽�� -->
				<PbPayPerAmt>
				   <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)" />
				</PbPayPerAmt>
				<!-- ������ -->
				<xsl:if test="count(/TranData/Body/Risk) != '1'">
				<Appd_Lis>
					<Appd_Detail>
						<!-- ���ִ���  -->
						<LiAppdInsuType><xsl:value-of select="TranData/Body/Risk[RiskCode!=MainRiskCode]/RiskCode" /></LiAppdInsuType>
						<!-- ���ڸ����ձ��� -->	
						<LiAppdInsuExp><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Risk[RiskCode!=MainRiskCode]/Prem)" /></LiAppdInsuExp>
					</Appd_Detail>
				</Appd_Lis>
				</xsl:if>
				<!-- ת��Ƶ�� -->
				<PbPayFre>
				<xsl:call-template name="tran_payintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="TranData/Body/PayIntv" />
						</xsl:with-param>
					</xsl:call-template>
				</PbPayFre>
				 <!--  ת������   PAYENDYEARFLAG   A�����䣬M���£�D���գ�Y����  -->
				 <xsl:variable name="PAYENDYEARFLAG" select="TranData/Body/PayendYearFlag" />
				 <xsl:variable name="PayendYear" select="TranData/Body/PayendYear" />
				 <xsl:if test="$PAYENDYEARFLAG = 'A'">		    
					<PbPayDeadLine>����<xsl:value-of select="$PayendYear" />����</PbPayDeadLine>
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'M'">
				    <PbPayDeadLine><xsl:value-of select="$PayendYear" />��</PbPayDeadLine>		
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'D'">
				    <PbPayDeadLine><xsl:value-of select="$PayendYear" />��</PbPayDeadLine>		
				 </xsl:if>
				 <xsl:if test="$PAYENDYEARFLAG = 'Y'">
				 <xsl:choose>
				 <xsl:when test="$PayendYear = 1000"> 
				    <PbPayDeadLine>һ�ν���</PbPayDeadLine>		
				 </xsl:when>
				 <xsl:otherwise>
				      <PbPayDeadLine><xsl:value-of select="$PayendYear" />��</PbPayDeadLine>	
				 </xsl:otherwise>
				 </xsl:choose>
				</xsl:if>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	
    <!-- ת��Ƶ��ת�� -->
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:if test="$payintv = '-1'">�����ڽ�</xsl:if><!-- �����ڽ� -->
		<xsl:if test="$payintv = '0'">һ�ν���</xsl:if><!-- �������һ�ν��� -->
		<xsl:if test="$payintv = '1'">�½�</xsl:if><!-- �½�-->
		<xsl:if test="$payintv = '3'">����</xsl:if><!-- ���� -->
		<xsl:if test="$payintv = '6'">���꽻</xsl:if><!-- ���꽻 -->
		<xsl:if test="$payintv = '12'">�꽻</xsl:if><!-- �꽻 -->
	</xsl:template>
</xsl:stylesheet>
