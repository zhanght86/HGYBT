<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
		<ABCB2I>
			<xsl:apply-templates select="Body"/>
		</ABCB2I>
	</xsl:template>
	<!-- ������׳ɹ����ŷ�������Ľ�� -->
	<xsl:template name="Base" match="Body">
		<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>
		<xsl:variable name="SumPremYuan" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:if test="//Flag='0'">
				<!--������Ϣ-->
			<App>
			   <Ret>
				<!-- ������ -->
				<PolicyNo>
					<xsl:value-of select="ContNo"/>
				</PolicyNo>
				<!-- Ͷ����� -->
				<VchNo>
					<xsl:value-of select="ContPrtNo"/>
				</VchNo>
				<!-- ǩ������ -->
				<AcceptDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/SignDate"/>
				</AcceptDate>
				<!-- ������Ч�� -->
				<ValidDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/CValiDate"/>
				</ValidDate>
				<!-- ������ֹ�� -->
				<!-- ��ȷ�� -->
				<PolicyDuedate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</PolicyDuedate>
				<!-- �ɷ����� -->
				<!-- ��ȷ�� -->
				<DueDate/>
				<!-- ҵ��Ա���� -->
				<UserId>
					<xsl:value-of select="AgentCode"/>
				</UserId>
				<!-- ���չ�˾�������� -->
				<ComPhone>
					<xsl:value-of select="ComPhone"/>
				</ComPhone>
				<!-- �������� -->
				<RiskName>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/RiskName"/>
				</RiskName>
				<!-- �˻����� -->
				<!-- ��ȷ�� -->
				<PayAccount>
					<xsl:value-of select="AccName"/>
				</PayAccount>
				<!-- �ܱ��� -->
				<Prem>
					<xsl:value-of select="$SumPremYuan"/>
				</Prem>
			<!-- �������� -->
			<Risks>
				<xsl:for-each select="Risk[RiskCode=MainRiskCode]">
					<!-- ���� -->
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
			<!--  ������   ��һ�������մ���-->
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
			
			<!-- ���ִ�ӡ�б� -->
			<Prnts>
				<!-- ���ִ�ӡ��Ϣ -->
				<Count>0</Count>
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
				<Prnt/> 
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/> 
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt><xsl:text>�� </xsl:text>���պ�ͬ��:<xsl:value-of select="/TranData/Body/ContNo"/>�����ҵ�λ�������/Ԫ</Prnt>
				    <Prnt> �� ------------------------------------------------------------------------------------------------ </Prnt>
				    <Prnt></Prnt> 
				    <Prnt> <xsl:text>�� </xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Prnt>
			        <Prnt>�� ------------------------------------------------------------------------------------------------</Prnt>
				    <Prnt><xsl:text></xsl:text>�� <xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>Ͷ�����䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Appnt/IDNo"/>
				   </Prnt>
				   <Prnt><xsl:text>�� </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>Ͷ�����䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Insured/IDNo"/>
				  </Prnt>
				 <xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>���/�˲е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt><xsl:text></xsl:text>�˲еڶ�˳��������Ϊ�������˱��ˡ�</Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>���/ȫ�е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt><xsl:text></xsl:text>ȫ�еڶ�˳��������Ϊ�������˱��ˡ�</Prnt>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <Prnt><xsl:text></xsl:text>��ʵڶ�˳�������ˣ�����</Prnt>
				 <Prnt/>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and Bnf[SeqNo='4']">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 <Prnt/>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <Prnt/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <Prnt>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>	
					<Prnt />
					<Prnt />
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text>��������                          �����ڼ�    �����ڼ�    ���ѷ�ʽ  �����������ս��/����  (����)���շ�</Prnt>
					<xsl:for-each select="Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<Value>
					<xsl:text>�� </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 32)"/>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskDay, 5,$Falseflag)"/><xsl:text>��</xsl:text>
																			<xsl:text>      </xsl:text>
																			<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>����        </xsl:text>
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
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,12,$Falseflag)"/>Ԫ</Value>
					</xsl:for-each>
					<Prnt />
					<Prnt />
					<Prnt><xsl:text>�����ڼ���ֹʱ�䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/CValiDate)"/><xsl:text>��ʱ����</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/InsuEndDate)"/><xsl:text>��ʮ��ʱֹ</xsl:text></Prnt>  
					<Prnt><xsl:text></xsl:text>���շѺϼƣ�<xsl:value-of select="PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>Ԫ��</Prnt>
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt />
					<Prnt />
					<Prnt/>
					<Prnt/>
					<Prnt/>
					<Prnt />
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text><xsl:text>�ر�Լ����</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>���ޣ�</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</Prnt>
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt />
					<Prnt/>
					<Prnt><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</Prnt>
					<Prnt><xsl:text></xsl:text>�����������ƣ�<xsl:value-of select="AgentComName"/></Prnt>
					<Prnt><xsl:text></xsl:text>����������Ա����/���룺<xsl:value-of select="SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(SaleStaff,42)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></Prnt>
					<Prnt><xsl:text></xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 54)"/>��������绰��<xsl:value-of select="AgentPhone"/></Prnt>
					<Prnt/>
					<Prnt />
					<Prnt/>
					<Prnt/>
					<Prnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 79,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></Prnt>
					<Prnt />
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
				  <Prnt></Prnt>
				  <Prnt><xsl:text>�� ��</xsl:text>                               ����ҳ�հף�                 </Prnt>    
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
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt>
				 <Prnt></Prnt> 
				 <Prnt></Prnt>
				 <Prnt></Prnt>	
				 <Prnt></Prnt>
				 <Prnt></Prnt>       
				 <Prnt><xsl:text>�� ��������������  </xsl:text><xsl:value-of select="ContNo"/></Prnt>
				 <Prnt><xsl:text>�� ������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18,$Falseflag)"/><xsl:text>                  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18,$Falseflag)"/><xsl:text>              </xsl:text><xsl:value-of select="AgentCode"/></Prnt>
				 <Prnt><xsl:text>�� ��                                                     </xsl:text><xsl:value-of select="ContNo"/></Prnt>
		</Messages>
		<TbdPrnts>
		                   <Count>22</Count>
		                   <Prnt1/>
					       <Prnt2/>
					       <Prnt3/>
					       <Prnt4/>
					       <Prnt5/>
					       <Prnt6><xsl:text>           </xsl:text>�����������������������������������պ�ͬ�ʹ��ִ</Prnt6>
					       <Prnt7/>
					       <Prnt8/>
					       <Prnt9><xsl:text>           </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></Prnt9>
					       <Prnt10><xsl:text>           </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Prnt10>
						   <Prnt11><xsl:text>           </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></Prnt11>
						   <Prnt12><xsl:text>           </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Prnt12>
						   <Prnt13><xsl:text>           </xsl:text><xsl:text>��Ͳ�Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Prnt13>
						   <Prnt14><xsl:text>           </xsl:text><xsl:text></xsl:text></Prnt14>
						   <Prnt15/>
						   <Prnt16><xsl:text>           </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Prnt16>
						   <Prnt17><xsl:text>           </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Prnt17>
						   <Prnt18><xsl:text>           </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Prnt18>
						   <Prnt19><xsl:text>           </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Prnt19>
						   <Prnt20><xsl:text>           </xsl:text><xsl:text>��ִ���ء�</xsl:text></Prnt20>
						   <Prnt21/>
						   <Prnt22><xsl:text>           </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Prnt22>
		</TbdPrnts>
		<!-- ������׳ɹ����ŷ�������Ľ�� -->
				</Ret>
		</App>
		</xsl:if>

	</xsl:template>
	
	
	<!--֤������  -->
	<xsl:template name="tran_IDType" match="IDType">
		<xsl:choose>
			<xsl:when test=".=0">���֤    </xsl:when>
			<!-- ���֤ -->
			<xsl:when test=".=1">����      </xsl:when>
			<!-- ���� -->
			<xsl:when test=".=2">����֤    </xsl:when>
			<!-- ����֤ -->
			<xsl:when test=".=3">����      </xsl:when>
			<xsl:when test=".=4">����֤    </xsl:when>
			<xsl:when test=".=5">�۰�ͨ��֤</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- �Ա� -->
	<xsl:template name="tran_sex" match="Sex">
		<xsl:choose>
			<xsl:when test=".=0">��  </xsl:when>
			<!-- �� -->
			<xsl:when test=".=1">Ů  </xsl:when>
			<!-- Ů -->
			<xsl:when test=".=2">����</xsl:when>
			<!-- ���� -->
			<xsl:otherwise>--  </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_PayIntv" match="PayIntv">
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
	<xsl:template match="InsuYearFlag">
		<xsl:choose>
			<xsl:when test=".= 'Y'">��</xsl:when>
			<xsl:when test=".= 'A'">��</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="InsuYearFlag"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	 <!-- ���ؽɷѷ�ʽ -->
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
