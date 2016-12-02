<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="SRCB">
		<TranData>
			<Head>
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="Header/TransDate" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="Header/TransTime" /></TranTime>
				<!-- 交易渠道 -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="Header/BranchNo" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0107</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="Header/TellerNo" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Header/SerialNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- 报文体 -->
		<xsl:apply-templates select="Body" />
		</TranData>
	</xsl:template>

	<!-- 报文体 -->
	<xsl:template match="Body">
		<Body>
		    <!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="Base/InsuranceSlipNo" /></ProposalPrtNo>
			<!-- 保单印刷号 -->
			<ContPrtNo><xsl:value-of select="Base/InsurancePrintNo" /></ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="Base/ApplyDate" /></PolApplyDate>
			<!-- 保单传送方式 -->
			<GetPolMode>
			  <xsl:call-template name="PbSendMode">
			    <xsl:with-param name="pbSendMode">
				<xsl:value-of select="Base/GetPolMode"/></xsl:with-param>
			  </xsl:call-template>
			</GetPolMode> 
			<!-- 健康告知 -->
			<HealthNotice><xsl:value-of select="Base/HealthNotice"></xsl:value-of></HealthNotice>
			<!-- 账户姓名 -->
			<AccName><xsl:value-of select="Base/AccName" /></AccName>
			<!-- 账户-->
			<AccNo><xsl:value-of select="Base/AccNo" /></AccNo>
			
			<!-- 销售人员工号 -->
		    <SaleStaff><xsl:value-of select="Base/SellerNo" /></SaleStaff>
		    <!-- 销售人员姓名  -->
            <SaleName><xsl:value-of select="Base/SellerName" /></SaleName>
            <!-- 销售人员资格证号  -->
            <SaleCertNo><xsl:value-of select="Base/SellerCertiCode" /></SaleCertNo>
            
			<!-- 投保人 -->
			<Appnt>
			<!-- 投保人姓名 -->
				<Name>
					<xsl:value-of select="PolicyHolder/Name" />
				</Name>
				<!-- 投保人性别 -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="PolicyHolder/Sex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- 投保人生日 -->
				<Birthday>
					<xsl:value-of select="PolicyHolder/Birthday" />
				</Birthday>
				<!-- 投保人证件类型 -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="PolicyHolder/IDType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 投保人证件号 -->
				<IDNo>
					<xsl:value-of select="PolicyHolder/IDNo" />
				</IDNo>
				<!-- 投保人职业代码   写死了，一般内勤-->
				<JobCode>3010101</JobCode>
				<!-- 投保人国籍 -->
				<Nationality>
							<xsl:value-of select="PolicyHolder/Nationality"/>
				</Nationality>
				
				<Stature />
				<Weight />
				<MaritalStatus><xsl:value-of select="PolicyHolder/MaritalStatus" /></MaritalStatus><!-- 核心不需要，也可以放空 -->
				<!-- 投保人年收入 -->
				<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(PolicyHolder/Salary)"/></YearSalary>
				<!-- 投保人证件有效期-->
				<xsl:choose>
				<xsl:when test="PolicyHolder/IDEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="PolicyHolder/IDEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!-- 投保人地址-->
				<Address>
					<xsl:value-of select="PolicyHolder/Address" />
				</Address>
				<!-- 投保人单位地址 -->
				<WorkAddress></WorkAddress>
				<!-- 投保人邮政编码-->
				<ZipCode>
					<xsl:value-of select="PolicyHolder/PostCode" />
				</ZipCode>
				<!-- 投保人单位邮政编码-->
				<WorkZipCode></WorkZipCode>
				<!-- 投保人移动电话-->
				<Mobile>
					<xsl:value-of select="PolicyHolder/Mobile" />
				</Mobile>
				<!-- 投保人固定电话-->
				<Phone>
					<xsl:value-of select="PolicyHolder/Phone" />
				</Phone>
				<!-- 投保人单位电话 -->
				<WorkPhone></WorkPhone>
				<!-- 投保人邮箱-->
				<Email>
				<xsl:value-of select="PolicyHolder/Email" />
				</Email>
				<!-- 投保人与被保人关系  这个关系需要跟银行确认一下，应该为投保人是被保人的XX，与核心需要关系相反-->
				<!-- 比如，SRCB传 01 被保人是投保人的父母，那么核心需要的是投保人是被保人的XX关系，所以需要转换为子女-->
				<RelaToInsured>
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="PolicyHolder/Relation" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured>
				<!-- 投保人家庭年收入 -->
				<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PolicyHolder/FamilySalary)"/></FamilyYearSalary>
				<!-- 投保人居民类型 -->
				<DenType><xsl:value-of select="PolicyHolder/LiveZone" /></DenType>
			</Appnt>

			<!-- 被保人 -->
			<Insured>
			<!-- 被保人姓名 -->
				<Name>
					<xsl:value-of select="Insured/Name" />
				</Name>
				<!-- 被保人性别 -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="Insured/Name" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- 被保人生日 -->
				<Birthday>
					<xsl:value-of select="Insured/Birthday" />
				</Birthday>
				<!-- 被保人证件类型-->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Insured/IDType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 被保人证件号-->
				<IDNo>
					<xsl:value-of select="Insured/IDNo" />
				</IDNo>
				<!-- 被保人职业代码-->
				<JobCode>3010101</JobCode>
				<!-- 被保人国籍-->
				<Nationality>
							<xsl:value-of select="Insured/Nationality" />
				</Nationality>
				<!-- 被保人证件有效期-->
				<xsl:choose>
				<xsl:when test="Insured/IDEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Insured/IDEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<Stature />
				<Weight />
				<MaritalStatus><xsl:value-of select="Insured/MaritalStatus" /></MaritalStatus><!-- 核心不需要，也可以放空 -->
				<!-- 收入 -->
				<YearSalary/>
				<!-- 被保人地址-->
				<Address>
					<xsl:value-of select="Insured/Address" />
				</Address>
				<!-- 被保人邮编-->
				<ZipCode>
					<xsl:value-of select="Insured/PostCode" />
				</ZipCode>
				<!-- 被保人移动号码-->
				<Mobile>
					<xsl:value-of select="Insured/Mobile" />
				</Mobile>
				<!-- 被保人固定电话-->
				<Phone>
					<xsl:value-of select="Insured/Phone" />
				</Phone>
				<!-- 被保人邮箱-->
				<Email></Email>
				
				<!-- 未成年被保险人在其他保险公司累计身故保额  单位：元-->
            	<CovSumAmt></CovSumAmt>
				
			</Insured>

			<!-- 受益人 -->
		
				<xsl:choose>
					<xsl:when
						test="/SRCB/Body/BeneNum ='0'">
					</xsl:when>
					<xsl:when
						test="/SRCB/Body/BeneNum !='0'">
						
						<xsl:for-each select="BeneList/Bene">
						<Bnf>
							<!-- 受益人类别 -->
							<Type>
								<xsl:value-of	select="Type" />
							</Type>
							<!-- 受益人顺序 -->
							<Grade><xsl:value-of select="Grade" /></Grade>
							<!--受益人姓名 -->
							<Name><xsl:value-of select="Name" /></Name>
							<!-- 受益人性别 -->
						     <Sex>
							   <xsl:call-template name="tran_sex">
								  <xsl:with-param name="Sex">
									 <xsl:value-of select="Sex" />
								  </xsl:with-param>
							   </xsl:call-template>
						    </Sex>
							<Birthday>
								<xsl:value-of	select="Birthday" />
							</Birthday>
							<IDType>
								<xsl:call-template name="tran_idtype">
									<xsl:with-param name="idtype">
										<xsl:value-of	select="IDType" />
									</xsl:with-param>
								</xsl:call-template>
							</IDType>
							<IDNo><xsl:value-of select="IDNo" /></IDNo>
							<!-- 收益比例 -->
							<Lot>
								<xsl:value-of	select="Rate" />
							</Lot>
							<!-- 受益人与投保人关系 -->
							<RelaToInsured>
								<xsl:call-template name="tran_relation">
									<xsl:with-param name="RelaToInsured">
										<xsl:value-of	select="Relation" />
									</xsl:with-param>
								</xsl:call-template>
							</RelaToInsured>
							<BeneficType>N</BeneficType>
						</Bnf>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			

			<!-- 险种 -->
			<Risk>
				<RiskCode>					
					<xsl:call-template name="tran_MainRiskCode">
						<xsl:with-param name="MainRiskCode">
							<xsl:value-of select="Base/RiskCode" />
						</xsl:with-param>
					</xsl:call-template>
				</RiskCode>
				<MainRiskCode>
					<xsl:call-template name="tran_MainRiskCode">
						<xsl:with-param name="MainRiskCode">
							<xsl:value-of select="Base/RiskCode" />
						</xsl:with-param>
					</xsl:call-template>	
				</MainRiskCode>
				<Amnt>
					<xsl:value-of select="Base/Amnt" />
				</Amnt>
				<Prem>
					<xsl:value-of select="Base/Prem" />
				</Prem>
				<Mult>
					<xsl:value-of select="Base/Share" />
				</Mult>
				<PayIntv>
					<xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="Base/PayMode" />
						</xsl:with-param>
					</xsl:call-template>
				</PayIntv>
				<!-- 早在几百年前，前置机和核心约定缴费方式为B，现在无从查起了 -->
				<PayMode>B</PayMode>
				<InsuYearFlag>
					<xsl:call-template name="tran_PbIYF">
						<xsl:with-param name="PbInsuYearFlag">
							<xsl:value-of select="Base/InsuYearType" />
						</xsl:with-param>
					</xsl:call-template>	
				</InsuYearFlag>
				<InsuYear><xsl:value-of select="Base/InsuYear" /></InsuYear>	
				<!-- 缴费年期和缴费年龄 -->
				 <xsl:if test="Base/PayDurationType = 1"><!-- 趸交1000 Y 给核心 -->				    
					<PayEndYear>1000</PayEndYear>
					<PayEndYearFlag>Y</PayEndYearFlag>
				</xsl:if>
				<xsl:if test="Base/PayDurationType != 1">	<!-- 非趸交  目前这个版本的核心只支持年交的 -->
				    <PayEndYearFlag>Y</PayEndYearFlag>			    
					<PayEndYear><xsl:value-of select="Base/PayDuration" /></PayEndYear>			
				</xsl:if>
				<BonusGetMode>
					<xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							<xsl:value-of select="Base/BonusGetMode" />
						</xsl:with-param>
					</xsl:call-template>
			 </BonusGetMode>
				<SpecContent />
				<!-- 满期领取方式，传空 -->
				<FullBonusGetMode />
    	         <GetYearFlag></GetYearFlag> 
    	          <!-- 领取年龄年期标志  这个需要转换-->
    	          <GetYear></GetYear>   <!-- 领取起始年龄 -->	
                  <GetTerms></GetTerms><!-- 领取年期 -->
				<!-- 领取方式 -->
				<GetIntv></GetIntv>
				<GetBankCode />
				<GetBankAccNo />
				<GetAccName />
				<AutoPayFlag><xsl:value-of select="Base/AutoPayFlag" /></AutoPayFlag>
			</Risk>
		</Body>
	</xsl:template>
	
	
	<!-- 性别 -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 0">0</xsl:if><!-- 男 -->
		<xsl:if test="$Sex = 1">1</xsl:if><!-- 女 -->
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
		<xsl:if test="$BonusGetMode = 2">2</xsl:if><!-- 直接给付 -->
		<xsl:if test="$BonusGetMode = 3">3</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$BonusGetMode = 0">1</xsl:if><!-- 累积生息 -->
	</xsl:template>

	<!-- 保单的缴费方式 -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 1">0</xsl:when><!-- 趸缴 -->
			<xsl:when test="$payintv = 2">12</xsl:when><!-- 年缴 -->
			<xsl:when test="$payintv = 3">6</xsl:when><!-- 半年缴 -->
			<xsl:when test="$payintv = 4">3</xsl:when><!-- 季缴 -->
			<xsl:when test="$payintv = 5">1</xsl:when><!-- 月缴 -->
			<xsl:when test="$payintv = 6">9</xsl:when><!-- 其他(YBT校验) -->
			<xsl:when test="$payintv = 7">-1</xsl:when><!-- 不定期 -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保单传送方式?    需要和核心确认 -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 0">1</xsl:when><!-- 纸质保单 -->
			<xsl:when test="$pbSendMode = 1">1</xsl:when><!-- 电子保单 -->
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
		<xsl:if test="$idtype = '0'">0</xsl:if><!-- 公民身份证号码 -->
		<xsl:if test="$idtype = '1'">1</xsl:if><!-- 护照-->
		<xsl:if test="$idtype = '2'">2</xsl:if><!-- 军官证 -->
		<xsl:if test="$idtype = '3'">8</xsl:if><!-- 港澳居民通行证-->
		<xsl:if test="$idtype = '4'">E</xsl:if><!-- 台湾居民通行证-->
		<xsl:if test="$idtype = '9'">8</xsl:if><!-- 其它 -->
	</xsl:template>

	<!-- 关系 -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:if test="$RelaToInsured = 00">00</xsl:if><!--本人 -->
		<xsl:if test="$RelaToInsured = 01">01</xsl:if><!--父母  -->
		<xsl:if test="$RelaToInsured = 02">02</xsl:if><!--配偶 -->
		<xsl:if test="$RelaToInsured = 03">03</xsl:if><!--儿女  -->
		<xsl:if test="$RelaToInsured = 04">06</xsl:if><!--其他 -->
</xsl:template>
     
	<!-- 主险种代码 -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">201201</xsl:if><!-- 险种1 -->
		<xsl:if test="$MainRiskCode = 0002">201202</xsl:if><!-- 险种2 -->
		<xsl:if test="$MainRiskCode = 0003">201203</xsl:if><!-- 险种3 -->
		<xsl:if test="$MainRiskCode = 0004">201204</xsl:if><!-- 险种4 -->
		<xsl:if test="$MainRiskCode = 0005">201205</xsl:if><!-- 险种5 -->

	</xsl:template>
	
</xsl:stylesheet>

