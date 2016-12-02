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
					<ServiceId>1</ServiceId>--> 
					
					<!-- 生产上用下面方式，上面为了测试所以加上下面字段-->
					
					<!-- 交易类型 服务类中添加-->
					<!-- 服务id 服务类中添加-->
					<!-- 银行端ip[非必须] 服务类中添加-->
			  </Head>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<!--保险公司代码-->
				<CarrierCode></CarrierCode > 
				<!-- 保险单号 -->
				<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
				<!-- 险种编号 -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
				<!--保单密码-->
				<Password></Password> 
				<!-- 满期给付帐号 -->
				<PayAcc><xsl:value-of select="Exp_Prst_AccNo" /></PayAcc>
				<!--收款帐户户名-->
				<AccName ></AccName > 
				<!--单证名称-->
				<PaperTitle></PaperTitle > 
				<!-- 批单印刷号 -->
				<PrintNo><xsl:value-of select="Btch_Bl_Prt_No" /></PrintNo>
				<!--银行交易渠道-->
				<Channel></Channel>
				<!--委托授权书标志-->
				<EntrustFlag></EntrustFlag > 
  			</Body>
		</xsl:template>
</xsl:stylesheet>