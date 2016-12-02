<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
				<!--交易日期 -->
				<TranDate>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
				</TranDate>
				<!--交易时间 -->
				<TranTime>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" />
				</TranTime>
				<!-- 银行网点 -->
				<NodeNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" />
				</NodeNo>
				<!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<!--柜员号 -->
				<TellerNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" />
				</TellerNo>
				<!-- 交易流水号 -->
				<TranNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
				</TranNo>
				<!-- 银行端ip[非必须]
				<ClientIp>127.0.0.1</ClientIp> -->
				<!-- 交易渠道
				<TranCom>03</TranCom> -->
				<!-- 交易类型 -->
				<!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->
				<!-- 服务id ？待确认
				<ServiceId>0</ServiceId> -->
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
			<ContNo>
				<xsl:value-of select="InsPolcy_No" />
			</ContNo>
			<TranNo>
				<xsl:value-of select="//TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
			</TranNo>
			<!-- 投保人证件类型代码 Y-->
			<IDType>
				<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of
						select="Plchd_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</IDType>
			<!-- 投保人证件号码 -->
			<IDNo>
				<xsl:value-of select="Plchd_Crdt_No" />
			</IDNo>
			<!-- 保费金额 -->
			<Prem>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" />
			</Prem>
			<!-- 一级分行号 -->
			<NodeNo>
				<xsl:value-of select="Lv1_Br_No" />
			</NodeNo>
		</Body>
	</xsl:template>

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when>
			<!-- 公民身份证号码 -->
			<xsl:when test="$idtype = '1022'">2</xsl:when>
			<!-- 军官证 -->
			<xsl:when test="$idtype = '1032'">D</xsl:when>
			<!-- 警官证 -->
			<xsl:when test="$idtype = '1021'">A</xsl:when>
			<!-- 解放军士兵证 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when>
			<!-- 户口簿 -->
			<xsl:when test="$idtype = '1080'">B</xsl:when>
			<!-- (港澳)回乡证及通行证 -->
			<xsl:when test="$idtype = '1070'">B</xsl:when>
			<!-- 台湾来内地通行证-->
			<xsl:when test="$idtype = '1050'">1</xsl:when>
			<!-- 护照-->
			<xsl:when test="$idtype = '1051'">1</xsl:when>
			<!-- (外国)护照-->
			<xsl:when test="$idtype = '1052'">1</xsl:when>
			<!-- (中国)护照-->
			<xsl:when test="$idtype = '1060'">5</xsl:when>
			<!-- 学生证-->
			<xsl:when test="$idtype = '1999'">6</xsl:when>
			<!-- 个人其他证件-->
			<xsl:when test="$idtype = '2999'">6</xsl:when>
			<!-- 对公其他证件-->
			<xsl:when test="$idtype = '1100'">3</xsl:when>
			<!-- 驾照 -->
			<xsl:when test="$idtype = '1011'">C</xsl:when>
			<!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1160'">E</xsl:when>
			<!-- 台湾居民身份证 台胞证 -->
			<xsl:otherwise>
				<xsl:value-of select="8" />
				<!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>

