<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
 	
	<xsl:template match="ABCB2I">
	<TranData>
		<!--������Ϣ-->
		<Head>
				<!-- �������� -->
				<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
		  		<!-- ����ʱ��-->
				<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
				<!-- ���д��� -->
				<BankCode>0102</BankCode>
				<!-- �������� -->
				<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				<!-- ��Ա���� -->
				<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
		        <!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
				<!-- YBT��֯�Ľڵ���Ϣ -->
				 <xsl:copy-of select="Head/*"/>
		</Head>
		<Body>
				<!--������ -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
				 <!-- ���ִ��� -->
				<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
				<!--ҵ������: 05Ͷ����ת����06Ͷ���ղ�����ȡ��07��ԥ�ڳ�����09 ���ڸ�����10�˱���11���ڽɷ� -->
				<BusiType>
						<xsl:call-template name="busitype">
							<xsl:with-param name="BusiType">
								<xsl:value-of select="App/Req/BusinType" />
							</xsl:with-param>
						</xsl:call-template>
				</BusiType>
		</Body>
	</TranData>
	</xsl:template>

	<!--  ҵ������ -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- �̳� -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- ���ڸ��� -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- �˱� -->
	</xsl:template>

</xsl:stylesheet>