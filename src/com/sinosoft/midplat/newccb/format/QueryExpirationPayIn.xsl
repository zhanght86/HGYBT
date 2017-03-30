<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
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
					<!-- ���ж�ip[�Ǳ���] 
					<ClientIp>127.0.0.1</ClientIp> -->
					<!-- �������� 
					<TranCom>03</TranCom> -->
					<!-- �������� -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- ����id ?��ȷ��
					<ServiceId>1</ServiceId> -->
					
					<!-- �����������淽ʽ������Ϊ�˲������Լ��������ֶ�-->
					
					<!-- �������� �����������-->
					<!-- ����id �����������-->
					<!-- ���ж�ip[�Ǳ���] �����������-->
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- ������ -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<!-- ���յ��� -->
				<ContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(InsPolcy_No)" /></ContNo> 
				<!-- �������� -->
				<Password><xsl:value-of select="InsPolcy_Pswd" /></Password>
				<!--���չ�˾����-->
				<CarrierCode></CarrierCode> 
				<!-- ���ֱ�� -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
				<!-- ֤������ -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- ֤���� -->
				<IDNo><xsl:value-of select="Crdt_No" /></IDNo>			
				<!-- ���������� -->
				<Name></Name >
				<!--���н�������-->
				<Channel></Channel>
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
			<xsl:when test="$idtype = '1999'">8</xsl:when><!-- ����֤�������ˣ�-->
			<xsl:when test="$idtype = '2010'">8</xsl:when><!-- Ӫҵִ��-->
			<xsl:when test="$idtype = '2020'">8</xsl:when><!-- ��֯��������֤-->
			<xsl:when test="$idtype = '2030'">8</xsl:when><!-- ������巨�˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2040'">8</xsl:when><!-- ��ҵ���˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2090'">8</xsl:when><!-- ˰��Ǽ�֤-->
			<xsl:when test="$idtype = '2999'">8</xsl:when><!-- ����֤�����Թ���-->
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- ���� -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>