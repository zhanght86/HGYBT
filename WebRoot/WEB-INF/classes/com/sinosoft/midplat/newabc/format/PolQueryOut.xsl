<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<ABCB2I>
	<Header>
		<!--������ -->
		<RetCode></RetCode>
		<!--������Ϣ -->
		<RetMsg></RetMsg>
		<!--���н�����ˮ�� -->
		<SerialNo><xsl:value-of select="Head/TranNo"/></SerialNo>
		<!--���չ�˾��ˮ�� -->
		<InsuSerial></InsuSerial>
		<!-- ��������-->
		<TransTime><xsl:value-of select="Head/TransTime"/></TransTime>
		<!-- ����ʱ-->
		<TransDate><xsl:value-of select="Head/TranDate"/></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���д���-->
		<TransCode>1016</TransCode>
	</Header>
	<App>
		<Ret>
			<!-- ������ -->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!--���չ�˾���ִ���  -->
			<RiskCode><xsl:value-of select="Body/RiskCode"/></RiskCode>
			<!--��������  -->
			<RiskName><xsl:value-of select="Body/RiskName"/></RiskName>
			<!--  ����״̬-->
			<PolicyStatus>
					<xsl:call-template name="policyStatus">
						<xsl:with-param name="PolicyStatus">
							<xsl:value-of select="Body/PolicyStatus"/>
						</xsl:with-param>
					</xsl:call-template>
			</PolicyStatus>
			<!-- ������Ѻ״̬ -->
			<PolicyPledge>0</PolicyPledge>
			<!--  ��������-->
			<AcceptDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/ApplyDate)"/></AcceptDate>
			<!--  ������Ч��-->
			<PolicyBgnDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></PolicyBgnDate>
			<!--  ����������-->
			<PolicyEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/InsuEndDate)"/></PolicyEndDate>
			<!-- Ͷ������ -->
			<PolicyAmmount><xsl:value-of select="Body/Mult"/></PolicyAmmount>
			<!--  ����-->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></Amt>
			<!--  ����-->
			<Beamt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></Beamt>
			<!-- ������ֵ -->
			<PolicyValue><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/ContValue)"/></PolicyValue>
			<!--  �Զ�ת����Ȩ�˺�-->
			<AutoTransferAccNo><xsl:value-of select="Body/AccNo"/></AutoTransferAccNo>
			<!-- ��������1 -->
			<prt1><xsl:value-of select="Body/prt1"/></prt1>
			<!-- ��������2 -->
			<prt2><xsl:value-of select="Body/prt2"/></prt2>
			<!-- ��������3 -->
			<prt3><xsl:value-of select="Body/prt3"/></prt3>
			<!-- ��������4 -->
			<prt4><xsl:value-of select="Body/prt4"/></prt4>
			<!-- ��������5 -->
			<prt5><xsl:value-of select="Body/prt5"/></prt5>
			<!-- ��������6 -->
			<prt6><xsl:value-of select="Body/prt6"/></prt6>
		</Ret>
	</App>
	</ABCB2I>
	</xsl:template>
	
	<!--  ����״̬ -->
	<xsl:template name="policyStatus" >
		<xsl:param name="PolicyStatus"></xsl:param>
			<xsl:if test="$PolicyStatus = ''">05</xsl:if><!-- 	״̬����ȷ -->	
			<xsl:if test="$PolicyStatus = 01">04</xsl:if><!-- 	������ֹ -->
			<xsl:if test="$PolicyStatus = 02">01</xsl:if><!-- 	�˱���ֹ -->
			<xsl:if test="$PolicyStatus = 03">08</xsl:if><!-- 	������ -->
			<xsl:if test="$PolicyStatus = 04">07</xsl:if><!-- 	������ֹ -->
			<xsl:if test="$PolicyStatus = 05">01</xsl:if><!-- 	Э����ֹ -->
			<xsl:if test="$PolicyStatus = 06">03</xsl:if><!-- 	������ֹ -->
			<xsl:if test="$PolicyStatus = 07">05</xsl:if><!-- 	ʧЧ��ֹ -->
			<xsl:if test="$PolicyStatus = 08">00</xsl:if><!-- 	��Ч -->
			<xsl:if test="$PolicyStatus = 09">05</xsl:if><!-- 	ʧЧ -->
			<xsl:if test="$PolicyStatus = 10"></xsl:if><!-- 	Э����ֹ -->
			<xsl:if test="$PolicyStatus = 11"></xsl:if><!-- 	������ֹ -->		
			<xsl:if test="$PolicyStatus = 12"></xsl:if><!-- 	�Ե���ֹ -->
			<xsl:if test="$PolicyStatus = 13"></xsl:if><!-- 	������ֹ -->
	</xsl:template>
</xsl:stylesheet>
