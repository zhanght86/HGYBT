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
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- �������� -->
			<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
			<!-- ���յ��� -->
			<ContNo />
			<!-- Ͷ����(ӡˢ)�� -->
			<xsl:choose>
		   		<!-- ���� -->
		   		<xsl:when test="Header/EntrustWay='11'">
		   			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyNo)"/></ProposalPrtNo>
		   		</xsl:when>
		   		<xsl:otherwise>
			   		<xsl:variable name="tMaxPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.CreateMaxNo('ABCPRTNO','SN')"></xsl:variable> 
					<xsl:variable name="tPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.PrtNoTo8($tMaxPrtNo,'05')"></xsl:variable> 
					<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($tPrtNo)" /></ProposalPrtNo>
		   		</xsl:otherwise>
		   	</xsl:choose>
			<!-- ������ͬӡˢ�� -->
			<ContPrtNo/>
			<!-- ��������˳��� -->
			<ApplyNo><xsl:value-of select="App/Req/ApplySerial" /></ApplyNo>
			<!-- Ͷ������ -->
			<PolApplyDate></PolApplyDate>
			<BkAcctNo><xsl:value-of select="App/Req/PayAccount" /></BkAcctNo>
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/PayAmt)"/></Prem>	
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
