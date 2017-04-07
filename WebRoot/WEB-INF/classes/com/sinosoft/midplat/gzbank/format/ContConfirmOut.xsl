<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="TranData"><!-- 核心收费签单应答报文 -->
<TXLife><!-- 贵州银行缴费出单应答报文 -->
	<!-- 银行交易时间-->
	<TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime>
	<ResultCode>0<xsl:value-of select ="Head/Flag"/></ResultCode>
	<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
	<CpicWater><xsl:value-of select="Body/AgentName"/></CpicWater>
	<xsl:if test="Head/Flag = '0'">
	<HOAppFormNumber><!-- 投保单号 -->
		<xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/>
	</HOAppFormNumber>  
	<ProviderPolicyNumber><!-- 保单单证号 -->
		<xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
	</ProviderPolicyNumber>
	<DanClassNo>2</DanClassNo>
	<OldDanNo></OldDanNo>
	<NewDanNo><!-- 保单单证号 -->
		<xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
	</NewDanNo>
	<ProviderFormNumber></ProviderFormNumber><!-- 收据单证号 -->
	<PolNumber><!-- 保单号 -->
		<xsl:value-of select="Body/ContNo"/>
	</PolNumber>
	<AgentSalesCode><!-- 网点销售人员代码 -->
		<xsl:value-of select="Body/SaleStaff"/>
	</AgentSalesCode>
	<AgentSalesName><!-- 网点销售人员姓名 -->
		<xsl:value-of select="Body/SaleName"/>
	</AgentSalesName>
	<Credentials><xsl:value-of select="Body/SaleCertNo"/></Credentials><!-- 资格证书 -->
	<BankNetUpLeaderCode><xsl:value-of select="Body/AgentCode"/></BankNetUpLeaderCode><!-- 银行网点负责人工号 -->
	<BankNetUpLeaderName><xsl:value-of select="Body/AgentName"/></BankNetUpLeaderName><!-- 银行网点负责人姓名 -->
	<BankNetCredentialsNo></BankNetCredentialsNo><!-- 银行保险兼业代理业务许可证 -->
	<BankNetName></BankNetName><!-- 网点名称 -->
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
	<CpicTeller><xsl:value-of select="Body/AgentCode " /></CpicTeller>	<!-- 保险公司业务员代码 -->
	<BankManagerCode><xsl:value-of select="Body/AgentCode " /></BankManagerCode>	<!-- 银管员代码 -->
	<BankManagerName><xsl:value-of select="Body/AgentName " /></BankManagerName>	<!-- 银管员名称 -->
	<BankMagCredentialsNo></BankMagCredentialsNo><!-- 银管员名称资格证 -->
	<PayoutStart></PayoutStart>		<!-- 领取年龄 -->
	<DivType></DivType>				<!-- 红利领取方式 -->
	<DivTypeName></DivTypeName>		<!-- 红利领取方式名称 -->
	<BenefitMode></BenefitMode>		<!-- 领取/给付方式代码 -->
	<BenefitModeName></BenefitModeName><!-- 领取/给付方式名称 -->
	<FirstPayOutDate><xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/GetStartDate " /></FirstPayOutDate><!-- 首期年金领取日期 -->
	<BasePreCount></BasePreCount>	<!-- 保额责任个数 -->
	<BasePre1>
		<BasePerName></BasePerName>	<!-- 保额名称 -->
		<BasePeramount></BasePeramount><!-- 保额数值 -->
	</BasePre1>
	<FeeStd>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/Prem " />
	</FeeStd>		<!-- 缴费标准 -->
	<FeeCon></FeeCon>		<!-- 加费标准 -->
	<FeePro></FeePro>		<!-- 职业加费标准 -->
	<PaymentEndAge>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PayEndDate " />
	</PaymentEndAge>		<!-- 缴费终止年龄 -->
	<PaymentDueDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PolApplyDate " />
	</PaymentDueDate>	<!-- 缴费起始日期 -->
	<FinalPaymentDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/PayToDate " />
	</FinalPaymentDate><!-- 缴费终止日期 -->
	<EffDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/CValiDate " />
	</EffDate><!-- 责任起始日期 -->
	<TermDate>
		<xsl:value-of select="Body/Risk[RiskCode = MainRiskCode]/InsuEndDate " />
	</TermDate><!-- 责任终止日期 -->
	<xsl:variable name="tAddCount" select="count(/TranData/Risk[RiskCode != MainRiskCode] )"/>
	<CoverageCount>
		<xsl:value-of select="$tAddCount " />
	</CoverageCount><!-- 附加险个数 -->
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
			<PaymentEndAge></PaymentEndAge>		<!-- 缴费终止年龄 -->
			<FinalPaymentDate><xsl:value-of select="PayEndDate " /></FinalPaymentDate><!-- 缴费终止日期 -->
			<TermDate><xsl:value-of select="InsuEndDate " /></TermDate><!-- 责任终止日期 -->
			<PaymentMode></PaymentMode><!-- 缴费方式 -->
			<PaymentModeName></PaymentModeName><!-- 缴费方式名称 -->
			<PayoutDuration><xsl:value-of select="PayEndYear " /></PayoutDuration><!-- 缴费年限 -->
			<DivType></DivType>				<!-- 红利领取方式 -->
			<DivTypeName></DivTypeName>		<!-- 红利领取方式名称 -->
			<COVERAGE_YEAR><xsl:value-of select="Years " /></COVERAGE_YEAR>	<!-- 保障年期 -->
			<BenefitMode></BenefitMode>		<!-- 领取/给付方式代码 -->
			<BenefitModeName></BenefitModeName><!-- 领取/给付方式名称 -->
			<PayOutDate><xsl:value-of select="GetStartDate " /></PayOutDate>		<!-- 领取日期 -->
			<PayoutStart></PayoutStart>		<!-- 领取年龄 -->
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
		<Text>收到本保险单后请仔细核对</Text>
		<Text>后退还所缴保险费</Text>
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
		<Spec1>1、本保险单最高保险金额以 100万元为限。无论被保险人持有几份本保险，本公司对其承担的保险金给付责任以</Spec1>
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
								<Details><xsl:text>　</xsl:text></Details> 
								<Details><xsl:text>　</xsl:text></Details> 
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details>
								<Details><xsl:text>　</xsl:text></Details> 
								<Details><xsl:text>　</xsl:text>保险合同号2：<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 </Details>
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details/>
								<Details><xsl:text>　</xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Details>
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>      
								<Details><xsl:text>　</xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																												<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
																												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
								</Details>
								<Details><xsl:text>　</xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																												<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
																												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
								</Details>
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
								<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
								<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
								<xsl:for-each select="/TranData/Body/Bnf">
								<Details><xsl:text>　</xsl:text><xsl:text></xsl:text>受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
								<xsl:text>性别:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
								 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
								<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			                   	</Details>
								</xsl:for-each>
								<xsl:choose>
								<xsl:when test="$num = 0"><Details><xsl:text>　</xsl:text><xsl:text></xsl:text>受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
								<xsl:text>性别:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
								 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
								<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			                   	</Details></xsl:when>
								</xsl:choose>		
								<Details />
								<Details />
								<Details />
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>      
								<Details><xsl:text>　</xsl:text>险种名称                          保险期间    交费年期    交费方式  （基本）保额/份数   保险费</Details>
								<xsl:for-each select="/TranData/Body/Risk">
								<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
								<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
								<xsl:variable name="Mult" select="Mult"/>
								<Details>
								<!-- 险种名称 -->
								<xsl:text>　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
								                                                     <xsl:choose>
																										<xsl:when test="InsuYearFlag = 'A'"><xsl:text>至</xsl:text>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																										</xsl:when>
																										<xsl:when test="InsuYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																										</xsl:when>  
																										<xsl:when test="InsuYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>月  </xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>日</xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:text>     </xsl:text>
																						<xsl:choose>
																										<xsl:when test="PayIntv = 0">
																											<xsl:text>趸交        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>年        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>月        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'D'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>日        </xsl:text>
																										</xsl:when>  
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁    </xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:apply-templates select="PayIntv"/>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>份</xsl:text>
																						 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</Details>
								</xsl:for-each>
								 <Details/>
								 <Details/>
								 <Details/>
								 <Details/>
								<Details><xsl:text>　</xsl:text>保险费合计：<xsl:value-of select="/TranData/Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>元）</Details>
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<xsl:choose><!-- 保驾护航产品（221201） 此处打印的是（此栏空白），不是红利领取方式 -->
								<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
								<Details><xsl:text>　</xsl:text>红利领取方式：<xsl:apply-templates select="/TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Details>
								</xsl:when>
								<xsl:otherwise>
								<Details><xsl:text>　</xsl:text>（本栏空白）</Details>
								<Details/>
								</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$num=1 or $num=0"><Details/><Details /><Details /><Details /></xsl:when><!-- 受益人个数为0或者个数为1的情况是一样的，受益人的信息都占一行 -->
									<xsl:when test="$num=2"><Details /><Details /><Details /></xsl:when>
									<xsl:when test="$num=3"><Details /><Details /></xsl:when>
									<xsl:when test="$num=4"><Details /></xsl:when>
									<xsl:otherwise></xsl:otherwise>
								</xsl:choose>
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details><xsl:text>　</xsl:text><xsl:text>特别约定：</xsl:text>
																								<xsl:choose>
																										<xsl:when test="$MainRisk/SpecContent = ''">
																											<xsl:text>（无）</xsl:text>
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
								<Details><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Details>
								<Details><xsl:text>　</xsl:text>银行网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/></Details>
								<Details><xsl:text>　</xsl:text>银行销售人员姓名/代码：<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,44)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Details>
								<Details><xsl:text>　</xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>银保经理电话：<xsl:value-of select="/TranData/Body/AgentPhone"/></Details>
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
								<Details><xsl:text>　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Details>
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
									 <Details><xsl:text>　　　</xsl:text>                                     现金价值表                     </Details>
									 <Details/>
									 <Details><xsl:text>　　　</xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Details>
									 <Details><xsl:text>　　　</xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Details>
									 <Details><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <Details><xsl:text>　　　　　　</xsl:text>  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值</Details>
								     <Details><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
									 <Details><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Details>
									 <Details><xsl:text>　　　</xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现</Details>
								     <Details><xsl:text>　　　</xsl:text>金价值额。投保后所做的各项变更可能使本表不再适用。</Details>
								     <Details><xsl:text>　　　</xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向</Details>
								     <Details><xsl:text>　　　</xsl:text>公司来电垂询。</Details>				    
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
		 <Details><xsl:text>     </xsl:text>                                        保险合同送达回执</Details>
		 <Details/>
		 <Details/>
		 <Details><xsl:text>     </xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/></Details>
		 <Details><xsl:text>     </xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="/TranData/Body/AgentCode"/></Details>
		 <xsl:choose><!-- 保驾护航产品（221201） 没有产品说明说明书，智赢C有 -->
		   <xsl:when test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and /TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
		     <Details><xsl:text>     </xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单、</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>书和产品说明书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Details>
		   </xsl:when>
		   <xsl:otherwise>
		     <Details><xsl:text>     </xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text></Details>
		     <Details><xsl:text>     </xsl:text><xsl:text>书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Details>
		   </xsl:otherwise>
		  </xsl:choose>
		  <Details><xsl:text>     </xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Details>
	      <Details><xsl:text>     </xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></Details>
		  <Details><xsl:text>     </xsl:text><xsl:text>回执交回。</xsl:text></Details>
		  <Details/>
		  <Details><xsl:text>     </xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></Details>
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
			<xsl:when test=".=0">男</xsl:when>	<!-- 男 -->
			<xsl:when test=".=1">女</xsl:when>	<!-- 女 -->
			<xsl:when test=".=2">其他</xsl:when>	<!-- 其他 -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template> 

	<xsl:template name="tran_sex">
		<xsl:param name="sex"></xsl:param>
		<xsl:choose>
			<xsl:when test="$sex = 0">1</xsl:when><!-- 男 -->
			<xsl:when test="$sex = 1">2</xsl:when><!-- 女 -->
			<xsl:otherwise>2</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_idtype"><!-- 核心证件类型转换为贵州银行证件类型 -->
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = 0">0</xsl:when><!-- 身份证 -->
			<xsl:when test="$idtype = A">1</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = 1">2</xsl:when><!-- 护照 -->
			<xsl:when test="$idtype = B">3</xsl:when><!-- 港澳通行证 -->
			<xsl:when test="$idtype = E">4</xsl:when><!-- 台湾通行证 -->
			<xsl:when test="$idtype = 8">9</xsl:when><!-- 其他 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 0">1</xsl:when><!-- 趸交 -->
			<xsl:when test="$payintv = 6">2</xsl:when><!-- 半年 -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- 季交 -->
			<xsl:when test="$payintv = 1">4</xsl:when><!-- 月缴 -->
			<xsl:when test="$payintv = 12">5</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = -1">6</xsl:when><!-- 不定期 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_payintvname">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 0">1</xsl:when><!-- 趸交 -->
			<xsl:when test="$payintv = 6">2</xsl:when><!-- 半年 -->
			<xsl:when test="$payintv = 3">3</xsl:when><!-- 季交 -->
			<xsl:when test="$payintv = 1">4</xsl:when><!-- 月缴 -->
			<xsl:when test="$payintv = 12">5</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = -1">6</xsl:when><!-- 不定期 -->
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
			<xsl:when test="$getmode = 2">11</xsl:when><!-- 现金 -->
			<xsl:when test="$getmode = 1">12</xsl:when><!-- 累计生息 -->
			<xsl:when test="$getmode = 3">23</xsl:when><!-- 抵交保费 -->
			<xsl:when test="$getmode = 5">14</xsl:when><!-- 增额交清 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_BenefitMode">
		<xsl:param name="BenefitMode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$BenefitMode = 0">1</xsl:when><!-- 趸领 -->
			<xsl:when test="$BenefitMode = 12">2</xsl:when><!-- 年领 -->
			<xsl:when test="$BenefitMode = 1">5</xsl:when><!-- 月领 -->
			<xsl:otherwise>12</xsl:otherwise>
			<!-- 增额交清 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insuyearflag">
		<xsl:param name="insuyearflag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insuyearflag = Y">2</xsl:when><!-- 年 -->
			<xsl:when test="$insuyearflag = A">3</xsl:when><!-- 年龄 -->
			<xsl:when test="$insuyearflag = M">4</xsl:when><!-- 月 -->
			<xsl:when test="$insuyearflag = D">5</xsl:when><!-- 天 -->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_relation">
		<xsl:param name="relation"></xsl:param>
		<xsl:choose>
			<xsl:when test="$relation = 301">00</xsl:when><!-- 本人 -->
			<xsl:when test="$relation = 303 or $relation = 304 or $relation = 306 or $relation = 309">
			</xsl:when>
			<xsl:when test="$relation = 305">04</xsl:when><!-- 配偶 -->
			<xsl:otherwise>06</xsl:otherwise><!-- 其他 -->
		</xsl:choose>
	</xsl:template>
	
	<!-- 缴费频次/保费缴纳方式 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">年交     </xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">月交      </xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">半年交    </xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">季交      </xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">趸交     </xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">不定期交  </xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  
	
</xsl:stylesheet>