<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="ABCB2I">
	 <TranData><!-- ���ĵ��ճ��������� -->
	     <Head>
	  		<!-- �������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- ������� -->
			<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
	        <!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/>
	  	</Head>
		<Body>
			<!-- ���յ��� -->
			<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
			<!-- Ͷ����(ӡˢ)�� -->
			<ProposalPrtNo></ProposalPrtNo>
			<!-- �±�����ͬӡˢ�� -->
			<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/VchNo)"/></ContPrtNo>
			
			<TranNo><xsl:value-of select="App/Req/OrgSerialNo"/></TranNo>
			<AccNo><xsl:value-of select="App/Req/PayAcc"/></AccNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>
		</Body>
	</TranData>
</xsl:template>
</xsl:stylesheet>
