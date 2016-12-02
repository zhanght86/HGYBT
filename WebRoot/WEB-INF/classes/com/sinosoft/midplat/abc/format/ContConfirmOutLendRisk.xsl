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
				    <Value/>
				 </Prnt>
				<Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt>
				<Prnt>
				    <Value/>
				 </Prnt> 
				 <Prnt>  
				    <value><xsl:text>�� ������������������������������������������������������������������������    </xsl:text>���ҵ�λ�������/Ԫ </value>
				 </Prnt>
				 <Prnt> 
				    <Value>�� ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value/>
				 </Prnt> 
				 <Prnt> 
					<Value><xsl:text>�� </xsl:text>������ͬ��:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 51)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)"/> ��ʱ</Value>
				 </Prnt>
			   <Prnt>
				    <Value>�� ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				<Prnt>
					<Value>
						<xsl:text>�� </xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Appnt/IDNo"/>
					</Value>
				</Prnt>
				<Prnt>
					<Value>
						<xsl:text>�� </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Insured/IDNo"/>
					</Value>
				</Prnt>
				<xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
					 <Prnt>
					    <Value><xsl:text>�� </xsl:text><xsl:text>���/�˲е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text></Value>
					 </Prnt>
					 <Prnt>
					    <Value><xsl:text>�� </xsl:text>�˲еڶ�˳��������Ϊ�������˱��ˡ�</Value>
					 </Prnt>
				 </xsl:when>
				 <xsl:otherwise>
					 <Prnt>
					    <Value><xsl:text>�� </xsl:text><xsl:text>���/ȫ�е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text></Value>
					 </Prnt>
					 <Prnt>
					    <Value><xsl:text>�� </xsl:text>ȫ�еڶ�˳��������Ϊ�������˱��ˡ�</Value>
					 </Prnt>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <Prnt><Value><xsl:text>�� </xsl:text>��ʵڶ�˳�������ˣ�����</Value></Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and Bnf[SeqNo='4']">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>����ݶ</xsl:text><xsl:value-of select="Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <Prnt><Value/></Prnt>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
				 </Prnt>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>����ݶ</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text></Value>
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
				<Prnt>
				    <Value>�� ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>��������                         �����ڼ�   �����ڼ�   ���ѷ�ʽ ������������/����  (����)���շ�</Value>
				 </Prnt>
				 <Prnt>
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
				</Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value><xsl:text>�� �����ڼ���ֹʱ�䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/CValiDate)"/><xsl:text>��ʱ����</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/InsuEndDate)"/><xsl:text>��ʮ��ʱֹ</xsl:text></Value></Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>���շѺϼƣ�<xsl:value-of select="PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>Ԫ��</Value>
				 </Prnt>
				 <Prnt>
				    <Value>�� ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text><xsl:text>�ر�Լ����</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>���ޣ�</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
				    </Value>
				 </Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt><Value><xsl:text>�� ������������������������������������������������������������������������</xsl:text>�к����ٱ������޹�˾</Value></Prnt>
				<Prnt><Value><xsl:text>�� ��������������������������������������������������������������������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></Value></Prnt>
				<Prnt><Value/></Prnt>
				<Prnt>
				    <Value>�� ------------------------------------------------------------------------------------------------</Value>
				 </Prnt>
				 <Prnt><Value/></Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>��˾��ַ��<xsl:value-of select="ComLocation"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>ȫ���ͻ��������ߣ�<xsl:value-of select="ComPhone"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>Ӫҵ���㣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom,52)"/> �����Ŷӣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentComName,5)"/></Value>
				 </Prnt>
				 <Prnt>
				    <Value><xsl:text>�� </xsl:text>������ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('',30)"/>�ͻ�����<xsl:value-of select="AgentName"/></Value>
				 </Prnt>
				 </Prnts>
				
			<Messages>
				<Count>0</Count>
				 <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				 <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				  <Message><Value/></Message>
				 <Message><Value/></Message>
				  <Message>
				    <Value><xsl:text>�� ��</xsl:text>                               ����ҳ�հף�                     </Value>
				 </Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message>
				 <Message><Value/></Message> 
				 <Message><Value/></Message>
				 <Message><Value/></Message>	
				 <Message><Value/></Message>
				 <Message><Value/></Message>       
				 <Message><Value><xsl:text>�� ��������������  </xsl:text><xsl:value-of select="ContNo"/></Value></Message>
				 <Message><Value><xsl:text>�� ������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18,$Falseflag)"/><xsl:text>                  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18,$Falseflag)"/><xsl:text>              </xsl:text><xsl:value-of select="AgentCode"/></Value></Message>
				 <Message><Value><xsl:text>�� ��                                                     </xsl:text><xsl:value-of select="ContNo"/></Value></Message>
		</Messages>
		<!-- ������׳ɹ����ŷ�������Ľ�� -->
		</xsl:if>
	</xsl:template>
	
	
	 <!-- ѭ��ȡ�ֽ��ֵ��Ϣ �˿�����û�� -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (11)]">
				<Message><Value/></Message>
		</xsl:for-each>
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
