<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
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
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
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
				<!-- ���ж�ip[�Ǳ���] 
				<ClientIp>127.0.0.1</ClientIp> -->
				<!-- �������� 
				<TranCom>03</TranCom> -->
				<!-- �������� -->
				<!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
				<!-- ����id ?��ȷ��
				<ServiceId>1</ServiceId>--> 
				
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
		<!--�����������룺���棺0���ֻ����У�2��������1�������նˣ�8-->
		<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
		<!-- ���ֱ�� -->
		<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
		<!-- �������� -->
		<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
			<!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalPrtNo>			
		<!-- Ͷ�������� -->
		<Name><xsl:value-of select="Plchd_Nm" /></Name>					
		<!-- Ͷ����֤������ -->
		<IDType>
			<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="Plchd_Crdt_TpCd" />
				</xsl:with-param>
			</xsl:call-template>
		</IDType>			
		<!-- Ͷ����֤���� -->
		<IDNo><xsl:value-of select="Plchd_Crdt_No" /></IDNo>
			</Body>
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
<!-- ֤������ -->
<xsl:template name="tran_idtype">
	<xsl:param name="idtype"></xsl:param>
	<xsl:choose>
		<xsl:when test="$idtype = '1010'">0</xsl:when><!-- �������֤���� -->
		<xsl:when test="$idtype = '1022'">2</xsl:when><!-- ����֤ -->
		<xsl:when test="$idtype = '1032'">D</xsl:when><!-- ����֤ -->
		<xsl:when test="$idtype = '1021'">A</xsl:when><!-- ��ž�ʿ��֤ -->
		<xsl:when test="$idtype = '1040'">4</xsl:when><!-- ���ڲ� -->
		<xsl:when test="$idtype = '1080'">B</xsl:when><!-- (�۰�)����֤��ͨ��֤ -->
		<xsl:when test="$idtype = '1070'">B</xsl:when><!-- ̨�����ڵ�ͨ��֤-->
		<xsl:when test="$idtype = '1050'">1</xsl:when><!-- ����-->
		<xsl:when test="$idtype = '1051'">1</xsl:when><!-- (���)����-->
		<xsl:when test="$idtype = '1052'">1</xsl:when><!-- (�й�)����-->
		<xsl:when test="$idtype = '1060'">5</xsl:when><!-- ѧ��֤-->
		<xsl:when test="$idtype = '1999'">6</xsl:when><!-- ��������֤��-->
		<xsl:when test="$idtype = '2999'">6</xsl:when><!-- �Թ�����֤��-->
		<xsl:when test="$idtype = '1100'">3</xsl:when><!-- ���� -->
		<xsl:when test="$idtype = '1011'">C</xsl:when><!-- ��ʱ�������֤ -->
		<xsl:when test="$idtype = '1160'">E</xsl:when><!-- ̨��������֤ ̨��֤ -->
		<xsl:otherwise>
				<xsl:value-of select="8" /><!-- ���� -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>