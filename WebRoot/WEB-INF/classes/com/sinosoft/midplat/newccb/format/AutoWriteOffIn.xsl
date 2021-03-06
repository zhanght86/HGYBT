<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<!--冲正-->
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
			<!-- 原交易流水号 -->
			<OldTranNo><xsl:value-of select="Orig_TxnSrlNo" /></OldTranNo>
			<!-- 投保单号  format类中添加-->
			<ProposalPrtNo></ProposalPrtNo>
			<!-- 保单号  format类中添加-->
			<ContNo></ContNo>
			<!-- 保单印刷号/保全受理号  format类中添加-->
			<ContPrtNo></ContPrtNo>
			<!-- 冲正类型 -->
			<Type></Type>
			<!-- 续期冲正标志  与核心约定1为续期冲正-->
			<Flag></Flag>
		</Body>
	</xsl:template>

	
</xsl:stylesheet>
