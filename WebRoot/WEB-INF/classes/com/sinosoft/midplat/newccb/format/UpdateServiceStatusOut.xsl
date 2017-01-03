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
				<xsl:if test = "/TranData/RetData/Flag='1'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/RetData/Flag ='0'">
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
			        	<APP_ENTITY>
							<!-- 循环记录条数 -->
							<Rvl_Rcrd_Num><xsl:value-of select="count(/TranData/LCConts/LCCont/RiskCode)"/></Rvl_Rcrd_Num>
							<Insu_List>
								<xsl:for-each select="/TranData/LCConts/LCCont">
									<Insu_Detail>
										<!-- 缴费通知单号 -->
										<PmNtc_BillNo></PmNtc_BillNo>
										<!-- 投保单号 -->
										<Ins_BillNo><xsl:value-of select="substring(ProposalContNo,1,13)"/></Ins_BillNo>
										<!-- 代理保险合约状态代码 -->
										<AcIsAR_StCd>
											<xsl:call-template name="tran_acisarStcd">
												<xsl:with-param name="acisarStcd">
													<xsl:value-of select="ContState"/>
												</xsl:with-param>
											</xsl:call-template>
										</AcIsAR_StCd>
										<!-- 核保信息 -->
										<Uwrt_Inf><xsl:value-of select="UWError"/></Uwrt_Inf>
										<!-- 总保费金额 -->
										<Tot_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(SumPrem)"/></Tot_InsPrem_Amt>
										<!-- 保险缴费金额  -->
										<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Ins_PyF_Amt>
										<!-- 缴费截止日期 -->
										<PyF_CODt><xsl:value-of select="PayEndDate"/></PyF_CODt>
										<!-- 保险年期类别代码 -->
										<Ins_Yr_Prd_CgyCd>
											<xsl:call-template name="tran_insYrPrdCgyCd">
												<xsl:with-param name="insYrPrdCgyCd">
													<xsl:value-of select="InsuYearFlag"/>
												</xsl:with-param>
											</xsl:call-template>
										</Ins_Yr_Prd_CgyCd>
										<!-- 保险期限 -->
										<Ins_Ddln><xsl:value-of select="InsuYear"/></Ins_Ddln>
										<!-- 保险周期代码 -->
										<Ins_Cyc_Cd>
											<xsl:call-template name="tran_insCycCd">
												<xsl:with-param name="insCycCd">
													<xsl:value-of select="InsuYearFlag"/>
												</xsl:with-param>
											</xsl:call-template>
										</Ins_Cyc_Cd>
										<!-- 保费缴费方式代码 -->
										<InsPrem_PyF_MtdCd><xsl:value-of select="PayEndYearFlag"/></InsPrem_PyF_MtdCd>
										<!-- 保费缴费期数 -->
										<InsPrem_PyF_Prd_Num><xsl:value-of select="PayEndYear"/></InsPrem_PyF_Prd_Num>
										<!-- 保费缴费周期代码 format类中添加 -->
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
			<xsl:when test="$acisarStcd=01">076012</xsl:when><!-- 有效 -->
			<xsl:when test="$acisarStcd=13">076034</xsl:when><!-- 失效 -->
			<xsl:when test="$acisarStcd=02">076035</xsl:when><!-- 复效 -->
			<xsl:when test="$acisarStcd=03">076038</xsl:when><!-- 停效 -->
			<xsl:when test="$acisarStcd=04">076030</xsl:when><!-- 满期终止 -->
			<xsl:when test="$acisarStcd=05">076025</xsl:when><!-- 解约终止 -->
			<xsl:when test="$acisarStcd=06">076023</xsl:when><!-- 契撤终止 -->
			<xsl:when test="$acisarStcd=98">076014</xsl:when><!-- 保险公司已经接收非实时核保信息 -->
			<xsl:when test="$acisarStcd=99">076018</xsl:when><!-- 保险公司未找到此投保单 -->
			<xsl:when test="$acisarStcd=07"></xsl:when><!-- 核保通过 -->
			<xsl:when test="$acisarStcd=09">076016</xsl:when><!-- 拒保 客户提交核保，保险公司拒保 -->
			<xsl:when test="$acisarStcd=11"></xsl:when><!-- 撤销 -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>