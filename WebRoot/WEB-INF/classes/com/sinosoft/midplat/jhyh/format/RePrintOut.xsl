<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
	<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>正常</STSDESC>
		<BUSI>
		<SUBJECT>2</SUBJECT>  
		<TRANS><xsl:value-of select="Head/TranNo" /></TRANS>
		<xsl:if test="Head/Flag='0'">
			<xsl:apply-templates select="Body" />
		</xsl:if>
		<xsl:if test="Head/Flag!='0'">
			<CONTENT>   
				<REJECT_CODE>0001</REJECT_CODE>   
				<REJECT_DESC><xsl:value-of select="Head/Desc" /></REJECT_DESC>  
			</CONTENT>
		</xsl:if>
		</BUSI>	       
	</RETURN >
</xsl:template>
	    
	
	


<xsl:template name="RETURN" match="TranData/Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<xsl:variable name="RiderRisk" select="Risk[RiskCode!=MainRiskCode]" />
<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
	
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
	    <xsl:variable name ="print">
	    <xsl:text>&lt;![CDATA[※※※※※※※※※※※</xsl:text>
		<xsl:text>  </xsl:text>  保险合同号:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 56)"/><xsl:text>货币单位：人民币/元</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text> 
		<xsl:text>※※  </xsl:text>  保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 37)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" />
	    <xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※  </xsl:text>  投保人姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																							<xsl:text>  性别：</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>  年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>周岁       </xsl:text>  
																							<xsl:text>  证件号码：</xsl:text><xsl:value-of select="Appnt/IDNo"/>
		<xsl:text>※  </xsl:text><xsl:text>  被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																							<xsl:text>  性别：</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>  年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>周岁       </xsl:text>
																							<xsl:text>  证件号码：</xsl:text><xsl:value-of select="Insured/IDNo"/>
		
		<xsl:for-each select="Bnf">
		<xsl:text>※  </xsl:text><xsl:text>  受益人姓名:</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 19)"/><xsl:text>性别:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>   </xsl:text><xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 14)"/><xsl:text>受益比例:</xsl:text><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text></xsl:for-each>
      <xsl:choose><xsl:when test="$num = 0">
		<xsl:text>※  </xsl:text>  受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 18)"/>																
																							<xsl:text>  性别:</xsl:text><xsl:text> </xsl:text>--<xsl:text></xsl:text>
																							<xsl:text>  受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
																							<xsl:text>  受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
		</xsl:when></xsl:choose>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※※ </xsl:text><xsl:text>  险种名称               保险期间    交费期间   交费方式        （基本）保额/份数     保险费</xsl:text>
	 	<xsl:for-each select="Risk">
		<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
		<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:text>※   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 22)"/>

		<xsl:choose>
				<xsl:when test="/TranData/Body/Risk/RiskCode = '221301'">
					<xsl:choose>
						<xsl:when test="InsuYearFlag = 'A'">
							<xsl:text>  至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>周岁</xsl:text>
						</xsl:when>
						<xsl:when test="InsuYearFlag = 'Y'">
							<xsl:text>  至</xsl:text><xsl:value-of select=" java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>年  </xsl:text>
						</xsl:when>  
						<xsl:when test="InsuYearFlag = 'M'">
							<xsl:text>  至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>月  </xsl:text>
						</xsl:when>
						<xsl:otherwise> 
							<xsl:text>  至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>日</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise> 
					<xsl:choose>
						<xsl:when test="InsuYearFlag = 'A'">
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>年</xsl:text>
						</xsl:when>
						<xsl:when test="InsuYearFlag = 'Y'">
							<xsl:text></xsl:text><xsl:value-of select=" java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>年  </xsl:text>
						</xsl:when>  
						<xsl:when test="InsuYearFlag = 'M'">
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>月  </xsl:text>
						</xsl:when>
						<xsl:otherwise> 
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>日</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
		</xsl:choose>
		
		<xsl:text></xsl:text>
		<xsl:choose>
			<xsl:when test="PayIntv = 0">
				<xsl:text>      趸交        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'Y'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>年        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'M'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>月        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'D'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>日        </xsl:text>
			</xsl:when>  
			<xsl:otherwise> 
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>周岁    </xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="PayIntv"/>
		<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,20,$Falseflag)"/><xsl:text>元</xsl:text>
 		<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,17,$Falseflag)"/>元</xsl:for-each>
		<xsl:text>※※※※  </xsl:text>  保险费合计：<xsl:value-of select="PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/><xsl:text>元）</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:choose>
				<xsl:when test="/TranData/Body/Risk/RiskCode = '221301'">
					<xsl:text>※</xsl:text>    年金领取起始年龄：<xsl:value-of select="/TranData/Body/Risk/GetYear"/>周岁            领取期限：<xsl:value-of select="/TranData/Body/Risk/GetTerms" />年
					<xsl:text>※※  </xsl:text>
							<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
							<xsl:text>※  </xsl:text><xsl:text>  特别约定：</xsl:text>
							<xsl:choose>
									<xsl:when test="$MainRisk/SpecContent = ''">
										<xsl:text>（无）</xsl:text>
									</xsl:when>
									<xsl:otherwise> 
										<xsl:value-of select="$MainRisk/SpecContent"/>
									</xsl:otherwise>
							</xsl:choose>
				</xsl:when>
				<xsl:otherwise> 
							<xsl:text>※  </xsl:text><xsl:text>  特别约定：</xsl:text>
							<xsl:choose>
									<xsl:when test="$MainRisk/SpecContent = ''">
										<xsl:text>（无）</xsl:text>
									</xsl:when>
									<xsl:otherwise> 
										<xsl:value-of select="$MainRisk/SpecContent"/>
									</xsl:otherwise>
							</xsl:choose>
							<xsl:text>※※※※※  </xsl:text>
				</xsl:otherwise>
		</xsl:choose>
		

		<xsl:text>※※※※※※※※※※※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※   </xsl:text>  银行网点名称：<xsl:value-of select="/TranData/Body/AgentComName"/>
		<xsl:text>※   </xsl:text>  银行销售人员姓名/代码：<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,34)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/>
		<xsl:text>※   </xsl:text>  银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 50)"/>银保经理电话：<xsl:value-of select="/TranData/Body/AgentPhone"/>
		<xsl:text>※※※※※※※※※※※</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/>
		<xsl:text>※※※※※※※※※   </xsl:text><xsl:text>                                          现金价值表</xsl:text>
		<xsl:text>※ ※</xsl:text>   保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/>
		<xsl:text>※ </xsl:text>  险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/><xsl:text>货币单位：人民币/元</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※         </xsl:text><xsl:text>  保单年度末 现金价值  | 保单年度末 现金价值  | 保单年度末  现金价值</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text> </xsl:text><xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
	    <xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※</xsl:text><xsl:text>    *现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末</xsl:text>
		<xsl:text>※ </xsl:text><xsl:text>    所对应的现金价值额。投保后所做的各项变更可能使本表不再适用。</xsl:text>
		<xsl:text>※</xsl:text><xsl:text>    *对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金</xsl:text>
		<xsl:text>※</xsl:text><xsl:text>    价值，可向公司来电垂询。</xsl:text>
		<xsl:text>※※※※※※※※※※※※※※※※※</xsl:text><xsl:text>                                           保险合同送达回执</xsl:text>
		<xsl:text>※※※    </xsl:text><xsl:text>  保险合同号: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/>
       	<xsl:text>※    </xsl:text><xsl:text>  投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="/TranData/Body/AgentCode"/>
	   	<xsl:text>※    </xsl:text><xsl:text>      本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单、</xsl:text>
	   	<xsl:text>※    </xsl:text><xsl:text>  现金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、</xsl:text>
	   	<xsl:text>※    </xsl:text><xsl:text>  投保提示书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text>
	   	<xsl:text>※  </xsl:text><xsl:text></xsl:text>
		<xsl:text>※※</xsl:text><xsl:text>      投保人签名：                                    签收日期：         年     月     日</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text>
		<xsl:text>※  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>※</xsl:text><xsl:text>      保险合同号为 </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text>
		<xsl:text>※</xsl:text><xsl:text>      回执交回。</xsl:text>
		<xsl:text>※※</xsl:text><xsl:text>      银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text>
		<xsl:text>※]]&gt;</xsl:text>
		</xsl:variable>
	<PRINT><xsl:value-of disable-output-escaping="yes" select="$print"/></PRINT>
</xsl:template>


<!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:variable name="LeiShu" select="33"></xsl:variable>	
	    <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
	    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<xsl:text>※        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose>
	    </xsl:for-each>
</xsl:template>
<!-- 主险种代码 -->
<xsl:template name="idname" match="ID">
<xsl:choose>
	<xsl:when test=".=221201">中韩保驾护航两全保险A款</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=211902">中韩安赢借款人意外伤害保险 A款</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:when test=".=221301">中韩悦未来年金险</xsl:when>  	<!-- 中韩悦未来年金险-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">男</xsl:when>	<!-- 男 -->
	<xsl:when test=".=1">女</xsl:when>	<!-- 女 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- 证件类型？？？-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=6">A</xsl:when>	<!--文职干部证        -->
	<xsl:when test=".=A">B</xsl:when>	<!--士兵证、          -->
	<xsl:when test=".=5">C</xsl:when> <!--军事院校学员证    -->
	<xsl:when test=".=B">H</xsl:when> <!--回乡证            -->
	<xsl:when test=".=C">0</xsl:when> <!--外国人/临时居留证 -->
	<xsl:when test=".=0">1</xsl:when> <!--公民身份证        -->
	<xsl:when test=".=2">2</xsl:when> <!--军官证            -->
	<xsl:when test=".=4">3</xsl:when> <!--户口簿            -->
	<xsl:when test=".=1">4</xsl:when> <!--护照              -->
	<xsl:when test=".=D">5</xsl:when> <!--警官证            -->
	<xsl:when test=".=8">8</xsl:when> <!--其他（对公证件）  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 关系 -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=00">5</xsl:when> <!-- 本人    -->
	<xsl:when test=".=02">1</xsl:when> <!-- 配偶    -->
	<xsl:when test=".=01">2</xsl:when> <!-- 父母    -->
	<xsl:when test=".=03">3</xsl:when> <!-- 子女    -->
	<xsl:when test=".=04">4</xsl:when> <!-- 亲属    -->
	<xsl:when test=".=06">6</xsl:when> <!-- 其他     -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose> 
</xsl:template>

<!-- 缴费方式 -->
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=0">趸交</xsl:when>	<!-- 趸交 -->
		<xsl:when test=".=12">年交</xsl:when>	<!-- 年交 -->
		<xsl:when test=".=6">半年交 </xsl:when>	<!-- 半年交 -->
		<xsl:when test=".=3">季交</xsl:when>	<!-- 季交 -->
		<xsl:when test=".=1">月交</xsl:when>	<!-- 月交 -->
		<xsl:when test=".=-1">不定期</xsl:when>	<!-- 不定期 -->
		<xsl:otherwise>--</xsl:otherwise> 
	</xsl:choose> 
</xsl:template>
<!-- 终交年期标志的转换 -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- 缴至某确定年龄 -->
	<xsl:when test=".='M'">2</xsl:when>	<!-- 月 -->
	<xsl:when test=".='D'">3</xsl:when>	<!-- 日 -->
	<xsl:when test=".='Y'">4</xsl:when>	<!-- 年 -->
	<xsl:otherwise></xsl:otherwise>  
</xsl:choose>
</xsl:template>
<!-- 保险年龄年期标志 -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".=Y">2</xsl:when>	<!-- 年 -->
	<xsl:when test=".=A">3</xsl:when>	<!-- 年龄 -->
	<xsl:when test=".=M">4</xsl:when>	<!-- 月 -->
	<xsl:when test=".=D">5</xsl:when>	<!-- 日 -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>
</xsl:template> 


<!-- 红利领取方式的转换 -->
<xsl:template match="BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- 积累生息 -->
	<xsl:when test=".=2">2</xsl:when>	<!-- 领取现金 -->
	<xsl:when test=".=3">3</xsl:when>	<!-- 抵交保费 -->
	<xsl:when test=".=4">5</xsl:when>	<!-- 增额交清 -->
	<xsl:otherwise></xsl:otherwise>  
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
