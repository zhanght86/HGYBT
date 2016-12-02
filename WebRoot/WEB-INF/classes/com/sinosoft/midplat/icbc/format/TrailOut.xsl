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
		<!-- 保单信息 --> 
		<Policy>     
			<!--  保单号 -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--  产品代码 --> 
			<ProductCode><xsl:apply-templates select="$MainRisk/RiskCode" /></ProductCode>
			<!-- 保单状态 -->
			<PolicyStatus></PolicyStatus>	
			<!--  缴费方式 -->  
			<PaymentMode><xsl:apply-templates select="$MainRisk/PayIntv" /></PaymentMode>
			<!--  缴费形式 -->
			<PaymentMethod><xsl:apply-templates select="$MainRisk/PayMode" /></PaymentMethod> 	
			<!--  首期保费-->
			<PaymentAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($MainRisk/Prem)"/></PaymentAmt> <!-- 本期保险费合计 首期保费金额(字节,带小数点) --> 
 			<!--  银行帐户20字节 -->
 			<AccountNumber><xsl:value-of select="//Account/AccNo"/></AccountNumber>  
 			<!--  帐户姓名10字节 -->     
 			<AcctHolderName></AcctHolderName>  
 			<!--  帐户类型 -->  
 			<BankAcctType></BankAcctType> 
 			<!--  银行编码10字节 --> 
  			<BankName></BankName> 
			<Life> 
				<!--  垫交标志/减额交清标志 表示非垫交 --> 
				<PremOffsetMethod/> 
				<!--  红利领取方式 --> 
				<DivType><xsl:apply-templates select="$MainRisk/BonusGetMode"/></DivType>
	  			<!--  险种循环次数  -->
	  			<CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				<xsl:apply-templates select="Risk" />
			</Life> 
			
			<!--申请信息--> 
			<ApplicationInfo> 
				<!--投保书号-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				 <!--  投保日期8字节  --> 			
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate> 			
			</ApplicationInfo>
			
			<OLifEExtension>
				<!-- 保险合同生效日期 --> 
				<ContractEffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)"/></ContractEffDate> 
				<!-- 保险合同到期日期 -->
				<ContractEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/InsuEndDate)"/></ContractEndDate>
				<!-- 保障类型 -->
				<CovType></CovType>
				<!-- 保障区域 -->	 
				<CovArea></CovArea>		 
				<!-- 保险期限（天） -->
				<CovPeriod></CovPeriod>
				<!-- 总保费 -->
				<GrossPremAmt><xsl:value-of select="//Body/Prem"/></GrossPremAmt>
			</OLifEExtension>
		</Policy>
	</Holding>
	
	<xsl:apply-templates select="Appnt" />
	<xsl:apply-templates select="Insured" />
	<xsl:apply-templates select="Bnf" />
	<!-- 交易代码 -->  
		
<!-- 代理人信息 -->
<Party id="agent"> 
	<FullName><xsl:value-of select="//Body/AgentName"/></FullName>
	<Person/> 
	<Producer/>
</Party>
<!-- 84代理人关系 -->
<Relation OriginatingObjectID="cont" RelatedObjectID="agent" id="r_cont_agent">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="84">84</RelationRoleCode>
</Relation>

<!-- 保险公司信息 -->
<Party id="com"> 
	<!-- 保险公司名称 -->
	<FullName><xsl:value-of select="ComName"/></FullName>
	<!-- 保险公司地址 -->
	<Address>
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="ComLocation"/></Line1>
		<Zip><xsl:value-of select="ComZipCode"/></Zip>
	</Address>
	<!-- 保险公司电话 -->
	<Phone>
		<PhoneTypeCode tc="2">2</PhoneTypeCode>
		<DialNumber><xsl:value-of select="ComPhone"/></DialNumber>
	</Phone>
	<!-- 公司代码 -->
	<Carrier> 
		<CarrierCode></CarrierCode>
	</Carrier>
	</Party>	
<!-- 85承包公司关系 -->
<Relation OriginatingObjectID="cont" RelatedObjectID="com" id="r_cont_com">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="85">85</RelationRoleCode>
	</Relation> 
</OLifE>
 
</xsl:template>


<!-- 险种信息 -->
<xsl:template name="Coverage" match="Risk">
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('risk_', string(position()))" /></xsl:attribute>
	<!-- 险种名称 -->        
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- 险种代码 --> 
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
	
	<!-- 险种类型 LifeCovTypeCode-->
	<xsl:choose>
		<xsl:when test="RiskCode='313020' or RiskCode='313030' ">
			<LifeCovTypeCode>9</LifeCovTypeCode>	<!-- 非终身寿险 -->
		</xsl:when>  
		<xsl:otherwise> 
			<LifeCovTypeCode>1</LifeCovTypeCode>	<!-- 终身寿险 -->
		</xsl:otherwise>
	</xsl:choose> 
	
	<!-- 主副险标志 -->
	<xsl:choose> 
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose>
	<!--  缴费方式  --> 
	<PaymentMode><xsl:apply-templates select="PayIntv" /></PaymentMode>
 	<!-- 投保金额 -->
	<InitCovAmt>  
		<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</InitCovAmt>
	<!-- 投保份额 -->
	<IntialNumberOfUnits> 
		<xsl:choose>
			<xsl:when test="Mult>=0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</IntialNumberOfUnits>
	<!-- 险种保费 -->
	<ModalPremAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></ModalPremAmt>
	<!-- 起保日期 -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- 终止日期 -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- 缴费终止日期 --> 
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
			<!-- 领取起始年龄 --> 
  			<PayoutStart><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(GetStartDate)" /></PayoutStart>
			<!-- 领取终止年龄  --> 
 			<PayoutEnd /> 
 			
 			
		 
		
		<!-- 保险期间 --> 
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
		
			<!--  健康加费  --> 
			<HealthPrem></HealthPrem> 
			<!--  职业加费  -->
 			<JobPrem></JobPrem> 
			<!--  红利保额 -->
 			<BonusAmnt></BonusAmnt> 	
 			<!--  保险费率  --> 	 
 			<PremRate></PremRate> 			
		</OLifEExtension>		
</Coverage> 
</xsl:template>
  


<xsl:template name="Party80" match="Appnt">
<xsl:variable name="Rela"><xsl:apply-templates select="RelaToInsured" /></xsl:variable>
<!-- 投保人信息 -->
<Party id="appnt">
	<!-- 投保人姓名10字节 -->
	<FullName><xsl:value-of select="Name"/></FullName>
	<!-- 投保人证件类型1字节 -->
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<!-- 投保人性别1字节 -->
		<Gender><xsl:apply-templates select="Sex" /></Gender>
			<!-- 投保人出生日期10字节 -->
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>  
	<Address>	<!-- 邮寄地址 -->
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="Address"/></Line1>
		<Zip><xsl:value-of select="ZipCode"/></Zip>
	</Address>
	<Phone>	<!-- 家庭电话 -->
		<PhoneTypeCode tc="1">1</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Phone"/></DialNumber>
	</Phone>
	<Phone>	<!-- 移动电话 -->
		<PhoneTypeCode tc="3">3</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Mobile"/></DialNumber>
	</Phone>
</Party>

<!-- 投保人关系 -->
<Relation OriginatingObjectID="cont" RelatedObjectID="appnt" id="r_cont_appnt">
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="80">80</RelationRoleCode>
	<OLifEExtension VendorCode="100">
		<CustomerNo><xsl:value-of select="CustomerNo" /></CustomerNo>
	</OLifEExtension> 
</Relation>

<!-- 投保人与被保人的关系 -->
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
<!-- 被保人信息 -->
<Party id="insured">
	<FullName><xsl:value-of select="Name"/></FullName>
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<Gender><xsl:apply-templates select="Sex" /></Gender>
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>
	<Address> <!-- 邮寄地址 -->
		<AddressTypeCode tc="2">2</AddressTypeCode>
		<Line1><xsl:value-of select="Address"/></Line1>
		<Zip><xsl:value-of select="ZipCode"/></Zip>
	</Address>
	<Phone>	<!-- 家庭电话 -->
		<PhoneTypeCode tc="1">1</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Phone"/></DialNumber>
	</Phone>
	<Phone>	<!-- 移动电话 -->
		<PhoneTypeCode tc="3">3</PhoneTypeCode>
		<DialNumber><xsl:value-of select="Mobile"/></DialNumber>
	</Phone>
</Party>

<!-- 81被保人关系 -->
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
<!-- 受益人信息 --> 
<Party><xsl:attribute name="id"><xsl:value-of select="$BnfId" /></xsl:attribute>
	<FullName><xsl:value-of select="Name"/></FullName>
	<GovtIDTC><xsl:apply-templates select="IDType" /></GovtIDTC>
	<GovtID><xsl:value-of select="IDNo"/></GovtID>
	<Person>
		<Gender><xsl:apply-templates select="Sex" /></Gender>
		<BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)" /></BirthDate>
	</Person>
</Party>

<!-- 82受益人关系 -->
<Relation OriginatingObjectID="cont">
	<xsl:attribute name="RelatedObjectID"><xsl:value-of select="$BnfId" /></xsl:attribute>
	<xsl:attribute name="id"><xsl:value-of select="concat('r_cont_', $BnfId)" /></xsl:attribute> 
	<OriginatingObjectType>4</OriginatingObjectType>
	<RelatedObjectType>6</RelatedObjectType>
	<RelationRoleCode tc="82">82</RelationRoleCode>
	<Sequence><xsl:value-of select="Grade"/></Sequence>
	<InterestPercent><xsl:value-of select="Lot"/></InterestPercent>
</Relation>
<!-- 受益人与被保人关系 -->
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






<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>     
	<xsl:when test=".=0">1</xsl:when>	<!-- 男 -->
	<xsl:when test=".=1">2</xsl:when>	<!-- 女 -->
	<xsl:when test=".=2">3</xsl:when>	<!-- 其他 -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template> 
 
<!-- 证件类型？？？-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=7">9</xsl:when>	<!-- 警官证  -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 关系 -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=01">1</xsl:when>	<!-- 配偶 -->
	<xsl:when test=".=03">2</xsl:when>	<!-- 父母 -->
	<xsl:when test=".=04">3</xsl:when>	<!-- 子女 -->
	<xsl:when test=".=21">4</xsl:when>	<!-- 祖父祖母 -->
	<xsl:when test=".=31">5</xsl:when>	<!-- 孙子女 -->
	<xsl:when test=".=12">6</xsl:when>	<!-- 兄弟姐妹 -->
	<xsl:when test=".=25">7</xsl:when>	<!-- 其他亲属 -->
	<xsl:when test=".=00">8</xsl:when>	<!-- 本人 -->
	<xsl:when test=".=27">9</xsl:when>	<!-- 朋友 -->
	<xsl:otherwise></xsl:otherwise>
</xsl:choose>  
</xsl:template>

<xsl:template name="tran_RelaToInsured"> 
  <xsl:param name="RelaIns">0</xsl:param>  
	<xsl:if test="$RelaIns =01">1</xsl:if>	<!-- 配偶 -->
	<xsl:if test="$RelaIns =04">2</xsl:if>	<!-- 父母-->
	<xsl:if test="$RelaIns =03">3</xsl:if>	<!-- 子女 -->
	<xsl:if test="$RelaIns =31">4</xsl:if>	<!-- 祖父祖母 -->
	<xsl:if test="$RelaIns =21">5</xsl:if>	<!-- 孙子女 -->
	<xsl:if test="$RelaIns =12">6</xsl:if>	<!-- 兄弟姐妹 -->
	<xsl:if test="$RelaIns =25">7</xsl:if>	<!-- 其他亲属 -->
	<xsl:if test="$RelaIns =00">8</xsl:if>	<!-- 本人 -->
	<xsl:if test="$RelaIns =27">9</xsl:if>	<!-- 朋友 -->
  </xsl:template> 
 
<!-- 险种代码 -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=321010">001</xsl:when>	<!-- 中融融盛连年终身寿险（万能型）B款 -->
	<xsl:when test=".=313030">003</xsl:when>  <!-- 中融融华富贵两全保险（分红型）A款 -->
	<xsl:when test=".=313040">004</xsl:when>  <!-- 中融融华富贵两全保险（分红型）B款 -->
	<xsl:when test=".=321030">002</xsl:when>  <!-- 中融融盛连年终身寿险（万能型）C款 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 缴费形式 -->
<xsl:template name="tran_PaymentMethod" match="PayMode">
<xsl:choose>
	<xsl:when test=".=1">4</xsl:when>	<!-- 银行转账 -->
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

<!-- 缴费频次 -->
<xsl:template name="tran_PaymentMode" match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">1</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">2</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">3</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">4</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">5</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">6</xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 终交年期标志的转换 -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 缴至某确定年龄 -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
<!-- 保险年龄年期标志 -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 保至某确定年龄 -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年保 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月保 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日保 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 红利领取方式 -->
<xsl:template name="tran_BonusGetMode" match="DivType">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- 累积生息 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 领取现金 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 抵交保费 -->
	<xsl:when test=".=5">4</xsl:when>	<!-- 增额交清 -->
	<xsl:when test=".=''">5</xsl:when>	<!-- 无红利 转为NULL -->
	<xsl:when test=".=4">9</xsl:when>	<!-- 其他 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
</xsl:stylesheet>
