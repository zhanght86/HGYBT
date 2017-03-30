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
					<!-- 银行端ip[非必须] 
					<ClientIp>127.0.0.1</ClientIp> -->
					<!-- 交易渠道 
					<TranCom>03</TranCom> -->
					<!-- 交易类型 -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- 服务id ?待确认
					<ServiceId>1</ServiceId> -->
					
					<!-- 生产上用下面方式，上面为了测试所以加上下面字段-->
					
					<!-- 交易类型 服务类中添加-->
					<!-- 服务id 服务类中添加-->
					<!-- 银行端ip[非必须] 服务类中添加-->
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<!-- 保险单号 -->
				<ContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(InsPolcy_No)" /></ContNo> 
				<!-- 保单密码 -->
				<Password><xsl:value-of select="InsPolcy_Pswd" /></Password>
				<!--保险公司代码-->
				<CarrierCode></CarrierCode> 
				<!-- 险种编号 -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
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
				<!-- 被保人姓名 -->
				<Name></Name >
				<!--银行交易渠道-->
				<Channel></Channel>
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
			<xsl:when test="$idtype = '1999'">8</xsl:when><!-- 其他证件（个人）-->
			<xsl:when test="$idtype = '2010'">8</xsl:when><!-- 营业执照-->
			<xsl:when test="$idtype = '2020'">8</xsl:when><!-- 组织机构代码证-->
			<xsl:when test="$idtype = '2030'">8</xsl:when><!-- 社会团体法人登记证书-->
			<xsl:when test="$idtype = '2040'">8</xsl:when><!-- 事业法人登记证书-->
			<xsl:when test="$idtype = '2090'">8</xsl:when><!-- 税务登记证-->
			<xsl:when test="$idtype = '2999'">8</xsl:when><!-- 其他证件（对公）-->
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>