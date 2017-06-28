<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TranData">
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
								<Plchd_Nm><xsl:value-of select="Body/AppntName" /></Plchd_Nm>
								<!--���ձ�������� -->
								<Ins_Ulyg_Nm><xsl:value-of select="Body/Corpore" /></Ins_Ulyg_Nm>
								<!-- ��һ����������-->
								<Fst_Benf_Nm><xsl:value-of select="Body/BnfName1st" /></Fst_Benf_Nm>
								<!-- Ͷ������  -->
								<Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToDouble(Body/Amnt)" /></Ins_Cvr>
								<!-- ����������-->
								<RspbPsn_Nm><xsl:value-of select="Body/HandlerName" /></RspbPsn_Nm>
								<!-- ������֤�����ʹ���-->
								<RspbPsn_Crdt_TpCd>
									<xsl:call-template name="tran_idtype">
										<xsl:with-param name="idtype">
											<xsl:value-of select="Body/HandlerIDType" />
									</xsl:with-param></xsl:call-template>
								</RspbPsn_Crdt_TpCd>
								<!-- ������֤������-->
								<RspbPsn_Crdt_No><xsl:value-of select="Body/HandlerIDNo" /></RspbPsn_Crdt_No>
								<!--������Ч���� -->
								<InsPolcy_EfDt><xsl:value-of select="Body/ContStartDate" /></InsPolcy_EfDt>
								<!-- ����ʧЧ����-->
								<InsPolcy_ExpDt><xsl:value-of select="Body/ContEndDate" /></InsPolcy_ExpDt>
								<!-- �����պ�Լ״̬����-->
								<AcIsAR_StCd>
									<xsl:call-template name="tran_contstate">
										<xsl:with-param name="contstate">
											<xsl:value-of select="Body/ContState" />
									</xsl:with-param></xsl:call-template>
								</AcIsAR_StCd>
								<!-- ����������-->
								<Rcgn_Nm><xsl:value-of select="Body/InsuredName" /></Rcgn_Nm>
								<!-- Ͷ������-->
								<Ins_Cps><xsl:value-of select="Body/Mult" /></Ins_Cps>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
	<!-- ֤������ ����һ��ֵ��Ӧ���ж��ֵ �������ȷ��-->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '0'">1010</xsl:when><!-- ���֤ -->
			<xsl:when test="$idtype = '1'">1052</xsl:when><!-- ���� -->
			<xsl:when test="$idtype = '2'">1020</xsl:when><!-- (����֤)����֤ -->
			<xsl:when test="$idtype = '4'">1040</xsl:when><!-- ���ڱ� -->
			<xsl:when test="$idtype = 'F'">1070</xsl:when><!-- �ۡ��ġ�̨ͨ��֤ -->
			<xsl:otherwise>
					<xsl:value-of select="1999" /><!-- ���� -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- �����պ�Լ״̬���� 
	1-�б� 
	2-������ֹ 
	3-�˱���ֹ 
	B-������
	 -->
	<xsl:template name="tran_contstate">
		<xsl:param name="contstate"></xsl:param>
		<xsl:choose>
			<xsl:when test="$contstate = '1'">076036</xsl:when><!-- �б� -->
			<xsl:when test="$contstate = '2'">076024</xsl:when><!-- ������ֹ-->
			<xsl:when test="$contstate = '3'">076025</xsl:when><!-- �˱���ֹ-->
			<xsl:when test="$contstate = 'B'">076047</xsl:when><!-- ������-->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>	
