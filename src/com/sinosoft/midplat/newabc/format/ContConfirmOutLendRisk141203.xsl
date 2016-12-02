<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
		<ABCB2I>
			<xsl:apply-templates select="Body"/>
		</ABCB2I>
	</xsl:template>
	<!-- 如果交易成功，才返回下面的结点 -->
	<xsl:template name="Base" match="Body">
		<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>
		<xsl:variable name="SumPremYuan" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:if test="//Flag='0'">
				<!--保单信息-->
			<App>
			   <Ret>
				<!-- 保单号 -->
				<PolicyNo>
					<xsl:value-of select="ContNo"/>
				</PolicyNo>
				<!-- 投保书号 -->
				<VchNo>
					<xsl:value-of select="ContPrtNo"/>
				</VchNo>
				<!-- 签单日期 -->
				<AcceptDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/SignDate"/>
				</AcceptDate>
				<!-- 保单生效日 -->
				<ValidDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/CValiDate"/>
				</ValidDate>
				<!-- 保险终止日 -->
				<!-- 不确定 -->
				<PolicyDuedate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</PolicyDuedate>
				<!-- 缴费期限 -->
				<!-- 不确定 -->
				<DueDate/>
				<!-- 业务员代码 -->
				<UserId>
					<xsl:value-of select="AgentCode"/>
				</UserId>
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
				<PayAccount>
					<xsl:value-of select="AccName"/>
				</PayAccount>
				<!-- 总保费 -->
				<Prem>
					<xsl:value-of select="$SumPremYuan"/>
				</Prem>
			<!-- 主险险种 -->
			<Risks>
				<xsl:for-each select="Risk[RiskCode=MainRiskCode]">
					<!-- 险种 -->
						<Name>
							<xsl:value-of select="RiskName"/>
						</Name>
						<Share>
							<xsl:value-of select="Mult"/>
						</Share>
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						</Prem>
						<PayDueDate>
							<xsl:value-of select="PayEndYear"/>
						</PayDueDate>
						<PayType>
							<xsl:call-template name="TempPayIntv">
								<xsl:with-param name="PayIntv"> 
									<xsl:value-of select="PayIntv"/>
								</xsl:with-param>   
							</xsl:call-template>  
						</PayType>
				</xsl:for-each>
			</Risks>
			<!--  附加险   就一个附加险处理-->
			<Addt>
			    <Count><xsl:value-of select="count(Risk[RiskCode!=MainRiskCode])"/></Count>
			     <xsl:if test="count(Risk[RiskCode!=MainRiskCode])=1">
			     <xsl:for-each select="Risk[RiskCode!=MainRiskCode]">
			       <Name1><xsl:value-of select="RiskName"/></Name1>
                   <Share1><xsl:value-of select="Mult"/></Share1>
                   <Prem1><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Prem1>
                   <PayDueDate1><xsl:value-of select="PayEndYear"/></PayDueDate1>
                   <PayType1>
                  <xsl:call-template name="TempPayIntv">
								<xsl:with-param name="PayIntv"> 
									<xsl:value-of select="PayIntv"/>
								</xsl:with-param>   
							</xsl:call-template> 
                   </PayType1>
			    </xsl:for-each>
			    </xsl:if>
			</Addt>
			
			<!-- 险种打印列表 -->
			<Prnts>
				<!-- 险种打印信息 -->
				<Count>0</Count>
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
				<Prnt/> 
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/> 
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt><xsl:text>　 </xsl:text>保险合同号:<xsl:value-of select="/TranData/Body/ContNo"/>　货币单位：人民币/元</Prnt>
				    <Prnt> 　 ------------------------------------------------------------------------------------------------ </Prnt>
				    <Prnt></Prnt> 
				    <Prnt> <xsl:text>　 </xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Prnt>
			        <Prnt>　 ------------------------------------------------------------------------------------------------</Prnt>
				    <Prnt><xsl:text></xsl:text>　 <xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Appnt/IDNo"/>
				   </Prnt>
				   <Prnt><xsl:text>　 </xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Insured/IDNo"/>
				  </Prnt>
				 <xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故/伤残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt><xsl:text></xsl:text>伤残第二顺序受益人为被保险人本人。</Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故/全残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt><xsl:text></xsl:text>全残第二顺序受益人为被保险人本人。</Prnt>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <Prnt><xsl:text></xsl:text>身故第二顺序受益人：法定</Prnt>
				 <Prnt/>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and Bnf[SeqNo='4']">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>受益比例：</xsl:text><xsl:value-of select="Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt/>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>身故第四顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
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
					<Prnt />
					<Prnt />
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text>险种名称                          保险期间    交费期间    交费方式  （基本）保险金额/份数  (首期)保险费</Prnt>
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
					<Prnt />
					<Prnt />
					<Prnt><xsl:text>保险期间起止时间：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/CValiDate)"/><xsl:text>零时起至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/InsuEndDate)"/><xsl:text>二十四时止</xsl:text></Prnt>  
					<Prnt><xsl:text></xsl:text>保险费合计：<xsl:value-of select="PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>元）</Prnt>
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt />
					<Prnt />
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt />
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text><xsl:text>特别约定：</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>（无）</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</Prnt>
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt/>
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text>银行网点名称：<xsl:value-of select="AgentComName"/></Prnt>
					<Prnt><xsl:text></xsl:text>银行销售人员姓名/代码：<xsl:value-of select="SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(SaleStaff,42)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></Prnt>
					<Prnt><xsl:text></xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 54)"/>银保经理电话：<xsl:value-of select="AgentPhone"/></Prnt>
					<Prnt/>
					<Prnt />
					<Prnt/>
					<Prnt/>
					<Prnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 79,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></Prnt>
					<Prnt />
				 </Prnts>
				
			<Messages>
				 <Count>0</Count>
				  <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
				  <Prnt></Prnt>
				  <Prnt></Prnt>
				  <Prnt></Prnt>
				  <Prnt></Prnt>
				  <Prnt></Prnt>
				  <Prnt></Prnt>
				  <Prnt><xsl:text>　 　</xsl:text>                               （本页空白）                 </Prnt>    
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt> 
				 <Prnt></Prnt>
				 <Prnt></Prnt>	
				 <Prnt></Prnt>
				 <Prnt></Prnt>       
				 <Prnt><xsl:text>　 　　　　　　　  </xsl:text><xsl:value-of select="ContNo"/></Prnt>
				 <Prnt><xsl:text>　 　　　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18,$Falseflag)"/><xsl:text>                  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18,$Falseflag)"/><xsl:text>              </xsl:text><xsl:value-of select="AgentCode"/></Prnt>
				 <Prnt><xsl:text>　 　                                                     </xsl:text><xsl:value-of select="ContNo"/></Prnt>
		</Messages>
		<TbdPrnts>
		                   <Count>22</Count>
		                   <Prnt1/>
					       <Prnt2/>
					       <Prnt3/>
					       <Prnt4/>
					       <Prnt5/>
					       <Prnt6><xsl:text>           </xsl:text>　　　　　　　　　　　　　　　　保险合同送达回执</Prnt6>
					       <Prnt7/>
					       <Prnt8/>
					       <Prnt9><xsl:text>           </xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></Prnt9>
					       <Prnt10><xsl:text>           </xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Prnt10>
						   <Prnt11><xsl:text>           </xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单、现</xsl:text></Prnt11>
						   <Prnt12><xsl:text>           </xsl:text><xsl:text>金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text></Prnt12>
						   <Prnt13><xsl:text>           </xsl:text><xsl:text>书和产品说明书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Prnt13>
						   <Prnt14><xsl:text>           </xsl:text><xsl:text></xsl:text></Prnt14>
						   <Prnt15/>
						   <Prnt16><xsl:text>           </xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text></Prnt16>
						   <Prnt17><xsl:text>           </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text></Prnt17>
						   <Prnt18><xsl:text>           </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Prnt18>
						   <Prnt19><xsl:text>           </xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></Prnt19>
						   <Prnt20><xsl:text>           </xsl:text><xsl:text>回执交回。</xsl:text></Prnt20>
						   <Prnt21/>
						   <Prnt22><xsl:text>           </xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></Prnt22>
		</TbdPrnts>
		<!-- 如果交易成功，才返回上面的结点 -->
				</Ret>
		</App>
		</xsl:if>

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
