<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
	<xsl:template match="/">
		<InsuRet>
			<Policy>
			<!-- 投保单号 -->
				<ApplyNo>
				    <xsl:value-of select="TranData/Body/ProposalPrtNo"/>
				</ApplyNo>
				<!-- 保单号 -->
				<PolicyNo>
				    <xsl:value-of select="TranData/Body/ContNo"/>
				</PolicyNo>
				<!-- 保单印刷号 -->
				<PrintNo></PrintNo>
				<!-- 投保日期 -->
				<ApplyDate>
				    <xsl:value-of select="TranData/Body/Risk/PolApplyDate"/>
				</ApplyDate>
				<!-- 首期总保费 -->
				<TotalPrem>
				    <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)"/>
				</TotalPrem>
				<!-- 主险保额 -->
				<InsuAmount>
				     <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Amnt)"/>
				</InsuAmount>
				<!-- 缴费终止日期 -->
				<PayEndDate>
				     <xsl:value-of select="TranData/Body/Risk/PayEndDate"/>
				</PayEndDate>
				<!-- 保单生效日期 -->
				<PolEffDate>
				     <xsl:value-of select="TranData/Body/Risk/CValiDate"/>
				</PolEffDate>
				<!-- 保单终止日期 -->
				<PolEndDate>
				     <xsl:value-of select="TranData/Body/Risk/SignDate"/>
				</PolEndDate>
			</Policy>
			<Print>
				<PaperTypeCount>1</PaperTypeCount><!-- 凭证类型个数 -->
				<Paper>
					<PaperType>1</PaperType><!-- 凭证类型 -->
					<PaperTitle>中融人寿保险单</PaperTitle><!-- 打印凭证说明 -->
					<PageCount></PageCount><!-- 凭证页数 -->
					<PageContent><!-- #####每页凭证的打印信息###### -->
						<RowCount></RowCount><!-- 凭证每页行数 -->
						<Details><!-- #####保单打印行内容循环##### -->
						<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
						<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>                                                       保 险 单</Row>
							<Row>             </Row>
							<Row>                                                                                           币值单位：人民币元</Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             保单合同号码（保单号）：<xsl:value-of select="TranData/Body/ContNo"/></Row>
							<Row>             保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /><xsl:text>零时                                   </xsl:text>交费方式：<xsl:apply-templates  select="$MainRisk/PayIntv" /></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             投保人：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name,22)"/>性别：<xsl:apply-templates select="TranData/Body/Appnt/Sex"/>    生日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Appnt/Birthday)"/>       证件号码：<xsl:value-of select="TranData/Body/Appnt/IDNo"/></Row>
							<Row>             被保险人：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Insured/Name,20)"/>性别：<xsl:apply-templates select="TranData/Body/Insured/Sex"/>    生日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Insured/Birthday)"/>       证件号码：<xsl:value-of select="TranData/Body/Insured/IDNo"/></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             </Row>
							<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
							<xsl:if test="count(/TranData/Body/Bnf) = 0">
							<Row>             身故受益人：未指定</Row>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="/TranData/Body/Bnf/Name = '未指定' or /TranData/Body/Bnf/Name = '法定'">
									<Row>             身故受益人：未指定</Row>
								</xsl:when>
								<xsl:when test="/TranData/Body/Bnf/Name != '未指定'">
									<xsl:for-each select="/TranData/Body/Bnf">
										<Row>             身故受益人：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 20)"/>             受益比例：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $flag)"/>%             受益顺序：<xsl:value-of select="Grade"/></Row>
									</xsl:for-each>
								</xsl:when>   
							</xsl:choose>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             险种名称                                红利领取方式  保险期间  交费年期  保额/份数    保险费</Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<xsl:for-each select="/TranData/Body/Risk">
							<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
							<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
							<Row><xsl:text>             </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 42)"/>
															<xsl:apply-templates select="$MainRisk/BonusGetMode" />
																					<xsl:choose>
																							<xsl:when test="(InsuYear= 105) and (InsuYearFlag = 'A')">
																								<xsl:text>    终身</xsl:text>
																							</xsl:when>
																							<xsl:when test="InsuYearFlag = 'Y'">
																								<xsl:text>     </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text>      </xsl:text>
																				<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>趸交</xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																				<xsl:choose>
																							<xsl:when test="RiskCode = 321010 or RiskCode = 321020 or RiskCode = '321170'">
																								<xsl:text>       --  </xsl:text>
																							</xsl:when>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>元</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</Row>
							</xsl:for-each>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             本期保险费合计：<xsl:value-of select="/TranData/Body/PremText"/>（RMB<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>元）</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             ---------------------------------------（本栏以下为空白）----------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             特别约定：</Row>
							<xsl:choose>
								<xsl:when test="$MainRisk/SpecContent = ''">
									<Row><xsl:text>             犹豫期提示语："您在收到保险合同后15个自然日内有全额退保（扣除不超过10元的工本费）的权利。</xsl:text></Row>
								    <Row><xsl:text>             超过15个自然日退保有损失。" </xsl:text></Row>
								    </xsl:when> 
								<xsl:otherwise> 
									<Row><xsl:value-of select="$MainRisk/SpecContent"/></Row>
								</xsl:otherwise>
							</xsl:choose>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row><xsl:text>             </xsl:text><xsl:value-of select="/TranData/Body/ComName"/></Row>
							<Row><xsl:text>             </xsl:text>服务网点地址：<xsl:value-of select="/TranData/Body/ComLocation"/></Row>
							<Row><xsl:text>             </xsl:text>邮政编码：<xsl:value-of select="/TranData/Body/ComZipCode"/></Row>
							<Row><xsl:text>             </xsl:text>公司网站：www.zhongronglife.com</Row>
							<Row><xsl:text>             </xsl:text>客户服务电话：4008186636</Row>
							<Row><xsl:text>             </xsl:text>为确保您的保单权益，请及时拨打本公司服务电话、登陆网站或到柜台</Row>
							<Row><xsl:text>             </xsl:text>进行查询，核实保单信息。</Row>
							<Row></Row>
							<Row><xsl:text>             </xsl:text>网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/></Row>
							<Row><xsl:text>             </xsl:text>销售人员名称：<xsl:value-of select="/TranData/Body/AgentPersonName"/></Row>
						</Details>
					</PageContent>
					<xsl:if test="/TranData/Body/Risk/CashValues/CashValue != ''">
					<PageContent>
						<RowCount></RowCount>
						<Details>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>                                               现金价值表</Row>
							<Row><xsl:text>             </xsl:text>保单合同号码：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 16)"/><xsl:text>                                  </xsl:text>币值单位：人民币元 </Row>
							<Row><xsl:text>             </xsl:text>保单年度末<xsl:text>                                  </xsl:text><xsl:value-of select="/TranData/Body/Risk/RiskName"/></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<xsl:for-each select="/TranData/Body/Risk/CashValues/CashValue">  
					       	<Row><xsl:text>             </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,50)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash)"/>元</Row> 
					        </xsl:for-each>
							<Row><xsl:text>             </xsl:text>---------------------------------------（本栏以下为空白）---------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------------</Row>
							<Row><xsl:text>             </xsl:text> 现金价值表中所有列保险年度末现金价值为保险合同在每一保险年度最后一天的现金价值，是本公司按照中国保险监</Row>
							<Row><xsl:text>             </xsl:text> 督管理委员会的有关规定计算确定的，其他时间的现金价值将在该时间所在保险年度对应的年度末现金价值基础上另行</Row>
							<Row><xsl:text>             </xsl:text> 计算。</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
						</Details>
					</PageContent>
					</xsl:if>									     
				</Paper>
			</Print>  	
		</InsuRet>
	</xsl:template>
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 321010">0002</xsl:if>
		<xsl:if test="$RiskCode = 313030">0001</xsl:if>
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
<!-- 缴费频次2 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">年缴</xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">月缴</xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">半年缴</xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">季缴</xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">趸缴</xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">不定期缴</xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
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
</xsl:stylesheet>
