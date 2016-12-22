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
		    <!-- Ͷ����ӡˢ�� -->
			<ProposalPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_Bl_Prt_No)" /></ProposalPrtNo>
  			<!--������ͬӡˢ�� (��֤) Ŀǰ�����½ӿڲ������ڴ�ӡ������20140903-->
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
				<!-- Ͷ����֤����Ч����
				<xsl:choose>
				<xsl:when test="Plchd_Crdt_EfDt=20991231" ><PlchdCrdtEfDt>99990101</PlchdCrdtEfDt></xsl:when>
				<xsl:otherwise><PlchdCrdtEfDt><xsl:value-of select="Plchd_Crdt_EfDt"/></PlchdCrdtEfDt></xsl:otherwise>
				</xsl:choose>-->
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
				<!-- Ͷ���˵�ַ -->
				<Address><xsl:value-of select="Plchd_Dtl_Adr_Cntnt" /></Address>
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
				<RelaToInsured2><xsl:value-of select="Plchd_And_Rcgn_ReTpCd" /></RelaToInsured2>				
				<!-- Ͷ���˾������� -->
				<DenType><xsl:value-of select="Rsdnt_TpCd" /></DenType>
				
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
 				<!-- �����˵�ַ-->
				<Address>
					<xsl:value-of select="Rcgn_Dtl_Adr_Cntnt" />
				</Address>
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
				<JobCode>3010101</JobCode>
				<!-- ������������ -->
				<RcgnYrIncmAm><xsl:value-of select="Rcgn_Yr_IncmAm" /></RcgnYrIncmAm>
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
			<!-- 		<xsl:for-each select="Benf_Detail">    �����Լ����Ĳ��Ա���û��Benf_List/Benf_Detailֻ�� Benf_Detail -->	
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
							<xsl:call-template name="tran_Bnfrelation">
								<xsl:with-param name="BnfRelaToInsured">
									<xsl:value-of
										select="Benf_And_Rcgn_ReTpCd" />
								</xsl:with-param>
							</xsl:call-template>
						</RelaToInsured>
						<RelaToInsured3>	<xsl:value-of select="Benf_And_Rcgn_ReTpCd" /></RelaToInsured3>	
						<!-- ������������� -->
						<Lot>
							<xsl:value-of
								select="Bnft_Pct" />
						</Lot>
						<!-- ��������ϸ��ַ���� -->
						<AddressContent><xsl:value-of select="Benf_Dtl_Adr_Cntnt" /></AddressContent>
						<!-- ������ͨѶ��ַ -->
						<BenfCommAdr><xsl:value-of select="Benf_Comm_Adr" /></BenfCommAdr>
						<BeneficType>N</BeneficType>
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
				<RiskCode><xsl:value-of select="Cvr_ID" /></RiskCode>
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
				<Mult>
					<xsl:value-of select="Ins_Cps" />
				</Mult>
				<PayMode>B</PayMode>
				<!-- ���ѽ��ɷ�ʽ���� -->
				<xsl:if test="Cvr_ID='011A0100'">
					<PayIntv>0</PayIntv>
					<PayEndYear>1000</PayEndYear>
					<PayEndYearFlag>Y</PayEndYearFlag>
				</xsl:if>
				<xsl:if test="Cvr_ID !='011A0100'">
					<PayIntv>
						<xsl:call-template name="tran_Contpayintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="InsPrem_PyF_MtdCd" />
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- �ɷ����ںͽɷ����� -->
					 <xsl:if test="InsPrem_PyF_MtdCd = 02"><!-- ����1000Y������ -->				    
						<PayEndYear>1000</PayEndYear>
						<PayEndYearFlag>Y</PayEndYearFlag>
					</xsl:if>
					<xsl:if test="InsPrem_PyF_MtdCd != 02">	<!-- ����û�н�����������һ˵ -->
						<PayEndYear><xsl:value-of select="InsPrem_PyF_Prd_Num" /></PayEndYear>			
					    <PayEndYearFlag>Y</PayEndYearFlag>			    
					</xsl:if>
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
    	        </GetYearFlag> <!-- ��ȡ�������ڱ�־  �����Ҫת��-->
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
                <GetYear><xsl:value-of select="Anuty_Drw_Prd_Num" /></GetYear><!-- ��ȡ���� ������֮ǰ��GetTerms-->				
				
				<!--�����ڼ�-->	 
				<xsl:choose>		
					<xsl:when test="Ins_Yr_Prd_CgyCd='04'">	<!-- ��ĳ�ض����� -->
						<!-- �������������־ -->
						<InsuYearFlag>A</InsuYearFlag>
						<!-- ������������ -->
						<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear> 
					</xsl:when> 
					<xsl:when test="Ins_Yr_Prd_CgyCd='05'">	<!-- ������ -->
						<!-- �������������־ -->
						<InsuYearFlag>A</InsuYearFlag>
						<!-- ������������ -->
						<InsuYear><xsl:value-of select="Ins_Ddln" /></InsuYear> 
					</xsl:when> 
					<xsl:otherwise>	
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
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> �½� -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> ���� -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> ���꽻 -->
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
			<xsl:when test="$RelaToInsured = 0133015">01</xsl:when><!--����  03 ��Ů   -->
			<xsl:when test="$RelaToInsured = 0133016">01</xsl:when><!--ĸ�� 03 ��Ů -->
			<xsl:when test="$RelaToInsured = 0133011">03</xsl:when><!--���� 01 ��ĸ -->
			<xsl:when test="$RelaToInsured = 0133012">03</xsl:when><!--Ů�� 01 ��ĸ-->
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
<!--ְҵ����-->
<xsl:template name="tran_JobCode">
  <xsl:param name="JobCode"></xsl:param>
  <xsl:choose>
    <xsl:when test="$JobCode = 'A0000'">??</xsl:when><!--����-->
    <xsl:when test="$JobCode = 'B0000'">??</xsl:when><!--���� -->
    <xsl:when test="$JobCode = 'C0000'">???</xsl:when><!--�����ն� -->
    <xsl:when test="$JobCode = 'D0000'">??</xsl:when><!--�����ն� -->
    <xsl:when test="$JobCode = 'E0000'">??</xsl:when><!--�����ն� -->
    <xsl:when test="$JobCode = 'F0000'">??</xsl:when><!--�����ն� -->
    <xsl:when test="$JobCode = 'Y0000'">??</xsl:when><!--�����ն� -->
    <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>