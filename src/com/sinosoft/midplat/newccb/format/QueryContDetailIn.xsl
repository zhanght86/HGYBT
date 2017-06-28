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
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--交易时间 -->
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
				<ServiceId>1</ServiceId>--> 
				
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
		<!--核心渠道编码：柜面：0，手机银行：2，网银：1，自助终端：8-->
		<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
		<!-- 险种编号 -->
		<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
		<!-- 保单号码 -->
		<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
			<!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalPrtNo>			
		<!-- 投保人姓名 -->
		<Name><xsl:value-of select="Plchd_Nm" /></Name>					
		<!-- 投保人证件类型 -->
		<IDType>
			<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="Plchd_Crdt_TpCd" />
				</xsl:with-param>
			</xsl:call-template>
		</IDType>			
		<!-- 投保人证件号 -->
		<IDNo><xsl:value-of select="Plchd_Crdt_No" /></IDNo>
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