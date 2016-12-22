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
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<!-- 投保单(印刷)号 -->
                <ProposalPrtNo><xsl:value-of select="Ins_BillNo" /></ProposalPrtNo>
				<!-- 保险单号 -->
				<ContNo><xsl:value-of select="InsPolcy_No" /></ContNo> 
				<!--保单密码-->
				<Password></Password> 
				<!-- 投保人姓名 -->
				<Name></Name>  	
				<!-- 投保人证件类型 -->
				<IDType></IDType>		
				<!-- 投保人证件号 -->
				<IDNo></IDNo>			
				<!--保险公司代码-->	
				<CarrierCode></CarrierCode > 
				<!--保险产品代码-->
				<RiskCode></RiskCode> 
				<!--银行交易渠道-->
				<Channel></Channel>
  			</Body>
		</xsl:template>

</xsl:stylesheet>
