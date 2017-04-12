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
				<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
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
				<!-- �������� -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo"/></ContNo>
				 <!--��������-->
				<Password></Password>
				<!--���չ�˾����-->
				<CarrierCode></CarrierCode > 
				<!--���ղ�Ʒ����-->
				<RiskCode><xsl:value-of select="App/Req/RiskCode"/></RiskCode> 
				<!--Ͷ����֤������-->
				<IDType ></IDType > 
				<!--Ͷ����֤������-->
				<IDNo ></IDNo > 
				<!--Ͷ��������-->
				<Name ></Name > 
				<!--���н�������-->
				<Channel>1</Channel>
				
				<!-- Ͷ����(ӡˢ)�� -->
				<ProposalPrtNo></ProposalPrtNo>
		</Body>
	</TranData>
	</xsl:template>

</xsl:stylesheet>