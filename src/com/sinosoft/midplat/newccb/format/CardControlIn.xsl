<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TX">
		<!--�ؿպ˶�-->
		<TranData>
				<Head>
				     <!--�������� -->
					<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
					<!--����ʱ�� -->
					<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
					<!-- �������� -->
					<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
					<!-- ���б��� -->
					<BankCode>0104</BankCode>
					<!--��Ա�� -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- ������ˮ�� -->
					<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
					<!-- ���ж�ip[�Ǳ���] 
					<ClientIp>127.0.0.1</ClientIp> -->
					<!-- �������� 
					<TranCom>03</TranCom> -->
					<!-- �������� -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- ����id 
					<ServiceId>0</ServiceId> -->
				<xsl:copy-of select="Head/*"/>
				</Head>

			<Body>
				<!-- �����ؿ����ͱ��� 
				<CardType>
					<xsl:call-template name="tran_type">
						<xsl:with-param name="Type">
							<xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Tp_ECD" />
						</xsl:with-param>
					</xsl:call-template>
				</CardType>-->
				<!-- �����ؿ����ͱ��� -->
				<CardType>
							<xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Tp_ECD" />
				</CardType>
				<!-- �����ؿ���ʼ���� -->
				<StartNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_Beg_ID)"/></StartNo>
				<!-- �����ؿս������� -->
				<EndNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(TX_BODY/ENTITY/APP_ENTITY/Ins_IBVoch_End_ID)"/></EndNo>
			</Body>
		</TranData>
	</xsl:template>
	
	<!-- �����ؿ����ͱ��� -->
	<xsl:template name="tran_type" >
		<xsl:param name="Type"></xsl:param>
		<xsl:if test="$Type = '010072000001'">2104141</xsl:if><!-- Ͷ������ -->
		<xsl:if test="$Type = '010072000002'">0101141</xsl:if><!-- ����ӡˢ�� -->
	</xsl:template>
	
</xsl:stylesheet>
