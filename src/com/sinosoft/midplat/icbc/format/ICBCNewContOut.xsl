<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	 
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse> 
	<TransRefGUID></TransRefGUID>
 	 <TransType>1012</TransType> 
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		
		<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse> 
	
</TXLife>
</xsl:template>  
 
 
<xsl:template name="OLifE" match="Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<xsl:variable name="RiderRisk" select="Risk[RiskCode!=MainRiskCode]" />
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
				<SpecialClause><xsl:value-of select="$MainRisk/SpecContent"/>无</SpecialClause>
				<!-- 旧保单印刷号 -->
				<OriginalPolicyFormNumber/>
				<!-- 保险公司服务电话 -->
				<InsurerDialNumber><xsl:value-of select="ComPhone"/></InsurerDialNumber>
				<!-- 人意险保障信息 -->
				<ContractNo/>
				<!-- 贷款合同号 -->
				<LoanAccountNo/>
				<!-- 贷款产品代码 -->
				<LoanProductCode/>
				<!-- 借款金额 -->
				<LoanAmount/>
				<!-- 保险金额 -->
				<FaceAmount/>
				<!-- 贷款起始日期 -->
				<LoanStartDate/>
				<!-- 贷款到期日期 -->
				<LoanEndDate/>
				<!-- 保险合同生效日期 -->
				<ContractEffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)"/></ContractEffDate>
				<!-- 保险合同到期日期 -->
				<ContractEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/InsuEndDate)"/></ContractEndDate>
				<!-- 保障类型 -->
				<CovType/>
				<!-- 保障区域 -->
				<CovArea/>
				<!-- 保险起期 -->
				<StartDate/>
				<!-- 保险止期 -->
				<EndDate/>
				<!-- 总保费 -->
				<GrossPremAmt><xsl:value-of select="../Body/Prem"/></GrossPremAmt>
				<!-- 前往国家 -->
				<CountryTo>China</CountryTo>
				<!-- 保险期限（天） -->
				<CovPeriod/>
				<!-- 试算后总保费 -->	
				<CalAmount><xsl:value-of select="../Body/Prem"/></CalAmount>
			</OLifEExtension>
		</Policy>
	</Holding>
</OLifE>

 <!--保单逐行打印接口       -->
	<Print>
		<!--凭证个数-->
		<VoucherNum>1</VoucherNum>
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
					<TextRowContent><xsl:text></xsl:text>                                                                            货币单位：人民币/元</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>保单合同号:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 51)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)"/> 零时</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>      
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Appnt/IDNo"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Insured/IDNo"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>受益人姓名<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 20)"/>
																									<xsl:text>性别</xsl:text><xsl:text> </xsl:text>
																									<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益份额', 14, $Falseflag)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('受益顺序  ', 26, $Falseflag)"/>
					</TextRowContent>
					
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					<xsl:variable name="num" select="count(Bnf)" />
					<xsl:choose>
					<xsl:when test="$num = 0"><TextRowContent><xsl:text></xsl:text>法定</TextRowContent>
					<TextRowContent/>
					<TextRowContent/>
					</xsl:when>
					</xsl:choose>
						<xsl:for-each select="Bnf">
						<TextRowContent>
					<xsl:text></xsl:text>     <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 25)"/>
																									<xsl:text>      </xsl:text><xsl:apply-templates select="Sex"/><xsl:text> </xsl:text>
																									<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 10, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 21, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/>
					</TextRowContent>
					</xsl:for-each>
					<xsl:if test="$num = 2" >
					<TextRowContent/>
					</xsl:if>
					<xsl:if test="$num = 3" >
					</xsl:if>
										
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>险种名称                          保险期间    交费期间    交费方式  （基本）保额/份数   保险费</TextRowContent>
					<xsl:for-each select="Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = 'A'">
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
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>元</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</TextRowContent>
					</xsl:for-each>
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />  
					<TextRowContent><xsl:text></xsl:text>保险费合计：<xsl:value-of select="PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>元）</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent />
					<TextRowContent ><xsl:choose><xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode !='121504'">
                                     <xsl:text>红利领取方式：</xsl:text><xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/BonusGetMode" /> 
					                </xsl:when>
					                <xsl:otherwise>
					                	<xsl:text>（本栏空白）</xsl:text>
					                </xsl:otherwise>
					</xsl:choose></TextRowContent>                                                              
					<TextRowContent />
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>特别约定：</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>（无）</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</TextRowContent>
	        		<!-- <TextRowContent />
					<TextRowContent /> 
					<TextRowContent />-->
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/>中韩人寿保险有限公司</TextRowContent>
					<TextRowContent><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 79,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></TextRowContent>
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>公司地址：<xsl:value-of select="ComLocation"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>全国客户服务热线：<xsl:value-of select="ComPhone"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>营业网点：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom,40)"/> 所属团队：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentComName,5)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>保单打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('',18)"/>客户经理：<xsl:value-of select="AgentName"/></TextRowContent> 
			</Text>
		</SubVoucher>
		 <xsl:if test="$MainRisk/CashValues/CashValue != ''">
	<SubVoucher>
		<!--凭证类型 4 现价表-->
		<VoucherType>4</VoucherType>
		<!--总页数-->
		<PageTotal>1</PageTotal>
		<Text> 
		<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
					     <PageNum>1</PageNum>
					     <!-- <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>-->
					     <TextRowContent/>
					     <TextRowContent/>
						   <TextRowContent/>
					     <TextRowContent/> 
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>                                        现金价值表                     </TextRowContent>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>保单合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					       <TextRowContent/>
					       <TextRowContent >  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					       <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
					       <TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*本表给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现金价值额。</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>投保后所做的各项变更可能使本表不再适用。</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向公</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>司来电垂询。</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent> 
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text></TextRowContent>
					       <TextRowContent><xsl:text>             </xsl:text><xsl:value-of select="ContNo"/></TextRowContent>
					       <TextRowContent><xsl:text>           </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 19,$Falseflag)"/><xsl:text>                    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18,$Falseflag)"/><xsl:text>              </xsl:text><xsl:value-of select="AgentCode"/></TextRowContent>
						   <TextRowContent><xsl:text>                                                     </xsl:text><xsl:value-of select="ContNo"/></TextRowContent>
		 </Text>
	 </SubVoucher>
</xsl:if>
	</Print>
</xsl:template>

 <!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:if test=" /TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '121504'">
			<xsl:for-each select="CashValue[EndYear &lt; (11)]">
			<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
					<TextRowContent><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/>年末<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-    ',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+10)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+10)]/EndYear,2,$Falseflag)"/>年末<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+10)]/Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-    ',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+20)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+20)]/EndYear,2,$Falseflag)"/>年末<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+20)]/Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-    ',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |  <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+30)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+30)]/EndYear,2,$Falseflag)"/>年末<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+30)]/Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-    ',13,$Falseflag)"/></xsl:otherwise></xsl:choose></TextRowContent>
			</xsl:for-each>
		</xsl:if>
		  <!--  中韩臻佑终身重大疾病保险 保终生，可以保106年， -->
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode= '121504' ">    
            <xsl:variable name="LeiShu" select="36"></xsl:variable>	
		    <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<TextRowContent><xsl:text>  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></TextRowContent>
		</xsl:for-each>
        </xsl:if>
</xsl:template>


<!-- 险种信息 -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('Cov_', string(position()))" /></xsl:attribute>
	<!-- 险种名称 -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- 险种代码 -->
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
	<!-- 险种类型 LifeCovTypeCode-->
	<xsl:choose>
		<xsl:when test="RiskCode='313020'">
			<LifeCovTypeCode>9</LifeCovTypeCode>	<!-- 非终身寿险 -->
		</xsl:when>  
		<xsl:otherwise> 
			<LifeCovTypeCode>1</LifeCovTypeCode>	<!-- 终身寿险 -->
		</xsl:otherwise>
	</xsl:choose> 
	
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
	<xsl:call-template name="tran_PayIntv">
			<xsl:with-param name="PayIntv"> 
				<xsl:value-of select="PayIntv"/>
			</xsl:with-param>   
		</xsl:call-template> 
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
	<!--  -->
	<FaceAmt>
	<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
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

		<xsl:choose>
			<xsl:when test="(PayEndYear = 105) and (PayEndYearFlag = 'A')">
				<PaymentDurationMode>5</PaymentDurationMode>
				<PaymentDuration>0</PaymentDuration>
			</xsl:when>
			<xsl:otherwise> 
				<PaymentDurationMode><xsl:apply-templates select="PayEndYearFlag" /></PaymentDurationMode>
				<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>
			</xsl:otherwise>
		</xsl:choose>
		 
		
		<!-- 保险期间 --> 
		<xsl:choose>
			<xsl:when test="(InsuYear= 105) and (InsuYearFlag = 'A')">
				<DurationMode>5</DurationMode>
				<Duration>999</Duration>
			</xsl:when>  
			<xsl:otherwise> 
				<DurationMode><xsl:apply-templates select="InsuYearFlag" /></DurationMode>
				<Duration><xsl:value-of select="InsuYear"/></Duration>
			</xsl:otherwise>
		</xsl:choose>
		
	</OLifEExtension>
</Coverage> 
</xsl:template> 
 




<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">男</xsl:when>	<!-- 男 -->
	<xsl:when test=".=1">女</xsl:when>	<!-- 女 -->
	<xsl:when test=".=2">其他</xsl:when>	<!-- 其他 -->
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
	<xsl:when test=".=231201">001</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">002</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）B款-->
	<xsl:when test=".=231203">003</xsl:when>	<!-- 中韩卓越财富两全保险（分红型）-->
	<xsl:when test=".=211901">004</xsl:when>	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=231301">005</xsl:when>	<!-- 中韩永裕丰年年金保险（分红型） -->
	<xsl:when test=".=221201">006</xsl:when>	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">007</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">008</xsl:when>	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:when test=".=221301">013</xsl:when>    <!-- 中韩悦未来年金险-->
	<xsl:when test=".=231302">010</xsl:when>    <!-- 中韩永利年年年金保险（分红型）-->
	<xsl:when test=".=221203">011</xsl:when>    <!-- 中韩悦无忧两全保险-->
	<xsl:when test=".=225501">101</xsl:when>    <!-- 中韩附加悦无忧重大疾病保险-->
	<xsl:when test=".=121504">014</xsl:when>    <!-- 中韩臻佑终身重大疾病保险-->
	<xsl:when test=".=145201">102</xsl:when>    <!-- 附加定盈宝两全保险（万能型）-->
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
 
 
<!-- 缴费频次 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">年交     </xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">月交      </xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">半年交    </xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">季交      </xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">趸交      </xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">不定期交  </xsl:when>	<!-- 不定期 -->
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
<xsl:template match="BonusGetMode">
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
