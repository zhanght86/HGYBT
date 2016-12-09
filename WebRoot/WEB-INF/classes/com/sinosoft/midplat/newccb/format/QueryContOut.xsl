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
								 
							<!-- ���չ�˾����  -->
							<Ins_Co_Nm><xsl:value-of select="/TranData/Body/ComName"/></Ins_Co_Nm>
							<!-- ���չ�˾��� -->
							<Ins_Co_ID></Ins_Co_ID>
							<!-- ѭ����¼����  -->
							<Rvl_Rcrd_Num><xsl:value-of select="count(/TranData/Body/Detail)"/></Rvl_Rcrd_Num>
							<!-- �ͻ�����ѭ�� -->
							<MyInsu_List>
								<xsl:for-each select="/TranData/Body/Detail">
									<MyInsu_Detail>
						        		<!-- �������ײͱ�� -->
						        		<AgIns_Pkg_ID><xsl:value-of select="AgInsPkgID"/></AgIns_Pkg_ID>
						        		<!-- �ײ�����  -->
						        		<Pkg_Nm><xsl:value-of select="PkgNm"/></Pkg_Nm>
										<!-- ���ֱ�� -->
								        <Cvr_ID><xsl:value-of select="RiskCode" /></Cvr_ID>
										<!-- �������� -->
										<Cvr_Nm><xsl:value-of select="RiskName"/></Cvr_Nm>
										<!-- �������� -->
										<InsPolcy_No><xsl:value-of select="ContNo"/></InsPolcy_No>
										<!-- �����պ�Լ״̬���� -->
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
										<AcIsAR_StCd>
										   	<xsl:call-template name="states">
												<xsl:with-param name="States">
													<xsl:value-of select="PolicyStatus" />
												</xsl:with-param>
											</xsl:call-template>
										</AcIsAR_StCd>
										<!-- �����Ǽ����� -->
										<InsPolcy_RgDt><xsl:value-of select="PolApplyDate"/></InsPolcy_RgDt>
										<!-- ���ѽ�� -->
										<InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></InsPrem_Amt>
										<!-- ������������  ����-->
										<Agnc_Chnl_Cd>
											<xsl:call-template name="salechnl">
												<xsl:with-param name="Salechnls">
													<xsl:value-of select="SaleChanel"/>
												</xsl:with-param>
											</xsl:call-template>	
										</Agnc_Chnl_Cd>
										<!-- ���д����־ -->
										<CCB_Agnc_Ind>1</CCB_Agnc_Ind>
										<!-- #�����ֶ�һ -->
										<Rsrv_Fld_1></Rsrv_Fld_1>
										<!-- #�����ֶζ� -->
										<Rsrv_Fld_2></Rsrv_Fld_2>
										<!-- #�����ֶ��� -->
										<Rsrv_Fld_3></Rsrv_Fld_3>
									</MyInsu_Detail>
								</xsl:for-each>
							</MyInsu_List>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
<!-- �����պ�Լ״̬���� -->
	<xsl:template name="states">
		<xsl:param name="States">0</xsl:param>
		<xsl:if test="$States = 0">076012</xsl:if><!-- ��Ч -->
		<xsl:if test="$States =1">076034</xsl:if><!-- ʧЧ -->
		<xsl:if test="$States = 2">076036</xsl:if><!-- ��Ч -->
	</xsl:template>	
	
<!-- ������������ -->
	<xsl:template name="salechnl">
		<xsl:param name="Salechnls">9999</xsl:param>
		<xsl:if test="$Salechnls = '01'">9999</xsl:if><!-- �������� -->
		<xsl:if test="$Salechnls = '02'">9999</xsl:if><!-- ����Ӫ�� -->
		<xsl:if test="$Salechnls = '03'">9999</xsl:if><!-- ���д��� -->
		<xsl:if test="$Salechnls = '04'">9999</xsl:if><!-- �н����� -->
		<xsl:if test="$Salechnls = '05'">9999</xsl:if><!-- �������� -->
	</xsl:template>	
	
</xsl:stylesheet>