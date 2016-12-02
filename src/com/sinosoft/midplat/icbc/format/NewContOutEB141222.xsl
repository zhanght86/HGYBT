<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	
     <xsl:copy-of select="TranData/Head" /> 
	<TXLifeResponse> 
	<!-- TransRefGUID ������ˮ�� -û��Ҫȡ����ֵ,������ֵ���ò�����   lilu20141127ǰ����ͻ���˭д�ģ�����format�����Ѿ���������-->
	<TransRefGUID/> 
	<TransType tc="103">1013</TransType>
 	<TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	<TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 	
    <xsl:apply-templates select="TranData/Head" />
	<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse> 
</TXLife>
</xsl:template>  

<xsl:template name="OLifE" match="Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>

<!-- ������׳ɹ����ŷ�������Ľ�� -->
<xsl:if test="/TranData/Head/Flag='0'">
<OLifE>   
	<Holding id="Holding_1">
	<xsl:attribute name="id"><xsl:value-of select="concat('Holding_', ContNo)" /></xsl:attribute>
		<!-- ���� -->
		<CurrencyTypeCode>001</CurrencyTypeCode>
		<!-- ������Ϣ --> 
		<Policy carrierPartyId="CAR_PTY_1">    
			<!-- ������ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--���ִ��� -->
			<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
			<!-- �ɷѷ�ʽ -->
			<PaymentMode tc="1">
		    	<!-- �ɷѷ�ʽ Ƶ�� -->  
					<xsl:call-template name="tran_PayIntv">
							<xsl:with-param name="PayIntv"> 
								<xsl:value-of select="PayIntv"/>
							</xsl:with-param>   
						</xsl:call-template> 
		    </PaymentMode> 
			<!-- ������ -->
			<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)" /></EffDate>
			<!-- �б����� -->
			<IssueDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></IssueDate>
			<!-- ������ֹ���� -->
			<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/InsuEndDate)" /></TermDate>
			<!-- �ɷ���ֹ���� -->  
			<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/PayEndDate)" /></FinalPaymentDate>
			<!--���ڱ���-->
			<PaymentAmt><xsl:value-of select="Prem"/></PaymentAmt> <!-- ���ڱ��շѺϼƣ�����PaymentAmtת��Ϊ���ֽ�� ��RMBPaymentAmtԪ�� -->
			<!-- �ɷ���ʽ -->
			<PaymentMethod><xsl:apply-templates select="tran_paymode" /></PaymentMethod>  
			<Life> 
					<FaceAmt>
					<xsl:choose>
					<!-- ����  Ϊ����Ϊ- -->
						<xsl:when test="/TranData/Body/Amnt='0'">-</xsl:when>						 
						<xsl:otherwise><xsl:value-of select="/TranData/Body/Amnt" /></xsl:otherwise>
					</xsl:choose>
					</FaceAmt>
					<InitCovAmt>
					    <xsl:choose>
					     <!-- ����  Ϊ����Ϊ- -->
						<xsl:when test="/TranData/Body/Amnt='0'">-</xsl:when>						 
						<xsl:otherwise><xsl:value-of select="/TranData/Body/Amnt" /></xsl:otherwise>
					    </xsl:choose>
      				</InitCovAmt>
					
					<!-- �潻��־/������־ -->
					<PremOffsetMethod tc="1">
		            <xsl:attribute name="tc"></xsl:attribute>
			             <xsl:call-template name="auto_payflag">
							<xsl:with-param name="AutoPayFlag"> 
								<xsl:value-of select="/TranData/Body/AutoPayFlag"/>
							</xsl:with-param>   
						</xsl:call-template> 
			        </PremOffsetMethod>					
					<!-- ������ȡ��ʽ -->
                    <DivType tc="1">
		            <xsl:attribute name="tc"><xsl:value-of select="Risk/BonusGetMode" /></xsl:attribute>
			             <xsl:value-of select="Risk/BonusGetMode" />                     
                    </DivType>
				    <CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				    <xsl:apply-templates select="Risk" />
				    <OLifEExtension VendorCode="11">
					    <!-- ���ձ����ܶ� -->
					    <BasePremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem"/></BasePremAmt>
						<!-- ���ռӷ��ܶ� -->					
						<BaseExcessPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/Amnt"/></BaseExcessPremAmt>
						<!-- �����ձ����ܶ� -->					
						<RiderPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/Prem"/></RiderPremAmt>
						<!-- �����ռӷ��ܶ� -->						
						<RiderExcessPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/Amnt"/></RiderExcessPremAmt>
						<!-- ���������� -->
						<RiderDec><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/RiskName"/></RiderDec>
					    <!-- �����������б�̧ͷ1 -->
						<RiderListTitle1></RiderListTitle1>
						<!-- �����������б�̧ͷ2 -->
						<RiderListTitle2></RiderListTitle2>
						<!-- �����������б�1 -->
						<RiderList1></RiderList1>
						<!-- �����������б�2 -->
						<RiderList2></RiderList2>
						<!-- �����������б�3 -->
						<RiderList3></RiderList3>
						<!-- �����������б�4 -->
						<RiderList4></RiderList4>
						<!-- �����������б�5 -->
						<RiderList5></RiderList5>
						<!-- ��ȡ���ڱ�־ -->
						<PayOutDateType></PayOutDateType>
						<!-- ��һ����ȡ���� -->
						<FirstPayOutDate></FirstPayOutDate>
						<!-- �ɷѷ�ʽ���� -->
						<PaymentModeDec></PaymentModeDec>
						<RenewalPermit><xsl:value-of select="/TranData/Body/RenewalPermit" /></RenewalPermit>        							
					</OLifEExtension>  	
			</Life> 
			<!--������Ϣ-->
			<ApplicationInfo>
				<!--Ͷ�����-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				<!-- Ͷ������ -->
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate>
			</ApplicationInfo>
			<!-- �ر�Լ�� -->			
			<OLifEExtension VendorCode="2">  
				<!-- �ر�Լ����ӡ��־ -->
				<SpecialClause></SpecialClause>
				<!-- �ر�Լ��  ��ʱΪ��-->  
				<xsl:choose>
				<xsl:when test="Risk/RiskCode=''" >				
				<SpecialClausePrtInd>1</SpecialClausePrtInd>
				  <!-- �ر�Լ�� -����ȡ/TranData/Body/ContNoֵ��Ϊ�˺��������������ƥ��--> 
				<xsl:apply-templates select="/TranData/Body/ContNo" />  
				</xsl:when>				
				<xsl:otherwise>
				<!-- ���ر�Լ�����ر�Լ����־ֵΪ0 -->
			    <SpecialClausePrtInd>0</SpecialClausePrtInd>			  			   
			    </xsl:otherwise>
			    </xsl:choose>						
			    <!-- �ֽ��ֵ��ӡ��־ -->
			    <xsl:choose>
			    <xsl:when test="count(Risk/CashValues/CashValue) = '0'">
			    <CashValueIndicator>0</CashValueIndicator>
			    </xsl:when>
			    <xsl:otherwise>
			    <CashValueIndicator>1</CashValueIndicator>
			    </xsl:otherwise>
				</xsl:choose>
                <!-- �ֽ��ֵ��ɨ������ -->
                <CashValueDecLeft/>
                <!-- �ֽ��ֵ��ɨ������ -->
                <CashValueDecRight/>				
				<!-- ���ڱ��Ѵ�д -->
				<PaymentAmtText><xsl:value-of select="PremText"/></PaymentAmtText>
                <!-- ��ƹ���ѡ�� -->
                <FinFunction/>
                <!-- �������� -->
                <FixedProfitPercent/>
                <!-- �״ζ���׷�ӱ��� -->
                <!-- �״ζ���׷�ӱ���Ϊ'0'�ÿ� -->
	            <FirstSuperaddAmt></FirstSuperaddAmt>
                 <!-- ��˾�绰--> 
                <InsurerDialNumber>4009-800-800</InsurerDialNumber>
                <!-- ��ԥ���˱�˵�� -->
                <xsl:choose>
	                <!-- ����/�����ն� -->
	                <xsl:when test="/TranData/Body/SaleChannel !='0'">
	                   <xsl:choose>
		                   <xsl:when test="/TranData/Body/Risk/RiskCode='231201' or /TranData/Body/Risk/RiskCode='231202' or /TranData/Body/Risk/RiskCode='231203' or /TranData/Body/Risk/RiskCode='211901' or /TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='221201' or /TranData/Body/Risk/RiskCode='231204' or /TranData/Body/Risk/RiskCode='211902' or /TranData/Body/Risk/RiskCode='221301' or /TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='221203' or  /TranData/Body/Risk/RiskCode='225501' " >
		                   <xsl:choose>
			                  <xsl:when test="/TranData/Body/Appnt/Email =''">
			                  <WithdrawalDescription>��ƾ�����������ޣ�������һ�����ڵ�¼���±��չ�˾��ַwww.sinokorealife.com��ѯ�����ص��ӱ�����ͬʱ���ӱ�����������Ͷ��ʱ���ĵ������䡣���ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
			                  </xsl:when>
			                  <xsl:otherwise>
			                  <WithdrawalDescription>���ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
			                  </xsl:otherwise>
		                  </xsl:choose>
		                 </xsl:when>
		                  <xsl:otherwise>
			                   <xsl:choose>
				                  <xsl:when test="/TranData/Body/Appnt/Email =''">
				                  <WithdrawalDescription>���ı�����ԥ��ΪͶ���������15�գ�������ڴ��ڼ��������Լ�����չ�˾��ȫ����Ϣ�˻�����ȡ�ı��շѣ�������ڴ�֮�������Լ�����չ�˾������ͬ����Լ��֧���˱������ڸ��ؼ��������Ҫ�󣬶�����ԥ�ڵļ�������������������Ը��ؼ��Ҫ��Ϊ׼�����ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
				                  </xsl:when>
				                  <xsl:otherwise>
				                  <WithdrawalDescription>��ƾ�����������ޣ�������һ�����ڵ�¼���±��չ�˾��ַwww.sinokorealife.com��ѯ�����ص��ӱ�����ͬʱ���ӱ�����������Ͷ��ʱ���ĵ������䡣���ı�����ԥ��ΪͶ���������15�գ�������ڴ��ڼ��������Լ�����չ�˾��ȫ����Ϣ�˻�����ȡ�ı��շѣ�������ڴ�֮�������Լ�����չ�˾������ͬ����Լ��֧���˱������ڸ��ؼ��������Ҫ�󣬶�����ԥ�ڵļ�������������������Ը��ؼ��Ҫ��Ϊ׼�����ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
				                  </xsl:otherwise>
			                  </xsl:choose>
		                  </xsl:otherwise>
	                  </xsl:choose>
	                </xsl:when>
	                 <xsl:otherwise>
	                <xsl:choose>
	                    <!-- �״ζ���׷�ӱ���Ϊ'0'�ÿ� -->
		                 <xsl:when test="/TranData/Body/Risk/RiskCode='231201' or /TranData/Body/Risk/RiskCode='231202' or /TranData/Body/Risk/RiskCode='231203' or /TranData/Body/Risk/RiskCode='211901' or /TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='221201' or /TranData/Body/Risk/RiskCode='231204' or /TranData/Body/Risk/RiskCode='211902' or /TranData/Body/Risk/RiskCode='221301' or /TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='221203' or  /TranData/Body/Risk/RiskCode='225501' " >
		                <WithdrawalDescription></WithdrawalDescription>
		                </xsl:when>               
		                <xsl:otherwise>
		                	<WithdrawalDescription>������ԥ��Ϊ����ϵͳ�б����ڴ������10�գ�������ڴ��ڼ��������Լ�����չ�˾��ȫ����Ϣ�˻�����ȡ�ı��շѣ�������ڴ�֮�������Լ�����չ�˾������ͬ����Լ��֧���˱������ڸ��ؼ��������Ҫ�󣬶�����ԥ�ڵļ�������������������Ը��ؼ��Ҫ��Ϊ׼��</WithdrawalDescription>
		                </xsl:otherwise>
	                </xsl:choose> 
	                </xsl:otherwise>
                </xsl:choose>
        		             		
			</OLifEExtension>
		</Policy>
		<xsl:if test="count(Risk/Account)!='0'">
		<Investment>
		            <!--Ͷ���ʻ�����-->
                    <SubAccountCount><xsl:value-of select="count(Risk/Account)"/></SubAccountCount>
					<xsl:for-each select="Risk/Account">                   
                    <SubAccount>
                        <!--Ͷ���ʻ�����-->
                        <ProductCode><xsl:value-of select="AccCode"/></ProductCode>
                        <!-- Ͷ���˻����� -->
                        <ProductFullName><xsl:value-of select="AccName"/></ProductFullName>
                        <!-- Ͷ���˻����� -->
                        <AllocPercent><xsl:value-of select="AccRate"/></AllocPercent>
                    </SubAccount>
                    </xsl:for-each> 
        </Investment>
        </xsl:if>
		<OLifEExtension VendorCode="3">  
				<!-- �����־ -->
				<CashEXF>0</CashEXF>
				<!-- Ͷ�����ڱ�־ -->
				<InvestDateInd></InvestDateInd>
		</OLifEExtension>
	</Holding>
    <!-- Ͷ���� -->
    <xsl:apply-templates select="Appnt" />  
    <!-- ������ -->
    <xsl:apply-templates select="Insured" />            
    <!-- ������ -->
    <xsl:apply-templates select="Bnf" />  
	<!-- Ͷ����˾��ϵ ��ѡPrem��Ϊ�˴�����ȡֵʱ��ƥ�䡤����-->
    <xsl:apply-templates select="Prem" /> 
	<!-- Ͷ���˹�ϵ -->
 	<Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Applicant0">
			<xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', ContNo)" /></xsl:attribute>
			<xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
		        <OriginatingObjectType tc="4">4</OriginatingObjectType>
		        <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="80">80</RelationRoleCode>
                <OLifEExtension VendorCode="100">
				    <!-- �ͻ���(Ͷ����) -->
					<CustomerNo></CustomerNo>
				</OLifEExtension>
    </Relation>
    <!-- �����˹�ϵ  -->
    <Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Insured0">
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', ContNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="4">4</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="81">81</RelationRoleCode>
                <OLifEExtension VendorCode="100">
				    <!-- �ͻ���(������) -->
					<CustomerNo></CustomerNo>
				</OLifEExtension>
    </Relation>
    <!-- ��������Ͷ���˹�ϵ -->
    <Relation OriginatingObjectID="ID_Insured0" RelatedObjectID="ID_Applicant0">           
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="6">6</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="8"><xsl:attribute name="tc"><xsl:value-of select="//Appnt/RelaToInsured" /></xsl:attribute><xsl:value-of select="//Appnt/RelaToInsured"/></RelationRoleCode>
    </Relation> 
    <!-- Ͷ�����뱻���˵Ĺ�ϵ -->
    <Relation OriginatingObjectID="ID_Applicant0" RelatedObjectID="ID_Insured0">           
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="6">6</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
               <RelationRoleCode tc="8"><xsl:attribute name="tc"><xsl:value-of select="//Appnt/RelaToInsured" /></xsl:attribute><xsl:value-of select="//Appnt/RelaToInsured"/></RelationRoleCode>
    </Relation>                  
    <!-- �����˹�ϵ -->
    <xsl:for-each select="Bnf">
    <Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Bnf0">
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', /TranData/Body/ContNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Bnf0', IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="4">4</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="82">82</RelationRoleCode>
                <!-- ����ٷ��� -->
                <InterestPercent><xsl:value-of select="Lot"/></InterestPercent>
				<!-- ˳�� -->
				<Sequence><xsl:value-of select="Grade"/></Sequence>				
    </Relation>
    
    <Relation OriginatingObjectID="ID_Bnf0" RelatedObjectID="ID_Insured0">           
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_Bnf0', IDNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Insured0', //Insured/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="6">6</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="8"><xsl:attribute name="tc"><xsl:value-of select="RelaToInsured" /></xsl:attribute><xsl:value-of select="RelaToInsured"/></RelationRoleCode>
    </Relation>
    </xsl:for-each>
    <!-- �������뱻���˹�ϵ -->

    <!-- Ͷ����˾��ϵ -->     
    <Relation OriginatingObjectID="ID_" RelatedObjectID="ID_CAR_PTY_">
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', /TranData/Body/ContNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_CAR_PTY_', string(position()))" /></xsl:attribute>
                <OriginatingObjectType tc="4">4</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="85">85</RelationRoleCode>
    </Relation>
</OLifE>
<OLifEExtension VendorCode="1">
            <TransNo />
			<!-- ���״��� -->
			<PingAnDepNo><xsl:value-of select="/TranData/Body/AgentComName"/></PingAnDepNo>
			<!-- ƽ�����Ŵ��� -->
			<PingAnAgentNo><xsl:value-of select="/TranData/Body/AgentName"/>(<xsl:value-of select="/TranData/Body/AXAAgentCode"/>)</PingAnAgentNo>
			<!-- ƽ��ר��Ա���� -->
			<RcptNo></RcptNo>
			<!-- ƽ����̨����� -->
			<RcptId></RcptId>
			<!-- ƽ��ǰ����ˮ�� -->
			<BasePlanPrintInfo1/>
			<!--��̬��ӡ��Ϣ1 120C-->
			<BasePlanPrintInfo2/>
			<!--��̬��ӡ��Ϣ2 120C-->
			<BasePlanPrintInfo3/>
			<!--��̬��ӡ��Ϣ3 120C-->
			<BasePlanPrintInfo4/>
			<!--��̬��ӡ��Ϣ4 120C-->
			<BasePlanPrintInfo5/>
			<!--��̬��ӡ��Ϣ5 120C-->
			<BasePlanPrintInfo6/>
			<!--��̬��ӡ��Ϣ6 120C-->
			<BasePlanPrintInfo7/>
			<!--��̬��ӡ��Ϣ7 120C-->
			<BasePlanPrintInfo8/>	
			<!--��̬��ӡ��Ϣ8 120C-->	
			<BasePlanPrintInfo9/>	
			<!--��̬��ӡ��Ϣ9 120C-->
			<BasePlanPrintInfo10/>	
			<!--��̬��ӡ��Ϣ10 120C-->
</OLifEExtension>      
</xsl:if>  
<!-- ������׳ɹ����ŷ�������Ľ�� -->
</xsl:template> 

  
<!-- ������Ϣ -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<xsl:variable name="tRisk" select="Risk[RiskCode!=MainRiskCode]" />
<Coverage>
	<xsl:attribute name="id">
		<xsl:choose>
			<!-- �����ձ�־ -->
			<xsl:when test="RiskCode=MainRiskCode">
				<xsl:value-of select="concat('Cov_', '1')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat('Cov_', '2')" />
			</xsl:otherwise>
		</xsl:choose> 		
	</xsl:attribute>
    <!-- �������� -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- ���ִ��� -->
	<ProductCode><xsl:value-of select="TranRiskCode" /></ProductCode>
	<!-- �������� -->
	<LifeCovTypeCode>9</LifeCovTypeCode>
	<xsl:choose>
		<!-- �����ձ�־ -->
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose> 
	<PaymentMode tc="1">
	<xsl:attribute name="tc"><xsl:value-of select="PayIntv" /></xsl:attribute>
		<xsl:value-of select="PayIntv" /> 
	</PaymentMode>
	<!-- Ͷ����� -->   
	<InitCovAmt>   
		<xsl:choose>
			<xsl:when test="Amnt='0'"></xsl:when>
			<xsl:otherwise><xsl:value-of select="Amnt"/></xsl:otherwise>
		</xsl:choose>
	</InitCovAmt> 
	<FaceAmt>
		<xsl:choose>
			<xsl:when test="Amnt='0'"></xsl:when>
			<xsl:otherwise><xsl:value-of select="Amnt" /></xsl:otherwise>
		</xsl:choose>
	</FaceAmt>
	<!-- Ͷ������ -->
	<IntialNumberOfUnits>    
		<xsl:choose>
			<xsl:when test="Mult>0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose> 
	</IntialNumberOfUnits> 	
	<!-- ���ֱ��� -->
	<ModalPremAmt><xsl:value-of select="Prem"/></ModalPremAmt>
	<!-- ������ -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- ��ֹ���� -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- ���ڽɷ����� -->	
	<PaymentDueDate></PaymentDueDate>	 
	<!-- �ɷ���ֹ���� -->
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	<BenefitPeriod tc="1" />   
	<!-- ��ȡ��ʽ -->
	<BenefitMode tc="1" >
	<xsl:attribute name="tc"><xsl:value-of select="../GetIntv" /></xsl:attribute>
		<xsl:value-of select="../GetIntv" /> 
	</BenefitMode>
	<!-- �ֽ��ֵ�� -->							
	<OLifEExtension VendorCode="10">
	       <!-- �ֽ��ֵ��    -->  
		   <CashValues>
				<!-- �����˱����� -->
				<SurrenderPctFY />
				<!-- -�ڶ����˱����� -->
				<SurrenderPctSY />
				<!-- �ֽ��ֵѭ������ -->
				<CashValueCount><xsl:value-of select="count(CashValues/CashValue)"/></CashValueCount>
				<xsl:for-each select="CashValues/CashValue">	
					 <CashValue>
							<!-- ������� -->
							<Live></Live>
							<!--������ʱ��ս� -->
							<IllDeathBenefitAmt />
							<!--������ʱ��ս� -->
							<AciDeathBenefitAmt />
							<!-- ��ĩ -->
							<End><xsl:value-of select="EndYear"/></End>
							<!-- ��ĩ�ֽ��ֵ -->
							<Cash><xsl:value-of select="Cash"/></Cash>
							<!-- ������� -->
							<Year />
							<!-- ���屣�� -->
							<PaidAmt />
						    <!-- �ֽ��ֵ��˵�� -->
							<CashValueDescription></CashValueDescription>
					   </CashValue>
				 </xsl:for-each>
			</CashValues>
			<!-- ������������ĩ�ֽ��ֵ�� -->
			<BonusValues>   
						<!-- �ֽ��ֵ��Ŀ -->			
						<BonusValueCount><xsl:value-of select="count(DeductionValues/DeductionValue)"/></BonusValueCount>
							<xsl:for-each select="DeductionValues/DeductionValue">
								<BonusValue>
										<!-- ��ĩ -->
										<End><xsl:value-of select="EndYear"/></End>
										<!-- ��ĩ�ֽ��ֵ -->
										<Cash><xsl:value-of select="EndYearAmnt"/></Cash>
								</BonusValue>
							</xsl:for-each>
			</BonusValues>
			<!-- ���ּӷ� -->
			<ExcessPremAmt/>  						
			<xsl:choose>
				<xsl:when test="PayEndYear=1000"><!-- ������1000 Y -->
					<!-- �ɷ�����/��������--> 
					<PaymentDurationMode>5</PaymentDurationMode>
					<!-- �ɷ�����/����--> 
					<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>
				</xsl:when>
				<xsl:otherwise>
					<!-- �ɷ�����/��������--> 
					<PaymentDurationMode><xsl:apply-templates select="PayEndYearFlag" /></PaymentDurationMode>
					<!-- �ɷ�����/����--> 
					<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>	
				</xsl:otherwise>
			</xsl:choose>
			<!-- ��ȡ��ʼ���� -->	   
			<PayoutStart/>
			<!-- ��ȡ��ֹ���� -->				
			<PayoutEnd/>				
		    <!-- ��������/�����־ -->
			<DurationMode> <xsl:apply-templates select="InsuYearFlag" /></DurationMode><!-- ����Ҫȷ��һ�� -->
			<!-- ��������/���� -->
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage>

</xsl:template> 

  <!-- Ͷ���� -->
        <xsl:template name="Appnt" match="Appnt">      
            <Party id="ID_Applicant0">
                <xsl:attribute name="id"><xsl:value-of select="concat('ID_Applicant0', IDNo)" /></xsl:attribute>
                <PartyKey>1</PartyKey>
                <!-- Ͷ�������� -->
                <FullName><xsl:value-of select="Name"/></FullName>
                <!-- Ͷ����֤������ -->
                <GovtID><xsl:value-of select="IDNo"/></GovtID>
                <!-- Ͷ����֤������ -->
                <GovtIDTC tc="0">
                <xsl:attribute name="tc"><xsl:value-of select="IDType" /></xsl:attribute>
                <xsl:value-of select="IDType"/>
                </GovtIDTC>
                <Person>
                    <!-- Ͷ�����Ա� -->
                    <Gender tc="1">
                    <xsl:attribute name="tc"><xsl:value-of select="Sex" /></xsl:attribute>
                    <xsl:apply-templates select="Sex "/>
                    </Gender>
                    <!-- Ͷ���˳������� -->
                    <BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/></BirthDate>
                    <!-- Ͷ����ְҵ���� -->
                    <OccupationType tc="1">
                    <xsl:attribute name="tc"><xsl:value-of select="JobType" /></xsl:attribute>
                    <xsl:value-of select="JobCode"/>
                    </OccupationType>
                    <!-- Ͷ����������루��λ����Ԫ�� -->
                    <EstSalary></EstSalary>
                    <!-- Ͷ���˹��� -->
                    <Nationality><xsl:value-of select="Nationality"/></Nationality>	
                </Person>
                <Address id="Address_1">
                	<!-- ��ʱ�˵�ַ��� -->
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <!-- Ͷ�����ʼĵ�ַ -->
                    <Line1><xsl:value-of select="Address"/></Line1>
                    <!-- Ͷ�����ʼ��ʱ�  -->
                    <Zip><xsl:value-of select="ZipCode"/></Zip>                   
                </Address>
                <Address id="Address_2">
                	<!-- ��ʱ�˵�ַ��� -->
                    <AddressTypeCode tc="2"></AddressTypeCode>
                    <!-- Ͷ�����ʼĵ�ַ -->
                    <Line1></Line1>
                    <!-- Ͷ�����ʼ��ʱ�  -->
                    <Zip></Zip>                   
                </Address>
                <Phone id="Phone_1">
					<!-- �绰������� -->                
                    <PhoneTypeCode tc="1">1</PhoneTypeCode>
                    <!-- Ͷ���˰칫�绰 -->
                    <DialNumber><xsl:value-of select="Phone"/></DialNumber>
                </Phone>
                <Phone id="Phone_2">
                    <!-- Ͷ����סլ�绰 (Ͷ������ϵ�绰) -->
                    <PhoneTypeCode tc="3">3</PhoneTypeCode>
                    <DialNumber><xsl:value-of select="Mobile"/></DialNumber>
                </Phone>
                <Phone id="Phone_3">
                    <!-- Ͷ����סլ�绰 (Ͷ������ϵ�绰) -->
                    <PhoneTypeCode tc="3"></PhoneTypeCode>
                    <DialNumber></DialNumber>
                </Phone>
                <EMailAddress id="EMailAddress_1">
					<!-- Ͷ���˵����ʼ���ַ  -->
					<AddrLine><xsl:value-of select="Email"/></AddrLine>					
				</EMailAddress>
				<!-- ��֪�б�  -->
                <OLifEExtension VendorCode="200">
                	<!-- ��֪�б� -->				
					<TellInfos>
						<!-- ��֪��Ŀ�� -->
						<TellInfoCount />						
						<TellInfo>
						    <!-- ��֪��� -->
							<TellVersion></TellVersion>
							<!-- ��֪���� -->
							<TellCode></TellCode>
							<!-- ��֪���� -->
							<TellContent></TellContent>
							<!-- ��֪��ע -->
							<TellRemark />							
						</TellInfo>
					</TellInfos>
                </OLifEExtension>
            </Party>
       </xsl:template>
		
	<!-- ������ -->
     <xsl:template name="Insured" match="Insured">
            <Party id="ID_Insured0">
                <xsl:attribute name="id"><xsl:value-of select="concat('ID_Insured0', IDNo)" /></xsl:attribute>
                <PartyKey>2</PartyKey>
                <FullName><xsl:value-of select="Name"/></FullName>
                <GovtID><xsl:value-of select="IDNo"/></GovtID>
                <GovtIDTC tc="0">
                <xsl:attribute name="tc"><xsl:value-of select="IDType" /></xsl:attribute>
                <xsl:value-of select="IDType"/>                 
                </GovtIDTC>                
                <Person>
                    <Gender tc="1">
                    <xsl:attribute name="tc"><xsl:value-of select="Sex" /></xsl:attribute>
                    <xsl:apply-templates select="Sex "/>
                    </Gender>
                    <BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/></BirthDate>
                    <!-- ������ְҵ���� -->
                    <OccupationType tc="">
                    <xsl:attribute name="tc"><xsl:value-of select="JobType" /></xsl:attribute>
                    <xsl:value-of select="JobCode"/>
                    </OccupationType>
                    <!-- Ͷ����������루��λ����Ԫ�� -->
                    <EstSalary></EstSalary>
                    <!-- Ͷ���˹��� -->
                    <Nationality><xsl:value-of select="Nationality"/></Nationality>
                </Person>
                <Address id="Address_3">
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <Line1><xsl:value-of select="Address"/></Line1>
                    <Zip><xsl:value-of select="ZipCode"/></Zip>                    
                </Address>
                <Address id="Address_7">
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <Line1><xsl:value-of select="Address"/></Line1>
                    <Zip><xsl:value-of select="ZipCode"/></Zip>                    
                </Address>
                <Phone id="Phone_4">
                    <PhoneTypeCode tc="1">1</PhoneTypeCode>
                    <DialNumber><xsl:value-of select="Phone"/></DialNumber>
                </Phone>
                <Phone id="Phone_5"> 
                    <PhoneTypeCode tc="3">3</PhoneTypeCode>
                    <DialNumber><xsl:value-of select="Mobile"/></DialNumber>
                </Phone>
                <!-- ��֪�����Ϣ -->
                <EMailAddress id="EMailAddress_2">
					<!-- Ͷ���˵����ʼ���ַ  -->
					<AddrLine><xsl:value-of select="Email"/></AddrLine>					
				</EMailAddress>
				<!-- ��֪�б�  -->
                <OLifEExtension VendorCode="200">
                	<!-- ��֪�б� -->				
					<TellInfos>
						<!-- ��֪��Ŀ�� -->
						<TellInfoCount />						
						<TellInfo>
						    <!-- ��֪��� -->
							<TellVersion></TellVersion>
							<!-- ��֪���� -->
							<TellCode></TellCode>
							<!-- ��֪���� -->
							<TellContent></TellContent>
							<!-- ��֪��ע -->
							<TellRemark />							
						</TellInfo>
					</TellInfos>
                </OLifEExtension>
         </Party>
	</xsl:template>
	
	<!-- ������ -->
     <xsl:template name="Bnf" match="Bnf">
            <Party id="ID_Bnf0">
            <xsl:attribute name="id"><xsl:value-of select="concat('ID_Bnf0', IDNo)" /></xsl:attribute>
                <PartyKey>3</PartyKey>
                <FullName><xsl:value-of select="Name"/></FullName>
                <GovtID><xsl:value-of select="IDNo"/></GovtID>
                <GovtIDTC tc="0">
                   <xsl:attribute name="tc"><xsl:value-of select="IDType" /></xsl:attribute>
                   <xsl:value-of select="IDType"/>                 
                </GovtIDTC>
                <Person>
                    <Gender tc="1">
                        <xsl:attribute name="tc"><xsl:value-of select="Sex" /></xsl:attribute>
                        <xsl:apply-templates select="Sex "/>
                    </Gender>
                    <BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/></BirthDate>
                    <!-- Ͷ���˹��� -->
                    <Nationality><xsl:value-of select="Nationality"/></Nationality>
                </Person>
                <!-- �����˵�������Ϣû��¼��ϵͳ������˵����û�д����������Զ��ǿ�ֵ -->
                <Address id="Address_4">
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <Line1/>
                </Address>
           </Party>
	</xsl:template>
	
	<!-- Ͷ����˾��ϵ -->
 	<xsl:template name="Policy" match="Prem">
            <Party id="ID_CAR_PTY_">
            <xsl:attribute name="id"><xsl:value-of select="concat('ID_CAR_PTY_', string(position()))" /></xsl:attribute>
                <FullName>�к����ٱ������޹�˾</FullName>
                <GovtID></GovtID>
                <Organization/>
                <Address>
                    <Line1>�й��㽭ʡ�����н�������ҵ·8��UDCʱ������A��24��</Line1>
                    <Line2/>
                    <City>����</City>
                    <AddressStateTC/>
                    <Zip>310016</Zip>
                </Address>
                <Phone>
                <!-- �����˵绰��� -->
                    <PhoneTypeCode tc="2">2</PhoneTypeCode>
                    <AreaCode/>
                    <DialNumber>4009-800-800</DialNumber>
                </Phone>
                <Carrier>
					<!-- ��˾���� -->
					<CarrierCode>050</CarrierCode>
				</Carrier>
            </Party>
	</xsl:template>
	
	<!--���ֵ��ر�Լ����Ϣ -->
	<xsl:template name="SpecContent" match="ContNo">
	<SpecialClause>	
	<!-- ֻ������ʱ -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode=''">(1)������ʱ���=100%�����˻���ֵ+10%�����˻���ֵ(10%�����˻���ֵ����������100��Ԫ)��(2) ��������ʱ���=100%�����˻���ֵ+5%�����˻���ֵ(5%�����˻���ֵ����������100��Ԫ)��</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode=''">�����յı��ս��������˻���ֵ��105%���㡣</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
		</SpecialClause>
	</xsl:template>
	
	<!-- ��̬��ӡ��Ϣ -->
	<xsl:template name="BasePlanPrintInfo" match="ProposalPrtNo">
	<!-- ֻ������ -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='' ">
				<BasePlanPrintInfo1>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo1>
	            <BasePlanPrintInfo2>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo2>
	            <BasePlanPrintInfo3>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo3>
	            <BasePlanPrintInfo4>����˾���紹ѯ��</BasePlanPrintInfo4>
	            <BasePlanPrintInfo5>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo5>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='' ">
				<BasePlanPrintInfo1>�����յı��ս��������˻���ֵ��105%���㡣</BasePlanPrintInfo1>
				<BasePlanPrintInfo2>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo2>
				<BasePlanPrintInfo3>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo3>
				<BasePlanPrintInfo4>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo4>
				<BasePlanPrintInfo5>����˾���紹ѯ��</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo6>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231303'">
				<BasePlanPrintInfo1>(1)������ʱ���=100%�����˻���ֵ+10%�����˻���ֵ(10%�����˻���ֵ����������100��Ԫ)��(2) ��������ʱ���=100%�����˻���ֵ+5%�����˻���ֵ(5%�����˻���ֵ����������100��Ԫ)��</BasePlanPrintInfo1>
			</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:if>

	</xsl:template>
<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".='M'">1</xsl:when>	<!-- �� -->
	<xsl:when test=".='F'">2</xsl:when>	<!-- Ů -->
	<xsl:otherwise>3</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- ���ִ��� -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">001</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">002</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">003</xsl:when>	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=221201">006</xsl:when>	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">007</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=221301">009</xsl:when>    <!-- �к���δ�������-->
	<xsl:when test=".=231302">010</xsl:when>    <!-- �к�������������գ��ֺ��ͣ�-->
	<xsl:when test=".=221203">011</xsl:when> 	<!-- �к���������ȫ����-->
	<xsl:when test=".=225501">012</xsl:when> 	<!-- �к������������ش󼲲�����-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


   <!-- ���ؽɷѷ�ʽ -->
<xsl:template name="tran_PayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =12">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =6">3</xsl:when>
		<xsl:when test="PayIntv =3">4</xsl:when>
		<xsl:when test="PayIntv =0">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template> 

<!-- �Զ��潻/������־ -->

<xsl:template name="auto_payflag">
    <xsl:param name="AutoPayFlag"></xsl:param> 
	<xsl:choose>
		<xsl:when test="AutoPayFlag=0">N</xsl:when>	<!-- ���潻 -->
		<xsl:when test="AutoPayFlag=1">Y</xsl:when>	<!-- �潻 -->
	</xsl:choose>  
 </xsl:template> 

<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=1">�꽻</xsl:when>	<!-- ��� -->
	<xsl:when test=".=2">�½�</xsl:when>	<!-- �½� -->
	<xsl:when test=".=3">���꽻</xsl:when>	<!-- ����� -->
	<xsl:when test=".=4">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=5">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=6">�����ڽ�</xsl:when>	<!-- ������ -->
	<xsl:when test=".=9">����</xsl:when>	<!-- ����-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

<xsl:template  match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������-->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �꽻 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �½� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �ս� -->
															<!-- ����  PayEndYear 1000,PayEndYearFlag Y-->
															<!-- �ս��� -->
															<!-- �����ڽ� -->
															<!-- ���� -->
															<!-- ����-->
</xsl:choose>
</xsl:template>  

<xsl:template  match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �걣 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �±� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �ձ� -->
	<xsl:when test=".='A'">5</xsl:when>	<!-- ������ -->
	<xsl:when test=".='9'">9</xsl:when>	<!-- ����-->
</xsl:choose>
</xsl:template>  
 
 	<xsl:template name="RetData" match="TranData/Head">
		<TransResult>
			<xsl:if test="Flag='0'">
				<ResultCode tc="1">0000</ResultCode>
				<ResultInfo>
					<ResultInfoDesc>���׳ɹ�</ResultInfoDesc>
				</ResultInfo>
			</xsl:if>
		</TransResult>
	</xsl:template>
	
	 
<!-- �ɷ���ʽ ����Ϲ�ͨ��������ͨ�������к���߽ɷ���ʽ����ΪB-->
<xsl:template name="tran_paymode" match="PayMode">
<xsl:choose>
	<xsl:when test=".='B'">1</xsl:when>	<!-- ����ת�� -->
	<xsl:when test=".='B'">3</xsl:when>	<!-- ���д��� -->
</xsl:choose>
</xsl:template>	
	
 <!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'">    <!--  ��δ��������ּۿ��ܳ�100 -->
          <xsl:variable name="LeiShu" select="25"></xsl:variable>	
          <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<TextRowContent><xsl:text>  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></TextRowContent>
		</xsl:for-each>
        </xsl:if>
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode= '221301' ">     <!--  ��δ��������ּۿ��ܳ�100 -->
            <xsl:variable name="LeiShu" select="33"></xsl:variable>	
		    <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<TextRowContent><xsl:text>  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></TextRowContent>
		</xsl:for-each>
        </xsl:if>
</xsl:template>	
	
</xsl:stylesheet>


