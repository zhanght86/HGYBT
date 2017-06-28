<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TXLife">
		<TranData>
		   <Head>
		   	   <!-- �������� -->
		       <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
		       <!-- ����ʱ�� -->
		       <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
		       <!-- ���д��� -->
		       <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>	
		       <!-- �������� -->
		       <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>
		       <!-- �������� -->
		       <NodeNo><xsl:value-of select="Branch"/></NodeNo>
		       <!-- ��Ա���� -->
		       <TellerNo><xsl:value-of select="Teller"/></TellerNo>
		       <!-- ������ˮ�� -->
		       <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
		       <!-- YBT��֯�Ľڵ���Ϣ -->
			   <xsl:copy-of select="Head/*"/> 
		   </Head>
		   <Body>
		   	   <!-- �������� -->
		   	   <SaleChannel>0</SaleChannel>
		       <!-- ���յ��� -->
		       <ContNo />
		   	   <!-- Ͷ����(ӡˢ)�� -->
		       <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>
		       <!-- ������ͬӡˢ�� -->
		       <ContPrtNo>1<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(ProviderPolicyNumber)"/></ContPrtNo>
		       <OldTranNo></OldTranNo>
			   <BkAcctNo><xsl:value-of select="PayInAcctNo" /></BkAcctNo>
		       <Prem></Prem>
		       <PayMode />
		       <AcctNo />
		       <BkPayMode>
		      		<xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="PayMode" />
						</xsl:with-param>
					</xsl:call-template>
			   </BkPayMode>
		   </Body>
		</TranData>
	</xsl:template>
	
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode"></xsl:param>
		<xsl:if test="$PayMode = 'CS'">0</xsl:if><!-- �ֽ� -->
		<xsl:if test="$PayMode = 'TR'">0</xsl:if><!-- �۴��� -->
	</xsl:template>	

</xsl:stylesheet>