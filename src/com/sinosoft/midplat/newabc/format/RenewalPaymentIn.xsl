<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- ����ũ�����ڽɷ������� -->
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
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			</xsl:when>
			<xsl:otherwise>
			<TellerNo>0005</TellerNo>
			</xsl:otherwise>
			</xsl:choose>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	<Body>
			<!-- ����֧����ʽ����:Y -->
			<PayMode >B</PayMode >
			<!-- Ͷ����(ӡˢ)�� -->
		     <ProposalPrtNo>
		     	<xsl:if test="Header/EntrustWay = '11'">
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)"/>
				</xsl:if>
				<xsl:if test="Header/EntrustWay = '04'">
					<xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/>
				</xsl:if>
		     </ProposalPrtNo>
			<!-- ���չ�˾��ˮ��:Y -->
			<TranNo><xsl:value-of select="Header/InsuSerial"></xsl:value-of></TranNo>
		    <!--������ -->
			<ContNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/PolicyNo" /></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
			</ContNo>
		    <!--���� -->
			<Prem><xsl:value-of select="App/Req/PayAmt"/></Prem>
		    <!--������ˣ������� -->
			<PayAcc><xsl:value-of select="App/Req/PayAcc"/></PayAcc>
	</Body>
</TranData>
</xsl:template>

</xsl:stylesheet>