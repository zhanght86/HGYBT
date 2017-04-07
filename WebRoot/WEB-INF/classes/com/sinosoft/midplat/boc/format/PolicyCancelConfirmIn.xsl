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
      <!-- ũ�������ն����� 0���� 8�����ն� -->
      <SaleChannel>
         <xsl:call-template name="tran_Channel">
			<xsl:with-param name="Channel">
		       <xsl:value-of select="Main/Channel"/>
			</xsl:with-param>
		 </xsl:call-template>
      </SaleChannel>
      <!--������ -->
	  <ContNo><xsl:value-of select="Main/PolicyNo"/></ContNo>
	  <!-- ����ӡˢ�� -->
	  <ContPrtNo></ContPrtNo>
	  <!-- ��������  -->
	  <Password></Password>
	  <!--����������  -->
	  <AccName><xsl:value-of select="Main/Name"/></AccName>
	  <!--֤������ -->
	  <IDType></IDType>
	  <!-- ֤������ -->
	  <IDNo></IDNo>
	  <!--������ˣ������� -->
	  <PayAcc><xsl:value-of select="Main/PayAcc"/></PayAcc>
	  <!--���� -->
	  <Prem></Prem>
	  <!--ҵ������  -->
	  <BusiType></BusiType>
	</Body>
</TranData>
</xsl:template>
<!--��������-->
<xsl:template name="tran_Channel">
  <xsl:param name="Channel"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Channel = '1'">0</xsl:when><!--����-->
  <xsl:when test="$Channel = '2'">07</xsl:when><!--���� -->
   <xsl:when test="$Channel = '4'">06</xsl:when><!--�����ն� -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!-- ֤������     -->
<xsl:template name="tran_idtype"><!--֤������-->
  <xsl:param name="IDType"></xsl:param>
  <xsl:choose>
  <xsl:when test="$IDType = '01'">0</xsl:when><!-- ���֤ -->
  <xsl:when test="$IDType = '02'">C</xsl:when> <!-- ��ʱ���֤ -->
  <xsl:when test="$IDType = '03'">1</xsl:when><!--���� -->
  <xsl:when test="$IDType = '04'">4</xsl:when><!-- ���ڱ� -->
  <xsl:when test="$IDType = '05'">2</xsl:when><!--����֤ -->
  <xsl:when test="$IDType = '06'">D</xsl:when><!-- ����֤ -->
  <xsl:when test="$IDType = '08'">8</xsl:when><!--�⽻��Ա���֤-->
  <xsl:when test="$IDType = '09'">8</xsl:when><!-- ����˾������֤ -->
  <xsl:when test="$IDType = '10'">8</xsl:when><!-- ������뾳ͨ��֤ -->
  <xsl:when test="$IDType = '11'">8</xsl:when><!-- ���� -->
  <xsl:when test="$IDType = '47'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤����ۣ� -->
  <xsl:when test="$IDType = '48'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤�����ţ� -->
  <xsl:when test="$IDType = '49'">F</xsl:when><!-- ̨�����������½ͨ��֤ -->
  <xsl:otherwise>8</xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
