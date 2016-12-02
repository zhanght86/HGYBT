<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="SRCB">
		<TranData>
			<Head>
			     <!--�������� -->
				<TranDate><xsl:value-of select="Header/TransDate" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="Header/TransTime" /></TranTime>
				<!-- �������� -->
				<!-- <TranCom><xsl:value-of select="" /></TranCom> -->
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Header/BranchNo" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0107</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="Header/TellerNo" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Header/SerialNo" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- ������ -->
		<xsl:apply-templates select="Body" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="Body">
		<Body>
		    <!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="Base/InsuranceSlipNo" /></ProposalPrtNo>
			<!-- ����ӡˢ�� -->
			<ContPrtNo><xsl:value-of select="Base/InsurancePrintNo" /></ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate><xsl:value-of select="Base/ApplyDate" /></PolApplyDate>
			<!-- �������ͷ�ʽ -->
			<GetPolMode>
			  <xsl:call-template name="PbSendMode">
			    <xsl:with-param name="pbSendMode">
				<xsl:value-of select="Base/GetPolMode"/></xsl:with-param>
			  </xsl:call-template>
			</GetPolMode> 
			<!-- ������֪ -->
			<HealthNotice><xsl:value-of select="Base/HealthNotice"></xsl:value-of></HealthNotice>
			<!-- �˻����� -->
			<AccName><xsl:value-of select="Base/AccName" /></AccName>
			<!-- �˻�-->
			<AccNo><xsl:value-of select="Base/AccNo" /></AccNo>
			
			<!-- ������Ա���� -->
		    <SaleStaff><xsl:value-of select="Base/SellerNo" /></SaleStaff>
		    <!-- ������Ա����  -->
            <SaleName><xsl:value-of select="Base/SellerName" /></SaleName>
            <!-- ������Ա�ʸ�֤��  -->
            <SaleCertNo><xsl:value-of select="Base/SellerCertiCode" /></SaleCertNo>
            
			<!-- Ͷ���� -->
			<Appnt>
			<!-- Ͷ�������� -->
				<Name>
					<xsl:value-of select="PolicyHolder/Name" />
				</Name>
				<!-- Ͷ�����Ա� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="PolicyHolder/Sex" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- Ͷ�������� -->
				<Birthday>
					<xsl:value-of select="PolicyHolder/Birthday" />
				</Birthday>
				<!-- Ͷ����֤������ -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="PolicyHolder/IDType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- Ͷ����֤���� -->
				<IDNo>
					<xsl:value-of select="PolicyHolder/IDNo" />
				</IDNo>
				<!-- Ͷ����ְҵ����   д���ˣ�һ������-->
				<JobCode>3010101</JobCode>
				<!-- Ͷ���˹��� -->
				<Nationality>
							<xsl:value-of select="PolicyHolder/Nationality"/>
				</Nationality>
				
				<Stature />
				<Weight />
				<MaritalStatus><xsl:value-of select="PolicyHolder/MaritalStatus" /></MaritalStatus><!-- ���Ĳ���Ҫ��Ҳ���Էſ� -->
				<!-- Ͷ���������� -->
				<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(PolicyHolder/Salary)"/></YearSalary>
				<!-- Ͷ����֤����Ч��-->
				<xsl:choose>
				<xsl:when test="PolicyHolder/IDEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="PolicyHolder/IDEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!-- Ͷ���˵�ַ-->
				<Address>
					<xsl:value-of select="PolicyHolder/Address" />
				</Address>
				<!-- Ͷ���˵�λ��ַ -->
				<WorkAddress></WorkAddress>
				<!-- Ͷ������������-->
				<ZipCode>
					<xsl:value-of select="PolicyHolder/PostCode" />
				</ZipCode>
				<!-- Ͷ���˵�λ��������-->
				<WorkZipCode></WorkZipCode>
				<!-- Ͷ�����ƶ��绰-->
				<Mobile>
					<xsl:value-of select="PolicyHolder/Mobile" />
				</Mobile>
				<!-- Ͷ���˹̶��绰-->
				<Phone>
					<xsl:value-of select="PolicyHolder/Phone" />
				</Phone>
				<!-- Ͷ���˵�λ�绰 -->
				<WorkPhone></WorkPhone>
				<!-- Ͷ��������-->
				<Email>
				<xsl:value-of select="PolicyHolder/Email" />
				</Email>
				<!-- Ͷ�����뱻���˹�ϵ  �����ϵ��Ҫ������ȷ��һ�£�Ӧ��ΪͶ�����Ǳ����˵�XX���������Ҫ��ϵ�෴-->
				<!-- ���磬SRCB�� 01 ��������Ͷ���˵ĸ�ĸ����ô������Ҫ����Ͷ�����Ǳ����˵�XX��ϵ��������Ҫת��Ϊ��Ů-->
				<RelaToInsured>
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="PolicyHolder/Relation" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured>
				<!-- Ͷ���˼�ͥ������ -->
				<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(PolicyHolder/FamilySalary)"/></FamilyYearSalary>
				<!-- Ͷ���˾������� -->
				<DenType><xsl:value-of select="PolicyHolder/LiveZone" /></DenType>
			</Appnt>

			<!-- ������ -->
			<Insured>
			<!-- ���������� -->
				<Name>
					<xsl:value-of select="Insured/Name" />
				</Name>
				<!-- �������Ա� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="Insured/Name" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- ���������� -->
				<Birthday>
					<xsl:value-of select="Insured/Birthday" />
				</Birthday>
				<!-- ������֤������-->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Insured/IDType" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- ������֤����-->
				<IDNo>
					<xsl:value-of select="Insured/IDNo" />
				</IDNo>
				<!-- ������ְҵ����-->
				<JobCode>3010101</JobCode>
				<!-- �����˹���-->
				<Nationality>
							<xsl:value-of select="Insured/Nationality" />
				</Nationality>
				<!-- ������֤����Ч��-->
				<xsl:choose>
				<xsl:when test="Insured/IDEndDate=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Insured/IDEndDate"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<Stature />
				<Weight />
				<MaritalStatus><xsl:value-of select="Insured/MaritalStatus" /></MaritalStatus><!-- ���Ĳ���Ҫ��Ҳ���Էſ� -->
				<!-- ���� -->
				<YearSalary/>
				<!-- �����˵�ַ-->
				<Address>
					<xsl:value-of select="Insured/Address" />
				</Address>
				<!-- �������ʱ�-->
				<ZipCode>
					<xsl:value-of select="Insured/PostCode" />
				</ZipCode>
				<!-- �������ƶ�����-->
				<Mobile>
					<xsl:value-of select="Insured/Mobile" />
				</Mobile>
				<!-- �����˹̶��绰-->
				<Phone>
					<xsl:value-of select="Insured/Phone" />
				</Phone>
				<!-- ����������-->
				<Email></Email>
				
				<!-- δ���걻���������������չ�˾�ۼ���ʱ���  ��λ��Ԫ-->
            	<CovSumAmt></CovSumAmt>
				
			</Insured>

			<!-- ������ -->
		
				<xsl:choose>
					<xsl:when
						test="/SRCB/Body/BeneNum ='0'">
					</xsl:when>
					<xsl:when
						test="/SRCB/Body/BeneNum !='0'">
						
						<xsl:for-each select="BeneList/Bene">
						<Bnf>
							<!-- ��������� -->
							<Type>
								<xsl:value-of	select="Type" />
							</Type>
							<!-- ������˳�� -->
							<Grade><xsl:value-of select="Grade" /></Grade>
							<!--���������� -->
							<Name><xsl:value-of select="Name" /></Name>
							<!-- �������Ա� -->
						     <Sex>
							   <xsl:call-template name="tran_sex">
								  <xsl:with-param name="Sex">
									 <xsl:value-of select="Sex" />
								  </xsl:with-param>
							   </xsl:call-template>
						    </Sex>
							<Birthday>
								<xsl:value-of	select="Birthday" />
							</Birthday>
							<IDType>
								<xsl:call-template name="tran_idtype">
									<xsl:with-param name="idtype">
										<xsl:value-of	select="IDType" />
									</xsl:with-param>
								</xsl:call-template>
							</IDType>
							<IDNo><xsl:value-of select="IDNo" /></IDNo>
							<!-- ������� -->
							<Lot>
								<xsl:value-of	select="Rate" />
							</Lot>
							<!-- ��������Ͷ���˹�ϵ -->
							<RelaToInsured>
								<xsl:call-template name="tran_relation">
									<xsl:with-param name="RelaToInsured">
										<xsl:value-of	select="Relation" />
									</xsl:with-param>
								</xsl:call-template>
							</RelaToInsured>
							<BeneficType>N</BeneficType>
						</Bnf>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			

			<!-- ���� -->
			<Risk>
				<RiskCode>					
					<xsl:call-template name="tran_MainRiskCode">
						<xsl:with-param name="MainRiskCode">
							<xsl:value-of select="Base/RiskCode" />
						</xsl:with-param>
					</xsl:call-template>
				</RiskCode>
				<MainRiskCode>
					<xsl:call-template name="tran_MainRiskCode">
						<xsl:with-param name="MainRiskCode">
							<xsl:value-of select="Base/RiskCode" />
						</xsl:with-param>
					</xsl:call-template>	
				</MainRiskCode>
				<Amnt>
					<xsl:value-of select="Base/Amnt" />
				</Amnt>
				<Prem>
					<xsl:value-of select="Base/Prem" />
				</Prem>
				<Mult>
					<xsl:value-of select="Base/Share" />
				</Mult>
				<PayIntv>
					<xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="Base/PayMode" />
						</xsl:with-param>
					</xsl:call-template>
				</PayIntv>
				<!-- ���ڼ�����ǰ��ǰ�û��ͺ���Լ���ɷѷ�ʽΪB�������޴Ӳ����� -->
				<PayMode>B</PayMode>
				<InsuYearFlag>
					<xsl:call-template name="tran_PbIYF">
						<xsl:with-param name="PbInsuYearFlag">
							<xsl:value-of select="Base/InsuYearType" />
						</xsl:with-param>
					</xsl:call-template>	
				</InsuYearFlag>
				<InsuYear><xsl:value-of select="Base/InsuYear" /></InsuYear>	
				<!-- �ɷ����ںͽɷ����� -->
				 <xsl:if test="Base/PayDurationType = 1"><!-- ����1000 Y ������ -->				    
					<PayEndYear>1000</PayEndYear>
					<PayEndYearFlag>Y</PayEndYearFlag>
				</xsl:if>
				<xsl:if test="Base/PayDurationType != 1">	<!-- ������  Ŀǰ����汾�ĺ���ֻ֧���꽻�� -->
				    <PayEndYearFlag>Y</PayEndYearFlag>			    
					<PayEndYear><xsl:value-of select="Base/PayDuration" /></PayEndYear>			
				</xsl:if>
				<BonusGetMode>
					<xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							<xsl:value-of select="Base/BonusGetMode" />
						</xsl:with-param>
					</xsl:call-template>
			 </BonusGetMode>
				<SpecContent />
				<!-- ������ȡ��ʽ������ -->
				<FullBonusGetMode />
    	         <GetYearFlag></GetYearFlag> 
    	          <!-- ��ȡ�������ڱ�־  �����Ҫת��-->
    	          <GetYear></GetYear>   <!-- ��ȡ��ʼ���� -->	
                  <GetTerms></GetTerms><!-- ��ȡ���� -->
				<!-- ��ȡ��ʽ -->
				<GetIntv></GetIntv>
				<GetBankCode />
				<GetBankAccNo />
				<GetAccName />
				<AutoPayFlag><xsl:value-of select="Base/AutoPayFlag" /></AutoPayFlag>
			</Risk>
		</Body>
	</xsl:template>
	
	
	<!-- �Ա� -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 0">0</xsl:if><!-- �� -->
		<xsl:if test="$Sex = 1">1</xsl:if><!-- Ů -->
	</xsl:template>

	<!-- ���ս���ȡ��ʽ -->
	<xsl:template name="tran_GetIntv">
		<xsl:param name="GetIntv"></xsl:param>
		<xsl:if test="$GetIntv = 0">0</xsl:if>
	</xsl:template>
	
	
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>          <!--  0-һ�θ���  -->
		<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>          <!--  1-�¸���  -->
		<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>          <!--  3-������  -->
		<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>          <!--  6-�������  -->
		<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>          <!--  12-�����  -->
	</xsl:template>
	
	<!-- Ͷ�����˹��� -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 0156">CHN</xsl:if>
		<xsl:if test="$Nationality = 0344"></xsl:if><!--��� -->
		<xsl:if test="$Nationality = 0158"></xsl:if><!--̨�� -->
		<xsl:if test="$Nationality = 0446"></xsl:if><!--���� -->
		<xsl:if test="$Nationality = 0392">JAN</xsl:if><!--�ձ� -->
		<xsl:if test="$Nationality = 0840">USA</xsl:if><!--����-->
		<xsl:if test="$Nationality = 0643"></xsl:if><!--����˹ -->
		<xsl:if test="$Nationality = 0826">ENG</xsl:if><!--Ӣ�� -->
		<xsl:if test="$Nationality = 0250">FRA</xsl:if><!--����-->
		<xsl:if test="$Nationality = 0276">DEU</xsl:if><!--�¹� -->
		<xsl:if test="$Nationality = 0410">KOR</xsl:if><!--���� -->
		<xsl:if test="$Nationality = 0702">SG</xsl:if><!--�¼��� -->
		<xsl:if test="$Nationality = 0360">INA</xsl:if><!--ӡ��������-->
		<xsl:if test="$Nationality = 0356">IND</xsl:if><!--ӡ��-->
		<xsl:if test="$Nationality = 0380">ITA</xsl:if><!--����� -->
		<xsl:if test="$Nationality = 0458">MY</xsl:if><!--�������� -->
		<xsl:if test="$Nationality = 0764">THA</xsl:if><!--̩��-->
		<xsl:if test="$Nationality = 0999"></xsl:if><!--����-->
	</xsl:template>

	<!-- ������ȡ��ʽ -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = 2">2</xsl:if><!-- ֱ�Ӹ��� -->
		<xsl:if test="$BonusGetMode = 3">3</xsl:if><!-- �ֽ����� -->
		<xsl:if test="$BonusGetMode = 0">1</xsl:if><!-- �ۻ���Ϣ -->
	</xsl:template>

	<!-- �����Ľɷѷ�ʽ -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 1">0</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 2">12</xsl:when><!-- ��� -->
			<xsl:when test="$payintv = 3">6</xsl:when><!-- ����� -->
			<xsl:when test="$payintv = 4">3</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 5">1</xsl:when><!-- �½� -->
			<xsl:when test="$payintv = 6">9</xsl:when><!-- ����(YBTУ��) -->
			<xsl:when test="$payintv = 7">-1</xsl:when><!-- ������ -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- �������ͷ�ʽ?    ��Ҫ�ͺ���ȷ�� -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 0">1</xsl:when><!-- ֽ�ʱ��� -->
			<xsl:when test="$pbSendMode = 1">1</xsl:when><!-- ���ӱ��� -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- ��������/�����־ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">2</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 0"></xsl:when>
			<xsl:when test="$PbInsuYearFlag = 1">Y</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 2">Y</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 3"></xsl:when>
			<xsl:when test="$PbInsuYearFlag = 4">M</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 5">D</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 6">A</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype">0</xsl:param>
		<xsl:if test="$idtype = '0'">0</xsl:if><!-- �������֤���� -->
		<xsl:if test="$idtype = '1'">1</xsl:if><!-- ����-->
		<xsl:if test="$idtype = '2'">2</xsl:if><!-- ����֤ -->
		<xsl:if test="$idtype = '3'">8</xsl:if><!-- �۰ľ���ͨ��֤-->
		<xsl:if test="$idtype = '4'">E</xsl:if><!-- ̨�����ͨ��֤-->
		<xsl:if test="$idtype = '9'">8</xsl:if><!-- ���� -->
	</xsl:template>

	<!-- ��ϵ -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:if test="$RelaToInsured = 00">00</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 01">01</xsl:if><!--��ĸ  -->
		<xsl:if test="$RelaToInsured = 02">02</xsl:if><!--��ż -->
		<xsl:if test="$RelaToInsured = 03">03</xsl:if><!--��Ů  -->
		<xsl:if test="$RelaToInsured = 04">06</xsl:if><!--���� -->
</xsl:template>
     
	<!-- �����ִ��� -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 0001">201201</xsl:if><!-- ����1 -->
		<xsl:if test="$MainRiskCode = 0002">201202</xsl:if><!-- ����2 -->
		<xsl:if test="$MainRiskCode = 0003">201203</xsl:if><!-- ����3 -->
		<xsl:if test="$MainRiskCode = 0004">201204</xsl:if><!-- ����4 -->
		<xsl:if test="$MainRiskCode = 0005">201205</xsl:if><!-- ����5 -->

	</xsl:template>
	
</xsl:stylesheet>

