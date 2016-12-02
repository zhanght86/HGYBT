<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="Transaction">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="Transaction_Header/BkPlatDate" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="Transaction_Header/BkPlatTime" /></TranTime>
				<!-- �������� -->
				<!-- <TranCom><xsl:value-of select="Transaction_Header/BkChnlNo" /></TranCom> -->
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Transaction_Header/BkBrchNo" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0122</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="Transaction_Header/BkTellerNo" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Transaction_Header/BkPlatSeqNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- ������ -->
		<xsl:apply-templates select="Transaction_Body" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="Transaction_Body">
		<Body>
		    <!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="PbApplNo" /></ProposalPrtNo>
			<!-- ����ӡˢ�� -->
			<ContPrtNo><xsl:value-of select="BkVchNo" /></ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="../Transaction_Header/BkPlatDate" /></PolApplyDate>
			<!-- �������ͷ�ʽ -->
			<GetPolMode>
			  <xsl:call-template name="PbSendMode">
			    <xsl:with-param name="pbSendMode">
				<xsl:value-of select="PbSendMode"/></xsl:with-param>
			  </xsl:call-template>
			</GetPolMode> 
			<!-- ������֪ -->
			<HealthNotice>
			  <xsl:call-template name="TranHeal">
			    <xsl:with-param name="HealFlag">
			    <xsl:value-of select="LiHealthTag"></xsl:value-of>
			    </xsl:with-param>
			  </xsl:call-template>
			</HealthNotice>
			<!-- �˻����� -->
			<AccName><xsl:value-of select="PbHoldName" /></AccName>
			
			<!-- �˻�-->
			<AccNo><xsl:value-of select="BkAcctNo1" /></AccNo>
			
			<!-- ������Ա���� -->
		    <SaleStaff><xsl:value-of select="BkRckrNo" /></SaleStaff>
		    <!-- ������Ա����  -->
            <SaleName><xsl:value-of select="BkSaleName" /></SaleName>
            <!-- ������Ա�ʸ�֤��  -->
            <SaleCertNo><xsl:value-of select="BkSaleCertNo" /></SaleCertNo>
            
			<!-- Ͷ���� -->
			<Appnt>
			<!-- Ͷ�������� -->
				<Name>
					<xsl:value-of select="PbHoldName" />
				</Name>
				<!-- Ͷ�����Ա� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="PbHoldSex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- Ͷ�������� -->
				<Birthday>
					<xsl:value-of select="PbHoldBirdy" />
				</Birthday>
				<!-- Ͷ����֤������ -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="PbHoldIdType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- Ͷ����֤���� -->
				<IDNo>
					<xsl:value-of select="PbHoldId" />
				</IDNo>
				<!-- Ͷ����ְҵ����-->
				<JobCode>3010101</JobCode>
				<!-- Ͷ���˹��� -->
				<Nationality>
					<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="PbNationality"/>
					</xsl:with-param>
					</xsl:call-template>
				</Nationality>
				
				<Stature />
				<Weight />
				<MaritalStatus />
				<!-- Ͷ���������� -->
				<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PbInCome)"/></YearSalary>
				<!-- Ͷ����֤����Ч��-->
				<xsl:choose>
				<xsl:when test="PbIdEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="PbIdEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!-- Ͷ���˵�ַ-->
				<Address>
					<xsl:value-of select="PbHoldHomeAddr" />
				</Address>
				<!-- Ͷ���˵�λ��ַ -->
				<WorkAddress><xsl:value-of select="PbHoldAddr" /></WorkAddress>
				<!-- Ͷ������������-->
				<ZipCode>
					<xsl:value-of select="PbHoldHomePost" />
				</ZipCode>
				<!-- Ͷ���˵�λ��������-->
				<WorkZipCode><xsl:value-of select="PbHoldPost" /></WorkZipCode>
				<!-- Ͷ�����ƶ��绰-->
				<Mobile>
					<xsl:value-of select="PbHoldMobl" />
				</Mobile>
				<!-- Ͷ���˹̶��绰-->
				<Phone>
					<xsl:value-of select="PbHoldHomeTele" />
				</Phone>
				<!-- Ͷ���˵�λ�绰 -->
				<WorkPhone><xsl:value-of select="PbHoldOfficTele" /></WorkPhone>
				<!-- Ͷ��������-->
				<Email>
				<xsl:value-of select="PbHoldEmail" />
				</Email>
				
	<!-- 			<RelaToInsured> ������˵�������Ǻ��Ĵ��������20141219
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="PbHoldRcgnRela" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured> -->
				<RelaToInsured>
							<xsl:value-of select="PbHoldRcgnRela" />
				</RelaToInsured>
				<!-- Ͷ���˼�ͥ������ -->
				<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PbHomeInCome)"/></FamilyYearSalary>
				<!-- Ͷ���˾������� -->
				<DenType><xsl:apply-templates select="PbDenType" /></DenType>
			</Appnt>

			<!-- ������ -->
			<Insured>
			<!-- ���������� -->
				<Name>
					<xsl:value-of select="LiRcgnName" />
				</Name>
				<!-- �������Ա� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="LiRcgnSex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- ���������� -->
				<Birthday>
					<xsl:value-of select="LiRcgnBirdy" />
				</Birthday>
				<!-- ������֤������-->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="LiRcgnIdType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- ������֤����-->
				<IDNo>
					<xsl:value-of select="LiRcgnId" />
				</IDNo>
				<!-- ������ְҵ����-->
				<JobCode>3010101</JobCode>
				<!-- �����˹���-->
				<Nationality>
					<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="LiNationality" />
					</xsl:with-param>
					</xsl:call-template>
				</Nationality>
				<!-- ������֤����Ч��-->
				<xsl:choose>
				<xsl:when test="LiIdEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="LiIdEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<Stature />
				<Weight />
				<MaritalStatus />
				<!-- ���� -->
				<YearSalary/>
				<!-- �����˵�ַ-->
				<Address>
					<xsl:value-of select="LiRcgnAddr" />
				</Address>
				<!-- �������ʱ�-->
				<ZipCode>
					<xsl:value-of select="LiRcgnPost" />
				</ZipCode>
				<!-- �������ƶ�����-->
				<Mobile>
					<xsl:value-of select="LiRcgnMobl" />
				</Mobile>
				<!-- �����˹̶��绰-->
				<Phone>
					<xsl:value-of select="LiRcgnTele" />
				</Phone>
				<!-- ����������-->
				<Email>
				<xsl:value-of select="LiRcgnEmail" />
				</Email>
				
							               <!-- δ���걻���������������չ�˾�ۼ���ʱ��� -->
            <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PiZxbe20)"/></CovSumAmt>
				
			</Insured>

			<!-- ������ -->
		
				<xsl:choose>
					<xsl:when
						test="/Transaction/Transaction_Body/PbBenfNum ='0'">
					</xsl:when>
					<xsl:when
						test="/Transaction/Transaction_Body/PbBenfNum !='0'">
						
						<xsl:for-each select="Benf_List/Benf_Detail">
						<Bnf>
						<Type>
							<xsl:value-of
								select="PbBenfType" />
						</Type>
						<Grade><xsl:value-of select="PbBenfSequ" /></Grade>
						<Name><xsl:value-of select="PbBenfName" /></Name>
								<!-- �������Ա� -->
				     <Sex>
					   <xsl:call-template name="tran_sex">
						  <xsl:with-param name="Sex">
							 <xsl:value-of select="PbBenfSex" />
						  </xsl:with-param>
					   </xsl:call-template>
				    </Sex>
						<Birthday>
							<xsl:value-of
								select="PbBenfBirdy" />
						</Birthday>
						<IDType>
							<xsl:call-template name="tran_idtype">
								<xsl:with-param name="idtype">
									<xsl:value-of
										select="PbBenfIdType" />
								</xsl:with-param>
							</xsl:call-template>
						</IDType>
						<IDNo><xsl:value-of select="PbBenfId" /></IDNo>
						<!-- ������֤����Ч��-->
						<xsl:choose>
						<xsl:when test="PbBenfIdEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
						<xsl:otherwise><IdExpDate><xsl:value-of select="PbBenfIdEndDate"/></IdExpDate></xsl:otherwise>
						</xsl:choose>
						<Lot>
							<xsl:value-of
								select="PbBenfProp" />
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
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			

			<!-- �������� -->
			<Risk>
			<!-- 20150109 lilu ���е���˵�ú��ĵĴ���
				<RiskCode>					
							<xsl:call-template name="tran_MainRiskCode">
						<xsl:with-param name="MainRiskCode">
							<xsl:value-of select="PbInsuType" />
						</xsl:with-param>
					</xsl:call-template>
				</RiskCode>
			 -->
				<RiskCode><xsl:value-of select="PbInsuType" /></RiskCode>
				<MainRiskCode><xsl:value-of select="PbInsuType" /></MainRiskCode>
				<Amnt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PbInsuAmt)" />
				</Amnt>
				<Prem>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PbInsuExp)" />
				</Prem>
				<Mult>
					<xsl:value-of select="PbSlipNumb" />
				</Mult>
				<BkRckrNo>
					<xsl:value-of select="BkRckrNo" />
				</BkRckrNo>
				<PayIntv>
					<xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="PbPayPeriod" />
						</xsl:with-param>
					</xsl:call-template>
				</PayIntv>
				<PayMode>B</PayMode>
				<InsuYearFlag>
					<xsl:call-template name="tran_PbIYF">
						<xsl:with-param name="PbInsuYearFlag">
							<xsl:value-of select="PbInsuYearFlag" />
						</xsl:with-param>
					</xsl:call-template>	
				</InsuYearFlag>
				<InsuYear><xsl:value-of select="LiInsuPeriod" /></InsuYear>	
				<!-- �ɷ����ںͽɷ����� -->
				 <xsl:if test="PbPayPeriod = 0"><!-- ����1000Y������ -->				    
					<PayEndYear>1000</PayEndYear>
					<PayEndYearFlag>Y</PayEndYearFlag>
				</xsl:if>
				<xsl:if test="PbPayPeriod != 0">	<!-- ����û�н�����������һ˵ -->
				    <PayEndYearFlag>Y</PayEndYearFlag>			    
					<PayEndYear><xsl:value-of select="PbPayAgeTag" /></PayEndYear>			
				</xsl:if>
				<BonusGetMode>
					<xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							<xsl:value-of select="LiBonusGetMode" />
						</xsl:with-param>
					</xsl:call-template>
			 </BonusGetMode>
				<SpecContent />
				<!-- ������ȡ��ʽ������ -->
				<FullBonusGetMode />
				 <xsl:choose>
    	            <xsl:when test="PbInsuType = '221301'">
    	               <GetYearFlag>
    	               <xsl:call-template name="tran_LiRenteDrawMode">
						<xsl:with-param name="GETLiRenteDrawMode">
							 <xsl:value-of select="LiRenteDrawMode" />
						</xsl:with-param>
					   </xsl:call-template>
    	               </GetYearFlag> <!-- ��ȡ�������ڱ�־  �����Ҫת��-->
    	               <GetYear><xsl:value-of select="PbDrawAge" /></GetYear>   <!-- ��ȡ��ʼ���� -->	
    	               </xsl:when>
    	                 <xsl:otherwise>
    	                    <GetYearFlag/>
    	                    <GetYear/>
    	              </xsl:otherwise>
                </xsl:choose>
                  <GetTerms><xsl:value-of select="PbDrawAgeTag" /></GetTerms><!-- ��ȡ���� -->
				<!-- ��ȡ��ʽ -->
				<GetIntv>
					<xsl:call-template name="tran_GetIntv">
						<xsl:with-param name="GetIntv">
							<xsl:value-of select="LiExpireInsuDrawMode" />
						</xsl:with-param>
					</xsl:call-template>
				</GetIntv>
				<GetBankCode />
				<GetBankAccNo />
				<GetAccName />
				<AutoPayFlag><xsl:value-of select="PbAutoPayTag" /></AutoPayFlag>
			</Risk>
			
			<!-- ������ѭ�� -->
			<xsl:if test="count(Appd_List/Appd_Detail) != 0">
				<xsl:for-each select="Appd_List/Appd_Detail">
				<Risk>
					<RiskCode><xsl:value-of select="LiAppdInsuType" /></RiskCode>
					<MainRiskCode><xsl:value-of select="/Transaction/Transaction_Body/PbInsuType" /></MainRiskCode>
					<Amnt>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LiAppdInsuAmot)" />
					</Amnt>
					<Prem>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LiAppdInsuExp)" />
					</Prem>
					<Mult>
						<xsl:value-of select="LiAppdInsuNumb" />
					</Mult>
					<BkRckrNo>
						<xsl:value-of select="/Transaction/Transaction_Body/BkRckrNo" />
					</BkRckrNo>
					<xsl:choose>
					<xsl:when test="LiAppdInsuType ='145201'">
					<PayIntv>0</PayIntv>
					</xsl:when>
					<xsl:otherwise>
					<PayIntv>
						<xsl:call-template name="tran_Contpayintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="/Transaction/Transaction_Body/PbPayPeriod" />
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					</xsl:otherwise>
					</xsl:choose>
					<PayMode>B</PayMode>
					<InsuYearFlag>
						<xsl:call-template name="tran_PbIYF">
							<xsl:with-param name="PbInsuYearFlag">
								<xsl:value-of select="/Transaction/Transaction_Body/PbInsuYearFlag" />
							</xsl:with-param>
						</xsl:call-template>	
					</InsuYearFlag>
					<InsuYear><xsl:value-of select="LiAppdInsuTerm" /></InsuYear>	
					<!-- �ɷ����ںͽɷ����� -->
					 <xsl:if test="/Transaction/Transaction_Body/PbPayPeriod = 0"><!-- ����1000Y������ -->				    
						<PayEndYear>1000</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<xsl:if test="/Transaction/Transaction_Body/PbPayPeriod != 0">	<!-- ����û�н�����������һ˵ -->
					    <PayEndYearFlag>Y</PayEndYearFlag>			    
						<PayEndYear><xsl:value-of select="LiAppdInsuPayTerm" /></PayEndYear>			
					</xsl:if>
					<BonusGetMode>
						<xsl:call-template name="tran_BonusGetMode">
							<xsl:with-param name="BonusGetMode">
								<xsl:value-of select="/Transaction/Transaction_Body/LiBonusGetMode" />
							</xsl:with-param>
						</xsl:call-template>
				 </BonusGetMode>
					<SpecContent />
					<!-- ������ȡ��ʽ������ -->
					<FullBonusGetMode />
					 <xsl:choose>
	    	            <xsl:when test="PbInsuType = '221301'">
	    	               <GetYearFlag>
	    	               <xsl:call-template name="tran_LiRenteDrawMode">
							<xsl:with-param name="GETLiRenteDrawMode">
								 <xsl:value-of select="/Transaction/Transaction_Body/LiRenteDrawMode" />
							</xsl:with-param>
						   </xsl:call-template>
	    	               </GetYearFlag> <!-- ��ȡ�������ڱ�־  �����Ҫת��-->
	    	               <GetYear><xsl:value-of select="PbDrawAge" /></GetYear>   <!-- ��ȡ��ʼ���� -->	
	    	               </xsl:when>
	    	                 <xsl:otherwise>
	    	                    <GetYearFlag/>
	    	                    <GetYear/>
	    	              </xsl:otherwise>
	                </xsl:choose>
	                  <GetTerms><xsl:value-of select="/Transaction/Transaction_Body/PbDrawAgeTag" /></GetTerms><!-- ��ȡ���� -->
					<!-- ��ȡ��ʽ -->
					<GetIntv>
						<xsl:call-template name="tran_GetIntv">
							<xsl:with-param name="GetIntv">
								<xsl:value-of select="/Transaction/Transaction_Body/LiExpireInsuDrawMode" />
							</xsl:with-param>
						</xsl:call-template>
					</GetIntv>
					<GetBankCode />
					<GetBankAccNo />
					<GetAccName />
					<AutoPayFlag><xsl:value-of select="/Transaction/Transaction_Body/PbAutoPayTag" /></AutoPayFlag>
				</Risk>
				</xsl:for-each>
			</xsl:if>
		
		<xsl:if test = "PbInsuType='211902'"><!-- ��Ӯ�����A��-->
		<Loan>
			<!-- �����ͬ�� -->
			<LoanNo><xsl:value-of select="LoanNo"/></LoanNo>
			<!-- ������� -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
			<!-- �������� -->
			<LoanDate><xsl:value-of select="LoanDate"/></LoanDate>
			<!-- ������� -->
			<LoanEndDate><xsl:value-of select="LoanEndDate"/></LoanEndDate>
			<!-- �������� -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- �����˺� -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- ������ -->
			<xsl:choose><!-- �����ж�choose�£���Ȼ�յĻ���yuanToFen��ʱ����0�ˣ����ݲ�ԭ֭ԭζ�� -->
			<xsl:when test = "LoanPrem=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LoanPrem)"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- ������ʼ�� -->
			<InsuDate><xsl:value-of select="InsuDate"/></InsuDate>
			<!-- ���������� -->
			<InsuEndDate><xsl:value-of select="InsuEndDate"/></InsuEndDate>
		</Loan>
		</xsl:if>
			
		</Body>
	</xsl:template>
	
	
	<!-- �Ա� -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- �� -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- Ů -->
	</xsl:template>

	<!-- ���ս���ȡ��ʽ -->
	<xsl:template name="tran_GetIntv">
		<xsl:param name="GetIntv"></xsl:param>
		<xsl:if test="$GetIntv = 0">0</xsl:if>
	</xsl:template>
	
	
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>          <!--  0-һ�θ���  -->
		<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>          <!--  1-�¸���  -->
		<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>          <!--  3-������  -->
		<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>          <!--  6-�������  -->
		<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>          <!--  12-�����  -->
	</xsl:template>
	
	<!-- Ͷ�����˹��� -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 0156">CHN</xsl:if>
		<xsl:if test="$Nationality = 0344"></xsl:if><!--��� -->
		<xsl:if test="$Nationality = 0158"></xsl:if><!--̨�� -->
		<xsl:if test="$Nationality = 0446"></xsl:if><!--���� -->
		<xsl:if test="$Nationality = 0392">JAN</xsl:if><!--�ձ� -->
		<xsl:if test="$Nationality = 0840">USA</xsl:if><!--����-->
		<xsl:if test="$Nationality = 0643"></xsl:if><!--����˹ -->
		<xsl:if test="$Nationality = 0826">ENG</xsl:if><!--Ӣ�� -->
		<xsl:if test="$Nationality = 0250">FRA</xsl:if><!--����-->
		<xsl:if test="$Nationality = 0276">DEU</xsl:if><!--�¹� -->
		<xsl:if test="$Nationality = 0410">KOR</xsl:if><!--���� -->
		<xsl:if test="$Nationality = 0702">SG</xsl:if><!--�¼��� -->
		<xsl:if test="$Nationality = 0360">INA</xsl:if><!--ӡ��������-->
		<xsl:if test="$Nationality = 0356">IND</xsl:if><!--ӡ��-->
		<xsl:if test="$Nationality = 0380">ITA</xsl:if><!--����� -->
		<xsl:if test="$Nationality = 0458">MY</xsl:if><!--�������� -->
		<xsl:if test="$Nationality = 0764">THA</xsl:if><!--̩��-->
		<xsl:if test="$Nationality = 0999"></xsl:if><!--����-->
	</xsl:template>

	<!-- ������ȡ��ʽ -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = 0">2</xsl:if><!-- ֱ�Ӹ��� -->
		<xsl:if test="$BonusGetMode = 1">3</xsl:if><!-- �ֽ����� -->
		<xsl:if test="$BonusGetMode = 2">1</xsl:if><!-- �ۻ���Ϣ -->
		<xsl:if test="$BonusGetMode = 3">5</xsl:if><!-- ����� -->
	</xsl:template>

	<!-- �����Ľɷѷ�ʽ -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 0">0</xsl:when>
			<xsl:when test="$payintv = 12">12</xsl:when>
			<xsl:when test="$payintv = 1">1</xsl:when>
			<xsl:when test="$payintv = 6">6</xsl:when>
			<xsl:when test="$payintv = 3">3</xsl:when>
			<xsl:when test="$payintv = -1">-1</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- �������ͷ�ʽ?    ��Ҫ�ͺ���ȷ�� -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 1">1</xsl:when>
			<xsl:when test="$pbSendMode = 2">1</xsl:when>
			<xsl:when test="$pbSendMode = 3">1</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- ��������/�����־ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">2</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 0"></xsl:when>
			<xsl:when test="$PbInsuYearFlag = 1">Y</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 2">Y</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 3"></xsl:when>
			<xsl:when test="$PbInsuYearFlag = 4">M</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 5">D</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 6">A</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype">0</xsl:param>
		<xsl:if test="$idtype = 'A'">0</xsl:if><!-- �������֤���� -->
		<xsl:if test="$idtype = 'B'">2</xsl:if><!-- ����֤ -->
		<xsl:if test="$idtype = 'C'">8</xsl:if><!-- ��ž���ְ�ɲ�֤ -->
		<xsl:if test="$idtype = 'D'">D</xsl:if><!-- ����֤ -->
		<xsl:if test="$idtype = 'E'">A</xsl:if><!-- ��ž�ʿ��֤ -->
		<xsl:if test="$idtype = 'F'">4</xsl:if><!-- ���ڲ� -->
		<xsl:if test="$idtype = 'G'">B</xsl:if><!-- (�۰�)����֤��ͨ��֤ -->
		<xsl:if test="$idtype = 'H'">8</xsl:if><!-- ̨ͨ��֤��������Ч����֤-->
		<xsl:if test="$idtype = 'I'">1</xsl:if><!-- (���)����-->
		<xsl:if test="$idtype = 'J'">1</xsl:if><!-- (�й�)����-->
		<xsl:if test="$idtype = 'K'">8</xsl:if><!-- �侯��ְ�ɲ�֤-->
		<xsl:if test="$idtype = 'L'">A</xsl:if><!-- �侯ʿ��֤-->
		<xsl:if test="$idtype = 'M'">3</xsl:if><!-- ���� -->
		<xsl:if test="$idtype = 'Z'">8</xsl:if><!-- ���� -->
	</xsl:template>

	<!-- ��ϵ   ������˵ʹ�ú��ı��룬���Բ���ת��-->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:if test="$RelaToInsured = 1">00</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 2">02</xsl:if><!--�ɷ� -->
		<xsl:if test="$RelaToInsured = 3">02</xsl:if><!--����  -->
		<xsl:if test="$RelaToInsured = 4">01</xsl:if><!--����  -->
		<xsl:if test="$RelaToInsured = 5">01</xsl:if><!--ĸ�� -->
		<xsl:if test="$RelaToInsured = 6">03</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 7">03</xsl:if><!--Ů��-->
		<xsl:if test="$RelaToInsured = 8">04</xsl:if><!--�游-->
		<xsl:if test="$RelaToInsured = 9">04</xsl:if><!--��ĸ-->
		<xsl:if test="$RelaToInsured = 10">04</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 11">04</xsl:if><!--��Ů -->
		<xsl:if test="$RelaToInsured = 12">04</xsl:if><!--���游-->
		<xsl:if test="$RelaToInsured = 13">04</xsl:if><!--����ĸ -->
		<xsl:if test="$RelaToInsured = 14">04</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 15">04</xsl:if><!--����Ů -->
		<xsl:if test="$RelaToInsured = 16">06</xsl:if><!--��� -->
		<xsl:if test="$RelaToInsured = 17">06</xsl:if><!--���-->
		<xsl:if test="$RelaToInsured = 18">06</xsl:if><!--�ܵ�-->
		<xsl:if test="$RelaToInsured = 19">06</xsl:if><!--����-->
		<xsl:if test="$RelaToInsured = 20">06</xsl:if><!--����-->
		<xsl:if test="$RelaToInsured = 21">06</xsl:if><!--����-->
		<xsl:if test="$RelaToInsured = 22">06</xsl:if><!--��ϱ-->
		<xsl:if test="$RelaToInsured = 23">06</xsl:if><!--����-->
		<xsl:if test="$RelaToInsured = 24">06</xsl:if><!--��ĸ-->
		<xsl:if test="$RelaToInsured = 25">06</xsl:if><!--Ů��-->
		<xsl:if test="$RelaToInsured = 26">06</xsl:if><!--�������� -->
		<xsl:if test="$RelaToInsured = 27">06</xsl:if><!--ͬ�� -->
		<xsl:if test="$RelaToInsured = 28">06</xsl:if><!--����--><!-- ����û���Ѱ���ֻ��Ϊ����� Update 20130226 -->
		<xsl:if test="$RelaToInsured = 29">06</xsl:if><!--����-->
		<xsl:if test="$RelaToInsured = 30">06</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = ''">--</xsl:if><!--���� -->
	</xsl:template>

    <!-- ������֪ -->
    <xsl:template name="TranHeal">
    <xsl:param name="HealFlag">N</xsl:param>
    <xsl:if test="$HealFlag = 0">N</xsl:if><!-- �޽�����֪ -->
    <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- �н�����֪ -->
     </xsl:template>
     
<!-- Ͷ���˾������� -->
<xsl:template name="tran_dentype" match="PbDenType">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">2</xsl:when>	<!-- ũ�� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
     
	<!-- �����ִ��� -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231204</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$MainRiskCode = 0002">211902</xsl:if><!-- �к���Ӯ����������˺�����A�� -->
		<xsl:if test="$MainRiskCode = 0003">221201</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$MainRiskCode = 0004">231302</xsl:if><!-- �к�������������գ��ֺ��ͣ�-->
		<xsl:if test="$MainRiskCode = 0005">221203</xsl:if><!-- �к���������ȫ����-->
		<xsl:if test="$MainRiskCode = 0006">225501</xsl:if><!-- �к������������ش󼲲�����-->
	</xsl:template>
	
	<!-- ����մ������� -->
<xsl:template name="tran_LoanType" match="LoanType">
	<xsl:choose>
		<xsl:when test=".='1'">01</xsl:when>	<!-- ��ͨ����/һ����ҵ���� -->
		<xsl:when test=".='2'">28</xsl:when>	<!-- ���Ҵ��� -->
		<xsl:when test=".='3'">29</xsl:when>	<!-- ����ѭ������ -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
	
</xsl:stylesheet>

