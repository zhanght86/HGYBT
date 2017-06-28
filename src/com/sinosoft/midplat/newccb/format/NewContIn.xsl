<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<!-- �������� -->
	<xsl:variable name="tSellType" select="/TX/TX_BODY/ENTITY/COM_ENTITY/TXN_ITT_CHNL_CGY_CODE"></xsl:variable>
	<!-- �����������룺���棺0���ֻ����У�2��������1�������նˣ�8-->
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
			    <!--�������� -->
				<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
				<!--����ʱ�� -->
				<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
				<!-- �������� -->
				<xsl:variable name="tNodeNo" select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID"></xsl:variable>
				<xsl:choose>
					<xsl:when test="$tLisSaleChnl = '0'"><!--�������� -->
						<NodeNo><xsl:value-of select="$tNodeNo" /></NodeNo>
					</xsl:when>
					<xsl:when test="$tLisSaleChnl != '0'"><!--��������:���д������ȡֵ�������ģ�����CCBWEB -->
						<xsl:if test="$tNodeNo = ''">
							<NodeNo>CCBWEB</NodeNo>
						</xsl:if>
						<xsl:if test="$tNodeNo != ''">
							<NodeNo><xsl:value-of select="$tNodeNo"/></NodeNo>
						</xsl:if>
					</xsl:when>
				</xsl:choose>
				<!-- ������������ -->
				<SellType><xsl:value-of select="$tSellType"/></SellType>
				<!-- ���ؽڵ㣬�������õ� -->
				<LocalID><xsl:value-of select="TX_HEADER/LocalID"/></LocalID>
				<!-- �Զ˽ڵ㣬�������õ� -->
				<remoteID><xsl:value-of select="TX_HEADER/remoteID"/></remoteID>
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
	<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
	<Body>
		<!--�����������룺���棺0���ֻ����У�2��������1�������նˣ�8-->
		<SaleChannel><xsl:value-of select="$tLisSaleChnl" /></SaleChannel>
	    <!-- Ͷ����ӡˢ�� -->
	    <xsl:choose>
			<xsl:when test="$tLisSaleChnl = '0'"><!--�������� -->
				<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_Bl_Prt_No)" /></ProposalPrtNo>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="tMaxPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.CreateMaxNo('CCBPRTNO','SN')"></xsl:variable> 
				<xsl:variable name="tPrtNo" select="java:com.sinosoft.midplat.util.YBTFun.PrtNoTo8($tMaxPrtNo,'03')"></xsl:variable> 
				<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15($tPrtNo)" /></ProposalPrtNo>
			</xsl:otherwise>
		</xsl:choose>
		<!--������ͬӡˢ�� (��֤) Ŀǰ�����½ӿڲ���,������ӡ���лᴫ��֤��-->
		<ContPrtNo></ContPrtNo>   
		<!-- Ͷ������ -->
		<PolApplyDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></PolApplyDate>
           <!--Ͷ���˽ɷ��˺�  -->
           <AccNo><xsl:value-of select="Plchd_PyF_AccNo" /></AccNo>
           <!-- �˻����� ���в����˻���Ĭ��ΪͶ��������-->
			 <AccName><xsl:value-of select="Plchd_Nm" /></AccName> 
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
           <SaleName><xsl:value-of select="BO_Sale_Stff_Nm" /></SaleName>
           <!-- ������Ա���� -->
           <SaleStaff><xsl:value-of select="BO_Sale_Stff_ID" /></SaleStaff>
           <!-- ������Ա�ʸ�֤����  -->
           <SaleCertNo><xsl:value-of select="Sale_Stff_AICSQCtf_ID" /></SaleCertNo>
           <!-- ������Ա�ʸ�֤��  -->
           <!-- ����һ�����к� -->
           <Lv1BrNo><xsl:value-of select="Lv1_Br_No" /></Lv1BrNo>
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
			<!-- Ͷ����֤��ʧЧ����-->
			<xsl:choose>
				<xsl:when test="Plchd_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Plchd_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
			</xsl:choose>				
			<!-- Ͷ���˹��� -->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Plchd_Nat_Cd"/>
				</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- Ͷ������ϸ��ַ���� -->
			<AddressContent><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></AddressContent>
			<!-- Ͷ���˹̶��绰�������� -->
			<FixTelDmstDstcNo><xsl:value-of select="PlchdFixTelDmstDstcNo" /></FixTelDmstDstcNo>
			<!-- Ͷ�����ƶ��绰�������� -->
			<MobileItlDstcNo><xsl:value-of select="PlchdMoveTelItlDstcNo" /></MobileItlDstcNo>
			<!-- Ͷ���˹��ҵ������� -->
			<NationalityCode><xsl:value-of select="Plchd_Nat_Cd" /></NationalityCode>
			<!-- ʡ��¼�� -->
			<Province><xsl:value-of select="Plchd_Prov_Cd" /></Province>
			<!-- �� ��¼��-->
			<City><xsl:value-of select="Plchd_City_Cd" /></City>
			<!-- ����¼��-->
			<County><xsl:value-of select="Plchd_CntyAndDstc_Cd" /></County>
			<!-- Ͷ���˵�ַ -->
			<Address><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></Address>
			<!-- ʡ�Ǳ��� -->
			<WorkProvince></WorkProvince>
			<!-- �� �Ǳ���-->
			<WorkCity></WorkCity>
			<!-- ���Ǳ���-->
			<WorkCounty></WorkCounty>
			<!--��λ��ַ -->
			<WorkAddress><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></WorkAddress>
			<!-- Ͷ������������-->
			<ZipCode><xsl:value-of select="Plchd_ZipECD" /></ZipCode>
			<!-- Ͷ���˹̶��绰-->
			<Phone>
				<xsl:value-of select="PlchdFixTelDmstDstcNo" /><xsl:value-of select="Plchd_Fix_TelNo" />
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
			<xsl:call-template name="tran_JobCode">
		      <xsl:with-param name="JobCode">
	            <xsl:value-of select="Plchd_Ocp_Cd"/>
		      </xsl:with-param>
	        </xsl:call-template>
			</JobCode>
			<!-- Ͷ���������� -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Plchd_Yr_IncmAm)"/></YearSalary>
			<!-- Ͷ���˼�ͥ������ -->
			<FamilyYearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Fam_Yr_IncmAm)"/></FamilyYearSalary>
			<!-- Ͷ�����뱻���˹�ϵ���� -->
			<RelaToInsured>
				<xsl:call-template name="tran_relation">
					<xsl:with-param name="RelaToInsured">
						<xsl:value-of select="Plchd_And_Rcgn_ReTpCd" />
					</xsl:with-param>
				</xsl:call-template>
			</RelaToInsured>				
			<!-- <RelaToInsured2><xsl:value-of select="Plchd_And_Rcgn_ReTpCd" /></RelaToInsured2> -->				
			<!-- Ͷ���˾������� -->
			<DenType>
			  <xsl:call-template name="tran_Rsdnt_TpCd">
			    <xsl:with-param name="Rsdnt_TpCd">
			      <xsl:value-of select="Rsdnt_TpCd" />
				</xsl:with-param>
			  </xsl:call-template>
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
			<!-- ������֤��ʧЧ����-->
			<xsl:choose>
				<xsl:when test="Rcgn_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="Rcgn_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
				<!-- �����˹���-->
			<Nationality>
				<xsl:call-template name="tran_Nationality">
				<xsl:with-param name="Nationality">
						<xsl:value-of select="Rcgn_Nat_Cd" />
				</xsl:with-param>
				</xsl:call-template>
			</Nationality>
			<!-- ��������ϸ��ַ���� -->
			<AddressContent><xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" /></AddressContent>
			<!-- �����˹̶��绰�������� -->
			<FixTelDmstDstcNo><xsl:value-of select="RcgnFixTelDmst_DstcNo" /></FixTelDmstDstcNo>
			<!-- �������ƶ��绰�������� -->
			<MobileItlDstcNo><xsl:value-of select="RcgnMoveTelItnlDstcNo" /></MobileItlDstcNo>
			<!-- �����˹��ҵ������� -->
			<NationalityCode><xsl:value-of select="Rcgn_Nat_Cd" /></NationalityCode>
			<!-- ʡ��¼�� -->
			<Province><xsl:value-of select="Rcgn_Prov_Cd" /></Province>
			<!-- �� ��¼��-->
			<City><xsl:value-of select="Rcgn_City_Cd" /></City>
			<!-- ����¼��-->
			<County><xsl:value-of select="Rcgn_CntyAndDstc_Cd" /></County>
			<!-- �����˵�ַ-->
			<Address><xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" /></Address>
			<!-- ʡ�Ǳ��� -->
			<WorkProvince></WorkProvince>
			<!-- �� �Ǳ���-->
			<WorkCity></WorkCity>
			<!-- ���Ǳ���-->
			<WorkCounty></WorkCounty>
			<!--��λ��ַ -->
			<WorkAddress></WorkAddress>
			<!-- �������ʱ�-->
			<ZipCode>
				<xsl:value-of select="Rcgn_ZipECD" />
			</ZipCode>
			<!-- �����˹̶��绰-->
			<Phone>
				<xsl:value-of select="RcgnFixTelDmst_DstcNo" /><xsl:value-of select="Rcgn_Fix_TelNo" />
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
			<xsl:call-template name="tran_JobCode">
		      <xsl:with-param name="JobCode">
	            <xsl:value-of select="Rcgn_Ocp_Cd"/>
		      </xsl:with-param>
	        </xsl:call-template>
			</JobCode>
			<!-- ������������ -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(Rcgn_Yr_IncmAm)" /></YearSalary>
			<!-- δ���걻���������������չ�˾�ۼ���ʱ��� -->
            <CovSumAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Minr_Acm_Cvr)"/></CovSumAmt>
			<!--�ͻ����ճ�����������-->
			<RiskAssessmentResult><xsl:value-of select="Cst_Rsk_Tlrnc_Cpy_Cd" /></RiskAssessmentResult>
			<!--���ղ�����Ч��-->
			<RiskAssessmentEndDate><xsl:value-of select="Rsk_Evlt_AvlDt" /></RiskAssessmentEndDate>
			<!--Ԥ����-->
			<PremiumBudget><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Bdgt_Amt)" /></PremiumBudget>
		</Insured>
		<!-- ������ -->
		<xsl:choose>
			<xsl:when
				test="Benf_Num ='0'">
			</xsl:when>
			<xsl:when
				test="Benf_Num !='0'">
				<xsl:for-each select="Benf_List/Benf_Detail">
				<Bnf>
				<!-- ���������ʹ��� -->
				<Type><xsl:value-of select="AgIns_Benf_TpCd" /></Type>
				<!-- ��������� -->
				<BenfSN><xsl:value-of select="Benf_SN" /></BenfSN>
				<!-- ����������˳�� -->
				<Grade><xsl:value-of select="Benf_Bnft_Seq" /></Grade>
				<!-- ���������� -->
				<Name><xsl:value-of select="Benf_Nm" /></Name>
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
				<IDNo><xsl:value-of select="Benf_Crdt_No" /></IDNo>
				<!-- ������֤��ʧЧ����-->
				<xsl:choose>
					<xsl:when test="Benf_Crdt_ExpDt=20991231" ><IdExpDate>99990101</IdExpDate></xsl:when>
					<xsl:otherwise><IdExpDate><xsl:value-of select="Benf_Crdt_ExpDt"/></IdExpDate></xsl:otherwise>
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
							<xsl:value-of select="Benf_And_Rcgn_ReTpCd" />
						</xsl:with-param>
					</xsl:call-template>
				</RelaToInsured>
				<!-- <RelaToInsured3><xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured3> -->	
				<!-- ������������� -->
				<Lot><xsl:value-of select="Bnft_Pct" /></Lot>
				<!-- ��������ϸ��ַ���� -->
				<AddressContent><xsl:value-of select="Benf_Dtl_Adr_Cntnt" /></AddressContent>
				<!-- ������ͨѶ��ַ -->
				<BenfCommAdr><xsl:value-of select="Benf_Comm_Adr" /></BenfCommAdr>
				<BeneficType>N</BeneficType>
				</Bnf>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
		<xsl:variable name="MainCode">
		  <xsl:value-of select="Busi_List/Busi_Detail[MainAndAdlIns_Ind='1']/Cvr_ID"/><!--�����ձ�־1������ -->
		</xsl:variable>
		<xsl:choose>
			<!-- ���ָ��� -->
			<xsl:when test="Cvr_Num ='0'">
			</xsl:when>
			<xsl:when test="Cvr_Num !='0'">
				<xsl:for-each select="Busi_List/Busi_Detail">
					<Risk>
						<!-- ���ֱ�� -->
						<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
						<!--�����ֱ��� -->
						<MainRiskCode><xsl:value-of select="$MainCode"/></MainRiskCode>
						<!-- �������� -->
						<RiskName><xsl:value-of select="Cvr_Nm" /></RiskName>
						<!-- �������ײͱ�� -->
						<AgInsPkgID><xsl:value-of select="AgIns_Pkg_ID" /></AgInsPkgID>
						<!-- ���� -->
						<Amnt>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Ins_Cvr)" />
						</Amnt>
						<!-- ���� -->
						<Prem>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsPrem_Amt)" />
						</Prem>
						<!-- Ͷ������ -->
						<Mult><xsl:value-of select="Ins_Cps" /></Mult>
						<!-- ��¼�������־  �����Զ�����; N�����Զ�����  Ĭ��ΪN-->
						<RnewFlag>N</RnewFlag>
						<PayMode>B</PayMode>
						<!-- �ɷ�Ƶ�����ݲ�����ϸ��ȡ���ѽɷ����ڴ���-->
						<PayIntv>
							<xsl:call-template name="tran_payintv">
								<xsl:with-param name="PayIntv">
									<xsl:value-of select="InsPrem_PyF_Cyc_Cd" />
								</xsl:with-param>
							</xsl:call-template>
						</PayIntv> 
						<xsl:choose>
							<!-- �����ʱ�2����ȫ����(������) -->
							<xsl:when test="Cvr_ID='011A0100'">
								<PayEndYear>1000</PayEndYear>
								<PayEndYearFlag>Y</PayEndYearFlag>
							</xsl:when>
							<!-- ���������������˺�����(������) -->
							<xsl:when test="Cvr_ID='022J0300'">
								<PayEndYear>12</PayEndYear>
								<PayEndYearFlag>M</PayEndYearFlag>
							</xsl:when>
							<xsl:otherwise>
								<!-- �ɷ����ںͽɷ����� -->
								 <xsl:if test="InsPrem_PyF_Cyc_Cd = '0100'">
								 	<!-- ������PayEndYear=1000;PayEndYearFlag=Y������ -->				    
									<PayEndYear>1000</PayEndYear>
									<PayEndYearFlag>Y</PayEndYearFlag>
								</xsl:if>
								<!-- �����޽�����������-->
								<xsl:if test="InsPrem_PyF_Cyc_Cd != '0100'">	
									<PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear>			
								    <PayEndYearFlag>Y</PayEndYearFlag>			    
								</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
						<!--�����ڼ�-->	 
						<xsl:choose>		
							<xsl:when test="Ins_Yr_Prd_CgyCd='05'">	<!-- ������ -->
								<!--������ʢ�����������  -->
								<xsl:if test="Cvr_ID='012E0100'">
									<!-- �������������־ -->
									<InsuYearFlag>A</InsuYearFlag>
									<!-- ������������ -->
									<InsuYear>105</InsuYear> 
								</xsl:if>
							</xsl:when> 
							<xsl:otherwise>	
								<!-- �������������־ -->
								<InsuYearFlag>
									<xsl:call-template name="tran_PbIYF">
										<xsl:with-param name="PbInsuYearFlag">
											<!--  <xsl:value-of select="Ins_Cyc_Cd" />-->
											<xsl:value-of select="Ins_Yr_Prd_CgyCd" />
										</xsl:with-param>
									</xsl:call-template>	
								</InsuYearFlag>
								<!-- ������������ -->
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
			   	        <!-- �������ȡ���ڴ��� -->
			   	        <GetYearFlag>
			   	            <xsl:call-template name="tran_LiRenteDrawMode">
							<xsl:with-param name="GETLiRenteDrawMode">
								 <xsl:value-of select="SvBnf_Drw_Cyc_Cd" />
							</xsl:with-param>
							</xsl:call-template>
			   	        </GetYearFlag> 
						<!-- ������ȡ��ʽ -->
						<BonusGetMode>
							<xsl:call-template name="tran_BonusGetMode">
								<xsl:with-param name="BonusGetMode">
									<xsl:value-of select="XtraDvdn_Pcsg_MtdCd" />
								</xsl:with-param>
							</xsl:call-template>
					    </BonusGetMode>
						<!-- ������־ -->
						<SubFlag><xsl:value-of select="RdAmtPyCls_Ind" /></SubFlag>
						<!-- �����ȡ���� -->
		                <GetYear><xsl:value-of select="Anuty_Drw_Prd_Num" /></GetYear><!-- ��ȡ���� ������֮ǰ��GetTerms-->				
						<!-- ��ȡ���б��� ����-->
			   			<GetBankCode></GetBankCode> 
			   			<!-- ��ȡ�����˻� ����-->
			   			<GetBankAccNo></GetBankAccNo> 
			   			<!-- ��ȡ���л���  ����-->
						<GetAccName></GetAccName> 
						<!-- �Զ��潻��־ ����-->
						<AutoPayFlag><xsl:value-of select="ApntInsPremPyAdvnInd" /></AutoPayFlag> 
					</Risk>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>	
		<!-- �ǻ��ն�������Ҫ��Ӱ���ļ�����Ҫ��Ӱ���ļ����������� ;
        	    �����д�Ӱ���ļ�ʱ���ʹ��������޲���-->
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
	<!-- ����ת�� -->
	<xsl:template name="transChannel">
		<xsl:param name="nTransChannel"></xsl:param>
		<xsl:choose>
			<xsl:when test="$nTransChannel='20170029'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20180030'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20220037'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='20230038'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='19999999'">0</xsl:when>	<!-- ������� -->
			<xsl:when test="$nTransChannel='10010001'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10010002'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10010003'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10060009'">1</xsl:when>	<!-- �������� -->
			<xsl:when test="$nTransChannel='10110023'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110016'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110017'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110018'">8</xsl:when>	<!-- �����ն� -->
			<xsl:when test="$nTransChannel='10110109'">8</xsl:when><!--  �ǻ��նˣ��鵽�����ն� --> 
			<xsl:when test="$nTransChannel='10030006'">2</xsl:when><!--  �ֻ����� --> 
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- ��������-->
	<xsl:template name="tran_Rsdnt_TpCd" >
		<xsl:param name="Rsdnt_TpCd"></xsl:param>
		<xsl:if test="$Rsdnt_TpCd = '1'">2</xsl:if><!--��ũҵ-->
		<xsl:if test="$Rsdnt_TpCd = '2'">1</xsl:if><!--ũҵ -->
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
		<xsl:if test="$GetIntv = '001'">0</xsl:if>     <!--����  -->
		<xsl:if test="$GetIntv = '002'">1</xsl:if>     <!-- ���� -->
		<xsl:if test="$GetIntv = '005'">12</xsl:if>  <!-- ���� -->
	</xsl:template>
	
	<!-- �������ȡ��ʽ -->
	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = '03'">Y</xsl:if>  <!-- 3-����  :����ƷĬ�ϻ���03-->
		<xsl:if test="$GETLiRenteDrawMode = '04'">M</xsl:if>  <!-- ���� -->
		<xsl:if test="$GETLiRenteDrawMode = '05'">D</xsl:if>  <!-- ���� -->
		<xsl:if test="$GETLiRenteDrawMode = ''"></xsl:if>
	</xsl:template>
	
	<!-- Ͷ�����˹��� -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = '156'">CHN</xsl:if><!--�й� -->
		<xsl:if test="$Nationality = '124'">CAN</xsl:if><!--���ô� -->
		<xsl:if test="$Nationality = '158'">TW</xsl:if><!--̨�� -->
		<xsl:if test="$Nationality = '344'">HK</xsl:if><!--���-->
		<xsl:if test="$Nationality = '392'">JAN</xsl:if><!--�ձ� -->
		<xsl:if test="$Nationality = '410'">KOR</xsl:if><!--����-->
		<xsl:if test="$Nationality = '446'">MO</xsl:if><!-- ���� -->
		<xsl:if test="$Nationality = '643'">RUS</xsl:if><!--����˹����-->
		<xsl:if test="$Nationality = '826'">ENG</xsl:if><!--Ӣ��-->
		<xsl:if test="$Nationality = '840'">USA</xsl:if><!--���� -->
		<xsl:if test="$Nationality = '036'">AUS</xsl:if><!--�Ĵ����� -->
		<xsl:if test="$Nationality = '999'">OTH</xsl:if><!--����-->
	</xsl:template>

	<!-- ������ȡ��ʽ -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="BonusGetMode"></xsl:param>
		<xsl:if test="$BonusGetMode = '0'">2</xsl:if><!-- ֱ�Ӹ��� -->
		<xsl:if test="$BonusGetMode = '1'">3</xsl:if><!-- �ֽ����� -->
		<xsl:if test="$BonusGetMode = '2'">1</xsl:if><!-- �ۻ���Ϣ -->
		<xsl:if test="$BonusGetMode = '3'">5</xsl:if><!-- ����� -->
		<xsl:if test="$BonusGetMode = ''"></xsl:if><!-- ����Ʒû�к�����ȡ��ʽ -->
	</xsl:template>
	
	<!-- �ɷѷ�ʽ��Ƶ�Σ� -->
	<xsl:template name="tran_payintv" >
			<xsl:param name="PayIntv" />
		<xsl:choose>
			<xsl:when test="$PayIntv = 9999">-1</xsl:when>	<!-- ������ -->
			<xsl:when test="$PayIntv = 0100">0</xsl:when>	<!-- ���� -->
			<xsl:when test="$PayIntv = 0204">1</xsl:when>	<!-- �½�-->
			<xsl:when test="$PayIntv = 0201">3</xsl:when>	<!-- ����-->
			<xsl:when test="$PayIntv = 0202">6</xsl:when>	<!-- �����-->
			<xsl:when test="$PayIntv = 0203">12</xsl:when>	<!-- ��� -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- �����ڼ䵥λ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 01">M</xsl:when><!-- ������-->
			<xsl:when test="$PbInsuYearFlag = 02">Y</xsl:when><!-- һ�� -->
			<xsl:when test="$PbInsuYearFlag = 03">Y</xsl:when><!-- ������ -->
			<xsl:when test="$PbInsuYearFlag = 04">A</xsl:when><!-- ������ -->
			<xsl:when test="$PbInsuYearFlag = 05">A</xsl:when><!-- ���� -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '1010'">0</xsl:when><!-- �������֤ -->
			<xsl:when test="$idtype = '1011'">0</xsl:when><!-- ��ʱ�������֤ -->
			<xsl:when test="$idtype = '1020'">2</xsl:when><!-- �������֤�� -->
			<xsl:when test="$idtype = '1030'">2</xsl:when><!-- �侯���֤�� -->
			<xsl:when test="$idtype = '1040'">4</xsl:when><!-- ���ڲ� -->
			<xsl:when test="$idtype = '1052'">1</xsl:when><!-- ������� -->
			<xsl:when test="$idtype = '1070'">F</xsl:when><!-- �۰ľ��������ڵ�ͨ��֤-->
			<xsl:when test="$idtype = '1080'">F</xsl:when><!-- ̨�����������½ͨ��֤-->
			<xsl:when test="$idtype = '1120'">1</xsl:when><!-- (���)����-->
			<xsl:when test="$idtype = '1999'">99</xsl:when><!-- ����֤�������ˣ�-->
			<xsl:when test="$idtype = '2010'">99</xsl:when><!-- Ӫҵִ��-->
			<xsl:when test="$idtype = '2020'">99</xsl:when><!-- ��֯��������֤-->
			<xsl:when test="$idtype = '2030'">99</xsl:when><!-- ������巨�˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2040'">99</xsl:when><!-- ��ҵ���˵Ǽ�֤��-->
			<xsl:when test="$idtype = '2090'">99</xsl:when><!-- ˰��Ǽ�֤-->
			<xsl:when test="$idtype = '2999'">99</xsl:when><!-- ����֤�����Թ���-->
			<xsl:otherwise>99</xsl:otherwise><!-- ���� -->
		</xsl:choose>
	</xsl:template>

	<!-- Ͷ���˺ͱ����˵� ��ϵ -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured">00</xsl:param>
		<xsl:choose>
		    <xsl:when test="$RelaToInsured = '0133999'">06</xsl:when><!-- ������ϵ-->
		    <xsl:when test="$RelaToInsured = '0133043'">00</xsl:when><!-- ����-->
			<xsl:when test="$RelaToInsured = '0132004'">09</xsl:when><!-- ��Ӷ��ϵ-->
			<xsl:when test="$RelaToInsured = '0133010'">02</xsl:when><!--��ż -->
			<xsl:when test="$RelaToInsured = '0133011'">03</xsl:when><!-- ��Ů: ����  -->
			<xsl:when test="$RelaToInsured = '0133012'">03</xsl:when><!-- ��Ů :Ů�� -->
			<xsl:when test="$RelaToInsured = '0133013'">04</xsl:when><!--����: ����-->
			<xsl:when test="$RelaToInsured = '0133014'">04</xsl:when><!--����: ��Ů-->
			<xsl:when test="$RelaToInsured = '0133015'">01</xsl:when><!--��ĸ:����-->
			<xsl:when test="$RelaToInsured = '0133016'">01</xsl:when><!--��ĸ:ĸ��-->
			<xsl:when test="$RelaToInsured = '0133017'">04</xsl:when><!--����: �游 -->
			<xsl:when test="$RelaToInsured = '0133018'">04</xsl:when><!--����: ��ĸ -->
			<xsl:when test="$RelaToInsured = '0133031'">04</xsl:when><!--����: ���游 -->
			<xsl:when test="$RelaToInsured = '0133032'">04</xsl:when><!--����: ����ĸ-->
			<xsl:when test="$RelaToInsured = '0133033'">04</xsl:when><!--����: ����-->
			<xsl:when test="$RelaToInsured = '0133034'">04</xsl:when><!--����: ����Ů-->
			<xsl:otherwise>99</xsl:otherwise><!-- û��ӳ��Ķ������� -->
		</xsl:choose>
	</xsl:template>
	
<!-- ������֪ -->
<xsl:template name="healFlag">
  <xsl:param name="HealFlag">N</xsl:param>
  <xsl:if test="$HealFlag = 0">N</xsl:if><!-- �޽�����֪ -->
  <xsl:if test="$HealFlag = 1">Y</xsl:if><!-- �н�����֪ -->
</xsl:template>
<!--ְҵ����-->
<xsl:template name="tran_JobCode">
  <xsl:param name="JobCode"></xsl:param>
  <xsl:choose>
    <xsl:when test="$JobCode = 'A0000'">1050104</xsl:when><!--������ҵ������-->
    <xsl:when test="$JobCode = 'B0000'">2021301</xsl:when><!--�����Ӳ��������Ա -->
    <xsl:when test="$JobCode = 'C0000'">3030201</xsl:when><!--����ҵ��ӪҵԱ -->
    <xsl:when test="$JobCode = 'D0000'">4030501</xsl:when><!--��������Ա -->
    <xsl:when test="$JobCode = 'E0000'">5010101</xsl:when><!--ũ�� -->
    <xsl:when test="$JobCode = 'F0000'">6051006</xsl:when><!--�칫С��е����װ�乤 -->
    <xsl:when test="$JobCode = 'Y0000'">8000002</xsl:when><!--������Ա -->
    <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>