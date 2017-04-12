<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
	
<xsl:template match="ABCB2I">
<TranData><!-- �����շ�ǩ�������� -->
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
			<xsl:choose>
				<xsl:when test="Header/EntrustWay ='11'">
					<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
				</xsl:when>
				<xsl:otherwise>
					<TellerNo>0005</TellerNo>
				</xsl:otherwise>
			</xsl:choose>
	        <!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- ũ�������ն����� 0���� 8�����ն� -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay ='11'">
					<SaleChannel>0</SaleChannel>
				</xsl:when>
				<xsl:when test="Header/EntrustWay ='04'">
					<SaleChannel>8</SaleChannel>
				</xsl:when>
			</xsl:choose>
			
			<!-- ���յ��� -->
			<ContNo />
			<!-- Ͷ����(ӡˢ)�� -->
			<ProposalPrtNo>
				<xsl:if test="Header/EntrustWay = '11'">
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyNo)"/>
				</xsl:if>
				<xsl:if test="Header/EntrustWay = '04'">
					<xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/>
				</xsl:if>
			</ProposalPrtNo>
			<!-- ������ͬӡˢ�� -->
			<ContPrtNo></ContPrtNo>
			
			<!-- ��������˳��� -->
			<ApplyNo><xsl:value-of select="App/Req/ApplySerial" /></ApplyNo>
			<!-- Ͷ������ -->
			<PolApplyDate></PolApplyDate>
			<BkAcctNo><xsl:value-of select="App/Req/PayAccount" /></BkAcctNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>	
		</Body>
	</TranData>
</xsl:template>	

</xsl:stylesheet>
