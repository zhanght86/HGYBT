<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
		<Ret>
			<!-- �������ݰ� -->
			<RetData>
				<Flag>
					<xsl:value-of select="Head/Flag"/>
				</Flag>
				<Mesg>
					<xsl:value-of select="Head/Desc"/>
				</Mesg>
			</RetData>
			<xsl:apply-templates select="Body"/>
		</Ret>
	</xsl:template>
	<!-- ������׳ɹ����ŷ�������Ľ�� -->
	<xsl:template name="Base" match="Body">
		<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>
		<xsl:variable name="SumPremYuan" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:if test="//Flag='0'">
			<!--������Ϣ-->
			<Base>
				<!-- ������ -->
				<ContNo>
					<xsl:value-of select="ContNo"/>
				</ContNo>
				<!-- Ͷ����� -->
				<ProposalContNo>
					<xsl:value-of select="ProposalPrtNo"/>
				</ProposalContNo>
				<!-- ǩ������ -->
				<SignDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/SignDate"/>
				</SignDate>
				<!-- ������Ч�� -->
				<ContBgnDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/CValiDate"/>
				</ContBgnDate>
				<!-- ������ֹ�� -->
				<!-- ��ȷ�� -->
				<ContEndDate>
					<xsl:value-of select="Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</ContEndDate>
				<!-- �ɷ����� -->
				<!-- ��ȷ�� -->
				<ExpDate/>
				<!-- ҵ��Ա���� -->
				<AgentCode>
					<xsl:value-of select="AgentCode"/>
				</AgentCode>
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
				<BankAccName>
					<xsl:value-of select="AccName"/>
				</BankAccName>
				<!-- �ܱ��� -->
				<Prem>
					<xsl:value-of select="$SumPremYuan"/>
				</Prem>
			</Base>
			<!-- �����б� -->
			<Risks>
				<Count>
					<xsl:value-of select="count(Risk)"/>
				</Count>
				<xsl:for-each select="Risk">
					<!-- ���� -->
					<Risk>
						<Name>
							<xsl:value-of select="RiskName"/>
						</Name>
						<Mult>
							<xsl:value-of select="Mult"/>
						</Mult>
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						</Prem>
						<PayEndYear>
							<xsl:value-of select="PayEndYear"/>
						</PayEndYear>
						<PayIntv>
							<xsl:call-template name="TempPayIntv">
								<xsl:with-param name="PayIntv"> 
									<xsl:value-of select="PayIntv"/>
								</xsl:with-param>   
							</xsl:call-template> 
						</PayIntv>
					</Risk>
				</xsl:for-each>
			</Risks>
			<!-- ���ִ�ӡ�б� -->
			<Prnts>
				<!-- ���ִ�ӡ��Ϣ -->
				<Count>0</Count>
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
				
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���պ�ͬ���룺</xsl:text>
						<xsl:value-of select="ContNo"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>Ͷ���ˣ�  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 12)"/>
						<xsl:text>�Ա�</xsl:text>
						<xsl:apply-templates select="Appnt/Sex"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���գ�', 8 ,$Falseflag)"/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Appnt/Birthday), 18)"/>
						<xsl:text>֤�����룺</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/IDNo , 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>�������ˣ�</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 12)"/>
						<xsl:text>�Ա�</xsl:text>
						<xsl:apply-templates select="Insured/Sex"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���գ�', 8 ,$Falseflag)"/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Insured/Birthday), 18)"/>
						<xsl:text>֤�����룺</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/IDNo , 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���������ˣ�</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 12)"/>
						
						<xsl:if test="count(Bnf) = 0">
						<xsl:text>��������ˣ�</xsl:text>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�����̳��� 100%', 60)"/>
						</xsl:if>
						<xsl:if test="count(Bnf) = 1">
						<xsl:text>��������ˣ�</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1 ">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/>
								</xsl:if> 
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 2  and sum(Bnf/Lot) != 100">
						<xsl:text>��������ˣ�(˳λ)</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/><xsl:text>;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 2  and sum(Bnf/Lot) = 100">
						<xsl:text>��������ˣ�</xsl:text>
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1">
									<xsl:text/>
									<xsl:value-of select="Name"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot)"/><xsl:text>;</xsl:text>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="count(Bnf) = 3">
							<xsl:for-each select="Bnf">
								<xsl:variable name="Type" select="Type"/>
								<xsl:if test=" $Type = 1 ">
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 10)"/>
									<xsl:text/>
									<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddBnfLot(Lot), 10)"/>
								</xsl:if>
							</xsl:for-each>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>Ͷ������ϵ��ַ��  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Address, 40)"/>
						<xsl:text>�������룺</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/ZipCode, 28)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>Ͷ������ϵ�绰��  </xsl:text>
						<xsl:if test="Appnt/Mobile != ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Mobile, 78)"/>
						</xsl:if>
						<xsl:if test="Appnt/Mobile = ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Phone, 78)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>�������ƣ�  </xsl:text>
						<xsl:value-of select="Risk[RiskCode=MainRiskCode]/RiskName"/>
					</Value>
				</Prnt>
				<xsl:if test="Risk[RiskCode=MainRiskCode]/PayIntv != 0">
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���ѷ�ʽ��  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/PayIntv"/>
						<xsl:text>    �����ڼ䣺  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/PayEndYear) , 10)"/>
						<xsl:text>�����ڼ䣺  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/InsuYear) , 40)"/>
					</Value>
				</Prnt>
				</xsl:if>
				<xsl:if test="Risk[RiskCode=MainRiskCode]/PayIntv = 0">
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���ѷ�ʽ��  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/PayIntv"/>
						<xsl:text>    �����ڼ䣺  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-', 10)"/>
						<xsl:text>�����ڼ䣺  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UseAddYear(Risk[RiskCode=MainRiskCode]/InsuYear) , 40)"/>
					</Value>
				</Prnt>
				</xsl:if>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���շѣ�  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.RMB(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Risk[RiskCode=MainRiskCode]/Prem)), 30)"/>
						<xsl:text>�������ս�  </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.RMB(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Risk[RiskCode=MainRiskCode]/Amnt)), 40)"/>
					</Value>
				</Prnt>
				<xsl:if test="Risk/RiskCode = '222001' or Risk/RiskCode = '222002'">
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>������ȡ��ʽ��  �ۼ���Ϣ</xsl:text>
					</Value>
				</Prnt>
				</xsl:if>
				<xsl:if test="Risk/RiskCode = '222003' and Risk[RiskCode=MainRiskCode]/BonusGetMode = ''">
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>������ȡ��ʽ��  �ۼ���Ϣ</xsl:text>
					</Value>
				</Prnt>
				</xsl:if>
					<xsl:if test="Risk/RiskCode = '222003' and Risk[RiskCode=MainRiskCode]/BonusGetMode != ''">
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>������ȡ��ʽ��  </xsl:text>
						<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/BonusGetMode"/>
					</Value>
				</Prnt>
				</xsl:if>
	
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���Ѻϼƣ��������</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.UpperRMB(java:com.sinosoft.midplat.common.YBTDataVerification.getChnMoney(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)),java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)), 72)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���������¿հף�', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�ر�Լ����', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>��������</xsl:text>
						<xsl:if test="Risk[RiskCode=MainRiskCode]/SpecContent != ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Risk[RiskCode=MainRiskCode]/SpecContent, 88)"/>
						</xsl:if>
						<xsl:if test="Risk[RiskCode=MainRiskCode]/SpecContent = ''">
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�����հ�', 88)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>����������������������������</xsl:text>��Ҫ��������ժҪ����ֵ��λ�������Ԫ��</Value>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��Ҫ��������ժҪ', 50)"/>
						<xsl:text/>
						<xsl:if test="Risk/RiskCode = '222001' or Risk/RiskCode = '222002'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�ֽ��ֵ�����屣��(ÿǧԪ���շ�)', 46)"/>
						</xsl:if>
								<xsl:if test="Risk/RiskCode = '222003'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�ֽ��ֵ�����屣��(ÿǧԪ�꽻���շ�)', 46)"/>
						</xsl:if>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��ĩ�����', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������', 12)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������ĩ', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�ֽ��ֵ', 14)"/>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�����', 18)"/>
					</Value>
				</Prnt>
				<xsl:for-each select="Risk/CashValues/CashValue">
					<Prnt>
						<Value>
							<xsl:text>��������</xsl:text>
							<xsl:text>��������������������������������������������������</xsl:text>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddYearEnd(EndYear),14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-', 18)"/>
						</Value>
					</Prnt>
				</xsl:for-each>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>����δ����ȼ���������ͬ����������¿հס����ϱ����е��ֽ��ֵ������������������</xsl:text>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>����������������������������������������������������������档����δ����ȼ���������</xsl:text>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>��������������������������������������������������ͬ����������¿հ�</xsl:text>
					</Value>
				</Prnt>
				<xsl:for-each select="Risk/CashValues/CashNum">
					<Prnt>
						<Value>
							<xsl:text></xsl:text>
						</Value>
					</Prnt>
				</xsl:for-each>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value/>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('ʵ�ʱ�������������ر�Լ�������������עΪ׼��δ�����ı����ֽ��ֵ�����屣����򱾹�˾��', 96)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text/>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('ѯ�����µ�ȫ��ͳһ��ѯ�绰4008080080', 96)"/>
					</Value>
				</Prnt>
				
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>������룺 </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom, 14)"/>
						<xsl:text>����</xsl:text>
						<xsl:text>�����˴��룺 </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCode, 21)"/>
						<xsl:text>����</xsl:text>
						<xsl:text>�����ˣ� </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 21)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>��������</xsl:text>
						<xsl:text>���չ�˾�����ַ: </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ComLocation, 36)"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
					<xsl:text>��������</xsl:text>
						<xsl:text>���պ�ͬǩ������: </xsl:text>
						<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk[RiskCode=MainRiskCode]/SignDate)"/>
						<xsl:text>��������ǩ�������� </xsl:text>
						<xsl:value-of select="ComName"/>
					</Value>
				</Prnt>
			</Prnts>
			<!-- �ֽ��ֵ�б� -->
			<Messages>
				<Count>0</Count>
				<xsl:for-each select="Risk/CashValues/CashValue">
					<Message>
						<Value>
							<xsl:text>��������</xsl:text>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtilZR.AddYearEnd(EndYear),14)"/>
							<xsl:text/>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 82)"/>
						</Value>
					</Message>
				</xsl:for-each>
			</Messages>
		</xsl:if>
		<!-- ������׳ɹ����ŷ�������Ľ�� -->
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
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_BonusGetMode" match="BonusGetMode">
		<xsl:choose>
			<xsl:when test=".=1">�ۻ���Ϣ  </xsl:when>
			<xsl:when test=".=2">��ȡ�ֽ�  </xsl:when>
			<xsl:when test=".=3">�ֽ�����  </xsl:when>
			<xsl:when test=".=4">���� </xsl:when>
			<xsl:when test=".=5">�����  </xsl:when>
			<xsl:otherwise>�� </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_PayIntv" match="PayIntv">
		<xsl:choose>
			<xsl:when test=".=0">����  </xsl:when>
			<xsl:when test=".=1">�½�  </xsl:when>
			<xsl:when test=".=3">����  </xsl:when>
			<xsl:when test=".=6">���꽻</xsl:when>
			<xsl:when test=".=12">�꽻  </xsl:when>
			<xsl:when test=".=-1">������</xsl:when>
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
