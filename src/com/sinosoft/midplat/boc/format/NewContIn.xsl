<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="InsuReq">
<TranData>
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
   </Head>
   <Body>
   	  <!-- �������� -->
      <SaleChannel>
      <xsl:call-template name="tran_Channel">
        <xsl:with-param name="Channel">
		  <xsl:value-of select="Main/Channel"/>
	    </xsl:with-param>
	  </xsl:call-template>
      </SaleChannel>
      <!-- Ͷ������ -->
      <ProposalPrtNo>
        <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Main/ApplyNo)"/>
      </ProposalPrtNo>
      <!-- ������ -->
      <ContPrtNo />
      <!-- Ͷ������ -->
      <PolApplyDate><xsl:value-of select="Main/ApplyDate"/></PolApplyDate>
      <!-- �������ͷ�ʽ -->
      <GetPolMode>1</GetPolMode>
      <!-- ������֪ -->
      <HealthNotice><xsl:value-of select="Risks/Appendix/HealthFlag"/></HealthNotice>
      <!-- �����˻� -->
      <AccNo><xsl:value-of select="Risks/Appendix/PayAcc"/></AccNo>
      <!-- �˻����� -->
      <AccName><xsl:value-of select="Risks/Appendix/PayAccName"/></AccName>
      <!-- Ͷ������Ϣ -->
      <Appnt>
      	 <!-- Ͷ�������� -->
         <Name><xsl:value-of select="Appnt/Name"/></Name>
         <!-- Ͷ�����Ա� -->
         <Sex>
            <xsl:call-template name="tran_sex">
			  <xsl:with-param name="Sex">
				<xsl:value-of select="Appnt/Sex"/>
			  </xsl:with-param>
			</xsl:call-template>
         </Sex>
         <!-- Ͷ���˳�������-->
         <Birthday><xsl:value-of select="Appnt/Birthday"/></Birthday>
         <!-- Ͷ����֤������ -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="Appnt/IDType"/>
			  </xsl:with-param>
			</xsl:call-template>
		 </IDType>
		 <!-- Ͷ����֤������ -->
         <IDNo><xsl:value-of select="Appnt/IDNo"/></IDNo>
         <!-- Ͷ����ְҵ����(��ת��)-->
         <JobCode>3010101</JobCode>
         <!-- Ͷ���˹��� -->
         <Nationality>
         	<xsl:call-template name="ApplCountry">
				<xsl:with-param name="Country">
					<xsl:value-of select="Appnt/Nationality"/>
				</xsl:with-param>
			</xsl:call-template>
         </Nationality>
         <!-- Ͷ������� -->
         <Stature></Stature>
         <!-- Ͷ�������� -->
         <Weight></Weight>
         <!-- ���(N/Y) ��ֵ -->
         <MaritalStatus></MaritalStatus>
         <!-- Ͷ���������� (��Ԫ)-->
         <YearSalary><xsl:value-of select="Appnt/Income div 10000"/></YearSalary>
         <!-- Ͷ����֤����Ч�� -->
         <IdExpDate><xsl:value-of select="Appnt/IDEndDate"/></IdExpDate>
         <!-- Ͷ���˵�ַ -->
         <Address><xsl:value-of select="Appnt/HomeAddr"/></Address>
         <!-- Ͷ�����ʱ� -->
         <ZipCode><xsl:value-of select="Appnt/HomeZipCode"/></ZipCode>
         <!-- Ͷ���˹̶��绰 -->
         <Phone><xsl:value-of select="Appnt/HomePhone"/></Phone>
         <!-- Ͷ�����ƶ��绰 -->
         <Mobile><xsl:value-of select="Appnt/MobilePhone"/></Mobile>
         <!-- Ͷ���˵������� -->
         <Email><xsl:value-of select="Appnt/Email"/></Email>
         <!-- Ͷ�����뱻���˹�ϵ -->
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="Appnt/RelaToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <!-- Ͷ���˵�λ��ַ -->
         <WorkAddress></WorkAddress>
         <!-- Ͷ���˵�λ�ʱ� -->
         <WorkZipCode></WorkZipCode>
      </Appnt>
      <!-- ��������Ϣ -->
      <Insured>
      	 <!-- ���������� -->
         <Name><xsl:value-of select="Insured/Name"/></Name>
         <!-- �������Ա� -->
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Insured/Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <!--�����˳�������  -->
         <Birthday><xsl:value-of select="Insured/Birthday"/></Birthday>
         <!-- ������֤������ -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			 <xsl:with-param name="IDType">
			   <xsl:value-of select="Insured/IDType"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <!-- ������֤������ -->
         <IDNo><xsl:value-of select="Insured/IDNo"/></IDNo>
         <!-- ������ְҵ����(��ת��) -->
         <JobCode>3010101</JobCode>
         <!-- �����˹���(��ת��) -->
         <Nationality>
         	<xsl:call-template name="ApplCountry">
				<xsl:with-param name="Country">
					<xsl:value-of select="Insured/Nationality"/>
				</xsl:with-param>
			</xsl:call-template>
		</Nationality>
         <!-- ���������(cm) -->
         <Stature></Stature>
         <!--����������(g) -->
         <Weight></Weight>
         <!-- ���(N/Y) ��ֵ- -->
         <MaritalStatus></MaritalStatus>
         <!-- ������������(��Ԫ) -->
         <YearSalary></YearSalary>
         <!-- ���������֤֤����Ч��yyyyMMdd -->
         <IdExpDate><xsl:value-of select="Insured/IDEndDate"/></IdExpDate>
         <!-- �����˵�ַ -->
         <Address><xsl:value-of select="Insured/HomeAddr"/></Address>
         <!-- �������ʱ� -->
         <ZipCode><xsl:value-of select="Insured/HomeZipCode"/></ZipCode>
         <!-- �����˹̶��绰 -->
         <Phone><xsl:value-of select="Insured/HomePhone"/></Phone>
         <!-- �������ƶ��绰 -->
         <Mobile><xsl:value-of select="Insured/MobilePhone"/></Mobile>
         <!-- ���������� -->
         <Email><xsl:value-of select="Insured/Email"/></Email>
      </Insured>
      <!-- ��������Ϣ -->
      <xsl:for-each select="Bnfs/Bnf">
      <Bnf>
      	 <!-- ���������(0���棬1��ʣ�2����) -->
         <Type>
           <xsl:call-template name="tran_Type">
			 <xsl:with-param name="Type">
			   <xsl:value-of select="Type"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Type>
         <!-- ����˳�� -->
         <Grade><xsl:value-of select="Order"/></Grade>
         <!-- ���������� -->
         <Name><xsl:value-of select="Name"/></Name>
         <!-- �Ա� -->
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <!-- �������� -->
         <Birthday><xsl:value-of select="Birthday"/></Birthday>
         <!-- ֤������ -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="IDType"/>
			  </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <!-- ֤������ -->
         <IDNo><xsl:value-of select="IDNo"/></IDNo>
         <!-- �뱻���˹�ϵ -->
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="RelationToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <!-- ������� -->
         <Lot><xsl:value-of select="Percent"/></Lot>
         <!--���֤֤����Ч��yyyyMMdd-  -->
         <IdExpDate><xsl:value-of select="IDEndDate"/></IdExpDate>
      </Bnf>
      </xsl:for-each>
      <!-- ������Ϣ -->
      <xsl:variable name='MainCode'>
         <xsl:value-of select="Risks/Risk[MainSubFlag='1']/Code"/>
      </xsl:variable> 
      <xsl:for-each select="Risks/Risk">
      <Risk>
      	 <!-- ���ִ��� -->
         <RiskCode><xsl:value-of select="Code"/></RiskCode>
         <!-- �������ִ��� -->
         <MainRiskCode><xsl:value-of select="$MainCode"/></MainRiskCode>
         <!-- ����֣� -->
         <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsuAmount)"/></Amnt>
         <!-- ���ѣ��֣� -->
         <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Premium)"/></Prem>
         <!-- Ͷ������ -->
         <Mult><xsl:value-of select="Unit"/></Mult>
         <!-- �ɷ���ʽ -->
         <PayMode>B</PayMode>
         <!-- �ɷ�Ƶ�� -->
         <PayIntv>
	         <xsl:call-template name="tran_PayIntv">
			   <xsl:with-param name="PayIntv">
			     <xsl:value-of select="../Appendix/PayIntv"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </PayIntv>
         <!-- �������������־ -->
         <InsuYearFlag>
	         <xsl:call-template name="tran_InsuYearFlag">
			   <xsl:with-param name="InsuYearFlag">
			     <xsl:value-of select="InsuYearFlag"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </InsuYearFlag>
         <!-- ������������ -->
         <InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
         <xsl:if test="../Appendix/PayIntv='01'"><!-- ����1000Y������ -->				    
		   <PayEndYear>1000</PayEndYear><!-- �ɷ��������� -->
		   <PayEndYearFlag>Y</PayEndYearFlag><!-- �ɷ����������־ -->
		 </xsl:if>
		 <xsl:if test="../Appendix/PayIntv!='01'">		    
		   <PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
           <PayEndYearFlag>
	           <xsl:call-template name="tran_PayEndYearFlag">
			     <xsl:with-param name="PayEndYearFlag">
			       <xsl:value-of select="PayEndYearFlag"/>
		   		 </xsl:with-param>
			   </xsl:call-template>
           </PayEndYearFlag>
		 </xsl:if>
		 <!-- ������ȡ��ʽ -->
		 <BonusGetMode>
	         <xsl:call-template name="tran_BonusGetMode">
			   <xsl:with-param name="BonusGetMode">
			     <xsl:value-of select="../Appendix/BonusGetMode"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </BonusGetMode>
		 <!-- ������ȡ����ȡ��ʽ ���� -->
		 <FullBonusGetMode></FullBonusGetMode>
		 <!-- ��ȡ�������ڱ�־ -->
		 <GetYearFlag>
	         <xsl:call-template name="tran_GetYearFlag">
			   <xsl:with-param name="GetYearFlag">
			     <xsl:value-of select="../Appendix/GetYearFlag"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </GetYearFlag>
		 <!-- ��ȡ���� -->
		 <GetYear><xsl:value-of select="../Appendix/GetStartAge"/></GetYear>
		 <!-- ��ȡ����-->
		 <GetTerms><xsl:value-of select="../Appendix/GetYear"/></GetTerms>
		 <!--��ȡ��ʽ ����  -->
		 <GetIntv />
		 <!-- ��ȡ���б��� ���� -->
		 <GetBankCode />
		 <!-- ��ȡ�����˻� ���� -->
		 <GetBankAccNo />
		 <!-- ��ȡ���л���  ����-->
		 <GetAccName />
		 <!-- �Զ��潻��־ ���� -->
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

<!-- ���� -->
<xsl:template name="ApplCountry">
	<xsl:param name="Country"></xsl:param>
	<xsl:choose>
		<xsl:when test="$Country='AU'">AUS</xsl:when>	<!-- �Ĵ�����-->
		<xsl:when test="$Country='CN'">CHN</xsl:when>	<!-- �й� -->
		<xsl:when test="$Country='US'">USA</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='JP'">JAN</xsl:when>	<!-- �ձ� -->
		<xsl:when test="$Country='GB'">ENG</xsl:when>	<!-- Ӣ�� -->
		<xsl:when test="$Country='RU'">RUS</xsl:when>	<!-- ����˹ -->
		<xsl:when test="$Country='TW'">TW</xsl:when>	<!-- ̨�� -->
		<xsl:when test="$Country='MO'">MO</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='HK'">HK</xsl:when>	<!-- ��� -->
		<xsl:when test="$Country='PL'">PL</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='NL'">NL</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='NO'">NO</xsl:when>	<!-- Ų�� -->
		<xsl:when test="$Country='SG'">SG</xsl:when>	<!-- �¼��� -->
		<xsl:when test="$Country='GH'">DG</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='CA'">CAN</xsl:when>	<!-- ���ô� -->
		<xsl:when test="$Country='DE'">DEU</xsl:when>	<!-- �¹� -->
		<xsl:when test="$Country='MY'">MY</xsl:when>	<!-- �������� -->
		<xsl:when test="$Country='IT'">ITA</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='02'">BOL</xsl:when>	<!--  ����ά��-->
		<xsl:when test="$Country='NZ'">NZL</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='PH'">PHL</xsl:when>	<!-- ���ɱ� -->
		<xsl:when test="$Country='TO'">TO</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='KR'">KOR</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='BR'">BAS</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='LK'">SLK</xsl:when>	<!-- ˹������ -->
		<xsl:when test="$Country='AF'">AFG</xsl:when>	<!--������  -->
		<xsl:when test="$Country='AL'">ALB</xsl:when>	<!-- ���������� -->
		<xsl:when test="$Country='DZ'">ALG</xsl:when>	<!-- ���������� -->
		<xsl:when test="$Country='AD'">AND</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='AO'">ANG</xsl:when>	<!--  ������-->
		<xsl:when test="$Country='AM'">ARM</xsl:when>	<!-- �������� -->
		<xsl:when test="$Country='AR'">ARG</xsl:when>	<!-- ����͢ -->
		<xsl:when test="$Country='AW'">ARU</xsl:when>	<!-- ��³�͵� -->
		<xsl:when test="$Country='AT'">AUT</xsl:when>	<!-- �µ��� -->
		<xsl:when test="$Country='AZ'">AZE</xsl:when>	<!-- �����ݽ� -->
		<xsl:when test="$Country='BD'">BAN</xsl:when>	<!-- �ϼ����� -->
		<xsl:when test="$Country='BB'">BAR</xsl:when>	<!--�ͰͶ�˹  -->
		<xsl:when test="$Country='BH'">BRN</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='BY'">BLR</xsl:when>	<!-- �׶���˹ -->
		<xsl:when test="$Country='BE'">BEL</xsl:when>	<!--  ����ʱ-->
		<xsl:when test="$Country='BM'">BER</xsl:when>	<!-- ��Ľ�� -->
		<xsl:when test="$Country='BW'">BOT</xsl:when>	<!-- �������� -->
		<xsl:when test="$Country='BN'">BRU</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='VG'">IVB</xsl:when>	<!-- Ӣ��ά����Ⱥ�� -->
		<xsl:when test="$Country='01'">BUL</xsl:when>	<!-- �������� -->
		<xsl:when test="$Country='05'">CHI</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='CO'">COL</xsl:when>	<!-- ���ױ��� -->
		<xsl:when test="$Country='CR'">CRC</xsl:when>	<!-- ��˹����� -->
		<xsl:when test="$Country='HR'">CRO</xsl:when>	<!-- ���޵��� -->
		<xsl:when test="$Country='CU'">CUB</xsl:when>	<!-- �Ű� -->
		<xsl:when test="$Country='CY'">CYP</xsl:when>	<!-- ����·˹ -->
		<xsl:when test="$Country='CZ'">CZE</xsl:when>	<!-- �ݿ˹��͹� -->
		<xsl:when test="$Country='DK'">DEN</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='DO'">DOM</xsl:when>	<!--�������  -->
		<xsl:when test="$Country='EG'">EGY</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='EC'">ECU</xsl:when>	<!-- ��϶�� -->
		<xsl:when test="$Country='EE'">EST</xsl:when>	<!-- ��ɳ���� -->
		<xsl:when test="$Country='ET'">ETH</xsl:when>	<!-- ��������� -->
		<xsl:when test="$Country='FO'">FAI</xsl:when>	<!-- ����Ⱥ�� -->
		<xsl:when test="$Country='FI'">FIN</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='FR'">FRA</xsl:when>	<!--����  -->
		<xsl:when test="$Country='MK'">MKD</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='GE'">GEO</xsl:when>	<!-- ��³���� -->
		<xsl:when test="$Country='GR'">GRE</xsl:when>	<!-- ϣ�� -->
		<xsl:when test="$Country='HN'">HON</xsl:when>	<!-- �鶼��˹ -->
		<xsl:when test="$Country='HU'">HUN</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='IN'">IND</xsl:when>	<!-- ӡ�� -->
		<xsl:when test="$Country='IE'">IRL</xsl:when>	<!--������ -->
		<xsl:when test="$Country='IQ'">IRQ</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='IR'">IRI</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='IS'">ISL</xsl:when>	<!--����  -->
		<xsl:when test="$Country='ID'">INA</xsl:when>	<!-- ӡ�������� -->
		<xsl:when test="$Country='IL'">ISR</xsl:when>	<!-- ��ɫ�� -->
		<xsl:when test="$Country='JM'">JAM</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='JE'">JCI</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='KZ'">KAZ</xsl:when>	<!-- ������˹̹ -->
		<xsl:when test="$Country='KE'">KEN</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='TJ'">KGZ</xsl:when>	<!--������˹̹  -->
		<xsl:when test="$Country='LV'">LAT</xsl:when>	<!--����ά��  -->
		<xsl:when test="$Country='LB'">LIB</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='LY'">LBA</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='LI'">LIE</xsl:when>	<!--��֧��ʿ��  -->
		<xsl:when test="$Country='LT'">LTU</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='LU'">LUX</xsl:when>	<!-- ¬ɭ�� -->
		<xsl:when test="$Country='MW'">MAW</xsl:when>	<!-- ����ά -->
		<xsl:when test="$Country='MT'">MLT</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='ZZ'">OTH</xsl:when>	<!-- ���� -->
		<xsl:when test="$Country='MU'">MRI</xsl:when>	<!-- ë����˹ -->
		<xsl:when test="$Country='MX'">MEX</xsl:when>	<!-- ī���� -->
		<xsl:when test="$Country='MC'">MNC</xsl:when>	<!-- Ħ�ɸ� -->
		<xsl:when test="$Country='MN'">MGL</xsl:when>	<!-- �ɹ� -->
		<xsl:when test="$Country='MA'">MAR</xsl:when>	<!-- Ħ��� -->
		<xsl:when test="$Country='NA'">NAM</xsl:when>	<!--���ױ���  -->
		<xsl:when test="$Country='AN'">AHO</xsl:when>	<!-- ����������˹ -->
		<xsl:when test="$Country='NI'">NCA</xsl:when>	<!--������� -->
		<xsl:when test="$Country='NG'">NGR</xsl:when>	<!--��������  -->
		<xsl:when test="$Country='PS'">PLE</xsl:when>	<!--����˹̹  -->
		<xsl:when test="$Country='PA'">PAN</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='PG'">PNG</xsl:when>	<!-- �Ͳ����¼����� -->
		<xsl:when test="$Country='PY'">PAR</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='PE'">PER</xsl:when>	<!-- ��³ -->
		<xsl:when test="$Country='PT'">POR</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='QA'">QAT</xsl:when>	<!--������  -->
		<xsl:when test="$Country='RO'">ROM</xsl:when>	<!-- �������� -->
		<xsl:when test="$Country='RW'">RWA</xsl:when>	<!-- ¬���� -->
		<xsl:when test="$Country='SM'">SMR</xsl:when>	<!-- ʥ����ŵ -->
		<xsl:when test="$Country='SC'">SEY</xsl:when>	<!-- ����� -->
		<xsl:when test="$Country='SK'">SVK</xsl:when>	<!-- ˹�工�� -->
		<xsl:when test="$Country='SI'">SLO</xsl:when>	<!-- ˹�������� -->
		<xsl:when test="$Country='SO'">SOM</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='ES'">ESP</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='SD'">SUD</xsl:when>	<!-- �յ� -->
		<xsl:when test="$Country='SR'">SUR</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='SE'">SWE</xsl:when>	<!-- ��� -->
		<xsl:when test="$Country='CH'">SUI</xsl:when>	<!-- ��ʿ -->
		<xsl:when test="$Country='SY'">SYR</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='TJ'">TJK</xsl:when>	<!-- ������˹̹ -->
		<xsl:when test="$Country='TH'">THA</xsl:when>	<!-- ̩�� -->
		<xsl:when test="$Country='TT'">TRI</xsl:when>	<!-- �������Ͷ�͸� -->
		<xsl:when test="$Country='TN'">TUN</xsl:when>	<!-- ͻ��˹ -->
		<xsl:when test="$Country='TR'">TUR</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='TM'">TKM</xsl:when>	<!-- ������˹̹ -->
		<xsl:when test="$Country='UG'">UGA</xsl:when>	<!-- �ڸɴ� -->
		<xsl:when test="$Country='ZA'">SFA</xsl:when>	<!--�Ϸ�  -->
		<xsl:when test="$Country='UA'">UKR</xsl:when>	<!--�ڿ���  -->
		<xsl:when test="$Country='UY'">URU</xsl:when>	<!-- ������ -->
		<xsl:when test="$Country='VI'">ISV</xsl:when>	<!-- ����ά��Ⱥ�� -->
		<xsl:when test="$Country='UZ'">UZB</xsl:when>	<!-- ���ȱ��˹̹ -->
		<xsl:when test="$Country='VE'">VEN</xsl:when>	<!--ί������  -->
		<xsl:when test="$Country='VN'">VIE</xsl:when>	<!-- Խ�� -->
		<xsl:when test="$Country='YE'">YEM</xsl:when>	<!-- Ҳ�� -->
		<xsl:when test="$Country='YU'">YUG</xsl:when>	<!--��˹����  -->
		<xsl:when test="$Country='ZM'">ZAM</xsl:when>	<!-- �ޱ��� -->
		<xsl:when test="$Country='ZW'">ZIM</xsl:when>	<!-- ��Ͳ�Τ -->
		<xsl:otherwise>OTH</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--�Ա�ת��-->
<xsl:template name="tran_sex">
  <xsl:param name="Sex"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Sex = '1'">0</xsl:when><!-- ��-->
  <xsl:when test="$Sex = '0'">1</xsl:when><!-- Ů -->
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
  <xsl:otherwise></xsl:otherwise> 
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
