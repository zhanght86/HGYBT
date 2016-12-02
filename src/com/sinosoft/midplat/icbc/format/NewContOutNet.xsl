<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	
     <xsl:copy-of select="TranData/Head" /> 
	<TXLifeResponse> 
	<!-- TransRefGUID ������ˮ�� -û��Ҫȡ����ֵ,������ֵ���ò�����-->
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
	<Holding id="Holding_">
	<xsl:attribute name="id"><xsl:value-of select="concat('Holding_', ContNo)" /></xsl:attribute>
		<!-- ���� -->
		<CurrencyTypeCode>1</CurrencyTypeCode>
		<!-- ������Ϣ --> 
		<Policy carrierPartyId="CAR_PTY_1">    
			<!-- ������ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--���ִ��� -->
			<ProductCode><xsl:value-of select="Risk[RiskCode=MainRiskCode]/TranRiskCode" /></ProductCode>
			<!-- �ɷѷ�ʽ -->
			<PaymentMode tc="1">
		    <xsl:attribute name="tc"><xsl:value-of select="Risk/PayIntv" /></xsl:attribute>
			   <xsl:value-of select="Risk/PayIntv" /> 
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
			<PaymentMethod></PaymentMethod>  
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
		            <xsl:attribute name="tc"><xsl:value-of select="/TranData/Body/AutoPayFlag" /></xsl:attribute>
			             <xsl:value-of select="/TranData/Body/AutoPayFlag" /> 
			        </PremOffsetMethod>					
					<!-- ������ȡ��ʽ -->
                    <DivType tc="1">
		            <xsl:attribute name="tc"><xsl:value-of select="Risk/BonusGetMode" /></xsl:attribute>
			             <xsl:value-of select="Risk/BonusGetMode" />                     
                    </DivType>
				    <CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				    <xsl:apply-templates select="Risk" />  	
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
				<!-- �ر�Լ��  -->  
				<xsl:choose>
				<xsl:when test="Risk/RiskCode='GHONP' or Risk/RiskCode = 'EDU' or Risk/RiskCode ='NHY' or Risk/RiskCode ='HYG3'" >				
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
                <xsl:choose>
                    <!-- �״ζ���׷�ӱ���Ϊ'0'�ÿ� -->
	                <xsl:when test="/TranData/Body/FirstAddPrem ='0'">
	                </xsl:when>               
	                <xsl:otherwise>
	                	<FirstSuperaddAmt><xsl:value-of select="/TranData/Body/FirstAddPrem"/></FirstSuperaddAmt>
	                </xsl:otherwise>
                </xsl:choose> 
                 <!-- ��˾�绰--> 
                <InsurerDialNumber>400-670-5566</InsurerDialNumber>
                <!-- ��ԥ���˱�˵�� -->
                <xsl:choose>
                <xsl:when test="/TranData/Body/TransChnl='8'">
                   <xsl:choose>
                   <xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode=''">
                   <xsl:choose>
                  <xsl:when test="/TranData/Body/Appnt/Email =''">
                 <WithdrawalDescription>��ƾ�����������ޣ�������һ�����ڵ�¼���±��չ�˾��ַwww.icbc-axa.com��ѯ�����ص��ӱ�����ͬʱ���ӱ�����������Ͷ��ʱ���ĵ������䡣���ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
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
                  <WithdrawalDescription>��ƾ�����������ޣ�������һ�����ڵ�¼���±��չ�˾��ַwww.icbc-axa.com��ѯ�����ص��ӱ�����ͬʱ���ӱ�����������Ͷ��ʱ���ĵ������䡣���ı�����ԥ��ΪͶ���������15�գ�������ڴ��ڼ��������Լ�����չ�˾��ȫ����Ϣ�˻�����ȡ�ı��շѣ�������ڴ�֮�������Լ�����չ�˾������ͬ����Լ��֧���˱������ڸ��ؼ��������Ҫ�󣬶�����ԥ�ڵļ�������������������Ը��ؼ��Ҫ��Ϊ׼�����ݼ�ܲ���Ҫ�󣬽��������ڹ���������ϵ��ַ����ͻ�Ͷ����Ϣ�������չ�˾��</WithdrawalDescription>
                  </xsl:otherwise>
                  </xsl:choose>
                  </xsl:otherwise>
                  </xsl:choose>
                 </xsl:when>
                 <xsl:otherwise>
                <xsl:choose>
                    <!-- �״ζ���׷�ӱ���Ϊ'0'�ÿ� -->
	                <xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode=''">
	                <WithdrawalDescription></WithdrawalDescription>
	                </xsl:when>               
	                <xsl:otherwise>
	                	<WithdrawalDescription>������ԥ��Ϊ����ϵͳ�б����ڴ������15�գ�������ڴ��ڼ��������Լ�����չ�˾��ȫ����Ϣ�˻�����ȡ�ı��շѣ�������ڴ�֮�������Լ�����չ�˾������ͬ����Լ��֧���˱������ڸ��ؼ��������Ҫ�󣬶�����ԥ�ڵļ�������������������Ը��ؼ��Ҫ��Ϊ׼��</WithdrawalDescription>
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
				<InvestDateInd><xsl:value-of select="/TranData/Body/AccTimeFlag"/></InvestDateInd>
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
<Coverage>
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
			<!-- ������������ĩ�ֽ��ֵ�� -->
			<BonusValues>   
						<!-- �ֽ��ֵ��Ŀ -->			
						<BonusValueCount />
								<BonusValue>
										<!-- ��ĩ -->
										<End />
										<!-- ��ĩ�ֽ��ֵ -->
										<Cash />
								</BonusValue>
			</BonusValues>
			<!-- ���ּӷ� -->
			<ExcessPremAmt/>  						
		    <!-- �ɷ�����/��������--> 
			<PaymentDurationMode> <xsl:value-of select="PayEndYearFlag" /></PaymentDurationMode>
			<!-- �ɷ�����/����--> 
			<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>		
			<!--�D��ȡ��ʼ���� -->	   
			<PayoutStart/>
			<!--�D��ȡ��ֹ���� -->				
			<PayoutEnd/>				
		    <!-- ��������/�����־ -->
			<DurationMode> <xsl:value-of select="InsuYearFlag" /></DurationMode>
			<!-- ��������/���� -->
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage> 
<OLifEExtension VendorCode="11">
    <!-- ���ձ����ܶ� -->
    <BasePremAmt/>
	<!-- ���ռӷ��ܶ� -->					
	<BaseExcessPremAmt />
	<!-- �����ձ����ܶ� -->					
	<RiderPremAmt />
	<!-- �����ռӷ��ܶ� -->						
	<RiderExcessPremAmt />
	<!-- ���������� -->
	<RiderDec />
    <!-- �����������б�̧ͷ1 -->
	<RiderListTitle1 />
	<!-- �����������б�̧ͷ2 -->
	<RiderListTitle2 />
	<!-- �����������б�1 -->
	<RiderList1 />
	<!-- �����������б�2 -->
	<RiderList2 />
	<!-- �����������б�3 -->
	<RiderList3 />
	<!-- �����������б�4 -->
	<RiderList4 />
	<!-- �����������б�5 -->
	<RiderList5 />
	<!-- ��ȡ���ڱ�־ -->
	<PayOutDateType />
	<!-- ��һ����ȡ���� -->
	<FirstPayOutDate></FirstPayOutDate>
	<!-- �ɷѷ�ʽ���� -->
	<PaymentModeDec></PaymentModeDec>
	<RenewalPermit><xsl:value-of select="/TranData/Body/RenewalPermit" /></RenewalPermit>        							
</OLifEExtension>
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
                <FullName>������ʢ���ٱ������޹�˾</FullName>
                <GovtID></GovtID>
                <Organization/>
                <Address>
                    <Line1>�й��Ϻ����ֶ�½���컷·166��δ���ʲ�����19¥</Line1>
                    <Line2/>
                    <City>�Ϻ�</City>
                    <AddressStateTC/>
                    <Zip>200120</Zip>
                </Address>
                <Phone>
                <!-- �����˵绰��� -->
                    <PhoneTypeCode tc="2">2</PhoneTypeCode>
                    <AreaCode/>
                    <DialNumber>400-670-5566</DialNumber>
                </Phone>
                <Carrier>
					<!-- ��˾���� -->
					<CarrierCode>028</CarrierCode>
				</Carrier>
            </Party>
	</xsl:template>
	
	<!--���ֵ��ر�Լ����Ϣ -->
	<xsl:template name="SpecContent" match="ContNo">
	<SpecialClause>	
	<!-- ֻ������ʱ -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='GHONP'">(1)������ʱ���=100%�����˻���ֵ+10%�����˻���ֵ(10%�����˻���ֵ����������100��Ԫ)��(2) ��������ʱ���=100%�����˻���ֵ+5%�����˻���ֵ(5%�����˻���ֵ����������100��Ԫ)��</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">�����յı��ս��������˻���ֵ��105%���㡣</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
	<xsl:if test="count(/TranData/Body/Risk)='2'">
	<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHYrider(RTU)' or /TranData/Body/Risk/RiskCode='HYG3rider(RTU)'">(1) �����յı��ս��������˻���ֵ��105%����;(2) *�ø������Զ��ڽ���׷�ӱ��շѵ���ʽ���Ӹ����˻���ֵ��</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">�����յı��ս��������˻���ֵ��105%���㡣</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
	<!-- ������������ʱ,��Ϊֻ������NHY��YHG3�����������գ������ǵ��ر�Լ����Ϣ��һ�µģ����Բ����жϣ�ֱ�Ӹ�ֵ���� -->
	<xsl:if test="count(/TranData/Body/Risk)='3'">(1)�����յı��ս��������˻���ֵ��105%����;(2) *�ø������Զ��ڽ���׷�ӱ��շѵ���ʽ���Ӹ����˻���ֵ��</xsl:if>
		</SpecialClause>
	</xsl:template>
	
	<!-- ��̬��ӡ��Ϣ -->
	<xsl:template name="BasePlanPrintInfo" match="ProposalPrtNo">
	<!-- ֻ������ -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='PAC' or /TranData/Body/Risk/RiskCode='TM' or /TranData/Body/Risk/RiskCode='BSP' or /TranData/Body/Risk/RiskCode='NBSP' or /TranData/Body/Risk/RiskCode='NPACB' or /TranData/Body/Risk/RiskCode='NPACA' or /TranData/Body/Risk/RiskCode='SPPACB' or /TranData/Body/Risk/RiskCode='SPPACA'">
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
	            <BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
	            <BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
	            <BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
	            <BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">
				<BasePlanPrintInfo3>�����յı��ս��������˻���ֵ��105%���㡣</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='GHONP'">
				<BasePlanPrintInfo3>(1)������ʱ���=100%�����˻���ֵ+10%�����˻���ֵ(10%�����˻���ֵ����������100��Ԫ)��(2) ��������ʱ���=100%�����˻���ֵ+5%�����˻���ֵ(5%�����˻���ֵ����������100��Ԫ)��</BasePlanPrintInfo3>
			</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	<!-- ��һ�������� -->
	<xsl:if test="count(/TranData/Body/Risk)='2'">
		<xsl:choose>
			<!-- MCCIB��LGCI��Ϊ�����и����յ����֣�����������Ϊ2�������������������Ϊ1��3������� -->
			<xsl:when test="/TranData/Body/Risk/RiskCode='MCCIB'">
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='LGCI'">
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
	            <BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
	            <BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
	            <BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
	            <BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHYrider(RTU)' or /TranData/Body/Risk/RiskCode = 'HYG3rider(RTU)'">
				<BasePlanPrintInfo3>(1) �����յı��ս��������˻���ֵ��105%����;(2) *�ø������Զ��ڽ���׷�ӱ��շѵ���ʽ���Ӹ����˻���ֵ��</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
			</xsl:when>
			<!-- NHY�ĸ����ղ���102��HYG3�ĸ����ղ���107ʱƥ����ʾ�������Ϣ -->
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">
				<BasePlanPrintInfo3>�����յı��ս��������˻���ֵ��105%���㡣</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
			</xsl:when>
		</xsl:choose>
	</xsl:if>
	<!-- ������������ʱ,��Ϊֻ������NHY��YHG3�����������գ������ǵĶ�̬��ӡ��Ϣ��һ�µģ����Բ����жϣ�ֱ�Ӹ�ֵ���� -->
	<xsl:if test="count(/TranData/Body/Risk)='3'">
			<BasePlanPrintInfo3>(1)�����յı��ս��������˻���ֵ��105%����;(2) *�ø������Զ��ڽ���׷�ӱ��շѵ���ʽ���Ӹ����˻���ֵ��</BasePlanPrintInfo3>
			<BasePlanPrintInfo5>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ��</BasePlanPrintInfo5>
			<BasePlanPrintInfo6>�ֽ��ֵ�������ͬ������ձ���ͬ���ֽ��ֵ���㡣</BasePlanPrintInfo6>
			<BasePlanPrintInfo7>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ,</BasePlanPrintInfo7>
			<BasePlanPrintInfo8>����˾���紹ѯ��</BasePlanPrintInfo8>
			<BasePlanPrintInfo9>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�</BasePlanPrintInfo9>
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
	<xsl:when test=".='PAC'">003</xsl:when>	<!--  -->
	<xsl:when test=".='NHY'">001</xsl:when>	<!--  -->	
	<xsl:when test=".='NHYrider(RTU)'">102</xsl:when> 
	<xsl:when test=".='NHYrider(lpsm)'">101</xsl:when>
	<xsl:when test=".='HONPG3'">004</xsl:when>
	<xsl:when test=".='TM'">006</xsl:when> 
	<xsl:when test=".='HONG3'">007</xsl:when>
	<xsl:when test=".='MCCIB'">005</xsl:when> 
	<xsl:when test=".='MCCIR'">103</xsl:when>
	<xsl:when test=".='TPD'">008</xsl:when>
	<xsl:when test=".='ADD'">105</xsl:when>
	<xsl:when test=".='NBSP'">002</xsl:when>
	<xsl:when test=".='HYG3'">009</xsl:when>
	<xsl:when test=".='HYG3rider(lpsm)'">106</xsl:when>
	<xsl:when test=".='HYG3rider(RTU)'">107</xsl:when>
	<xsl:when test=".='LGCI'">010</xsl:when>
	<xsl:when test=".='LGCIR'">108</xsl:when>
	<xsl:when test=".='EDU'">280</xsl:when>
	<xsl:when test=".='BSP'">283</xsl:when>
	<xsl:when test=".='GHONP'">286</xsl:when>
	<xsl:when test=".='SPPACA'">013</xsl:when>
	<xsl:when test=".='SPPACB'">014</xsl:when>
	<xsl:when test=".='NPACA'">011</xsl:when>
	<xsl:when test=".='NPACB'">012</xsl:when>
	<xsl:when test=".='AA'">024</xsl:when>
	<xsl:when test=".='FTA'">025</xsl:when>
	<xsl:otherwise>--</xsl:otherwise>
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
	<xsl:when test=".=1">����ĳȷ������</xsl:when>	<!-- ��� -->
	<xsl:when test=".=2">�꽻</xsl:when>	<!-- �½� -->
	<xsl:when test=".=3">�½�</xsl:when>	<!-- ����� -->
	<xsl:when test=".=4">�ս�</xsl:when>	<!-- ���� -->
	<xsl:when test=".=5">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=6">�ս���</xsl:when>	<!-- ������ -->
	<xsl:when test=".=7">�����ڽ�</xsl:when>	<!-- ������ -->
	<xsl:when test=".=8">����</xsl:when>	<!-- ������ -->
	<xsl:when test=".=9">����</xsl:when>	<!-- ����-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

<xsl:template  match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".=1">����ĳȷ������</xsl:when>	<!-- ��� -->
	<xsl:when test=".=2">�걣</xsl:when>	<!-- �½� -->
	<xsl:when test=".=3">�±�</xsl:when>	<!-- ����� -->
	<xsl:when test=".=4">�ձ�</xsl:when>	<!-- ���� -->
	<xsl:when test=".=5">������</xsl:when>	<!-- ���� -->
	<xsl:when test=".=9">����</xsl:when>	<!-- ����-->
	<xsl:otherwise>--</xsl:otherwise>
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
	
</xsl:stylesheet>


