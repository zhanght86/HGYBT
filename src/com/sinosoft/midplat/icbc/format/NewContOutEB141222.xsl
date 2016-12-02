<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	
     <xsl:copy-of select="TranData/Head" /> 
	<TXLifeResponse> 
	<!-- TransRefGUID 交易流水号 -没必要取它的值,即它的值是用不到的   lilu20141127前面这就话是谁写的？我在format类里已经给加上了-->
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
	<Holding id="Holding_1">
	<xsl:attribute name="id"><xsl:value-of select="concat('Holding_', ContNo)" /></xsl:attribute>
		<!-- 币种 -->
		<CurrencyTypeCode>001</CurrencyTypeCode>
		<!-- 保单信息 --> 
		<Policy carrierPartyId="CAR_PTY_1">    
			<!-- 保单号ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--险种代码 -->
			<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
			<!-- 缴费方式 -->
			<PaymentMode tc="1">
		    	<!-- 缴费方式 频次 -->  
					<xsl:call-template name="tran_PayIntv">
							<xsl:with-param name="PayIntv"> 
								<xsl:value-of select="PayIntv"/>
							</xsl:with-param>   
						</xsl:call-template> 
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
			<PaymentMethod><xsl:apply-templates select="tran_paymode" /></PaymentMethod>  
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
		            <xsl:attribute name="tc"></xsl:attribute>
			             <xsl:call-template name="auto_payflag">
							<xsl:with-param name="AutoPayFlag"> 
								<xsl:value-of select="/TranData/Body/AutoPayFlag"/>
							</xsl:with-param>   
						</xsl:call-template> 
			        </PremOffsetMethod>					
					<!-- 红利领取方式 -->
                    <DivType tc="1">
		            <xsl:attribute name="tc"><xsl:value-of select="Risk/BonusGetMode" /></xsl:attribute>
			             <xsl:value-of select="Risk/BonusGetMode" />                     
                    </DivType>
				    <CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				    <xsl:apply-templates select="Risk" />
				    <OLifEExtension VendorCode="11">
					    <!-- 主险保费总额 -->
					    <BasePremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem"/></BasePremAmt>
						<!-- 主险加费总额 -->					
						<BaseExcessPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/Amnt"/></BaseExcessPremAmt>
						<!-- 附加险保费总额 -->					
						<RiderPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/Prem"/></RiderPremAmt>
						<!-- 附件险加费总额 -->						
						<RiderExcessPremAmt><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/Amnt"/></RiderExcessPremAmt>
						<!-- 附加险描述 -->
						<RiderDec><xsl:value-of select="/TranData/Body/Risk[RiskCode!=MainRiskCode]/RiskName"/></RiderDec>
					    <!-- 附加险险种列表抬头1 -->
						<RiderListTitle1></RiderListTitle1>
						<!-- 附加险险种列表抬头2 -->
						<RiderListTitle2></RiderListTitle2>
						<!-- 附件险险种列表1 -->
						<RiderList1></RiderList1>
						<!-- 附件险险种列表2 -->
						<RiderList2></RiderList2>
						<!-- 附件险险种列表3 -->
						<RiderList3></RiderList3>
						<!-- 附件险险种列表4 -->
						<RiderList4></RiderList4>
						<!-- 附件险险种列表5 -->
						<RiderList5></RiderList5>
						<!-- 领取日期标志 -->
						<PayOutDateType></PayOutDateType>
						<!-- 第一次领取日期 -->
						<FirstPayOutDate></FirstPayOutDate>
						<!-- 缴费方式描述 -->
						<PaymentModeDec></PaymentModeDec>
						<RenewalPermit><xsl:value-of select="/TranData/Body/RenewalPermit" /></RenewalPermit>        							
					</OLifEExtension>  	
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
				<!-- 特别约定  暂时为空-->  
				<xsl:choose>
				<xsl:when test="Risk/RiskCode=''" >				
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
                <!-- 首次额外追加保费为'0'置空 -->
	            <FirstSuperaddAmt></FirstSuperaddAmt>
                 <!-- 公司电话--> 
                <InsurerDialNumber>4009-800-800</InsurerDialNumber>
                <!-- 犹豫期退保说明 -->
                <xsl:choose>
	                <!-- 网银/自助终端 -->
	                <xsl:when test="/TranData/Body/SaleChannel !='0'">
	                   <xsl:choose>
		                   <xsl:when test="/TranData/Body/Risk/RiskCode='231201' or /TranData/Body/Risk/RiskCode='231202' or /TranData/Body/Risk/RiskCode='231203' or /TranData/Body/Risk/RiskCode='211901' or /TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='221201' or /TranData/Body/Risk/RiskCode='231204' or /TranData/Body/Risk/RiskCode='211902' or /TranData/Body/Risk/RiskCode='221301' or /TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='221203' or  /TranData/Body/Risk/RiskCode='225501' " >
		                   <xsl:choose>
			                  <xsl:when test="/TranData/Body/Appnt/Email =''">
			                  <WithdrawalDescription>本凭条保存期有限，请您在一个月内登录以下保险公司网址www.sinokorealife.com查询和下载电子保单，同时电子保单将发送您投保时留的电子邮箱。根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
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
				                  <WithdrawalDescription>本凭条保存期有限，请您在一个月内登录以下保险公司网址www.sinokorealife.com查询和下载电子保单，同时电子保单将发送您投保时留的电子邮箱。您的保单犹豫期为投保次日起的15日，如果您在此期间内申请解约，保险公司将全额无息退还己收取的保险费；如果您在此之后申请解约，保险公司将按合同条款约定支付退保金。由于各地监管有特殊要求，对于犹豫期的计算若存在特殊情况，以各地监管要求为准。根据监管部门要求，将根据您在工行留的联系地址补充客户投保信息给到保险公司。</WithdrawalDescription>
				                  </xsl:otherwise>
			                  </xsl:choose>
		                  </xsl:otherwise>
	                  </xsl:choose>
	                </xsl:when>
	                 <xsl:otherwise>
	                <xsl:choose>
	                    <!-- 首次额外追加保费为'0'置空 -->
		                 <xsl:when test="/TranData/Body/Risk/RiskCode='231201' or /TranData/Body/Risk/RiskCode='231202' or /TranData/Body/Risk/RiskCode='231203' or /TranData/Body/Risk/RiskCode='211901' or /TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='221201' or /TranData/Body/Risk/RiskCode='231204' or /TranData/Body/Risk/RiskCode='211902' or /TranData/Body/Risk/RiskCode='221301' or /TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='221203' or  /TranData/Body/Risk/RiskCode='225501' " >
		                <WithdrawalDescription></WithdrawalDescription>
		                </xsl:when>               
		                <xsl:otherwise>
		                	<WithdrawalDescription>您的犹豫期为银行系统承保日期次日起的10日，如果您在此期间内申请解约，保险公司将全额无息退还己收取的保险费；如果您在此之后申请解约，保险公司将按合同条款约定支付退保金。由于各地监管有特殊要求，对于犹豫期的计算若存在特殊情况，以各地监管要求为准。</WithdrawalDescription>
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
				<InvestDateInd></InvestDateInd>
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
<xsl:variable name="tRisk" select="Risk[RiskCode!=MainRiskCode]" />
<Coverage>
	<xsl:attribute name="id">
		<xsl:choose>
			<!-- 主副险标志 -->
			<xsl:when test="RiskCode=MainRiskCode">
				<xsl:value-of select="concat('Cov_', '1')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat('Cov_', '2')" />
			</xsl:otherwise>
		</xsl:choose> 		
	</xsl:attribute>
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
		   <CashValues>
				<!-- 首年退保比例 -->
				<SurrenderPctFY />
				<!-- -第二年退保比例 -->
				<SurrenderPctSY />
				<!-- 现金价值循环次数 -->
				<CashValueCount><xsl:value-of select="count(CashValues/CashValue)"/></CashValueCount>
				<xsl:for-each select="CashValues/CashValue">	
					 <CashValue>
							<!-- 生存年金 -->
							<Live></Live>
							<!--疾病身故保险金 -->
							<IllDeathBenefitAmt />
							<!--意外身故保险金 -->
							<AciDeathBenefitAmt />
							<!-- 年末 -->
							<End><xsl:value-of select="EndYear"/></End>
							<!-- 年末现金价值 -->
							<Cash><xsl:value-of select="Cash"/></Cash>
							<!-- 保单年度 -->
							<Year />
							<!-- 交清保额 -->
							<PaidAmt />
						    <!-- 现金价值表说明 -->
							<CashValueDescription></CashValueDescription>
					   </CashValue>
				 </xsl:for-each>
			</CashValues>
			<!-- 红利保额保单年度末现金价值表 -->
			<BonusValues>   
						<!-- 现金价值数目 -->			
						<BonusValueCount><xsl:value-of select="count(DeductionValues/DeductionValue)"/></BonusValueCount>
							<xsl:for-each select="DeductionValues/DeductionValue">
								<BonusValue>
										<!-- 年末 -->
										<End><xsl:value-of select="EndYear"/></End>
										<!-- 年末现金价值 -->
										<Cash><xsl:value-of select="EndYearAmnt"/></Cash>
								</BonusValue>
							</xsl:for-each>
			</BonusValues>
			<!-- 险种加费 -->
			<ExcessPremAmt/>  						
			<xsl:choose>
				<xsl:when test="PayEndYear=1000"><!-- 趸交传1000 Y -->
					<!-- 缴费年期/年龄类型--> 
					<PaymentDurationMode>5</PaymentDurationMode>
					<!-- 缴费年期/年龄--> 
					<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>
				</xsl:when>
				<xsl:otherwise>
					<!-- 缴费年期/年龄类型--> 
					<PaymentDurationMode><xsl:apply-templates select="PayEndYearFlag" /></PaymentDurationMode>
					<!-- 缴费年期/年龄--> 
					<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>	
				</xsl:otherwise>
			</xsl:choose>
			<!-- 领取起始年龄 -->	   
			<PayoutStart/>
			<!-- 领取终止年龄 -->				
			<PayoutEnd/>				
		    <!-- 保险年期/年龄标志 -->
			<DurationMode> <xsl:apply-templates select="InsuYearFlag" /></DurationMode><!-- 还需要确定一下 -->
			<!-- 保险年期/年龄 -->
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage>

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
                <FullName>中韩人寿保险有限公司</FullName>
                <GovtID></GovtID>
                <Organization/>
                <Address>
                    <Line1>中国浙江省杭州市江干区新业路8号UDC时代大厦A座24层</Line1>
                    <Line2/>
                    <City>杭州</City>
                    <AddressStateTC/>
                    <Zip>310016</Zip>
                </Address>
                <Phone>
                <!-- 当事人电话类别 -->
                    <PhoneTypeCode tc="2">2</PhoneTypeCode>
                    <AreaCode/>
                    <DialNumber>4009-800-800</DialNumber>
                </Phone>
                <Carrier>
					<!-- 公司代码 -->
					<CarrierCode>050</CarrierCode>
				</Carrier>
            </Party>
	</xsl:template>
	
	<!--险种的特别约定信息 -->
	<xsl:template name="SpecContent" match="ContNo">
	<SpecialClause>	
	<!-- 只有主险时 -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode=''">(1)意外身故保额=100%个人账户价值+10%个人账户价值(10%个人账户价值最高至人民币100万元)；(2) 非意外身故保额=100%个人账户价值+5%个人账户价值(5%个人账户价值最高至人民币100万元)。</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='' or /TranData/Body/Risk/RiskCode='NHY' or /TranData/Body/Risk/RiskCode=''">附加险的保险金额按您个人账户价值的105%计算。</xsl:when>
			<xsl:otherwise/>
		</xsl:choose>
	</xsl:if>
		</SpecialClause>
	</xsl:template>
	
	<!-- 动态打印信息 -->
	<xsl:template name="BasePlanPrintInfo" match="ProposalPrtNo">
	<!-- 只有主险 -->
	<xsl:if test="count(/TranData/Body/Risk)='1'">
		<xsl:choose>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231302' or /TranData/Body/Risk/RiskCode='' ">
				<BasePlanPrintInfo1>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo1>
	            <BasePlanPrintInfo2>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo2>
	            <BasePlanPrintInfo3>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo3>
	            <BasePlanPrintInfo4>可向公司来电垂询。</BasePlanPrintInfo4>
	            <BasePlanPrintInfo5>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo5>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231301' or /TranData/Body/Risk/RiskCode='' ">
				<BasePlanPrintInfo1>附加险的保险金额按您个人账户价值的105%计算。</BasePlanPrintInfo1>
				<BasePlanPrintInfo2>*保险合同解除时，本公司向投保人退还本合同的现金价值。</BasePlanPrintInfo2>
				<BasePlanPrintInfo3>现金价值的数额按合同解除当日本合同的现金价值计算。</BasePlanPrintInfo3>
				<BasePlanPrintInfo4>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值,</BasePlanPrintInfo4>
				<BasePlanPrintInfo5>可向公司来电垂询。</BasePlanPrintInfo5>
				<BasePlanPrintInfo6>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。</BasePlanPrintInfo6>
			</xsl:when>
			<xsl:when test="/TranData/Body/Risk/RiskCode='231303'">
				<BasePlanPrintInfo1>(1)意外身故保额=100%个人账户价值+10%个人账户价值(10%个人账户价值最高至人民币100万元)；(2) 非意外身故保额=100%个人账户价值+5%个人账户价值(5%个人账户价值最高至人民币100万元)。</BasePlanPrintInfo1>
			</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
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
	<xsl:when test=".=231201">001</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">002</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">003</xsl:when>	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=221201">006</xsl:when>	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">007</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=221301">009</xsl:when>    <!-- 中韩悦未来年金险-->
	<xsl:when test=".=231302">010</xsl:when>    <!-- 中韩永利年年年金保险（分红型）-->
	<xsl:when test=".=221203">011</xsl:when> 	<!-- 中韩悦无忧两全保险-->
	<xsl:when test=".=225501">012</xsl:when> 	<!-- 中韩附加悦无忧重大疾病保险-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


   <!-- 返回缴费方式 -->
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

<!-- 自动垫交/减额交清标志 -->

<xsl:template name="auto_payflag">
    <xsl:param name="AutoPayFlag"></xsl:param> 
	<xsl:choose>
		<xsl:when test="AutoPayFlag=0">N</xsl:when>	<!-- 不垫交 -->
		<xsl:when test="AutoPayFlag=1">Y</xsl:when>	<!-- 垫交 -->
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
	<xsl:when test=".='A'">1</xsl:when>	<!-- 交至某确定年龄-->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年交 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月交 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日交 -->
															<!-- 趸交  PayEndYear 1000,PayEndYearFlag Y-->
															<!-- 终交费 -->
															<!-- 不定期交 -->
															<!-- 半年 -->
															<!-- 其他-->
</xsl:choose>
</xsl:template>  

<xsl:template  match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 保至某确定年龄 -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年保 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月保 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日保 -->
	<xsl:when test=".='A'">5</xsl:when>	<!-- 保终身 -->
	<xsl:when test=".='9'">9</xsl:when>	<!-- 其他-->
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
	
	 
<!-- 缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B-->
<xsl:template name="tran_paymode" match="PayMode">
<xsl:choose>
	<xsl:when test=".='B'">1</xsl:when>	<!-- 银行转账 -->
	<xsl:when test=".='B'">3</xsl:when>	<!-- 银行代扣 -->
</xsl:choose>
</xsl:template>	
	
 <!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'">    <!--  悦未来年金险现价可能超100 -->
          <xsl:variable name="LeiShu" select="25"></xsl:variable>	
          <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<TextRowContent><xsl:text>  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></TextRowContent>
		</xsl:for-each>
        </xsl:if>
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode= '221301' ">     <!--  悦未来年金险现价可能超100 -->
            <xsl:variable name="LeiShu" select="33"></xsl:variable>	
		    <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<TextRowContent><xsl:text>  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></TextRowContent>
		</xsl:for-each>
        </xsl:if>
</xsl:template>	
	
</xsl:stylesheet>


