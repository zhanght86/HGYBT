<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="Req">
	  <TranData>
	     <Head>
	        <!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- ������� -->
			<NodeNo>
			<xsl:value-of select="ZoneNo"/>
			<xsl:value-of select="BrNo"/>
			</NodeNo>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="BankDate"/></TranDate>
	  		<!-- ����ʱ�� ũ�в�������ʱ�� ȡϵͳ��ǰʱ�� -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- ���н���ʱ�� -->
			<!-- <TranTime><xsl:value-of select="BankTime"/></TranTime> -->
			<TranCom><xsl:value-of select="BankCode"></xsl:value-of></TranCom>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--Ͷ����Ϣ-->
		<Body>
			<xsl:variable name ="sAccNo" select ="Base/AccNo"/>
			<!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="Base/ProposalContNo"/></ProposalPrtNo>
			<!-- ����ӡˢ�� -->
			<ContPrtNo><xsl:value-of select="Base/PrtNo"/></ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="Base/PolApplyDate"/></PolApplyDate>
			<!-- �˻����� -->
			<!--
			<AccName><xsl:value-of select="Appl/Name"/></AccName>
			-->
			<!-- �����ʺ����� ���ڽɷ��˺�����ReAccName�����ڽɷ��˺�����AccName ��ũ�е�������Ա��ͨ�� -->
			<AccName><xsl:value-of select="Base/AccName"/></AccName>
			<!-- �˻����д��� -->
			<AccBankCode/>
			<!-- �����ʺ� ���ڽɷ��˺� BankAccNo�����ڽɷ��˺�AccNo ��ũ�е�������Ա��ͨ��20130402 -->
			<AccNo><xsl:value-of select="Base/AccNo"/></AccNo>
			<!--
			<xsl:if test="$sAccNo = ''">
			<AccNo>0000</AccNo>
			</xsl:if>
			<xsl:if test="$sAccNo != ''">
			<AccNo><xsl:value-of select="Base/AccNo"/></AccNo>
			</xsl:if>
			-->
			<!--�ɷ���ʽ ����Ϲ�ͨ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
			<PayMode>B</PayMode>
			<!-- �������ͷ�ʽ -->
			<GetPolMode>1</GetPolMode><!-- Ĭ��Ϊ1������д����1 -->
			<!-- ְҵ��֪ -->
			<JobNotice/>
			<!-- ������֪ -->
			<HealthNotice><xsl:apply-templates select="Risks/Risk[Code=MainRiskCode]/HealthFlag"/></HealthNotice>
			<!-- Ͷ���� -->
			<xsl:apply-templates select="Appl"/>
			<!-- ������ -->
			<xsl:apply-templates select="Insu"/>
			<!-- ������ -->
			<xsl:apply-templates select="Bnfs"/>
			<!-- ������Ϣ -->
			<xsl:apply-templates select="Risks"/>
			<!-- ������Ϣ -->
			<xsl:apply-templates select="Loan"/>
		</Body>
	</TranData>
</xsl:template>	
	
<!--Ͷ������Ϣ-->
<xsl:template match="Appl">
		<Appnt>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- ���� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDType"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDNo"/></IDNo>
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
			<YearSalary/>
			<DenType><xsl:value-of select="PbDenType" /></DenType>
			<!-- ֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- ��� -->
			<MaritalStatus/>
				<!-- Ͷ�����뱳���˹�ϵ -->
		<RelaToInsured><xsl:apply-templates select="RelaToInsured"/></RelaToInsured>
		</Appnt>
</xsl:template>
		
<!--��������Ϣ-->
<xsl:template match="Insu">
		<Insured>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- ���� -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDType"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDNo"/></IDNo>
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
			<YearSalary/>
			<!-- ֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- ��� -->
			<MaritalStatus/>
		</Insured>
</xsl:template>

<!--������-->
<xsl:template match="Bnfs">
	<xsl:variable name ="count" select ="Count"/>
	<xsl:choose>
	<xsl:when test="$count!=0">
		<xsl:for-each select="Bnf">
			<Bnf>
			<BeneficType>N</BeneficType>
				<!-- ���������� ũ�кͺ��ĵ�һ��   0-���������ˣ�1-���������-->
				<Type><xsl:value-of select="Type"/></Type>
				<!-- ����˳�� -->
				<Grade><xsl:value-of select="BnfGrade"/></Grade>
				<!-- ���� -->
				<Name><xsl:value-of select="Name"/></Name>
				<!-- �Ա� -->
				<Sex><xsl:apply-templates select="Sex"/></Sex>
				<!-- ���� -->
				<Birthday><xsl:value-of select="Birthday"/></Birthday>
				<!-- ֤������ -->
				<IDType><xsl:apply-templates select="IDType"/></IDType>
				<!-- ֤������ -->
				<IDNo><xsl:value-of select="IDNo"/></IDNo>
				<!-- ֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
				<xsl:choose>
					<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
					<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!--Ͷ�����뱳���˹�ϵ   -->
				<RelaToInsured><xsl:apply-templates select="RelationToInsured"/></RelaToInsured>
				<!-- ������� -->
				<Lot><xsl:value-of select="BnfLot"/></Lot>
			</Bnf>
		</xsl:for-each>
	</xsl:when>
	<xsl:otherwise>
	</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--������Ϣ-->
<xsl:template match="Risks">
	<xsl:variable name="MainProductCode" select="Risk[Code = MainRiskCode]/Code"/>
	<xsl:for-each select="Risk[Code = MainRiskCode]">
		<Risk>
		    <!-- ���ִ��� -->
			<RiskCode><xsl:apply-templates select="Code"/></RiskCode>
			<!-- �������ִ��� -->
			<MainRiskCode><xsl:apply-templates select="MainRiskCode"/></MainRiskCode>
			<!-- ���� -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt)"/></Amnt>
			<!-- ���� -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></Prem>
			<!-- Ͷ������ -->
			<Mult><xsl:value-of select="Mult"/></Mult>
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
			
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			
			<xsl:choose>
				<xsl:when test="PayIntv=1"><!-- ������1000Y -->
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
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- �������������־ -->
			<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
			<!-- ������������ -->
			<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
			<!-- ������ȡ��ʽ -->
			<BonusGetMode><xsl:apply-templates select="BonusGetMode"/></BonusGetMode>
			<!-- ���� --> <!-- ������ȡ����ȡ��ʽ -->
			<FullBonusGetMode><xsl:apply-templates select="FullBonusGetMode"/></FullBonusGetMode>
			<!-- ���� --> <!-- ��ȡ�������ڱ�־ -->
			<GetYearFlag><xsl:apply-templates select="GetYearFlag"/></GetYearFlag>
			<!-- ���� --> <!-- ��ȡ���� -->
			<GetYear><xsl:value-of select="GetYear"/></GetYear>
			<!-- ũ�в��� -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
	</xsl:for-each>
	<xsl:for-each select="Risk[Code != MainRiskCode]">
		<Risk>
		    <!-- ���ִ��� -->
			<RiskCode><xsl:apply-templates select="Code"/></RiskCode>
			<!-- �������ִ��� -->
			<MainRiskCode><xsl:apply-templates select="MainRiskCode"/></MainRiskCode>
			<!-- ���� -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt)"/></Amnt>
			<!-- ���� -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></Prem>
			<!-- Ͷ������ -->
			<Mult><xsl:value-of select="Mult"/></Mult>
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
			
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			<xsl:choose>
				<xsl:when test="PayIntv=1"><!-- ������1000Y -->
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
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>			
				<!-- �ɷ��������� -->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			
			<!-- �������������־ -->
			<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
			<!-- ������������ -->
			<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
			<!-- ������ȡ��ʽ -->
			<BonusGetMode><xsl:apply-templates select="BonusGetMode"/></BonusGetMode>
			<!-- ���� --> <!-- ������ȡ����ȡ��ʽ -->
			<FullBonusGetMode><xsl:apply-templates select="FullBonusGetMode"/></FullBonusGetMode>
			<!-- ���� --> <!-- ��ȡ�������ڱ�־ -->
			<GetYearFlag><xsl:apply-templates select="GetYearFlag"/></GetYearFlag>
			<!-- ���� --> <!-- ��ȡ���� -->
			<GetYear><xsl:value-of select="GetYear"/></GetYear>
			<!-- ũ�в��� -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
	</xsl:for-each>
</xsl:template>


<!--������Ϣ-->
<xsl:template match="Loan">
		<Loan>
			<!-- �����ͬ�� -->
			<LoanNo><xsl:value-of select="LoanNo"/></LoanNo>
			<!-- ������� -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
			<!-- �������� -->
			<LoanDate><xsl:value-of select="LoanDate"/></LoanDate>
			<!-- ������� -->
			<LoanEndDate><xsl:value-of select="LoanEndDate"/></LoanEndDate>
			<!-- �������� -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- �����˺� -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- ������ -->
			<xsl:choose><!-- �����ж�choose�£���Ȼ�յĻ���yuanToFen��ʱ����0�ˣ����ݲ�ԭ֭ԭζ�� -->
			<xsl:when test = "LoanPrem=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LoanPrem)"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- ������ʼ�� -->
			<InsuDate><xsl:value-of select="InsuDate"/></InsuDate>
			<!-- ���������� -->
			<InsuEndDate><xsl:value-of select="InsuEndDate"/></InsuEndDate>
		</Loan>
</xsl:template>


<!--������� -->
<xsl:template name="tran_health" match="HealthFlag">
	<xsl:choose>
		<xsl:when test=".=0">N</xsl:when>	<!-- �� -->
		<xsl:when test=".=1">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="RelationToInsured" match="RelationToInsured"> 
 <xsl:choose>
	<xsl:when test=".=1">00</xsl:when> <!-- ����    -->
		<xsl:when test=".=2">02</xsl:when> <!-- �ɷ�    -->
		<xsl:when test=".=3">02</xsl:when> <!-- ����    -->
		<xsl:when test=".=4">01</xsl:when> <!-- ����    -->
		<xsl:when test=".=5">01</xsl:when> <!-- ĸ��    -->
		<xsl:when test=".=6">03</xsl:when> <!-- ����    -->
		<xsl:when test=".=7">03</xsl:when> <!-- Ů��    -->
		<xsl:when test=".=8">04</xsl:when> <!-- �游    -->
		<xsl:when test=".=9">04</xsl:when> <!-- ��ĸ    -->
		<xsl:when test=".=10">04</xsl:when> <!--����     -->
		<xsl:when test=".=11">04</xsl:when> <!--��Ů     -->
		<xsl:when test=".=12">04</xsl:when> <!--���游   -->
		<xsl:when test=".=13">04</xsl:when> <!--����ĸ   -->
		<xsl:when test=".=14">04</xsl:when> <!--����     -->
		<xsl:when test=".=15">04</xsl:when> <!--����Ů   -->
		<xsl:when test=".=16">06</xsl:when> <!--���     -->
		<xsl:when test=".=17">06</xsl:when> <!--���     -->
		<xsl:when test=".=18">06</xsl:when> <!--�ܵ�     -->
		<xsl:when test=".=19">06</xsl:when> <!--����     -->
		<xsl:when test=".=20">06</xsl:when> <!--����     -->
		<xsl:when test=".=21">06</xsl:when> <!--����     -->
		<xsl:when test=".=22">06</xsl:when> <!--����     -->
		<xsl:when test=".=23">06</xsl:when> <!--��ĸ     -->
		<xsl:when test=".=24">06</xsl:when> <!--��ϱ     -->
		<xsl:when test=".=25">06</xsl:when> <!--Ů��     -->
		<xsl:when test=".=26">06</xsl:when> <!--�������� -->
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
<xsl:template name="tran_idtype" match="IDType">
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
<xsl:template name="Code" match="Code">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �����ִ��� -->
<xsl:template name="MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when> 	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>		<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


<!-- �ɷѷ�ʽ��Ƶ�Σ� -->
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">1</xsl:when>	<!-- �½� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
		<xsl:when test=".=4">6</xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=5">12</xsl:when>	<!-- �꽻 -->
		<xsl:when test=".=6">-1</xsl:when>	<!-- ������ -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- �ɷ������������� -->
<xsl:template name="tran_payendyearflag" match="PayEndYearFlag">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- ����ĳȷ������ -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- ����-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- �� -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- �� -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<!-- ���������������� -->
<xsl:template name="tran_insuyearflag" match="InsuYearFlag">
	<xsl:choose>
		<xsl:when test=".=1">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">M</xsl:when>	<!-- �� -->
		<xsl:when test=".=3">D</xsl:when>	<!-- �� -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- �� -->
		<xsl:when test=".=5">A</xsl:when>	<!-- ���� ?��ȷ�ϣ����������������ͺ��ı������Ӧ�����Ƕ���-->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ��ȡ�������ڱ�־ -->
<xsl:template name="tran_getYearFlag" match="GetYearFlag">
	<xsl:choose>
		<xsl:when test=".=1">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".=2">M</xsl:when>	<!-- �� -->
		<xsl:when test=".=3">D</xsl:when>	<!-- �� -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- �� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ������ȡ��ʽ -->
<xsl:template name="tran_bonusgetmode" match="BonusGetMode">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when>	<!-- �޺��� -->
		<xsl:when test=".=1">1</xsl:when>	<!-- ������Ϣ -->
		<xsl:when test=".=2">2</xsl:when>	<!-- ��ȡ�ֽ� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- �ֽ����� -->
		<xsl:when test=".=4">4</xsl:when>	<!-- ���� -->
		<xsl:when test=".=5">5</xsl:when>	<!-- ����� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>


<!-- ��ϵ���� -->
<xsl:template name="tran_relatoinsured" match="RelaToInsured">
	<xsl:choose>
		<xsl:when test=".=1">00</xsl:when> <!-- ����    -->
		<xsl:when test=".=2">02</xsl:when> <!-- �ɷ�    -->
		<xsl:when test=".=3">02</xsl:when> <!-- ����    -->
		<xsl:when test=".=4">01</xsl:when> <!-- ����    -->
		<xsl:when test=".=5">01</xsl:when> <!-- ĸ��    -->
		<xsl:when test=".=6">03</xsl:when> <!-- ����    -->
		<xsl:when test=".=7">03</xsl:when> <!-- Ů��    -->
		<xsl:when test=".=8">04</xsl:when> <!-- �游    -->
		<xsl:when test=".=9">04</xsl:when> <!-- ��ĸ    -->
		<xsl:when test=".=10">04</xsl:when> <!--����     -->
		<xsl:when test=".=11">04</xsl:when> <!--��Ů     -->
		<xsl:when test=".=12">04</xsl:when> <!--���游   -->
		<xsl:when test=".=13">04</xsl:when> <!--����ĸ   -->
		<xsl:when test=".=14">04</xsl:when> <!--����     -->
		<xsl:when test=".=15">04</xsl:when> <!--����Ů   -->
		<xsl:when test=".=16">06</xsl:when> <!--���     -->
		<xsl:when test=".=17">06</xsl:when> <!--���     -->
		<xsl:when test=".=18">06</xsl:when> <!--�ܵ�     -->
		<xsl:when test=".=19">06</xsl:when> <!--����     -->
		<xsl:when test=".=20">06</xsl:when> <!--����     -->
		<xsl:when test=".=21">06</xsl:when> <!--����     -->
		<xsl:when test=".=22">06</xsl:when> <!--����     -->
		<xsl:when test=".=23">06</xsl:when> <!--��ĸ     -->
		<xsl:when test=".=24">06</xsl:when> <!--��ϱ     -->
		<xsl:when test=".=25">06</xsl:when> <!--Ů��     -->
		<xsl:when test=".=26">06</xsl:when> <!--�������� -->
		<xsl:when test=".=27">06</xsl:when> <!--ͬ��     -->
		<xsl:when test=".=28">06</xsl:when> <!--����     -->
		<xsl:when test=".=29">06</xsl:when> <!--����     -->
		<xsl:when test=".=30">06</xsl:when> <!--����     -->
		<xsl:otherwise>--</xsl:otherwise>
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
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>