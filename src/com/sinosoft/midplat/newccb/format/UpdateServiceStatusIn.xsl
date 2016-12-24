<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
			<TranData>
				<BaseInfo>
					<!--���б�� -->
					<BankCode>003</BankCode>
				    <!--�������� -->
					<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
					<!--����ʱ�� -->
					<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
					<!-- һ�����к�(�������) -->
					<ZoneNo></ZoneNo>
					<!-- ���տͻ������ṩ�������(��֧���) -->
					<BrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BrNo>
					<!--��Ա�� -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- ������ˮ�� -->
					<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
				    <!--�������� -->
					<FunctionFlag>26</FunctionFlag>
					<!--���չ�˾��� -->
					<InsuID><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/Ins_Co_ID" /></InsuID>
				    <xsl:copy-of select="Head/*"/>
			  </BaseInfo>
			  <!-- ������ -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
		<LCConts>
			<!--�������� -->
			<LCContCount><xsl:value-of select="Rvl_Rcrd_Num" /></LCContCount>
			<xsl:choose>
				<xsl:when test="Rvl_Rcrd_Num ='0'">
				</xsl:when>
				<xsl:when test="Rvl_Rcrd_Num !='0'">
					<!-- ����ѭ���ڵ� -->
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
