<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
			    
			     <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- �������� -->
				<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
				<!-- ���б��� -->
				<BankCode>0104</BankCode>
				<!--��Ա�� -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- ������ -->
		<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
	
		
		 <!-- ���չ�˾��� -->
		 <InsCoID><xsl:value-of select="Ins_Co_ID" /></InsCoID>
		 <Appnt>
		 <!-- Ͷ���˹��ҵ������� -->
         <NationalityCode>
          <xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="Plchd_Nat_Cd" />
					</xsl:with-param>
          </xsl:call-template>
        </NationalityCode>
         <!-- Ͷ���˳������� -->	
         <Birthday><xsl:value-of select="Plchd_Brth_Dt" /></Birthday>	
         <!--Ͷ�����Ա����  -->	
         <Sex> 
         <xsl:call-template name="tran_sex">
			<xsl:with-param name="Sex">
				<xsl:value-of select="Plchd_Gnd_Cd" />
			</xsl:with-param>
         </xsl:call-template>
         </Sex>
         <!-- Ͷ����ְҵ���� -->		
         <JobCode><xsl:value-of select="Plchd_Ocp_Cd" /></JobCode>
         <!--Ͷ������������  -->		
         <YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Plchd_Yr_IncmAm)" /></YearSalary>	
         <!--��ͥ��������  -->	
         <FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Fam_Yr_IncmAm)" /></FamilyYearSalary>		
         <!-- ����Ԥ���� -->
         <PremiumBudget><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Bdgt_Amt)" /></PremiumBudget>
         <!-- �������ʹ��� -->		
         <DenType><xsl:value-of select="Rsdnt_TpCd" /></DenType>		
         <!-- �ͻ����׶���ֵ -->
         <CstCtbNVal><xsl:value-of select="Cst_Ctb_NVal" /></CstCtbNVal>
         <!-- Ͷ���˺ͱ����˹�ϵ���ʹ��� -->		
         <RelaToInsured>	
         <xsl:call-template name="tran_Bnfrelation">
           <xsl:with-param name="BnfRelaToInsured">
					<xsl:value-of
						select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
		</xsl:call-template>
		</RelaToInsured>
		</Appnt>
		
		
         <!-- �����˹��ҵ������� -->	
        <Insured>
       
         <Nationality>
         <xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
		 </xsl:call-template>
         </Nationality>
         <!-- �����˳������� -->	
         <Birthday><xsl:value-of select="Rcgn_Brth_Dt" /></Birthday>	
         <!--�������Ա�-->
         <Sex> 
         <xsl:choose>
         <xsl:when test="//Rcgn_Gnd_Cd !=''  ">
         <xsl:call-template name="tran_sex">
			<xsl:with-param name="Sex">
				<xsl:value-of select="//Rcgn_Gnd_Cd" />
			</xsl:with-param>
         </xsl:call-template>
         </xsl:when>
         <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
         </Sex>
         
         <!--  ������ְҵ����-->	
         <JobCode>3010101</JobCode>
         <!--��������������  -->	
         <YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Rcgn_Yr_IncmAm)" /></YearSalary>
         <!--  δ�������ۼƱ���-->		
         <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)" /></CovSumAmt>
         <DestCount><xsl:value-of select="Rvl_Rcrd_Num"></xsl:value-of></DestCount>
         <xsl:choose>
         <xsl:when test="Rvl_Rcrd_Num ='0'"></xsl:when>
         <xsl:when test="Rvl_Rcrd_Num !='0'">
         <xsl:for-each select="Lnd_List/Lnd_Detail">
           <!-- ������ǰ��Ŀ�ĵ� -->
            <Destination><xsl:value-of select="Rcgn_LvFr_Pps_Lnd" /></Destination>
         <Name>AAA</Name>
         </xsl:for-each>	
         </xsl:when>
         </xsl:choose>
       
       </Insured>
       
      <BnfCount><xsl:value-of select="Rvl_Rcrd_Num_1"/></BnfCount>  <!-- ѭ����¼�� Y-->
         <xsl:choose>
         <xsl:when test="Rvl_Rcrd_Num_1 ='0'"></xsl:when>
			<xsl:when test="Rvl_Rcrd_Num_1 !='0'">
             <xsl:for-each select="Benf_List/Benf_Detail">
            <Bnf>
             <!-- ���������������ʹ��� -->
              <Type><xsl:value-of select="AgIns_Benf_TpCd" /></Type>	
              <!--�����˺ͱ����˹�ϵ���ʹ���  -->	
              <RelaToInsured><xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured>
              <!-- �����˳������� -->		
              <Birthday><xsl:value-of select="Benf_Brth_Dt" /></Birthday>	
              <!-- �������Ա���� -->	
              <Sex>
               <xsl:call-template name="tran_sex">
					<xsl:with-param name="Sex">
					<xsl:value-of select="Benf_Gnd_Cd" />
				</xsl:with-param>
				</xsl:call-template>
              </Sex>
              <!-- �����˹��ҵ������� -->		
              <Nationality>
              <xsl:call-template name="tran_Nationality">
							<xsl:with-param name="Nationality">
									<xsl:value-of select="Benf_Nat_Cd" />
							</xsl:with-param>
				</xsl:call-template>
              </Nationality>
              <!-- �������������ֵ -->		
              <Grade><xsl:value-of select="AgIns_Bnft_Ord_Val" /></Grade>	
              <!--�������  -->	
              <Lot><xsl:value-of select="Bnft_Pct" /></Lot>	
              </Bnf>
         </xsl:for-each>
       </xsl:when>
         </xsl:choose>
         
         
       
       
            
         <!-- #ѭ����¼������ -->
         <xsl:choose>
           <xsl:when test="Rvl_Rcrd_Num_2 ='0'"></xsl:when>
			<xsl:when test="Rvl_Rcrd_Num_2 !='0'">
             <xsl:for-each select="Life_List/Life_Detail">	
           <Risk>
           <!--���ֱ��  -->	
              <RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
              <!-- �������� -->	
              <RiskName><xsl:value-of select="Cvr_Nm " /></RiskName>	
              <!-- �����ձ�־ -->	
              <MainRiskFlag><xsl:value-of select="MainAndAdlIns_Ind" /></MainRiskFlag>	
              <!-- Ͷ������ -->	
              <Mult><xsl:value-of select="Ins_Cps" /></Mult>	
              <!--  Ͷ������-->	
              <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Ins_Cvr)"/></Amnt>	
              <!-- ���ѽ�� -->	
              <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>	
              <!-- Ͷ��������Ϣ -->	
              <ScmInfo><xsl:value-of select="Ins_Scm_Inf" /></ScmInfo>	
              <!-- ��ѡ������ʱ��ս�� -->	
              <OptPartDieInsAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Opt_Part_DieIns_Amt)" /></OptPartDieInsAmt>		
              <!-- �״ζ���׷�ӱ��� -->
              <FstAddExrPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(FTm_Extr_Adl_InsPrem)" /></FstAddExrPrem>	
              <!-- Ͷ�ʷ�ʽ���� -->	
              <InvestType><xsl:value-of select="Ivs_MtdCd" /></InvestType>	
              <!--�����մ����ͬ���  -->	
              <LoanContId><xsl:value-of select="AgIns_Ln_Ctr_ID" /></LoanContId>	
              <!-- �����ͬʧЧ���� -->	
              <LoanExpDate><xsl:value-of select="Ln_Ctr_ExpDt" /></LoanExpDate>	
              <!-- δ�������� -->	
              <UnPayLoanAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Upd_Loan_Amt)" /></UnPayLoanAmt>	
              <!-- ��������ƾ֤���� -->	
              <MainContPrtNo><xsl:value-of select="PrimBlInsPolcyVchr_No" /></MainContPrtNo>	
              <!--��������������  -->	
              <InsuYearFlag>
              <xsl:choose>
              <xsl:when test="InsPrem_PyF_Cyc_Cd= '0100' ">Y</xsl:when>
              <xsl:otherwise>A</xsl:otherwise>
              </xsl:choose>
              
              </InsuYearFlag>	
              <!-- �������� -->	
              <InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>	
              <!--�������ڴ���  -->	
              <InsCycCd><xsl:value-of select="Ins_Cyc_Cd" /></InsCycCd>		
              <!--���ѽɷѷ�ʽ����  -->
              <PayIntv>
              <xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="InsPrem_PyF_MtdCd" />
						</xsl:with-param>
			  </xsl:call-template>
              </PayIntv>	
              <!--���ѽɷ�����  -->	
              <PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear>	
              <!-- ���ѽɷ����ڴ��� -->	
              <PayEndYearFlag>
              <xsl:choose>
              <xsl:when test="InsPrem_PyF_Cyc_Cd= '0100' ">Y</xsl:when>
              <xsl:otherwise>A</xsl:otherwise>
              </xsl:choose>
              </PayEndYearFlag>	
              <!--�����ȡ������  -->	
              <AnutyGetType><xsl:value-of select="Anuty_Drw_CgyCd" /></AnutyGetType>
              <!-- �����ȡ���� -->		
              <AnutyGetNum><xsl:value-of select="Anuty_Drw_Prd_Num" /></AnutyGetNum>	
              <!-- �����ȡ���ڴ��� -->	
              <AnutyGetCycle><xsl:value-of select="Anuty_Drw_Cyc_Cd" /></AnutyGetCycle>
              <!-- �����ʽ���� -->		
              <AnutyDealType><xsl:value-of select="Anuty_Pcsg_MtdCd" /></AnutyDealType>		
              <!--  ���ڱ��ս���ȡ��ʽ������-->
              <FullPayPremGetType><xsl:value-of select="ExpPrmmRcvModCgyCd" /></FullPayPremGetType>
              <!-- �������ȡ���ڴ��� -->		
              <SurviveGetCycle><xsl:value-of select="SvBnf_Drw_Cyc_Cd" /></SurviveGetCycle>	
              <!-- ��������ʽ���� -->	
              <BonusGetMode>
              <xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							 <xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
						</xsl:with-param>
					</xsl:call-template>
             </BonusGetMode>	
              <!--Լ�����ѵ潻��־  -->	
              <AutoPayFlag><xsl:value-of select="ApntInsPremPyAdvnInd" /></AutoPayFlag>		
              <RsrvFld1><xsl:value-of select="Rsrv_Fld_1" /></RsrvFld1>		<!--  #�����ֶ�һ-->
              <RsrvFld2><xsl:value-of select="Rsrv_Fld_2" /></RsrvFld2>		<!-- #�����ֶζ� -->
              <RsrvFld3><xsl:value-of select="Rsrv_Fld_3" /></RsrvFld3>		<!-- #�����ֶ��� -->
              <RsrvFld4><xsl:value-of select="Rsrv_Fld_4" /></RsrvFld4>		<!-- #�����ֶ��� -->
              <RsrvFld5><xsl:value-of select="Rsrv_Fld_5" /></RsrvFld5>		<!-- #�����ֶ��� -->
              <RsrvFld6><xsl:value-of select="Rsrv_Fld_6" /></RsrvFld6>		<!--  #�����ֶ���-->
              <RsrvFld7><xsl:value-of select="Rsrv_Fld_7" /></RsrvFld7>		<!-- #�����ֶ��� -->
              <RsrvFld8><xsl:value-of select="Rsrv_Fld_8" /></RsrvFld8>		<!-- #�����ֶΰ� -->
              <RsrvFld9><xsl:value-of select="Rsrv_Fld_9" /></RsrvFld9>		<!--  #�����ֶξ�-->
              <RsrvFld10><xsl:value-of select="Rsrv_Fld_10" /></RsrvFld10>	<!-- #�����ֶ�ʮ -->
             </Risk>
             
               
               <xsl:choose>
               <xsl:when test="//Rvl_Rcrd_Num ='0'"></xsl:when>
		       <xsl:when test="//Rvl_Rcrd_Num !='0'">
		      
               <xsl:for-each select="//Ilivar_Detail">
              	
              <Ilivar>
                <!-- Ͷ�����ͷ������ -->
                <InvestTypePct><xsl:value-of select="//Ivs_Tp_Alct_Pctg" /></InvestTypePct>		
                <!-- ׷�ӱ��շѷ������ -->
                <AddPremPct><xsl:value-of select="//Adl_Ins_Fee_Alct_Pctg" /></AddPremPct>	
              </Ilivar>
               </xsl:for-each>	
               </xsl:when>
       </xsl:choose>
       </xsl:for-each>
        </xsl:when>
       </xsl:choose>
      
     
           
          
           <!--�����ɷѳ������-->
          <PolicyCshEx_Cd><xsl:value-of select="InsPolcy_PyF_CshEx_Cd" /></PolicyCshEx_Cd>
          <!--�����ɷѱ��ִ���-->
          <PolicyCcyCd><xsl:value-of select="InsPolcy_PyF_CcyCd" /></PolicyCcyCd>
          <!--�����տͻ��������ʹ���-->
          <AgIns_Cst_Line_TpCd><xsl:value-of select="AgIns_Cst_Line_TpCd" /></AgIns_Cst_Line_TpCd>
          <!--����ר��������  -->
          <Ins_Prj_CgyCd><xsl:value-of select="Ins_Prj_CgyCd" /></Ins_Prj_CgyCd>
          <!--���Ա�ע-->
          <PstcrptRmrk><xsl:value-of select="Pstcrpt_Rmrk" /></PstcrptRmrk>
          <RsrvFld11><xsl:value-of select="Rsrv_Fld_11" /></RsrvFld11><!-- #�����ֶ�ʮһ -->
          <RsrvFld12><xsl:value-of select="Rsrv_Fld_12" /></RsrvFld12><!-- #�����ֶ�ʮ�� -->
          <RsrvFld13><xsl:value-of select="Rsrv_Fld_13" /></RsrvFld13><!-- #�����ֶ�ʮ�� -->
          <RsrvFld14><xsl:value-of select="Rsrv_Fld_14" /></RsrvFld14><!-- #�����ֶ�ʮ�� -->
          <RsrvFld15><xsl:value-of select="Rsrv_Fld_15" /></RsrvFld15><!--#�����ֶ�ʮ��  -->
          <RsrvFld16><xsl:value-of select="Rsrv_Fld_16" /></RsrvFld16><!--#�����ֶ�ʮ��  -->
          <RsrvFld17><xsl:value-of select="Rsrv_Fld_17" /></RsrvFld17><!--#�����ֶ�ʮ��  -->
          <RsrvFld18><xsl:value-of select="Rsrv_Fld_18" /></RsrvFld18><!--#�����ֶ�ʮ��  -->
          <RsrvFld19><xsl:value-of select="Rsrv_Fld_19" /></RsrvFld19><!-- #�����ֶ�ʮ�� -->
          <RsrvFld20><xsl:value-of select="Rsrv_Fld_20" /></RsrvFld20><!--#�����ֶζ�ʮ  -->
		</Body>
	</xsl:template>
	
	
	<!-- �Ա� -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- �� -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- Ů -->
	</xsl:template>

	<!-- ���ս���ȡ��ʽ -->
	<xsl:template name="tran_GetIntv">
		<xsl:param name="GetIntv"></xsl:param>
		<xsl:if test="$GetIntv = 0">0</xsl:if>     <!--����  -->
		<xsl:if test="$GetIntv = 1">1</xsl:if>     <!-- ���� -->
		<xsl:if test="$GetIntv = 12">12</xsl:if>  <!-- ���� -->
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
		<xsl:if test="$BonusGetMode = 0">2</xsl:if><!-- ֱ�Ӹ��� -->
		<xsl:if test="$BonusGetMode = 1">3</xsl:if><!-- �ֽ����� -->
		<xsl:if test="$BonusGetMode = 2">1</xsl:if><!-- �ۻ���Ϣ -->
		<xsl:if test="$BonusGetMode = 3">5</xsl:if><!-- ����� -->
	</xsl:template>

	<!-- �����Ľɷѷ�ʽ -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 01">-1</xsl:when><!-- �����ڽ� -->
			<xsl:when test="$payintv = 02">0</xsl:when><!-- ���� -->
			<xsl:when test="$payintv = 03">12</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = 04">98</xsl:when><!-- ����ĳȷ���� -->
			<xsl:when test="$payintv = 05">99</xsl:when><!-- ������ -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- �������ͷ�ʽ?    ��Ҫ�ͺ���ȷ�� -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 1">1</xsl:when>
			<xsl:when test="$pbSendMode = 2">1</xsl:when>
			<xsl:when test="$pbSendMode = 3">1</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- ��������/�����־ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 01">M</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 02">M</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 03">Y</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 04">M</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 99">D</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="Y" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- �������֤���� -->
			<xsl:when test="$idtype = '1022'">2</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = '1032'">D</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = '1021'">A</xsl:when><!-- ��ž�ʿ��֤ -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- ���ڲ� -->
			<xsl:when test="$idtype = '1080'">B</xsl:when><!-- (�۰�)����֤��ͨ��֤ -->
			<xsl:when test="$idtype = '1070'">B</xsl:when><!-- ̨�����ڵ�ͨ��֤-->
			<xsl:when test="$idtype = '1050'">1</xsl:when><!-- ����-->
			<xsl:when test="$idtype = '1051'">1</xsl:when><!-- (���)����-->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- (�й�)����-->
			<xsl:when test="$idtype = '1060'">5</xsl:when><!-- ѧ��֤-->
			<xsl:when test="$idtype = '1999'">6</xsl:when><!-- ��������֤��-->
			<xsl:when test="$idtype = '2999'">6</xsl:when><!-- �Թ�����֤��-->
			<xsl:when test="$idtype = '1100'">3</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = '1011'">C</xsl:when><!-- ��ʱ�������֤ -->
			<xsl:when test="$idtype = '1160'">E</xsl:when><!-- ̨��������֤ ̨��֤ -->
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- ���� -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Ͷ���˺ͱ����˵� ��ϵ -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:choose>
			<xsl:when test="$RelaToInsured = 0133043">00</xsl:when><!--���� -->
			<xsl:when test="$RelaToInsured = 0133010">02</xsl:when><!--��ż -->
			<xsl:when test="$RelaToInsured = 0133015">03</xsl:when><!--����  03 ��Ů   -->
			<xsl:when test="$RelaToInsured = 0133016">03</xsl:when><!--ĸ�� 03 ��Ů -->
			<xsl:when test="$RelaToInsured = 0133011">01</xsl:when><!--���� 01 ��ĸ -->
			<xsl:when test="$RelaToInsured = 0133012">01</xsl:when><!--Ů�� 01 ��ĸ-->
			<xsl:when test="$RelaToInsured = 0133017">04</xsl:when><!--�游-->
			<xsl:when test="$RelaToInsured = 0133018">04</xsl:when><!--��ĸ-->
			<xsl:when test="$RelaToInsured = 0133013">04</xsl:when><!--���� -->
			<xsl:when test="$RelaToInsured = 0133014">04</xsl:when><!--��Ů -->
			<xsl:when test="$RelaToInsured = 0133020">06</xsl:when><!--��� -->
			<xsl:when test="$RelaToInsured = 0133019">06</xsl:when><!--���-->
			<xsl:when test="$RelaToInsured = 0133035">06</xsl:when><!--�ܵ�-->
			<xsl:when test="$RelaToInsured = 0133036">06</xsl:when><!--����-->
			<xsl:when test="$RelaToInsured = 0133021">06</xsl:when><!--�������� -->
			<xsl:when test="$RelaToInsured = 0133002">06</xsl:when><!--ͬ�� -->
			<xsl:when test="$RelaToInsured = 0133001">06</xsl:when><!--����--><!-- ����û���Ѱ���ֻ��Ϊ����� Update 20130226 -->
			<xsl:otherwise>--</xsl:otherwise><!-- û��ӳ��Ķ������� -->
		</xsl:choose>
	</xsl:template>
	
		<!-- �����˺������˵� ��ϵ -->
	<xsl:template name="tran_Bnfrelation">
		<xsl:param name="BnfRelaToInsured">00</xsl:param>
		<xsl:choose>
			<xsl:when test="$BnfRelaToInsured = 0133043">00</xsl:when><!--���� -->
			<xsl:when test="$BnfRelaToInsured = 0133010">02</xsl:when><!--��ż -->
			<xsl:when test="$BnfRelaToInsured = 0133015">01</xsl:when><!--����     -->
			<xsl:when test="$BnfRelaToInsured = 0133016">01</xsl:when><!--ĸ��  -->
			<xsl:when test="$BnfRelaToInsured = 0133011">03</xsl:when><!--���� -->
			<xsl:when test="$BnfRelaToInsured = 0133012">03</xsl:when><!--Ů�� -->
			<xsl:when test="$BnfRelaToInsured = 0133017">04</xsl:when><!--�游-->
			<xsl:when test="$BnfRelaToInsured = 0133018">04</xsl:when><!--��ĸ-->
			<xsl:when test="$BnfRelaToInsured = 0133013">04</xsl:when><!--���� -->
			<xsl:when test="$BnfRelaToInsured = 0133014">04</xsl:when><!--��Ů -->
			<xsl:when test="$BnfRelaToInsured = 0133020">06</xsl:when><!--��� -->
			<xsl:when test="$BnfRelaToInsured = 0133019">06</xsl:when><!--���-->
			<xsl:when test="$BnfRelaToInsured = 0133035">06</xsl:when><!--�ܵ�-->
			<xsl:when test="$BnfRelaToInsured = 0133036">06</xsl:when><!--����-->
			<xsl:when test="$BnfRelaToInsured = 0133021">06</xsl:when><!--�������� -->
			<xsl:when test="$BnfRelaToInsured = 0133002">06</xsl:when><!--ͬ�� -->
			<xsl:when test="$BnfRelaToInsured = 0133001">06</xsl:when><!--����--><!-- ����û���Ѱ���ֻ��Ϊ����� Update 20130226 -->
			<xsl:otherwise>--</xsl:otherwise><!-- û��ӳ��Ķ������� -->
		</xsl:choose>
	</xsl:template>

    <!-- ������֪ -->
    <xsl:template name="healFlag">
    <xsl:param name="HealFlag">N</xsl:param>
    <xsl:if test="$HealFlag = 0">N</xsl:if><!-- �޽�����֪ -->
    <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- �н�����֪ -->
     </xsl:template>
     
</xsl:stylesheet>