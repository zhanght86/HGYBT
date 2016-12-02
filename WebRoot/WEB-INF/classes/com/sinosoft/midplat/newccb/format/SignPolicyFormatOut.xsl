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
				            <FILE_NUM>1</FILE_NUM>
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
			        			<!-- Ͷ��������-->
								<Plchd_Nm><xsl:value-of select="/TranData/Body/AppntName" /></Plchd_Nm>
								<!--���ձ�������� -->
								<Ins_Ulyg_Nm><xsl:value-of select="/TranData/Body/InsuFlag" /></Ins_Ulyg_Nm>
								<!-- ��һ����������-->
								<Fst_Benf_Nm><xsl:value-of select="/TranData/Body/BnfName" /></Fst_Benf_Nm>
								<!-- Ͷ������  -->
								<Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToDouble(/TranData/Body/Amnt)" /></Ins_Cvr>
								<!-- ����������-->
								<RspbPsn_Nm><xsl:value-of select="/TranData/Body/AgentName" /></RspbPsn_Nm>
								<!-- ������֤�����ʹ���-->
								<RspbPsn_Crdt_TpCd>
									<xsl:call-template name="tran_idtype">
										<xsl:with-param name="idtype">
											<xsl:value-of select="/TranData/Body/IDType" />
									</xsl:with-param></xsl:call-template>
								</RspbPsn_Crdt_TpCd>
								<!-- ������֤������-->
								<RspbPsn_Crdt_No><xsl:value-of select="/TranData/Body/IDNo" /></RspbPsn_Crdt_No>
								<!--������Ч���� -->
								<InsPolcy_EfDt><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(/TranData/Body/InsuStartDate)" /></InsPolcy_EfDt>
								<!-- ����ʧЧ����-->
								<InsPolcy_ExpDt><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(/TranData/Body/InsuEndDate)" /></InsPolcy_ExpDt>
								<!-- �����պ�Լ״̬����-->
								<AcIsAR_StCd>
									<xsl:call-template name="tran_policystatus">
										<xsl:with-param name="policystatus">
											<xsl:value-of select="/TranData/Body/PolicyStatus" />
									</xsl:with-param></xsl:call-template>
								</AcIsAR_StCd>
								<!-- ����������-->
								<Rcgn_Nm><xsl:value-of select="/TranData/Body/InsuredName" /></Rcgn_Nm>
								<!-- Ͷ������-->
								<Ins_Cps><xsl:value-of select="/TranData/Body/Mult" /></Ins_Cps>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
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
			<xsl:when test="$idtype = 'B'">1080</xsl:when><!-- (�۰�)����֤��ͨ��֤ -->
			<xsl:when test="$idtype = '1'">1050</xsl:when><!-- ����-->
			<xsl:when test="$idtype = '5'">1060</xsl:when><!-- ѧ��֤-->
			<xsl:when test="$idtype = '6'">1999</xsl:when><!-- ��������֤��-->
			<xsl:when test="$idtype = '3'">1100</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = 'C'">1011</xsl:when><!-- ��ʱ�������֤ -->
			<xsl:when test="$idtype = 'E'">1160</xsl:when><!-- ̨��������֤ ̨��֤ -->
			<xsl:otherwise>
					<xsl:value-of select="2999" /><!-- ���� -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
		<!--�����պ�Լ״̬���� -->
	<xsl:template name="tran_policystatus">
		<xsl:param name="policystatus"></xsl:param>
		<xsl:choose>
			<xsl:when test="$policystatus = '1'">076011</xsl:when><!-- ��������Ч���ͻ���ǩ�� -->
			<xsl:when test="$policystatus = '4'">076034</xsl:when><!-- ʧЧ-->
			<xsl:when test="$policystatus = 'B'">076034</xsl:when><!-- û�ж���-->
			<xsl:when test="$policystatus = 'F'">076034</xsl:when><!-- ����ʧ��-->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>	


