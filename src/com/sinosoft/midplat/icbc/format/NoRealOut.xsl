<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
			<!-- 结果文件返回报文解析 -->
			<xsl:apply-templates select="Body/ResultDetail"/>
			<!--  投保单状态变更文件返回报文解析 -->	
			<xsl:apply-templates select="Body/StateDetail"/>				
			</Body>
		</TranData>
</xsl:template>


<!--结果文件返回报文解析  -->
<xsl:template name="resultDtl" match="ResultDetail">			
					<ResultDetail>
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<InsuId>050</InsuId><!-- 保险公司代码，工行定义的，中韩为固定值 050 -->
						<TranNo><xsl:value-of select="TranNo" /></TranNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<ContNo><xsl:value-of select="ContNo" /></ContNo>
						
						<Result><!-- 保单核保结论，需要映射 -->
						<xsl:call-template name="tran_Result">
						<xsl:with-param name="Result">
							<xsl:value-of select="Result" />
						</xsl:with-param>
						</xsl:call-template>
						</Result>
						
						<ReMark><xsl:value-of select="ReMark" /></ReMark>
						<TotalPrem><xsl:value-of select="TotalPrem" /></TotalPrem>
						<InsuName><xsl:value-of select="InsuName" /></InsuName>
						<InsuIDType><xsl:apply-templates select="InsuIDType" /></InsuIDType><!-- 被保人证件类型，需要映射 -->
						<InsuIDNo><xsl:value-of select="InsuIDNo" /></InsuIDNo>
						<MainRiskCode><xsl:apply-templates select="MainRiskCode" /></MainRiskCode><!-- 主险种代码，需要映射 -->
						<PayIntv>
						  <xsl:apply-templates select="PayIntv" />
						</PayIntv>
						<RiskState><!-- 险种核保结论，需要映射 -->
						<xsl:call-template name="tran_RiskState">
						<xsl:with-param name="RiskState">
							<xsl:value-of select="RiskState" />
						</xsl:with-param>
						</xsl:call-template>
						</RiskState>
						
						<Mult><xsl:value-of select="Mult" /></Mult>
						<Prem><xsl:value-of select="Prem" /></Prem>
						<Amnt><xsl:value-of select="Amnt" /></Amnt>
						
						<InsuYearFlag><!-- 保险期间类型，需要映射 -->
						<xsl:call-template name="tran_InsuYearFlag">
						<xsl:with-param name="InsuYearFlag">
							<xsl:value-of select="InsuYearFlag" />
						</xsl:with-param>
						</xsl:call-template>
						</InsuYearFlag>
						<InsuYear><xsl:value-of select="InsuYear" /></InsuYear>
						
						<PayEndYearFlag><!-- 缴费年期类型,需要映射 -->
						<xsl:call-template name="tran_PayEndYearFlag">
						<xsl:with-param name="PayEndYearFlag">
							<xsl:value-of select="PayEndYearFlag" />
						</xsl:with-param>
						</xsl:call-template>
						</PayEndYearFlag>
						<PayEndYear><xsl:value-of select="PayEndYear" /></PayEndYear>
						
						<!-- 附加险部分信息解析 -->
						<List>
						<xsl:for-each select="List/Risk">
						<Risk>
							<RiskCode><xsl:apply-templates select="RiskCode" /></RiskCode><!-- 附加险险种代码，需要映射 -->
							
							<RiskState><!-- 险种核保结论，需要映射 -->
							<xsl:call-template name="tran_RiskState">
							<xsl:with-param name="RiskState">
								<xsl:value-of select="RiskState" />
							</xsl:with-param>
							</xsl:call-template>
							</RiskState>
							
							<Mult><xsl:value-of select="Mult" /></Mult>
							<Prem><xsl:value-of select="Prem" /></Prem>
							<Amnt><xsl:value-of select="Amnt" /></Amnt>
							
							<InsuYearFlag><!-- 保险期间类型，需要映射 -->
							<xsl:call-template name="tran_InsuYearFlag">
							<xsl:with-param name="InsuYearFlag">
								<xsl:value-of select="InsuYearFlag" />
							</xsl:with-param>
							</xsl:call-template>
							</InsuYearFlag>
							<InsuYear><xsl:value-of select="InsuYear" /></InsuYear>
							
							<PayEndYearFlag><!-- 缴费年期类型,需要映射 -->
							<xsl:call-template name="tran_PayEndYearFlag">
							<xsl:with-param name="PayEndYearFlag">
								<xsl:value-of select="PayEndYearFlag" />
							</xsl:with-param>
							</xsl:call-template>
							</PayEndYearFlag>
							<PayEndYear><xsl:value-of select="PayEndYear" /></PayEndYear>
						</Risk>
						</xsl:for-each>
						</List>
					</ResultDetail>
</xsl:template>

<!--  投保单状态变更文件返回报文解析 -->
<xsl:template name="stateDtl" match="StateDetail">
				<StateDetail>
					<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
					<InsuId>050</InsuId><!-- 保险公司代码，工行定义的，中韩为固定值 050 -->
					<TranNo><xsl:value-of select="TranNo" /></TranNo>
					<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
					<AppntName><xsl:value-of select="AppntName" /></AppntName>
					<!-- 投保单受理状态 -->
					<State><xsl:value-of select="State" /></State>
					<!-- 银行只要返回有变更的(以银行的状态来为标准的),让核心那边处理,核心Mapping成银行的状态码,我们直接取就可以了 -->
					<!-- 
					<State>
						<xsl:call-template name="tran_State">
						<xsl:with-param name="State">
							<xsl:value-of select="State" />
						</xsl:with-param>
						</xsl:call-template>
					</State>
					 -->
					<ReMark><xsl:value-of select="ReMark" /></ReMark>
				</StateDetail>
</xsl:template>


<!-- 保单核保结论 --><!-- 核心的-》工行的 -->
<xsl:template name="tran_Result">
	<xsl:param name="Result">3</xsl:param>
	<xsl:if test="$Result='1'">3</xsl:if><!-- 拒保 -->
	<xsl:if test="$Result='2'">5</xsl:if><!-- 延期 -->
	<xsl:if test="$Result='4'">1</xsl:if><!-- 次标准 -->
	<xsl:if test="$Result='9'">0</xsl:if><!-- 标准 -->
	<xsl:if test="$Result='a'">4</xsl:if><!-- 撤单 -->
</xsl:template>
	
<!-- 证件类型 -->
<xsl:template name="tran_idtype" match="InsuIDType">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- 身份证 -->
	<xsl:when test=".=1">1</xsl:when>	<!-- 护照 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 军官证 -->
	<xsl:when test=".=A">3</xsl:when>	<!-- 士兵证  -->
	<xsl:when test=".=B">4</xsl:when>	<!-- 回乡证  -->
	<xsl:when test=".=C">5</xsl:when>	<!-- 临时身份证  -->
	<xsl:when test=".=4">6</xsl:when>	<!-- 户口本  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- 其他  -->
	<xsl:when test=".=D">9</xsl:when>	<!-- 警官证  -->
										<!--12 外国人居留证  -->
	<xsl:otherwise>7</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 主险险种代码 -->
<xsl:template name="tran_MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".=231201">001</xsl:when>	
	<xsl:when test=".=231202">002</xsl:when>
	<xsl:when test=".=231203">003</xsl:when>
	<xsl:when test=".=231301">005</xsl:when>
	<xsl:when test=".=211901">004</xsl:when>
	<xsl:when test=".=221201">006</xsl:when>
	<xsl:when test=".=231204">007</xsl:when> <!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">008</xsl:when> <!-- 中韩安赢借款人意外伤害保险A款 -->
	<xsl:when test=".=241201">012</xsl:when> <!-- 中韩创赢财富两全保险（万能型）A款 -->
	<xsl:when test=".=221206">013</xsl:when>  <!--中韩优越财富两全保险  -->
	<xsl:when test=".=145201">102</xsl:when>  <!--中韩附加定盈宝两全保险（万能型）  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


<!-- 交费方式代码 -->
<xsl:template name="tran_PayIntv" match="PayIntv">
<xsl:choose>
	<xsl:when test=".=-1">6</xsl:when>
	<xsl:when test=".=0">5</xsl:when>      
	<xsl:when test=".=1">2</xsl:when>  
	<xsl:when test=".=3">4</xsl:when>
	<xsl:when test=".=6">3</xsl:when>
	<xsl:when test=".=12">1</xsl:when>
	<xsl:otherwise>9</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 险种核保结论 --><!-- 核心的-》工行的 -->
<xsl:template name="tran_RiskState">
	<xsl:param name="RiskState">0</xsl:param>
	<xsl:if test="$RiskState='1'">56</xsl:if>
	<xsl:if test="$RiskState='2'">58</xsl:if>
	<xsl:if test="$RiskState='9'">01</xsl:if>
	<xsl:if test="$RiskState='a'">57</xsl:if>
	<xsl:if test="$RiskState='b'">02</xsl:if>
	<xsl:if test="$RiskState='c'">16</xsl:if>
	<xsl:if test="$RiskState='d'">03</xsl:if>
	<xsl:if test="$RiskState='e'">21</xsl:if>
	<xsl:if test="$RiskState='f'">05</xsl:if>
	<xsl:if test="$RiskState='g'">18</xsl:if>
	<xsl:if test="$RiskState='h'">24</xsl:if>
</xsl:template>

<!-- 保障年期类型 -->
<xsl:template name="tran_InsuYearFlag">
	<xsl:param name="InsuYearFlag">0</xsl:param>
	<xsl:if test="$InsuYearFlag='A'">1</xsl:if>	<!-- 保至某确定年龄 -->
	<xsl:if test="$InsuYearFlag='Y'">2</xsl:if>	<!-- 年保 -->
	<xsl:if test="$InsuYearFlag='M'">3</xsl:if>	<!-- 月保 -->
	<xsl:if test="$InsuYearFlag='D'">4</xsl:if>	<!-- 日保 -->
</xsl:template> 

<!-- 缴费年期类型-->
<xsl:template name="tran_PayEndYearFlag">
	<xsl:param name="PayEndYearFlag">0</xsl:param>
	<xsl:if test="$PayEndYearFlag='A'">1</xsl:if>	<!-- 缴至某确定年龄 -->
	<xsl:if test="$PayEndYearFlag='Y'">2</xsl:if>	<!-- 年 -->
	<xsl:if test="$PayEndYearFlag='M'">3</xsl:if>	<!-- 月 -->
	<xsl:if test="$PayEndYearFlag='D'">4</xsl:if>	<!-- 日 -->
</xsl:template>

<!-- 附加险险种代码 -->
<xsl:template name="tran_RiskCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=241201">111</xsl:when>	
	<xsl:when test=".=241202">222</xsl:when>
	<xsl:when test=".=241203">333</xsl:when>  
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 投保单受理状态 --><!-- 核心的-》工行的 -->
<xsl:template name="tran_State">
	<xsl:param name="State">01</xsl:param>
	<xsl:if test="$State ='S00059'">01</xsl:if>
	<xsl:if test="$State ='S00060'">01</xsl:if>
	<xsl:if test="$State ='S00061'">07</xsl:if>
	<xsl:if test="$State ='S00062'">09</xsl:if>
	<xsl:if test="$State ='S00064'">07</xsl:if>
	<xsl:if test="$State ='S00065'">10</xsl:if>
	<xsl:if test="$State ='S00066'">01</xsl:if>
	<xsl:if test="$State ='S00067'">01</xsl:if>
	<xsl:if test="$State ='S00069'">09</xsl:if>
	<xsl:if test="$State ='S00070'">12</xsl:if>
	<xsl:if test="$State ='S00071'">12</xsl:if>
	<xsl:if test="$State ='S00072'">12</xsl:if>
	<xsl:if test="$State ='S00074'">08</xsl:if>
	<xsl:if test="$State ='S00078'">08</xsl:if>
	<xsl:if test="$State ='S00079'">09</xsl:if>
	<xsl:if test="$State ='S00080'">02</xsl:if>
	<xsl:if test="$State ='S00082'">09</xsl:if>
	<xsl:if test="$State ='S00083'">09</xsl:if>
	<xsl:if test="$State ='S00105'">02</xsl:if>
	<xsl:if test="$State ='S00106'">03</xsl:if>
	<xsl:if test="$State ='S00107'">03</xsl:if>
	<xsl:if test="$State ='S00151'">02</xsl:if>
	<xsl:if test="$State ='S00152'">03</xsl:if>
	<xsl:if test="$State ='S00153'">09</xsl:if>
	<xsl:if test="$State ='S10152'">01</xsl:if>
	<xsl:if test="$State ='S10153'">07</xsl:if>
	<xsl:if test="$State ='S10154'">09</xsl:if>
	<xsl:if test="$State ='S10158'">09</xsl:if>
	<xsl:if test="$State ='S10170'">15</xsl:if>
</xsl:template>

</xsl:stylesheet>
