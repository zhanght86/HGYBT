<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	 
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse> 
	<TransRefGUID></TransRefGUID>
 	 <TransType>1013</TransType> 
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		
		<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse> 
	
</TXLife>
</xsl:template>  
 
 
<xsl:template name="OLifE" match="Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<xsl:variable name="RiderRisk" select="Risk[RiskCode!=MainRiskCode]" />
<OLifE>   
	<Holding id="cont">
		<CurrencyTypeCode>001</CurrencyTypeCode><!-- ? -->
		<!-- ������Ϣ --> 
		<Policy>    
			<!-- ������ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--���ڱ���-->
			<PaymentAmt><xsl:value-of select="Prem"/></PaymentAmt> <!-- ���ڱ��շѺϼƣ�����PaymentAmtת��Ϊ���ֽ�� ��RMBPaymentAmtԪ�� -->
			<Life> 
				<CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				<xsl:apply-templates select="Risk" />
			</Life>   
			<!--������Ϣ-->
			<ApplicationInfo>
				<!--Ͷ�����-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate>
			</ApplicationInfo>
			<OLifEExtension>  
				<!-- �ر�Լ�� --> 
				<SpecialClause><xsl:value-of select="$MainRisk/SpecContent"/>��</SpecialClause>
				<!-- �ɱ���ӡˢ�� -->
				<OriginalPolicyFormNumber/>
				<!-- ���չ�˾����绰 -->
				<InsurerDialNumber><xsl:value-of select="ComPhone"/></InsurerDialNumber>
				<!-- �����ձ�����Ϣ -->
				<ContractNo/>
				<!-- �����ͬ�� -->
				<LoanAccountNo/>
				<!-- �����Ʒ���� -->
				<LoanProductCode/>
				<!-- ����� -->
				<LoanAmount/>
				<!-- ���ս�� -->
				<FaceAmount/>
				<!-- ������ʼ���� -->
				<LoanStartDate/>
				<!-- ��������� -->
				<LoanEndDate/>
				<!-- ���պ�ͬ��Ч���� -->
				<ContractEffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)"/></ContractEffDate>
				<!-- ���պ�ͬ�������� -->
				<ContractEndDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/InsuEndDate)"/></ContractEndDate>
				<!-- �������� -->
				<CovType/>
				<!-- �������� -->
				<CovArea/>
				<!-- �������� -->
				<StartDate/>
				<!-- ����ֹ�� -->
				<EndDate/>
				<!-- �ܱ��� -->
				<GrossPremAmt><xsl:value-of select="../Body/Prem"/></GrossPremAmt>
				<!-- ǰ������ -->
				<CountryTo>China</CountryTo>
				<!-- �������ޣ��죩 -->
				<CovPeriod/>
				<!-- ������ܱ��� -->	
				<CalAmount/>
			</OLifEExtension>
		</Policy>
	</Holding>
</OLifE>

 <!--�������д�ӡ�ӿ�       -->
	<Print>
		<!--ƾ֤����-->
		<VoucherNum>1</VoucherNum>
		<SubVoucher>
			<!--ƾ֤����3����-->
			<VoucherType>3</VoucherType>
			<!--��ҳ��-->
			<PageTotal>1</PageTotal>
			<Text>
				<!--ҳ��-->
				<PageNum>1</PageNum>
					<!--һ�д�ӡ������--> 
					<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<TextRowContent/> 
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/> 
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>���պ�ͬ��:<xsl:value-of select="ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>      
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)"/></TextRowContent>
	         		<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>      
					<TextRowContent><xsl:text></xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Appnt/IDNo"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Insured/IDNo"/>
					</TextRowContent>
				 <xsl:choose>
				 <xsl:when test ="$MainRisk/RiskCode=211902" >
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>���/�˲е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 <TextRowContent><xsl:text></xsl:text>�˲еڶ�˳��������Ϊ�������˱��ˡ�</TextRowContent>
				 </xsl:when>
				 <xsl:otherwise>
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>���/ȫ�е�һ˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='1']/Name, 48)"/><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='1']/Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 <TextRowContent><xsl:text></xsl:text>ȫ�еڶ�˳��������Ϊ�������˱��ˡ�</TextRowContent>
				 </xsl:otherwise>
				 </xsl:choose>
				 <xsl:variable name="num" select="count(Bnf)" />
				 <xsl:choose>
				 <xsl:when test="$num = 3">
				 <TextRowContent><xsl:text></xsl:text>��ʵڶ�˳�������ˣ�����</TextRowContent>
				 <TextRowContent/>
				 <TextRowContent/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				<xsl:when test="$num = 4 and Bnf[SeqNo='4']">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Bnf[Grade='2'][Type='1']/LendRiskIDMsg, 53)"/><xsl:text></xsl:text><xsl:text>���������</xsl:text><xsl:value-of select="Bnf[Grade='2'][Type='1']/Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 <TextRowContent/>
				 <TextRowContent/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:choose>
				 <xsl:when test="$num = 5">
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				 <xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=4 and Grade=3">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 <TextRowContent/>
				 </xsl:when>
				 <xsl:otherwise>
				 <xsl:for-each select="Bnf[SeqNo &gt; (3)]">
				<xsl:choose>
				 <xsl:when test="SeqNo=4 and Grade=2">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=2">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=5 and Grade=3">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=2">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵڶ�˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=3">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 <xsl:when test="SeqNo=6 and Grade=4">
				 <TextRowContent>
				    <xsl:text></xsl:text><xsl:text>��ʵ���˳�������ˣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(LendRiskIDMsg, 53)"/><xsl:text>���������</xsl:text><xsl:value-of select="Lot"/><xsl:text>%</xsl:text>
				 </TextRowContent>
				 </xsl:when>
				 </xsl:choose>
				 </xsl:for-each>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>
				 </xsl:otherwise>
				 </xsl:choose>	
					<TextRowContent />
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>��������                          �����ڼ�    �����ڼ�    ���ѷ�ʽ  �����������ս��/����  (����)���շ�</TextRowContent>
					<xsl:for-each select="Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
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
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</TextRowContent>
					</xsl:for-each>
					<TextRowContent />
					<TextRowContent />
					<TextRowContent><xsl:text>�����ڼ���ֹʱ�䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/CValiDate)"/><xsl:text>��ʱ����</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/InsuEndDate)"/><xsl:text>��ʮ��ʱֹ</xsl:text></TextRowContent>  
					<TextRowContent><xsl:text></xsl:text>���շѺϼƣ�<xsl:value-of select="PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>Ԫ��</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent />
					<TextRowContent />
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>�ر�Լ����</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>���ޣ�</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</TextRowContent>
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>------------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>�����������ƣ�<xsl:value-of select="AgentComName"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>���й�Ա����/���룺<xsl:value-of select="SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(SaleStaff,42)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 54)"/>��������绰��<xsl:value-of select="AgentPhone"/></TextRowContent>
					<TextRowContent/>
					<TextRowContent />
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 79,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11(Risk/SignDate)"/></TextRowContent>
					<TextRowContent />
		</Text>
		</SubVoucher>
		<SubVoucher>
		<!--ƾ֤���� 4 �ּ۱�-->
		<VoucherType>5</VoucherType>
		<!--��ҳ��-->
		<PageTotal>1</PageTotal>
		<Text> 
					       <PageNum>1</PageNum>
					       <TextRowContent/> 
					       <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent/>
					      <TextRowContent/>
					      <TextRowContent/> 
					      <TextRowContent/>
					      <TextRowContent/>
					      <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent><xsl:text>                                      ���պ�ͬ�ʹ��ִ</xsl:text></TextRowContent>
					       <TextRowContent/>
					       <TextRowContent/>
					       <TextRowContent><xsl:text></xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="ContNo"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text><xsl:text>Ͷ���ˣ�   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 19)"/><xsl:text>������������: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="AgentCode"/></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��:</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 17)"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>��Ͳ�Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text></xsl:text></TextRowContent>
						   <TextRowContent/>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 19)"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></TextRowContent>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>��ִ���ء�</xsl:text></TextRowContent>
						   <TextRowContent/>
						   <TextRowContent><xsl:text></xsl:text><xsl:text>    ��������Աǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></TextRowContent>
		</Text>
	 </SubVoucher>
	</Print>
</xsl:template>

 <!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (11)]">
				<TextRowContent/>
		</xsl:for-each>
</xsl:template>


<!-- ������Ϣ -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('risk_', string(position()))" /></xsl:attribute>
	<!-- �������� -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- ���ִ��� -->
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>
	<!-- �������� LifeCovTypeCode-->
	<xsl:choose>
		<xsl:when test="RiskCode='313020'">
			<LifeCovTypeCode>9</LifeCovTypeCode>	<!-- ���������� -->
		</xsl:when>  
		<xsl:otherwise> 
			<LifeCovTypeCode>1</LifeCovTypeCode>	<!-- �������� -->
		</xsl:otherwise>
	</xsl:choose> 
	
	<xsl:choose>
		<!-- �����ձ�־ -->
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose> 
	  
	<!-- �ɷѷ�ʽ Ƶ�� -->  
	<PaymentMode>
	<xsl:call-template name="tran_PayIntv">
			<xsl:with-param name="PayIntv"> 
				<xsl:value-of select="PayIntv"/>
			</xsl:with-param>   
		</xsl:call-template> 
</PaymentMode> 
     
	<!-- Ͷ����� -->   
	<InitCovAmt>   
		<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</InitCovAmt>
	<!--  -->
	<FaceAmt>
	<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</FaceAmt>
	<!-- Ͷ���ݶ� -->
	<IntialNumberOfUnits>    
		<xsl:choose>
			<xsl:when test="Mult>=0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose> 
	</IntialNumberOfUnits>
	<!-- ���ֱ��� -->
	<ModalPremAmt><xsl:value-of select="Prem"/></ModalPremAmt>
	<!-- ������ -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- ������ֹ���� -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- ������ʼ���� -->  
	<FirstPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(SignDate)" /></FirstPaymentDate>
	<!-- �ɷ���ֹ���� -->
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	<OLifEExtension>

		<xsl:choose>
			<xsl:when test="(PayEndYear = 105) and (PayEndYearFlag = 'A')">
				<PaymentDurationMode>5</PaymentDurationMode>
				<PaymentDuration>0</PaymentDuration>
			</xsl:when>
			<xsl:otherwise> 
				<PaymentDurationMode><xsl:apply-templates select="PayEndYearFlag" /></PaymentDurationMode>
				<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>
			</xsl:otherwise>
		</xsl:choose>
		 
		
		<!-- �����ڼ� --> 
		<xsl:choose>
			<xsl:when test="(InsuYear= 105) and (InsuYearFlag = 'A')">
				<DurationMode>5</DurationMode>
				<Duration>999</Duration>
			</xsl:when>  
			<xsl:otherwise> 
				<DurationMode><xsl:apply-templates select="InsuYearFlag" /></DurationMode>
				<Duration><xsl:value-of select="InsuYear"/></Duration>
			</xsl:otherwise>
		</xsl:choose>
		
	</OLifEExtension>
</Coverage> 
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

<!-- ֤�����ͣ�����-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- ����  -->
	<xsl:when test=".=7">9</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ��ϵ -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=01">1</xsl:when>	<!-- ��ż -->
	<xsl:when test=".=03">2</xsl:when>	<!-- ��ĸ -->
	<xsl:when test=".=04">3</xsl:when>	<!-- ��Ů -->
	<xsl:when test=".=21">4</xsl:when>	<!-- �游��ĸ -->
	<xsl:when test=".=31">5</xsl:when>	<!-- ����Ů -->
	<xsl:when test=".=12">6</xsl:when>	<!-- �ֵܽ��� -->
	<xsl:when test=".=25">7</xsl:when>	<!-- �������� -->
	<xsl:when test=".=00">8</xsl:when>	<!-- ���� -->
	<xsl:when test=".=27">9</xsl:when>	<!-- ���� -->
	<xsl:when test=".=25">99</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose> 
</xsl:template>
  
<!-- ���ִ��� -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=211901">004</xsl:when>	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=211902">008</xsl:when>	<!-- �к���Ӯ����������˺�����A�� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


 
       
   <!-- ���ؽɷѷ�ʽ -->
<xsl:template name="tran_PayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =12">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =6">3</xsl:when>
		<xsl:when test="PayIntv =3">4</xsl:when>
		<xsl:when test="PayIntv =0">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template> 
 
 
<!-- �ɷ�Ƶ�� -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">   ����   </xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

    

<!-- �ս����ڱ�־��ת�� -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �� -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
<!-- �����������ڱ�־ -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �걣 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �±� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �ձ� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 



<!-- ������ȡ��ʽ��ת�� -->
<xsl:template match="BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">�ۻ���Ϣ</xsl:when>
	<xsl:when test=".=2">��ȡ�ֽ�</xsl:when>
	<xsl:when test=".=3">�ֽ�����</xsl:when>
	<xsl:when test=".=5">�����</xsl:when>
	<xsl:when test=".=''">   --   </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
