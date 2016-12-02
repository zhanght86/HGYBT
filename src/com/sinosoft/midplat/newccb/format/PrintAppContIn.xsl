<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
				<!--交易日期 -->
				<TranDate>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
				</TranDate>
				<!--交易时间 -->
				<TranTime>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" />
				</TranTime>
				<!-- 银行网点 -->
				<NodeNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" />
				</NodeNo>
				<!-- 银行编码 -->
				<BankCode>0104</BankCode>
				<!--柜员号 -->
				<TellerNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" />
				</TellerNo>
				<!-- 交易流水号 -->
				<TranNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
				</TranNo>
				<!-- 银行端ip[非必须]
				<ClientIp>127.0.0.1</ClientIp> -->
				<!-- 交易渠道
				<TranCom>03</TranCom> -->
				<!-- 	<TranCom><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_ID" /></TranCom>   -->
				<!-- 交易类型 -->
				<!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->
				<!-- 服务id ?
				<ServiceId>0</ServiceId> -->

				<!-- 生产上用下面方式，上面为了测试所以加上下面字段 -->

				<!-- 交易类型 服务类中添加-->
				<!-- 服务id 服务类中添加-->
				<!-- 银行端ip[非必须] 服务类中添加-->



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
			<ProposalPrtNo>
				<xsl:value-of select="Ins_Bl_Prt_No" />
			</ProposalPrtNo>
			<!--保单合同印刷号 (单证) 银行第一次不传，返回核心返回必须要录入单证号，银行第二次发单证号-->
			<ContPrtNo>
				<xsl:value-of select="BkVchNo" />
			</ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate>
				<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
			</PolApplyDate>
			<!--投保人缴费账号  -->
			<AccNo>
				<xsl:value-of select="Plchd_PyF_AccNo" />
			</AccNo>
			<!-- 账户姓名 工行不传账户名默认为投保人姓名-->
			<AccName>
				<xsl:value-of select="Plchd_Nm" />
			</AccName>
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
			<SaleName>
				<xsl:value-of select="BO_Sale_Stff_Nm" />
			</SaleName>
			<!-- 销售人员编号  -->
			<SaleStaff>
				<xsl:value-of select="BO_Sale_Stff_ID" />
			</SaleStaff>
			<!-- 销售人员资格证号  -->
			<!-- 建行一级分行号 -->
			<Lv1BrNo>
				<xsl:value-of select="Lv1_Br_No" />
			</Lv1BrNo>

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
				<xsl:when test="Plchd_Crdt_EfDt=20991231" >
				<PlchdCrdtEfDt>99990101</PlchdCrdtEfDt>
				</xsl:when>
				<xsl:otherwise>
				<PlchdCrdtEfDt>
				<xsl:value-of select="Plchd_Crdt_EfDt"/>
				</PlchdCrdtEfDt>
				</xsl:otherwise>
			</xsl:choose>-->
			<!-- 投保人证件失效日期-->
			<xsl:choose>
				<xsl:when test="Plchd_Crdt_ExpDt=20991231" >
					<IdExpDate>99990101</IdExpDate>
				</xsl:when>
				<xsl:otherwise>
					<IdExpDate>
						<xsl:value-of select="Plchd_Crdt_ExpDt"/>
					</IdExpDate>
				</xsl:otherwise>
			</xsl:choose>
			<!-- 投保人国籍 -->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
						<xsl:value-of select="Plchd_Nat_Cd"/>
					</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- 投保人地址 -->
			<Address>
				<xsl:value-of select="Plchd_Comm_Adr" />
			</Address>
			<!-- 投保人邮政编码-->
			<ZipCode>
				<xsl:value-of select="Plchd_ZipECD" />
			</ZipCode>
			<!-- 投保人固定电话-->
			<Phone>
				<xsl:value-of select="Plchd_Fix_TelNo" />
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
				<xsl:value-of select="Plchd_Ocp_Cd" />
			</JobCode>
			<!-- 投保人年收入 -->
			<YearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Plchd_Yr_IncmAm)"/>
			</YearSalary>
			<!-- 投保人家庭年收入 -->
			<FamilyYearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Fam_Yr_IncmAm)"/>
			</FamilyYearSalary>
			<!-- 投保人与被保人关系代码 -->
			<RelaToInsured>
				<xsl:call-template name="tran_relation">
					<xsl:with-param name="RelaToInsured">
						<xsl:value-of select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</RelaToInsured>
			<!-- 投保人居民类型 -->
			<DenType>
				<xsl:value-of select="Rsdnt_TpCd" />
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

			<!-- 被保人证件生效日期
			<xsl:choose>
			<xsl:when test="Rcgn_Crdt_EfDt=20991231" >
			<RcgnCrdtEfDt>99990101</RcgnCrdtEfDt>
			</xsl:when>
			<xsl:otherwise>
			<RcgnCrdtEfDt>
			<xsl:value-of select="Rcgn_Crdt_EfDt"/>
			</RcgnCrdtEfDt>
			</xsl:otherwise>
		</xsl:choose>-->
		<!-- 被保人证件失效日期-->
		<xsl:choose>
			<xsl:when test="Rcgn_Crdt_ExpDt=20991231" >
				<IdExpDate>99990101</IdExpDate>
			</xsl:when>
			<xsl:otherwise>
				<IdExpDate>
					<xsl:value-of select="Rcgn_Crdt_ExpDt"/>
				</IdExpDate>
			</xsl:otherwise>
		</xsl:choose>
		<!-- 被保人国籍-->
		<Nationality>
			<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
					<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
			</xsl:call-template>
		</Nationality>
		<!-- 被保人地址-->
		<Address>
			<xsl:value-of select="Rcgn_Comm_Adr" />
		</Address>
		<!-- 被保人邮编-->
		<ZipCode>
			<xsl:value-of select="Rcgn_ZipECD" />
		</ZipCode>
		<!-- 被保人固定电话-->
		<Phone>
			<xsl:value-of select="Rcgn_Fix_TelNo" />
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
			<xsl:value-of select="Rcgn_Ocp_Cd" />
		</JobCode>
		<!-- 被保人年收入 -->
		<RcgnYrIncmAm>
			<xsl:value-of select="Rcgn_Yr_IncmAm" />
		</RcgnYrIncmAm>
		<!-- 未成年被保险人在其他保险公司累计身故保额 -->
		<CovSumAmt>
			<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)"/>
		</CovSumAmt>

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
					<Type>
						<xsl:value-of select="AgIns_Benf_TpCd" />
					</Type>
					<!-- 受益人序号 -->
					<BenfSN>
						<xsl:value-of select="Benf_SN" />
					</BenfSN>
					<!-- 受益人受益顺序 -->
					<Grade>
						<xsl:value-of select="Benf_Bnft_Seq" />
					</Grade>
					<!-- 受益人姓名 -->
					<Name>
						<xsl:value-of select="Benf_Nm" />
					</Name>
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
					<IDNo>
						<xsl:value-of select="Benf_Crdt_No" />
					</IDNo>
					<!-- 受益人证件生效日期-->
					<xsl:choose>
						<xsl:when test="Benf_Crdt_EfDt=20991231" >
							<BenfCrdtEfDt>99990101</BenfCrdtEfDt>
						</xsl:when>
						<xsl:otherwise>
							<BenfCrdtEfDt>
								<xsl:value-of select="Benf_Crdt_EfDt"/>
							</BenfCrdtEfDt>
						</xsl:otherwise>
					</xsl:choose>
					<!-- 受益人证件失效日期-->
					<xsl:choose>
						<xsl:when test="Benf_Crdt_ExpDt=20991231" >
							<IdExpDate>99990101</IdExpDate>
						</xsl:when>
						<xsl:otherwise>
							<IdExpDate>
								<xsl:value-of select="Benf_Crdt_ExpDt"/>
							</IdExpDate>
						</xsl:otherwise>
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
								<xsl:value-of
								select="Benf_And_Rcgn_ReTpCd" />
							</xsl:with-param>
						</xsl:call-template>
					</RelaToInsured>
					<!-- 受益人受益比例 -->
					<Lot>
						<xsl:value-of
						select="Bnft_Pct" />
					</Lot>
					<!-- 受益人通讯地址 -->
					<BenfCommAdr>
						<xsl:value-of select="Benf_Comm_Adr" />
					</BenfCommAdr>
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
					<RiskCode>
						<xsl:value-of select="Cvr_ID" />
					</RiskCode>
					<!-- 险种名称 -->
					<RiskName>
						<xsl:value-of select="Cvr_Nm" />
					</RiskName>
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
					<!-- 保费缴纳方式代码 -->
					<PayIntv>
						<xsl:call-template name="tran_Contpayintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="InsPrem_PyF_MtdCd" />
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- 缴费年期和缴费年龄 -->
					<xsl:if test="InsPrem_PyF_MtdCd = 0">
						<!-- 趸交1000Y给核心 -->
						<PayEndYear>1000</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<xsl:if test="InsPrem_PyF_MtdCd != 0">
						<!-- 建行没有交费年期类型一说 -->
						<PayEndYear>
							<xsl:value-of select="InsPrem_PyF_Prd_Num" />
						</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
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
			<!-- 生存金领取周期代码 -->
			<GetYearFlag>
				<xsl:call-template name="tran_LiRenteDrawMode">
					<xsl:with-param name="GETLiRenteDrawMode">
						<xsl:value-of select="SvBnf_Drw_Cyc_Cd" />
					</xsl:with-param>
				</xsl:call-template>
			</GetYearFlag>
			<!-- 领取年龄年期标志  这个需要转换-->
			<!-- 约定保费垫交标志 -->
			<AutoPayFlag>
				<xsl:call-template name="apntInsPayType">
					<xsl:with-param name="ApntInsPayType">
						<xsl:value-of select="ApntInsPremPyAdvnInd" />
					</xsl:with-param>
				</xsl:call-template>
			</AutoPayFlag>
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
		<GetYear>
			<xsl:value-of select="Anuty_Drw_Prd_Num" />
		</GetYear>
		<!-- 领取年期 有争议之前用GetTerms-->
		<!-- 年金领取周期代码
		<Anuty_Drw_Cyc_Cd>
		<xsl:value-of select="Anuty_Drw_Cyc_Cd" />
	</Anuty_Drw_Cyc_Cd>-->
	<!-- 保险年期类别代码 -->
	<InsuYearFlag>
		<xsl:call-template name="tran_PbIYF">
			<xsl:with-param name="PbInsuYearFlag">
				<xsl:value-of select="Ins_Yr_Prd_CgyCd" />
			</xsl:with-param>
		</xsl:call-template>
	</InsuYearFlag>
	<!-- 保险期限 -->
	<InsuYear>
		<xsl:value-of select="Ins_Ddln" />
	</InsuYear>
	<!-- 领取银行编码 传空-->
	<GetBankCode></GetBankCode>
	<!-- 领取银行账户 传空-->
	<GetBankAccNo></GetBankAccNo>
	<!-- 领取银行户名  传空-->
	<GetAccName></GetAccName>
	<!-- 自动垫交标志 传空-->
	<AutoPayFlag></AutoPayFlag>

</Risk>

</xsl:for-each>
</xsl:when>
</xsl:choose>



</Body>
</xsl:template>


<!-- 约定保费垫交标志 -->
<xsl:template name="apntInsPayType" >
	<xsl:param name="ApntInsPayType"></xsl:param>
	<xsl:if test="$ApntInsPayType = 0">N</xsl:if>
	<!-- 不垫交 -->
	<xsl:if test="$ApntInsPayType = 1">Y</xsl:if>
	<!-- 自动垫交 -->
</xsl:template>
<!-- 性别 -->
<xsl:template name="tran_sex" >
	<xsl:param name="Sex"></xsl:param>
	<xsl:if test="$Sex = 1">0</xsl:if>
	<!-- 男 -->
	<xsl:if test="$Sex = 2">1</xsl:if>
	<!-- 女 -->
</xsl:template>

<!-- 保险金领取方式 -->
<xsl:template name="tran_GetIntv">
	<xsl:param name="GetIntv"></xsl:param>
	<xsl:if test="$GetIntv = 0">0</xsl:if>
	<!--趸领  -->
	<xsl:if test="$GetIntv = 1">1</xsl:if>
	<!-- 月领 -->
	<xsl:if test="$GetIntv = 12">12</xsl:if>
	<!-- 年领 -->
</xsl:template>


<xsl:template name="tran_LiRenteDrawMode">
	<xsl:param name="GETLiRenteDrawMode"></xsl:param>
	<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>
	<!--  0-一次给付  -->
	<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>
	<!--  1-月给付  -->
	<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>
	<!--  3-季给付  -->
	<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>
	<!--  6-半年给付  -->
	<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>
	<!--  12-年给付  -->
</xsl:template>

<!-- 投被保人国籍 -->
<xsl:template name="tran_Nationality">
	<xsl:param name="Nationality"></xsl:param>
	<xsl:if test="$Nationality = 0156">CHN</xsl:if>
	<xsl:if test="$Nationality = 0344"></xsl:if>
	<!--香港 -->
	<xsl:if test="$Nationality = 0158"></xsl:if>
	<!--台湾 -->
	<xsl:if test="$Nationality = 0446"></xsl:if>
	<!--澳门 -->
	<xsl:if test="$Nationality = 0392">JAN</xsl:if>
	<!--日本 -->
	<xsl:if test="$Nationality = 0840">USA</xsl:if>
	<!--美国-->
	<xsl:if test="$Nationality = 0643"></xsl:if>
	<!--俄罗斯 -->
	<xsl:if test="$Nationality = 0826">ENG</xsl:if>
	<!--英国 -->
	<xsl:if test="$Nationality = 0250">FRA</xsl:if>
	<!--法国-->
	<xsl:if test="$Nationality = 0276">DEU</xsl:if>
	<!--德国 -->
	<xsl:if test="$Nationality = 0410">KOR</xsl:if>
	<!--韩国 -->
	<xsl:if test="$Nationality = 0702">SG</xsl:if>
	<!--新加坡 -->
	<xsl:if test="$Nationality = 0360">INA</xsl:if>
	<!--印度尼西亚-->
	<xsl:if test="$Nationality = 0356">IND</xsl:if>
	<!--印度-->
	<xsl:if test="$Nationality = 0380">ITA</xsl:if>
	<!--意大利 -->
	<xsl:if test="$Nationality = 0458">MY</xsl:if>
	<!--马来西亚 -->
	<xsl:if test="$Nationality = 0764">THA</xsl:if>
	<!--泰国-->
	<xsl:if test="$Nationality = 0999"></xsl:if>
	<!--其他-->
</xsl:template>

<!-- 红利领取方式 -->
<xsl:template name="tran_BonusGetMode">
	<xsl:param name="BonusGetMode"></xsl:param>
	<xsl:if test="$BonusGetMode = 0">2</xsl:if>
	<!-- 直接给付 -->
	<xsl:if test="$BonusGetMode = 1">3</xsl:if>
	<!-- 抵交保费 -->
	<xsl:if test="$BonusGetMode = 2">1</xsl:if>
	<!-- 累积生息 -->
	<xsl:if test="$BonusGetMode = 3">5</xsl:if>
	<!-- 增额交清 -->
</xsl:template>

<!-- 保单的缴费方式 -->
<xsl:template name="tran_Contpayintv">
	<xsl:param name="payintv">0</xsl:param>
	<xsl:choose>
		<xsl:when test="$payintv = 01">-1</xsl:when>
		<!-- 不定期交 -->
		<xsl:when test="$payintv = 02">0</xsl:when>
		<!-- 趸交 -->
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> 月交 -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> 季交 -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> 半年交 -->
		<xsl:when test="$payintv = 03">12</xsl:when>
		<!-- 年交 -->
		<xsl:when test="$payintv = 04">98</xsl:when>
		<!-- 交至某确定年 -->
		<xsl:when test="$payintv = 05">99</xsl:when>
		<!-- 终身交费 -->
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

<!-- 证件类型 -->
<xsl:template name="tran_idtype">
	<xsl:param name="idtype"></xsl:param>
	<xsl:choose>
		<xsl:when test="$idtype = '1010'">0</xsl:when>
		<!-- 公民身份证号码 -->
		<xsl:when test="$idtype = '1022'">2</xsl:when>
		<!-- 军官证 -->
		<xsl:when test="$idtype = '1032'">D</xsl:when>
		<!-- 警官证 -->
		<xsl:when test="$idtype = '1021'">A</xsl:when>
		<!-- 解放军士兵证 -->
		<xsl:when test="$idtype = '1040'">4</xsl:when>
		<!-- 户口簿 -->
		<xsl:when test="$idtype = '1080'">B</xsl:when>
		<!-- (港澳)回乡证及通行证 -->
		<xsl:when test="$idtype = '1070'">B</xsl:when>
		<!-- 台湾来内地通行证-->
		<xsl:when test="$idtype = '1050'">1</xsl:when>
		<!-- 护照-->
		<xsl:when test="$idtype = '1051'">1</xsl:when>
		<!-- (外国)护照-->
		<xsl:when test="$idtype = '1052'">1</xsl:when>
		<!-- (中国)护照-->
		<xsl:when test="$idtype = '1060'">5</xsl:when>
		<!-- 学生证-->
		<xsl:when test="$idtype = '1999'">6</xsl:when>
		<!-- 个人其他证件-->
		<xsl:when test="$idtype = '2999'">6</xsl:when>
		<!-- 对公其他证件-->
		<xsl:when test="$idtype = '1100'">3</xsl:when>
		<!-- 驾照 -->
		<xsl:when test="$idtype = '1011'">C</xsl:when>
		<!-- 临时居民身份证 -->
		<xsl:when test="$idtype = '1160'">E</xsl:when>
		<!-- 台湾居民身份证 台胞证 -->
		<xsl:otherwise>
			<xsl:value-of select="8" />
			<!-- 其他 -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 关系 -->
<xsl:template name="tran_relation">
	<xsl:param name="RelaToInsured">00</xsl:param>
	<xsl:if test="$RelaToInsured = 0133043">00</xsl:if>
	<!--本人 -->
	<xsl:if test="$RelaToInsured = 0133010">02</xsl:if>
	<!--配偶 -->
	<xsl:if test="$RelaToInsured = 0133015">01</xsl:if>
	<!--父亲  -->
	<xsl:if test="$RelaToInsured = 0133016">01</xsl:if>
	<!--母亲 -->
	<xsl:if test="$RelaToInsured = 0133011">03</xsl:if>
	<!--儿子 -->
	<xsl:if test="$RelaToInsured = 0133012">03</xsl:if>
	<!--女儿-->
	<xsl:if test="$RelaToInsured = 0133017">04</xsl:if>
	<!--祖父-->
	<xsl:if test="$RelaToInsured = 0133018">04</xsl:if>
	<!--祖母-->
	<xsl:if test="$RelaToInsured = 0133013">04</xsl:if>
	<!--孙子 -->
	<xsl:if test="$RelaToInsured = 0133014">04</xsl:if>
	<!--孙女 -->
	<xsl:if test="$RelaToInsured = 0133020">06</xsl:if>
	<!--哥哥 -->
	<xsl:if test="$RelaToInsured = 0133019">06</xsl:if>
	<!--姐姐-->
	<xsl:if test="$RelaToInsured = 0133021">06</xsl:if>
	<!--其它亲属 -->
	<xsl:if test="$RelaToInsured = 0133002">06</xsl:if>
	<!--同事 -->
	<xsl:if test="$RelaToInsured = 0133001">06</xsl:if>
	<!--朋友-->
	<!-- 核心没朋友啊，只能为其他喽 Update 20130226 -->
</xsl:template>

<!-- 健康告知 -->
<xsl:template name="healFlag">
	<xsl:param name="HealFlag">N</xsl:param>
	<xsl:if test="$HealFlag = 0">N</xsl:if>
	<!-- 无健康告知 -->
	<xsl:if test="$HealFlag = 1">Y</xsl:if>
	<!-- 有健康告知 -->
</xsl:template>
</xsl:stylesheet>
