<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TX">
		<!--重打请求-->
		<TranData>
			
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)"/></TranDate>
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
				<ClientIp>127.0.0.1</ClientIp>--> 				
				<!-- 交易渠道 
				<TranCom>03</TranCom> -->		
				<!-- 交易类型 
				<FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag> -->
				<!-- 服务id 
				<ServiceId>3</ServiceId> -->
				<xsl:copy-of select="Head/*"/>
			</Head>

			<Body>
				<!--保单号 -->
				<ContNo><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/InsPolcy_No"/></ContNo>
				<!-- 投保单(印刷)号 -->
				<ProposalPrtNo></ProposalPrtNo>
				<!-- 新保单合同印刷号 -->
				<xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail">
				<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Mod_Af_Ins_IBVoch_ID)"/></ContPrtNo>
				</xsl:for-each>
				<!-- 旧保单合同印刷号-->
				<OldContPrtNo></OldContPrtNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>