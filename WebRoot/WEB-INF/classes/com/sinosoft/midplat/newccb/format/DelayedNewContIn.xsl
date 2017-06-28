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
		<!--银行编号 -->
		<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
		<!--交易日期 -->
		<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
		<!--交易时间 -->
		<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
		<!-- 一级分行号(地区编号)：核心对此有非空校验 -->
		<ZoneNo>99999</ZoneNo>
		<!-- 保险年期类别代码|保险期限|保险周期代码|保费缴费方式代码|保费缴费期数|保费缴费周期代码 
			非实时核保状态更新和查询非实时缴费信息使用-->
		<xsl:variable name="insYrPrdCgyCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Yr_Prd_CgyCd"/>
		<xsl:variable name="insDdln"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Ddln"/>
		<xsl:variable name="insCycCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/Ins_Cyc_Cd"/>
		<xsl:variable name="insPremPyFMtdCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_MtdCd"/>
		<xsl:variable name="insPremPyFPrdNum"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_Prd_Num"/>
		<xsl:variable name="insPremPyFCycCd"  select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail/InsPrem_PyF_Cyc_Cd"/>
		<RiskMessage>
			<xsl:value-of select="$insYrPrdCgyCd" />|<xsl:value-of select="$insDdln" />|<xsl:value-of select="$insCycCd" />|<xsl:value-of select="$insPremPyFMtdCd" />|<xsl:value-of select="$insPremPyFPrdNum" />|<xsl:value-of select="$insPremPyFCycCd" />
		</RiskMessage>
		<!-- 网点号 -->
		<BrNo>0104</BrNo>
		<!--柜员编号 -->
		<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
		<!--#服务方流水号 -->
		<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
		<!--交易类型 -->
		<FunctionFlag><xsl:value-of select="Head/FuncFlag" /></FunctionFlag>
		<!--保险公司编号 -->
		<InsuID><xsl:value-of select="Head/InsuID" /></InsuID>
		<!--一级分行号-->
		<BankBrNo_Lv1><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Lv1_Br_No" /></BankBrNo_Lv1>
		<InNoDoc><xsl:value-of select="Head/InNoDoc" /></InNoDoc>
	</BaseInfo>
	<LCCont>
      <Risks>
	    <!-- 险种个数 -->
        <RiskCount><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Cvr_Num" /></RiskCount>
        <xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Bu_List/Bu_Detail">
	    <Risk>
		  <!-- 险种编号 -->
		  <RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
	    </Risk>
	    </xsl:for-each>
	  </Risks>
	  <!--投保单印刷号 -->
	  <ProposalContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_Bl_Prt_No)" /></ProposalContNo>
	  <!-- 预算金额 -->
	  <xsl:variable name="BudGet" select="TX_BODY/ENTITY/APP_ENTITY/InsPrem_Bdgt_Amt"/>
	  <BudGet><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($BudGet)" /></BudGet>
	  <LCAppnt>
	    <!-- 投保人名称 -->
		<AppntName><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Nm" /></AppntName>
		<!-- 投保人证件类型代码 -->
		<AppntIDType>
		<xsl:call-template name="tran_AppntIDType">
		  <xsl:with-param name="AppntIDType">
		    <xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Crdt_TpCd"></xsl:value-of>
		  </xsl:with-param>
		</xsl:call-template>
		</AppntIDType>
		<!-- 投保人证件号码 -->
		<AppntIDNo><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Plchd_Crdt_No" /></AppntIDNo>
	  </LCAppnt>
	</LCCont>
</TranData>
</xsl:template>
<!-- 证件类型 -->
<xsl:template name="tran_AppntIDType">
  <xsl:param name="AppntIDType"></xsl:param>
  <xsl:choose>
    <xsl:when test="$AppntIDType = '1010'">0</xsl:when><!-- 居民身份证 -->
	<xsl:when test="$AppntIDType = '1011'">0</xsl:when><!-- 临时居民身份证 -->
	<xsl:when test="$AppntIDType = '1020'">2</xsl:when><!-- 军人身份证件 -->
	<xsl:when test="$AppntIDType = '1030'">2</xsl:when><!-- 武警身份证件 -->
	<xsl:when test="$AppntIDType = '1040'">4</xsl:when><!-- 户口簿 -->
	<xsl:when test="$AppntIDType = '1052'">1</xsl:when><!-- 外国护照 -->
	<xsl:when test="$AppntIDType = '1070'">F</xsl:when><!-- 港澳居民往来内地通行证-->
	<xsl:when test="$AppntIDType = '1080'">F</xsl:when><!-- 台湾居民来往大陆通行证-->
	<xsl:when test="$AppntIDType = '1120'">1</xsl:when><!-- (外国)护照-->
	<xsl:when test="$AppntIDType = '1999'">99</xsl:when><!-- 其他证件（个人）-->
	<xsl:when test="$AppntIDType = '2010'">99</xsl:when><!-- 营业执照-->
	<xsl:when test="$AppntIDType = '2020'">99</xsl:when><!-- 组织机构代码证-->
	<xsl:when test="$AppntIDType = '2030'">99</xsl:when><!-- 社会团体法人登记证书-->
	<xsl:when test="$AppntIDType = '2040'">99</xsl:when><!-- 事业法人登记证书-->
	<xsl:when test="$AppntIDType = '2090'">99</xsl:when><!-- 税务登记证-->
	<xsl:when test="$AppntIDType = '2999'">99</xsl:when><!-- 其他证件（对公）-->
	<xsl:otherwise>
	  <xsl:value-of select="99" /><!-- 其他 -->
	</xsl:otherwise>
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>