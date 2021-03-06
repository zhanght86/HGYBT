<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
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
				<!-- 银行端ip[非必须] 
				<ClientIp>127.0.0.1</ClientIp> -->
				<!-- 交易渠道 
				<TranCom>03</TranCom>--> 
				<!-- 交易类型 -->
		 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
				<!-- 服务id 
				<ServiceId>0</ServiceId> -->
				
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

	<!-- 报文体 -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
			    <!-- 保单号码 -->
				<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo>
			    <!-- 保单密码 -->
				<Password><xsl:value-of select="InsPolcy_Pswd" /></Password>
				<!-- 险种编号 -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>	
				<!-- 批单印刷号-->
				<PrintNo><xsl:value-of select="Btch_Bl_Prt_No" /></PrintNo>
									
				<Appnt>		
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
					<!-- 投保人详细地址内容 -->
					<AddressContent><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></AddressContent>
					<!-- 投保人固定电话国内区号 -->
					<FixTelDmstDstcNo><xsl:value-of select="PlchdFixTelDmstDstcNo" /></FixTelDmstDstcNo>
					<!-- 投保人移动电话国际区号 -->
					<MobileItlDstcNo><xsl:value-of select="PlchdMoveTelItlDstcNo" /></MobileItlDstcNo>
					<!-- 投保人地址-->
					<Address><xsl:value-of select="Plchd_Comm_Adr" /></Address>		
					<!-- 投保人邮编-->
					<ZipCode><xsl:value-of select="Plchd_ZipECD" /></ZipCode>			
					<!-- 投保人邮箱-->
					<Email><xsl:value-of select="Plchd_Email_Adr" /></Email>			
					<!-- 投保人固定电话-->
					<Phone><xsl:value-of select="PlchdFixTelDmstDstcNo" /><xsl:value-of select="Plchd_Fix_TelNo" /></Phone>
					<!-- 投保人移动电话-->
					<Mobile><xsl:value-of select="Plchd_Move_TelNo" /></Mobile>
					<!-- 投保人缴费帐号-->
					<AccNo><xsl:value-of select="Plchd_PyF_AccNo" /></AccNo>
					<!-- 投保人领取帐号-->
					<ApptDrwAccNo><xsl:value-of select="Plchd_Drw_AccNo" /></ApptDrwAccNo>
				</Appnt>
				
				<Insured>
					<!-- 被保人帐号-->
					<RcgnAccNo><xsl:value-of select="Rcgn_AccNo" /></RcgnAccNo>
				</Insured>
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
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>