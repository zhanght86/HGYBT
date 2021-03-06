<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<ABCB2I>
			<xsl:apply-templates select="Body"/>
	</ABCB2I>
	</xsl:template>
	<!-- 如果交易成功，才返回下面的结点 -->
	<xsl:template name="Base" match="Body">
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
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
				<Prnt></Prnt>
				<Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt> 
				 <Prnt><xsl:text>　 </xsl:text>    保险合同号:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 56)"/>货币单位：人民币/元</Prnt>
				 <Prnt> 　     ------------------------------------------------------------------------------------------------ </Prnt>
				 <Prnt></Prnt> 
				 <Prnt> <xsl:text>　 </xsl:text>    保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)" /> </Prnt>
			     <Prnt>　     ------------------------------------------------------------------------------------------------</Prnt>
				<Prnt><xsl:text></xsl:text>　 <xsl:text>    投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Appnt/IDNo"/>
				</Prnt>
				<Prnt><xsl:text>　 </xsl:text><xsl:text>    被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>投保年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="Insured/IDNo"/>
				</Prnt>				
			        <xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
					<xsl:variable name="num" select="count(Bnf) " />
					<xsl:for-each select="Bnf">
					<Prnt><xsl:text>　 </xsl:text>    受益人姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 14)"/>																
					<xsl:text>    性别：</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>　　</xsl:text>
					 <xsl:text>    受益顺序：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 14)"/>	
					<xsl:text>    受益比例：</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
                   	</Prnt>
					</xsl:for-each>
					<xsl:choose>
					<xsl:when test="$num = 0"><Prnt><xsl:text>　 </xsl:text><xsl:text></xsl:text>    受益人姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
					<xsl:text>    性别：</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
					 <xsl:text>    受益顺序：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
					<xsl:text>    受益比例：</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
                   	</Prnt></xsl:when>
					</xsl:choose>
				<Prnt>　     ------------------------------------------------------------------------------------------------</Prnt>
				 
				 <Prnt><xsl:text>　 </xsl:text>    险种名称                          保险期间    交费期间    交费方式  （基本）保额/份数   保险费</Prnt>
				 <Prnt>
				 	<xsl:for-each select="Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<xsl:text>　     </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
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
																								<xsl:text>趸交　　　　</xsl:text>
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
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,9,$Falseflag)"/><xsl:text>元</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</xsl:for-each></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt><xsl:text>　 </xsl:text>　  保险费合计：<xsl:value-of select="PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>元）</Prnt>
				<Prnt>　     ------------------------------------------------------------------------------------------------</Prnt>
				<Prnt ><xsl:text>　     </xsl:text>红利领取方式：<xsl:call-template name="Tran_BonusGetMode">
						<xsl:with-param name="bonusGetMode">
							<xsl:value-of select="Risk[RiskCode=MainRiskCode]/BonusGetMode"/>
						</xsl:with-param>
					</xsl:call-template>
				</Prnt>    
				<xsl:choose>
				<xsl:when test = "Risk[RiskCode=MainRiskCode]/GetYearFlag='E'">
				<Prnt ><xsl:text>　     </xsl:text>年金领取起始年龄：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('交费期满',52)"/>年金领取期限：<xsl:value-of select="Risk[RiskCode=MainRiskCode]/GetTerms"/>年</Prnt>
				</xsl:when>
				<xsl:otherwise>
				<Prnt ><xsl:text>　     </xsl:text>年金领取起始年龄：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Risk[RiskCode=MainRiskCode]/GetYear,52)"/>年金领取期限：<xsl:value-of select="Risk[RiskCode=MainRiskCode]/GetTerms"/>年</Prnt>
				</xsl:otherwise>
				</xsl:choose>
				<Prnt />
				<Prnt />
				<Prnt/>
				<Prnt/>
				<Prnt/>
				<Prnt><xsl:text>　     </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				<Prnt><xsl:text>　     </xsl:text><xsl:text>特别约定：</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>（无）</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
				</Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt></Prnt>
				<Prnt><xsl:text>　     </xsl:text>本保单是银行根据保险公司的授权代理销售，相关合同责任由保险公司承担。</Prnt>
				<Prnt><xsl:text>　     </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				<Prnt><xsl:text>　     </xsl:text>银行网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/></Prnt>
				<Prnt><xsl:text>　     </xsl:text>银行销售人员姓名/代码：<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,34)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Prnt>
				<Prnt><xsl:text>　     </xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 50)"/>银保经理电话：<xsl:value-of select="/TranData/Body/AgentPhone"/></Prnt>
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
			    <Prnt>　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Prnt>
				<Prnt></Prnt>
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
				 <Prnt><xsl:text>        </xsl:text>　　　　　　　　　　　　　　　　　　现金价值及减额交清表                     </Prnt>
				 <Prnt/>
				 <Prnt><xsl:text>　　　    </xsl:text>  保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>  险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>保单年 <xsl:text>         </xsl:text>减额交清后|保单年<xsl:text>          </xsl:text>减额交清后|保单年<xsl:text>         </xsl:text>减额交清后</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额|度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额|度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				 <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
				 <Prnt><xsl:text>　　　    </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>现金价值额。投保后所做的各项变更可能使本表不再适用。</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向公</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>司来电垂询。</Prnt>
				 <Prnt><xsl:text>　　　    </xsl:text>*变更为减额交清保险后，本合同将不再参加以后各年度的红利分配。</Prnt>
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
		</Messages>
		<TbdPrnts>
		                   <Count></Count>
		                   <Prnt/>
					       <Prnt/>
					       <Prnt/>
					       <Prnt/>
					       <Prnt/>
					       <Prnt><xsl:text>           </xsl:text>　　　　　　　　　　　　　　　　　　　　保险合同送达回执</Prnt>
					       <Prnt/>
					       <Prnt/>
					       <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　保险合同号: </xsl:text><xsl:value-of select="ContNo"/></Prnt>
					       <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="AgentCode"/></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　　　本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="ContNo"/><xsl:text>），本保险合同包括保险单、</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　现金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　提示书和产品说明书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text></xsl:text></Prnt>
						   <Prnt/>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　投保人签名：                                    签收日期：         年     月     日</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　以下栏由公司人员填写</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　------------------------------------------------------------------------------------------------</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　　　保险合同号为 </xsl:text><xsl:value-of select="ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></Prnt>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　回执交回。</xsl:text></Prnt>
						   <Prnt/>
						   <Prnt><xsl:text>           </xsl:text><xsl:text>　　　　银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></Prnt>
		</TbdPrnts>
		<!-- 如果交易成功，才返回上面的结点 -->
		</Ret>
		</App>
		</xsl:if>
	</xsl:template>
	
	
	 <!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (36)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<Prnt><xsl:text>　　　    </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),12,$Falseflag)"/><xsl:choose><xsl:when test="EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndYearAmnt),12,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',12,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',12,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYear!=''"><xsl:value-of select="../CashValue[EndYear=($EndYear+35)]/EndYear"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',11,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+70)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/Cash),11,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/></xsl:otherwise></xsl:choose> </xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/></xsl:otherwise></xsl:choose></Prnt>
		</xsl:for-each>
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
<!-- 红利领取方式的转换 -->
<xsl:template name="Tran_BonusGetMode"  match="tran_BonusGetMode">
<xsl:param name="bonusGetMode"/> 
<xsl:choose>
	<xsl:when test="$bonusGetMode ='1' ">累积生息</xsl:when>
	<xsl:when test="$bonusGetMode ='2' ">领取现金</xsl:when>
	<xsl:when test="$bonusGetMode ='3' ">抵交保费</xsl:when>
	<xsl:when test="$bonusGetMode ='5' ">增额交清</xsl:when>
	<xsl:when test="$bonusGetMode =' ' ">累积生息</xsl:when> 
	<xsl:otherwise>累积生息</xsl:otherwise>
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
