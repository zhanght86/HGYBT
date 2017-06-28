<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
<xsl:template match="TX">
<TranData>
    <Head>
      <ServiceId>67</ServiceId>
      <TranCom><xsl:value-of select="Head/TranCom" /></TranCom>
    </Head>
	<BaseInfo>
		<!--���б�� -->
		<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
		<!--�������� -->
		<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
		<!--����ʱ�� -->
		<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
		<!-- һ�����к�(�������)�����ĶԴ��зǿ�У�� -->
		<ZoneNo>99999</ZoneNo>
		<!-- ��������������|��������|�������ڴ���|���ѽɷѷ�ʽ����|���ѽɷ�����|���ѽɷ����ڴ��� 
			��ʵʱ�˱�״̬���ºͲ�ѯ��ʵʱ�ɷ���Ϣʹ��-->
		<xsl:variable name="insYrPrdCgyCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Yr_Prd_CgyCd"/>
		<xsl:variable name="insDdln"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Ddln"/>
		<xsl:variable name="insCycCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Cyc_Cd"/>
		<xsl:variable name="insPremPyFMtdCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_MtdCd"/>
		<xsl:variable name="insPremPyFPrdNum"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_Prd_Num"/>
		<xsl:variable name="insPremPyFCycCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_Cyc_Cd"/>
		<RiskMessage>
			<xsl:value-of select="$insYrPrdCgyCd" />|<xsl:value-of select="$insDdln" />|<xsl:value-of select="$insCycCd" />|<xsl:value-of select="$insPremPyFMtdCd" />|<xsl:value-of select="$insPremPyFPrdNum" />|<xsl:value-of select="$insPremPyFCycCd" />
		</RiskMessage>
		<!-- ����� -->
		<BrNo>0104</BrNo>
		<!--��Ա��� -->
		<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
		<!--#������ˮ�� -->
		<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
		<!--�������� -->
		<FunctionFlag><xsl:value-of select="Head/FuncFlag" /></FunctionFlag>
		<!--���չ�˾��� -->
		<InsuID><xsl:value-of select="Head/InsuID" /></InsuID>
		<!--һ�����к�-->
		<BankBrNo_Lv1><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Lv1_Br_No" /></BankBrNo_Lv1>
		<InNoDoc><xsl:value-of select="Head/InNoDoc" /></InNoDoc>
	</BaseInfo>
	<LCCont>
      <Risks>
	    <!-- ���ָ��� -->
        <RiskCount><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Cvr_Num" /></RiskCount>
        <xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail">
	    <Risk>
		  <!-- ���ֱ�� -->
		  <RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
	    </Risk>
	    </xsl:for-each>
	  </Risks>
	  <!--Ͷ����ӡˢ�� -->
	  <ProposalContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_Bl_Prt_No)" /></ProposalContNo>
	  <!-- Ԥ���� -->
	  <xsl:variable name="BudGet" select="TX_BODY/ENTITY/APP_ENTITY/InsPrem_Bdgt_Amt"/>
	  <BudGet><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($BudGet)" /></BudGet>
	  <LCAppnt>
	    <!-- Ͷ�������� -->
		<AppntName><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Nm" /></AppntName>
		<!-- Ͷ����֤�����ʹ��� -->
		<AppntIDType>
		<xsl:call-template name="tran_AppntIDType">
		  <xsl:with-param name="AppntIDType">
		    <xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Crdt_TpCd"></xsl:value-of>
		  </xsl:with-param>
		</xsl:call-template>
		</AppntIDType>
		<!-- Ͷ����֤������ -->
		<AppntIDNo><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Crdt_No" /></AppntIDNo>
	  </LCAppnt>
	</LCCont>
</TranData>
</xsl:template>
<!-- ֤������ -->
<xsl:template name="tran_AppntIDType">
  <xsl:param name="AppntIDType"></xsl:param>
  <xsl:choose>
    <xsl:when test="$AppntIDType = '1010'">0</xsl:when><!-- �������֤ -->
	<xsl:when test="$AppntIDType = '1011'">0</xsl:when><!-- ��ʱ�������֤ -->
	<xsl:when test="$AppntIDType = '1020'">2</xsl:when><!-- �������֤�� -->
	<xsl:when test="$AppntIDType = '1030'">2</xsl:when><!-- �侯���֤�� -->
	<xsl:when test="$AppntIDType = '1040'">4</xsl:when><!-- ���ڲ� -->
	<xsl:when test="$AppntIDType = '1052'">1</xsl:when><!-- ������� -->
	<xsl:when test="$AppntIDType = '1070'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤-->
	<xsl:when test="$AppntIDType = '1080'">F</xsl:when><!-- ̨�����������½ͨ��֤-->
	<xsl:when test="$AppntIDType = '1120'">1</xsl:when><!-- (���)����-->
	<xsl:when test="$AppntIDType = '1999'">99</xsl:when><!-- ����֤�������ˣ�-->
	<xsl:when test="$AppntIDType = '2010'">99</xsl:when><!-- Ӫҵִ��-->
	<xsl:when test="$AppntIDType = '2020'">99</xsl:when><!-- ��֯��������֤-->
	<xsl:when test="$AppntIDType = '2030'">99</xsl:when><!-- ������巨�˵Ǽ�֤��-->
	<xsl:when test="$AppntIDType = '2040'">99</xsl:when><!-- ��ҵ���˵Ǽ�֤��-->
	<xsl:when test="$AppntIDType = '2090'">99</xsl:when><!-- ˰��Ǽ�֤-->
	<xsl:when test="$AppntIDType = '2999'">99</xsl:when><!-- ����֤�����Թ���-->
	<xsl:otherwise>
	  <xsl:value-of select="99" /><!-- ���� -->
	</xsl:otherwise>
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>