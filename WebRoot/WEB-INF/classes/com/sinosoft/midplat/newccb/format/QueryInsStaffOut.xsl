<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
	<xsl:template match="/">
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
				            <FILE_NUM>0</FILE_NUM>
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
	      			<xsl:if test = "/TranData/Head/Flag ='0'">
			        	<APP_ENTITY>
							<!-- #循环记录条数  -->
							<Rvl_Rcrd_Num><xsl:value-of select="count(/TranData/Body/Detail)"/></Rvl_Rcrd_Num>
							<InsCoAcrdtStff_List>
								<xsl:for-each select="/TranData/Body/Detail">
									<InsCoAcrdtStff_Detail>
											<!-- 保险公司派驻人员姓名 -->
											<Ins_Co_Acrdt_Stff_Nm><xsl:value-of select="Name" /></Ins_Co_Acrdt_Stff_Nm>
											<!-- 证件类型 -->
											<Crdt_TpCd>
												<xsl:call-template name="tran_idtype">
													<xsl:with-param name="idtype">
														<xsl:value-of select="IDType" />
													</xsl:with-param>
												</xsl:call-template>
											</Crdt_TpCd>
											<!-- 证件号 -->
											<Crdt_No><xsl:value-of select="IDNo" /></Crdt_No>
											<!-- 移动电话号码 -->
											<Move_TelNo><xsl:value-of select="Mobile" /></Move_TelNo>
											<!-- 固定电话号码 -->
											<Fix_TelNo><xsl:value-of select="Phone" /></Fix_TelNo>
											<!-- 电子邮箱地址 -->
											<Email_Adr><xsl:value-of select="Email" /></Email_Adr>
									</InsCoAcrdtStff_Detail>		
								</xsl:for-each>
							</InsCoAcrdtStff_List>
			        	</APP_ENTITY>
			        	</xsl:if>   
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '0'">1010</xsl:when><!-- 公民身份证号码 -->
			<xsl:when test="$idtype = '2'">1022</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = 'D'">1032</xsl:when><!-- 警官证 -->
			<xsl:when test="$idtype = 'A'">1021</xsl:when><!-- 解放军士兵证 -->
			<xsl:when test="$idtype = '4'">1040</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = 'B'">1080'</xsl:when><!-- (港澳)回乡证及通行证 -->
			<xsl:when test="$idtype = '1'">1050</xsl:when><!-- 护照-->
			<xsl:when test="$idtype = '5'">1060</xsl:when><!-- 学生证-->
			<xsl:when test="$idtype = '6'">2999</xsl:when><!-- 对公其他证件-->
			<xsl:when test="$idtype = '3'">1100</xsl:when><!-- 驾照 -->
			<xsl:when test="$idtype = 'C'">1011</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = 'E'">1160</xsl:when><!-- 台湾居民身份证 台胞证 -->
			<xsl:otherwise>
					<xsl:value-of select="1999" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>