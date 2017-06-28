<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	<!-- 01 ����������11 ��̨��04 �����նˣ�20 ���չ�˾������ 02�ֻ����� -->
 	<xsl:variable name="tSellType" select="/ABCB2I/Header/EntrustWay" />
	<xsl:template match="ABCB2I">
	  <TranData><!-- ����¼���Ժ������� -->
	     <Head>
	  		<!-- �������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ���д��� -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- �������� -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay='11'"><!-- ���� -->
					<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				</xsl:when>
				<xsl:otherwise><!-- ��������ʵ���������ȡʵ�����㣬������ȡ�������� -->
					<xsl:if test="Header/ProvCode != '' and Header/BranchNo != ''">
						<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
					</xsl:if>
					<xsl:if test="Header/ProvCode = '' or Header/BranchNo = ''">
						<NodeNo>ABCWEB</NodeNo>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<!-- ��Ա���� -->
			<xsl:choose>
				<xsl:when test="Header/EntrustWay ='11'">
					<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
				</xsl:when>
				<xsl:otherwise>
					<TellerNo>0005</TellerNo>
				</xsl:otherwise>
			</xsl:choose>
			<!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- �������� -->
			<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
            <!-- ����˳��ţ���ũ�м����ֶΣ������ѯ�õ��� -->
			<ApplyNo><xsl:value-of  select ="App/Req/AppNo"/></ApplyNo >
		   	<!-- Ͷ����(ӡˢ)�� -->
		   	<xsl:choose>
		   		<!-- ���� -->
		   		<xsl:when test="Header/EntrustWay='11'">
		   			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/Base/PolicyApplySerial)"/></ProposalPrtNo>
		   		</xsl:when>
		   		<xsl:otherwise>
			   		<xsl:variable name="tMaxPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.CreateMaxNo('ABCPRTNO','SN')"></xsl:variable> 
					<xsl:variable name="tPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.PrtNoTo8($tMaxPrtNo,'05')"></xsl:variable> 
					<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($tPrtNo)" /></ProposalPrtNo>
		   		</xsl:otherwise>
		   	</xsl:choose>
		   	<!-- ������ͬӡˢ�� (��֤)  -->
			<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/Base/VchNo)"/></ContPrtNo>
		   <!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="App/Req/Base/ApplyDate"/></PolApplyDate>
		   <!-- �������ͷ�ʽ Ĭ��ȫΪ1-->
			<GetPolMode>1</GetPolMode><!-- Ĭ��Ϊ1������д����1 -->
		   <!-- ������֪(N/Y) ,ȡ�����˽�����֪  0��  1��-->
		   <HealthNotice><xsl:apply-templates select="App/Req/Insu/HealthNotice"/></HealthNotice>
		   <!-- �����˻����� ���ڽɷ��˺�����ConAccName��ũ��û�������˻�����  -->
		   <AccName><xsl:value-of select="App/Req/Base/ConAccName"/></AccName>
		   <!-- ���������˻� ���ڽɷ��˺� ConAccNo�����ڽɷ��˺����µ�ȷ�Ͻ����лᴫ�����ģ� ��ũ�е�������Ա��ͨ��20141203 -->
		   <AccNo><xsl:value-of select="App/Req/Base/ConAccNo"/></AccNo>
		    <!-- ������Ա����  -->
		    <SaleName><xsl:value-of select="App/Req/Base/Saler" /></SaleName>
		   	<!-- ������Ա����   ��ũ��Լ�������ֶ�2Ϊ������Ա����-->
		   	<SaleStaff><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleStaff>
            <!-- ������Ա�ʸ�֤��  -->
            <SaleCertNo><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleCertNo>
			<!-- Ͷ���� -->
			<xsl:apply-templates select="App/Req/Appl"/>
			<!-- ������ -->
			<xsl:apply-templates select="App/Req/Insu"/>
			<!-- ��������Ϣ -->
		    <Risk>
		    	<!-- ���ִ��� -->
		    	<xsl:variable name="tRiskCode" select="App/Req/Risks/RiskCode"/>
				<RiskCode><xsl:value-of select="$tRiskCode"/></RiskCode>
				<!-- �������ִ��� -->
				<MainRiskCode><xsl:value-of select="$tRiskCode"/></MainRiskCode>
				<!-- ����(��)  -->
				<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Amnt)"/></Amnt>
				<!-- ���շ�(��) --><!--���ĸ��ݱ��������㱣��,���ǶԱ��ѽڵ��зǿ�У��,����PremΪ�ǿ�У��ֵ��ʵ������ -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Prem)"/></Prem>
				<!-- Ͷ������ -->
				<Mult><xsl:value-of select="App/Req/Risks/Share"/></Mult>
				<!-- �ɷ���ʽ -->
				<PayMode>B</PayMode>
				<!-- �ɷ�Ƶ�� -->
				<xsl:variable name="tPayIntv" select="App/Req/Risks/PayType"/>
				<xsl:variable name="tInsuYearFlag" select="App/Req/Risks/InsuDueType"/>
				<PayIntv><xsl:apply-templates select="$tPayIntv"/></PayIntv>
				<xsl:choose>
					<xsl:when test="$tInsuYearFlag=6">
						<!-- �������������־ -->
						<InsuYearFlag>A</InsuYearFlag>
						<!-- ������������ -->
						<InsuYear>105</InsuYear>
					</xsl:when>
					<xsl:otherwise>
						<!-- �������������־ -->
						<InsuYearFlag><xsl:apply-templates select="$tInsuYearFlag"/></InsuYearFlag>
						<!-- ������������ -->
						<InsuYear><xsl:value-of select="App/Req/Risks/InsuDueDate"/></InsuYear>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="$tPayIntv=1 and ($tRiskCode='011A0100'  or $tRiskCode='012D0100' or $tRiskCode='012E0100')"><!-- ������1000Y -->
						<!-- �ɷ����������־ -->
						<PayEndYearFlag>Y</PayEndYearFlag>			
						<!-- �ɷ��������� -->
						<PayEndYear>1000</PayEndYear>
					</xsl:when>
					<xsl:when test="$tPayIntv=1 and $tRiskCode='022J0300'">
						<!-- �ɷ����������־ -->
						<PayEndYearFlag>M</PayEndYearFlag>			
						<!-- �ɷ��������� -->
						<PayEndYear>12</PayEndYear>
					</xsl:when>
					<xsl:otherwise>
						<!-- �ɷ����������־ -->
						<PayEndYearFlag><xsl:apply-templates select="App/Req/Risks/PayDueType"/></PayEndYearFlag>			
						<!-- �ɷ��������� -->
						<PayEndYear><xsl:value-of select="App/Req/Risks/PayDueDate"/></PayEndYear>
					</xsl:otherwise>
				</xsl:choose>
				<!-- ������ȡ��ʽ -->
				<BonusGetMode><xsl:apply-templates select="App/Req/Risks/BonusGetMode"/></BonusGetMode>
				<!-- ������ȡ����ȡ��ʽ -->
				<FullBonusGetMode><xsl:apply-templates select="App/Req/Risks/FullBonusGetMode"/></FullBonusGetMode>
				<!-- ��ȡ�������ڱ�־ -->
				<GetYearFlag><xsl:apply-templates select="App/Req/Risks/GetYearFlag"/></GetYearFlag>
				<!-- ��ȡ���� -->
				<GetYear><xsl:value-of select="App/Req/Risks/GetYear"/></GetYear>
				<!-- ��ȡ����-->
				<GetTerms/>
				<!-- ��ȡ��ʽ ���� -->
				<GetIntv/>
				<!-- ��ȡ���б��� ���� -->
				<GetBankCode/>
				<!-- ��ȡ�����˻� ���� -->
				<GetBankAccNo/>
				<!-- ��ȡ���л���  ���� -->
				<GetAccName/>
				<!-- �Զ��潻��־ ���� -->
		    	<AutoPayFlag/>
		    </Risk>
			<!-- ������Ϣ -->
			<xsl:apply-templates select="App/Req/Loan"/>
			<!--�ɷ���ʽ ����Ϲ�ͨ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
			<PayMode>B</PayMode>
			<!-- ְҵ��֪ -->
			<JobNotice/>
		</Body>
	</TranData>
</xsl:template>	
<xsl:template match="App/Req/Appl">
		<Appnt>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- �������� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- ְҵ���� --><!-- �ͺ����Լ��ͻ�ȷ�Ϲ���ũ�в�������Ĭ��ֵ3010101��һ�����ڣ�����ȷ���ʼ� -->
			<JobCode><xsl:value-of select="JobCode"/></JobCode>
			<!-- ���� -->
			<Nationality><xsl:apply-templates select="Country"/></Nationality>
			<!-- ���(cm)  ��ֵ -->
			<Stature/>
			<!-- ����(kg)  -->
			<Weight/>
			<!-- ���(N/Y) ��ֵ -->
			<MaritalStatus/>
			<!-- ������(��Ԫ) -->
			<xsl:if test="$tSellType ='11' or $tSellType ='04'">
				<YearSalary>
					<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
				</YearSalary>
			</xsl:if>
			<xsl:if test="$tSellType ='01' or $tSellType ='02'">
				<YearSalary>
					<xsl:call-template name="tran_income">
						<xsl:with-param name="income">
							<xsl:value-of select="AnnualIncome" />
						</xsl:with-param>
					</xsl:call-template>
				</YearSalary>
			</xsl:if>
			<!-- Ͷ���˼�ͥ������ -->
			<FamilyYearSalary/>
			<!-- ���֤֤����Ч�ڸ�ʽ:yyyyMMdd --><!-- 8λ���ڣ�������ЧΪ99991231 -->
			<xsl:choose>
				<xsl:when test = "InvalidDate=99991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="InvalidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- Ͷ������ϸ��ַ���� -->
			<AddressContent/>
			<!-- Ͷ���˹̶��绰�������� -->
			<FixTelDmstDstcNo/>
			<!-- Ͷ�����ƶ��绰�������� -->
			<MobileItlDstcNo/>
			<!-- Ͷ���˹��ҵ������� -->
			<NationalityCode/>
			<!-- Ͷ���˵�ַ -->
			<Address><xsl:value-of select="Address"/></Address>
			<!-- Ͷ������������ -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- �ƶ��绰 -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- ��ͥ�绰 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- Ͷ�����뱻���˹�ϵ -->
			<RelaToInsured><xsl:apply-templates select="RelaToInsured"/></RelaToInsured>
			<!-- Ͷ���˾������� -->
		 	<DenType><xsl:apply-templates select="CustSource" /></DenType>
			<!-- ʡ��¼�� -->
			<Province><xsl:value-of select="Prov"/></Province>
			<!-- �� ��¼��-->
			<City><xsl:value-of select="City"/></City>
			<!-- ����¼��-->
			<County><xsl:value-of select="Zone"/></County>
			<!-- ʡ�Ǳ��� -->
			<WorkProvince></WorkProvince>
			<!-- �� �Ǳ���-->
			<WorkCity></WorkCity>
			<!-- ���Ǳ���-->
			<WorkCounty></WorkCounty>
			<!--��λ��ַ -->
			<WorkAddress></WorkAddress>
		</Appnt>
</xsl:template>
<!--��������Ϣ-->
<xsl:template match="App/Req/Insu">
		<Insured>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- �������� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- ְҵ���� --><!-- �ͺ����Լ��ͻ�ȷ�Ϲ���ũ�в�������Ĭ��ֵ3010101��һ�����ڣ�����ȷ���ʼ� -->
			<JobCode><xsl:value-of select="JobCode"/></JobCode>
			<!-- ���� -->
			<Nationality><xsl:apply-templates select="Country"/></Nationality>
			<!-- ���(cm)  ��ֵ -->
			<Stature><xsl:value-of select="Tall"/></Stature>
			<!-- ����(g)  ��ֵ -->
			<Weight><xsl:value-of select="Weight"/></Weight>
			<!-- ���(N/Y) ��ֵ -->
			<MaritalStatus/>
			<!-- ������(��Ԫ) -->
			<!-- ���ݽ��������Ĳ�ͬ������ʾ��ʽ��ͬ -->
			<xsl:if test="$tSellType ='11' or $tSellType ='04'">
				<YearSalary>
					<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
				</YearSalary>
			</xsl:if>
			<xsl:if test="$tSellType ='01' or $tSellType ='02'">
				<YearSalary>
					<xsl:call-template name="tran_income">
						<xsl:with-param name="income">
							<xsl:value-of select="AnnualIncome" />
						</xsl:with-param>
					</xsl:call-template>
				</YearSalary>
			</xsl:if>
			<!-- ���֤֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ99991231 -->
			<xsl:choose>
				<xsl:when test = "ValidDate=99991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="ValidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- �����˵�ַ -->
			<Address><xsl:value-of select="Address"/></Address>
			<!-- �ʱ� -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- �ƶ��绰 -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- �̶��绰 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- ʡ��¼�� -->
			<Province><xsl:value-of select="Prov"/></Province>
			<!-- �� ��¼��-->
			<City><xsl:value-of select="City"/></City>
			<!-- ����¼��-->
			<County><xsl:value-of select="Zone"/></County>
			<!-- ʡ�Ǳ��� -->
			<WorkProvince></WorkProvince>
			<!-- �� �Ǳ���-->
			<WorkCity></WorkCity>
			<!-- ���Ǳ���-->
			<WorkCounty></WorkCounty>
			<!--��λ��ַ -->
			<WorkAddress></WorkAddress>
		</Insured>
</xsl:template>

<!--������Ϣ-->
<xsl:template match="App/Req/Loan">
		<Loan>
			<!-- �����ͬ�� -->
			<LoanNo><xsl:value-of select="ContNo"/></LoanNo>
			<!-- ����������� -->
			<LoanBankName><xsl:value-of select="LoanBank"/></LoanBankName>
			<!-- �������� -->
			<LoanDate><xsl:value-of select="BegDate"/></LoanDate>
			<!-- ������� -->
			<LoanEndDate><xsl:value-of select="EndDate"/></LoanEndDate>
			<!-- �������� -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- �����˺� -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- ������ -->
			<xsl:choose><!-- �����ж�choose�£���Ȼ�յĻ���yuanToFen��ʱ����0�ˣ����ݲ�ԭ֭ԭζ�� -->
				<xsl:when test = "Prem=''"><LoanPrem/></xsl:when>
				<xsl:otherwise>
					<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></LoanPrem>
				</xsl:otherwise>
			</xsl:choose>
			<!-- ������ʼ�� -->
			<InsuDate><xsl:value-of select="/ABCB2I/App/Req/Risks/RiskBeginDate"/></InsuDate>
			<!-- ������ֹ�� -->
			<InsuEndDate><xsl:value-of select="/ABCB2I/App/Req/Risks/RiskEndDate"/></InsuEndDate>
			
			<IsPrepayInsu></IsPrepayInsu>
			<PrepayInsuPrem>0</PrepayInsuPrem>
			<PrepayYear></PrepayYear>
		</Loan>
</xsl:template>

<!-- ί�з�ʽ
01-������������
02-������������
04-���������ն�����
11-���й�̨����
20-���չ�˾����
 -->
<xsl:template name="tran_salechannel" match="EntrustWay">
	<xsl:choose>
		<xsl:when test=".=01">1</xsl:when><!-- ������������ -->
		<xsl:when test=".=02">2</xsl:when><!-- ������������ -->
		<xsl:when test=".=04">8</xsl:when><!-- ���������ն����� -->
		<xsl:when test=".=11">0</xsl:when><!-- ���й�̨���� -->
		<xsl:when test=".=20"></xsl:when><!-- ���չ�˾���� -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--������֪ -->
<xsl:template name="tran_health" match="HealthNotice">
	<xsl:choose>
		<xsl:when test=".=0">N</xsl:when>	<!-- �� -->
		<xsl:when test=".=1">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ��ϵ���� -->
<xsl:template name="tran_relatoinsured" match="RelaToInsured">
	<xsl:choose>
		<xsl:when test=".=01">00</xsl:when> <!-- ����              -->
		<xsl:when test=".=02">02</xsl:when> <!-- �ɷ�              -->
		<xsl:when test=".=03">02</xsl:when> <!-- ����              -->
		<xsl:when test=".=04">01</xsl:when> <!-- ����              -->
		<xsl:when test=".=05">01</xsl:when> <!-- ĸ��              -->
		<xsl:when test=".=06">03</xsl:when> <!-- ����              -->
		<xsl:when test=".=07">03</xsl:when> <!-- Ů��              -->
		<xsl:when test=".=08">04</xsl:when> <!-- �游              -->
		<xsl:when test=".=09">04</xsl:when> <!-- ��ĸ              -->
		<xsl:when test=".=10">04</xsl:when> <!--����               -->
		<xsl:when test=".=11">04</xsl:when> <!--��Ů               -->
		<xsl:when test=".=12">04</xsl:when> <!--���游             -->
		<xsl:when test=".=13">04</xsl:when> <!--����ĸ             -->
		<xsl:when test=".=14">04</xsl:when> <!--����               -->
		<xsl:when test=".=15">04</xsl:when> <!--����Ů             -->
		<xsl:when test=".=16">99</xsl:when> <!--���               -->
		<xsl:when test=".=17">99</xsl:when> <!--���               -->
		<xsl:when test=".=18">99</xsl:when> <!--�ܵ�               -->
		<xsl:when test=".=19">99</xsl:when> <!--����               -->
		<xsl:when test=".=20">99</xsl:when> <!--����               -->
		<xsl:when test=".=21">99</xsl:when> <!--����               -->
		<xsl:when test=".=22">99</xsl:when> <!--��ϱ               -->
		<xsl:when test=".=23">99</xsl:when> <!--����               -->
		<xsl:when test=".=24">99</xsl:when> <!--��ĸ               -->
		<xsl:when test=".=25">99</xsl:when> <!--Ů��               -->
		<xsl:when test=".=26">99</xsl:when> <!--��������        -->
		<xsl:when test=".=27">99</xsl:when> <!--ͬ��     -->
		<xsl:when test=".=28">99</xsl:when> <!--����     -->
		<xsl:when test=".=29">99</xsl:when> <!--����     -->
		<xsl:when test=".=30">99</xsl:when> <!--����     -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
	<xsl:choose>
		<xsl:when test=".=0">0</xsl:when>	<!-- �� -->
		<xsl:when test=".=1">1</xsl:when>	<!-- Ů -->
		<xsl:when test=".=2">2</xsl:when>	<!-- Ĭ�� -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ֤������-->
<xsl:template name="tran_idtype" match="IDKind">
	<xsl:choose>
		<xsl:when test=".=110001">0</xsl:when>	<!-- �������֤ -->
		<xsl:when test=".=110002">99</xsl:when>	<!-- �غž������֤-->
		<xsl:when test=".=110003">0</xsl:when>	<!-- ��ʱ�������֤ -->
		<xsl:when test=".=110004">99</xsl:when>	<!-- �غ���ʱ�������֤ -->
		<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
		<xsl:when test=".=110006">99</xsl:when>  <!-- �غŻ��ڲ�  -->
		<xsl:when test=".=110007">99</xsl:when> <!-- �������֤ -->
		<xsl:when test=".=110008">99</xsl:when> <!-- �غ��й������ž��������֤�� -->
		<xsl:when test=".=110009">99</xsl:when> <!-- ��װ�������֤ -->
		<xsl:when test=".=110010">99</xsl:when> <!-- �غ��й�������װ�������֤�� -->
		<xsl:when test=".=110011">99</xsl:when>  <!-- ���ݸɲ�����֤ -->
		<xsl:when test=".=110012">99</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
		<xsl:when test=".=110013">99</xsl:when>  <!-- ��������֤ -->
		<xsl:when test=".=110014">99</xsl:when>  <!-- �غž�������֤ -->
		<xsl:when test=".=110015">99</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
		<xsl:when test=".=110016">99</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
		<xsl:when test=".=110017">99</xsl:when>  <!-- ����ԺУѧԱ֤ -->
		<xsl:when test=".=110018">99</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
		<xsl:when test=".=110019">F</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110020">99</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110021">F</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
		<xsl:when test=".=110022">99</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
		<xsl:when test=".=110023">99</xsl:when>  <!-- �л����񹲺͹����� -->
		<xsl:when test=".=110024">99</xsl:when>  <!-- �غ��л����񹲺͹����� -->
		<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
		<xsl:when test=".=110026">99</xsl:when>  <!-- �غ�������� -->
		<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110028">99</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110029">2</xsl:when>  <!-- ��ְ�ɲ�֤ -->
		<xsl:when test=".=110030">99</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
		<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110032">99</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110033">2</xsl:when>  <!-- ����ʿ��֤ -->
		<xsl:when test=".=110034">99</xsl:when>  <!-- �غž���ʿ��֤ -->
		<xsl:when test=".=110035">2</xsl:when>  <!-- �侯ʿ��֤ -->
		<xsl:when test=".=110036">99</xsl:when>  <!-- �غ��侯ʿ��֤ -->
		<xsl:when test=".=110037">1</xsl:when>  <!-- ����˾���֤-->
		<xsl:when test=".=110043">99</xsl:when>  <!-- ����������֤ -->
		<xsl:when test=".=110045">99</xsl:when>  <!-- �⽻��֤ -->
		<xsl:when test=".=110047">99</xsl:when>  <!-- �л����񹲺͹�����֤ -->
		<xsl:when test=".=119998">99</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
		<xsl:when test=".=119999">99</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ���ִ��� -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".='011A0100'">011A0100</xsl:when>	<!--  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �����ִ��� -->
<xsl:template name="MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".='011A0100'">011A0100</xsl:when>	<!--  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ɷѷ�ʽ��Ƶ�Σ�   6	����   7	������  ����û��   
0	������
1	����
2	�½�
3	����
4	���꽻
5	�꽻
6	����
7	������
-->
<xsl:template name="tran_payintv" match="PayType">
	<xsl:choose>
		<xsl:when test=".=0">-1</xsl:when>	<!-- ������ -->
		<xsl:when test=".=1">0</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">1</xsl:when>	<!-- �½� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
		<xsl:when test=".=4">6</xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=5">12</xsl:when>	<!-- �꽻 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="tran_payendyearflag" match="PayDueType">
	<xsl:choose>
		<xsl:when test=".='0'">A</xsl:when>	<!-- ����-->
		<xsl:when test=".='1'">A</xsl:when>	<!-- ����ĳȷ������ -->
		<xsl:when test=".='2'">M</xsl:when>	<!-- �� -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- �� -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- �� -->
		<xsl:when test=".='5'"></xsl:when><!-- ����û������ -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<!-- ����������������
0	�޹�
1	��
2	��
3	��
4	��
5	����
6	����
 -->
<xsl:template name="tran_insuyearflag" match="InsuDueType">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- �޹� -->
		<xsl:when test=".=1">D</xsl:when>	<!-- �� -->
		<xsl:when test=".=2">M</xsl:when>	<!-- �� -->
		<xsl:when test=".=3"></xsl:when><!-- �� -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- �� -->
		<xsl:when test=".=5">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".=6">A</xsl:when>	<!-- ���� ?��ȷ�ϣ����������������ͺ��ı������Ӧ�����Ƕ���-->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ��ȡ�������ڱ�־
   1 	������
2	����
3	����
4	����
 -->
<xsl:template name="tran_getYearFlag" match="GetYearFlag">
	<xsl:choose>
		<xsl:when test=".=0">Y</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">A</xsl:when><!-- ������ -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ������ȡ��ʽ
 0	ֱ�Ӹ���
1	�ֽ�����
2	�ۻ���Ϣ
3	�����
 -->
<xsl:template name="tran_bonusgetmode" match="BonusGetMode">
		<xsl:choose>
		<xsl:when test=".=0">2</xsl:when>	<!-- ֱ�Ӹ��� -->
		<xsl:when test=".=1">3</xsl:when>	<!-- �ֽ����� -->
		<xsl:when test=".=2">1</xsl:when>	<!-- �ۻ���Ϣ -->
		<xsl:when test=".=3">5</xsl:when>	<!-- ����� -->
		<xsl:when test=".=''"></xsl:when><!-- ����Ʒû�к�����ȡ��ʽ -->
		<xsl:otherwise>4</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ������ȡ����ȡ��ʽ -->
<xsl:template name="tran_FullBonusGetMode" match="FullBonusGetMode">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- ���� -->
		<xsl:when test=".=1">M</xsl:when><!-- ���� -->
		<xsl:when test=".=2"></xsl:when><!-- ���� -->
		<xsl:when test=".=3"></xsl:when><!-- ������ -->
		<xsl:when test=".=4">Y</xsl:when><!-- ���� -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ����մ������� -->
<xsl:template name="tran_LoanType" match="LoanType">
	<xsl:choose>
		<xsl:when test=".=00">01</xsl:when>	<!-- һ����ҵ���� -->
		<xsl:when test=".=01">02</xsl:when>	<!-- �����ҵ���� -->
		<xsl:when test=".=10">03</xsl:when>	<!-- ��������ϴ��� -->
		<xsl:when test=".=11">04</xsl:when>	<!-- ����������� -->
		<xsl:when test=".=20">05</xsl:when>	<!-- ��Ϣ��ѧ���� -->
		<xsl:otherwise>01</xsl:otherwise>  
	</xsl:choose>
</xsl:template>
<!--������ת��-->
<xsl:template name="tran_income">
		<xsl:param name="income">1</xsl:param>
		<xsl:choose>
			<xsl:when test="$income = '5������'">5</xsl:when>
			<xsl:when test="$income = '5-10��'">10</xsl:when>
			<xsl:when test="$income = '10-30��'">30</xsl:when>
			<xsl:when test="$income = '30-50��'">50</xsl:when>
			<xsl:when test="$income = '50������'">70</xsl:when>
			<xsl:otherwise>0</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
<!-- Ͷ���˾������� -->
<xsl:template name="cust_Source" match="CustSource">
	<xsl:choose>
		<xsl:when test=".=0">2</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">1</xsl:when>	<!-- ũ�� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ����  -->
<xsl:template name="tran_Country" match="Country">
	<xsl:choose>
		<xsl:when test=".=156">CHN</xsl:when> <!--�й�                      -->
		<xsl:when test=".=344">HK</xsl:when> <!--�й����                  -->
		<xsl:when test=".=158">TW</xsl:when> <!--�й�̨��                  -->
		<xsl:when test=".=446">MO</xsl:when> <!--�й�����                  -->
		<xsl:when test=".=392">JAN</xsl:when> <!--�ձ�                      -->
		<xsl:when test=".=840">USA</xsl:when> <!--����                      -->
		<xsl:when test=".=643">RUS</xsl:when> <!--����˹                    -->
		<xsl:when test=".=826">ENG</xsl:when> <!--Ӣ��                      -->
		<xsl:when test=".=250">FRA</xsl:when> <!--����                      -->
		<xsl:when test=".=276">DEU</xsl:when> <!--�¹�                      -->
		<xsl:when test=".=410">KOR</xsl:when> <!--����                      -->
		<xsl:when test=".=702">SG</xsl:when> <!--�¼���                    -->
		<xsl:when test=".=360">INA</xsl:when> <!--ӡ��������                -->
		<xsl:when test=".=356">IND</xsl:when> <!--ӡ��                      -->
		<xsl:when test=".=380">ITA</xsl:when> <!--�����                    -->
		<xsl:when test=".=458">MY</xsl:when> <!--��������                  -->
		<xsl:when test=".=764">THA</xsl:when> <!--̩��                      -->
		<xsl:when test=".=999">OTH</xsl:when> <!--�������Һ͵���            -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>