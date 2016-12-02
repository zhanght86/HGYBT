<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--冲正-->
	<xsl:template match="TX">
		<TranData>
			<Head>
				<!-- 交易日期 -->
				<TranDate>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
				</TranDate>
				<!-- 交易时间 -->
				<TranTime>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" />
				</TranTime>
				<!-- 银行网点 -->
				<NodeNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" />
				</NodeNo>
				<!-- 柜员号 -->
				<TellerNo>
					<xsl:value-of	 select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" />
				</TellerNo>
				<!-- 交易流水号 -->
				<TranNo>
					<xsl:value-of	select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
				</TranNo>
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
			<!-- 投保单印刷号 -->
			<ProposalPrtNo></ProposalPrtNo>
			<!--保单合同印刷号 (单证) 银行第一次不传，返回核心返回必须要录入单证号，银行第二次发单证号-->
			<ContPrtNo></ContPrtNo>
			<!--保单合同号-->
			<ContNo>
				<xsl:value-of select="InsPolcy_No" />
			</ContNo>
			<!--原交易流水号-->
			<TranNo>
				<xsl:value-of select="Ins_Co_Jrnl_No" />
			</TranNo>
			<!--投保人缴费账号  -->
			<AccNo>
				<xsl:value-of select="CCB_AccNo" />
			</AccNo>
			<!-- 账户姓名 工行不传账户名默认为投保人姓名-->
			<AccName></AccName>
		</Body>
	</xsl:template>


</xsl:stylesheet>
