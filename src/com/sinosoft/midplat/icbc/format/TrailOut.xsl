<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>
 	
	<xsl:copy-of select="TranData/Head" /> 
	<TXLifeResponse>
	<TransRefGUID></TransRefGUID> 
 	 <TransType>1012</TransType> 
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse>    
</TXLife> 
</xsl:template>  
 
<xsl:template name="OLifE" match="Body">

<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
 
<OLifE>
	<Holding id="cont">   
		<CurrencyTypeCode>001</CurrencyTypeCode>
		<!-- ������Ϣ --> 
		<Policy>     
			<!--  ������ -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--  ��Ʒ���� --> 
			<ProductCode><xsl:apply-templates select="$MainRisk/RiskCode" /></ProductCode>
			<!-- ����״̬ -->
			<PolicyStatus></PolicyStatus>	
			<!--  �ɷѷ�ʽ -->  
			<PaymentMode><xsl:apply-templates select="$MainRisk/PayIntv" /></PaymentMode>
			<!--  �ɷ���ʽ -->
			<PaymentMethod><xsl:apply-templates select="$MainRisk/PayMode" /></PaymentMethod> 	
			<!--  ���ڱ���-->
			<PaymentAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($MainRisk/Prem)"/></PaymentAmt> <!-- ���ڱ��շѺϼ� ���ڱ��ѽ��(�ֽ�,��С����) --> 
 			<!--  �����ʻ�20�ֽ� -->
 			<AccountNumber><xsl:value-of select="//Account/AccNo"/></AccountNumber>  
 			<!--  �ʻ�����10�ֽ� -->     
 			<AcctHolderName></AcctHolderName>  
 			<!--  �ʻ����� -->  
 			<BankAcctType></BankAcctType> 
 			<!--  ���б���10�ֽ� --> 
  			<BankName></BankName> 
			<Life> 
				<!--  �潻��־/������־ ��ʾ�ǵ潻 --> 
				<PremOffsetMethod/> 
				<!--  ������ȡ��ʽ --> 
				<DivType><xsl:apply-templates select="$MainRisk/BonusGetMode"/></DivType>
	  			<!--  ����ѭ������  -->
	  			<CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				<xsl:apply-templates select="Risk" />
			</Life> 
			
			<!--������Ϣ--> 
			<ApplicationInfo> 
				<!--Ͷ�����-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				 <!--  Ͷ������8�ֽ�  --> 			
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate> 			
			</ApplicationInfo>
			
			<OLifEExtension>
				<!-- ���պ�ͬ��Ч���� --> 
				<ContractEffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)"/></ContractEffDate> 
				<!-- ���պ�ͬ�������� -->
				<ContractEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/InsuEndDate)"/></ContractEndDate>
				<!-- �������� -->
				<CovType></CovType>
				<!-- �������� -->	 
				<CovArea></CovArea>		 
				<!-- �������ޣ��죩 -->
				<CovPeriod></CovPeriod>
				<!-- �ܱ��� -->
				<GrossPremAmt><xsl:value-of select="//Body/Prem"/></GrossPremAmt>
			</OLifEExtension>
		</Policy>
	</Holding>
	
	<xsl:apply-templates select="Appnt" />
	<xsl:apply-templates select="Insured" />
	<xsl:apply-templates select="Bnf" />
	<!-- ���״��� -->  
		
<!-- ��������Ϣ -->
<Party id="agent"> 
	<FullName><xsl:value-of select="//Body/AgentName"/></FullName>
	<Person/> 
	<Producer/>
</Party>
<!-- 84�����˹�ϵ -->
<Relation OriginatingObjectID="cont" RelatedObjectID="agent" id="r_cont_agent">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="84">84</RelationRoleCode>
</Relation>

<!-- ���չ�˾��Ϣ -->
<Party id="com"> 
	<!-- ���չ�˾���� -->
	<FullName><xsl:value-of select="ComName"/></FullName>
	<!-- ���չ�˾��ַ -->
	<Address>
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="ComLocation"/></Line1>
		<Zip><xsl:value-of select="ComZipCode"/></Zip>
	</Address>
	<!-- ���չ�˾�绰 -->
	<Phone>
		<PhoneTypeCode tc="2">2</PhoneTypeCode>
		<DialNumber><xsl:value-of select="ComPhone"/></DialNumber>
	</Phone>
	<!-- ��˾���� -->
	<Carrier> 
		<CarrierCode></CarrierCode>
	</Carrier>
	</Party>	
<!-- 85�а���˾��ϵ -->
<Relation OriginatingObjectID="cont" RelatedObjectID="com" id="r_cont_com">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="85">85</RelationRoleCode>
	</Relation> 
</OLifE>
 
</xsl:template>


<!-- ������Ϣ -->
<xsl:template name="Coverage" match="Risk">
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('risk_', string(position()))" /></xsl:attribute>
	<!-- �������� -->        
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- ���ִ��� --> 
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
	
	<!-- �������� LifeCovTypeCode-->
	<xsl:choose>
		<xsl:when test="RiskCode='313020' or RiskCode='313030' ">
			<LifeCovTypeCode>9</LifeCovTypeCode>	<!-- ���������� -->
		</xsl:when>  
		<xsl:otherwise> 
			<LifeCovTypeCode>1</LifeCovTypeCode>	<!-- �������� -->
		</xsl:otherwise>
	</xsl:choose> 
	
	<!-- �����ձ�־ -->
	<xsl:choose> 
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose>
	<!--  �ɷѷ�ʽ  --> 
	<PaymentMode><xsl:apply-templates select="PayIntv" /></PaymentMode>
 	<!-- Ͷ����� -->
	<InitCovAmt>  
		<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</InitCovAmt>
	<!-- Ͷ���ݶ� -->
	<IntialNumberOfUnits> 
		<xsl:choose>
			<xsl:when test="Mult>=0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</IntialNumberOfUnits>
	<!-- ���ֱ��� -->
	<ModalPremAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></ModalPremAmt>
	<!-- ������ -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- ��ֹ���� -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- �ɷ���ֹ���� --> 
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	<BenefitPeriod tc="1" /> 
    <BenefitMode tc="1" ><xsl:value-of select="GetIntv"/></BenefitMode>  
	
	<OLifEExtension>
		<xsl:choose>
			<xsl:when test="(PayEndYear = 105) and (PayEndYearFlag = 'A')">
				<PaymentDurationMode>5</PaymentDurationMode>
				<PaymentDuration>0</PaymentDuration>
			</xsl:when>
			<xsl:otherwise> 
				<PaymentDurationMode><xsl:apply-templates select="PayEndYearFlag" /></PaymentDurationMode>
				<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>
			</xsl:otherwise>
		</xsl:choose> 
			<!-- ��ȡ��ʼ���� --> 
  			<PayoutStart><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(GetStartDate)" /></PayoutStart>
			<!-- ��ȡ��ֹ����  --> 
 			<PayoutEnd /> 
 			
 			
		 
		
		<!-- �����ڼ� --> 
		<xsl:choose>
			<xsl:when test="(InsuYear= 105) and (InsuYearFlag = 'A')">
				<DurationMode>5</DurationMode>
				<Duration>999</Duration>
			</xsl:when>
			<xsl:otherwise> 
				<DurationMode><xsl:apply-templates select="InsuYearFlag" /></DurationMode>
				<Duration><xsl:value-of select="InsuYear"/></Duration>
			</xsl:otherwise>
		</xsl:choose>
		
			<!--  �����ӷ�  --> 
			<HealthPrem></HealthPrem> 
			<!--  ְҵ�ӷ�  -->
 			<JobPrem></JobPrem> 
			<!--  �������� -->
 			<BonusAmnt></BonusAmnt> 	
 			<!--  ���շ���  --> 	 
 			<PremRate></PremRate> 			
		</OLifEExtension>		
</Coverage> 
</xsl:template>
  


<xsl:template name="Party80" match="Appnt">
<xsl:variable name="Rela"><xsl:apply-templates select="RelaToInsured" /></xsl:variable>
<!-- Ͷ������Ϣ -->
<Party id="appnt">
	<!-- Ͷ��������10�ֽ� -->
	<FullName><xsl:value-of select="Name"/></FullName>
	<!-- Ͷ����֤������1�ֽ� -->
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<!-- Ͷ�����Ա�1�ֽ� -->
		<Gender><xsl:apply-templates select="Sex" /></Gender>
			<!-- Ͷ���˳�������10�ֽ� -->
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>  
	<Address>	<!-- �ʼĵ�ַ -->
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="Address"/></Line1>
		<Zip><xsl:value-of select="ZipCode"/></Zip>
	</Address>
	<Phone>	<!-- ��ͥ�绰 -->
		<PhoneTypeCode tc="1">1</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Phone"/></DialNumber>
	</Phone>
	<Phone>	<!-- �ƶ��绰 -->
		<PhoneTypeCode tc="3">3</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Mobile"/></DialNumber>
	</Phone>
</Party>

<!-- Ͷ���˹�ϵ -->
<Relation OriginatingObjectID="cont" RelatedObjectID="appnt" id="r_cont_appnt">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="80">80</RelationRoleCode>
	<OLifEExtension VendorCode="100">
		<CustomerNo><xsl:value-of select="CustomerNo" /></CustomerNo>
	</OLifEExtension> 
</Relation>

<!-- Ͷ�����뱻���˵Ĺ�ϵ -->
<Relation OriginatingObjectID="insured" RelatedObjectID="appnt" id="r_insured_appnt">
	<OriginatingObjectType>6</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode>
		<xsl:attribute name="tc"><xsl:value-of select="$Rela" /></xsl:attribute>
		<xsl:value-of select="$Rela" />
	</RelationRoleCode>  
</Relation>
</xsl:template>


<xsl:template name="Party81" match="Insured">
<!-- ��������Ϣ -->
<Party id="insured">
	<FullName><xsl:value-of select="Name"/></FullName>
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<Gender><xsl:apply-templates select="Sex" /></Gender>
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>
	<Address> <!-- �ʼĵ�ַ -->
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="Address"/></Line1>
		<Zip><xsl:value-of select="ZipCode"/></Zip>
	</Address>
	<Phone>	<!-- ��ͥ�绰 -->
		<PhoneTypeCode tc="1">1</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Phone"/></DialNumber>
	</Phone>
	<Phone>	<!-- �ƶ��绰 -->
		<PhoneTypeCode tc="3">3</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Mobile"/></DialNumber>
	</Phone>
</Party>

<!-- 81�����˹�ϵ -->
<Relation OriginatingObjectID="cont" RelatedObjectID="insured" id="r_cont_insured">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="81">81</RelationRoleCode>
	<OLifEExtension VendorCode="100">
		<CustomerNo><xsl:value-of select="CustomerNo" /></CustomerNo>
	</OLifEExtension> 
</Relation>
</xsl:template>


<xsl:template name="Party82" match="Bnf">
<xsl:variable name="BnfId" select="concat('bnf_', string(position()))" />
<xsl:variable name="Rela"><xsl:call-template name="tran_RelaToInsured">
			<xsl:with-param name="RelaIns">
	<xsl:value-of select="RelaToInsured"/>
 				</xsl:with-param>
 				</xsl:call-template>
 				
 				</xsl:variable>
<!-- ��������Ϣ --> 
<Party><xsl:attribute name="id"><xsl:value-of select="$BnfId" /></xsl:attribute>
	<FullName><xsl:value-of select="Name"/></FullName>
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<Gender><xsl:apply-templates select="Sex" /></Gender>
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>
</Party>

<!-- 82�����˹�ϵ -->
<Relation OriginatingObjectID="cont">
	<xsl:attribute name="RelatedObjectID"><xsl:value-of select="$BnfId" /></xsl:attribute>
	<xsl:attribute name="id"><xsl:value-of select="concat('r_cont_', $BnfId)" /></xsl:attribute> 
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="82">82</RelationRoleCode>
	<Sequence><xsl:value-of select="Grade"/></Sequence>
	<InterestPercent><xsl:value-of select="Lot"/></InterestPercent>
</Relation>
<!-- �������뱻���˹�ϵ -->
<Relation OriginatingObjectID="insured">
	<xsl:attribute name="RelatedObjectID"><xsl:value-of select="$BnfId" /></xsl:attribute>
	<xsl:attribute name="id"><xsl:value-of select="concat('r_insured_', $BnfId)" /></xsl:attribute>
	<OriginatingObjectType>6</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode> 
		<xsl:attribute name="tc"><xsl:value-of select="$Rela" /></xsl:attribute>
		
		<xsl:value-of select="$Rela" />
	</RelationRoleCode>
</Relation>

</xsl:template>






<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>     
	<xsl:when test=".=0">1</xsl:when>	<!-- �� -->
	<xsl:when test=".=1">2</xsl:when>	<!-- Ů -->
	<xsl:when test=".=2">3</xsl:when>	<!-- ���� -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template> 
 
<!-- ֤�����ͣ�����-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- ����  -->
	<xsl:when test=".=7">9</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- ��ϵ -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=01">1</xsl:when>	<!-- ��ż -->
	<xsl:when test=".=03">2</xsl:when>	<!-- ��ĸ -->
	<xsl:when test=".=04">3</xsl:when>	<!-- ��Ů -->
	<xsl:when test=".=21">4</xsl:when>	<!-- �游��ĸ -->
	<xsl:when test=".=31">5</xsl:when>	<!-- ����Ů -->
	<xsl:when test=".=12">6</xsl:when>	<!-- �ֵܽ��� -->
	<xsl:when test=".=25">7</xsl:when>	<!-- �������� -->
	<xsl:when test=".=00">8</xsl:when>	<!-- ���� -->
	<xsl:when test=".=27">9</xsl:when>	<!-- ���� -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>  
</xsl:template>

<xsl:template name="tran_RelaToInsured"> 
  <xsl:param name="RelaIns">0</xsl:param>  
	<xsl:if test="$RelaIns =01">1</xsl:if>	<!-- ��ż -->
	<xsl:if test="$RelaIns =04">2</xsl:if>	<!-- ��ĸ-->
	<xsl:if test="$RelaIns =03">3</xsl:if>	<!-- ��Ů -->
	<xsl:if test="$RelaIns =31">4</xsl:if>	<!-- �游��ĸ -->
	<xsl:if test="$RelaIns =21">5</xsl:if>	<!-- ����Ů -->
	<xsl:if test="$RelaIns =12">6</xsl:if>	<!-- �ֵܽ��� -->
	<xsl:if test="$RelaIns =25">7</xsl:if>	<!-- �������� -->
	<xsl:if test="$RelaIns =00">8</xsl:if>	<!-- ���� -->
	<xsl:if test="$RelaIns =27">9</xsl:if>	<!-- ���� -->
  </xsl:template> 
 
<!-- ���ִ��� -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=321010">001</xsl:when>	<!-- ������ʢ�����������գ������ͣ�B�� -->
	<xsl:when test=".=313030">003</xsl:when>  <!-- �����ڻ�������ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=313040">004</xsl:when>  <!-- �����ڻ�������ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=321030">002</xsl:when>  <!-- ������ʢ�����������գ������ͣ�C�� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- �ɷ���ʽ -->
<xsl:template name="tran_PaymentMethod" match="PayMode">
<xsl:choose>
	<xsl:when test=".=1">4</xsl:when>	<!-- ����ת�� -->
	<!--  
	<xsl:when test=".=3">2</xsl:when>	 
	<xsl:when test=".=1">3</xsl:when>	
	<xsl:when test=".=1">4</xsl:when>	
	<xsl:when test=".=1">5</xsl:when>	
	<xsl:when test=".=4">6</xsl:when>	 
	<xsl:when test=".=8">7</xsl:when>	
	<xsl:when test=".=9">9</xsl:when>	
	-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ɷ�Ƶ�� -->
<xsl:template name="tran_PaymentMode" match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">1</xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">2</xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">3</xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">4</xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">5</xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">6</xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ս����ڱ�־��ת�� -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �� -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
<!-- �����������ڱ�־ -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �걣 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �±� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �ձ� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- ������ȡ��ʽ -->
<xsl:template name="tran_BonusGetMode" match="DivType">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- �ۻ���Ϣ -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ��ȡ�ֽ� -->
	<xsl:when test=".=3">3</xsl:when>	<!-- �ֽ����� -->
	<xsl:when test=".=5">4</xsl:when>	<!-- ����� -->
	<xsl:when test=".=''">5</xsl:when>	<!-- �޺��� תΪNULL -->
	<xsl:when test=".=4">9</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
</xsl:stylesheet>
