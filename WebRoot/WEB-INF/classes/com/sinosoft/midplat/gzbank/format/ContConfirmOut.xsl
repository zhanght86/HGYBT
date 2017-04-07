<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TranData"><!-- �����շ�ǩ��Ӧ���� -->
<TXLife><!-- �������нɷѳ���Ӧ���� -->
	<!-- ���н���ʱ��-->
	<TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime>
	<ResultCode>0<xsl:value-of select ="Head/Flag"/></ResultCode>
	<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
	<CpicWater><xsl:value-of select="Body/AgentName"/></CpicWater>
	<xsl:if test="Head/Flag = '0'">
	<HOAppFormNumber><!-- Ͷ������ -->
		<xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/>
	</HOAppFormNumber>  
	<ProviderPolicyNumber><!-- ������֤�� -->
		<xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
	</ProviderPolicyNumber>
	<DanClassNo>2</DanClassNo>
	<OldDanNo></OldDanNo>
	<NewDanNo><!-- ������֤�� -->
		<xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
	</NewDanNo>
	<ProviderFormNumber></ProviderFormNumber><!-- �վݵ�֤�� -->
	<PolNumber><!-- ������ -->
		<xsl:value-of select="Body/ContNo"/>
	</PolNumber>
	<AgentSalesCode><!-- ����������Ա���� -->
		<xsl:value-of select="Body/SaleStaff"/>
	</AgentSalesCode>
	<AgentSalesName><!-- ����������Ա���� -->
		<xsl:value-of select="Body/SaleName"/>
	</AgentSalesName>
	<Credentials><xsl:value-of select="Body/SaleCertNo"/></Credentials><!-- �ʸ�֤�� -->
	<BankNetUpLeaderCode><xsl:value-of select="Body/AgentCode"/></BankNetUpLeaderCode><!-- �������㸺���˹��� -->
	<BankNetUpLeaderName><xsl:value-of select="Body/AgentName"/></BankNetUpLeaderName><!-- �������㸺�������� -->
	<BankNetCredentialsNo></BankNetCredentialsNo><!-- ���б��ռ�ҵ����ҵ�����֤ -->
	<BankNetName></BankNetName><!-- �������� -->
	<PolicyHolder>
		<GovtIDTC>
			<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="Body/Appnt/IDType" />
				</xsl:with-param>
			</xsl:call-template> 
		</GovtIDTC>
		<GovtID>
			<xsl:value-of select="Body/Appnt/IDNo"/>
		</GovtID>
		<EffectDate></EffectDate>
		<ExpireDate></ExpireDate>
		<BirthDate>
			<xsl:value-of select="Body/Appnt/Birthday"/>
		</BirthDate>
		<Gender>
			<xsl:call-template name="tran_sex">
				<xsl:with-param name="sex">
					<xsl:value-of select="Body/Appnt/Sex" />
				</xsl:with-param>
			</xsl:call-template> 
		</Gender>
		<FullName>
			<xsl:value-of select="Body/Appnt/Name"/>
		</FullName>
		<OccupationType>
			<xsl:value-of select="Body/Appnt/JobCode"/>
		</OccupationType>
		<Line1>
			<xsl:value-of select="Body/Appnt/Address"/>
		</Line1>
		<MobileNumber>
			<xsl:value-of select="Body/Appnt/Mobile"/>
		</MobileNumber>
		<DialNumber>
			<xsl:value-of select="Body/Appnt/Phone"/>
		</DialNumber>
		<DialCode></DialCode>
		<Zip>
			<xsl:value-of select="Body/Appnt/ZipCode"/>
		</Zip>
		<RelatedToInsuredRoleCode>
			<xsl:value-of select="Body/Appnt/RelaToInsured"/>
		</RelatedToInsuredRoleCode>
		<Nation>
			<xsl:value-of select="Body/Appnt/Nationality"/>
		</Nation>
		<Province></Province>
		<County></County>
		<Education></Education>
		<Marriage></Marriage>
		<Email><xsl:value-of select="Body/Appnt/Email"/></Email>
	</PolicyHolder>
	<Insured>
		<GovtIDTC>
			<xsl:call-template name="tran_idtype">
				<xsl:with-param name="idtype">
					<xsl:value-of select="Body/Insured/IDType" />
				</xsl:with-param>
			</xsl:call-template> 
		</GovtIDTC>
		<GovtID>
			<xsl:value-of select="Body/Insured/IDNo" />
		</GovtID>
		<EffectDate></EffectDate>
		<ExpireDate></ExpireDate>
		<BirthDate>
			<xsl:value-of select="Body/Insured/Birthday" />
		</BirthDate>
		<Gender>
			<xsl:call-template name="tran_sex">
				<xsl:with-param name="sex">
					<xsl:value-of select="Body/Insured/Sex" />
				</xsl:with-param>
			</xsl:call-template> 
		</Gender>
		<FullName>
			<xsl:value-of select="Body/Insured/Name" />
		</FullName>
		<OccupationType>
			<xsl:value-of select="Body/Insured/JobCode" />
		</OccupationType>
		<Line1>
			<xsl:value-of select="Body/Insured/Address" />
		</Line1>
		<DialCode></DialCode>
		<DialNumber>
			<xsl:value-of select="Body/Insured/Phone" />
		</DialNumber>
		<MobileNumber>
			<xsl:value-of select="Body/Insured/Mobile" />
		</MobileNumber>
		<Zip>
			<xsl:value-of select="Body/Insured/ZipCode" />
		</Zip>
		<RelatedToInsuredRoleCode></RelatedToInsuredRoleCode>
		<Nation>
			<xsl:value-of select="Body/Insured/Nationality" />
		</Nation>
		<Province></Province>
		<County></County>
		<Education></Education>
		<Marriage></Marriage>
		<Email>
			<xsl:value-of select="Body/Insured/Email" />
		</Email>
	</Insured>
	
	<xsl:variable name="tbnfCount" select="count(/TranData/Bnf[Type= '1'] )"/>
	<xsl:if test="$tbnfCount > 0">
		<BeneficiaryIndicator>N</BeneficiaryIndicator>
	</xsl:if>
	<xsl:if test="$tbnfCount = 0">
		<BeneficiaryIndicator>Y</BeneficiaryIndicator>
	</xsl:if>
	<BeneficiaryCount><xsl:value-of select="$tbnfCount" /></BeneficiaryCount>
	<xsl:for-each select="/TranData/Bnf[Type= '1'] ">
		<xsl:element name="{'Beneficiary'}{position()}">
			<No><xsl:value-of select="Grade" /></No>
			<BeneficiaryMethod>3</BeneficiaryMethod>
			<GovtIDTC>
				<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of select="IDType" />
					</xsl:with-param>
				</xsl:call-template> 
			</GovtIDTC>
			<GovtID><xsl:value-of select="IDNo" /></GovtID>
			<BirthDate><xsl:value-of select="Birthday" /></BirthDate>
			<EffectDate></EffectDate>
			<ExpireDate></ExpireDate>
			<FullName><xsl:value-of select="Name" /></FullName>
			<OccupationType></OccupationType>
			<Line1></Line1>
			<DialCode></DialCode>
			<DialNumber></DialNumber>
			<MobileNumber></MobileNumber>
			<Zip></Zip>
			<Gender></Gender>
			<RelatedToInsuredRoleCode>
				<xsl:call-template name="tran_relation">
					<xsl:with-param name="relation">
						<xsl:value-of select="RelaToInsured" />
					</xsl:with-param>
				</xsl:call-template> 
			</RelatedToInsuredRoleCode>
			<InterestPercent><xsl:value-of select="Lot" /></InterestPercent>
			<Nation></Nation>
			<Province></Province>
			<County></County>
			<Education></Education>
			<Marriage></Marriage>
			<Email></Email>
		</xsl:element>
	</xsl:for-each>
	<PaymentAmt>
		<xsl:value-of select="Body/PayPrem " />
	</PaymentAmt>
	<PaymentAmtUper>
		<xsl:value-of select="Body/PremText " />
	</PaymentAmtUper>
	<ProductCode>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/RiskCode " />
	</ProductCode>
	<ModalPremAmt>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/Prem " />
	</ModalPremAmt>
	<ModalPremAmtUper></ModalPremAmtUper>
	<PlanName>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/RiskName " />
	</PlanName>
	<SubmissionDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PolApplyDate " />
	</SubmissionDate>
	<IntialNumberOfUnits>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/Mult " />
	</IntialNumberOfUnits>
	<PaymentMode>
		<xsl:call-template name="tran_payintv">
			<xsl:with-param name="payintv">
				<xsl:value-of select="Body/PayIntv " />
			</xsl:with-param>
		</xsl:call-template> 
	</PaymentMode>
	<PaymentModeName>
		<xsl:call-template name="tran_payintvname">
			<xsl:with-param name="payintv">
				<xsl:value-of select="Body/PayIntv " />
			</xsl:with-param>
		</xsl:call-template> 
	</PaymentModeName>
	<AgeTouBao></AgeTouBao>
	<CpicTeller><xsl:value-of select="Body/AgentCode " /></CpicTeller>	<!-- ���չ�˾ҵ��Ա���� -->
	<BankManagerCode><xsl:value-of select="Body/AgentCode " /></BankManagerCode>	<!-- ����Ա���� -->
	<BankManagerName><xsl:value-of select="Body/AgentName " /></BankManagerName>	<!-- ����Ա���� -->
	<BankMagCredentialsNo></BankMagCredentialsNo><!-- ����Ա�����ʸ�֤ -->
	<PayoutStart></PayoutStart>		<!-- ��ȡ���� -->
	<DivType></DivType>				<!-- ������ȡ��ʽ -->
	<DivTypeName></DivTypeName>		<!-- ������ȡ��ʽ���� -->
	<BenefitMode></BenefitMode>		<!-- ��ȡ/������ʽ���� -->
	<BenefitModeName></BenefitModeName><!-- ��ȡ/������ʽ���� -->
	<FirstPayOutDate><xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/GetStartDate " /></FirstPayOutDate><!-- ���������ȡ���� -->
	<BasePreCount></BasePreCount>	<!-- �������θ��� -->
	<BasePre1>
		<BasePerName></BasePerName>	<!-- �������� -->
		<BasePeramount></BasePeramount><!-- ������ֵ -->
	</BasePre1>
	<FeeStd>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/Prem " />
	</FeeStd>		<!-- �ɷѱ�׼ -->
	<FeeCon></FeeCon>		<!-- �ӷѱ�׼ -->
	<FeePro></FeePro>		<!-- ְҵ�ӷѱ�׼ -->
	<PaymentEndAge>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PayEndDate " />
	</PaymentEndAge>		<!-- �ɷ���ֹ���� -->
	<PaymentDueDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PolApplyDate " />
	</PaymentDueDate>	<!-- �ɷ���ʼ���� -->
	<FinalPaymentDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PayToDate " />
	</FinalPaymentDate><!-- �ɷ���ֹ���� -->
	<EffDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/CValiDate " />
	</EffDate><!-- ������ʼ���� -->
	<TermDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/InsuEndDate " />
	</TermDate><!-- ������ֹ���� -->
	<xsl:variable name="tAddCount" select="count(/TranData/Risk[RiskCode != MainRiskCode] )"/>
	<CoverageCount>
		<xsl:value-of select="$tAddCount " />
	</CoverageCount><!-- �����ո��� -->
	<xsl:for-each select="/TranData/Risk[RiskCode != MainRiskCode] ">
		<xsl:element name="{'Extension'}{position()}">
			<ProductCode>
				<xsl:value-of select="RiskCode " />
			</ProductCode>
			<PlanName><xsl:value-of select="RiskName " /></PlanName>
			<ModalPremAmt><xsl:value-of select="Prem" /></ModalPremAmt>
			<IntialNumberOfUnits><xsl:value-of select="Mult " /></IntialNumberOfUnits>
			<FeeStd><xsl:value-of select="Prem " /></FeeStd>
			<FeeCon></FeeCon>
			<FeePro></FeePro>
			<BasePremAmt><xsl:value-of select="Amnt " /></BasePremAmt>
			<PaymentEndAge></PaymentEndAge>		<!-- �ɷ���ֹ���� -->
			<FinalPaymentDate><xsl:value-of select="PayEndDate " /></FinalPaymentDate><!-- �ɷ���ֹ���� -->
			<TermDate><xsl:value-of select="InsuEndDate " /></TermDate><!-- ������ֹ���� -->
			<PaymentMode></PaymentMode><!-- �ɷѷ�ʽ -->
			<PaymentModeName></PaymentModeName><!-- �ɷѷ�ʽ���� -->
			<PayoutDuration><xsl:value-of select="PayEndYear " /></PayoutDuration><!-- �ɷ����� -->
			<DivType></DivType>				<!-- ������ȡ��ʽ -->
			<DivTypeName></DivTypeName>		<!-- ������ȡ��ʽ���� -->
			<COVERAGE_YEAR><xsl:value-of select="Years " /></COVERAGE_YEAR>	<!-- �������� -->
			<BenefitMode></BenefitMode>		<!-- ��ȡ/������ʽ���� -->
			<BenefitModeName></BenefitModeName><!-- ��ȡ/������ʽ���� -->
			<PayOutDate><xsl:value-of select="GetStartDate " /></PayOutDate>		<!-- ��ȡ���� -->
			<PayoutStart></PayoutStart>		<!-- ��ȡ���� -->
		</xsl:element>
	</xsl:for-each>
	<CashValue>
		<Cash></Cash>
		<Year></Year>
	</CashValue>
	<FullCashValue>
		<CashValue>
			<Cash></Cash>
			<Year></Year>
		</CashValue>
	</FullCashValue>
	<CpicAddr><xsl:value-of select="Body/ComLocation " /></CpicAddr>
	<CpicDialNumber><xsl:value-of select="Body/ComPhone " /></CpicDialNumber>
	<AgentName><xsl:value-of select="Body/ComName " /></AgentName>
	<ResultInfo></ResultInfo>
	<PolicyInfoDesc>
		<Text>�յ������յ�������ϸ�˶�</Text>
		<Text>���˻����ɱ��շ�</Text>
	</PolicyInfoDesc>
	<ContractInfo>
		<RiskAmt></RiskAmt>
		<TotalRiskAmt></TotalRiskAmt>
		<LoanBenefitDesc></LoanBenefitDesc>
		<LoanContVerifyNo></LoanContVerifyNo>
		<LoanContractNo></LoanContractNo>
		<LoanInvoiceNo></LoanInvoiceNo>
		<LoanStartDate></LoanStartDate>
		<LoanEndDate></LoanEndDate>
		<Spec1>1�������յ���߱��ս���� 100��ԪΪ�ޡ����۱������˳��м��ݱ����գ�����˾����е��ı��ս����������</Spec1>
	</ContractInfo>
	<BeiYong></BeiYong>
	<DigitalSignInfo>
		<SignatureFlag>N</SignatureFlag>
		<PolicySerialNumber></PolicySerialNumber>
		<DigitalSignature></DigitalSignature>
	</DigitalSignInfo>
	<xsl:choose>
		<xsl:when test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '011A0100'">
			<TotalPages>2</TotalPages>
		</xsl:when>
		<xsl:otherwise>
			<TotalPages>3</TotalPages>
		</xsl:otherwise>
	</xsl:choose>
	<PrintList>
		<PrintDesc></PrintDesc>
		<PrintPages>1</PrintPages>
		<VoucherType>1</VoucherType>
		<PrintRecNum></PrintRecNum>
		<PrintDetails>
			<Details></Details>
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
								<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
								<Details><xsl:text>��</xsl:text></Details> 
								<Details><xsl:text>��</xsl:text></Details> 
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details>
								<Details><xsl:text>��</xsl:text></Details> 
								<Details><xsl:text>��</xsl:text>���պ�ͬ��2��<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ </Details>
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details/>
								<Details><xsl:text>��</xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Details>
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>      
								<Details><xsl:text>��</xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																												<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
																												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
								</Details>
								<Details><xsl:text>��</xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																												<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
																												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
								</Details>
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
								<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
								<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
								<xsl:for-each select="/TranData/Body/Bnf">
								<Details><xsl:text>��</xsl:text><xsl:text></xsl:text>����������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
								<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
								 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
								<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			                   	</Details>
								</xsl:for-each>
								<xsl:choose>
								<xsl:when test="$num = 0"><Details><xsl:text>��</xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
								<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
								 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
								<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			                   	</Details></xsl:when>
								</xsl:choose>		
								<Details />
								<Details />
								<Details />
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>      
								<Details><xsl:text>��</xsl:text>��������                          �����ڼ�    ��������    ���ѷ�ʽ  ������������/����   ���շ�</Details>
								<xsl:for-each select="/TranData/Body/Risk">
								<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
								<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
								<xsl:variable name="Mult" select="Mult"/>
								<Details>
								<!-- �������� -->
								<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
								                                                     <xsl:choose>
																										<xsl:when test="InsuYearFlag = 'A'"><xsl:text>��</xsl:text>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																										</xsl:when>
																										<xsl:when test="InsuYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																										</xsl:when>  
																										<xsl:when test="InsuYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��</xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:text>     </xsl:text>
																						<xsl:choose>
																										<xsl:when test="PayIntv = 0">
																											<xsl:text>����        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'D'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>  
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����    </xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:apply-templates select="PayIntv"/>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>��</xsl:text>
																						 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</Details>
								</xsl:for-each>
								 <Details/>
								 <Details/>
								 <Details/>
								 <Details/>
								<Details><xsl:text>��</xsl:text>���շѺϼƣ�<xsl:value-of select="/TranData/Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>Ԫ��</Details>
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<xsl:choose><!-- ���ݻ�����Ʒ��221201�� �˴���ӡ���ǣ������հף������Ǻ�����ȡ��ʽ -->
								<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
								<Details><xsl:text>��</xsl:text>������ȡ��ʽ��<xsl:apply-templates select="/TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Details>
								</xsl:when>
								<xsl:otherwise>
								<Details><xsl:text>��</xsl:text>�������հף�</Details>
								<Details/>
								</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$num=1 or $num=0"><Details/><Details /><Details /><Details /></xsl:when><!-- �����˸���Ϊ0���߸���Ϊ1�������һ���ģ������˵���Ϣ��ռһ�� -->
									<xsl:when test="$num=2"><Details /><Details /><Details /></xsl:when>
									<xsl:when test="$num=3"><Details /><Details /></xsl:when>
									<xsl:when test="$num=4"><Details /></xsl:when>
									<xsl:otherwise></xsl:otherwise>
								</xsl:choose>
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details><xsl:text>��</xsl:text><xsl:text>�ر�Լ����</xsl:text>
																								<xsl:choose>
																										<xsl:when test="$MainRisk/SpecContent = ''">
																											<xsl:text>���ޣ�</xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="$MainRisk/SpecContent"/>
																										</xsl:otherwise>
																								</xsl:choose>
								</Details>
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details><xsl:text>��</xsl:text>�����������ƣ�<xsl:value-of select="/TranData/Body/AgentComName"/></Details>
								<Details><xsl:text>��</xsl:text>����������Ա����/���룺<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,44)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Details>
								<Details><xsl:text>��</xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>��������绰��<xsl:value-of select="/TranData/Body/AgentPhone"/></Details>
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details />
								<Details><xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Details>
								<Details />
			<Details></Details>
		</PrintDetails>
	</PrintList>
	<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '011A0100'">
		<PrintList>
		<xsl:variable name="Amnt"
			select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Amnt)" />
		<xsl:variable name="Falseflag"
			select="java:java.lang.Boolean.parseBoolean('false')" />
		<xsl:variable name="MainRisk"
			select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
			<PrintDesc></PrintDesc>
			<PrintPages>2</PrintPages>
			<VoucherType>2</VoucherType>
			<PrintRecNum></PrintRecNum>
			<PrintDetails>
				<Details></Details>
				<Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details><xsl:text>������</xsl:text>                                     �ֽ��ֵ��                     </Details>
									 <Details/>
									 <Details><xsl:text>������</xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/></Details>
									 <Details><xsl:text>������</xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ</Details>
									 <Details><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <Details><xsl:text>������������</xsl:text>  �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ</Details>
								     <Details><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
									 <Details><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <Details><xsl:text>������</xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ����</Details>
								     <Details><xsl:text>������</xsl:text>���ֵ�Ͷ���������ĸ���������ʹ���������á�</Details>
								     <Details><xsl:text>������</xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������</Details>
								     <Details><xsl:text>������</xsl:text>��˾���紹ѯ��</Details>				    
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
									 <Details/>
				<Details></Details>
			</PrintDetails>
		</PrintList>
	</xsl:if>
	<PrintList>
	  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
	  <PrintDesc></PrintDesc>
	  <PrintPages>2</PrintPages>
	  <VoucherType>2</VoucherType>
	  <PrintRecNum></PrintRecNum>
	  <PrintDetails>
	     <Details/>
         <Details/>
		 <Details/>
		 <Details/>
		 <Details/>
		 <Details><xsl:text>     </xsl:text>                                        ���պ�ͬ�ʹ��ִ</Details>
		 <Details/>
		 <Details/>
		 <Details><xsl:text>     </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/></Details>
		 <Details><xsl:text>     </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="/TranData/Body/AgentCode"/></Details>
		 <xsl:choose><!-- ���ݻ�����Ʒ��221201�� û�в�Ʒ˵��˵���飬��ӮC�� -->
		   <xsl:when test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and /TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
		     <Details><xsl:text>     </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ���</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>���������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>��Ͳ�Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Details>
		   </xsl:when>
		   <xsl:otherwise>
		     <Details><xsl:text>     </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Details>
		   </xsl:otherwise>
		  </xsl:choose>
		  <Details><xsl:text>     </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Details>
	      <Details><xsl:text>     </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>��ִ���ء�</xsl:text></Details>
		  <Details/>
		  <Details><xsl:text>     </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Details>
	  </PrintDetails>
	</PrintList>
	</xsl:if>
</TXLife>
</xsl:template>

	<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Details><xsl:text>                </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Details>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Details><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Details>
		</xsl:for-each>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="tran_Sex" match="Sex">
		<xsl:choose>      
			<xsl:when test=".=0">��</xsl:when>	<!-- �� -->
			<xsl:when test=".=1">Ů</xsl:when>	<!-- Ů -->
			<xsl:when test=".=2">����</xsl:when>	<!-- ���� -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template> 

	<xsl:template name="tran_sex">
		<xsl:param name="sex"></xsl:param>
		<xsl:choose>
			<xsl:when test="$sex = 0">1</xsl:when><!-- �� -->
			<xsl:when test="$sex = 1">2</xsl:when><!-- Ů -->
			<xsl:otherwise>2</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_idtype"><!-- ����֤������ת��Ϊ��������֤������ -->
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = 0">0</xsl:when><!-- ���֤ -->
			<xsl:when test="$idtype = A">1</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = 1">2</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = B">3</xsl:when><!-- �۰�ͨ��֤ -->
			<xsl:when test="$idtype = E">4</xsl:when><!-- ̨��ͨ��֤ -->
			<xsl:when test="$idtype = 8">9</xsl:when><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 0">1</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 6">2</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 1">4</xsl:when><!-- �½� -->
			<xsl:when test="$payintv = 12">5</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = -1">6</xsl:when><!-- ������ -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintvname">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 0">1</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 6">2</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 1">4</xsl:when><!-- �½� -->
			<xsl:when test="$payintv = 12">5</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = -1">6</xsl:when><!-- ������ -->
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
			<xsl:when test="$getmode = 2">11</xsl:when><!-- �ֽ� -->
			<xsl:when test="$getmode = 1">12</xsl:when><!-- �ۼ���Ϣ -->
			<xsl:when test="$getmode = 3">23</xsl:when><!-- �ֽ����� -->
			<xsl:when test="$getmode = 5">14</xsl:when><!-- ����� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_BenefitMode">
		<xsl:param name="BenefitMode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$BenefitMode = 0">1</xsl:when><!-- ���� -->
			<xsl:when test="$BenefitMode = 12">2</xsl:when><!-- ���� -->
			<xsl:when test="$BenefitMode = 1">5</xsl:when><!-- ���� -->
			<xsl:otherwise>12</xsl:otherwise>
			<!-- ����� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = Y">2</xsl:when><!-- �� -->
			<xsl:when test="$insuyearflag = A">3</xsl:when><!-- ���� -->
			<xsl:when test="$insuyearflag = M">4</xsl:when><!-- �� -->
			<xsl:when test="$insuyearflag = D">5</xsl:when><!-- �� -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_relation">
		<xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = 301">00</xsl:when><!-- ���� -->
			<xsl:when test="$relation = 303 or $relation = 304 or $relation = 306 or $relation = 309">
			</xsl:when>
			<xsl:when test="$relation = 305">04</xsl:when><!-- ��ż -->
			<xsl:otherwise>06</xsl:otherwise><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
	<!-- �ɷ�Ƶ��/���ѽ��ɷ�ʽ -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">����     </xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  
	
</xsl:stylesheet>