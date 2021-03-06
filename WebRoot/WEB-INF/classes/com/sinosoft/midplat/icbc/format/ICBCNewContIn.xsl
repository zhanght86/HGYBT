<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TranData>
	<xsl:apply-templates select="TXLife/TXLifeRequest" /> 
	<Body>  
		
		
		<!-- 投保人 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Relation[RelationRoleCode=80]" />
		<!-- 被保人 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Relation[RelationRoleCode=81]"/>
		<!-- 受益人 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Relation[RelationRoleCode=82]" />
		<!-- 险种信息 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy/Life/Coverage"/>
		<!-- 贷款信息 -->
		<xsl:apply-templates select="TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension"/>
	</Body>
</TranData>
</xsl:template> 

<xsl:template name="Head" match="TXLifeRequest">

<!-- 报文头结点 -->
<Head>
	<TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(TransExeDate)"/></TranDate>
	<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
	<ZoneNo><xsl:value-of select="OLifEExtension/RegionCode"/></ZoneNo>
	<NodeNo>
 			<xsl:value-of select="OLifEExtension/RegionCode"/>
 			 <xsl:value-of select="OLifEExtension/Branch" /> 
 	 </NodeNo>
	
	
	<TellerNo><xsl:value-of select="OLifEExtension/Teller"/></TellerNo>
	<TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
			
			
    
	<xsl:copy-of select="../Head/*"/>
	<BankCode>0101</BankCode>
	<!-- bankcode 取自icbc.xml   TranCom outcode="01">1</TranCom  与银行端传进来的bankcode无关 -->
</Head> 
</xsl:template>

<!-- 保单基本信息 -->
<xsl:template name="Body" match="Relation[RelationRoleCode=80]">
			<xsl:variable name="PartyID" select="@RelatedObjectID" />
			<xsl:variable name="HoldingID" select="@OriginatingObjectID" />
			<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
			<xsl:variable name="HoldingNode" select="../Holding[@id=$HoldingID]" />
<ProposalPrtNo><xsl:value-of select="$HoldingNode/Policy/ApplicationInfo/HOAppFormNumber" /></ProposalPrtNo>
<SaleChannel><xsl:value-of select="/TXLife/TXLifeRequest/OLifEExtension/SourceType" /></SaleChannel>
<ContPrtNo><xsl:value-of select="../FormInstance[FormName='2']/ProviderFormNumber" /></ContPrtNo>
<PolApplyDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($HoldingNode/Policy/ApplicationInfo/SubmissionDate)" /></PolApplyDate>
<!-- 账户姓名 -->
<AccName><xsl:value-of select="$PartyNode/FullName"/></AccName>
<!-- 账户银行代码 -->
<AccBankCode/>
<!-- 交费帐号 -->
<AccNo><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding[@id='Acct_1']/Banking/AccountNumber"/></AccNo>
<!-- 保单传送方式 -->
<GetPolMode><xsl:apply-templates select="$HoldingNode/Policy/OLifEExtension/PolicyDeliveryMethod" /></GetPolMode>
<JobNotice><xsl:value-of select="$HoldingNode/Policy/OLifEExtension/OccupationIndicator" /></JobNotice> 
<!-- 销售人员工号 -->
 <SaleStaff><xsl:value-of select="OLifEExtension/Teller" /></SaleStaff>
 <!-- 销售人员姓名  -->
 <SaleName><xsl:value-of select="$HoldingNode/Policy/BankManagerName" /></SaleName>
 <!-- 销售人员资格证号  -->
 <SaleCertNo><xsl:value-of select="$HoldingNode/Policy/BankManagerAgentId" /></SaleCertNo>
 <HealthNotice><xsl:apply-templates select="$HoldingNode/Policy/OLifEExtension/HealthIndicator" /></HealthNotice> 
<!-- 投保人 -->

<Appnt>
	<Name><xsl:value-of select="$PartyNode/FullName" /></Name>
	<Sex><xsl:apply-templates select="$PartyNode/Person/Gender" /></Sex>
	<Birthday><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode/Person/BirthDate)" /></Birthday>
	<IDType><xsl:apply-templates select="$PartyNode/GovtIDTC" /></IDType>
	<IDNo><xsl:value-of select="$PartyNode/GovtID" /></IDNo>
	<Address><xsl:value-of	select="$PartyNode/Address[AddressTypeCode='1']/Line1" /></Address>
   	<ZipCode><xsl:value-of	select="$PartyNode/Address[AddressTypeCode='1']/Zip" /></ZipCode>
    <Phone><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='1']/DialNumber" /></Phone>
    <Mobile><xsl:value-of select="$PartyNode/Phone[PhoneTypeCode='3']/DialNumber" /></Mobile>
    <Email><xsl:value-of select="$PartyNode/EMailAddress/AddrLine" /></Email>
	<JobCode><xsl:value-of select="$PartyNode/Person/OccupationType" /></JobCode>  <!-- 核心默认值 代码为3010101 一般内勤，20150105 新增产品需要职业代码，工行采用核心编码，不需转换-->
	<Company><xsl:value-of select="$PartyNode/Person/Company" /></Company>
	<Nationality><xsl:apply-templates select="$PartyNode/Person/Nationality" /></Nationality>
	<Stature><xsl:value-of select="$PartyNode/Person/Stature" /></Stature> <!-- 身高(cm)  空值-->
    <Weight><xsl:value-of select="$PartyNode/Person/Weight" /></Weight> <!-- 体重(g)  空值-->
    <!-- 收入 -->
	<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan($PartyNode/Person/EstSalary)" /></YearSalary>
	<!-- 证件有效期 -->
	<IdExpDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode/GovtTermDate)"/></IdExpDate>
    <MaritalStatus></MaritalStatus> <!-- 婚否  空值-->
	
   <RelaToInsured>
   <xsl:call-template name="tran_RelaToInsured">
 	 <xsl:with-param name="RelaIns">
	 <xsl:variable name="InsuredPartyID" select="../Relation[RelationRoleCode='81']/@RelatedObjectID"/>
	  <xsl:value-of select="../Relation[@OriginatingObjectID=$InsuredPartyID and @RelatedObjectID=$PartyID]/RelationRoleCode"/>
	  </xsl:with-param>
   </xsl:call-template>
   </RelaToInsured>
   <!-- 投保人家庭年收入 -->
   <FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan($PartyNode/Person/FamilyEstSalary)" /></FamilyYearSalary>
   <!-- 投保人居民类型 -->
   <DenType><xsl:value-of select="$PartyNode/Person/LiveZone" /></DenType>
</Appnt>
			
</xsl:template> 
 
<!-- 被保人 -->
<xsl:template name="Insured" match="Relation[RelationRoleCode=81]">
<xsl:variable name="PartyID2" select="@RelatedObjectID" />
<xsl:variable name="PartyNode2" select="../Party[@id=$PartyID2]" />

<Insured>
	<Name><xsl:value-of select="$PartyNode2/FullName" /></Name>
	<Sex><xsl:apply-templates select="$PartyNode2/Person/Gender" /></Sex>
	<Birthday><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode2/Person/BirthDate)" /></Birthday>
	<IDType><xsl:apply-templates select="$PartyNode2/GovtIDTC" /></IDType>
	<IDNo><xsl:value-of select="$PartyNode2/GovtID" /></IDNo>
	<Address><xsl:value-of	select="$PartyNode2/Address[AddressTypeCode='1']/Line1" /></Address>
   	<ZipCode><xsl:value-of	select="$PartyNode2/Address[AddressTypeCode='1']/Zip" /></ZipCode>
    <Phone><xsl:value-of select="$PartyNode2/Phone[PhoneTypeCode='1']/DialNumber" /></Phone>
    <Mobile><xsl:value-of select="$PartyNode2/Phone[PhoneTypeCode='3']/DialNumber" /></Mobile>
    <Email><xsl:value-of select="$PartyNode2/EMailAddress/AddrLine" /></Email>
	<JobCode>3010101</JobCode>  <!-- 核心默认值 代码为3010101 -->
	<Company><xsl:value-of select="$PartyNode2/Person/Company" /></Company>
	<Nationality><xsl:apply-templates select="$PartyNode2/Person/Nationality" /></Nationality>
	<Stature><xsl:value-of select="$PartyNode2/Person/Stature" /></Stature> <!-- 身高(cm)  空值-->
    <Weight><xsl:value-of select="$PartyNode2/Person/Weight" /></Weight> <!-- 体重(g)  空值-->
    <!-- 收入 -->
	<YearSalary><xsl:value-of select="$PartyNode2/Person/EstSalary" /></YearSalary>
	<!-- 证件有效期 -->
	<IdExpDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode2/GovtTermDate)"/></IdExpDate>
    <MaritalStatus></MaritalStatus> <!-- 婚否  空值-->
</Insured>
</xsl:template>

<!-- 受益人 -->
<xsl:template name="Bnf" match="Relation[RelationRoleCode=82]">
<xsl:variable name="PartyID" select="@RelatedObjectID" />
<xsl:variable name="PartyNode" select="../Party[@id=$PartyID]" />
<xsl:variable name="HoldingID3" select="@OriginatingObjectID" />
<xsl:variable name="HoldingNode3" select="../Holding[@id=$HoldingID3]/Policy" />
	

	<xsl:choose> 
		<xsl:when test="../Holding/Policy/OLifEExtension/BeneficiaryIndicator='N'">
		<Bnf>
			<Type>1</Type>	<!-- 默认为“1-死亡受益人” -->
			<Grade><xsl:value-of select="Sequence" /></Grade>
			<Name><xsl:value-of select="$PartyNode/FullName" /></Name>
			<Sex><xsl:apply-templates select="$PartyNode/Person/Gender" /></Sex>
			<Birthday><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8($PartyNode/Person/BirthDate)" /></Birthday>
			<IDType><xsl:apply-templates select="$PartyNode/GovtIDTC" /></IDType>
			<IDNo><xsl:value-of select="$PartyNode/GovtID" /></IDNo>
			<Lot><xsl:value-of select="InterestPercent" /></Lot>
			<RelaToInsured>
			 	<xsl:call-template name="tran_RelaToInsured">
 				<xsl:with-param name="RelaIns">
					<xsl:variable name="InsuredPartyID" select="../Relation[RelationRoleCode='81']/@RelatedObjectID" />
					<xsl:value-of select="../Relation[@OriginatingObjectID=$InsuredPartyID and @RelatedObjectID=$PartyID]/RelationRoleCode" />
 				 </xsl:with-param>	
			 </xsl:call-template>
			</RelaToInsured>
			<BeneficType>N</BeneficType> 
		</Bnf>
		</xsl:when> 
	</xsl:choose>  

</xsl:template>

<!-- 险种 -->
<xsl:template name="Risk" match="Coverage">
<Risk> 

			<!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="ProductCode" /></RiskCode>
			<!-- 主险险种代码 -->
			<MainRiskCode><xsl:apply-templates select="ProductCode"/></MainRiskCode>
			<!-- 保额  借贷险的时候取贷款信息部分保险金额-->
			<xsl:choose>
				<xsl:when  test = "ProductCode='004' or ProductCode='008'">
				<Amnt><xsl:value-of select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/OLifEExtension/FaceAmount"/></Amnt>
				</xsl:when>
				<xsl:otherwise>
				<Amnt><xsl:value-of select="InitCovAmt"/></Amnt>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- 保费 -->
			<Prem><xsl:value-of select="ModalPremAmt"/></Prem>
			<!-- 投保份数 -->
			<Mult><xsl:value-of select="IntialNumberOfUnits"/></Mult>
			<!-- 缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B
			<PayMode><xsl:apply-templates select="$HoldingNode3/PaymentMethod" /></PayMode>-->
			<PayMode>B</PayMode>
			
			<!-- 缴费频次 -->
			<xsl:choose>
			<xsl:when test="ProductCode='145201'">
			<PayIntv>0</PayIntv>
			</xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="/TXLife/TXLifeRequest/OLifE/Holding/Policy/PaymentMode" /></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			<!--缴费期间-->	     
	<xsl:choose> 
	<xsl:when test="(OLifEExtension/PaymentDurationMode=5) and (OLifEExtension/DurationMode=5)">	<!-- 趸交 -->
			<!-- 缴费年期年龄标志 -->
			<PayEndYearFlag><xsl:apply-templates select="OLifEExtension/DurationMode" /></PayEndYearFlag>
			<!-- 缴费年期年龄 -->
			<PayEndYear>1000</PayEndYear>
		</xsl:when>
		<xsl:when test="(OLifEExtension/PaymentDurationMode=5) and (OLifEExtension/DurationMode != 5)">	<!-- 趸交 -->
			<PayEndYearFlag><xsl:apply-templates select="OLifEExtension/DurationMode" /></PayEndYearFlag>   
			<PayEndYear>1000</PayEndYear>                    <!-- modified in 20121029 -->
		</xsl:when>
		<xsl:otherwise>	<!-- 其他 -->
			<PayEndYearFlag><xsl:apply-templates select="OLifEExtension/PaymentDurationMode" /></PayEndYearFlag>
			<PayEndYear><xsl:value-of select="OLifEExtension/PaymentDuration" /></PayEndYear>
		</xsl:otherwise>
	</xsl:choose>
			

			<!--保险期间-->	 
	<xsl:choose>   
		<xsl:when test="OLifEExtension/DurationMode=5">	<!-- 保终身 -->
			<!-- 保险年期年龄标志 -->
			<InsuYearFlag>Y</InsuYearFlag>
			<!-- 保险年期年龄 -->
			<InsuYear>1000</InsuYear>         <!-- 保终身暂时修改，待联调时确认！ -->
		</xsl:when> 
		   
		<xsl:otherwise>	
			<InsuYearFlag><xsl:apply-templates select="OLifEExtension/DurationMode" /></InsuYearFlag>
			<InsuYear><xsl:value-of select="OLifEExtension/Duration" /></InsuYear>
		</xsl:otherwise>
		      
	   
	</xsl:choose> 

	<!-- 20150204lilu 工行网银和终端红利领取方式字段为BenefitMode，柜台为DivType -->
	<xsl:choose>		
		<xsl:when test="/TXLife/TXLifeRequest/OLifEExtension/SourceType !=0">	<!-- 非柜台 -->
			<BonusGetMode><xsl:apply-templates select="//BenefitMode" /></BonusGetMode>
		</xsl:when> 
		<xsl:otherwise>	
			<BonusGetMode><xsl:apply-templates select="../DivType" /></BonusGetMode>
		</xsl:otherwise>
	</xsl:choose> 
	
	<FullBonusGetMode></FullBonusGetMode> <!-- 满期领取金领取方式 传空-->
    
    <xsl:choose>
    	<xsl:when test="OLifEExtension/PayoutStart = '99'">
    	<GetYearFlag>E</GetYearFlag> <!-- 领取年龄年期标志-->
    	<GetYear>0</GetYear>   <!-- 领取起始年龄 -->	
    	</xsl:when>
    	<xsl:otherwise><GetYearFlag>A</GetYearFlag>
    	<GetYear><xsl:value-of select="OLifEExtension/PayoutStart" /></GetYear>   <!-- 领取起始年龄 -->
    	</xsl:otherwise>
    </xsl:choose>
    
    	
    <GetTerms><xsl:value-of select="OLifEExtension/PayoutDuration" /></GetTerms><!-- 领取年期 -->
    
    	
		<!-- 领取方式 -->		
    <GetIntv><xsl:apply-templates select="//BenefitMode" /></GetIntv>		
    <GetBankCode></GetBankCode> <!-- 领取银行编码 传空-->
    <GetBankAccNo></GetBankAccNo> <!-- 领取银行账户 传空-->        
	<GetAccName></GetAccName> <!-- 领取银行户名  传空-->  
	         
</Risk> 
</xsl:template>

<!--贷款信息-->
<xsl:template match="OLifEExtension">
		<Loan>
			<!-- 贷款合同号 -->
			<LoanNo><xsl:value-of select="ContractNo"/></LoanNo>
			<!-- 贷款机构 -->
			<LoanBank>中国工商银行</LoanBank>
			<!-- 贷款日期 -->
			<LoanDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(LoanStartDate)"/></LoanDate>
			<!-- 贷款到期日 -->
			<LoanEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(LoanEndDate)"/></LoanEndDate>
			<!-- 贷款种类 工行没有，默认为1 一般商业贷款-->
			<LoanType>1</LoanType>
			<!-- 贷款账号 -->
			<AccNo><xsl:value-of select="LoanAccountNo"/></AccNo>
			<!-- 贷款金额 -->
			<xsl:choose><!-- 这里判断choose下，不然空的话在yuanToFen的时候变成0了，数据不原汁原味了 -->
			<xsl:when test = "LoanAmount=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="LoanAmount"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- 保险起始日 -->
			<InsuDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(ContractEffDate)"/></InsuDate>
			<!-- 保险期满日 -->
			<InsuEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(ContractEndDate)"/></InsuEndDate>
		</Loan>
</xsl:template>


	<!-- 性别 -->
<xsl:template name="tran_sex" match="Gender">
<xsl:choose> 
	<xsl:when test=".=1">0</xsl:when>	<!-- 男 -->
	<xsl:when test=".=2">1</xsl:when>	<!-- 女 -->
	<xsl:when test=".=3">2</xsl:when>	<!-- 默认 -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>  
</xsl:template>

	<!-- 国籍 -->
<xsl:template name="tran_nationality" match="Nationality">
<xsl:choose> 
	<xsl:when test=".=4">AFG</xsl:when>	<!-- 阿富汗 -->
	<xsl:when test=".=8">ALB</xsl:when>	<!-- 阿尔巴尼亚 -->
	<xsl:when test=".=10"></xsl:when>	<!-- 南极洲 -->
	<xsl:when test=".=12">ALG</xsl:when>	<!-- 阿尔及利亚 -->
	<xsl:when test=".=16"></xsl:when>	<!-- 美属萨摩亚 -->
	<xsl:when test=".=20">AND</xsl:when>	<!-- 安道尔 -->
	<xsl:when test=".=24">AFG</xsl:when>	<!-- 安哥拉 -->
	<xsl:when test=".=28"></xsl:when>	<!-- 安提瓜和巴布达 -->
	<xsl:when test=".=31">AZE</xsl:when>	<!-- 阿塞拜疆 -->
	<xsl:when test=".=32">ARG</xsl:when>	<!-- 阿根廷 -->
	<xsl:when test=".=36">AUS</xsl:when>	<!-- 澳大利亚 -->
	<xsl:when test=".=40">AUT</xsl:when>	<!-- 奥地利 -->
	<xsl:when test=".=44"></xsl:when>	<!-- 巴哈马 -->
	<xsl:when test=".=48">BRN</xsl:when>	<!-- 巴林 -->
	<xsl:when test=".=50">BAN</xsl:when>	<!-- 孟加拉国 -->
	<xsl:when test=".=51">ARM</xsl:when>	<!-- 亚美尼亚 -->
	<xsl:when test=".=52">BAR</xsl:when>	<!-- 巴巴多斯 -->
	<xsl:when test=".=56">BEL</xsl:when>	<!-- 比利时 -->
	<xsl:when test=".=60">BER</xsl:when>	<!-- 百慕大 -->
	<xsl:when test=".=64"></xsl:when>	<!-- 不丹 -->
	<xsl:when test=".=68">BOL</xsl:when>	<!-- 玻利维亚 -->
	<xsl:when test=".=70">AFG</xsl:when>	<!-- 波黑 -->
	<xsl:when test=".=72">BOT</xsl:when>	<!-- 博茨瓦纳 -->
	<xsl:when test=".=74"></xsl:when>	<!-- 布维岛 -->
	<xsl:when test=".=76">BAS</xsl:when>	<!-- 巴西 -->
	<xsl:when test=".=84"></xsl:when>	<!-- 伯利兹 -->
	<xsl:when test=".=86"></xsl:when>	<!-- 英属印度洋领地 -->
	<xsl:when test=".=90"></xsl:when>	<!-- 所罗门群岛 -->
	<xsl:when test=".=92">IVB</xsl:when>	<!-- 英属维尔京群岛 -->
	<xsl:when test=".=96">BRU</xsl:when>	<!-- 文莱 -->
	<xsl:when test=".=100">BUL</xsl:when>	<!--保加利亚 -->
	<xsl:when test=".=104"></xsl:when>	<!-- 缅甸 -->
	<xsl:when test=".=108"></xsl:when>	<!-- 布隆迪 -->
	<xsl:when test=".=112">BLR</xsl:when>	<!-- 白俄罗斯 -->
	<xsl:when test=".=116"></xsl:when>	<!-- 柬埔寨 -->
	<xsl:when test=".=120"></xsl:when>	<!-- 喀麦隆 -->
	<xsl:when test=".=124">CAN</xsl:when>	<!-- 加拿大 -->
	<xsl:when test=".=132"></xsl:when>	<!-- 佛得角 -->
	<xsl:when test=".=136"></xsl:when>	<!-- 开曼群岛 -->
	<xsl:when test=".=140"></xsl:when>	<!-- 中非 -->
	<xsl:when test=".=144">SLK</xsl:when>	<!-- 斯里兰卡 -->
	<xsl:when test=".=148"></xsl:when>	<!-- 乍得 -->
	<xsl:when test=".=152">CHI</xsl:when>	<!-- 智利 -->
	<xsl:when test=".=156">CHN</xsl:when>	<!-- 中国 -->
	<xsl:when test=".=158"></xsl:when>	<!-- 中国台湾 -->
	<xsl:when test=".=162"></xsl:when>	<!-- 圣诞岛 -->
	<xsl:when test=".=166"></xsl:when>	<!-- 科科斯（基林）群岛 -->
	<xsl:when test=".=170">COL</xsl:when>	<!-- 哥伦比亚 -->
	<xsl:when test=".=174">AFG</xsl:when>	<!-- 科摩罗 -->
	<xsl:when test=".=175"></xsl:when>	<!-- 马约特 -->
	<xsl:when test=".=178"></xsl:when>	<!-- 刚果（布） -->
	<xsl:when test=".=180"></xsl:when>	<!-- 刚果（金） -->
	<xsl:when test=".=184"></xsl:when>	<!-- 库克群岛 -->
	<xsl:when test=".=188">CRC</xsl:when>	<!-- 哥斯达黎加 -->
	<xsl:when test=".=191">CRO</xsl:when>	<!-- 克罗地亚 -->
	<xsl:when test=".=192">CUB</xsl:when>	<!-- 古巴 -->
	<xsl:when test=".=196">CYP</xsl:when>	<!-- 塞浦路斯 -->
	<xsl:when test=".=203"></xsl:when>	<!-- 捷克 -->
	<xsl:when test=".=204"></xsl:when>	<!-- 贝宁 -->
	<xsl:when test=".=208">DEN</xsl:when>	<!-- 丹麦 -->
	<xsl:when test=".=212"></xsl:when>	<!-- 多米尼克 -->
	<xsl:when test=".=214">DOM</xsl:when>	<!-- 多米尼加 -->
	<xsl:when test=".=218">ECU</xsl:when>	<!-- 厄瓜多尔 -->
	<xsl:when test=".=222"></xsl:when>	<!-- 萨尔瓦多 -->
	<xsl:when test=".=226"></xsl:when>	<!-- 赤道几内亚 -->
	<xsl:when test=".=231">ETH</xsl:when>	<!-- 埃塞俄比亚 -->
	<xsl:when test=".=232"></xsl:when>	<!-- 厄立特里亚 -->
	<xsl:when test=".=233">EST</xsl:when>	<!-- 爱沙尼亚 -->
	<xsl:when test=".=234">FAI</xsl:when>	<!-- 法罗群岛 -->
	<xsl:when test=".=238"></xsl:when>	<!-- 福克兰群岛（马尔维纳斯） -->
	<xsl:when test=".=239"></xsl:when>	<!-- 南乔治亚岛和南桑德韦奇岛 -->
	<xsl:when test=".=242"></xsl:when>	<!-- 斐济 -->
	<xsl:when test=".=246">FIN</xsl:when>	<!-- 芬兰 -->
	<xsl:when test=".=250">FRA</xsl:when>	<!-- 法国 -->
	<xsl:when test=".=254"></xsl:when>	<!-- 法属圭亚那 -->
	<xsl:when test=".=258"></xsl:when>	<!-- 法属波利尼西亚 -->
	<xsl:when test=".=260"></xsl:when>	<!-- 法属南部领地 -->
	<xsl:when test=".=262"></xsl:when>	<!-- 吉布提 -->
	<xsl:when test=".=266"></xsl:when>	<!-- 加蓬 -->
	<xsl:when test=".=268">GEO</xsl:when>	<!-- 格鲁吉亚 -->
	<xsl:when test=".=270"></xsl:when>	<!-- 冈比亚 -->
	<xsl:when test=".=275">PLE</xsl:when>	<!-- 巴勒斯坦 -->
	<xsl:when test=".=276">DEU</xsl:when>	<!-- 德国 -->
	<xsl:when test=".=288">DG</xsl:when>	<!-- 加纳 -->
	<xsl:when test=".=292"></xsl:when>	<!-- 直布罗陀 -->
	<xsl:when test=".=296"></xsl:when>	<!-- 基里巴斯 -->
	<xsl:when test=".=300">GRE</xsl:when>	<!-- 希腊 -->
	<xsl:when test=".=304"></xsl:when>	<!-- 格陵兰 -->
	<xsl:when test=".=308"></xsl:when>	<!-- 格林纳达 -->
	<xsl:when test=".=312"></xsl:when>	<!-- 瓜德罗普 -->
	<xsl:when test=".=316"></xsl:when>	<!-- 关岛 -->
	<xsl:when test=".=320"></xsl:when>	<!-- 危地马拉 -->
	<xsl:when test=".=324"></xsl:when>	<!-- 几内亚 -->
	<xsl:when test=".=328"></xsl:when>	<!-- 圭亚那 -->
	<xsl:when test=".=332"></xsl:when>	<!-- 海地 -->
	<xsl:when test=".=334"></xsl:when>	<!-- 赫德岛和麦克唐纳岛 -->
	<xsl:when test=".=336"></xsl:when>	<!-- 梵蒂冈 -->
	<xsl:when test=".=340">HON</xsl:when>	<!-- 洪都拉斯 -->
	<xsl:when test=".=344"></xsl:when>	<!-- 中国香港 -->
	<xsl:when test=".=348">HUN</xsl:when>	<!-- 匈牙利 -->
	<xsl:when test=".=352">ISL</xsl:when>	<!-- 冰岛 -->
	<xsl:when test=".=356">IND</xsl:when>	<!-- 印度 -->
	<xsl:when test=".=360">INA</xsl:when>	<!-- 印度尼西亚 -->
	<xsl:when test=".=364">IRI</xsl:when>	<!-- 伊朗 -->
	<xsl:when test=".=368">IRQ</xsl:when>	<!-- 伊拉克 -->
	<xsl:when test=".=372">IRL</xsl:when>	<!-- 爱尔兰 -->
	<xsl:when test=".=376">ISR</xsl:when>	<!-- 以色列 -->
	<xsl:when test=".=380">ITA</xsl:when>	<!-- 意大利 -->
	<xsl:when test=".=384"></xsl:when>	<!-- 科特迪瓦 -->
	<xsl:when test=".=388">JAM</xsl:when>	<!-- 牙买加 -->
	<xsl:when test=".=392">JAN</xsl:when>	<!-- 日本 -->
	<xsl:when test=".=398">KAZ</xsl:when>	<!-- 哈萨克斯坦 -->
	<xsl:when test=".=400"></xsl:when>	<!-- 约旦 -->
	<xsl:when test=".=404">KEN</xsl:when>	<!-- 肯尼亚 -->
	<xsl:when test=".=408"></xsl:when>	<!-- 朝鲜 -->
	<xsl:when test=".=410">KOR</xsl:when>	<!-- 韩国 -->
	<xsl:when test=".=414"></xsl:when>	<!-- 科威特 -->
	<xsl:when test=".=417"></xsl:when>	<!-- 吉尔吉斯斯坦 -->
	<xsl:when test=".=418"></xsl:when>	<!-- 老挝 -->
	<xsl:when test=".=422">LIB</xsl:when>	<!-- 黎巴嫩 -->
	<xsl:when test=".=426"></xsl:when>	<!-- 莱索托 -->
	<xsl:when test=".=428">LAT</xsl:when>	<!-- 拉脱维亚 -->
	<xsl:when test=".=430"></xsl:when>	<!-- 利比里亚 -->
	<xsl:when test=".=434">LBA</xsl:when>	<!-- 利比亚 -->
	<xsl:when test=".=438">LIE</xsl:when>	<!-- 列支敦士登 -->
	<xsl:when test=".=440">LTU</xsl:when>	<!-- 立陶宛 -->
	<xsl:when test=".=426">LUX</xsl:when>	<!-- 卢森堡 -->
	<xsl:when test=".=446"></xsl:when>	<!-- 中国澳门 -->
	<xsl:when test=".=450"></xsl:when>	<!-- 马达加斯加 -->
	<xsl:when test=".=454">MAW</xsl:when>	<!-- 马拉维 -->
	<xsl:when test=".=458">MY</xsl:when>	<!-- 马来西亚 -->
	<xsl:when test=".=462"></xsl:when>	<!-- 马尔代夫 -->
	<xsl:when test=".=466"></xsl:when>	<!-- 马里 -->
	<xsl:when test=".=470"></xsl:when>	<!-- 马耳他 -->
	<xsl:when test=".=474"></xsl:when>	<!-- 马提尼克 -->
	<xsl:when test=".=478"></xsl:when>	<!-- 毛里塔尼亚 -->
	<xsl:when test=".=480"></xsl:when>	<!-- 毛里求斯 -->
	<xsl:when test=".=484">MEX</xsl:when>	<!-- 墨西哥 -->
	<xsl:when test=".=492">MNC</xsl:when>	<!-- 摩纳哥 -->
	<xsl:when test=".=496">MGL</xsl:when>	<!-- 蒙古 -->
	<xsl:when test=".=498"></xsl:when>	<!-- 摩尔多瓦 -->
	<xsl:when test=".=500"></xsl:when>	<!-- 蒙特塞拉特 -->
	<xsl:when test=".=504">MAR</xsl:when>	<!-- 摩洛哥 -->
	<xsl:when test=".=508"></xsl:when>	<!-- 莫桑比亚 -->
	<xsl:when test=".=512"></xsl:when>	<!-- 阿曼 -->
	<xsl:when test=".=516">NAM</xsl:when>	<!-- 纳米比亚 -->
	<xsl:when test=".=520"></xsl:when>	<!-- 瑙鲁 -->
	<xsl:when test=".=524"></xsl:when>	<!-- 尼泊尔 -->
	<xsl:when test=".=528">NL</xsl:when>	<!-- 荷兰 -->
	<xsl:when test=".=530">AHO</xsl:when>	<!-- 荷属安的列斯 -->
	<xsl:when test=".=533"></xsl:when>	<!-- 阿鲁巴 -->
	<xsl:when test=".=540"></xsl:when>	<!-- 新喀里多尼亚 -->
	<xsl:when test=".=548"></xsl:when>	<!-- 瓦努阿图 -->
	<xsl:when test=".=554">NZL</xsl:when>	<!-- 新西兰 -->
	<xsl:when test=".=558">NCA</xsl:when>	<!-- 尼加拉瓜 -->
	<xsl:when test=".=562"></xsl:when>	<!-- 尼日尔 -->
	<xsl:when test=".=566">NGR</xsl:when>	<!-- 尼日利亚 -->
	<xsl:when test=".=570"></xsl:when>	<!-- 纽埃 -->
	<xsl:when test=".=574"></xsl:when>	<!-- 诺福克岛 -->
	<xsl:when test=".=578">NO</xsl:when>	<!-- 挪威-->
	<xsl:when test=".=580"></xsl:when>	<!-- 北马里亚纳 -->
	<xsl:when test=".=581"></xsl:when>	<!-- 美国本土外小岛屿 -->
	<xsl:when test=".=583"></xsl:when>	<!-- 密克罗尼西亚联邦 -->
	<xsl:when test=".=584"></xsl:when>	<!-- 马绍尔群岛 -->
	<xsl:when test=".=585"></xsl:when>	<!-- 帕劳 -->
	<xsl:when test=".=586"></xsl:when>	<!-- 巴基斯坦 -->
	<xsl:when test=".=591">PAN</xsl:when>	<!-- 巴拿马 -->
	<xsl:when test=".=598">PNG</xsl:when>	<!-- 巴布亚新几内亚 -->
	<xsl:when test=".=600">PAR</xsl:when>	<!-- 巴拉圭 -->
	<xsl:when test=".=604">PER</xsl:when>	<!-- 秘鲁 -->
	<xsl:when test=".=608">PHL</xsl:when>	<!-- 菲律宾 -->
	<xsl:when test=".=612"></xsl:when>	<!-- 皮特凯恩 -->
	<xsl:when test=".=616">PL</xsl:when>	<!-- 波兰 -->
	<xsl:when test=".=620">POR</xsl:when>	<!-- 葡萄牙 -->
	<xsl:when test=".=624"></xsl:when>	<!-- 几内亚比绍 -->
	<xsl:when test=".=626"></xsl:when>	<!-- 东帝汶 -->
	<xsl:when test=".=630"></xsl:when>	<!-- 波多黎各 -->
	<xsl:when test=".=634">QAT</xsl:when>	<!-- 卡塔尔 -->
	<xsl:when test=".=638"></xsl:when>	<!-- 留尼汪 -->
	<xsl:when test=".=642">ROM</xsl:when>	<!-- 罗马尼亚 -->
	<xsl:when test=".=643"></xsl:when>	<!-- 俄罗斯联邦 -->
	<xsl:when test=".=646">RWA</xsl:when>	<!-- 卢旺达 -->
	<xsl:when test=".=654"></xsl:when>	<!-- 圣赫勒拿 -->
	<xsl:when test=".=659"></xsl:when>	<!-- 圣基茨和尼维斯 -->
	<xsl:when test=".=660"></xsl:when>	<!-- 安圭拉 -->
	<xsl:when test=".=662"></xsl:when>	<!-- 圣卢西亚 -->
	<xsl:when test=".=666"></xsl:when>	<!-- 圣皮埃尔和密克隆 -->
	<xsl:when test=".=670"></xsl:when>	<!-- 圣文森特和格林纳丁斯 -->
	<xsl:when test=".=674">SMR</xsl:when>	<!-- 圣马力诺 -->
	<xsl:when test=".=678"></xsl:when>	<!-- 圣多美和普林西比 -->
	<xsl:when test=".=682"></xsl:when>	<!-- 沙特阿拉伯 -->
	<xsl:when test=".=686"></xsl:when>	<!-- 塞内加尔-->
	<xsl:when test=".=690">SEY</xsl:when>	<!-- 塞舌尔 -->
	<xsl:when test=".=694"></xsl:when>	<!-- 塞拉利昂 -->
	<xsl:when test=".=702">SG</xsl:when>	<!-- 新加坡 -->
	<xsl:when test=".=703">SVK</xsl:when>	<!-- 斯洛伐克 -->
	<xsl:when test=".=704">VIE</xsl:when>	<!-- 越南 -->
	<xsl:when test=".=705">SLO</xsl:when>	<!-- 斯洛文尼亚 -->
	<xsl:when test=".=706">SOM</xsl:when>	<!-- 索马里 -->
	<xsl:when test=".=710">SFA</xsl:when>	<!-- 南非 -->
	<xsl:when test=".=716">ZIM</xsl:when>	<!-- 津巴布韦 -->
	<xsl:when test=".=724">ESP</xsl:when>	<!-- 西班牙 -->
	<xsl:when test=".=732"></xsl:when>	<!-- 西撒哈拉 -->
	<xsl:when test=".=736">SUD</xsl:when>	<!-- 苏丹 -->
	<xsl:when test=".=740">SUR</xsl:when>	<!-- 苏里南 -->
	<xsl:when test=".=744"></xsl:when>	<!-- 斯瓦尔巴岛和扬马延岛 -->
	<xsl:when test=".=748"></xsl:when>	<!-- 斯威士兰 -->
	<xsl:when test=".=752">SWE</xsl:when>	<!-- 瑞典 -->
	<xsl:when test=".=756">SUI</xsl:when>	<!-- 瑞士 -->
	<xsl:when test=".=760">SYR</xsl:when>	<!-- 叙利亚 -->
	<xsl:when test=".=762">TJK</xsl:when>	<!-- 塔吉克斯坦 -->
	<xsl:when test=".=764">THA</xsl:when>	<!-- 泰国 -->
	<xsl:when test=".=768"></xsl:when>	<!-- 多哥 -->
	<xsl:when test=".=772"></xsl:when>	<!-- 托克劳 -->
	<xsl:when test=".=776">TO</xsl:when>	<!-- 汤加 -->
	<xsl:when test=".=780">TRI</xsl:when>	<!-- 特立尼达和多巴哥 -->
	<xsl:when test=".=784"></xsl:when>	<!-- 阿联酋 -->
	<xsl:when test=".=788">TUN</xsl:when>	<!-- 突尼斯 -->
	<xsl:when test=".=792"></xsl:when>	<!-- 土耳其 -->
	<xsl:when test=".=795">TKM</xsl:when>	<!-- 土库曼斯坦 -->
	<xsl:when test=".=796"></xsl:when>	<!-- 特克斯和凯科斯群岛 -->
	<xsl:when test=".=798"></xsl:when>	<!-- 图瓦卢 -->
	<xsl:when test=".=800">UGA</xsl:when>	<!-- 乌干达 -->
	<xsl:when test=".=804">UKR</xsl:when>	<!-- 乌克兰 -->
	<xsl:when test=".=807">MKD</xsl:when>	<!-- 马其顿 -->
	<xsl:when test=".=818">EGY</xsl:when>	<!-- 埃及 -->
	<xsl:when test=".=826">ENG</xsl:when>	<!-- 英国 -->
	<xsl:when test=".=834"></xsl:when>	<!-- 坦桑尼亚 -->
	<xsl:when test=".=840">USA</xsl:when>	<!-- 美国 -->
	<xsl:when test=".=850"></xsl:when>	<!-- 美属维尔京群岛 -->
	<xsl:when test=".=854"></xsl:when>	<!-- 布基纳法索 -->
	<xsl:when test=".=858">URU</xsl:when>	<!-- 乌拉圭 -->
	<xsl:when test=".=860">UZB</xsl:when>	<!-- 乌兹别克斯坦 -->
	<xsl:when test=".=862">VEN</xsl:when>	<!-- 委内瑞拉 -->
	<xsl:when test=".=876"></xsl:when>	<!-- 瓦利斯和富图纳 -->
	<xsl:when test=".=882"></xsl:when>	<!-- 萨摩亚 -->
	<xsl:when test=".=883"></xsl:when>	<!-- 塞尔维亚 -->
	<xsl:when test=".=887">YEM</xsl:when>	<!-- 也门 -->
	<xsl:when test=".=891">YUG</xsl:when>	<!-- 南斯拉夫 -->
	<xsl:when test=".=894">ZAM</xsl:when>	<!-- 赞比亚 -->
	<xsl:when test=".=895"></xsl:when>	<!-- 中途岛 -->
	<xsl:when test=".=999"></xsl:when>	<!-- 其它 -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>  
</xsl:template>

<xsl:template name="tran_health" match="HealthIndicator">
<xsl:choose>
	<xsl:when test=".='Y'">Y</xsl:when>	<!-- Y: 有健康告知 -->
	<xsl:when test=".='N'">N</xsl:when>	<!-- N: 无健康告知 -->
</xsl:choose>  
</xsl:template>

<!-- 证件类型 -->
<xsl:template name="tran_idtype" match="GovtIDTC">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=3">A</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=4">B</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=5">C</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=6">4</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=7">8</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=9">D</xsl:when>	<!-- 警官证  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
  
<!-- 关系 -->
 <xsl:template name="tran_RelaToInsured"> 
  <xsl:param name="RelaIns">0</xsl:param>  
	<xsl:if test="$RelaIns =1">02</xsl:if>	<!-- 配偶 -->
	<xsl:if test="$RelaIns =2">01</xsl:if>	<!-- 父母-->
	<xsl:if test="$RelaIns =3">03</xsl:if>	<!-- 子女 -->
	<xsl:if test="$RelaIns =4">04</xsl:if>	<!-- 祖父祖母 -->
	<xsl:if test="$RelaIns =5">04</xsl:if>	<!-- 孙子女 -->
	<xsl:if test="$RelaIns =6">06</xsl:if>	<!-- 兄弟姐妹 -->
	<xsl:if test="$RelaIns =7">06</xsl:if>	<!-- 其他亲属 -->
	<xsl:if test="$RelaIns =8">00</xsl:if>	<!-- 本人 -->
	<xsl:if test="$RelaIns =9">06</xsl:if>	<!-- 朋友 -->
	<xsl:if test="$RelaIns =99">06</xsl:if>	<!-- 其他 -->  
  </xsl:template> 

 
<!-- 险种代码 -->
<xsl:template name="tran_RiskCode" match="ProductCode">
<xsl:choose>
	<xsl:when test=".=001">231201</xsl:when> <!-- 中韩智赢财富两全保险（分红型）A款 -->		
	<xsl:when test=".=002">231202</xsl:when> <!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=003">231203</xsl:when> <!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=004">211901</xsl:when> <!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=005">231301</xsl:when> <!-- 中韩永裕丰年年金保险（分红型）-->
	<xsl:when test=".=006">221201</xsl:when> <!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=007">231204</xsl:when> <!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=008">211902</xsl:when> <!-- 中韩安赢借款人意外伤害保险A款 -->
	<xsl:when test=".=013">221301</xsl:when> <!-- 中韩悦未来年金险-->
	<xsl:when test=".=010">231302</xsl:when> <!-- 中韩永利年年年金保险（分红型）-->
	<xsl:when test=".=011">221203</xsl:when> <!-- 中韩悦无忧两全保险-->
	<xsl:when test=".=101">225501</xsl:when> <!-- 中韩附加悦无忧重大疾病保险-->
	<xsl:when test=".=014">121504</xsl:when> <!-- 中韩臻佑终身重大疾病保险-->
	<xsl:when test=".=102">145201</xsl:when> <!-- 附加定盈宝两全保险（万能型）-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

 
<!-- 缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B-->
<xsl:template name="tran_paymode" match="PaymentMethod">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- 银行转账 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 支票 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 银行代扣 -->
	<xsl:when test=".=4">4</xsl:when>	<!-- 现金 -->
	<xsl:when test=".=5">5</xsl:when>	<!-- 现金送款簿 -->
	<xsl:when test=".=6">6</xsl:when>	<!-- 内部转账 -->
	<xsl:when test=".=7">7</xsl:when>	<!-- Pos收款 -->
	<xsl:when test=".=9">9</xsl:when>	<!-- 其他 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 

<!-- 缴费频次 -->
<xsl:template name="tran_payintv" match="PaymentMode">
<xsl:choose> 
	<xsl:when test=".=1">12</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=2">1</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=3">6</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=4">3</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=5">0</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=6">-1</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=9">9</xsl:when>	<!-- 其他(YBT校验) -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 缴费年期/年龄类型 -->
<xsl:template name="tran_payendyearflag" match="PaymentDurationMode">
<xsl:choose>
	<xsl:when test=".=1">A</xsl:when>	<!-- 缴至某确定年龄 -->
	<xsl:when test=".=2">Y</xsl:when>	<!-- 年 -->
	<xsl:when test=".=3">M</xsl:when>	<!-- 月 -->
	<xsl:when test=".=4">D</xsl:when>	<!-- 日 -->
	<xsl:when test=".=6"></xsl:when>	<!-- 终缴费 -->
	<xsl:when test=".=7"></xsl:when>	<!-- 不定期缴 -->
	<xsl:when test=".=8"></xsl:when>	<!-- 半年 -->
	<xsl:when test=".=9"></xsl:when>	<!-- 其他 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 保险年龄年期标志 -->
<xsl:template name="tran_InsuYearFlag" match="DurationMode">
<xsl:choose>
	<xsl:when test=".=1">A</xsl:when>	<!-- 保至某确定年龄 -->
	<xsl:when test=".=2">Y</xsl:when>	<!-- 年保 -->
	<xsl:when test=".=3">M</xsl:when>	<!-- 月保 --> 
	<xsl:when test=".=4">D</xsl:when>	<!-- 日保 -->
	<xsl:when test=".=5">Y</xsl:when>	<!-- 保终身 -->
	<xsl:when test=".=9">9</xsl:when>	<!-- 其他YBT校验 -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>  
</xsl:template>
  
<!-- 红利领取方式的转换 --> 
<xsl:template name="tran_BonusGetMode" match="DivType">
<xsl:choose> 
	<xsl:when test=".=1">1</xsl:when>	<!-- 累积生息 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 领取现金 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 抵交保费 -->
	<xsl:when test=".=4">5</xsl:when>	<!-- 增额交清 -->
	<xsl:when test=".=5"></xsl:when>	<!-- 无红利 转为NULL -->
	<xsl:when test=".=6">4</xsl:when>	<!-- 增额红利？？？？？？？？？？？？？？？？ -->
	<xsl:when test=".=9">4</xsl:when>	<!-- 其他 -->
	<xsl:otherwise></xsl:otherwise>   
</xsl:choose> 
</xsl:template> 

<!-- 保单传送方式 -->
<xsl:template name="tran_GetPolMode" match="PolicyDeliveryMethod">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- 部门发送 -->
	<xsl:when test=".=2">1</xsl:when>	<!-- 邮寄或传递 -->
	<xsl:when test=".=3">1</xsl:when>	<!-- 上门快递 -->
	<xsl:when test=".=4">1</xsl:when>	<!-- 银行领取 -->
	<xsl:when test=".=5">1</xsl:when>	<!-- 区域中心渠道银行出单 -->
	<xsl:when test=".=6">1</xsl:when>	<!-- 区域中心渠道保险公司出单 -->
	<xsl:when test=".=7">1</xsl:when>	<!-- 银行侧提供电子保单 20150203lilu-->
	<xsl:when test=".=8">1</xsl:when>	<!-- 保险公司邮寄电子保单 20150203lilu-->
	<xsl:when test=".=9">1</xsl:when>	<!-- 其他 -->
	<xsl:when test=".=10">1</xsl:when>	<!-- 客户自行通过保险公司官方网站下载电子保单  20150203lilu -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>
</xsl:template> 
 
<!-- 领取方式 -->
<xsl:template name="tran_GetIntv" match="BenefitMode">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- 年领 -->
	<xsl:when test=".=2"></xsl:when>	<!-- 月领 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 泵领-->  
	<xsl:when test=".=4"></xsl:when>	<!-- 泵领 -->
	<xsl:when test=".=5"></xsl:when>	<!-- 季领 -->
	<xsl:when test=".=6"></xsl:when>	<!-- 每三年领 -->
	<xsl:when test=".=7"></xsl:when>	<!-- 半年领 -->
	<xsl:when test=".=8"></xsl:when>	<!-- 无关 -->
	<xsl:when test=".=9">9</xsl:when>	<!-- 其他 -->
	<xsl:when test=".=10">10</xsl:when>	<!-- 累积生息 -->
	<xsl:when test=".=11">11</xsl:when>	<!--领取现金 -->
	<xsl:when test=".=12">12</xsl:when>	<!-- 抵交保费 -->
	<xsl:when test=".=13">13</xsl:when>	<!-- 现金领取 -->
	<xsl:otherwise>--</xsl:otherwise> 
</xsl:choose>
</xsl:template>

<!--  领取年期/年龄类型 -->
<xsl:template name="tran_GetYearFlag" match="PayOutDurationMode">
<xsl:choose>
	<xsl:when test=".=1">A</xsl:when>	<!-- 领至某确定年龄 -->
	<xsl:when test=".=2">A</xsl:when>	<!-- 年领 -->
	<xsl:when test=".=3">A</xsl:when>	<!-- 月领 --> 
	<xsl:when test=".=4">A</xsl:when>	<!-- 日领 -->
	<xsl:when test=".=5">E</xsl:when>	<!-- 一次性领取 -->
	<xsl:when test=".=6">A</xsl:when>	<!-- 不定期 -->
	<xsl:when test=".=9">A</xsl:when>	<!-- 其他-->
	<xsl:otherwise>A</xsl:otherwise> 
</xsl:choose>  
</xsl:template>
</xsl:stylesheet>