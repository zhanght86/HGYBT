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
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>保险合同号码：</xsl:text>
						<xsl:value-of select="ContNo"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>投保人：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 12)"/>
						<xsl:text>性别：</xsl:text>
						<xsl:apply-templates select="Appnt/Sex"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('生日：', 8 ,$Falseflag)"/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Appnt/Birthday), 18)"/>
						<xsl:text>证件号码：</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/IDNo , 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>被保险人：</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 12)"/>
						<xsl:text>性别：</xsl:text>
						<xsl:apply-templates select="Insured/Sex"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('生日：', 8 ,$Falseflag)"/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Insured/Birthday), 18)"/>
						<xsl:text>证件号码：</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/IDNo , 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>生存受益人：</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 12)"/>
						
						<xsl:if test="count(Bnf) = 0">
						<xsl:text>身故受益人：</xsl:text>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('法定继承人 100%', 60)"/>
						</xsl:if>
						<xsl:if test="count(Bnf) = 1">
						<xsl:text>身故受益人：</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1 ">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/>
								</xsl:if> 
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 2  and sum(Bnf/Lot) != 100">
						<xsl:text>身故受益人：(顺位)</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/><xsl:text>;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 2  and sum(Bnf/Lot) = 100">
						<xsl:text>身故受益人：</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/><xsl:text>;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 3">
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1 ">
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 10)"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot), 10)"/>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>投保人联系地址：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Address, 40)"/>
						<xsl:text>邮政编码：</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/ZipCode, 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>投保人联系电话：  </xsl:text>
						<xsl:if test="Appnt/Mobile != ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Mobile, 78)"/>
						</xsl:if>
						<xsl:if test="Appnt/Mobile = ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Phone, 78)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>险种名称：  </xsl:text>
						<xsl:value-of select="Risk[RiskCode=MainRiskCode]/RiskName"/>
					</Value>
				</Prnt>
				<xsl:if test="Risk[RiskCode=MainRiskCode]/PayIntv != 0">
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>交费方式：  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/PayIntv"/>
						<xsl:text>    交费期间：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/PayEndYear) , 10)"/>
						<xsl:text>保险期间：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/InsuYear) , 40)"/>
					</Value>
				</Prnt>
				</xsl:if>
				<xsl:if test="Risk[RiskCode=MainRiskCode]/PayIntv = 0">
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>交费方式：  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/PayIntv"/>
						<xsl:text>    交费期间：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-', 10)"/>
						<xsl:text>保险期间：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/InsuYear) , 40)"/>
					</Value>
				</Prnt>
				</xsl:if>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>保险费：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.RMB(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Risk[RiskCode=MainRiskCode]/Prem)), 30)"/>
						<xsl:text>基本保险金额：  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.RMB(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Risk[RiskCode=MainRiskCode]/Amnt)), 40)"/>
					</Value>
				</Prnt>
				<xsl:if test="Risk/RiskCode = '222001' or Risk/RiskCode = '222002'">
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>红利领取方式：  累计生息</xsl:text>
					</Value>
				</Prnt>
				</xsl:if>
				<xsl:if test="Risk/RiskCode = '222003' and Risk[RiskCode=MainRiskCode]/BonusGetMode = ''">
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>红利领取方式：  累计生息</xsl:text>
					</Value>
				</Prnt>
				</xsl:if>
					<xsl:if test="Risk/RiskCode = '222003' and Risk[RiskCode=MainRiskCode]/BonusGetMode != ''">
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>红利领取方式：  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/BonusGetMode"/>
					</Value>
				</Prnt>
				</xsl:if>
	
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>保费合计：　人民币</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UpperRMB(java:com.sinosoft.midplat.common.YBTDataVerification.getChnMoney(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)),java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)), 72)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('（本栏以下空白）', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('特别约定：', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>　　　　</xsl:text>
						<xsl:if test="Risk[RiskCode=MainRiskCode]/SpecContent != ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Risk[RiskCode=MainRiskCode]/SpecContent, 88)"/>
						</xsl:if>
						<xsl:if test="Risk[RiskCode=MainRiskCode]/SpecContent = ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('本栏空白', 88)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>　　　　　　　　　　　　　　</xsl:text>主要保险利益摘要表（币值单位：人民币元）</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('主要保险利益摘要', 50)"/>
						<xsl:text/>
						<xsl:if test="Risk/RiskCode = '222001' or Risk/RiskCode = '222002'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('现金价值及交清保额(每千元保险费)', 46)"/>
						</xsl:if>
								<xsl:if test="Risk/RiskCode = '222003'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('现金价值及交清保额(每千元年交保险费)', 46)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('保单年度', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('年末生存金', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('疾病身故', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('意外身故', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('保单年度末', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('现金价值', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('减额交清', 18)"/>
					</Value>
				</Prnt>
				<xsl:for-each select="Risk/CashValues/CashValue">
					<Prnt>
						<Value>
							<xsl:text>　　　　</xsl:text>
							<xsl:text>　　　　　　　　　　　　　　　　　　　　　　　　　</xsl:text>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddYearEnd(EndYear),14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-', 18)"/>
						</Value>
					</Prnt>
				</xsl:for-each>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>如有未列年度及事项，详见合同条款，本栏以下空白。　上表所列的现金价值不包括因红利分配而生</xsl:text>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>　　　　　　　　　　　　　　　　　　　　　　　　　的相关利益。如有未列年度及事项，详见合</xsl:text>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>　　　　　　　　　　　　　　　　　　　　　　　　　同条款。本栏以下空白</xsl:text>
					</Value>
				</Prnt>
				<xsl:for-each select="Risk/CashValues/CashNum">
					<Prnt>
						<Value>
							<xsl:text></xsl:text>
						</Value>
					</Prnt>
				</xsl:for-each>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('实际保险利益以条款、特别约定及保单变更批注为准，未列明的保单现金价值及交清保额可向本公司咨', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('询，或致电全国统一咨询电话4008080080', 96)"/>
					</Value>
				</Prnt>
				
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>网点代码： </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom, 14)"/>
						<xsl:text>　　</xsl:text>
						<xsl:text>出单人代码： </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCode, 21)"/>
						<xsl:text>　　</xsl:text>
						<xsl:text>代理人： </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 21)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>保险公司服务地址: </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ComLocation, 36)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
					<xsl:text>　　　　</xsl:text>
						<xsl:text>保险合同签订日期: </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk[RiskCode=MainRiskCode]/SignDate)"/>
						<xsl:text>　　　　签单机构： </xsl:text>
						<xsl:value-of select="ComName"/>
					</Value>
				</Prnt>
			</Prnts>
			<!-- 现金价值列表 -->
			<Messages>
				<Count>0</Count>
				<xsl:for-each select="Risk/CashValues/CashValue">
					<Message>
						<Value>
							<xsl:text>　　　　</xsl:text>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddYearEnd(EndYear),14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 82)"/>
						</Value>
					</Message>
				</xsl:for-each>
			</Messages>
		</xsl:if>
		<!-- 如果交易成功，才返回上面的结点 -->
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
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_BonusGetMode" match="BonusGetMode">
		<xsl:choose>
			<xsl:when test=".=1">累积生息  </xsl:when>
			<xsl:when test=".=2">领取现金  </xsl:when>
			<xsl:when test=".=3">抵交保费  </xsl:when>
			<xsl:when test=".=4">其他 </xsl:when>
			<xsl:when test=".=5">增额交清  </xsl:when>
			<xsl:otherwise>无 </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_PayIntv" match="PayIntv">
		<xsl:choose>
			<xsl:when test=".=0">趸交  </xsl:when>
			<xsl:when test=".=1">月交  </xsl:when>
			<xsl:when test=".=3">季交  </xsl:when>
			<xsl:when test=".=6">半年交</xsl:when>
			<xsl:when test=".=12">年交  </xsl:when>
			<xsl:when test=".=-1">不定期</xsl:when>
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
