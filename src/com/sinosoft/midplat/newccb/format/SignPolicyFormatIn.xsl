<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="TX">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- �������� -->
				<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0104</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- ������ -->
		<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>
	<!-- ������ -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
			<!-- �������� Y -->
		   <ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
			<!-- Ͷ����֤�����ʹ��� Y-->
	 	   <AppntIDType>
	 	   		<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of
							select="Plchd_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
		   </AppntIDType>
	 	   <!-- Ͷ����֤������ -->
		   <AppntIDNo><xsl:value-of select="Plchd_Crdt_No" /></AppntIDNo>
		   <!-- ���ѽ�� -->
		   <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>
		   <!-- �����ѷ��� -->
		   <PoundageRate><xsl:value-of select="HdCg_FeeRt"></xsl:value-of></PoundageRate>
		   <!-- ���ֱ�� -->
		   <RiskCode><xsl:value-of select="Cvr_ID"></xsl:value-of></RiskCode>
		   <!-- �������� -->
		   <RiskName><xsl:value-of select="Cvr_Nm"></xsl:value-of></RiskName>
		   <!-- Ӫ���ͻ������� -->
		   <AgentCode><xsl:value-of select="Cmpn_CstMgr_ID"></xsl:value-of></AgentCode>
		   <!-- һ�����к� --> 
	      <BankCodeLv1><xsl:value-of select="Lv1_Br_No" /></BankCodeLv1>
		</Body>
	</xsl:template>	
	
	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- �������֤ -->
			<xsl:when test="$idtype = '1011'">0</xsl:when><!-- ��ʱ�������֤ -->
			<xsl:when test="$idtype = '1020'">2</xsl:when><!-- �������֤�� -->
			<xsl:when test="$idtype = '1030'">2</xsl:when><!-- �侯���֤�� -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- ���ڲ� -->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- ������� -->
			<xsl:when test="$idtype = '1070'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤-->
			<xsl:when test="$idtype = '1080'">F</xsl:when><!-- ̨�����������½ͨ��֤-->
			<xsl:when test="$idtype = '1120'">1</xsl:when><!-- (���)����-->
			<xsl:when test="$idtype = '1999'">99</xsl:when><!-- ����֤�������ˣ�-->
			<xsl:when test="$idtype = '2010'">99</xsl:when><!-- Ӫҵִ��-->
			<xsl:when test="$idtype = '2020'">99</xsl:when><!-- ��֯��������֤-->
			<xsl:when test="$idtype = '2030'">99</xsl:when><!-- ������巨�˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2040'">99</xsl:when><!-- ��ҵ���˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2090'">99</xsl:when><!-- ˰��Ǽ�֤-->
			<xsl:when test="$idtype = '2999'">99</xsl:when><!-- ����֤�����Թ���-->
			<xsl:otherwise>99</xsl:otherwise><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>

