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
			        	<APP_ENTITY>
								 
							<!-- 保险公司名称  -->
							<Ins_Co_Nm><xsl:value-of select="/TranData/Body/ComName"/></Ins_Co_Nm>
							<!-- 保险公司编号 -->
							<Ins_Co_ID></Ins_Co_ID>
							<!-- 循环记录条数  -->
							<Rvl_Rcrd_Num><xsl:value-of select="count(/TranData/Body/Detail)"/></Rvl_Rcrd_Num>
							<!-- 客户保单循环 -->
							<MyInsu_List>
								<xsl:for-each select="/TranData/Body/Detail">
									<MyInsu_Detail>
						        		<!-- 代理保险套餐编号 -->
						        		<AgIns_Pkg_ID><xsl:value-of select="AgInsPkgID"/></AgIns_Pkg_ID>
						        		<!-- 套餐名称  -->
						        		<Pkg_Nm><xsl:value-of select="PkgNm"/></Pkg_Nm>
										<!-- 险种编号 -->
								        <Cvr_ID><xsl:value-of select="RiskCode" /></Cvr_ID>
										<!-- 险种名称 -->
										<Cvr_Nm><xsl:value-of select="RiskName"/></Cvr_Nm>
										<!-- 保单号码 -->
										<InsPolcy_No><xsl:value-of select="ContNo"/></InsPolcy_No>
										<!-- 代理保险合约状态代码 -->
										<!-- 
												Available: 0-有效 1-失效 （险种状态）
												Terminate: 0-未终止 1-终止 （险种状态）
												Lost: 0-未挂失 1-挂失 （保单状态）
												PayPrem: 0-未自垫 1-保费自动垫交 （险种状态）
												Loan: 0-未贷款 1-贷款 （保单状态）
												BankLoan: 0-未质押银行贷款 1-质押银行贷款 （保单状态）
												RPU：0-未减额缴清 1-减额缴清 （险种状态）
												暂时与核心约定 状态代码
																		0-有效
																		1-失效
																		2-复效
										 -->
										<AcIsAR_StCd>
										   	<xsl:call-template name="states">
												<xsl:with-param name="States">
													<xsl:value-of select="PolicyStatus" />
												</xsl:with-param>
											</xsl:call-template>
										</AcIsAR_StCd>
										<!-- 保单登记日期 -->
										<InsPolcy_RgDt><xsl:value-of select="PolApplyDate"/></InsPolcy_RgDt>
										<!-- 保费金额 -->
										<InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></InsPrem_Amt>
										<!-- 代理渠道代码  必填-->
										<Agnc_Chnl_Cd>
											<xsl:call-template name="salechnl">
												<xsl:with-param name="Salechnls">
													<xsl:value-of select="SaleChanel"/>
												</xsl:with-param>
											</xsl:call-template>	
										</Agnc_Chnl_Cd>
										<!-- 建行代理标志 -->
										<CCB_Agnc_Ind>1</CCB_Agnc_Ind>
										<!-- #保留字段一 -->
										<Rsrv_Fld_1></Rsrv_Fld_1>
										<!-- #保留字段二 -->
										<Rsrv_Fld_2></Rsrv_Fld_2>
										<!-- #保留字段三 -->
										<Rsrv_Fld_3></Rsrv_Fld_3>
									</MyInsu_Detail>
								</xsl:for-each>
							</MyInsu_List>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
<!-- 代理保险合约状态代码 -->
	<xsl:template name="states">
		<xsl:param name="States">0</xsl:param>
		<xsl:if test="$States = 0">076012</xsl:if><!-- 有效 -->
		<xsl:if test="$States =1">076034</xsl:if><!-- 失效 -->
		<xsl:if test="$States = 2">076036</xsl:if><!-- 复效 -->
	</xsl:template>	
	
<!-- 代理渠道代码 -->
	<xsl:template name="salechnl">
		<xsl:param name="Salechnls">9999</xsl:param>
		<xsl:if test="$Salechnls = '01'">9999</xsl:if><!-- 团险渠道 -->
		<xsl:if test="$Salechnls = '02'">9999</xsl:if><!-- 个人营销 -->
		<xsl:if test="$Salechnls = '03'">9999</xsl:if><!-- 银行代理 -->
		<xsl:if test="$Salechnls = '04'">9999</xsl:if><!-- 中介渠道 -->
		<xsl:if test="$Salechnls = '05'">9999</xsl:if><!-- 网销渠道 -->
	</xsl:template>	
	
</xsl:stylesheet>