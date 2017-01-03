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
				<xsl:if test = "/TranData/RetData/Flag='1'">
			     <!-- ����״̬ -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- ������Ӧ�� -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/RetData/Flag ='0'">
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
							<!-- ѭ����¼���� -->
							<Rvl_Rcrd_Num><xsl:value-of select="count(/TranData/LCConts/LCCont/RiskCode)"/></Rvl_Rcrd_Num>
							<Insu_List>
								<xsl:for-each select="/TranData/LCConts/LCCont">
									<Insu_Detail>
										<!-- �ɷ�֪ͨ���� -->
										<PmNtc_BillNo></PmNtc_BillNo>
										<!-- Ͷ������ -->
										<Ins_BillNo><xsl:value-of select="substring(ProposalContNo,1,13)"/></Ins_BillNo>
										<!-- �����պ�Լ״̬���� -->
										<AcIsAR_StCd>
											<xsl:call-template name="tran_acisarStcd">
												<xsl:with-param name="acisarStcd">
													<xsl:value-of select="ContState"/>
												</xsl:with-param>
											</xsl:call-template>
										</AcIsAR_StCd>
										<!-- �˱���Ϣ -->
										<Uwrt_Inf><xsl:value-of select="UWError"/></Uwrt_Inf>
										<!-- �ܱ��ѽ�� -->
										<Tot_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(SumPrem)"/></Tot_InsPrem_Amt>
										<!-- ���սɷѽ��  -->
										<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Ins_PyF_Amt>
										<!-- �ɷѽ�ֹ���� -->
										<PyF_CODt><xsl:value-of select="PayEndDate"/></PyF_CODt>
										<!-- �������������� -->
										<Ins_Yr_Prd_CgyCd>
											<xsl:call-template name="tran_insYrPrdCgyCd">
												<xsl:with-param name="insYrPrdCgyCd">
													<xsl:value-of select="InsuYearFlag"/>
												</xsl:with-param>
											</xsl:call-template>
										</Ins_Yr_Prd_CgyCd>
										<!-- �������� -->
										<Ins_Ddln><xsl:value-of select="InsuYear"/></Ins_Ddln>
										<!-- �������ڴ��� -->
										<Ins_Cyc_Cd>
											<xsl:call-template name="tran_insCycCd">
												<xsl:with-param name="insCycCd">
													<xsl:value-of select="InsuYearFlag"/>
												</xsl:with-param>
											</xsl:call-template>
										</Ins_Cyc_Cd>
										<!-- ���ѽɷѷ�ʽ���� -->
										<InsPrem_PyF_MtdCd><xsl:value-of select="PayEndYearFlag"/></InsPrem_PyF_MtdCd>
										<!-- ���ѽɷ����� -->
										<InsPrem_PyF_Prd_Num><xsl:value-of select="PayEndYear"/></InsPrem_PyF_Prd_Num>
										<!-- ���ѽɷ����ڴ��� format������� -->
										<InsPrem_PyF_Cyc_Cd></InsPrem_PyF_Cyc_Cd>
									</Insu_Detail>
								</xsl:for-each>
							</Insu_List>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
	<xsl:template name="tran_insYrPrdCgyCd">
		<xsl:param name="insYrPrdCgyCd"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insYrPrdCgyCd='A'">04</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insCycCd">
		<xsl:param name="insCycCd"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insCycCd !='A'"><xsl:value-of select="$insCycCd" /></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_acisarStcd">
		<xsl:param name="acisarStcd"></xsl:param>
		<xsl:choose>
			<xsl:when test="$acisarStcd=01">076012</xsl:when><!-- ��Ч -->
			<xsl:when test="$acisarStcd=13">076034</xsl:when><!-- ʧЧ -->
			<xsl:when test="$acisarStcd=02">076035</xsl:when><!-- ��Ч -->
			<xsl:when test="$acisarStcd=03">076038</xsl:when><!-- ͣЧ -->
			<xsl:when test="$acisarStcd=04">076030</xsl:when><!-- ������ֹ -->
			<xsl:when test="$acisarStcd=05">076025</xsl:when><!-- ��Լ��ֹ -->
			<xsl:when test="$acisarStcd=06">076023</xsl:when><!-- ������ֹ -->
			<xsl:when test="$acisarStcd=98">076014</xsl:when><!-- ���չ�˾�Ѿ����շ�ʵʱ�˱���Ϣ -->
			<xsl:when test="$acisarStcd=99">076018</xsl:when><!-- ���չ�˾δ�ҵ���Ͷ���� -->
			<xsl:when test="$acisarStcd=07"></xsl:when><!-- �˱�ͨ�� -->
			<xsl:when test="$acisarStcd=09">076016</xsl:when><!-- �ܱ� �ͻ��ύ�˱������չ�˾�ܱ� -->
			<xsl:when test="$acisarStcd=11"></xsl:when><!-- ���� -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>