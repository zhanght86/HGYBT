<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	 
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse> 
	<TransRefGUID></TransRefGUID> 
 	 <TransType>1013</TransType> 
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse> 
</TXLife>
</xsl:template>  
 
 
<xsl:template name="OLifE" match="Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<OLifE>   
	<Holding id="cont">
		<CurrencyTypeCode>001</CurrencyTypeCode><!-- ? -->
		<!-- 保单信息 --> 
		<Policy>    
			<!-- 保单号ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--首期保费-->
			<PaymentAmt><xsl:value-of select="Prem"/></PaymentAmt> <!-- 本期保险费合计：根据PaymentAmt转化为汉字金额 （RMBPaymentAmt元） -->
			<Life> 
				<CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				<xsl:apply-templates select="Risk" />
			</Life>   
			<!--申请信息-->
			<ApplicationInfo>
				<!--投保书号-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate>
			</ApplicationInfo>
			<OLifEExtension>  
				<!-- 特别约定 --> 
				<SpecialClause><xsl:value-of select="$MainRisk/SpecContent"/></SpecialClause>
				<!-- 保险公司服务电话 -->
				<InsurerDialNumber><xsl:value-of select="ComPhone"/></InsurerDialNumber>		
				<!--  -->
				<WithdrawalDescription></WithdrawalDescription>	
			</OLifEExtension>
		</Policy>
	</Holding>
</OLifE>

 <!--保单逐行打印接口-->
	<Print>
		<SubVoucher>
			<!--凭证类型3保单-->
			<VoucherType>3</VoucherType>
			<!--总页数-->
			<PageTotal>1</PageTotal>
			<Text>
				<!--页号-->
				<PageNum>1</PageNum>
					<!--一行打印的内容--> 
					<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<TextRowContent/> 
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>                                           保单首页                                           </TextRowContent>
					<TextRowContent><xsl:text></xsl:text>交费方式：<xsl:apply-templates select="$MainRisk/PayIntv"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('货币单位：人民币',80,$Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>保单合同编号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 52)"/><xsl:text></xsl:text>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)" /><xsl:text></xsl:text></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>      
					<TextRowContent><xsl:text></xsl:text>投保人信息                                                                                    </TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Appnt/Sex "/><xsl:text>      </xsl:text>
																									<xsl:text>出生日期：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Appnt/Birthday)"/><xsl:text>      </xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/IDNo , 20)"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>被保人信息                                                                                    </TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>      </xsl:text>
																									<xsl:text>出生日期：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Insured/Birthday)"/><xsl:text>      </xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/IDNo , 20)"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent> 
					<TextRowContent><xsl:text></xsl:text>受益人信息                                                                                    </TextRowContent>
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					<xsl:if test="Bnf/Name = '法定' and Bnf/Sex = ''">
						<xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 94)"/>
                               </TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
						<TextRowContent/>
					</xsl:if>                                                                         

					<xsl:if test="count(Bnf) = 1 and Bnf/Sex != '' ">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>性别：</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('出生日期：', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益比例：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益顺序：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
						<TextRowContent/>
					</xsl:if>
					<xsl:if test="count(Bnf) = 2">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>性别：</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('出生日期：', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益比例：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益顺序：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
					</xsl:if>
					<xsl:if test="count(Bnf) = 3">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>性别：</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('出生日期：', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益比例：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益顺序：', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
					</xsl:if>
					
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>险种名称                               保险期间    交费年期    保额/份数    保险费/基本保险费 </TextRowContent>
					<xsl:if test="count(Risk) = 2 ">
					<xsl:for-each select="Risk">
						<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
						<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 38)"/>
																		<xsl:text> </xsl:text>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																		<xsl:text>   </xsl:text>
																					<xsl:choose>
																							<xsl:when test="PayEndYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text></xsl:text>
																				<xsl:choose>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,12,$Falseflag)"/><xsl:text>元</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,14,$Falseflag)"/>元<xsl:text>     </xsl:text></TextRowContent></xsl:for-each>				
					</xsl:if>
					<xsl:if test="count(Risk) = 1 ">
					<xsl:for-each select="Risk">
						<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
						<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 38)"/>
																		<xsl:text> </xsl:text>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																		<xsl:text>   </xsl:text>
																					<xsl:choose>
																							<xsl:when test="PayEndYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text></xsl:text>
																				<xsl:choose>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,12,$Falseflag)"/><xsl:text>元</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,14,$Falseflag)"/>元<xsl:text>     </xsl:text></TextRowContent></xsl:for-each>				
					<TextRowContent />
					</xsl:if>
					<xsl:if test="FirstAddPrem != '' ">
						<TextRowContent>
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetFirstAddMoney(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(FirstAddPrem)), 94 ,$Falseflag)"/>
						</TextRowContent>
					</xsl:if> 	
					<xsl:if test="FirstAddPrem = '' or FirstAddPrem = null ">
						<TextRowContent />
					</xsl:if>				                                                                  
					<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetCountMoney(PremText , java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)), 94 ,$Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<xsl:choose>
						<xsl:when test="Risk/RiskCode = 'NHY'">
							<TextRowContent><xsl:text></xsl:text>投资账户名称                                                               分配比例<xsl:text>           </xsl:text></TextRowContent>
							<xsl:if test="count(Risk/AccountList/Account) = 0">
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 1">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 2">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 3">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
						</xsl:otherwise>
					</xsl:choose>
					<TextRowContent><xsl:text></xsl:text>投资时间<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('合同生效后立即投资', 86 , $Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>特别约定：*</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>无                                                                                 </xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/SpecContent , 83)"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</TextRowContent>
	        <TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>公司地址：上海市浦东新区陆家嘴环路166号未来资产大厦19楼<xsl:text>   </xsl:text>全国咨询电话：400-670-5566          </TextRowContent>
					<TextRowContent><xsl:text></xsl:text>营业网点：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom,30)"/><xsl:text>                  </xsl:text>所属团队：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentComName , 26)"/><xsl:text></xsl:text></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>保单打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCurDateTime()" /><xsl:text>                         </xsl:text>业务员：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName , 25)"/><xsl:text></xsl:text></TextRowContent>	
			</Text>
		</SubVoucher>
		 <xsl:if test="$MainRisk/CashValues/CashValue != ''">
	<SubVoucher>
		<!--凭证类型 4 现价表-->
		<VoucherType>4</VoucherType>
		<!--总页数-->
		<PageTotal>1</PageTotal>
		<Text> 
					     <PageNum>1</PageNum>
					     <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>                                          现金价值表                                          </TextRowContent>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>保单合同号码：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 26)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetInsuredName(Insured/Name) , 54 ,$Falseflag)"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>险种名称：<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName , 84)"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
                           <TextRowContent>保单年度年终  现金价值  保单年度年终  现金价值  保单年度年   现金价值   保单年度年终  现金价值</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>				
						   <xsl:for-each select="Risk/CashValues/CashValue">						   
							   <xsl:if test="(EndYear) mod 4 = '1'">					   
				                   <TextRowContent>
				                   <xsl:variable name="EndYear1" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextOne(EndYear)"/>
								   <xsl:variable name="EndYear2" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextTwo(EndYear)"/>
								   <xsl:variable name="EndYear3" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextThree(EndYear)"/>												 			    									       
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 10)"/>                       		
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear1,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear1=EndYear]/Cash),10)"/>
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear2,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear2=EndYear]/Cash),10)"/>
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear3,9)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear3=EndYear]/Cash),9)"/>
								   </TextRowContent>
							   </xsl:if>		
						   </xsl:for-each>	
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent><xsl:text></xsl:text>---------------------------------------（本栏以下空白）---------------------------------------</TextRowContent>
					       <TextRowContent />
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*保险合同解除时，本公司向投保人退还本合同的现金价值。现金价值的数额按合同解除当日本合同的现金 </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>价值计算                                                                                      </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可 </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>向公司来电垂询                                                                                </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*在根据本保单有关条款支付或使用生存现金后，现金价值将会减少。                                 </TextRowContent>
		 </Text>
	 </SubVoucher>
</xsl:if>
	</Print>
</xsl:template>



<!-- 险种信息 -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('risk_', string(position()))" /></xsl:attribute>
	<!-- 险种名称 -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- 险种代码 -->
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>

	<xsl:choose>
		<!-- 主副险标志 -->
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose> 
	  
	<!-- 缴费方式 频次 -->  
	<PaymentMode>
		<xsl:value-of select="PayIntv" /> 
	</PaymentMode> 
     
	<!-- 投保金额 -->   
	<InitCovAmt>   
		<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</InitCovAmt>
	<!-- ? -->
	<FaceAmt>
		<xsl:choose>
			<xsl:when test="Amnt>=0"><xsl:value-of select="Amnt" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</FaceAmt>
	<!-- 投保份额 -->
	<IntialNumberOfUnits>    
		<xsl:choose>
			<xsl:when test="Mult>=0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose> 
	</IntialNumberOfUnits>
	<!-- 险种保费 -->
	<ModalPremAmt><xsl:value-of select="Prem"/></ModalPremAmt>
	<!-- 起保日期 -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- 保单终止日期 -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- 交费起始日期 -->  
	<FirstPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(SignDate)" /></FirstPaymentDate>
	<!-- 缴费终止日期 -->
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	
	<OLifEExtension>
		<!-- 缴费年期和缴费年期类型 --> 
			<PaymentDurationMode><xsl:value-of select="PayEndYearFlag" /></PaymentDurationMode>
			<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>		 		
		<!-- 保险年期和保险年期类型 -->
			<DurationMode><xsl:value-of select="InsuYearFlag" /></DurationMode>
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage> 
</xsl:template> 
 




<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".='M'">男</xsl:when>	<!-- 男 -->
	<xsl:when test=".='F'">女</xsl:when>	<!-- 女 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 证件类型？？？-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=7">9</xsl:when>	<!-- 警官证  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 关系 -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=01">1</xsl:when>	<!-- 配偶 -->
	<xsl:when test=".=03">2</xsl:when>	<!-- 父母 -->
	<xsl:when test=".=04">3</xsl:when>	<!-- 子女 -->
	<xsl:when test=".=21">4</xsl:when>	<!-- 祖父祖母 -->
	<xsl:when test=".=31">5</xsl:when>	<!-- 孙子女 -->
	<xsl:when test=".=12">6</xsl:when>	<!-- 兄弟姐妹 -->
	<xsl:when test=".=25">7</xsl:when>	<!-- 其他亲属 -->
	<xsl:when test=".=00">8</xsl:when>	<!-- 本人 -->
	<xsl:when test=".=27">9</xsl:when>	<!-- 朋友 -->
	<xsl:when test=".=25">99</xsl:when>	<!-- 其他 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose> 
</xsl:template>
  
<!-- 险种代码 -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".='PAC'">001</xsl:when>	<!-- 中融融盛连年终身寿险（万能型）B款 -->
	<xsl:when test=".='NBHY'">002</xsl:when>	<!-- 中融融盛连年终身寿险（万能型）A款 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 缴费形式
<xsl:template name="tran_PaymentMethod" match="PayMode">
<xsl:choose>
	<xsl:when test=".=4">1</xsl:when>	
	
	<xsl:when test=".=3">2</xsl:when>	 
	<xsl:when test=".=1">3</xsl:when>	
	<xsl:when test=".=1">4</xsl:when>	
	<xsl:when test=".=1">5</xsl:when>	
	<xsl:when test=".=4">6</xsl:when>	 
	<xsl:when test=".=8">7</xsl:when>	
	<xsl:when test=".=9">9</xsl:when>	
	
	<xsl:otherwise></xsl:otherwise>
</xsl:choose> 
</xsl:template>
 -->
 
       
   <!-- 返回缴费方式 -->
<xsl:template name="tran_PayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =12">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =6">3</xsl:when>
		<xsl:when test="PayIntv =3">4</xsl:when>
		<xsl:when test="PayIntv =0">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template> 
 
 
<!-- 缴费频次2 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=1">年缴</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=2">月缴</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=3">半年缴</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=4">季缴</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=5">趸缴</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=6">不定期缴</xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

    

<!-- 终交年期标志的转换 -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 缴至某确定年龄 -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
<!-- 保险年龄年期标志 -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 保至某确定年龄 -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- 年保 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- 月保 -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- 日保 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 



<!-- 红利领取方式的转换 -->	
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">累积生息</xsl:when>
	<xsl:when test=".=2">领取现金</xsl:when>
	<xsl:when test=".=3">抵交保费</xsl:when>
	<xsl:when test=".=5">增额交清</xsl:when>
	<xsl:when test=".=''">   --   </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
