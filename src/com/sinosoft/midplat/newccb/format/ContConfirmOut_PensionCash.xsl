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
							<!-- �������ֱ�� -->
					        <MainIns_Cvr_ID><xsl:value-of select="/TranData/Body/Risk/MainRiskCode" /></MainIns_Cvr_ID>
			        		<!-- �������ײͱ�� -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
					        <!-- ����ʧЧ����-->
					        <InsPolcy_ExpDt><xsl:value-of select="/TranData/Body/Risk/InsuEndDate" /></InsPolcy_ExpDt>
			        		<!-- ������ȡ���� -->
			        		<InsPolcy_Rcv_Dt><xsl:value-of select="/TranData/Body/Risk/GetStartDate"/></InsPolcy_Rcv_Dt>
							<!-- ������ -->
							<InsPolcy_No><xsl:value-of select="/TranData/Body/ContNo"/></InsPolcy_No>
							<!-- ���ս��ɽ�� -->
							<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk/Prem)"/></Ins_PyF_Amt>
							<!-- Ͷ���˵����ʼ���ַ-->
							<Plchd_Email_Adr><xsl:value-of select="/TranData/Body/Appnt/Email"/></Plchd_Email_Adr>
							<!-- ����Ͷ������-->
							<InsPolcy_Ins_Dt><xsl:value-of select="/TranData/Body/Risk/PolApplyDate"/></InsPolcy_Ins_Dt>
			        		<!-- ������Ч���� -->
			        		<InsPolcy_EfDt><xsl:value-of select="/TranData/Body/Risk/CValiDate"/></InsPolcy_EfDt>
			        		<!-- �������ڽɴ����˺� -->
			        		<AgInsRgAutoDdcn_AccNo></AgInsRgAutoDdcn_AccNo>
			        		<!-- ÿ�ڽɷѽ����Ϣ -->
			        		<EcIst_PyF_Amt_Inf><xsl:value-of select="/TranData/Body/Risk/Prem"/></EcIst_PyF_Amt_Inf>
			        		<!-- ���ѽɷѷ�ʽ���� -->
			        		<InsPrem_PyF_MtdCd>
						        <xsl:call-template name="tran_Contpayintv">
									<xsl:with-param name="payintv">
										<xsl:value-of select="/TranData/Body/Risk/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
			        		</InsPrem_PyF_MtdCd>
			        		<!-- ���ѽɷ����� -->
			        		<InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/Body/Risk/PayEndYear"/></InsPrem_PyF_Prd_Num>
			        		<!-- ���ѽɷ����ڴ��� -->
			        		<InsPrem_PyF_Cyc_Cd>
						        <xsl:call-template name="tran_PbPayPerCode">
									<xsl:with-param name="PbPayPerCode">
										<xsl:value-of select="/TranData/Body/Risk/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
			        		</InsPrem_PyF_Cyc_Cd>			        		
			        		<!-- ������ԥ�� -->
			        		<InsPolcy_HsitPrd><xsl:value-of select="/TranData/Body/HesitatePeriod"/></InsPolcy_HsitPrd>
			        		<!-- �����ļ����� -->
			       <!-- 	<Ret_File_Num><xsl:value-of select="/TranData/Body/Ret_File_Num"/></Ret_File_Num> --> 	
			        		<Ret_File_Num>3</Ret_File_Num>
					<!-- 	<xsl:for-each select="/TranData/Body/Detail_List">
								<Detail_List>
									<Prmpt_Inf_Dsc><xsl:value-of select="Prmpt_Inf_Dsc"/></Prmpt_Inf_Dsc>
									<AgIns_Vchr_TpCd><xsl:value-of select="AgIns_Vchr_TpCd"/></AgIns_Vchr_TpCd>
									<Rvl_Rcrd_Num><xsl:value-of select="Rvl_Rcrd_Num"/></Rvl_Rcrd_Num>
									<xsl:for-each>
										<Detail>
											<Ret_Inf><xsl:value-of select="Detail/Ret_Inf"/></Ret_Inf>
										</Detail>
									</xsl:for-each>
								</Detail_List>
							</xsl:for-each>   -->	

							<Detail_List>
								<!-- ��ʾ��Ϣ���� -->
								<Prmpt_Inf_Dsc>������һҳ</Prmpt_Inf_Dsc>
								<!-- ������ƾ֤���� -->
								<!-- 	1	����
										2	����
										3	�ֽ��ֵ��
										4	��Ʊ
										5	����ƾ֤
										6	Ͷ����
										7	�ͻ�Ȩ�汣��ȷ���� -->
								<AgIns_Vchr_TpCd>1</AgIns_Vchr_TpCd>
								<!-- #ѭ����¼���� -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								<!--һ�д�ӡ������--> 
								<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
								<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf>��</Ret_Inf> 
								<Ret_Inf><xsl:text>         </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ </Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf/>
								<Ret_Inf><xsl:text>         </xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>      
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																												<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday),2)"/><xsl:text>            </xsl:text>  
																												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
								</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																												<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday),2)"/><xsl:text>            </xsl:text>
																												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
								</Ret_Inf>
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" /> 
								<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
								<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
								<xsl:for-each select="/TranData/Body/Bnf">
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text></xsl:text>����������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
								<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
								 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
								<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			                   	</Ret_Inf>
								</xsl:for-each>
								<xsl:choose>
								<xsl:when test="$num = 0"><Ret_Inf><xsl:text>         </xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
								<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
								 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
								<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			                   	</Ret_Inf></xsl:when>
								</xsl:choose>		
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>      
								<Ret_Inf><xsl:text>         </xsl:text>��������                          �����ڼ�    ��������    ���ѷ�ʽ  ������������/����   ���շ�</Ret_Inf>
								<xsl:for-each select="/TranData/Body/Risk">
								<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
								<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
								<Ret_Inf>
								<!-- �������� -->
								<xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 34)"/>
								                                                     <xsl:choose>
																										<xsl:when test="InsuYearFlag = 'A'"><xsl:text>��</xsl:text>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																										</xsl:when>
																										<xsl:when test="InsuYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																										</xsl:when>  
																										<xsl:when test="InsuYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��</xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:text>     </xsl:text>
																						<xsl:choose>
																										<xsl:when test="PayIntv = 0">
																											<xsl:text>����        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'D'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																										</xsl:when>  
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����    </xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:apply-templates select="PayIntv"/>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																						 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</Ret_Inf>
								</xsl:for-each>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								<Ret_Inf><xsl:text>         </xsl:text>���շѺϼƣ�<xsl:value-of select="TranData/Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)"/>Ԫ��</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>������ȡ��ʽ��<xsl:apply-templates select="TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Ret_Inf>
								<xsl:choose>
								       <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/GetYearFlag='E'">
								           <Ret_Inf><xsl:text>         </xsl:text>�����ȡ��ʼ���䣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��������',52)"/>�����ȡ���ޣ�<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>��</Ret_Inf>
								      </xsl:when>
								       <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/GetYearFlag!='E'">
								           <Ret_Inf><xsl:text>         </xsl:text>�����ȡ��ʼ���䣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��������',52)"/>�����ȡ���ޣ�<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>��</Ret_Inf>
								      </xsl:when>
								      <xsl:otherwise>
								            <Ret_Inf><xsl:text>        </xsl:text>�����ȡ��ʼ���䣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Risk[RiskCode=MainRiskCode]/GetYear,52)"/>�����ȡ���ޣ�<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>��</Ret_Inf>
								      </xsl:otherwise>
								</xsl:choose>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>�ر�Լ����</xsl:text>
																								<xsl:choose>
																										<xsl:when test="$MainRisk/SpecContent = ''">
																											<xsl:text>���ޣ�</xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="$MainRisk/SpecContent"/>
																										</xsl:otherwise>
																								</xsl:choose>
								</Ret_Inf>
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>�����������ƣ�<xsl:value-of select="TranData/Body/AgentComName"/></Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>����������Ա����/���룺<xsl:value-of select="TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/SaleStaff,40)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 55)"/>��������绰��<xsl:value-of select="TranData/Body/AgentPhone"/></Ret_Inf>
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Ret_Inf>
								<Ret_Inf /> 
							    </Detail>
								 </Detail_List>
								 <Detail_List>
								  <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Amnt)"/>
								  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
								  <xsl:variable name="MainRisk" select="TranData/Body/Risk[RiskCode=MainRiskCode]"/>
								<!-- ��ʾ��Ϣ���� -->
								<Prmpt_Inf_Dsc>�����ڶ�ҳ</Prmpt_Inf_Dsc>
								<!-- ������ƾ֤���� ?-->
								<AgIns_Vchr_TpCd>3</AgIns_Vchr_TpCd>
								<!-- #ѭ����¼���� -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf><xsl:text></xsl:text>                                                 �ֽ��ֵ��������                                               </Ret_Inf>
								 <Ret_Inf/>
								 <Ret_Inf><xsl:text>     </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/></Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>������ <xsl:text>         </xsl:text>������|������<xsl:text>          </xsl:text>������|������<xsl:text>         </xsl:text>������</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>��ĩ <xsl:text> </xsl:text>�ֽ��ֵ<xsl:text>  </xsl:text>�Ļ�������|��ĩ <xsl:text> </xsl:text>�ֽ��ֵ<xsl:text>  </xsl:text>�Ļ�������|��ĩ <xsl:text> </xsl:text>�ֽ��ֵ<xsl:text>  </xsl:text>�Ļ�������</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ��</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>�ֽ��ֵ�Ͷ���������ĸ���������ʹ���������á�</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������</Ret_Inf>
							     <Ret_Inf><xsl:text>     </xsl:text>˾���紹ѯ��</Ret_Inf>
							     <Ret_Inf><xsl:text>     </xsl:text>*���Ϊ����屣�պ󣬱���ͬ�����ٲμ��Ժ����ȵĺ������䡣</Ret_Inf>			    
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								</Detail>	 
							</Detail_List>
							<Detail_List>
							    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
								<!-- ��ʾ��Ϣ���� -->
								<Prmpt_Inf_Dsc>��������ҳ</Prmpt_Inf_Dsc>
								<!-- ������ƾ֤���� ?-->
								<AgIns_Vchr_TpCd>9</AgIns_Vchr_TpCd>
								<!-- #ѭ����¼���� -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf><xsl:text>     </xsl:text>                                     ���պ�ͬ�ʹ��ִ</Ret_Inf>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf><xsl:text>     </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></Ret_Inf>
								       <Ret_Inf><xsl:text>     </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>��Ͳ�Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text></xsl:text></Ret_Inf>
									   <Ret_Inf/>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>��ִ���ء�</xsl:text></Ret_Inf>
									   <Ret_Inf/>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Ret_Inf>
							  </Detail>	 
							</Detail_List>	

			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
	      	
		</TX>
	</xsl:template>
	
<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
       <!--  <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
					<Ret_Inf><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,12,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndYearAmnt),12,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',18,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise>								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>  | <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+35)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',11,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+70)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/Cash),11,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/></xsl:otherwise></xsl:choose> </xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if> -->
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '231302'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (36)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
					<Ret_Inf><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,12,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndYearAmnt),10,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',10,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise>								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>  | <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+35)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',11,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+70)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/Cash),11,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/></xsl:otherwise></xsl:choose> </xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if>

</xsl:template>

<!-- �ɷ�Ƶ��/���ѽ��ɷ�ʽ -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

	<!-- �����Ľɷѷ�ʽ -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = -1">01</xsl:when><!-- �����ڽ� -->
			<xsl:when test="$payintv = 0">02</xsl:when><!-- ���� -->
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> �½� -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> ���� -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> ���꽻 -->
			<xsl:when test="$payintv = 12">03</xsl:when><!-- �꽻 -->
			<xsl:when test="$payintv = 98">04</xsl:when><!-- ����ĳȷ���� -->
			<xsl:when test="$payintv = 99">05</xsl:when><!-- ������ -->
		</xsl:choose>
	</xsl:template>

	<!-- ���ѽ������ڴ��� -->
	<xsl:template name="tran_PbPayPerCode">
		<xsl:param name="PbPayPerCode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PbPayPerCode = 3">0201</xsl:when><!-- ���� -->
			<xsl:when test="$PbPayPerCode = 0">0100</xsl:when><!-- ���� -->
			<xsl:when test="$PbPayPerCode = 12">0203</xsl:when><!-- �꽻 -->
			<xsl:when test="$PbPayPerCode = 6">0202</xsl:when><!-- ���꽻 -->
			<xsl:when test="$PbPayPerCode = 1">0204</xsl:when><!-- �½� -->
			<xsl:otherwise>
				<xsl:value-of select="9999" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
<!-- ������ȡ��ʽ��ת�� -->
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">�ۻ���Ϣ</xsl:when>
	<xsl:when test=".=2">��ȡ�ֽ�</xsl:when>
	<xsl:when test=".=3">�ֽ�����</xsl:when>
	<xsl:when test=".=5">�����</xsl:when>
	<xsl:when test=".=''">�ۻ���Ϣ  </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">��</xsl:when>	<!-- �� -->
	<xsl:when test=".=1">Ů</xsl:when>	<!-- Ů -->
	<xsl:when test=".=2">����</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 	
	<!-- ��������/�����־ -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">2</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = ''">0</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">1</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">2</xsl:when>
			<xsl:when test="$PbInsuYearFlag = ''">3</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'M'">4</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'D'">5</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'A'">6</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
</xsl:stylesheet>