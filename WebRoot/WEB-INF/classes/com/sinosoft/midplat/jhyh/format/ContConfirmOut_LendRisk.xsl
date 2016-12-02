<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
			<Transaction>
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<!--# # # # 险种信息 # # # # -->
				<!--主险险种信息-->
				<PbInsuType><xsl:value-of select="/TranData/Body/Risk/MainRiskCode"/></PbInsuType>
				<PiEndDate>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</PiEndDate>
				<PbFinishDate>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/PayEndDate"/>
				</PbFinishDate>
				<LiDrawstring>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/GetStartDate"/>
				</LiDrawstring>
				<!--# # # # 现金价值表# # # #因为建行不要一定传，所以直接置零先-->
				<LiCashValueCount>0</LiCashValueCount>
				<!--现金价值表循环，循环标签用Cash_List，每条现金价值的标签用Cash_Detail-->
			
				<!--循环结束-->
				<!--# # # # 红利保额保单年度末现金价值表 # # # #-->
				<LiBonusValueCount>0</LiBonusValueCount>
				<!--Bonus  红利保额保单年度末现金价值表循环-->
				<!--循环标签用Bonus_List，每条现金价值的标签用Bonus_Detail-->
				<!--<Bonus_List>
					<Bonus_Detail>
						<LiBonusEnd/>
						<LiBonusCash/>
					</Bonus_Detail>
				</Bonus_List>-->
				<!--循环结束-->
				<PbInsuSlipNo>
					<xsl:value-of select="/TranData/Body/ContNo"/>
				</PbInsuSlipNo>
				<!--保单号-->
				<BkTotAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>
				</BkTotAmt>
				<!--总保费-->
				<LiSureRate>					
				</LiSureRate>
				<!--保证利率-->
				<PbBrokId>
					<xsl:value-of select="/TranData/Body/AgentCode"/>
				</PbBrokId>
				<!--业务员代码-->
				<LiBrokName>
					<xsl:value-of select="/TranData/Body/AgentName"/>
				</LiBrokName>
				<!--业务员姓名-->
				<LiBrokGroupNo>
					<xsl:value-of select="/TranData/Body/AgentGrpCode"/>
				</LiBrokGroupNo>
				<!--业务员组号-->
				<BkOthName>
					<xsl:value-of select="/TranData/Body/ComName"/>
				</BkOthName>
				<!--保险公司名称-->
				<BkOthAddr>
					<xsl:value-of select="/TranData/Body/ComLocation"/>
				</BkOthAddr>
				<!--保险公司地址-->
				<PiCpicZipcode>
					<xsl:value-of select="/TranData/Body/ComZipCode"/>
				</PiCpicZipcode>
				<!--保险公司邮编-->
				<PiCpicTelno>
					<xsl:value-of select="/TranData/Body/ComPhone"/>
				</PiCpicTelno>
				<!--保险公司电话-->				
				
				<BkFileNum>3</BkFileNum>
				<Detail_List>
					<BkFileDesc>保单第一页</BkFileDesc>
					<BkType1>010072000001</BkType1>
					<BkVchNo>
						<xsl:value-of select="/TranData/Body/ContPrtNo"/>
					</BkVchNo>
					<BkRecNum>52</BkRecNum>
					<Detail>
							<!--一行打印的内容--> 
					<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
					<xsl:variable name="RiderRisk" select="/TranData/Body/Risk[RiskCode!=MainRiskCode]" />
					<BkDetail1/> 
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/> 
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1><xsl:text>           </xsl:text>保险合同号：<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 </BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1/>
					<BkDetail1><xsl:text>           </xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>      
					<BkDetail1><xsl:text>           </xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday),2)"/><xsl:text>周岁　　　</xsl:text>  
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
					</BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																									<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday),2)"/><xsl:text>周岁　　　</xsl:text>
																									<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
					</BkDetail1>
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					
					
				<xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>身故/伤残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='1']/Name, 48)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 <BkDetail1><xsl:text>           </xsl:text>伤残第二顺序受益人为被保险人本人</BkDetail1>
				 </xsl:when>
				 <xsl:otherwise>
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>身故/全残第一顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='1']/Name, 48)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 <BkDetail1><xsl:text>           </xsl:text>全残第二顺序受益人为被保险人本人</BkDetail1>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(/TranData/Body/Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <BkDetail1><xsl:text>           </xsl:text>身故第二顺序受益人：法定</BkDetail1>
				 <BkDetail1/>
				 <BkDetail1/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and TranData/Body/Bnf[SeqNo='4']">
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>受益比例：</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 <BkDetail1/>
				 <BkDetail1/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="/TranData/Body/Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <BkDetail1/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="/TranData/Body/Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第二顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第三顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <BkDetail1>
				    <xsl:text>　　　 </xsl:text><xsl:text>身故第四顺序受益人：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>受益比例：</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>	
					
					
					<BkDetail1 />
					
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1><xsl:text></xsl:text>险种名称                      保险期间    交费期间   交费方式  (基本)保险金额/份数  (首期)保险费</BkDetail1>
					<xsl:for-each select="/TranData/Body/Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<BkDetail1><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 32)"/>
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
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>天</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text>     </xsl:text>
																			<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>   趸交     </xsl:text>
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
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</BkDetail1>
					</xsl:for-each>
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1><xsl:text>保险期间起止时间：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(/TranData/Body/Risk/CValiDate)"/><xsl:text>零时起至</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(/TranData/Body/Risk/InsuEndDate)"/><xsl:text>二十四时止</xsl:text></BkDetail1>  
					<BkDetail1><xsl:text></xsl:text>保险费合计：<xsl:value-of select="/TranData/Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>元）</BkDetail1>
					<BkDetail1><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1 />
					<BkDetail1><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1><xsl:text></xsl:text><xsl:text>特别约定：</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>（无）</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</BkDetail1>
					<BkDetail1 />					
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>银行网点名称：<xsl:value-of select="TranData/Body/AgentComName"/></BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>银行销售人员姓名/代码：<xsl:value-of select="TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/SaleStaff,40)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 54)"/>银保经理电话：<xsl:value-of select="TranData/Body/AgentPhone"/></BkDetail1>
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1><xsl:text>　　　　　　　　</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('　', 66,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></BkDetail1>
					<BkDetail1 /> 
				    </Detail>
				</Detail_List>

				<Detail_List>
				    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<BkFileDesc>保单第二页</BkFileDesc>
					<BkType1>010072000001</BkType1>
					<BkVchNo>
						<xsl:value-of select="/TranData/Body/ContPrtNo"/>
					</BkVchNo>
					<BkRecNum>52</BkRecNum>
					<Detail>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1><xsl:text>           </xsl:text>　　　　　　　　　　　　　　　　　　保险合同送达回执</BkDetail1>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1><xsl:text>           </xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></BkDetail1>
					       <BkDetail1><xsl:text>           </xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>　　本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单、</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款，确认已了解并认可</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>保险合同的全部内容，知晓本人的权利和义务。</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text></xsl:text></BkDetail1>
						   <BkDetail1/>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>回执交回。</xsl:text></BkDetail1>
						   <BkDetail1/>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></BkDetail1>
						   
				  </Detail>	 
				</Detail_List>					
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	
	
	
<!-- 转换险种代码 -->
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 231204">0001</xsl:if><!-- 中韩智赢财富两全保险（分红型）C款 -->
		<xsl:if test="$RiskCode = 211902">0002</xsl:if><!-- 中韩安赢借款人意外伤害保险A款 -->
		<xsl:if test="$RiskCode = 221201">0003</xsl:if><!-- 中韩保驾护航两全保险A款 -->
		<xsl:if test="$RiskCode = 231302">0004</xsl:if><!-- 中韩永利年年年金保险（分红型） -->
		<xsl:if test="$RiskCode = 221203">0005</xsl:if><!-- 中韩悦无忧两全保险 -->
		<xsl:if test="$RiskCode = 225501">0006</xsl:if><!-- 中韩附加悦无忧重大疾病保险 -->
	</xsl:template>
	
<!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<BkDetail1><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></BkDetail1>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<BkDetail1><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></BkDetail1>
		</xsl:for-each>
		</xsl:if>
</xsl:template>

<!-- 红利领取方式的转换 -->
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">累积生息</xsl:when>
	<xsl:when test=".=2">领取现金</xsl:when>
	<xsl:when test=".=3">抵交保费</xsl:when>
	<xsl:when test=".=5">增额交清</xsl:when>
	<xsl:when test=".=''">累积生息  </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 缴费频次2 -->
<xsl:template  match="PayIntv">
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
