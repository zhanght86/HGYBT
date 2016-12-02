<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<TXLife>
	<xsl:copy-of select="TranData/Head"/>
	<TXLifeResponse>
	<!-- ������ˮ�� -->
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
	<!-- ������/�����־ -->
	<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
 	 <TransResult>
			<ResultCode><xsl:value-of select="TranData/Head/Flag"/></ResultCode>
			<ResultInfo>
				<ResultInfoDesc><xsl:value-of select="TranData/Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
		<xsl:apply-templates select="TranData/Body"/>
	</TXLifeResponse>    
</TXLife> 
</xsl:template>   
<xsl:template match="Body">
	<OLifE>
		<Holding>
			<Policy>
				<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
				<Life>
						<!--  ������Ŀ   --> 
  					<CoverageCount>1</CoverageCount> 
						<!-- Optional repeating -->
						<Coverage>
							<!-- ѭ���ڵ�,����ʱ��Ӧ�����˳��������� -->
							<PlanName><xsl:value-of select="PlanName"/></PlanName>
							<!-- �������� -->
							<ProductCode><xsl:value-of select="RiskCode"/></ProductCode>
							<!-- ���ִ��� -->
							<OLifEExtension VendorCode="10">
								<!-- Ӧ�����,����ʱ��˳����� -->
								<PaymentOrder>1</PaymentOrder>
								<!-- �ɷѽ�� -->
								<NextPayAmt><xsl:value-of select="Prem"/></NextPayAmt>
								<!-- Ӧ������-->
								<PaymentDate><xsl:value-of select="RecvDate"/></PaymentDate>
								<!-- Ӧ����¼״̬-->
								<PaymentState><xsl:value-of select="PaymentState"/></PaymentState>
								<!-- ��ʾ��Ϣ-->
								<Remark><xsl:value-of select="Remark"/></Remark>			
							</OLifEExtension>
						</Coverage>
					</Life>	
				</Policy>
				<FinancialActivity>
					<FinActivityGrossAmt><xsl:value-of select="RecvAmount"/></FinActivityGrossAmt>
					<!--Ӧ�ս��-->
					<FinEffDate><xsl:value-of select="RecvDate"/></FinEffDate> 
					<!-- Ӧ�����ڣ����٣�  --> 
					<OLifEExtension VendorCode="9">
						<!--  �ɷ�����   --> 
  					<PaymentYears><xsl:value-of select="PayEndYear"/></PaymentYears> 
						<!-- �շ���Ŀ-->
						<PayItm><xsl:value-of select="PayItm"/></PayItm>					
						<!-- Ӧ������ -->
						<PaymentTimes><xsl:value-of select="RecvNum"/></PaymentTimes>
						<!-- �ѽ����� -->
						<PayedTimes><xsl:value-of select="PayedTimes"/> </PayedTimes>
						<PaymentStartDate><xsl:value-of select="PayStartDate"/></PaymentStartDate>
						<!-- �ɷ���ʼ���� -->
						<PaymentEndDate><xsl:value-of select="PayEndDate"/></PaymentEndDate>
						<!-- �ɷ���ֹ���� -->
						<ACCCODE/>
						<!-- for�й����٣��˻����� -->
					</OLifEExtension>
				</FinancialActivity>
		</Holding>
		<Party id="Party_1">
				<FullName><xsl:value-of select="Name"/></FullName>
				<!-- Ͷ�������� -->
				<GovtID><xsl:value-of select="IDNo"/></GovtID>
				<!-- Ͷ����֤������ -->
				<GovtIDTC tc="1"><xsl:value-of select="IDType"/></GovtIDTC>
				<!-- Ͷ����֤������ -->				
			</Party>
			<Relation OriginatingObjectID="Holding_1" RelatedObjectID="Party_1" id="Relation_1">
				<OriginatingObjectType tc="4">Holding</OriginatingObjectType>
				<RelatedObjectType tc="6">Party</RelatedObjectType>
				<RelationRoleCode tc="8">Owner</RelationRoleCode>
				<!-- Ͷ���˹�ϵ -->
			</Relation>
	</OLifE>
</xsl:template>
</xsl:stylesheet>
