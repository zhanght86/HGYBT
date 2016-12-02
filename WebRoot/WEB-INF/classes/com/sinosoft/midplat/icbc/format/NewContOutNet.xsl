<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	
     <xsl:copy-of select="TranData/Head" /> 
	<TXLifeResponse> 
	<!-- TransRefGUID 交易流水号 -没必要取它的值,即它的值是用不到的-->
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

<!-- 如果交易成功，才返回下面的结点 -->
<xsl:if test="/TranData/Head/Flag='0'">
<OLifE>   
	<Holding id="Holding_">
	<xsl:attribute name="id"><xsl:value-of select="concat('Holding_', ContNo)" /></xsl:attribute>
		<!-- 币种 -->
		<CurrencyTypeCode>1</CurrencyTypeCode>
		<!-- 保单信息 --> 
		<Policy carrierPartyId="CAR_PTY_1">    
			<!-- 保单号ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--险种代码 -->
			<ProductCode><xsl:value-of select="Risk[RiskCode=MainRiskCode]/TranRiskCode" /></ProductCode>
			<!-- 缴费方式 -->
			<PaymentMode tc="1">
		    <xsl:attribute name="tc"><xsl:value-of select="Risk/PayIntv" /></xsl:attribute>
			   <xsl:value-of select="Risk/PayIntv" /> 
		    </PaymentMode> 
			<!-- 起保日期 -->
			<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)" /></EffDate>
			<!-- 承保日期 -->
			<IssueDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></IssueDate>
			<!-- 保单终止日期 -->
			<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/InsuEndDate)" /></TermDate>
			<!-- 缴费终止日期 -->  
			<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/PayEndDate)" /></FinalPaymentDate>
			<!--首期保费-->
			<PaymentAmt><xsl:value-of select="Prem"/></PaymentAmt> <!-- 本期保险费合计：根据PaymentAmt转化为汉字金额 （RMBPaymentAmt元） -->
			<!-- 缴费形式 -->
			<PaymentMethod></PaymentMethod>  
			<Life> 
					<FaceAmt>
					<xsl:choose>
					<!-- 保额  为零置为- -->
						<xsl:when test="/TranData/Body/Amnt='0'">-</xsl:when>						 
						<xsl:otherwise><xsl:value-of select="/TranData/Body/Amnt" /></xsl:otherwise>
					</xsl:choose>
					</FaceAmt>
					<InitCovAmt>
					    <xsl:choose>
					     <!-- 保额  为零置为- -->
						<xsl:when test="/TranData/Body/Amnt='0'">-</xsl:when>						 
						<xsl:otherwise><xsl:value-of select="/TranData/Body/Amnt" /></xsl:otherwise>
					    </xsl:choose>
      				</InitCovAmt>
					
					<!-- 垫交标志/减额交清标志 -->
					<PremOffsetMethod tc="1">
		            <xsl:attribute name="tc"><xsl:value-of select="/TranData/Body/AutoPayFlag" /></xsl:attribute>
			             <xsl:value-of select="/TranData/Body/AutoPayFlag" /> 
			        </PremOffsetMethod>					
					<!-- 红利领取方式 -->
                    <DivType tc="1">
		            <xsl:attribute name="tc"><xsl:value-of select="Risk/BonusGetMode" /></xsl:attribute>
			             <xsl:value-of select="Risk/BonusGetMode" />                     
                    </DivType>
				    <CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				    <xsl:apply-templates select="Risk" />  	
			</Life> 
			<!--申请信息-->
			<ApplicationInfo>
				<!--投保书号-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				<!-- 投保日期 -->
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate>
			</ApplicationInfo>
			<!-- 特别约定 -->			
			<OLifEExtension VendorCode="2">  
				<!-- 特别约定打印标志 -->
				<SpecialClause></SpecialClause>
				<!-- 特别约定  -->  
				<xsl:choose>
				<xsl:when test="Risk/RiskCode='GHONP' or Risk/RiskCode = 'EDU' or Risk/RiskCode ='NHY' or Risk/RiskCode ='HYG3'" >				
				<SpecialClausePrtInd>1</SpecialClausePrtInd>
				  <!-- 特别约定 -这里取/TranData/Body/ContNo值是为了和下面的险种类型匹配--> 
				<xsl:apply-templates select="/TranData/Body/ContNo" />  
				</xsl:when>				
				<xsl:otherwise>
				<!-- 无特别约定，特别约定标志值为0 -->
			    <SpecialClausePrtInd>0</SpecialClausePrtInd>			  			   
			    </xsl:otherwise>
			    </xsl:choose>						
			    <!-- 现金价值打印标志 -->
			    <xsl:choose>
			    <xsl:when test="count(Risk/CashValues/CashValue) = '0'">
			    <CashValueIndicator>0</CashValueIndicator>
			    </xsl:when>
			    <xsl:otherwise>
			    <CashValueIndicator>1</CashValueIndicator>
			    </xsl:otherwise>
				</xsl:choose>
                <!-- 现金价值表扫描左下 -->
                <CashValueDecLeft/>
                <!-- 现金价值表扫描右下 -->
                <CashValueDecRight/>				
				<!-- 首期保费大写 -->
				<PaymentAmtText><xsl:value-of select="PremText"/></PaymentAmtText>
                <!-- 理财功能选择 -->
                <FinFunction/>
                <!-- 锁定收益 -->
                <FixedProfitPercent/>
                <!-- 首次额外追加保费 -->
                <xsl:choose>
                    <!-- 首次额外追加保费为'0'置空 -->
	                <xsl:when test="/TranData/Body/FirstAddPrem ='0'">
	                </xsl:when>               
	                <xsl:otherwise>
	                	<FirstSuperaddAmt><xsl:value-of select="/TranData/Body/FirstAddPrem"/></FirstSuperaddAmt>
	                </xsl:otherwise>
                </xsl:choose> 
                 <!-- 公司电话--> 
                <InsurerDialNumber>400-670-5566</InsurerDialNumber>
                <!-- 犹豫期退保说明 -->
                <xsl:choose>
                <xsl:when test="/TranData/Body/TransChnl='8'">
                   <xsl:choose>
                   <xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode=''">
                   <xsl:choose>
                  <xsl:when test="/TranData/Body/Appnt/Email =''">
                 <WithdrawalDescription>本凭条保存期有限，请您在一个月内登录以下保险公司网址www.icbc-axa.com查询和下载电子保单，同时电子保单将发送您投保时留的电子邮箱。根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
                 </xsl:when>
                  <xsl:otherwise>
                  <WithdrawalDescription>根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
                  </xsl:otherwise>
                  </xsl:choose>
                 </xsl:when>
                  <xsl:otherwise>
                   <xsl:choose>
                  <xsl:when test="/TranData/Body/Appnt/Email =''">
                  <WithdrawalDescription>您的保单犹豫期为投保次日起的15日，如果您在此期间内申请解约，保险公司将全额无息退还己收取的保险费；如果您在此之后申请解约，保险公司将按合同条款约定支付退保金。由于各地监管有特殊要求，对于犹豫期的计算若存在特殊情况，以各地监管要求为准。根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
                  </xsl:when>
                  <xsl:otherwise>
                  <WithdrawalDescription>本凭条保存期有限，请您在一个月内登录以下保险公司网址www.icbc-axa.com查询和下载电子保单，同时电子保单将发送您投保时留的电子邮箱。您的保单犹豫期为投保次日起的15日，如果您在此期间内申请解约，保险公司将全额无息退还己收取的保险费；如果您在此之后申请解约，保险公司将按合同条款约定支付退保金。由于各地监管有特殊要求，对于犹豫期的计算若存在特殊情况，以各地监管要求为准。根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
                  </xsl:otherwise>
                  </xsl:choose>
                  </xsl:otherwise>
                  </xsl:choose>
                 </xsl:when>
                 <xsl:otherwise>
                <xsl:choose>
                    <!-- 首次额外追加保费为'0'置空 -->
	                <xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode=''">
	                <WithdrawalDescription></WithdrawalDescription>
	                </xsl:when>               
	                <xsl:otherwise>
	                	<WithdrawalDescription>您的犹豫期为银行系统承保日期次日起的15日，如果您在此期间内申请解约，保险公司将全额无息退还己收取的保险费；如果您在此之后申请解约，保险公司将按合同条款约定支付退保金。由于各地监管有特殊要求，对于犹豫期的计算若存在特殊情况，以各地监管要求为准。</WithdrawalDescription>
	                </xsl:otherwise>
                </xsl:choose> 
                </xsl:otherwise>
                </xsl:choose>
        		             		
			</OLifEExtension>
		</Policy>
		<xsl:if test="count(Risk/Account)!='0'">
		<Investment>
		            <!--投资帐户个数-->
                    <SubAccountCount><xsl:value-of select="count(Risk/Account)"/></SubAccountCount>
					<xsl:for-each select="Risk/Account">                   
                    <SubAccount>
                        <!--投资帐户代码-->
                        <ProductCode><xsl:value-of select="AccCode"/></ProductCode>
                        <!-- 投资账户名称 -->
                        <ProductFullName><xsl:value-of select="AccName"/></ProductFullName>
                        <!-- 投资账户比率 -->
                        <AllocPercent><xsl:value-of select="AccRate"/></AllocPercent>
                    </SubAccount>
                    </xsl:for-each> 
        </Investment>
        </xsl:if>
		<OLifEExtension VendorCode="3">  
				<!-- 炒汇标志 -->
				<CashEXF>0</CashEXF>
				<!-- 投资日期标志 -->
				<InvestDateInd><xsl:value-of select="/TranData/Body/AccTimeFlag"/></InvestDateInd>
		</OLifEExtension>
	</Holding>
    <!-- 投保人 -->
    <xsl:apply-templates select="Appnt" />  
    <!-- 被保人 -->
    <xsl:apply-templates select="Insured" />            
    <!-- 受益人 -->
    <xsl:apply-templates select="Bnf" />  
	<!-- 投保公司关系 ，选Prem是为了从下面取值时的匹配···-->
    <xsl:apply-templates select="Prem" /> 
	<!-- 投保人关系 -->
 	<Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Applicant0">
			<xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', ContNo)" /></xsl:attribute>
			<xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
		        <OriginatingObjectType tc="4">4</OriginatingObjectType>
		        <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="80">80</RelationRoleCode>
                <OLifEExtension VendorCode="100">
				    <!-- 客户号(投保人) -->
					<CustomerNo></CustomerNo>
				</OLifEExtension>
    </Relation>
    <!-- 被保人关系  -->
    <Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Insured0">
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', ContNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="4">4</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="81">81</RelationRoleCode>
                <OLifEExtension VendorCode="100">
				    <!-- 客户号(被保人) -->
					<CustomerNo></CustomerNo>
				</OLifEExtension>
    </Relation>
    <!-- 被保人与投保人关系 -->
    <Relation OriginatingObjectID="ID_Insured0" RelatedObjectID="ID_Applicant0">           
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="6">6</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="8"><xsl:attribute name="tc"><xsl:value-of select="//Appnt/RelaToInsured" /></xsl:attribute><xsl:value-of select="//Appnt/RelaToInsured"/></RelationRoleCode>
    </Relation> 
    <!-- 投保人与被保人的关系 -->
    <Relation OriginatingObjectID="ID_Applicant0" RelatedObjectID="ID_Insured0">           
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_Applicant0', Appnt/IDNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Insured0', Insured/IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="6">6</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
               <RelationRoleCode tc="8"><xsl:attribute name="tc"><xsl:value-of select="//Appnt/RelaToInsured" /></xsl:attribute><xsl:value-of select="//Appnt/RelaToInsured"/></RelationRoleCode>
    </Relation>                  
    <!-- 受益人关系 -->
    <xsl:for-each select="Bnf">
    <Relation OriginatingObjectID="ID_" RelatedObjectID="ID_Bnf0">
            <xsl:attribute name="OriginatingObjectID"><xsl:value-of select="concat('ID_', /TranData/Body/ContNo)" /></xsl:attribute>
            <xsl:attribute name="RelatedObjectID"><xsl:value-of select="concat('ID_Bnf0', IDNo)" /></xsl:attribute>
                <OriginatingObjectType tc="4">4</OriginatingObjectType>
                <RelatedObjectType tc="6">6</RelatedObjectType>
                <RelationRoleCode tc="82">82</RelationRoleCode>
                <!-- 受益百分数 -->
                <InterestPercent><xsl:value-of select="Lot"/></InterestPercent>
				<!-- 顺序 -->
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
    <!-- 受益人与被保人关系 -->

    <!-- 投保公司关系 -->     
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
			<!-- 交易代码 -->
			<PingAnDepNo><xsl:value-of select="/TranData/Body/AgentComName"/></PingAnDepNo>
			<!-- 平安部门代码 -->
			<PingAnAgentNo><xsl:value-of select="/TranData/Body/AgentName"/>(<xsl:value-of select="/TranData/Body/AXAAgentCode"/>)</PingAnAgentNo>
			<!-- 平安专管员代码 -->
			<RcptNo></RcptNo>
			<!-- 平安后台处理号 -->
			<RcptId></RcptId>
			<!-- 平安前置流水号 -->
			<BasePlanPrintInfo1/>
			<!--动态打印信息1 120C-->
			<BasePlanPrintInfo2/>
			<!--动态打印信息2 120C-->
			<BasePlanPrintInfo3/>
			<!--动态打印信息3 120C-->
			<BasePlanPrintInfo4/>
			<!--动态打印信息4 120C-->
			<BasePlanPrintInfo5/>
			<!--动态打印信息5 120C-->
			<BasePlanPrintInfo6/>
			<!--动态打印信息6 120C-->
			<BasePlanPrintInfo7/>
			<!--动态打印信息7 120C-->
			<BasePlanPrintInfo8/>	
			<!--动态打印信息8 120C-->	
			<BasePlanPrintInfo9/>	
			<!--动态打印信息9 120C-->
			<BasePlanPrintInfo10/>	
			<!--动态打印信息10 120C-->
</OLifEExtension>      
</xsl:if>  
<!-- 如果交易成功，才返回上面的结点 -->
</xsl:template> 

  
<!-- 险种信息 -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<Coverage>
    <!-- 险种名称 -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- 险种代码 -->
	<ProductCode><xsl:value-of select="TranRiskCode" /></ProductCode>
	<!-- 险种类型 -->
	<LifeCovTypeCode>9</LifeCovTypeCode>
	<xsl:choose>
		<!-- 主副险标志 -->
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
	<!-- 投保金额 -->   
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
	<!-- 投保份数 -->
	<IntialNumberOfUnits>    
		<xsl:choose>
			<xsl:when test="Mult>0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose> 
	</IntialNumberOfUnits> 	
	<!-- 险种保费 -->
	<ModalPremAmt><xsl:value-of select="Prem"/></ModalPremAmt>
	<!-- 起保日期 -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- 终止日期 -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- 续期缴费日期 -->	
	<PaymentDueDate></PaymentDueDate>	 
	<!-- 缴费终止日期 -->
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	<BenefitPeriod tc="1" />   
	<!-- 领取方式 -->
	<BenefitMode tc="1" >
	<xsl:attribute name="tc"><xsl:value-of select="../GetIntv" /></xsl:attribute>
		<xsl:value-of select="../GetIntv" /> 
	</BenefitMode>
	<!-- 现金价值表 -->							
	<OLifEExtension VendorCode="10">
	        <!-- 现金价值表    -->  
			<!-- 红利保额保单年度末现金价值表 -->
			<BonusValues>   
						<!-- 现金价值数目 -->			
						<BonusValueCount />
								<BonusValue>
										<!-- 年末 -->
										<End />
										<!-- 年末现金价值 -->
										<Cash />
								</BonusValue>
			</BonusValues>
			<!-- 险种加费 -->
			<ExcessPremAmt/>  						
		    <!-- 缴费年期/年龄类型--> 
			<PaymentDurationMode> <xsl:value-of select="PayEndYearFlag" /></PaymentDurationMode>
			<!-- 缴费年期/年龄--> 
			<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>		
			<!--―领取起始年龄 -->	   
			<PayoutStart/>
			<!--―领取终止年龄 -->				
			<PayoutEnd/>				
		    <!-- 保险年期/年龄标志 -->
			<DurationMode> <xsl:value-of select="InsuYearFlag" /></DurationMode>
			<!-- 保险年期/年龄 -->
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage> 
<OLifEExtension VendorCode="11">
    <!-- 主险保费总额 -->
    <BasePremAmt/>
	<!-- 主险加费总额 -->					
	<BaseExcessPremAmt />
	<!-- 附加险保费总额 -->					
	<RiderPremAmt />
	<!-- 附件险加费总额 -->						
	<RiderExcessPremAmt />
	<!-- 附加险描述 -->
	<RiderDec />
    <!-- 附加险险种列表抬头1 -->
	<RiderListTitle1 />
	<!-- 附加险险种列表抬头2 -->
	<RiderListTitle2 />
	<!-- 附件险险种列表1 -->
	<RiderList1 />
	<!-- 附件险险种列表2 -->
	<RiderList2 />
	<!-- 附件险险种列表3 -->
	<RiderList3 />
	<!-- 附件险险种列表4 -->
	<RiderList4 />
	<!-- 附件险险种列表5 -->
	<RiderList5 />
	<!-- 领取日期标志 -->
	<PayOutDateType />
	<!-- 第一次领取日期 -->
	<FirstPayOutDate></FirstPayOutDate>
	<!-- 缴费方式描述 -->
	<PaymentModeDec></PaymentModeDec>
	<RenewalPermit><xsl:value-of select="/TranData/Body/RenewalPermit" /></RenewalPermit>        							
</OLifEExtension>
</xsl:template> 

  <!-- 投保人 -->
        <xsl:template name="Appnt" match="Appnt">      
            <Party id="ID_Applicant0">
                <xsl:attribute name="id"><xsl:value-of select="concat('ID_Applicant0', IDNo)" /></xsl:attribute>
                <PartyKey>1</PartyKey>
                <!-- 投保人姓名 -->
                <FullName><xsl:value-of select="Name"/></FullName>
                <!-- 投保人证件号码 -->
                <GovtID><xsl:value-of select="IDNo"/></GovtID>
                <!-- 投保人证件类型 -->
                <GovtIDTC tc="0">
                <xsl:attribute name="tc"><xsl:value-of select="IDType" /></xsl:attribute>
                <xsl:value-of select="IDType"/>
                </GovtIDTC>
                <Person>
                    <!-- 投保人性别 -->
                    <Gender tc="1">
                    <xsl:attribute name="tc"><xsl:value-of select="Sex" /></xsl:attribute>
                    <xsl:apply-templates select="Sex "/>
                    </Gender>
                    <!-- 投保人出生日期 -->
                    <BirthDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/></BirthDate>
                    <!-- 投保人职业代码 -->
                    <OccupationType tc="1">
                    <xsl:attribute name="tc"><xsl:value-of select="JobType" /></xsl:attribute>
                    <xsl:value-of select="JobCode"/>
                    </OccupationType>
                    <!-- 投保人年均收入（单位：万元） -->
                    <EstSalary></EstSalary>
                    <!-- 投保人国籍 -->
                    <Nationality><xsl:value-of select="Nationality"/></Nationality>	
                </Person>
                <Address id="Address_1">
                	<!-- 当时人地址类别 -->
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <!-- 投保人邮寄地址 -->
                    <Line1><xsl:value-of select="Address"/></Line1>
                    <!-- 投保人邮寄邮编  -->
                    <Zip><xsl:value-of select="ZipCode"/></Zip>                   
                </Address>
                <Address id="Address_2">
                	<!-- 当时人地址类别 -->
                    <AddressTypeCode tc="2"></AddressTypeCode>
                    <!-- 投保人邮寄地址 -->
                    <Line1></Line1>
                    <!-- 投保人邮寄邮编  -->
                    <Zip></Zip>                   
                </Address>
                <Phone id="Phone_1">
					<!-- 电话号码类别 -->                
                    <PhoneTypeCode tc="1">1</PhoneTypeCode>
                    <!-- 投保人办公电话 -->
                    <DialNumber><xsl:value-of select="Phone"/></DialNumber>
                </Phone>
                <Phone id="Phone_2">
                    <!-- 投保人住宅电话 (投保人联系电话) -->
                    <PhoneTypeCode tc="3">3</PhoneTypeCode>
                    <DialNumber><xsl:value-of select="Mobile"/></DialNumber>
                </Phone>
                <Phone id="Phone_3">
                    <!-- 投保人住宅电话 (投保人联系电话) -->
                    <PhoneTypeCode tc="3"></PhoneTypeCode>
                    <DialNumber></DialNumber>
                </Phone>
                <EMailAddress id="EMailAddress_1">
					<!-- 投保人电子邮件地址  -->
					<AddrLine><xsl:value-of select="Email"/></AddrLine>					
				</EMailAddress>
				<!-- 告知列表  -->
                <OLifEExtension VendorCode="200">
                	<!-- 告知列表 -->				
					<TellInfos>
						<!-- 告知项目数 -->
						<TellInfoCount />						
						<TellInfo>
						    <!-- 告知版别 -->
							<TellVersion></TellVersion>
							<!-- 告知编码 -->
							<TellCode></TellCode>
							<!-- 告知内容 -->
							<TellContent></TellContent>
							<!-- 告知备注 -->
							<TellRemark />							
						</TellInfo>
					</TellInfos>
                </OLifEExtension>
            </Party>
       </xsl:template>
		
	<!-- 被保人 -->
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
                    <!-- 被保人职业代码 -->
                    <OccupationType tc="">
                    <xsl:attribute name="tc"><xsl:value-of select="JobType" /></xsl:attribute>
                    <xsl:value-of select="JobCode"/>
                    </OccupationType>
                    <!-- 投保人年均收入（单位：万元） -->
                    <EstSalary></EstSalary>
                    <!-- 投保人国籍 -->
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
                <!-- 告知相关信息 -->
                <EMailAddress id="EMailAddress_2">
					<!-- 投保人电子邮件地址  -->
					<AddrLine><xsl:value-of select="Email"/></AddrLine>					
				</EMailAddress>
				<!-- 告知列表  -->
                <OLifEExtension VendorCode="200">
                	<!-- 告知列表 -->				
					<TellInfos>
						<!-- 告知项目数 -->
						<TellInfoCount />						
						<TellInfo>
						    <!-- 告知版别 -->
							<TellVersion></TellVersion>
							<!-- 告知编码 -->
							<TellCode></TellCode>
							<!-- 告知内容 -->
							<TellContent></TellContent>
							<!-- 告知备注 -->
							<TellRemark />							
						</TellInfo>
					</TellInfos>
                </OLifEExtension>
         </Party>
	</xsl:template>
	
	<!-- 受益人 -->
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
                    <!-- 投保人国籍 -->
                    <Nationality><xsl:value-of select="Nationality"/></Nationality>
                </Person>
                <!-- 受益人的以下信息没有录入系统，或者说柜面没有传过来，所以都是空值 -->
                <Address id="Address_4">
                    <AddressTypeCode tc="2">2</AddressTypeCode>
                    <Line1/>
                </Address>
           </Party>
	</xsl:template>
	
	<!-- 投保公司关系 -->
 	<xsl:template name="Policy" match="Prem">
            <Party id="ID_CAR_PTY_">
            <xsl:attribute name="id"><xsl:value-of select="concat('ID_CAR_PTY_', string(position()))" /></xsl:attribute>
                <FullName>工银安盛人寿保险有限公司</FullName>
                <GovtID></GovtID>
                <Organization/>
                <Address>
                    <Line1>中国上海市浦东陆家嘴环路166号未来资产大厦19楼</Line1>
                    <Line2/>
                    <City>上海</City>
                    <AddressStateTC/>
                    <Zip>200120</Zip>
                </Address>
                <Phone>
                <!-- 当事人电话类别 -->
                    <PhoneTypeCode tc="2">2</PhoneTypeCode>
                    <AreaCode/>
                    <DialNumber>400-670-5566</DialNumber>
                </Phone>
                <Carrier>
					<!-- 公司代码 -->
					<CarrierCode>028</CarrierCode>
				</Carrier>
            </Party>
	</xsl:template>
	
	<!--险种的特别约定信息 -->
	<xsl:template name="SpecContent" match="ContNo">
	<SpecialClause>	
	<!-- 只有主险时 -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='GHONP'">(1)意外身故保额=100%个人账户价值+10%个人账户价值(10%个人账户价值最高至人民币100万元)；(2) 非意外身故保额=100%个人账户价值+5%个人账户价值(5%个人账户价值最高至人民币100万元)。</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">附加险的保险金额按您个人账户价值的105%计算。</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
	<xsl:if test="count(/TranData/Body/Risk)='2'">
	<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHYrider(RTU)' or /TranData/Body/Risk/RiskCode='HYG3rider(RTU)'">(1) 附加险的保险金额按您个人账户价值的105%计算;(2) *该附加险以定期交纳追加保险费的形式增加个人账户价值。</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">附加险的保险金额按您个人账户价值的105%计算。</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
	<!-- 有两个附加险时,因为只有险种NHY和YHG3有两个附加险，且他们的特别约定信息是一致的，所以不用判断，直接赋值即可 -->
	<xsl:if test="count(/TranData/Body/Risk)='3'">(1)附加险的保险金额按您个人账户价值的105%计算;(2) *该附加险以定期交纳追加保险费的形式增加个人账户价值。</xsl:if>
		</SpecialClause>
	</xsl:template>
	
	<!-- 动态打印信息 -->
	<xsl:template name="BasePlanPrintInfo" match="ProposalPrtNo">
	<!-- 只有主险 -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='PAC' or /TranData/Body/Risk/RiskCode='TM' or /TranData/Body/Risk/RiskCode='BSP' or /TranData/Body/Risk/RiskCode='NBSP' or /TranData/Body/Risk/RiskCode='NPACB' or /TranData/Body/Risk/RiskCode='NPACA' or /TranData/Body/Risk/RiskCode='SPPACB' or /TranData/Body/Risk/RiskCode='SPPACA'">
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
	            <BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
	            <BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
	            <BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
	            <BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='EDU' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">
				<BasePlanPrintInfo3>附加险的保险金额按您个人账户价值的105%计算。</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='GHONP'">
				<BasePlanPrintInfo3>(1)意外身故保额=100%个人账户价值+10%个人账户价值(10%个人账户价值最高至人民币100万元)；(2) 非意外身故保额=100%个人账户价值+5%个人账户价值(5%个人账户价值最高至人民币100万元)。</BasePlanPrintInfo3>
			</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	<!-- 有一个附加险 -->
	<xsl:if test="count(/TranData/Body/Risk)='2'">
		<xsl:choose>
			<!-- MCCIB，LGCI都为必须有附加险的险种，所以险种数为2，而不会出现在险种数为1或3的情况中 -->
			<xsl:when test="/TranData/Body/Risk/RiskCode='MCCIB'">
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='LGCI'">
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
	            <BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
	            <BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
	            <BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
	            <BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHYrider(RTU)' or /TranData/Body/Risk/RiskCode = 'HYG3rider(RTU)'">
				<BasePlanPrintInfo3>(1) 附加险的保险金额按您个人账户价值的105%计算;(2) *该附加险以定期交纳追加保险费的形式增加个人账户价值。</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
			</xsl:when>
			<!-- NHY的附加险不是102，HYG3的附加险不是107时匹配显示下面的信息 -->
			<xsl:when test="/TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode='HYG3'">
				<BasePlanPrintInfo3>附加险的保险金额按您个人账户价值的105%计算。</BasePlanPrintInfo3>
				<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
				<BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
				<BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
				<BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
			</xsl:when>
		</xsl:choose>
	</xsl:if>
	<!-- 有两个附加险时,因为只有险种NHY和YHG3有两个附加险，且他们的动态打印信息是一致的，所以不用判断，直接赋值即可 -->
	<xsl:if test="count(/TranData/Body/Risk)='3'">
			<BasePlanPrintInfo3>(1)附加险的保险金额按您个人账户价值的105%计算;(2) *该附加险以定期交纳追加保险费的形式增加个人账户价值。</BasePlanPrintInfo3>
			<BasePlanPrintInfo5>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo5>
			<BasePlanPrintInfo6>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo6>
			<BasePlanPrintInfo7>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo7>
			<BasePlanPrintInfo8>可向公司来电垂询。</BasePlanPrintInfo8>
			<BasePlanPrintInfo9>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo9>
	</xsl:if>
	</xsl:template>
<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".='M'">1</xsl:when>	<!-- 男 -->
	<xsl:when test=".='F'">2</xsl:when>	<!-- 女 -->
	<xsl:otherwise>3</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 险种代码 -->
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
	<xsl:when test=".=1">年交</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=2">月交</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=3">半年交</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=4">季交</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=5">趸交</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=6">不定期交</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=9">其他</xsl:when>	<!-- 其他-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

<xsl:template  match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".=1">交至某确定年龄</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=2">年交</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=3">月交</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=4">日交</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=5">趸交</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=6">终交费</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=7">不定期交</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=8">半年</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=9">其他</xsl:when>	<!-- 其他-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

<xsl:template  match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".=1">保至某确定年龄</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=2">年保</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=3">月保</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=4">日保</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=5">保终身</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=9">其他</xsl:when>	<!-- 其他-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  
 
 	<xsl:template name="RetData" match="TranData/Head">
		<TransResult>
			<xsl:if test="Flag='0'">
				<ResultCode tc="1">0000</ResultCode>
				<ResultInfo>
					<ResultInfoDesc>交易成功</ResultInfoDesc>
				</ResultInfo>
			</xsl:if>
		</TransResult>
	</xsl:template>
	
</xsl:stylesheet>


