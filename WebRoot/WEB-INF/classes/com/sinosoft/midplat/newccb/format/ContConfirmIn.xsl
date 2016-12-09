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
					<!-- 交易渠道 -->
					<TranCom>13</TranCom> 
					<!-- 交易类型 -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- 服务id 
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
				<BankCode>0104</BankCode>		
				<!-- 投保单(印刷)号 -->
                <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalPrtNo>
				<!-- 保费缴纳金额 -->
				<Prem><xsl:value-of select="Ins_PyF_Amt" /></Prem> 
  			
				<!--原来的交易流水号-->
  				<OldTranNo>
					<xsl:value-of select="Ins_Co_Jrnl_No"/>
				</OldTranNo>
				 <!-- 保险单号 -->
				<ContNo></ContNo>

                 <!-- 保单合同印刷号 -->
                <ContPrtNo></ContPrtNo>
                <!-- 首期缴费方式 -->
                <BkPayMode>
                <xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="InsPrem_PyMd_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</BkPayMode>
                 <!-- 首期缴费帐号 -->
                <BkAcctNo><xsl:value-of select="CCB_AccNo" /></BkAcctNo>
  			
  			</Body>
		</xsl:template>

	   <!-- 首期缴费方式 -->
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode">0</xsl:param>
		<xsl:if test="$PayMode = 1">0</xsl:if><!-- 现金 -->
		<xsl:if test="$PayMode = 2">0</xsl:if><!-- 折代扣 -->
		<xsl:if test="$PayMode = 3">0</xsl:if><!-- 卡代扣 -->
		<xsl:if test="$PayMode = 9">0</xsl:if><!-- 对公代扣 -->
	</xsl:template>	

</xsl:stylesheet>