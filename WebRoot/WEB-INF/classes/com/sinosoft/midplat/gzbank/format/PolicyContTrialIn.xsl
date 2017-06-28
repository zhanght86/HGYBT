<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TXLife">
		<TranData>
		   <Head>
		   	   <!-- 交易日期 -->
		       <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
		       <!-- 交易时间 -->
		       <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
		       <!-- 银行代码 -->
		       <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
		       <!-- 地区代码 -->
		       <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>
		       <!-- 网点代码 -->
		       <NodeNo><xsl:value-of select="Branch"/></NodeNo>
		       <!-- 柜员代码 -->
		       <TellerNo><xsl:value-of select="Teller"/></TellerNo>
		      	<!-- 交易流水号 -->
		       <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
		       <!-- YBT组织的节点信息 -->
			   <xsl:copy-of select="Head/*"/>
		   </Head>
		   <Body>
		   	   <SaleChannel>0</SaleChannel> <!-- 柜面 -->
		       <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>	<!-- 投保单号 -->
		       <ContPrtNo />	<!--保单印刷号  -->
		       <PolApplyDate><xsl:value-of select="SubmissionDate"/></PolApplyDate>	
		       <GetPolMode>1</GetPolMode>
		       <HealthNotice><xsl:value-of select="HealthIndicator"/></HealthNotice>
		       <AccName><xsl:value-of select="PolicyHolder/FullName"/></AccName>
		       <AccNo><xsl:value-of select="Banking[AccountType='0' or AccountType='2']/AccountNumber"/></AccNo>
		   	   <SaleName><xsl:value-of select="AgentSalesName"/></SaleName>
		       <SaleStaff><xsl:value-of select="AgentSalesCode"/></SaleStaff>
		       <SaleCertNo><xsl:value-of select="Credentials"/></SaleCertNo>
		       <!--缴费形式，从银保通出单的中韩这边缴费形式都置为B -工行对应处的说明-->
			   <PayMode>B</PayMode>
			   <JobNotice/>
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
			        <JobCode><!-- 投保人职业类别 -->
			         	<xsl:call-template name="tran_jobcode">
								<xsl:with-param name="jobcode">
									<xsl:value-of select="PolicyHolder/OccupationType" />
								</xsl:with-param>
						</xsl:call-template>
	        		</JobCode>
			        <Nationality>
			         	<xsl:call-template name="tran_nationality">
							<xsl:with-param name="nationality">
								<xsl:value-of select="PolicyHolder/Nation" />
							</xsl:with-param>
						</xsl:call-template>
			        </Nationality>
			        <!-- 体重 -->
					<Stature><xsl:value-of select="PolicyHolder/Height"/></Stature>
					<!-- 身高 -->
					<Weight><xsl:value-of select="PolicyHolder/Weight"/></Weight>
					<MaritalStatus><xsl:value-of select="PolicyHolder/Marriage"/></MaritalStatus>
			     	<YearSalary><xsl:value-of select="PolicyHolder/AnnualIncome div 10000"/></YearSalary>
			   	 	<IdExpDate><xsl:value-of select="PolicyHolder/ExpireDate"/></IdExpDate>
			    	<Address><xsl:value-of select="PolicyHolder/Line1"/></Address>
			    	<ZipCode><xsl:value-of select="PolicyHolder/Zip"/></ZipCode>
			    	<Mobile><xsl:value-of select="PolicyHolder/MobileNumber"/></Mobile>
				    <Phone><xsl:value-of select="PolicyHolder/DialNumber"/></Phone>
				    <Email><xsl:value-of select="PolicyHolder/Email"/></Email>
			        <!-- 投保人与被保人关系 -->
			        <RelaToInsured>
				        <xsl:choose>
				         	<!-- 投保人出生日期大于等于被保人 -->
				         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(PolicyHolder/BirthDate,Insured/BirthDate,'D')>=0">
				         		<xsl:call-template name="tran_relation1">
							       <xsl:with-param name="relation">
								    	<xsl:value-of select="PolicyHolder/RelatedToInsuredRoleCode" />
								   </xsl:with-param>
							 	</xsl:call-template> 
				         	</xsl:when>
				         	<!-- 投保人出生日期小于被保人 -->
				         	<xsl:otherwise>
				         		<xsl:call-template name="tran_relation2">
							      	<xsl:with-param name="relation">
								    	<xsl:value-of select="PolicyHolder/RelatedToInsuredRoleCode" />
								 	</xsl:with-param>
								</xsl:call-template> 
				         	</xsl:otherwise>
				        </xsl:choose>
			        </RelaToInsured>
	         		<Prov><xsl:value-of select="PolicyHolder/Province"/></Prov>
			 		<City><xsl:value-of select="PolicyHolder/County"/></City>
					<Zone></Zone>
			        <AddressContent><xsl:value-of select="PolicyHolder/Line1"/></AddressContent>
			        <FamilyYearSalary><xsl:value-of select="PolicyHolder/AnnualHomeIncome div 10000"/></FamilyYearSalary>
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
		           <JobCode><!-- 被保人职业类别 -->
		         		<xsl:call-template name="tran_jobcode">
							<xsl:with-param name="jobcode">
								<xsl:value-of select="Insured/OccupationType" />
							</xsl:with-param>
						</xsl:call-template>
		           </JobCode>
		           <Nationality>	
		         		<xsl:call-template name="tran_nationality">
							<xsl:with-param name="nationality">
								<xsl:value-of select="Insured/Nation" />
							</xsl:with-param>
						</xsl:call-template>
		           </Nationality>
		           <!-- 体重 -->
				   <Stature><xsl:value-of select="Insured/Height"/></Stature>
				   <!-- 身高 -->
				   <Weight><xsl:value-of select="Insured/Weight"/></Weight>
				   <MaritalStatus><xsl:value-of select="Insured/Marriage"/></MaritalStatus>
		           <YearSalary><xsl:value-of select="Insured/AnnualIncome div 10000"/></YearSalary>
		           <IdExpDate><xsl:value-of select="Insured/ExpireDate"/></IdExpDate>
		           <Address><xsl:value-of select="Insured/Line1"/></Address>
		           <ZipCode><xsl:value-of select="Insured/Zip"/></ZipCode>
		           <Mobile><xsl:value-of select="Insured/MobileNumber"/></Mobile>
		           <Phone><xsl:value-of select="Insured/DialNumber"/></Phone>
		           <Email><xsl:value-of select="Insured/Email"/></Email>
		           <AddressContent><xsl:value-of select="Insured/Line1"/></AddressContent>
		           <FixTelDmstDstcNo></FixTelDmstDstcNo>
		           <MobileItlDstcNo></MobileItlDstcNo>
		           <Prov><xsl:value-of select="Insured/Province"/></Prov>
				   <City><xsl:value-of select="Insured/County"/></City>
				   <Zone></Zone>
		           <RiskAssessmentResult><xsl:value-of select="Insured/RiskIndicator"/></RiskAssessmentResult>
		           <!-- <RiskAssessmentEndDate></RiskAssessmentEndDate>
		           <PremiumBudget></PremiumBudget> -->
		      </Insured>
		       <!-- 被保人出生日期 -->
		       <xsl:variable name="tInsuBirthDay" select="Insured/BirthDate" />
		       <!--身故受益人节点 -->
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
										<Grade>
										<xsl:if test="../ProductCode='022J0300'"><!--借意险 -->
										    <xsl:value-of select="No+1" /><!--对于借意险身故受益人顺序从2开始，1默认为机构受益人 -->
										</xsl:if>
										<xsl:if test="../ProductCode!='022J0300'"><!--非借意险 -->
										    <xsl:value-of select="No" />
										</xsl:if>
										</Grade>
										<Name><xsl:value-of select="FullName" /></Name>
										<!-- 受益人性别 -->
										<Sex>
											<xsl:call-template name="tran_sex">
												<xsl:with-param name="sex">
													<xsl:value-of select="Gender" />
												</xsl:with-param>
											</xsl:call-template>
										</Sex>
										<Birthday><xsl:value-of select="BirthDate" /></Birthday>
										<!-- 受益人证件类型 -->
										<IDType>
											<xsl:call-template name="tran_idtype">
												<xsl:with-param name="idtype">
													<xsl:value-of select="GovtIDTC" />
												</xsl:with-param>
											</xsl:call-template>
										</IDType>
										<IDNo><xsl:value-of select="GovtID" /></IDNo>
									    <!-- 受益人与被保人关系 -->
									    <RelaToInsured>
									   	<xsl:choose>
								         	<!-- 受益人出生日期大于等于被保人 -->
								         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(BirthDate,$tInsuBirthDay,'D')>=0">
								         		<xsl:call-template name="tran_relation1">
											      	<xsl:with-param name="relation">
												    	<xsl:value-of select="RelatedToInsuredRoleCode" />
												  	</xsl:with-param>
												</xsl:call-template> 
								         	</xsl:when>
								         	<!-- 受益人出生日期小于被保人 -->
								         	<xsl:otherwise>
								         		<xsl:call-template name="tran_relation2">
											      	<xsl:with-param name="relation">
												    	<xsl:value-of select="RelatedToInsuredRoleCode" />
												  	</xsl:with-param>
												</xsl:call-template> 
								         	</xsl:otherwise>
								        </xsl:choose>
					         			</RelaToInsured>
										<Lot><xsl:value-of select="InterestPercent" /></Lot>
										<!-- 受益人证件有效期-->
										<IdExpDate><xsl:value-of select="ExpireDate" /></IdExpDate>
										<BeneficType>N</BeneficType>
							 		</Bnf>
						 		</xsl:if>
					 		</xsl:for-each>
					 	</xsl:for-each>
					 </xsl:when>
			   </xsl:choose>
			   <!--生存受益人节点 -->
			   <xsl:for-each select="LifeBnft">
			     	<Bnf>
			            <Type>0</Type>
			            <Grade>
			             	<xsl:if test="../ProductCode='022J0300'">
			               		<xsl:call-template name="tran_Grade1">
					         		<xsl:with-param name="Grade">
						       			<xsl:value-of select="No" />
						     		</xsl:with-param>
					       		</xsl:call-template>
					     	</xsl:if>
					     	<xsl:if test="../ProductCode!='022J0300'">
			               		<xsl:call-template name="tran_Grade">
					         		<xsl:with-param name="Grade">
						       			<xsl:value-of select="No" />
						     		</xsl:with-param>
					       		</xsl:call-template>
					     	</xsl:if>
			           </Grade>
			            <Name><xsl:value-of select="Name"/></Name>
			            <Sex>
			             	<xsl:call-template name="tran_sex">
								<xsl:with-param name="sex">
									<xsl:value-of select="Sex" />
								</xsl:with-param>
							</xsl:call-template> 
			            </Sex>
			            <Birthday><xsl:value-of select="BirthDate"/></Birthday>
			            <IDType>
			             	<xsl:call-template name="tran_idtype">
								<xsl:with-param name="idtype">
									<xsl:value-of select="IdType" />
								</xsl:with-param>
							</xsl:call-template>
			            </IDType>
			            <IDNo><xsl:value-of select="IdNo"/></IDNo>
					    <RelaToInsured>
					   		<xsl:choose>
					         	<!-- 投保人出生日期大于等于被保人 -->
					         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(BirthDate,$tInsuBirthDay,'D')>=0">
					         		<xsl:call-template name="tran_relation1">
								      	<xsl:with-param name="relation">
									    	<xsl:value-of select="Relation" />
									  	</xsl:with-param>
									</xsl:call-template> 
					         	</xsl:when>
					         	<!-- 投保人出生日期小于被保人 -->
					         	<xsl:otherwise>
					         		<xsl:call-template name="tran_relation2">
								      	<xsl:with-param name="relation">
									    	<xsl:value-of select="Relation" />
									  </xsl:with-param>
									</xsl:call-template> 
					         	</xsl:otherwise>
				          	</xsl:choose>
			    	    </RelaToInsured>
			            <!-- 受益人与被保人关系 -->
			            <Lot><xsl:value-of select="InterestPercent"/></Lot>
			            <IdExpDate><xsl:value-of select="ExpireDate"/></IdExpDate>
			            <BeneficType>N</BeneficType>
			       </Bnf>
			   </xsl:for-each>
		       <Risk>
		         	<RiskCode><xsl:value-of select="ProductCode"/></RiskCode>
		         	<MainRiskCode><xsl:value-of select="ProductCode"/></MainRiskCode>
		         	<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Amnt>
		         	<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Prem><!--核心根据保额来计算保费,但是对保费节点有非空校验,所以1000为非空校验值无实际意义 -->
		         	<Mult><xsl:value-of select="IntialNumberOfUnits"/></Mult>
		         	<PayMode>B</PayMode>
		         	<!-- 保费缴纳方式代码 -->
				 	<xsl:if test="ProductCode='011A0100'"><!--万能险缴费方式固定 -->
				   		<PayIntv>0</PayIntv>
					   	<PayEndYear>1000</PayEndYear>
					   	<PayEndYearFlag>Y</PayEndYearFlag>
				 	</xsl:if>
					<xsl:if test="ProductCode!='011A0100'">
			           	<PayIntv>
			         	  	<xsl:call-template name="tran_payintv">
								<xsl:with-param name="payintv">
									<xsl:value-of select="PaymentMode" />
								</xsl:with-param>
						  	</xsl:call-template>
			           	</PayIntv>
			           	<xsl:choose>
			           		<xsl:when test="PaymentMode=1">
			           			<PayEndYear>1000</PayEndYear>
			           			<PayEndYearFlag>Y</PayEndYearFlag>
			           		</xsl:when>
			           		<xsl:otherwise>
					            <PayEndYear><xsl:value-of select="PaymentDuration"/></PayEndYear>
					            <PayEndYearFlag>
					         	 	<xsl:call-template name="tran_payendyearflag">
										<xsl:with-param name="payendyearflag">
											<xsl:value-of select="PaymentMode" />
										</xsl:with-param>
								 	</xsl:call-template>
					            </PayEndYearFlag>
			           		</xsl:otherwise>
			           	</xsl:choose>
			        </xsl:if>
			        <InsuYearFlag>
			         	<xsl:call-template name="tran_insuyearflag">
							<xsl:with-param name="insuyearflag">
								<xsl:value-of select="PeriodType" />
							</xsl:with-param>
						</xsl:call-template>
			        </InsuYearFlag>
			        <InsuYear>
				        <xsl:choose>
				        	<xsl:when test="PeriodType=1">105</xsl:when>
				        	<xsl:otherwise>
			         			<xsl:value-of select="CoverPeriod"/>
				        	</xsl:otherwise>
				        </xsl:choose>
			        </InsuYear>
			        <BonusGetMode>
			         	<xsl:call-template name="tran_getmode">
							<xsl:with-param name="getmode">
								<xsl:value-of select="DivType" />
							</xsl:with-param>
						</xsl:call-template>
			        </BonusGetMode>
		         <FullBonusGetMode />
			        <GetYearFlag>Y</GetYearFlag>
			        <GetYear><xsl:value-of select="COVERAGE_YEAR"/></GetYear>
			        <GetTerms></GetTerms>
			        <GetIntv><xsl:value-of select="BenefitMode"/>
			         	<xsl:call-template name="tran_BenefitMode">
							<xsl:with-param name="BenefitMode">
								<xsl:value-of select="BenefitMode" />
							</xsl:with-param>
						</xsl:call-template>
			        </GetIntv>
			        <GetBankCode />
			        <GetBankAccNo>
			         	<xsl:value-of select="Banking[AccountType='1' or AccountType='2']/AccountNumber"/>
			        </GetBankAccNo>
			        <GetAccName />
			        <AutoPayFlag />
		         	<SubFlag />
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
								        <InsuYearFlag>
							         		<xsl:call-template name="tran_insuyearflag">
												<xsl:with-param name="insuyearflag">
													<xsl:value-of select="PeriodType" />
												</xsl:with-param>
											</xsl:call-template>
								        </InsuYearFlag>
								        <InsuYear><xsl:value-of select="CoverPeriod"/></InsuYear>
								        <PayEndYearFlag><xsl:value-of select="PaymentMode"/>
									         <xsl:call-template name="tran_payendyearflag">
											       <xsl:with-param name="payendyearflag">
												     	<xsl:value-of select="PaymentMode" />
												   </xsl:with-param>
											 </xsl:call-template>
								        </PayEndYearFlag>
								        <PayEndYear><xsl:value-of select="PayoutDuration"/></PayEndYear>
								        <BonusGetMode>
								         	<xsl:call-template name="tran_getmode">
												<xsl:with-param name="getmode">
													<xsl:value-of select="DivType" />
												</xsl:with-param>
											</xsl:call-template>
								        </BonusGetMode>
								        <FullBonusGetMode />
								        <GetYearFlag>Y</GetYearFlag>
								        <GetYear><xsl:value-of select="COVERAGE_YEAR"/></GetYear>
								        <GetTerms />
								        <GetIntv><xsl:value-of select="BenefitMode"/>
								         	<xsl:call-template name="tran_BenefitMode">
												<xsl:with-param name="BenefitMode">
													<xsl:value-of select="BenefitMode" />
												</xsl:with-param>
											</xsl:call-template>
								        </GetIntv>
								        <GetBankCode />
								        <GetBankAccNo>
								         	<xsl:value-of select="Banking[AccountType='1' or AccountType='2']/AccountNumber"/>
								        </GetBankAccNo>
								        <GetAccName />
								        <AutoPayFlag />
								        <SubFlag />
							      	</Risk>
					 			</xsl:if>
				 			</xsl:for-each>
				 		</xsl:for-each>
				 	</xsl:when>
			   </xsl:choose>
			   <xsl:if test="ProductCode='022J0300'"><!-- 借意险新添节点 -->
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
	
    <!--生存收益顺序装换 ：非借意险-->
    <xsl:template name="tran_Grade">
		<xsl:param name="Grade"></xsl:param>
		<xsl:choose>
			<xsl:when test="$Grade = 'a'">1</xsl:when>
			<xsl:when test="$Grade = 'b'">2</xsl:when>
			<xsl:when test="$Grade = 'c'">3</xsl:when>
			<xsl:when test="$Grade = 'd'">4</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<!--生存收益顺序转换换 ：借意险022J0300-->
    <xsl:template name="tran_Grade1">
		<xsl:param name="Grade"></xsl:param>
		<xsl:choose>
			<xsl:when test="$Grade = 'a'">3</xsl:when>
			<xsl:when test="$Grade = 'b'">4</xsl:when>
			<xsl:when test="$Grade = 'c'">5</xsl:when>
			<xsl:when test="$Grade = 'd'">6</xsl:when>
		</xsl:choose>
	</xsl:template>
	
    <!--性别转换 -->
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
			<xsl:when test="$idtype = 1">2</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = 2">1</xsl:when><!-- 护照 -->
			<xsl:when test="$idtype = 3">F</xsl:when><!-- 港澳通行证 -->
			<xsl:when test="$idtype = 4">F</xsl:when><!-- 台湾通行证 -->
			<xsl:when test="$idtype = 9">99</xsl:when><!-- 其他 -->
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
			<xsl:when test="$payendyearflag = 2"></xsl:when><!-- 半年 -->
			<xsl:when test="$payendyearflag = 3"></xsl:when><!-- 季交 -->
			<xsl:when test="$payendyearflag = 4">M</xsl:when><!-- 月缴 -->
			<xsl:when test="$payendyearflag = 5">Y</xsl:when><!-- 年交 -->
			<xsl:when test="$payendyearflag = 6"></xsl:when><!-- 不定期 -->
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
			<xsl:when test="$BenefitMode = 1">0</xsl:when><!-- 一次性给付 -->
			<xsl:when test="$BenefitMode = 2">12</xsl:when><!-- 年给付 -->
			<xsl:when test="$BenefitMode = 3">6</xsl:when><!-- 半年给付 -->
			<xsl:when test="$BenefitMode = 4">3</xsl:when><!-- 季给付 -->
			<xsl:when test="$BenefitMode = 5">1</xsl:when><!-- 月给付 -->
			<xsl:when test="$BenefitMode = 6"></xsl:when><!-- 其他给付 -->
			<xsl:otherwise></xsl:otherwise>
			<!-- 增额交清 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = 0">A</xsl:when><!-- 无关 -->
			<xsl:when test="$insuyearflag = 1">A</xsl:when><!-- 保终身 -->
			<xsl:when test="$insuyearflag = 2">Y</xsl:when><!-- 按年限保 -->
			<xsl:when test="$insuyearflag = 3">A</xsl:when><!-- 保至某确定年龄 -->
			<xsl:when test="$insuyearflag = 4">M</xsl:when><!-- 按月保 -->
			<xsl:when test="$insuyearflag = 5">D</xsl:when><!-- 按天保 -->
		</xsl:choose>
	</xsl:template>
	
	<!-- 投保人出生日期大于、等于被保人出生日期
		    投保人、受益人与被保人关系 -->
	<xsl:template name="tran_relation1">
	  <xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = '301'">00</xsl:when><!-- 本人 -->
			<xsl:when test="$relation = '302'">05</xsl:when><!--法定 -->
			<xsl:when test="$relation = '303'">01</xsl:when><!--母女 -->
			<xsl:when test="$relation = '304'">01</xsl:when><!-- 父女 -->
			<xsl:when test="$relation = '305'">04</xsl:when><!-- 祖孙 -->
			<xsl:when test="$relation = '306'">01</xsl:when><!-- 母子 -->
			<xsl:when test="$relation = '307'">06</xsl:when><!-- 兄弟 -->
			<xsl:when test="$relation = '308'">06</xsl:when><!-- 姐妹 -->
			<xsl:when test="$relation = '309'">01</xsl:when><!-- 父子 -->
			<xsl:when test="$relation = '310'">02</xsl:when><!-- 夫妻 -->
			<xsl:when test="$relation = '311'">06</xsl:when><!-- 岳婿 -->
			<xsl:when test="$relation = '312'">09</xsl:when><!-- 雇佣 -->
			<xsl:when test="$relation = '313'">06</xsl:when><!-- 侄儿 -->
			<xsl:when test="$relation = '314'">06</xsl:when><!-- 侄女 -->
			<xsl:when test="$relation = '315'">06</xsl:when><!-- 姐弟 -->
			<xsl:when test="$relation = '316'">06</xsl:when><!-- 朋友 -->
			<xsl:when test="$relation = '317'">06</xsl:when><!-- 外甥女 -->
			<xsl:when test="$relation = '318'">06</xsl:when><!-- 外甥 -->
			<xsl:when test="$relation = '319'">06</xsl:when><!-- 兄妹 -->
			<xsl:when test="$relation = '320'">06</xsl:when><!-- 婆媳 -->
			<xsl:when test="$relation = '321'">06</xsl:when><!-- 姑嫂 -->
			<xsl:when test="$relation = '322'">06</xsl:when><!-- 未婚夫妻 -->
			<xsl:when test="$relation = '323'">06</xsl:when><!-- 妯娌 -->
			<xsl:when test="$relation = '324'">06</xsl:when><!-- 亲属 -->
			<xsl:when test="$relation = '325'">06</xsl:when><!-- 抚养 -->
			<xsl:when test="$relation = '326'">06</xsl:when><!-- 公媳 -->
			<xsl:when test="$relation = '333'">06</xsl:when><!-- 其它 -->
			<xsl:when test="$relation = '371'">06</xsl:when><!-- 外甥 -->
			<xsl:when test="$relation = '380'">05</xsl:when><!-- 法人 -->
			<xsl:when test="$relation = '381'">06</xsl:when><!-- 兄妹 -->
			<xsl:when test="$relation = '382'">06</xsl:when><!-- 姐弟 -->
			<xsl:when test="$relation = '391'">09</xsl:when><!-- 雇佣 -->
			<xsl:when test="$relation = '392'">06</xsl:when><!-- 亲属 -->
			<xsl:when test="$relation = '393'">06</xsl:when><!-- 朋友 -->
			<xsl:when test="$relation = '394'">06</xsl:when><!-- 同学或同事 -->
			<xsl:when test="$relation = '395'">06</xsl:when><!-- 未婚夫妻 -->
			<xsl:when test="$relation = '396'">06</xsl:when><!-- 职员 -->
			<xsl:when test="$relation = '397'">06</xsl:when><!-- 其他 -->
			<xsl:when test="$relation = '398'">06</xsl:when><!-- 叔侄关系 -->
			<xsl:when test="$relation = '399'">06</xsl:when><!-- 其他 -->
			<xsl:when test="$relation = ''">00</xsl:when><!--本人：和被保人关系未输入时，默认为本人 -->
		</xsl:choose>
	</xsl:template>
	
	<!-- 投保人出生日期小于被保人出生日期
		    投保人、受益人与被保人关系 -->
	<xsl:template name="tran_relation2">
	  <xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = '301'">00</xsl:when><!-- 本人 -->
			<xsl:when test="$relation = '302'">05</xsl:when><!-- 法定 -->
			<xsl:when test="$relation = '303'">03</xsl:when><!-- 投保人、受益人母女  被保人子女-->
			<xsl:when test="$relation = '304'">03</xsl:when><!-- 投保人、受益人父女  被保人子女-->
			<xsl:when test="$relation = '305'">04</xsl:when><!-- 投保人、受益人祖孙  被保人祖孙-->
			<xsl:when test="$relation = '306'">03</xsl:when><!-- 投保人、受益人母子  被保人子女-->
			<xsl:when test="$relation = '307'">06</xsl:when><!-- 投保人、受益人兄弟  被保人其他-->
			<xsl:when test="$relation = '308'">06</xsl:when><!-- 投保人、受益人姐妹  被保人其他-->
			<xsl:when test="$relation = '309'">03</xsl:when><!-- 投保人、受益人父子  被保人子女-->
			<xsl:when test="$relation = '310'">02</xsl:when><!-- 投保人、受益人夫妻  被保人配偶-->
			<xsl:when test="$relation = '311'">06</xsl:when><!-- 投保人、受益人岳婿  被保人其他-->
			<xsl:when test="$relation = '312'">09</xsl:when><!-- 投保人、受益人雇佣  被保人雇佣关系-->
			<xsl:when test="$relation = '313'">06</xsl:when><!-- 投保人、受益人侄儿  被保人其他-->
			<xsl:when test="$relation = '314'">06</xsl:when><!-- 投保人、受益人侄女  被保人其他-->
			<xsl:when test="$relation = '315'">06</xsl:when><!-- 投保人、受益人姐弟  被保人其他-->
			<xsl:when test="$relation = '316'">06</xsl:when><!-- 投保人、受益人朋友  被保人其他-->
			<xsl:when test="$relation = '317'">06</xsl:when><!-- 投保人、受益人外甥女  被保人其他-->
			<xsl:when test="$relation = '318'">06</xsl:when><!-- 投保人、受益人外甥  被保人其他-->
			<xsl:when test="$relation = '319'">06</xsl:when><!-- 投保人、受益人兄妹  被保人其他-->
			<xsl:when test="$relation = '320'">06</xsl:when><!-- 投保人、受益人婆媳  被保人其他-->
			<xsl:when test="$relation = '321'">06</xsl:when><!-- 投保人、受益人姑嫂  被保人其他-->
			<xsl:when test="$relation = '322'">06</xsl:when><!-- 投保人、受益人未婚夫妻  被保人其他-->
			<xsl:when test="$relation = '323'">06</xsl:when><!-- 投保人、受益人妯娌  被保人其他-->
			<xsl:when test="$relation = '324'">06</xsl:when><!-- 投保人、受益人亲属  被保人其他-->
			<xsl:when test="$relation = '325'">06</xsl:when><!-- 投保人、受益人抚养  被保人其他-->
			<xsl:when test="$relation = '326'">06</xsl:when><!-- 投保人、受益人公媳  被保人其他-->
			<xsl:when test="$relation = '333'">06</xsl:when><!-- 投保人、受益人其它  被保人其他-->
			<xsl:when test="$relation = '371'">06</xsl:when><!-- 投保人、受益人外甥  被保人其他-->
			<xsl:when test="$relation = '380'">05</xsl:when><!-- 投保人、受益人法人  被保人法定监护人-->
			<xsl:when test="$relation = '381'">06</xsl:when><!-- 投保人、受益人兄妹  被保人其他-->
			<xsl:when test="$relation = '382'">06</xsl:when><!-- 投保人、受益人姐弟  被保人其他-->
			<xsl:when test="$relation = '391'">09</xsl:when><!-- 投保人、受益人雇佣  被保人雇佣关系-->
			<xsl:when test="$relation = '392'">06</xsl:when><!-- 投保人、受益人亲属  被保人其他-->
			<xsl:when test="$relation = '393'">06</xsl:when><!-- 投保人、受益人朋友  被保人其他-->
			<xsl:when test="$relation = '394'">06</xsl:when><!-- 投保人、受益人同学或同事  被保人其他-->
			<xsl:when test="$relation = '395'">06</xsl:when><!-- 投保人、受益人未婚夫妻  被保人其他-->
			<xsl:when test="$relation = '396'">06</xsl:when><!-- 投保人、受益人职员  被保人其他-->
			<xsl:when test="$relation = '397'">06</xsl:when><!-- 其他 -->
			<xsl:when test="$relation = '398'">06</xsl:when><!-- 投保人、受益人叔侄关系  被保人其他-->
			<xsl:when test="$relation = '399'">06</xsl:when><!-- 其他 -->
			<xsl:when test="$relation = ''">00</xsl:when><!--本人：和被保人关系未输入时，默认为本人 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_jobcode">
		<xsl:param name="jobcode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$jobcode = 1001">2099904</xsl:when><!-- 教练 核心有多个值 -->
			<xsl:when test="$jobcode = 1002">8000002</xsl:when><!-- 离退休人员  8000003、8000002-->
			<xsl:when test="$jobcode = 1003">2099907</xsl:when><!-- 儿童、18岁前学生 核心一般学生 -->
			<xsl:when test="$jobcode = 1004"></xsl:when><!-- 负责人（不亲自作业） 核心有多个值，没有确定映射-->
			<xsl:when test="$jobcode = 1005">2021106</xsl:when><!-- 工程师  6050710、6051807 -->
			<xsl:when test="$jobcode = 1006">6030516</xsl:when><!-- 技师  6059901、6080112、6099902、6180106 -->
			<xsl:when test="$jobcode = 1007">4040701</xsl:when><!-- 负责人 核心有多个值 -->
			<xsl:when test="$jobcode = 1008"></xsl:when><!-- 一般内勤、经理 4030504、4040105、4040208、4050208、4050304 -->
			<xsl:when test="$jobcode = 1009">6050712</xsl:when><!-- 领班、监工 6040117、6050712、5020408、6020210、6059902、-->
			<xsl:when test="$jobcode = 1010">6010316</xsl:when><!-- 经营者（不到现场者）-->
			<xsl:when test="$jobcode = 1011">6010317</xsl:when><!-- 经营者（现场监督者） -->
			<xsl:when test="$jobcode = 1012">6010608</xsl:when><!-- 行政人员 -->
			<xsl:when test="jobcode = 1013"></xsl:when><!-- 一般内勤工作人员 4050111、2022111 -->
			<xsl:when test="$jobcode = 1014">4030503</xsl:when><!-- 经理人员 4030503、6010318 -->
			<xsl:when test="$jobcode = 1015">6230710</xsl:when><!-- 承包商、监工 -->
			<xsl:when test="$jobcode = 1016">2129901</xsl:when><!-- 内勤人员 -->
			<xsl:when test="$jobcode = 1017"></xsl:when><!-- 外勤人员 -->
			<xsl:when test="$jobcode = 1018"></xsl:when><!-- 业务员 核心有多个值 -->
			<xsl:when test="$jobcode = 1019"></xsl:when><!-- 教师 核心有多个值-->
			<xsl:when test="$jobcode = 1020"></xsl:when><!-- 学生 核心有多个值-->
			<xsl:when test="$jobcode = 1021">2099902</xsl:when><!-- 军训教官、体育教师 -->
			<xsl:when test="$jobcode = 1022">2080301</xsl:when><!-- 律师 -->
			<xsl:when test="$jobcode = 1023">2060301</xsl:when><!-- 会计师 -->
			<xsl:when test="$jobcode = 1024">4071203</xsl:when><!-- 家庭主妇  -->
			<xsl:when test="$jobcode = 1025">3020101</xsl:when><!-- 警务行政及内勤人员 -->
			<xsl:when test="$jobcode = 9999999"></xsl:when><!-- 9999999999 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_nationality">
		<xsl:param name="nationality"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nationality=ARA">UAE</xsl:when><!-- 阿拉伯 -->
			<xsl:when test="$nationality=AUS">AUS</xsl:when><!-- 澳大利亚 -->
			<xsl:when test="$nationality=BIE">BLR</xsl:when><!-- 白俄罗斯 -->
			<xsl:when test="$nationality=BRE">BAS</xsl:when><!-- 巴西 -->
			<xsl:when test="$nationality=D">DEU</xsl:when><!-- 德国 -->
			<xsl:when test="$nationality=DAN">DEN</xsl:when><!-- 丹麦 -->
			<xsl:when test="$nationality=E">ESP</xsl:when><!-- 西班牙 -->
			<xsl:when test="$nationality=F">FRA</xsl:when><!--法国 -->
			<xsl:when test="$nationality=FIN">FIN</xsl:when><!-- 芬兰 -->
			<xsl:when test="$nationality=GB">ENG</xsl:when><!-- 英国 -->
			<xsl:when test="$nationality=GR">GRE</xsl:when><!--希腊  -->
			<xsl:when test="$nationality=H">HUN</xsl:when><!-- 匈牙利 -->
			<xsl:when test="$nationality=IN">IND</xsl:when><!-- 印度 -->
			<xsl:when test="$nationality=IND">INA</xsl:when><!-- 印尼 -->
			<xsl:when test="$nationality=JAP">JAN</xsl:when><!-- 日本 -->
			<xsl:when test="$nationality=L">LUX</xsl:when><!-- 卢森堡 -->
			<xsl:when test="$nationality=MAL">MY</xsl:when><!-- 马来西亚 -->
			<xsl:when test="$nationality=NL">NL</xsl:when><!-- 荷兰 -->
			<xsl:when test="$nationality=POL">PL</xsl:when><!-- 波兰 -->
			<xsl:when test="$nationality=POR">POR</xsl:when><!-- 葡萄牙 -->
			<xsl:when test="$nationality=ROU">ROM</xsl:when><!-- 罗马尼亚 -->
			<xsl:when test="$nationality=RUS">RUS</xsl:when><!-- 俄国 -->
			<xsl:when test="$nationality=TCH">CZE</xsl:when><!-- 捷克 -->
			<xsl:when test="$nationality=USA">USA</xsl:when><!-- 美国 -->
			<xsl:when test="$nationality=WEL">WLS</xsl:when><!-- 威尔士 -->
			<xsl:when test="$nationality=ABW">ABW</xsl:when><!-- 阿鲁巴 -->
			<xsl:when test="$nationality=ANT">AHO</xsl:when><!-- 荷属安的列斯 -->
			<xsl:when test="$nationality=ARE">UAE</xsl:when><!-- 阿联酋 -->
			<xsl:when test="$nationality=BRA">BAS</xsl:when><!-- 巴西 -->
			<xsl:when test="$nationality=BHR">BRN</xsl:when><!-- 巴林-->
			<xsl:when test="$nationality=FRA">FRA</xsl:when><!-- 法国 -->
			<xsl:when test="$nationality=BMU">BER</xsl:when><!-- 百慕大 -->
			<xsl:when test="$nationality=BOL">BOL</xsl:when><!-- 玻利维亚 -->
			<xsl:when test="$nationality=JPN">JAN</xsl:when><!-- 日本 -->
			<xsl:when test="$nationality=BRN">BRU</xsl:when><!-- 文莱 -->
			<xsl:when test="$nationality=BWA">BOT</xsl:when><!-- 博茨瓦纳 -->
			<xsl:when test="$nationality=CAN">CAN</xsl:when><!-- 加拿大 -->
			<xsl:when test="$nationality=CHE">SUI</xsl:when><!-- 瑞士 -->
			<xsl:when test="$nationality=CHL">CHI</xsl:when><!-- 智利 -->
			<xsl:when test="$nationality=PRT">POR</xsl:when><!-- 葡萄牙 -->
			<xsl:when test="$nationality=COL">COL</xsl:when><!-- 哥伦比亚 -->
			<xsl:when test="$nationality=CRI">CRC</xsl:when><!-- 哥斯达黎加 -->
			<xsl:when test="$nationality=CUB">CUB</xsl:when><!-- 古巴 -->
			<xsl:when test="$nationality=CYP">CYP</xsl:when><!-- 塞浦路斯 -->
			<xsl:when test="$nationality=CZE">CZE</xsl:when><!-- 捷克 -->
			<xsl:when test="$nationality=BLR">BLR</xsl:when><!-- 白俄罗斯 -->
			<xsl:when test="$nationality=GBR">ENG</xsl:when><!-- 英国 -->
			<xsl:when test="$nationality=DOM">DOM</xsl:when><!-- 多米尼加 -->
			<xsl:when test="$nationality=DZA">ALG</xsl:when><!-- 阿尔及利亚 -->
			<xsl:when test="$nationality=ECU">ECU</xsl:when><!-- 厄瓜多尔 -->
			<xsl:when test="$nationality=EGY">EGY</xsl:when><!-- 埃及 -->
			<xsl:when test="$nationality=ROM">ROM</xsl:when><!-- 罗马尼亚 -->
			<xsl:when test="$nationality=ETH">ETH</xsl:when><!-- 埃塞俄比亚 -->
			<xsl:when test="$nationality=DEU">DEU</xsl:when><!-- 德国 -->
			<xsl:when test="$nationality=FRO">FAI</xsl:when><!-- 法罗群岛 -->
			<xsl:when test="$nationality=LUX">LUX</xsl:when><!-- 卢森堡 -->
			<xsl:when test="$nationality=GEO">GEO</xsl:when><!-- 格鲁吉亚 -->
			<xsl:when test="$nationality=GHA">DG</xsl:when><!-- 加纳 -->
			<xsl:when test="$nationality=CN or $nationality=CHN">CHN</xsl:when><!-- 中国 -->
			<xsl:when test="$nationality=HKG">HK</xsl:when><!-- 香港 -->
			<xsl:when test="$nationality=HND">HON</xsl:when><!-- 洪都拉斯 -->
			<xsl:when test="$nationality=HRV">CRO</xsl:when><!-- 克罗地亚 -->
			<xsl:when test="$nationality=BUL or $nationality=BGR">BUL</xsl:when><!-- 保加利亚 -->
			<xsl:when test="$nationality=IDN">INA</xsl:when><!-- 印度尼西亚 -->
			<xsl:when test="$nationality=IRN">IRI</xsl:when><!-- 伊朗 -->
			<xsl:when test="$nationality=IRQ">IRQ</xsl:when><!-- 伊拉克 -->
			<xsl:when test="$nationality=ISL">ISL</xsl:when><!-- 冰岛 -->
			<xsl:when test="$nationality=ISR">ISR</xsl:when><!-- 以色列 -->
			<xsl:when test="$nationality=JAM">JAM</xsl:when><!-- 牙买加 -->
			<xsl:when test="$nationality=GRC">GRE</xsl:when><!-- 希腊 -->
			<xsl:when test="$nationality=KAZ">KAZ</xsl:when><!-- 哈萨克斯坦 -->
			<xsl:when test="$nationality=KEN">KEN</xsl:when><!-- 肯尼亚 -->
			<xsl:when test="$nationality=KOR">KOR</xsl:when><!-- 韩国 -->
			<xsl:when test="$nationality=LBN">LIB</xsl:when><!-- 黎巴嫩 -->
			<xsl:when test="$nationality=LBY">LBA</xsl:when><!-- 利比亚 -->
			<xsl:when test="$nationality=LIE">LIE</xsl:when><!-- 列支敦士登 -->
			<xsl:when test="$nationality=LKA">SLK</xsl:when><!-- 斯里兰卡 -->
			<xsl:when test="$nationality=LTU">LTU</xsl:when><!-- 立陶宛 -->
			<xsl:when test="$nationality=HUN">HUN</xsl:when><!-- 匈牙利 -->
			<xsl:when test="$nationality=LVA">LAT</xsl:when><!-- 拉脱维亚 -->
			<xsl:when test="$nationality=MAC">MO</xsl:when><!-- 澳门 -->
			<xsl:when test="$nationality=MAR">MAR</xsl:when><!-- 摩洛哥 -->
			<xsl:when test="$nationality=MCO">MNC</xsl:when><!-- 摩纳哥 -->
			<xsl:when test="$nationality=MEX">MEX</xsl:when><!-- 墨西哥 -->
			<xsl:when test="$nationality=MNG">MGL</xsl:when><!-- 蒙古 -->
			<xsl:when test="$nationality=MWI">MAW</xsl:when><!-- 马拉维 -->
			<xsl:when test="$nationality=MYS">MY</xsl:when><!-- 马来西亚 -->
			<xsl:when test="$nationality=NAM">NAM</xsl:when><!-- 纳米比亚 -->
			<xsl:when test="$nationality=NGA">NGR</xsl:when><!-- 尼日利亚 -->
			<xsl:when test="$nationality=ESP">ESP</xsl:when><!-- 西班牙 -->
			<xsl:when test="$nationality=NOR">NO</xsl:when><!-- 挪威 -->
			<xsl:when test="$nationality=PAN">PAN</xsl:when><!-- 巴拿马 -->
			<xsl:when test="$nationality=PHL">PHL</xsl:when><!-- 菲律宾 -->
			<xsl:when test="$nationality=NIC">NCA</xsl:when><!-- 尼加拉瓜 -->
			<xsl:when test="$nationality=PNG">PNG</xsl:when><!-- 巴布亚新几内亚 -->
			<xsl:when test="$nationality=DNK">DEN</xsl:when><!-- 丹麦 -->
			<xsl:when test="$nationality=PRY">PAR</xsl:when><!-- 巴拉圭 -->
			<xsl:when test="$nationality=PSE">PLE</xsl:when><!-- 巴勒斯坦 -->
			<xsl:when test="$nationality=QAT">QAT</xsl:when><!-- 卡塔尔 -->
			<xsl:when test="$nationality=NLD">NL</xsl:when><!-- 荷兰 -->
			<xsl:when test="$nationality=RWA">RWA</xsl:when><!-- 卢旺达 -->
			<xsl:when test="$nationality=SDN">SUD</xsl:when><!-- 苏丹 -->
			<xsl:when test="$nationality=SGP">SG</xsl:when><!-- 新加坡 -->
			<xsl:when test="$nationality=SMR">SMR</xsl:when><!--圣马力诺 -->
			<xsl:when test="$nationality=SOM">SOM</xsl:when><!-- 索马里 -->
			<xsl:when test="$nationality=SUR">SUR</xsl:when><!-- 苏里南 -->
			<xsl:when test="$nationality=SVK">SVK</xsl:when><!-- 斯洛伐克 -->
			<xsl:when test="$nationality=SVN">SLO</xsl:when><!-- 斯洛文尼亚 -->
			<xsl:when test="$nationality=SWE">SWE</xsl:when><!-- 瑞典 -->
			<xsl:when test="$nationality=SYC">SEY</xsl:when><!-- 塞舌尔 -->
			<xsl:when test="$nationality=SYR">SYR</xsl:when><!-- 叙利亚 -->
			<xsl:when test="$nationality=THA">THA</xsl:when><!-- 泰国 -->
			<xsl:when test="$nationality=TJK">TJK</xsl:when><!-- 塔吉克斯坦 -->
			<xsl:when test="$nationality=TKM">TKM</xsl:when><!-- 土库曼斯坦 -->
			<xsl:when test="$nationality=TON">TO</xsl:when><!-- 汤加 -->
			<xsl:when test="$nationality=TTO">TRI</xsl:when><!-- 特立尼达和多巴 -->
			<xsl:when test="$nationality=TUN">TUN</xsl:when><!-- 突尼斯 -->
			<xsl:when test="$nationality=TWN">TW</xsl:when><!-- 中国台湾 -->
			<xsl:when test="$nationality=UGA">UGA</xsl:when><!-- 乌干达 -->
			<xsl:when test="$nationality=URY">URU</xsl:when><!-- 乌拉圭 -->
			<xsl:when test="$nationality=UZB">UZB</xsl:when><!-- 乌兹别克斯坦 -->
			<xsl:when test="$nationality=VEN">VEN</xsl:when><!-- 委内瑞拉 -->
			<xsl:when test="$nationality=VGB">IVB</xsl:when><!-- 英属维尔京群岛 -->
			<xsl:when test="$nationality=YEM">YEM</xsl:when><!-- 也门 -->
			<xsl:when test="$nationality=ZAF">SFA</xsl:when><!-- 南非 -->
			<xsl:when test="$nationality=ZMB">ZAM</xsl:when><!-- 赞比亚 -->
			<xsl:when test="$nationality=ZWE">ZIM</xsl:when><!-- 津巴布韦 -->
			<xsl:when test="$nationality=VNM">VIE</xsl:when><!-- 越南 -->
			<xsl:otherwise>OTH</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>