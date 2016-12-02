<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
			<!-- ��ȫ���ݻش����ر�������ֶ�Mapping -->
			<xsl:apply-templates select="Body/Detail"/>
			</Body>
		</TranData>
</xsl:template>

		<xsl:template name="policyDtl" match="Detail">			
					<Detail>
						<TranDate><xsl:value-of select="TranDate" /></TranDate>
						
						<BusiType><!-- ҵ�����࣬��ҪMapping -->
						<xsl:apply-templates select="BusiType" />
						</BusiType>
						
						<ModifyDate><xsl:value-of select="ModifyDate" /></ModifyDate><!--  ����״̬������� -->
						<InsuId>050</InsuId><!-- ���չ�˾���룬���ж���ģ��к�Ϊ�̶�ֵ 050 -->
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<ContNo><xsl:value-of select="ContNo" /></ContNo>
						<AppntName><xsl:value-of select="AppntName" /></AppntName>
						
						<AppntIDType><!-- �ͻ�֤������ ����ҪMapping -->
						<xsl:apply-templates select="AppntIDType" />
						</AppntIDType>
						
						<AppntIDNo><xsl:value-of select="AppntIDNo" /></AppntIDNo>
						
						<State><!-- ��������״̬����Ҫӳ�� -->
						<xsl:apply-templates select="State" />
						</State>
						
						<InsuEndDate><xsl:value-of select="InsuEndDate" /></InsuEndDate><!-- ������������ -->
						<Bak1><xsl:value-of select="Bak1" /></Bak1>
						<Bak2><xsl:value-of select="Bak2" /></Bak2>
						<Bak3><xsl:value-of select="Bak3" /></Bak3>
						<Bak4><xsl:value-of select="Bak4" /></Bak4>
					</Detail>
		</xsl:template>

<!-- ҵ������  ����-�����е�  -->
<xsl:template name="tran_BusiType" match="BusiType">
<xsl:choose>
	<xsl:when test=".=01">001</xsl:when>	<!-- 001���ڸ��� -->
	<xsl:when test=".=06">002</xsl:when>	<!-- 002��ԥ�ڳ���-->
	<xsl:when test=".=02">003</xsl:when>	<!-- 003�˱� -->
	<xsl:when test=".=15">004</xsl:when>	<!-- 004���ڽ���  -->
	<xsl:when test=".=16">006</xsl:when>	<!-- 006��������ʧЧ��ҵ��  -->
	<xsl:when test=".=04">099</xsl:when>	<!-- 099������ֹ  -->
	
	<!-- ������ʱû�� ����״̬-->
	<xsl:when test=".=5">005</xsl:when>	<!-- 005׷��Ͷ��  -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>
	
<!-- ֤������  ����-�����е� -->
<xsl:template name="tran_AppntIDType" match="AppntIDType">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=A">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=B">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=C">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=4">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- ����  -->
	<xsl:when test=".=D">9</xsl:when>	<!-- ����֤  -->
										<!--12  ����˾���֤  -->
	<xsl:otherwise>7</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ��������״̬   ����-�����е�    -->
<xsl:template name="tran_State" match="State">
<xsl:choose>
	<xsl:when test=".=15">12</xsl:when>	<!-- 12-������Ч -->
	<xsl:when test=".=06">14</xsl:when>	<!-- 14-��ԥ���˱���������ֹ -->
	<xsl:when test=".=02">20</xsl:when>  <!-- 20-�˱���ֹ -->
	<xsl:when test=".=04">21</xsl:when>	<!-- 21-������ֹ -->
	<xsl:when test=".=16">22</xsl:when>	<!-- 22-��������ʧЧ״̬ -->
	<xsl:when test=".=01">23</xsl:when>	<!-- 23-���ڸ�����ֹ -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
