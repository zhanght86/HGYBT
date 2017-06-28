<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!-- 银行渠道 -->
	<xsl:variable name="tSellType" select="/TX/TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_CGY_CODE"></xsl:variable>
	<!-- 核心渠道编码：柜面：0，手机银行：2，网银：1，自助终端：8-->
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
			    <!-- 交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
			    <!-- 交易时间 -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
			    <!-- 银行网点 -->
			    <xsl:variable name="tNodeNo" select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID"></xsl:variable>
			    <xsl:choose>
					<xsl:when test="$tLisSaleChnl = '0'"><!--柜面渠道 -->
						<NodeNo><xsl:value-of select="$tNodeNo" /></NodeNo>
					</xsl:when>
					<xsl:when test="$tLisSaleChnl != '0'"><!--网销渠道:银行传网点就取值传给核心，否则传CCBWEB -->
						<xsl:if test="$tNodeNo = ''">
							<NodeNo>CCBWEB</NodeNo>
						</xsl:if>
						<xsl:if test="$tNodeNo != ''">
							<NodeNo><xsl:value-of select="$tNodeNo"/></NodeNo>
						</xsl:if>
					</xsl:when>
				</xsl:choose>
			    <!-- 柜员号 -->
				<TellerNo><xsl:value-of	 select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
			    <!-- 交易流水号 -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
			    <!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<!-- 报文体 -->
			<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>			
	<!-- 报文体 -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">			
		<Body>
			<!--核心渠道编码：柜面：0，手机银行：2，网银：1，自助终端：8-->
			<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
			<!-- 投保单印刷号 -->
			<ProposalPrtNo></ProposalPrtNo>
  			<!--保单合同印刷号 (单证) 银行第一次不传，返回核心返回必须要录入单证号，银行第二次发单证号-->
  			<ContPrtNo></ContPrtNo>   
  			<!--保单合同号-->
  			<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo>   
  			<!--原交易流水号-->
  			<TranNo><xsl:value-of select="Ins_Co_Jrnl_No" /></TranNo>   
            <!--投保人缴费账号  -->
            <AccNo><xsl:value-of select="CCB_AccNo" /></AccNo>
            <!-- 账户姓名 工行不传账户名默认为投保人姓名-->
 			 <AccName></AccName> 
		</Body>
	</xsl:template>
	<!-- 渠道转换 -->
	<xsl:template name="transChannel">
		<xsl:param name="nTransChannel"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nTransChannel='20170029'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20180030'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20220037'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20230038'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='19999999'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='10010001'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10010002'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10010003'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10060009'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10110023'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110016'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110017'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110018'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110109'">8</xsl:when><!--  智慧终端，归到自助终端 --> 
			<xsl:when test="$nTransChannel='10030006'">2</xsl:when><!--  手机银行 --> 
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
