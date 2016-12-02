<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
			    
			     <!--交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- 银行网点 -->
				<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
				<!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<!--柜员号 -->
				<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
				<!-- 交易流水号 -->
				<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				<xsl:copy-of select="Head/*"/>
			</Head>
		<!-- 报文体 -->
		<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- 报文体 -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
	
		
		 <!-- 保险公司编号 -->
		 <InsCoID><xsl:value-of select="Ins_Co_ID" /></InsCoID>
		 <Appnt>
		 <!-- 投保人国家地区代码 -->
         <NationalityCode>
          <xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="Plchd_Nat_Cd" />
					</xsl:with-param>
          </xsl:call-template>
        </NationalityCode>
         <!-- 投保人出生日期 -->	
         <Birthday><xsl:value-of select="Plchd_Brth_Dt" /></Birthday>	
         <!--投保人性别代码  -->	
         <Sex> 
         <xsl:call-template name="tran_sex">
			<xsl:with-param name="Sex">
				<xsl:value-of select="Plchd_Gnd_Cd" />
			</xsl:with-param>
         </xsl:call-template>
         </Sex>
         <!-- 投保人职业代码 -->		
         <JobCode><xsl:value-of select="Plchd_Ocp_Cd" /></JobCode>
         <!--投保人年收入金额  -->		
         <YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Plchd_Yr_IncmAm)" /></YearSalary>	
         <!--家庭年收入金额  -->	
         <FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Fam_Yr_IncmAm)" /></FamilyYearSalary>		
         <!-- 保费预算金额 -->
         <PremiumBudget><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Bdgt_Amt)" /></PremiumBudget>
         <!-- 居民类型代码 -->		
         <DenType><xsl:value-of select="Rsdnt_TpCd" /></DenType>		
         <!-- 客户贡献度数值 -->
         <CstCtbNVal><xsl:value-of select="Cst_Ctb_NVal" /></CstCtbNVal>
         <!-- 投保人和被保人关系类型代码 -->		
         <RelaToInsured>	
         <xsl:call-template name="tran_Bnfrelation">
           <xsl:with-param name="BnfRelaToInsured">
					<xsl:value-of
						select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
		</xsl:call-template>
		</RelaToInsured>
		</Appnt>
		
		
         <!-- 被保人国家地区代码 -->	
        <Insured>
       
         <Nationality>
         <xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
		 </xsl:call-template>
         </Nationality>
         <!-- 被保人出生日期 -->	
         <Birthday><xsl:value-of select="Rcgn_Brth_Dt" /></Birthday>	
         <!--被保人性别-->
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
         
         <!--  被保人职业代码-->	
         <JobCode>3010101</JobCode>
         <!--被保人年收入金额  -->	
         <YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Rcgn_Yr_IncmAm)" /></YearSalary>
         <!--  未成年人累计保额-->		
         <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)" /></CovSumAmt>
         <DestCount><xsl:value-of select="Rvl_Rcrd_Num"></xsl:value-of></DestCount>
         <xsl:choose>
         <xsl:when test="Rvl_Rcrd_Num ='0'"></xsl:when>
         <xsl:when test="Rvl_Rcrd_Num !='0'">
         <xsl:for-each select="Lnd_List/Lnd_Detail">
           <!-- 被保人前往目的地 -->
            <Destination><xsl:value-of select="Rcgn_LvFr_Pps_Lnd" /></Destination>
         <Name>AAA</Name>
         </xsl:for-each>	
         </xsl:when>
         </xsl:choose>
       
       </Insured>
       
      <BnfCount><xsl:value-of select="Rvl_Rcrd_Num_1"/></BnfCount>  <!-- 循环记录数 Y-->
         <xsl:choose>
         <xsl:when test="Rvl_Rcrd_Num_1 ='0'"></xsl:when>
			<xsl:when test="Rvl_Rcrd_Num_1 !='0'">
             <xsl:for-each select="Benf_List/Benf_Detail">
            <Bnf>
             <!-- 代理保险受益人类型代码 -->
              <Type><xsl:value-of select="AgIns_Benf_TpCd" /></Type>	
              <!--受益人和被保人关系类型代码  -->	
              <RelaToInsured><xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured>
              <!-- 受益人出生日期 -->		
              <Birthday><xsl:value-of select="Benf_Brth_Dt" /></Birthday>	
              <!-- 受益人性别代码 -->	
              <Sex>
               <xsl:call-template name="tran_sex">
					<xsl:with-param name="Sex">
					<xsl:value-of select="Benf_Gnd_Cd" />
				</xsl:with-param>
				</xsl:call-template>
              </Sex>
              <!-- 受益人国家地区代码 -->		
              <Nationality>
              <xsl:call-template name="tran_Nationality">
							<xsl:with-param name="Nationality">
									<xsl:value-of select="Benf_Nat_Cd" />
							</xsl:with-param>
				</xsl:call-template>
              </Nationality>
              <!-- 代理保险受益次序值 -->		
              <Grade><xsl:value-of select="AgIns_Bnft_Ord_Val" /></Grade>	
              <!--受益比例  -->	
              <Lot><xsl:value-of select="Bnft_Pct" /></Lot>	
              </Bnf>
         </xsl:for-each>
       </xsl:when>
         </xsl:choose>
         
         
       
       
            
         <!-- #循环记录条数二 -->
         <xsl:choose>
           <xsl:when test="Rvl_Rcrd_Num_2 ='0'"></xsl:when>
			<xsl:when test="Rvl_Rcrd_Num_2 !='0'">
             <xsl:for-each select="Life_List/Life_Detail">	
           <Risk>
           <!--险种编号  -->	
              <RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
              <!-- 险种名称 -->	
              <RiskName><xsl:value-of select="Cvr_Nm " /></RiskName>	
              <!-- 主附险标志 -->	
              <MainRiskFlag><xsl:value-of select="MainAndAdlIns_Ind" /></MainRiskFlag>	
              <!-- 投保份数 -->	
              <Mult><xsl:value-of select="Ins_Cps" /></Mult>	
              <!--  投保保额-->	
              <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Ins_Cvr)"/></Amnt>	
              <!-- 保费金额 -->	
              <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" /></Prem>	
              <!-- 投保方案信息 -->	
              <ScmInfo><xsl:value-of select="Ins_Scm_Inf" /></ScmInfo>	
              <!-- 可选部分身故保险金额 -->	
              <OptPartDieInsAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Opt_Part_DieIns_Amt)" /></OptPartDieInsAmt>		
              <!-- 首次额外追加保费 -->
              <FstAddExrPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(FTm_Extr_Adl_InsPrem)" /></FstAddExrPrem>	
              <!-- 投资方式代码 -->	
              <InvestType><xsl:value-of select="Ivs_MtdCd" /></InvestType>	
              <!--代理保险贷款合同编号  -->	
              <LoanContId><xsl:value-of select="AgIns_Ln_Ctr_ID" /></LoanContId>	
              <!-- 贷款合同失效日期 -->	
              <LoanExpDate><xsl:value-of select="Ln_Ctr_ExpDt" /></LoanExpDate>	
              <!-- 未还贷款金额 -->	
              <UnPayLoanAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Upd_Loan_Amt)" /></UnPayLoanAmt>	
              <!-- 主单保单凭证号码 -->	
              <MainContPrtNo><xsl:value-of select="PrimBlInsPolcyVchr_No" /></MainContPrtNo>	
              <!--保险年期类别代码  -->	
              <InsuYearFlag>
              <xsl:choose>
              <xsl:when test="InsPrem_PyF_Cyc_Cd= '0100' ">Y</xsl:when>
              <xsl:otherwise>A</xsl:otherwise>
              </xsl:choose>
              
              </InsuYearFlag>	
              <!-- 保险期限 -->	
              <InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>	
              <!--保险周期代码  -->	
              <InsCycCd><xsl:value-of select="Ins_Cyc_Cd" /></InsCycCd>		
              <!--保费缴费方式代码  -->
              <PayIntv>
              <xsl:call-template name="tran_Contpayintv">
						<xsl:with-param name="payintv">
							<xsl:value-of select="InsPrem_PyF_MtdCd" />
						</xsl:with-param>
			  </xsl:call-template>
              </PayIntv>	
              <!--保费缴费期数  -->	
              <PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear>	
              <!-- 保费缴费周期代码 -->	
              <PayEndYearFlag>
              <xsl:choose>
              <xsl:when test="InsPrem_PyF_Cyc_Cd= '0100' ">Y</xsl:when>
              <xsl:otherwise>A</xsl:otherwise>
              </xsl:choose>
              </PayEndYearFlag>	
              <!--年金领取类别代码  -->	
              <AnutyGetType><xsl:value-of select="Anuty_Drw_CgyCd" /></AnutyGetType>
              <!-- 年金领取期数 -->		
              <AnutyGetNum><xsl:value-of select="Anuty_Drw_Prd_Num" /></AnutyGetNum>	
              <!-- 年金领取周期代码 -->	
              <AnutyGetCycle><xsl:value-of select="Anuty_Drw_Cyc_Cd" /></AnutyGetCycle>
              <!-- 年金处理方式代码 -->		
              <AnutyDealType><xsl:value-of select="Anuty_Pcsg_MtdCd" /></AnutyDealType>		
              <!--  满期保险金领取方式类别代码-->
              <FullPayPremGetType><xsl:value-of select="ExpPrmmRcvModCgyCd" /></FullPayPremGetType>
              <!-- 生存金领取周期代码 -->		
              <SurviveGetCycle><xsl:value-of select="SvBnf_Drw_Cyc_Cd" /></SurviveGetCycle>	
              <!-- 红利处理方式代码 -->	
              <BonusGetMode>
              <xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							 <xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
						</xsl:with-param>
					</xsl:call-template>
             </BonusGetMode>	
              <!--约定保费垫交标志  -->	
              <AutoPayFlag><xsl:value-of select="ApntInsPremPyAdvnInd" /></AutoPayFlag>		
              <RsrvFld1><xsl:value-of select="Rsrv_Fld_1" /></RsrvFld1>		<!--  #保留字段一-->
              <RsrvFld2><xsl:value-of select="Rsrv_Fld_2" /></RsrvFld2>		<!-- #保留字段二 -->
              <RsrvFld3><xsl:value-of select="Rsrv_Fld_3" /></RsrvFld3>		<!-- #保留字段三 -->
              <RsrvFld4><xsl:value-of select="Rsrv_Fld_4" /></RsrvFld4>		<!-- #保留字段四 -->
              <RsrvFld5><xsl:value-of select="Rsrv_Fld_5" /></RsrvFld5>		<!-- #保留字段五 -->
              <RsrvFld6><xsl:value-of select="Rsrv_Fld_6" /></RsrvFld6>		<!--  #保留字段六-->
              <RsrvFld7><xsl:value-of select="Rsrv_Fld_7" /></RsrvFld7>		<!-- #保留字段七 -->
              <RsrvFld8><xsl:value-of select="Rsrv_Fld_8" /></RsrvFld8>		<!-- #保留字段八 -->
              <RsrvFld9><xsl:value-of select="Rsrv_Fld_9" /></RsrvFld9>		<!--  #保留字段九-->
              <RsrvFld10><xsl:value-of select="Rsrv_Fld_10" /></RsrvFld10>	<!-- #保留字段十 -->
             </Risk>
             
               
               <xsl:choose>
               <xsl:when test="//Rvl_Rcrd_Num ='0'"></xsl:when>
		       <xsl:when test="//Rvl_Rcrd_Num !='0'">
		      
               <xsl:for-each select="//Ilivar_Detail">
              	
              <Ilivar>
                <!-- 投资类型分配比例 -->
                <InvestTypePct><xsl:value-of select="//Ivs_Tp_Alct_Pctg" /></InvestTypePct>		
                <!-- 追加保险费分配比例 -->
                <AddPremPct><xsl:value-of select="//Adl_Ins_Fee_Alct_Pctg" /></AddPremPct>	
              </Ilivar>
               </xsl:for-each>	
               </xsl:when>
       </xsl:choose>
       </xsl:for-each>
        </xsl:when>
       </xsl:choose>
      
     
           
          
           <!--保单缴费钞汇代码-->
          <PolicyCshEx_Cd><xsl:value-of select="InsPolcy_PyF_CshEx_Cd" /></PolicyCshEx_Cd>
          <!--保单缴费币种代码-->
          <PolicyCcyCd><xsl:value-of select="InsPolcy_PyF_CcyCd" /></PolicyCcyCd>
          <!--代理保险客户条线类型代码-->
          <AgIns_Cst_Line_TpCd><xsl:value-of select="AgIns_Cst_Line_TpCd" /></AgIns_Cst_Line_TpCd>
          <!--保险专案类别代码  -->
          <Ins_Prj_CgyCd><xsl:value-of select="Ins_Prj_CgyCd" /></Ins_Prj_CgyCd>
          <!--附言备注-->
          <PstcrptRmrk><xsl:value-of select="Pstcrpt_Rmrk" /></PstcrptRmrk>
          <RsrvFld11><xsl:value-of select="Rsrv_Fld_11" /></RsrvFld11><!-- #保留字段十一 -->
          <RsrvFld12><xsl:value-of select="Rsrv_Fld_12" /></RsrvFld12><!-- #保留字段十二 -->
          <RsrvFld13><xsl:value-of select="Rsrv_Fld_13" /></RsrvFld13><!-- #保留字段十三 -->
          <RsrvFld14><xsl:value-of select="Rsrv_Fld_14" /></RsrvFld14><!-- #保留字段十四 -->
          <RsrvFld15><xsl:value-of select="Rsrv_Fld_15" /></RsrvFld15><!--#保留字段十五  -->
          <RsrvFld16><xsl:value-of select="Rsrv_Fld_16" /></RsrvFld16><!--#保留字段十六  -->
          <RsrvFld17><xsl:value-of select="Rsrv_Fld_17" /></RsrvFld17><!--#保留字段十七  -->
          <RsrvFld18><xsl:value-of select="Rsrv_Fld_18" /></RsrvFld18><!--#保留字段十八  -->
          <RsrvFld19><xsl:value-of select="Rsrv_Fld_19" /></RsrvFld19><!-- #保留字段十九 -->
          <RsrvFld20><xsl:value-of select="Rsrv_Fld_20" /></RsrvFld20><!--#保留字段二十  -->
		</Body>
	</xsl:template>
	
	
	<!-- 性别 -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 1">0</xsl:if><!-- 男 -->
		<xsl:if test="$Sex = 2">1</xsl:if><!-- 女 -->
	</xsl:template>

	<!-- 保险金领取方式 -->
	<xsl:template name="tran_GetIntv">
		<xsl:param name="GetIntv"></xsl:param>
		<xsl:if test="$GetIntv = 0">0</xsl:if>     <!--趸领  -->
		<xsl:if test="$GetIntv = 1">1</xsl:if>     <!-- 月领 -->
		<xsl:if test="$GetIntv = 12">12</xsl:if>  <!-- 年领 -->
	</xsl:template>
	
	
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>          <!--  0-一次给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>          <!--  1-月给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>          <!--  3-季给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>          <!--  6-半年给付  -->
		<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>          <!--  12-年给付  -->
	</xsl:template>
	
	<!-- 投被保人国籍 -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 0156">CHN</xsl:if>
		<xsl:if test="$Nationality = 0344"></xsl:if><!--香港 -->
		<xsl:if test="$Nationality = 0158"></xsl:if><!--台湾 -->
		<xsl:if test="$Nationality = 0446"></xsl:if><!--澳门 -->
		<xsl:if test="$Nationality = 0392">JAN</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = 0840">USA</xsl:if><!--美国-->
		<xsl:if test="$Nationality = 0643"></xsl:if><!--俄罗斯 -->
		<xsl:if test="$Nationality = 0826">ENG</xsl:if><!--英国 -->
		<xsl:if test="$Nationality = 0250">FRA</xsl:if><!--法国-->
		<xsl:if test="$Nationality = 0276">DEU</xsl:if><!--德国 -->
		<xsl:if test="$Nationality = 0410">KOR</xsl:if><!--韩国 -->
		<xsl:if test="$Nationality = 0702">SG</xsl:if><!--新加坡 -->
		<xsl:if test="$Nationality = 0360">INA</xsl:if><!--印度尼西亚-->
		<xsl:if test="$Nationality = 0356">IND</xsl:if><!--印度-->
		<xsl:if test="$Nationality = 0380">ITA</xsl:if><!--意大利 -->
		<xsl:if test="$Nationality = 0458">MY</xsl:if><!--马来西亚 -->
		<xsl:if test="$Nationality = 0764">THA</xsl:if><!--泰国-->
		<xsl:if test="$Nationality = 0999"></xsl:if><!--其他-->
	</xsl:template>

	<!-- 红利领取方式 -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = 0">2</xsl:if><!-- 直接给付 -->
		<xsl:if test="$BonusGetMode = 1">3</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$BonusGetMode = 2">1</xsl:if><!-- 累积生息 -->
		<xsl:if test="$BonusGetMode = 3">5</xsl:if><!-- 增额交清 -->
	</xsl:template>

	<!-- 保单的缴费方式 -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 01">-1</xsl:when><!-- 不定期交 -->
			<xsl:when test="$payintv = 02">0</xsl:when><!-- 趸交 -->
			<xsl:when test="$payintv = 03">12</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = 04">98</xsl:when><!-- 交至某确定年 -->
			<xsl:when test="$payintv = 05">99</xsl:when><!-- 终身交费 -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保单传送方式?    需要和核心确认 -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 1">1</xsl:when>
			<xsl:when test="$pbSendMode = 2">1</xsl:when>
			<xsl:when test="$pbSendMode = 3">1</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保障年期/年龄标志 -->
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

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- 公民身份证号码 -->
			<xsl:when test="$idtype = '1022'">2</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = '1032'">D</xsl:when><!-- 警官证 -->
			<xsl:when test="$idtype = '1021'">A</xsl:when><!-- 解放军士兵证 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = '1080'">B</xsl:when><!-- (港澳)回乡证及通行证 -->
			<xsl:when test="$idtype = '1070'">B</xsl:when><!-- 台湾来内地通行证-->
			<xsl:when test="$idtype = '1050'">1</xsl:when><!-- 护照-->
			<xsl:when test="$idtype = '1051'">1</xsl:when><!-- (外国)护照-->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- (中国)护照-->
			<xsl:when test="$idtype = '1060'">5</xsl:when><!-- 学生证-->
			<xsl:when test="$idtype = '1999'">6</xsl:when><!-- 个人其他证件-->
			<xsl:when test="$idtype = '2999'">6</xsl:when><!-- 对公其他证件-->
			<xsl:when test="$idtype = '1100'">3</xsl:when><!-- 驾照 -->
			<xsl:when test="$idtype = '1011'">C</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1160'">E</xsl:when><!-- 台湾居民身份证 台胞证 -->
			<xsl:otherwise>
					<xsl:value-of select="8" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 投保人和被保人的 关系 -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:choose>
			<xsl:when test="$RelaToInsured = 0133043">00</xsl:when><!--本人 -->
			<xsl:when test="$RelaToInsured = 0133010">02</xsl:when><!--配偶 -->
			<xsl:when test="$RelaToInsured = 0133015">03</xsl:when><!--父亲  03 子女   -->
			<xsl:when test="$RelaToInsured = 0133016">03</xsl:when><!--母亲 03 子女 -->
			<xsl:when test="$RelaToInsured = 0133011">01</xsl:when><!--儿子 01 父母 -->
			<xsl:when test="$RelaToInsured = 0133012">01</xsl:when><!--女儿 01 父母-->
			<xsl:when test="$RelaToInsured = 0133017">04</xsl:when><!--祖父-->
			<xsl:when test="$RelaToInsured = 0133018">04</xsl:when><!--祖母-->
			<xsl:when test="$RelaToInsured = 0133013">04</xsl:when><!--孙子 -->
			<xsl:when test="$RelaToInsured = 0133014">04</xsl:when><!--孙女 -->
			<xsl:when test="$RelaToInsured = 0133020">06</xsl:when><!--哥哥 -->
			<xsl:when test="$RelaToInsured = 0133019">06</xsl:when><!--姐姐-->
			<xsl:when test="$RelaToInsured = 0133035">06</xsl:when><!--弟弟-->
			<xsl:when test="$RelaToInsured = 0133036">06</xsl:when><!--妹妹-->
			<xsl:when test="$RelaToInsured = 0133021">06</xsl:when><!--其它亲属 -->
			<xsl:when test="$RelaToInsured = 0133002">06</xsl:when><!--同事 -->
			<xsl:when test="$RelaToInsured = 0133001">06</xsl:when><!--朋友--><!-- 核心没朋友啊，只能为其他喽 Update 20130226 -->
			<xsl:otherwise>--</xsl:otherwise><!-- 没有映射的都是这样 -->
		</xsl:choose>
	</xsl:template>
	
		<!-- 被保人和收益人的 关系 -->
	<xsl:template name="tran_Bnfrelation">
		<xsl:param name="BnfRelaToInsured">00</xsl:param>
		<xsl:choose>
			<xsl:when test="$BnfRelaToInsured = 0133043">00</xsl:when><!--本人 -->
			<xsl:when test="$BnfRelaToInsured = 0133010">02</xsl:when><!--配偶 -->
			<xsl:when test="$BnfRelaToInsured = 0133015">01</xsl:when><!--父亲     -->
			<xsl:when test="$BnfRelaToInsured = 0133016">01</xsl:when><!--母亲  -->
			<xsl:when test="$BnfRelaToInsured = 0133011">03</xsl:when><!--儿子 -->
			<xsl:when test="$BnfRelaToInsured = 0133012">03</xsl:when><!--女儿 -->
			<xsl:when test="$BnfRelaToInsured = 0133017">04</xsl:when><!--祖父-->
			<xsl:when test="$BnfRelaToInsured = 0133018">04</xsl:when><!--祖母-->
			<xsl:when test="$BnfRelaToInsured = 0133013">04</xsl:when><!--孙子 -->
			<xsl:when test="$BnfRelaToInsured = 0133014">04</xsl:when><!--孙女 -->
			<xsl:when test="$BnfRelaToInsured = 0133020">06</xsl:when><!--哥哥 -->
			<xsl:when test="$BnfRelaToInsured = 0133019">06</xsl:when><!--姐姐-->
			<xsl:when test="$BnfRelaToInsured = 0133035">06</xsl:when><!--弟弟-->
			<xsl:when test="$BnfRelaToInsured = 0133036">06</xsl:when><!--妹妹-->
			<xsl:when test="$BnfRelaToInsured = 0133021">06</xsl:when><!--其它亲属 -->
			<xsl:when test="$BnfRelaToInsured = 0133002">06</xsl:when><!--同事 -->
			<xsl:when test="$BnfRelaToInsured = 0133001">06</xsl:when><!--朋友--><!-- 核心没朋友啊，只能为其他喽 Update 20130226 -->
			<xsl:otherwise>--</xsl:otherwise><!-- 没有映射的都是这样 -->
		</xsl:choose>
	</xsl:template>

    <!-- 健康告知 -->
    <xsl:template name="healFlag">
    <xsl:param name="HealFlag">N</xsl:param>
    <xsl:if test="$HealFlag = 0">N</xsl:if><!-- 无健康告知 -->
    <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- 有健康告知 -->
     </xsl:template>
     
</xsl:stylesheet>