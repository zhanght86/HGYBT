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
				<xsl:for-each select="Detail_List/Detail">
				      <Detail>
				         <!-- 业务类型05投连险转换，06投连险部分领取，07犹豫期撤保，09 满期给付，10退保，11续期-->
				         <BusiType></BusiType> 
				         <!-- 交易流水号-->
						 <TranNo><xsl:value-of select="Ins_Co_Jrnl_No" /></TranNo>
						 <!-- 网点编码--> 
						 <NodeNo><xsl:value-of select="//TX/TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
						 <!-- 保单号 --> 
						 <ContNo><xsl:value-of select="InsPolcy_No" /></ContNo>
						 <!-- 批单号 -->
						 <EdorNo></EdorNo >
						 <!-- 领取账户-->
						 <AccNo></AccNo>
						 <!-- 账户姓名-->
						 <AccName></AccName>
						 <!-- 交易日期--> 
						 <TranDate><xsl:value-of select="Txn_Dt" /></TranDate> 
				      </Detail>
			      </xsl:for-each>
  			</Body>
		</xsl:template>

</xsl:stylesheet>
