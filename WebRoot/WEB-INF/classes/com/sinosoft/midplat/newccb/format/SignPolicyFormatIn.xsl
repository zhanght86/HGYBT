<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="TX">
		<TranData>
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- 报文体 -->
		<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>
	<!-- 报文体 -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
			<!-- 保单号码 Y -->
		   <ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
			<!-- 投保人证件类型代码 Y-->
	 	   <AppntIDType>
	 	   		<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of
							select="Plchd_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
		   </AppntIDType>
	 	   <!-- 投保人证件号码 -->
		   <AppntIDNo><xsl:value-of select="Plchd_Crdt_No" /></AppntIDNo>
		   <!-- 保费金额 -->
		   <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>
		   <!-- 手续费费率 -->
		   <PoundageRate><xsl:value-of select="HdCg_FeeRt"></xsl:value-of></PoundageRate>
		   <!-- 险种编号 -->
		   <RiskCode><xsl:value-of select="Cvr_ID"></xsl:value-of></RiskCode>
		   <!-- 险种名称 -->
		   <RiskName><xsl:value-of select="Cvr_Nm"></xsl:value-of></RiskName>
		   <!-- 营销客户经理编号 -->
		   <AgentCode><xsl:value-of select="Cmpn_CstMgr_ID"></xsl:value-of></AgentCode>
		   <!-- 一级分行号 --> 
	      <BankCodeLv1><xsl:value-of select="Lv1_Br_No" /></BankCodeLv1>
		</Body>
	</xsl:template>	
	
	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- 居民身份证 -->
			<xsl:when test="$idtype = '1011'">0</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1020'">2</xsl:when><!-- 军人身份证件 -->
			<xsl:when test="$idtype = '1030'">2</xsl:when><!-- 武警身份证件 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- 外国护照 -->
			<xsl:when test="$idtype = '1070'">F</xsl:when><!-- 港澳居民往来内地通行证-->
			<xsl:when test="$idtype = '1080'">F</xsl:when><!-- 台湾居民来往大陆通行证-->
			<xsl:when test="$idtype = '1120'">1</xsl:when><!-- (外国)护照-->
			<xsl:when test="$idtype = '1999'">99</xsl:when><!-- 其他证件（个人）-->
			<xsl:when test="$idtype = '2010'">99</xsl:when><!-- 营业执照-->
			<xsl:when test="$idtype = '2020'">99</xsl:when><!-- 组织机构代码证-->
			<xsl:when test="$idtype = '2030'">99</xsl:when><!-- 社会团体法人登记证书-->
			<xsl:when test="$idtype = '2040'">99</xsl:when><!-- 事业法人登记证书-->
			<xsl:when test="$idtype = '2090'">99</xsl:when><!-- 税务登记证-->
			<xsl:when test="$idtype = '2999'">99</xsl:when><!-- 其他证件（对公）-->
			<xsl:otherwise>99</xsl:otherwise><!-- 其他 -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>

