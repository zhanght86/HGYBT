<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
 <TranData>
 	<xsl:apply-templates select="TXLife/TXLifeRequest" />
	
	<Body>
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy" />
	</Body>
</TranData>
</xsl:template>

<xsl:template name="Head" match="TXLifeRequest">
<Head> 
	<!-- �������� -->
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<!-- ����ʱ�� -->
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<!-- ���׵�λ(����/ũ����/������˾) -->
	<BankCode>01</BankCode>
	<TranCom>1</TranCom>
	<!-- �������� -->
	<NodeNo>
		<xsl:value-of select="OLifEExtension/RegionCode"/>
		<xsl:value-of select="OLifEExtension/Branch"/>
	</NodeNo>
	<!-- ��Ա���� -->
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<!-- ������ˮ�� -->
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
	<!-- �ӽ����� 401 -->
	<FuncFlag>41</FuncFlag>
	<FuncFlagDetail>411</FuncFlagDetail>
	<!-- �����ױ���  40 -->
	<AgentCom></AgentCom>
	<AgentCode></AgentCode>
</Head>
</xsl:template>

    <xsl:template name="Body" match="Policy">
    <!-- ������ -->
    <PolNumber><xsl:value-of select="PolNumber"/></PolNumber>
     <ProviderFormNumber><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/FormInstance/ProviderFormNumber"/></ProviderFormNumber>
    <xsl:variable name="AppntNode"
				select="/TXLife/TXLifeRequest/OLifE/Party[@id='Party_1']" />
	
    <!-- Ͷ����֤������ -->
    <AppntIDType><xsl:value-of select="$AppntNode/GovtIDTC"/></AppntIDType> 
    <!-- Ͷ����֤������ -->
    <AppntID><xsl:value-of select="$AppntNode/GovtID"/></AppntID> 
    <!-- Ͷ�������� -->
    <AppntName><xsl:value-of select="$AppntNode/FullName"/></AppntName>
    <xsl:variable name="InsNode"
				select="/TXLife/TXLifeRequest/OLifE/Party[@id='Party_2']" />
    <!-- ������֤������ -->
    <InsuredIDType><xsl:value-of select="$InsNode/GovtIDTC"/></InsuredIDType> 
    <!-- ������֤������ -->
    <InsuredID><xsl:value-of select="$InsNode/GovtID"/></InsuredID>
    <!-- ���������� -->
    <InsuredName><xsl:value-of select="$InsNode/FullName"/></InsuredName>
    <ReasonCode>
         <xsl:call-template name="tran_WithDrawReason">
					<xsl:with-param name="WithDrawReason">
								<xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason" />
				</xsl:with-param>
			</xsl:call-template>
    </ReasonCode>
    <xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 0">
    	 <ReasonName>�ƾӹ���</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 1 or /TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 3">
    	 <ReasonName>�ƾ����</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 2">
    	 <ReasonName>����ԭ��</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 4 or /TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 6">
    	 <ReasonName>���ֲ�����</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 5">
    	 <ReasonName>ת��˾Ͷ��</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 7">
    	 <ReasonName>Ӫ��Ա��ְ</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 8">
    	 <ReasonName>��������</ReasonName>
    	</xsl:if>
    	<xsl:if test = "/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/WithDrawReason= 9">
    	 <ReasonName>����ԭ��</ReasonName>
    	</xsl:if>   
</xsl:template>
<xsl:template name="tran_WithDrawReason">
		<xsl:param name="WithDrawReason">99</xsl:param>
		<xsl:if test="$WithDrawReason =0">10</xsl:if><!-- �������� -->
		<xsl:if test="$WithDrawReason =1">7</xsl:if><!-- �ƾ����-->
		<xsl:if test="$WithDrawReason =2">9</xsl:if><!-- ����ԭ�� -->
		<xsl:if test="$WithDrawReason =3">7</xsl:if><!-- �ƾ���� -->
		<xsl:if test="$WithDrawReason =4">4</xsl:if><!-- ���ֲ����� -->
		<xsl:if test="$WithDrawReason =5">24</xsl:if><!-- ת��˾Ͷ�� -->
		<xsl:if test="$WithDrawReason =6">4</xsl:if><!-- ���ֲ����� -->
		<xsl:if test="$WithDrawReason =7">25</xsl:if><!-- Ӫ��Ա��ְ -->
		<xsl:if test="$WithDrawReason =8">5</xsl:if><!-- �������� -->
		<xsl:if test="$WithDrawReason =9">99</xsl:if><!-- ����ԭ�� -->
	</xsl:template>

</xsl:stylesheet>
