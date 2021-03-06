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
				<!-- 总比数 -->
				<Count><xsl:value-of select="Rcncl_Dtl_TDnum" /></Count>
				<xsl:for-each select="Detail_List/Detail">
				      <Detail>
				      	 <!--交易日期 -->	
				         <TranDate><xsl:value-of select="Txn_Dt" /></TranDate>
				      	 <!--机构编码 -->	
				         <AgentCom><xsl:value-of select="CCBIns_ID" /></AgentCom>
				      	 <!--柜员代码 -->	
				         <TellerNo><xsl:value-of select="//TX/TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				      	 <!--交易流水号 -->	
				         <TranNo><xsl:value-of select="Ins_Co_Jrnl_No" /></TranNo>
				      	 <!--保单号 -->	
				         <ContNo><xsl:value-of select="InsPolcy_No" /></ContNo>
				      	 <!-- 保费 -->
				         <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(TxnAmt)" /></Prem>
				      	 <!-- 投保人姓名 -->	
				         <AppntName></AppntName>
				      	 <!--投保单号 -->	
				         <ProposalPrtNo></ProposalPrtNo>
				      </Detail>
			      </xsl:for-each>
  			</Body>
		</xsl:template>

</xsl:stylesheet>
