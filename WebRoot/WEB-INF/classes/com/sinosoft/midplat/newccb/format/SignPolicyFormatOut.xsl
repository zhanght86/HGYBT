<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="TranData">
		<TX>
			<!-- 报文头 -->
			<TX_HEADER>
			     <!-- 报文头长度 -->
				<SYS_HDR_LEN></SYS_HDR_LEN>
			     <!-- 协议版本号 -->
				<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
			     <!-- 报文总长度 -->
				<SYS_TTL_LEN></SYS_TTL_LEN>
			     <!-- 发送方安全节点编号 -->
				<SYS_SND_SEC_ID>510050</SYS_SND_SEC_ID>
			     <!-- 发起发安全节点编号  转换类中添加-->
				<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
			     <!--服务种类-->
				<SYS_TX_TYPE>020000</SYS_TX_TYPE>
			     <!-- 全局事件跟踪号  转换类中添加-->
				<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
			     <!-- 子交易序号  转换类中添加-->
				<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
			     <!-- 应用报文格式类型 -->
				<SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
			     <!-- 应用报文长度  转换类中添加-->
				<SYS_MSG_LEN></SYS_MSG_LEN>
			     <!-- 应用报文是否加密 -->
				<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
			     <!-- 应用报文加密方式 -->
				<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
			     <!-- 应用报文压缩方式 -->
				<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
			     <!-- 捎带报文长度 -->
				<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
			     <!-- 服务接受时间 转换类中添加-->
				<SYS_RECV_TIME></SYS_RECV_TIME>    
			     <!-- 服务响应时间  转换类中添加-->
				<SYS_RESP_TIME></SYS_RESP_TIME>
			     <!-- 报文状态类型  -->
				<SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
				<xsl:if test = "/TranData/Head/Flag='0'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/Head/Flag !='0'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>01</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE></SYS_RESP_CODE>    
				</xsl:if>
			     <!-- 服务响应描述长度  转换类中添加-->
				<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
			     <!-- 服务响应描述 -->
				<SYS_RESP_DESC></SYS_RESP_DESC>    
			</TX_HEADER>
	
		<!-- 报文体 -->
			<TX_BODY>
	      			<COMMON>
	         			<FILE_LIST_PACK>
	         				<!-- 文件个数 -->
				            <FILE_NUM>1</FILE_NUM>
				            <!-- 文件处理方式 -->
				            <FILE_MODE></FILE_MODE>
				        	<!-- 文件节点 -->
				            <FILE_NODE></FILE_NODE>
				            <!-- 打包后后文件名 -->
				            <FILE_NAME_PACK></FILE_NAME_PACK>
				            <!-- 打包文件获取路径 -->
				            <FILE_PATH_PACK></FILE_PATH_PACK>
				            <!-- 文件信息 -->
				            <FILE_INFO>
				            <!-- 文件信息 -->
				               <FILE_NAME></FILE_NAME>
				            <!-- 文件路径 -->
				               <FILE_PATH></FILE_PATH>
				            </FILE_INFO>
	         			</FILE_LIST_PACK>
	      			</COMMON>
	      			<ENTITY>
			        	<APP_ENTITY>
			        			<!-- 投保人名称-->
								<Plchd_Nm><xsl:value-of select="Body/AppntName" /></Plchd_Nm>
								<!--保险标的物名称 -->
								<Ins_Ulyg_Nm><xsl:value-of select="Body/Corpore" /></Ins_Ulyg_Nm>
								<!-- 第一受益人名称-->
								<Fst_Benf_Nm><xsl:value-of select="Body/BnfName1st" /></Fst_Benf_Nm>
								<!-- 投保保额  -->
								<Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToDouble(Body/Amnt)" /></Ins_Cvr>
								<!-- 经办人名称-->
								<RspbPsn_Nm><xsl:value-of select="Body/HandlerName" /></RspbPsn_Nm>
								<!-- 经办人证件类型代码-->
								<RspbPsn_Crdt_TpCd>
									<xsl:call-template name="tran_idtype">
										<xsl:with-param name="idtype">
											<xsl:value-of select="Body/HandlerIDType" />
									</xsl:with-param></xsl:call-template>
								</RspbPsn_Crdt_TpCd>
								<!-- 经办人证件号码-->
								<RspbPsn_Crdt_No><xsl:value-of select="Body/HandlerIDNo" /></RspbPsn_Crdt_No>
								<!--保单生效日期 -->
								<InsPolcy_EfDt><xsl:value-of select="Body/ContStartDate" /></InsPolcy_EfDt>
								<!-- 保单失效日期-->
								<InsPolcy_ExpDt><xsl:value-of select="Body/ContEndDate" /></InsPolcy_ExpDt>
								<!-- 代理保险合约状态代码-->
								<AcIsAR_StCd>
									<xsl:call-template name="tran_contstate">
										<xsl:with-param name="contstate">
											<xsl:value-of select="Body/ContState" />
									</xsl:with-param></xsl:call-template>
								</AcIsAR_StCd>
								<!-- 被保人名称-->
								<Rcgn_Nm><xsl:value-of select="Body/InsuredName" /></Rcgn_Nm>
								<!-- 投保份数-->
								<Ins_Cps><xsl:value-of select="Body/Mult" /></Ins_Cps>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
	<!-- 证件类型 核心一个值对应建行多个值 待与核心确认-->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '0'">1010</xsl:when><!-- 身份证 -->
			<xsl:when test="$idtype = '1'">1052</xsl:when><!-- 护照 -->
			<xsl:when test="$idtype = '2'">1020</xsl:when><!-- (军人证)军官证 -->
			<xsl:when test="$idtype = '4'">1040</xsl:when><!-- 户口本 -->
			<xsl:when test="$idtype = 'F'">1070</xsl:when><!-- 港、澳、台通行证 -->
			<xsl:otherwise>
					<xsl:value-of select="1999" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 代理保险合约状态代码 
	1-承保 
	2-犹退终止 
	3-退保终止 
	B-待对账
	 -->
	<xsl:template name="tran_contstate">
		<xsl:param name="contstate"></xsl:param>
		<xsl:choose>
			<xsl:when test="$contstate = '1'">076036</xsl:when><!-- 承保 -->
			<xsl:when test="$contstate = '2'">076024</xsl:when><!-- 犹退终止-->
			<xsl:when test="$contstate = '3'">076025</xsl:when><!-- 退保终止-->
			<xsl:when test="$contstate = 'B'">076047</xsl:when><!-- 待对账-->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>	
