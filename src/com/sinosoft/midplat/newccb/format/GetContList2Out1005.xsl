<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<TX>
			<!-- ����ͷ -->
			<TX_HEADER>
			     <!-- ����ͷ���� -->
				<SYS_HDR_LEN></SYS_HDR_LEN>
			     <!-- Э��汾�� -->
				<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
			     <!-- �����ܳ��� -->
				<SYS_TTL_LEN></SYS_TTL_LEN>
			     <!-- ���ͷ���ȫ�ڵ��� -->
				<SYS_SND_SEC_ID>510050</SYS_SND_SEC_ID>
			     <!-- ���𷢰�ȫ�ڵ���  ת���������-->
				<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
			     <!--��������-->
				<SYS_TX_TYPE>020000</SYS_TX_TYPE>
			     <!-- ȫ���¼����ٺ�  ת���������-->
				<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
			     <!-- �ӽ������  ת���������-->
				<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
			     <!-- Ӧ�ñ��ĸ�ʽ���� -->
				<SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
			     <!-- Ӧ�ñ��ĳ���  ת���������-->
				<SYS_MSG_LEN></SYS_MSG_LEN>
			     <!-- Ӧ�ñ����Ƿ���� -->
				<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
			     <!-- Ӧ�ñ��ļ��ܷ�ʽ -->
				<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
			     <!-- Ӧ�ñ���ѹ����ʽ -->
				<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
			     <!-- �Ӵ����ĳ��� -->
				<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
			     <!-- �������ʱ�� ת���������-->
				<SYS_RECV_TIME></SYS_RECV_TIME>    
			     <!-- ������Ӧʱ��  ת���������-->
				<SYS_RESP_TIME></SYS_RESP_TIME>
			     <!-- ����״̬����  -->
				<SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
				<xsl:if test = "/TranData/Head/Flag='0'">
			     <!-- ����״̬ -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- ������Ӧ�� -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/Head/Flag !='0'">
			     <!-- ����״̬ -->
				<SYS_TX_STATUS>01</SYS_TX_STATUS>    
			     <!-- ������Ӧ�� -->
				<SYS_RESP_CODE></SYS_RESP_CODE>    
				</xsl:if>
			     <!-- ������Ӧ��������  ת���������-->
				<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
			     <!-- ������Ӧ���� -->
				<SYS_RESP_DESC></SYS_RESP_DESC>    
			</TX_HEADER>
	
		<!-- ������ -->
			<TX_BODY>
	      			<COMMON>
	         			<FILE_LIST_PACK>
	         				<!-- �ļ����� -->
				            <FILE_NUM></FILE_NUM>
				            <!-- �ļ�����ʽ -->
				            <FILE_MODE></FILE_MODE>
				        	<!-- �ļ��ڵ� -->
				            <FILE_NODE></FILE_NODE>
				            <!-- �������ļ��� -->
				            <FILE_NAME_PACK></FILE_NAME_PACK>
				            <!-- ����ļ���ȡ·�� -->
				            <FILE_PATH_PACK></FILE_PATH_PACK>
				            <!-- �ļ���Ϣ -->
				            <FILE_INFO>
				            <!-- �ļ���Ϣ -->
				               <FILE_NAME></FILE_NAME>
				            <!-- �ļ�·�� -->
				               <FILE_PATH></FILE_PATH>
				            </FILE_INFO>
	         			</FILE_LIST_PACK>
	      			</COMMON>
	      			<ENTITY>
			        	<APP_ENTITY>
			        		<!-- #��������� -->
			        		<BtchBag_Num></BtchBag_Num>
			        		<!-- #��ǰ����ϸ�ܱ��� -->
			        		<Cur_Btch_Dtl_TDnum><xsl:value-of select="count(/TranData/Body/Detail)" /></Cur_Btch_Dtl_TDnum>
			        		<Insu_List>
			        			<xsl:for-each select="/TranData/Body/Detail">
			        			<Insu_Detil>
			        			
					        		<!-- ���չ�˾���  -->
									<Ins_Co_ID><xsl:value-of select="ComCode"/></Ins_Co_ID>
									<!-- ���չ�˾����  -->
									<Ins_Co_Nm><xsl:value-of select="ComName"/></Ins_Co_Nm>
								    <!-- ���д����־  -->
								    <CCB_Agnc_Ind>1</CCB_Agnc_Ind>
								    <!-- �������ײͱ�� -->
						    		<AgIns_Pkg_ID></AgIns_Pkg_ID>
								    <!-- �ײ�����  -->
								    <Pkg_Nm></Pkg_Nm>
									    <!-- Ͷ�������� -->
									    <Plchd_Nm><xsl:value-of select="Appnt/Name"/></Plchd_Nm>
									    <!-- Ͷ�����Ա���� -->
									    <Plchd_Gnd_Cd>
											<xsl:call-template name="tran_sex">
												<xsl:with-param name="Sex">
													<xsl:value-of select="Appnt/Sex" />
												</xsl:with-param>
											</xsl:call-template>
										</Plchd_Gnd_Cd>
									    <!-- Ͷ���˳������� -->
									    <Plchd_Brth_Dt><xsl:value-of select="Appnt/Birthday"/></Plchd_Brth_Dt>
									    <!-- Ͷ����֤�����ʹ��� -->
									    <Plchd_Crdt_TpCd>
									    	<xsl:call-template name="tran_idtype">
												<xsl:with-param name="idtype">
													<xsl:value-of select="Appnt/IDType" />
												</xsl:with-param>
											</xsl:call-template>
									    </Plchd_Crdt_TpCd>
									    <!-- Ͷ����֤���� -->
									    <Plchd_Crdt_No><xsl:value-of select="Appnt/IDNo"/></Plchd_Crdt_No>
									    <!-- Ͷ����֤����Ч���� -->
									    <Plchd_Crdt_EfDt></Plchd_Crdt_EfDt>
									    <!-- Ͷ����֤��ʧЧ���� -->
									    <Plchd_Crdt_ExpDt></Plchd_Crdt_ExpDt>
									    <!-- Ͷ���˹������� -->
									    <Plchd_Nat_Cd>
											<xsl:call-template name="tran_Nationality">
												<xsl:with-param name="Nationality">
													<xsl:value-of select="Appnt/Nationality"/>
												</xsl:with-param>
											</xsl:call-template>
										</Plchd_Nat_Cd>
									    <!-- Ͷ����ͨ�ŵ�ַ -->
									    <Plchd_Comm_Adr><xsl:value-of select="Appnt/Address"/></Plchd_Comm_Adr>
									    <!-- Ͷ������������ -->
									    <Plchd_ZipECD><xsl:value-of select="Appnt/ZipCode"/></Plchd_ZipECD>
									    <!-- Ͷ���˹̶��绰 -->
									    <Plchd_Fix_TelNo><xsl:value-of select="Appnt/Phone"/></Plchd_Fix_TelNo>
									    <!-- Ͷ�����ƶ��绰 -->
									    <Plchd_Move_TelNo><xsl:value-of select="Appnt/Mobile"/></Plchd_Move_TelNo>
									    <!-- Ͷ���˵����ʼ���ַ -->
									    <Plchd_Email_Adr><xsl:value-of select="Appnt/Email"/></Plchd_Email_Adr>
									    <!-- Ͷ����ְҵ���� -->
									    <Plchd_Ocp_Cd><xsl:value-of select="Appnt/JobCode"/></Plchd_Ocp_Cd>
									    <!-- Ͷ���������� -->
									    <Plchd_Yr_IncmAm></Plchd_Yr_IncmAm>
									    <!-- Ͷ���˼�ͥ������ -->
									    <Fam_Yr_IncmAm></Fam_Yr_IncmAm>
									    <!-- Ͷ���˾������ʹ��� -->
									    <Rsdnt_TpCd></Rsdnt_TpCd>
							    		<!-- Ͷ�����뱻���˹�ϵ���� -->
									    <Plchd_And_Rcgn_ReTpCd>
											<xsl:call-template name="tran_relation">
												<xsl:with-param name="RelaToInsured">
													<xsl:value-of select="Appnt/RelaToInsured" />
												</xsl:with-param>
											</xsl:call-template>						    
									    </Plchd_And_Rcgn_ReTpCd>
		 
						    		<!-- ���������� -->
								    <Rcgn_Nm><xsl:value-of select="Insured/Name"/></Rcgn_Nm>
						    		<!-- ������ƴ��ȫ�� -->
								    <Rcgn_CPA_FullNm></Rcgn_CPA_FullNm>
						    		<!-- �������Ա� -->
								    <Rcgn_Gnd_Cd>
										<xsl:call-template name="tran_sex">
											<xsl:with-param name="Sex">
												<xsl:value-of select="Insured/Sex" />
											</xsl:with-param>
										</xsl:call-template>
									</Rcgn_Gnd_Cd>
						    		<!-- �����˳������� -->
								    <Rcgn_Brth_Dt><xsl:value-of select="Insured/BirthDay"/></Rcgn_Brth_Dt>
						    		<!-- ������֤�����ʹ��� -->
								    <Rcgn_Crdt_TpCd>
								    	<xsl:call-template name="tran_idtype">
											<xsl:with-param name="idtype">
												<xsl:value-of select="Insured/IDType" />
											</xsl:with-param>
										</xsl:call-template>
									</Rcgn_Crdt_TpCd>
						    		<!-- ������֤���� -->
								    <Rcgn_Crdt_No><xsl:value-of select="Insured/IDNo"/></Rcgn_Crdt_No>
						    		<!-- ������֤����Ч���� -->
								    <Rcgn_Crdt_EfDt></Rcgn_Crdt_EfDt>
						    		<!-- ������֤��ʧЧ���� -->
								    <Rcgn_Crdt_ExpDt></Rcgn_Crdt_ExpDt>
						    		<!-- �����˹������� -->
								    <Rcgn_Nat_Cd>
										<xsl:call-template name="tran_Nationality">
											<xsl:with-param name="Nationality">
												<xsl:value-of select="Insured/Nationality"/>
											</xsl:with-param>
										</xsl:call-template>						    
								    </Rcgn_Nat_Cd>
						    		<!-- ������ͨѶ��ַ -->
								    <Rcgn_Comm_Adr><xsl:value-of select="Insured/Address"/></Rcgn_Comm_Adr>
						    		<!-- �������������� -->
								    <Rcgn_ZipECD><xsl:value-of select="Insured/ZipCode"/></Rcgn_ZipECD>
						    		<!-- �����˹̶��绰 -->
								    <Rcgn_Fix_TelNo><xsl:value-of select="Insured/Phone"/></Rcgn_Fix_TelNo>
						    		<!-- �������ƶ��绰 -->
								    <Rcgn_Move_TelNo><xsl:value-of select="Insured/Mobile"/></Rcgn_Move_TelNo>
						    		<!-- �����˵����ʼ���ַ -->
								    <Rcgn_Email_Adr><xsl:value-of select="Insured/Email"/></Rcgn_Email_Adr>
						    		<!-- ������ְҵ���� -->
								    <Rcgn_Ocp_Cd><xsl:value-of select="Insured/JobCode"/></Rcgn_Ocp_Cd>
						    		<!-- ������ǰ��Ŀ�ĵظ��� -->
								    <Rcgn_LvFr_Pps_Lnd_Num>0</Rcgn_LvFr_Pps_Lnd_Num>
						    		<Pps_List>
						    		</Pps_List>
						    		
						    		<!-- �����˸��� -->
								    <Benf_Num><xsl:value-of select="count(Bnf)"/></Benf_Num>
								    <Benf_List>
								    	<!-- ��������Ϣѭ�� -->
								    	<xsl:for-each select="Bnf">
									    	<Benf_Detail>
									    		<!-- ���������������ʹ��� -->
											    <AgIns_Benf_TpCd><xsl:value-of select="Type"/></AgIns_Benf_TpCd>
									    		<!-- #��ű�� -->
											    <SN_ID></SN_ID>
									    		<!-- �������������ֵ -->
											    <AgIns_Bnft_Ord_Val><xsl:value-of select="Grade"/></AgIns_Bnft_Ord_Val>
									    		<!-- ���������� -->
											    <Benf_Nm><xsl:value-of select="Name"/></Benf_Nm>
									    		<!-- �������Ա���� -->
											    <Benf_Gnd_Cd>
													<xsl:call-template name="tran_sex">
														<xsl:with-param name="Sex">
															<xsl:value-of select="Sex" />
														</xsl:with-param>
													</xsl:call-template>
												</Benf_Gnd_Cd>
									    		<!-- �����˳������� -->
											    <Benf_Brth_Dt><xsl:value-of select="BirthDay"/></Benf_Brth_Dt>
									    		<!-- ������֤�����ʹ��� -->
											    <Benf_Crdt_TpCd>						    	
											    	<xsl:call-template name="tran_idtype">
														<xsl:with-param name="idtype">
															<xsl:value-of select="IDType" />
														</xsl:with-param>
													</xsl:call-template>
												</Benf_Crdt_TpCd>
									    		<!-- ������֤���� -->
											    <Benf_Crdt_No><xsl:value-of select="IDNo"/></Benf_Crdt_No>
									    		<!-- ������֤����Ч���� -->
											    <Benf_Crdt_EfDt></Benf_Crdt_EfDt>
									    		<!-- ������֤��ʧЧ���� -->
											    <Benf_Crdt_ExpDt></Benf_Crdt_ExpDt>
									    		<!-- �����˹������� -->
											    <Benf_Nat_Cd>
													<xsl:call-template name="tran_Nationality">
													<xsl:with-param name="Nationality">
															<xsl:value-of select="Nationality"/>
													</xsl:with-param>
													</xsl:call-template>								    
											    </Benf_Nat_Cd>
									    		<!-- �������뱻���˹�ϵ���� -->
											    <Benf_And_Rcgn_ReTpCd>
													<xsl:call-template name="tran_relation">
														<xsl:with-param name="RelaToInsured">
															<xsl:value-of select="RelaToInsured" />
														</xsl:with-param>
													</xsl:call-template>
												</Benf_And_Rcgn_ReTpCd>
									    		<!-- ������� -->
											    <Bnft_Pct><xsl:value-of select="Lot"/></Bnft_Pct>
									    		<!-- ������ͨѶ��ַ -->
											    <Benf_Comm_Adr><xsl:value-of select="Address"/></Benf_Comm_Adr>
									    	</Benf_Detail>
								    	</xsl:for-each>
								    </Benf_List>
						    		
						    		<!-- #ѭ����¼���� -->
									<Rvl_Rcrd_Num><xsl:value-of select="count(Risk)"/></Rvl_Rcrd_Num>				    		
						    		<Busi_List>
						    			<xsl:for-each select="Risk">
						    				<Busi_Detail>
									    		<!-- ���ֱ�� -->
											    <Cvr_ID><xsl:value-of select="RiskCode"/></Cvr_ID>
									    		<!-- �������� -->
											    <Cvr_Nm><xsl:value-of select="RiskName"/></Cvr_Nm>
									    		<!-- �����ձ�ʶ -->
											    <MainAndAdlIns_Ind>1</MainAndAdlIns_Ind>
									    		<!-- Ͷ������ -->
											    <Ins_Cps><xsl:value-of select="Mult"/></Ins_Cps>
									    		<!-- ���ѽ�� -->
											    <InsPrem_Amt><xsl:value-of select="Prem"/></InsPrem_Amt>
									    		<!-- Ͷ������ -->
											    <Ins_Cvr><xsl:value-of select="Amnt"/></Ins_Cvr>
									    		<!-- Ͷ��������Ϣ -->
											    <Ins_Scm_Inf></Ins_Scm_Inf>
									    		<!-- ��ѡ������ʱ��ս�� -->
											    <Opt_Part_DieIns_Amt></Opt_Part_DieIns_Amt>
									    		<!-- �״ζ���׷�ӱ��� -->
											    <FTm_Extr_Adl_InsPrem><xsl:value-of select="Prem"/></FTm_Extr_Adl_InsPrem>
									    		<!-- Ͷ�ʷ�ʽ���� -->
											    <Ivs_MtdCd></Ivs_MtdCd>
									    		<!-- ������ȡ��ʽ��ʽ���� -->
											    <InsPolcyRcvMtdCd></InsPolcyRcvMtdCd>
									    		<!-- �����ʺ� -->
											    <CCB_AccNo></CCB_AccNo>
									    		<!-- �������������� -->
											    <Ins_Yr_Prd_CgyCd><xsl:value-of select="InsuYearFlag"/></Ins_Yr_Prd_CgyCd>
									    		<!-- �������� -->
											    <Ins_Ddln><xsl:value-of select="InsuYear"/></Ins_Ddln>
									    		<!-- �������ڴ��� -->
											    <Ins_Cyc_Cd></Ins_Cyc_Cd>
									    		<!-- ���ѽ��ɷ�ʽ���� -->
											    <Ins_PyF_MtdCd>
											    	<xsl:call-template name="tran_Contpayintv">
														<xsl:with-param name="payintv">
															<xsl:value-of select="PayIntv" />
														</xsl:with-param>
													</xsl:call-template>
											    </Ins_PyF_MtdCd>
									    		<!-- ���ѽɷ����� -->
											    <InsPrem_PyF_Prd_Num><xsl:value-of select="PayEndYear"/></InsPrem_PyF_Prd_Num>
									    		<!-- ���ѽ������ڴ��� -->
											    <InsPrem_PyF_Cyc_Cd></InsPrem_PyF_Cyc_Cd>
									    		<!-- �ر�Լ����Ϣ -->
											    <Spcl_Apnt_Inf><xsl:value-of select="SpecContent"/></Spcl_Apnt_Inf>
									    		<!-- �����ȡ������ -->
											    <Anuty_Drw_CgyCd></Anuty_Drw_CgyCd>
									    		<!-- �����ȡ���� -->
											    <Anuty_Drw_Prd_Num><xsl:value-of select="GetYear"/></Anuty_Drw_Prd_Num>
									    		<!-- �����ȡ���ڴ��� -->
											    <Anuty_Drw_Cyc_Cd></Anuty_Drw_Cyc_Cd>
									    		<!-- �����ʽ���� -->
											    <Anuty_Pcsg_MtdCd></Anuty_Pcsg_MtdCd>
									    		<!-- ���ڱ��ս���ȡ��ʽ������ -->
											    <ExpPrmmRcvModCgyCd><xsl:value-of select="GetIntv"/></ExpPrmmRcvModCgyCd>
									    		<!-- �������ȡ���ڴ��� -->
											    <SvBnf_Drw_Cyc_Cd>
								    	               <xsl:call-template name="tran_LiRenteDrawMode">
															<xsl:with-param name="GETLiRenteDrawMode">
																 <xsl:value-of select="GetYearFlag" />
															</xsl:with-param>
													   </xsl:call-template>
												</SvBnf_Drw_Cyc_Cd>
									    		<!-- ������ȡ��ʽ -->
											    <XtraDvdn_Pcsg_MtdCd><xsl:value-of select="BonusGetMode"/></XtraDvdn_Pcsg_MtdCd>
									    		<!-- Լ�����ѵ潻��־ -->
											    <ApntInsPremPyAdvnInd><xsl:value-of select="AutoPayFlag"/></ApntInsPremPyAdvnInd>
									    		<!-- �Զ�������־ -->
											    <Auto_RnwCv_Ind></Auto_RnwCv_Ind>
									    		<!-- ���鴦��ʽ���� -->
											    <Dspt_Pcsg_MtdCd></Dspt_Pcsg_MtdCd>
									    		<!-- �����ٲû������� -->
											    <Dspt_Arbtr_Inst_Nm></Dspt_Arbtr_Inst_Nm>
									    		<!-- ��֪�����־ -->
											    <Ntf_Itm_Ind><xsl:value-of select="HealthNotice"/></Ntf_Itm_Ind>
									    		<!-- ���������־ -->
											    <XtraDvdn_Alct_Ind></XtraDvdn_Alct_Ind>
									    		<!-- ������־ -->
											    <RdAmtPyCls_Ind></RdAmtPyCls_Ind>
									    		<!-- ���ս��ݼ���ʽ���� -->
											    <Ins_Amt_Dgrs_MtdCd></Ins_Amt_Dgrs_MtdCd>
									    		<!-- ������Ч���� -->
											    <InsPolcy_EfDt><xsl:value-of select="CValiDate"/></InsPolcy_EfDt>
									    		<!-- ��������Ч���� -->
											    <InsPolcy_Intnd_EfDt></InsPolcy_Intnd_EfDt>
									    		<!-- ����ʧЧ���� -->
											    <InsPolcy_ExpDt><xsl:value-of select="InsuEndDate"/></InsPolcy_ExpDt>
									    		<!-- ������ϵ�� -->
											    <Emgr_CtcPsn></Emgr_CtcPsn>
									    		<!-- ������ϵ���뱻���˹�ϵ���ʹ��� -->
											    <EmgrCtcPsnAndRcReTpCd>
												</EmgrCtcPsnAndRcReTpCd>
									    		<!-- ������ϵ�绰 -->
											    <Emgr_Ctc_Tel></Emgr_Ctc_Tel>
									    		<!-- ���д����ͬ��� -->
											    <Bnk_Loan_Ctr_ID></Bnk_Loan_Ctr_ID>
									    		<!-- �����ͬ���ʧЧ���� -->
											    <Ln_Ctr_ExpDt></Ln_Ctr_ExpDt>
									    		<!-- δ�������� -->
											    <Upd_Loan_Amt></Upd_Loan_Amt>
									    		<!-- ��������ƾ֤���� -->
											    <PrimBlInsPolcyVchr_No></PrimBlInsPolcyVchr_No>
									    		<!-- Ͷ�����˻����� -->
											    <IvLkIns_Acc_Num>0</IvLkIns_Acc_Num>
											    <PayAcctCode_List>
										    		<!-- #�����ֶ�һ -->
													<Rsrv_Fld_1></Rsrv_Fld_1>
													<!-- #�����ֶζ� -->
													<Rsrv_Fld_2></Rsrv_Fld_2>
													<!-- #�����ֶ��� -->
													<Rsrv_Fld_3></Rsrv_Fld_3>
													<!-- #�����ֶ��� -->
													<Rsrv_Fld_4></Rsrv_Fld_4>
													<!-- #�����ֶ��� -->
													<Rsrv_Fld_5></Rsrv_Fld_5>
													<!-- #�����ֶ��� -->
													<Rsrv_Fld_6></Rsrv_Fld_6>
													<!-- #�����ֶ��� -->
													<Rsrv_Fld_7></Rsrv_Fld_7>
													<!-- #�����ֶΰ� -->
													<Rsrv_Fld_8></Rsrv_Fld_8>
													<!-- #�����ֶξ� -->
													<Rsrv_Fld_9></Rsrv_Fld_9>
													<!-- #�����ֶ�ʮ -->
													<Rsrv_Fld_10></Rsrv_Fld_10>
											    </PayAcctCode_List>
						    				</Busi_Detail>
						    			</xsl:for-each>
						    		</Busi_List>
									<!-- Ͷ������ -->
									<Ins_BillNo><xsl:value-of select="ProposalPrtNo"/></Ins_BillNo>
									<!-- �������� -->
									<InsPolcy_No><xsl:value-of select="ContNo"/></InsPolcy_No>
									<!-- �������ʺ� -->
									<Rcgn_AccNo><xsl:value-of select="RcgnAccNo"/></Rcgn_AccNo>
									<!-- �������ʺ� -->
									<Benf_AccNo><xsl:value-of select="BenfAccNo"/></Benf_AccNo>
									<!-- ���ڽɷ�֧����ʽ���� -->
									<Rnew_PyF_PyMd_Cd></Rnew_PyF_PyMd_Cd>
									<!-- �����պ�Լ״̬���� -->
									<AcIsAR_StCd>
									<!-- 
														Available: 0-��Ч 1-ʧЧ ������״̬��
														Terminate: 0-δ��ֹ 1-��ֹ ������״̬��
														Lost: 0-δ��ʧ 1-��ʧ ������״̬��
														PayPrem: 0-δ�Ե� 1-�����Զ��潻 ������״̬��
														Loan: 0-δ���� 1-���� ������״̬��
														BankLoan: 0-δ��Ѻ���д��� 1-��Ѻ���д��� ������״̬��
														RPU��0-δ������� 1-������� ������״̬��
														��ʱ�����Լ�� ״̬����
																				0-��Ч
																				1-ʧЧ
																				2-��Ч
									 -->
									</AcIsAR_StCd>
									<!-- �����տͻ��������ʹ��� -->
									<AgIns_Cst_Line_TpCd></AgIns_Cst_Line_TpCd>
									<!-- �����������ʹ��� -->
									<InsPolcy_Medm_TpCd></InsPolcy_Medm_TpCd>
									<!-- ���л������� -->
									<CCBIns_ID></CCBIns_ID>
									<!-- һ�����к� -->
									<Lv1_Br_No></Lv1_Br_No>
									<!-- ���㱣�ռ�ְ����ҵ�����֤���� -->
									<BOInsPrAgnBsnLcns_ECD></BOInsPrAgnBsnLcns_ECD>
									<!-- ����ֹܴ�����ҵ�����˱�� -->
									<BOIChOfAgInsBsnPnp_ID></BOIChOfAgInsBsnPnp_ID>
									<!-- ����ֹܴ�����ҵ���������� -->
									<BOIChOfAgInsBsnPnp_Nm></BOIChOfAgInsBsnPnp_Nm>
									<!-- Ͷ���˲������� -->
									<Ins_Mnplt_Psn_ID></Ins_Mnplt_Psn_ID>
									<!-- ����������Ա���� -->
									<BO_Sale_Stff_ID><xsl:value-of select="SaleStaff"/></BO_Sale_Stff_ID>
									<!-- ����������Ա���� -->
									<BO_Sale_Stff_Nm><xsl:value-of select="SaleName"/></BO_Sale_Stff_Nm>
									<!-- ������Ա�����մ�ҵ��Ա�ʸ�֤����-->
									<Sale_Stff_AICSQCtf_ID><xsl:value-of select="SaleCertNo"/></Sale_Stff_AICSQCtf_ID>
									<!-- �Ƽ��ͻ������� -->
									<RcmCst_Mgr_ID></RcmCst_Mgr_ID>
									<!-- �Ƽ��ͻ��������� -->
									<RcmCst_Mgr_Nm></RcmCst_Mgr_Nm>
									<!-- ����ר�������� -->
									<Ins_Prj_CgyCd></Ins_Prj_CgyCd>
									<!-- ���ѳ������ʹ��� -->
									<PydFeeOutBill_CgyCd></PydFeeOutBill_CgyCd>
									<!-- ����ʵ�����۵������� -->
									<InsPolcyActSaleRgonID></InsPolcyActSaleRgonID>
									<!-- ���տͻ������ṩ�������� -->
									<Ins_CsLs_Prvd_Rgon_ID></Ins_CsLs_Prvd_Rgon_ID>
									<!-- ������ȡ��ʽ���� -->
									<InsPolcy_Rcv_MtdCd></InsPolcy_Rcv_MtdCd>
									<!-- #�����ֶ�ʮһ -->
									<Rsrv_Fld_10_1></Rsrv_Fld_10_1>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_12></Rsrv_Fld_12>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_13></Rsrv_Fld_13>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_14></Rsrv_Fld_14>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_15></Rsrv_Fld_15>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_16></Rsrv_Fld_16>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_17></Rsrv_Fld_17>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_18></Rsrv_Fld_18>
									<!-- #�����ֶ�ʮ�� -->
									<Rsrv_Fld_19></Rsrv_Fld_19>
									<!-- #�����ֶζ�ʮ -->
									<Rsrv_Fld_20></Rsrv_Fld_20>
									<!-- ���Ա�¼ -->
									<Pstcrpt_Rmrk></Pstcrpt_Rmrk>
			        			
			        			</Insu_Detil>
			        			</xsl:for-each>
			        		</Insu_List>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
	      	
		</TX>
	</xsl:template>


	<!-- �Ա� -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 0">1</xsl:if><!-- �� -->
		<xsl:if test="$Sex = 1">2</xsl:if><!-- Ů -->
	</xsl:template>

		<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:if test="$GETLiRenteDrawMode = Y">0</xsl:if>          <!--  0-һ�θ���  -->
		<xsl:if test="$GETLiRenteDrawMode = Y">1</xsl:if>          <!--  1-�¸���  -->
		<xsl:if test="$GETLiRenteDrawMode = Y">3</xsl:if>          <!--  3-������  -->
		<xsl:if test="$GETLiRenteDrawMode = Y">6</xsl:if>          <!--  6-�������  -->
		<xsl:if test="$GETLiRenteDrawMode = Y">12</xsl:if>         <!--  12-�����  -->
	</xsl:template>

	<!-- �������� -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 'CHN'">0156</xsl:if>
		<xsl:if test="$Nationality = ''">0344</xsl:if><!--��� -->
		<xsl:if test="$Nationality = ''">0158</xsl:if><!--̨�� -->
		<xsl:if test="$Nationality = ''">0446</xsl:if><!--���� -->
		<xsl:if test="$Nationality = 'JAN'">0392</xsl:if><!--�ձ� -->
		<xsl:if test="$Nationality = 'USA'">0840</xsl:if><!--����-->
		<xsl:if test="$Nationality = ''">0643</xsl:if><!--����˹ -->
		<xsl:if test="$Nationality = 'ENG'">0826</xsl:if><!--Ӣ�� -->
		<xsl:if test="$Nationality = 'FRA'">0250</xsl:if><!--����-->
		<xsl:if test="$Nationality = 'DEU'">0276</xsl:if><!--�¹� -->
		<xsl:if test="$Nationality = 'KOR'">0410</xsl:if><!--���� -->
		<xsl:if test="$Nationality = 'SG'">0702</xsl:if><!--�¼��� -->
		<xsl:if test="$Nationality = 'INA'">0360</xsl:if><!--ӡ��������-->
		<xsl:if test="$Nationality = 'IND'">0356</xsl:if><!--ӡ��-->
		<xsl:if test="$Nationality = 'ITA'">0380</xsl:if><!--����� -->
		<xsl:if test="$Nationality = 'MY'">0458</xsl:if><!--�������� -->
		<xsl:if test="$Nationality = 'THA'">0764</xsl:if><!--̩��-->
		<xsl:if test="$Nationality = ''">0999</xsl:if><!--����-->
	</xsl:template>

	<!-- ֤������ -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '0'">1010</xsl:when><!-- �������֤���� -->
			<xsl:when test="$idtype = '2'">1022</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = 'D'">1032</xsl:when><!-- ����֤ -->
			<xsl:when test="$idtype = 'A'">1021</xsl:when><!-- ��ž�ʿ��֤ -->
			<xsl:when test="$idtype = '4'">1040</xsl:when><!-- ���ڲ� -->
			<xsl:when test="$idtype = 'B'">1080'</xsl:when><!-- (�۰�)����֤��ͨ��֤ -->
			<xsl:when test="$idtype = '1'">1050</xsl:when><!-- ����-->
			<xsl:when test="$idtype = '5'">1060</xsl:when><!-- ѧ��֤-->
			<xsl:when test="$idtype = '6'">2999</xsl:when><!-- �Թ�����֤��-->
			<xsl:when test="$idtype = '3'">1100</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = 'C'">1011</xsl:when><!-- ��ʱ�������֤ -->
			<xsl:when test="$idtype = 'E'">1160</xsl:when><!-- ̨��������֤ ̨��֤ -->
			<xsl:otherwise>
					<xsl:value-of select="1999" /><!-- ���� -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ��ϵ -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured"></xsl:param>
		<xsl:if test="$RelaToInsured = 00">0133043</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 02">0133010</xsl:if><!--�ɷ� -->
		<xsl:if test="$RelaToInsured = 02">0133010</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 01">0133015</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 01">0133016</xsl:if><!--ĸ�� -->
		<xsl:if test="$RelaToInsured = 03">0133011</xsl:if><!--���� -->
		<xsl:if test="$RelaToInsured = 03">0133012</xsl:if><!--Ů��-->
		<xsl:if test="$RelaToInsured = 06">0133021</xsl:if><!--�������� -->
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
	
<!-- �����ִ��� -->
	<xsl:template name="tran_MainRiskCode">
		<xsl:param name="MainRiskCode">0</xsl:param>
		<xsl:if test="$MainRiskCode = 231201">0001</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
		<xsl:if test="$MainRiskCode = 231202">0002</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
		<xsl:if test="$MainRiskCode = 231203">0003</xsl:if><!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
		<xsl:if test="$MainRiskCode = 221201">0004</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$MainRiskCode = 231204">0005</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$MainRiskCode = 221301">0006</xsl:if><!-- �к���δ�������-->
	</xsl:template>	
	
</xsl:stylesheet>	


