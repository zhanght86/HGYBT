<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TX">
		<!--�ش�����-->
		<TranData>
			
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)"/></TranDate>
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
				<ClientIp>127.0.0.1</ClientIp>--> 				
				<!-- �������� 
				<TranCom>03</TranCom> -->		
				<!-- �������� 
				<FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag> -->
				<!-- ����id 
				<ServiceId>3</ServiceId> -->
				<xsl:copy-of select="Head/*"/>
			</Head>

			<Body>
				<!--������ -->
				<ContNo><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/InsPolcy_No"/></ContNo>
				<!-- Ͷ����(ӡˢ)�� -->
				<ProposalPrtNo></ProposalPrtNo>
				<!-- �±�����ͬӡˢ�� -->
				<xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail">
				<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Mod_Af_Ins_IBVoch_ID)"/></ContPrtNo>
				</xsl:for-each>
				<!-- �ɱ�����ͬӡˢ��-->
				<OldContPrtNo></OldContPrtNo>
			</Body>
		</TranData>
	</xsl:template>
</xsl:stylesheet>