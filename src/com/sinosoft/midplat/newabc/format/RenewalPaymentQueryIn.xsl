<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData>
	<!--������Ϣ-->
	<Head>
	        <!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- ������� -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
			<ProposalPrtNo></ProposalPrtNo><!-- Ͷ����(ӡˢ)�� -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo><!--������ -->
			<Password></Password> <!--��������-->
			<CarrierCode></CarrierCode > <!--���չ�˾����-->
			<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode> <!--���ղ�Ʒ����-->
			<IDType ></IDType > <!--Ͷ����֤������-->
			<IDNo ></IDNo > <!--Ͷ����֤������-->
			<Name ></Name > <!--Ͷ��������-->
			<Channel>1</Channel><!--���н�������-->
	</Body>
</TranData>
</xsl:template>

<!-- ���ִ��� -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
</xsl:choose>
</xsl:template>

</xsl:stylesheet>