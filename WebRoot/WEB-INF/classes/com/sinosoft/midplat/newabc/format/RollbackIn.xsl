<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- �����µ��ع������� -->
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
			<xsl:choose>
				<xsl:when test="Header/EntrustWay='11'"><!-- ���� -->
					<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				</xsl:when>
				<xsl:otherwise><!-- ��������ʵ���������ȡʵ�����㣬������ȡ�������� -->
					<xsl:if test="Header/ProvCode != '' and Header/BranchNo != ''">
						<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
					</xsl:if>
					<xsl:if test="Header/ProvCode = '' or Header/BranchNo = ''">
						<NodeNo>ABCWEB</NodeNo>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<!-- ��Ա���� -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay = '11'">
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
		<Body>
			<!-- �������� -->
			<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
			<!-- ���յ��� -->
			<xsl:variable name="policyNo" select="App/Req/PolicyNo"/>
			<ContNo>
				<xsl:choose>
					<xsl:when test="string-length($policyNo)=13">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($policyNo)"/>	
					</xsl:when>
					<xsl:otherwise><xsl:value-of select="$policyNo"/></xsl:otherwise>
				</xsl:choose>
			</ContNo>
			<!-- Ͷ����(ӡˢ)�� -->
			<ProposalPrtNo></ProposalPrtNo>
			<OldTranNo><xsl:value-of select="App/Req/OrgSerialNo"/></OldTranNo>
		</Body>
</TranData>
</xsl:template>

<!-- ί�з�ʽ
01-������������
02-������������
04-���������ն�����
11-���й�̨����
20-���չ�˾����
 -->
<xsl:template name="tran_salechannel" match="EntrustWay">
	<xsl:choose>
		<xsl:when test=".=01">1</xsl:when><!-- ������������ -->
		<xsl:when test=".=02">2</xsl:when><!-- ������������ -->
		<xsl:when test=".=04">8</xsl:when><!-- ���������ն����� -->
		<xsl:when test=".=11">0</xsl:when><!-- ���й�̨���� -->
		<xsl:when test=".=20"></xsl:when><!-- ���չ�˾���� -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>