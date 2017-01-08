<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="ABCB2I">
	  <TranData><!-- ����¼���Ժ������� -->
	     <Head>
	        <!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- ������� -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			
			<!-- ��Ա���� -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			</xsl:when>
			<xsl:otherwise>
			<TellerNo>0005</TellerNo>
			</xsl:otherwise>
			</xsl:choose>
			
			
			
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- ũ�������ն����� 0���� 8�����ն� -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<SaleChannel>0</SaleChannel>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			<SaleChannel>8</SaleChannel>
			</xsl:when>
			
			</xsl:choose>
		    <!-- ������Ա����   ��ũ��Լ�������ֶ�2Ϊ������Ա����-->
		   
		    <xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			 <SaleStaff><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleStaff>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			  <SaleStaff>0007 </SaleStaff>
			</xsl:when>
			</xsl:choose>
		    <!-- ������Ա����  -->
            <xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			 <SaleName>
            <xsl:value-of select="App/Req/Base/Saler" />
            </SaleName>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			 <SaleName>0007</SaleName>
			</xsl:when>
			</xsl:choose>
			
            
            <!-- ������Ա�ʸ�֤��  -->
            <SaleCertNo><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleCertNo>
            <!-- ��������˳��� -->
			<ApplyNo><xsl:value-of  select ="App/Req/AppNo"/></ApplyNo >	
			<!-- Ͷ������ -->
			<ProposalPrtNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/PolicyApplySerial"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
			</ProposalPrtNo>  
			<!-- ����ӡˢ�� -->
			<ContPrtNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/VchNo"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="App/Req/Base/ApplyDate"/></PolApplyDate>
			<!-- �˻����� -->
			<!--
			<AccName><xsl:value-of select="Appl/Name"/></AccName>
			-->
			<!-- �����ʺ����� ���ڽɷ��˺�����ConAccName��ũ��û�������˻�����  -->
			<AccName>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/ConAccName"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</AccName>
			<!-- �˻����д��� -->
			<AccBankCode/>
			<!-- �����ʺ� ���ڽɷ��˺� ConAccNo�����ڽɷ��˺����µ�ȷ�Ͻ����лᴫ�����ģ� ��ũ�е�������Ա��ͨ��20141203 -->
			<AccNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/ConAccNo"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</AccNo>
			
			<!--�ɷ���ʽ ����Ϲ�ͨ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
			<PayMode>B</PayMode>
			<!-- �������ͷ�ʽ -->
			<GetPolMode>1</GetPolMode><!-- Ĭ��Ϊ1������д����1 -->
			<!-- ְҵ��֪ -->
			<JobNotice/>
			<!-- ������֪ ,ȡ�����˽�����֪  0��  1��-->
			<HealthNotice><xsl:apply-templates select="App/Req/Insu/HealthNotice"/></HealthNotice>
			<!-- Ͷ���� -->
			<xsl:apply-templates select="App/Req/Appl"/>
			<!-- ������ -->
			<xsl:apply-templates select="App/Req/Insu"/>
			
			<!-- ��������Ϣ -->
		    <Risk>
		    <!-- ���ִ��� -->
			<RiskCode><xsl:apply-templates select="App/Req/Risks/RiskCode"/></RiskCode>
			<!-- �������ִ��� -->
			<MainRiskCode><xsl:apply-templates select="App/Req/Risks/RiskCode"/></MainRiskCode>
			<!-- ���� -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Amnt)"/></Amnt>
			<!-- ���� -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Prem)"/></Prem>
			<!-- Ͷ������ -->
			<Mult>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Risks/Share"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'">1</xsl:if>
			</Mult>
			<!-- �ɷ���ʽ -->
			<PayMode>B</PayMode>
			<!-- �ɷ�Ƶ�� -->
			<!--<xsl:choose> ũ�н���յĽɷ�Ƶ��Ϊ0-����-�ſ���rule.xsl��У��
			<xsl:when test="$MainProductCode=211901"><PayIntv>0</PayIntv></xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			-->
			<xsl:choose>
			<xsl:when test="App/Req/Risks/RiskCode='145201'">
			<PayIntv>0</PayIntv>
			</xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="App/Req/Risks/PayType"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="App/Req/Risks/PayType=1"><!-- ������1000Y -->
				<!-- �ɷ����������־ -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:when test="App/Req/Risks/RiskCode=211901"><!-- ������1000Y -->
				<!-- �ɷ����������־ -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:otherwise>
				<!-- �ɷ����������־ -->
				<PayEndYearFlag><xsl:apply-templates select="App/Req/Risks/PayDueType"/></PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear><xsl:value-of select="App/Req/Risks/PayDueDate"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- �������������־ -->
			<InsuYearFlag><xsl:apply-templates select="App/Req/Risks/InsuDueType"/></InsuYearFlag>
			<!-- ������������ -->
			<InsuYear><xsl:value-of select="App/Req/Risks/InsuDueDate"/></InsuYear>
			<!-- ������ȡ��ʽ -->
			<BonusGetMode><xsl:apply-templates select="App/Req/Risks/BonusGetMode"/></BonusGetMode>
			<!-- ���� --> <!-- ������ȡ����ȡ��ʽ -->
			<FullBonusGetMode><xsl:apply-templates select="App/Req/Risks/FullBonusGetMode"/></FullBonusGetMode>
			<!-- ���� --> <!-- ��ȡ�������ڱ�־ -->
			<GetYearFlag><xsl:apply-templates select="App/Req/Risks/GetYearFlag"/></GetYearFlag>
			<!-- ���� --> <!-- ��ȡ���� -->
			<GetYear><xsl:value-of select="App/Req/Risks/GetYear"/></GetYear>
			<!-- ũ�в��� -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
			<!-- ��������Ϣ -->
			<xsl:apply-templates select="App/Req/Addt"/>
			<!-- ������Ϣ -->
			<xsl:apply-templates select="App/Req/Loan"/>
		</Body>
	</TranData>
</xsl:template>	
<xsl:template match="App/Req/Appl">
		<Appnt>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- ���� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- ��ַ -->
			<Prov><xsl:value-of select="Prov"/></Prov>
			<City><xsl:value-of select="City"/></City>
			<Zone><xsl:value-of select="Zone"/></Zone>
			<Address><xsl:value-of select="Address"/></Address>
			<!-- �ʱ� -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- ��ͥ�绰 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- �ֻ����� -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- ְҵ���� --><!-- �ͺ����Լ��ͻ�ȷ�Ϲ���ũ�в�������Ĭ��ֵ3010101��һ�����ڣ�����ȷ���ʼ� -->
			 <JobCode>3010101</JobCode>
			<!-- ���� -->
			<Nationality>CHN</Nationality>
			<!-- ���� -->
			<Stature/>
			<!-- ��� -->
			<Weight/>
			<!-- ���� -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/></YearSalary>
			
			<!-- ֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "InvalidDate=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="InvalidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- ��� -->
			<MaritalStatus/>
				<!-- Ͷ�����뱳���˹�ϵ -->
		<RelaToInsured><xsl:apply-templates select="RelaToInsured"/></RelaToInsured>
		<!-- Ͷ���˾������� -->
		 <DenType><xsl:apply-templates select="CustSource" /></DenType>
		</Appnt>
</xsl:template>
		
<!--��������Ϣ-->
<xsl:template match="App/Req/Insu">
		<Insured>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- ���� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- ��ַ -->
			<Prov><xsl:value-of select="Prov"/></Prov>
			<City><xsl:value-of select="City"/></City>
			<Zone><xsl:value-of select="Zone"/></Zone>
			<Address><xsl:value-of select="Address"/></Address>
			<!-- �ʱ� -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- ��ͥ�绰 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- �ֻ����� -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- ְҵ���� --><!-- �ͺ����Լ��ͻ�ȷ�Ϲ���ũ�в�������Ĭ��ֵ3010101��һ�����ڣ�����ȷ���ʼ� -->
			<JobCode>3010101</JobCode>
			<!-- ���� -->
			<Nationality>CHN</Nationality>
			<!-- ���� -->
			<Stature><xsl:value-of select="Tall"/></Stature>
			<!-- ��� -->
			<Weight><xsl:value-of select="Weight"/></Weight>
			
			
			<!-- ������ -->
			<!-- ���ݽ��������Ĳ�ͬ������ʾ��ʽ��ͬ -->
			
			
			<YearSalary>
			<xsl:if  test="Header/EntrustWay = '04' ">
			<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
			</xsl:if>
			
			<xsl:if  test="Header/EntrustWay = '11' ">
			<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
			</xsl:if>
			</YearSalary>
			
			
			
			
			
			
			<!-- ֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "InvalidDate=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="InvalidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- ��� -->
			<MaritalStatus/>
		</Insured>
</xsl:template>



<!--����������Ϣ-->
<xsl:template match="App/Req/Addt">
        <xsl:if test="Count!='0'">
	    <xsl:variable name="MainProductCode" select="../Risks/RiskCode"/>
		<Risk>
		    <!-- ���ִ��� -->
			<RiskCode><xsl:apply-templates select="RiskCode1"/></RiskCode>
			<!-- �������ִ��� -->
			<MainRiskCode><xsl:apply-templates select="$MainProductCode"/></MainRiskCode>
			<!-- ���� -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt1)"/></Amnt>
			<!-- ���� -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem1)"/></Prem>
			<!-- Ͷ������ -->
			<Mult><xsl:value-of select="Share1"/></Mult>
			<!-- �ɷ���ʽ --><!-- ũ���� -->
			<PayMode>B</PayMode>
			<!-- �ɷ�Ƶ�� -->
			<!--<xsl:choose> ũ�н���յĽɷ�Ƶ��Ϊ0-����-�ſ���rule.xsl��У��
			<xsl:when test="$MainProductCode=211901"><PayIntv>0</PayIntv></xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			-->
			<xsl:choose>
			<xsl:when test="RiskCode1='145201'">
			<PayIntv>0</PayIntv>
			</xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayType1"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="PayType1=1"><!-- ������1000Y -->
				<!-- �ɷ����������־ -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:when test="$MainProductCode=211901"><!-- ������1000Y -->
				<!-- �ɷ����������־ -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:otherwise>
				<!-- �ɷ����������־ -->
				<PayEndYearFlag><xsl:apply-templates select="PayDueType1"/></PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear><xsl:value-of select="PayDueDate1"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			
			<!-- �������������־ -->
			<InsuYearFlag><xsl:apply-templates select="//App/Req/Risks/InsuDueType"/></InsuYearFlag>
			<!-- ������������ -->
			<InsuYear><xsl:value-of select="//App/Req/Risks/InsuDueDate"/></InsuYear>
			<!-- ������ȡ��ʽ -->
			<BonusGetMode></BonusGetMode>
			<!-- ���� --> <!-- ������ȡ����ȡ��ʽ -->
			<FullBonusGetMode></FullBonusGetMode>
			<!-- ���� --> <!-- ��ȡ�������ڱ�־ -->
			<GetYearFlag></GetYearFlag>
			<!-- ���� --> <!-- ��ȡ���� -->
			<GetYear></GetYear>
			<!-- ũ�в��� -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
		</xsl:if>
</xsl:template>


<!--������Ϣ-->
<xsl:template match="App/Req/Loan">
		<Loan>
			<!-- �����ͬ�� -->
			<LoanNo><xsl:value-of select="ContNo"/></LoanNo>
			<!-- ������� -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
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
			<!-- ���������� -->
			<InsuEndDate><xsl:value-of select="/ABCB2I/App/Req/Risks/RiskEndDate"/></InsuEndDate>
		</Loan>
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
		<xsl:when test=".=00">00</xsl:when> <!-- ����              -->
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
		<xsl:when test=".=16">06</xsl:when> <!--���               -->
		<xsl:when test=".=17">06</xsl:when> <!--���               -->
		<xsl:when test=".=18">06</xsl:when> <!--�ܵ�               -->
		<xsl:when test=".=19">06</xsl:when> <!--����               -->
		<xsl:when test=".=20">06</xsl:when> <!--����               -->
		<xsl:when test=".=21">06</xsl:when> <!--����               -->
		<xsl:when test=".=22">06</xsl:when> <!--����               -->
		<xsl:when test=".=23">06</xsl:when> <!--��ĸ               -->
		<xsl:when test=".=24">06</xsl:when> <!--��ϱ               -->
		<xsl:when test=".=25">06</xsl:when> <!--Ů��               -->
		<xsl:when test=".=26">06</xsl:when> <!--��������        -->
		<xsl:when test=".=27">06</xsl:when> <!--ͬ��     -->
		<xsl:when test=".=28">06</xsl:when> <!--����     -->
		<xsl:when test=".=29">06</xsl:when> <!--����     -->
		<xsl:when test=".=30">06</xsl:when> <!--����     -->
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
		<xsl:when test=".=110001">0</xsl:when>	<!-- ���֤ -->
		<xsl:when test=".=110002">0</xsl:when>	<!-- �غž������֤-->
		<xsl:when test=".=110003">C</xsl:when>	<!-- ��ʱ�������֤ -->
		<xsl:when test=".=110004">C</xsl:when>	<!-- �غ���ʱ�������֤ -->
		<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
		<xsl:when test=".=110006">4</xsl:when>  <!-- �غŻ��ڲ�  -->
		<xsl:when test=".=110007">2</xsl:when>  <!-- �й������ž��������֤  -->
		<xsl:when test=".=110008">2</xsl:when>  <!-- �غ��й������ž��������֤  -->
		<xsl:when test=".=110009">D</xsl:when>  <!-- �й�������װ�������֤��  -->
		<xsl:when test=".=110010">D</xsl:when>  <!-- �غ��й�������װ�������֤��  -->
		<xsl:when test=".=110011">8</xsl:when>  <!-- ���ݸɲ�����֤ -->
		<xsl:when test=".=110012">8</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
		<xsl:when test=".=110013">8</xsl:when>  <!-- ��������֤ -->
		<xsl:when test=".=110014">8</xsl:when>  <!-- �غž�������֤ -->
		<xsl:when test=".=110015">8</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
		<xsl:when test=".=110016">8</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
		<xsl:when test=".=110017">5</xsl:when>  <!-- ����ԺУѧԱ֤ -->
		<xsl:when test=".=110018">5</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
		<xsl:when test=".=110019">8</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110020">8</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110021">E</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
		<xsl:when test=".=110022">E</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
		<xsl:when test=".=110023">1</xsl:when>  <!-- �л����񹲺͹����� -->
		<xsl:when test=".=110024">1</xsl:when>  <!-- �غ��л����񹲺͹����� -->
		<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
		<xsl:when test=".=110026">1</xsl:when>  <!-- �غ�������� -->
		<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110028">2</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110029">8</xsl:when>  <!-- ��ְ�ɲ�֤ -->
		<xsl:when test=".=110030">8</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
		<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110032">D</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110033">A</xsl:when>  <!-- ����ʿ��֤ -->
		<xsl:when test=".=110034">A</xsl:when>  <!-- �غž���ʿ��֤ -->
		<xsl:when test=".=110035">A</xsl:when>  <!-- �侯ʿ��֤ -->
		<xsl:when test=".=110036">A</xsl:when>  <!-- �غ��侯ʿ��֤ -->
		<xsl:when test=".=119998">8</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
		<xsl:when test=".=119999">8</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
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
		<xsl:when test=".=1">0</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">1</xsl:when>	<!-- �½� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
		<xsl:when test=".=4">6</xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=5">12</xsl:when>	<!-- �꽻 -->
		<xsl:when test=".=0">-1</xsl:when>	<!-- ������ -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="tran_payendyearflag" match="PayDueType">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- ����ĳȷ������ -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- ����-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- �� -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- �� -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<xsl:template name="tran_payendyearflag1" match="PayDueType1">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- ����ĳȷ������ -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- ����-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- �� -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- �� -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
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
		<xsl:when test=".=5">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">M</xsl:when>	<!-- �� -->
		<xsl:when test=".=1">D</xsl:when>	<!-- �� -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- �� -->
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
		<xsl:when test=".=1">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">M</xsl:when>	<!-- �� -->
		<xsl:when test=".=3">D</xsl:when>	<!-- �� -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- �� -->
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
		<xsl:when test=".=2">1</xsl:when>	<!-- ������Ϣ -->
		<xsl:when test=".=1">3</xsl:when>	<!-- �ֽ����� -->
		<xsl:when test=".=0">2</xsl:when>	<!-- �ֽ���ȡ -->
		<xsl:otherwise>1</xsl:otherwise>  
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

<!-- Ͷ���˾������� -->
<xsl:template name="cust_Source" match="CustSource">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">2</xsl:when>	<!-- ũ�� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ����  -->
<xsl:template name="tran_nativeplace" match="nativeplace">
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