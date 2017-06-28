<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<!-- 银行渠道 -->
	<xsl:variable name="tSellType" select="/TX/TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_CGY_CODE"></xsl:variable>
	<!-- 核心渠道编码：柜面：0，手机银行：2，网银：1，自助终端：8-->
	<xsl:variable name="tLisSaleChnl">
		<xsl:call-template name="transChannel">
		    <xsl:with-param name="nTransChannel">
		      <xsl:value-of select="$tSellType" />
			</xsl:with-param>
	  	</xsl:call-template>
  	</xsl:variable>
	<xsl:template match="TX">
		<TranData>
			<Head>
			    <!--交易日期 -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--交易时间 -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- 银行网点 -->
				<xsl:variable name="tNodeNo" select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID"></xsl:variable>
				<xsl:choose>
					<xsl:when test="$tLisSaleChnl = '0'"><!--柜面渠道 -->
						<NodeNo><xsl:value-of select="$tNodeNo" /></NodeNo>
					</xsl:when>
					<xsl:when test="$tLisSaleChnl != '0'"><!--网销渠道:银行传网点就取值传给核心，否则传CCBWEB -->
						<xsl:if test="$tNodeNo = ''">
							<NodeNo>CCBWEB</NodeNo>
						</xsl:if>
						<xsl:if test="$tNodeNo != ''">
							<NodeNo><xsl:value-of select="$tNodeNo"/></NodeNo>
						</xsl:if>
					</xsl:when>
				</xsl:choose>
				<!-- 银行渠道编码 -->
				<SellType><xsl:value-of select="$tSellType"/></SellType>
				<!-- 本地节点，解密是用到 -->
				<LocalID><xsl:value-of select="TX_HEADER/LocalID"/></LocalID>
				<!-- 对端节点，解密是用到 -->
				<remoteID><xsl:value-of select="TX_HEADER/remoteID"/></remoteID>
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
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
	<Body>
		<!--核心渠道编码：柜面：0，手机银行：2，网银：1，自助终端：8-->
		<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
	    <!-- 投保单印刷号 -->
	    <xsl:choose>
			<xsl:when test="$tLisSaleChnl = '0'"><!--柜面渠道 -->
				<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_Bl_Prt_No)" /></ProposalPrtNo>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="tMaxPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.CreateMaxNo('CCBPRTNO','SN')"></xsl:variable> 
				<xsl:variable name="tPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.PrtNoTo8($tMaxPrtNo,'03')"></xsl:variable> 
				<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($tPrtNo)" /></ProposalPrtNo>
			</xsl:otherwise>
		</xsl:choose>
		<!--保单合同印刷号 (单证) 目前建行新接口不传,保单打印银行会传单证号-->
		<ContPrtNo></ContPrtNo>   
		<!-- 投保日期 -->
		<PolApplyDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></PolApplyDate>
           <!--投保人缴费账号  -->
           <AccNo><xsl:value-of select="Plchd_PyF_AccNo" /></AccNo>
           <!-- 账户姓名 工行不传账户名默认为投保人姓名-->
			 <AccName><xsl:value-of select="Plchd_Nm" /></AccName> 
           <!-- 保单递送方式  默认全为1-->
		<GetPolMode>1</GetPolMode> 
	    <!-- 健康告知 -->
		<HealthNotice>
			<xsl:call-template name="healFlag">
		    	<xsl:with-param name="HealFlag">
			    	<xsl:value-of select="Ntf_Itm_Ind"/>
			    </xsl:with-param>
			</xsl:call-template>
		</HealthNotice>
			<!-- 销售人员姓名  -->
           <SaleName><xsl:value-of select="BO_Sale_Stff_Nm" /></SaleName>
           <!-- 销售人员工号 -->
           <SaleStaff><xsl:value-of select="BO_Sale_Stff_ID" /></SaleStaff>
           <!-- 销售人员资格证书编号  -->
           <SaleCertNo><xsl:value-of select="Sale_Stff_AICSQCtf_ID" /></SaleCertNo>
           <!-- 销售人员资格证号  -->
           <!-- 建行一级分行号 -->
           <Lv1BrNo><xsl:value-of select="Lv1_Br_No" /></Lv1BrNo>
		<!-- 投保人 -->
		<Appnt>
		<!-- 投保人姓名 -->
			<Name>
				<xsl:value-of select="Plchd_Nm" />
			</Name>
			<!-- 投保人性别 -->
			<Sex>
				<xsl:call-template name="tran_sex">
					<xsl:with-param name="Sex">
						<xsl:value-of select="Plchd_Gnd_Cd" />
					</xsl:with-param>
				</xsl:call-template>
			</Sex>
			<!-- 投保人生日 -->
			<Birthday>
				<xsl:value-of select="Plchd_Brth_Dt" />
			</Birthday>
			<!-- 投保人证件类型 -->
			<IDType>
				<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of select="Plchd_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</IDType>
			<!-- 投保人证件号 -->
			<IDNo>
				<xsl:value-of select="Plchd_Crdt_No" />
			</IDNo>
			<!-- 投保人证件失效日期-->
			<xsl:choose>
				<xsl:when test="Plchd_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Plchd_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
			</xsl:choose>				
			<!-- 投保人国籍 -->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Plchd_Nat_Cd"/>
				</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- 投保人详细地址内容 -->
			<AddressContent><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></AddressContent>
			<!-- 投保人固定电话国内区号 -->
			<FixTelDmstDstcNo><xsl:value-of select="PlchdFixTelDmstDstcNo" /></FixTelDmstDstcNo>
			<!-- 投保人移动电话国际区号 -->
			<MobileItlDstcNo><xsl:value-of select="PlchdMoveTelItlDstcNo" /></MobileItlDstcNo>
			<!-- 投保人国家地区代码 -->
			<NationalityCode><xsl:value-of select="Plchd_Nat_Cd" /></NationalityCode>
			<!-- 省必录项 -->
			<Province><xsl:value-of select="Plchd_Prov_Cd" /></Province>
			<!-- 市 必录项-->
			<City><xsl:value-of select="Plchd_City_Cd" /></City>
			<!-- 区必录项-->
			<County><xsl:value-of select="Plchd_CntyAndDstc_Cd" /></County>
			<!-- 投保人地址 -->
			<Address><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></Address>
			<!-- 省非必填 -->
			<WorkProvince></WorkProvince>
			<!-- 市 非必填-->
			<WorkCity></WorkCity>
			<!-- 区非必填-->
			<WorkCounty></WorkCounty>
			<!--单位地址 -->
			<WorkAddress><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></WorkAddress>
			<!-- 投保人邮政编码-->
			<ZipCode><xsl:value-of select="Plchd_ZipECD" /></ZipCode>
			<!-- 投保人固定电话-->
			<Phone>
				<xsl:value-of select="PlchdFixTelDmstDstcNo" /><xsl:value-of select="Plchd_Fix_TelNo" />
			</Phone>
			<!-- 投保人移动电话-->
			<Mobile>
				<xsl:value-of select="Plchd_Move_TelNo" />
			</Mobile>				
			<!-- 投保人邮箱-->
			<Email>
			<xsl:value-of select="Plchd_Email_Adr" />
			</Email>
			<!-- 投保人职业代码-->
			<JobCode>
			<xsl:call-template name="tran_JobCode">
		      <xsl:with-param name="JobCode">
	            <xsl:value-of select="Plchd_Ocp_Cd"/>
		      </xsl:with-param>
	        </xsl:call-template>
			</JobCode>
			<!-- 投保人年收入 -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Plchd_Yr_IncmAm)"/></YearSalary>
			<!-- 投保人家庭年收入 -->
			<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Fam_Yr_IncmAm)"/></FamilyYearSalary>
			<!-- 投保人与被保人关系代码 -->
			<RelaToInsured>
				<xsl:call-template name="tran_relation">
					<xsl:with-param name="RelaToInsured">
						<xsl:value-of select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</RelaToInsured>				
			<!-- <RelaToInsured2><xsl:value-of select="Plchd_And_Rcgn_ReTpCd" /></RelaToInsured2> -->				
			<!-- 投保人居民类型 -->
			<DenType>
			  <xsl:call-template name="tran_Rsdnt_TpCd">
			    <xsl:with-param name="Rsdnt_TpCd">
			      <xsl:value-of select="Rsdnt_TpCd" />
				</xsl:with-param>
			  </xsl:call-template>
			</DenType>
		</Appnt>
		<!-- 被保人 -->
		<Insured>
		<!-- 被保人姓名 -->
			<Name>
				<xsl:value-of select="Rcgn_Nm" />
			</Name>
			<!-- 被保人性别 -->
			<Sex>
				<xsl:call-template name="tran_sex">
					<xsl:with-param name="Sex">
						<xsl:value-of select="Rcgn_Gnd_Cd" />
					</xsl:with-param>
				</xsl:call-template>
			</Sex>
			<!-- 被保人生日 -->
			<Birthday>
				<xsl:value-of select="Rcgn_Brth_Dt" />
			</Birthday>
			<!-- 被保人证件类型-->
			<IDType>
				<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of select="Rcgn_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</IDType>
			<!-- 被保人证件号-->
			<IDNo>
				<xsl:value-of select="Rcgn_Crdt_No" />
			</IDNo>
			<!-- 被保人证件失效日期-->
			<xsl:choose>
				<xsl:when test="Rcgn_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Rcgn_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
				<!-- 被保人国籍-->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- 被保人详细地址内容 -->
			<AddressContent><xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" /></AddressContent>
			<!-- 被保人固定电话国内区号 -->
			<FixTelDmstDstcNo><xsl:value-of select="RcgnFixTelDmst_DstcNo" /></FixTelDmstDstcNo>
			<!-- 被保人移动电话国际区号 -->
			<MobileItlDstcNo><xsl:value-of select="RcgnMoveTelItnlDstcNo" /></MobileItlDstcNo>
			<!-- 被保人国家地区代码 -->
			<NationalityCode><xsl:value-of select="Rcgn_Nat_Cd" /></NationalityCode>
			<!-- 省必录项 -->
			<Province><xsl:value-of select="Rcgn_Prov_Cd" /></Province>
			<!-- 市 必录项-->
			<City><xsl:value-of select="Rcgn_City_Cd" /></City>
			<!-- 区必录项-->
			<County><xsl:value-of select="Rcgn_CntyAndDstc_Cd" /></County>
			<!-- 被保人地址-->
			<Address><xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" /></Address>
			<!-- 省非必填 -->
			<WorkProvince></WorkProvince>
			<!-- 市 非必填-->
			<WorkCity></WorkCity>
			<!-- 区非必填-->
			<WorkCounty></WorkCounty>
			<!--单位地址 -->
			<WorkAddress></WorkAddress>
			<!-- 被保人邮编-->
			<ZipCode>
				<xsl:value-of select="Rcgn_ZipECD" />
			</ZipCode>
			<!-- 被保人固定电话-->
			<Phone>
				<xsl:value-of select="RcgnFixTelDmst_DstcNo" /><xsl:value-of select="Rcgn_Fix_TelNo" />
			</Phone>
			<!-- 被保人移动号码-->
			<Mobile>
				<xsl:value-of select="Rcgn_Move_TelNo" />
			</Mobile>
			<!-- 被保人邮箱-->
			<Email>
				<xsl:value-of select="Rcgn_Email_Adr" />
			</Email>
			<!-- 被保人职业代码-->
			<JobCode>
			<xsl:call-template name="tran_JobCode">
		      <xsl:with-param name="JobCode">
	            <xsl:value-of select="Rcgn_Ocp_Cd"/>
		      </xsl:with-param>
	        </xsl:call-template>
			</JobCode>
			<!-- 被保人年收入 -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Rcgn_Yr_IncmAm)" /></YearSalary>
			<!-- 未成年被保险人在其他保险公司累计身故保额 -->
            <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)"/></CovSumAmt>
			<!--客户风险承受能力代码-->
			<RiskAssessmentResult><xsl:value-of select="Cst_Rsk_Tlrnc_Cpy_Cd" /></RiskAssessmentResult>
			<!--风险测评有效期-->
			<RiskAssessmentEndDate><xsl:value-of select="Rsk_Evlt_AvlDt" /></RiskAssessmentEndDate>
			<!--预算金额-->
			<PremiumBudget><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Bdgt_Amt)" /></PremiumBudget>
		</Insured>
		<!-- 受益人 -->
		<xsl:choose>
			<xsl:when
				test="Benf_Num ='0'">
			</xsl:when>
			<xsl:when
				test="Benf_Num !='0'">
				<xsl:for-each select="Benf_List/Benf_Detail">
				<Bnf>
				<!-- 受益人类型代码 -->
				<Type><xsl:value-of select="AgIns_Benf_TpCd" /></Type>
				<!-- 受益人序号 -->
				<BenfSN><xsl:value-of select="Benf_SN" /></BenfSN>
				<!-- 受益人受益顺序 -->
				<Grade><xsl:value-of select="Benf_Bnft_Seq" /></Grade>
				<!-- 受益人姓名 -->
				<Name><xsl:value-of select="Benf_Nm" /></Name>
				<!-- 受益人性别 -->
			     <Sex>
				   <xsl:call-template name="tran_sex">
					  <xsl:with-param name="Sex">
						 <xsl:value-of select="Benf_Gnd_Cd" />
					  </xsl:with-param>
				   </xsl:call-template>
			    </Sex>
			    <!-- 受益人出生日期 -->
				<Birthday>
					<xsl:value-of
						select="Benf_Brth_Dt" />
				</Birthday>
				<!-- 受益人证件类型 -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of
								select="Benf_Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- 受益人证件号 -->
				<IDNo><xsl:value-of select="Benf_Crdt_No" /></IDNo>
				<!-- 受益人证件失效日期-->
				<xsl:choose>
					<xsl:when test="Benf_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
					<xsl:otherwise><IdExpDate><xsl:value-of select="Benf_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
				</xsl:choose>						
 				<!-- 受益人国籍-->
				<Nationality>
					<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
							<xsl:value-of select="Benf_Nat_Cd" />
					</xsl:with-param>
					</xsl:call-template>
				</Nationality>						
				<!-- 受益人与被保人关系代码 -->
				<RelaToInsured>
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="Benf_And_Rcgn_ReTpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured>
				<!-- <RelaToInsured3><xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured3> -->	
				<!-- 受益人受益比例 -->
				<Lot><xsl:value-of select="Bnft_Pct" /></Lot>
				<!-- 受益人详细地址内容 -->
				<AddressContent><xsl:value-of select="Benf_Dtl_Adr_Cntnt" /></AddressContent>
				<!-- 受益人通讯地址 -->
				<BenfCommAdr><xsl:value-of select="Benf_Comm_Adr" /></BenfCommAdr>
				<BeneficType>N</BeneficType>
				</Bnf>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
		<xsl:variable name="MainCode">
		  <xsl:value-of select="Busi_List/Busi_Detail[MainAndAdlIns_Ind='1']/Cvr_ID"/><!--主附险标志1：主险 -->
		</xsl:variable>
		<xsl:choose>
			<!-- 险种个数 -->
			<xsl:when test="Cvr_Num ='0'">
			</xsl:when>
			<xsl:when test="Cvr_Num !='0'">
				<xsl:for-each select="Busi_List/Busi_Detail">
					<Risk>
						<!-- 险种编号 -->
						<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
						<!--主险种编码 -->
						<MainRiskCode><xsl:value-of select="$MainCode"/></MainRiskCode>
						<!-- 险种名称 -->
						<RiskName><xsl:value-of select="Cvr_Nm" /></RiskName>
						<!-- 代理保险套餐编号 -->
						<AgInsPkgID><xsl:value-of select="AgIns_Pkg_ID" /></AgInsPkgID>
						<!-- 保额 -->
						<Amnt>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Ins_Cvr)" />
						</Amnt>
						<!-- 保费 -->
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" />
						</Prem>
						<!-- 投保份数 -->
						<Mult><xsl:value-of select="Ins_Cps" /></Mult>
						<!-- 必录项：续保标志  代表自动续保; N代表不自动续保  默认为N-->
						<RnewFlag>N</RnewFlag>
						<PayMode>B</PayMode>
						<!-- 缴费频次内容不够详细，取保费缴费周期代码-->
						<PayIntv>
							<xsl:call-template name="tran_payintv">
								<xsl:with-param name="PayIntv">
									<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
								</xsl:with-param>
							</xsl:call-template>
						</PayIntv> 
						<xsl:choose>
							<!-- 华贵多彩宝2号两全保险(万能险) -->
							<xsl:when test="Cvr_ID='011A0100'">
								<PayEndYear>1000</PayEndYear>
								<PayEndYearFlag>Y</PayEndYearFlag>
							</xsl:when>
							<!-- 华贵借贷无忧意外伤害保险(借意险) -->
							<xsl:when test="Cvr_ID='022J0300'">
								<PayEndYear>12</PayEndYear>
								<PayEndYearFlag>M</PayEndYearFlag>
							</xsl:when>
							<xsl:otherwise>
								<!-- 缴费年期和缴费年龄 -->
								 <xsl:if test="InsPrem_PyF_Cyc_Cd = '0100'">
								 	<!-- 趸交传PayEndYear=1000;PayEndYearFlag=Y给核心 -->				    
									<PayEndYear>1000</PayEndYear>
									<PayEndYearFlag>Y</PayEndYearFlag>
								</xsl:if>
								<!-- 建行无交费年期类型-->
								<xsl:if test="InsPrem_PyF_Cyc_Cd != '0100'">	
									<PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear>			
								    <PayEndYearFlag>Y</PayEndYearFlag>			    
								</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
						<!--保险期间-->	 
						<xsl:choose>		
							<xsl:when test="Ins_Yr_Prd_CgyCd='05'">	<!-- 保终身 -->
								<!--华贵多彩盛世养老年金保险  -->
								<xsl:if test="Cvr_ID='012E0100'">
									<!-- 保险年期年龄标志 -->
									<InsuYearFlag>A</InsuYearFlag>
									<!-- 保险年期年龄 -->
									<InsuYear>105</InsuYear> 
								</xsl:if>
							</xsl:when> 
							<xsl:otherwise>	
								<!-- 保险年期年龄标志 -->
								<InsuYearFlag>
									<xsl:call-template name="tran_PbIYF">
										<xsl:with-param name="PbInsuYearFlag">
											<!--  <xsl:value-of select="Ins_Cyc_Cd" />-->
											<xsl:value-of select="Ins_Yr_Prd_CgyCd" />
										</xsl:with-param>
									</xsl:call-template>	
								</InsuYearFlag>
								<!-- 保险年期年龄 -->
								<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>
							</xsl:otherwise>
						</xsl:choose> 
						<GetIntv>
							<xsl:call-template name="tran_GetIntv">
								<xsl:with-param name="GetIntv">
									<xsl:value-of select="ExpPrmmRcvModCgyCd" />
								</xsl:with-param>
							</xsl:call-template>
						</GetIntv>
			   	        <!-- 生存金领取周期代码 -->
			   	        <GetYearFlag>
			   	            <xsl:call-template name="tran_LiRenteDrawMode">
							<xsl:with-param name="GETLiRenteDrawMode">
								 <xsl:value-of select="SvBnf_Drw_Cyc_Cd" />
							</xsl:with-param>
							</xsl:call-template>
			   	        </GetYearFlag> 
						<!-- 红利领取方式 -->
						<BonusGetMode>
							<xsl:call-template name="tran_BonusGetMode">
								<xsl:with-param name="BonusGetMode">
									<xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
								</xsl:with-param>
							</xsl:call-template>
					    </BonusGetMode>
						<!-- 减额交清标志 -->
						<SubFlag><xsl:value-of select="RdAmtPyCls_Ind" /></SubFlag>
						<!-- 年金领取期数 -->
		                <GetYear><xsl:value-of select="Anuty_Drw_Prd_Num" /></GetYear><!-- 领取年期 有争议之前用GetTerms-->				
						<!-- 领取银行编码 传空-->
			   			<GetBankCode></GetBankCode> 
			   			<!-- 领取银行账户 传空-->
			   			<GetBankAccNo></GetBankAccNo> 
			   			<!-- 领取银行户名  传空-->
						<GetAccName></GetAccName> 
						<!-- 自动垫交标志 传空-->
						<AutoPayFlag><xsl:value-of select="ApntInsPremPyAdvnInd" /></AutoPayFlag> 
					</Risk>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>	
		<!-- 智慧终端渠道需要传影像文件，需要把影像文件名传给核心 ;
        	    当银行传影像文件时，就传给核心无不传-->
        <xsl:choose>
        	<xsl:when test="../../COMMON/FILE_LIST_PACK/FILE_NUM != '0'">
	        	<FileList>
	        		<xsl:for-each select="../../COMMON/FILE_LIST_PACK/FILE_INFO">
	        			<File><xsl:value-of select="FILE_NAME"/></File>
	        			<FilePath><xsl:value-of select="FILE_PATH"/></FilePath>
	        		</xsl:for-each>
				</FileList>
        	</xsl:when>
        	<xsl:otherwise>
        	</xsl:otherwise>
        </xsl:choose>
	</Body>
	</xsl:template>
	<!-- 渠道转换 -->
	<xsl:template name="transChannel">
		<xsl:param name="nTransChannel"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nTransChannel='20170029'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20180030'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20220037'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='20230038'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='19999999'">0</xsl:when>	<!-- 柜面出单 -->
			<xsl:when test="$nTransChannel='10010001'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10010002'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10010003'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10060009'">1</xsl:when>	<!-- 网银出单 -->
			<xsl:when test="$nTransChannel='10110023'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110016'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110017'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110018'">8</xsl:when>	<!-- 自助终端 -->
			<xsl:when test="$nTransChannel='10110109'">8</xsl:when><!--  智慧终端，归到自助终端 --> 
			<xsl:when test="$nTransChannel='10030006'">2</xsl:when><!--  手机银行 --> 
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 居民类型-->
	<xsl:template name="tran_Rsdnt_TpCd" >
		<xsl:param name="Rsdnt_TpCd"></xsl:param>
		<xsl:if test="$Rsdnt_TpCd = '1'">2</xsl:if><!--非农业-->
		<xsl:if test="$Rsdnt_TpCd = '2'">1</xsl:if><!--农业 -->
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
		<xsl:if test="$GetIntv = '001'">0</xsl:if>     <!--趸领  -->
		<xsl:if test="$GetIntv = '002'">1</xsl:if>     <!-- 月领 -->
		<xsl:if test="$GetIntv = '005'">12</xsl:if>  <!-- 年领 -->
	</xsl:template>
	
	<!-- 生存金领取方式 -->
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = '03'">Y</xsl:if>  <!-- 3-年领  :年金产品默认会是03-->
		<xsl:if test="$GETLiRenteDrawMode = '04'">M</xsl:if>  <!-- 月领 -->
		<xsl:if test="$GETLiRenteDrawMode = '05'">D</xsl:if>  <!-- 日领 -->
		<xsl:if test="$GETLiRenteDrawMode = ''"></xsl:if>
	</xsl:template>
	
	<!-- 投被保人国籍 -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = '156'">CHN</xsl:if><!--中国 -->
		<xsl:if test="$Nationality = '124'">CAN</xsl:if><!--加拿大 -->
		<xsl:if test="$Nationality = '158'">TW</xsl:if><!--台湾 -->
		<xsl:if test="$Nationality = '344'">HK</xsl:if><!--香港-->
		<xsl:if test="$Nationality = '392'">JAN</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = '410'">KOR</xsl:if><!--韩国-->
		<xsl:if test="$Nationality = '446'">MO</xsl:if><!-- 澳门 -->
		<xsl:if test="$Nationality = '643'">RUS</xsl:if><!--俄罗斯联邦-->
		<xsl:if test="$Nationality = '826'">ENG</xsl:if><!--英国-->
		<xsl:if test="$Nationality = '840'">USA</xsl:if><!--美国 -->
		<xsl:if test="$Nationality = '036'">AUS</xsl:if><!--澳大利亚 -->
		<xsl:if test="$Nationality = '999'">OTH</xsl:if><!--其他-->
	</xsl:template>

	<!-- 红利领取方式 -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = '0'">2</xsl:if><!-- 直接给付 -->
		<xsl:if test="$BonusGetMode = '1'">3</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$BonusGetMode = '2'">1</xsl:if><!-- 累积生息 -->
		<xsl:if test="$BonusGetMode = '3'">5</xsl:if><!-- 增额交清 -->
		<xsl:if test="$BonusGetMode = ''"></xsl:if><!-- 年金产品没有红利领取方式 -->
	</xsl:template>
	
	<!-- 缴费方式（频次） -->
	<xsl:template name="tran_payintv" >
			<xsl:param name="PayIntv" />
		<xsl:choose>
			<xsl:when test="$PayIntv = 9999">-1</xsl:when>	<!-- 不定期 -->
			<xsl:when test="$PayIntv = 0100">0</xsl:when>	<!-- 趸交 -->
			<xsl:when test="$PayIntv = 0204">1</xsl:when>	<!-- 月缴-->
			<xsl:when test="$PayIntv = 0201">3</xsl:when>	<!-- 季缴-->
			<xsl:when test="$PayIntv = 0202">6</xsl:when>	<!-- 半年缴-->
			<xsl:when test="$PayIntv = 0203">12</xsl:when>	<!-- 年缴 -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保险期间单位 -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 01">M</xsl:when><!-- 不定期-->
			<xsl:when test="$PbInsuYearFlag = 02">Y</xsl:when><!-- 一次 -->
			<xsl:when test="$PbInsuYearFlag = 03">Y</xsl:when><!-- 按周期 -->
			<xsl:when test="$PbInsuYearFlag = 04">A</xsl:when><!-- 按年龄 -->
			<xsl:when test="$PbInsuYearFlag = 05">A</xsl:when><!-- 终身 -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- 居民身份证 -->
			<xsl:when test="$idtype = '1011'">0</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1020'">2</xsl:when><!-- 军人身份证件 -->
			<xsl:when test="$idtype = '1030'">2</xsl:when><!-- 武警身份证件 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- 外国护照 -->
			<xsl:when test="$idtype = '1070'">F</xsl:when><!-- 港澳居民往来内地通行证-->
			<xsl:when test="$idtype = '1080'">F</xsl:when><!-- 台湾居民来往大陆通行证-->
			<xsl:when test="$idtype = '1120'">1</xsl:when><!-- (外国)护照-->
			<xsl:when test="$idtype = '1999'">99</xsl:when><!-- 其他证件（个人）-->
			<xsl:when test="$idtype = '2010'">99</xsl:when><!-- 营业执照-->
			<xsl:when test="$idtype = '2020'">99</xsl:when><!-- 组织机构代码证-->
			<xsl:when test="$idtype = '2030'">99</xsl:when><!-- 社会团体法人登记证书-->
			<xsl:when test="$idtype = '2040'">99</xsl:when><!-- 事业法人登记证书-->
			<xsl:when test="$idtype = '2090'">99</xsl:when><!-- 税务登记证-->
			<xsl:when test="$idtype = '2999'">99</xsl:when><!-- 其他证件（对公）-->
			<xsl:otherwise>99</xsl:otherwise><!-- 其他 -->
		</xsl:choose>
	</xsl:template>

	<!-- 投保人和被保人的 关系 -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:choose>
		    <xsl:when test="$RelaToInsured = '0133999'">06</xsl:when><!-- 其他关系-->
		    <xsl:when test="$RelaToInsured = '0133043'">00</xsl:when><!-- 本人-->
			<xsl:when test="$RelaToInsured = '0132004'">09</xsl:when><!-- 雇佣关系-->
			<xsl:when test="$RelaToInsured = '0133010'">02</xsl:when><!--配偶 -->
			<xsl:when test="$RelaToInsured = '0133011'">03</xsl:when><!-- 子女: 儿子  -->
			<xsl:when test="$RelaToInsured = '0133012'">03</xsl:when><!-- 子女 :女儿 -->
			<xsl:when test="$RelaToInsured = '0133013'">04</xsl:when><!--祖孙: 孙子-->
			<xsl:when test="$RelaToInsured = '0133014'">04</xsl:when><!--祖孙: 孙女-->
			<xsl:when test="$RelaToInsured = '0133015'">01</xsl:when><!--父母:父亲-->
			<xsl:when test="$RelaToInsured = '0133016'">01</xsl:when><!--父母:母亲-->
			<xsl:when test="$RelaToInsured = '0133017'">04</xsl:when><!--祖孙: 祖父 -->
			<xsl:when test="$RelaToInsured = '0133018'">04</xsl:when><!--祖孙: 祖母 -->
			<xsl:when test="$RelaToInsured = '0133031'">04</xsl:when><!--祖孙: 外祖父 -->
			<xsl:when test="$RelaToInsured = '0133032'">04</xsl:when><!--祖孙: 外祖母-->
			<xsl:when test="$RelaToInsured = '0133033'">04</xsl:when><!--祖孙: 外孙-->
			<xsl:when test="$RelaToInsured = '0133034'">04</xsl:when><!--祖孙: 外孙女-->
			<xsl:otherwise>99</xsl:otherwise><!-- 没有映射的都是这样 -->
		</xsl:choose>
	</xsl:template>
	
<!-- 健康告知 -->
<xsl:template name="healFlag">
  <xsl:param name="HealFlag">N</xsl:param>
  <xsl:if test="$HealFlag = 0">N</xsl:if><!-- 无健康告知 -->
  <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- 有健康告知 -->
</xsl:template>
<!--职业代码-->
<xsl:template name="tran_JobCode">
  <xsl:param name="JobCode"></xsl:param>
  <xsl:choose>
    <xsl:when test="$JobCode = 'A0000'">1050104</xsl:when><!--工厂企业负责人-->
    <xsl:when test="$JobCode = 'B0000'">2021301</xsl:when><!--计算机硬件技术人员 -->
    <xsl:when test="$JobCode = 'C0000'">3030201</xsl:when><!--电信业务营业员 -->
    <xsl:when test="$JobCode = 'D0000'">4030501</xsl:when><!--餐厅服务员 -->
    <xsl:when test="$JobCode = 'E0000'">5010101</xsl:when><!--农夫 -->
    <xsl:when test="$JobCode = 'F0000'">6051006</xsl:when><!--办公小机械制造装配工 -->
    <xsl:when test="$JobCode = 'Y0000'">8000002</xsl:when><!--退休人员 -->
    <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>