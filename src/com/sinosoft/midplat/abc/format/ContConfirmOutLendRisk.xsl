<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
		<Ret>
			<!-- 返回数据包 -->
			<RetData>
				<Flag>
					<xsl:value-of select="Head/Flag"/>
				</Flag>
				<Mesg>
					<xsl:value-of select="Head/Desc"/>
				</Mesg>
			</RetData>
			<xsl:apply-templates select="Body"/>
		</Ret>
	</xsl:template>
	<!-- 如果交易成功，才返回下面的结点 -->
	<xsl:template name="Base" match="Body">
		<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>
		<xsl:variable name="SumPremYuan" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:if test="//Flag='0'">
			<!--保单信息-->
			<Base>
				<!-- 保单号 -->
				<ContNo>
					<xsl:value-of select="ContNo"/>
				</ContNo>
				<!-- 投保书号 -->
				<ProposalContNo>
					<xsl:value-of select="ProposalPrtNo"/>
				</ProposalContNo>
				<!-- 签单日期 -->
				<SignDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/SignDate"/>
				</SignDate>
				<!-- 保单生效日 -->
				<ContBgnDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/CValiDate"/>
				</ContBgnDate>
				<!-- 保险终止日 -->
				<!-- 不确定 -->
				<ContEndDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</ContEndDate>
				<!-- 缴费期限 -->
				<!-- 不确定 -->
				<ExpDate/>
				<!-- 业务员代码 -->
				<AgentCode>
					<xsl:value-of select="AgentCode"/>
				</AgentCode>
				<!-- 保险公司服务热线 -->
				<ComPhone>
					<xsl:value-of select="ComPhone"/>
				</ComPhone>
				<!-- 险种名称 -->
				<RiskName>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/RiskName"/>
				</RiskName>
				<!-- 账户姓名 -->
				<!-- 不确定 -->
				<BankAccName>
					<xsl:value-of select="AccName"/>
				</BankAccName>
				<!-- 总保费 -->
				<Prem>
					<xsl:value-of select="$SumPremYuan"/>
				</Prem>
			</Base>
			<!-- 险种列表 -->
			<Risks>
				<Count>
					<xsl:value-of select="count(Risk)"/>
				</Count>
				<xsl:for-each select="Risk">
					<!-- 险种 -->
					<Risk>
						<Name>
							<xsl:value-of select="RiskName"/>
						</Name>
						<Mult>
							<xsl:value-of select="Mult"/>
						</Mult>
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						</Prem>
						<PayEndYear>
							<xsl:value-of select="PayEndYear"/>
						</PayEndYear>
						<PayIntv>
							<xsl:call-template name="TempPayIntv">
								<xsl:with-param name="PayIntv"> 
									<xsl:value-of select="PayIntv"/>
								</xsl:with-param>   
							</xsl:call-template> 
						</PayIntv>
					</Risk>
				</xsl:for-each>
			</Risks>
			<!-- 险种打印列表 -->
			<Prnts>
				<!-- 险种打印信息 -->
				<Count>0</Count>
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
				<Prnt>
				    <Value/>
				 </Prnt>
				<Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				<Prnt>
				    <Value/>
				 </Prnt> 
				 <Prnt>  
				    <value><xsl:text>　 　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　    </xsl:text>货币单位：人民币/元 </value>
				 </Prnt>
				 <Prnt> 
				    <Value>　 ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt> 
				 <Prnt> 
					<Value><xsl:text>　 </xsl:text>保单合同号:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 51)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)"/> 零时</Value>
				 </Prnt>
			   <Prnt>
				    <Value>　 ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				<Prnt>
					<Value>
						<xsl:text>　 </xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Appnt/IDNo"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　 </xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Insured/IDNo"/>
					</Value>
				</Prnt>
				<xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
					 <Prnt>
					    <Value><xsl:text>　 </xsl:text><xsl:text>身故/伤残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text></Value>
					 </Prnt>
					 <Prnt>
					    <Value><xsl:text>　 </xsl:text>伤残第二顺序受益人为被保险人本人。</Value>
					 </Prnt>
				 </xsl:when>
				 <xsl:otherwise>
					 <Prnt>
					    <Value><xsl:text>　 </xsl:text><xsl:text>身故/全残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text></Value>
					 </Prnt>
					 <Prnt>
					    <Value><xsl:text>　 </xsl:text>全残第二顺序受益人为被保险人本人。</Value>
					 </Prnt>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <Prnt><Value><xsl:text>　 </xsl:text>身故第二顺序受益人：法定</Value></Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and Bnf[SeqNo='4']">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>受益份额：</xsl:text><xsl:value-of select="Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>身故第四顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益份额：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>
				<Prnt>
				    <Value>　 ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>险种名称                         保险期间   交费期间   交费方式 （基本）保额/份数  (首期)保险费</Value>
				 </Prnt>
				 <Prnt>
				 	<xsl:for-each select="Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<Value>
					<xsl:text>　 </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 32)"/>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskDay, 5,$Falseflag)"/><xsl:text>天</xsl:text>
																			<xsl:text>      </xsl:text>
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
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>天        </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁    </xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:apply-templates select="PayIntv"/>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>元</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,12,$Falseflag)"/>元</Value>
					</xsl:for-each>
				</Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value><xsl:text>　 保险期间起止时间：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/CValiDate)"/><xsl:text>零时起至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/InsuEndDate)"/><xsl:text>二十四时止</xsl:text></Value></Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>保险费合计：<xsl:value-of select="PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>元）</Value>
				 </Prnt>
				 <Prnt>
				    <Value>　 ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text><xsl:text>特别约定：</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>（无）</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
				    </Value>
				 </Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value><xsl:text>　 　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</xsl:text>中韩人寿保险有限公司</Value></Prnt>
				<Prnt><Value><xsl:text>　 　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></Value></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt>
				    <Value>　 ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>公司地址：<xsl:value-of select="ComLocation"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>全国客户服务热线：<xsl:value-of select="ComPhone"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>营业网点：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom,52)"/> 所属团队：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentComName,5)"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>　 </xsl:text>保单打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('',30)"/>客户经理：<xsl:value-of select="AgentName"/></Value>
				 </Prnt>
				 </Prnts>
				
			<Messages>
				<Count>0</Count>
				 <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				 <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				  <Message><Value/></Message>
				 <Message><Value/></Message>
				  <Message>
				    <Value><xsl:text>　 　</xsl:text>                               （本页空白）                     </Value>
				 </Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message> 
				 <Message><Value/></Message>
				 <Message><Value/></Message>	
				 <Message><Value/></Message>
				 <Message><Value/></Message>       
				 <Message><Value><xsl:text>　 　　　　　　　  </xsl:text><xsl:value-of select="ContNo"/></Value></Message>
				 <Message><Value><xsl:text>　 　　　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18,$Falseflag)"/><xsl:text>                  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18,$Falseflag)"/><xsl:text>              </xsl:text><xsl:value-of select="AgentCode"/></Value></Message>
				 <Message><Value><xsl:text>　 　                                                     </xsl:text><xsl:value-of select="ContNo"/></Value></Message>
		</Messages>
		<!-- 如果交易成功，才返回上面的结点 -->
		</xsl:if>
	</xsl:template>
	
	
	 <!-- 循环取现金价值信息 此款险种没有 -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (11)]">
				<Message><Value/></Message>
		</xsl:for-each>
</xsl:template>
	<!--证件类型  -->
	<xsl:template name="tran_IDType" match="IDType">
		<xsl:choose>
			<xsl:when test=".=0">身份证    </xsl:when>
			<!-- 身份证 -->
			<xsl:when test=".=1">护照      </xsl:when>
			<!-- 护照 -->
			<xsl:when test=".=2">军官证    </xsl:when>
			<!-- 军官证 -->
			<xsl:when test=".=3">其他      </xsl:when>
			<xsl:when test=".=4">出生证    </xsl:when>
			<xsl:when test=".=5">港澳通行证</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 性别 -->
	<xsl:template name="tran_sex" match="Sex">
		<xsl:choose>
			<xsl:when test=".=0">男  </xsl:when>
			<!-- 男 -->
			<xsl:when test=".=1">女  </xsl:when>
			<!-- 女 -->
			<xsl:when test=".=2">其他</xsl:when>
			<!-- 其他 -->
			<xsl:otherwise>--  </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_PayIntv" match="PayIntv">
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
	<xsl:template match="InsuYearFlag">
		<xsl:choose>
			<xsl:when test=".= 'Y'">年</xsl:when>
			<xsl:when test=".= 'A'">岁</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="InsuYearFlag"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	 <!-- 返回缴费方式 -->
<xsl:template name="TempPayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =0">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =3">3</xsl:when>
		<xsl:when test="PayIntv =6">4</xsl:when>
		<xsl:when test="PayIntv =12">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template> 
</xsl:stylesheet>
