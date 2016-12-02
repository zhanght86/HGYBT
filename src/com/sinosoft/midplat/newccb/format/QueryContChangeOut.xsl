<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
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
				            <FILE_NUM>0</FILE_NUM>
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
			        	
							<!-- ���ڽɷѱ���  -->
							<Rnew_PyF_Dnum><xsl:value-of select="count(/TranData/Body/Detail1)"/></Rnew_PyF_Dnum>
							<!-- �ɷ���Ϣѭ�� -->
							<InsPyF_List>
								<xsl:for-each select="/TranData/Body/Detail1">
									<InsPyF_Detail>
										<!-- ���սɷ����� -->
							    		<Ins_PyF_Dt><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(PayDate)"/></Ins_PyF_Dt>
										<!-- ���սɷѽ�� -->
							    		<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Ins_PyF_Amt>
									</InsPyF_Detail>
								</xsl:for-each>
							</InsPyF_List>
							
						    <!-- �ֺ���� -->
				    		<Dvdn_Cnt><xsl:value-of select="count(/TranData/Body/Detail2)"/></Dvdn_Cnt>
				    		<!-- �ֺ���Ϣѭ�� -->
				    		<XtraDvdn_List>
				    			<xsl:for-each select="/TranData/Body/Detail2">
				    				<!-- ����ʵ�ʷ������� -->
						    		<XtraDvdn_Act_Dstr_Dt><xsl:value-of select="BonusGetDate"/></XtraDvdn_Act_Dstr_Dt>
				    				<!-- ��������ʽ���� -->
						    		<XtraDvdn_Pcsg_MtdCd><xsl:value-of select="BonusGetMode"/>
						    			<xsl:call-template name="tran_BonusGetMode">
											<xsl:with-param name="tBonusGetMode">
												<xsl:value-of select="BonusGetMode" />
											</xsl:with-param>
										</xsl:call-template>
						    		</XtraDvdn_Pcsg_MtdCd>
				    				<!-- ���ں������ -->
						    		<CrnPrd_XtDvdAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(CurrentAmt)"/></CrnPrd_XtDvdAmt>
				    				<!-- ���˺������ -->
						    		<ATEndOBns_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndAmt)"/></ATEndOBns_Amt>
				    				<!-- �ۼƺ������ -->
						    		<Acm_XtDvdAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(BonusAmt)"/></Acm_XtDvdAmt>
				    			</xsl:for-each>
				    		</XtraDvdn_List>
				    		
				    		<!-- ��ȫҵ����� -->
						    <PsvBsn_Dnum><xsl:value-of select="count(/TranData/Body/Detail3)"/></PsvBsn_Dnum>
							<!-- ��ȫ��Ϣѭ�� -->			        	
			        		<Prsrvt_List>
			        			<xsl:for-each select="/TranData/Body/Detail3">
			        				<Prsrvt_Detail>
							    		<!-- ��ȫ���� -->
									    <Prsrvt_Dt><xsl:value-of select="EdorDate"/></Prsrvt_Dt>
							    		<!-- ��ȫ�䶯�������� -->
									    <Prsrvt_Chg_Itm_Dsc><xsl:value-of select="Description"/></Prsrvt_Chg_Itm_Dsc>
			        				</Prsrvt_Detail>
			        			</xsl:for-each>
			        		</Prsrvt_List>
			        		
				    		<!-- ������Ч��Ч���� -->
						    <InsPolcyEff_Rinst_Cnt><xsl:value-of select="count(/TranData/Body/Detail4)"/></InsPolcyEff_Rinst_Cnt>
							<!-- ʧЧ��Ч��Ϣѭ�� -->			        	
			        		<InsPolcyDt_List>
			        			<xsl:for-each select="/TranData/Body/Detail4">
			        				<InsPolcyDt_Detail>
							    		<!-- ����ʧЧ���� -->
									    <InsPolcy_ExpDt><xsl:value-of select="InsuEndDate"/></InsPolcy_ExpDt>
							    		<!-- ������Ч���� -->
									    <InsPolcy_Rinst_Dt><xsl:value-of select="InsuRinstDate"/></InsPolcy_Rinst_Dt>
							    		<!-- �����պ�Լ״̬���� -->
							    		<!-- ����ֻ�����б���״̬ 
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
									    <AcIsAR_StCd>
										    <xsl:call-template name="tran_Status">
												<xsl:with-param name="Status">
													<xsl:value-of select="PolicyStatus" />
												</xsl:with-param>
											</xsl:call-template>
									    </AcIsAR_StCd>
			        				</InsPolcyDt_Detail>
			        			</xsl:for-each>
			        		</InsPolcyDt_List>
			        	
				    		<!-- ��������䶯���� -->
						    <InsPolcy_Cvr_Chg_Cnt><xsl:value-of select="count(/TranData/Body/Detail5)"/></InsPolcy_Cvr_Chg_Cnt>
							<!-- ����/����/�˱���Ϣѭ�� -->			        	
			        		<InsPolcyAply_List>
			        			<xsl:for-each select="/TranData/Body/Detail5">
			        				<InsPolcyAply_Detail>
							    		<!-- ��������ҵ�������� -->
									    <InsPolcyAplyBsnCgyCd>
									    	<xsl:call-template name="tran_Type">
												<xsl:with-param name="Type">
													<xsl:value-of select="EdorType" />
												</xsl:with-param>
											</xsl:call-template>
									    </InsPolcyAplyBsnCgyCd>
							    		<!-- ҵ���������� -->
									    <Bapl_Dt><xsl:value-of select="ApplyDate"/></Bapl_Dt>
			        				</InsPolcyAply_Detail>
			        			</xsl:for-each>
			        		</InsPolcyAply_List>
			        	
				    		<!-- �����¼�� -->
						    <SetlOfClms_Rcrd_Num><xsl:value-of select="count(/TranData/Body/Detail6)"/></SetlOfClms_Rcrd_Num>
							<!-- ������Ϣѭ�� -->			        	
			        		<SetlOfClms_List>
			        			<xsl:for-each select="/TranData/Body/Detail6">
			        				<SetlOfClms_Detail>
							    		<!-- �����¼���� -->
									    <SetlOfClms_Rcrd_Dt><xsl:value-of select="RcrdDate"/></SetlOfClms_Rcrd_Dt>
							    		<!-- ������ -->
									    <SetlOfClms_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></SetlOfClms_Amt>
			        				</SetlOfClms_Detail>
			        			</xsl:for-each>
			        		</SetlOfClms_List>
			        	
			    		<!-- ��ֵ���� -->
					    <NetVal_Dt><xsl:value-of select="/TranData/Body/NetValDate"/></NetVal_Dt>
						<!-- �������ֽ�ֵ -->
					    <AgIns_Cash_NetVal><xsl:value-of select="/TranData/Body/AgInsCashNetVal"/></AgIns_Cash_NetVal>
			        	
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>

	<!-- ������ȡ��ʽ -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="tBonusGetMode"></xsl:param>
		<xsl:if test="$tBonusGetMode = 2">0</xsl:if><!-- ֱ�Ӹ��� -->
		<xsl:if test="$tBonusGetMode = 3">1</xsl:if><!-- �ֽ����� -->
		<xsl:if test="$tBonusGetMode = 1">2</xsl:if><!-- �ۻ���Ϣ -->
		<xsl:if test="$tBonusGetMode = 5">3</xsl:if><!-- ����� -->
	</xsl:template>
	
	<!-- �����պ�Լ״̬ -->
	<xsl:template name="tran_Status">
		<xsl:param name="Status"></xsl:param>
		<xsl:if test="$Status = 0">076012</xsl:if><!-- ��Ч -->
		<xsl:if test="$Status = 1">076034</xsl:if><!-- ʧЧ -->
		<xsl:if test="$Status = 2">076035</xsl:if><!-- ��Ч -->
	</xsl:template>
	
	<!-- ��������ҵ�������� -->
	<xsl:template name="tran_Type">
		<xsl:param name="Type"></xsl:param>
		<xsl:if test="$Type = 0">003</xsl:if><!-- ���� -->
		<xsl:if test="$Type = PT">002</xsl:if><!-- ���� -->
		<xsl:if test="$Type = CT ">004</xsl:if><!-- �˱� -->
	</xsl:template>

</xsl:stylesheet>
