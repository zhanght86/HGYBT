<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TX">
		<TranData>
			<Head>
				<!--�������� -->
				<TranDate>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
				</TranDate>
				<!--����ʱ�� -->
				<TranTime>
					<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" />
				</TranTime>
				<!-- �������� -->
				<NodeNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" />
				</NodeNo>
				<!-- ���б��� -->
				<BankCode>0104</BankCode>
				<!--��Ա�� -->
				<TellerNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" />
				</TellerNo>
				<!-- ������ˮ�� -->
				<TranNo>
					<xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" />
				</TranNo>
				<!-- ���ж�ip[�Ǳ���]
				<ClientIp>127.0.0.1</ClientIp> -->
				<!-- ��������
				<TranCom>03</TranCom> -->
				<!-- 	<TranCom><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_ID" /></TranCom>   -->
				<!-- �������� -->
				<!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->
				<!-- ����id ?
				<ServiceId>0</ServiceId> -->

				<!-- �����������淽ʽ������Ϊ�˲������Լ��������ֶ� -->

				<!-- �������� �����������-->
				<!-- ����id �����������-->
				<!-- ���ж�ip[�Ǳ���] �����������-->



				<xsl:copy-of select="Head/*"/>
			</Head>
			<!-- ������ -->
			<xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">
		<Body>
			<!-- Ͷ����ӡˢ�� -->
			<ProposalPrtNo>
				<xsl:value-of select="Ins_Bl_Prt_No" />
			</ProposalPrtNo>
			<!--������ͬӡˢ�� (��֤) ���е�һ�β��������غ��ķ��ر���Ҫ¼�뵥֤�ţ����еڶ��η���֤��-->
			<ContPrtNo>
				<xsl:value-of select="BkVchNo" />
			</ContPrtNo>
			<!-- Ͷ������ -->
			<PolApplyDate>
				<xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" />
			</PolApplyDate>
			<!--Ͷ���˽ɷ��˺�  -->
			<AccNo>
				<xsl:value-of select="Plchd_PyF_AccNo" />
			</AccNo>
			<!-- �˻����� ���в����˻���Ĭ��ΪͶ��������-->
			<AccName>
				<xsl:value-of select="Plchd_Nm" />
			</AccName>
			<!-- �������ͷ�ʽ  Ĭ��ȫΪ1-->
			<GetPolMode>1</GetPolMode>
			<!-- ������֪ -->
			<HealthNotice>
				<xsl:call-template name="healFlag">
					<xsl:with-param name="HealFlag">
						<xsl:value-of select="Ntf_Itm_Ind"/>
					</xsl:with-param>
				</xsl:call-template>
			</HealthNotice>
			<!-- ������Ա����  -->
			<SaleName>
				<xsl:value-of select="BO_Sale_Stff_Nm" />
			</SaleName>
			<!-- ������Ա���  -->
			<SaleStaff>
				<xsl:value-of select="BO_Sale_Stff_ID" />
			</SaleStaff>
			<!-- ������Ա�ʸ�֤��  -->
			<!-- ����һ�����к� -->
			<Lv1BrNo>
				<xsl:value-of select="Lv1_Br_No" />
			</Lv1BrNo>

			<!-- Ͷ���� -->
			<Appnt>
				<!-- Ͷ�������� -->
				<Name>
					<xsl:value-of select="Plchd_Nm" />
				</Name>
				<!-- Ͷ�����Ա� -->
				<Sex>
					<xsl:call-template name="tran_sex">
						<xsl:with-param name="Sex">
							<xsl:value-of select="Plchd_Gnd_Cd" />
						</xsl:with-param>
					</xsl:call-template>
				</Sex>
				<!-- Ͷ�������� -->
				<Birthday>
					<xsl:value-of select="Plchd_Brth_Dt" />
				</Birthday>
				<!-- Ͷ����֤������ -->
				<IDType>
					<xsl:call-template name="tran_idtype">
						<xsl:with-param name="idtype">
							<xsl:value-of select="Plchd_Crdt_TpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</IDType>
				<!-- Ͷ����֤���� -->
				<IDNo>
					<xsl:value-of select="Plchd_Crdt_No" />
				</IDNo>
				<!-- Ͷ����֤����Ч����
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
			<!-- Ͷ����֤��ʧЧ����-->
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
			<!-- Ͷ���˹��� -->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
					<xsl:with-param name="Nationality">
						<xsl:value-of select="Plchd_Nat_Cd"/>
					</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- Ͷ���˵�ַ -->
			<Address>
				<xsl:value-of select="Plchd_Comm_Adr" />
			</Address>
			<!-- Ͷ������������-->
			<ZipCode>
				<xsl:value-of select="Plchd_ZipECD" />
			</ZipCode>
			<!-- Ͷ���˹̶��绰-->
			<Phone>
				<xsl:value-of select="Plchd_Fix_TelNo" />
			</Phone>
			<!-- Ͷ�����ƶ��绰-->
			<Mobile>
				<xsl:value-of select="Plchd_Move_TelNo" />
			</Mobile>
			<!-- Ͷ��������-->
			<Email>
				<xsl:value-of select="Plchd_Email_Adr" />
			</Email>
			<!-- Ͷ����ְҵ����-->
			<JobCode>
				<xsl:value-of select="Plchd_Ocp_Cd" />
			</JobCode>
			<!-- Ͷ���������� -->
			<YearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Plchd_Yr_IncmAm)"/>
			</YearSalary>
			<!-- Ͷ���˼�ͥ������ -->
			<FamilyYearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Fam_Yr_IncmAm)"/>
			</FamilyYearSalary>
			<!-- Ͷ�����뱻���˹�ϵ���� -->
			<RelaToInsured>
				<xsl:call-template name="tran_relation">
					<xsl:with-param name="RelaToInsured">
						<xsl:value-of select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</RelaToInsured>
			<!-- Ͷ���˾������� -->
			<DenType>
				<xsl:value-of select="Rsdnt_TpCd" />
			</DenType>

		</Appnt>

		<!-- ������ -->
		<Insured>
			<!-- ���������� -->
			<Name>
				<xsl:value-of select="Rcgn_Nm" />
			</Name>
			<!-- �������Ա� -->
			<Sex>
				<xsl:call-template name="tran_sex">
					<xsl:with-param name="Sex">
						<xsl:value-of select="Rcgn_Gnd_Cd" />
					</xsl:with-param>
				</xsl:call-template>
			</Sex>
			<!-- ���������� -->
			<Birthday>
				<xsl:value-of select="Rcgn_Brth_Dt" />
			</Birthday>
			<!-- ������֤������-->
			<IDType>
				<xsl:call-template name="tran_idtype">
					<xsl:with-param name="idtype">
						<xsl:value-of select="Rcgn_Crdt_TpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</IDType>
			<!-- ������֤����-->
			<IDNo>
				<xsl:value-of select="Rcgn_Crdt_No" />
			</IDNo>

			<!-- ������֤����Ч����
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
		<!-- ������֤��ʧЧ����-->
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
		<!-- �����˹���-->
		<Nationality>
			<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
					<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
			</xsl:call-template>
		</Nationality>
		<!-- �����˵�ַ-->
		<Address>
			<xsl:value-of select="Rcgn_Comm_Adr" />
		</Address>
		<!-- �������ʱ�-->
		<ZipCode>
			<xsl:value-of select="Rcgn_ZipECD" />
		</ZipCode>
		<!-- �����˹̶��绰-->
		<Phone>
			<xsl:value-of select="Rcgn_Fix_TelNo" />
		</Phone>
		<!-- �������ƶ�����-->
		<Mobile>
			<xsl:value-of select="Rcgn_Move_TelNo" />
		</Mobile>
		<!-- ����������-->
		<Email>
			<xsl:value-of select="Rcgn_Email_Adr" />
		</Email>
		<!-- ������ְҵ����-->
		<JobCode>
			<xsl:value-of select="Rcgn_Ocp_Cd" />
		</JobCode>
		<!-- ������������ -->
		<RcgnYrIncmAm>
			<xsl:value-of select="Rcgn_Yr_IncmAm" />
		</RcgnYrIncmAm>
		<!-- δ���걻���������������չ�˾�ۼ���ʱ��� -->
		<CovSumAmt>
			<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)"/>
		</CovSumAmt>

	</Insured>


	<!-- ������ -->

	<xsl:choose>
		<xsl:when
			test="Benf_Num ='0'">
		</xsl:when>
		<xsl:when
			test="Benf_Num !='0'">
			<!-- 		<xsl:for-each select="Benf_Detail">    �����Լ����Ĳ��Ա���û��Benf_List/Benf_Detailֻ�� Benf_Detail -->
			<xsl:for-each select="Benf_List/Benf_Detail">
				<Bnf>
					<!-- ���������ʹ��� -->
					<Type>
						<xsl:value-of select="AgIns_Benf_TpCd" />
					</Type>
					<!-- ��������� -->
					<BenfSN>
						<xsl:value-of select="Benf_SN" />
					</BenfSN>
					<!-- ����������˳�� -->
					<Grade>
						<xsl:value-of select="Benf_Bnft_Seq" />
					</Grade>
					<!-- ���������� -->
					<Name>
						<xsl:value-of select="Benf_Nm" />
					</Name>
					<!-- �������Ա� -->
					<Sex>
						<xsl:call-template name="tran_sex">
							<xsl:with-param name="Sex">
								<xsl:value-of select="Benf_Gnd_Cd" />
							</xsl:with-param>
						</xsl:call-template>
					</Sex>
					<!-- �����˳������� -->
					<Birthday>
						<xsl:value-of
						select="Benf_Brth_Dt" />
					</Birthday>
					<!-- ������֤������ -->
					<IDType>
						<xsl:call-template name="tran_idtype">
							<xsl:with-param name="idtype">
								<xsl:value-of
								select="Benf_Crdt_TpCd" />
							</xsl:with-param>
						</xsl:call-template>
					</IDType>
					<!-- ������֤���� -->
					<IDNo>
						<xsl:value-of select="Benf_Crdt_No" />
					</IDNo>
					<!-- ������֤����Ч����-->
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
					<!-- ������֤��ʧЧ����-->
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
					<!-- �����˹���-->
					<Nationality>
						<xsl:call-template name="tran_Nationality">
							<xsl:with-param name="Nationality">
								<xsl:value-of select="Benf_Nat_Cd" />
							</xsl:with-param>
						</xsl:call-template>
					</Nationality>
					<!-- �������뱻���˹�ϵ���� -->
					<RelaToInsured>
						<xsl:call-template name="tran_relation">
							<xsl:with-param name="RelaToInsured">
								<xsl:value-of
								select="Benf_And_Rcgn_ReTpCd" />
							</xsl:with-param>
						</xsl:call-template>
					</RelaToInsured>
					<!-- ������������� -->
					<Lot>
						<xsl:value-of
						select="Bnft_Pct" />
					</Lot>
					<!-- ������ͨѶ��ַ -->
					<BenfCommAdr>
						<xsl:value-of select="Benf_Comm_Adr" />
					</BenfCommAdr>
				</Bnf>
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>


	<xsl:choose>
		<!-- ���ָ��� -->
		<xsl:when
			test="Cvr_Num ='0'">
		</xsl:when>
		<xsl:when
			test="Cvr_Num !='0'">
			<xsl:for-each select="Busi_List/Busi_Detail">
				<Risk>
					<!-- ���ֱ�� -->
					<RiskCode>
						<xsl:value-of select="Cvr_ID" />
					</RiskCode>
					<!-- �������� -->
					<RiskName>
						<xsl:value-of select="Cvr_Nm" />
					</RiskName>
					<!-- ���� -->
					<Amnt>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Ins_Cvr)" />
					</Amnt>
					<!-- ���� -->
					<Prem>
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" />
					</Prem>
					<!-- Ͷ������ -->
					<Mult>
						<xsl:value-of select="Ins_Cps" />
					</Mult>
					<PayMode>B</PayMode>
					<!-- ���ѽ��ɷ�ʽ���� -->
					<PayIntv>
						<xsl:call-template name="tran_Contpayintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="InsPrem_PyF_MtdCd" />
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- �ɷ����ںͽɷ����� -->
					<xsl:if test="InsPrem_PyF_MtdCd = 0">
						<!-- ����1000Y������ -->
						<PayEndYear>1000</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<xsl:if test="InsPrem_PyF_MtdCd != 0">
						<!-- ����û�н�����������һ˵ -->
						<PayEndYear>
							<xsl:value-of select="InsPrem_PyF_Prd_Num" />
						</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<!-- ���ѽ�������
					<PayEndYear>
					<xsl:value-of select="InsPrem_PyF_Prd_Num" />
				</PayEndYear>-->
				<!-- ���ѽ������ڴ���
				<PayEndYearFlag>
				<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
			</PayEndYearFlag>-->
			<!-- ���ڱ��ս���ȡ��ʽ -->
			<GetIntv>
				<xsl:call-template name="tran_GetIntv">
					<xsl:with-param name="GetIntv">
						<xsl:value-of select="ExpPrmmRcvModCgyCd" />
					</xsl:with-param>
				</xsl:call-template>
			</GetIntv>
			<!-- �������ȡ���ڴ��� -->
			<GetYearFlag>
				<xsl:call-template name="tran_LiRenteDrawMode">
					<xsl:with-param name="GETLiRenteDrawMode">
						<xsl:value-of select="SvBnf_Drw_Cyc_Cd" />
					</xsl:with-param>
				</xsl:call-template>
			</GetYearFlag>
			<!-- ��ȡ�������ڱ�־  �����Ҫת��-->
			<!-- Լ�����ѵ潻��־ -->
			<AutoPayFlag>
				<xsl:call-template name="apntInsPayType">
					<xsl:with-param name="ApntInsPayType">
						<xsl:value-of select="ApntInsPremPyAdvnInd" />
					</xsl:with-param>
				</xsl:call-template>
			</AutoPayFlag>
			<!-- ������ȡ��ʽ -->
			<BonusGetMode>
				<xsl:call-template name="tran_BonusGetMode">
					<xsl:with-param name="BonusGetMode">
						<xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
					</xsl:with-param>
				</xsl:call-template>
			</BonusGetMode>
			<!-- ������־ -->
			<SubFlag>
				<xsl:value-of select="RdAmtPyCls_Ind" />
			</SubFlag>

			<!-- �����ȡ������
			<Anuty_Drw_CgyCd>
			<xsl:value-of select="Anuty_Drw_CgyCd" />
		</Anuty_Drw_CgyCd> -->
		<!-- �����ȡ���� -->
		<GetYear>
			<xsl:value-of select="Anuty_Drw_Prd_Num" />
		</GetYear>
		<!-- ��ȡ���� ������֮ǰ��GetTerms-->
		<!-- �����ȡ���ڴ���
		<Anuty_Drw_Cyc_Cd>
		<xsl:value-of select="Anuty_Drw_Cyc_Cd" />
	</Anuty_Drw_Cyc_Cd>-->
	<!-- �������������� -->
	<InsuYearFlag>
		<xsl:call-template name="tran_PbIYF">
			<xsl:with-param name="PbInsuYearFlag">
				<xsl:value-of select="Ins_Yr_Prd_CgyCd" />
			</xsl:with-param>
		</xsl:call-template>
	</InsuYearFlag>
	<!-- �������� -->
	<InsuYear>
		<xsl:value-of select="Ins_Ddln" />
	</InsuYear>
	<!-- ��ȡ���б��� ����-->
	<GetBankCode></GetBankCode>
	<!-- ��ȡ�����˻� ����-->
	<GetBankAccNo></GetBankAccNo>
	<!-- ��ȡ���л���  ����-->
	<GetAccName></GetAccName>
	<!-- �Զ��潻��־ ����-->
	<AutoPayFlag></AutoPayFlag>

</Risk>

</xsl:for-each>
</xsl:when>
</xsl:choose>



</Body>
</xsl:template>


<!-- Լ�����ѵ潻��־ -->
<xsl:template name="apntInsPayType" >
	<xsl:param name="ApntInsPayType"></xsl:param>
	<xsl:if test="$ApntInsPayType = 0">N</xsl:if>
	<!-- ���潻 -->
	<xsl:if test="$ApntInsPayType = 1">Y</xsl:if>
	<!-- �Զ��潻 -->
</xsl:template>
<!-- �Ա� -->
<xsl:template name="tran_sex" >
	<xsl:param name="Sex"></xsl:param>
	<xsl:if test="$Sex = 1">0</xsl:if>
	<!-- �� -->
	<xsl:if test="$Sex = 2">1</xsl:if>
	<!-- Ů -->
</xsl:template>

<!-- ���ս���ȡ��ʽ -->
<xsl:template name="tran_GetIntv">
	<xsl:param name="GetIntv"></xsl:param>
	<xsl:if test="$GetIntv = 0">0</xsl:if>
	<!--����  -->
	<xsl:if test="$GetIntv = 1">1</xsl:if>
	<!-- ���� -->
	<xsl:if test="$GetIntv = 12">12</xsl:if>
	<!-- ���� -->
</xsl:template>


<xsl:template name="tran_LiRenteDrawMode">
	<xsl:param name="GETLiRenteDrawMode"></xsl:param>
	<xsl:if test="$GETLiRenteDrawMode = 0">Y</xsl:if>
	<!--  0-һ�θ���  -->
	<xsl:if test="$GETLiRenteDrawMode = 1">Y</xsl:if>
	<!--  1-�¸���  -->
	<xsl:if test="$GETLiRenteDrawMode = 3">Y</xsl:if>
	<!--  3-������  -->
	<xsl:if test="$GETLiRenteDrawMode = 6">Y</xsl:if>
	<!--  6-�������  -->
	<xsl:if test="$GETLiRenteDrawMode = 12">Y</xsl:if>
	<!--  12-�����  -->
</xsl:template>

<!-- Ͷ�����˹��� -->
<xsl:template name="tran_Nationality">
	<xsl:param name="Nationality"></xsl:param>
	<xsl:if test="$Nationality = 0156">CHN</xsl:if>
	<xsl:if test="$Nationality = 0344"></xsl:if>
	<!--��� -->
	<xsl:if test="$Nationality = 0158"></xsl:if>
	<!--̨�� -->
	<xsl:if test="$Nationality = 0446"></xsl:if>
	<!--���� -->
	<xsl:if test="$Nationality = 0392">JAN</xsl:if>
	<!--�ձ� -->
	<xsl:if test="$Nationality = 0840">USA</xsl:if>
	<!--����-->
	<xsl:if test="$Nationality = 0643"></xsl:if>
	<!--����˹ -->
	<xsl:if test="$Nationality = 0826">ENG</xsl:if>
	<!--Ӣ�� -->
	<xsl:if test="$Nationality = 0250">FRA</xsl:if>
	<!--����-->
	<xsl:if test="$Nationality = 0276">DEU</xsl:if>
	<!--�¹� -->
	<xsl:if test="$Nationality = 0410">KOR</xsl:if>
	<!--���� -->
	<xsl:if test="$Nationality = 0702">SG</xsl:if>
	<!--�¼��� -->
	<xsl:if test="$Nationality = 0360">INA</xsl:if>
	<!--ӡ��������-->
	<xsl:if test="$Nationality = 0356">IND</xsl:if>
	<!--ӡ��-->
	<xsl:if test="$Nationality = 0380">ITA</xsl:if>
	<!--����� -->
	<xsl:if test="$Nationality = 0458">MY</xsl:if>
	<!--�������� -->
	<xsl:if test="$Nationality = 0764">THA</xsl:if>
	<!--̩��-->
	<xsl:if test="$Nationality = 0999"></xsl:if>
	<!--����-->
</xsl:template>

<!-- ������ȡ��ʽ -->
<xsl:template name="tran_BonusGetMode">
	<xsl:param name="BonusGetMode"></xsl:param>
	<xsl:if test="$BonusGetMode = 0">2</xsl:if>
	<!-- ֱ�Ӹ��� -->
	<xsl:if test="$BonusGetMode = 1">3</xsl:if>
	<!-- �ֽ����� -->
	<xsl:if test="$BonusGetMode = 2">1</xsl:if>
	<!-- �ۻ���Ϣ -->
	<xsl:if test="$BonusGetMode = 3">5</xsl:if>
	<!-- ����� -->
</xsl:template>

<!-- �����Ľɷѷ�ʽ -->
<xsl:template name="tran_Contpayintv">
	<xsl:param name="payintv">0</xsl:param>
	<xsl:choose>
		<xsl:when test="$payintv = 01">-1</xsl:when>
		<!-- �����ڽ� -->
		<xsl:when test="$payintv = 02">0</xsl:when>
		<!-- ���� -->
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> �½� -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> ���� -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> ���꽻 -->
		<xsl:when test="$payintv = 03">12</xsl:when>
		<!-- �꽻 -->
		<xsl:when test="$payintv = 04">98</xsl:when>
		<!-- ����ĳȷ���� -->
		<xsl:when test="$payintv = 05">99</xsl:when>
		<!-- ������ -->
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
	<xsl:param name="idtype"></xsl:param>
	<xsl:choose>
		<xsl:when test="$idtype = '1010'">0</xsl:when>
		<!-- �������֤���� -->
		<xsl:when test="$idtype = '1022'">2</xsl:when>
		<!-- ����֤ -->
		<xsl:when test="$idtype = '1032'">D</xsl:when>
		<!-- ����֤ -->
		<xsl:when test="$idtype = '1021'">A</xsl:when>
		<!-- ��ž�ʿ��֤ -->
		<xsl:when test="$idtype = '1040'">4</xsl:when>
		<!-- ���ڲ� -->
		<xsl:when test="$idtype = '1080'">B</xsl:when>
		<!-- (�۰�)����֤��ͨ��֤ -->
		<xsl:when test="$idtype = '1070'">B</xsl:when>
		<!-- ̨�����ڵ�ͨ��֤-->
		<xsl:when test="$idtype = '1050'">1</xsl:when>
		<!-- ����-->
		<xsl:when test="$idtype = '1051'">1</xsl:when>
		<!-- (���)����-->
		<xsl:when test="$idtype = '1052'">1</xsl:when>
		<!-- (�й�)����-->
		<xsl:when test="$idtype = '1060'">5</xsl:when>
		<!-- ѧ��֤-->
		<xsl:when test="$idtype = '1999'">6</xsl:when>
		<!-- ��������֤��-->
		<xsl:when test="$idtype = '2999'">6</xsl:when>
		<!-- �Թ�����֤��-->
		<xsl:when test="$idtype = '1100'">3</xsl:when>
		<!-- ���� -->
		<xsl:when test="$idtype = '1011'">C</xsl:when>
		<!-- ��ʱ�������֤ -->
		<xsl:when test="$idtype = '1160'">E</xsl:when>
		<!-- ̨��������֤ ̨��֤ -->
		<xsl:otherwise>
			<xsl:value-of select="8" />
			<!-- ���� -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ��ϵ -->
<xsl:template name="tran_relation">
	<xsl:param name="RelaToInsured">00</xsl:param>
	<xsl:if test="$RelaToInsured = 0133043">00</xsl:if>
	<!--���� -->
	<xsl:if test="$RelaToInsured = 0133010">02</xsl:if>
	<!--��ż -->
	<xsl:if test="$RelaToInsured = 0133015">01</xsl:if>
	<!--����  -->
	<xsl:if test="$RelaToInsured = 0133016">01</xsl:if>
	<!--ĸ�� -->
	<xsl:if test="$RelaToInsured = 0133011">03</xsl:if>
	<!--���� -->
	<xsl:if test="$RelaToInsured = 0133012">03</xsl:if>
	<!--Ů��-->
	<xsl:if test="$RelaToInsured = 0133017">04</xsl:if>
	<!--�游-->
	<xsl:if test="$RelaToInsured = 0133018">04</xsl:if>
	<!--��ĸ-->
	<xsl:if test="$RelaToInsured = 0133013">04</xsl:if>
	<!--���� -->
	<xsl:if test="$RelaToInsured = 0133014">04</xsl:if>
	<!--��Ů -->
	<xsl:if test="$RelaToInsured = 0133020">06</xsl:if>
	<!--��� -->
	<xsl:if test="$RelaToInsured = 0133019">06</xsl:if>
	<!--���-->
	<xsl:if test="$RelaToInsured = 0133021">06</xsl:if>
	<!--�������� -->
	<xsl:if test="$RelaToInsured = 0133002">06</xsl:if>
	<!--ͬ�� -->
	<xsl:if test="$RelaToInsured = 0133001">06</xsl:if>
	<!--����-->
	<!-- ����û���Ѱ���ֻ��Ϊ����� Update 20130226 -->
</xsl:template>

<!-- ������֪ -->
<xsl:template name="healFlag">
	<xsl:param name="HealFlag">N</xsl:param>
	<xsl:if test="$HealFlag = 0">N</xsl:if>
	<!-- �޽�����֪ -->
	<xsl:if test="$HealFlag = 1">Y</xsl:if>
	<!-- �н�����֪ -->
</xsl:template>
</xsl:stylesheet>
