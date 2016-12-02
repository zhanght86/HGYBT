<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
			<Transaction>
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<!--# # # # ������Ϣ # # # # -->
				<!--����������Ϣ-->
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
				<!--# # # # �ֽ��ֵ��# # # #��Ϊ���в�Ҫһ����������ֱ��������-->
				<LiCashValueCount>0</LiCashValueCount>
				<!--�ֽ��ֵ��ѭ����ѭ����ǩ��Cash_List��ÿ���ֽ��ֵ�ı�ǩ��Cash_Detail-->
			
				<!--ѭ������-->
				<!--# # # # ������������ĩ�ֽ��ֵ�� # # # #-->
				<LiBonusValueCount>0</LiBonusValueCount>
				<!--Bonus  ������������ĩ�ֽ��ֵ��ѭ��-->
				<!--ѭ����ǩ��Bonus_List��ÿ���ֽ��ֵ�ı�ǩ��Bonus_Detail-->
				<!--<Bonus_List>
					<Bonus_Detail>
						<LiBonusEnd/>
						<LiBonusCash/>
					</Bonus_Detail>
				</Bonus_List>-->
				<!--ѭ������-->
				<PbInsuSlipNo>
					<xsl:value-of select="/TranData/Body/ContNo"/>
				</PbInsuSlipNo>
				<!--������-->
				<BkTotAmt>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>
				</BkTotAmt>
				<!--�ܱ���-->
				<LiSureRate>					
				</LiSureRate>
				<!--��֤����-->
				<PbBrokId>
					<xsl:value-of select="/TranData/Body/AgentCode"/>
				</PbBrokId>
				<!--ҵ��Ա����-->
				<LiBrokName>
					<xsl:value-of select="/TranData/Body/AgentName"/>
				</LiBrokName>
				<!--ҵ��Ա����-->
				<LiBrokGroupNo>
					<xsl:value-of select="/TranData/Body/AgentGrpCode"/>
				</LiBrokGroupNo>
				<!--ҵ��Ա���-->
				<BkOthName>
					<xsl:value-of select="/TranData/Body/ComName"/>
				</BkOthName>
				<!--���չ�˾����-->
				<BkOthAddr>
					<xsl:value-of select="/TranData/Body/ComLocation"/>
				</BkOthAddr>
				<!--���չ�˾��ַ-->
				<PiCpicZipcode>
					<xsl:value-of select="/TranData/Body/ComZipCode"/>
				</PiCpicZipcode>
				<!--���չ�˾�ʱ�-->
				<PiCpicTelno>
					<xsl:value-of select="/TranData/Body/ComPhone"/>
				</PiCpicTelno>
				<!--���չ�˾�绰-->				
				
				<BkFileNum>3</BkFileNum>
				<Detail_List>
					<BkFileDesc>������һҳ</BkFileDesc>
					<BkType1>010072000001</BkType1>
					<BkVchNo>
						<xsl:value-of select="/TranData/Body/ContPrtNo"/>
					</BkVchNo>
					<BkRecNum>52</BkRecNum>
					<Detail>
							<!--һ�д�ӡ������--> 
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
					<BkDetail1><xsl:text>           </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ </BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1/>
					<BkDetail1><xsl:text>           </xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>      
					<BkDetail1><xsl:text>           </xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday),2)"/><xsl:text>���ꡡ����</xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
					</BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday),2)"/><xsl:text>���ꡡ����</xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
					</BkDetail1>
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					
					
				<xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>���/�˲е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 <BkDetail1><xsl:text>           </xsl:text>�˲еڶ�˳��������Ϊ�������˱���</BkDetail1>
				 </xsl:when>
				 <xsl:otherwise>
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>���/ȫ�е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 <BkDetail1><xsl:text>           </xsl:text>ȫ�еڶ�˳��������Ϊ�������˱���</BkDetail1>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(/TranData/Body/Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <BkDetail1><xsl:text>           </xsl:text>��ʵڶ�˳�������ˣ�����</BkDetail1>
				 <BkDetail1/>
				 <BkDetail1/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and TranData/Body/Bnf[SeqNo='4']">
				 <BkDetail1>
				    <xsl:text>           </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>���������</xsl:text><xsl:value-of select="/TranData/Body/Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text>
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
				    <xsl:text>������ </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
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
				    <xsl:text>������ </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </BkDetail1>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <BkDetail1>
				    <xsl:text>������ </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
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
					<BkDetail1><xsl:text></xsl:text>��������                      �����ڼ�    �����ڼ�   ���ѷ�ʽ  (����)���ս��/����  (����)���շ�</BkDetail1>
					<xsl:for-each select="/TranData/Body/Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<BkDetail1><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 32)"/>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = 'A'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:when>
																							<xsl:when test="InsuYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:when test="InsuYearFlag = 'M'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text>     </xsl:text>
																			<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>   ����     </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'M'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'D'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����    </xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:apply-templates select="PayIntv"/>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</BkDetail1>
					</xsl:for-each>
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1><xsl:text>�����ڼ���ֹʱ�䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(/TranData/Body/Risk/CValiDate)"/><xsl:text>��ʱ����</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(/TranData/Body/Risk/InsuEndDate)"/><xsl:text>��ʮ��ʱֹ</xsl:text></BkDetail1>  
					<BkDetail1><xsl:text></xsl:text>���շѺϼƣ�<xsl:value-of select="/TranData/Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>Ԫ��</BkDetail1>
					<BkDetail1><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1 />
					<BkDetail1 />
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1/>
					<BkDetail1 />
					<BkDetail1><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</BkDetail1>
					<BkDetail1><xsl:text></xsl:text><xsl:text>�ر�Լ����</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>���ޣ�</xsl:text>
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
					<BkDetail1><xsl:text>           </xsl:text>�����������ƣ�<xsl:value-of select="TranData/Body/AgentComName"/></BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>����������Ա����/���룺<xsl:value-of select="TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/SaleStaff,40)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></BkDetail1>
					<BkDetail1><xsl:text>           </xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 54)"/>��������绰��<xsl:value-of select="TranData/Body/AgentPhone"/></BkDetail1>
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
					<BkDetail1><xsl:text>����������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��', 66,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></BkDetail1>
					<BkDetail1 /> 
				    </Detail>
				</Detail_List>

				<Detail_List>
				    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<BkFileDesc>�����ڶ�ҳ</BkFileDesc>
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
					       <BkDetail1><xsl:text>           </xsl:text>���������������������������������������պ�ͬ�ʹ��ִ</BkDetail1>
					       <BkDetail1/>
					       <BkDetail1/>
					       <BkDetail1><xsl:text>           </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></BkDetail1>
					       <BkDetail1><xsl:text>           </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>������Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ���</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>���������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���ȷ�����˽Ⲣ�Ͽ�</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>���պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text></xsl:text></BkDetail1>
						   <BkDetail1/>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></BkDetail1>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>��ִ���ء�</xsl:text></BkDetail1>
						   <BkDetail1/>
						   <BkDetail1><xsl:text>           </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></BkDetail1>
						   
				  </Detail>	 
				</Detail_List>					
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
	
	
	
<!-- ת�����ִ��� -->
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 231204">0001</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$RiskCode = 211902">0002</xsl:if><!-- �к���Ӯ����������˺�����A�� -->
		<xsl:if test="$RiskCode = 221201">0003</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$RiskCode = 231302">0004</xsl:if><!-- �к�������������գ��ֺ��ͣ� -->
		<xsl:if test="$RiskCode = 221203">0005</xsl:if><!-- �к���������ȫ���� -->
		<xsl:if test="$RiskCode = 225501">0006</xsl:if><!-- �к������������ش󼲲����� -->
	</xsl:template>
	
<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
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

<!-- ������ȡ��ʽ��ת�� -->
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">�ۻ���Ϣ</xsl:when>
	<xsl:when test=".=2">��ȡ�ֽ�</xsl:when>
	<xsl:when test=".=3">�ֽ�����</xsl:when>
	<xsl:when test=".=5">�����</xsl:when>
	<xsl:when test=".=''">�ۻ���Ϣ  </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ɷ�Ƶ��2 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  
<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">��</xsl:when>	<!-- �� -->
	<xsl:when test=".=1">Ů</xsl:when>	<!-- Ů -->
	<xsl:when test=".=2">����</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

</xsl:stylesheet>
