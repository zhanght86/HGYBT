<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TXLife">
<TranData>
   <Head>
      <TranDate><xsl:value-of select="TransExeDate"/></TranDate>
      <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.time8to6(TransExeTime)"/></TranTime>
      <ZoneNo><xsl:value-of select="BankCode"/></ZoneNo>			<!-- �������� -->
      <NodeNo><xsl:value-of select="Branch"/></NodeNo>				<!-- ������� -->
      <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>	<!-- ���д��� -->
      <TellerNo><xsl:value-of select="Teller"/></TellerNo>			<!-- ��Ա���� -->
      <TranNo><xsl:value-of select="TransRefGUID"/></TranNo>		<!-- ��ˮ�� -->
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
   	  <SaleChannel>0</SaleChannel> <!-- ���� -->
   	  <SaleName><xsl:value-of select="AgentSalesName"/></SaleName>
      <SaleStaff><xsl:value-of select="AgentSalesCode"/></SaleStaff>
      <SaleCertNo><xsl:value-of select="Credentials"/></SaleCertNo>
      <ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(HOAppFormNumber)"/></ProposalPrtNo>	<!-- Ͷ������ -->
      <ContPrtNo />	<!--����ӡˢ��  -->
      <PolApplyDate><xsl:value-of select="SubmissionDate"/></PolApplyDate>	
      <AccNo><xsl:value-of select="Banking[AccountType='0' or AccountType='2']/AccountNumber"/></AccNo>
      <AccName><xsl:value-of select="PolicyHolder/FullName"/></AccName>
      <!--�ɷ���ʽ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
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
         <IDType><!-- Ͷ����֤������ -->
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
         <JobCode><!-- Ͷ����ְҵ��� -->
         	<xsl:call-template name="tran_jobcode">
					<xsl:with-param name="jobcode">
						<xsl:value-of
							select="PolicyHolder/OccupationType" />
					</xsl:with-param>
			</xsl:call-template>
         </JobCode>
         <!-- ���� -->
		 <Stature><xsl:value-of select="PolicyHolder/Height"/></Stature>
		 <!-- ��� -->
		 <Weight><xsl:value-of select="PolicyHolder/Weight"/></Weight>
		 <MaritalStatus><xsl:value-of select="PolicyHolder/Marriage"/></MaritalStatus>
         <YearSalary><xsl:value-of select="PolicyHolder/AnnualIncome div 10000"/></YearSalary>
         <FamilyYearSalary><xsl:value-of select="PolicyHolder/AnnualHomeIncome div 10000"/></FamilyYearSalary>
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
         <JobCode><!-- ������ְҵ��� -->
         	<xsl:call-template name="tran_jobcode">
					<xsl:with-param name="jobcode">
						<xsl:value-of
							select="Insured/OccupationType" />
					</xsl:with-param>
			</xsl:call-template>
         </JobCode>
          <!-- ���� -->
		 <Stature><xsl:value-of select="Insured/Height"/></Stature>
		 <!-- ��� -->
		 <Weight><xsl:value-of select="Insured/Weight"/></Weight>
		 <MaritalStatus><xsl:value-of select="Insured/Marriage"/></MaritalStatus>
         <YearSalary><xsl:value-of select="Insured/AnnualIncome div 10000"/></YearSalary>
         <RiskAssessmentResult><xsl:value-of select="Insured/RiskIndicator"/></RiskAssessmentResult>
         <RiskAssessmentEndDate></RiskAssessmentEndDate>
         <PremiumBudget></PremiumBudget>
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
						<Birthday>
							<xsl:value-of
								select="BirthDate" />
						</Birthday>
						<IDType><!-- ������֤������ -->
							<xsl:call-template name="tran_idtype">
								<xsl:with-param name="idtype">
									<xsl:value-of select="GovtIDTC" />
								</xsl:with-param>
							</xsl:call-template>
						</IDType>
						<IDNo><xsl:value-of select="GovtID" /></IDNo>
						<!-- ������֤����Ч��-->
							<IdExpDate><xsl:value-of select="ExpireDate" /></IdExpDate>
						<Lot>
							<xsl:value-of select="InterestPercent" />
						</Lot>
				 		<!-- <RelaToInsured>
				 		  <xsl:call-template name="tran_relation">
		                     <xsl:with-param name="relation">
			                   <xsl:value-of select="RelatedToInsuredRoleCode" />
			                 </xsl:with-param>
			              </xsl:call-template>
					   </RelaToInsured> -->
					   <!-- �������뱻���˹�ϵ -->
					   <RelaToInsured>
					   	<xsl:choose>
				         	<!-- Ͷ���˳������ڴ��ڵ��ڱ����� -->
				         	<xsl:when test="java:com.sinosoft.lis.pubfun.PubFun.calInterval(BirthDate,$tInsuBirthDay,'D')>=0">
				         		<xsl:call-template name="tran_relation1">
							      <xsl:with-param name="relation">
								    <xsl:value-of select="RelatedToInsuredRoleCode" />
								  </xsl:with-param>
							</xsl:call-template> 
				         	</xsl:when>
				         	<!-- Ͷ���˳�������С�ڱ����� -->
				         	<xsl:otherwise>
				         		<xsl:call-template name="tran_relation2">
							      <xsl:with-param name="relation">
								    <xsl:value-of select="RelatedToInsuredRoleCode" />
								  </xsl:with-param>
							</xsl:call-template> 
				         	</xsl:otherwise>
				         </xsl:choose>
	         			</RelaToInsured>
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
           <IdExpDate><xsl:value-of select="ExpireDate"/></IdExpDate>
           <Lot><xsl:value-of select="InterestPercent"/></Lot>
           <!--  <RelaToInsured>
             <xsl:call-template name="tran_relation">
		       <xsl:with-param name="relation">
			       <xsl:value-of select="Relation" />
			   </xsl:with-param>
			 </xsl:call-template>
           </RelaToInsured>-->
           <!-- �������뱻���˹�ϵ -->
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
           <BeneficType>N</BeneficType>
      </Bnf>
	  </xsl:for-each>
    	<Risk>
         <RiskCode><xsl:value-of select="ProductCode"/></RiskCode>
         <MainRiskCode><xsl:value-of select="ProductCode"/></MainRiskCode>
         <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PaymentAmt)"/></Amnt>
         <Prem>1000</Prem><!--���ĸ��ݱ��������㱣��,���ǶԱ��ѽڵ��зǿ�У��,����1000Ϊ�ǿ�У��ֵ��ʵ������ -->
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
           <PayEndYear><xsl:value-of select="PaymentDuration"/></PayEndYear>
           <PayEndYearFlag>
         	 <xsl:call-template name="tran_payendyearflag">
				<xsl:with-param name="payendyearflag">
					<xsl:value-of select="PaymentMode" />
				</xsl:with-param>
			 </xsl:call-template>
           </PayEndYearFlag>
         </xsl:if>
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
			<xsl:when test="$idtype = 1">A</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = 2">1</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = 3">B</xsl:when><!-- �۰�ͨ��֤ -->
			<xsl:when test="$idtype = 4">E</xsl:when><!-- ̨��ͨ��֤ -->
			<xsl:when test="$idtype = 9">8</xsl:when><!-- ���� -->
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
			<xsl:when test="$payendyearflag = 2">6</xsl:when><!-- ���� -->
			<xsl:when test="$payendyearflag = 3">3</xsl:when><!-- ���� -->
			<xsl:when test="$payendyearflag = 4">1</xsl:when><!-- �½� -->
			<xsl:when test="$payendyearflag = 5">Y</xsl:when><!-- �꽻 -->
			<xsl:when test="$payendyearflag = 6">-1</xsl:when><!-- ������ -->
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
			<xsl:when test="$BenefitMode = 1">0</xsl:when><!-- ���� -->
			<xsl:when test="$BenefitMode = 2">12</xsl:when><!-- ���� -->
			<xsl:when test="$BenefitMode = 5">1</xsl:when><!-- ���� -->
			<xsl:otherwise>12</xsl:otherwise>
			<!-- ����� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = 0">A</xsl:when><!-- �޹� -->
			<xsl:when test="$insuyearflag = 1">A</xsl:when><!-- ���� -->
			<xsl:when test="$insuyearflag = 2">Y</xsl:when><!-- �� -->
			<xsl:when test="$insuyearflag = 3">A</xsl:when><!-- ���� -->
			<xsl:when test="$insuyearflag = 4">M</xsl:when><!-- �� -->
			<xsl:when test="$insuyearflag = 5">D</xsl:when><!-- �� -->
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
			<xsl:when test="$relation = '302'">05</xsl:when><!--���� -->
			<xsl:when test="$relation = '303'">03</xsl:when><!--ĸŮ -->
			<xsl:when test="$relation = '304'">03</xsl:when><!-- ��Ů -->
			<xsl:when test="$relation = '305'">04</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '306'">03</xsl:when><!-- ĸ�� -->
			<xsl:when test="$relation = '307'">06</xsl:when><!-- �ֵ� -->
			<xsl:when test="$relation = '308'">06</xsl:when><!-- ���� -->
			<xsl:when test="$relation = '309'">03</xsl:when><!-- ���� -->
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
			<xsl:when test="$jobcode = 1008"></xsl:when><!-- 4050304��4040208 -->
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
			<xsl:when test="$nationality=CAN">CAN</xsl:when><!-- ���ô� -->
			<xsl:when test="$nationality=CN or $nationality=CHN">CHN</xsl:when><!-- �й� -->
			<xsl:when test="$nationality=TWN">TW</xsl:when><!-- ̨�� -->
			<xsl:when test="$nationality=HKG">HK</xsl:when><!-- ��� -->
			<xsl:when test="$nationality=JAP">JAN</xsl:when><!-- �ձ� -->
			<xsl:when test="$nationality=KOR">KOR</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=MAC">MO</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=RUS">RUS</xsl:when><!-- ����˹���� -->
			<xsl:when test="$nationality=GBR">ENG</xsl:when><!-- Ӣ�� -->
			<xsl:when test="$nationality=USA">USA</xsl:when><!-- ���� -->
			<xsl:when test="$nationality=AUS">AUS</xsl:when><!-- �Ĵ����� -->
			<xsl:otherwise>OTH</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>