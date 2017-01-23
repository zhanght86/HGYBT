<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TXLife"><!-- 贵州银行请求报文转换为核心请求报文 -->
<TranData>
   <Head>
      <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
      <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
      <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>	<!-- 地区代码 -->
      <NodeNo><xsl:value-of select="Branch"/></NodeNo>		<!-- 网点代码 -->
      <BankCode>0105</BankCode>								<!--  -->
      <TellerNo><xsl:value-of select="Teller"/></TellerNo>	<!-- 柜员代码 -->
      <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>	<!-- 流水号 -->
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
   	  <SaleChannel>0</SaleChannel> <!-- 柜面 -->
   	  <SaleName><xsl:value-of select="AgentSalesName"/></SaleName>
      <SaleStaff><xsl:value-of select="AgentSalesCode"/></SaleStaff>
      <SaleCertNo><xsl:value-of select="Credentials"/></SaleCertNo>
      <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>	<!-- 投保单号 -->
      <ContPrtNo />	<!--保单印刷号  -->
      <PolApplyDate><xsl:value-of select="SubmissionDate"/></PolApplyDate>	
      <AccNo><xsl:value-of select="Banking[AccountType='0' or AccountType='2']/AccountNumber"/></AccNo>
      <AccName><xsl:value-of select="PolicyHolder/FullName"/></AccName>
      <!--缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B -工行对应处的说明-->
	  <PayMode>B</PayMode>
      <GetPolMode>1</GetPolMode>
      <JobNotice/>
      <HealthNotice><xsl:value-of select="HealthIndicator"/></HealthNotice>
      <Appnt>
         <Name><xsl:value-of select="PolicyHolder/FullName"/></Name>
         <Sex>
			<xsl:call-template name="tran_sex">
				<xsl:with-param name="sex">
					<xsl:value-of select="PolicyHolder/Gender" />
				</xsl:with-param>
			</xsl:call-template>         
         </Sex>
         <Birthday><xsl:value-of select="PolicyHolder/BirthDate"/></Birthday>
         <IDType><!-- 投保人证件类型 -->
         	<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="PolicyHolder/GovtIDTC" />
				</xsl:with-param>
			</xsl:call-template> 
         </IDType>
         <IDNo><xsl:value-of select="PolicyHolder/GovtID"/></IDNo>
         <Prov><xsl:value-of select="PolicyHolder/Province"/></Prov>
		 <City><xsl:value-of select="PolicyHolder/County"/></City>
		 <Zone></Zone>
         <Address><xsl:value-of select="PolicyHolder/Line1"/></Address>
         <ZipCode><xsl:value-of select="PolicyHolder/Zip"/></ZipCode>
         <IdExpDate><xsl:value-of select="PolicyHolder/ExpireDate"/></IdExpDate>
         <Nationality>
         	<xsl:call-template name="tran_nationality">
				<xsl:with-param name="nationality">
					<xsl:value-of select="PolicyHolder/Nation" />
				</xsl:with-param>
			</xsl:call-template>
         </Nationality>
         <AddressContent><xsl:value-of select="PolicyHolder/Line1"/></AddressContent>
         <Phone><xsl:value-of select="PolicyHolder/DialNumber"/></Phone>
         <Mobile><xsl:value-of select="PolicyHolder/MobileNumber"/></Mobile>
         <Email><xsl:value-of select="PolicyHolder/Email"/></Email>
         <JobCode><!-- 投保人职业类别 -->
         	<xsl:call-template name="tran_jobcode">
					<xsl:with-param name="jobcode">
						<xsl:value-of
							select="PolicyHolder/OccupationType" />
					</xsl:with-param>
			</xsl:call-template>
         </JobCode>
         <!-- 体重 -->
		 <Stature><xsl:value-of select="PolicyHolder/Height"/></Stature>
		 <!-- 身高 -->
		 <Weight><xsl:value-of select="PolicyHolder/Weight"/></Weight>
		 <MaritalStatus><xsl:value-of select="PolicyHolder/Marriage"/></MaritalStatus>
         <YearSalary><xsl:value-of select="PolicyHolder/AnnualIncome div 10000"/></YearSalary>
         <FamilyYearSalary><xsl:value-of select="PolicyHolder/AnnualHomeIncome div 10000"/></FamilyYearSalary>
         <xsl:variable name="tAppBirthDay" 	select="PolicyHolder/BirthDate" />
         <xsl:variable name="tInsuBirthDay" 	select="Insured/BirthDate" />
         <RelaToInsured>
         	<xsl:call-template name="tran_relation">
				<xsl:with-param name="relation">
					<xsl:value-of select="PolicyHolder/RelatedToInsuredRoleCode" />
				</xsl:with-param>
				<xsl:with-param name="tApp">
					<xsl:value-of select="$tAppBirthDay" />
				</xsl:with-param>
				<xsl:with-param name="tInsu">
					<xsl:value-of select="$tInsuBirthDay" />
				</xsl:with-param>
			</xsl:call-template> 
         </RelaToInsured>
         <DenType><xsl:value-of select="PolicyHolder/ResidentsType" /></DenType>
      </Appnt>
      <Insured>
         <Name><xsl:value-of select="Insured/FullName"/></Name>
         <Sex>
         	<xsl:call-template name="tran_sex">
				<xsl:with-param name="sex">
					<xsl:value-of select="Insured/Gender" />
				</xsl:with-param>
			</xsl:call-template>    
         </Sex>
         <Birthday><xsl:value-of select="Insured/BirthDate"/></Birthday>
         <IDType><!-- 被保人证件类型 -->
         	<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="Insured/GovtIDTC" />
				</xsl:with-param>
			</xsl:call-template> 
         </IDType>
         <IDNo><xsl:value-of select="Insured/GovtID"/></IDNo>
         <IdExpDate><xsl:value-of select="Insured/ExpireDate"/></IdExpDate>
         <Nationality>
         	<xsl:call-template name="tran_nationality">
				<xsl:with-param name="nationality">
					<xsl:value-of select="Insured/Nation" />
				</xsl:with-param>
			</xsl:call-template>
         </Nationality>
         <AddressContent><xsl:value-of select="Insured/Line1"/></AddressContent>
         <FixTelDmstDstcNo></FixTelDmstDstcNo>
         <MobileItlDstcNo></MobileItlDstcNo>
         <Prov><xsl:value-of select="Insured/Province"/></Prov>
		 <City><xsl:value-of select="Insured/County"/></City>
		 <Zone></Zone>
         <Address><xsl:value-of select="Insured/Line1"/></Address>
         <ZipCode><xsl:value-of select="Insured/Zip"/></ZipCode>
         <Phone><xsl:value-of select="Insured/DialNumber"/></Phone>
         <Mobile><xsl:value-of select="Insured/MobileNumber"/></Mobile>
         <Email><xsl:value-of select="Insured/Email"/></Email>
         <JobCode><!-- 被保人职业类别 -->
         	<xsl:call-template name="tran_jobcode">
					<xsl:with-param name="jobcode">
						<xsl:value-of
							select="Insured/OccupationType" />
					</xsl:with-param>
			</xsl:call-template>
         </JobCode>
          <!-- 体重 -->
		 <Stature><xsl:value-of select="Insured/Height"/></Stature>
		 <!-- 身高 -->
		 <Weight><xsl:value-of select="Insured/Weight"/></Weight>
		 <MaritalStatus><xsl:value-of select="Insured/Marriage"/></MaritalStatus>
         <YearSalary><xsl:value-of select="Insured/AnnualIncome div 10000"/></YearSalary>
         <RiskAssessmentResult><xsl:value-of select="Insured/RiskIndicator"/></RiskAssessmentResult>
         <RiskAssessmentEndDate></RiskAssessmentEndDate>
         <PremiumBudget></PremiumBudget>
      </Insured>
      <xsl:choose>
		 <xsl:when test="BeneficiaryCount !='0'">
		 	<xsl:variable name="count" 	select="BeneficiaryCount" /><!-- 身故受益人个数 -->
		 	<xsl:for-each select="(//*)[position() &lt;= $count]"><!-- 选取所有节点相对于节点列表中所有选定节点的位置（或索引号）<=身故受益人个数 -->
		 		<xsl:variable name="tBeneficiary" select="concat('Beneficiary',position() )"></xsl:variable><!-- 返回上下文节点在正在处理的节点集合中的位置，串连Beneficiary字符串，作为当前身故受益人 -->
		 		<xsl:for-each select="/TXLife/*"><!--  根节点的所有子节点 -->
		 			<xsl:if test="local-name()= $tBeneficiary"><!-- 本地名为当前身故受益人 -->
		 			<xsl:variable name="tnode" select="node()" /><!-- 声明局部变量匹配除属性节点和根节点之外的其他任何节点[非根节点、属性节点的元素(根节点子元素)] -->
			 		<Bnf>
			 			<Type>1</Type> <!-- 身故受益人 -->
						<Grade><xsl:value-of select="No " /></Grade>
						<Name><xsl:value-of select="FullName" /></Name>
							<!-- 受益人性别 -->
						<Sex>
							<xsl:call-template name="tran_sex">
								<xsl:with-param name="sex">
									<xsl:value-of select="Gender" />
								</xsl:with-param>
							</xsl:call-template>
						</Sex>
						<Birthday>
							<xsl:value-of
								select="BirthDate" />
						</Birthday>
						<IDType><!-- 受益人证件类型 -->
							<xsl:call-template name="tran_idtype">
								<xsl:with-param name="idtype">
									<xsl:value-of
										select="GovtIDTC" />
								</xsl:with-param>
							</xsl:call-template>
						</IDType>
						<IDNo><xsl:value-of select="GovtID" /></IDNo>
						<!-- 受益人证件有效期-->
							<IdExpDate><xsl:value-of select="ExpireDate" /></IdExpDate>
						<Lot>
							<xsl:value-of
								select="InterestPercent" />
						</Lot>
				<!-- 		<RelaToInsured>
								<xsl:call-template name="tran_relation">
									<xsl:with-param name="RelaToInsured">
										<xsl:value-of
											select="PbBenfHoldRela" />
									</xsl:with-param>
								</xsl:call-template>
							</RelaToInsured> -->
							
						<xsl:choose>
							<xsl:when test="PbBenfHoldRela = '' ">
							<RelaToInsured>--</RelaToInsured>
							</xsl:when>
							<xsl:otherwise><RelaToInsured><xsl:value-of	select="PbBenfHoldRela" /></RelaToInsured></xsl:otherwise>
						</xsl:choose>
						<BeneficType>N</BeneficType>
			 		</Bnf>
			 		</xsl:if>
		 		</xsl:for-each>
		 	</xsl:for-each>
		 </xsl:when>
	  </xsl:choose>
    	<Risk>
         <RiskCode><xsl:value-of select="ProductCode"/></RiskCode>
         <MainRiskCode><xsl:value-of select="ProductCode"/></MainRiskCode>
         <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Amnt>
         <Prem>1000</Prem>
         <Mult><xsl:value-of select="IntialNumberOfUnits"/></Mult>
         <PayMode>B</PayMode>
         <xsl:choose>
         	<!-- 万能险PayIntv值固定 -->
         	<xsl:when test="ProductCode='011A0100'">
         		<PayIntv>0</PayIntv><!-- 趸交 -->
         		<PayEndYear>1000</PayEndYear>
        		<PayEndYearFlag>Y</PayEndYearFlag>
         		<!-- <InsuYearFlag>Y</InsuYearFlag>按年
         		<InsuYear>5</InsuYear>5年 -->
         	</xsl:when>
         	<xsl:otherwise>
         		<PayIntv>
		         	<xsl:call-template name="tran_payintv">
						<xsl:with-param name="payintv">
							<xsl:value-of
								select="PaymentMode" />
						</xsl:with-param>
					</xsl:call-template>
		         </PayIntv>
		         <PayEndYear><xsl:value-of select="PaymentDuration"/></PayEndYear>
		   		 <PayEndYearFlag>
		         	<xsl:call-template name="tran_payendyearflag">
						<xsl:with-param name="payendyearflag">
							<xsl:value-of
								select="PaymentMode" />
						</xsl:with-param>
					</xsl:call-template>
		         </PayEndYearFlag>
         	</xsl:otherwise>
         </xsl:choose>
         <GetIntv><xsl:value-of select="BenefitMode"/>
         	<xsl:call-template name="tran_BenefitMode">
				<xsl:with-param name="BenefitMode">
					<xsl:value-of
						select="BenefitMode" />
				</xsl:with-param>
			</xsl:call-template>
         </GetIntv>
         <GetYearFlag>Y</GetYearFlag>
         <BonusGetMode>
         	<xsl:call-template name="tran_getmode">
				<xsl:with-param name="getmode">
					<xsl:value-of
						select="DivType" />
				</xsl:with-param>
			</xsl:call-template>
         </BonusGetMode>
         <SubFlag />
         <GetYear><xsl:value-of select="COVERAGE_YEAR"/></GetYear>
         <InsuYearFlag>
         	<xsl:call-template name="tran_insuyearflag">
				<xsl:with-param name="insuyearflag">
					<xsl:value-of
						select="PeriodType" />
				</xsl:with-param>
			</xsl:call-template>
         </InsuYearFlag>
         <InsuYear><xsl:value-of select="CoverPeriod"/></InsuYear>
         <GetBankCode />
         <GetBankAccNo>
         	<xsl:value-of select="Banking[AccountType='1' or AccountType='2']/AccountNumber"/>
         </GetBankAccNo>
         <GetAccName />
         <AutoPayFlag />
      </Risk>
     <xsl:choose>
		 <xsl:when test="CoverageCount !='0'">
		 	<xsl:variable name="count1" 	select="CoverageCount" />
		 	<xsl:for-each select="(//*)[position() &lt;= $count1]">
		 		<xsl:variable name="tExtension" select="concat('Extension',position() )"></xsl:variable>
		 		<xsl:for-each select="/TXLife/*">
		 			<xsl:if test="local-name()= $tExtension">
		 				<Risk>
				         <RiskCode><xsl:value-of select="ProductCode"/></RiskCode>
				         <MainRiskCode><xsl:value-of select="/TXLife/ProductCode"/></MainRiskCode>
				         <Amnt></Amnt>
				         <Prem><xsl:value-of select="ModalPremAmt"/></Prem>
				         <Mult><xsl:value-of select="IntialNumberOfUnits"/></Mult>
				         <PayMode>B</PayMode>
				         <PayIntv>
					         <xsl:call-template name="tran_payintv">
							   <xsl:with-param name="payintv">
							     <xsl:value-of select="PaymentMode" />
							   </xsl:with-param>
						     </xsl:call-template>
				         </PayIntv>
				         <PayEndYear><xsl:value-of select="PayoutDuration"/></PayEndYear>
				         <PayEndYearFlag><xsl:value-of select="PaymentMode"/>
				         <xsl:call-template name="tran_payendyearflag">
					       <xsl:with-param name="payendyearflag">
						     <xsl:value-of select="PaymentMode" />
						   </xsl:with-param>
						 </xsl:call-template>
				         </PayEndYearFlag>
				         <GetIntv><xsl:value-of select="BenefitMode"/>
				         	<xsl:call-template name="tran_BenefitMode">
								<xsl:with-param name="BenefitMode">
									<xsl:value-of
										select="BenefitMode" />
								</xsl:with-param>
							</xsl:call-template>
				         </GetIntv>
				         <GetYearFlag>Y</GetYearFlag>
				         <BonusGetMode>
				         	<xsl:call-template name="tran_getmode">
								<xsl:with-param name="getmode">
									<xsl:value-of
										select="DivType" />
								</xsl:with-param>
							</xsl:call-template>
				         </BonusGetMode>
				         <SubFlag />
				         <GetYear><xsl:value-of select="COVERAGE_YEAR"/></GetYear>
				         <InsuYearFlag>
				         	<xsl:call-template name="tran_insuyearflag">
								<xsl:with-param name="insuyearflag">
									<xsl:value-of
										select="PeriodType" />
								</xsl:with-param>
							</xsl:call-template>
				         </InsuYearFlag>
				         <InsuYear><xsl:value-of select="CoverPeriod"/></InsuYear>
				         <GetBankCode />
				         <GetBankAccNo>
				         	<xsl:value-of select="Banking[AccountType='1' or AccountType='2']/AccountNumber"/>
				         </GetBankAccNo>
				         <GetAccName />
				         <AutoPayFlag />
				      </Risk>
			 		</xsl:if>
		 		</xsl:for-each>
		 	</xsl:for-each>
		 </xsl:when>
	  </xsl:choose>
	  <!-- 借意险添加贷款节点 -->
	  <xsl:if test="ProductCode='022J0300'">
			 <Loan>
			       <LoanNo><xsl:value-of select="Loan/LoanNo"/></LoanNo>                <!-- 贷款合同号-->
			       <LoanBankName><xsl:value-of select="Loan/LoanBankName"/></LoanBankName>    <!-- 贷款机构名称-->
			       <LoanDate><xsl:value-of select="Loan/LoanDate"/></LoanDate>            <!-- 贷款日期yyyyMMdd -->
			       <LoanEndDate><xsl:value-of select="Loan/LoanEndDate"/></LoanEndDate>      <!-- 贷款到期日yyyyMMdd-->
			       <LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Loan/LoanPrem)"/></LoanPrem><!-- 贷款金额-->
			       <InsuDate><xsl:value-of select="Loan/InsuDate"/></InsuDate>            <!--保险期限:月 -->
			       <IsPrepayInsu><xsl:value-of select="Loan/IsPrepayInsu"/></IsPrepayInsu>    <!-- 是否预交保费-->
			       <PrepayInsuPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Loan/PrepayInsuPrem)"/></PrepayInsuPrem><!--预交保费金额-->
			       <PrepayYear><xsl:value-of select="Loan/PrepayYear"/></PrepayYear>        <!--预交年期:月-->
			 </Loan>
	  </xsl:if>
  </Body>
</TranData>
</xsl:template>

	<xsl:template name="tran_sex">
		<xsl:param name="sex"></xsl:param>
		<xsl:choose>
			<xsl:when test="$sex = 1">0</xsl:when><!-- 男 -->
			<xsl:when test="$sex = 2">1</xsl:when><!-- 女 -->
			<xsl:otherwise>2</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_idtype"><!-- 贵州银行证件类型转换为核心证件类型 -->
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = 0">0</xsl:when><!-- 身份证 -->
			<xsl:when test="$idtype = 1">A</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = 2">1</xsl:when><!-- 护照 -->
			<xsl:when test="$idtype = 3">B</xsl:when><!-- 港澳通行证 -->
			<xsl:when test="$idtype = 4">E</xsl:when><!-- 台湾通行证 -->
			<xsl:when test="$idtype = 9">8</xsl:when><!-- 其他 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 1">0</xsl:when><!-- 趸交 -->
			<xsl:when test="$payintv = 2">6</xsl:when><!-- 半年 -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- 季交 -->
			<xsl:when test="$payintv = 4">1</xsl:when><!-- 月缴 -->
			<xsl:when test="$payintv = 5">12</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = 6">-1</xsl:when><!-- 不定期 -->
			<xsl:when test="$payintv = 7">-1</xsl:when><!-- 某年龄 -->
			<xsl:when test="$payintv = 8">-1</xsl:when><!-- 终身 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payendyearflag">
		<xsl:param name="payendyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payendyearflag = 1">A</xsl:when><!-- 趸交 -->
			<xsl:when test="$payendyearflag = 2">6</xsl:when><!-- 半年 -->
			<xsl:when test="$payendyearflag = 3">3</xsl:when><!-- 季交 -->
			<xsl:when test="$payendyearflag = 4">1</xsl:when><!-- 月缴 -->
			<xsl:when test="$payendyearflag = 5">Y</xsl:when><!-- 年交 -->
			<xsl:when test="$payendyearflag = 6">-1</xsl:when><!-- 不定期 -->
			<xsl:when test="$payendyearflag = 7">A</xsl:when><!-- 某年龄 -->
			<xsl:when test="$payendyearflag = 8">A</xsl:when><!-- 终身 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_getmode">
		<xsl:param name="getmode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$getmode = 11">2</xsl:when><!-- 现金 -->
			<xsl:when test="$getmode = 12">1</xsl:when><!-- 累计生息 -->
			<xsl:when test="$getmode = 13">3</xsl:when><!-- 抵交保费 -->
			<xsl:when test="$getmode = 14">5</xsl:when><!-- 增额交清 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_BenefitMode">
		<xsl:param name="BenefitMode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$BenefitMode = 1">0</xsl:when><!-- 趸领 -->
			<xsl:when test="$BenefitMode = 2">12</xsl:when><!-- 年领 -->
			<xsl:when test="$BenefitMode = 5">1</xsl:when><!-- 月领 -->
			<xsl:otherwise>12</xsl:otherwise>
			<!-- 增额交清 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = 0">A</xsl:when><!-- 无关 -->
			<xsl:when test="$insuyearflag = 1">A</xsl:when><!-- 终身 -->
			<xsl:when test="$insuyearflag = 2">Y</xsl:when><!-- 年 -->
			<xsl:when test="$insuyearflag = 3">A</xsl:when><!-- 年龄 -->
			<xsl:when test="$insuyearflag = 4">M</xsl:when><!-- 月 -->
			<xsl:when test="$insuyearflag = 5">D</xsl:when><!-- 天 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_relation">
		<xsl:param name="relation"></xsl:param>
		<xsl:param name="tApp"></xsl:param>
		<xsl:param name="tInsu"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = 301">00</xsl:when><!-- 本人 -->
			<xsl:when test="$relation = 303 or $relation = 304 or $relation = 306 or $relation = 309">
				<xsl:if test="$tApp >=  $tInsu">03</xsl:if><!-- 子女 -->
				<xsl:if test="$tApp &lt; $tInsu">01</xsl:if><!-- 父母 -->
			</xsl:when>
			<xsl:when test="$relation = 305">04</xsl:when><!-- 配偶 -->
			<xsl:otherwise>06</xsl:otherwise><!-- 其他 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_jobcode">
		<xsl:param name="jobcode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$jobcode = 1001">2099904</xsl:when>
			<xsl:when test="$jobcode = 1002">8000002</xsl:when>
			<xsl:when test="$jobcode = 1003">2099907</xsl:when>
			<xsl:when test="$jobcode = 1004"></xsl:when>
			<xsl:when test="$jobcode = 1005">2021106</xsl:when>
			<xsl:when test="$jobcode = 1006">6030516</xsl:when>
			<xsl:when test="$jobcode = 1007">4040701</xsl:when>
			<xsl:when test="$jobcode = 1008"></xsl:when><!-- 4050304、4040208 -->
			<xsl:when test="$jobcode = 1009">6050712</xsl:when>
			<xsl:when test="$jobcode = 1010">6010316</xsl:when>
			<xsl:when test="$jobcode = 1011">6010317</xsl:when>
			<xsl:when test="$jobcode = 1012">6010608</xsl:when>
			<xsl:when test="jobcode = 1013"></xsl:when>
			<xsl:when test="$jobcode = 1014">4030503</xsl:when>
			<xsl:when test="$jobcode = 1015">6230710</xsl:when>
			<xsl:when test="$jobcode = 1016">2129901</xsl:when>
			<xsl:when test="$jobcode = 1017"></xsl:when>
			<xsl:when test="$jobcode = 1018"></xsl:when>
			<xsl:when test="$jobcode = 1019"></xsl:when>
			<xsl:when test="$jobcode = 1020"></xsl:when>
			<xsl:when test="$jobcode = 1021">2099902</xsl:when>
			<xsl:when test="$jobcode = 1022">2080301</xsl:when>
			<xsl:when test="$jobcode = 1023">2060301</xsl:when>
			<xsl:when test="$jobcode = 1024">4071203</xsl:when>
			<xsl:when test="$jobcode = 1025">3020101</xsl:when>
			<xsl:when test="$jobcode = 9999999"></xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_nationality">
		<xsl:param name="nationality"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nationality=CAN">CAN</xsl:when><!-- 加拿大 -->
			<xsl:when test="$nationality=CN or $nationality=CHN">CHN</xsl:when><!-- 中国 -->
			<xsl:when test="$nationality=TWN">TW</xsl:when><!-- 台湾 -->
			<xsl:when test="$nationality=HKG">HK</xsl:when><!-- 香港 -->
			<xsl:when test="$nationality=JAP">JAN</xsl:when><!-- 日本 -->
			<xsl:when test="$nationality=KOR">KOR</xsl:when><!-- 韩国 -->
			<xsl:when test="$nationality=MAC">MO</xsl:when><!-- 澳门 -->
			<xsl:when test="$nationality=RUS">RUS</xsl:when><!-- 俄罗斯联邦 -->
			<xsl:when test="$nationality=GBR">ENG</xsl:when><!-- 英国 -->
			<xsl:when test="$nationality=USA">USA</xsl:when><!-- 美国 -->
			<xsl:when test="$nationality=AUS">AUS</xsl:when><!-- 澳大利亚 -->
			<xsl:otherwise>OTH</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>