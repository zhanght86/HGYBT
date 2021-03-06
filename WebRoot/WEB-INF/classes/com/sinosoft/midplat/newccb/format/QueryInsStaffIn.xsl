<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
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
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<!-- 保险公司派驻人员姓名 -->
				<Name><xsl:value-of select="Ins_Co_Acrdt_Stff_Nm" /></Name >
				<!-- 证件类型 -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 证件号 -->
				<IDNo><xsl:value-of select="Crdt_No" /></IDNo>			
  			</Body>
		</xsl:template>

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- 公民身份证号码 -->
			<xsl:when test="$idtype = '1022'">2</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = '1032'">D</xsl:when><!-- 警官证 -->
			<xsl:when test="$idtype = '1021'">A</xsl:when><!-- 解放军士兵证 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = '1080'">B</xsl:when><!-- (港澳)回乡证及通行证 -->
			<xsl:when test="$idtype = '1070'">B</xsl:when><!-- 台湾来内地通行证-->
			<xsl:when test="$idtype = '1050'">1</xsl:when><!-- 护照-->
			<xsl:when test="$idtype = '1051'">1</xsl:when><!-- (外国)护照-->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- (中国)护照-->
			<xsl:when test="$idtype = '1060'">5</xsl:when><!-- 学生证-->
			<xsl:when test="$idtype = '1999'">6</xsl:when><!-- 个人其他证件-->
			<xsl:when test="$idtype = '2999'">6</xsl:when><!-- 对公其他证件-->
			<xsl:when test="$idtype = '1100'">3</xsl:when><!-- 驾照 -->
			<xsl:when test="$idtype = '1011'">C</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1160'">E</xsl:when><!-- 台湾居民身份证 台胞证 -->
			<xsl:when test="$idtype = ''"></xsl:when><!-- 不发证件类型 -->
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 投被保人国籍 -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 0156">CHN</xsl:if>
		<xsl:if test="$Nationality = 0344"></xsl:if><!--香港 -->
		<xsl:if test="$Nationality = 0158"></xsl:if><!--台湾 -->
		<xsl:if test="$Nationality = 0446"></xsl:if><!--澳门 -->
		<xsl:if test="$Nationality = 0392">JAN</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = 0840">USA</xsl:if><!--美国-->
		<xsl:if test="$Nationality = 0643"></xsl:if><!--俄罗斯 -->
		<xsl:if test="$Nationality = 0826">ENG</xsl:if><!--英国 -->
		<xsl:if test="$Nationality = 0250">FRA</xsl:if><!--法国-->
		<xsl:if test="$Nationality = 0276">DEU</xsl:if><!--德国 -->
		<xsl:if test="$Nationality = 0410">KOR</xsl:if><!--韩国 -->
		<xsl:if test="$Nationality = 0702">SG</xsl:if><!--新加坡 -->
		<xsl:if test="$Nationality = 0360">INA</xsl:if><!--印度尼西亚-->
		<xsl:if test="$Nationality = 0356">IND</xsl:if><!--印度-->
		<xsl:if test="$Nationality = 0380">ITA</xsl:if><!--意大利 -->
		<xsl:if test="$Nationality = 0458">MY</xsl:if><!--马来西亚 -->
		<xsl:if test="$Nationality = 0764">THA</xsl:if><!--泰国-->
		<xsl:if test="$Nationality = 0999"></xsl:if><!--其他-->
	</xsl:template>


</xsl:stylesheet>
