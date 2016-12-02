<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="TranData">
		<TranData>
			<Head>
				<xsl:copy-of select="Head/*"/>
			</Head>
			<Body>
			<!-- ����ļ����ر��Ľ��� -->
			<xsl:apply-templates select="Body/ResultDetail"/>
			<!--  Ͷ����״̬����ļ����ر��Ľ��� -->	
			<xsl:apply-templates select="Body/StateDetail"/>				
			</Body>
		</TranData>
</xsl:template>


<!--����ļ����ر��Ľ���  -->
<xsl:template name="resultDtl" match="ResultDetail">			
					<ResultDetail>
						<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
						<InsuId>050</InsuId><!-- ���չ�˾���룬���ж���ģ��к�Ϊ�̶�ֵ 050 -->
						<TranNo><xsl:value-of select="TranNo" /></TranNo>
						<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
						<ContNo><xsl:value-of select="ContNo" /></ContNo>
						
						<Result><!-- �����˱����ۣ���Ҫӳ�� -->
						<xsl:call-template name="tran_Result">
						<xsl:with-param name="Result">
							<xsl:value-of select="Result" />
						</xsl:with-param>
						</xsl:call-template>
						</Result>
						
						<ReMark><xsl:value-of select="ReMark" /></ReMark>
						<TotalPrem><xsl:value-of select="TotalPrem" /></TotalPrem>
						<InsuName><xsl:value-of select="InsuName" /></InsuName>
						<InsuIDType><xsl:apply-templates select="InsuIDType" /></InsuIDType><!-- ������֤�����ͣ���Ҫӳ�� -->
						<InsuIDNo><xsl:value-of select="InsuIDNo" /></InsuIDNo>
						<MainRiskCode><xsl:apply-templates select="MainRiskCode" /></MainRiskCode><!-- �����ִ��룬��Ҫӳ�� -->
						<PayIntv>
						  <xsl:apply-templates select="PayIntv" />
						</PayIntv>
						<RiskState><!-- ���ֺ˱����ۣ���Ҫӳ�� -->
						<xsl:call-template name="tran_RiskState">
						<xsl:with-param name="RiskState">
							<xsl:value-of select="RiskState" />
						</xsl:with-param>
						</xsl:call-template>
						</RiskState>
						
						<Mult><xsl:value-of select="Mult" /></Mult>
						<Prem><xsl:value-of select="Prem" /></Prem>
						<Amnt><xsl:value-of select="Amnt" /></Amnt>
						
						<InsuYearFlag><!-- �����ڼ����ͣ���Ҫӳ�� -->
						<xsl:call-template name="tran_InsuYearFlag">
						<xsl:with-param name="InsuYearFlag">
							<xsl:value-of select="InsuYearFlag" />
						</xsl:with-param>
						</xsl:call-template>
						</InsuYearFlag>
						<InsuYear><xsl:value-of select="InsuYear" /></InsuYear>
						
						<PayEndYearFlag><!-- �ɷ���������,��Ҫӳ�� -->
						<xsl:call-template name="tran_PayEndYearFlag">
						<xsl:with-param name="PayEndYearFlag">
							<xsl:value-of select="PayEndYearFlag" />
						</xsl:with-param>
						</xsl:call-template>
						</PayEndYearFlag>
						<PayEndYear><xsl:value-of select="PayEndYear" /></PayEndYear>
						
						<!-- �����ղ�����Ϣ���� -->
						<List>
						<xsl:for-each select="List/Risk">
						<Risk>
							<RiskCode><xsl:apply-templates select="RiskCode" /></RiskCode><!-- ���������ִ��룬��Ҫӳ�� -->
							
							<RiskState><!-- ���ֺ˱����ۣ���Ҫӳ�� -->
							<xsl:call-template name="tran_RiskState">
							<xsl:with-param name="RiskState">
								<xsl:value-of select="RiskState" />
							</xsl:with-param>
							</xsl:call-template>
							</RiskState>
							
							<Mult><xsl:value-of select="Mult" /></Mult>
							<Prem><xsl:value-of select="Prem" /></Prem>
							<Amnt><xsl:value-of select="Amnt" /></Amnt>
							
							<InsuYearFlag><!-- �����ڼ����ͣ���Ҫӳ�� -->
							<xsl:call-template name="tran_InsuYearFlag">
							<xsl:with-param name="InsuYearFlag">
								<xsl:value-of select="InsuYearFlag" />
							</xsl:with-param>
							</xsl:call-template>
							</InsuYearFlag>
							<InsuYear><xsl:value-of select="InsuYear" /></InsuYear>
							
							<PayEndYearFlag><!-- �ɷ���������,��Ҫӳ�� -->
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

<!--  Ͷ����״̬����ļ����ر��Ľ��� -->
<xsl:template name="stateDtl" match="StateDetail">
				<StateDetail>
					<NodeNo><xsl:value-of select="NodeNo" /></NodeNo>
					<InsuId>050</InsuId><!-- ���չ�˾���룬���ж���ģ��к�Ϊ�̶�ֵ 050 -->
					<TranNo><xsl:value-of select="TranNo" /></TranNo>
					<ProposalPrtNo><xsl:value-of select="ProposalPrtNo" /></ProposalPrtNo>
					<AppntName><xsl:value-of select="AppntName" /></AppntName>
					<!-- Ͷ��������״̬ -->
					<State><xsl:value-of select="State" /></State>
					<!-- ����ֻҪ�����б����(�����е�״̬��Ϊ��׼��),�ú����Ǳߴ���,����Mapping�����е�״̬��,����ֱ��ȡ�Ϳ����� -->
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


<!-- �����˱����� --><!-- ���ĵ�-�����е� -->
<xsl:template name="tran_Result">
	<xsl:param name="Result">3</xsl:param>
	<xsl:if test="$Result='1'">3</xsl:if><!-- �ܱ� -->
	<xsl:if test="$Result='2'">5</xsl:if><!-- ���� -->
	<xsl:if test="$Result='4'">1</xsl:if><!-- �α�׼ -->
	<xsl:if test="$Result='9'">0</xsl:if><!-- ��׼ -->
	<xsl:if test="$Result='a'">4</xsl:if><!-- ���� -->
</xsl:template>
	
<!-- ֤������ -->
<xsl:template name="tran_idtype" match="InsuIDType">
<xsl:choose> 
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=A">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=B">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=C">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=4">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- ����  -->
	<xsl:when test=".=D">9</xsl:when>	<!-- ����֤  -->
										<!--12 ����˾���֤  -->
	<xsl:otherwise>7</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �������ִ��� -->
<xsl:template name="tran_MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".=231201">001</xsl:when>	
	<xsl:when test=".=231202">002</xsl:when>
	<xsl:when test=".=231203">003</xsl:when>
	<xsl:when test=".=231301">005</xsl:when>
	<xsl:when test=".=211901">004</xsl:when>
	<xsl:when test=".=221201">006</xsl:when>
	<xsl:when test=".=231204">007</xsl:when> <!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">008</xsl:when> <!-- �к���Ӯ����������˺�����A�� -->
	<xsl:when test=".=241201">012</xsl:when> <!-- �к���Ӯ�Ƹ���ȫ���գ������ͣ�A�� -->
	<xsl:when test=".=221206">013</xsl:when>  <!--�к���Խ�Ƹ���ȫ����  -->
	<xsl:when test=".=145201">102</xsl:when>  <!--�к����Ӷ�ӯ����ȫ���գ������ͣ�  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


<!-- ���ѷ�ʽ���� -->
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

<!-- ���ֺ˱����� --><!-- ���ĵ�-�����е� -->
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

<!-- ������������ -->
<xsl:template name="tran_InsuYearFlag">
	<xsl:param name="InsuYearFlag">0</xsl:param>
	<xsl:if test="$InsuYearFlag='A'">1</xsl:if>	<!-- ����ĳȷ������ -->
	<xsl:if test="$InsuYearFlag='Y'">2</xsl:if>	<!-- �걣 -->
	<xsl:if test="$InsuYearFlag='M'">3</xsl:if>	<!-- �±� -->
	<xsl:if test="$InsuYearFlag='D'">4</xsl:if>	<!-- �ձ� -->
</xsl:template> 

<!-- �ɷ���������-->
<xsl:template name="tran_PayEndYearFlag">
	<xsl:param name="PayEndYearFlag">0</xsl:param>
	<xsl:if test="$PayEndYearFlag='A'">1</xsl:if>	<!-- ����ĳȷ������ -->
	<xsl:if test="$PayEndYearFlag='Y'">2</xsl:if>	<!-- �� -->
	<xsl:if test="$PayEndYearFlag='M'">3</xsl:if>	<!-- �� -->
	<xsl:if test="$PayEndYearFlag='D'">4</xsl:if>	<!-- �� -->
</xsl:template>

<!-- ���������ִ��� -->
<xsl:template name="tran_RiskCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=241201">111</xsl:when>	
	<xsl:when test=".=241202">222</xsl:when>
	<xsl:when test=".=241203">333</xsl:when>  
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- Ͷ��������״̬ --><!-- ���ĵ�-�����е� -->
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
