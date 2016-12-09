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
				<!-- ���ֱ�� -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
				<!-- �Ա���� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="Gnd_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- ���ѽɷѷ�ʽ����  ?��������-->
				<PayEndYearFlag>
					<xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</PayEndYearFlag>
				<!-- �������� ���Ա��������꣬���Ǳ����˶�����-->	
				<Age><xsl:value-of select="Ins_Age" /></Age >	
				<!-- �������� -->
				<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>
				<!-- ���սɷ����� -->	
				<PayEndYear>
					<xsl:if test = "InsPrem_PyF_Cyc_Cd = '0100'">1000</xsl:if>
					<xsl:if test = "InsPrem_PyF_Cyc_Cd != '0100'"><xsl:value-of select="InsPrem_PyF_Ddln" /></xsl:if>
				</PayEndYear>	
				<!-- 
					<PayEndYear>
										<xsl:call-template name="tran_Contpayintv">
								aa		<xsl:with-param name="payintv">
											<xsl:value-of select="InsPrem_PyF_Ddln" />
										</xsl:with-param>
									</xsl:call-template>
					</PayEndYear>					
				 -->
				<!-- ���ձ��� -->
				<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(MainIns_Cvr)" /></Amnt>
				<!-- ���ѽ�� -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>	
  			</Body>
		</xsl:template>

	<!-- �����Ľɷѷ�ʽ -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 9999">D</xsl:when><!-- �����ڽ� -->
			<xsl:when test="$payintv = 0100">Y</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 0204">M</xsl:when><!-- �½� -->
			<xsl:when test="$payintv = 0201">M</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 0202">Y</xsl:when><!-- ���꽻 -->
			<xsl:when test="$payintv = 0203">Y</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = 0401">A</xsl:when><!-- ����ĳȷ���� -->
			<xsl:when test="$payintv = 0501">Y</xsl:when><!-- ������ -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- �Ա� -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- �� -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- Ů -->
	</xsl:template>
</xsl:stylesheet>