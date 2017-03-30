<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
			<TranData>
				<Head>
					<ServiceId>64</ServiceId>
					<TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
				</Head>
				<BaseInfo>
					<!--银行编号 -->
					<BankCode>0104</BankCode>
				    <!--交易日期 -->
					<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
					<!--交易时间 -->
					<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
					<!-- 一级分行号(地区编号) -->
					<ZoneNo>1</ZoneNo>
					<!-- 保险客户名单提供地区编号(分支编号) -->
					<BrNo>1</BrNo>
					<!--柜员号 -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- 交易流水号 -->
					<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
				    <!--交易类型 -->
					<FunctionFlag>26</FunctionFlag>
					<!--保险公司编号 -->
					<InsuID><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/Ins_Co_ID" /></InsuID>
				    <xsl:copy-of select="Head/*"/>
			  </BaseInfo>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
		<LCConts>
			<!--保单个数 -->
			<LCContCount><xsl:value-of select="Rvl_Rcrd_Num" /></LCContCount>
			<xsl:choose>
				<xsl:when test="Rvl_Rcrd_Num ='0'">
				</xsl:when>
				<xsl:when test="Rvl_Rcrd_Num !='0'">
					<!-- 保单循环节点 -->
					<xsl:for-each select="Insu_List/Insu_Detail">
						<LCCont>
							<ProposalContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalContNo>
						</LCCont>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
 			</LCConts>
	</xsl:template>

</xsl:stylesheet> 
