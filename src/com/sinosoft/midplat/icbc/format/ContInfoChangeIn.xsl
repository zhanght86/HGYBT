<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<Head> 
		<!-- 交易日期 -->
		<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TXLife/TXLifeRequest/TransExeDate)"/></TranDate>
		<!-- 交易时间 -->
		<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TXLife/TXLifeRequest/TransExeTime)"/></TranTime>
		<!-- 交易单位(银行/农信社/经代公司) -->
		<BankCode>01</BankCode>
		<TranCom>1</TranCom>
		<!-- 银行网点 -->
		<NodeNo>
			<xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/RegionCode"/>
			<xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/Branch"/>
		</NodeNo>
		<!-- 柜员代码 -->
		<TellerNo><xsl:value-of select="TXLife/TXLifeRequest/OLifEExtension/Teller"/></TellerNo>
		<!-- 交易流水号 -->
		<TranNo><xsl:value-of select="TXLife/TXLifeRequest/TransRefGUID"/></TranNo>
		<!-- 主交易编码  61 即serviceid -->
		<FuncFlag>61</FuncFlag>
		<!-- 子交易码 612 -->
		<FuncFlagDetail>612</FuncFlagDetail>
		<AgentCom></AgentCom>
		<AgentCode></AgentCode>
		<SourceType><xsl:apply-templates select="TXLife/TXLifeRequest/OLifEExtension/SourceType" /></SourceType>
	</Head>
	<Body>
		<!-- 投保人 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Relation[RelationRoleCode=80]" />
	</Body>
		
</TranData>
</xsl:template>

<xsl:template name="Detail" match="Relation[RelationRoleCode=80]">
		<xsl:variable name="PartyID" select="@RelatedObjectID" />
		<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
		<Detail>
			<PolNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/PolNumber"/></PolNumber>
			<ProviderFormNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/PolNumber"/></ProviderFormNumber> <!--保全申请书号码[必录项]-->
			<CustomerType>0</CustomerType ><!-- 客户类型 -->
			<FullName><xsl:value-of select="$PartyNode/FullName"/></FullName><!-- 姓名 -->
			<GovtIDTC><xsl:apply-templates select="$PartyNode/GovtIDTC" /></GovtIDTC><!--证件类型 -->
			<GovtID><xsl:value-of select="$PartyNode/GovtID"/></GovtID> <!--证件号码 -->
			<Gender><xsl:apply-templates select="$PartyNode/Person/Gender" /></Gender><!--  性别 不可更改，若不一致会返回不可修改的错误信息-->
			<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode/Person/BirthDate)" /></BirthDate><!-- 出生日期 不可更改，若不一致会返回不可修改的错误信息 -->
			<Line1><xsl:value-of select="$PartyNode/Address[AddressTypeCode='Mailing']/Line1" /></Line1><!-- 邮寄地址 核心的联系地址ContPostalAddress  -->
			<Zip><xsl:value-of select="$PartyNode/Address[AddressTypeCode='Mailing']/Zip" /></Zip><!-- 邮寄邮编-->
			<ContPostalAddress><xsl:value-of select="$PartyNode/Address[AddressTypeCode='1']/Line1" /></ContPostalAddress><!-- 通讯地址 -->
			<ContZipCode><xsl:value-of select="$PartyNode/Address[AddressTypeCode='1']/Zip" /></ContZipCode> <!-- 通讯邮编-->
			<AppntPhone><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Home']/DialNumber" /></AppntPhone> <!-- 住宅电话，核心是联系电话 -->
			<Mobile><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Mobile']/DialNumber" /></Mobile><!-- 移动电话-->
			<CompanyPhone><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='Business']/DialNumber" /></CompanyPhone><!-- 办公电话，不可更改 -->
			<EMail><xsl:value-of select="$PartyNode/EMailAddress/AddrLine" /></EMail><!-- 电子邮箱 -->
		</Detail>
		</xsl:template>
		
<!-- 渠道 -->
<xsl:template name="tran_SourceType" match="SourceType">
<xsl:choose>
	<xsl:when test=".=0">ybt</xsl:when>	<!-- 银保通 -->
	<xsl:when test=".=1">wy</xsl:when>	<!-- 网银 -->
	<xsl:when test=".=8">atm</xsl:when>	<!-- 自助终端 -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>
</xsl:template>
<!-- 性别 -->
<xsl:template name="tran_sex" match="Gender">
<xsl:choose> 
	<xsl:when test=".=1">0</xsl:when>	<!-- 男 -->
	<xsl:when test=".=2">1</xsl:when>	<!-- 女 -->
	<xsl:when test=".=3">2</xsl:when>	<!-- 默认 -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>  
</xsl:template>

<!-- 证件类型 -->
<xsl:template name="tran_idtype" match="GovtIDTC">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=9">7</xsl:when>	<!-- 警官证  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
