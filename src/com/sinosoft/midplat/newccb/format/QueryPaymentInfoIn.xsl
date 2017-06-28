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
					<!-- 银行编号 -->
					<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
				     <!--交易日期 -->
					<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
					<!--交易时间 -->
					<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
					<!-- 一级分行号(地区编号) -->
					<ZoneNo>1</ZoneNo>
					<!-- 保险客户名单提供地区编号(分支编号) -->
					<BrNo>1</BrNo>
					<!--柜员编号 -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- #服务方流水号 -->
					<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
					<!--交易类型 -->
					<FunctionFlag>48</FunctionFlag>
					<!--保险公司编号 -->
					<InsuID></InsuID>
				    <xsl:copy-of select="Head/*"/>
			  </BaseInfo>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<LCCont>
 				<!-- 投保单号 -->
 				<PrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></PrtNo>			
				<!-- 投保人姓名 -->
				<AppntName><xsl:value-of select="Plchd_Nm" /></AppntName>					
				<!-- 投保人证件类型 -->
				<AppntIDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Plchd_Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</AppntIDType>			
				<!-- 投保人证件号 -->
				<AppntIDNo><xsl:value-of select="Plchd_Crdt_No" /></AppntIDNo>
				<!-- 投保人证件有效期类型 -->
				<AppntIDValiDateType><xsl:value-of select="Plchd_Crdt_TpCd" /></AppntIDValiDateType>
				<!-- 投保人证件生效日期 -->
				<AppntIDStartDate><xsl:value-of select="Plchd_Crdt_EfDt" /></AppntIDStartDate>
				<!-- 投保人证件失效日期 -->
				<AppntIDEndDate><xsl:value-of select="Plchd_Crdt_ExpDt" /></AppntIDEndDate>
				<!-- 投保人国际地区代码 -->
				<AppntNationality><xsl:value-of select="Plchd_Nat_Cd" /></AppntNationality>
  			</LCCont>
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
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>