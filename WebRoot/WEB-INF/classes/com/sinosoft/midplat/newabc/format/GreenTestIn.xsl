<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

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
			 <xsl:copy-of select="Head/*"/>
	  	    </Head>
			<!-- ������ -->
			<Reserve><xsl:value-of select="App/Req/Reserve"/></Reserve>
		</TranData>
	</xsl:template>
</xsl:stylesheet>

