<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TXLife">
		<TranData>
		   <Head>
		   	   <!-- �������� -->
		       <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
		       <!-- ����ʱ�� -->
		       <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
		       <!-- ���д��� -->
		       <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
		       <!-- �������� -->
		       <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>
		       <!-- ������� -->
		       <NodeNo><xsl:value-of select="Branch"/></NodeNo>
		       <!-- ��Ա���� -->
		       <TellerNo><xsl:value-of select="Teller"/></TellerNo>
		      	<!-- ������ˮ�� -->
		       <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>
		       <!-- YBT��֯�Ľڵ���Ϣ -->
			   <xsl:copy-of select="Head/*"/>
		   </Head>
		   <Body>
		   	   <SaleChannel>0</SaleChannel> <!-- ���� -->
		       <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>	<!-- Ͷ������ -->
		       <ContPrtNo />	<!--����ӡˢ��  -->
		       <PolApplyDate><xsl:value-of select="SubmissionDate"/></PolApplyDate>	
		       <GetPolMode>1</GetPolMode>
		       <HealthNotice><xsl:value-of select="HealthIndicator"/></HealthNotice>
		       <AccName><xsl:value-of select="PolicyHolder/FullName"/></AccName>
		       <AccNo><xsl:value-of select="Banking[AccountType='0' or AccountType='2']/AccountNumber"/></AccNo>
		   	   <SaleName><xsl:value-of select="AgentSalesName"/></SaleName>
		       <SaleStaff><xsl:value-of select="AgentSalesCode"/></SaleStaff>
		       <SaleCertNo><xsl:value-of select="Credentials"/></SaleCertNo>
		       <!--�ɷ���ʽ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
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
		        	<IDType><!-- Ͷ����֤������ -->
		         	<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="PolicyHolder/GovtIDTC" />
						</xsl:with-param>
					</xsl:call-template> 
		         	</IDType>
			        <IDNo><xsl:value-of select="PolicyHolder/GovtID"/></IDNo>
			        <JobCode><!-- Ͷ����ְҵ��� -->
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
			        <!-- ���� -->
					<Stature><xsl:value-of select="PolicyHolder/Height"/></Stature>
					<!-- ��� -->
					<Weight><xsl:value-of select="PolicyHolder/Weight"/></Weight>
					<MaritalStatus><xsl:value-of select="PolicyHolder/Marriage"/></MaritalStatus>
			     	<YearSalary><xsl:value-of select="PolicyHolder/AnnualIncome div 10000"/></YearSalary>
			   	 	<IdExpDate><xsl:value-of select="PolicyHolder/ExpireDate"/></IdExpDate>
			    	<Address><xsl:value-of select="PolicyHolder/Line1"/></Address>
			    	<ZipCode><xsl:value-of select="PolicyHolder/Zip"/></ZipCode>
			    	<Mobile><xsl:value-of select="PolicyHolder/MobileNumber"/></Mobile>
				    <Phone><xsl:value-of select="PolicyHolder/DialNumber"/></Phone>
				    <Email><xsl:value-of select="PolicyHolder/Email"/></Email>
			        <!-- Ͷ�����뱻���˹�ϵ -->
			        <RelaToInsured>
				        <xsl:choose>
				         	<!-- Ͷ���˳������ڴ��ڵ��ڱ����� -->
				         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(PolicyHolder/BirthDate,Insured/BirthDate,'D')>=0">
				         		<xsl:call-template name="tran_relation1">
							       <xsl:with-param name="relation">
								    	<xsl:value-of select="PolicyHolder/RelatedToInsuredRoleCode" />
								   </xsl:with-param>
							 	</xsl:call-template> 
				         	</xsl:when>
				         	<!-- Ͷ���˳�������С�ڱ����� -->
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
		           <IDType><!-- ������֤������ -->
			         	<xsl:call-template name="tran_idtype">
							<xsl:with-param name="idtype">
								<xsl:value-of select="Insured/GovtIDTC" />
							</xsl:with-param>
						</xsl:call-template> 
		           </IDType>
		           <IDNo><xsl:value-of select="Insured/GovtID"/></IDNo>
		           <JobCode><!-- ������ְҵ��� -->
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
		           <!-- ���� -->
				   <Stature><xsl:value-of select="Insured/Height"/></Stature>
				   <!-- ��� -->
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
		       <!-- �����˳������� -->
		       <xsl:variable name="tInsuBirthDay" select="Insured/BirthDate" />
		       <!--��������˽ڵ� -->
		       <xsl:choose>
					<xsl:when test="BeneficiaryCount !='0'">
					 	<xsl:variable name="count" 	select="BeneficiaryCount" /><!-- ��������˸��� -->
					 	<xsl:for-each select="(//*)[position() &lt;= $count]"><!-- ѡȡ���нڵ�����ڽڵ��б�������ѡ���ڵ��λ�ã��������ţ�<=��������˸��� -->
					 		<xsl:variable name="tBeneficiary" select="concat('Beneficiary',position() )"></xsl:variable><!-- ���������Ľڵ������ڴ���Ľڵ㼯���е�λ�ã�����Beneficiary�ַ�������Ϊ��ǰ��������� -->
					 		<xsl:for-each select="/TXLife/*"><!--  ���ڵ�������ӽڵ� -->
					 			<xsl:if test="local-name()= $tBeneficiary"><!-- ������Ϊ��ǰ��������� -->
						 			<xsl:variable name="tnode" select="node()" /><!-- �����ֲ�����ƥ������Խڵ�͸��ڵ�֮��������κνڵ�[�Ǹ��ڵ㡢���Խڵ��Ԫ��(���ڵ���Ԫ��)] -->
							 		<Bnf>
							 			<Type>1</Type> <!-- ��������� -->
										<Grade>
										<xsl:if test="../ProductCode='022J0300'"><!--������ -->
										    <xsl:value-of select="No+1" /><!--���ڽ��������������˳���2��ʼ��1Ĭ��Ϊ���������� -->
										</xsl:if>
										<xsl:if test="../ProductCode!='022J0300'"><!--�ǽ����� -->
										    <xsl:value-of select="No" />
										</xsl:if>
										</Grade>
										<Name><xsl:value-of select="FullName" /></Name>
										<!-- �������Ա� -->
										<Sex>
											<xsl:call-template name="tran_sex">
												<xsl:with-param name="sex">
													<xsl:value-of select="Gender" />
												</xsl:with-param>
											</xsl:call-template>
										</Sex>
										<Birthday><xsl:value-of select="BirthDate" /></Birthday>
										<!-- ������֤������ -->
										<IDType>
											<xsl:call-template name="tran_idtype">
												<xsl:with-param name="idtype">
													<xsl:value-of select="GovtIDTC" />
												</xsl:with-param>
											</xsl:call-template>
										</IDType>
										<IDNo><xsl:value-of select="GovtID" /></IDNo>
									    <!-- �������뱻���˹�ϵ -->
									    <RelaToInsured>
									   	<xsl:choose>
								         	<!-- �����˳������ڴ��ڵ��ڱ����� -->
								         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(BirthDate,$tInsuBirthDay,'D')>=0">
								         		<xsl:call-template name="tran_relation1">
											      	<xsl:with-param name="relation">
												    	<xsl:value-of select="RelatedToInsuredRoleCode" />
												  	</xsl:with-param>
												</xsl:call-template> 
								         	</xsl:when>
								         	<!-- �����˳�������С�ڱ����� -->
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
										<!-- ������֤����Ч��-->
										<IdExpDate><xsl:value-of select="ExpireDate" /></IdExpDate>
										<BeneficType>N</BeneficType>
							 		</Bnf>
						 		</xsl:if>
					 		</xsl:for-each>
					 	</xsl:for-each>
					 </xsl:when>
			   </xsl:choose>
			   <!--���������˽ڵ� -->
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
					         	<!-- Ͷ���˳������ڴ��ڵ��ڱ����� -->
					         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(BirthDate,$tInsuBirthDay,'D')>=0">
					         		<xsl:call-template name="tran_relation1">
								      	<xsl:with-param name="relation">
									    	<xsl:value-of select="Relation" />
									  	</xsl:with-param>
									</xsl:call-template> 
					         	</xsl:when>
					         	<!-- Ͷ���˳�������С�ڱ����� -->
					         	<xsl:otherwise>
					         		<xsl:call-template name="tran_relation2">
								      	<xsl:with-param name="relation">
									    	<xsl:value-of select="Relation" />
									  </xsl:with-param>
									</xsl:call-template> 
					         	</xsl:otherwise>
				          	</xsl:choose>
			    	    </RelaToInsured>
			            <!-- �������뱻���˹�ϵ -->
			            <Lot><xsl:value-of select="InterestPercent"/></Lot>
			            <IdExpDate><xsl:value-of select="ExpireDate"/></IdExpDate>
			            <BeneficType>N</BeneficType>
			       </Bnf>
			   </xsl:for-each>
		       <Risk>
		         	<RiskCode><xsl:value-of select="ProductCode"/></RiskCode>
		         	<MainRiskCode><xsl:value-of select="ProductCode"/></MainRiskCode>
		         	<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Amnt>
		         	<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Prem><!--���ĸ��ݱ��������㱣��,���ǶԱ��ѽڵ��зǿ�У��,����1000Ϊ�ǿ�У��ֵ��ʵ������ -->
		         	<Mult><xsl:value-of select="IntialNumberOfUnits"/></Mult>
		         	<PayMode>B</PayMode>
		         	<!-- ���ѽ��ɷ�ʽ���� -->
				 	<xsl:if test="ProductCode='011A0100'"><!--�����սɷѷ�ʽ�̶� -->
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
			   <xsl:if test="ProductCode='022J0300'"><!-- ����������ڵ� -->
			    <Loan>
		          <LoanNo><xsl:value-of select="Loan/LoanNo"/></LoanNo>                <!-- �����ͬ��-->
		          <LoanBankName><xsl:value-of select="Loan/LoanBankName"/></LoanBankName>    <!-- �����������-->
		          <LoanDate><xsl:value-of select="Loan/LoanDate"/></LoanDate>            <!-- ��������yyyyMMdd -->
		          <LoanEndDate><xsl:value-of select="Loan/LoanEndDate"/></LoanEndDate>      <!-- �������yyyyMMdd-->
		          <LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Loan/LoanPrem)"/></LoanPrem><!-- ������-->
		          <InsuDate><xsl:value-of select="Loan/InsuDate"/></InsuDate>            <!--��������:�� -->
		          <IsPrepayInsu><xsl:value-of select="Loan/IsPrepayInsu"/></IsPrepayInsu>    <!-- �Ƿ�Ԥ������-->
		          <PrepayInsuPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Loan/PrepayInsuPrem)"/></PrepayInsuPrem><!--Ԥ�����ѽ��-->
		          <PrepayYear><xsl:value-of select="Loan/PrepayYear"/></PrepayYear>        <!--Ԥ������:��-->
		        </Loan>
		       </xsl:if>
		  </Body>
		</TranData>
	</xsl:template>
	
    <!--��������˳��װ�� ���ǽ�����-->
    <xsl:template name="tran_Grade">
		<xsl:param name="Grade"></xsl:param>
		<xsl:choose>
			<xsl:when test="$Grade = 'a'">1</xsl:when>
			<xsl:when test="$Grade = 'b'">2</xsl:when>
			<xsl:when test="$Grade = 'c'">3</xsl:when>
			<xsl:when test="$Grade = 'd'">4</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<!--��������˳��ת���� ��������022J0300-->
    <xsl:template name="tran_Grade1">
		<xsl:param name="Grade"></xsl:param>
		<xsl:choose>
			<xsl:when test="$Grade = 'a'">3</xsl:when>
			<xsl:when test="$Grade = 'b'">4</xsl:when>
			<xsl:when test="$Grade = 'c'">5</xsl:when>
			<xsl:when test="$Grade = 'd'">6</xsl:when>
		</xsl:choose>
	</xsl:template>
	
    <!--�Ա�ת�� -->
	<xsl:template name="tran_sex">
		<xsl:param name="sex"></xsl:param>
		<xsl:choose>
			<xsl:when test="$sex = 1">0</xsl:when><!-- �� -->
			<xsl:when test="$sex = 2">1</xsl:when><!-- Ů -->
			<xsl:otherwise>2</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_idtype"><!-- ��������֤������ת��Ϊ����֤������ -->
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = 0">0</xsl:when><!-- ���֤ -->
			<xsl:when test="$idtype = 1">2</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = 2">1</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = 3">F</xsl:when><!-- �۰�ͨ��֤ -->
			<xsl:when test="$idtype = 4">F</xsl:when><!-- ̨��ͨ��֤ -->
			<xsl:when test="$idtype = 9">99</xsl:when><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 1">0</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 2">6</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 4">1</xsl:when><!-- �½� -->
			<xsl:when test="$payintv = 5">12</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = 6">-1</xsl:when><!-- ������ -->
			<xsl:when test="$payintv = 7">-1</xsl:when><!-- ĳ���� -->
			<xsl:when test="$payintv = 8">-1</xsl:when><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payendyearflag">
		<xsl:param name="payendyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payendyearflag = 1">A</xsl:when><!-- ���� -->
			<xsl:when test="$payendyearflag = 2"></xsl:when><!-- ���� -->
			<xsl:when test="$payendyearflag = 3"></xsl:when><!-- ���� -->
			<xsl:when test="$payendyearflag = 4">M</xsl:when><!-- �½� -->
			<xsl:when test="$payendyearflag = 5">Y</xsl:when><!-- �꽻 -->
			<xsl:when test="$payendyearflag = 6"></xsl:when><!-- ������ -->
			<xsl:when test="$payendyearflag = 7">A</xsl:when><!-- ĳ���� -->
			<xsl:when test="$payendyearflag = 8">A</xsl:when><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_getmode">
		<xsl:param name="getmode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$getmode = 11">2</xsl:when><!-- �ֽ� -->
			<xsl:when test="$getmode = 12">1</xsl:when><!-- �ۼ���Ϣ -->
			<xsl:when test="$getmode = 13">3</xsl:when><!-- �ֽ����� -->
			<xsl:when test="$getmode = 14">5</xsl:when><!-- ����� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_BenefitMode">
		<xsl:param name="BenefitMode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$BenefitMode = 1">0</xsl:when><!-- һ���Ը��� -->
			<xsl:when test="$BenefitMode = 2">12</xsl:when><!-- ����� -->
			<xsl:when test="$BenefitMode = 3">6</xsl:when><!-- ������� -->
			<xsl:when test="$BenefitMode = 4">3</xsl:when><!-- ������ -->
			<xsl:when test="$BenefitMode = 5">1</xsl:when><!-- �¸��� -->
			<xsl:when test="$BenefitMode = 6"></xsl:when><!-- �������� -->
			<xsl:otherwise></xsl:otherwise>
			<!-- ����� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = 0">A</xsl:when><!-- �޹� -->
			<xsl:when test="$insuyearflag = 1">A</xsl:when><!-- ������ -->
			<xsl:when test="$insuyearflag = 2">Y</xsl:when><!-- �����ޱ� -->
			<xsl:when test="$insuyearflag = 3">A</xsl:when><!-- ����ĳȷ������ -->
			<xsl:when test="$insuyearflag = 4">M</xsl:when><!-- ���±� -->
			<xsl:when test="$insuyearflag = 5">D</xsl:when><!-- ���챣 -->
		</xsl:choose>
	</xsl:template>
	
	<!-- Ͷ���˳������ڴ��ڡ����ڱ����˳�������
		    Ͷ���ˡ��������뱻���˹�ϵ -->
	<xsl:template name="tran_relation1">
	  <xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = '301'">00</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '302'">05</xsl:when><!--���� -->
			<xsl:when test="$relation = '303'">01</xsl:when><!--ĸŮ -->
			<xsl:when test="$relation = '304'">01</xsl:when><!-- ��Ů -->
			<xsl:when test="$relation = '305'">04</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '306'">01</xsl:when><!-- ĸ�� -->
			<xsl:when test="$relation = '307'">06</xsl:when><!-- �ֵ� -->
			<xsl:when test="$relation = '308'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '309'">01</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '310'">02</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '311'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '312'">09</xsl:when><!-- ��Ӷ -->
			<xsl:when test="$relation = '313'">06</xsl:when><!-- ֶ�� -->
			<xsl:when test="$relation = '314'">06</xsl:when><!-- ֶŮ -->
			<xsl:when test="$relation = '315'">06</xsl:when><!-- ��� -->
			<xsl:when test="$relation = '316'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '317'">06</xsl:when><!-- ����Ů -->
			<xsl:when test="$relation = '318'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '319'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '320'">06</xsl:when><!-- ��ϱ -->
			<xsl:when test="$relation = '321'">06</xsl:when><!-- ��ɩ -->
			<xsl:when test="$relation = '322'">06</xsl:when><!-- δ����� -->
			<xsl:when test="$relation = '323'">06</xsl:when><!-- �� -->
			<xsl:when test="$relation = '324'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '325'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '326'">06</xsl:when><!-- ��ϱ -->
			<xsl:when test="$relation = '333'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '371'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '380'">05</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '381'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '382'">06</xsl:when><!-- ��� -->
			<xsl:when test="$relation = '391'">09</xsl:when><!-- ��Ӷ -->
			<xsl:when test="$relation = '392'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '393'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '394'">06</xsl:when><!-- ͬѧ��ͬ�� -->
			<xsl:when test="$relation = '395'">06</xsl:when><!-- δ����� -->
			<xsl:when test="$relation = '396'">06</xsl:when><!-- ְԱ -->
			<xsl:when test="$relation = '397'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '398'">06</xsl:when><!-- ��ֶ��ϵ -->
			<xsl:when test="$relation = '399'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = ''">00</xsl:when><!--���ˣ��ͱ����˹�ϵδ����ʱ��Ĭ��Ϊ���� -->
		</xsl:choose>
	</xsl:template>
	
	<!-- Ͷ���˳�������С�ڱ����˳�������
		    Ͷ���ˡ��������뱻���˹�ϵ -->
	<xsl:template name="tran_relation2">
	  <xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = '301'">00</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '302'">05</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '303'">03</xsl:when><!-- Ͷ���ˡ�������ĸŮ  ��������Ů-->
			<xsl:when test="$relation = '304'">03</xsl:when><!-- Ͷ���ˡ������˸�Ů  ��������Ů-->
			<xsl:when test="$relation = '305'">04</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '306'">03</xsl:when><!-- Ͷ���ˡ�������ĸ��  ��������Ů-->
			<xsl:when test="$relation = '307'">06</xsl:when><!-- Ͷ���ˡ��������ֵ�  ����������-->
			<xsl:when test="$relation = '308'">06</xsl:when><!-- Ͷ���ˡ������˽���  ����������-->
			<xsl:when test="$relation = '309'">03</xsl:when><!-- Ͷ���ˡ������˸���  ��������Ů-->
			<xsl:when test="$relation = '310'">02</xsl:when><!-- Ͷ���ˡ������˷���  ��������ż-->
			<xsl:when test="$relation = '311'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '312'">09</xsl:when><!-- Ͷ���ˡ������˹�Ӷ  �����˹�Ӷ��ϵ-->
			<xsl:when test="$relation = '313'">06</xsl:when><!-- Ͷ���ˡ�������ֶ��  ����������-->
			<xsl:when test="$relation = '314'">06</xsl:when><!-- Ͷ���ˡ�������ֶŮ  ����������-->
			<xsl:when test="$relation = '315'">06</xsl:when><!-- Ͷ���ˡ������˽��  ����������-->
			<xsl:when test="$relation = '316'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '317'">06</xsl:when><!-- Ͷ���ˡ�����������Ů  ����������-->
			<xsl:when test="$relation = '318'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '319'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '320'">06</xsl:when><!-- Ͷ���ˡ���������ϱ  ����������-->
			<xsl:when test="$relation = '321'">06</xsl:when><!-- Ͷ���ˡ������˹�ɩ  ����������-->
			<xsl:when test="$relation = '322'">06</xsl:when><!-- Ͷ���ˡ�������δ�����  ����������-->
			<xsl:when test="$relation = '323'">06</xsl:when><!-- Ͷ���ˡ���������  ����������-->
			<xsl:when test="$relation = '324'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '325'">06</xsl:when><!-- Ͷ���ˡ������˸���  ����������-->
			<xsl:when test="$relation = '326'">06</xsl:when><!-- Ͷ���ˡ������˹�ϱ  ����������-->
			<xsl:when test="$relation = '333'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '371'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '380'">05</xsl:when><!-- Ͷ���ˡ������˷���  �����˷����໤��-->
			<xsl:when test="$relation = '381'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '382'">06</xsl:when><!-- Ͷ���ˡ������˽��  ����������-->
			<xsl:when test="$relation = '391'">09</xsl:when><!-- Ͷ���ˡ������˹�Ӷ  �����˹�Ӷ��ϵ-->
			<xsl:when test="$relation = '392'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '393'">06</xsl:when><!-- Ͷ���ˡ�����������  ����������-->
			<xsl:when test="$relation = '394'">06</xsl:when><!-- Ͷ���ˡ�������ͬѧ��ͬ��  ����������-->
			<xsl:when test="$relation = '395'">06</xsl:when><!-- Ͷ���ˡ�������δ�����  ����������-->
			<xsl:when test="$relation = '396'">06</xsl:when><!-- Ͷ���ˡ�������ְԱ  ����������-->
			<xsl:when test="$relation = '397'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '398'">06</xsl:when><!-- Ͷ���ˡ���������ֶ��ϵ  ����������-->
			<xsl:when test="$relation = '399'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = ''">00</xsl:when><!--���ˣ��ͱ����˹�ϵδ����ʱ��Ĭ��Ϊ���� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_jobcode">
		<xsl:param name="jobcode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$jobcode = 1001">2099904</xsl:when><!-- ���� �����ж��ֵ -->
			<xsl:when test="$jobcode = 1002">8000002</xsl:when><!-- ��������Ա  8000003��8000002-->
			<xsl:when test="$jobcode = 1003">2099907</xsl:when><!-- ��ͯ��18��ǰѧ�� ����һ��ѧ�� -->
			<xsl:when test="$jobcode = 1004"></xsl:when><!-- �����ˣ���������ҵ�� �����ж��ֵ��û��ȷ��ӳ��-->
			<xsl:when test="$jobcode = 1005">2021106</xsl:when><!-- ����ʦ  6050710��6051807 -->
			<xsl:when test="$jobcode = 1006">6030516</xsl:when><!-- ��ʦ  6059901��6080112��6099902��6180106 -->
			<xsl:when test="$jobcode = 1007">4040701</xsl:when><!-- ������ �����ж��ֵ -->
			<xsl:when test="$jobcode = 1008"></xsl:when><!-- һ�����ڡ����� 4030504��4040105��4040208��4050208��4050304 -->
			<xsl:when test="$jobcode = 1009">6050712</xsl:when><!-- ��ࡢ�๤ 6040117��6050712��5020408��6020210��6059902��-->
			<xsl:when test="$jobcode = 1010">6010316</xsl:when><!-- ��Ӫ�ߣ������ֳ��ߣ�-->
			<xsl:when test="$jobcode = 1011">6010317</xsl:when><!-- ��Ӫ�ߣ��ֳ��ල�ߣ� -->
			<xsl:when test="$jobcode = 1012">6010608</xsl:when><!-- ������Ա -->
			<xsl:when test="jobcode = 1013"></xsl:when><!-- һ�����ڹ�����Ա 4050111��2022111 -->
			<xsl:when test="$jobcode = 1014">4030503</xsl:when><!-- ������Ա 4030503��6010318 -->
			<xsl:when test="$jobcode = 1015">6230710</xsl:when><!-- �а��̡��๤ -->
			<xsl:when test="$jobcode = 1016">2129901</xsl:when><!-- ������Ա -->
			<xsl:when test="$jobcode = 1017"></xsl:when><!-- ������Ա -->
			<xsl:when test="$jobcode = 1018"></xsl:when><!-- ҵ��Ա �����ж��ֵ -->
			<xsl:when test="$jobcode = 1019"></xsl:when><!-- ��ʦ �����ж��ֵ-->
			<xsl:when test="$jobcode = 1020"></xsl:when><!-- ѧ�� �����ж��ֵ-->
			<xsl:when test="$jobcode = 1021">2099902</xsl:when><!-- ��ѵ�̹١�������ʦ -->
			<xsl:when test="$jobcode = 1022">2080301</xsl:when><!-- ��ʦ -->
			<xsl:when test="$jobcode = 1023">2060301</xsl:when><!-- ���ʦ -->
			<xsl:when test="$jobcode = 1024">4071203</xsl:when><!-- ��ͥ����  -->
			<xsl:when test="$jobcode = 1025">3020101</xsl:when><!-- ����������������Ա -->
			<xsl:when test="$jobcode = 9999999"></xsl:when><!-- 9999999999 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_nationality">
		<xsl:param name="nationality"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nationality=ARA">UAE</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=AUS">AUS</xsl:when><!-- �Ĵ����� -->
			<xsl:when test="$nationality=BIE">BLR</xsl:when><!-- �׶���˹ -->
			<xsl:when test="$nationality=BRE">BAS</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=D">DEU</xsl:when><!-- �¹� -->
			<xsl:when test="$nationality=DAN">DEN</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=E">ESP</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=F">FRA</xsl:when><!--���� -->
			<xsl:when test="$nationality=FIN">FIN</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=GB">ENG</xsl:when><!-- Ӣ�� -->
			<xsl:when test="$nationality=GR">GRE</xsl:when><!--ϣ��  -->
			<xsl:when test="$nationality=H">HUN</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=IN">IND</xsl:when><!-- ӡ�� -->
			<xsl:when test="$nationality=IND">INA</xsl:when><!-- ӡ�� -->
			<xsl:when test="$nationality=JAP">JAN</xsl:when><!-- �ձ� -->
			<xsl:when test="$nationality=L">LUX</xsl:when><!-- ¬ɭ�� -->
			<xsl:when test="$nationality=MAL">MY</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=NL">NL</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=POL">PL</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=POR">POR</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=ROU">ROM</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=RUS">RUS</xsl:when><!-- ��� -->
			<xsl:when test="$nationality=TCH">CZE</xsl:when><!-- �ݿ� -->
			<xsl:when test="$nationality=USA">USA</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=WEL">WLS</xsl:when><!-- ����ʿ -->
			<xsl:when test="$nationality=ABW">ABW</xsl:when><!-- ��³�� -->
			<xsl:when test="$nationality=ANT">AHO</xsl:when><!-- ����������˹ -->
			<xsl:when test="$nationality=ARE">UAE</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=BRA">BAS</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=BHR">BRN</xsl:when><!-- ����-->
			<xsl:when test="$nationality=FRA">FRA</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=BMU">BER</xsl:when><!-- ��Ľ�� -->
			<xsl:when test="$nationality=BOL">BOL</xsl:when><!-- ����ά�� -->
			<xsl:when test="$nationality=JPN">JAN</xsl:when><!-- �ձ� -->
			<xsl:when test="$nationality=BRN">BRU</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=BWA">BOT</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=CAN">CAN</xsl:when><!-- ���ô� -->
			<xsl:when test="$nationality=CHE">SUI</xsl:when><!-- ��ʿ -->
			<xsl:when test="$nationality=CHL">CHI</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=PRT">POR</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=COL">COL</xsl:when><!-- ���ױ��� -->
			<xsl:when test="$nationality=CRI">CRC</xsl:when><!-- ��˹����� -->
			<xsl:when test="$nationality=CUB">CUB</xsl:when><!-- �Ű� -->
			<xsl:when test="$nationality=CYP">CYP</xsl:when><!-- ����·˹ -->
			<xsl:when test="$nationality=CZE">CZE</xsl:when><!-- �ݿ� -->
			<xsl:when test="$nationality=BLR">BLR</xsl:when><!-- �׶���˹ -->
			<xsl:when test="$nationality=GBR">ENG</xsl:when><!-- Ӣ�� -->
			<xsl:when test="$nationality=DOM">DOM</xsl:when><!-- ������� -->
			<xsl:when test="$nationality=DZA">ALG</xsl:when><!-- ���������� -->
			<xsl:when test="$nationality=ECU">ECU</xsl:when><!-- ��϶�� -->
			<xsl:when test="$nationality=EGY">EGY</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=ROM">ROM</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=ETH">ETH</xsl:when><!-- ��������� -->
			<xsl:when test="$nationality=DEU">DEU</xsl:when><!-- �¹� -->
			<xsl:when test="$nationality=FRO">FAI</xsl:when><!-- ����Ⱥ�� -->
			<xsl:when test="$nationality=LUX">LUX</xsl:when><!-- ¬ɭ�� -->
			<xsl:when test="$nationality=GEO">GEO</xsl:when><!-- ��³���� -->
			<xsl:when test="$nationality=GHA">DG</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=CN or $nationality=CHN">CHN</xsl:when><!-- �й� -->
			<xsl:when test="$nationality=HKG">HK</xsl:when><!-- ��� -->
			<xsl:when test="$nationality=HND">HON</xsl:when><!-- �鶼��˹ -->
			<xsl:when test="$nationality=HRV">CRO</xsl:when><!-- ���޵��� -->
			<xsl:when test="$nationality=BUL or $nationality=BGR">BUL</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=IDN">INA</xsl:when><!-- ӡ�������� -->
			<xsl:when test="$nationality=IRN">IRI</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=IRQ">IRQ</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=ISL">ISL</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=ISR">ISR</xsl:when><!-- ��ɫ�� -->
			<xsl:when test="$nationality=JAM">JAM</xsl:when><!-- ����� -->
			<xsl:when test="$nationality=GRC">GRE</xsl:when><!-- ϣ�� -->
			<xsl:when test="$nationality=KAZ">KAZ</xsl:when><!-- ������˹̹ -->
			<xsl:when test="$nationality=KEN">KEN</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=KOR">KOR</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=LBN">LIB</xsl:when><!-- ����� -->
			<xsl:when test="$nationality=LBY">LBA</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=LIE">LIE</xsl:when><!-- ��֧��ʿ�� -->
			<xsl:when test="$nationality=LKA">SLK</xsl:when><!-- ˹������ -->
			<xsl:when test="$nationality=LTU">LTU</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=HUN">HUN</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=LVA">LAT</xsl:when><!-- ����ά�� -->
			<xsl:when test="$nationality=MAC">MO</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=MAR">MAR</xsl:when><!-- Ħ��� -->
			<xsl:when test="$nationality=MCO">MNC</xsl:when><!-- Ħ�ɸ� -->
			<xsl:when test="$nationality=MEX">MEX</xsl:when><!-- ī���� -->
			<xsl:when test="$nationality=MNG">MGL</xsl:when><!-- �ɹ� -->
			<xsl:when test="$nationality=MWI">MAW</xsl:when><!-- ����ά -->
			<xsl:when test="$nationality=MYS">MY</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=NAM">NAM</xsl:when><!-- ���ױ��� -->
			<xsl:when test="$nationality=NGA">NGR</xsl:when><!-- �������� -->
			<xsl:when test="$nationality=ESP">ESP</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=NOR">NO</xsl:when><!-- Ų�� -->
			<xsl:when test="$nationality=PAN">PAN</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=PHL">PHL</xsl:when><!-- ���ɱ� -->
			<xsl:when test="$nationality=NIC">NCA</xsl:when><!-- ������� -->
			<xsl:when test="$nationality=PNG">PNG</xsl:when><!-- �Ͳ����¼����� -->
			<xsl:when test="$nationality=DNK">DEN</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=PRY">PAR</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=PSE">PLE</xsl:when><!-- ����˹̹ -->
			<xsl:when test="$nationality=QAT">QAT</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=NLD">NL</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=RWA">RWA</xsl:when><!-- ¬���� -->
			<xsl:when test="$nationality=SDN">SUD</xsl:when><!-- �յ� -->
			<xsl:when test="$nationality=SGP">SG</xsl:when><!-- �¼��� -->
			<xsl:when test="$nationality=SMR">SMR</xsl:when><!--ʥ����ŵ -->
			<xsl:when test="$nationality=SOM">SOM</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=SUR">SUR</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=SVK">SVK</xsl:when><!-- ˹�工�� -->
			<xsl:when test="$nationality=SVN">SLO</xsl:when><!-- ˹�������� -->
			<xsl:when test="$nationality=SWE">SWE</xsl:when><!-- ��� -->
			<xsl:when test="$nationality=SYC">SEY</xsl:when><!-- ����� -->
			<xsl:when test="$nationality=SYR">SYR</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=THA">THA</xsl:when><!-- ̩�� -->
			<xsl:when test="$nationality=TJK">TJK</xsl:when><!-- ������˹̹ -->
			<xsl:when test="$nationality=TKM">TKM</xsl:when><!-- ������˹̹ -->
			<xsl:when test="$nationality=TON">TO</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=TTO">TRI</xsl:when><!-- �������Ͷ�� -->
			<xsl:when test="$nationality=TUN">TUN</xsl:when><!-- ͻ��˹ -->
			<xsl:when test="$nationality=TWN">TW</xsl:when><!-- �й�̨�� -->
			<xsl:when test="$nationality=UGA">UGA</xsl:when><!-- �ڸɴ� -->
			<xsl:when test="$nationality=URY">URU</xsl:when><!-- ������ -->
			<xsl:when test="$nationality=UZB">UZB</xsl:when><!-- ���ȱ��˹̹ -->
			<xsl:when test="$nationality=VEN">VEN</xsl:when><!-- ί������ -->
			<xsl:when test="$nationality=VGB">IVB</xsl:when><!-- Ӣ��ά����Ⱥ�� -->
			<xsl:when test="$nationality=YEM">YEM</xsl:when><!-- Ҳ�� -->
			<xsl:when test="$nationality=ZAF">SFA</xsl:when><!-- �Ϸ� -->
			<xsl:when test="$nationality=ZMB">ZAM</xsl:when><!-- �ޱ��� -->
			<xsl:when test="$nationality=ZWE">ZIM</xsl:when><!-- ��Ͳ�Τ -->
			<xsl:when test="$nationality=VNM">VIE</xsl:when><!-- Խ�� -->
			<xsl:otherwise>OTH</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>