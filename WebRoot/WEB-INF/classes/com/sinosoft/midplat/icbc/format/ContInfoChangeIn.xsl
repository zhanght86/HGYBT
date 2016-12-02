<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<Head> 
		<!-- �������� -->
		<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TXLife/TXLifeRequest/TransExeDate)"/></TranDate>
		<!-- ����ʱ�� -->
		<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TXLife/TXLifeRequest/TransExeTime)"/></TranTime>
		<!-- ���׵�λ(����/ũ����/������˾) -->
		<BankCode>01</BankCode>
		<TranCom>1</TranCom>
		<!-- �������� -->
		<NodeNo>
			<xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/RegionCode"/>
			<xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/Branch"/>
		</NodeNo>
		<!-- ��Ա���� -->
		<TellerNo><xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/Teller"/></TellerNo>
		<!-- ������ˮ�� -->
		<TranNo><xsl:value-of select="TXLife/TXLifeRequest/TransRefGUID"/></TranNo>
		<!-- �����ױ���  61 ��serviceid -->
		<FuncFlag>61</FuncFlag>
		<!-- �ӽ����� 612 -->
		<FuncFlagDetail>612</FuncFlagDetail>
		<AgentCom></AgentCom>
		<AgentCode></AgentCode>
		<SourceType><xsl:apply-templates select="TXLife/TXLifeRequest/OLifEExtension/SourceType" /></SourceType>
	</Head>
	<Body>
		<!-- Ͷ���� -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Relation[RelationRoleCode=80]" />
	</Body>
		
</TranData>
</xsl:template>

<xsl:template name="Detail" match="Relation[RelationRoleCode=80]">
		<xsl:variable name="PartyID" select="@RelatedObjectID" />
		<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		<Detail>
			<PolNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/PolNumber"/></PolNumber>
			<ProviderFormNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/PolNumber"/></ProviderFormNumber> <!--��ȫ���������[��¼��]-->
			<CustomerType>0</CustomerType ><!-- �ͻ����� -->
			<FullName><xsl:value-of select="$PartyNode/FullName"/></FullName><!-- ���� -->
			<GovtIDTC><xsl:apply-templates select="$PartyNode/GovtIDTC" /></GovtIDTC><!--֤������ -->
			<GovtID><xsl:value-of select="$PartyNode/GovtID"/></GovtID> <!--֤������ -->
			<Gender><xsl:apply-templates select="$PartyNode/Person/Gender" /></Gender><!--  �Ա� ���ɸ��ģ�����һ�»᷵�ز����޸ĵĴ�����Ϣ-->
			<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode/Person/BirthDate)" /></BirthDate><!-- �������� ���ɸ��ģ�����һ�»᷵�ز����޸ĵĴ�����Ϣ -->
			<Line1><xsl:value-of select="$PartyNode/Address[AddressTypeCode='Mailing']/Line1" /></Line1><!-- �ʼĵ�ַ ���ĵ���ϵ��ַContPostalAddress  -->
			<Zip><xsl:value-of select="$PartyNode/Address[AddressTypeCode='Mailing']/Zip" /></Zip><!-- �ʼ��ʱ�-->
			<ContPostalAddress><xsl:value-of select="$PartyNode/Address[AddressTypeCode='1']/Line1" /></ContPostalAddress><!-- ͨѶ��ַ -->
			<ContZipCode><xsl:value-of select="$PartyNode/Address[AddressTypeCode='1']/Zip" /></ContZipCode> <!-- ͨѶ�ʱ�-->
			<AppntPhone><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Home']/DialNumber" /></AppntPhone> <!-- סլ�绰����������ϵ�绰 -->
			<Mobile><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Mobile']/DialNumber" /></Mobile><!-- �ƶ��绰-->
			<CompanyPhone><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Business']/DialNumber" /></CompanyPhone><!-- �칫�绰�����ɸ��� -->
			<EMail><xsl:value-of select="$PartyNode/EMailAddress/AddrLine" /></EMail><!-- �������� -->
		</Detail>
		</xsl:template>
		
<!-- ���� -->
<xsl:template name="tran_SourceType" match="SourceType">
<xsl:choose>
	<xsl:when test=".=0">ybt</xsl:when>	<!-- ����ͨ -->
	<xsl:when test=".=1">wy</xsl:when>	<!-- ���� -->
	<xsl:when test=".=8">atm</xsl:when>	<!-- �����ն� -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>
</xsl:template>
<!-- �Ա� -->
<xsl:template name="tran_sex" match="Gender">
<xsl:choose> 
	<xsl:when test=".=1">0</xsl:when>	<!-- �� -->
	<xsl:when test=".=2">1</xsl:when>	<!-- Ů -->
	<xsl:when test=".=3">2</xsl:when>	<!-- Ĭ�� -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>  
</xsl:template>

<!-- ֤������ -->
<xsl:template name="tran_idtype" match="GovtIDTC">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- ����  -->
	<xsl:when test=".=9">7</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
