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
		    <!-- 投保单印刷号 -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_Bl_Prt_No)" /></ProposalPrtNo>
  			<!--保单合同印刷号 (单证) 目前建行新接口不传，在打印保单传20140903-->
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
				<!-- 投保人证件生效日期
				<xsl:choose>
				<xsl:when test="Plchd_Crdt_EfDt=20991231" ><PlchdCrdtEfDt>99990101</PlchdCrdtEfDt></xsl:when>
				<xsl:otherwise><PlchdCrdtEfDt><xsl:value-of select="Plchd_Crdt_EfDt"/></PlchdCrdtEfDt></xsl:otherwise>
				</xsl:choose>-->
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
				<!-- 投保人国家地区代码 --><!-- 映射为核心国籍字段 -->
				<NationalityCode><xsl:value-of select="Plchd_Nat_Cd" /></NationalityCode>
				<!-- 投保人地址 -->
				<Address><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></Address>
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
				<!-- 投保人与被保人关系代码 --><!-- 核心字段 -->
				<RelaToInsured>
					<xsl:call-template name="tran_relation">
						<xsl:with-param name="RelaToInsured">
							<xsl:value-of select="Plchd_And_Rcgn_ReTpCd" /><!-- 新建行投保人和被保人关系类型代码字段 -->
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured>				
				<RelaToInsured2><xsl:value-of select="Plchd_And_Rcgn_ReTpCd" /></RelaToInsured2>				
				<!-- 投保人居民类型 -->
				<DenType>
					<xsl:call-template name="tran_DenType">
						<xsl:with-param name="Rsdnt_TpCd">
							<xsl:value-of select="Rsdnt_TpCd"/>
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
 				<!-- 被保人国籍--><!-- 映射为核心国籍字段 -->
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
 				<!-- 被保人地址-->
				<Address>
					<xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" />
				</Address>
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
				<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Rcgn_Yr_IncmAm)" /></YearSalary><!-- 建行转核心请求XSL，核心字段写成建行字段 -->
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
			<!-- 		<xsl:for-each select="Benf_Detail">    建行自己发的测试报文没有Benf_List/Benf_Detail只有 Benf_Detail -->	
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
						<RelaToInsured><!-- 核心字段 -->
							<xsl:call-template name="tran_relation">
								<xsl:with-param name="RelaToInsured">
									<xsl:value-of
										select="Benf_And_Rcgn_ReTpCd" /><!-- 新建行受益人和被保人关系类型代码字段 -->
								</xsl:with-param>
							</xsl:call-template>
						</RelaToInsured>
						<RelaToInsured3>	<xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured3>	
						<!-- 受益人受益比例 -->
						<Lot>
							<xsl:value-of
								select="Bnft_Pct" />
						</Lot>
						<!-- 受益人详细地址内容 -->
						<AddressContent><xsl:value-of select="Benf_Dtl_Adr_Cntnt" /></AddressContent>
						<!-- 受益人通讯地址 -->
						<BenfCommAdr><xsl:value-of select="Benf_Comm_Adr" /></BenfCommAdr>
						<!-- 受益类型 -->
						<BeneficType>N</BeneficType>
						</Bnf>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			
			<xsl:choose>
				<!-- 险种个数 -->
					<xsl:when
						test="Cvr_Num ='0'">
					</xsl:when>
					<xsl:when
						test="Cvr_Num !='0'">
						<xsl:for-each select="Busi_List/Busi_Detail">
			<Risk>
				<!-- 险种编号 -->
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
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
				<Mult>
					<xsl:value-of select="Ins_Cps" />
				</Mult>
				<PayMode>B</PayMode>
				<!-- 保费缴纳方式代码 --><!-- 万能型 -->
				<xsl:if test="Cvr_ID='011A0100'">
					<PayIntv>0</PayIntv><!-- 缴费频次[方式]:趸交 -->
					<PayEndYear>1000</PayEndYear><!-- 缴费年期年龄:1000 -->
					<PayEndYearFlag>Y</PayEndYearFlag><!-- 缴费年期类型:年-->
				</xsl:if>
				<xsl:if test="Cvr_ID !='011A0100'">
					<PayIntv>
						<xsl:call-template name="tran_Contpayintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="InsPrem_PyF_MtdCd" />
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- 缴费年期和缴费年龄 -->
					 <xsl:if test="InsPrem_PyF_MtdCd = 02"><!-- 趸交1000Y给核心 -->				    
						<PayEndYear>1000</PayEndYear><!-- 缴费年龄 -->
						<PayEndYearFlag>Y</PayEndYearFlag><!-- 缴费年期 -->
					</xsl:if>
					<xsl:if test="InsPrem_PyF_MtdCd != 02">	<!-- 建行没有交费年期类型一说 -->
						<PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear><!-- 缴费期数 -->			
					    <PayEndYearFlag>Y</PayEndYearFlag><!-- 交费年期 -->
					</xsl:if>
				</xsl:if>
				<!-- 保费缴纳期数 
				<PayEndYear>
					<xsl:value-of select="InsPrem_PyF_Prd_Num" />
				</PayEndYear>-->
				<!-- 保费缴纳周期代码 
				<PayEndYearFlag>
					<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
				</PayEndYearFlag>-->
				<!-- 满期保险金领取方式 -->
				<GetIntv>
					<xsl:call-template name="tran_GetIntv">
						<xsl:with-param name="GetIntv">
							<xsl:value-of select="ExpPrmmRcvModCgyCd" />
						</xsl:with-param>
					</xsl:call-template>
				</GetIntv>
    	        <!-- 生存金领取周期代码:年领 -->
    	        <GetYearFlag>
    	            <xsl:call-template name="tran_LiRenteDrawMode">
					<xsl:with-param name="GETLiRenteDrawMode">
						 <xsl:value-of select="SvBnf_Drw_Cyc_Cd" />
					</xsl:with-param>
					</xsl:call-template>
    	        </GetYearFlag> <!-- 领取年龄年期标志  这个需要转换-->
				<!-- 红利领取方式 -->
				<BonusGetMode>
					<xsl:call-template name="tran_BonusGetMode">
						<xsl:with-param name="BonusGetMode">
							<xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
						</xsl:with-param>
					</xsl:call-template>
			    </BonusGetMode>
				<!-- 减额交清标志 -->
				<SubFlag>
					<xsl:value-of select="RdAmtPyCls_Ind" />
				</SubFlag>

				<!-- 年金领取类别代码
				<Anuty_Drw_CgyCd>
					<xsl:value-of select="Anuty_Drw_CgyCd" />
				</Anuty_Drw_CgyCd> -->
				<!-- 年金领取期数 -->
                <GetYear><xsl:value-of select="Anuty_Drw_Prd_Num" /></GetYear><!-- 领取年期 有争议之前用GetTerms-->				
				
				<!--保险期间-->	 
				<xsl:choose>		
					<xsl:when test="Ins_Yr_Prd_CgyCd='04'">	<!-- 至某特定年龄 --><!-- 保险年期类别代码为04[至某特定年龄] -->
						<!-- 保险年期年龄标志 -->
						<InsuYearFlag>A</InsuYearFlag><!-- 保险期间类型为A[年龄] -->
						<!-- 保险年期年龄 -->
						<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear> 
					</xsl:when> 
					<xsl:when test="Ins_Yr_Prd_CgyCd='05'">	<!-- 保终身 --><!-- 保险年期类别代码为05 -->
						<!-- 保险年期年龄标志 -->
						<InsuYearFlag>A</InsuYearFlag><!-- 保险期间类型为A -->
						<!-- 保险年期年龄[保险期限] -->
						<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear> 
					</xsl:when> 
					<xsl:otherwise><!-- 否则，保险期间类型为保险周期代码 -->
						<InsuYearFlag>
							<xsl:call-template name="tran_PbIYF">
								<xsl:with-param name="PbInsuYearFlag">
									<xsl:value-of select="Ins_Cyc_Cd" />
								</xsl:with-param>
							</xsl:call-template>	
						</InsuYearFlag>
						<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear>
					</xsl:otherwise>
				</xsl:choose> 
				
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
		</Body>
	</xsl:template>
	
	
	<!-- 居民类型 -->
	<xsl:template name="tran_DenType">
		<xsl:param name="Rsdnt_TpCd"></xsl:param>
		<xsl:choose>
			<xsl:when test="$Rsdnt_TpCd=1">2</xsl:when><!-- 城镇居民 -->
			<xsl:when test="$Rsdnt_TpCd=2">1</xsl:when><!-- 农村居民 -->
		</xsl:choose>
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
	
	<!-- 投被保人国籍 --><!-- 新建行请求报文国籍字段转换为核心请求报文国籍字段[新建行国籍：核心国籍] -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 124">CAN</xsl:if><!-- 加拿大 -->
		<xsl:if test="$Nationality = 156">CHN</xsl:if><!--中国 -->
		<xsl:if test="$Nationality = 158">TW</xsl:if><!--台湾 -->
		<xsl:if test="$Nationality = 344">HK</xsl:if><!--香港 -->
		<xsl:if test="$Nationality = 392">JAN</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = 410">KOR</xsl:if><!--韩国-->
		<xsl:if test="$Nationality = 446">MO</xsl:if><!--澳门 -->
		<xsl:if test="$Nationality = 643">RUS</xsl:if><!--俄罗斯联邦 -->
		<xsl:if test="$Nationality = 826">ENG</xsl:if><!--英国-->
		<xsl:if test="$Nationality = 840">USA</xsl:if><!--美国 -->
		<xsl:if test="$Nationality = 999">OTH</xsl:if><!--其他国家和地区 -->
		<xsl:if test="$Nationality = 036">AUS</xsl:if><!--澳大利亚-->
	</xsl:template>

	<!-- 红利领取方式 -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = 0">2</xsl:if><!-- 直接给付 -->
		<xsl:if test="$BonusGetMode = 1">3</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$BonusGetMode = 2">1</xsl:if><!-- 累积生息 -->
		<xsl:if test="$BonusGetMode = 3">5</xsl:if><!-- 增额交清 -->
	</xsl:template>

	<!-- 保单的缴费方式[保险年期类别] -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = 01">-1</xsl:when><!-- 不定期 -->
			<xsl:when test="$payintv = 02">0</xsl:when><!-- 一次:趸交 -->
			<xsl:when test="$payintv = 03">12</xsl:when><!-- 按周期:年交 -->
			<xsl:when test="$payintv = 04">98</xsl:when><!-- 至某特定年龄[确定年] -->
			<xsl:when test="$payintv = 05">99</xsl:when><!-- 终身 -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 保单传送方式?    需要和核心确认 -->
	<xsl:template name="PbSendMode">
	<xsl:param name="pbSendMode"></xsl:param>
	<xsl:choose>
			<xsl:when test="$pbSendMode = 1">1</xsl:when><!-- 邮寄 -->
			<xsl:when test="$pbSendMode = 2">1</xsl:when><!-- 电子发送 -->
			<xsl:when test="$pbSendMode = 3">1</xsl:when><!-- 指定柜台领取 -->
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
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- 居民身份证 -->
			<xsl:when test="$idtype = '1011'">0</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = '1020'">2</xsl:when><!-- 军人身份证件 -->
			<xsl:when test="$idtype = '1030'">2</xsl:when><!-- 武警身份证件 -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- 外国护照 -->
			<xsl:when test="$idtype = '1070'">F</xsl:when><!-- 港澳居民往来内地通行证 -->
			<xsl:when test="$idtype = '1080'">F</xsl:when><!-- 台湾居民来往大陆通行证-->
			<xsl:when test="$idtype = '1120'">1</xsl:when><!-- 外国人居留证-->
			<xsl:when test="$idtype = '1999'">99</xsl:when><!-- 其他证件（个人） -->
			<xsl:when test="$idtype = '2010'">99</xsl:when><!-- 营业执照 -->
			<xsl:when test="$idtype = '2020'">99</xsl:when><!-- 组织机构代码证 -->
			<xsl:when test="$idtype = '2030'">99</xsl:when><!-- 社会团体法人登记证书 -->
			<xsl:when test="$idtype = '2040'">99</xsl:when><!-- 事业法人登记证书 -->
			<xsl:when test="$idtype = '2090'">99</xsl:when><!-- 税务登记证 -->
			<xsl:when test="$idtype = '2999'">99</xsl:when><!-- 其他证件（对公） -->
			<xsl:otherwise>
					<xsl:value-of select="99" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 投保人和被保人的 关系 -->
	<xsl:template name="tran_relation"><!-- 投保人与被保人关系、受益人与被保人关系转换一致 -->
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:choose>
			<xsl:when test="$RelaToInsured = 0132004">09</xsl:when><!--雇佣关系 -->
			<xsl:when test="$RelaToInsured = 0133010">02</xsl:when><!--配偶 -->
			<xsl:when test="$RelaToInsured = 0133011">03</xsl:when><!--子女:儿子 -->
			<xsl:when test="$RelaToInsured = 0133012">03</xsl:when><!--子女:女儿 -->
			<xsl:when test="$RelaToInsured = 0133013">04</xsl:when><!--祖孙:孙子 -->
			<xsl:when test="$RelaToInsured = 0133014">04</xsl:when><!--祖孙:孙女-->
			<xsl:when test="$RelaToInsured = 0133015">01</xsl:when><!--父母:父亲-->
			<xsl:when test="$RelaToInsured = 0133016">01</xsl:when><!--父母:母亲-->
			<xsl:when test="$RelaToInsured = 0133017">04</xsl:when><!--祖孙:祖父-->
			<xsl:when test="$RelaToInsured = 0133018">04</xsl:when><!--祖孙:祖母-->
			<xsl:when test="$RelaToInsured = 0133031">04</xsl:when><!--祖孙:外祖父-->
			<xsl:when test="$RelaToInsured = 0133032">04</xsl:when><!--祖孙:外祖母-->
			<xsl:when test="$RelaToInsured = 0133033">04</xsl:when><!--祖孙:外孙-->
			<xsl:when test="$RelaToInsured = 0133034">04</xsl:when><!--祖孙:外孙女-->
			<xsl:when test="$RelaToInsured = 0133043">00</xsl:when><!--本人 -->
			<xsl:when test="$RelaToInsured = 0133999">06</xsl:when><!-- 其他关系 -->
			<xsl:otherwise>--</xsl:otherwise><!-- 没有映射的都是这样 -->
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
    <xsl:when test="$JobCode = 'A0000'">1050104</xsl:when><!-- 工厂企业负责人-->
    <xsl:when test="$JobCode = 'B0000'">2021301</xsl:when><!-- 计算机硬件技术人员 -->
    <xsl:when test="$JobCode = 'C0000'">3030201</xsl:when><!-- 电信业务营业员 -->
    <xsl:when test="$JobCode = 'D0000'">4030501</xsl:when><!-- 餐厅服务员 -->
    <xsl:when test="$JobCode = 'E0000'">5010101</xsl:when><!-- 农夫 -->
    <xsl:when test="$JobCode = 'F0000'">6051006</xsl:when><!-- 办公小机械制造装配工 -->
    <xsl:when test="$JobCode = 'Y0000'">8000002</xsl:when><!-- 退休人员 -->
    <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>