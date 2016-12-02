<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="Transaction">
		<TranData>
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="Transaction_Header/BkPlatDate" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="Transaction_Header/BkPlatTime" /></TranTime>
				<!-- 交易渠道 -->
				<!-- <TranCom><xsl:value-of select="Transaction_Header/BkChnlNo" /></TranCom> -->
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="Transaction_Header/BkBrchNo" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0122</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="Transaction_Header/BkTellerNo" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Transaction_Header/BkPlatSeqNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- 报文体 -->
		<xsl:apply-templates select="Transaction_Body" />
		</TranData>
	</xsl:template>

	<!-- 报文体 -->
	<xsl:template match="Transaction_Body">
		<Body>
		    <!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="PbApplNo" /></ProposalPrtNo>
			<!-- 保单印刷号 -->
			<ContPrtNo><xsl:value-of select="BkVchNo" /></ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="../Transaction_Header/BkPlatDate" /></PolApplyDate>
			<!-- 保单传送方式 -->
			<GetPolMode>
			  <xsl:call-template name="PbSendMode">
			    <xsl:with-param name="pbSendMode">
				<xsl:value-of select="PbSendMode"/></xsl:with-param>
			  </xsl:call-template>
			</GetPolMode> 
			<!-- 健康告知 -->
			<HealthNotice>
			  <xsl:call-template name="TranHeal">
			    <xsl:with-param name="HealFlag">
			    <xsl:value-of select="LiHealthTag"></xsl:value-of>
			    </xsl:with-param>
			  </xsl:call-template>
			</HealthNotice>
			<!-- 账户姓名 -->
			<AccName><xsl:value-of select="PbHoldName" /></AccName>
			
			<!-- 账户-->
			<AccNo><xsl:value-of select="BkAcctNo1" /></AccNo>
			
			<!-- 销售人员工号 -->
		    <SaleStaff><xsl:value-of select="BkRckrNo" /></SaleStaff>
		    <!-- 销售人员姓名  -->
            <SaleName><xsl:value-of select="BkSaleName" /></SaleName>
            <!-- 销售人员资格证号  -->
            <SaleCertNo><xsl:value-of select="BkSaleCertNo" /></SaleCertNo>
            
			<!-- 投保人 -->
			<Appnt>
			<!-- 投保人姓名 -->
				<Name>
					<xsl:value-of select="PbHoldName" />
				</Name>
				<!-- 投保人性别 -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="PbHoldSex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- 投保人生日 -->
				<Birthday>
					<xsl:value-of select="PbHoldBirdy" />
				</Birthday>
				<!-- 投保人证件类型 -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="PbHoldIdType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 投保人证件号 -->
				<IDNo>
					<xsl:value-of select="PbHoldId" />
				</IDNo>
				<!-- 投保人职业代码-->
				<JobCode>3010101</JobCode>
				<!-- 投保人国籍 -->
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
				<!-- 投保人年收入 -->
				<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PbInCome)"/></YearSalary>
				<!-- 投保人证件有效期-->
				<xsl:choose>
				<xsl:when test="PbIdEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="PbIdEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!-- 投保人地址-->
				<Address>
					<xsl:value-of select="PbHoldHomeAddr" />
				</Address>
				<!-- 投保人单位地址 -->
				<WorkAddress><xsl:value-of select="PbHoldAddr" /></WorkAddress>
				<!-- 投保人邮政编码-->
				<ZipCode>
					<xsl:value-of select="PbHoldHomePost" />
				</ZipCode>
				<!-- 投保人单位邮政编码-->
				<WorkZipCode><xsl:value-of select="PbHoldPost" /></WorkZipCode>
				<!-- 投保人移动电话-->
				<Mobile>
					<xsl:value-of select="PbHoldMobl" />
				</Mobile>
				<!-- 投保人固定电话-->
				<Phone>
					<xsl:value-of select="PbHoldHomeTele" />
				</Phone>
				<!-- 投保人单位电话 -->
				<WorkPhone><xsl:value-of select="PbHoldOfficTele" /></WorkPhone>
				<!-- 投保人邮箱-->
				<Email>
				<xsl:value-of select="PbHoldEmail" />
				</Email>
				
	<!-- 			<RelaToInsured> 金华银行说按照我们核心代码规则来20141219
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="PbHoldRcgnRela" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured> -->
				<RelaToInsured>
							<xsl:value-of select="PbHoldRcgnRela" />
				</RelaToInsured>
				<!-- 投保人家庭年收入 -->
				<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PbHomeInCome)"/></FamilyYearSalary>
				<!-- 投保人居民类型 -->
				<DenType><xsl:apply-templates select="PbDenType" /></DenType>
			</Appnt>

			<!-- 被保人 -->
			<Insured>
			<!-- 被保人姓名 -->
				<Name>
					<xsl:value-of select="LiRcgnName" />
				</Name>
				<!-- 被保人性别 -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="LiRcgnSex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- 被保人生日 -->
				<Birthday>
					<xsl:value-of select="LiRcgnBirdy" />
				</Birthday>
				<!-- 被保人证件类型-->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="LiRcgnIdType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 被保人证件号-->
				<IDNo>
					<xsl:value-of select="LiRcgnId" />
				</IDNo>
				<!-- 被保人职业代码-->
				<JobCode>3010101</JobCode>
				<!-- 被保人国籍-->
				<Nationality>
					<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="LiNationality" />
					</xsl:with-param>
					</xsl:call-template>
				</Nationality>
				<!-- 被保人证件有效期-->
				<xsl:choose>
				<xsl:when test="LiIdEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="LiIdEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<Stature />
				<Weight />
				<MaritalStatus />
				<!-- 收入 -->
				<YearSalary/>
				<!-- 被保人地址-->
				<Address>
					<xsl:value-of select="LiRcgnAddr" />
				</Address>
				<!-- 被保人邮编-->
				<ZipCode>
					<xsl:value-of select="LiRcgnPost" />
				</ZipCode>
				<!-- 被保人移动号码-->
				<Mobile>
					<xsl:value-of select="LiRcgnMobl" />
				</Mobile>
				<!-- 被保人固定电话-->
				<Phone>
					<xsl:value-of select="LiRcgnTele" />
				</Phone>
				<!-- 被保人邮箱-->
				<Email>
				<xsl:value-of select="LiRcgnEmail" />
				</Email>
				
							               <!-- 未成年被保险人在其他保险公司累计身故保额 -->
            <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(PiZxbe20)"/></CovSumAmt>
				
			</Insured>

			<!-- 受益人 -->
		
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
								<!-- 受益人性别 -->
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
						<!-- 受益人证件有效期-->
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
			

			<!-- 主险险种 -->
			<Risk>
			<!-- 20150109 lilu 银行的又说用核心的代码
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
				<!-- 缴费年期和缴费年龄 -->
				 <xsl:if test="PbPayPeriod = 0"><!-- 趸交1000Y给核心 -->				    
					<PayEndYear>1000</PayEndYear>
					<PayEndYearFlag>Y</PayEndYearFlag>
				</xsl:if>
				<xsl:if test="PbPayPeriod != 0">	<!-- 建行没有交费年期类型一说 -->
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
				<!-- 满期领取方式，传空 -->
				<FullBonusGetMode />
				 <xsl:choose>
    	            <xsl:when test="PbInsuType = '221301'">
    	               <GetYearFlag>
    	               <xsl:call-template name="tran_LiRenteDrawMode">
						<xsl:with-param name="GETLiRenteDrawMode">
							 <xsl:value-of select="LiRenteDrawMode" />
						</xsl:with-param>
					   </xsl:call-template>
    	               </GetYearFlag> <!-- 领取年龄年期标志  这个需要转换-->
    	               <GetYear><xsl:value-of select="PbDrawAge" /></GetYear>   <!-- 领取起始年龄 -->	
    	               </xsl:when>
    	                 <xsl:otherwise>
    	                    <GetYearFlag/>
    	                    <GetYear/>
    	              </xsl:otherwise>
                </xsl:choose>
                  <GetTerms><xsl:value-of select="PbDrawAgeTag" /></GetTerms><!-- 领取年期 -->
				<!-- 领取方式 -->
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
			
			<!-- 附加险循环 -->
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
					<!-- 缴费年期和缴费年龄 -->
					 <xsl:if test="/Transaction/Transaction_Body/PbPayPeriod = 0"><!-- 趸交1000Y给核心 -->				    
						<PayEndYear>1000</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<xsl:if test="/Transaction/Transaction_Body/PbPayPeriod != 0">	<!-- 建行没有交费年期类型一说 -->
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
					<!-- 满期领取方式，传空 -->
					<FullBonusGetMode />
					 <xsl:choose>
	    	            <xsl:when test="PbInsuType = '221301'">
	    	               <GetYearFlag>
	    	               <xsl:call-template name="tran_LiRenteDrawMode">
							<xsl:with-param name="GETLiRenteDrawMode">
								 <xsl:value-of select="/Transaction/Transaction_Body/LiRenteDrawMode" />
							</xsl:with-param>
						   </xsl:call-template>
	    	               </GetYearFlag> <!-- 领取年龄年期标志  这个需要转换-->
	    	               <GetYear><xsl:value-of select="PbDrawAge" /></GetYear>   <!-- 领取起始年龄 -->	
	    	               </xsl:when>
	    	                 <xsl:otherwise>
	    	                    <GetYearFlag/>
	    	                    <GetYear/>
	    	              </xsl:otherwise>
	                </xsl:choose>
	                  <GetTerms><xsl:value-of select="/Transaction/Transaction_Body/PbDrawAgeTag" /></GetTerms><!-- 领取年期 -->
					<!-- 领取方式 -->
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
		
		<xsl:if test = "PbInsuType='211902'"><!-- 安赢借贷险A款-->
		<Loan>
			<!-- 贷款合同号 -->
			<LoanNo><xsl:value-of select="LoanNo"/></LoanNo>
			<!-- 贷款机构 -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
			<!-- 贷款日期 -->
			<LoanDate><xsl:value-of select="LoanDate"/></LoanDate>
			<!-- 贷款到期日 -->
			<LoanEndDate><xsl:value-of select="LoanEndDate"/></LoanEndDate>
			<!-- 贷款种类 -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- 贷款账号 -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- 贷款金额 -->
			<xsl:choose><!-- 这里判断choose下，不然空的话在yuanToFen的时候变成0了，数据不原汁原味了 -->
			<xsl:when test = "LoanPrem=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LoanPrem)"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- 保险起始日 -->
			<InsuDate><xsl:value-of select="InsuDate"/></InsuDate>
			<!-- 保险期满日 -->
			<InsuEndDate><xsl:value-of select="InsuEndDate"/></InsuEndDate>
		</Loan>
		</xsl:if>
			
		</Body>
	</xsl:template>
	
	
	<!-- 性别 -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- 男 -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- 女 -->
	</xsl:template>

	<!-- 保险金领取方式 -->
	<xsl:template name="tran_GetIntv">
		<xsl:param name="GetIntv"></xsl:param>
		<xsl:if test="$GetIntv = 0">0</xsl:if>
	</xsl:template>
	
	
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>          <!--  0-一次给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>          <!--  1-月给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>          <!--  3-季给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>          <!--  6-半年给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>          <!--  12-年给付  -->
	</xsl:template>
	
	<!-- 投被保人国籍 -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 0156">CHN</xsl:if>
		<xsl:if test="$Nationality = 0344"></xsl:if><!--香港 -->
		<xsl:if test="$Nationality = 0158"></xsl:if><!--台湾 -->
		<xsl:if test="$Nationality = 0446"></xsl:if><!--澳门 -->
		<xsl:if test="$Nationality = 0392">JAN</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = 0840">USA</xsl:if><!--美国-->
		<xsl:if test="$Nationality = 0643"></xsl:if><!--俄罗斯 -->
		<xsl:if test="$Nationality = 0826">ENG</xsl:if><!--英国 -->
		<xsl:if test="$Nationality = 0250">FRA</xsl:if><!--法国-->
		<xsl:if test="$Nationality = 0276">DEU</xsl:if><!--德国 -->
		<xsl:if test="$Nationality = 0410">KOR</xsl:if><!--韩国 -->
		<xsl:if test="$Nationality = 0702">SG</xsl:if><!--新加坡 -->
		<xsl:if test="$Nationality = 0360">INA</xsl:if><!--印度尼西亚-->
		<xsl:if test="$Nationality = 0356">IND</xsl:if><!--印度-->
		<xsl:if test="$Nationality = 0380">ITA</xsl:if><!--意大利 -->
		<xsl:if test="$Nationality = 0458">MY</xsl:if><!--马来西亚 -->
		<xsl:if test="$Nationality = 0764">THA</xsl:if><!--泰国-->
		<xsl:if test="$Nationality = 0999"></xsl:if><!--其他-->
	</xsl:template>

	<!-- 红利领取方式 -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = 0">2</xsl:if><!-- 直接给付 -->
		<xsl:if test="$BonusGetMode = 1">3</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$BonusGetMode = 2">1</xsl:if><!-- 累积生息 -->
		<xsl:if test="$BonusGetMode = 3">5</xsl:if><!-- 增额交清 -->
	</xsl:template>

	<!-- 保单的缴费方式 -->
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
	
	<!-- 保单传送方式?    需要和核心确认 -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 1">1</xsl:when>
			<xsl:when test="$pbSendMode = 2">1</xsl:when>
			<xsl:when test="$pbSendMode = 3">1</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保障年期/年龄标志 -->
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

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype">0</xsl:param>
		<xsl:if test="$idtype = 'A'">0</xsl:if><!-- 公民身份证号码 -->
		<xsl:if test="$idtype = 'B'">2</xsl:if><!-- 军官证 -->
		<xsl:if test="$idtype = 'C'">8</xsl:if><!-- 解放军文职干部证 -->
		<xsl:if test="$idtype = 'D'">D</xsl:if><!-- 警官证 -->
		<xsl:if test="$idtype = 'E'">A</xsl:if><!-- 解放军士兵证 -->
		<xsl:if test="$idtype = 'F'">4</xsl:if><!-- 户口簿 -->
		<xsl:if test="$idtype = 'G'">B</xsl:if><!-- (港澳)回乡证及通行证 -->
		<xsl:if test="$idtype = 'H'">8</xsl:if><!-- 台通行证及其他有效旅行证-->
		<xsl:if test="$idtype = 'I'">1</xsl:if><!-- (外国)护照-->
		<xsl:if test="$idtype = 'J'">1</xsl:if><!-- (中国)护照-->
		<xsl:if test="$idtype = 'K'">8</xsl:if><!-- 武警文职干部证-->
		<xsl:if test="$idtype = 'L'">A</xsl:if><!-- 武警士兵证-->
		<xsl:if test="$idtype = 'M'">3</xsl:if><!-- 驾照 -->
		<xsl:if test="$idtype = 'Z'">8</xsl:if><!-- 其它 -->
	</xsl:template>

	<!-- 关系   金华银行说使用核心编码，所以不用转换-->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:if test="$RelaToInsured = 1">00</xsl:if><!--本人 -->
		<xsl:if test="$RelaToInsured = 2">02</xsl:if><!--丈夫 -->
		<xsl:if test="$RelaToInsured = 3">02</xsl:if><!--妻子  -->
		<xsl:if test="$RelaToInsured = 4">01</xsl:if><!--父亲  -->
		<xsl:if test="$RelaToInsured = 5">01</xsl:if><!--母亲 -->
		<xsl:if test="$RelaToInsured = 6">03</xsl:if><!--儿子 -->
		<xsl:if test="$RelaToInsured = 7">03</xsl:if><!--女儿-->
		<xsl:if test="$RelaToInsured = 8">04</xsl:if><!--祖父-->
		<xsl:if test="$RelaToInsured = 9">04</xsl:if><!--祖母-->
		<xsl:if test="$RelaToInsured = 10">04</xsl:if><!--孙子 -->
		<xsl:if test="$RelaToInsured = 11">04</xsl:if><!--孙女 -->
		<xsl:if test="$RelaToInsured = 12">04</xsl:if><!--外祖父-->
		<xsl:if test="$RelaToInsured = 13">04</xsl:if><!--外祖母 -->
		<xsl:if test="$RelaToInsured = 14">04</xsl:if><!--外孙 -->
		<xsl:if test="$RelaToInsured = 15">04</xsl:if><!--外孙女 -->
		<xsl:if test="$RelaToInsured = 16">06</xsl:if><!--哥哥 -->
		<xsl:if test="$RelaToInsured = 17">06</xsl:if><!--姐姐-->
		<xsl:if test="$RelaToInsured = 18">06</xsl:if><!--弟弟-->
		<xsl:if test="$RelaToInsured = 19">06</xsl:if><!--妹妹-->
		<xsl:if test="$RelaToInsured = 20">06</xsl:if><!--公公-->
		<xsl:if test="$RelaToInsured = 21">06</xsl:if><!--婆婆-->
		<xsl:if test="$RelaToInsured = 22">06</xsl:if><!--儿媳-->
		<xsl:if test="$RelaToInsured = 23">06</xsl:if><!--岳父-->
		<xsl:if test="$RelaToInsured = 24">06</xsl:if><!--岳母-->
		<xsl:if test="$RelaToInsured = 25">06</xsl:if><!--女婿-->
		<xsl:if test="$RelaToInsured = 26">06</xsl:if><!--其它亲属 -->
		<xsl:if test="$RelaToInsured = 27">06</xsl:if><!--同事 -->
		<xsl:if test="$RelaToInsured = 28">06</xsl:if><!--朋友--><!-- 核心没朋友啊，只能为其他喽 Update 20130226 -->
		<xsl:if test="$RelaToInsured = 29">06</xsl:if><!--雇主-->
		<xsl:if test="$RelaToInsured = 30">06</xsl:if><!--其它 -->
		<xsl:if test="$RelaToInsured = ''">--</xsl:if><!--其它 -->
	</xsl:template>

    <!-- 健康告知 -->
    <xsl:template name="TranHeal">
    <xsl:param name="HealFlag">N</xsl:param>
    <xsl:if test="$HealFlag = 0">N</xsl:if><!-- 无健康告知 -->
    <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- 有健康告知 -->
     </xsl:template>
     
<!-- 投保人居民类型 -->
<xsl:template name="tran_dentype" match="PbDenType">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- 城镇 -->
		<xsl:when test=".=1">2</xsl:when>	<!-- 农村 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
     
	<!-- 主险种代码 -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">231204</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
		<xsl:if test="$MainRiskCode = 0002">211902</xsl:if><!-- 中韩安赢借款人意外伤害保险A款 -->
		<xsl:if test="$MainRiskCode = 0003">221201</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$MainRiskCode = 0004">231302</xsl:if><!-- 中韩永利年年年金保险（分红型）-->
		<xsl:if test="$MainRiskCode = 0005">221203</xsl:if><!-- 中韩悦无忧两全保险-->
		<xsl:if test="$MainRiskCode = 0006">225501</xsl:if><!-- 中韩附加悦无忧重大疾病保险-->
	</xsl:template>
	
	<!-- 借贷险贷款种类 -->
<xsl:template name="tran_LoanType" match="LoanType">
	<xsl:choose>
		<xsl:when test=".='1'">01</xsl:when>	<!-- 普通贷款/一般商业贷款 -->
		<xsl:when test=".='2'">28</xsl:when>	<!-- 按揭贷款 -->
		<xsl:when test=".='3'">29</xsl:when>	<!-- 自主循环贷款 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
	
</xsl:stylesheet>

