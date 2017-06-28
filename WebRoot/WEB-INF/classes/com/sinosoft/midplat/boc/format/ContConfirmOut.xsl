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
    <ApplyNo>
      <xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/>
    </ApplyNo>
    <PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
    <PrintNo>
      <xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
    </PrintNo>
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
      <PageCount>3</PageCount>
      <PageContent>
        <RowCount></RowCount>
        <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
        <xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text></Row>
          <Row><xsl:text>　</xsl:text>保险合同号：<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 </Row>
          <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row></Row>
          <Row><xsl:text>　</xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Row>
          <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>　</xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 18)"/>
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
          <Row><xsl:text>　</xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 18)"/>
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
		  	<xsl:if test="Type='0'"><!-- 生存受益人 -->
			  	<Row><xsl:text>　</xsl:text><xsl:text></xsl:text>生存受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 14)"/>
			  	<xsl:text>性别:</xsl:text><xsl:text> </xsl:text>
			  	<xsl:call-template name="tran_Sex">
				  	<xsl:with-param name="Sex">
						<xsl:value-of select="/TranData/Body/Appnt/Sex"/>
				  	</xsl:with-param>
			  	</xsl:call-template><xsl:text>       </xsl:text>
			  	<xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>
			  	<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			  	</Row>
			  </xsl:if>
			  <xsl:if test="Type='1'"><!-- 身故受益人 -->
		  		  <Row><xsl:text>　</xsl:text><xsl:text></xsl:text>身故受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 14)"/>
			  	  <xsl:text>性别:</xsl:text><xsl:text> </xsl:text>
			  	  <xsl:call-template name="tran_Sex">
				  	<xsl:with-param name="Sex">
						<xsl:value-of select="/TranData/Body/Appnt/Sex"/>
				  	</xsl:with-param>
			  	  </xsl:call-template><xsl:text>       </xsl:text>
			  	  <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>
			  	  <xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			  	</Row>
			  </xsl:if>
		  </xsl:for-each>
          <xsl:choose>
			<xsl:when test="$num = 0"><Row><xsl:text>　</xsl:text><xsl:text></xsl:text>受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
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
          <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:variable name="MainRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
          <xsl:choose>
			<!-- 华贵多彩盛世养老年金保险 -->
			<xsl:when test="$MainRiskCode='012E0100'">
				<Prnt><xsl:text>　</xsl:text>险种名称                          基本保险金额    份数    保险期间  交费期间   每期保险费</Prnt>
				<xsl:for-each select="/TranData/Body/Risk">
				<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
				<xsl:variable name="Mult" select="Mult"/>
				<Prnt>
				<!-- 险种名称 -->
				<xsl:text>　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 35)"/>
				<!-- 保额 -->
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,10,$Falseflag)"/><xsl:text></xsl:text>
				<xsl:choose>
					<xsl:when test="string-length($Mult)=2">
						<!-- 份数-->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,8,$Falseflag)"/><xsl:text></xsl:text>
						<!-- 保险期间 -->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('终身', 11,$Falseflag)"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- 份数-->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,9,$Falseflag)"/><xsl:text></xsl:text>
						<!-- 保险期间 -->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('终身', 10,$Falseflag)"/>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:text>     </xsl:text>
				<!--交费期间  -->
				<xsl:choose>
					<xsl:when test="PayIntv = 0">
						<xsl:text> 趸交     </xsl:text>
					</xsl:when>
					<xsl:when test="PayEndYearFlag = 'Y'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 3,$Falseflag)"/><xsl:text>年      </xsl:text>
					</xsl:when>
				</xsl:choose>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,8,$Falseflag)"/></Prnt>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
	          	<Row><xsl:text>　</xsl:text>险种名称                          保险期间    交费年期    交费方式  （基本）保额/份数   保险费</Row>
		        <xsl:for-each select="/TranData/Body/Risk">
		        <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
				<Row>
				<xsl:text>　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
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
				 <xsl:call-template name="tran_PayIntv">
				    <xsl:with-param name="PayIntv">
						<xsl:value-of select="PayIntv"/>
					</xsl:with-param>
				 </xsl:call-template>
				 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>元</xsl:text>
				 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元
				 </Row>
		         </xsl:for-each>
	         </xsl:otherwise>
          </xsl:choose>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>　</xsl:text>保险费合计：<xsl:value-of select="/TranData/Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>元）</Row>
          <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:choose>
			<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
			<Row><xsl:text>　</xsl:text>红利领取方式：<xsl:apply-templates select="/TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Row>
			</xsl:when>
			<xsl:otherwise>
			<Row><xsl:text>　</xsl:text>（本栏空白）</Row>
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
		  <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
		  <Row>
		  <xsl:text>　</xsl:text><xsl:text>特别约定：</xsl:text>
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
		  <Row><xsl:text>　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>　</xsl:text>银行网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/></Row>
          <Row><xsl:text>　</xsl:text>银行销售人员姓名/代码：<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,44)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Row>
          <Row><xsl:text>　</xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>银保经理电话：<xsl:value-of select="/TranData/Body/AgentPhone"/></Row>
          <Row><xsl:text>　</xsl:text>签发机构：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComName, 54)"/></Row>
          <Row><xsl:text>　</xsl:text>机构地址：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComLocation, 54)"/></Row>
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
          <Row><xsl:text>　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Row>
          <Row></Row>
        </Details>
      </PageContent>
      <PageContent>
      <RowCount></RowCount>
      <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Amnt)" />
	  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
	  <xsl:variable name="MainRisk"  select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>　　　</xsl:text>                                     现金价值表                     </Row>
          <Row></Row>
          <Row><xsl:text>　　　</xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Row>
          <Row><xsl:text>　　　</xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Row>       
          <Row><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>　　　　　　</xsl:text>  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值</Row>
          <Row><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
          <Row><xsl:text>　　　</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>　　　</xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现</Row>
          <Row><xsl:text>　　　</xsl:text>金价值额。投保后所做的各项变更可能使本表不再适用。</Row>
          <Row><xsl:text>　　　</xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向</Row>
          <Row><xsl:text>　　　</xsl:text>公司来电垂询。</Row>
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
      <PageContent>
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<RowCount></RowCount>
		<Details>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row><xsl:text>     </xsl:text>                                        保险合同送达回执</Row>
		  <Row/>
		  <Row/>
		  <Row><xsl:text>     </xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="Body/ContNo"/></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>），本保险合同包括保险单、现</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>回执交回。</xsl:text></Row>
		  <Row/>
		  <Row><xsl:text>     </xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></Row>
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
				<Row><xsl:text>                </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Row>
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
  <xsl:when test="$PayIntv = '01'">趸交</xsl:when><!--趸交 -->
  <xsl:when test="$PayIntv = '02'">年交</xsl:when> <!--年交-->
  <xsl:when test="$PayIntv = '03'">半年交</xsl:when><!--半年交  -->
  <xsl:when test="$PayIntv = '04'">季交</xsl:when><!--季交  -->
  <xsl:when test="$PayIntv = '05'">月交</xsl:when><!--月交  -->
  <xsl:when test="$PayIntv = '06'">不定期交</xsl:when><!--不定期交  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
