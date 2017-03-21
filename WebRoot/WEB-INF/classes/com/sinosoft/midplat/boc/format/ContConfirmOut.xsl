<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
<xsl:template match="TranData">
<InsuReq>
  <Main>
    <TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Date()"/></TranDate>
    <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
    <InsuId></InsuId>
    <ZoneNo></ZoneNo>
    <BrNo></BrNo>
    <TellerNo></TellerNo>
    <TransNo></TransNo>
    <TranCode></TranCode>
    <xsl:if test="Head/Flag='0'">
    <ResultCode>0000</ResultCode>
    </xsl:if>
    <xsl:if test="Head/Flag='1'">
    <ResultCode>0001</ResultCode>
    </xsl:if>
    <ResultInfo><xsl:value-of select="Head/Desc"/></ResultInfo>
  </Main>
  <xsl:if test="Head/Flag='0'">
  <Policy>
    <ApplyNo><xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/></ApplyNo>
    <PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
    <PrintNo><xsl:value-of select="substring(Body/ContPrtNo,1,13)"/></PrintNo>
    <ApplyDate><xsl:value-of select="Body/Risk/PolApplyDate"/></ApplyDate>
    <TotalPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></TotalPrem>
    <InsuAmount><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></InsuAmount>
    <xsl:if test="Body/Risk/InsuYearFlag='1000Y'">
      <PayEndDate>99999999</PayEndDate>
    </xsl:if>
    <xsl:if test="Body/Risk/InsuYearFlag!='1000Y'">
      <PayEndDate><xsl:value-of select="Body/Risk/InsuEndDate"/></PayEndDate>
    </xsl:if>
    <PolEffDate><xsl:value-of select="Body/Risk/CValiDate"/></PolEffDate>
    <PolEndDate><xsl:value-of select="Body/Risk/InsuEndDate"/></PolEndDate>
  </Policy>  
  <Print>
    <PaperTypeCount>1</PaperTypeCount>
    <Paper>
      <PaperType>1</PaperType>
      <PaperTitle></PaperTitle>
      <PageCount>2</PageCount>
      <PageContent>
        <RowCount>68</RowCount>
        <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
        <xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>         </xsl:text>保险合同号：<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 </Row>
          <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row></Row>
          <Row><xsl:text>         </xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Row>
          <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>         </xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
												<xsl:text>性别：</xsl:text>
												<xsl:call-template name="tran_Sex">
			                                        <xsl:with-param name="Sex">
				                                          <xsl:value-of select="/TranData/Body/Appnt/Sex"/>
			                                        </xsl:with-param>
		                                        </xsl:call-template>
												<xsl:text>   </xsl:text>
												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
          </Row>
          <Row><xsl:text>         </xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
												<xsl:text>性别：</xsl:text>
												<xsl:call-template name="tran_Sex">
			                                        <xsl:with-param name="Sex">
				                                          <xsl:value-of select="/TranData/Body/Appnt/Sex"/>
			                                        </xsl:with-param>
		                                        </xsl:call-template>
												<xsl:text>   </xsl:text>
												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
		  </Row>
		  <xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
		  <xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
		  <xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
		  <xsl:for-each select="/TranData/Body/Bnf">
		  <Row><xsl:text>         </xsl:text><xsl:text></xsl:text>受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>
		  <xsl:text>性别:</xsl:text><xsl:text> </xsl:text>
		  <xsl:call-template name="tran_Sex">
			  <xsl:with-param name="Sex">
				<xsl:value-of select="/TranData/Body/Appnt/Sex"/>
			  </xsl:with-param>
		  </xsl:call-template><xsl:text>       </xsl:text>
		  <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>
		  <xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
		  </Row>
		  </xsl:for-each>
          <xsl:choose>
			<xsl:when test="$num = 0"><Row><xsl:text>         </xsl:text><xsl:text></xsl:text>受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
			<xsl:text>性别:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
			<xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
			<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			</Row></xsl:when>
			</xsl:choose>	
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>         </xsl:text>险种名称                          保险期间    交费年期    交费方式  （基本）保额/份数   保险费</Row>
          <xsl:for-each select="/TranData/Body/Risk">
          <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
		  <xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		  <xsl:variable name="Mult" select="Mult"/>
		  <Row>
		  	<xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
		    <xsl:choose>
			    <xsl:when test="InsuYearFlag = 'A'"><xsl:text>至</xsl:text>
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
			  	<xsl:text>趸交       </xsl:text>
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
			  <xsl:call-template name="tran_PayIntv">
			    <xsl:with-param name="PayIntv">
				  <xsl:value-of select="PayIntv"/>
			    </xsl:with-param>
		     </xsl:call-template>
		                     <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>份</xsl:text>
			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元
		  </Row>
          </xsl:for-each>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>         </xsl:text>保险费合计：<xsl:value-of select="/TranData/Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>元）</Row>
          <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:choose><!-- 保驾护航产品（221201） 此处打印的是（此栏空白），不是红利领取方式 -->
			<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
			<Row><xsl:text>         </xsl:text>红利领取方式：<xsl:apply-templates select="/TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Row>
			</xsl:when>
			<xsl:otherwise>
			<Row><xsl:text>         </xsl:text>（本栏空白）</Row>
			<Row/>
			</xsl:otherwise>
		  </xsl:choose>
		  <xsl:choose>
		   <xsl:when test="$num=1 or $num=0"><Row/><Row /><Row /><Row /></xsl:when><!-- 受益人个数为0或者个数为1的情况是一样的，受益人的信息都占一行 -->
		   <xsl:when test="$num=2"><Row /><Row /><Row /></xsl:when>
		   <xsl:when test="$num=3"><Row /><Row /></xsl:when>
		   <xsl:when test="$num=4"><Row /></xsl:when>
		   <xsl:otherwise></xsl:otherwise>
		   </xsl:choose>
		  <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
		  <Row>
		  <xsl:text>         </xsl:text><xsl:text>特别约定：</xsl:text>
		    <xsl:choose>
		     <xsl:when test="$MainRisk/SpecContent = ''">
			  <xsl:text>（无）</xsl:text>
		     </xsl:when>
		     <xsl:otherwise> 
		      <xsl:value-of select="$MainRisk/SpecContent"/>
		     </xsl:otherwise>
		    </xsl:choose>
		  </Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>         </xsl:text>银行网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/></Row>
          <Row><xsl:text>         </xsl:text>银行销售人员姓名/代码：<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,40)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Row>
          <Row><xsl:text>         </xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>银保经理电话：<xsl:value-of select="/TranData/Body/AgentPhone"/></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Row>
          <Row></Row>
        </Details>
      </PageContent>
      <PageContent>
      <RowCount>43</RowCount>
      <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Amnt)" />
	  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
	  <xsl:variable name="MainRisk"  select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>     </xsl:text>                                        现金价值表                     </Row>
          <Row></Row>
          <Row><xsl:text>     </xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Row>
          <Row><xsl:text>     </xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Row>       
          <Row><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>              </xsl:text>  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值</Row>
          <Row><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:text></xsl:text>     <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
          <Row><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>     </xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现</Row>
          <Row><xsl:text>     </xsl:text>金价值额。投保后所做的各项变更可能使本表不再适用。</Row>
          <Row><xsl:text>     </xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向</Row>
          <Row><xsl:text>     </xsl:text>公司来电垂询。</Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
        </Details>
      </PageContent>
    </Paper> 
  </Print>                      
  </xsl:if>
</InsuReq>		
</xsl:template> 
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Row><xsl:text>                  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Row>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Row><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Row>
		</xsl:for-each>
		</xsl:if>
</xsl:template>
<!--性别转换-->
<xsl:template name="tran_Sex">
  <xsl:param name="Sex"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Sex = '0'">男</xsl:when><!-- 男-->
  <xsl:when test="$Sex = '1'">女</xsl:when><!-- 女 -->
  <xsl:otherwise>默认</xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!--缴费方式-->
<xsl:template name="tran_PayIntv">
  <xsl:param name="PayIntv"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayIntv = '12'">年交     </xsl:when> <!--年交-->
  <xsl:when test="$PayIntv = '1'">月交     </xsl:when><!--月交  -->
  <xsl:when test="$PayIntv = '6'">半年交    </xsl:when><!--半年交  -->
  <xsl:when test="$PayIntv = '3'">季交     </xsl:when><!--季交  -->
  <xsl:when test="$PayIntv = '0'">趸交     </xsl:when><!--趸交 -->
  <xsl:when test="$PayIntv = '-1'">不定期交  </xsl:when><!--不定期交  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
