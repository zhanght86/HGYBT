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
				<!-- 险种编号 -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
				<!-- 性别代码 -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="Gnd_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- 保费缴费方式代码  ?还有疑问-->
				<PayEndYearFlag>
					<xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</PayEndYearFlag>
				<!-- 保险年龄 可以保到多少岁，不是保险人多少岁-->	
				<Age><xsl:value-of select="Ins_Age" /></Age >	
				<!-- 保险期限 -->
				<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>
				<!-- 保险缴费期限 -->	
				<PayEndYear>
					<xsl:if test = "InsPrem_PyF_Cyc_Cd = '0100'">1000</xsl:if>
					<xsl:if test = "InsPrem_PyF_Cyc_Cd != '0100'"><xsl:value-of select="InsPrem_PyF_Ddln" /></xsl:if>
				</PayEndYear>	
				<!-- 
					<PayEndYear>
										<xsl:call-template name="tran_Contpayintv">
								aa		<xsl:with-param name="payintv">
											<xsl:value-of select="InsPrem_PyF_Ddln" />
										</xsl:with-param>
									</xsl:call-template>
					</PayEndYear>					
				 -->
				<!-- 主险保额 -->
				<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(MainIns_Cvr)" /></Amnt>
				<!-- 保费金额 -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>	
  			</Body>
		</xsl:template>

	<!-- 保单的缴费方式 -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 9999">D</xsl:when><!-- 不定期交 -->
			<xsl:when test="$payintv = 0100">Y</xsl:when><!-- 趸交 -->
			<xsl:when test="$payintv = 0204">M</xsl:when><!-- 月交 -->
			<xsl:when test="$payintv = 0201">M</xsl:when><!-- 季交 -->
			<xsl:when test="$payintv = 0202">Y</xsl:when><!-- 半年交 -->
			<xsl:when test="$payintv = 0203">Y</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = 0401">A</xsl:when><!-- 交至某确定年 -->
			<xsl:when test="$payintv = 0501">Y</xsl:when><!-- 终身交费 -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 性别 -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- 男 -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- 女 -->
	</xsl:template>
</xsl:stylesheet>