<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	
<xsl:template match="REQUEST">
	  <TranData><!-- ����¼���Ժ������� -->
	     <Head>
	  		<!-- �������� -->
	  		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="string(java:com.sinosoft.midplat.common.DateUtil.getCur6Time())"/></TranTime>
			<!-- ���д��� -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
			<!-- �������� -->
			<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
			<!-- ������ˮ�� -->
			<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
			<!-- YBT��֯�Ľڵ���Ϣ -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- ��������-->
			<SaleChannel>0</SaleChannel>
			<!-- ����˳���- ��ʱ���� -->
			<ApplyNo />
			<xsl:variable name="sAccNo" select="BUSI/CONTENT" />
		  	<!-- Ͷ����(ӡˢ)�� -->
			<ProposalPrtNo>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/MAIN/APPNO)"/>
			</ProposalPrtNo>  
		   	<!-- ������ͬӡˢ�� (��֤)  -->
			<ContPrtNo>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/BILL/BILL_USED)"/>
			</ContPrtNo>
		   <!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="BUSI/CONTENT/MAIN/APPDATE"/></PolApplyDate>
		   <!-- �������ͷ�ʽ Ĭ��ȫΪ1-->
			<GetPolMode>1</GetPolMode><!-- Ĭ��Ϊ1������д����1 -->
		   <!-- ������֪(N/Y) -->
			<HealthNotice><xsl:value-of select="BUSI/CONTENT/HEALTH/NOTICE"/></HealthNotice>
		   <!-- �����˻����� -->
		   <AccName><xsl:value-of select="BUSI/CONTENT/TBR/NAME" /></AccName>
		   <!-- ���������˻� -->
		   <AccNo><xsl:value-of select="BUSI/CONTENT/MAIN/PAYACC"/></AccNo>
		    <!-- ������Ա����  -->
			<SaleName/>
		   	<!-- ������Ա���� -->
			<SaleStaff><xsl:value-of select="//DIST/TELLER"/></SaleStaff>
            <!-- ������Ա�ʸ�֤��  -->
            <SaleCertNo/>
			<!-- Ͷ���� -->
			<xsl:apply-templates select="BUSI/CONTENT/TBR"/>
			<!-- ������ -->
			<xsl:apply-templates select="BUSI/CONTENT/BBR"/>
			<!-- ������ -->
			<xsl:apply-templates select="BUSI/CONTENT/SYR"/>
			<!-- ������Ϣ -->
			<xsl:for-each select="BUSI/CONTENT/PTS/PT">
			    <Risk>
			    	<!-- ���ִ��� -->
					<RiskCode><xsl:value-of select="ID"/></RiskCode>
					<!-- �������ִ��� ��ȷ�� -->
					<MainRiskCode><xsl:value-of select="ID"/></MainRiskCode>
					<!-- ����(��)  -->
					<Amnt><xsl:value-of select="AMNT"/></Amnt>
					<!-- ���շ�(��) -->
					<Prem><xsl:value-of select="PREMIUM"/></Prem>
					<!-- Ͷ������ -->
					<Mult>
						<xsl:value-of select="UNIT"/>
					</Mult>
					<!-- �ɷ���ʽ -->
					<PayMode>B</PayMode>
					<!-- �ɷ�Ƶ�� -->
					<PayIntv>
						<xsl:call-template name="tran_payintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="CRG_T"/>
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- �������������־ -->
					<InsuYearFlag><xsl:apply-templates select="COVER_T"/></InsuYearFlag>
					<!-- ������������ -->
					<InsuYear><xsl:value-of select="COVER_Y"/></InsuYear>
					<xsl:choose>
						<xsl:when test="CRG_T = '1'"><!-- ������1000Y -->
							<!-- �ɷ����������־ -->
							<PayEndYearFlag>Y</PayEndYearFlag>			
							<!-- �ɷ��������� -->
							<PayEndYear>1000</PayEndYear>
						</xsl:when>
						<xsl:when test="CRG_T = '2' or CRG_T='7'"><!-- �꽻��2Y -->
							<!-- �ɷ����������־ -->
							<PayEndYearFlag><xsl:apply-templates select="CRG_T"/></PayEndYearFlag>			
							<!-- �ɷ��������� -->
							<PayEndYear><xsl:value-of select="CRG_Y"/></PayEndYear>
						</xsl:when>
					</xsl:choose>
					<!-- ������ȡ��ʽ -->
					<BonusGetMode><xsl:apply-templates select="HLLQ_T"/></BonusGetMode>
					<!-- ������ȡ����ȡ��ʽ -->
					<FullBonusGetMode><xsl:apply-templates select="DRAW_T" /></FullBonusGetMode>
					<!-- ��ȡ�������ڱ�־ -->
					<GetYearFlag><xsl:apply-templates select="DRAW_T" /></GetYearFlag>
					<!-- ��ȡ���� -->
					<xsl:variable name="Start" select="DRAW_FST" />
					<xsl:variable name="End" select="DRAW_LST" />
					<xsl:if test="string-length($Start) != 0 and string-length($End) !=0">
						<GetYear><xsl:value-of select="$Start - $End" /></GetYear>
					</xsl:if>
					<xsl:if test="string-length($Start) = 0 or string-length($End) =0">
						<GetYear>0</GetYear>
					</xsl:if>
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
			</xsl:for-each>
						
			<!--�ɷ���ʽ ����Ϲ�ͨ��������ͨ�������к���߽ɷ���ʽ����ΪB -���ж�Ӧ����˵��-->
			<PayMode>B</PayMode>
			<!-- ְҵ��֪ -->
			<JobNotice/>
		</Body>
	</TranData>
</xsl:template>	

<xsl:template match="BUSI/CONTENT/TBR">
		<Appnt>
			<!-- ���� -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- �������� -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			<!-- ְҵ���� -->
			<JobCode><xsl:apply-templates select="Occupation"/></JobCode>
			<!-- ���� -->
			<Nationality><xsl:apply-templates select="COUNTRY_CODE"/></Nationality>
			<!-- ���(cm)  ��ֵ -->
			<Stature/>
			<!-- ����(kg)  -->
			<Weight/>
			<!-- ���(N/Y) ��ֵ -->
			<MaritalStatus><xsl:apply-templates select="marriage"/></MaritalStatus>
			<!-- ������(��Ԫ) -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(INCOME)"/></YearSalary>
			<!-- Ͷ���˼�ͥ������ -->
			<FamilyYearSalary/>
			<!-- ���֤֤����Ч�ڸ�ʽ:yyyyMMdd --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
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
			<Address><xsl:value-of select="ADDR"/></Address>
			<!-- Ͷ������������ -->
			<ZipCode><xsl:value-of select="ZIP"/></ZipCode>
			<!-- �ƶ��绰 -->
			<Mobile><xsl:value-of select="MP"/></Mobile>
			<!-- ��ͥ�绰 -->
			<Phone><xsl:value-of select="TEL"/></Phone>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="EMAIL"/></Email>
			<!-- Ͷ�����뱻���˹�ϵ -->
			<RelaToInsured><xsl:apply-templates select="BBR_RELA"/></RelaToInsured>
			<!-- Ͷ���˾������� -->
		 	<DenType></DenType>
			
		</Appnt>
</xsl:template>
		
<!--��������Ϣ-->
<xsl:template match="BUSI/CONTENT/BBR">
		<Insured>
			<!-- ���� -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- �������� -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			<!-- ְҵ���� -->
			<JobCode><xsl:apply-templates select="Occupation"/></JobCode>
			<!-- ���� -->
			<Nationality><xsl:apply-templates select="COUNTRY_CODE"/></Nationality>
			<!-- ���(cm)  ��ֵ -->
			<Stature/>
			<!-- ����(g)  ��ֵ -->
			<Weight/>
			<!-- ���(N/Y) ��ֵ -->
			<MaritalStatus><xsl:apply-templates select="marriage"/></MaritalStatus>
			<!-- ������(��Ԫ) -->
			<!-- ���ݽ��������Ĳ�ͬ������ʾ��ʽ��ͬ -->
			<YearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(INCOME)"/>
			</YearSalary>
			<!-- ���֤֤����Ч�� --><!-- 8λ���ڣ�������ЧΪ20991231 -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- �����˵�ַ -->
			<Address><xsl:value-of select="ADDR"/></Address>
			<!-- �ʱ� -->
			<ZipCode><xsl:value-of select="ZIP"/></ZipCode>
			<!-- �ƶ��绰 -->
			<Mobile><xsl:value-of select="MP"/></Mobile>
			<!-- �̶��绰 -->
			<Phone><xsl:value-of select="TEL"/></Phone>
			<!-- �����ʼ� -->
			<Email><xsl:value-of select="EMAIL"/></Email>
			
		</Insured>
</xsl:template>

<xsl:template match="BUSI/CONTENT/SYR">
		<Bnf>
			<!-- ��������� (0���棬1��ʣ�2����) -->
			<Type>0</Type>
			<!-- ����˳�� -->
			<Grade><xsl:value-of select="ORDER"/></Grade>
			<!-- ���� -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- �Ա� -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- �������� -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			 <!-- �������뱻���˹�ϵ -->
			<RelaToInsured><xsl:apply-templates select="BBR_RELA"/></RelaToInsured>
			<!-- ������� -->
			<Lot><xsl:value-of select="RATIO"/></Lot>
			<!-- ���֤֤����Ч�� -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<BeneficType>N</BeneficType>
		</Bnf>
</xsl:template>

<!-- �������ͷ�ʽ -->
<xsl:template>
	<xsl:choose>
		<xsl:when test=".=1"></xsl:when><!-- ���ŷ��� -->
		<xsl:when test=".=2">1</xsl:when><!-- �ʼ� -->
		<xsl:when test=".=3"></xsl:when><!-- ���ŵ��� -->
		<xsl:when test=".=4">0</xsl:when><!-- ���й�̨ -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ��ϵ���� -->
<xsl:template name="tran_relatoinsured" match="BBR_RELA">
	<xsl:choose>
		<xsl:when test=".=1">02</xsl:when><!-- ��ż -->
		<xsl:when test=".=2">01</xsl:when><!-- ��ĸ -->
		<xsl:when test=".=3">03</xsl:when><!-- ��Ů -->
		<xsl:when test=".=4"></xsl:when><!-- ���� -->
		<xsl:when test=".=5">00</xsl:when><!-- ���� -->
		<xsl:when test=".=6">06</xsl:when><!-- ���� -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- �Ա� -->
<xsl:template name="tran_sex" match="SEX">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- �� -->
		<xsl:when test=".=2">1</xsl:when>	<!-- Ů -->
		<xsl:when test=".=3"></xsl:when>	<!-- ��ȷ�� -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>


<!-- ֤������-->
<xsl:template name="tran_idtype" match="IDTYPE">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- ���֤ -->
		<xsl:when test=".=2">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=3">2</xsl:when>	<!-- ����֤ -->
		<xsl:when test=".=4">D</xsl:when>	<!-- �侯֤ -->
		<xsl:when test=".=5">F</xsl:when>	<!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=6">4</xsl:when>	<!-- ���ڲ� -->
		<xsl:when test=".=7"></xsl:when>	<!-- ���� -->
		<xsl:when test=".=8">D</xsl:when>	<!-- ����֤ -->
		<xsl:when test=".=9"></xsl:when>	<!-- ִ�й���֤ -->
		<xsl:when test=".=A">2</xsl:when>	<!-- ʿ��֤ -->
		<xsl:when test=".=B">F</xsl:when>	<!-- ̨��ͬ�������ڵ�ͨ��֤ -->
		<xsl:when test=".=C">0</xsl:when>	<!-- ��ʱ���֤ -->
		<xsl:when test=".=D"></xsl:when>	<!-- ����˾���֤ -->
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
<xsl:template name="tran_payintv" >
	<xsl:param name="payintv"/>
	<xsl:choose>
		<xsl:when test="$payintv=1">0</xsl:when>	<!-- ���� -->
		<xsl:when test="$payintv=2">12</xsl:when>	<!-- �꽻 -->
		<xsl:when test="$payintv=3">6</xsl:when>	<!-- ���꽻 -->
		<xsl:when test="$payintv=4">3</xsl:when>	<!-- ���� -->
		<xsl:when test="$payintv=5">1</xsl:when>	<!-- �½� -->
		<xsl:when test="$payintv=6">12</xsl:when>	<!-- �꽻 -->
		<xsl:when test="$payintv=8">-1</xsl:when>	<!-- �����ڽ� -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- �ɷ������������� -->
<xsl:template name="tran_payendyearflag" match="CRG_T">
	<xsl:choose>
		<xsl:when test=".='0'"></xsl:when>	<!-- �޹�-->
		<xsl:when test=".='1'">A</xsl:when>	<!-- ���� -->
		<xsl:when test=".='2'">Y</xsl:when>	<!-- ��� -->
		<xsl:when test=".='3'"></xsl:when>	<!-- ����� -->
		<xsl:when test=".='4'"></xsl:when>	<!-- ���� -->
		<xsl:when test=".='5'">M</xsl:when><!-- �½� -->
		<xsl:when test=".='6'">A</xsl:when><!-- ����ĳȷ������ -->
		<xsl:when test=".='7'"></xsl:when><!-- �����ɷ� -->
		<xsl:when test=".='8 '"></xsl:when><!-- �����ڽ� -->
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
<xsl:template name="tran_insuyearflag" match="COVER_T">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- �޹� -->
		<xsl:when test=".=1"></xsl:when>	<!-- ������ -->
		<xsl:when test=".=2">Y</xsl:when>	<!-- �����ޱ� -->
		<xsl:when test=".=3">A</xsl:when><!-- ����ĳȷ������ -->
		<xsl:when test=".=4">M</xsl:when>	<!-- ���±� -->
		<xsl:when test=".=5">D</xsl:when>	<!-- ���챣 -->
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
		<xsl:when test=".=1">A</xsl:when>	<!-- ������ -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ������ȡ��ʽ
 0	ֱ�Ӹ���
1	�ֽ�����
2	�ۻ���Ϣ
3	�����
 -->
<xsl:template name="tran_bonusgetmode" match="HLLQ_T">
		<xsl:choose>
		<xsl:when test=".=1">1</xsl:when>	<!-- ������Ϣ -->
		<xsl:when test=".=2">2</xsl:when>	<!-- �ֽ���ȡ -->
		<xsl:when test=".=3">3</xsl:when>	<!-- �ֽ����շ�   -->
		<xsl:when test=".=4">5</xsl:when>	<!-- ��������   -->
		<xsl:when test=".=''"></xsl:when><!-- ����Ʒû�к�����ȡ��ʽ -->
		<xsl:otherwise>4</xsl:otherwise><!-- ���� -->
	</xsl:choose>
</xsl:template>

<!-- ������ȡ����ȡ��ʽ -->
<xsl:template name="tran_FullBonusGetMode" match="DRAW_T">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- �޹� -->
		<xsl:when test=".=1">Y</xsl:when><!-- ���� -->
		<xsl:when test=".=2"></xsl:when><!-- ������ -->
		<xsl:when test=".=3"></xsl:when><!-- ���� -->
		<xsl:when test=".=4"></xsl:when><!-- ���� -->
		<xsl:when test=".=5"></xsl:when><!-- ���� -->
		<xsl:otherwise></xsl:otherwise>
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

<xsl:template name="tran_jobcode" match="Occupation">
	<xsl:choose>
		<xsl:when test=".=2113001">2099904</xsl:when><!-- ���� -->
		<xsl:when test=".=9999999 "></xsl:when><!-- 9999999999 -->
		<xsl:when test=".=0000003">8000002</xsl:when><!-- ��������Ա -->
		<xsl:when test=".=0000001">2099907</xsl:when><!-- ��ͯ��18��ǰѧ�� -->
		<xsl:when test=".=0002001"></xsl:when><!-- �����ˣ���������ҵ�� -->
		<xsl:when test=".=0805001">2021106</xsl:when><!-- ����ʦ -->
		<xsl:when test=".=0805002">6030516</xsl:when><!-- ��ʦ -->
		<xsl:when test=".=0601004">4040701</xsl:when><!-- ������ -->
		<xsl:when test=".=0703005"></xsl:when><!-- һ�����ڡ����� -->
		<xsl:when test=".=0810012">6050712</xsl:when><!-- 	��ࡢ�๤ -->
		<xsl:when test=".=0402001">6010316</xsl:when><!-- ��Ӫ�ߣ������ֳ��ߣ� -->
		<xsl:when test=".=0402002">6010317</xsl:when><!-- ��Ӫ�ߣ��ֳ��ල�ߣ� -->
		<xsl:when test=".=0405001">6010608</xsl:when><!-- ������Ա -->
		<xsl:when test=".=0602002"></xsl:when><!-- һ�����ڹ�����Ա -->
		<xsl:when test=".=0603001">4030503</xsl:when><!-- ������Ա -->
		<xsl:when test=".=0705005">6230710</xsl:when><!-- �а��̡��๤ -->
		<xsl:when test=".=0901001">2129901</xsl:when><!-- ������Ա -->
		<xsl:when test=".=0901002"></xsl:when><!-- ������Ա -->
		<xsl:when test=".=0902002"></xsl:when><!-- ҵ��Ա -->
		<xsl:when test=".=1201001"></xsl:when><!-- ��ʦ -->
		<xsl:when test=".=1201002"></xsl:when><!-- ѧ�� -->
		<xsl:when test=".=1201004">2099902</xsl:when><!-- ��ѵ�̹١�������ʦ -->
		<xsl:when test=".=1602001">2080301</xsl:when><!-- ��ʦ -->
		<xsl:when test=".=1602002">2060301</xsl:when><!-- ���ʦ -->
		<xsl:when test=".=1701001">4071203</xsl:when><!-- ��ͥ���� -->
		<xsl:when test=".=1801001">3020101</xsl:when><!-- ����������������Ա -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ����  -->
<xsl:template name="tran_Country" match="COUNTRY_CODE">
	<xsl:choose>
		<xsl:when test=".=ALB">ALB</xsl:when><!-- ���������� -->
		<xsl:when test=".=DZA">ALG</xsl:when><!-- ���������� -->
		<xsl:when test=".=AFG">AFG</xsl:when><!-- ������ -->
		<xsl:when test=".=ARG">ARG</xsl:when><!-- ����͢ -->
		<xsl:when test=".=AZE">AZE</xsl:when><!-- �����ݽ� -->
		<xsl:when test=".=EGY">EGY</xsl:when><!-- ���� -->
		<xsl:when test=".=ETH">ETH</xsl:when><!-- ��������� -->
		<xsl:when test=".=IRL">IRL</xsl:when><!-- ������ -->
		<xsl:when test=".=EST">EST</xsl:when><!-- ��ɳ���� -->
		<xsl:when test=".=AND">AND</xsl:when><!-- ������ -->
		<xsl:when test=".=AUT">AUT</xsl:when><!-- �µ��� -->
		<xsl:when test=".=AUS">AUS</xsl:when><!-- �Ĵ����� -->
		<xsl:when test=".=MAC">MO</xsl:when><!-- ���� -->
		<xsl:when test=".=BRB">BAR</xsl:when><!-- �ͰͶ�˹ -->
		<xsl:when test=".=PNG">PNG</xsl:when><!-- �Ͳ����¼����� -->
		<xsl:when test=".=PRY">PAR</xsl:when><!-- ������ -->
		<xsl:when test=".=PSE">PLE</xsl:when><!-- ����˹̹ -->
		<xsl:when test=".=BHR">BRN</xsl:when><!-- ���� -->
		<xsl:when test=".=PAN">PAN</xsl:when><!-- ������ -->
		<xsl:when test=".=BRA">BAS</xsl:when><!-- ���� -->
		<xsl:when test=".=BLR">BLR</xsl:when><!-- �׶���˹ -->
		<xsl:when test=".=BMU">BER</xsl:when><!-- ��Ľ�� -->
		<xsl:when test=".=BGR">BUL</xsl:when><!-- �������� -->
		<xsl:when test=".=BEL">BEL</xsl:when><!-- ����ʱ -->
		<xsl:when test=".=ISL">ISL</xsl:when><!-- ���� -->
		<xsl:when test=".=POL">PL</xsl:when><!-- ���� -->
		<xsl:when test=".=BOL">BOL</xsl:when><!-- ����ά�� -->
		<xsl:when test=".=BIH">BIH</xsl:when><!-- ��˹���Ǻͺ�����ά�� -->
		<xsl:when test=".=BWA">BOT</xsl:when><!-- �������� -->
		<xsl:when test=".=DNK">DEN</xsl:when><!-- ���� -->
		<xsl:when test=".=DEU">DEU</xsl:when><!-- �¹� -->
		<xsl:when test=".=DOM">DOM</xsl:when><!-- ������ӹ��͹� -->
		<xsl:when test=".=RUS">RUS</xsl:when><!-- ����˹ -->
		<xsl:when test=".=ECU">ECU</xsl:when><!-- ��϶�� -->
		<xsl:when test=".=FRA">FRA</xsl:when><!-- ���� -->
		<xsl:when test=".=FRO">FAI</xsl:when><!-- ����Ⱥ�� -->
		<xsl:when test=".=PHL">PHL</xsl:when><!-- ���ɱ� -->
		<xsl:when test=".=FIN">FIN</xsl:when><!-- ���� -->
		<xsl:when test=".=COL">COL</xsl:when><!-- ���ױ��� -->
		<xsl:when test=".=CRI">CRC</xsl:when><!-- ��˹����� -->
		<xsl:when test=".=GEO">GEO</xsl:when><!-- ��³���� -->
		<xsl:when test=".=CUB">CUB</xsl:when><!-- �Ű� -->
		<xsl:when test=".=KAZ">KAZ</xsl:when><!-- ������˹̹ -->
		<xsl:when test=".=KOR">KOR</xsl:when><!-- ���� -->
		<xsl:when test=".=NLD">NL</xsl:when><!-- ���� -->
		<xsl:when test=".=ANT">AHO</xsl:when><!-- ����������˹ -->
		<xsl:when test=".=HND">HON</xsl:when><!-- �鶼��˹ -->
		<xsl:when test=".=CAN">CAN</xsl:when><!-- ���ô� -->
		<xsl:when test=".=GHA">DG</xsl:when><!-- ���� -->
		<xsl:when test=".=CZE">CZE</xsl:when><!-- �ݿ� -->
		<xsl:when test=".=ZWE">ZIM</xsl:when><!-- ��Ͳ�Τ -->
		<xsl:when test=".=QAT">QAT</xsl:when><!-- ������ -->
		<xsl:when test=".=HRV">CRO</xsl:when><!-- ���޵��� -->
		<xsl:when test=".=KEN">KEN</xsl:when><!-- ������ -->
		<xsl:when test=".=LVA">LAT</xsl:when><!-- ����ά�� -->
		<xsl:when test=".=LBN">LIB</xsl:when><!-- ����� -->
		<xsl:when test=".=LBY">LBA</xsl:when><!-- ������ -->
		<xsl:when test=".=LTU">LTU</xsl:when><!-- ������ -->
		<xsl:when test=".=LIE">LIE</xsl:when><!-- ��֧��ʿ�� -->
		<xsl:when test=".=LUX">LUX</xsl:when><!-- ¬ɭ�� -->
		<xsl:when test=".=RWA">RWA</xsl:when><!-- ¬���� -->
		<xsl:when test=".=ROM">ROM</xsl:when><!-- �������� -->
		<xsl:when test=".=MWI">MAW</xsl:when><!-- ����ά -->
		<xsl:when test=".=MYS">MY</xsl:when><!-- �������� -->
		<xsl:when test=".=MKD">MKD</xsl:when><!-- ����� -->
		<xsl:when test=".=MUS">MRI</xsl:when><!-- ë����˹ -->
		<xsl:when test=".=USA">USA</xsl:when><!-- ���� -->
		<xsl:when test=".=MNG">MGL</xsl:when><!-- �ɹ� -->
		<xsl:when test=".=BGD">BAN</xsl:when><!-- �ϼ����� -->
		<xsl:when test=".=PER">PER</xsl:when><!-- ��³ -->
		<xsl:when test=".=MAR">MAR</xsl:when><!-- Ħ��� -->
		<xsl:when test=".=MCO">MNC</xsl:when><!-- Ħ�ɸ� -->
		<xsl:when test=".=MEX">MEX</xsl:when><!-- ī���� -->
		<xsl:when test=".=NAM">NAM</xsl:when><!-- ���ױ��� -->
		<xsl:when test=".=ZAF">SFA</xsl:when><!-- �Ϸ� -->
		<xsl:when test=".=NIC">NCA</xsl:when><!-- ������� -->
		<xsl:when test=".=NGA">NGR</xsl:when><!-- �������� -->
		<xsl:when test=".=NOR">NO</xsl:when><!-- Ų�� -->
		<xsl:when test=".=PRT">POR</xsl:when><!-- ������ -->
		<xsl:when test=".=JPN">JAN</xsl:when><!-- �ձ� -->
		<xsl:when test=".=SWE">SWE</xsl:when><!-- ��� -->
		<xsl:when test=".=CHE">SUI</xsl:when><!-- ��ʿ -->
		<xsl:when test=".=CYP">CYP</xsl:when><!-- ����·˹ -->
		<xsl:when test=".=SYC">SEY</xsl:when><!-- ����� -->
		<xsl:when test=".=SAU">UAE</xsl:when><!-- ɳ�ذ����� -->
		<xsl:when test=".=SMR">SMR</xsl:when><!-- ʥ����ŵ -->
		<xsl:when test=".=LKA">SLK</xsl:when><!-- ˹������ -->
		<xsl:when test=".=SVK">SVK</xsl:when><!-- ˹�工�� -->
		<xsl:when test=".=SVN">SLO</xsl:when><!-- ˹�������� -->
		<xsl:when test=".=SDN">SUD</xsl:when><!-- �յ� -->
		<xsl:when test=".=SUR">SUR</xsl:when><!-- ������ -->
		<xsl:when test=".=SOM">SOM</xsl:when><!-- ������ -->
		<xsl:when test=".=TJK">KGZ</xsl:when><!-- ������˹̹ -->
		<xsl:when test=".=THA">THA</xsl:when><!-- ̩�� -->
		<xsl:when test=".=TON">TO</xsl:when><!-- ���� -->
		<xsl:when test=".=TTO">TRI</xsl:when><!-- �������Ͷ�͸� -->
		<xsl:when test=".=TUN">TUN</xsl:when><!-- ͻ��˹ -->
		<xsl:when test=".=TUR">TUR</xsl:when><!-- ������ -->
		<xsl:when test=".=TKM">TKM</xsl:when><!-- ������˹̹ -->
		<xsl:when test=".=VEN">VEN</xsl:when><!-- ί������ -->
		<xsl:when test=".=BRN">BRU</xsl:when><!-- ���� -->
		<xsl:when test=".=UGA">UGA</xsl:when><!-- �ڸɴ� -->
		<xsl:when test=".=UKR">UKR</xsl:when><!-- �ڿ��� -->
		<xsl:when test=".=URY">URU</xsl:when><!-- ������ -->
		<xsl:when test=".=UZB">UZB</xsl:when><!-- ���ȱ��˹̹ -->
		<xsl:when test=".=ESP">ESP</xsl:when><!-- ������ -->
		<xsl:when test=".=GRC">GRE</xsl:when><!-- ϣ�� -->
		<xsl:when test=".=HKG">HK</xsl:when><!-- ��� -->
		<xsl:when test=".=SGP">SG</xsl:when><!-- �¼��� -->
		<xsl:when test=".=NZL">NZL</xsl:when><!-- ������ -->
		<xsl:when test=".=HUN">HUN</xsl:when><!-- ������ -->
		<xsl:when test=".=SYR">SYR</xsl:when><!-- ������ -->
		<xsl:when test=".=JAM">JAM</xsl:when><!-- ����� -->
		<xsl:when test=".=ARM">ARM</xsl:when><!-- �������� -->
		<xsl:when test=".=YEM">YEM</xsl:when><!-- Ҳ�� -->
		<xsl:when test=".=IRQ">IRQ</xsl:when><!-- ������ -->
		<xsl:when test=".=IRN">IRI</xsl:when><!-- ���� -->
		<xsl:when test=".=ISR">ISR</xsl:when><!-- ��ɫ�� -->
		<xsl:when test=".=ITA">ITA</xsl:when><!-- ����� -->
		<xsl:when test=".=IND">IND</xsl:when><!-- ӡ�� -->
		<xsl:when test=".=IDN">INA</xsl:when><!-- ӡ�������� -->
		<xsl:when test=".=GBR">ENG</xsl:when><!-- Ӣ�� -->
		<xsl:when test=".=VGB">IVB</xsl:when><!-- Ӣ��ά����Ⱥ�� -->
		<xsl:when test=".=VNM">VIE</xsl:when><!-- Խ�� -->
		<xsl:when test=".=ZMB">ZAM</xsl:when><!-- �ޱ��� -->
		<xsl:when test=".=CHL">CHI</xsl:when><!-- ���� -->
		<xsl:when test=".=CHN">CHN</xsl:when><!-- �й� -->
		<xsl:when test=".=TWN">TW</xsl:when><!-- �й�̨�� -->
		<xsl:otherwise>OTH</xsl:otherwise><!-- ���� -->
	</xsl:choose>
</xsl:template>

<!-- ��� -->
<xsl:template name="tran_maritalstatus" match="marriage">
	<xsl:choose>
		<xsl:when test=".=02 or 03 or 04">0</xsl:when><!-- ����ż(δ�� �����졢ɥż) -->
		<xsl:when test=".=01 ">1</xsl:when><!-- ����ż(�ѻ�) -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>