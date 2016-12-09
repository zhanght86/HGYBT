<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
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
					<!-- �������� -->
					<TranCom>13</TranCom> 
					<!-- �������� -->
			 <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- ����id 
					<ServiceId>1</ServiceId> -->
					
					<!-- �����������淽ʽ������Ϊ�˲������Լ��������ֶ�-->
					
					<!-- �������� �����������-->
					<!-- ����id �����������-->
					<!-- ���ж�ip[�Ǳ���] �����������-->
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- ������ -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
				<BankCode>0104</BankCode>		
				<!-- Ͷ����(ӡˢ)�� -->
                <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalPrtNo>
				<!-- ���ѽ��ɽ�� -->
				<Prem><xsl:value-of select="Ins_PyF_Amt" /></Prem> 
  			
				<!--ԭ���Ľ�����ˮ��-->
  				<OldTranNo>
					<xsl:value-of select="Ins_Co_Jrnl_No"/>
				</OldTranNo>
				 <!-- ���յ��� -->
				<ContNo></ContNo>

                 <!-- ������ͬӡˢ�� -->
                <ContPrtNo></ContPrtNo>
                <!-- ���ڽɷѷ�ʽ -->
                <BkPayMode>
                <xsl:call-template name="tran_BKPayMode">
						<xsl:with-param name="PayMode">
							<xsl:value-of select="InsPrem_PyMd_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</BkPayMode>
                 <!-- ���ڽɷ��ʺ� -->
                <BkAcctNo><xsl:value-of select="CCB_AccNo" /></BkAcctNo>
  			
  			</Body>
		</xsl:template>

	   <!-- ���ڽɷѷ�ʽ -->
	<xsl:template name="tran_BKPayMode">
		<xsl:param name="PayMode">0</xsl:param>
		<xsl:if test="$PayMode = 1">0</xsl:if><!-- �ֽ� -->
		<xsl:if test="$PayMode = 2">0</xsl:if><!-- �۴��� -->
		<xsl:if test="$PayMode = 3">0</xsl:if><!-- ������ -->
		<xsl:if test="$PayMode = 9">0</xsl:if><!-- �Թ����� -->
	</xsl:template>	

</xsl:stylesheet>