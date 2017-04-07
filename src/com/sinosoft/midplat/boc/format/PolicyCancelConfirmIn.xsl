<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
<xsl:template match="InsuReq">
<TranData>
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
      <!-- 农行自助终端渠道 0柜面 8自助终端 -->
      <SaleChannel>
         <xsl:call-template name="tran_Channel">
			<xsl:with-param name="Channel">
		       <xsl:value-of select="Main/Channel"/>
			</xsl:with-param>
		 </xsl:call-template>
      </SaleChannel>
      <!--保单号 -->
	  <ContNo><xsl:value-of select="Main/PolicyNo"/></ContNo>
	  <!-- 保单印刷号 -->
	  <ContPrtNo></ContPrtNo>
	  <!-- 保单密码  -->
	  <Password></Password>
	  <!--申请人姓名  -->
	  <AccName><xsl:value-of select="Main/Name"/></AccName>
	  <!--证件类型 -->
	  <IDType></IDType>
	  <!-- 证件号码 -->
	  <IDNo></IDNo>
	  <!--领款人账（卡）号 -->
	  <PayAcc><xsl:value-of select="Main/PayAcc"/></PayAcc>
	  <!--保费 -->
	  <Prem></Prem>
	  <!--业务类型  -->
	  <BusiType></BusiType>
	</Body>
</TranData>
</xsl:template>
<!--销售渠道-->
<xsl:template name="tran_Channel">
  <xsl:param name="Channel"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Channel = '1'">0</xsl:when><!--柜面-->
  <xsl:when test="$Channel = '2'">07</xsl:when><!--网银 -->
   <xsl:when test="$Channel = '4'">06</xsl:when><!--自助终端 -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!-- 证件类型     -->
<xsl:template name="tran_idtype"><!--证件类型-->
  <xsl:param name="IDType"></xsl:param>
  <xsl:choose>
  <xsl:when test="$IDType = '01'">0</xsl:when><!-- 身份证 -->
  <xsl:when test="$IDType = '02'">C</xsl:when> <!-- 临时身份证 -->
  <xsl:when test="$IDType = '03'">1</xsl:when><!--护照 -->
  <xsl:when test="$IDType = '04'">4</xsl:when><!-- 户口本 -->
  <xsl:when test="$IDType = '05'">2</xsl:when><!--军官证 -->
  <xsl:when test="$IDType = '06'">D</xsl:when><!-- 警官证 -->
  <xsl:when test="$IDType = '08'">8</xsl:when><!--外交人员身份证-->
  <xsl:when test="$IDType = '09'">8</xsl:when><!-- 外国人居留许可证 -->
  <xsl:when test="$IDType = '10'">8</xsl:when><!-- 边民出入境通行证 -->
  <xsl:when test="$IDType = '11'">8</xsl:when><!-- 其他 -->
  <xsl:when test="$IDType = '47'">F</xsl:when><!-- 港澳居民来往内地通行证（香港） -->
  <xsl:when test="$IDType = '48'">F</xsl:when><!-- 港澳居民来往内地通行证（澳门） -->
  <xsl:when test="$IDType = '49'">F</xsl:when><!-- 台湾居民来往大陆通行证 -->
  <xsl:otherwise>8</xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
