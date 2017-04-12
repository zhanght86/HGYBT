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
		<TransTime></TransTime>
		<!-- ����ʱ-->
		<TransDate></TransDate>
		<!-- ���д���-->
		<BankCode></BankCode>
		<!--���չ�˾���� -->
		<CorpNo></CorpNo>
		<!-- ���ױ���-->
		<TransCode>1014</TransCode>
	</Header>
	<App>
		<Ret>
			<!--���ִ��� format�������-->
			<RiskCode></RiskCode>
			<!-- ������-->
			<PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
			<!--����״̬ -->
			<PolicyStatus>
					<xsl:call-template name="policyStatus">
						<xsl:with-param name="PolicyStatus">
							<xsl:value-of select="Body/State"/>
						</xsl:with-param>
					</xsl:call-template>
			</PolicyStatus>
			<!--�������� -->
			<ApplyDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/ApplyDate)"/></ApplyDate>
			<!--��Ч���� -->
			<ValidDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(Body/CValiDate)"/></ValidDate>
			<!--ҵ�����  format�������-->
			<BusinType></BusinType>
			<!--��ȫ����״̬ -->
			<BQStatus>
					<xsl:call-template name="bQStatus">
						<xsl:with-param name="BQStatus">
							<xsl:value-of select="Body/BQState"/>
						</xsl:with-param>
					</xsl:call-template>
			</BQStatus>
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
	
	<!--  ��ȫ����״̬ -->
	<xsl:template name="bQStatus" >
		<xsl:param name="BQStatus"></xsl:param>
		<xsl:if test="$BQStatus= 0">S</xsl:if><!-- 	ȷ����Ч	 -->
		<xsl:if test="$BQStatus = 1">D</xsl:if><!-- ¼�����	 -->
		<xsl:if test="$BQStatus = 2">D</xsl:if><!-- ����ȷ��	 -->
		<xsl:if test="$BQStatus = 3">D</xsl:if><!-- �ȴ�¼��	 -->
		<xsl:if test="$BQStatus = 4">F</xsl:if><!-- ������ֹ	 -->
		<xsl:if test="$BQStatus = 5">D</xsl:if><!-- �����޸�	 -->
		<xsl:if test="$BQStatus = 6">D</xsl:if><!-- ȷ��δ��Ч -->
		<xsl:if test="$BQStatus = 7">F</xsl:if><!-- ��ȫ����	 -->
		<xsl:if test="$BQStatus = 8">F</xsl:if><!-- �˱���ֹ	 -->
		<xsl:if test="$BQStatus = 9">F</xsl:if><!-- ������ֹ	 -->
		<xsl:if test="$BQStatus = a">D</xsl:if><!-- ����ͨ��	 -->
		<xsl:if test="$BQStatus = b">U</xsl:if><!-- ��ȫ����	 -->
		<xsl:if test="$BQStatus = c">F</xsl:if><!-- ��ȫ��ֹ	    ������ȷ��֮ǰ������״̬   -->
		<xsl:if test="$BQStatus = d">F</xsl:if><!-- ǿ����ֹ		�ڱ�ȫȷ�Ͻ�����������״̬ -->
	</xsl:template>
	
</xsl:stylesheet>
