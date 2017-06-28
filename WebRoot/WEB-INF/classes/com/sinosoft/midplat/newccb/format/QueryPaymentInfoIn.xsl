<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
			<TranData>
				<Head>
					<ServiceId>65</ServiceId>
					<TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
				</Head>
				<BaseInfo>
					<!-- ���б�� -->
					<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
				     <!--�������� -->
					<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
					<!--����ʱ�� -->
					<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
					<!-- һ�����к�(�������) -->
					<ZoneNo>1</ZoneNo>
					<!-- ���տͻ������ṩ�������(��֧���) -->
					<BrNo>1</BrNo>
					<!--��Ա��� -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- #������ˮ�� -->
					<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
					<!--�������� -->
					<FunctionFlag>48</FunctionFlag>
					<!--���չ�˾��� -->
					<InsuID></InsuID>
				    <xsl:copy-of select="Head/*"/>
			  </BaseInfo>
			  <!-- ������ -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<LCCont>
 				<!-- Ͷ������ -->
 				<PrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></PrtNo>			
				<!-- Ͷ�������� -->
				<AppntName><xsl:value-of select="Plchd_Nm" /></AppntName>					
				<!-- Ͷ����֤������ -->
				<AppntIDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Plchd_Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</AppntIDType>			
				<!-- Ͷ����֤���� -->
				<AppntIDNo><xsl:value-of select="Plchd_Crdt_No" /></AppntIDNo>
				<!-- Ͷ����֤����Ч������ -->
				<AppntIDValiDateType><xsl:value-of select="Plchd_Crdt_TpCd" /></AppntIDValiDateType>
				<!-- Ͷ����֤����Ч���� -->
				<AppntIDStartDate><xsl:value-of select="Plchd_Crdt_EfDt" /></AppntIDStartDate>
				<!-- Ͷ����֤��ʧЧ���� -->
				<AppntIDEndDate><xsl:value-of select="Plchd_Crdt_ExpDt" /></AppntIDEndDate>
				<!-- Ͷ���˹��ʵ������� -->
				<AppntNationality><xsl:value-of select="Plchd_Nat_Cd" /></AppntNationality>
  			</LCCont>
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