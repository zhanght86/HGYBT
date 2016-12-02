<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="InsuReq">
<TranData><!--����¼���Ժ�������-->
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Main/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <ClientIp>127.0.0.1</ClientIp>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
   </Head>
   <Body>
      <SaleChannel>
      <xsl:call-template name="tran_Channel">
        <xsl:with-param name="Channel">
		  <xsl:value-of select="Main/Channel"/>
	    </xsl:with-param>
	  </xsl:call-template>
      </SaleChannel>
      <ProposalPrtNo><xsl:value-of select="Main/ApplyNo"/></ProposalPrtNo>
      <ContPrtNo />
      <PolApplyDate><xsl:value-of select="Main/ApplyDate"/></PolApplyDate>
      <AccNo><xsl:value-of select="Risks/Appendix/PayAcc"/></AccNo>
      <AccName><xsl:value-of select="Risks/Appendix/PayAccName"/></AccName>
      <GetPolMode>1</GetPolMode>
      <HealthNotice><xsl:value-of select="Risks/Appendix/HealthFlag"/></HealthNotice>
      <SaleName><xsl:value-of select="Main/SellTellerName"/></SaleName>
      <SaleStaff><xsl:value-of select="Main/SellTeller"/></SaleStaff>
      <SaleCertNo><xsl:value-of select="Main/SellCertID"/></SaleCertNo>
      <Lv1BrNo></Lv1BrNo>
      <Appnt>
         <Name><xsl:value-of select="Appnt/Name"/></Name>
         <Sex>
            <xsl:call-template name="tran_sex">
			  <xsl:with-param name="Sex">
				<xsl:value-of select="Appnt/Sex"/>
			  </xsl:with-param>
			</xsl:call-template>
         </Sex>
         <Birthday><xsl:value-of select="Appnt/Birthday"/></Birthday>
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="Appnt/IDType"/>
			  </xsl:with-param>
			</xsl:call-template>
		 </IDType>
         <IDNo><xsl:value-of select="Appnt/IDNo"/></IDNo>
         <IdExpDate><xsl:value-of select="Appnt/IDEndDate"/></IdExpDate>
         <Nationality><xsl:value-of select="Appnt/Nationality"/></Nationality>
         <AddressContent><xsl:value-of select="Appnt/HomeAddr"/></AddressContent>
         <FixTelDmstDstcNo />
         <MobileItlDstcNo />
         <NationalityCode></NationalityCode>
         <Address><xsl:value-of select="Appnt/HomeAddr"/></Address>
         <ZipCode><xsl:value-of select="Appnt/HomeZipCode"/></ZipCode>
         <Phone><xsl:value-of select="Appnt/HomePhone"/></Phone>
         <Mobile><xsl:value-of select="Appnt/MobilePhone"/></Mobile>
         <Email><xsl:value-of select="Appnt/Email"/></Email>
         <JobCode>3010101</JobCode>
         <YearSalary><xsl:value-of select="Appnt/Income div 10000"/></YearSalary>
         <FamilyYearSalary><xsl:value-of select="Appnt/FamilyIncome div 10000"/></FamilyYearSalary>
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="Appnt/RelaToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <DenType><xsl:value-of select="Appnt/ResiType"/></DenType>
      </Appnt>
      <Insured>
         <Name><xsl:value-of select="Insured/Name"/></Name>
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Insured/Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <Birthday><xsl:value-of select="Insured/Birthday"/></Birthday>
         <IDType>
           <xsl:call-template name="tran_idtype">
			 <xsl:with-param name="IDType">
			   <xsl:value-of select="Insured/IDType"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <IDNo><xsl:value-of select="Insured/IDNo"/></IDNo>
         <IdExpDate><xsl:value-of select="Insured/IDEndDate"/></IdExpDate>
         <Nationality><xsl:value-of select="Insured/Nationality"/></Nationality>
         <AddressContent><xsl:value-of select="Insured/HomeAddr"/></AddressContent>
         <FixTelDmstDstcNo></FixTelDmstDstcNo>
         <MobileItlDstcNo></MobileItlDstcNo>
         <NationalityCode>0156</NationalityCode>
         <Address><xsl:value-of select="Insured/HomeAddr"/></Address>
         <ZipCode><xsl:value-of select="Insured/HomeZipCode"/></ZipCode>
         <Phone><xsl:value-of select="Insured/HomePhone"/></Phone>
         <Mobile><xsl:value-of select="Insured/MobilePhone"/></Mobile>
         <Email><xsl:value-of select="Insured/Email"/></Email>
         <JobCode>3010101</JobCode>
         <RcgnYrIncmAm></RcgnYrIncmAm>
         <CovSumAmt></CovSumAmt>
         <RiskAssessmentResult></RiskAssessmentResult>
         <RiskAssessmentEndDate></RiskAssessmentEndDate>
         <PremiumBudget></PremiumBudget>
      </Insured>
      <xsl:for-each select="Bnfs/Bnf">
      <Bnf>
         <Type>
           <xsl:call-template name="tran_Type">
			 <xsl:with-param name="Type">
			   <xsl:value-of select="Type"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Type>
         <BenfSN />
         <Grade><xsl:value-of select="Order"/></Grade>
         <Name><xsl:value-of select="Name"/></Name>
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <Birthday><xsl:value-of select="Birthday"/></Birthday>
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="IDType"/>
			  </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <IDNo><xsl:value-of select="IDNo"/></IDNo>
         <IdExpDate><xsl:value-of select="IDEndDate"/></IdExpDate>
         <Nationality><xsl:value-of select="Nationality"/></Nationality>
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="RelationToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <Lot><xsl:value-of select="Percent"/></Lot>
         <AddressContent><xsl:value-of select="HomeAddr"/></AddressContent>
         <BenfCommAdr></BenfCommAdr>
         <BeneficType></BeneficType>
      </Bnf>
      </xsl:for-each>
      <xsl:variable name='MainCode'>
         <xsl:value-of select="Risks/Risk[MainSubFlag='1']/Code"/>
      </xsl:variable> 
      <xsl:for-each select="Risks/Risk">
      <Risk>
         <RiskCode><xsl:value-of select="Code"/></RiskCode>
         <MainRiskCode><xsl:value-of select="$MainCode"/></MainRiskCode>
         <RiskName />
         <AgInsPkgID />
         <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsuAmount)"/></Amnt>
         <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Premium)"/></Prem>
         <Mult><xsl:value-of select="Unit"/></Mult>
         <PayMode>B</PayMode>
         <PayIntv>
         <xsl:call-template name="tran_PayIntv">
		   <xsl:with-param name="PayIntv">
		     <xsl:value-of select="../Appendix/PayIntv"/>
		   </xsl:with-param>
		 </xsl:call-template>
         </PayIntv>
         <PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
         <PayEndYearFlag>
         <xsl:call-template name="tran_PayEndYearFlag">
		   <xsl:with-param name="PayEndYearFlag">
		     <xsl:value-of select="PayEndYearFlag"/>
		   </xsl:with-param>
		 </xsl:call-template>
         </PayEndYearFlag>
         <GetIntv />
         <GetYearFlag>
         <xsl:call-template name="tran_GetYearFlag">
		   <xsl:with-param name="GetYearFlag">
		     <xsl:value-of select="../Appendix/GetYearFlag"/>
		   </xsl:with-param>
		 </xsl:call-template>
         </GetYearFlag>
         <BonusGetMode>
         <xsl:call-template name="tran_BonusGetMode">
		   <xsl:with-param name="BonusGetMode">
		     <xsl:value-of select="../Appendix/BonusGetMode"/>
		   </xsl:with-param>
		 </xsl:call-template>
         </BonusGetMode>
         <SubFlag />
         <GetYear><xsl:value-of select="../Appendix/GetYear"/></GetYear>
         <InsuYearFlag>
         <xsl:call-template name="tran_InsuYearFlag">
		   <xsl:with-param name="InsuYearFlag">
		     <xsl:value-of select="InsuYearFlag"/>
		   </xsl:with-param>
		 </xsl:call-template>
         </InsuYearFlag>
         <InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
         <GetBankCode />
         <GetBankAccNo />
         <GetAccName />
         <AutoPayFlag />
      </Risk>
      </xsl:for-each>
   </Body>
</TranData>
</xsl:template>
<!--��������-->
<xsl:template name="tran_Channel">
  <xsl:param name="Channel"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Channel = '1'">0</xsl:when><!--����-->
  <xsl:when test="$Channel = '2'">07</xsl:when><!--���� -->
   <xsl:when test="$Channel = '4'">06</xsl:when><!--�����ն� -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!--�Ա�ת��-->
<xsl:template name="tran_sex">
  <xsl:param name="Sex"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Sex = '0'">0</xsl:when><!-- ��-->
  <xsl:when test="$Sex = '1'">1</xsl:when><!-- Ů -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!-- ֤������     -->
<xsl:template name="tran_idtype"><!--֤������-->
  <xsl:param name="IDType"></xsl:param>
  <xsl:choose>
  <xsl:when test="$IDType = '01'">0</xsl:when><!-- ���֤ -->
  <xsl:when test="$IDType = '02'">C</xsl:when> <!-- ��ʱ���֤ -->
  <xsl:when test="$IDType = '03'">1</xsl:when><!--���� -->
  <xsl:when test="$IDType = '04'">4</xsl:when><!-- ���ڱ� -->
  <xsl:when test="$IDType = '05'">2</xsl:when><!--����֤ -->
  <xsl:when test="$IDType = '06'">D</xsl:when><!-- ����֤ -->
  <xsl:when test="$IDType = '08'">8</xsl:when><!--�⽻��Ա���֤-->
  <xsl:when test="$IDType = '09'">8</xsl:when><!-- ����˾������֤ -->
  <xsl:when test="$IDType = '10'">8</xsl:when><!-- ������뾳ͨ��֤ -->
  <xsl:when test="$IDType = '11'">8</xsl:when><!-- ���� -->
  <xsl:when test="$IDType = '47'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤����ۣ� -->
  <xsl:when test="$IDType = '48'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤�����ţ� -->
  <xsl:when test="$IDType = '49'">F</xsl:when><!-- ̨�����������½ͨ��֤ -->
  <xsl:otherwise>8</xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--Ͷ�����˹�ϵ -->
<xsl:template name="tran_relatoinsured">
  <xsl:param name="RelaToInsured"></xsl:param>
  <xsl:choose>
  <xsl:when test="$RelaToInsured = '01'">00</xsl:when><!--���� -->
  <xsl:when test="$RelaToInsured = '02'">02</xsl:when> <!--��ż-->
  <xsl:when test="$RelaToInsured = '03'">02</xsl:when><!--��ż -->
  <xsl:when test="$RelaToInsured = '04'">01</xsl:when><!--��ĸ -->
  <xsl:when test="$RelaToInsured = '05'">01</xsl:when><!--��ĸ -->
  <xsl:when test="$RelaToInsured = '06'">03</xsl:when><!--��Ů -->
  <xsl:when test="$RelaToInsured = '07'">03</xsl:when><!--��Ů-->
  <xsl:when test="$RelaToInsured = '08'">04</xsl:when><!-- �游��ĸ������Ů -->
  <xsl:when test="$RelaToInsured = '09'">04</xsl:when><!-- �游��ĸ������Ů -->
  <xsl:when test="$RelaToInsured = '10'">04</xsl:when><!-- �游��ĸ������Ů -->
  <xsl:when test="$RelaToInsured = '11'">04</xsl:when><!-- �游��ĸ������Ů -->
  <xsl:otherwise>06</xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--���������� -->
<xsl:template name="tran_Type">
  <xsl:param name="Type"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Type = '1'">1</xsl:when><!--��� -->
  <xsl:when test="$Type = '2'">0</xsl:when> <!--����-->
  <xsl:when test="$Type = '3'">2</xsl:when><!--����  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--�ɷѷ�ʽ-->
<xsl:template name="tran_PayIntv">
  <xsl:param name="PayIntv"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayIntv = '01'">0</xsl:when><!--���� -->
  <xsl:when test="$PayIntv = '02'">12</xsl:when> <!--�꽻-->
  <xsl:when test="$PayIntv = '03'">6</xsl:when><!--���꽻  -->
  <xsl:when test="$PayIntv = '04'">3</xsl:when><!--����  -->
  <xsl:when test="$PayIntv = '05'">1</xsl:when><!--�½�  -->
  <xsl:when test="$PayIntv = '06'">-1</xsl:when><!--�����ڽ�  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--�ɷ����������־-->
<xsl:template name="tran_PayEndYearFlag">
  <xsl:param name="PayEndYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayEndYearFlag = '00'"></xsl:when><!--�޹� -->
  <xsl:when test="$PayEndYearFlag = '01'">A</xsl:when> <!--����-->
  <xsl:when test="$PayEndYearFlag = '02'">Y</xsl:when><!--�꽻  -->
  <xsl:when test="$PayEndYearFlag = '05'">M</xsl:when><!--�½�  -->
  <xsl:when test="$PayEndYearFlag = '06'">D</xsl:when><!--�ս�  -->
  <xsl:when test="$PayEndYearFlag = '07'">A</xsl:when><!--����ĳȷ������  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--��ȡ���������־-->
<xsl:template name="tran_GetYearFlag">
  <xsl:param name="GetYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$GetYearFlag = '00'"></xsl:when><!--�޹� -->
  <xsl:when test="$GetYearFlag = '01'"></xsl:when> <!--����-->
  <xsl:when test="$GetYearFlag = '02'">Y</xsl:when><!--����  -->
  <xsl:when test="$GetYearFlag = '03'"></xsl:when><!--������  -->
  <xsl:when test="$GetYearFlag = '04'"></xsl:when><!--����  -->
  <xsl:when test="$GetYearFlag = '05'">M</xsl:when><!--�����ڽ�  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--������ȡ��ʽ-->
<xsl:template name="tran_BonusGetMode">
  <xsl:param name="BonusGetMode"></xsl:param>
  <xsl:choose>
  <xsl:when test="$BonusGetMode = '0'">4</xsl:when><!--�޹� -->
  <xsl:when test="$BonusGetMode = '1'">1</xsl:when> <!--����-->
  <xsl:when test="$BonusGetMode = '2'">2</xsl:when><!--����  -->
  <xsl:when test="$BonusGetMode = '3'">3</xsl:when><!--������  -->
  <xsl:when test="$BonusGetMode = '4'">5</xsl:when><!--����  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
<!--�������������־-->
<xsl:template name="tran_InsuYearFlag">
  <xsl:param name="InsuYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$InsuYearFlag = '01'">Y</xsl:when><!--�걣 -->
  <xsl:when test="$InsuYearFlag = '03'">M</xsl:when> <!--�±�-->
  <xsl:when test="$InsuYearFlag = '04'">D</xsl:when><!--�ձ� -->
  <xsl:when test="$InsuYearFlag = '05'">A</xsl:when><!--�����䱣  -->
  <xsl:when test="$InsuYearFlag = '06'">1000Y</xsl:when><!--������ -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>

