<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
	<RETURN>
		<ACKSTS>0</ACKSTS>
		<STSDESC>正常</STSDESC>
		<BUSI>
			<SUBJECT>1</SUBJECT>
			<TRANS><xsl:value-of select ="Head/TranNo"/></TRANS>
			<!-- 逐行打印 -->
			<xsl:variable name ="print">
			&lt;![CDATA[
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<xsl:variable name="MainRisk" select="Body/Risk[RiskCode=MainRiskCode]" />
			<!-- 保险单内容开始 -->
			
			※<xsl:text>　　　　</xsl:text>保险合同号：<xsl:value-of select="Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			※
			※<xsl:text>　　　　</xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Body/Risk/CValiDate)" /> 
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------      
			※<xsl:text>　　　　</xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 12)"/>
																							<xsl:text>性别：</xsl:text><xsl:apply-templates select="Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
																							<xsl:text>证件号码：</xsl:text><xsl:value-of select="Body/Appnt/IDNo"/>
			
			※<xsl:text>　　　　</xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Insured/Name, 12)"/>
																							<xsl:text>性别：</xsl:text><xsl:apply-templates select="Body/Insured/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
																							<xsl:text>证件号码：</xsl:text><xsl:value-of select="Body/Insured/IDNo"/>
			
			<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
			<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
			<xsl:variable name="num" select="count(Body/Bnf) " />
			<xsl:for-each select="Body/Bnf">
			※<xsl:text>　　　　</xsl:text><xsl:text></xsl:text>受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
			<xsl:text>性别:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
			 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
			<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
                 	
			</xsl:for-each>
			<xsl:choose>
			<xsl:when test="$num = 0">※<xsl:text>　　　　</xsl:text><xsl:text></xsl:text>受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
			<xsl:text>性别:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
			 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
			<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
                 	</xsl:when>
			</xsl:choose>		
			※
			※
			※
			※
			※
			※
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------      
			※<xsl:text>　　　　</xsl:text>险种名称                          保险期间    交费年期    交费方式  （基本）保额/份数   保险费
			<xsl:for-each select="Body/Risk">
			<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
			<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
			<xsl:variable name="Mult" select="Mult"/>
			<!-- 险种名称 -->
			※<xsl:text>　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
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
																	<xsl:apply-templates select="PayIntv"/>
																						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>份</xsl:text>
																	 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元
			</xsl:for-each>
			※
			※
			※
			※
			※
			※
			※<xsl:text>　　　　</xsl:text>保险费合计：<xsl:value-of select="Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/>元）
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			<xsl:choose><!-- 保驾护航产品（221201） 此处打印的是（此栏空白），不是红利领取方式 -->
			<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
			※<xsl:text>　　　　</xsl:text>红利领取方式：<xsl:apply-templates select="Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" />
			</xsl:when>
			<xsl:otherwise>
			※<xsl:text>　　　　</xsl:text>（本栏空白）
			※
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="$num=1 or $num=0">※※※※</xsl:when><!-- 受益人个数为0或者个数为1的情况是一样的，受益人的信息都占一行 -->
				<xsl:when test="$num=2">※※※</xsl:when>
				<xsl:when test="$num=3">※※</xsl:when>
				<xsl:when test="$num=4">※</xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			※<xsl:text>　　　　</xsl:text><xsl:text>特别约定：</xsl:text>
																			<xsl:choose>
																					<xsl:when test="$MainRisk/SpecContent = ''">
																						<xsl:text>（无）</xsl:text>
																					</xsl:when>
																					<xsl:otherwise> 
																						<xsl:value-of select="$MainRisk/SpecContent"/>
																					</xsl:otherwise>
																			</xsl:choose>
			
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			※<xsl:text>　　　　</xsl:text>银行网点名称：<xsl:value-of select="Body/AgentComName"/>
			※<xsl:text>　　　　</xsl:text>银行销售人员姓名/代码：<xsl:value-of select="Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/SaleStaff,44)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/>
			※<xsl:text>　　　　</xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/AgentName, 54)"/>银保经理电话：<xsl:value-of select="Body/AgentPhone"/>
			※<xsl:text>　　　　</xsl:text>签发机构：<xsl:value-of select="Body/ComName"/>
			※<xsl:text>　　　　</xsl:text>机构地址：<xsl:value-of select="Body/ComLocation"/>
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※<xsl:text>　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/>
			※ 
			
		<xsl:if test="Body/Risk[RiskCode=MainRiskCode]/RiskCode != '011A0100'">
			<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/>
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
			<xsl:variable name="MainRisk" select="Body/Risk[RiskCode=MainRiskCode]"/>
			※
			※
			※
			※
			※
			※<xsl:text>　　　　</xsl:text>                                     现金价值表                     
			※
			※<xsl:text>　　　　</xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/>
			※<xsl:text>　　　　</xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			※<xsl:text>　　　　　　　</xsl:text>  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值
		  ※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			<xsl:text></xsl:text>           <xsl:apply-templates select="Body/Risk/CashValues"/>
			※<xsl:text>　　　　</xsl:text>------------------------------------------------------------------------------------------------
			※<xsl:text>　　　　</xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现
		  ※<xsl:text>　　　　</xsl:text>金价值额。投保后所做的各项变更可能使本表不再适用。
		  ※<xsl:text>　　　　</xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向
		  ※<xsl:text>　　　　</xsl:text>公司来电垂询。				    
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
			※
				 
		</xsl:if>
		
		    <!-- <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" /> -->
			※
		  ※
		  ※
		  ※
		  ※
		  ※<xsl:text>　　　　</xsl:text>                                        保险合同送达回执
		  ※
		  ※
		  ※<xsl:text>　　　　</xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="Body/ContNo"/>
		  ※<xsl:text>　　　　</xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="Body/AgentCode"/>
				   <xsl:choose><!-- 保驾护航产品（221201） 没有产品说明说明书，智赢C有 -->
					<xsl:when test="Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
			※<xsl:text>　　　　</xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>），本保险合同包括保险单、</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>书和产品说明书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text>
					</xsl:when>
					<xsl:otherwise>
			※<xsl:text>　　　　</xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>），本保险合同包括保险单、现</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text>
					</xsl:otherwise>
					</xsl:choose>
			※<xsl:text>　　　　</xsl:text><xsl:text></xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>回执交回。</xsl:text>
			※<xsl:text>　　　　</xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text>
		   	
			<text>※]]&gt;</text>
			</xsl:variable>
			<!-- 逐行打印结束 -->
			<PRINT>
				<xsl:value-of disable-output-escaping="yes" select="string($print)" />
			</PRINT>
		</BUSI>
	</RETURN>
</xsl:template>

<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
	<xsl:choose>
		<xsl:when test=".=0">男</xsl:when><!-- 男 -->
		<xsl:when test=".=1">女</xsl:when><!-- 女 -->
		<xsl:when test=".=2">其他</xsl:when><!-- 其他 -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 缴费频次/保费缴纳方式 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">年交     </xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">月交      </xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">半年交    </xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">季交      </xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">趸交     </xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">不定期交  </xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
			<xsl:for-each select="CashValue[EndYear &lt; (26)]">
				<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<text>※<xsl:text>                  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></text>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
			    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
			    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<text>※<xsl:text>                          </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></text>
			</xsl:for-each>
		</xsl:if>
</xsl:template>

</xsl:stylesheet>