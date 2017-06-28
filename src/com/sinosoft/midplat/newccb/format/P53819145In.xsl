<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<!-- �������� -->
	<xsl:variable name="tSellType" select="/TX/TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_CGY_CODE"></xsl:variable>
	<!-- �����������룺���棺0���ֻ����У�2��������1�������նˣ�8-->
	<xsl:variable name="tLisSaleChnl">
		<xsl:call-template name="transChannel">
		    <xsl:with-param name="nTransChannel">
		      <xsl:value-of select="$tSellType" />
			</xsl:with-param>
	  	</xsl:call-template>
  	</xsl:variable>
	<xsl:template match="TX">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)"/></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- �������� -->	
				<xsl:variable name="tNodeNo" select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID"></xsl:variable>
				<xsl:choose>
					<xsl:when test="$tLisSaleChnl = '0'"><!--�������� -->
						<NodeNo><xsl:value-of select="$tNodeNo" /></NodeNo>
					</xsl:when>
					<xsl:when test="$tLisSaleChnl != '0'"><!--��������:���д������ȡֵ�������ģ�����CCBWEB -->
						<xsl:if test="$tNodeNo = ''">
							<NodeNo>CCBWEB</NodeNo>
						</xsl:if>
						<xsl:if test="$tNodeNo != ''">
							<NodeNo><xsl:value-of select="$tNodeNo"/></NodeNo>
						</xsl:if>
					</xsl:when>
				</xsl:choose>			
				<!-- ���б��� -->
				<BankCode>0104</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
				<!--�����������룺���棺0���ֻ����У�2��������1�������նˣ�8-->
				<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
				<!-- Ͷ������ -->
				<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_BillNo)" /></ProposalPrtNo>
			</Body>
		</TranData>
	</xsl:template>
	<!-- ����ת�� -->
	<xsl:template name="transChannel">
		<xsl:param name="nTransChannel"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nTransChannel='20170029'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20180030'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20220037'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20230038'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='19999999'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='10010001'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10010002'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10010003'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10060009'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10110023'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110016'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110017'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110018'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110109'">8</xsl:when><!--  �ǻ��նˣ��鵽�����ն� --> 
			<xsl:when test="$nTransChannel='10030006'">2</xsl:when><!--  �ֻ����� --> 
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>